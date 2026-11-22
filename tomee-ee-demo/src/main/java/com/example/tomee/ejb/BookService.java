package com.example.tomee.ejb;

import com.example.tomee.entity.Book;
import com.example.tomee.interceptor.Audited;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@Stateless
@Audited
public class BookService {

    @PersistenceContext(unitName = "tomeePU")
    private EntityManager em;

    public Book create(@Valid Book book) {
        em.persist(book);
        return book;
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