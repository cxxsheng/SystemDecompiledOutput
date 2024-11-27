package com.android.server.storage;

/* loaded from: classes2.dex */
public class AppCollector {
    private static java.lang.String TAG = "AppCollector";
    private final com.android.server.storage.AppCollector.BackgroundHandler mBackgroundHandler;
    private java.util.concurrent.CompletableFuture<java.util.List<android.content.pm.PackageStats>> mStats;

    public AppCollector(android.content.Context context, @android.annotation.NonNull android.os.storage.VolumeInfo volumeInfo) {
        java.util.Objects.requireNonNull(volumeInfo);
        this.mBackgroundHandler = new com.android.server.storage.AppCollector.BackgroundHandler(com.android.internal.os.BackgroundThread.get().getLooper(), volumeInfo, context.getPackageManager(), (android.os.UserManager) context.getSystemService("user"), (android.app.usage.StorageStatsManager) context.getSystemService("storagestats"));
    }

    public java.util.List<android.content.pm.PackageStats> getPackageStats(long j) {
        synchronized (this) {
            try {
                if (this.mStats == null) {
                    this.mStats = new java.util.concurrent.CompletableFuture<>();
                    this.mBackgroundHandler.sendEmptyMessage(0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        try {
            return this.mStats.get(j, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            android.util.Log.e(TAG, "An exception occurred while getting app storage", e);
            return null;
        } catch (java.util.concurrent.TimeoutException e2) {
            android.util.Log.e(TAG, "AppCollector timed out");
            return null;
        }
    }

    private class BackgroundHandler extends android.os.Handler {
        static final int MSG_START_LOADING_SIZES = 0;
        private final android.content.pm.PackageManager mPm;
        private final android.app.usage.StorageStatsManager mStorageStatsManager;
        private final android.os.UserManager mUm;
        private final android.os.storage.VolumeInfo mVolume;

        BackgroundHandler(android.os.Looper looper, @android.annotation.NonNull android.os.storage.VolumeInfo volumeInfo, android.content.pm.PackageManager packageManager, android.os.UserManager userManager, android.app.usage.StorageStatsManager storageStatsManager) {
            super(looper);
            this.mVolume = volumeInfo;
            this.mPm = packageManager;
            this.mUm = userManager;
            this.mStorageStatsManager = storageStatsManager;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.List users = this.mUm.getUsers();
                    int size = users.size();
                    for (int i = 0; i < size; i++) {
                        android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) users.get(i);
                        java.util.List installedApplicationsAsUser = this.mPm.getInstalledApplicationsAsUser(512, userInfo.id);
                        int size2 = installedApplicationsAsUser.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) installedApplicationsAsUser.get(i2);
                            if (java.util.Objects.equals(applicationInfo.volumeUuid, this.mVolume.getFsUuid())) {
                                try {
                                    android.app.usage.StorageStats queryStatsForPackage = this.mStorageStatsManager.queryStatsForPackage(applicationInfo.storageUuid, applicationInfo.packageName, userInfo.getUserHandle());
                                    android.content.pm.PackageStats packageStats = new android.content.pm.PackageStats(applicationInfo.packageName, userInfo.id);
                                    packageStats.cacheSize = queryStatsForPackage.getCacheBytes();
                                    packageStats.codeSize = queryStatsForPackage.getAppBytes();
                                    packageStats.dataSize = queryStatsForPackage.getDataBytes();
                                    arrayList.add(packageStats);
                                } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException e) {
                                    android.util.Log.e(com.android.server.storage.AppCollector.TAG, "An exception occurred while fetching app size", e);
                                }
                            }
                        }
                    }
                    com.android.server.storage.AppCollector.this.mStats.complete(arrayList);
                    break;
            }
        }
    }
}
