package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class BrowsedAuthenticationMetric {
    private static final java.lang.String TAG = "AuthenticationMetric";
    private final int mSessionIdProvider;
    private int mProviderUid = -1;
    private com.android.server.credentials.metrics.shared.ResponseCollective mAuthEntryCollective = new com.android.server.credentials.metrics.shared.ResponseCollective(java.util.Map.of(), java.util.Map.of());
    private boolean mHasException = false;
    private java.lang.String mFrameworkException = "";
    private int mProviderStatus = -1;
    private boolean mAuthReturned = false;

    public BrowsedAuthenticationMetric(int i) {
        this.mSessionIdProvider = i;
    }

    public int getSessionIdProvider() {
        return this.mSessionIdProvider;
    }

    public void setProviderUid(int i) {
        this.mProviderUid = i;
    }

    public int getProviderUid() {
        return this.mProviderUid;
    }

    public void setAuthEntryCollective(com.android.server.credentials.metrics.shared.ResponseCollective responseCollective) {
        this.mAuthEntryCollective = responseCollective;
    }

    public com.android.server.credentials.metrics.shared.ResponseCollective getAuthEntryCollective() {
        return this.mAuthEntryCollective;
    }

    public void setHasException(boolean z) {
        this.mHasException = z;
    }

    public void setFrameworkException(java.lang.String str) {
        this.mFrameworkException = str;
    }

    public void setProviderStatus(int i) {
        this.mProviderStatus = i;
    }

    public void setAuthReturned(boolean z) {
        this.mAuthReturned = z;
    }

    public boolean isAuthReturned() {
        return this.mAuthReturned;
    }

    public int getProviderStatus() {
        return this.mProviderStatus;
    }

    public java.lang.String getFrameworkException() {
        return this.mFrameworkException;
    }

    public boolean isHasException() {
        return this.mHasException;
    }
}
