package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public class ReverseProjectile extends Projectile {

    private Vector2 startingPoint;
    private final Vector2 currentPoint = new Vector2(getX(), getY());
    private float angle;
    private float startingAngle;
    private float endingAngle;
    private float angleDelta = 3f;

    private final int ROTATION_RADIUS = 70;
    private boolean isCircular = false;
    private final boolean clockwise;

    private final float SIZE = 8;
    private float textureAngle = 0;

    public ReverseProjectile(TextureRegion textureRegion, Direction direction) {
        super(textureRegion, direction);

        clockwise = Math.random() < 0.5;

        setPointsAndRotationDirection();
        angle = startingAngle;
        setWidth(SIZE);
        setHeight(SIZE);
        updateBounds();
    }

    @Override
    public void act(float delta) {
        currentPoint.set(getX() + getOriginX(), getY() + getOriginY());
        if (currentPoint.epsilonEquals(startingPoint, 5f) && !isCircular) {
            isCircular = true;
        } else if (Math.abs(angle - endingAngle) < Math.PI/360 * Math.abs(angleDelta) && isCircular) {
            isCircular = false;
            this.velocity = this.velocity.scl(-1);
            fixPosition();
        }
        if (!isCircular)
            super.act(delta);
        else
            moveInCircle(delta);
    }

    private void fixPosition() {
        switch (direction) {
            case UP -> setDownPosition();
            case DOWN -> setUpPosition();
            case LEFT -> setRightPosition();
            case RIGHT -> setLeftPosition();
        }
        updateBounds();
    }

    private void setDownPosition() {
        setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT / 2 + ROTATION_RADIUS);
    }

    private void setUpPosition() {
        setPosition(Constants.GAME_WIDTH / 2 - getWidth() / 2, Constants.GAME_HEIGHT / 2 - ROTATION_RADIUS);
    }

    private void setRightPosition() {
        setPosition(Constants.GAME_WIDTH / 2 - getWidth() - ROTATION_RADIUS, Constants.GAME_HEIGHT / 2 - getHeight() / 2);
    }

    private void setLeftPosition() {
        setPosition(Constants.GAME_WIDTH / 2 + ROTATION_RADIUS, Constants.GAME_HEIGHT / 2 - getHeight() / 2);
    }

    private void moveInCircle(float delta) {
        float originX = Constants.GAME_WIDTH / 2;
        float originY = Constants.GAME_HEIGHT / 2;

        angle += angleDelta * delta;

        float x_offset = ROTATION_RADIUS * (float) Math.cos(angle);
        float y_offset = ROTATION_RADIUS * (float) Math.sin(angle);

        float x = originX + x_offset;
        float y = originY + y_offset;
        setPosition(x, y);
        updateBounds();
    }

    private void setPointsAndRotationDirection() {
        switch (direction) {
            case UP -> {
                startingPoint = new Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2 - ROTATION_RADIUS);
                startingAngle = (float) Math.PI * 3 / 2;
                if (clockwise)
                    endingAngle = (float) Math.PI * 5 / 2;
                else {
                    endingAngle = (float) Math.PI / 2;
                    angleDelta *= -1;
                }
            }
            case DOWN -> {
                startingPoint = new Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2 + ROTATION_RADIUS);
                startingAngle = (float) Math.PI / 2;
                if (clockwise)
                    endingAngle = (float) Math.PI * 3 / 2;
                else {
                    endingAngle = (float) -Math.PI / 2;
                    angleDelta *= -1;
                }
            }
            case LEFT -> {
                startingPoint = new Vector2(Constants.GAME_WIDTH / 2 + ROTATION_RADIUS, Constants.GAME_HEIGHT / 2);
                startingAngle = 0;
                if (clockwise)
                    endingAngle = (float) Math.PI;
                else {
                    endingAngle = (float) -Math.PI;
                    angleDelta *= -1;
                }
            }
            case RIGHT -> {
                startingPoint = new Vector2(Constants.GAME_WIDTH / 2 - ROTATION_RADIUS, Constants.GAME_HEIGHT / 2);
                startingAngle = (float) Math.PI;
                if (clockwise)
                    endingAngle = (float) Math.PI * 2;
                else {
                    endingAngle = 0;
                    angleDelta *= -1;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isActive) {
            float offsetX = (direction == Direction.UP || direction == Direction.DOWN) ? (getWidth() - getHeight()) / 2 : 0;
            float offsetY = (direction == Direction.UP || direction == Direction.DOWN) ? (getHeight() - getWidth()) / 2 : 0;

            batch.draw(textureRegion, getX() + offsetX, getY() + offsetY, SIZE / 2, SIZE / 2, SIZE, SIZE, 1, 1, textureAngle);
            textureAngle += 15f;
        }
    }
}
