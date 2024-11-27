package com.android.modules.utils;

/* loaded from: classes5.dex */
public interface TypedXmlPullParser extends org.xmlpull.v1.XmlPullParser {
    boolean getAttributeBoolean(int i) throws org.xmlpull.v1.XmlPullParserException;

    byte[] getAttributeBytesBase64(int i) throws org.xmlpull.v1.XmlPullParserException;

    byte[] getAttributeBytesHex(int i) throws org.xmlpull.v1.XmlPullParserException;

    double getAttributeDouble(int i) throws org.xmlpull.v1.XmlPullParserException;

    float getAttributeFloat(int i) throws org.xmlpull.v1.XmlPullParserException;

    int getAttributeInt(int i) throws org.xmlpull.v1.XmlPullParserException;

    int getAttributeIntHex(int i) throws org.xmlpull.v1.XmlPullParserException;

    long getAttributeLong(int i) throws org.xmlpull.v1.XmlPullParserException;

    long getAttributeLongHex(int i) throws org.xmlpull.v1.XmlPullParserException;

    default int getAttributeIndex(java.lang.String str, java.lang.String str2) {
        boolean z = str == null;
        int attributeCount = getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ((z || str.equals(getAttributeNamespace(i))) && str2.equals(getAttributeName(i))) {
                return i;
            }
        }
        return -1;
    }

    default int getAttributeIndexOrThrow(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            throw new org.xmlpull.v1.XmlPullParserException("Missing attribute " + str2);
        }
        return attributeIndex;
    }

    default byte[] getAttributeBytesHex(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeBytesHex(getAttributeIndexOrThrow(str, str2));
    }

    default byte[] getAttributeBytesBase64(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeBytesBase64(getAttributeIndexOrThrow(str, str2));
    }

    default int getAttributeInt(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeInt(getAttributeIndexOrThrow(str, str2));
    }

    default int getAttributeIntHex(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeIntHex(getAttributeIndexOrThrow(str, str2));
    }

    default long getAttributeLong(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeLong(getAttributeIndexOrThrow(str, str2));
    }

    default long getAttributeLongHex(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeLongHex(getAttributeIndexOrThrow(str, str2));
    }

    default float getAttributeFloat(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeFloat(getAttributeIndexOrThrow(str, str2));
    }

    default double getAttributeDouble(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeDouble(getAttributeIndexOrThrow(str, str2));
    }

    default boolean getAttributeBoolean(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        return getAttributeBoolean(getAttributeIndexOrThrow(str, str2));
    }

    default byte[] getAttributeBytesHex(java.lang.String str, java.lang.String str2, byte[] bArr) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return bArr;
        }
        try {
            return getAttributeBytesHex(attributeIndex);
        } catch (java.lang.Exception e) {
            return bArr;
        }
    }

    default byte[] getAttributeBytesBase64(java.lang.String str, java.lang.String str2, byte[] bArr) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return bArr;
        }
        try {
            return getAttributeBytesBase64(attributeIndex);
        } catch (java.lang.Exception e) {
            return bArr;
        }
    }

    default int getAttributeInt(java.lang.String str, java.lang.String str2, int i) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return i;
        }
        try {
            return getAttributeInt(attributeIndex);
        } catch (java.lang.Exception e) {
            return i;
        }
    }

    default int getAttributeIntHex(java.lang.String str, java.lang.String str2, int i) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return i;
        }
        try {
            return getAttributeIntHex(attributeIndex);
        } catch (java.lang.Exception e) {
            return i;
        }
    }

    default long getAttributeLong(java.lang.String str, java.lang.String str2, long j) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return j;
        }
        try {
            return getAttributeLong(attributeIndex);
        } catch (java.lang.Exception e) {
            return j;
        }
    }

    default long getAttributeLongHex(java.lang.String str, java.lang.String str2, long j) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return j;
        }
        try {
            return getAttributeLongHex(attributeIndex);
        } catch (java.lang.Exception e) {
            return j;
        }
    }

    default float getAttributeFloat(java.lang.String str, java.lang.String str2, float f) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return f;
        }
        try {
            return getAttributeFloat(attributeIndex);
        } catch (java.lang.Exception e) {
            return f;
        }
    }

    default double getAttributeDouble(java.lang.String str, java.lang.String str2, double d) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return d;
        }
        try {
            return getAttributeDouble(attributeIndex);
        } catch (java.lang.Exception e) {
            return d;
        }
    }

    default boolean getAttributeBoolean(java.lang.String str, java.lang.String str2, boolean z) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex == -1) {
            return z;
        }
        try {
            return getAttributeBoolean(attributeIndex);
        } catch (java.lang.Exception e) {
            return z;
        }
    }
}
