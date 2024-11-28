package io.github.shield_master.game_elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private final Rectangle rectangle;
    private Direction direction;
    private final Texture playerSheet = new Texture("player_sheet.png");

    private int lives = 5;

    private final Animation<TextureRegion> idleUpAnimation;
    private final Animation<TextureRegion> idleDownAnimation;
    private final Animation<TextureRegion> idleLeftAnimation;
    private final Animation<TextureRegion> idleRightAnimation;

    private Animation<TextureRegion> currentAnimation;

    private float stateTime = 0f;

    public Player(float x, float y) {
        float WIDTH = 16;
        float HEIGHT = 16;
        rectangle = new Rectangle(x- WIDTH /2, y- HEIGHT /2, WIDTH, HEIGHT);
        direction = Direction.UP;

        TextureRegion[] idleUpFrames = new TextureRegion[6];
        TextureRegion[] idleDownFrames = new TextureRegion[6];
        TextureRegion[] idleLeftFrames = new TextureRegion[6];
        TextureRegion[] idleRightFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            TextureRegion[][] playerFrames = TextureRegion.split(playerSheet, 16, 16);
            idleDownFrames[i] = playerFrames[0][i];
            idleLeftFrames[i] = playerFrames[0][i+6];
            idleRightFrames[i] = playerFrames[0][i+12];
            idleUpFrames[i] = playerFrames[0][i+18];
        }

        idleUpAnimation = new Animation<>(0.15f, idleUpFrames);
        idleDownAnimation = new Animation<>(0.15f, idleDownFrames);
        idleLeftAnimation = new Animation<>(0.15f, idleLeftFrames);
        idleRightAnimation = new Animation<>(0.15f, idleRightFrames);

        currentAnimation = idleUpAnimation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Direction getDirection() {
        return direction;
    }

    public void draw(SpriteBatch batch, float delta) {
        stateTime += delta;
        batch.draw(currentAnimation.getKeyFrame(stateTime, true), rectangle.x, rectangle.y);
    }

    public void turnRight() {
        direction = Direction.RIGHT;
        currentAnimation = idleRightAnimation;
    }

    public void turnLeft() {
        direction = Direction.LEFT;
        currentAnimation = idleLeftAnimation;
    }

    public void turnUp() {
        direction = Direction.UP;
        currentAnimation = idleUpAnimation;
    }

    public void turnDown() {
        direction = Direction.DOWN;
        currentAnimation = idleDownAnimation;
    }

    public void loseLife() {
        if (lives > 0)
            lives--;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public void dispose() {
        playerSheet.dispose();
    }
}
