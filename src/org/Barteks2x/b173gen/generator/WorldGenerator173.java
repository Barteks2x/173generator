package org.Barteks2x.b173gen.generator;

import java.util.Random;
import net.minecraft.server.v1_5_R1.World;
import net.minecraft.server.v1_5_R1.WorldGenerator;

public abstract class WorldGenerator173 extends WorldGenerator {

	public boolean generate(World w, Random r, int i, int j, int k){
		return this.a(w, r, i, j, k);
	}
}
