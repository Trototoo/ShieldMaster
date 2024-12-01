package io.github.shield_master.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {
    private static final Preferences prefs = Gdx.app.getPreferences("shield_master");

    public static void setHighScore(int val) {
        prefs.putInteger("highscore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highscore", 0);
    }
}
