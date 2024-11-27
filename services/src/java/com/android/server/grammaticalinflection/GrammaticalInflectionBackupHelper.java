package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
public class GrammaticalInflectionBackupHelper {
    private static final java.lang.String SYSTEM_BACKUP_PACKAGE_KEY = "android";
    private final android.util.SparseArray<com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper.StagedData> mCache = new android.util.SparseArray<>();
    private final java.lang.Object mCacheLock = new java.lang.Object();
    private final java.time.Clock mClock = java.time.Clock.systemUTC();
    private final com.android.server.grammaticalinflection.GrammaticalInflectionService mGrammaticalGenderService;
    private final android.content.pm.PackageManager mPackageManager;
    private static final java.lang.String TAG = com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper.class.getSimpleName();
    private static final java.time.Duration STAGE_DATA_RETENTION_PERIOD = java.time.Duration.ofDays(3);

    static class StagedData {
        final long mCreationTimeMillis;
        final java.util.HashMap<java.lang.String, java.lang.Integer> mPackageStates = new java.util.HashMap<>();

        StagedData(long j) {
            this.mCreationTimeMillis = j;
        }
    }

    public GrammaticalInflectionBackupHelper(com.android.server.grammaticalinflection.GrammaticalInflectionService grammaticalInflectionService, android.content.pm.PackageManager packageManager) {
        this.mGrammaticalGenderService = grammaticalInflectionService;
        this.mPackageManager = packageManager;
    }

    public byte[] getBackupPayload(int i) {
        synchronized (this.mCacheLock) {
            cleanStagedDataForOldEntries();
        }
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap = new java.util.HashMap<>();
        for (android.content.pm.ApplicationInfo applicationInfo : this.mPackageManager.getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), i)) {
            int applicationGrammaticalGender = this.mGrammaticalGenderService.getApplicationGrammaticalGender(applicationInfo.packageName, i);
            if (applicationGrammaticalGender != 0) {
                hashMap.put(applicationInfo.packageName, java.lang.Integer.valueOf(applicationGrammaticalGender));
            }
        }
        if (!hashMap.isEmpty()) {
            return convertToByteArray(hashMap);
        }
        return null;
    }

    public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
        synchronized (this.mCacheLock) {
            try {
                cleanStagedDataForOldEntries();
                java.util.HashMap<java.lang.String, java.lang.Integer> readFromByteArray = readFromByteArray(bArr);
                if (readFromByteArray.isEmpty()) {
                    return;
                }
                com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper.StagedData stagedData = new com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper.StagedData(this.mClock.millis());
                for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : readFromByteArray.entrySet()) {
                    if (isPackageInstalledForUser(entry.getKey(), i)) {
                        if (!hasSetBeforeRestoring(entry.getKey(), i)) {
                            this.mGrammaticalGenderService.setRequestedApplicationGrammaticalGender(entry.getKey(), i, entry.getValue().intValue());
                        }
                    } else if (entry.getValue().intValue() != 0) {
                        stagedData.mPackageStates.put(entry.getKey(), entry.getValue());
                    }
                }
                this.mCache.append(i, stagedData);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean hasSetBeforeRestoring(java.lang.String str, int i) {
        return this.mGrammaticalGenderService.getApplicationGrammaticalGender(str, i) != 0;
    }

    public void onPackageAdded(java.lang.String str, int i) {
        int intValue;
        synchronized (this.mCacheLock) {
            try {
                int userId = android.os.UserHandle.getUserId(i);
                com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper.StagedData stagedData = this.mCache.get(userId);
                if (stagedData != null && stagedData.mPackageStates.containsKey(str) && (intValue = stagedData.mPackageStates.get(str).intValue()) != 0) {
                    this.mGrammaticalGenderService.setRequestedApplicationGrammaticalGender(str, userId, intValue);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onPackageDataCleared() {
        notifyBackupManager();
    }

    public void onPackageRemoved() {
        notifyBackupManager();
    }

    public static void notifyBackupManager() {
        android.app.backup.BackupManager.dataChanged("android");
    }

    private byte[] convertToByteArray(java.util.HashMap<java.lang.String, java.lang.Integer> hashMap) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(hashMap);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "cannot convert payload to byte array.", e);
            return null;
        }
    }

    private java.util.HashMap<java.lang.String, java.lang.Integer> readFromByteArray(byte[] bArr) {
        java.util.HashMap<java.lang.String, java.lang.Integer> hashMap = new java.util.HashMap<>();
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                java.io.ObjectInputStream objectInputStream = new java.io.ObjectInputStream(byteArrayInputStream);
                try {
                    java.util.HashMap<java.lang.String, java.lang.Integer> hashMap2 = (java.util.HashMap) objectInputStream.readObject();
                    try {
                        objectInputStream.close();
                        try {
                            byteArrayInputStream.close();
                            return hashMap2;
                        } catch (java.io.IOException | java.lang.ClassNotFoundException e) {
                            e = e;
                            hashMap = hashMap2;
                            android.util.Log.e(TAG, "cannot convert payload to HashMap.", e);
                            e.printStackTrace();
                            return hashMap;
                        }
                    } catch (java.lang.Throwable th) {
                        th = th;
                        hashMap = hashMap2;
                        try {
                            byteArrayInputStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th3) {
                    try {
                        objectInputStream.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } catch (java.lang.Throwable th5) {
                th = th5;
            }
        } catch (java.io.IOException | java.lang.ClassNotFoundException e2) {
            e = e2;
        }
    }

    private void cleanStagedDataForOldEntries() {
        for (int i = 0; i < this.mCache.size(); i++) {
            int keyAt = this.mCache.keyAt(i);
            if (this.mCache.get(keyAt).mCreationTimeMillis < this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
                this.mCache.remove(keyAt);
            }
        }
    }

    private boolean isPackageInstalledForUser(java.lang.String str, int i) {
        android.content.pm.PackageInfo packageInfo;
        try {
            packageInfo = this.mPackageManager.getPackageInfoAsUser(str, 0, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }
}
