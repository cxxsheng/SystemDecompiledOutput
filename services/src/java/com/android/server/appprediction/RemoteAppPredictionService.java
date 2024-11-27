package com.android.server.appprediction;

/* loaded from: classes.dex */
public class RemoteAppPredictionService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.appprediction.RemoteAppPredictionService, android.service.appprediction.IPredictionService> {
    private static final java.lang.String TAG = "RemoteAppPredictionService";
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 2000;
    private final com.android.server.appprediction.RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks mCallback;

    public interface RemoteAppPredictionServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.appprediction.RemoteAppPredictionService> {
        void onConnectedStateChanged(boolean z);

        void onFailureOrTimeout(boolean z);
    }

    public RemoteAppPredictionService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.server.appprediction.RemoteAppPredictionService.RemoteAppPredictionServiceCallbacks remoteAppPredictionServiceCallbacks, boolean z, boolean z2) {
        super(context, str, componentName, i, remoteAppPredictionServiceCallbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
        this.mCallback = remoteAppPredictionServiceCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.appprediction.IPredictionService getServiceInterface(android.os.IBinder iBinder) {
        return android.service.appprediction.IPredictionService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return 0L;
    }

    protected long getRemoteRequestMillis() {
        return TIMEOUT_REMOTE_REQUEST_MILLIS;
    }

    public void reconnect() {
        super.scheduleBind();
    }

    public void scheduleOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.appprediction.IPredictionService> asyncRequest) {
        scheduleAsyncRequest(asyncRequest);
    }

    public void executeOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.appprediction.IPredictionService> asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onConnectedStateChanged(z);
        }
    }
}
