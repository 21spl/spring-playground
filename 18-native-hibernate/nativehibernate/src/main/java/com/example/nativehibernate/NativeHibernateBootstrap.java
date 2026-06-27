package com.example.nativehibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class NativeHibernateBootstrap {
    
    public static SessionFactory createSessionFactory(){

        // 1. Create a configuration - the settings container
        Configuration configuration = new Configuration();

        // 2. Load hibernate.cfg.xml from classpath
        // addAnnotationClass() tells Hibernate which classes are entities
        // alternative to listing them in the XML file
        configuration.configure().addAnnotatedClass(Message.class);

        // 3. Build the ServiceRegistry - Hibernate's internal service container
        // applySettings copies all properties from Configuration into it
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();

        // 4. Build the session factory - the final usable object
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        // SessionFactory IS a Hibernate object (not a proxy)
        System.out.println("Factory class: " + sessionFactory.getClass().getName());

        // output: org.hibernate.internal.SessionFactoryImpl

        return sessionFactory;
    }
}
