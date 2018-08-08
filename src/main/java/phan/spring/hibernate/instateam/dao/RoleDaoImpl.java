package phan.spring.hibernate.instateam.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import phan.spring.hibernate.instateam.model.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class RoleDaoImpl extends HibernateDao implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Role> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);

        // Specify criteria root
        criteria.from(Role.class);

        // Execute query
        List<Role> roles = session.createQuery(criteria).getResultList();

        for (Role role : roles) {
            Hibernate.initialize(role.getCollaborators());
            Hibernate.initialize(role.getName());
            Hibernate.initialize(role.getId());
        }

        // Close session
        session.close();

        return roles;
    }

    @Override
    public Role findById(Long id) {
        Session session = sessionFactory.openSession();
        Role role = session.get(Role.class, id);
        Hibernate.initialize(role.getCollaborators());
        Hibernate.initialize(role.getName());
        Hibernate.initialize(role.getId());
        session.close();
        return role;
    }
}
