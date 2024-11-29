package io.github.shield_master;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import io.github.shield_master.screens.GameScreen;
import io.github.shield_master.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        setScreen(new GameScreen());
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
