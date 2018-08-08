package phan.spring.hibernate.instateam.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import phan.spring.hibernate.instateam.model.Collaborator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CollaboratorDaoImpl extends HibernateDao implements CollaboratorDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Collaborator> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Collaborator> criteria = builder.createQuery(Collaborator.class);

        // Specify criteria root
        criteria.from(Collaborator.class);

        // Execute query
        List<Collaborator> collaborators = session.createQuery(criteria).getResultList();

        // Close session
        session.close();

        return collaborators;
    }

    @Override
    public Collaborator findById(Long id) {
        Session session = sessionFactory.openSession();
        Collaborator collaborator = session.get(Collaborator.class, id);
        Hibernate.initialize(collaborator.getName());
        Hibernate.initialize(collaborator.getId());
        Hibernate.initialize(collaborator.getRole());
        session.close();
        return collaborator;
    }
}
