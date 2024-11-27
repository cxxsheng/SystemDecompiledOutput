package com.android.server.display;

/* loaded from: classes.dex */
class HighBrightnessModeController {
    private static final float DEFAULT_MAX_DESIRED_HDR_SDR_RATIO = 1.0f;

    @com.android.internal.annotations.VisibleForTesting
    static final float HBM_TRANSITION_POINT_INVALID = Float.POSITIVE_INFINITY;
    private float mAmbientLux;
    private float mBrightness;
    private final float mBrightnessMax;
    private final float mBrightnessMin;
    private final com.android.server.display.DisplayManagerService.Clock mClock;
    private final android.content.Context mContext;
    private int mDisplayStatsId;
    private boolean mForceHbmChangeCallback;
    private final android.os.Handler mHandler;
    private final java.lang.Runnable mHbmChangeCallback;

    @android.annotation.Nullable
    private com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData mHbmData;
    private int mHbmMode;
    private int mHbmStatsState;
    private com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig mHdrBrightnessCfg;
    private com.android.server.display.HighBrightnessModeController.HdrListener mHdrListener;
    private int mHeight;

    @android.annotation.Nullable
    private com.android.server.display.HighBrightnessModeMetadata mHighBrightnessModeMetadata;
    private final com.android.server.display.HighBrightnessModeController.Injector mInjector;
    private boolean mIsAutoBrightnessEnabled;
    private boolean mIsAutoBrightnessOffByState;
    private boolean mIsBlockedByLowPowerMode;
    private boolean mIsHdrLayerPresent;
    private boolean mIsInAllowedAmbientRange;
    private boolean mIsTimeAvailable;
    private float mMaxDesiredHdrSdrRatio;
    private final java.lang.Runnable mRecalcRunnable;
    private android.os.IBinder mRegisteredDisplayToken;
    private final com.android.server.display.HighBrightnessModeController.SettingsObserver mSettingsObserver;
    private int mThrottlingReason;
    private float mUnthrottledBrightness;
    private int mWidth;
    private static final java.lang.String TAG = "HighBrightnessModeController";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    public interface HdrBrightnessDeviceConfig {
        float getHdrBrightnessFromSdr(float f, float f2);
    }

    HighBrightnessModeController(android.os.Handler handler, int i, int i2, android.os.IBinder iBinder, java.lang.String str, float f, float f2, com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig, java.lang.Runnable runnable, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, android.content.Context context) {
        this(new com.android.server.display.HighBrightnessModeController.Injector(), handler, i, i2, iBinder, str, f, f2, highBrightnessModeData, hdrBrightnessDeviceConfig, runnable, highBrightnessModeMetadata, context);
    }

    @com.android.internal.annotations.VisibleForTesting
    HighBrightnessModeController(com.android.server.display.HighBrightnessModeController.Injector injector, android.os.Handler handler, int i, int i2, android.os.IBinder iBinder, java.lang.String str, float f, float f2, com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig, java.lang.Runnable runnable, com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata, android.content.Context context) {
        this.mIsInAllowedAmbientRange = false;
        this.mIsTimeAvailable = false;
        this.mIsAutoBrightnessEnabled = false;
        this.mIsAutoBrightnessOffByState = false;
        this.mThrottlingReason = 0;
        this.mHbmMode = 0;
        this.mIsHdrLayerPresent = false;
        this.mMaxDesiredHdrSdrRatio = 1.0f;
        this.mForceHbmChangeCallback = false;
        this.mIsBlockedByLowPowerMode = false;
        this.mHbmStatsState = 1;
        this.mInjector = injector;
        this.mContext = context;
        this.mClock = injector.getClock();
        this.mHandler = handler;
        this.mBrightness = f;
        this.mBrightnessMin = f;
        this.mBrightnessMax = f2;
        this.mHbmChangeCallback = runnable;
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
        this.mSettingsObserver = new com.android.server.display.HighBrightnessModeController.SettingsObserver(this.mHandler);
        this.mRecalcRunnable = new java.lang.Runnable() { // from class: com.android.server.display.HighBrightnessModeController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.HighBrightnessModeController.this.recalculateTimeAllowance();
            }
        };
        this.mHdrListener = new com.android.server.display.HighBrightnessModeController.HdrListener();
        resetHbmData(i, i2, iBinder, str, highBrightnessModeData, hdrBrightnessDeviceConfig);
    }

    void setAutoBrightnessEnabled(int i) {
        boolean z = i == 1;
        this.mIsAutoBrightnessOffByState = i == 3;
        if (!deviceSupportsHbm() || z == this.mIsAutoBrightnessEnabled) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "setAutoBrightnessEnabled( " + z + " )");
        }
        this.mIsAutoBrightnessEnabled = z;
        this.mIsInAllowedAmbientRange = false;
        recalculateTimeAllowance();
    }

    float getCurrentBrightnessMin() {
        return this.mBrightnessMin;
    }

    float getCurrentBrightnessMax() {
        if (!deviceSupportsHbm() || isCurrentlyAllowed()) {
            return this.mBrightnessMax;
        }
        return this.mHbmData.transitionPoint;
    }

    float getNormalBrightnessMax() {
        return deviceSupportsHbm() ? this.mHbmData.transitionPoint : this.mBrightnessMax;
    }

    float getHdrBrightnessValue() {
        if (this.mHdrBrightnessCfg != null) {
            float hdrBrightnessFromSdr = this.mHdrBrightnessCfg.getHdrBrightnessFromSdr(this.mBrightness, this.mMaxDesiredHdrSdrRatio);
            if (hdrBrightnessFromSdr != -1.0f) {
                return hdrBrightnessFromSdr;
            }
        }
        return android.util.MathUtils.map(getCurrentBrightnessMin(), getCurrentBrightnessMax(), this.mBrightnessMin, this.mBrightnessMax, this.mBrightness);
    }

    void onAmbientLuxChange(float f) {
        this.mAmbientLux = f;
        if (!deviceSupportsHbm() || !this.mIsAutoBrightnessEnabled) {
            return;
        }
        boolean z = f >= this.mHbmData.minimumLux;
        if (z != this.mIsInAllowedAmbientRange) {
            this.mIsInAllowedAmbientRange = z;
            recalculateTimeAllowance();
        }
    }

    void onBrightnessChanged(float f, float f2, int i) {
        if (!deviceSupportsHbm()) {
            return;
        }
        this.mBrightness = f;
        this.mUnthrottledBrightness = f2;
        this.mThrottlingReason = i;
        long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
        boolean z = false;
        boolean z2 = runningStartTimeMillis != -1;
        if (this.mBrightness > this.mHbmData.transitionPoint && !this.mIsHdrLayerPresent) {
            z = true;
        }
        if (z2 != z) {
            long uptimeMillis = this.mClock.uptimeMillis();
            if (z) {
                this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(uptimeMillis);
            } else {
                this.mHighBrightnessModeMetadata.addHbmEvent(new com.android.server.display.HbmEvent(runningStartTimeMillis, uptimeMillis));
                this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(-1L);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "New HBM event: " + this.mHighBrightnessModeMetadata.getHbmEventQueue().peekFirst());
                }
            }
        }
        recalculateTimeAllowance();
    }

    int getHighBrightnessMode() {
        return this.mHbmMode;
    }

    float getTransitionPoint() {
        if (deviceSupportsHbm()) {
            return this.mHbmData.transitionPoint;
        }
        return HBM_TRANSITION_POINT_INVALID;
    }

    void stop() {
        registerHdrListener(null);
        this.mSettingsObserver.stopObserving();
    }

    void setHighBrightnessModeMetadata(com.android.server.display.HighBrightnessModeMetadata highBrightnessModeMetadata) {
        this.mHighBrightnessModeMetadata = highBrightnessModeMetadata;
    }

    void resetHbmData(int i, int i2, android.os.IBinder iBinder, java.lang.String str, com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData, com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mHbmData = highBrightnessModeData;
        this.mHdrBrightnessCfg = hdrBrightnessDeviceConfig;
        this.mDisplayStatsId = str.hashCode();
        unregisterHdrListener();
        this.mSettingsObserver.stopObserving();
        if (deviceSupportsHbm()) {
            registerHdrListener(iBinder);
            recalculateTimeAllowance();
            if (!this.mHbmData.allowInLowPowerMode) {
                this.mIsBlockedByLowPowerMode = false;
                this.mSettingsObserver.startObserving();
            }
        }
    }

    void dump(final java.io.PrintWriter printWriter) {
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.display.HighBrightnessModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.HighBrightnessModeController.this.lambda$dump$0(printWriter);
            }
        }, 1000L);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.HighBrightnessModeController.HdrListener getHdrListener() {
        return this.mHdrListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public void lambda$dump$0(java.io.PrintWriter printWriter) {
        java.lang.String str;
        printWriter.println("HighBrightnessModeController:");
        printWriter.println("  mBrightness=" + this.mBrightness);
        printWriter.println("  mUnthrottledBrightness=" + this.mUnthrottledBrightness);
        printWriter.println("  mThrottlingReason=" + android.hardware.display.BrightnessInfo.briMaxReasonToString(this.mThrottlingReason));
        printWriter.println("  mCurrentMin=" + getCurrentBrightnessMin());
        printWriter.println("  mCurrentMax=" + getCurrentBrightnessMax());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("  mHbmMode=");
        sb.append(android.hardware.display.BrightnessInfo.hbmToString(this.mHbmMode));
        if (this.mHbmMode == 2) {
            str = "(" + getHdrBrightnessValue() + ")";
        } else {
            str = "";
        }
        sb.append(str);
        printWriter.println(sb.toString());
        printWriter.println("  mHbmStatsState=" + hbmStatsStateToString(this.mHbmStatsState));
        printWriter.println("  mHbmData=" + this.mHbmData);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("  mAmbientLux=");
        sb2.append(this.mAmbientLux);
        sb2.append(this.mIsAutoBrightnessEnabled ? "" : " (old/invalid)");
        printWriter.println(sb2.toString());
        printWriter.println("  mIsInAllowedAmbientRange=" + this.mIsInAllowedAmbientRange);
        printWriter.println("  mIsAutoBrightnessEnabled=" + this.mIsAutoBrightnessEnabled);
        printWriter.println("  mIsAutoBrightnessOffByState=" + this.mIsAutoBrightnessOffByState);
        printWriter.println("  mIsHdrLayerPresent=" + this.mIsHdrLayerPresent);
        printWriter.println("  mBrightnessMin=" + this.mBrightnessMin);
        printWriter.println("  mBrightnessMax=" + this.mBrightnessMax);
        printWriter.println("  remainingTime=" + calculateRemainingTime(this.mClock.uptimeMillis()));
        printWriter.println("  mIsTimeAvailable= " + this.mIsTimeAvailable);
        printWriter.println("  mIsBlockedByLowPowerMode=" + this.mIsBlockedByLowPowerMode);
        printWriter.println("  width*height=" + this.mWidth + com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER + this.mHeight);
        if (this.mHighBrightnessModeMetadata != null) {
            printWriter.println("  mRunningStartTimeMillis=" + android.util.TimeUtils.formatUptime(this.mHighBrightnessModeMetadata.getRunningStartTimeMillis()));
            printWriter.println("  mEvents=");
            long uptimeMillis = this.mClock.uptimeMillis();
            long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
            if (runningStartTimeMillis != -1) {
                uptimeMillis = dumpHbmEvent(printWriter, new com.android.server.display.HbmEvent(runningStartTimeMillis, uptimeMillis));
            }
            java.util.Iterator<com.android.server.display.HbmEvent> it = this.mHighBrightnessModeMetadata.getHbmEventQueue().iterator();
            while (it.hasNext()) {
                com.android.server.display.HbmEvent next = it.next();
                if (uptimeMillis > next.getEndTimeMillis()) {
                    printWriter.println("    event: [normal brightness]: " + android.util.TimeUtils.formatDuration(uptimeMillis - next.getEndTimeMillis()));
                }
                uptimeMillis = dumpHbmEvent(printWriter, next);
            }
            return;
        }
        printWriter.println("  mHighBrightnessModeMetadata=null");
    }

    private long dumpHbmEvent(java.io.PrintWriter printWriter, com.android.server.display.HbmEvent hbmEvent) {
        printWriter.println("    event: [" + android.util.TimeUtils.formatUptime(hbmEvent.getStartTimeMillis()) + ", " + android.util.TimeUtils.formatUptime(hbmEvent.getEndTimeMillis()) + "] (" + android.util.TimeUtils.formatDuration(hbmEvent.getEndTimeMillis() - hbmEvent.getStartTimeMillis()) + ")");
        return hbmEvent.getStartTimeMillis();
    }

    private boolean isCurrentlyAllowed() {
        return !this.mIsHdrLayerPresent && this.mIsAutoBrightnessEnabled && this.mIsTimeAvailable && this.mIsInAllowedAmbientRange && !this.mIsBlockedByLowPowerMode;
    }

    private boolean deviceSupportsHbm() {
        return (this.mHbmData == null || this.mHighBrightnessModeMetadata == null) ? false : true;
    }

    private long calculateRemainingTime(long j) {
        long j2;
        if (!deviceSupportsHbm()) {
            return 0L;
        }
        long runningStartTimeMillis = this.mHighBrightnessModeMetadata.getRunningStartTimeMillis();
        if (runningStartTimeMillis <= 0) {
            j2 = 0;
        } else {
            if (runningStartTimeMillis > j) {
                android.util.Slog.e(TAG, "Start time set to the future. curr: " + j + ", start: " + runningStartTimeMillis);
                this.mHighBrightnessModeMetadata.setRunningStartTimeMillis(j);
                runningStartTimeMillis = j;
            }
            j2 = j - runningStartTimeMillis;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Time already used after current session: " + j2);
        }
        long j3 = j - this.mHbmData.timeWindowMillis;
        java.util.Iterator<com.android.server.display.HbmEvent> it = this.mHighBrightnessModeMetadata.getHbmEventQueue().iterator();
        while (it.hasNext()) {
            com.android.server.display.HbmEvent next = it.next();
            if (next.getEndTimeMillis() < j3) {
                it.remove();
            } else {
                j2 += next.getEndTimeMillis() - java.lang.Math.max(next.getStartTimeMillis(), j3);
            }
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Time already used after all sessions: " + j2);
        }
        return java.lang.Math.max(0L, this.mHbmData.timeMaxMillis - j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recalculateTimeAllowance() {
        long j;
        long uptimeMillis = this.mClock.uptimeMillis();
        long calculateRemainingTime = calculateRemainingTime(uptimeMillis);
        boolean z = calculateRemainingTime >= this.mHbmData.timeMinMillis;
        boolean z2 = !z && calculateRemainingTime > 0 && this.mBrightness > this.mHbmData.transitionPoint;
        this.mIsTimeAvailable = z || z2;
        java.util.ArrayDeque<com.android.server.display.HbmEvent> hbmEventQueue = this.mHighBrightnessModeMetadata.getHbmEventQueue();
        if (this.mBrightness > this.mHbmData.transitionPoint) {
            j = uptimeMillis + calculateRemainingTime;
        } else if (!this.mIsTimeAvailable && hbmEventQueue.size() > 0) {
            long j2 = uptimeMillis - this.mHbmData.timeWindowMillis;
            j = (((java.lang.Math.max(j2, hbmEventQueue.peekLast().getStartTimeMillis()) + this.mHbmData.timeMinMillis) - j2) + uptimeMillis) - calculateRemainingTime;
        } else {
            j = -1;
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("HBM recalculated.  IsAllowedWithoutRestrictions: ");
            sb.append(z);
            sb.append(", isOnlyAllowedToStayOn: ");
            sb.append(z2);
            sb.append(", remainingAllowedTime: ");
            sb.append(calculateRemainingTime);
            sb.append(", isLuxHigh: ");
            sb.append(this.mIsInAllowedAmbientRange);
            sb.append(", isHBMCurrentlyAllowed: ");
            sb.append(isCurrentlyAllowed());
            sb.append(", isHdrLayerPresent: ");
            sb.append(this.mIsHdrLayerPresent);
            sb.append(", mMaxDesiredHdrSdrRatio: ");
            sb.append(this.mMaxDesiredHdrSdrRatio);
            sb.append(", isAutoBrightnessEnabled: ");
            sb.append(this.mIsAutoBrightnessEnabled);
            sb.append(", mIsTimeAvailable: ");
            sb.append(this.mIsTimeAvailable);
            sb.append(", mIsInAllowedAmbientRange: ");
            sb.append(this.mIsInAllowedAmbientRange);
            sb.append(", mIsBlockedByLowPowerMode: ");
            sb.append(this.mIsBlockedByLowPowerMode);
            sb.append(", mBrightness: ");
            sb.append(this.mBrightness);
            sb.append(", mUnthrottledBrightness: ");
            sb.append(this.mUnthrottledBrightness);
            sb.append(", mThrottlingReason: ");
            sb.append(android.hardware.display.BrightnessInfo.briMaxReasonToString(this.mThrottlingReason));
            sb.append(", RunningStartTimeMillis: ");
            sb.append(this.mHighBrightnessModeMetadata.getRunningStartTimeMillis());
            sb.append(", nextTimeout: ");
            sb.append(j != -1 ? j - uptimeMillis : -1L);
            sb.append(", events: ");
            sb.append(hbmEventQueue);
            android.util.Slog.d(TAG, sb.toString());
        }
        if (j != -1) {
            this.mHandler.removeCallbacks(this.mRecalcRunnable);
            this.mHandler.postAtTime(this.mRecalcRunnable, j + 1);
        }
        updateHbmMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHbmMode() {
        int calculateHighBrightnessMode = calculateHighBrightnessMode();
        updateHbmStats(calculateHighBrightnessMode);
        if (this.mHbmMode != calculateHighBrightnessMode || this.mForceHbmChangeCallback) {
            this.mForceHbmChangeCallback = false;
            this.mHbmMode = calculateHighBrightnessMode;
            this.mHbmChangeCallback.run();
        }
    }

    private void updateHbmStats(int i) {
        int i2;
        int i3 = 3;
        if (i == 2 && getHdrBrightnessValue() > this.mHbmData.transitionPoint) {
            i2 = 2;
        } else if (i == 1 && this.mBrightness > this.mHbmData.transitionPoint) {
            i2 = 3;
        } else {
            i2 = 1;
        }
        if (i2 != this.mHbmStatsState) {
            boolean z = this.mHbmStatsState == 3;
            boolean z2 = i2 == 3;
            if (z && !z2) {
                if (!this.mIsAutoBrightnessEnabled && this.mIsAutoBrightnessOffByState) {
                    i3 = 6;
                } else if (!this.mIsAutoBrightnessEnabled) {
                    i3 = 7;
                } else if (!this.mIsInAllowedAmbientRange) {
                    i3 = 1;
                } else if (!this.mIsTimeAvailable) {
                    i3 = 2;
                } else if (!isThermalThrottlingActive()) {
                    if (this.mIsHdrLayerPresent) {
                        i3 = 4;
                    } else if (this.mIsBlockedByLowPowerMode) {
                        i3 = 5;
                    } else if (this.mBrightness <= this.mHbmData.transitionPoint) {
                        i3 = 9;
                    }
                }
                this.mInjector.reportHbmStateChange(this.mDisplayStatsId, i2, i3);
                this.mHbmStatsState = i2;
            }
            i3 = 0;
            this.mInjector.reportHbmStateChange(this.mDisplayStatsId, i2, i3);
            this.mHbmStatsState = i2;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isThermalThrottlingActive() {
        return this.mUnthrottledBrightness > this.mHbmData.transitionPoint && this.mBrightness <= this.mHbmData.transitionPoint && this.mThrottlingReason == 1;
    }

    private java.lang.String hbmStatsStateToString(int i) {
        switch (i) {
            case 1:
                return "HBM_OFF";
            case 2:
                return "HBM_ON_HDR";
            case 3:
                return "HBM_ON_SUNLIGHT";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    private int calculateHighBrightnessMode() {
        if (!deviceSupportsHbm()) {
            return 0;
        }
        if (this.mIsHdrLayerPresent) {
            return 2;
        }
        return isCurrentlyAllowed() ? 1 : 0;
    }

    private void registerHdrListener(android.os.IBinder iBinder) {
        if (this.mRegisteredDisplayToken == iBinder) {
            return;
        }
        unregisterHdrListener();
        this.mRegisteredDisplayToken = iBinder;
        if (this.mRegisteredDisplayToken != null) {
            this.mHdrListener.register(this.mRegisteredDisplayToken);
        }
    }

    private void unregisterHdrListener() {
        if (this.mRegisteredDisplayToken != null) {
            this.mHdrListener.unregister(this.mRegisteredDisplayToken);
            this.mIsHdrLayerPresent = false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class HdrListener extends android.view.SurfaceControlHdrLayerInfoListener {
        HdrListener() {
        }

        public void onHdrInfoChanged(android.os.IBinder iBinder, final int i, final int i2, final int i3, int i4, final float f) {
            com.android.server.display.HighBrightnessModeController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.HighBrightnessModeController$HdrListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.HighBrightnessModeController.HdrListener.this.lambda$onHdrInfoChanged$0(i, i2, i3, f);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHdrInfoChanged$0(int i, int i2, int i3, float f) {
            android.os.Trace.traceBegin(131072L, "HBMController#onHdrInfoChanged");
            com.android.server.display.HighBrightnessModeController.this.mIsHdrLayerPresent = i > 0 && ((float) (i2 * i3)) >= ((float) (com.android.server.display.HighBrightnessModeController.this.mWidth * com.android.server.display.HighBrightnessModeController.this.mHeight)) * com.android.server.display.HighBrightnessModeController.this.mHbmData.minimumHdrPercentOfScreen;
            float f2 = 1.0f;
            if (!com.android.server.display.HighBrightnessModeController.this.mIsHdrLayerPresent || com.android.server.display.HighBrightnessModeController.this.mHdrBrightnessCfg == null) {
                f = 1.0f;
            }
            if (f >= 1.0f) {
                f2 = f;
            } else {
                android.util.Slog.w(com.android.server.display.HighBrightnessModeController.TAG, "Ignoring invalid desired HDR/SDR Ratio: " + f);
            }
            if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(com.android.server.display.HighBrightnessModeController.this.mMaxDesiredHdrSdrRatio, f2)) {
                com.android.server.display.HighBrightnessModeController.this.mForceHbmChangeCallback = true;
                com.android.server.display.HighBrightnessModeController.this.mMaxDesiredHdrSdrRatio = f2;
            }
            com.android.server.display.HighBrightnessModeController.this.onBrightnessChanged(com.android.server.display.HighBrightnessModeController.this.mBrightness, com.android.server.display.HighBrightnessModeController.this.mUnthrottledBrightness, com.android.server.display.HighBrightnessModeController.this.mThrottlingReason);
            android.os.Trace.traceEnd(131072L);
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri mLowPowerModeSetting;
        private boolean mStarted;

        SettingsObserver(android.os.Handler handler) {
            super(handler);
            this.mLowPowerModeSetting = android.provider.Settings.Global.getUriFor("low_power");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            updateLowPower();
        }

        void startObserving() {
            if (!this.mStarted) {
                com.android.server.display.HighBrightnessModeController.this.mContext.getContentResolver().registerContentObserver(this.mLowPowerModeSetting, false, this, -1);
                this.mStarted = true;
                updateLowPower();
            }
        }

        void stopObserving() {
            com.android.server.display.HighBrightnessModeController.this.mIsBlockedByLowPowerMode = false;
            if (this.mStarted) {
                com.android.server.display.HighBrightnessModeController.this.mContext.getContentResolver().unregisterContentObserver(this);
                this.mStarted = false;
            }
        }

        private void updateLowPower() {
            boolean isLowPowerMode = isLowPowerMode();
            if (isLowPowerMode == com.android.server.display.HighBrightnessModeController.this.mIsBlockedByLowPowerMode) {
                return;
            }
            if (com.android.server.display.HighBrightnessModeController.DEBUG) {
                android.util.Slog.d(com.android.server.display.HighBrightnessModeController.TAG, "Settings.Global.LOW_POWER_MODE enabled: " + isLowPowerMode);
            }
            com.android.server.display.HighBrightnessModeController.this.mIsBlockedByLowPowerMode = isLowPowerMode;
            com.android.server.display.HighBrightnessModeController.this.updateHbmMode();
        }

        private boolean isLowPowerMode() {
            return android.provider.Settings.Global.getInt(com.android.server.display.HighBrightnessModeController.this.mContext.getContentResolver(), "low_power", 0) != 0;
        }
    }

    public static class Injector {
        public com.android.server.display.DisplayManagerService.Clock getClock() {
            return new com.android.server.display.DisplayManagerService.Clock() { // from class: com.android.server.display.HighBrightnessModeController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayManagerService.Clock
                public final long uptimeMillis() {
                    return android.os.SystemClock.uptimeMillis();
                }
            };
        }

        public void reportHbmStateChange(int i, int i2, int i3) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, i, i2, i3);
        }
    }
}
