package com.android.internal.vibrator.persistence;

/* loaded from: classes5.dex */
public final class XmlReader {
    public static void readDocumentStartTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        readDocumentStart(typedXmlPullParser);
        java.lang.String name = typedXmlPullParser.getName();
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(str.equals(name), "Unexpected root tag found %s, expected %s", name, str);
    }

    public static void readDocumentStart(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        try {
            int eventType = typedXmlPullParser.getEventType();
            com.android.internal.util.Preconditions.checkArgument(eventType == 0, "Unexpected type, expected %d", java.lang.Integer.valueOf(eventType));
            typedXmlPullParser.nextTag();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException("document start tag", e);
        }
    }

    public static void readDocumentEndTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        try {
            boolean z = true;
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(typedXmlPullParser.getEventType() == 3, "Unexpected element at document end, expected end of root tag", new java.lang.Object[0]);
            int next = typedXmlPullParser.next();
            if (next == 4 && typedXmlPullParser.isWhitespace()) {
                next = typedXmlPullParser.next();
            }
            if (next != 1) {
                z = false;
            }
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(z, "Unexpected tag found %s, expected document end", typedXmlPullParser.getName());
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException("document end tag", e);
        }
    }

    public static boolean readNextTagWithin(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        try {
            if (typedXmlPullParser.getEventType() == 3 && typedXmlPullParser.getDepth() == i) {
                return false;
            }
            int nextTag = typedXmlPullParser.nextTag();
            if (nextTag == 2 && typedXmlPullParser.getDepth() == i + 1) {
                return true;
            }
            com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(nextTag == 3 && typedXmlPullParser.getDepth() == i, "Unexpected tag found %s, expected end tag at depth %d", typedXmlPullParser.getName(), java.lang.Integer.valueOf(i));
            return false;
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException(typedXmlPullParser.getName(), e);
        }
    }

    public static void readEndTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        readEndTag(typedXmlPullParser, typedXmlPullParser.getName(), typedXmlPullParser.getDepth());
    }

    public static void readEndTag(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) throws com.android.internal.vibrator.persistence.XmlParserException, java.io.IOException {
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(!readNextTagWithin(typedXmlPullParser, i), "Unexpected nested tag %s found in tag %s", typedXmlPullParser.getName(), str);
    }

    public static int readAttributeIntNonNegative(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) throws com.android.internal.vibrator.persistence.XmlParserException {
        if (typedXmlPullParser.getAttributeIndex(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str) < 0) {
            return i;
        }
        return readAttributeIntNonNegative(typedXmlPullParser, str);
    }

    public static int readAttributeIntNonNegative(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException {
        java.lang.String name = typedXmlPullParser.getName();
        int readAttributeInt = readAttributeInt(typedXmlPullParser, str);
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(readAttributeInt >= 0, "Unexpected %s = %d in tag %s, expected %s >= 0", str, java.lang.Integer.valueOf(readAttributeInt), name, str);
        return readAttributeInt;
    }

    public static int readAttributeIntInRange(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i, int i2) throws com.android.internal.vibrator.persistence.XmlParserException {
        java.lang.String name = typedXmlPullParser.getName();
        int readAttributeInt = readAttributeInt(typedXmlPullParser, str);
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(readAttributeInt >= i && readAttributeInt <= i2, "Unexpected %s = %d in tag %s, expected %s in [%d, %d]", str, java.lang.Integer.valueOf(readAttributeInt), name, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        return readAttributeInt;
    }

    public static float readAttributeFloatInRange(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, float f, float f2, float f3) throws com.android.internal.vibrator.persistence.XmlParserException {
        if (typedXmlPullParser.getAttributeIndex(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str) < 0) {
            return f3;
        }
        java.lang.String name = typedXmlPullParser.getName();
        float readAttributeFloat = readAttributeFloat(typedXmlPullParser, str);
        com.android.internal.vibrator.persistence.XmlValidator.checkParserCondition(readAttributeFloat >= f && readAttributeFloat <= f2, "Unexpected %s = %f in tag %s, expected %s in [%f, %f]", str, java.lang.Float.valueOf(readAttributeFloat), name, str, java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2));
        return readAttributeFloat;
    }

    private static int readAttributeInt(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException {
        java.lang.String name = typedXmlPullParser.getName();
        try {
            return typedXmlPullParser.getAttributeInt(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException(name, str, typedXmlPullParser.getAttributeValue(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str), e);
        }
    }

    private static float readAttributeFloat(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws com.android.internal.vibrator.persistence.XmlParserException {
        java.lang.String name = typedXmlPullParser.getName();
        try {
            return typedXmlPullParser.getAttributeFloat(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw com.android.internal.vibrator.persistence.XmlParserException.createFromPullParserException(name, str, typedXmlPullParser.getAttributeValue(com.android.internal.vibrator.persistence.XmlConstants.NAMESPACE, str), e);
        }
    }
}
