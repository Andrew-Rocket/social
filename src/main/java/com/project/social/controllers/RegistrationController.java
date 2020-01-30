package com.project.social.controllers;

import com.project.social.domain.User;
import com.project.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService = new UserService();

    @GetMapping("/registration")
    public String registration() {

        return "registration";
    }

    @PostMapping("registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        if(user.getPassword()!=null&&!user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }


    @GetMapping("/activation/{code}")
    public String activation(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        String message;

        if(isActivated) {
            message = "User successfully activated!";
        } else {
            message = "Activation code is not found!";
        }
            model.addAttribute("message", message);

        return "login";
    }


}
