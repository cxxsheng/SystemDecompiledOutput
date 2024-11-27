package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class TBSRequest extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer V1 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0);
    com.android.internal.org.bouncycastle.asn1.x509.Extensions requestExtensions;
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence requestList;
    com.android.internal.org.bouncycastle.asn1.x509.GeneralName requestorName;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer version;
    boolean versionSet;

    public TBSRequest(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.X509Extensions x509Extensions) {
        this.version = V1;
        this.requestorName = generalName;
        this.requestList = aSN1Sequence;
        this.requestExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(x509Extensions);
    }

    public TBSRequest(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        this.version = V1;
        this.requestorName = generalName;
        this.requestList = aSN1Sequence;
        this.requestExtensions = extensions;
    }

    private TBSRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            if (((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0)).getTagNo() == 0) {
                this.versionSet = true;
                this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
                i = 1;
            } else {
                this.version = V1;
            }
        } else {
            this.version = V1;
        }
        if (aSN1Sequence.getObjectAt(i) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            this.requestorName = com.android.internal.org.bouncycastle.asn1.x509.GeneralName.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i), true);
            i++;
        }
        int i2 = i + 1;
        this.requestList = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(i);
        if (aSN1Sequence.size() == i2 + 1) {
            this.requestExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i2), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralName getRequestorName() {
        return this.requestorName;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Sequence getRequestList() {
        return this.requestList;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getRequestExtensions() {
        return this.requestExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        if (!this.version.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) V1) || this.versionSet) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.version));
        }
        if (this.requestorName != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.requestorName));
        }
        aSN1EncodableVector.add(this.requestList);
        if (this.requestExtensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 2, this.requestExtensions));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
