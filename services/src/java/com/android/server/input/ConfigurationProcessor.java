package com.android.server.input;

/* loaded from: classes2.dex */
class ConfigurationProcessor {
    private static final java.lang.String TAG = "ConfigurationProcessor";

    ConfigurationProcessor() {
    }

    static java.util.List<java.lang.String> processExcludedDeviceNames(java.io.InputStream inputStream) throws java.lang.Exception {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, "devices");
        while (true) {
            com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
            if ("device".equals(resolvePullParser.getName())) {
                java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "name");
                if (attributeValue != null) {
                    arrayList.add(attributeValue);
                }
            } else {
                return arrayList;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.util.Map<java.lang.String, java.lang.Integer> processInputPortAssociations(java.io.InputStream inputStream) throws java.lang.Exception {
        java.util.HashMap hashMap = new java.util.HashMap();
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, "ports");
        while (true) {
            com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
            if ("port".equals(resolvePullParser.getName())) {
                java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "input");
                java.lang.String attributeValue2 = resolvePullParser.getAttributeValue((java.lang.String) null, "display");
                if (android.text.TextUtils.isEmpty(attributeValue) || android.text.TextUtils.isEmpty(attributeValue2)) {
                    android.util.Slog.wtf(TAG, "Ignoring incomplete entry");
                } else {
                    try {
                        hashMap.put(attributeValue, java.lang.Integer.valueOf(java.lang.Integer.parseUnsignedInt(attributeValue2)));
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.wtf(TAG, "Display port should be an integer");
                    }
                }
            } else {
                return hashMap;
            }
        }
    }
}
