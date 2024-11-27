package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class PackagePolicyKey extends android.app.admin.PolicyKey {
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    public static final android.os.Parcelable.Creator<android.app.admin.PackagePolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PackagePolicyKey>() { // from class: android.app.admin.PackagePolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PackagePolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePolicyKey[] newArray(int i) {
            return new android.app.admin.PackagePolicyKey[i];
        }
    };
    private final java.lang.String mPackageName;

    public PackagePolicyKey(java.lang.String str, java.lang.String str2) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxPackageNameLength(str2);
        }
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str2);
    }

    private PackagePolicyKey(android.os.Parcel parcel) {
        super(parcel.readString());
        this.mPackageName = parcel.readString();
    }

    public PackagePolicyKey(java.lang.String str) {
        super(str);
        this.mPackageName = null;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
    }

    @Override // android.app.admin.PolicyKey
    public android.app.admin.PackagePolicyKey readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.PackagePolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(android.os.Bundle bundle) {
        bundle.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_PACKAGE_NAME, this.mPackageName);
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
        android.app.admin.PackagePolicyKey packagePolicyKey = (android.app.admin.PackagePolicyKey) obj;
        if (java.util.Objects.equals(getIdentifier(), packagePolicyKey.getIdentifier()) && java.util.Objects.equals(this.mPackageName, packagePolicyKey.mPackageName)) {
            return true;
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return java.util.Objects.hash(getIdentifier(), this.mPackageName);
    }

    public java.lang.String toString() {
        return "PackagePolicyKey{mPolicyKey= " + getIdentifier() + "; mPackageName= " + this.mPackageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mPackageName);
    }
}
