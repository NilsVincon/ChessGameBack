package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.board;

public class pawn extends piece {

    public pawn(colorPiece color, position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == colorPiece.WHITE ? "P" : "p";
    }

    public boolean isValidMove(position newPosition, board chessboard) {
        int forwardDirection = color == colorPiece.WHITE ? -1 : 1;
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int colDiff = newPosition.getColumn() - position.getColumn();

        // Forward move
        if (colDiff == 0 && rowDiff == 1 && chessboard.getPieceAt(newPosition) == null) {
            return true; // Move forward one square
        }

        boolean isStartingPosition = (color == colorPiece.WHITE && position.getRow() == 6) ||
                (color == colorPiece.BLACK && position.getRow() == 1);
        if (colDiff == 0 && rowDiff == 2 && isStartingPosition
                && chessboard.getPieceAt(newPosition) == null) {
            // Check the square in between for blocking pieces
            int middleRow = position.getRow() + forwardDirection;
            if (chessboard.getPieceAt(new position(middleRow, getPosition().getColumn())) == null) {
                return true; // Move forward two squares
            }
        }
        if (Math.abs(colDiff) == 1 && rowDiff == 1 && chessboard.getPieceAt(newPosition) != null &&
                chessboard.getPieceAt(newPosition).color != this.color) {
            return true; // Capture an opponent's piece
        }

        return false;
    }


    }



