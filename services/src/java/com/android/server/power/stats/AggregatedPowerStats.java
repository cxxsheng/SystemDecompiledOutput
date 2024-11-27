package com.android.server.power.stats;

/* loaded from: classes2.dex */
class AggregatedPowerStats {
    private static final int MAX_CLOCK_UPDATES = 100;
    private static final java.lang.String TAG = "AggregatedPowerStats";
    private static final java.lang.String XML_TAG_AGGREGATED_POWER_STATS = "agg-power-stats";
    private final java.util.List<com.android.server.power.stats.AggregatedPowerStats.ClockUpdate> mClockUpdates = new java.util.ArrayList();
    private long mDurationMs;
    private final com.android.server.power.stats.PowerComponentAggregatedPowerStats[] mPowerComponentStats;

    static class ClockUpdate {
        public long currentTime;
        public long monotonicTime;

        ClockUpdate() {
        }
    }

    AggregatedPowerStats(com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig) {
        java.util.List<com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent> powerComponentsAggregatedStatsConfigs = aggregatedPowerStatsConfig.getPowerComponentsAggregatedStatsConfigs();
        this.mPowerComponentStats = new com.android.server.power.stats.PowerComponentAggregatedPowerStats[powerComponentsAggregatedStatsConfigs.size()];
        for (int i = 0; i < powerComponentsAggregatedStatsConfigs.size(); i++) {
            this.mPowerComponentStats[i] = new com.android.server.power.stats.PowerComponentAggregatedPowerStats(powerComponentsAggregatedStatsConfigs.get(i));
        }
    }

    void addClockUpdate(long j, long j2) {
        com.android.server.power.stats.AggregatedPowerStats.ClockUpdate clockUpdate = new com.android.server.power.stats.AggregatedPowerStats.ClockUpdate();
        clockUpdate.monotonicTime = j;
        clockUpdate.currentTime = j2;
        if (this.mClockUpdates.size() < 100) {
            this.mClockUpdates.add(clockUpdate);
            return;
        }
        android.util.Slog.i(TAG, "Too many clock updates. Replacing the previous update with " + ((java.lang.Object) android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", j2)));
        this.mClockUpdates.set(this.mClockUpdates.size() + (-1), clockUpdate);
    }

    long getStartTime() {
        if (this.mClockUpdates.isEmpty()) {
            return 0L;
        }
        return this.mClockUpdates.get(0).monotonicTime;
    }

    java.util.List<com.android.server.power.stats.AggregatedPowerStats.ClockUpdate> getClockUpdates() {
        return this.mClockUpdates;
    }

    void setDuration(long j) {
        this.mDurationMs = j;
    }

    public long getDuration() {
        return this.mDurationMs;
    }

    com.android.server.power.stats.PowerComponentAggregatedPowerStats getPowerComponentStats(int i) {
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            if (powerComponentAggregatedPowerStats.powerComponentId == i) {
                return powerComponentAggregatedPowerStats;
            }
        }
        return null;
    }

    void setDeviceState(int i, int i2, long j) {
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats.setState(i, i2, j);
        }
    }

    void setUidState(int i, int i2, int i3, long j) {
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats.setUidState(i, i2, i3, j);
        }
    }

    boolean isCompatible(com.android.internal.os.PowerStats powerStats) {
        int i = powerStats.descriptor.powerComponentId;
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            if (powerComponentAggregatedPowerStats.powerComponentId == i && !powerComponentAggregatedPowerStats.isCompatible(powerStats)) {
                return false;
            }
        }
        return true;
    }

    void addPowerStats(com.android.internal.os.PowerStats powerStats, long j) {
        int i = powerStats.descriptor.powerComponentId;
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            if (powerComponentAggregatedPowerStats.powerComponentId == i) {
                powerComponentAggregatedPowerStats.addPowerStats(powerStats, j);
            }
        }
    }

    void reset() {
        this.mClockUpdates.clear();
        this.mDurationMs = 0L;
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats.reset();
        }
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_AGGREGATED_POWER_STATS);
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats.writeXml(typedXmlSerializer);
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_AGGREGATED_POWER_STATS);
        typedXmlSerializer.flush();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.NonNull
    public static com.android.server.power.stats.AggregatedPowerStats createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        char c;
        com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats = new com.android.server.power.stats.AggregatedPowerStats(aggregatedPowerStatsConfig);
        int eventType = typedXmlPullParser.getEventType();
        boolean z = false;
        boolean z2 = false;
        while (eventType != 1 && (eventType != 3 || !typedXmlPullParser.getName().equals(XML_TAG_AGGREGATED_POWER_STATS))) {
            if (!z && eventType == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -925966781:
                        if (name.equals("power_component")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 381213451:
                        if (name.equals(XML_TAG_AGGREGATED_POWER_STATS)) {
                            c = 0;
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
                        z2 = true;
                        break;
                    case 1:
                        if (!z2) {
                            break;
                        } else {
                            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "id");
                            com.android.server.power.stats.PowerComponentAggregatedPowerStats[] powerComponentAggregatedPowerStatsArr = aggregatedPowerStats.mPowerComponentStats;
                            int length = powerComponentAggregatedPowerStatsArr.length;
                            int i = 0;
                            while (true) {
                                if (i < length) {
                                    com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats = powerComponentAggregatedPowerStatsArr[i];
                                    if (powerComponentAggregatedPowerStats.powerComponentId != attributeInt) {
                                        i++;
                                    } else if (!powerComponentAggregatedPowerStats.readFromXml(typedXmlPullParser)) {
                                        z = true;
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
            eventType = typedXmlPullParser.next();
        }
        return aggregatedPowerStats;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        long j = 0;
        for (int i = 0; i < this.mClockUpdates.size(); i++) {
            com.android.server.power.stats.AggregatedPowerStats.ClockUpdate clockUpdate = this.mClockUpdates.get(i);
            sb.setLength(0);
            if (i == 0) {
                j = clockUpdate.monotonicTime;
                sb.append("Start time: ");
                sb.append(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", clockUpdate.currentTime));
                sb.append(" (");
                sb.append(j);
                sb.append(") duration: ");
                sb.append(this.mDurationMs);
                indentingPrintWriter.println(sb);
            } else {
                sb.setLength(0);
                sb.append("Clock update:  ");
                android.util.TimeUtils.formatDuration(clockUpdate.monotonicTime - j, sb, 22);
                sb.append(" ");
                sb.append(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", clockUpdate.currentTime));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println(sb);
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.println("Device");
        indentingPrintWriter.increaseIndent();
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats.dumpDevice(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
        java.util.HashSet hashSet = new java.util.HashSet();
        for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats2 : this.mPowerComponentStats) {
            powerComponentAggregatedPowerStats2.collectUids(hashSet);
        }
        java.lang.Integer[] numArr = (java.lang.Integer[]) hashSet.toArray(new java.lang.Integer[hashSet.size()]);
        java.util.Arrays.sort(numArr);
        for (java.lang.Integer num : numArr) {
            int intValue = num.intValue();
            indentingPrintWriter.println(android.os.UserHandle.formatUid(intValue));
            indentingPrintWriter.increaseIndent();
            for (com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats3 : this.mPowerComponentStats) {
                powerComponentAggregatedPowerStats3.dumpUid(indentingPrintWriter, intValue);
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public java.lang.String toString() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        dump(new android.util.IndentingPrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
