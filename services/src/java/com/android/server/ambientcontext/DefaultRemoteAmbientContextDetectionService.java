package com.android.server.ambientcontext;

/* loaded from: classes.dex */
final class DefaultRemoteAmbientContextDetectionService extends com.android.internal.infra.ServiceConnector.Impl<android.service.ambientcontext.IAmbientContextDetectionService> implements com.android.server.ambientcontext.RemoteAmbientDetectionService {
    private static final java.lang.String TAG = com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService.class.getSimpleName();

    DefaultRemoteAmbientContextDetectionService(android.content.Context context, android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.ambientcontext.AmbientContextDetectionService").setComponent(componentName), 67112960, i, new java.util.function.Function() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.ambientcontext.IAmbientContextDetectionService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void startDetection(@android.annotation.NonNull final android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, final java.lang.String str, final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
        android.util.Slog.i(TAG, "Start detection for " + ambientContextEventRequest.getEventTypes());
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.ambientcontext.IAmbientContextDetectionService) obj).startDetection(ambientContextEventRequest, str, remoteCallback, remoteCallback2);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void stopDetection(final java.lang.String str) {
        android.util.Slog.i(TAG, "Stop detection for " + str);
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.ambientcontext.IAmbientContextDetectionService) obj).stopDetection(str);
            }
        });
    }

    @Override // com.android.server.ambientcontext.RemoteAmbientDetectionService
    public void queryServiceStatus(final int[] iArr, final java.lang.String str, final android.os.RemoteCallback remoteCallback) {
        android.util.Slog.i(TAG, "Query status for " + str);
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.ambientcontext.DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda3
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.ambientcontext.IAmbientContextDetectionService) obj).queryServiceStatus(iArr, str, remoteCallback);
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
