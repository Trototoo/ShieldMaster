package io.github.shield_master.game_elements.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.shield_master.game_elements.Direction;

public interface ProjectileInterface {
    void move(float deltaTime);
    boolean collidesWith(Rectangle rectangle);
    Rectangle getRectangle();
    Direction getDirection();
    void setDirection(Direction direction);
    TextureRegion getTextureRegion();
    void draw(SpriteBatch batch);
    void dispose();
}
