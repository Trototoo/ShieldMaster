package io.github.shield_master.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    private float stateTime;
    private Direction currentDirection;
    public int lives = Constants.PLAYER_LIVES;
    private final Rectangle shieldRectangle;
    private final Map<Direction, Animation<TextureRegion>> playerAnimations;
    private Animation<TextureRegion> currentAnimation;

    public Player() {
        stateTime = 0f;
        currentDirection = Direction.UP;

        shieldRectangle = new Rectangle();
        updateShieldPosition();

        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT / 2 - getHeight() / 2);

        playerAnimations = new HashMap<>();
        playerAnimations.put(Direction.UP, AssetLoader.playerUpAnimation);
        playerAnimations.put(Direction.DOWN, AssetLoader.playerDownAnimation);
        playerAnimations.put(Direction.LEFT, AssetLoader.playerLeftAnimation);
        playerAnimations.put(Direction.RIGHT, AssetLoader.playerRightAnimation);

        setCurrentAnimation();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

        updateShieldPosition();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawPlayer(batch);
        drawShield(batch);
    }

    private void drawPlayer(Batch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    private void drawShield(Batch batch) {
        TextureRegion shieldTexture = switch (currentDirection) {
            case UP, DOWN -> AssetLoader.shieldTextureHorizontal;
            case RIGHT, LEFT -> AssetLoader.shieldTextureVertical;
        };

        batch.draw(
            shieldTexture,
            shieldRectangle.x,
            shieldRectangle.y,
            shieldRectangle.width,
            shieldRectangle.height
        );
    }

    private void updateShieldPosition() {
        final float x = getX();
        final float y = getY();
        final float width = getWidth();
        final float height = getHeight();

        switch (currentDirection) {
            case UP:
                shieldRectangle.set(
                    x - Constants.SHIELD_SIZE_OFFSET,
                    y + height + Constants.SHIELD_PADDING_OFFSET,
                    Constants.SHIELD_LONG_SIDE,
                    Constants.SHIELD_SHORT_SIDE
                );
                break;
            case DOWN:
                shieldRectangle.set(
                    x - Constants.SHIELD_SIZE_OFFSET,
                    y - Constants.SHIELD_PADDING_OFFSET - Constants.SHIELD_SHORT_SIDE,
                    Constants.SHIELD_LONG_SIDE,
                    Constants.SHIELD_SHORT_SIDE
                );
                break;
            case LEFT:
                shieldRectangle.set(
                    x - Constants.SHIELD_PADDING_OFFSET - Constants.SHIELD_SHORT_SIDE,
                    y - Constants.SHIELD_SIZE_OFFSET,
                    Constants.SHIELD_SHORT_SIDE,
                    Constants.SHIELD_LONG_SIDE
                );
                break;
            case RIGHT:
                shieldRectangle.set(
                    x + width + Constants.SHIELD_PADDING_OFFSET,
                    y - Constants.SHIELD_SIZE_OFFSET,
                    Constants.SHIELD_SHORT_SIDE,
                    Constants.SHIELD_LONG_SIDE
                );
                break;
        }
    }

    public Rectangle getShieldBoundingRectangle() {
        return shieldRectangle;
    }

    public void rotate(Direction direction) {
        currentDirection = direction;
        updateShieldPosition();
        setCurrentAnimation();
    }

    private void setCurrentAnimation() {
        currentAnimation = playerAnimations.get(currentDirection);
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
