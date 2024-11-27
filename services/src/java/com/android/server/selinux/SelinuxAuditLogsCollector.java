package com.android.server.selinux;

/* loaded from: classes2.dex */
class SelinuxAuditLogsCollector {
    private static final java.lang.String TAG = "SelinuxAuditLogs";
    private final com.android.server.selinux.QuotaLimiter mQuotaLimiter;
    private final com.android.server.selinux.RateLimiter mRateLimiter;
    private static final java.lang.String SELINUX_PATTERN = "^.*\\bavc:\\s+(?<denial>.*)$";

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.regex.Matcher SELINUX_MATCHER = java.util.regex.Pattern.compile(SELINUX_PATTERN).matcher("");

    @com.android.internal.annotations.VisibleForTesting
    java.time.Instant mLastWrite = java.time.Instant.MIN;
    final java.util.concurrent.atomic.AtomicBoolean mStopRequested = new java.util.concurrent.atomic.AtomicBoolean(false);

    SelinuxAuditLogsCollector(com.android.server.selinux.RateLimiter rateLimiter, com.android.server.selinux.QuotaLimiter quotaLimiter) {
        this.mRateLimiter = rateLimiter;
        this.mQuotaLimiter = quotaLimiter;
    }

    boolean collect(int i) {
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        java.time.Instant collectLogLines = collectLogLines(i, arrayDeque);
        if (writeAuditLogs(arrayDeque)) {
            android.util.Log.w(TAG, "Too many SELinux logs in the queue, I am giving up.");
            this.mLastWrite = collectLogLines;
            arrayDeque.clear();
        }
        return arrayDeque.isEmpty();
    }

    private java.time.Instant collectLogLines(int i, java.util.Queue<android.util.EventLog.Event> queue) {
        java.util.ArrayList<android.util.EventLog.Event> arrayList = new java.util.ArrayList();
        try {
            android.util.EventLog.readEvents(new int[]{i}, arrayList);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Error reading event logs", e);
        }
        java.time.Instant instant = this.mLastWrite;
        for (android.util.EventLog.Event event : arrayList) {
            java.time.Instant ofEpochSecond = java.time.Instant.ofEpochSecond(0L, event.getTimeNanos());
            if (ofEpochSecond.isAfter(instant)) {
                instant = ofEpochSecond;
            }
            if (ofEpochSecond.compareTo(this.mLastWrite) > 0 && (event.getData() instanceof java.lang.String)) {
                queue.add(event);
            }
        }
        return instant;
    }

    private boolean writeAuditLogs(java.util.Queue<android.util.EventLog.Event> queue) {
        com.android.server.selinux.SelinuxAuditLogBuilder selinuxAuditLogBuilder = new com.android.server.selinux.SelinuxAuditLogBuilder();
        while (!this.mStopRequested.get() && !queue.isEmpty()) {
            android.util.EventLog.Event poll = queue.poll();
            java.lang.String str = (java.lang.String) poll.getData();
            java.time.Instant ofEpochSecond = java.time.Instant.ofEpochSecond(0L, poll.getTimeNanos());
            if (SELINUX_MATCHER.reset(str).matches()) {
                selinuxAuditLogBuilder.reset(SELINUX_MATCHER.group("denial"));
                com.android.server.selinux.SelinuxAuditLogBuilder.SelinuxAuditLog build = selinuxAuditLogBuilder.build();
                if (build == null) {
                    continue;
                } else {
                    if (!this.mQuotaLimiter.acquire()) {
                        return true;
                    }
                    this.mRateLimiter.acquire();
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SELINUX_AUDIT_LOG, build.mGranted, build.mPermissions, build.mSType, build.mSCategories, build.mTType, build.mTCategories, build.mTClass, build.mPath, build.mPermissive);
                    if (ofEpochSecond.isAfter(this.mLastWrite)) {
                        this.mLastWrite = ofEpochSecond;
                    }
                }
            }
        }
        return false;
    }
}
