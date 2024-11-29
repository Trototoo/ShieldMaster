package io.github.shield_master.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public class Player extends Actor {
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private Direction currentDirection = Direction.UP;

    public Player() {
        currentAnimation = AssetLoader.playerUpAnimation;
        stateTime = 0f;
        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT / 2 - getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void rotateShield(Direction direction) {
        if (currentDirection != direction) {
            currentDirection = direction;
            stateTime = 0f;
        }

        switch (direction) {
            case UP:
                currentAnimation = AssetLoader.playerUpAnimation;
                break;
            case DOWN:
                currentAnimation = AssetLoader.playerDownAnimation;
                break;
            case LEFT:
                currentAnimation = AssetLoader.playerLeftAnimation;
                break;
            case RIGHT:
                currentAnimation = AssetLoader.playerRightAnimation;
                break;
        }
    }
}
