package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class PolicyKey implements android.os.Parcelable {
    static final java.lang.String ATTR_POLICY_IDENTIFIER = "policy-identifier";
    static final java.lang.String TAG = "PolicyKey";
    private final java.lang.String mIdentifier;

    public abstract void writeToBundle(android.os.Bundle bundle);

    protected PolicyKey(java.lang.String str) {
        this.mIdentifier = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    public java.lang.String getIdentifier() {
        return this.mIdentifier;
    }

    public boolean hasSameIdentifierAs(android.app.admin.PolicyKey policyKey) {
        if (policyKey == null) {
            return false;
        }
        return this.mIdentifier.equals(policyKey.mIdentifier);
    }

    public static android.app.admin.PolicyKey readGenericPolicyKeyFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, ATTR_POLICY_IDENTIFIER);
        if (attributeValue == null) {
            android.util.Log.wtf(TAG, "Error parsing generic policy key, identifier is null.");
            return null;
        }
        return new android.app.admin.NoArgsPolicyKey(attributeValue);
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute(null, ATTR_POLICY_IDENTIFIER, this.mIdentifier);
    }

    public android.app.admin.PolicyKey readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return this;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mIdentifier, ((android.app.admin.PolicyKey) obj).mIdentifier);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mIdentifier);
    }
}
