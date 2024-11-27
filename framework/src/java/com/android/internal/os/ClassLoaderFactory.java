package com.android.internal.os;

/* loaded from: classes4.dex */
public class ClassLoaderFactory {
    private static final java.lang.String PATH_CLASS_LOADER_NAME = dalvik.system.PathClassLoader.class.getName();
    private static final java.lang.String DEX_CLASS_LOADER_NAME = dalvik.system.DexClassLoader.class.getName();
    private static final java.lang.String DELEGATE_LAST_CLASS_LOADER_NAME = dalvik.system.DelegateLastClassLoader.class.getName();

    private static native java.lang.String createClassloaderNamespace(java.lang.ClassLoader classLoader, int i, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4);

    private ClassLoaderFactory() {
    }

    public static java.lang.String getPathClassLoaderName() {
        return PATH_CLASS_LOADER_NAME;
    }

    public static boolean isValidClassLoaderName(java.lang.String str) {
        return str != null && (isPathClassLoaderName(str) || isDelegateLastClassLoaderName(str));
    }

    public static boolean isPathClassLoaderName(java.lang.String str) {
        return str == null || PATH_CLASS_LOADER_NAME.equals(str) || DEX_CLASS_LOADER_NAME.equals(str);
    }

    public static boolean isDelegateLastClassLoaderName(java.lang.String str) {
        return DELEGATE_LAST_CLASS_LOADER_NAME.equals(str);
    }

    public static java.lang.ClassLoader createClassLoader(java.lang.String str, java.lang.String str2, java.lang.ClassLoader classLoader, java.lang.String str3, java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.ClassLoader> list2) {
        java.lang.ClassLoader[] classLoaderArr;
        java.lang.ClassLoader[] classLoaderArr2;
        if (list == null) {
            classLoaderArr = null;
        } else {
            classLoaderArr = (java.lang.ClassLoader[]) list.toArray(new java.lang.ClassLoader[list.size()]);
        }
        if (list2 == null) {
            classLoaderArr2 = null;
        } else {
            classLoaderArr2 = (java.lang.ClassLoader[]) list2.toArray(new java.lang.ClassLoader[list2.size()]);
        }
        if (isPathClassLoaderName(str3)) {
            return new dalvik.system.PathClassLoader(str, str2, classLoader, classLoaderArr, classLoaderArr2);
        }
        if (isDelegateLastClassLoaderName(str3)) {
            return new dalvik.system.DelegateLastClassLoader(str, str2, classLoader, classLoaderArr, classLoaderArr2);
        }
        throw new java.lang.AssertionError("Invalid classLoaderName: " + str3);
    }

    public static java.lang.ClassLoader createClassLoader(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, int i, boolean z, java.lang.String str4) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ALL");
        return createClassLoader(str, str2, str3, classLoader, i, z, str4, null, arrayList, null);
    }

    public static java.lang.ClassLoader createClassLoader(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.ClassLoader classLoader, int i, boolean z, java.lang.String str4, java.util.List<java.lang.ClassLoader> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.ClassLoader> list3) {
        java.lang.String str5;
        java.lang.ClassLoader createClassLoader = createClassLoader(str, str2, classLoader, str4, list, list3);
        if (list2 != null) {
            str5 = java.lang.String.join(":", list2);
        } else {
            str5 = "";
        }
        android.os.Trace.traceBegin(64L, "createClassloaderNamespace");
        java.lang.String createClassloaderNamespace = createClassloaderNamespace(createClassLoader, i, str2, str3, z, str, str5);
        android.os.Trace.traceEnd(64L);
        if (createClassloaderNamespace != null) {
            throw new java.lang.UnsatisfiedLinkError("Unable to create namespace for the classloader " + createClassLoader + ": " + createClassloaderNamespace);
        }
        return createClassLoader;
    }
}
