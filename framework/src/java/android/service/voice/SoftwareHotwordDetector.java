package android.service.voice;

/* loaded from: classes3.dex */
class SoftwareHotwordDetector extends android.service.voice.AbstractDetector {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.service.voice.SoftwareHotwordDetector.class.getSimpleName();
    private final java.lang.String mAttributionTag;
    private final android.media.AudioFormat mAudioFormat;
    private final android.service.voice.HotwordDetector.Callback mCallback;
    private final java.util.concurrent.Executor mExecutor;
    private final com.android.internal.app.IVoiceInteractionManagerService mManagerService;

    SoftwareHotwordDetector(com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService, android.media.AudioFormat audioFormat, java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback, java.lang.String str) {
        super(iVoiceInteractionManagerService, executor, callback);
        this.mManagerService = iVoiceInteractionManagerService;
        this.mAudioFormat = audioFormat;
        this.mCallback = callback;
        this.mExecutor = executor == null ? new android.os.HandlerExecutor(new android.os.Handler(android.os.Looper.getMainLooper())) : executor;
        this.mAttributionTag = str;
    }

    @Override // android.service.voice.AbstractDetector
    void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
        initAndVerifyDetector(persistableBundle, sharedMemory, new android.service.voice.SoftwareHotwordDetector.InitializationStateListener(this.mExecutor, this.mCallback), 2, this.mAttributionTag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetectorRemoteException$1() throws java.lang.Exception {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.voice.SoftwareHotwordDetector.this.lambda$onDetectorRemoteException$0();
            }
        });
    }

    void onDetectorRemoteException() {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                android.service.voice.SoftwareHotwordDetector.this.lambda$onDetectorRemoteException$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetectorRemoteException$0() {
        this.mCallback.onFailure(new android.service.voice.HotwordDetectionServiceFailure(7, "Detector remote exception occurs"));
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition() {
        throwIfDetectorIsNoLongerActive();
        maybeCloseExistingSession();
        try {
            this.mManagerService.startListeningFromMic(this.mAudioFormat, new android.service.voice.SoftwareHotwordDetector.BinderCallback(this.mExecutor, this.mCallback));
            return true;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return true;
        } catch (java.lang.SecurityException e2) {
            android.util.Slog.e(TAG, "startRecognition failed: " + e2);
            return false;
        }
    }

    @Override // android.service.voice.HotwordDetector
    public boolean stopRecognition() {
        throwIfDetectorIsNoLongerActive();
        try {
            this.mManagerService.stopListeningFromMic();
            return true;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public void destroy() {
        try {
            stopRecognition();
        } catch (java.lang.Exception e) {
            android.util.Log.i(TAG, "failed to stopRecognition in destroy", e);
        }
        maybeCloseExistingSession();
        super.destroy();
    }

    @Override // android.service.voice.HotwordDetector
    public boolean isUsingSandboxedDetectionService() {
        return true;
    }

    private void maybeCloseExistingSession() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BinderCallback extends android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub {
        private final android.service.voice.HotwordDetector.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        BinderCallback(java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetected$1(final android.media.AudioFormat audioFormat, final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onDetected$0(audioFormat, parcelFileDescriptor, hotwordDetectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onDetected(final android.service.voice.HotwordDetectedResult hotwordDetectedResult, final android.media.AudioFormat audioFormat, final android.os.ParcelFileDescriptor parcelFileDescriptor) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onDetected$1(audioFormat, parcelFileDescriptor, hotwordDetectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetected$0(android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor, android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
            this.mCallback.onDetected(new android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder().setCaptureAudioFormat(audioFormat).setAudioStream(parcelFileDescriptor).setHotwordDetectedResult(hotwordDetectedResult).build());
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onHotwordDetectionServiceFailure(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            android.util.Slog.v(android.service.voice.SoftwareHotwordDetector.TAG, "BinderCallback#onHotwordDetectionServiceFailure:" + hotwordDetectionServiceFailure);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$3(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$3(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$2(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$2(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            if (hotwordDetectionServiceFailure != null) {
                this.mCallback.onFailure(hotwordDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRejected$5(final android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onRejected$4(hotwordRejectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onRejected(final android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.BinderCallback.this.lambda$onRejected$5(hotwordRejectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRejected$4(android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
            android.service.voice.HotwordDetector.Callback callback = this.mCallback;
            if (hotwordRejectedResult == null) {
                hotwordRejectedResult = new android.service.voice.HotwordRejectedResult.Builder().build();
            }
            callback.onRejected(hotwordRejectedResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InitializationStateListener extends com.android.internal.app.IHotwordRecognitionStatusCallback.Stub {
        private final android.service.voice.HotwordDetector.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        InitializationStateListener(java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.SoftwareHotwordDetector.TAG, "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onHotwordDetectionServiceFailure$1(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$1(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onHotwordDetectionServiceFailure$0(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$0(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            if (hotwordDetectionServiceFailure != null) {
                this.mCallback.onFailure(hotwordDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
            android.util.Slog.w(android.service.voice.SoftwareHotwordDetector.TAG, "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) throws android.os.RemoteException {
            android.util.Slog.wtf(android.service.voice.SoftwareHotwordDetector.TAG, "Unexpected STFailure in software detector: " + soundTriggerFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(final java.lang.String str) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.SoftwareHotwordDetector.TAG, "onUnknownFailure: " + str);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onUnknownFailure$3(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$3(final java.lang.String str) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onUnknownFailure$2(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$2(java.lang.String str) {
            android.service.voice.HotwordDetector.Callback callback = this.mCallback;
            if (android.text.TextUtils.isEmpty(str)) {
                str = "Error data is null";
            }
            callback.onUnknownFailure(str);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(final int i) {
            android.util.Slog.v(android.service.voice.SoftwareHotwordDetector.TAG, "onStatusReported");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onStatusReported$5(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$5(final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onStatusReported$4(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$4(int i) {
            this.mCallback.onHotwordDetectionServiceInitialized(i);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.SoftwareHotwordDetector.TAG, "onProcessRestarted()");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onProcessRestarted$7();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessRestarted$7() throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.SoftwareHotwordDetector.InitializationStateListener.this.lambda$onProcessRestarted$6();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessRestarted$6() {
            this.mCallback.onHotwordDetectionServiceRestarted();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
            throw new java.lang.UnsupportedOperationException("Hotword cannot access files from the disk.");
        }
    }

    @Override // android.service.voice.HotwordDetector
    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
    }
}
