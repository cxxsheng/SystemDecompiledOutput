package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class VisualQueryDetectionService extends android.app.Service implements android.service.voice.SandboxedDetectionInitializer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final java.lang.String KEY_INITIALIZATION_STATUS = "initialization_status";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.voice.VisualQueryDetectionService";
    private static final java.lang.String TAG = android.service.voice.VisualQueryDetectionService.class.getSimpleName();
    private static final long UPDATE_TIMEOUT_MILLIS = 20000;
    private android.view.contentcapture.ContentCaptureManager mContentCaptureManager;
    private android.service.voice.IDetectorSessionStorageService mDetectorSessionStorageService;
    private android.speech.IRecognitionServiceManager mIRecognitionServiceManager;
    private android.service.voice.IDetectorSessionVisualQueryDetectionCallback mRemoteCallback = null;
    private final android.service.voice.ISandboxedDetectionService mInterface = new android.service.voice.ISandboxedDetectionService.Stub() { // from class: android.service.voice.VisualQueryDetectionService.1
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(android.service.voice.IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) {
            android.util.Log.v(android.service.voice.VisualQueryDetectionService.TAG, "#detectWithVisualSignals");
            android.service.voice.VisualQueryDetectionService.this.mRemoteCallback = iDetectorSessionVisualQueryDetectionCallback;
            android.service.voice.VisualQueryDetectionService.this.onStartDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() {
            android.util.Log.v(android.service.voice.VisualQueryDetectionService.TAG, "#stopDetection");
            android.service.voice.VisualQueryDetectionService.this.onStopDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            android.util.Log.v(android.service.voice.VisualQueryDetectionService.TAG, "#updateState" + (iRemoteCallback != null ? " with callback" : ""));
            android.service.voice.VisualQueryDetectionService.this.onUpdateStateInternal(persistableBundle, sharedMemory, iRemoteCallback);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            iRemoteCallback.sendResult(null);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.media.AudioFormat audioFormat, long j, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            throw new java.lang.UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            throw new java.lang.UnsupportedOperationException("Not supported by VisualQueryDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(android.os.IBinder iBinder) {
            android.media.AudioSystem.setAudioFlingerBinder(iBinder);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) {
            android.service.voice.VisualQueryDetectionService.this.mContentCaptureManager = new android.view.contentcapture.ContentCaptureManager(android.service.voice.VisualQueryDetectionService.this, iContentCaptureManager, contentCaptureOptions);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(android.speech.IRecognitionServiceManager iRecognitionServiceManager) {
            android.service.voice.VisualQueryDetectionService.this.mIRecognitionServiceManager = iRecognitionServiceManager;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(android.service.voice.IDetectorSessionStorageService iDetectorSessionStorageService) {
            android.service.voice.VisualQueryDetectionService.this.mDetectorSessionStorageService = iDetectorSessionStorageService;
        }
    };

    @Override // android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE.equals(str)) {
            return this.mContentCaptureManager;
        }
        if (android.content.Context.SPEECH_RECOGNITION_SERVICE.equals(str) && this.mIRecognitionServiceManager != null) {
            return this.mIRecognitionServiceManager.asBinder();
        }
        return super.getSystemService(str);
    }

    @Override // android.service.voice.SandboxedDetectionInitializer
    @android.annotation.SystemApi
    public void onUpdateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, long j, java.util.function.IntConsumer intConsumer) {
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.voice.VisualQueryDetectionService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateStateInternal(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) {
        onUpdateState(persistableBundle, sharedMemory, UPDATE_TIMEOUT_MILLIS, android.service.voice.SandboxedDetectionInitializer.createInitializationStatusConsumer(iRemoteCallback));
    }

    public void onStartDetection() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void onStopDetection() {
    }

    public final void gainedAttention() {
        if (android.service.voice.flags.Flags.allowVariousAttentionTypes()) {
            gainedAttention(new android.service.voice.VisualQueryAttentionResult.Builder().build());
            return;
        }
        try {
            this.mRemoteCallback.onAttentionGained(null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void gainedAttention(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) {
        try {
            this.mRemoteCallback.onAttentionGained(visualQueryAttentionResult);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void lostAttention() {
        if (android.service.voice.flags.Flags.allowVariousAttentionTypes()) {
            lostAttention(0);
            lostAttention(1);
        } else {
            try {
                this.mRemoteCallback.onAttentionLost(0);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void lostAttention(int i) {
        try {
            this.mRemoteCallback.onAttentionLost(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void streamQuery(java.lang.String str) throws java.lang.IllegalStateException {
        java.util.Objects.requireNonNull(str);
        try {
            this.mRemoteCallback.onQueryDetected(str);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void streamQuery(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) {
        java.util.Objects.requireNonNull(visualQueryDetectedResult);
        try {
            this.mRemoteCallback.onResultDetected(visualQueryDetectedResult);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("#streamQuery must be only be triggered after calling #gainedAttention to be in the attention gained state.");
        }
    }

    public final void rejectQuery() throws java.lang.IllegalStateException {
        try {
            this.mRemoteCallback.onQueryRejected();
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("#rejectQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    public final void finishQuery() throws java.lang.IllegalStateException {
        try {
            this.mRemoteCallback.onQueryFinished();
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("#finishQuery must be only be triggered after calling #streamQuery to be in the query streaming state.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContextWrapper, android.content.Context
    public java.io.FileInputStream openFileInput(java.lang.String str) throws java.io.FileNotFoundException {
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            this.mDetectorSessionStorageService.openFile(str, androidFuture);
            android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) androidFuture.get();
            if (parcelFileDescriptor == null) {
                throw new java.io.FileNotFoundException("File does not exist. Unable to open " + str + android.media.MediaMetrics.SEPARATOR);
            }
            return new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor());
        } catch (android.os.RemoteException | java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            android.util.Log.w(TAG, "Cannot open file due to remote service failure");
            throw new java.io.FileNotFoundException(e.getMessage());
        }
    }
}
