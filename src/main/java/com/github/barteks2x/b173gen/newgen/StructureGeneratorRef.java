package com.github.barteks2x.b173gen.newgen;

import com.github.barteks2x.b173gen.reflection.Util;
import com.github.barteks2x.b173gen.reflection.WorldGenBaseRef;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;

public abstract class StructureGeneratorRef extends WorldGenBaseRef {

	protected Method generate3;

	public StructureGeneratorRef(String name) {
		super(name);
		generate3 = Util.getMethod(cl, boolean.class, false, true, Util.NMS_WORLD_CLASS,
				Random.class, int.class, int.class);

	}

	public boolean generate(World world, Random random, int i, int j) {
		try {
			Object nmsWorld = world_getHandle.invoke(world);
			return (Boolean)generate3.invoke(worldGenBaseObject, nmsWorld, random, i, j);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(StructureGeneratorRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(StructureGeneratorRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(StructureGeneratorRef.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
}
