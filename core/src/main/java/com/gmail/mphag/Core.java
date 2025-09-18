package com.gmail.mphag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

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

        input();
        logic();
        draw();

    }

    public void input() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && GAME_STATE == GameState.WAITING_FOR_ROLL) {
            spinManager.spin();
        }

        actionManager.input();
    }

    public void logic() {

        spinManager.update();
        actionManager.update();
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
        actionManager.draw(batch);
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
