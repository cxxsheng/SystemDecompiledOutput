package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class ActiveSourceHandler {
    private static final java.lang.String TAG = "ActiveSourceHandler";

    @android.annotation.Nullable
    private final android.hardware.hdmi.IHdmiControlCallback mCallback;
    private final com.android.server.hdmi.HdmiControlService mService;
    private final com.android.server.hdmi.HdmiCecLocalDeviceTv mSource;

    static com.android.server.hdmi.ActiveSourceHandler create(com.android.server.hdmi.HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        if (hdmiCecLocalDeviceTv == null) {
            android.util.Slog.e(TAG, "Wrong arguments");
            return null;
        }
        return new com.android.server.hdmi.ActiveSourceHandler(hdmiCecLocalDeviceTv, iHdmiControlCallback);
    }

    private ActiveSourceHandler(com.android.server.hdmi.HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        this.mSource = hdmiCecLocalDeviceTv;
        this.mService = this.mSource.getService();
        this.mCallback = iHdmiControlCallback;
    }

    void process(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource, int i) {
        com.android.server.hdmi.HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = this.mSource;
        if (this.mService.getDeviceInfo(activeSource.logicalAddress) == null) {
            hdmiCecLocalDeviceTv.startNewDeviceAction(activeSource, i);
        }
        if (!hdmiCecLocalDeviceTv.isProhibitMode()) {
            com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource of = com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource.of(hdmiCecLocalDeviceTv.getActiveSource());
            hdmiCecLocalDeviceTv.updateActiveSource(activeSource, TAG);
            boolean z = this.mCallback == null;
            if (!of.equals(activeSource)) {
                hdmiCecLocalDeviceTv.setPrevPortId(hdmiCecLocalDeviceTv.getActivePortId());
            }
            hdmiCecLocalDeviceTv.updateActiveInput(activeSource.physicalAddress, z);
            invokeCallback(0);
            return;
        }
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource2 = hdmiCecLocalDeviceTv.getActiveSource();
        if (activeSource2.logicalAddress == getSourceAddress()) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildActiveSource(activeSource2.logicalAddress, activeSource2.physicalAddress));
            hdmiCecLocalDeviceTv.updateActiveSource(activeSource2, TAG);
            invokeCallback(0);
            return;
        }
        hdmiCecLocalDeviceTv.startRoutingControl(activeSource.physicalAddress, activeSource2.physicalAddress, this.mCallback);
    }

    private final int getSourceAddress() {
        return this.mSource.getDeviceInfo().getLogicalAddress();
    }

    private void invokeCallback(int i) {
        if (this.mCallback == null) {
            return;
        }
        try {
            this.mCallback.onComplete(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Callback failed:" + e);
        }
    }
}
