package com.epf.Chessgame.Model;

public class MoveResponse {
    private Move move;
    private boolean checkmate;

    public MoveResponse(Move move, boolean checkmate) {
        this.move = move;
        this.checkmate = checkmate;
    }

    public Move getMove() {
        return move;
    }

    public boolean isCheckmate() {
        return checkmate;
    }
}
