package io.github.shield_master;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.shield_master.game_elements.Direction;
import io.github.shield_master.game_elements.GameOverScreen;
import io.github.shield_master.game_elements.Player;
import io.github.shield_master.game_elements.Shield;
import io.github.shield_master.game_elements.projectile.ProjectileInterface;
import io.github.shield_master.game_elements.projectile.RegularProjectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Preferences preferences = Gdx.app.getPreferences("ShieldMaster");
    private Texture backgroundTexture;
    private Texture shieldTexture;
    private Player player;
    private Shield shield;
    private Viewport viewport;
    private List<ProjectileInterface> projectilesList;
    private Random random;
    private int frameCounter;
    private Texture deathScreenTexture;
    private Texture restartButtonTexture;
    private Texture menuButtonTexture;
    private boolean isPlayerDead;
    private int score = 0;

    private final float GAME_WIDTH = 456;
    private final float GAME_HEIGHT = 256;
    private final int MINIMAL_ARROW_COOLDOWN = 60;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Prepare your screen here.
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);

        batch = new SpriteBatch();
        backgroundTexture = new Texture("tile.png");

        player = new Player(GAME_WIDTH/2, GAME_HEIGHT/2);
        shield = new Shield(player);
        projectilesList = new ArrayList<>();
        random = new Random();
        frameCounter = 0;

        deathScreenTexture = new Texture("ui/death_screen.png");
        restartButtonTexture = new Texture("ui/try_again.png");
        menuButtonTexture = new Texture("ui/exit_button.png");
        isPlayerDead = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!player.isAlive()) {
            batch.begin();
            renderDeathScreen();
            batch.end();
            return;
        }

        handleInput();
        shield.updatePosition();
        shieldTexture = shield.getTexture();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        drawTiledBackground();
        drawPlayerAndShield();
        drawProjectiles();
        batch.end();

        updateProjectiles(delta);
        frameCounter++;
        if (frameCounter >= MINIMAL_ARROW_COOLDOWN) {
            if (random.nextBoolean()) {
                frameCounter = 0;
                spawnArrow();
            }
        }
    }

    private void drawTiledBackground() {
        int textureWidth = backgroundTexture.getWidth();
        int textureHeight = backgroundTexture.getHeight();

        for (int x = 0; x < GAME_WIDTH; x += textureWidth) {
            for (int y = 0; y < GAME_HEIGHT; y += textureHeight) {
                batch.draw(backgroundTexture, x, y);
            }
        }
    }

    private void drawPlayerAndShield() {
        player.draw(batch, Gdx.graphics.getDeltaTime());
        batch.draw(shieldTexture, shield.getRectangle().x, shield.getRectangle().y);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.turnUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.turnDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.turnLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.turnRight();
        }
    }

    private void renderDeathScreen() {
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(deathScreenTexture, 0, 0, deathScreenTexture.getWidth(), deathScreenTexture.getHeight());
        batch.setColor(1, 1, 1, 1);

        batch.draw(restartButtonTexture, 100, 100);
        batch.draw(menuButtonTexture, 200, 100);

        BitmapFont font = new BitmapFont();
        font.draw(batch, String.valueOf(preferences.getInteger("highscore", 0)), 100, 200);
        font.draw(batch, String.valueOf(score), 100, 150);
    }

    private void drawProjectiles() {
        for (ProjectileInterface projectile : projectilesList) {
            projectile.draw(batch);
        }
    }

    private void updateProjectiles(float delta) {
        Iterator<ProjectileInterface> iterator = projectilesList.iterator();
        while (iterator.hasNext()) {
            ProjectileInterface projectile = iterator.next();
            projectile.move(delta);
            if (projectile.collidesWith(shield.getRectangle())) {
                System.out.println("Collided with shield");
                score += 10;
                iterator.remove();
            } else if (projectile.collidesWith(player.getRectangle())) {
                System.out.println("Collided with player");
                player.loseLife();
                if (!player.isAlive()) {
                    if (score > preferences.getInteger("highscore", 0)) {
                        preferences.putInteger("highscore", score);
                        preferences.flush();
                    }
                    game.setScreen(new GameOverScreen(game, score));
                }
                iterator.remove();
            }
        }
    }

    private void spawnArrow() {
        float x = 0, y = 0;
        Direction direction = Direction.UP;

        switch (random.nextInt(4)) {
            case 0: // Top
                x = GAME_WIDTH / 2 - RegularProjectile.WIDTH / 2;
                y = GAME_HEIGHT;
                direction = Direction.DOWN;
                break;
            case 1: // Bottom
                x = GAME_WIDTH / 2 - RegularProjectile.WIDTH / 2;
                y = 0;
                direction = Direction.UP;
                break;
            case 2: // Left
                x = 0;
                y = GAME_HEIGHT / 2 - RegularProjectile.HEIGHT / 2;
                direction = Direction.RIGHT;
                break;
            case 3: // Right
                x = GAME_WIDTH;
                y = GAME_HEIGHT / 2 - RegularProjectile.HEIGHT / 2;
                direction = Direction.LEFT;
                break;
        }

        projectilesList.add(new RegularProjectile(x, y, 100, direction));
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        batch.dispose();
        backgroundTexture.dispose();
        player.dispose();
        shieldTexture.dispose();
    }
}
