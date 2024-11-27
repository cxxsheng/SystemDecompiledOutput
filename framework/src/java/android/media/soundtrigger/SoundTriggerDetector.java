package android.media.soundtrigger;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class SoundTriggerDetector {
    private static final boolean DBG = false;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_SOUND_TRIGGER_DETECTED = 2;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_ECHO_CANCELLATION = 4;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_NOISE_SUPPRESSION = 8;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_FLAG_RUN_IN_BATTERY_SAVER = 16;
    private static final java.lang.String TAG = "SoundTriggerDetector";
    private final android.media.soundtrigger.SoundTriggerDetector.Callback mCallback;
    private final android.os.Handler mHandler;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.media.soundtrigger.SoundTriggerDetector.RecognitionCallback mRecognitionCallback;
    private final android.hardware.soundtrigger.SoundTrigger.GenericSoundModel mSoundModel;
    private final com.android.internal.app.ISoundTriggerSession mSoundTriggerSession;

    public static abstract class Callback {
        public abstract void onAvailabilityChanged(int i);

        public abstract void onDetected(android.media.soundtrigger.SoundTriggerDetector.EventPayload eventPayload);

        public abstract void onError();

        public abstract void onRecognitionPaused();

        public abstract void onRecognitionResumed();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionFlags {
    }

    public static class EventPayload {
        private final android.media.AudioFormat mAudioFormat;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final boolean mTriggerAvailable;

        private EventPayload(boolean z, boolean z2, android.media.AudioFormat audioFormat, int i, byte[] bArr) {
            this.mTriggerAvailable = z;
            this.mCaptureAvailable = z2;
            this.mCaptureSession = i;
            this.mAudioFormat = audioFormat;
            this.mData = bArr;
        }

        public android.media.AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        public byte[] getTriggerAudio() {
            if (this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }

        public byte[] getData() {
            if (!this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }

        public java.lang.Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return java.lang.Integer.valueOf(this.mCaptureSession);
            }
            return null;
        }
    }

    SoundTriggerDetector(com.android.internal.app.ISoundTriggerSession iSoundTriggerSession, android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.media.soundtrigger.SoundTriggerDetector.Callback callback, android.os.Handler handler) {
        this.mSoundTriggerSession = iSoundTriggerSession;
        this.mSoundModel = genericSoundModel;
        this.mCallback = callback;
        if (handler == null) {
            this.mHandler = new android.media.soundtrigger.SoundTriggerDetector.MyHandler();
        } else {
            this.mHandler = new android.media.soundtrigger.SoundTriggerDetector.MyHandler(handler.getLooper());
        }
        this.mRecognitionCallback = new android.media.soundtrigger.SoundTriggerDetector.RecognitionCallback();
    }

    @java.lang.Deprecated
    public boolean startRecognition(int i) {
        int i2;
        int i3;
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 16) != 0;
        if ((i & 4) == 0) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        if ((i & 8) == 0) {
            i3 = i2;
        } else {
            i3 = i2 | 2;
        }
        try {
            return this.mSoundTriggerSession.startRecognition(this.mSoundModel, this.mRecognitionCallback, new android.hardware.soundtrigger.SoundTrigger.RecognitionConfig(z, z2, null, null, i3), z3) == 0;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @java.lang.Deprecated
    public boolean stopRecognition() {
        try {
            return this.mSoundTriggerSession.stopRecognition(new android.os.ParcelUuid(this.mSoundModel.getUuid()), this.mRecognitionCallback) == 0;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
        }
    }

    private class RecognitionCallback extends android.hardware.soundtrigger.IRecognitionStatusCallback.Stub {
        private RecognitionCallback() {
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onGenericSoundTriggerDetected()" + genericRecognitionEvent);
            android.os.Message.obtain(android.media.soundtrigger.SoundTriggerDetector.this.mHandler, 2, new android.media.soundtrigger.SoundTriggerDetector.EventPayload(genericRecognitionEvent.triggerInData, genericRecognitionEvent.captureAvailable, genericRecognitionEvent.captureFormat, genericRecognitionEvent.captureSession, genericRecognitionEvent.data)).sendToTarget();
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            android.util.Slog.e(android.media.soundtrigger.SoundTriggerDetector.TAG, "Ignoring onKeyphraseDetected() called for " + keyphraseRecognitionEvent);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionPaused() {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onRecognitionPaused()");
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(4);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onRecognitionResumed() {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onRecognitionResumed()");
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(5);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPreempted() {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onPreempted()");
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onModuleDied() {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onModuleDied()");
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onResumeFailed(int i) {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onResumeFailed()" + i);
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.hardware.soundtrigger.IRecognitionStatusCallback
        public void onPauseFailed(int i) {
            android.util.Slog.d(android.media.soundtrigger.SoundTriggerDetector.TAG, "onPauseFailed()" + i);
            android.media.soundtrigger.SoundTriggerDetector.this.mHandler.sendEmptyMessage(3);
        }
    }

    private class MyHandler extends android.os.Handler {
        MyHandler() {
        }

        MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.media.soundtrigger.SoundTriggerDetector.this.mCallback == null) {
                android.util.Slog.w(android.media.soundtrigger.SoundTriggerDetector.TAG, "Received message: " + message.what + " for NULL callback.");
            }
            switch (message.what) {
                case 2:
                    android.media.soundtrigger.SoundTriggerDetector.this.mCallback.onDetected((android.media.soundtrigger.SoundTriggerDetector.EventPayload) message.obj);
                    break;
                case 3:
                    android.media.soundtrigger.SoundTriggerDetector.this.mCallback.onError();
                    break;
                case 4:
                    android.media.soundtrigger.SoundTriggerDetector.this.mCallback.onRecognitionPaused();
                    break;
                case 5:
                    android.media.soundtrigger.SoundTriggerDetector.this.mCallback.onRecognitionResumed();
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }
}
