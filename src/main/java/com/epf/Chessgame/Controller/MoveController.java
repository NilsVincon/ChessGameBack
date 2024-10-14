package com.epf.Chessgame.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/move")
public class MoveController {

    @PostMapping
    public ResponseEntity<String> move() {
        return ResponseEntity.ok("ok");
    }
}
