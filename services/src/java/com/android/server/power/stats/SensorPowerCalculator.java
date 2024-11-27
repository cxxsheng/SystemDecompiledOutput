package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class SensorPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final android.util.SparseArray<android.hardware.Sensor> mSensors;

    public SensorPowerCalculator(android.hardware.SensorManager sensorManager) {
        java.util.List<android.hardware.Sensor> sensorList = sensorManager.getSensorList(-1);
        this.mSensors = new android.util.SparseArray<>(sensorList.size());
        for (int i = 0; i < sensorList.size(); i++) {
            android.hardware.Sensor sensor = sensorList.get(i);
            this.mSensors.put(sensor.getHandle(), sensor);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 9;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        double d = 0.0d;
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (!builder2.isVirtualUid()) {
                d += calculateApp(builder2, builder2.getBatteryStatsUid(), j);
            }
        }
        builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(9, d);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(9, d);
    }

    private double calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, long j) {
        double calculatePowerMah = calculatePowerMah(uid, j, 0);
        builder.setUsageDurationMillis(9, calculateDuration(uid, j, 0)).setConsumedPower(9, calculatePowerMah);
        return calculatePowerMah;
    }

    private long calculateDuration(android.os.BatteryStats.Uid uid, long j, int i) {
        android.util.SparseArray sensorStats = uid.getSensorStats();
        int size = sensorStats.size();
        long j2 = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (sensorStats.keyAt(i2) != -10000) {
                j2 += ((android.os.BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime().getTotalTimeLocked(j, i) / 1000;
            }
        }
        return j2;
    }

    private double calculatePowerMah(android.os.BatteryStats.Uid uid, long j, int i) {
        android.hardware.Sensor sensor;
        android.util.SparseArray sensorStats = uid.getSensorStats();
        int size = sensorStats.size();
        double d = 0.0d;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sensorStats.keyAt(i2);
            if (keyAt != -10000) {
                if (((android.os.BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime().getTotalTimeLocked(j, i) / 1000 != 0 && (sensor = this.mSensors.get(keyAt)) != null) {
                    d += (r5 * sensor.getPower()) / 3600000.0f;
                }
            }
        }
        return d;
    }
}
