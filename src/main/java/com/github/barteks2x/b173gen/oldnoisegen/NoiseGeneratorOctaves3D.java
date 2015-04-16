package com.github.barteks2x.b173gen.oldnoisegen;

import java.util.Random;

public class NoiseGeneratorOctaves3D extends NoiseGenerator {

    private final NoiseGenerator3dPerlin generatorCollection[];
    private final int octaves;

    public NoiseGeneratorOctaves3D(Random random, int i, boolean nofarlands) {
        octaves = i;
        generatorCollection = new NoiseGenerator3dPerlin[i];
        for(int j = 0; j < i; j++) {
            generatorCollection[j] = new NoiseGenerator3dPerlin(random, nofarlands);
        }

    }

    public double generateNoise(double d, double d1) {
        double d2 = 0.0D;
        double d3 = 1.0D;
        for(int i = 0; i < octaves; i++) {
            d2 += generatorCollection[i].generateNoise(d * d3, d1 * d3) / d3;
            d3 /= 2D;
        }

        return d2;
    }

    public double[] generateNoiseArray(double ad[], double x, double y, double z, int xSize, int ySize, int zSize, double gridX, double gridY, double gridZ) {
        if(ad == null) {
            ad = new double[xSize * ySize * zSize];
        } else {
            for(int l = 0; l < ad.length; l++) {
                ad[l] = 0.0D;
            }

        }
        double frequency = 1.0D;
        for(int i1 = 0; i1 < octaves; i1++) {
            generatorCollection[i1].generateNoiseArray(ad, x, y, z, xSize, ySize, zSize, gridX * frequency, gridY * frequency, gridZ * frequency, frequency);
            frequency /= 2D;
        }

        return ad;
    }

    public double[] generateNoiseArray(double ad[], int x, int z, int xSize, int zSize,
            double gridX, double gridZ, double d2) {
        return generateNoiseArray(ad, x, 10D, z, xSize, 1, zSize, gridX, 1.0D, gridZ);
    }
}
