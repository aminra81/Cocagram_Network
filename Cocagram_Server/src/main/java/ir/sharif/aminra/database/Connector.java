package ir.sharif.aminra.database;

import ir.sharif.aminra.models.SaveAble;
import ir.sharif.aminra.util.Config;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.util.List;

public class Connector {
    private final static PrintStream logFilePrintStream;
    private final static Object lock = new Object();

    static {
        PrintStream temp;
        String logPath = Config.getConfig("hibernate").getProperty("logAddress");
        try {
            temp = new PrintStream(logPath);
        } catch (FileNotFoundException e) {
            temp = System.err;
        }
        logFilePrintStream = temp;
    }

    private final SessionFactory sessionFactory;


    @SneakyThrows
    public Connector() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        PrintStream err = System.err;
        System.setErr(logFilePrintStream);
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        System.setErr(err);
        return sessionFactory;
    }

    public void close() {
        sessionFactory.close();
    }

    public void save(SaveAble saveAble) {
        if (saveAble != null)
            synchronized (lock) {
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.saveOrUpdate(saveAble);
                session.getTransaction().commit();
                session.close();
            }
    }

    public void delete(SaveAble saveAble) {
        if (saveAble != null)
            synchronized (lock) {
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.delete(saveAble);
                session.getTransaction().commit();
                session.close();
            }
    }

    public <E extends SaveAble> E fetch(Class<E> entity, Serializable id) {
        synchronized (lock) {
            Session session = sessionFactory.openSession();
            E result = session.get(entity, id);
            session.close();
            return result;
        }
    }


    public <E extends SaveAble> List<E> fetchAll(Class<E> entity) {
        String hql = "from " + entity.getName();
        return executeHQL(hql, entity);
    }

    public <E extends SaveAble> List<E> executeHQL(String hql, Class<E> entity) {
        synchronized (lock) {
            Session session = sessionFactory.openSession();
            List<E> result = session.createQuery(hql, entity).getResultList();
            session.close();
            return result;
        }
    }
}
