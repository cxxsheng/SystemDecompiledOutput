package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class ChangeReporter {
    public static final int SOURCE_APP_PROCESS = 1;
    public static final int SOURCE_SYSTEM_SERVER = 2;
    public static final int SOURCE_UNKNOWN_SOURCE = 0;
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 1;
    public static final int STATE_LOGGED = 3;
    public static final int STATE_UNKNOWN_STATE = 0;
    private static final java.lang.String TAG = "CompatibilityChangeReporter";
    private int mSource;
    private final java.util.Map<java.lang.Integer, java.util.Set<com.android.internal.compat.ChangeReporter.ChangeReport>> mReportedChanges = new java.util.HashMap();
    private boolean mDebugLogAll = false;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    private static final class ChangeReport {
        long mChangeId;
        int mState;

        ChangeReport(long j, int i) {
            this.mChangeId = j;
            this.mState = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.compat.ChangeReporter.ChangeReport changeReport = (com.android.internal.compat.ChangeReporter.ChangeReport) obj;
            if (this.mChangeId == changeReport.mChangeId && this.mState == changeReport.mState) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Long.valueOf(this.mChangeId), java.lang.Integer.valueOf(this.mState));
        }
    }

    public ChangeReporter(int i) {
        this.mSource = i;
    }

    public void reportChange(int i, long j, int i2) {
        if (shouldWriteToStatsLog(i, j, i2)) {
            com.android.internal.util.FrameworkStatsLog.write(228, i, j, i2, this.mSource);
        }
        if (shouldWriteToDebug(i, j, i2)) {
            debugLog(i, j, i2);
        }
        markAsReported(i, new com.android.internal.compat.ChangeReporter.ChangeReport(j, i2));
    }

    public void startDebugLogAll() {
        this.mDebugLogAll = true;
    }

    public void stopDebugLogAll() {
        this.mDebugLogAll = false;
    }

    public boolean shouldWriteToStatsLog(int i, long j, int i2) {
        return !isAlreadyReported(i, new com.android.internal.compat.ChangeReporter.ChangeReport(j, i2));
    }

    public boolean shouldWriteToDebug(int i, long j, int i2) {
        return this.mDebugLogAll || !isAlreadyReported(i, new com.android.internal.compat.ChangeReporter.ChangeReport(j, i2));
    }

    private boolean isAlreadyReported(int i, com.android.internal.compat.ChangeReporter.ChangeReport changeReport) {
        synchronized (this.mReportedChanges) {
            java.util.Set<com.android.internal.compat.ChangeReporter.ChangeReport> set = this.mReportedChanges.get(java.lang.Integer.valueOf(i));
            if (set == null) {
                return false;
            }
            return set.contains(changeReport);
        }
    }

    private void markAsReported(int i, com.android.internal.compat.ChangeReporter.ChangeReport changeReport) {
        synchronized (this.mReportedChanges) {
            java.util.Set<com.android.internal.compat.ChangeReporter.ChangeReport> set = this.mReportedChanges.get(java.lang.Integer.valueOf(i));
            if (set == null) {
                this.mReportedChanges.put(java.lang.Integer.valueOf(i), new java.util.HashSet());
                set = this.mReportedChanges.get(java.lang.Integer.valueOf(i));
            }
            set.add(changeReport);
        }
    }

    public void resetReportedChanges(int i) {
        synchronized (this.mReportedChanges) {
            this.mReportedChanges.remove(java.lang.Integer.valueOf(i));
        }
    }

    private void debugLog(int i, long j, int i2) {
        java.lang.String formatSimple = android.text.TextUtils.formatSimple("Compat change id reported: %d; UID %d; state: %s", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i), stateToString(i2));
        if (this.mSource == 2) {
            android.util.Slog.d(TAG, formatSimple);
        } else {
            android.util.Log.d(TAG, formatSimple);
        }
    }

    private static java.lang.String stateToString(int i) {
        switch (i) {
            case 1:
                return "ENABLED";
            case 2:
                return "DISABLED";
            case 3:
                return "LOGGED";
            default:
                return "UNKNOWN";
        }
    }
}
