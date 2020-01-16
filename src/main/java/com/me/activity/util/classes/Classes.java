package com.me.activity.util.classes;


import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @program: activity
 * @description: classes
 * @author: lic
 * @create: 2020-01-16 11:01
 **/
public class Classes {
    private Classes(){}

    private static final ClassLoader THREAD_LOADER = Thread.currentThread().getContextClassLoader();

    private static final ClassLoader CLASS_LOADER = Classes.class.getClassLoader();

    private static final ClassLoader SYSTEM_LOADER = ClassLoader.getSystemClassLoader();


    public static <T> Class<T> forName(String fqcn) throws UnknownClassException, ClassNotFoundException {

        Class clazz = THREAD_LOADER.loadClass(fqcn);

        if (clazz == null) {
            clazz = CLASS_LOADER.loadClass(fqcn);
        }

        if (clazz == null) {
            clazz = SYSTEM_LOADER.loadClass(fqcn);
        }

        if (clazz == null) {
            String msg = "Unable to load class named [" + fqcn + "] from the thread context, current, or " +
                    "system/application ClassLoaders.  All heuristics have been exhausted.  Class could not be found.";

            if (fqcn != null && fqcn.startsWith("com.stormpath.sdk.impl")) {
                msg += "  Have you remembered to include the stormpath-sdk-impl .jar in your runtime classpath?";
            }

            throw new UnknownClassException(msg);
        }

        return clazz;
    }


    public static InputStream getResourceAsStream(String name) {

        InputStream is = THREAD_LOADER.getResourceAsStream(name);

        if (is == null) {
            is = CLASS_LOADER.getResourceAsStream(name);
        }

        if (is == null) {
            is = SYSTEM_LOADER.getResourceAsStream(name);
        }

        return is;
    }

    public static boolean isAvailable(String fullyQualifiedClassName) {
        try {
            forName(fullyQualifiedClassName);
            return true;
        } catch (UnknownClassException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            String msg = "Class method parameter cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new InstantiationException("Unable to instantiate class [" + clazz.getName() + "]", e);
        }
    }
    public static <T> T newInstance(String fqcn) throws ClassNotFoundException {
        Class<T> aClass = forName(fqcn);
        return newInstance(aClass);
    }

    public static <T> T newInstance(Class<T> clazz, Object... args) {
        Class[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Constructor<T> ctor = getConstructor(clazz, argTypes);
        return instantiate(ctor, args);
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class... argTypes) {
        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }

    }

    public static <T> T instantiate(Constructor<T> ctor, Object... args) {
        try {
            return ctor.newInstance(args);
        } catch (Exception e) {
            String msg = "Unable to instantiate instance with constructor [" + ctor + "]";
            throw new InstantiationException(msg, e);
        }
    }

    public static <T> T newInstance(String fqcn, Object... args) throws ClassNotFoundException {
        return (T)newInstance(forName(fqcn), args);
    }

    public static <T> T invokeStatic(String fqcn, String methodName, Class[] argTypes, Object... args) {
        try {
            Class clazz = Classes.forName(fqcn);
            Method method = clazz.getDeclaredMethod(methodName, argTypes);
            method.setAccessible(true);
            return(T)method.invoke(null, args);
        } catch (Exception e) {
            String msg = "Unable to invoke class method " + fqcn + "#" + methodName + ".  Ensure the necessary " +
                    "implementation is in the runtime classpath.";
            throw new IllegalStateException(msg, e);
        }
    }
}
