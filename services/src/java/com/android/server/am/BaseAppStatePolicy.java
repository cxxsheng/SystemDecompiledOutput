package com.android.server.am;

/* loaded from: classes.dex */
public abstract class BaseAppStatePolicy<T extends com.android.server.am.BaseAppStateTracker> {
    protected final boolean mDefaultTrackerEnabled;
    protected final com.android.server.am.BaseAppStateTracker.Injector<?> mInjector;

    @android.annotation.NonNull
    protected final java.lang.String mKeyTrackerEnabled;
    protected final T mTracker;
    volatile boolean mTrackerEnabled;

    public abstract void onTrackerEnabled(boolean z);

    BaseAppStatePolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector<?> injector, @android.annotation.NonNull T t, @android.annotation.NonNull java.lang.String str, boolean z) {
        this.mInjector = injector;
        this.mTracker = t;
        this.mKeyTrackerEnabled = str;
        this.mDefaultTrackerEnabled = z;
    }

    void updateTrackerEnabled() {
        boolean z = android.provider.DeviceConfig.getBoolean("activity_manager", this.mKeyTrackerEnabled, this.mDefaultTrackerEnabled);
        if (z != this.mTrackerEnabled) {
            this.mTrackerEnabled = z;
            onTrackerEnabled(z);
        }
    }

    public void onPropertiesChanged(@android.annotation.NonNull java.lang.String str) {
        if (this.mKeyTrackerEnabled.equals(str)) {
            updateTrackerEnabled();
        }
    }

    public int getProposedRestrictionLevel(java.lang.String str, int i, int i2) {
        return 0;
    }

    public void onSystemReady() {
        updateTrackerEnabled();
    }

    public boolean isEnabled() {
        return this.mTrackerEnabled;
    }

    public int shouldExemptUid(int i) {
        return this.mTracker.mAppRestrictionController.getBackgroundRestrictionExemptionReason(i);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print(this.mKeyTrackerEnabled);
        printWriter.print('=');
        printWriter.println(this.mTrackerEnabled);
    }
}
