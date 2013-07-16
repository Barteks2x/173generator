package com.github.barteks2x.b173gen.oldnoisegen;

import java.util.Random;

public class NoiseGenerator3dPerlin extends NoiseGenerator {

	public NoiseGenerator3dPerlin() {
		this(new Random());
	}

	public NoiseGenerator3dPerlin(Random random) {
		permutations = new int[512];
		randomDX = random.nextDouble() * 256D;
		randomDY = random.nextDouble() * 256D;
		randomDZ = random.nextDouble() * 256D;
		for (int i = 0; i < 256; i++) {
			permutations[i] = i;
		}
		//array of random values
		for (int j = 0; j < 256; j++) {
			int k = random.nextInt(256 - j) + j;
			int l = permutations[j];
			permutations[j] = permutations[k];
			permutations[k] = l;
			permutations[j + 256] = permutations[j];
		}

	}

	@SuppressWarnings("cast")
	public double generateNoise(double xPos, double yPos, double zPos) {
		double x = xPos + randomDX;
		double y = yPos + randomDY;
		double z = zPos + randomDZ;
		int intX = (int) x;
		int intY = (int) y;
		int intZ = (int) z;
		if (x < (double) intX) {
			intX--;
		}
		if (y < (double) intY) {
			intY--;
		}
		if (z < (double) intZ) {
			intZ--;
		}
		int p1 = intX & 0xff;
		int p2 = intY & 0xff;
		int p3 = intZ & 0xff;
		x -= intX;
		y -= intY;
		z -= intZ;
		double fx = x * x * x * (x * (x * 6D - 15D) + 10D);
		double fy = y * y * y * (y * (y * 6D - 15D) + 10D);
		double fz = z * z * z * (z * (z * 6D - 15D) + 10D);
		int a1 = permutations[p1] + p2;
		int a2 = permutations[a1] + p3;
		int a3 = permutations[a1 + 1] + p3;
		int a4 = permutations[p1 + 1] + p2;
		int a5 = permutations[a4] + p3;
		int a6 = permutations[a4 + 1] + p3;
		return lerp(fz, lerp(fy,
					lerp(fx,grad3d(permutations[a2], x, y, z),
						grad3d(permutations[a5], x - 1.0D, y, z)),
					lerp(fx,grad3d(permutations[a3], x, y - 1.0D, z),
						grad3d(permutations[a6], x - 1.0D, y - 1.0D, z))),
				lerp(fy,
					lerp(fx,grad3d(permutations[a2 + 1], x, y, z - 1.0D),
						grad3d(permutations[a5 + 1], x - 1.0D, y, z - 1.0D)),
					lerp(fx,grad3d(permutations[a3 + 1], x, y - 1.0D, z - 1.0D),
						grad3d(permutations[a6 + 1], x - 1.0D, y - 1.0D, z - 1.0D))));
	}

	public final double lerp(double d, double d1, double d2) {
		return d1 + d * (d2 - d1);
	}

	public final double grad2d(int i, double x, double z) {
		int j = i & 0xf;
		double d2 = (double) (1 - ((j & 8) >> 3)) * x;
		double d3 = j >= 4 ? j != 12 && j != 14 ? z : x : 0.0D;
		return ((j & 1) != 0 ? -d2 : d2) + ((j & 2) != 0 ? -d3 : d3);
	}

	public final double grad3d(int i, double x, double y, double z) {
		int j = i & 0xf;
		double d3 = j >= 8 ? y : x;
		double d4 = j >= 4 ? j != 12 && j != 14 ? z : x : y;
		return ((j & 1) != 0 ? -d3 : d3) + ((j & 2) != 0 ? -d4 : d4);
	}

	public double generateNoise(double d, double d1) {
		return generateNoise(d, d1, 0.0D);
	}

	public void generateNoiseArray(double array[], double xPos, double yPos, double zPos, int xSize,
			int ySize, int zSize, double gridX, double gridY, double gridZ, double a) {
		if (ySize == 1) {
			//boolean flag = false;
			//boolean flag1 = false;
			//boolean flag2 = false;
			//boolean flag3 = false;
			//double d8 = 0.0D;
			//double d10 = 0.0D;
			int index = 0;
			double amplitude = 1.0D / a;
			for (int dx = 0; dx < xSize; dx++) {
				double x = (xPos + (double) dx) * gridX + randomDX;
				int intX = (int) x;
				if (x < (double) intX) {
					intX--;
				}
				int k4 = intX & 0xff;
				x -= intX;
				//x^3(6x^2-15x+10)
				double d17 = x * x * x * (x * (x * 6D - 15D) + 10D);
				for (int dz = 0; dz < zSize; dz++) {
					double z = (zPos + (double) dz) * gridZ + randomDZ;
					int intZ = (int) z;
					if (z < (double) intZ) {
						intZ--;
					}
					int l5 = intZ & 0xff;
					z -= intZ;
					//x^3(6x^2-15x+10)
					double d21 = z * z * z * (z * (z * 6D - 15D) + 10D);
					int l = permutations[k4] + 0;
					int j1 = permutations[l] + l5;
					int k1 = permutations[k4 + 1] + 0;
					int l1 = permutations[k1] + l5;
					double d9 = lerp(d17,
							grad2d(permutations[j1], x, z),
							grad3d(permutations[l1], x - 1.0D, 0.0D, z));
					double d11 = lerp(
							d17,
							grad3d(permutations[j1 + 1], x, 0.0D, z - 1.0D),
							grad3d(permutations[l1 + 1], x - 1.0D, 0.0D,
									z - 1.0D));
					double value = lerp(d21, d9, d11);
					array[index++] += value * amplitude;
				}

			}

			return;
		}
		int i1 = 0;
		double amplitude = 1.0D / a;
		int i2 = -1;
		//boolean flag4 = false;
		//boolean flag5 = false;
		//boolean flag6 = false;
		//boolean flag7 = false;
		//boolean flag8 = false;
		//boolean flag9 = false;
		double d13 = 0.0D;
		double d15 = 0.0D;
		double d16 = 0.0D;
		double d18 = 0.0D;
		for (int dx = 0; dx < xSize; dx++) {
			double x = (xPos + (double) dx) * gridX + randomDX;
			int intX = (int) x;
			if (x < (double) intX) {
				intX--;
			}
			int i6 = intX & 0xff;
			x -= intX;
			double d22 = x * x * x * (x * (x * 6D - 15D) + 10D);
			for (int dz = 0; dz < zSize; dz++) {
				double z = (zPos + (double) dz) * gridZ + randomDZ;
				int k6 = (int) z;
				if (z < (double) k6) {
					k6--;
				}
				int l6 = k6 & 0xff;
				z -= k6;
				double d25 = z * z * z * (z * (z * 6D - 15D) + 10D);
				for (int dy = 0; dy < ySize; dy++) {
					double y = (yPos + (double) dy) * gridY + randomDY;
					int j7 = (int) y;
					if (y < (double) j7) {
						j7--;
					}
					int k7 = j7 & 0xff;
					y -= j7;
					double d27 = y * y * y
							* (y * (y * 6D - 15D) + 10D);
					if (dy == 0 || k7 != i2) {
						i2 = k7;
						int j2 = permutations[i6] + k7;
						int k2 = permutations[j2] + l6;
						int l2 = permutations[j2 + 1] + l6;
						int i3 = permutations[i6 + 1] + k7;
						int k3 = permutations[i3] + l6;
						int l3 = permutations[i3 + 1] + l6;
						d13 = lerp(d22, grad3d(permutations[k2], x, y, z),
								grad3d(permutations[k3], x - 1.0D, y, z));
						d15 = lerp(
								d22,
								grad3d(permutations[l2], x, y - 1.0D, z),
								grad3d(permutations[l3], x - 1.0D, y - 1.0D,
										z));
						d16 = lerp(
								d22,
								grad3d(permutations[k2 + 1], x, y, z - 1.0D),
								grad3d(permutations[k3 + 1], x - 1.0D, y,
										z - 1.0D));
						d18 = lerp(
								d22,
								grad3d(permutations[l2 + 1], x, y - 1.0D,
										z - 1.0D),
								grad3d(permutations[l3 + 1], x - 1.0D,
										y - 1.0D, z - 1.0D));
					}
					double d28 = lerp(d27, d13, d15);
					double d29 = lerp(d27, d16, d18);
					double value = lerp(d25, d28, d29);
					array[i1++] += value * amplitude;
				}

			}

		}

	}

	private int permutations[];
	public double randomDX;
	public double randomDY;
	public double randomDZ;
}
