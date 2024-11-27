package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERSet extends com.android.internal.org.bouncycastle.asn1.ASN1Set {
    public BERSet() {
    }

    public BERSet(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public BERSet(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
    }

    public BERSet(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
    }

    BERSet(boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        super(z, aSN1EncodableArr);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        int length = this.elements.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i += this.elements[i2].toASN1Primitive().encodedLength();
        }
        return i + 2 + 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Set, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncodedIndef(z, 49, this.elements);
    }
}
