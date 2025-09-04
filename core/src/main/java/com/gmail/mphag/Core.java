package com.gmail.mphag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends ApplicationAdapter {

    public static GameState GAME_STATE;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private BoardManager boardManager;
    private TurnManager turnManager;
    private SpinManager rollManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        boardManager = new BoardManager();
        turnManager = new TurnManager();
        rollManager = new SpinManager();

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
            rollManager.spin();
        }
    }

    public void logic() {
        turnManager.update();
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        boardManager.draw(shapeRenderer);
        batch.begin();
        turnManager.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
