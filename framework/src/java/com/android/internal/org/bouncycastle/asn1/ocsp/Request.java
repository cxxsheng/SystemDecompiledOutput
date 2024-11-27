package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class Request extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ocsp.CertID reqCert;
    com.android.internal.org.bouncycastle.asn1.x509.Extensions singleRequestExtensions;

    public Request(com.android.internal.org.bouncycastle.asn1.ocsp.CertID certID, com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        this.reqCert = certID;
        this.singleRequestExtensions = extensions;
    }

    private Request(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.reqCert = com.android.internal.org.bouncycastle.asn1.ocsp.CertID.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.singleRequestExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.Request getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.Request getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.Request) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.Request) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.Request(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.CertID getReqCert() {
        return this.reqCert;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getSingleRequestExtensions() {
        return this.singleRequestExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.reqCert);
        if (this.singleRequestExtensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.singleRequestExtensions));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
