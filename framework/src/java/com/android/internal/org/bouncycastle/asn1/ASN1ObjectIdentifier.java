package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1ObjectIdentifier extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    private static final long LONG_LIMIT = 72057594037927808L;
    private static final java.util.concurrent.ConcurrentMap<com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier> pool = new java.util.concurrent.ConcurrentHashMap();
    private byte[] body;
    private final java.lang.String identifier;

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Primitive;
            }
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) fromByteArray((byte[]) obj);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct object identifier from byte[]: " + e.getMessage());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier)) {
            return getInstance(object);
        }
        return fromOctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    ASN1ObjectIdentifier(byte[] bArr) {
        int i;
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        boolean z = true;
        java.math.BigInteger bigInteger = null;
        int i2 = 0;
        long j = 0;
        while (i2 != bArr.length) {
            int i3 = bArr[i2] & 255;
            if (j <= LONG_LIMIT) {
                i = i2;
                long j2 = j + (i3 & 127);
                if ((i3 & 128) == 0) {
                    if (z) {
                        if (j2 < 40) {
                            stringBuffer.append('0');
                        } else if (j2 < 80) {
                            stringBuffer.append('1');
                            j2 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j2 -= 80;
                        }
                        z = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                }
            } else {
                i = i2;
                java.math.BigInteger or = (bigInteger == null ? java.math.BigInteger.valueOf(j) : bigInteger).or(java.math.BigInteger.valueOf(i3 & 127));
                if ((i3 & 128) == 0) {
                    if (z) {
                        stringBuffer.append('2');
                        or = or.subtract(java.math.BigInteger.valueOf(80L));
                        z = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(or);
                    bigInteger = null;
                    j = 0;
                } else {
                    bigInteger = or.shiftLeft(7);
                }
            }
            i2 = i + 1;
        }
        this.identifier = stringBuffer.toString().intern();
        this.body = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }

    public ASN1ObjectIdentifier(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("'identifier' cannot be null");
        }
        if (!isValidIdentifier(str)) {
            throw new java.lang.IllegalArgumentException("string " + str + " not an OID");
        }
        this.identifier = str.intern();
    }

    ASN1ObjectIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (!isValidBranchID(str, 0)) {
            throw new java.lang.IllegalArgumentException("string " + str + " not a valid OID branch");
        }
        this.identifier = aSN1ObjectIdentifier.getId() + android.media.MediaMetrics.SEPARATOR + str;
    }

    public java.lang.String getId() {
        return this.identifier;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier branch(java.lang.String str) {
        return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(this, str);
    }

    public boolean on(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String id = getId();
        java.lang.String id2 = aSN1ObjectIdentifier.getId();
        return id.length() > id2.length() && id.charAt(id2.length()) == '.' && id.startsWith(id2);
    }

    private void writeField(java.io.ByteArrayOutputStream byteArrayOutputStream, long j) {
        byte[] bArr = new byte[9];
        int i = 8;
        bArr[8] = (byte) (((int) j) & 127);
        while (j >= 128) {
            j >>= 7;
            i--;
            bArr[i] = (byte) ((((int) j) & 127) | 128);
        }
        byteArrayOutputStream.write(bArr, i, 9 - i);
    }

    private void writeField(java.io.ByteArrayOutputStream byteArrayOutputStream, java.math.BigInteger bigInteger) {
        int bitLength = (bigInteger.bitLength() + 6) / 7;
        if (bitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[bitLength];
        int i = bitLength - 1;
        for (int i2 = i; i2 >= 0; i2--) {
            bArr[i2] = (byte) ((bigInteger.intValue() & 127) | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i] = (byte) (bArr[i] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, bitLength);
    }

    private void doOutput(java.io.ByteArrayOutputStream byteArrayOutputStream) {
        com.android.internal.org.bouncycastle.asn1.OIDTokenizer oIDTokenizer = new com.android.internal.org.bouncycastle.asn1.OIDTokenizer(this.identifier);
        int parseInt = java.lang.Integer.parseInt(oIDTokenizer.nextToken()) * 40;
        java.lang.String nextToken = oIDTokenizer.nextToken();
        if (nextToken.length() <= 18) {
            writeField(byteArrayOutputStream, parseInt + java.lang.Long.parseLong(nextToken));
        } else {
            writeField(byteArrayOutputStream, new java.math.BigInteger(nextToken).add(java.math.BigInteger.valueOf(parseInt)));
        }
        while (oIDTokenizer.hasMoreTokens()) {
            java.lang.String nextToken2 = oIDTokenizer.nextToken();
            if (nextToken2.length() <= 18) {
                writeField(byteArrayOutputStream, java.lang.Long.parseLong(nextToken2));
            } else {
                writeField(byteArrayOutputStream, new java.math.BigInteger(nextToken2));
            }
        }
    }

    private synchronized byte[] getBody() {
        if (this.body == null) {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            doOutput(byteArrayOutputStream);
            this.body = byteArrayOutputStream.toByteArray();
        }
        return this.body;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        int length = getBody().length;
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 6, getBody());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return this.identifier.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == this) {
            return true;
        }
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier)) {
            return false;
        }
        return this.identifier.equals(((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Primitive).identifier);
    }

    public java.lang.String toString() {
        return getId();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean isValidBranchID(java.lang.String str, int i) {
        int length = str.length();
        int i2 = 0;
        while (true) {
            length--;
            if (length < i) {
                return i2 != 0 && (i2 <= 1 || str.charAt(length + 1) != '0');
            }
            char charAt = str.charAt(length);
            if (charAt == '.') {
                if (i2 == 0 || (i2 > 1 && str.charAt(length + 1) == '0')) {
                    break;
                }
                i2 = 0;
            } else {
                if ('0' > charAt || charAt > '9') {
                    break;
                }
                i2++;
            }
        }
        return false;
    }

    private static boolean isValidIdentifier(java.lang.String str) {
        char charAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (charAt = str.charAt(0)) < '0' || charAt > '2') {
            return false;
        }
        return isValidBranchID(str, 2);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier intern() {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle oidHandle = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle(getBody());
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = pool.get(oidHandle);
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier putIfAbsent = pool.putIfAbsent(oidHandle, this);
        if (putIfAbsent == null) {
            return this;
        }
        return putIfAbsent;
    }

    private static class OidHandle {
        private final byte[] enc;
        private final int key;

        OidHandle(byte[] bArr) {
            this.key = com.android.internal.org.bouncycastle.util.Arrays.hashCode(bArr);
            this.enc = bArr;
        }

        public int hashCode() {
            return this.key;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle) {
                return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.enc, ((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle) obj).enc);
            }
            return false;
        }
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier fromOctetString(byte[] bArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = pool.get(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.OidHandle(bArr));
        if (aSN1ObjectIdentifier == null) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(bArr);
        }
        return aSN1ObjectIdentifier;
    }
}
