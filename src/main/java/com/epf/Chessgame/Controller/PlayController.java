package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.Play;
import com.epf.Chessgame.Service.PlayService;
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

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<Play> index() {
        return playService.getPlays();
    }

    @PostMapping("/add")
    public ResponseEntity<String> AddPlay(@RequestBody Play play) {
        try {
            playService.createPlay(play);
            return ResponseEntity.ok("Partie ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la partie" + e.getMessage());
        }

    }

}
