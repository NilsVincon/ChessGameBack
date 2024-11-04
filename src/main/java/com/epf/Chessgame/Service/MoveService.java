package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.MoveDAO;
import com.epf.Chessgame.Model.Board;
import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Model.pieces.Position;
import com.epf.Chessgame.Model.pieces.ColorPiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    private final MoveDAO moveDAO;
    private final Board board; // Instance de Board pour gérer le jeu
    private ColorPiece currentPlayer; // Variable pour suivre le joueur actuel

    @Autowired
    public MoveService(MoveDAO moveDAO, Board board) {
        this.moveDAO = moveDAO;
        this.board = board;
        this.currentPlayer = ColorPiece.WHITE; // Initialisation à blanc (ou noir selon votre préférence)
    }

    public Iterable<Move> getMoves() {
        return moveDAO.findAll();
    }

    public Move getMove(Long id) {
        return moveDAO.findById(id).orElse(null);
    }

    public Move createMove(Move move) {
        Position start = move.getInitialPosition();
        Position end = move.getFinalPosition();
        ColorPiece pieceColor = board.getPieceAt(start).getColor();
        System.out.println("couleur" + pieceColor);

        if (isMoveValid(start, end, pieceColor)) {
            board.movePieces(start, end); // Déplace la pièce avant de sauvegarder le mouvement
            Move savedMove = moveDAO.save(move); // Sauvegarde le mouvement

            currentPlayer = (currentPlayer == ColorPiece.WHITE) ? ColorPiece.BLACK : ColorPiece.WHITE;

            return savedMove;
        } else {
            throw new IllegalArgumentException("Mouvement invalide.");
        }
    }

    public Move updateMove(Move move) {
        Position start = move.getInitialPosition();
        Position end = move.getFinalPosition();
        ColorPiece pieceColor = board.getPieceAt(start).getColor();

        // Validation du mouvement avant mise à jour
        if (isMoveValid(start, end, pieceColor)) {
            return moveDAO.save(move);
        } else {
            throw new IllegalArgumentException("Mouvement invalide.");
        }
    }

    public void deleteMove(Long id) {
        moveDAO.deleteById(id);
    }

    private boolean isMoveValid(Position start, Position end, ColorPiece pieceColor) {
        // Vérifie si c'est le tour du joueur
        if (pieceColor != currentPlayer) {
            return false;
        }

        // Vérifie si la position de départ et d'arrivée sont valides
        if (!board.isPositionOnBoard(start) || !board.isPositionOnBoard(end)) {
            return false;
        }

        // Vérifie si la pièce peut se déplacer
        if (board.getPieceAt(start) == null || board.getPieceAt(start).getColor() != pieceColor) {
            return false;
        }

        // Vérifie si le mouvement est valide et ne met pas le roi en échec
        if (board.getPieceAt(start).isValidMove(end, board) && !board.wouldBeInCheckAfterMove(pieceColor, start, end)) {
            return true;
        }

        return false;
    }
}
