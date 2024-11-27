package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class TBSCertificate extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.Time endDate;
    com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    com.android.internal.org.bouncycastle.asn1.DERBitString issuerUniqueId;
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer serialNumber;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    com.android.internal.org.bouncycastle.asn1.x509.Time startDate;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name subject;
    com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo;
    com.android.internal.org.bouncycastle.asn1.DERBitString subjectUniqueId;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate) {
            return (com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private TBSCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int i;
        boolean z;
        boolean z2;
        this.seq = aSN1Sequence;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
            i = 0;
        } else {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L);
            i = -1;
        }
        if (this.version.hasValue(java.math.BigInteger.valueOf(0L))) {
            z2 = false;
            z = true;
        } else if (this.version.hasValue(java.math.BigInteger.valueOf(1L))) {
            z = false;
            z2 = true;
        } else if (this.version.hasValue(java.math.BigInteger.valueOf(2L))) {
            z = false;
            z2 = false;
        } else {
            throw new java.lang.IllegalArgumentException("version number not recognised");
        }
        this.serialNumber = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.signature = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 2));
        this.issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(i + 3));
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(i + 4);
        this.startDate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence2.getObjectAt(0));
        this.endDate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence2.getObjectAt(1));
        this.subject = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(i + 5));
        int i2 = i + 6;
        this.subjectPublicKeyInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(i2));
        int size = (aSN1Sequence.size() - i2) - 1;
        if (size != 0 && z) {
            throw new java.lang.IllegalArgumentException("version 1 certificate contains extra data");
        }
        while (size > 0) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i2 + size);
            switch (aSN1TaggedObject.getTagNo()) {
                case 1:
                    this.issuerUniqueId = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.subjectUniqueId = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 3:
                    if (z2) {
                        throw new java.lang.IllegalArgumentException("version 2 certificate cannot contain extensions");
                    }
                    this.extensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, true));
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown tag encountered in structure: " + aSN1TaggedObject.getTagNo());
            }
            size--;
        }
    }

    public int getVersionNumber() {
        return this.version.intValueExact() + 1;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.issuer;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getStartDate() {
        return this.startDate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getEndDate() {
        return this.endDate;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubject() {
        return this.subject;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.extensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (com.android.internal.org.bouncycastle.util.Properties.getPropertyValue("com.android.internal.org.bouncycastle.x509.allow_non-der_tbscert") != null) {
            if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.allow_non-der_tbscert")) {
                return this.seq;
            }
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            if (!this.version.hasValue(com.android.internal.org.bouncycastle.util.BigIntegers.ZERO)) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.version));
            }
            aSN1EncodableVector.add(this.serialNumber);
            aSN1EncodableVector.add(this.signature);
            aSN1EncodableVector.add(this.issuer);
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
            aSN1EncodableVector2.add(this.startDate);
            aSN1EncodableVector2.add(this.endDate);
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
            if (this.subject != null) {
                aSN1EncodableVector.add(this.subject);
            } else {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence());
            }
            aSN1EncodableVector.add(this.subjectPublicKeyInfo);
            if (this.issuerUniqueId != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.issuerUniqueId));
            }
            if (this.subjectUniqueId != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.subjectUniqueId));
            }
            if (this.extensions != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 3, this.extensions));
            }
            return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }
}
