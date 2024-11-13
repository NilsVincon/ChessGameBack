package com.epf.Chessgame.DTO;

import com.epf.Chessgame.Model.Move;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveDTO {
    private Move move;
    private String activePlayer;

    @Override
    public String toString() {
        return "MoveDTO{" +
                "move=" + move +
                ", activePlayer='" + activePlayer + '\'' +
                '}';
    }
}
