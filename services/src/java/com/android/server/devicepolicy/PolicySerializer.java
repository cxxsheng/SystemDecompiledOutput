package com.android.server.devicepolicy;

/* loaded from: classes.dex */
abstract class PolicySerializer<V> {
    PolicySerializer() {
    }

    abstract android.app.admin.PolicyValue<V> readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser);

    abstract void saveToXml(android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull V v) throws java.io.IOException;
}
