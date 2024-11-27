package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public final class DexoptUtils {
    private static final java.lang.String SHARED_LIBRARY_LOADER_TYPE = com.android.internal.os.ClassLoaderFactory.getPathClassLoaderName();
    private static final java.lang.String TAG = "DexoptUtils";

    private DexoptUtils() {
    }

    public static java.lang.String[] getClassLoaderContexts(com.android.server.pm.pkg.AndroidPackage androidPackage, java.util.List<android.content.pm.SharedLibraryInfo> list, boolean[] zArr) {
        java.lang.String str;
        if (list == null) {
            str = "";
        } else {
            str = encodeSharedLibraries(list);
        }
        java.lang.String encodeClassLoader = encodeClassLoader("", androidPackage.getClassLoaderName(), str);
        if (com.android.internal.util.ArrayUtils.isEmpty(androidPackage.getSplitCodePaths())) {
            return new java.lang.String[]{encodeClassLoader};
        }
        java.lang.String[] splitRelativeCodePaths = getSplitRelativeCodePaths(androidPackage);
        java.lang.String name = new java.io.File(androidPackage.getBaseApkPath()).getName();
        int i = 1;
        int length = splitRelativeCodePaths.length + 1;
        java.lang.String[] strArr = new java.lang.String[length];
        if (!zArr[0]) {
            encodeClassLoader = null;
        }
        strArr[0] = encodeClassLoader;
        android.util.SparseArray splitDependencies = androidPackage.getSplitDependencies();
        if (!androidPackage.isIsolatedSplitLoading() || splitDependencies == null || splitDependencies.size() == 0) {
            while (i < length) {
                if (zArr[i]) {
                    strArr[i] = encodeClassLoader(name, androidPackage.getClassLoaderName(), str);
                } else {
                    strArr[i] = null;
                }
                name = encodeClasspath(name, splitRelativeCodePaths[i - 1]);
                i++;
            }
        } else {
            java.lang.String[] strArr2 = new java.lang.String[splitRelativeCodePaths.length];
            for (int i2 = 0; i2 < splitRelativeCodePaths.length; i2++) {
                strArr2[i2] = encodeClassLoader(splitRelativeCodePaths[i2], androidPackage.getSplitClassLoaderNames()[i2]);
            }
            java.lang.String encodeClassLoader2 = encodeClassLoader(name, androidPackage.getClassLoaderName());
            for (int i3 = 1; i3 < splitDependencies.size(); i3++) {
                int keyAt = splitDependencies.keyAt(i3);
                if (zArr[keyAt]) {
                    getParentDependencies(keyAt, strArr2, splitDependencies, strArr, encodeClassLoader2);
                }
            }
            while (i < length) {
                java.lang.String encodeClassLoader3 = encodeClassLoader("", androidPackage.getSplitClassLoaderNames()[i - 1]);
                if (zArr[i]) {
                    if (strArr[i] != null) {
                        encodeClassLoader3 = encodeClassLoaderChain(encodeClassLoader3, strArr[i]) + str;
                    }
                    strArr[i] = encodeClassLoader3;
                } else {
                    strArr[i] = null;
                }
                i++;
            }
        }
        return strArr;
    }

    public static java.lang.String getClassLoaderContext(android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        java.lang.String str;
        if (sharedLibraryInfo.getDependencies() == null) {
            str = "";
        } else {
            str = encodeSharedLibraries(sharedLibraryInfo.getDependencies());
        }
        return encodeClassLoader("", SHARED_LIBRARY_LOADER_TYPE, str);
    }

    private static java.lang.String getParentDependencies(int i, java.lang.String[] strArr, android.util.SparseArray<int[]> sparseArray, java.lang.String[] strArr2, java.lang.String str) {
        if (i == 0) {
            return str;
        }
        if (strArr2[i] != null) {
            return strArr2[i];
        }
        int i2 = sparseArray.get(i)[0];
        java.lang.String parentDependencies = getParentDependencies(i2, strArr, sparseArray, strArr2, str);
        if (i2 != 0) {
            parentDependencies = encodeClassLoaderChain(strArr[i2 - 1], parentDependencies);
        }
        strArr2[i] = parentDependencies;
        return parentDependencies;
    }

    private static java.lang.String encodeSharedLibrary(android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        java.util.List allCodePaths = sharedLibraryInfo.getAllCodePaths();
        java.lang.String encodeClassLoader = encodeClassLoader(encodeClasspath((java.lang.String[]) allCodePaths.toArray(new java.lang.String[allCodePaths.size()])), SHARED_LIBRARY_LOADER_TYPE);
        if (sharedLibraryInfo.getDependencies() != null) {
            return encodeClassLoader + encodeSharedLibraries(sharedLibraryInfo.getDependencies());
        }
        return encodeClassLoader;
    }

    private static java.lang.String encodeSharedLibraries(java.util.List<android.content.pm.SharedLibraryInfo> list) {
        java.lang.String str = "{";
        boolean z = true;
        for (android.content.pm.SharedLibraryInfo sharedLibraryInfo : list) {
            if (!z) {
                str = str + "#";
            }
            str = str + encodeSharedLibrary(sharedLibraryInfo);
            z = false;
        }
        return str + "}";
    }

    private static java.lang.String encodeClasspath(java.lang.String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.lang.String str : strArr) {
            if (sb.length() != 0) {
                sb.append(":");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    private static java.lang.String encodeClasspath(java.lang.String str, java.lang.String str2) {
        if (str.isEmpty()) {
            return str2;
        }
        return str + ":" + str2;
    }

    static java.lang.String encodeClassLoader(java.lang.String str, java.lang.String str2) {
        str.getClass();
        if (com.android.internal.os.ClassLoaderFactory.isPathClassLoaderName(str2)) {
            str2 = "PCL";
        } else if (com.android.internal.os.ClassLoaderFactory.isDelegateLastClassLoaderName(str2)) {
            str2 = "DLC";
        } else {
            android.util.Slog.wtf(TAG, "Unsupported classLoaderName: " + str2);
        }
        return str2 + "[" + str + "]";
    }

    private static java.lang.String encodeClassLoader(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return encodeClassLoader(str, str2) + str3;
    }

    static java.lang.String encodeClassLoaderChain(java.lang.String str, java.lang.String str2) {
        if (str.isEmpty()) {
            return str2;
        }
        if (str2.isEmpty()) {
            return str;
        }
        return str + ";" + str2;
    }

    static java.lang.String[] processContextForDexLoad(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        if (list.size() != list2.size()) {
            throw new java.lang.IllegalArgumentException("The size of the class loader names and the dex paths do not match.");
        }
        if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Empty classLoadersNames");
        }
        java.lang.String str = "";
        java.lang.String str2 = "";
        for (int i = 1; i < list.size(); i++) {
            if (!com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(list.get(i)) || list2.get(i) == null) {
                return null;
            }
            str2 = encodeClassLoaderChain(str2, encodeClassLoader(encodeClasspath(list2.get(i).split(java.io.File.pathSeparator)), list.get(i)));
        }
        java.lang.String str3 = list.get(0);
        if (!com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(str3)) {
            return null;
        }
        java.lang.String[] split = list2.get(0).split(java.io.File.pathSeparator);
        java.lang.String[] strArr = new java.lang.String[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            java.lang.String str4 = split[i2];
            strArr[i2] = encodeClassLoaderChain(encodeClassLoader(str, str3), str2);
            str = encodeClasspath(str, str4);
        }
        return strArr;
    }

    private static java.lang.String[] getSplitRelativeCodePaths(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.lang.String parent = new java.io.File(androidPackage.getBaseApkPath()).getParent();
        java.lang.String[] splitCodePaths = androidPackage.getSplitCodePaths();
        int size = com.android.internal.util.ArrayUtils.size(splitCodePaths);
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i = 0; i < size; i++) {
            java.io.File file = new java.io.File(splitCodePaths[i]);
            strArr[i] = file.getName();
            java.lang.String parent2 = file.getParent();
            if (!parent2.equals(parent)) {
                android.util.Slog.wtf(TAG, "Split paths have different base paths: " + parent2 + " and " + parent);
            }
        }
        return strArr;
    }
}
