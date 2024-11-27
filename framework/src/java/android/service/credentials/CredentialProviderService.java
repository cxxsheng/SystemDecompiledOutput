package android.service.credentials;

/* loaded from: classes3.dex */
public abstract class CredentialProviderService extends android.app.Service {
    public static final java.lang.String EXTRA_AUTOFILL_ID = "android.service.credentials.extra.AUTOFILL_ID";
    public static final java.lang.String EXTRA_BEGIN_GET_CREDENTIAL_REQUEST = "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_REQUEST";
    public static final java.lang.String EXTRA_BEGIN_GET_CREDENTIAL_RESPONSE = "android.service.credentials.extra.BEGIN_GET_CREDENTIAL_RESPONSE";
    public static final java.lang.String EXTRA_CREATE_CREDENTIAL_EXCEPTION = "android.service.credentials.extra.CREATE_CREDENTIAL_EXCEPTION";
    public static final java.lang.String EXTRA_CREATE_CREDENTIAL_REQUEST = "android.service.credentials.extra.CREATE_CREDENTIAL_REQUEST";
    public static final java.lang.String EXTRA_CREATE_CREDENTIAL_RESPONSE = "android.service.credentials.extra.CREATE_CREDENTIAL_RESPONSE";
    public static final java.lang.String EXTRA_GET_CREDENTIAL_EXCEPTION = "android.service.credentials.extra.GET_CREDENTIAL_EXCEPTION";
    public static final java.lang.String EXTRA_GET_CREDENTIAL_REQUEST = "android.service.credentials.extra.GET_CREDENTIAL_REQUEST";
    public static final java.lang.String EXTRA_GET_CREDENTIAL_RESPONSE = "android.service.credentials.extra.GET_CREDENTIAL_RESPONSE";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.credentials.CredentialProviderService";
    public static final java.lang.String SERVICE_META_DATA = "android.credentials.provider";
    public static final java.lang.String SYSTEM_SERVICE_INTERFACE = "android.service.credentials.system.CredentialProviderService";
    private static final java.lang.String TAG = "CredProviderService";
    public static final java.lang.String TEST_SYSTEM_PROVIDER_META_DATA_KEY = "android.credentials.testsystemprovider";
    private android.os.Handler mHandler;
    private final android.service.credentials.ICredentialProviderService mInterface = new android.service.credentials.ICredentialProviderService.Stub() { // from class: android.service.credentials.CredentialProviderService.1
        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, final android.service.credentials.IBeginGetCredentialCallback iBeginGetCredentialCallback) {
            java.util.Objects.requireNonNull(beginGetCredentialRequest);
            java.util.Objects.requireNonNull(iBeginGetCredentialCallback);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iBeginGetCredentialCallback.onCancellable(createTransport);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            android.service.credentials.CredentialProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.credentials.CredentialProviderService) obj).onBeginGetCredential((android.service.credentials.BeginGetCredentialRequest) obj2, (android.os.CancellationSignal) obj3, (android.service.credentials.CredentialProviderService.AnonymousClass1.C00081) obj4);
                }
            }, android.service.credentials.CredentialProviderService.this, beginGetCredentialRequest, android.os.CancellationSignal.fromTransport(createTransport), new android.os.OutcomeReceiver<android.service.credentials.BeginGetCredentialResponse, android.credentials.GetCredentialException>() { // from class: android.service.credentials.CredentialProviderService.1.1
                @Override // android.os.OutcomeReceiver
                public void onResult(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) {
                    try {
                        iBeginGetCredentialCallback.onSuccess(beginGetCredentialResponse);
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(android.credentials.GetCredentialException getCredentialException) {
                    try {
                        iBeginGetCredentialCallback.onFailure(getCredentialException.getType(), getCredentialException.getMessage());
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, final android.service.credentials.IBeginCreateCredentialCallback iBeginCreateCredentialCallback) {
            java.util.Objects.requireNonNull(beginCreateCredentialRequest);
            java.util.Objects.requireNonNull(iBeginCreateCredentialCallback);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iBeginCreateCredentialCallback.onCancellable(createTransport);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            android.service.credentials.CredentialProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.credentials.CredentialProviderService) obj).onBeginCreateCredential((android.service.credentials.BeginCreateCredentialRequest) obj2, (android.os.CancellationSignal) obj3, (android.service.credentials.CredentialProviderService.AnonymousClass1.AnonymousClass2) obj4);
                }
            }, android.service.credentials.CredentialProviderService.this, beginCreateCredentialRequest, android.os.CancellationSignal.fromTransport(createTransport), new android.os.OutcomeReceiver<android.service.credentials.BeginCreateCredentialResponse, android.credentials.CreateCredentialException>() { // from class: android.service.credentials.CredentialProviderService.1.2
                @Override // android.os.OutcomeReceiver
                public void onResult(android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse) {
                    try {
                        iBeginCreateCredentialCallback.onSuccess(beginCreateCredentialResponse);
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(android.credentials.CreateCredentialException createCredentialException) {
                    try {
                        iBeginCreateCredentialCallback.onFailure(createCredentialException.getType(), createCredentialException.getMessage());
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }

        @Override // android.service.credentials.ICredentialProviderService
        public void onClearCredentialState(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, final android.service.credentials.IClearCredentialStateCallback iClearCredentialStateCallback) {
            java.util.Objects.requireNonNull(clearCredentialStateRequest);
            java.util.Objects.requireNonNull(iClearCredentialStateCallback);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iClearCredentialStateCallback.onCancellable(createTransport);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            android.service.credentials.CredentialProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.credentials.CredentialProviderService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.credentials.CredentialProviderService) obj).onClearCredentialState((android.service.credentials.ClearCredentialStateRequest) obj2, (android.os.CancellationSignal) obj3, (android.service.credentials.CredentialProviderService.AnonymousClass1.AnonymousClass3) obj4);
                }
            }, android.service.credentials.CredentialProviderService.this, clearCredentialStateRequest, android.os.CancellationSignal.fromTransport(createTransport), new android.os.OutcomeReceiver<java.lang.Void, android.credentials.ClearCredentialStateException>() { // from class: android.service.credentials.CredentialProviderService.1.3
                @Override // android.os.OutcomeReceiver
                public void onResult(java.lang.Void r1) {
                    try {
                        iClearCredentialStateCallback.onSuccess();
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(android.credentials.ClearCredentialStateException clearCredentialStateException) {
                    try {
                        iClearCredentialStateCallback.onFailure(clearCredentialStateException.getType(), clearCredentialStateException.getMessage());
                    } catch (android.os.RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }));
        }
    };

    public abstract void onBeginCreateCredential(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, android.os.CancellationSignal cancellationSignal, android.os.OutcomeReceiver<android.service.credentials.BeginCreateCredentialResponse, android.credentials.CreateCredentialException> outcomeReceiver);

    public abstract void onBeginGetCredential(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, android.os.CancellationSignal cancellationSignal, android.os.OutcomeReceiver<android.service.credentials.BeginGetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver);

    public abstract void onClearCredentialState(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, android.os.CancellationSignal cancellationSignal, android.os.OutcomeReceiver<java.lang.Void, android.credentials.ClearCredentialStateException> outcomeReceiver);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Slog.w(TAG, "Failed to bind with intent: " + intent);
        return null;
    }
}
