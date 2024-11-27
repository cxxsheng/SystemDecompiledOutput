package com.android.server.utils.quota;

/* loaded from: classes2.dex */
public class MultiRateLimiter {
    private static final com.android.server.utils.quota.CountQuotaTracker[] EMPTY_TRACKER_ARRAY = new com.android.server.utils.quota.CountQuotaTracker[0];
    private static final java.lang.String TAG = "MultiRateLimiter";
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.quota.CountQuotaTracker[] mQuotaTrackers;

    private MultiRateLimiter(java.util.List<com.android.server.utils.quota.CountQuotaTracker> list) {
        this.mLock = new java.lang.Object();
        this.mQuotaTrackers = (com.android.server.utils.quota.CountQuotaTracker[]) list.toArray(EMPTY_TRACKER_ARRAY);
    }

    public void noteEvent(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        synchronized (this.mLock) {
            noteEventLocked(i, str, str2);
        }
    }

    public boolean isWithinQuota(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        boolean isWithinQuotaLocked;
        synchronized (this.mLock) {
            isWithinQuotaLocked = isWithinQuotaLocked(i, str, str2);
        }
        return isWithinQuotaLocked;
    }

    public void clear(int i, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            clearLocked(i, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void noteEventLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        for (com.android.server.utils.quota.CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            countQuotaTracker.noteEvent(i, str, str2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isWithinQuotaLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        for (com.android.server.utils.quota.CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            if (!countQuotaTracker.isWithinQuota(i, str, str2)) {
                return false;
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void clearLocked(int i, @android.annotation.NonNull java.lang.String str) {
        for (com.android.server.utils.quota.CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            countQuotaTracker.onAppRemovedLocked(i, str);
        }
    }

    public static class Builder {
        private final com.android.server.utils.quota.Categorizer mCategorizer;
        private final com.android.server.utils.quota.Category mCategory;
        private final android.content.Context mContext;

        @android.annotation.Nullable
        private final com.android.server.utils.quota.QuotaTracker.Injector mInjector;
        private final java.util.List<com.android.server.utils.quota.CountQuotaTracker> mQuotaTrackers;

        @com.android.internal.annotations.VisibleForTesting
        Builder(android.content.Context context, com.android.server.utils.quota.QuotaTracker.Injector injector) {
            this.mQuotaTrackers = new java.util.ArrayList();
            this.mContext = context;
            this.mInjector = injector;
            this.mCategorizer = com.android.server.utils.quota.Categorizer.SINGLE_CATEGORIZER;
            this.mCategory = com.android.server.utils.quota.Category.SINGLE_CATEGORY;
        }

        public Builder(android.content.Context context) {
            this(context, null);
        }

        public com.android.server.utils.quota.MultiRateLimiter.Builder addRateLimit(int i, java.time.Duration duration) {
            com.android.server.utils.quota.CountQuotaTracker countQuotaTracker;
            if (this.mInjector != null) {
                countQuotaTracker = new com.android.server.utils.quota.CountQuotaTracker(this.mContext, this.mCategorizer, this.mInjector);
            } else {
                countQuotaTracker = new com.android.server.utils.quota.CountQuotaTracker(this.mContext, this.mCategorizer);
            }
            countQuotaTracker.setCountLimit(this.mCategory, i, duration.toMillis());
            this.mQuotaTrackers.add(countQuotaTracker);
            return this;
        }

        public com.android.server.utils.quota.MultiRateLimiter.Builder addRateLimit(@android.annotation.NonNull com.android.server.utils.quota.MultiRateLimiter.RateLimit rateLimit) {
            return addRateLimit(rateLimit.mLimit, rateLimit.mWindowSize);
        }

        public com.android.server.utils.quota.MultiRateLimiter.Builder addRateLimits(@android.annotation.NonNull com.android.server.utils.quota.MultiRateLimiter.RateLimit[] rateLimitArr) {
            for (com.android.server.utils.quota.MultiRateLimiter.RateLimit rateLimit : rateLimitArr) {
                addRateLimit(rateLimit);
            }
            return this;
        }

        public com.android.server.utils.quota.MultiRateLimiter build() {
            return new com.android.server.utils.quota.MultiRateLimiter(this.mQuotaTrackers);
        }
    }

    public static class RateLimit {
        public final int mLimit;
        public final java.time.Duration mWindowSize;

        private RateLimit(int i, java.time.Duration duration) {
            this.mLimit = i;
            this.mWindowSize = duration;
        }

        public static com.android.server.utils.quota.MultiRateLimiter.RateLimit create(int i, java.time.Duration duration) {
            return new com.android.server.utils.quota.MultiRateLimiter.RateLimit(i, duration);
        }
    }
}
