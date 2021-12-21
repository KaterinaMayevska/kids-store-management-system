package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;

import edu.kpi.iasa.mmsa.ka9616.kidshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Controller
@RequestMapping
public class AuthorizationController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String login() {
        return "redirect:/products";
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("isAuthenticated", false);
        model.addAttribute("isAdmin", false);
        return "registration";
    }
    @PostMapping("/register")
    public String register(@RequestParam(value = "login") String login,
                                           @RequestParam(value = "password") String password,
                                           @RequestParam(value = "email") String email,
                                           Model model) {

        if (userService.isUnique(login, email)) {
            userService.add(login, password, email);
            return "redirect:/login";
        }
        model.addAttribute("isAuthenticated", false);
        model.addAttribute("isAdmin", false);
        model.addAttribute("isUser", false);
        model.addAttribute("usernameMessage", "Login and email must be unique");
        return "registration";

    }
}
