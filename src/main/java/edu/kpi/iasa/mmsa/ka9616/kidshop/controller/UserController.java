package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Roles;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("{user}")
    public String byId(@PathVariable User user, Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("{id}/roles")
    public String changeRoles(@PathVariable Long id,
                              @RequestParam(required = false) boolean isAdmin,
                              @RequestParam(required = false) boolean isUser){
        List<Roles> roles = new ArrayList<>();
        if(isAdmin)
            roles.add(Roles.ADMIN);
        if(isUser)
            roles.add(Roles.CUSTOMER);
        System.out.println(roles);
        userService.changeRoles(id);
        return "redirect:/users";
    }

    @GetMapping("/add/admin")
    public String addPage(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        return "add-admin";
    }

    @PostMapping("add/admin")
    public String addAdmin(@RequestParam(value = "login") String login,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "email") String email,
                           Model model) {

        if (userService.isUnique(login, email)) {
            userService.addAdmin(login, password, email);
            return "redirect:/users";
        }

        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("usernameMessage", "Login and email must be unique");
        return "add-admin";}

}
