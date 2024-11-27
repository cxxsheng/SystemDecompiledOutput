package android.speech.tts;

/* loaded from: classes3.dex */
class PlaybackSynthesisCallback extends android.speech.tts.AbstractSynthesisCallback {
    private static final boolean DBG = false;
    private static final int MIN_AUDIO_BUFFER_SIZE = 8192;
    private static final java.lang.String TAG = "PlaybackSynthesisRequest";
    private final android.speech.tts.TextToSpeechService.AudioOutputParams mAudioParams;
    private final android.speech.tts.AudioPlaybackHandler mAudioTrackHandler;
    private final java.lang.Object mCallerIdentity;
    private final android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher mDispatcher;
    private volatile boolean mDone;
    private android.speech.tts.SynthesisPlaybackQueueItem mItem;
    private final android.speech.tts.AbstractEventLogger mLogger;
    private final java.lang.Object mStateLock;
    protected int mStatusCode;

    PlaybackSynthesisCallback(android.speech.tts.TextToSpeechService.AudioOutputParams audioOutputParams, android.speech.tts.AudioPlaybackHandler audioPlaybackHandler, android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, java.lang.Object obj, android.speech.tts.AbstractEventLogger abstractEventLogger, boolean z) {
        super(z);
        this.mStateLock = new java.lang.Object();
        this.mItem = null;
        this.mDone = false;
        this.mAudioParams = audioOutputParams;
        this.mAudioTrackHandler = audioPlaybackHandler;
        this.mDispatcher = utteranceProgressDispatcher;
        this.mCallerIdentity = obj;
        this.mLogger = abstractEventLogger;
        this.mStatusCode = 0;
    }

    @Override // android.speech.tts.AbstractSynthesisCallback
    void stop() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            if (this.mStatusCode == -2) {
                android.util.Log.w(TAG, "stop() called twice");
                return;
            }
            android.speech.tts.SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            this.mStatusCode = -2;
            if (synthesisPlaybackQueueItem != null) {
                synthesisPlaybackQueueItem.stop(-2);
            } else {
                this.mLogger.onCompleted(-2);
                this.mDispatcher.dispatchOnStop();
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int getMaxBufferSize() {
        return 8192;
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasStarted() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mItem != null;
        }
        return z;
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasFinished() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mDone;
        }
        return z;
    }

    @Override // android.speech.tts.SynthesisCallback
    public int start(int i, int i2, int i3) {
        if (i2 != 3 && i2 != 2 && i2 != 4) {
            android.util.Log.w(TAG, "Audio format encoding " + i2 + " not supported. Please use one of AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT or AudioFormat.ENCODING_PCM_FLOAT");
        }
        this.mDispatcher.dispatchOnBeginSynthesis(i, i2, i3);
        int channelConfig = android.speech.tts.BlockingAudioTrack.getChannelConfig(i3);
        synchronized (this.mStateLock) {
            if (channelConfig == 0) {
                android.util.Log.e(TAG, "Unsupported number of channels :" + i3);
                this.mStatusCode = -5;
                return -1;
            }
            if (this.mStatusCode == -2) {
                return errorCodeOnStop();
            }
            if (this.mStatusCode != 0) {
                return -1;
            }
            if (this.mItem != null) {
                android.util.Log.e(TAG, "Start called twice");
                return -1;
            }
            android.speech.tts.SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = new android.speech.tts.SynthesisPlaybackQueueItem(this.mAudioParams, i, i2, i3, this.mDispatcher, this.mCallerIdentity, this.mLogger);
            this.mAudioTrackHandler.enqueue(synthesisPlaybackQueueItem);
            this.mItem = synthesisPlaybackQueueItem;
            return 0;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int audioAvailable(byte[] bArr, int i, int i2) {
        if (i2 > getMaxBufferSize() || i2 <= 0) {
            throw new java.lang.IllegalArgumentException("buffer is too large or of zero length (" + i2 + " bytes)");
        }
        synchronized (this.mStateLock) {
            if (this.mItem == null) {
                this.mStatusCode = -5;
                return -1;
            }
            if (this.mStatusCode != 0) {
                return -1;
            }
            if (this.mStatusCode == -2) {
                return errorCodeOnStop();
            }
            android.speech.tts.SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            byte[] bArr2 = new byte[i2];
            java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
            this.mDispatcher.dispatchOnAudioAvailable(bArr2);
            try {
                synthesisPlaybackQueueItem.put(bArr2);
                this.mLogger.onEngineDataReceived();
                return 0;
            } catch (java.lang.InterruptedException e) {
                synchronized (this.mStateLock) {
                    this.mStatusCode = -5;
                    return -1;
                }
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int done() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                android.util.Log.w(TAG, "Duplicate call to done()");
                return -1;
            }
            if (this.mStatusCode == -2) {
                return errorCodeOnStop();
            }
            this.mDone = true;
            if (this.mItem == null) {
                android.util.Log.w(TAG, "done() was called before start() call");
                if (this.mStatusCode == 0) {
                    this.mDispatcher.dispatchOnSuccess();
                } else {
                    this.mDispatcher.dispatchOnError(this.mStatusCode);
                }
                this.mLogger.onEngineComplete();
                return -1;
            }
            android.speech.tts.SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
            int i = this.mStatusCode;
            if (i == 0) {
                synthesisPlaybackQueueItem.done();
            } else {
                synthesisPlaybackQueueItem.stop(i);
            }
            this.mLogger.onEngineComplete();
            return 0;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error() {
        error(-3);
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error(int i) {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            this.mStatusCode = i;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public void rangeStart(int i, int i2, int i3) {
        if (this.mItem == null) {
            android.util.Log.e(TAG, "mItem is null");
        } else {
            this.mItem.rangeStart(i, i2, i3);
        }
    }
}
