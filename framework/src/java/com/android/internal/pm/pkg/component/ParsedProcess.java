package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedProcess {
    android.util.ArrayMap<java.lang.String, java.lang.String> getAppClassNamesByPackage();

    java.util.Set<java.lang.String> getDeniedPermissions();

    int getGwpAsanMode();

    int getMemtagMode();

    java.lang.String getName();

    int getNativeHeapZeroInitialized();

    boolean isUseEmbeddedDex();
}
