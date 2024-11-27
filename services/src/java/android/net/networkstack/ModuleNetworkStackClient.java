package android.net.networkstack;

/* loaded from: classes.dex */
public class ModuleNetworkStackClient extends android.net.networkstack.NetworkStackClientBase {
    private static final java.lang.String TAG = android.net.networkstack.ModuleNetworkStackClient.class.getSimpleName();
    private static android.net.networkstack.ModuleNetworkStackClient sInstance;

    private ModuleNetworkStackClient() {
    }

    @android.annotation.NonNull
    public static synchronized android.net.networkstack.ModuleNetworkStackClient getInstance(android.content.Context context) {
        android.net.networkstack.ModuleNetworkStackClient moduleNetworkStackClient;
        synchronized (android.net.networkstack.ModuleNetworkStackClient.class) {
            try {
                if (sInstance == null) {
                    sInstance = new android.net.networkstack.ModuleNetworkStackClient();
                    sInstance.startPolling();
                }
                moduleNetworkStackClient = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return moduleNetworkStackClient;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static synchronized void resetInstanceForTest() {
        synchronized (android.net.networkstack.ModuleNetworkStackClient.class) {
            sInstance = null;
        }
    }

    private void startPolling() {
        android.os.IBinder service = android.net.NetworkStack.getService();
        if (service != null) {
            onNetworkStackConnected(android.net.INetworkStackConnector.Stub.asInterface(service));
        } else {
            new java.lang.Thread(new android.net.networkstack.ModuleNetworkStackClient.PollingRunner()).start();
        }
    }

    private class PollingRunner implements java.lang.Runnable {
        private PollingRunner() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                android.os.IBinder service = android.net.NetworkStack.getService();
                if (service == null) {
                    try {
                        java.lang.Thread.sleep(200L);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.e(android.net.networkstack.ModuleNetworkStackClient.TAG, "Interrupted while waiting for NetworkStack connector", e);
                    }
                } else {
                    android.net.networkstack.ModuleNetworkStackClient.this.onNetworkStackConnected(android.net.INetworkStackConnector.Stub.asInterface(service));
                    return;
                }
            }
        }
    }
}
