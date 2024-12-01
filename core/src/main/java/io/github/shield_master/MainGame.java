package io.github.shield_master;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import io.github.shield_master.screens.MainMenuScreen;
import io.github.shield_master.utils.AssetLoader;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private AssetManager assetManager;

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new MainMenuScreen(this));
    }
}
