package com.example.test_login2.service;

import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.entity.Funcionario;
import com.example.test_login2.entity.Role;
import com.example.test_login2.repository.RoleRepository;
import com.example.test_login2.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(FuncionarioDto funcionarioDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setName(funcionarioDto.getName());
        funcionario.setName(funcionarioDto.getName());
        // encrypt the password using spring security
        funcionario.setPassword(passwordEncoder.encode(funcionarioDto.getPassword()));

        Role role = roleRepository.findByName(funcionarioDto.getRole());
        if(role == null){
            role = checkRoleExist(funcionarioDto.getRole());
        }
        funcionario.setRoles(Arrays.asList(role));
        funcionarioRepository.save(funcionario);
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