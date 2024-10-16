package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> move(@RequestBody Move move) {
        log.info("Move received: {}", move);

        // Assurez-vous que votre service a cette méthode
        moveService.createMove(move);

        // Préparez la réponse JSON
        return ResponseEntity.ok().body(new ResponseMessage("Move successfully processed"));
    }

    // Classe interne pour la réponse JSON
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
