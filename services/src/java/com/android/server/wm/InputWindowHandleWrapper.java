package com.android.server.wm;

/* loaded from: classes3.dex */
class InputWindowHandleWrapper {
    private boolean mChanged = true;

    @android.annotation.NonNull
    private final android.view.InputWindowHandle mHandle;

    InputWindowHandleWrapper(@android.annotation.NonNull android.view.InputWindowHandle inputWindowHandle) {
        this.mHandle = inputWindowHandle;
    }

    boolean isChanged() {
        return this.mChanged;
    }

    void forceChange() {
        this.mChanged = true;
    }

    void applyChangesToSurface(@android.annotation.NonNull android.view.SurfaceControl.Transaction transaction, @android.annotation.NonNull android.view.SurfaceControl surfaceControl) {
        transaction.setInputWindowInfo(surfaceControl, this.mHandle);
        this.mChanged = false;
    }

    int getDisplayId() {
        return this.mHandle.displayId;
    }

    boolean isFocusable() {
        return (this.mHandle.inputConfig & 4) == 0;
    }

    boolean isPaused() {
        return (this.mHandle.inputConfig & 128) != 0;
    }

    boolean isTrustedOverlay() {
        return (this.mHandle.inputConfig & 256) != 0;
    }

    boolean hasWallpaper() {
        return (this.mHandle.inputConfig & 32) != 0;
    }

    android.view.InputApplicationHandle getInputApplicationHandle() {
        return this.mHandle.inputApplicationHandle;
    }

    void setInputApplicationHandle(android.view.InputApplicationHandle inputApplicationHandle) {
        if (this.mHandle.inputApplicationHandle == inputApplicationHandle) {
            return;
        }
        this.mHandle.inputApplicationHandle = inputApplicationHandle;
        this.mChanged = true;
    }

    void setToken(android.os.IBinder iBinder) {
        if (this.mHandle.token == iBinder) {
            return;
        }
        this.mHandle.token = iBinder;
        this.mChanged = true;
    }

    void setName(java.lang.String str) {
        if (java.util.Objects.equals(this.mHandle.name, str)) {
            return;
        }
        this.mHandle.name = str;
        this.mChanged = true;
    }

    void setLayoutParamsFlags(int i) {
        if (this.mHandle.layoutParamsFlags == i) {
            return;
        }
        this.mHandle.layoutParamsFlags = i;
        this.mChanged = true;
    }

    void setLayoutParamsType(int i) {
        if (this.mHandle.layoutParamsType == i) {
            return;
        }
        this.mHandle.layoutParamsType = i;
        this.mChanged = true;
    }

    void setDispatchingTimeoutMillis(long j) {
        if (this.mHandle.dispatchingTimeoutMillis == j) {
            return;
        }
        this.mHandle.dispatchingTimeoutMillis = j;
        this.mChanged = true;
    }

    void setTouchableRegion(android.graphics.Region region) {
        if (this.mHandle.touchableRegion.equals(region)) {
            return;
        }
        this.mHandle.touchableRegion.set(region);
        this.mChanged = true;
    }

    void clearTouchableRegion() {
        if (this.mHandle.touchableRegion.isEmpty()) {
            return;
        }
        this.mHandle.touchableRegion.setEmpty();
        this.mChanged = true;
    }

    void setFocusable(boolean z) {
        if (isFocusable() == z) {
            return;
        }
        this.mHandle.setInputConfig(4, !z);
        this.mChanged = true;
    }

    void setTouchOcclusionMode(int i) {
        if (this.mHandle.touchOcclusionMode == i) {
            return;
        }
        this.mHandle.touchOcclusionMode = i;
        this.mChanged = true;
    }

    void setHasWallpaper(boolean z) {
        if (hasWallpaper() == z) {
            return;
        }
        this.mHandle.setInputConfig(32, z);
        this.mChanged = true;
    }

    void setPaused(boolean z) {
        if (isPaused() == z) {
            return;
        }
        this.mHandle.setInputConfig(128, z);
        this.mChanged = true;
    }

    void setTrustedOverlay(boolean z) {
        if (isTrustedOverlay() == z) {
            return;
        }
        this.mHandle.setInputConfig(256, z);
        this.mChanged = true;
    }

    void setTrustedOverlay(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, boolean z) {
        this.mHandle.setTrustedOverlay(transaction, surfaceControl, z);
    }

    void setOwnerPid(int i) {
        if (this.mHandle.ownerPid == i) {
            return;
        }
        this.mHandle.ownerPid = i;
        this.mChanged = true;
    }

    void setOwnerUid(int i) {
        if (this.mHandle.ownerUid == i) {
            return;
        }
        this.mHandle.ownerUid = i;
        this.mChanged = true;
    }

    void setPackageName(java.lang.String str) {
        if (java.util.Objects.equals(this.mHandle.packageName, str)) {
            return;
        }
        this.mHandle.packageName = str;
        this.mChanged = true;
    }

    void setDisplayId(int i) {
        if (this.mHandle.displayId == i) {
            return;
        }
        this.mHandle.displayId = i;
        this.mChanged = true;
    }

    void setSurfaceInset(int i) {
        if (this.mHandle.surfaceInset == i) {
            return;
        }
        this.mHandle.surfaceInset = i;
        this.mChanged = true;
    }

    void setScaleFactor(float f) {
        if (this.mHandle.scaleFactor == f) {
            return;
        }
        this.mHandle.scaleFactor = f;
        this.mChanged = true;
    }

    void setTouchableRegionCrop(@android.annotation.Nullable android.view.SurfaceControl surfaceControl) {
        if (this.mHandle.touchableRegionSurfaceControl.get() == surfaceControl) {
            return;
        }
        this.mHandle.setTouchableRegionCrop(surfaceControl);
        this.mChanged = true;
    }

    void setReplaceTouchableRegionWithCrop(boolean z) {
        if (this.mHandle.replaceTouchableRegionWithCrop == z) {
            return;
        }
        this.mHandle.replaceTouchableRegionWithCrop = z;
        this.mChanged = true;
    }

    void setWindowToken(android.os.IBinder iBinder) {
        if (this.mHandle.getWindowToken() == iBinder) {
            return;
        }
        this.mHandle.setWindowToken(iBinder);
        this.mChanged = true;
    }

    void setInputConfigMasked(int i, int i2) {
        int i3 = i & i2;
        if (i3 == (this.mHandle.inputConfig & i2)) {
            return;
        }
        android.view.InputWindowHandle inputWindowHandle = this.mHandle;
        inputWindowHandle.inputConfig = (~i2) & inputWindowHandle.inputConfig;
        android.view.InputWindowHandle inputWindowHandle2 = this.mHandle;
        inputWindowHandle2.inputConfig = i3 | inputWindowHandle2.inputConfig;
        this.mChanged = true;
    }

    void setFocusTransferTarget(android.os.IBinder iBinder) {
        if (this.mHandle.focusTransferTarget == iBinder) {
            return;
        }
        this.mHandle.focusTransferTarget = iBinder;
        this.mChanged = true;
    }

    public java.lang.String toString() {
        return this.mHandle + ", changed=" + this.mChanged;
    }
}
