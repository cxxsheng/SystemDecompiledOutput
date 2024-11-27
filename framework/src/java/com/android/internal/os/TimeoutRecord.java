package com.android.internal.os;

/* loaded from: classes4.dex */
public class TimeoutRecord {
    public final boolean mEndTakenBeforeLocks;
    public final long mEndUptimeMillis;
    public final int mKind;
    public final com.android.internal.os.anr.AnrLatencyTracker mLatencyTracker;
    public final java.lang.String mReason;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeoutKind {
        public static final int APP_REGISTERED = 7;
        public static final int APP_START = 10;
        public static final int BROADCAST_RECEIVER = 3;
        public static final int CONTENT_PROVIDER = 6;
        public static final int FGS_TIMEOUT = 11;
        public static final int INPUT_DISPATCH_NO_FOCUSED_WINDOW = 1;
        public static final int INPUT_DISPATCH_WINDOW_UNRESPONSIVE = 2;
        public static final int JOB_SERVICE = 9;
        public static final int SERVICE_EXEC = 5;
        public static final int SERVICE_START = 4;
        public static final int SHORT_FGS_TIMEOUT = 8;
    }

    private TimeoutRecord(int i, java.lang.String str, long j, boolean z) {
        this.mKind = i;
        this.mReason = str;
        this.mEndUptimeMillis = j;
        this.mEndTakenBeforeLocks = z;
        this.mLatencyTracker = new com.android.internal.os.anr.AnrLatencyTracker(i, j);
    }

    private static com.android.internal.os.TimeoutRecord endingNow(int i, java.lang.String str) {
        return new com.android.internal.os.TimeoutRecord(i, str, android.os.SystemClock.uptimeMillis(), true);
    }

    private static com.android.internal.os.TimeoutRecord endingApproximatelyNow(int i, java.lang.String str) {
        return new com.android.internal.os.TimeoutRecord(i, str, android.os.SystemClock.uptimeMillis(), false);
    }

    public static com.android.internal.os.TimeoutRecord forBroadcastReceiver(android.content.Intent intent, java.lang.String str, java.lang.String str2) {
        if (str != null) {
            if (str2 != null) {
                android.content.Intent intent2 = new android.content.Intent(intent);
                intent2.setComponent(new android.content.ComponentName(str, str2));
                intent = intent2;
            } else {
                android.content.Intent intent3 = new android.content.Intent(intent);
                intent3.setPackage(str);
                intent = intent3;
            }
        }
        return forBroadcastReceiver(intent);
    }

    public static com.android.internal.os.TimeoutRecord forBroadcastReceiver(android.content.Intent intent) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Broadcast of ");
        intent.toString(sb);
        return endingNow(3, sb.toString());
    }

    public static com.android.internal.os.TimeoutRecord forBroadcastReceiver(android.content.Intent intent, long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Broadcast of ");
        intent.toString(sb);
        sb.append(", waited ");
        sb.append(j);
        sb.append("ms");
        return endingNow(3, sb.toString());
    }

    public static com.android.internal.os.TimeoutRecord forInputDispatchNoFocusedWindow(java.lang.String str) {
        return endingNow(1, str);
    }

    public static com.android.internal.os.TimeoutRecord forInputDispatchWindowUnresponsive(java.lang.String str) {
        return endingNow(2, str);
    }

    public static com.android.internal.os.TimeoutRecord forServiceExec(java.lang.String str, long j) {
        return endingNow(5, "executing service " + str + ", waited " + j + "ms");
    }

    public static com.android.internal.os.TimeoutRecord forServiceStartWithEndTime(java.lang.String str, long j) {
        return new com.android.internal.os.TimeoutRecord(4, str, j, true);
    }

    public static com.android.internal.os.TimeoutRecord forContentProvider(java.lang.String str) {
        return endingApproximatelyNow(6, str);
    }

    public static com.android.internal.os.TimeoutRecord forApp(java.lang.String str) {
        return endingApproximatelyNow(7, str);
    }

    public static com.android.internal.os.TimeoutRecord forShortFgsTimeout(java.lang.String str) {
        return endingNow(8, str);
    }

    public static com.android.internal.os.TimeoutRecord forFgsTimeout(java.lang.String str) {
        return endingNow(11, str);
    }

    public static com.android.internal.os.TimeoutRecord forJobService(java.lang.String str) {
        return endingNow(9, str);
    }

    public static com.android.internal.os.TimeoutRecord forAppStart(java.lang.String str) {
        return endingNow(10, str);
    }
}
