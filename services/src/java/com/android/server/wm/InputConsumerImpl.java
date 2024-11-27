package com.android.server.wm;

/* loaded from: classes3.dex */
class InputConsumerImpl implements android.os.IBinder.DeathRecipient {
    final android.view.InputApplicationHandle mApplicationHandle;
    final android.view.InputChannel mClientChannel;
    final int mClientPid;
    final android.os.UserHandle mClientUser;
    final android.view.SurfaceControl mInputSurface;
    final java.lang.String mName;
    final com.android.server.wm.WindowManagerService mService;
    final android.os.IBinder mToken;
    final android.view.InputWindowHandle mWindowHandle;
    android.graphics.Rect mTmpClipRect = new android.graphics.Rect();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private final android.graphics.Point mOldPosition = new android.graphics.Point();
    private final android.graphics.Rect mOldWindowCrop = new android.graphics.Rect();

    InputConsumerImpl(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, java.lang.String str, android.view.InputChannel inputChannel, int i, android.os.UserHandle userHandle, int i2, android.view.SurfaceControl.Transaction transaction) {
        this.mService = windowManagerService;
        this.mToken = iBinder;
        this.mName = str;
        this.mClientPid = i;
        this.mClientUser = userHandle;
        this.mClientChannel = this.mService.mInputManager.createInputChannel(str);
        if (inputChannel != null) {
            this.mClientChannel.copyTo(inputChannel);
        }
        this.mApplicationHandle = new android.view.InputApplicationHandle(new android.os.Binder(), str, android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS);
        this.mWindowHandle = new android.view.InputWindowHandle(this.mApplicationHandle, i2);
        this.mWindowHandle.name = str;
        this.mWindowHandle.token = this.mClientChannel.getToken();
        this.mWindowHandle.layoutParamsType = 2022;
        this.mWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        this.mWindowHandle.ownerPid = com.android.server.wm.WindowManagerService.MY_PID;
        this.mWindowHandle.ownerUid = com.android.server.wm.WindowManagerService.MY_UID;
        this.mWindowHandle.scaleFactor = 1.0f;
        this.mWindowHandle.inputConfig = 4;
        this.mInputSurface = this.mService.makeSurfaceBuilder(this.mService.mRoot.getDisplayContent(i2).getSession()).setContainerLayer().setName("Input Consumer " + str).setCallsite("InputConsumerImpl").build();
        this.mWindowHandle.setTrustedOverlay(transaction, this.mInputSurface, true);
    }

    void linkToDeathRecipient() {
        if (this.mToken == null) {
            return;
        }
        try {
            this.mToken.linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
        }
    }

    void unlinkFromDeathRecipient() {
        if (this.mToken == null) {
            return;
        }
        this.mToken.unlinkToDeath(this, 0);
    }

    void layout(android.view.SurfaceControl.Transaction transaction, int i, int i2) {
        this.mTmpRect.set(0, 0, i, i2);
        layout(transaction, this.mTmpRect);
    }

    void layout(android.view.SurfaceControl.Transaction transaction, android.graphics.Rect rect) {
        this.mTmpClipRect.set(0, 0, rect.width(), rect.height());
        if (this.mOldPosition.equals(rect.left, rect.top) && this.mOldWindowCrop.equals(this.mTmpClipRect)) {
            return;
        }
        transaction.setPosition(this.mInputSurface, rect.left, rect.top);
        transaction.setWindowCrop(this.mInputSurface, this.mTmpClipRect);
        this.mOldPosition.set(rect.left, rect.top);
        this.mOldWindowCrop.set(this.mTmpClipRect);
    }

    void hide(android.view.SurfaceControl.Transaction transaction) {
        transaction.hide(this.mInputSurface);
    }

    void show(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer) {
        transaction.show(this.mInputSurface);
        transaction.setInputWindowInfo(this.mInputSurface, this.mWindowHandle);
        transaction.setRelativeLayer(this.mInputSurface, windowContainer.getSurfaceControl(), windowContainer.getChildCount() + 1);
    }

    void show(android.view.SurfaceControl.Transaction transaction, int i) {
        transaction.show(this.mInputSurface);
        transaction.setInputWindowInfo(this.mInputSurface, this.mWindowHandle);
        transaction.setLayer(this.mInputSurface, i);
    }

    void reparent(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer) {
        transaction.reparent(this.mInputSurface, windowContainer.getSurfaceControl());
    }

    void disposeChannelsLw(android.view.SurfaceControl.Transaction transaction) {
        this.mService.mInputManager.removeInputChannel(this.mClientChannel.getToken());
        this.mClientChannel.dispose();
        transaction.remove(this.mInputSurface);
        unlinkFromDeathRecipient();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mService.getWindowManagerLock()) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(this.mWindowHandle.displayId);
                if (displayContent == null) {
                    return;
                }
                displayContent.getInputMonitor().destroyInputConsumer(this.mToken);
                unlinkFromDeathRecipient();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        printWriter.println(str2 + "  name=" + str + " pid=" + this.mClientPid + " user=" + this.mClientUser);
    }
}
