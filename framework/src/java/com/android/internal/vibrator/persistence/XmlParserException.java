package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public final class XmlParserException extends java.lang.Exception {
    public static com.android.internal.vibrator.persistence.XmlParserException createFromPullParserException(java.lang.String str, org.xmlpull.v1.XmlPullParserException xmlPullParserException) {
        return new com.android.internal.vibrator.persistence.XmlParserException("Error parsing " + str, xmlPullParserException);
    }

    public static com.android.internal.vibrator.persistence.XmlParserException createFromPullParserException(java.lang.String str, java.lang.String str2, java.lang.String str3, org.xmlpull.v1.XmlPullParserException xmlPullParserException) {
        return new com.android.internal.vibrator.persistence.XmlParserException(android.text.TextUtils.formatSimple("Error parsing %s = %s in tag %s", str2, str3, str), xmlPullParserException);
    }

    public XmlParserException(java.lang.String str) {
        super(str);
    }

    public XmlParserException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
