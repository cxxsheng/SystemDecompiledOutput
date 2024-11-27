package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class ResponseBytes extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString response;
    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier responseType;

    public ResponseBytes(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.responseType = aSN1ObjectIdentifier;
        this.response = aSN1OctetString;
    }

    public ResponseBytes(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.responseType = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.response = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Sequence.getObjectAt(1);
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getResponseType() {
        return this.responseType;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getResponse() {
        return this.response;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.responseType);
        aSN1EncodableVector.add(this.response);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
