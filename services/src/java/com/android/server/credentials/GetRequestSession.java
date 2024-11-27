package com.android.server.credentials;

/* loaded from: classes.dex */
public class GetRequestSession extends com.android.server.credentials.RequestSession<android.credentials.GetCredentialRequest, android.credentials.IGetCredentialCallback, android.credentials.GetCredentialResponse> implements com.android.server.credentials.ProviderSession.ProviderInternalCallback<android.credentials.GetCredentialResponse> {
    private static final java.lang.String TAG = "GetRequestSession";

    @Override // com.android.server.credentials.RequestSession
    public /* bridge */ /* synthetic */ void addProviderSession(android.content.ComponentName componentName, com.android.server.credentials.ProviderSession providerSession) {
        super.addProviderSession(componentName, providerSession);
    }

    @Override // com.android.server.credentials.RequestSession, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public /* bridge */ /* synthetic */ void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver) {
        super.onUiSelection(userSelectionDialogResult, resultReceiver);
    }

    public GetRequestSession(android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, android.credentials.IGetCredentialCallback iGetCredentialCallback, android.credentials.GetCredentialRequest getCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, android.os.CancellationSignal cancellationSignal, long j) {
        super(context, sessionLifetime, obj, i, i2, getCredentialRequest, iGetCredentialCallback, getRequestInfoFromRequest(getCredentialRequest), callingAppInfo, set, cancellationSignal, j, true);
        this.mRequestSessionMetric.collectGetFlowInitialMetricInfo(getCredentialRequest);
    }

    private static java.lang.String getRequestInfoFromRequest(android.credentials.GetCredentialRequest getCredentialRequest) {
        java.util.Iterator<android.credentials.CredentialOption> it = getCredentialRequest.getCredentialOptions().iterator();
        while (it.hasNext()) {
            if (it.next().getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS") != null) {
                return "android.credentials.selection.TYPE_GET_VIA_REGISTRY";
            }
        }
        return "android.credentials.selection.TYPE_GET";
    }

    @Override // com.android.server.credentials.RequestSession
    @android.annotation.Nullable
    public com.android.server.credentials.ProviderSession initiateProviderSession(android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        com.android.server.credentials.ProviderGetSession createNewSession = com.android.server.credentials.ProviderGetSession.createNewSession(this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
        if (createNewSession != null) {
            android.util.Slog.i(TAG, "Provider session created and being added for: " + credentialProviderInfo.getComponentName());
            this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
        }
        return createNewSession;
    }

    @Override // com.android.server.credentials.RequestSession
    protected void launchUiWithProviderData(final java.util.ArrayList<android.credentials.selection.ProviderData> arrayList) {
        this.mRequestSessionMetric.collectUiCallStartTime(java.lang.System.nanoTime());
        this.mCredentialManagerUi.setStatus(com.android.server.credentials.CredentialManagerUi.UiStatus.USER_INTERACTION);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.credentials.GetRequestSession.this.lambda$launchUiWithProviderData$0(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$launchUiWithProviderData$0(java.util.ArrayList arrayList) throws java.lang.Exception {
        try {
            cancelExistingPendingIntent();
            this.mPendingIntent = this.mCredentialManagerUi.createPendingIntent(android.credentials.selection.RequestInfo.newGetRequestInfo(this.mRequestId, (android.credentials.GetCredentialRequest) this.mClientRequest, this.mClientAppInfo.getPackageName(), android.service.credentials.PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"), false), arrayList);
            ((android.credentials.IGetCredentialCallback) this.mClientCallback).onPendingIntent(this.mPendingIntent);
        } catch (android.os.RemoteException e) {
            this.mRequestSessionMetric.collectUiReturnedFinalPhase(false);
            this.mCredentialManagerUi.setStatus(com.android.server.credentials.CredentialManagerUi.UiStatus.TERMINATED);
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_UNKNOWN");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_UNKNOWN", "Unable to instantiate selector");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackSuccess(android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException {
        ((android.credentials.IGetCredentialCallback) this.mClientCallback).onResponse(getCredentialResponse);
    }

    @Override // com.android.server.credentials.RequestSession
    protected void invokeClientCallbackError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        ((android.credentials.IGetCredentialCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalResponseReceived(android.content.ComponentName componentName, @android.annotation.Nullable android.credentials.GetCredentialResponse getCredentialResponse) {
        android.util.Slog.i(TAG, "onFinalResponseReceived from: " + componentName.flattenToString());
        this.mRequestSessionMetric.collectUiResponseData(true, java.lang.System.nanoTime());
        this.mRequestSessionMetric.updateMetricsOnResponseReceived(this.mProviders, componentName, isPrimaryProviderViaProviderInfo(componentName));
        if (getCredentialResponse != null) {
            this.mRequestSessionMetric.collectChosenProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_SUCCESS.getMetricCode());
            respondToClientWithResponseAndFinish(getCredentialResponse);
        } else {
            this.mRequestSessionMetric.collectChosenProviderStatus(com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_FAILURE.getMetricCode());
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "Invalid response from provider");
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
            str = "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
            str2 = "User cancelled the selector";
        } else {
            str = "android.credentials.GetCredentialException.TYPE_INTERRUPTED";
            str2 = "The UI was interrupted - please try again.";
        }
        this.mRequestSessionMetric.collectFrameworkException(str);
        respondToClientWithErrorAndFinish(str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
        respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available.");
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(com.android.server.credentials.ProviderSession.Status status, android.content.ComponentName componentName, com.android.server.credentials.ProviderSession.CredentialsSource credentialsSource) {
        android.util.Slog.i(TAG, "Status changed for: " + componentName + ", with status: " + status + ", and source: " + credentialsSource);
        if (status == com.android.server.credentials.ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
            handleEmptyAuthenticationSelection(componentName);
            return;
        }
        if (!isAnyProviderPending()) {
            if (isUiInvocationNeeded()) {
                android.util.Slog.i(TAG, "Provider status changed - ui invocation is needed");
                getProviderDataAndInitiateUi();
            } else {
                this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
                respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available");
            }
        }
    }

    protected void handleEmptyAuthenticationSelection(final android.content.ComponentName componentName) {
        this.mProviders.keySet().forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.GetRequestSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.GetRequestSession.this.lambda$handleEmptyAuthenticationSelection$1(componentName, (java.lang.String) obj);
            }
        });
        getProviderDataAndInitiateUi();
        if (providerDataContainsEmptyAuthEntriesOnly()) {
            this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
            respondToClientWithErrorAndFinish("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", "No credentials available");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleEmptyAuthenticationSelection$1(android.content.ComponentName componentName, java.lang.String str) {
        com.android.server.credentials.ProviderGetSession providerGetSession = (com.android.server.credentials.ProviderGetSession) this.mProviders.get(str);
        if (!providerGetSession.mComponentName.equals(componentName)) {
            providerGetSession.updateAuthEntriesStatusFromAnotherSession();
        }
    }

    private boolean providerDataContainsEmptyAuthEntriesOnly() {
        java.util.Iterator<java.lang.String> it = this.mProviders.keySet().iterator();
        while (it.hasNext()) {
            if (!((com.android.server.credentials.ProviderGetSession) this.mProviders.get(it.next())).containsEmptyAuthEntriesOnly()) {
                return false;
            }
        }
        return true;
    }
}
