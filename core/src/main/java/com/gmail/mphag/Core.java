package com.gmail.mphag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.mphag.board.BoardManager;
import com.gmail.mphag.managers.ActionManager;
import com.gmail.mphag.managers.PlayerManager;
import com.gmail.mphag.question.QuestionManager;
import com.gmail.mphag.spin.SpinManager;
import com.gmail.mphag.type.ColorType;
import com.gmail.mphag.type.GameState;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends ApplicationAdapter {

    public static GameState GAME_STATE;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private BoardManager boardManager;
    private SpinManager spinManager;
    private ActionManager actionManager;
    private QuestionManager questionManager;

    private PlayerManager playerManager;

    @Override
    public void create() {
        System.out.println(Settings.GAME_WIDTH);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        playerManager = new PlayerManager();
        boardManager = new BoardManager(playerManager.getPlayerOne(), playerManager.getPlayerTwo());
        questionManager = new QuestionManager();
        actionManager = new ActionManager(playerManager, boardManager, questionManager);

        spinManager = new SpinManager(playerManager, boardManager, actionManager);

        GAME_STATE = GameState.WAITING_FOR_ROLL;
    }

    @Override
    public void render() {

        if(boardManager.gameIsOver()) {
            handleFinishScreen();
            return;
        }

        input();
        logic();
        draw();

    }

    private void handleFinishScreen() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if(boardManager.getGameWinner() == 1) {
            shapeRenderer.setColor(ColorType.PLAYER_BASE_TWO.getColor());
        } else {
            shapeRenderer.setColor(ColorType.PLAYER_BASE_ONE.getColor());
        }

        shapeRenderer.rect(0,0,Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        shapeRenderer.end();

        batch.begin();

        boardManager.drawWinner(batch);

        batch.end();

    }

    public void input() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && GAME_STATE == GameState.WAITING_FOR_ROLL) {
            spinManager.spin();
        }

        actionManager.input();
        questionManager.input();
    }

    public void logic() {
        boardManager.update();
        spinManager.update();
        actionManager.update();
        questionManager.logic();
    }

    private void draw() {
        ScreenUtils.clear(0.2f, 0.2f, 0.3f, 1f);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        spinManager.drawShapes(shapeRenderer);
        shapeRenderer.end();

        boardManager.drawShapes(shapeRenderer);

        batch.begin();
        boardManager.drawImages(batch);
        playerManager.draw(batch);
        spinManager.draw(batch);


        batch.end();

        questionManager.drawShapes(shapeRenderer);
        questionManager.drawImages(batch);

        actionManager.drawShapes(shapeRenderer);
        actionManager.draw(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
