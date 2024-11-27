package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsExporter {
    private static final long BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(2);
    private static final java.lang.String TAG = "PowerStatsExporter";
    private final long mBatterySessionTimeSpanSlackMillis;
    private final com.android.server.power.stats.PowerStatsAggregator mPowerStatsAggregator;
    private final com.android.server.power.stats.PowerStatsStore mPowerStatsStore;

    public PowerStatsExporter(com.android.server.power.stats.PowerStatsStore powerStatsStore, com.android.server.power.stats.PowerStatsAggregator powerStatsAggregator) {
        this(powerStatsStore, powerStatsAggregator, BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS);
    }

    public PowerStatsExporter(com.android.server.power.stats.PowerStatsStore powerStatsStore, com.android.server.power.stats.PowerStatsAggregator powerStatsAggregator, long j) {
        this.mPowerStatsStore = powerStatsStore;
        this.mPowerStatsAggregator = powerStatsAggregator;
        this.mBatterySessionTimeSpanSlackMillis = j;
    }

    public void exportAggregatedPowerStats(final android.os.BatteryUsageStats.Builder builder, long j, long j2) {
        java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> list;
        int i;
        java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> tableOfContents = this.mPowerStatsStore.getTableOfContents();
        int size = tableOfContents.size() - 1;
        long j3 = j;
        boolean z = false;
        while (size >= 0) {
            com.android.server.power.stats.PowerStatsSpan.Metadata metadata = tableOfContents.get(size);
            java.util.List<java.lang.String> sections = metadata.getSections();
            java.lang.String str = com.android.server.power.stats.AggregatedPowerStatsSection.TYPE;
            if (!sections.contains(com.android.server.power.stats.AggregatedPowerStatsSection.TYPE)) {
                list = tableOfContents;
                i = size;
            } else {
                java.util.List<com.android.server.power.stats.PowerStatsSpan.TimeFrame> timeFrames = metadata.getTimeFrames();
                long j4 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                long j5 = Long.MIN_VALUE;
                int i2 = 0;
                while (i2 < timeFrames.size()) {
                    com.android.server.power.stats.PowerStatsSpan.TimeFrame timeFrame = timeFrames.get(i2);
                    java.util.List<com.android.server.power.stats.PowerStatsSpan.Metadata> list2 = tableOfContents;
                    int i3 = size;
                    long j6 = timeFrame.startMonotonicTime;
                    java.util.List<com.android.server.power.stats.PowerStatsSpan.TimeFrame> list3 = timeFrames;
                    java.lang.String str2 = str;
                    long j7 = timeFrame.duration + j6;
                    if (j6 < j4) {
                        j4 = j6;
                    }
                    if (j7 > j5) {
                        j5 = j7;
                    }
                    i2++;
                    tableOfContents = list2;
                    size = i3;
                    str = str2;
                    timeFrames = list3;
                }
                list = tableOfContents;
                i = size;
                java.lang.String str3 = str;
                if (j4 >= j && j5 < j2) {
                    if (j5 > j3) {
                        j3 = j5;
                    }
                    com.android.server.power.stats.PowerStatsSpan loadPowerStatsSpan = this.mPowerStatsStore.loadPowerStatsSpan(metadata.getId(), str3);
                    if (loadPowerStatsSpan == null) {
                        android.util.Slog.e(TAG, "Could not read PowerStatsStore section " + metadata);
                    } else {
                        java.util.List<com.android.server.power.stats.PowerStatsSpan.Section> sections2 = loadPowerStatsSpan.getSections();
                        int i4 = 0;
                        while (i4 < sections2.size()) {
                            lambda$exportAggregatedPowerStats$0(builder, ((com.android.server.power.stats.AggregatedPowerStatsSection) sections2.get(i4)).getAggregatedPowerStats());
                            i4++;
                            z = true;
                        }
                    }
                }
            }
            size = i - 1;
            tableOfContents = list;
        }
        if (!z || j3 < j2 - this.mBatterySessionTimeSpanSlackMillis) {
            this.mPowerStatsAggregator.aggregatePowerStats(j3, j2, new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsExporter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.power.stats.PowerStatsExporter.this.lambda$exportAggregatedPowerStats$0(builder, (com.android.server.power.stats.AggregatedPowerStats) obj);
                }
            });
        }
        this.mPowerStatsAggregator.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: populateBatteryUsageStatsBuilder, reason: merged with bridge method [inline-methods] */
    public void lambda$exportAggregatedPowerStats$0(android.os.BatteryUsageStats.Builder builder, com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        java.util.List<com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent> powerComponentsAggregatedStatsConfigs = this.mPowerStatsAggregator.getConfig().getPowerComponentsAggregatedStatsConfigs();
        for (int size = powerComponentsAggregatedStatsConfigs.size() - 1; size >= 0; size--) {
            populateBatteryUsageStatsBuilder(builder, aggregatedPowerStats, powerComponentsAggregatedStatsConfigs.get(size));
        }
    }

    private void populateBatteryUsageStatsBuilder(android.os.BatteryUsageStats.Builder builder, com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats, com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent powerComponent) {
        com.android.internal.os.PowerStats.Descriptor powerStatsDescriptor;
        boolean z;
        android.os.BatteryUsageStats.Builder builder2 = builder;
        int powerComponentId = powerComponent.getPowerComponentId();
        final com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentStats = aggregatedPowerStats.getPowerComponentStats(powerComponentId);
        if (powerComponentStats == null || (powerStatsDescriptor = powerComponentStats.getPowerStatsDescriptor()) == null) {
            return;
        }
        final com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout statsArrayLayout = new com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout();
        statsArrayLayout.fromExtras(powerStatsDescriptor.extras);
        final long[] jArr = new long[powerStatsDescriptor.statsArrayLength];
        final double[] dArr = new double[1];
        com.android.server.power.stats.MultiStateStats.States.forEachTrackedStateCombination(powerComponent.getDeviceStateConfig(), new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsExporter$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.stats.PowerStatsExporter.lambda$populateBatteryUsageStatsBuilder$1(com.android.server.power.stats.PowerComponentAggregatedPowerStats.this, jArr, dArr, statsArrayLayout, (int[]) obj);
            }
        });
        int i = 0;
        builder2.getAggregateBatteryConsumerBuilder(0).addConsumedPower(powerComponentId, dArr[0], 0);
        final long[] jArr2 = new long[powerStatsDescriptor.uidStatsArrayLength];
        java.util.ArrayList arrayList = new java.util.ArrayList();
        powerComponentStats.collectUids(arrayList);
        if (builder.isProcessStateDataNeeded() && powerComponent.getUidStateConfig()[2].isTracked()) {
            z = true;
        } else {
            z = false;
        }
        int i2 = z ? 5 : 1;
        final double[] dArr2 = new double[i2];
        java.util.Iterator it = arrayList.iterator();
        double d = 0.0d;
        double d2 = 0.0d;
        while (it.hasNext()) {
            final int intValue = ((java.lang.Integer) it.next()).intValue();
            android.os.UidBatteryConsumer.Builder orCreateUidBatteryConsumerBuilder = builder2.getOrCreateUidBatteryConsumerBuilder(intValue);
            java.util.Arrays.fill(dArr2, d);
            double d3 = d2;
            double d4 = d;
            final boolean z2 = z;
            double[] dArr3 = dArr2;
            com.android.server.power.stats.MultiStateStats.States.forEachTrackedStateCombination(powerComponent.getUidStateConfig(), new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsExporter$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.power.stats.PowerStatsExporter.lambda$populateBatteryUsageStatsBuilder$2(com.android.server.power.stats.PowerComponentAggregatedPowerStats.this, jArr2, intValue, statsArrayLayout, z2, dArr2, (int[]) obj);
                }
            });
            double d5 = d4;
            for (int i3 = 0; i3 < i2; i3++) {
                double d6 = dArr3[i3];
                if (d6 != d4) {
                    d5 += d6;
                    if (z && i3 != 0) {
                        orCreateUidBatteryConsumerBuilder.addConsumedPower(orCreateUidBatteryConsumerBuilder.getKey(powerComponentId, i3), d6, 0);
                    }
                }
            }
            orCreateUidBatteryConsumerBuilder.addConsumedPower(powerComponentId, d5, 0);
            d2 = d3 + d5;
            builder2 = builder;
            i = 0;
            dArr2 = dArr3;
            d = d4;
        }
        builder.getAggregateBatteryConsumerBuilder(1).addConsumedPower(powerComponentId, d2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$populateBatteryUsageStatsBuilder$1(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long[] jArr, double[] dArr, com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout statsArrayLayout, int[] iArr) {
        if (iArr[0] == 0 && powerComponentAggregatedPowerStats.getDeviceStats(jArr, iArr)) {
            dArr[0] = dArr[0] + statsArrayLayout.getDevicePowerEstimate(jArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$populateBatteryUsageStatsBuilder$2(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long[] jArr, int i, com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout statsArrayLayout, boolean z, double[] dArr, int[] iArr) {
        int i2 = 0;
        if (iArr[0] != 0 || !powerComponentAggregatedPowerStats.getUidStats(jArr, i, iArr)) {
            return;
        }
        double uidPowerEstimate = statsArrayLayout.getUidPowerEstimate(jArr);
        if (z) {
            i2 = iArr[2];
        }
        dArr[i2] = dArr[i2] + uidPowerEstimate;
    }
}
