package com.epf.Chessgame.Model.pieces;

import com.epf.Chessgame.Model.board;

public class knight extends piece {


    public knight(colorPiece color, position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == colorPiece.WHITE ? "C" : "c";
    }

    public boolean isValidMove(position newPosition, board chessboard) {

        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());
        piece destinationPiece = chessboard.getPieceAt(newPosition);

        if (colDiff == 1 && rowDiff == 2 || colDiff == 2 && rowDiff == 1) {
            if (destinationPiece == null) {
                return true;
            } else if (destinationPiece.getColor() != this.getColor()) {
                return true;
            }
        }


        return false;
    }

}
