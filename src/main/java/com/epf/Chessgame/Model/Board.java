package com.epf.Chessgame.Model;

import com.epf.Chessgame.Model.pieces.*;
import org.springframework.stereotype.Component;

@Component
public class Board {
    private Piece[][] chessboard;

    public Board() {
        this.chessboard = new Piece[8][8];
        initBoard();
    }

    public Piece[][] getChessBoard() {
        return chessboard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                if (chessboard[ligne][colonne] != null) {
                    sb.append(chessboard[ligne][colonne].toString());
                } else {
                    sb.append(".");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Position findKingPosition(ColorPiece kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chessboard[row][col];
                if (p instanceof King && p.getColor() == kingColor) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public void setPiece(int row, int column, Piece p) {
        chessboard[row][column] = p;
    }

    private void initBoard() {

        chessboard[0][0] = new Rook(ColorPiece.BLACK, new Position(0,0));
        chessboard[0][7] = new Rook(ColorPiece.BLACK, new Position(0, 7));
        chessboard[7][0] = new Rook(ColorPiece.WHITE, new Position(7, 0));
        chessboard[7][7] = new Rook(ColorPiece.WHITE, new Position(7, 7));

        chessboard[0][1] = new Knight(ColorPiece.BLACK, new Position(0, 1));
        chessboard[0][6] = new Knight(ColorPiece.BLACK, new Position(0, 6));
        chessboard[7][1] = new Knight(ColorPiece.WHITE, new Position(7, 1));
        chessboard[7][6] = new Knight(ColorPiece.WHITE, new Position(7, 6));

        chessboard[0][2] = new Bishop(ColorPiece.BLACK, new Position(0, 2));
        chessboard[0][5] = new Bishop(ColorPiece.BLACK, new Position(0, 5));
        chessboard[7][2] = new Bishop(ColorPiece.WHITE, new Position(7, 2));
        chessboard[7][5] = new Bishop(ColorPiece.WHITE, new Position(7, 5));

        chessboard[0][3] = new Queen(ColorPiece.BLACK, new Position(0, 3));
        chessboard[7][3] = new Queen(ColorPiece.WHITE, new Position(7, 3));

        chessboard[0][4] = new King(ColorPiece.BLACK, new Position(0, 4));
        chessboard[7][4] = new King(ColorPiece.WHITE, new Position(7, 4));

        for (int i = 0; i < 8; i++) {
            chessboard[1][i] = new Pawn(ColorPiece.BLACK, new Position(1, i));
            chessboard[6][i] = new Pawn(ColorPiece.WHITE, new Position(6, i));
        }

    }

    public void movePieces(Position start, Position end) {
        Piece movingPiece = getPieceAt(start);


        if (movingPiece != null) {
            if (movingPiece instanceof King && Math.abs(start.getColumn() - end.getColumn()) == 2) {
                Position rookStart = (end.getColumn() > start.getColumn()) ?
                        new Position(start.getRow(), 7) :
                        new Position(start.getRow(), 0);
                castle(start, rookStart);
            } else if (chessboard[start.getRow()][start.getColumn()] != null &&
                    chessboard[start.getRow()][start.getColumn()].isValidMove(end, this)) {

                chessboard[end.getRow()][end.getColumn()] = chessboard[start.getRow()][start.getColumn()];

                chessboard[end.getRow()][end.getColumn()].setPosition(end);

                chessboard[start.getRow()][start.getColumn()] = null;
            }
        }
    }
   public Piece getPieceAt(Position pos){
       return chessboard[pos.getRow()][pos.getColumn()];
   }
    public Piece getPieceAt(int row, int column){
        return chessboard[row][column];
    }

    public boolean isInCheck(ColorPiece kingColor) {
        Position kingPosition = findKingPosition(kingColor);
        for (int row = 0; row < chessboard.length; row++) {
            for (int col = 0; col < chessboard[row].length; col++) {
                Piece piece = getPieceAt(row, col);
                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isValidMove(kingPosition, this)) {
                        return true; // An opposing piece can capture the king
                    }
                }
            }
        }
        return false;

    }

    public boolean wouldBeInCheckAfterMove(ColorPiece kingColor, Position from, Position to) {
        // Simulate the move temporarily
        Piece temp = getPieceAt(to.getRow(), to.getColumn());
        setPiece(to.getRow(), to.getColumn(), getPieceAt(from.getRow(), from.getColumn()));
        setPiece(from.getRow(), from.getColumn(), null);

        boolean inCheck = isInCheck(kingColor);

        // Undo the move
        setPiece(from.getRow(), from.getColumn(), getPieceAt(to.getRow(), to.getColumn()));
        setPiece(to.getRow(), to.getColumn(), temp);

        return inCheck;

    }

    public boolean isPositionOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < chessboard.length &&
                position.getColumn() >= 0 && position.getColumn() < chessboard[0].length;

    }

    public boolean isCheckmate(ColorPiece kingColor) {
        if (!isInCheck(kingColor)) {
            return false;
        }

        Position kingPosition = findKingPosition(kingColor);
        King king = (King) getPieceAt(kingPosition.getRow(), kingPosition.getColumn());


        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }
                Position newPosition = new Position(kingPosition.getRow() + rowOffset,
                        kingPosition.getColumn() + colOffset);

                if (isPositionOnBoard(newPosition) && king.isValidMove(newPosition,this)
                        && !wouldBeInCheckAfterMove(kingColor, kingPosition, newPosition)) {
                    return false;
                }
            }
        }

        return true;

    }

    public void castle(Position kingStart, Position rookStart ) {
        Piece kingPiece = getPieceAt(kingStart);
        Piece rookPiece = getPieceAt(rookStart);

        if (kingPiece instanceof King && rookPiece instanceof Rook) {
            King king = (King) kingPiece;
            Rook rook = (Rook) rookPiece;


            if (!king.hasMoved() && !rook.hasMoved() &&
                    isPathClear(kingStart, rookStart, king.getColor()) && !isInCheck(king.getColor())) {


                Position newKingPos = new Position(kingStart.getRow(), kingStart.getColumn() + (rookStart.getColumn() > kingStart.getColumn() ? 1 : -1));
                movePieces(kingStart, newKingPos);
                king.setHasMoved(true);


                Position newRookPos = new Position(kingStart.getRow(), newKingPos.getColumn() - (rookStart.getColumn() > kingStart.getColumn() ? 1 : -1));
                movePieces(rookStart, newRookPos);
                rook.setHasMoved(true);
            } else {
                System.out.println("Conditions pour le roque non remplies.");
            }
        }
    }


    private boolean isPathClear(Position start, Position end, ColorPiece kingColor) {
        int startCol = start.getColumn();
        int endCol = end.getColumn();
        int row = start.getRow();

        if (startCol < endCol) {

            for (int col = startCol + 1; col < endCol; col++) {
                if (getPieceAt(row, col) != null && wouldBeInCheckAfterMove(kingColor, new Position(row,col-1), new Position(row,col-1))) {
                    return false;
                }
            }
        } else {

            for (int col = endCol + 1; col < startCol; col++) {
                if (getPieceAt(row, col) != null && wouldBeInCheckAfterMove(kingColor, new Position(row,col-1), new Position(row,col)))  {
                    return false;
                }
            }
        }
        return true;
    }



}
