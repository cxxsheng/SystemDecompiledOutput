package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class IntentFilterPolicyKey extends android.app.admin.PolicyKey {
    public static final android.os.Parcelable.Creator<android.app.admin.IntentFilterPolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.IntentFilterPolicyKey>() { // from class: android.app.admin.IntentFilterPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.IntentFilterPolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.IntentFilterPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.IntentFilterPolicyKey[] newArray(int i) {
            return new android.app.admin.IntentFilterPolicyKey[i];
        }
    };
    private static final java.lang.String TAG = "IntentFilterPolicyKey";
    private static final java.lang.String TAG_INTENT_FILTER_ENTRY = "filter";
    private final android.content.IntentFilter mFilter;

    public IntentFilterPolicyKey(java.lang.String str, android.content.IntentFilter intentFilter) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxParcelableFieldsLength(intentFilter);
        }
        this.mFilter = (android.content.IntentFilter) java.util.Objects.requireNonNull(intentFilter);
    }

    public IntentFilterPolicyKey(java.lang.String str) {
        super(str);
        this.mFilter = null;
    }

    private IntentFilterPolicyKey(android.os.Parcel parcel) {
        super(parcel.readString());
        this.mFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
    }

    public android.content.IntentFilter getIntentFilter() {
        return this.mFilter;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.startTag(null, TAG_INTENT_FILTER_ENTRY);
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, TAG_INTENT_FILTER_ENTRY);
    }

    @Override // android.app.admin.PolicyKey
    public android.app.admin.IntentFilterPolicyKey readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.IntentFilterPolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), readIntentFilterFromXml(typedXmlPullParser));
    }

    private android.content.IntentFilter readIntentFilterFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            java.lang.String name = typedXmlPullParser.getName();
            if (name.equals(TAG_INTENT_FILTER_ENTRY)) {
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.readFromXml(typedXmlPullParser);
                return intentFilter;
            }
            android.util.Log.e(TAG, "Unknown tag: " + name);
        }
        android.util.Log.e(TAG, "Error parsing IntentFilterPolicyKey, IntentFilter not found");
        return null;
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(android.os.Bundle bundle) {
        bundle.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putParcelable(android.app.admin.PolicyUpdateReceiver.EXTRA_INTENT_FILTER, this.mFilter);
        bundle.putBundle(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_BUNDLE_KEY, bundle2);
    }

    @Override // android.app.admin.PolicyKey
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.IntentFilterPolicyKey intentFilterPolicyKey = (android.app.admin.IntentFilterPolicyKey) obj;
        if (java.util.Objects.equals(getIdentifier(), intentFilterPolicyKey.getIdentifier()) && android.content.IntentFilter.filterEquals(this.mFilter, intentFilterPolicyKey.mFilter)) {
            return true;
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return java.util.Objects.hash(getIdentifier());
    }

    public java.lang.String toString() {
        return "IntentFilterPolicyKey{mKey= " + getIdentifier() + "; mFilter= " + this.mFilter + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeTypedObject(this.mFilter, i);
    }
}
