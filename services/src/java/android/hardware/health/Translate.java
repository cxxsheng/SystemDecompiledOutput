package android.hardware.health;

/* loaded from: classes.dex */
public class Translate {
    public static android.hardware.health.StorageInfo h2aTranslate(android.hardware.health.V2_0.StorageInfo storageInfo) {
        android.hardware.health.StorageInfo storageInfo2 = new android.hardware.health.StorageInfo();
        storageInfo2.eol = storageInfo.eol;
        storageInfo2.lifetimeA = storageInfo.lifetimeA;
        storageInfo2.lifetimeB = storageInfo.lifetimeB;
        storageInfo2.version = storageInfo.version;
        return storageInfo2;
    }

    public static android.hardware.health.DiskStats h2aTranslate(android.hardware.health.V2_0.DiskStats diskStats) {
        android.hardware.health.DiskStats diskStats2 = new android.hardware.health.DiskStats();
        diskStats2.reads = diskStats.reads;
        diskStats2.readMerges = diskStats.readMerges;
        diskStats2.readSectors = diskStats.readSectors;
        diskStats2.readTicks = diskStats.readTicks;
        diskStats2.writes = diskStats.writes;
        diskStats2.writeMerges = diskStats.writeMerges;
        diskStats2.writeSectors = diskStats.writeSectors;
        diskStats2.writeTicks = diskStats.writeTicks;
        diskStats2.ioInFlight = diskStats.ioInFlight;
        diskStats2.ioTicks = diskStats.ioTicks;
        diskStats2.ioInQueue = diskStats.ioInQueue;
        return diskStats2;
    }

    private static void h2aTranslateInternal(android.hardware.health.HealthInfo healthInfo, android.hardware.health.V1_0.HealthInfo healthInfo2) {
        healthInfo.chargerAcOnline = healthInfo2.chargerAcOnline;
        healthInfo.chargerUsbOnline = healthInfo2.chargerUsbOnline;
        healthInfo.chargerWirelessOnline = healthInfo2.chargerWirelessOnline;
        healthInfo.maxChargingCurrentMicroamps = healthInfo2.maxChargingCurrent;
        healthInfo.maxChargingVoltageMicrovolts = healthInfo2.maxChargingVoltage;
        healthInfo.batteryStatus = healthInfo2.batteryStatus;
        healthInfo.batteryHealth = healthInfo2.batteryHealth;
        healthInfo.batteryPresent = healthInfo2.batteryPresent;
        healthInfo.batteryLevel = healthInfo2.batteryLevel;
        healthInfo.batteryVoltageMillivolts = healthInfo2.batteryVoltage;
        healthInfo.batteryTemperatureTenthsCelsius = healthInfo2.batteryTemperature;
        healthInfo.batteryCurrentMicroamps = healthInfo2.batteryCurrent;
        healthInfo.batteryCycleCount = healthInfo2.batteryCycleCount;
        healthInfo.batteryFullChargeUah = healthInfo2.batteryFullCharge;
        healthInfo.batteryChargeCounterUah = healthInfo2.batteryChargeCounter;
        healthInfo.batteryTechnology = healthInfo2.batteryTechnology;
    }

    public static android.hardware.health.HealthInfo h2aTranslate(android.hardware.health.V1_0.HealthInfo healthInfo) {
        android.hardware.health.HealthInfo healthInfo2 = new android.hardware.health.HealthInfo();
        h2aTranslateInternal(healthInfo2, healthInfo);
        return healthInfo2;
    }

    public static android.hardware.health.HealthInfo h2aTranslate(android.hardware.health.V2_1.HealthInfo healthInfo) {
        android.hardware.health.HealthInfo healthInfo2 = new android.hardware.health.HealthInfo();
        h2aTranslateInternal(healthInfo2, healthInfo.legacy.legacy);
        healthInfo2.batteryCurrentAverageMicroamps = healthInfo.legacy.batteryCurrentAverage;
        healthInfo2.diskStats = new android.hardware.health.DiskStats[healthInfo.legacy.diskStats.size()];
        for (int i = 0; i < healthInfo.legacy.diskStats.size(); i++) {
            healthInfo2.diskStats[i] = h2aTranslate(healthInfo.legacy.diskStats.get(i));
        }
        healthInfo2.storageInfos = new android.hardware.health.StorageInfo[healthInfo.legacy.storageInfos.size()];
        for (int i2 = 0; i2 < healthInfo.legacy.storageInfos.size(); i2++) {
            healthInfo2.storageInfos[i2] = h2aTranslate(healthInfo.legacy.storageInfos.get(i2));
        }
        healthInfo2.batteryCapacityLevel = healthInfo.batteryCapacityLevel;
        healthInfo2.batteryChargeTimeToFullNowSeconds = healthInfo.batteryChargeTimeToFullNowSeconds;
        healthInfo2.batteryFullChargeDesignCapacityUah = healthInfo.batteryFullChargeDesignCapacityUah;
        return healthInfo2;
    }
}
