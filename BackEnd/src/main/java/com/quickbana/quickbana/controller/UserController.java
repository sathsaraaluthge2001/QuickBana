package com.quickbana.quickbana.controller;

import com.quickbana.quickbana.dto.LoginRequestDTO;
import com.quickbana.quickbana.dto.LoginResponseDTO;
import com.quickbana.quickbana.dto.UserDTO;
import com.quickbana.quickbana.entity.User;
import com.quickbana.quickbana.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private static final Logger auditLogger = LogManager.getLogger("AUDIT_LOG");
    private static final Logger advanceLogger = LogManager.getLogger("ADVANCE_LOG");
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        auditLogger.info("user create end point trigger onject is: "+user);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        auditLogger.info("get all users end point trigger");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        auditLogger.info("get  user by id  end point trigger id is: "+id);
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        auditLogger.info("User login request: " + loginRequest);
        LoginResponseDTO response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
