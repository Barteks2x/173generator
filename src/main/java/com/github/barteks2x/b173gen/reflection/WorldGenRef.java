package com.github.barteks2x.b173gen.reflection;

import com.github.barteks2x.b173gen.generator.WorldGenerator173;
import com.github.barteks2x.b173gen.newgen.WorldGenClayRef;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;

public abstract class WorldGenRef extends WorldGenerator173 {

	protected final Object worldGenObject;
	protected final Method generate;
	protected final Method world_getHandle;

	protected WorldGenRef(String className, Object... params) {
		Class<?> cl = Util.getNMSClass(className);
		worldGenObject = Util.newInstance(cl, params);
		generate = Util.
				getMethod(cl, boolean.class, false, false, Util.NMS_WORLD_CLASS, Random.class,
				int.class, int.class, int.class);
		world_getHandle = Util.getMethodByName("getHandle", Util.getOBCClass("CraftWorld"));
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		try {
			Object nmsWorld = world_getHandle.invoke(world);
			return (Boolean)generate.invoke(worldGenObject, nmsWorld, rand, x, y, z);
		} catch (SecurityException ex) {
			Logger.getLogger(WorldGenClayRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenClayRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenClayRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenClayRef.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
}
