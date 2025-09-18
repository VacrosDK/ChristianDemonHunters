package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class ActionManager {

    private final BitmapFont font;

    private final PlayerManager playerManager;
    private final BoardManager boardManager;
    private final QuestionManager questionManager;

    private boolean executingAction = false;
    private boolean isAwaitingInput = false;

    private boolean isDrawingError = false;
    private long drawingStartTime;
    private long errorMessageTime = 2000;
    private String errorText = "";

    public ActionManager(PlayerManager playerManager, BoardManager boardManager, QuestionManager questionManager) {
        this.playerManager = playerManager;
        this.boardManager = boardManager;
        this.questionManager = questionManager;

        this.font = new BitmapFont(Gdx.files.internal("error.fnt"));
        System.out.println("Font:");
        System.out.println(font);
    }

    public void update() {
        handleHighlighting();

        if (isDrawingError && TimeUtils.timeSinceMillis(drawingStartTime) > errorMessageTime) {
            stopErrorMessage();
        }

    }

    public void draw(SpriteBatch batch) {

        if (isDrawingError) {
            Utils.drawStringCentered(font, batch, errorText, Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        }
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

        switch (playerManager.getCurrentPlayer().getCurrentSpinType()) {
            case ADD_DEMON:
                //Await input
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
                //Await input
                break;
            default:
                break;
        }
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
        this.executingAction = true;
        Player currentPlayer = playerManager.getCurrentPlayer();
        SpinType currentSpinType = currentPlayer.getCurrentSpinType();

        switch (currentSpinType) {
            case NOTHING:
                displayActionMessage("You have been skipped!");
                break;
            case QUESTION:
                //TODO
                finishAction();
                break;

            case MOVE_DEMONS:

                if(!boardManager.hasAnyDemonsOnBoard()) {
                    displayActionMessage("No demons could move...");
                    break;
                }

                if(!isDrawingError) {
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

                    displayActionMessage("All angel tiles are full!");

                }
                break;

            case SHOOT_WITH_ANGEL:
                finishAction();
                //TODO
            case REMOVE_ANGEL:
                finishAction();
                //TODO
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
        this.executingAction = false;
        this.isAwaitingInput = false;
        playerManager.switchTurn();
        Core.GAME_STATE = GameState.WAITING_FOR_ROLL;
        playerManager.getCurrentPlayer().resetCurrentSpinType();
    }

}
