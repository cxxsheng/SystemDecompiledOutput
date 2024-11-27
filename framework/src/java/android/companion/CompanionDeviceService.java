package android.companion;

/* loaded from: classes.dex */
public abstract class CompanionDeviceService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "CDM_CompanionDeviceService";
    public static final java.lang.String SERVICE_INTERFACE = "android.companion.CompanionDeviceService";
    private final android.companion.CompanionDeviceService.Stub mRemote = new android.companion.CompanionDeviceService.Stub();

    @java.lang.Deprecated
    public void onDeviceAppeared(java.lang.String str) {
    }

    @java.lang.Deprecated
    public void onDeviceDisappeared(java.lang.String str) {
    }

    @java.lang.Deprecated
    public void onMessageDispatchedFromSystem(int i, int i2, byte[] bArr) {
        android.util.Log.w(LOG_TAG, "Replaced by attachSystemDataTransport");
    }

    @java.lang.Deprecated
    public final void dispatchMessageToSystem(int i, int i2, byte[] bArr) throws android.companion.DeviceNotAssociatedException {
        android.util.Log.w(LOG_TAG, "Replaced by attachSystemDataTransport");
    }

    public final void attachSystemDataTransport(int i, java.io.InputStream inputStream, java.io.OutputStream outputStream) throws android.companion.DeviceNotAssociatedException {
        ((android.companion.CompanionDeviceManager) getSystemService(android.companion.CompanionDeviceManager.class)).attachSystemDataTransport(i, (java.io.InputStream) java.util.Objects.requireNonNull(inputStream), (java.io.OutputStream) java.util.Objects.requireNonNull(outputStream));
    }

    public final void detachSystemDataTransport(int i) throws android.companion.DeviceNotAssociatedException {
        ((android.companion.CompanionDeviceManager) getSystemService(android.companion.CompanionDeviceManager.class)).detachSystemDataTransport(i);
    }

    public void onDeviceAppeared(android.companion.AssociationInfo associationInfo) {
        if (!associationInfo.isSelfManaged()) {
            onDeviceAppeared(associationInfo.getDeviceMacAddressAsString());
        }
    }

    public void onDeviceDisappeared(android.companion.AssociationInfo associationInfo) {
        if (!associationInfo.isSelfManaged()) {
            onDeviceDisappeared(associationInfo.getDeviceMacAddressAsString());
        }
    }

    public void onDevicePresenceEvent(android.companion.DevicePresenceEvent devicePresenceEvent) {
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (java.util.Objects.equals(intent.getAction(), SERVICE_INTERFACE)) {
            onBindCompanionDeviceService(intent);
            return this.mRemote;
        }
        android.util.Log.w(LOG_TAG, "Tried to bind to wrong intent (should be android.companion.CompanionDeviceService): " + intent);
        return null;
    }

    public void onBindCompanionDeviceService(android.content.Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Stub extends android.companion.ICompanionDeviceService.Stub {
        final android.os.Handler mMainHandler;
        final android.companion.CompanionDeviceService mService;

        private Stub() {
            this.mMainHandler = android.os.Handler.getMain();
            this.mService = android.companion.CompanionDeviceService.this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceAppeared$0(android.companion.AssociationInfo associationInfo) {
            this.mService.onDeviceAppeared(associationInfo);
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceAppeared(final android.companion.AssociationInfo associationInfo) {
            this.mMainHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceService.Stub.this.lambda$onDeviceAppeared$0(associationInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceDisappeared$1(android.companion.AssociationInfo associationInfo) {
            this.mService.onDeviceDisappeared(associationInfo);
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceDisappeared(final android.companion.AssociationInfo associationInfo) {
            this.mMainHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.CompanionDeviceService.Stub.this.lambda$onDeviceDisappeared$1(associationInfo);
                }
            });
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDevicePresenceEvent(final android.companion.DevicePresenceEvent devicePresenceEvent) {
            if (android.companion.Flags.devicePresence()) {
                this.mMainHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.companion.CompanionDeviceService$Stub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.CompanionDeviceService.Stub.this.lambda$onDevicePresenceEvent$2(devicePresenceEvent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDevicePresenceEvent$2(android.companion.DevicePresenceEvent devicePresenceEvent) {
            this.mService.onDevicePresenceEvent(devicePresenceEvent);
        }
    }
}
