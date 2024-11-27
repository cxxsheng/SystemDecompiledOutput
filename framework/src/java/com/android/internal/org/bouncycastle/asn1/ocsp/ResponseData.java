package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class ResponseData extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer V1 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0);
    private com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime producedAt;
    private com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID responderID;
    private com.android.internal.org.bouncycastle.asn1.x509.Extensions responseExtensions;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence responses;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;
    private boolean versionPresent;

    public ResponseData(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID responderID, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        this.version = aSN1Integer;
        this.responderID = responderID;
        this.producedAt = aSN1GeneralizedTime;
        this.responses = aSN1Sequence;
        this.responseExtensions = extensions;
    }

    public ResponseData(com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID responderID, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.X509Extensions x509Extensions) {
        this(V1, responderID, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1GeneralizedTime), aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(x509Extensions));
    }

    public ResponseData(com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID responderID, com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        this(V1, responderID, aSN1GeneralizedTime, aSN1Sequence, extensions);
    }

    private ResponseData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            if (((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0)).getTagNo() == 0) {
                this.versionPresent = true;
                this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
                i = 1;
            } else {
                this.version = V1;
            }
        } else {
            this.version = V1;
        }
        int i2 = i + 1;
        this.responderID = com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID.getInstance(aSN1Sequence.getObjectAt(i));
        int i3 = i2 + 1;
        this.producedAt = com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(i2));
        int i4 = i3 + 1;
        this.responses = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(i3);
        if (aSN1Sequence.size() > i4) {
            this.responseExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i4), true);
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID getResponderID() {
        return this.responderID;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime getProducedAt() {
        return this.producedAt;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Sequence getResponses() {
        return this.responses;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getResponseExtensions() {
        return this.responseExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(5);
        if (this.versionPresent || !this.version.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) V1)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.version));
        }
        aSN1EncodableVector.add(this.responderID);
        aSN1EncodableVector.add(this.producedAt);
        aSN1EncodableVector.add(this.responses);
        if (this.responseExtensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.responseExtensions));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
