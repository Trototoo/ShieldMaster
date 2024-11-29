package io.github.shield_master.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.shield_master.actors.Background;
import io.github.shield_master.actors.Player;
import io.github.shield_master.actors.projectiles.Projectile;
import io.github.shield_master.actors.projectiles.RegularProjectile;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public class GameScreen implements Screen {
    private final Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private Player player;

    private Label scoreLabel;
    private int score;
    private float spawnTimer;

    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, camera);
        stage = new Stage(viewport);

        AssetLoader.load();

        Background background = new Background();
        stage.addActor(background);

        player = new Player();
        stage.addActor(player);

        Skin skin = new Skin(Gdx.files.internal("images/ui_default/uiskin.json"));
        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(20, Constants.GAME_HEIGHT - 30);
        stage.addActor(scoreLabel);

        score = 0;

        Gdx.input.setInputProcessor(stage);
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

        spawnTimer += delta;
        if (spawnTimer >= 1) {
            if (Math.random() < 0.3) {
                spawnProjectile();
                spawnTimer = 0;
            }
        }

        handleInput();
        checkCollisions();
        stage.act(delta);
        stage.draw();
    }

    private void handleInput() {
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
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Projectile) {
                Projectile projectile = (Projectile) actor;

                if (!projectile.isActive()) continue;

//                if (player.getShi)

            }
        }
    }

    private void spawnProjectile() {
        Direction direction = Direction.values()[(int) (Math.random() * Direction.values().length)];

        Projectile projectile;
        projectile = new RegularProjectile(AssetLoader.regularProjectileTexture, direction);
        stage.addActor(projectile);
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
        AssetLoader.dispose();
    }
}
