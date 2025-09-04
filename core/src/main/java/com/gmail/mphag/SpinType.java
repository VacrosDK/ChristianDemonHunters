package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum SpinType {
    A(1, "Spawn engel", "spin1.png"),
    B(2, "Spawn dæmon", "spin1.png"),
    C(3, "Træk spørgsmål", "spin1.png"),
    D(4, "Fjern modstanderengel", "spin1.png"),
    E(5, "Skyd med engel", "spin1.png"),
    F(6, "Intet", "spin1.png"),
    G(7, "Alle demoner rykker frem", "spin1.png");


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
}
