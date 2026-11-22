package com.example.tomcat.service;

import com.example.tomcat.entity.Book;
import com.example.tomcat.interceptor.Audited;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@Audited
public class BookService {

    @Inject
    private EntityManager em;

    @Inject
    private Validator validator;

    public Book create(Book book) {
        // Explicit Bean Validation check (Hibernate Validator)
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Manual RESOURCE_LOCAL transaction boundary (or use Narayana JTA for @Transactional)
        em.getTransaction().begin();
        try {
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public String toXml(Book book) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Book.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(book, writer);
        return writer.toString();
    }
}