package com.example.test_login2.controller;


import com.example.test_login2.dto.AgendaDto;
import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.dto.PacienteDto;
import com.example.test_login2.entity.Agenda;
import com.example.test_login2.entity.Funcionario;
import com.example.test_login2.entity.Medico;
import com.example.test_login2.entity.Paciente;
import com.example.test_login2.repository.AgendaRepository;
import com.example.test_login2.repository.MedicoRepository;
import com.example.test_login2.repository.PacienteRepository;
import com.example.test_login2.service.FuncionarioService;
import com.example.test_login2.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class AuthController {

    private FuncionarioService funcionarioService;

    private PacienteService pacienteService;




    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
    

    public AuthController(FuncionarioService funcionarioService, MedicoRepository medicoRepository, PacienteService pacienteService, PacienteRepository pacienteRepository) {
        this.funcionarioService = funcionarioService;
        this.medicoRepository = medicoRepository;
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;

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

    @GetMapping("/add_paciente")
    public String showRegistrationFormPaciente(Model model){
        // create model object to store form data
        PacienteDto user = new PacienteDto();
        model.addAttribute("Paciente", user);
        List<Medico> users = medicoRepository.findAll();
        model.addAttribute("medicoList", users);

        return "add_paciente";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/add_paciente/save")
    public String registration(@Valid @ModelAttribute("Paciente") PacienteDto pacienteDto,
                               BindingResult result,
                               Model model){

        Paciente existingPaciente = pacienteService.findPacienteByName(pacienteDto.getName());

        if(existingPaciente != null && existingPaciente.getName() != null && !existingPaciente.getName().isEmpty()){
            result.rejectValue("name", null,
                    "There is already an account registered with the same name");
        }


        if(result.hasErrors()){
            model.addAttribute("name", pacienteDto);
            return "/add_paciente";
        }


        pacienteService.savePaciente(pacienteDto);

        return "redirect:/add_paciente?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<FuncionarioDto> users = funcionarioService.findAllUsers();
        model.addAttribute("Funcionario", users);
        int f = -1;
        model.addAttribute("delFunc", f);
        return "users";
    }

    //handler method to handle login request
    @GetMapping(value = {"/login","/"})
    public String login(){
        return "login";
    }

    @GetMapping("/agenda")
    public String agenda_new_week(Model model,HttpSession session, Authentication authentication){
        Integer offset = null;
        try {
             offset = (int) session.getAttribute("offset");
        }
       catch (Exception e){
             offset = 0;
        }


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Boolean is_med = false;
        Medico med = null;
        // Redirect the user based on their role
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MEDICO"))) {
            is_med = true;
            med = medicoRepository.findByName(authentication.getName()).get(0);
        }



        AgendaWeek week = new AgendaWeek();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Agenda> AgendaWeek = agendaRepository.findAgendaByDateBetween(java.sql.Date.valueOf(dateFormat.format(week.FirstWeekDay(offset))), java.sql.Date.valueOf(dateFormat.format(week.LastWeekDay(offset))));
        ArrayList<Date> DayList = new ArrayList<>();
        ArrayList<Time> TimeList = new ArrayList<>();
        ArrayList<String> CodeList = new ArrayList<>();
        for(Agenda a : AgendaWeek) {
            DayList.add(a.getDate());
            TimeList.add(a.getTime());
            CodeList.add("a" + week.DayWeek(a.getDate()) + String.valueOf(a.getTime().getHours()));
        }



        int i = 0;

        for (int day = 2; day <= 6; day++){
            for (int hour = 9; hour <= 17; hour++){
                String HTMLAtributeName = "a" + String.valueOf(day) + String.valueOf(hour);
                if (CodeList.contains(HTMLAtributeName)) {
                    if (is_med) {
                        if (AgendaWeek.get(i).getPaciente().getMedico().getId() == med.getId()){
                            model.addAttribute(HTMLAtributeName, AgendaWeek.get(i).getPaciente().getName());
                        }
                        else{
                            model.addAttribute(HTMLAtributeName, " ");
                        }

                    }
                    else {
                        model.addAttribute(HTMLAtributeName, AgendaWeek.get(i).getPaciente().getName());
                    }
                    i++;
                }
                else  {
                    model.addAttribute(HTMLAtributeName, " ");
                }
            }
        }

        model.addAttribute("header", java.sql.Date.valueOf(dateFormat.format(week.FirstWeekDay(offset))) + " - " +  java.sql.Date.valueOf(dateFormat.format(week.LastWeekDay(offset))));
        model.addAttribute("offset", offset);


        return "agenda";
    }



    @PostMapping("/agenda/update_offset")
    public String updateOffset(@RequestParam("new_offset") int offset, HttpSession session) {
        session.setAttribute("offset", offset);
        return "redirect:/agenda?sucess";
    }

    @GetMapping("/agenda/add_consulta")
    public String showRegistrationFormConsulta(Model model){
        // create model object to store form data
        AgendaDto user = new AgendaDto();
        model.addAttribute("Agenda", user);
        List<Paciente> users = pacienteRepository.findAll();
        model.addAttribute("pacienteList", users);


        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);



        return "add_consulta";
    }

    @PostMapping("/agenda/add_consulta/save")
    public String addConsulta(@Valid @ModelAttribute("Agenda") AgendaDto agendaDto,
                               BindingResult result,
                               Model model){

        Agenda agenda = new Agenda();
        agenda.setPaciente(agendaDto.getPaciente());
        agenda.setDate(agendaDto.getDate());
        agenda.setTime(Time.valueOf(agendaDto.getTime()));

        agendaRepository.save(agenda);

        return "redirect:/agenda/add_consulta?success";
    }

    @GetMapping("/users/delete_funcionario/{name}")
    public String deleteFuncionario(@PathVariable(value = "name") String name) {
        System.out.println("here");
        System.out.println(name);
        funcionarioService.deleteUser(name);

        return "redirect:/users?sucess";
    }

    @GetMapping("/paciente")
    public String pacientes(Model model){
        List<Paciente> users = pacienteRepository.findAll();
        model.addAttribute("Pacientes", users);

        return "paciente";
    }
    @GetMapping("/paciente/delete/{name}")
    public String deletePaciente(@PathVariable(value = "name") String name) {
        Paciente p = pacienteRepository.findByName(name);
        p.setMedico(null); // Remove the association between Paciente and Medico
        pacienteRepository.save(p);
        p = pacienteRepository.findByName(name);
        pacienteRepository.delete(p);


        return "redirect:/paciente?sucess";
    }

    @PostMapping("/paciente/save/{id}")
    public String savePaciente(@PathVariable("id") Long id, @RequestParam("observacoes") String observacoes) {
        System.out.println("here");
        System.out.println(observacoes);
        Paciente paciente = pacienteRepository.findByid(id);
        paciente.setObservacoes(observacoes);
        pacienteRepository.save(paciente);

        return "redirect:/paciente?success";
    }



}
