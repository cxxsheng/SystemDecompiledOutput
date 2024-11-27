package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class LockTaskPolicySerializer extends com.android.server.devicepolicy.PolicySerializer<android.app.admin.LockTaskPolicy> {
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_PACKAGES = "packages";
    private static final java.lang.String ATTR_PACKAGES_SEPARATOR = ";";
    private static final java.lang.String TAG = "LockTaskPolicySerializer";

    LockTaskPolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.app.admin.LockTaskPolicy lockTaskPolicy) throws java.io.IOException {
        java.util.Objects.requireNonNull(lockTaskPolicy);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_PACKAGES, java.lang.String.join(ATTR_PACKAGES_SEPARATOR, lockTaskPolicy.getPackages()));
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_FLAGS, lockTaskPolicy.getFlags());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public android.app.admin.LockTaskPolicy readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_PACKAGES);
        if (attributeValue == null) {
            android.util.Log.e(TAG, "Error parsing LockTask policy value.");
            return null;
        }
        try {
            return new android.app.admin.LockTaskPolicy(java.util.Set.of((java.lang.Object[]) attributeValue.split(ATTR_PACKAGES_SEPARATOR)), typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_FLAGS));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing LockTask policy value", e);
            return null;
        }
    }
}
