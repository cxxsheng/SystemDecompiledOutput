package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class LongPolicySerializer extends com.android.server.devicepolicy.PolicySerializer<java.lang.Long> {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String TAG = "LongPolicySerializer";

    LongPolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.lang.Long l) throws java.io.IOException {
        java.util.Objects.requireNonNull(l);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, l.longValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    @android.annotation.Nullable
    public android.app.admin.LongPolicyValue readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            return new android.app.admin.LongPolicyValue(typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE));
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing Long policy value", e);
            return null;
        }
    }
}
