package com.example.test_login2.service;

import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.entity.*;
import com.example.test_login2.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    private MedicoRepository medicoRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private RececionistaRepository rececionistaRepository;

    private PacienteRepository pacienteRepository;

    private AgendaRepository agendaRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository,
                                  RoleRepository roleRepository,
                                  MedicoRepository medicoRepository,
                                  RececionistaRepository rececionistaRepository,
                                  PacienteRepository pacienteRepository,
                                  AgendaRepository agendaRepository,
                                  PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.roleRepository = roleRepository;
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
        this.rececionistaRepository = rececionistaRepository;
        this.pacienteRepository = pacienteRepository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public void saveUser(FuncionarioDto funcionarioDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setName(funcionarioDto.getName());
        funcionario.setPassword(passwordEncoder.encode(funcionarioDto.getPassword()));

        Role role = roleRepository.findByName(funcionarioDto.getRole());
        if (role == null) {
            role = checkRoleExist(funcionarioDto.getRole());
        }
        funcionario.setRoles(Arrays.asList(role));
        funcionarioRepository.save(funcionario);

        if (role.getName().equals("ROLE_MEDICO")) {
            Medico medico = new Medico();
            medico.setName(funcionario.getName());
            medico.setPassword(funcionario.getPassword());
            medico.setFuncionario(funcionario);
            medicoRepository.save(medico);
        }

        if (role.getName().equals("ROLE_RECECIONISTA")) {
            Rececionista rececionista = new Rececionista();
            rececionista.setName(funcionario.getName());
            rececionista.setPassword(funcionario.getPassword());
            rececionista.setFuncionario(funcionario);
            rececionistaRepository.save(rececionista);
        }
    }

    @Override
    public void deleteUser(String funcionarioDto) {
        Funcionario funcionario = funcionarioRepository.findByName(funcionarioDto);
        Role role= null;
        if (funcionario != null) {
          role= funcionario.getRoles().get(0); // Supondo que existe apenas uma role para cada funcionário
            System.out.println(role.getId());
            if (role.getName().equals("ROLE_MEDICO")) {
                role.getFuncionarios().remove(funcionario);
                Medico medico = medicoRepository.findByFuncionario(funcionario);

                if (medico != null) {
                    List<Paciente> p = pacienteRepository.findAllByMedico(medico);
                    for (int i = 0; i < p.size(); i++){
                        List<Agenda> a = agendaRepository.findAllByPaciente(p.get(i));
                        for (int j = 0; j < a.size(); j++){
                            agendaRepository.delete(a.get(j));
                        }
                        pacienteRepository.delete(p.get(i));
                    }
                    medicoRepository.delete(medico);
                }
            }

            if (role.getName().equals("ROLE_RECECIONISTA")) {
                role.getFuncionarios().remove(funcionario);
                Rececionista rececionista = rececionistaRepository.findByFuncionario(funcionario);
                if (rececionista != null) {
                    rececionistaRepository.delete(rececionista);
                }
            }



        }
    }
    @Override
    public Funcionario findUserByName(String name) {
        return funcionarioRepository.findByName(name);
    }

    @Override
    public List<FuncionarioDto> findAllUsers() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private FuncionarioDto mapToUserDto(Funcionario funcionario){
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setName(funcionario.getName());
        funcionarioDto.setRole(funcionario.getRoles().get(0).getName());
        return funcionarioDto;
    }

    private Role checkRoleExist(String s){
        Role role = new Role();
        switch (s){
            case "ROLE_MEDICO":
                role.setName("ROLE_MEDICO");
                return roleRepository.save(role);
            case "ROLE_RECECIONISTA":
                role.setName("ROLE_RECECIONISTA");
                return roleRepository.save(role);
            default:
                role.setName("ROLE_RECECIONISTA");
                return roleRepository.save(role);
        }

    }
}