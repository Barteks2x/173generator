package com.github.barteks2x.b173gen.reflection;

import com.github.barteks2x.b173gen.exception.B173GenInitException;
import com.github.barteks2x.b173gen.exception.B173GenInitWarning;
import static com.github.barteks2x.b173gen.reflection.Util.NMSClassHelper.*;
import java.lang.reflect.*;
import java.util.*;

public class Util {
    public static void init(final List<Exception> errors, final List<B173GenInitWarning> warnings) {
        PackageHelper.init(errors, warnings);
        NMSClassHelper.init(errors, warnings);
        OBCClassHelper.init(errors, warnings);
        MethodHelper.init(errors, warnings);
    }

    private static <T extends Object> void addArray(List<T> list, T[] objects) {
        list.addAll(Arrays.asList(objects));
    }

    public static class NMSClassHelper {
        public static Class<?> Block;
        public static Class<?> ChunkProviderGenerate;
        public static Class<?> IChunkProvider;
        public static Class<?> World;
        public static Class<?> WorldGenCanyon;
        public static Class<?> WorldGenCaves;
        public static Class<?> WorldGenClay;
        public static Class<?> WorldGenDungeons;
        public static Class<?> WorldGenLakes;
        public static Class<?> WorldGenLargeFeature;
        public static Class<?> WorldGenMineshaft;
        public static Class<?> WorldGenStronghold;
        public static Class<?> WorldGenVillage;
        private static final String EXCEPTION_INFO = "NMS_CLASS_HELPER_INIT";

        private static void init(List<Exception> err, List<B173GenInitWarning> warn) {
            String pckg = PackageHelper.getNMSPackageName();
            if(pckg == null) {
                err.add(new B173GenInitException(EXCEPTION_INFO, "package name == null"));
                return;
            }
            Block = findClass(err, pckg, "Block", true);
            ChunkProviderGenerate = findClass(err, pckg, "ChunkProviderGenerate", true);
            IChunkProvider = findClass(err, pckg, "IChunkProvider", true);
            World = findClass(err, pckg, "World", false);
            WorldGenCanyon = findClass(err, pckg, "WorldGenCanyon", false);
            WorldGenCaves = findClass(err, pckg, "WorldGenCaves", false);
            WorldGenClay = findClass(err, pckg, "WorldGenClay", false);
            WorldGenDungeons = findClass(err, pckg, "WorldGenDungeons", false);
            WorldGenLakes = findClass(err, pckg, "WorldGenLakes", false);
            WorldGenLargeFeature = findClass(err, pckg, "WorldGenLargeFeature", false);
            WorldGenMineshaft = findClass(err, pckg, "WorldGenMineshaft", false);
            WorldGenStronghold = findClass(err, pckg, "WorldGenStronghold", false);
            WorldGenVillage = findClass(err, pckg, "WorldGenVillage", false);
        }

        private static Class<?> findClass(List<Exception> errors, String p, String cl, boolean isRequired) {
            String name = p.endsWith(".") ? p + cl : p + "." + cl;
            try {
                return Class.forName(name);
            } catch(ClassNotFoundException ex) {
                if(isRequired) {
                    errors.add(new B173GenInitException(ex, EXCEPTION_INFO,
                            "Couldn't find class: \"", cl, "\", full name: \"", name, "\""));
                } else {

                }
            } catch(Exception ex) {
                errors.add(new B173GenInitException(ex, EXCEPTION_INFO,
                        "Unknown exception while assessing class\"",
                        cl, "\", full name: \"", name, "\""));
            }
            return null;
        }
    }

    public static class OBCClassHelper {
        public static Class<?> CraftWorld;
        private static final String EXCEPTION_INFO = "OBC_CLASS_HELPER_INIT";

        private static void init(List<Exception> err, List<B173GenInitWarning> warn) {
            String packageName = PackageHelper.getOBCPackageName();
            if(packageName == null) {
                err.add(new B173GenInitException(EXCEPTION_INFO, "package name == null"));
                return;
            }
            CraftWorld = NMSClassHelper.findClass(err, packageName, "CraftWorld", true);
        }
    }

    public static class MethodHelper {
        public static Method CraftWorld_getHandle;
        public static Method WorldGenCanyon_generate;
        public static Method WorldGenCaves_generate;
        public static Method WorldGenClay_generate;
        public static Method WorldGenDungeons_generate;
        public static Method WorldGenLakes_generate;
        public static Method WorldGenlargeFeature_generate_populator;
        public static Method WorldGenlargeFeature_generate_generator;
        public static Method WorldGenMineshaft_generate_populator;
        public static Method WorldGenMineshaft_generate_generator;
        public static Method WorldGenStronghold_generate_populator;
        public static Method WorldGenStronghold_generate_generator;
        public static Method WorldGenVillage_generate_populator;
        public static Method WorldGenVillage_generate_generator;
        protected static final Class<?> INT = int.class;
        protected static final Class<?> BYTE = byte.class;
        protected static final Class<?> CHAR = char.class;
        protected static final Class<?> SHORT = short.class;
        protected static final Class<?> LONG = long.class;
        protected static final Class<?> BOOLEAN = boolean.class;
        protected static final Class<?> FLOAT = float.class;
        protected static final Class<?> DOUBLE = double.class;
        private static final String EXCEPTION_INFO = "METHOD_HELPER_INIT";

        private static void init(List<Exception> err, List<B173GenInitWarning> warn) {
            Class<?> clazz = OBCClassHelper.CraftWorld;
            if(clazz == null) {
                err.add(new B173GenInitException(EXCEPTION_INFO, "CraftWorld class == null"));
                return;
            }
            CraftWorld_getHandle = getMethod(err, warn, clazz, "getHandle", true);
            final Class<?> types_generate_populator[] = new Class<?>[] {World, Random.class, INT, INT, INT};
            final Class<?> types_generate_populator_structures[] = new Class<?>[] {World, Random.class, INT, INT};
            final Class<?> types_generate_generator_new[] = new Class<?>[] {IChunkProvider, World, INT, INT, Array.newInstance(Block, 0).getClass()};
            final Class<?> types_generate_generator_old[] = new Class<?>[] {IChunkProvider, World, INT, INT, byte[].class};
            //--------------------------------------------------------------------------------------, INT
            //Canyons
            clazz = NMSClassHelper.WorldGenCanyon;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenCanyon class == null"));
                return;
            }
            WorldGenCanyon_generate = findAnyOf(err, warn, "generate", WorldGenCanyon, Void.TYPE,
                    false, false, Arrays.asList(types_generate_generator_new, types_generate_generator_old));
            //--------------------------------------------------------------------------------------
            //Caves
            clazz = NMSClassHelper.WorldGenCaves;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenCaves class == null"));
                return;
            }
            WorldGenCaves_generate = findAnyOf(err, warn, "generate", WorldGenCaves, Void.TYPE,
                    false, false, Arrays.asList(types_generate_generator_new, types_generate_generator_old));
            //--------------------------------------------------------------------------------------
            //New clay generator
            clazz = NMSClassHelper.WorldGenClay;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenClay class == null"));
                return;
            }
            WorldGenClay_generate = findMethod(err, warn, "generate", WorldGenClay, BOOLEAN,
                    false, false, false, types_generate_populator);
            //--------------------------------------------------------------------------------------
            //New dungeon generator
            clazz = NMSClassHelper.WorldGenDungeons;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenDungeon class == null"));
                return;
            }
            WorldGenDungeons_generate = findMethod(err, warn, "generate", WorldGenDungeons, BOOLEAN,
                    false, false, false, types_generate_populator);
            //--------------------------------------------------------------------------------------
            //New lake generator
            clazz = NMSClassHelper.WorldGenLakes;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenLakes class == null"));
                return;
            }
            WorldGenLakes_generate = findMethod(err, warn, "generate", WorldGenLakes, BOOLEAN,
                    false, false, false, types_generate_populator);
            //--------------------------------------------------------------------------------------
            //"Large feature" generator
            clazz = NMSClassHelper.WorldGenLargeFeature;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenLargeFeature class == null"));
                return;
            }
            WorldGenlargeFeature_generate_populator = findMethod(err, warn, "generate",
                    WorldGenLargeFeature, BOOLEAN, false, false, false,
                    types_generate_populator_structures);
            WorldGenlargeFeature_generate_generator = findAnyOf(err, warn, "generate",
                    WorldGenLargeFeature, Void.TYPE, false, false,
                    Arrays.asList(types_generate_generator_new, types_generate_generator_old));
            //--------------------------------------------------------------------------------------
            //Minwshaft generator
            clazz = NMSClassHelper.WorldGenMineshaft;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenMineshaft class == null"));
                return;
            }
            WorldGenMineshaft_generate_populator = findMethod(err, warn, "generate",
                    WorldGenMineshaft, BOOLEAN, false, false, false,
                    types_generate_populator_structures);
            WorldGenMineshaft_generate_generator = findAnyOf(err, warn, "generate",
                    WorldGenMineshaft, Void.TYPE, false, false,
                    Arrays.asList(types_generate_generator_new, types_generate_generator_old));
            //--------------------------------------------------------------------------------------
            //Stronghold generator
            clazz = NMSClassHelper.WorldGenStronghold;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenStronghold class == null"));
                return;
            }
            WorldGenStronghold_generate_populator = findMethod(err, warn, "generate",
                    WorldGenStronghold, BOOLEAN, false, false, false,
                    types_generate_populator_structures);
            WorldGenStronghold_generate_generator = findAnyOf(err, warn, "generate",
                    WorldGenStronghold, Void.TYPE, false, false,
                    Arrays.asList(types_generate_generator_new, types_generate_generator_old));
            //--------------------------------------------------------------------------------------
            //Village generator
            clazz = NMSClassHelper.WorldGenVillage;
            if(clazz == null) {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, "WorldGenCanyon class == null"));
                return;
            }
            WorldGenVillage_generate_populator = findMethod(err, warn, "generate",
                    WorldGenVillage, BOOLEAN, false, false, false,
                    types_generate_populator_structures);
            WorldGenVillage_generate_generator = findAnyOf(err, warn, "generate",
                    WorldGenVillage, Void.TYPE, false, false,
                    Arrays.asList(types_generate_generator_new, types_generate_generator_old));
        }

        private static Method getMethod(List<Exception> errors, List<B173GenInitWarning> warn,
                Class<?> cl, String name, boolean required, Class... paramTypes) {
            try {
                if(paramTypes == null || paramTypes.length == 0 || paramTypes[0] == null) {
                    return cl.getDeclaredMethod(name);
                }
                return cl.getDeclaredMethod(name, paramTypes);
            } catch(NoSuchMethodException ex) {
                String[] msg = new String[] {
                    "Couldn't find method: \"", name, "\", in class: \"", cl.getName(), "\""};
                if(required) {
                    errors.add(new B173GenInitException(ex, EXCEPTION_INFO, msg));
                } else {
                    warn.add(new B173GenInitWarning(EXCEPTION_INFO, msg));
                }
            } catch(SecurityException ex) {
                String[] msg = new String[] {
                    "Access denied! Method: \"", name, "\", in class: \"", cl.getName(), "\""};
                if(required) {
                    errors.add(new B173GenInitException(ex, EXCEPTION_INFO, msg));
                } else {
                    warn.add(new B173GenInitWarning(EXCEPTION_INFO, msg));
                }
            } catch(Exception ex) {
                String[] msg = new String[] {
                    "Unknown exception while accessing method \"",
                    name, "\", in class: \"", cl.getName(), "\""};
                if(required) {
                    errors.add(new B173GenInitException(ex, EXCEPTION_INFO, msg));
                } else {
                    warn.add(new B173GenInitWarning(EXCEPTION_INFO, msg));
                }
            }
            return null;
        }

        private static Method findMethod(List<Exception> errors, List<B173GenInitWarning> warn,
                String deobfName, Class<?> cl, Class<?> retType, boolean isStatic,
                boolean accessibleOlny, boolean required, Class... paramTypes) {
            LinkedList<Method> methods = new LinkedList<Method>();
            Class<?> cl_temp = cl;
            addArray(methods, cl.getDeclaredMethods());
            while(!cl_temp.getSuperclass().equals(Object.class)) {//Find all superclass methods
                cl_temp = cl_temp.getSuperclass();
                addArray(methods, cl_temp.getDeclaredMethods());
            }
            search:
            for(Method m: methods) {
                if(!m.isAccessible()) {
                    if(!accessibleOlny) {
                        m.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                if(m.getParameterTypes().length != paramTypes.length) {
                    continue;
                }
                if(Modifier.isStatic(m.getModifiers()) != isStatic) {
                    continue;
                }
                if(m.getReturnType() != retType) {
                    continue;
                }
                Class<?> types[] = m.getParameterTypes();

                for(int i = 0; i < paramTypes.length; ++i) {
                    if(!types[i].getName().equals(paramTypes[i].getName())) {
                        continue search;
                    }
                }
                return m;
            }
            String[] msg = new String[] {
                "Couldn't find method (deobfuscated name: \"", deobfName,
                "\") in class: \"", cl.getName(),
                "\", return type: \"", retType.getName(),
                "\", parameters: \"", getClassNameArray(paramTypes),
                "\", isStatic: ", Boolean.toString(isStatic)};
            if(required) {
                errors.add(new B173GenInitException(EXCEPTION_INFO, msg));
            } else {
                warn.add(new B173GenInitWarning(EXCEPTION_INFO, msg));
            }
            return null;
        }

        private static Method findAnyOf(List<Exception> errors, List<B173GenInitWarning> warn,
                String deobfName, Class<?> cl, Class<?> retType, boolean isStatic,
                boolean accessibleOlny, List<Class<?>[]> types) {
            LinkedList<Exception> errTemp = new LinkedList<Exception>();
            LinkedList<B173GenInitWarning> warnTemp = new LinkedList<B173GenInitWarning>();
            Method m;
            for(Class<?>[] typeArray: types) {
                m = findMethod(errTemp, warnTemp, deobfName, cl, retType, isStatic, accessibleOlny,
                        false, typeArray);
                if(m != null) {
                    return m;
                }
            }
            warn.addAll(warnTemp);
            return null;
        }

        private static String getClassNameArray(Class clazz[]) {
            if(clazz == null || clazz.length == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder(clazz[0].getName());
            for(int i = 1; i < clazz.length; ++i) {
                sb.append(", ").append(clazz[i].getName());
            }
            return sb.toString();
        }
    }

    private static class PackageHelper {
        private static final String EXCEPTION_INFO = "PACKAGE_HELPER_INIT";
        private static String NMS_PACKAGE = null;
        private static String OBC_PACKAGE = null;

        public static void init(List<Exception> err, List<B173GenInitWarning> warn) {

            String[] packagesToFind = new String[] {
                "net.minecraft.server",
                "org.bukkit.craftbukkit"
            };
            String[] packages = findPackages(packagesToFind, true, false);
            if(packages[0] == null) {
                err.add(new B173GenInitException(
                        EXCEPTION_INFO, "Couldn't find net.minecraft.server.* package!"));
            }
            NMS_PACKAGE = packages[0];
            if(packages[1] == null) {
                err.add(new B173GenInitException(
                        EXCEPTION_INFO, "Couldn't find org.bukkit.craftbukkit.* package!"));
            }
            OBC_PACKAGE = packages[1];
        }

        public static String getNMSPackageName() {
            return NMS_PACKAGE;
        }

        public static String getOBCPackageName() {
            return OBC_PACKAGE;
        }

        private static String[] findPackages(String pckgs[], boolean... canEqual) {
            Package ps[] = Package.getPackages();
            String pname[] = new String[pckgs.length];
            for(Package p: ps) {
                int i = 0;
                for(String pckg: pckgs) {
                    if(p.getName().startsWith(pckg)) {
                        if(pname[i] == null || p.getName().length() < pname[i].length()) {
                            if(!canEqual[i] && p.getName().replaceFirst(pckgs[i], "").isEmpty()) {
                                continue;
                            }
                            pname[i] = p.getName();
                        }
                    }
                    ++i;
                }
            }
            return pname;
        }
    }
}
