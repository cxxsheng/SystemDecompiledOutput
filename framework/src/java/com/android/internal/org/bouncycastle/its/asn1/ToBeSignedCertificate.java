package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class ToBeSignedCertificate extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private ToBeSignedCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
    }

    public static com.android.internal.org.bouncycastle.its.asn1.ToBeSignedCertificate getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.ToBeSignedCertificate) {
            return (com.android.internal.org.bouncycastle.its.asn1.ToBeSignedCertificate) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.ToBeSignedCertificate(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return null;
    }
}
