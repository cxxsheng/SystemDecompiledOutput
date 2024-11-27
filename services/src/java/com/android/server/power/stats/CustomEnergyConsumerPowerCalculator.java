package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class CustomEnergyConsumerPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final java.lang.String TAG = "CustomEnergyCsmrPowerCalc";

    public CustomEnergyConsumerPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return false;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        double[] dArr = null;
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            dArr = calculateApp(builder2, builder2.getBatteryStatsUid(), dArr);
        }
        double[] uCtoMah = uCtoMah(batteryStats.getCustomEnergyConsumerBatteryConsumptionUC());
        if (uCtoMah != null) {
            android.os.AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder = builder.getAggregateBatteryConsumerBuilder(0);
            for (int i = 0; i < uCtoMah.length; i++) {
                aggregateBatteryConsumerBuilder.setConsumedPowerForCustomComponent(i + 1000, uCtoMah[i]);
            }
        }
        if (dArr != null) {
            android.os.AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder2 = builder.getAggregateBatteryConsumerBuilder(1);
            for (int i2 = 0; i2 < dArr.length; i2++) {
                aggregateBatteryConsumerBuilder2.setConsumedPowerForCustomComponent(i2 + 1000, dArr[i2]);
            }
        }
    }

    private double[] calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, double[] dArr) {
        double[] uCtoMah = uCtoMah(uid.getCustomEnergyConsumerBatteryConsumptionUC());
        if (uCtoMah == null) {
            return null;
        }
        if (dArr == null) {
            dArr = new double[uCtoMah.length];
        } else if (dArr.length != uCtoMah.length) {
            android.util.Slog.wtf(TAG, "Number of custom energy components is not the same for all apps: " + dArr.length + ", " + uCtoMah.length);
            dArr = java.util.Arrays.copyOf(dArr, uCtoMah.length);
        }
        for (int i = 0; i < uCtoMah.length; i++) {
            builder.setConsumedPowerForCustomComponent(i + 1000, uCtoMah[i]);
            if (!builder.isVirtualUid()) {
                dArr[i] = dArr[i] + uCtoMah[i];
            }
        }
        return dArr;
    }

    private double[] uCtoMah(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        double[] dArr = new double[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            dArr[i] = com.android.server.power.stats.PowerCalculator.uCtoMah(jArr[i]);
        }
        return dArr;
    }
}
