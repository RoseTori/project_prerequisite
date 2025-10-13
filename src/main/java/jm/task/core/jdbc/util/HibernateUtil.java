package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HibernateUtil {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    static {
        try {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, String> dbSettings = new HashMap<>();

            dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/deus_schema?useSSL=false&serverTimezone=UTC");
            dbSettings.put(Environment.USER, "root");
            dbSettings.put(Environment.PASS, "_84Bibozoazapro");
            dbSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");


            dbSettings.put(Environment.SHOW_SQL, "true");
            dbSettings.put(Environment.FORMAT_SQL, "true");
            dbSettings.put(Environment.HBM2DDL_AUTO, "update");


            dbSettings.put(Environment.C3P0_MIN_SIZE, "5");
            dbSettings.put(Environment.C3P0_MAX_SIZE, "20");
            dbSettings.put(Environment.C3P0_TIMEOUT, "300");
            dbSettings.put(Environment.C3P0_MAX_STATEMENTS, "50");

            registryBuilder.applySettings(dbSettings);

            standardServiceRegistry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(standardServiceRegistry);


            sources.addAnnotatedClass(jm.task.core.jdbc.model.User.class);



            Metadata metaData = sources.getMetadataBuilder().build();
            sessionFactory = metaData.getSessionFactoryBuilder().build();

            System.out.println("Hibernate SessionFactory created successfully!");

        } catch (Exception e) {
            System.err.println("Failed to create Hibernate SessionFactory:");
            e.printStackTrace();
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
            throw new ExceptionInInitializerError("Failed to create SessionFactory: " + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
