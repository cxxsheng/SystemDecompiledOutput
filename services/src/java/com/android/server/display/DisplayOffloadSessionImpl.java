package com.android.server.display;

/* loaded from: classes.dex */
public class DisplayOffloadSessionImpl implements android.hardware.display.DisplayManagerInternal.DisplayOffloadSession {

    @android.annotation.Nullable
    private final android.hardware.display.DisplayManagerInternal.DisplayOffloader mDisplayOffloader;
    private final com.android.server.display.DisplayPowerControllerInterface mDisplayPowerController;
    private boolean mIsActive;

    public DisplayOffloadSessionImpl(@android.annotation.Nullable android.hardware.display.DisplayManagerInternal.DisplayOffloader displayOffloader, com.android.server.display.DisplayPowerControllerInterface displayPowerControllerInterface) {
        this.mDisplayOffloader = displayOffloader;
        this.mDisplayPowerController = displayPowerControllerInterface;
    }

    public void setDozeStateOverride(int i) {
        this.mDisplayPowerController.overrideDozeScreenState(i);
    }

    public boolean isActive() {
        return this.mIsActive;
    }

    public void updateBrightness(float f) {
        if (this.mIsActive) {
            this.mDisplayPowerController.setBrightnessFromOffload(f);
        }
    }

    public boolean blockScreenOn(java.lang.Runnable runnable) {
        if (this.mDisplayOffloader == null) {
            return false;
        }
        this.mDisplayOffloader.onBlockingScreenOn(runnable);
        return true;
    }

    public float[] getAutoBrightnessLevels(int i) {
        if (i < 0 || i > 2) {
            throw new java.lang.IllegalArgumentException("Unknown auto-brightness mode: " + i);
        }
        return this.mDisplayPowerController.getAutoBrightnessLevels(i);
    }

    public float[] getAutoBrightnessLuxLevels(int i) {
        if (i < 0 || i > 2) {
            throw new java.lang.IllegalArgumentException("Unknown auto-brightness mode: " + i);
        }
        return this.mDisplayPowerController.getAutoBrightnessLuxLevels(i);
    }

    public boolean startOffload() {
        if (this.mDisplayOffloader == null || this.mIsActive) {
            return false;
        }
        android.os.Trace.traceBegin(131072L, "DisplayOffloader#startOffload");
        try {
            boolean startOffload = this.mDisplayOffloader.startOffload();
            this.mIsActive = startOffload;
            return startOffload;
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    public void stopOffload() {
        if (this.mDisplayOffloader == null || !this.mIsActive) {
            return;
        }
        android.os.Trace.traceBegin(131072L, "DisplayOffloader#stopOffload");
        try {
            this.mDisplayOffloader.stopOffload();
            this.mIsActive = false;
            this.mDisplayPowerController.setBrightnessFromOffload(Float.NaN);
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }
}
