package com.epf.Chessgame.Controller;

import com.epf.Chessgame.DTO.WinnerRequest;
import com.epf.Chessgame.Enum.GameStatus;
import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.GameService;
import com.epf.Chessgame.Service.PlayService;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayService playService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Game> index() {
        return gameService.getGames();
    }
    @PostMapping("/endGame")
    public ResponseEntity<String> endGame(@RequestBody WinnerRequest winnerRequest) {
        try {
            User winner = userService.getUser(winnerRequest.getWinnerId());
            Game game = gameService.getGame(winnerRequest.getGameId());
            Play play = playService.findPlayByGameId(game.getId());
            if (game.getStatus().equals(GameStatus.TERMINEE) || game.getStatus().equals(GameStatus.A_VENIR)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("La partie n'est pas en cours");
            }
            if (play.getReceiver().equals(winner) || play.getSender().equals(winner)) {
                game.endGame(winner);
                gameService.updateGame(game);
                playService.deletePlay(play.getId());
                return ResponseEntity.ok("Partie terminée avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Le gagnant n'est pas un des joueurs de la partie");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }
    }
}
