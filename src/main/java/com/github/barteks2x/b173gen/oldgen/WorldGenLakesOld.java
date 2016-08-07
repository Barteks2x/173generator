package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class WorldGenLakesOld implements WorldGenerator173 {
    private Material liquidBlock;

    public WorldGenLakesOld(Material i) {
        this.liquidBlock = i;
    }

    public boolean generate(World world, Random random, int blockX, int blockY, int blockZ) {
        blockX -= 8;
        blockZ -= 8;

        while(blockY > 0 && world.getBlockAt(blockX, blockY, blockZ).getType() == Material.AIR)
            --blockY;

        blockY -= 4;

        boolean[] emptyOrLiquid = new boolean[2048];
        int parts = random.nextInt(4) + 4;

        for(int i = 0; i < parts; ++i) {
            double scaleX = random.nextDouble() * 6.0D + 3.0D;
            double scaleY = random.nextDouble() * 4.0D + 2.0D;
            double scaleZ = random.nextDouble() * 6.0D + 3.0D;
            double posX = random.nextDouble() * (16.0D - scaleX - 2.0D) + 1.0D + scaleX / 2.0D;
            double posY = random.nextDouble() * (8.0D - scaleY - 4.0D) + 2.0D + scaleY / 2.0D;
            double posZ = random.nextDouble() * (16.0D - scaleZ - 2.0D) + 1.0D + scaleZ / 2.0D;

            for(int localX = 1; localX < 15; ++localX) {
                for(int localZ = 1; localZ < 15; ++localZ) {
                    for(int localY = 1; localY < 7; ++localY) {
                        double scaledDistX = ((double)localX - posX) / (scaleX / 2.0D);
                        double scaledDistY = ((double)localY - posY) / (scaleY / 2.0D);
                        double scaledDistZ = ((double)localZ - posZ) / (scaleZ / 2.0D);
                        double scaledDist = scaledDistX * scaledDistX + scaledDistY * scaledDistY + scaledDistZ * scaledDistZ;

                        if(scaledDist < 1.0D) {
                            emptyOrLiquid[(localX * 16 + localZ) * 8 + localY] = true;
                        }
                    }
                }
            }
        }

        for(int localX = 0; localX < 16; ++localX) {
            for(int localZ = 0; localZ < 16; ++localZ) {
                for(int localY = 0; localY < 8; ++localY) {
                    boolean isNewlyExposed = !emptyOrLiquid[(localX * 16 + localZ) * 8 + localY] &&
                            (localX < 15 && emptyOrLiquid[((localX + 1) * 16 + localZ) * 8 + localY] ||
                                    localX > 0 && emptyOrLiquid[((localX - 1) * 16 + localZ) * 8 + localY] ||
                                    localZ < 15 && emptyOrLiquid[(localX * 16 + localZ + 1) * 8 + localY] ||
                                    localZ > 0 && emptyOrLiquid[(localX * 16 + (localZ - 1)) * 8 + localY] ||
                                    localY < 7 && emptyOrLiquid[(localX * 16 + localZ) * 8 + localY + 1] ||
                                    localY > 0 && emptyOrLiquid[(localX * 16 + localZ) * 8 + (localY - 1)]);
                    if (!isNewlyExposed) {
                        continue;
                    }
                    Block block = world.getBlockAt(blockX + localX, blockY + localY, blockZ + localZ);
                    //if above liquid surface and is liquid
                    if(localY >= 4 && block.isLiquid()) {
                        return false;
                    }

                    //if below liquid surface and is non-solid block different then generated liquid
                    if(localY < 4 && !block.getType().isSolid() && block.getType() != this.liquidBlock) {
                        return false;
                    }
                }
            }
        }

        for(int localX = 0; localX < 16; ++localX) {
            for(int localZ = 0; localZ < 16; ++localZ) {
                for(int localY = 0; localY < 8; ++localY) {
                    if(emptyOrLiquid[(localX * 16 + localZ) * 8 + localY]) {
                        world.getBlockAt(blockX + localX, blockY + localY, blockZ + localZ).setType(localY >= 4 ? Material.AIR : this.liquidBlock);
                    }
                }
            }
        }

        for(int i1 = 0; i1 < 16; ++i1) {
            for(int i2 = 0; i2 < 16; ++i2) {
                for(int j2 = 4; j2 < 8; ++j2) {
                    if(emptyOrLiquid[(i1 * 16 + i2) * 8 + j2]
                            && world.getBlockAt(blockX + i1, blockY + j2 - 1, blockZ + i2).getType() == Material.DIRT
                            && MinecraftMethods.World_getlightValue(world, blockX + i1, blockY + j2, blockZ + i2, LightType.SKY) > 0) {
                        world.getBlockAt(blockX + i1, blockY + j2 - 1, blockZ + i2).setType(Material.GRASS);
                    }
                }
            }
        }

        if(this.liquidBlock == Material.LAVA) {
            for(int i1 = 0; i1 < 16; ++i1) {
                for(int i2 = 0; i2 < 16; ++i2) {
                    for(int j2 = 0; j2 < 8; ++j2) {
                        boolean flag = !emptyOrLiquid[(i1 * 16 + i2) * 8 + j2] && (i1 < 15 && emptyOrLiquid[((i1 + 1) * 16 + i2) * 8 + j2] || i1 > 0 && emptyOrLiquid[((i1 - 1) * 16 + i2) * 8 + j2] || i2 < 15 && emptyOrLiquid[(i1 * 16 + i2 + 1) * 8 + j2] || i2 > 0 && emptyOrLiquid[(i1 * 16 + (i2 - 1)) * 8 + j2] || j2 < 7 && emptyOrLiquid[(i1 * 16 + i2) * 8 + j2 + 1] || j2 > 0 && emptyOrLiquid[(i1 * 16 + i2) * 8 + (j2 - 1)]);
                        if(flag && (j2 < 4 || random.nextInt(2) != 0) && MinecraftMethods.Material_isBuildable(world, blockX + i1, blockY + j2, blockZ + i2)) {
                            world.getBlockAt(blockX + i1, blockY + j2, blockZ + i2).setType(Material.STONE);
                        }
                    }
                }
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }//*/
}
