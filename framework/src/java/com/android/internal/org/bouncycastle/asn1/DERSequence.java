package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERSequence extends com.android.internal.org.bouncycastle.asn1.ASN1Sequence {
    private int bodyLength;

    public static com.android.internal.org.bouncycastle.asn1.DERSequence convert(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        return (com.android.internal.org.bouncycastle.asn1.DERSequence) aSN1Sequence.toDERObject();
    }

    public DERSequence() {
        this.bodyLength = -1;
    }

    public DERSequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
        this.bodyLength = -1;
    }

    public DERSequence(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
        this.bodyLength = -1;
    }

    public DERSequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
        this.bodyLength = -1;
    }

    DERSequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr, boolean z) {
        super(aSN1EncodableArr, z);
        this.bodyLength = -1;
    }

    private int getBodyLength() throws java.io.IOException {
        if (this.bodyLength < 0) {
            int length = this.elements.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.elements[i2].toASN1Primitive().toDERObject().encodedLength();
            }
            this.bodyLength = i;
        }
        return this.bodyLength;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        int bodyLength = getBodyLength();
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(bodyLength) + 1 + bodyLength;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        if (z) {
            aSN1OutputStream.write(48);
        }
        com.android.internal.org.bouncycastle.asn1.DEROutputStream dERSubStream = aSN1OutputStream.getDERSubStream();
        int length = this.elements.length;
        int i = 0;
        if (this.bodyLength >= 0 || length > 16) {
            aSN1OutputStream.writeLength(getBodyLength());
            while (i < length) {
                this.elements[i].toASN1Primitive().toDERObject().encode(dERSubStream, true);
                i++;
            }
            return;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive[] aSN1PrimitiveArr = new com.android.internal.org.bouncycastle.asn1.ASN1Primitive[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive dERObject = this.elements[i3].toASN1Primitive().toDERObject();
            aSN1PrimitiveArr[i3] = dERObject;
            i2 += dERObject.encodedLength();
        }
        this.bodyLength = i2;
        aSN1OutputStream.writeLength(i2);
        while (i < length) {
            aSN1PrimitiveArr[i].encode(dERSubStream, true);
            i++;
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Sequence, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }
}
