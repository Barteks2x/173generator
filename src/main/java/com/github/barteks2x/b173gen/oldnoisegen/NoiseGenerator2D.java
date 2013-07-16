package com.github.barteks2x.b173gen.oldnoisegen;

import java.util.Random;

public class NoiseGenerator2D {

	public NoiseGenerator2D() {
		this(new Random());
	}

	public NoiseGenerator2D(Random random) {
		permutations = new int[512];
		randomDX = random.nextDouble() * 256D;
		randomDZ = random.nextDouble() * 256D;
		double unused = random.nextDouble() * 256D;
		for (int i = 0; i < 256; i++) {
			permutations[i] = i;
		}

		for (int j = 0; j < 256; j++) {
			int k = random.nextInt(256 - j) + j;
			int l = permutations[j];
			permutations[j] = permutations[k];
			permutations[k] = l;
			permutations[j + 256] = permutations[j];
		}

	}

	private static int wrap(double d) {
		return d <= 0.0D ? (int)d - 1 : (int)d;
	}

	private static double method1(int ai[], double d, double d1) {
		return (double)ai[0] * d + (double)ai[1] * d1;
	}

	public void generateNoiseArray(double array[], double xPos, double zPos, int xSize, int zSize,
			double gridX, double gridZ, double amplitude) {
		int k = 0;
		for (int x = 0; x < xSize; x++) {
			double cx = (xPos + (double)x) * gridX + randomDX;
			for (int z = 0; z < zSize; z++) {
				double cz = (zPos + (double)z) * gridZ + randomDZ;
				double d10 = (cx + cz) * const1;
				int j1 = wrap(cx + d10);
				int k1 = wrap(cz + d10);
				double d11 = (double)(j1 + k1) * const2;
				double d12 = (double)j1 - d11;
				double d13 = (double)k1 - d11;
				double d14 = cx - d12;
				double d15 = cz - d13;
				int l1;
				int i2;
				if (d14 > d15) {
					l1 = 1;
					i2 = 0;
				} else {
					l1 = 0;
					i2 = 1;
				}
				double d16 = (d14 - (double)l1) + const2;
				double d17 = (d15 - (double)i2) + const2;
				double d18 = (d14 - 1.0D) + 2D * const2;
				double d19 = (d15 - 1.0D) + 2D * const2;
				int j2 = j1 & 0xff;
				int k2 = k1 & 0xff;
				int l2 = permutations[j2 + permutations[k2]] % 12;
				int i3 = permutations[j2 + l1 + permutations[k2 + i2]] % 12;
				int j3 = permutations[j2 + 1 + permutations[k2 + 1]] % 12;
				double d20 = 0.5D - d14 * d14 - d15 * d15;
				double d7;
				if (d20 < 0.0D) {
					d7 = 0.0D;
				} else {
					d20 *= d20;
					d7 = d20 * d20 * method1(arrayI[l2], d14, d15);
				}
				double d21 = 0.5D - d16 * d16 - d17 * d17;
				double d8;
				if (d21 < 0.0D) {
					d8 = 0.0D;
				} else {
					d21 *= d21;
					d8 = d21 * d21 * method1(arrayI[i3], d16, d17);
				}
				double d22 = 0.5D - d18 * d18 - d19 * d19;
				double d9;
				if (d22 < 0.0D) {
					d9 = 0.0D;
				} else {
					d22 *= d22;
					d9 = d22 * d22 * method1(arrayI[j3], d18, d19);
				}
				array[k++] += 70D * (d7 + d8 + d9) * amplitude;
			}
		}
	}
	private static int arrayI[][] = {
		{1, 1, 0},
		{-1, 1, 0},
		{1, -1, 0},
		{-1, -1, 0},
		{1, 0, 1},
		{-1, 0, 1},
		{1, 0, -1},
		{-1, 0, -1},
		{0, 1, 1},
		{0, -1, 1},
		{0, 1, -1},
		{0, -1, -1}};
	private int permutations[];
	public double randomDX;
	public double randomDZ;
	//public double randomDZ;
	private static final double const1 = 0.5D * (Math.sqrt(3D) - 1.0D);
	private static final double const2 = (3D - Math.sqrt(3D)) / 6D;
}
