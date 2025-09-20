package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.*;

public class BoardManager {

    private final Map<GridPoint2, BoardTile> boardMap = new HashMap<>();

    private final List<Projectile> projectiles = new ArrayList<>();
    private long lastUpdate = TimeUtils.millis();

    private final Texture projectileTexture;

    private Player currentPlayer;

    private BitmapFont winnerFont;

    private boolean isHighlightingDemonTiles; //Om demon tiles skal highlightes
    private boolean isHighlightingAngelTiles; //Om angel tiles skal highlightes
    private boolean isHighlightingOpponentAngels; //Om modstanderens angels skal highlightes
    private boolean isHighlightingAngels; //Om ens egne angels skal highlightes
    private boolean isDoneShooting;
    private boolean gameIsOver;
    private int gameWinner;

    public BoardManager(Player player1, Player player2) {
        this.projectileTexture = new Texture(Gdx.files.internal("fireball.png"));
        this.winnerFont = new BitmapFont(Gdx.files.internal("winnerFont.fnt"));
        for (TileOccupant value : TileOccupant.values()) {
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
        drawHighlighting(shapeRenderer);
    }


    private void drawHighlighting(ShapeRenderer shapeRenderer) {

        //Highlighting
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255/255f, 255/255f, 153/255f, 0.5f);

        List<BoardTile> tiles = new ArrayList<>(this.boardMap.values());
        for (int i = 0; i < tiles.size(); i++) {
            BoardTile tile = tiles.get(i);
            if(isHighlightingDemonTiles) {
                if(tile.isDemonTile() && tile.isEmpty() && !tile.belongsTo(currentPlayer)) {
                    highlight(shapeRenderer, tile);
                    continue;
                }
            }

            if(isHighlightingAngels) {
                if(tileIsAnAngelAndCurrentPlayerIsOwner(tiles, i)) {
                    highlight(shapeRenderer, tile);
                    continue;
                }
            }

            if(isHighlightingAngelTiles) {
                if(tile.isAngelTile() && tile.belongsTo(currentPlayer) && tile.isEmpty()) {
                    highlight(shapeRenderer, tile);
                    continue;
                }
            }

            if(isHighlightingOpponentAngels) {
                if(tile.getTileOccupant() == TileOccupant.ANGEL && !tile.belongsTo(currentPlayer)) {
                    highlight(shapeRenderer, tile);
                }
            }
        }

        shapeRenderer.end();
    }

    private boolean tileIsAnAngelAndCurrentPlayerIsOwner(List<BoardTile> tiles, int i) {
        return tiles.get(i).getTileOccupant() == TileOccupant.ANGEL && tiles.get(i).belongsTo(currentPlayer);
    }

    private void highlight(ShapeRenderer shapeRenderer, BoardTile boardTile) {
        shapeRenderer.rect(boardTile.getGridPoint().x, boardTile.getGridPoint().y, Settings.TILE_WIDTH, Settings.TILE_HEIGHT);
    }

    private void drawTileIcons(SpriteBatch batch) {
        for (BoardTile tile : boardMap.values()) {
            tile.draw(batch);
        }
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
        drawProjectiles(batch);
    }

    private void drawProjectiles(SpriteBatch batch) {
        for (Projectile projectile : projectiles) {
            projectile.draw(batch);
        }
    }

    private void drawTileMark(SpriteBatch batch) {
        for (BoardTile tile : boardMap.values()) {
            tile.drawMark(batch);
        }
    }

    public void moveAllDemons() {

        List<BoardTile> demonsToMove = new ArrayList<>();

        Collection<BoardTile> values = this.boardMap.values();

        for (BoardTile value : values) {
            if(value.getTileOccupant() == TileOccupant.DEMON) {
                demonsToMove.add(value);
            }
        }

        for (BoardTile boardTile : demonsToMove) {

            GridPoint2 gridPoint = boardTile.getOriginalGridPoint();

            GridPoint2 nextGridPoint = new GridPoint2(gridPoint.x - 1, gridPoint.y );

            if(nextGridPoint.x == 0) {
                if(gridPoint.y < 3) {
                    finishGame(1);
                } else {
                    finishGame(2);
                }
            }

            BoardTile nextTile = this.boardMap.get(nextGridPoint);

            if(nextTile.getTileOccupant() == TileOccupant.ANGEL) {
                nextTile.setTileOccupant(TileOccupant.EMPTY);
                return;
            } else {
                nextTile.setTileOccupant(TileOccupant.DEMON);
            }

            boardTile.setTileOccupant(TileOccupant.EMPTY);

        }

    }

    private void finishGame(int playerNumber) {
        this.gameIsOver = true;
        this.gameWinner = playerNumber;
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    public int getGameWinner() {
        return gameWinner;
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

    public boolean canSpawnMoreDemons(Player currentPlayer) {

        Collection<BoardTile> tiles = this.boardMap.values();

        boolean canSpawn = false;

        for (BoardTile tile : tiles) {
            if(!tile.belongsTo(currentPlayer) && tile.isEmpty() && tile.isDemonTile()) {
                canSpawn = true;
            }
        }

        return canSpawn;
    }

    public boolean canSpawnMoreAngels(Player currentPlayer) {
        Collection<BoardTile> tiles = this.boardMap.values();

        boolean canSpawn = false;

        for (BoardTile tile : tiles) {
            if(tile.belongsTo(currentPlayer) && tile.isEmpty() && tile.isAngelTile()) {
                canSpawn = true;
            }
        }

        return canSpawn;
    }

    public boolean hasAnyDemonsOnBoard() {
        for (BoardTile tile : this.boardMap.values()) {
            if (tile.getTileOccupant() == TileOccupant.DEMON) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAngelsOfPlayer(Player currentPlayer) {
        for (BoardTile value : this.boardMap.values()) {
            if(value.getTileOccupant() == TileOccupant.ANGEL && value.belongsTo(currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    public void shootWithAngel(BoardTile tile) {
        isDoneShooting = false;
        if(tile.getTileOccupant() != TileOccupant.ANGEL) {
            return;
        }

        Projectile projectile = new Projectile(projectileTexture, new GridPoint2((int) (tile.getGridPoint().x / Settings.TILE_WIDTH), (int) ((tile.getGridPoint().y) /Settings.TILE_HEIGHT)));

        projectiles.add(projectile);

    }

    public void update() {
        if(!(TimeUtils.timeSinceMillis(lastUpdate) > 500)) {
            return;
        }

        ArrayList<Projectile> projectilesToRemove = new ArrayList<>();

        for (Projectile projectile : projectiles) {

            projectile.update(boardMap.values());

            if(projectile.isShotIsDone()) {
                projectilesToRemove.add(projectile);
                isDoneShooting = true;
            }

        }

        projectiles.removeAll(projectilesToRemove);

        lastUpdate = TimeUtils.millis();

    }

    public boolean isDoneShooting() {
        return isDoneShooting;
    }

    public void drawWinner(SpriteBatch batch) {
        Utils.drawStringCentered(winnerFont, batch, "Spiller " + getGameWinner() + " har vundet!", Settings.GAME_WIDTH/2, Settings.GAME_HEIGHT/2);
    }
}
