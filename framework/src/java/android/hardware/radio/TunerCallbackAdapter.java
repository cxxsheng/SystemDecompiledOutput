package android.hardware.radio;

/* loaded from: classes2.dex */
final class TunerCallbackAdapter extends android.hardware.radio.ITunerCallback.Stub {
    private static final java.lang.String TAG = "BroadcastRadio.TunerCallbackAdapter";
    private final android.hardware.radio.RadioTuner.Callback mCallback;
    android.hardware.radio.RadioManager.ProgramInfo mCurrentProgramInfo;
    private boolean mDelayedCompleteCallback;
    private final android.os.Handler mHandler;
    java.util.List<android.hardware.radio.RadioManager.ProgramInfo> mLastCompleteList;
    android.hardware.radio.ProgramList mProgramList;
    private final java.lang.Object mLock = new java.lang.Object();
    boolean mIsAntennaConnected = true;

    TunerCallbackAdapter(android.hardware.radio.RadioTuner.Callback callback, android.os.Handler handler) {
        this.mCallback = (android.hardware.radio.RadioTuner.Callback) java.util.Objects.requireNonNull(callback, "Callback cannot be null");
        if (handler == null) {
            this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        } else {
            this.mHandler = handler;
        }
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                this.mProgramList.close();
            }
        }
    }

    void setProgramListObserver(final android.hardware.radio.ProgramList programList, final android.hardware.radio.ProgramList.OnCloseListener onCloseListener) {
        java.util.Objects.requireNonNull(onCloseListener, "CloseListener cannot be null");
        synchronized (this.mLock) {
            if (this.mProgramList != null) {
                android.util.Log.w(TAG, "Previous program list observer wasn't properly closed, closing it...");
                this.mProgramList.close();
            }
            this.mProgramList = programList;
            if (programList == null) {
                return;
            }
            programList.setOnCloseListener(new android.hardware.radio.ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda4
                @Override // android.hardware.radio.ProgramList.OnCloseListener
                public final void onClose() {
                    android.hardware.radio.TunerCallbackAdapter.this.lambda$setProgramListObserver$0(programList, onCloseListener);
                }
            });
            programList.addOnCompleteListener(new android.hardware.radio.ProgramList.OnCompleteListener() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda5
                @Override // android.hardware.radio.ProgramList.OnCompleteListener
                public final void onComplete() {
                    android.hardware.radio.TunerCallbackAdapter.this.lambda$setProgramListObserver$1(programList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgramListObserver$0(android.hardware.radio.ProgramList programList, android.hardware.radio.ProgramList.OnCloseListener onCloseListener) {
        synchronized (this.mLock) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mProgramList = null;
            this.mLastCompleteList = null;
            onCloseListener.onClose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgramListObserver$1(android.hardware.radio.ProgramList programList) {
        synchronized (this.mLock) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mLastCompleteList = programList.toList();
            if (this.mDelayedCompleteCallback) {
                android.util.Log.d(TAG, "Sending delayed onBackgroundScanComplete callback");
                sendBackgroundScanCompleteLocked();
            }
        }
    }

    java.util.List<android.hardware.radio.RadioManager.ProgramInfo> getLastCompleteList() {
        java.util.List<android.hardware.radio.RadioManager.ProgramInfo> list;
        synchronized (this.mLock) {
            list = this.mLastCompleteList;
        }
        return list;
    }

    void clearLastCompleteList() {
        synchronized (this.mLock) {
            this.mLastCompleteList = null;
        }
    }

    android.hardware.radio.RadioManager.ProgramInfo getCurrentProgramInformation() {
        android.hardware.radio.RadioManager.ProgramInfo programInfo;
        synchronized (this.mLock) {
            programInfo = this.mCurrentProgramInfo;
        }
        return programInfo;
    }

    boolean isAntennaConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsAntennaConnected;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$2(int i) {
        this.mCallback.onError(i);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onError(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onError$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTuneFailed$3(int i, android.hardware.radio.ProgramSelector programSelector) {
        this.mCallback.onTuneFailed(i, programSelector);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.hardware.radio.ITunerCallback
    public void onTuneFailed(final int i, final android.hardware.radio.ProgramSelector programSelector) {
        final int i2;
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onTuneFailed$3(i, programSelector);
            }
        });
        switch (i) {
            case Integer.MIN_VALUE:
            case -38:
            case -22:
            case -19:
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
                android.util.Log.i(TAG, "Got an error with no mapping to the legacy API (" + i + "), doing a best-effort conversion to ERROR_SCAN_TIMEOUT");
                i2 = 3;
                break;
            case -32:
            case -1:
                i2 = 1;
                break;
            case 6:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onTuneFailed$4(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTuneFailed$4(int i) {
        this.mCallback.onError(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigurationChanged$5(android.hardware.radio.RadioManager.BandConfig bandConfig) {
        this.mCallback.onConfigurationChanged(bandConfig);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onConfigurationChanged(final android.hardware.radio.RadioManager.BandConfig bandConfig) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onConfigurationChanged$5(bandConfig);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onCurrentProgramInfoChanged(final android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        if (programInfo == null) {
            android.util.Log.e(TAG, "ProgramInfo must not be null");
            return;
        }
        synchronized (this.mLock) {
            this.mCurrentProgramInfo = programInfo;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onCurrentProgramInfoChanged$6(programInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCurrentProgramInfoChanged$6(android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        this.mCallback.onProgramInfoChanged(programInfo);
        android.hardware.radio.RadioMetadata metadata = programInfo.getMetadata();
        if (metadata != null) {
            this.mCallback.onMetadataChanged(metadata);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrafficAnnouncement$7(boolean z) {
        this.mCallback.onTrafficAnnouncement(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onTrafficAnnouncement(final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onTrafficAnnouncement$7(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEmergencyAnnouncement$8(boolean z) {
        this.mCallback.onEmergencyAnnouncement(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onEmergencyAnnouncement(final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onEmergencyAnnouncement$8(z);
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onAntennaState(final boolean z) {
        synchronized (this.mLock) {
            this.mIsAntennaConnected = z;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onAntennaState$9(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAntennaState$9(boolean z) {
        this.mCallback.onAntennaState(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackgroundScanAvailabilityChange$10(boolean z) {
        this.mCallback.onBackgroundScanAvailabilityChange(z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanAvailabilityChange(final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onBackgroundScanAvailabilityChange$10(z);
            }
        });
    }

    private void sendBackgroundScanCompleteLocked() {
        this.mDelayedCompleteCallback = false;
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$sendBackgroundScanCompleteLocked$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendBackgroundScanCompleteLocked$11() {
        this.mCallback.onBackgroundScanComplete();
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onBackgroundScanComplete() {
        synchronized (this.mLock) {
            if (this.mLastCompleteList == null) {
                android.util.Log.i(TAG, "Got onBackgroundScanComplete callback, but the program list didn't get through yet. Delaying it...");
                this.mDelayedCompleteCallback = true;
            } else {
                sendBackgroundScanCompleteLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListChanged$12() {
        this.mCallback.onProgramListChanged();
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListChanged() {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onProgramListChanged$12();
            }
        });
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onProgramListUpdated(final android.hardware.radio.ProgramList.Chunk chunk) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onProgramListUpdated$13(chunk);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgramListUpdated$13(android.hardware.radio.ProgramList.Chunk chunk) {
        synchronized (this.mLock) {
            if (this.mProgramList == null) {
                return;
            }
            this.mProgramList.apply((android.hardware.radio.ProgramList.Chunk) java.util.Objects.requireNonNull(chunk, "Chunk cannot be null"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConfigFlagUpdated$14(int i, boolean z) {
        this.mCallback.onConfigFlagUpdated(i, z);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onConfigFlagUpdated(final int i, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onConfigFlagUpdated$14(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onParametersUpdated$15(java.util.Map map) {
        this.mCallback.onParametersUpdated(map);
    }

    @Override // android.hardware.radio.ITunerCallback
    public void onParametersUpdated(final java.util.Map<java.lang.String, java.lang.String> map) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.hardware.radio.TunerCallbackAdapter$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.radio.TunerCallbackAdapter.this.lambda$onParametersUpdated$15(map);
            }
        });
    }
}
