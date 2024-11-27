package android.media.musicrecognition;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class MusicRecognitionService extends android.app.Service {
    public static final java.lang.String ACTION_MUSIC_SEARCH_LOOKUP = "android.service.musicrecognition.MUSIC_RECOGNITION";
    private static final java.lang.String TAG = android.media.musicrecognition.MusicRecognitionService.class.getSimpleName();
    private android.os.Handler mHandler;
    private final android.media.musicrecognition.IMusicRecognitionService mServiceInterface = new android.media.musicrecognition.IMusicRecognitionService.Stub() { // from class: android.media.musicrecognition.MusicRecognitionService.1
        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void onAudioStreamStarted(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, final android.media.musicrecognition.IMusicRecognitionServiceCallback iMusicRecognitionServiceCallback) {
            android.os.Handler handler = android.media.musicrecognition.MusicRecognitionService.this.mHandler;
            final android.media.musicrecognition.MusicRecognitionService musicRecognitionService = android.media.musicrecognition.MusicRecognitionService.this;
            handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.musicrecognition.MusicRecognitionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    android.media.musicrecognition.MusicRecognitionService.this.onRecognize((android.os.ParcelFileDescriptor) obj, (android.media.AudioFormat) obj2, (android.media.musicrecognition.MusicRecognitionService.AnonymousClass1.C00041) obj3);
                }
            }, parcelFileDescriptor, audioFormat, new android.media.musicrecognition.MusicRecognitionService.Callback() { // from class: android.media.musicrecognition.MusicRecognitionService.1.1
                @Override // android.media.musicrecognition.MusicRecognitionService.Callback
                public void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle) {
                    try {
                        iMusicRecognitionServiceCallback.onRecognitionSucceeded(mediaMetadata, bundle);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }

                @Override // android.media.musicrecognition.MusicRecognitionService.Callback
                public void onRecognitionFailed(int i) {
                    try {
                        iMusicRecognitionServiceCallback.onRecognitionFailed(i);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.media.musicrecognition.IMusicRecognitionService
        public void getAttributionTag(android.media.musicrecognition.IMusicRecognitionAttributionTagCallback iMusicRecognitionAttributionTagCallback) throws android.os.RemoteException {
            iMusicRecognitionAttributionTagCallback.onAttributionTag(android.media.musicrecognition.MusicRecognitionService.this.getAttributionTag());
        }
    };

    public interface Callback {
        void onRecognitionFailed(int i);

        void onRecognitionSucceeded(android.media.MediaMetadata mediaMetadata, android.os.Bundle bundle);
    }

    public abstract void onRecognize(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.media.musicrecognition.MusicRecognitionService.Callback callback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (ACTION_MUSIC_SEARCH_LOOKUP.equals(intent.getAction())) {
            return this.mServiceInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.musicrecognition.MUSIC_RECOGNITION: " + intent);
        return null;
    }
}
