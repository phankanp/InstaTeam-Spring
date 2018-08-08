package phan.spring.hibernate.instateam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phan.spring.hibernate.instateam.dao.RoleDao;
import phan.spring.hibernate.instateam.model.Collaborator;
import phan.spring.hibernate.instateam.model.Project;
import phan.spring.hibernate.instateam.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private ProjectService projectService;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Role role) {

        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Project> projects = projectService.findAll();

        for (Collaborator collaborator : collaborators) {
            if (collaborator.getRole().getId() == role.getId()) {
                throw new AssignedException("Cannot delete. Role is assigned to a collaborator.");
            }
        }

        for (Project project : projects) {
            if (project.getRolesNeeded().contains(role)) {
                throw new AssignedException("Cannot delete. Role is needed for a project.");
            }
        }


        roleDao.delete(role);
    }
}
