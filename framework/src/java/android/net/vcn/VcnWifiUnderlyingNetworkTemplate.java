package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnWifiUnderlyingNetworkTemplate extends android.net.vcn.VcnUnderlyingNetworkTemplate {
    private static final java.lang.String SSIDS_KEY = "mSsids";
    private final java.util.Set<java.lang.String> mSsids;

    private VcnWifiUnderlyingNetworkTemplate(int i, int i2, int i3, int i4, int i5, java.util.Set<java.lang.String> set) {
        super(1, i, i2, i3, i4, i5);
        this.mSsids = new android.util.ArraySet(set);
        validate();
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    protected void validate() {
        super.validate();
        validateSsids(this.mSsids);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateSsids(java.util.Set<java.lang.String> set) {
        java.util.Objects.requireNonNull(set, "ssids is null");
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            java.util.Objects.requireNonNull(it.next(), "found null value ssid");
        }
    }

    public static android.net.vcn.VcnWifiUnderlyingNetworkTemplate fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        int i = persistableBundle.getInt("mMeteredMatchCriteria");
        int i2 = persistableBundle.getInt("mMinEntryUpstreamBandwidthKbps", 0);
        int i3 = persistableBundle.getInt("mMinExitUpstreamBandwidthKbps", 0);
        int i4 = persistableBundle.getInt("mMinEntryDownstreamBandwidthKbps", 0);
        int i5 = persistableBundle.getInt("mMinExitDownstreamBandwidthKbps", 0);
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(SSIDS_KEY);
        java.util.Objects.requireNonNull(persistableBundle2, "ssidsBundle is null");
        return new android.net.vcn.VcnWifiUnderlyingNetworkTemplate(i, i2, i3, i4, i5, new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, com.android.server.vcn.repackaged.util.PersistableBundleUtils.STRING_DESERIALIZER)));
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public android.os.PersistableBundle toPersistableBundle() {
        android.os.PersistableBundle persistableBundle = super.toPersistableBundle();
        persistableBundle.putPersistableBundle(SSIDS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mSsids), com.android.server.vcn.repackaged.util.PersistableBundleUtils.STRING_SERIALIZER));
        return persistableBundle;
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mSsids);
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public boolean equals(java.lang.Object obj) {
        if (super.equals(obj) && (obj instanceof android.net.vcn.VcnWifiUnderlyingNetworkTemplate)) {
            return this.mSsids.equals(((android.net.vcn.VcnWifiUnderlyingNetworkTemplate) obj).mSsids);
        }
        return false;
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    void dumpTransportSpecificFields(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        if (!this.mSsids.isEmpty()) {
            indentingPrintWriter.println("mSsids: " + this.mSsids);
        }
    }

    public java.util.Set<java.lang.String> getSsids() {
        return java.util.Collections.unmodifiableSet(this.mSsids);
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public java.util.Map<java.lang.Integer, java.lang.Integer> getCapabilitiesMatchCriteria() {
        return java.util.Collections.singletonMap(12, 1);
    }

    public static final class Builder {
        private int mMeteredMatchCriteria = 0;
        private final java.util.Set<java.lang.String> mSsids = new android.util.ArraySet();
        private int mMinEntryUpstreamBandwidthKbps = 0;
        private int mMinExitUpstreamBandwidthKbps = 0;
        private int mMinEntryDownstreamBandwidthKbps = 0;
        private int mMinExitDownstreamBandwidthKbps = 0;

        public android.net.vcn.VcnWifiUnderlyingNetworkTemplate.Builder setMetered(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setMetered");
            this.mMeteredMatchCriteria = i;
            return this;
        }

        public android.net.vcn.VcnWifiUnderlyingNetworkTemplate.Builder setSsids(java.util.Set<java.lang.String> set) {
            android.net.vcn.VcnWifiUnderlyingNetworkTemplate.validateSsids(set);
            this.mSsids.clear();
            this.mSsids.addAll(set);
            return this;
        }

        public android.net.vcn.VcnWifiUnderlyingNetworkTemplate.Builder setMinUpstreamBandwidthKbps(int i, int i2) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMinBandwidthKbps(i, i2);
            this.mMinEntryUpstreamBandwidthKbps = i;
            this.mMinExitUpstreamBandwidthKbps = i2;
            return this;
        }

        public android.net.vcn.VcnWifiUnderlyingNetworkTemplate.Builder setMinDownstreamBandwidthKbps(int i, int i2) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMinBandwidthKbps(i, i2);
            this.mMinEntryDownstreamBandwidthKbps = i;
            this.mMinExitDownstreamBandwidthKbps = i2;
            return this;
        }

        public android.net.vcn.VcnWifiUnderlyingNetworkTemplate build() {
            return new android.net.vcn.VcnWifiUnderlyingNetworkTemplate(this.mMeteredMatchCriteria, this.mMinEntryUpstreamBandwidthKbps, this.mMinExitUpstreamBandwidthKbps, this.mMinEntryDownstreamBandwidthKbps, this.mMinExitDownstreamBandwidthKbps, this.mSsids);
        }
    }
}
