package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.board;

public class rook extends piece {


    public rook(colorPiece color, position position) {
        super(color, position);
    }


    @Override
    public String toString() {
        return getColor() == colorPiece.WHITE ? "T" : "t";
    }

    public boolean isValidMove(position newPosition, board chessboard) {

        if (position.getRow() == newPosition.getRow()) {
            int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
            int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());
            for (int column = columnStart; column < columnEnd; column++) {
                if (chessboard.getPieceAt(new position(position.getRow(), column)) != null) {
                    return false;
                }
            }
        } else if (position.getColumn() == newPosition.getColumn()) {
            int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
            int rowEnd = Math.max(position.getRow(), newPosition.getRow());
            for (int row = rowStart; row < rowEnd; row++) {
                if (chessboard.getPieceAt(new position(row, position.getColumn())) != null) {
                    return false;
                }
            }
        } else {
            return false; // Not a valid rook move (not straight line)
        }

        piece destinationPiece = chessboard.getPieceAt(newPosition);
        if (destinationPiece == null) {
            return true;
        } else if (destinationPiece.getColor() != this.getColor()) {
            return true;
        }

        return false;
    }


    }
