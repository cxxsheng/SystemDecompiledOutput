package android.service.autofill;

/* loaded from: classes3.dex */
public abstract class AutofillService extends android.app.Service {
    public static final java.lang.String EXTRA_ERROR = "error";
    public static final java.lang.String EXTRA_FILL_RESPONSE = "android.service.autofill.extra.FILL_RESPONSE";
    public static final java.lang.String EXTRA_RESULT = "result";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.autofill.AutofillService";
    public static final java.lang.String SERVICE_META_DATA = "android.autofill";
    private static final java.lang.String TAG = "AutofillService";
    public static final java.lang.String WEBVIEW_REQUESTED_CREDENTIAL_KEY = "webview_requested_credential";
    private android.os.Handler mHandler;
    private final android.service.autofill.IAutoFillService mInterface = new android.service.autofill.IAutoFillService.Stub() { // from class: android.service.autofill.AutofillService.1
        @Override // android.service.autofill.IAutoFillService
        public void onConnectedStateChanged(boolean z) {
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(z ? new java.util.function.Consumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.autofill.AutofillService) obj).onConnected();
                }
            } : new java.util.function.Consumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.autofill.AutofillService) obj).onDisconnected();
                }
            }, android.service.autofill.AutofillService.this));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback) {
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iFillCallback.onCancellable(createTransport);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.autofill.AutofillService) obj).onFillRequest((android.service.autofill.FillRequest) obj2, (android.os.CancellationSignal) obj3, (android.service.autofill.FillCallback) obj4);
                }
            }, android.service.autofill.AutofillService.this, fillRequest, android.os.CancellationSignal.fromTransport(createTransport), new android.service.autofill.FillCallback(iFillCallback, fillRequest.getId())));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onConvertCredentialRequest(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) {
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.autofill.AutofillService) obj).onConvertCredentialRequest((android.service.autofill.ConvertCredentialRequest) obj2, (android.service.autofill.ConvertCredentialCallback) obj3);
                }
            }, android.service.autofill.AutofillService.this, convertCredentialRequest, new android.service.autofill.ConvertCredentialCallback(iConvertCredentialCallback)));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onFillCredentialRequest(android.service.autofill.FillRequest fillRequest, android.service.autofill.IFillCallback iFillCallback, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iFillCallback.onCancellable(createTransport);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.service.autofill.AutofillService) obj).onFillCredentialRequest((android.service.autofill.FillRequest) obj2, (android.os.CancellationSignal) obj3, (android.service.autofill.FillCallback) obj4, (android.view.autofill.IAutoFillManagerClient) obj5);
                }
            }, android.service.autofill.AutofillService.this, fillRequest, android.os.CancellationSignal.fromTransport(createTransport), new android.service.autofill.FillCallback(iFillCallback, fillRequest.getId()), iAutoFillManagerClient));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSaveRequest(android.service.autofill.SaveRequest saveRequest, android.service.autofill.ISaveCallback iSaveCallback) {
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.autofill.AutofillService) obj).onSaveRequest((android.service.autofill.SaveRequest) obj2, (android.service.autofill.SaveCallback) obj3);
                }
            }, android.service.autofill.AutofillService.this, saveRequest, new android.service.autofill.SaveCallback(iSaveCallback)));
        }

        @Override // android.service.autofill.IAutoFillService
        public void onSavedPasswordCountRequest(com.android.internal.os.IResultReceiver iResultReceiver) {
            android.service.autofill.AutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.autofill.AutofillService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.autofill.AutofillService) obj).onSavedDatasetsInfoRequest((android.service.autofill.SavedDatasetsInfoCallbackImpl) obj2);
                }
            }, android.service.autofill.AutofillService.this, new android.service.autofill.SavedDatasetsInfoCallbackImpl(iResultReceiver, android.service.autofill.SavedDatasetsInfo.TYPE_PASSWORDS)));
        }
    };

    public abstract void onFillRequest(android.service.autofill.FillRequest fillRequest, android.os.CancellationSignal cancellationSignal, android.service.autofill.FillCallback fillCallback);

    public abstract void onSaveRequest(android.service.autofill.SaveRequest saveRequest, android.service.autofill.SaveCallback saveCallback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
        android.os.BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.AutofillService: " + intent);
        return null;
    }

    public void onConnected() {
    }

    public void onFillCredentialRequest(android.service.autofill.FillRequest fillRequest, android.os.CancellationSignal cancellationSignal, android.service.autofill.FillCallback fillCallback, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
    }

    public void onConvertCredentialRequest(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.ConvertCredentialCallback convertCredentialCallback) {
    }

    public void onSavedDatasetsInfoRequest(android.service.autofill.SavedDatasetsInfoCallback savedDatasetsInfoCallback) {
        savedDatasetsInfoCallback.onError(1);
    }

    public void onDisconnected() {
    }

    public final android.service.autofill.FillEventHistory getFillEventHistory() {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager == null) {
            return null;
        }
        return autofillManager.getFillEventHistory();
    }
}
