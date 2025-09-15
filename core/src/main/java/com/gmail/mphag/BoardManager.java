package com.gmail.mphag;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.*;

public class BoardManager {

    private final Map<GridPoint2, BoardTile> boardMap = new HashMap<>();

    private Player currentPlayer;

    private boolean isHighlightingDemonTiles; //Om demon tiles skal highlightes
    private boolean isHighlightingAngelTiles; //Om angel tiles skal highlightes
    private boolean isHighlightingOpponentAngels; //Om modstanderens angels skal highlightes
    private boolean isHighlightingAngels; //Om ens egne angels skal highlightes

    public BoardManager(Player player1, Player player2) {
        for (TileType value : TileType.values()) {
            value.load();
        }
        load(player1, player2);
    }

    public void load(Player player2, Player player1) {
        Player currentPlayer = player1;
        for (int row = 0; row < Settings.TILE_ROWS; row++) {

            if(row > 2) {
                currentPlayer = player2;
            }

            for (int column = 0; column < Settings.TILE_COLUMNS; column++) {
                GridPoint2 gridPoint2 = new GridPoint2(column, row);

                if(column == Settings.TILE_COLUMNS - 1) {
                    boardMap.put(gridPoint2, new BoardTile(currentPlayer, gridPoint2, "demonTile.png", true));
                    continue;
                } else if(column == 1) {
                    boardMap.put(gridPoint2, new BoardTile(currentPlayer, gridPoint2, "angelTile.png", false));
                    continue;
                }
                boardMap.put(gridPoint2, new BoardTile(currentPlayer, gridPoint2));
            }
        }
    }

    public void drawShapes(ShapeRenderer shapeRenderer) {
        drawBorder(shapeRenderer);
        drawGrid(shapeRenderer);
        drawPlayerBases(shapeRenderer);

    }

    private void drawTileIcons(SpriteBatch batch) {
        for (BoardTile tile : boardMap.values()) {
            tile.draw(batch);
        }
    }

    public Collection<BoardTile> getAllTiles() {
        return boardMap.values();
    }

    private void drawPlayerBases(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(ColorType.PLAYER_BASE_ONE.getColor());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < Settings.GAME_HEIGHT; i += Settings.TILE_HEIGHT) {
            shapeRenderer.rect(0, i, Settings.TILE_WIDTH, Settings.TILE_HEIGHT);

            if (i == Settings.TILE_HEIGHT * 2) {
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
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0; i < Settings.GAME_WIDTH; i += Settings.TILE_WIDTH) {
            for (int j = 0; j < Settings.GAME_HEIGHT; j += Settings.TILE_HEIGHT) {
                shapeRenderer.rect(i , j - 1, Settings.TILE_WIDTH, Settings.TILE_HEIGHT);

                if (j == Settings.TILE_HEIGHT * 2) {
                    j += Settings.BORDER_HEIGHT;
                }
            }

        }

        shapeRenderer.end();
    }

    public void drawImages(SpriteBatch batch) {
        drawTileMark(batch);
        drawTileIcons(batch);

    }

    private void drawTileMark(SpriteBatch batch) {
        for (BoardTile tile : boardMap.values()) {
            tile.drawMark(batch);
        }
    }

    public void moveAllDemons() {

        List<GridPoint2> demonsToMove = new ArrayList<>();

        for (int column = Settings.TILE_COLUMNS - 1; column >= 0; column--) {
            for (int row = 0; row < Settings.TILE_ROWS - 1 ; row++) {
                BoardTile tile = this.boardMap.get(new GridPoint2(column, row));

                if(tile.getTileType() == TileType.DEMON) {
                    demonsToMove.add(new GridPoint2(column, row));
                }

            }
        }

        System.out.println(demonsToMove.size());

        for (GridPoint2 gridPoint2 : demonsToMove) {
            this.boardMap.get(gridPoint2).setTileType(TileType.EMPTY);
            this.boardMap.get(gridPoint2.set(gridPoint2.x, gridPoint2.y + 1)).setTileType(TileType.DEMON);
        }

        System.out.println("done");
    }

    public void highlightDemonTiles(Player currentPlayer) {
        this.isHighlightingDemonTiles = true;
        this.currentPlayer = currentPlayer;
    }

    public void highlightAngelTiles(Player currentPlayer) {
        this.isHighlightingAngelTiles = true;
        this.currentPlayer = currentPlayer;
    }

    public void highlightOpponentAngels(Player currentPlayer) {
        this.isHighlightingOpponentAngels = true;
        this.currentPlayer = currentPlayer;
    }

    public void highlightAngels(Player currentPlayer) {
        this.isHighlightingAngels = true;
        this.currentPlayer = currentPlayer;
    }

    public void resetHighlighting() {
        this.isHighlightingAngels = false;
        this.isHighlightingOpponentAngels = false;
        this.isHighlightingAngelTiles = false;
        this.isHighlightingDemonTiles = false;
    }

    public BoardTile getTile(int i, int i1) {
        return this.boardMap.get(new GridPoint2(i, i1));
    }
}
