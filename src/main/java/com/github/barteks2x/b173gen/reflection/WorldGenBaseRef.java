package com.github.barteks2x.b173gen.reflection;

import com.github.barteks2x.b173gen.WorldGenBaseOld;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;

public class WorldGenBaseRef extends WorldGenBaseOld {

	protected Class<?> cl;
	protected Object worldGenBaseObject;
	protected Method generate1, generate2;
	protected Method world_getHandle;

	public WorldGenBaseRef(String className) {
		cl = Util.getNMSClass(className);
		worldGenBaseObject = Util.newInstance(cl);
		generate1 = Util.getMethod(cl, Void.TYPE, false, false, Util.NMS_CPG_CLASS,
				Util.NMS_WORLD_CLASS, Random.class, int.class, int.class, byte[].class);
		generate2 = Util.getMethod(cl, Void.TYPE, false, false, Util.NMS_WORLD_CLASS, int.class,
				int.class, int.class, int.class, byte[].class);
		world_getHandle = Util.getMethodByName("getHandle", Util.getOBCClass("CraftWorld"));
	}

	@Override
	public void generate(World world, int i, int j, byte abyte0[]) {
		try {
			Object nmsWorld = world_getHandle.invoke(world);
			generate1.invoke(worldGenBaseObject, null, nmsWorld, i, j, abyte0);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void generate(World world, int i1, int j1, int i, int j, byte abyte0[]) {
		try {
			Object nmsWorld = world_getHandle.invoke(world);
			generate2.invoke(worldGenBaseObject, nmsWorld, i1, j1, i, j, abyte0);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenBaseRef.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
