package com.android.server.appop;

/* loaded from: classes.dex */
public interface AppOpsCheckingServiceInterface {

    public interface AppOpsModeChangedListener {
        void onPackageModeChanged(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3);

        void onUidModeChanged(int i, int i2, int i3, java.lang.String str);
    }

    boolean addAppOpsModeChangedListener(@android.annotation.NonNull com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener);

    void clearAllModes();

    android.util.SparseBooleanArray getForegroundOps(int i, java.lang.String str);

    android.util.SparseBooleanArray getForegroundOps(java.lang.String str, int i);

    android.util.SparseIntArray getNonDefaultPackageModes(java.lang.String str, int i);

    android.util.SparseIntArray getNonDefaultUidModes(int i, java.lang.String str);

    int getPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2);

    int getUidMode(int i, java.lang.String str, int i2);

    void readState();

    boolean removeAppOpsModeChangedListener(@android.annotation.NonNull com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener appOpsModeChangedListener);

    boolean removePackage(@android.annotation.NonNull java.lang.String str, int i);

    void removeUid(int i);

    void setPackageMode(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3);

    boolean setUidMode(int i, java.lang.String str, int i2, int i3);

    void shutdown();

    void systemReady();

    @com.android.internal.annotations.VisibleForTesting
    void writeState();
}
