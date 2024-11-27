package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1Integer extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    static final int SIGN_EXT_SIGNED = -1;
    static final int SIGN_EXT_UNSIGNED = 255;
    private final byte[] bytes;
    private final int start;

    public static com.android.internal.org.bouncycastle.asn1.ASN1Integer getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Integer) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Integer) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Integer getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer)) {
            return getInstance(object);
        }
        return new com.android.internal.org.bouncycastle.asn1.ASN1Integer(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    public ASN1Integer(long j) {
        this.bytes = java.math.BigInteger.valueOf(j).toByteArray();
        this.start = 0;
    }

    public ASN1Integer(java.math.BigInteger bigInteger) {
        this.bytes = bigInteger.toByteArray();
        this.start = 0;
    }

    public ASN1Integer(byte[] bArr) {
        this(bArr, true);
    }

    ASN1Integer(byte[] bArr, boolean z) {
        if (isMalformed(bArr)) {
            throw new java.lang.IllegalArgumentException("malformed integer");
        }
        this.bytes = z ? com.android.internal.org.bouncycastle.util.Arrays.clone(bArr) : bArr;
        this.start = signBytesToSkip(bArr);
    }

    public java.math.BigInteger getPositiveValue() {
        return new java.math.BigInteger(1, this.bytes);
    }

    public java.math.BigInteger getValue() {
        return new java.math.BigInteger(this.bytes);
    }

    public boolean hasValue(java.math.BigInteger bigInteger) {
        return bigInteger != null && intValue(this.bytes, this.start, -1) == bigInteger.intValue() && getValue().equals(bigInteger);
    }

    public int intPositiveValueExact() {
        int length = this.bytes.length - this.start;
        if (length > 4 || (length == 4 && (this.bytes[this.start] & 128) != 0)) {
            throw new java.lang.ArithmeticException("ASN.1 Integer out of positive int range");
        }
        return intValue(this.bytes, this.start, 255);
    }

    public int intValueExact() {
        if (this.bytes.length - this.start > 4) {
            throw new java.lang.ArithmeticException("ASN.1 Integer out of int range");
        }
        return intValue(this.bytes, this.start, -1);
    }

    public long longValueExact() {
        if (this.bytes.length - this.start > 8) {
            throw new java.lang.ArithmeticException("ASN.1 Integer out of long range");
        }
        return longValue(this.bytes, this.start, -1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.bytes.length) + 1 + this.bytes.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 2, this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.bytes, ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Primitive).bytes);
    }

    public java.lang.String toString() {
        return getValue().toString();
    }

    static int intValue(byte[] bArr, int i, int i2) {
        int length = bArr.length;
        int max = java.lang.Math.max(i, length - 4);
        int i3 = i2 & bArr[max];
        while (true) {
            max++;
            if (max < length) {
                i3 = (i3 << 8) | (bArr[max] & 255);
            } else {
                return i3;
            }
        }
    }

    static long longValue(byte[] bArr, int i, int i2) {
        int length = bArr.length;
        int max = java.lang.Math.max(i, length - 8);
        long j = i2 & bArr[max];
        while (true) {
            max++;
            if (max < length) {
                j = (j << 8) | (bArr[max] & 255);
            } else {
                return j;
            }
        }
    }

    static boolean isMalformed(byte[] bArr) {
        switch (bArr.length) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return bArr[0] == (bArr[1] >> 7) && !com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.asn1.allow_unsafe_integer");
        }
    }

    static int signBytesToSkip(byte[] bArr) {
        int length = bArr.length - 1;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (bArr[i] != (bArr[i2] >> 7)) {
                break;
            }
            i = i2;
        }
        return i;
    }
}
