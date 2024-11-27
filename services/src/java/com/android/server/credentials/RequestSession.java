package com.android.server.credentials;

/* loaded from: classes.dex */
abstract class RequestSession<T, U, V> implements com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback {
    private static final java.lang.String TAG = "RequestSession";
    private final int mCallingUid;

    @android.annotation.NonNull
    protected final android.os.CancellationSignal mCancellationSignal;

    @android.annotation.NonNull
    protected final android.service.credentials.CallingAppInfo mClientAppInfo;

    @android.annotation.NonNull
    protected final U mClientCallback;

    @android.annotation.NonNull
    protected final T mClientRequest;

    @android.annotation.NonNull
    protected final android.content.Context mContext;

    @android.annotation.NonNull
    protected final com.android.server.credentials.CredentialManagerUi mCredentialManagerUi;
    private final java.util.Set<android.content.ComponentName> mEnabledProviders;

    @android.annotation.Nullable
    protected android.os.ResultReceiver mFinalResponseReceiver;
    protected final java.lang.String mHybridService;
    protected final java.lang.Object mLock;
    protected android.app.PendingIntent mPendingIntent;

    @android.annotation.NonNull
    protected final java.lang.String mRequestType;
    protected final com.android.server.credentials.RequestSession.SessionLifetime mSessionCallback;
    protected final int mUserId;
    protected final java.util.Map<java.lang.String, com.android.server.credentials.ProviderSession> mProviders = new java.util.concurrent.ConcurrentHashMap();
    private final com.android.server.credentials.RequestSession<T, U, V>.RequestSessionDeathRecipient mDeathRecipient = new com.android.server.credentials.RequestSession.RequestSessionDeathRecipient();

    @android.annotation.NonNull
    protected com.android.server.credentials.RequestSession.RequestSessionStatus mRequestSessionStatus = com.android.server.credentials.RequestSession.RequestSessionStatus.IN_PROGRESS;

    @android.annotation.NonNull
    protected final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);

    @android.annotation.NonNull
    protected final android.os.IBinder mRequestId = new android.os.Binder();
    protected final int mUniqueSessionInteger = com.android.server.credentials.MetricUtilities.getHighlyUniqueInteger();
    protected final com.android.server.credentials.metrics.RequestSessionMetric mRequestSessionMetric = new com.android.server.credentials.metrics.RequestSessionMetric(this.mUniqueSessionInteger, com.android.server.credentials.MetricUtilities.getHighlyUniqueInteger());

    enum RequestSessionStatus {
        IN_PROGRESS,
        CANCELLED,
        COMPLETE
    }

    public interface SessionLifetime {
        void onFinishRequestSession(int i, android.os.IBinder iBinder);
    }

    public abstract com.android.server.credentials.ProviderSession initiateProviderSession(android.credentials.CredentialProviderInfo credentialProviderInfo, com.android.server.credentials.RemoteCredentialService remoteCredentialService);

    protected abstract void invokeClientCallbackError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    protected abstract void invokeClientCallbackSuccess(V v) throws android.os.RemoteException;

    protected abstract void launchUiWithProviderData(java.util.ArrayList<android.credentials.selection.ProviderData> arrayList);

    protected RequestSession(@android.annotation.NonNull android.content.Context context, com.android.server.credentials.RequestSession.SessionLifetime sessionLifetime, java.lang.Object obj, int i, int i2, @android.annotation.NonNull T t, U u, @android.annotation.NonNull java.lang.String str, android.service.credentials.CallingAppInfo callingAppInfo, java.util.Set<android.content.ComponentName> set, android.os.CancellationSignal cancellationSignal, long j, boolean z) {
        this.mContext = context;
        this.mLock = obj;
        this.mSessionCallback = sessionLifetime;
        this.mUserId = i;
        this.mCallingUid = i2;
        this.mClientRequest = t;
        this.mClientCallback = u;
        this.mRequestType = str;
        this.mClientAppInfo = callingAppInfo;
        this.mEnabledProviders = set;
        this.mCancellationSignal = cancellationSignal;
        this.mCredentialManagerUi = new com.android.server.credentials.CredentialManagerUi(this.mContext, this.mUserId, this, this.mEnabledProviders);
        this.mHybridService = context.getResources().getString(android.R.string.config_defaultContentCaptureService);
        this.mRequestSessionMetric.collectInitialPhaseMetricInfo(j, this.mCallingUid, com.android.server.credentials.metrics.ApiName.getMetricCodeFromRequestInfo(this.mRequestType));
        setCancellationListener();
        if (z && android.credentials.flags.Flags.clearSessionEnabled() && this.mClientCallback != null && (this.mClientCallback instanceof android.os.IInterface)) {
            setUpClientCallbackListener(((android.os.IInterface) this.mClientCallback).asBinder());
        }
    }

    protected void setUpClientCallbackListener(android.os.IBinder iBinder) {
        if (this.mClientCallback != null && (this.mClientCallback instanceof android.os.IInterface)) {
            try {
                iBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, e.getMessage());
            }
        }
    }

    private void setCancellationListener() {
        this.mCancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: com.android.server.credentials.RequestSession$$ExternalSyntheticLambda1
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                com.android.server.credentials.RequestSession.this.lambda$setCancellationListener$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCancellationListener$0() {
        android.util.Slog.d(TAG, "Cancellation invoked from the client - clearing session");
        finishSession(!maybeCancelUi(), com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
    }

    private boolean maybeCancelUi() {
        if (this.mCredentialManagerUi.getStatus() == com.android.server.credentials.CredentialManagerUi.UiStatus.USER_INTERACTION) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mContext.startActivityAsUser(this.mCredentialManagerUi.createCancelIntent(this.mRequestId, this.mClientAppInfo.getPackageName()).addFlags(268435456), android.os.UserHandle.of(this.mUserId));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUiWaitingForData() {
        return this.mCredentialManagerUi.getStatus() == com.android.server.credentials.CredentialManagerUi.UiStatus.IN_PROGRESS;
    }

    public void addProviderSession(android.content.ComponentName componentName, com.android.server.credentials.ProviderSession providerSession) {
        this.mProviders.put(componentName.flattenToString(), providerSession);
    }

    @Override // com.android.server.credentials.CredentialManagerUi.CredentialManagerUiCallback
    public void onUiSelection(android.credentials.selection.UserSelectionDialogResult userSelectionDialogResult, android.os.ResultReceiver resultReceiver) {
        if (this.mRequestSessionStatus == com.android.server.credentials.RequestSession.RequestSessionStatus.COMPLETE) {
            android.util.Slog.w(TAG, "Request has already been completed. This is strange.");
            return;
        }
        if (isSessionCancelled()) {
            finishSession(true, com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
            return;
        }
        com.android.server.credentials.ProviderSession providerSession = this.mProviders.get(userSelectionDialogResult.getProviderId());
        if (providerSession == null) {
            android.util.Slog.w(TAG, "providerSession not found in onUiSelection. This is strange.");
            return;
        }
        this.mFinalResponseReceiver = resultReceiver;
        com.android.server.credentials.metrics.ProviderSessionMetric providerSessionMetric = providerSession.mProviderSessionMetric;
        int size = providerSessionMetric.getBrowsedAuthenticationMetric().size();
        this.mRequestSessionMetric.collectMetricPerBrowsingSelect(userSelectionDialogResult, providerSession.mProviderSessionMetric.getCandidatePhasePerProviderMetric());
        providerSession.onUiEntrySelected(userSelectionDialogResult.getEntryKey(), userSelectionDialogResult.getEntrySubkey(), userSelectionDialogResult.getPendingIntentProviderResponse());
        int size2 = providerSessionMetric.getBrowsedAuthenticationMetric().size();
        if (size2 - size == 1) {
            this.mRequestSessionMetric.logAuthEntry(providerSession.mProviderSessionMetric.getBrowsedAuthenticationMetric().get(size2 - 1));
        }
    }

    protected void finishSession(boolean z, int i) {
        android.util.Slog.i(TAG, "finishing session with propagateCancellation " + z);
        if (z) {
            this.mProviders.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.credentials.RequestSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.credentials.ProviderSession) obj).cancelProviderRemoteSession();
                }
            });
        }
        this.mRequestSessionMetric.logApiCalledAtFinish(i);
        this.mRequestSessionStatus = com.android.server.credentials.RequestSession.RequestSessionStatus.COMPLETE;
        this.mProviders.clear();
        clearRequestSessionLocked();
    }

    void cancelExistingPendingIntent() {
        if (this.mPendingIntent != null) {
            try {
                this.mPendingIntent.cancel();
                this.mPendingIntent = null;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Unable to cancel existing pending intent", e);
            }
        }
    }

    private void clearRequestSessionLocked() {
        synchronized (this.mLock) {
            this.mSessionCallback.onFinishRequestSession(this.mUserId, this.mRequestId);
        }
    }

    protected boolean isAnyProviderPending() {
        java.util.Iterator<com.android.server.credentials.ProviderSession> it = this.mProviders.values().iterator();
        while (it.hasNext()) {
            if (com.android.server.credentials.ProviderSession.isStatusWaitingForRemoteResponse(it.next().getStatus())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSessionCancelled() {
        return this.mCancellationSignal.isCanceled();
    }

    protected boolean isUiInvocationNeeded() {
        for (com.android.server.credentials.ProviderSession providerSession : this.mProviders.values()) {
            if (com.android.server.credentials.ProviderSession.isUiInvokingStatus(providerSession.getStatus())) {
                return true;
            }
            if (com.android.server.credentials.ProviderSession.isStatusWaitingForRemoteResponse(providerSession.getStatus())) {
                return false;
            }
        }
        return false;
    }

    void getProviderDataAndInitiateUi() {
        java.util.ArrayList<android.credentials.selection.ProviderData> providerDataForUi = getProviderDataForUi();
        if (!providerDataForUi.isEmpty()) {
            launchUiWithProviderData(providerDataForUi);
        }
    }

    @android.annotation.NonNull
    protected java.util.ArrayList<android.credentials.selection.ProviderData> getProviderDataForUi() {
        android.util.Slog.i(TAG, "For ui, provider data size: " + this.mProviders.size());
        java.util.ArrayList<android.credentials.selection.ProviderData> arrayList = new java.util.ArrayList<>();
        this.mRequestSessionMetric.logCandidatePhaseMetrics(this.mProviders);
        if (isSessionCancelled()) {
            finishSession(true, com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
            return arrayList;
        }
        java.util.Iterator<com.android.server.credentials.ProviderSession> it = this.mProviders.values().iterator();
        while (it.hasNext()) {
            android.credentials.selection.ProviderData mo3078prepareUiData = it.next().mo3078prepareUiData();
            if (mo3078prepareUiData != null) {
                arrayList.add(mo3078prepareUiData);
            }
        }
        return arrayList;
    }

    protected void respondToClientWithResponseAndFinish(V v) {
        this.mRequestSessionMetric.logCandidateAggregateMetrics(this.mProviders);
        this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(false, com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_SUCCESS);
        if (this.mRequestSessionStatus == com.android.server.credentials.RequestSession.RequestSessionStatus.COMPLETE) {
            android.util.Slog.w(TAG, "Request has already been completed. This is strange.");
            return;
        }
        if (isSessionCancelled()) {
            finishSession(true, com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
            return;
        }
        try {
            invokeClientCallbackSuccess(v);
            finishSession(false, com.android.server.credentials.metrics.ApiStatus.SUCCESS.getMetricCode());
        } catch (android.os.RemoteException e) {
            this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(true, com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_FAILURE);
            android.util.Slog.e(TAG, "Issue while responding to client with a response : " + e);
            finishSession(false, com.android.server.credentials.metrics.ApiStatus.FAILURE.getMetricCode());
        }
    }

    protected void respondToClientWithErrorAndFinish(java.lang.String str, java.lang.String str2) {
        this.mRequestSessionMetric.logCandidateAggregateMetrics(this.mProviders);
        this.mRequestSessionMetric.collectFinalPhaseProviderMetricStatus(true, com.android.server.credentials.metrics.ProviderStatusForMetrics.FINAL_FAILURE);
        if (this.mRequestSessionStatus == com.android.server.credentials.RequestSession.RequestSessionStatus.COMPLETE) {
            android.util.Slog.w(TAG, "Request has already been completed. This is strange.");
            return;
        }
        if (isSessionCancelled()) {
            finishSession(true, com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
            return;
        }
        try {
            invokeClientCallbackError(str, str2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Issue while responding to client with error : " + e);
        }
        if (str.contains(com.android.server.credentials.MetricUtilities.USER_CANCELED_SUBSTRING)) {
            this.mRequestSessionMetric.setHasExceptionFinalPhase(false);
            finishSession(false, com.android.server.credentials.metrics.ApiStatus.USER_CANCELED.getMetricCode());
        } else {
            finishSession(false, com.android.server.credentials.metrics.ApiStatus.FAILURE.getMetricCode());
        }
    }

    protected boolean isPrimaryProviderViaProviderInfo(android.content.ComponentName componentName) {
        com.android.server.credentials.ProviderSession providerSession = this.mProviders.get(componentName.flattenToString());
        return (providerSession == null || providerSession.mProviderInfo == null || !providerSession.mProviderInfo.isPrimary()) ? false : true;
    }

    private class RequestSessionDeathRecipient implements android.os.IBinder.DeathRecipient {
        private RequestSessionDeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.d(com.android.server.credentials.RequestSession.TAG, "Client binder died - clearing session");
            com.android.server.credentials.RequestSession.this.finishSession(com.android.server.credentials.RequestSession.this.isUiWaitingForData(), com.android.server.credentials.metrics.ApiStatus.CLIENT_CANCELED.getMetricCode());
        }
    }
}
