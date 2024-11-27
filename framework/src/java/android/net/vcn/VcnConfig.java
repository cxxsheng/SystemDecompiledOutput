package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.vcn.VcnConfig> CREATOR;
    private static final java.lang.String GATEWAY_CONNECTION_CONFIGS_KEY = "mGatewayConnectionConfigs";
    private static final java.lang.String IS_TEST_MODE_PROFILE_KEY = "mIsTestModeProfile";
    private static final java.lang.String PACKAGE_NAME_KEY = "mPackageName";
    private static final java.util.Set<java.lang.Integer> RESTRICTED_TRANSPORTS_DEFAULT;
    private static final java.lang.String RESTRICTED_TRANSPORTS_KEY = "mRestrictedTransports";
    private final java.util.Set<android.net.vcn.VcnGatewayConnectionConfig> mGatewayConnectionConfigs;
    private final boolean mIsTestModeProfile;
    private final java.lang.String mPackageName;
    private final java.util.Set<java.lang.Integer> mRestrictedTransports;
    private static final java.lang.String TAG = android.net.vcn.VcnConfig.class.getSimpleName();
    private static final java.util.Set<java.lang.Integer> ALLOWED_TRANSPORTS = new android.util.ArraySet();

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VcnUnderlyingNetworkTransport {
    }

    static {
        ALLOWED_TRANSPORTS.add(1);
        ALLOWED_TRANSPORTS.add(0);
        ALLOWED_TRANSPORTS.add(7);
        RESTRICTED_TRANSPORTS_DEFAULT = java.util.Collections.singleton(1);
        CREATOR = new android.os.Parcelable.Creator<android.net.vcn.VcnConfig>() { // from class: android.net.vcn.VcnConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.net.vcn.VcnConfig createFromParcel(android.os.Parcel parcel) {
                return new android.net.vcn.VcnConfig((android.os.PersistableBundle) parcel.readParcelable(null, android.os.PersistableBundle.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.net.vcn.VcnConfig[] newArray(int i) {
                return new android.net.vcn.VcnConfig[i];
            }
        };
    }

    private VcnConfig(java.lang.String str, java.util.Set<android.net.vcn.VcnGatewayConnectionConfig> set, java.util.Set<java.lang.Integer> set2, boolean z) {
        this.mPackageName = str;
        this.mGatewayConnectionConfigs = java.util.Collections.unmodifiableSet(new android.util.ArraySet(set));
        this.mRestrictedTransports = java.util.Collections.unmodifiableSet(new android.util.ArraySet(set2));
        this.mIsTestModeProfile = z;
        validate();
    }

    public VcnConfig(android.os.PersistableBundle persistableBundle) {
        this.mPackageName = persistableBundle.getString(PACKAGE_NAME_KEY);
        this.mGatewayConnectionConfigs = new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle.getPersistableBundle(GATEWAY_CONNECTION_CONFIGS_KEY), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.VcnConfig$$ExternalSyntheticLambda1
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle2) {
                return new android.net.vcn.VcnGatewayConnectionConfig(persistableBundle2);
            }
        }));
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(RESTRICTED_TRANSPORTS_KEY);
        if (persistableBundle2 == null) {
            this.mRestrictedTransports = RESTRICTED_TRANSPORTS_DEFAULT;
        } else {
            this.mRestrictedTransports = new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER));
        }
        this.mIsTestModeProfile = persistableBundle.getBoolean(IS_TEST_MODE_PROFILE_KEY);
        validate();
    }

    private void validate() {
        java.util.Objects.requireNonNull(this.mPackageName, "packageName was null");
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(this.mGatewayConnectionConfigs, "gatewayConnectionConfigs was empty");
        java.util.Iterator<java.lang.Integer> it = this.mRestrictedTransports.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (!ALLOWED_TRANSPORTS.contains(java.lang.Integer.valueOf(intValue))) {
                it.remove();
                android.util.Log.w(TAG, "Found invalid transport " + intValue + " which might be from a new version of VcnConfig");
            }
            if (intValue == 7 && !this.mIsTestModeProfile) {
                throw new java.lang.IllegalArgumentException("Found TRANSPORT_TEST in a non-test-mode profile");
            }
        }
    }

    public java.lang.String getProvisioningPackageName() {
        return this.mPackageName;
    }

    public java.util.Set<android.net.vcn.VcnGatewayConnectionConfig> getGatewayConnectionConfigs() {
        return java.util.Collections.unmodifiableSet(this.mGatewayConnectionConfigs);
    }

    public java.util.Set<java.lang.Integer> getRestrictedUnderlyingNetworkTransports() {
        return java.util.Collections.unmodifiableSet(this.mRestrictedTransports);
    }

    public boolean isTestModeProfile() {
        return this.mIsTestModeProfile;
    }

    public android.os.PersistableBundle toPersistableBundle() {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString(PACKAGE_NAME_KEY, this.mPackageName);
        persistableBundle.putPersistableBundle(GATEWAY_CONNECTION_CONFIGS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mGatewayConnectionConfigs), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.VcnConfig$$ExternalSyntheticLambda0
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return ((android.net.vcn.VcnGatewayConnectionConfig) obj).toPersistableBundle();
            }
        }));
        persistableBundle.putPersistableBundle(RESTRICTED_TRANSPORTS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mRestrictedTransports), com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER));
        persistableBundle.putBoolean(IS_TEST_MODE_PROFILE_KEY, this.mIsTestModeProfile);
        return persistableBundle;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageName, this.mGatewayConnectionConfigs, this.mRestrictedTransports, java.lang.Boolean.valueOf(this.mIsTestModeProfile));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.vcn.VcnConfig)) {
            return false;
        }
        android.net.vcn.VcnConfig vcnConfig = (android.net.vcn.VcnConfig) obj;
        return this.mPackageName.equals(vcnConfig.mPackageName) && this.mGatewayConnectionConfigs.equals(vcnConfig.mGatewayConnectionConfigs) && this.mRestrictedTransports.equals(vcnConfig.mRestrictedTransports) && this.mIsTestModeProfile == vcnConfig.mIsTestModeProfile;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(toPersistableBundle(), i);
    }

    public static final class Builder {
        private final java.lang.String mPackageName;
        private final java.util.Set<android.net.vcn.VcnGatewayConnectionConfig> mGatewayConnectionConfigs = new android.util.ArraySet();
        private final java.util.Set<java.lang.Integer> mRestrictedTransports = new android.util.ArraySet();
        private boolean mIsTestModeProfile = false;

        public Builder(android.content.Context context) {
            java.util.Objects.requireNonNull(context, "context was null");
            this.mPackageName = context.getOpPackageName();
            this.mRestrictedTransports.addAll(android.net.vcn.VcnConfig.RESTRICTED_TRANSPORTS_DEFAULT);
        }

        public android.net.vcn.VcnConfig.Builder addGatewayConnectionConfig(android.net.vcn.VcnGatewayConnectionConfig vcnGatewayConnectionConfig) {
            java.util.Objects.requireNonNull(vcnGatewayConnectionConfig, "gatewayConnectionConfig was null");
            java.util.Iterator<android.net.vcn.VcnGatewayConnectionConfig> it = this.mGatewayConnectionConfigs.iterator();
            while (it.hasNext()) {
                if (it.next().getGatewayConnectionName().equals(vcnGatewayConnectionConfig.getGatewayConnectionName())) {
                    throw new java.lang.IllegalArgumentException("GatewayConnection for specified name already exists");
                }
            }
            this.mGatewayConnectionConfigs.add(vcnGatewayConnectionConfig);
            return this;
        }

        private void validateRestrictedTransportsOrThrow(java.util.Set<java.lang.Integer> set) {
            java.util.Objects.requireNonNull(set, "transports was null");
            java.util.Iterator<java.lang.Integer> it = set.iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (!android.net.vcn.VcnConfig.ALLOWED_TRANSPORTS.contains(java.lang.Integer.valueOf(intValue))) {
                    throw new java.lang.IllegalArgumentException("Invalid transport " + intValue);
                }
            }
        }

        public android.net.vcn.VcnConfig.Builder setRestrictedUnderlyingNetworkTransports(java.util.Set<java.lang.Integer> set) {
            validateRestrictedTransportsOrThrow(set);
            this.mRestrictedTransports.clear();
            this.mRestrictedTransports.addAll(set);
            return this;
        }

        public android.net.vcn.VcnConfig.Builder setIsTestModeProfile() {
            this.mIsTestModeProfile = true;
            return this;
        }

        public android.net.vcn.VcnConfig build() {
            return new android.net.vcn.VcnConfig(this.mPackageName, this.mGatewayConnectionConfigs, this.mRestrictedTransports, this.mIsTestModeProfile);
        }
    }
}
