package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecLocalDeviceAudioSystem extends com.android.server.hdmi.HdmiCecLocalDeviceSource {
    private static final java.util.HashMap<java.lang.Integer, java.util.List<java.lang.Integer>> AUDIO_CODECS_MAP = mapAudioCodecWithAudioFormat();
    private static final int MAX_CHANNELS = 8;
    private static final java.lang.String SHORT_AUDIO_DESCRIPTOR_CONFIG_PATH = "/vendor/etc/sadConfig.xml";
    private static final java.lang.String TAG = "HdmiCecLocalDeviceAudioSystem";
    private static final boolean WAKE_ON_HOTPLUG = false;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mArcEstablished;
    private boolean mArcIntentUsed;
    private final com.android.server.hdmi.DelayedMessageBuffer mDelayedMessageBuffer;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<java.lang.Integer, java.lang.String> mPortIdToTvInputs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSystemAudioControlFeatureEnabled;
    private final android.media.tv.TvInputManager.TvInputCallback mTvInputCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<java.lang.String, android.hardware.hdmi.HdmiDeviceInfo> mTvInputsToDeviceInfo;
    private java.lang.Boolean mTvSystemAudioModeSupport;

    interface TvSystemAudioModeSupportedCallback {
        void onResult(boolean z);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public /* bridge */ /* synthetic */ java.util.concurrent.ArrayBlockingQueue getActiveSourceHistory() {
        return super.getActiveSourceHistory();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    public /* bridge */ /* synthetic */ void invokeStandbyCompletedCallback(com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        super.invokeStandbyCompletedCallback(standbyCompletedCallback);
    }

    protected HdmiCecLocalDeviceAudioSystem(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        super(hdmiControlService, 5);
        this.mTvSystemAudioModeSupport = null;
        this.mArcEstablished = false;
        this.mArcIntentUsed = ((java.lang.String) android.sysprop.HdmiProperties.arc_port().orElse("0")).contains("tvinput");
        this.mPortIdToTvInputs = new java.util.HashMap<>();
        this.mTvInputsToDeviceInfo = new java.util.HashMap<>();
        this.mDelayedMessageBuffer = new com.android.server.hdmi.DelayedMessageBuffer(this);
        this.mTvInputCallback = new android.media.tv.TvInputManager.TvInputCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.1
            @Override // android.media.tv.TvInputManager.TvInputCallback
            public void onInputAdded(java.lang.String str) {
                com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.addOrUpdateTvInput(str);
            }

            @Override // android.media.tv.TvInputManager.TvInputCallback
            public void onInputRemoved(java.lang.String str) {
                com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.removeTvInput(str);
            }

            @Override // android.media.tv.TvInputManager.TvInputCallback
            public void onInputUpdated(java.lang.String str) {
                com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.addOrUpdateTvInput(str);
            }
        };
        this.mRoutingControlFeatureEnabled = this.mService.getHdmiCecConfig().getIntValue("routing_control") == 1;
        this.mSystemAudioControlFeatureEnabled = this.mService.getHdmiCecConfig().getIntValue("system_audio_control") == 1;
        this.mStandbyHandler = new com.android.server.hdmi.HdmiCecStandbyModeHandler(hdmiControlService, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void addOrUpdateTvInput(java.lang.String str) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                android.media.tv.TvInputInfo tvInputInfo = this.mService.getTvInputManager().getTvInputInfo(str);
                if (tvInputInfo == null) {
                    return;
                }
                android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo();
                if (hdmiDeviceInfo == null) {
                    return;
                }
                this.mPortIdToTvInputs.put(java.lang.Integer.valueOf(hdmiDeviceInfo.getPortId()), str);
                this.mTvInputsToDeviceInfo.put(str, hdmiDeviceInfo);
                if (hdmiDeviceInfo.isCecDevice()) {
                    processDelayedActiveSource(hdmiDeviceInfo.getLogicalAddress());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void removeTvInput(java.lang.String str) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                if (this.mTvInputsToDeviceInfo.get(str) == null) {
                    return;
                }
                this.mPortIdToTvInputs.remove(java.lang.Integer.valueOf(this.mTvInputsToDeviceInfo.get(str).getPortId()));
                this.mTvInputsToDeviceInfo.remove(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected boolean isInputReady(int i) {
        assertRunOnServiceThread();
        return this.mTvInputsToDeviceInfo.get(this.mPortIdToTvInputs.get(java.lang.Integer.valueOf(i))) != null;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected android.hardware.hdmi.DeviceFeatures computeDeviceFeatures() {
        return android.hardware.hdmi.DeviceFeatures.NO_FEATURES_SUPPORTED.toBuilder().setArcRxSupport(android.os.SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true) ? 1 : 0).build();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        android.hardware.hdmi.HdmiPortInfo portInfo = this.mService.getPortInfo(i);
        if (portInfo != null && portInfo.getType() == 1) {
            this.mCecMessageCache.flushAll();
            if (!z) {
                if (isSystemAudioActivated()) {
                    this.mTvSystemAudioModeSupport = null;
                    checkSupportAndSetSystemAudioMode(false);
                }
                if (isArcEnabled()) {
                    setArcStatus(false);
                    return;
                }
                return;
            }
            return;
        }
        if (!z && this.mPortIdToTvInputs.get(java.lang.Integer.valueOf(i)) != null) {
            android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = this.mTvInputsToDeviceInfo.get(this.mPortIdToTvInputs.get(java.lang.Integer.valueOf(i)));
            if (hdmiDeviceInfo == null) {
                return;
            }
            this.mService.getHdmiCecNetwork().removeCecDevice(this, hdmiDeviceInfo.getLogicalAddress());
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void disableDevice(boolean z, com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        terminateAudioReturnChannel();
        super.disableDevice(z, pendingActionClearedCallback);
        assertRunOnServiceThread();
        this.mService.unregisterTvInputCallback(this.mTvInputCallback);
        removeAllActions();
        checkIfPendingActionsCleared();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onStandby(boolean z, int i, com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        assertRunOnServiceThread();
        this.mService.setActiveSource(-1, com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL, "HdmiCecLocalDeviceAudioSystem#onStandby()");
        this.mTvSystemAudioModeSupport = null;
        synchronized (this.mLock) {
            try {
                this.mService.writeStringSystemProperty("persist.sys.hdmi.last_system_audio_control", isSystemAudioActivated() ? "true" : "false");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        terminateSystemAudioMode(standbyCompletedCallback);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void onAddressAllocated(int i, int i2) {
        assertRunOnServiceThread();
        if (i2 == 0) {
            this.mService.setAndBroadcastActiveSource(this.mService.getPhysicalAddress(), getDeviceInfo().getDeviceType(), 15, "HdmiCecLocalDeviceAudioSystem#onAddressAllocated()");
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(getDeviceInfo().getLogicalAddress(), this.mService.getPhysicalAddress(), this.mDeviceType));
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildDeviceVendorIdCommand(getDeviceInfo().getLogicalAddress(), this.mService.getVendorId()));
        this.mService.registerTvInputCallback(this.mTvInputCallback);
        initArcOnFromAvr();
        if (!this.mService.isScreenOff()) {
            systemAudioControlOnPowerOn(android.os.SystemProperties.getInt("persist.sys.hdmi.system_audio_control_on_power_on", 0), android.os.SystemProperties.getBoolean("persist.sys.hdmi.last_system_audio_control", true));
        }
        this.mService.getHdmiCecNetwork().clearDeviceList();
        launchDeviceDiscovery();
        startQueuedActions();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected int findKeyReceiverAddress() {
        if (getActiveSource().isValid()) {
            return getActiveSource().logicalAddress;
        }
        return -1;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void systemAudioControlOnPowerOn(int i, boolean z) {
        if (i == 0 || (i == 1 && z && isSystemAudioControlFeatureEnabled())) {
            if (hasAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class)) {
                android.util.Slog.i(TAG, "SystemAudioInitiationActionFromAvr is in progress. Restarting.");
                removeAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class);
            }
            addAndStartAction(new com.android.server.hdmi.SystemAudioInitiationActionFromAvr(this));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int getPreferredAddress() {
        assertRunOnServiceThread();
        return android.os.SystemProperties.getInt("persist.sys.hdmi.addr.audiosystem", 15);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void setPreferredAddress(int i) {
        assertRunOnServiceThread();
        this.mService.writeStringSystemProperty("persist.sys.hdmi.addr.audiosystem", java.lang.String.valueOf(i));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void processDelayedActiveSource(int i) {
        assertRunOnServiceThread();
        this.mDelayedMessageBuffer.processActiveSource(i);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource, com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int source = hdmiCecMessage.getSource();
        if (com.android.server.hdmi.HdmiUtils.getLocalPortFromPhysicalAddress(com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams()), this.mService.getPhysicalAddress()) == -1) {
            return super.handleActiveSource(hdmiCecMessage);
        }
        android.hardware.hdmi.HdmiDeviceInfo cecDeviceInfo = this.mService.getHdmiCecNetwork().getCecDeviceInfo(source);
        if (cecDeviceInfo == null) {
            com.android.server.hdmi.HdmiLogger.debug("Device info %X not found; buffering the command", java.lang.Integer.valueOf(source));
            this.mDelayedMessageBuffer.add(hdmiCecMessage);
        } else if (!isInputReady(cecDeviceInfo.getPortId())) {
            com.android.server.hdmi.HdmiLogger.debug("Input not ready for device: %X; buffering the command", java.lang.Integer.valueOf(cecDeviceInfo.getId()));
            this.mDelayedMessageBuffer.add(hdmiCecMessage);
        } else {
            this.mDelayedMessageBuffer.removeActiveSource();
            return super.handleActiveSource(hdmiCecMessage);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleInitiateArc(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("HdmiCecLocalDeviceAudioSystemStub handleInitiateArc", new java.lang.Object[0]);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleReportArcInitiate(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleReportArcTermination(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        processArcTermination();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGiveAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (isSystemAudioControlFeatureEnabled() && this.mService.getHdmiCecVolumeControl() == 1) {
            reportAudioStatus(hdmiCecMessage.getSource());
            return -1;
        }
        return 4;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleGiveSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        boolean isSystemAudioActivated = isSystemAudioActivated();
        if (!isSystemAudioActivated && hdmiCecMessage.getSource() == 0 && hasAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class)) {
            isSystemAudioActivated = true;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportSystemAudioMode(getDeviceInfo().getLogicalAddress(), hdmiCecMessage.getSource(), isSystemAudioActivated));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestArcInitiate(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        removeAction(com.android.server.hdmi.ArcInitiationActionFromAvr.class);
        if (!this.mService.readBooleanSystemProperty("persist.sys.hdmi.property_arc_support", true)) {
            return 0;
        }
        if (!isDirectConnectToTv()) {
            com.android.server.hdmi.HdmiLogger.debug("AVR device is not directly connected with TV", new java.lang.Object[0]);
            return 1;
        }
        addAndStartAction(new com.android.server.hdmi.ArcInitiationActionFromAvr(this));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestArcTermination(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!android.os.SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true)) {
            return 0;
        }
        if (!isArcEnabled()) {
            com.android.server.hdmi.HdmiLogger.debug("ARC is not established between TV and AVR device", new java.lang.Object[0]);
            return 1;
        }
        if (!getActions(com.android.server.hdmi.ArcTerminationActionFromAvr.class).isEmpty() && !((com.android.server.hdmi.ArcTerminationActionFromAvr) getActions(com.android.server.hdmi.ArcTerminationActionFromAvr.class).get(0)).mCallbacks.isEmpty()) {
            android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback = ((com.android.server.hdmi.ArcTerminationActionFromAvr) getActions(com.android.server.hdmi.ArcTerminationActionFromAvr.class).get(0)).mCallbacks.get(0);
            removeAction(com.android.server.hdmi.ArcTerminationActionFromAvr.class);
            addAndStartAction(new com.android.server.hdmi.ArcTerminationActionFromAvr(this, iHdmiControlCallback));
            return -1;
        }
        removeAction(com.android.server.hdmi.ArcTerminationActionFromAvr.class);
        addAndStartAction(new com.android.server.hdmi.ArcTerminationActionFromAvr(this));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleRequestShortAudioDescriptor(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] supportedShortAudioDescriptors;
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("HdmiCecLocalDeviceAudioSystemStub handleRequestShortAudioDescriptor", new java.lang.Object[0]);
        if (!isSystemAudioControlFeatureEnabled()) {
            return 4;
        }
        if (!isSystemAudioActivated()) {
            return 1;
        }
        java.io.File file = new java.io.File(SHORT_AUDIO_DESCRIPTOR_CONFIG_PATH);
        java.util.List<com.android.server.hdmi.HdmiUtils.DeviceConfig> list = null;
        if (file.exists()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                list = com.android.server.hdmi.HdmiUtils.ShortAudioDescriptorXmlParser.parse(fileInputStream);
                fileInputStream.close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Error reading file: " + file, e);
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Slog.e(TAG, "Unable to parse file: " + file, e2);
            }
        }
        int[] parseAudioCodecs = parseAudioCodecs(hdmiCecMessage.getParams());
        if (list != null && list.size() > 0) {
            supportedShortAudioDescriptors = getSupportedShortAudioDescriptorsFromConfig(list, parseAudioCodecs);
        } else {
            android.media.AudioDeviceInfo systemAudioDeviceInfo = getSystemAudioDeviceInfo();
            if (systemAudioDeviceInfo == null) {
                return 5;
            }
            supportedShortAudioDescriptors = getSupportedShortAudioDescriptors(systemAudioDeviceInfo, parseAudioCodecs);
        }
        if (supportedShortAudioDescriptors.length == 0) {
            return 3;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportShortAudioDescriptor(getDeviceInfo().getLogicalAddress(), hdmiCecMessage.getSource(), supportedShortAudioDescriptors));
        return -1;
    }

    @com.android.internal.annotations.VisibleForTesting
    byte[] getSupportedShortAudioDescriptors(android.media.AudioDeviceInfo audioDeviceInfo, int[] iArr) {
        java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>(iArr.length);
        for (int i : iArr) {
            byte[] supportedShortAudioDescriptor = getSupportedShortAudioDescriptor(audioDeviceInfo, i);
            if (supportedShortAudioDescriptor != null) {
                if (supportedShortAudioDescriptor.length == 3) {
                    arrayList.add(supportedShortAudioDescriptor);
                } else {
                    com.android.server.hdmi.HdmiLogger.warning("Dropping Short Audio Descriptor with length %d for requested codec %x", java.lang.Integer.valueOf(supportedShortAudioDescriptor.length), java.lang.Integer.valueOf(i));
                }
            }
        }
        return getShortAudioDescriptorBytes(arrayList);
    }

    private byte[] getSupportedShortAudioDescriptorsFromConfig(java.util.List<com.android.server.hdmi.HdmiUtils.DeviceConfig> list, int[] iArr) {
        com.android.server.hdmi.HdmiUtils.DeviceConfig deviceConfig;
        byte[] bArr;
        java.lang.String str = android.os.SystemProperties.get("persist.sys.hdmi.property_sytem_audio_mode_audio_port", "VX_AUDIO_DEVICE_IN_HDMI_ARC");
        java.util.Iterator<com.android.server.hdmi.HdmiUtils.DeviceConfig> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                deviceConfig = null;
                break;
            }
            deviceConfig = it.next();
            if (deviceConfig.name.equals(str)) {
                break;
            }
        }
        if (deviceConfig == null) {
            android.util.Slog.w(TAG, "sadConfig.xml does not have required device info for " + str);
            return new byte[0];
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>(iArr.length);
        for (com.android.server.hdmi.HdmiUtils.CodecSad codecSad : deviceConfig.supportedCodecs) {
            hashMap.put(java.lang.Integer.valueOf(codecSad.audioCodec), codecSad.sad);
        }
        for (int i = 0; i < iArr.length; i++) {
            if (hashMap.containsKey(java.lang.Integer.valueOf(iArr[i])) && (bArr = (byte[]) hashMap.get(java.lang.Integer.valueOf(iArr[i]))) != null && bArr.length == 3) {
                arrayList.add(bArr);
            }
        }
        return getShortAudioDescriptorBytes(arrayList);
    }

    private byte[] getShortAudioDescriptorBytes(java.util.ArrayList<byte[]> arrayList) {
        byte[] bArr = new byte[arrayList.size() * 3];
        java.util.Iterator<byte[]> it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            java.lang.System.arraycopy(it.next(), 0, bArr, i, 3);
            i += 3;
        }
        return bArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    byte[] getSupportedShortAudioDescriptor(android.media.AudioDeviceInfo audioDeviceInfo, int i) {
        byte[] bArr = new byte[3];
        int[] encodings = audioDeviceInfo.getEncodings();
        if (!AUDIO_CODECS_MAP.containsKey(java.lang.Integer.valueOf(i)) || encodings.length == 0) {
            return null;
        }
        java.util.List<java.lang.Integer> list = AUDIO_CODECS_MAP.get(java.lang.Integer.valueOf(i));
        for (int i2 : encodings) {
            if (list.contains(java.lang.Integer.valueOf(i2))) {
                bArr[0] = getFirstByteOfSAD(audioDeviceInfo, i);
                bArr[1] = getSecondByteOfSAD(audioDeviceInfo);
                switch (i) {
                    case 0:
                        return null;
                    case 1:
                        if (i2 == 2) {
                            bArr[2] = 1;
                        } else if (i2 == 21) {
                            bArr[2] = 4;
                        } else {
                            bArr[2] = 0;
                        }
                        return bArr;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        bArr[2] = getThirdSadByteForCodecs2Through8(audioDeviceInfo);
                        return bArr;
                    case 8:
                    case 9:
                    default:
                        return null;
                    case 10:
                    case 11:
                    case 12:
                        bArr[2] = 0;
                        return bArr;
                }
            }
        }
        return null;
    }

    private static java.util.HashMap<java.lang.Integer, java.util.List<java.lang.Integer>> mapAudioCodecWithAudioFormat() {
        java.util.HashMap<java.lang.Integer, java.util.List<java.lang.Integer>> hashMap = new java.util.HashMap<>();
        hashMap.put(0, java.util.List.of(1));
        hashMap.put(1, java.util.List.of(3, 2, 4, 21, 22));
        hashMap.put(2, java.util.List.of(5));
        hashMap.put(3, java.util.List.of(11));
        hashMap.put(5, java.util.List.of(12));
        hashMap.put(4, java.util.List.of(9));
        hashMap.put(6, java.util.List.of(10));
        hashMap.put(7, java.util.List.of(7));
        hashMap.put(10, java.util.List.of(6, 18));
        hashMap.put(11, java.util.List.of(8));
        hashMap.put(12, java.util.List.of(14, 19));
        return hashMap;
    }

    private byte getFirstByteOfSAD(android.media.AudioDeviceInfo audioDeviceInfo, int i) {
        return (byte) (((byte) ((getMaxNumberOfChannels(audioDeviceInfo) - 1) | 0)) | (i << 3));
    }

    private byte getSecondByteOfSAD(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.ArrayList arrayList = new java.util.ArrayList(java.util.Arrays.asList(32, 44, 48, 88, 96, java.lang.Integer.valueOf(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT), 192));
        int[] sampleRates = audioDeviceInfo.getSampleRates();
        if (sampleRates.length == 0) {
            android.util.Slog.e(TAG, "Device supports arbitrary rates");
            return Byte.MAX_VALUE;
        }
        byte b = 0;
        for (int i : sampleRates) {
            if (arrayList.contains(java.lang.Integer.valueOf(i))) {
                b = (byte) (b | (1 << arrayList.indexOf(java.lang.Integer.valueOf(i))));
            }
        }
        return b;
    }

    private int getMaxNumberOfChannels(android.media.AudioDeviceInfo audioDeviceInfo) {
        int i;
        int[] channelCounts = audioDeviceInfo.getChannelCounts();
        if (channelCounts.length == 0 || (i = channelCounts[channelCounts.length - 1]) > 8) {
            return 8;
        }
        return i;
    }

    private byte getThirdSadByteForCodecs2Through8(android.media.AudioDeviceInfo audioDeviceInfo) {
        int i;
        int[] sampleRates = audioDeviceInfo.getSampleRates();
        if (sampleRates.length == 0) {
            i = 192;
        } else {
            int i2 = 0;
            for (int i3 : sampleRates) {
                if (i2 < i3) {
                    i2 = i3;
                }
            }
            i = i2;
        }
        return (byte) (i / 8);
    }

    @android.annotation.Nullable
    private android.media.AudioDeviceInfo getSystemAudioDeviceInfo() {
        android.media.AudioManager audioManager = (android.media.AudioManager) this.mService.getContext().getSystemService(android.media.AudioManager.class);
        if (audioManager == null) {
            com.android.server.hdmi.HdmiLogger.error("Error getting system audio device because AudioManager not available.", new java.lang.Object[0]);
            return null;
        }
        android.media.AudioDeviceInfo[] devices = audioManager.getDevices(1);
        com.android.server.hdmi.HdmiLogger.debug("Found %d audio input devices", java.lang.Integer.valueOf(devices.length));
        for (android.media.AudioDeviceInfo audioDeviceInfo : devices) {
            com.android.server.hdmi.HdmiLogger.debug("%s at port %s", audioDeviceInfo.getProductName(), audioDeviceInfo.getPort());
            com.android.server.hdmi.HdmiLogger.debug("Supported encodings are %s", java.util.Arrays.stream(audioDeviceInfo.getEncodings()).mapToObj(new java.util.function.IntFunction() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    return android.media.AudioFormat.toLogFriendlyEncoding(i);
                }
            }).collect(java.util.stream.Collectors.joining(", ")));
            if (audioDeviceInfo.getType() == 10) {
                return audioDeviceInfo;
            }
        }
        return null;
    }

    private int[] parseAudioCodecs(byte[] bArr) {
        int[] iArr = new int[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            if (b < 1 || b > 15) {
                b = 0;
            }
            iArr[i] = b;
        }
        return iArr;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSystemAudioModeRequest(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        boolean z = hdmiCecMessage.getParams().length != 0;
        if (hdmiCecMessage.getSource() != 0) {
            if (z) {
                return handleSystemAudioModeOnFromNonTvDevice(hdmiCecMessage);
            }
        } else {
            setTvSystemAudioModeSupport(true);
        }
        if (!checkSupportAndSetSystemAudioMode(z)) {
            return 4;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetSystemAudioMode(getDeviceInfo().getLogicalAddress(), 15, z));
        if (z) {
            int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(hdmiCecMessage.getParams());
            if (com.android.server.hdmi.HdmiUtils.getLocalPortFromPhysicalAddress(twoBytesToInt, getDeviceInfo().getPhysicalAddress()) == -1 && this.mService.getHdmiCecNetwork().getSafeDeviceInfoByPath(twoBytesToInt) == null) {
                switchInputOnReceivingNewActivePath(twoBytesToInt);
            }
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSetSystemAudioMode(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!checkSupportAndSetSystemAudioMode(com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))) {
            return 4;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected int handleSystemAudioModeStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!checkSupportAndSetSystemAudioMode(com.android.server.hdmi.HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))) {
            return 4;
        }
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setArcStatus(boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("Set Arc Status[old:%b new:%b]", java.lang.Boolean.valueOf(this.mArcEstablished), java.lang.Boolean.valueOf(z));
        enableAudioReturnChannel(z);
        notifyArcStatusToAudioService(z);
        this.mArcEstablished = z;
    }

    void processArcTermination() {
        setArcStatus(false);
        if (getLocalActivePort() == 17) {
            routeToInputFromPortId(getRoutingPort());
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void enableAudioReturnChannel(boolean z) {
        assertRunOnServiceThread();
        this.mService.enableAudioReturnChannel(java.lang.Integer.parseInt((java.lang.String) android.sysprop.HdmiProperties.arc_port().orElse("0")), z);
    }

    private void notifyArcStatusToAudioService(boolean z) {
        this.mService.getAudioManager().setWiredDeviceConnectionState(-2013265920, z ? 1 : 0, "", "");
    }

    void reportAudioStatus(int i) {
        assertRunOnServiceThread();
        if (this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        int streamVolume = this.mService.getAudioManager().getStreamVolume(3);
        boolean isStreamMute = this.mService.getAudioManager().isStreamMute(3);
        int streamMaxVolume = this.mService.getAudioManager().getStreamMaxVolume(3);
        int streamMinVolume = this.mService.getAudioManager().getStreamMinVolume(3);
        int scaleToCecVolume = com.android.server.hdmi.VolumeControlAction.scaleToCecVolume(streamVolume, streamMaxVolume);
        com.android.server.hdmi.HdmiLogger.debug("Reporting volume %d (%d-%d) as CEC volume %d", java.lang.Integer.valueOf(streamVolume), java.lang.Integer.valueOf(streamMinVolume), java.lang.Integer.valueOf(streamMaxVolume), java.lang.Integer.valueOf(scaleToCecVolume));
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildReportAudioStatus(getDeviceInfo().getLogicalAddress(), i, scaleToCecVolume, isStreamMute));
    }

    protected boolean checkSupportAndSetSystemAudioMode(boolean z) {
        if (!isSystemAudioControlFeatureEnabled()) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Cannot turn ");
            sb.append(z ? "on" : "off");
            sb.append("system audio mode because the System Audio Control feature is disabled.");
            com.android.server.hdmi.HdmiLogger.debug(sb.toString(), new java.lang.Object[0]);
            return false;
        }
        com.android.server.hdmi.HdmiLogger.debug("System Audio Mode change[old:%b new:%b]", java.lang.Boolean.valueOf(isSystemAudioActivated()), java.lang.Boolean.valueOf(z));
        if (z) {
            this.mService.wakeUp();
        }
        setSystemAudioMode(z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSystemAudioMode(boolean z) {
        int i;
        int pathToPortId = this.mService.pathToPortId(getActiveSource().physicalAddress);
        if (z && pathToPortId >= 0) {
            switchToAudioInput();
        }
        boolean z2 = this.mService.getHdmiCecConfig().getIntValue("system_audio_mode_muting") == 1;
        if (this.mService.getAudioManager().isStreamMute(3) == z && (z2 || z)) {
            com.android.server.hdmi.AudioManagerWrapper audioManager = this.mService.getAudioManager();
            if (z) {
                i = 100;
            } else {
                i = -100;
            }
            audioManager.adjustStreamVolume(3, i, 0);
        }
        updateAudioManagerForSystemAudio(z);
        synchronized (this.mLock) {
            try {
                if (isSystemAudioActivated() != z) {
                    this.mService.setSystemAudioActivated(z);
                    this.mService.announceSystemAudioModeChange(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (this.mArcIntentUsed && !z2 && !z && getLocalActivePort() == 17) {
            routeToInputFromPortId(getRoutingPort());
        }
        if (android.os.SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true) && isDirectConnectToTv() && this.mService.isSystemAudioActivated() && !hasAction(com.android.server.hdmi.ArcInitiationActionFromAvr.class)) {
            addAndStartAction(new com.android.server.hdmi.ArcInitiationActionFromAvr(this));
        }
    }

    protected void switchToAudioInput() {
    }

    protected boolean isDirectConnectToTv() {
        int physicalAddress = this.mService.getPhysicalAddress();
        return (61440 & physicalAddress) == physicalAddress;
    }

    private void updateAudioManagerForSystemAudio(boolean z) {
        com.android.server.hdmi.HdmiLogger.debug("[A]UpdateSystemAudio mode[on=%b] output=[%X]", java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(this.mService.getAudioManager().setHdmiSystemAudioSupported(z)));
    }

    void onSystemAudioControlFeatureSupportChanged(boolean z) {
        setSystemAudioControlFeatureEnabled(z);
        if (z) {
            if (hasAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class)) {
                android.util.Slog.i(TAG, "SystemAudioInitiationActionFromAvr is in progress. Restarting.");
                removeAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class);
            }
            addAndStartAction(new com.android.server.hdmi.SystemAudioInitiationActionFromAvr(this));
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setSystemAudioControlFeatureEnabled(boolean z) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mSystemAudioControlFeatureEnabled = z;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setRoutingControlFeatureEnabled(boolean z) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mRoutingControlFeatureEnabled = z;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void doManualPortSwitching(int i, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        int physicalAddress;
        assertRunOnServiceThread();
        if (!this.mService.isValidPortId(i)) {
            invokeCallback(iHdmiControlCallback, 3);
            return;
        }
        if (i == getLocalActivePort()) {
            invokeCallback(iHdmiControlCallback, 0);
            return;
        }
        if (!this.mService.isCecControlEnabled()) {
            setRoutingPort(i);
            setLocalActivePort(i);
            invokeCallback(iHdmiControlCallback, 6);
            return;
        }
        if (getRoutingPort() != 0) {
            physicalAddress = this.mService.portIdToPath(getRoutingPort());
        } else {
            physicalAddress = getDeviceInfo().getPhysicalAddress();
        }
        int portIdToPath = this.mService.portIdToPath(i);
        if (physicalAddress == portIdToPath) {
            return;
        }
        setRoutingPort(i);
        setLocalActivePort(i);
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRoutingChange(getDeviceInfo().getLogicalAddress(), physicalAddress, portIdToPath));
    }

    boolean isSystemAudioControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioControlFeatureEnabled;
        }
        return z;
    }

    protected boolean isSystemAudioActivated() {
        return this.mService.isSystemAudioActivated();
    }

    protected void terminateSystemAudioMode() {
        terminateSystemAudioMode(null);
    }

    protected void terminateSystemAudioMode(final com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        removeAction(com.android.server.hdmi.SystemAudioInitiationActionFromAvr.class);
        if (!isSystemAudioActivated()) {
            invokeStandbyCompletedCallback(standbyCompletedCallback);
        } else if (checkSupportAndSetSystemAudioMode(false)) {
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetSystemAudioMode(getDeviceInfo().getLogicalAddress(), 15, false), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.2
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public void onSendCompleted(int i) {
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.invokeStandbyCompletedCallback(standbyCompletedCallback);
                }
            });
        }
    }

    private void terminateAudioReturnChannel() {
        removeAction(com.android.server.hdmi.ArcInitiationActionFromAvr.class);
        if (!isArcEnabled() || !this.mService.readBooleanSystemProperty("persist.sys.hdmi.property_arc_support", true)) {
            return;
        }
        addAndStartAction(new com.android.server.hdmi.ArcTerminationActionFromAvr(this));
    }

    void queryTvSystemAudioModeSupport(com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback tvSystemAudioModeSupportedCallback) {
        if (this.mTvSystemAudioModeSupport == null) {
            addAndStartAction(new com.android.server.hdmi.DetectTvSystemAudioModeSupportAction(this, tvSystemAudioModeSupportedCallback));
        } else {
            tvSystemAudioModeSupportedCallback.onResult(this.mTvSystemAudioModeSupport.booleanValue());
        }
    }

    int handleSystemAudioModeOnFromNonTvDevice(final com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!isSystemAudioControlFeatureEnabled()) {
            com.android.server.hdmi.HdmiLogger.debug("Cannot turn onsystem audio mode because the System Audio Control feature is disabled.", new java.lang.Object[0]);
            return 4;
        }
        this.mService.wakeUp();
        if (this.mService.pathToPortId(getActiveSource().physicalAddress) != -1) {
            setSystemAudioMode(true);
            this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetSystemAudioMode(getDeviceInfo().getLogicalAddress(), 15, true));
            return -1;
        }
        queryTvSystemAudioModeSupport(new com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.3
            @Override // com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback
            public void onResult(boolean z) {
                if (z) {
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.setSystemAudioMode(true);
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetSystemAudioMode(com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.getDeviceInfo().getLogicalAddress(), 15, true));
                } else {
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.mService.maySendFeatureAbortCommand(hdmiCecMessage, 4);
                }
            }
        });
        return -1;
    }

    void setTvSystemAudioModeSupport(boolean z) {
        this.mTvSystemAudioModeSupport = java.lang.Boolean.valueOf(z);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isArcEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mArcEstablished;
        }
        return z;
    }

    private void initArcOnFromAvr() {
        removeAction(com.android.server.hdmi.ArcTerminationActionFromAvr.class);
        if (android.os.SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true) && isDirectConnectToTv() && !isArcEnabled()) {
            removeAction(com.android.server.hdmi.ArcInitiationActionFromAvr.class);
            addAndStartAction(new com.android.server.hdmi.ArcInitiationActionFromAvr(this));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    protected void switchInputOnReceivingNewActivePath(int i) {
        int pathToPortId = this.mService.pathToPortId(i);
        if (isSystemAudioActivated() && pathToPortId < 0) {
            routeToInputFromPortId(17);
        } else if (this.mIsSwitchDevice && pathToPortId >= 0) {
            routeToInputFromPortId(pathToPortId);
        }
    }

    protected void routeToInputFromPortId(int i) {
        if (!isRoutingControlFeatureEnabled()) {
            com.android.server.hdmi.HdmiLogger.debug("Routing Control Feature is not enabled.", new java.lang.Object[0]);
        } else if (this.mArcIntentUsed) {
            routeToTvInputFromPortId(i);
        }
    }

    protected void routeToTvInputFromPortId(int i) {
        if (i < 0 || i >= 21) {
            com.android.server.hdmi.HdmiLogger.debug("Invalid port number for Tv Input switching.", new java.lang.Object[0]);
            return;
        }
        this.mService.wakeUp();
        if (getLocalActivePort() == i && i != 17) {
            com.android.server.hdmi.HdmiLogger.debug("Not switching to the same port " + i + " except for arc", new java.lang.Object[0]);
            return;
        }
        if (i == 0 && this.mService.isPlaybackDevice()) {
            switchToHomeTvInput();
        } else if (i == 17) {
            switchToTvInput((java.lang.String) android.sysprop.HdmiProperties.arc_port().orElse("0"));
            setLocalActivePort(i);
            return;
        } else {
            java.lang.String str = this.mPortIdToTvInputs.get(java.lang.Integer.valueOf(i));
            if (str != null) {
                switchToTvInput(str);
            } else {
                com.android.server.hdmi.HdmiLogger.debug("Port number does not match any Tv Input.", new java.lang.Object[0]);
                return;
            }
        }
        setLocalActivePort(i);
        setRoutingPort(i);
    }

    private void switchToTvInput(java.lang.String str) {
        try {
            this.mService.getContext().startActivity(new android.content.Intent("android.intent.action.VIEW", android.media.tv.TvContract.buildChannelUriForPassthroughInput(str)).addFlags(268435456));
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(TAG, "Can't find activity to switch to " + str, e);
        }
    }

    private void switchToHomeTvInput() {
        try {
            this.mService.getContext().startActivity(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").setFlags(872480768));
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Slog.e(TAG, "Can't find activity to switch to HOME", e);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    protected void handleRoutingChangeAndInformation(int i, com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        int pathToPortId = this.mService.pathToPortId(i);
        if (pathToPortId > 0) {
            return;
        }
        if (pathToPortId < 0 && isSystemAudioActivated()) {
            handleRoutingChangeAndInformationForSystemAudio();
        } else if (pathToPortId == 0) {
            handleRoutingChangeAndInformationForSwitch(hdmiCecMessage);
        }
    }

    private void handleRoutingChangeAndInformationForSystemAudio() {
        routeToInputFromPortId(17);
    }

    private void handleRoutingChangeAndInformationForSwitch(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (getRoutingPort() == 0 && this.mService.isPlaybackDevice()) {
            routeToInputFromPortId(0);
            this.mService.setAndBroadcastActiveSourceFromOneDeviceType(hdmiCecMessage.getSource(), this.mService.getPhysicalAddress(), "HdmiCecLocalDeviceAudioSystem#handleRoutingChangeAndInformationForSwitch()");
            return;
        }
        int portIdToPath = this.mService.portIdToPath(getRoutingPort());
        if (portIdToPath == this.mService.getPhysicalAddress()) {
            com.android.server.hdmi.HdmiLogger.debug("Current device can't assign valid physical addressto devices under it any more. It's physical address is " + portIdToPath, new java.lang.Object[0]);
            return;
        }
        this.mService.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRoutingInformation(getDeviceInfo().getLogicalAddress(), portIdToPath));
        routeToInputFromPortId(getRoutingPort());
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void launchDeviceDiscovery() {
        assertRunOnServiceThread();
        if (this.mService.isDeviceDiscoveryHandledByPlayback()) {
            return;
        }
        if (hasAction(com.android.server.hdmi.DeviceDiscoveryAction.class)) {
            android.util.Slog.i(TAG, "Device Discovery Action is in progress. Restarting.");
            removeAction(com.android.server.hdmi.DeviceDiscoveryAction.class);
        }
        addAndStartAction(new com.android.server.hdmi.DeviceDiscoveryAction(this, new com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback() { // from class: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.4
            @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
            public void onDeviceDiscoveryDone(java.util.List<android.hardware.hdmi.HdmiDeviceInfo> list) {
                java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it = list.iterator();
                while (it.hasNext()) {
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.this.mService.getHdmiCecNetwork().addCecDevice(it.next());
                }
            }
        }));
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    protected void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("HdmiCecLocalDeviceAudioSystem:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("isRoutingFeatureEnabled " + isRoutingControlFeatureEnabled());
        indentingPrintWriter.println("mSystemAudioControlFeatureEnabled: " + this.mSystemAudioControlFeatureEnabled);
        indentingPrintWriter.println("mTvSystemAudioModeSupport: " + this.mTvSystemAudioModeSupport);
        indentingPrintWriter.println("mArcEstablished: " + this.mArcEstablished);
        indentingPrintWriter.println("mArcIntentUsed: " + this.mArcIntentUsed);
        indentingPrintWriter.println("mRoutingPort: " + getRoutingPort());
        indentingPrintWriter.println("mLocalActivePort: " + getLocalActivePort());
        com.android.server.hdmi.HdmiUtils.dumpMap(indentingPrintWriter, "mPortIdToTvInputs:", this.mPortIdToTvInputs);
        com.android.server.hdmi.HdmiUtils.dumpMap(indentingPrintWriter, "mTvInputsToDeviceInfo:", this.mTvInputsToDeviceInfo);
        indentingPrintWriter.decreaseIndent();
        super.dump(indentingPrintWriter);
    }
}
