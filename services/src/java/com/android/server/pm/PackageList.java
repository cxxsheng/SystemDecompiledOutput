package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageList implements android.content.pm.PackageManagerInternal.PackageListObserver, java.lang.AutoCloseable {
    private final java.util.List<java.lang.String> mPackageNames;
    private final android.content.pm.PackageManagerInternal.PackageListObserver mWrappedObserver;

    public PackageList(@android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.Nullable android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver) {
        this.mPackageNames = list;
        this.mWrappedObserver = packageListObserver;
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public void onPackageAdded(java.lang.String str, int i) {
        if (this.mWrappedObserver != null) {
            this.mWrappedObserver.onPackageAdded(str, i);
        }
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public void onPackageChanged(java.lang.String str, int i) {
        if (this.mWrappedObserver != null) {
            this.mWrappedObserver.onPackageChanged(str, i);
        }
    }

    @Override // android.content.pm.PackageManagerInternal.PackageListObserver
    public void onPackageRemoved(java.lang.String str, int i) {
        if (this.mWrappedObserver != null) {
            this.mWrappedObserver.onPackageRemoved(str, i);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws java.lang.Exception {
        ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).removePackageListObserver(this);
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getPackageNames() {
        return this.mPackageNames;
    }
}
