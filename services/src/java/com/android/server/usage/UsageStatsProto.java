package com.android.server.usage;

/* loaded from: classes2.dex */
final class UsageStatsProto {
    private static java.lang.String TAG = "UsageStatsProto";

    private UsageStatsProto() {
    }

    private static java.util.List<java.lang.String> readStringPool(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        java.util.ArrayList arrayList;
        long start = protoInputStream.start(1146756268034L);
        if (protoInputStream.nextField(1120986464257L)) {
            arrayList = new java.util.ArrayList(protoInputStream.readInt(1120986464257L));
        } else {
            arrayList = new java.util.ArrayList();
        }
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 2:
                    arrayList.add(protoInputStream.readString(2237677961218L));
                    break;
            }
        }
        protoInputStream.end(start);
        return arrayList;
    }

    private static void loadUsageStats(android.util.proto.ProtoInputStream protoInputStream, long j, com.android.server.usage.IntervalStats intervalStats, java.util.List<java.lang.String> list) throws java.io.IOException {
        android.app.usage.UsageStats usageStats;
        long start = protoInputStream.start(j);
        if (protoInputStream.nextField(1120986464258L)) {
            usageStats = intervalStats.getOrCreateUsageStats(list.get(protoInputStream.readInt(1120986464258L) - 1));
        } else if (protoInputStream.nextField(1138166333441L)) {
            usageStats = intervalStats.getOrCreateUsageStats(protoInputStream.readString(1138166333441L));
        } else {
            usageStats = new android.app.usage.UsageStats();
        }
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    android.app.usage.UsageStats orCreateUsageStats = intervalStats.getOrCreateUsageStats(protoInputStream.readString(1138166333441L));
                    orCreateUsageStats.mLastTimeUsed = usageStats.mLastTimeUsed;
                    orCreateUsageStats.mTotalTimeInForeground = usageStats.mTotalTimeInForeground;
                    orCreateUsageStats.mLastEvent = usageStats.mLastEvent;
                    orCreateUsageStats.mAppLaunchCount = usageStats.mAppLaunchCount;
                    usageStats = orCreateUsageStats;
                    break;
                case 2:
                    android.app.usage.UsageStats orCreateUsageStats2 = intervalStats.getOrCreateUsageStats(list.get(protoInputStream.readInt(1120986464258L) - 1));
                    orCreateUsageStats2.mLastTimeUsed = usageStats.mLastTimeUsed;
                    orCreateUsageStats2.mTotalTimeInForeground = usageStats.mTotalTimeInForeground;
                    orCreateUsageStats2.mLastEvent = usageStats.mLastEvent;
                    orCreateUsageStats2.mAppLaunchCount = usageStats.mAppLaunchCount;
                    usageStats = orCreateUsageStats2;
                    break;
                case 3:
                    usageStats.mLastTimeUsed = intervalStats.beginTime + protoInputStream.readLong(1112396529667L);
                    break;
                case 4:
                    usageStats.mTotalTimeInForeground = protoInputStream.readLong(1112396529668L);
                    break;
                case 5:
                    usageStats.mLastEvent = protoInputStream.readInt(1120986464261L);
                    break;
                case 6:
                    usageStats.mAppLaunchCount = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    try {
                        long start2 = protoInputStream.start(2246267895815L);
                        loadChooserCounts(protoInputStream, usageStats);
                        protoInputStream.end(start2);
                        break;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to read chooser counts for " + usageStats.mPackageName, e);
                        break;
                    }
                case 8:
                    usageStats.mLastTimeForegroundServiceUsed = intervalStats.beginTime + protoInputStream.readLong(1112396529672L);
                    break;
                case 9:
                    usageStats.mTotalTimeForegroundServiceUsed = protoInputStream.readLong(1112396529673L);
                    break;
                case 10:
                    usageStats.mLastTimeVisible = intervalStats.beginTime + protoInputStream.readLong(1112396529674L);
                    break;
                case 11:
                    usageStats.mTotalTimeVisible = protoInputStream.readLong(1112396529675L);
                    break;
                case 12:
                    usageStats.mLastTimeComponentUsed = intervalStats.beginTime + protoInputStream.readLong(1112396529676L);
                    break;
            }
        }
        protoInputStream.end(start);
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

    private static void loadChooserCounts(android.util.proto.ProtoInputStream protoInputStream, android.app.usage.UsageStats usageStats) throws java.io.IOException {
        android.util.ArrayMap arrayMap;
        java.lang.String str;
        if (usageStats.mChooserCounts == null) {
            usageStats.mChooserCounts = new android.util.ArrayMap();
        }
        if (protoInputStream.nextField(1138166333441L)) {
            str = protoInputStream.readString(1138166333441L);
            arrayMap = (android.util.ArrayMap) usageStats.mChooserCounts.get(str);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap();
                usageStats.mChooserCounts.put(str, arrayMap);
            }
        } else {
            arrayMap = new android.util.ArrayMap();
            str = null;
        }
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    usageStats.mChooserCounts.put(str, arrayMap);
                    continue;
                case 3:
                    long start = protoInputStream.start(2246267895811L);
                    loadCountsForAction(protoInputStream, arrayMap);
                    protoInputStream.end(start);
                    break;
            }
        }
        if (str == null) {
            usageStats.mChooserCounts.put("", arrayMap);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        if (r0 != null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        r5.put("", java.lang.Integer.valueOf(r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        r5.put(r0, java.lang.Integer.valueOf(r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0033, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadCountsForAction(android.util.proto.ProtoInputStream protoInputStream, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) throws java.io.IOException {
        java.lang.String str = null;
        int i = 0;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    break;
                case 3:
                    i = protoInputStream.readInt(1120986464259L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
    
        if (r4 == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        r12.activeConfiguration = r3.mConfiguration;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0078, code lost:
    
        r9.end(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007b, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadConfigStats(android.util.proto.ProtoInputStream protoInputStream, long j, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException {
        android.app.usage.ConfigurationStats configurationStats;
        long start = protoInputStream.start(j);
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        boolean z = false;
        if (protoInputStream.nextField(1146756268033L)) {
            configuration.readFromProto(protoInputStream, 1146756268033L);
            configurationStats = intervalStats.getOrCreateConfigurationStats(configuration);
        } else {
            configurationStats = new android.app.usage.ConfigurationStats();
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

    private static void loadEvent(android.util.proto.ProtoInputStream protoInputStream, long j, com.android.server.usage.IntervalStats intervalStats, java.util.List<java.lang.String> list) throws java.io.IOException {
        long start = protoInputStream.start(j);
        android.app.usage.UsageEvents.Event buildEvent = intervalStats.buildEvent(protoInputStream, list);
        protoInputStream.end(start);
        if (buildEvent.mPackage == null) {
            throw new java.net.ProtocolException("no package field present");
        }
        intervalStats.events.insert(buildEvent);
    }

    private static void writeStringPool(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.usage.IntervalStats intervalStats) throws java.lang.IllegalArgumentException {
        long start = protoOutputStream.start(1146756268034L);
        int size = intervalStats.mStringCache.size();
        protoOutputStream.write(1120986464257L, size);
        for (int i = 0; i < size; i++) {
            protoOutputStream.write(2237677961218L, intervalStats.mStringCache.valueAt(i));
        }
        protoOutputStream.end(start);
    }

    private static void writeUsageStats(android.util.proto.ProtoOutputStream protoOutputStream, long j, com.android.server.usage.IntervalStats intervalStats, android.app.usage.UsageStats usageStats) throws java.lang.IllegalArgumentException {
        long start = protoOutputStream.start(j);
        int indexOf = intervalStats.mStringCache.indexOf(usageStats.mPackageName);
        if (indexOf >= 0) {
            protoOutputStream.write(1120986464258L, indexOf + 1);
        } else {
            protoOutputStream.write(1138166333441L, usageStats.mPackageName);
        }
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529667L, usageStats.mLastTimeUsed, intervalStats.beginTime);
        protoOutputStream.write(1112396529668L, usageStats.mTotalTimeInForeground);
        protoOutputStream.write(1120986464261L, usageStats.mLastEvent);
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529672L, usageStats.mLastTimeForegroundServiceUsed, intervalStats.beginTime);
        protoOutputStream.write(1112396529673L, usageStats.mTotalTimeForegroundServiceUsed);
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529674L, usageStats.mLastTimeVisible, intervalStats.beginTime);
        protoOutputStream.write(1112396529675L, usageStats.mTotalTimeVisible);
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529676L, usageStats.mLastTimeComponentUsed, intervalStats.beginTime);
        protoOutputStream.write(1120986464262L, usageStats.mAppLaunchCount);
        try {
            writeChooserCounts(protoOutputStream, usageStats);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Unable to write chooser counts for " + usageStats.mPackageName, e);
        }
        protoOutputStream.end(start);
    }

    private static void writeCountAndTime(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i, long j2) throws java.lang.IllegalArgumentException {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.end(start);
    }

    private static void writeChooserCounts(android.util.proto.ProtoOutputStream protoOutputStream, android.app.usage.UsageStats usageStats) throws java.lang.IllegalArgumentException {
        if (usageStats == null || usageStats.mChooserCounts == null || usageStats.mChooserCounts.keySet().isEmpty()) {
            return;
        }
        int size = usageStats.mChooserCounts.size();
        for (int i = 0; i < size; i++) {
            java.lang.String str = (java.lang.String) usageStats.mChooserCounts.keyAt(i);
            android.util.ArrayMap arrayMap = (android.util.ArrayMap) usageStats.mChooserCounts.valueAt(i);
            if (str != null && arrayMap != null && !arrayMap.isEmpty()) {
                long start = protoOutputStream.start(2246267895815L);
                protoOutputStream.write(1138166333441L, str);
                writeCountsForAction(protoOutputStream, arrayMap);
                protoOutputStream.end(start);
            }
        }
    }

    private static void writeCountsForAction(android.util.proto.ProtoOutputStream protoOutputStream, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) throws java.lang.IllegalArgumentException {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = arrayMap.keyAt(i);
            int intValue = arrayMap.valueAt(i).intValue();
            if (intValue > 0) {
                long start = protoOutputStream.start(2246267895811L);
                protoOutputStream.write(1138166333441L, keyAt);
                protoOutputStream.write(1120986464259L, intValue);
                protoOutputStream.end(start);
            }
        }
    }

    private static void writeConfigStats(android.util.proto.ProtoOutputStream protoOutputStream, long j, com.android.server.usage.IntervalStats intervalStats, android.app.usage.ConfigurationStats configurationStats, boolean z) throws java.lang.IllegalArgumentException {
        long start = protoOutputStream.start(j);
        configurationStats.mConfiguration.dumpDebug(protoOutputStream, 1146756268033L);
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529666L, configurationStats.mLastTimeActive, intervalStats.beginTime);
        protoOutputStream.write(1112396529667L, configurationStats.mTotalTimeActive);
        protoOutputStream.write(1120986464260L, configurationStats.mActivationCount);
        protoOutputStream.write(1133871366149L, z);
        protoOutputStream.end(start);
    }

    private static void writeEvent(android.util.proto.ProtoOutputStream protoOutputStream, long j, com.android.server.usage.IntervalStats intervalStats, android.app.usage.UsageEvents.Event event) throws java.lang.IllegalArgumentException {
        int indexOf;
        int indexOf2;
        int indexOf3;
        long start = protoOutputStream.start(j);
        int indexOf4 = intervalStats.mStringCache.indexOf(event.mPackage);
        if (indexOf4 >= 0) {
            protoOutputStream.write(1120986464258L, indexOf4 + 1);
        } else {
            protoOutputStream.write(1138166333441L, event.mPackage);
        }
        if (event.mClass != null) {
            int indexOf5 = intervalStats.mStringCache.indexOf(event.mClass);
            if (indexOf5 >= 0) {
                protoOutputStream.write(1120986464260L, indexOf5 + 1);
            } else {
                protoOutputStream.write(1138166333443L, event.mClass);
            }
        }
        com.android.server.usage.UsageStatsProtoV2.writeOffsetTimestamp(protoOutputStream, 1112396529669L, event.mTimeStamp, intervalStats.beginTime);
        protoOutputStream.write(1120986464262L, event.mFlags);
        protoOutputStream.write(1120986464263L, event.mEventType);
        protoOutputStream.write(1120986464270L, event.mInstanceId);
        if (event.mTaskRootPackage != null && (indexOf3 = intervalStats.mStringCache.indexOf(event.mTaskRootPackage)) >= 0) {
            protoOutputStream.write(1120986464271L, indexOf3 + 1);
        }
        if (event.mTaskRootClass != null && (indexOf2 = intervalStats.mStringCache.indexOf(event.mTaskRootClass)) >= 0) {
            protoOutputStream.write(1120986464272L, indexOf2 + 1);
        }
        switch (event.mEventType) {
            case 5:
                if (event.mConfiguration != null) {
                    event.mConfiguration.dumpDebug(protoOutputStream, 1146756268040L);
                    break;
                }
                break;
            case 8:
                if (event.mShortcutId != null) {
                    protoOutputStream.write(1138166333449L, event.mShortcutId);
                    break;
                }
                break;
            case 11:
                if (event.mBucketAndReason != 0) {
                    protoOutputStream.write(1120986464267L, event.mBucketAndReason);
                    break;
                }
                break;
            case 12:
                if (event.mNotificationChannelId != null) {
                    int indexOf6 = intervalStats.mStringCache.indexOf(event.mNotificationChannelId);
                    if (indexOf6 >= 0) {
                        protoOutputStream.write(1120986464269L, indexOf6 + 1);
                        break;
                    } else {
                        protoOutputStream.write(1138166333452L, event.mNotificationChannelId);
                        break;
                    }
                }
                break;
            case 30:
                if (event.mLocusId != null && (indexOf = intervalStats.mStringCache.indexOf(event.mLocusId)) >= 0) {
                    protoOutputStream.write(1120986464273L, indexOf + 1);
                    break;
                }
                break;
        }
        protoOutputStream.end(start);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c4, code lost:
    
        r6.upgradeIfNeeded();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c7, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void read(java.io.InputStream inputStream, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        intervalStats.packageStats.clear();
        intervalStats.configurations.clear();
        java.util.List<java.lang.String> list = null;
        intervalStats.activeConfiguration = null;
        intervalStats.events.clear();
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 1:
                    intervalStats.endTime = intervalStats.beginTime + protoInputStream.readLong(1112396529665L);
                    break;
                case 2:
                    try {
                        list = readStringPool(protoInputStream);
                        intervalStats.mStringCache.addAll(list);
                        break;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to read string pool from proto.", e);
                        break;
                    }
                case 3:
                    intervalStats.majorVersion = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    intervalStats.minorVersion = protoInputStream.readInt(1120986464260L);
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
                        loadUsageStats(protoInputStream, 2246267895828L, intervalStats, list);
                        break;
                    } catch (java.io.IOException e2) {
                        android.util.Slog.e(TAG, "Unable to read some usage stats from proto.", e2);
                        break;
                    }
                case 21:
                    try {
                        loadConfigStats(protoInputStream, 2246267895829L, intervalStats);
                        break;
                    } catch (java.io.IOException e3) {
                        android.util.Slog.e(TAG, "Unable to read some configuration stats from proto.", e3);
                        break;
                    }
                case 22:
                    try {
                        loadEvent(protoInputStream, 2246267895830L, intervalStats, list);
                        break;
                    } catch (java.io.IOException e4) {
                        android.util.Slog.e(TAG, "Unable to read some events from proto.", e4);
                        break;
                    }
            }
        }
    }

    public static void write(java.io.OutputStream outputStream, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException, java.lang.IllegalArgumentException {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        protoOutputStream.write(1112396529665L, com.android.server.usage.UsageStatsProtoV2.getOffsetTimestamp(intervalStats.endTime, intervalStats.beginTime));
        protoOutputStream.write(1120986464259L, intervalStats.majorVersion);
        protoOutputStream.write(1120986464260L, intervalStats.minorVersion);
        try {
            writeStringPool(protoOutputStream, intervalStats);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Unable to write string pool to proto.", e);
        }
        try {
            writeCountAndTime(protoOutputStream, 1146756268042L, intervalStats.interactiveTracker.count, intervalStats.interactiveTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268043L, intervalStats.nonInteractiveTracker.count, intervalStats.nonInteractiveTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268044L, intervalStats.keyguardShownTracker.count, intervalStats.keyguardShownTracker.duration);
            writeCountAndTime(protoOutputStream, 1146756268045L, intervalStats.keyguardHiddenTracker.count, intervalStats.keyguardHiddenTracker.duration);
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.e(TAG, "Unable to write some interval stats trackers to proto.", e2);
        }
        int size = intervalStats.packageStats.size();
        for (int i = 0; i < size; i++) {
            try {
                writeUsageStats(protoOutputStream, 2246267895828L, intervalStats, intervalStats.packageStats.valueAt(i));
            } catch (java.lang.IllegalArgumentException e3) {
                android.util.Slog.e(TAG, "Unable to write some usage stats to proto.", e3);
            }
        }
        int size2 = intervalStats.configurations.size();
        for (int i2 = 0; i2 < size2; i2++) {
            try {
                writeConfigStats(protoOutputStream, 2246267895829L, intervalStats, intervalStats.configurations.valueAt(i2), intervalStats.activeConfiguration.equals(intervalStats.configurations.keyAt(i2)));
            } catch (java.lang.IllegalArgumentException e4) {
                android.util.Slog.e(TAG, "Unable to write some configuration stats to proto.", e4);
            }
        }
        int size3 = intervalStats.events.size();
        for (int i3 = 0; i3 < size3; i3++) {
            try {
                writeEvent(protoOutputStream, 2246267895830L, intervalStats, intervalStats.events.get(i3));
            } catch (java.lang.IllegalArgumentException e5) {
                android.util.Slog.e(TAG, "Unable to write some events to proto.", e5);
            }
        }
        protoOutputStream.flush();
    }
}
