package android.net.ip;

@android.annotation.Hide
/* loaded from: classes.dex */
public class IpClientManager {

    @android.annotation.NonNull
    private final android.net.ip.IIpClient mIpClient;

    @android.annotation.NonNull
    private final java.lang.String mTag;

    public IpClientManager(@android.annotation.NonNull android.net.ip.IIpClient iIpClient, @android.annotation.NonNull java.lang.String str) {
        this.mIpClient = iIpClient;
        this.mTag = str;
    }

    public IpClientManager(@android.annotation.NonNull android.net.ip.IIpClient iIpClient) {
        this(iIpClient, android.net.ip.IpClientManager.class.getSimpleName());
    }

    private void log(java.lang.String str, java.lang.Throwable th) {
        android.util.Log.e(this.mTag, str, th);
    }

    public boolean completedPreDhcpAction() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.completedPreDhcpAction();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error completing PreDhcpAction", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean confirmConfiguration() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.confirmConfiguration();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error confirming IpClient configuration", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean readPacketFilterComplete(byte[] bArr) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.readPacketFilterComplete(bArr);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error notifying IpClient of packet filter read", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean shutdown() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.shutdown();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error shutting down IpClient", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean startProvisioning(android.net.shared.ProvisioningConfiguration provisioningConfiguration) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.startProvisioning(provisioningConfiguration.toStableParcelable());
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error starting IpClient provisioning", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean stop() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.stop();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error stopping IpClient", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setTcpBufferSizes(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.setTcpBufferSizes(str);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error setting IpClient TCP buffer sizes", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setHttpProxy(android.net.ProxyInfo proxyInfo) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.setHttpProxy(proxyInfo);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error setting IpClient proxy", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setMulticastFilter(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.setMulticastFilter(z);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error setting multicast filter", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean addKeepalivePacketFilter(int i, android.net.TcpKeepalivePacketData tcpKeepalivePacketData) {
        return addKeepalivePacketFilter(i, android.net.util.KeepalivePacketDataUtil.toStableParcelable(tcpKeepalivePacketData));
    }

    @java.lang.Deprecated
    public boolean addKeepalivePacketFilter(int i, android.net.TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.addKeepalivePacketFilter(i, tcpKeepalivePacketDataParcelable);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error adding Keepalive Packet Filter ", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean addKeepalivePacketFilter(int i, android.net.NattKeepalivePacketData nattKeepalivePacketData) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.addNattKeepalivePacketFilter(i, android.net.util.KeepalivePacketDataUtil.toStableParcelable(nattKeepalivePacketData));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error adding NAT-T Keepalive Packet Filter ", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean removeKeepalivePacketFilter(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.removeKeepalivePacketFilter(i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error removing Keepalive Packet Filter ", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setL2KeyAndGroupHint(java.lang.String str, java.lang.String str2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.setL2KeyAndGroupHint(str, str2);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Failed setL2KeyAndGroupHint", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean notifyPreconnectionComplete(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.notifyPreconnectionComplete(z);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error notifying IpClient Preconnection completed", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean updateLayer2Information(android.net.shared.Layer2Information layer2Information) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.updateLayer2Information(layer2Information.toStableParcelable());
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error updating layer2 information", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean updateApfCapabilities(android.net.apf.ApfCapabilities apfCapabilities) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mIpClient.updateApfCapabilities(apfCapabilities);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (android.os.RemoteException e) {
                log("Error updating APF capabilities", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
