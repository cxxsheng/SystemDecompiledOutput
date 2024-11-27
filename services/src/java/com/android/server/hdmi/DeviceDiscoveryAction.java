package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class DeviceDiscoveryAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_WAITING_FOR_DEVICES = 5;
    private static final int STATE_WAITING_FOR_DEVICE_POLLING = 1;
    private static final int STATE_WAITING_FOR_OSD_NAME = 3;
    private static final int STATE_WAITING_FOR_PHYSICAL_ADDRESS = 2;
    private static final int STATE_WAITING_FOR_POWER = 6;
    private static final int STATE_WAITING_FOR_VENDOR_ID = 4;
    private static final java.lang.String TAG = "DeviceDiscoveryAction";
    private final com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback mCallback;
    private final int mDelayPeriod;
    private final java.util.ArrayList<com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo> mDevices;
    private boolean mIsTvDevice;
    private int mProcessedDeviceCount;
    private int mTimeoutRetry;

    interface DeviceDiscoveryCallback {
        void onDeviceDiscoveryDone(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list);
    }

    private static final class DeviceInfo {
        private int mDeviceType;
        private java.lang.String mDisplayName;
        private final int mLogicalAddress;
        private int mPhysicalAddress;
        private int mPortId;
        private int mPowerStatus;
        private int mVendorId;

        private DeviceInfo(int i) {
            this.mPhysicalAddress = com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL;
            this.mPortId = -1;
            this.mVendorId = android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK;
            this.mPowerStatus = -1;
            this.mDisplayName = "";
            this.mDeviceType = -1;
            this.mLogicalAddress = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.hardware.hdmi.HdmiDeviceInfo toHdmiDeviceInfo() {
            return android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(this.mLogicalAddress).setPhysicalAddress(this.mPhysicalAddress).setPortId(this.mPortId).setVendorId(this.mVendorId).setDeviceType(this.mDeviceType).setDisplayName(this.mDisplayName).setDevicePowerStatus(this.mPowerStatus).build();
        }
    }

    DeviceDiscoveryAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback deviceDiscoveryCallback, int i) {
        super(hdmiCecLocalDevice);
        this.mDevices = new java.util.ArrayList<>();
        this.mProcessedDeviceCount = 0;
        this.mTimeoutRetry = 0;
        this.mIsTvDevice = localDevice().mService.isTvDevice();
        java.util.Objects.requireNonNull(deviceDiscoveryCallback);
        this.mCallback = deviceDiscoveryCallback;
        this.mDelayPeriod = i;
    }

    DeviceDiscoveryAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback deviceDiscoveryCallback) {
        this(hdmiCecLocalDevice, deviceDiscoveryCallback, 0);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mDevices.clear();
        this.mState = 1;
        pollDevices(new com.android.server.hdmi.HdmiControlService.DevicePollingCallback() { // from class: com.android.server.hdmi.DeviceDiscoveryAction.1
            @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
            public void onPollingFinished(java.util.List<java.lang.Integer> list) {
                if (list.isEmpty()) {
                    android.util.Slog.v(com.android.server.hdmi.DeviceDiscoveryAction.TAG, "No device is detected.");
                    com.android.server.hdmi.DeviceDiscoveryAction.this.wrapUpAndFinish();
                    return;
                }
                if (com.android.server.hdmi.DeviceDiscoveryAction.this.mState == 0) {
                    android.util.Slog.v(com.android.server.hdmi.DeviceDiscoveryAction.TAG, "Action was already finished before the callback was called.");
                    com.android.server.hdmi.DeviceDiscoveryAction.this.wrapUpAndFinish();
                    return;
                }
                android.util.Slog.v(com.android.server.hdmi.DeviceDiscoveryAction.TAG, "Device detected: " + list);
                com.android.server.hdmi.DeviceDiscoveryAction.this.allocateDevices(list);
                if (com.android.server.hdmi.DeviceDiscoveryAction.this.mDelayPeriod > 0) {
                    com.android.server.hdmi.DeviceDiscoveryAction.this.startToDelayAction();
                } else {
                    com.android.server.hdmi.DeviceDiscoveryAction.this.startPhysicalAddressStage();
                }
            }
        }, 131073, 1);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void allocateDevices(java.util.List<java.lang.Integer> list) {
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            this.mDevices.add(new com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo(it.next().intValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startToDelayAction() {
        android.util.Slog.v(TAG, "Waiting for connected devices to be ready");
        this.mState = 5;
        checkAndProceedStage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPhysicalAddressStage() {
        android.util.Slog.v(TAG, "Start [Physical Address Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 2;
        checkAndProceedStage();
    }

    private boolean verifyValidLogicalAddress(int i) {
        return i >= 0 && i < 15;
    }

    private void queryPhysicalAddress(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 132)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGivePhysicalAddress(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    private void delayActionWithTimePeriod(int i) {
        this.mActionTimer.clearTimerMessage();
        addTimer(this.mState, i);
    }

    private void startOsdNameStage() {
        android.util.Slog.v(TAG, "Start [Osd Name Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 3;
        checkAndProceedStage();
    }

    private void queryOsdName(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 71)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveOsdNameCommand(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    private void startVendorIdStage() {
        android.util.Slog.v(TAG, "Start [Vendor Id Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 4;
        checkAndProceedStage();
    }

    private void queryVendorId(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 135)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDeviceVendorIdCommand(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    private void startPowerStatusStage() {
        android.util.Slog.v(TAG, "Start [Power Status Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 6;
        checkAndProceedStage();
    }

    private void queryPowerStatus(int i) {
        if (!verifyValidLogicalAddress(i)) {
            checkAndProceedStage();
            return;
        }
        this.mActionTimer.clearTimerMessage();
        if (mayProcessMessageIfCached(i, 144)) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveDevicePowerStatus(getSourceAddress(), i));
        addTimer(this.mState, 2000);
    }

    private boolean mayProcessMessageIfCached(int i, int i2) {
        com.android.server.hdmi.HdmiCecMessage message = getCecMessageCache().getMessage(i, i2);
        if (message != null) {
            processCommand(message);
            return true;
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        switch (this.mState) {
            case 2:
                if (hdmiCecMessage.getOpcode() != 132) {
                    return false;
                }
                handleReportPhysicalAddress(hdmiCecMessage);
                return true;
            case 3:
                if (hdmiCecMessage.getOpcode() == 71) {
                    handleSetOsdName(hdmiCecMessage);
                    return true;
                }
                if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 70) {
                    return false;
                }
                handleSetOsdName(hdmiCecMessage);
                return true;
            case 4:
                if (hdmiCecMessage.getOpcode() == 135) {
                    handleVendorId(hdmiCecMessage);
                    return true;
                }
                if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 140) {
                    return false;
                }
                handleVendorId(hdmiCecMessage);
                return true;
            case 5:
            default:
                return false;
            case 6:
                if (hdmiCecMessage.getOpcode() == 144) {
                    handleReportPowerStatus(hdmiCecMessage);
                    return true;
                }
                if (hdmiCecMessage.getOpcode() != 0 || (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 144) {
                    return false;
                }
                handleReportPowerStatus(hdmiCecMessage);
                return true;
        }
    }

    private void handleReportPhysicalAddress(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.internal.util.Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo deviceInfo = this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            android.util.Slog.w(TAG, "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        byte[] params = hdmiCecMessage.getParams();
        deviceInfo.mPhysicalAddress = com.android.server.hdmi.HdmiUtils.twoBytesToInt(params);
        deviceInfo.mPortId = getPortId(deviceInfo.mPhysicalAddress);
        deviceInfo.mDeviceType = params[2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        deviceInfo.mDisplayName = "";
        if (this.mIsTvDevice) {
            localDevice().mService.getHdmiCecNetwork().updateCecSwitchInfo(deviceInfo.mLogicalAddress, deviceInfo.mDeviceType, deviceInfo.mPhysicalAddress);
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    private int getPortId(int i) {
        return this.mIsTvDevice ? tv().getPortId(i) : source().getPortId(i);
    }

    private void handleSetOsdName(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.internal.util.Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo deviceInfo = this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            android.util.Slog.w(TAG, "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        java.lang.String str = "";
        try {
            if (hdmiCecMessage.getOpcode() != 0) {
                str = new java.lang.String(hdmiCecMessage.getParams(), "US-ASCII");
            }
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Slog.w(TAG, "Failed to decode display name: " + hdmiCecMessage.toString());
        }
        deviceInfo.mDisplayName = str;
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    private void handleVendorId(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.internal.util.Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo deviceInfo = this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            android.util.Slog.w(TAG, "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        if (hdmiCecMessage.getOpcode() != 0) {
            deviceInfo.mVendorId = com.android.server.hdmi.HdmiUtils.threeBytesToInt(hdmiCecMessage.getParams());
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    private void handleReportPowerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.internal.util.Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo deviceInfo = this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.getSource()) {
            android.util.Slog.w(TAG, "Unmatched address[expected:" + deviceInfo.mLogicalAddress + ", actual:" + hdmiCecMessage.getSource());
            return;
        }
        if (hdmiCecMessage.getOpcode() != 0) {
            deviceInfo.mPowerStatus = hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    private void increaseProcessedDeviceCount() {
        this.mProcessedDeviceCount++;
        this.mTimeoutRetry = 0;
    }

    private void removeDevice(int i) {
        this.mDevices.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wrapUpAndFinish() {
        android.util.Slog.v(TAG, "---------Wrap up Device Discovery:[" + this.mDevices.size() + "]---------");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.hdmi.DeviceDiscoveryAction.DeviceInfo> it = this.mDevices.iterator();
        while (it.hasNext()) {
            android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = it.next().toHdmiDeviceInfo();
            android.util.Slog.v(TAG, " DeviceInfo: " + hdmiDeviceInfo);
            arrayList.add(hdmiDeviceInfo);
        }
        android.util.Slog.v(TAG, "--------------------------------------------");
        this.mCallback.onDeviceDiscoveryDone(arrayList);
        finish();
        if (this.mIsTvDevice) {
            tv().processAllDelayedMessages();
        }
    }

    private void checkAndProceedStage() {
        if (this.mDevices.isEmpty()) {
            wrapUpAndFinish();
            return;
        }
        if (this.mProcessedDeviceCount == this.mDevices.size()) {
            this.mProcessedDeviceCount = 0;
            switch (this.mState) {
                case 2:
                    startOsdNameStage();
                    break;
                case 3:
                    startVendorIdStage();
                    break;
                case 4:
                    startPowerStatusStage();
                    break;
                case 6:
                    wrapUpAndFinish();
                    break;
            }
            return;
        }
        sendQueryCommand();
    }

    private void sendQueryCommand() {
        int i = this.mDevices.get(this.mProcessedDeviceCount).mLogicalAddress;
        switch (this.mState) {
            case 2:
                queryPhysicalAddress(i);
                break;
            case 3:
                queryOsdName(i);
                break;
            case 4:
                queryVendorId(i);
                break;
            case 5:
                delayActionWithTimePeriod(this.mDelayPeriod);
                break;
            case 6:
                queryPowerStatus(i);
                break;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == 0 || this.mState != i) {
            return;
        }
        if (this.mState == 5) {
            startPhysicalAddressStage();
            return;
        }
        int i2 = this.mTimeoutRetry + 1;
        this.mTimeoutRetry = i2;
        if (i2 < 5) {
            sendQueryCommand();
            return;
        }
        this.mTimeoutRetry = 0;
        android.util.Slog.v(TAG, "Timeout[State=" + this.mState + ", Processed=" + this.mProcessedDeviceCount);
        if (this.mState != 6 && this.mState != 3) {
            removeDevice(this.mProcessedDeviceCount);
        } else {
            increaseProcessedDeviceCount();
        }
        checkAndProceedStage();
    }
}
