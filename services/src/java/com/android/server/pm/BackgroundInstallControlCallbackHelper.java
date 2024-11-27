package com.android.server.pm;

/* loaded from: classes2.dex */
public class BackgroundInstallControlCallbackHelper {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FLAGGED_PACKAGE_NAME_KEY = "packageName";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FLAGGED_USER_ID_KEY = "userId";
    private static final java.lang.String TAG = "BackgroundInstallControlCallbackHelper";

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final android.os.RemoteCallbackList<android.os.IRemoteCallback> mCallbacks = new android.os.RemoteCallbackList<>();
    private final android.os.Handler mHandler;

    BackgroundInstallControlCallbackHelper() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread("BackgroundInstallControlCallbackHelperBg", 10, true);
        serviceThread.start();
        this.mHandler = new android.os.Handler(serviceThread.getLooper());
    }

    public void registerBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.register(iRemoteCallback, null);
        }
    }

    public void unregisterBackgroundInstallCallback(android.os.IRemoteCallback iRemoteCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.unregister(iRemoteCallback);
        }
    }

    public void notifyAllCallbacks(int i, java.lang.String str) {
        final android.os.Bundle bundle = new android.os.Bundle();
        bundle.putCharSequence("packageName", str);
        bundle.putInt("userId", i);
        synchronized (this.mCallbacks) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BackgroundInstallControlCallbackHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.BackgroundInstallControlCallbackHelper.this.lambda$notifyAllCallbacks$1(bundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllCallbacks$1(final android.os.Bundle bundle) {
        this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.pm.BackgroundInstallControlCallbackHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.BackgroundInstallControlCallbackHelper.lambda$notifyAllCallbacks$0(bundle, (android.os.IRemoteCallback) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyAllCallbacks$0(android.os.Bundle bundle, android.os.IRemoteCallback iRemoteCallback) {
        try {
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "error detected: " + e.getLocalizedMessage(), e);
        }
    }
}
