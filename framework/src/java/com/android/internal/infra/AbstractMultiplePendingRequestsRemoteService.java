package com.android.internal.infra;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public abstract class AbstractMultiplePendingRequestsRemoteService<S extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<S, I>, I extends android.os.IInterface> extends com.android.internal.infra.AbstractRemoteService<S, I> {
    private final int mInitialCapacity;
    protected java.util.List<com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I>> mPendingRequests;

    public AbstractMultiplePendingRequestsRemoteService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.internal.infra.AbstractRemoteService.VultureCallback<S> vultureCallback, android.os.Handler handler, int i2, boolean z, int i3) {
        super(context, str, componentName, i, vultureCallback, handler, i2, z);
        this.mInitialCapacity = i3;
        this.mPendingRequests = new java.util.ArrayList(this.mInitialCapacity);
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequests() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "Sending " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                handlePendingRequest(this.mPendingRequests.get(i));
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    protected void handleOnDestroy() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "Canceling " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                this.mPendingRequests.get(i).cancel();
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    final void handleBindFailure() {
        synchronized (this.mPendingRequests) {
            int size = this.mPendingRequests.size();
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "Sending failure to " + size + " pending requests");
            }
            for (int i = 0; i < size; i++) {
                com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequests.get(i);
                basePendingRequest.onFailed();
                basePendingRequest.finish();
            }
            this.mPendingRequests.clear();
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        int size;
        super.dump(str, printWriter);
        printWriter.append((java.lang.CharSequence) str).append("initialCapacity=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mInitialCapacity)).println();
        synchronized (this.mPendingRequests) {
            size = this.mPendingRequests.size();
        }
        printWriter.append((java.lang.CharSequence) str).append("pendingRequests=").append((java.lang.CharSequence) java.lang.String.valueOf(size)).println();
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequestWhileUnBound(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        synchronized (this.mPendingRequests) {
            this.mPendingRequests.add(basePendingRequest);
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "queued " + this.mPendingRequests.size() + " requests; last=" + basePendingRequest);
            }
        }
    }
}
