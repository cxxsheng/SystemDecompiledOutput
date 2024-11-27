package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class BitmapSspRange extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final byte[] sspBitmask;
    private final byte[] sspValue;

    private BitmapSspRange(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("expected sequence with sspValue and sspBitmask");
        }
        this.sspValue = com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets());
        this.sspBitmask = com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
    }

    public static com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange) {
            return (com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getSspValue() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.sspValue);
    }

    public byte[] getSspBitmask() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.sspBitmask);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.sspValue));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.sspBitmask));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
