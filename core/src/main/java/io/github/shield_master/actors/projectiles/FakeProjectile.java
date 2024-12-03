package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.shield_master.utils.Constants;
import io.github.shield_master.utils.Direction;

public class FakeProjectile extends Projectile {

    private final Vector2 currentPoint = new Vector2(getX() + getOriginX(), getY() + getOriginY());
    private Vector2 endPoint;
    private boolean isReversed = false;
    private final boolean isReversedFake;
    private int textureAngle = 0;

    public FakeProjectile(TextureRegion textureRegion, Direction direction, boolean isReverseFake) {
        super(textureRegion, direction);
        this.isReversedFake = isReverseFake;
        int FAKE_RADIUS = 40;
        switch (direction) {
            case UP -> endPoint = new Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2 - FAKE_RADIUS);
            case DOWN -> endPoint = new Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2 + FAKE_RADIUS);
            case LEFT -> endPoint = new Vector2(Constants.GAME_WIDTH / 2 + FAKE_RADIUS, Constants.GAME_HEIGHT / 2);
            case RIGHT -> endPoint = new Vector2(Constants.GAME_WIDTH / 2 - FAKE_RADIUS, Constants.GAME_HEIGHT / 2);
        }
        if (this.isReversedFake) {
            setSize(8, 8);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        currentPoint.set(getX() + getOriginX(), getY() + getOriginY());
        if (currentPoint.epsilonEquals(endPoint, 5f) && !isReversed) {
            isReversed = true;
            velocity.scl(-1);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isReversedFake) {
            super.draw(batch, parentAlpha);
            return;
        }
        if (isActive) {
            float offsetX = (direction == Direction.UP || direction == Direction.DOWN) ? (getWidth() - getHeight()) / 2 : 0;
            float offsetY = (direction == Direction.UP || direction == Direction.DOWN) ? (getHeight() - getWidth()) / 2 : 0;

            batch.draw(textureRegion, getX() + offsetX, getY() + offsetY, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, textureAngle);
            textureAngle += 15f;
        }
    }
}
