package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class CertificateType {
    public static final com.android.internal.org.bouncycastle.its.asn1.CertificateType Explicit = new com.android.internal.org.bouncycastle.its.asn1.CertificateType(0);
    public static final com.android.internal.org.bouncycastle.its.asn1.CertificateType Implicit = new com.android.internal.org.bouncycastle.its.asn1.CertificateType(1);
    private final com.android.internal.org.bouncycastle.asn1.ASN1Enumerated enumerated;

    protected CertificateType(int i) {
        this.enumerated = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i);
    }

    private CertificateType(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated) {
        this.enumerated = aSN1Enumerated;
    }

    public com.android.internal.org.bouncycastle.its.asn1.CertificateType getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.CertificateType) {
            return (com.android.internal.org.bouncycastle.its.asn1.CertificateType) obj;
        }
        return new com.android.internal.org.bouncycastle.its.asn1.CertificateType(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(obj));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.enumerated;
    }
}
