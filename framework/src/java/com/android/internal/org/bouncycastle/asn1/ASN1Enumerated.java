package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1Enumerated extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    private static com.android.internal.org.bouncycastle.asn1.ASN1Enumerated[] cache = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated[12];
    private final byte[] bytes;
    private final int start;

    public static com.android.internal.org.bouncycastle.asn1.ASN1Enumerated getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Enumerated)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Enumerated) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Enumerated) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Enumerated getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Enumerated)) {
            return getInstance(object);
        }
        return fromOctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    public ASN1Enumerated(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("enumerated must be non-negative");
        }
        this.bytes = java.math.BigInteger.valueOf(i).toByteArray();
        this.start = 0;
    }

    public ASN1Enumerated(java.math.BigInteger bigInteger) {
        if (bigInteger.signum() < 0) {
            throw new java.lang.IllegalArgumentException("enumerated must be non-negative");
        }
        this.bytes = bigInteger.toByteArray();
        this.start = 0;
    }

    public ASN1Enumerated(byte[] bArr) {
        if (com.android.internal.org.bouncycastle.asn1.ASN1Integer.isMalformed(bArr)) {
            throw new java.lang.IllegalArgumentException("malformed enumerated");
        }
        if ((bArr[0] & 128) != 0) {
            throw new java.lang.IllegalArgumentException("enumerated must be non-negative");
        }
        this.bytes = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.start = com.android.internal.org.bouncycastle.asn1.ASN1Integer.signBytesToSkip(bArr);
    }

    public java.math.BigInteger getValue() {
        return new java.math.BigInteger(this.bytes);
    }

    public boolean hasValue(java.math.BigInteger bigInteger) {
        return bigInteger != null && com.android.internal.org.bouncycastle.asn1.ASN1Integer.intValue(this.bytes, this.start, -1) == bigInteger.intValue() && getValue().equals(bigInteger);
    }

    public int intValueExact() {
        if (this.bytes.length - this.start > 4) {
            throw new java.lang.ArithmeticException("ASN.1 Enumerated out of int range");
        }
        return com.android.internal.org.bouncycastle.asn1.ASN1Integer.intValue(this.bytes, this.start, -1);
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
        aSN1OutputStream.writeEncoded(z, 10, this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Enumerated)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.bytes, ((com.android.internal.org.bouncycastle.asn1.ASN1Enumerated) aSN1Primitive).bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.bytes);
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Enumerated fromOctetString(byte[] bArr) {
        if (bArr.length > 1) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(bArr);
        }
        if (bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("ENUMERATED has zero length");
        }
        int i = bArr[0] & 255;
        if (i >= cache.length) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(bArr);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated = cache[i];
        if (aSN1Enumerated == null) {
            com.android.internal.org.bouncycastle.asn1.ASN1Enumerated[] aSN1EnumeratedArr = cache;
            com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated2 = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(bArr);
            aSN1EnumeratedArr[i] = aSN1Enumerated2;
            return aSN1Enumerated2;
        }
        return aSN1Enumerated;
    }
}
