package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public final class XmlValidator {
    public static void checkStartTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException {
        checkStartTag(typedXmlPullParser);
        checkParserCondition(str.equals(typedXmlPullParser.getName()), "Unexpected start tag found %s, expected %s", typedXmlPullParser.getName(), str);
    }

    public static void checkStartTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException {
        try {
            checkParserCondition(typedXmlPullParser.getEventType() == 2, "Expected start tag, got " + typedXmlPullParser.getEventType(), new java.lang.Object[0]);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException(typedXmlPullParser.getName(), e);
        }
    }

    public static void checkTagHasNoUnexpectedAttributes(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String... strArr) throws com.android.internal.vibrator.persistence.XmlParserException {
        if (strArr == null || strArr.length == 0) {
            checkParserCondition(typedXmlPullParser.getAttributeCount() == 0, "Unexpected attributes in tag %s, expected no attributes", typedXmlPullParser.getName());
            return;
        }
        java.lang.String name = typedXmlPullParser.getName();
        int attributeCount = typedXmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            java.lang.String attributeName = typedXmlPullParser.getAttributeName(i);
            checkParserCondition(com.android.internal.util.ArrayUtils.contains(strArr, attributeName), "Unexpected attribute %s found in tag %s", attributeName, name);
        }
    }

    public static <T> void checkSerializedVibration(com.android.internal.vibrator.persistence.XmlSerializedVibration<T> xmlSerializedVibration, T t) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        java.lang.Object requireNonNull = java.util.Objects.requireNonNull(xmlSerializedVibration.deserialize());
        checkSerializerCondition(java.util.Objects.equals(t, requireNonNull), "Unexpected serialized vibration %s: found deserialization %s, expected %s", xmlSerializedVibration, requireNonNull, t);
    }

    public static void checkSerializerCondition(boolean z, java.lang.String str, java.lang.Object... objArr) throws com.android.internal.vibrator.persistence.XmlSerializerException {
        if (!z) {
            throw new com.android.internal.vibrator.persistence.XmlSerializerException(android.text.TextUtils.formatSimple(str, objArr));
        }
    }

    public static void checkParserCondition(boolean z, java.lang.String str, java.lang.Object... objArr) throws com.android.internal.vibrator.persistence.XmlParserException {
        if (!z) {
            throw new com.android.internal.vibrator.persistence.XmlParserException(android.text.TextUtils.formatSimple(str, objArr));
        }
    }
}
