package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;

public class King extends Piece {
    public King(ColorPiece color, Position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == ColorPiece.WHITE ? "R" : "r";
    }
    public boolean isValidMove(Position newPosition, Board chessboard) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());
        Piece destinationPiece = chessboard.getPieceAt(newPosition);

        if (colDiff <=1 && rowDiff<=1) {
            if (destinationPiece == null) {
                return true;
            } else if (destinationPiece.getColor() != this.getColor()) {
                return true;
            }
        }

        return false;
    }
    }

