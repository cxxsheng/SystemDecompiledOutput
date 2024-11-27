package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class InitialPhaseMetric {
    private static final java.lang.String TAG = "InitialPhaseMetric";
    private final int mSessionIdCaller;
    private int mApiName = com.android.server.credentials.metrics.ApiName.UNKNOWN.getMetricCode();
    private int mCallerUid = -1;
    private long mCredentialServiceStartedTimeNanoseconds = -1;
    private long mCredentialServiceBeginQueryTimeNanoseconds = -1;
    private boolean mOriginSpecified = false;
    private java.util.Map<java.lang.String, java.lang.Integer> mRequestCounts = new java.util.LinkedHashMap();
    private int mAutofillSessionId = -1;
    private int mAutofillRequestId = -1;

    public InitialPhaseMetric(int i) {
        this.mSessionIdCaller = i;
    }

    public int getServiceStartToQueryLatencyMicroseconds() {
        return (int) ((this.mCredentialServiceStartedTimeNanoseconds - this.mCredentialServiceBeginQueryTimeNanoseconds) / 1000);
    }

    public void setCredentialServiceStartedTimeNanoseconds(long j) {
        this.mCredentialServiceStartedTimeNanoseconds = j;
    }

    public void setCredentialServiceBeginQueryTimeNanoseconds(long j) {
        this.mCredentialServiceBeginQueryTimeNanoseconds = j;
    }

    public long getCredentialServiceStartedTimeNanoseconds() {
        return this.mCredentialServiceStartedTimeNanoseconds;
    }

    public long getCredentialServiceBeginQueryTimeNanoseconds() {
        return this.mCredentialServiceBeginQueryTimeNanoseconds;
    }

    public void setApiName(int i) {
        this.mApiName = i;
    }

    public int getApiName() {
        return this.mApiName;
    }

    public void setCallerUid(int i) {
        this.mCallerUid = i;
    }

    public int getCallerUid() {
        return this.mCallerUid;
    }

    public int getSessionIdCaller() {
        return this.mSessionIdCaller;
    }

    public int getCountRequestClassType() {
        return this.mRequestCounts.size();
    }

    public void setOriginSpecified(boolean z) {
        this.mOriginSpecified = z;
    }

    public boolean isOriginSpecified() {
        return this.mOriginSpecified;
    }

    public void setAutofillSessionId(int i) {
        this.mAutofillSessionId = i;
    }

    public int getAutofillSessionId() {
        return this.mAutofillSessionId;
    }

    public void setAutofillRequestId(int i) {
        this.mAutofillRequestId = i;
    }

    public int getAutofillRequestId() {
        return this.mAutofillRequestId;
    }

    public void setRequestCounts(java.util.Map<java.lang.String, java.lang.Integer> map) {
        this.mRequestCounts = map;
    }

    public java.lang.String[] getUniqueRequestStrings() {
        java.lang.String[] strArr = new java.lang.String[this.mRequestCounts.keySet().size()];
        this.mRequestCounts.keySet().toArray(strArr);
        return strArr;
    }

    public int[] getUniqueRequestCounts() {
        return this.mRequestCounts.values().stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
    }
}
