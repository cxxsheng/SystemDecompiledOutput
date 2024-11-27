package android.service.translation;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class TranslationService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.translation.TranslationService";
    public static final java.lang.String SERVICE_META_DATA = "android.translation_service";
    private static final java.lang.String TAG = "TranslationService";
    private android.view.translation.ITranslationServiceCallback mCallback;
    private android.os.Handler mHandler;
    private final android.service.translation.ITranslationService mInterface = new android.service.translation.TranslationService.AnonymousClass1();
    private final android.view.translation.ITranslationDirectManager mClientInterface = new android.view.translation.ITranslationDirectManager.Stub() { // from class: android.service.translation.TranslationService.2
        @Override // android.view.translation.ITranslationDirectManager
        public void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.ICancellationSignal iCancellationSignal, android.service.translation.ITranslationCallback iTranslationCallback) throws android.os.RemoteException {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.service.translation.TranslationService$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.service.translation.TranslationService) obj).onTranslationRequest((android.view.translation.TranslationRequest) obj2, ((java.lang.Integer) obj3).intValue(), (android.os.CancellationSignal) obj4, (java.util.function.Consumer<android.view.translation.TranslationResponse>) obj5);
                }
            }, android.service.translation.TranslationService.this, translationRequest, java.lang.Integer.valueOf(i), android.os.CancellationSignal.fromTransport(iCancellationSignal), new android.service.translation.OnTranslationResultCallbackWrapper(iTranslationCallback)));
        }

        @Override // android.view.translation.ITranslationDirectManager
        public void onFinishTranslationSession(int i) throws android.os.RemoteException {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.translation.TranslationService$2$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.translation.TranslationService) obj).onFinishTranslationSession(((java.lang.Integer) obj2).intValue());
                }
            }, android.service.translation.TranslationService.this, java.lang.Integer.valueOf(i)));
        }
    };

    @java.lang.Deprecated
    public interface OnTranslationResultCallback {
        @java.lang.Deprecated
        void onError();

        void onTranslationSuccess(android.view.translation.TranslationResponse translationResponse);
    }

    public abstract void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i, java.util.function.Consumer<java.lang.Boolean> consumer);

    public abstract void onFinishTranslationSession(int i);

    public abstract void onTranslationCapabilitiesRequest(int i, int i2, java.util.function.Consumer<java.util.Set<android.view.translation.TranslationCapability>> consumer);

    public abstract void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.view.translation.TranslationResponse> consumer);

    /* renamed from: android.service.translation.TranslationService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.translation.ITranslationService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.translation.ITranslationService
        public void onConnected(android.os.IBinder iBinder) {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.translation.TranslationService) obj).handleOnConnected((android.os.IBinder) obj2);
                }
            }, android.service.translation.TranslationService.this, iBinder));
        }

        @Override // android.service.translation.ITranslationService
        public void onDisconnected() {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.translation.TranslationService) obj).onDisconnected();
                }
            }, android.service.translation.TranslationService.this));
        }

        @Override // android.service.translation.ITranslationService
        public void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.translation.TranslationService) obj).handleOnCreateTranslationSession((android.view.translation.TranslationContext) obj2, ((java.lang.Integer) obj3).intValue(), (com.android.internal.os.IResultReceiver) obj4);
                }
            }, android.service.translation.TranslationService.this, translationContext, java.lang.Integer.valueOf(i), iResultReceiver));
        }

        @Override // android.service.translation.ITranslationService
        public void onTranslationCapabilitiesRequest(int i, int i2, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            android.service.translation.TranslationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.translation.TranslationService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.translation.TranslationService) obj).handleOnTranslationCapabilitiesRequest(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (android.os.ResultReceiver) obj4);
                }
            }, android.service.translation.TranslationService.this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), resultReceiver));
        }
    }

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
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.translation.TranslationService: " + intent);
        return null;
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    @java.lang.Deprecated
    public void onCreateTranslationSession(android.view.translation.TranslationContext translationContext, int i) {
    }

    @java.lang.Deprecated
    public void onTranslationRequest(android.view.translation.TranslationRequest translationRequest, int i, android.os.CancellationSignal cancellationSignal, android.service.translation.TranslationService.OnTranslationResultCallback onTranslationResultCallback) {
    }

    public final void updateTranslationCapability(android.view.translation.TranslationCapability translationCapability) {
        java.util.Objects.requireNonNull(translationCapability, "translation capability should not be null");
        android.view.translation.ITranslationServiceCallback iTranslationServiceCallback = this.mCallback;
        if (iTranslationServiceCallback == null) {
            android.util.Log.w(TAG, "updateTranslationCapability(): no server callback");
            return;
        }
        try {
            iTranslationServiceCallback.updateTranslationCapability(translationCapability);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(android.os.IBinder iBinder) {
        this.mCallback = android.view.translation.ITranslationServiceCallback.Stub.asInterface(iBinder);
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnCreateTranslationSession(final android.view.translation.TranslationContext translationContext, final int i, final com.android.internal.os.IResultReceiver iResultReceiver) {
        onCreateTranslationSession(translationContext, i, new java.util.function.Consumer<java.lang.Boolean>() { // from class: android.service.translation.TranslationService.3
            @Override // java.util.function.Consumer
            public void accept(java.lang.Boolean bool) {
                try {
                    if (!bool.booleanValue()) {
                        android.util.Log.w(android.service.translation.TranslationService.TAG, "handleOnCreateTranslationSession(): context=" + translationContext + " not supported by service.");
                        iResultReceiver.send(2, null);
                    } else {
                        android.os.Bundle bundle = new android.os.Bundle();
                        bundle.putBinder("binder", android.service.translation.TranslationService.this.mClientInterface.asBinder());
                        bundle.putInt("sessionId", i);
                        iResultReceiver.send(1, bundle);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.translation.TranslationService.TAG, "RemoteException sending client interface: " + e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnTranslationCapabilitiesRequest(final int i, final int i2, final android.os.ResultReceiver resultReceiver) {
        onTranslationCapabilitiesRequest(i, i2, new java.util.function.Consumer<java.util.Set<android.view.translation.TranslationCapability>>() { // from class: android.service.translation.TranslationService.4
            @Override // java.util.function.Consumer
            public void accept(java.util.Set<android.view.translation.TranslationCapability> set) {
                if (!android.service.translation.TranslationService.this.isValidCapabilities(i, i2, set)) {
                    throw new java.lang.IllegalStateException("Invalid capabilities and format compatibility");
                }
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putParcelable(android.view.translation.TranslationManager.EXTRA_CAPABILITIES, new android.content.pm.ParceledListSlice(java.util.Arrays.asList((android.view.translation.TranslationCapability[]) set.toArray(new android.view.translation.TranslationCapability[0]))));
                resultReceiver.send(1, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidCapabilities(int i, int i2, java.util.Set<android.view.translation.TranslationCapability> set) {
        if (i != 1 && i2 != 1) {
            return true;
        }
        java.util.Iterator<android.view.translation.TranslationCapability> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().getState() == 1000) {
                return false;
            }
        }
        return true;
    }
}
