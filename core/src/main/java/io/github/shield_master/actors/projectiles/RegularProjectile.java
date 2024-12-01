package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.shield_master.utils.Direction;

public class RegularProjectile extends Projectile {
    public RegularProjectile(TextureRegion textureRegion, Direction direction) {
        super(textureRegion, direction);
    }
}
