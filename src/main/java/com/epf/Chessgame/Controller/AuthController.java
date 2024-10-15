package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtUtil;
import com.epf.Chessgame.DTO.AuthenticationRequest;
import com.epf.Chessgame.DTO.AuthenticationResponse;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> SignUp(@RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Veuillez remplir tous les champs"));
            }
            if (userService.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Ce nom d'utilisateur est déjà pris"));
            }
            if (!userService.validPassword(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Votre mot de passe doit contenir au moins 8 caractères"));
            }
            userService.createUser(user);
            log.info("Utilisateur ajouté : " + user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Utilisateur ajouté avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Erreur lors de l'ajout de l'utilisateur: " + e.getMessage()));
        }
    }

}
