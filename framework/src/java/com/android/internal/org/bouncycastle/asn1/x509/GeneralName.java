package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class GeneralName extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable obj;
    private int tag;

    public GeneralName(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        this.obj = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name);
        this.tag = 4;
    }

    public GeneralName(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.obj = x500Name;
        this.tag = 4;
    }

    public GeneralName(int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.obj = aSN1Encodable;
        this.tag = i;
    }

    public GeneralName(int i, java.lang.String str) {
        this.tag = i;
        if (i == 1 || i == 2 || i == 6) {
            this.obj = new com.android.internal.org.bouncycastle.asn1.DERIA5String(str);
            return;
        }
        if (i == 8) {
            this.obj = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str);
            return;
        }
        if (i == 4) {
            this.obj = new com.android.internal.org.bouncycastle.asn1.x500.X500Name(str);
        } else {
            if (i == 7) {
                byte[] generalNameEncoding = toGeneralNameEncoding(str);
                if (generalNameEncoding != null) {
                    this.obj = new com.android.internal.org.bouncycastle.asn1.DEROctetString(generalNameEncoding);
                    return;
                }
                throw new java.lang.IllegalArgumentException("IP Address is invalid");
            }
            throw new java.lang.IllegalArgumentException("can't process String for tag: " + i);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralName getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.GeneralName)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.GeneralName) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj;
            int tagNo = aSN1TaggedObject.getTagNo();
            switch (tagNo) {
                case 0:
                case 3:
                case 5:
                    return new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(tagNo, com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, false));
                case 1:
                case 2:
                case 6:
                    return new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(tagNo, com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(aSN1TaggedObject, false));
                case 4:
                    return new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(tagNo, com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1TaggedObject, true));
                case 7:
                    return new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(tagNo, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1TaggedObject, false));
                case 8:
                    return new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(tagNo, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1TaggedObject, false));
                default:
                    throw new java.lang.IllegalArgumentException("unknown tag: " + tagNo);
            }
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("unable to parse encoded general name");
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralName getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1TaggedObject, true));
    }

    public int getTagNo() {
        return this.tag;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getName() {
        return this.obj;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append(this.tag);
        stringBuffer.append(": ");
        switch (this.tag) {
            case 1:
            case 2:
            case 6:
                stringBuffer.append(com.android.internal.org.bouncycastle.asn1.DERIA5String.getInstance(this.obj).getString());
                break;
            case 3:
            case 5:
            default:
                stringBuffer.append(this.obj.toString());
                break;
            case 4:
                stringBuffer.append(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.obj).toString());
                break;
        }
        return stringBuffer.toString();
    }

    private byte[] toGeneralNameEncoding(java.lang.String str) {
        int[] parseMask;
        if (com.android.internal.org.bouncycastle.util.IPAddress.isValidIPv6WithNetmask(str) || com.android.internal.org.bouncycastle.util.IPAddress.isValidIPv6(str)) {
            int indexOf = str.indexOf(47);
            if (indexOf < 0) {
                byte[] bArr = new byte[16];
                copyInts(parseIPv6(str), bArr, 0);
                return bArr;
            }
            byte[] bArr2 = new byte[32];
            copyInts(parseIPv6(str.substring(0, indexOf)), bArr2, 0);
            java.lang.String substring = str.substring(indexOf + 1);
            if (substring.indexOf(58) > 0) {
                parseMask = parseIPv6(substring);
            } else {
                parseMask = parseMask(substring);
            }
            copyInts(parseMask, bArr2, 16);
            return bArr2;
        }
        if (com.android.internal.org.bouncycastle.util.IPAddress.isValidIPv4WithNetmask(str) || com.android.internal.org.bouncycastle.util.IPAddress.isValidIPv4(str)) {
            int indexOf2 = str.indexOf(47);
            if (indexOf2 < 0) {
                byte[] bArr3 = new byte[4];
                parseIPv4(str, bArr3, 0);
                return bArr3;
            }
            byte[] bArr4 = new byte[8];
            parseIPv4(str.substring(0, indexOf2), bArr4, 0);
            java.lang.String substring2 = str.substring(indexOf2 + 1);
            if (substring2.indexOf(46) > 0) {
                parseIPv4(substring2, bArr4, 4);
            } else {
                parseIPv4Mask(substring2, bArr4, 4);
            }
            return bArr4;
        }
        return null;
    }

    private void parseIPv4Mask(java.lang.String str, byte[] bArr, int i) {
        int parseInt = java.lang.Integer.parseInt(str);
        for (int i2 = 0; i2 != parseInt; i2++) {
            int i3 = (i2 / 8) + i;
            bArr[i3] = (byte) (bArr[i3] | (1 << (7 - (i2 % 8))));
        }
    }

    private void parseIPv4(java.lang.String str, byte[] bArr, int i) {
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "./");
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            bArr[i2 + i] = (byte) java.lang.Integer.parseInt(stringTokenizer.nextToken());
            i2++;
        }
    }

    private int[] parseMask(java.lang.String str) {
        int[] iArr = new int[8];
        int parseInt = java.lang.Integer.parseInt(str);
        for (int i = 0; i != parseInt; i++) {
            int i2 = i / 16;
            iArr[i2] = iArr[i2] | (1 << (15 - (i % 16)));
        }
        return iArr;
    }

    private void copyInts(int[] iArr, byte[] bArr, int i) {
        for (int i2 = 0; i2 != iArr.length; i2++) {
            int i3 = i2 * 2;
            bArr[i3 + i] = (byte) (iArr[i2] >> 8);
            bArr[i3 + 1 + i] = (byte) iArr[i2];
        }
    }

    private int[] parseIPv6(java.lang.String str) {
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, ":", true);
        int[] iArr = new int[8];
        if (str.charAt(0) == ':' && str.charAt(1) == ':') {
            stringTokenizer.nextToken();
        }
        int i = -1;
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals(":")) {
                iArr[i2] = 0;
                int i3 = i2;
                i2++;
                i = i3;
            } else if (nextToken.indexOf(46) < 0) {
                int i4 = i2 + 1;
                iArr[i2] = java.lang.Integer.parseInt(nextToken, 16);
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken();
                }
                i2 = i4;
            } else {
                java.util.StringTokenizer stringTokenizer2 = new java.util.StringTokenizer(nextToken, android.media.MediaMetrics.SEPARATOR);
                int i5 = i2 + 1;
                iArr[i2] = (java.lang.Integer.parseInt(stringTokenizer2.nextToken()) << 8) | java.lang.Integer.parseInt(stringTokenizer2.nextToken());
                i2 = i5 + 1;
                iArr[i5] = java.lang.Integer.parseInt(stringTokenizer2.nextToken()) | (java.lang.Integer.parseInt(stringTokenizer2.nextToken()) << 8);
            }
        }
        if (i2 != 8) {
            int i6 = i2 - i;
            int i7 = 8 - i6;
            java.lang.System.arraycopy(iArr, i, iArr, i7, i6);
            while (i != i7) {
                iArr[i] = 0;
                i++;
            }
        }
        return iArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(this.tag == 4, this.tag, this.obj);
    }
}
