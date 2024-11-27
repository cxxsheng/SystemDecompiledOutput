package com.android.server.credentials;

/* loaded from: classes.dex */
public final class ProviderCreateSession extends com.android.server.credentials.ProviderSession<android.service.credentials.BeginCreateCredentialRequest, android.service.credentials.BeginCreateCredentialResponse> {
    private static final java.lang.String REMOTE_ENTRY_KEY = "remote_entry_key";
    public static final java.lang.String SAVE_ENTRY_KEY = "save_entry_key";
    private static final java.lang.String TAG = "ProviderCreateSession";
    private final android.service.credentials.CreateCredentialRequest mCompleteRequest;
    private android.credentials.CreateCredentialException mProviderException;
    private final com.android.server.credentials.ProviderCreateSession.ProviderResponseDataHandler mProviderResponseDataHandler;

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.Nullable
    public static com.android.server.credentials.ProviderCreateSession createNewSession(android.content.Context context, int i, android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.CreateRequestSession createRequestSession, com.android.server.credentials.RemoteCredentialService remoteCredentialService) {
        android.service.credentials.CreateCredentialRequest createProviderRequest = createProviderRequest(credentialProviderInfo.getCapabilities(), (android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest, createRequestSession.mClientAppInfo, credentialProviderInfo.isSystemProvider());
        if (createProviderRequest != null) {
            return new com.android.server.credentials.ProviderCreateSession(context, credentialProviderInfo, createRequestSession, i, remoteCredentialService, constructQueryPhaseRequest(((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).getType(), ((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).getCandidateQueryData(), createRequestSession.mClientAppInfo, ((android.credentials.CreateCredentialRequest) createRequestSession.mClientRequest).alwaysSendAppInfoToProvider()), createProviderRequest, createRequestSession.mHybridService);
        }
        android.util.Slog.i(TAG, "Unable to create provider session for: " + credentialProviderInfo.getComponentName());
        return null;
    }

    private static android.service.credentials.BeginCreateCredentialRequest constructQueryPhaseRequest(java.lang.String str, android.os.Bundle bundle, android.service.credentials.CallingAppInfo callingAppInfo, boolean z) {
        if (z) {
            return new android.service.credentials.BeginCreateCredentialRequest(str, bundle, callingAppInfo);
        }
        return new android.service.credentials.BeginCreateCredentialRequest(str, bundle);
    }

    @android.annotation.Nullable
    private static android.service.credentials.CreateCredentialRequest createProviderRequest(java.util.List<java.lang.String> list, android.credentials.CreateCredentialRequest createCredentialRequest, android.service.credentials.CallingAppInfo callingAppInfo, boolean z) {
        if (createCredentialRequest.isSystemProviderRequired() && !z) {
            return null;
        }
        java.lang.String type = createCredentialRequest.getType();
        if (list.contains(type)) {
            return new android.service.credentials.CreateCredentialRequest(callingAppInfo, type, createCredentialRequest.getCredentialData());
        }
        return null;
    }

    private ProviderCreateSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.credentials.CredentialProviderInfo credentialProviderInfo, @android.annotation.NonNull com.android.server.credentials.ProviderSession.ProviderInternalCallback<android.credentials.CreateCredentialResponse> providerInternalCallback, int i, @android.annotation.NonNull com.android.server.credentials.RemoteCredentialService remoteCredentialService, @android.annotation.NonNull android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, @android.annotation.NonNull android.service.credentials.CreateCredentialRequest createCredentialRequest, java.lang.String str) {
        super(context, beginCreateCredentialRequest, providerInternalCallback, credentialProviderInfo.getComponentName(), i, remoteCredentialService);
        this.mCompleteRequest = createCredentialRequest;
        setStatus(com.android.server.credentials.ProviderSession.Status.PENDING);
        this.mProviderResponseDataHandler = new com.android.server.credentials.ProviderCreateSession.ProviderResponseDataHandler(android.content.ComponentName.unflattenFromString(str));
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseSuccess(@android.annotation.Nullable android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse) {
        android.util.Slog.i(TAG, "Remote provider responded with a valid response: " + this.mComponentName);
        onSetInitialRemoteResponse(beginCreateCredentialResponse);
    }

    @Override // com.android.server.credentials.RemoteCredentialService.ProviderCallbacks
    public void onProviderResponseFailure(int i, @android.annotation.Nullable java.lang.Exception exc) {
        if (exc instanceof android.credentials.CreateCredentialException) {
            this.mProviderException = (android.credentials.CreateCredentialException) exc;
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

    /* JADX WARN: Multi-variable type inference failed */
    private void onSetInitialRemoteResponse(android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse) {
        this.mProviderResponse = beginCreateCredentialResponse;
        this.mProviderResponseDataHandler.addResponseContent(beginCreateCredentialResponse.getCreateEntries(), beginCreateCredentialResponse.getRemoteCreateEntry());
        if (this.mProviderResponseDataHandler.isEmptyResponse(beginCreateCredentialResponse)) {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((com.android.server.credentials.RequestSession) this.mCallbacks).mRequestSessionMetric.getInitialPhaseMetric());
            updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.EMPTY_RESPONSE, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        } else {
            this.mProviderSessionMetric.collectCandidateEntryMetrics(beginCreateCredentialResponse, false, ((com.android.server.credentials.RequestSession) this.mCallbacks).mRequestSessionMetric.getInitialPhaseMetric());
            updateStatusAndInvokeCallback(com.android.server.credentials.ProviderSession.Status.SAVE_ENTRIES_RECEIVED, com.android.server.credentials.ProviderSession.CredentialsSource.REMOTE_PROVIDER);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.credentials.ProviderSession
    @android.annotation.Nullable
    /* renamed from: prepareUiData, reason: merged with bridge method [inline-methods] */
    public android.credentials.selection.CreateCredentialProviderData mo3078prepareUiData() throws java.lang.IllegalArgumentException {
        if (!com.android.server.credentials.ProviderSession.isUiInvokingStatus(getStatus())) {
            android.util.Slog.i(TAG, "No data for UI from: " + this.mComponentName.flattenToString());
            return null;
        }
        if (this.mProviderResponse == 0 || this.mProviderResponseDataHandler.isEmptyResponse()) {
            return null;
        }
        return this.mProviderResponseDataHandler.toCreateCredentialProviderData();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.credentials.ProviderSession
    public void onUiEntrySelected(java.lang.String str, java.lang.String str2, android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        char c;
        switch (str.hashCode()) {
            case -1424551728:
                if (str.equals(SAVE_ENTRY_KEY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1110515801:
                if (str.equals("remote_entry_key")) {
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
                if (this.mProviderResponseDataHandler.getCreateEntry(str2) == null) {
                    android.util.Slog.i(TAG, "Unexpected save entry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                } else {
                    onCreateEntrySelected(providerPendingIntentResponse);
                    break;
                }
            case 1:
                if (this.mProviderResponseDataHandler.getRemoteEntry(str2) == null) {
                    android.util.Slog.i(TAG, "Unexpected remote entry key");
                    invokeCallbackOnInternalInvalidState();
                    break;
                } else {
                    onRemoteEntrySelected(providerPendingIntentResponse);
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
            this.mRemoteCredentialService.onBeginCreateCredential((android.service.credentials.BeginCreateCredentialRequest) this.mProviderRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.Intent setUpFillInIntent() {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST", this.mCompleteRequest);
        return intent;
    }

    private void onCreateEntrySelected(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        android.credentials.CreateCredentialException maybeGetPendingIntentException = maybeGetPendingIntentException(providerPendingIntentResponse);
        if (maybeGetPendingIntentException != null) {
            invokeCallbackWithError(maybeGetPendingIntentException.getType(), maybeGetPendingIntentException.getMessage());
            return;
        }
        android.credentials.CreateCredentialResponse extractCreateCredentialResponse = com.android.server.credentials.PendingIntentResultHandler.extractCreateCredentialResponse(providerPendingIntentResponse.getResultData());
        if (extractCreateCredentialResponse != null) {
            this.mCallbacks.onFinalResponseReceived(this.mComponentName, extractCreateCredentialResponse);
        } else {
            android.util.Slog.i(TAG, "onSaveEntrySelected - no response or error found in pending intent response");
            invokeCallbackOnInternalInvalidState();
        }
    }

    private void onRemoteEntrySelected(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        onCreateEntrySelected(providerPendingIntentResponse);
    }

    @android.annotation.Nullable
    private android.credentials.CreateCredentialException maybeGetPendingIntentException(android.credentials.selection.ProviderPendingIntentResponse providerPendingIntentResponse) {
        if (providerPendingIntentResponse == null) {
            android.util.Slog.i(TAG, "pendingIntentResponse is null");
            return new android.credentials.CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isValidResponse(providerPendingIntentResponse)) {
            android.credentials.CreateCredentialException extractCreateCredentialException = com.android.server.credentials.PendingIntentResultHandler.extractCreateCredentialException(providerPendingIntentResponse.getResultData());
            if (extractCreateCredentialException != null) {
                android.util.Slog.i(TAG, "Pending intent contains provider exception");
                return extractCreateCredentialException;
            }
            return null;
        }
        if (com.android.server.credentials.PendingIntentResultHandler.isCancelledResponse(providerPendingIntentResponse)) {
            return new android.credentials.CreateCredentialException("android.credentials.CreateCredentialException.TYPE_USER_CANCELED");
        }
        return new android.credentials.CreateCredentialException("android.credentials.CreateCredentialException.TYPE_NO_CREATE_OPTIONS");
    }

    private void invokeCallbackOnInternalInvalidState() {
        this.mCallbacks.onFinalErrorReceived(this.mComponentName, "android.credentials.CreateCredentialException.TYPE_UNKNOWN", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProviderResponseDataHandler {

        @android.annotation.Nullable
        private final android.content.ComponentName mExpectedRemoteEntryProviderService;

        @android.annotation.NonNull
        private final java.util.Map<java.lang.String, android.util.Pair<android.service.credentials.CreateEntry, android.credentials.selection.Entry>> mUiCreateEntries = new java.util.HashMap();

        @android.annotation.Nullable
        private android.util.Pair<java.lang.String, android.util.Pair<android.service.credentials.RemoteEntry, android.credentials.selection.Entry>> mUiRemoteEntry = null;

        ProviderResponseDataHandler(@android.annotation.Nullable android.content.ComponentName componentName) {
            this.mExpectedRemoteEntryProviderService = componentName;
        }

        public void addResponseContent(java.util.List<android.service.credentials.CreateEntry> list, android.service.credentials.RemoteEntry remoteEntry) {
            list.forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.ProviderCreateSession$ProviderResponseDataHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.credentials.ProviderCreateSession.ProviderResponseDataHandler.this.addCreateEntry((android.service.credentials.CreateEntry) obj);
                }
            });
            if (remoteEntry != null) {
                setRemoteEntry(remoteEntry);
            }
        }

        public void addCreateEntry(android.service.credentials.CreateEntry createEntry) {
            java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
            this.mUiCreateEntries.put(generateUniqueId, new android.util.Pair<>(createEntry, new android.credentials.selection.Entry(com.android.server.credentials.ProviderCreateSession.SAVE_ENTRY_KEY, generateUniqueId, createEntry.getSlice(), com.android.server.credentials.ProviderCreateSession.this.setUpFillInIntent())));
        }

        public void setRemoteEntry(@android.annotation.Nullable android.service.credentials.RemoteEntry remoteEntry) {
            if (!com.android.server.credentials.ProviderCreateSession.this.enforceRemoteEntryRestrictions(this.mExpectedRemoteEntryProviderService)) {
                android.util.Slog.w(com.android.server.credentials.ProviderCreateSession.TAG, "Remote entry being dropped as it does not meet the restrictionchecks.");
            } else if (remoteEntry == null) {
                this.mUiRemoteEntry = null;
            } else {
                java.lang.String generateUniqueId = com.android.server.credentials.ProviderSession.generateUniqueId();
                this.mUiRemoteEntry = new android.util.Pair<>(generateUniqueId, new android.util.Pair(remoteEntry, new android.credentials.selection.Entry("remote_entry_key", generateUniqueId, remoteEntry.getSlice(), com.android.server.credentials.ProviderCreateSession.this.setUpFillInIntent())));
            }
        }

        public android.credentials.selection.CreateCredentialProviderData toCreateCredentialProviderData() {
            return new android.credentials.selection.CreateCredentialProviderData.Builder(com.android.server.credentials.ProviderCreateSession.this.mComponentName.flattenToString()).setSaveEntries(prepareUiCreateEntries()).setRemoteEntry(prepareRemoteEntry()).build();
        }

        private java.util.List<android.credentials.selection.Entry> prepareUiCreateEntries() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = this.mUiCreateEntries.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add((android.credentials.selection.Entry) this.mUiCreateEntries.get(it.next()).second);
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
            return this.mUiCreateEntries.isEmpty() && this.mUiRemoteEntry == null;
        }

        @android.annotation.Nullable
        public android.service.credentials.RemoteEntry getRemoteEntry(java.lang.String str) {
            if (this.mUiRemoteEntry == null || this.mUiRemoteEntry.first == null || !((java.lang.String) this.mUiRemoteEntry.first).equals(str) || this.mUiRemoteEntry.second == null) {
                return null;
            }
            return (android.service.credentials.RemoteEntry) ((android.util.Pair) this.mUiRemoteEntry.second).first;
        }

        @android.annotation.Nullable
        public android.service.credentials.CreateEntry getCreateEntry(java.lang.String str) {
            if (this.mUiCreateEntries.get(str) == null) {
                return null;
            }
            return (android.service.credentials.CreateEntry) this.mUiCreateEntries.get(str).first;
        }

        public boolean isEmptyResponse(android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse) {
            return (beginCreateCredentialResponse.getCreateEntries() == null || beginCreateCredentialResponse.getCreateEntries().isEmpty()) && beginCreateCredentialResponse.getRemoteCreateEntry() == null;
        }
    }
}
