package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import static com.github.barteks2x.b173gen.oldgen.MathHelper.*;
import java.util.Random;
import org.bukkit.Material;
import static org.bukkit.Material.*;
import org.bukkit.World;

public class WorldGenMinableOld implements WorldGenerator173 {

    private final Material block;
    private final int size;

    public WorldGenMinableOld(Material i, int j) {
        this.block = i;
        this.size = j;
    }

    public boolean generate(World world, Random rand, int xBase, int yBase, int zBase) {
        float randAngle = rand.nextFloat() * PI;
        double xPlus = (double)((xBase + 8) + sin(randAngle) * this.size / 8.0F);
        double xMinus = (double)((xBase + 8) - sin(randAngle) * this.size / 8.0F);
        double zPlus = (double)((zBase + 8) + cos(randAngle) * this.size / 8.0F);
        double zMinus = (double)((zBase + 8) - cos(randAngle) * this.size / 8.0F);
        double yPlus = (double)(yBase + rand.nextInt(3) + 2);
        double yMinus = (double)(yBase + rand.nextInt(3) + 2);

        double distX, distY, distZ;
        double distSquared1d, distSquared2d, distSquared3d;
        for(int l = 0; l <= this.size; ++l) {
            double dx = xPlus + (xMinus - xPlus) * l / (double)this.size;
            double dy = yPlus + (yMinus - yPlus) * l / (double)this.size;
            double dz = zPlus + (zMinus - zPlus) * l / (double)this.size;
            double Rmultimplier = rand.nextDouble() * (double)this.size / 16.0D;
            double xz2R = (double)(sin(l * PI / this.size) + 1.0F) * Rmultimplier + 1.0D;
            double y2R = (double)(sin(l * PI / this.size) + 1.0F) * Rmultimplier + 1.0D;
            int i1 = floor(dx - xz2R / 2.0D);
            int j1 = floor(dy - y2R / 2.0D);
            int k1 = floor(dz - xz2R / 2.0D);
            int l1 = floor(dx + xz2R / 2.0D);
            int i2 = floor(dy + y2R / 2.0D);
            int j2 = floor(dz + xz2R / 2.0D);

            for(int x = i1; x <= l1; ++x) {
                distX = ((double)x + 0.5D - dx) / (xz2R / 2.0D);
                distSquared1d = distX * distX;
                if(distSquared1d >= 1.0D) {
                    continue;
                }
                for(int y = j1; y <= i2; ++y) {
                    distY = ((double)y + 0.5D - dy) / (y2R / 2.0D);
                    distSquared2d = distX * distX + distY * distY;
                    if(distSquared2d >= 1.0D) {
                        continue;
                    }
                    for(int z = k1; z <= j2; ++z) {
                        distZ = ((double)z + 0.5D - dz) / (xz2R / 2.0D);
                        distSquared3d = distX * distX + distY * distY + distZ * distZ;

                        if(distSquared3d < 1.0D && world.getBlockAt(x, y, z).getType() == STONE) {
                            world.getBlockAt(x, y, z).setType(this.block);
                        }
                    }

                }
            }
        }

        return true;
    }

    public void scale(double d0, double d1, double d2) {
    }
}
