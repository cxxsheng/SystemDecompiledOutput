package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public class BrightnessLowLuxModifier implements com.android.server.display.brightness.clamper.BrightnessStateModifier {
    private float mBrightnessLowerBound;
    private final com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener mChangeListener;
    private final android.content.ContentResolver mContentResolver;
    private final android.os.Handler mHandler;
    private boolean mIsActive;
    private int mReason;
    protected float mSettingNitsLowerBound = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private final com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.SettingsObserver mSettingsObserver;
    private static final java.lang.String TAG = "BrightnessLowLuxModifier";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    @com.android.internal.annotations.VisibleForTesting
    BrightnessLowLuxModifier(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, android.content.Context context) {
        this.mChangeListener = clamperChangeListener;
        this.mHandler = handler;
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.SettingsObserver(this.mHandler);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessLowLuxModifier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.this.lambda$new$0();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    public void recalculateLowerBound() {
        int i;
        float floatForUser = android.provider.Settings.Secure.getFloatForUser(this.mContentResolver, "even_dimmer_min_nits", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -2);
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "even_dimmer_activated", 0, -2) == 1;
        if (floatForUser > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            i = 32;
        } else {
            i = 16;
        }
        if (this.mBrightnessLowerBound != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mReason != i || this.mIsActive != z) {
            this.mIsActive = z;
            this.mReason = i;
            if (DEBUG) {
                android.util.Slog.i(TAG, "isActive: " + z + ", settingNitsLowerBound: " + floatForUser + ", lowerBound: " + com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            }
            this.mBrightnessLowerBound = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.mChangeListener.onChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isActive() {
        return this.mIsActive;
    }

    @com.android.internal.annotations.VisibleForTesting
    public int getBrightnessReason() {
        return this.mReason;
    }

    @com.android.internal.annotations.VisibleForTesting
    public float getBrightnessLowerBound() {
        return this.mBrightnessLowerBound;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: start, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        recalculateLowerBound();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void apply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, com.android.server.display.DisplayBrightnessState.Builder builder) {
        builder.setMinBrightness(this.mBrightnessLowerBound);
        builder.setBrightness(java.lang.Math.max(this.mBrightnessLowerBound, builder.getBrightness()));
        if (com.android.internal.display.BrightnessSynchronizer.floatEquals(builder.getBrightness(), this.mBrightnessLowerBound)) {
            builder.getBrightnessReason().addModifier(this.mReason);
        }
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void stop() {
        this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessLowLuxModifier:");
        printWriter.println("  mBrightnessLowerBound=" + this.mBrightnessLowerBound);
        printWriter.println("  mIsActive=" + this.mIsActive);
        printWriter.println("  mReason=" + this.mReason);
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
            com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.this.mContentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("even_dimmer_min_nits"), false, this);
            com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.this.mContentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("even_dimmer_activated"), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            com.android.server.display.brightness.clamper.BrightnessLowLuxModifier.this.recalculateLowerBound();
        }
    }
}
