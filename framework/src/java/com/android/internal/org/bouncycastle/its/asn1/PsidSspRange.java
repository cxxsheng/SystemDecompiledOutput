package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class PsidSspRange extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer psid;
    private com.android.internal.org.bouncycastle.its.asn1.SspRange sspRange;

    public static com.android.internal.org.bouncycastle.its.asn1.PsidSspRange getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.PsidSspRange) {
            return (com.android.internal.org.bouncycastle.its.asn1.PsidSspRange) obj;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj);
        com.android.internal.org.bouncycastle.its.asn1.PsidSspRange psidSspRange = new com.android.internal.org.bouncycastle.its.asn1.PsidSspRange();
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new java.lang.IllegalStateException("expected sequences with one or optionally two items");
        }
        if (aSN1Sequence.size() == 1) {
            psidSspRange.psid = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(0);
        }
        if (aSN1Sequence.size() == 2) {
            psidSspRange.sspRange = com.android.internal.org.bouncycastle.its.asn1.SspRange.getInstance(aSN1Sequence.getObjectAt(1));
        }
        return psidSspRange;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getPsid() {
        return this.psid;
    }

    public void setPsid(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.psid = aSN1Integer;
    }

    public com.android.internal.org.bouncycastle.its.asn1.SspRange getSspRange() {
        return this.sspRange;
    }

    public void setSspRange(com.android.internal.org.bouncycastle.its.asn1.SspRange sspRange) {
        this.sspRange = sspRange;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(this.psid);
        if (this.sspRange != null) {
            aSN1EncodableVector.add(this.sspRange);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
