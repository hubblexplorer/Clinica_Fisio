package com.example.test_login2.controller;


import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.entity.Agenda;
import com.example.test_login2.entity.Funcionario;
import com.example.test_login2.repository.AgendaRepository;
import com.example.test_login2.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AuthController {

    private FuncionarioService funcionarioService;

    @Autowired
    private AgendaRepository agendaRepository;

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
    
    @GetMapping("/agenda")
    public String agenda(Model model){
        AgendaWeek week = new AgendaWeek();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Agenda> AgendaWeek = agendaRepository.findAgendaByDateBetween(java.sql.Date.valueOf(dateFormat.format(week.FirstWeekDay())), java.sql.Date.valueOf(dateFormat.format(week.LastWeekDay())));
        System.out.println(AgendaWeek);
        ArrayList<Date> DayList = new ArrayList<>();
        ArrayList<Time> TimeList = new ArrayList<>();
        ArrayList<String> CodeList = new ArrayList<>();
        for(Agenda a : AgendaWeek) {
            DayList.add(a.getDate());
            TimeList.add(a.getTime());
            CodeList.add("a" + week.DayWeek(a.getDate()) + String.valueOf(a.getTime().getHours()));
        }

        for (int day = 2; day <= 6; day++){
            for (int hour = 9; hour <= 17; hour++){
                String HTMLAtributeName = "a" + String.valueOf(day) + String.valueOf(hour);
                if (CodeList.contains(HTMLAtributeName))    model.addAttribute(HTMLAtributeName, "X");
                else                                        model.addAttribute(HTMLAtributeName, " ");
            }
        }

        System.out.println(DayList);
        System.out.println(TimeList);
        System.out.println(CodeList);

        return "agenda";
    }
}
