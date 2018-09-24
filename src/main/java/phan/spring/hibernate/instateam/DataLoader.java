package phan.spring.hibernate.instateam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import phan.spring.hibernate.instateam.dao.CollaboratorDao;
import phan.spring.hibernate.instateam.dao.ProjectDao;
import phan.spring.hibernate.instateam.dao.RoleDao;
import phan.spring.hibernate.instateam.model.Collaborator;
import phan.spring.hibernate.instateam.model.Project;
import phan.spring.hibernate.instateam.model.Role;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    ProjectDao projectDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    CollaboratorDao collaboratorDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Date date = Calendar.getInstance().getTime();

        Role role = new Role();
        role.setName("Software Engineer");
        roleDao.save(role);

        Collaborator collaborator = new Collaborator();
        collaborator.setName("Fred Zal");
        collaborator.setRole(roleDao.findById((long) 1));
        collaboratorDao.save(collaborator);

        Project project = new Project();
        project.setName("Test Project");
        project.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque ut nulla ac" +
                " rhoncus. Nulla facilisi. In iaculis sem arcu, vel ornare odio consectetur eget. Vivamus facilisis felis" +
                " non sem maximus, ut dapibus risus pretium. Duis nec velit ante. Nulla id risus nec quam sollicitudin tincidunt. ");
        project.setStatus("Active");
        project.setStartDate(date);
        List<Collaborator> collaborators = new ArrayList<>();
        collaborators.add(collaborator);

        project.addRole(roleDao.findById((long)1));
        project.addCollaborator(collaboratorDao.findById((long)1));

        projectDao.save(project);



    }
}
