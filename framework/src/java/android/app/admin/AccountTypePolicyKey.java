package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AccountTypePolicyKey extends android.app.admin.PolicyKey {
    private static final java.lang.String ATTR_ACCOUNT_TYPE = "account-type";
    public static final android.os.Parcelable.Creator<android.app.admin.AccountTypePolicyKey> CREATOR = new android.os.Parcelable.Creator<android.app.admin.AccountTypePolicyKey>() { // from class: android.app.admin.AccountTypePolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.AccountTypePolicyKey createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.AccountTypePolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.AccountTypePolicyKey[] newArray(int i) {
            return new android.app.admin.AccountTypePolicyKey[i];
        }
    };
    private final java.lang.String mAccountType;

    public AccountTypePolicyKey(java.lang.String str, java.lang.String str2) {
        super(str);
        if (android.app.admin.flags.Flags.devicePolicySizeTrackingEnabled()) {
            android.app.admin.PolicySizeVerifier.enforceMaxStringLength(str2, "accountType");
        }
        this.mAccountType = (java.lang.String) java.util.Objects.requireNonNull(str2);
    }

    private AccountTypePolicyKey(android.os.Parcel parcel) {
        super(parcel.readString());
        this.mAccountType = parcel.readString();
    }

    public AccountTypePolicyKey(java.lang.String str) {
        super(str);
        this.mAccountType = null;
    }

    public java.lang.String getAccountType() {
        return this.mAccountType;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_ACCOUNT_TYPE, this.mAccountType);
    }

    @Override // android.app.admin.PolicyKey
    public android.app.admin.AccountTypePolicyKey readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return new android.app.admin.AccountTypePolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_ACCOUNT_TYPE));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(android.os.Bundle bundle) {
        bundle.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putString(android.app.admin.PolicyUpdateReceiver.EXTRA_ACCOUNT_TYPE, this.mAccountType);
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
        android.app.admin.AccountTypePolicyKey accountTypePolicyKey = (android.app.admin.AccountTypePolicyKey) obj;
        if (java.util.Objects.equals(getIdentifier(), accountTypePolicyKey.getIdentifier()) && java.util.Objects.equals(this.mAccountType, accountTypePolicyKey.mAccountType)) {
            return true;
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return java.util.Objects.hash(getIdentifier(), this.mAccountType);
    }

    public java.lang.String toString() {
        return "AccountTypePolicyKey{mPolicyKey= " + getIdentifier() + "; mAccountType= " + this.mAccountType + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mAccountType);
    }
}
