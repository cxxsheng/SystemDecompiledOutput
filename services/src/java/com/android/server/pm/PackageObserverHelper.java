package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageObserverHelper {

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> mActiveSnapshot = new android.util.ArraySet<>();

    PackageObserverHelper() {
    }

    public void addObserver(@android.annotation.NonNull android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver) {
        synchronized (this.mLock) {
            android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> arraySet = new android.util.ArraySet<>(this.mActiveSnapshot);
            arraySet.add(packageListObserver);
            this.mActiveSnapshot = arraySet;
        }
    }

    public void removeObserver(@android.annotation.NonNull android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver) {
        synchronized (this.mLock) {
            android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> arraySet = new android.util.ArraySet<>(this.mActiveSnapshot);
            arraySet.remove(packageListObserver);
            this.mActiveSnapshot = arraySet;
        }
    }

    public void notifyAdded(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> arraySet;
        synchronized (this.mLock) {
            arraySet = this.mActiveSnapshot;
        }
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            arraySet.valueAt(i2).onPackageAdded(str, i);
        }
    }

    public void notifyChanged(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> arraySet;
        synchronized (this.mLock) {
            arraySet = this.mActiveSnapshot;
        }
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            arraySet.valueAt(i2).onPackageChanged(str, i);
        }
    }

    public void notifyRemoved(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.ArraySet<android.content.pm.PackageManagerInternal.PackageListObserver> arraySet;
        synchronized (this.mLock) {
            arraySet = this.mActiveSnapshot;
        }
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            arraySet.valueAt(i2).onPackageRemoved(str, i);
        }
    }
}
