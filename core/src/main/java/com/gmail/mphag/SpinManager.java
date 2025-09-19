package com.gmail.mphag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpinManager {

    private final PlayerManager playerManager;
    private final BoardManager boardManager;
    private final ActionManager actionManager;

    private final Random random = new Random();

    private float spinTime;
    private float spinSpeed;

    private boolean hasSpun = false;
    private final int boxWidth = Settings.GAME_WIDTH / 5;
    private final int boxHeight = Settings.GAME_WIDTH / 10;

    private final List<SpinBox> spinBoxes = new ArrayList<>();
    private final List<SpinBox> spinBoxesToRemove = new ArrayList<>();

    private final int boxSpacing = boxWidth / 20;
    private final int maxSpinBoxAmount = 2 + Settings.GAME_WIDTH / (boxWidth + boxSpacing) ;
    private float spinSpeedDecrement;
    private float spinEndPause;

    private SpinBox chosenSpinBox;
    private boolean shouldHide = false;
    private boolean spinTotallyDone = false;

    public SpinManager(PlayerManager playerManager, BoardManager boardManager, ActionManager actionManager) {
        this.playerManager = playerManager;
        this.boardManager = boardManager;
        this.actionManager = actionManager;
        load();
        resetVariables();
        this.hasSpun = false;
    }

    private void resetVariables() {
        this.spinSpeed = 3000;
        this.spinTime = 2000;
        this.spinEndPause = 2000;
        this.spinSpeedDecrement = 1000;

    }

    public void load() {
        for (SpinType value : SpinType.values()) {
            value.load();
        }
    }

    public void update() {
        if(!(Core.GAME_STATE == GameState.SPINNING)) {
            return;
        }

        if(spinTotallyDone) {
            return;
        }

        if(spinBoxes.size() < maxSpinBoxAmount) {
            if(newBoxCanSpawn()) {
                spawnNewBox();
            }
        }

        updateSpinSpeed();

        for (SpinBox spinBox : spinBoxes) {
            spinBox.update(spinSpeed * Gdx.graphics.getDeltaTime());

            if(spinBox.shouldRemove()) {
                spinBoxesToRemove.add(spinBox);
            }

        }

        spinBoxes.removeAll(spinBoxesToRemove);
        spinBoxesToRemove.clear();

    }

    private void updateSpinSpeed() {

        if(spinTotallyDone) {
            return;
        }
        spinTime -= spinSpeedDecrement * Gdx.graphics.getDeltaTime();

        if(spinTime <= 0) {
            if(spinSpeed > 0) {
                spinSpeed -= spinSpeedDecrement * Gdx.graphics.getDeltaTime();
            }

            if(spinSpeed < 0) {
                spinSpeed = 0;

            }
        }

        if(spinSpeed == 0) {
            spinEndPause -= spinSpeedDecrement * Gdx.graphics.getDeltaTime();

            if(spinEndPause <= 0) {
                spinTotallyDone = true;
                finishSpin();
            }
        }
    }

    private void finishSpin() {
        for (SpinBox spinBox : spinBoxes) {
            if(spinBox.getLocation().x <= Settings.GAME_WIDTH/2 && spinBox.getLocation().x + boxWidth >= Settings.GAME_WIDTH/2) {
                chosenSpinBox = spinBox;

                playerManager.getCurrentPlayer().setCurrentSpinType(spinBox.getType());
                actionManager.executeAction();
                shouldHide = true;

                break;
            }
            chosenSpinBox = null;
        }

        if(chosenSpinBox == null) {
            spin();
        }

    }

    private boolean newBoxCanSpawn() {
        return spinBoxes.isEmpty() || spinBoxes.get(spinBoxes.size() - 1).getLocation().x < Settings.GAME_WIDTH - boxSpacing - boxWidth;
    }

    private void spawnNewBox() {

        List<SpinType> values = new ArrayList<>(Arrays.asList(SpinType.values()));

        updateSpinPool(values);

        int i = random.nextInt(0, values.size());

        spinBoxes.add(new SpinBox(values.get(i), boxHeight/2, boxWidth, boxHeight));
    }

    private void updateSpinPool(List<SpinType> values) {
        if(!boardManager.hasAnyDemonsOnBoard()) {
            values.remove(SpinType.MOVE_DEMONS);
        }

        if(!boardManager.canSpawnMoreAngels(playerManager.getCurrentPlayer())) {
            values.remove(SpinType.ADD_ANGEL);
        }

        if(!boardManager.canSpawnMoreDemons(playerManager.getCurrentPlayer())) {
            values.remove(SpinType.ADD_DEMON);
        }

        if(!boardManager.hasAngelsOfPlayer(playerManager.getCurrentPlayer())) {
            values.remove(SpinType.SHOOT_WITH_ANGEL);
        }

        if(!boardManager.hasAngelsOfPlayer(playerManager.getPlayerNotAtTurn())) {
            values.remove(SpinType.REMOVE_ANGEL);
        }
    }

    public void draw(SpriteBatch batch) {

        if(shouldHide) {
            return;
        }

        for (SpinBox spinBox : spinBoxes) {
            spinBox.draw(batch);
        }

    }

    public void drawShapes(ShapeRenderer shapeRenderer) {

        if(shouldHide) {
            return;
        }

        if(Core.GAME_STATE != GameState.SPINNING) {
            return;
        }

        int size = Settings.GAME_WIDTH/100;
        int y = Settings.GAME_HEIGHT/2 - Settings.GAME_HEIGHT/10;
        int x = Settings.GAME_WIDTH/2;

        shapeRenderer.triangle(
            x, y, //1
            x - size, y - size,
            x + size, y - size
        );
    }

    public void spin() {
        Core.GAME_STATE = GameState.SPINNING;
        resetVariables();
        hasSpun = true;
        this.spinTotallyDone = false;
        shouldHide = false;
        this.spinBoxes.clear();
    }


}
