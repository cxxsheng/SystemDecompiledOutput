package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class GroupLinkageValue extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private byte[] jValue;
    private byte[] value;

    private GroupLinkageValue(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("sequence not length 2");
        }
        this.jValue = com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets(), 4);
        this.value = com.android.internal.org.bouncycastle.its.asn1.Utils.octetStringFixed(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets(), 9);
    }

    public static com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue) {
            return (com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.GroupLinkageValue(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getJValue() {
        return this.jValue;
    }

    public byte[] getValue() {
        return this.value;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.jValue));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.value));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
