package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class GnssPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final double mAveragePowerGnssOn;
    private final double[] mAveragePowerPerSignalQuality = new double[2];

    public GnssPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mAveragePowerGnssOn = powerProfile.getAveragePowerOrDefault("gps.on", -1.0d);
        for (int i = 0; i < 2; i++) {
            this.mAveragePowerPerSignalQuality[i] = powerProfile.getAveragePower("gps.signalqualitybased", i);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 10;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double d;
        double averageGnssPower = getAverageGnssPower(batteryStats, j, 0);
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        double d2 = 0.0d;
        while (size >= 0) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            long gnssEnergyConsumptionUC = builder2.getBatteryStatsUid().getGnssEnergyConsumptionUC();
            double d3 = d2;
            int i = size;
            android.util.SparseArray sparseArray = uidBatteryConsumerBuilders;
            double calculateApp = calculateApp(builder2, builder2.getBatteryStatsUid(), com.android.server.power.stats.PowerCalculator.getPowerModel(gnssEnergyConsumptionUC, batteryUsageStatsQuery), j, averageGnssPower, gnssEnergyConsumptionUC);
            if (builder2.isVirtualUid()) {
                d2 = d3;
            } else {
                d2 = d3 + calculateApp;
            }
            size = i - 1;
            uidBatteryConsumerBuilders = sparseArray;
        }
        long gnssEnergyConsumptionUC2 = batteryStats.getGnssEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(gnssEnergyConsumptionUC2, batteryUsageStatsQuery);
        if (powerModel == 2) {
            d = com.android.server.power.stats.PowerCalculator.uCtoMah(gnssEnergyConsumptionUC2);
        } else {
            d = d2;
        }
        builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(10, d, powerModel);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(10, d2, powerModel);
    }

    private double calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, int i, long j, double d, long j2) {
        double uCtoMah;
        long computeDuration = computeDuration(uid, j, 0);
        switch (i) {
            case 2:
                uCtoMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j2);
                break;
            default:
                uCtoMah = computePower(computeDuration, d);
                break;
        }
        builder.setUsageDurationMillis(10, computeDuration).setConsumedPower(10, uCtoMah, i);
        return uCtoMah;
    }

    private long computeDuration(android.os.BatteryStats.Uid uid, long j, int i) {
        android.os.BatteryStats.Uid.Sensor sensor = (android.os.BatteryStats.Uid.Sensor) uid.getSensorStats().get(com.android.server.am.ProcessList.INVALID_ADJ);
        if (sensor == null) {
            return 0L;
        }
        return sensor.getSensorTime().getTotalTimeLocked(j, i) / 1000;
    }

    private double computePower(long j, double d) {
        return (j * d) / 3600000.0d;
    }

    private double getAverageGnssPower(android.os.BatteryStats batteryStats, long j, int i) {
        double d = this.mAveragePowerGnssOn;
        if (d != -1.0d) {
            return d;
        }
        long j2 = 0;
        double d2 = 0.0d;
        for (int i2 = 0; i2 < 2; i2++) {
            long gpsSignalQualityTime = batteryStats.getGpsSignalQualityTime(i2, j, i);
            j2 += gpsSignalQualityTime;
            d2 += this.mAveragePowerPerSignalQuality[i2] * gpsSignalQualityTime;
        }
        if (j2 == 0) {
            return 0.0d;
        }
        return d2 / j2;
    }
}
