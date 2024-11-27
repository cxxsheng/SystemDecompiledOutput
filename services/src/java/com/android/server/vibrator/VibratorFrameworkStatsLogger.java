package com.android.server.vibrator;

/* loaded from: classes2.dex */
public class VibratorFrameworkStatsLogger {
    private static final java.lang.String TAG = "VibratorFrameworkStatsLogger";
    private static final int VIBRATION_REPORTED_MAX_QUEUE_SIZE = 300;
    private static final int VIBRATION_REPORTED_MIN_INTERVAL_MILLIS = 10;
    private static final int VIBRATION_REPORTED_WARNING_QUEUE_SIZE = 200;
    private final java.lang.Runnable mConsumeVibrationStatsQueueRunnable;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastVibrationReportedLogUptime;
    private final java.lang.Object mLock;
    private final long mVibrationReportedLogIntervalMillis;
    private final long mVibrationReportedQueueMaxSize;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Queue<com.android.server.vibrator.VibrationStats.StatsInfo> mVibrationStatsQueue;

    VibratorFrameworkStatsLogger(android.os.Handler handler) {
        this(handler, 10, 300);
    }

    @com.android.internal.annotations.VisibleForTesting
    VibratorFrameworkStatsLogger(android.os.Handler handler, int i, int i2) {
        this.mLock = new java.lang.Object();
        this.mConsumeVibrationStatsQueueRunnable = new java.lang.Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.vibrator.VibratorFrameworkStatsLogger.this.lambda$new$0();
            }
        };
        this.mVibrationStatsQueue = new java.util.ArrayDeque();
        this.mHandler = handler;
        this.mVibrationReportedLogIntervalMillis = i;
        this.mVibrationReportedQueueMaxSize = i2;
    }

    public void writeVibratorStateOnAsync(final int i, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.util.FrameworkStatsLog.write_non_chained(84, i, (java.lang.String) null, 1, j);
            }
        });
    }

    public void writeVibratorStateOffAsync(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.vibrator.VibratorFrameworkStatsLogger$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.util.FrameworkStatsLog.write_non_chained(84, i, (java.lang.String) null, 0, 0);
            }
        });
    }

    public void writeVibrationReportedAsync(com.android.server.vibrator.VibrationStats.StatsInfo statsInfo) {
        int size;
        boolean z;
        long max;
        synchronized (this.mLock) {
            try {
                size = this.mVibrationStatsQueue.size();
                z = size == 0;
                if (size < this.mVibrationReportedQueueMaxSize) {
                    this.mVibrationStatsQueue.offer(statsInfo);
                }
                max = java.lang.Math.max(0L, (this.mLastVibrationReportedLogUptime + this.mVibrationReportedLogIntervalMillis) - android.os.SystemClock.uptimeMillis());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (size + 1 == 200) {
            android.util.Slog.w(TAG, " Approaching vibration metrics queue limit, events might be dropped.");
        }
        if (z) {
            this.mHandler.postDelayed(this.mConsumeVibrationStatsQueueRunnable, max);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: writeVibrationReportedFromQueue, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        com.android.server.vibrator.VibrationStats.StatsInfo poll;
        boolean z;
        synchronized (this.mLock) {
            try {
                poll = this.mVibrationStatsQueue.poll();
                z = !this.mVibrationStatsQueue.isEmpty();
                if (poll != null) {
                    this.mLastVibrationReportedLogUptime = android.os.SystemClock.uptimeMillis();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (poll == null) {
            android.util.Slog.w(TAG, "Unexpected vibration metric flush with empty queue. Ignoring.");
        } else {
            poll.writeVibrationReported();
        }
        if (z) {
            this.mHandler.postDelayed(this.mConsumeVibrationStatsQueueRunnable, this.mVibrationReportedLogIntervalMillis);
        }
    }

    public static void logPerformHapticsFeedbackIfKeyboard(int i, int i2) {
        boolean z;
        switch (i2) {
            case 3:
            case 7:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("vibrator.value_perform_haptic_feedback_keyboard", i);
        }
    }
}
