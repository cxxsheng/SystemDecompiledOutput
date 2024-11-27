package com.android.internal.org.bouncycastle.crypto.paddings;

/* loaded from: classes4.dex */
public class ISO7816d4Padding implements com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding {
    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(java.security.SecureRandom secureRandom) throws java.lang.IllegalArgumentException {
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public java.lang.String getPaddingName() {
        return "ISO7816-4";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        int length = bArr.length - i;
        bArr[i] = Byte.MIN_VALUE;
        while (true) {
            i++;
            if (i < bArr.length) {
                bArr[i] = 0;
            } else {
                return length;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        int length = bArr.length - 1;
        while (length > 0 && bArr[length] == 0) {
            length--;
        }
        if (bArr[length] != Byte.MIN_VALUE) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("pad block corrupted");
        }
        return bArr.length - length;
    }
}
