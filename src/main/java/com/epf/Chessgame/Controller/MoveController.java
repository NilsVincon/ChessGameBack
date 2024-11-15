package com.epf.Chessgame.Controller;

import com.epf.Chessgame.DTO.MoveDTO;
import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Model.MoveResponse;
import com.epf.Chessgame.Model.pieces.ColorPiece;
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
            MoveResponse moveResponse=moveService.createMove(move);
            if (moveResponse.isCheckmate()){
                response.put("checkmate", "true");


            }else{
                response.put("checkmate", "false");
            }
            log.info("Move created: {}", move);
            response.put("message", "Mouvement joué avec succès");
        } catch (Exception e) {
            response.put("error", "Error while creating the move: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        log.info("Réponse envoyée");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/surrender")
    public Map<String, String> surrender(@RequestBody Map<String, String> request) {
        String player = request.get("player");
        moveService.CreateSurrender(    );
        Map<String, String> response = new HashMap<>();
        response.put("message", "Abandon réussi");
        return response;
    }

    @PostMapping("/draw")
    public Map<String, String> drawRequest(@RequestBody Map<String, Boolean> request) {

        Map<String, String> response = new HashMap<>();

            // Logique pour accepter la demande de nulle (par exemple, mettre fin à la partie)
            moveService.createDraw(); // Appelez le service pour gérer la nulle
            response.put("message", "La partie est déclarée nulle");


        return response;
    }



    @MessageMapping("/move/{gameId}")
    @SendTo("/topic/game-progress/{gameId}")
    public MoveResponse moveOnline(@DestinationVariable String gameId, @RequestBody MoveDTO moveDTO) {
        Move move = moveDTO.getMove();
        log.info("J'ai reçu : "+moveDTO);
        log.info("Move received for game {}: {}", gameId, move);
        MoveResponse moveResponse = null;
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
            moveResponse = moveService.createMove(savedMove);
            log.info("activePlayer reçu par le front"+moveDTO.getActivePlayer());
            String newactivePlayer = moveDTO.getActivePlayer().equals("white") ? "black" : "white";
            log.info("activePlayer envoyé au front"+newactivePlayer);
            moveResponse.setActivePlayer(newactivePlayer);

        } catch (Exception e) {
            log.error("Error while creating the move: {}", e.getMessage());
            throw new RuntimeException("Error while creating the move", e);
        }
        if (moveResponse.getMove() == null) {
            log.error("Error while creating the move: movetoFront is null");
            throw new RuntimeException("Error while creating the move: movetoFront is null");  // Gérer l'exception correctement
        }
        log.info("Move created: {}", moveResponse);
        return moveResponse;
    }

}
