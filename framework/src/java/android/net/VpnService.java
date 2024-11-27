package android.net;

/* loaded from: classes2.dex */
public class VpnService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.net.VpnService";
    public static final java.lang.String SERVICE_META_DATA_SUPPORTS_ALWAYS_ON = "android.net.VpnService.SUPPORTS_ALWAYS_ON";

    /* JADX INFO: Access modifiers changed from: private */
    public static android.net.IVpnManager getService() {
        return android.net.IVpnManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VPN_MANAGEMENT_SERVICE));
    }

    public static android.content.Intent prepare(android.content.Context context) {
        try {
            if (getService().prepareVpn(context.getPackageName(), null, context.getUserId())) {
                return null;
            }
        } catch (android.os.RemoteException e) {
        }
        return com.android.internal.net.VpnConfig.getIntentForConfirmation();
    }

    @android.annotation.SystemApi
    public static void prepareAndAuthorize(android.content.Context context) {
        android.net.IVpnManager service = getService();
        java.lang.String packageName = context.getPackageName();
        try {
            int userId = context.getUserId();
            if (!service.prepareVpn(packageName, null, userId)) {
                service.prepareVpn(null, packageName, userId);
            }
            service.setVpnPackageAuthorization(packageName, userId, 1);
        } catch (android.os.RemoteException e) {
        }
    }

    public boolean protect(int i) {
        return com.android.internal.net.NetworkUtilsInternal.protectFromVpn(i);
    }

    public boolean protect(java.net.Socket socket) {
        return protect(socket.getFileDescriptor$().getInt$());
    }

    public boolean protect(java.net.DatagramSocket datagramSocket) {
        return protect(datagramSocket.getFileDescriptor$().getInt$());
    }

    public boolean addAddress(java.net.InetAddress inetAddress, int i) {
        check(inetAddress, i);
        try {
            return getService().addVpnAddress(inetAddress.getHostAddress(), i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public boolean removeAddress(java.net.InetAddress inetAddress, int i) {
        check(inetAddress, i);
        try {
            return getService().removeVpnAddress(inetAddress.getHostAddress(), i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public boolean setUnderlyingNetworks(android.net.Network[] networkArr) {
        try {
            return getService().setUnderlyingNetworksForVpn(networkArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public final boolean isAlwaysOn() {
        try {
            return getService().isCallerCurrentAlwaysOnVpnApp();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final boolean isLockdownEnabled() {
        try {
            return getService().isCallerCurrentAlwaysOnVpnLockdownApp();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (intent == null || !"android.net.VpnService".equals(intent.getAction())) {
            return null;
        }
        return new android.net.VpnService.Callback();
    }

    public void onRevoke() {
        stopSelf();
    }

    private class Callback extends android.os.Binder {
        private Callback() {
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) {
            if (i == 16777215) {
                android.net.VpnService.this.onRevoke();
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void check(java.net.InetAddress inetAddress, int i) {
        if (inetAddress.isLoopbackAddress()) {
            throw new java.lang.IllegalArgumentException("Bad address");
        }
        if (inetAddress instanceof java.net.Inet4Address) {
            if (i < 0 || i > 32) {
                throw new java.lang.IllegalArgumentException("Bad prefixLength");
            }
        } else {
            if (inetAddress instanceof java.net.Inet6Address) {
                if (i < 0 || i > 128) {
                    throw new java.lang.IllegalArgumentException("Bad prefixLength");
                }
                return;
            }
            throw new java.lang.IllegalArgumentException("Unsupported family");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkNonPrefixBytes(java.net.InetAddress inetAddress, int i) {
        if (!new android.net.IpPrefix(inetAddress, i).getAddress().equals(inetAddress)) {
            throw new java.lang.IllegalArgumentException("Bad address");
        }
    }

    public class Builder {
        private final com.android.internal.net.VpnConfig mConfig = new com.android.internal.net.VpnConfig();
        private final java.util.List<android.net.LinkAddress> mAddresses = new java.util.ArrayList();
        private final java.util.List<android.net.RouteInfo> mRoutes = new java.util.ArrayList();

        public Builder() {
            this.mConfig.user = android.net.VpnService.this.getClass().getName();
        }

        public android.net.VpnService.Builder setSession(java.lang.String str) {
            this.mConfig.session = str;
            return this;
        }

        public android.net.VpnService.Builder setConfigureIntent(android.app.PendingIntent pendingIntent) {
            this.mConfig.configureIntent = pendingIntent;
            return this;
        }

        public android.net.VpnService.Builder setMtu(int i) {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Bad mtu");
            }
            this.mConfig.mtu = i;
            return this;
        }

        public android.net.VpnService.Builder setHttpProxy(android.net.ProxyInfo proxyInfo) {
            this.mConfig.proxyInfo = proxyInfo;
            return this;
        }

        public android.net.VpnService.Builder addAddress(java.net.InetAddress inetAddress, int i) {
            android.net.VpnService.check(inetAddress, i);
            if (inetAddress.isAnyLocalAddress()) {
                throw new java.lang.IllegalArgumentException("Bad address");
            }
            this.mAddresses.add(new android.net.LinkAddress(inetAddress, i));
            return this;
        }

        public android.net.VpnService.Builder addAddress(java.lang.String str, int i) {
            return addAddress(java.net.InetAddress.parseNumericAddress(str), i);
        }

        private android.net.VpnService.Builder addRoute(android.net.IpPrefix ipPrefix, int i) {
            android.net.VpnService.check(ipPrefix.getAddress(), ipPrefix.getPrefixLength());
            android.net.RouteInfo routeInfo = new android.net.RouteInfo(ipPrefix, null, null, i);
            int findRouteIndexByDestination = findRouteIndexByDestination(routeInfo);
            if (findRouteIndexByDestination == -1) {
                this.mRoutes.add(routeInfo);
            } else {
                this.mRoutes.set(findRouteIndexByDestination, routeInfo);
            }
            return this;
        }

        public android.net.VpnService.Builder addRoute(java.net.InetAddress inetAddress, int i) {
            android.net.VpnService.checkNonPrefixBytes(inetAddress, i);
            return addRoute(new android.net.IpPrefix(inetAddress, i), 1);
        }

        public android.net.VpnService.Builder addRoute(android.net.IpPrefix ipPrefix) {
            return addRoute(ipPrefix, 1);
        }

        public android.net.VpnService.Builder addRoute(java.lang.String str, int i) {
            return addRoute(java.net.InetAddress.parseNumericAddress(str), i);
        }

        public android.net.VpnService.Builder excludeRoute(android.net.IpPrefix ipPrefix) {
            return addRoute(ipPrefix, 9);
        }

        public android.net.VpnService.Builder addDnsServer(java.net.InetAddress inetAddress) {
            if (inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress()) {
                throw new java.lang.IllegalArgumentException("Bad address");
            }
            if (this.mConfig.dnsServers == null) {
                this.mConfig.dnsServers = new java.util.ArrayList();
            }
            this.mConfig.dnsServers.add(inetAddress.getHostAddress());
            return this;
        }

        public android.net.VpnService.Builder addDnsServer(java.lang.String str) {
            return addDnsServer(java.net.InetAddress.parseNumericAddress(str));
        }

        public android.net.VpnService.Builder addSearchDomain(java.lang.String str) {
            if (this.mConfig.searchDomains == null) {
                this.mConfig.searchDomains = new java.util.ArrayList();
            }
            this.mConfig.searchDomains.add(str);
            return this;
        }

        public android.net.VpnService.Builder allowFamily(int i) {
            if (i == android.system.OsConstants.AF_INET) {
                this.mConfig.allowIPv4 = true;
            } else if (i == android.system.OsConstants.AF_INET6) {
                this.mConfig.allowIPv6 = true;
            } else {
                throw new java.lang.IllegalArgumentException(i + " is neither " + android.system.OsConstants.AF_INET + " nor " + android.system.OsConstants.AF_INET6);
            }
            return this;
        }

        private void verifyApp(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            try {
                android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService("package")).getApplicationInfo(str, 0L, android.os.UserHandle.getCallingUserId());
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException(e);
            }
        }

        public android.net.VpnService.Builder addAllowedApplication(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            if (this.mConfig.disallowedApplications != null) {
                throw new java.lang.UnsupportedOperationException("addDisallowedApplication already called");
            }
            verifyApp(str);
            if (this.mConfig.allowedApplications == null) {
                this.mConfig.allowedApplications = new java.util.ArrayList();
            }
            this.mConfig.allowedApplications.add(str);
            return this;
        }

        public android.net.VpnService.Builder addDisallowedApplication(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            if (this.mConfig.allowedApplications != null) {
                throw new java.lang.UnsupportedOperationException("addAllowedApplication already called");
            }
            verifyApp(str);
            if (this.mConfig.disallowedApplications == null) {
                this.mConfig.disallowedApplications = new java.util.ArrayList();
            }
            this.mConfig.disallowedApplications.add(str);
            return this;
        }

        public android.net.VpnService.Builder allowBypass() {
            this.mConfig.allowBypass = true;
            return this;
        }

        public android.net.VpnService.Builder setBlocking(boolean z) {
            this.mConfig.blocking = z;
            return this;
        }

        public android.net.VpnService.Builder setUnderlyingNetworks(android.net.Network[] networkArr) {
            this.mConfig.underlyingNetworks = networkArr != null ? (android.net.Network[]) networkArr.clone() : null;
            return this;
        }

        public android.net.VpnService.Builder setMetered(boolean z) {
            this.mConfig.isMetered = z;
            return this;
        }

        public android.os.ParcelFileDescriptor establish() {
            this.mConfig.addresses = this.mAddresses;
            this.mConfig.routes = this.mRoutes;
            try {
                return android.net.VpnService.getService().establishVpn(this.mConfig);
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException(e);
            }
        }

        private int findRouteIndexByDestination(android.net.RouteInfo routeInfo) {
            for (int i = 0; i < this.mRoutes.size(); i++) {
                if (this.mRoutes.get(i).getDestination().equals(routeInfo.getDestination())) {
                    return i;
                }
            }
            return -1;
        }

        public java.util.List<android.net.RouteInfo> routes() {
            return java.util.Collections.unmodifiableList(this.mRoutes);
        }
    }
}
