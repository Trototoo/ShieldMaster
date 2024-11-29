package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public abstract class Projectile extends Actor {
    protected TextureRegion textureRegion;
    protected Vector2 velocity;
    protected boolean isActive;
    private Direction direction;

    public Projectile(TextureRegion textureRegion, Direction direction) {
        this.textureRegion = textureRegion;

        this.isActive = true;
        this.direction = direction;

        if (direction == Direction.UP) {
            velocity = new Vector2(0, 10);
            setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, 0);
        } else if (direction == Direction.DOWN) {
            velocity = new Vector2(0, -10);
            setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT - getHeight());
        } else if (direction == Direction.LEFT) {
            velocity = new Vector2(-10, 0);
            setPosition(Constants.GAME_WIDTH - getWidth(), Constants.GAME_HEIGHT / 2 - getHeight() / 2);
        } else if (direction == Direction.RIGHT) {
            velocity = new Vector2(10, 0);
            setPosition(0, Constants.GAME_HEIGHT / 2 - getHeight() / 2);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isActive) {
            batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void deactivate() {
        isActive = false;
        remove();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    };
}
