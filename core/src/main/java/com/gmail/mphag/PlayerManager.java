package com.gmail.mphag;

public class PlayerManager {

    private final Player player1;
    private final Player player2;

    public PlayerManager() {
        this.player1 = new Player(true);
        this.player2 = new Player(false);
    }

    public void switchTurn() {
        if(player1.hasTurn()) {
            player1.setTurn(false);
            player2.setTurn(true);
        } else {
            player2.setTurn(false);
            player1.setTurn(true);
        }

    }

    public Player getCurrentPlayer() {
        if(player1.hasTurn()) {
            return player1;
        }
        return player2;
    }

}
