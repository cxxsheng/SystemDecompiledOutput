package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class Ieee1609Dot2Content extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    public static com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Content getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Content) {
            return (com.android.internal.org.bouncycastle.its.asn1.Ieee1609Dot2Content) obj;
        }
        if (obj != null) {
            return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector());
    }
}
