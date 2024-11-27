package com.android.server.credentials;

/* loaded from: classes.dex */
public class PrepareGetRequestSession extends com.android.server.credentials.GetRequestSession {
    private static final java.lang.String TAG = "PrepareGetRequestSession";
    private final android.credentials.IPrepareGetCredentialCallback mPrepareGetCredentialCallback;

    public PrepareGetRequestSession(android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, android.credentials.IGetCredentialCallback iGetCredentialCallback, android.credentials.GetCredentialRequest getCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, android.os.CancellationSignal cancellationSignal, long j, android.credentials.IPrepareGetCredentialCallback iPrepareGetCredentialCallback) {
        super(context, sessionLifetime, obj, i, i2, iGetCredentialCallback, getCredentialRequest, callingAppInfo, set, cancellationSignal, j);
        ((java.util.Set) getCredentialRequest.getCredentialOptions().stream().map(new com.android.server.credentials.CredentialManagerService$CredentialManagerServiceStub$$ExternalSyntheticLambda3()).collect(java.util.stream.Collectors.toSet())).size();
        this.mRequestSessionMetric.collectGetFlowInitialMetricInfo(getCredentialRequest);
        this.mPrepareGetCredentialCallback = iPrepareGetCredentialCallback;
    }

    @Override // com.android.server.credentials.GetRequestSession, com.android.server.credentials.ProviderSession.ProviderInternalCallback
    public void onProviderStatusChanged(com.android.server.credentials.ProviderSession.Status status, android.content.ComponentName componentName, com.android.server.credentials.ProviderSession.CredentialsSource credentialsSource) {
        android.util.Slog.i(TAG, "Provider Status changed with status: " + status + ", and source: " + credentialsSource);
        switch (com.android.server.credentials.PrepareGetRequestSession.AnonymousClass1.$SwitchMap$com$android$server$credentials$ProviderSession$CredentialsSource[credentialsSource.ordinal()]) {
            case 1:
                if (!isAnyProviderPending()) {
                    boolean hasPermission = android.service.credentials.PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS");
                    if (isUiInvocationNeeded()) {
                        java.util.ArrayList<android.credentials.selection.ProviderData> providerDataForUi = getProviderDataForUi();
                        if (!providerDataForUi.isEmpty()) {
                            constructPendingResponseAndInvokeCallback(hasPermission, getCredentialResultTypes(hasPermission), hasAuthenticationResults(providerDataForUi, hasPermission), hasRemoteResults(providerDataForUi, hasPermission), getUiIntent());
                            break;
                        }
                    }
                    constructEmptyPendingResponseAndInvokeCallback(hasPermission);
                    break;
                }
                break;
            case 2:
                if (status == com.android.server.credentials.ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY) {
                    super.handleEmptyAuthenticationSelection(componentName);
                    break;
                } else if (status == com.android.server.credentials.ProviderSession.Status.CREDENTIALS_RECEIVED) {
                    getProviderDataAndInitiateUi();
                    break;
                }
                break;
            default:
                android.util.Slog.w(TAG, "Unexpected source");
                break;
        }
    }

    /* renamed from: com.android.server.credentials.PrepareGetRequestSession$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$credentials$ProviderSession$CredentialsSource = new int[com.android.server.credentials.ProviderSession.CredentialsSource.values().length];

        static {
            try {
                $SwitchMap$com$android$server$credentials$ProviderSession$CredentialsSource[com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$credentials$ProviderSession$CredentialsSource[com.android.server.credentials.ProviderSession.CredentialsSource.AUTH_ENTRY.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    private void constructPendingResponseAndInvokeCallback(boolean z, java.util.Set<java.lang.String> set, boolean z2, boolean z3, android.app.PendingIntent pendingIntent) {
        try {
            this.mPrepareGetCredentialCallback.onResponse(new android.credentials.PrepareGetCredentialResponseInternal(z, set, z2, z3, pendingIntent));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "EXCEPTION while mPendingCallback.onResponse", e);
        }
    }

    private void constructEmptyPendingResponseAndInvokeCallback(boolean z) {
        try {
            this.mPrepareGetCredentialCallback.onResponse(new android.credentials.PrepareGetCredentialResponseInternal(z, (java.util.Set) null, false, false, (android.app.PendingIntent) null));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "EXCEPTION while mPendingCallback.onResponse", e);
        }
    }

    private boolean hasRemoteResults(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList, boolean z) {
        if (!z) {
            return false;
        }
        return arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.credentials.selection.GetCredentialProviderData lambda$hasRemoteResults$0;
                lambda$hasRemoteResults$0 = com.android.server.credentials.PrepareGetRequestSession.lambda$hasRemoteResults$0((android.credentials.selection.ProviderData) obj);
                return lambda$hasRemoteResults$0;
            }
        }).anyMatch(new java.util.function.Predicate() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$hasRemoteResults$1;
                lambda$hasRemoteResults$1 = com.android.server.credentials.PrepareGetRequestSession.lambda$hasRemoteResults$1((android.credentials.selection.GetCredentialProviderData) obj);
                return lambda$hasRemoteResults$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.credentials.selection.GetCredentialProviderData lambda$hasRemoteResults$0(android.credentials.selection.ProviderData providerData) {
        return (android.credentials.selection.GetCredentialProviderData) providerData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasRemoteResults$1(android.credentials.selection.GetCredentialProviderData getCredentialProviderData) {
        return getCredentialProviderData.getRemoteEntry() != null;
    }

    private boolean hasAuthenticationResults(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList, boolean z) {
        if (!z) {
            return false;
        }
        return arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.credentials.selection.GetCredentialProviderData lambda$hasAuthenticationResults$2;
                lambda$hasAuthenticationResults$2 = com.android.server.credentials.PrepareGetRequestSession.lambda$hasAuthenticationResults$2((android.credentials.selection.ProviderData) obj);
                return lambda$hasAuthenticationResults$2;
            }
        }).anyMatch(new java.util.function.Predicate() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$hasAuthenticationResults$3;
                lambda$hasAuthenticationResults$3 = com.android.server.credentials.PrepareGetRequestSession.lambda$hasAuthenticationResults$3((android.credentials.selection.GetCredentialProviderData) obj);
                return lambda$hasAuthenticationResults$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.credentials.selection.GetCredentialProviderData lambda$hasAuthenticationResults$2(android.credentials.selection.ProviderData providerData) {
        return (android.credentials.selection.GetCredentialProviderData) providerData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasAuthenticationResults$3(android.credentials.selection.GetCredentialProviderData getCredentialProviderData) {
        return !getCredentialProviderData.getAuthenticationEntries().isEmpty();
    }

    @android.annotation.Nullable
    private java.util.Set<java.lang.String> getCredentialResultTypes(boolean z) {
        if (!z) {
            return null;
        }
        return (java.util.Set) this.mProviders.values().stream().map(new java.util.function.Function() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.credentials.ProviderGetSession lambda$getCredentialResultTypes$4;
                lambda$getCredentialResultTypes$4 = com.android.server.credentials.PrepareGetRequestSession.lambda$getCredentialResultTypes$4((com.android.server.credentials.ProviderSession) obj);
                return lambda$getCredentialResultTypes$4;
            }
        }).flatMap(new java.util.function.Function() { // from class: com.android.server.credentials.PrepareGetRequestSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.stream.Stream lambda$getCredentialResultTypes$5;
                lambda$getCredentialResultTypes$5 = com.android.server.credentials.PrepareGetRequestSession.lambda$getCredentialResultTypes$5((com.android.server.credentials.ProviderGetSession) obj);
                return lambda$getCredentialResultTypes$5;
            }
        }).collect(java.util.stream.Collectors.toSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.credentials.ProviderGetSession lambda$getCredentialResultTypes$4(com.android.server.credentials.ProviderSession providerSession) {
        return (com.android.server.credentials.ProviderGetSession) providerSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.stream.Stream lambda$getCredentialResultTypes$5(com.android.server.credentials.ProviderGetSession providerGetSession) {
        return providerGetSession.getCredentialEntryTypes().stream();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private android.app.PendingIntent getUiIntent() {
        java.util.ArrayList<android.credentials.selection.ProviderData> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<com.android.server.credentials.ProviderSession> it = this.mProviders.values().iterator();
        while (it.hasNext()) {
            android.credentials.selection.ProviderData mo3078prepareUiData = it.next().mo3078prepareUiData();
            if (mo3078prepareUiData != null) {
                arrayList.add(mo3078prepareUiData);
            }
        }
        if (!arrayList.isEmpty()) {
            return this.mCredentialManagerUi.createPendingIntent(android.credentials.selection.RequestInfo.newGetRequestInfo(this.mRequestId, (android.credentials.GetCredentialRequest) this.mClientRequest, this.mClientAppInfo.getPackageName(), android.service.credentials.PermissionUtils.hasPermission(this.mContext, this.mClientAppInfo.getPackageName(), "android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS"), false), arrayList);
        }
        return null;
    }
}
