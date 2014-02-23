package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import static org.bukkit.Material.*;
import org.bukkit.World;

public class WorldGenLakesOld implements WorldGenerator173 {

    private final Material block;

    public WorldGenLakesOld(Material i) {
        block = i;
    }

    @Override
    public boolean generate(World world, Random random, int X, int Y, int Z) {
        X -= 8;
        for(Z -= 8; Y > 0 && world.getBlockAt(X, Y, Z).isEmpty(); Y--) {
        }
        Y -= 4;
        boolean aflag[] = new boolean[2048];
        int l = random.nextInt(4) + 4;
        for(int i1 = 0; i1 < l; i1++) {
            double d = random.nextDouble() * 6D + 3D;
            double d1 = random.nextDouble() * 4D + 2D;
            double d2 = random.nextDouble() * 6D + 3D;
            double d3 = random.nextDouble() * (16D - d - 2D) + 1.0D + d / 2D;
            double d4 = random.nextDouble() * (8D - d1 - 4D) + 2D + d1 / 2D;
            double d5 = random.nextDouble() * (16D - d2 - 2D) + 1.0D + d2 / 2D;
            for(int j4 = 1; j4 < 15; j4++) {
                for(int k4 = 1; k4 < 15; k4++) {
                    for(int l4 = 1; l4 < 7; l4++) {
                        double d6 = (j4 - d3) / (d / 2D);
                        double d7 = (l4 - d4) / (d1 / 2D);
                        double d8 = (k4 - d5) / (d2 / 2D);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                        if(d9 < 1.0D) {
                            aflag[(j4 * 16 + k4) * 8 + l4] = true;
                        }
                    }

                }

            }

        }

        for(int j1 = 0; j1 < 16; j1++) {
            for(int j2 = 0; j2 < 16; j2++) {
                for(int j3 = 0; j3 < 8; j3++) {
                    boolean flag = !aflag[(j1 * 16 + j2) * 8 + j3]
                            && (j1 < 15 && aflag[((j1 + 1) * 16 + j2) * 8 + j3] || j1 > 0
                            && aflag[((j1 - 1) * 16 + j2) * 8 + j3] || j2 < 15 && aflag[(j1 * 16
                            + (j2 + 1)) * 8 + j3] || j2 > 0 && aflag[(j1 * 16 + (j2 - 1)) * 8 + j3]
                            || j3 < 7 && aflag[(j1 * 16 + j2) * 8 + (j3 + 1)] || j3 > 0 && aflag[(j1
                            * 16 + j2) * 8 + (j3 - 1)]);
                    if(!flag) {
                        continue;
                    }
                    Material material = world.getBlockAt(X + j1, Y + j3, Z + j2).getType();
                    if(j3 >= 4 && MinecraftMethods.isLiquid(material)) {
                        return false;
                    }
                    if(j3 < 4
                            && !world.getBlockAt(X + j1, Y + j3, Z + j2).getType().isSolid()
                            && world.getBlockAt(X + j1, Y + j3, Z + j2).getType() != block) {
                        return false;
                    }
                }

            }

        }

        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                for(int y = 0; y < 8; y++) {
                    if(aflag[(x * 16 + z) * 8 + y]) {
                        world.getBlockAt(X + x, Y + y, Z + z).setType(y < 4 ? block : AIR);
                    }
                }

            }

        }

        for(int l1 = 0; l1 < 16; l1++) {
            for(int l2 = 0; l2 < 16; l2++) {
                for(int l3 = 4; l3 < 8; l3++) {
                    if(aflag[(l1 * 16 + l2) * 8 + l3]
                            && world.getBlockAt(X + l1, Y + l3 - 1, Z + l2).getType() == DIRT
                            && MinecraftMethods.World_getlightValue(world, X + l1, Y + l3, Z + l2,
                                    LightType.SKY) > 0) {
                        world.getBlockAt(X + l1, (Y + l3) - 1, Z + l2).setType(GRASS);
                    }
                }
            }
        }

        if(block == LAVA || block == STATIONARY_LAVA) {
            for(int i2 = 0; i2 < 16; i2++) {
                for(int i3 = 0; i3 < 16; i3++) {
                    for(int i4 = 0; i4 < 8; i4++) {
                        boolean flag1 = !aflag[(i2 * 16 + i3) * 8 + i4]
                                && (i2 < 15
                                && aflag[((i2 + 1) * 16 + i3) * 8 + i4]
                                || i2 > 0
                                && aflag[((i2 - 1) * 16 + i3) * 8 + i4]
                                || i3 < 15
                                && aflag[(i2 * 16 + (i3 + 1)) * 8 + i4]
                                || i3 > 0
                                && aflag[(i2 * 16 + (i3 - 1)) * 8 + i4]
                                || i4 < 7
                                && aflag[(i2 * 16 + i3) * 8 + (i4 + 1)]
                                || i4 > 0
                                && aflag[(i2 * 16 + i3) * 8 + (i4 - 1)]);
                        if(flag1 && (i4 < 4 || random.nextInt(2) != 0)
                                && world.getBlockAt(X + i2, Y + i4, Z + i3).getType().isSolid()) {
                            world.getBlockAt(X + i2, Y + i4, Z + i3).setType(STONE);
                        }
                    }
                }
            }
        }
        return true;
    }

    public void scale(double d0, double d1, double d2) {}
}
