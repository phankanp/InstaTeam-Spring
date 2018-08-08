package phan.spring.hibernate.instateam.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phan.spring.hibernate.instateam.model.Role;
import phan.spring.hibernate.instateam.service.RoleService;
import phan.spring.hibernate.instateam.service.AssignedException;
import phan.spring.hibernate.instateam.web.FlashMessage;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    //---------------------------------------------------Add Role-------------------------------------------------------
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();

        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new Role());
        }

        model.addAttribute("action", "/roles/add");
        model.addAttribute("roles", roles);
        model.addAttribute("heading", "Manage Roles");
        model.addAttribute("submit", "Add");

        return "role/roles";
    }

    @RequestMapping(value = "/roles/add", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
            redirectAttributes.addFlashAttribute("role", role);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/roles";
        }

        roleService.save(role);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("New Role Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }
    //---------------------------------------------------Edit Role------------------------------------------------------
    @RequestMapping("/roles/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Role roles = roleService.findById(id);


        if (!model.containsAttribute("role")) {
            model.addAttribute("role", roles);
        }

        model.addAttribute("action", String.format("/roles/%s", id));
        model.addAttribute("submit", "Edit");

        return "role/edit_role";
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.POST)
    public String editRoles(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
            redirectAttributes.addFlashAttribute("role", role);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid Input!",
                    FlashMessage.Status.FAILURE));
            return "redirect:/roles";
        }

        roleService.save(role);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("New Role Successfully Added!",
                FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }
    //---------------------------------------------------Delete Role----------------------------------------------------
    @RequestMapping(value = "/roles/{id}/delete")
    public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Role role = roleService.findById(id);

        try {
            roleService.delete(role);
        } catch (AssignedException rae) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage(rae.getExceptionMessage(), FlashMessage
                    .Status.FAILURE));
            return "redirect:/roles";
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Role deleted.", FlashMessage.Status
                .SUCCESS));

        return "redirect:/roles";

    }
}
