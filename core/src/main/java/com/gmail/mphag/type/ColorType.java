package com.gmail.mphag.type;

import com.badlogic.gdx.graphics.Color;

public enum ColorType {

    PLAYER_BASE_ONE(new Color(0,204 / 255f,204 / 255f,1)),
    PLAYER_BASE_TWO(new Color(255/255f, 102/255f, 178/255f, 1));

    private final Color color;

    ColorType(Color color) {

        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
