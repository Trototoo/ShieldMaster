package io.github.shield_master.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.shield_master.MainGame;
import io.github.shield_master.actors.Background;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.GameManager;

public class GameOverScreen implements Screen {
    private final Stage stage;

    public GameOverScreen(MainGame game, int finalScore) {
        OrthographicCamera camera = new OrthographicCamera();
        StretchViewport viewport = new StretchViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);
        stage = new Stage(viewport);

        Background background = new Background();
        stage.addActor(background);

        Image highScoreImage = new Image(AssetLoader.highScoreImage);
        highScoreImage.setPosition(Constants.GAME_WIDTH / 2 - highScoreImage.getWidth() / 2, Constants.GAME_HEIGHT - 200);
        stage.addActor(highScoreImage);

        if (finalScore > GameManager.getHighScore()) {
            GameManager.setHighScore(finalScore);
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle(AssetLoader.fontHighscore, Color.WHITE);

        Label scoreLabel = new Label(String.valueOf(finalScore), labelStyle);
        float scoreLabelX = Constants.GAME_WIDTH / 2 - 55;
        float scoreLabelY = Constants.GAME_HEIGHT - 147;
        scoreLabel.setPosition(scoreLabelX, scoreLabelY);
        scoreLabel.setBounds(scoreLabelX, scoreLabelY, 47, 10);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);

        Label highScoreLabel = new Label(String.valueOf(GameManager.getHighScore()), labelStyle);
        float highScoreLabelX = Constants.GAME_WIDTH / 2 + 5;
        float highScoreLabelY = Constants.GAME_HEIGHT - 147;
        highScoreLabel.setPosition(highScoreLabelX, highScoreLabelY);
        highScoreLabel.setBounds(highScoreLabelX, highScoreLabelY, 47, 10);
        highScoreLabel.setAlignment(Align.center);
        stage.addActor(highScoreLabel);

        ImageButton restartButton = new ImageButton(AssetLoader.getRestartButtonStyle());
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.buttonPressSound.play(GameManager.getSoundVolume());
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        restartButton.setPosition(Constants.GAME_WIDTH / 2 - restartButton.getWidth() / 2, 84);
        stage.addActor(restartButton);

        ImageButton mainMenuButton = new ImageButton(AssetLoader.getMenuButtonStyle());
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AssetLoader.buttonPressSound.play(GameManager.getSoundVolume());
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        mainMenuButton.setPosition(Constants.GAME_WIDTH / 2 - mainMenuButton.getWidth() / 2, 62);
        stage.addActor(mainMenuButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        AssetLoader.loseSound.play(GameManager.getSoundVolume());
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
