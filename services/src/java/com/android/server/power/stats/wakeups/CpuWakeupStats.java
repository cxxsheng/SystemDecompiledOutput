package com.android.server.power.stats.wakeups;

/* loaded from: classes2.dex */
public class CpuWakeupStats {
    private static final java.lang.String SUBSYSTEM_ALARM_STRING = "Alarm";
    private static final java.lang.String SUBSYSTEM_CELLULAR_DATA_STRING = "Cellular_data";
    private static final java.lang.String SUBSYSTEM_SENSOR_STRING = "Sensor";
    private static final java.lang.String SUBSYSTEM_SOUND_TRIGGER_STRING = "Sound_trigger";
    private static final java.lang.String SUBSYSTEM_WIFI_STRING = "Wifi";
    private static final java.lang.String TAG = "CpuWakeupStats";
    private static final java.lang.String TRACE_TRACK_WAKEUP_ATTRIBUTION = "wakeup_attribution";
    private static final long WAKEUP_WRITE_DELAY_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(30);
    private final android.os.Handler mHandler;
    private final com.android.server.power.stats.wakeups.IrqDeviceMap mIrqDeviceMap;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.power.stats.wakeups.CpuWakeupStats.Config mConfig = new com.android.server.power.stats.wakeups.CpuWakeupStats.Config();

    @com.android.internal.annotations.VisibleForTesting
    final android.util.LongSparseArray<com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup> mWakeupEvents = new android.util.LongSparseArray<>();

    @com.android.internal.annotations.VisibleForTesting
    final android.util.LongSparseArray<android.util.SparseArray<android.util.SparseIntArray>> mWakeupAttribution = new android.util.LongSparseArray<>();
    final android.util.SparseIntArray mUidProcStates = new android.util.SparseIntArray();
    private final android.util.SparseIntArray mReusableUidProcStates = new android.util.SparseIntArray(4);
    private final com.android.server.power.stats.wakeups.CpuWakeupStats.WakingActivityHistory mRecentWakingActivity = new com.android.server.power.stats.wakeups.CpuWakeupStats.WakingActivityHistory(new java.util.function.LongSupplier() { // from class: com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda1
        @Override // java.util.function.LongSupplier
        public final long getAsLong() {
            long lambda$new$0;
            lambda$new$0 = com.android.server.power.stats.wakeups.CpuWakeupStats.this.lambda$new$0();
            return lambda$new$0;
        }
    });

    public CpuWakeupStats(android.content.Context context, int i, android.os.Handler handler) {
        this.mIrqDeviceMap = com.android.server.power.stats.wakeups.IrqDeviceMap.getInstance(context, i);
        this.mHandler = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ long lambda$new$0() {
        return this.mConfig.WAKING_ACTIVITY_RETENTION_MS;
    }

    public synchronized void systemServicesReady() {
        this.mConfig.register(new android.os.HandlerExecutor(this.mHandler));
    }

    private static int typeToStatsType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int subsystemToStatsReason(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: logWakeupAttribution, reason: merged with bridge method [inline-methods] */
    public synchronized void lambda$noteWakeupTimeAndReason$1(com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup wakeup) {
        int[] iArr;
        int[] iArr2;
        if (com.android.internal.util.ArrayUtils.isEmpty(wakeup.mDevices)) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.KERNEL_WAKEUP_ATTRIBUTED, 0, 0, (int[]) null, wakeup.mElapsedMillis, (int[]) null);
            android.os.Trace.instantForTrack(131072L, TRACE_TRACK_WAKEUP_ATTRIBUTION, wakeup.mElapsedMillis + " --");
            return;
        }
        android.util.SparseArray<android.util.SparseIntArray> sparseArray = this.mWakeupAttribution.get(wakeup.mElapsedMillis);
        if (sparseArray == null) {
            android.util.Slog.wtf(TAG, "Unexpected null attribution found for " + wakeup);
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            android.util.SparseIntArray valueAt = sparseArray.valueAt(i);
            if (valueAt == null || valueAt.size() == 0) {
                iArr = new int[0];
                iArr2 = iArr;
            } else {
                int size = valueAt.size();
                iArr = new int[size];
                int[] iArr3 = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = valueAt.keyAt(i2);
                    iArr3[i2] = android.app.ActivityManager.processStateAmToProto(valueAt.valueAt(i2));
                }
                iArr2 = iArr3;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.KERNEL_WAKEUP_ATTRIBUTED, typeToStatsType(wakeup.mType), subsystemToStatsReason(keyAt), iArr, wakeup.mElapsedMillis, iArr2);
            if (android.os.Trace.isTagEnabled(131072L)) {
                if (i == 0) {
                    sb.append(wakeup.mElapsedMillis + " ");
                }
                sb.append(subsystemToString(keyAt));
                sb.append(":");
                sb.append(java.util.Arrays.toString(iArr));
                sb.append(" ");
            }
        }
        android.os.Trace.instantForTrack(131072L, TRACE_TRACK_WAKEUP_ATTRIBUTION, sb.toString().trim());
    }

    public synchronized void onUidRemoved(int i) {
        this.mUidProcStates.delete(i);
    }

    public synchronized void noteUidProcessState(int i, int i2) {
        this.mUidProcStates.put(i, i2);
    }

    public synchronized void noteWakeupTimeAndReason(long j, long j2, java.lang.String str) {
        try {
            final com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup parseWakeup = com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.parseWakeup(str, j, j2, this.mIrqDeviceMap);
            if (parseWakeup == null) {
                return;
            }
            this.mWakeupEvents.put(j, parseWakeup);
            attemptAttributionFor(parseWakeup);
            long j3 = j - this.mConfig.WAKEUP_STATS_RETENTION_MS;
            for (int lastIndexOnOrBefore = this.mWakeupEvents.lastIndexOnOrBefore(j3); lastIndexOnOrBefore >= 0; lastIndexOnOrBefore--) {
                this.mWakeupEvents.removeAt(lastIndexOnOrBefore);
            }
            for (int lastIndexOnOrBefore2 = this.mWakeupAttribution.lastIndexOnOrBefore(j3); lastIndexOnOrBefore2 >= 0; lastIndexOnOrBefore2--) {
                this.mWakeupAttribution.removeAt(lastIndexOnOrBefore2);
            }
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.power.stats.wakeups.CpuWakeupStats$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.stats.wakeups.CpuWakeupStats.this.lambda$noteWakeupTimeAndReason$1(parseWakeup);
                }
            }, WAKEUP_WRITE_DELAY_MS);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void noteWakingActivity(int i, long j, int... iArr) {
        if (iArr == null) {
            return;
        }
        try {
            this.mReusableUidProcStates.clear();
            for (int i2 = 0; i2 < iArr.length; i2++) {
                this.mReusableUidProcStates.put(iArr[i2], this.mUidProcStates.get(iArr[i2], -1));
            }
            if (!attemptAttributionWith(i, j, this.mReusableUidProcStates)) {
                this.mRecentWakingActivity.recordActivity(i, j, this.mReusableUidProcStates);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized void attemptAttributionFor(com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup wakeup) {
        try {
            android.util.SparseBooleanArray sparseBooleanArray = wakeup.mResponsibleSubsystems;
            android.util.SparseArray<android.util.SparseIntArray> sparseArray = this.mWakeupAttribution.get(wakeup.mElapsedMillis);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mWakeupAttribution.put(wakeup.mElapsedMillis, sparseArray);
            }
            long j = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
            for (int i = 0; i < sparseBooleanArray.size(); i++) {
                int keyAt = sparseBooleanArray.keyAt(i);
                sparseArray.put(keyAt, this.mRecentWakingActivity.removeBetween(keyAt, wakeup.mElapsedMillis - j, wakeup.mElapsedMillis + j));
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized boolean attemptAttributionWith(int i, long j, android.util.SparseIntArray sparseIntArray) {
        try {
            long j2 = this.mConfig.WAKEUP_MATCHING_WINDOW_MS;
            int firstIndexOnOrAfter = this.mWakeupEvents.firstIndexOnOrAfter(j - j2);
            int lastIndexOnOrBefore = this.mWakeupEvents.lastIndexOnOrBefore(j + j2);
            while (true) {
                if (firstIndexOnOrAfter > lastIndexOnOrBefore) {
                    return false;
                }
                com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup valueAt = this.mWakeupEvents.valueAt(firstIndexOnOrAfter);
                if (!valueAt.mResponsibleSubsystems.get(i)) {
                    firstIndexOnOrAfter++;
                } else {
                    android.util.SparseArray<android.util.SparseIntArray> sparseArray = this.mWakeupAttribution.get(valueAt.mElapsedMillis);
                    if (sparseArray == null) {
                        sparseArray = new android.util.SparseArray<>();
                        this.mWakeupAttribution.put(valueAt.mElapsedMillis, sparseArray);
                    }
                    android.util.SparseIntArray sparseIntArray2 = sparseArray.get(i);
                    if (sparseIntArray2 == null) {
                        sparseArray.put(i, sparseIntArray.clone());
                    } else {
                        for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                            sparseIntArray2.put(sparseIntArray.keyAt(i2), sparseIntArray.valueAt(i2));
                        }
                    }
                    return true;
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
        try {
            indentingPrintWriter.println("CPU wakeup stats:");
            indentingPrintWriter.increaseIndent();
            this.mConfig.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            this.mIrqDeviceMap.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            this.mRecentWakingActivity.dump(indentingPrintWriter, j);
            indentingPrintWriter.println();
            indentingPrintWriter.println("Current proc-state map (" + this.mUidProcStates.size() + "):");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mUidProcStates.size(); i++) {
                if (i > 0) {
                    indentingPrintWriter.print(", ");
                }
                android.os.UserHandle.formatUid(indentingPrintWriter, this.mUidProcStates.keyAt(i));
                indentingPrintWriter.print(":" + android.app.ActivityManager.procStateToString(this.mUidProcStates.valueAt(i)));
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
            indentingPrintWriter.println("Wakeup events:");
            indentingPrintWriter.increaseIndent();
            for (int size = this.mWakeupEvents.size() - 1; size >= 0; size--) {
                android.util.TimeUtils.formatDuration(this.mWakeupEvents.keyAt(size), j, indentingPrintWriter);
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup valueAt = this.mWakeupEvents.valueAt(size);
                indentingPrintWriter.println(valueAt);
                indentingPrintWriter.print("Attribution: ");
                android.util.SparseArray<android.util.SparseIntArray> sparseArray = this.mWakeupAttribution.get(valueAt.mElapsedMillis);
                if (sparseArray == null) {
                    indentingPrintWriter.println("N/A");
                } else {
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        if (i2 > 0) {
                            indentingPrintWriter.print(", ");
                        }
                        long j2 = sparseLongArray.get(sparseArray.keyAt(i2), com.android.internal.util.IntPair.of(0, 0));
                        int first = com.android.internal.util.IntPair.first(j2);
                        int second = com.android.internal.util.IntPair.second(j2) + 1;
                        indentingPrintWriter.print(subsystemToString(sparseArray.keyAt(i2)));
                        indentingPrintWriter.print(" [");
                        android.util.SparseIntArray valueAt2 = sparseArray.valueAt(i2);
                        if (valueAt2 != null) {
                            for (int i3 = 0; i3 < valueAt2.size(); i3++) {
                                if (i3 > 0) {
                                    indentingPrintWriter.print(", ");
                                }
                                android.os.UserHandle.formatUid(indentingPrintWriter, valueAt2.keyAt(i3));
                                indentingPrintWriter.print(" " + android.app.ActivityManager.procStateToString(valueAt2.valueAt(i3)));
                            }
                            first++;
                        }
                        indentingPrintWriter.print("]");
                        sparseLongArray.put(sparseArray.keyAt(i2), com.android.internal.util.IntPair.of(first, second));
                    }
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Attribution stats:");
            indentingPrintWriter.increaseIndent();
            for (int i4 = 0; i4 < sparseLongArray.size(); i4++) {
                indentingPrintWriter.print("Subsystem " + subsystemToString(sparseLongArray.keyAt(i4)));
                indentingPrintWriter.print(": ");
                long valueAt3 = sparseLongArray.valueAt(i4);
                indentingPrintWriter.println(com.android.internal.util.IntPair.first(valueAt3) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + com.android.internal.util.IntPair.second(valueAt3));
            }
            indentingPrintWriter.println("Total: " + this.mWakeupEvents.size());
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class WakingActivityHistory {
        private java.util.function.LongSupplier mRetentionSupplier;

        @com.android.internal.annotations.VisibleForTesting
        final android.util.SparseArray<android.util.LongSparseArray<android.util.SparseIntArray>> mWakingActivity = new android.util.SparseArray<>();

        WakingActivityHistory(java.util.function.LongSupplier longSupplier) {
            this.mRetentionSupplier = longSupplier;
        }

        void recordActivity(int i, long j, android.util.SparseIntArray sparseIntArray) {
            if (sparseIntArray == null) {
                return;
            }
            android.util.LongSparseArray<android.util.SparseIntArray> longSparseArray = this.mWakingActivity.get(i);
            if (longSparseArray == null) {
                longSparseArray = new android.util.LongSparseArray<>();
                this.mWakingActivity.put(i, longSparseArray);
            }
            android.util.SparseIntArray sparseIntArray2 = longSparseArray.get(j);
            if (sparseIntArray2 == null) {
                longSparseArray.put(j, sparseIntArray.clone());
            } else {
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    int keyAt = sparseIntArray.keyAt(i2);
                    if (sparseIntArray2.indexOfKey(keyAt) < 0) {
                        sparseIntArray2.put(keyAt, sparseIntArray.valueAt(i2));
                    }
                }
            }
            for (int lastIndexOnOrBefore = longSparseArray.lastIndexOnOrBefore(j - this.mRetentionSupplier.getAsLong()); lastIndexOnOrBefore >= 0; lastIndexOnOrBefore--) {
                longSparseArray.removeAt(lastIndexOnOrBefore);
            }
        }

        android.util.SparseIntArray removeBetween(int i, long j, long j2) {
            android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
            android.util.LongSparseArray<android.util.SparseIntArray> longSparseArray = this.mWakingActivity.get(i);
            if (longSparseArray != null) {
                int firstIndexOnOrAfter = longSparseArray.firstIndexOnOrAfter(j);
                int lastIndexOnOrBefore = longSparseArray.lastIndexOnOrBefore(j2);
                for (int i2 = lastIndexOnOrBefore; i2 >= firstIndexOnOrAfter; i2--) {
                    android.util.SparseIntArray valueAt = longSparseArray.valueAt(i2);
                    for (int i3 = 0; i3 < valueAt.size(); i3++) {
                        sparseIntArray.put(valueAt.keyAt(i3), valueAt.valueAt(i3));
                    }
                }
                while (lastIndexOnOrBefore >= firstIndexOnOrAfter) {
                    longSparseArray.removeAt(lastIndexOnOrBefore);
                    lastIndexOnOrBefore--;
                }
            }
            if (sparseIntArray.size() > 0) {
                return sparseIntArray;
            }
            return null;
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.println("Recent waking activity:");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mWakingActivity.size(); i++) {
                indentingPrintWriter.println("Subsystem " + com.android.server.power.stats.wakeups.CpuWakeupStats.subsystemToString(this.mWakingActivity.keyAt(i)) + ":");
                android.util.LongSparseArray<android.util.SparseIntArray> valueAt = this.mWakingActivity.valueAt(i);
                if (valueAt != null) {
                    indentingPrintWriter.increaseIndent();
                    for (int size = valueAt.size() - 1; size >= 0; size--) {
                        android.util.TimeUtils.formatDuration(valueAt.keyAt(size), j, indentingPrintWriter);
                        android.util.SparseIntArray valueAt2 = valueAt.valueAt(size);
                        if (valueAt2 == null) {
                            indentingPrintWriter.println();
                        } else {
                            indentingPrintWriter.print(": ");
                            for (int i2 = 0; i2 < valueAt2.size(); i2++) {
                                android.os.UserHandle.formatUid(indentingPrintWriter, valueAt2.keyAt(i2));
                                indentingPrintWriter.print(" [" + android.app.ActivityManager.procStateToString(valueAt2.valueAt(i2)));
                                indentingPrintWriter.print("], ");
                            }
                            indentingPrintWriter.println();
                        }
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static int stringToKnownSubsystem(java.lang.String str) {
        boolean z;
        switch (str.hashCode()) {
            case -1822081062:
                if (str.equals(SUBSYSTEM_SENSOR_STRING)) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case -1102294721:
                if (str.equals(SUBSYSTEM_CELLULAR_DATA_STRING)) {
                    z = 4;
                    break;
                }
                z = -1;
                break;
            case -424380824:
                if (str.equals(SUBSYSTEM_SOUND_TRIGGER_STRING)) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 2695989:
                if (str.equals(SUBSYSTEM_WIFI_STRING)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 63343153:
                if (str.equals(SUBSYSTEM_ALARM_STRING)) {
                    z = false;
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
                return 1;
            case true:
                return 2;
            case true:
                return 3;
            case true:
                return 4;
            case true:
                return 5;
            default:
                return -1;
        }
    }

    static java.lang.String subsystemToString(int i) {
        switch (i) {
            case -1:
                return "Unknown";
            case 0:
            default:
                return "N/A";
            case 1:
                return SUBSYSTEM_ALARM_STRING;
            case 2:
                return SUBSYSTEM_WIFI_STRING;
            case 3:
                return SUBSYSTEM_SOUND_TRIGGER_STRING;
            case 4:
                return SUBSYSTEM_SENSOR_STRING;
            case 5:
                return SUBSYSTEM_CELLULAR_DATA_STRING;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class Wakeup {
        private static final java.lang.String ABORT_REASON_PREFIX = "Abort";
        private static final java.lang.String PARSER_TAG = "CpuWakeupStats.Wakeup";
        static final int TYPE_ABNORMAL = 2;
        static final int TYPE_IRQ = 1;
        private static final java.util.regex.Pattern sIrqPattern = java.util.regex.Pattern.compile("^(\\-?\\d+)\\s+(\\S+)");
        com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice[] mDevices;
        long mElapsedMillis;
        android.util.SparseBooleanArray mResponsibleSubsystems;
        int mType;
        long mUptimeMillis;

        private Wakeup(int i, com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice[] irqDeviceArr, long j, long j2, android.util.SparseBooleanArray sparseBooleanArray) {
            this.mType = i;
            this.mDevices = irqDeviceArr;
            this.mElapsedMillis = j;
            this.mUptimeMillis = j2;
            this.mResponsibleSubsystems = sparseBooleanArray;
        }

        static com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup parseWakeup(java.lang.String str, long j, long j2, com.android.server.power.stats.wakeups.IrqDeviceMap irqDeviceMap) {
            boolean z;
            java.lang.String[] split = str.split(":");
            if (com.android.internal.util.ArrayUtils.isEmpty(split) || split[0].startsWith(ABORT_REASON_PREFIX)) {
                return null;
            }
            com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice[] irqDeviceArr = new com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice[split.length];
            android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
            int i = 0;
            int i2 = 1;
            for (java.lang.String str2 : split) {
                java.util.regex.Matcher matcher = sIrqPattern.matcher(str2.trim());
                if (matcher.find()) {
                    try {
                        int parseInt = java.lang.Integer.parseInt(matcher.group(1));
                        java.lang.String group = matcher.group(2);
                        if (parseInt < 0) {
                            i2 = 2;
                        }
                        int i3 = i + 1;
                        irqDeviceArr[i] = new com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice(parseInt, group);
                        java.util.List<java.lang.String> subsystemsForDevice = irqDeviceMap.getSubsystemsForDevice(group);
                        if (subsystemsForDevice == null) {
                            z = false;
                        } else {
                            z = false;
                            for (int i4 = 0; i4 < subsystemsForDevice.size(); i4++) {
                                int stringToKnownSubsystem = com.android.server.power.stats.wakeups.CpuWakeupStats.stringToKnownSubsystem(subsystemsForDevice.get(i4));
                                if (stringToKnownSubsystem != -1) {
                                    sparseBooleanArray.put(stringToKnownSubsystem, true);
                                    z = true;
                                }
                            }
                        }
                        if (!z) {
                            sparseBooleanArray.put(-1, true);
                        }
                        i = i3;
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(PARSER_TAG, "Exception while parsing device names from part: " + str2, e);
                    }
                }
            }
            if (i == 0) {
                return null;
            }
            if (sparseBooleanArray.size() == 1 && sparseBooleanArray.get(-1, false)) {
                return null;
            }
            return new com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup(i2, (com.android.server.power.stats.wakeups.CpuWakeupStats.Wakeup.IrqDevice[]) java.util.Arrays.copyOf(irqDeviceArr, i), j, j2, sparseBooleanArray);
        }

        public java.lang.String toString() {
            return "Wakeup{mType=" + this.mType + ", mElapsedMillis=" + this.mElapsedMillis + ", mUptimeMillis=" + this.mUptimeMillis + ", mDevices=" + java.util.Arrays.toString(this.mDevices) + ", mResponsibleSubsystems=" + this.mResponsibleSubsystems + '}';
        }

        static final class IrqDevice {
            java.lang.String mDevice;
            int mLine;

            IrqDevice(int i, java.lang.String str) {
                this.mLine = i;
                this.mDevice = str;
            }

            public java.lang.String toString() {
                return "IrqDevice{mLine=" + this.mLine + ", mDevice='" + this.mDevice + "'}";
            }
        }
    }

    static final class Config implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        static final java.lang.String KEY_WAKEUP_STATS_RETENTION_MS = "wakeup_stats_retention_ms";
        static final java.lang.String KEY_WAKEUP_MATCHING_WINDOW_MS = "wakeup_matching_window_ms";
        static final java.lang.String KEY_WAKING_ACTIVITY_RETENTION_MS = "waking_activity_retention_ms";
        private static final java.lang.String[] PROPERTY_NAMES = {KEY_WAKEUP_STATS_RETENTION_MS, KEY_WAKEUP_MATCHING_WINDOW_MS, KEY_WAKING_ACTIVITY_RETENTION_MS};
        static final long DEFAULT_WAKEUP_STATS_RETENTION_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(3);
        private static final long DEFAULT_WAKEUP_MATCHING_WINDOW_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(1);
        private static final long DEFAULT_WAKING_ACTIVITY_RETENTION_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(5);
        public volatile long WAKEUP_STATS_RETENTION_MS = DEFAULT_WAKEUP_STATS_RETENTION_MS;
        public volatile long WAKEUP_MATCHING_WINDOW_MS = DEFAULT_WAKEUP_MATCHING_WINDOW_MS;
        public volatile long WAKING_ACTIVITY_RETENTION_MS = DEFAULT_WAKING_ACTIVITY_RETENTION_MS;

        Config() {
        }

        @android.annotation.SuppressLint({"MissingPermission"})
        void register(java.util.concurrent.Executor executor) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("battery_stats", executor, this);
            onPropertiesChanged(android.provider.DeviceConfig.getProperties("battery_stats", PROPERTY_NAMES));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            char c;
            for (java.lang.String str : properties.getKeyset()) {
                if (str != null) {
                    switch (str.hashCode()) {
                        case 241713043:
                            if (str.equals(KEY_WAKEUP_MATCHING_WINDOW_MS)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 588912391:
                            if (str.equals(KEY_WAKEUP_STATS_RETENTION_MS)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1049257273:
                            if (str.equals(KEY_WAKING_ACTIVITY_RETENTION_MS)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            this.WAKEUP_STATS_RETENTION_MS = properties.getLong(KEY_WAKEUP_STATS_RETENTION_MS, DEFAULT_WAKEUP_STATS_RETENTION_MS);
                            break;
                        case 1:
                            this.WAKEUP_MATCHING_WINDOW_MS = properties.getLong(KEY_WAKEUP_MATCHING_WINDOW_MS, DEFAULT_WAKEUP_MATCHING_WINDOW_MS);
                            break;
                        case 2:
                            this.WAKING_ACTIVITY_RETENTION_MS = properties.getLong(KEY_WAKING_ACTIVITY_RETENTION_MS, DEFAULT_WAKING_ACTIVITY_RETENTION_MS);
                            break;
                    }
                }
            }
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Config:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_WAKEUP_STATS_RETENTION_MS);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.WAKEUP_STATS_RETENTION_MS, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_WAKEUP_MATCHING_WINDOW_MS);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.WAKEUP_MATCHING_WINDOW_MS, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_WAKING_ACTIVITY_RETENTION_MS);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.WAKING_ACTIVITY_RETENTION_MS, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }
}
