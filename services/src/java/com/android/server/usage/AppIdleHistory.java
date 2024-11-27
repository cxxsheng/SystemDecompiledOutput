package com.android.server.usage;

/* loaded from: classes2.dex */
public class AppIdleHistory {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String APP_IDLE_FILENAME = "app_idle_stats.xml";
    private static final java.lang.String ATTR_BUCKET = "bucket";
    private static final java.lang.String ATTR_BUCKETING_REASON = "bucketReason";
    private static final java.lang.String ATTR_BUCKET_ACTIVE_TIMEOUT_TIME = "activeTimeoutTime";
    private static final java.lang.String ATTR_BUCKET_WORKING_SET_TIMEOUT_TIME = "workingSetTimeoutTime";
    private static final java.lang.String ATTR_CURRENT_BUCKET = "appLimitBucket";
    private static final java.lang.String ATTR_ELAPSED_IDLE = "elapsedIdleTime";
    private static final java.lang.String ATTR_EXPIRY_TIME = "expiry";
    private static final java.lang.String ATTR_LAST_PREDICTED_TIME = "lastPredictedTime";
    private static final java.lang.String ATTR_LAST_RESTRICTION_ATTEMPT_ELAPSED = "lastRestrictionAttemptElapsedTime";
    private static final java.lang.String ATTR_LAST_RESTRICTION_ATTEMPT_REASON = "lastRestrictionAttemptReason";
    private static final java.lang.String ATTR_LAST_RUN_JOB_TIME = "lastJobRunTime";
    private static final java.lang.String ATTR_LAST_USED_BY_USER_ELAPSED = "lastUsedByUserElapsedTime";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_NEXT_ESTIMATED_APP_LAUNCH_TIME = "nextEstimatedAppLaunchTime";
    private static final java.lang.String ATTR_SCREEN_IDLE = "screenIdleTime";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final boolean DEBUG = false;
    static final int IDLE_BUCKET_CUTOFF = 40;
    private static final long ONE_MINUTE = 60000;
    static final int STANDBY_BUCKET_UNKNOWN = -1;
    private static final java.lang.String TAG = "AppIdleHistory";
    private static final java.lang.String TAG_BUCKET_EXPIRY_TIMES = "expiryTimes";
    private static final java.lang.String TAG_ITEM = "item";
    private static final java.lang.String TAG_PACKAGE = "package";
    private static final java.lang.String TAG_PACKAGES = "packages";
    private static final int XML_VERSION_ADD_BUCKET_EXPIRY_TIMES = 1;
    private static final int XML_VERSION_CURRENT = 1;
    private static final int XML_VERSION_INITIAL = 0;
    private long mElapsedDuration;
    private long mElapsedSnapshot;
    private android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory>> mIdleHistory = new android.util.SparseArray<>();
    private boolean mScreenOn;
    private long mScreenOnDuration;
    private long mScreenOnSnapshot;
    private final java.io.File mStorageDir;

    static class AppUsageHistory {
        android.util.SparseLongArray bucketExpiryTimesMs;
        int bucketingReason;
        int currentBucket;
        int lastInformedBucket;
        long lastJobRunTime;
        int lastPredictedBucket = -1;
        long lastPredictedTime;
        long lastRestrictAttemptElapsedTime;
        int lastRestrictReason;
        long lastUsedByUserElapsedTime;
        long lastUsedElapsedTime;
        long lastUsedScreenTime;
        long nextEstimatedLaunchTime;

        AppUsageHistory() {
        }
    }

    AppIdleHistory(java.io.File file, long j) {
        this.mElapsedSnapshot = j;
        this.mScreenOnSnapshot = j;
        this.mStorageDir = file;
        readScreenOnTime();
    }

    public void updateDisplay(boolean z, long j) {
        if (z == this.mScreenOn) {
            return;
        }
        this.mScreenOn = z;
        if (this.mScreenOn) {
            this.mScreenOnSnapshot = j;
            return;
        }
        this.mScreenOnDuration += j - this.mScreenOnSnapshot;
        this.mElapsedDuration += j - this.mElapsedSnapshot;
        this.mElapsedSnapshot = j;
    }

    public long getScreenOnTime(long j) {
        long j2 = this.mScreenOnDuration;
        if (this.mScreenOn) {
            return j2 + (j - this.mScreenOnSnapshot);
        }
        return j2;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getScreenOnTimeFile() {
        return new java.io.File(this.mStorageDir, "screen_on_time");
    }

    private void readScreenOnTime() {
        java.io.File screenOnTimeFile = getScreenOnTimeFile();
        if (screenOnTimeFile.exists()) {
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(screenOnTimeFile));
                this.mScreenOnDuration = java.lang.Long.parseLong(bufferedReader.readLine());
                this.mElapsedDuration = java.lang.Long.parseLong(bufferedReader.readLine());
                bufferedReader.close();
                return;
            } catch (java.io.IOException | java.lang.NumberFormatException e) {
                return;
            }
        }
        writeScreenOnTime();
    }

    private void writeScreenOnTime() {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(getScreenOnTimeFile());
        try {
            fileOutputStream = atomicFile.startWrite();
            try {
                fileOutputStream.write((java.lang.Long.toString(this.mScreenOnDuration) + "\n" + java.lang.Long.toString(this.mElapsedDuration) + "\n").getBytes());
                atomicFile.finishWrite(fileOutputStream);
            } catch (java.io.IOException e) {
                atomicFile.failWrite(fileOutputStream);
            }
        } catch (java.io.IOException e2) {
            fileOutputStream = null;
        }
    }

    public void writeAppIdleDurations() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        this.mElapsedDuration += elapsedRealtime - this.mElapsedSnapshot;
        this.mElapsedSnapshot = elapsedRealtime;
        writeScreenOnTime();
    }

    com.android.server.usage.AppIdleHistory.AppUsageHistory reportUsage(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, java.lang.String str, int i, int i2, int i3, long j, long j2) {
        int i4 = i3 | 768;
        boolean isUserUsage = com.android.server.usage.AppStandbyController.isUserUsage(i4);
        if (appUsageHistory.currentBucket == 45 && !isUserUsage && (appUsageHistory.bucketingReason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) != 512) {
            i4 = appUsageHistory.bucketingReason;
            i2 = 45;
        } else if (j2 > j) {
            long elapsedTime = getElapsedTime(j2);
            if (appUsageHistory.bucketExpiryTimesMs == null) {
                appUsageHistory.bucketExpiryTimesMs = new android.util.SparseLongArray();
            }
            appUsageHistory.bucketExpiryTimesMs.put(i2, java.lang.Math.max(elapsedTime, appUsageHistory.bucketExpiryTimesMs.get(i2)));
            removeElapsedExpiryTimes(appUsageHistory, getElapsedTime(j));
        }
        if (j != 0) {
            appUsageHistory.lastUsedElapsedTime = this.mElapsedDuration + (j - this.mElapsedSnapshot);
            if (isUserUsage) {
                appUsageHistory.lastUsedByUserElapsedTime = appUsageHistory.lastUsedElapsedTime;
            }
            appUsageHistory.lastUsedScreenTime = getScreenOnTime(j);
        }
        if (appUsageHistory.currentBucket >= i2) {
            if (appUsageHistory.currentBucket > i2) {
                appUsageHistory.currentBucket = i2;
                logAppStandbyBucketChanged(str, i, i2, i4);
            }
            appUsageHistory.bucketingReason = i4;
        }
        return appUsageHistory;
    }

    private void removeElapsedExpiryTimes(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, long j) {
        if (appUsageHistory.bucketExpiryTimesMs == null) {
            return;
        }
        for (int size = appUsageHistory.bucketExpiryTimesMs.size() - 1; size >= 0; size--) {
            if (appUsageHistory.bucketExpiryTimesMs.valueAt(size) < j) {
                appUsageHistory.bucketExpiryTimesMs.removeAt(size);
            }
        }
    }

    public com.android.server.usage.AppIdleHistory.AppUsageHistory reportUsage(java.lang.String str, int i, int i2, int i3, long j, long j2) {
        return reportUsage(getPackageHistory(getUserHistory(i), str, j, true), str, i, i2, i3, j, j2);
    }

    private android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> getUserHistory(int i) {
        android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap = this.mIdleHistory.get(i);
        if (arrayMap == null) {
            android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap2 = new android.util.ArrayMap<>();
            this.mIdleHistory.put(i, arrayMap2);
            readAppIdleTimes(i, arrayMap2);
            return arrayMap2;
        }
        return arrayMap;
    }

    private com.android.server.usage.AppIdleHistory.AppUsageHistory getPackageHistory(android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap, java.lang.String str, long j, boolean z) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = arrayMap.get(str);
        if (appUsageHistory == null && z) {
            com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory2 = new com.android.server.usage.AppIdleHistory.AppUsageHistory();
            appUsageHistory2.lastUsedByUserElapsedTime = -2147483648L;
            appUsageHistory2.lastUsedElapsedTime = -2147483648L;
            appUsageHistory2.lastUsedScreenTime = -2147483648L;
            appUsageHistory2.lastPredictedTime = -2147483648L;
            appUsageHistory2.currentBucket = 50;
            appUsageHistory2.bucketingReason = 256;
            appUsageHistory2.lastInformedBucket = -1;
            appUsageHistory2.lastJobRunTime = Long.MIN_VALUE;
            arrayMap.put(str, appUsageHistory2);
            return appUsageHistory2;
        }
        return appUsageHistory;
    }

    public void onUserRemoved(int i) {
        this.mIdleHistory.remove(i);
    }

    public boolean isIdle(java.lang.String str, int i, long j) {
        return getPackageHistory(getUserHistory(i), str, j, true).currentBucket >= 40;
    }

    public com.android.server.usage.AppIdleHistory.AppUsageHistory getAppUsageHistory(java.lang.String str, int i, long j) {
        return getPackageHistory(getUserHistory(i), str, j, true);
    }

    public void setAppStandbyBucket(java.lang.String str, int i, long j, int i2, int i3) {
        setAppStandbyBucket(str, i, j, i2, i3, false);
    }

    public void setAppStandbyBucket(java.lang.String str, int i, long j, int i2, int i3, boolean z) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, true);
        boolean z2 = packageHistory.currentBucket != i2;
        packageHistory.currentBucket = i2;
        packageHistory.bucketingReason = i3;
        long elapsedTime = getElapsedTime(j);
        if ((65280 & i3) == 1280) {
            packageHistory.lastPredictedTime = elapsedTime;
            packageHistory.lastPredictedBucket = i2;
        }
        if (z && packageHistory.bucketExpiryTimesMs != null) {
            packageHistory.bucketExpiryTimesMs.clear();
        }
        if (z2) {
            logAppStandbyBucketChanged(str, i, i2, i3);
        }
    }

    public void updateLastPrediction(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, long j, int i) {
        appUsageHistory.lastPredictedTime = j;
        appUsageHistory.lastPredictedBucket = i;
    }

    public void setEstimatedLaunchTime(java.lang.String str, int i, long j, long j2) {
        getPackageHistory(getUserHistory(i), str, j, true).nextEstimatedLaunchTime = j2;
    }

    public void setLastJobRunTime(java.lang.String str, int i, long j) {
        getPackageHistory(getUserHistory(i), str, j, true).lastJobRunTime = getElapsedTime(j);
    }

    void noteRestrictionAttempt(java.lang.String str, int i, long j, int i2) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, true);
        packageHistory.lastRestrictAttemptElapsedTime = getElapsedTime(j);
        packageHistory.lastRestrictReason = i2;
    }

    public long getEstimatedLaunchTime(java.lang.String str, int i, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null || packageHistory.nextEstimatedLaunchTime < java.lang.System.currentTimeMillis()) {
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return packageHistory.nextEstimatedLaunchTime;
    }

    public long getTimeSinceLastJobRun(java.lang.String str, int i, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null || packageHistory.lastJobRunTime == Long.MIN_VALUE) {
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return getElapsedTime(j) - packageHistory.lastJobRunTime;
    }

    public long getTimeSinceLastUsedByUser(java.lang.String str, int i, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null || packageHistory.lastUsedByUserElapsedTime == Long.MIN_VALUE || packageHistory.lastUsedByUserElapsedTime <= 0) {
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return getElapsedTime(j) - packageHistory.lastUsedByUserElapsedTime;
    }

    public int getAppStandbyBucket(java.lang.String str, int i, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null) {
            return 50;
        }
        return packageHistory.currentBucket;
    }

    public java.util.ArrayList<android.app.usage.AppStandbyInfo> getAppStandbyBuckets(int i, boolean z) {
        android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> userHistory = getUserHistory(i);
        int size = userHistory.size();
        java.util.ArrayList<android.app.usage.AppStandbyInfo> arrayList = new java.util.ArrayList<>(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(new android.app.usage.AppStandbyInfo(userHistory.keyAt(i2), z ? userHistory.valueAt(i2).currentBucket : 10));
        }
        return arrayList;
    }

    public int getAppStandbyReason(java.lang.String str, int i, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory != null) {
            return packageHistory.bucketingReason;
        }
        return 0;
    }

    public long getElapsedTime(long j) {
        return (j - this.mElapsedSnapshot) + this.mElapsedDuration;
    }

    public int setIdle(java.lang.String str, int i, boolean z, long j) {
        int i2;
        int i3;
        if (z) {
            com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = getAppUsageHistory(str, i, j);
            i2 = 40;
            if (appUsageHistory.bucketExpiryTimesMs != null) {
                for (int size = appUsageHistory.bucketExpiryTimesMs.size() - 1; size >= 0; size--) {
                    if (appUsageHistory.bucketExpiryTimesMs.keyAt(size) < 40) {
                        appUsageHistory.bucketExpiryTimesMs.removeAt(size);
                    }
                }
            }
            i3 = 1024;
        } else {
            i2 = 10;
            i3 = 771;
        }
        setAppStandbyBucket(str, i, j, i2, i3, false);
        return i2;
    }

    public void clearUsage(java.lang.String str, int i) {
        getUserHistory(i).remove(str);
    }

    boolean shouldInformListeners(java.lang.String str, int i, long j, int i2) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, true);
        if (packageHistory.lastInformedBucket != i2) {
            packageHistory.lastInformedBucket = i2;
            return true;
        }
        return false;
    }

    int getThresholdIndex(java.lang.String str, int i, long j, long[] jArr, long[] jArr2) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null || packageHistory.lastUsedElapsedTime < 0 || packageHistory.lastUsedScreenTime < 0) {
            return -1;
        }
        long screenOnTime = getScreenOnTime(j) - packageHistory.lastUsedScreenTime;
        long elapsedTime = getElapsedTime(j) - packageHistory.lastUsedElapsedTime;
        for (int length = jArr.length - 1; length >= 0; length--) {
            if (screenOnTime >= jArr[length] && elapsedTime >= jArr2[length]) {
                return length;
            }
        }
        return 0;
    }

    private void logAppStandbyBucketChanged(java.lang.String str, int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(258, str, i, i2, i3 & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK, i3 & 255);
    }

    @com.android.internal.annotations.VisibleForTesting
    long getBucketExpiryTimeMs(java.lang.String str, int i, int i2, long j) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, j, false);
        if (packageHistory == null || packageHistory.bucketExpiryTimesMs == null) {
            return 0L;
        }
        return packageHistory.bucketExpiryTimesMs.get(i2, 0L);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getUserFile(int i) {
        return new java.io.File(new java.io.File(new java.io.File(this.mStorageDir, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS), java.lang.Integer.toString(i)), APP_IDLE_FILENAME);
    }

    void clearLastUsedTimestamps(java.lang.String str, int i) {
        com.android.server.usage.AppIdleHistory.AppUsageHistory packageHistory = getPackageHistory(getUserHistory(i), str, android.os.SystemClock.elapsedRealtime(), false);
        if (packageHistory != null) {
            packageHistory.lastUsedByUserElapsedTime = -2147483648L;
            packageHistory.lastUsedElapsedTime = -2147483648L;
            packageHistory.lastUsedScreenTime = -2147483648L;
        }
    }

    public boolean userFileExists(int i) {
        return getUserFile(i).exists();
    }

    private void readAppIdleTimes(int i, android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap) {
        java.io.FileInputStream fileInputStream;
        int next;
        int i2;
        int i3;
        java.io.FileInputStream fileInputStream2 = null;
        java.lang.String str = null;
        try {
            try {
                java.io.FileInputStream openRead = new android.util.AtomicFile(getUserFile(i)).openRead();
                try {
                    try {
                        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                        newPullParser.setInput(openRead, java.nio.charset.StandardCharsets.UTF_8.name());
                        do {
                            next = newPullParser.next();
                            i2 = 1;
                            i3 = 2;
                            if (next == 2) {
                                break;
                            }
                        } while (next != 1);
                        if (next != 2) {
                            try {
                                android.util.Slog.e(TAG, "Unable to read app idle file for user " + i);
                                libcore.io.IoUtils.closeQuietly(openRead);
                                return;
                            } catch (java.io.FileNotFoundException e) {
                                fileInputStream2 = openRead;
                                android.util.Slog.d(TAG, "App idle file for user " + i + " does not exist");
                                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                return;
                            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                                e = e2;
                                fileInputStream2 = openRead;
                                android.util.Slog.e(TAG, "Unable to read app idle file for user " + i, e);
                                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                return;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                fileInputStream2 = openRead;
                                libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                throw th;
                            }
                        }
                        if (!newPullParser.getName().equals(TAG_PACKAGES)) {
                            libcore.io.IoUtils.closeQuietly(openRead);
                            return;
                        }
                        int intValue = getIntValue(newPullParser, ATTR_VERSION, 0);
                        while (true) {
                            int next2 = newPullParser.next();
                            if (next2 == i2) {
                                libcore.io.IoUtils.closeQuietly(openRead);
                                return;
                            }
                            if (next2 == i3) {
                                if (newPullParser.getName().equals("package")) {
                                    java.lang.String attributeValue = newPullParser.getAttributeValue(str, "name");
                                    com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory = new com.android.server.usage.AppIdleHistory.AppUsageHistory();
                                    appUsageHistory.lastUsedElapsedTime = java.lang.Long.parseLong(newPullParser.getAttributeValue(str, ATTR_ELAPSED_IDLE));
                                    appUsageHistory.lastUsedByUserElapsedTime = getLongValue(newPullParser, ATTR_LAST_USED_BY_USER_ELAPSED, appUsageHistory.lastUsedElapsedTime);
                                    appUsageHistory.lastUsedScreenTime = java.lang.Long.parseLong(newPullParser.getAttributeValue(str, ATTR_SCREEN_IDLE));
                                    appUsageHistory.lastPredictedTime = getLongValue(newPullParser, ATTR_LAST_PREDICTED_TIME, 0L);
                                    java.lang.String attributeValue2 = newPullParser.getAttributeValue(str, ATTR_CURRENT_BUCKET);
                                    appUsageHistory.currentBucket = attributeValue2 == null ? 10 : java.lang.Integer.parseInt(attributeValue2);
                                    java.lang.String attributeValue3 = newPullParser.getAttributeValue(str, ATTR_BUCKETING_REASON);
                                    fileInputStream = openRead;
                                    try {
                                        try {
                                            appUsageHistory.lastJobRunTime = getLongValue(newPullParser, ATTR_LAST_RUN_JOB_TIME, Long.MIN_VALUE);
                                            appUsageHistory.bucketingReason = 256;
                                            if (attributeValue3 != null) {
                                                try {
                                                    appUsageHistory.bucketingReason = java.lang.Integer.parseInt(attributeValue3, 16);
                                                } catch (java.lang.NumberFormatException e3) {
                                                    android.util.Slog.wtf(TAG, "Unable to read bucketing reason", e3);
                                                }
                                            }
                                            appUsageHistory.lastRestrictAttemptElapsedTime = getLongValue(newPullParser, ATTR_LAST_RESTRICTION_ATTEMPT_ELAPSED, 0L);
                                            java.lang.String attributeValue4 = newPullParser.getAttributeValue(null, ATTR_LAST_RESTRICTION_ATTEMPT_REASON);
                                            if (attributeValue4 != null) {
                                                try {
                                                    appUsageHistory.lastRestrictReason = java.lang.Integer.parseInt(attributeValue4, 16);
                                                } catch (java.lang.NumberFormatException e4) {
                                                    android.util.Slog.wtf(TAG, "Unable to read last restrict reason", e4);
                                                }
                                            }
                                            appUsageHistory.nextEstimatedLaunchTime = getLongValue(newPullParser, ATTR_NEXT_ESTIMATED_APP_LAUNCH_TIME, 0L);
                                            appUsageHistory.lastInformedBucket = -1;
                                            arrayMap.put(attributeValue, appUsageHistory);
                                            if (intValue >= 1) {
                                                int depth = newPullParser.getDepth();
                                                while (com.android.internal.util.jobs.XmlUtils.nextElementWithin(newPullParser, depth)) {
                                                    if (TAG_BUCKET_EXPIRY_TIMES.equals(newPullParser.getName())) {
                                                        readBucketExpiryTimes(newPullParser, appUsageHistory);
                                                    }
                                                }
                                            } else {
                                                long longValue = getLongValue(newPullParser, ATTR_BUCKET_ACTIVE_TIMEOUT_TIME, 0L);
                                                long longValue2 = getLongValue(newPullParser, ATTR_BUCKET_WORKING_SET_TIMEOUT_TIME, 0L);
                                                if (longValue != 0 || longValue2 != 0) {
                                                    insertBucketExpiryTime(appUsageHistory, 10, longValue);
                                                    insertBucketExpiryTime(appUsageHistory, 20, longValue2);
                                                }
                                            }
                                        } catch (java.io.FileNotFoundException e5) {
                                            fileInputStream2 = fileInputStream;
                                            android.util.Slog.d(TAG, "App idle file for user " + i + " does not exist");
                                            libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                            return;
                                        } catch (java.lang.Throwable th2) {
                                            th = th2;
                                            fileInputStream2 = fileInputStream;
                                            libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                            throw th;
                                        }
                                    } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e6) {
                                        e = e6;
                                        fileInputStream2 = fileInputStream;
                                        android.util.Slog.e(TAG, "Unable to read app idle file for user " + i, e);
                                        libcore.io.IoUtils.closeQuietly(fileInputStream2);
                                        return;
                                    }
                                } else {
                                    fileInputStream = openRead;
                                }
                                openRead = fileInputStream;
                                str = null;
                                i2 = 1;
                                i3 = 2;
                            } else {
                                str = null;
                                i2 = 1;
                                i3 = 2;
                            }
                        }
                    } catch (java.io.FileNotFoundException e7) {
                        fileInputStream = openRead;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        fileInputStream = openRead;
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e8) {
                    e = e8;
                    fileInputStream = openRead;
                }
            } catch (java.io.FileNotFoundException e9) {
                fileInputStream2 = null;
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e10) {
                e = e10;
                fileInputStream2 = null;
            } catch (java.lang.Throwable th4) {
                th = th4;
                fileInputStream2 = null;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
        }
    }

    private void readBucketExpiryTimes(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.jobs.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("item".equals(xmlPullParser.getName())) {
                int intValue = getIntValue(xmlPullParser, ATTR_BUCKET, -1);
                if (intValue == -1) {
                    android.util.Slog.e(TAG, "Error reading the buckets expiry times");
                } else {
                    insertBucketExpiryTime(appUsageHistory, intValue, getLongValue(xmlPullParser, ATTR_EXPIRY_TIME, 0L));
                }
            }
        }
    }

    private void insertBucketExpiryTime(com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, int i, long j) {
        if (j == 0) {
            return;
        }
        if (appUsageHistory.bucketExpiryTimesMs == null) {
            appUsageHistory.bucketExpiryTimesMs = new android.util.SparseLongArray();
        }
        appUsageHistory.bucketExpiryTimesMs.put(i, j);
    }

    private long getLongValue(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, long j) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : java.lang.Long.parseLong(attributeValue);
    }

    private int getIntValue(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, int i) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i : java.lang.Integer.parseInt(attributeValue);
    }

    public void writeAppIdleTimes(long j) {
        int size = this.mIdleHistory.size();
        for (int i = 0; i < size; i++) {
            writeAppIdleTimes(this.mIdleHistory.keyAt(i), j);
        }
    }

    public void writeAppIdleTimes(int i, long j) {
        java.io.FileOutputStream fileOutputStream;
        java.io.FileOutputStream fileOutputStream2;
        android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap;
        long j2;
        int i2;
        long j3;
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(getUserFile(i));
        java.io.FileOutputStream fileOutputStream3 = null;
        java.lang.String str = null;
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(startWrite);
                com.android.internal.util.jobs.FastXmlSerializer fastXmlSerializer = new com.android.internal.util.jobs.FastXmlSerializer();
                fastXmlSerializer.setOutput(bufferedOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                fastXmlSerializer.startDocument(null, true);
                fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                fastXmlSerializer.startTag(null, TAG_PACKAGES);
                fastXmlSerializer.attribute(null, ATTR_VERSION, java.lang.String.valueOf(1));
                long elapsedTime = getElapsedTime(j);
                android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> userHistory = getUserHistory(i);
                int size = userHistory.size();
                int i3 = 0;
                while (i3 < size) {
                    try {
                        java.lang.String keyAt = userHistory.keyAt(i3);
                        if (keyAt == null) {
                            try {
                                android.util.Slog.w(TAG, "Skipping App Idle write for unexpected null package");
                                fileOutputStream2 = startWrite;
                                arrayMap = userHistory;
                                j2 = elapsedTime;
                            } catch (java.lang.Exception e) {
                                e = e;
                                fileOutputStream3 = startWrite;
                                atomicFile.failWrite(fileOutputStream3);
                                android.util.Slog.e(TAG, "Error writing app idle file for user " + i, e);
                            }
                        } else {
                            com.android.server.usage.AppIdleHistory.AppUsageHistory valueAt = userHistory.valueAt(i3);
                            fastXmlSerializer.startTag(str, "package");
                            arrayMap = userHistory;
                            fastXmlSerializer.attribute(null, "name", keyAt);
                            fileOutputStream2 = startWrite;
                            try {
                                fastXmlSerializer.attribute(null, ATTR_ELAPSED_IDLE, java.lang.Long.toString(valueAt.lastUsedElapsedTime));
                                fastXmlSerializer.attribute(null, ATTR_LAST_USED_BY_USER_ELAPSED, java.lang.Long.toString(valueAt.lastUsedByUserElapsedTime));
                                fastXmlSerializer.attribute(null, ATTR_SCREEN_IDLE, java.lang.Long.toString(valueAt.lastUsedScreenTime));
                                fastXmlSerializer.attribute(null, ATTR_LAST_PREDICTED_TIME, java.lang.Long.toString(valueAt.lastPredictedTime));
                                fastXmlSerializer.attribute(null, ATTR_CURRENT_BUCKET, java.lang.Integer.toString(valueAt.currentBucket));
                                fastXmlSerializer.attribute(null, ATTR_BUCKETING_REASON, java.lang.Integer.toHexString(valueAt.bucketingReason));
                                if (valueAt.lastJobRunTime != Long.MIN_VALUE) {
                                    fastXmlSerializer.attribute(null, ATTR_LAST_RUN_JOB_TIME, java.lang.Long.toString(valueAt.lastJobRunTime));
                                }
                                if (valueAt.lastRestrictAttemptElapsedTime > 0) {
                                    fastXmlSerializer.attribute(null, ATTR_LAST_RESTRICTION_ATTEMPT_ELAPSED, java.lang.Long.toString(valueAt.lastRestrictAttemptElapsedTime));
                                }
                                fastXmlSerializer.attribute(null, ATTR_LAST_RESTRICTION_ATTEMPT_REASON, java.lang.Integer.toHexString(valueAt.lastRestrictReason));
                                if (valueAt.nextEstimatedLaunchTime > 0) {
                                    fastXmlSerializer.attribute(null, ATTR_NEXT_ESTIMATED_APP_LAUNCH_TIME, java.lang.Long.toString(valueAt.nextEstimatedLaunchTime));
                                }
                                if (valueAt.bucketExpiryTimesMs != null) {
                                    fastXmlSerializer.startTag(null, TAG_BUCKET_EXPIRY_TIMES);
                                    int size2 = valueAt.bucketExpiryTimesMs.size();
                                    int i4 = 0;
                                    while (i4 < size2) {
                                        long valueAt2 = valueAt.bucketExpiryTimesMs.valueAt(i4);
                                        if (valueAt2 < elapsedTime) {
                                            i2 = size2;
                                            j3 = elapsedTime;
                                        } else {
                                            int keyAt2 = valueAt.bucketExpiryTimesMs.keyAt(i4);
                                            i2 = size2;
                                            fastXmlSerializer.startTag(null, "item");
                                            j3 = elapsedTime;
                                            fastXmlSerializer.attribute(null, ATTR_BUCKET, java.lang.String.valueOf(keyAt2));
                                            fastXmlSerializer.attribute(null, ATTR_EXPIRY_TIME, java.lang.String.valueOf(valueAt2));
                                            fastXmlSerializer.endTag(null, "item");
                                        }
                                        i4++;
                                        size2 = i2;
                                        elapsedTime = j3;
                                    }
                                    j2 = elapsedTime;
                                    fastXmlSerializer.endTag(null, TAG_BUCKET_EXPIRY_TIMES);
                                } else {
                                    j2 = elapsedTime;
                                }
                                fastXmlSerializer.endTag(null, "package");
                            } catch (java.lang.Exception e2) {
                                e = e2;
                                fileOutputStream3 = fileOutputStream2;
                                atomicFile.failWrite(fileOutputStream3);
                                android.util.Slog.e(TAG, "Error writing app idle file for user " + i, e);
                            }
                        }
                        i3++;
                        userHistory = arrayMap;
                        startWrite = fileOutputStream2;
                        elapsedTime = j2;
                        str = null;
                    } catch (java.lang.Exception e3) {
                        e = e3;
                        fileOutputStream2 = startWrite;
                    }
                }
                java.io.FileOutputStream fileOutputStream4 = startWrite;
                try {
                    fastXmlSerializer.endTag(null, TAG_PACKAGES);
                    fastXmlSerializer.endDocument();
                    fileOutputStream = fileOutputStream4;
                } catch (java.lang.Exception e4) {
                    e = e4;
                    fileOutputStream = fileOutputStream4;
                }
            } catch (java.lang.Exception e5) {
                e = e5;
                fileOutputStream = startWrite;
            }
        } catch (java.lang.Exception e6) {
            e = e6;
        }
        try {
            atomicFile.finishWrite(fileOutputStream);
        } catch (java.lang.Exception e7) {
            e = e7;
            fileOutputStream3 = fileOutputStream;
            atomicFile.failWrite(fileOutputStream3);
            android.util.Slog.e(TAG, "Error writing app idle file for user " + i, e);
        }
    }

    public void dumpUsers(android.util.IndentingPrintWriter indentingPrintWriter, int[] iArr, java.util.List<java.lang.String> list) {
        for (int i : iArr) {
            indentingPrintWriter.println();
            dumpUser(indentingPrintWriter, i, list);
        }
    }

    private void dumpUser(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.util.List<java.lang.String> list) {
        android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap;
        int i2;
        int i3;
        int i4;
        int i5 = i;
        indentingPrintWriter.print("User ");
        indentingPrintWriter.print(i);
        indentingPrintWriter.println(" App Standby States:");
        indentingPrintWriter.increaseIndent();
        android.util.ArrayMap<java.lang.String, com.android.server.usage.AppIdleHistory.AppUsageHistory> arrayMap2 = this.mIdleHistory.get(i5);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long elapsedTime = getElapsedTime(elapsedRealtime);
        getScreenOnTime(elapsedRealtime);
        if (arrayMap2 == null) {
            return;
        }
        int size = arrayMap2.size();
        int i6 = 0;
        while (i6 < size) {
            java.lang.String keyAt = arrayMap2.keyAt(i6);
            com.android.server.usage.AppIdleHistory.AppUsageHistory valueAt = arrayMap2.valueAt(i6);
            if (!com.android.internal.util.jobs.CollectionUtils.isEmpty(list) && !list.contains(keyAt)) {
                i2 = size;
                i3 = i6;
                i4 = i5;
                arrayMap = arrayMap2;
                i6 = i3 + 1;
                i5 = i4;
                arrayMap2 = arrayMap;
                size = i2;
            }
            indentingPrintWriter.print("package=" + keyAt);
            indentingPrintWriter.print(" u=" + i5);
            indentingPrintWriter.print(" bucket=" + valueAt.currentBucket + " reason=" + android.app.usage.UsageStatsManager.reasonToString(valueAt.bucketingReason));
            indentingPrintWriter.print(" used=");
            arrayMap = arrayMap2;
            i2 = size;
            i3 = i6;
            printLastActionElapsedTime(indentingPrintWriter, elapsedTime, valueAt.lastUsedElapsedTime);
            indentingPrintWriter.print(" usedByUser=");
            printLastActionElapsedTime(indentingPrintWriter, elapsedTime, valueAt.lastUsedByUserElapsedTime);
            indentingPrintWriter.print(" usedScr=");
            printLastActionElapsedTime(indentingPrintWriter, elapsedTime, valueAt.lastUsedScreenTime);
            indentingPrintWriter.print(" lastPred=");
            printLastActionElapsedTime(indentingPrintWriter, elapsedTime, valueAt.lastPredictedTime);
            dumpBucketExpiryTimes(indentingPrintWriter, valueAt, elapsedTime);
            indentingPrintWriter.print(" lastJob=");
            android.util.TimeUtils.formatDuration(elapsedTime - valueAt.lastJobRunTime, indentingPrintWriter);
            indentingPrintWriter.print(" lastInformedBucket=" + valueAt.lastInformedBucket);
            if (valueAt.lastRestrictAttemptElapsedTime > 0) {
                indentingPrintWriter.print(" lastRestrictAttempt=");
                android.util.TimeUtils.formatDuration(elapsedTime - valueAt.lastRestrictAttemptElapsedTime, indentingPrintWriter);
                indentingPrintWriter.print(" lastRestrictReason=" + android.app.usage.UsageStatsManager.reasonToString(valueAt.lastRestrictReason));
            }
            if (valueAt.nextEstimatedLaunchTime > 0) {
                indentingPrintWriter.print(" nextEstimatedLaunchTime=");
                android.util.TimeUtils.formatDuration(valueAt.nextEstimatedLaunchTime - currentTimeMillis, indentingPrintWriter);
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(" idle=");
            i4 = i;
            sb.append(isIdle(keyAt, i4, elapsedRealtime) ? "y" : "n");
            indentingPrintWriter.print(sb.toString());
            indentingPrintWriter.println();
            i6 = i3 + 1;
            i5 = i4;
            arrayMap2 = arrayMap;
            size = i2;
        }
        indentingPrintWriter.println();
        indentingPrintWriter.print("totalElapsedTime=");
        android.util.TimeUtils.formatDuration(getElapsedTime(elapsedRealtime), indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("totalScreenOnTime=");
        android.util.TimeUtils.formatDuration(getScreenOnTime(elapsedRealtime), indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    private void printLastActionElapsedTime(android.util.IndentingPrintWriter indentingPrintWriter, long j, long j2) {
        if (j2 < 0) {
            indentingPrintWriter.print("<uninitialized>");
        } else {
            android.util.TimeUtils.formatDuration(j - j2, indentingPrintWriter);
        }
    }

    private void dumpBucketExpiryTimes(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.usage.AppIdleHistory.AppUsageHistory appUsageHistory, long j) {
        indentingPrintWriter.print(" expiryTimes=");
        if (appUsageHistory.bucketExpiryTimesMs == null || appUsageHistory.bucketExpiryTimesMs.size() == 0) {
            indentingPrintWriter.print("<none>");
            return;
        }
        indentingPrintWriter.print("(");
        int size = appUsageHistory.bucketExpiryTimesMs.size();
        for (int i = 0; i < size; i++) {
            int keyAt = appUsageHistory.bucketExpiryTimesMs.keyAt(i);
            long valueAt = appUsageHistory.bucketExpiryTimesMs.valueAt(i);
            if (i != 0) {
                indentingPrintWriter.print(",");
            }
            indentingPrintWriter.print(keyAt + ":");
            android.util.TimeUtils.formatDuration(j - valueAt, indentingPrintWriter);
        }
        indentingPrintWriter.print(")");
    }
}
