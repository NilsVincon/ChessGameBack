package com.epf.Chessgame.Model;

public class MoveResponse {
    private Move move;
    private boolean checkmate;

    private String activePlayer;

    public MoveResponse(Move move, boolean checkmate) {
        this.move = move;
        this.checkmate = checkmate;
    }

    public MoveResponse(Move move, boolean checkmate, String activePlayer) {
        this.move = move;
        this.checkmate = checkmate;
        this.activePlayer=activePlayer;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Move getMove() {
        return move;
    }

    public boolean isCheckmate() {
        return checkmate;
    }
}
