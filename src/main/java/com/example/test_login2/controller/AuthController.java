package com.example.test_login2.controller;

import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.entity.Funcionario;
import com.example.test_login2.service.FuncionarioService;
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

    private FuncionarioService funcionarioService;

    public AuthController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }
    // handler method to handle home page request
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        FuncionarioDto user = new FuncionarioDto();
        model.addAttribute("Funcionario", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("Funcionario") FuncionarioDto funcionarioDto,
                               BindingResult result,
                               Model model){
        Funcionario existingFuncionario = funcionarioService.findUserByName(funcionarioDto.getName());

        if(existingFuncionario != null && existingFuncionario.getName() != null && !existingFuncionario.getName().isEmpty()){
            result.rejectValue("name", null,
                    "There is already an account registered with the same name");
        }

        if(result.hasErrors()){
            model.addAttribute("name", funcionarioDto);
            return "/register";
        }

        funcionarioService.saveUser(funcionarioDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<FuncionarioDto> users = funcionarioService.findAllUsers();
        model.addAttribute("Funcionario", users);
        return "users";
    }

    //handler method to handle login request
    @GetMapping(value = {"/login","/"})
    public String login(){
        return "login";
    }
}