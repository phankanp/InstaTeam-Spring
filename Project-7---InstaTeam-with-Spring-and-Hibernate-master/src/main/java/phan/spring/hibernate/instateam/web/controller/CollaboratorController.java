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
import phan.spring.hibernate.instateam.model.Role;
import phan.spring.hibernate.instateam.service.CollaboratorService;
import phan.spring.hibernate.instateam.service.RoleService;
import phan.spring.hibernate.instateam.service.AssignedException;
import phan.spring.hibernate.instateam.web.FlashMessage;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;
    //---------------------------------------------------Add Collaborator-----------------------------------------------
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator", new Collaborator());
        }

        model.addAttribute("action", "/collaborators/add");
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("heading", "Manage Collaborators");
        model.addAttribute("roles", roles);
        model.addAttribute("submit", "Add");

        return "collaborator/collaborators";
    }

    @RequestMapping(value = "/collaborators/add", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes
            redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/collaborators";
        }

        collaboratorService.save(collaborator);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("New Collaborator Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }
    //---------------------------------------------------Edit Collaborator----------------------------------------------
    @RequestMapping("/collaborators/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Collaborator collaborator = collaboratorService.findById(id);
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("collaborator")) {
            model.addAttribute("collaborator", collaborator);
        }

        model.addAttribute("action", String.format("/collaborators/%s", id));
        model.addAttribute("roles", roles);
        model.addAttribute("submit", "Edit");

        return "collaborator/edit_collaborator";
    }

    @RequestMapping(value = "/collaborators/{id}", method = RequestMethod.POST)
    public String editCollaborator(@Valid Collaborator collaborator, BindingResult result, RedirectAttributes
            redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
            redirectAttributes.addFlashAttribute("collaborator", collaborator);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/collaborators";
        }

        collaboratorService.save(collaborator);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("New Collaborator Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }
    //---------------------------------------------------Delete Collaborator--------------------------------------------
    @RequestMapping(value = "/collaborators/{id}/delete")
    public String deleteCollaborator(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Collaborator collaborator = collaboratorService.findById(id);

        try {
            collaboratorService.delete(collaborator);
        } catch (AssignedException rae) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage(rae.getExceptionMessage(), FlashMessage
                    .Status.FAILURE));
            return "redirect:/collaborators";
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator deleted.", FlashMessage.Status
                .SUCCESS));

        return "redirect:/collaborators";

    }
}
