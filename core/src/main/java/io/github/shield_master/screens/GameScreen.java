package io.github.shield_master.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.shield_master.MainGame;
import io.github.shield_master.actors.Background;
import io.github.shield_master.actors.Player;
import io.github.shield_master.actors.projectiles.Projectile;
import io.github.shield_master.actors.projectiles.ProjectileFactory;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public class GameScreen implements Screen {
    private final MainGame game;

    private final Stage stage;
    private final Viewport viewport;

    private final Player player;

    private final Label scoreLabel;
    private int score;
    private float spawnTimer;

    private boolean isPaused;
    private Table pauseMenu;

    private Group projectiles;

    public GameScreen(MainGame game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);
        stage = new Stage(viewport);

        AssetLoader.load();

        Background background = new Background();
        stage.addActor(background);

        player = new Player();
        stage.addActor(player);

        scoreLabel = new Label("Score: 0", new Label.LabelStyle(AssetLoader.fontScore, Color.WHITE));
        scoreLabel.setPosition(20, Constants.GAME_HEIGHT - 30);
        stage.addActor(scoreLabel);

        score = 0;

        projectiles = new Group();
        stage.addActor(projectiles);

        setupPauseMenu();

        Gdx.input.setInputProcessor(stage);
    }

    private void setupPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        pauseMenu.setVisible(false);

        pauseMenu.setBackground(AssetLoader.createTransparentBackground(0.5f));

        ImageButton continueButton = new ImageButton(AssetLoader.getContinueButtonStyle());
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) return;
                isPaused = false;
                pauseMenu.setVisible(false);
            }
        });

        ImageButton mainMenuButton = new ImageButton(AssetLoader.getMenuButtonStyle());
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) return;
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        pauseMenu.add(continueButton).pad(10).row();
        pauseMenu.add(mainMenuButton).pad(10);
        stage.addActor(pauseMenu);
    }

    @Override
    public void show() {
        player.rotate(Direction.UP);
        score = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        if (isPaused) {
            stage.draw();
            return;
        }

        spawnTimer += delta;
        if (spawnTimer >= Constants.PROJECTILE_SPAWN_INTERVAL) {
            if (Math.random() < 0.3) {
                ProjectileFactory.generateProjectile(projectiles);
                spawnTimer = 0;
            }
        }

        checkCollisions();
        stage.act(delta);
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
            pauseMenu.setVisible(isPaused);
        }
        if (isPaused) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.rotate(Direction.UP);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.rotate(Direction.DOWN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.rotate(Direction.LEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.rotate(Direction.RIGHT);
        }
    }

    private void checkCollisions() {
        for (Actor actor : projectiles.getChildren()) {
            if (actor instanceof Projectile projectile) {
                if (Intersector.overlaps(player.getShieldBoundingRectangle(), projectile.getBounds())) {
                    projectile.deactivate();
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
                if (Intersector.overlaps(player.getBoundingRectangle(), projectile.getBounds())) {
                    player.lives--;
                    projectile.deactivate();
                    if (player.lives == 0) {
                        game.setScreen(new GameOverScreen(game, score));
                        dispose();
                    }
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
