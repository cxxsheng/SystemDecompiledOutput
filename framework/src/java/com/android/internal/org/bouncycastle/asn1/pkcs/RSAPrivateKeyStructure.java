package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class RSAPrivateKeyStructure extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.math.BigInteger coefficient;
    private java.math.BigInteger exponent1;
    private java.math.BigInteger exponent2;
    private java.math.BigInteger modulus;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence otherPrimeInfos;
    private java.math.BigInteger prime1;
    private java.math.BigInteger prime2;
    private java.math.BigInteger privateExponent;
    private java.math.BigInteger publicExponent;
    private int version;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj);
        }
        throw new java.lang.IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public RSAPrivateKeyStructure(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, java.math.BigInteger bigInteger5, java.math.BigInteger bigInteger6, java.math.BigInteger bigInteger7, java.math.BigInteger bigInteger8) {
        this.otherPrimeInfos = null;
        this.version = 0;
        this.modulus = bigInteger;
        this.publicExponent = bigInteger2;
        this.privateExponent = bigInteger3;
        this.prime1 = bigInteger4;
        this.prime2 = bigInteger5;
        this.exponent1 = bigInteger6;
        this.exponent2 = bigInteger7;
        this.coefficient = bigInteger8;
    }

    public RSAPrivateKeyStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.otherPrimeInfos = null;
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        int intValueExact = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).intValueExact();
        if (intValueExact < 0 || intValueExact > 1) {
            throw new java.lang.IllegalArgumentException("wrong version for RSA private key");
        }
        this.version = intValueExact;
        this.modulus = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.publicExponent = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.privateExponent = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.prime1 = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.prime2 = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.exponent1 = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.exponent2 = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        this.coefficient = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement()).getValue();
        if (objects.hasMoreElements()) {
            this.otherPrimeInfos = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) objects.nextElement();
        }
    }

    public int getVersion() {
        return this.version;
    }

    public java.math.BigInteger getModulus() {
        return this.modulus;
    }

    public java.math.BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public java.math.BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    public java.math.BigInteger getPrime1() {
        return this.prime1;
    }

    public java.math.BigInteger getPrime2() {
        return this.prime2;
    }

    public java.math.BigInteger getExponent1() {
        return this.exponent1;
    }

    public java.math.BigInteger getExponent2() {
        return this.exponent2;
    }

    public java.math.BigInteger getCoefficient() {
        return this.coefficient;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(10);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.version));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getModulus()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getPublicExponent()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getPrivateExponent()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getPrime1()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getPrime2()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getExponent1()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getExponent2()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getCoefficient()));
        if (this.otherPrimeInfos != null) {
            aSN1EncodableVector.add(this.otherPrimeInfos);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
