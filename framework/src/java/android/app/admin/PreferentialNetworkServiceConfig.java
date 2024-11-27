package android.app.admin;

/* loaded from: classes.dex */
public final class PreferentialNetworkServiceConfig implements android.os.Parcelable {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String LOG_TAG = "PreferentialNetworkServiceConfig";
    public static final int PREFERENTIAL_NETWORK_ID_1 = 1;
    public static final int PREFERENTIAL_NETWORK_ID_2 = 2;
    public static final int PREFERENTIAL_NETWORK_ID_3 = 3;
    public static final int PREFERENTIAL_NETWORK_ID_4 = 4;
    public static final int PREFERENTIAL_NETWORK_ID_5 = 5;
    private static final java.lang.String TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION = "allow_fallback_to_default_connection";
    private static final java.lang.String TAG_BLOCK_NON_MATCHING_NETWORKS = "block_non_matching_networks";
    private static final java.lang.String TAG_CONFIG_ENABLED = "preferential_network_service_config_enabled";
    private static final java.lang.String TAG_EXCLUDED_UIDS = "excluded_uids";
    private static final java.lang.String TAG_INCLUDED_UIDS = "included_uids";
    private static final java.lang.String TAG_NETWORK_ID = "preferential_network_service_network_id";
    private static final java.lang.String TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG = "preferential_network_service_config";
    private static final java.lang.String TAG_UID = "uid";
    final boolean mAllowFallbackToDefaultConnection;
    final int[] mExcludedUids;
    final int[] mIncludedUids;
    final boolean mIsEnabled;
    final int mNetworkId;
    final boolean mShouldBlockNonMatchingNetworks;
    public static final android.app.admin.PreferentialNetworkServiceConfig DEFAULT = new android.app.admin.PreferentialNetworkServiceConfig.Builder().build();
    public static final android.os.Parcelable.Creator<android.app.admin.PreferentialNetworkServiceConfig> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PreferentialNetworkServiceConfig>() { // from class: android.app.admin.PreferentialNetworkServiceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PreferentialNetworkServiceConfig[] newArray(int i) {
            return new android.app.admin.PreferentialNetworkServiceConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PreferentialNetworkServiceConfig createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PreferentialNetworkServiceConfig(parcel);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PreferentialNetworkPreferenceId {
    }

    private PreferentialNetworkServiceConfig(boolean z, boolean z2, boolean z3, int[] iArr, int[] iArr2, int i) {
        this.mIsEnabled = z;
        this.mAllowFallbackToDefaultConnection = z2;
        this.mShouldBlockNonMatchingNetworks = z3;
        this.mIncludedUids = iArr;
        this.mExcludedUids = iArr2;
        this.mNetworkId = i;
    }

    private PreferentialNetworkServiceConfig(android.os.Parcel parcel) {
        this.mIsEnabled = parcel.readBoolean();
        this.mAllowFallbackToDefaultConnection = parcel.readBoolean();
        this.mShouldBlockNonMatchingNetworks = parcel.readBoolean();
        this.mNetworkId = parcel.readInt();
        this.mIncludedUids = parcel.createIntArray();
        this.mExcludedUids = parcel.createIntArray();
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isFallbackToDefaultConnectionAllowed() {
        return this.mAllowFallbackToDefaultConnection;
    }

    public boolean shouldBlockNonMatchingNetworks() {
        return this.mShouldBlockNonMatchingNetworks;
    }

    public int[] getIncludedUids() {
        return this.mIncludedUids;
    }

    public int[] getExcludedUids() {
        return this.mExcludedUids;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    public java.lang.String toString() {
        return "PreferentialNetworkServiceConfig{mIsEnabled=" + isEnabled() + "mAllowFallbackToDefaultConnection=" + isFallbackToDefaultConnectionAllowed() + "mBlockNonMatchingNetworks=" + shouldBlockNonMatchingNetworks() + "mIncludedUids=" + java.util.Arrays.toString(this.mIncludedUids) + "mExcludedUids=" + java.util.Arrays.toString(this.mExcludedUids) + "mNetworkId=" + this.mNetworkId + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.PreferentialNetworkServiceConfig preferentialNetworkServiceConfig = (android.app.admin.PreferentialNetworkServiceConfig) obj;
        if (this.mIsEnabled == preferentialNetworkServiceConfig.mIsEnabled && this.mAllowFallbackToDefaultConnection == preferentialNetworkServiceConfig.mAllowFallbackToDefaultConnection && this.mShouldBlockNonMatchingNetworks == preferentialNetworkServiceConfig.mShouldBlockNonMatchingNetworks && this.mNetworkId == preferentialNetworkServiceConfig.mNetworkId && java.util.Arrays.equals(this.mIncludedUids, preferentialNetworkServiceConfig.mIncludedUids) && java.util.Arrays.equals(this.mExcludedUids, preferentialNetworkServiceConfig.mExcludedUids)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsEnabled), java.lang.Boolean.valueOf(this.mAllowFallbackToDefaultConnection), java.lang.Boolean.valueOf(this.mShouldBlockNonMatchingNetworks), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mIncludedUids)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mExcludedUids)), java.lang.Integer.valueOf(this.mNetworkId));
    }

    public static final class Builder {
        boolean mIsEnabled = false;
        int mNetworkId = 0;
        boolean mAllowFallbackToDefaultConnection = true;
        boolean mShouldBlockNonMatchingNetworks = false;
        int[] mIncludedUids = new int[0];
        int[] mExcludedUids = new int[0];

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setFallbackToDefaultConnectionAllowed(boolean z) {
            this.mAllowFallbackToDefaultConnection = z;
            return this;
        }

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setShouldBlockNonMatchingNetworks(boolean z) {
            this.mShouldBlockNonMatchingNetworks = z;
            return this;
        }

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setIncludedUids(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            this.mIncludedUids = iArr;
            return this;
        }

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setExcludedUids(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            this.mExcludedUids = iArr;
            return this;
        }

        public android.app.admin.PreferentialNetworkServiceConfig build() {
            if (this.mIncludedUids.length > 0 && this.mExcludedUids.length > 0) {
                throw new java.lang.IllegalStateException("Both includedUids and excludedUids cannot be nonempty");
            }
            if (this.mShouldBlockNonMatchingNetworks && this.mAllowFallbackToDefaultConnection) {
                throw new java.lang.IllegalStateException("A config cannot both allow fallback and block non-matching networks");
            }
            return new android.app.admin.PreferentialNetworkServiceConfig(this.mIsEnabled, this.mAllowFallbackToDefaultConnection, this.mShouldBlockNonMatchingNetworks, this.mIncludedUids, this.mExcludedUids, this.mNetworkId);
        }

        public android.app.admin.PreferentialNetworkServiceConfig.Builder setNetworkId(int i) {
            if (i < 1 || i > 5) {
                throw new java.lang.IllegalArgumentException("Invalid preference identifier");
            }
            this.mNetworkId = i;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEnabled);
        parcel.writeBoolean(this.mAllowFallbackToDefaultConnection);
        parcel.writeBoolean(this.mShouldBlockNonMatchingNetworks);
        parcel.writeInt(this.mNetworkId);
        parcel.writeIntArray(this.mIncludedUids);
        parcel.writeIntArray(this.mExcludedUids);
    }

    private void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, int i) throws java.io.IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeInt(null, "value", i);
        typedXmlSerializer.endTag(null, str);
    }

    private void writeAttributeValueToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, boolean z) throws java.io.IOException {
        typedXmlSerializer.startTag(null, str);
        typedXmlSerializer.attributeBoolean(null, "value", z);
        typedXmlSerializer.endTag(null, str);
    }

    private void writeAttributeValuesToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2, java.util.Collection<java.lang.String> collection) throws java.io.IOException {
        typedXmlSerializer.startTag(null, str);
        for (java.lang.String str3 : collection) {
            typedXmlSerializer.startTag(null, str2);
            typedXmlSerializer.attribute(null, "value", str3);
            typedXmlSerializer.endTag(null, str2);
        }
        typedXmlSerializer.endTag(null, str);
    }

    private static void readAttributeValues(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.util.Collection<java.lang.String> collection) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        collection.clear();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        java.lang.String name = typedXmlPullParser.getName();
                        if (str.equals(name)) {
                            collection.add(typedXmlPullParser.getAttributeValue(null, "value"));
                        } else {
                            android.util.Log.e(LOG_TAG, "Expected tag " + str + " but found " + name);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private java.util.List<java.lang.String> intArrayToStringList(int[] iArr) {
        return (java.util.List) java.util.Arrays.stream(iArr).mapToObj(new java.util.function.IntFunction() { // from class: android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                return java.lang.String.valueOf(i);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    private static int[] readStringListToIntArray(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        readAttributeValues(typedXmlPullParser, str, arrayList);
        return arrayList.stream().map(new java.util.function.Function() { // from class: android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer valueOf;
                valueOf = java.lang.Integer.valueOf(java.lang.Integer.parseInt((java.lang.String) obj));
                return valueOf;
            }
        }).mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    public static android.app.admin.PreferentialNetworkServiceConfig getPreferentialNetworkServiceConfig(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        android.app.admin.PreferentialNetworkServiceConfig.Builder builder = new android.app.admin.PreferentialNetworkServiceConfig.Builder();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (TAG_CONFIG_ENABLED.equals(name)) {
                    builder.setEnabled(typedXmlPullParser.getAttributeBoolean(null, "value", false));
                } else if (TAG_NETWORK_ID.equals(name)) {
                    int attributeInt = typedXmlPullParser.getAttributeInt(null, "value", 0);
                    if (attributeInt != 0) {
                        builder.setNetworkId(attributeInt);
                    }
                } else if (TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION.equals(name)) {
                    builder.setFallbackToDefaultConnectionAllowed(typedXmlPullParser.getAttributeBoolean(null, "value", true));
                } else if (TAG_BLOCK_NON_MATCHING_NETWORKS.equals(name)) {
                    builder.setShouldBlockNonMatchingNetworks(typedXmlPullParser.getAttributeBoolean(null, "value", false));
                } else if (TAG_INCLUDED_UIDS.equals(name)) {
                    builder.setIncludedUids(readStringListToIntArray(typedXmlPullParser, "uid"));
                } else if (TAG_EXCLUDED_UIDS.equals(name)) {
                    builder.setExcludedUids(readStringListToIntArray(typedXmlPullParser, "uid"));
                } else {
                    android.util.Log.w(LOG_TAG, "Unknown tag under " + str + ": " + name);
                }
            }
        }
        return builder.build();
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG);
        writeAttributeValueToXml(typedXmlSerializer, TAG_CONFIG_ENABLED, isEnabled());
        writeAttributeValueToXml(typedXmlSerializer, TAG_NETWORK_ID, getNetworkId());
        writeAttributeValueToXml(typedXmlSerializer, TAG_ALLOW_FALLBACK_TO_DEFAULT_CONNECTION, isFallbackToDefaultConnectionAllowed());
        writeAttributeValueToXml(typedXmlSerializer, TAG_BLOCK_NON_MATCHING_NETWORKS, shouldBlockNonMatchingNetworks());
        writeAttributeValuesToXml(typedXmlSerializer, TAG_INCLUDED_UIDS, "uid", intArrayToStringList(getIncludedUids()));
        writeAttributeValuesToXml(typedXmlSerializer, TAG_EXCLUDED_UIDS, "uid", intArrayToStringList(getExcludedUids()));
        typedXmlSerializer.endTag(null, TAG_PREFERENTIAL_NETWORK_SERVICE_CONFIG);
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("networkId=");
        indentingPrintWriter.println(this.mNetworkId);
        indentingPrintWriter.print("isEnabled=");
        indentingPrintWriter.println(this.mIsEnabled);
        indentingPrintWriter.print("allowFallbackToDefaultConnection=");
        indentingPrintWriter.println(this.mAllowFallbackToDefaultConnection);
        indentingPrintWriter.print("blockNonMatchingNetworks=");
        indentingPrintWriter.println(this.mShouldBlockNonMatchingNetworks);
        indentingPrintWriter.print("includedUids=");
        indentingPrintWriter.println(java.util.Arrays.toString(this.mIncludedUids));
        indentingPrintWriter.print("excludedUids=");
        indentingPrintWriter.println(java.util.Arrays.toString(this.mExcludedUids));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
