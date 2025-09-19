package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ActionManager {

    private final BitmapFont font;
    private final BitmapFont font2;

    private final PlayerManager playerManager;
    private final BoardManager boardManager;
    private final QuestionManager questionManager;

    private boolean isAwaitingInput = false;

    private boolean isDrawingError = false;
    private long drawingStartTime;
    private long errorMessageTime = 2000;
    private String errorText = "";
    private boolean isShooting = false;
    private boolean questionHasBeenAsked = false;

    public ActionManager(PlayerManager playerManager, BoardManager boardManager, QuestionManager questionManager) {
        this.playerManager = playerManager;
        this.boardManager = boardManager;
        this.questionManager = questionManager;

        this.font = new BitmapFont(Gdx.files.internal("error.fnt"));
        this.font2 = new BitmapFont(Gdx.files.internal("price.fnt"));

    }

    public void update() {
        handleHighlighting();

        if (isDrawingError && TimeUtils.timeSinceMillis(drawingStartTime) > errorMessageTime) {
            stopErrorMessage();
        }

        if (isShooting) {
            if (boardManager.isDoneShooting()) {
                isShooting = false;
                finishAction();
            }
        }

        if (questionManager.isQuestionDone() && questionHasBeenAsked) {
            if (!questionManager.isWaitingForFreePick()) {
                questionHasBeenAsked = false;
                finishAction();
            } else {
                isAwaitingInput = true;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        if (isDrawingError) {
            Utils.drawStringCentered(font, batch, errorText, Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        }

        if (questionManager.isWaitingForFreePick()) {
            Utils.drawStringCentered(font2, batch, "KORREKT! VÆLG PRÆMIE:", Settings.GAME_WIDTH / 2, (int) (Settings.GAME_HEIGHT - Settings.TILE_HEIGHT));

            FreePickLocation[] values = FreePickLocation.values();
            List<SpinType> spinTypes = new ArrayList<>(Arrays.asList(SpinType.values()));
            spinTypes.remove(SpinType.QUESTION);

            for (int i = 0; i < spinTypes.size(); i++) {
                FreePickLocation gridLocation = values[i];

                int width = 0;

                if (i == 2 || i == 3) {
                    width = -spinTypes.get(i).getTexture().getWidth();
                } else if (i == 4 || i == 5) {
                    width = -spinTypes.get(i).getTexture().getWidth() / 2;
                }

                    batch.draw(spinTypes.get(i).getTexture(), gridLocation.getGridPoint2().x + width, gridLocation.getGridPoint2().y);
            }
        }

        batch.end();

    }

    public void drawShapes(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (questionManager.isWaitingForFreePick()) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(
                0 + Settings.TILE_WIDTH / 2,
                0 + Settings.TILE_HEIGHT / 2,
                Settings.GAME_WIDTH - Settings.TILE_WIDTH,
                Settings.GAME_HEIGHT - Settings.TILE_HEIGHT);
        }
        shapeRenderer.end();
    }

    private void handleInput() {

        if (!isAwaitingInput) {
            return;
        }

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        int newX = (int) (mouseX / Settings.TILE_WIDTH);
        int newY = (int) (Settings.TILE_ROWS - (mouseY / Settings.TILE_HEIGHT));

        BoardTile tile = boardManager.getTile(newX, newY);

        if (questionManager.isWaitingForFreePick()) {
            handleFreePickInput();
            return;
        }

        switch (playerManager.getCurrentPlayer().getCurrentSpinType()) {
            case ADD_DEMON:
                if (tile.isDemonTile() && !tile.belongsTo(playerManager.getCurrentPlayer()) && tile.isEmpty()) {
                    tile.spawnDemon();
                    finishAction();
                }
                break;
            case ADD_ANGEL:
                if (tile.isAngelTile() && tile.belongsTo(playerManager.getCurrentPlayer()) && tile.isEmpty()) {
                    tile.spawnAngel();
                    finishAction();
                }
                break;
            case REMOVE_ANGEL:
                if (tile.getTileOccupant() == TileOccupant.ANGEL && !tile.belongsTo(playerManager.getCurrentPlayer())) {
                    tile.killAngel();
                    finishAction();
                }
                break;
            case SHOOT_WITH_ANGEL:
                if (tile.getTileOccupant() == TileOccupant.ANGEL && tile.belongsTo(playerManager.getCurrentPlayer()) && !isShooting) {
                    boardManager.shootWithAngel(tile);
                    this.isShooting = true;
                }
                break;
            default:
                break;
        }
    }

    private void handleFreePickInput() {
        //TODO
    }

    public void input() {
        if (!isAwaitingInput) {
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        handleInput();

    }

    private void handleHighlighting() {
        boardManager.resetHighlighting();

        if (playerManager.getCurrentPlayer().getCurrentSpinType() == null) {
            return;
        }

        switch (playerManager.getCurrentPlayer().getCurrentSpinType()) {
            case ADD_DEMON:
                boardManager.highlightDemonTiles(playerManager.getCurrentPlayer());
                break;
            case ADD_ANGEL:
                boardManager.highlightAngelTiles(playerManager.getCurrentPlayer());
                break;
            case REMOVE_ANGEL:
                boardManager.highlightOpponentAngels(playerManager.getCurrentPlayer());
                break;
            case SHOOT_WITH_ANGEL:
                boardManager.highlightAngels(playerManager.getCurrentPlayer());
                break;
            default:
                break;
        }
    }

    public void executeAction() {
        Player currentPlayer = playerManager.getCurrentPlayer();
        SpinType currentSpinType = currentPlayer.getCurrentSpinType();

        switch (currentSpinType) {
            case NOTHING:
                finishAction();
                //displayActionMessage("You have been skipped!");
                break;
            case QUESTION:
                if (questionHasBeenAsked) {
                    return;
                }
                questionHasBeenAsked = true;
                questionManager.askQuestion();
                break;

            case MOVE_DEMONS:

                if (!boardManager.hasAnyDemonsOnBoard()) {
                    displayActionMessage("No demons could move...");
                    break;
                }

                if (!isDrawingError) {
                    boardManager.moveAllDemons();
                }
                displayActionMessage("All demons move once...");

                break;

            //Disse skal alle sammen vente på player input, som bliver støttet i input og update ift hvilken en af dem det er:
            case ADD_DEMON:

                if (boardManager.canSpawnMoreDemons(currentPlayer)) {
                    isAwaitingInput = true;
                } else {

                    displayActionMessage("All demon tiles are occupied!");

                }
                break;

            case ADD_ANGEL:

                if (boardManager.canSpawnMoreAngels(currentPlayer)) {
                    isAwaitingInput = true;
                } else {

                    displayActionMessage("All angel tiles are full...");

                }
                break;

            case SHOOT_WITH_ANGEL:
                if (boardManager.hasAngelsOfPlayer(currentPlayer)) {
                    isAwaitingInput = true;
                } else {
                    displayActionMessage("No angels to shoot...");
                }

                break;

            case REMOVE_ANGEL:
                if (boardManager.hasAngelsOfPlayer(playerManager.getPlayerNotAtTurn())) {
                    isAwaitingInput = true;
                } else {
                    displayActionMessage("No angels to remove...");
                }

                break;

            default:
                break;
        }
    }

    private void displayActionMessage(String s) {
        if (isDrawingError) {
            return;
        }
        isDrawingError = true;
        errorText = s;
        drawingStartTime = TimeUtils.millis();
    }

    private void stopErrorMessage() {
        isDrawingError = false;
        finishAction();
    }

    private void finishAction() {
        this.isAwaitingInput = false;
        this.questionHasBeenAsked = false;
        playerManager.switchTurn();
        Core.GAME_STATE = GameState.WAITING_FOR_ROLL;
        playerManager.getCurrentPlayer().resetCurrentSpinType();
    }

}
