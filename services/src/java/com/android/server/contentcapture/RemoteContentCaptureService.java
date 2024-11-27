package com.android.server.contentcapture;

/* loaded from: classes.dex */
final class RemoteContentCaptureService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.contentcapture.RemoteContentCaptureService, android.service.contentcapture.IContentCaptureService> {
    private final int mIdleUnbindTimeoutMs;
    private final com.android.server.contentcapture.ContentCapturePerUserService mPerUserService;
    private final android.os.IBinder mServerCallback;

    public interface ContentCaptureServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.contentcapture.RemoteContentCaptureService> {
    }

    RemoteContentCaptureService(android.content.Context context, java.lang.String str, android.content.ComponentName componentName, android.service.contentcapture.IContentCaptureServiceCallback iContentCaptureServiceCallback, int i, com.android.server.contentcapture.ContentCapturePerUserService contentCapturePerUserService, boolean z, boolean z2, int i2) {
        super(context, str, componentName, i, contentCapturePerUserService, context.getMainThreadHandler(), (z ? 4194304 : 0) | 4096, z2, 2);
        this.mPerUserService = contentCapturePerUserService;
        this.mServerCallback = iContentCaptureServiceCallback.asBinder();
        this.mIdleUnbindTimeoutMs = i2;
        ensureBoundLocked();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.contentcapture.IContentCaptureService getServiceInterface(@android.annotation.NonNull android.os.IBinder iBinder) {
        return android.service.contentcapture.IContentCaptureService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return this.mIdleUnbindTimeoutMs;
    }

    protected void handleOnConnectedStateChanged(boolean z) {
        if (z && getTimeoutIdleBindMillis() != 0) {
            scheduleUnbind();
        }
        try {
            if (z) {
                try {
                    ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mService.onConnected(this.mServerCallback, android.view.contentcapture.ContentCaptureHelper.sVerbose, android.view.contentcapture.ContentCaptureHelper.sDebug);
                    com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(1, ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
                    android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_CONNECT_STATE_CHANGED, java.lang.Integer.valueOf(this.mPerUserService.getUserId()), 1, java.lang.Integer.valueOf(com.android.internal.util.CollectionUtils.size(this.mPerUserService.getContentCaptureAllowlist())));
                    this.mPerUserService.onConnected();
                    return;
                } catch (java.lang.Throwable th) {
                    this.mPerUserService.onConnected();
                    throw th;
                }
            }
            ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mService.onDisconnected();
            com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(2, ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
            android.util.EventLog.writeEvent(com.android.server.contentcapture.EventLogTags.CC_CONNECT_STATE_CHANGED, java.lang.Integer.valueOf(this.mPerUserService.getUserId()), 2, 0);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mTag, "Exception calling onConnectedStateChanged(" + z + "): " + e);
        }
    }

    public void ensureBoundLocked() {
        scheduleBind();
    }

    public void onSessionStarted(@android.annotation.Nullable final android.view.contentcapture.ContentCaptureContext contentCaptureContext, final int i, final int i2, @android.annotation.NonNull final com.android.internal.os.IResultReceiver iResultReceiver, final int i3) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onSessionStarted(contentCaptureContext, i, i2, iResultReceiver, i3);
            }
        });
        com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 1, i3, getComponentName(), false);
    }

    public void onSessionFinished(final int i) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onSessionFinished(i);
            }
        });
        com.android.server.contentcapture.ContentCaptureMetricsLogger.writeSessionEvent(i, 2, 0, getComponentName(), false);
    }

    public void onActivitySnapshotRequest(final int i, @android.annotation.NonNull final android.service.contentcapture.SnapshotData snapshotData) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda4
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onActivitySnapshot(i, snapshotData);
            }
        });
    }

    public void onDataRemovalRequest(@android.annotation.NonNull final android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda5
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onDataRemovalRequest(dataRemovalRequest);
            }
        });
        com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(5, ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
    }

    public void onDataShareRequest(@android.annotation.NonNull final android.view.contentcapture.DataShareRequest dataShareRequest, @android.annotation.NonNull final android.service.contentcapture.IDataShareCallback.Stub stub) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda3
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onDataShared(dataShareRequest, stub);
            }
        });
        com.android.server.contentcapture.ContentCaptureMetricsLogger.writeServiceEvent(6, ((com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService) this).mComponentName);
    }

    public void onActivityLifecycleEvent(@android.annotation.NonNull final android.service.contentcapture.ActivityEvent activityEvent) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentcapture.RemoteContentCaptureService$$ExternalSyntheticLambda2
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentcapture.IContentCaptureService) iInterface).onActivityEvent(activityEvent);
            }
        });
    }
}
