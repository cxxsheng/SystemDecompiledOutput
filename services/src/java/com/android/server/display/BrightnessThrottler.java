package com.android.server.display;

@java.lang.Deprecated
/* loaded from: classes.dex */
class BrightnessThrottler {
    private static final int THROTTLING_INVALID = -1;
    private float mBrightnessCap;
    private int mBrightnessMaxReason;
    private final com.android.server.display.feature.DeviceConfigParameterProvider mConfigParameterProvider;
    private final java.util.function.BiFunction<java.lang.String, java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel> mDataPointMapper;
    private final java.util.function.Function<java.util.List<com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel>, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> mDataSetMapper;

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> mDdcThermalThrottlingDataMap;
    private final android.os.Handler mDeviceConfigHandler;
    private final com.android.server.display.BrightnessThrottler.DeviceConfigListener mDeviceConfigListener;
    private final android.os.Handler mHandler;
    private final com.android.server.display.BrightnessThrottler.Injector mInjector;
    private final com.android.server.display.BrightnessThrottler.SkinThermalStatusObserver mSkinThermalStatusObserver;

    @android.annotation.NonNull
    private com.android.server.display.config.SensorData mTempSensor;
    private java.lang.String mThermalBrightnessThrottlingDataId;
    private final java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData>> mThermalBrightnessThrottlingDataOverride;
    private java.lang.String mThermalBrightnessThrottlingDataString;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingData;
    private final java.lang.Runnable mThrottlingChangeCallback;
    private int mThrottlingStatus;
    private java.lang.String mUniqueDisplayId;
    private static final java.lang.String TAG = "BrightnessThrottler";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel lambda$new$0(java.lang.String str, java.lang.String str2) {
        try {
            return new com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(com.android.server.display.utils.DeviceConfigParsingUtils.parseThermalStatus(str), com.android.server.display.utils.DeviceConfigParsingUtils.parseBrightness(str2));
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    BrightnessThrottler(android.os.Handler handler, java.lang.Runnable runnable, java.lang.String str, java.lang.String str2, @android.annotation.NonNull com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        this(new com.android.server.display.BrightnessThrottler.Injector(), handler, handler, runnable, str, str2, displayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId(), displayDeviceConfig.getTempSensor());
    }

    @com.android.internal.annotations.VisibleForTesting
    BrightnessThrottler(com.android.server.display.BrightnessThrottler.Injector injector, android.os.Handler handler, android.os.Handler handler2, java.lang.Runnable runnable, java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> map, @android.annotation.NonNull com.android.server.display.config.SensorData sensorData) {
        this.mBrightnessCap = 1.0f;
        this.mBrightnessMaxReason = 0;
        this.mThermalBrightnessThrottlingDataOverride = new java.util.HashMap();
        this.mDataPointMapper = new java.util.function.BiFunction() { // from class: com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel lambda$new$0;
                lambda$new$0 = com.android.server.display.BrightnessThrottler.lambda$new$0((java.lang.String) obj, (java.lang.String) obj2);
                return lambda$new$0;
            }
        };
        this.mDataSetMapper = new com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda2();
        this.mInjector = injector;
        this.mHandler = handler;
        this.mDeviceConfigHandler = handler2;
        this.mDdcThermalThrottlingDataMap = map;
        this.mThrottlingChangeCallback = runnable;
        this.mSkinThermalStatusObserver = new com.android.server.display.BrightnessThrottler.SkinThermalStatusObserver(this.mInjector, this.mHandler);
        this.mUniqueDisplayId = str;
        this.mConfigParameterProvider = new com.android.server.display.feature.DeviceConfigParameterProvider(injector.getDeviceConfig());
        this.mDeviceConfigListener = new com.android.server.display.BrightnessThrottler.DeviceConfigListener();
        this.mThermalBrightnessThrottlingDataId = str2;
        this.mDdcThermalThrottlingDataMap = map;
        loadThermalBrightnessThrottlingDataFromDeviceConfig();
        loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(this.mDdcThermalThrottlingDataMap, sensorData, this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
    }

    boolean deviceSupportsThrottling() {
        return this.mThermalThrottlingData != null;
    }

    float getBrightnessCap() {
        return this.mBrightnessCap;
    }

    int getBrightnessMaxReason() {
        return this.mBrightnessMaxReason;
    }

    boolean isThrottled() {
        return this.mBrightnessMaxReason != 0;
    }

    void stop() {
        this.mSkinThermalStatusObserver.stopObserving();
        this.mConfigParameterProvider.removeOnPropertiesChangedListener(this.mDeviceConfigListener);
        this.mBrightnessCap = 1.0f;
        this.mBrightnessMaxReason = 0;
        this.mThrottlingStatus = -1;
    }

    void loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(java.util.Map<java.lang.String, com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData> map, com.android.server.display.config.SensorData sensorData, java.lang.String str, java.lang.String str2) {
        this.mDdcThermalThrottlingDataMap = map;
        this.mThermalBrightnessThrottlingDataId = str;
        this.mUniqueDisplayId = str2;
        this.mTempSensor = sensorData;
        resetThermalThrottlingData();
    }

    private float verifyAndConstrainBrightnessCap(float f) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            android.util.Slog.e(TAG, "brightness " + f + " is lower than the minimum possible brightness " + com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            f = 0.0f;
        }
        if (f > 1.0f) {
            android.util.Slog.e(TAG, "brightness " + f + " is higher than the maximum possible brightness 1.0");
            return 1.0f;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void thermalStatusChanged(int i) {
        if (this.mThrottlingStatus != i) {
            this.mThrottlingStatus = i;
            updateThermalThrottling();
        }
    }

    private void updateThermalThrottling() {
        if (!deviceSupportsThrottling()) {
            return;
        }
        float f = 1.0f;
        int i = 0;
        if (this.mThrottlingStatus != -1 && this.mThermalThrottlingData != null) {
            for (com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel : this.mThermalThrottlingData.throttlingLevels) {
                if (throttlingLevel.thermalStatus > this.mThrottlingStatus) {
                    break;
                }
                f = throttlingLevel.brightness;
                i = 1;
            }
        }
        if (this.mBrightnessCap != f || this.mBrightnessMaxReason != i) {
            this.mBrightnessCap = verifyAndConstrainBrightnessCap(f);
            this.mBrightnessMaxReason = i;
            if (DEBUG) {
                android.util.Slog.d(TAG, "State changed: mBrightnessCap = " + this.mBrightnessCap + ", mBrightnessMaxReason = " + android.hardware.display.BrightnessInfo.briMaxReasonToString(this.mBrightnessMaxReason));
            }
            if (this.mThrottlingChangeCallback != null) {
                this.mThrottlingChangeCallback.run();
            }
        }
    }

    void dump(final java.io.PrintWriter printWriter) {
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.BrightnessThrottler.this.lambda$dump$1(printWriter);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public void lambda$dump$1(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessThrottler:");
        printWriter.println("  mThermalBrightnessThrottlingDataId=" + this.mThermalBrightnessThrottlingDataId);
        printWriter.println("  mThermalThrottlingData=" + this.mThermalThrottlingData);
        printWriter.println("  mUniqueDisplayId=" + this.mUniqueDisplayId);
        printWriter.println("  mThrottlingStatus=" + this.mThrottlingStatus);
        printWriter.println("  mBrightnessCap=" + this.mBrightnessCap);
        printWriter.println("  mBrightnessMaxReason=" + android.hardware.display.BrightnessInfo.briMaxReasonToString(this.mBrightnessMaxReason));
        printWriter.println("  mDdcThermalThrottlingDataMap=" + this.mDdcThermalThrottlingDataMap);
        printWriter.println("  mThermalBrightnessThrottlingDataOverride=" + this.mThermalBrightnessThrottlingDataOverride);
        printWriter.println("  mThermalBrightnessThrottlingDataString=" + this.mThermalBrightnessThrottlingDataString);
        this.mSkinThermalStatusObserver.dump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadThermalBrightnessThrottlingDataFromDeviceConfig() {
        this.mThermalBrightnessThrottlingDataString = this.mConfigParameterProvider.getBrightnessThrottlingData();
        this.mThermalBrightnessThrottlingDataOverride.clear();
        if (this.mThermalBrightnessThrottlingDataString != null) {
            this.mThermalBrightnessThrottlingDataOverride.putAll(com.android.server.display.utils.DeviceConfigParsingUtils.parseDeviceConfigMap(this.mThermalBrightnessThrottlingDataString, this.mDataPointMapper, this.mDataSetMapper));
        } else {
            android.util.Slog.w(TAG, "DeviceConfig ThermalBrightnessThrottlingData is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetThermalThrottlingData() {
        stop();
        this.mDeviceConfigListener.startListening();
        this.mThermalThrottlingData = getConfigFromId(this.mThermalBrightnessThrottlingDataId);
        if (!"default".equals(this.mThermalBrightnessThrottlingDataId) && this.mThermalThrottlingData == null) {
            this.mThermalThrottlingData = getConfigFromId("default");
            android.util.Slog.d(TAG, "Falling back to default throttling Id");
        }
        if (deviceSupportsThrottling()) {
            this.mSkinThermalStatusObserver.startObserving(this.mTempSensor);
        }
    }

    private com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData getConfigFromId(java.lang.String str) {
        com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData;
        if (this.mThermalBrightnessThrottlingDataOverride.get(this.mUniqueDisplayId) == null) {
            thermalBrightnessThrottlingData = null;
        } else {
            thermalBrightnessThrottlingData = this.mThermalBrightnessThrottlingDataOverride.get(this.mUniqueDisplayId).get(str);
        }
        if (thermalBrightnessThrottlingData == null) {
            return this.mDdcThermalThrottlingDataMap.get(str);
        }
        return thermalBrightnessThrottlingData;
    }

    public class DeviceConfigListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        public java.util.concurrent.Executor mExecutor;

        public DeviceConfigListener() {
            this.mExecutor = new android.os.HandlerExecutor(com.android.server.display.BrightnessThrottler.this.mDeviceConfigHandler);
        }

        public void startListening() {
            com.android.server.display.BrightnessThrottler.this.mConfigParameterProvider.addOnPropertiesChangedListener(this.mExecutor, this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            com.android.server.display.BrightnessThrottler.this.loadThermalBrightnessThrottlingDataFromDeviceConfig();
            com.android.server.display.BrightnessThrottler.this.resetThermalThrottlingData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SkinThermalStatusObserver extends android.os.IThermalEventListener.Stub {
        private final android.os.Handler mHandler;
        private final com.android.server.display.BrightnessThrottler.Injector mInjector;
        private com.android.server.display.config.SensorData mObserverTempSensor;
        private boolean mStarted;
        private android.os.IThermalService mThermalService;

        SkinThermalStatusObserver(com.android.server.display.BrightnessThrottler.Injector injector, android.os.Handler handler) {
            this.mInjector = injector;
            this.mHandler = handler;
        }

        public void notifyThrottling(final android.os.Temperature temperature) {
            if (com.android.server.display.BrightnessThrottler.DEBUG) {
                android.util.Slog.d(com.android.server.display.BrightnessThrottler.TAG, "New thermal throttling status = " + temperature.getStatus());
            }
            if (this.mObserverTempSensor.name != null && !this.mObserverTempSensor.name.equals(temperature.getName())) {
                android.util.Slog.i(com.android.server.display.BrightnessThrottler.TAG, "Skipping thermal throttling notification as monitored sensor: " + this.mObserverTempSensor.name + " != notified sensor: " + temperature.getName());
                return;
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.BrightnessThrottler$SkinThermalStatusObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.BrightnessThrottler.SkinThermalStatusObserver.this.lambda$notifyThrottling$0(temperature);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyThrottling$0(android.os.Temperature temperature) {
            com.android.server.display.BrightnessThrottler.this.thermalStatusChanged(temperature.getStatus());
        }

        void startObserving(com.android.server.display.config.SensorData sensorData) {
            if (!this.mStarted || this.mObserverTempSensor == null) {
                this.mObserverTempSensor = sensorData;
                registerThermalListener();
                return;
            }
            java.lang.String str = this.mObserverTempSensor.type;
            this.mObserverTempSensor = sensorData;
            if (str.equals(sensorData.type)) {
                if (com.android.server.display.BrightnessThrottler.DEBUG) {
                    android.util.Slog.d(com.android.server.display.BrightnessThrottler.TAG, "Thermal status observer already started");
                }
            } else {
                stopObserving();
                registerThermalListener();
            }
        }

        void registerThermalListener() {
            this.mThermalService = this.mInjector.getThermalService();
            if (this.mThermalService == null) {
                android.util.Slog.e(com.android.server.display.BrightnessThrottler.TAG, "Could not observe thermal status. Service not available");
                return;
            }
            try {
                this.mThermalService.registerThermalEventListenerWithType(this, com.android.server.display.utils.SensorUtils.getSensorTemperatureType(this.mObserverTempSensor));
                this.mStarted = true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.display.BrightnessThrottler.TAG, "Failed to register thermal status listener", e);
            }
        }

        void stopObserving() {
            if (!this.mStarted) {
                if (com.android.server.display.BrightnessThrottler.DEBUG) {
                    android.util.Slog.d(com.android.server.display.BrightnessThrottler.TAG, "Stop skipped because thermal status observer not started");
                }
            } else {
                try {
                    this.mThermalService.unregisterThermalEventListener(this);
                    this.mStarted = false;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.display.BrightnessThrottler.TAG, "Failed to unregister thermal status listener", e);
                }
                this.mThermalService = null;
            }
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  SkinThermalStatusObserver:");
            printWriter.println("    mStarted: " + this.mStarted);
            printWriter.println("    mObserverTempSensor: " + this.mObserverTempSensor);
            if (this.mThermalService != null) {
                printWriter.println("    ThermalService available");
            } else {
                printWriter.println("    ThermalService not available");
            }
        }
    }

    public static class Injector {
        public android.os.IThermalService getThermalService() {
            return android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
        }

        @android.annotation.NonNull
        public android.provider.DeviceConfigInterface getDeviceConfig() {
            return android.provider.DeviceConfigInterface.REAL;
        }
    }
}
