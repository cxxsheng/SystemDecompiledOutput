package com.android.server.appop;

/* loaded from: classes.dex */
public interface AppOpMigrationHelper {
    @android.annotation.NonNull
    java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>> getLegacyAppIdAppOpModes(int i);

    int getLegacyAppOpVersion();

    @android.annotation.NonNull
    java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>> getLegacyPackageAppOpModes(int i);

    boolean hasLegacyAppOpState();
}
