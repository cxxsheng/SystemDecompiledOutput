package android.media;

/* loaded from: classes2.dex */
public final class MediaSync {
    private static final int CB_RETURN_AUDIO_BUFFER = 1;
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int MEDIASYNC_ERROR_AUDIOTRACK_FAIL = 1;
    public static final int MEDIASYNC_ERROR_SURFACE_FAIL = 2;
    private static final java.lang.String TAG = "MediaSync";
    private long mNativeContext;
    private final java.lang.Object mCallbackLock = new java.lang.Object();
    private android.os.Handler mCallbackHandler = null;
    private android.media.MediaSync.Callback mCallback = null;
    private final java.lang.Object mOnErrorListenerLock = new java.lang.Object();
    private android.os.Handler mOnErrorListenerHandler = null;
    private android.media.MediaSync.OnErrorListener mOnErrorListener = null;
    private java.lang.Thread mAudioThread = null;
    private android.os.Handler mAudioHandler = null;
    private android.os.Looper mAudioLooper = null;
    private final java.lang.Object mAudioLock = new java.lang.Object();
    private android.media.AudioTrack mAudioTrack = null;
    private java.util.List<android.media.MediaSync.AudioBuffer> mAudioBuffers = new java.util.LinkedList();
    private float mPlaybackRate = 0.0f;

    public static abstract class Callback {
        public abstract void onAudioBufferConsumed(android.media.MediaSync mediaSync, java.nio.ByteBuffer byteBuffer, int i);
    }

    public interface OnErrorListener {
        void onError(android.media.MediaSync mediaSync, int i, int i2);
    }

    private final native void native_finalize();

    private final native void native_flush();

    /* JADX INFO: Access modifiers changed from: private */
    public final native long native_getPlayTimeForPendingAudioFrames();

    private final native boolean native_getTimestamp(android.media.MediaTimestamp mediaTimestamp);

    private static final native void native_init();

    private final native void native_release();

    private final native void native_setAudioTrack(android.media.AudioTrack audioTrack);

    private native float native_setPlaybackParams(android.media.PlaybackParams playbackParams);

    private final native void native_setSurface(android.view.Surface surface);

    private native float native_setSyncParams(android.media.SyncParams syncParams);

    private final native void native_setup();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_updateQueuedAudioData(int i, long j);

    public final native android.view.Surface createInputSurface();

    public native android.media.PlaybackParams getPlaybackParams();

    public native android.media.SyncParams getSyncParams();

    private static class AudioBuffer {
        public int mBufferIndex;
        public java.nio.ByteBuffer mByteBuffer;
        long mPresentationTimeUs;

        public AudioBuffer(java.nio.ByteBuffer byteBuffer, int i, long j) {
            this.mByteBuffer = byteBuffer;
            this.mBufferIndex = i;
            this.mPresentationTimeUs = j;
        }
    }

    public MediaSync() {
        native_setup();
    }

    protected void finalize() {
        native_finalize();
    }

    public final void release() {
        returnAudioBuffers();
        if (this.mAudioThread != null && this.mAudioLooper != null) {
            this.mAudioLooper.quit();
        }
        setCallback(null, null);
        native_release();
    }

    public void setCallback(android.media.MediaSync.Callback callback, android.os.Handler handler) {
        synchronized (this.mCallbackLock) {
            if (handler != null) {
                this.mCallbackHandler = handler;
            } else {
                android.os.Looper myLooper = android.os.Looper.myLooper();
                if (myLooper == null) {
                    myLooper = android.os.Looper.getMainLooper();
                }
                if (myLooper == null) {
                    this.mCallbackHandler = null;
                } else {
                    this.mCallbackHandler = new android.os.Handler(myLooper);
                }
            }
            this.mCallback = callback;
        }
    }

    public void setOnErrorListener(android.media.MediaSync.OnErrorListener onErrorListener, android.os.Handler handler) {
        synchronized (this.mOnErrorListenerLock) {
            if (handler != null) {
                this.mOnErrorListenerHandler = handler;
            } else {
                android.os.Looper myLooper = android.os.Looper.myLooper();
                if (myLooper == null) {
                    myLooper = android.os.Looper.getMainLooper();
                }
                if (myLooper == null) {
                    this.mOnErrorListenerHandler = null;
                } else {
                    this.mOnErrorListenerHandler = new android.os.Handler(myLooper);
                }
            }
            this.mOnErrorListener = onErrorListener;
        }
    }

    public void setSurface(android.view.Surface surface) {
        native_setSurface(surface);
    }

    public void setAudioTrack(android.media.AudioTrack audioTrack) {
        native_setAudioTrack(audioTrack);
        this.mAudioTrack = audioTrack;
        if (audioTrack != null && this.mAudioThread == null) {
            createAudioThread();
        }
    }

    public void setPlaybackParams(android.media.PlaybackParams playbackParams) {
        synchronized (this.mAudioLock) {
            this.mPlaybackRate = native_setPlaybackParams(playbackParams);
        }
        if (this.mPlaybackRate != 0.0d && this.mAudioThread != null) {
            postRenderAudio(0L);
        }
    }

    public void setSyncParams(android.media.SyncParams syncParams) {
        synchronized (this.mAudioLock) {
            this.mPlaybackRate = native_setSyncParams(syncParams);
        }
        if (this.mPlaybackRate != 0.0d && this.mAudioThread != null) {
            postRenderAudio(0L);
        }
    }

    public void flush() {
        synchronized (this.mAudioLock) {
            this.mAudioBuffers.clear();
            this.mCallbackHandler.removeCallbacksAndMessages(null);
        }
        if (this.mAudioTrack != null) {
            this.mAudioTrack.pause();
            this.mAudioTrack.flush();
            this.mAudioTrack.stop();
        }
        native_flush();
    }

    public android.media.MediaTimestamp getTimestamp() {
        try {
            android.media.MediaTimestamp mediaTimestamp = new android.media.MediaTimestamp();
            if (!native_getTimestamp(mediaTimestamp)) {
                return null;
            }
            return mediaTimestamp;
        } catch (java.lang.IllegalStateException e) {
            return null;
        }
    }

    public void queueAudio(java.nio.ByteBuffer byteBuffer, int i, long j) {
        if (this.mAudioTrack == null || this.mAudioThread == null) {
            throw new java.lang.IllegalStateException("AudioTrack is NOT set or audio thread is not created");
        }
        synchronized (this.mAudioLock) {
            this.mAudioBuffers.add(new android.media.MediaSync.AudioBuffer(byteBuffer, i, j));
        }
        if (this.mPlaybackRate != 0.0d) {
            postRenderAudio(0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postRenderAudio(long j) {
        this.mAudioHandler.postDelayed(new java.lang.Runnable() { // from class: android.media.MediaSync.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (android.media.MediaSync.this.mAudioLock) {
                    if (android.media.MediaSync.this.mPlaybackRate == 0.0d) {
                        return;
                    }
                    if (android.media.MediaSync.this.mAudioBuffers.isEmpty()) {
                        return;
                    }
                    android.media.MediaSync.AudioBuffer audioBuffer = (android.media.MediaSync.AudioBuffer) android.media.MediaSync.this.mAudioBuffers.get(0);
                    int remaining = audioBuffer.mByteBuffer.remaining();
                    if (remaining > 0 && android.media.MediaSync.this.mAudioTrack.getPlayState() != 3) {
                        try {
                            android.media.MediaSync.this.mAudioTrack.play();
                        } catch (java.lang.IllegalStateException e) {
                            android.util.Log.w(android.media.MediaSync.TAG, "could not start audio track");
                        }
                    }
                    int write = android.media.MediaSync.this.mAudioTrack.write(audioBuffer.mByteBuffer, remaining, 1);
                    if (write > 0) {
                        if (audioBuffer.mPresentationTimeUs != -1) {
                            android.media.MediaSync.this.native_updateQueuedAudioData(remaining, audioBuffer.mPresentationTimeUs);
                            audioBuffer.mPresentationTimeUs = -1L;
                        }
                        if (write == remaining) {
                            android.media.MediaSync.this.postReturnByteBuffer(audioBuffer);
                            android.media.MediaSync.this.mAudioBuffers.remove(0);
                            if (!android.media.MediaSync.this.mAudioBuffers.isEmpty()) {
                                android.media.MediaSync.this.postRenderAudio(0L);
                            }
                            return;
                        }
                    }
                    android.media.MediaSync.this.postRenderAudio(java.util.concurrent.TimeUnit.MICROSECONDS.toMillis(android.media.MediaSync.this.native_getPlayTimeForPendingAudioFrames()) / 2);
                }
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void postReturnByteBuffer(final android.media.MediaSync.AudioBuffer audioBuffer) {
        synchronized (this.mCallbackLock) {
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.post(new java.lang.Runnable() { // from class: android.media.MediaSync.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (android.media.MediaSync.this.mCallbackLock) {
                            android.media.MediaSync.Callback callback = android.media.MediaSync.this.mCallback;
                            if (android.media.MediaSync.this.mCallbackHandler != null && android.media.MediaSync.this.mCallbackHandler.getLooper().getThread() == java.lang.Thread.currentThread()) {
                                if (callback != null) {
                                    callback.onAudioBufferConsumed(this, audioBuffer.mByteBuffer, audioBuffer.mBufferIndex);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private final void returnAudioBuffers() {
        synchronized (this.mAudioLock) {
            java.util.Iterator<android.media.MediaSync.AudioBuffer> it = this.mAudioBuffers.iterator();
            while (it.hasNext()) {
                postReturnByteBuffer(it.next());
            }
            this.mAudioBuffers.clear();
        }
    }

    private void createAudioThread() {
        this.mAudioThread = new java.lang.Thread() { // from class: android.media.MediaSync.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                android.os.Looper.prepare();
                synchronized (android.media.MediaSync.this.mAudioLock) {
                    android.media.MediaSync.this.mAudioLooper = android.os.Looper.myLooper();
                    android.media.MediaSync.this.mAudioHandler = new android.os.Handler();
                    android.media.MediaSync.this.mAudioLock.notify();
                }
                android.os.Looper.loop();
            }
        };
        this.mAudioThread.start();
        synchronized (this.mAudioLock) {
            try {
                this.mAudioLock.wait();
            } catch (java.lang.InterruptedException e) {
            }
        }
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }
}
