package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class Pfx extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo;
    private com.android.internal.org.bouncycastle.asn1.pkcs.MacData macData;

    private Pfx(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.macData = null;
        if (com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intValueExact() != 3) {
            throw new java.lang.IllegalArgumentException("wrong version for PFX PDU");
        }
        this.contentInfo = com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() == 3) {
            this.macData = com.android.internal.org.bouncycastle.asn1.pkcs.MacData.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.Pfx getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.Pfx) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.Pfx) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.Pfx(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Pfx(com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo, com.android.internal.org.bouncycastle.asn1.pkcs.MacData macData) {
        this.macData = null;
        this.contentInfo = contentInfo;
        this.macData = macData;
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo getAuthSafe() {
        return this.contentInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.MacData getMacData() {
        return this.macData;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(3L));
        aSN1EncodableVector.add(this.contentInfo);
        if (this.macData != null) {
            aSN1EncodableVector.add(this.macData);
        }
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }
}
