package com.epf.Chessgame.Controller;

import com.epf.Chessgame.Model.Move;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/move")
    @SendTo("/topic/{gameId}")
    public Move movePiece(@Payload Move move) {
        return move;
    }
}