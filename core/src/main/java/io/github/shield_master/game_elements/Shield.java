package io.github.shield_master.game_elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Shield {
    private final Rectangle rectangle;
    private final Player player;
    private Texture shieldTexture;
    private final Texture shieldHorizontalTexture;
    private final Texture shieldVerticalTexture;
    private boolean isVertical = false;

    private final float WIDTH = 20;
    private final float HEIGHT = 5;

    public final float OFFSET_WIDTH = 2;
    public final float OFFSET_PADDING = 5;

    public Shield(Player player) {
        this.player = player;
        rectangle = new Rectangle(player.getRectangle().getX() - OFFSET_WIDTH, player.getRectangle().getY() + player.getRectangle().height + OFFSET_PADDING, WIDTH, HEIGHT);

        shieldHorizontalTexture = new Texture("shield_horizontal.png");
        shieldVerticalTexture = new Texture("shield_vertical.png");

        shieldTexture = shieldHorizontalTexture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return shieldTexture;
    }

    public void updatePosition() {
        switch (player.getDirection()) {
            case UP:
                rotateUp();
                break;
            case DOWN:
                rotateDown();
                break;
            case LEFT:
                rotateLeft();
                break;
            case RIGHT:
                rotateRight();
                break;
        }
    }

    public void rotateUp() {
        if (isVertical) rotate();
        isVertical = false;
        shieldTexture = shieldHorizontalTexture;
        rectangle.setPosition(player.getRectangle().x - OFFSET_WIDTH, player.getRectangle().y + player.getRectangle().height + OFFSET_PADDING);
    }

    public void rotateDown() {
        if (isVertical) rotate();
        isVertical = false;
        shieldTexture = shieldHorizontalTexture;
        rectangle.setPosition(player.getRectangle().x - OFFSET_WIDTH, player.getRectangle().y - HEIGHT - OFFSET_PADDING);
    }

    public void rotateLeft() {
        if (!isVertical) rotate();
        isVertical = true;
        shieldTexture = shieldVerticalTexture;
        rectangle.setPosition(player.getRectangle().x - rectangle.width - OFFSET_PADDING, player.getRectangle().y - OFFSET_WIDTH);
    }

    public void rotateRight() {
        if (!isVertical) rotate();
        isVertical = true;
        shieldTexture = shieldVerticalTexture;
        rectangle.setPosition(player.getRectangle().x + player.getRectangle().width + OFFSET_PADDING, player.getRectangle().y - OFFSET_WIDTH);
    }

    private void rotate() {
        float temp = rectangle.width;
        rectangle.width = rectangle.height;
        rectangle.height = temp;
        isVertical = !isVertical;
    }
}
