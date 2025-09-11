package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum TileType {
    EMPTY("empty.png"),
    ANGEL("angel.png"),
    DEMON("demon.png");

    private final String path;
    private Texture texture;

    TileType(String path) {
        this.path = path;
    }

    public void load() {
        this.texture = new Texture(Gdx.files.internal(path));
    }

    public Texture getTexture() {
        return texture;
    }
}
