package com.github.barteks2x.b173gen.generator.populator;

import com.github.barteks2x.b173gen.ISimpleWorld;
import com.github.barteks2x.b173gen.generator.IPopulator;
import com.github.barteks2x.b173gen.generator.PopulatorState;
import com.github.barteks2x.b173gen.oldgen.MinecraftMethods;
import org.bukkit.Material;

import java.util.Random;

import static org.bukkit.Material.ICE;
import static org.bukkit.Material.SNOW;

public class SnowPopulator implements IPopulator {
    private static void generateSnow(ISimpleWorld world, Random rand, double[] temperatures, int chunkStartX, int chunkStartZ) {

        for (int blockX = chunkStartX; blockX < chunkStartX + 16; blockX++) {
            for (int blockZ = chunkStartZ; blockZ < chunkStartZ + 16; blockZ++) {
                int localX = blockX - chunkStartX;
                int localZ = blockZ - chunkStartZ;
                int blockY = getHighestSolidOrLiquidBlock(world, blockX, blockZ);
                double temp = temperatures[(localX << 4) | localZ] - (blockY - 64) / 64D * 0.3D;
                Material blockBelow = world.getType(blockX, blockY - 1, blockZ);
                if ((temp < 0.5D) &&
                        blockY > 0 && blockY < 128 &&
                        world.isEmpty(blockX, blockY, blockZ) &&
                        blockBelow.isSolid() && blockBelow != ICE) {
                    world.setType(blockX, blockY, blockZ, SNOW);
                }
            }
        }
    }

    public static int getHighestSolidOrLiquidBlock(ISimpleWorld world, int i, int j) {
        for (int k = 127; k > 0; --k) {
            Material material = world.getType(i, k, j);
            if (material != Material.AIR || MinecraftMethods.isLiquid(material)) {
                return k + 1;
            }
        }

        return -1;
    }

    @Override
    public void populate(ISimpleWorld world, PopulatorState state) {
        generateSnow(world, state.getRng(), state.getTemperatures(), state.getBlockX(), state.getBlockZ());
    }
}
