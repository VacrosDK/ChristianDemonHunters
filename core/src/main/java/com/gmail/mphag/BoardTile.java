package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class BoardTile {

    private final Player player;

    private boolean isAngelTile;
    private boolean isDemonTile;

    private TileOccupant tileOccupant;
    private GridPoint2 gridPoint;

    private Texture tileMark = null;

    public BoardTile(Player owner, GridPoint2 gridPoint2) {
        this.player = owner;
        this.tileOccupant = TileOccupant.EMPTY;
        this.gridPoint = new GridPoint2(gridPoint2.x, gridPoint2.y);

        setupGridPoint();
    }
    public BoardTile(Player owner, GridPoint2 gridPoint2, String tileMarkPath, boolean isDemonTile) {
        this.player = owner;
        this.tileOccupant = TileOccupant.EMPTY;
        this.gridPoint = new GridPoint2(gridPoint2.x, gridPoint2.y);
        this.tileMark = new Texture(Gdx.files.internal(tileMarkPath));

        if(isDemonTile) {
            this.isDemonTile = true;
            this.isAngelTile = false;
        } else {
            this.isAngelTile = true;
            this.isDemonTile = false;
        }

        setupGridPoint();
    }

    private void setupGridPoint() {
        this.gridPoint.x = (int) (gridPoint.x * Settings.TILE_WIDTH);

        int deltaY = 0;

        if(gridPoint.y > 2) {
            deltaY = Settings.BORDER_HEIGHT;
        }

        gridPoint.y = (int) (gridPoint.y * Settings.TILE_HEIGHT);
        gridPoint.y += deltaY;
    }

    public void setTileOccupant(TileOccupant tileOccupant) {
        this.tileOccupant = tileOccupant;
    }

    public TileOccupant getTileOccupant() {
        return tileOccupant;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tileOccupant.getTexture(), gridPoint.x + Settings.TILE_WIDTH/2 - tileOccupant.getTexture().getWidth()/2, gridPoint.y + Settings.TILE_HEIGHT/8);

    }

    public void drawMark(SpriteBatch batch) {
        if(tileMark == null) {
            return;
        }
        batch.draw(
            tileMark,
            gridPoint.x + Settings.TILE_WIDTH / 2 - tileMark.getWidth() / 2,
            gridPoint.y + Settings.TILE_HEIGHT / 2 - tileMark.getHeight() / 2);
    }

    public void spawnDemon() {
        this.tileOccupant = TileOccupant.DEMON;
    }

    public boolean isAngelTile() {
        return isAngelTile;
    }

    public boolean isDemonTile() {
        return isDemonTile;
    }

    public void spawnAngel() {
        this.tileOccupant = TileOccupant.ANGEL;
    }

    public boolean belongsTo(Player currentPlayer) {
        return player == currentPlayer;
    }

    public GridPoint2 getGridPoint() {
        return gridPoint;
    }

    public boolean isEmpty() {
        return tileOccupant == TileOccupant.EMPTY;
    }

    public void killAngel() {
        this.tileOccupant = TileOccupant.EMPTY;
    }

    public void shootWithAngel() {

    }
}
