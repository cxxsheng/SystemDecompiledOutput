package com.android.server.am;

/* loaded from: classes.dex */
class UserSwitchingDialog extends android.app.Dialog {
    private static final int ANIMATION_TIMEOUT_MS = 1000;
    protected static final boolean DEBUG = true;
    private static final long DIALOG_SHOW_HIDE_ANIMATION_DURATION_MS = 300;
    private static final java.lang.String TAG = "UserSwitchingDialog";
    private static final long TRACE_TAG = 64;
    protected final android.content.Context mContext;
    private final boolean mDisableAnimations;
    private final android.os.Handler mHandler;
    private final boolean mNeedToFreezeScreen;
    protected final android.content.pm.UserInfo mNewUser;
    protected final android.content.pm.UserInfo mOldUser;
    private final java.lang.String mSwitchingFromSystemUserMessage;
    private final java.lang.String mSwitchingToSystemUserMessage;
    private final int mTraceCookie;
    private final com.android.server.wm.WindowManagerService mWindowManager;

    UserSwitchingDialog(android.content.Context context, android.content.pm.UserInfo userInfo, android.content.pm.UserInfo userInfo2, java.lang.String str, java.lang.String str2, com.android.server.wm.WindowManagerService windowManagerService) {
        super(context, android.R.style.Theme.Material.NoActionBar.Fullscreen);
        this.mHandler = new android.os.Handler(android.os.Looper.myLooper());
        this.mContext = context;
        this.mOldUser = userInfo;
        this.mNewUser = userInfo2;
        this.mSwitchingFromSystemUserMessage = str;
        this.mSwitchingToSystemUserMessage = str2;
        boolean z = false;
        this.mDisableAnimations = android.os.SystemProperties.getBoolean("debug.usercontroller.disable_user_switching_dialog_animations", false);
        this.mWindowManager = windowManagerService;
        if (!this.mDisableAnimations && !isUserSetupComplete(userInfo2)) {
            z = true;
        }
        this.mNeedToFreezeScreen = z;
        this.mTraceCookie = (userInfo.id * 21473) + userInfo2.id;
        inflateContent();
        configureWindow();
    }

    private void configureWindow() {
        android.view.Window window = getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags = 272;
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setType(2010);
        window.setDecorFitsSystemWindows(false);
    }

    void inflateContent() {
        setCancelable(false);
        setContentView(android.R.layout.typing_filter);
        android.widget.TextView textView = (android.widget.TextView) findViewById(android.R.id.message);
        if (textView != null) {
            java.lang.String textMessage = getTextMessage();
            textView.setAccessibilityPaneTitle(textMessage);
            textView.setText(textMessage);
        }
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(android.R.id.icon);
        if (imageView != null) {
            imageView.setImageBitmap(getUserIconRounded());
        }
        android.widget.ImageView imageView2 = (android.widget.ImageView) findViewById(android.R.id.profile_tabhost);
        if (imageView2 != null) {
            if (this.mDisableAnimations) {
                imageView2.setVisibility(8);
                return;
            }
            android.util.TypedValue typedValue = new android.util.TypedValue();
            getContext().getTheme().resolveAttribute(android.R.^attr-private.colorAccentPrimary, typedValue, true);
            imageView2.setColorFilter(typedValue.data);
        }
    }

    private android.graphics.Bitmap getUserIconRounded() {
        android.graphics.Bitmap bitmap = (android.graphics.Bitmap) com.android.internal.util.ObjectUtils.getOrElse(android.graphics.BitmapFactory.decodeFile(this.mNewUser.iconPath), defaultUserIcon(this.mNewUser.id));
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(width, height, bitmap.getConfig());
        android.graphics.Paint paint = new android.graphics.Paint(1);
        paint.setShader(new android.graphics.BitmapShader(bitmap, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP));
        float f = width;
        float f2 = height;
        new android.graphics.Canvas(createBitmap).drawRoundRect(new android.graphics.RectF(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f, f2), f / 2.0f, f2 / 2.0f, paint);
        return createBitmap;
    }

    private android.graphics.Bitmap defaultUserIcon(int i) {
        android.content.res.Resources resources = getContext().getResources();
        return com.android.internal.util.UserIcons.convertToBitmapAtUserIconSize(resources, com.android.internal.util.UserIcons.getDefaultUserIcon(resources, i, false));
    }

    private java.lang.String getTextMessage() {
        java.lang.String str;
        int i;
        android.content.res.Resources resources = getContext().getResources();
        if (android.os.UserManager.isDeviceInDemoMode(this.mContext)) {
            if (this.mOldUser.isDemo()) {
                i = android.R.string.default_audio_route_name;
            } else {
                i = android.R.string.default_audio_route_name_dock_speakers;
            }
            return resources.getString(i);
        }
        if (this.mOldUser.id == 0) {
            str = this.mSwitchingFromSystemUserMessage;
        } else {
            str = this.mNewUser.id == 0 ? this.mSwitchingToSystemUserMessage : null;
        }
        if (str != null) {
            return str;
        }
        return resources.getString(android.R.string.unsupported_compile_sdk_message, this.mNewUser.name);
    }

    private boolean isUserSetupComplete(android.content.pm.UserInfo userInfo) {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, userInfo.id) == 1;
    }

    @Override // android.app.Dialog
    public void show() {
        asyncTraceBegin("dialog", 0);
        super.show();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        stopFreezingScreen();
        asyncTraceEnd("dialog", 0);
    }

    public void show(@android.annotation.NonNull final java.lang.Runnable runnable) {
        android.util.Slog.d(TAG, "show called");
        show();
        startShowAnimation(new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserSwitchingDialog.this.lambda$show$0(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(java.lang.Runnable runnable) {
        startFreezingScreen();
        runnable.run();
    }

    public void dismiss(@android.annotation.Nullable final java.lang.Runnable runnable) {
        android.util.Slog.d(TAG, "dismiss called");
        if (runnable == null) {
            dismiss();
        } else {
            startDismissAnimation(new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserSwitchingDialog.this.lambda$dismiss$1(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismiss$1(java.lang.Runnable runnable) {
        dismiss();
        runnable.run();
    }

    private void startFreezingScreen() {
        if (!this.mNeedToFreezeScreen) {
            return;
        }
        traceBegin("startFreezingScreen");
        this.mWindowManager.startFreezingScreen(0, 0);
        traceEnd("startFreezingScreen");
    }

    private void stopFreezingScreen() {
        if (!this.mNeedToFreezeScreen) {
            return;
        }
        traceBegin("stopFreezingScreen");
        this.mWindowManager.stopFreezingScreen();
        traceEnd("stopFreezingScreen");
    }

    private void startShowAnimation(final java.lang.Runnable runnable) {
        if (this.mDisableAnimations) {
            runnable.run();
        } else {
            asyncTraceBegin("showAnimation", 1);
            startDialogAnimation("show", new android.view.animation.AlphaAnimation(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f), new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserSwitchingDialog.this.lambda$startShowAnimation$3(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowAnimation$3(final java.lang.Runnable runnable) {
        asyncTraceEnd("showAnimation", 1);
        asyncTraceBegin("spinnerAnimation", 2);
        startProgressAnimation(new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserSwitchingDialog.this.lambda$startShowAnimation$2(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowAnimation$2(java.lang.Runnable runnable) {
        asyncTraceEnd("spinnerAnimation", 2);
        runnable.run();
    }

    private void startDismissAnimation(final java.lang.Runnable runnable) {
        if (this.mDisableAnimations || this.mNeedToFreezeScreen) {
            runnable.run();
        } else {
            asyncTraceBegin("dismissAnimation", 3);
            startDialogAnimation("dismiss", new android.view.animation.AlphaAnimation(1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE), new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserSwitchingDialog.this.lambda$startDismissAnimation$4(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDismissAnimation$4(java.lang.Runnable runnable) {
        asyncTraceEnd("dismissAnimation", 3);
        runnable.run();
    }

    private void startProgressAnimation(java.lang.Runnable runnable) {
        android.graphics.drawable.AnimatedVectorDrawable spinnerAVD = getSpinnerAVD();
        if (this.mDisableAnimations || spinnerAVD == null) {
            runnable.run();
            return;
        }
        final java.lang.Runnable animationWithTimeout = animationWithTimeout("spinner", runnable);
        spinnerAVD.registerAnimationCallback(new android.graphics.drawable.Animatable2.AnimationCallback() { // from class: com.android.server.am.UserSwitchingDialog.1
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public void onAnimationEnd(android.graphics.drawable.Drawable drawable) {
                animationWithTimeout.run();
            }
        });
        spinnerAVD.start();
    }

    private android.graphics.drawable.AnimatedVectorDrawable getSpinnerAVD() {
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(android.R.id.profile_tabhost);
        if (imageView != null) {
            android.graphics.drawable.Drawable drawable = imageView.getDrawable();
            if (drawable instanceof android.graphics.drawable.AnimatedVectorDrawable) {
                return (android.graphics.drawable.AnimatedVectorDrawable) drawable;
            }
            return null;
        }
        return null;
    }

    private void startDialogAnimation(java.lang.String str, android.view.animation.Animation animation, java.lang.Runnable runnable) {
        android.view.View findViewById = findViewById(android.R.id.content);
        if (this.mDisableAnimations || findViewById == null) {
            runnable.run();
            return;
        }
        final java.lang.Runnable animationWithTimeout = animationWithTimeout(str, runnable);
        animation.setDuration(DIALOG_SHOW_HIDE_ANIMATION_DURATION_MS);
        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() { // from class: com.android.server.am.UserSwitchingDialog.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(android.view.animation.Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(android.view.animation.Animation animation2) {
                animationWithTimeout.run();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(android.view.animation.Animation animation2) {
            }
        });
        findViewById.startAnimation(animation);
    }

    private java.lang.Runnable animationWithTimeout(final java.lang.String str, final java.lang.Runnable runnable) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(true);
        final java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserSwitchingDialog.this.lambda$animationWithTimeout$5(atomicBoolean, runnable);
            }
        };
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserSwitchingDialog.lambda$animationWithTimeout$6(str, runnable2);
            }
        }, 1000L);
        return runnable2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animationWithTimeout$5(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, java.lang.Runnable runnable) {
        if (atomicBoolean.getAndSet(false)) {
            this.mHandler.removeCallbacksAndMessages(null);
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$animationWithTimeout$6(java.lang.String str, java.lang.Runnable runnable) {
        android.util.Slog.w(TAG, str + " animation not completed in 1000 ms");
        runnable.run();
    }

    private void asyncTraceBegin(java.lang.String str, int i) {
        android.util.Slog.d(TAG, "asyncTraceBegin-" + str);
        android.os.Trace.asyncTraceBegin(TRACE_TAG, TAG + str, this.mTraceCookie + i);
    }

    private void asyncTraceEnd(java.lang.String str, int i) {
        android.os.Trace.asyncTraceEnd(TRACE_TAG, TAG + str, this.mTraceCookie + i);
        android.util.Slog.d(TAG, "asyncTraceEnd-" + str);
    }

    private void traceBegin(java.lang.String str) {
        android.util.Slog.d(TAG, "traceBegin-" + str);
        android.os.Trace.traceBegin(TRACE_TAG, str);
    }

    private void traceEnd(java.lang.String str) {
        android.os.Trace.traceEnd(TRACE_TAG);
        android.util.Slog.d(TAG, "traceEnd-" + str);
    }
}
