package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@ModelAttribute User user) {
        log.info("Username : "+user.getUsername()+" Password : "+user.getPassword());
        try {
            if (userService.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce nom d'utilisateur est déjà pris");
            }
            if (!userService.validPassword(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Votre mot de passe doit contenir au moins 8 caractères");
            }
            userService.createUser(user);
            return ResponseEntity.ok("Utilisateur ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'utilisateur" + e.getMessage());
        }
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@ModelAttribute User user) {
        try {
            log.info("Username : "+user.getUsername()+" Password : "+user.getPassword());
            if (!userService.existsByUsername(user.getUsername())) {
                log.info("Cet utilisateur n'existe pas");
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cet utilisateur n'existe pas");
            }
            if (!user.getPassword().equals(userService.findUserByUsername(user.getUsername()).getPassword())) {
                log.info("Mot de passe incorrect");
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Mot de passe incorrect");
            }
            log.info("Connexion réussie");
            return ResponseEntity.ok("Connexion réussie");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la connexion" + e.getMessage());
        }
    }
}
