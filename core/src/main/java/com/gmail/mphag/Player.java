package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Player {

    private boolean hasTurn;
    private SpinType currentSpinType;

    private final int uiTextY;
    private final BitmapFont uiText;

    public Player(boolean hasTurn, int uiTextY, String uiTextPath) {
        this.hasTurn = hasTurn;
        this.uiTextY = uiTextY;
        this.uiText = new BitmapFont(Gdx.files.internal(uiTextPath));
    }

    public boolean hasTurn() {
        return hasTurn;
    }

    public void setTurn(boolean b) {
        this.hasTurn = b;
    }

    public SpinType getCurrentSpinType() {
        return currentSpinType;
    }

    public void setCurrentSpinType(SpinType currentSpinType) {
        this.currentSpinType = currentSpinType;
    }

    public int getUiTextY() {
        return uiTextY;
    }

    public BitmapFont getFont() {
        return uiText;
    }
}
