package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class ActiveSourceAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_FINISHED = 2;
    private static final int STATE_STARTED = 1;
    private final int mDestination;

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public /* bridge */ /* synthetic */ void addCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super.addCallback(iHdmiControlCallback);
    }

    ActiveSourceAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        super(hdmiCecLocalDevice);
        this.mDestination = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mState = 1;
        int sourceAddress = getSourceAddress();
        int sourcePath = getSourcePath();
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(sourceAddress, sourcePath));
        if (source().getType() == 4) {
            sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportMenuStatus(sourceAddress, this.mDestination, 0));
        }
        source().setActiveSource(sourceAddress, sourcePath, "ActiveSourceAction");
        this.mState = 2;
        finish();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
    }
}
