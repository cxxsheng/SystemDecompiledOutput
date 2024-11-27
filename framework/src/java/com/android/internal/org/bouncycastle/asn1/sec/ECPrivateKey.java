package com.android.internal.org.bouncycastle.asn1.sec;

/* loaded from: classes4.dex */
public class ECPrivateKey extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;

    private ECPrivateKey(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
    }

    public static com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey) {
            return (com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ECPrivateKey(java.math.BigInteger bigInteger) {
        this(bigInteger.bitLength(), bigInteger);
    }

    public ECPrivateKey(int i, java.math.BigInteger bigInteger) {
        byte[] asUnsignedByteArray = com.android.internal.org.bouncycastle.util.BigIntegers.asUnsignedByteArray((i + 7) / 8, bigInteger);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(asUnsignedByteArray));
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public ECPrivateKey(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this(bigInteger, (com.android.internal.org.bouncycastle.asn1.DERBitString) null, aSN1Encodable);
    }

    public ECPrivateKey(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this(bigInteger.bitLength(), bigInteger, dERBitString, aSN1Encodable);
    }

    public ECPrivateKey(int i, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this(i, bigInteger, null, aSN1Encodable);
    }

    public ECPrivateKey(int i, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        byte[] asUnsignedByteArray = com.android.internal.org.bouncycastle.util.BigIntegers.asUnsignedByteArray((i + 7) / 8, bigInteger);
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(asUnsignedByteArray));
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, aSN1Encodable));
        }
        if (dERBitString != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, dERBitString));
        }
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public java.math.BigInteger getKey() {
        return new java.math.BigInteger(1, ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) this.seq.getObjectAt(1)).getOctets());
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getPublicKey() {
        return (com.android.internal.org.bouncycastle.asn1.DERBitString) getObjectInTag(1);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getParameters() {
        return getObjectInTag(0);
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObjectInTag(int i) {
        java.util.Enumeration objects = this.seq.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement();
            if (aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Encodable;
                if (aSN1TaggedObject.getTagNo() == i) {
                    return aSN1TaggedObject.getObject().toASN1Primitive();
                }
            }
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}