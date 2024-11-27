package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class SingleResponse extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ocsp.CertID certID;
    private com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus certStatus;
    private com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime nextUpdate;
    private com.android.internal.org.bouncycastle.asn1.x509.Extensions singleExtensions;
    private com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime thisUpdate;

    public SingleResponse(com.android.internal.org.bouncycastle.asn1.ocsp.CertID certID, com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus certStatus, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime2, com.android.internal.org.bouncycastle.asn1.x509.X509Extensions x509Extensions) {
        this(certID, certStatus, aSN1GeneralizedTime, aSN1GeneralizedTime2, com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(x509Extensions));
    }

    public SingleResponse(com.android.internal.org.bouncycastle.asn1.ocsp.CertID certID, com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus certStatus, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime2, com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        this.certID = certID;
        this.certStatus = certStatus;
        this.thisUpdate = aSN1GeneralizedTime;
        this.nextUpdate = aSN1GeneralizedTime2;
        this.singleExtensions = extensions;
    }

    private SingleResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.certID = com.android.internal.org.bouncycastle.asn1.ocsp.CertID.getInstance(aSN1Sequence.getObjectAt(0));
        this.certStatus = com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus.getInstance(aSN1Sequence.getObjectAt(1));
        this.thisUpdate = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 4) {
            this.nextUpdate = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(3), true);
            this.singleExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(4), true);
        } else if (aSN1Sequence.size() > 3) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(3);
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.nextUpdate = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
            } else {
                this.singleExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(aSN1TaggedObject, true);
            }
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.CertID getCertID() {
        return this.certID;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus getCertStatus() {
        return this.certStatus;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getThisUpdate() {
        return this.thisUpdate;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getNextUpdate() {
        return this.nextUpdate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getSingleExtensions() {
        return this.singleExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.certID);
        aSN1EncodableVector.add(this.certStatus);
        aSN1EncodableVector.add(this.thisUpdate);
        if (this.nextUpdate != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.nextUpdate));
        }
        if (this.singleExtensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.singleExtensions));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
