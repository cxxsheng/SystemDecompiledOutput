package com.android.server.content;

/* loaded from: classes.dex */
class SyncAdapterStateFetcher {
    private final java.util.HashMap<android.content.pm.UserPackage, java.lang.Integer> mBucketCache = new java.util.HashMap<>();

    public int getStandbyBucket(int i, java.lang.String str) {
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
        java.lang.Integer num = this.mBucketCache.get(of);
        if (num != null) {
            return num.intValue();
        }
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        if (usageStatsManagerInternal == null) {
            return -1;
        }
        int appStandbyBucket = usageStatsManagerInternal.getAppStandbyBucket(str, i, android.os.SystemClock.elapsedRealtime());
        this.mBucketCache.put(of, java.lang.Integer.valueOf(appStandbyBucket));
        return appStandbyBucket;
    }

    public boolean isAppActive(int i) {
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal != null) {
            return activityManagerInternal.isUidActive(i);
        }
        return false;
    }
}
