package com.android.internal.display;

/* loaded from: classes4.dex */
public class BrightnessSynchronizer {
    private static final boolean DEBUG = false;
    public static final float EPSILON = 1.0E-4f;
    private static final int MSG_RUN_UPDATE = 1;
    private static final java.lang.String TAG = "BrightnessSynchronizer";
    private static final long WAIT_FOR_RESPONSE_MILLIS = 200;
    private final com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver mBrightnessSyncObserver;
    private final com.android.internal.display.BrightnessSynchronizer.Clock mClock;
    private final android.content.Context mContext;
    private com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate mCurrentUpdate;
    private android.hardware.display.DisplayManager mDisplayManager;
    private final android.os.Handler mHandler;
    private final boolean mIntRangeUserPerceptionEnabled;
    private float mLatestFloatBrightness;
    private int mLatestIntBrightness;
    private com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate mPendingUpdate;
    private static final android.net.Uri BRIGHTNESS_URI = android.provider.Settings.System.getUriFor(android.provider.Settings.System.SCREEN_BRIGHTNESS);
    private static int sBrightnessUpdateCount = 1;

    public interface Clock {
        long uptimeMillis();
    }

    public BrightnessSynchronizer(android.content.Context context, boolean z) {
        this(context, android.os.Looper.getMainLooper(), new com.android.internal.display.BrightnessSynchronizer.Clock() { // from class: com.android.internal.display.BrightnessSynchronizer$$ExternalSyntheticLambda0
            @Override // com.android.internal.display.BrightnessSynchronizer.Clock
            public final long uptimeMillis() {
                return android.os.SystemClock.uptimeMillis();
            }
        }, z);
    }

    public BrightnessSynchronizer(android.content.Context context, android.os.Looper looper, com.android.internal.display.BrightnessSynchronizer.Clock clock, boolean z) {
        this.mContext = context;
        this.mClock = clock;
        this.mBrightnessSyncObserver = new com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver();
        this.mHandler = new com.android.internal.display.BrightnessSynchronizer.BrightnessSynchronizerHandler(looper);
        this.mIntRangeUserPerceptionEnabled = z;
    }

    public void startSynchronizing() {
        if (this.mDisplayManager == null) {
            this.mDisplayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        }
        if (this.mBrightnessSyncObserver.isObserving()) {
            android.util.Slog.wtf(TAG, "Brightness sync observer requesting synchronization a second time.");
            return;
        }
        this.mLatestFloatBrightness = getScreenBrightnessFloat();
        this.mLatestIntBrightness = getScreenBrightnessInt();
        android.util.Slog.i(TAG, "Initial brightness readings: " + this.mLatestIntBrightness + "(int), " + this.mLatestFloatBrightness + "(float)");
        if (!java.lang.Float.isNaN(this.mLatestFloatBrightness)) {
            this.mPendingUpdate = new com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate(2, this.mLatestFloatBrightness);
        } else if (this.mLatestIntBrightness != -1) {
            this.mPendingUpdate = new com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate(1, this.mLatestIntBrightness);
        } else {
            float f = this.mContext.getResources().getFloat(com.android.internal.R.dimen.config_screenBrightnessSettingDefaultFloat);
            this.mPendingUpdate = new com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate(2, f);
            android.util.Slog.i(TAG, "Setting initial brightness to default value of: " + f);
        }
        this.mBrightnessSyncObserver.startObserving(this.mHandler);
        this.mHandler.sendEmptyMessageAtTime(1, this.mClock.uptimeMillis());
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println(TAG);
        printWriter.println("  mLatestIntBrightness=" + this.mLatestIntBrightness);
        printWriter.println("  mLatestFloatBrightness=" + this.mLatestFloatBrightness);
        printWriter.println("  mCurrentUpdate=" + this.mCurrentUpdate);
        printWriter.println("  mPendingUpdate=" + this.mPendingUpdate);
        printWriter.println("  mIntRangeUserPerceptionEnabled=" + this.mIntRangeUserPerceptionEnabled);
    }

    public static float brightnessIntToFloat(int i) {
        if (i == 0) {
            return -1.0f;
        }
        if (i == -1) {
            return Float.NaN;
        }
        return android.util.MathUtils.constrainedMap(0.0f, 1.0f, 1.0f, 255.0f, i);
    }

    public static int brightnessFloatToInt(float f) {
        return java.lang.Math.round(brightnessFloatToIntRange(f));
    }

    public static float brightnessFloatToIntRange(float f) {
        if (floatEquals(f, -1.0f)) {
            return 0.0f;
        }
        if (java.lang.Float.isNaN(f)) {
            return -1.0f;
        }
        return android.util.MathUtils.constrainedMap(1.0f, 255.0f, 0.0f, 1.0f, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBrightnessChangeFloat(float f) {
        this.mLatestFloatBrightness = f;
        handleBrightnessChange(2, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBrightnessChangeInt(int i) {
        this.mLatestIntBrightness = i;
        handleBrightnessChange(1, i);
    }

    private void handleBrightnessChange(int i, float f) {
        com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate brightnessUpdate;
        boolean z = this.mCurrentUpdate != null && this.mCurrentUpdate.swallowUpdate(i, f);
        if (z) {
            brightnessUpdate = null;
        } else {
            brightnessUpdate = this.mPendingUpdate;
            this.mPendingUpdate = new com.android.internal.display.BrightnessSynchronizer.BrightnessUpdate(i, f);
        }
        runUpdate();
        if (!z && this.mPendingUpdate != null) {
            android.util.Slog.i(TAG, "New PendingUpdate: " + this.mPendingUpdate + ", prev=" + brightnessUpdate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runUpdate() {
        do {
            if (this.mCurrentUpdate != null) {
                this.mCurrentUpdate.update();
                if (!this.mCurrentUpdate.isRunning()) {
                    if (this.mCurrentUpdate.isCompleted()) {
                        if (this.mCurrentUpdate.madeUpdates()) {
                            android.util.Slog.i(TAG, "Completed Update: " + this.mCurrentUpdate);
                        }
                        this.mCurrentUpdate = null;
                    }
                } else {
                    return;
                }
            }
            if (this.mCurrentUpdate == null && this.mPendingUpdate != null) {
                this.mCurrentUpdate = this.mPendingUpdate;
                this.mPendingUpdate = null;
            }
        } while (this.mCurrentUpdate != null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getScreenBrightnessFloat() {
        return this.mDisplayManager.getBrightness(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScreenBrightnessInt() {
        return android.provider.Settings.System.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, -1, -2);
    }

    public static boolean floatEquals(float f, float f2) {
        if (f == f2) {
            return true;
        }
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || java.lang.Math.abs(f - f2) < 1.0E-4f;
    }

    public static float brightnessIntSettingToFloat(android.content.Context context, int i) {
        android.hardware.display.BrightnessInfo brightnessInfo;
        if (i == 0) {
            return -1.0f;
        }
        if (i == -1) {
            return Float.NaN;
        }
        float convertGammaToLinear = com.android.internal.display.BrightnessUtils.convertGammaToLinear(android.util.MathUtils.norm(1.0f, 255.0f, i));
        android.view.Display display = context.getDisplay();
        if (display == null || (brightnessInfo = display.getBrightnessInfo()) == null) {
            return Float.NaN;
        }
        return android.util.MathUtils.lerp(brightnessInfo.brightnessMinimum, brightnessInfo.brightnessMaximum, convertGammaToLinear);
    }

    public static int brightnessFloatToIntSetting(android.content.Context context, float f) {
        android.view.Display display;
        android.hardware.display.BrightnessInfo brightnessInfo;
        if (floatEquals(f, -1.0f)) {
            return 0;
        }
        if (java.lang.Float.isNaN(f) || (display = context.getDisplay()) == null || (brightnessInfo = display.getBrightnessInfo()) == null) {
            return -1;
        }
        return java.lang.Math.round(android.util.MathUtils.lerp(1.0f, 255.0f, com.android.internal.display.BrightnessUtils.convertLinearToGamma(android.util.MathUtils.norm(brightnessInfo.brightnessMinimum, brightnessInfo.brightnessMaximum, f))));
    }

    public class BrightnessUpdate {
        private static final int STATE_COMPLETED = 3;
        private static final int STATE_NOT_STARTED = 1;
        private static final int STATE_RUNNING = 2;
        static final int TYPE_FLOAT = 2;
        static final int TYPE_INT = 1;
        private final float mBrightness;
        private int mConfirmedTypes;
        private int mId;
        private final int mSourceType;
        private int mState;
        private long mTimeUpdated;
        private int mUpdatedTypes;

        BrightnessUpdate(int i, float f) {
            int i2 = com.android.internal.display.BrightnessSynchronizer.sBrightnessUpdateCount;
            com.android.internal.display.BrightnessSynchronizer.sBrightnessUpdateCount = i2 + 1;
            this.mId = i2;
            this.mSourceType = i;
            this.mBrightness = f;
            this.mTimeUpdated = 0L;
            this.mUpdatedTypes = 0;
            this.mConfirmedTypes = 0;
            this.mState = 1;
        }

        public java.lang.String toString() {
            return "{[" + this.mId + "] " + toStringLabel(this.mSourceType, this.mBrightness) + ", mUpdatedTypes=" + this.mUpdatedTypes + ", mConfirmedTypes=" + this.mConfirmedTypes + ", mTimeUpdated=" + this.mTimeUpdated + "}";
        }

        void update() {
            if (this.mState == 1) {
                this.mState = 2;
                int brightnessAsInt = getBrightnessAsInt();
                if (com.android.internal.display.BrightnessSynchronizer.this.mLatestIntBrightness != brightnessAsInt) {
                    android.provider.Settings.System.putIntForUser(com.android.internal.display.BrightnessSynchronizer.this.mContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, brightnessAsInt, -2);
                    com.android.internal.display.BrightnessSynchronizer.this.mLatestIntBrightness = brightnessAsInt;
                    this.mUpdatedTypes |= 1;
                }
                float brightnessAsFloat = getBrightnessAsFloat();
                if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(com.android.internal.display.BrightnessSynchronizer.this.mLatestFloatBrightness, brightnessAsFloat)) {
                    com.android.internal.display.BrightnessSynchronizer.this.mDisplayManager.setBrightness(0, brightnessAsFloat);
                    com.android.internal.display.BrightnessSynchronizer.this.mLatestFloatBrightness = brightnessAsFloat;
                    this.mUpdatedTypes |= 2;
                }
                if (this.mUpdatedTypes != 0) {
                    android.util.Slog.i(com.android.internal.display.BrightnessSynchronizer.TAG, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mId + "] New Update " + toStringLabel(this.mSourceType, this.mBrightness) + " set brightness values: " + toStringLabel(this.mUpdatedTypes & 2, brightnessAsFloat) + " " + toStringLabel(this.mUpdatedTypes & 1, brightnessAsInt));
                    com.android.internal.display.BrightnessSynchronizer.this.mHandler.sendEmptyMessageAtTime(1, com.android.internal.display.BrightnessSynchronizer.this.mClock.uptimeMillis() + com.android.internal.display.BrightnessSynchronizer.WAIT_FOR_RESPONSE_MILLIS);
                }
                this.mTimeUpdated = com.android.internal.display.BrightnessSynchronizer.this.mClock.uptimeMillis();
            }
            if (this.mState == 2) {
                if (this.mConfirmedTypes == this.mUpdatedTypes || this.mTimeUpdated + com.android.internal.display.BrightnessSynchronizer.WAIT_FOR_RESPONSE_MILLIS < com.android.internal.display.BrightnessSynchronizer.this.mClock.uptimeMillis()) {
                    this.mState = 3;
                }
            }
        }

        boolean swallowUpdate(int i, float f) {
            if ((this.mUpdatedTypes & i) != i || (this.mConfirmedTypes & i) != 0) {
                return false;
            }
            boolean z = i == 2 && com.android.internal.display.BrightnessSynchronizer.floatEquals(getBrightnessAsFloat(), f);
            boolean z2 = i == 1 && getBrightnessAsInt() == ((int) f);
            if (!z && !z2) {
                return false;
            }
            this.mConfirmedTypes |= i;
            android.util.Slog.i(com.android.internal.display.BrightnessSynchronizer.TAG, "Swallowing update of " + toStringLabel(i, f) + " by update: " + this);
            return true;
        }

        boolean isRunning() {
            return this.mState == 2;
        }

        boolean isCompleted() {
            return this.mState == 3;
        }

        boolean madeUpdates() {
            return this.mUpdatedTypes != 0;
        }

        private int getBrightnessAsInt() {
            if (this.mSourceType == 1) {
                return (int) this.mBrightness;
            }
            if (com.android.internal.display.BrightnessSynchronizer.this.mIntRangeUserPerceptionEnabled) {
                return com.android.internal.display.BrightnessSynchronizer.brightnessFloatToIntSetting(com.android.internal.display.BrightnessSynchronizer.this.mContext, this.mBrightness);
            }
            return com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(this.mBrightness);
        }

        private float getBrightnessAsFloat() {
            if (this.mSourceType == 2) {
                return this.mBrightness;
            }
            if (com.android.internal.display.BrightnessSynchronizer.this.mIntRangeUserPerceptionEnabled) {
                return com.android.internal.display.BrightnessSynchronizer.brightnessIntSettingToFloat(com.android.internal.display.BrightnessSynchronizer.this.mContext, (int) this.mBrightness);
            }
            return com.android.internal.display.BrightnessSynchronizer.brightnessIntToFloat((int) this.mBrightness);
        }

        private java.lang.String toStringLabel(int i, float f) {
            return i == 1 ? ((int) f) + "(i)" : i == 2 ? f + "(f)" : "";
        }
    }

    class BrightnessSynchronizerHandler extends android.os.Handler {
        BrightnessSynchronizerHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.display.BrightnessSynchronizer.this.runUpdate();
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    private class BrightnessSyncObserver {
        private boolean mIsObserving;
        private final android.hardware.display.DisplayManager.DisplayListener mListener;

        private BrightnessSyncObserver() {
            this.mListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) {
                    com.android.internal.display.BrightnessSynchronizer.this.handleBrightnessChangeFloat(com.android.internal.display.BrightnessSynchronizer.this.getScreenBrightnessFloat());
                }
            };
        }

        private android.database.ContentObserver createBrightnessContentObserver(android.os.Handler handler) {
            return new android.database.ContentObserver(handler) { // from class: com.android.internal.display.BrightnessSynchronizer.BrightnessSyncObserver.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z, android.net.Uri uri) {
                    if (!z && com.android.internal.display.BrightnessSynchronizer.BRIGHTNESS_URI.equals(uri)) {
                        com.android.internal.display.BrightnessSynchronizer.this.handleBrightnessChangeInt(com.android.internal.display.BrightnessSynchronizer.this.getScreenBrightnessInt());
                    }
                }
            };
        }

        boolean isObserving() {
            return this.mIsObserving;
        }

        void startObserving(android.os.Handler handler) {
            com.android.internal.display.BrightnessSynchronizer.this.mContext.getContentResolver().registerContentObserver(com.android.internal.display.BrightnessSynchronizer.BRIGHTNESS_URI, false, createBrightnessContentObserver(handler), -1);
            com.android.internal.display.BrightnessSynchronizer.this.mDisplayManager.registerDisplayListener(this.mListener, handler, 8L);
            this.mIsObserving = true;
        }
    }
}
