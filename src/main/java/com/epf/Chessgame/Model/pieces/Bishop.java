package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;

public class Bishop extends Piece {
    public Bishop(ColorPiece color, Position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == ColorPiece.WHITE ? "F" : "f";
    }

    public boolean isValidMove(Position newPosition, Board chessboard) {
        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());
        Piece destinationPiece = chessboard.getPieceAt(newPosition);

        int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
        int colStep = newPosition.getColumn() > position.getColumn() ? 1 : -1;
        int steps = rowDiff - 1;

        if (colDiff != rowDiff) {
            return false;
        }

        for (int i = 1; i <= steps; i++) {
            if (chessboard.getPieceAt(new Position(position.getRow() + i * rowStep, position.getColumn() + i * colStep)) != null) {
                return false;
            }
        }

        if (destinationPiece == null) {
            return true;
        } else if (destinationPiece.getColor() != this.getColor()) {
            return true;
        }

        return false;
    }
}
