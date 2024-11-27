package com.android.server.wm;

/* loaded from: classes3.dex */
class EmbeddedWindowController {
    private static final java.lang.String TAG = "WindowManager";
    private final com.android.server.wm.ActivityTaskManagerService mAtmService;
    private final java.lang.Object mGlobalLock;
    private final com.android.server.input.InputManagerService mInputManagerService;
    private android.util.ArrayMap<android.os.IBinder, com.android.server.wm.EmbeddedWindowController.EmbeddedWindow> mWindows = new android.util.ArrayMap<>();
    private android.util.ArrayMap<android.window.InputTransferToken, com.android.server.wm.EmbeddedWindowController.EmbeddedWindow> mWindowsByInputTransferToken = new android.util.ArrayMap<>();
    private android.util.ArrayMap<android.os.IBinder, com.android.server.wm.EmbeddedWindowController.EmbeddedWindow> mWindowsByWindowToken = new android.util.ArrayMap<>();

    EmbeddedWindowController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.input.InputManagerService inputManagerService) {
        this.mAtmService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.getGlobalLock();
        this.mInputManagerService = inputManagerService;
    }

    void add(final android.os.IBinder iBinder, com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow) {
        try {
            this.mWindows.put(iBinder, embeddedWindow);
            final android.window.InputTransferToken inputTransferToken = embeddedWindow.getInputTransferToken();
            this.mWindowsByInputTransferToken.put(inputTransferToken, embeddedWindow);
            this.mWindowsByWindowToken.put(embeddedWindow.getWindowToken(), embeddedWindow);
            updateProcessController(embeddedWindow);
            embeddedWindow.mClient.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.EmbeddedWindowController$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.wm.EmbeddedWindowController.this.lambda$add$0(iBinder, inputTransferToken);
                }
            }, 0);
        } catch (android.os.RemoteException e) {
            this.mWindows.remove(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add$0(android.os.IBinder iBinder, android.window.InputTransferToken inputTransferToken) {
        synchronized (this.mGlobalLock) {
            this.mWindows.remove(iBinder);
            this.mWindowsByInputTransferToken.remove(inputTransferToken);
        }
    }

    private void updateProcessController(com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow) {
        if (embeddedWindow.mHostActivityRecord == null) {
            return;
        }
        com.android.server.wm.WindowProcessController processController = this.mAtmService.getProcessController(embeddedWindow.mOwnerPid, embeddedWindow.mOwnerUid);
        if (processController == null) {
            android.util.Slog.w(TAG, "Could not find the embedding process.");
        } else {
            processController.addHostActivity(embeddedWindow.mHostActivityRecord);
        }
    }

    void remove(android.os.IBinder iBinder) {
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.EmbeddedWindowController.EmbeddedWindow valueAt = this.mWindows.valueAt(size);
            if (valueAt.mClient == iBinder) {
                this.mWindows.removeAt(size).onRemoved();
                this.mWindowsByInputTransferToken.remove(valueAt.getInputTransferToken());
                this.mWindowsByWindowToken.remove(valueAt.getWindowToken());
                return;
            }
        }
    }

    void onWindowRemoved(com.android.server.wm.WindowState windowState) {
        for (int size = this.mWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.EmbeddedWindowController.EmbeddedWindow valueAt = this.mWindows.valueAt(size);
            if (valueAt.mHostWindowState == windowState) {
                this.mWindows.removeAt(size).onRemoved();
                this.mWindowsByInputTransferToken.remove(valueAt.getInputTransferToken());
                this.mWindowsByWindowToken.remove(valueAt.getWindowToken());
            }
        }
    }

    com.android.server.wm.EmbeddedWindowController.EmbeddedWindow get(android.os.IBinder iBinder) {
        return this.mWindows.get(iBinder);
    }

    com.android.server.wm.EmbeddedWindowController.EmbeddedWindow getByInputTransferToken(android.window.InputTransferToken inputTransferToken) {
        return this.mWindowsByInputTransferToken.get(inputTransferToken);
    }

    com.android.server.wm.EmbeddedWindowController.EmbeddedWindow getByWindowToken(android.os.IBinder iBinder) {
        return this.mWindowsByWindowToken.get(iBinder);
    }

    private boolean isValidTouchGestureParams(com.android.server.wm.WindowState windowState, com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow) {
        if (embeddedWindow == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, -1797662102094201628L, 0, null, null);
            return false;
        }
        com.android.server.wm.WindowState windowState2 = embeddedWindow.getWindowState();
        if (windowState2 == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, 929964979835124721L, 0, null, null);
            return false;
        }
        if (windowState2.mClient.asBinder() != windowState.mClient.asBinder()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, 676191989331669410L, 0, null, null);
            return false;
        }
        if (embeddedWindow.getInputChannelToken() == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, 553249487221306249L, 0, null, null);
            return false;
        }
        if (windowState.mInputChannelToken == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_EMBEDDED_WINDOWS, -8678904073078032058L, 0, null, null);
            return false;
        }
        return true;
    }

    boolean transferToHost(@android.annotation.NonNull android.window.InputTransferToken inputTransferToken, @android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        com.android.server.wm.EmbeddedWindowController.EmbeddedWindow byInputTransferToken = getByInputTransferToken(inputTransferToken);
        if (!isValidTouchGestureParams(windowState, byInputTransferToken)) {
            return false;
        }
        return this.mInputManagerService.transferTouchGesture(byInputTransferToken.getInputChannelToken(), windowState.mInputChannelToken);
    }

    boolean transferToEmbedded(com.android.server.wm.WindowState windowState, @android.annotation.NonNull android.window.InputTransferToken inputTransferToken) {
        com.android.server.wm.EmbeddedWindowController.EmbeddedWindow byInputTransferToken = getByInputTransferToken(inputTransferToken);
        if (!isValidTouchGestureParams(windowState, byInputTransferToken)) {
            return false;
        }
        return this.mInputManagerService.transferTouchGesture(windowState.mInputChannelToken, byInputTransferToken.getInputChannelToken());
    }

    static class EmbeddedWindow implements com.android.server.wm.InputTarget {
        final android.os.IBinder mClient;
        final int mDisplayId;

        @android.annotation.Nullable
        final com.android.server.wm.ActivityRecord mHostActivityRecord;

        @android.annotation.Nullable
        final com.android.server.wm.WindowState mHostWindowState;
        android.view.InputChannel mInputChannel;
        private final android.window.InputTransferToken mInputTransferToken;
        private boolean mIsFocusable;
        final java.lang.String mName;
        final int mOwnerPid;
        final int mOwnerUid;
        public com.android.server.wm.Session mSession;
        final int mWindowType;
        final com.android.server.wm.WindowManagerService mWmService;

        EmbeddedWindow(com.android.server.wm.Session session, com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, com.android.server.wm.WindowState windowState, int i, int i2, int i3, int i4, android.window.InputTransferToken inputTransferToken, java.lang.String str, boolean z) {
            java.lang.String str2;
            this.mSession = session;
            this.mWmService = windowManagerService;
            this.mClient = iBinder;
            this.mHostWindowState = windowState;
            this.mHostActivityRecord = this.mHostWindowState != null ? this.mHostWindowState.mActivityRecord : null;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
            this.mWindowType = i3;
            this.mDisplayId = i4;
            this.mInputTransferToken = inputTransferToken;
            if (this.mHostWindowState != null) {
                str2 = "-" + this.mHostWindowState.getWindowTag().toString();
            } else {
                str2 = "";
            }
            this.mIsFocusable = z;
            this.mName = "Embedded{" + str + str2 + "}";
        }

        public java.lang.String toString() {
            return this.mName;
        }

        android.view.InputApplicationHandle getApplicationHandle() {
            if (this.mHostWindowState == null || this.mHostWindowState.mInputWindowHandle.getInputApplicationHandle() == null) {
                return null;
            }
            return new android.view.InputApplicationHandle(this.mHostWindowState.mInputWindowHandle.getInputApplicationHandle());
        }

        void openInputChannel(@android.annotation.NonNull android.view.InputChannel inputChannel) {
            this.mInputChannel = this.mWmService.mInputManager.createInputChannel(toString());
            this.mInputChannel.copyTo(inputChannel);
        }

        void onRemoved() {
            com.android.server.wm.WindowProcessController processController;
            if (this.mInputChannel != null) {
                this.mWmService.mInputManager.removeInputChannel(this.mInputChannel.getToken());
                this.mInputChannel.dispose();
                this.mInputChannel = null;
            }
            if (this.mHostActivityRecord != null && (processController = this.mWmService.mAtmService.getProcessController(this.mOwnerPid, this.mOwnerUid)) != null) {
                processController.removeHostActivity(this.mHostActivityRecord);
            }
        }

        @Override // com.android.server.wm.InputTarget
        public com.android.server.wm.WindowState getWindowState() {
            return this.mHostWindowState;
        }

        @Override // com.android.server.wm.InputTarget
        public int getDisplayId() {
            return this.mDisplayId;
        }

        @Override // com.android.server.wm.InputTarget
        public com.android.server.wm.DisplayContent getDisplayContent() {
            return this.mWmService.mRoot.getDisplayContent(getDisplayId());
        }

        @Override // com.android.server.wm.InputTarget
        public android.os.IBinder getWindowToken() {
            return this.mClient;
        }

        @Override // com.android.server.wm.InputTarget
        public int getPid() {
            return this.mOwnerPid;
        }

        @Override // com.android.server.wm.InputTarget
        public int getUid() {
            return this.mOwnerUid;
        }

        android.window.InputTransferToken getInputTransferToken() {
            return this.mInputTransferToken;
        }

        android.os.IBinder getInputChannelToken() {
            if (this.mInputChannel != null) {
                return this.mInputChannel.getToken();
            }
            return null;
        }

        void setIsFocusable(boolean z) {
            this.mIsFocusable = z;
        }

        @Override // com.android.server.wm.InputTarget
        public boolean receiveFocusFromTapOutside() {
            return this.mIsFocusable;
        }

        private void handleTap(boolean z) {
            if (this.mInputChannel != null) {
                if (this.mHostWindowState != null) {
                    this.mWmService.grantEmbeddedWindowFocus(null, this.mHostWindowState.mClient, this.mInputTransferToken, z);
                    if (z) {
                        this.mHostWindowState.handleTapOutsideFocusInsideSelf();
                        return;
                    }
                    return;
                }
                this.mWmService.grantEmbeddedWindowFocus(this.mSession, this.mInputTransferToken, z);
            }
        }

        @Override // com.android.server.wm.InputTarget
        public void handleTapOutsideFocusOutsideSelf() {
            handleTap(false);
        }

        @Override // com.android.server.wm.InputTarget
        public void handleTapOutsideFocusInsideSelf() {
            handleTap(true);
        }

        @Override // com.android.server.wm.InputTarget
        public boolean shouldControlIme() {
            return this.mHostWindowState != null;
        }

        @Override // com.android.server.wm.InputTarget
        public boolean canScreenshotIme() {
            return true;
        }

        @Override // com.android.server.wm.InputTarget
        public com.android.server.wm.InsetsControlTarget getImeControlTarget() {
            if (this.mHostWindowState != null) {
                return this.mHostWindowState.getImeControlTarget();
            }
            return this.mWmService.getDefaultDisplayContentLocked().mRemoteInsetsControlTarget;
        }

        @Override // com.android.server.wm.InputTarget
        public boolean isInputMethodClientFocus(int i, int i2) {
            return i == this.mOwnerUid && i2 == this.mOwnerPid;
        }

        @Override // com.android.server.wm.InputTarget
        public com.android.server.wm.ActivityRecord getActivityRecord() {
            return this.mHostActivityRecord;
        }

        @Override // com.android.server.wm.InputTarget
        public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
            long start = protoOutputStream.start(j);
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
            protoOutputStream.write(1138166333443L, "EmbeddedWindow");
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }
    }
}
