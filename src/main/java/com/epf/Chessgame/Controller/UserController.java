package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<User> index() {
        return userService.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<String> AddUser(@RequestBody User user) {
        log.info("Username : "+user.getUsername()+" Password : "+user.getPassword());
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Utilisateur ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'utilisateur" + e.getMessage());
        }

    }
}
