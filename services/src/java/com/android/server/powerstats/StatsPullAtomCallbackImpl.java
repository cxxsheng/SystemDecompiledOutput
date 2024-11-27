package com.android.server.powerstats;

/* loaded from: classes2.dex */
public class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
    private static final boolean DEBUG = false;
    private static final int STATS_PULL_TIMEOUT_MILLIS = 2000;
    private static final java.lang.String TAG = com.android.server.powerstats.StatsPullAtomCallbackImpl.class.getSimpleName();
    private android.content.Context mContext;
    private android.power.PowerStatsInternal mPowerStatsInternal;
    private java.util.Map<java.lang.Integer, android.hardware.power.stats.Channel> mChannels = new java.util.HashMap();
    private java.util.Map<java.lang.Integer, java.lang.String> mEntityNames = new java.util.HashMap();
    private java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, java.lang.String>> mStateNames = new java.util.HashMap();

    public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
        switch (i) {
            case com.android.internal.util.FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE /* 10005 */:
                return pullSubsystemSleepState(i, list);
            case com.android.internal.util.FrameworkStatsLog.ON_DEVICE_POWER_MEASUREMENT /* 10038 */:
                return pullOnDevicePowerMeasurement(i, list);
            default:
                throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
        }
    }

    private boolean initPullOnDevicePowerMeasurement() {
        android.hardware.power.stats.Channel[] energyMeterInfo = this.mPowerStatsInternal.getEnergyMeterInfo();
        if (energyMeterInfo == null || energyMeterInfo.length == 0) {
            android.util.Slog.e(TAG, "Failed to init OnDevicePowerMeasurement puller");
            return false;
        }
        for (android.hardware.power.stats.Channel channel : energyMeterInfo) {
            this.mChannels.put(java.lang.Integer.valueOf(channel.id), channel);
        }
        return true;
    }

    private int pullOnDevicePowerMeasurement(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            android.hardware.power.stats.EnergyMeasurement[] energyMeasurementArr = this.mPowerStatsInternal.readEnergyMeterAsync(new int[0]).get(2000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (energyMeasurementArr == null) {
                return 1;
            }
            for (android.hardware.power.stats.EnergyMeasurement energyMeasurement : energyMeasurementArr) {
                if (energyMeasurement.durationMs == energyMeasurement.timestampMs) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mChannels.get(java.lang.Integer.valueOf(energyMeasurement.id)).subsystem, this.mChannels.get(java.lang.Integer.valueOf(energyMeasurement.id)).name, energyMeasurement.durationMs, energyMeasurement.energyUWs));
                }
            }
            return 0;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to readEnergyMeterAsync", e);
            return 1;
        }
    }

    private boolean initSubsystemSleepState() {
        android.hardware.power.stats.PowerEntity[] powerEntityInfo = this.mPowerStatsInternal.getPowerEntityInfo();
        if (powerEntityInfo == null || powerEntityInfo.length == 0) {
            android.util.Slog.e(TAG, "Failed to init SubsystemSleepState puller");
            return false;
        }
        for (android.hardware.power.stats.PowerEntity powerEntity : powerEntityInfo) {
            java.util.HashMap hashMap = new java.util.HashMap();
            for (int i = 0; i < powerEntity.states.length; i++) {
                android.hardware.power.stats.State state = powerEntity.states[i];
                hashMap.put(java.lang.Integer.valueOf(state.id), state.name);
            }
            this.mEntityNames.put(java.lang.Integer.valueOf(powerEntity.id), powerEntity.name);
            this.mStateNames.put(java.lang.Integer.valueOf(powerEntity.id), hashMap);
        }
        return true;
    }

    private int pullSubsystemSleepState(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr = this.mPowerStatsInternal.getStateResidencyAsync(new int[0]).get(2000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (stateResidencyResultArr == null) {
                return 1;
            }
            for (android.hardware.power.stats.StateResidencyResult stateResidencyResult : stateResidencyResultArr) {
                for (int i2 = 0; i2 < stateResidencyResult.stateResidencyData.length; i2++) {
                    android.hardware.power.stats.StateResidency stateResidency = stateResidencyResult.stateResidencyData[i2];
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mEntityNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)), this.mStateNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)).get(java.lang.Integer.valueOf(stateResidency.id)), stateResidency.totalStateEntryCount, stateResidency.totalTimeInStateMs));
                }
            }
            return 0;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to getStateResidencyAsync", e);
            return 1;
        }
    }

    public StatsPullAtomCallbackImpl(android.content.Context context, android.power.PowerStatsInternal powerStatsInternal) {
        this.mContext = context;
        this.mPowerStatsInternal = powerStatsInternal;
        if (powerStatsInternal == null) {
            android.util.Slog.e(TAG, "Failed to start PowerStatsService statsd pullers");
            return;
        }
        android.app.StatsManager statsManager = (android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class);
        if (initPullOnDevicePowerMeasurement()) {
            statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ON_DEVICE_POWER_MEASUREMENT, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this);
        }
        if (initSubsystemSleepState()) {
            statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this);
        }
    }
}
