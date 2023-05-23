package com.example.test_login2.service;



import com.example.test_login2.dto.FuncionarioDto;
import com.example.test_login2.entity.Funcionario;

import java.util.List;

public interface FuncionarioService {
    void saveUser(FuncionarioDto funcionarioDto);

    Funcionario findUserByName(String name);

    List<FuncionarioDto> findAllUsers();
}