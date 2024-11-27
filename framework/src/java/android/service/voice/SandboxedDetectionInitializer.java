package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface SandboxedDetectionInitializer {
    public static final int INITIALIZATION_STATUS_SUCCESS = 0;
    public static final int INITIALIZATION_STATUS_UNKNOWN = 100;
    public static final java.lang.String KEY_INITIALIZATION_STATUS = "initialization_status";
    public static final int MAXIMUM_NUMBER_OF_INITIALIZATION_STATUS_CUSTOM_ERROR = 2;

    void onUpdateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, long j, java.util.function.IntConsumer intConsumer);

    static int getMaxCustomInitializationStatus() {
        return 2;
    }

    static java.util.function.IntConsumer createInitializationStatusConsumer(final android.os.IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback == null) {
            return null;
        }
        return new java.util.function.IntConsumer() { // from class: android.service.voice.SandboxedDetectionInitializer$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                android.service.voice.SandboxedDetectionInitializer.lambda$createInitializationStatusConsumer$0(android.os.IRemoteCallback.this, i);
            }
        };
    }

    static /* synthetic */ void lambda$createInitializationStatusConsumer$0(android.os.IRemoteCallback iRemoteCallback, int i) {
        if (i > getMaxCustomInitializationStatus()) {
            throw new java.lang.IllegalArgumentException("The initialization status is invalid for " + i);
        }
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt("initialization_status", i);
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
