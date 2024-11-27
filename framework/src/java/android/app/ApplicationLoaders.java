package android.app;

/* loaded from: classes.dex */
public class ApplicationLoaders {
    private static final java.lang.String TAG = "ApplicationLoaders";
    private static final android.app.ApplicationLoaders gApplicationLoaders = new android.app.ApplicationLoaders();
    private final android.util.ArrayMap<java.lang.String, java.lang.ClassLoader> mLoaders = new android.util.ArrayMap<>();
    private java.util.Map<java.lang.String, android.app.ApplicationLoaders.CachedClassLoader> mSystemLibsCacheMap = null;

    public static android.app.ApplicationLoaders getDefault() {
        return gApplicationLoaders;
    }

    java.lang.ClassLoader getClassLoader(java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, java.lang.String str4) {
        return getClassLoaderWithSharedLibraries(str, i, z, str2, str3, classLoader, str4, null, null, null);
    }

    java.lang.ClassLoader getClassLoaderWithSharedLibraries(java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, java.lang.String str4, java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.ClassLoader> list3) {
        return getClassLoader(str, i, z, str2, str3, classLoader, str, str4, list, list2, list3);
    }

    java.lang.ClassLoader getSharedLibraryClassLoaderWithSharedLibraries(java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, java.lang.String str4, java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.ClassLoader> list2) {
        java.lang.ClassLoader cachedNonBootclasspathSystemLib = getCachedNonBootclasspathSystemLib(str, classLoader, str4, list);
        if (cachedNonBootclasspathSystemLib != null) {
            return cachedNonBootclasspathSystemLib;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ALL");
        return getClassLoaderWithSharedLibraries(str, i, z, str2, str3, classLoader, str4, list, arrayList, list2);
    }

    private java.lang.ClassLoader getClassLoader(java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, java.lang.String str4, java.lang.String str5, java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.ClassLoader> list3) {
        java.lang.ClassLoader classLoader2;
        java.lang.ClassLoader parent = java.lang.ClassLoader.getSystemClassLoader().getParent();
        synchronized (this.mLoaders) {
            if (classLoader != null) {
                classLoader2 = classLoader;
            } else {
                classLoader2 = parent;
            }
            if (classLoader2 == parent) {
                java.lang.ClassLoader classLoader3 = this.mLoaders.get(str4);
                if (classLoader3 != null) {
                    return classLoader3;
                }
                android.os.Trace.traceBegin(64L, str);
                java.lang.ClassLoader createClassLoader = com.android.internal.os.ClassLoaderFactory.createClassLoader(str, str2, str3, classLoader2, i, z, str5, list, list2, list3);
                android.os.Trace.traceEnd(64L);
                android.os.Trace.traceBegin(64L, "setLayerPaths");
                android.os.GraphicsEnvironment.getInstance().setLayerPaths(createClassLoader, str2, str3);
                android.os.Trace.traceEnd(64L);
                if (str4 != null) {
                    this.mLoaders.put(str4, createClassLoader);
                }
                return createClassLoader;
            }
            android.os.Trace.traceBegin(64L, str);
            java.lang.ClassLoader createClassLoader2 = com.android.internal.os.ClassLoaderFactory.createClassLoader(str, null, classLoader2, str5, list, null);
            android.os.Trace.traceEnd(64L);
            return createClassLoader2;
        }
    }

    public void createAndCacheNonBootclasspathSystemClassLoaders(java.util.List<android.content.pm.SharedLibraryInfo> list) {
        if (this.mSystemLibsCacheMap != null) {
            throw new java.lang.IllegalStateException("Already cached.");
        }
        this.mSystemLibsCacheMap = new java.util.HashMap();
        for (int i = 0; i < list.size(); i++) {
            createAndCacheNonBootclasspathSystemClassLoader(list.get(i));
        }
    }

    private void createAndCacheNonBootclasspathSystemClassLoader(android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        java.util.ArrayList arrayList;
        java.lang.String path = sharedLibraryInfo.getPath();
        java.util.List<android.content.pm.SharedLibraryInfo> dependencies = sharedLibraryInfo.getDependencies();
        if (dependencies == null) {
            arrayList = null;
        } else {
            java.util.ArrayList arrayList2 = new java.util.ArrayList(dependencies.size());
            java.util.Iterator<android.content.pm.SharedLibraryInfo> it = dependencies.iterator();
            while (it.hasNext()) {
                java.lang.String path2 = it.next().getPath();
                android.app.ApplicationLoaders.CachedClassLoader cachedClassLoader = this.mSystemLibsCacheMap.get(path2);
                if (cachedClassLoader == null) {
                    throw new java.lang.IllegalStateException("Failed to find dependency " + path2 + " of cachedlibrary " + path);
                }
                arrayList2.add(cachedClassLoader.loader);
            }
            arrayList = arrayList2;
        }
        java.lang.ClassLoader classLoader = getClassLoader(path, android.os.Build.VERSION.SDK_INT, true, null, null, null, null, null, arrayList, null, null);
        if (classLoader == null) {
            throw new java.lang.IllegalStateException("Failed to cache " + path);
        }
        android.app.ApplicationLoaders.CachedClassLoader cachedClassLoader2 = new android.app.ApplicationLoaders.CachedClassLoader();
        cachedClassLoader2.loader = classLoader;
        cachedClassLoader2.sharedLibraries = arrayList;
        android.util.Log.d(TAG, "Created zygote-cached class loader: " + path);
        this.mSystemLibsCacheMap.put(path, cachedClassLoader2);
    }

    private static boolean sharedLibrariesEquals(java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.ClassLoader> list2) {
        if (list == null) {
            return list2 == null;
        }
        return list.equals(list2);
    }

    public java.lang.ClassLoader getCachedNonBootclasspathSystemLib(java.lang.String str, java.lang.ClassLoader classLoader, java.lang.String str2, java.util.List<java.lang.ClassLoader> list) {
        android.app.ApplicationLoaders.CachedClassLoader cachedClassLoader;
        if (this.mSystemLibsCacheMap == null || classLoader != null || str2 != null || (cachedClassLoader = this.mSystemLibsCacheMap.get(str)) == null) {
            return null;
        }
        if (!sharedLibrariesEquals(list, cachedClassLoader.sharedLibraries)) {
            android.util.Log.w(TAG, "Unexpected environment loading cached library " + str + " (real|cached): (" + list + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + cachedClassLoader.sharedLibraries + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            return null;
        }
        android.util.Log.d(TAG, "Returning zygote-cached class loader: " + str);
        return cachedClassLoader.loader;
    }

    public java.lang.ClassLoader createAndCacheWebViewClassLoader(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return getClassLoader(str, android.os.Build.VERSION.SDK_INT, false, str2, null, null, str3, null, null, null, null);
    }

    void addPath(java.lang.ClassLoader classLoader, java.lang.String str) {
        if (!(classLoader instanceof dalvik.system.PathClassLoader)) {
            throw new java.lang.IllegalStateException("class loader is not a PathClassLoader");
        }
        ((dalvik.system.PathClassLoader) classLoader).addDexPath(str);
    }

    void addNative(java.lang.ClassLoader classLoader, java.util.Collection<java.lang.String> collection) {
        if (!(classLoader instanceof dalvik.system.PathClassLoader)) {
            throw new java.lang.IllegalStateException("class loader is not a PathClassLoader");
        }
        ((dalvik.system.PathClassLoader) classLoader).addNativePath(collection);
    }

    private static class CachedClassLoader {
        java.lang.ClassLoader loader;
        java.util.List<java.lang.ClassLoader> sharedLibraries;

        private CachedClassLoader() {
        }
    }
}
