package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class PackagePermissionPolicyKey extends android.app.admin.PolicyKey {
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String ATTR_PERMISSION_NAME = "permission-name";
    public static final android.os.Parcelable.Creator<android.app.admin.PackagePermissionPolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PackagePermissionPolicyKey>() { // from class: android.app.admin.PackagePermissionPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePermissionPolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PackagePermissionPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PackagePermissionPolicyKey[] newArray(int i) {
            return new android.app.admin.PackagePermissionPolicyKey[i];
        }
    };
    private final java.lang.String mPackageName;
    private final java.lang.String mPermissionName;

    public PackagePermissionPolicyKey(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxPackageNameLength(str2);
            android.app.admin.PolicySizeVerifier.enforceMaxStringLength(str3, "permissionName");
        }
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mPermissionName = (java.lang.String) java.util.Objects.requireNonNull(str3);
    }

    public PackagePermissionPolicyKey(java.lang.String str) {
        super(str);
        this.mPackageName = null;
        this.mPermissionName = null;
    }

    private PackagePermissionPolicyKey(android.os.Parcel parcel) {
        super(parcel.readString());
        this.mPackageName = parcel.readString();
        this.mPermissionName = parcel.readString();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getPermissionName() {
        return this.mPermissionName;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
        typedXmlSerializer.attribute(null, ATTR_PERMISSION_NAME, this.mPermissionName);
    }

    @Override // android.app.admin.PolicyKey
    public android.app.admin.PackagePermissionPolicyKey readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.PackagePermissionPolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME), typedXmlPullParser.getAttributeValue(null, ATTR_PERMISSION_NAME));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(android.os.Bundle bundle) {
        bundle.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_PACKAGE_NAME, this.mPackageName);
        bundle2.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_PERMISSION_NAME, this.mPermissionName);
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
        android.app.admin.PackagePermissionPolicyKey packagePermissionPolicyKey = (android.app.admin.PackagePermissionPolicyKey) obj;
        if (java.util.Objects.equals(getIdentifier(), packagePermissionPolicyKey.getIdentifier()) && java.util.Objects.equals(this.mPackageName, packagePermissionPolicyKey.mPackageName) && java.util.Objects.equals(this.mPermissionName, packagePermissionPolicyKey.mPermissionName)) {
            return true;
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return java.util.Objects.hash(getIdentifier(), this.mPackageName, this.mPermissionName);
    }

    public java.lang.String toString() {
        return "PackagePermissionPolicyKey{mIdentifier= " + getIdentifier() + "; mPackageName= " + this.mPackageName + "; mPermissionName= " + this.mPermissionName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mPermissionName);
    }
}
