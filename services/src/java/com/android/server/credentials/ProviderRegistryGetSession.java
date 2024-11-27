package com.android.server.credentials;

/* loaded from: classes.dex */
public class ProviderRegistryGetSession extends com.android.server.credentials.ProviderSession<android.credentials.CredentialOption, java.util.Set<com.android.server.credentials.CredentialDescriptionRegistry.FilterResult>> {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String CREDENTIAL_ENTRY_KEY = "credential_key";
    private static final java.lang.String TAG = "ProviderRegistryGetSession";

    @android.annotation.NonNull
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;

    @android.annotation.NonNull
    private final com.android.server.credentials.CredentialDescriptionRegistry mCredentialDescriptionRegistry;

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.service.credentials.CredentialEntry> mCredentialEntries;

    @android.annotation.NonNull
    private final java.lang.String mCredentialProviderPackageName;

    @android.annotation.NonNull
    private final java.util.Set<java.lang.String> mElementKeys;

    @android.annotation.NonNull
    private final java.util.Map<java.lang.String, android.service.credentials.CredentialEntry> mUiCredentialEntries;

    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderRegistryGetSession createNewSession(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull com.android.server.credentials.GetRequestSession getRequestSession, @android.annotation.NonNull android.service.credentials.CallingAppInfo callingAppInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.credentials.CredentialOption credentialOption) {
        return new com.android.server.credentials.ProviderRegistryGetSession(context, i, getRequestSession, callingAppInfo, str, credentialOption);
    }

    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderRegistryGetSession createNewSession(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull com.android.server.credentials.PrepareGetRequestSession prepareGetRequestSession, @android.annotation.NonNull android.service.credentials.CallingAppInfo callingAppInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.credentials.CredentialOption credentialOption) {
        return new com.android.server.credentials.ProviderRegistryGetSession(context, i, prepareGetRequestSession, callingAppInfo, str, credentialOption);
    }

    protected ProviderRegistryGetSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull int i, @android.annotation.NonNull com.android.server.credentials.GetRequestSession getRequestSession, @android.annotation.NonNull android.service.credentials.CallingAppInfo callingAppInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.credentials.CredentialOption credentialOption) {
        super(context, credentialOption, getRequestSession, new android.content.ComponentName(str, java.util.UUID.randomUUID().toString()), i, null);
        this.mUiCredentialEntries = new java.util.HashMap();
        this.mCredentialDescriptionRegistry = com.android.server.credentials.CredentialDescriptionRegistry.forUser(i);
        this.mCallingAppInfo = callingAppInfo;
        this.mCredentialProviderPackageName = str;
        this.mElementKeys = new java.util.HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
        this.mStatus = com.android.server.credentials.ProviderSession.Status.PENDING;
    }

    protected ProviderRegistryGetSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull int i, @android.annotation.NonNull com.android.server.credentials.PrepareGetRequestSession prepareGetRequestSession, @android.annotation.NonNull android.service.credentials.CallingAppInfo callingAppInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.credentials.CredentialOption credentialOption) {
        super(context, credentialOption, prepareGetRequestSession, new android.content.ComponentName(str, java.util.UUID.randomUUID().toString()), i, null);
        this.mUiCredentialEntries = new java.util.HashMap();
        this.mCredentialDescriptionRegistry = com.android.server.credentials.CredentialDescriptionRegistry.forUser(i);
        this.mCallingAppInfo = callingAppInfo;
        this.mCredentialProviderPackageName = str;
        this.mElementKeys = new java.util.HashSet(credentialOption.getCredentialRetrievalData().getStringArrayList("android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS"));
        this.mStatus = com.android.server.credentials.ProviderSession.Status.PENDING;
    }

    private java.util.List<android.credentials.selection.Entry> prepareUiCredentialEntries(@android.annotation.NonNull java.util.List<android.service.credentials.CredentialEntry> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.service.credentials.CredentialEntry credentialEntry : list) {
            java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
            this.mUiCredentialEntries.put(generateUniqueId, credentialEntry);
            arrayList.add(new android.credentials.selection.Entry("credential_key", generateUniqueId, credentialEntry.getSlice(), setUpFillInIntent()));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private android.content.Intent setUpFillInIntent() {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.service.credentials.extra.GET_CREDENTIAL_REQUEST", new android.service.credentials.GetCredentialRequest(this.mCallingAppInfo, java.util.List.of((android.credentials.CredentialOption) this.mProviderRequest)));
        return intent;
    }

    @Override // com.android.server.credentials.ProviderSession
    /* renamed from: prepareUiData */
    protected android.credentials.selection.ProviderData mo3078prepareUiData() {
        if (!com.android.server.credentials.ProviderSession.isUiInvokingStatus(getStatus())) {
            android.util.Slog.i(TAG, "No date for UI coming from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == 0) {
            android.util.Slog.w(TAG, "response is null when preparing ui data. This is strange.");
            return null;
        }
        return new android.credentials.selection.GetCredentialProviderData.Builder(this.mComponentName.flattenToString()).setActionChips(java.util.Collections.EMPTY_LIST).setAuthenticationEntries(java.util.Collections.EMPTY_LIST).setCredentialEntries(prepareUiCredentialEntries((java.util.List) ((java.util.Set) this.mProviderResponse).stream().flatMap(new java.util.function.Function() { // from class: com.android.server.credentials.ProviderRegistryGetSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.stream.Stream lambda$prepareUiData$0;
                lambda$prepareUiData$0 = com.android.server.credentials.ProviderRegistryGetSession.lambda$prepareUiData$0((com.android.server.credentials.CredentialDescriptionRegistry.FilterResult) obj);
                return lambda$prepareUiData$0;
            }
        }).collect(java.util.stream.Collectors.toList()))).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.stream.Stream lambda$prepareUiData$0(com.android.server.credentials.CredentialDescriptionRegistry.FilterResult filterResult) {
        return filterResult.mCredentialEntries.stream();
    }

    @Override // com.android.server.credentials.ProviderSession
    protected void onUiEntrySelected(java.lang.String str, java.lang.String str2, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        char c;
        switch (str.hashCode()) {
            case 1208398455:
                if (str.equals("credential_key")) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                android.service.credentials.CredentialEntry credentialEntry = this.mUiCredentialEntries.get(str2);
                if (credentialEntry == null) {
                    android.util.Slog.i(TAG, "Unexpected credential entry key");
                    break;
                } else {
                    onCredentialEntrySelected(credentialEntry, providerPendingIntentResponse);
                    break;
                }
            default:
                android.util.Slog.i(TAG, "Unsupported entry type selected");
                break;
        }
    }

    private void onCredentialEntrySelected(android.service.credentials.CredentialEntry credentialEntry, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse != null) {
            android.credentials.GetCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
            if (maybeGetPendingIntentException != null) {
                invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
                return;
            }
            android.credentials.GetCredentialResponse extractGetCredentialResponse = com.android.server.credentials.PendingIntentResultHandler.extractGetCredentialResponse(providerPendingIntentResponse.getResultData());
            if (extractGetCredentialResponse != null) {
                if (this.mCallbacks != null) {
                    ((com.android.server.credentials.GetRequestSession) this.mCallbacks).onFinalResponseReceived(this.mComponentName, extractGetCredentialResponse);
                    return;
                }
                return;
            }
        }
        android.util.Slog.w(TAG, "CredentialEntry does not have a credential or a pending intent result");
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(@android.annotation.Nullable java.util.Set<com.android.server.credentials.CredentialDescriptionRegistry.FilterResult> set) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, @android.annotation.Nullable java.lang.Exception exc) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderServiceDied(com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderCancellable(android.os.ICancellationSignal iCancellationSignal) {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [R, java.util.Set] */
    @Override // com.android.server.credentials.ProviderSession
    protected void invokeSession() {
        startCandidateMetrics();
        this.mProviderResponse = this.mCredentialDescriptionRegistry.getFilteredResultForProvider(this.mCredentialProviderPackageName, this.mElementKeys);
        this.mCredentialEntries = (java.util.List) ((java.util.Set) this.mProviderResponse).stream().flatMap(new java.util.function.Function() { // from class: com.android.server.credentials.ProviderRegistryGetSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.stream.Stream lambda$invokeSession$1;
                lambda$invokeSession$1 = com.android.server.credentials.ProviderRegistryGetSession.lambda$invokeSession$1((com.android.server.credentials.CredentialDescriptionRegistry.FilterResult) obj);
                return lambda$invokeSession$1;
            }
        }).collect(java.util.stream.Collectors.toList());
        updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.CREDENTIALS_RECEIVED, com.android.server.credentials.ProviderSession.CredentialsSource.REGISTRY);
        this.mProviderSessionMetric.collectCandidateEntryMetrics(this.mCredentialEntries);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.stream.Stream lambda$invokeSession$1(com.android.server.credentials.CredentialDescriptionRegistry.FilterResult filterResult) {
        return filterResult.mCredentialEntries.stream();
    }

    @android.annotation.Nullable
    protected android.credentials.GetCredentialException maybeGetPendingIntentException(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            return null;
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isValidResponse(providerPendingIntentResponse)) {
            android.credentials.GetCredentialException extractGetCredentialException = com.android.server.credentials.PendingIntentResultHandler.extractGetCredentialException(providerPendingIntentResponse.getResultData());
            if (extractGetCredentialException == null) {
                return null;
            }
            android.util.Slog.i(TAG, "Pending intent contains provider exception");
            return extractGetCredentialException;
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isCancelledResponse(providerPendingIntentResponse)) {
            return new android.credentials.GetCredentialException("android.credentials.GetCredentialException.TYPE_USER_CANCELED");
        }
        return new android.credentials.GetCredentialException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
    }
}
