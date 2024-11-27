package android.net;

@android.annotation.Hide
/* loaded from: classes.dex */
public class NetworkMonitorManager {

    @android.annotation.NonNull
    private final android.net.INetworkMonitor mNetworkMonitor;

    @android.annotation.NonNull
    private final java.lang.String mTag;

    public NetworkMonitorManager(@android.annotation.NonNull android.net.INetworkMonitor iNetworkMonitor, @android.annotation.NonNull java.lang.String str) {
        this.mNetworkMonitor = iNetworkMonitor;
        this.mTag = str;
    }

    public NetworkMonitorManager(@android.annotation.NonNull android.net.INetworkMonitor iNetworkMonitor) {
        this(iNetworkMonitor, android.net.NetworkMonitorManager.class.getSimpleName());
    }

    private void log(java.lang.String str, java.lang.Throwable th) {
        android.util.Log.e(this.mTag, str, th);
    }

    public boolean start() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.start();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in start", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean launchCaptivePortalApp() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.launchCaptivePortalApp();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in launchCaptivePortalApp", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyCaptivePortalAppFinished(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyCaptivePortalAppFinished(i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyCaptivePortalAppFinished", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setAcceptPartialConnectivity() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.setAcceptPartialConnectivity();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in setAcceptPartialConnectivity", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean forceReevaluation(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.forceReevaluation(i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in forceReevaluation", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyPrivateDnsChanged(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyPrivateDnsChanged(privateDnsConfigParcel);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyPrivateDnsChanged", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyDnsResponse(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyDnsResponse(i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyDnsResponse", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @java.lang.Deprecated
    public boolean notifyNetworkConnected(android.net.LinkProperties linkProperties, android.net.NetworkCapabilities networkCapabilities) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyNetworkConnected(linkProperties, networkCapabilities);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyNetworkConnected", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyNetworkConnected(android.net.networkstack.aidl.NetworkMonitorParameters networkMonitorParameters) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyNetworkConnectedParcel(networkMonitorParameters);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyNetworkConnected", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyNetworkDisconnected() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyNetworkDisconnected();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyNetworkDisconnected", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyLinkPropertiesChanged(android.net.LinkProperties linkProperties) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyLinkPropertiesChanged(linkProperties);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyLinkPropertiesChanged", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyNetworkCapabilitiesChanged(android.net.NetworkCapabilities networkCapabilities) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkMonitor.notifyNetworkCapabilitiesChanged(networkCapabilities);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error in notifyNetworkCapabilitiesChanged", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
