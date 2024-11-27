package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class HashAlgorithm {
    public static final com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm sha256 = new com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm(0);
    public static final com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm sha384 = new com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm(1);
    private final com.android.internal.org.bouncycastle.asn1.ASN1Enumerated enumerated;

    protected HashAlgorithm(int i) {
        this.enumerated = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i);
    }

    private HashAlgorithm(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated) {
        this.enumerated = aSN1Enumerated;
    }

    public com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm) {
            return (com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm) obj;
        }
        return new com.android.internal.org.bouncycastle.its.asn1.HashAlgorithm(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(obj));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.enumerated;
    }
}
