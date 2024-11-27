package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class BERFactory {
    static final com.android.internal.org.bouncycastle.asn1.BERSequence EMPTY_SEQUENCE = new com.android.internal.org.bouncycastle.asn1.BERSequence();
    static final com.android.internal.org.bouncycastle.asn1.BERSet EMPTY_SET = new com.android.internal.org.bouncycastle.asn1.BERSet();

    BERFactory() {
    }

    static com.android.internal.org.bouncycastle.asn1.BERSequence createSequence(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SEQUENCE;
        }
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }

    static com.android.internal.org.bouncycastle.asn1.BERSet createSet(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() < 1) {
            return EMPTY_SET;
        }
        return new com.android.internal.org.bouncycastle.asn1.BERSet(aSN1EncodableVector);
    }
}
