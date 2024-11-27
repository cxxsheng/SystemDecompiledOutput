package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class BatteryUsageStatsProvider {
    private static final java.lang.String TAG = "BatteryUsageStatsProv";
    private static boolean sErrorReported;
    private final com.android.internal.os.Clock mClock;
    private final android.content.Context mContext;
    private final com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.List<com.android.server.power.stats.PowerCalculator> mPowerCalculators;
    private final com.android.internal.os.PowerProfile mPowerProfile;
    private final com.android.server.power.stats.PowerStatsExporter mPowerStatsExporter;
    private boolean mPowerStatsExporterEnabled;
    private final com.android.server.power.stats.PowerStatsStore mPowerStatsStore;

    public BatteryUsageStatsProvider(android.content.Context context, com.android.server.power.stats.PowerStatsExporter powerStatsExporter, com.android.internal.os.PowerProfile powerProfile, com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, com.android.server.power.stats.PowerStatsStore powerStatsStore, com.android.internal.os.Clock clock) {
        this.mContext = context;
        this.mPowerStatsExporter = powerStatsExporter;
        this.mPowerStatsStore = powerStatsStore;
        this.mPowerProfile = powerProfile;
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mClock = clock;
    }

    private java.util.List<com.android.server.power.stats.PowerCalculator> getPowerCalculators() {
        synchronized (this.mLock) {
            try {
                if (this.mPowerCalculators == null) {
                    this.mPowerCalculators = new java.util.ArrayList();
                    this.mPowerCalculators.add(new com.android.server.power.stats.BatteryChargeCalculator());
                    if (!this.mPowerStatsExporterEnabled) {
                        this.mPowerCalculators.add(new com.android.server.power.stats.CpuPowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile));
                    }
                    this.mPowerCalculators.add(new com.android.server.power.stats.MemoryPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.WakelockPowerCalculator(this.mPowerProfile));
                    if (!android.os.BatteryStats.checkWifiOnly(this.mContext)) {
                        this.mPowerCalculators.add(new com.android.server.power.stats.MobileRadioPowerCalculator(this.mPowerProfile));
                    }
                    this.mPowerCalculators.add(new com.android.server.power.stats.WifiPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.BluetoothPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.SensorPowerCalculator((android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class)));
                    this.mPowerCalculators.add(new com.android.server.power.stats.GnssPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.CameraPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.FlashlightPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.AudioPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.VideoPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.PhonePowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.ScreenPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.AmbientDisplayPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.IdlePowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.CustomEnergyConsumerPowerCalculator(this.mPowerProfile));
                    this.mPowerCalculators.add(new com.android.server.power.stats.UserPowerCalculator());
                    com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
                    this.mPowerCalculators.add(new com.android.server.power.stats.SystemServicePowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mPowerCalculators;
    }

    public static boolean shouldUpdateStats(java.util.List<android.os.BatteryUsageStatsQuery> list, long j, long j2) {
        long j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        for (int size = list.size() - 1; size >= 0; size--) {
            j3 = java.lang.Math.min(j3, list.get(size).getMaxStatsAge());
        }
        return j - j2 > j3;
    }

    public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, java.util.List<android.os.BatteryUsageStatsQuery> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        synchronized (batteryStatsImpl) {
            batteryStatsImpl.prepareForDumpLocked();
        }
        long currentTimeMillis = this.mClock.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(getBatteryUsageStats(batteryStatsImpl, list.get(i), currentTimeMillis));
        }
        return arrayList;
    }

    public android.os.BatteryUsageStats getBatteryUsageStats(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        return getBatteryUsageStats(batteryStatsImpl, batteryUsageStatsQuery, this.mClock.currentTimeMillis());
    }

    private android.os.BatteryUsageStats getBatteryUsageStats(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery, long j) {
        if (batteryUsageStatsQuery.getToTimestamp() == 0) {
            return getCurrentBatteryUsageStats(batteryStatsImpl, batteryUsageStatsQuery, j);
        }
        return getAggregatedBatteryUsageStats(batteryStatsImpl, batteryUsageStatsQuery);
    }

    private android.os.BatteryUsageStats getCurrentBatteryUsageStats(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery, long j) {
        long monotonicStartTime;
        long monotonicEndTime;
        char c;
        android.os.BatteryUsageStats.Builder builder;
        int i;
        int i2;
        java.util.List<com.android.server.power.stats.PowerCalculator> list;
        char c2;
        android.os.BatteryUsageStats.Builder builder2;
        boolean z;
        boolean z2;
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        long uptimeMillis = this.mClock.uptimeMillis() * 1000;
        boolean z3 = true;
        boolean z4 = (batteryUsageStatsQuery.getFlags() & 4) != 0;
        boolean z5 = (batteryUsageStatsQuery.getFlags() & 8) != 0 && batteryStatsImpl.isProcessStateDataAvailable();
        boolean z6 = (batteryUsageStatsQuery.getFlags() & 16) != 0;
        double minConsumedPowerThreshold = batteryUsageStatsQuery.getMinConsumedPowerThreshold();
        synchronized (batteryStatsImpl) {
            try {
                monotonicStartTime = batteryStatsImpl.getMonotonicStartTime();
                monotonicEndTime = batteryStatsImpl.getMonotonicEndTime();
                android.os.BatteryUsageStats.Builder builder3 = new android.os.BatteryUsageStats.Builder(batteryStatsImpl.getCustomEnergyConsumerNames(), z4, z5, minConsumedPowerThreshold);
                builder3.setStatsStartTimestamp(batteryStatsImpl.getStartClockTime());
                builder3.setStatsEndTimestamp(j);
                android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = batteryStatsImpl.getUidStats();
                int size = uidStats.size() - 1;
                while (true) {
                    c = 2;
                    if (size < 0) {
                        break;
                    }
                    android.os.BatteryStats.Uid valueAt = uidStats.valueAt(size);
                    if (!z6 && valueAt.getUid() == 1090) {
                        z2 = z3;
                    } else {
                        z2 = true;
                        builder3.getOrCreateUidBatteryConsumerBuilder(valueAt).setTimeInProcessStateMs(2, getProcessBackgroundTimeMs(valueAt, elapsedRealtime)).setTimeInProcessStateMs(1, getProcessForegroundTimeMs(valueAt, elapsedRealtime)).setTimeInProcessStateMs(3, getProcessForegroundServiceTimeMs(valueAt, elapsedRealtime));
                    }
                    size--;
                    z3 = z2;
                }
                boolean z7 = z3;
                int[] powerComponents = batteryUsageStatsQuery.getPowerComponents();
                java.util.List<com.android.server.power.stats.PowerCalculator> powerCalculators = getPowerCalculators();
                int size2 = powerCalculators.size();
                int i3 = 0;
                while (i3 < size2) {
                    com.android.server.power.stats.PowerCalculator powerCalculator = powerCalculators.get(i3);
                    if (powerComponents != null) {
                        int length = powerComponents.length;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length) {
                                z = false;
                                break;
                            }
                            if (!powerCalculator.isPowerComponentSupported(powerComponents[i4])) {
                                i4++;
                            } else {
                                z = z7;
                                break;
                            }
                        }
                        if (!z) {
                            i = size2;
                            i2 = i3;
                            list = powerCalculators;
                            builder2 = builder3;
                            c2 = 2;
                            i3 = i2 + 1;
                            builder3 = builder2;
                            size2 = i;
                            c = c2;
                            powerCalculators = list;
                            z7 = true;
                        }
                    }
                    i = size2;
                    i2 = i3;
                    list = powerCalculators;
                    c2 = 2;
                    builder2 = builder3;
                    powerCalculator.calculate(builder3, batteryStatsImpl, elapsedRealtime, uptimeMillis, batteryUsageStatsQuery);
                    i3 = i2 + 1;
                    builder3 = builder2;
                    size2 = i;
                    c = c2;
                    powerCalculators = list;
                    z7 = true;
                }
                builder = builder3;
                if ((batteryUsageStatsQuery.getFlags() & 2) != 0) {
                    builder.setBatteryHistory(batteryStatsImpl.copyHistory());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (this.mPowerStatsExporterEnabled) {
            this.mPowerStatsExporter.exportAggregatedPowerStats(builder, monotonicStartTime, monotonicEndTime);
        }
        android.os.BatteryUsageStats build = builder.build();
        if (z5) {
            verify(build);
        }
        return build;
    }

    private void verify(android.os.BatteryUsageStats batteryUsageStats) {
        if (sErrorReported) {
            return;
        }
        int[] iArr = {1, 8, 11, 2};
        int[] iArr2 = {1, 2, 3, 4};
        for (android.os.UidBatteryConsumer uidBatteryConsumer : batteryUsageStats.getUidBatteryConsumers()) {
            for (int i = 0; i < 4; i++) {
                int i2 = iArr[i];
                double consumedPower = uidBatteryConsumer.getConsumedPower(uidBatteryConsumer.getKey(i2));
                double d = 0.0d;
                for (int i3 = 0; i3 < 4; i3++) {
                    d += uidBatteryConsumer.getConsumedPower(uidBatteryConsumer.getKey(i2, iArr2[i3]));
                }
                if (d > 2.0d + consumedPower) {
                    java.lang.String str = "Sum of states exceeds total. UID = " + uidBatteryConsumer.getUid() + " " + android.os.BatteryConsumer.powerComponentIdToString(i2) + " total = " + consumedPower + " states = " + d;
                    if (!sErrorReported) {
                        android.util.Slog.wtf(TAG, str);
                        sErrorReported = true;
                        return;
                    } else {
                        android.util.Slog.e(TAG, str);
                        return;
                    }
                }
            }
        }
    }

    private long getProcessForegroundTimeMs(android.os.BatteryStats.Uid uid, long j) {
        long j2;
        long processStateTime = uid.getProcessStateTime(0, j, 0);
        android.os.BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer == null) {
            j2 = 0;
        } else {
            j2 = foregroundActivityTimer.getTotalTimeLocked(j, 0);
        }
        return (java.lang.Math.min(processStateTime, j2) + uid.getProcessStateTime(2, j, 0)) / 1000;
    }

    private long getProcessBackgroundTimeMs(android.os.BatteryStats.Uid uid, long j) {
        return uid.getProcessStateTime(3, j, 0) / 1000;
    }

    private long getProcessForegroundServiceTimeMs(android.os.BatteryStats.Uid uid, long j) {
        return uid.getProcessStateTime(1, j, 0) / 1000;
    }

    private android.os.BatteryUsageStats getAggregatedBatteryUsageStats(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        boolean z = (batteryUsageStatsQuery.getFlags() & 4) != 0;
        boolean z2 = (batteryUsageStatsQuery.getFlags() & 8) != 0 && batteryStatsImpl.isProcessStateDataAvailable();
        double minConsumedPowerThreshold = batteryUsageStatsQuery.getMinConsumedPowerThreshold();
        java.lang.String[] customEnergyConsumerNames = batteryStatsImpl.getCustomEnergyConsumerNames();
        android.os.BatteryUsageStats.Builder builder = new android.os.BatteryUsageStats.Builder(customEnergyConsumerNames, z, z2, minConsumedPowerThreshold);
        com.android.server.power.stats.PowerStatsStore powerStatsStore = this.mPowerStatsStore;
        java.lang.String str = TAG;
        if (powerStatsStore == null) {
            android.util.Log.e(TAG, "PowerStatsStore is unavailable");
            return builder.build();
        }
        java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.Metadata> it = this.mPowerStatsStore.getTableOfContents().iterator();
        while (it.hasNext()) {
            com.android.server.power.stats.PowerStatsSpan.Metadata next = it.next();
            if (next.getSections().contains(com.android.server.power.stats.BatteryUsageStatsSection.TYPE)) {
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                long j2 = 0;
                for (com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame : next.getTimeFrames()) {
                    long j3 = timeFrame.startTime + timeFrame.duration;
                    j = java.lang.Math.min(j, j3);
                    j2 = java.lang.Math.max(j2, j3);
                    str = str;
                    it = it;
                }
                java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.Metadata> it2 = it;
                java.lang.String str2 = str;
                if (!((batteryUsageStatsQuery.getFromTimestamp() == 0 || j > batteryUsageStatsQuery.getFromTimestamp()) && (batteryUsageStatsQuery.getToTimestamp() == 0 || j2 <= batteryUsageStatsQuery.getToTimestamp()))) {
                    str = str2;
                    it = it2;
                } else {
                    com.android.server.power.stats.PowerStatsSpan loadPowerStatsSpan = this.mPowerStatsStore.loadPowerStatsSpan(next.getId(), com.android.server.power.stats.BatteryUsageStatsSection.TYPE);
                    if (loadPowerStatsSpan == null) {
                        str = str2;
                        it = it2;
                    } else {
                        java.util.Iterator<com.android.server.power.stats.PowerStatsSpan.Section> it3 = loadPowerStatsSpan.getSections().iterator();
                        while (it3.hasNext()) {
                            android.os.BatteryUsageStats batteryUsageStats = ((com.android.server.power.stats.BatteryUsageStatsSection) it3.next()).getBatteryUsageStats();
                            if (!java.util.Arrays.equals(batteryUsageStats.getCustomPowerComponentNames(), customEnergyConsumerNames)) {
                                android.util.Log.w(str2, "Ignoring older BatteryUsageStats snapshot, which has different custom power components: " + java.util.Arrays.toString(batteryUsageStats.getCustomPowerComponentNames()));
                            } else {
                                java.lang.String str3 = str2;
                                if (z2 && !batteryUsageStats.isProcessStateDataIncluded()) {
                                    android.util.Log.w(str3, "Ignoring older BatteryUsageStats snapshot, which  does not include process state data");
                                    str2 = str3;
                                } else {
                                    builder.add(batteryUsageStats);
                                    str2 = str3;
                                }
                            }
                        }
                        str = str2;
                        it = it2;
                    }
                }
            }
        }
        return builder.build();
    }

    public void setPowerStatsExporterEnabled(boolean z) {
        this.mPowerStatsExporterEnabled = z;
    }
}
