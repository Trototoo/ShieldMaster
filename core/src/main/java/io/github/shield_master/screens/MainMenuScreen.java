package io.github.shield_master.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.shield_master.MainGame;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.GameManager;

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
        playButton.setPosition(Constants.GAME_WIDTH / 2 - playButton.getWidth() / 2, Constants.GAME_HEIGHT / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.buttonPressSound.play(GameManager.getSoundVolume());
                hide();
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        stage.addActor(playButton);

        ImageButton exitButton = new ImageButton(AssetLoader.getExitButtonStyle());
        exitButton.setPosition(Constants.GAME_WIDTH / 2 - exitButton.getWidth() / 2, Constants.GAME_HEIGHT / 2 - exitButton.getHeight() / 2 - 20);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.buttonPressSound.play(GameManager.getSoundVolume());
                game.dispose();
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);

        Skin skin = new Skin(Gdx.files.internal("images/ui/open_source_ui_asset/uiskin.json"));

        Label musicLabel = new Label("Music Volume", new Label.LabelStyle(AssetLoader.fontScore, Color.WHITE));
        musicLabel.setPosition(Constants.GAME_WIDTH / 2 - musicLabel.getWidth() / 2, Constants.GAME_HEIGHT / 2 - musicLabel.getHeight() / 2 - 40);
        stage.addActor(musicLabel);

        Slider musicSlider = new Slider(0, 1, 0.05f, false, skin);
        musicSlider.setSize(playButton.getWidth(), playButton.getHeight());
        musicSlider.setValue(GameManager.getMusicVolume());
        musicSlider.setPosition(Constants.GAME_WIDTH / 2 - musicSlider.getWidth() / 2, Constants.GAME_HEIGHT / 2 - musicSlider.getHeight() / 2 - 55);
        musicSlider.addListener(event -> {
            GameManager.setMusicVolume(musicSlider.getValue());
            AssetLoader.mainMenuMusic.setVolume(GameManager.getMusicVolume());
            return false;
        });
        stage.addActor(musicSlider);

        Label soundLabel = new Label("Sound Volume", new Label.LabelStyle(AssetLoader.fontScore, Color.WHITE));
        soundLabel.setPosition(Constants.GAME_WIDTH / 2 - soundLabel.getWidth() / 2, Constants.GAME_HEIGHT / 2 - soundLabel.getHeight() / 2 - 70);
        stage.addActor(soundLabel);
        Slider soundSlider = new Slider(0, 1, 0.05f, false, skin);
        soundSlider.setSize(playButton.getWidth(), playButton.getHeight());
        soundSlider.setValue(GameManager.getSoundVolume());
        soundSlider.setPosition(Constants.GAME_WIDTH / 2 - soundSlider.getWidth() / 2, Constants.GAME_HEIGHT / 2 - soundSlider.getHeight() / 2 - 85);
        soundSlider.addListener(event -> {
            GameManager.setSoundVolume(soundSlider.getValue());
            return false;
        });
        stage.addActor(soundSlider);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        AssetLoader.mainMenuMusic.play();
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
        AssetLoader.mainMenuMusic.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
