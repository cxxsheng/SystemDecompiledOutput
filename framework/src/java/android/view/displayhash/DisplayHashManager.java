package android.view.displayhash;

/* loaded from: classes4.dex */
public final class DisplayHashManager {
    private static final java.lang.String TAG = "DisplayHashManager";
    private static java.util.Set<java.lang.String> sSupportedHashAlgorithms;
    private final java.lang.Object mSupportedHashingAlgorithmLock = new java.lang.Object();

    public java.util.Set<java.lang.String> getSupportedHashAlgorithms() {
        synchronized (this.mSupportedHashingAlgorithmLock) {
            if (sSupportedHashAlgorithms != null) {
                return sSupportedHashAlgorithms;
            }
            try {
                java.lang.String[] supportedDisplayHashAlgorithms = android.view.WindowManagerGlobal.getWindowManagerService().getSupportedDisplayHashAlgorithms();
                if (supportedDisplayHashAlgorithms == null) {
                    return java.util.Collections.emptySet();
                }
                sSupportedHashAlgorithms = new android.util.ArraySet(supportedDisplayHashAlgorithms);
                return sSupportedHashAlgorithms;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to send request getSupportedHashingAlgorithms", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(android.view.displayhash.DisplayHash displayHash) {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().verifyDisplayHash(displayHash);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to send request verifyImpressionToken", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDisplayHashThrottlingEnabled(boolean z) {
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().setDisplayHashThrottlingEnabled(z);
        } catch (android.os.RemoteException e) {
        }
    }
}
