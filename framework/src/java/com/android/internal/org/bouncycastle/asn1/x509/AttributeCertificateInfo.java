package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AttributeCertificateInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod attrCertValidityPeriod;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence attributes;
    private com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
    private com.android.internal.org.bouncycastle.asn1.x509.Holder holder;
    private com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer issuer;
    private com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueID;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private AttributeCertificateInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 6 || aSN1Sequence.size() > 9) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            i = 1;
        } else {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L);
        }
        this.holder = com.android.internal.org.bouncycastle.asn1.x509.Holder.getInstance(aSN1Sequence.getObjectAt(i));
        this.issuer = com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.signature = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 2));
        this.serialNumber = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i + 3));
        this.attrCertValidityPeriod = com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod.getInstance(aSN1Sequence.getObjectAt(i + 4));
        this.attributes = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i + 5));
        for (int i2 = i + 6; i2 < aSN1Sequence.size(); i2++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable objectAt = aSN1Sequence.getObjectAt(i2);
            if (objectAt instanceof com.android.internal.org.bouncycastle.asn1.DERBitString) {
                this.issuerUniqueID = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(i2));
            } else if ((objectAt instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) || (objectAt instanceof com.android.internal.org.bouncycastle.asn1.x509.Extensions)) {
                this.extensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(aSN1Sequence.getObjectAt(i2));
            }
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Holder getHolder() {
        return this.holder;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer getIssuer() {
        return this.issuer;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AttCertValidityPeriod getAttrCertValidityPeriod() {
        return this.attrCertValidityPeriod;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Sequence getAttributes() {
        return this.attributes;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getIssuerUniqueID() {
        return this.issuerUniqueID;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.extensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(9);
        if (this.version.intValueExact() != 0) {
            aSN1EncodableVector.add(this.version);
        }
        aSN1EncodableVector.add(this.holder);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.serialNumber);
        aSN1EncodableVector.add(this.attrCertValidityPeriod);
        aSN1EncodableVector.add(this.attributes);
        if (this.issuerUniqueID != null) {
            aSN1EncodableVector.add(this.issuerUniqueID);
        }
        if (this.extensions != null) {
            aSN1EncodableVector.add(this.extensions);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
