package com.epf.Chessgame.Auth;

import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    public User getUserfromJwt(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            if (jwtUtil.validateToken(token, username)) {
                log.info("User connected : " + username);
                return userService.findUserByUsername(username);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not connected");
    }
}
