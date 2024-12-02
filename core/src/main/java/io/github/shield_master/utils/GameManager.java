package io.github.shield_master.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {
    private static final Preferences prefs = Gdx.app.getPreferences("shield_master");

    public static float getMusicVolume() {
        return prefs.getFloat("musicVolume", 1.0f);
    }

    public static void setMusicVolume(float volume) {
        prefs.putFloat("musicVolume", Math.max(0, Math.min(volume, 1.0f)));
        prefs.flush();
    }

    public static float getSoundVolume() {
        return prefs.getFloat("soundVolume", 1.0f);
    }

    public static void setSoundVolume(float volume) {
        prefs.putFloat("soundVolume", Math.max(0, Math.min(volume, 1.0f)));
        prefs.flush();
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highscore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highscore", 0);
    }
}
