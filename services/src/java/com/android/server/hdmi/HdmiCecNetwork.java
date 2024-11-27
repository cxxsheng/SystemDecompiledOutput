package com.android.server.hdmi;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class HdmiCecNetwork {
    private static final java.lang.String TAG = "HdmiCecNetwork";
    private final android.os.Handler mHandler;
    private final com.android.server.hdmi.HdmiCecController mHdmiCecController;
    private final com.android.server.hdmi.HdmiControlService mHdmiControlService;
    private final com.android.server.hdmi.HdmiMhlControllerStub mHdmiMhlController;
    protected final java.lang.Object mLock;
    private com.android.server.hdmi.UnmodifiableSparseArray<android.hardware.hdmi.HdmiDeviceInfo> mPortDeviceMap;
    private com.android.server.hdmi.UnmodifiableSparseIntArray mPortIdMap;
    private com.android.server.hdmi.UnmodifiableSparseArray<android.hardware.hdmi.HdmiPortInfo> mPortInfoMap;
    private final android.util.SparseArray<com.android.server.hdmi.HdmiCecLocalDevice> mLocalDevices = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.hardware.hdmi.HdmiDeviceInfo> mDeviceInfos = new android.util.SparseArray<>();
    private final android.util.ArraySet<java.lang.Integer> mCecSwitches = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.hardware.hdmi.HdmiDeviceInfo> mSafeAllDeviceInfos = java.util.Collections.emptyList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.hardware.hdmi.HdmiDeviceInfo> mSafeExternalInputs = java.util.Collections.emptyList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.hardware.hdmi.HdmiPortInfo> mPortInfo = java.util.Collections.emptyList();

    HdmiCecNetwork(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiCecController hdmiCecController, com.android.server.hdmi.HdmiMhlControllerStub hdmiMhlControllerStub) {
        this.mHdmiControlService = hdmiControlService;
        this.mHdmiCecController = hdmiCecController;
        this.mHdmiMhlController = hdmiMhlControllerStub;
        this.mHandler = new android.os.Handler(this.mHdmiControlService.getServiceLooper());
        this.mLock = this.mHdmiControlService.getServiceLock();
    }

    private static boolean isConnectedToCecSwitch(int i, java.util.Collection<java.lang.Integer> collection) {
        java.util.Iterator<java.lang.Integer> it = collection.iterator();
        while (it.hasNext()) {
            if (isParentPath(it.next().intValue(), i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isParentPath(int i, int i2) {
        for (int i3 = 0; i3 <= 12; i3 += 4) {
            if (((i2 >> i3) & 15) != 0) {
                if (((i >> i3) & 15) != 0) {
                    return false;
                }
                int i4 = i3 + 4;
                return (i2 >> i4) == (i >> i4);
            }
        }
        return false;
    }

    public void addLocalDevice(int i, com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        this.mLocalDevices.put(i, hdmiCecLocalDevice);
    }

    com.android.server.hdmi.HdmiCecLocalDevice getLocalDevice(int i) {
        return this.mLocalDevices.get(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    java.util.List<com.android.server.hdmi.HdmiCecLocalDevice> getLocalDeviceList() {
        assertRunOnServiceThread();
        return com.android.server.hdmi.HdmiUtils.sparseArrayToList(this.mLocalDevices);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isAllocatedLocalDeviceAddress(int i) {
        assertRunOnServiceThread();
        for (int i2 = 0; i2 < this.mLocalDevices.size(); i2++) {
            if (this.mLocalDevices.valueAt(i2).isAddressOf(i)) {
                return true;
            }
        }
        return false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void clearLocalDevices() {
        assertRunOnServiceThread();
        this.mLocalDevices.clear();
    }

    @android.annotation.Nullable
    public android.hardware.hdmi.HdmiDeviceInfo getDeviceInfo(int i) {
        return this.mDeviceInfos.get(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private android.hardware.hdmi.HdmiDeviceInfo addDeviceInfo(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(hdmiDeviceInfo.getLogicalAddress());
        this.mHdmiControlService.checkLogicalAddressConflictAndReallocate(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getPhysicalAddress());
        if (cecDeviceInfo != null) {
            removeDeviceInfo(hdmiDeviceInfo.getId());
        }
        this.mDeviceInfos.append(hdmiDeviceInfo.getId(), hdmiDeviceInfo);
        updateSafeDeviceInfoList();
        return cecDeviceInfo;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private android.hardware.hdmi.HdmiDeviceInfo removeDeviceInfo(int i) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = this.mDeviceInfos.get(i);
        if (hdmiDeviceInfo != null) {
            this.mDeviceInfos.remove(i);
        }
        updateSafeDeviceInfoList();
        return hdmiDeviceInfo;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @android.annotation.Nullable
    android.hardware.hdmi.HdmiDeviceInfo getCecDeviceInfo(int i) {
        assertRunOnServiceThread();
        return this.mDeviceInfos.get(android.hardware.hdmi.HdmiDeviceInfo.idForCecDevice(i));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final void addCecDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo addDeviceInfo = addDeviceInfo(hdmiDeviceInfo);
        if (isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())) {
            return;
        }
        this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
        if (hdmiDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        if (addDeviceInfo == null || addDeviceInfo.getPhysicalAddress() == 65535) {
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        } else if (!addDeviceInfo.equals(hdmiDeviceInfo)) {
            invokeDeviceEventListener(addDeviceInfo, 2);
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        }
    }

    private void invokeDeviceEventListener(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) {
        if (!hideDevicesBehindLegacySwitch(hdmiDeviceInfo)) {
            this.mHdmiControlService.invokeDeviceEventListeners(hdmiDeviceInfo, i);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final void updateCecDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo addDeviceInfo = addDeviceInfo(hdmiDeviceInfo);
        if (hdmiDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        if (addDeviceInfo == null || addDeviceInfo.getPhysicalAddress() == 65535) {
            invokeDeviceEventListener(hdmiDeviceInfo, 1);
        } else if (!addDeviceInfo.equals(hdmiDeviceInfo)) {
            invokeDeviceEventListener(hdmiDeviceInfo, 3);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void updateSafeDeviceInfoList() {
        assertRunOnServiceThread();
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> sparseArrayToList = com.android.server.hdmi.HdmiUtils.sparseArrayToList(this.mDeviceInfos);
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> inputDevices = getInputDevices();
        this.mSafeAllDeviceInfos = sparseArrayToList;
        this.mSafeExternalInputs = inputDevices;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceInfoList(boolean z) {
        assertRunOnServiceThread();
        if (z) {
            return com.android.server.hdmi.HdmiUtils.sparseArrayToList(this.mDeviceInfos);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mDeviceInfos.size(); i++) {
            android.hardware.hdmi.HdmiDeviceInfo valueAt = this.mDeviceInfos.valueAt(i);
            if (!isLocalDeviceAddress(valueAt.getLogicalAddress())) {
                arrayList.add(valueAt);
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getSafeExternalInputsLocked() {
        return this.mSafeExternalInputs;
    }

    private java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mDeviceInfos.size(); i++) {
            android.hardware.hdmi.HdmiDeviceInfo valueAt = this.mDeviceInfos.valueAt(i);
            if (!isLocalDeviceAddress(valueAt.getLogicalAddress()) && valueAt.isSourceType() && !hideDevicesBehindLegacySwitch(valueAt)) {
                arrayList.add(valueAt);
            }
        }
        return arrayList;
    }

    private boolean hideDevicesBehindLegacySwitch(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        return (!isLocalDeviceAddress(0) || isConnectedToCecSwitch(hdmiDeviceInfo.getPhysicalAddress(), getCecSwitches()) || hdmiDeviceInfo.getPhysicalAddress() == 65535) ? false : true;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final void removeCecDevice(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo removeDeviceInfo = removeDeviceInfo(android.hardware.hdmi.HdmiDeviceInfo.idForCecDevice(i));
        this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
        hdmiCecLocalDevice.mCecMessageCache.flushMessagesFrom(i);
        if (removeDeviceInfo.getPhysicalAddress() == 65535) {
            return;
        }
        invokeDeviceEventListener(removeDeviceInfo, 2);
    }

    public void updateDevicePowerStatus(int i, int i2) {
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(i);
        if (cecDeviceInfo == null) {
            android.util.Slog.w(TAG, "Can not update power status of non-existing device:" + i);
            return;
        }
        if (cecDeviceInfo.getDevicePowerStatus() == i2) {
            return;
        }
        updateCecDevice(cecDeviceInfo.toBuilder().setDevicePowerStatus(i2).build());
    }

    boolean isConnectedToArcPort(int i) {
        int physicalAddressToPortId = physicalAddressToPortId(i);
        if (physicalAddressToPortId != -1 && physicalAddressToPortId != 0) {
            return this.mPortInfoMap.get(physicalAddressToPortId).isArcSupported();
        }
        return false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    public void initPortInfo() {
        android.hardware.hdmi.HdmiPortInfo[] hdmiPortInfoArr;
        assertRunOnServiceThread();
        if (this.mHdmiCecController == null) {
            hdmiPortInfoArr = null;
        } else {
            hdmiPortInfoArr = this.mHdmiCecController.getPortInfos();
        }
        if (hdmiPortInfoArr == null) {
            return;
        }
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo : hdmiPortInfoArr) {
            sparseIntArray.put(hdmiPortInfo.getAddress(), hdmiPortInfo.getId());
            sparseArray.put(hdmiPortInfo.getId(), hdmiPortInfo);
            sparseArray2.put(hdmiPortInfo.getId(), android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(hdmiPortInfo.getAddress(), hdmiPortInfo.getId()));
        }
        this.mPortIdMap = new com.android.server.hdmi.UnmodifiableSparseIntArray(sparseIntArray);
        this.mPortInfoMap = new com.android.server.hdmi.UnmodifiableSparseArray<>(sparseArray);
        this.mPortDeviceMap = new com.android.server.hdmi.UnmodifiableSparseArray<>(sparseArray2);
        if (this.mHdmiMhlController == null) {
            return;
        }
        android.hardware.hdmi.HdmiPortInfo[] portInfos = this.mHdmiMhlController.getPortInfos();
        android.util.ArraySet arraySet = new android.util.ArraySet(portInfos.length);
        for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo2 : portInfos) {
            if (hdmiPortInfo2.isMhlSupported()) {
                arraySet.add(java.lang.Integer.valueOf(hdmiPortInfo2.getId()));
            }
        }
        if (arraySet.isEmpty()) {
            setPortInfo(java.util.Collections.unmodifiableList(java.util.Arrays.asList(hdmiPortInfoArr)));
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(hdmiPortInfoArr.length);
        for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo3 : hdmiPortInfoArr) {
            if (arraySet.contains(java.lang.Integer.valueOf(hdmiPortInfo3.getId()))) {
                arrayList.add(new android.hardware.hdmi.HdmiPortInfo.Builder(hdmiPortInfo3.getId(), hdmiPortInfo3.getType(), hdmiPortInfo3.getAddress()).setCecSupported(hdmiPortInfo3.isCecSupported()).setMhlSupported(true).setArcSupported(hdmiPortInfo3.isArcSupported()).setEarcSupported(hdmiPortInfo3.isEarcSupported()).build());
            } else {
                arrayList.add(hdmiPortInfo3);
            }
        }
        setPortInfo(java.util.Collections.unmodifiableList(arrayList));
    }

    android.hardware.hdmi.HdmiDeviceInfo getDeviceForPortId(int i) {
        return this.mPortDeviceMap.get(i, android.hardware.hdmi.HdmiDeviceInfo.INACTIVE_DEVICE);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isInDeviceList(int i, int i2) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(i);
        return cecDeviceInfo != null && cecDeviceInfo.getPhysicalAddress() == i2;
    }

    private static int logicalAddressToDeviceType(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 9:
                return 1;
            case 3:
            case 6:
            case 7:
            case 10:
                return 3;
            case 4:
            case 8:
            case 11:
                return 4;
            case 5:
                return 5;
            default:
                return 2;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void handleCecMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        if (getCecDeviceInfo(source) == null) {
            addCecDevice(android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(source).setDisplayName(com.android.server.hdmi.HdmiUtils.getDefaultDeviceName(source)).setDeviceType(logicalAddressToDeviceType(source)).build());
        }
        if (hdmiCecMessage instanceof com.android.server.hdmi.ReportFeaturesMessage) {
            handleReportFeatures((com.android.server.hdmi.ReportFeaturesMessage) hdmiCecMessage);
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                handleFeatureAbort(hdmiCecMessage);
                break;
            case 71:
                handleSetOsdName(hdmiCecMessage);
                break;
            case 132:
                handleReportPhysicalAddress(hdmiCecMessage);
                break;
            case 135:
                handleDeviceVendorId(hdmiCecMessage);
                break;
            case 144:
                handleReportPowerStatus(hdmiCecMessage);
                break;
            case 158:
                handleCecVersion(hdmiCecMessage);
                break;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleReportFeatures(com.android.server.hdmi.ReportFeaturesMessage reportFeaturesMessage) {
        assertRunOnServiceThread();
        updateCecDevice(getCecDeviceInfo(reportFeaturesMessage.getSource()).toBuilder().setCecVersion(reportFeaturesMessage.getCecVersion()).updateDeviceFeatures(reportFeaturesMessage.getDeviceFeatures()).build());
        this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleFeatureAbort(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (hdmiCecMessage.getParams().length < 2) {
            return;
        }
        int i = hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        int i2 = hdmiCecMessage.getParams()[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        if (i == 115) {
            int i3 = i2 == 0 ? 0 : 2;
            android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(hdmiCecMessage.getSource());
            updateCecDevice(cecDeviceInfo.toBuilder().updateDeviceFeatures(cecDeviceInfo.getDeviceFeatures().toBuilder().setSetAudioVolumeLevelSupport(i3).build()).build());
            this.mHdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleCecVersion(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        updateDeviceCecVersion(hdmiCecMessage.getSource(), java.lang.Byte.toUnsignedInt(hdmiCecMessage.getParams()[0]));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleReportPhysicalAddress(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
        byte b = hdmiCecMessage.getParams()[2];
        if (updateCecSwitchInfo(source, b, twoBytesToInt)) {
            return;
        }
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(source);
        if (cecDeviceInfo == null) {
            android.util.Slog.i(TAG, "Unknown source device info for <Report Physical Address> " + hdmiCecMessage);
            return;
        }
        updateCecDevice(cecDeviceInfo.toBuilder().setPhysicalAddress(twoBytesToInt).setPortId(physicalAddressToPortId(twoBytesToInt)).setDeviceType(b).build());
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleReportPowerStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        updateDevicePowerStatus(hdmiCecMessage.getSource(), hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE);
        if (hdmiCecMessage.getDestination() == 15) {
            updateDeviceCecVersion(hdmiCecMessage.getSource(), 6);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void updateDeviceCecVersion(int i, int i2) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(i);
        if (cecDeviceInfo == null) {
            android.util.Slog.w(TAG, "Can not update CEC version of non-existing device:" + i);
            return;
        }
        if (cecDeviceInfo.getCecVersion() == i2) {
            return;
        }
        updateCecDevice(cecDeviceInfo.toBuilder().setCecVersion(i2).build());
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleSetOsdName(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(hdmiCecMessage.getSource());
        if (cecDeviceInfo == null) {
            android.util.Slog.i(TAG, "No source device info for <Set Osd Name>." + hdmiCecMessage);
            return;
        }
        try {
            java.lang.String str = new java.lang.String(hdmiCecMessage.getParams(), "US-ASCII");
            if (cecDeviceInfo.getDisplayName() != null && cecDeviceInfo.getDisplayName().equals(str)) {
                android.util.Slog.d(TAG, "Ignore incoming <Set Osd Name> having same osd name:" + hdmiCecMessage);
                return;
            }
            android.util.Slog.d(TAG, "Updating device OSD name from " + cecDeviceInfo.getDisplayName() + " to " + str);
            updateCecDevice(cecDeviceInfo.toBuilder().setDisplayName(str).build());
        } catch (java.io.UnsupportedEncodingException e) {
            android.util.Slog.e(TAG, "Invalid <Set Osd Name> request:" + hdmiCecMessage, e);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void handleDeviceVendorId(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        int threeBytesToInt = com.android.server.hdmi.HdmiUtils.threeBytesToInt(hdmiCecMessage.getParams());
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = getCecDeviceInfo(source);
        if (cecDeviceInfo == null) {
            android.util.Slog.i(TAG, "Unknown source device info for <Device Vendor ID> " + hdmiCecMessage);
            return;
        }
        updateCecDevice(cecDeviceInfo.toBuilder().setVendorId(threeBytesToInt).build());
    }

    void addCecSwitch(int i) {
        this.mCecSwitches.add(java.lang.Integer.valueOf(i));
    }

    public android.util.ArraySet<java.lang.Integer> getCecSwitches() {
        return this.mCecSwitches;
    }

    void removeCecSwitches(int i) {
        java.util.Iterator<java.lang.Integer> it = this.mCecSwitches.iterator();
        while (it.hasNext()) {
            int physicalAddressToPortId = physicalAddressToPortId(it.next().intValue());
            if (physicalAddressToPortId == i || physicalAddressToPortId == -1) {
                it.remove();
            }
        }
    }

    void removeDevicesConnectedToPort(int i) {
        removeCecSwitches(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < this.mDeviceInfos.size(); i2++) {
            int keyAt = this.mDeviceInfos.keyAt(i2);
            int physicalAddressToPortId = physicalAddressToPortId(this.mDeviceInfos.get(keyAt).getPhysicalAddress());
            if (physicalAddressToPortId == i || physicalAddressToPortId == -1) {
                arrayList.add(java.lang.Integer.valueOf(keyAt));
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            removeDeviceInfo(((java.lang.Integer) it.next()).intValue());
        }
    }

    boolean updateCecSwitchInfo(int i, int i2, int i3) {
        if (i == 15 && i2 == 6) {
            this.mCecSwitches.add(java.lang.Integer.valueOf(i3));
            updateSafeDeviceInfoList();
            return true;
        }
        if (i2 == 5) {
            this.mCecSwitches.add(java.lang.Integer.valueOf(i3));
            return false;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getSafeCecDevicesLocked() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : this.mSafeAllDeviceInfos) {
            if (!isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())) {
                arrayList.add(hdmiDeviceInfo);
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    android.hardware.hdmi.HdmiDeviceInfo getSafeCecDeviceInfo(int i) {
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : this.mSafeAllDeviceInfos) {
            if (hdmiDeviceInfo.isCecDevice() && hdmiDeviceInfo.getLogicalAddress() == i) {
                return hdmiDeviceInfo;
            }
        }
        return null;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    final android.hardware.hdmi.HdmiDeviceInfo getDeviceInfoByPath(int i) {
        assertRunOnServiceThread();
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : getDeviceInfoList(false)) {
            if (hdmiDeviceInfo.getPhysicalAddress() == i) {
                return hdmiDeviceInfo;
            }
        }
        return null;
    }

    android.hardware.hdmi.HdmiDeviceInfo getSafeDeviceInfoByPath(int i) {
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : this.mSafeAllDeviceInfos) {
            if (hdmiDeviceInfo.getPhysicalAddress() == i) {
                return hdmiDeviceInfo;
            }
        }
        return null;
    }

    public int getPhysicalAddress() {
        return this.mHdmiCecController.getPhysicalAddress();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void clear() {
        assertRunOnServiceThread();
        initPortInfo();
        clearDeviceList();
        clearLocalDevices();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void removeUnusedLocalDevices(java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < this.mLocalDevices.size(); i++) {
            final int keyAt = this.mLocalDevices.keyAt(i);
            if (arrayList.stream().noneMatch(new java.util.function.Predicate() { // from class: com.android.server.hdmi.HdmiCecNetwork$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeUnusedLocalDevices$0;
                    lambda$removeUnusedLocalDevices$0 = com.android.server.hdmi.HdmiCecNetwork.lambda$removeUnusedLocalDevices$0(keyAt, (com.android.server.hdmi.HdmiCecLocalDevice) obj);
                    return lambda$removeUnusedLocalDevices$0;
                }
            })) {
                arrayList2.add(java.lang.Integer.valueOf(keyAt));
            }
        }
        java.util.Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            this.mLocalDevices.remove(((java.lang.Integer) it.next()).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeUnusedLocalDevices$0(int i, com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        return hdmiCecLocalDevice.getDeviceInfo() != null && hdmiCecLocalDevice.getDeviceInfo().getDeviceType() == i;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void removeLocalDeviceWithType(int i) {
        this.mLocalDevices.remove(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void clearDeviceList() {
        assertRunOnServiceThread();
        for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : com.android.server.hdmi.HdmiUtils.sparseArrayToList(this.mDeviceInfos)) {
            if (hdmiDeviceInfo.getPhysicalAddress() != getPhysicalAddress() && hdmiDeviceInfo.getPhysicalAddress() != 65535) {
                invokeDeviceEventListener(hdmiDeviceInfo, 2);
            }
        }
        this.mDeviceInfos.clear();
        updateSafeDeviceInfoList();
    }

    android.hardware.hdmi.HdmiPortInfo getPortInfo(int i) {
        return this.mPortInfoMap.get(i, null);
    }

    int portIdToPath(int i) {
        if (i == 0) {
            return getPhysicalAddress();
        }
        android.hardware.hdmi.HdmiPortInfo portInfo = getPortInfo(i);
        if (portInfo == null) {
            android.util.Slog.e(TAG, "Cannot find the port info: " + i);
            return com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL;
        }
        return portInfo.getAddress();
    }

    int physicalAddressToPortId(int i) {
        int physicalAddress = getPhysicalAddress();
        if (i == physicalAddress) {
            return 0;
        }
        int i2 = 61440;
        int i3 = physicalAddress;
        int i4 = 61440;
        while (i3 != 0) {
            i3 = physicalAddress & i4;
            i2 |= i4;
            i4 >>= 4;
        }
        return this.mPortIdMap.get(i & i2, -1);
    }

    java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
        return this.mPortInfo;
    }

    void setPortInfo(java.util.List<android.hardware.hdmi.HdmiPortInfo> list) {
        this.mPortInfo = list;
    }

    private boolean isLocalDeviceAddress(int i) {
        for (int i2 = 0; i2 < this.mLocalDevices.size(); i2++) {
            if (this.mLocalDevices.get(this.mLocalDevices.keyAt(i2)).getDeviceInfo().getLogicalAddress() == i) {
                return true;
            }
        }
        return false;
    }

    private void assertRunOnServiceThread() {
        if (android.os.Looper.myLooper() != this.mHandler.getLooper()) {
            throw new java.lang.IllegalStateException("Should run on service thread.");
        }
    }

    protected void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("HDMI CEC Network");
        indentingPrintWriter.increaseIndent();
        com.android.server.hdmi.HdmiUtils.dumpIterable(indentingPrintWriter, "mPortInfo:", this.mPortInfo);
        for (int i = 0; i < this.mLocalDevices.size(); i++) {
            indentingPrintWriter.println("HdmiCecLocalDevice #" + this.mLocalDevices.keyAt(i) + ":");
            indentingPrintWriter.increaseIndent();
            this.mLocalDevices.valueAt(i).dump(indentingPrintWriter);
            indentingPrintWriter.println("Active Source history:");
            indentingPrintWriter.increaseIndent();
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Iterator<com.android.server.hdmi.HdmiCecController.Dumpable> it = this.mLocalDevices.valueAt(i).getActiveSourceHistory().iterator();
            while (it.hasNext()) {
                it.next().dump(indentingPrintWriter, simpleDateFormat);
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
        com.android.server.hdmi.HdmiUtils.dumpIterable(indentingPrintWriter, "mDeviceInfos:", this.mSafeAllDeviceInfos);
        indentingPrintWriter.decreaseIndent();
    }
}
