package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtService;
import com.epf.Chessgame.DTO.CheckFriendshipRequest;
import com.epf.Chessgame.Model.Friendship;
import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.FriendshipService;
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
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Friendship> index() {
        return friendshipService.getFriendships();
    }

    @PostMapping("/add/{username_friend}")
    public ResponseEntity<String> AddPlay(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String username_friend) {
        try {
            User user_connected = jwtService.getUserfromJwt(authorizationHeader);
            User user_friend = userService.findUserByUsername(username_friend);
            Friendship friendship = new Friendship(user_connected, user_friend);
            friendshipService.createFriendship(friendship);
            return ResponseEntity.ok("Partie ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }
    }

    @PostMapping("/checkFriendship")
    public ResponseEntity<String> AreFriend(@RequestBody Friendship friendship) {
        try {
            boolean areTheyFriend = friendshipService.areFriends(friendship.getFriend1(), friendship.getFriend2());
            return ResponseEntity.ok("L'amitié est : " + areTheyFriend);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> DeleteFriendship(@RequestBody Friendship friendship) {
        try {
            if (friendshipService.areFriends(friendship.getFriend1(), friendship.getFriend2())) {
                log.info("Id amitié" + friendshipService.findFriendshipByUser(friendship.getFriend1(), friendship.getFriend2()));
                friendshipService.deleteFriendship(friendshipService.findFriendshipByUser(friendship.getFriend1(), friendship.getFriend2()).getId());
                return ResponseEntity.ok("Amitié supprimée avec succès");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Vous n'êtes pas amis");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de l'amitié " + e.getMessage());
        }
    }

}
