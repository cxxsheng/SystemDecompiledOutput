package com.android.internal.org.bouncycastle.asn1.x500.style;

/* loaded from: classes4.dex */
public class IETFUtils {
    private static java.lang.String unescape(java.lang.String str) {
        int i;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        char c = 0;
        while (i != charArray.length) {
            char c2 = charArray[i];
            if (c2 != ' ') {
                z3 = true;
            }
            if (c2 == '\"') {
                if (!z) {
                    z2 = !z2;
                } else {
                    stringBuffer.append(c2);
                }
                z = false;
            } else if (c2 == '\\' && !z && !z2) {
                i2 = stringBuffer.length();
                z = true;
            } else if (c2 != ' ' || z || z3) {
                if (z && isHexDigit(c2)) {
                    if (c != 0) {
                        stringBuffer.append((char) ((convertHex(c) * 16) + convertHex(c2)));
                        z = false;
                        c = 0;
                    } else {
                        c = c2;
                    }
                } else {
                    stringBuffer.append(c2);
                    z = false;
                }
            }
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i2 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    private static boolean isHexDigit(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
    }

    private static int convertHex(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return (c - android.text.format.DateFormat.AM_PM) + 10;
        }
        return (c - 'A') + 10;
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNsFromString(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle) {
        com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer x500NameTokenizer = new com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer(str);
        com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder x500NameBuilder = new com.android.internal.org.bouncycastle.asn1.x500.X500NameBuilder(x500NameStyle);
        while (x500NameTokenizer.hasMoreTokens()) {
            java.lang.String nextToken = x500NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer x500NameTokenizer2 = new com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer(nextToken, '+');
                com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer x500NameTokenizer3 = new com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer(x500NameTokenizer2.nextToken(), '=');
                java.lang.String nextToken2 = x500NameTokenizer3.nextToken();
                if (!x500NameTokenizer3.hasMoreTokens()) {
                    throw new java.lang.IllegalArgumentException("badly formatted directory string");
                }
                java.lang.String nextToken3 = x500NameTokenizer3.nextToken();
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier attrNameToOID = x500NameStyle.attrNameToOID(nextToken2.trim());
                if (x500NameTokenizer2.hasMoreTokens()) {
                    java.util.Vector vector = new java.util.Vector();
                    java.util.Vector vector2 = new java.util.Vector();
                    vector.addElement(attrNameToOID);
                    vector2.addElement(unescape(nextToken3));
                    while (x500NameTokenizer2.hasMoreTokens()) {
                        com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer x500NameTokenizer4 = new com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer(x500NameTokenizer2.nextToken(), '=');
                        java.lang.String nextToken4 = x500NameTokenizer4.nextToken();
                        if (!x500NameTokenizer4.hasMoreTokens()) {
                            throw new java.lang.IllegalArgumentException("badly formatted directory string");
                        }
                        java.lang.String nextToken5 = x500NameTokenizer4.nextToken();
                        vector.addElement(x500NameStyle.attrNameToOID(nextToken4.trim()));
                        vector2.addElement(unescape(nextToken5));
                    }
                    x500NameBuilder.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
                } else {
                    x500NameBuilder.addRDN(attrNameToOID, unescape(nextToken3));
                }
            } else {
                com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer x500NameTokenizer5 = new com.android.internal.org.bouncycastle.asn1.x500.style.X500NameTokenizer(nextToken, '=');
                java.lang.String nextToken6 = x500NameTokenizer5.nextToken();
                if (!x500NameTokenizer5.hasMoreTokens()) {
                    throw new java.lang.IllegalArgumentException("badly formatted directory string");
                }
                x500NameBuilder.addRDN(x500NameStyle.attrNameToOID(nextToken6.trim()), unescape(x500NameTokenizer5.nextToken()));
            }
        }
        return x500NameBuilder.build().getRDNs();
    }

    private static java.lang.String[] toValueArray(java.util.Vector vector) {
        int size = vector.size();
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i = 0; i != size; i++) {
            strArr[i] = (java.lang.String) vector.elementAt(i);
        }
        return strArr;
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] toOIDArray(java.util.Vector vector) {
        int size = vector.size();
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[size];
        for (int i = 0; i != size; i++) {
            aSN1ObjectIdentifierArr[i] = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) vector.elementAt(i);
        }
        return aSN1ObjectIdentifierArr;
    }

    public static java.lang.String[] findAttrNamesForOID(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.util.Hashtable hashtable) {
        java.util.Enumeration elements = hashtable.elements();
        int i = 0;
        int i2 = 0;
        while (elements.hasMoreElements()) {
            if (aSN1ObjectIdentifier.equals(elements.nextElement())) {
                i2++;
            }
        }
        java.lang.String[] strArr = new java.lang.String[i2];
        java.util.Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            java.lang.String str = (java.lang.String) keys.nextElement();
            if (aSN1ObjectIdentifier.equals(hashtable.get(str))) {
                strArr[i] = str;
                i++;
            }
        }
        return strArr;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier decodeAttrName(java.lang.String str, java.util.Hashtable hashtable) {
        if (com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str).startsWith("OID.")) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) hashtable.get(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier == null) {
            throw new java.lang.IllegalArgumentException("Unknown object id - " + str + " - passed to distinguished name");
        }
        return aSN1ObjectIdentifier;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Encodable valueFromHexString(java.lang.String str, int i) throws java.io.IOException {
        int length = (str.length() - i) / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            int i3 = (i2 * 2) + i;
            char charAt = str.charAt(i3);
            char charAt2 = str.charAt(i3 + 1);
            bArr[i2] = (byte) (convertHex(charAt2) | (convertHex(charAt) << 4));
        }
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr);
    }

    public static void appendRDN(java.lang.StringBuffer stringBuffer, com.android.internal.org.bouncycastle.asn1.x500.RDN rdn, java.util.Hashtable hashtable) {
        if (rdn.isMultiValued()) {
            com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
            boolean z = true;
            for (int i = 0; i != typesAndValues.length; i++) {
                if (z) {
                    z = false;
                } else {
                    stringBuffer.append('+');
                }
                appendTypeAndValue(stringBuffer, typesAndValues[i], hashtable);
            }
            return;
        }
        if (rdn.getFirst() != null) {
            appendTypeAndValue(stringBuffer, rdn.getFirst(), hashtable);
        }
    }

    public static void appendTypeAndValue(java.lang.StringBuffer stringBuffer, com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue attributeTypeAndValue, java.util.Hashtable hashtable) {
        java.lang.String str = (java.lang.String) hashtable.get(attributeTypeAndValue.getType());
        if (str != null) {
            stringBuffer.append(str);
        } else {
            stringBuffer.append(attributeTypeAndValue.getType().getId());
        }
        stringBuffer.append('=');
        stringBuffer.append(valueToString(attributeTypeAndValue.getValue()));
    }

    public static java.lang.String valueToString(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if ((aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1String) && !(aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.DERUniversalString)) {
            java.lang.String string = ((com.android.internal.org.bouncycastle.asn1.ASN1String) aSN1Encodable).getString();
            if (string.length() > 0 && string.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(string);
        } else {
            try {
                stringBuffer.append('#');
                stringBuffer.append(com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER)));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Other value has no encoded form");
            }
        }
        int length = stringBuffer.length();
        int i = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i != length) {
            switch (stringBuffer.charAt(i)) {
                case '\"':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                    stringBuffer.insert(i, "\\");
                    i += 2;
                    length++;
                    break;
                default:
                    i++;
                    break;
            }
        }
        if (stringBuffer.length() > 0) {
            for (int i2 = 0; stringBuffer.length() > i2 && stringBuffer.charAt(i2) == ' '; i2 += 2) {
                stringBuffer.insert(i2, "\\");
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= 0 && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005a, code lost:
    
        if (r5 >= r0) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String canonicalize(java.lang.String str) {
        int i = 0;
        if (str.length() > 0 && str.charAt(0) == '#') {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable decodeObject = decodeObject(str);
            if (decodeObject instanceof com.android.internal.org.bouncycastle.asn1.ASN1String) {
                str = ((com.android.internal.org.bouncycastle.asn1.ASN1String) decodeObject).getString();
            }
        }
        java.lang.String lowerCase = com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str);
        int length = lowerCase.length();
        if (length < 2) {
            return lowerCase;
        }
        int i2 = length - 1;
        while (i < i2 && lowerCase.charAt(i) == '\\' && lowerCase.charAt(i + 1) == ' ') {
            i += 2;
        }
        int i3 = i + 1;
        int i4 = i2;
        while (i4 > i3 && lowerCase.charAt(i4 - 1) == '\\' && lowerCase.charAt(i4) == ' ') {
            i4 -= 2;
        }
        lowerCase = lowerCase.substring(i, i4 + 1);
        return stripInternalSpaces(lowerCase);
    }

    public static java.lang.String canonicalString(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        return canonicalize(valueToString(aSN1Encodable));
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Primitive decodeObject(java.lang.String str) {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("unknown encoding in name: " + e);
        }
    }

    public static java.lang.String stripInternalSpaces(java.lang.String str) {
        if (str.indexOf("  ") < 0) {
            return str;
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        char charAt = str.charAt(0);
        stringBuffer.append(charAt);
        for (int i = 1; i < str.length(); i++) {
            char charAt2 = str.charAt(i);
            if (charAt != ' ' || charAt2 != ' ') {
                stringBuffer.append(charAt2);
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    public static boolean rDNAreEqual(com.android.internal.org.bouncycastle.asn1.x500.RDN rdn, com.android.internal.org.bouncycastle.asn1.x500.RDN rdn2) {
        if (rdn.size() != rdn2.size()) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
        com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] typesAndValues2 = rdn2.getTypesAndValues();
        if (typesAndValues.length != typesAndValues2.length) {
            return false;
        }
        for (int i = 0; i != typesAndValues.length; i++) {
            if (!atvAreEqual(typesAndValues[i], typesAndValues2[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean atvAreEqual(com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue attributeTypeAndValue, com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue attributeTypeAndValue2) {
        if (attributeTypeAndValue == attributeTypeAndValue2) {
            return true;
        }
        if (attributeTypeAndValue != null && attributeTypeAndValue2 != null && attributeTypeAndValue.getType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) attributeTypeAndValue2.getType()) && canonicalString(attributeTypeAndValue.getValue()).equals(canonicalString(attributeTypeAndValue2.getValue()))) {
            return true;
        }
        return false;
    }
}
