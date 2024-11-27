package com.android.server.usage;

/* loaded from: classes2.dex */
class UserUsageStatsService {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "UsageStatsService";
    private static final int sDateFormatFlags = 131093;
    private final android.content.Context mContext;
    private final com.android.server.usage.UsageStatsDatabase mDatabase;
    private java.lang.String mLastBackgroundedPackage;
    private final com.android.server.usage.UserUsageStatsService.StatsUpdatedListener mListener;
    private final java.lang.String mLogPrefix;
    private final int mUserId;
    private static final java.text.SimpleDateFormat sDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long[] INTERVAL_LENGTH = {86400000, com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS, com.android.server.usage.UnixCalendar.MONTH_IN_MILLIS, 31536000000L};
    private static final com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.UsageStats> sUsageStatsCombiner = new com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.UsageStats>() { // from class: com.android.server.usage.UserUsageStatsService.1
        @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
        public boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List<android.app.usage.UsageStats> list) {
            if (!z) {
                list.addAll(intervalStats.packageStats.values());
                return true;
            }
            int size = intervalStats.packageStats.size();
            for (int i = 0; i < size; i++) {
                list.add(new android.app.usage.UsageStats(intervalStats.packageStats.valueAt(i)));
            }
            return true;
        }
    };
    private static final com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.ConfigurationStats> sConfigStatsCombiner = new com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.ConfigurationStats>() { // from class: com.android.server.usage.UserUsageStatsService.2
        @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
        public boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List<android.app.usage.ConfigurationStats> list) {
            if (!z) {
                list.addAll(intervalStats.configurations.values());
                return true;
            }
            int size = intervalStats.configurations.size();
            for (int i = 0; i < size; i++) {
                list.add(new android.app.usage.ConfigurationStats(intervalStats.configurations.valueAt(i)));
            }
            return true;
        }
    };
    private static final com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.EventStats> sEventStatsCombiner = new com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.EventStats>() { // from class: com.android.server.usage.UserUsageStatsService.3
        @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
        public boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List<android.app.usage.EventStats> list) {
            intervalStats.addEventStatsTo(list);
            return true;
        }
    };
    private boolean mStatsChanged = false;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.usage.UserUsageStatsService.CachedEarlyEvents> mCachedEarlyEvents = new android.util.SparseArrayMap<>();
    private final com.android.server.usage.UnixCalendar mDailyExpiryDate = new com.android.server.usage.UnixCalendar(0);
    private final com.android.server.usage.IntervalStats[] mCurrentStats = new com.android.server.usage.IntervalStats[4];
    private long mRealTimeSnapshot = android.os.SystemClock.elapsedRealtime();
    private long mSystemTimeSnapshot = java.lang.System.currentTimeMillis();

    private static final class CachedEarlyEvents {
        public long eventTime;

        @android.annotation.Nullable
        public java.util.List<android.app.usage.UsageEvents.Event> events;
        public long searchBeginTime;

        private CachedEarlyEvents() {
        }
    }

    interface StatsUpdatedListener {
        void onNewUpdate(int i);

        void onStatsReloaded();

        void onStatsUpdated();
    }

    UserUsageStatsService(android.content.Context context, int i, java.io.File file, com.android.server.usage.UserUsageStatsService.StatsUpdatedListener statsUpdatedListener) {
        this.mContext = context;
        this.mDatabase = new com.android.server.usage.UsageStatsDatabase(file);
        this.mListener = statsUpdatedListener;
        this.mLogPrefix = "User[" + java.lang.Integer.toString(i) + "] ";
        this.mUserId = i;
    }

    void init(long j, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, boolean z) {
        readPackageMappingsLocked(hashMap, z);
        this.mDatabase.init(j);
        if (this.mDatabase.wasUpgradePerformed()) {
            this.mDatabase.prunePackagesDataOnUpgrade(hashMap);
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mCurrentStats.length; i2++) {
            this.mCurrentStats[i2] = this.mDatabase.getLatestUsageStats(i2);
            if (this.mCurrentStats[i2] == null) {
                i++;
            }
        }
        if (i > 0) {
            if (i != this.mCurrentStats.length) {
                android.util.Slog.w(TAG, this.mLogPrefix + "Some stats have no latest available");
            }
            loadActiveStats(j);
        } else {
            updateRolloverDeadline();
        }
        com.android.server.usage.IntervalStats intervalStats = this.mCurrentStats[0];
        if (intervalStats != null) {
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event(26, java.lang.Math.max(intervalStats.lastTimeSaved, intervalStats.endTime));
            event.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
            intervalStats.addEvent(event);
            android.app.usage.UsageEvents.Event event2 = new android.app.usage.UsageEvents.Event(27, java.lang.System.currentTimeMillis());
            event2.mPackage = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
            intervalStats.addEvent(event2);
        }
        if (this.mDatabase.isNewUpdate()) {
            notifyNewUpdate();
        }
    }

    void userStopped() {
        persistActiveStats();
        this.mCachedEarlyEvents.clear();
    }

    int onPackageRemoved(java.lang.String str, long j) {
        for (int numMaps = this.mCachedEarlyEvents.numMaps() - 1; numMaps >= 0; numMaps--) {
            this.mCachedEarlyEvents.delete(this.mCachedEarlyEvents.keyAt(numMaps), str);
        }
        return this.mDatabase.onPackageRemoved(str, j);
    }

    private void readPackageMappingsLocked(java.util.HashMap<java.lang.String, java.lang.Long> hashMap, boolean z) {
        this.mDatabase.readMappingsLocked();
        if (this.mUserId != 0 && z) {
            updatePackageMappingsLocked(hashMap);
        }
    }

    boolean updatePackageMappingsLocked(java.util.HashMap<java.lang.String, java.lang.Long> hashMap) {
        if (com.android.internal.util.ArrayUtils.isEmpty(hashMap)) {
            return true;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int size = this.mDatabase.mPackagesTokenData.packagesToTokensMap.size() - 1; size >= 0; size--) {
            java.lang.String keyAt = this.mDatabase.mPackagesTokenData.packagesToTokensMap.keyAt(size);
            if (!hashMap.containsKey(keyAt)) {
                arrayList.add(keyAt);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            this.mDatabase.mPackagesTokenData.removePackage((java.lang.String) arrayList.get(size2), currentTimeMillis);
        }
        try {
            this.mDatabase.writeMappingsLocked();
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unable to write updated package mappings file on service initialization.");
            return false;
        }
    }

    boolean pruneUninstalledPackagesData() {
        return this.mDatabase.pruneUninstalledPackagesData();
    }

    private void onTimeChanged(long j, long j2) {
        this.mCachedEarlyEvents.clear();
        persistActiveStats();
        this.mDatabase.onTimeChanged(j2 - j);
        loadActiveStats(j2);
    }

    private long checkAndGetTimeLocked() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (!com.android.server.usage.UsageStatsService.ENABLE_TIME_CHANGE_CORRECTION) {
            return currentTimeMillis;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = (elapsedRealtime - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
        long j2 = currentTimeMillis - j;
        if (java.lang.Math.abs(j2) > 2000) {
            android.util.Slog.i(TAG, this.mLogPrefix + "Time changed in by " + (j2 / 1000) + " seconds");
            onTimeChanged(j, currentTimeMillis);
            this.mRealTimeSnapshot = elapsedRealtime;
            this.mSystemTimeSnapshot = currentTimeMillis;
        }
        return currentTimeMillis;
    }

    private void convertToSystemTimeLocked(android.app.usage.UsageEvents.Event event) {
        event.mTimeStamp = java.lang.Math.max(0L, event.mTimeStamp - this.mRealTimeSnapshot) + this.mSystemTimeSnapshot;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x006a, code lost:
    
        if (r15.mPackage.equals(r14.mLastBackgroundedPackage) == false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void reportEvent(android.app.usage.UsageEvents.Event event) {
        if (event.mEventType != 7 && event.mEventType != 31) {
            checkAndGetTimeLocked();
            convertToSystemTimeLocked(event);
        }
        if (event.mTimeStamp >= this.mDailyExpiryDate.getTimeInMillis()) {
            rolloverStats(event.mTimeStamp);
        }
        com.android.server.usage.IntervalStats intervalStats = this.mCurrentStats[0];
        android.content.res.Configuration configuration = event.mConfiguration;
        if (event.mEventType == 5 && intervalStats.activeConfiguration != null) {
            event.mConfiguration = android.content.res.Configuration.generateDelta(intervalStats.activeConfiguration, configuration);
        }
        if (event.mEventType != 6 && event.mEventType != 24 && event.mEventType != 25 && event.mEventType != 26 && event.mEventType != 31) {
            intervalStats.addEvent(event);
        }
        boolean z = true;
        if (event.mEventType == 1) {
            if (event.mPackage != null) {
            }
        } else if (event.mEventType == 2 && event.mPackage != null) {
            this.mLastBackgroundedPackage = event.mPackage;
        }
        z = false;
        for (com.android.server.usage.IntervalStats intervalStats2 : this.mCurrentStats) {
            switch (event.mEventType) {
                case 5:
                    intervalStats2.updateConfigurationStats(configuration, event.mTimeStamp);
                    break;
                case 9:
                    intervalStats2.updateChooserCounts(event.mPackage, event.mContentType, event.mAction);
                    java.lang.String[] strArr = event.mContentAnnotations;
                    if (strArr != null) {
                        for (java.lang.String str : strArr) {
                            intervalStats2.updateChooserCounts(event.mPackage, str, event.mAction);
                        }
                        break;
                    } else {
                        break;
                    }
                case 15:
                    intervalStats2.updateScreenInteractive(event.mTimeStamp);
                    break;
                case 16:
                    intervalStats2.updateScreenNonInteractive(event.mTimeStamp);
                    break;
                case 17:
                    intervalStats2.updateKeyguardShown(event.mTimeStamp);
                    break;
                case 18:
                    intervalStats2.updateKeyguardHidden(event.mTimeStamp);
                    break;
                default:
                    intervalStats2.update(event.mPackage, event.getClassName(), event.mTimeStamp, event.mEventType, event.mInstanceId);
                    if (z) {
                        intervalStats2.incrementAppLaunchCount(event.mPackage);
                        break;
                    } else {
                        break;
                    }
            }
        }
        notifyStatsChanged();
    }

    private static boolean validRange(long j, long j2, long j3) {
        return j2 <= j && j2 < j3;
    }

    @android.annotation.Nullable
    private <T> java.util.List<T> queryStats(int i, long j, long j2, com.android.server.usage.UsageStatsDatabase.StatCombiner<T> statCombiner, boolean z) {
        int i2 = i;
        if (i2 == 4) {
            int findBestFitBucket = this.mDatabase.findBestFitBucket(j, j2);
            if (findBestFitBucket >= 0) {
                i2 = findBestFitBucket;
            } else {
                i2 = 0;
            }
        }
        if (i2 < 0 || i2 >= this.mCurrentStats.length) {
            return null;
        }
        com.android.server.usage.IntervalStats intervalStats = this.mCurrentStats[i2];
        if (j >= intervalStats.endTime) {
            return null;
        }
        java.util.List<T> queryUsageStats = this.mDatabase.queryUsageStats(i2, j, java.lang.Math.min(intervalStats.beginTime, j2), statCombiner, z);
        if (j < intervalStats.endTime && j2 > intervalStats.beginTime) {
            if (queryUsageStats == null) {
                queryUsageStats = new java.util.ArrayList<>();
            }
            this.mDatabase.filterStats(intervalStats);
            statCombiner.combine(intervalStats, true, queryUsageStats);
        }
        return queryUsageStats;
    }

    java.util.List<android.app.usage.UsageStats> queryUsageStats(int i, long j, long j2) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        return queryStats(i, j, j2, sUsageStatsCombiner, true);
    }

    java.util.List<android.app.usage.ConfigurationStats> queryConfigurationStats(int i, long j, long j2) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        return queryStats(i, j, j2, sConfigStatsCombiner, true);
    }

    java.util.List<android.app.usage.EventStats> queryEventStats(int i, long j, long j2) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        return queryStats(i, j, j2, sEventStatsCombiner, true);
    }

    android.app.usage.UsageEvents queryEvents(final long j, final long j2, final int i, int[] iArr, final android.util.ArraySet<java.lang.String> arraySet) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final boolean isEmpty = com.android.internal.util.ArrayUtils.isEmpty(iArr);
        final boolean z = arraySet == null || arraySet.isEmpty();
        final boolean[] zArr = new boolean[32];
        if (!isEmpty) {
            for (int i2 : iArr) {
                if (i2 < 0 || i2 > 31) {
                    throw new java.lang.IllegalArgumentException("invalid event type: " + i2);
                }
                zArr[i2] = true;
            }
        }
        final android.util.ArraySet arraySet2 = new android.util.ArraySet();
        java.util.List queryStats = queryStats(0, j, j2, new com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.UsageEvents.Event>() { // from class: com.android.server.usage.UserUsageStatsService.4
            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
            public boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z2, java.util.List<android.app.usage.UsageEvents.Event> list) {
                int size = intervalStats.events.size();
                for (int firstIndexOnOrAfter = intervalStats.events.firstIndexOnOrAfter(j); firstIndexOnOrAfter < size; firstIndexOnOrAfter++) {
                    android.app.usage.UsageEvents.Event event = intervalStats.events.get(firstIndexOnOrAfter);
                    if (event.mTimeStamp >= j2) {
                        return false;
                    }
                    int i3 = event.mEventType;
                    if ((isEmpty || zArr[i3]) && ((i3 != 8 || (i & 2) != 2) && (i3 != 30 || (i & 8) != 8))) {
                        if ((i3 == 10 || i3 == 12) && (i & 4) == 4) {
                            event = event.getObfuscatedNotificationEvent();
                        }
                        if ((i & 1) == 1) {
                            event = event.getObfuscatedIfInstantApp();
                        }
                        if (z || arraySet.contains(event.mPackage)) {
                            if (event.mPackage != null) {
                                arraySet2.add(event.mPackage);
                            }
                            if (event.mClass != null) {
                                arraySet2.add(event.mClass);
                            }
                            if (event.mTaskRootPackage != null) {
                                arraySet2.add(event.mTaskRootPackage);
                            }
                            if (event.mTaskRootClass != null) {
                                arraySet2.add(event.mTaskRootClass);
                            }
                            list.add(event);
                        }
                    }
                }
                return true;
            }
        }, false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        java.lang.String[] strArr = (java.lang.String[]) arraySet2.toArray(new java.lang.String[arraySet2.size()]);
        java.util.Arrays.sort(strArr);
        return new android.app.usage.UsageEvents(queryStats, strArr, true);
    }

    @android.annotation.Nullable
    android.app.usage.UsageEvents queryEarliestAppEvents(final long j, final long j2, final int i) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        final android.util.ArraySet arraySet2 = new android.util.ArraySet();
        java.util.List queryStats = queryStats(0, j, j2, new com.android.server.usage.UsageStatsDatabase.StatCombiner() { // from class: com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda1
            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
            public final boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List list) {
                boolean lambda$queryEarliestAppEvents$0;
                lambda$queryEarliestAppEvents$0 = com.android.server.usage.UserUsageStatsService.lambda$queryEarliestAppEvents$0(j, j2, arraySet2, arraySet, i, intervalStats, z, list);
                return lambda$queryEarliestAppEvents$0;
            }
        }, false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        java.lang.String[] strArr = (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
        java.util.Arrays.sort(strArr);
        return new android.app.usage.UsageEvents(queryStats, strArr, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$queryEarliestAppEvents$0(long j, long j2, android.util.ArraySet arraySet, android.util.ArraySet arraySet2, int i, com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List list) {
        int size = intervalStats.events.size();
        for (int firstIndexOnOrAfter = intervalStats.events.firstIndexOnOrAfter(j); firstIndexOnOrAfter < size; firstIndexOnOrAfter++) {
            android.app.usage.UsageEvents.Event event = intervalStats.events.get(firstIndexOnOrAfter);
            if (event.getTimeStamp() >= j2) {
                return false;
            }
            if (event.getPackageName() != null && !arraySet.contains(event.getPackageName())) {
                boolean add = arraySet2.add(event.getPackageName());
                if (event.getEventType() == i) {
                    list.add(event);
                    arraySet.add(event.getPackageName());
                } else if (add) {
                    list.add(event);
                }
            }
        }
        return true;
    }

    @android.annotation.Nullable
    android.app.usage.UsageEvents queryEventsForPackage(final long j, final long j2, final java.lang.String str, final boolean z) {
        if (!validRange(checkAndGetTimeLocked(), j, j2)) {
            return null;
        }
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.add(str);
        java.util.List queryStats = queryStats(0, j, j2, new com.android.server.usage.UsageStatsDatabase.StatCombiner() { // from class: com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda0
            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
            public final boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z2, java.util.List list) {
                boolean lambda$queryEventsForPackage$1;
                lambda$queryEventsForPackage$1 = com.android.server.usage.UserUsageStatsService.lambda$queryEventsForPackage$1(j, j2, str, arraySet, z, intervalStats, z2, list);
                return lambda$queryEventsForPackage$1;
            }
        }, false);
        if (queryStats == null || queryStats.isEmpty()) {
            return null;
        }
        java.lang.String[] strArr = (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
        java.util.Arrays.sort(strArr);
        return new android.app.usage.UsageEvents(queryStats, strArr, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$queryEventsForPackage$1(long j, long j2, java.lang.String str, android.util.ArraySet arraySet, boolean z, com.android.server.usage.IntervalStats intervalStats, boolean z2, java.util.List list) {
        int size = intervalStats.events.size();
        for (int firstIndexOnOrAfter = intervalStats.events.firstIndexOnOrAfter(j); firstIndexOnOrAfter < size; firstIndexOnOrAfter++) {
            android.app.usage.UsageEvents.Event event = intervalStats.events.get(firstIndexOnOrAfter);
            if (event.mTimeStamp >= j2) {
                return false;
            }
            if (str.equals(event.mPackage)) {
                if (event.mClass != null) {
                    arraySet.add(event.mClass);
                }
                if (z && event.mTaskRootPackage != null) {
                    arraySet.add(event.mTaskRootPackage);
                }
                if (z && event.mTaskRootClass != null) {
                    arraySet.add(event.mTaskRootClass);
                }
                list.add(event);
            }
        }
        return true;
    }

    @android.annotation.Nullable
    android.app.usage.UsageEvents queryEarliestEventsForPackage(long j, final long j2, @android.annotation.NonNull final java.lang.String str, final int i) {
        long j3;
        com.android.server.usage.UserUsageStatsService.CachedEarlyEvents cachedEarlyEvents;
        long checkAndGetTimeLocked = checkAndGetTimeLocked();
        if (!validRange(checkAndGetTimeLocked, j, j2)) {
            return null;
        }
        com.android.server.usage.UserUsageStatsService.CachedEarlyEvents cachedEarlyEvents2 = (com.android.server.usage.UserUsageStatsService.CachedEarlyEvents) this.mCachedEarlyEvents.get(i, str);
        if (cachedEarlyEvents2 == null) {
            com.android.server.usage.UserUsageStatsService.CachedEarlyEvents cachedEarlyEvents3 = new com.android.server.usage.UserUsageStatsService.CachedEarlyEvents();
            cachedEarlyEvents3.searchBeginTime = j;
            this.mCachedEarlyEvents.add(i, str, cachedEarlyEvents3);
            j3 = j;
            cachedEarlyEvents = cachedEarlyEvents3;
        } else if (cachedEarlyEvents2.searchBeginTime <= j && j <= cachedEarlyEvents2.eventTime) {
            int size = cachedEarlyEvents2.events == null ? 0 : cachedEarlyEvents2.events.size();
            if ((size != 0 && cachedEarlyEvents2.events.get(size - 1).getEventType() == i) || cachedEarlyEvents2.eventTime >= j2) {
                if (cachedEarlyEvents2.eventTime > j2 || cachedEarlyEvents2.events == null) {
                    return null;
                }
                return new android.app.usage.UsageEvents(cachedEarlyEvents2.events, new java.lang.String[]{str}, false);
            }
            cachedEarlyEvents = cachedEarlyEvents2;
            j3 = java.lang.Math.min(checkAndGetTimeLocked, cachedEarlyEvents2.eventTime);
        } else {
            cachedEarlyEvents2.searchBeginTime = j;
            j3 = j;
            cachedEarlyEvents = cachedEarlyEvents2;
        }
        final long j4 = j3;
        java.util.List<android.app.usage.UsageEvents.Event> queryStats = queryStats(0, j4, j2, new com.android.server.usage.UsageStatsDatabase.StatCombiner() { // from class: com.android.server.usage.UserUsageStatsService$$ExternalSyntheticLambda2
            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
            public final boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List list) {
                boolean lambda$queryEarliestEventsForPackage$2;
                lambda$queryEarliestEventsForPackage$2 = com.android.server.usage.UserUsageStatsService.lambda$queryEarliestEventsForPackage$2(j4, j2, str, i, intervalStats, z, list);
                return lambda$queryEarliestEventsForPackage$2;
            }
        }, false);
        if (queryStats == null || queryStats.isEmpty()) {
            cachedEarlyEvents.eventTime = java.lang.Math.min(checkAndGetTimeLocked, j2);
            cachedEarlyEvents.events = null;
            return null;
        }
        cachedEarlyEvents.eventTime = queryStats.get(queryStats.size() - 1).getTimeStamp();
        cachedEarlyEvents.events = queryStats;
        return new android.app.usage.UsageEvents(queryStats, new java.lang.String[]{str}, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$queryEarliestEventsForPackage$2(long j, long j2, java.lang.String str, int i, com.android.server.usage.IntervalStats intervalStats, boolean z, java.util.List list) {
        int size = intervalStats.events.size();
        for (int firstIndexOnOrAfter = intervalStats.events.firstIndexOnOrAfter(j); firstIndexOnOrAfter < size; firstIndexOnOrAfter++) {
            android.app.usage.UsageEvents.Event event = intervalStats.events.get(firstIndexOnOrAfter);
            if (event.getTimeStamp() >= j2) {
                return false;
            }
            if (str.equals(event.getPackageName())) {
                if (event.getEventType() == i) {
                    list.add(event);
                    return false;
                }
                if (list.size() == 0) {
                    list.add(event);
                }
            }
        }
        return true;
    }

    void persistActiveStats() {
        if (this.mStatsChanged) {
            android.util.Slog.i(TAG, this.mLogPrefix + "Flushing usage stats to disk");
            try {
                this.mDatabase.obfuscateCurrentStats(this.mCurrentStats);
                this.mDatabase.writeMappingsLocked();
                for (int i = 0; i < this.mCurrentStats.length; i++) {
                    this.mDatabase.putUsageStats(i, this.mCurrentStats[i]);
                }
                this.mStatsChanged = false;
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, this.mLogPrefix + "Failed to persist active stats", e);
            }
        }
    }

    private void rolloverStats(long j) {
        android.util.ArraySet arraySet;
        android.util.ArrayMap arrayMap;
        int i;
        int i2;
        com.android.server.usage.IntervalStats[] intervalStatsArr;
        long j2;
        com.android.server.usage.IntervalStats[] intervalStatsArr2;
        android.app.usage.UsageStats usageStats;
        int i3;
        int i4;
        com.android.server.usage.IntervalStats intervalStats;
        android.app.usage.UsageStats usageStats2;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        android.util.Slog.i(TAG, this.mLogPrefix + "Rolling over usage stats");
        int i5 = 0;
        android.content.res.Configuration configuration = this.mCurrentStats[0].activeConfiguration;
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        com.android.server.usage.IntervalStats[] intervalStatsArr3 = this.mCurrentStats;
        int length = intervalStatsArr3.length;
        int i6 = 0;
        while (i6 < length) {
            com.android.server.usage.IntervalStats intervalStats2 = intervalStatsArr3[i6];
            int size = intervalStats2.packageStats.size();
            int i7 = i5;
            while (i7 < size) {
                android.app.usage.UsageStats valueAt = intervalStats2.packageStats.valueAt(i7);
                if (valueAt.mActivities.size() > 0 || !valueAt.mForegroundServices.isEmpty()) {
                    if (valueAt.mActivities.size() <= 0) {
                        intervalStatsArr2 = intervalStatsArr3;
                        usageStats = valueAt;
                        i3 = i7;
                        i4 = size;
                        intervalStats = intervalStats2;
                    } else {
                        intervalStatsArr2 = intervalStatsArr3;
                        arrayMap2.put(valueAt.mPackageName, valueAt.mActivities);
                        usageStats = valueAt;
                        i3 = i7;
                        i4 = size;
                        intervalStats = intervalStats2;
                        intervalStats2.update(valueAt.mPackageName, null, this.mDailyExpiryDate.getTimeInMillis() - 1, 3, 0);
                    }
                    android.app.usage.UsageStats usageStats3 = usageStats;
                    if (usageStats3.mForegroundServices.isEmpty()) {
                        usageStats2 = usageStats3;
                    } else {
                        arrayMap3.put(usageStats3.mPackageName, usageStats3.mForegroundServices);
                        usageStats2 = usageStats3;
                        intervalStats.update(usageStats3.mPackageName, null, this.mDailyExpiryDate.getTimeInMillis() - 1, 22, 0);
                    }
                    arraySet2.add(usageStats2.mPackageName);
                    notifyStatsChanged();
                } else {
                    intervalStatsArr2 = intervalStatsArr3;
                    i3 = i7;
                    i4 = size;
                    intervalStats = intervalStats2;
                }
                i7 = i3 + 1;
                size = i4;
                intervalStats2 = intervalStats;
                intervalStatsArr3 = intervalStatsArr2;
            }
            com.android.server.usage.IntervalStats[] intervalStatsArr4 = intervalStatsArr3;
            com.android.server.usage.IntervalStats intervalStats3 = intervalStats2;
            intervalStats3.updateConfigurationStats(null, this.mDailyExpiryDate.getTimeInMillis() - 1);
            intervalStats3.commitTime(this.mDailyExpiryDate.getTimeInMillis() - 1);
            i6++;
            intervalStatsArr3 = intervalStatsArr4;
            i5 = 0;
        }
        persistActiveStats();
        this.mDatabase.prune(j);
        loadActiveStats(j);
        int size2 = arraySet2.size();
        for (int i8 = 0; i8 < size2; i8++) {
            java.lang.String str = (java.lang.String) arraySet2.valueAt(i8);
            long j3 = this.mCurrentStats[0].beginTime;
            com.android.server.usage.IntervalStats[] intervalStatsArr5 = this.mCurrentStats;
            int length2 = intervalStatsArr5.length;
            int i9 = 0;
            while (i9 < length2) {
                int i10 = size2;
                com.android.server.usage.IntervalStats intervalStats4 = intervalStatsArr5[i9];
                if (!arrayMap2.containsKey(str)) {
                    arraySet = arraySet2;
                    arrayMap = arrayMap2;
                    i = i9;
                    i2 = length2;
                    intervalStatsArr = intervalStatsArr5;
                    j2 = j3;
                } else {
                    com.android.server.usage.IntervalStats[] intervalStatsArr6 = intervalStatsArr5;
                    android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) arrayMap2.get(str);
                    arraySet = arraySet2;
                    int size3 = sparseIntArray.size();
                    arrayMap = arrayMap2;
                    int i11 = 0;
                    while (i11 < size3) {
                        intervalStats4.update(str, null, j3, sparseIntArray.valueAt(i11), sparseIntArray.keyAt(i11));
                        i11++;
                        i9 = i9;
                        length2 = length2;
                        intervalStatsArr6 = intervalStatsArr6;
                        sparseIntArray = sparseIntArray;
                    }
                    i = i9;
                    i2 = length2;
                    j2 = j3;
                    intervalStatsArr = intervalStatsArr6;
                }
                if (arrayMap3.containsKey(str)) {
                    android.util.ArrayMap arrayMap4 = (android.util.ArrayMap) arrayMap3.get(str);
                    int size4 = arrayMap4.size();
                    for (int i12 = 0; i12 < size4; i12++) {
                        intervalStats4.update(str, (java.lang.String) arrayMap4.keyAt(i12), j2, ((java.lang.Integer) arrayMap4.valueAt(i12)).intValue(), 0);
                    }
                }
                long j4 = j2;
                intervalStats4.updateConfigurationStats(configuration, j4);
                notifyStatsChanged();
                i9 = i + 1;
                size2 = i10;
                j3 = j4;
                arraySet2 = arraySet;
                arrayMap2 = arrayMap;
                length2 = i2;
                intervalStatsArr5 = intervalStatsArr;
            }
        }
        persistActiveStats();
        android.util.Slog.i(TAG, this.mLogPrefix + "Rolling over usage stats complete. Took " + (android.os.SystemClock.elapsedRealtime() - elapsedRealtime) + " milliseconds");
    }

    private void notifyStatsChanged() {
        if (!this.mStatsChanged) {
            this.mStatsChanged = true;
            this.mListener.onStatsUpdated();
        }
    }

    private void notifyNewUpdate() {
        this.mListener.onNewUpdate(this.mUserId);
    }

    private void loadActiveStats(long j) {
        for (int i = 0; i < this.mCurrentStats.length; i++) {
            com.android.server.usage.IntervalStats latestUsageStats = this.mDatabase.getLatestUsageStats(i);
            if (latestUsageStats != null && j < latestUsageStats.beginTime + INTERVAL_LENGTH[i]) {
                this.mCurrentStats[i] = latestUsageStats;
            } else {
                this.mCurrentStats[i] = new com.android.server.usage.IntervalStats();
                this.mCurrentStats[i].beginTime = j;
                this.mCurrentStats[i].endTime = 1 + j;
            }
        }
        this.mStatsChanged = false;
        updateRolloverDeadline();
        this.mListener.onStatsReloaded();
    }

    private void updateRolloverDeadline() {
        this.mDailyExpiryDate.setTimeInMillis(this.mCurrentStats[0].beginTime);
        this.mDailyExpiryDate.addDays(1);
        android.util.Slog.i(TAG, this.mLogPrefix + "Rollover scheduled @ " + sDateFormat.format(java.lang.Long.valueOf(this.mDailyExpiryDate.getTimeInMillis())) + "(" + this.mDailyExpiryDate.getTimeInMillis() + ")");
    }

    void checkin(final com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        this.mDatabase.checkinDailyFiles(new com.android.server.usage.UsageStatsDatabase.CheckinAction() { // from class: com.android.server.usage.UserUsageStatsService.5
            @Override // com.android.server.usage.UsageStatsDatabase.CheckinAction
            public boolean checkin(com.android.server.usage.IntervalStats intervalStats) {
                com.android.server.usage.UserUsageStatsService.this.printIntervalStats(indentingPrintWriter, intervalStats, false, false, null);
                return true;
            }
        });
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.util.List<java.lang.String> list) {
        dump(indentingPrintWriter, list, false);
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.util.List<java.lang.String> list, boolean z) {
        printLast24HrEvents(indentingPrintWriter, !z, list);
        for (int i = 0; i < this.mCurrentStats.length; i++) {
            indentingPrintWriter.print("In-memory ");
            indentingPrintWriter.print(intervalToString(i));
            indentingPrintWriter.println(" stats");
            printIntervalStats(indentingPrintWriter, this.mCurrentStats[i], !z, true, list);
        }
        if (com.android.internal.util.CollectionUtils.isEmpty(list)) {
            this.mDatabase.dump(indentingPrintWriter, z);
        }
    }

    void dumpDatabaseInfo(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        this.mDatabase.dump(indentingPrintWriter, false);
    }

    void dumpMappings(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        this.mDatabase.dumpMappings(indentingPrintWriter);
    }

    void deleteDataFor(java.lang.String str) {
        this.mDatabase.deleteDataFor(str);
    }

    void dumpFile(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            int length = this.mDatabase.mSortedStatFiles.length;
            for (int i = 0; i < length; i++) {
                indentingPrintWriter.println("interval=" + intervalToString(i));
                indentingPrintWriter.increaseIndent();
                dumpFileDetailsForInterval(indentingPrintWriter, i);
                indentingPrintWriter.decreaseIndent();
            }
            return;
        }
        try {
            int stringToInterval = stringToInterval(strArr[0]);
            if (stringToInterval == -1) {
                stringToInterval = java.lang.Integer.valueOf(strArr[0]).intValue();
            }
            if (stringToInterval < 0 || stringToInterval >= this.mDatabase.mSortedStatFiles.length) {
                indentingPrintWriter.println("the specified interval does not exist.");
                return;
            }
            if (strArr.length == 1) {
                dumpFileDetailsForInterval(indentingPrintWriter, stringToInterval);
                return;
            }
            try {
                com.android.server.usage.IntervalStats readIntervalStatsForFile = this.mDatabase.readIntervalStatsForFile(stringToInterval, java.lang.Long.valueOf(strArr[1]).longValue());
                if (readIntervalStatsForFile == null) {
                    indentingPrintWriter.println("the specified filename does not exist.");
                } else {
                    dumpFileDetails(indentingPrintWriter, readIntervalStatsForFile, java.lang.Long.valueOf(strArr[1]).longValue());
                }
            } catch (java.lang.NumberFormatException e) {
                indentingPrintWriter.println("invalid filename specified.");
            }
        } catch (java.lang.NumberFormatException e2) {
            indentingPrintWriter.println("invalid interval specified.");
        }
    }

    private void dumpFileDetailsForInterval(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, int i) {
        android.util.LongSparseArray<android.util.AtomicFile> longSparseArray = this.mDatabase.mSortedStatFiles[i];
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            long keyAt = longSparseArray.keyAt(i2);
            dumpFileDetails(indentingPrintWriter, this.mDatabase.readIntervalStatsForFile(i, keyAt), keyAt);
            indentingPrintWriter.println();
        }
    }

    private void dumpFileDetails(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.usage.IntervalStats intervalStats, long j) {
        indentingPrintWriter.println("file=" + j);
        indentingPrintWriter.increaseIndent();
        printIntervalStats(indentingPrintWriter, intervalStats, false, false, null);
        indentingPrintWriter.decreaseIndent();
    }

    static java.lang.String formatDateTime(long j, boolean z) {
        if (z) {
            return "\"" + sDateFormat.format(java.lang.Long.valueOf(j)) + "\"";
        }
        return java.lang.Long.toString(j);
    }

    private java.lang.String formatElapsedTime(long j, boolean z) {
        if (z) {
            return "\"" + android.text.format.DateUtils.formatElapsedTime(j / 1000) + "\"";
        }
        return java.lang.Long.toString(j);
    }

    static void printEvent(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, android.app.usage.UsageEvents.Event event, boolean z) {
        indentingPrintWriter.printPair("time", formatDateTime(event.mTimeStamp, z));
        indentingPrintWriter.printPair(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, eventToString(event.mEventType));
        indentingPrintWriter.printPair(com.android.server.pm.Settings.ATTR_PACKAGE, event.mPackage);
        if (event.mClass != null) {
            indentingPrintWriter.printPair("class", event.mClass);
        }
        if (event.mConfiguration != null) {
            indentingPrintWriter.printPair("config", android.content.res.Configuration.resourceQualifierString(event.mConfiguration));
        }
        if (event.mShortcutId != null) {
            indentingPrintWriter.printPair("shortcutId", event.mShortcutId);
        }
        if (event.mEventType == 11) {
            indentingPrintWriter.printPair("standbyBucket", java.lang.Integer.valueOf(event.getAppStandbyBucket()));
            indentingPrintWriter.printPair(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY, android.app.usage.UsageStatsManager.reasonToString(event.getStandbyReason()));
        } else if (event.mEventType == 1 || event.mEventType == 2 || event.mEventType == 23) {
            indentingPrintWriter.printPair("instanceId", java.lang.Integer.valueOf(event.getInstanceId()));
        }
        if (event.getTaskRootPackageName() != null) {
            indentingPrintWriter.printPair("taskRootPackage", event.getTaskRootPackageName());
        }
        if (event.getTaskRootClassName() != null) {
            indentingPrintWriter.printPair("taskRootClass", event.getTaskRootClassName());
        }
        if (event.mNotificationChannelId != null) {
            indentingPrintWriter.printPair("channelId", event.mNotificationChannelId);
        }
        if (event.mEventType == 7 && event.mExtras != null) {
            indentingPrintWriter.print(event.mExtras.toString());
        }
        indentingPrintWriter.printHexPair("flags", event.mFlags);
        indentingPrintWriter.println();
    }

    void printLast24HrEvents(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, boolean z, final java.util.List<java.lang.String> list) {
        final long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.server.usage.UnixCalendar unixCalendar = new com.android.server.usage.UnixCalendar(currentTimeMillis);
        unixCalendar.addDays(-1);
        final long timeInMillis = unixCalendar.getTimeInMillis();
        java.util.List queryStats = queryStats(0, timeInMillis, currentTimeMillis, new com.android.server.usage.UsageStatsDatabase.StatCombiner<android.app.usage.UsageEvents.Event>() { // from class: com.android.server.usage.UserUsageStatsService.6
            @Override // com.android.server.usage.UsageStatsDatabase.StatCombiner
            public boolean combine(com.android.server.usage.IntervalStats intervalStats, boolean z2, java.util.List<android.app.usage.UsageEvents.Event> list2) {
                int size = intervalStats.events.size();
                for (int firstIndexOnOrAfter = intervalStats.events.firstIndexOnOrAfter(timeInMillis); firstIndexOnOrAfter < size; firstIndexOnOrAfter++) {
                    if (intervalStats.events.get(firstIndexOnOrAfter).mTimeStamp >= currentTimeMillis) {
                        return false;
                    }
                    android.app.usage.UsageEvents.Event event = intervalStats.events.get(firstIndexOnOrAfter);
                    if (com.android.internal.util.CollectionUtils.isEmpty(list) || list.contains(event.mPackage)) {
                        list2.add(event);
                    }
                }
                return true;
            }
        }, false);
        indentingPrintWriter.print("Last 24 hour events (");
        if (z) {
            indentingPrintWriter.printPair("timeRange", "\"" + android.text.format.DateUtils.formatDateRange(this.mContext, timeInMillis, currentTimeMillis, sDateFormatFlags) + "\"");
        } else {
            indentingPrintWriter.printPair("beginTime", java.lang.Long.valueOf(timeInMillis));
            indentingPrintWriter.printPair("endTime", java.lang.Long.valueOf(currentTimeMillis));
        }
        indentingPrintWriter.println(")");
        if (queryStats != null) {
            indentingPrintWriter.increaseIndent();
            java.util.Iterator it = queryStats.iterator();
            while (it.hasNext()) {
                printEvent(indentingPrintWriter, (android.app.usage.UsageEvents.Event) it.next(), z);
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    void printEventAggregation(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, com.android.server.usage.IntervalStats.EventTracker eventTracker, boolean z) {
        if (eventTracker.count != 0 || eventTracker.duration != 0) {
            indentingPrintWriter.print(str);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.print(eventTracker.count);
            indentingPrintWriter.print("x for ");
            indentingPrintWriter.print(formatElapsedTime(eventTracker.duration, z));
            if (eventTracker.curStartTime != 0) {
                indentingPrintWriter.print(" (now running, started at ");
                formatDateTime(eventTracker.curStartTime, z);
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.println();
        }
    }

    void printIntervalStats(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.usage.IntervalStats intervalStats, boolean z, boolean z2, java.util.List<java.lang.String> list) {
        java.util.Iterator<android.app.usage.UsageStats> it;
        android.app.usage.UsageStats usageStats;
        if (z) {
            indentingPrintWriter.printPair("timeRange", "\"" + android.text.format.DateUtils.formatDateRange(this.mContext, intervalStats.beginTime, intervalStats.endTime, sDateFormatFlags) + "\"");
        } else {
            indentingPrintWriter.printPair("beginTime", java.lang.Long.valueOf(intervalStats.beginTime));
            indentingPrintWriter.printPair("endTime", java.lang.Long.valueOf(intervalStats.endTime));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("packages");
        indentingPrintWriter.increaseIndent();
        android.util.ArrayMap<java.lang.String, android.app.usage.UsageStats> arrayMap = intervalStats.packageStats;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            android.app.usage.UsageStats valueAt = arrayMap.valueAt(i);
            if (com.android.internal.util.CollectionUtils.isEmpty(list) || list.contains(valueAt.mPackageName)) {
                indentingPrintWriter.printPair(com.android.server.pm.Settings.ATTR_PACKAGE, valueAt.mPackageName);
                indentingPrintWriter.printPair("totalTimeUsed", formatElapsedTime(valueAt.mTotalTimeInForeground, z));
                indentingPrintWriter.printPair("lastTimeUsed", formatDateTime(valueAt.mLastTimeUsed, z));
                indentingPrintWriter.printPair("totalTimeVisible", formatElapsedTime(valueAt.mTotalTimeVisible, z));
                indentingPrintWriter.printPair("lastTimeVisible", formatDateTime(valueAt.mLastTimeVisible, z));
                indentingPrintWriter.printPair("lastTimeComponentUsed", formatDateTime(valueAt.mLastTimeComponentUsed, z));
                indentingPrintWriter.printPair("totalTimeFS", formatElapsedTime(valueAt.mTotalTimeForegroundServiceUsed, z));
                indentingPrintWriter.printPair("lastTimeFS", formatDateTime(valueAt.mLastTimeForegroundServiceUsed, z));
                indentingPrintWriter.printPair("appLaunchCount", java.lang.Integer.valueOf(valueAt.mAppLaunchCount));
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("ChooserCounts");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<android.app.usage.UsageStats> it2 = arrayMap.values().iterator();
        while (it2.hasNext()) {
            android.app.usage.UsageStats next = it2.next();
            if (com.android.internal.util.CollectionUtils.isEmpty(list) || list.contains(next.mPackageName)) {
                indentingPrintWriter.printPair(com.android.server.pm.Settings.ATTR_PACKAGE, next.mPackageName);
                if (next.mChooserCounts != null) {
                    int size2 = next.mChooserCounts.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        java.lang.String str = (java.lang.String) next.mChooserCounts.keyAt(i2);
                        android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) next.mChooserCounts.valueAt(i2);
                        int size3 = arrayMap2.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            java.lang.String str2 = (java.lang.String) arrayMap2.keyAt(i3);
                            int intValue = ((java.lang.Integer) arrayMap2.valueAt(i3)).intValue();
                            if (intValue == 0) {
                                it = it2;
                                usageStats = next;
                            } else {
                                it = it2;
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append(str);
                                usageStats = next;
                                sb.append(":");
                                sb.append(str2);
                                sb.append(" is ");
                                sb.append(java.lang.Integer.toString(intValue));
                                indentingPrintWriter.printPair("ChooserCounts", sb.toString());
                                indentingPrintWriter.println();
                            }
                            i3++;
                            it2 = it;
                            next = usageStats;
                        }
                    }
                }
                indentingPrintWriter.println();
                it2 = it2;
            }
        }
        indentingPrintWriter.decreaseIndent();
        if (com.android.internal.util.CollectionUtils.isEmpty(list)) {
            indentingPrintWriter.println("configurations");
            indentingPrintWriter.increaseIndent();
            android.util.ArrayMap<android.content.res.Configuration, android.app.usage.ConfigurationStats> arrayMap3 = intervalStats.configurations;
            int size4 = arrayMap3.size();
            for (int i4 = 0; i4 < size4; i4++) {
                android.app.usage.ConfigurationStats valueAt2 = arrayMap3.valueAt(i4);
                indentingPrintWriter.printPair("config", android.content.res.Configuration.resourceQualifierString(valueAt2.mConfiguration));
                indentingPrintWriter.printPair("totalTime", formatElapsedTime(valueAt2.mTotalTimeActive, z));
                indentingPrintWriter.printPair("lastTime", formatDateTime(valueAt2.mLastTimeActive, z));
                indentingPrintWriter.printPair(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, java.lang.Integer.valueOf(valueAt2.mActivationCount));
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("event aggregations");
            indentingPrintWriter.increaseIndent();
            printEventAggregation(indentingPrintWriter, "screen-interactive", intervalStats.interactiveTracker, z);
            printEventAggregation(indentingPrintWriter, "screen-non-interactive", intervalStats.nonInteractiveTracker, z);
            printEventAggregation(indentingPrintWriter, "keyguard-shown", intervalStats.keyguardShownTracker, z);
            printEventAggregation(indentingPrintWriter, "keyguard-hidden", intervalStats.keyguardHiddenTracker, z);
            indentingPrintWriter.decreaseIndent();
        }
        if (!z2) {
            indentingPrintWriter.println("events");
            indentingPrintWriter.increaseIndent();
            android.app.usage.EventList eventList = intervalStats.events;
            int size5 = eventList != null ? eventList.size() : 0;
            for (int i5 = 0; i5 < size5; i5++) {
                android.app.usage.UsageEvents.Event event = eventList.get(i5);
                if (com.android.internal.util.CollectionUtils.isEmpty(list) || list.contains(event.mPackage)) {
                    printEvent(indentingPrintWriter, event, z);
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static java.lang.String intervalToString(int i) {
        switch (i) {
            case 0:
                return "daily";
            case 1:
                return "weekly";
            case 2:
                return "monthly";
            case 3:
                return "yearly";
            default:
                return "?";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int stringToInterval(java.lang.String str) {
        boolean z;
        java.lang.String lowerCase = str.toLowerCase();
        switch (lowerCase.hashCode()) {
            case -791707519:
                if (lowerCase.equals("weekly")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -734561654:
                if (lowerCase.equals("yearly")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 95346201:
                if (lowerCase.equals("daily")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1236635661:
                if (lowerCase.equals("monthly")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return 0;
            case true:
                return 1;
            case true:
                return 2;
            case true:
                return 3;
            default:
                return -1;
        }
    }

    static java.lang.String eventToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "ACTIVITY_RESUMED";
            case 2:
                return "ACTIVITY_PAUSED";
            case 3:
                return "END_OF_DAY";
            case 4:
                return "CONTINUE_PREVIOUS_DAY";
            case 5:
                return "CONFIGURATION_CHANGE";
            case 6:
                return "SYSTEM_INTERACTION";
            case 7:
                return "USER_INTERACTION";
            case 8:
                return "SHORTCUT_INVOCATION";
            case 9:
                return "CHOOSER_ACTION";
            case 10:
                return "NOTIFICATION_SEEN";
            case 11:
                return "STANDBY_BUCKET_CHANGED";
            case 12:
                return "NOTIFICATION_INTERRUPTION";
            case 13:
                return "SLICE_PINNED_PRIV";
            case 14:
                return "SLICE_PINNED";
            case 15:
                return "SCREEN_INTERACTIVE";
            case 16:
                return "SCREEN_NON_INTERACTIVE";
            case 17:
                return "KEYGUARD_SHOWN";
            case 18:
                return "KEYGUARD_HIDDEN";
            case 19:
                return "FOREGROUND_SERVICE_START";
            case 20:
                return "FOREGROUND_SERVICE_STOP";
            case 21:
                return "CONTINUING_FOREGROUND_SERVICE";
            case 22:
                return "ROLLOVER_FOREGROUND_SERVICE";
            case 23:
                return "ACTIVITY_STOPPED";
            case 24:
            case 25:
            default:
                return "UNKNOWN_TYPE_" + i;
            case 26:
                return "DEVICE_SHUTDOWN";
            case 27:
                return "DEVICE_STARTUP";
            case 28:
                return "USER_UNLOCKED";
            case 29:
                return "USER_STOPPED";
            case 30:
                return "LOCUS_ID_SET";
            case 31:
                return "APP_COMPONENT_USED";
        }
    }

    byte[] getBackupPayload(java.lang.String str) {
        checkAndGetTimeLocked();
        persistActiveStats();
        return this.mDatabase.getBackupPayload(str);
    }

    java.util.Set<java.lang.String> applyRestoredPayload(java.lang.String str, byte[] bArr) {
        checkAndGetTimeLocked();
        return this.mDatabase.applyRestoredPayload(str, bArr);
    }
}
