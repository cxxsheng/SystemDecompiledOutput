package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiCecController {
    private static final int ACTION_ON_RECEIVE_MSG = 2;
    private static final int CEC_DISABLED_DROP_MSG = 4;
    private static final int CEC_DISABLED_IGNORE = 1;
    private static final int CEC_DISABLED_LOG_WARNING = 2;
    private static final byte[] EMPTY_BODY = libcore.util.EmptyArray.BYTE;
    protected static final int HDMI_CEC_HAL_DEATH_COOKIE = 353;
    private static final int INITIAL_HDMI_MESSAGE_HISTORY_SIZE = 250;
    private static final int INVALID_PHYSICAL_ADDRESS = 65535;
    private static final int MAX_DEDICATED_ADDRESS = 11;
    private static final int NUM_LOGICAL_ADDRESS = 16;
    private static final java.lang.String TAG = "HdmiCecController";
    private android.os.Handler mControlHandler;
    private final com.android.server.hdmi.HdmiCecAtomWriter mHdmiCecAtomWriter;
    private android.os.Handler mIoHandler;
    private final com.android.server.hdmi.HdmiCecController.NativeWrapper mNativeWrapperImpl;
    private final com.android.server.hdmi.HdmiControlService mService;
    private final java.util.function.Predicate<java.lang.Integer> mRemoteDeviceAddressPredicate = new java.util.function.Predicate<java.lang.Integer>() { // from class: com.android.server.hdmi.HdmiCecController.1
        @Override // java.util.function.Predicate
        public boolean test(java.lang.Integer num) {
            return !com.android.server.hdmi.HdmiCecController.this.mService.getHdmiCecNetwork().isAllocatedLocalDeviceAddress(num.intValue());
        }
    };
    private final java.util.function.Predicate<java.lang.Integer> mSystemAudioAddressPredicate = new java.util.function.Predicate<java.lang.Integer>() { // from class: com.android.server.hdmi.HdmiCecController.2
        @Override // java.util.function.Predicate
        public boolean test(java.lang.Integer num) {
            return com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(5, num.intValue());
        }
    };
    private java.util.concurrent.ArrayBlockingQueue<com.android.server.hdmi.HdmiCecController.Dumpable> mMessageHistory = new java.util.concurrent.ArrayBlockingQueue<>(250);
    private final java.lang.Object mMessageHistoryLock = new java.lang.Object();
    private long mLogicalAddressAllocationDelay = 0;
    private long mPollDevicesDelay = 0;

    interface AllocateAddressCallback {
        void onAllocated(int i, int i2);
    }

    protected interface NativeWrapper {
        void enableCec(boolean z);

        void enableSystemCecControl(boolean z);

        void enableWakeupByOtp(boolean z);

        int nativeAddLogicalAddress(int i);

        void nativeClearLogicalAddress();

        void nativeEnableAudioReturnChannel(int i, boolean z);

        int nativeGetHpdSignalType(int i);

        int nativeGetPhysicalAddress();

        android.hardware.hdmi.HdmiPortInfo[] nativeGetPortInfos();

        int nativeGetVendorId();

        int nativeGetVersion();

        java.lang.String nativeInit();

        boolean nativeIsConnected(int i);

        int nativeSendCecCommand(int i, int i2, byte[] bArr);

        void nativeSetHpdSignalType(int i, int i2);

        void nativeSetLanguage(java.lang.String str);

        void setCallback(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback);
    }

    private HdmiCecController(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiCecController.NativeWrapper nativeWrapper, com.android.server.hdmi.HdmiCecAtomWriter hdmiCecAtomWriter) {
        this.mService = hdmiControlService;
        this.mNativeWrapperImpl = nativeWrapper;
        this.mHdmiCecAtomWriter = hdmiCecAtomWriter;
    }

    static com.android.server.hdmi.HdmiCecController create(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiCecAtomWriter hdmiCecAtomWriter) {
        byte b = 0;
        byte b2 = 0;
        com.android.server.hdmi.HdmiCecController createWithNativeWrapper = createWithNativeWrapper(hdmiControlService, new com.android.server.hdmi.HdmiCecController.NativeWrapperImplAidl(), hdmiCecAtomWriter);
        if (createWithNativeWrapper != null) {
            return createWithNativeWrapper;
        }
        com.android.server.hdmi.HdmiLogger.warning("Unable to use CEC and HDMI Connection AIDL HALs", new java.lang.Object[0]);
        com.android.server.hdmi.HdmiCecController createWithNativeWrapper2 = createWithNativeWrapper(hdmiControlService, new com.android.server.hdmi.HdmiCecController.NativeWrapperImpl11(), hdmiCecAtomWriter);
        if (createWithNativeWrapper2 != null) {
            return createWithNativeWrapper2;
        }
        com.android.server.hdmi.HdmiLogger.warning("Unable to use cec@1.1", new java.lang.Object[0]);
        return createWithNativeWrapper(hdmiControlService, new com.android.server.hdmi.HdmiCecController.NativeWrapperImpl(), hdmiCecAtomWriter);
    }

    static com.android.server.hdmi.HdmiCecController createWithNativeWrapper(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiCecController.NativeWrapper nativeWrapper, com.android.server.hdmi.HdmiCecAtomWriter hdmiCecAtomWriter) {
        com.android.server.hdmi.HdmiCecController hdmiCecController = new com.android.server.hdmi.HdmiCecController(hdmiControlService, nativeWrapper, hdmiCecAtomWriter);
        if (nativeWrapper.nativeInit() == null) {
            com.android.server.hdmi.HdmiLogger.warning("Couldn't get tv.cec service.", new java.lang.Object[0]);
            return null;
        }
        hdmiCecController.init(nativeWrapper);
        return hdmiCecController;
    }

    private void init(com.android.server.hdmi.HdmiCecController.NativeWrapper nativeWrapper) {
        this.mIoHandler = new android.os.Handler(this.mService.getIoLooper());
        this.mControlHandler = new android.os.Handler(this.mService.getServiceLooper());
        nativeWrapper.setCallback(new com.android.server.hdmi.HdmiCecController.HdmiCecCallback());
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void allocateLogicalAddress(final int i, final int i2, final com.android.server.hdmi.HdmiCecController.AllocateAddressCallback allocateAddressCallback) {
        assertRunOnServiceThread();
        this.mIoHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.hdmi.HdmiCecController.this.handleAllocateLogicalAddress(i, i2, allocateAddressCallback);
            }
        }, this.mLogicalAddressAllocationDelay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.IoThreadOnly
    public void handleAllocateLogicalAddress(final int i, int i2, final com.android.server.hdmi.HdmiCecController.AllocateAddressCallback allocateAddressCallback) {
        final int i3;
        boolean z;
        assertRunOnIoThread();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(i, i2)) {
            arrayList.add(java.lang.Integer.valueOf(i2));
        }
        for (int i4 = 0; i4 < 16; i4++) {
            if (!arrayList.contains(java.lang.Integer.valueOf(i4)) && com.android.server.hdmi.HdmiUtils.isEligibleAddressForDevice(i, i4) && com.android.server.hdmi.HdmiUtils.isEligibleAddressForCecVersion(this.mService.getCecVersion(), i4)) {
                arrayList.add(java.lang.Integer.valueOf(i4));
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                i3 = 15;
                break;
            }
            java.lang.Integer num = (java.lang.Integer) it.next();
            int i5 = 0;
            while (true) {
                if (i5 >= 3) {
                    z = false;
                    break;
                }
                z = true;
                if (sendPollMessage(num.intValue(), num.intValue(), 1)) {
                    break;
                } else {
                    i5++;
                }
            }
            if (!z) {
                i3 = num.intValue();
                break;
            }
        }
        com.android.server.hdmi.HdmiLogger.debug("New logical address for device [%d]: [preferred:%d, assigned:%d]", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
        if (allocateAddressCallback != null) {
            runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.4
                @Override // java.lang.Runnable
                public void run() {
                    allocateAddressCallback.onAllocated(i, i3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] buildBody(int i, byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 1];
        bArr2[0] = (byte) i;
        java.lang.System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        return bArr2;
    }

    android.hardware.hdmi.HdmiPortInfo[] getPortInfos() {
        return this.mNativeWrapperImpl.nativeGetPortInfos();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int addLogicalAddress(int i) {
        assertRunOnServiceThread();
        if (com.android.server.hdmi.HdmiUtils.isValidAddress(i)) {
            return this.mNativeWrapperImpl.nativeAddLogicalAddress(i);
        }
        return 2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void clearLogicalAddress() {
        assertRunOnServiceThread();
        this.mNativeWrapperImpl.nativeClearLogicalAddress();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getPhysicalAddress() {
        assertRunOnServiceThread();
        return this.mNativeWrapperImpl.nativeGetPhysicalAddress();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getVersion() {
        assertRunOnServiceThread();
        return this.mNativeWrapperImpl.nativeGetVersion();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getVendorId() {
        assertRunOnServiceThread();
        return this.mNativeWrapperImpl.nativeGetVendorId();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableWakeupByOtp(boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("enableWakeupByOtp: %b", java.lang.Boolean.valueOf(z));
        this.mNativeWrapperImpl.enableWakeupByOtp(z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableCec(boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("enableCec: %b", java.lang.Boolean.valueOf(z));
        this.mNativeWrapperImpl.enableCec(z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableSystemCecControl(boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("enableSystemCecControl: %b", java.lang.Boolean.valueOf(z));
        this.mNativeWrapperImpl.enableSystemCecControl(z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setHpdSignalType(@com.android.server.hdmi.Constants.HpdSignalType int i, int i2) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("setHpdSignalType: portId %b, signal %b", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
        this.mNativeWrapperImpl.nativeSetHpdSignalType(i, i2);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.server.hdmi.Constants.HpdSignalType
    int getHpdSignalType(int i) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("getHpdSignalType: portId %b ", java.lang.Integer.valueOf(i));
        return this.mNativeWrapperImpl.nativeGetHpdSignalType(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setLanguage(java.lang.String str) {
        assertRunOnServiceThread();
        if (!isLanguage(str)) {
            return;
        }
        this.mNativeWrapperImpl.nativeSetLanguage(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLogicalAddressAllocationDelay(long j) {
        this.mLogicalAddressAllocationDelay = j;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPollDevicesDelay(long j) {
        this.mPollDevicesDelay = j;
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isLanguage(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            new android.icu.util.ULocale.Builder().setLanguage(str);
            return true;
        } catch (android.icu.util.IllformedLocaleException e) {
            return false;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void enableAudioReturnChannel(int i, boolean z) {
        assertRunOnServiceThread();
        this.mNativeWrapperImpl.nativeEnableAudioReturnChannel(i, z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isConnected(int i) {
        assertRunOnServiceThread();
        return this.mNativeWrapperImpl.nativeIsConnected(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void pollDevices(final com.android.server.hdmi.HdmiControlService.DevicePollingCallback devicePollingCallback, final int i, int i2, final int i3) {
        assertRunOnServiceThread();
        final java.util.List<java.lang.Integer> pickPollCandidates = pickPollCandidates(i2);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mControlHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.hdmi.HdmiCecController.this.lambda$pollDevices$0(i, pickPollCandidates, i3, devicePollingCallback, arrayList);
            }
        }, this.mPollDevicesDelay);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private java.util.List<java.lang.Integer> pickPollCandidates(int i) {
        java.util.function.Predicate<java.lang.Integer> predicate;
        switch (i & 3) {
            case 2:
                predicate = this.mSystemAudioAddressPredicate;
                break;
            default:
                predicate = this.mRemoteDeviceAddressPredicate;
                break;
        }
        int i2 = i & 196608;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        switch (i2) {
            case 65536:
                for (int i3 = 0; i3 <= 14; i3++) {
                    if (predicate.test(java.lang.Integer.valueOf(i3))) {
                        arrayList.add(java.lang.Integer.valueOf(i3));
                    }
                }
                return arrayList;
            default:
                for (int i4 = 14; i4 >= 0; i4--) {
                    if (predicate.test(java.lang.Integer.valueOf(i4))) {
                        arrayList.add(java.lang.Integer.valueOf(i4));
                    }
                }
                return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    /* renamed from: runDevicePolling, reason: merged with bridge method [inline-methods] */
    public void lambda$pollDevices$0(final int i, final java.util.List<java.lang.Integer> list, final int i2, final com.android.server.hdmi.HdmiControlService.DevicePollingCallback devicePollingCallback, final java.util.List<java.lang.Integer> list2) {
        assertRunOnServiceThread();
        if (list.isEmpty()) {
            if (devicePollingCallback != null) {
                com.android.server.hdmi.HdmiLogger.debug("[P]:AllocatedAddress=%s", list2.toString());
                devicePollingCallback.onPollingFinished(list2);
                return;
            }
            return;
        }
        final java.lang.Integer remove = list.remove(0);
        runOnIoThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.5
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.hdmi.HdmiCecController.this.sendPollMessage(i, remove.intValue(), i2)) {
                    list2.add(remove);
                }
                com.android.server.hdmi.HdmiCecController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.hdmi.HdmiCecController.this.lambda$pollDevices$0(i, list, i2, devicePollingCallback, list2);
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.IoThreadOnly
    public boolean sendPollMessage(int i, int i2, int i3) {
        assertRunOnIoThread();
        for (int i4 = 0; i4 < i3; i4++) {
            int nativeSendCecCommand = this.mNativeWrapperImpl.nativeSendCecCommand(i, i2, EMPTY_BODY);
            if (nativeSendCecCommand == 0) {
                return true;
            }
            if (nativeSendCecCommand != 1) {
                com.android.server.hdmi.HdmiLogger.warning("Failed to send a polling message(%d->%d) with return code %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(nativeSendCecCommand));
            }
        }
        return false;
    }

    private void assertRunOnIoThread() {
        if (android.os.Looper.myLooper() != this.mIoHandler.getLooper()) {
            throw new java.lang.IllegalStateException("Should run on io thread.");
        }
    }

    private void assertRunOnServiceThread() {
        if (android.os.Looper.myLooper() != this.mControlHandler.getLooper()) {
            throw new java.lang.IllegalStateException("Should run on service thread.");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void runOnIoThread(java.lang.Runnable runnable) {
        this.mIoHandler.post(new com.android.server.hdmi.WorkSourceUidPreservingRunnable(runnable));
    }

    @com.android.internal.annotations.VisibleForTesting
    void runOnServiceThread(java.lang.Runnable runnable) {
        this.mControlHandler.post(new com.android.server.hdmi.WorkSourceUidPreservingRunnable(runnable));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void flush(final java.lang.Runnable runnable) {
        assertRunOnServiceThread();
        runOnIoThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.6
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.hdmi.HdmiCecController.this.runOnServiceThread(runnable);
            }
        });
    }

    private boolean isAcceptableAddress(int i) {
        if (i == 15) {
            return true;
        }
        return this.mService.getHdmiCecNetwork().isAllocatedLocalDeviceAddress(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    void onReceiveCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled() && !com.android.server.hdmi.HdmiCecMessage.isCecTransportMessage(hdmiCecMessage.getOpcode())) {
            com.android.server.hdmi.HdmiLogger.warning("Message " + hdmiCecMessage + " received when cec disabled", new java.lang.Object[0]);
        }
        if (this.mService.isAddressAllocated() && !isAcceptableAddress(hdmiCecMessage.getDestination())) {
            return;
        }
        int handleCecCommand = this.mService.handleCecCommand(hdmiCecMessage);
        if (handleCecCommand == -2) {
            maySendFeatureAbortCommand(hdmiCecMessage, 0);
        } else if (handleCecCommand != -1) {
            maySendFeatureAbortCommand(hdmiCecMessage, handleCecCommand);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void maySendFeatureAbortCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i) {
        int opcode;
        assertRunOnServiceThread();
        int destination = hdmiCecMessage.getDestination();
        int source = hdmiCecMessage.getSource();
        if (destination == 15 || source == 15 || (opcode = hdmiCecMessage.getOpcode()) == 0) {
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildFeatureAbortCommand(destination, source, opcode, i));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void sendCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        sendCommand(hdmiCecMessage, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCallingUid() {
        int callingWorkSourceUid = android.os.Binder.getCallingWorkSourceUid();
        if (callingWorkSourceUid == -1) {
            return android.os.Binder.getCallingUid();
        }
        return callingWorkSourceUid;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void sendCommand(final com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, final com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        assertRunOnServiceThread();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        runOnIoThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.7
            @Override // java.lang.Runnable
            public void run() {
                final int nativeSendCecCommand;
                int i = 0;
                com.android.server.hdmi.HdmiLogger.debug("[S]:" + hdmiCecMessage, new java.lang.Object[0]);
                byte[] buildBody = com.android.server.hdmi.HdmiCecController.buildBody(hdmiCecMessage.getOpcode(), hdmiCecMessage.getParams());
                while (true) {
                    nativeSendCecCommand = com.android.server.hdmi.HdmiCecController.this.mNativeWrapperImpl.nativeSendCecCommand(hdmiCecMessage.getSource(), hdmiCecMessage.getDestination(), buildBody);
                    switch (nativeSendCecCommand) {
                        case 0:
                            arrayList.add("ACK");
                            break;
                        case 1:
                            arrayList.add("NACK");
                            break;
                        case 2:
                            arrayList.add("BUSY");
                            break;
                        case 3:
                            arrayList.add("FAIL");
                            break;
                    }
                    if (nativeSendCecCommand != 0) {
                        int i2 = i + 1;
                        if (i < 1) {
                            i = i2;
                        }
                    }
                }
                if (nativeSendCecCommand != 0) {
                    android.util.Slog.w(com.android.server.hdmi.HdmiCecController.TAG, "Failed to send " + hdmiCecMessage + " with errorCode=" + nativeSendCecCommand);
                }
                com.android.server.hdmi.HdmiCecController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.hdmi.HdmiCecController.this.mHdmiCecAtomWriter.messageReported(hdmiCecMessage, 2, com.android.server.hdmi.HdmiCecController.this.getCallingUid(), nativeSendCecCommand);
                        if (sendMessageCallback != null) {
                            sendMessageCallback.onSendCompleted(nativeSendCecCommand);
                        }
                    }
                });
            }
        });
        addCecMessageToHistory(false, hdmiCecMessage, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void handleIncomingCecCommand(int i, int i2, byte[] bArr) {
        assertRunOnServiceThread();
        if (bArr.length == 0) {
            android.util.Slog.e(TAG, "Message with empty body received.");
            return;
        }
        com.android.server.hdmi.HdmiCecMessage build = com.android.server.hdmi.HdmiCecMessage.build(i, i2, bArr[0], java.util.Arrays.copyOfRange(bArr, 1, bArr.length));
        if (build.getValidationResult() != 0) {
            android.util.Slog.e(TAG, "Invalid message received: " + build);
        }
        com.android.server.hdmi.HdmiLogger.debug("[R]:" + build, new java.lang.Object[0]);
        addCecMessageToHistory(true, build, null);
        this.mHdmiCecAtomWriter.messageReported(build, incomingMessageDirection(i, i2), getCallingUid());
        onReceiveCommand(build);
    }

    private int incomingMessageDirection(int i, int i2) {
        boolean z = false;
        boolean z2 = i2 == 15;
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mService.getHdmiCecNetwork().getLocalDeviceList().iterator();
        while (it.hasNext()) {
            int logicalAddress = it.next().getDeviceInfo().getLogicalAddress();
            if (logicalAddress == i) {
                z = true;
            }
            if (logicalAddress == i2) {
                z2 = true;
            }
        }
        if (z || !z2) {
            return (z && z2) ? 4 : 1;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void handleHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiLogger.debug("Hotplug event:[port:%d, connected:%b]", java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z));
        addHotplugEventToHistory(i, z);
        this.mService.onHotplug(i, z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void addHotplugEventToHistory(int i, boolean z) {
        assertRunOnServiceThread();
        addEventToHistory(new com.android.server.hdmi.HdmiCecController.HotplugHistoryRecord(i, z));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void addCecMessageToHistory(boolean z, com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, java.util.List<java.lang.String> list) {
        assertRunOnServiceThread();
        addEventToHistory(new com.android.server.hdmi.HdmiCecController.MessageHistoryRecord(z, hdmiCecMessage, list));
    }

    private void addEventToHistory(com.android.server.hdmi.HdmiCecController.Dumpable dumpable) {
        synchronized (this.mMessageHistoryLock) {
            try {
                if (!this.mMessageHistory.offer(dumpable)) {
                    this.mMessageHistory.poll();
                    this.mMessageHistory.offer(dumpable);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int getMessageHistorySize() {
        int size;
        synchronized (this.mMessageHistoryLock) {
            size = this.mMessageHistory.size() + this.mMessageHistory.remainingCapacity();
        }
        return size;
    }

    boolean setMessageHistorySize(int i) {
        if (i < 250) {
            return false;
        }
        java.util.concurrent.ArrayBlockingQueue<com.android.server.hdmi.HdmiCecController.Dumpable> arrayBlockingQueue = new java.util.concurrent.ArrayBlockingQueue<>(i);
        synchronized (this.mMessageHistoryLock) {
            try {
                if (i < this.mMessageHistory.size()) {
                    for (int i2 = 0; i2 < this.mMessageHistory.size() - i; i2++) {
                        this.mMessageHistory.poll();
                    }
                }
                arrayBlockingQueue.addAll(this.mMessageHistory);
                this.mMessageHistory = arrayBlockingQueue;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return true;
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("CEC message history:");
        indentingPrintWriter.increaseIndent();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Iterator<com.android.server.hdmi.HdmiCecController.Dumpable> it = this.mMessageHistory.iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter, simpleDateFormat);
        }
        indentingPrintWriter.decreaseIndent();
    }

    private static final class NativeWrapperImplAidl implements com.android.server.hdmi.HdmiCecController.NativeWrapper, android.os.IBinder.DeathRecipient {

        @android.annotation.Nullable
        private com.android.server.hdmi.HdmiCecController.HdmiCecCallback mCallback;
        private android.hardware.tv.hdmi.cec.IHdmiCec mHdmiCec;
        private android.hardware.tv.hdmi.connection.IHdmiConnection mHdmiConnection;
        private final java.lang.Object mLock;

        private NativeWrapperImplAidl() {
            this.mLock = new java.lang.Object();
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public java.lang.String nativeInit() {
            if (!connectToHal()) {
                return null;
            }
            return this.mHdmiCec.toString() + " " + this.mHdmiConnection.toString();
        }

        boolean connectToHal() {
            this.mHdmiCec = android.hardware.tv.hdmi.cec.IHdmiCec.Stub.asInterface(android.os.ServiceManager.getService(android.hardware.tv.hdmi.cec.IHdmiCec.DESCRIPTOR + "/default"));
            if (this.mHdmiCec == null) {
                com.android.server.hdmi.HdmiLogger.error("Could not initialize HDMI CEC AIDL HAL", new java.lang.Object[0]);
                return false;
            }
            try {
                this.mHdmiCec.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't link to death : ", e, new java.lang.Object[0]);
            }
            this.mHdmiConnection = android.hardware.tv.hdmi.connection.IHdmiConnection.Stub.asInterface(android.os.ServiceManager.getService(android.hardware.tv.hdmi.connection.IHdmiConnection.DESCRIPTOR + "/default"));
            if (this.mHdmiConnection == null) {
                com.android.server.hdmi.HdmiLogger.error("Could not initialize HDMI Connection AIDL HAL", new java.lang.Object[0]);
                return false;
            }
            try {
                this.mHdmiConnection.asBinder().linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e2) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't link to death : ", e2, new java.lang.Object[0]);
                return true;
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mHdmiCec.asBinder().unlinkToDeath(this, 0);
            this.mHdmiConnection.asBinder().unlinkToDeath(this, 0);
            com.android.server.hdmi.HdmiLogger.error("HDMI Connection or CEC service died, reconnecting", new java.lang.Object[0]);
            connectToHal();
            if (this.mCallback != null) {
                setCallback(this.mCallback);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void setCallback(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                this.mHdmiCec.setCallback(new com.android.server.hdmi.HdmiCecController.HdmiCecCallbackAidl(hdmiCecCallback));
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't initialise tv.cec callback : ", e, new java.lang.Object[0]);
            }
            try {
                this.mHdmiConnection.setCallback(new com.android.server.hdmi.HdmiCecController.HdmiConnectionCallbackAidl(hdmiCecCallback));
            } catch (android.os.RemoteException e2) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't initialise tv.hdmi callback : ", e2, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            android.hardware.tv.hdmi.cec.CecMessage cecMessage = new android.hardware.tv.hdmi.cec.CecMessage();
            cecMessage.initiator = (byte) (i & 15);
            cecMessage.destination = (byte) (i2 & 15);
            cecMessage.body = bArr;
            try {
                return this.mHdmiCec.sendMessage(cecMessage);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to send CEC message : ", e, new java.lang.Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeAddLogicalAddress(int i) {
            try {
                return this.mHdmiCec.addLogicalAddress((byte) i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to add a logical address : ", e, new java.lang.Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeClearLogicalAddress() {
            try {
                this.mHdmiCec.clearLogicalAddress();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to clear logical address : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetPhysicalAddress() {
            try {
                return this.mHdmiCec.getPhysicalAddress();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get physical address : ", e, new java.lang.Object[0]);
                return 65535;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVersion() {
            try {
                return this.mHdmiCec.getCecVersion();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get cec version : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVendorId() {
            try {
                return this.mHdmiCec.getVendorId();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get vendor id : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableWakeupByOtp(boolean z) {
            try {
                this.mHdmiCec.enableWakeupByOtp(z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed call to enableWakeupByOtp : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableCec(boolean z) {
            try {
                this.mHdmiCec.enableCec(z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed call to enableCec : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableSystemCecControl(boolean z) {
            try {
                this.mHdmiCec.enableSystemCecControl(z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed call to enableSystemCecControl : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetLanguage(java.lang.String str) {
            try {
                this.mHdmiCec.setLanguage(str);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to set language : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                this.mHdmiCec.enableAudioReturnChannel(i, z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to enable/disable ARC : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public android.hardware.hdmi.HdmiPortInfo[] nativeGetPortInfos() {
            try {
                android.hardware.tv.hdmi.connection.HdmiPortInfo[] portInfo = this.mHdmiConnection.getPortInfo();
                android.hardware.hdmi.HdmiPortInfo[] hdmiPortInfoArr = new android.hardware.hdmi.HdmiPortInfo[portInfo.length];
                int i = 0;
                for (android.hardware.tv.hdmi.connection.HdmiPortInfo hdmiPortInfo : portInfo) {
                    hdmiPortInfoArr[i] = new android.hardware.hdmi.HdmiPortInfo.Builder(hdmiPortInfo.portId, hdmiPortInfo.type, hdmiPortInfo.physicalAddress).setCecSupported(hdmiPortInfo.cecSupported).setMhlSupported(false).setArcSupported(hdmiPortInfo.arcSupported).setEarcSupported(hdmiPortInfo.eArcSupported).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get port information : ", e, new java.lang.Object[0]);
                return null;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public boolean nativeIsConnected(int i) {
            try {
                return this.mHdmiConnection.isConnected(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get connection info : ", e, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetHpdSignalType(int i, int i2) {
            try {
                this.mHdmiConnection.setHpdSignal((byte) i, i2);
            } catch (android.os.ServiceSpecificException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not set HPD signal type for portId " + i2 + " to " + i + ". Error: ", java.lang.Integer.valueOf(e.errorCode));
            } catch (android.os.RemoteException e2) {
                com.android.server.hdmi.HdmiLogger.error("Could not set HPD signal type for portId " + i2 + " to " + i + ". Exception: ", e2, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetHpdSignalType(int i) {
            try {
                return this.mHdmiConnection.getHpdSignal(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not get HPD signal type for portId " + i + ". Exception: ", e, new java.lang.Object[0]);
                return 0;
            }
        }
    }

    private static final class NativeWrapperImpl11 implements com.android.server.hdmi.HdmiCecController.NativeWrapper, android.os.IHwBinder.DeathRecipient, android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback {

        @android.annotation.Nullable
        private com.android.server.hdmi.HdmiCecController.HdmiCecCallback mCallback;
        private android.hardware.tv.cec.V1_1.IHdmiCec mHdmiCec;
        private final java.lang.Object mLock;
        private int mPhysicalAddress;

        private NativeWrapperImpl11() {
            this.mLock = new java.lang.Object();
            this.mPhysicalAddress = 65535;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public java.lang.String nativeInit() {
            if (connectToHal()) {
                return this.mHdmiCec.toString();
            }
            return null;
        }

        boolean connectToHal() {
            try {
                this.mHdmiCec = android.hardware.tv.cec.V1_1.IHdmiCec.getService(true);
                try {
                    this.mHdmiCec.linkToDeath(this, 353L);
                } catch (android.os.RemoteException e) {
                    com.android.server.hdmi.HdmiLogger.error("Couldn't link to death : ", e, new java.lang.Object[0]);
                }
                return true;
            } catch (android.os.RemoteException | java.util.NoSuchElementException e2) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't connect to cec@1.1", e2, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback
        public void onValues(int i, short s) {
            if (i == 0) {
                synchronized (this.mLock) {
                    this.mPhysicalAddress = new java.lang.Short(s).intValue();
                }
            }
        }

        public void serviceDied(long j) {
            if (j == 353) {
                com.android.server.hdmi.HdmiLogger.error("Service died cookie : " + j + "; reconnecting", new java.lang.Object[0]);
                connectToHal();
                if (this.mCallback != null) {
                    setCallback(this.mCallback);
                }
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void setCallback(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                this.mHdmiCec.setCallback_1_1(new com.android.server.hdmi.HdmiCecController.HdmiCecCallback11(hdmiCecCallback));
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't initialise tv.cec callback : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            android.hardware.tv.cec.V1_1.CecMessage cecMessage = new android.hardware.tv.cec.V1_1.CecMessage();
            cecMessage.initiator = i;
            cecMessage.destination = i2;
            cecMessage.body = new java.util.ArrayList<>(bArr.length);
            for (byte b : bArr) {
                cecMessage.body.add(java.lang.Byte.valueOf(b));
            }
            try {
                return this.mHdmiCec.sendMessage_1_1(cecMessage);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to send CEC message : ", e, new java.lang.Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeAddLogicalAddress(int i) {
            try {
                return this.mHdmiCec.addLogicalAddress_1_1(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to add a logical address : ", e, new java.lang.Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeClearLogicalAddress() {
            try {
                this.mHdmiCec.clearLogicalAddress();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to clear logical address : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetPhysicalAddress() {
            try {
                this.mHdmiCec.getPhysicalAddress(this);
                return this.mPhysicalAddress;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get physical address : ", e, new java.lang.Object[0]);
                return 65535;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVersion() {
            try {
                return this.mHdmiCec.getCecVersion();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get cec version : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVendorId() {
            try {
                return this.mHdmiCec.getVendorId();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get vendor id : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public android.hardware.hdmi.HdmiPortInfo[] nativeGetPortInfos() {
            try {
                java.util.ArrayList<android.hardware.tv.cec.V1_0.HdmiPortInfo> portInfo = this.mHdmiCec.getPortInfo();
                android.hardware.hdmi.HdmiPortInfo[] hdmiPortInfoArr = new android.hardware.hdmi.HdmiPortInfo[portInfo.size()];
                java.util.Iterator<android.hardware.tv.cec.V1_0.HdmiPortInfo> it = portInfo.iterator();
                int i = 0;
                while (it.hasNext()) {
                    android.hardware.tv.cec.V1_0.HdmiPortInfo next = it.next();
                    hdmiPortInfoArr[i] = new android.hardware.hdmi.HdmiPortInfo.Builder(next.portId, next.type, java.lang.Short.toUnsignedInt(next.physicalAddress)).setCecSupported(next.cecSupported).setMhlSupported(false).setArcSupported(next.arcSupported).setEarcSupported(false).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get port information : ", e, new java.lang.Object[0]);
                return null;
            }
        }

        private void nativeSetOption(int i, boolean z) {
            try {
                this.mHdmiCec.setOption(i, z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to set option : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableWakeupByOtp(boolean z) {
            nativeSetOption(1, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableCec(boolean z) {
            nativeSetOption(2, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableSystemCecControl(boolean z) {
            nativeSetOption(3, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetLanguage(java.lang.String str) {
            try {
                this.mHdmiCec.setLanguage(str);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to set language : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                this.mHdmiCec.enableAudioReturnChannel(i, z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to enable/disable ARC : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public boolean nativeIsConnected(int i) {
            try {
                return this.mHdmiCec.isConnected(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get connection info : ", e, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetHpdSignalType(int i, int i2) {
            com.android.server.hdmi.HdmiLogger.error("Failed to set HPD signal type: not supported by HAL.", new java.lang.Object[0]);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetHpdSignalType(int i) {
            com.android.server.hdmi.HdmiLogger.error("Failed to get HPD signal type: not supported by HAL.", new java.lang.Object[0]);
            return 0;
        }
    }

    private static final class NativeWrapperImpl implements com.android.server.hdmi.HdmiCecController.NativeWrapper, android.os.IHwBinder.DeathRecipient, android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback {

        @android.annotation.Nullable
        private com.android.server.hdmi.HdmiCecController.HdmiCecCallback mCallback;
        private android.hardware.tv.cec.V1_0.IHdmiCec mHdmiCec;
        private final java.lang.Object mLock;
        private int mPhysicalAddress;

        private NativeWrapperImpl() {
            this.mLock = new java.lang.Object();
            this.mPhysicalAddress = 65535;
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public java.lang.String nativeInit() {
            if (connectToHal()) {
                return this.mHdmiCec.toString();
            }
            return null;
        }

        boolean connectToHal() {
            try {
                this.mHdmiCec = android.hardware.tv.cec.V1_0.IHdmiCec.getService(true);
                try {
                    this.mHdmiCec.linkToDeath(this, 353L);
                } catch (android.os.RemoteException e) {
                    com.android.server.hdmi.HdmiLogger.error("Couldn't link to death : ", e, new java.lang.Object[0]);
                }
                return true;
            } catch (android.os.RemoteException | java.util.NoSuchElementException e2) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't connect to cec@1.0", e2, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void setCallback(@android.annotation.NonNull com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mCallback = hdmiCecCallback;
            try {
                this.mHdmiCec.setCallback(new com.android.server.hdmi.HdmiCecController.HdmiCecCallback10(hdmiCecCallback));
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't initialise tv.cec callback : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeSendCecCommand(int i, int i2, byte[] bArr) {
            android.hardware.tv.cec.V1_0.CecMessage cecMessage = new android.hardware.tv.cec.V1_0.CecMessage();
            cecMessage.initiator = i;
            cecMessage.destination = i2;
            cecMessage.body = new java.util.ArrayList<>(bArr.length);
            for (byte b : bArr) {
                cecMessage.body.add(java.lang.Byte.valueOf(b));
            }
            try {
                return this.mHdmiCec.sendMessage(cecMessage);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to send CEC message : ", e, new java.lang.Object[0]);
                return 3;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeAddLogicalAddress(int i) {
            try {
                return this.mHdmiCec.addLogicalAddress(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to add a logical address : ", e, new java.lang.Object[0]);
                return 2;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeClearLogicalAddress() {
            try {
                this.mHdmiCec.clearLogicalAddress();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to clear logical address : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetPhysicalAddress() {
            try {
                this.mHdmiCec.getPhysicalAddress(this);
                return this.mPhysicalAddress;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get physical address : ", e, new java.lang.Object[0]);
                return 65535;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVersion() {
            try {
                return this.mHdmiCec.getCecVersion();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get cec version : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetVendorId() {
            try {
                return this.mHdmiCec.getVendorId();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get vendor id : ", e, new java.lang.Object[0]);
                return 1;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public android.hardware.hdmi.HdmiPortInfo[] nativeGetPortInfos() {
            try {
                java.util.ArrayList<android.hardware.tv.cec.V1_0.HdmiPortInfo> portInfo = this.mHdmiCec.getPortInfo();
                android.hardware.hdmi.HdmiPortInfo[] hdmiPortInfoArr = new android.hardware.hdmi.HdmiPortInfo[portInfo.size()];
                java.util.Iterator<android.hardware.tv.cec.V1_0.HdmiPortInfo> it = portInfo.iterator();
                int i = 0;
                while (it.hasNext()) {
                    android.hardware.tv.cec.V1_0.HdmiPortInfo next = it.next();
                    hdmiPortInfoArr[i] = new android.hardware.hdmi.HdmiPortInfo.Builder(next.portId, next.type, java.lang.Short.toUnsignedInt(next.physicalAddress)).setCecSupported(next.cecSupported).setMhlSupported(false).setArcSupported(next.arcSupported).setEarcSupported(false).build();
                    i++;
                }
                return hdmiPortInfoArr;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get port information : ", e, new java.lang.Object[0]);
                return null;
            }
        }

        private void nativeSetOption(int i, boolean z) {
            try {
                this.mHdmiCec.setOption(i, z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to set option : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableWakeupByOtp(boolean z) {
            nativeSetOption(1, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableCec(boolean z) {
            nativeSetOption(2, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void enableSystemCecControl(boolean z) {
            nativeSetOption(3, z);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetLanguage(java.lang.String str) {
            try {
                this.mHdmiCec.setLanguage(str);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to set language : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeEnableAudioReturnChannel(int i, boolean z) {
            try {
                this.mHdmiCec.enableAudioReturnChannel(i, z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to enable/disable ARC : ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public boolean nativeIsConnected(int i) {
            try {
                return this.mHdmiCec.isConnected(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Failed to get connection info : ", e, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public void nativeSetHpdSignalType(int i, int i2) {
            com.android.server.hdmi.HdmiLogger.error("Failed to set HPD signal type: not supported by HAL.", new java.lang.Object[0]);
        }

        @Override // com.android.server.hdmi.HdmiCecController.NativeWrapper
        public int nativeGetHpdSignalType(int i) {
            com.android.server.hdmi.HdmiLogger.error("Failed to get HPD signal type: not supported by HAL.", new java.lang.Object[0]);
            return 0;
        }

        public void serviceDied(long j) {
            if (j == 353) {
                com.android.server.hdmi.HdmiLogger.error("Service died cookie : " + j + "; reconnecting", new java.lang.Object[0]);
                connectToHal();
                if (this.mCallback != null) {
                    setCallback(this.mCallback);
                }
            }
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCec.getPhysicalAddressCallback
        public void onValues(int i, short s) {
            if (i == 0) {
                synchronized (this.mLock) {
                    this.mPhysicalAddress = new java.lang.Short(s).intValue();
                }
            }
        }
    }

    final class HdmiCecCallback {
        HdmiCecCallback() {
        }

        @com.android.internal.annotations.VisibleForTesting
        public void onCecMessage(final int i, final int i2, final byte[] bArr) {
            com.android.server.hdmi.HdmiCecController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController$HdmiCecCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiCecController.HdmiCecCallback.this.lambda$onCecMessage$0(i, i2, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCecMessage$0(int i, int i2, byte[] bArr) {
            com.android.server.hdmi.HdmiCecController.this.handleIncomingCecCommand(i, i2, bArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotplugEvent$1(int i, boolean z) {
            com.android.server.hdmi.HdmiCecController.this.handleHotplug(i, z);
        }

        @com.android.internal.annotations.VisibleForTesting
        public void onHotplugEvent(final int i, final boolean z) {
            com.android.server.hdmi.HdmiCecController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiCecController$HdmiCecCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiCecController.HdmiCecCallback.this.lambda$onHotplugEvent$1(i, z);
                }
            });
        }
    }

    private static final class HdmiCecCallback10 extends android.hardware.tv.cec.V1_0.IHdmiCecCallback.Stub {
        private final com.android.server.hdmi.HdmiCecController.HdmiCecCallback mHdmiCecCallback;

        HdmiCecCallback10(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback
        public void onCecMessage(android.hardware.tv.cec.V1_0.CecMessage cecMessage) throws android.os.RemoteException {
            byte[] bArr = new byte[cecMessage.body.size()];
            for (int i = 0; i < cecMessage.body.size(); i++) {
                bArr[i] = cecMessage.body.get(i).byteValue();
            }
            this.mHdmiCecCallback.onCecMessage(cecMessage.initiator, cecMessage.destination, bArr);
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback
        public void onHotplugEvent(android.hardware.tv.cec.V1_0.HotplugEvent hotplugEvent) throws android.os.RemoteException {
            this.mHdmiCecCallback.onHotplugEvent(hotplugEvent.portId, hotplugEvent.connected);
        }
    }

    private static final class HdmiCecCallback11 extends android.hardware.tv.cec.V1_1.IHdmiCecCallback.Stub {
        private final com.android.server.hdmi.HdmiCecController.HdmiCecCallback mHdmiCecCallback;

        HdmiCecCallback11(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.hardware.tv.cec.V1_1.IHdmiCecCallback
        public void onCecMessage_1_1(android.hardware.tv.cec.V1_1.CecMessage cecMessage) throws android.os.RemoteException {
            byte[] bArr = new byte[cecMessage.body.size()];
            for (int i = 0; i < cecMessage.body.size(); i++) {
                bArr[i] = cecMessage.body.get(i).byteValue();
            }
            this.mHdmiCecCallback.onCecMessage(cecMessage.initiator, cecMessage.destination, bArr);
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback
        public void onCecMessage(android.hardware.tv.cec.V1_0.CecMessage cecMessage) throws android.os.RemoteException {
            byte[] bArr = new byte[cecMessage.body.size()];
            for (int i = 0; i < cecMessage.body.size(); i++) {
                bArr[i] = cecMessage.body.get(i).byteValue();
            }
            this.mHdmiCecCallback.onCecMessage(cecMessage.initiator, cecMessage.destination, bArr);
        }

        @Override // android.hardware.tv.cec.V1_0.IHdmiCecCallback
        public void onHotplugEvent(android.hardware.tv.cec.V1_0.HotplugEvent hotplugEvent) throws android.os.RemoteException {
            this.mHdmiCecCallback.onHotplugEvent(hotplugEvent.portId, hotplugEvent.connected);
        }
    }

    private static final class HdmiCecCallbackAidl extends android.hardware.tv.hdmi.cec.IHdmiCecCallback.Stub {
        private final com.android.server.hdmi.HdmiCecController.HdmiCecCallback mHdmiCecCallback;

        HdmiCecCallbackAidl(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public void onCecMessage(android.hardware.tv.hdmi.cec.CecMessage cecMessage) throws android.os.RemoteException {
            this.mHdmiCecCallback.onCecMessage(cecMessage.initiator, cecMessage.destination, cecMessage.body);
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
            return "cd956e3a0c2e6ade71693c85e9f0aeffa221ea26";
        }

        @Override // android.hardware.tv.hdmi.cec.IHdmiCecCallback
        public int getInterfaceVersion() throws android.os.RemoteException {
            return 1;
        }
    }

    private static final class HdmiConnectionCallbackAidl extends android.hardware.tv.hdmi.connection.IHdmiConnectionCallback.Stub {
        private final com.android.server.hdmi.HdmiCecController.HdmiCecCallback mHdmiCecCallback;

        HdmiConnectionCallbackAidl(com.android.server.hdmi.HdmiCecController.HdmiCecCallback hdmiCecCallback) {
            this.mHdmiCecCallback = hdmiCecCallback;
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnectionCallback
        public void onHotplugEvent(boolean z, int i) throws android.os.RemoteException {
            this.mHdmiCecCallback.onHotplugEvent(i, z);
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnectionCallback
        public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
            return "85c26fa47f3c3062aa93ffc8bb0897a85c8cb118";
        }

        @Override // android.hardware.tv.hdmi.connection.IHdmiConnectionCallback
        public int getInterfaceVersion() throws android.os.RemoteException {
            return 1;
        }
    }

    public static abstract class Dumpable {
        protected final long mTime = java.lang.System.currentTimeMillis();

        abstract void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.text.SimpleDateFormat simpleDateFormat);

        Dumpable() {
        }
    }

    private static final class MessageHistoryRecord extends com.android.server.hdmi.HdmiCecController.Dumpable {
        private final boolean mIsReceived;
        private final com.android.server.hdmi.HdmiCecMessage mMessage;
        private final java.util.List<java.lang.String> mSendResults;

        MessageHistoryRecord(boolean z, com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, java.util.List<java.lang.String> list) {
            this.mIsReceived = z;
            this.mMessage = hdmiCecMessage;
            this.mSendResults = list;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.text.SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print(this.mIsReceived ? "[R]" : "[S]");
            indentingPrintWriter.print(" time=");
            indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mTime)));
            indentingPrintWriter.print(" message=");
            indentingPrintWriter.print(this.mMessage);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (!this.mIsReceived && this.mSendResults != null) {
                sb.append(" (");
                sb.append(java.lang.String.join(", ", this.mSendResults));
                sb.append(")");
            }
            indentingPrintWriter.println(sb);
        }
    }

    private static final class HotplugHistoryRecord extends com.android.server.hdmi.HdmiCecController.Dumpable {
        private final boolean mConnected;
        private final int mPort;

        HotplugHistoryRecord(int i, boolean z) {
            this.mPort = i;
            this.mConnected = z;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.text.SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("[H]");
            indentingPrintWriter.print(" time=");
            indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mTime)));
            indentingPrintWriter.print(" hotplug port=");
            indentingPrintWriter.print(this.mPort);
            indentingPrintWriter.print(" connected=");
            indentingPrintWriter.println(this.mConnected);
        }
    }
}
