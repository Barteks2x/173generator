package org.Barteks2x.b173gen.generator.beta173;

@SuppressWarnings("cast")
public class MathHelper {

	public MathHelper() {
	}

	public static float sin(float f) {
		return SIN_TABLE[(int) (f * 10430.38F) & 0xffff];
	}

	public static float cos(float f) {
		return SIN_TABLE[(int) (f * 10430.38F + 16384F) & 0xffff];
	}

	public static float sqrt_float(float f) {
		return (float) Math.sqrt(f);
	}

	public static float sqrt_double(double d) {
		return (float) Math.sqrt(d);
	}

	public static int floor_float(float f) {
		int i = (int) f;
		return f >= (float) i ? i : i - 1;
	}

	public static int floor_double(double d) {
		int i = (int) d;
		return d >= (double) i ? i : i - 1;
	}

	public static float abs(float f) {
		return f < 0.0F ? -f : f;
	}


	private static float SIN_TABLE[];

	static {
		SIN_TABLE = new float[0x10000];
		for (int i = 0; i < 0x10000; i++) {
			SIN_TABLE[i] = (float) Math
					.sin(((double) i * 3.1415926535897931D * 2D) / 65536D);
		}

	}
}
