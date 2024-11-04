package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@Slf4j
@RequestMapping("/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Move> index() {
        return moveService.getMoves();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> move(@RequestBody Move move) {
        Map<String, String> response = new HashMap<>();
        log.info("Move received: {}", move);
        try {
            moveService.createMove(move);
            response.put("message", "Movement joué avec succès");
        }
        catch (Exception e) {
            response.put("error", "Error while creating the move: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
