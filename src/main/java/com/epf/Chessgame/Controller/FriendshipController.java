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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> AddPlay(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String username_friend) {
        try {
            User user_connected = jwtService.getUserfromJwt(authorizationHeader);
            User user_friend = userService.findUserByUsername(username_friend);
            Friendship friendship = new Friendship(user_connected, user_friend);
            log.info("Amitié créer " + friendship.getFriend1().getUsername() + " + " + friendship.getFriend2().getUsername());
            friendshipService.createFriendship(friendship);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Amitié ajoutée avec succès");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'ajout de l'amitié: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);        }
    }

    @PostMapping("/checkFriendship")
    public ResponseEntity<Map<String, Object>> AreFriend(@RequestBody Friendship friendship) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean areTheyFriend = friendshipService.areFriends(friendship.getFriend1(), friendship.getFriend2());
            response.put("areFriends", areTheyFriend);
            response.put("message", "L'amitié a été vérifiée avec succès.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Erreur lors de la vérification de l'amitié: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> DeleteFriendship(@RequestBody Friendship friendship) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (friendshipService.areFriends(friendship.getFriend1(), friendship.getFriend2())) {
                Long friendshipId = friendshipService.findFriendshipByUser(friendship.getFriend1(), friendship.getFriend2()).getId();
                log.info("Id amitié: " + friendshipId);
                friendshipService.deleteFriendship(friendshipId);
                response.put("message", "Amitié supprimée avec succès");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Vous n'êtes pas amis");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Utilisation de BAD_REQUEST ici
            }
        } catch (Exception e) {
            response.put("error", "Erreur lors de la suppression de l'amitié: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
