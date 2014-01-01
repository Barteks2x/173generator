package com.github.barteks2x.b173gen.reflection;

import static com.github.barteks2x.b173gen.reflection.Util.MethodHelper.*;
import java.lang.reflect.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;

public class ReflectionHelper {

    public static Object CraftWorld_getHandle(World w) {
        return invoke(CraftWorld_getHandle, w);
    }

    public static void WorldGenCanyon_generate(Object worldGenCanyon, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenCanyon_generate, worldGenCanyon, world, x, z, blocks);
    }

    public static void WorldGenCanyon_generate(Object worldGenCanyon, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenCanyon_generate, worldGenCanyon, world, x, z, blocks);
    }

    public static void WorldGenCaves_generate(Object worldGenCaves, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenCaves_generate, worldGenCaves, world, x, z, blocks);
    }

    public static void WorldGenCaves_generate(Object worldGenCaves, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenCaves_generate, worldGenCaves, world, x, z, blocks);
    }

    public static boolean WorldGenClay_generate(Object worldGenClay, World world, Random rand, int x, int y, int z) {
        Boolean ret = (Boolean)invoke(WorldGenClay_generate, worldGenClay, world, rand, x, y, z);
        return ret == null ? false : ret;//can't cast null to primitive (boolean), return default value if null
    }

    public static boolean WorldGenDungeons_generate(Object worldGenDungeons, World world, Random rand, int x, int y, int z) {
        Boolean ret = (Boolean)invoke(WorldGenDungeons_generate, worldGenDungeons, world, rand, x, y, z);
        return ret == null ? false : ret;
    }

    public static boolean WorldGenLakes_generate(Object worldGenLakes, World world, Random rand, int x, int y, int z) {
        Boolean ret = (Boolean)invoke(WorldGenLakes_generate, worldGenLakes, world, rand, x, y, z);
        return ret == null ? false : ret;
    }

    public static boolean WorldGenlargeFeature_generate_populator(Object worldGenLargeFeature, World world, Random rand, int x, int z) {
        Boolean ret = (Boolean)invoke(WorldGenlargeFeature_generate_populator, worldGenLargeFeature, world, rand, x, z);
        return ret == null ? false : ret;
    }

    public static void WorldGenlargeFeature_generate_generator(Object worldGenLargeFeature, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenlargeFeature_generate_generator, worldGenLargeFeature, world, x, z, blocks);
    }

    public static void WorldGenlargeFeature_generate_generator(Object worldGenLargeFeature, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenlargeFeature_generate_generator, worldGenLargeFeature, world, x, z, blocks);
    }

    public static boolean WorldGenMineshaft_generate_populator(Object worldGenMineshaft, World world, Random rand, int x, int z) {
        Boolean ret = (Boolean)invoke(WorldGenMineshaft_generate_populator, worldGenMineshaft, world, rand, x, z);
        return ret == null ? false : ret;
    }

    public static void WorldGenMineshaft_generate_generator(Object worldGenMineshaft, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenMineshaft_generate_generator, worldGenMineshaft, world, x, z, blocks);
    }

    public static void WorldGenMineshaft_generate_generator(Object worldGenMineshaft, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenMineshaft_generate_generator, worldGenMineshaft, world, x, z, blocks);
    }

    public static boolean WorldGenStronghold_generate_populator(Object worldGenStronghold, World world, Random rand, int x, int z) {
        Boolean ret = (Boolean)invoke(WorldGenStronghold_generate_populator, worldGenStronghold, world, rand, x, z);
        return ret == null ? false : ret;
    }

    public static void WorldGenStronghold_generate_generator(Object worldGenStronghold, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenStronghold_generate_generator, worldGenStronghold, world, x, z, blocks);
    }

    public static void WorldGenStronghold_generate_generator(Object worldGenStronghold, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenStronghold_generate_generator, worldGenStronghold, world, x, z, blocks);
    }

    public static boolean WorldGenVillage_generate_populator(Object worldGenVillage, World world, Random rand, int x, int z) {
        Boolean ret = (Boolean)invoke(WorldGenVillage_generate_populator, worldGenVillage, world, rand, x, z);
        return ret == null ? false : ret;
    }

    public static void WorldGenVillage_generate_generator(Object worldGenVillage, World world, int x, int z, Object[] blocks) {
        invoke(WorldGenVillage_generate_generator, worldGenVillage, world, x, z, blocks);
    }

    public static void WorldGenVillage_generate_generator(Object worldGenVillage, World world, int x, int z, byte[] blocks) {
        invoke(WorldGenVillage_generate_generator, worldGenVillage, world, x, z, blocks);
    }

    public static Object newInstance(Class<?> cl, Object[] par, Class<?>[] paramTypes) {//parameter type array is required (primitive types are automatically changed to Objects (int --> Integer) when adding tom array)
        //param types and paramerer array length must be the same (or null)
        //assert (paramTypes != null && par != null) ? par.length != paramTypes.length : (par == null) != (paramTypes == null);
        try {
            if(paramTypes != null && paramTypes.length != 0) {
                Constructor c = cl.getConstructor(paramTypes);
                c.setAccessible(true);
                return c.newInstance(par);
            }
            Constructor c = cl.getConstructor(paramTypes);
            c.setAccessible(true);
            return c.newInstance();
        } catch(InstantiationException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);//TODO logging
        } catch(IllegalAccessException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IllegalArgumentException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch(InvocationTargetException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NoSuchMethodException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SecurityException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Object invoke(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch(IllegalAccessException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IllegalArgumentException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch(InvocationTargetException ex) {
            Logger.getLogger(ReflectionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
