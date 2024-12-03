package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public abstract class Projectile extends Actor {
    protected TextureRegion textureRegion;
    protected Vector2 velocity;
    protected boolean isActive;
    protected Direction direction;
    private Rectangle bounds;
    private Vector2 standardVelocity;
    private final float PROJECTILE_LONG_SIDE = 13f;
    private final float PROJECTILE_SHORT_SIDE = 7f;

    public Projectile(TextureRegion textureRegion, Direction direction) {
        this.textureRegion = textureRegion;

        this.isActive = true;
        this.direction = direction;

        switch (direction) {
            case UP -> {
                standardVelocity = new Vector2(0, 10 * Constants.PROJECTILE_SPEED);
                setSize(PROJECTILE_SHORT_SIDE, PROJECTILE_LONG_SIDE);
                setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, 0);
            }
            case DOWN -> {
                standardVelocity = new Vector2(0, -10 * Constants.PROJECTILE_SPEED);
                setSize(PROJECTILE_SHORT_SIDE, PROJECTILE_LONG_SIDE);
                setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT - getHeight());
            }
            case LEFT -> {
                standardVelocity = new Vector2(-10 * Constants.PROJECTILE_SPEED, 0);
                setSize(PROJECTILE_LONG_SIDE, PROJECTILE_SHORT_SIDE);
                setPosition(Constants.GAME_WIDTH - getWidth(), Constants.GAME_HEIGHT / 2 - getHeight() / 2);
            }
            case RIGHT -> {
                standardVelocity = new Vector2(10 * Constants.PROJECTILE_SPEED, 0);
                setSize(PROJECTILE_LONG_SIDE, PROJECTILE_SHORT_SIDE);
                setPosition(0, Constants.GAME_HEIGHT / 2 - getHeight() / 2);
            }
        }
        velocity = standardVelocity.cpy();
        updateBounds();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
        updateBounds();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isActive) {
            float rotation = switch (direction) {
                case RIGHT -> 0;
                case UP -> 90;
                case LEFT -> 180;
                case DOWN -> 270;
            };

            float offsetX = (direction == Direction.UP || direction == Direction.DOWN) ? (getWidth() - getHeight()) / 2 : 0;
            float offsetY = (direction == Direction.UP || direction == Direction.DOWN) ? (getHeight() - getWidth()) / 2 : 0;

            batch.draw(textureRegion, getX() + offsetX, getY() + offsetY, PROJECTILE_LONG_SIDE / 2, PROJECTILE_SHORT_SIDE / 2, PROJECTILE_LONG_SIDE, PROJECTILE_SHORT_SIDE, 1, 1, rotation);
        }
    }

    public void deactivate() {
        isActive = false;
        remove();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    void updateBounds() {
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
