package android.speech.tts;

/* loaded from: classes3.dex */
final class SynthesisPlaybackQueueItem extends android.speech.tts.PlaybackQueueItem implements android.media.AudioTrack.OnPlaybackPositionUpdateListener {
    private static final boolean DBG = false;
    private static final long MAX_UNCONSUMED_AUDIO_MS = 500;
    private static final int NOT_RUN = 0;
    private static final int RUN_CALLED = 1;
    private static final int STOP_CALLED = 2;
    private static final java.lang.String TAG = "TTS.SynthQueueItem";
    private final android.speech.tts.BlockingAudioTrack mAudioTrack;
    private final java.util.LinkedList<android.speech.tts.SynthesisPlaybackQueueItem.ListEntry> mDataBufferList;
    private volatile boolean mDone;
    private final java.util.concurrent.locks.Lock mListLock;
    private final android.speech.tts.AbstractEventLogger mLogger;
    private final java.util.concurrent.locks.Condition mNotFull;
    private final java.util.concurrent.locks.Condition mReadReady;
    private final java.util.concurrent.atomic.AtomicInteger mRunState;
    private volatile int mStatusCode;
    private volatile boolean mStopped;
    private int mUnconsumedBytes;
    private java.util.concurrent.ConcurrentLinkedQueue<android.speech.tts.SynthesisPlaybackQueueItem.ProgressMarker> markerList;

    SynthesisPlaybackQueueItem(android.speech.tts.TextToSpeechService.AudioOutputParams audioOutputParams, int i, int i2, int i3, android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, java.lang.Object obj, android.speech.tts.AbstractEventLogger abstractEventLogger) {
        super(utteranceProgressDispatcher, obj);
        this.mListLock = new java.util.concurrent.locks.ReentrantLock();
        this.mReadReady = this.mListLock.newCondition();
        this.mNotFull = this.mListLock.newCondition();
        this.mDataBufferList = new java.util.LinkedList<>();
        this.markerList = new java.util.concurrent.ConcurrentLinkedQueue<>();
        this.mRunState = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mUnconsumedBytes = 0;
        this.mStopped = false;
        this.mDone = false;
        this.mStatusCode = 0;
        this.mAudioTrack = new android.speech.tts.BlockingAudioTrack(audioOutputParams, i, i2, i3);
        this.mLogger = abstractEventLogger;
    }

    @Override // android.speech.tts.PlaybackQueueItem, java.lang.Runnable
    public void run() {
        if (!this.mRunState.compareAndSet(0, 1)) {
            return;
        }
        android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
        dispatcher.dispatchOnStart();
        if (!this.mAudioTrack.init()) {
            dispatcher.dispatchOnError(-5);
            return;
        }
        this.mAudioTrack.setPlaybackPositionUpdateListener(this);
        updateMarker();
        while (true) {
            try {
                byte[] take = take();
                if (take == null) {
                    break;
                }
                this.mAudioTrack.write(take);
                this.mLogger.onAudioDataWritten();
            } catch (java.lang.InterruptedException e) {
            }
        }
        this.mAudioTrack.waitAndRelease();
        dispatchEndStatus();
    }

    private void dispatchEndStatus() {
        android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher dispatcher = getDispatcher();
        if (this.mStatusCode == 0) {
            dispatcher.dispatchOnSuccess();
        } else if (this.mStatusCode == -2) {
            dispatcher.dispatchOnStop();
        } else {
            dispatcher.dispatchOnError(this.mStatusCode);
        }
        this.mLogger.onCompleted(this.mStatusCode);
    }

    @Override // android.speech.tts.PlaybackQueueItem
    void stop(int i) {
        try {
            this.mListLock.lock();
            this.mStopped = true;
            this.mStatusCode = i;
            this.mNotFull.signal();
            if (this.mRunState.getAndSet(2) == 0) {
                dispatchEndStatus();
                return;
            }
            this.mReadReady.signal();
            this.mListLock.unlock();
            this.mAudioTrack.stop();
        } finally {
            this.mListLock.unlock();
        }
    }

    void done() {
        try {
            this.mListLock.lock();
            this.mDone = true;
            this.mReadReady.signal();
            this.mNotFull.signal();
        } finally {
            this.mListLock.unlock();
        }
    }

    private class ProgressMarker {
        public final int end;
        public final int frames;
        public final int start;

        public ProgressMarker(int i, int i2, int i3) {
            this.frames = i;
            this.start = i2;
            this.end = i3;
        }
    }

    void updateMarker() {
        android.speech.tts.SynthesisPlaybackQueueItem.ProgressMarker peek = this.markerList.peek();
        if (peek != null) {
            this.mAudioTrack.setNotificationMarkerPosition(peek.frames == 0 ? 1 : peek.frames);
        }
    }

    void rangeStart(int i, int i2, int i3) {
        this.markerList.add(new android.speech.tts.SynthesisPlaybackQueueItem.ProgressMarker(i, i2, i3));
        updateMarker();
    }

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onMarkerReached(android.media.AudioTrack audioTrack) {
        android.speech.tts.SynthesisPlaybackQueueItem.ProgressMarker poll = this.markerList.poll();
        if (poll == null) {
            android.util.Log.e(TAG, "onMarkerReached reached called but no marker in queue");
        } else {
            getDispatcher().dispatchOnRangeStart(poll.start, poll.end, poll.frames);
            updateMarker();
        }
    }

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onPeriodicNotification(android.media.AudioTrack audioTrack) {
    }

    void put(byte[] bArr) throws java.lang.InterruptedException {
        try {
            this.mListLock.lock();
            while (this.mAudioTrack.getAudioLengthMs(this.mUnconsumedBytes) > MAX_UNCONSUMED_AUDIO_MS && !this.mStopped) {
                this.mNotFull.await();
            }
            if (this.mStopped) {
                return;
            }
            this.mDataBufferList.add(new android.speech.tts.SynthesisPlaybackQueueItem.ListEntry(bArr));
            this.mUnconsumedBytes += bArr.length;
            this.mReadReady.signal();
        } finally {
            this.mListLock.unlock();
        }
    }

    private byte[] take() throws java.lang.InterruptedException {
        try {
            this.mListLock.lock();
            while (this.mDataBufferList.size() == 0 && !this.mStopped && !this.mDone) {
                this.mReadReady.await();
            }
            if (this.mStopped) {
                return null;
            }
            android.speech.tts.SynthesisPlaybackQueueItem.ListEntry poll = this.mDataBufferList.poll();
            if (poll == null) {
                return null;
            }
            this.mUnconsumedBytes -= poll.mBytes.length;
            this.mNotFull.signal();
            return poll.mBytes;
        } finally {
            this.mListLock.unlock();
        }
    }

    static final class ListEntry {
        final byte[] mBytes;

        ListEntry(byte[] bArr) {
            this.mBytes = bArr;
        }
    }
}
