package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class SequenceOfRectangularRegion extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.its.asn1.RectangularRegion[] sequence;

    private SequenceOfRectangularRegion(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.sequence = new com.android.internal.org.bouncycastle.its.asn1.RectangularRegion[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.sequence[i] = com.android.internal.org.bouncycastle.its.asn1.RectangularRegion.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(this.sequence);
    }
}
