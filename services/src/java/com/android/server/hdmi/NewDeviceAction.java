package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class NewDeviceAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    static final int STATE_WAITING_FOR_DEVICE_VENDOR_ID = 2;
    static final int STATE_WAITING_FOR_SET_OSD_NAME = 1;
    private static final java.lang.String TAG = "NewDeviceAction";
    private final int mDeviceLogicalAddress;
    private final int mDevicePhysicalAddress;
    private final int mDeviceType;
    private java.lang.String mDisplayName;
    private android.hardware.hdmi.HdmiDeviceInfo mOldDeviceInfo;
    private int mTimeoutRetry;
    private int mVendorId;

    NewDeviceAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, int i2, int i3) {
        super(hdmiCecLocalDevice);
        this.mDeviceLogicalAddress = i;
        this.mDevicePhysicalAddress = i2;
        this.mDeviceType = i3;
        this.mVendorId = android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean start() {
        this.mOldDeviceInfo = localDevice().mService.getHdmiCecNetwork().getCecDeviceInfo(this.mDeviceLogicalAddress);
        if (this.mOldDeviceInfo != null && this.mOldDeviceInfo.getPhysicalAddress() == this.mDevicePhysicalAddress) {
            android.util.Slog.d(TAG, "Start NewDeviceAction with old deviceInfo:[" + this.mOldDeviceInfo.toString() + "]");
        } else {
            android.util.Slog.d(TAG, "Start NewDeviceAction with default deviceInfo");
            android.hardware.hdmi.HdmiDeviceInfo build = android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(this.mDeviceLogicalAddress).setPhysicalAddress(this.mDevicePhysicalAddress).setPortId(tv().getPortId(this.mDevicePhysicalAddress)).setDeviceType(this.mDeviceType).setVendorId(android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK).build();
            if (this.mOldDeviceInfo != null) {
                android.util.Slog.d(TAG, "Remove device by NewDeviceAction, logical address conflicts: " + this.mDevicePhysicalAddress);
                localDevice().mService.getHdmiCecNetwork().removeCecDevice(localDevice(), this.mDeviceLogicalAddress);
            }
            localDevice().mService.getHdmiCecNetwork().addCecDevice(build);
        }
        requestOsdName(true);
        return true;
    }

    private void requestOsdName(boolean z) {
        if (z) {
            this.mTimeoutRetry = 0;
        }
        this.mState = 1;
        if (mayProcessCommandIfCached(this.mDeviceLogicalAddress, 71)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveOsdNameCommand(getSourceAddress(), this.mDeviceLogicalAddress));
        addTimer(this.mState, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int opcode = hdmiCecMessage.getOpcode();
        int source = hdmiCecMessage.getSource();
        byte[] params = hdmiCecMessage.getParams();
        if (this.mDeviceLogicalAddress != source) {
            return false;
        }
        if (this.mState == 1) {
            if (opcode == 71) {
                try {
                    this.mDisplayName = new java.lang.String(params, "US-ASCII");
                } catch (java.io.UnsupportedEncodingException e) {
                    android.util.Slog.e(TAG, "Failed to get OSD name: " + e.getMessage());
                }
                requestVendorId(true);
                return true;
            }
            if (opcode == 0 && (params[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 70) {
                requestVendorId(true);
                return true;
            }
        } else if (this.mState == 2) {
            if (opcode == 135) {
                this.mVendorId = com.android.server.hdmi.HdmiUtils.threeBytesToInt(params);
                addDeviceInfo();
                finish();
                return true;
            }
            if (opcode == 0 && (params[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 140) {
                addDeviceInfo();
                finish();
                return true;
            }
        }
        return false;
    }

    private boolean mayProcessCommandIfCached(int i, int i2) {
        com.android.server.hdmi.HdmiCecMessage message = getCecMessageCache().getMessage(i, i2);
        if (message != null) {
            return processCommand(message);
        }
        return false;
    }

    private void requestVendorId(boolean z) {
        if (z) {
            this.mTimeoutRetry = 0;
        }
        this.mState = 2;
        if (mayProcessCommandIfCached(this.mDeviceLogicalAddress, 135)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDeviceVendorIdCommand(getSourceAddress(), this.mDeviceLogicalAddress));
        addTimer(this.mState, 2000);
    }

    private void addDeviceInfo() {
        if (!localDevice().mService.getHdmiCecNetwork().isInDeviceList(this.mDeviceLogicalAddress, this.mDevicePhysicalAddress)) {
            android.util.Slog.w(TAG, java.lang.String.format("Device not found (%02x, %04x)", java.lang.Integer.valueOf(this.mDeviceLogicalAddress), java.lang.Integer.valueOf(this.mDevicePhysicalAddress)));
            return;
        }
        if (this.mDisplayName == null) {
            this.mDisplayName = "";
        }
        android.hardware.hdmi.HdmiDeviceInfo build = android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(this.mDeviceLogicalAddress).setPhysicalAddress(this.mDevicePhysicalAddress).setPortId(tv().getPortId(this.mDevicePhysicalAddress)).setDeviceType(this.mDeviceType).setVendorId(this.mVendorId).setDisplayName(this.mDisplayName).build();
        if (this.mOldDeviceInfo != null && this.mOldDeviceInfo.getLogicalAddress() == this.mDeviceLogicalAddress && this.mOldDeviceInfo.getPhysicalAddress() == this.mDevicePhysicalAddress && this.mOldDeviceInfo.getDeviceType() == this.mDeviceType && this.mOldDeviceInfo.getVendorId() == this.mVendorId && this.mOldDeviceInfo.getDisplayName().equals(this.mDisplayName)) {
            tv().processDelayedMessages(this.mDeviceLogicalAddress);
            android.util.Slog.d(TAG, "Ignore NewDevice, deviceInfo is same as current device");
            android.util.Slog.d(TAG, "Old:[" + this.mOldDeviceInfo.toString() + "]; New:[" + build.toString() + "]");
            return;
        }
        android.util.Slog.d(TAG, "Add NewDevice:[" + build.toString() + "]");
        localDevice().mService.getHdmiCecNetwork().addCecDevice(build);
        tv().processDelayedMessages(this.mDeviceLogicalAddress);
        if (com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(5, this.mDeviceLogicalAddress)) {
            tv().onNewAvrAdded(build);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public void handleTimerEvent(int i) {
        if (this.mState == 0 || this.mState != i) {
            return;
        }
        if (i == 1) {
            int i2 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i2;
            if (i2 < 5) {
                requestOsdName(false);
                return;
            } else {
                requestVendorId(true);
                return;
            }
        }
        if (i == 2) {
            int i3 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i3;
            if (i3 < 5) {
                requestVendorId(false);
            } else {
                addDeviceInfo();
                finish();
            }
        }
    }

    boolean isActionOf(com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource) {
        return this.mDeviceLogicalAddress == activeSource.logicalAddress && this.mDevicePhysicalAddress == activeSource.physicalAddress;
    }
}
