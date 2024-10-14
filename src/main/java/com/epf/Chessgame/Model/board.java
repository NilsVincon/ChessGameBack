package com.epf.Chessgame.Model;

import com.epf.Chessgame.Model.pieces.*;


public class board {

    private piece[][] chessboard;

    public board() {
        this.chessboard = new piece[8][8];
        initBoard();
    }

    public piece[][] getChessBoard() {
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

    private void initBoard() {

        chessboard[0][0] = new rook(colorPiece.BLACK, new position(0,0));
        chessboard[0][7] = new rook(colorPiece.BLACK, new position(0, 7));
        chessboard[7][0] = new rook(colorPiece.WHITE, new position(7, 0));
        chessboard[7][7] = new rook(colorPiece.WHITE, new position(7, 7));

        chessboard[0][1] = new knight(colorPiece.BLACK, new position(0, 1));
        chessboard[0][6] = new knight(colorPiece.BLACK, new position(0, 6));
        chessboard[7][1] = new knight(colorPiece.WHITE, new position(7, 1));
        chessboard[7][6] = new knight(colorPiece.WHITE, new position(7, 6));

        chessboard[0][2] = new bishop(colorPiece.BLACK, new position(0, 2));
        chessboard[0][5] = new bishop(colorPiece.BLACK, new position(0, 5));
        chessboard[7][2] = new bishop(colorPiece.WHITE, new position(7, 2));
        chessboard[7][5] = new bishop(colorPiece.WHITE, new position(7, 5));

        chessboard[0][3] = new queen(colorPiece.BLACK, new position(0, 3));
        chessboard[7][3] = new queen(colorPiece.WHITE, new position(7, 3));

        chessboard[0][4] = new king(colorPiece.BLACK, new position(0, 4));
        chessboard[7][4] = new king(colorPiece.WHITE, new position(7, 4));

        for (int i = 0; i < 8; i++) {
            chessboard[1][i] = new pawn(colorPiece.BLACK, new position(1, i));
            chessboard[6][i] = new pawn(colorPiece.WHITE, new position(6, i));
        }

    }

    public void movePieces(position start, position end){
        if (chessboard[start.getRow()][start.getColumn()] != null &&
                chessboard[start.getRow()][start.getColumn()].isValidMove(end, this)) {

            chessboard[end.getRow()][end.getColumn()] = chessboard[start.getRow()][start.getColumn()];

            chessboard[end.getRow()][end.getColumn()].setPosition(end);

            chessboard[start.getRow()][start.getColumn()] = null;
        }
    }

   public piece getPieceAt(position pos){
       return chessboard[pos.getRow()][pos.getColumn()];
   }
    public piece getPieceAt(int row, int column){
        return chessboard[row][column];
    }
}
