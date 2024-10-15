package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtUtil;
import com.epf.Chessgame.DTO.AuthenticationRequest;
import com.epf.Chessgame.DTO.AuthenticationResponse;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        String storedPassword = userService.findUserByUsername(authRequest.getUsername()).getPassword();
        if (storedPassword != null && storedPassword.equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            log.info("Utilisateur connecté : " + authRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
