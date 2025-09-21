package com.gmail.mphag.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.gmail.mphag.Settings;

import java.util.ArrayList;
import java.util.Collection;

public class Projectile {

    private final Texture texture;
    private final GridPoint2 gridPoint2;

    private boolean shotIsDone = false;

    public Projectile(Texture texture, GridPoint2 gridPoint2) {
        this.texture = texture;
        this.gridPoint2 = gridPoint2;

        System.out.println("New shot:");
        System.out.println(gridPoint2.x);
        System.out.println(gridPoint2.y);

    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, gridPoint2.x * Settings.TILE_WIDTH + Settings.TILE_WIDTH / 2, gridPoint2.y * Settings.TILE_HEIGHT + Settings.TILE_HEIGHT / 2);
    }

    public void update(Collection<BoardTile> values) {

        gridPoint2.x += 1;

        if(gridPoint2.x == Settings.TILE_COLUMNS) {
            shotIsDone = true;
            return;
        }

        ArrayList<BoardTile> boardTiles = new ArrayList<>(values);
        for (BoardTile boardTile : boardTiles) {
            if(boardTile.getTileOccupant() != TileOccupant.DEMON) {
                continue;
            }

            if(boardTile.getGridPoint().x / Settings.TILE_WIDTH == gridPoint2.x && (int)(boardTile.getGridPoint().y / Settings.TILE_HEIGHT) == gridPoint2.y) {
                boardTile.setTileOccupant(TileOccupant.EMPTY);
                shotIsDone = true;
            }
        }
    }

    public boolean isShotIsDone() {
        return shotIsDone;
    }
}
