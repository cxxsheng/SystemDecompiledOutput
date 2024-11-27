package com.android.internal.util;

/* loaded from: classes5.dex */
public class StatLogger {
    private static final java.lang.String TAG = "StatLogger";
    private final int SIZE;
    private final int[] mCallsPerSecond;
    private final int[] mCountStats;
    private final long[] mDurationPerSecond;
    private final long[] mDurationStats;
    private final java.lang.String[] mLabels;
    private final java.lang.Object mLock;
    private final int[] mMaxCallsPerSecond;
    private final long[] mMaxDurationPerSecond;
    private final long[] mMaxDurationStats;
    private long mNextTickTime;
    private final java.lang.String mStatsTag;

    public StatLogger(java.lang.String[] strArr) {
        this(null, strArr);
    }

    public StatLogger(java.lang.String str, java.lang.String[] strArr) {
        this.mLock = new java.lang.Object();
        this.mNextTickTime = android.os.SystemClock.elapsedRealtime() + 1000;
        this.mStatsTag = str;
        this.SIZE = strArr.length;
        this.mCountStats = new int[this.SIZE];
        this.mDurationStats = new long[this.SIZE];
        this.mCallsPerSecond = new int[this.SIZE];
        this.mMaxCallsPerSecond = new int[this.SIZE];
        this.mDurationPerSecond = new long[this.SIZE];
        this.mMaxDurationPerSecond = new long[this.SIZE];
        this.mMaxDurationStats = new long[this.SIZE];
        this.mLabels = strArr;
    }

    public long getTime() {
        return android.os.SystemClock.uptimeNanos() / 1000;
    }

    public long logDurationStat(int i, long j) {
        synchronized (this.mLock) {
            long time = getTime() - j;
            if (i >= 0 && i < this.SIZE) {
                int[] iArr = this.mCountStats;
                iArr[i] = iArr[i] + 1;
                long[] jArr = this.mDurationStats;
                jArr[i] = jArr[i] + time;
                if (this.mMaxDurationStats[i] < time) {
                    this.mMaxDurationStats[i] = time;
                }
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                if (elapsedRealtime > this.mNextTickTime) {
                    if (this.mMaxCallsPerSecond[i] < this.mCallsPerSecond[i]) {
                        this.mMaxCallsPerSecond[i] = this.mCallsPerSecond[i];
                    }
                    if (this.mMaxDurationPerSecond[i] < this.mDurationPerSecond[i]) {
                        this.mMaxDurationPerSecond[i] = this.mDurationPerSecond[i];
                    }
                    this.mCallsPerSecond[i] = 0;
                    this.mDurationPerSecond[i] = 0;
                    this.mNextTickTime = elapsedRealtime + 1000;
                }
                int[] iArr2 = this.mCallsPerSecond;
                iArr2[i] = iArr2[i] + 1;
                long[] jArr2 = this.mDurationPerSecond;
                jArr2[i] = jArr2[i] + time;
                return time;
            }
            android.util.Slog.wtf(TAG, "Invalid event ID: " + i);
            return time;
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        dump(new android.util.IndentingPrintWriter(printWriter, "  ").setIndent(str));
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            if (!android.text.TextUtils.isEmpty(this.mStatsTag)) {
                indentingPrintWriter.println(this.mStatsTag + ":");
            } else {
                indentingPrintWriter.println("Stats:");
            }
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.SIZE; i++) {
                int i2 = this.mCountStats[i];
                double d = this.mDurationStats[i] / 1000.0d;
                indentingPrintWriter.println(java.lang.String.format("%s: count=%d, total=%.1fms, avg=%.3fms, max calls/s=%d max dur/s=%.1fms max time=%.1fms", this.mLabels[i], java.lang.Integer.valueOf(i2), java.lang.Double.valueOf(d), java.lang.Double.valueOf(i2 == 0 ? 0.0d : d / i2), java.lang.Integer.valueOf(this.mMaxCallsPerSecond[i]), java.lang.Double.valueOf(this.mMaxDurationPerSecond[i] / 1000.0d), java.lang.Double.valueOf(this.mMaxDurationStats[i] / 1000.0d)));
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            long start = protoOutputStream.start(j);
            for (int i = 0; i < this.mLabels.length; i++) {
                long start2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, i);
                protoOutputStream.write(1138166333442L, this.mLabels[i]);
                protoOutputStream.write(1120986464259L, this.mCountStats[i]);
                protoOutputStream.write(1112396529668L, this.mDurationStats[i]);
                protoOutputStream.write(1120986464261L, this.mMaxCallsPerSecond[i]);
                protoOutputStream.write(1112396529670L, this.mMaxDurationPerSecond[i]);
                protoOutputStream.write(1112396529671L, this.mMaxDurationStats[i]);
                protoOutputStream.end(start2);
            }
            protoOutputStream.end(start);
        }
    }
}
