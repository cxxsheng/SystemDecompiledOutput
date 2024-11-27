package com.android.server.credentials;

/* loaded from: classes.dex */
public final class ClearRequestSession extends com.android.server.credentials.RequestSession<android.credentials.ClearCredentialStateRequest, android.credentials.IClearCredentialStateCallback, java.lang.Void> implements com.android.server.credentials.ProviderSession.ProviderInternalCallback<java.lang.Void> {
    private static final java.lang.String TAG = "GetRequestSession";

    @Override // com.android.server.credentials.RequestSession
    public /* bridge */ /* synthetic */ void addProviderSession(android.content.ComponentName componentName, com.android.server.credentials.ProviderSession providerSession) {
        super.addProviderSession(componentName, providerSession);
    }

    @Override // com.android.server.credentials.RequestSession, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public /* bridge */ /* synthetic */ void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver) {
        super.onUiSelection(userSelectionDialogResult, resultReceiver);
    }

    public ClearRequestSession(android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, android.credentials.IClearCredentialStateCallback iClearCredentialStateCallback, android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, android.os.CancellationSignal cancellationSignal, long j) {
        super(context, sessionLifetime, obj, i, i2, clearCredentialStateRequest, iClearCredentialStateCallback, "android.credentials.selection.TYPE_UNDEFINED", callingAppInfo, set, cancellationSignal, j, true);
    }

    @Override // com.android.server.credentials.RequestSession
    @android.annotation.Nullable
    public com.android.server.credentials.ProviderSession initiateProviderSession(android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        com.android.server.credentials.ProviderClearSession createNewSession = com.android.server.credentials.ProviderClearSession.createNewSession(this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
        if (createNewSession != null) {
            android.util.Slog.i(TAG, "Provider session created and being added for: " + credentialProviderInfo.getComponentName());
            this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
        }
        return createNewSession;
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(com.android.server.credentials.ProviderSession.Status status, android.content.ComponentName componentName, com.android.server.credentials.ProviderSession.CredentialsSource credentialsSource) {
        android.util.Slog.i(TAG, "Provider changed with status: " + status + ", and source: " + credentialsSource);
        if (com.android.server.credentials.ProviderSession.isTerminatingStatus(status)) {
            android.util.Slog.i(TAG, "Provider terminating status");
            onProviderTerminated(componentName);
        } else if (com.android.server.credentials.ProviderSession.isCompletionStatus(status)) {
            android.util.Slog.i(TAG, "Provider has completion status");
            onProviderResponseComplete(componentName);
        }
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalResponseReceived(android.content.ComponentName componentName, java.lang.Void r5) {
        this.mRequestSessionMetric.collectUiResponseData(true, java.lang.System.nanoTime());
        this.mRequestSessionMetric.updateMetricsOnResponseReceived(this.mProviders, componentName, isPrimaryProviderViaProviderInfo(componentName));
        respondToClientWithResponseAndFinish(null);
    }

    protected void onProviderResponseComplete(android.content.ComponentName componentName) {
        if (!isAnyProviderPending()) {
            onFinalResponseReceived(componentName, (java.lang.Void) null);
        }
    }

    protected void onProviderTerminated(android.content.ComponentName componentName) {
        if (!isAnyProviderPending()) {
            processResponses();
        }
    }

    @Override // com.android.server.credentials.RequestSession
    protected void launchUiWithProviderData(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackSuccess(java.lang.Void r1) throws android.os.RemoteException {
        ((android.credentials.IClearCredentialStateCallback) this.mClientCallback).onSuccess();
    }

    @Override // com.android.server.credentials.RequestSession
    protected void invokeClientCallbackError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        ((android.credentials.IClearCredentialStateCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalErrorReceived(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
    }

    private void processResponses() {
        java.util.Iterator<com.android.server.credentials.ProviderSession> it = this.mProviders.values().iterator();
        while (it.hasNext()) {
            if (it.next().isProviderResponseSet().booleanValue()) {
                respondToClientWithResponseAndFinish(null);
                return;
            }
        }
        this.mRequestSessionMetric.collectFrameworkException("android.credentials.ClearCredentialStateException.TYPE_UNKNOWN");
        respondToClientWithErrorAndFinish("android.credentials.ClearCredentialStateException.TYPE_UNKNOWN", "All providers failed");
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiCancellation(boolean z, android.os.ResultReceiver resultReceiver) {
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelectorInvocationFailure() {
    }
}
