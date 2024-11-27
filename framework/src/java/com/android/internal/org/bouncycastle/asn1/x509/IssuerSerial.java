package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class IssuerSerial extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.GeneralNames issuer;
    com.android.internal.org.bouncycastle.asn1.DERBitString issuerUID;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serial;

    public static com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial) {
            return (com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    private IssuerSerial(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2 && aSN1Sequence.size() != 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.issuer = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1Sequence.getObjectAt(0));
        this.serial = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() == 3) {
            this.issuerUID = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public IssuerSerial(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, java.math.BigInteger bigInteger) {
        this(new com.android.internal.org.bouncycastle.asn1.x509.GeneralNames(new com.android.internal.org.bouncycastle.asn1.x509.GeneralName(x500Name)), new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger));
    }

    public IssuerSerial(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, java.math.BigInteger bigInteger) {
        this(generalNames, new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger));
    }

    public IssuerSerial(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.issuer = generalNames;
        this.serial = aSN1Integer;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getIssuer() {
        return this.issuer;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerial() {
        return this.serial;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getIssuerUID() {
        return this.issuerUID;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.serial);
        if (this.issuerUID != null) {
            aSN1EncodableVector.add(this.issuerUID);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
