package com.android.server.usage;

/* loaded from: classes2.dex */
final class UsageStatsProtoV2 {
    private static final long ONE_HOUR_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(1);
    private static final java.lang.String TAG = "UsageStatsProtoV2";

    private UsageStatsProtoV2() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00af, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.app.usage.UsageStats parseUsageStats(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException {
        android.app.usage.UsageStats usageStats = new android.app.usage.UsageStats();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    usageStats.mPackageToken = protoInputStream.readInt(1120986464257L) - 1;
                    break;
                case 3:
                    usageStats.mLastTimeUsed = protoInputStream.readLong(1112396529667L) + j;
                    break;
                case 4:
                    usageStats.mTotalTimeInForeground = protoInputStream.readLong(1112396529668L);
                    break;
                case 6:
                    usageStats.mAppLaunchCount = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    try {
                        long start = protoInputStream.start(2246267895815L);
                        loadChooserCounts(protoInputStream, usageStats);
                        protoInputStream.end(start);
                        break;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to read chooser counts for " + usageStats.mPackageToken);
                        break;
                    }
                case 8:
                    usageStats.mLastTimeForegroundServiceUsed = protoInputStream.readLong(1112396529672L) + j;
                    break;
                case 9:
                    usageStats.mTotalTimeForegroundServiceUsed = protoInputStream.readLong(1112396529673L);
                    break;
                case 10:
                    usageStats.mLastTimeVisible = protoInputStream.readLong(1112396529674L) + j;
                    break;
                case 11:
                    usageStats.mTotalTimeVisible = protoInputStream.readLong(1112396529675L);
                    break;
                case 12:
                    usageStats.mLastTimeComponentUsed = protoInputStream.readLong(1112396529676L) + j;
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0027, code lost:
    
        r4.end(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadCountAndTime(android.util.proto.ProtoInputStream protoInputStream, long j, com.android.server.usage.IntervalStats.EventTracker eventTracker) {
        try {
            long start = protoInputStream.start(j);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to read event tracker " + j, e);
            return;
        }
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 0:
                default:
                    continue;
                case 1:
                    eventTracker.count = protoInputStream.readInt(1120986464257L);
                    continue;
                case 2:
                    eventTracker.duration = protoInputStream.readLong(1112396529666L);
                    continue;
            }
            android.util.Slog.e(TAG, "Unable to read event tracker " + j, e);
            return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0050, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadChooserCounts(android.util.proto.ProtoInputStream protoInputStream, android.app.usage.UsageStats usageStats) throws java.io.IOException {
        android.util.SparseIntArray sparseIntArray;
        if (protoInputStream.nextField(1120986464257L)) {
            int readInt = protoInputStream.readInt(1120986464257L) - 1;
            sparseIntArray = (android.util.SparseIntArray) usageStats.mChooserCountsObfuscated.get(readInt);
            if (sparseIntArray == null) {
                sparseIntArray = new android.util.SparseIntArray();
                usageStats.mChooserCountsObfuscated.put(readInt, sparseIntArray);
            }
        } else {
            sparseIntArray = new android.util.SparseIntArray();
        }
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    usageStats.mChooserCountsObfuscated.put(protoInputStream.readInt(1120986464257L) - 1, sparseIntArray);
                    break;
                case 2:
                    long start = protoInputStream.start(2246267895810L);
                    loadCountsForAction(protoInputStream, sparseIntArray);
                    protoInputStream.end(start);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
    
        if (r2 == (-1)) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
    
        r6.put(r2, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadCountsForAction(android.util.proto.ProtoInputStream protoInputStream, android.util.SparseIntArray sparseIntArray) throws java.io.IOException {
        int i = 0;
        int i2 = -1;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    i2 = protoInputStream.readInt(1120986464257L) - 1;
                    break;
                case 2:
                    i = protoInputStream.readInt(1120986464258L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006e, code lost:
    
        if (r5 == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0070, code lost:
    
        r11.activeConfiguration = r1.mConfiguration;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0074, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadConfigStats(android.util.proto.ProtoInputStream protoInputStream, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException {
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        android.app.usage.ConfigurationStats configurationStats = new android.app.usage.ConfigurationStats();
        boolean z = false;
        if (protoInputStream.nextField(1146756268033L)) {
            configuration.readFromProto(protoInputStream, 1146756268033L);
            configurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
        }
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    configuration.readFromProto(protoInputStream, 1146756268033L);
                    android.app.usage.ConfigurationStats orCreateConfigurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
                    orCreateConfigurationStats.mLastTimeActive = configurationStats.mLastTimeActive;
                    orCreateConfigurationStats.mTotalTimeActive = configurationStats.mTotalTimeActive;
                    orCreateConfigurationStats.mActivationCount = configurationStats.mActivationCount;
                    configurationStats = orCreateConfigurationStats;
                    break;
                case 2:
                    configurationStats.mLastTimeActive = intervalStats.beginTime + protoInputStream.readLong(1112396529666L);
                    break;
                case 3:
                    configurationStats.mTotalTimeActive = protoInputStream.readLong(1112396529667L);
                    break;
                case 4:
                    configurationStats.mActivationCount = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    z = protoInputStream.readBoolean(1133871366149L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e6, code lost:
    
        if (r0.mPackageToken != (-1)) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00e8, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.app.usage.UsageEvents.Event parseEvent(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    event.mPackageToken = protoInputStream.readInt(1120986464257L) - 1;
                    break;
                case 2:
                    event.mClassToken = protoInputStream.readInt(1120986464258L) - 1;
                    break;
                case 3:
                    event.mTimeStamp = protoInputStream.readLong(1112396529667L) + j;
                    break;
                case 4:
                    event.mFlags = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    event.mEventType = protoInputStream.readInt(1120986464261L);
                    break;
                case 6:
                    event.mConfiguration = new android.content.res.Configuration();
                    event.mConfiguration.readFromProto(protoInputStream, 1146756268038L);
                    break;
                case 7:
                    event.mShortcutIdToken = protoInputStream.readInt(1120986464263L) - 1;
                    break;
                case 8:
                    event.mBucketAndReason = protoInputStream.readInt(1120986464264L);
                    break;
                case 9:
                    event.mNotificationChannelIdToken = protoInputStream.readInt(1120986464265L) - 1;
                    break;
                case 10:
                    event.mInstanceId = protoInputStream.readInt(1120986464266L);
                    break;
                case 11:
                    event.mTaskRootPackageToken = protoInputStream.readInt(1120986464267L) - 1;
                    break;
                case 12:
                    event.mTaskRootClassToken = protoInputStream.readInt(1120986464268L) - 1;
                    break;
                case 13:
                    event.mLocusIdToken = protoInputStream.readInt(1120986464269L) - 1;
                    break;
                case 14:
                    try {
                        long start = protoInputStream.start(1146756268046L);
                        event.mUserInteractionExtrasToken = parseUserInteractionEventExtras(protoInputStream);
                        protoInputStream.end(start);
                        break;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to read some user interaction extras from proto.", e);
                        break;
                    }
            }
        }
    }

    static void writeOffsetTimestamp(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
        if (j2 > j3 - ONE_HOUR_MS) {
            protoOutputStream.write(j, getOffsetTimestamp(j2, j3));
        }
    }

    static long getOffsetTimestamp(long j, long j2) {
        long j3 = j - j2;
        return j3 == 0 ? j3 + 1 : j3;
    }

    private static void writeUsageStats(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.app.usage.UsageStats usageStats) throws java.lang.IllegalArgumentException {
        protoOutputStream.write(1120986464257L, usageStats.mPackageToken + 1);
        writeOffsetTimestamp(protoOutputStream, 1112396529667L, usageStats.mLastTimeUsed, j);
        protoOutputStream.write(1112396529668L, usageStats.mTotalTimeInForeground);
        writeOffsetTimestamp(protoOutputStream, 1112396529672L, usageStats.mLastTimeForegroundServiceUsed, j);
        protoOutputStream.write(1112396529673L, usageStats.mTotalTimeForegroundServiceUsed);
        writeOffsetTimestamp(protoOutputStream, 1112396529674L, usageStats.mLastTimeVisible, j);
        protoOutputStream.write(1112396529675L, usageStats.mTotalTimeVisible);
        writeOffsetTimestamp(protoOutputStream, 1112396529676L, usageStats.mLastTimeComponentUsed, j);
        protoOutputStream.write(1120986464262L, usageStats.mAppLaunchCount);
        try {
            writeChooserCounts(protoOutputStream, usageStats);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Unable to write chooser counts for " + usageStats.mPackageName, e);
        }
    }

    private static void writeCountAndTime(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i, long j2) throws java.lang.IllegalArgumentException {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.end(start);
    }

    private static void writeChooserCounts(android.util.proto.ProtoOutputStream protoOutputStream, android.app.usage.UsageStats usageStats) throws java.lang.IllegalArgumentException {
        if (usageStats == null || usageStats.mChooserCountsObfuscated.size() == 0) {
            return;
        }
        int size = usageStats.mChooserCountsObfuscated.size();
        for (int i = 0; i < size; i++) {
            int keyAt = usageStats.mChooserCountsObfuscated.keyAt(i);
            android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) usageStats.mChooserCountsObfuscated.valueAt(i);
            if (sparseIntArray != null && sparseIntArray.size() != 0) {
                long start = protoOutputStream.start(2246267895815L);
                protoOutputStream.write(1120986464257L, keyAt + 1);
                writeCountsForAction(protoOutputStream, sparseIntArray);
                protoOutputStream.end(start);
            }
        }
    }

    private static void writeCountsForAction(android.util.proto.ProtoOutputStream protoOutputStream, android.util.SparseIntArray sparseIntArray) throws java.lang.IllegalArgumentException {
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseIntArray.keyAt(i);
            int valueAt = sparseIntArray.valueAt(i);
            if (valueAt > 0) {
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1120986464257L, keyAt + 1);
                protoOutputStream.write(1120986464258L, valueAt);
                protoOutputStream.end(start);
            }
        }
    }

    private static void writeConfigStats(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.app.usage.ConfigurationStats configurationStats, boolean z) throws java.lang.IllegalArgumentException {
        configurationStats.mConfiguration.dumpDebug(protoOutputStream, 1146756268033L);
        writeOffsetTimestamp(protoOutputStream, 1112396529666L, configurationStats.mLastTimeActive, j);
        protoOutputStream.write(1112396529667L, configurationStats.mTotalTimeActive);
        protoOutputStream.write(1120986464260L, configurationStats.mActivationCount);
        protoOutputStream.write(1133871366149L, z);
    }

    private static void writeEvent(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.app.usage.UsageEvents.Event event) throws java.io.IOException, java.lang.IllegalArgumentException {
        protoOutputStream.write(1120986464257L, event.mPackageToken + 1);
        if (event.mClassToken != -1) {
            protoOutputStream.write(1120986464258L, event.mClassToken + 1);
        }
        writeOffsetTimestamp(protoOutputStream, 1112396529667L, event.mTimeStamp, j);
        protoOutputStream.write(1120986464260L, event.mFlags);
        protoOutputStream.write(1120986464261L, event.mEventType);
        protoOutputStream.write(1120986464266L, event.mInstanceId);
        if (event.mTaskRootPackageToken != -1) {
            protoOutputStream.write(1120986464267L, event.mTaskRootPackageToken + 1);
        }
        if (event.mTaskRootClassToken != -1) {
            protoOutputStream.write(1120986464268L, event.mTaskRootClassToken + 1);
        }
        switch (event.mEventType) {
            case 5:
                if (event.mConfiguration != null) {
                    event.mConfiguration.dumpDebug(protoOutputStream, 1146756268038L);
                    break;
                }
                break;
            case 7:
                if (event.mUserInteractionExtrasToken != null) {
                    writeUserInteractionEventExtras(protoOutputStream, 1146756268046L, event.mUserInteractionExtrasToken);
                    break;
                }
                break;
            case 8:
                if (event.mShortcutIdToken != -1) {
                    protoOutputStream.write(1120986464263L, event.mShortcutIdToken + 1);
                    break;
                }
                break;
            case 11:
                if (event.mBucketAndReason != 0) {
                    protoOutputStream.write(1120986464264L, event.mBucketAndReason);
                    break;
                }
                break;
            case 12:
                if (event.mNotificationChannelIdToken != -1) {
                    protoOutputStream.write(1120986464265L, event.mNotificationChannelIdToken + 1);
                    break;
                }
                break;
            case 30:
                if (event.mLocusIdToken != -1) {
                    protoOutputStream.write(1120986464269L, event.mLocusIdToken + 1);
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d1, code lost:
    
        r6 = r7.packageStatsObfuscated.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d8, code lost:
    
        if (r8 >= r6) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00da, code lost:
    
        r0 = r7.packageStatsObfuscated.valueAt(r8);
        r0.mBeginTimeStamp = r7.beginTime;
        r0.mEndTimeStamp = r7.endTime;
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ed, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void read(java.io.InputStream inputStream, com.android.server.usage.IntervalStats intervalStats, boolean z) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 1:
                    intervalStats.endTime = intervalStats.beginTime + protoInputStream.readLong(1112396529665L);
                    break;
                case 2:
                    intervalStats.majorVersion = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    intervalStats.minorVersion = protoInputStream.readInt(1120986464259L);
                    break;
                case 10:
                    loadCountAndTime(protoInputStream, 1146756268042L, intervalStats.interactiveTracker);
                    break;
                case 11:
                    loadCountAndTime(protoInputStream, 1146756268043L, intervalStats.nonInteractiveTracker);
                    break;
                case 12:
                    loadCountAndTime(protoInputStream, 1146756268044L, intervalStats.keyguardShownTracker);
                    break;
                case 13:
                    loadCountAndTime(protoInputStream, 1146756268045L, intervalStats.keyguardHiddenTracker);
                    break;
                case 20:
                    try {
                        long start = protoInputStream.start(2246267895828L);
                        android.app.usage.UsageStats parseUsageStats = parseUsageStats(protoInputStream, intervalStats.beginTime);
                        protoInputStream.end(start);
                        if (parseUsageStats.mPackageToken == -1) {
                            break;
                        } else {
                            intervalStats.packageStatsObfuscated.put(parseUsageStats.mPackageToken, parseUsageStats);
                            break;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to read some usage stats from proto.", e);
                        break;
                    }
                case 21:
                    try {
                        long start2 = protoInputStream.start(2246267895829L);
                        loadConfigStats(protoInputStream, intervalStats);
                        protoInputStream.end(start2);
                        break;
                    } catch (java.io.IOException e2) {
                        android.util.Slog.e(TAG, "Unable to read some configuration stats from proto.", e2);
                        break;
                    }
                case 22:
                    if (!z) {
                        try {
                            long start3 = protoInputStream.start(2246267895830L);
                            android.app.usage.UsageEvents.Event parseEvent = parseEvent(protoInputStream, intervalStats.beginTime);
                            protoInputStream.end(start3);
                            if (parseEvent == null) {
                                break;
                            } else {
                                intervalStats.events.insert(parseEvent);
                                break;
                            }
                        } catch (java.io.IOException e3) {
                            android.util.Slog.e(TAG, "Unable to read some events from proto.", e3);
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }
    }

    public static void write(java.io.OutputStream outputStream, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException, java.lang.IllegalArgumentException {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        protoOutputStream.write(1112396529665L, getOffsetTimestamp(intervalStats.endTime, intervalStats.beginTime));
        protoOutputStream.write(1120986464258L, intervalStats.majorVersion);
        protoOutputStream.write(1120986464259L, intervalStats.minorVersion);
        try {
            writeCountAndTime(protoOutputStream, 1146756268042L, intervalStats.interactiveTracker.count, intervalStats.interactiveTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268043L, intervalStats.nonInteractiveTracker.count, intervalStats.nonInteractiveTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268044L, intervalStats.keyguardShownTracker.count, intervalStats.keyguardShownTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268045L, intervalStats.keyguardHiddenTracker.count, intervalStats.keyguardHiddenTracker.duration);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Unable to write some interval stats trackers to proto.", e);
        }
        int size = intervalStats.packageStatsObfuscated.size();
        for (int i = 0; i < size; i++) {
            try {
                long start = protoOutputStream.start(2246267895828L);
                writeUsageStats(protoOutputStream, intervalStats.beginTime, intervalStats.packageStatsObfuscated.valueAt(i));
                protoOutputStream.end(start);
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.e(TAG, "Unable to write some usage stats to proto.", e2);
            }
        }
        int size2 = intervalStats.configurations.size();
        for (int i2 = 0; i2 < size2; i2++) {
            boolean equals = intervalStats.activeConfiguration.equals(intervalStats.configurations.keyAt(i2));
            try {
                long start2 = protoOutputStream.start(2246267895829L);
                writeConfigStats(protoOutputStream, intervalStats.beginTime, intervalStats.configurations.valueAt(i2), equals);
                protoOutputStream.end(start2);
            } catch (java.lang.IllegalArgumentException e3) {
                android.util.Slog.e(TAG, "Unable to write some configuration stats to proto.", e3);
            }
        }
        int size3 = intervalStats.events.size();
        for (int i3 = 0; i3 < size3; i3++) {
            try {
                long start3 = protoOutputStream.start(2246267895830L);
                writeEvent(protoOutputStream, intervalStats.beginTime, intervalStats.events.get(i3));
                protoOutputStream.end(start3);
            } catch (java.lang.IllegalArgumentException e4) {
                android.util.Slog.e(TAG, "Unable to write some events to proto.", e4);
            }
        }
        protoOutputStream.flush();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
    
        if (r2 == (-1)) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002c, code lost:
    
        r6.put(r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadPackagesMap(android.util.proto.ProtoInputStream protoInputStream, android.util.SparseArray<java.util.ArrayList<java.lang.String>> sparseArray) throws java.io.IOException {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        int i = -1;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    i = protoInputStream.readInt(1120986464257L) - 1;
                    break;
                case 2:
                    arrayList.add(protoInputStream.readString(2237677961218L));
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002c, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void readObfuscatedData(java.io.InputStream inputStream, com.android.server.usage.PackagesTokenData packagesTokenData) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    packagesTokenData.counter = protoInputStream.readInt(1120986464257L);
                    break;
                case 2:
                    long start = protoInputStream.start(2246267895810L);
                    loadPackagesMap(protoInputStream, packagesTokenData.tokensToPackagesMap);
                    protoInputStream.end(start);
                    break;
            }
        }
    }

    static void writeObfuscatedData(java.io.OutputStream outputStream, com.android.server.usage.PackagesTokenData packagesTokenData) throws java.io.IOException, java.lang.IllegalArgumentException {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        protoOutputStream.write(1120986464257L, packagesTokenData.counter);
        int size = packagesTokenData.tokensToPackagesMap.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, packagesTokenData.tokensToPackagesMap.keyAt(i) + 1);
            java.util.ArrayList<java.lang.String> valueAt = packagesTokenData.tokensToPackagesMap.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                protoOutputStream.write(2237677961218L, valueAt.get(i2));
            }
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ba, code lost:
    
        switch(r0.mEventType) {
            case 5: goto L29;
            case 8: goto L26;
            case 12: goto L23;
            default: goto L32;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c0, code lost:
    
        if (r0.mNotificationChannelId != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c2, code lost:
    
        r0.mNotificationChannelId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c7, code lost:
    
        if (r0.mShortcutId != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00c9, code lost:
    
        r0.mShortcutId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ce, code lost:
    
        if (r0.mConfiguration != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d0, code lost:
    
        r0.mConfiguration = new android.content.res.Configuration();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d9, code lost:
    
        if (r0.mPackage != null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00db, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.app.usage.UsageEvents.Event parsePendingEvent(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    event.mPackage = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    event.mClass = protoInputStream.readString(1138166333442L);
                    break;
                case 3:
                    event.mTimeStamp = protoInputStream.readLong(1112396529667L);
                    break;
                case 4:
                    event.mFlags = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    event.mEventType = protoInputStream.readInt(1120986464261L);
                    break;
                case 6:
                    event.mConfiguration = new android.content.res.Configuration();
                    event.mConfiguration.readFromProto(protoInputStream, 1146756268038L);
                    break;
                case 7:
                    event.mShortcutId = protoInputStream.readString(1138166333447L);
                    break;
                case 8:
                    event.mBucketAndReason = protoInputStream.readInt(1120986464264L);
                    break;
                case 9:
                    event.mNotificationChannelId = protoInputStream.readString(1138166333449L);
                    break;
                case 10:
                    event.mInstanceId = protoInputStream.readInt(1120986464266L);
                    break;
                case 11:
                    event.mTaskRootPackage = protoInputStream.readString(1138166333451L);
                    break;
                case 12:
                    event.mTaskRootClass = protoInputStream.readString(1138166333452L);
                    break;
                case 14:
                    event.mExtras = parsePendingEventExtras(protoInputStream, 1151051235342L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0030, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void readPendingEvents(java.io.InputStream inputStream, java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 23:
                    try {
                        long start = protoInputStream.start(2246267895831L);
                        android.app.usage.UsageEvents.Event parsePendingEvent = parsePendingEvent(protoInputStream);
                        protoInputStream.end(start);
                        if (parsePendingEvent == null) {
                            break;
                        } else {
                            linkedList.add(parsePendingEvent);
                            break;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to parse some pending events from proto.", e);
                        break;
                    }
            }
        }
    }

    private static void writePendingEvent(android.util.proto.ProtoOutputStream protoOutputStream, android.app.usage.UsageEvents.Event event) throws java.io.IOException, java.lang.IllegalArgumentException {
        protoOutputStream.write(1138166333441L, event.mPackage);
        if (event.mClass != null) {
            protoOutputStream.write(1138166333442L, event.mClass);
        }
        protoOutputStream.write(1112396529667L, event.mTimeStamp);
        protoOutputStream.write(1120986464260L, event.mFlags);
        protoOutputStream.write(1120986464261L, event.mEventType);
        protoOutputStream.write(1120986464266L, event.mInstanceId);
        if (event.mTaskRootPackage != null) {
            protoOutputStream.write(1138166333451L, event.mTaskRootPackage);
        }
        if (event.mTaskRootClass != null) {
            protoOutputStream.write(1138166333452L, event.mTaskRootClass);
        }
        switch (event.mEventType) {
            case 5:
                if (event.mConfiguration != null) {
                    event.mConfiguration.dumpDebug(protoOutputStream, 1146756268038L);
                    break;
                }
                break;
            case 7:
                if (event.mExtras != null && event.mExtras.size() != 0) {
                    writePendingEventExtras(protoOutputStream, 1151051235342L, event.mExtras);
                    break;
                }
                break;
            case 8:
                if (event.mShortcutId != null) {
                    protoOutputStream.write(1138166333447L, event.mShortcutId);
                    break;
                }
                break;
            case 11:
                if (event.mBucketAndReason != 0) {
                    protoOutputStream.write(1120986464264L, event.mBucketAndReason);
                    break;
                }
                break;
            case 12:
                if (event.mNotificationChannelId != null) {
                    protoOutputStream.write(1138166333449L, event.mNotificationChannelId);
                    break;
                }
                break;
        }
    }

    static void writePendingEvents(java.io.OutputStream outputStream, java.util.LinkedList<android.app.usage.UsageEvents.Event> linkedList) throws java.io.IOException, java.lang.IllegalArgumentException {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            try {
                long start = protoOutputStream.start(2246267895831L);
                writePendingEvent(protoOutputStream, linkedList.get(i));
                protoOutputStream.end(start);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Unable to write some pending events to proto.", e);
            }
        }
        protoOutputStream.flush();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:
    
        return new android.util.Pair<>(r0, java.lang.Long.valueOf(r1));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.util.Pair<java.lang.String, java.lang.Long> parseGlobalComponentUsage(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        java.lang.String str = "";
        long j = 0;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    j = protoInputStream.readLong(1112396529666L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x004e, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void readGlobalComponentUsage(java.io.InputStream inputStream, java.util.Map<java.lang.String, java.lang.Long> map) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 24:
                    try {
                        long start = protoInputStream.start(2246267895832L);
                        android.util.Pair<java.lang.String, java.lang.Long> parseGlobalComponentUsage = parseGlobalComponentUsage(protoInputStream);
                        protoInputStream.end(start);
                        if (!((java.lang.String) parseGlobalComponentUsage.first).isEmpty() && ((java.lang.Long) parseGlobalComponentUsage.second).longValue() > 0) {
                            map.put((java.lang.String) parseGlobalComponentUsage.first, (java.lang.Long) parseGlobalComponentUsage.second);
                            break;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to parse some package usage from proto.", e);
                        break;
                    }
                    break;
            }
        }
    }

    static void writeGlobalComponentUsage(java.io.OutputStream outputStream, java.util.Map<java.lang.String, java.lang.Long> map) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        java.util.Map.Entry[] entryArr = (java.util.Map.Entry[]) map.entrySet().toArray();
        int length = entryArr.length;
        for (int i = 0; i < length; i++) {
            if (((java.lang.Long) entryArr[i].getValue()).longValue() > 0) {
                long start = protoOutputStream.start(2246267895832L);
                protoOutputStream.write(1138166333441L, (java.lang.String) entryArr[i].getKey());
                protoOutputStream.write(1112396529666L, ((java.lang.Long) entryArr[i].getValue()).longValue());
                protoOutputStream.end(start);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken parseUserInteractionEventExtras(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken userInteractionEventExtrasToken = new android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    userInteractionEventExtrasToken.mCategoryToken = protoInputStream.readInt(1120986464257L) - 1;
                    break;
                case 2:
                    userInteractionEventExtrasToken.mActionToken = protoInputStream.readInt(1120986464258L) - 1;
                    break;
            }
        }
    }

    static void writeUserInteractionEventExtras(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.app.usage.UsageEvents.Event.UserInteractionEventExtrasToken userInteractionEventExtrasToken) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, userInteractionEventExtrasToken.mCategoryToken + 1);
        protoOutputStream.write(1120986464258L, userInteractionEventExtrasToken.mActionToken + 1);
        protoOutputStream.end(start);
    }

    private static android.os.PersistableBundle parsePendingEventExtras(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException {
        return android.os.PersistableBundle.readFromStream(new java.io.ByteArrayInputStream(protoInputStream.readBytes(j)));
    }

    static void writePendingEventExtras(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.PersistableBundle persistableBundle) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        persistableBundle.writeToStream(byteArrayOutputStream);
        protoOutputStream.write(j, byteArrayOutputStream.toByteArray());
    }
}
