package io.github.shield_master.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import io.github.shield_master.utils.AssetLoader;

public class Background extends Actor {
    private final TiledDrawable tiledDrawable;
    public Background() {
        tiledDrawable = new TiledDrawable(AssetLoader.backgroundTile);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tiledDrawable.draw(batch, 0, 0, getStage().getWidth(), getStage().getHeight());
    }
}
