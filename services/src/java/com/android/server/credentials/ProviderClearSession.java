package com.android.server.credentials;

/* loaded from: classes.dex */
public final class ProviderClearSession extends com.android.server.credentials.ProviderSession<android.service.credentials.ClearCredentialStateRequest, java.lang.Void> implements com.android.server.credentials.RemoteCredentialService.ProviderCallbacks<java.lang.Void> {
    private static final java.lang.String TAG = "ProviderClearSession";
    private android.credentials.ClearCredentialStateException mProviderException;

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderClearSession createNewSession(android.content.Context context, int i, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.ClearRequestSession clearRequestSession, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        return new com.android.server.credentials.ProviderClearSession(context, credentialProviderInfo, clearRequestSession, i, remoteCredentialService, createProviderRequest((android.credentials.ClearCredentialStateRequest) clearRequestSession.mClientRequest, clearRequestSession.mClientAppInfo));
    }

    @android.annotation.Nullable
    private static android.service.credentials.ClearCredentialStateRequest createProviderRequest(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.service.credentials.CallingAppInfo callingAppInfo) {
        return new android.service.credentials.ClearCredentialStateRequest(callingAppInfo, clearCredentialStateRequest.getData());
    }

    public ProviderClearSession(android.content.Context context, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.ProviderSession.ProviderInternalCallback providerInternalCallback, int i, com.android.server.credentials.RemoteCredentialService remoteCredentialService, android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest) {
        super(context, clearCredentialStateRequest, providerInternalCallback, credentialProviderInfo.getComponentName(), i, remoteCredentialService);
        setStatus(com.android.server.credentials.ProviderSession.Status.PENDING);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(@android.annotation.Nullable java.lang.Void r2) {
        android.util.Slog.i(TAG, "Remote provider responded with a valid response: " + this.mComponentName);
        this.mProviderResponseSet = true;
        updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.COMPLETE, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, java.lang.Exception exc) {
        if (exc instanceof android.credentials.ClearCredentialStateException) {
            this.mProviderException = (android.credentials.ClearCredentialStateException) exc;
            this.mProviderSessionMetric.collectCandidateFrameworkException(this.mProviderException.getType());
        }
        this.mProviderSessionMetric.collectCandidateExceptionStatus(true);
        updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.CANCELED, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderServiceDied(com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        if (remoteCredentialService.getComponentName().equals(this.mComponentName)) {
            updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.SERVICE_DEAD, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            android.util.Slog.w(TAG, "Component names different in onProviderServiceDied - this should not happen");
        }
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderCancellable(android.os.ICancellationSignal iCancellationSignal) {
        this.mProviderCancellationSignal = iCancellationSignal;
    }

    @Override // com.android.server.credentials.ProviderSession
    @android.annotation.Nullable
    /* renamed from: prepareUiData */
    protected android.credentials.selection.ProviderData mo3078prepareUiData() {
        return null;
    }

    @Override // com.android.server.credentials.ProviderSession
    protected void onUiEntrySelected(java.lang.String str, java.lang.String str2, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.credentials.ProviderSession
    protected void invokeSession() {
        if (this.mRemoteCredentialService != null) {
            startCandidateMetrics();
            this.mRemoteCredentialService.setCallback(this);
            this.mRemoteCredentialService.onClearCredentialState((android.service.credentials.ClearCredentialStateRequest) this.mProviderRequest);
        }
    }
}
