package com.android.server.power.stats;

/* loaded from: classes2.dex */
class AggregatedPowerStatsSection extends com.android.server.power.stats.PowerStatsSpan.Section {
    public static final java.lang.String TYPE = "aggregated-power-stats";
    private final com.android.server.power.stats.AggregatedPowerStats mAggregatedPowerStats;

    AggregatedPowerStatsSection(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        super(TYPE);
        this.mAggregatedPowerStats = aggregatedPowerStats;
    }

    public com.android.server.power.stats.AggregatedPowerStats getAggregatedPowerStats() {
        return this.mAggregatedPowerStats;
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    void write(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        this.mAggregatedPowerStats.writeXml(typedXmlSerializer);
    }

    @Override // com.android.server.power.stats.PowerStatsSpan.Section
    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mAggregatedPowerStats.dump(indentingPrintWriter);
    }
}
