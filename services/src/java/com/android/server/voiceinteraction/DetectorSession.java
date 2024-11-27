package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
abstract class DetectorSession {
    static final boolean DEBUG = false;
    private static final long EXTERNAL_HOTWORD_CLEANUP_MILLIS = 2000;
    private static final int EXTERNAL_SOURCE_DETECT_SECURITY_EXCEPTION = 13;
    private static final java.lang.String HOTWORD_DETECTION_OP_MESSAGE = "Providing hotword detection result to VoiceInteractionService";
    private static final int HOTWORD_EVENT_TYPE_DETECTION = 1;
    private static final int HOTWORD_EVENT_TYPE_REJECTION = 2;
    private static final int HOTWORD_EVENT_TYPE_TRAINING_DATA = 3;
    private static final java.time.Duration MAX_UPDATE_TIMEOUT_DURATION = java.time.Duration.ofMillis(30000);
    private static final long MAX_UPDATE_TIMEOUT_MILLIS = 30000;
    private static final int METRICS_CALLBACK_ON_STATUS_REPORTED_EXCEPTION = 14;
    private static final int METRICS_EXTERNAL_SOURCE_DETECTED = 11;
    private static final int METRICS_EXTERNAL_SOURCE_REJECTED = 12;
    private static final int METRICS_INIT_CALLBACK_STATE_ERROR = 1;
    private static final int METRICS_INIT_CALLBACK_STATE_SUCCESS = 0;
    private static final int METRICS_INIT_UNKNOWN_NO_VALUE = 2;
    private static final int METRICS_INIT_UNKNOWN_OVER_MAX_CUSTOM_VALUE = 3;
    private static final int METRICS_INIT_UNKNOWN_TIMEOUT = 4;
    static final int METRICS_KEYPHRASE_TRIGGERED_DETECT_SECURITY_EXCEPTION = 8;
    static final int METRICS_KEYPHRASE_TRIGGERED_DETECT_UNEXPECTED_CALLBACK = 7;
    static final int METRICS_KEYPHRASE_TRIGGERED_REJECT_UNEXPECTED_CALLBACK = 9;
    static final int ONDETECTED_GOT_SECURITY_EXCEPTION = 5;
    static final int ONDETECTED_STREAM_COPY_ERROR = 6;
    private static final java.lang.String TAG = "DetectorSession";
    private final android.app.AppOpsManager mAppOpsManager;

    @android.annotation.Nullable
    android.attention.AttentionManagerInternal mAttentionManagerInternal;
    final com.android.internal.app.IHotwordRecognitionStatusCallback mCallback;
    final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    android.os.ParcelFileDescriptor mCurrentAudioSink;
    boolean mDebugHotwordLogging;
    final com.android.server.voiceinteraction.HotwordAudioStreamCopier mHotwordAudioStreamCopier;
    final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mPerformingExternalSourceHotwordDetection;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection mRemoteDetectionService;

    @android.annotation.NonNull
    com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener mRemoteExceptionListener;
    final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;

    @android.annotation.NonNull
    final android.os.IBinder mToken;
    final int mUserId;
    final int mVoiceInteractionServiceUid;

    @android.annotation.Nullable
    private final android.media.permission.Identity mVoiceInteractorIdentity;
    private final java.util.concurrent.Executor mAudioCopyExecutor = java.util.concurrent.Executors.newCachedThreadPool();
    final java.util.concurrent.atomic.AtomicBoolean mUpdateStateAfterStartFinished = new java.util.concurrent.atomic.AtomicBoolean(false);
    final android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal mProximityCallbackInternal = new android.attention.AttentionManagerInternal.ProximityUpdateCallbackInternal() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda0
        public final void onProximityUpdate(double d) {
            com.android.server.voiceinteraction.DetectorSession.this.setProximityValue(d);
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private double mProximityMeters = -1.0d;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mInitialized = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDestroyed = false;

    abstract void informRestartProcessLocked();

    DetectorSession(@android.annotation.NonNull com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, android.media.permission.Identity identity, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, boolean z, @android.annotation.NonNull com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener detectorRemoteExceptionListener, int i2) {
        this.mAttentionManagerInternal = null;
        this.mDebugHotwordLogging = false;
        this.mRemoteExceptionListener = detectorRemoteExceptionListener;
        this.mRemoteDetectionService = serviceConnection;
        this.mLock = obj;
        this.mContext = context;
        this.mToken = iBinder;
        this.mUserId = i2;
        this.mCallback = iHotwordRecognitionStatusCallback;
        this.mVoiceInteractionServiceUid = i;
        this.mVoiceInteractorIdentity = identity;
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        if (getDetectorType() != 3) {
            this.mHotwordAudioStreamCopier = new com.android.server.voiceinteraction.HotwordAudioStreamCopier(this.mAppOpsManager, getDetectorType(), this.mVoiceInteractorIdentity.uid, this.mVoiceInteractorIdentity.packageName, this.mVoiceInteractorIdentity.attributionTag);
        } else {
            this.mHotwordAudioStreamCopier = null;
        }
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mDebugHotwordLogging = z;
        this.mAttentionManagerInternal = (android.attention.AttentionManagerInternal) com.android.server.LocalServices.getService(android.attention.AttentionManagerInternal.class);
        if (this.mAttentionManagerInternal != null && this.mAttentionManagerInternal.isProximitySupported()) {
            this.mAttentionManagerInternal.onStartProximityUpdates(this.mProximityCallbackInternal);
        }
    }

    void notifyOnDetectorRemoteException() {
        android.util.Slog.d(TAG, "notifyOnDetectorRemoteException: mRemoteExceptionListener=" + this.mRemoteExceptionListener);
        if (this.mRemoteExceptionListener != null) {
            this.mRemoteExceptionListener.onDetectorRemoteException(this.mToken, getDetectorType());
        }
    }

    private void updateStateAfterProcessStartLocked(final android.os.PersistableBundle persistableBundle, final android.os.SharedMemory sharedMemory) {
        if (this.mRemoteDetectionService.postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda1
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$updateStateAfterProcessStartLocked$0;
                lambda$updateStateAfterProcessStartLocked$0 = com.android.server.voiceinteraction.DetectorSession.this.lambda$updateStateAfterProcessStartLocked$0(persistableBundle, sharedMemory, (android.service.voice.ISandboxedDetectionService) obj);
                return lambda$updateStateAfterProcessStartLocked$0;
            }
        }).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.voiceinteraction.DetectorSession.this.lambda$updateStateAfterProcessStartLocked$1((java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        }) == null) {
            android.util.Slog.w(TAG, "Failed to create AndroidFuture");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$updateStateAfterProcessStartLocked$0(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        final com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        try {
            iSandboxedDetectionService.updateState(persistableBundle, sharedMemory, new android.os.IRemoteCallback.Stub() { // from class: com.android.server.voiceinteraction.DetectorSession.1
                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                    androidFuture.complete((java.lang.Object) null);
                    if (com.android.server.voiceinteraction.DetectorSession.this.mUpdateStateAfterStartFinished.getAndSet(true)) {
                        android.util.Slog.w(com.android.server.voiceinteraction.DetectorSession.TAG, "call callback after timeout");
                        if (com.android.server.voiceinteraction.DetectorSession.this.getDetectorType() != 3) {
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), 5, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                            return;
                        }
                        return;
                    }
                    android.util.Pair initStatusAndMetricsResult = com.android.server.voiceinteraction.DetectorSession.getInitStatusAndMetricsResult(bundle);
                    int intValue = ((java.lang.Integer) initStatusAndMetricsResult.first).intValue();
                    int intValue2 = ((java.lang.Integer) initStatusAndMetricsResult.second).intValue();
                    try {
                        com.android.server.voiceinteraction.DetectorSession.this.mCallback.onStatusReported(intValue);
                        if (com.android.server.voiceinteraction.DetectorSession.this.getDetectorType() != 3) {
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeServiceInitResultEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), intValue2, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.voiceinteraction.DetectorSession.TAG, "Failed to report initialization status: " + e);
                        if (com.android.server.voiceinteraction.DetectorSession.this.getDetectorType() != 3) {
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), 14, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                        }
                        com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                    }
                }
            });
            if (getDetectorType() != 3) {
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 4, this.mVoiceInteractionServiceUid);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to updateState for HotwordDetectionService", e);
            if (getDetectorType() != 3) {
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 19, this.mVoiceInteractionServiceUid);
            }
        }
        return androidFuture.orTimeout(30000L, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStateAfterProcessStartLocked$1(java.lang.Void r4, java.lang.Throwable th) {
        if (th instanceof java.util.concurrent.TimeoutException) {
            android.util.Slog.w(TAG, "updateState timed out");
            if (this.mUpdateStateAfterStartFinished.getAndSet(true)) {
                return;
            }
            try {
                this.mCallback.onStatusReported(100);
                if (getDetectorType() != 3) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeServiceInitResultEvent(getDetectorType(), 4, this.mVoiceInteractionServiceUid);
                    return;
                }
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to report initialization status UNKNOWN", e);
                if (getDetectorType() != 3) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 14, this.mVoiceInteractionServiceUid);
                }
                notifyOnDetectorRemoteException();
                return;
            }
        }
        if (th != null) {
            android.util.Slog.w(TAG, "Failed to update state: " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getInitStatusAndMetricsResult(android.os.Bundle bundle) {
        int i;
        int i2 = 2;
        if (bundle == null) {
            return new android.util.Pair<>(100, 2);
        }
        int i3 = bundle.getInt("initialization_status", 100);
        if (i3 > android.service.voice.HotwordDetectionService.getMaxCustomInitializationStatus()) {
            if (i3 != 100) {
                i2 = 3;
            }
            return new android.util.Pair<>(100, java.lang.Integer.valueOf(i2));
        }
        if (i3 == 0) {
            i = 0;
        } else {
            i = 1;
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i));
    }

    void updateStateLocked(final android.os.PersistableBundle persistableBundle, final android.os.SharedMemory sharedMemory, java.time.Instant instant) {
        if (getDetectorType() != 3) {
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 8, this.mVoiceInteractionServiceUid);
        }
        if (!this.mUpdateStateAfterStartFinished.get() && java.time.Instant.now().minus((java.time.temporal.TemporalAmount) MAX_UPDATE_TIMEOUT_DURATION).isBefore(instant)) {
            android.util.Slog.v(TAG, "call updateStateAfterProcessStartLocked");
            updateStateAfterProcessStartLocked(persistableBundle, sharedMemory);
        } else {
            this.mRemoteDetectionService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda6
                public final void runNoResult(java.lang.Object obj) {
                    ((android.service.voice.ISandboxedDetectionService) obj).updateState(persistableBundle, sharedMemory, null);
                }
            });
        }
    }

    void startListeningFromExternalSourceLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        handleExternalSourceHotwordDetectionLocked(parcelFileDescriptor, audioFormat, persistableBundle, iMicrophoneHotwordDetectionVoiceInteractionCallback, true, true);
    }

    void startListeningFromWearableLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, final android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback wearableHotwordDetectionCallback) {
        handleExternalSourceHotwordDetectionLocked(parcelFileDescriptor, audioFormat, persistableBundle, new android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback() { // from class: com.android.server.voiceinteraction.DetectorSession.2
            public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.media.AudioFormat audioFormat2, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
                wearableHotwordDetectionCallback.onDetected();
                try {
                    com.android.server.voiceinteraction.DetectorSession.this.mCallback.onKeyphraseDetectedFromExternalSource(hotwordDetectedResult);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.voiceinteraction.DetectorSession.TAG, "RemoteException when sending HotwordDetectedResult to VoiceInteractionService.", e);
                    wearableHotwordDetectionCallback.onError("RemoteException when sending HotwordDetectedResult to VoiceInteractionService.");
                    com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                }
                java.util.Iterator it = hotwordDetectedResult.getAudioStreams().iterator();
                while (it.hasNext()) {
                    try {
                        ((android.service.voice.HotwordAudioStream) it.next()).getAudioStreamParcelFileDescriptor().close();
                    } catch (java.io.IOException e2) {
                        android.util.Slog.i(com.android.server.voiceinteraction.DetectorSession.TAG, "Unable to close audio stream parcel file descriptor,", e2);
                    }
                }
            }

            public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
                wearableHotwordDetectionCallback.onError("onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            }

            public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
                wearableHotwordDetectionCallback.onRejected();
            }

            public android.os.IBinder asBinder() {
                return null;
            }
        }, false, false);
    }

    private void handleExternalSourceHotwordDetectionLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, final android.media.AudioFormat audioFormat, @android.annotation.Nullable final android.os.PersistableBundle persistableBundle, final android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback, final boolean z, final boolean z2) {
        if (this.mPerformingExternalSourceHotwordDetection) {
            android.util.Slog.i(TAG, "Hotword validation is already in progress for external source.");
            return;
        }
        final android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe = createPipe();
        if (createPipe == null) {
            return;
        }
        final android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) createPipe.second;
        final android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) createPipe.first;
        this.mCurrentAudioSink = parcelFileDescriptor2;
        this.mPerformingExternalSourceHotwordDetection = true;
        this.mAudioCopyExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.DetectorSession.this.lambda$handleExternalSourceHotwordDetectionLocked$3(autoCloseInputStream, parcelFileDescriptor2, iMicrophoneHotwordDetectionVoiceInteractionCallback);
            }
        });
        this.mRemoteDetectionService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda4
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.voiceinteraction.DetectorSession.this.lambda$handleExternalSourceHotwordDetectionLocked$4(persistableBundle, z, parcelFileDescriptor3, audioFormat, parcelFileDescriptor2, autoCloseInputStream, iMicrophoneHotwordDetectionVoiceInteractionCallback, z2, (android.service.voice.ISandboxedDetectionService) obj);
            }
        });
        com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 10, this.mVoiceInteractionServiceUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleExternalSourceHotwordDetectionLocked$3(java.io.InputStream inputStream, android.os.ParcelFileDescriptor parcelFileDescriptor, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        try {
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed supplying audio data to validator", e);
            try {
                iMicrophoneHotwordDetectionVoiceInteractionCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(3, "Copy audio data failure for external source detection."));
            } catch (android.os.RemoteException e2) {
                android.util.Slog.w(TAG, "Failed to report onHotwordDetectionServiceFailure status: " + e2);
                if (getDetectorType() != 3) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 15, this.mVoiceInteractionServiceUid);
                }
                notifyOnDetectorRemoteException();
            }
            synchronized (this.mLock) {
                this.mPerformingExternalSourceHotwordDetection = false;
                closeExternalAudioStreamLocked("start external source");
            }
        }
        try {
            try {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr, 0, 1024);
                        if (read < 0) {
                            break;
                        } else {
                            autoCloseOutputStream.write(bArr, 0, read);
                        }
                    }
                    android.util.Slog.i(TAG, "Reached end of stream for external hotword");
                    autoCloseOutputStream.close();
                    inputStream.close();
                    synchronized (this.mLock) {
                        this.mPerformingExternalSourceHotwordDetection = false;
                        closeExternalAudioStreamLocked("start external source");
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            synchronized (this.mLock) {
                this.mPerformingExternalSourceHotwordDetection = false;
                closeExternalAudioStreamLocked("start external source");
                throw th3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleExternalSourceHotwordDetectionLocked$4(android.os.PersistableBundle persistableBundle, boolean z, android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor2, java.io.InputStream inputStream, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback, boolean z2, android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        android.os.PersistableBundle persistableBundle2;
        if (android.app.wearable.Flags.enableHotwordWearableSensingApi()) {
            if (persistableBundle != null) {
                persistableBundle2 = persistableBundle;
            } else {
                persistableBundle2 = new android.os.PersistableBundle();
            }
            persistableBundle2.putBoolean("android.service.voice.HotwordDetectionService.KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK", z);
        } else {
            persistableBundle2 = persistableBundle;
        }
        iSandboxedDetectionService.detectFromMicrophoneSource(parcelFileDescriptor, 2, audioFormat, persistableBundle2, new com.android.server.voiceinteraction.DetectorSession.AnonymousClass3(parcelFileDescriptor2, inputStream, iMicrophoneHotwordDetectionVoiceInteractionCallback, z, z2));
        bestEffortClose(parcelFileDescriptor);
    }

    /* renamed from: com.android.server.voiceinteraction.DetectorSession$3, reason: invalid class name */
    class AnonymousClass3 extends android.service.voice.IDspHotwordDetectionCallback.Stub {
        final /* synthetic */ java.io.InputStream val$audioSource;
        final /* synthetic */ android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback val$callback;
        final /* synthetic */ android.os.ParcelFileDescriptor val$serviceAudioSink;
        final /* synthetic */ boolean val$shouldCheckPermissionsAndAppOpsOnDetected;
        final /* synthetic */ boolean val$shouldCloseAudioStreamWithDelayOnDetect;

        AnonymousClass3(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.InputStream inputStream, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback, boolean z, boolean z2) {
            this.val$serviceAudioSink = parcelFileDescriptor;
            this.val$audioSource = inputStream;
            this.val$callback = iMicrophoneHotwordDetectionVoiceInteractionCallback;
            this.val$shouldCloseAudioStreamWithDelayOnDetect = z;
            this.val$shouldCheckPermissionsAndAppOpsOnDetected = z2;
        }

        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
            synchronized (com.android.server.voiceinteraction.DetectorSession.this.mLock) {
                try {
                    com.android.server.voiceinteraction.DetectorSession.this.mPerformingExternalSourceHotwordDetection = false;
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), 12, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                    java.util.concurrent.ScheduledExecutorService scheduledExecutorService = com.android.server.voiceinteraction.DetectorSession.this.mScheduledExecutorService;
                    final android.os.ParcelFileDescriptor parcelFileDescriptor = this.val$serviceAudioSink;
                    final java.io.InputStream inputStream = this.val$audioSource;
                    scheduledExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.DetectorSession$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.voiceinteraction.DetectorSession.AnonymousClass3.lambda$onRejected$0(parcelFileDescriptor, inputStream);
                        }
                    }, com.android.server.voiceinteraction.DetectorSession.EXTERNAL_HOTWORD_CLEANUP_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
                    try {
                        this.val$callback.onRejected(hotwordRejectedResult);
                        if (hotwordRejectedResult != null) {
                            android.util.Slog.i(com.android.server.voiceinteraction.DetectorSession.TAG, "Egressed 'hotword rejected result' from hotword trusted process");
                            if (com.android.server.voiceinteraction.DetectorSession.this.mDebugHotwordLogging) {
                                android.util.Slog.i(com.android.server.voiceinteraction.DetectorSession.TAG, "Egressed detected result: " + hotwordRejectedResult);
                            }
                        }
                    } catch (android.os.RemoteException e) {
                        com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                        throw e;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onRejected$0(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.InputStream inputStream) {
            com.android.server.voiceinteraction.DetectorSession.bestEffortClose(parcelFileDescriptor, inputStream);
        }

        public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
            synchronized (com.android.server.voiceinteraction.DetectorSession.this.mLock) {
                try {
                    com.android.server.voiceinteraction.DetectorSession.this.mPerformingExternalSourceHotwordDetection = false;
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), 11, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                    if (this.val$shouldCloseAudioStreamWithDelayOnDetect) {
                        java.util.concurrent.ScheduledExecutorService scheduledExecutorService = com.android.server.voiceinteraction.DetectorSession.this.mScheduledExecutorService;
                        final android.os.ParcelFileDescriptor parcelFileDescriptor = this.val$serviceAudioSink;
                        final java.io.InputStream inputStream = this.val$audioSource;
                        scheduledExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.DetectorSession$3$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.voiceinteraction.DetectorSession.AnonymousClass3.lambda$onDetected$1(parcelFileDescriptor, inputStream);
                            }
                        }, com.android.server.voiceinteraction.DetectorSession.EXTERNAL_HOTWORD_CLEANUP_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
                    }
                    if (this.val$shouldCheckPermissionsAndAppOpsOnDetected) {
                        try {
                            com.android.server.voiceinteraction.DetectorSession.this.enforcePermissionsForDataDelivery();
                        } catch (java.lang.SecurityException e) {
                            android.util.Slog.w(com.android.server.voiceinteraction.DetectorSession.TAG, "Ignoring #onDetected due to a SecurityException", e);
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.DetectorSession.this.getDetectorType(), 13, com.android.server.voiceinteraction.DetectorSession.this.mVoiceInteractionServiceUid);
                            try {
                                this.val$callback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(5, "Security exception occurs in #onDetected method"));
                                return;
                            } catch (android.os.RemoteException e2) {
                                com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                                throw e2;
                            }
                        }
                    }
                    try {
                        android.service.voice.HotwordDetectedResult startCopyingAudioStreams = com.android.server.voiceinteraction.DetectorSession.this.mHotwordAudioStreamCopier.startCopyingAudioStreams(hotwordDetectedResult, this.val$shouldCheckPermissionsAndAppOpsOnDetected);
                        try {
                            this.val$callback.onDetected(startCopyingAudioStreams, (android.media.AudioFormat) null, (android.os.ParcelFileDescriptor) null);
                            android.util.Slog.i(com.android.server.voiceinteraction.DetectorSession.TAG, "Egressed " + android.service.voice.HotwordDetectedResult.getUsageSize(startCopyingAudioStreams) + " bits from hotword trusted process");
                            if (com.android.server.voiceinteraction.DetectorSession.this.mDebugHotwordLogging) {
                                android.util.Slog.i(com.android.server.voiceinteraction.DetectorSession.TAG, "Egressed detected result: " + startCopyingAudioStreams);
                            }
                        } catch (android.os.RemoteException e3) {
                            com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                            throw e3;
                        }
                    } catch (java.io.IOException e4) {
                        android.util.Slog.w(com.android.server.voiceinteraction.DetectorSession.TAG, "Ignoring #onDetected due to a IOException", e4);
                        try {
                            this.val$callback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(6, "Copy audio stream failure."));
                        } catch (android.os.RemoteException e5) {
                            com.android.server.voiceinteraction.DetectorSession.this.notifyOnDetectorRemoteException();
                            throw e5;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onDetected$1(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.InputStream inputStream) {
            com.android.server.voiceinteraction.DetectorSession.bestEffortClose(parcelFileDescriptor, inputStream);
        }
    }

    void initialize(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.SharedMemory sharedMemory) {
        synchronized (this.mLock) {
            try {
                if (this.mInitialized || this.mDestroyed) {
                    return;
                }
                updateStateAfterProcessStartLocked(persistableBundle, sharedMemory);
                this.mInitialized = true;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void destroyLocked() {
        this.mDestroyed = true;
        this.mDebugHotwordLogging = false;
        this.mRemoteDetectionService = null;
        this.mRemoteExceptionListener = null;
        if (this.mAttentionManagerInternal != null) {
            this.mAttentionManagerInternal.onStopProximityUpdates(this.mProximityCallbackInternal);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDebugHotwordLoggingLocked(boolean z) {
        android.util.Slog.v(TAG, "setDebugHotwordLoggingLocked: " + z);
        this.mDebugHotwordLogging = z;
    }

    void updateRemoteSandboxedDetectionServiceLocked(@android.annotation.NonNull com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection) {
        this.mRemoteDetectionService = serviceConnection;
    }

    private void reportErrorGetRemoteException() {
        if (getDetectorType() != 3) {
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 15, this.mVoiceInteractionServiceUid);
        }
        notifyOnDetectorRemoteException();
    }

    void reportErrorLocked(@android.annotation.NonNull android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
        try {
            this.mCallback.onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to call onHotwordDetectionServiceFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    void reportErrorLocked(@android.annotation.NonNull android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
        try {
            this.mCallback.onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to call onVisualQueryDetectionServiceFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    void reportErrorLocked(@android.annotation.NonNull java.lang.String str) {
        try {
            this.mCallback.onUnknownFailure(str);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to call onUnknownFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    boolean isSameCallback(@android.annotation.Nullable com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                if (iHotwordRecognitionStatusCallback == null) {
                    return false;
                }
                return this.mCallback.asBinder().equals(iHotwordRecognitionStatusCallback.asBinder());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isSameToken(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                if (iBinder == null) {
                    return false;
                }
                return this.mToken == iBinder;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    private static android.util.Pair<android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor> createPipe() {
        try {
            android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
            return android.util.Pair.create(createPipe[0], createPipe[1]);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to create audio stream pipe", e);
            return null;
        }
    }

    void saveProximityValueToBundle(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        synchronized (this.mLock) {
            if (hotwordDetectedResult != null) {
                try {
                    if (this.mProximityMeters != -1.0d) {
                        hotwordDetectedResult.setProximity(this.mProximityMeters);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProximityValue(double d) {
        synchronized (this.mLock) {
            this.mProximityMeters = d;
        }
    }

    void closeExternalAudioStreamLocked(java.lang.String str) {
        if (this.mCurrentAudioSink != null) {
            android.util.Slog.i(TAG, "Closing external audio stream to hotword detector: " + str);
            bestEffortClose(this.mCurrentAudioSink);
            this.mCurrentAudioSink = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void bestEffortClose(java.io.Closeable... closeableArr) {
        for (java.io.Closeable closeable : closeableArr) {
            bestEffortClose(closeable);
        }
    }

    private static void bestEffortClose(java.io.Closeable closeable) {
        try {
            closeable.close();
        } catch (java.io.IOException e) {
        }
    }

    void enforcePermissionsForDataDelivery() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda5
            public final void runOrThrow() {
                com.android.server.voiceinteraction.DetectorSession.this.lambda$enforcePermissionsForDataDelivery$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enforcePermissionsForDataDelivery$5() throws java.lang.Exception {
        synchronized (this.mLock) {
            try {
                if (com.android.server.policy.AppOpsPolicy.isHotwordDetectionServiceRequired(this.mContext.getPackageManager())) {
                    if (android.content.PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.RECORD_AUDIO", -1, this.mVoiceInteractorIdentity.uid, this.mVoiceInteractorIdentity.packageName) != 0) {
                        throw new java.lang.SecurityException("Failed to obtain permission RECORD_AUDIO for identity " + this.mVoiceInteractorIdentity);
                    }
                    int unsafeCheckOpNoThrow = this.mAppOpsManager.unsafeCheckOpNoThrow(android.app.AppOpsManager.opToPublicName(com.android.server.policy.AppOpsPolicy.getVoiceActivationOp()), this.mVoiceInteractorIdentity.uid, this.mVoiceInteractorIdentity.packageName);
                    if (unsafeCheckOpNoThrow == 3 || unsafeCheckOpNoThrow == 0) {
                        this.mAppOpsManager.noteOpNoThrow(com.android.server.policy.AppOpsPolicy.getVoiceActivationOp(), this.mVoiceInteractorIdentity.uid, this.mVoiceInteractorIdentity.packageName, this.mVoiceInteractorIdentity.attributionTag, HOTWORD_DETECTION_OP_MESSAGE);
                    } else {
                        throw new java.lang.SecurityException("The app op OP_RECEIVE_SANDBOX_TRIGGER_AUDIO is denied for identity" + this.mVoiceInteractorIdentity);
                    }
                } else {
                    enforcePermissionForDataDelivery(this.mContext, this.mVoiceInteractorIdentity, "android.permission.RECORD_AUDIO", HOTWORD_DETECTION_OP_MESSAGE);
                }
                enforcePermissionForDataDelivery(this.mContext, this.mVoiceInteractorIdentity, "android.permission.CAPTURE_AUDIO_HOTWORD", HOTWORD_DETECTION_OP_MESSAGE);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void enforcePermissionForDataDelivery(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (android.media.permission.PermissionUtil.checkPermissionForDataDelivery(context, identity, str, str2) != 0) {
            throw new java.lang.SecurityException(android.text.TextUtils.formatSimple("Failed to obtain permission %s for identity %s", new java.lang.Object[]{str, identity}));
        }
    }

    @android.annotation.RequiresPermission(allOf = {"android.permission.READ_COMPAT_CHANGE_CONFIG", "android.permission.LOG_COMPAT_CHANGE"})
    void enforceExtraKeyphraseIdNotLeaked(android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
        if (!android.app.compat.CompatChanges.isChangeEnabled(com.android.server.voiceinteraction.HotwordDetectionConnection.ENFORCE_HOTWORD_PHRASE_ID, this.mVoiceInteractionServiceUid)) {
            return;
        }
        for (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra : keyphraseRecognitionEvent.keyphraseExtras) {
            if (keyphraseRecognitionExtra.getKeyphraseId() == hotwordDetectedResult.getHotwordPhraseId()) {
                return;
            }
        }
        throw new java.lang.SecurityException("Ignoring #onDetected due to trusted service sharing a keyphrase ID which the DSP did not detect");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDetectorType() {
        if (this instanceof com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) {
            return 1;
        }
        if (this instanceof com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession) {
            return 2;
        }
        if (this instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession) {
            return 3;
        }
        android.util.Slog.v(TAG, "Unexpected detector type");
        return -1;
    }

    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mCallback=");
        printWriter.println(this.mCallback);
        printWriter.print(str);
        printWriter.print("mUpdateStateAfterStartFinished=");
        printWriter.println(this.mUpdateStateAfterStartFinished);
        printWriter.print(str);
        printWriter.print("mInitialized=");
        printWriter.println(this.mInitialized);
        printWriter.print(str);
        printWriter.print("mDestroyed=");
        printWriter.println(this.mDestroyed);
        printWriter.print(str);
        printWriter.print("DetectorType=");
        printWriter.println(android.service.voice.HotwordDetector.detectorTypeToString(getDetectorType()));
        printWriter.print(str);
        printWriter.print("mPerformingExternalSourceHotwordDetection=");
        printWriter.println(this.mPerformingExternalSourceHotwordDetection);
    }
}
