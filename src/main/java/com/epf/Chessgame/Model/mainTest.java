package com.epf.Chessgame.Model;
import com.epf.Chessgame.Model.pieces.colorPiece;
import com.epf.Chessgame.Model.pieces.piece;
import com.epf.Chessgame.Model.pieces.position;

import java.util.Scanner;

public class mainTest {

    public static void main(String[] args) {
        board chessboard = new board();
        System.out.println(chessboard);
        startGame(chessboard);




    }

    public static void startGame(board chessBoard) {
        Scanner scanner = new Scanner(System.in);
        boolean isWhiteTurn = true; // Commence par le joueur blanc

        while (true) {
            System.out.println(isWhiteTurn ? "C'est le tour des Blancs" : "C'est le tour des Noirs");
            System.out.println(chessBoard);

            System.out.println("Entrez la position de départ (ex: e2): ");
            String startPositionInput = scanner.nextLine();
            position startPosition = convertInputToPosition(startPositionInput);

            System.out.println("Entrez la position d'arrivée (ex: e4): ");
            String endPositionInput = scanner.nextLine();
            position endPosition = convertInputToPosition(endPositionInput);

            piece movingPiece = chessBoard.getPieceAt(startPosition);
            if (movingPiece != null && movingPiece.getColor() == (isWhiteTurn ? colorPiece.WHITE : colorPiece.BLACK)) {
                if (movingPiece.isValidMove(endPosition, chessBoard)) {
                    chessBoard.movePieces(startPosition, endPosition);
                    isWhiteTurn = !isWhiteTurn; // Change de tour
                } else {
                    System.out.println("Mouvement invalide. Essayez encore.");
                }
            } else {
                System.out.println("Pas de pièce à cette position ou ce n'est pas votre tour. Essayez encore.");
            }
        }
    }

    private static position convertInputToPosition(String input) {
        // Ex : e2 -> Position(6, 4) pour un échiquier de 0 à 7
        int row = 8 - Character.getNumericValue(input.charAt(1)); // Conversion de 1-8 à 0-7
        int column = input.charAt(0) - 'a'; // Conversion de 'a'-'h' à 0-7
        return new position(row, column);
    }
}
