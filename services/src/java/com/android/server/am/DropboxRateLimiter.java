package com.android.server.am;

/* loaded from: classes.dex */
public class DropboxRateLimiter {
    private static final java.lang.String FLAG_NAMESPACE = "dropbox";
    private static final int RATE_LIMIT_ALLOWED_ENTRIES_DEFAULT = 6;
    private static final long RATE_LIMIT_BUFFER_DURATION_DEFAULT = 600000;
    private static final long RATE_LIMIT_BUFFER_EXPIRY_FACTOR_DEFAULT = 3;
    private static final int STRICT_RATE_LIMIT_ALLOWED_ENTRIES_DEFAULT = 1;
    private static final long STRICT_RATE_LIMIT_BUFFER_DURATION_DEFAULT = 1200000;
    private static final java.lang.String TAG = "DropboxRateLimiter";
    private final com.android.server.am.DropboxRateLimiter.Clock mClock;

    @com.android.internal.annotations.GuardedBy({"mErrorClusterRecords"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.am.DropboxRateLimiter.ErrorRecord> mErrorClusterRecords;
    private long mLastMapCleanUp;
    private int mRateLimitAllowedEntries;
    private long mRateLimitBufferDuration;
    private long mRateLimitBufferExpiryFactor;
    private long mStrictRateLimitBufferDuration;
    private int mStrictRatelimitAllowedEntries;

    public interface Clock {
        long uptimeMillis();
    }

    public DropboxRateLimiter() {
        this(new com.android.server.am.DropboxRateLimiter.DefaultClock());
    }

    public DropboxRateLimiter(com.android.server.am.DropboxRateLimiter.Clock clock) {
        this.mErrorClusterRecords = new android.util.ArrayMap<>();
        this.mLastMapCleanUp = 0L;
        this.mClock = clock;
        this.mRateLimitBufferDuration = 600000L;
        this.mRateLimitBufferExpiryFactor = 3L;
        this.mRateLimitAllowedEntries = 6;
        this.mStrictRatelimitAllowedEntries = 1;
        this.mStrictRateLimitBufferDuration = STRICT_RATE_LIMIT_BUFFER_DURATION_DEFAULT;
    }

    public void init() {
        this.mRateLimitBufferDuration = android.provider.DeviceConfig.getLong(FLAG_NAMESPACE, "DropboxRateLimiter__rate_limit_buffer_duration", 600000L);
        this.mRateLimitBufferExpiryFactor = android.provider.DeviceConfig.getLong(FLAG_NAMESPACE, "DropboxRateLimiter__rate_limit_buffer_expiry_factor", 3L);
        this.mRateLimitAllowedEntries = android.provider.DeviceConfig.getInt(FLAG_NAMESPACE, "DropboxRateLimiter__rate_limit_allowed_entries", 6);
        this.mStrictRatelimitAllowedEntries = android.provider.DeviceConfig.getInt(FLAG_NAMESPACE, "DropboxRateLimiter__strict_rate_limit_allowed_entries", 1);
        this.mStrictRateLimitBufferDuration = android.provider.DeviceConfig.getLong(FLAG_NAMESPACE, "DropboxRateLimiter__strict_rate_limit_buffer_duration", STRICT_RATE_LIMIT_BUFFER_DURATION_DEFAULT);
    }

    public com.android.server.am.DropboxRateLimiter.RateLimitResult shouldRateLimit(java.lang.String str, java.lang.String str2) {
        long uptimeMillis = this.mClock.uptimeMillis();
        synchronized (this.mErrorClusterRecords) {
            try {
                maybeRemoveExpiredRecords(uptimeMillis);
                com.android.server.am.DropboxRateLimiter.ErrorRecord errorRecord = this.mErrorClusterRecords.get(errorKey(str, str2));
                if (errorRecord == null) {
                    this.mErrorClusterRecords.put(errorKey(str, str2), new com.android.server.am.DropboxRateLimiter.ErrorRecord(uptimeMillis, 1));
                    return new com.android.server.am.DropboxRateLimiter.RateLimitResult(false, 0);
                }
                long startTime = uptimeMillis - errorRecord.getStartTime();
                if (startTime > errorRecord.getBufferDuration()) {
                    int recentlyDroppedCount = recentlyDroppedCount(errorRecord);
                    errorRecord.setStartTime(uptimeMillis);
                    errorRecord.setCount(1);
                    if (recentlyDroppedCount > 0 && startTime < errorRecord.getBufferDuration() * 2) {
                        errorRecord.incrementSuccessiveRateLimitCycles();
                    } else {
                        errorRecord.setSuccessiveRateLimitCycles(0);
                    }
                    return new com.android.server.am.DropboxRateLimiter.RateLimitResult(false, recentlyDroppedCount);
                }
                errorRecord.incrementCount();
                if (errorRecord.getCount() > errorRecord.getAllowedEntries()) {
                    return new com.android.server.am.DropboxRateLimiter.RateLimitResult(true, recentlyDroppedCount(errorRecord));
                }
                return new com.android.server.am.DropboxRateLimiter.RateLimitResult(false, 0);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int recentlyDroppedCount(com.android.server.am.DropboxRateLimiter.ErrorRecord errorRecord) {
        if (errorRecord == null || errorRecord.getCount() < errorRecord.getAllowedEntries()) {
            return 0;
        }
        return errorRecord.getCount() - errorRecord.getAllowedEntries();
    }

    private void maybeRemoveExpiredRecords(long j) {
        if (j - this.mLastMapCleanUp <= this.mRateLimitBufferExpiryFactor * this.mRateLimitBufferDuration) {
            return;
        }
        for (int size = this.mErrorClusterRecords.size() - 1; size >= 0; size--) {
            if (this.mErrorClusterRecords.valueAt(size).hasExpired(j)) {
                com.android.modules.expresslog.Counter.logIncrement("stability_errors.value_dropbox_buffer_expired_count", this.mErrorClusterRecords.valueAt(size).getCount());
                this.mErrorClusterRecords.removeAt(size);
            }
        }
        this.mLastMapCleanUp = j;
    }

    public void reset() {
        synchronized (this.mErrorClusterRecords) {
            this.mErrorClusterRecords.clear();
        }
        this.mLastMapCleanUp = 0L;
        android.util.Slog.i(TAG, "Rate limiter reset.");
    }

    java.lang.String errorKey(java.lang.String str, java.lang.String str2) {
        return str + str2;
    }

    public class RateLimitResult {
        final int mDroppedCountSinceRateLimitActivated;
        final boolean mShouldRateLimit;

        public RateLimitResult(boolean z, int i) {
            this.mShouldRateLimit = z;
            this.mDroppedCountSinceRateLimitActivated = i;
        }

        public boolean shouldRateLimit() {
            return this.mShouldRateLimit;
        }

        public int droppedCountSinceRateLimitActivated() {
            return this.mDroppedCountSinceRateLimitActivated;
        }

        public java.lang.String createHeader() {
            return "Dropped-Count: " + this.mDroppedCountSinceRateLimitActivated + "\n";
        }
    }

    private class ErrorRecord {
        int mCount;
        long mStartTime;
        int mSuccessiveRateLimitCycles = 0;

        ErrorRecord(long j, int i) {
            this.mStartTime = j;
            this.mCount = i;
        }

        public void setStartTime(long j) {
            this.mStartTime = j;
        }

        public void setCount(int i) {
            this.mCount = i;
        }

        public void incrementCount() {
            this.mCount++;
        }

        public void setSuccessiveRateLimitCycles(int i) {
            this.mSuccessiveRateLimitCycles = i;
        }

        public void incrementSuccessiveRateLimitCycles() {
            this.mSuccessiveRateLimitCycles++;
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public int getCount() {
            return this.mCount;
        }

        public int getSuccessiveRateLimitCycles() {
            return this.mSuccessiveRateLimitCycles;
        }

        public boolean isRepeated() {
            return this.mSuccessiveRateLimitCycles >= 2;
        }

        public int getAllowedEntries() {
            return isRepeated() ? com.android.server.am.DropboxRateLimiter.this.mStrictRatelimitAllowedEntries : com.android.server.am.DropboxRateLimiter.this.mRateLimitAllowedEntries;
        }

        public long getBufferDuration() {
            return isRepeated() ? com.android.server.am.DropboxRateLimiter.this.mStrictRateLimitBufferDuration : com.android.server.am.DropboxRateLimiter.this.mRateLimitBufferDuration;
        }

        public boolean hasExpired(long j) {
            return j - this.mStartTime > com.android.server.am.DropboxRateLimiter.this.mRateLimitBufferExpiryFactor * getBufferDuration();
        }
    }

    private static class DefaultClock implements com.android.server.am.DropboxRateLimiter.Clock {
        private DefaultClock() {
        }

        @Override // com.android.server.am.DropboxRateLimiter.Clock
        public long uptimeMillis() {
            return android.os.SystemClock.uptimeMillis();
        }
    }
}
