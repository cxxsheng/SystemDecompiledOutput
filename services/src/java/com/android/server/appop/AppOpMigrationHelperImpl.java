package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpMigrationHelperImpl implements com.android.server.appop.AppOpMigrationHelper {
    private int mVersionAtBoot;
    private android.util.SparseArray<java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>>> mAppIdAppOpModes = null;
    private android.util.SparseArray<java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>>> mPackageAppOpModes = null;
    private final java.lang.Object mLock = new java.lang.Object();

    @Override // com.android.server.appop.AppOpMigrationHelper
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>> getLegacyAppIdAppOpModes(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mAppIdAppOpModes == null) {
                    readLegacyAppOpState();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mAppIdAppOpModes.get(i, java.util.Collections.emptyMap());
    }

    @Override // com.android.server.appop.AppOpMigrationHelper
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>> getLegacyPackageAppOpModes(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mPackageAppOpModes == null) {
                    readLegacyAppOpState();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mPackageAppOpModes.get(i, java.util.Collections.emptyMap());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readLegacyAppOpState() {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(com.android.server.SystemServiceManager.ensureSystemDir(), "appops.xml"));
        android.util.SparseArray<android.util.SparseIntArray> sparseArray = new android.util.SparseArray<>();
        android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray2 = new android.util.SparseArray<>();
        int readState = new com.android.server.appop.LegacyAppOpStateParser().readState(atomicFile, sparseArray, sparseArray2);
        switch (readState) {
            case -2:
                this.mVersionAtBoot = -1;
                break;
            case -1:
                this.mVersionAtBoot = 0;
                break;
            default:
                this.mVersionAtBoot = readState;
                break;
        }
        this.mAppIdAppOpModes = getAppIdAppOpModes(sparseArray);
        this.mPackageAppOpModes = getPackageAppOpModes(sparseArray2);
    }

    private android.util.SparseArray<java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>>> getAppIdAppOpModes(android.util.SparseArray<android.util.SparseIntArray> sparseArray) {
        android.util.SparseArray<java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>>> sparseArray2 = new android.util.SparseArray<>();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            int userId = android.os.UserHandle.getUserId(keyAt);
            java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, java.lang.Integer>> map = sparseArray2.get(userId);
            if (map == null) {
                map = new android.util.ArrayMap<>();
                sparseArray2.put(userId, map);
            }
            map.put(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(keyAt)), getAppOpModesForOpName(sparseArray.valueAt(i)));
        }
        return sparseArray2;
    }

    private android.util.SparseArray<java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>>> getPackageAppOpModes(android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseIntArray>> sparseArray) {
        android.util.SparseArray<java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>>> sparseArray2 = new android.util.SparseArray<>();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.Integer>> map = sparseArray2.get(keyAt);
            if (map == null) {
                map = new android.util.ArrayMap<>();
                sparseArray2.put(keyAt, map);
            }
            android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> valueAt = sparseArray.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                map.put(valueAt.keyAt(i2), getAppOpModesForOpName(valueAt.valueAt(i2)));
            }
        }
        return sparseArray2;
    }

    private java.util.Map<java.lang.String, java.lang.Integer> getAppOpModesForOpName(android.util.SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
        for (int i = 0; i < size; i++) {
            arrayMap.put(android.app.AppOpsManager.opToPublicName(sparseIntArray.keyAt(i)), java.lang.Integer.valueOf(sparseIntArray.valueAt(i)));
        }
        return arrayMap;
    }

    @Override // com.android.server.appop.AppOpMigrationHelper
    public int getLegacyAppOpVersion() {
        synchronized (this.mLock) {
            try {
                if (this.mAppIdAppOpModes == null || this.mPackageAppOpModes == null) {
                    readLegacyAppOpState();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mVersionAtBoot;
    }

    @Override // com.android.server.appop.AppOpMigrationHelper
    public boolean hasLegacyAppOpState() {
        return getLegacyAppOpVersion() > -1;
    }
}
