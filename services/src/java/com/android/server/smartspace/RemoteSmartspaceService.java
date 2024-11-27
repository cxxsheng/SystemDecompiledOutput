package com.android.server.smartspace;

/* loaded from: classes2.dex */
public class RemoteSmartspaceService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.smartspace.RemoteSmartspaceService, android.service.smartspace.ISmartspaceService> {
    private static final java.lang.String TAG = "RemoteSmartspaceService";
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 2000;
    private final com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks mCallback;

    public interface RemoteSmartspaceServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.smartspace.RemoteSmartspaceService> {
        void onConnectedStateChanged(boolean z);

        void onFailureOrTimeout(boolean z);
    }

    public RemoteSmartspaceService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, int i, com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks remoteSmartspaceServiceCallbacks, boolean z, boolean z2) {
        super(context, str, componentName, i, remoteSmartspaceServiceCallbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
        this.mCallback = remoteSmartspaceServiceCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.smartspace.ISmartspaceService getServiceInterface(android.os.IBinder iBinder) {
        return android.service.smartspace.ISmartspaceService.Stub.asInterface(iBinder);
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

    public void scheduleOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.smartspace.ISmartspaceService> asyncRequest) {
        scheduleAsyncRequest(asyncRequest);
    }

    public void executeOnResolvedService(@android.annotation.NonNull com.android.internal.infra.AbstractRemoteService.AsyncRequest<android.service.smartspace.ISmartspaceService> asyncRequest) {
        executeAsyncRequest(asyncRequest);
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onConnectedStateChanged(z);
        }
    }
}
