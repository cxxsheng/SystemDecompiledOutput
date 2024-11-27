package com.android.server.display;

/* loaded from: classes.dex */
final class DisplayPowerState {
    private java.util.concurrent.Executor mAsyncDestroyExecutor;
    private final com.android.server.display.DisplayBlanker mBlanker;
    private final android.view.Choreographer mChoreographer;
    private java.lang.Runnable mCleanListener;
    private final com.android.server.display.ColorFade mColorFade;
    private boolean mColorFadeDrawPending;

    @com.android.internal.annotations.VisibleForTesting
    final java.lang.Runnable mColorFadeDrawRunnable;
    private float mColorFadeLevel;
    private boolean mColorFadePrepared;
    private boolean mColorFadeReady;
    private final int mDisplayId;
    private final android.os.Handler mHandler;
    private final com.android.server.display.DisplayPowerState.PhotonicModulator mPhotonicModulator;
    private float mScreenBrightness;
    private boolean mScreenReady;
    private int mScreenState;
    private boolean mScreenUpdatePending;
    private final java.lang.Runnable mScreenUpdateRunnable;
    private float mSdrScreenBrightness;
    private volatile boolean mStopped;
    private static final java.lang.String TAG = "DisplayPowerState";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static java.lang.String COUNTER_COLOR_FADE = "ColorFadeLevel";
    public static final android.util.FloatProperty<com.android.server.display.DisplayPowerState> COLOR_FADE_LEVEL = new android.util.FloatProperty<com.android.server.display.DisplayPowerState>("electronBeamLevel") { // from class: com.android.server.display.DisplayPowerState.1
        @Override // android.util.FloatProperty
        public void setValue(com.android.server.display.DisplayPowerState displayPowerState, float f) {
            displayPowerState.setColorFadeLevel(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(com.android.server.display.DisplayPowerState displayPowerState) {
            return java.lang.Float.valueOf(displayPowerState.getColorFadeLevel());
        }
    };
    public static final android.util.FloatProperty<com.android.server.display.DisplayPowerState> SCREEN_BRIGHTNESS_FLOAT = new android.util.FloatProperty<com.android.server.display.DisplayPowerState>("screenBrightnessFloat") { // from class: com.android.server.display.DisplayPowerState.2
        @Override // android.util.FloatProperty
        public void setValue(com.android.server.display.DisplayPowerState displayPowerState, float f) {
            displayPowerState.setScreenBrightness(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(com.android.server.display.DisplayPowerState displayPowerState) {
            return java.lang.Float.valueOf(displayPowerState.getScreenBrightness());
        }
    };
    public static final android.util.FloatProperty<com.android.server.display.DisplayPowerState> SCREEN_SDR_BRIGHTNESS_FLOAT = new android.util.FloatProperty<com.android.server.display.DisplayPowerState>("sdrScreenBrightnessFloat") { // from class: com.android.server.display.DisplayPowerState.3
        @Override // android.util.FloatProperty
        public void setValue(com.android.server.display.DisplayPowerState displayPowerState, float f) {
            displayPowerState.setSdrScreenBrightness(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(com.android.server.display.DisplayPowerState displayPowerState) {
            return java.lang.Float.valueOf(displayPowerState.getSdrScreenBrightness());
        }
    };

    DisplayPowerState(com.android.server.display.DisplayBlanker displayBlanker, com.android.server.display.ColorFade colorFade, int i, int i2) {
        this(displayBlanker, colorFade, i, i2, com.android.internal.os.BackgroundThread.getExecutor());
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayPowerState(com.android.server.display.DisplayBlanker displayBlanker, com.android.server.display.ColorFade colorFade, int i, int i2, java.util.concurrent.Executor executor) {
        float f;
        this.mScreenUpdateRunnable = new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerState.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.DisplayPowerState.this.mScreenUpdatePending = false;
                float f2 = -1.0f;
                float f3 = (com.android.server.display.DisplayPowerState.this.mScreenState == 1 || com.android.server.display.DisplayPowerState.this.mColorFadeLevel <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) ? -1.0f : com.android.server.display.DisplayPowerState.this.mScreenBrightness;
                if (com.android.server.display.DisplayPowerState.this.mScreenState != 1 && com.android.server.display.DisplayPowerState.this.mColorFadeLevel > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    f2 = com.android.server.display.DisplayPowerState.this.mSdrScreenBrightness;
                }
                if (com.android.server.display.DisplayPowerState.this.mPhotonicModulator.setState(com.android.server.display.DisplayPowerState.this.mScreenState, f3, f2)) {
                    if (com.android.server.display.DisplayPowerState.DEBUG) {
                        android.util.Slog.d(com.android.server.display.DisplayPowerState.TAG, "Screen ready");
                    }
                    com.android.server.display.DisplayPowerState.this.mScreenReady = true;
                    com.android.server.display.DisplayPowerState.this.invokeCleanListenerIfNeeded();
                    return;
                }
                if (com.android.server.display.DisplayPowerState.DEBUG) {
                    android.util.Slog.d(com.android.server.display.DisplayPowerState.TAG, "Screen not ready");
                }
            }
        };
        this.mColorFadeDrawRunnable = new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerState.5
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.DisplayPowerState.this.mColorFadeDrawPending = false;
                if (com.android.server.display.DisplayPowerState.this.mColorFadePrepared) {
                    com.android.server.display.DisplayPowerState.this.mColorFade.draw(com.android.server.display.DisplayPowerState.this.mColorFadeLevel);
                    android.os.Trace.traceCounter(131072L, com.android.server.display.DisplayPowerState.COUNTER_COLOR_FADE, java.lang.Math.round(com.android.server.display.DisplayPowerState.this.mColorFadeLevel * 100.0f));
                }
                com.android.server.display.DisplayPowerState.this.mColorFadeReady = true;
                com.android.server.display.DisplayPowerState.this.invokeCleanListenerIfNeeded();
            }
        };
        this.mHandler = new android.os.Handler(true);
        this.mChoreographer = android.view.Choreographer.getInstance();
        this.mBlanker = displayBlanker;
        this.mColorFade = colorFade;
        this.mPhotonicModulator = new com.android.server.display.DisplayPowerState.PhotonicModulator();
        this.mPhotonicModulator.start();
        this.mDisplayId = i;
        this.mAsyncDestroyExecutor = executor;
        this.mScreenState = i2;
        if (i2 != 1) {
            f = 1.0f;
        } else {
            f = -1.0f;
        }
        this.mScreenBrightness = f;
        this.mSdrScreenBrightness = this.mScreenBrightness;
        scheduleScreenUpdate();
        this.mColorFadePrepared = false;
        this.mColorFadeLevel = 1.0f;
        this.mColorFadeReady = true;
    }

    public void setScreenState(int i) {
        if (this.mScreenState != i) {
            if (DEBUG) {
                android.util.Slog.w(TAG, "setScreenState: state=" + android.view.Display.stateToString(i));
            }
            this.mScreenState = i;
            this.mScreenReady = false;
            scheduleScreenUpdate();
        }
    }

    public int getScreenState() {
        return this.mScreenState;
    }

    public void setSdrScreenBrightness(float f) {
        if (this.mSdrScreenBrightness != f) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "setSdrScreenBrightness: brightness=" + f);
            }
            this.mSdrScreenBrightness = f;
            if (this.mScreenState != 1) {
                this.mScreenReady = false;
                scheduleScreenUpdate();
            }
        }
    }

    public float getSdrScreenBrightness() {
        return this.mSdrScreenBrightness;
    }

    public void setScreenBrightness(float f) {
        if (this.mScreenBrightness != f) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "setScreenBrightness: brightness=" + f);
            }
            this.mScreenBrightness = f;
            if (this.mScreenState != 1) {
                this.mScreenReady = false;
                scheduleScreenUpdate();
            }
        }
    }

    public float getScreenBrightness() {
        return this.mScreenBrightness;
    }

    public boolean prepareColorFade(android.content.Context context, int i) {
        if (this.mColorFade == null || !this.mColorFade.prepare(context, i)) {
            this.mColorFadePrepared = false;
            this.mColorFadeReady = true;
            return false;
        }
        this.mColorFadePrepared = true;
        this.mColorFadeReady = false;
        scheduleColorFadeDraw();
        return true;
    }

    public void dismissColorFade() {
        android.os.Trace.traceCounter(131072L, COUNTER_COLOR_FADE, 100);
        if (this.mColorFade != null) {
            this.mColorFade.dismiss();
        }
        this.mColorFadePrepared = false;
        this.mColorFadeReady = true;
    }

    public void dismissColorFadeResources() {
        if (this.mColorFade != null) {
            this.mColorFade.dismissResources();
        }
    }

    public void setColorFadeLevel(float f) {
        if (this.mColorFadeLevel != f) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "setColorFadeLevel: level=" + f);
            }
            this.mColorFadeLevel = f;
            if (this.mScreenState != 1) {
                this.mScreenReady = false;
                scheduleScreenUpdate();
            }
            if (this.mColorFadePrepared) {
                this.mColorFadeReady = false;
                scheduleColorFadeDraw();
            }
        }
    }

    public float getColorFadeLevel() {
        return this.mColorFadeLevel;
    }

    public boolean waitUntilClean(java.lang.Runnable runnable) {
        if (!this.mScreenReady || !this.mColorFadeReady) {
            this.mCleanListener = runnable;
            return false;
        }
        this.mCleanListener = null;
        return true;
    }

    public void stop() {
        this.mStopped = true;
        this.mPhotonicModulator.interrupt();
        this.mColorFadePrepared = false;
        this.mColorFadeReady = true;
        if (this.mColorFade != null) {
            java.util.concurrent.Executor executor = this.mAsyncDestroyExecutor;
            final com.android.server.display.ColorFade colorFade = this.mColorFade;
            java.util.Objects.requireNonNull(colorFade);
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.display.DisplayPowerState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.ColorFade.this.destroy();
                }
            });
        }
        this.mCleanListener = null;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Display Power State:");
        printWriter.println("  mStopped=" + this.mStopped);
        printWriter.println("  mScreenState=" + android.view.Display.stateToString(this.mScreenState));
        printWriter.println("  mScreenBrightness=" + this.mScreenBrightness);
        printWriter.println("  mSdrScreenBrightness=" + this.mSdrScreenBrightness);
        printWriter.println("  mScreenReady=" + this.mScreenReady);
        printWriter.println("  mScreenUpdatePending=" + this.mScreenUpdatePending);
        printWriter.println("  mColorFadePrepared=" + this.mColorFadePrepared);
        printWriter.println("  mColorFadeLevel=" + this.mColorFadeLevel);
        printWriter.println("  mColorFadeReady=" + this.mColorFadeReady);
        printWriter.println("  mColorFadeDrawPending=" + this.mColorFadeDrawPending);
        this.mPhotonicModulator.dump(printWriter);
        if (this.mColorFade != null) {
            this.mColorFade.dump(printWriter);
        }
    }

    void resetScreenState() {
        this.mScreenState = 0;
        this.mScreenReady = false;
    }

    private void scheduleScreenUpdate() {
        if (!this.mScreenUpdatePending) {
            this.mScreenUpdatePending = true;
            postScreenUpdateThreadSafe();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postScreenUpdateThreadSafe() {
        this.mHandler.removeCallbacks(this.mScreenUpdateRunnable);
        this.mHandler.post(this.mScreenUpdateRunnable);
    }

    private void scheduleColorFadeDraw() {
        if (!this.mColorFadeDrawPending) {
            this.mColorFadeDrawPending = true;
            this.mChoreographer.postCallback(3, this.mColorFadeDrawRunnable, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeCleanListenerIfNeeded() {
        java.lang.Runnable runnable = this.mCleanListener;
        if (runnable != null && this.mScreenReady && this.mColorFadeReady) {
            this.mCleanListener = null;
            runnable.run();
        }
    }

    private final class PhotonicModulator extends java.lang.Thread {
        private static final float INITIAL_BACKLIGHT_FLOAT = Float.NaN;
        private static final int INITIAL_SCREEN_STATE = 0;
        private float mActualBacklight;
        private float mActualSdrBacklight;
        private int mActualState;
        private boolean mBacklightChangeInProgress;
        private final java.lang.Object mLock;
        private float mPendingBacklight;
        private float mPendingSdrBacklight;
        private int mPendingState;
        private boolean mStateChangeInProgress;

        public PhotonicModulator() {
            super("PhotonicModulator");
            this.mLock = new java.lang.Object();
            this.mPendingState = 0;
            this.mPendingBacklight = Float.NaN;
            this.mPendingSdrBacklight = Float.NaN;
            this.mActualState = 0;
            this.mActualBacklight = Float.NaN;
            this.mActualSdrBacklight = Float.NaN;
        }

        public boolean setState(int i, float f, float f2) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    boolean z2 = i != this.mPendingState;
                    boolean z3 = (f == this.mPendingBacklight && f2 == this.mPendingSdrBacklight) ? false : true;
                    if (z2 || z3) {
                        if (com.android.server.display.DisplayPowerState.DEBUG) {
                            android.util.Slog.d(com.android.server.display.DisplayPowerState.TAG, "Requesting new screen state: state=" + android.view.Display.stateToString(i) + ", backlight=" + f);
                        }
                        this.mPendingState = i;
                        this.mPendingBacklight = f;
                        this.mPendingSdrBacklight = f2;
                        boolean z4 = this.mStateChangeInProgress || this.mBacklightChangeInProgress;
                        this.mStateChangeInProgress = z2 || this.mStateChangeInProgress;
                        this.mBacklightChangeInProgress = z3 || this.mBacklightChangeInProgress;
                        if (!z4) {
                            this.mLock.notifyAll();
                        }
                    }
                    z = this.mStateChangeInProgress ? false : true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return z;
        }

        public void dump(java.io.PrintWriter printWriter) {
            synchronized (this.mLock) {
                printWriter.println();
                printWriter.println("Photonic Modulator State:");
                printWriter.println("  mPendingState=" + android.view.Display.stateToString(this.mPendingState));
                printWriter.println("  mPendingBacklight=" + this.mPendingBacklight);
                printWriter.println("  mPendingSdrBacklight=" + this.mPendingSdrBacklight);
                printWriter.println("  mActualState=" + android.view.Display.stateToString(this.mActualState));
                printWriter.println("  mActualBacklight=" + this.mActualBacklight);
                printWriter.println("  mActualSdrBacklight=" + this.mActualSdrBacklight);
                printWriter.println("  mStateChangeInProgress=" + this.mStateChangeInProgress);
                printWriter.println("  mBacklightChangeInProgress=" + this.mBacklightChangeInProgress);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                synchronized (this.mLock) {
                    try {
                        int i = this.mPendingState;
                        boolean z = true;
                        boolean z2 = i != this.mActualState;
                        float f = this.mPendingBacklight;
                        float f2 = this.mPendingSdrBacklight;
                        boolean z3 = (f == this.mActualBacklight && f2 == this.mActualSdrBacklight) ? false : true;
                        if (!z2) {
                            com.android.server.display.DisplayPowerState.this.postScreenUpdateThreadSafe();
                            this.mStateChangeInProgress = false;
                        }
                        if (!z3) {
                            this.mBacklightChangeInProgress = false;
                        }
                        boolean z4 = (i == 0 || java.lang.Float.isNaN(f)) ? false : true;
                        if (!z2 && !z3) {
                            z = false;
                        }
                        if (!z4 || !z) {
                            this.mStateChangeInProgress = false;
                            this.mBacklightChangeInProgress = false;
                            try {
                                this.mLock.wait();
                            } catch (java.lang.InterruptedException e) {
                                if (com.android.server.display.DisplayPowerState.this.mStopped) {
                                    return;
                                }
                            }
                        } else {
                            this.mActualState = i;
                            this.mActualBacklight = f;
                            this.mActualSdrBacklight = f2;
                            if (com.android.server.display.DisplayPowerState.DEBUG) {
                                android.util.Slog.d(com.android.server.display.DisplayPowerState.TAG, "Updating screen state: id=" + com.android.server.display.DisplayPowerState.this.mDisplayId + ", state=" + android.view.Display.stateToString(i) + ", backlight=" + f + ", sdrBacklight=" + f2);
                            }
                            com.android.server.display.DisplayPowerState.this.mBlanker.requestDisplayState(com.android.server.display.DisplayPowerState.this.mDisplayId, i, f, f2);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }
}
