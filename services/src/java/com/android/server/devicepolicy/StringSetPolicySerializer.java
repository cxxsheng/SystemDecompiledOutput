package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class StringSetPolicySerializer extends com.android.server.devicepolicy.PolicySerializer<java.util.Set<java.lang.String>> {
    private static final java.lang.String ATTR_VALUES = "strings";
    private static final java.lang.String ATTR_VALUES_SEPARATOR = ";";

    StringSetPolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.util.Set<java.lang.String> set) throws java.io.IOException {
        java.util.Objects.requireNonNull(set);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUES, java.lang.String.join(ATTR_VALUES_SEPARATOR, set));
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    @android.annotation.Nullable
    android.app.admin.PolicyValue<java.util.Set<java.lang.String>> readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUES);
        if (attributeValue == null) {
            android.util.Log.e("DevicePolicyEngine", "Error parsing StringSet policy value.");
            return null;
        }
        return new android.app.admin.StringSetPolicyValue(java.util.Set.of((java.lang.Object[]) attributeValue.split(ATTR_VALUES_SEPARATOR)));
    }
}
