package android.media;

/* loaded from: classes2.dex */
public class SoundPool extends android.media.PlayerBase {
    private static final boolean DEBUG;
    private static final int SAMPLE_LOADED = 1;
    private static final java.lang.String TAG = "SoundPool";
    private final android.media.AudioAttributes mAttributes;
    private final java.util.concurrent.atomic.AtomicReference<android.media.SoundPool.EventHandler> mEventHandler;
    private boolean mHasAppOpsPlayAudio;
    private long mNativeContext;

    public interface OnLoadCompleteListener {
        void onLoadComplete(android.media.SoundPool soundPool, int i, int i2);
    }

    private final native int _load(java.io.FileDescriptor fileDescriptor, long j, long j2, int i);

    private final native void _mute(boolean z);

    private final native int _play(int i, float f, float f2, int i2, int i3, float f3, int i4);

    private final native void _setVolume(int i, float f, float f2);

    private final native void native_release();

    private native int native_setup(int i, java.lang.Object obj, java.lang.String str);

    public final native void autoPause();

    public final native void autoResume();

    public final native void pause(int i);

    public final native void resume(int i);

    public final native void setLoop(int i, int i2);

    public final native void setPriority(int i, int i2);

    public final native void setRate(int i, float f);

    public final native void stop(int i);

    public final native boolean unload(int i);

    static {
        java.lang.System.loadLibrary("soundpool");
        DEBUG = android.util.Log.isLoggable(TAG, 3);
    }

    public SoundPool(int i, int i2, int i3) {
        this(null, i, new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i2).build(), 0);
        android.media.PlayerBase.deprecateStreamTypeForPlayback(i2, TAG, "SoundPool()");
    }

    private SoundPool(android.content.Context context, int i, android.media.AudioAttributes audioAttributes, int i2) {
        super(audioAttributes, 3);
        this.mEventHandler = new java.util.concurrent.atomic.AtomicReference<>(null);
        if (native_setup(i, audioAttributes, getCurrentOpPackageName()) != 0) {
            throw new java.lang.RuntimeException("Native setup failed");
        }
        this.mAttributes = audioAttributes;
        baseRegisterPlayer(resolvePlaybackSessionId(context, i2));
    }

    public final void release() {
        baseRelease();
        native_release();
    }

    protected void finalize() {
        release();
    }

    public int load(java.lang.String str, int i) {
        int i2 = 0;
        try {
            java.io.File file = new java.io.File(str);
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
            if (open == null) {
                return 0;
            }
            i2 = _load(open.getFileDescriptor(), 0L, file.length(), i);
            open.close();
            return i2;
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "error loading " + str);
            return i2;
        }
    }

    public int load(android.content.Context context, int i, int i2) {
        android.content.res.AssetFileDescriptor openRawResourceFd = context.getResources().openRawResourceFd(i);
        if (openRawResourceFd == null) {
            return 0;
        }
        int _load = _load(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength(), i2);
        try {
            openRawResourceFd.close();
            return _load;
        } catch (java.io.IOException e) {
            return _load;
        }
    }

    public int load(android.content.res.AssetFileDescriptor assetFileDescriptor, int i) {
        if (assetFileDescriptor != null) {
            long length = assetFileDescriptor.getLength();
            if (length < 0) {
                throw new android.util.AndroidRuntimeException("no length for fd");
            }
            return _load(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), length, i);
        }
        return 0;
    }

    public int load(java.io.FileDescriptor fileDescriptor, long j, long j2, int i) {
        return _load(fileDescriptor, j, j2, i);
    }

    public final int play(int i, float f, float f2, int i2, int i3, float f3) {
        baseStart(0);
        return _play(i, f, f2, i2, i3, f3, getPlayerIId());
    }

    public final void setVolume(int i, float f, float f2) {
        _setVolume(i, f, f2);
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
        return -1;
    }

    @Override // android.media.PlayerBase
    android.media.VolumeShaper.State playerGetVolumeShaperState(int i) {
        return null;
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
        _mute(z);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerStart() {
    }

    @Override // android.media.PlayerBase
    void playerPause() {
    }

    @Override // android.media.PlayerBase
    void playerStop() {
    }

    public void setVolume(int i, float f) {
        setVolume(i, f, f);
    }

    public void setOnLoadCompleteListener(android.media.SoundPool.OnLoadCompleteListener onLoadCompleteListener) {
        if (onLoadCompleteListener == null) {
            this.mEventHandler.set(null);
            return;
        }
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler.set(new android.media.SoundPool.EventHandler(myLooper, onLoadCompleteListener));
            return;
        }
        android.os.Looper mainLooper = android.os.Looper.getMainLooper();
        if (mainLooper == null) {
            this.mEventHandler.set(null);
        } else {
            this.mEventHandler.set(new android.media.SoundPool.EventHandler(mainLooper, onLoadCompleteListener));
        }
    }

    private void postEventFromNative(int i, int i2, int i3, java.lang.Object obj) {
        android.media.SoundPool.EventHandler eventHandler = this.mEventHandler.get();
        if (eventHandler == null) {
            return;
        }
        eventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj));
    }

    private final class EventHandler extends android.os.Handler {
        private final android.media.SoundPool.OnLoadCompleteListener mOnLoadCompleteListener;

        EventHandler(android.os.Looper looper, android.media.SoundPool.OnLoadCompleteListener onLoadCompleteListener) {
            super(looper);
            this.mOnLoadCompleteListener = onLoadCompleteListener;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what != 1) {
                android.util.Log.e(android.media.SoundPool.TAG, "Unknown message type " + message.what);
                return;
            }
            if (android.media.SoundPool.DEBUG) {
                android.util.Log.d(android.media.SoundPool.TAG, "Sample " + message.arg1 + " loaded");
            }
            this.mOnLoadCompleteListener.onLoadComplete(android.media.SoundPool.this, message.arg1, message.arg2);
        }
    }

    public static class Builder {
        private android.media.AudioAttributes mAudioAttributes;
        private android.content.Context mContext;
        private int mMaxStreams = 1;
        private int mSessionId = 0;

        public android.media.SoundPool.Builder setMaxStreams(int i) throws java.lang.IllegalArgumentException {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Strictly positive value required for the maximum number of streams");
            }
            this.mMaxStreams = i;
            return this;
        }

        public android.media.SoundPool.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
            if (audioAttributes == null) {
                throw new java.lang.IllegalArgumentException("Invalid null AudioAttributes");
            }
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        public android.media.SoundPool.Builder setAudioSessionId(int i) {
            if (i != 0 && i < 1) {
                throw new java.lang.IllegalArgumentException("Invalid audio session ID " + i);
            }
            this.mSessionId = i;
            return this;
        }

        public android.media.SoundPool.Builder setContext(android.content.Context context) {
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
            return this;
        }

        public android.media.SoundPool build() {
            if (this.mAudioAttributes == null) {
                this.mAudioAttributes = new android.media.AudioAttributes.Builder().setUsage(1).build();
            }
            return new android.media.SoundPool(this.mContext, this.mMaxStreams, this.mAudioAttributes, this.mSessionId);
        }
    }
}
