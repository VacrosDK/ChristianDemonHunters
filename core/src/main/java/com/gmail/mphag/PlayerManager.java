package com.gmail.mphag;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerManager {

    private final Player player1;
    private final Player player2;

    private final String spinText = "Click anywhere to spin!";

    public PlayerManager() {
        this.player1 = new Player(true, Settings.GAME_HEIGHT - Settings.GAME_HEIGHT/4, "playerOneUI.fnt");
        this.player2 = new Player(false, Settings.GAME_HEIGHT/4, "playerTwoUI.fnt");
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

    public void draw(SpriteBatch batch) {
        if(Core.GAME_STATE == GameState.WAITING_FOR_ROLL) {
            Utils.drawStringCentered(getCurrentPlayer().getFont(), batch, spinText, Settings.GAME_WIDTH/2, getCurrentPlayer().getUiTextY());
        }
    }

    public Player getCurrentPlayer() {
        if(player1.hasTurn()) {
            return player1;
        }
        return player2;
    }

    public Player getPlayerTwo() {
        return player2;
    }

    public Player getPlayerOne() {
        return player1;
    }
}
