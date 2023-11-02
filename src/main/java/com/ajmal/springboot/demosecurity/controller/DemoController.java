package com.ajmal.springboot.demosecurity.controller;

import com.ajmal.springboot.demosecurity.entity.User;
import com.ajmal.springboot.demosecurity.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DemoController {

    private UserService userService;

    @Autowired
    public DemoController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome(User theUser, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        theUser = userService.existsByUserName(currentPrincipalName);

        session.setAttribute("user", theUser);
        return "home";
    }

    // add request mapping for /systems

    @GetMapping("/systems")
    public String showSystems(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "systems";
    }

    @GetMapping("/search")
    public String getSearchResults(Model model, String keyword) {
        User user = userService.existsByUserName(keyword);
        model.addAttribute("user", user);
        return "search";
    }
}









