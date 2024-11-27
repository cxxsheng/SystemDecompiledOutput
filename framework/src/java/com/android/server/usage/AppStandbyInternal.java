package com.android.server.usage;

/* loaded from: classes5.dex */
public interface AppStandbyInternal {
    void addActiveDeviceAdmin(java.lang.String str, int i);

    void addListener(com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener);

    void clearCarrierPrivilegedApps();

    void clearLastUsedTimestampsForTest(java.lang.String str, int i);

    void dumpState(java.lang.String[] strArr, java.io.PrintWriter printWriter);

    void dumpUsers(android.util.IndentingPrintWriter indentingPrintWriter, int[] iArr, java.util.List<java.lang.String> list);

    void flushToDisk();

    int getAppId(java.lang.String str);

    int getAppMinStandbyBucket(java.lang.String str, int i, int i2, boolean z);

    int getAppStandbyBucket(java.lang.String str, int i, long j, boolean z);

    int getAppStandbyBucketReason(java.lang.String str, int i, long j);

    java.util.List<android.app.usage.AppStandbyInfo> getAppStandbyBuckets(int i);

    java.lang.String getAppStandbyConstant(java.lang.String str);

    java.util.List<java.lang.String> getBroadcastResponseExemptedPermissions();

    java.util.List<java.lang.String> getBroadcastResponseExemptedRoles();

    int getBroadcastResponseFgThresholdState();

    long getBroadcastResponseWindowDurationMs();

    long getBroadcastSessionsDurationMs();

    long getBroadcastSessionsWithResponseDurationMs();

    long getEstimatedLaunchTime(java.lang.String str, int i);

    int[] getIdleUidsForUser(int i);

    long getTimeSinceLastJobRun(java.lang.String str, int i);

    long getTimeSinceLastUsedByUser(java.lang.String str, int i);

    void initializeDefaultsForSystemApps(int i);

    boolean isActiveDeviceAdmin(java.lang.String str, int i);

    boolean isAppIdleEnabled();

    boolean isAppIdleFiltered(java.lang.String str, int i, int i2, long j);

    boolean isAppIdleFiltered(java.lang.String str, int i, long j, boolean z);

    boolean isInParole();

    void maybeUnrestrictApp(java.lang.String str, int i, int i2, int i3, int i4, int i5);

    void onAdminDataAvailable();

    void onBootPhase(int i);

    void onUserRemoved(int i);

    void postCheckIdleStates(int i);

    void postOneTimeCheckIdleStates();

    void postReportContentProviderUsage(java.lang.String str, java.lang.String str2, int i);

    void postReportExemptedSyncStart(java.lang.String str, int i);

    void postReportSyncScheduled(java.lang.String str, int i, boolean z);

    void removeListener(com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener appIdleStateChangeListener);

    void restoreAppsToRare(java.util.Set<java.lang.String> set, int i);

    void restrictApp(java.lang.String str, int i, int i2);

    void restrictApp(java.lang.String str, int i, int i2, int i3);

    void setActiveAdminApps(java.util.Set<java.lang.String> set, int i);

    void setAdminProtectedPackages(java.util.Set<java.lang.String> set, int i);

    void setAppIdleAsync(java.lang.String str, boolean z, int i);

    void setAppStandbyBucket(java.lang.String str, int i, int i2, int i3, int i4);

    void setAppStandbyBuckets(java.util.List<android.app.usage.AppStandbyInfo> list, int i, int i2, int i3);

    void setEstimatedLaunchTime(java.lang.String str, int i, long j);

    void setLastJobRunTime(java.lang.String str, int i, long j);

    boolean shouldNoteResponseEventForAllBroadcastSessions();

    static com.android.server.usage.AppStandbyInternal newAppStandbyController(java.lang.ClassLoader classLoader, android.content.Context context) {
        try {
            return (com.android.server.usage.AppStandbyInternal) java.lang.Class.forName("com.android.server.usage.AppStandbyController", true, classLoader).getConstructor(android.content.Context.class).newInstance(context);
        } catch (java.lang.ClassNotFoundException | java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            throw new java.lang.RuntimeException("Unable to instantiate AppStandbyController!", e);
        }
    }

    public static abstract class AppIdleStateChangeListener {
        public abstract void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3);

        public void onParoleStateChanged(boolean z) {
        }

        public void onUserInteractionStarted(java.lang.String str, int i) {
        }

        public void triggerTemporaryQuotaBump(java.lang.String str, int i) {
        }
    }
}
