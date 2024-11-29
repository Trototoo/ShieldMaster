package io.github.shield_master.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture playerSpriteSheet;
    public static Animation<TextureRegion> playerUpAnimation;
    public static Animation<TextureRegion> playerDownAnimation;
    public static Animation<TextureRegion> playerLeftAnimation;
    public static Animation<TextureRegion> playerRightAnimation;

    public static void load() {
        loadPlayerAnimations();
    }

    private static void loadPlayerAnimations() {
        playerSpriteSheet = new Texture("images/player.png");

        TextureRegion[][] temp = TextureRegion.split(playerSpriteSheet, 16, 16);

        TextureRegion[] playerUpFrames = new TextureRegion[6];
        TextureRegion[] playerDownFrames = new TextureRegion[6];
        TextureRegion[] playerLeftFrames = new TextureRegion[6];
        TextureRegion[] playerRightFrames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {
            playerUpFrames[i] = temp[0][i];
            playerDownFrames[i] = temp[0][i + 6];
            playerLeftFrames[i] = temp[0][i + 12];
            playerRightFrames[i] = temp[0][i + 18];
        }

        playerUpAnimation = new Animation<>(0.1f, playerUpFrames);
        playerDownAnimation = new Animation<>(0.1f, playerDownFrames);
        playerLeftAnimation = new Animation<>(0.1f, playerLeftFrames);
        playerRightAnimation = new Animation<>(0.1f, playerRightFrames);
    }

    public static void dispose() {
        playerSpriteSheet.dispose();
    }
}
