package com.github.barteks2x.b173gen.oldgen;

@SuppressWarnings("cast")
public class MathHelper {

	public static float sin(float f) {
		return SIN_TABLE[(int)(f * 10430.38F) & 0xffff];
	}

	public static float cos(float f) {
		return SIN_TABLE[(int)(f * 10430.38F + 16384F) & 0xffff];
	}

	public static float sqrt(float f) {
		return (float)Math.sqrt(f);
	}

	public static float sqrt(double d) {
		return (float)Math.sqrt(d);
	}

	public static int floor(float f) {
		int i = (int)f;
		return f >= (float)i ? i : i - 1;
	}

	public static int floor(double d) {
		int i = (int)d;
		return d >= (double)i ? i : i - 1;
	}

	public static float abs(float f) {
		return f < 0.0F ? -f : f;
	}
	private static float SIN_TABLE[];

	static {
		SIN_TABLE = new float[0x10000];
		for (int i = 0; i < 0x10000; i++) {
			SIN_TABLE[i] = (float)Math.sin((i * Math.PI * 2D) / 65536D);
		}

	}
}
