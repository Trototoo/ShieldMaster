package io.github.shield_master.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetLoader {
    public static AssetManager assetManager;
    public static Texture playerSpriteSheet;
    public static TextureRegion[][] playerFrames;

    public static TextureRegion regularProjectileTexture;

    public static Animation<TextureRegion> playerUpAnimation;
    public static Animation<TextureRegion> playerDownAnimation;
    public static Animation<TextureRegion> playerLeftAnimation;
    public static Animation<TextureRegion> playerRightAnimation;

    public static TextureRegion backgroundTile;
    public static TextureRegion shieldTextureVertical;
    public static TextureRegion shieldTextureHorizontal;

    public static void load() {
        assetManager = new AssetManager();

        assetManager.load("images/background_tile.png", Texture.class);
        assetManager.load("images/player_sheet.png", Texture.class);
        assetManager.load("images/shield_horizontal.png", Texture.class);
        assetManager.load("images/shield_vertical.png", Texture.class);
        assetManager.load("images/projectiles/regular.png", Texture.class);
        assetManager.finishLoading();

        playerSpriteSheet = assetManager.get("images/player_sheet.png", Texture.class);
        playerFrames = TextureRegion.split(playerSpriteSheet, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);

        loadPlayerAnimations();

        backgroundTile = new TextureRegion(assetManager.get("images/background_tile.png", Texture.class));

        regularProjectileTexture = new TextureRegion(assetManager.get("images/projectiles/regular.png", Texture.class));

        shieldTextureVertical = new TextureRegion(assetManager.get("images/shield_vertical.png", Texture.class));
        shieldTextureHorizontal = new TextureRegion(assetManager.get("images/shield_horizontal.png", Texture.class));
    }

    private static void loadPlayerAnimations() {
        float frameDuration = 0.1f;

        playerUpAnimation = new Animation<>(
            frameDuration,
            new Array<>(playerFrames[0]),
            Animation.PlayMode.LOOP
        );

        playerLeftAnimation = new Animation<>(
            frameDuration,
            new Array<>(playerFrames[1]),
            Animation.PlayMode.LOOP
        );

        playerRightAnimation = new Animation<>(
            frameDuration,
            new Array<>(playerFrames[2]),
            Animation.PlayMode.LOOP
        );

        playerDownAnimation = new Animation<>(
            frameDuration,
            new Array<>(playerFrames[3]),
            Animation.PlayMode.LOOP
        );
    }

    public static void dispose() {
        if (assetManager != null) {
            assetManager.dispose();
        }
    }
}
