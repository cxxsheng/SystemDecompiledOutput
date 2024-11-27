package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class ChosenProviderFinalPhaseMetric {
    private static final java.lang.String TAG = "ChosenFinalPhaseMetric";
    private final int mSessionIdCaller;
    private final int mSessionIdProvider;
    private boolean mUiReturned = false;
    private int mChosenUid = -1;
    private int mPreQueryPhaseLatencyMicroseconds = -1;
    private int mQueryPhaseLatencyMicroseconds = -1;
    private long mServiceBeganTimeNanoseconds = -1;
    private long mQueryStartTimeNanoseconds = -1;
    private long mQueryEndTimeNanoseconds = -1;
    private long mUiCallStartTimeNanoseconds = -1;
    private long mUiCallEndTimeNanoseconds = -1;
    private long mFinalFinishTimeNanoseconds = -1;
    private int mChosenProviderStatus = -1;
    private boolean mHasException = false;
    private java.lang.String mFrameworkException = "";
    private com.android.server.credentials.metrics.shared.ResponseCollective mResponseCollective = new com.android.server.credentials.metrics.shared.ResponseCollective(java.util.Map.of(), java.util.Map.of());
    private boolean mIsPrimary = false;

    public ChosenProviderFinalPhaseMetric(int i, int i2) {
        this.mSessionIdCaller = i;
        this.mSessionIdProvider = i2;
    }

    public int getChosenUid() {
        return this.mChosenUid;
    }

    public void setChosenUid(int i) {
        this.mChosenUid = i;
    }

    public void setPreQueryPhaseLatencyMicroseconds(int i) {
        this.mPreQueryPhaseLatencyMicroseconds = i;
    }

    public void setQueryPhaseLatencyMicroseconds(int i) {
        this.mQueryPhaseLatencyMicroseconds = i;
    }

    public int getPreQueryPhaseLatencyMicroseconds() {
        return this.mPreQueryPhaseLatencyMicroseconds;
    }

    public int getQueryPhaseLatencyMicroseconds() {
        return this.mQueryPhaseLatencyMicroseconds;
    }

    public int getUiPhaseLatencyMicroseconds() {
        return (int) ((this.mUiCallEndTimeNanoseconds - this.mUiCallStartTimeNanoseconds) / 1000);
    }

    public int getEntireProviderLatencyMicroseconds() {
        return (int) ((this.mFinalFinishTimeNanoseconds - this.mQueryStartTimeNanoseconds) / 1000);
    }

    public int getEntireLatencyMicroseconds() {
        return (int) ((this.mFinalFinishTimeNanoseconds - this.mServiceBeganTimeNanoseconds) / 1000);
    }

    public void setServiceBeganTimeNanoseconds(long j) {
        this.mServiceBeganTimeNanoseconds = j;
    }

    public void setQueryStartTimeNanoseconds(long j) {
        this.mQueryStartTimeNanoseconds = j;
    }

    public void setQueryEndTimeNanoseconds(long j) {
        this.mQueryEndTimeNanoseconds = j;
    }

    public void setUiCallStartTimeNanoseconds(long j) {
        this.mUiCallStartTimeNanoseconds = j;
    }

    public void setUiCallEndTimeNanoseconds(long j) {
        this.mUiCallEndTimeNanoseconds = j;
    }

    public void setFinalFinishTimeNanoseconds(long j) {
        this.mFinalFinishTimeNanoseconds = j;
    }

    public long getServiceBeganTimeNanoseconds() {
        return this.mServiceBeganTimeNanoseconds;
    }

    public long getQueryStartTimeNanoseconds() {
        return this.mQueryStartTimeNanoseconds;
    }

    public long getQueryEndTimeNanoseconds() {
        return this.mQueryEndTimeNanoseconds;
    }

    public long getUiCallStartTimeNanoseconds() {
        return this.mUiCallStartTimeNanoseconds;
    }

    public long getUiCallEndTimeNanoseconds() {
        return this.mUiCallEndTimeNanoseconds;
    }

    public long getFinalFinishTimeNanoseconds() {
        return this.mFinalFinishTimeNanoseconds;
    }

    public int getTimestampFromReferenceStartMicroseconds(long j) {
        if (j < this.mServiceBeganTimeNanoseconds) {
            android.util.Slog.i(TAG, "The timestamp is before service started, falling back to default int");
            return -1;
        }
        return (int) ((j - this.mServiceBeganTimeNanoseconds) / 1000);
    }

    public int getChosenProviderStatus() {
        return this.mChosenProviderStatus;
    }

    public void setChosenProviderStatus(int i) {
        this.mChosenProviderStatus = i;
    }

    public int getSessionIdProvider() {
        return this.mSessionIdProvider;
    }

    public void setUiReturned(boolean z) {
        this.mUiReturned = z;
    }

    public boolean isUiReturned() {
        return this.mUiReturned;
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

    public int getSessionIdCaller() {
        return this.mSessionIdCaller;
    }

    public void setPrimary(boolean z) {
        this.mIsPrimary = z;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }
}
