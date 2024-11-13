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

    @GetMapping("/getMyInvitations")
    @ResponseBody
    public Iterable<Play> getPlays(@RequestHeader("Authorization") String authorizationHeader) {
        User userConnected = jwtService.getUserfromJwt(authorizationHeader);
        log.info("Liste des invitations de :"+userConnected.getUsername()+" : "+ playService.getPlaysByReceiver(userConnected.getId()));
        return playService.getPlaysByReceiver(userConnected.getId());
    }

    @PostMapping("/invite")
    public ResponseEntity<Map<String,String>> Invite(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String username) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            Game game = new Game();
            Game gameBack = gameService.createGame(game);
            String colorSender = Math.random() < 0.5 ? "white" : "black";
            String colorReceiver = colorSender.equals("white") ? "black" : "white";
            Play play = new Play(game,userConnected,userService.findUserByUsername(username),colorSender,colorReceiver);
            playService.createPlay(play);
            Map<String,String> response = Map.of("message","Invitation envoyée avec succès",
                    "gameId",gameBack.getId().toString(),"colorSender",colorSender,"colorReceiver",colorReceiver,"senderUsername",userConnected.getUsername(),"receiverUsername",username);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String,String> response = Map.of("message","Erreur lors de l'envoi de l'invitation" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<Map<String,String>> Accept(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Long playId) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            if(userConnected.equals(playService.getPlay(playId).getReceiver())){
                Play currentPlay = playService.getPlay(playId);
                currentPlay.setStatus(InvitationStatus.ACCEPTEE);
                currentPlay.getGame().startGame();
                String gameId= currentPlay.getGame().getId().toString();
                playService.updatePlay(currentPlay);
                Map<String, String> response = Map.of(
                        "message", "Invitation acceptée avec succès",
                        "gameId", gameId,"colorSender",currentPlay.getSendercolor(),"colorReceiver",currentPlay.getReceivercolor(),"senderUsername",currentPlay.getSender().getUsername(),"receiverUsername",currentPlay.getReceiver().getUsername()
                );
                return ResponseEntity.ok(response);
            }
            else {
                Map<String,String> response = Map.of("message","Vous n'êtes pas le destinataire de cette invitation");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            Map<String,String> response = Map.of("message","Erreur lors de l'acceptation de l'invitation"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/refuse")
    public ResponseEntity<Map<String,String>> Refuse(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Long playId) {
        try {
            User userConnected = jwtService.getUserfromJwt(authorizationHeader);
            if(userConnected.equals(playService.getPlay(playId).getReceiver())){
                Play currentPlay = playService.getPlay(playId);
                currentPlay.setStatus(InvitationStatus.REFUSEE);
                playService.updatePlay(currentPlay);
                Game game = currentPlay.getGame();
                game.setStatus(GameStatus.ANNULEE);
                gameService.updateGame(game);
                Map<String,String> response = Map.of("message","Invitation refusé avec succès");
                return ResponseEntity.ok(response);
            }
            else {
                Map<String,String> response = Map.of("message","Vous n'êtes pas le destinataire de cette invitation");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

            }
        } catch (Exception e) {
            Map<String,String> response = Map.of("message","Erreur lors du refus de l'invitation"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
