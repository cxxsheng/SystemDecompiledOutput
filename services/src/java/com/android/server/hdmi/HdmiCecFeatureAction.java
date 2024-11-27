package com.android.server.hdmi;

/* loaded from: classes2.dex */
abstract class HdmiCecFeatureAction {
    protected static final int MSG_TIMEOUT = 100;
    protected static final int STATE_NONE = 0;
    private static final java.lang.String TAG = "HdmiCecFeatureAction";
    protected com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer mActionTimer;
    final java.util.List<android.hardware.hdmi.IHdmiControlCallback> mCallbacks;
    private java.util.ArrayList<android.util.Pair<com.android.server.hdmi.HdmiCecFeatureAction, java.lang.Runnable>> mOnFinishedCallbacks;
    private final com.android.server.hdmi.HdmiControlService mService;
    private final com.android.server.hdmi.HdmiCecLocalDevice mSource;
    protected int mState;

    interface ActionTimer {
        void clearTimerMessage();

        void sendTimerMessage(int i, long j);
    }

    abstract void handleTimerEvent(int i);

    abstract boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage);

    abstract boolean start();

    HdmiCecFeatureAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        this(hdmiCecLocalDevice, new java.util.ArrayList());
    }

    HdmiCecFeatureAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this(hdmiCecLocalDevice, (java.util.List<android.hardware.hdmi.IHdmiControlCallback>) java.util.Arrays.asList(iHdmiControlCallback));
    }

    HdmiCecFeatureAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, java.util.List<android.hardware.hdmi.IHdmiControlCallback> list) {
        this.mState = 0;
        this.mCallbacks = new java.util.ArrayList();
        java.util.Iterator<android.hardware.hdmi.IHdmiControlCallback> it = list.iterator();
        while (it.hasNext()) {
            addCallback(it.next());
        }
        this.mSource = hdmiCecLocalDevice;
        this.mService = this.mSource.getService();
        this.mActionTimer = createActionTimer(this.mService.getServiceLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    void setActionTimer(com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer actionTimer) {
        this.mActionTimer = actionTimer;
    }

    private class ActionTimerHandler extends android.os.Handler implements com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer {
        public ActionTimerHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer
        public void sendTimerMessage(int i, long j) {
            sendMessageDelayed(obtainMessage(100, i, 0), j);
        }

        @Override // com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer
        public void clearTimerMessage() {
            removeMessages(100);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 100:
                    com.android.server.hdmi.HdmiCecFeatureAction.this.handleTimerEvent(message.arg1);
                    break;
                default:
                    android.util.Slog.w(com.android.server.hdmi.HdmiCecFeatureAction.TAG, "Unsupported message:" + message.what);
                    break;
            }
        }
    }

    private com.android.server.hdmi.HdmiCecFeatureAction.ActionTimer createActionTimer(android.os.Looper looper) {
        return new com.android.server.hdmi.HdmiCecFeatureAction.ActionTimerHandler(looper);
    }

    protected void addTimer(int i, int i2) {
        this.mActionTimer.sendTimerMessage(i, i2);
    }

    boolean started() {
        return this.mState != 0;
    }

    protected final void sendCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        this.mService.sendCecCommand(hdmiCecMessage);
    }

    protected final void sendCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        this.mService.sendCecCommand(hdmiCecMessage, sendMessageCallback);
    }

    protected final void sendCommandWithoutRetries(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        this.mService.sendCecCommandWithoutRetries(hdmiCecMessage, sendMessageCallback);
    }

    protected final void addAndStartAction(com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        this.mSource.addAndStartAction(hdmiCecFeatureAction);
    }

    protected final <T extends com.android.server.hdmi.HdmiCecFeatureAction> java.util.List<T> getActions(java.lang.Class<T> cls) {
        return this.mSource.getActions(cls);
    }

    protected final com.android.server.hdmi.HdmiCecMessageCache getCecMessageCache() {
        return this.mSource.getCecMessageCache();
    }

    protected final void removeAction(com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        this.mSource.removeAction(hdmiCecFeatureAction);
    }

    protected final <T extends com.android.server.hdmi.HdmiCecFeatureAction> void removeAction(java.lang.Class<T> cls) {
        this.mSource.removeActionExcept(cls, null);
    }

    protected final <T extends com.android.server.hdmi.HdmiCecFeatureAction> void removeActionExcept(java.lang.Class<T> cls, com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction) {
        this.mSource.removeActionExcept(cls, hdmiCecFeatureAction);
    }

    protected final void pollDevices(com.android.server.hdmi.HdmiControlService.DevicePollingCallback devicePollingCallback, int i, int i2) {
        this.mService.pollDevices(devicePollingCallback, getSourceAddress(), i, i2);
    }

    void clear() {
        this.mState = 0;
        this.mActionTimer.clearTimerMessage();
    }

    protected void finish() {
        finish(true);
    }

    void finish(boolean z) {
        clear();
        if (z) {
            removeAction(this);
        }
        if (this.mOnFinishedCallbacks != null) {
            java.util.Iterator<android.util.Pair<com.android.server.hdmi.HdmiCecFeatureAction, java.lang.Runnable>> it = this.mOnFinishedCallbacks.iterator();
            while (it.hasNext()) {
                android.util.Pair<com.android.server.hdmi.HdmiCecFeatureAction, java.lang.Runnable> next = it.next();
                if (((com.android.server.hdmi.HdmiCecFeatureAction) next.first).mState != 0) {
                    ((java.lang.Runnable) next.second).run();
                }
            }
            this.mOnFinishedCallbacks = null;
        }
    }

    protected final com.android.server.hdmi.HdmiCecLocalDevice localDevice() {
        return this.mSource;
    }

    protected final com.android.server.hdmi.HdmiCecLocalDevicePlayback playback() {
        return (com.android.server.hdmi.HdmiCecLocalDevicePlayback) this.mSource;
    }

    protected final com.android.server.hdmi.HdmiCecLocalDeviceSource source() {
        return (com.android.server.hdmi.HdmiCecLocalDeviceSource) this.mSource;
    }

    protected final com.android.server.hdmi.HdmiCecLocalDeviceTv tv() {
        return (com.android.server.hdmi.HdmiCecLocalDeviceTv) this.mSource;
    }

    protected final com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem() {
        return (com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem) this.mSource;
    }

    protected final int getSourceAddress() {
        return this.mSource.getDeviceInfo().getLogicalAddress();
    }

    protected final int getSourcePath() {
        return this.mSource.getDeviceInfo().getPhysicalAddress();
    }

    protected final void sendUserControlPressedAndReleased(int i, int i2) {
        this.mSource.sendUserControlPressedAndReleased(i, i2);
    }

    protected final void addOnFinishedCallback(com.android.server.hdmi.HdmiCecFeatureAction hdmiCecFeatureAction, java.lang.Runnable runnable) {
        if (this.mOnFinishedCallbacks == null) {
            this.mOnFinishedCallbacks = new java.util.ArrayList<>();
        }
        this.mOnFinishedCallbacks.add(android.util.Pair.create(hdmiCecFeatureAction, runnable));
    }

    protected void finishWithCallback(int i) {
        invokeCallback(i);
        finish();
    }

    public void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this.mCallbacks.add(iHdmiControlCallback);
    }

    private void invokeCallback(int i) {
        try {
            for (android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback : this.mCallbacks) {
                if (iHdmiControlCallback != null) {
                    iHdmiControlCallback.onComplete(i);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Callback failed:" + e);
        }
    }
}
