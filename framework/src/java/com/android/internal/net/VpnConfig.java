package com.android.internal.net;

/* loaded from: classes4.dex */
public class VpnConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.net.VpnConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.net.VpnConfig>() { // from class: com.android.internal.net.VpnConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.VpnConfig createFromParcel(android.os.Parcel parcel) {
            com.android.internal.net.VpnConfig vpnConfig = new com.android.internal.net.VpnConfig();
            vpnConfig.user = parcel.readString();
            vpnConfig.interfaze = parcel.readString();
            vpnConfig.session = parcel.readString();
            vpnConfig.mtu = parcel.readInt();
            parcel.readTypedList(vpnConfig.addresses, android.net.LinkAddress.CREATOR);
            parcel.readTypedList(vpnConfig.routes, android.net.RouteInfo.CREATOR);
            vpnConfig.dnsServers = parcel.createStringArrayList();
            vpnConfig.searchDomains = parcel.createStringArrayList();
            vpnConfig.allowedApplications = parcel.createStringArrayList();
            vpnConfig.disallowedApplications = parcel.createStringArrayList();
            vpnConfig.configureIntent = (android.app.PendingIntent) parcel.readParcelable(null, android.app.PendingIntent.class);
            vpnConfig.startTime = parcel.readLong();
            vpnConfig.legacy = parcel.readInt() != 0;
            vpnConfig.blocking = parcel.readInt() != 0;
            vpnConfig.allowBypass = parcel.readInt() != 0;
            vpnConfig.allowIPv4 = parcel.readInt() != 0;
            vpnConfig.allowIPv6 = parcel.readInt() != 0;
            vpnConfig.isMetered = parcel.readInt() != 0;
            vpnConfig.requiresInternetValidation = parcel.readInt() != 0;
            vpnConfig.excludeLocalRoutes = parcel.readInt() != 0;
            vpnConfig.underlyingNetworks = (android.net.Network[]) parcel.createTypedArray(android.net.Network.CREATOR);
            vpnConfig.proxyInfo = (android.net.ProxyInfo) parcel.readParcelable(null, android.net.ProxyInfo.class);
            return vpnConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.VpnConfig[] newArray(int i) {
            return new com.android.internal.net.VpnConfig[i];
        }
    };
    public static final java.lang.String DIALOGS_PACKAGE = "com.android.vpndialogs";
    public static final java.lang.String LEGACY_VPN = "[Legacy VPN]";
    public static final java.lang.String SERVICE_INTERFACE = "android.net.VpnService";
    public java.util.List<android.net.LinkAddress> addresses;
    public boolean allowBypass;
    public boolean allowIPv4;
    public boolean allowIPv6;
    public java.util.List<java.lang.String> allowedApplications;
    public boolean blocking;
    public android.app.PendingIntent configureIntent;
    public java.util.List<java.lang.String> disallowedApplications;
    public java.util.List<java.lang.String> dnsServers;
    public boolean excludeLocalRoutes;
    public java.lang.String interfaze;
    public boolean isMetered;
    public boolean legacy;
    public int mtu;
    public android.net.ProxyInfo proxyInfo;
    public boolean requiresInternetValidation;
    public java.util.List<android.net.RouteInfo> routes;
    public java.util.List<java.lang.String> searchDomains;
    public java.lang.String session;
    public long startTime;
    public android.net.Network[] underlyingNetworks;
    public java.lang.String user;

    public static android.content.Intent getIntentForConfirmation() {
        android.content.Intent intent = new android.content.Intent();
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(android.content.res.Resources.getSystem().getString(com.android.internal.R.string.config_customVpnConfirmDialogComponent));
        intent.setClassName(unflattenFromString.getPackageName(), unflattenFromString.getClassName());
        return intent;
    }

    public static android.app.PendingIntent getIntentForStatusPanel(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent();
        intent.setClassName(DIALOGS_PACKAGE, "com.android.vpndialogs.ManageDialog");
        intent.addFlags(1350565888);
        return android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT);
    }

    public static java.lang.CharSequence getVpnLabel(android.content.Context context, java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.Intent intent = new android.content.Intent("android.net.VpnService");
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
        if (queryIntentServices != null && queryIntentServices.size() == 1) {
            return queryIntentServices.get(0).loadLabel(packageManager);
        }
        return packageManager.getApplicationInfo(str, 0).loadLabel(packageManager);
    }

    public VpnConfig() {
        this.mtu = -1;
        this.addresses = new java.util.ArrayList();
        this.routes = new java.util.ArrayList();
        this.startTime = -1L;
        this.isMetered = true;
        this.requiresInternetValidation = false;
        this.excludeLocalRoutes = false;
    }

    public VpnConfig(com.android.internal.net.VpnConfig vpnConfig) {
        this.mtu = -1;
        this.addresses = new java.util.ArrayList();
        this.routes = new java.util.ArrayList();
        this.startTime = -1L;
        this.isMetered = true;
        this.requiresInternetValidation = false;
        this.excludeLocalRoutes = false;
        this.user = vpnConfig.user;
        this.interfaze = vpnConfig.interfaze;
        this.session = vpnConfig.session;
        this.mtu = vpnConfig.mtu;
        this.addresses = copyOf(vpnConfig.addresses);
        this.routes = copyOf(vpnConfig.routes);
        this.dnsServers = copyOf(vpnConfig.dnsServers);
        this.searchDomains = copyOf(vpnConfig.searchDomains);
        this.allowedApplications = copyOf(vpnConfig.allowedApplications);
        this.disallowedApplications = copyOf(vpnConfig.disallowedApplications);
        this.configureIntent = vpnConfig.configureIntent;
        this.startTime = vpnConfig.startTime;
        this.legacy = vpnConfig.legacy;
        this.blocking = vpnConfig.blocking;
        this.allowBypass = vpnConfig.allowBypass;
        this.allowIPv4 = vpnConfig.allowIPv4;
        this.allowIPv6 = vpnConfig.allowIPv6;
        this.isMetered = vpnConfig.isMetered;
        this.requiresInternetValidation = vpnConfig.requiresInternetValidation;
        this.excludeLocalRoutes = vpnConfig.excludeLocalRoutes;
        this.underlyingNetworks = vpnConfig.underlyingNetworks != null ? (android.net.Network[]) java.util.Arrays.copyOf(vpnConfig.underlyingNetworks, vpnConfig.underlyingNetworks.length) : null;
        this.proxyInfo = vpnConfig.proxyInfo;
    }

    private static <T> java.util.List<T> copyOf(java.util.List<T> list) {
        if (list != null) {
            return new java.util.ArrayList(list);
        }
        return null;
    }

    public void addLegacyRoutes(java.lang.String str) {
        if (str.trim().equals("")) {
            return;
        }
        for (java.lang.String str2 : str.trim().split(" ")) {
            this.routes.add(new android.net.RouteInfo(new android.net.IpPrefix(str2), null, null, 1));
        }
    }

    public void addLegacyAddresses(java.lang.String str) {
        if (str.trim().equals("")) {
            return;
        }
        for (java.lang.String str2 : str.trim().split(" ")) {
            this.addresses.add(new android.net.LinkAddress(str2));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.user);
        parcel.writeString(this.interfaze);
        parcel.writeString(this.session);
        parcel.writeInt(this.mtu);
        parcel.writeTypedList(this.addresses);
        parcel.writeTypedList(this.routes);
        parcel.writeStringList(this.dnsServers);
        parcel.writeStringList(this.searchDomains);
        parcel.writeStringList(this.allowedApplications);
        parcel.writeStringList(this.disallowedApplications);
        parcel.writeParcelable(this.configureIntent, i);
        parcel.writeLong(this.startTime);
        parcel.writeInt(this.legacy ? 1 : 0);
        parcel.writeInt(this.blocking ? 1 : 0);
        parcel.writeInt(this.allowBypass ? 1 : 0);
        parcel.writeInt(this.allowIPv4 ? 1 : 0);
        parcel.writeInt(this.allowIPv6 ? 1 : 0);
        parcel.writeInt(this.isMetered ? 1 : 0);
        parcel.writeInt(this.requiresInternetValidation ? 1 : 0);
        parcel.writeInt(this.excludeLocalRoutes ? 1 : 0);
        parcel.writeTypedArray(this.underlyingNetworks, i);
        parcel.writeParcelable(this.proxyInfo, i);
    }

    public java.lang.String toString() {
        return "VpnConfig{ user=" + this.user + ", interface=" + this.interfaze + ", session=" + this.session + ", mtu=" + this.mtu + ", addresses=" + toString(this.addresses) + ", routes=" + toString(this.routes) + ", dns=" + toString(this.dnsServers) + ", searchDomains=" + toString(this.searchDomains) + ", allowedApps=" + toString(this.allowedApplications) + ", disallowedApps=" + toString(this.disallowedApplications) + ", configureIntent=" + this.configureIntent + ", startTime=" + this.startTime + ", legacy=" + this.legacy + ", blocking=" + this.blocking + ", allowBypass=" + this.allowBypass + ", allowIPv4=" + this.allowIPv4 + ", allowIPv6=" + this.allowIPv6 + ", isMetered=" + this.isMetered + ", requiresInternetValidation=" + this.requiresInternetValidation + ", excludeLocalRoutes=" + this.excludeLocalRoutes + ", underlyingNetworks=" + java.util.Arrays.toString(this.underlyingNetworks) + ", proxyInfo=" + this.proxyInfo + "}";
    }

    static <T> java.lang.String toString(java.util.List<T> list) {
        if (list == null) {
            return "null";
        }
        return java.util.Arrays.toString(list.toArray());
    }
}
