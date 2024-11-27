package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public class PmicMonitor {
    private static final java.lang.String TAG = "PmicMonitor";
    private android.os.Temperature mCurrentTemperature;
    private java.util.concurrent.ScheduledFuture<?> mPmicMonitorFuture;

    @android.annotation.NonNull
    private final com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerChangeListener mPowerChangeListener;
    private final long mPowerMonitorPeriodConfigSecs;
    private float mLastEnergyConsumed = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private float mCurrentAvgPower = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private long mCurrentTimestampMillis = 0;
    private final android.power.PowerStatsInternal mPowerStatsInternal = (android.power.PowerStatsInternal) com.android.server.LocalServices.getService(android.power.PowerStatsInternal.class);

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IThermalService mThermalService = android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
    private final java.util.concurrent.ScheduledExecutorService mExecutor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();

    PmicMonitor(com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerChangeListener powerChangeListener, int i) {
        this.mPowerChangeListener = powerChangeListener;
        this.mPowerMonitorPeriodConfigSecs = i;
    }

    @android.annotation.Nullable
    private android.os.Temperature getDisplayTemperature() {
        try {
            android.os.Temperature[] currentTemperaturesWithType = this.mThermalService.getCurrentTemperaturesWithType(3);
            if (currentTemperaturesWithType.length > 1) {
                android.util.Slog.w(TAG, "Multiple skin temperatures not allowed!");
            }
            if (currentTemperaturesWithType.length <= 0) {
                return null;
            }
            return currentTemperaturesWithType[0];
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "getDisplayTemperature failed" + e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void capturePeriodicDisplayPower() {
        android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = this.mPowerStatsInternal.getEnergyConsumerInfo();
        if (energyConsumerInfo == null || energyConsumerInfo.length == 0) {
            return;
        }
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i = 0; i < energyConsumerInfo.length; i++) {
            if (energyConsumerInfo[i].type == 3) {
                intArray.add(energyConsumerInfo[i].id);
            }
        }
        if (intArray.size() == 0) {
            android.util.Slog.w(TAG, "DISPLAY energyConsumerIds size is null");
            return;
        }
        java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> energyConsumedAsync = this.mPowerStatsInternal.getEnergyConsumedAsync(intArray.toArray());
        if (energyConsumedAsync == null) {
            android.util.Slog.w(TAG, "Energy consumers results are null");
            return;
        }
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr = null;
        try {
            energyConsumerResultArr = energyConsumedAsync.get();
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.w(TAG, "timeout or interrupt reading getEnergyConsumedAsync failed", e);
        } catch (java.util.concurrent.ExecutionException e2) {
            android.util.Slog.wtf(TAG, "exception reading getEnergyConsumedAsync: ", e2);
        }
        if (energyConsumerResultArr == null || energyConsumerResultArr.length == 0) {
            android.util.Slog.w(TAG, "displayResults are null");
            return;
        }
        float f = ((energyConsumerResultArr[0].energyUWs - this.mLastEnergyConsumed) / ((energyConsumerResultArr[0].timestampMs - this.mCurrentTimestampMillis) / 1000.0f)) / 1000.0f;
        android.os.Temperature displayTemperature = getDisplayTemperature();
        this.mCurrentAvgPower = f;
        this.mCurrentTemperature = displayTemperature;
        this.mLastEnergyConsumed = energyConsumerResultArr[0].energyUWs;
        this.mCurrentTimestampMillis = energyConsumerResultArr[0].timestampMs;
        if (this.mCurrentTemperature != null) {
            this.mPowerChangeListener.onChanged(this.mCurrentAvgPower, this.mCurrentTemperature.getStatus());
        }
    }

    public void start() {
        if (this.mPowerStatsInternal == null) {
            android.util.Slog.w(TAG, "Power stats service not found for monitoring.");
            return;
        }
        if (this.mThermalService == null) {
            android.util.Slog.w(TAG, "Thermal service not found.");
        } else if (this.mPmicMonitorFuture == null) {
            this.mPmicMonitorFuture = this.mExecutor.scheduleAtFixedRate(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.PmicMonitor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.brightness.clamper.PmicMonitor.this.capturePeriodicDisplayPower();
                }
            }, this.mPowerMonitorPeriodConfigSecs, this.mPowerMonitorPeriodConfigSecs, java.util.concurrent.TimeUnit.SECONDS);
        } else {
            android.util.Slog.e(TAG, "already scheduled, stop() called before start.");
        }
    }

    public void stop() {
        if (this.mPmicMonitorFuture != null) {
            this.mPmicMonitorFuture.cancel(true);
            this.mPmicMonitorFuture = null;
        }
    }

    public void shutdown() {
        this.mExecutor.shutdownNow();
    }
}
