package com.quickbana.quickbana.service;

import com.quickbana.quickbana.dto.LoginRequestDTO;
import com.quickbana.quickbana.dto.LoginResponseDTO;
import com.quickbana.quickbana.dto.UserDTO;
import com.quickbana.quickbana.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(User user);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    LoginResponseDTO loginUser(LoginRequestDTO loginRequest);
}
