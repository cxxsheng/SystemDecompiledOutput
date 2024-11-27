package com.android.server.credentials;

/* loaded from: classes.dex */
public final class CreateRequestSession extends com.android.server.credentials.RequestSession<android.credentials.CreateCredentialRequest, android.credentials.ICreateCredentialCallback, android.credentials.CreateCredentialResponse> implements com.android.server.credentials.ProviderSession.ProviderInternalCallback<android.credentials.CreateCredentialResponse> {
    private static final java.lang.String TAG = "CreateRequestSession";
    private final java.util.Set<android.content.ComponentName> mPrimaryProviders;

    @Override // com.android.server.credentials.RequestSession
    public /* bridge */ /* synthetic */ void addProviderSession(android.content.ComponentName componentName, com.android.server.credentials.ProviderSession providerSession) {
        super.addProviderSession(componentName, providerSession);
    }

    @Override // com.android.server.credentials.RequestSession, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public /* bridge */ /* synthetic */ void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver) {
        super.onUiSelection(userSelectionDialogResult, resultReceiver);
    }

    CreateRequestSession(@android.annotation.NonNull android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.ICreateCredentialCallback iCreateCredentialCallback, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, java.util.Set<android.content.ComponentName> set2, android.os.CancellationSignal cancellationSignal, long j) {
        super(context, sessionLifetime, obj, i, i2, createCredentialRequest, iCreateCredentialCallback, "android.credentials.selection.TYPE_CREATE", callingAppInfo, set, cancellationSignal, j, true);
        this.mRequestSessionMetric.collectCreateFlowInitialMetricInfo(createCredentialRequest.getOrigin() != null, createCredentialRequest);
        this.mPrimaryProviders = set2;
    }

    @Override // com.android.server.credentials.RequestSession
    @android.annotation.Nullable
    public com.android.server.credentials.ProviderSession initiateProviderSession(android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        com.android.server.credentials.ProviderCreateSession createNewSession = com.android.server.credentials.ProviderCreateSession.createNewSession(this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
        if (createNewSession != null) {
            android.util.Slog.i(TAG, "Provider session created and being added for: " + credentialProviderInfo.getComponentName());
            this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
        }
        return createNewSession;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.credentials.RequestSession
    protected void launchUiWithProviderData(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList) {
        this.mRequestSessionMetric.collectUiCallStartTime(java.lang.System.nanoTime());
        this.mCredentialManagerUi.setStatus(com.android.server.credentials.CredentialManagerUi.UiStatus.USER_INTERACTION);
        cancelExistingPendingIntent();
        try {
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            java.util.Iterator<android.content.ComponentName> it = this.mPrimaryProviders.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().flattenToString());
            }
            this.mPendingIntent = this.mCredentialManagerUi.createPendingIntent(android.credentials.selection.RequestInfo.newCreateRequestInfo(this.mRequestId, (android.credentials.CreateCredentialRequest) this.mClientRequest, this.mClientAppInfo.getPackageName(), android.service.credentials.PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"), arrayList2, false), arrayList);
            ((android.credentials.ICreateCredentialCallback) this.mClientCallback).onPendingIntent(this.mPendingIntent);
        } catch (android.os.RemoteException e) {
            this.mRequestSessionMetric.collectUiReturnedFinalPhase(false);
            this.mCredentialManagerUi.setStatus(com.android.server.credentials.CredentialManagerUi.UiStatus.TERMINATED);
            respondToClientWithErrorAndFinish("android.credentials.CreateCredentialException.TYPE_UNKNOWN", "Unable to invoke selector");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackSuccess(android.credentials.CreateCredentialResponse createCredentialResponse) throws android.os.RemoteException {
        ((android.credentials.ICreateCredentialCallback) this.mClientCallback).onResponse(createCredentialResponse);
    }

    @Override // com.android.server.credentials.RequestSession
    protected void invokeClientCallbackError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        ((android.credentials.ICreateCredentialCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalResponseReceived(android.content.ComponentName componentName, @android.annotation.Nullable android.credentials.CreateCredentialResponse createCredentialResponse) {
        android.util.Slog.i(TAG, "Final credential received from: " + componentName.flattenToString());
        this.mRequestSessionMetric.collectUiResponseData(true, java.lang.System.nanoTime());
        this.mRequestSessionMetric.updateMetricsOnResponseReceived(this.mProviders, componentName, isPrimaryProviderViaProviderInfo(componentName));
        if (createCredentialResponse != null) {
            this.mRequestSessionMetric.collectChosenProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_SUCCESS.getMetricCode());
            respondToClientWithResponseAndFinish(createCredentialResponse);
        } else {
            this.mRequestSessionMetric.collectChosenProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_FAILURE.getMetricCode());
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
            respondToClientWithErrorAndFinish("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS", "Invalid response");
        }
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalErrorReceived(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiCancellation(boolean z, android.os.ResultReceiver resultReceiver) {
        java.lang.String str;
        java.lang.String str2;
        if (z) {
            str = "android.credentials.CreateCredentialException.TYPE_USER_CANCELED";
            str2 = "User cancelled the selector";
        } else {
            str = "android.credentials.CreateCredentialException.TYPE_INTERRUPTED";
            str2 = "The UI was interrupted - please try again.";
        }
        this.mRequestSessionMetric.collectFrameworkException(str);
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        respondToClientWithErrorAndFinish("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS", "No create options available.");
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(com.android.server.credentials.ProviderSession.Status status, android.content.ComponentName componentName, com.android.server.credentials.ProviderSession.CredentialsSource credentialsSource) {
        android.util.Slog.i(TAG, "Provider status changed: " + status + ", and source: " + credentialsSource);
        if (!isAnyProviderPending()) {
            if (isUiInvocationNeeded()) {
                android.util.Slog.i(TAG, "Provider status changed - ui invocation is needed");
                getProviderDataAndInitiateUi();
            } else {
                this.mRequestSessionMetric.collectFrameworkException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
                respondToClientWithErrorAndFinish("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS", "No create options available.");
            }
        }
    }
}
