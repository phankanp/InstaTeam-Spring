package phan.spring.hibernate.instateam.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import phan.spring.hibernate.instateam.model.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ProjectDaoImpl extends HibernateDao implements ProjectDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Project> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);

        // Specify criteria root
        criteria.from(Project.class);

        // Execute query
        List<Project> projects = session.createQuery(criteria).getResultList();

        for (Project project : projects) {
            Hibernate.initialize(project.getCollaborators());
            Hibernate.initialize(project.getRolesNeeded());
            Hibernate.initialize(project.getStartDate());
        }

        // Close session
        session.close();

        return projects;
    }

    @Override
    public Project findById(Long id) {
        Session session = sessionFactory.openSession();
        Project project = session.get(Project.class, id);
        Hibernate.initialize(project.getName());
        Hibernate.initialize(project.getCollaborators());
        Hibernate.initialize(project.getRolesNeeded());
        session.close();
        return project;
    }
}
