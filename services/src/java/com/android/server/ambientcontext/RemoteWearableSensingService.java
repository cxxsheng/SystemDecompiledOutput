package com.android.server.ambientcontext;

/* loaded from: classes.dex */
final class RemoteWearableSensingService extends com.android.internal.infra.ServiceConnector.Impl<android.service.wearable.IWearableSensingService> implements com.android.server.ambientcontext.RemoteAmbientDetectionService {
    private static final java.lang.String TAG = com.android.server.ambientcontext.RemoteWearableSensingService.class.getSimpleName();

    RemoteWearableSensingService(android.content.Context context, android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.wearable.WearableSensingService").setComponent(componentName), 67112960, i, new com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda1());
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void startDetection(@android.annotation.NonNull final android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, final java.lang.String str, final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
        android.util.Slog.i(TAG, "Start detection for " + ambientContextEventRequest.getEventTypes());
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).startDetection(ambientContextEventRequest, str, remoteCallback, remoteCallback2);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void stopDetection(final java.lang.String str) {
        android.util.Slog.i(TAG, "Stop detection for " + str);
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda3
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).stopDetection(str);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void queryServiceStatus(final int[] iArr, final java.lang.String str, final android.os.RemoteCallback remoteCallback) {
        android.util.Slog.i(TAG, "Query status for " + str);
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.wearable.IWearableSensingService) obj).queryServiceStatus(iArr, str, remoteCallback);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void dump(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        super.dump(str, printWriter);
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void unbind() {
        super.unbind();
    }
}
