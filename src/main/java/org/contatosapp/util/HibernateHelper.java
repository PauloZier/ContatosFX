package org.contatosapp.util;

import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

public class HibernateHelper {

    private HibernateHelper() {

    }

    private static SessionFactory sessionFactory;

    private static EntityManagerFactory entityManagerFactory;

    public static Session openSession() {
        return sessionFactory.openSession();
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    static {

        Configuration cfg = new Configuration();

        cfg.setProperties(getHibernateProperties());

        for (Class<?> cls : getPersistentClasses()) {
            cfg.addAnnotatedClass(cls);
        }

        sessionFactory = cfg.buildSessionFactory();

        entityManagerFactory = sessionFactory.openSession().getEntityManagerFactory();

    }

    private static Properties getHibernateProperties() {

        Properties prop = new Properties();

        prop.put("hibernate.connection.driver_class", "org.h2.Driver");
        prop.put("hibernate.connection.url", "jdbc:h2:~/localdb/appdb");
        prop.put("hibernate.connection.username", "root");
        prop.put("hibernate.connection.password", "C0nT4t05_App@");
        prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        prop.put("hibernate.show_sql", "true");
        
        return prop;

    }

    private static Set<Class<?>> getPersistentClasses() {
        return new Reflections("").getTypesAnnotatedWith(Entity.class);
    }

    public static <T extends EntityManager, R> R runInTransaction(Function<T, R> function) {
        EntityManager em = HibernateHelper.getEntityManager();

        try {

            em.getTransaction().begin();

            R r = function.apply((T) em);

            em.getTransaction().commit();

            return r;

        } catch (Exception ex) {

            em.getTransaction().rollback();

            throw ex;

        } finally {

            em.close();

        }
    }

    public static <T extends EntityManager> void runInTransaction(Action<T> action) {
        EntityManager em = HibernateHelper.getEntityManager();

        try {

            em.getTransaction().begin();

            action.execute((T) em);

            em.getTransaction().commit();

        } catch (Exception ex) {

            em.getTransaction().rollback();

            throw ex;

        } finally {

            em.close();

        }
    }

    public static <T extends EntityManager, R> R runInSession(Function<T, R> function) {
        EntityManager em = HibernateHelper.getEntityManager();

        try {

            return function.apply((T) em);

        } catch (Exception ex) {

            throw ex;

        } finally {

            em.close();

        }
    }

    public static <T extends EntityManager> void runInSession(Action<T> action) {
        EntityManager em = HibernateHelper.getEntityManager();

        try {

            action.execute((T) em);

        } catch (Exception ex) {

            throw ex;

        } finally {

            em.close();

        }
    }
}
