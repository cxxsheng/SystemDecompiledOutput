package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
class BrightnessThermalClamper extends com.android.server.display.brightness.clamper.BrightnessClamper<com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData> {
    private static final java.lang.String TAG = "BrightnessThermalClamper";

    @android.annotation.NonNull
    private final com.android.server.display.feature.DeviceConfigParameterProvider mConfigParameterProvider;

    @android.annotation.Nullable
    private java.lang.String mDataId;
    private final java.util.function.BiFunction<java.lang.String, java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel> mDataPointMapper;
    private final java.util.function.Function<java.util.List<com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel>, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> mDataSetMapper;

    @android.annotation.NonNull
    private final com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalStatusObserver mThermalStatusObserver;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingDataActive;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingDataFromDeviceConfig;

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData>> mThermalThrottlingDataOverride;
    private int mThrottlingStatus;

    @android.annotation.Nullable
    private java.lang.String mUniqueDisplayId;

    interface ThermalData {
        @android.annotation.NonNull
        com.android.server.display.config.SensorData getTempSensor();

        @android.annotation.Nullable
        com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData getThermalBrightnessThrottlingData();

        @android.annotation.NonNull
        java.lang.String getThermalThrottlingDataId();

        @android.annotation.NonNull
        java.lang.String getUniqueDisplayId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel lambda$new$0(java.lang.String str, java.lang.String str2) {
        try {
            return new com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(com.android.server.display.utils.DeviceConfigParsingUtils.parseThermalStatus(str), com.android.server.display.utils.DeviceConfigParsingUtils.parseBrightness(str2));
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    BrightnessThermalClamper(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        this(new com.android.server.display.brightness.clamper.BrightnessThermalClamper.Injector(), handler, clamperChangeListener, thermalData);
    }

    @com.android.internal.annotations.VisibleForTesting
    BrightnessThermalClamper(com.android.server.display.brightness.clamper.BrightnessThermalClamper.Injector injector, android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, final com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        super(handler, clamperChangeListener);
        this.mThermalThrottlingDataOverride = java.util.Map.of();
        this.mThermalThrottlingDataFromDeviceConfig = null;
        this.mThermalThrottlingDataActive = null;
        this.mUniqueDisplayId = null;
        this.mDataId = null;
        this.mThrottlingStatus = 0;
        this.mDataPointMapper = new java.util.function.BiFunction() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel lambda$new$0;
                lambda$new$0 = com.android.server.display.brightness.clamper.BrightnessThermalClamper.lambda$new$0((java.lang.String) obj, (java.lang.String) obj2);
                return lambda$new$0;
            }
        };
        this.mDataSetMapper = new com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda2();
        this.mConfigParameterProvider = injector.getDeviceConfigParameterProvider();
        this.mThermalStatusObserver = new com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalStatusObserver(injector, handler);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessThermalClamper.this.lambda$new$1(thermalData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        setDisplayData(thermalData);
        loadOverrideData();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    @android.annotation.NonNull
    com.android.server.display.brightness.clamper.BrightnessClamper.Type getType() {
        return com.android.server.display.brightness.clamper.BrightnessClamper.Type.THERMAL;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void onDeviceConfigChanged() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessThermalClamper.this.lambda$onDeviceConfigChanged$2();
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
    public void onDisplayChanged(final com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessThermalClamper.this.lambda$onDisplayChanged$3(thermalData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayChanged$3(com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        setDisplayData(thermalData);
        recalculateActiveData();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void stop() {
        this.mThermalStatusObserver.stopObserving();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessThermalClamper:");
        printWriter.println("  mThrottlingStatus: " + this.mThrottlingStatus);
        printWriter.println("  mUniqueDisplayId: " + this.mUniqueDisplayId);
        printWriter.println("  mDataId: " + this.mDataId);
        printWriter.println("  mDataOverride: " + this.mThermalThrottlingDataOverride);
        printWriter.println("  mDataFromDeviceConfig: " + this.mThermalThrottlingDataFromDeviceConfig);
        printWriter.println("  mDataActive: " + this.mThermalThrottlingDataActive);
        this.mThermalStatusObserver.dump(printWriter);
        super.dump(printWriter);
    }

    private void recalculateActiveData() {
        if (this.mUniqueDisplayId == null || this.mDataId == null) {
            return;
        }
        this.mThermalThrottlingDataActive = this.mThermalThrottlingDataOverride.getOrDefault(this.mUniqueDisplayId, java.util.Map.of()).getOrDefault(this.mDataId, this.mThermalThrottlingDataFromDeviceConfig);
        recalculateBrightnessCap();
    }

    private void loadOverrideData() {
        this.mThermalThrottlingDataOverride = com.android.server.display.utils.DeviceConfigParsingUtils.parseDeviceConfigMap(this.mConfigParameterProvider.getBrightnessThrottlingData(), this.mDataPointMapper, this.mDataSetMapper);
    }

    private void setDisplayData(@android.annotation.NonNull com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData thermalData) {
        this.mUniqueDisplayId = thermalData.getUniqueDisplayId();
        this.mDataId = thermalData.getThermalThrottlingDataId();
        this.mThermalThrottlingDataFromDeviceConfig = thermalData.getThermalBrightnessThrottlingData();
        if (this.mThermalThrottlingDataFromDeviceConfig == null && !"default".equals(this.mDataId)) {
            android.util.Slog.wtf(TAG, "Thermal throttling data is missing for thermalThrottlingDataId=" + this.mDataId);
        }
        this.mThermalStatusObserver.registerSensor(thermalData.getTempSensor());
    }

    private void recalculateBrightnessCap() {
        float f = 1.0f;
        boolean z = false;
        if (this.mThermalThrottlingDataActive != null) {
            for (com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel : this.mThermalThrottlingDataActive.throttlingLevels) {
                if (throttlingLevel.thermalStatus > this.mThrottlingStatus) {
                    break;
                }
                f = throttlingLevel.brightness;
                z = true;
            }
        }
        if (f != this.mBrightnessCap || this.mIsActive != z) {
            this.mBrightnessCap = f;
            this.mIsActive = z;
            this.mChangeListener.onChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void thermalStatusChanged(int i) {
        if (this.mThrottlingStatus != i) {
            this.mThrottlingStatus = i;
            recalculateBrightnessCap();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ThermalStatusObserver extends android.os.IThermalEventListener.Stub {
        private final android.os.Handler mHandler;
        private final com.android.server.display.brightness.clamper.BrightnessThermalClamper.Injector mInjector;
        private com.android.server.display.config.SensorData mObserverTempSensor;
        private boolean mStarted = false;
        private android.os.IThermalService mThermalService;

        ThermalStatusObserver(com.android.server.display.brightness.clamper.BrightnessThermalClamper.Injector injector, android.os.Handler handler) {
            this.mInjector = injector;
            this.mHandler = handler;
        }

        void registerSensor(com.android.server.display.config.SensorData sensorData) {
            if (!this.mStarted || this.mObserverTempSensor == null) {
                this.mObserverTempSensor = sensorData;
                registerThermalListener();
                return;
            }
            java.lang.String str = this.mObserverTempSensor.type;
            this.mObserverTempSensor = sensorData;
            if (str.equals(sensorData.type)) {
                android.util.Slog.d(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "Thermal status observer already started");
            } else {
                stopObserving();
                registerThermalListener();
            }
        }

        void registerThermalListener() {
            this.mThermalService = this.mInjector.getThermalService();
            if (this.mThermalService == null) {
                android.util.Slog.e(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "Could not observe thermal status. Service not available");
                return;
            }
            try {
                this.mThermalService.registerThermalEventListenerWithType(this, com.android.server.display.utils.SensorUtils.getSensorTemperatureType(this.mObserverTempSensor));
                this.mStarted = true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "Failed to register thermal status listener", e);
            }
        }

        public void notifyThrottling(android.os.Temperature temperature) {
            android.util.Slog.d(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "New thermal throttling status = " + temperature.getStatus());
            if (this.mObserverTempSensor.name != null && !this.mObserverTempSensor.name.equals(temperature.getName())) {
                android.util.Slog.i(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "Skipping thermal throttling notification as monitored sensor: " + this.mObserverTempSensor.name + " != notified sensor: " + temperature.getName());
                return;
            }
            final int status = temperature.getStatus();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessThermalClamper$ThermalStatusObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalStatusObserver.this.lambda$notifyThrottling$0(status);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyThrottling$0(int i) {
            com.android.server.display.brightness.clamper.BrightnessThermalClamper.this.thermalStatusChanged(i);
        }

        void stopObserving() {
            if (!this.mStarted) {
                return;
            }
            try {
                this.mThermalService.unregisterThermalEventListener(this);
                this.mStarted = false;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.display.brightness.clamper.BrightnessThermalClamper.TAG, "Failed to unregister thermal status listener", e);
            }
            this.mThermalService = null;
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  ThermalStatusObserver:");
            printWriter.println("    mStarted: " + this.mStarted);
            printWriter.println("    mObserverTempSensor: " + this.mObserverTempSensor);
            if (this.mThermalService != null) {
                printWriter.println("    ThermalService available");
            } else {
                printWriter.println("    ThermalService not available");
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        android.os.IThermalService getThermalService() {
            return android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
        }

        com.android.server.display.feature.DeviceConfigParameterProvider getDeviceConfigParameterProvider() {
            return new com.android.server.display.feature.DeviceConfigParameterProvider(android.provider.DeviceConfigInterface.REAL);
        }
    }
}
