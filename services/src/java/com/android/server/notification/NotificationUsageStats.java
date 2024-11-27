package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationUsageStats {
    private static final boolean DEBUG = false;
    private static final java.lang.String DEVICE_GLOBAL_STATS = "__global";
    private static final long EMIT_PERIOD = 14400000;
    private static final com.android.server.notification.NotificationUsageStats.AggregatedStats[] EMPTY_AGGREGATED_STATS = new com.android.server.notification.NotificationUsageStats.AggregatedStats[0];
    private static final boolean ENABLE_AGGREGATED_IN_MEMORY_STATS = true;
    public static final int FOUR_HOURS = 14400000;
    private static final int MSG_EMIT = 1;
    private static final java.lang.String TAG = "NotificationUsageStats";
    public static final int TEN_SECONDS = 10000;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Map<java.lang.String, com.android.server.notification.NotificationUsageStats.AggregatedStats> mStats = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.ArrayDeque<com.android.server.notification.NotificationUsageStats.AggregatedStats[]> mStatsArrays = new java.util.ArrayDeque<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.ArraySet<java.lang.String> mStatExpiredkeys = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastEmitTime = android.os.SystemClock.elapsedRealtime();

    public NotificationUsageStats(android.content.Context context) {
        this.mContext = context;
        this.mHandler = new android.os.Handler(this.mContext.getMainLooper()) { // from class: com.android.server.notification.NotificationUsageStats.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.notification.NotificationUsageStats.this.emit();
                        break;
                    default:
                        android.util.Log.wtf(com.android.server.notification.NotificationUsageStats.TAG, "Unknown message type: " + message.what);
                        break;
                }
            }
        };
        this.mHandler.sendEmptyMessageDelayed(1, 14400000L);
    }

    public synchronized float getAppEnqueueRate(java.lang.String str) {
        return getOrCreateAggregatedStatsLocked(str).getEnqueueRate(android.os.SystemClock.elapsedRealtime());
    }

    public synchronized boolean isAlertRateLimited(java.lang.String str) {
        return getOrCreateAggregatedStatsLocked(str).isAlertRateLimited();
    }

    public synchronized void registerEnqueuedByApp(java.lang.String str) {
        try {
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(str);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numEnqueuedByApp++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerEnqueuedByAppAndAccepted(java.lang.String str) {
        try {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(str);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.updateInterarrivalEstimate(elapsedRealtime);
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerPostedByApp(com.android.server.notification.NotificationRecord notificationRecord) {
        try {
            notificationRecord.stats.posttimeElapsedMs = android.os.SystemClock.elapsedRealtime();
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                int i = 1;
                aggregatedStats.numPostedByApp++;
                aggregatedStats.countApiUse(notificationRecord);
                int i2 = aggregatedStats.numUndecoratedRemoteViews;
                if (!notificationRecord.hasUndecoratedRemoteView()) {
                    i = 0;
                }
                aggregatedStats.numUndecoratedRemoteViews = i2 + i;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerUpdatedByApp(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2) {
        try {
            notificationRecord.stats.updateFrom(notificationRecord2.stats);
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numUpdatedByApp++;
                aggregatedStats.countApiUse(notificationRecord);
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerRemovedByApp(com.android.server.notification.NotificationRecord notificationRecord) {
        try {
            notificationRecord.stats.onRemoved();
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numRemovedByApp++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerDismissedByUser(com.android.server.notification.NotificationRecord notificationRecord) {
        com.android.internal.logging.MetricsLogger.histogram(this.mContext, "note_dismiss_longevity", ((int) (java.lang.System.currentTimeMillis() - notificationRecord.getRankingTimeMs())) / 60000);
        notificationRecord.stats.onDismiss();
    }

    public synchronized void registerClickedByUser(com.android.server.notification.NotificationRecord notificationRecord) {
        com.android.internal.logging.MetricsLogger.histogram(this.mContext, "note_click_longevity", ((int) (java.lang.System.currentTimeMillis() - notificationRecord.getRankingTimeMs())) / 60000);
        notificationRecord.stats.onClick();
    }

    public synchronized void registerPeopleAffinity(com.android.server.notification.NotificationRecord notificationRecord, boolean z, boolean z2, boolean z3) {
        try {
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                if (z) {
                    aggregatedStats.numWithValidPeople++;
                }
                if (z2) {
                    aggregatedStats.numWithStaredPeople++;
                }
                if (z3) {
                    aggregatedStats.numPeopleCacheHit++;
                } else {
                    aggregatedStats.numPeopleCacheMiss++;
                }
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerBlocked(com.android.server.notification.NotificationRecord notificationRecord) {
        try {
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numBlocked++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerSuspendedByAdmin(com.android.server.notification.NotificationRecord notificationRecord) {
        try {
            com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord);
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numSuspendedByAdmin++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void registerOverRateQuota(java.lang.String str) {
        for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numRateViolations++;
        }
    }

    public synchronized void registerOverCountQuota(java.lang.String str) {
        for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numQuotaViolations++;
        }
    }

    public synchronized void registerImageRemoved(java.lang.String str) {
        for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numImagesRemoved++;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.notification.NotificationUsageStats.AggregatedStats[] getAggregatedStatsLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        return getAggregatedStatsLocked(notificationRecord.getSbn().getPackageName());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.notification.NotificationUsageStats.AggregatedStats[] getAggregatedStatsLocked(java.lang.String str) {
        com.android.server.notification.NotificationUsageStats.AggregatedStats[] poll = this.mStatsArrays.poll();
        if (poll == null) {
            poll = new com.android.server.notification.NotificationUsageStats.AggregatedStats[2];
        }
        poll[0] = getOrCreateAggregatedStatsLocked(DEVICE_GLOBAL_STATS);
        poll[1] = getOrCreateAggregatedStatsLocked(str);
        return poll;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void releaseAggregatedStatsLocked(com.android.server.notification.NotificationUsageStats.AggregatedStats[] aggregatedStatsArr) {
        for (int i = 0; i < aggregatedStatsArr.length; i++) {
            aggregatedStatsArr[i] = null;
        }
        this.mStatsArrays.offer(aggregatedStatsArr);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.notification.NotificationUsageStats.AggregatedStats getOrCreateAggregatedStatsLocked(java.lang.String str) {
        com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats = this.mStats.get(str);
        if (aggregatedStats == null) {
            aggregatedStats = new com.android.server.notification.NotificationUsageStats.AggregatedStats(this.mContext, str);
            this.mStats.put(str, aggregatedStats);
        }
        aggregatedStats.mLastAccessTime = android.os.SystemClock.elapsedRealtime();
        return aggregatedStats;
    }

    public synchronized org.json.JSONObject dumpJson(com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        org.json.JSONObject jSONObject;
        try {
            jSONObject = new org.json.JSONObject();
            try {
                org.json.JSONArray jSONArray = new org.json.JSONArray();
                for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : this.mStats.values()) {
                    if (dumpFilter == null || dumpFilter.matches(aggregatedStats.key)) {
                        jSONArray.put(aggregatedStats.dumpJson());
                    }
                }
                jSONObject.put("current", jSONArray);
            } catch (org.json.JSONException e) {
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return jSONObject;
    }

    public com.android.server.notification.PulledStats remoteViewStats(long j, boolean z) {
        com.android.server.notification.PulledStats pulledStats = new com.android.server.notification.PulledStats(j);
        for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : this.mStats.values()) {
            if (aggregatedStats.numUndecoratedRemoteViews > 0) {
                pulledStats.addUndecoratedPackage(aggregatedStats.key, aggregatedStats.mCreated);
            }
        }
        return pulledStats;
    }

    public synchronized void dump(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        try {
            for (com.android.server.notification.NotificationUsageStats.AggregatedStats aggregatedStats : this.mStats.values()) {
                if (dumpFilter == null || dumpFilter.matches(aggregatedStats.key)) {
                    aggregatedStats.dump(printWriter, str);
                }
            }
            printWriter.println(str + "mStatsArrays.size(): " + this.mStatsArrays.size());
            printWriter.println(str + "mStats.size(): " + this.mStats.size());
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void emit() {
        try {
            getOrCreateAggregatedStatsLocked(DEVICE_GLOBAL_STATS).emit();
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 14400000L);
            for (java.lang.String str : this.mStats.keySet()) {
                if (this.mStats.get(str).mLastAccessTime < this.mLastEmitTime) {
                    this.mStatExpiredkeys.add(str);
                }
            }
            java.util.Iterator<java.lang.String> it = this.mStatExpiredkeys.iterator();
            while (it.hasNext()) {
                this.mStats.remove(it.next());
            }
            this.mStatExpiredkeys.clear();
            this.mLastEmitTime = android.os.SystemClock.elapsedRealtime();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private static class AggregatedStats {
        public com.android.server.notification.NotificationUsageStats.ImportanceHistogram finalImportance;
        public final java.lang.String key;
        private final android.content.Context mContext;
        public long mLastAccessTime;
        private com.android.server.notification.NotificationUsageStats.AggregatedStats mPrevious;
        public com.android.server.notification.NotificationUsageStats.ImportanceHistogram noisyImportance;
        public int numAlertViolations;
        public int numAutoCancel;
        public int numBlocked;
        public int numEnqueuedByApp;
        public int numForegroundService;
        public int numImagesRemoved;
        public int numInterrupt;
        public int numOngoing;
        public int numPeopleCacheHit;
        public int numPeopleCacheMiss;
        public int numPostedByApp;
        public int numPrivate;
        public int numQuotaViolations;
        public int numRateViolations;
        public int numRemovedByApp;
        public int numSecret;
        public int numSuspendedByAdmin;
        public int numUndecoratedRemoteViews;
        public int numUpdatedByApp;
        public int numUserInitiatedJob;
        public int numWithActions;
        public int numWithBigPicture;
        public int numWithBigText;
        public int numWithInbox;
        public int numWithInfoText;
        public int numWithLargeIcon;
        public int numWithMediaSession;
        public int numWithStaredPeople;
        public int numWithSubText;
        public int numWithText;
        public int numWithTitle;
        public int numWithValidPeople;
        public com.android.server.notification.NotificationUsageStats.ImportanceHistogram quietImportance;
        private final long mCreated = android.os.SystemClock.elapsedRealtime();
        public com.android.server.notification.RateEstimator enqueueRate = new com.android.server.notification.RateEstimator();
        public com.android.server.notification.AlertRateLimiter alertRate = new com.android.server.notification.AlertRateLimiter();

        public AggregatedStats(android.content.Context context, java.lang.String str) {
            this.key = str;
            this.mContext = context;
            this.noisyImportance = new com.android.server.notification.NotificationUsageStats.ImportanceHistogram(context, "note_imp_noisy_");
            this.quietImportance = new com.android.server.notification.NotificationUsageStats.ImportanceHistogram(context, "note_imp_quiet_");
            this.finalImportance = new com.android.server.notification.NotificationUsageStats.ImportanceHistogram(context, "note_importance_");
        }

        public com.android.server.notification.NotificationUsageStats.AggregatedStats getPrevious() {
            if (this.mPrevious == null) {
                this.mPrevious = new com.android.server.notification.NotificationUsageStats.AggregatedStats(this.mContext, this.key);
            }
            return this.mPrevious;
        }

        public void countApiUse(com.android.server.notification.NotificationRecord notificationRecord) {
            android.app.Notification notification = notificationRecord.getNotification();
            if (notification.actions != null) {
                this.numWithActions++;
            }
            if ((notification.flags & 64) != 0) {
                this.numForegroundService++;
            }
            if ((notification.flags & 32768) != 0) {
                this.numUserInitiatedJob++;
            }
            if ((notification.flags & 2) != 0) {
                this.numOngoing++;
            }
            if ((notification.flags & 16) != 0) {
                this.numAutoCancel++;
            }
            if ((notification.defaults & 1) != 0 || (notification.defaults & 2) != 0 || notification.sound != null || notification.vibrate != null) {
                this.numInterrupt++;
            }
            switch (notification.visibility) {
                case -1:
                    this.numSecret++;
                    break;
                case 0:
                    this.numPrivate++;
                    break;
            }
            if (notificationRecord.stats.isNoisy) {
                this.noisyImportance.increment(notificationRecord.stats.requestedImportance);
            } else {
                this.quietImportance.increment(notificationRecord.stats.requestedImportance);
            }
            this.finalImportance.increment(notificationRecord.getImportance());
            java.util.Set<java.lang.String> keySet = notification.extras.keySet();
            if (keySet.contains("android.bigText")) {
                this.numWithBigText++;
            }
            if (keySet.contains("android.picture")) {
                this.numWithBigPicture++;
            }
            if (keySet.contains("android.largeIcon")) {
                this.numWithLargeIcon++;
            }
            if (keySet.contains("android.textLines")) {
                this.numWithInbox++;
            }
            if (keySet.contains("android.mediaSession")) {
                this.numWithMediaSession++;
            }
            if (keySet.contains("android.title") && !android.text.TextUtils.isEmpty(notification.extras.getCharSequence("android.title"))) {
                this.numWithTitle++;
            }
            if (keySet.contains("android.text") && !android.text.TextUtils.isEmpty(notification.extras.getCharSequence("android.text"))) {
                this.numWithText++;
            }
            if (keySet.contains("android.subText") && !android.text.TextUtils.isEmpty(notification.extras.getCharSequence("android.subText"))) {
                this.numWithSubText++;
            }
            if (keySet.contains("android.infoText") && !android.text.TextUtils.isEmpty(notification.extras.getCharSequence("android.infoText"))) {
                this.numWithInfoText++;
            }
        }

        public void emit() {
            com.android.server.notification.NotificationUsageStats.AggregatedStats previous = getPrevious();
            maybeCount("note_enqueued", this.numEnqueuedByApp - previous.numEnqueuedByApp);
            maybeCount("note_post", this.numPostedByApp - previous.numPostedByApp);
            maybeCount("note_update", this.numUpdatedByApp - previous.numUpdatedByApp);
            maybeCount("note_remove", this.numRemovedByApp - previous.numRemovedByApp);
            maybeCount("note_with_people", this.numWithValidPeople - previous.numWithValidPeople);
            maybeCount("note_with_stars", this.numWithStaredPeople - previous.numWithStaredPeople);
            maybeCount("people_cache_hit", this.numPeopleCacheHit - previous.numPeopleCacheHit);
            maybeCount("people_cache_miss", this.numPeopleCacheMiss - previous.numPeopleCacheMiss);
            maybeCount("note_blocked", this.numBlocked - previous.numBlocked);
            maybeCount("note_suspended", this.numSuspendedByAdmin - previous.numSuspendedByAdmin);
            maybeCount("note_with_actions", this.numWithActions - previous.numWithActions);
            maybeCount("note_private", this.numPrivate - previous.numPrivate);
            maybeCount("note_secret", this.numSecret - previous.numSecret);
            maybeCount("note_interupt", this.numInterrupt - previous.numInterrupt);
            maybeCount("note_big_text", this.numWithBigText - previous.numWithBigText);
            maybeCount("note_big_pic", this.numWithBigPicture - previous.numWithBigPicture);
            maybeCount("note_fg", this.numForegroundService - previous.numForegroundService);
            maybeCount("note_uij", this.numUserInitiatedJob - previous.numUserInitiatedJob);
            maybeCount("note_ongoing", this.numOngoing - previous.numOngoing);
            maybeCount("note_auto", this.numAutoCancel - previous.numAutoCancel);
            maybeCount("note_large_icon", this.numWithLargeIcon - previous.numWithLargeIcon);
            maybeCount("note_inbox", this.numWithInbox - previous.numWithInbox);
            maybeCount("note_media", this.numWithMediaSession - previous.numWithMediaSession);
            maybeCount("note_title", this.numWithTitle - previous.numWithTitle);
            maybeCount("note_text", this.numWithText - previous.numWithText);
            maybeCount("note_sub_text", this.numWithSubText - previous.numWithSubText);
            maybeCount("note_info_text", this.numWithInfoText - previous.numWithInfoText);
            maybeCount("note_over_rate", this.numRateViolations - previous.numRateViolations);
            maybeCount("note_over_alert_rate", this.numAlertViolations - previous.numAlertViolations);
            maybeCount("note_over_quota", this.numQuotaViolations - previous.numQuotaViolations);
            maybeCount("note_images_removed", this.numImagesRemoved - previous.numImagesRemoved);
            this.noisyImportance.maybeCount(previous.noisyImportance);
            this.quietImportance.maybeCount(previous.quietImportance);
            this.finalImportance.maybeCount(previous.finalImportance);
            previous.numEnqueuedByApp = this.numEnqueuedByApp;
            previous.numPostedByApp = this.numPostedByApp;
            previous.numUpdatedByApp = this.numUpdatedByApp;
            previous.numRemovedByApp = this.numRemovedByApp;
            previous.numPeopleCacheHit = this.numPeopleCacheHit;
            previous.numPeopleCacheMiss = this.numPeopleCacheMiss;
            previous.numWithStaredPeople = this.numWithStaredPeople;
            previous.numWithValidPeople = this.numWithValidPeople;
            previous.numBlocked = this.numBlocked;
            previous.numSuspendedByAdmin = this.numSuspendedByAdmin;
            previous.numWithActions = this.numWithActions;
            previous.numPrivate = this.numPrivate;
            previous.numSecret = this.numSecret;
            previous.numInterrupt = this.numInterrupt;
            previous.numWithBigText = this.numWithBigText;
            previous.numWithBigPicture = this.numWithBigPicture;
            previous.numForegroundService = this.numForegroundService;
            previous.numUserInitiatedJob = this.numUserInitiatedJob;
            previous.numOngoing = this.numOngoing;
            previous.numAutoCancel = this.numAutoCancel;
            previous.numWithLargeIcon = this.numWithLargeIcon;
            previous.numWithInbox = this.numWithInbox;
            previous.numWithMediaSession = this.numWithMediaSession;
            previous.numWithTitle = this.numWithTitle;
            previous.numWithText = this.numWithText;
            previous.numWithSubText = this.numWithSubText;
            previous.numWithInfoText = this.numWithInfoText;
            previous.numRateViolations = this.numRateViolations;
            previous.numAlertViolations = this.numAlertViolations;
            previous.numQuotaViolations = this.numQuotaViolations;
            previous.numImagesRemoved = this.numImagesRemoved;
            this.noisyImportance.update(previous.noisyImportance);
            this.quietImportance.update(previous.quietImportance);
            this.finalImportance.update(previous.finalImportance);
        }

        void maybeCount(java.lang.String str, int i) {
            if (i > 0) {
                com.android.internal.logging.MetricsLogger.count(this.mContext, str, i);
            }
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(toStringWithIndent(str));
        }

        public java.lang.String toString() {
            return toStringWithIndent("");
        }

        public float getEnqueueRate() {
            return getEnqueueRate(android.os.SystemClock.elapsedRealtime());
        }

        public float getEnqueueRate(long j) {
            return this.enqueueRate.getRate(j);
        }

        public void updateInterarrivalEstimate(long j) {
            this.enqueueRate.update(j);
        }

        public boolean isAlertRateLimited() {
            boolean shouldRateLimitAlert = this.alertRate.shouldRateLimitAlert(android.os.SystemClock.elapsedRealtime());
            if (shouldRateLimitAlert) {
                this.numAlertViolations++;
            }
            return shouldRateLimitAlert;
        }

        private java.lang.String toStringWithIndent(java.lang.String str) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("AggregatedStats{\n");
            java.lang.String str2 = str + "  ";
            sb.append(str2);
            sb.append("key='");
            sb.append(this.key);
            sb.append("',\n");
            sb.append(str2);
            sb.append("numEnqueuedByApp=");
            sb.append(this.numEnqueuedByApp);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numPostedByApp=");
            sb.append(this.numPostedByApp);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numUpdatedByApp=");
            sb.append(this.numUpdatedByApp);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numRemovedByApp=");
            sb.append(this.numRemovedByApp);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numPeopleCacheHit=");
            sb.append(this.numPeopleCacheHit);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numWithStaredPeople=");
            sb.append(this.numWithStaredPeople);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numWithValidPeople=");
            sb.append(this.numWithValidPeople);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numPeopleCacheMiss=");
            sb.append(this.numPeopleCacheMiss);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numBlocked=");
            sb.append(this.numBlocked);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numSuspendedByAdmin=");
            sb.append(this.numSuspendedByAdmin);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numWithActions=");
            sb.append(this.numWithActions);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numPrivate=");
            sb.append(this.numPrivate);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numSecret=");
            sb.append(this.numSecret);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numInterrupt=");
            sb.append(this.numInterrupt);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numWithBigText=");
            sb.append(this.numWithBigText);
            sb.append(",\n");
            sb.append(str2);
            sb.append("numWithBigPicture=");
            sb.append(this.numWithBigPicture);
            sb.append("\n");
            sb.append(str2);
            sb.append("numForegroundService=");
            sb.append(this.numForegroundService);
            sb.append("\n");
            sb.append(str2);
            sb.append("numUserInitiatedJob=");
            sb.append(this.numUserInitiatedJob);
            sb.append("\n");
            sb.append(str2);
            sb.append("numOngoing=");
            sb.append(this.numOngoing);
            sb.append("\n");
            sb.append(str2);
            sb.append("numAutoCancel=");
            sb.append(this.numAutoCancel);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithLargeIcon=");
            sb.append(this.numWithLargeIcon);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithInbox=");
            sb.append(this.numWithInbox);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithMediaSession=");
            sb.append(this.numWithMediaSession);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithTitle=");
            sb.append(this.numWithTitle);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithText=");
            sb.append(this.numWithText);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithSubText=");
            sb.append(this.numWithSubText);
            sb.append("\n");
            sb.append(str2);
            sb.append("numWithInfoText=");
            sb.append(this.numWithInfoText);
            sb.append("\n");
            sb.append(str2);
            sb.append("numRateViolations=");
            sb.append(this.numRateViolations);
            sb.append("\n");
            sb.append(str2);
            sb.append("numAlertViolations=");
            sb.append(this.numAlertViolations);
            sb.append("\n");
            sb.append(str2);
            sb.append("numQuotaViolations=");
            sb.append(this.numQuotaViolations);
            sb.append("\n");
            sb.append(str2);
            sb.append("numImagesRemoved=");
            sb.append(this.numImagesRemoved);
            sb.append("\n");
            sb.append(str2);
            sb.append(this.noisyImportance.toString());
            sb.append("\n");
            sb.append(str2);
            sb.append(this.quietImportance.toString());
            sb.append("\n");
            sb.append(str2);
            sb.append(this.finalImportance.toString());
            sb.append("\n");
            sb.append(str2);
            sb.append("numUndecorateRVs=");
            sb.append(this.numUndecoratedRemoteViews);
            sb.append("\n");
            sb.append(str);
            sb.append("}");
            return sb.toString();
        }

        public org.json.JSONObject dumpJson() throws org.json.JSONException {
            com.android.server.notification.NotificationUsageStats.AggregatedStats previous = getPrevious();
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            jSONObject.put("key", this.key);
            jSONObject.put("duration", android.os.SystemClock.elapsedRealtime() - this.mCreated);
            maybePut(jSONObject, "numEnqueuedByApp", this.numEnqueuedByApp);
            maybePut(jSONObject, "numPostedByApp", this.numPostedByApp);
            maybePut(jSONObject, "numUpdatedByApp", this.numUpdatedByApp);
            maybePut(jSONObject, "numRemovedByApp", this.numRemovedByApp);
            maybePut(jSONObject, "numPeopleCacheHit", this.numPeopleCacheHit);
            maybePut(jSONObject, "numPeopleCacheMiss", this.numPeopleCacheMiss);
            maybePut(jSONObject, "numWithStaredPeople", this.numWithStaredPeople);
            maybePut(jSONObject, "numWithValidPeople", this.numWithValidPeople);
            maybePut(jSONObject, "numBlocked", this.numBlocked);
            maybePut(jSONObject, "numSuspendedByAdmin", this.numSuspendedByAdmin);
            maybePut(jSONObject, "numWithActions", this.numWithActions);
            maybePut(jSONObject, "numPrivate", this.numPrivate);
            maybePut(jSONObject, "numSecret", this.numSecret);
            maybePut(jSONObject, "numInterrupt", this.numInterrupt);
            maybePut(jSONObject, "numWithBigText", this.numWithBigText);
            maybePut(jSONObject, "numWithBigPicture", this.numWithBigPicture);
            maybePut(jSONObject, "numForegroundService", this.numForegroundService);
            maybePut(jSONObject, "numUserInitiatedJob", this.numUserInitiatedJob);
            maybePut(jSONObject, "numOngoing", this.numOngoing);
            maybePut(jSONObject, "numAutoCancel", this.numAutoCancel);
            maybePut(jSONObject, "numWithLargeIcon", this.numWithLargeIcon);
            maybePut(jSONObject, "numWithInbox", this.numWithInbox);
            maybePut(jSONObject, "numWithMediaSession", this.numWithMediaSession);
            maybePut(jSONObject, "numWithTitle", this.numWithTitle);
            maybePut(jSONObject, "numWithText", this.numWithText);
            maybePut(jSONObject, "numWithSubText", this.numWithSubText);
            maybePut(jSONObject, "numWithInfoText", this.numWithInfoText);
            maybePut(jSONObject, "numRateViolations", this.numRateViolations);
            maybePut(jSONObject, "numQuotaLViolations", this.numQuotaViolations);
            maybePut(jSONObject, "notificationEnqueueRate", getEnqueueRate());
            maybePut(jSONObject, "numAlertViolations", this.numAlertViolations);
            maybePut(jSONObject, "numImagesRemoved", this.numImagesRemoved);
            this.noisyImportance.maybePut(jSONObject, previous.noisyImportance);
            this.quietImportance.maybePut(jSONObject, previous.quietImportance);
            this.finalImportance.maybePut(jSONObject, previous.finalImportance);
            return jSONObject;
        }

        private void maybePut(org.json.JSONObject jSONObject, java.lang.String str, int i) throws org.json.JSONException {
            if (i > 0) {
                jSONObject.put(str, i);
            }
        }

        private void maybePut(org.json.JSONObject jSONObject, java.lang.String str, float f) throws org.json.JSONException {
            double d = f;
            if (d > 0.0d) {
                jSONObject.put(str, d);
            }
        }
    }

    private static class ImportanceHistogram {
        private static final java.lang.String[] IMPORTANCE_NAMES = {"none", "min", "low", "default", "high", "max"};
        private static final int NUM_IMPORTANCES = 6;
        private final android.content.Context mContext;
        private int[] mCount = new int[6];
        private final java.lang.String[] mCounterNames = new java.lang.String[6];
        private final java.lang.String mPrefix;

        ImportanceHistogram(android.content.Context context, java.lang.String str) {
            this.mContext = context;
            this.mPrefix = str;
            for (int i = 0; i < 6; i++) {
                this.mCounterNames[i] = this.mPrefix + IMPORTANCE_NAMES[i];
            }
        }

        void increment(int i) {
            int max = java.lang.Math.max(0, java.lang.Math.min(i, this.mCount.length - 1));
            int[] iArr = this.mCount;
            iArr[max] = iArr[max] + 1;
        }

        void maybeCount(com.android.server.notification.NotificationUsageStats.ImportanceHistogram importanceHistogram) {
            for (int i = 0; i < 6; i++) {
                int i2 = this.mCount[i] - importanceHistogram.mCount[i];
                if (i2 > 0) {
                    com.android.internal.logging.MetricsLogger.count(this.mContext, this.mCounterNames[i], i2);
                }
            }
        }

        void update(com.android.server.notification.NotificationUsageStats.ImportanceHistogram importanceHistogram) {
            for (int i = 0; i < 6; i++) {
                this.mCount[i] = importanceHistogram.mCount[i];
            }
        }

        public void maybePut(org.json.JSONObject jSONObject, com.android.server.notification.NotificationUsageStats.ImportanceHistogram importanceHistogram) throws org.json.JSONException {
            jSONObject.put(this.mPrefix, new org.json.JSONArray(this.mCount));
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mPrefix);
            sb.append(": [");
            for (int i = 0; i < 6; i++) {
                sb.append(this.mCount[i]);
                if (i < 5) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static class SingleNotificationStats {
        public boolean isNoisy;
        public int naturalImportance;
        public int requestedImportance;
        private boolean isVisible = false;
        private boolean isExpanded = false;
        public long posttimeElapsedMs = -1;
        public long posttimeToFirstClickMs = -1;
        public long posttimeToDismissMs = -1;
        public long airtimeCount = 0;
        public long posttimeToFirstAirtimeMs = -1;
        public long currentAirtimeStartElapsedMs = -1;
        public long airtimeMs = 0;
        public long posttimeToFirstVisibleExpansionMs = -1;
        public long currentAirtimeExpandedStartElapsedMs = -1;
        public long airtimeExpandedMs = 0;
        public long userExpansionCount = 0;

        public long getCurrentPosttimeMs() {
            if (this.posttimeElapsedMs < 0) {
                return 0L;
            }
            return android.os.SystemClock.elapsedRealtime() - this.posttimeElapsedMs;
        }

        public long getCurrentAirtimeMs() {
            long j = this.airtimeMs;
            if (this.currentAirtimeStartElapsedMs >= 0) {
                return j + (android.os.SystemClock.elapsedRealtime() - this.currentAirtimeStartElapsedMs);
            }
            return j;
        }

        public long getCurrentAirtimeExpandedMs() {
            long j = this.airtimeExpandedMs;
            if (this.currentAirtimeExpandedStartElapsedMs >= 0) {
                return j + (android.os.SystemClock.elapsedRealtime() - this.currentAirtimeExpandedStartElapsedMs);
            }
            return j;
        }

        public void onClick() {
            if (this.posttimeToFirstClickMs < 0) {
                this.posttimeToFirstClickMs = android.os.SystemClock.elapsedRealtime() - this.posttimeElapsedMs;
            }
        }

        public void onDismiss() {
            if (this.posttimeToDismissMs < 0) {
                this.posttimeToDismissMs = android.os.SystemClock.elapsedRealtime() - this.posttimeElapsedMs;
            }
            finish();
        }

        public void onCancel() {
            finish();
        }

        public void onRemoved() {
            finish();
        }

        public void onVisibilityChanged(boolean z) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            boolean z2 = this.isVisible;
            this.isVisible = z;
            if (z) {
                if (this.currentAirtimeStartElapsedMs < 0) {
                    this.airtimeCount++;
                    this.currentAirtimeStartElapsedMs = elapsedRealtime;
                }
                if (this.posttimeToFirstAirtimeMs < 0) {
                    this.posttimeToFirstAirtimeMs = elapsedRealtime - this.posttimeElapsedMs;
                }
            } else if (this.currentAirtimeStartElapsedMs >= 0) {
                this.airtimeMs += elapsedRealtime - this.currentAirtimeStartElapsedMs;
                this.currentAirtimeStartElapsedMs = -1L;
            }
            if (z2 != this.isVisible) {
                updateVisiblyExpandedStats();
            }
        }

        public void onExpansionChanged(boolean z, boolean z2) {
            this.isExpanded = z2;
            if (this.isExpanded && z) {
                this.userExpansionCount++;
            }
            updateVisiblyExpandedStats();
        }

        public boolean hasBeenVisiblyExpanded() {
            return this.posttimeToFirstVisibleExpansionMs >= 0;
        }

        private void updateVisiblyExpandedStats() {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            if (!this.isExpanded || !this.isVisible) {
                if (this.currentAirtimeExpandedStartElapsedMs >= 0) {
                    this.airtimeExpandedMs += elapsedRealtime - this.currentAirtimeExpandedStartElapsedMs;
                    this.currentAirtimeExpandedStartElapsedMs = -1L;
                    return;
                }
                return;
            }
            if (this.currentAirtimeExpandedStartElapsedMs < 0) {
                this.currentAirtimeExpandedStartElapsedMs = elapsedRealtime;
            }
            if (this.posttimeToFirstVisibleExpansionMs < 0) {
                this.posttimeToFirstVisibleExpansionMs = elapsedRealtime - this.posttimeElapsedMs;
            }
        }

        public void finish() {
            onVisibilityChanged(false);
        }

        public java.lang.String toString() {
            return "SingleNotificationStats{posttimeElapsedMs=" + this.posttimeElapsedMs + ", posttimeToFirstClickMs=" + this.posttimeToFirstClickMs + ", posttimeToDismissMs=" + this.posttimeToDismissMs + ", airtimeCount=" + this.airtimeCount + ", airtimeMs=" + this.airtimeMs + ", currentAirtimeStartElapsedMs=" + this.currentAirtimeStartElapsedMs + ", airtimeExpandedMs=" + this.airtimeExpandedMs + ", posttimeToFirstVisibleExpansionMs=" + this.posttimeToFirstVisibleExpansionMs + ", currentAirtimeExpandedStartElapsedMs=" + this.currentAirtimeExpandedStartElapsedMs + ", requestedImportance=" + this.requestedImportance + ", naturalImportance=" + this.naturalImportance + ", isNoisy=" + this.isNoisy + '}';
        }

        public void updateFrom(com.android.server.notification.NotificationUsageStats.SingleNotificationStats singleNotificationStats) {
            this.posttimeElapsedMs = singleNotificationStats.posttimeElapsedMs;
            this.posttimeToFirstClickMs = singleNotificationStats.posttimeToFirstClickMs;
            this.airtimeCount = singleNotificationStats.airtimeCount;
            this.posttimeToFirstAirtimeMs = singleNotificationStats.posttimeToFirstAirtimeMs;
            this.currentAirtimeStartElapsedMs = singleNotificationStats.currentAirtimeStartElapsedMs;
            this.airtimeMs = singleNotificationStats.airtimeMs;
            this.posttimeToFirstVisibleExpansionMs = singleNotificationStats.posttimeToFirstVisibleExpansionMs;
            this.currentAirtimeExpandedStartElapsedMs = singleNotificationStats.currentAirtimeExpandedStartElapsedMs;
            this.airtimeExpandedMs = singleNotificationStats.airtimeExpandedMs;
            this.userExpansionCount = singleNotificationStats.userExpansionCount;
        }
    }

    public static class Aggregate {
        double avg;
        long numSamples;
        double sum2;
        double var;

        public void addSample(long j) {
            this.numSamples++;
            double d = this.numSamples;
            double d2 = j - this.avg;
            this.avg += (1.0d / d) * d2;
            double d3 = d - 1.0d;
            this.sum2 += (d3 / d) * d2 * d2;
            this.var = this.sum2 / (this.numSamples != 1 ? d3 : 1.0d);
        }

        public java.lang.String toString() {
            return "Aggregate{numSamples=" + this.numSamples + ", avg=" + this.avg + ", var=" + this.var + '}';
        }
    }
}
