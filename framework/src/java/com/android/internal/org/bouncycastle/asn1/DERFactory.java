package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class DERFactory {
    static final com.android.internal.org.bouncycastle.asn1.ASN1Sequence EMPTY_SEQUENCE = new com.android.internal.org.bouncycastle.asn1.DERSequence();
    static final com.android.internal.org.bouncycastle.asn1.ASN1Set EMPTY_SET = new com.android.internal.org.bouncycastle.asn1.DERSet();

    DERFactory() {
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Sequence createSequence(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SEQUENCE;
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Set createSet(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SET;
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector);
    }
}
