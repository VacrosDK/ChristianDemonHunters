package com.gmail.mphag;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class SpinBox {

    private final GridPoint2 gridPoint2;
    private final int width;
    private final int height;
    private final SpinType type;

    public SpinBox(SpinType type, int spacing, int width, int height) {
        this.gridPoint2 = new GridPoint2(Settings.GAME_WIDTH, Settings.GAME_HEIGHT/2 - spacing);
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(type.getTexture(), gridPoint2.x, gridPoint2.y, width, height);
    }

    public void update(float speed) {
        this.gridPoint2.x -= (int) speed;
    }

    public GridPoint2 getLocation() {
        return gridPoint2;
    }

    public boolean shouldRemove() {
        return gridPoint2.x + width <= 0;
    }
}
