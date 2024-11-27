package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1Boolean extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    private static final byte FALSE_VALUE = 0;
    private static final byte TRUE_VALUE = -1;
    private final byte value;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1Boolean FALSE = new com.android.internal.org.bouncycastle.asn1.ASN1Boolean((byte) 0);
    public static final com.android.internal.org.bouncycastle.asn1.ASN1Boolean TRUE = new com.android.internal.org.bouncycastle.asn1.ASN1Boolean((byte) -1);

    public static com.android.internal.org.bouncycastle.asn1.ASN1Boolean getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Boolean)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Boolean) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Boolean) fromByteArray((byte[]) obj);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Boolean getInstance(boolean z) {
        return z ? TRUE : FALSE;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Boolean getInstance(int i) {
        return i != 0 ? TRUE : FALSE;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Boolean getInstance(byte[] bArr) {
        return bArr[0] != 0 ? TRUE : FALSE;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Boolean getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Boolean)) {
            return getInstance(object);
        }
        return fromOctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    private ASN1Boolean(byte b) {
        this.value = b;
    }

    public boolean isTrue() {
        return this.value != 0;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return 3;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 1, this.value);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        return (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Boolean) && isTrue() == ((com.android.internal.org.bouncycastle.asn1.ASN1Boolean) aSN1Primitive).isTrue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return isTrue() ? 1 : 0;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return isTrue() ? TRUE : FALSE;
    }

    public java.lang.String toString() {
        return isTrue() ? "TRUE" : "FALSE";
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Boolean fromOctetString(byte[] bArr) {
        if (bArr.length != 1) {
            throw new java.lang.IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
        byte b = bArr[0];
        switch (b) {
            case -1:
                return TRUE;
            case 0:
                return FALSE;
            default:
                return new com.android.internal.org.bouncycastle.asn1.ASN1Boolean(b);
        }
    }
}
