package android.service.voice;

/* loaded from: classes3.dex */
abstract class AbstractDetector implements android.service.voice.HotwordDetector {
    private static final boolean DEBUG = false;
    static final boolean IS_IDENTITY_WITH_ATTRIBUTION_TAG = false;
    private static final java.lang.String TAG = android.service.voice.AbstractDetector.class.getSimpleName();
    private final android.service.voice.HotwordDetector.Callback mCallback;
    private final java.util.concurrent.Executor mExecutor;
    private final java.util.concurrent.atomic.AtomicBoolean mIsDetectorActive;
    private final com.android.internal.app.IVoiceInteractionManagerService mManagerService;
    private java.util.function.Consumer<android.service.voice.AbstractDetector> mOnDestroyListener;
    protected final java.lang.Object mLock = new java.lang.Object();
    private final android.os.IBinder mToken = new android.os.Binder();

    abstract void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory);

    AbstractDetector(com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService, java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback) {
        this.mManagerService = iVoiceInteractionManagerService;
        this.mCallback = callback;
        this.mExecutor = executor == null ? new android.os.HandlerExecutor(new android.os.Handler(android.os.Looper.getMainLooper())) : executor;
        this.mIsDetectorActive = new java.util.concurrent.atomic.AtomicBoolean(true);
    }

    boolean isSameToken(android.os.IBinder iBinder) {
        return iBinder != null && this.mToken == iBinder;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle) {
        throwIfDetectorIsNoLongerActive();
        try {
            this.mManagerService.startListeningFromExternalSource(parcelFileDescriptor, audioFormat, persistableBundle, this.mToken, new android.service.voice.AbstractDetector.BinderCallback(this.mExecutor, this.mCallback));
            return true;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // android.service.voice.HotwordDetector
    public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
        throwIfDetectorIsNoLongerActive();
        try {
            this.mManagerService.updateState(persistableBundle, sharedMemory, this.mToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void initAndVerifyDetector(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, java.lang.String str) {
        android.media.permission.Identity identity = new android.media.permission.Identity();
        identity.packageName = android.app.ActivityThread.currentOpPackageName();
        try {
            this.mManagerService.initAndVerifyDetector(identity, persistableBundle, sharedMemory, this.mToken, iHotwordRecognitionStatusCallback, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void registerOnDestroyListener(java.util.function.Consumer<android.service.voice.AbstractDetector> consumer) {
        synchronized (this.mLock) {
            if (this.mOnDestroyListener != null) {
                throw new java.lang.IllegalStateException("only one destroy listener can be registered");
            }
            this.mOnDestroyListener = consumer;
        }
    }

    @Override // android.service.voice.HotwordDetector
    public void destroy() {
        java.util.function.Consumer<android.service.voice.AbstractDetector> consumer;
        if (!this.mIsDetectorActive.get()) {
            return;
        }
        this.mIsDetectorActive.set(false);
        try {
            this.mManagerService.destroyDetector(this.mToken);
            synchronized (this.mLock) {
                consumer = this.mOnDestroyListener;
            }
            if (consumer != null) {
                consumer.accept(this);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void throwIfDetectorIsNoLongerActive() {
        if (!this.mIsDetectorActive.get()) {
            android.util.Slog.e(TAG, "attempting to use a destroyed detector which is no longer active");
            throw new java.lang.IllegalStateException("attempting to use a destroyed detector which is no longer active");
        }
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
        public /* synthetic */ void lambda$onDetected$1(final android.media.AudioFormat audioFormat, final android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onDetected$0(audioFormat, hotwordDetectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onDetected(final android.service.voice.HotwordDetectedResult hotwordDetectedResult, final android.media.AudioFormat audioFormat, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onDetected$1(audioFormat, hotwordDetectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetected$0(android.media.AudioFormat audioFormat, android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
            this.mCallback.onDetected(new android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder().setCaptureAudioFormat(audioFormat).setHotwordDetectedResult(hotwordDetectedResult).build());
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onHotwordDetectionServiceFailure(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            android.util.Slog.v(android.service.voice.AbstractDetector.TAG, "BinderCallback#onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$3(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$3(final android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$2(hotwordDetectionServiceFailure);
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
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onRejected$4(hotwordRejectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onRejected(final android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.AbstractDetector.BinderCallback.this.lambda$onRejected$5(hotwordRejectedResult);
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
}
