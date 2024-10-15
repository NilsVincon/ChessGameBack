package com.epf.Chessgame.Model.pieces;


import com.epf.Chessgame.Model.Board;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class King extends Piece {


    private boolean hasMoved = false;


    public King(ColorPiece color, Position position) {
        super(color, position);
    }

    public boolean hasMoved() {
        return hasMoved;
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
                setHasMoved(true);
                return true;
            } else if (destinationPiece.getColor() != this.getColor()) {
                setHasMoved(true);
                return true;
            }
        }

        return false;
    }
    }

