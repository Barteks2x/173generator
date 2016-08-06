package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.WorldGenBaseOld;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import static com.github.barteks2x.b173gen.oldgen.MathHelper.*;
import static org.bukkit.Material.*;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.LAVA;

public class WorldGenCavesOld extends WorldGenBaseOld {

    public static final double MIN_HORIZONTAL_SIZE = 1.5D;

    protected void generate(int generatedChunkX, int generatedChunkZ, ChunkGenerator.ChunkData data, double structureOriginBlockX, double structureOriginBlockY, double structureOriginBlockZ) {
        generateBranch(
                generatedChunkX, generatedChunkZ,
                data,
                structureOriginBlockX, structureOriginBlockY, structureOriginBlockZ,
                1.0F + rand.nextFloat() * 6F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void generateBranch(int generatedChunkX, int generatedChunkZ,
                                  ChunkGenerator.ChunkData data,
                                  double currentBlockX, double currentBlockY, double currentBlockZ,
                                  float maxHorizontalSize, float directionAngleHorizontal, float directionAngleVertical,
                                  int currentCaveSystemRadius, int maxCaveSystemRadius, double verticalCaveSizeMultiplier) {

        double generatedChunkCenterX = generatedChunkX * 16 + 8;
        double generatedChunkCenterZ = generatedChunkZ * 16 + 8;
        float directionHorizontalChange = 0.0F;
        float directionVerticalChange = 0.0F;
        Random random = new Random(rand.nextLong());
        //negative means not generated yet
        if(maxCaveSystemRadius <= 0) {
            int maxBlockRadius = maxGenerationRadius * 16 - 16;
            maxCaveSystemRadius = maxBlockRadius - random.nextInt(maxBlockRadius / 4);
        }
        boolean noSplitBranch = false;
        if(currentCaveSystemRadius == -1) {
            currentCaveSystemRadius = maxCaveSystemRadius / 2;
            noSplitBranch = true;
        }
        int splitDistance = random.nextInt(maxCaveSystemRadius / 2) + maxCaveSystemRadius / 4;
        boolean allowSteepCave = random.nextInt(6) == 0;
        for(; currentCaveSystemRadius < maxCaveSystemRadius; currentCaveSystemRadius++) {

            //caveRadius grows as we go out of the center
            double caveRadiusHorizontal = getCaveRadius(maxHorizontalSize, currentCaveSystemRadius, maxCaveSystemRadius);
            double caveRadiusVertical = caveRadiusHorizontal * verticalCaveSizeMultiplier;

            //from sin(alpha)=y/r and cos(alpha)=x/r ==> x = r*cos(alpha) and y = r*sin(alpha)
            //always moves by one block in some direction
            //x is horizontal radius, y is vertical
            float horizontalDirectionSize = cos(directionAngleVertical);
            float directionY = sin(directionAngleVertical);
            //y is directionZ and is is directionX
            currentBlockX += cos(directionAngleHorizontal) * horizontalDirectionSize;
            currentBlockY += directionY;
            currentBlockZ += sin(directionAngleHorizontal) * horizontalDirectionSize;
            if(allowSteepCave) {
                directionAngleVertical *= 0.92F;
            } else {
                directionAngleVertical *= 0.7F;
            }
            directionAngleVertical += directionVerticalChange * 0.1F;
            directionAngleHorizontal += directionHorizontalChange * 0.1F;

            directionVerticalChange *= 0.9F;
            directionHorizontalChange *= 0.75F;
            directionVerticalChange += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            directionHorizontalChange += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4F;

            if(!noSplitBranch && currentCaveSystemRadius == splitDistance && maxHorizontalSize > 1.0F) {
                generateBranch(generatedChunkX, generatedChunkZ, data, currentBlockX, currentBlockY, currentBlockZ,
                        random.nextFloat() * 0.5F + 0.5F, directionAngleHorizontal - 1.570796F,
                        directionAngleVertical / 3F, currentCaveSystemRadius, maxCaveSystemRadius, 1.0D);
                generateBranch(generatedChunkX, generatedChunkZ, data, currentBlockX, currentBlockY, currentBlockZ,
                        random.nextFloat() * 0.5F + 0.5F, directionAngleHorizontal + 1.570796F,
                        directionAngleVertical / 3F, currentCaveSystemRadius, maxCaveSystemRadius, 1.0D);
                return;
            }
            if(!noSplitBranch && random.nextInt(4) == 0) {
                continue;
            }
            double chunkCenterToCurrentX = currentBlockX - generatedChunkCenterX;
            double chunkCenterToCurrentZ = currentBlockZ - generatedChunkCenterZ;

            if(isCurrentChunkUnreachable(chunkCenterToCurrentX, chunkCenterToCurrentZ, maxCaveSystemRadius, currentCaveSystemRadius, maxHorizontalSize)) {
                return;
            }
            //is cave out of bounds of current chunk?
            if(currentBlockX < generatedChunkCenterX - 16D - caveRadiusHorizontal * 2D ||
                    currentBlockZ < generatedChunkCenterZ - 16D - caveRadiusHorizontal * 2D ||
                    currentBlockX > generatedChunkCenterX + 16D + caveRadiusHorizontal * 2D ||
                    currentBlockZ > generatedChunkCenterZ + 16D + caveRadiusHorizontal * 2D) {
                continue;
            }
            int startX = floor(currentBlockX - caveRadiusHorizontal) - generatedChunkX * 16 - 1;
            int endX = (floor(currentBlockX + caveRadiusHorizontal) - generatedChunkX * 16) + 1;
            int startY = floor(currentBlockY - caveRadiusVertical) - 1;
            int endY = floor(currentBlockY + caveRadiusVertical) + 1;
            int startZ = floor(currentBlockZ - caveRadiusHorizontal) - generatedChunkZ * 16 - 1;
            int endZ = (floor(currentBlockZ + caveRadiusHorizontal) - generatedChunkZ * 16) + 1;
            if(startX < 0) {
                startX = 0;
            }
            if(endX > 16) {
                endX = 16;
            }
            if(startY < 1) {
                startY = 1;
            }
            if(endY > 120) {
                endY = 120;
            }
            if(startZ < 0) {
                startZ = 0;
            }
            if(endZ > 16) {
                endZ = 16;
            }

            if(findWater(data, startX, endX, startY, endY, startZ, endZ)) {
                continue;
            }
            for(int localX = startX; localX < endX; localX++) {
                double xDistanceScaled = (localX + generatedChunkX * 16.0 + 0.5D - currentBlockX) / caveRadiusHorizontal;
                for(int localZ = startZ; localZ < endZ; localZ++) {
                    double zDistanceScaled = (localZ + generatedChunkZ * 16 + 0.5D - currentBlockZ) / caveRadiusHorizontal;
                    boolean hitGrassSurface = false;
                    if(xDistanceScaled * xDistanceScaled + zDistanceScaled * zDistanceScaled >= 1.0D) {
                        continue;
                    }

                    for(int localY = endY; localY >= startY; localY--) {
                        double yDistanceScaled = (localY + 0.5D - currentBlockY) / caveRadiusVertical;
                        //yDistanceScaled > -0.7 ==> flattened floor
                        if(yDistanceScaled > -0.7D &&
                                xDistanceScaled * xDistanceScaled + yDistanceScaled * yDistanceScaled + zDistanceScaled * zDistanceScaled < 1.0D) {
                            Material previousBlock = data.getType(localX, localY, localZ);
                            if(previousBlock == GRASS) {
                                hitGrassSurface = true;
                            }
                            if(previousBlock == STONE
                                    || previousBlock == DIRT
                                    || previousBlock == GRASS) {
                                if(localY < 10) {
                                    data.setBlock(localX, localY, localZ, LAVA);
                                } else {
                                    data.setBlock(localX, localY, localZ, AIR);
                                    if(hitGrassSurface && data.getType(localX, localY - 1, localZ) == DIRT) {
                                        data.setBlock(localX, localY - 1, localZ, GRASS);
                                    }
                                }
                            }
                        }

                    }
                }
            }

            //why?
            if(noSplitBranch) {
                break;
            }
        }

    }

    //returns true of this distance can't be reached even after all remaining iterations
    private static boolean isCurrentChunkUnreachable(double distanceToOriginX, double distanceToOriginZ, int maxCaveSystemRadius, int currentCaveSystemRadius, float maxHorizontalSize) {
        double blocksLeft = maxCaveSystemRadius - currentCaveSystemRadius;
        //even if the exact block can't be reached, the chunk may be reachable by center of the cave
        //and cave size must be also included
        double bufferDistance = maxHorizontalSize + 2.0F + 16F;
        return (distanceToOriginX * distanceToOriginX + distanceToOriginZ * distanceToOriginZ) - blocksLeft * blocksLeft > bufferDistance * bufferDistance;
    }

    //returns radius of the insize of a cave
    private static double getCaveRadius(float horizontalSizeFactor, int currentCaveSystemRadius, int maxCaveSystemRadius) {
        float baseSize = sin(currentCaveSystemRadius * 3.141593F / maxCaveSystemRadius);
        assert baseSize >= 0;
        return MIN_HORIZONTAL_SIZE + baseSize * horizontalSizeFactor;
    }

    private boolean findWater(ChunkGenerator.ChunkData data, int startX, int endX, int startY, int endY, int startZ, int endZ) {
        for(int xPos = startX; xPos < endX; xPos++) {
            for(int zPos = startZ; zPos < endZ; zPos++) {
                for(int yPos = endY + 1; yPos >= startY - 1; yPos--) {
                    if(yPos < 0 || yPos >= 128) {
                        continue;
                    }
                    if(data.getType(xPos, yPos, zPos) == WATER
                            || data.getType(xPos, yPos, zPos) == STATIONARY_WATER) {
                        return true;
                    }
                    if(yPos != startY - 1 && xPos != startX && xPos != endX - 1
                            && zPos != startZ && zPos != endZ - 1) {
                        yPos = startY;
                    }
                }

            }
        }
        return false;
    }

    @Override
    protected void generate(World world, int structureOriginChunkX, int structureOriginChunkZ, int generatedChunkX, int generatedChunkZ, ChunkGenerator.ChunkData data) {
        int i1 = rand.nextInt(rand.nextInt(rand.nextInt(40) + 1) + 1);
        if(rand.nextInt(15) != 0) {
            i1 = 0;
        }
        for(int j1 = 0; j1 < i1; j1++) {
            double structureOriginBlockX = structureOriginChunkX * 16 + rand.nextInt(16);
            double structureOriginBlockY = rand.nextInt(rand.nextInt(120) + 8);
            double structureOriginBlockZ = structureOriginChunkZ * 16 + rand.nextInt(16);
            int k1 = 1;
            if(rand.nextInt(4) == 0) {
                generate(generatedChunkX, generatedChunkZ, data, structureOriginBlockX, structureOriginBlockY, structureOriginBlockZ);
                k1 += rand.nextInt(4);
            }
            for(int l1 = 0; l1 < k1; l1++) {
                float f = rand.nextFloat() * 3.141593F * 2.0F;
                float f1 = ((rand.nextFloat() - 0.5F) * 2.0F) / 8F;
                float f2 = rand.nextFloat() * 2.0F + rand.nextFloat();
                generateBranch(generatedChunkX, generatedChunkZ, data, structureOriginBlockX, structureOriginBlockY, structureOriginBlockZ, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }
}
