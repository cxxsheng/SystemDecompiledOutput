package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class SystemAudioActionFromTv extends com.android.server.hdmi.SystemAudioAction {
    SystemAudioActionFromTv(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, i, z, iHdmiControlCallback);
        com.android.server.hdmi.HdmiUtils.verifyAddressType(getSourceAddress(), 0);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        removeSystemAudioActionInProgress();
        sendSystemAudioModeRequest();
        return true;
    }
}
