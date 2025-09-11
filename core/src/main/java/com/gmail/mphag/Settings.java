package com.gmail.mphag;

import com.badlogic.gdx.Gdx;

public class Settings {

    public static final int GAME_WIDTH = Gdx.graphics.getWidth();
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight();

    public static final int TILE_COLUMNS = 10;
    public static final int TILE_ROWS = 6;

    public static final int BORDER_HEIGHT = GAME_HEIGHT / 54;
    public static final int BORDER_WIDTH = GAME_WIDTH;
    public static final float TILE_HEIGHT = GAME_HEIGHT / TILE_ROWS - BORDER_HEIGHT / TILE_ROWS;
    public static final float TILE_WIDTH = GAME_WIDTH / TILE_COLUMNS;

}
