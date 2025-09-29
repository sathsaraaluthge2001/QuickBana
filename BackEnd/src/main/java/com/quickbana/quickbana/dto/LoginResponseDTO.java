package com.quickbana.quickbana.dto;

import com.quickbana.quickbana.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private UserDTO userDTO;
    private String token; // Add JWT token field
}
