package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class CandidatePhaseMetric {
    private static final java.lang.String TAG = "CandidateProviderMetric";
    private final int mSessionIdProvider;
    private boolean mQueryReturned = false;
    private int mCandidateUid = -1;
    private long mServiceBeganTimeNanoseconds = -1;
    private long mStartQueryTimeNanoseconds = -1;
    private long mQueryFinishTimeNanoseconds = -1;
    private int mProviderQueryStatus = -1;
    private boolean mHasException = false;
    private java.lang.String mFrameworkException = "";
    private com.android.server.credentials.metrics.shared.ResponseCollective mResponseCollective = new com.android.server.credentials.metrics.shared.ResponseCollective(java.util.Map.of(), java.util.Map.of());
    private boolean mIsPrimary = false;

    public CandidatePhaseMetric(int i) {
        this.mSessionIdProvider = i;
    }

    public void setServiceBeganTimeNanoseconds(long j) {
        this.mServiceBeganTimeNanoseconds = j;
    }

    public void setStartQueryTimeNanoseconds(long j) {
        this.mStartQueryTimeNanoseconds = j;
    }

    public void setQueryFinishTimeNanoseconds(long j) {
        this.mQueryFinishTimeNanoseconds = j;
    }

    public long getServiceBeganTimeNanoseconds() {
        return this.mServiceBeganTimeNanoseconds;
    }

    public long getStartQueryTimeNanoseconds() {
        return this.mStartQueryTimeNanoseconds;
    }

    public long getQueryFinishTimeNanoseconds() {
        return this.mQueryFinishTimeNanoseconds;
    }

    public int getQueryLatencyMicroseconds() {
        return (int) ((getQueryFinishTimeNanoseconds() - getStartQueryTimeNanoseconds()) / 1000);
    }

    public int getTimestampFromReferenceStartMicroseconds(long j) {
        if (j < this.mServiceBeganTimeNanoseconds) {
            android.util.Slog.i(TAG, "The timestamp is before service started, falling back to default int");
            return -1;
        }
        return (int) ((j - this.mServiceBeganTimeNanoseconds) / 1000);
    }

    public void setProviderQueryStatus(int i) {
        this.mProviderQueryStatus = i;
    }

    public int getProviderQueryStatus() {
        return this.mProviderQueryStatus;
    }

    public void setCandidateUid(int i) {
        this.mCandidateUid = i;
    }

    public int getCandidateUid() {
        return this.mCandidateUid;
    }

    public int getSessionIdProvider() {
        return this.mSessionIdProvider;
    }

    public void setQueryReturned(boolean z) {
        this.mQueryReturned = z;
    }

    public boolean isQueryReturned() {
        return this.mQueryReturned;
    }

    public void setHasException(boolean z) {
        this.mHasException = z;
    }

    public boolean isHasException() {
        return this.mHasException;
    }

    public void setResponseCollective(com.android.server.credentials.metrics.shared.ResponseCollective responseCollective) {
        this.mResponseCollective = responseCollective;
    }

    public com.android.server.credentials.metrics.shared.ResponseCollective getResponseCollective() {
        return this.mResponseCollective;
    }

    public void setFrameworkException(java.lang.String str) {
        this.mFrameworkException = str;
    }

    public java.lang.String getFrameworkException() {
        return this.mFrameworkException;
    }

    public void setPrimary(boolean z) {
        this.mIsPrimary = z;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }
}
