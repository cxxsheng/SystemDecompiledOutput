package android.service.assist.classification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class FieldClassificationService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.assist.classification.FieldClassificationService";
    private static final java.lang.String TAG = android.service.assist.classification.FieldClassificationService.class.getSimpleName();
    static boolean sDebug = !android.os.Build.IS_USER;
    static boolean sVerbose = false;
    private android.content.ComponentName mServiceComponentName;

    public abstract void onClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, android.os.CancellationSignal cancellationSignal, android.os.OutcomeReceiver<android.service.assist.classification.FieldClassificationResponse, java.lang.Exception> outcomeReceiver);

    private final class FieldClassificationServiceImpl extends android.service.assist.classification.IFieldClassificationService.Stub {
        private FieldClassificationServiceImpl() {
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onConnected(boolean z, boolean z2) {
            android.service.assist.classification.FieldClassificationService.this.handleOnConnected(z, z2);
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onDisconnected() {
            android.service.assist.classification.FieldClassificationService.this.handleOnDisconnected();
        }

        @Override // android.service.assist.classification.IFieldClassificationService
        public void onFieldClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, android.service.assist.classification.IFieldClassificationCallback iFieldClassificationCallback) {
            android.service.assist.classification.FieldClassificationService.this.handleOnClassificationRequest(fieldClassificationRequest, iFieldClassificationCallback);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        android.os.BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            this.mServiceComponentName = intent.getComponent();
            return new android.service.assist.classification.FieldClassificationService.FieldClassificationServiceImpl().asBinder();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.assist.classification.FieldClassificationService: " + intent);
        return null;
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(boolean z, boolean z2) {
        if (sDebug || z) {
            android.util.Log.d(TAG, "handleOnConnected(): debug=" + z + ", verbose=" + z2);
        }
        sDebug = z;
        sVerbose = z2;
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnClassificationRequest(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, final android.service.assist.classification.IFieldClassificationCallback iFieldClassificationCallback) {
        onClassificationRequest(fieldClassificationRequest, android.os.CancellationSignal.fromTransport(android.os.CancellationSignal.createTransport()), new android.os.OutcomeReceiver<android.service.assist.classification.FieldClassificationResponse, java.lang.Exception>() { // from class: android.service.assist.classification.FieldClassificationService.1
            @Override // android.os.OutcomeReceiver
            public void onResult(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) {
                try {
                    iFieldClassificationCallback.onSuccess(fieldClassificationResponse);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }

            @Override // android.os.OutcomeReceiver
            public void onError(java.lang.Exception exc) {
                try {
                    iFieldClassificationCallback.onFailure();
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        });
    }
}
