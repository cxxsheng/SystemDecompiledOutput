package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class RectangularRegion extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private RectangularRegion(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
    }

    public static com.android.internal.org.bouncycastle.its.asn1.RectangularRegion getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.RectangularRegion) {
            return (com.android.internal.org.bouncycastle.its.asn1.RectangularRegion) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.RectangularRegion(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return null;
    }
}