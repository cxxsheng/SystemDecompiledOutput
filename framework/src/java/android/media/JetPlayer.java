package android.media;

/* loaded from: classes2.dex */
public class JetPlayer {
    private static final int JET_EVENT = 1;
    private static final int JET_EVENT_CHAN_MASK = 245760;
    private static final int JET_EVENT_CHAN_SHIFT = 14;
    private static final int JET_EVENT_CTRL_MASK = 16256;
    private static final int JET_EVENT_CTRL_SHIFT = 7;
    private static final int JET_EVENT_SEG_MASK = -16777216;
    private static final int JET_EVENT_SEG_SHIFT = 24;
    private static final int JET_EVENT_TRACK_MASK = 16515072;
    private static final int JET_EVENT_TRACK_SHIFT = 18;
    private static final int JET_EVENT_VAL_MASK = 127;
    private static final int JET_NUMQUEUEDSEGMENT_UPDATE = 3;
    private static final int JET_OUTPUT_CHANNEL_CONFIG = 12;
    private static final int JET_OUTPUT_RATE = 22050;
    private static final int JET_PAUSE_UPDATE = 4;
    private static final int JET_USERID_UPDATE = 2;
    private static int MAXTRACKS = 32;
    private static final java.lang.String TAG = "JetPlayer-J";
    private static android.media.JetPlayer singletonRef;
    private android.os.Looper mInitializationLooper;
    private long mNativePlayerInJavaObj;
    private android.media.JetPlayer.NativeEventHandler mEventHandler = null;
    private final java.lang.Object mEventListenerLock = new java.lang.Object();
    private android.media.JetPlayer.OnJetEventListener mJetEventListener = null;

    public interface OnJetEventListener {
        void onJetEvent(android.media.JetPlayer jetPlayer, short s, byte b, byte b2, byte b3, byte b4);

        void onJetNumQueuedSegmentUpdate(android.media.JetPlayer jetPlayer, int i);

        void onJetPauseUpdate(android.media.JetPlayer jetPlayer, int i);

        void onJetUserIdUpdate(android.media.JetPlayer jetPlayer, int i, int i2);
    }

    private final native boolean native_clearQueue();

    private final native boolean native_closeJetFile();

    private final native void native_finalize();

    private final native boolean native_loadJetFromFile(java.lang.String str);

    private final native boolean native_loadJetFromFileD(java.io.FileDescriptor fileDescriptor, long j, long j2);

    private final native boolean native_pauseJet();

    private final native boolean native_playJet();

    private final native boolean native_queueJetSegment(int i, int i2, int i3, int i4, int i5, byte b);

    private final native boolean native_queueJetSegmentMuteArray(int i, int i2, int i3, int i4, boolean[] zArr, byte b);

    private final native void native_release();

    private final native boolean native_setMuteArray(boolean[] zArr, boolean z);

    private final native boolean native_setMuteFlag(int i, boolean z, boolean z2);

    private final native boolean native_setMuteFlags(int i, boolean z);

    private final native boolean native_setup(java.lang.Object obj, int i, int i2);

    private final native boolean native_triggerClip(int i);

    static {
        java.lang.System.loadLibrary("media_jni");
    }

    public static android.media.JetPlayer getJetPlayer() {
        if (singletonRef == null) {
            singletonRef = new android.media.JetPlayer();
        }
        return singletonRef;
    }

    public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new java.lang.CloneNotSupportedException();
    }

    private JetPlayer() {
        this.mInitializationLooper = null;
        android.os.Looper myLooper = android.os.Looper.myLooper();
        this.mInitializationLooper = myLooper;
        if (myLooper == null) {
            this.mInitializationLooper = android.os.Looper.getMainLooper();
        }
        int minBufferSize = android.media.AudioTrack.getMinBufferSize(JET_OUTPUT_RATE, 12, 2);
        if (minBufferSize != -1 && minBufferSize != -2) {
            native_setup(new java.lang.ref.WeakReference(this), getMaxTracks(), java.lang.Math.max(1200, minBufferSize / (android.media.AudioFormat.getBytesPerSample(2) * 2)));
        }
    }

    protected void finalize() {
        native_finalize();
    }

    public void release() {
        native_release();
        singletonRef = null;
    }

    public static int getMaxTracks() {
        return MAXTRACKS;
    }

    public boolean loadJetFile(java.lang.String str) {
        return native_loadJetFromFile(str);
    }

    public boolean loadJetFile(android.content.res.AssetFileDescriptor assetFileDescriptor) {
        long length = assetFileDescriptor.getLength();
        if (length < 0) {
            throw new android.util.AndroidRuntimeException("no length for fd");
        }
        return native_loadJetFromFileD(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), length);
    }

    public boolean closeJetFile() {
        return native_closeJetFile();
    }

    public boolean play() {
        return native_playJet();
    }

    public boolean pause() {
        return native_pauseJet();
    }

    public boolean queueJetSegment(int i, int i2, int i3, int i4, int i5, byte b) {
        return native_queueJetSegment(i, i2, i3, i4, i5, b);
    }

    public boolean queueJetSegmentMuteArray(int i, int i2, int i3, int i4, boolean[] zArr, byte b) {
        if (zArr.length != getMaxTracks()) {
            return false;
        }
        return native_queueJetSegmentMuteArray(i, i2, i3, i4, zArr, b);
    }

    public boolean setMuteFlags(int i, boolean z) {
        return native_setMuteFlags(i, z);
    }

    public boolean setMuteArray(boolean[] zArr, boolean z) {
        if (zArr.length != getMaxTracks()) {
            return false;
        }
        return native_setMuteArray(zArr, z);
    }

    public boolean setMuteFlag(int i, boolean z, boolean z2) {
        return native_setMuteFlag(i, z, z2);
    }

    public boolean triggerClip(int i) {
        return native_triggerClip(i);
    }

    public boolean clearQueue() {
        return native_clearQueue();
    }

    private class NativeEventHandler extends android.os.Handler {
        private android.media.JetPlayer mJet;

        public NativeEventHandler(android.media.JetPlayer jetPlayer, android.os.Looper looper) {
            super(looper);
            this.mJet = jetPlayer;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.JetPlayer.OnJetEventListener onJetEventListener;
            synchronized (android.media.JetPlayer.this.mEventListenerLock) {
                onJetEventListener = this.mJet.mJetEventListener;
            }
            switch (message.what) {
                case 1:
                    if (onJetEventListener != null) {
                        android.media.JetPlayer.this.mJetEventListener.onJetEvent(this.mJet, (short) ((message.arg1 & (-16777216)) >> 24), (byte) ((message.arg1 & android.media.JetPlayer.JET_EVENT_TRACK_MASK) >> 18), (byte) (((message.arg1 & android.media.JetPlayer.JET_EVENT_CHAN_MASK) >> 14) + 1), (byte) ((message.arg1 & android.media.JetPlayer.JET_EVENT_CTRL_MASK) >> 7), (byte) (message.arg1 & 127));
                        return;
                    }
                    return;
                case 2:
                    if (onJetEventListener != null) {
                        onJetEventListener.onJetUserIdUpdate(this.mJet, message.arg1, message.arg2);
                        return;
                    }
                    return;
                case 3:
                    if (onJetEventListener != null) {
                        onJetEventListener.onJetNumQueuedSegmentUpdate(this.mJet, message.arg1);
                        return;
                    }
                    return;
                case 4:
                    if (onJetEventListener != null) {
                        onJetEventListener.onJetPauseUpdate(this.mJet, message.arg1);
                        return;
                    }
                    return;
                default:
                    android.media.JetPlayer.loge("Unknown message type " + message.what);
                    return;
            }
        }
    }

    public void setEventListener(android.media.JetPlayer.OnJetEventListener onJetEventListener) {
        setEventListener(onJetEventListener, null);
    }

    public void setEventListener(android.media.JetPlayer.OnJetEventListener onJetEventListener, android.os.Handler handler) {
        synchronized (this.mEventListenerLock) {
            this.mJetEventListener = onJetEventListener;
            if (onJetEventListener != null) {
                if (handler != null) {
                    this.mEventHandler = new android.media.JetPlayer.NativeEventHandler(this, handler.getLooper());
                } else {
                    this.mEventHandler = new android.media.JetPlayer.NativeEventHandler(this, this.mInitializationLooper);
                }
            } else {
                this.mEventHandler = null;
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3) {
        android.media.JetPlayer jetPlayer = (android.media.JetPlayer) ((java.lang.ref.WeakReference) obj).get();
        if (jetPlayer != null && jetPlayer.mEventHandler != null) {
            jetPlayer.mEventHandler.sendMessage(jetPlayer.mEventHandler.obtainMessage(i, i2, i3, null));
        }
    }

    private static void logd(java.lang.String str) {
        android.util.Log.d(TAG, "[ android.media.JetPlayer ] " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(java.lang.String str) {
        android.util.Log.e(TAG, "[ android.media.JetPlayer ] " + str);
    }
}
