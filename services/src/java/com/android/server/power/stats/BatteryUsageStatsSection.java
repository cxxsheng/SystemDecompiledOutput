package com.android.server.power.stats;

/* loaded from: classes2.dex */
class BatteryUsageStatsSection extends com.android.server.power.stats.PowerStatsSpan.Section {
    public static final java.lang.String TYPE = "battery-usage-stats";
    private final android.os.BatteryUsageStats mBatteryUsageStats;

    BatteryUsageStatsSection(android.os.BatteryUsageStats batteryUsageStats) {
        super(TYPE);
        this.mBatteryUsageStats = batteryUsageStats;
    }

    public android.os.BatteryUsageStats getBatteryUsageStats() {
        return this.mBatteryUsageStats;
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        this.mBatteryUsageStats.writeXml(typedXmlSerializer);
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mBatteryUsageStats.dump(indentingPrintWriter, "");
    }
}
