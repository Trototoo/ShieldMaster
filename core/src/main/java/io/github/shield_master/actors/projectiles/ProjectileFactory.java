package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Direction;

public class ProjectileFactory {

    private static final TextureRegion regularProjectileTexture = AssetLoader.regularProjectileTexture;
    private static final TextureRegion reverseProjectileTexture = AssetLoader.reverseProjectileTexture;

    private enum ProjectileType {
        REGULAR,
        REVERSE
    }

    public static void generateProjectile(Group projectilesGroup) {
        Direction direction = Direction.values()[(int) (Math.random() * Direction.values().length)];

        ProjectileType projectileType = ProjectileType.values()[(int) (Math.random() * ProjectileType.values().length)];

        Projectile projectile = switch (projectileType) {
            case REGULAR -> new RegularProjectile(regularProjectileTexture, direction);
            case REVERSE -> new ReverseProjectile(reverseProjectileTexture, direction);
        };

        projectilesGroup.addActor(projectile);
    }
}
