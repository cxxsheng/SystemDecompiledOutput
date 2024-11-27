package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class SystemAudioActionFromAvr extends com.android.server.hdmi.SystemAudioAction {
    SystemAudioActionFromAvr(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, i, z, iHdmiControlCallback);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(getSourceAddress(), 0);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        removeSystemAudioActionInProgress();
        handleSystemAudioActionFromAvr();
        return true;
    }

    private void handleSystemAudioActionFromAvr() {
        if (this.mTargetAudioStatus == tv().isSystemAudioActivated()) {
            finishWithCallback(0);
            return;
        }
        if (tv().isProhibitMode()) {
            sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildFeatureAbortCommand(getSourceAddress(), this.mAvrLogicalAddress, 114, 4));
            this.mTargetAudioStatus = false;
            sendSystemAudioModeRequest();
            return;
        }
        removeAction(com.android.server.hdmi.SystemAudioAutoInitiationAction.class);
        if (this.mTargetAudioStatus) {
            setSystemAudioMode(true);
            finish();
        } else {
            setSystemAudioMode(false);
            finishWithCallback(0);
        }
    }
}
