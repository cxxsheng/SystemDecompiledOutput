package com.android.internal.util.jobs;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public class XmlUtils {
    private static final java.lang.String STRING_ARRAY_SEPARATOR = ":";

    public interface ReadMapCallback {
        java.lang.Object readThisUnknownObjectXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException;
    }

    public interface WriteMapCallback {
        void writeUnknownObject(java.lang.Object obj, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException;
    }

    private static class ForcedTypedXmlSerializer extends com.android.internal.util.XmlSerializerWrapper implements com.android.modules.utils.TypedXmlSerializer {
        public ForcedTypedXmlSerializer(org.xmlpull.v1.XmlSerializer xmlSerializer) {
            super(xmlSerializer);
        }

        public org.xmlpull.v1.XmlSerializer attributeInterned(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException {
            return attribute(str, str2, str3);
        }

        public org.xmlpull.v1.XmlSerializer attributeBytesHex(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException {
            return attribute(str, str2, com.android.internal.util.HexDump.toHexString(bArr));
        }

        public org.xmlpull.v1.XmlSerializer attributeBytesBase64(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException {
            return attribute(str, str2, android.util.Base64.encodeToString(bArr, 2));
        }

        public org.xmlpull.v1.XmlSerializer attributeInt(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException {
            return attribute(str, str2, java.lang.Integer.toString(i));
        }

        public org.xmlpull.v1.XmlSerializer attributeIntHex(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException {
            return attribute(str, str2, java.lang.Integer.toString(i, 16));
        }

        public org.xmlpull.v1.XmlSerializer attributeLong(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException {
            return attribute(str, str2, java.lang.Long.toString(j));
        }

        public org.xmlpull.v1.XmlSerializer attributeLongHex(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException {
            return attribute(str, str2, java.lang.Long.toString(j, 16));
        }

        public org.xmlpull.v1.XmlSerializer attributeFloat(java.lang.String str, java.lang.String str2, float f) throws java.io.IOException {
            return attribute(str, str2, java.lang.Float.toString(f));
        }

        public org.xmlpull.v1.XmlSerializer attributeDouble(java.lang.String str, java.lang.String str2, double d) throws java.io.IOException {
            return attribute(str, str2, java.lang.Double.toString(d));
        }

        public org.xmlpull.v1.XmlSerializer attributeBoolean(java.lang.String str, java.lang.String str2, boolean z) throws java.io.IOException {
            return attribute(str, str2, java.lang.Boolean.toString(z));
        }
    }

    @android.annotation.NonNull
    public static com.android.modules.utils.TypedXmlSerializer makeTyped(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer) {
        if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
            return (com.android.modules.utils.TypedXmlSerializer) xmlSerializer;
        }
        return new com.android.internal.util.jobs.XmlUtils.ForcedTypedXmlSerializer(xmlSerializer);
    }

    private static class ForcedTypedXmlPullParser extends com.android.internal.util.XmlPullParserWrapper implements com.android.modules.utils.TypedXmlPullParser {
        public ForcedTypedXmlPullParser(org.xmlpull.v1.XmlPullParser xmlPullParser) {
            super(xmlPullParser);
        }

        public byte[] getAttributeBytesHex(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return com.android.internal.util.HexDump.hexStringToByteArray(getAttributeValue(i));
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public byte[] getAttributeBytesBase64(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return android.util.Base64.decode(getAttributeValue(i), 2);
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public int getAttributeInt(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Integer.parseInt(getAttributeValue(i));
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public int getAttributeIntHex(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Integer.parseInt(getAttributeValue(i), 16);
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public long getAttributeLong(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Long.parseLong(getAttributeValue(i));
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public long getAttributeLongHex(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Long.parseLong(getAttributeValue(i), 16);
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public float getAttributeFloat(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Float.parseFloat(getAttributeValue(i));
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public double getAttributeDouble(int i) throws org.xmlpull.v1.XmlPullParserException {
            try {
                return java.lang.Double.parseDouble(getAttributeValue(i));
            } catch (java.lang.Exception e) {
                throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + e);
            }
        }

        public boolean getAttributeBoolean(int i) throws org.xmlpull.v1.XmlPullParserException {
            java.lang.String attributeValue = getAttributeValue(i);
            if ("true".equalsIgnoreCase(attributeValue)) {
                return true;
            }
            if ("false".equalsIgnoreCase(attributeValue)) {
                return false;
            }
            throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + getAttributeName(i) + ": " + attributeValue);
        }
    }

    @android.annotation.NonNull
    public static com.android.modules.utils.TypedXmlPullParser makeTyped(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            return (com.android.modules.utils.TypedXmlPullParser) xmlPullParser;
        }
        return new com.android.internal.util.jobs.XmlUtils.ForcedTypedXmlPullParser(xmlPullParser);
    }

    public static void skipCurrentTag(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next == 3 && xmlPullParser.getDepth() <= depth) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public static final int convertValueToList(java.lang.CharSequence charSequence, java.lang.String[] strArr, int i) {
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (charSequence.equals(strArr[i2])) {
                    return i2;
                }
            }
        }
        return i;
    }

    public static final boolean convertValueToBoolean(java.lang.CharSequence charSequence, boolean z) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return z;
        }
        return charSequence.equals("1") || charSequence.equals("true") || charSequence.equals("TRUE");
    }

    public static final int convertValueToInt(java.lang.CharSequence charSequence, int i) {
        int i2;
        int i3;
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return i;
        }
        java.lang.String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        if ('-' != charSequence2.charAt(0)) {
            i2 = 0;
            i3 = 1;
        } else {
            i3 = -1;
            i2 = 1;
        }
        int i4 = 16;
        if ('0' == charSequence2.charAt(i2)) {
            if (i2 == length - 1) {
                return 0;
            }
            int i5 = i2 + 1;
            char charAt = charSequence2.charAt(i5);
            if ('x' == charAt || 'X' == charAt) {
                i2 += 2;
            } else {
                i4 = 8;
                i2 = i5;
            }
        } else if ('#' != charSequence2.charAt(i2)) {
            i4 = 10;
        } else {
            i2++;
        }
        return java.lang.Integer.parseInt(charSequence2.substring(i2), i4) * i3;
    }

    public static int convertValueToUnsignedInt(java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            return i;
        }
        return parseUnsignedIntAttribute(str);
    }

    public static int parseUnsignedIntAttribute(java.lang.CharSequence charSequence) {
        java.lang.String charSequence2 = charSequence.toString();
        int length = charSequence2.length();
        int i = 0;
        int i2 = 16;
        if ('0' == charSequence2.charAt(0)) {
            if (length - 1 == 0) {
                return 0;
            }
            char charAt = charSequence2.charAt(1);
            if ('x' == charAt || 'X' == charAt) {
                i = 2;
            } else {
                i2 = 8;
                i = 1;
            }
        } else if ('#' != charSequence2.charAt(0)) {
            i2 = 10;
        } else {
            i = 1;
        }
        return (int) java.lang.Long.parseLong(charSequence2.substring(i), i2);
    }

    public static final void writeMapXml(java.util.Map map, java.io.OutputStream outputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((java.lang.String) null, true);
        newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        writeMapXml(map, (java.lang.String) null, newFastSerializer);
        newFastSerializer.endDocument();
    }

    public static final void writeListXml(java.util.List list, java.io.OutputStream outputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((java.lang.String) null, true);
        newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        writeListXml(list, null, newFastSerializer);
        newFastSerializer.endDocument();
    }

    public static final void writeMapXml(java.util.Map map, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        writeMapXml(map, str, typedXmlSerializer, null);
    }

    public static final void writeMapXml(java.util.Map map, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.internal.util.jobs.XmlUtils.WriteMapCallback writeMapCallback) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (map == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "map");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        writeMapXml(map, typedXmlSerializer, writeMapCallback);
        typedXmlSerializer.endTag((java.lang.String) null, "map");
    }

    public static final void writeMapXml(java.util.Map map, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.internal.util.jobs.XmlUtils.WriteMapCallback writeMapCallback) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (map == null) {
            return;
        }
        for (java.util.Map.Entry entry : map.entrySet()) {
            writeValueXml(entry.getValue(), (java.lang.String) entry.getKey(), typedXmlSerializer, writeMapCallback);
        }
    }

    public static final void writeListXml(java.util.List list, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (list == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "list");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            writeValueXml(list.get(i), (java.lang.String) null, typedXmlSerializer);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "list");
    }

    public static final void writeSetXml(java.util.Set set, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (set == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "set");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        java.util.Iterator it = set.iterator();
        while (it.hasNext()) {
            writeValueXml(it.next(), (java.lang.String) null, typedXmlSerializer);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "set");
    }

    public static final void writeByteArrayXml(byte[] bArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (bArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "byte-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", bArr.length);
        typedXmlSerializer.text(libcore.util.HexEncoding.encodeToString(bArr).toLowerCase());
        typedXmlSerializer.endTag((java.lang.String) null, "byte-array");
    }

    public static final void writeIntArrayXml(int[] iArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (iArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "int-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", iArr.length);
        for (int i : iArr) {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attributeInt((java.lang.String) null, "value", i);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "int-array");
    }

    public static final void writeLongArrayXml(long[] jArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (jArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "long-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", jArr.length);
        for (long j : jArr) {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attributeLong((java.lang.String) null, "value", j);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "long-array");
    }

    public static final void writeDoubleArrayXml(double[] dArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (dArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "double-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", dArr.length);
        for (double d : dArr) {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attributeDouble((java.lang.String) null, "value", d);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "double-array");
    }

    public static final void writeStringArrayXml(java.lang.String[] strArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (strArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "string-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", strArr.length);
        for (java.lang.String str2 : strArr) {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attribute((java.lang.String) null, "value", str2);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "string-array");
    }

    public static final void writeBooleanArrayXml(boolean[] zArr, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (zArr == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "boolean-array");
        if (str != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "num", zArr.length);
        for (boolean z : zArr) {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "value", z);
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "boolean-array");
    }

    @java.lang.Deprecated
    public static final void writeValueXml(java.lang.Object obj, java.lang.String str, org.xmlpull.v1.XmlSerializer xmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        writeValueXml(obj, str, makeTyped(xmlSerializer));
    }

    public static final void writeValueXml(java.lang.Object obj, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        writeValueXml(obj, str, typedXmlSerializer, null);
    }

    private static final void writeValueXml(java.lang.Object obj, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.internal.util.jobs.XmlUtils.WriteMapCallback writeMapCallback) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (obj == null) {
            typedXmlSerializer.startTag((java.lang.String) null, "null");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.endTag((java.lang.String) null, "null");
            return;
        }
        if (obj instanceof java.lang.String) {
            typedXmlSerializer.startTag((java.lang.String) null, "string");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.text(obj.toString());
            typedXmlSerializer.endTag((java.lang.String) null, "string");
            return;
        }
        if (obj instanceof java.lang.Integer) {
            typedXmlSerializer.startTag((java.lang.String) null, "int");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.attributeInt((java.lang.String) null, "value", ((java.lang.Integer) obj).intValue());
            typedXmlSerializer.endTag((java.lang.String) null, "int");
            return;
        }
        if (obj instanceof java.lang.Long) {
            typedXmlSerializer.startTag((java.lang.String) null, "long");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.attributeLong((java.lang.String) null, "value", ((java.lang.Long) obj).longValue());
            typedXmlSerializer.endTag((java.lang.String) null, "long");
            return;
        }
        if (obj instanceof java.lang.Float) {
            typedXmlSerializer.startTag((java.lang.String) null, "float");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.attributeFloat((java.lang.String) null, "value", ((java.lang.Float) obj).floatValue());
            typedXmlSerializer.endTag((java.lang.String) null, "float");
            return;
        }
        if (obj instanceof java.lang.Double) {
            typedXmlSerializer.startTag((java.lang.String) null, "double");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.attributeDouble((java.lang.String) null, "value", ((java.lang.Double) obj).doubleValue());
            typedXmlSerializer.endTag((java.lang.String) null, "double");
            return;
        }
        if (obj instanceof java.lang.Boolean) {
            typedXmlSerializer.startTag((java.lang.String) null, "boolean");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "value", ((java.lang.Boolean) obj).booleanValue());
            typedXmlSerializer.endTag((java.lang.String) null, "boolean");
            return;
        }
        if (obj instanceof byte[]) {
            writeByteArrayXml((byte[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof int[]) {
            writeIntArrayXml((int[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof long[]) {
            writeLongArrayXml((long[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof double[]) {
            writeDoubleArrayXml((double[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof java.lang.String[]) {
            writeStringArrayXml((java.lang.String[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof boolean[]) {
            writeBooleanArrayXml((boolean[]) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof java.util.Map) {
            writeMapXml((java.util.Map) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof java.util.List) {
            writeListXml((java.util.List) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof java.util.Set) {
            writeSetXml((java.util.Set) obj, str, typedXmlSerializer);
            return;
        }
        if (obj instanceof java.lang.CharSequence) {
            typedXmlSerializer.startTag((java.lang.String) null, "string");
            if (str != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            }
            typedXmlSerializer.text(obj.toString());
            typedXmlSerializer.endTag((java.lang.String) null, "string");
            return;
        }
        if (writeMapCallback != null) {
            writeMapCallback.writeUnknownObject(obj, str, typedXmlSerializer);
            return;
        }
        throw new java.lang.RuntimeException("writeValueXml: unable to write value " + obj);
    }

    public static final java.util.HashMap<java.lang.String, ?> readMapXml(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        return (java.util.HashMap) readValueXml(newFastPullParser, new java.lang.String[1]);
    }

    public static final java.util.ArrayList readListXml(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        return (java.util.ArrayList) readValueXml(newFastPullParser, new java.lang.String[1]);
    }

    public static final java.util.HashSet readSetXml(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        return (java.util.HashSet) readValueXml(newFastPullParser, new java.lang.String[1]);
    }

    public static final java.util.HashMap<java.lang.String, ?> readThisMapXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return readThisMapXml(typedXmlPullParser, str, strArr, null);
    }

    public static final java.util.HashMap<java.lang.String, ?> readThisMapXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr, com.android.internal.util.jobs.XmlUtils.ReadMapCallback readMapCallback) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.HashMap<java.lang.String, ?> hashMap = new java.util.HashMap<>();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                hashMap.put(strArr[0], readThisValueXml(typedXmlPullParser, strArr, readMapCallback, false));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return hashMap;
                }
                throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final android.util.ArrayMap<java.lang.String, ?> readThisArrayMapXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr, com.android.internal.util.jobs.XmlUtils.ReadMapCallback readMapCallback) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.util.ArrayMap<java.lang.String, ?> arrayMap = new android.util.ArrayMap<>();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayMap.put(strArr[0], readThisValueXml(typedXmlPullParser, strArr, readMapCallback, true));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return arrayMap;
                }
                throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final java.util.ArrayList readThisListXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return readThisListXml(typedXmlPullParser, str, strArr, null, false);
    }

    private static final java.util.ArrayList readThisListXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr, com.android.internal.util.jobs.XmlUtils.ReadMapCallback readMapCallback, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                arrayList.add(readThisValueXml(typedXmlPullParser, strArr, readMapCallback, z));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return arrayList;
                }
                throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final java.util.HashSet readThisSetXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return readThisSetXml(typedXmlPullParser, str, strArr, null, false);
    }

    private static final java.util.HashSet readThisSetXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr, com.android.internal.util.jobs.XmlUtils.ReadMapCallback readMapCallback, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.HashSet hashSet = new java.util.HashSet();
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 2) {
                hashSet.add(readThisValueXml(typedXmlPullParser, strArr, readMapCallback, z));
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return hashSet;
                }
                throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final byte[] readThisByteArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        byte[] bArr = new byte[0];
        int eventType = typedXmlPullParser.getEventType();
        do {
            if (eventType == 4) {
                if (attributeInt > 0) {
                    java.lang.String text = typedXmlPullParser.getText();
                    if (text == null || text.length() != attributeInt * 2) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid value found in byte-array: " + text);
                    }
                    bArr = libcore.util.HexEncoding.decode(text);
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return bArr;
                }
                throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final int[] readThisIntArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        typedXmlPullParser.next();
        int[] iArr = new int[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    iArr[i] = typedXmlPullParser.getAttributeInt((java.lang.String) null, "value");
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected item tag at: " + typedXmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return iArr;
                }
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    i++;
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
                }
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final long[] readThisLongArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        typedXmlPullParser.next();
        long[] jArr = new long[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    jArr[i] = typedXmlPullParser.getAttributeLong((java.lang.String) null, "value");
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected item tag at: " + typedXmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return jArr;
                }
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    i++;
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
                }
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final double[] readThisDoubleArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        typedXmlPullParser.next();
        double[] dArr = new double[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    dArr[i] = typedXmlPullParser.getAttributeDouble((java.lang.String) null, "value");
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected item tag at: " + typedXmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return dArr;
                }
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    i++;
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
                }
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final java.lang.String[] readThisStringArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        typedXmlPullParser.next();
        java.lang.String[] strArr2 = new java.lang.String[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    strArr2[i] = typedXmlPullParser.getAttributeValue((java.lang.String) null, "value");
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected item tag at: " + typedXmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return strArr2;
                }
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    i++;
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
                }
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final boolean[] readThisBooleanArrayXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "num");
        typedXmlPullParser.next();
        boolean[] zArr = new boolean[attributeInt];
        int eventType = typedXmlPullParser.getEventType();
        int i = 0;
        do {
            if (eventType == 2) {
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    zArr[i] = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "value");
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected item tag at: " + typedXmlPullParser.getName());
                }
            } else if (eventType == 3) {
                if (typedXmlPullParser.getName().equals(str)) {
                    return zArr;
                }
                if (typedXmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    i++;
                } else {
                    throw new org.xmlpull.v1.XmlPullParserException("Expected " + str + " end tag at: " + typedXmlPullParser.getName());
                }
            }
            eventType = typedXmlPullParser.next();
        } while (eventType != 1);
        throw new org.xmlpull.v1.XmlPullParserException("Document ended before " + str + " end tag");
    }

    public static final java.lang.Object readValueXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int eventType = typedXmlPullParser.getEventType();
        while (eventType != 2) {
            if (eventType == 3) {
                throw new org.xmlpull.v1.XmlPullParserException("Unexpected end tag at: " + typedXmlPullParser.getName());
            }
            if (eventType == 4) {
                throw new org.xmlpull.v1.XmlPullParserException("Unexpected text: " + typedXmlPullParser.getText());
            }
            eventType = typedXmlPullParser.next();
            if (eventType == 1) {
                throw new org.xmlpull.v1.XmlPullParserException("Unexpected end of document");
            }
        }
        return readThisValueXml(typedXmlPullParser, strArr, null, false);
    }

    private static final java.lang.Object readThisValueXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String[] strArr, com.android.internal.util.jobs.XmlUtils.ReadMapCallback readMapCallback, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        java.lang.Object readThisMapXml;
        java.lang.Object obj = null;
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        java.lang.String name = typedXmlPullParser.getName();
        if (!name.equals("null")) {
            if (name.equals("string")) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                while (true) {
                    int next2 = typedXmlPullParser.next();
                    if (next2 != 1) {
                        if (next2 == 3) {
                            if (typedXmlPullParser.getName().equals("string")) {
                                strArr[0] = attributeValue;
                                return sb.toString();
                            }
                            throw new org.xmlpull.v1.XmlPullParserException("Unexpected end tag in <string>: " + typedXmlPullParser.getName());
                        }
                        if (next2 == 4) {
                            sb.append(typedXmlPullParser.getText());
                        } else if (next2 == 2) {
                            throw new org.xmlpull.v1.XmlPullParserException("Unexpected start tag in <string>: " + typedXmlPullParser.getName());
                        }
                    } else {
                        throw new org.xmlpull.v1.XmlPullParserException("Unexpected end of document in <string>");
                    }
                }
            } else {
                obj = readThisPrimitiveValueXml(typedXmlPullParser, name);
                if (obj == null) {
                    if (name.equals("byte-array")) {
                        byte[] readThisByteArrayXml = readThisByteArrayXml(typedXmlPullParser, "byte-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisByteArrayXml;
                    }
                    if (name.equals("int-array")) {
                        int[] readThisIntArrayXml = readThisIntArrayXml(typedXmlPullParser, "int-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisIntArrayXml;
                    }
                    if (name.equals("long-array")) {
                        long[] readThisLongArrayXml = readThisLongArrayXml(typedXmlPullParser, "long-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisLongArrayXml;
                    }
                    if (name.equals("double-array")) {
                        double[] readThisDoubleArrayXml = readThisDoubleArrayXml(typedXmlPullParser, "double-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisDoubleArrayXml;
                    }
                    if (name.equals("string-array")) {
                        java.lang.String[] readThisStringArrayXml = readThisStringArrayXml(typedXmlPullParser, "string-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisStringArrayXml;
                    }
                    if (name.equals("boolean-array")) {
                        boolean[] readThisBooleanArrayXml = readThisBooleanArrayXml(typedXmlPullParser, "boolean-array", strArr);
                        strArr[0] = attributeValue;
                        return readThisBooleanArrayXml;
                    }
                    if (name.equals("map")) {
                        typedXmlPullParser.next();
                        if (z) {
                            readThisMapXml = readThisArrayMapXml(typedXmlPullParser, "map", strArr, readMapCallback);
                        } else {
                            readThisMapXml = readThisMapXml(typedXmlPullParser, "map", strArr, readMapCallback);
                        }
                        strArr[0] = attributeValue;
                        return readThisMapXml;
                    }
                    if (name.equals("list")) {
                        typedXmlPullParser.next();
                        java.util.ArrayList readThisListXml = readThisListXml(typedXmlPullParser, "list", strArr, readMapCallback, z);
                        strArr[0] = attributeValue;
                        return readThisListXml;
                    }
                    if (name.equals("set")) {
                        typedXmlPullParser.next();
                        java.util.HashSet readThisSetXml = readThisSetXml(typedXmlPullParser, "set", strArr, readMapCallback, z);
                        strArr[0] = attributeValue;
                        return readThisSetXml;
                    }
                    if (readMapCallback != null) {
                        java.lang.Object readThisUnknownObjectXml = readMapCallback.readThisUnknownObjectXml(typedXmlPullParser, name);
                        strArr[0] = attributeValue;
                        return readThisUnknownObjectXml;
                    }
                    throw new org.xmlpull.v1.XmlPullParserException("Unknown tag: " + name);
                }
            }
        }
        do {
            next = typedXmlPullParser.next();
            if (next == 1) {
                throw new org.xmlpull.v1.XmlPullParserException("Unexpected end of document in <" + name + ">");
            }
            if (next == 3) {
                if (!typedXmlPullParser.getName().equals(name)) {
                    throw new org.xmlpull.v1.XmlPullParserException("Unexpected end tag in <" + name + ">: " + typedXmlPullParser.getName());
                }
                strArr[0] = attributeValue;
                return obj;
            }
            if (next == 4) {
                throw new org.xmlpull.v1.XmlPullParserException("Unexpected text in <" + name + ">: " + typedXmlPullParser.getName());
            }
        } while (next != 2);
        throw new org.xmlpull.v1.XmlPullParserException("Unexpected start tag in <" + name + ">: " + typedXmlPullParser.getName());
    }

    private static final java.lang.Object readThisPrimitiveValueXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (str.equals("int")) {
            return java.lang.Integer.valueOf(typedXmlPullParser.getAttributeInt((java.lang.String) null, "value"));
        }
        if (str.equals("long")) {
            return java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, "value"));
        }
        if (str.equals("float")) {
            return java.lang.Float.valueOf(typedXmlPullParser.getAttributeFloat((java.lang.String) null, "value"));
        }
        if (str.equals("double")) {
            return java.lang.Double.valueOf(typedXmlPullParser.getAttributeDouble((java.lang.String) null, "value"));
        }
        if (str.equals("boolean")) {
            return java.lang.Boolean.valueOf(typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "value"));
        }
        return null;
    }

    public static final void beginDocument(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        if (!xmlPullParser.getName().equals(str)) {
            throw new org.xmlpull.v1.XmlPullParserException("Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + str);
        }
    }

    public static final void nextElement(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    public static boolean nextElementWithin(org.xmlpull.v1.XmlPullParser xmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next == 3 && xmlPullParser.getDepth() == i) {
                    return false;
                }
                if (next == 2 && xmlPullParser.getDepth() == i + 1) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static int readIntAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, int i) {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeInt((java.lang.String) null, str, i);
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return i;
        }
        try {
            return java.lang.Integer.parseInt(attributeValue);
        } catch (java.lang.NumberFormatException e) {
            return i;
        }
    }

    public static int readIntAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws java.io.IOException {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            try {
                return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeInt((java.lang.String) null, str);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                throw new java.net.ProtocolException(e.getMessage());
            }
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return java.lang.Integer.parseInt(attributeValue);
        } catch (java.lang.NumberFormatException e2) {
            throw new java.net.ProtocolException("problem parsing " + str + "=" + attributeValue + " as int");
        }
    }

    public static void writeIntAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, int i) throws java.io.IOException {
        if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
            ((com.android.modules.utils.TypedXmlSerializer) xmlSerializer).attributeInt((java.lang.String) null, str, i);
        } else {
            xmlSerializer.attribute(null, str, java.lang.Integer.toString(i));
        }
    }

    public static long readLongAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, long j) {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeLong((java.lang.String) null, str, j);
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return j;
        }
        try {
            return java.lang.Long.parseLong(attributeValue);
        } catch (java.lang.NumberFormatException e) {
            return j;
        }
    }

    public static long readLongAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws java.io.IOException {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            try {
                return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeLong((java.lang.String) null, str);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                throw new java.net.ProtocolException(e.getMessage());
            }
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return java.lang.Long.parseLong(attributeValue);
        } catch (java.lang.NumberFormatException e2) {
            throw new java.net.ProtocolException("problem parsing " + str + "=" + attributeValue + " as long");
        }
    }

    public static void writeLongAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, long j) throws java.io.IOException {
        if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
            ((com.android.modules.utils.TypedXmlSerializer) xmlSerializer).attributeLong((java.lang.String) null, str, j);
        } else {
            xmlSerializer.attribute(null, str, java.lang.Long.toString(j));
        }
    }

    public static float readFloatAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws java.io.IOException {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            try {
                return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeFloat((java.lang.String) null, str);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                throw new java.net.ProtocolException(e.getMessage());
            }
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        try {
            return java.lang.Float.parseFloat(attributeValue);
        } catch (java.lang.NumberFormatException e2) {
            throw new java.net.ProtocolException("problem parsing " + str + "=" + attributeValue + " as long");
        }
    }

    public static void writeFloatAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, float f) throws java.io.IOException {
        if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
            ((com.android.modules.utils.TypedXmlSerializer) xmlSerializer).attributeFloat((java.lang.String) null, str, f);
        } else {
            xmlSerializer.attribute(null, str, java.lang.Float.toString(f));
        }
    }

    public static boolean readBooleanAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
        return readBooleanAttribute(xmlPullParser, str, false);
    }

    public static boolean readBooleanAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, boolean z) {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeBoolean((java.lang.String) null, str, z);
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return z;
        }
        return java.lang.Boolean.parseBoolean(attributeValue);
    }

    public static void writeBooleanAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, boolean z) throws java.io.IOException {
        if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
            ((com.android.modules.utils.TypedXmlSerializer) xmlSerializer).attributeBoolean((java.lang.String) null, str, z);
        } else {
            xmlSerializer.attribute(null, str, java.lang.Boolean.toString(z));
        }
    }

    public static android.net.Uri readUriAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (attributeValue != null) {
            return android.net.Uri.parse(attributeValue);
        }
        return null;
    }

    public static void writeUriAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, android.net.Uri uri) throws java.io.IOException {
        if (uri != null) {
            xmlSerializer.attribute(null, str, uri.toString());
        }
    }

    public static java.lang.String readStringAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
        return xmlPullParser.getAttributeValue(null, str);
    }

    public static void writeStringAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, java.lang.CharSequence charSequence) throws java.io.IOException {
        if (charSequence != null) {
            xmlSerializer.attribute(null, str, charSequence.toString());
        }
    }

    public static byte[] readByteArrayAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
        if (xmlPullParser instanceof com.android.modules.utils.TypedXmlPullParser) {
            try {
                return ((com.android.modules.utils.TypedXmlPullParser) xmlPullParser).getAttributeBytesBase64((java.lang.String) null, str);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                return null;
            }
        }
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return null;
        }
        return android.util.Base64.decode(attributeValue, 0);
    }

    public static void writeByteArrayAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, byte[] bArr) throws java.io.IOException {
        if (bArr != null) {
            if (xmlSerializer instanceof com.android.modules.utils.TypedXmlSerializer) {
                ((com.android.modules.utils.TypedXmlSerializer) xmlSerializer).attributeBytesBase64((java.lang.String) null, str, bArr);
            } else {
                xmlSerializer.attribute(null, str, android.util.Base64.encodeToString(bArr, 0));
            }
        }
    }

    public static android.graphics.Bitmap readBitmapAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
        byte[] readByteArrayAttribute = readByteArrayAttribute(xmlPullParser, str);
        if (readByteArrayAttribute != null) {
            return android.graphics.BitmapFactory.decodeByteArray(readByteArrayAttribute, 0, readByteArrayAttribute.length);
        }
        return null;
    }

    @java.lang.Deprecated
    public static void writeBitmapAttribute(org.xmlpull.v1.XmlSerializer xmlSerializer, java.lang.String str, android.graphics.Bitmap bitmap) throws java.io.IOException {
        if (bitmap != null) {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
            writeByteArrayAttribute(xmlSerializer, str, byteArrayOutputStream.toByteArray());
        }
    }
}
