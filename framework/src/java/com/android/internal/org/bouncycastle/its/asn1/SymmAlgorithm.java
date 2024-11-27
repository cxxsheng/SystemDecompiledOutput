package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class SymmAlgorithm extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm aes128Ccm = new com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm(new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(0));
    private com.android.internal.org.bouncycastle.asn1.ASN1Enumerated symmAlgorithm;

    private SymmAlgorithm(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated) {
        this.symmAlgorithm = aSN1Enumerated;
    }

    public SymmAlgorithm(int i) {
        this.symmAlgorithm = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i);
    }

    public com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm) {
            return (com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm) obj;
        }
        return new com.android.internal.org.bouncycastle.its.asn1.SymmAlgorithm(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(obj));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Enumerated getSymmAlgorithm() {
        return this.symmAlgorithm;
    }

    public void setSymmAlgorithm(com.android.internal.org.bouncycastle.asn1.ASN1Enumerated aSN1Enumerated) {
        this.symmAlgorithm = aSN1Enumerated;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.symmAlgorithm.toASN1Primitive();
    }
}
