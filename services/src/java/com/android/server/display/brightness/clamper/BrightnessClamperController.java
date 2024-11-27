package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public class BrightnessClamperController {
    private static final java.lang.String TAG = "BrightnessClamperController";
    private float mBrightnessCap;
    private boolean mClamperApplied;
    private final com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener mClamperChangeListenerExternal;

    @android.annotation.Nullable
    private com.android.server.display.brightness.clamper.BrightnessClamper.Type mClamperType;
    private final java.util.List<com.android.server.display.brightness.clamper.BrightnessClamper<? super com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData>> mClampers;
    private float mCustomAnimationRate;
    private final com.android.server.display.feature.DeviceConfigParameterProvider mDeviceConfigParameterProvider;
    private final java.util.concurrent.Executor mExecutor;
    private final android.os.Handler mHandler;
    private final java.util.List<com.android.server.display.brightness.clamper.BrightnessStateModifier> mModifiers;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;

    public interface ClamperChangeListener {
        void onChanged();
    }

    public BrightnessClamperController(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData displayDeviceData, android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this(new com.android.server.display.brightness.clamper.BrightnessClamperController.Injector(), handler, clamperChangeListener, displayDeviceData, context, displayManagerFlags);
    }

    @com.android.internal.annotations.VisibleForTesting
    BrightnessClamperController(com.android.server.display.brightness.clamper.BrightnessClamperController.Injector injector, android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData displayDeviceData, android.content.Context context, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mBrightnessCap = 1.0f;
        this.mCustomAnimationRate = -1.0f;
        this.mClamperType = null;
        this.mClamperApplied = false;
        this.mDeviceConfigParameterProvider = injector.getDeviceConfigParameterProvider();
        this.mHandler = handler;
        this.mClamperChangeListenerExternal = clamperChangeListener;
        this.mExecutor = new android.os.HandlerExecutor(handler);
        final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessClamperController.this.recalculateBrightnessCap();
            }
        };
        this.mClampers = injector.getClampers(handler, new com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda6
            @Override // com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener
            public final void onChanged() {
                com.android.server.display.brightness.clamper.BrightnessClamperController.this.lambda$new$0(runnable);
            }
        }, displayDeviceData, displayManagerFlags, context);
        this.mModifiers = injector.getModifiers(displayManagerFlags, context, handler, clamperChangeListener);
        this.mOnPropertiesChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda7
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.display.brightness.clamper.BrightnessClamperController.this.lambda$new$1(properties);
            }
        };
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.Runnable runnable) {
        if (!this.mHandler.hasCallbacks(runnable)) {
            this.mHandler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.provider.DeviceConfig.Properties properties) {
        this.mClampers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessClamper) obj).onDeviceConfigChanged();
            }
        });
    }

    public void onDisplayChanged(final com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData displayDeviceData) {
        this.mClampers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessClamper) obj).onDisplayChanged(com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData.this);
            }
        });
    }

    public com.android.server.display.DisplayBrightnessState clamp(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, float f, boolean z) {
        float min = java.lang.Math.min(f, this.mBrightnessCap);
        com.android.server.display.DisplayBrightnessState.Builder builder = com.android.server.display.DisplayBrightnessState.builder();
        builder.setIsSlowChange(z);
        builder.setBrightness(min);
        builder.setMaxBrightness(this.mBrightnessCap);
        builder.setCustomAnimationRate(this.mCustomAnimationRate);
        if (this.mClamperType != null) {
            builder.getBrightnessReason().addModifier(8);
            if (!this.mClamperApplied) {
                builder.setIsSlowChange(false);
            }
            this.mClamperApplied = true;
        } else {
            this.mClamperApplied = false;
        }
        for (int i = 0; i < this.mModifiers.size(); i++) {
            this.mModifiers.get(i).apply(displayPowerRequest, builder);
        }
        return builder.build();
    }

    public int getBrightnessMaxReason() {
        if (this.mClamperType == null) {
            return 0;
        }
        if (this.mClamperType == com.android.server.display.brightness.clamper.BrightnessClamper.Type.THERMAL) {
            return 1;
        }
        if (this.mClamperType == com.android.server.display.brightness.clamper.BrightnessClamper.Type.POWER) {
            return 2;
        }
        if (this.mClamperType == com.android.server.display.brightness.clamper.BrightnessClamper.Type.WEAR_BEDTIME_MODE) {
            return 3;
        }
        android.util.Slog.wtf(TAG, "BrightnessMaxReason not mapped for type=" + this.mClamperType);
        return 0;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessClamperController:");
        printWriter.println("  mBrightnessCap: " + this.mBrightnessCap);
        printWriter.println("  mClamperType: " + this.mClamperType);
        printWriter.println("  mClamperApplied: " + this.mClamperApplied);
        final android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
        this.mClampers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessClamper) obj).dump(indentingPrintWriter);
            }
        });
        this.mModifiers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessStateModifier) obj).dump(indentingPrintWriter);
            }
        });
    }

    public void stop() {
        this.mDeviceConfigParameterProvider.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
        this.mClampers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessClamper) obj).stop();
            }
        });
        this.mModifiers.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.brightness.clamper.BrightnessStateModifier) obj).stop();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recalculateBrightnessCap() {
        float f;
        float f2;
        com.android.server.display.brightness.clamper.BrightnessClamper.Type type;
        com.android.server.display.brightness.clamper.BrightnessClamper<? super com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData> orElse = this.mClampers.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.display.brightness.clamper.BrightnessClamper) obj).isActive();
            }
        }).min(new java.util.Comparator() { // from class: com.android.server.display.brightness.clamper.BrightnessClamperController$$ExternalSyntheticLambda10
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$recalculateBrightnessCap$5;
                lambda$recalculateBrightnessCap$5 = com.android.server.display.brightness.clamper.BrightnessClamperController.lambda$recalculateBrightnessCap$5((com.android.server.display.brightness.clamper.BrightnessClamper) obj, (com.android.server.display.brightness.clamper.BrightnessClamper) obj2);
                return lambda$recalculateBrightnessCap$5;
            }
        }).orElse(null);
        if (orElse == null) {
            f = 1.0f;
            f2 = -1.0f;
            type = null;
        } else {
            f = orElse.getBrightnessCap();
            type = orElse.getType();
            f2 = orElse.getCustomAnimationRate();
        }
        if (this.mBrightnessCap != f || this.mClamperType != type || this.mCustomAnimationRate != f2) {
            this.mBrightnessCap = f;
            this.mClamperType = type;
            this.mCustomAnimationRate = f2;
            this.mClamperChangeListenerExternal.onChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$recalculateBrightnessCap$5(com.android.server.display.brightness.clamper.BrightnessClamper brightnessClamper, com.android.server.display.brightness.clamper.BrightnessClamper brightnessClamper2) {
        return java.lang.Float.compare(brightnessClamper.getBrightnessCap(), brightnessClamper2.getBrightnessCap());
    }

    private void start() {
        if (!this.mClampers.isEmpty()) {
            this.mDeviceConfigParameterProvider.addOnPropertiesChangedListener(this.mExecutor, this.mOnPropertiesChangedListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.feature.DeviceConfigParameterProvider getDeviceConfigParameterProvider() {
            return new com.android.server.display.feature.DeviceConfigParameterProvider(android.provider.DeviceConfigInterface.REAL);
        }

        java.util.List<com.android.server.display.brightness.clamper.BrightnessClamper<? super com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData>> getClampers(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessClamperController.DisplayDeviceData displayDeviceData, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.content.Context context) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(new com.android.server.display.brightness.clamper.BrightnessThermalClamper(handler, clamperChangeListener, displayDeviceData));
            if (displayManagerFlags.isPowerThrottlingClamperEnabled()) {
                arrayList.add(new com.android.server.display.brightness.clamper.BrightnessPowerClamper(handler, clamperChangeListener, displayDeviceData));
            }
            if (displayManagerFlags.isBrightnessWearBedtimeModeClamperEnabled()) {
                arrayList.add(new com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper(handler, context, clamperChangeListener, displayDeviceData));
            }
            return arrayList;
        }

        java.util.List<com.android.server.display.brightness.clamper.BrightnessStateModifier> getModifiers(com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.content.Context context, android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(new com.android.server.display.brightness.clamper.DisplayDimModifier(context));
            arrayList.add(new com.android.server.display.brightness.clamper.BrightnessLowPowerModeModifier());
            if (displayManagerFlags.isEvenDimmerEnabled()) {
                arrayList.add(new com.android.server.display.brightness.clamper.BrightnessLowLuxModifier(handler, clamperChangeListener, context));
            }
            return arrayList;
        }
    }

    public static class DisplayDeviceData implements com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData, com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData, com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData {
        private final com.android.server.display.DisplayDeviceConfig mDisplayDeviceConfig;

        @android.annotation.NonNull
        private final java.lang.String mPowerThrottlingDataId;

        @android.annotation.NonNull
        private final java.lang.String mThermalThrottlingDataId;

        @android.annotation.NonNull
        private final java.lang.String mUniqueDisplayId;

        public DisplayDeviceData(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
            this.mUniqueDisplayId = str;
            this.mThermalThrottlingDataId = str2;
            this.mPowerThrottlingDataId = str3;
            this.mDisplayDeviceConfig = displayDeviceConfig;
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData, com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData
        @android.annotation.NonNull
        public java.lang.String getUniqueDisplayId() {
            return this.mUniqueDisplayId;
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData
        @android.annotation.NonNull
        public java.lang.String getThermalThrottlingDataId() {
            return this.mThermalThrottlingDataId;
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData
        @android.annotation.Nullable
        public com.android.server.display.DisplayDeviceConfig.ThermalBrightnessThrottlingData getThermalBrightnessThrottlingData() {
            return this.mDisplayDeviceConfig.getThermalBrightnessThrottlingDataMapByThrottlingId().get(this.mThermalThrottlingDataId);
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData
        @android.annotation.NonNull
        public java.lang.String getPowerThrottlingDataId() {
            return this.mPowerThrottlingDataId;
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData
        @android.annotation.Nullable
        public com.android.server.display.DisplayDeviceConfig.PowerThrottlingData getPowerThrottlingData() {
            return this.mDisplayDeviceConfig.getPowerThrottlingDataMapByThrottlingId().get(this.mPowerThrottlingDataId);
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessPowerClamper.PowerData
        @android.annotation.Nullable
        public com.android.server.display.DisplayDeviceConfig.PowerThrottlingConfigData getPowerThrottlingConfigData() {
            return this.mDisplayDeviceConfig.getPowerThrottlingConfigData();
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData
        public float getBrightnessWearBedtimeModeCap() {
            return this.mDisplayDeviceConfig.getBrightnessCapForWearBedtimeMode();
        }

        @Override // com.android.server.display.brightness.clamper.BrightnessThermalClamper.ThermalData
        @android.annotation.NonNull
        public com.android.server.display.config.SensorData getTempSensor() {
            return this.mDisplayDeviceConfig.getTempSensor();
        }
    }
}
