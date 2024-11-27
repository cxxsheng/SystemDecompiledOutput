package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class Ieee1609Dot2Data extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Content content;
    private final java.math.BigInteger protcolVersion;

    private Ieee1609Dot2Data(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("sequence not length 2");
        }
        this.protcolVersion = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue();
        this.content = com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Content.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Data getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Data) {
            return (com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Data) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Data(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector());
    }
}
