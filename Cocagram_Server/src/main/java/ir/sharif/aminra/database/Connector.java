package ir.sharif.aminra.database;

import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.SaveAble;
import ir.sharif.aminra.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.*;
import java.util.List;

public class Connector {
    private final static Object lock = new Object();
    static private final Logger logger = LogManager.getLogger(Connector.class);
    private static Connector connector;

    private final SessionFactory sessionFactory;

    public static Connector getInstance() throws DatabaseDisconnectException {
        if(connector == null)
            connector = new Connector();
        return connector;
    }


    public Connector() throws DatabaseDisconnectException {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() throws DatabaseDisconnectException {
        try {
            ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            logger.info("connected to database");
            return sessionFactory;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("can't connect to database");
            throw new DatabaseDisconnectException(throwable);
        }
    }

    public void close() {
        sessionFactory.close();
    }

    public void save(SaveAble saveAble) throws DatabaseDisconnectException {
        if (saveAble != null)
            synchronized (lock) {
                try {
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    logger.info("transaction started");
                    session.saveOrUpdate(saveAble);
                    logger.info("instance saved : " + saveAble);
                    session.getTransaction().commit();
                    logger.info("transaction committed");
                    session.close();
                } catch (Throwable throwable) {
                    logger.error("instance not saved : " + saveAble);
                    throw new DatabaseDisconnectException(throwable);
                }
            }
    }

    public void delete(SaveAble saveAble) throws DatabaseDisconnectException {
        if (saveAble != null)
            synchronized (lock) {
                try {
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    logger.info("transaction started");
                    session.delete(saveAble);
                    logger.info("instance deleted : " + saveAble);
                    session.getTransaction().commit();
                    logger.info("transaction committed");
                    session.close();
                } catch (Throwable throwable) {
                    logger.error("instance not deleted : " + saveAble);
                    throw new DatabaseDisconnectException(throwable);
                }
            }
    }

    public <E extends SaveAble> E fetch(Class<E> entity, Serializable id) throws DatabaseDisconnectException {
        synchronized (lock) {
            try {
                Session session = sessionFactory.openSession();
                E result = session.get(entity, id);
                logger.info("instance fetched");
                session.close();
                return result;
            } catch (Throwable throwable) {
                logger.error("can't fetch instance");
                throw new DatabaseDisconnectException(throwable);
            }
        }
    }


    public <E extends SaveAble> List<E> fetchAll(Class<E> entity) throws DatabaseDisconnectException {
        String hql = "from " + entity.getName();
        return executeHQL(hql, entity);
    }

    public <E extends SaveAble> List<E> executeHQL(String hql, Class<E> entity) throws DatabaseDisconnectException {
        synchronized (lock) {
            try {
                Session session = sessionFactory.openSession();
                List<E> result = session.createQuery(hql, entity).getResultList();
                logger.info("instances fetched");
                session.close();
                return result;
            } catch (Throwable throwable) {
                logger.error("can't fetch instances");
                throw new DatabaseDisconnectException(throwable);
            }
        }
    }

    public User getUserByUsername(String username) throws DatabaseDisconnectException {
        List<User> users = fetchAll(User.class);
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public User getUserByEmail(String email) throws DatabaseDisconnectException {
        List<User> users = fetchAll(User.class);
        for (User user : users)
            if(user.getEmail().equals(email))
                return user;
        return null;
    }

    public User getUserByPhoneNumber(String phoneNumber) throws DatabaseDisconnectException {
        List<User> users = fetchAll(User.class);
        for (User user : users)
            if(user.getPhoneNumber().equals(phoneNumber))
                return user;
        return null;
    }
}
