package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Rook extends Piece {
    private boolean hasMoved = false;

    public Rook(ColorPiece color, Position position) {
        super(color, position);
    }

    public boolean hasMoved() {
        return hasMoved;
    }


    @Override
    public String toString() {
        return getColor() == ColorPiece.WHITE ? "T" : "t";
    }

    public boolean isValidMove(Position newPosition, Board chessboard) {

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
        } else {
            return false; // Not a valid rook move (not straight line)
        }

        Piece destinationPiece = chessboard.getPieceAt(newPosition);
        if (destinationPiece == null) {
            setHasMoved(true);
            return true;
        } else if (destinationPiece.getColor() != this.getColor()) {
            setHasMoved(true);
            return true;
        }

        return false;
    }


    }