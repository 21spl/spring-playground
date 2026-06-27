package com.example.jpademo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JPATest {

    // One EntityManagerFactory for all tests
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setup() {
        // Reads META-INF/persistence.xml
        emf = Persistence.createEntityManagerFactory("jpademo");
    }

    @AfterAll
    static void teardown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void factoryIsOpen() {
        assertNotNull(emf);
        assertTrue(emf.isOpen());
    }

    @Test
    void entityManagerCanBeCreated() {
        EntityManager em = emf.createEntityManager();

        assertNotNull(em);
        assertTrue(em.isOpen());

        em.close();
    }

    @Test
    void persistAndFind() {

        // ---------- Transaction 1 : Persist ----------
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        Message msg = new Message("Hello JPA");
        em.persist(msg);

        tx1.commit();

        Long generatedId = msg.getId();

        assertNotNull(generatedId);

        System.out.println("Generated ID = " + generatedId);

        em.close();

        // ---------- Transaction 2 : Find ----------
        EntityManager em2 = emf.createEntityManager();

        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();

        Message found = em2.find(Message.class, generatedId);

        assertNotNull(found);
        assertEquals("Hello JPA", found.getText());

        System.out.println("Found = " + found);

        tx2.commit();
        em2.close();
    }

    @Test
    void dirtyChecking() {

        EntityManager em = emf.createEntityManager();

        // ---------- Transaction 1 : Insert ----------
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        Message msg = new Message("Original Text...");
        em.persist(msg);

        tx1.commit();

        Long id = msg.getId();

        // ---------- Transaction 2 : Modify ----------
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();

        Message loaded = em.find(Message.class, id);

        loaded.setText("Modified Text...");

        // No persist() call needed
        tx2.commit();

        // ---------- Transaction 3 : Verify ----------
        EntityTransaction tx3 = em.getTransaction();
        tx3.begin();

        Message verified = em.find(Message.class, id);

        assertNotNull(verified);
        assertEquals("Modified Text...", verified.getText());

        tx3.commit();

        em.close();
    }
}