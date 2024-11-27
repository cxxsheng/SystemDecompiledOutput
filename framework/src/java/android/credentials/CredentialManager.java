package android.credentials;

/* loaded from: classes.dex */
public final class CredentialManager {
    private static final java.lang.String DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API = "enable_credential_description_api";
    public static final java.lang.String DEVICE_CONFIG_ENABLE_CREDENTIAL_MANAGER = "enable_credential_manager";
    private static final android.os.Bundle OPTIONS_SENDER_BAL_OPTIN = android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
    public static final int PROVIDER_FILTER_ALL_PROVIDERS = 0;
    public static final int PROVIDER_FILTER_SYSTEM_PROVIDERS_ONLY = 1;
    public static final int PROVIDER_FILTER_USER_PROVIDERS_ONLY = 2;
    private static final java.lang.String TAG = "CredentialManager";
    private final android.content.Context mContext;
    private final android.credentials.ICredentialManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProviderFilter {
    }

    public CredentialManager(android.content.Context context, android.credentials.ICredentialManager iCredentialManager) {
        this.mContext = context;
        this.mService = iCredentialManager;
    }

    public void getCandidateCredentials(android.credentials.GetCredentialRequest getCredentialRequest, java.lang.String str, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.GetCandidateCredentialsResponse, android.credentials.GetCandidateCredentialsException> outcomeReceiver, android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(getCredentialRequest, "request must not be null");
        java.util.Objects.requireNonNull(str, "callingPackage must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "getCandidateCredentials already canceled");
            return;
        }
        android.os.ICancellationSignal iCancellationSignal = null;
        try {
            iCancellationSignal = this.mService.getCandidateCredentials(getCredentialRequest, new android.credentials.CredentialManager.GetCandidateCredentialsTransport(executor, outcomeReceiver), iBinder, str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void getCredential(android.content.Context context, android.credentials.GetCredentialRequest getCredentialRequest, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
        java.util.Objects.requireNonNull(getCredentialRequest, "request must not be null");
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "getCredential already canceled");
            return;
        }
        android.os.ICancellationSignal iCancellationSignal = null;
        try {
            iCancellationSignal = this.mService.executeGetCredential(getCredentialRequest, new android.credentials.CredentialManager.GetCredentialTransport(context, executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void getCredential(android.content.Context context, android.credentials.PrepareGetCredentialResponse.PendingGetCredentialHandle pendingGetCredentialHandle, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
        java.util.Objects.requireNonNull(pendingGetCredentialHandle, "pendingGetCredentialHandle must not be null");
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "getCredential already canceled");
        } else {
            pendingGetCredentialHandle.show(context, cancellationSignal, executor, outcomeReceiver);
        }
    }

    public void prepareGetCredential(android.credentials.GetCredentialRequest getCredentialRequest, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.PrepareGetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
        java.util.Objects.requireNonNull(getCredentialRequest, "request must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "prepareGetCredential already canceled");
            return;
        }
        android.os.ICancellationSignal iCancellationSignal = null;
        byte b = 0;
        android.credentials.CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase = new android.credentials.CredentialManager.GetCredentialTransportPendingUseCase();
        try {
            iCancellationSignal = this.mService.executePrepareGetCredential(getCredentialRequest, new android.credentials.CredentialManager.PrepareGetCredentialTransport(executor, outcomeReceiver, getCredentialTransportPendingUseCase), getCredentialTransportPendingUseCase, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void createCredential(android.content.Context context, android.credentials.CreateCredentialRequest createCredentialRequest, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.CreateCredentialResponse, android.credentials.CreateCredentialException> outcomeReceiver) {
        java.util.Objects.requireNonNull(createCredentialRequest, "request must not be null");
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "createCredential already canceled");
            return;
        }
        android.os.ICancellationSignal iCancellationSignal = null;
        try {
            iCancellationSignal = this.mService.executeCreateCredential(createCredentialRequest, new android.credentials.CredentialManager.CreateCredentialTransport(context, executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void clearCredentialState(android.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.credentials.ClearCredentialStateException> outcomeReceiver) {
        java.util.Objects.requireNonNull(clearCredentialStateRequest, "request must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            android.util.Log.w(TAG, "clearCredentialState already canceled");
            return;
        }
        android.os.ICancellationSignal iCancellationSignal = null;
        try {
            iCancellationSignal = this.mService.clearCredentialState(clearCredentialStateRequest, new android.credentials.CredentialManager.ClearCredentialStateTransport(executor, outcomeReceiver), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (cancellationSignal != null && iCancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignal);
        }
    }

    public void setEnabledProviders(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, int i, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.credentials.SetEnabledProvidersException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(outcomeReceiver, "callback must not be null");
        java.util.Objects.requireNonNull(list2, "providers must not be null");
        java.util.Objects.requireNonNull(list, "primaryProviders must not be null");
        try {
            this.mService.setEnabledProviders(list, list2, i, new android.credentials.CredentialManager.SetEnabledProvidersTransport(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isEnabledCredentialProviderService(android.content.ComponentName componentName) {
        java.util.Objects.requireNonNull(componentName, "componentName must not be null");
        try {
            return this.mService.isEnabledCredentialProviderService(componentName, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(int i) {
        try {
            return this.mService.getCredentialProviderServicesForTesting(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(int i, int i2) {
        try {
            return this.mService.getCredentialProviderServices(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isServiceEnabled(android.content.Context context) {
        android.credentials.CredentialManager credentialManager;
        java.util.Objects.requireNonNull(context, "context must not be null");
        if (context == null || (credentialManager = (android.credentials.CredentialManager) context.getSystemService("credential")) == null) {
            return false;
        }
        return credentialManager.isServiceEnabled();
    }

    private boolean isServiceEnabled() {
        try {
            return this.mService.isServiceEnabled();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public static boolean isCredentialDescriptionApiEnabled(android.content.Context context) {
        android.credentials.CredentialManager credentialManager;
        if (context == null || (credentialManager = (android.credentials.CredentialManager) context.getSystemService("credential")) == null) {
            return false;
        }
        return credentialManager.isCredentialDescriptionApiEnabled();
    }

    private boolean isCredentialDescriptionApiEnabled() {
        return android.provider.DeviceConfig.getBoolean("credential_manager", DEVICE_CONFIG_ENABLE_CREDENTIAL_DESC_API, false);
    }

    public void registerCredentialDescription(android.credentials.RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest) {
        java.util.Objects.requireNonNull(registerCredentialDescriptionRequest, "request must not be null");
        try {
            this.mService.registerCredentialDescription(registerCredentialDescriptionRequest, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unregisterCredentialDescription(android.credentials.UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest) {
        java.util.Objects.requireNonNull(unregisterCredentialDescriptionRequest, "request must not be null");
        try {
            this.mService.unregisterCredentialDescription(unregisterCredentialDescriptionRequest, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PrepareGetCredentialTransport extends android.credentials.IPrepareGetCredentialCallback.Stub {
        private final android.os.OutcomeReceiver<android.credentials.PrepareGetCredentialResponse, android.credentials.GetCredentialException> mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final android.credentials.CredentialManager.GetCredentialTransportPendingUseCase mGetCredentialTransport;

        private PrepareGetCredentialTransport(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.PrepareGetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver, android.credentials.CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
            this.mGetCredentialTransport = getCredentialTransportPendingUseCase;
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(final android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.PrepareGetCredentialTransport.this.lambda$onResponse$0(prepareGetCredentialResponseInternal);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) {
            this.mCallback.onResult(new android.credentials.PrepareGetCredentialResponse(prepareGetCredentialResponseInternal, this.mGetCredentialTransport));
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$PrepareGetCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.PrepareGetCredentialTransport.this.lambda$onError$1(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.GetCredentialException(str, str2));
        }
    }

    protected static class GetCredentialTransportPendingUseCase extends android.credentials.IGetCredentialCallback.Stub {
        private android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback mCallback;

        private GetCredentialTransportPendingUseCase() {
            this.mCallback = null;
        }

        public void setCallback(android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback getPendingCredentialInternalCallback) {
            if (this.mCallback == null) {
                this.mCallback = getPendingCredentialInternalCallback;
                return;
            }
            throw new java.lang.IllegalStateException("callback has already been set once");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(android.app.PendingIntent pendingIntent) {
            if (this.mCallback != null) {
                this.mCallback.onPendingIntent(pendingIntent);
            } else {
                android.util.Log.d(android.credentials.CredentialManager.TAG, "Unexpected onPendingIntent call before the show invocation");
            }
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(android.credentials.GetCredentialResponse getCredentialResponse) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mCallback.onResponse(getCredentialResponse);
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.d(android.credentials.CredentialManager.TAG, "Unexpected onResponse call before the show invocation");
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(java.lang.String str, java.lang.String str2) {
            if (this.mCallback != null) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mCallback.onError(str, str2);
                    return;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            android.util.Log.d(android.credentials.CredentialManager.TAG, "Unexpected onError call before the show invocation");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCandidateCredentialsTransport extends android.credentials.IGetCandidateCredentialsCallback.Stub {
        private final android.os.OutcomeReceiver<android.credentials.GetCandidateCredentialsResponse, android.credentials.GetCandidateCredentialsException> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private GetCandidateCredentialsTransport(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.GetCandidateCredentialsResponse, android.credentials.GetCandidateCredentialsException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onResponse(final android.credentials.GetCandidateCredentialsResponse getCandidateCredentialsResponse) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.GetCandidateCredentialsTransport.this.lambda$onResponse$0(getCandidateCredentialsResponse);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(android.credentials.GetCandidateCredentialsResponse getCandidateCredentialsResponse) {
            this.mCallback.onResult(getCandidateCredentialsResponse);
        }

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$GetCandidateCredentialsTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.GetCandidateCredentialsTransport.this.lambda$onError$1(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.GetCandidateCredentialsException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GetCredentialTransport extends android.credentials.IGetCredentialCallback.Stub {
        private final android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> mCallback;
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;

        private GetCredentialTransport(android.content.Context context, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(android.app.PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, android.credentials.CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Log.e(android.credentials.CredentialManager.TAG, "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(), e);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.credentials.CredentialManager.GetCredentialTransport.this.lambda$onPendingIntent$0();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(new android.credentials.GetCredentialException(android.credentials.GetCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(final android.credentials.GetCredentialResponse getCredentialResponse) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.GetCredentialTransport.this.lambda$onResponse$1(getCredentialResponse);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(android.credentials.GetCredentialResponse getCredentialResponse) {
            this.mCallback.onResult(getCredentialResponse);
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$GetCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.GetCredentialTransport.this.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.GetCredentialException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CreateCredentialTransport extends android.credentials.ICreateCredentialCallback.Stub {
        private final android.os.OutcomeReceiver<android.credentials.CreateCredentialResponse, android.credentials.CreateCredentialException> mCallback;
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;

        private CreateCredentialTransport(android.content.Context context, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.credentials.CreateCredentialResponse, android.credentials.CreateCredentialException> outcomeReceiver) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onPendingIntent(android.app.PendingIntent pendingIntent) {
            try {
                this.mContext.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, android.credentials.CredentialManager.OPTIONS_SENDER_BAL_OPTIN);
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Log.e(android.credentials.CredentialManager.TAG, "startIntentSender() failed for intent:" + pendingIntent.getIntentSender(), e);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.credentials.CredentialManager.CreateCredentialTransport.this.lambda$onPendingIntent$0();
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPendingIntent$0() {
            this.mCallback.onError(new android.credentials.CreateCredentialException(android.credentials.CreateCredentialException.TYPE_UNKNOWN));
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onResponse(final android.credentials.CreateCredentialResponse createCredentialResponse) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.CreateCredentialTransport.this.lambda$onResponse$1(createCredentialResponse);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1(android.credentials.CreateCredentialResponse createCredentialResponse) {
            this.mCallback.onResult(createCredentialResponse);
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$CreateCredentialTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.CreateCredentialTransport.this.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.CreateCredentialException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ClearCredentialStateTransport extends android.credentials.IClearCredentialStateCallback.Stub {
        private final android.os.OutcomeReceiver<java.lang.Void, android.credentials.ClearCredentialStateException> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private ClearCredentialStateTransport(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.credentials.ClearCredentialStateException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onSuccess() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mCallback.onResult(null);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$ClearCredentialStateTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.ClearCredentialStateTransport.this.lambda$onError$0(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.ClearCredentialStateException(str, str2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetEnabledProvidersTransport extends android.credentials.ISetEnabledProvidersCallback.Stub {
        private final android.os.OutcomeReceiver<java.lang.Void, android.credentials.SetEnabledProvidersException> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private SetEnabledProvidersTransport(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.credentials.SetEnabledProvidersException> outcomeReceiver) {
            this.mExecutor = executor;
            this.mCallback = outcomeReceiver;
        }

        public void onResponse(final java.lang.Void r5) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.SetEnabledProvidersTransport.this.lambda$onResponse$0(r5);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(java.lang.Void r2) {
            this.mCallback.onResult(r2);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onResponse() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.SetEnabledProvidersTransport.this.lambda$onResponse$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$1() {
            this.mCallback.onResult(null);
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onError(final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.credentials.CredentialManager$SetEnabledProvidersTransport$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.credentials.CredentialManager.SetEnabledProvidersTransport.this.lambda$onError$2(str, str2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(java.lang.String str, java.lang.String str2) {
            this.mCallback.onError(new android.credentials.SetEnabledProvidersException(str, str2));
        }
    }
}
