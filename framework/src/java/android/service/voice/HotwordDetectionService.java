package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class HotwordDetectionService extends android.app.Service implements android.service.voice.SandboxedDetectionInitializer {
    public static final int AUDIO_SOURCE_EXTERNAL = 2;
    public static final int AUDIO_SOURCE_MICROPHONE = 1;
    private static final boolean DBG = false;
    public static final boolean ENABLE_PROXIMITY_RESULT = true;

    @java.lang.Deprecated
    public static final int INITIALIZATION_STATUS_SUCCESS = 0;

    @java.lang.Deprecated
    public static final int INITIALIZATION_STATUS_UNKNOWN = 100;
    public static final java.lang.String KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK = "android.service.voice.HotwordDetectionService.KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.voice.HotwordDetectionService";
    private static final java.lang.String TAG = "HotwordDetectionService";
    private static final long UPDATE_TIMEOUT_MILLIS = 20000;
    private android.view.contentcapture.ContentCaptureManager mContentCaptureManager;
    private android.speech.IRecognitionServiceManager mIRecognitionServiceManager;
    private final android.service.voice.ISandboxedDetectionService mInterface = new android.service.voice.ISandboxedDetectionService.Stub() { // from class: android.service.voice.HotwordDetectionService.1
        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.media.AudioFormat audioFormat, long j, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
            android.service.voice.HotwordDetectionService.this.onDetect(new android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder(keyphraseRecognitionEvent).build(), j, new android.service.voice.HotwordDetectionService.Callback(iDspHotwordDetectionCallback));
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            android.util.Log.v(android.service.voice.HotwordDetectionService.TAG, "#updateState" + (iRemoteCallback != null ? " with callback" : ""));
            android.service.voice.HotwordDetectionService.this.onUpdateStateInternal(persistableBundle, sharedMemory, iRemoteCallback);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectFromMicrophoneSource(android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    android.service.voice.HotwordDetectionService.this.onDetect(new android.service.voice.HotwordDetectionService.Callback(iDspHotwordDetectionCallback));
                    break;
                case 2:
                    android.service.voice.HotwordDetectionService.this.onDetect(parcelFileDescriptor, audioFormat, persistableBundle, new android.service.voice.HotwordDetectionService.Callback(iDspHotwordDetectionCallback));
                    break;
                default:
                    android.util.Log.i(android.service.voice.HotwordDetectionService.TAG, "Unsupported audio source " + i);
                    break;
            }
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void detectWithVisualSignals(android.service.voice.IDetectorSessionVisualQueryDetectionCallback iDetectorSessionVisualQueryDetectionCallback) {
            throw new java.lang.UnsupportedOperationException("Not supported by HotwordDetectionService");
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateAudioFlinger(android.os.IBinder iBinder) {
            android.media.AudioSystem.setAudioFlingerBinder(iBinder);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateContentCaptureManager(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) {
            android.service.voice.HotwordDetectionService.this.mContentCaptureManager = new android.view.contentcapture.ContentCaptureManager(android.service.voice.HotwordDetectionService.this, iContentCaptureManager, contentCaptureOptions);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void updateRecognitionServiceManager(android.speech.IRecognitionServiceManager iRecognitionServiceManager) {
            android.service.voice.HotwordDetectionService.this.mIRecognitionServiceManager = iRecognitionServiceManager;
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void ping(android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
            iRemoteCallback.sendResult(null);
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void stopDetection() {
            android.service.voice.HotwordDetectionService.this.onStopDetection();
        }

        @Override // android.service.voice.ISandboxedDetectionService
        public void registerRemoteStorageService(android.service.voice.IDetectorSessionStorageService iDetectorSessionStorageService) {
            throw new java.lang.UnsupportedOperationException("Hotword cannot access files from the disk.");
        }
    };

    @java.lang.annotation.Documented
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AudioSource {
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.voice.HotwordDetectionService: " + intent);
        return null;
    }

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

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static int getMaxCustomInitializationStatus() {
        return 2;
    }

    @android.annotation.SystemApi
    public void onDetect(android.service.voice.AlwaysOnHotwordDetector.EventPayload eventPayload, long j, android.service.voice.HotwordDetectionService.Callback callback) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // android.service.voice.SandboxedDetectionInitializer
    @android.annotation.SystemApi
    public void onUpdateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, long j, java.util.function.IntConsumer intConsumer) {
    }

    public void onDetect(android.service.voice.HotwordDetectionService.Callback callback) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void onDetect(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.HotwordDetectionService.Callback callback) {
        throw new java.lang.UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateStateInternal(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.IRemoteCallback iRemoteCallback) {
        onUpdateState(persistableBundle, sharedMemory, UPDATE_TIMEOUT_MILLIS, android.service.voice.SandboxedDetectionInitializer.createInitializationStatusConsumer(iRemoteCallback));
    }

    public void onStopDetection() {
    }

    @android.annotation.SystemApi
    public static final class Callback {
        private final android.service.voice.IDspHotwordDetectionCallback mRemoteCallback;

        private Callback(android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback) {
            this.mRemoteCallback = iDspHotwordDetectionCallback;
        }

        public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
            java.util.Objects.requireNonNull(hotwordDetectedResult);
            android.os.PersistableBundle extras = hotwordDetectedResult.getExtras();
            if (!extras.isEmpty() && android.service.voice.HotwordDetectedResult.getParcelableSize(extras) > android.service.voice.HotwordDetectedResult.getMaxBundleSize()) {
                throw new java.lang.IllegalArgumentException("The bundle size of result is larger than max bundle size (" + android.service.voice.HotwordDetectedResult.getMaxBundleSize() + ") of HotwordDetectedResult");
            }
            try {
                this.mRemoteCallback.onDetected(hotwordDetectedResult);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
            java.util.Objects.requireNonNull(hotwordRejectedResult);
            try {
                this.mRemoteCallback.onRejected(hotwordRejectedResult);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
