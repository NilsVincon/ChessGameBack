package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;

public class Queen extends Piece {
    public Queen(ColorPiece color, Position position) {
        super(color, position);
    }

    @Override
    public String toString() {
        return getColor() == ColorPiece.WHITE ? "D" : "d";
    }

    public boolean isValidMove(Position newPosition, Board chessboard) {

        int rowDiff = Math.abs(newPosition.getRow() - position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());
        Piece destinationPiece = chessboard.getPieceAt(newPosition);

        int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
        int colStep = newPosition.getColumn() > position.getColumn() ? 1 : -1;
        int steps = rowDiff - 1;

        if(position.getRow() == newPosition.getRow() || position.getColumn() == newPosition.getColumn()) {

            if (position.getRow() == newPosition.getRow()) {
                int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
                int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());
                for (int column = columnStart; column < columnEnd; column++) {
                    if (chessboard.getPieceAt(new Position(position.getRow(), column)) != null) {
                        return false;
                    }
                }
            } else if (position.getColumn() == newPosition.getColumn()) {
                int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
                int rowEnd = Math.max(position.getRow(), newPosition.getRow());
                for (int row = rowStart; row < rowEnd; row++) {
                    if (chessboard.getPieceAt(new Position(row, position.getColumn())) != null) {
                        return false;
                    }
                }
            }
        }
        else if (colDiff == rowDiff) {
            for (int i = 1; i <= steps; i++) {
                if (chessboard.getPieceAt(new Position(position.getRow() + i * rowStep, position.getColumn() + i * colStep)) != null) {
                    return false;
                }
            }
        }


         else {
            return false;
        }

        if (destinationPiece == null) {
            return true;
        } else if (destinationPiece.getColor() != this.getColor()) {
            return true;
        }

        return false;


    }
}