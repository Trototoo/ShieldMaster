package io.github.shield_master.game_elements.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.shield_master.game_elements.Direction;

public class RegularProjectile implements ProjectileInterface {
    private Rectangle rectangle;
    private float speed;
    private Direction direction;
    private final TextureRegion textureRegion = new TextureRegion(new Texture("game_elements/projectiles/regular_projectile.png"));

    public static final float WIDTH = 13;
    public static final float HEIGHT = 7;

    public RegularProjectile(float x, float y, float speed, Direction direction) {
        rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
        this.speed = speed;
        this.direction = direction;
    }

    @Override
    public void move(float deltaTime) {
        switch (direction) {
            case UP:
                rectangle.y += speed * deltaTime;
                break;
            case DOWN:
                rectangle.y -= speed * deltaTime;
                break;
            case LEFT:
                rectangle.x -= speed * deltaTime;
                break;
            case RIGHT:
                rectangle.x += speed * deltaTime;
                break;
        }
    }

    @Override
    public boolean collidesWith(Rectangle rectangle) {
        return this.rectangle.overlaps(rectangle);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void draw(SpriteBatch batch) {
        float rotation = switch (direction) {
            case UP -> 90f;
            case DOWN -> 270f;
            case LEFT -> 180f;
            case RIGHT -> 0f;
        };
        batch.draw(textureRegion, rectangle.x, rectangle.y, WIDTH/2, HEIGHT/2, WIDTH, HEIGHT, 1, 1, rotation);
    }

    @Override
    public void dispose() {
        textureRegion.getTexture().dispose();
    }
}
