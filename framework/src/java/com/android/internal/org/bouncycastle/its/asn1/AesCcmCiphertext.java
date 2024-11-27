package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class AesCcmCiphertext extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final byte[] nonce;
    private final com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString opaque;

    private AesCcmCiphertext(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("sequence not length 2");
        }
        this.nonce = com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), 12);
        this.opaque = com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static com.android.internal.org.bouncycastle.its.asn1.AesCcmCiphertext getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.AesCcmCiphertext) {
            return (com.android.internal.org.bouncycastle.its.asn1.AesCcmCiphertext) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.AesCcmCiphertext(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.nonce));
        aSN1EncodableVector.add(this.opaque);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
