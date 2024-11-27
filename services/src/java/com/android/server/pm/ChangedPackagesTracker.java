package com.android.server.pm;

/* loaded from: classes2.dex */
class ChangedPackagesTracker {

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mChangedPackagesSequenceNumber;

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.util.SparseArray<java.lang.String>> mUserIdToSequenceToPackage = new android.util.SparseArray<>();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.Map<java.lang.String, java.lang.Integer>> mChangedPackagesSequenceNumbers = new android.util.SparseArray<>();

    ChangedPackagesTracker() {
    }

    @android.annotation.Nullable
    public android.content.pm.ChangedPackages getChangedPackages(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (i >= this.mChangedPackagesSequenceNumber) {
                    return null;
                }
                android.util.SparseArray<java.lang.String> sparseArray = this.mUserIdToSequenceToPackage.get(i2);
                if (sparseArray == null) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mChangedPackagesSequenceNumber - i);
                while (i < this.mChangedPackagesSequenceNumber) {
                    java.lang.String str = sparseArray.get(i);
                    if (str != null) {
                        arrayList.add(str);
                    }
                    i++;
                }
                return arrayList.isEmpty() ? null : new android.content.pm.ChangedPackages(this.mChangedPackagesSequenceNumber, arrayList);
            } finally {
            }
        }
    }

    int getSequenceNumber() {
        return this.mChangedPackagesSequenceNumber;
    }

    void iterateAll(@android.annotation.NonNull java.util.function.BiConsumer<java.lang.Integer, android.util.SparseArray<android.util.SparseArray<java.lang.String>>> biConsumer) {
        synchronized (this.mLock) {
            biConsumer.accept(java.lang.Integer.valueOf(this.mChangedPackagesSequenceNumber), this.mUserIdToSequenceToPackage);
        }
    }

    void updateSequenceNumber(@android.annotation.NonNull java.lang.String str, int[] iArr) {
        synchronized (this.mLock) {
            try {
                for (int length = iArr.length - 1; length >= 0; length--) {
                    int i = iArr[length];
                    android.util.SparseArray<java.lang.String> sparseArray = this.mUserIdToSequenceToPackage.get(i);
                    if (sparseArray == null) {
                        sparseArray = new android.util.SparseArray<>();
                        this.mUserIdToSequenceToPackage.put(i, sparseArray);
                    }
                    java.util.Map<java.lang.String, java.lang.Integer> map = this.mChangedPackagesSequenceNumbers.get(i);
                    if (map == null) {
                        map = new java.util.HashMap<>();
                        this.mChangedPackagesSequenceNumbers.put(i, map);
                    }
                    java.lang.Integer num = map.get(str);
                    if (num != null) {
                        sparseArray.remove(num.intValue());
                    }
                    sparseArray.put(this.mChangedPackagesSequenceNumber, str);
                    map.put(str, java.lang.Integer.valueOf(this.mChangedPackagesSequenceNumber));
                }
                this.mChangedPackagesSequenceNumber++;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
