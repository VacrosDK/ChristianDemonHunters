package com.gmail.mphag;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class SpinBox {

    private final GridPoint2 gridPoint2;
    private final Texture texture;

    public SpinBox(GridPoint2 gridPoint2, Texture texture) {
        this.gridPoint2 = gridPoint2;
        this.texture = texture;
    }

    public void draw() {

    }

    public void update(float speed) {
        this.gridPoint2.x -= speed;
    }

}
