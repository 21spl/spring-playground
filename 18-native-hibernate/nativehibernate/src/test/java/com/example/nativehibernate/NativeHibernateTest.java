package com.example.nativehibernate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;


import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;



public class NativeHibernateTest {
    
    // SessionFactory implements AutoCloseable
    static SessionFactory sessionFactory;

    @BeforeAll
    static void setup(){
        sessionFactory = NativeHibernateBootstrap.createSessionFactory();
    }

    @AfterAll
    static void teardown(){
        if(sessionFactory!=null) sessionFactory.close();
    }

    @Test
    void storeAndLoadWithCriteriaApi(){

        // session also implements AutoCloseable
        try(Session session = sessionFactory.openSession()){

            // transaction 1 - insert
            session.beginTransaction();

            Message msg = new Message();
            msg.setText("Hello from Native Hibernate");
            session.persist(msg);

            // persist() is the JPA-compatible method name
            session.getTransaction().commit();
            //  INSERT INTO MESSAGE (TEXT) VALUES (?)

            // transaction 2 - Select via criteria API
            session.beginTransaction();

            // CriteriaBuilder: factory for query components
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // CriteriaQuery: the query definition - what type to return
            CriteriaQuery<Message> query = cb.createQuery(Message.class);

            // root: the FROM clause - "FROM Message e"
            query.from(Message.class);

            // Execute
            List<Message> messages = session.createQuery(query).getResultList();
            // SELECT ID, TEXT from MESSAGE

            session.getTransaction().commit();

            assertAll(
                () -> assertEquals("Hello from Native Hibernate", messages.get(0).getText()),
                () -> assertEquals(1, messages.size())
            );
        }
        // session auto-closed here
    }
}
