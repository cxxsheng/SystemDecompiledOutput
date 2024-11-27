package com.android.server.broadcastradio.hal1;

/* loaded from: classes.dex */
class TunerCallback implements android.hardware.radio.ITunerCallback {
    private static final java.lang.String TAG = "BcRadio1Srv.TunerCallback";

    @android.annotation.NonNull
    private final android.hardware.radio.ITunerCallback mClientCallback;
    private final long mNativeContext;

    @android.annotation.NonNull
    private final com.android.server.broadcastradio.hal1.Tuner mTuner;
    private final java.util.concurrent.atomic.AtomicReference<android.hardware.radio.ProgramList.Filter> mProgramListFilter = new java.util.concurrent.atomic.AtomicReference<>();
    private boolean mInitialConfigurationDone = false;

    /* JADX INFO: Access modifiers changed from: private */
    interface RunnableThrowingRemoteException {
        void run() throws android.os.RemoteException;
    }

    private native void nativeDetach(long j);

    private native void nativeFinalize(long j);

    private native long nativeInit(@android.annotation.NonNull com.android.server.broadcastradio.hal1.Tuner tuner, int i);

    TunerCallback(@android.annotation.NonNull com.android.server.broadcastradio.hal1.Tuner tuner, @android.annotation.NonNull android.hardware.radio.ITunerCallback iTunerCallback, int i) {
        this.mTuner = tuner;
        this.mClientCallback = iTunerCallback;
        this.mNativeContext = nativeInit(tuner, i);
    }

    protected void finalize() throws java.lang.Throwable {
        nativeFinalize(this.mNativeContext);
        super.finalize();
    }

    public void detach() {
        nativeDetach(this.mNativeContext);
    }

    private void dispatch(com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException runnableThrowingRemoteException) {
        try {
            runnableThrowingRemoteException.run();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "client died", e);
        }
    }

    private void handleHwFailure() {
        onError(0);
        this.mTuner.close();
    }

    void startProgramListUpdates(@android.annotation.Nullable android.hardware.radio.ProgramList.Filter filter) {
        if (filter == null) {
            filter = new android.hardware.radio.ProgramList.Filter();
        }
        this.mProgramListFilter.set(filter);
        sendProgramListUpdate();
    }

    void stopProgramListUpdates() {
        this.mProgramListFilter.set(null);
    }

    boolean isInitialConfigurationDone() {
        return this.mInitialConfigurationDone;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$0(int i) throws android.os.RemoteException {
        this.mClientCallback.onError(i);
    }

    public void onError(final int i) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda9
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onError$0(i);
            }
        });
    }

    public void onTuneFailed(int i, android.hardware.radio.ProgramSelector programSelector) {
        android.util.Slog.e(TAG, "Not applicable for HAL 1.x");
    }

    public void onConfigurationChanged(final android.hardware.radio.RadioManager.BandConfig bandConfig) {
        this.mInitialConfigurationDone = true;
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda5
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onConfigurationChanged$1(bandConfig);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$1(android.hardware.radio.RadioManager.BandConfig bandConfig) throws android.os.RemoteException {
        this.mClientCallback.onConfigurationChanged(bandConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCurrentProgramInfoChanged$2(android.hardware.radio.RadioManager.ProgramInfo programInfo) throws android.os.RemoteException {
        this.mClientCallback.onCurrentProgramInfoChanged(programInfo);
    }

    public void onCurrentProgramInfoChanged(final android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda7
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onCurrentProgramInfoChanged$2(programInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrafficAnnouncement$3(boolean z) throws android.os.RemoteException {
        this.mClientCallback.onTrafficAnnouncement(z);
    }

    public void onTrafficAnnouncement(final boolean z) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda6
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onTrafficAnnouncement$3(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEmergencyAnnouncement$4(boolean z) throws android.os.RemoteException {
        this.mClientCallback.onEmergencyAnnouncement(z);
    }

    public void onEmergencyAnnouncement(final boolean z) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda3
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onEmergencyAnnouncement$4(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAntennaState$5(boolean z) throws android.os.RemoteException {
        this.mClientCallback.onAntennaState(z);
    }

    public void onAntennaState(final boolean z) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda4
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onAntennaState$5(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackgroundScanAvailabilityChange$6(boolean z) throws android.os.RemoteException {
        this.mClientCallback.onBackgroundScanAvailabilityChange(z);
    }

    public void onBackgroundScanAvailabilityChange(final boolean z) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda1
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onBackgroundScanAvailabilityChange$6(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackgroundScanComplete$7() throws android.os.RemoteException {
        this.mClientCallback.onBackgroundScanComplete();
    }

    public void onBackgroundScanComplete() {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda10
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onBackgroundScanComplete$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListChanged$8() throws android.os.RemoteException {
        this.mClientCallback.onProgramListChanged();
    }

    public void onProgramListChanged() {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda2
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onProgramListChanged$8();
            }
        });
        sendProgramListUpdate();
    }

    private void sendProgramListUpdate() {
        android.hardware.radio.ProgramList.Filter filter = this.mProgramListFilter.get();
        if (filter == null) {
            return;
        }
        try {
            final android.hardware.radio.ProgramList.Chunk chunk = new android.hardware.radio.ProgramList.Chunk(true, true, (java.util.Set) this.mTuner.getProgramList(filter.getVendorFilter()).stream().collect(java.util.stream.Collectors.toSet()), (java.util.Set) null);
            dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda0
                @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
                public final void run() {
                    com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$sendProgramListUpdate$9(chunk);
                }
            });
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.d(TAG, "Program list not ready yet");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendProgramListUpdate$9(android.hardware.radio.ProgramList.Chunk chunk) throws android.os.RemoteException {
        this.mClientCallback.onProgramListUpdated(chunk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListUpdated$10(android.hardware.radio.ProgramList.Chunk chunk) throws android.os.RemoteException {
        this.mClientCallback.onProgramListUpdated(chunk);
    }

    public void onProgramListUpdated(final android.hardware.radio.ProgramList.Chunk chunk) {
        dispatch(new com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException() { // from class: com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda8
            @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
            public final void run() {
                com.android.server.broadcastradio.hal1.TunerCallback.this.lambda$onProgramListUpdated$10(chunk);
            }
        });
    }

    public void onConfigFlagUpdated(int i, boolean z) {
        android.util.Slog.w(TAG, "Not applicable for HAL 1.x");
    }

    public void onParametersUpdated(java.util.Map<java.lang.String, java.lang.String> map) {
        android.util.Slog.w(TAG, "Not applicable for HAL 1.x");
    }

    public android.os.IBinder asBinder() {
        throw new java.lang.RuntimeException("Not a binder");
    }
}
