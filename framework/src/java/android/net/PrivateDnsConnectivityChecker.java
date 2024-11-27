package android.net;

/* loaded from: classes2.dex */
public class PrivateDnsConnectivityChecker {
    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int PRIVATE_DNS_PORT = 853;
    private static final java.lang.String TAG = "NetworkUtils";

    private PrivateDnsConnectivityChecker() {
    }

    public static boolean canConnectToPrivateDnsServer(java.lang.String str) {
        javax.net.SocketFactory socketFactory = javax.net.ssl.SSLSocketFactory.getDefault();
        android.net.TrafficStats.setThreadStatsTagApp();
        try {
            javax.net.ssl.SSLSocket sSLSocket = (javax.net.ssl.SSLSocket) socketFactory.createSocket();
            try {
                sSLSocket.setSoTimeout(5000);
                sSLSocket.connect(new java.net.InetSocketAddress(str, 853));
                if (!sSLSocket.isConnected()) {
                    android.util.Log.w(TAG, java.lang.String.format("Connection to %s failed.", str));
                    if (sSLSocket != null) {
                        sSLSocket.close();
                    }
                    return false;
                }
                sSLSocket.startHandshake();
                android.util.Log.w(TAG, java.lang.String.format("TLS handshake to %s succeeded.", str));
                if (sSLSocket != null) {
                    sSLSocket.close();
                    return true;
                }
                return true;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, java.lang.String.format("TLS handshake to %s failed.", str), e);
            return false;
        }
    }
}
