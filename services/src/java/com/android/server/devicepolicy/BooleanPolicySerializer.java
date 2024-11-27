package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class BooleanPolicySerializer extends com.android.server.devicepolicy.PolicySerializer<java.lang.Boolean> {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String TAG = "BooleanPolicySerializer";

    BooleanPolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.lang.Boolean bool) throws java.io.IOException {
        java.util.Objects.requireNonNull(bool);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    @android.annotation.Nullable
    public android.app.admin.BooleanPolicyValue readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            return new android.app.admin.BooleanPolicyValue(typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing Boolean policy value", e);
            return null;
        }
    }
}
