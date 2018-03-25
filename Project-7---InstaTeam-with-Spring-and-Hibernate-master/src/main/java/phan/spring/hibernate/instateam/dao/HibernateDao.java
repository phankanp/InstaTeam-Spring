package phan.spring.hibernate.instateam.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HibernateDao<T> {
    @Autowired
    SessionFactory sessionFactory;

    Class<T> classType;

    public void save(T t) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Save the category
        session.saveOrUpdate(t);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    public void delete(T t) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }
}

