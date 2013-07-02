package com.github.barteks2x.b173gen.generator.beta173;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import java.util.Random;
import net.minecraft.server.v1_6_R1.World;

public class WorldGenBigTreeOld extends WorldGenerator173 {

	static byte a[] = {2, 0, 0, 1, 2, 1};
	private final Random rand;
	private World worldObj;
	private final int basePos[] = {0, 0, 0};
	private int e;
	private int f;
	private final double g;
	private final double h;
	private final double i;
	private double j;
	private double k;
	private final int l;
	private int m;
	private int n;
	private int o[][];

	public WorldGenBigTreeOld() {
		rand = new Random();
		e = 0;
		g = 0.61799999999999999D;
		h = 1.0D;
		i = 0.38100000000000001D;
		j = 1.0D;
		k = 1.0D;
		l = 1;
		m = 12;
		n = 4;
	}

	@SuppressWarnings("cast")
	void a() {
		f = (int)((double)e * g);
		if (f >= e) {
			f = e - 1;
		}
		int i = (int)(1.3819999999999999D + Math.pow(
				(k * (double)e) / 13D, 2D));
		if (i < 1) {
			i = 1;
		}
		int ai[][] = new int[i * e][4];
		int j = (basePos[1] + e) - n;
		int k = 1;
		int l = basePos[1] + f;
		int i1 = j - basePos[1];
		ai[0][0] = basePos[0];
		ai[0][1] = j;
		ai[0][2] = basePos[2];
		ai[0][3] = l;
		j--;
		while (i1 >= 0) {
			int j1 = 0;
			float f = a(i1);
			if (f < 0.0F) {
				j--;
				i1--;
			} else {
				double d = 0.5D;
				for (; j1 < i; j1++) {
					double d1 = this.j *
							((double)f * ((double)rand.nextFloat() +
							0.32800000000000001D));
					double d2 = (double)rand.nextFloat() * 2D *
							3.1415899999999999D;
					int k1 = MathHelper.floor_double(d1 * Math.sin(d2) +
							(double)basePos[0] + d);
					int l1 = MathHelper.floor_double(d1 * Math.cos(d2) +
							(double)basePos[2] + d);
					int ai1[] = {k1, j, l1};
					int ai2[] = {k1, j + n, l1};
					if (a(ai1, ai2) != -1) {
						continue;
					}
					int ai3[] = {basePos[0], basePos[1], basePos[2]};
					double d3 = Math.sqrt(Math.pow(
							Math.abs(basePos[0] - ai1[0]), 2D) +
							Math.pow(Math.abs(basePos[2] - ai1[2]), 2D));
					double d4 = d3 * this.i;
					if ((double)ai1[1] - d4 > (double)l) {
						ai3[1] = l;
					} else {
						ai3[1] = (int)((double)ai1[1] - d4);
					}
					if (a(ai3, ai1) == -1) {
						ai[k][0] = k1;
						ai[k][1] = j;
						ai[k][2] = l1;
						ai[k][3] = ai3[1];
						k++;
					}
				}

				j--;
				i1--;
			}
		}
		o = new int[k][4];
		System.arraycopy(ai, 0, o, 0, k);
	}

	@SuppressWarnings("cast")
	void a(int i, int j, int k, float f, byte byte0, int l) {
		int i1 = (int)((double)f + 0.61799999999999999D);
		byte byte1 = a[byte0];
		byte byte2 = a[byte0 + 3];
		int ai[] = {i, j, k};
		int ai1[] = {0, 0, 0};
		int j1 = -i1;
		int k1 = -i1;
		ai1[byte0] = ai[byte0];
		for (; j1 <= i1; j1++) {
			ai1[byte1] = ai[byte1] + j1;
			for (int l1 = -i1; l1 <= i1;) {
				double d = Math.sqrt(Math.pow((double)Math.abs(j1) + 0.5D, 2D) +
						Math.pow((double)Math.abs(l1) + 0.5D, 2D));
				if (d > (double)f) {
					l1++;
				} else {
					ai1[byte2] = ai[byte2] + l1;
					int i2 = worldObj.getTypeId(ai1[0], ai1[1], ai1[2]);
					if (i2 != 0 && i2 != 18) {
						l1++;
					} else {
						worldObj.setTypeIdAndData(ai1[0], ai1[1], ai1[2], l,
								0, 2);
						l1++;
					}
				}
			}

		}

	}

	float a(int i) {
		if ((double)i < (double)(float)e * 0.29999999999999999D) {
			return -1.618F;
		}
		float f = (float)e / 2.0F;
		float f1 = (float)e / 2.0F - (float)i;
		float f2;
		if (f1 == 0.0F) {
			f2 = f;
		} else if (Math.abs(f1) >= f) {
			f2 = 0.0F;
		} else {
			f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2D) -
					Math.pow(Math.abs(f1), 2D));
		}
		f2 *= 0.5F;
		return f2;
	}

	float b(int i) {
		if (i < 0 || i >= n) {
			return -1F;
		}
		return i != 0 && i != n - 1 ? 3F : 2.0F;
	}

	void a(int i, int j, int k) {
		int l = j;
		for (int i1 = j + n; l < i1; l++) {
			float f = b(l - j);
			a(i, l, k, f, (byte)1, 18);
		}

	}

	void a(int ai[], int ai1[], int i) {
		int ai2[] = {0, 0, 0};
		byte byte0 = 0;
		int j = 0;
		for (; byte0 < 3; byte0++) {
			ai2[byte0] = ai1[byte0] - ai[byte0];
			if (Math.abs(ai2[byte0]) > Math.abs(ai2[j])) {
				j = byte0;
			}
		}

		if (ai2[j] == 0) {
			return;
		}
		byte byte1 = a[j];
		byte byte2 = a[j + 3];
		byte byte3;
		if (ai2[j] > 0) {
			byte3 = 1;
		} else {
			byte3 = -1;
		}
		double d = (double)ai2[byte1] / (double)ai2[j];
		double d1 = (double)ai2[byte2] / (double)ai2[j];
		int ai3[] = {0, 0, 0};
		int k = 0;
		for (int l = ai2[j] + byte3; k != l; k += byte3) {
			ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
			ai3[byte1] = MathHelper.floor_double((double)ai[byte1] +
					(double)k * d + 0.5D);
			ai3[byte2] = MathHelper.floor_double((double)ai[byte2] +
					(double)k * d1 + 0.5D);
			worldObj.setTypeIdAndData(ai3[0], ai3[1], ai3[2], i, 0, 2);
		}

	}

	void b() {
		int i = 0;
		for (int j = o.length; i < j; i++) {
			int k = o[i][0];
			int l = o[i][1];
			int i1 = o[i][2];
			a(k, l, i1);
		}

	}

	boolean c(int i) {
		return (double)i >= (double)e * 0.20000000000000001D;
	}

	void c() {
		int i = basePos[0];
		int j = basePos[1];
		int k = basePos[1] + f;
		int l = basePos[2];
		int ai[] = {i, j, l};
		int ai1[] = {i, k, l};
		a(ai, ai1, 17);
		if (this.l == 2) {
			ai[0]++;
			ai1[0]++;
			a(ai, ai1, 17);
			ai[2]++;
			ai1[2]++;
			a(ai, ai1, 17);
			ai[0]--;
			ai1[0]--;
			a(ai, ai1, 17);
		}
	}

	void d() {
		int i = 0;
		int j = o.length;
		int ai[] = {basePos[0], basePos[1], basePos[2]};
		for (; i < j; i++) {
			int ai1[] = o[i];
			int ai2[] = {ai1[0], ai1[1], ai1[2]};
			ai[1] = ai1[3];
			int k = ai[1] - basePos[1];
			if (c(k)) {
				a(ai, ai2, 17);
			}
		}

	}

	int a(int pos1[], int pos2[]) {
		int posN[] = {0, 0, 0};
		int i = 0;
		for (byte byte0 = 0; byte0 < 3; byte0++) {
			posN[byte0] = pos2[byte0] - pos1[byte0];
			if (Math.abs(posN[byte0]) > Math.abs(posN[i])) {
				i = byte0;
			}
		}

		if (posN[i] == 0) {
			return -1;
		}
		byte byte1 = a[i];
		byte byte2 = a[i + 3];
		byte byte3;
		if (posN[i] > 0) {
			byte3 = 1;
		} else {
			byte3 = -1;
		}
		double d = (double)posN[byte1] / (double)posN[i];
		double d1 = (double)posN[byte2] / (double)posN[i];
		int ai3[] = {0, 0, 0};
		int j = 0;
		int k = posN[i] + byte3;
		do {
			if (j == k) {
				break;
			}
			ai3[i] = pos1[i] + j;
			ai3[byte1] = MathHelper.floor_double((double)pos1[byte1] +
					(double)j * d);
			ai3[byte2] = MathHelper.floor_double((double)pos1[byte2] +
					(double)j * d1);
			int l = worldObj.getTypeId(ai3[0], ai3[1], ai3[2]);
			if (l != 0 && l != 18) {
				break;
			}
			j += byte3;
		} while (true);
		if (j == k) {
			return -1;
		}
		return Math.abs(j);
	}

	public boolean canGenerate() {
		int pos1[] = {basePos[0], basePos[1], basePos[2]};
		int pos2[] = {basePos[0], (basePos[1] + e) - 1, basePos[2]};
		int id = worldObj.getTypeId(basePos[0], basePos[1] - 1, basePos[2]);
		if (id != 2 && id != 3) {
			return false;
		}
		int j = a(pos1, pos2);
		if (j == -1) {
			return true;
		}
		if (j < 6) {
			return false;
		}
		e = j;
		return true;
	}

	@Override
	public void a(double d, double d1, double d2) {
		m = (int)(d * 12D);
		if (d > 0.5D) {
			n = 5;
		}
		j = d1;
		k = d2;
	}

	@Override
	public boolean a(World world, Random random, int x, int y, int z) {
		worldObj = world;
		rand.setSeed(random.nextLong());
		basePos[0] = x;
		basePos[1] = y;
		basePos[2] = z;
		if (e == 0) {
			e = 5 + rand.nextInt(m);
		}
		if (!canGenerate()) {
			return false;
		}
		a();
		b();
		c();
		d();
		return true;
	}
}
