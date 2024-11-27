package com.android.modules.utils;

/* loaded from: classes5.dex */
public interface TypedXmlSerializer extends org.xmlpull.v1.XmlSerializer {
    org.xmlpull.v1.XmlSerializer attributeBoolean(java.lang.String str, java.lang.String str2, boolean z) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeBytesBase64(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeBytesHex(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeDouble(java.lang.String str, java.lang.String str2, double d) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeFloat(java.lang.String str, java.lang.String str2, float f) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeInt(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeIntHex(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeInterned(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeLong(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException;

    org.xmlpull.v1.XmlSerializer attributeLongHex(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException;
}
