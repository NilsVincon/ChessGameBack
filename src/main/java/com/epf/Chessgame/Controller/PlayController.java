package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Enum.GameStatus;
import com.epf.Chessgame.Enum.InvitationStatus;
import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.GameService;
import com.epf.Chessgame.Service.PlayService;
import com.epf.Chessgame.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/play")
public class PlayController {

    @Autowired
    private PlayService playService;

    @Autowired
    private GameService gameService;


    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Play> index() {
        return playService.getPlays();
    }

    @PostMapping("/invite/{idUserConnected}")
    public ResponseEntity<String> Invite(@RequestBody User user, @PathVariable Long idUserConnected) {
        try {
            User userConnected = userService.getUser(idUserConnected);
            Game game = new Game();
            gameService.createGame(game);
            Play play = new Play(game,userConnected,userService.getUser(user.getId()));
            playService.createPlay(play);
            return ResponseEntity.ok("Invitation envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'invitation" + e.getMessage());
        }

    }

    @PostMapping("/accept/{idUserConnected}")
    public ResponseEntity<String> Accept(@RequestBody Play play,@PathVariable Long idUserConnected) {
        try {
            if(userService.getUser(idUserConnected).equals(playService.getPlay(play.getId()).getReceiver())){
                Play currentPlay = playService.getPlay(play.getId());
                currentPlay.setStatus(InvitationStatus.ACCEPTEE);
                currentPlay.getGame().startGame();
                playService.updatePlay(currentPlay);
                return ResponseEntity.ok("Invitation accepté avec succès");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'êtes pas le destinataire de cette invitation");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'invitation" + e.getMessage());
        }
    }

    @PostMapping("/refuse/{idUserConnected}")
    public ResponseEntity<String> Refuse(@RequestBody Play play,@PathVariable Long idUserConnected) {
        try {
            if(userService.getUser(idUserConnected).equals(playService.getPlay(play.getId()).getReceiver())){
                Play currentPlay = playService.getPlay(play.getId());
                currentPlay.setStatus(InvitationStatus.REFUSEE);
                playService.updatePlay(currentPlay);
                gameService.deleteGame(currentPlay.getGame().getId());
                return ResponseEntity.ok("Invitation refusé avec succès");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'êtes pas le destinataire de cette invitation");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'invitation" + e.getMessage());
        }
    }


}
