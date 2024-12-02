package io.github.shield_master.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.github.shield_master.actors.Player;

public class AssetLoader {
    public static AssetManager assetManager;
    public static Texture playerSpriteSheet;
    public static TextureRegion[][] playerFrames;

    public static TextureRegion regularProjectileTexture;
    public static TextureRegion reverseProjectileTexture;

    public static Animation<TextureRegion> playerUpAnimation;
    public static Animation<TextureRegion> playerDownAnimation;
    public static Animation<TextureRegion> playerLeftAnimation;
    public static Animation<TextureRegion> playerRightAnimation;

    public static TextureRegion backgroundTile;
    public static TextureRegion shieldTextureVertical;
    public static TextureRegion shieldTextureHorizontal;

    public static TextureRegion highScoreImage;
    public static BitmapFont fontScore;
    public static BitmapFont fontHighscore;

    public static Image mainMenuBackground;

    public static Music mainMenuMusic;
    public static Music gameMusic;
    public static Sound hitSound;
    public static Sound buttonPressSound;
    public static Sound[] blockSounds = new Sound[2];
    public static Sound loseSound;

    public static void load() {
        assetManager = new AssetManager();

        // Load sounds
        assetManager.load("sounds/menu_song.ogg", Sound.class);
        assetManager.load("sounds/game_song.ogg", Sound.class);
        assetManager.load("sounds/hit.ogg", Sound.class);
        assetManager.load("sounds/button_press.ogg", Sound.class);
        assetManager.load("sounds/block_1.ogg", Sound.class);
        assetManager.load("sounds/block_2.ogg", Sound.class);
        assetManager.load("sounds/lose_sound.ogg", Sound.class);

        // Load images
        assetManager.load("images/background_tile.png", Texture.class);
        assetManager.load("images/player_sheet.png", Texture.class);
        assetManager.load("images/shield_horizontal.png", Texture.class);
        assetManager.load("images/shield_vertical.png", Texture.class);
        assetManager.load("images/projectiles/regular.png", Texture.class);
        assetManager.load("images/projectiles/reverse.png", Texture.class);
        assetManager.load("images/ui/highscore.png", Texture.class);
        assetManager.load("images/ui/main_menu_background.png", Texture.class);
        assetManager.finishLoading();

        // Assign loaded textures
        mainMenuBackground = new Image(assetManager.get("images/ui/main_menu_background.png", Texture.class));

        playerSpriteSheet = assetManager.get("images/player_sheet.png", Texture.class);
        playerFrames = TextureRegion.split(playerSpriteSheet, Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);

        loadPlayerAnimations();

        backgroundTile = new TextureRegion(assetManager.get("images/background_tile.png", Texture.class));

        regularProjectileTexture = new TextureRegion(assetManager.get("images/projectiles/regular.png", Texture.class));
        reverseProjectileTexture = new TextureRegion(assetManager.get("images/projectiles/reverse.png", Texture.class));

        shieldTextureVertical = new TextureRegion(assetManager.get("images/shield_vertical.png", Texture.class));
        shieldTextureHorizontal = new TextureRegion(assetManager.get("images/shield_horizontal.png", Texture.class));

        highScoreImage = new TextureRegion(assetManager.get("images/ui/highscore.png", Texture.class));

        // Create fonts
        createFontScore();
        createFontHighscore();

        // Assign loaded sounds
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_song.ogg"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_song.ogg"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.ogg"));
        buttonPressSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_press.ogg"));
        blockSounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/block_1.ogg"));
        blockSounds[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/block_2.ogg"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lose_sound.ogg"));

        // Set music properties
        mainMenuMusic.setLooping(true);
        gameMusic.setLooping(true);
        mainMenuMusic.setVolume(GameManager.getMusicVolume());
        gameMusic.setVolume(GameManager.getMusicVolume());
    }

    private static void createFontScore() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        fontScore = generator.generateFont(parameter);
        generator.dispose();
    }

    private static void createFontHighscore() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        fontHighscore = generator.generateFont(parameter);
        generator.dispose();
    }

    private static ImageButton.ImageButtonStyle createButtonStyle(String texturePath) {
        Texture buttonTexture = new Texture(Gdx.files.internal(texturePath));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = buttonDrawable;
        style.imageDown = buttonDrawable.tint(Color.GRAY);
        return style;
    }

    public static ImageButton.ImageButtonStyle getPlayButtonStyle() {
        return createButtonStyle("images/ui/play_button.png");
    }

    public static ImageButton.ImageButtonStyle getExitButtonStyle() {
        return createButtonStyle("images/ui/exit_button.png");
    }

    public static ImageButton.ImageButtonStyle getContinueButtonStyle() {
        return createButtonStyle("images/ui/continue_button.png");
    }

    public static ImageButton.ImageButtonStyle getRestartButtonStyle() {
        return createButtonStyle("images/ui/restart_button.png");
    }

    public static ImageButton.ImageButtonStyle getMenuButtonStyle() {
        return createButtonStyle("images/ui/menu_button.png");
    }

    public static Drawable createTransparentBackground(float alpha) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, alpha);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
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
