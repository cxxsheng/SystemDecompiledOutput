package com.android.server.pm;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface PackageManagerLocal {
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public interface FilteredSnapshot extends java.lang.AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        @android.annotation.Nullable
        com.android.server.pm.pkg.PackageState getPackageState(@android.annotation.NonNull java.lang.String str);

        @android.annotation.NonNull
        java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getPackageStates();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StorageFlags {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public interface UnfilteredSnapshot extends java.lang.AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        @android.annotation.NonNull
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot filtered(int i, @android.annotation.NonNull android.os.UserHandle userHandle);

        @android.annotation.NonNull
        java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getDisabledSystemPackageStates();

        @android.annotation.NonNull
        java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getPackageStates();

        @android.annotation.NonNull
        java.util.Map<java.lang.String, com.android.server.pm.pkg.SharedUserApi> getSharedUsers();
    }

    void reconcileSdkData(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i, int i2, int i3, @android.annotation.NonNull java.lang.String str3, int i4) throws java.io.IOException;

    @android.annotation.NonNull
    com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot();

    @android.annotation.NonNull
    com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot(int i, @android.annotation.NonNull android.os.UserHandle userHandle);

    @android.annotation.NonNull
    com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot();
}
