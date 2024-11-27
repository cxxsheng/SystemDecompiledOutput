package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERVideotexString extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.asn1.ASN1String {
    private final byte[] string;

    public static com.android.internal.org.bouncycastle.asn1.DERVideotexString getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.DERVideotexString)) {
            return (com.android.internal.org.bouncycastle.asn1.DERVideotexString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (com.android.internal.org.bouncycastle.asn1.DERVideotexString) fromByteArray((byte[]) obj);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.DERVideotexString getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (z || (object instanceof com.android.internal.org.bouncycastle.asn1.DERVideotexString)) {
            return getInstance(object);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERVideotexString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(object).getOctets());
    }

    public DERVideotexString(byte[] bArr) {
        this.string = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }

    public byte[] getOctets() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 21, this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.DERVideotexString)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.string, ((com.android.internal.org.bouncycastle.asn1.DERVideotexString) aSN1Primitive).string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1String
    public java.lang.String getString() {
        return com.android.internal.org.bouncycastle.util.Strings.fromByteArray(this.string);
    }
}
