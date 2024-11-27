package com.android.server.notification;

/* loaded from: classes2.dex */
class DefaultDeviceEffectsApplier implements android.service.notification.DeviceEffectsApplier {
    private static final int SATURATION_LEVEL_FULL_COLOR = 100;
    private static final int SATURATION_LEVEL_GRAYSCALE = 0;
    private static final android.content.IntentFilter SCREEN_OFF_INTENT_FILTER = new android.content.IntentFilter("android.intent.action.SCREEN_OFF");
    private static final java.lang.String SUPPRESS_AMBIENT_DISPLAY_TOKEN = "DefaultDeviceEffectsApplier:SuppressAmbientDisplay";
    private static final float WALLPAPER_DIM_AMOUNT_DIMMED = 0.6f;
    private static final float WALLPAPER_DIM_AMOUNT_NORMAL = 0.0f;
    private final android.hardware.display.ColorDisplayManager mColorDisplayManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mRegisterReceiverLock"})
    private boolean mIsScreenOffReceiverRegistered;
    private boolean mPendingNightMode;
    private final android.os.PowerManager mPowerManager;
    private final android.app.UiModeManager mUiModeManager;
    private final android.app.WallpaperManager mWallpaperManager;
    private final java.lang.Object mRegisterReceiverLock = new java.lang.Object();
    private android.service.notification.ZenDeviceEffects mLastAppliedEffects = new android.service.notification.ZenDeviceEffects.Builder().build();

    @com.android.internal.annotations.GuardedBy({"mRegisterReceiverLock"})
    private final android.content.BroadcastReceiver mNightModeWhenScreenOff = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.DefaultDeviceEffectsApplier.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.notification.DefaultDeviceEffectsApplier.this.unregisterScreenOffReceiver();
            com.android.server.notification.DefaultDeviceEffectsApplier.this.updateNightModeImmediately(com.android.server.notification.DefaultDeviceEffectsApplier.this.mPendingNightMode);
        }
    };

    DefaultDeviceEffectsApplier(android.content.Context context) {
        this.mContext = context;
        this.mColorDisplayManager = (android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class);
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        this.mUiModeManager = (android.app.UiModeManager) context.getSystemService(android.app.UiModeManager.class);
        android.app.WallpaperManager wallpaperManager = (android.app.WallpaperManager) context.getSystemService(android.app.WallpaperManager.class);
        this.mWallpaperManager = (wallpaperManager == null || !wallpaperManager.isWallpaperSupported()) ? null : wallpaperManager;
    }

    public void apply(final android.service.notification.ZenDeviceEffects zenDeviceEffects, final int i) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.DefaultDeviceEffectsApplier$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.notification.DefaultDeviceEffectsApplier.this.lambda$apply$0(zenDeviceEffects, i);
            }
        });
        this.mLastAppliedEffects = zenDeviceEffects;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$apply$0(android.service.notification.ZenDeviceEffects zenDeviceEffects, int i) throws java.lang.Exception {
        if (this.mLastAppliedEffects.shouldSuppressAmbientDisplay() != zenDeviceEffects.shouldSuppressAmbientDisplay()) {
            this.mPowerManager.suppressAmbientDisplay(SUPPRESS_AMBIENT_DISPLAY_TOKEN, zenDeviceEffects.shouldSuppressAmbientDisplay());
        }
        if (this.mLastAppliedEffects.shouldDisplayGrayscale() != zenDeviceEffects.shouldDisplayGrayscale() && this.mColorDisplayManager != null) {
            this.mColorDisplayManager.setSaturationLevel(zenDeviceEffects.shouldDisplayGrayscale() ? 0 : 100);
        }
        if (this.mLastAppliedEffects.shouldDimWallpaper() != zenDeviceEffects.shouldDimWallpaper() && this.mWallpaperManager != null) {
            this.mWallpaperManager.setWallpaperDimAmount(zenDeviceEffects.shouldDimWallpaper() ? WALLPAPER_DIM_AMOUNT_DIMMED : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        }
        if (this.mLastAppliedEffects.shouldUseNightMode() != zenDeviceEffects.shouldUseNightMode()) {
            updateOrScheduleNightMode(zenDeviceEffects.shouldUseNightMode(), i);
        }
    }

    private void updateOrScheduleNightMode(boolean z, int i) {
        this.mPendingNightMode = z;
        if (i == 1 || i == 2 || i == 3 || !this.mPowerManager.isInteractive()) {
            unregisterScreenOffReceiver();
            updateNightModeImmediately(z);
        } else {
            registerScreenOffReceiver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNightModeImmediately(final boolean z) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.DefaultDeviceEffectsApplier$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.notification.DefaultDeviceEffectsApplier.this.lambda$updateNightModeImmediately$1(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNightModeImmediately$1(boolean z) throws java.lang.Exception {
        this.mUiModeManager.setAttentionModeThemeOverlay(z ? 1001 : 1000);
    }

    private void registerScreenOffReceiver() {
        synchronized (this.mRegisterReceiverLock) {
            try {
                if (!this.mIsScreenOffReceiverRegistered) {
                    this.mContext.registerReceiver(this.mNightModeWhenScreenOff, SCREEN_OFF_INTENT_FILTER, 4);
                    this.mIsScreenOffReceiverRegistered = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterScreenOffReceiver() {
        synchronized (this.mRegisterReceiverLock) {
            try {
                if (this.mIsScreenOffReceiverRegistered) {
                    this.mIsScreenOffReceiverRegistered = false;
                    this.mContext.unregisterReceiver(this.mNightModeWhenScreenOff);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
