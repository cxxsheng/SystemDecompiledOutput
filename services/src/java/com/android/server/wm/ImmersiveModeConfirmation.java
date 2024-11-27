package com.android.server.wm;

/* loaded from: classes3.dex */
public class ImmersiveModeConfirmation {
    private static final java.lang.String CONFIRMED = "confirmed";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_SHOW_EVERY_TIME = false;
    private static final int IMMERSIVE_MODE_CONFIRMATION_WINDOW_TYPE = 2017;
    private static final java.lang.String TAG = "ImmersiveModeConfirmation";
    private static boolean sConfirmed;
    private boolean mCanSystemBarsBeShownByUser;
    private com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView mClingWindow;
    private final android.content.Context mContext;
    private final com.android.server.wm.ImmersiveModeConfirmation.H mHandler;
    private final long mPanicThresholdMs;
    private long mPanicTime;
    private final long mShowDelayMs;
    private boolean mVrModeEnabled;

    @android.annotation.Nullable
    private android.content.Context mWindowContext;

    @android.annotation.Nullable
    private android.view.WindowManager mWindowManager;
    private final android.os.IBinder mWindowToken = new android.os.Binder();
    private int mWindowContextRootDisplayAreaId = -1;
    private int mLockTaskState = 0;
    private final java.lang.Runnable mConfirm = new java.lang.Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.1
        @Override // java.lang.Runnable
        public void run() {
            if (!com.android.server.wm.ImmersiveModeConfirmation.sConfirmed) {
                com.android.server.wm.ImmersiveModeConfirmation.sConfirmed = true;
                com.android.server.wm.ImmersiveModeConfirmation.saveSetting(com.android.server.wm.ImmersiveModeConfirmation.this.mContext);
            }
            com.android.server.wm.ImmersiveModeConfirmation.this.handleHide();
        }
    };

    ImmersiveModeConfirmation(android.content.Context context, android.os.Looper looper, boolean z, boolean z2) {
        android.view.Display display = context.getDisplay();
        android.content.Context systemUiContext = android.app.ActivityThread.currentActivityThread().getSystemUiContext();
        this.mContext = display.getDisplayId() != 0 ? systemUiContext.createDisplayContext(display) : systemUiContext;
        this.mHandler = new com.android.server.wm.ImmersiveModeConfirmation.H(looper);
        this.mShowDelayMs = context.getResources().getInteger(android.R.integer.device_idle_min_time_to_alarm_ms) * 3;
        this.mPanicThresholdMs = context.getResources().getInteger(android.R.integer.config_fixedRefreshRateInHighZone);
        this.mVrModeEnabled = z;
        this.mCanSystemBarsBeShownByUser = z2;
    }

    static boolean loadSetting(int i, android.content.Context context) {
        boolean z = sConfirmed;
        sConfirmed = false;
        java.lang.String str = null;
        try {
            str = android.provider.Settings.Secure.getStringForUser(context.getContentResolver(), "immersive_mode_confirmations", -2);
            sConfirmed = CONFIRMED.equals(str);
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Error loading confirmations, value=" + str, th);
        }
        return sConfirmed != z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveSetting(android.content.Context context) {
        try {
            android.provider.Settings.Secure.putStringForUser(context.getContentResolver(), "immersive_mode_confirmations", sConfirmed ? CONFIRMED : null, -2);
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Error saving confirmations, sConfirmed=" + sConfirmed, th);
        }
    }

    void release() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
    }

    boolean onSettingChanged(int i) {
        boolean loadSetting = loadSetting(i, this.mContext);
        if (loadSetting && sConfirmed) {
            this.mHandler.sendEmptyMessage(2);
        }
        return loadSetting;
    }

    void immersiveModeChangedLw(int i, boolean z, boolean z2, boolean z3) {
        this.mHandler.removeMessages(1);
        if (z) {
            if (!sConfirmed && z2 && !this.mVrModeEnabled && this.mCanSystemBarsBeShownByUser && !z3 && !android.os.UserManager.isDeviceInDemoMode(this.mContext) && this.mLockTaskState != 1) {
                android.os.Message obtainMessage = this.mHandler.obtainMessage(1);
                obtainMessage.arg1 = i;
                this.mHandler.sendMessageDelayed(obtainMessage, this.mShowDelayMs);
                return;
            }
            return;
        }
        this.mHandler.sendEmptyMessage(2);
    }

    boolean onPowerKeyDown(boolean z, long j, boolean z2, boolean z3) {
        if (!z && j - this.mPanicTime < this.mPanicThresholdMs) {
            return this.mClingWindow == null;
        }
        if (z && z2 && !z3) {
            this.mPanicTime = j;
        } else {
            this.mPanicTime = 0L;
        }
        return false;
    }

    void confirmCurrentPrompt() {
        if (this.mClingWindow != null) {
            this.mHandler.post(this.mConfirm);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHide() {
        if (this.mClingWindow != null) {
            if (this.mWindowManager != null) {
                try {
                    this.mWindowManager.removeView(this.mClingWindow);
                } catch (android.view.WindowManager.InvalidDisplayException e) {
                    android.util.Slog.w(TAG, "Fail to hide the immersive confirmation window because of " + e);
                }
                this.mWindowManager = null;
                this.mWindowContext = null;
            }
            this.mClingWindow = null;
        }
    }

    private android.view.WindowManager.LayoutParams getClingWindowLayoutParams() {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams(-1, -1, IMMERSIVE_MODE_CONFIRMATION_WINDOW_TYPE, 16777504, -3);
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~android.view.WindowInsets.Type.statusBars()));
        layoutParams.privateFlags |= 537002000;
        layoutParams.setTitle(TAG);
        layoutParams.windowAnimations = android.R.style.Animation.Holo.Dialog;
        layoutParams.token = getWindowToken();
        return layoutParams;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.widget.FrameLayout.LayoutParams getBubbleLayoutParams() {
        return new android.widget.FrameLayout.LayoutParams(this.mContext.getResources().getDimensionPixelSize(android.R.dimen.harmful_app_message_padding_right), -2, 49);
    }

    android.os.IBinder getWindowToken() {
        return this.mWindowToken;
    }

    private class ClingWindowView extends android.widget.FrameLayout {
        private static final int ANIMATION_DURATION = 250;
        private static final int BGCOLOR = Integer.MIN_VALUE;
        private static final int OFFSET_DP = 96;
        private android.view.ViewGroup mClingLayout;
        private final android.graphics.drawable.ColorDrawable mColor;
        private android.animation.ValueAnimator mColorAnim;
        private final java.lang.Runnable mConfirm;
        private android.view.ViewTreeObserver.OnComputeInternalInsetsListener mInsetsListener;
        private final android.view.animation.Interpolator mInterpolator;
        private android.content.BroadcastReceiver mReceiver;
        private java.lang.Runnable mUpdateLayoutRunnable;

        ClingWindowView(android.content.Context context, java.lang.Runnable runnable) {
            super(context);
            this.mColor = new android.graphics.drawable.ColorDrawable(0);
            this.mUpdateLayoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout != null && com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout.getParent() != null) {
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout.setLayoutParams(com.android.server.wm.ImmersiveModeConfirmation.this.getBubbleLayoutParams());
                    }
                }
            };
            this.mInsetsListener = new android.view.ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.2
                private final int[] mTmpInt2 = new int[2];

                public void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                    com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout.getLocationInWindow(this.mTmpInt2);
                    internalInsetsInfo.setTouchableInsets(3);
                    internalInsetsInfo.touchableRegion.set(this.mTmpInt2[0], this.mTmpInt2[1], this.mTmpInt2[0] + com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout.getWidth(), this.mTmpInt2[1] + com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mClingLayout.getHeight());
                }
            };
            this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context2, android.content.Intent intent) {
                    if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.post(com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mUpdateLayoutRunnable);
                    }
                }
            };
            this.mConfirm = runnable;
            setBackground(this.mColor);
            setImportantForAccessibility(2);
            this.mInterpolator = android.view.animation.AnimationUtils.loadInterpolator(((android.widget.FrameLayout) this).mContext, android.R.interpolator.linear_out_slow_in);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
            ((android.widget.FrameLayout) this).mContext.getDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsListener);
            this.mClingLayout = (android.view.ViewGroup) android.view.View.inflate(getContext(), android.R.layout.immersive_mode_cling, null);
            ((android.widget.Button) this.mClingLayout.findViewById(android.R.id.oem)).setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.4
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mConfirm.run();
                }
            });
            addView(this.mClingLayout, com.android.server.wm.ImmersiveModeConfirmation.this.getBubbleLayoutParams());
            if (android.app.ActivityManager.isHighEndGfx()) {
                final android.view.ViewGroup viewGroup = this.mClingLayout;
                viewGroup.setAlpha(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                viewGroup.setTranslationY(f * (-96.0f));
                postOnAnimation(new java.lang.Runnable() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5
                    @Override // java.lang.Runnable
                    public void run() {
                        viewGroup.animate().alpha(1.0f).translationY(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).setDuration(250L).setInterpolator(com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mInterpolator).withLayer().start();
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColorAnim = android.animation.ValueAnimator.ofObject(new android.animation.ArgbEvaluator(), 0, Integer.MIN_VALUE);
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColorAnim.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.5.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                                com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColor.setColor(((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColorAnim.setDuration(250L);
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColorAnim.setInterpolator(com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mInterpolator);
                        com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView.this.mColorAnim.start();
                    }
                });
            } else {
                this.mColor.setColor(Integer.MIN_VALUE);
            }
            ((android.widget.FrameLayout) this).mContext.registerReceiver(this.mReceiver, new android.content.IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            ((android.widget.FrameLayout) this).mContext.unregisterReceiver(this.mReceiver);
        }

        @Override // android.view.View
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.View
        public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
            return new android.view.WindowInsets.Builder(windowInsets).setInsets(android.view.WindowInsets.Type.systemBars(), android.graphics.Insets.NONE).build();
        }
    }

    @android.annotation.NonNull
    private android.view.WindowManager createWindowManager(int i) {
        if (this.mWindowManager != null) {
            throw new java.lang.IllegalStateException("Must not create a new WindowManager while there is an existing one");
        }
        android.os.Bundle optionsForWindowContext = getOptionsForWindowContext(i);
        this.mWindowContextRootDisplayAreaId = i;
        this.mWindowContext = this.mContext.createWindowContext(IMMERSIVE_MODE_CONFIRMATION_WINDOW_TYPE, optionsForWindowContext);
        this.mWindowManager = (android.view.WindowManager) this.mWindowContext.getSystemService(android.view.WindowManager.class);
        return this.mWindowManager;
    }

    @android.annotation.Nullable
    private android.os.Bundle getOptionsForWindowContext(int i) {
        if (i == -1) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("root_display_area_id", i);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShow(int i) {
        if (this.mClingWindow != null) {
            if (i == this.mWindowContextRootDisplayAreaId) {
                return;
            } else {
                handleHide();
            }
        }
        this.mClingWindow = new com.android.server.wm.ImmersiveModeConfirmation.ClingWindowView(this.mContext, this.mConfirm);
        try {
            createWindowManager(i).addView(this.mClingWindow, getClingWindowLayoutParams());
        } catch (android.view.WindowManager.InvalidDisplayException e) {
            android.util.Slog.w(TAG, "Fail to show the immersive confirmation window because of " + e);
        }
    }

    private final class H extends android.os.Handler {
        private static final int HIDE = 2;
        private static final int SHOW = 1;

        H(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            }
            switch (message.what) {
                case 1:
                    com.android.server.wm.ImmersiveModeConfirmation.this.handleShow(message.arg1);
                    break;
                case 2:
                    com.android.server.wm.ImmersiveModeConfirmation.this.handleHide();
                    break;
            }
        }
    }

    void onVrStateChangedLw(boolean z) {
        this.mVrModeEnabled = z;
        if (this.mVrModeEnabled) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessage(2);
        }
    }

    void onLockTaskModeChangedLw(int i) {
        this.mLockTaskState = i;
    }
}
