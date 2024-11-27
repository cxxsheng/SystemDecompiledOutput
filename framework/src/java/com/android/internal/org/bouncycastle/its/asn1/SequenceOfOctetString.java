package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class SequenceOfOctetString extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private byte[][] octetStrings;

    private SequenceOfOctetString(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.octetStrings = toByteArrays(aSN1Sequence);
    }

    public static com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString) {
            return (com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int size() {
        return this.octetStrings.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        for (int i = 0; i != this.octetStrings.length; i++) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(com.android.internal.org.bouncycastle.util.Arrays.clone(this.octetStrings[i])));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    static byte[][] toByteArrays(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        byte[][] bArr = new byte[aSN1Sequence.size()][];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            bArr[i] = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i)).getOctets();
        }
        return bArr;
    }
}
