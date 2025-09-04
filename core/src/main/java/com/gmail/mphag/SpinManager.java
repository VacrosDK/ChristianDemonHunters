package com.gmail.mphag;

import java.util.ArrayList;
import java.util.List;

public class SpinManager {


    private boolean hasSpun;
    private int spinNumber;

    private final List<SpinBox> spinBoxes = new ArrayList<>();

    public SpinManager() {
        load();
    }

    public void load() {
        for (SpinType value : SpinType.values()) {
            value.load();
        }
    }

    public void draw() {

    }

    public void spin() {
        Core.GAME_STATE = GameState.SPINNING;

    }

    public boolean hasSpun() {
        return hasSpun;
    }

    public int getSpinNumber() {
        return spinNumber;
    }
}
