package com.android.internal.net;

/* loaded from: classes4.dex */
public final class VpnProfile implements java.lang.Cloneable, android.os.Parcelable {
    private static final java.lang.String ENCODED_NULL_PROXY_INFO = "\u0000\u0000\u0000\u0000";
    static final java.lang.String LIST_DELIMITER = ",";
    public static final int PROXY_MANUAL = 1;
    public static final int PROXY_NONE = 0;
    private static final java.lang.String TAG = "VpnProfile";
    public static final int TYPE_IKEV2_FROM_IKE_TUN_CONN_PARAMS = 9;
    public static final int TYPE_IKEV2_IPSEC_PSK = 7;
    public static final int TYPE_IKEV2_IPSEC_RSA = 8;
    public static final int TYPE_IKEV2_IPSEC_USER_PASS = 6;
    public static final int TYPE_IPSEC_HYBRID_RSA = 5;
    public static final int TYPE_IPSEC_XAUTH_PSK = 3;
    public static final int TYPE_IPSEC_XAUTH_RSA = 4;
    public static final int TYPE_L2TP_IPSEC_PSK = 1;
    public static final int TYPE_L2TP_IPSEC_RSA = 2;
    public static final int TYPE_MAX = 9;
    public static final int TYPE_PPTP = 0;
    static final java.lang.String VALUE_DELIMITER = "\u0000";
    public boolean areAuthParamsInline;
    public final boolean automaticIpVersionSelectionEnabled;
    public final boolean automaticNattKeepaliveTimerEnabled;
    public java.lang.String dnsServers;
    public final boolean excludeLocalRoutes;
    public final android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunConnParams;
    public java.lang.String ipsecCaCert;
    public java.lang.String ipsecIdentifier;
    public java.lang.String ipsecSecret;
    public java.lang.String ipsecServerCert;
    public java.lang.String ipsecUserCert;
    public boolean isBypassable;
    public boolean isMetered;
    public final boolean isRestrictedToTestNetworks;
    public final java.lang.String key;
    public java.lang.String l2tpSecret;
    private java.util.List<java.lang.String> mAllowedAlgorithms;
    public int maxMtu;
    public boolean mppe;
    public java.lang.String name;
    public java.lang.String password;
    public android.net.ProxyInfo proxy;
    public final boolean requiresInternetValidation;
    public java.lang.String routes;
    public transient boolean saveLogin;
    public java.lang.String searchDomains;
    public java.lang.String server;
    public int type;
    public java.lang.String username;
    private static final java.lang.String DEFAULT_ENCODING = java.nio.charset.StandardCharsets.UTF_8.name();
    public static final android.os.Parcelable.Creator<com.android.internal.net.VpnProfile> CREATOR = new android.os.Parcelable.Creator<com.android.internal.net.VpnProfile>() { // from class: com.android.internal.net.VpnProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.VpnProfile createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.net.VpnProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.net.VpnProfile[] newArray(int i) {
            return new com.android.internal.net.VpnProfile[i];
        }
    };

    public VpnProfile(java.lang.String str) {
        this(str, false, false, false, null);
    }

    public VpnProfile(java.lang.String str, boolean z) {
        this(str, z, false, false, null);
    }

    public VpnProfile(java.lang.String str, boolean z, boolean z2, boolean z3, android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams) {
        this(str, z, z2, z3, ikeTunnelConnectionParams, false, false);
    }

    public VpnProfile(java.lang.String str, boolean z, boolean z2, boolean z3, android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams, boolean z4, boolean z5) {
        this.name = "";
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.proxy = null;
        this.mAllowedAlgorithms = new java.util.ArrayList();
        this.isBypassable = false;
        this.isMetered = false;
        this.maxMtu = 1360;
        this.areAuthParamsInline = false;
        this.saveLogin = false;
        this.key = str;
        this.isRestrictedToTestNetworks = z;
        this.excludeLocalRoutes = z2;
        this.requiresInternetValidation = z3;
        this.ikeTunConnParams = ikeTunnelConnectionParams;
        this.automaticNattKeepaliveTimerEnabled = z4;
        this.automaticIpVersionSelectionEnabled = z5;
    }

    public VpnProfile(android.os.Parcel parcel) {
        boolean z;
        this.name = "";
        this.type = 0;
        this.server = "";
        this.username = "";
        this.password = "";
        this.dnsServers = "";
        this.searchDomains = "";
        this.routes = "";
        this.mppe = true;
        this.l2tpSecret = "";
        this.ipsecIdentifier = "";
        this.ipsecSecret = "";
        this.ipsecUserCert = "";
        this.ipsecCaCert = "";
        this.ipsecServerCert = "";
        this.proxy = null;
        this.mAllowedAlgorithms = new java.util.ArrayList();
        this.isBypassable = false;
        this.isMetered = false;
        this.maxMtu = 1360;
        this.areAuthParamsInline = false;
        this.saveLogin = false;
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.type = parcel.readInt();
        this.server = parcel.readString();
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.dnsServers = parcel.readString();
        this.searchDomains = parcel.readString();
        this.routes = parcel.readString();
        if (parcel.readInt() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mppe = z;
        this.l2tpSecret = parcel.readString();
        this.ipsecIdentifier = parcel.readString();
        this.ipsecSecret = parcel.readString();
        this.ipsecUserCert = parcel.readString();
        this.ipsecCaCert = parcel.readString();
        this.ipsecServerCert = parcel.readString();
        this.saveLogin = parcel.readInt() != 0;
        this.proxy = (android.net.ProxyInfo) parcel.readParcelable(null, android.net.ProxyInfo.class);
        this.mAllowedAlgorithms = new java.util.ArrayList();
        parcel.readList(this.mAllowedAlgorithms, null, java.lang.String.class);
        this.isBypassable = parcel.readBoolean();
        this.isMetered = parcel.readBoolean();
        this.maxMtu = parcel.readInt();
        this.areAuthParamsInline = parcel.readBoolean();
        this.isRestrictedToTestNetworks = parcel.readBoolean();
        this.excludeLocalRoutes = parcel.readBoolean();
        this.requiresInternetValidation = parcel.readBoolean();
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readParcelable(android.os.PersistableBundle.class.getClassLoader(), android.os.PersistableBundle.class);
        this.ikeTunConnParams = persistableBundle != null ? android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.fromPersistableBundle(persistableBundle) : null;
        this.automaticNattKeepaliveTimerEnabled = parcel.readBoolean();
        this.automaticIpVersionSelectionEnabled = parcel.readBoolean();
    }

    public java.util.List<java.lang.String> getAllowedAlgorithms() {
        return java.util.Collections.unmodifiableList(this.mAllowedAlgorithms);
    }

    public void setAllowedAlgorithms(java.util.List<java.lang.String> list) {
        this.mAllowedAlgorithms = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.name);
        parcel.writeInt(this.type);
        parcel.writeString(this.server);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.dnsServers);
        parcel.writeString(this.searchDomains);
        parcel.writeString(this.routes);
        parcel.writeInt(this.mppe ? 1 : 0);
        parcel.writeString(this.l2tpSecret);
        parcel.writeString(this.ipsecIdentifier);
        parcel.writeString(this.ipsecSecret);
        parcel.writeString(this.ipsecUserCert);
        parcel.writeString(this.ipsecCaCert);
        parcel.writeString(this.ipsecServerCert);
        parcel.writeInt(this.saveLogin ? 1 : 0);
        parcel.writeParcelable(this.proxy, i);
        parcel.writeList(this.mAllowedAlgorithms);
        parcel.writeBoolean(this.isBypassable);
        parcel.writeBoolean(this.isMetered);
        parcel.writeInt(this.maxMtu);
        parcel.writeBoolean(this.areAuthParamsInline);
        parcel.writeBoolean(this.isRestrictedToTestNetworks);
        parcel.writeBoolean(this.excludeLocalRoutes);
        parcel.writeBoolean(this.requiresInternetValidation);
        parcel.writeParcelable(this.ikeTunConnParams == null ? null : android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.toPersistableBundle(this.ikeTunConnParams), i);
        parcel.writeBoolean(this.automaticNattKeepaliveTimerEnabled);
        parcel.writeBoolean(this.automaticIpVersionSelectionEnabled);
    }

    public static com.android.internal.net.VpnProfile decode(java.lang.String str, byte[] bArr) {
        boolean z;
        boolean z2;
        boolean z3;
        android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams;
        boolean z4;
        boolean z5;
        if (str == null) {
            return null;
        }
        try {
            java.lang.String[] split = new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8).split(VALUE_DELIMITER, -1);
            if (split.length >= 14 && ((split.length <= 19 || split.length >= 24) && ((split.length <= 28 || split.length >= 30) && split.length <= 30))) {
                if (split.length >= 25) {
                    z = java.lang.Boolean.parseBoolean(split[24]);
                } else {
                    z = false;
                }
                if (split.length >= 26) {
                    z2 = java.lang.Boolean.parseBoolean(split[25]);
                } else {
                    z2 = false;
                }
                if (split.length >= 27) {
                    z3 = java.lang.Boolean.parseBoolean(split[26]);
                } else {
                    z3 = false;
                }
                if (split.length >= 28 && split[27].length() != 0) {
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    byte[] hexStringToByteArray = com.android.internal.util.HexDump.hexStringToByteArray(split[27]);
                    obtain.unmarshall(hexStringToByteArray, 0, hexStringToByteArray.length);
                    obtain.setDataPosition(0);
                    ikeTunnelConnectionParams = android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.fromPersistableBundle((android.os.PersistableBundle) obtain.readValue(android.os.PersistableBundle.class.getClassLoader()));
                } else {
                    ikeTunnelConnectionParams = null;
                }
                if (split.length >= 30) {
                    z4 = java.lang.Boolean.parseBoolean(split[28]);
                    z5 = java.lang.Boolean.parseBoolean(split[29]);
                } else {
                    z4 = false;
                    z5 = false;
                }
                com.android.internal.net.VpnProfile vpnProfile = new com.android.internal.net.VpnProfile(str, z, z2, z3, ikeTunnelConnectionParams, z4, z5);
                vpnProfile.name = split[0];
                vpnProfile.type = java.lang.Integer.parseInt(split[1]);
                if (vpnProfile.type >= 0 && vpnProfile.type <= 9) {
                    vpnProfile.server = split[2];
                    vpnProfile.username = split[3];
                    vpnProfile.password = split[4];
                    vpnProfile.dnsServers = split[5];
                    vpnProfile.searchDomains = split[6];
                    vpnProfile.routes = split[7];
                    vpnProfile.mppe = java.lang.Boolean.parseBoolean(split[8]);
                    vpnProfile.l2tpSecret = split[9];
                    vpnProfile.ipsecIdentifier = split[10];
                    vpnProfile.ipsecSecret = split[11];
                    vpnProfile.ipsecUserCert = split[12];
                    vpnProfile.ipsecCaCert = split[13];
                    vpnProfile.ipsecServerCert = split.length > 14 ? split[14] : "";
                    if (split.length > 15) {
                        java.lang.String str2 = split.length > 15 ? split[15] : "";
                        java.lang.String str3 = split.length > 16 ? split[16] : "";
                        java.lang.String str4 = split.length > 17 ? split[17] : "";
                        java.lang.String str5 = split.length > 18 ? split[18] : "";
                        if (str2.isEmpty() && str3.isEmpty() && str4.isEmpty()) {
                            if (!str5.isEmpty()) {
                                vpnProfile.proxy = android.net.ProxyInfo.buildPacProxy(android.net.Uri.parse(str5));
                            }
                        }
                        vpnProfile.proxy = android.net.ProxyInfo.buildDirectProxy(str2, str3.isEmpty() ? 0 : java.lang.Integer.parseInt(str3), com.android.net.module.util.ProxyUtils.exclusionStringAsList(str4));
                    }
                    if (split.length >= 24) {
                        vpnProfile.mAllowedAlgorithms = new java.util.ArrayList();
                        java.util.Iterator it = java.util.Arrays.asList(split[19].split(",")).iterator();
                        while (it.hasNext()) {
                            vpnProfile.mAllowedAlgorithms.add(java.net.URLDecoder.decode((java.lang.String) it.next(), DEFAULT_ENCODING));
                        }
                        vpnProfile.isBypassable = java.lang.Boolean.parseBoolean(split[20]);
                        vpnProfile.isMetered = java.lang.Boolean.parseBoolean(split[21]);
                        vpnProfile.maxMtu = java.lang.Integer.parseInt(split[22]);
                        vpnProfile.areAuthParamsInline = java.lang.Boolean.parseBoolean(split[23]);
                    }
                    vpnProfile.saveLogin = (vpnProfile.username.isEmpty() && vpnProfile.password.isEmpty()) ? false : true;
                    return vpnProfile;
                }
                return null;
            }
            return null;
        } catch (java.lang.Exception e) {
            android.util.Log.d(TAG, "Got exception in decode.", e);
            return null;
        }
    }

    public byte[] encode() {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.name);
        sb.append(VALUE_DELIMITER).append(this.type);
        sb.append(VALUE_DELIMITER).append(this.server);
        sb.append(VALUE_DELIMITER).append(this.saveLogin ? this.username : "");
        sb.append(VALUE_DELIMITER).append(this.saveLogin ? this.password : "");
        sb.append(VALUE_DELIMITER).append(this.dnsServers);
        sb.append(VALUE_DELIMITER).append(this.searchDomains);
        sb.append(VALUE_DELIMITER).append(this.routes);
        sb.append(VALUE_DELIMITER).append(this.mppe);
        sb.append(VALUE_DELIMITER).append(this.l2tpSecret);
        sb.append(VALUE_DELIMITER).append(this.ipsecIdentifier);
        sb.append(VALUE_DELIMITER).append(this.ipsecSecret);
        sb.append(VALUE_DELIMITER).append(this.ipsecUserCert);
        sb.append(VALUE_DELIMITER).append(this.ipsecCaCert);
        sb.append(VALUE_DELIMITER).append(this.ipsecServerCert);
        if (this.proxy != null) {
            sb.append(VALUE_DELIMITER).append(this.proxy.getHost() != null ? this.proxy.getHost() : "");
            sb.append(VALUE_DELIMITER).append(this.proxy.getPort());
            java.lang.StringBuilder append = sb.append(VALUE_DELIMITER);
            if (com.android.net.module.util.ProxyUtils.exclusionListAsString(this.proxy.getExclusionList()) != null) {
                str = com.android.net.module.util.ProxyUtils.exclusionListAsString(this.proxy.getExclusionList());
            } else {
                str = "";
            }
            append.append(str);
            sb.append(VALUE_DELIMITER).append(this.proxy.getPacFileUrl().toString());
        } else {
            sb.append(ENCODED_NULL_PROXY_INFO);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.util.Iterator<java.lang.String> it = this.mAllowedAlgorithms.iterator();
            while (it.hasNext()) {
                arrayList.add(java.net.URLEncoder.encode(it.next(), DEFAULT_ENCODING));
            }
            sb.append(VALUE_DELIMITER).append(java.lang.String.join(",", arrayList));
            sb.append(VALUE_DELIMITER).append(this.isBypassable);
            sb.append(VALUE_DELIMITER).append(this.isMetered);
            sb.append(VALUE_DELIMITER).append(this.maxMtu);
            sb.append(VALUE_DELIMITER).append(this.areAuthParamsInline);
            sb.append(VALUE_DELIMITER).append(this.isRestrictedToTestNetworks);
            sb.append(VALUE_DELIMITER).append(this.excludeLocalRoutes);
            sb.append(VALUE_DELIMITER).append(this.requiresInternetValidation);
            if (this.ikeTunConnParams != null) {
                android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.toPersistableBundle(this.ikeTunConnParams);
                android.os.Parcel obtain = android.os.Parcel.obtain();
                obtain.writeValue(persistableBundle);
                sb.append(VALUE_DELIMITER).append(com.android.internal.util.HexDump.toHexString(obtain.marshall()));
            } else {
                sb.append(VALUE_DELIMITER).append("");
            }
            sb.append(VALUE_DELIMITER).append(this.automaticNattKeepaliveTimerEnabled);
            sb.append(VALUE_DELIMITER).append(this.automaticIpVersionSelectionEnabled);
            return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.IllegalStateException("Failed to encode algorithms.", e);
        }
    }

    public static boolean isLegacyType(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    private boolean isValidLockdownLegacyVpnProfile() {
        return isLegacyType(this.type) && isServerAddressNumeric() && hasDns() && areDnsAddressesNumeric();
    }

    private boolean isValidLockdownPlatformVpnProfile() {
        return android.net.Ikev2VpnProfile.isValidVpnProfile(this);
    }

    public boolean isValidLockdownProfile() {
        return isTypeValidForLockdown() && (isValidLockdownLegacyVpnProfile() || isValidLockdownPlatformVpnProfile());
    }

    public boolean isTypeValidForLockdown() {
        return this.type != 0;
    }

    public boolean isServerAddressNumeric() {
        try {
            java.net.InetAddress.parseNumericAddress(this.server);
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    public boolean hasDns() {
        return !android.text.TextUtils.isEmpty(this.dnsServers);
    }

    public boolean areDnsAddressesNumeric() {
        try {
            for (java.lang.String str : this.dnsServers.split(" +")) {
                java.net.InetAddress.parseNumericAddress(str);
            }
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.key, java.lang.Integer.valueOf(this.type), this.server, this.username, this.password, this.dnsServers, this.searchDomains, this.routes, java.lang.Boolean.valueOf(this.mppe), this.l2tpSecret, this.ipsecIdentifier, this.ipsecSecret, this.ipsecUserCert, this.ipsecCaCert, this.ipsecServerCert, this.proxy, this.mAllowedAlgorithms, java.lang.Boolean.valueOf(this.isBypassable), java.lang.Boolean.valueOf(this.isMetered), java.lang.Integer.valueOf(this.maxMtu), java.lang.Boolean.valueOf(this.areAuthParamsInline), java.lang.Boolean.valueOf(this.isRestrictedToTestNetworks), java.lang.Boolean.valueOf(this.excludeLocalRoutes), java.lang.Boolean.valueOf(this.requiresInternetValidation), this.ikeTunConnParams, java.lang.Boolean.valueOf(this.automaticNattKeepaliveTimerEnabled), java.lang.Boolean.valueOf(this.automaticIpVersionSelectionEnabled));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.net.VpnProfile)) {
            return false;
        }
        com.android.internal.net.VpnProfile vpnProfile = (com.android.internal.net.VpnProfile) obj;
        return java.util.Objects.equals(this.key, vpnProfile.key) && java.util.Objects.equals(this.name, vpnProfile.name) && this.type == vpnProfile.type && java.util.Objects.equals(this.server, vpnProfile.server) && java.util.Objects.equals(this.username, vpnProfile.username) && java.util.Objects.equals(this.password, vpnProfile.password) && java.util.Objects.equals(this.dnsServers, vpnProfile.dnsServers) && java.util.Objects.equals(this.searchDomains, vpnProfile.searchDomains) && java.util.Objects.equals(this.routes, vpnProfile.routes) && this.mppe == vpnProfile.mppe && java.util.Objects.equals(this.l2tpSecret, vpnProfile.l2tpSecret) && java.util.Objects.equals(this.ipsecIdentifier, vpnProfile.ipsecIdentifier) && java.util.Objects.equals(this.ipsecSecret, vpnProfile.ipsecSecret) && java.util.Objects.equals(this.ipsecUserCert, vpnProfile.ipsecUserCert) && java.util.Objects.equals(this.ipsecCaCert, vpnProfile.ipsecCaCert) && java.util.Objects.equals(this.ipsecServerCert, vpnProfile.ipsecServerCert) && java.util.Objects.equals(this.proxy, vpnProfile.proxy) && java.util.Objects.equals(this.mAllowedAlgorithms, vpnProfile.mAllowedAlgorithms) && this.isBypassable == vpnProfile.isBypassable && this.isMetered == vpnProfile.isMetered && this.maxMtu == vpnProfile.maxMtu && this.areAuthParamsInline == vpnProfile.areAuthParamsInline && this.isRestrictedToTestNetworks == vpnProfile.isRestrictedToTestNetworks && this.excludeLocalRoutes == vpnProfile.excludeLocalRoutes && this.requiresInternetValidation == vpnProfile.requiresInternetValidation && java.util.Objects.equals(this.ikeTunConnParams, vpnProfile.ikeTunConnParams) && this.automaticNattKeepaliveTimerEnabled == vpnProfile.automaticNattKeepaliveTimerEnabled && this.automaticIpVersionSelectionEnabled == vpnProfile.automaticIpVersionSelectionEnabled;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.internal.net.VpnProfile m6876clone() {
        try {
            return (com.android.internal.net.VpnProfile) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
            android.util.Log.wtf(TAG, e);
            return null;
        }
    }
}
