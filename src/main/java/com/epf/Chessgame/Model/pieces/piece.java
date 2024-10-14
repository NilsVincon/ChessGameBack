package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.board;

import java.util.Objects;


public abstract class piece {

    protected colorPiece color;
    protected position position;

    public piece(colorPiece color, com.epf.Chessgame.Model.pieces.position position) {
        this.color = color;
        this.position = position;
    }



    public colorPiece getColor() {
        return color;
    }

    public void setColor(colorPiece color) {
        this.color = color;
    }

    public com.epf.Chessgame.Model.pieces.position getPosition() {
        return position;
    }

    public void setPosition(com.epf.Chessgame.Model.pieces.position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof piece piece)) return false;
        return getColor() == piece.getColor() && Objects.equals(getPosition(), piece.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getPosition());
    }


    public abstract boolean isValidMove(position newPosition, board chessboard);
}
