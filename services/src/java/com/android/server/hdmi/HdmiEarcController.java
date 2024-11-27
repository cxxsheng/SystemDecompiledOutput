package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiEarcController {
    private static final java.lang.String TAG = "HdmiEarcController";
    private android.os.Handler mControlHandler;
    private com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper mEarcNativeWrapperImpl;
    private final com.android.server.hdmi.HdmiControlService mService;

    protected interface EarcNativeWrapper {
        byte[] nativeGetLastReportedAudioCapabilities(int i);

        byte nativeGetState(int i);

        boolean nativeInit();

        boolean nativeIsEarcEnabled();

        void nativeSetCallback(com.android.server.hdmi.HdmiEarcController.EarcAidlCallback earcAidlCallback);

        void nativeSetEarcEnabled(boolean z);
    }

    private static final class EarcNativeWrapperImpl implements com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper, android.os.IBinder.DeathRecipient {
        private android.hardware.tv.hdmi.earc.IEArc mEarc;
        private com.android.server.hdmi.HdmiEarcController.EarcAidlCallback mEarcCallback;

        private EarcNativeWrapperImpl() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mEarc.asBinder().unlinkToDeath(this, 0);
            connectToHal();
            if (this.mEarcCallback != null) {
                nativeSetCallback(this.mEarcCallback);
            }
        }

        boolean connectToHal() {
            this.mEarc = android.hardware.tv.hdmi.earc.IEArc.Stub.asInterface(android.os.ServiceManager.getService(android.hardware.tv.hdmi.earc.IEArc.DESCRIPTOR + "/default"));
            if (this.mEarc == null) {
                return false;
            }
            try {
                this.mEarc.asBinder().linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Couldn't link callback object: ", e, new java.lang.Object[0]);
                return true;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public boolean nativeInit() {
            return connectToHal();
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public void nativeSetEarcEnabled(boolean z) {
            try {
                this.mEarc.setEArcEnabled(z);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not set eARC enabled to " + z + ":. Exception: ", e, new java.lang.Object[0]);
            } catch (android.os.ServiceSpecificException e2) {
                com.android.server.hdmi.HdmiLogger.error("Could not set eARC enabled to " + z + ". Error: ", java.lang.Integer.valueOf(e2.errorCode));
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public boolean nativeIsEarcEnabled() {
            try {
                return this.mEarc.isEArcEnabled();
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not read if eARC is enabled. Exception: ", e, new java.lang.Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public void nativeSetCallback(com.android.server.hdmi.HdmiEarcController.EarcAidlCallback earcAidlCallback) {
            this.mEarcCallback = earcAidlCallback;
            try {
                this.mEarc.setCallback(earcAidlCallback);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not set callback. Exception: ", e, new java.lang.Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public byte nativeGetState(int i) {
            try {
                return this.mEarc.getState(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not get eARC state. Exception: ", e, new java.lang.Object[0]);
                return (byte) -1;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public byte[] nativeGetLastReportedAudioCapabilities(int i) {
            try {
                return this.mEarc.getLastReportedAudioCapabilities(i);
            } catch (android.os.RemoteException e) {
                com.android.server.hdmi.HdmiLogger.error("Could not read last reported audio capabilities. Exception: ", e, new java.lang.Object[0]);
                return null;
            }
        }
    }

    private HdmiEarcController(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper earcNativeWrapper) {
        this.mService = hdmiControlService;
        this.mEarcNativeWrapperImpl = earcNativeWrapper;
    }

    static com.android.server.hdmi.HdmiEarcController create(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        return createWithNativeWrapper(hdmiControlService, new com.android.server.hdmi.HdmiEarcController.EarcNativeWrapperImpl());
    }

    static com.android.server.hdmi.HdmiEarcController createWithNativeWrapper(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper earcNativeWrapper) {
        com.android.server.hdmi.HdmiEarcController hdmiEarcController = new com.android.server.hdmi.HdmiEarcController(hdmiControlService, earcNativeWrapper);
        if (!hdmiEarcController.init(earcNativeWrapper)) {
            com.android.server.hdmi.HdmiLogger.warning("Could not connect to eARC AIDL HAL.", new java.lang.Object[0]);
            return null;
        }
        return hdmiEarcController;
    }

    private boolean init(com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper earcNativeWrapper) {
        if (earcNativeWrapper.nativeInit()) {
            this.mControlHandler = new android.os.Handler(this.mService.getServiceLooper());
            this.mEarcNativeWrapperImpl.nativeSetCallback(new com.android.server.hdmi.HdmiEarcController.EarcAidlCallback());
            return true;
        }
        return false;
    }

    private void assertRunOnServiceThread() {
        if (android.os.Looper.myLooper() != this.mControlHandler.getLooper()) {
            throw new java.lang.IllegalStateException("Should run on service thread.");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void runOnServiceThread(java.lang.Runnable runnable) {
        this.mControlHandler.post(new com.android.server.hdmi.WorkSourceUidPreservingRunnable(runnable));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setEarcEnabled(boolean z) {
        assertRunOnServiceThread();
        this.mEarcNativeWrapperImpl.nativeSetEarcEnabled(z);
    }

    @com.android.server.hdmi.Constants.EarcStatus
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getState(int i) {
        return this.mEarcNativeWrapperImpl.nativeGetState(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    byte[] getLastReportedCaps(int i) {
        return this.mEarcNativeWrapperImpl.nativeGetLastReportedAudioCapabilities(i);
    }

    final class EarcAidlCallback extends android.hardware.tv.hdmi.earc.IEArcCallback.Stub {
        EarcAidlCallback() {
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public void onStateChange(@com.android.server.hdmi.Constants.EarcStatus final byte b, final int i) {
            com.android.server.hdmi.HdmiEarcController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiEarcController$EarcAidlCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiEarcController.EarcAidlCallback.this.lambda$onStateChange$0(b, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStateChange$0(byte b, int i) {
            com.android.server.hdmi.HdmiEarcController.this.mService.handleEarcStateChange(b, i);
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public void onCapabilitiesReported(final byte[] bArr, final int i) {
            com.android.server.hdmi.HdmiEarcController.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiEarcController$EarcAidlCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiEarcController.EarcAidlCallback.this.lambda$onCapabilitiesReported$1(bArr, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapabilitiesReported$1(byte[] bArr, int i) {
            com.android.server.hdmi.HdmiEarcController.this.mService.handleEarcCapabilitiesReported(bArr, i);
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
            return "101230f18c7b8438921e517e80eea4ccc7c1e463";
        }

        @Override // android.hardware.tv.hdmi.earc.IEArcCallback
        public int getInterfaceVersion() throws android.os.RemoteException {
            return 1;
        }
    }
}
