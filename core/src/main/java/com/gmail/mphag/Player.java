package com.gmail.mphag;

public class Player {

    private boolean hasTurn;
    private SpinType currentSpinType;

    public Player(boolean hasTurn) {
        this.hasTurn = hasTurn;
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
}
