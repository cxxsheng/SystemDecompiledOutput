package com.android.server.display;

/* loaded from: classes.dex */
class BrightnessRangeController {
    private final com.android.server.display.HighBrightnessModeController mHbmController;
    private final com.android.server.display.brightness.clamper.HdrClamper mHdrClamper;
    private final java.lang.Runnable mModeChangeCallback;
    private final com.android.server.display.NormalBrightnessModeController mNormalBrightnessModeController;
    private final boolean mUseHdrClamper;
    private final boolean mUseNbmController;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BrightnessRangeController(com.android.server.display.HighBrightnessModeController highBrightnessModeController, java.lang.Runnable runnable, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, android.os.Handler handler, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.os.IBinder iBinder, com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        this(highBrightnessModeController, runnable, displayDeviceConfig, new com.android.server.display.brightness.clamper.HdrClamper(new com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda5(runnable), new android.os.Handler(handler.getLooper())), displayManagerFlags, iBinder, displayDeviceInfo);
        java.util.Objects.requireNonNull(runnable);
    }

    BrightnessRangeController(com.android.server.display.HighBrightnessModeController highBrightnessModeController, java.lang.Runnable runnable, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, com.android.server.display.brightness.clamper.HdrClamper hdrClamper, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.os.IBinder iBinder, com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        this.mNormalBrightnessModeController = new com.android.server.display.NormalBrightnessModeController();
        this.mHbmController = highBrightnessModeController;
        this.mModeChangeCallback = runnable;
        this.mHdrClamper = hdrClamper;
        this.mUseHdrClamper = displayManagerFlags.isHdrClamperEnabled();
        this.mUseNbmController = displayManagerFlags.isNbmControllerEnabled();
        if (this.mUseNbmController) {
            this.mNormalBrightnessModeController.resetNbmData(displayDeviceConfig.getLuxThrottlingData());
        }
        updateHdrClamper(displayDeviceInfo, iBinder, displayDeviceConfig);
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessRangeController:");
        printWriter.println("  mUseNormalBrightnessController=" + this.mUseNbmController);
        printWriter.println("  mUseHdrClamper=" + this.mUseHdrClamper);
        this.mHbmController.dump(printWriter);
        this.mNormalBrightnessModeController.dump(printWriter);
        this.mHdrClamper.dump(printWriter);
    }

    void onAmbientLuxChange(final float f) {
        applyChanges(new java.util.function.BooleanSupplier() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$onAmbientLuxChange$0;
                lambda$onAmbientLuxChange$0 = com.android.server.display.BrightnessRangeController.this.lambda$onAmbientLuxChange$0(f);
                return lambda$onAmbientLuxChange$0;
            }
        }, new java.lang.Runnable() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.BrightnessRangeController.this.lambda$onAmbientLuxChange$1(f);
            }
        });
        if (this.mUseHdrClamper) {
            this.mHdrClamper.onAmbientLuxChange(f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onAmbientLuxChange$0(float f) {
        return this.mNormalBrightnessModeController.onAmbientLuxChange(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAmbientLuxChange$1(float f) {
        this.mHbmController.onAmbientLuxChange(f);
    }

    float getNormalBrightnessMax() {
        return this.mHbmController.getNormalBrightnessMax();
    }

    void loadFromConfig(final com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, final android.os.IBinder iBinder, final com.android.server.display.DisplayDeviceInfo displayDeviceInfo, final com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        applyChanges(new java.util.function.BooleanSupplier() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$loadFromConfig$2;
                lambda$loadFromConfig$2 = com.android.server.display.BrightnessRangeController.this.lambda$loadFromConfig$2(displayDeviceConfig);
                return lambda$loadFromConfig$2;
            }
        }, new java.lang.Runnable() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.BrightnessRangeController.this.lambda$loadFromConfig$3(highBrightnessModeMetadata, displayDeviceInfo, iBinder, displayDeviceConfig);
            }
        });
        updateHdrClamper(displayDeviceInfo, iBinder, displayDeviceConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$loadFromConfig$2(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        return this.mNormalBrightnessModeController.resetNbmData(displayDeviceConfig.getLuxThrottlingData());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadFromConfig$3(com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, com.android.server.display.DisplayDeviceInfo displayDeviceInfo, android.os.IBinder iBinder, final com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        this.mHbmController.setHighBrightnessModeMetadata(highBrightnessModeMetadata);
        com.android.server.display.HighBrightnessModeController highBrightnessModeController = this.mHbmController;
        int i = displayDeviceInfo.width;
        int i2 = displayDeviceInfo.height;
        java.lang.String str = displayDeviceInfo.uniqueId;
        com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig.getHighBrightnessModeData();
        java.util.Objects.requireNonNull(displayDeviceConfig);
        highBrightnessModeController.resetHbmData(i, i2, iBinder, str, highBrightnessModeData, new com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda4
            @Override // com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig
            public final float getHdrBrightnessFromSdr(float f, float f2) {
                return com.android.server.display.DisplayDeviceConfig.this.getHdrBrightnessFromSdr(f, f2);
            }
        });
    }

    void stop() {
        this.mHbmController.stop();
        this.mHdrClamper.stop();
    }

    void setAutoBrightnessEnabled(final int i) {
        applyChanges(new java.util.function.BooleanSupplier() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda6
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                boolean lambda$setAutoBrightnessEnabled$4;
                lambda$setAutoBrightnessEnabled$4 = com.android.server.display.BrightnessRangeController.this.lambda$setAutoBrightnessEnabled$4(i);
                return lambda$setAutoBrightnessEnabled$4;
            }
        }, new java.lang.Runnable() { // from class: com.android.server.display.BrightnessRangeController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.BrightnessRangeController.this.lambda$setAutoBrightnessEnabled$5(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setAutoBrightnessEnabled$4(int i) {
        return this.mNormalBrightnessModeController.setAutoBrightnessState(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAutoBrightnessEnabled$5(int i) {
        this.mHbmController.setAutoBrightnessEnabled(i);
    }

    void onBrightnessChanged(float f, float f2, int i) {
        this.mHbmController.onBrightnessChanged(f, f2, i);
    }

    float getCurrentBrightnessMin() {
        return this.mHbmController.getCurrentBrightnessMin();
    }

    float getCurrentBrightnessMax() {
        if (this.mUseNbmController && this.mHbmController.getHighBrightnessMode() == 0) {
            return java.lang.Math.min(this.mHbmController.getCurrentBrightnessMax(), this.mNormalBrightnessModeController.getCurrentBrightnessMax());
        }
        return this.mHbmController.getCurrentBrightnessMax();
    }

    int getHighBrightnessMode() {
        return this.mHbmController.getHighBrightnessMode();
    }

    float getHdrBrightnessValue() {
        return java.lang.Math.min(this.mHbmController.getHdrBrightnessValue(), this.mUseHdrClamper ? this.mHdrClamper.getMaxBrightness() : 1.0f);
    }

    float getTransitionPoint() {
        return this.mHbmController.getTransitionPoint();
    }

    private void updateHdrClamper(com.android.server.display.DisplayDeviceInfo displayDeviceInfo, android.os.IBinder iBinder, com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        if (this.mUseHdrClamper) {
            com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = displayDeviceConfig.getHighBrightnessModeData();
            this.mHdrClamper.resetHdrConfig(displayDeviceConfig.getHdrBrightnessData(), displayDeviceInfo.width, displayDeviceInfo.height, highBrightnessModeData == null ? -1.0f : highBrightnessModeData.minimumHdrPercentOfScreen, iBinder);
        }
    }

    private void applyChanges(java.util.function.BooleanSupplier booleanSupplier, java.lang.Runnable runnable) {
        if (this.mUseNbmController) {
            boolean asBoolean = booleanSupplier.getAsBoolean();
            runnable.run();
            if (asBoolean) {
                this.mModeChangeCallback.run();
                return;
            }
            return;
        }
        runnable.run();
    }

    public float getHdrTransitionRate() {
        if (this.mUseHdrClamper) {
            return this.mHdrClamper.getTransitionRate();
        }
        return -1.0f;
    }
}
