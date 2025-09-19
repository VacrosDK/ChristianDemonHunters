package com.gmail.mphag;

import com.badlogic.gdx.math.GridPoint2;

public enum QuestionChoice {

    //C1
    L1(new GridPoint2((int) Settings.TILE_WIDTH, (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2))),
    L4(new GridPoint2((int) Settings.TILE_WIDTH, (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT * 2 + Settings.TILE_HEIGHT/4))),

    //C2
    L2(new GridPoint2((int) (Settings.GAME_WIDTH - Settings.TILE_WIDTH), (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2))),
    L5(new GridPoint2((int) (Settings.GAME_WIDTH - Settings.TILE_WIDTH), (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT * 2 + Settings.TILE_HEIGHT/4))),

    //C3
    L3(new GridPoint2(Settings.GAME_WIDTH / 2, (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT/2))),
    L6(new GridPoint2(Settings.GAME_WIDTH / 2, (int) (Settings.TILE_HEIGHT + Settings.TILE_HEIGHT * 2 + Settings.TILE_HEIGHT/4)));


    private final GridPoint2 gridPoint2;
    private SpinType assignedSpinType;

    QuestionChoice(GridPoint2 gridPoint2) {
        this.gridPoint2 = gridPoint2;
    }

    public GridPoint2 getGridPoint2() {
        return gridPoint2;
    }

    public void assignSpinType(SpinType spinType) {
        this.assignedSpinType = spinType;
    }

    public SpinType getAssignedSpinType() {
        return assignedSpinType;
    }
}
