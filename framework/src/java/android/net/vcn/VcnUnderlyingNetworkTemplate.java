package android.net.vcn;

/* loaded from: classes2.dex */
public abstract class VcnUnderlyingNetworkTemplate {
    static final int DEFAULT_METERED_MATCH_CRITERIA = 0;
    public static final int DEFAULT_MIN_BANDWIDTH_KBPS = 0;
    public static final int MATCH_ANY = 0;
    private static final android.util.SparseArray<java.lang.String> MATCH_CRITERIA_TO_STRING_MAP = new android.util.SparseArray<>();
    public static final int MATCH_FORBIDDEN = 2;
    public static final int MATCH_REQUIRED = 1;
    static final java.lang.String METERED_MATCH_KEY = "mMeteredMatchCriteria";
    static final java.lang.String MIN_ENTRY_DOWNSTREAM_BANDWIDTH_KBPS_KEY = "mMinEntryDownstreamBandwidthKbps";
    static final java.lang.String MIN_ENTRY_UPSTREAM_BANDWIDTH_KBPS_KEY = "mMinEntryUpstreamBandwidthKbps";
    static final java.lang.String MIN_EXIT_DOWNSTREAM_BANDWIDTH_KBPS_KEY = "mMinExitDownstreamBandwidthKbps";
    static final java.lang.String MIN_EXIT_UPSTREAM_BANDWIDTH_KBPS_KEY = "mMinExitUpstreamBandwidthKbps";
    static final int NETWORK_PRIORITY_TYPE_CELL = 2;
    private static final java.lang.String NETWORK_PRIORITY_TYPE_KEY = "mNetworkPriorityType";
    static final int NETWORK_PRIORITY_TYPE_WIFI = 1;
    private final int mMeteredMatchCriteria;
    private final int mMinEntryDownstreamBandwidthKbps;
    private final int mMinEntryUpstreamBandwidthKbps;
    private final int mMinExitDownstreamBandwidthKbps;
    private final int mMinExitUpstreamBandwidthKbps;
    private final int mNetworkPriorityType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MatchCriteria {
    }

    abstract void dumpTransportSpecificFields(com.android.internal.util.IndentingPrintWriter indentingPrintWriter);

    public abstract java.util.Map<java.lang.Integer, java.lang.Integer> getCapabilitiesMatchCriteria();

    static {
        MATCH_CRITERIA_TO_STRING_MAP.put(0, "MATCH_ANY");
        MATCH_CRITERIA_TO_STRING_MAP.put(1, "MATCH_REQUIRED");
        MATCH_CRITERIA_TO_STRING_MAP.put(2, "MATCH_FORBIDDEN");
    }

    VcnUnderlyingNetworkTemplate(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mNetworkPriorityType = i;
        this.mMeteredMatchCriteria = i2;
        this.mMinEntryUpstreamBandwidthKbps = i3;
        this.mMinExitUpstreamBandwidthKbps = i4;
        this.mMinEntryDownstreamBandwidthKbps = i5;
        this.mMinExitDownstreamBandwidthKbps = i6;
    }

    static void validateMatchCriteria(int i, java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(MATCH_CRITERIA_TO_STRING_MAP.contains(i), "Invalid matching criteria: " + i + " for " + str);
    }

    static void validateMinBandwidthKbps(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0, "Invalid minEntryBandwidth, must be >= 0");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0, "Invalid minExitBandwidth, must be >= 0");
        com.android.internal.util.Preconditions.checkArgument(i >= i2, "Minimum entry bandwidth must be >= exit bandwidth");
    }

    protected void validate() {
        validateMatchCriteria(this.mMeteredMatchCriteria, METERED_MATCH_KEY);
        validateMinBandwidthKbps(this.mMinEntryUpstreamBandwidthKbps, this.mMinExitUpstreamBandwidthKbps);
        validateMinBandwidthKbps(this.mMinEntryDownstreamBandwidthKbps, this.mMinExitDownstreamBandwidthKbps);
    }

    public static android.net.vcn.VcnUnderlyingNetworkTemplate fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        int i = persistableBundle.getInt(NETWORK_PRIORITY_TYPE_KEY);
        switch (i) {
            case 1:
                return android.net.vcn.VcnWifiUnderlyingNetworkTemplate.fromPersistableBundle(persistableBundle);
            case 2:
                return android.net.vcn.VcnCellUnderlyingNetworkTemplate.fromPersistableBundle(persistableBundle);
            default:
                throw new java.lang.IllegalArgumentException("Invalid networkPriorityType:" + i);
        }
    }

    android.os.PersistableBundle toPersistableBundle() {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(NETWORK_PRIORITY_TYPE_KEY, this.mNetworkPriorityType);
        persistableBundle.putInt(METERED_MATCH_KEY, this.mMeteredMatchCriteria);
        persistableBundle.putInt(MIN_ENTRY_UPSTREAM_BANDWIDTH_KBPS_KEY, this.mMinEntryUpstreamBandwidthKbps);
        persistableBundle.putInt(MIN_EXIT_UPSTREAM_BANDWIDTH_KBPS_KEY, this.mMinExitUpstreamBandwidthKbps);
        persistableBundle.putInt(MIN_ENTRY_DOWNSTREAM_BANDWIDTH_KBPS_KEY, this.mMinEntryDownstreamBandwidthKbps);
        persistableBundle.putInt(MIN_EXIT_DOWNSTREAM_BANDWIDTH_KBPS_KEY, this.mMinExitDownstreamBandwidthKbps);
        return persistableBundle;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNetworkPriorityType), java.lang.Integer.valueOf(this.mMeteredMatchCriteria), java.lang.Integer.valueOf(this.mMinEntryUpstreamBandwidthKbps), java.lang.Integer.valueOf(this.mMinExitUpstreamBandwidthKbps), java.lang.Integer.valueOf(this.mMinEntryDownstreamBandwidthKbps), java.lang.Integer.valueOf(this.mMinExitDownstreamBandwidthKbps));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.vcn.VcnUnderlyingNetworkTemplate)) {
            return false;
        }
        android.net.vcn.VcnUnderlyingNetworkTemplate vcnUnderlyingNetworkTemplate = (android.net.vcn.VcnUnderlyingNetworkTemplate) obj;
        return this.mNetworkPriorityType == vcnUnderlyingNetworkTemplate.mNetworkPriorityType && this.mMeteredMatchCriteria == vcnUnderlyingNetworkTemplate.mMeteredMatchCriteria && this.mMinEntryUpstreamBandwidthKbps == vcnUnderlyingNetworkTemplate.mMinEntryUpstreamBandwidthKbps && this.mMinExitUpstreamBandwidthKbps == vcnUnderlyingNetworkTemplate.mMinExitUpstreamBandwidthKbps && this.mMinEntryDownstreamBandwidthKbps == vcnUnderlyingNetworkTemplate.mMinEntryDownstreamBandwidthKbps && this.mMinExitDownstreamBandwidthKbps == vcnUnderlyingNetworkTemplate.mMinExitDownstreamBandwidthKbps;
    }

    static java.lang.String getNameString(android.util.SparseArray<java.lang.String> sparseArray, int i) {
        return sparseArray.get(i, "Invalid value " + i);
    }

    static java.lang.String getMatchCriteriaString(int i) {
        return getNameString(MATCH_CRITERIA_TO_STRING_MAP, i);
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println(getClass().getSimpleName() + ":");
        indentingPrintWriter.increaseIndent();
        if (this.mMeteredMatchCriteria != 0) {
            indentingPrintWriter.println("mMeteredMatchCriteria: " + getMatchCriteriaString(this.mMeteredMatchCriteria));
        }
        if (this.mMinEntryUpstreamBandwidthKbps != 0) {
            indentingPrintWriter.println("mMinEntryUpstreamBandwidthKbps: " + this.mMinEntryUpstreamBandwidthKbps);
        }
        if (this.mMinExitUpstreamBandwidthKbps != 0) {
            indentingPrintWriter.println("mMinExitUpstreamBandwidthKbps: " + this.mMinExitUpstreamBandwidthKbps);
        }
        if (this.mMinEntryDownstreamBandwidthKbps != 0) {
            indentingPrintWriter.println("mMinEntryDownstreamBandwidthKbps: " + this.mMinEntryDownstreamBandwidthKbps);
        }
        if (this.mMinExitDownstreamBandwidthKbps != 0) {
            indentingPrintWriter.println("mMinExitDownstreamBandwidthKbps: " + this.mMinExitDownstreamBandwidthKbps);
        }
        dumpTransportSpecificFields(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public int getMetered() {
        return this.mMeteredMatchCriteria;
    }

    public int getMinEntryUpstreamBandwidthKbps() {
        return this.mMinEntryUpstreamBandwidthKbps;
    }

    public int getMinExitUpstreamBandwidthKbps() {
        return this.mMinExitUpstreamBandwidthKbps;
    }

    public int getMinEntryDownstreamBandwidthKbps() {
        return this.mMinEntryDownstreamBandwidthKbps;
    }

    public int getMinExitDownstreamBandwidthKbps() {
        return this.mMinExitDownstreamBandwidthKbps;
    }
}
