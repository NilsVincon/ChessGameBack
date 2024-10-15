package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Auth.JwtService;
import com.epf.Chessgame.DTO.CheckFriendshipRequest;
import com.epf.Chessgame.Model.Friendship;
import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Model.User;
import com.epf.Chessgame.Service.FriendshipService;
import com.epf.Chessgame.Service.PlayService;
import com.epf.Chessgame.Service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/friend")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/getFriends")
    public  ResponseEntity<?> getFriends(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            User user_connected = jwtService.getUserfromJwt(authorizationHeader);
            List<User> friends = friendshipService.getFriends(user_connected);
            List<String> listfriendsUsername = friends.stream().map(User::getUsername).toList();
            return ResponseEntity.ok(listfriendsUsername);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des amis" + e.getMessage());
        }
    }

    @PostMapping("/add/{username_friend}")
    public ResponseEntity<String> AddPlay(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String username_friend) {
        try {
            User user_connected = jwtService.getUserfromJwt(authorizationHeader);
            User user_friend = userService.findUserByUsername(username_friend);
            Friendship friendship = new Friendship(user_connected, user_friend);
            log.info("Amitié créer " + friendship.getFriend1().getUsername() + " + " + friendship.getFriend2().getUsername());
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
