package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class HandwritingEventReceiverSurface {
    static final boolean DEBUG = false;
    public static final java.lang.String TAG = com.android.server.inputmethod.HandwritingEventReceiverSurface.class.getSimpleName();
    private final android.view.InputChannel mClientChannel;
    private final android.view.SurfaceControl mInputSurface;
    private boolean mIsIntercepting;
    private final android.view.InputWindowHandle mWindowHandle;

    HandwritingEventReceiverSurface(java.lang.String str, int i, @android.annotation.NonNull android.view.SurfaceControl surfaceControl, @android.annotation.NonNull android.view.InputChannel inputChannel) {
        this.mClientChannel = inputChannel;
        this.mInputSurface = surfaceControl;
        this.mWindowHandle = new android.view.InputWindowHandle(new android.view.InputApplicationHandle((android.os.IBinder) null, str, android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS), i);
        this.mWindowHandle.name = str;
        this.mWindowHandle.token = this.mClientChannel.getToken();
        this.mWindowHandle.layoutParamsType = 2015;
        this.mWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        this.mWindowHandle.ownerPid = android.os.Process.myPid();
        this.mWindowHandle.ownerUid = android.os.Process.myUid();
        this.mWindowHandle.scaleFactor = 1.0f;
        this.mWindowHandle.inputConfig = 49164;
        this.mWindowHandle.replaceTouchableRegionWithCrop((android.view.SurfaceControl) null);
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        this.mWindowHandle.setTrustedOverlay(transaction, this.mInputSurface, true);
        transaction.setInputWindowInfo(this.mInputSurface, this.mWindowHandle);
        transaction.setLayer(this.mInputSurface, 2);
        transaction.setPosition(this.mInputSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        transaction.setCrop(this.mInputSurface, null);
        transaction.show(this.mInputSurface);
        transaction.apply();
        this.mIsIntercepting = false;
    }

    void startIntercepting(int i, int i2) {
        this.mWindowHandle.ownerPid = i;
        this.mWindowHandle.ownerUid = i2;
        this.mWindowHandle.inputConfig &= -16385;
        new android.view.SurfaceControl.Transaction().setInputWindowInfo(this.mInputSurface, this.mWindowHandle).apply();
        this.mIsIntercepting = true;
    }

    boolean isIntercepting() {
        return this.mIsIntercepting;
    }

    void remove() {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.remove(this.mInputSurface);
        transaction.apply();
    }

    android.view.InputChannel getInputChannel() {
        return this.mClientChannel;
    }

    android.view.SurfaceControl getSurface() {
        return this.mInputSurface;
    }

    android.view.InputWindowHandle getInputWindowHandle() {
        return this.mWindowHandle;
    }
}
