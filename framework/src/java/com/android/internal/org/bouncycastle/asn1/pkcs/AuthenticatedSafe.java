package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class AuthenticatedSafe extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] info;
    private boolean isBer;

    private AuthenticatedSafe(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.isBer = true;
        this.info = new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[aSN1Sequence.size()];
        for (int i = 0; i != this.info.length; i++) {
            this.info[i] = com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo.getInstance(aSN1Sequence.getObjectAt(i));
        }
        this.isBer = aSN1Sequence instanceof com.android.internal.org.bouncycastle.asn1.BERSequence;
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.AuthenticatedSafe(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AuthenticatedSafe(com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] contentInfoArr) {
        this.isBer = true;
        this.info = copy(contentInfoArr);
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] getContentInfo() {
        return copy(this.info);
    }

    private com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] copy(com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] contentInfoArr) {
        int length = contentInfoArr.length;
        com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[] contentInfoArr2 = new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo[length];
        java.lang.System.arraycopy(contentInfoArr, 0, contentInfoArr2, 0, length);
        return contentInfoArr2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (this.isBer) {
            return new com.android.internal.org.bouncycastle.asn1.BERSequence(this.info);
        }
        return new com.android.internal.org.bouncycastle.asn1.DLSequence(this.info);
    }
}
