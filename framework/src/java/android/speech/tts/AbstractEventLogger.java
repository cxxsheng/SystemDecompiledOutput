package android.speech.tts;

/* loaded from: classes3.dex */
abstract class AbstractEventLogger {
    protected final int mCallerPid;
    protected final int mCallerUid;
    protected final java.lang.String mServiceApp;
    protected long mPlaybackStartTime = -1;
    private volatile long mRequestProcessingStartTime = -1;
    private volatile long mEngineStartTime = -1;
    private volatile long mEngineCompleteTime = -1;
    private boolean mLogWritten = false;
    protected final long mReceivedTime = android.os.SystemClock.elapsedRealtime();

    protected abstract void logFailure(int i);

    protected abstract void logSuccess(long j, long j2, long j3);

    AbstractEventLogger(int i, int i2, java.lang.String str) {
        this.mCallerUid = i;
        this.mCallerPid = i2;
        this.mServiceApp = str;
    }

    public void onRequestProcessingStart() {
        this.mRequestProcessingStartTime = android.os.SystemClock.elapsedRealtime();
    }

    public void onEngineDataReceived() {
        if (this.mEngineStartTime == -1) {
            this.mEngineStartTime = android.os.SystemClock.elapsedRealtime();
        }
    }

    public void onEngineComplete() {
        this.mEngineCompleteTime = android.os.SystemClock.elapsedRealtime();
    }

    public void onAudioDataWritten() {
        if (this.mPlaybackStartTime == -1) {
            this.mPlaybackStartTime = android.os.SystemClock.elapsedRealtime();
        }
    }

    public void onCompleted(int i) {
        if (this.mLogWritten) {
            return;
        }
        this.mLogWritten = true;
        android.os.SystemClock.elapsedRealtime();
        if (i != 0 || this.mPlaybackStartTime == -1 || this.mEngineCompleteTime == -1) {
            logFailure(i);
        } else {
            logSuccess(this.mPlaybackStartTime - this.mReceivedTime, this.mEngineStartTime - this.mRequestProcessingStartTime, this.mEngineCompleteTime - this.mRequestProcessingStartTime);
        }
    }
}
