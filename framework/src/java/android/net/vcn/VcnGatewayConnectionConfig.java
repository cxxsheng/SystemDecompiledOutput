package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnGatewayConnectionConfig {
    public static final java.util.Set<java.lang.Integer> ALLOWED_CAPABILITIES;
    private static final java.util.Set<java.lang.Integer> ALLOWED_GATEWAY_OPTIONS;
    private static final int DEFAULT_MAX_MTU = 1500;
    private static final long[] DEFAULT_RETRY_INTERVALS_MS;
    public static final java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> DEFAULT_UNDERLYING_NETWORK_TEMPLATES;
    private static final java.lang.String EXPOSED_CAPABILITIES_KEY = "mExposedCapabilities";
    private static final java.lang.String GATEWAY_CONNECTION_NAME_KEY = "mGatewayConnectionName";
    private static final java.lang.String GATEWAY_OPTIONS_KEY = "mGatewayOptions";
    private static final java.lang.String IS_SAFE_MODE_DISABLED_KEY = "mIsSafeModeDisabled";
    private static final java.lang.String MAX_MTU_KEY = "mMaxMtu";
    private static final int MAX_RETRY_INTERVAL_COUNT = 10;
    private static final long MINIMUM_REPEATING_RETRY_INTERVAL_MS;
    static final int MIN_MTU_V6 = 1280;
    public static final int MIN_UDP_PORT_4500_NAT_TIMEOUT_SECONDS = 120;
    private static final java.lang.String MIN_UDP_PORT_4500_NAT_TIMEOUT_SECONDS_KEY = "mMinUdpPort4500NatTimeoutSeconds";
    public static final int MIN_UDP_PORT_4500_NAT_TIMEOUT_UNSET = -1;
    private static final java.lang.String RETRY_INTERVAL_MS_KEY = "mRetryIntervalsMs";
    private static final java.lang.String TUNNEL_CONNECTION_PARAMS_KEY = "mTunnelConnectionParams";
    public static final java.lang.String UNDERLYING_NETWORK_TEMPLATES_KEY = "mUnderlyingNetworkTemplates";
    public static final int VCN_GATEWAY_OPTION_ENABLE_DATA_STALL_RECOVERY_WITH_MOBILITY = 0;
    private final java.util.SortedSet<java.lang.Integer> mExposedCapabilities;
    private final java.lang.String mGatewayConnectionName;
    private final java.util.Set<java.lang.Integer> mGatewayOptions;
    private final boolean mIsSafeModeDisabled;
    private final int mMaxMtu;
    private final int mMinUdpPort4500NatTimeoutSeconds;
    private final long[] mRetryIntervalsMs;
    private android.net.ipsec.ike.IkeTunnelConnectionParams mTunnelConnectionParams;
    private final java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> mUnderlyingNetworkTemplates;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VcnGatewayOption {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VcnSupportedCapability {
    }

    static {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.add(0);
        arraySet.add(1);
        arraySet.add(2);
        arraySet.add(3);
        arraySet.add(4);
        arraySet.add(5);
        arraySet.add(7);
        arraySet.add(8);
        arraySet.add(9);
        arraySet.add(10);
        arraySet.add(12);
        arraySet.add(23);
        ALLOWED_CAPABILITIES = java.util.Collections.unmodifiableSet(arraySet);
        ALLOWED_GATEWAY_OPTIONS = new android.util.ArraySet();
        ALLOWED_GATEWAY_OPTIONS.add(0);
        MINIMUM_REPEATING_RETRY_INTERVAL_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(15L);
        DEFAULT_RETRY_INTERVALS_MS = new long[]{java.util.concurrent.TimeUnit.SECONDS.toMillis(1L), java.util.concurrent.TimeUnit.SECONDS.toMillis(2L), java.util.concurrent.TimeUnit.SECONDS.toMillis(5L), java.util.concurrent.TimeUnit.SECONDS.toMillis(30L), java.util.concurrent.TimeUnit.MINUTES.toMillis(1L), java.util.concurrent.TimeUnit.MINUTES.toMillis(5L), java.util.concurrent.TimeUnit.MINUTES.toMillis(15L)};
        DEFAULT_UNDERLYING_NETWORK_TEMPLATES = new java.util.ArrayList();
        DEFAULT_UNDERLYING_NETWORK_TEMPLATES.add(new android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder().setOpportunistic(1).build());
        DEFAULT_UNDERLYING_NETWORK_TEMPLATES.add(new android.net.vcn.VcnWifiUnderlyingNetworkTemplate.Builder().build());
        DEFAULT_UNDERLYING_NETWORK_TEMPLATES.add(new android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder().build());
    }

    private VcnGatewayConnectionConfig(java.lang.String str, android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams, java.util.Set<java.lang.Integer> set, java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list, long[] jArr, int i, int i2, boolean z, java.util.Set<java.lang.Integer> set2) {
        this.mGatewayConnectionName = str;
        this.mTunnelConnectionParams = ikeTunnelConnectionParams;
        this.mExposedCapabilities = new java.util.TreeSet(set);
        this.mRetryIntervalsMs = jArr;
        this.mMaxMtu = i;
        this.mMinUdpPort4500NatTimeoutSeconds = i2;
        this.mGatewayOptions = java.util.Collections.unmodifiableSet(new android.util.ArraySet(set2));
        this.mIsSafeModeDisabled = z;
        this.mUnderlyingNetworkTemplates = new java.util.ArrayList(list);
        if (this.mUnderlyingNetworkTemplates.isEmpty()) {
            this.mUnderlyingNetworkTemplates.addAll(DEFAULT_UNDERLYING_NETWORK_TEMPLATES);
        }
        validate();
    }

    public VcnGatewayConnectionConfig(android.os.PersistableBundle persistableBundle) {
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(TUNNEL_CONNECTION_PARAMS_KEY);
        java.util.Objects.requireNonNull(persistableBundle2, "tunnelConnectionParamsBundle was null");
        android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EXPOSED_CAPABILITIES_KEY);
        this.mGatewayConnectionName = persistableBundle.getString(GATEWAY_CONNECTION_NAME_KEY);
        this.mTunnelConnectionParams = android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.fromPersistableBundle(persistableBundle2);
        this.mExposedCapabilities = new java.util.TreeSet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle3, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER));
        android.os.PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(UNDERLYING_NETWORK_TEMPLATES_KEY);
        if (persistableBundle4 == null) {
            this.mUnderlyingNetworkTemplates = new java.util.ArrayList(DEFAULT_UNDERLYING_NETWORK_TEMPLATES);
        } else {
            this.mUnderlyingNetworkTemplates = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle4, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.VcnGatewayConnectionConfig$$ExternalSyntheticLambda0
                @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
                public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle5) {
                    return android.net.vcn.VcnUnderlyingNetworkTemplate.fromPersistableBundle(persistableBundle5);
                }
            });
        }
        android.os.PersistableBundle persistableBundle5 = persistableBundle.getPersistableBundle(GATEWAY_OPTIONS_KEY);
        if (persistableBundle5 == null) {
            this.mGatewayOptions = java.util.Collections.emptySet();
        } else {
            this.mGatewayOptions = new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle5, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER));
        }
        this.mRetryIntervalsMs = persistableBundle.getLongArray(RETRY_INTERVAL_MS_KEY);
        this.mMaxMtu = persistableBundle.getInt(MAX_MTU_KEY);
        this.mMinUdpPort4500NatTimeoutSeconds = persistableBundle.getInt(MIN_UDP_PORT_4500_NAT_TIMEOUT_SECONDS_KEY, -1);
        this.mIsSafeModeDisabled = persistableBundle.getBoolean(IS_SAFE_MODE_DISABLED_KEY);
        validate();
    }

    private void validate() {
        java.util.Objects.requireNonNull(this.mGatewayConnectionName, "gatewayConnectionName was null");
        java.util.Objects.requireNonNull(this.mTunnelConnectionParams, "tunnel connection parameter was null");
        boolean z = true;
        com.android.internal.util.Preconditions.checkArgument((this.mExposedCapabilities == null || this.mExposedCapabilities.isEmpty()) ? false : true, "exposedCapsBundle was null or empty");
        java.util.Iterator<java.lang.Integer> it = getAllExposedCapabilities().iterator();
        while (it.hasNext()) {
            checkValidCapability(it.next().intValue());
        }
        validateNetworkTemplateList(this.mUnderlyingNetworkTemplates);
        java.util.Objects.requireNonNull(this.mRetryIntervalsMs, "retryIntervalsMs was null");
        validateRetryInterval(this.mRetryIntervalsMs);
        com.android.internal.util.Preconditions.checkArgument(this.mMaxMtu >= 1280, "maxMtu must be at least IPv6 min MTU (1280)");
        if (this.mMinUdpPort4500NatTimeoutSeconds != -1 && this.mMinUdpPort4500NatTimeoutSeconds < 120) {
            z = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "minUdpPort4500NatTimeoutSeconds must be at least 120s");
        java.util.Iterator<java.lang.Integer> it2 = this.mGatewayOptions.iterator();
        while (it2.hasNext()) {
            validateGatewayOption(it2.next().intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkValidCapability(int i) {
        com.android.internal.util.Preconditions.checkArgument(ALLOWED_CAPABILITIES.contains(java.lang.Integer.valueOf(i)), "NetworkCapability " + i + "out of range");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateRetryInterval(long[] jArr) {
        com.android.internal.util.Preconditions.checkArgument(jArr != null && jArr.length > 0 && jArr.length <= 10, "retryIntervalsMs was null, empty or exceed max interval count");
        long j = jArr[jArr.length - 1];
        if (j < MINIMUM_REPEATING_RETRY_INTERVAL_MS) {
            throw new java.lang.IllegalArgumentException("Repeating retry interval was too short, must be a minimum of 15 minutes: " + j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateNetworkTemplateList(java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list) {
        java.util.Objects.requireNonNull(list, "networkPriorityRules is null");
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.net.vcn.VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate : list) {
            java.util.Objects.requireNonNull(vcnUnderlyingNetworkTemplate, "Found null value VcnUnderlyingNetworkTemplate");
            if (!arraySet.add(vcnUnderlyingNetworkTemplate)) {
                throw new java.lang.IllegalArgumentException("Found duplicate VcnUnderlyingNetworkTemplate");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateGatewayOption(int i) {
        if (!ALLOWED_GATEWAY_OPTIONS.contains(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalArgumentException("Invalid vcn gateway option: " + i);
        }
    }

    public java.lang.String getGatewayConnectionName() {
        return this.mGatewayConnectionName;
    }

    public android.net.ipsec.ike.IkeTunnelConnectionParams getTunnelConnectionParams() {
        return this.mTunnelConnectionParams;
    }

    public int[] getExposedCapabilities() {
        return com.android.internal.util.ArrayUtils.convertToIntArray(new java.util.ArrayList(this.mExposedCapabilities));
    }

    @java.lang.Deprecated
    public java.util.Set<java.lang.Integer> getAllExposedCapabilities() {
        return java.util.Collections.unmodifiableSet(this.mExposedCapabilities);
    }

    public java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> getVcnUnderlyingNetworkPriorities() {
        return new java.util.ArrayList(this.mUnderlyingNetworkTemplates);
    }

    public long[] getRetryIntervalsMillis() {
        return java.util.Arrays.copyOf(this.mRetryIntervalsMs, this.mRetryIntervalsMs.length);
    }

    public int getMaxMtu() {
        return this.mMaxMtu;
    }

    public int getMinUdpPort4500NatTimeoutSeconds() {
        return this.mMinUdpPort4500NatTimeoutSeconds;
    }

    public boolean isSafeModeEnabled() {
        return !this.mIsSafeModeDisabled;
    }

    public boolean hasGatewayOption(int i) {
        validateGatewayOption(i);
        return this.mGatewayOptions.contains(java.lang.Integer.valueOf(i));
    }

    public android.os.PersistableBundle toPersistableBundle() {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        android.os.PersistableBundle persistableBundle2 = android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.toPersistableBundle(this.mTunnelConnectionParams);
        android.os.PersistableBundle fromList = com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mExposedCapabilities), com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER);
        android.os.PersistableBundle fromList2 = com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(this.mUnderlyingNetworkTemplates, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.VcnGatewayConnectionConfig$$ExternalSyntheticLambda1
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return ((android.net.vcn.VcnUnderlyingNetworkTemplate) obj).toPersistableBundle();
            }
        });
        android.os.PersistableBundle fromList3 = com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mGatewayOptions), com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER);
        persistableBundle.putString(GATEWAY_CONNECTION_NAME_KEY, this.mGatewayConnectionName);
        persistableBundle.putPersistableBundle(TUNNEL_CONNECTION_PARAMS_KEY, persistableBundle2);
        persistableBundle.putPersistableBundle(EXPOSED_CAPABILITIES_KEY, fromList);
        persistableBundle.putPersistableBundle(UNDERLYING_NETWORK_TEMPLATES_KEY, fromList2);
        persistableBundle.putPersistableBundle(GATEWAY_OPTIONS_KEY, fromList3);
        persistableBundle.putLongArray(RETRY_INTERVAL_MS_KEY, this.mRetryIntervalsMs);
        persistableBundle.putInt(MAX_MTU_KEY, this.mMaxMtu);
        persistableBundle.putInt(MIN_UDP_PORT_4500_NAT_TIMEOUT_SECONDS_KEY, this.mMinUdpPort4500NatTimeoutSeconds);
        persistableBundle.putBoolean(IS_SAFE_MODE_DISABLED_KEY, this.mIsSafeModeDisabled);
        return persistableBundle;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mGatewayConnectionName, this.mTunnelConnectionParams, this.mExposedCapabilities, this.mUnderlyingNetworkTemplates, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRetryIntervalsMs)), java.lang.Integer.valueOf(this.mMaxMtu), java.lang.Integer.valueOf(this.mMinUdpPort4500NatTimeoutSeconds), java.lang.Boolean.valueOf(this.mIsSafeModeDisabled), this.mGatewayOptions);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.vcn.VcnGatewayConnectionConfig)) {
            return false;
        }
        android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig = (android.net.vcn.VcnGatewayConnectionConfig) obj;
        return this.mGatewayConnectionName.equals(vcnGatewayConnectionConfig.mGatewayConnectionName) && this.mTunnelConnectionParams.equals(vcnGatewayConnectionConfig.mTunnelConnectionParams) && this.mExposedCapabilities.equals(vcnGatewayConnectionConfig.mExposedCapabilities) && this.mUnderlyingNetworkTemplates.equals(vcnGatewayConnectionConfig.mUnderlyingNetworkTemplates) && java.util.Arrays.equals(this.mRetryIntervalsMs, vcnGatewayConnectionConfig.mRetryIntervalsMs) && this.mMaxMtu == vcnGatewayConnectionConfig.mMaxMtu && this.mMinUdpPort4500NatTimeoutSeconds == vcnGatewayConnectionConfig.mMinUdpPort4500NatTimeoutSeconds && this.mIsSafeModeDisabled == vcnGatewayConnectionConfig.mIsSafeModeDisabled && this.mGatewayOptions.equals(vcnGatewayConnectionConfig.mGatewayOptions);
    }

    public static final class Builder {
        private final java.lang.String mGatewayConnectionName;
        private final android.net.ipsec.ike.IkeTunnelConnectionParams mTunnelConnectionParams;
        private final java.util.Set<java.lang.Integer> mExposedCapabilities = new android.util.ArraySet();
        private final java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> mUnderlyingNetworkTemplates = new java.util.ArrayList(android.net.vcn.VcnGatewayConnectionConfig.DEFAULT_UNDERLYING_NETWORK_TEMPLATES);
        private long[] mRetryIntervalsMs = android.net.vcn.VcnGatewayConnectionConfig.DEFAULT_RETRY_INTERVALS_MS;
        private int mMaxMtu = 1500;
        private int mMinUdpPort4500NatTimeoutSeconds = -1;
        private boolean mIsSafeModeDisabled = false;
        private final java.util.Set<java.lang.Integer> mGatewayOptions = new android.util.ArraySet();

        public Builder(java.lang.String str, android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams) {
            java.util.Objects.requireNonNull(str, "gatewayConnectionName was null");
            java.util.Objects.requireNonNull(ikeTunnelConnectionParams, "tunnelConnectionParams was null");
            if (!ikeTunnelConnectionParams.getIkeSessionParams().hasIkeOption(2)) {
                throw new java.lang.IllegalArgumentException("MOBIKE must be configured for the provided IkeSessionParams");
            }
            this.mGatewayConnectionName = str;
            this.mTunnelConnectionParams = ikeTunnelConnectionParams;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder addExposedCapability(int i) {
            android.net.vcn.VcnGatewayConnectionConfig.checkValidCapability(i);
            this.mExposedCapabilities.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder removeExposedCapability(int i) {
            android.net.vcn.VcnGatewayConnectionConfig.checkValidCapability(i);
            this.mExposedCapabilities.remove(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder setVcnUnderlyingNetworkPriorities(java.util.List<android.net.vcn.VcnUnderlyingNetworkTemplate> list) {
            android.net.vcn.VcnGatewayConnectionConfig.validateNetworkTemplateList(list);
            this.mUnderlyingNetworkTemplates.clear();
            if (list.isEmpty()) {
                this.mUnderlyingNetworkTemplates.addAll(android.net.vcn.VcnGatewayConnectionConfig.DEFAULT_UNDERLYING_NETWORK_TEMPLATES);
            } else {
                this.mUnderlyingNetworkTemplates.addAll(list);
            }
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder setRetryIntervalsMillis(long[] jArr) {
            android.net.vcn.VcnGatewayConnectionConfig.validateRetryInterval(jArr);
            this.mRetryIntervalsMs = jArr;
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder setMaxMtu(int i) {
            com.android.internal.util.Preconditions.checkArgument(i >= 1280, "maxMtu must be at least IPv6 min MTU (1280)");
            this.mMaxMtu = i;
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder setMinUdpPort4500NatTimeoutSeconds(int i) {
            com.android.internal.util.Preconditions.checkArgument(i >= 120, "Timeout must be at least 120s");
            this.mMinUdpPort4500NatTimeoutSeconds = i;
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder addGatewayOption(int i) {
            android.net.vcn.VcnGatewayConnectionConfig.validateGatewayOption(i);
            this.mGatewayOptions.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder removeGatewayOption(int i) {
            android.net.vcn.VcnGatewayConnectionConfig.validateGatewayOption(i);
            this.mGatewayOptions.remove(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig.Builder setSafeModeEnabled(boolean z) {
            this.mIsSafeModeDisabled = !z;
            return this;
        }

        public android.net.vcn.VcnGatewayConnectionConfig build() {
            return new android.net.vcn.VcnGatewayConnectionConfig(this.mGatewayConnectionName, this.mTunnelConnectionParams, this.mExposedCapabilities, this.mUnderlyingNetworkTemplates, this.mRetryIntervalsMs, this.mMaxMtu, this.mMinUdpPort4500NatTimeoutSeconds, this.mIsSafeModeDisabled, this.mGatewayOptions);
        }
    }
}
