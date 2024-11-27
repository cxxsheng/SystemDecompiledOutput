package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class LinkageData extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue groupLinkageValue;
    private final com.android.internal.org.bouncycastle.its.asn1.IValue iCert;
    private final com.android.internal.org.bouncycastle.its.asn1.LinkageValue linkageValue;

    private LinkageData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2 && aSN1Sequence.size() != 3) {
            throw new java.lang.IllegalArgumentException("sequence must be size 2 or 3");
        }
        this.iCert = com.android.internal.org.bouncycastle.its.asn1.IValue.getInstance(aSN1Sequence.getObjectAt(2));
        this.linkageValue = com.android.internal.org.bouncycastle.its.asn1.LinkageValue.getInstance(aSN1Sequence.getObjectAt(2));
        this.groupLinkageValue = com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue.getInstance(aSN1Sequence.getObjectAt(2));
    }

    public static com.android.internal.org.bouncycastle.its.asn1.LinkageData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.LinkageData) {
            return (com.android.internal.org.bouncycastle.its.asn1.LinkageData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.LinkageData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector());
    }
}
