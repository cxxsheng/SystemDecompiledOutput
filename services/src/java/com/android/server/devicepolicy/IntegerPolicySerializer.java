package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class IntegerPolicySerializer extends com.android.server.devicepolicy.PolicySerializer<java.lang.Integer> {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String TAG = "IntegerPolicySerializer";

    IntegerPolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.lang.Integer num) throws java.io.IOException {
        java.util.Objects.requireNonNull(num);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    @android.annotation.Nullable
    public android.app.admin.IntegerPolicyValue readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            return new android.app.admin.IntegerPolicyValue(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing Integer policy value", e);
            return null;
        }
    }
}
