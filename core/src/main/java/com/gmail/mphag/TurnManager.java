package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TurnManager {

    private int currentPlayer = 1;

    private int textDrawY;
    private String spinText = "Click anywhere to spin!";

    private final BitmapFont bitmapFont;

    public TurnManager() {
        this.bitmapFont = new BitmapFont(Gdx.files.internal("turnFont.fnt"));
    }

    public void update() {
        if(Core.GAME_STATE == GameState.WAITING_FOR_ROLL) {
            if(currentPlayer == 1) {
                textDrawY = Settings.GAME_HEIGHT - 3 * Settings.GAME_HEIGHT/4;
            } else {
                textDrawY = Settings.GAME_HEIGHT - Settings.GAME_HEIGHT/4;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if(Core.GAME_STATE == GameState.WAITING_FOR_ROLL) {
            Utils.drawStringCentered(bitmapFont, batch, spinText, Settings.GAME_WIDTH/2, textDrawY);
        }
    }

    public void switchPlayerTurn() {
        if(currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

    public void playTurn(int rolledNumber) {

    }
}
