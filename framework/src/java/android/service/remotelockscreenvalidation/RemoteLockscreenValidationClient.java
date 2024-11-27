package android.service.remotelockscreenvalidation;

/* loaded from: classes3.dex */
public interface RemoteLockscreenValidationClient {
    void disconnect();

    boolean isServiceAvailable();

    void validateLockscreenGuess(byte[] bArr, android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback);

    static android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient create(android.content.Context context, android.content.ComponentName componentName) {
        return new android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl(context, null, componentName);
    }

    static android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient create(android.content.Context context, java.util.concurrent.Executor executor, android.content.ComponentName componentName) {
        return new android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl(context, executor, componentName);
    }
}
