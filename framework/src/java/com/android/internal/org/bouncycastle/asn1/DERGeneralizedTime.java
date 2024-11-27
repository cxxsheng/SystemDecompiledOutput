package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERGeneralizedTime extends com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime {
    public DERGeneralizedTime(byte[] bArr) {
        super(bArr);
    }

    public DERGeneralizedTime(java.util.Date date) {
        super(date);
    }

    public DERGeneralizedTime(java.lang.String str) {
        super(str);
    }

    private byte[] getDERTime() {
        if (this.time[this.time.length - 1] == 90) {
            if (!hasMinutes()) {
                byte[] bArr = new byte[this.time.length + 4];
                java.lang.System.arraycopy(this.time, 0, bArr, 0, this.time.length - 1);
                java.lang.System.arraycopy(com.android.internal.org.bouncycastle.util.Strings.toByteArray("0000Z"), 0, bArr, this.time.length - 1, 5);
                return bArr;
            }
            if (!hasSeconds()) {
                byte[] bArr2 = new byte[this.time.length + 2];
                java.lang.System.arraycopy(this.time, 0, bArr2, 0, this.time.length - 1);
                java.lang.System.arraycopy(com.android.internal.org.bouncycastle.util.Strings.toByteArray("00Z"), 0, bArr2, this.time.length - 1, 3);
                return bArr2;
            }
            if (hasFractionalSeconds()) {
                int length = this.time.length - 2;
                while (length > 0 && this.time[length] == 48) {
                    length--;
                }
                if (this.time[length] == 46) {
                    byte[] bArr3 = new byte[length + 1];
                    java.lang.System.arraycopy(this.time, 0, bArr3, 0, length);
                    bArr3[length] = 90;
                    return bArr3;
                }
                byte[] bArr4 = new byte[length + 2];
                int i = length + 1;
                java.lang.System.arraycopy(this.time, 0, bArr4, 0, i);
                bArr4[i] = 90;
                return bArr4;
            }
            return this.time;
        }
        return this.time;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        int length = getDERTime().length;
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(length) + 1 + length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 24, getDERTime());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }
}
