package com.example.tomcat.config;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ApplicationScoped
public class JpaConfig {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("tomcatPU");
    private final ValidatorFactory vf = Validation.buildDefaultValidatorFactory();

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

    @Produces
    @ApplicationScoped
    public Validator createValidator() {
        return vf.getValidator();
    }

    @PreDestroy
    public void shutdown() {
        vf.close();
        emf.close();
    }
}