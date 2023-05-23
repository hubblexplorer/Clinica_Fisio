package com.example.test_login2.service;



import com.example.test_login2.dto.UserDto;
import com.example.test_login2.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByName(String name);

    List<UserDto> findAllUsers();
}