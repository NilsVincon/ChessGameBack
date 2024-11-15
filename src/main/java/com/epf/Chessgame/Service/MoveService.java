package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.MoveDAO;
import com.epf.Chessgame.Model.Board;
import com.epf.Chessgame.Model.Game;
import com.epf.Chessgame.Model.Move;
import com.epf.Chessgame.Model.MoveResponse;
import com.epf.Chessgame.Model.pieces.Position;
import com.epf.Chessgame.Model.pieces.ColorPiece;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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

    public MoveResponse createMove(Move move) {
        Position start = move.getInitialPosition();
        Position end = move.getFinalPosition();
        ColorPiece pieceColor = board.getPieceAt(start).getColor();
        ColorPiece otherColor = (pieceColor == ColorPiece.WHITE) ? ColorPiece.BLACK : ColorPiece.WHITE;
        log.info("Piece color: {}", pieceColor);
        if (isMoveValid(start, end, pieceColor)) {
            log.info("Mouvement valide: {}", move);
            board.movePieces(start, end);
            try {
                Move savedMove = moveDAO.save(move);
                log.info("Mouvement sauvegardé : " + move);
                currentPlayer = (currentPlayer == ColorPiece.WHITE) ? ColorPiece.BLACK : ColorPiece.WHITE;

                boolean checkmate = board.isCheckmate(otherColor);
                if (checkmate) {
                    System.out.println("Echec et mat !");
                    board.initBoard();
                    this.currentPlayer = ColorPiece.WHITE;
                }

                return new MoveResponse(savedMove, checkmate);
            } catch (Exception e) {
                log.error("Erreur lors de la sauvegarde du mouvement: {}", e.getMessage());
                throw new RuntimeException("Erreur lors de la sauvegarde du mouvement dans la base de données.", e);
            }


        } else {
            throw new IllegalArgumentException("Mouvement invalide.");
        }
    }

    public void CreateSurrender(){
        board.initBoard();
        this.currentPlayer = ColorPiece.WHITE;
    }

    public void createDraw(){
        board.initBoard();
        this.currentPlayer = ColorPiece.WHITE;
    }



    public Move updateMove(Move move) {
        Position start = move.getInitialPosition();
        Position end = move.getFinalPosition();
        ColorPiece pieceColor = board.getPieceAt(start).getColor();

        // Validation du mouvement avant mise à jour
        if (this.isMoveValid(start, end, pieceColor)) {
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
            log.warn("It is not the player's turn: expected {}, found {}", currentPlayer, pieceColor);
            return false;
        }

        // Vérifie si la position de départ et d'arrivée sont valides
        if (!board.isPositionOnBoard(start) || !board.isPositionOnBoard(end)) {
            log.warn("Invalid move positions: start={} end={}", start, end);
            return false;
        }

        if (board.castle(start,end)){
            return true;
        }

        // Vérifie si la pièce peut se déplacer
        if (board.getPieceAt(start) == null || board.getPieceAt(start).getColor() != pieceColor) {
            log.warn("No piece at start position or color mismatch: {}", start);
            return false;
        }

        // Vérifie si le mouvement est valide et ne met pas le roi en échec
        if (board.getPieceAt(start).isValidMove(end, board) && !board.wouldBeInCheckAfterMove(pieceColor, start, end)) {
            return true;
        }

        return false;
    }

}
