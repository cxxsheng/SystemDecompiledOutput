package com.android.server.translation;

/* loaded from: classes2.dex */
final class RemoteTranslationService extends com.android.internal.infra.ServiceConnector.Impl<android.service.translation.ITranslationService> {
    private static final java.lang.String TAG = com.android.server.translation.RemoteTranslationService.class.getSimpleName();
    private static final long TIMEOUT_IDLE_UNBIND_MS = 0;
    private static final int TIMEOUT_REQUEST_MS = 5000;
    private final android.content.ComponentName mComponentName;
    private final long mIdleUnbindTimeoutMs;
    private final android.os.IBinder mRemoteCallback;
    private final int mRequestTimeoutMs;

    RemoteTranslationService(android.content.Context context, android.content.ComponentName componentName, int i, boolean z, android.os.IBinder iBinder) {
        super(context, new android.content.Intent("android.service.translation.TranslationService").setComponent(componentName), z ? 4194304 : 0, i, new java.util.function.Function() { // from class: com.android.server.translation.RemoteTranslationService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.translation.ITranslationService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mIdleUnbindTimeoutMs = 0L;
        this.mRequestTimeoutMs = 5000;
        this.mComponentName = componentName;
        this.mRemoteCallback = iBinder;
        connect();
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(android.service.translation.ITranslationService iTranslationService, boolean z) {
        try {
            if (z) {
                iTranslationService.onConnected(this.mRemoteCallback);
            } else {
                iTranslationService.onDisconnected();
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }

    protected long getAutoDisconnectTimeoutMs() {
        return this.mIdleUnbindTimeoutMs;
    }

    public void onSessionCreated(@android.annotation.NonNull final android.view.translation.TranslationContext translationContext, final int i, final com.android.internal.os.IResultReceiver iResultReceiver) {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.translation.RemoteTranslationService$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.translation.ITranslationService) obj).onCreateTranslationSession(translationContext, i, iResultReceiver);
            }
        });
    }

    public void onTranslationCapabilitiesRequest(final int i, final int i2, @android.annotation.NonNull final android.os.ResultReceiver resultReceiver) {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.translation.RemoteTranslationService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.translation.ITranslationService) obj).onTranslationCapabilitiesRequest(i, i2, resultReceiver);
            }
        });
    }
}
