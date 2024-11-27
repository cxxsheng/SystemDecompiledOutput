package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class Holder extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int V1_CERTIFICATE_HOLDER = 0;
    public static final int V2_CERTIFICATE_HOLDER = 1;
    com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial baseCertificateID;
    com.android.internal.org.bouncycastle.asn1.x509.GeneralNames entityName;
    com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo objectDigestInfo;
    private int version;

    public static com.android.internal.org.bouncycastle.asn1.x509.Holder getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Holder) {
            return (com.android.internal.org.bouncycastle.asn1.x509.Holder) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return new com.android.internal.org.bouncycastle.asn1.x509.Holder(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(obj));
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.Holder(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private Holder(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject) {
        this.version = 1;
        switch (aSN1TaggedObject.getTagNo()) {
            case 0:
                this.baseCertificateID = com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial.getInstance(aSN1TaggedObject, true);
                break;
            case 1:
                this.entityName = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1TaggedObject, true);
                break;
            default:
                throw new java.lang.IllegalArgumentException("unknown tag in Holder");
        }
        this.version = 0;
    }

    private Holder(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.version = 1;
        if (aSN1Sequence.size() > 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.baseCertificateID = com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial.getInstance(aSN1TaggedObject, false);
                    break;
                case 1:
                    this.entityName = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.objectDigestInfo = com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo.getInstance(aSN1TaggedObject, false);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unknown tag in Holder");
            }
        }
        this.version = 1;
    }

    public Holder(com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial issuerSerial) {
        this(issuerSerial, 1);
    }

    public Holder(com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial issuerSerial, int i) {
        this.version = 1;
        this.baseCertificateID = issuerSerial;
        this.version = i;
    }

    public int getVersion() {
        return this.version;
    }

    public Holder(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        this(generalNames, 1);
    }

    public Holder(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, int i) {
        this.version = 1;
        this.entityName = generalNames;
        this.version = i;
    }

    public Holder(com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo objectDigestInfo) {
        this.version = 1;
        this.objectDigestInfo = objectDigestInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.IssuerSerial getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getEntityName() {
        return this.entityName;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (this.version == 1) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
            if (this.baseCertificateID != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.baseCertificateID));
            }
            if (this.entityName != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.entityName));
            }
            if (this.objectDigestInfo != null) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.objectDigestInfo));
            }
            return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
        }
        if (this.entityName != null) {
            return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.entityName);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.baseCertificateID);
    }
}
