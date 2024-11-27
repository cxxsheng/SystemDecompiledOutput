package com.android.server.credentials;

/* loaded from: classes.dex */
public class GetCandidateRequestSession extends com.android.server.credentials.RequestSession<android.credentials.GetCredentialRequest, android.credentials.IGetCandidateCredentialsCallback, android.credentials.GetCandidateCredentialsResponse> implements com.android.server.credentials.ProviderSession.ProviderInternalCallback<android.credentials.GetCredentialResponse> {
    private static final java.lang.String REQUEST_ID_KEY = "autofill_request_id";
    private static final java.lang.String SESSION_ID_KEY = "autofill_session_id";
    private static final java.lang.String TAG = "GetCandidateRequestSession";
    private final int mAutofillRequestId;
    private final int mAutofillSessionId;
    private final android.os.IBinder mClientBinder;

    @Override // com.android.server.credentials.RequestSession
    public /* bridge */ /* synthetic */ void addProviderSession(android.content.ComponentName componentName, com.android.server.credentials.ProviderSession providerSession) {
        super.addProviderSession(componentName, providerSession);
    }

    @Override // com.android.server.credentials.RequestSession, com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public /* bridge */ /* synthetic */ void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver) {
        super.onUiSelection(userSelectionDialogResult, resultReceiver);
    }

    public GetCandidateRequestSession(android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, android.credentials.IGetCandidateCredentialsCallback iGetCandidateCredentialsCallback, android.credentials.GetCredentialRequest getCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, android.os.CancellationSignal cancellationSignal, android.os.IBinder iBinder) {
        super(context, sessionLifetime, obj, i, i2, getCredentialRequest, iGetCandidateCredentialsCallback, "android.credentials.selection.TYPE_GET", callingAppInfo, set, cancellationSignal, 0L, false);
        this.mClientBinder = iBinder;
        this.mAutofillSessionId = getCredentialRequest.getData().getInt(SESSION_ID_KEY, -1);
        this.mAutofillRequestId = getCredentialRequest.getData().getInt(REQUEST_ID_KEY, -1);
        if (this.mClientBinder != null) {
            setUpClientCallbackListener(this.mClientBinder);
        }
    }

    @Override // com.android.server.credentials.RequestSession
    @android.annotation.Nullable
    public com.android.server.credentials.ProviderSession initiateProviderSession(android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        com.android.server.credentials.ProviderGetSession createNewSession = com.android.server.credentials.ProviderGetSession.createNewSession(this.mContext, this.mUserId, credentialProviderInfo, this, remoteCredentialService);
        if (createNewSession != null) {
            android.util.Slog.d(TAG, "In startProviderSession - provider session created and being added for: " + credentialProviderInfo.getComponentName());
            this.mProviders.put(createNewSession.getComponentName().flattenToString(), createNewSession);
        }
        return createNewSession;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.credentials.RequestSession
    protected void launchUiWithProviderData(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            respondToClientWithErrorAndFinish("android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL", "No credentials found");
            return;
        }
        android.content.Intent createIntentForAutofill = this.mCredentialManagerUi.createIntentForAutofill(android.credentials.selection.RequestInfo.newGetRequestInfo(this.mRequestId, (android.credentials.GetCredentialRequest) this.mClientRequest, this.mClientAppInfo.getPackageName(), android.service.credentials.PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"), true));
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator<android.credentials.selection.ProviderData> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add((android.credentials.selection.ProviderData) it.next());
        }
        try {
            invokeClientCallbackSuccess(new android.credentials.GetCandidateCredentialsResponse(arrayList2, createIntentForAutofill));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Issue while responding to client with error : " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.RequestSession
    public void invokeClientCallbackSuccess(android.credentials.GetCandidateCredentialsResponse getCandidateCredentialsResponse) throws android.os.RemoteException {
        ((android.credentials.IGetCandidateCredentialsCallback) this.mClientCallback).onResponse(getCandidateCredentialsResponse);
    }

    @Override // com.android.server.credentials.RequestSession
    protected void invokeClientCallbackError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        ((android.credentials.IGetCandidateCredentialsCallback) this.mClientCallback).onError(str, str2);
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalErrorReceived(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) {
        android.util.Slog.d(TAG, "onFinalErrorReceived");
        respondToFinalReceiverWithFailureAndFinish(this.mFinalResponseReceiver, str, str2);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiCancellation(boolean z, @android.annotation.Nullable android.os.ResultReceiver resultReceiver) {
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
        respondToFinalReceiverWithFailureAndFinish(resultReceiver, str, str2);
    }

    private void respondToFinalReceiverWithFailureAndFinish(android.os.ResultReceiver resultReceiver, java.lang.String str, java.lang.String str2) {
        if (resultReceiver != null) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putStringArray("android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION", new java.lang.String[]{str, str2});
            resultReceiver.send(-1, bundle);
        } else {
            android.util.Slog.w(TAG, "onUiCancellation called but finalResponseReceiver not found");
        }
        finishSession(false, com.android.server.credentials.metrics.ApiStatus.FAILURE.getMetricCode());
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelectorInvocationFailure() {
        this.mRequestSessionMetric.collectFrameworkException("android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL");
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(com.android.server.credentials.ProviderSession.Status status, android.content.ComponentName componentName, com.android.server.credentials.ProviderSession.CredentialsSource credentialsSource) {
        android.util.Slog.d(TAG, "in onStatusChanged with status: " + status + ", and source: " + credentialsSource);
        if (!isAnyProviderPending()) {
            if (isUiInvocationNeeded()) {
                android.util.Slog.d(TAG, "in onProviderStatusChanged - isUiInvocationNeeded");
                getProviderDataAndInitiateUi();
            } else {
                respondToClientWithErrorAndFinish("android.credentials.GetCandidateCredentialsException.TYPE_NO_CREDENTIAL", "No credentials available");
            }
        }
    }

    @Override // com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onFinalResponseReceived(android.content.ComponentName componentName, android.credentials.GetCredentialResponse getCredentialResponse) {
        android.util.Slog.d(TAG, "onFinalResponseReceived");
        if (this.mFinalResponseReceiver != null) {
            android.util.Slog.d(TAG, "onFinalResponseReceived sending through final receiver");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("android.service.credentials.extra.GET_CREDENTIAL_RESPONSE", getCredentialResponse);
            this.mFinalResponseReceiver.send(0, bundle);
            finishSession(false, com.android.server.credentials.metrics.ApiStatus.SUCCESS.getMetricCode());
            return;
        }
        android.util.Slog.w(TAG, "onFinalResponseReceived result receiver not found for pinned entry");
        finishSession(false, com.android.server.credentials.metrics.ApiStatus.FAILURE.getMetricCode());
    }

    public int getAutofillSessionId() {
        return this.mAutofillSessionId;
    }

    public int getAutofillRequestId() {
        return this.mAutofillRequestId;
    }
}
