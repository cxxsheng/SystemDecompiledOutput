package android.app.usage;

/* loaded from: classes.dex */
public final class UsageStatsManager {
    public static final java.lang.String EXTRA_EVENT_ACTION = "android.app.usage.extra.EVENT_ACTION";
    public static final java.lang.String EXTRA_EVENT_CATEGORY = "android.app.usage.extra.EVENT_CATEGORY";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_OBSERVER_ID = "android.app.usage.extra.OBSERVER_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_TIME_LIMIT = "android.app.usage.extra.TIME_LIMIT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_TIME_USED = "android.app.usage.extra.TIME_USED";
    public static final int INTERVAL_BEST = 4;
    public static final int INTERVAL_COUNT = 4;
    public static final int INTERVAL_DAILY = 0;
    public static final int INTERVAL_MONTHLY = 2;
    public static final int INTERVAL_WEEKLY = 1;
    public static final int INTERVAL_YEARLY = 3;
    public static final int REASON_MAIN_DEFAULT = 256;
    public static final int REASON_MAIN_FORCED_BY_SYSTEM = 1536;
    public static final int REASON_MAIN_FORCED_BY_USER = 1024;
    public static final int REASON_MAIN_MASK = 65280;
    public static final int REASON_MAIN_PREDICTED = 1280;
    public static final int REASON_MAIN_TIMEOUT = 512;
    public static final int REASON_MAIN_USAGE = 768;
    public static final int REASON_SUB_DEFAULT_APP_RESTORED = 2;
    public static final int REASON_SUB_DEFAULT_APP_UPDATE = 1;
    public static final int REASON_SUB_DEFAULT_UNDEFINED = 0;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_ABUSE = 2;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_BACKGROUND_RESOURCE_USAGE = 1;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_BUGGY = 4;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_UNDEFINED = 0;
    public static final int REASON_SUB_FORCED_USER_FLAG_INTERACTION = 2;
    public static final int REASON_SUB_MASK = 255;
    public static final int REASON_SUB_PREDICTED_RESTORED = 1;
    public static final int REASON_SUB_USAGE_ACTIVE_TIMEOUT = 7;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_DOZE = 12;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_NON_DOZE = 11;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_START = 13;
    public static final int REASON_SUB_USAGE_FOREGROUND_SERVICE_START = 15;
    public static final int REASON_SUB_USAGE_MOVE_TO_BACKGROUND = 5;
    public static final int REASON_SUB_USAGE_MOVE_TO_FOREGROUND = 4;
    public static final int REASON_SUB_USAGE_NOTIFICATION_SEEN = 2;
    public static final int REASON_SUB_USAGE_SLICE_PINNED = 9;
    public static final int REASON_SUB_USAGE_SLICE_PINNED_PRIV = 10;
    public static final int REASON_SUB_USAGE_SYNC_ADAPTER = 8;
    public static final int REASON_SUB_USAGE_SYSTEM_INTERACTION = 1;
    public static final int REASON_SUB_USAGE_SYSTEM_UPDATE = 6;
    public static final int REASON_SUB_USAGE_UNEXEMPTED_SYNC_SCHEDULED = 14;
    public static final int REASON_SUB_USAGE_USER_INTERACTION = 3;
    public static final int STANDBY_BUCKET_ACTIVE = 10;

    @android.annotation.SystemApi
    public static final int STANDBY_BUCKET_EXEMPTED = 5;
    public static final int STANDBY_BUCKET_FREQUENT = 30;

    @android.annotation.SystemApi
    public static final int STANDBY_BUCKET_NEVER = 50;
    public static final int STANDBY_BUCKET_RARE = 40;
    public static final int STANDBY_BUCKET_RESTRICTED = 45;
    public static final int STANDBY_BUCKET_WORKING_SET = 20;

    @android.annotation.SystemApi
    public static final int USAGE_SOURCE_CURRENT_ACTIVITY = 2;

    @android.annotation.SystemApi
    public static final int USAGE_SOURCE_TASK_ROOT_ACTIVITY = 1;
    private static final android.app.usage.UsageEvents sEmptyResults = new android.app.usage.UsageEvents();
    private final android.content.Context mContext;
    private final android.app.usage.IUsageStatsManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForcedReasons {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StandbyBuckets {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsageSource {
    }

    public UsageStatsManager(android.content.Context context, android.app.usage.IUsageStatsManager iUsageStatsManager) {
        this.mContext = context;
        this.mService = iUsageStatsManager;
    }

    public java.util.List<android.app.usage.UsageStats> queryUsageStats(int i, long j, long j2) {
        try {
            android.content.pm.ParceledListSlice queryUsageStats = this.mService.queryUsageStats(i, j, j2, this.mContext.getOpPackageName(), this.mContext.getUserId());
            if (queryUsageStats != null) {
                return queryUsageStats.getList();
            }
        } catch (android.os.RemoteException e) {
        }
        return java.util.Collections.emptyList();
    }

    public java.util.List<android.app.usage.ConfigurationStats> queryConfigurations(int i, long j, long j2) {
        try {
            android.content.pm.ParceledListSlice queryConfigurationStats = this.mService.queryConfigurationStats(i, j, j2, this.mContext.getOpPackageName());
            if (queryConfigurationStats != null) {
                return queryConfigurationStats.getList();
            }
        } catch (android.os.RemoteException e) {
        }
        return java.util.Collections.emptyList();
    }

    public java.util.List<android.app.usage.EventStats> queryEventStats(int i, long j, long j2) {
        try {
            android.content.pm.ParceledListSlice queryEventStats = this.mService.queryEventStats(i, j, j2, this.mContext.getOpPackageName());
            if (queryEventStats != null) {
                return queryEventStats.getList();
            }
        } catch (android.os.RemoteException e) {
        }
        return java.util.Collections.emptyList();
    }

    public android.app.usage.UsageEvents queryEvents(long j, long j2) {
        try {
            android.app.usage.UsageEvents queryEvents = this.mService.queryEvents(j, j2, this.mContext.getOpPackageName());
            if (queryEvents != null) {
                return queryEvents;
            }
        } catch (android.os.RemoteException e) {
        }
        return sEmptyResults;
    }

    public android.app.usage.UsageEvents queryEvents(android.app.usage.UsageEventsQuery usageEventsQuery) {
        try {
            android.app.usage.UsageEvents queryEventsWithFilter = this.mService.queryEventsWithFilter(usageEventsQuery, this.mContext.getOpPackageName());
            if (queryEventsWithFilter != null) {
                return queryEventsWithFilter;
            }
        } catch (android.os.RemoteException e) {
        }
        return sEmptyResults;
    }

    public android.app.usage.UsageEvents queryEventsForSelf(long j, long j2) {
        try {
            android.app.usage.UsageEvents queryEventsForPackage = this.mService.queryEventsForPackage(j, j2, this.mContext.getOpPackageName());
            if (queryEventsForPackage != null) {
                return queryEventsForPackage;
            }
        } catch (android.os.RemoteException e) {
        }
        return sEmptyResults;
    }

    public java.util.Map<java.lang.String, android.app.usage.UsageStats> queryAndAggregateUsageStats(long j, long j2) {
        java.util.List<android.app.usage.UsageStats> queryUsageStats = queryUsageStats(4, j, j2);
        if (queryUsageStats.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size = queryUsageStats.size();
        for (int i = 0; i < size; i++) {
            android.app.usage.UsageStats usageStats = queryUsageStats.get(i);
            android.app.usage.UsageStats usageStats2 = (android.app.usage.UsageStats) arrayMap.get(usageStats.getPackageName());
            if (usageStats2 == null) {
                arrayMap.put(usageStats.mPackageName, usageStats);
            } else {
                usageStats2.add(usageStats);
            }
        }
        return arrayMap;
    }

    public boolean isAppStandbyEnabled() {
        try {
            return this.mService.isAppStandbyEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAppInactive(java.lang.String str) {
        try {
            return this.mService.isAppInactive(str, this.mContext.getUserId(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void setAppInactive(java.lang.String str, boolean z) {
        try {
            this.mService.setAppInactive(str, z, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
        }
    }

    public int getAppStandbyBucket() {
        try {
            return this.mService.getAppStandbyBucket(this.mContext.getOpPackageName(), this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            return 10;
        }
    }

    @android.annotation.SystemApi
    public int getAppStandbyBucket(java.lang.String str) {
        try {
            return this.mService.getAppStandbyBucket(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            return 10;
        }
    }

    @android.annotation.SystemApi
    public void setAppStandbyBucket(java.lang.String str, int i) {
        try {
            this.mService.setAppStandbyBucket(str, i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, java.lang.Integer> getAppStandbyBuckets() {
        try {
            java.util.List list = this.mService.getAppStandbyBuckets(this.mContext.getOpPackageName(), this.mContext.getUserId()).getList();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.app.usage.AppStandbyInfo appStandbyInfo = (android.app.usage.AppStandbyInfo) list.get(i);
                arrayMap.put(appStandbyInfo.mPackageName, java.lang.Integer.valueOf(appStandbyInfo.mStandbyBucket));
            }
            return arrayMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setAppStandbyBuckets(java.util.Map<java.lang.String, java.lang.Integer> map) {
        if (map == null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(map.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
            arrayList.add(new android.app.usage.AppStandbyInfo(entry.getKey(), entry.getValue().intValue()));
        }
        try {
            this.mService.setAppStandbyBuckets(new android.content.pm.ParceledListSlice(arrayList), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAppMinStandbyBucket(java.lang.String str) {
        try {
            return this.mService.getAppMinStandbyBucket(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setEstimatedLaunchTimeMillis(java.lang.String str, long j) {
        if (str == null) {
            throw new java.lang.NullPointerException("package name cannot be null");
        }
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("estimated launch time must be positive");
        }
        try {
            this.mService.setEstimatedLaunchTime(str, j, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setEstimatedLaunchTimesMillis(java.util.Map<java.lang.String, java.lang.Long> map) {
        if (map == null) {
            throw new java.lang.NullPointerException("estimatedLaunchTimesMillis cannot be null");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(map.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            if (key == null) {
                throw new java.lang.NullPointerException("package name cannot be null");
            }
            java.lang.Long value = entry.getValue();
            if (value == null || value.longValue() <= 0) {
                throw new java.lang.IllegalArgumentException("estimated launch time must be positive");
            }
            arrayList.add(new android.app.usage.AppLaunchEstimateInfo(key, value.longValue()));
        }
        try {
            this.mService.setEstimatedLaunchTimes(new android.content.pm.ParceledListSlice(arrayList), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void registerAppUsageObserver(int i, java.lang.String[] strArr, long j, java.util.concurrent.TimeUnit timeUnit, android.app.PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageObserver(i, strArr, timeUnit.toMillis(j), pendingIntent, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterAppUsageObserver(int i) {
        try {
            this.mService.unregisterAppUsageObserver(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void registerUsageSessionObserver(int i, java.lang.String[] strArr, java.time.Duration duration, java.time.Duration duration2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
        try {
            this.mService.registerUsageSessionObserver(i, strArr, duration.toMillis(), duration2.toMillis(), pendingIntent, pendingIntent2, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterUsageSessionObserver(int i) {
        try {
            this.mService.unregisterUsageSessionObserver(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void registerAppUsageLimitObserver(int i, java.lang.String[] strArr, java.time.Duration duration, java.time.Duration duration2, android.app.PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageLimitObserver(i, strArr, duration.toMillis(), duration2.toMillis(), pendingIntent, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterAppUsageLimitObserver(int i) {
        try {
            this.mService.unregisterAppUsageLimitObserver(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserInteraction(java.lang.String str, int i) {
        try {
            this.mService.reportUserInteraction(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserInteraction(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) {
        try {
            this.mService.reportUserInteractionWithBundle(str, i, persistableBundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void reportUsageStart(android.app.Activity activity, java.lang.String str) {
        try {
            this.mService.reportUsageStart(activity.getActivityToken(), str, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void reportUsageStart(android.app.Activity activity, java.lang.String str, long j) {
        try {
            this.mService.reportPastUsageStart(activity.getActivityToken(), str, j, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void reportUsageStop(android.app.Activity activity, java.lang.String str) {
        try {
            this.mService.reportUsageStop(activity.getActivityToken(), str, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getUsageSource() {
        try {
            return this.mService.getUsageSource();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUsageSourceSettingRead() {
        try {
            this.mService.forceUsageSourceSettingRead();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String reasonToString(int i) {
        int i2 = i & 255;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        switch (i & 65280) {
            case 256:
                sb.append(android.app.blob.XmlTags.ATTR_DESCRIPTION);
                switch (i2) {
                    case 1:
                        sb.append("-au");
                        break;
                    case 2:
                        sb.append("-ar");
                        break;
                }
            case 512:
                sb.append("t");
                break;
            case 768:
                sb.append(android.app.blob.XmlTags.ATTR_UID);
                switch (i2) {
                    case 1:
                        sb.append("-si");
                        break;
                    case 2:
                        sb.append("-ns");
                        break;
                    case 3:
                        sb.append("-ui");
                        break;
                    case 4:
                        sb.append("-mf");
                        break;
                    case 5:
                        sb.append("-mb");
                        break;
                    case 6:
                        sb.append("-su");
                        break;
                    case 7:
                        sb.append("-at");
                        break;
                    case 8:
                        sb.append("-sa");
                        break;
                    case 9:
                        sb.append("-lp");
                        break;
                    case 10:
                        sb.append("-lv");
                        break;
                    case 11:
                        sb.append("-en");
                        break;
                    case 12:
                        sb.append("-ed");
                        break;
                    case 13:
                        sb.append("-es");
                        break;
                    case 14:
                        sb.append("-uss");
                        break;
                    case 15:
                        sb.append("-fss");
                        break;
                }
            case 1024:
                sb.append(android.app.backup.FullBackup.FILES_TREE_TOKEN);
                if (i2 > 0) {
                    sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE).append(java.lang.Integer.toBinaryString(i2));
                    break;
                }
                break;
            case 1280:
                sb.append("p");
                switch (i2) {
                    case 1:
                        sb.append("-r");
                        break;
                }
            case 1536:
                sb.append(android.app.blob.XmlTags.TAG_SESSION);
                if (i2 > 0) {
                    sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE).append(java.lang.Integer.toBinaryString(i2));
                    break;
                }
                break;
        }
        return sb.toString();
    }

    public static java.lang.String usageSourceToString(int i) {
        switch (i) {
            case 1:
                return "TASK_ROOT_ACTIVITY";
            case 2:
                return "CURRENT_ACTIVITY";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String standbyBucketToString(int i) {
        switch (i) {
            case 5:
                return "EXEMPTED";
            case 10:
                return "ACTIVE";
            case 20:
                return "WORKING_SET";
            case 30:
                return "FREQUENT";
            case 40:
                return "RARE";
            case 45:
                return "RESTRICTED";
            case 50:
                return "NEVER";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void whitelistAppTemporarily(java.lang.String str, long j, android.os.UserHandle userHandle) {
        ((android.os.PowerWhitelistManager) this.mContext.getSystemService(android.os.PowerWhitelistManager.class)).whitelistAppTemporarily(str, j);
    }

    @android.annotation.SystemApi
    public void onCarrierPrivilegedAppsChanged() {
        try {
            this.mService.onCarrierPrivilegedAppsChanged();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportChooserSelection(java.lang.String str, int i, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3) {
        try {
            this.mService.reportChooserSelection(str, i, str2, strArr, str3);
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public long getLastTimeAnyComponentUsed(java.lang.String str) {
        try {
            return this.mService.getLastTimeAnyComponentUsed(str, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.app.usage.BroadcastResponseStats> queryBroadcastResponseStats(java.lang.String str, long j) {
        try {
            return this.mService.queryBroadcastResponseStats(str, j, this.mContext.getOpPackageName(), this.mContext.getUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void clearBroadcastResponseStats(java.lang.String str, long j) {
        try {
            this.mService.clearBroadcastResponseStats(str, j, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearBroadcastEvents() {
        try {
            this.mService.clearBroadcastEvents(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPackageExemptedFromBroadcastResponseStats(java.lang.String str) {
        try {
            return this.mService.isPackageExemptedFromBroadcastResponseStats(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getAppStandbyConstant(java.lang.String str) {
        try {
            return this.mService.getAppStandbyConstant(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
