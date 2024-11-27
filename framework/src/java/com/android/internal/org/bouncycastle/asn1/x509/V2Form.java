package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class V2Form extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial baseCertificateID;
    com.android.internal.org.bouncycastle.asn1.x509.GeneralNames issuerName;
    com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo objectDigestInfo;

    public static com.android.internal.org.bouncycastle.asn1.x509.V2Form getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.V2Form getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.V2Form) {
            return (com.android.internal.org.bouncycastle.asn1.x509.V2Form) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.V2Form(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public V2Form(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        this(generalNames, null, null);
    }

    public V2Form(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial issuerSerial) {
        this(generalNames, issuerSerial, null);
    }

    public V2Form(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo objectDigestInfo) {
        this(generalNames, null, objectDigestInfo);
    }

    public V2Form(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial issuerSerial, com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo objectDigestInfo) {
        this.issuerName = generalNames;
        this.baseCertificateID = issuerSerial;
        this.objectDigestInfo = objectDigestInfo;
    }

    public V2Form(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int i;
        if (aSN1Sequence.size() > 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            i = 0;
        } else {
            this.issuerName = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1Sequence.getObjectAt(0));
            i = 1;
        }
        while (i != aSN1Sequence.size()) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.baseCertificateID = com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial.getInstance(aSN1TaggedObject, false);
            } else if (aSN1TaggedObject.getTagNo() == 1) {
                this.objectDigestInfo = com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo.getInstance(aSN1TaggedObject, false);
            } else {
                throw new java.lang.IllegalArgumentException("Bad tag number: " + aSN1TaggedObject.getTagNo());
            }
            i++;
        }
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getIssuerName() {
        return this.issuerName;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (this.issuerName != null) {
            aSN1EncodableVector.add(this.issuerName);
        }
        if (this.baseCertificateID != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.baseCertificateID));
        }
        if (this.objectDigestInfo != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.objectDigestInfo));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
