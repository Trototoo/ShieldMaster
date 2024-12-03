package io.github.shield_master.actors.projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import io.github.shield_master.utils.AssetLoader;
import io.github.shield_master.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class ProjectileFactory {

    private static final TextureRegion regularProjectileTexture = AssetLoader.regularProjectileTexture;
    private static final TextureRegion reverseProjectileTexture = AssetLoader.reverseProjectileTexture;

    private enum ProjectileType {
        REGULAR,
        REVERSE,
        FAKE
    }

    private static final List<ProjectileChance> projectileChances = new ArrayList<>();

    static {
        projectileChances.add(new ProjectileChance(ProjectileType.REGULAR, 0.75f));
        projectileChances.add(new ProjectileChance(ProjectileType.REVERSE, 0.10f));
        projectileChances.add(new ProjectileChance(ProjectileType.FAKE, 0.15f));
    }

    public static void generateProjectile(Group projectilesGroup) {
        Direction direction = Direction.values()[(int) (Math.random() * Direction.values().length)];

        ProjectileType projectileType = getRandomProjectileType();

        Projectile projectile = switch (projectileType) {
            case REGULAR -> new RegularProjectile(regularProjectileTexture, direction);
            case REVERSE -> new ReverseProjectile(reverseProjectileTexture, direction);
            case FAKE -> {
                boolean isReverseFake = Math.random() < 0.5;
                yield new FakeProjectile(isReverseFake ? reverseProjectileTexture : regularProjectileTexture, direction, isReverseFake);
            }
        };

        projectilesGroup.addActor(projectile);
    }

    private static ProjectileType getRandomProjectileType() {
        double randomValue = Math.random();
        double cumulativeProbability = 0.0;
        for (ProjectileChance projectileChance : projectileChances) {
            cumulativeProbability += projectileChance.chance;
            if (randomValue <= cumulativeProbability) {
                return projectileChance.projectileType;
            }
        }
        return ProjectileType.REGULAR;
    }

    private record ProjectileChance(ProjectileType projectileType, float chance) {
    }
}
