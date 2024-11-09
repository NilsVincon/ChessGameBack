package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtService;
import com.epf.Chessgame.Enum.GameStatus;
import com.epf.Chessgame.Enum.InvitationStatus;
import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.GameService;
import com.epf.Chessgame.Service.PlayService;
import com.epf.Chessgame.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/play")
public class PlayController {

    @Autowired
    private PlayService playService;

    @Autowired
    private GameService gameService;


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Play> index() {
        return playService.getPlays();
    }

    @PostMapping("/invite")
    public ResponseEntity<Map<String,String>> Invite(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String username) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            Game game = new Game();
            gameService.createGame(game);
            Play play = new Play(game,userConnected,userService.findUserByUsername(username));
            playService.createPlay(play);
            Map<String,String> response = Map.of("message","Invitation envoyée avec succès");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String,String> response = Map.of("message","Erreur lors de l'envoi de l'invitation" + e.getMessage());
            return ResponseEntity.ok(response);
        }

    }

    @PostMapping("/accept")
    public ResponseEntity<String> Accept(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Play play) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            if(userConnected.equals(playService.getPlay(play.getId()).getReceiver())){
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

    @PostMapping("/refuse")
    public ResponseEntity<String> Refuse(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Play play) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            if(userConnected.equals(playService.getPlay(play.getId()).getReceiver())){
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
