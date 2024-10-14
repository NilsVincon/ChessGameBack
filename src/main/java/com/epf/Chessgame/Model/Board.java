package com.epf.Chessgame.Model;

import com.epf.Chessgame.Model.pieces.*;


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

    public void movePieces(Position start, Position end){
        if (chessboard[start.getRow()][start.getColumn()] != null &&
                chessboard[start.getRow()][start.getColumn()].isValidMove(end, this)) {

            chessboard[end.getRow()][end.getColumn()] = chessboard[start.getRow()][start.getColumn()];

            chessboard[end.getRow()][end.getColumn()].setPosition(end);

            chessboard[start.getRow()][start.getColumn()] = null;
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

    private boolean isPositionOnBoard(Position position) {
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

}
