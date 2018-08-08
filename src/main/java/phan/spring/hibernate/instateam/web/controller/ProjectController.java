package phan.spring.hibernate.instateam.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phan.spring.hibernate.instateam.model.Collaborator;
import phan.spring.hibernate.instateam.model.Project;
import phan.spring.hibernate.instateam.model.Role;
import phan.spring.hibernate.instateam.service.CollaboratorService;
import phan.spring.hibernate.instateam.service.ProjectService;
import phan.spring.hibernate.instateam.service.RoleService;
import phan.spring.hibernate.instateam.web.FlashMessage;
import phan.spring.hibernate.instateam.web.Status;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    @RequestMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);
        model.addAttribute("status", Status.values());

        return "project/index";

    }

    //---------------------------------------------------Project Details------------------------------------------------
    @RequestMapping("/project/{projectId}")
    public String projectDetail(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("heading", "  Roles & Collaborators");

        return "project/project_detail";
    }

    //---------------------------------------------------Add Project----------------------------------------------------
    @RequestMapping("projects/add")
    public String newProject(Model model) {
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }


        model.addAttribute("action", "/project/add");
        model.addAttribute("roles", roles);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("submit", "Add");

        return "project/edit_project";
    }

    @RequestMapping(value = "/project/add", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/projects/add";
        }

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return "redirect:/";
    }

    //---------------------------------------------------Edit Project---------------------------------------------------
    @RequestMapping("project/{projectId}/edit")
    public String editProject(@PathVariable Long projectId, Model model) {
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findById(projectId));
        }

        model.addAttribute("action", String.format("/project/%s", projectId));
        model.addAttribute("roles", roles);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("submit", "update");

        return "project/edit_project";
    }

    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.POST)
    public String editProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/projects/add";
        }

        projectService.save(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return String.format("redirect:/project/%s", project.getId());
    }

    //------------------------------------------------Edit Collaborators------------------------------------------------
    @RequestMapping("project/{projectId}/edit-collaborators")
    public String editCollaborators(@PathVariable Long projectId, Model model) {
        List<Collaborator> projectCollaborators = projectService.findById(projectId).getCollaborators();
        List<Collaborator> availableCollaborators = collaboratorService.findAll();
        List<Role> projectRoles = projectService.findById(projectId).getRolesNeeded();

        if (!model.containsAttribute("project")) {
            model.addAttribute("project", projectService.findById(projectId));
        }

        model.addAttribute("action", String.format("/project/collaborators/%s", projectId));
        model.addAttribute("roles", projectRoles);
        model.addAttribute("projectCollaborators", projectCollaborators);
        model.addAttribute("availableCollaborators", availableCollaborators);
        model.addAttribute("heading", "Edit Collaborators");
        model.addAttribute("save", "update");
        model.addAttribute("cancel", String.format("/project/%s", projectId));

        return "project/project_collaborators";
    }

    @RequestMapping(value = "/project/collaborators/{projectId}", method = RequestMethod.POST)
    public String updateCollaborators(@PathVariable Long projectId, Project project, BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        Project saveProject = projectService.findById(projectId);
        saveProject.setCollaborators(project.getCollaborators());
        projectService.save(saveProject);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborators Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return String.format("redirect:/project/%s", project.getId());
    }

    //---------------------------------------------------Delete Project-------------------------------------------------
    @RequestMapping(value = "/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Project project = projectService.findById(id);

        projectService.delete(project);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project deleted.", FlashMessage.Status
                .SUCCESS));

        return "redirect:/";

    }


}
