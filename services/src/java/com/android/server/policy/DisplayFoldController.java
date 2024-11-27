package com.android.server.policy;

/* loaded from: classes2.dex */
class DisplayFoldController {
    private final int mDisplayId;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private java.lang.String mFocusedApp;
    private java.lang.Boolean mFolded;
    private final android.graphics.Rect mFoldedArea;
    private final android.os.Handler mHandler;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private android.graphics.Rect mOverrideFoldedArea = new android.graphics.Rect();
    private final android.view.DisplayInfo mNonOverrideDisplayInfo = new android.view.DisplayInfo();
    private final android.os.RemoteCallbackList<android.view.IDisplayFoldListener> mListeners = new android.os.RemoteCallbackList<>();
    private final com.android.server.policy.DisplayFoldDurationLogger mDurationLogger = new com.android.server.policy.DisplayFoldDurationLogger();

    DisplayFoldController(android.content.Context context, com.android.server.wm.WindowManagerInternal windowManagerInternal, android.hardware.display.DisplayManagerInternal displayManagerInternal, int i, android.graphics.Rect rect, android.os.Handler handler) {
        this.mWindowManagerInternal = windowManagerInternal;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mDisplayId = i;
        this.mFoldedArea = new android.graphics.Rect(rect);
        this.mHandler = handler;
        ((android.hardware.devicestate.DeviceStateManager) context.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(handler), new android.hardware.devicestate.DeviceStateManager.FoldStateListener(context, new java.util.function.Consumer() { // from class: com.android.server.policy.DisplayFoldController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.policy.DisplayFoldController.this.lambda$new$0((java.lang.Boolean) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.Boolean bool) {
        setDeviceFolded(bool.booleanValue());
    }

    void finishedGoingToSleep() {
        this.mDurationLogger.onFinishedGoingToSleep();
    }

    void finishedWakingUp() {
        this.mDurationLogger.onFinishedWakingUp(this.mFolded);
    }

    private void setDeviceFolded(boolean z) {
        android.graphics.Rect rect;
        if (this.mFolded != null && this.mFolded.booleanValue() == z) {
            return;
        }
        if (!this.mOverrideFoldedArea.isEmpty()) {
            rect = this.mOverrideFoldedArea;
        } else if (!this.mFoldedArea.isEmpty()) {
            rect = this.mFoldedArea;
        } else {
            rect = null;
        }
        if (rect != null) {
            if (!z) {
                this.mDisplayManagerInternal.setDisplayScalingDisabled(this.mDisplayId, false);
                this.mWindowManagerInternal.clearForcedDisplaySize(this.mDisplayId);
                this.mDisplayManagerInternal.setDisplayOffsets(this.mDisplayId, 0, 0);
            } else {
                this.mDisplayManagerInternal.getNonOverrideDisplayInfo(this.mDisplayId, this.mNonOverrideDisplayInfo);
                int width = ((this.mNonOverrideDisplayInfo.logicalWidth - rect.width()) / 2) - rect.left;
                int height = ((this.mNonOverrideDisplayInfo.logicalHeight - rect.height()) / 2) - rect.top;
                this.mDisplayManagerInternal.setDisplayScalingDisabled(this.mDisplayId, true);
                this.mWindowManagerInternal.setForcedDisplaySize(this.mDisplayId, rect.width(), rect.height());
                this.mDisplayManagerInternal.setDisplayOffsets(this.mDisplayId, -width, -height);
            }
        }
        this.mDurationLogger.setDeviceFolded(z);
        this.mDurationLogger.logFocusedAppWithFoldState(z, this.mFocusedApp);
        this.mFolded = java.lang.Boolean.valueOf(z);
        int beginBroadcast = this.mListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mListeners.getBroadcastItem(i).onDisplayFoldChanged(this.mDisplayId, z);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mListeners.finishBroadcast();
    }

    void registerDisplayFoldListener(final android.view.IDisplayFoldListener iDisplayFoldListener) {
        this.mListeners.register(iDisplayFoldListener);
        if (this.mFolded == null) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.DisplayFoldController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.policy.DisplayFoldController.this.lambda$registerDisplayFoldListener$1(iDisplayFoldListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDisplayFoldListener$1(android.view.IDisplayFoldListener iDisplayFoldListener) {
        try {
            iDisplayFoldListener.onDisplayFoldChanged(this.mDisplayId, this.mFolded.booleanValue());
        } catch (android.os.RemoteException e) {
        }
    }

    void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) {
        this.mListeners.unregister(iDisplayFoldListener);
    }

    void setOverrideFoldedArea(android.graphics.Rect rect) {
        this.mOverrideFoldedArea.set(rect);
    }

    android.graphics.Rect getFoldedArea() {
        if (!this.mOverrideFoldedArea.isEmpty()) {
            return this.mOverrideFoldedArea;
        }
        return this.mFoldedArea;
    }

    void onDefaultDisplayFocusChanged(java.lang.String str) {
        this.mFocusedApp = str;
    }

    static com.android.server.policy.DisplayFoldController create(android.content.Context context, int i) {
        android.graphics.Rect rect;
        com.android.server.wm.WindowManagerInternal windowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        android.hardware.display.DisplayManagerInternal displayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        java.lang.String string = context.getResources().getString(android.R.string.config_dozeUdfpsLongPressSensorType);
        if (string == null || string.isEmpty()) {
            rect = new android.graphics.Rect();
        } else {
            rect = android.graphics.Rect.unflattenFromString(string);
        }
        return new com.android.server.policy.DisplayFoldController(context, windowManagerInternal, displayManagerInternal, i, rect, com.android.server.DisplayThread.getHandler());
    }
}
