package com.gmail.mphag;

import com.badlogic.gdx.Gdx;

public class Settings {

    public static final int GAME_WIDTH = Gdx.graphics.getWidth();
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight();

    public static final int BORDER_HEIGHT = GAME_HEIGHT / 54;
    public static final int BORDER_WIDTH = GAME_WIDTH;
    public static final int TILE_SIZE = GAME_HEIGHT / 6 - BORDER_HEIGHT/6;

}
