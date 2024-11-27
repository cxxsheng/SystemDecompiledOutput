package com.android.server.net;

/* loaded from: classes5.dex */
public class NetworkPinner extends android.net.ConnectivityManager.NetworkCallback {
    private static android.net.ConnectivityManager sCM;
    private static com.android.server.net.NetworkPinner.Callback sCallback;
    protected static android.net.Network sNetwork;
    private static final java.lang.String TAG = com.android.server.net.NetworkPinner.class.getSimpleName();
    protected static final java.lang.Object sLock = new java.lang.Object();

    private static void maybeInitConnectivityManager(android.content.Context context) {
        if (sCM == null) {
            sCM = (android.net.ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
            if (sCM == null) {
                throw new java.lang.IllegalStateException("Bad luck, ConnectivityService not started.");
            }
        }
    }

    private static class Callback extends android.net.ConnectivityManager.NetworkCallback {
        private Callback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            synchronized (com.android.server.net.NetworkPinner.sLock) {
                if (this != com.android.server.net.NetworkPinner.sCallback) {
                    return;
                }
                if (com.android.server.net.NetworkPinner.sCM.getBoundNetworkForProcess() == null && com.android.server.net.NetworkPinner.sNetwork == null) {
                    com.android.server.net.NetworkPinner.sCM.bindProcessToNetwork(network);
                    com.android.server.net.NetworkPinner.sNetwork = network;
                    android.util.Log.d(com.android.server.net.NetworkPinner.TAG, "Wifi alternate reality enabled on network " + network);
                }
                com.android.server.net.NetworkPinner.sLock.notify();
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            synchronized (com.android.server.net.NetworkPinner.sLock) {
                if (this != com.android.server.net.NetworkPinner.sCallback) {
                    return;
                }
                if (network.equals(com.android.server.net.NetworkPinner.sNetwork) && network.equals(com.android.server.net.NetworkPinner.sCM.getBoundNetworkForProcess())) {
                    com.android.server.net.NetworkPinner.unpin();
                    android.util.Log.d(com.android.server.net.NetworkPinner.TAG, "Wifi alternate reality disabled on network " + network);
                }
                com.android.server.net.NetworkPinner.sLock.notify();
            }
        }
    }

    public static void pin(android.content.Context context, android.net.NetworkRequest networkRequest) {
        synchronized (sLock) {
            if (sCallback == null) {
                maybeInitConnectivityManager(context);
                sCallback = new com.android.server.net.NetworkPinner.Callback();
                try {
                    sCM.registerNetworkCallback(networkRequest, sCallback);
                } catch (java.lang.SecurityException e) {
                    android.util.Log.d(TAG, "Failed to register network callback", e);
                    sCallback = null;
                }
            }
        }
    }

    public static void unpin() {
        synchronized (sLock) {
            if (sCallback != null) {
                try {
                    sCM.bindProcessToNetwork(null);
                    sCM.unregisterNetworkCallback(sCallback);
                } catch (java.lang.SecurityException e) {
                    android.util.Log.d(TAG, "Failed to unregister network callback", e);
                }
                sCallback = null;
                sNetwork = null;
            }
        }
    }
}
