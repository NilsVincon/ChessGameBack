package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Service.GameService;
import com.epf.Chessgame.Service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @Autowired
    private GameService gameService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Move> index() {
        return moveService.getMoves();
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> move(@RequestBody Move move) {
        Map<String, String> response = new HashMap<>();
        log.info("Move received: {}", move);
        try {
            moveService.createMove(move);
            log.info("Move created: {}", move);
            response.put("message", "Movement joué avec succès");
        } catch (Exception e) {
            response.put("error", "Error while creating the move: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        log.info("Réponse envoyée");
        return ResponseEntity.ok(response);
    }

    @MessageMapping("/move/{gameId}")
    @SendTo("/topic/game-progress/{gameId}")
    public Move moveOnline(@DestinationVariable String gameId, @RequestBody Move move) {
        log.info("Move received for game {}: {}", gameId, move);
        Move movetoFront = null;
        try {
            Long gameLongId;
            try {
                gameLongId = Long.parseLong(gameId);
            } catch (NumberFormatException e) {
                log.error("Invalid gameId format: {}", gameId);
                throw new IllegalArgumentException("Invalid gameId format: " + gameId);
            }
            Game game = gameService.getGame(gameLongId);

            if (game == null) {
                log.error("Game not found for gameId: {}", gameId);
                throw new IllegalArgumentException("Game not found for gameId: " + gameId);
            }
            log.info("Game found: {}", game);
            Move savedMove = new Move(move.getInitialPosition(), move.getFinalPosition(), game);  // Associer le jeu à ce mouvement
            movetoFront = moveService.createMove(savedMove);  // Sauvegarder le mouvement
        } catch (Exception e) {
            log.error("Error while creating the move: {}", e.getMessage());
            throw new RuntimeException("Error while creating the move", e);  // Gérer l'exception correctement
        }
        if (movetoFront == null) {
            log.error("Error while creating the move: movetoFront is null");
            throw new RuntimeException("Error while creating the move: movetoFront is null");  // Gérer l'exception correctement
        }
        log.info("Move created: {}", movetoFront);
        return movetoFront;  // Renvoie le mouvement aux abonnés
    }

}
