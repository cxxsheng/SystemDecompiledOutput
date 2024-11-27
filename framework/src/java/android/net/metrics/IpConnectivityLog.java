package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class IpConnectivityLog {
    private static final boolean DBG = false;
    public static final java.lang.String SERVICE_NAME = "connmetrics";
    private static final java.lang.String TAG = android.net.metrics.IpConnectivityLog.class.getSimpleName();
    private android.net.IIpConnectivityMetrics mService;

    public interface Event extends android.os.Parcelable {
    }

    @android.annotation.SystemApi
    public IpConnectivityLog() {
    }

    public IpConnectivityLog(android.net.IIpConnectivityMetrics iIpConnectivityMetrics) {
        this.mService = iIpConnectivityMetrics;
    }

    private boolean checkLoggerService() {
        if (this.mService != null) {
            return true;
        }
        android.net.IIpConnectivityMetrics asInterface = android.net.IIpConnectivityMetrics.Stub.asInterface(android.os.ServiceManager.getService(SERVICE_NAME));
        if (asInterface == null) {
            return false;
        }
        this.mService = asInterface;
        return true;
    }

    public boolean log(android.net.ConnectivityMetricsEvent connectivityMetricsEvent) {
        if (!checkLoggerService()) {
            return false;
        }
        if (connectivityMetricsEvent.timestamp == 0) {
            connectivityMetricsEvent.timestamp = java.lang.System.currentTimeMillis();
        }
        try {
            return this.mService.logEvent(connectivityMetricsEvent) >= 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error logging event", e);
            return false;
        }
    }

    public boolean log(long j, android.net.metrics.IpConnectivityLog.Event event) {
        android.net.ConnectivityMetricsEvent makeEv = makeEv(event);
        makeEv.timestamp = j;
        return log(makeEv);
    }

    public boolean log(java.lang.String str, android.net.metrics.IpConnectivityLog.Event event) {
        android.net.ConnectivityMetricsEvent makeEv = makeEv(event);
        makeEv.ifname = str;
        return log(makeEv);
    }

    public boolean log(android.net.Network network, int[] iArr, android.net.metrics.IpConnectivityLog.Event event) {
        return log(network.getNetId(), iArr, event);
    }

    public boolean log(int i, int[] iArr, android.net.metrics.IpConnectivityLog.Event event) {
        android.net.ConnectivityMetricsEvent makeEv = makeEv(event);
        makeEv.netId = i;
        makeEv.transports = com.android.internal.util.BitUtils.packBits(iArr);
        return log(makeEv);
    }

    public boolean log(android.net.metrics.IpConnectivityLog.Event event) {
        return log(makeEv(event));
    }

    public boolean logDefaultNetworkValidity(boolean z) {
        if (!checkLoggerService()) {
            return false;
        }
        try {
            this.mService.logDefaultNetworkValidity(z);
            return true;
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    public boolean logDefaultNetworkEvent(android.net.Network network, int i, boolean z, android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities, android.net.Network network2, int i2, android.net.LinkProperties linkProperties2, android.net.NetworkCapabilities networkCapabilities2) {
        if (!checkLoggerService()) {
            return false;
        }
        try {
            this.mService.logDefaultNetworkEvent(network, i, z, linkProperties, networkCapabilities, network2, i2, linkProperties2, networkCapabilities2);
            return true;
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    private static android.net.ConnectivityMetricsEvent makeEv(android.net.metrics.IpConnectivityLog.Event event) {
        android.net.ConnectivityMetricsEvent connectivityMetricsEvent = new android.net.ConnectivityMetricsEvent();
        connectivityMetricsEvent.data = event;
        return connectivityMetricsEvent;
    }
}
