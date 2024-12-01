package io.github.shield_master.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.shield_master.MainGame;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;

public class MainMenuScreen implements Screen {
    private final Stage stage;

    public MainMenuScreen(MainGame game) {

        OrthographicCamera camera = new OrthographicCamera();
        StretchViewport viewport = new StretchViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);
        stage = new Stage(viewport);

        Image background = AssetLoader.mainMenuBackground;
        background.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        stage.addActor(background);

        ImageButton playButton = new ImageButton(AssetLoader.getPlayButtonStyle());
        playButton.setPosition(Constants.GAME_WIDTH / 2 - playButton.getWidth() / 2, Constants.GAME_HEIGHT / 2 - 50);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        stage.addActor(playButton);

        ImageButton exitButton = new ImageButton(AssetLoader.getExitButtonStyle());
        exitButton.setPosition(Constants.GAME_WIDTH / 2 - exitButton.getWidth() / 2, Constants.GAME_HEIGHT / 2 - exitButton.getHeight() / 2 - 70);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dispose();
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
