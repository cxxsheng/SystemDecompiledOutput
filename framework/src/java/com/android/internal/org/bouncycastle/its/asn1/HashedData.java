package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class HashedData extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private com.android.internal.org.bouncycastle.asn1.ASN1OctetString hashData;

    public HashedData(byte[] bArr) {
        this.hashData = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
    }

    private HashedData(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.hashData = aSN1OctetString;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.hashData;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getHashData() {
        return this.hashData;
    }

    public void setHashData(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.hashData = aSN1OctetString;
    }
}
