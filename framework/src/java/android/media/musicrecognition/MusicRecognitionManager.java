package android.media.musicrecognition;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class MusicRecognitionManager {
    public static final int RECOGNITION_FAILED_AUDIO_UNAVAILABLE = 7;
    public static final int RECOGNITION_FAILED_NOT_FOUND = 1;
    public static final int RECOGNITION_FAILED_NO_CONNECTIVITY = 2;
    public static final int RECOGNITION_FAILED_SERVICE_KILLED = 5;
    public static final int RECOGNITION_FAILED_SERVICE_UNAVAILABLE = 3;
    public static final int RECOGNITION_FAILED_TIMEOUT = 6;
    public static final int RECOGNITION_FAILED_UNKNOWN = -1;
    private final android.media.musicrecognition.IMusicRecognitionManager mService;

    public interface RecognitionCallback {
        void onAudioStreamClosed();

        void onRecognitionFailed(android.media.musicrecognition.RecognitionRequest recognitionRequest, int i);

        void onRecognitionSucceeded(android.media.musicrecognition.RecognitionRequest recognitionRequest, android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionFailureCode {
    }

    public MusicRecognitionManager(android.media.musicrecognition.IMusicRecognitionManager iMusicRecognitionManager) {
        this.mService = iMusicRecognitionManager;
    }

    @android.annotation.SystemApi
    public void beginStreamingSearch(android.media.musicrecognition.RecognitionRequest recognitionRequest, java.util.concurrent.Executor executor, android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback recognitionCallback) {
        try {
            this.mService.beginRecognition((android.media.musicrecognition.RecognitionRequest) java.util.Objects.requireNonNull(recognitionRequest), new android.media.musicrecognition.MusicRecognitionManager.MusicRecognitionCallbackWrapper((android.media.musicrecognition.RecognitionRequest) java.util.Objects.requireNonNull(recognitionRequest), (android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback) java.util.Objects.requireNonNull(recognitionCallback), (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor)));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MusicRecognitionCallbackWrapper extends android.media.musicrecognition.IMusicRecognitionManagerCallback.Stub {
        private final android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback mCallback;
        private final java.util.concurrent.Executor mCallbackExecutor;
        private final android.media.musicrecognition.RecognitionRequest mRecognitionRequest;

        MusicRecognitionCallbackWrapper(android.media.musicrecognition.RecognitionRequest recognitionRequest, android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback recognitionCallback, java.util.concurrent.Executor executor) {
            this.mRecognitionRequest = recognitionRequest;
            this.mCallback = recognitionCallback;
            this.mCallbackExecutor = executor;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionSucceeded(final android.media.MediaMetadata mediaMetadata, final android.os.Bundle bundle) {
            this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.musicrecognition.MusicRecognitionManager.MusicRecognitionCallbackWrapper.this.lambda$onRecognitionSucceeded$0(mediaMetadata, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecognitionSucceeded$0(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) {
            this.mCallback.onRecognitionSucceeded(this.mRecognitionRequest, mediaMetadata, bundle);
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onRecognitionFailed(final int i) {
            this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.musicrecognition.MusicRecognitionManager.MusicRecognitionCallbackWrapper.this.lambda$onRecognitionFailed$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecognitionFailed$1(int i) {
            this.mCallback.onRecognitionFailed(this.mRecognitionRequest, i);
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManagerCallback
        public void onAudioStreamClosed() {
            java.util.concurrent.Executor executor = this.mCallbackExecutor;
            final android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback recognitionCallback = this.mCallback;
            java.util.Objects.requireNonNull(recognitionCallback);
            executor.execute(new java.lang.Runnable() { // from class: android.media.musicrecognition.MusicRecognitionManager$MusicRecognitionCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.musicrecognition.MusicRecognitionManager.RecognitionCallback.this.onAudioStreamClosed();
                }
            });
        }
    }
}
