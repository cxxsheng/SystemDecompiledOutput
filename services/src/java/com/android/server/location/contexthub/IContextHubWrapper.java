package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public abstract class IContextHubWrapper {
    private static final java.lang.String TAG = "IContextHubWrapper";

    public interface ICallback {
        void handleContextHubEvent(int i);

        void handleMessageDeliveryStatus(android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus);

        void handleNanoappAbort(long j, int i);

        void handleNanoappInfo(java.util.List<android.hardware.location.NanoAppState> list);

        void handleNanoappMessage(short s, android.hardware.location.NanoAppMessage nanoAppMessage, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2);

        void handleServiceRestart();

        void handleTransactionResult(int i, boolean z);
    }

    public abstract int disableNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    public abstract int enableNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    public abstract android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> getHubs() throws android.os.RemoteException;

    public abstract long[] getPreloadedNanoappIds(int i);

    public abstract int loadNanoapp(int i, android.hardware.location.NanoAppBinary nanoAppBinary, int i2) throws android.os.RemoteException;

    public abstract void onAirplaneModeSettingChanged(boolean z);

    public abstract void onBtMainSettingChanged(boolean z);

    public abstract void onBtScanningSettingChanged(boolean z);

    public abstract void onLocationSettingChanged(boolean z);

    public abstract void onMicrophoneSettingChanged(boolean z);

    public abstract void onWifiMainSettingChanged(boolean z);

    public abstract void onWifiScanningSettingChanged(boolean z);

    public abstract void onWifiSettingChanged(boolean z);

    public abstract int queryNanoapps(int i) throws android.os.RemoteException;

    public abstract void registerCallback(int i, @android.annotation.NonNull com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) throws android.os.RemoteException;

    public abstract void registerExistingCallback(int i) throws android.os.RemoteException;

    public abstract int sendMessageDeliveryStatusToContextHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus);

    public abstract int sendMessageToContextHub(short s, int i, android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException;

    public abstract boolean setTestMode(boolean z);

    public abstract boolean supportsAirplaneModeSettingNotifications();

    public abstract boolean supportsBtSettingNotifications();

    public abstract boolean supportsLocationSettingNotifications();

    public abstract boolean supportsMicrophoneSettingNotifications();

    public abstract boolean supportsWifiSettingNotifications();

    public abstract int unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException;

    public static com.android.server.location.contexthub.IContextHubWrapper getContextHubWrapper() {
        com.android.server.location.contexthub.IContextHubWrapper maybeConnectToAidl = maybeConnectToAidl();
        if (maybeConnectToAidl == null) {
            maybeConnectToAidl = maybeConnectTo1_2();
        }
        if (maybeConnectToAidl == null) {
            maybeConnectToAidl = maybeConnectTo1_1();
        }
        if (maybeConnectToAidl == null) {
            return maybeConnectTo1_0();
        }
        return maybeConnectToAidl;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.location.contexthub.IContextHubWrapper maybeConnectTo1_0() {
        android.hardware.contexthub.V1_0.IContexthub iContexthub;
        try {
            iContexthub = android.hardware.contexthub.V1_0.IContexthub.getService(true);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException while attaching to Context Hub HAL proxy", e);
            iContexthub = null;
            if (iContexthub == null) {
            }
        } catch (java.util.NoSuchElementException e2) {
            android.util.Log.i(TAG, "Context Hub HAL service not found");
            iContexthub = null;
            if (iContexthub == null) {
            }
        }
        if (iContexthub == null) {
            return null;
        }
        return new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperV1_0(iContexthub);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.location.contexthub.IContextHubWrapper maybeConnectTo1_1() {
        android.hardware.contexthub.V1_1.IContexthub iContexthub;
        try {
            iContexthub = android.hardware.contexthub.V1_1.IContexthub.getService(true);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException while attaching to Context Hub HAL proxy", e);
            iContexthub = null;
            if (iContexthub == null) {
            }
        } catch (java.util.NoSuchElementException e2) {
            android.util.Log.i(TAG, "Context Hub HAL service not found");
            iContexthub = null;
            if (iContexthub == null) {
            }
        }
        if (iContexthub == null) {
            return null;
        }
        return new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperV1_1(iContexthub);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.location.contexthub.IContextHubWrapper maybeConnectTo1_2() {
        android.hardware.contexthub.V1_2.IContexthub iContexthub;
        try {
            iContexthub = android.hardware.contexthub.V1_2.IContexthub.getService(true);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException while attaching to Context Hub HAL proxy", e);
            iContexthub = null;
            if (iContexthub == null) {
            }
        } catch (java.util.NoSuchElementException e2) {
            android.util.Log.i(TAG, "Context Hub HAL service not found");
            iContexthub = null;
            if (iContexthub == null) {
            }
        }
        if (iContexthub == null) {
            return null;
        }
        return new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperV1_2(iContexthub);
    }

    public static android.hardware.contexthub.IContextHub maybeConnectToAidlGetProxy() {
        java.lang.String str = android.hardware.contexthub.IContextHub.class.getCanonicalName() + "/default";
        if (android.os.ServiceManager.isDeclared(str)) {
            android.hardware.contexthub.IContextHub asInterface = android.hardware.contexthub.IContextHub.Stub.asInterface(android.os.ServiceManager.waitForService(str));
            if (asInterface == null) {
                android.util.Log.e(TAG, "Context Hub AIDL service was declared but was not found");
                return asInterface;
            }
            return asInterface;
        }
        android.util.Log.d(TAG, "Context Hub AIDL service is not declared");
        return null;
    }

    @android.annotation.Nullable
    public static com.android.server.location.contexthub.IContextHubWrapper maybeConnectToAidl() {
        android.hardware.contexthub.IContextHub maybeConnectToAidlGetProxy = maybeConnectToAidlGetProxy();
        if (maybeConnectToAidlGetProxy == null) {
            return null;
        }
        return new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl(maybeConnectToAidlGetProxy);
    }

    public void onHostEndpointConnected(android.hardware.contexthub.HostEndpointInfo hostEndpointInfo) {
    }

    public void onHostEndpointDisconnected(short s) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ContextHubWrapperAidl extends com.android.server.location.contexthub.IContextHubWrapper implements android.os.IBinder.DeathRecipient {
        private android.os.Handler mHandler;
        private android.hardware.contexthub.IContextHub mHub;
        private final java.util.Map<java.lang.Integer, com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback> mAidlCallbackMap = new java.util.HashMap();
        private java.lang.Runnable mHandleServiceRestartCallback = null;
        private android.os.HandlerThread mHandlerThread = new android.os.HandlerThread("Context Hub AIDL callback", 10);

        /* JADX INFO: Access modifiers changed from: private */
        class ContextHubAidlCallback extends android.hardware.contexthub.IContextHubCallback.Stub {
            private static final java.lang.String NAME = "ContextHubService";
            private static final byte[] UUID = {-102, 23, 0, -115, 107, -15, 68, 90, -112, 17, 109, com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_HID, -67, -104, 91, 108};
            private final com.android.server.location.contexthub.IContextHubWrapper.ICallback mCallback;
            private final int mContextHubId;

            ContextHubAidlCallback(int i, com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) {
                this.mContextHubId = i;
                this.mCallback = iCallback;
            }

            public void handleNanoappInfo(android.hardware.contexthub.NanoappInfo[] nanoappInfoArr) {
                final java.util.List<android.hardware.location.NanoAppState> createNanoAppStateList = com.android.server.location.contexthub.ContextHubServiceUtil.createNanoAppStateList(nanoappInfoArr);
                com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback.this.lambda$handleNanoappInfo$0(createNanoAppStateList);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleNanoappInfo$0(java.util.List list) {
                this.mCallback.handleNanoappInfo(list);
            }

            public void handleContextHubMessage(final android.hardware.contexthub.ContextHubMessage contextHubMessage, final java.lang.String[] strArr) {
                com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback.this.lambda$handleContextHubMessage$1(contextHubMessage, strArr);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleContextHubMessage$1(android.hardware.contexthub.ContextHubMessage contextHubMessage, java.lang.String[] strArr) {
                this.mCallback.handleNanoappMessage((short) contextHubMessage.hostEndPoint, com.android.server.location.contexthub.ContextHubServiceUtil.createNanoAppMessage(contextHubMessage), new java.util.ArrayList(java.util.Arrays.asList(contextHubMessage.permissions)), new java.util.ArrayList(java.util.Arrays.asList(strArr)));
            }

            public void handleContextHubAsyncEvent(final int i) {
                com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback.this.lambda$handleContextHubAsyncEvent$2(i);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleContextHubAsyncEvent$2(int i) {
                this.mCallback.handleContextHubEvent(com.android.server.location.contexthub.ContextHubServiceUtil.toContextHubEventFromAidl(i));
            }

            public void handleTransactionResult(final int i, final boolean z) {
                com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback.this.lambda$handleTransactionResult$3(i, z);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleTransactionResult$3(int i, boolean z) {
                this.mCallback.handleTransactionResult(i, z);
            }

            public void handleNanSessionRequest(android.hardware.contexthub.NanSessionRequest nanSessionRequest) {
            }

            public void handleMessageDeliveryStatus(char c, final android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
                if (android.chre.flags.Flags.reliableMessageImplementation()) {
                    com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback.this.lambda$handleMessageDeliveryStatus$4(messageDeliveryStatus);
                        }
                    });
                } else {
                    android.util.Log.w(com.android.server.location.contexthub.IContextHubWrapper.TAG, "handleMessageDeliveryStatus called when the reliableMessageImplementation flag is disabled");
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$handleMessageDeliveryStatus$4(android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
                this.mCallback.handleMessageDeliveryStatus(messageDeliveryStatus);
            }

            public byte[] getUuid() {
                return UUID;
            }

            public java.lang.String getName() {
                return NAME;
            }

            public java.lang.String getInterfaceHash() {
                return "03f1982c8e20e58494a4ff8c9736b1c257dfeb6c";
            }

            public int getInterfaceVersion() {
                return 3;
            }
        }

        ContextHubWrapperAidl(android.hardware.contexthub.IContextHub iContextHub) {
            setHub(iContextHub);
            this.mHandlerThread.start();
            this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
            linkWrapperToHubDeath();
        }

        private synchronized android.hardware.contexthub.IContextHub getHub() {
            return this.mHub;
        }

        private synchronized void setHub(android.hardware.contexthub.IContextHub iContextHub) {
            this.mHub = iContextHub;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.i(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Context Hub AIDL HAL died");
            setHub(com.android.server.location.contexthub.IContextHubWrapper.maybeConnectToAidlGetProxy());
            if (getHub() == null) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Could not reconnect to Context Hub AIDL HAL");
                return;
            }
            linkWrapperToHubDeath();
            if (this.mHandleServiceRestartCallback != null) {
                this.mHandleServiceRestartCallback.run();
            } else {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "mHandleServiceRestartCallback is not set");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> getHubs() throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return new android.util.Pair<>(new java.util.ArrayList(), new java.util.ArrayList());
            }
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.hardware.contexthub.ContextHubInfo contextHubInfo : hub.getContextHubs()) {
                arrayList.add(new android.hardware.location.ContextHubInfo(contextHubInfo));
                for (java.lang.String str : contextHubInfo.supportedPermissions) {
                    hashSet.add(str);
                }
            }
            return new android.util.Pair<>(arrayList, new java.util.ArrayList(hashSet));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsLocationSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsWifiSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsAirplaneModeSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsMicrophoneSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsBtSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onLocationSettingChanged(boolean z) {
            onSettingChanged((byte) 1, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onAirplaneModeSettingChanged(boolean z) {
            onSettingChanged((byte) 4, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onMicrophoneSettingChanged(boolean z) {
            onSettingChanged((byte) 5, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiMainSettingChanged(boolean z) {
            onSettingChanged((byte) 2, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiScanningSettingChanged(boolean z) {
            onSettingChanged((byte) 3, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onBtMainSettingChanged(boolean z) {
            onSettingChanged((byte) 6, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onBtScanningSettingChanged(boolean z) {
            onSettingChanged((byte) 7, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onHostEndpointConnected(android.hardware.contexthub.HostEndpointInfo hostEndpointInfo) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onHostEndpointConnected(hostEndpointInfo);
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Exception in onHostEndpointConnected" + e.getMessage());
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onHostEndpointDisconnected(short s) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onHostEndpointDisconnected((char) s);
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Exception in onHostEndpointDisconnected" + e.getMessage());
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int sendMessageToContextHub(short s, int i, android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.sendMessageToHub(i, com.android.server.location.contexthub.ContextHubServiceUtil.createAidlContextHubMessage(s, nanoAppMessage));
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int sendMessageDeliveryStatusToContextHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.sendMessageDeliveryStatusToHub(i, messageDeliveryStatus);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int loadNanoapp(int i, android.hardware.location.NanoAppBinary nanoAppBinary, int i2) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.loadNanoapp(i, com.android.server.location.contexthub.ContextHubServiceUtil.createAidlNanoAppBinary(nanoAppBinary), i2);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.UnsupportedOperationException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.unloadNanoapp(i, j, i2);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.UnsupportedOperationException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int enableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.enableNanoapp(i, j, i2);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.UnsupportedOperationException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int disableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.disableNanoapp(i, j, i2);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.UnsupportedOperationException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int queryNanoapps(int i) throws android.os.RemoteException {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.queryNanoapps(i);
                return 0;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.UnsupportedOperationException e) {
                return 1;
            } catch (java.lang.IllegalArgumentException e2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public long[] getPreloadedNanoappIds(int i) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return null;
            }
            try {
                return hub.getPreloadedNanoappIds(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Exception while getting preloaded nanoapp IDs: " + e.getMessage());
                return null;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void registerExistingCallback(int i) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback contextHubAidlCallback = this.mAidlCallbackMap.get(java.lang.Integer.valueOf(i));
            if (contextHubAidlCallback == null) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Could not find existing callback to register for context hub ID = " + i);
                return;
            }
            try {
                hub.registerCallback(i, contextHubAidlCallback);
            } catch (android.os.RemoteException | android.os.ServiceSpecificException | java.lang.IllegalArgumentException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Exception while registering callback: " + e.getMessage());
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void registerCallback(int i, final com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) {
            if (getHub() == null) {
                return;
            }
            java.util.Objects.requireNonNull(iCallback);
            this.mHandleServiceRestartCallback = new java.lang.Runnable() { // from class: com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.contexthub.IContextHubWrapper.ICallback.this.handleServiceRestart();
                }
            };
            this.mAidlCallbackMap.put(java.lang.Integer.valueOf(i), new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback(i, iCallback));
            registerExistingCallback(i);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean setTestMode(boolean z) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return false;
            }
            try {
                hub.setTestMode(z);
                return true;
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Exception while setting test mode (enable: ");
                sb.append(z ? "true" : "false");
                sb.append("): ");
                sb.append(e.getMessage());
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, sb.toString());
                return false;
            }
        }

        private void onSettingChanged(byte b, boolean z) {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onSettingChanged(b, z);
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Exception while sending setting update: " + e.getMessage());
            }
        }

        private void linkWrapperToHubDeath() {
            android.hardware.contexthub.IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Context Hub AIDL service death receipt could not be linked");
            }
        }
    }

    private static abstract class ContextHubWrapperHidl extends com.android.server.location.contexthub.IContextHubWrapper {
        protected com.android.server.location.contexthub.IContextHubWrapper.ICallback mCallback = null;
        protected final java.util.Map<java.lang.Integer, com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl.ContextHubWrapperHidlCallback> mHidlCallbackMap = new java.util.HashMap();
        private android.hardware.contexthub.V1_0.IContexthub mHub;

        protected class ContextHubWrapperHidlCallback extends android.hardware.contexthub.V1_2.IContexthubCallback.Stub {
            private final com.android.server.location.contexthub.IContextHubWrapper.ICallback mCallback;
            private final int mContextHubId;

            ContextHubWrapperHidlCallback(int i, com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) {
                this.mContextHubId = i;
                this.mCallback = iCallback;
            }

            public void handleClientMsg(android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg) {
                this.mCallback.handleNanoappMessage(contextHubMsg.hostEndPoint, com.android.server.location.contexthub.ContextHubServiceUtil.createNanoAppMessage(contextHubMsg), java.util.Collections.emptyList(), java.util.Collections.emptyList());
            }

            public void handleTxnResult(int i, int i2) {
                this.mCallback.handleTransactionResult(i, i2 == 0);
            }

            public void handleHubEvent(int i) {
                this.mCallback.handleContextHubEvent(com.android.server.location.contexthub.ContextHubServiceUtil.toContextHubEvent(i));
            }

            public void handleAppAbort(long j, int i) {
                this.mCallback.handleNanoappAbort(j, i);
            }

            public void handleAppsInfo(java.util.ArrayList<android.hardware.contexthub.V1_0.HubAppInfo> arrayList) {
                handleAppsInfo_1_2(com.android.server.location.contexthub.ContextHubServiceUtil.toHubAppInfo_1_2(arrayList));
            }

            public void handleClientMsg_1_2(android.hardware.contexthub.V1_2.ContextHubMsg contextHubMsg, java.util.ArrayList<java.lang.String> arrayList) {
                this.mCallback.handleNanoappMessage(contextHubMsg.msg_1_0.hostEndPoint, com.android.server.location.contexthub.ContextHubServiceUtil.createNanoAppMessage(contextHubMsg.msg_1_0), contextHubMsg.permissions, arrayList);
            }

            public void handleAppsInfo_1_2(java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> arrayList) {
                this.mCallback.handleNanoappInfo(com.android.server.location.contexthub.ContextHubServiceUtil.createNanoAppStateList(arrayList));
            }
        }

        ContextHubWrapperHidl(android.hardware.contexthub.V1_0.IContexthub iContexthub) {
            this.mHub = iContexthub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int sendMessageToContextHub(short s, int i, android.hardware.location.NanoAppMessage nanoAppMessage) throws android.os.RemoteException {
            if (nanoAppMessage.isReliable()) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Reliable messages are only supported with the AIDL HAL");
                return 2;
            }
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.sendMessageToHub(i, com.android.server.location.contexthub.ContextHubServiceUtil.createHidlContextHubMessage(s, nanoAppMessage)));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int sendMessageDeliveryStatusToContextHub(int i, android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
            return 9;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int loadNanoapp(int i, android.hardware.location.NanoAppBinary nanoAppBinary, int i2) throws android.os.RemoteException {
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.loadNanoApp(i, com.android.server.location.contexthub.ContextHubServiceUtil.createHidlNanoAppBinary(nanoAppBinary), i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int unloadNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.unloadNanoApp(i, j, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int enableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.enableNanoApp(i, j, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int disableNanoapp(int i, long j, int i2) throws android.os.RemoteException {
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.disableNanoApp(i, j, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public int queryNanoapps(int i) throws android.os.RemoteException {
            return com.android.server.location.contexthub.ContextHubServiceUtil.toTransactionResult(this.mHub.queryApps(i));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public long[] getPreloadedNanoappIds(int i) {
            return new long[0];
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void registerCallback(int i, com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) throws android.os.RemoteException {
            this.mHidlCallbackMap.put(java.lang.Integer.valueOf(i), new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl.ContextHubWrapperHidlCallback(i, iCallback));
            this.mHub.registerCallback(i, this.mHidlCallbackMap.get(java.lang.Integer.valueOf(i)));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void registerExistingCallback(int i) throws android.os.RemoteException {
            com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl.ContextHubWrapperHidlCallback contextHubWrapperHidlCallback = this.mHidlCallbackMap.get(java.lang.Integer.valueOf(i));
            if (contextHubWrapperHidlCallback == null) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Could not find existing callback for context hub with ID = " + i);
                return;
            }
            this.mHub.registerCallback(i, contextHubWrapperHidlCallback);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean setTestMode(boolean z) {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsBtSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiMainSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiScanningSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onBtMainSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onBtScanningSettingChanged(boolean z) {
        }
    }

    private static class ContextHubWrapperV1_0 extends com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl {
        private android.hardware.contexthub.V1_0.IContexthub mHub;

        ContextHubWrapperV1_0(android.hardware.contexthub.V1_0.IContexthub iContexthub) {
            super(iContexthub);
            this.mHub = iContexthub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> getHubs() throws android.os.RemoteException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator it = this.mHub.getHubs().iterator();
            while (it.hasNext()) {
                arrayList.add(new android.hardware.location.ContextHubInfo((android.hardware.contexthub.V1_0.ContextHub) it.next()));
            }
            return new android.util.Pair<>(arrayList, new java.util.ArrayList());
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsLocationSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsWifiSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsAirplaneModeSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsMicrophoneSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onLocationSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onAirplaneModeSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onMicrophoneSettingChanged(boolean z) {
        }
    }

    private static class ContextHubWrapperV1_1 extends com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl {
        private android.hardware.contexthub.V1_1.IContexthub mHub;

        ContextHubWrapperV1_1(android.hardware.contexthub.V1_1.IContexthub iContexthub) {
            super(iContexthub);
            this.mHub = iContexthub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> getHubs() throws android.os.RemoteException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator it = this.mHub.getHubs().iterator();
            while (it.hasNext()) {
                arrayList.add(new android.hardware.location.ContextHubInfo((android.hardware.contexthub.V1_0.ContextHub) it.next()));
            }
            return new android.util.Pair<>(arrayList, new java.util.ArrayList());
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsLocationSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsWifiSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsAirplaneModeSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsMicrophoneSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onLocationSettingChanged(boolean z) {
            try {
                this.mHub.onSettingChanged((byte) 0, z ? (byte) 1 : (byte) 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Failed to send setting change to Contexthub", e);
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onAirplaneModeSettingChanged(boolean z) {
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onMicrophoneSettingChanged(boolean z) {
        }
    }

    private static class ContextHubWrapperV1_2 extends com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl implements android.hardware.contexthub.V1_2.IContexthub.getHubs_1_2Callback {
        private final android.hardware.contexthub.V1_2.IContexthub mHub;
        private android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> mHubInfo;

        ContextHubWrapperV1_2(android.hardware.contexthub.V1_2.IContexthub iContexthub) {
            super(iContexthub);
            this.mHubInfo = new android.util.Pair<>(java.util.Collections.emptyList(), java.util.Collections.emptyList());
            this.mHub = iContexthub;
        }

        public void onValues(java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> arrayList, java.util.ArrayList<java.lang.String> arrayList2) {
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            java.util.Iterator<android.hardware.contexthub.V1_0.ContextHub> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList3.add(new android.hardware.location.ContextHubInfo(it.next()));
            }
            this.mHubInfo = new android.util.Pair<>(arrayList3, arrayList2);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> getHubs() throws android.os.RemoteException {
            this.mHub.getHubs_1_2(this);
            return this.mHubInfo;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsLocationSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsWifiSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsAirplaneModeSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public boolean supportsMicrophoneSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onLocationSettingChanged(boolean z) {
            sendSettingChanged((byte) 0, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onWifiSettingChanged(boolean z) {
            sendSettingChanged((byte) 1, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onAirplaneModeSettingChanged(boolean z) {
            sendSettingChanged((byte) 2, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void onMicrophoneSettingChanged(boolean z) {
            sendSettingChanged((byte) 3, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl, com.android.server.location.contexthub.IContextHubWrapper
        public void registerCallback(int i, com.android.server.location.contexthub.IContextHubWrapper.ICallback iCallback) throws android.os.RemoteException {
            this.mHidlCallbackMap.put(java.lang.Integer.valueOf(i), new com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl.ContextHubWrapperHidlCallback(i, iCallback));
            this.mHub.registerCallback_1_2(i, this.mHidlCallbackMap.get(java.lang.Integer.valueOf(i)));
        }

        private void sendSettingChanged(byte b, byte b2) {
            try {
                this.mHub.onSettingChanged_1_2(b, b2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.location.contexthub.IContextHubWrapper.TAG, "Failed to send setting change to Contexthub", e);
            }
        }
    }
}
