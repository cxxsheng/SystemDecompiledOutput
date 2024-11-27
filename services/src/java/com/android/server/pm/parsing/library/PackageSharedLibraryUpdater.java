package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public abstract class PackageSharedLibraryUpdater {
    public abstract void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2);

    static void removeLibrary(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, java.lang.String str) {
        parsedPackage.removeUsesLibrary(str).removeUsesOptionalLibrary(str);
    }

    @android.annotation.NonNull
    static <T> java.util.ArrayList<T> prefix(@android.annotation.Nullable java.util.ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
        }
        arrayList.add(0, t);
        return arrayList;
    }

    private static boolean isLibraryPresent(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.lang.String str) {
        return com.android.internal.util.ArrayUtils.contains(list, str) || com.android.internal.util.ArrayUtils.contains(list2, str);
    }

    void prefixImplicitDependency(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, java.lang.String str, java.lang.String str2) {
        java.util.List usesLibraries = parsedPackage.getUsesLibraries();
        java.util.List usesOptionalLibraries = parsedPackage.getUsesOptionalLibraries();
        if (!isLibraryPresent(usesLibraries, usesOptionalLibraries, str2)) {
            if (com.android.internal.util.ArrayUtils.contains(usesLibraries, str)) {
                parsedPackage.addUsesLibrary(0, str2);
            } else if (com.android.internal.util.ArrayUtils.contains(usesOptionalLibraries, str)) {
                parsedPackage.addUsesOptionalLibrary(0, str2);
            }
        }
    }

    void prefixRequiredLibrary(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, java.lang.String str) {
        if (!isLibraryPresent(parsedPackage.getUsesLibraries(), parsedPackage.getUsesOptionalLibraries(), str)) {
            parsedPackage.addUsesLibrary(0, str);
        }
    }
}
