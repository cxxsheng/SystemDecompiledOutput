package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class SetAudioVolumeLevelDiscoveryAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_WAITING_FOR_FEATURE_ABORT = 1;
    private static final java.lang.String TAG = "SetAudioVolumeLevelDiscoveryAction";
    private final int mTargetAddress;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    public SetAudioVolumeLevelDiscoveryAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mTargetAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        sendCommand(com.android.server.hdmi.SetAudioVolumeLevelMessage.build(getSourceAddress(), this.mTargetAddress, 127), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction$$ExternalSyntheticLambda0
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                com.android.server.hdmi.SetAudioVolumeLevelDiscoveryAction.this.lambda$start$0(i);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0(int i) {
        if (i == 0) {
            this.mState = 1;
            addTimer(this.mState, 2000);
        } else {
            finishWithCallback(7);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
        }
        return false;
    }

    private boolean handleFeatureAbort(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.getParams().length < 2 || (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 115 || hdmiCecMessage.getSource() != this.mTargetAddress) {
            return false;
        }
        finishWithCallback(0);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (updateSetAudioVolumeLevelSupport(1)) {
            finishWithCallback(0);
        } else {
            finishWithCallback(5);
        }
    }

    private boolean updateSetAudioVolumeLevelSupport(@android.hardware.hdmi.DeviceFeatures.FeatureSupportStatus int i) {
        com.android.server.hdmi.HdmiCecNetwork hdmiCecNetwork = localDevice().mService.getHdmiCecNetwork();
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(this.mTargetAddress);
        if (cecDeviceInfo == null) {
            return false;
        }
        hdmiCecNetwork.updateCecDevice(cecDeviceInfo.toBuilder().setDeviceFeatures(cecDeviceInfo.getDeviceFeatures().toBuilder().setSetAudioVolumeLevelSupport(i).build()).build());
        return true;
    }

    public int getTargetAddress() {
        return this.mTargetAddress;
    }
}
