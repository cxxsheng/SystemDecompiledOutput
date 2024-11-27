package com.android.server.input;

/* loaded from: classes2.dex */
class GestureMonitorSpyWindow {
    final android.view.InputApplicationHandle mApplicationHandle;
    final android.view.InputChannel mClientChannel;
    final android.view.SurfaceControl mInputSurface;
    final android.os.IBinder mMonitorToken;
    final android.view.InputWindowHandle mWindowHandle;

    GestureMonitorSpyWindow(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3, android.view.SurfaceControl surfaceControl, android.view.InputChannel inputChannel) {
        this.mMonitorToken = iBinder;
        this.mClientChannel = inputChannel;
        this.mInputSurface = surfaceControl;
        this.mApplicationHandle = new android.view.InputApplicationHandle((android.os.IBinder) null, str, android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
        this.mWindowHandle = new android.view.InputWindowHandle(this.mApplicationHandle, i);
        this.mWindowHandle.name = str;
        this.mWindowHandle.token = this.mClientChannel.getToken();
        this.mWindowHandle.layoutParamsType = 2015;
        this.mWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        this.mWindowHandle.ownerPid = i2;
        this.mWindowHandle.ownerUid = i3;
        this.mWindowHandle.scaleFactor = 1.0f;
        this.mWindowHandle.replaceTouchableRegionWithCrop((android.view.SurfaceControl) null);
        this.mWindowHandle.inputConfig = 16388;
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        this.mWindowHandle.setTrustedOverlay(transaction, this.mInputSurface, true);
        transaction.setInputWindowInfo(this.mInputSurface, this.mWindowHandle);
        transaction.setLayer(this.mInputSurface, 1);
        transaction.setPosition(this.mInputSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        transaction.setCrop(this.mInputSurface, null);
        transaction.show(this.mInputSurface);
        transaction.apply();
    }

    void remove() {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.hide(this.mInputSurface);
        transaction.remove(this.mInputSurface);
        transaction.apply();
        this.mClientChannel.dispose();
    }

    java.lang.String dump() {
        return "name='" + this.mWindowHandle.name + "', inputChannelToken=" + this.mClientChannel.getToken() + " displayId=" + this.mWindowHandle.displayId;
    }
}
