package com.android.server.display.brightness;

/* loaded from: classes.dex */
public final class DisplayBrightnessController {

    @android.annotation.Nullable
    private com.android.server.display.AutomaticBrightnessController mAutomaticBrightnessController;
    private final android.os.HandlerExecutor mBrightnessChangeExecutor;
    private final com.android.server.display.BrightnessSetting mBrightnessSetting;
    private com.android.server.display.BrightnessSetting.BrightnessSettingListener mBrightnessSettingListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private float mCurrentScreenBrightness;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.display.brightness.strategy.DisplayBrightnessStrategy mDisplayBrightnessStrategy;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.display.brightness.DisplayBrightnessStrategySelector mDisplayBrightnessStrategySelector;
    private final int mDisplayId;
    private java.lang.Runnable mOnBrightnessChangeRunnable;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private float mPendingScreenBrightness;
    private final boolean mPersistBrightnessNitsForDefaultDisplay;
    private final float mScreenBrightnessDefault;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private float mLastUserSetScreenBrightness = Float.NaN;

    public DisplayBrightnessController(android.content.Context context, com.android.server.display.brightness.DisplayBrightnessController.Injector injector, int i, float f, com.android.server.display.BrightnessSetting brightnessSetting, java.lang.Runnable runnable, android.os.HandlerExecutor handlerExecutor, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        injector = injector == null ? new com.android.server.display.brightness.DisplayBrightnessController.Injector() : injector;
        this.mDisplayId = i;
        this.mBrightnessSetting = brightnessSetting;
        this.mPendingScreenBrightness = Float.NaN;
        this.mScreenBrightnessDefault = com.android.server.display.brightness.BrightnessUtils.clampAbsoluteBrightness(f);
        this.mCurrentScreenBrightness = getScreenBrightnessSetting();
        this.mOnBrightnessChangeRunnable = runnable;
        this.mDisplayBrightnessStrategySelector = injector.getDisplayBrightnessStrategySelector(context, i, displayManagerFlags);
        this.mBrightnessChangeExecutor = handlerExecutor;
        this.mPersistBrightnessNitsForDefaultDisplay = context.getResources().getBoolean(android.R.bool.config_overrideRemoteViewsActivityTransition);
    }

    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i) {
        com.android.server.display.DisplayBrightnessState updateBrightness;
        synchronized (this.mLock) {
            this.mDisplayBrightnessStrategy = this.mDisplayBrightnessStrategySelector.selectStrategy(displayPowerRequest, i);
            updateBrightness = this.mDisplayBrightnessStrategy.updateBrightness(displayPowerRequest);
        }
        if (updateBrightness != null) {
            return addAutomaticBrightnessState(updateBrightness);
        }
        return updateBrightness;
    }

    public void setTemporaryBrightness(java.lang.Float f) {
        synchronized (this.mLock) {
            setTemporaryBrightnessLocked(f.floatValue());
        }
    }

    public void setBrightnessToFollow(float f, boolean z) {
        synchronized (this.mLock) {
            this.mDisplayBrightnessStrategySelector.getFollowerDisplayBrightnessStrategy().setBrightnessToFollow(f, z);
        }
    }

    public void setBrightnessFromOffload(float f) {
        synchronized (this.mLock) {
            try {
                if (this.mDisplayBrightnessStrategySelector.getOffloadBrightnessStrategy() != null) {
                    this.mDisplayBrightnessStrategySelector.getOffloadBrightnessStrategy().setOffloadScreenBrightness(f);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAllowAutoBrightnessWhileDozingConfig() {
        boolean isAllowAutoBrightnessWhileDozingConfig;
        synchronized (this.mLock) {
            isAllowAutoBrightnessWhileDozingConfig = this.mDisplayBrightnessStrategySelector.isAllowAutoBrightnessWhileDozingConfig();
        }
        return isAllowAutoBrightnessWhileDozingConfig;
    }

    public void setAndNotifyCurrentScreenBrightness(float f) {
        boolean z;
        synchronized (this.mLock) {
            z = f != this.mCurrentScreenBrightness;
            setCurrentScreenBrightnessLocked(f);
        }
        if (z) {
            notifyCurrentScreenBrightness();
        }
    }

    public float getCurrentBrightness() {
        float f;
        synchronized (this.mLock) {
            f = this.mCurrentScreenBrightness;
        }
        return f;
    }

    public float getPendingScreenBrightness() {
        float f;
        synchronized (this.mLock) {
            f = this.mPendingScreenBrightness;
        }
        return f;
    }

    public void setPendingScreenBrightness(float f) {
        synchronized (this.mLock) {
            this.mPendingScreenBrightness = f;
        }
    }

    public boolean updateUserSetScreenBrightness() {
        synchronized (this.mLock) {
            try {
                if (!com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(this.mPendingScreenBrightness)) {
                    return false;
                }
                if (this.mCurrentScreenBrightness == this.mPendingScreenBrightness) {
                    this.mPendingScreenBrightness = Float.NaN;
                    setTemporaryBrightnessLocked(Float.NaN);
                    return false;
                }
                setCurrentScreenBrightnessLocked(this.mPendingScreenBrightness);
                this.mLastUserSetScreenBrightness = this.mPendingScreenBrightness;
                this.mPendingScreenBrightness = Float.NaN;
                setTemporaryBrightnessLocked(Float.NaN);
                notifyCurrentScreenBrightness();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerBrightnessSettingChangeListener(com.android.server.display.BrightnessSetting.BrightnessSettingListener brightnessSettingListener) {
        this.mBrightnessSettingListener = brightnessSettingListener;
        this.mBrightnessSetting.registerListener(this.mBrightnessSettingListener);
    }

    public float getLastUserSetScreenBrightness() {
        float f;
        synchronized (this.mLock) {
            f = this.mLastUserSetScreenBrightness;
        }
        return f;
    }

    public float getScreenBrightnessSetting() {
        float clampAbsoluteBrightness;
        float brightness = this.mBrightnessSetting.getBrightness();
        synchronized (this.mLock) {
            try {
                if (java.lang.Float.isNaN(brightness)) {
                    brightness = this.mScreenBrightnessDefault;
                }
                clampAbsoluteBrightness = com.android.server.display.brightness.BrightnessUtils.clampAbsoluteBrightness(brightness);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return clampAbsoluteBrightness;
    }

    public void setBrightness(float f) {
        this.mBrightnessSetting.setBrightness(f);
        if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
            float convertToNits = convertToNits(f);
            if (convertToNits >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mBrightnessSetting.setBrightnessNitsForDefaultDisplay(convertToNits);
            }
        }
    }

    public void setBrightness(float f, int i) {
        this.mBrightnessSetting.setUserSerial(i);
        setBrightness(f);
    }

    public void updateScreenBrightnessSetting(float f) {
        synchronized (this.mLock) {
            if (!com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(f) || f == this.mCurrentScreenBrightness) {
                return;
            }
            setCurrentScreenBrightnessLocked(f);
            notifyCurrentScreenBrightness();
            setBrightness(f);
        }
    }

    public void setAutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController automaticBrightnessController) {
        this.mAutomaticBrightnessController = automaticBrightnessController;
        loadNitBasedBrightnessSetting();
    }

    public com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy getAutomaticBrightnessStrategy() {
        return this.mDisplayBrightnessStrategySelector.getAutomaticBrightnessStrategy();
    }

    public float convertToNits(float f) {
        if (this.mAutomaticBrightnessController == null) {
            return -1.0f;
        }
        return this.mAutomaticBrightnessController.convertToNits(f);
    }

    public float convertToAdjustedNits(float f) {
        if (this.mAutomaticBrightnessController == null) {
            return -1.0f;
        }
        return this.mAutomaticBrightnessController.convertToAdjustedNits(f);
    }

    public float getBrightnessFromNits(float f) {
        if (this.mAutomaticBrightnessController == null) {
            return Float.NaN;
        }
        return this.mAutomaticBrightnessController.getBrightnessFromNits(f);
    }

    public void stop() {
        if (this.mBrightnessSetting != null) {
            this.mBrightnessSetting.unregisterListener(this.mBrightnessSettingListener);
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("DisplayBrightnessController:");
        printWriter.println("  mDisplayId=: " + this.mDisplayId);
        printWriter.println("  mScreenBrightnessDefault=" + this.mScreenBrightnessDefault);
        printWriter.println("  mPersistBrightnessNitsForDefaultDisplay=" + this.mPersistBrightnessNitsForDefaultDisplay);
        synchronized (this.mLock) {
            try {
                printWriter.println("  mPendingScreenBrightness=" + this.mPendingScreenBrightness);
                printWriter.println("  mCurrentScreenBrightness=" + this.mCurrentScreenBrightness);
                printWriter.println("  mLastUserSetScreenBrightness=" + this.mLastUserSetScreenBrightness);
                if (this.mDisplayBrightnessStrategy != null) {
                    printWriter.println("  Last selected DisplayBrightnessStrategy= " + this.mDisplayBrightnessStrategy.getName());
                }
                this.mDisplayBrightnessStrategySelector.dump(new android.util.IndentingPrintWriter(printWriter, " "));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        Injector() {
        }

        com.android.server.display.brightness.DisplayBrightnessStrategySelector getDisplayBrightnessStrategySelector(android.content.Context context, int i, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            return new com.android.server.display.brightness.DisplayBrightnessStrategySelector(context, null, i, displayManagerFlags);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.BrightnessSetting.BrightnessSettingListener getBrightnessSettingListener() {
        return this.mBrightnessSettingListener;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.brightness.strategy.DisplayBrightnessStrategy getCurrentDisplayBrightnessStrategy() {
        com.android.server.display.brightness.strategy.DisplayBrightnessStrategy displayBrightnessStrategy;
        synchronized (this.mLock) {
            displayBrightnessStrategy = this.mDisplayBrightnessStrategy;
        }
        return displayBrightnessStrategy;
    }

    private com.android.server.display.DisplayBrightnessState addAutomaticBrightnessState(com.android.server.display.DisplayBrightnessState displayBrightnessState) {
        com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy automaticBrightnessStrategy = getAutomaticBrightnessStrategy();
        com.android.server.display.DisplayBrightnessState.Builder from = com.android.server.display.DisplayBrightnessState.Builder.from(displayBrightnessState);
        from.setShouldUseAutoBrightness(automaticBrightnessStrategy != null && automaticBrightnessStrategy.shouldUseAutoBrightness());
        return from.build();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setTemporaryBrightnessLocked(float f) {
        this.mDisplayBrightnessStrategySelector.getTemporaryDisplayBrightnessStrategy().setTemporaryScreenBrightness(f);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setCurrentScreenBrightnessLocked(float f) {
        if (f != this.mCurrentScreenBrightness) {
            this.mCurrentScreenBrightness = f;
        }
    }

    private void notifyCurrentScreenBrightness() {
        this.mBrightnessChangeExecutor.execute(this.mOnBrightnessChangeRunnable);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void loadNitBasedBrightnessSetting() {
        float f;
        if (this.mDisplayId == 0 && this.mPersistBrightnessNitsForDefaultDisplay) {
            float brightnessNitsForDefaultDisplay = this.mBrightnessSetting.getBrightnessNitsForDefaultDisplay();
            if (brightnessNitsForDefaultDisplay >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                f = getBrightnessFromNits(brightnessNitsForDefaultDisplay);
                if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(f)) {
                    this.mBrightnessSetting.setBrightness(f);
                    if (java.lang.Float.isNaN(f)) {
                        f = getScreenBrightnessSetting();
                    }
                    synchronized (this.mLock) {
                        this.mCurrentScreenBrightness = f;
                    }
                    return;
                }
            }
        }
        f = Float.NaN;
        if (java.lang.Float.isNaN(f)) {
        }
        synchronized (this.mLock) {
        }
    }
}
