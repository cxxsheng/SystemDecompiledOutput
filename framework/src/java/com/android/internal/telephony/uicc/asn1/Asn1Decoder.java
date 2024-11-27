package com.android.internal.telephony.uicc.asn1;

/* loaded from: classes5.dex */
public final class Asn1Decoder {
    private final int mEnd;
    private int mPosition;
    private final byte[] mSrc;

    public Asn1Decoder(java.lang.String str) {
        this(com.android.internal.telephony.uicc.IccUtils.hexStringToBytes(str));
    }

    public Asn1Decoder(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public Asn1Decoder(byte[] bArr, int i, int i2) {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) > bArr.length) {
            throw new java.lang.IndexOutOfBoundsException("Out of the bounds: bytes=[" + bArr.length + "], offset=" + i + ", length=" + i2);
        }
        this.mSrc = bArr;
        this.mPosition = i;
        this.mEnd = i3;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public boolean hasNextNode() {
        return this.mPosition < this.mEnd;
    }

    public com.android.internal.telephony.uicc.asn1.Asn1Node nextNode() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mPosition >= this.mEnd) {
            throw new java.lang.IllegalStateException("No bytes to parse.");
        }
        int i = this.mPosition;
        int i2 = i + 1;
        if ((this.mSrc[i] & 31) == 31) {
            while (i2 < this.mEnd) {
                int i3 = this.mSrc[i2] & 128;
                i2++;
                if (i3 == 0) {
                    break;
                }
            }
        }
        if (i2 >= this.mEnd) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(0, "Invalid length at position: " + i2);
        }
        try {
            int bytesToInt = com.android.internal.telephony.uicc.IccUtils.bytesToInt(this.mSrc, i, i2 - i);
            int i4 = i2 + 1;
            int i5 = this.mSrc[i2];
            if ((i5 & 128) != 0) {
                int i6 = i5 & 127;
                int i7 = i4 + i6;
                if (i7 > this.mEnd) {
                    throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(bytesToInt, "Cannot parse length at position: " + i4);
                }
                try {
                    i5 = com.android.internal.telephony.uicc.IccUtils.bytesToInt(this.mSrc, i4, i6);
                    i4 = i7;
                } catch (java.lang.IllegalArgumentException e) {
                    throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(bytesToInt, "Cannot parse length at position: " + i4, e);
                }
            }
            int i8 = i4 + i5;
            if (i8 > this.mEnd) {
                throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(bytesToInt, "Incomplete data at position: " + i4 + ", expected bytes: " + i5 + ", actual bytes: " + (this.mEnd - i4));
            }
            com.android.internal.telephony.uicc.asn1.Asn1Node asn1Node = new com.android.internal.telephony.uicc.asn1.Asn1Node(bytesToInt, this.mSrc, i4, i5);
            this.mPosition = i8;
            return asn1Node;
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(0, "Cannot parse tag at position: " + i, e2);
        }
    }
}
