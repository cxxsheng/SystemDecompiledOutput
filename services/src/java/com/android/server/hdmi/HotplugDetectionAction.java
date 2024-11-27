package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HotplugDetectionAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int AVR_COUNT_MAX = 3;
    private static final int NUM_OF_ADDRESS = 15;
    public static final int POLLING_INTERVAL_MS_FOR_PLAYBACK = 60000;
    public static final int POLLING_INTERVAL_MS_FOR_TV = 5000;
    private static final int STATE_WAIT_FOR_NEXT_POLLING = 1;
    private static final java.lang.String TAG = "HotPlugDetectionAction";
    public static final int TIMEOUT_COUNT = 3;
    private int mAvrStatusCount;
    private final boolean mIsTvDevice;
    private int mTimeoutCount;

    HotplugDetectionAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
        this.mTimeoutCount = 0;
        this.mAvrStatusCount = 0;
        this.mIsTvDevice = localDevice().mService.isTvDevice();
    }

    private int getPollingInterval() {
        return this.mIsTvDevice ? 5000 : 60000;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        android.util.Slog.v(TAG, "Hot-plug detection started.");
        this.mState = 1;
        this.mTimeoutCount = 0;
        addTimer(this.mState, getPollingInterval());
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == i && this.mState == 1) {
            if (this.mIsTvDevice) {
                this.mTimeoutCount = (this.mTimeoutCount + 1) % 3;
                if (this.mTimeoutCount == 0) {
                    pollAllDevices();
                } else if (tv().isSystemAudioActivated()) {
                    pollAudioSystem();
                }
                addTimer(this.mState, 5000);
                return;
            }
            pollAllDevices();
            addTimer(this.mState, 60000);
        }
    }

    void pollAllDevicesNow() {
        this.mActionTimer.clearTimerMessage();
        this.mTimeoutCount = 0;
        this.mState = 1;
        pollAllDevices();
        addTimer(this.mState, getPollingInterval());
    }

    private void pollAllDevices() {
        android.util.Slog.v(TAG, "Poll all devices.");
        pollDevices(new com.android.server.hdmi.HdmiControlService.DevicePollingCallback() { // from class: com.android.server.hdmi.HotplugDetectionAction.1
            @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
            public void onPollingFinished(java.util.List<java.lang.Integer> list) {
                com.android.server.hdmi.HotplugDetectionAction.this.checkHotplug(list, false);
            }
        }, 65537, 1);
    }

    private void pollAudioSystem() {
        android.util.Slog.v(TAG, "Poll audio system.");
        pollDevices(new com.android.server.hdmi.HdmiControlService.DevicePollingCallback() { // from class: com.android.server.hdmi.HotplugDetectionAction.2
            @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
            public void onPollingFinished(java.util.List<java.lang.Integer> list) {
                com.android.server.hdmi.HotplugDetectionAction.this.checkHotplug(list, true);
            }
        }, 65538, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkHotplug(java.util.List<java.lang.Integer> list, boolean z) {
        android.hardware.hdmi.HdmiDeviceInfo avrDeviceInfo;
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> deviceInfoList = localDevice().mService.getHdmiCecNetwork().getDeviceInfoList(false);
        java.util.BitSet infoListToBitSet = infoListToBitSet(deviceInfoList, z, false);
        java.util.BitSet addressListToBitSet = addressListToBitSet(list);
        java.util.BitSet complement = complement(infoListToBitSet, addressListToBitSet);
        int i = -1;
        while (true) {
            i = complement.nextSetBit(i + 1);
            if (i == -1) {
                break;
            }
            if (this.mIsTvDevice && i == 5 && (avrDeviceInfo = tv().getAvrDeviceInfo()) != null && tv().isConnected(avrDeviceInfo.getPortId())) {
                this.mAvrStatusCount++;
                android.util.Slog.w(TAG, "Ack not returned from AVR. count: " + this.mAvrStatusCount);
                if (this.mAvrStatusCount < 3) {
                }
            }
            android.util.Slog.v(TAG, "Remove device by hot-plug detection:" + i);
            removeDevice(i);
        }
        if (!complement.get(5)) {
            this.mAvrStatusCount = 0;
        }
        java.util.BitSet complement2 = complement(addressListToBitSet, infoListToBitSet(deviceInfoList, z, true));
        int i2 = -1;
        while (true) {
            i2 = complement2.nextSetBit(i2 + 1);
            if (i2 != -1) {
                android.util.Slog.v(TAG, "Add device by hot-plug detection:" + i2);
                addDevice(i2);
            } else {
                return;
            }
        }
    }

    private static java.util.BitSet infoListToBitSet(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list, boolean z, boolean z2) {
        java.util.BitSet bitSet = new java.util.BitSet(15);
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : list) {
            boolean z3 = true;
            boolean z4 = !z || hdmiDeviceInfo.getDeviceType() == 5;
            if (z2 && hdmiDeviceInfo.getPhysicalAddress() == 65535) {
                z3 = false;
            }
            if (z4 && z3) {
                bitSet.set(hdmiDeviceInfo.getLogicalAddress());
            }
        }
        return bitSet;
    }

    private static java.util.BitSet addressListToBitSet(java.util.List<java.lang.Integer> list) {
        java.util.BitSet bitSet = new java.util.BitSet(15);
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            bitSet.set(it.next().intValue());
        }
        return bitSet;
    }

    private static java.util.BitSet complement(java.util.BitSet bitSet, java.util.BitSet bitSet2) {
        java.util.BitSet bitSet3 = (java.util.BitSet) bitSet.clone();
        bitSet3.andNot(bitSet2);
        return bitSet3;
    }

    private void addDevice(int i) {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGivePhysicalAddress(getSourceAddress(), i));
    }

    private void removeDevice(int i) {
        if (this.mIsTvDevice) {
            mayChangeRoutingPath(i);
            mayCancelOneTouchRecord(i);
            mayDisableSystemAudioAndARC(i);
        }
        mayCancelDeviceSelect(i);
        localDevice().mService.getHdmiCecNetwork().removeCecDevice(localDevice(), i);
    }

    private void mayChangeRoutingPath(int i) {
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = localDevice().mService.getHdmiCecNetwork().getCecDeviceInfo(i);
        if (cecDeviceInfo != null) {
            tv().handleRemoveActiveRoutingPath(cecDeviceInfo.getPhysicalAddress());
        }
    }

    private void mayCancelDeviceSelect(int i) {
        java.util.Iterator it = getActions(com.android.server.hdmi.DeviceSelectActionFromTv.class).iterator();
        while (it.hasNext()) {
            if (((com.android.server.hdmi.DeviceSelectActionFromTv) it.next()).getTargetAddress() == i) {
                removeAction(com.android.server.hdmi.DeviceSelectActionFromTv.class);
            }
        }
        java.util.Iterator it2 = getActions(com.android.server.hdmi.DeviceSelectActionFromPlayback.class).iterator();
        while (it2.hasNext()) {
            if (((com.android.server.hdmi.DeviceSelectActionFromPlayback) it2.next()).getTargetAddress() == i) {
                removeAction(com.android.server.hdmi.DeviceSelectActionFromTv.class);
            }
        }
    }

    private void mayCancelOneTouchRecord(int i) {
        for (com.android.server.hdmi.OneTouchRecordAction oneTouchRecordAction : getActions(com.android.server.hdmi.OneTouchRecordAction.class)) {
            if (oneTouchRecordAction.getRecorderAddress() == i) {
                removeAction(oneTouchRecordAction);
            }
        }
    }

    private void mayDisableSystemAudioAndARC(int i) {
        if (!com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(5, i)) {
            return;
        }
        tv().setSystemAudioMode(false);
        if (tv().isArcEstablished()) {
            tv().enableAudioReturnChannel(false);
            addAndStartAction(new com.android.server.hdmi.RequestArcTerminationAction(localDevice(), i));
        }
    }
}
