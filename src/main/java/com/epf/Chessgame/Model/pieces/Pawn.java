package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;

public class Pawn extends Piece {

    public Pawn(ColorPiece color, Position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == ColorPiece.WHITE ? "P" : "p";
    }

    public boolean isValidMove(Position newPosition, Board chessboard) {
        int forwardDirection = color == ColorPiece.WHITE ? -1 : 1;
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int colDiff = newPosition.getColumn() - position.getColumn();

        // Forward move
        if (colDiff == 0 && rowDiff == 1 && chessboard.getPieceAt(newPosition) == null) {
            return true; // Move forward one square
        }

        boolean isStartingPosition = (color == ColorPiece.WHITE && position.getRow() == 6) ||
                (color == ColorPiece.BLACK && position.getRow() == 1);
        if (colDiff == 0 && rowDiff == 2 && isStartingPosition
                && chessboard.getPieceAt(newPosition) == null) {
            // Check the square in between for blocking pieces
            int middleRow = position.getRow() + forwardDirection;
            if (chessboard.getPieceAt(new Position(middleRow, getPosition().getColumn())) == null) {
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



