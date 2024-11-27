package com.android.server.contentsuggestions;

/* loaded from: classes.dex */
public class RemoteContentSuggestionsService extends com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService<com.android.server.contentsuggestions.RemoteContentSuggestionsService, android.service.contentsuggestions.IContentSuggestionsService> {
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 2000;

    interface Callbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.contentsuggestions.RemoteContentSuggestionsService> {
    }

    RemoteContentSuggestionsService(android.content.Context context, android.content.ComponentName componentName, int i, com.android.server.contentsuggestions.RemoteContentSuggestionsService.Callbacks callbacks, boolean z, boolean z2) {
        super(context, "android.service.contentsuggestions.ContentSuggestionsService", componentName, i, callbacks, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.service.contentsuggestions.IContentSuggestionsService getServiceInterface(android.os.IBinder iBinder) {
        return android.service.contentsuggestions.IContentSuggestionsService.Stub.asInterface(iBinder);
    }

    protected long getTimeoutIdleBindMillis() {
        return 0L;
    }

    protected long getRemoteRequestMillis() {
        return TIMEOUT_REMOTE_REQUEST_MILLIS;
    }

    void provideContextImage(final int i, @android.annotation.Nullable final android.hardware.HardwareBuffer hardwareBuffer, final int i2, @android.annotation.NonNull final android.os.Bundle bundle) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentsuggestions.RemoteContentSuggestionsService$$ExternalSyntheticLambda1
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentsuggestions.IContentSuggestionsService) iInterface).provideContextImage(i, hardwareBuffer, i2, bundle);
            }
        });
    }

    void suggestContentSelections(@android.annotation.NonNull final android.app.contentsuggestions.SelectionsRequest selectionsRequest, @android.annotation.NonNull final android.app.contentsuggestions.ISelectionsCallback iSelectionsCallback) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentsuggestions.RemoteContentSuggestionsService$$ExternalSyntheticLambda3
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentsuggestions.IContentSuggestionsService) iInterface).suggestContentSelections(selectionsRequest, iSelectionsCallback);
            }
        });
    }

    void classifyContentSelections(@android.annotation.NonNull final android.app.contentsuggestions.ClassificationsRequest classificationsRequest, @android.annotation.NonNull final android.app.contentsuggestions.IClassificationsCallback iClassificationsCallback) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentsuggestions.RemoteContentSuggestionsService$$ExternalSyntheticLambda0
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentsuggestions.IContentSuggestionsService) iInterface).classifyContentSelections(classificationsRequest, iClassificationsCallback);
            }
        });
    }

    void notifyInteraction(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.os.Bundle bundle) {
        scheduleAsyncRequest(new com.android.internal.infra.AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentsuggestions.RemoteContentSuggestionsService$$ExternalSyntheticLambda2
            public final void run(android.os.IInterface iInterface) {
                ((android.service.contentsuggestions.IContentSuggestionsService) iInterface).notifyInteraction(str, bundle);
            }
        });
    }
}
