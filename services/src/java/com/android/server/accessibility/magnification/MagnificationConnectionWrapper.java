package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
class MagnificationConnectionWrapper {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "MagnificationConnectionWrapper";

    @android.annotation.NonNull
    private final android.view.accessibility.IMagnificationConnection mConnection;

    @android.annotation.NonNull
    private final com.android.server.accessibility.AccessibilityTraceManager mTrace;

    MagnificationConnectionWrapper(@android.annotation.NonNull android.view.accessibility.IMagnificationConnection iMagnificationConnection, @android.annotation.NonNull com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        this.mConnection = iMagnificationConnection;
        this.mTrace = accessibilityTraceManager;
    }

    void unlinkToDeath(@android.annotation.NonNull android.os.IBinder.DeathRecipient deathRecipient) {
        this.mConnection.asBinder().unlinkToDeath(deathRecipient, 0);
    }

    void linkToDeath(@android.annotation.NonNull android.os.IBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
        this.mConnection.asBinder().linkToDeath(deathRecipient, 0);
    }

    boolean onFullscreenMagnificationActivationChanged(int i, boolean z) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.onFullscreenMagnificationActivationChanged", 128L);
        }
        try {
            this.mConnection.onFullscreenMagnificationActivationChanged(i, z);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.enableWindowMagnification", 128L, "displayId=" + i + ";scale=" + f + ";centerX=" + f2 + ";centerY=" + f3 + ";magnificationFrameOffsetRatioX=" + f4 + ";magnificationFrameOffsetRatioY=" + f5 + ";callback=" + magnificationAnimationCallback);
        }
        try {
            this.mConnection.enableWindowMagnification(i, f, f2, f3, f4, f5, transformToRemoteCallback(magnificationAnimationCallback, this.mTrace));
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean setScaleForWindowMagnification(int i, float f) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.setScale", 128L, "displayId=" + i + ";scale=" + f);
        }
        try {
            this.mConnection.setScaleForWindowMagnification(i, f);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean disableWindowMagnification(int i, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.disableWindowMagnification", 128L, "displayId=" + i + ";callback=" + magnificationAnimationCallback);
        }
        try {
            this.mConnection.disableWindowMagnification(i, transformToRemoteCallback(magnificationAnimationCallback, this.mTrace));
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean moveWindowMagnifier(int i, float f, float f2) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.moveWindowMagnifier", 128L, "displayId=" + i + ";offsetX=" + f + ";offsetY=" + f2);
        }
        try {
            this.mConnection.moveWindowMagnifier(i, f, f2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean moveWindowMagnifierToPosition(int i, float f, float f2, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.moveWindowMagnifierToPosition", 128L, "displayId=" + i + ";positionX=" + f + ";positionY=" + f2);
        }
        try {
            this.mConnection.moveWindowMagnifierToPosition(i, f, f2, transformToRemoteCallback(magnificationAnimationCallback, this.mTrace));
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean showMagnificationButton(int i, int i2) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.showMagnificationButton", 128L, "displayId=" + i + ";mode=" + i2);
        }
        try {
            this.mConnection.showMagnificationButton(i, i2);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean removeMagnificationButton(int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.removeMagnificationButton", 128L, "displayId=" + i);
        }
        try {
            this.mConnection.removeMagnificationButton(i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean removeMagnificationSettingsPanel(int i) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.removeMagnificationSettingsPanel", 128L, "displayId=" + i);
        }
        try {
            this.mConnection.removeMagnificationSettingsPanel(i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean onUserMagnificationScaleChanged(int i, int i2, float f) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.onMagnificationScaleUpdated", 128L, "displayId=" + i2);
        }
        try {
            this.mConnection.onUserMagnificationScaleChanged(i, i2, f);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    boolean setConnectionCallback(android.view.accessibility.IMagnificationConnectionCallback iMagnificationConnectionCallback) {
        if (this.mTrace.isA11yTracingEnabledForTypes(384L)) {
            this.mTrace.logTrace("MagnificationConnectionWrapper.setConnectionCallback", 384L, "callback=" + iMagnificationConnectionCallback);
        }
        try {
            this.mConnection.setConnectionCallback(iMagnificationConnectionCallback);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @android.annotation.Nullable
    private static android.view.accessibility.IRemoteMagnificationAnimationCallback transformToRemoteCallback(android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        if (magnificationAnimationCallback == null) {
            return null;
        }
        return new com.android.server.accessibility.magnification.MagnificationConnectionWrapper.RemoteAnimationCallback(magnificationAnimationCallback, accessibilityTraceManager);
    }

    private static class RemoteAnimationCallback extends android.view.accessibility.IRemoteMagnificationAnimationCallback.Stub {
        private final android.view.accessibility.MagnificationAnimationCallback mCallback;
        private final com.android.server.accessibility.AccessibilityTraceManager mTrace;

        RemoteAnimationCallback(@android.annotation.NonNull android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, @android.annotation.NonNull com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
            this.mCallback = magnificationAnimationCallback;
            this.mTrace = accessibilityTraceManager;
            if (this.mTrace.isA11yTracingEnabledForTypes(64L)) {
                this.mTrace.logTrace("RemoteAnimationCallback.constructor", 64L, "callback=" + magnificationAnimationCallback);
            }
        }

        public void onResult(boolean z) throws android.os.RemoteException {
            this.mCallback.onResult(z);
            if (this.mTrace.isA11yTracingEnabledForTypes(64L)) {
                this.mTrace.logTrace("RemoteAnimationCallback.onResult", 64L, "success=" + z);
            }
        }
    }
}
