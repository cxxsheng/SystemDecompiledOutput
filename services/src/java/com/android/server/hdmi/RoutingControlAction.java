package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class RoutingControlAction extends com.android.server.hdmi.HdmiCecFeatureAction {

    @com.android.internal.annotations.VisibleForTesting
    static final int STATE_WAIT_FOR_ROUTING_INFORMATION = 1;
    private static final java.lang.String TAG = "RoutingControlAction";
    private static final int TIMEOUT_ROUTING_INFORMATION_MS = 1000;
    private int mCurrentRoutingPath;
    private final boolean mNotifyInputChange;

    RoutingControlAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mCurrentRoutingPath = i;
        this.mNotifyInputChange = iHdmiControlCallback == null;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        this.mState = 1;
        addTimer(this.mState, 1000);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int opcode = hdmiCecMessage.getOpcode();
        byte[] params = hdmiCecMessage.getParams();
        if (this.mState == 1 && opcode == 129) {
            int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(params);
            if (!com.android.server.hdmi.HdmiUtils.isInActiveRoutingPath(this.mCurrentRoutingPath, twoBytesToInt)) {
                return true;
            }
            this.mCurrentRoutingPath = twoBytesToInt;
            removeActionExcept(com.android.server.hdmi.RoutingControlAction.class, this);
            addTimer(this.mState, 1000);
            return true;
        }
        return false;
    }

    private void updateActiveInput() {
        com.android.server.hdmi.HdmiCecLocalDeviceTv tv = tv();
        tv.setPrevPortId(tv.getActivePortId());
        tv.updateActiveInput(this.mCurrentRoutingPath, this.mNotifyInputChange);
    }

    private void sendSetStreamPath() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetStreamPath(getSourceAddress(), this.mCurrentRoutingPath));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        if (this.mState != i || this.mState == 0) {
            android.util.Slog.w("CEC", "Timer in a wrong state. Ignored.");
            return;
        }
        switch (i) {
            case 1:
                updateActiveInput();
                sendSetStreamPath();
                finishWithCallback(0);
                break;
            default:
                android.util.Slog.e("CEC", "Invalid timeoutState (" + i + ").");
                break;
        }
    }
}
