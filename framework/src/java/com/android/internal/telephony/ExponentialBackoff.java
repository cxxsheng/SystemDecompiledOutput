package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class ExponentialBackoff {
    private long mCurrentDelayMs;
    private final android.os.Handler mHandler;
    private com.android.internal.telephony.ExponentialBackoff.HandlerAdapter mHandlerAdapter;
    private long mMaximumDelayMs;
    private int mMultiplier;
    private int mRetryCounter;
    private final java.lang.Runnable mRunnable;
    private long mStartDelayMs;

    public interface HandlerAdapter {
        boolean postDelayed(java.lang.Runnable runnable, long j);

        void removeCallbacks(java.lang.Runnable runnable);
    }

    public ExponentialBackoff(long j, long j2, int i, android.os.Looper looper, java.lang.Runnable runnable) {
        this(j, j2, i, new android.os.Handler(looper), runnable);
    }

    public ExponentialBackoff(long j, long j2, int i, android.os.Handler handler, java.lang.Runnable runnable) {
        this.mHandlerAdapter = new com.android.internal.telephony.ExponentialBackoff.HandlerAdapter() { // from class: com.android.internal.telephony.ExponentialBackoff.1
            @Override // com.android.internal.telephony.ExponentialBackoff.HandlerAdapter
            public boolean postDelayed(java.lang.Runnable runnable2, long j3) {
                return com.android.internal.telephony.ExponentialBackoff.this.mHandler.postDelayed(runnable2, j3);
            }

            @Override // com.android.internal.telephony.ExponentialBackoff.HandlerAdapter
            public void removeCallbacks(java.lang.Runnable runnable2) {
                com.android.internal.telephony.ExponentialBackoff.this.mHandler.removeCallbacks(runnable2);
            }
        };
        this.mRetryCounter = 0;
        this.mStartDelayMs = j;
        this.mMaximumDelayMs = j2;
        this.mMultiplier = i;
        this.mHandler = handler;
        this.mRunnable = runnable;
    }

    public void start() {
        this.mRetryCounter = 0;
        this.mCurrentDelayMs = this.mStartDelayMs;
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
        this.mHandlerAdapter.postDelayed(this.mRunnable, this.mCurrentDelayMs);
    }

    public void stop() {
        this.mRetryCounter = 0;
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
    }

    public void notifyFailed() {
        this.mRetryCounter++;
        this.mCurrentDelayMs = (long) (((java.lang.Math.random() + 1.0d) / 2.0d) * java.lang.Math.min(this.mMaximumDelayMs, (long) (this.mStartDelayMs * java.lang.Math.pow(this.mMultiplier, this.mRetryCounter))));
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
        this.mHandlerAdapter.postDelayed(this.mRunnable, this.mCurrentDelayMs);
    }

    public long getCurrentDelay() {
        return this.mCurrentDelayMs;
    }

    public void setHandlerAdapter(com.android.internal.telephony.ExponentialBackoff.HandlerAdapter handlerAdapter) {
        this.mHandlerAdapter = handlerAdapter;
    }
}
