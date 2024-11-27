package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class OneTouchPlayAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int LOOP_COUNTER_MAX = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_CHECK_STANDBY_PROCESS_STARTED = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAITING_FOR_REPORT_POWER_STATUS = 1;
    private static final java.lang.String TAG = "OneTouchPlayAction";
    private final boolean mIsCec20;
    private int mPowerStatusCounter;
    private com.android.server.hdmi.HdmiCecLocalDeviceSource mSource;
    private final int mTargetAddress;

    static com.android.server.hdmi.OneTouchPlayAction create(com.android.server.hdmi.HdmiCecLocalDeviceSource hdmiCecLocalDeviceSource, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        if (hdmiCecLocalDeviceSource == null || iHdmiControlCallback == null) {
            android.util.Slog.e(TAG, "Wrong arguments");
            return null;
        }
        return new com.android.server.hdmi.OneTouchPlayAction(hdmiCecLocalDeviceSource, i, iHdmiControlCallback);
    }

    private OneTouchPlayAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this(hdmiCecLocalDevice, i, iHdmiControlCallback, hdmiCecLocalDevice.getDeviceInfo().getCecVersion() >= 6 && getTargetCecVersion(hdmiCecLocalDevice, i) >= 6);
    }

    @com.android.internal.annotations.VisibleForTesting
    OneTouchPlayAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback, boolean z) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mPowerStatusCounter = 0;
        this.mTargetAddress = i;
        this.mIsCec20 = z;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mSource = source();
        if (!this.mSource.mService.getPowerManager().isInteractive()) {
            android.util.Slog.d(TAG, "PowerManager is not interactive. Delay the action to check if standby started!");
            this.mState = 2;
            addTimer(this.mState, 2000);
            return true;
        }
        startAction();
        return true;
    }

    private void startAction() {
        android.util.Slog.i(TAG, "Start action.");
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildTextViewOn(getSourceAddress(), this.mTargetAddress));
        boolean z = this.mIsCec20 && getTargetDevicePowerStatus(this.mSource, this.mTargetAddress, -1) == 0;
        setAndBroadcastActiveSource();
        if (shouldTurnOnConnectedAudioSystem()) {
            sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSystemAudioModeRequest(getSourceAddress(), 5, getSourcePath(), true));
        }
        if (!this.mIsCec20) {
            queryDevicePowerStatus();
        } else {
            int targetDevicePowerStatus = getTargetDevicePowerStatus(this.mSource, this.mTargetAddress, -1);
            if (targetDevicePowerStatus == -1) {
                queryDevicePowerStatus();
            } else if (targetDevicePowerStatus == 0) {
                if (!z) {
                    maySendActiveSource();
                }
                finishWithCallback(0);
                return;
            }
        }
        this.mState = 1;
        addTimer(this.mState, 2000);
    }

    private void setAndBroadcastActiveSource() {
        this.mSource.mService.setAndBroadcastActiveSourceFromOneDeviceType(this.mTargetAddress, getSourcePath(), "OneTouchPlayAction#broadcastActiveSource()");
        if (this.mSource.mService.audioSystem() != null) {
            this.mSource = this.mSource.mService.audioSystem();
        }
        this.mSource.setRoutingPort(0);
        this.mSource.setLocalActivePort(0);
    }

    private void maySendActiveSource() {
        this.mSource.maySendActiveSource(this.mTargetAddress);
    }

    private void queryDevicePowerStatus() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), this.mTargetAddress));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || this.mTargetAddress != hdmiCecMessage.getSource() || hdmiCecMessage.getOpcode() != 144) {
            return false;
        }
        if (hdmiCecMessage.getParams()[0] == 0) {
            maySendActiveSource();
            finishWithCallback(0);
        }
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState != i) {
        }
        switch (i) {
            case 1:
                int i2 = this.mPowerStatusCounter;
                this.mPowerStatusCounter = i2 + 1;
                if (i2 < 10) {
                    queryDevicePowerStatus();
                    addTimer(this.mState, 2000);
                    break;
                } else {
                    finishWithCallback(1);
                    break;
                }
            case 2:
                android.util.Slog.d(TAG, "Action was not removed, start the action.");
                startAction();
                break;
        }
    }

    private boolean shouldTurnOnConnectedAudioSystem() {
        com.android.server.hdmi.HdmiControlService hdmiControlService = this.mSource.mService;
        if (hdmiControlService.isAudioSystemDevice()) {
            return false;
        }
        java.lang.String stringValue = hdmiControlService.getHdmiCecConfig().getStringValue("power_control_mode");
        return stringValue.equals("to_tv_and_audio_system") || stringValue.equals("broadcast");
    }

    private static int getTargetCecVersion(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = hdmiCecLocalDevice.mService.getHdmiCecNetwork().getCecDeviceInfo(i);
        if (cecDeviceInfo != null) {
            return cecDeviceInfo.getCecVersion();
        }
        return 5;
    }

    private static int getTargetDevicePowerStatus(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, int i2) {
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = hdmiCecLocalDevice.mService.getHdmiCecNetwork().getCecDeviceInfo(i);
        if (cecDeviceInfo != null) {
            return cecDeviceInfo.getDevicePowerStatus();
        }
        return i2;
    }
}
