package com.gmail.mphag;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class SpinManager {


    private int spinSpeed;

    private boolean hasSpun = false;

    private final List<SpinBox> spinBoxes = new ArrayList<>();

    public SpinManager() {
        load();
        this.spinSpeed = 200;
    }

    public void load() {
        for (SpinType value : SpinType.values()) {
            value.load();
        }
    }

    public void update() {

        if(Core.GAME_STATE == GameState.SPINNING) {

        }

        for (SpinBox spinBox : spinBoxes) {
            spinBox.update(spinSpeed * Gdx.graphics.getDeltaTime());
        }
    }

    public void draw() {
        for (SpinBox spinBox : spinBoxes) {
            spinBox.draw();
        }
    }

    public void spin() {
        Core.GAME_STATE = GameState.SPINNING;

    }

    public boolean hasSpun() {
        return hasSpun;
    }

}
