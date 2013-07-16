package com.github.barteks2x.b173gen.reflection;

import com.github.barteks2x.b173gen.oldgen.WorldGenLakesOld;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.World;

public class Util {

	public static final Class<?> NMS_WORLD_CLASS = getNMSWorldClass();
	public static final Class<?> NMS_CPG_CLASS = getNMSCpgClass();
	public static final Class<?> NMS_ENUM_SKY_BLOCK_CLASS = getNMSClass("EnumSkyBlock");
	public static final Method NMS_WORLD_B_SKY = getNMSWORLD_B_SKY();

	static Class<?> getOBCClass(String name) {
		String pname = "org.bukkit.craftbukkit" + getNMSPackageName().
				replace("net.minecraft.server", "");

		try {
			if (pname.endsWith(".")) {
				return Class.forName(pname + name);
			}
			return Class.forName(pname + "." + name);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private static Method getNMSWORLD_B_SKY() {
		try {
			Class<?> enumSkyBlockClass = Util.getNMSClass("EnumSkyBlock");
			Method nmsWorld_b_SKY = Util.getMethod(NMS_WORLD_CLASS, int.class, false, false,
					enumSkyBlockClass, int.class, int.class, int.class);
			return nmsWorld_b_SKY;
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private static String getNMSPackageName() {
		Package ps[] = Package.getPackages();
		String pname = null;
		for (Package p : ps) {
			if (p.getName().startsWith("net.minecraft.server")) {
				pname = p.getName();
				break;
			}
		}
		return pname;
	}

	private static Class<?> getNMSWorldClass() {
		return getNMSClass("World");
	}

	private static Class<?> getNMSCpgClass() {
		return getNMSClass("ChunkProviderGenerate");
	}

	public static Class<?> getNMSClass(String name) {
		String pname = getNMSPackageName();
		try {
			if (pname.endsWith(".")) {
				return Class.forName(pname + name);
			}
			return Class.forName(pname + "." + name);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Object newInstance(Class<?> cl, Object... par) {
		Class<?> paramTypes[];
		if (par != null && par.length != 0 && par[0] != null) {
			paramTypes = new Class<?>[par.length];
			for (int i = 0; i < par.length; ++i) {
				paramTypes[i] = par[i].getClass();
				if (paramTypes[i] == Integer.class) {
					paramTypes[i] = int.class;
				}
				if (paramTypes[i] == Boolean.class) {
					paramTypes[i] = boolean.class;
				}
			}
		} else {
			paramTypes = null;
		}
		try {
			if (paramTypes != null) {
				return cl.getConstructor(paramTypes).newInstance(par);
			}
			return cl.getConstructor().newInstance();
		} catch (InstantiationException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Object newInstance(Class<?> cl) {
		try {
			return cl.getConstructor().newInstance();
		} catch (InstantiationException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Method getMethod(Class<?> cl, Class<?> retType, boolean isStatic,
			boolean accessibleOlny, Class... paramTypes) {
		ArrayList<Method> methods = new ArrayList<Method>(20);
		addArray(methods, cl.getDeclaredMethods());
		addArray(methods, cl.getSuperclass().getDeclaredMethods());
		search:
		for (Method m : methods) {
			if (!m.isAccessible()) {
				if (!accessibleOlny) {
					m.setAccessible(true);
				} else {
					continue;
				}
			}
			if (m.getParameterTypes().length != paramTypes.length) {
				continue;
			}
			if (Modifier.isStatic(m.getModifiers()) != isStatic) {
				continue;
			}
			if (m.getReturnType() != retType) {
				continue;
			}
			Class<?> types[] = m.getParameterTypes();

			for (int i = 0; i < paramTypes.length; ++i) {
				if (!types[i].getName().equals(paramTypes[i].getName())) {
					continue search;
				}
			}
			return m;
		}
		return null;
	}

	public static Method getMethodByName(String name, Class<?> cl, Class... paramTypes) {
		try {
			if (paramTypes == null || paramTypes.length == 0 || paramTypes[0] == null) {
				return cl.getDeclaredMethod(name);
			}
			return cl.getDeclaredMethod(name, paramTypes);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private static <T extends Object> void addArray(List<T> list, T[] objects) {
		list.addAll(Arrays.asList(objects));
	}

	public static boolean isSolid(World w, int x, int y, int z) {
		try {
			Object nmsWorldObj = Util.getMethodByName("getHandle", w.getClass()).invoke(w);
			Method getMaterial = Util.getMethodByName("getMaterial", NMS_WORLD_CLASS,
					int.class, int.class, int.class);
			Object material = getMaterial.invoke(nmsWorldObj, x, y, z);
			Method isSolid = Util.getMethodByName("isSolid", material.getClass());
			return (Boolean)isSolid.invoke(material);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	public static int nmsWorld_b_SKY(World w, int i1, int i2, int i3) {
		try {
			Object nmsWorldObj = Util.getMethodByName("getHandle", w.getClass()).invoke(w);
			Field enumSkyBlockField = Util.NMS_ENUM_SKY_BLOCK_CLASS.getField("SKY");
			Object enumSkyBlockObj = enumSkyBlockField.get(null);
			Method nmsWorld_b_SKY = Util.NMS_WORLD_B_SKY;
			return (Integer)nmsWorld_b_SKY.invoke(nmsWorldObj, enumSkyBlockObj, i1, i2, i3);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(WorldGenLakesOld.class.getName()).log(Level.SEVERE, null, ex);
		}
		return 0;
	}

	public static boolean isLiquid(Material m) {
		if (m.toString().toLowerCase().contains("water") || m.toString().toLowerCase().contains(
				"lava")) {
			return true;
		}
		return false;
	}
}
