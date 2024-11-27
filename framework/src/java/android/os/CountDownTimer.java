package android.os;

/* loaded from: classes3.dex */
public abstract class CountDownTimer {
    private static final int MSG = 1;
    private final long mCountdownInterval;
    private final long mMillisInFuture;
    private long mStopTimeInFuture;
    private boolean mCancelled = false;
    private android.os.Handler mHandler = new android.os.Handler() { // from class: android.os.CountDownTimer.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (android.os.CountDownTimer.this) {
                if (android.os.CountDownTimer.this.mCancelled) {
                    return;
                }
                long elapsedRealtime = android.os.CountDownTimer.this.mStopTimeInFuture - android.os.SystemClock.elapsedRealtime();
                long j = 0;
                if (elapsedRealtime <= 0) {
                    android.os.CountDownTimer.this.onFinish();
                } else {
                    long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                    android.os.CountDownTimer.this.onTick(elapsedRealtime);
                    long elapsedRealtime3 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime2;
                    if (elapsedRealtime < android.os.CountDownTimer.this.mCountdownInterval) {
                        long j2 = elapsedRealtime - elapsedRealtime3;
                        if (j2 >= 0) {
                            j = j2;
                        }
                    } else {
                        long j3 = android.os.CountDownTimer.this.mCountdownInterval - elapsedRealtime3;
                        while (j3 < 0) {
                            j3 += android.os.CountDownTimer.this.mCountdownInterval;
                        }
                        j = j3;
                    }
                    sendMessageDelayed(obtainMessage(1), j);
                }
            }
        }
    };

    public abstract void onFinish();

    public abstract void onTick(long j);

    public CountDownTimer(long j, long j2) {
        this.mMillisInFuture = j;
        this.mCountdownInterval = j2;
    }

    public final synchronized void cancel() {
        this.mCancelled = true;
        this.mHandler.removeMessages(1);
    }

    public final synchronized android.os.CountDownTimer start() {
        this.mCancelled = false;
        if (this.mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        this.mStopTimeInFuture = android.os.SystemClock.elapsedRealtime() + this.mMillisInFuture;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
        return this;
    }
}
