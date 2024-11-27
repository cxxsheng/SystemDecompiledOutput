package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class CertificateBase extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.its.asn1.CertificateType type;
    private byte[] version;

    protected CertificateBase(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
    }

    public static com.android.internal.org.bouncycastle.its.asn1.CertificateBase getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.ImplicitCertificate) {
            return (com.android.internal.org.bouncycastle.its.asn1.ImplicitCertificate) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.ExplicitCertificate) {
            return (com.android.internal.org.bouncycastle.its.asn1.ExplicitCertificate) obj;
        }
        if (obj != null) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj);
            if (aSN1Sequence.getObjectAt(1).equals(com.android.internal.org.bouncycastle.its.asn1.CertificateType.Implicit)) {
                return com.android.internal.org.bouncycastle.its.asn1.ImplicitCertificate.getInstance(aSN1Sequence);
            }
            if (aSN1Sequence.getObjectAt(1).equals(com.android.internal.org.bouncycastle.its.asn1.CertificateType.Explicit)) {
                return com.android.internal.org.bouncycastle.its.asn1.ExplicitCertificate.getInstance(aSN1Sequence);
            }
            throw new java.lang.IllegalArgumentException("unknown certificate type");
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector());
    }
}
