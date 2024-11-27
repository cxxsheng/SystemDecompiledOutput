package com.android.server.contentprotection;

/* loaded from: classes.dex */
public class RemoteContentProtectionService extends com.android.internal.infra.ServiceConnector.Impl<android.service.contentcapture.IContentProtectionService> {
    private static final java.lang.String TAG = com.android.server.contentprotection.RemoteContentProtectionService.class.getSimpleName();
    private final long mAutoDisconnectTimeoutMs;

    @android.annotation.NonNull
    private final android.content.ComponentName mComponentName;

    public RemoteContentProtectionService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, int i, boolean z, long j) {
        super(context, new android.content.Intent("android.service.contentcapture.ContentProtectionService").setComponent(componentName), z ? 4194304 : 0, i, new java.util.function.Function() { // from class: com.android.server.contentprotection.RemoteContentProtectionService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.contentcapture.IContentProtectionService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mComponentName = componentName;
        this.mAutoDisconnectTimeoutMs = j;
    }

    protected long getAutoDisconnectTimeoutMs() {
        return this.mAutoDisconnectTimeoutMs;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(@android.annotation.NonNull android.service.contentcapture.IContentProtectionService iContentProtectionService, boolean z) {
        java.lang.String str = TAG;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Connection status for: ");
        sb.append(this.mComponentName);
        sb.append(" changed to: ");
        sb.append(z ? "connected" : "disconnected");
        android.util.Slog.i(str, sb.toString());
    }

    public void onLoginDetected(@android.annotation.NonNull final android.content.pm.ParceledListSlice<android.view.contentcapture.ContentCaptureEvent> parceledListSlice) {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.contentprotection.RemoteContentProtectionService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.contentcapture.IContentProtectionService) obj).onLoginDetected(parceledListSlice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUpdateAllowlistRequest$1(android.service.contentcapture.IContentProtectionAllowlistCallback iContentProtectionAllowlistCallback, android.service.contentcapture.IContentProtectionService iContentProtectionService) throws java.lang.Exception {
        iContentProtectionService.onUpdateAllowlistRequest(iContentProtectionAllowlistCallback.asBinder());
    }

    public void onUpdateAllowlistRequest(@android.annotation.NonNull final android.service.contentcapture.IContentProtectionAllowlistCallback iContentProtectionAllowlistCallback) {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.contentprotection.RemoteContentProtectionService$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.contentprotection.RemoteContentProtectionService.lambda$onUpdateAllowlistRequest$1(iContentProtectionAllowlistCallback, (android.service.contentcapture.IContentProtectionService) obj);
            }
        });
    }
}
