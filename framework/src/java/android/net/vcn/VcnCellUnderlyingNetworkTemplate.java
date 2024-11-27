package android.net.vcn;

/* loaded from: classes2.dex */
public final class VcnCellUnderlyingNetworkTemplate extends android.net.vcn.VcnUnderlyingNetworkTemplate {
    private static final java.lang.String ALLOWED_NETWORK_PLMN_IDS_KEY = "mAllowedNetworkPlmnIds";
    private static final java.lang.String ALLOWED_SPECIFIC_CARRIER_IDS_KEY = "mAllowedSpecificCarrierIds";
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> CAPABILITIES_MATCH_CRITERIA_DEFAULT;
    private static final java.lang.String CAPABILITIES_MATCH_CRITERIA_KEY = "mCapabilitiesMatchCriteria";
    private static final int DEFAULT_OPPORTUNISTIC_MATCH_CRITERIA = 0;
    private static final int DEFAULT_ROAMING_MATCH_CRITERIA = 0;
    private static final java.lang.String OPPORTUNISTIC_MATCH_KEY = "mOpportunisticMatchCriteria";
    private static final java.lang.String ROAMING_MATCH_KEY = "mRoamingMatchCriteria";
    private final java.util.Set<java.lang.String> mAllowedNetworkPlmnIds;
    private final java.util.Set<java.lang.Integer> mAllowedSpecificCarrierIds;
    private final java.util.Map<java.lang.Integer, java.lang.Integer> mCapabilitiesMatchCriteria;
    private final int mOpportunisticMatchCriteria;
    private final int mRoamingMatchCriteria;

    static {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(5, 0);
        hashMap.put(2, 0);
        hashMap.put(4, 0);
        hashMap.put(12, 1);
        hashMap.put(0, 0);
        hashMap.put(8, 0);
        CAPABILITIES_MATCH_CRITERIA_DEFAULT = java.util.Collections.unmodifiableMap(hashMap);
    }

    private VcnCellUnderlyingNetworkTemplate(int i, int i2, int i3, int i4, int i5, java.util.Set<java.lang.String> set, java.util.Set<java.lang.Integer> set2, int i6, int i7, java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        super(2, i, i2, i3, i4, i5);
        this.mAllowedNetworkPlmnIds = new android.util.ArraySet(set);
        this.mAllowedSpecificCarrierIds = new android.util.ArraySet(set2);
        this.mRoamingMatchCriteria = i6;
        this.mOpportunisticMatchCriteria = i7;
        this.mCapabilitiesMatchCriteria = new java.util.HashMap(map);
        validate();
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    protected void validate() {
        super.validate();
        validatePlmnIds(this.mAllowedNetworkPlmnIds);
        validateCapabilitiesMatchCriteria(this.mCapabilitiesMatchCriteria);
        java.util.Objects.requireNonNull(this.mAllowedSpecificCarrierIds, "matchingCarrierIds is null");
        validateMatchCriteria(this.mRoamingMatchCriteria, ROAMING_MATCH_KEY);
        validateMatchCriteria(this.mOpportunisticMatchCriteria, OPPORTUNISTIC_MATCH_KEY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validatePlmnIds(java.util.Set<java.lang.String> set) {
        java.util.Objects.requireNonNull(set, "matchingOperatorPlmnIds is null");
        for (java.lang.String str : set) {
            if ((str.length() != 5 && str.length() != 6) || !str.matches("[0-9]+")) {
                throw new java.lang.IllegalArgumentException("Found invalid PLMN ID: " + str);
            }
        }
    }

    private static void validateCapabilitiesMatchCriteria(java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        java.util.Objects.requireNonNull(map, "capabilitiesMatchCriteria is null");
        boolean z = false;
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : map.entrySet()) {
            int intValue = entry.getKey().intValue();
            int intValue2 = entry.getValue().intValue();
            com.android.internal.util.Preconditions.checkArgument(CAPABILITIES_MATCH_CRITERIA_DEFAULT.containsKey(java.lang.Integer.valueOf(intValue)), "NetworkCapability " + intValue + "out of range");
            validateMatchCriteria(intValue2, "capability " + intValue);
            boolean z2 = true;
            if (intValue2 != 1) {
                z2 = false;
            }
            z |= z2;
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("No required capabilities found");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static android.net.vcn.VcnCellUnderlyingNetworkTemplate fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Map map;
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        int i = persistableBundle.getInt("mMeteredMatchCriteria");
        int i2 = persistableBundle.getInt("mMinEntryUpstreamBandwidthKbps", 0);
        int i3 = persistableBundle.getInt("mMinExitUpstreamBandwidthKbps", 0);
        int i4 = persistableBundle.getInt("mMinEntryDownstreamBandwidthKbps", 0);
        int i5 = persistableBundle.getInt("mMinExitDownstreamBandwidthKbps", 0);
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(ALLOWED_NETWORK_PLMN_IDS_KEY);
        java.util.Objects.requireNonNull(persistableBundle2, "plmnIdsBundle is null");
        android.util.ArraySet arraySet = new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, com.android.server.vcn.repackaged.util.PersistableBundleUtils.STRING_DESERIALIZER));
        android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(ALLOWED_SPECIFIC_CARRIER_IDS_KEY);
        java.util.Objects.requireNonNull(persistableBundle3, "specificCarrierIdsBundle is null");
        android.util.ArraySet arraySet2 = new android.util.ArraySet(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle3, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER));
        android.os.PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(CAPABILITIES_MATCH_CRITERIA_KEY);
        if (persistableBundle4 == null) {
            map = CAPABILITIES_MATCH_CRITERIA_DEFAULT;
        } else {
            map = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toMap(persistableBundle4, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_DESERIALIZER);
        }
        return new android.net.vcn.VcnCellUnderlyingNetworkTemplate(i, i2, i3, i4, i5, arraySet, arraySet2, persistableBundle.getInt(ROAMING_MATCH_KEY), persistableBundle.getInt(OPPORTUNISTIC_MATCH_KEY), map);
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public android.os.PersistableBundle toPersistableBundle() {
        android.os.PersistableBundle persistableBundle = super.toPersistableBundle();
        persistableBundle.putPersistableBundle(ALLOWED_NETWORK_PLMN_IDS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mAllowedNetworkPlmnIds), com.android.server.vcn.repackaged.util.PersistableBundleUtils.STRING_SERIALIZER));
        persistableBundle.putPersistableBundle(ALLOWED_SPECIFIC_CARRIER_IDS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(new java.util.ArrayList(this.mAllowedSpecificCarrierIds), com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER));
        persistableBundle.putPersistableBundle(CAPABILITIES_MATCH_CRITERIA_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromMap(this.mCapabilitiesMatchCriteria, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER, com.android.server.vcn.repackaged.util.PersistableBundleUtils.INTEGER_SERIALIZER));
        persistableBundle.putInt(ROAMING_MATCH_KEY, this.mRoamingMatchCriteria);
        persistableBundle.putInt(OPPORTUNISTIC_MATCH_KEY, this.mOpportunisticMatchCriteria);
        return persistableBundle;
    }

    public java.util.Set<java.lang.String> getOperatorPlmnIds() {
        return java.util.Collections.unmodifiableSet(this.mAllowedNetworkPlmnIds);
    }

    public java.util.Set<java.lang.Integer> getSimSpecificCarrierIds() {
        return java.util.Collections.unmodifiableSet(this.mAllowedSpecificCarrierIds);
    }

    public int getRoaming() {
        return this.mRoamingMatchCriteria;
    }

    public int getOpportunistic() {
        return this.mOpportunisticMatchCriteria;
    }

    public int getCbs() {
        return this.mCapabilitiesMatchCriteria.get(5).intValue();
    }

    public int getDun() {
        return this.mCapabilitiesMatchCriteria.get(2).intValue();
    }

    public int getIms() {
        return this.mCapabilitiesMatchCriteria.get(4).intValue();
    }

    public int getInternet() {
        return this.mCapabilitiesMatchCriteria.get(12).intValue();
    }

    public int getMms() {
        return this.mCapabilitiesMatchCriteria.get(0).intValue();
    }

    public int getRcs() {
        return this.mCapabilitiesMatchCriteria.get(8).intValue();
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public java.util.Map<java.lang.Integer, java.lang.Integer> getCapabilitiesMatchCriteria() {
        return java.util.Collections.unmodifiableMap(new java.util.HashMap(this.mCapabilitiesMatchCriteria));
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mAllowedNetworkPlmnIds, this.mAllowedSpecificCarrierIds, this.mCapabilitiesMatchCriteria, java.lang.Integer.valueOf(this.mRoamingMatchCriteria), java.lang.Integer.valueOf(this.mOpportunisticMatchCriteria));
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    public boolean equals(java.lang.Object obj) {
        if (!super.equals(obj) || !(obj instanceof android.net.vcn.VcnCellUnderlyingNetworkTemplate)) {
            return false;
        }
        android.net.vcn.VcnCellUnderlyingNetworkTemplate vcnCellUnderlyingNetworkTemplate = (android.net.vcn.VcnCellUnderlyingNetworkTemplate) obj;
        return java.util.Objects.equals(this.mAllowedNetworkPlmnIds, vcnCellUnderlyingNetworkTemplate.mAllowedNetworkPlmnIds) && java.util.Objects.equals(this.mAllowedSpecificCarrierIds, vcnCellUnderlyingNetworkTemplate.mAllowedSpecificCarrierIds) && java.util.Objects.equals(this.mCapabilitiesMatchCriteria, vcnCellUnderlyingNetworkTemplate.mCapabilitiesMatchCriteria) && this.mRoamingMatchCriteria == vcnCellUnderlyingNetworkTemplate.mRoamingMatchCriteria && this.mOpportunisticMatchCriteria == vcnCellUnderlyingNetworkTemplate.mOpportunisticMatchCriteria;
    }

    @Override // android.net.vcn.VcnUnderlyingNetworkTemplate
    void dumpTransportSpecificFields(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        if (!this.mAllowedNetworkPlmnIds.isEmpty()) {
            indentingPrintWriter.println("mAllowedNetworkPlmnIds: " + this.mAllowedNetworkPlmnIds);
        }
        if (!this.mAllowedNetworkPlmnIds.isEmpty()) {
            indentingPrintWriter.println("mAllowedSpecificCarrierIds: " + this.mAllowedSpecificCarrierIds);
        }
        indentingPrintWriter.println("mCapabilitiesMatchCriteria: " + this.mCapabilitiesMatchCriteria);
        if (this.mRoamingMatchCriteria != 0) {
            indentingPrintWriter.println("mRoamingMatchCriteria: " + getMatchCriteriaString(this.mRoamingMatchCriteria));
        }
        if (this.mOpportunisticMatchCriteria != 0) {
            indentingPrintWriter.println("mOpportunisticMatchCriteria: " + getMatchCriteriaString(this.mOpportunisticMatchCriteria));
        }
    }

    public static final class Builder {
        private int mMeteredMatchCriteria = 0;
        private final java.util.Set<java.lang.String> mAllowedNetworkPlmnIds = new android.util.ArraySet();
        private final java.util.Set<java.lang.Integer> mAllowedSpecificCarrierIds = new android.util.ArraySet();
        private final java.util.Map<java.lang.Integer, java.lang.Integer> mCapabilitiesMatchCriteria = new java.util.HashMap();
        private int mRoamingMatchCriteria = 0;
        private int mOpportunisticMatchCriteria = 0;
        private int mMinEntryUpstreamBandwidthKbps = 0;
        private int mMinExitUpstreamBandwidthKbps = 0;
        private int mMinEntryDownstreamBandwidthKbps = 0;
        private int mMinExitDownstreamBandwidthKbps = 0;

        public Builder() {
            this.mCapabilitiesMatchCriteria.putAll(android.net.vcn.VcnCellUnderlyingNetworkTemplate.CAPABILITIES_MATCH_CRITERIA_DEFAULT);
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setMetered(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setMetered");
            this.mMeteredMatchCriteria = i;
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setOperatorPlmnIds(java.util.Set<java.lang.String> set) {
            android.net.vcn.VcnCellUnderlyingNetworkTemplate.validatePlmnIds(set);
            this.mAllowedNetworkPlmnIds.clear();
            this.mAllowedNetworkPlmnIds.addAll(set);
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setSimSpecificCarrierIds(java.util.Set<java.lang.Integer> set) {
            java.util.Objects.requireNonNull(set, "simSpecificCarrierIds is null");
            this.mAllowedSpecificCarrierIds.clear();
            this.mAllowedSpecificCarrierIds.addAll(set);
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setRoaming(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setRoaming");
            this.mRoamingMatchCriteria = i;
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setOpportunistic(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setOpportunistic");
            this.mOpportunisticMatchCriteria = i;
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setMinUpstreamBandwidthKbps(int i, int i2) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMinBandwidthKbps(i, i2);
            this.mMinEntryUpstreamBandwidthKbps = i;
            this.mMinExitUpstreamBandwidthKbps = i2;
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setMinDownstreamBandwidthKbps(int i, int i2) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMinBandwidthKbps(i, i2);
            this.mMinEntryDownstreamBandwidthKbps = i;
            this.mMinExitDownstreamBandwidthKbps = i2;
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setCbs(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setCbs");
            this.mCapabilitiesMatchCriteria.put(5, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setDun(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setDun");
            this.mCapabilitiesMatchCriteria.put(2, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setIms(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setIms");
            this.mCapabilitiesMatchCriteria.put(4, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setInternet(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setInternet");
            this.mCapabilitiesMatchCriteria.put(12, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setMms(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setMms");
            this.mCapabilitiesMatchCriteria.put(0, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate.Builder setRcs(int i) {
            android.net.vcn.VcnUnderlyingNetworkTemplate.validateMatchCriteria(i, "setRcs");
            this.mCapabilitiesMatchCriteria.put(8, java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.vcn.VcnCellUnderlyingNetworkTemplate build() {
            return new android.net.vcn.VcnCellUnderlyingNetworkTemplate(this.mMeteredMatchCriteria, this.mMinEntryUpstreamBandwidthKbps, this.mMinExitUpstreamBandwidthKbps, this.mMinEntryDownstreamBandwidthKbps, this.mMinExitDownstreamBandwidthKbps, this.mAllowedNetworkPlmnIds, this.mAllowedSpecificCarrierIds, this.mRoamingMatchCriteria, this.mOpportunisticMatchCriteria, this.mCapabilitiesMatchCriteria);
        }
    }
}
