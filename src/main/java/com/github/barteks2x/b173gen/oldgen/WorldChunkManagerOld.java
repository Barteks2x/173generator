package com.github.barteks2x.b173gen.oldgen;

import com.github.barteks2x.b173gen.oldnoisegen.NoiseGeneratorOctaves2D;
import java.util.Random;
import org.bukkit.block.Biome;

public class WorldChunkManagerOld {

	public WorldChunkManagerOld(long seed) {
		e = new NoiseGeneratorOctaves2D(new Random(seed * 9871L), 4);
		f = new NoiseGeneratorOctaves2D(new Random(seed * 39811L), 4);
		g = new NoiseGeneratorOctaves2D(new Random(seed * 0x84a59L), 2);
	}

	public Biome getBiome(int i, int j) {
		return getBiomeData(i, j, 1, 1)[0];
	}

	public float[] getWetness(float[] ad, int i, int j, int k, int l) {
		if (ad == null || ad.length < k * l) {
			ad = new float[k * l];
		}
		double[] ad2 = new double[ad.length];
		for (int ii = 0; ii < ad.length; ii++) {
			ad2[ii] = ad[ii];
		}
		ad2 = f.generateNoiseArray(ad2, i, j, k, l, 0.02500000037252903D,
				0.02500000037252903D, 0.25D);
		c = g.generateNoiseArray(c, i, j, k, l, 0.25D, 0.25D, 0.58823529411764708D);
		int i1 = 0;
		for (int j1 = 0; j1 < k; j1++) {
			for (int k1 = 0; k1 < l; k1++) {
				double d = c[i1] * 1.1000000000000001D + 0.5D;
				double d1 = 0.01D;
				double d2 = 1.0D - d1;
				double d3 =
						(ad2[i1] * 0.14999999999999999D + 0.69999999999999996D) *
						d2 + d * d1;
				d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
				if (d3 < 0.0D) {
					d3 = 0.0D;
				}
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				ad2[i1] = d3;
				i1++;
			}

		}
		for (int ii = 0; ii < ad.length; ii++) {
			ad[ii] = (float)ad2[ii];
		}
		return ad;
	}

	public float[] getTemperatures(float[] ad, int i, int j, int k, int l) {
		if (ad == null || ad.length < k * l) {
			ad = new float[k * l];
		}
		double[] ad2 = new double[ad.length];
		for (int ii = 0; ii < ad.length; ii++) {
			ad2[ii] = ad[ii];
		}
		ad2 = e.generateNoiseArray(ad2, i, j, k, l, 0.02500000037252903D,
				0.02500000037252903D, 0.25D);
		c = g.generateNoiseArray(c, i, j, k, l, 0.25D, 0.25D, 0.58823529411764708D);
		int i1 = 0;
		for (int j1 = 0; j1 < k; j1++) {
			for (int k1 = 0; k1 < l; k1++) {
				double d = c[i1] * 1.1000000000000001D + 0.5D;
				double d1 = 0.01D;
				double d2 = 1.0D - d1;
				double d3 =
						(ad2[i1] * 0.14999999999999999D + 0.69999999999999996D) *
						d2 + d * d1;
				d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
				if (d3 < 0.0D) {
					d3 = 0.0D;
				}
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				ad2[i1] = d3;
				i1++;
			}

		}
		for (int ii = 0; ii < ad.length; ii++) {
			ad[ii] = (float)ad2[ii];
		}
		this.temperatures = ad2;
		return ad;
	}

	public Biome[] getBiomeBlock(Biome biomes[], int x, int z, int xSize, int zSize) {
		if (biomes == null || biomes.length < xSize * zSize) {
			biomes = new Biome[xSize * zSize];
		}
		temperatures = e.generateNoiseArray(temperatures, x, z, xSize, xSize,
				0.02500000037252903D, 0.02500000037252903D, 0.25D);
		rain = f.generateNoiseArray(rain, x, z, xSize, xSize, 0.05000000074505806D,
				0.05000000074505806D, 0.33333333333333331D);
		c = g.generateNoiseArray(c, x, z, xSize, xSize, 0.25D, 0.25D, 0.58823529411764708D);
		int i1 = 0;
		for (int j1 = 0; j1 < xSize; j1++) {
			for (int k1 = 0; k1 < zSize; k1++) {
				double d = c[i1] * 1.1000000000000001D + 0.5D;
				double d1 = 0.01D;
				double d2 = 1.0D - d1;
				double d3 = (temperatures[i1] * 0.14999999999999999D +
						0.69999999999999996D) *
						d2 + d * d1;
				d1 = 0.002D;
				d2 = 1.0D - d1;
				double d4 = (rain[i1] * 0.14999999999999999D + 0.5D) * d2 + d *
						d1;
				d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
				if (d3 < 0.0D) {
					d3 = 0.0D;
				}
				if (d4 < 0.0D) {
					d4 = 0.0D;
				}
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				if (d4 > 1.0D) {
					d4 = 1.0D;
				}
				temperatures[i1] = d3;
				rain[i1] = d4;
				biomes[i1++] = BiomeOld.getBiomeFromLookup(d3, d4);
			}

		}

		return biomes;
	}

	public Biome[] getBiomes(Biome abiomegenbase[], int i, int j,
			int k, int l) {
		if (abiomegenbase == null || abiomegenbase.length < k * l) {
			abiomegenbase = new Biome[k * l];
		}
		temperatures = e.generateNoiseArray(temperatures, i, j, k, k,
				0.02500000037252903D, 0.02500000037252903D, 0.25D);
		rain = f.generateNoiseArray(rain, i, j, k, k, 0.05000000074505806D,
				0.05000000074505806D, 0.33333333333333331D);
		c = g.generateNoiseArray(c, i, j, k, k, 0.25D, 0.25D, 0.58823529411764708D);
		int i1 = 0;
		for (int j1 = 0; j1 < k; j1++) {
			for (int k1 = 0; k1 < l; k1++) {
				double d = c[i1] * 1.1000000000000001D + 0.5D;
				double d1 = 0.01D;
				double d2 = 1.0D - d1;
				double d3 = (temperatures[i1] * 0.14999999999999999D +
						0.69999999999999996D) *
						d2 + d * d1;
				d1 = 0.002D;
				d2 = 1.0D - d1;
				double d4 = (rain[i1] * 0.14999999999999999D + 0.5D) * d2 + d *
						d1;
				d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
				if (d3 < 0.0D) {
					d3 = 0.0D;
				}
				if (d4 < 0.0D) {
					d4 = 0.0D;
				}
				if (d3 > 1.0D) {
					d3 = 1.0D;
				}
				if (d4 > 1.0D) {
					d4 = 1.0D;
				}
				temperatures[i1] = d3;
				rain[i1] = d4;
				abiomegenbase[i1++] = BiomeOld.getBiomeFromLookup(d3, d4);
			}

		}
		return abiomegenbase;
	}

	private Biome[] getBiomeData(int i, int j, int k, int l) {
		this.dx = getBiomes(this.dx, i, j, k, l);
		return this.dx;
	}
	private NoiseGeneratorOctaves2D e;
	private NoiseGeneratorOctaves2D f;
	private NoiseGeneratorOctaves2D g;
	public double temperatures[];
	public double rain[];
	public double c[];
	public Biome dx[];
}
