package com.github.barteks2x.b173gen.generator;

import com.github.barteks2x.b173gen.generator.beta173.Biome;
import java.util.*;
import net.minecraft.server.v1_6_R1.MathHelper;
import net.minecraft.server.v1_6_R1.WorldGenLargeFeature;

public class WorldGenLargeFeature173 extends WorldGenLargeFeature {

	private static List e;
	private List f;
	private int g;
	private int h;

	public WorldGenLargeFeature173() {
		super();
		f = new ArrayList();
		g = 32;
		h = 8;
		f.add(new net.minecraft.server.v1_6_R1.BiomeMeta(
				net.minecraft.server.v1_6_R1.EntityWitch.class, 1, 1, 1));
	}

	public WorldGenLargeFeature173(Map map) {
		this();
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
			if (((String)entry.getKey()).equals("distance")) {
				g = MathHelper.a((String)entry.getValue(), g, h + 1);
			}
		}
	}

	protected boolean a(int i, int j) {
		label0:
		{
			int k = i;
			int l = j;
			if (i < 0) {
				i -= g - 1;
			}
			if (j < 0) {
				j -= g - 1;
			}
			int i1 = i / g;
			int j1 = j / g;
			Random random = c.H(i1, j1, 0xdb1471);
			i1 *= g;
			j1 *= g;
			i1 += random.nextInt(g - h);
			j1 += random.nextInt(g - h);
			i = k;
			j = l;
			if (i != i1 || j != j1) {
				break label0;
			}
			/*
			 * net.minecraft.server.v1_6_R1.BiomeBase biomebase = c.getWorldChunkManager().
			 * getBiome(i * 16 + 8, j * 16 + 8);
			 * Iterator iterator = e.iterator();
			 * net.minecraft.server.v1_6_R1.BiomeBase biomebase1;
			 * do {
			 * if (!iterator.hasNext()) {
			 * break label0;
			 * }
			 * biomebase1 = (net.minecraft.server.v1_6_R1.BiomeBase) iterator.
			 * next();
			 * } while (!biomebase.y.equals(biomebase1.y));
			 */
			return true;
		}
		return false;
	}

	public List a() {
		return f;
	}

	static {
		e = Arrays.asList(new Biome[]{
			Biome.desert, Biome.rainforest, Biome.swampland
		});
	}
}
