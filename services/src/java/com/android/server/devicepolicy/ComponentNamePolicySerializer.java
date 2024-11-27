package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class ComponentNamePolicySerializer extends com.android.server.devicepolicy.PolicySerializer<android.content.ComponentName> {
    private static final java.lang.String ATTR_CLASS_NAME = "class-name";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String TAG = "ComponentNamePolicySerializer";

    ComponentNamePolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.content.ComponentName componentName) throws java.io.IOException {
        java.util.Objects.requireNonNull(componentName);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_PACKAGE_NAME, componentName.getPackageName());
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_CLASS_NAME, componentName.getClassName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    @android.annotation.Nullable
    public android.app.admin.ComponentNamePolicyValue readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_PACKAGE_NAME);
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_CLASS_NAME);
        if (attributeValue == null || attributeValue2 == null) {
            android.util.Log.e(TAG, "Error parsing ComponentName policy.");
            return null;
        }
        return new android.app.admin.ComponentNamePolicyValue(new android.content.ComponentName(attributeValue, attributeValue2));
    }
}
