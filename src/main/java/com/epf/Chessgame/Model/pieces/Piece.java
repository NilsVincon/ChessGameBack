package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;

import java.util.Objects;


public abstract class Piece {
    protected ColorPiece color;
    protected Position position;

    public Piece(ColorPiece color, Position position) {
        this.color = color;
        this.position = position;
    }



    public ColorPiece getColor() {
        return color;
    }

    public void setColor(ColorPiece color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;
        return getColor() == piece.getColor() && Objects.equals(getPosition(), piece.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getPosition());
    }


    public abstract boolean isValidMove(Position newPosition, Board chessboard);
}
