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

/*    @PostMapping("/add")
    public ResponseEntity<String> AddGame(@RequestBody Game game) {
        try {
            gameService.createGame(game);
            return ResponseEntity.ok("Partie ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }
    }*/

    @PostMapping("/endGame")
    public ResponseEntity<String> endGame(@RequestBody WinnerRequest winnerRequest) {
        try {
            //TODO VERIFIER QUE LA PARTIE EST PAS DEJA TERMINE ET EST BIEN EN COURS, VERIFIER LE WINNER EST BIEN UN DES JOUEURS
            log.info("Winner : "+winnerRequest.getWinnerId()+" Game : "+winnerRequest.getGameId());
            User winner = userService.getUser(winnerRequest.getWinnerId());
            Game game = gameService.getGame(winnerRequest.getGameId());
            game.endGame(winner);
            gameService.updateGame(game);
            Play playToDelete =playService.findPlayByGameId(game.getId());
            playService.deletePlay(playToDelete.getId());
            return ResponseEntity.ok("Partie ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }
    }


}
