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

}
