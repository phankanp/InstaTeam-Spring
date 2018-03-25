package phan.spring.hibernate.instateam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phan.spring.hibernate.instateam.dao.CollaboratorDao;
import phan.spring.hibernate.instateam.model.Collaborator;
import phan.spring.hibernate.instateam.model.Project;

import java.util.List;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {
    @Autowired
    private CollaboratorDao collaboratorDao;

    @Autowired
    private ProjectService projectService;


    @Override
    public List<Collaborator> findAll() {
        return collaboratorDao.findAll();
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorDao.findById(id);
    }

    @Override
    public void save(Collaborator collaborator) {
        collaboratorDao.save(collaborator);
    }

    @Override
    public void delete(Collaborator collaborator) {
        List<Project> projects = projectService.findAll();

        for (Project project : projects) {
            if (project.getCollaborators().contains(collaborator)) {
                throw new AssignedException("Cannot delete. Collaborator is assigned to a project.");
            }
        }


        collaboratorDao.delete(collaborator);
    }
}
