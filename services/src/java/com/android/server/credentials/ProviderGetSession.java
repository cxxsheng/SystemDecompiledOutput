package com.android.server.credentials;

/* loaded from: classes.dex */
public final class ProviderGetSession extends com.android.server.credentials.ProviderSession<android.service.credentials.BeginGetCredentialRequest, android.service.credentials.BeginGetCredentialResponse> implements com.android.server.credentials.RemoteCredentialService.ProviderCallbacks<android.service.credentials.BeginGetCredentialResponse> {
    public static final java.lang.String ACTION_ENTRY_KEY = "action_key";
    public static final java.lang.String AUTHENTICATION_ACTION_ENTRY_KEY = "authentication_action_key";
    public static final java.lang.String CREDENTIAL_ENTRY_KEY = "credential_key";
    public static final java.lang.String REMOTE_ENTRY_KEY = "remote_entry_key";
    private static final java.lang.String TAG = "ProviderGetSession";

    @android.annotation.NonNull
    private final java.util.Map<java.lang.String, android.credentials.CredentialOption> mBeginGetOptionToCredentialOptionMap;
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;
    private final android.credentials.GetCredentialRequest mCompleteRequest;
    private android.credentials.GetCredentialException mProviderException;
    private final com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler mProviderResponseDataHandler;

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderGetSession createNewSession(android.content.Context context, int i, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.GetRequestSession getRequestSession, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        android.credentials.GetCredentialRequest filterOptions = filterOptions(credentialProviderInfo.getCapabilities(), (android.credentials.GetCredentialRequest) getRequestSession.mClientRequest, credentialProviderInfo, getRequestSession.mHybridService);
        if (filterOptions != null) {
            java.util.HashMap hashMap = new java.util.HashMap();
            return new com.android.server.credentials.ProviderGetSession(context, credentialProviderInfo, getRequestSession, i, remoteCredentialService, constructQueryPhaseRequest(filterOptions, getRequestSession.mClientAppInfo, ((android.credentials.GetCredentialRequest) getRequestSession.mClientRequest).alwaysSendAppInfoToProvider(), hashMap), filterOptions, getRequestSession.mClientAppInfo, hashMap, getRequestSession.mHybridService);
        }
        android.util.Slog.i(TAG, "Unable to create provider session for: " + credentialProviderInfo.getComponentName());
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderGetSession createNewSession(android.content.Context context, int i, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.GetCandidateRequestSession getCandidateRequestSession, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        android.credentials.GetCredentialRequest filterOptions = filterOptions(credentialProviderInfo.getCapabilities(), (android.credentials.GetCredentialRequest) getCandidateRequestSession.mClientRequest, credentialProviderInfo, getCandidateRequestSession.mHybridService);
        if (filterOptions != null) {
            java.util.HashMap hashMap = new java.util.HashMap();
            return new com.android.server.credentials.ProviderGetSession(context, credentialProviderInfo, getCandidateRequestSession, i, remoteCredentialService, constructQueryPhaseRequest(filterOptions, getCandidateRequestSession.mClientAppInfo, ((android.credentials.GetCredentialRequest) getCandidateRequestSession.mClientRequest).alwaysSendAppInfoToProvider(), hashMap), filterOptions, getCandidateRequestSession.mClientAppInfo, hashMap, getCandidateRequestSession.mHybridService);
        }
        android.util.Slog.i(TAG, "Unable to create provider session for: " + credentialProviderInfo.getComponentName());
        return null;
    }

    private static android.service.credentials.BeginGetCredentialRequest constructQueryPhaseRequest(android.credentials.GetCredentialRequest getCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, boolean z, final java.util.Map<java.lang.String, android.credentials.CredentialOption> map) {
        final android.service.credentials.BeginGetCredentialRequest.Builder builder = new android.service.credentials.BeginGetCredentialRequest.Builder();
        getCredentialRequest.getCredentialOptions().forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.ProviderGetSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.credentials.ProviderGetSession.lambda$constructQueryPhaseRequest$0(builder, map, (android.credentials.CredentialOption) obj);
            }
        });
        if (z) {
            builder.setCallingAppInfo(callingAppInfo);
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$constructQueryPhaseRequest$0(android.service.credentials.BeginGetCredentialRequest.Builder builder, java.util.Map map, android.credentials.CredentialOption credentialOption) {
        java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
        builder.addBeginGetCredentialOption(new android.service.credentials.BeginGetCredentialOption(generateUniqueId, credentialOption.getType(), credentialOption.getCandidateQueryData()));
        map.put(generateUniqueId, credentialOption);
    }

    @android.annotation.Nullable
    private static android.credentials.GetCredentialRequest filterOptions(java.util.List<java.lang.String> list, android.credentials.GetCredentialRequest getCredentialRequest, android.credentials.CredentialProviderInfo credentialProviderInfo, java.lang.String str) {
        android.util.Slog.i(TAG, "Filtering request options for: " + credentialProviderInfo.getComponentName());
        if (android.credentials.flags.Flags.hybridFilterFixEnabled()) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
            if (unflattenFromString != null && unflattenFromString.equals(credentialProviderInfo.getComponentName())) {
                android.util.Slog.i(TAG, "Skipping filtering of options for hybrid service");
                return getCredentialRequest;
            }
            android.util.Slog.w(TAG, "Could not parse hybrid service while filtering options");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.credentials.CredentialOption credentialOption : getCredentialRequest.getCredentialOptions()) {
            if (list.contains(credentialOption.getType()) && isProviderAllowed(credentialOption, credentialProviderInfo) && checkSystemProviderRequirement(credentialOption, credentialProviderInfo.isSystemProvider())) {
                android.util.Slog.i(TAG, "Option of type: " + credentialOption.getType() + " meets all filteringconditions");
                arrayList.add(credentialOption);
            }
        }
        if (!arrayList.isEmpty()) {
            return new android.credentials.GetCredentialRequest.Builder(getCredentialRequest.getData()).setCredentialOptions(arrayList).build();
        }
        android.util.Slog.i(TAG, "No options filtered");
        return null;
    }

    private static boolean isProviderAllowed(android.credentials.CredentialOption credentialOption, android.credentials.CredentialProviderInfo credentialProviderInfo) {
        if (credentialProviderInfo.isSystemProvider() || credentialOption.getAllowedProviders().isEmpty() || credentialOption.getAllowedProviders().contains(credentialProviderInfo.getComponentName())) {
            return true;
        }
        android.util.Slog.i(TAG, "Provider allow list specified but does not contain this provider");
        return false;
    }

    private static boolean checkSystemProviderRequirement(android.credentials.CredentialOption credentialOption, boolean z) {
        if (credentialOption.isSystemProviderRequired() && !z) {
            android.util.Slog.i(TAG, "System provider required, but this service is not a system provider");
            return false;
        }
        return true;
    }

    public ProviderGetSession(android.content.Context context, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.ProviderSession.ProviderInternalCallback providerInternalCallback, int i, com.android.server.credentials.RemoteCredentialService remoteCredentialService, android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, android.credentials.GetCredentialRequest getCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Map<java.lang.String, android.credentials.CredentialOption> map, java.lang.String str) {
        super(context, beginGetCredentialRequest, providerInternalCallback, credentialProviderInfo.getComponentName(), i, remoteCredentialService);
        this.mCompleteRequest = getCredentialRequest;
        this.mCallingAppInfo = callingAppInfo;
        setStatus(com.android.server.credentials.ProviderSession.Status.PENDING);
        this.mBeginGetOptionToCredentialOptionMap = new java.util.HashMap(map);
        this.mProviderResponseDataHandler = new com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler(android.content.ComponentName.unflattenFromString(str));
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(@android.annotation.Nullable android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) {
        android.util.Slog.i(TAG, "Remote provider responded with a valid response: " + this.mComponentName);
        onSetInitialRemoteResponse(beginGetCredentialResponse);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, java.lang.Exception exc) {
        if (exc instanceof android.credentials.GetCredentialException) {
            this.mProviderException = (android.credentials.GetCredentialException) exc;
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.credentials.ProviderSession
    protected void onUiEntrySelected(java.lang.String str, java.lang.String str2, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        char c;
        android.util.Slog.i(TAG, "onUiEntrySelected with entryType: " + str + ", and entryKey: " + str2);
        switch (str.hashCode()) {
            case 1110515801:
                if (str.equals(REMOTE_ENTRY_KEY)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1182857469:
                if (str.equals(AUTHENTICATION_ACTION_ENTRY_KEY)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1208398455:
                if (str.equals(CREDENTIAL_ENTRY_KEY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1852195030:
                if (str.equals(ACTION_ENTRY_KEY)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (this.mProviderResponseDataHandler.getCredentialEntry(str2) == null) {
                    android.util.Slog.i(TAG, "Unexpected credential entry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                } else {
                    onCredentialEntrySelected(providerPendingIntentResponse);
                    break;
                }
            case 1:
                if (this.mProviderResponseDataHandler.getActionEntry(str2) == null) {
                    android.util.Slog.i(TAG, "Unexpected action entry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                } else {
                    onActionEntrySelected(providerPendingIntentResponse);
                    break;
                }
            case 2:
                android.service.credentials.Action authenticationAction = this.mProviderResponseDataHandler.getAuthenticationAction(str2);
                this.mProviderSessionMetric.createAuthenticationBrowsingMetric();
                if (authenticationAction == null) {
                    android.util.Slog.i(TAG, "Unexpected authenticationEntry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                } else if (onAuthenticationEntrySelected(providerPendingIntentResponse)) {
                    android.util.Slog.i(TAG, "Additional content received - removing authentication entry");
                    this.mProviderResponseDataHandler.removeAuthenticationAction(str2);
                    if (!this.mProviderResponseDataHandler.isEmptyResponse()) {
                        updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.CREDENTIALS_RECEIVED, com.android.server.credentials.ProviderSession.CredentialsSource.AUTH_ENTRY);
                        break;
                    }
                } else {
                    android.util.Slog.i(TAG, "Additional content not received from authentication entry");
                    this.mProviderResponseDataHandler.updateAuthEntryWithNoCredentialsReceived(str2);
                    updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.NO_CREDENTIALS_FROM_AUTH_ENTRY, com.android.server.credentials.ProviderSession.CredentialsSource.AUTH_ENTRY);
                    break;
                }
                break;
            case 3:
                if (this.mProviderResponseDataHandler.getRemoteEntry(str2) != null) {
                    onRemoteEntrySelected(providerPendingIntentResponse);
                    break;
                } else {
                    android.util.Slog.i(TAG, "Unexpected remote entry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                }
            default:
                android.util.Slog.i(TAG, "Unsupported entry type selected");
                invokeCallbackOnInternalInvalidState();
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.credentials.ProviderSession
    protected void invokeSession() {
        if (this.mRemoteCredentialService != null) {
            startCandidateMetrics();
            this.mRemoteCredentialService.setCallback(this);
            this.mRemoteCredentialService.onBeginGetCredential((android.service.credentials.BeginGetCredentialRequest) this.mProviderRequest);
        }
    }

    @android.annotation.NonNull
    protected java.util.Set<java.lang.String> getCredentialEntryTypes() {
        return this.mProviderResponseDataHandler.getCredentialEntryTypes();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.ProviderSession
    @android.annotation.Nullable
    /* renamed from: prepareUiData, reason: merged with bridge method [inline-methods] */
    public android.credentials.selection.GetCredentialProviderData mo3078prepareUiData() throws java.lang.IllegalArgumentException {
        if (!com.android.server.credentials.ProviderSession.isUiInvokingStatus(getStatus())) {
            android.util.Slog.i(TAG, "No data for UI from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == 0 || this.mProviderResponseDataHandler.isEmptyResponse()) {
            return null;
        }
        return this.mProviderResponseDataHandler.toGetCredentialProviderData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent setUpFillInIntentWithFinalRequest(@android.annotation.NonNull java.lang.String str) {
        android.content.Intent intent = new android.content.Intent();
        android.credentials.CredentialOption credentialOption = this.mBeginGetOptionToCredentialOptionMap.get(str);
        if (credentialOption == null) {
            android.util.Slog.w(TAG, "Id from Credential Entry does not resolve to a valid option");
            return intent;
        }
        return intent.putExtra("android.service.credentials.extra.GET_CREDENTIAL_REQUEST", new android.service.credentials.GetCredentialRequest(this.mCallingAppInfo, java.util.List.of(credentialOption)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent setUpFillInIntentWithQueryRequest() {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST", (android.os.Parcelable) this.mProviderRequest);
        return intent;
    }

    private void onRemoteEntrySelected(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        onCredentialEntrySelected(providerPendingIntentResponse);
    }

    private void onCredentialEntrySelected(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            invokeCallbackOnInternalInvalidState();
            return;
        }
        android.credentials.GetCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
        if (maybeGetPendingIntentException != null) {
            invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
            return;
        }
        android.credentials.GetCredentialResponse extractGetCredentialResponse = com.android.server.credentials.PendingIntentResultHandler.extractGetCredentialResponse(providerPendingIntentResponse.getResultData());
        if (extractGetCredentialResponse != null) {
            this.mCallbacks.onFinalResponseReceived(this.mComponentName, extractGetCredentialResponse);
        } else {
            android.util.Slog.i(TAG, "Pending intent response contains no credential, or error for a credential entry");
            invokeCallbackOnInternalInvalidState();
        }
    }

    @android.annotation.Nullable
    private android.credentials.GetCredentialException maybeGetPendingIntentException(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            return null;
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isValidResponse(providerPendingIntentResponse)) {
            android.credentials.GetCredentialException extractGetCredentialException = com.android.server.credentials.PendingIntentResultHandler.extractGetCredentialException(providerPendingIntentResponse.getResultData());
            if (extractGetCredentialException == null) {
                return null;
            }
            return extractGetCredentialException;
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isCancelledResponse(providerPendingIntentResponse)) {
            return new android.credentials.GetCredentialException("android.credentials.GetCredentialException.TYPE_USER_CANCELED");
        }
        return new android.credentials.GetCredentialException("android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL");
    }

    private boolean onAuthenticationEntrySelected(@android.annotation.Nullable android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            return false;
        }
        android.credentials.GetCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
        if (maybeGetPendingIntentException != null) {
            this.mProviderSessionMetric.collectAuthenticationExceptionStatus(true);
            invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
            return true;
        }
        android.service.credentials.BeginGetCredentialResponse extractResponseContent = com.android.server.credentials.PendingIntentResultHandler.extractResponseContent(providerPendingIntentResponse.getResultData());
        this.mProviderSessionMetric.collectCandidateEntryMetrics(extractResponseContent, true, null);
        if (extractResponseContent == null || this.mProviderResponseDataHandler.isEmptyResponse(extractResponseContent)) {
            return false;
        }
        addToInitialRemoteResponse(extractResponseContent, false);
        return true;
    }

    private void addToInitialRemoteResponse(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse, boolean z) {
        if (beginGetCredentialResponse == null) {
            return;
        }
        this.mProviderResponseDataHandler.addResponseContent(beginGetCredentialResponse.getCredentialEntries(), beginGetCredentialResponse.getActions(), beginGetCredentialResponse.getAuthenticationActions(), beginGetCredentialResponse.getRemoteCredentialEntry(), z);
    }

    private void onActionEntrySelected(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        android.util.Slog.i(TAG, "onActionEntrySelected");
        onCredentialEntrySelected(providerPendingIntentResponse);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void onSetInitialRemoteResponse(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) {
        this.mProviderResponse = beginGetCredentialResponse;
        addToInitialRemoteResponse(beginGetCredentialResponse, true);
        if (this.mProviderResponseDataHandler.isEmptyResponse(beginGetCredentialResponse)) {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginGetCredentialResponse, false, null);
            updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.EMPTY_RESPONSE, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginGetCredentialResponse, false, null);
            updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.CREDENTIALS_RECEIVED, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        }
    }

    private void invokeCallbackOnInternalInvalidState() {
        this.mCallbacks.onFinalErrorReceived(this.mComponentName, "android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL", null);
    }

    public void updateAuthEntriesStatusFromAnotherSession() {
        this.mProviderResponseDataHandler.updateAuthEntryWithNoCredentialsReceived(null);
    }

    public boolean containsEmptyAuthEntriesOnly() {
        return this.mProviderResponseDataHandler.mUiCredentialEntries.isEmpty() && this.mProviderResponseDataHandler.mUiRemoteEntry == null && this.mProviderResponseDataHandler.mUiAuthenticationEntries.values().stream().allMatch(new java.util.function.Predicate() { // from class: com.android.server.credentials.ProviderGetSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$containsEmptyAuthEntriesOnly$1;
                lambda$containsEmptyAuthEntriesOnly$1 = com.android.server.credentials.ProviderGetSession.lambda$containsEmptyAuthEntriesOnly$1((android.util.Pair) obj);
                return lambda$containsEmptyAuthEntriesOnly$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$containsEmptyAuthEntriesOnly$1(android.util.Pair pair) {
        return ((android.credentials.selection.AuthenticationEntry) pair.second).getStatus() == 1 || ((android.credentials.selection.AuthenticationEntry) pair.second).getStatus() == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProviderResponseDataHandler {

        @android.annotation.Nullable
        private final android.content.ComponentName mExpectedRemoteEntryProviderService;

        @android.annotation.NonNull
        private final java.util.Map<java.lang.String, android.util.Pair<android.service.credentials.CredentialEntry, android.credentials.selection.Entry>> mUiCredentialEntries = new java.util.HashMap();

        @android.annotation.NonNull
        private final java.util.Map<java.lang.String, android.util.Pair<android.service.credentials.Action, android.credentials.selection.Entry>> mUiActionsEntries = new java.util.HashMap();

        @android.annotation.Nullable
        private final java.util.Map<java.lang.String, android.util.Pair<android.service.credentials.Action, android.credentials.selection.AuthenticationEntry>> mUiAuthenticationEntries = new java.util.HashMap();

        @android.annotation.NonNull
        private final java.util.Set<java.lang.String> mCredentialEntryTypes = new java.util.HashSet();

        @android.annotation.Nullable
        private android.util.Pair<java.lang.String, android.util.Pair<android.service.credentials.RemoteEntry, android.credentials.selection.Entry>> mUiRemoteEntry = null;

        ProviderResponseDataHandler(@android.annotation.Nullable android.content.ComponentName componentName) {
            this.mExpectedRemoteEntryProviderService = componentName;
        }

        public void addResponseContent(java.util.List<android.service.credentials.CredentialEntry> list, java.util.List<android.service.credentials.Action> list2, java.util.List<android.service.credentials.Action> list3, android.service.credentials.RemoteEntry remoteEntry, boolean z) {
            list.forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler.this.addCredentialEntry((android.service.credentials.CredentialEntry) obj);
                }
            });
            list2.forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler.this.addAction((android.service.credentials.Action) obj);
                }
            });
            list3.forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler.this.lambda$addResponseContent$0((android.service.credentials.Action) obj);
                }
            });
            if (remoteEntry != null || !z) {
                setRemoteEntry(remoteEntry);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addResponseContent$0(android.service.credentials.Action action) {
            addAuthenticationAction(action, 0);
        }

        public void addCredentialEntry(android.service.credentials.CredentialEntry credentialEntry) {
            java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
            this.mUiCredentialEntries.put(generateUniqueId, new android.util.Pair<>(credentialEntry, new android.credentials.selection.Entry(com.android.server.credentials.ProviderGetSession.CREDENTIAL_ENTRY_KEY, generateUniqueId, credentialEntry.getSlice(), com.android.server.credentials.ProviderGetSession.this.setUpFillInIntentWithFinalRequest(credentialEntry.getBeginGetCredentialOptionId()))));
            this.mCredentialEntryTypes.add(credentialEntry.getType());
        }

        public void addAction(android.service.credentials.Action action) {
            java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
            this.mUiActionsEntries.put(generateUniqueId, new android.util.Pair<>(action, new android.credentials.selection.Entry(com.android.server.credentials.ProviderGetSession.ACTION_ENTRY_KEY, generateUniqueId, action.getSlice(), com.android.server.credentials.ProviderGetSession.this.setUpFillInIntentWithQueryRequest())));
        }

        public void addAuthenticationAction(android.service.credentials.Action action, int i) {
            java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
            this.mUiAuthenticationEntries.put(generateUniqueId, new android.util.Pair<>(action, new android.credentials.selection.AuthenticationEntry(com.android.server.credentials.ProviderGetSession.AUTHENTICATION_ACTION_ENTRY_KEY, generateUniqueId, action.getSlice(), i, com.android.server.credentials.ProviderGetSession.this.setUpFillInIntentWithQueryRequest())));
        }

        public void removeAuthenticationAction(java.lang.String str) {
            this.mUiAuthenticationEntries.remove(str);
        }

        public void setRemoteEntry(@android.annotation.Nullable android.service.credentials.RemoteEntry remoteEntry) {
            if (!com.android.server.credentials.ProviderGetSession.this.enforceRemoteEntryRestrictions(this.mExpectedRemoteEntryProviderService)) {
                android.util.Slog.w(com.android.server.credentials.ProviderGetSession.TAG, "Remote entry being dropped as it does not meet the restriction checks.");
            } else if (remoteEntry == null) {
                this.mUiRemoteEntry = null;
            } else {
                java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
                this.mUiRemoteEntry = new android.util.Pair<>(generateUniqueId, new android.util.Pair(remoteEntry, new android.credentials.selection.Entry(com.android.server.credentials.ProviderGetSession.REMOTE_ENTRY_KEY, generateUniqueId, remoteEntry.getSlice(), com.android.server.credentials.ProviderGetSession.this.setUpFillInIntentForRemoteEntry())));
            }
        }

        public android.credentials.selection.GetCredentialProviderData toGetCredentialProviderData() {
            return new android.credentials.selection.GetCredentialProviderData.Builder(com.android.server.credentials.ProviderGetSession.this.mComponentName.flattenToString()).setActionChips(prepareActionEntries()).setCredentialEntries(prepareCredentialEntries()).setAuthenticationEntries(prepareAuthenticationEntries()).setRemoteEntry(prepareRemoteEntry()).build();
        }

        private java.util.List<android.credentials.selection.Entry> prepareActionEntries() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = this.mUiActionsEntries.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add((android.credentials.selection.Entry) this.mUiActionsEntries.get(it.next()).second);
            }
            return arrayList;
        }

        private java.util.List<android.credentials.selection.AuthenticationEntry> prepareAuthenticationEntries() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = this.mUiAuthenticationEntries.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add((android.credentials.selection.AuthenticationEntry) this.mUiAuthenticationEntries.get(it.next()).second);
            }
            return arrayList;
        }

        private java.util.List<android.credentials.selection.Entry> prepareCredentialEntries() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = this.mUiCredentialEntries.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add((android.credentials.selection.Entry) this.mUiCredentialEntries.get(it.next()).second);
            }
            return arrayList;
        }

        private android.credentials.selection.Entry prepareRemoteEntry() {
            if (this.mUiRemoteEntry == null || this.mUiRemoteEntry.first == null || this.mUiRemoteEntry.second == null) {
                return null;
            }
            return (android.credentials.selection.Entry) ((android.util.Pair) this.mUiRemoteEntry.second).second;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmptyResponse() {
            return this.mUiCredentialEntries.isEmpty() && this.mUiActionsEntries.isEmpty() && this.mUiAuthenticationEntries.isEmpty() && this.mUiRemoteEntry == null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmptyResponse(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) {
            return beginGetCredentialResponse.getCredentialEntries().isEmpty() && beginGetCredentialResponse.getActions().isEmpty() && beginGetCredentialResponse.getAuthenticationActions().isEmpty() && beginGetCredentialResponse.getRemoteCredentialEntry() == null;
        }

        @android.annotation.NonNull
        public java.util.Set<java.lang.String> getCredentialEntryTypes() {
            return this.mCredentialEntryTypes;
        }

        @android.annotation.Nullable
        public android.service.credentials.Action getAuthenticationAction(java.lang.String str) {
            if (this.mUiAuthenticationEntries.get(str) == null) {
                return null;
            }
            return (android.service.credentials.Action) this.mUiAuthenticationEntries.get(str).first;
        }

        @android.annotation.Nullable
        public android.service.credentials.Action getActionEntry(java.lang.String str) {
            if (this.mUiActionsEntries.get(str) == null) {
                return null;
            }
            return (android.service.credentials.Action) this.mUiActionsEntries.get(str).first;
        }

        @android.annotation.Nullable
        public android.service.credentials.RemoteEntry getRemoteEntry(java.lang.String str) {
            if (!((java.lang.String) this.mUiRemoteEntry.first).equals(str) || this.mUiRemoteEntry.second == null) {
                return null;
            }
            return (android.service.credentials.RemoteEntry) ((android.util.Pair) this.mUiRemoteEntry.second).first;
        }

        @android.annotation.Nullable
        public android.service.credentials.CredentialEntry getCredentialEntry(java.lang.String str) {
            if (this.mUiCredentialEntries.get(str) == null) {
                return null;
            }
            return (android.service.credentials.CredentialEntry) this.mUiCredentialEntries.get(str).first;
        }

        public void updateAuthEntryWithNoCredentialsReceived(@android.annotation.Nullable java.lang.String str) {
            if (str == null) {
                updatePreviousMostRecentAuthEntry();
            } else {
                updatePreviousMostRecentAuthEntry();
                updateMostRecentAuthEntry(str);
            }
        }

        private void updateMostRecentAuthEntry(java.lang.String str) {
            android.credentials.selection.AuthenticationEntry authenticationEntry = (android.credentials.selection.AuthenticationEntry) this.mUiAuthenticationEntries.get(str).second;
            this.mUiAuthenticationEntries.put(str, new android.util.Pair<>((android.service.credentials.Action) this.mUiAuthenticationEntries.get(str).first, copyAuthEntryAndChangeStatus(authenticationEntry, 2)));
        }

        private void updatePreviousMostRecentAuthEntry() {
            java.util.Optional<java.util.Map.Entry<java.lang.String, android.util.Pair<android.service.credentials.Action, android.credentials.selection.AuthenticationEntry>>> findFirst = this.mUiAuthenticationEntries.entrySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.credentials.ProviderGetSession$ProviderResponseDataHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$updatePreviousMostRecentAuthEntry$1;
                    lambda$updatePreviousMostRecentAuthEntry$1 = com.android.server.credentials.ProviderGetSession.ProviderResponseDataHandler.lambda$updatePreviousMostRecentAuthEntry$1((java.util.Map.Entry) obj);
                    return lambda$updatePreviousMostRecentAuthEntry$1;
                }
            }).findFirst();
            if (findFirst.isEmpty()) {
                return;
            }
            java.lang.String key = findFirst.get().getKey();
            this.mUiAuthenticationEntries.remove(key);
            this.mUiAuthenticationEntries.put(key, new android.util.Pair<>((android.service.credentials.Action) findFirst.get().getValue().first, copyAuthEntryAndChangeStatus((android.credentials.selection.AuthenticationEntry) findFirst.get().getValue().second, 1)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$updatePreviousMostRecentAuthEntry$1(java.util.Map.Entry entry) {
            return ((android.credentials.selection.AuthenticationEntry) ((android.util.Pair) entry.getValue()).second).getStatus() == 2;
        }

        private android.credentials.selection.AuthenticationEntry copyAuthEntryAndChangeStatus(android.credentials.selection.AuthenticationEntry authenticationEntry, java.lang.Integer num) {
            return new android.credentials.selection.AuthenticationEntry(com.android.server.credentials.ProviderGetSession.AUTHENTICATION_ACTION_ENTRY_KEY, authenticationEntry.getSubkey(), authenticationEntry.getSlice(), num.intValue(), authenticationEntry.getFrameworkExtrasIntent());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent setUpFillInIntentForRemoteEntry() {
        return new android.content.Intent().putExtra("android.service.credentials.extra.GET_CREDENTIAL_REQUEST", new android.service.credentials.GetCredentialRequest(this.mCallingAppInfo, this.mCompleteRequest.getCredentialOptions()));
    }
}
