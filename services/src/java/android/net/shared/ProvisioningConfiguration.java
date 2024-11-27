package android.net.shared;

/* loaded from: classes.dex */
public class ProvisioningConfiguration {
    private static final int DEFAULT_TIMEOUT_MS = 18000;
    public static final int IPV6_ADDR_GEN_MODE_EUI64 = 0;
    public static final int IPV6_ADDR_GEN_MODE_STABLE_PRIVACY = 2;
    private static final java.lang.String TAG = "ProvisioningConfiguration";
    public static final int VERSION_ADDED_PROVISIONING_ENUM = 12;
    public android.net.apf.ApfCapabilities mApfCapabilities;
    public int mCreatorUid;
    public java.util.List<android.net.networkstack.aidl.dhcp.DhcpOption> mDhcpOptions;
    public java.lang.String mDisplayName;
    public boolean mEnablePreconnection;
    public int mHostnameSetting;
    public int mIPv4ProvisioningMode;
    public int mIPv6AddrGenMode;
    public int mIPv6ProvisioningMode;
    public android.net.shared.InitialConfiguration mInitialConfig;
    public android.net.shared.Layer2Information mLayer2Info;
    public android.net.Network mNetwork;
    public int mProvisioningTimeoutMs;
    public int mRequestedPreDhcpActionMs;
    public android.net.shared.ProvisioningConfiguration.ScanResultInfo mScanResultInfo;
    public android.net.StaticIpConfiguration mStaticIpConfig;
    public boolean mUniqueEui64AddressesOnly;
    public boolean mUsingIpReachabilityMonitor;
    public boolean mUsingMultinetworkPolicyTracker;

    public static class Builder {
        protected android.net.shared.ProvisioningConfiguration mConfig = new android.net.shared.ProvisioningConfiguration();

        public android.net.shared.ProvisioningConfiguration.Builder withoutIPv4() {
            this.mConfig.mIPv4ProvisioningMode = 0;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withoutIPv6() {
            this.mConfig.mIPv6ProvisioningMode = 0;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withoutMultinetworkPolicyTracker() {
            this.mConfig.mUsingMultinetworkPolicyTracker = false;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withoutIpReachabilityMonitor() {
            this.mConfig.mUsingIpReachabilityMonitor = false;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withPreDhcpAction() {
            this.mConfig.mRequestedPreDhcpActionMs = android.net.shared.ProvisioningConfiguration.DEFAULT_TIMEOUT_MS;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withPreDhcpAction(int i) {
            this.mConfig.mRequestedPreDhcpActionMs = i;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withPreconnection() {
            this.mConfig.mEnablePreconnection = true;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withInitialConfiguration(android.net.shared.InitialConfiguration initialConfiguration) {
            this.mConfig.mInitialConfig = initialConfiguration;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withStaticConfiguration(android.net.StaticIpConfiguration staticIpConfiguration) {
            this.mConfig.mIPv4ProvisioningMode = 1;
            this.mConfig.mStaticIpConfig = staticIpConfiguration;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withApfCapabilities(android.net.apf.ApfCapabilities apfCapabilities) {
            this.mConfig.mApfCapabilities = apfCapabilities;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withProvisioningTimeoutMs(int i) {
            this.mConfig.mProvisioningTimeoutMs = i;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withRandomMacAddress() {
            this.mConfig.mIPv6AddrGenMode = 0;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withStableMacAddress() {
            this.mConfig.mIPv6AddrGenMode = 2;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withNetwork(android.net.Network network) {
            this.mConfig.mNetwork = network;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withDisplayName(java.lang.String str) {
            this.mConfig.mDisplayName = str;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withCreatorUid(int i) {
            this.mConfig.mCreatorUid = i;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withScanResultInfo(android.net.shared.ProvisioningConfiguration.ScanResultInfo scanResultInfo) {
            this.mConfig.mScanResultInfo = scanResultInfo;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withLayer2Information(android.net.shared.Layer2Information layer2Information) {
            this.mConfig.mLayer2Info = layer2Information;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withDhcpOptions(@android.annotation.Nullable java.util.List<android.net.networkstack.aidl.dhcp.DhcpOption> list) {
            this.mConfig.mDhcpOptions = list;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withIpv6LinkLocalOnly() {
            this.mConfig.mIPv6ProvisioningMode = 2;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withUniqueEui64AddressesOnly() {
            this.mConfig.mUniqueEui64AddressesOnly = true;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration.Builder withHostnameSetting(int i) {
            this.mConfig.mHostnameSetting = i;
            return this;
        }

        public android.net.shared.ProvisioningConfiguration build() {
            if (this.mConfig.mIPv6ProvisioningMode == 2 && this.mConfig.mIPv4ProvisioningMode != 0) {
                throw new java.lang.IllegalArgumentException("IPv4 must be disabled in IPv6 link-localonly mode.");
            }
            return new android.net.shared.ProvisioningConfiguration(this.mConfig);
        }
    }

    public static class ScanResultInfo {

        @android.annotation.NonNull
        private final java.lang.String mBssid;

        @android.annotation.NonNull
        private final java.util.List<android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement> mInformationElements;

        @android.annotation.NonNull
        private final java.lang.String mSsid;

        public static class InformationElement {
            private final int mId;

            @android.annotation.NonNull
            private final byte[] mPayload;

            public InformationElement(int i, @android.annotation.NonNull java.nio.ByteBuffer byteBuffer) {
                this.mId = i;
                this.mPayload = android.net.shared.ProvisioningConfiguration.ScanResultInfo.convertToByteArray(byteBuffer.asReadOnlyBuffer());
            }

            public int getId() {
                return this.mId;
            }

            @android.annotation.NonNull
            public java.nio.ByteBuffer getPayload() {
                return java.nio.ByteBuffer.wrap(this.mPayload).asReadOnlyBuffer();
            }

            public boolean equals(java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement)) {
                    return false;
                }
                android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement informationElement = (android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement) obj;
                return this.mId == informationElement.mId && java.util.Arrays.equals(this.mPayload, informationElement.mPayload);
            }

            public int hashCode() {
                return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mPayload)));
            }

            public java.lang.String toString() {
                return "ID: " + this.mId + ", " + java.util.Arrays.toString(this.mPayload);
            }

            public android.net.InformationElementParcelable toStableParcelable() {
                android.net.InformationElementParcelable informationElementParcelable = new android.net.InformationElementParcelable();
                informationElementParcelable.id = this.mId;
                informationElementParcelable.payload = this.mPayload != null ? (byte[]) this.mPayload.clone() : null;
                return informationElementParcelable;
            }

            @android.annotation.Nullable
            public static android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement fromStableParcelable(android.net.InformationElementParcelable informationElementParcelable) {
                if (informationElementParcelable == null) {
                    return null;
                }
                return new android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement(informationElementParcelable.id, java.nio.ByteBuffer.wrap((byte[]) informationElementParcelable.payload.clone()).asReadOnlyBuffer());
            }
        }

        public ScanResultInfo(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement> list) {
            java.util.Objects.requireNonNull(str, "ssid must not be null.");
            java.util.Objects.requireNonNull(str2, "bssid must not be null.");
            this.mSsid = str;
            this.mBssid = str2;
            this.mInformationElements = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        }

        @android.annotation.NonNull
        public java.lang.String getSsid() {
            return this.mSsid;
        }

        @android.annotation.NonNull
        public java.lang.String getBssid() {
            return this.mBssid;
        }

        @android.annotation.NonNull
        public java.util.List<android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement> getInformationElements() {
            return this.mInformationElements;
        }

        public java.lang.String toString() {
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
            stringBuffer.append("SSID: ");
            stringBuffer.append(this.mSsid);
            stringBuffer.append(", BSSID: ");
            stringBuffer.append(this.mBssid);
            stringBuffer.append(", Information Elements: {");
            for (android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement informationElement : this.mInformationElements) {
                stringBuffer.append("[");
                stringBuffer.append(informationElement.toString());
                stringBuffer.append("]");
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.net.shared.ProvisioningConfiguration.ScanResultInfo)) {
                return false;
            }
            android.net.shared.ProvisioningConfiguration.ScanResultInfo scanResultInfo = (android.net.shared.ProvisioningConfiguration.ScanResultInfo) obj;
            return java.util.Objects.equals(this.mSsid, scanResultInfo.mSsid) && java.util.Objects.equals(this.mBssid, scanResultInfo.mBssid) && this.mInformationElements.equals(scanResultInfo.mInformationElements);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mSsid, this.mBssid, this.mInformationElements);
        }

        public android.net.ScanResultInfoParcelable toStableParcelable() {
            android.net.ScanResultInfoParcelable scanResultInfoParcelable = new android.net.ScanResultInfoParcelable();
            scanResultInfoParcelable.ssid = this.mSsid;
            scanResultInfoParcelable.bssid = this.mBssid;
            scanResultInfoParcelable.informationElements = (android.net.InformationElementParcelable[]) android.net.shared.ParcelableUtil.toParcelableArray(this.mInformationElements, new java.util.function.Function() { // from class: android.net.shared.ProvisioningConfiguration$ScanResultInfo$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement) obj).toStableParcelable();
                }
            }, android.net.InformationElementParcelable.class);
            return scanResultInfoParcelable;
        }

        public static android.net.shared.ProvisioningConfiguration.ScanResultInfo fromStableParcelable(android.net.ScanResultInfoParcelable scanResultInfoParcelable) {
            if (scanResultInfoParcelable == null) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll(android.net.shared.ParcelableUtil.fromParcelableArray(scanResultInfoParcelable.informationElements, new java.util.function.Function() { // from class: android.net.shared.ProvisioningConfiguration$ScanResultInfo$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.net.shared.ProvisioningConfiguration.ScanResultInfo.InformationElement.fromStableParcelable((android.net.InformationElementParcelable) obj);
                }
            }));
            return new android.net.shared.ProvisioningConfiguration.ScanResultInfo(scanResultInfoParcelable.ssid, scanResultInfoParcelable.bssid, arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static byte[] convertToByteArray(@android.annotation.NonNull java.nio.ByteBuffer byteBuffer) {
            byte[] bArr = new byte[byteBuffer.limit()];
            java.nio.ByteBuffer asReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
            try {
                try {
                    asReadOnlyBuffer.position(0);
                    asReadOnlyBuffer.get(bArr);
                    return bArr;
                } catch (java.nio.BufferUnderflowException e) {
                    android.util.Log.wtf(android.net.shared.ProvisioningConfiguration.TAG, "Buffer under flow exception should never happen.");
                    return bArr;
                }
            } catch (java.lang.Throwable th) {
                return bArr;
            }
        }
    }

    public ProvisioningConfiguration() {
        this.mUniqueEui64AddressesOnly = false;
        this.mEnablePreconnection = false;
        this.mUsingMultinetworkPolicyTracker = true;
        this.mUsingIpReachabilityMonitor = true;
        this.mProvisioningTimeoutMs = DEFAULT_TIMEOUT_MS;
        this.mIPv6AddrGenMode = 2;
        this.mNetwork = null;
        this.mDisplayName = null;
        this.mIPv4ProvisioningMode = 2;
        this.mIPv6ProvisioningMode = 1;
        this.mHostnameSetting = 0;
    }

    public ProvisioningConfiguration(android.net.shared.ProvisioningConfiguration provisioningConfiguration) {
        this.mUniqueEui64AddressesOnly = false;
        this.mEnablePreconnection = false;
        this.mUsingMultinetworkPolicyTracker = true;
        this.mUsingIpReachabilityMonitor = true;
        this.mProvisioningTimeoutMs = DEFAULT_TIMEOUT_MS;
        this.mIPv6AddrGenMode = 2;
        this.mNetwork = null;
        this.mDisplayName = null;
        this.mIPv4ProvisioningMode = 2;
        this.mIPv6ProvisioningMode = 1;
        this.mHostnameSetting = 0;
        this.mUniqueEui64AddressesOnly = provisioningConfiguration.mUniqueEui64AddressesOnly;
        this.mEnablePreconnection = provisioningConfiguration.mEnablePreconnection;
        this.mUsingMultinetworkPolicyTracker = provisioningConfiguration.mUsingMultinetworkPolicyTracker;
        this.mUsingIpReachabilityMonitor = provisioningConfiguration.mUsingIpReachabilityMonitor;
        this.mRequestedPreDhcpActionMs = provisioningConfiguration.mRequestedPreDhcpActionMs;
        this.mInitialConfig = android.net.shared.InitialConfiguration.copy(provisioningConfiguration.mInitialConfig);
        this.mStaticIpConfig = provisioningConfiguration.mStaticIpConfig != null ? new android.net.StaticIpConfiguration(provisioningConfiguration.mStaticIpConfig) : null;
        this.mApfCapabilities = provisioningConfiguration.mApfCapabilities;
        this.mProvisioningTimeoutMs = provisioningConfiguration.mProvisioningTimeoutMs;
        this.mIPv6AddrGenMode = provisioningConfiguration.mIPv6AddrGenMode;
        this.mNetwork = provisioningConfiguration.mNetwork;
        this.mDisplayName = provisioningConfiguration.mDisplayName;
        this.mCreatorUid = provisioningConfiguration.mCreatorUid;
        this.mScanResultInfo = provisioningConfiguration.mScanResultInfo;
        this.mLayer2Info = provisioningConfiguration.mLayer2Info;
        this.mDhcpOptions = provisioningConfiguration.mDhcpOptions;
        this.mIPv4ProvisioningMode = provisioningConfiguration.mIPv4ProvisioningMode;
        this.mIPv6ProvisioningMode = provisioningConfiguration.mIPv6ProvisioningMode;
        this.mHostnameSetting = provisioningConfiguration.mHostnameSetting;
    }

    public android.net.ProvisioningConfigurationParcelable toStableParcelable() {
        android.net.StaticIpConfiguration staticIpConfiguration;
        android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable = new android.net.ProvisioningConfigurationParcelable();
        provisioningConfigurationParcelable.enableIPv4 = this.mIPv4ProvisioningMode != 0;
        provisioningConfigurationParcelable.ipv4ProvisioningMode = this.mIPv4ProvisioningMode;
        provisioningConfigurationParcelable.enableIPv6 = this.mIPv6ProvisioningMode != 0;
        provisioningConfigurationParcelable.ipv6ProvisioningMode = this.mIPv6ProvisioningMode;
        provisioningConfigurationParcelable.uniqueEui64AddressesOnly = this.mUniqueEui64AddressesOnly;
        provisioningConfigurationParcelable.enablePreconnection = this.mEnablePreconnection;
        provisioningConfigurationParcelable.usingMultinetworkPolicyTracker = this.mUsingMultinetworkPolicyTracker;
        provisioningConfigurationParcelable.usingIpReachabilityMonitor = this.mUsingIpReachabilityMonitor;
        provisioningConfigurationParcelable.requestedPreDhcpActionMs = this.mRequestedPreDhcpActionMs;
        provisioningConfigurationParcelable.initialConfig = this.mInitialConfig == null ? null : this.mInitialConfig.toStableParcelable();
        if (this.mStaticIpConfig == null) {
            staticIpConfiguration = null;
        } else {
            staticIpConfiguration = new android.net.StaticIpConfiguration(this.mStaticIpConfig);
        }
        provisioningConfigurationParcelable.staticIpConfig = staticIpConfiguration;
        provisioningConfigurationParcelable.apfCapabilities = this.mApfCapabilities;
        provisioningConfigurationParcelable.provisioningTimeoutMs = this.mProvisioningTimeoutMs;
        provisioningConfigurationParcelable.ipv6AddrGenMode = this.mIPv6AddrGenMode;
        provisioningConfigurationParcelable.network = this.mNetwork;
        provisioningConfigurationParcelable.displayName = this.mDisplayName;
        provisioningConfigurationParcelable.creatorUid = this.mCreatorUid;
        provisioningConfigurationParcelable.scanResultInfo = this.mScanResultInfo == null ? null : this.mScanResultInfo.toStableParcelable();
        provisioningConfigurationParcelable.layer2Info = this.mLayer2Info == null ? null : this.mLayer2Info.toStableParcelable();
        provisioningConfigurationParcelable.options = this.mDhcpOptions != null ? new java.util.ArrayList(this.mDhcpOptions) : null;
        provisioningConfigurationParcelable.hostnameSetting = this.mHostnameSetting;
        return provisioningConfigurationParcelable;
    }

    public static android.net.shared.ProvisioningConfiguration fromStableParcelable(@android.annotation.Nullable android.net.ProvisioningConfigurationParcelable provisioningConfigurationParcelable, int i) {
        android.net.StaticIpConfiguration staticIpConfiguration;
        if (provisioningConfigurationParcelable == null) {
            return null;
        }
        android.net.shared.ProvisioningConfiguration provisioningConfiguration = new android.net.shared.ProvisioningConfiguration();
        provisioningConfiguration.mUniqueEui64AddressesOnly = provisioningConfigurationParcelable.uniqueEui64AddressesOnly;
        provisioningConfiguration.mEnablePreconnection = provisioningConfigurationParcelable.enablePreconnection;
        provisioningConfiguration.mUsingMultinetworkPolicyTracker = provisioningConfigurationParcelable.usingMultinetworkPolicyTracker;
        provisioningConfiguration.mUsingIpReachabilityMonitor = provisioningConfigurationParcelable.usingIpReachabilityMonitor;
        provisioningConfiguration.mRequestedPreDhcpActionMs = provisioningConfigurationParcelable.requestedPreDhcpActionMs;
        provisioningConfiguration.mInitialConfig = android.net.shared.InitialConfiguration.fromStableParcelable(provisioningConfigurationParcelable.initialConfig);
        if (provisioningConfigurationParcelable.staticIpConfig == null) {
            staticIpConfiguration = null;
        } else {
            staticIpConfiguration = new android.net.StaticIpConfiguration(provisioningConfigurationParcelable.staticIpConfig);
        }
        provisioningConfiguration.mStaticIpConfig = staticIpConfiguration;
        provisioningConfiguration.mApfCapabilities = provisioningConfigurationParcelable.apfCapabilities;
        provisioningConfiguration.mProvisioningTimeoutMs = provisioningConfigurationParcelable.provisioningTimeoutMs;
        provisioningConfiguration.mIPv6AddrGenMode = provisioningConfigurationParcelable.ipv6AddrGenMode;
        provisioningConfiguration.mNetwork = provisioningConfigurationParcelable.network;
        provisioningConfiguration.mDisplayName = provisioningConfigurationParcelable.displayName;
        provisioningConfiguration.mCreatorUid = provisioningConfigurationParcelable.creatorUid;
        provisioningConfiguration.mScanResultInfo = android.net.shared.ProvisioningConfiguration.ScanResultInfo.fromStableParcelable(provisioningConfigurationParcelable.scanResultInfo);
        provisioningConfiguration.mLayer2Info = android.net.shared.Layer2Information.fromStableParcelable(provisioningConfigurationParcelable.layer2Info);
        provisioningConfiguration.mDhcpOptions = provisioningConfigurationParcelable.options != null ? new java.util.ArrayList(provisioningConfigurationParcelable.options) : null;
        if (i < 12) {
            provisioningConfiguration.mIPv4ProvisioningMode = provisioningConfigurationParcelable.enableIPv4 ? 2 : 0;
            provisioningConfiguration.mIPv6ProvisioningMode = provisioningConfigurationParcelable.enableIPv6 ? 1 : 0;
        } else {
            provisioningConfiguration.mIPv4ProvisioningMode = provisioningConfigurationParcelable.ipv4ProvisioningMode;
            provisioningConfiguration.mIPv6ProvisioningMode = provisioningConfigurationParcelable.ipv6ProvisioningMode;
        }
        provisioningConfiguration.mHostnameSetting = provisioningConfigurationParcelable.hostnameSetting;
        return provisioningConfiguration;
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String ipv4ProvisioningModeToString(int i) {
        switch (i) {
            case 0:
                return com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED;
            case 1:
                return "static";
            case 2:
                return "dhcp";
            default:
                return "unknown";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String ipv6ProvisioningModeToString(int i) {
        switch (i) {
            case 0:
                return com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED;
            case 1:
                return "slaac";
            case 2:
                return "link-local";
            default:
                return "unknown";
        }
    }

    public java.lang.String toString() {
        java.lang.String ipv4ProvisioningModeToString = ipv4ProvisioningModeToString(this.mIPv4ProvisioningMode);
        java.lang.String ipv6ProvisioningModeToString = ipv6ProvisioningModeToString(this.mIPv6ProvisioningMode);
        return new java.util.StringJoiner(", ", getClass().getSimpleName() + "{", "}").add("mUniqueEui64AddressesOnly: " + this.mUniqueEui64AddressesOnly).add("mEnablePreconnection: " + this.mEnablePreconnection).add("mUsingMultinetworkPolicyTracker: " + this.mUsingMultinetworkPolicyTracker).add("mUsingIpReachabilityMonitor: " + this.mUsingIpReachabilityMonitor).add("mRequestedPreDhcpActionMs: " + this.mRequestedPreDhcpActionMs).add("mInitialConfig: " + this.mInitialConfig).add("mStaticIpConfig: " + this.mStaticIpConfig).add("mApfCapabilities: " + this.mApfCapabilities).add("mProvisioningTimeoutMs: " + this.mProvisioningTimeoutMs).add("mIPv6AddrGenMode: " + this.mIPv6AddrGenMode).add("mNetwork: " + this.mNetwork).add("mDisplayName: " + this.mDisplayName).add("mCreatorUid:" + this.mCreatorUid).add("mScanResultInfo: " + this.mScanResultInfo).add("mLayer2Info: " + this.mLayer2Info).add("mDhcpOptions: " + this.mDhcpOptions).add("mIPv4ProvisioningMode: " + ipv4ProvisioningModeToString).add("mIPv6ProvisioningMode: " + ipv6ProvisioningModeToString).add("mHostnameSetting: " + this.mHostnameSetting).toString();
    }

    private static boolean dhcpOptionEquals(@android.annotation.Nullable android.net.networkstack.aidl.dhcp.DhcpOption dhcpOption, @android.annotation.Nullable android.net.networkstack.aidl.dhcp.DhcpOption dhcpOption2) {
        if (dhcpOption == dhcpOption2) {
            return true;
        }
        if (dhcpOption == null || dhcpOption2 == null) {
            return false;
        }
        if (dhcpOption.type == dhcpOption2.type && java.util.Arrays.equals(dhcpOption.value, dhcpOption2.value)) {
            return true;
        }
        return false;
    }

    private static boolean dhcpOptionListEquals(@android.annotation.Nullable java.util.List<android.net.networkstack.aidl.dhcp.DhcpOption> list, @android.annotation.Nullable java.util.List<android.net.networkstack.aidl.dhcp.DhcpOption> list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!dhcpOptionEquals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.shared.ProvisioningConfiguration)) {
            return false;
        }
        android.net.shared.ProvisioningConfiguration provisioningConfiguration = (android.net.shared.ProvisioningConfiguration) obj;
        return this.mUniqueEui64AddressesOnly == provisioningConfiguration.mUniqueEui64AddressesOnly && this.mEnablePreconnection == provisioningConfiguration.mEnablePreconnection && this.mUsingMultinetworkPolicyTracker == provisioningConfiguration.mUsingMultinetworkPolicyTracker && this.mUsingIpReachabilityMonitor == provisioningConfiguration.mUsingIpReachabilityMonitor && this.mRequestedPreDhcpActionMs == provisioningConfiguration.mRequestedPreDhcpActionMs && java.util.Objects.equals(this.mInitialConfig, provisioningConfiguration.mInitialConfig) && java.util.Objects.equals(this.mStaticIpConfig, provisioningConfiguration.mStaticIpConfig) && java.util.Objects.equals(this.mApfCapabilities, provisioningConfiguration.mApfCapabilities) && this.mProvisioningTimeoutMs == provisioningConfiguration.mProvisioningTimeoutMs && this.mIPv6AddrGenMode == provisioningConfiguration.mIPv6AddrGenMode && java.util.Objects.equals(this.mNetwork, provisioningConfiguration.mNetwork) && java.util.Objects.equals(this.mDisplayName, provisioningConfiguration.mDisplayName) && java.util.Objects.equals(this.mScanResultInfo, provisioningConfiguration.mScanResultInfo) && java.util.Objects.equals(this.mLayer2Info, provisioningConfiguration.mLayer2Info) && dhcpOptionListEquals(this.mDhcpOptions, provisioningConfiguration.mDhcpOptions) && this.mIPv4ProvisioningMode == provisioningConfiguration.mIPv4ProvisioningMode && this.mIPv6ProvisioningMode == provisioningConfiguration.mIPv6ProvisioningMode && this.mCreatorUid == provisioningConfiguration.mCreatorUid && this.mHostnameSetting == provisioningConfiguration.mHostnameSetting;
    }

    public boolean isValid() {
        return this.mInitialConfig == null || this.mInitialConfig.isValid();
    }
}
