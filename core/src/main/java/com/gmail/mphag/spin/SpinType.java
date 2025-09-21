package com.gmail.mphag.spin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum SpinType {
    ADD_ANGEL(1, "Spawn engel", "spawnAngel.png"),
    ADD_DEMON(2, "Spawn dæmon", "spawnDemon.png"),
    QUESTION(3, "Træk spørgsmål", "question.png"),
    REMOVE_ANGEL(4, "Fjern modstanderengel", "angelKill.png"),
    SHOOT_WITH_ANGEL(5, "Skyd med engel", "angelShoot.png"),
    NOTHING(6, "Intet", "nothing.png"),
    MOVE_DEMONS(7, "Alle demoner rykker frem", "moveDemons.png");


    private final String info;
    private final int spinNumber;
    private final String texturePath;
    private Texture texture;

    SpinType(int spinNumber, String info, String texturePath) {
        this.info = info;
        this.spinNumber = spinNumber;
        this.texturePath = texturePath;
    }

    public void load() {
        if(this.texture == null) {
            this.texture = new Texture(Gdx.files.internal(texturePath));
        }
    }

    public String getInfo() {
        return info;
    }

    public int getSpinNumber() {
        return spinNumber;
    }

    public Texture getTexture() {
        return texture;
    }
}
