package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class CertStatus extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private int tagNo;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable value;

    public CertStatus() {
        this.tagNo = 0;
        this.value = com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
    }

    public CertStatus(com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo revokedInfo) {
        this.tagNo = 1;
        this.value = revokedInfo;
    }

    public CertStatus(int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.tagNo = i;
        this.value = aSN1Encodable;
    }

    private CertStatus(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject) {
        this.tagNo = aSN1TaggedObject.getTagNo();
        switch (aSN1TaggedObject.getTagNo()) {
            case 0:
                this.value = com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
                return;
            case 1:
                this.value = com.android.internal.org.bouncycastle.asn1.ocsp.RevokedInfo.getInstance(aSN1TaggedObject, false);
                return;
            case 2:
                this.value = com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown tag encountered: " + aSN1TaggedObject.getTagNo());
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus)) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj);
        }
        throw new java.lang.IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.CertStatus getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public int getTagNo() {
        return this.tagNo;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getStatus() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, this.tagNo, this.value);
    }
}
