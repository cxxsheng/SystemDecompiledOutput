package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
class BrightnessPowerClamper extends com.android.server.display.brightness.clamper.BrightnessClamper<com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData> {
    private static final java.lang.String TAG = "BrightnessPowerClamper";

    @android.annotation.NonNull
    private final com.android.server.display.feature.DeviceConfigParameterProvider mConfigParameterProvider;
    private float mCurrentAvgPowerConsumed;
    private int mCurrentThermalLevel;

    @android.annotation.Nullable
    private java.lang.String mDataId;
    private final java.util.function.BiFunction<java.lang.String, java.lang.String, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel> mDataPointMapper;
    private final java.util.function.Function<java.util.List<com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel>, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData> mDataSetMapper;

    @android.annotation.NonNull
    private final com.android.server.display.brightness.clamper.BrightnessPowerClamper.Injector mInjector;

    @android.annotation.Nullable
    private com.android.server.display.brightness.clamper.PmicMonitor mPmicMonitor;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData mPowerThrottlingConfigData;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.PowerThrottlingData mPowerThrottlingDataActive;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.PowerThrottlingData mPowerThrottlingDataFromDDC;

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.PowerThrottlingData>> mPowerThrottlingDataOverride;

    @android.annotation.Nullable
    private java.lang.String mUniqueDisplayId;

    @java.lang.FunctionalInterface
    public interface PowerChangeListener {
        void onChanged(float f, int i);
    }

    public interface PowerData {
        @android.annotation.Nullable
        com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData getPowerThrottlingConfigData();

        @android.annotation.Nullable
        com.android.server.display.DisplayDeviceConfig.PowerThrottlingData getPowerThrottlingData();

        @android.annotation.NonNull
        java.lang.String getPowerThrottlingDataId();

        @android.annotation.NonNull
        java.lang.String getUniqueDisplayId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel lambda$new$0(java.lang.String str, java.lang.String str2) {
        try {
            return new com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel(com.android.server.display.utils.DeviceConfigParsingUtils.parseThermalStatus(str), java.lang.Float.parseFloat(str2));
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    BrightnessPowerClamper(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        this(new com.android.server.display.brightness.clamper.BrightnessPowerClamper.Injector(), handler, clamperChangeListener, powerData);
    }

    @com.android.internal.annotations.VisibleForTesting
    BrightnessPowerClamper(com.android.server.display.brightness.clamper.BrightnessPowerClamper.Injector injector, android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, final com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        super(handler, clamperChangeListener);
        this.mPowerThrottlingDataOverride = java.util.Map.of();
        this.mPowerThrottlingDataFromDDC = null;
        this.mPowerThrottlingDataActive = null;
        this.mPowerThrottlingConfigData = null;
        this.mCurrentThermalLevel = 0;
        this.mCurrentAvgPowerConsumed = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mUniqueDisplayId = null;
        this.mDataId = null;
        this.mDataPointMapper = new java.util.function.BiFunction() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel lambda$new$0;
                lambda$new$0 = com.android.server.display.brightness.clamper.BrightnessPowerClamper.lambda$new$0((java.lang.String) obj, (java.lang.String) obj2);
                return lambda$new$0;
            }
        };
        this.mDataSetMapper = new java.util.function.Function() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.create((java.util.List) obj);
            }
        };
        this.mInjector = injector;
        this.mConfigParameterProvider = injector.getDeviceConfigParameterProvider();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessPowerClamper.this.lambda$new$1(powerData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        setDisplayData(powerData);
        loadOverrideData();
        start();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    @android.annotation.NonNull
    com.android.server.display.brightness.clamper.BrightnessClamper.Type getType() {
        return com.android.server.display.brightness.clamper.BrightnessClamper.Type.POWER;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void onDeviceConfigChanged() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessPowerClamper.this.lambda$onDeviceConfigChanged$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeviceConfigChanged$2() {
        loadOverrideData();
        recalculateActiveData();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public void onDisplayChanged(final com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessPowerClamper.this.lambda$onDisplayChanged$3(powerData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayChanged$3(com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        setDisplayData(powerData);
        recalculateActiveData();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void stop() {
        if (this.mPmicMonitor != null) {
            this.mPmicMonitor.shutdown();
        }
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessPowerClamper:");
        printWriter.println("  mCurrentAvgPowerConsumed=" + this.mCurrentAvgPowerConsumed);
        printWriter.println("  mUniqueDisplayId=" + this.mUniqueDisplayId);
        printWriter.println("  mCurrentThermalLevel=" + this.mCurrentThermalLevel);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("  mPowerThrottlingDataFromDDC=");
        sb.append(this.mPowerThrottlingDataFromDDC == null ? "null" : this.mPowerThrottlingDataFromDDC.toString());
        printWriter.println(sb.toString());
        super.dump(printWriter);
    }

    private void recalculateActiveData() {
        if (this.mUniqueDisplayId == null || this.mDataId == null) {
            return;
        }
        this.mPowerThrottlingDataActive = this.mPowerThrottlingDataOverride.getOrDefault(this.mUniqueDisplayId, java.util.Map.of()).getOrDefault(this.mDataId, this.mPowerThrottlingDataFromDDC);
        if (this.mPowerThrottlingDataActive != null) {
            if (this.mPmicMonitor != null) {
                this.mPmicMonitor.stop();
                this.mPmicMonitor.start();
            }
        } else if (this.mPmicMonitor != null) {
            this.mPmicMonitor.stop();
        }
        recalculateBrightnessCap();
    }

    private void loadOverrideData() {
        this.mPowerThrottlingDataOverride = com.android.server.display.utils.DeviceConfigParsingUtils.parseDeviceConfigMap(this.mConfigParameterProvider.getPowerThrottlingData(), this.mDataPointMapper, this.mDataSetMapper);
    }

    private void setDisplayData(@android.annotation.NonNull com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData powerData) {
        this.mUniqueDisplayId = powerData.getUniqueDisplayId();
        this.mDataId = powerData.getPowerThrottlingDataId();
        this.mPowerThrottlingDataFromDDC = powerData.getPowerThrottlingData();
        if (this.mPowerThrottlingDataFromDDC == null && !"default".equals(this.mDataId)) {
            android.util.Slog.wtf(TAG, "Power throttling data is missing for powerThrottlingDataId=" + this.mDataId);
        }
        this.mPowerThrottlingConfigData = powerData.getPowerThrottlingConfigData();
        if (this.mPowerThrottlingConfigData == null) {
            android.util.Slog.d(TAG, "Power throttling data is missing for configuration data.");
        }
    }

    private void recalculateBrightnessCap() {
        boolean z;
        float powerQuotaForThermalStatus = getPowerQuotaForThermalStatus(this.mCurrentThermalLevel);
        if (this.mPowerThrottlingDataActive == null) {
            return;
        }
        float f = 1.0f;
        if (powerQuotaForThermalStatus > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && this.mCurrentAvgPowerConsumed > powerQuotaForThermalStatus) {
            f = java.lang.Math.max((powerQuotaForThermalStatus / this.mCurrentAvgPowerConsumed) * 1.0f, this.mPowerThrottlingConfigData.brightnessLowestCapAllowed);
            z = true;
        } else {
            z = false;
        }
        if (this.mBrightnessCap != f || this.mIsActive != z) {
            this.mIsActive = z;
            this.mBrightnessCap = f;
            this.mChangeListener.onChanged();
        }
    }

    private float getPowerQuotaForThermalStatus(int i) {
        com.android.server.display.DisplayDeviceConfig.PowerThrottlingData powerThrottlingData = this.mPowerThrottlingDataActive;
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        if (powerThrottlingData != null) {
            for (com.android.server.display.DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel throttlingLevel : this.mPowerThrottlingDataActive.throttlingLevels) {
                if (throttlingLevel.thermalStatus > i) {
                    break;
                }
                f = throttlingLevel.powerQuotaMilliWatts;
            }
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: recalculatePowerQuotaChange, reason: merged with bridge method [inline-methods] */
    public void lambda$start$5(final float f, final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessPowerClamper.this.lambda$recalculatePowerQuotaChange$4(i, f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$recalculatePowerQuotaChange$4(int i, float f) {
        this.mCurrentThermalLevel = i;
        this.mCurrentAvgPowerConsumed = f;
        recalculateBrightnessCap();
    }

    private void start() {
        if (this.mPowerThrottlingConfigData == null) {
            return;
        }
        this.mPmicMonitor = this.mInjector.getPmicMonitor(new com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerChangeListener() { // from class: com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda2
            @Override // com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerChangeListener
            public final void onChanged(float f, int i) {
                com.android.server.display.brightness.clamper.BrightnessPowerClamper.this.lambda$start$5(f, i);
            }
        }, this.mPowerThrottlingConfigData.pollingWindowMillis);
        this.mPmicMonitor.start();
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.brightness.clamper.PmicMonitor getPmicMonitor(com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerChangeListener powerChangeListener, int i) {
            return new com.android.server.display.brightness.clamper.PmicMonitor(powerChangeListener, i);
        }

        com.android.server.display.feature.DeviceConfigParameterProvider getDeviceConfigParameterProvider() {
            return new com.android.server.display.feature.DeviceConfigParameterProvider(android.provider.DeviceConfigInterface.REAL);
        }
    }
}
