package com.example.test_login2.controller;

import com.example.test_login2.dto.UserDto;
import com.example.test_login2.entity.User;
import com.example.test_login2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;



    public AuthController(UserService userService) {
        this.userService = userService;
    }
    // handler method to handle home page request
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByName(userDto.getName());

        if(existingUser != null && existingUser.getName() != null && !existingUser.getName().isEmpty()){
            result.rejectValue("name", null,
                    "There is already an account registered with the same name");
        }

        if(result.hasErrors()){
            model.addAttribute("name", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    //handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}