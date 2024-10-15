package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ProtectedController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/protected")
    public ResponseEntity<String> getProtectedResource(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("Token : "+authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (jwtUtil.validateToken(token, username)) {
                return ResponseEntity.ok("This is a protected resource for user: " + username);
            }
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }
}