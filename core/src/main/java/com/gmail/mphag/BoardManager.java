package com.gmail.mphag;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BoardManager {

    public void draw(ShapeRenderer shapeRenderer) {
        drawBorder(shapeRenderer);
        drawGrid(shapeRenderer);
        drawPlayerBases(shapeRenderer);
    }

    private void drawPlayerBases(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(ColorType.PLAYER_BASE_ONE.getColor());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < Settings.GAME_HEIGHT; i += Settings.TILE_SIZE) {
            shapeRenderer.rect(0, i, Settings.TILE_SIZE, Settings.TILE_SIZE);

            if (i == Settings.TILE_SIZE * 2) {
                shapeRenderer.setColor(ColorType.PLAYER_BASE_TWO.getColor());
                i += Settings.BORDER_HEIGHT - 1;
            }

        }
        shapeRenderer.end();


    }

    private static void drawBorder(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, Settings.GAME_HEIGHT / 2 - Settings.BORDER_HEIGHT / 2, Settings.BORDER_WIDTH, Settings.BORDER_HEIGHT);
        shapeRenderer.end();
    }

    private static void drawGrid(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0; i < Settings.GAME_WIDTH; i += Settings.TILE_SIZE) {

            for (int j = 0; j < Settings.GAME_HEIGHT; j += Settings.TILE_SIZE) {
                shapeRenderer.rect(i - 1, j - 1, Settings.TILE_SIZE, Settings.TILE_SIZE);

                if (j == Settings.TILE_SIZE * 2) {
                    j += Settings.BORDER_HEIGHT - 1;
                }
            }

        }

        shapeRenderer.end();
    }

}
