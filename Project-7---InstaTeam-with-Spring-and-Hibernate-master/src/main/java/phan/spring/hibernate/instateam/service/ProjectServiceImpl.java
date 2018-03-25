package phan.spring.hibernate.instateam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phan.spring.hibernate.instateam.dao.ProjectDao;
import phan.spring.hibernate.instateam.model.Project;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectDao.findById(id);
    }

    @Override
    public void save(Project project) {
        projectDao.save(project);
    }

    @Override
    public void delete(Project project) {
        projectDao.delete(project);
    }
}
