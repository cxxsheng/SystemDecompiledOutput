package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public class HdrClamper {
    private float mAmbientLux;
    private final com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener mClamperChangeListener;
    private final java.lang.Runnable mDebouncer;
    private float mDesiredMaxBrightness;
    private float mDesiredTransitionRate;
    private final android.os.Handler mHandler;

    @android.annotation.Nullable
    private com.android.server.display.config.HdrBrightnessData mHdrBrightnessData;
    private final com.android.server.display.brightness.clamper.HdrClamper.HdrLayerInfoListener mHdrListener;
    private boolean mHdrVisible;
    private float mMaxBrightness;

    @android.annotation.Nullable
    private android.os.IBinder mRegisteredDisplayToken;
    private float mTransitionRate;

    @java.lang.FunctionalInterface
    interface HdrListener {
        void onHdrVisible(boolean z);
    }

    public HdrClamper(com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, android.os.Handler handler) {
        this(clamperChangeListener, handler, new com.android.server.display.brightness.clamper.HdrClamper.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    public HdrClamper(com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, android.os.Handler handler, com.android.server.display.brightness.clamper.HdrClamper.Injector injector) {
        this.mHdrBrightnessData = null;
        this.mRegisteredDisplayToken = null;
        this.mAmbientLux = Float.MAX_VALUE;
        this.mHdrVisible = false;
        this.mMaxBrightness = 1.0f;
        this.mDesiredMaxBrightness = 1.0f;
        this.mTransitionRate = -1.0f;
        this.mDesiredTransitionRate = -1.0f;
        this.mClamperChangeListener = clamperChangeListener;
        this.mHandler = handler;
        this.mDebouncer = new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.HdrClamper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.HdrClamper.this.lambda$new$0();
            }
        };
        this.mHdrListener = injector.getHdrListener(new com.android.server.display.brightness.clamper.HdrClamper.HdrListener() { // from class: com.android.server.display.brightness.clamper.HdrClamper$$ExternalSyntheticLambda1
            @Override // com.android.server.display.brightness.clamper.HdrClamper.HdrListener
            public final void onHdrVisible(boolean z) {
                com.android.server.display.brightness.clamper.HdrClamper.this.lambda$new$1(z);
            }
        }, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mTransitionRate = this.mDesiredTransitionRate;
        this.mMaxBrightness = this.mDesiredMaxBrightness;
        this.mClamperChangeListener.onChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(boolean z) {
        this.mHdrVisible = z;
        recalculateBrightnessCap(this.mHdrBrightnessData, this.mAmbientLux, this.mHdrVisible);
    }

    public float getMaxBrightness() {
        return this.mMaxBrightness;
    }

    public float getTransitionRate() {
        return this.mTransitionRate;
    }

    public void onAmbientLuxChange(float f) {
        this.mAmbientLux = f;
        recalculateBrightnessCap(this.mHdrBrightnessData, f, this.mHdrVisible);
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void resetHdrConfig(com.android.server.display.config.HdrBrightnessData hdrBrightnessData, int i, int i2, float f, android.os.IBinder iBinder) {
        this.mHdrBrightnessData = hdrBrightnessData;
        this.mHdrListener.mHdrMinPixels = i * i2 * f;
        if (iBinder != this.mRegisteredDisplayToken) {
            if (this.mRegisteredDisplayToken != null) {
                this.mHdrListener.unregister(this.mRegisteredDisplayToken);
                this.mHdrVisible = false;
                this.mRegisteredDisplayToken = null;
            }
            if (iBinder != null && this.mHdrListener.mHdrMinPixels >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mHdrListener.register(iBinder);
                this.mRegisteredDisplayToken = iBinder;
            }
        }
        recalculateBrightnessCap(hdrBrightnessData, this.mAmbientLux, this.mHdrVisible);
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void stop() {
        if (this.mRegisteredDisplayToken != null) {
            this.mHdrListener.unregister(this.mRegisteredDisplayToken);
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("HdrClamper:");
        printWriter.println("  mMaxBrightness=" + this.mMaxBrightness);
        printWriter.println("  mDesiredMaxBrightness=" + this.mDesiredMaxBrightness);
        printWriter.println("  mTransitionRate=" + this.mTransitionRate);
        printWriter.println("  mDesiredTransitionRate=" + this.mDesiredTransitionRate);
        printWriter.println("  mHdrVisible=" + this.mHdrVisible);
        printWriter.println("  mHdrListener.mHdrMinPixels=" + this.mHdrListener.mHdrMinPixels);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("  mHdrBrightnessData=");
        sb.append(this.mHdrBrightnessData == null ? "null" : this.mHdrBrightnessData.toString());
        printWriter.println(sb.toString());
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("  mHdrListener registered=");
        sb2.append(this.mRegisteredDisplayToken != null);
        printWriter.println(sb2.toString());
        printWriter.println("  mAmbientLux=" + this.mAmbientLux);
    }

    private void reset() {
        if (this.mMaxBrightness == 1.0f && this.mDesiredMaxBrightness == 1.0f && this.mTransitionRate == -1.0f && this.mDesiredTransitionRate == -1.0f) {
            return;
        }
        this.mHandler.removeCallbacks(this.mDebouncer);
        this.mMaxBrightness = 1.0f;
        this.mDesiredMaxBrightness = 1.0f;
        this.mDesiredTransitionRate = -1.0f;
        this.mTransitionRate = 1.0f;
        this.mClamperChangeListener.onChanged();
    }

    private void recalculateBrightnessCap(com.android.server.display.config.HdrBrightnessData hdrBrightnessData, float f, boolean z) {
        long j;
        if (hdrBrightnessData == null || !z) {
            reset();
            return;
        }
        float findBrightnessLimit = findBrightnessLimit(hdrBrightnessData, f);
        if (this.mMaxBrightness == findBrightnessLimit) {
            this.mDesiredMaxBrightness = this.mMaxBrightness;
            this.mDesiredTransitionRate = -1.0f;
            this.mTransitionRate = -1.0f;
            this.mHandler.removeCallbacks(this.mDebouncer);
            return;
        }
        if (this.mDesiredMaxBrightness != findBrightnessLimit) {
            this.mDesiredMaxBrightness = findBrightnessLimit;
            if (this.mDesiredMaxBrightness > this.mMaxBrightness) {
                j = this.mHdrBrightnessData.mBrightnessIncreaseDebounceMillis;
                this.mDesiredTransitionRate = this.mHdrBrightnessData.mScreenBrightnessRampIncrease;
            } else {
                j = this.mHdrBrightnessData.mBrightnessDecreaseDebounceMillis;
                this.mDesiredTransitionRate = this.mHdrBrightnessData.mScreenBrightnessRampDecrease;
            }
            this.mHandler.removeCallbacks(this.mDebouncer);
            this.mHandler.postDelayed(this.mDebouncer, j);
        }
    }

    private float findBrightnessLimit(com.android.server.display.config.HdrBrightnessData hdrBrightnessData, float f) {
        float f2 = Float.MAX_VALUE;
        float f3 = 1.0f;
        for (java.util.Map.Entry<java.lang.Float, java.lang.Float> entry : hdrBrightnessData.mMaxBrightnessLimits.entrySet()) {
            float floatValue = entry.getKey().floatValue();
            if (floatValue > f && floatValue < f2) {
                f3 = entry.getValue().floatValue();
                f2 = floatValue;
            }
        }
        return f3;
    }

    static class HdrLayerInfoListener extends android.view.SurfaceControlHdrLayerInfoListener {
        private final android.os.Handler mHandler;
        private final com.android.server.display.brightness.clamper.HdrClamper.HdrListener mHdrListener;
        private float mHdrMinPixels = Float.MAX_VALUE;

        HdrLayerInfoListener(com.android.server.display.brightness.clamper.HdrClamper.HdrListener hdrListener, android.os.Handler handler) {
            this.mHdrListener = hdrListener;
            this.mHandler = handler;
        }

        public void onHdrInfoChanged(android.os.IBinder iBinder, final int i, final int i2, final int i3, int i4, float f) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.HdrClamper$HdrLayerInfoListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.brightness.clamper.HdrClamper.HdrLayerInfoListener.this.lambda$onHdrInfoChanged$0(i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHdrInfoChanged$0(int i, int i2, int i3) {
            this.mHdrListener.onHdrVisible(i > 0 && ((float) (i2 * i3)) >= this.mHdrMinPixels);
        }
    }

    static class Injector {
        Injector() {
        }

        com.android.server.display.brightness.clamper.HdrClamper.HdrLayerInfoListener getHdrListener(com.android.server.display.brightness.clamper.HdrClamper.HdrListener hdrListener, android.os.Handler handler) {
            return new com.android.server.display.brightness.clamper.HdrClamper.HdrLayerInfoListener(hdrListener, handler);
        }
    }
}
