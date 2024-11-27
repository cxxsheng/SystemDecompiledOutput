package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class GCMParameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private int icvLen;
    private byte[] nonce;

    public static com.android.internal.org.bouncycastle.asn1.cms.GCMParameters getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.GCMParameters) {
            return (com.android.internal.org.bouncycastle.asn1.cms.GCMParameters) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.GCMParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private GCMParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.nonce = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets();
        if (aSN1Sequence.size() == 2) {
            this.icvLen = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).intValueExact();
        } else {
            this.icvLen = 12;
        }
    }

    public GCMParameters(byte[] bArr, int i) {
        this.nonce = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.icvLen = i;
    }

    public byte[] getNonce() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.nonce);
    }

    public int getIcvLen() {
        return this.icvLen;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.nonce));
        if (this.icvLen != 12) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.icvLen));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
