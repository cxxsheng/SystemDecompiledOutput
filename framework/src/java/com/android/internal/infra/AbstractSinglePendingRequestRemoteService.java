package com.android.internal.infra;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public abstract class AbstractSinglePendingRequestRemoteService<S extends com.android.internal.infra.AbstractSinglePendingRequestRemoteService<S, I>, I extends android.os.IInterface> extends com.android.internal.infra.AbstractRemoteService<S, I> {
    protected com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> mPendingRequest;

    public AbstractSinglePendingRequestRemoteService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.internal.infra.AbstractRemoteService.VultureCallback<S> vultureCallback, android.os.Handler handler, int i2, boolean z) {
        super(context, str, componentName, i, vultureCallback, handler, i2, z);
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequests() {
        if (this.mPendingRequest != null) {
            com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequest;
            this.mPendingRequest = null;
            handlePendingRequest(basePendingRequest);
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    protected void handleOnDestroy() {
        if (this.mPendingRequest != null) {
            this.mPendingRequest.cancel();
            this.mPendingRequest = null;
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handleBindFailure() {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "Sending failure to " + this.mPendingRequest);
            }
            this.mPendingRequest.onFailed();
            this.mPendingRequest = null;
        }
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dump(str, printWriter);
        printWriter.append((java.lang.CharSequence) str).append("hasPendingRequest=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mPendingRequest != null)).println();
    }

    @Override // com.android.internal.infra.AbstractRemoteService
    void handlePendingRequestWhileUnBound(com.android.internal.infra.AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                android.util.Slog.v(this.mTag, "handlePendingRequestWhileUnBound(): cancelling " + this.mPendingRequest + " to handle " + basePendingRequest);
            }
            this.mPendingRequest.cancel();
        }
        this.mPendingRequest = basePendingRequest;
    }
}
