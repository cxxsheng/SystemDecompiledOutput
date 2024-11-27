package com.android.internal.os;

/* loaded from: classes4.dex */
public final class SystemServerClassLoaderFactory {
    private static final android.util.ArrayMap<java.lang.String, dalvik.system.PathClassLoader> sLoadedPaths = new android.util.ArrayMap<>();

    static dalvik.system.PathClassLoader createClassLoader(java.lang.String str, java.lang.ClassLoader classLoader) {
        if (sLoadedPaths.containsKey(str)) {
            throw new java.lang.IllegalStateException("A ClassLoader for " + str + " already exists");
        }
        dalvik.system.PathClassLoader pathClassLoader = (dalvik.system.PathClassLoader) com.android.internal.os.ClassLoaderFactory.createClassLoader(str, null, null, classLoader, android.os.Build.VERSION.SDK_INT, true, null);
        sLoadedPaths.put(str, pathClassLoader);
        return pathClassLoader;
    }

    public static dalvik.system.PathClassLoader getOrCreateClassLoader(java.lang.String str, java.lang.ClassLoader classLoader, boolean z) {
        dalvik.system.PathClassLoader pathClassLoader = sLoadedPaths.get(str);
        if (pathClassLoader != null) {
            return pathClassLoader;
        }
        if (!allowClassLoaderCreation(str, z)) {
            throw new java.lang.RuntimeException("Creating a ClassLoader from " + str + " is not allowed. Please make sure that the jar is listed in `PRODUCT_APEX_STANDALONE_SYSTEM_SERVER_JARS` in the Makefile and added as a `standalone_contents` of a `systemserverclasspath_fragment` in `Android.bp`.");
        }
        return createClassLoader(str, classLoader);
    }

    private static boolean allowClassLoaderCreation(java.lang.String str, boolean z) {
        return !str.startsWith("/apex/") || z || com.android.internal.os.ZygoteInit.shouldProfileSystemServer();
    }
}
