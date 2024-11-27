package com.android.server.searchui;

/* loaded from: classes2.dex */
public class RemoteSearchUiService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.searchui.RemoteSearchUiService, android.service.search.ISearchUiService> {
    private static final java.lang.String TAG = "RemoteSearchUiService";
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 2000;
    private final com.android.server.searchui.RemoteSearchUiService.RemoteSearchUiServiceCallbacks mCallback;

    public interface RemoteSearchUiServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.searchui.RemoteSearchUiService> {
        void onConnectedStateChanged(boolean z);

        void onFailureOrTimeout(boolean z);
    }

    public RemoteSearchUiService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.server.searchui.RemoteSearchUiService.RemoteSearchUiServiceCallbacks remoteSearchUiServiceCallbacks, boolean z, boolean z2) {
        super(context, str, componentName, i, remoteSearchUiServiceCallbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
        this.mCallback = remoteSearchUiServiceCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.search.ISearchUiService getServiceInterface(android.os.IBinder iBinder) {
        return android.service.search.ISearchUiService.Stub.asInterface(iBinder);
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

    public void scheduleOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.search.ISearchUiService> asyncRequest) {
        scheduleAsyncRequest(asyncRequest);
    }

    public void executeOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.search.ISearchUiService> asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onConnectedStateChanged(z);
        }
    }
}
