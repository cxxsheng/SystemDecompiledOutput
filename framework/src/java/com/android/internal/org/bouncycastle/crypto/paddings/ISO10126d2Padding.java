package com.android.internal.org.bouncycastle.crypto.paddings;

/* loaded from: classes4.dex */
public class ISO10126d2Padding implements com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding {
    java.security.SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(java.security.SecureRandom secureRandom) throws java.lang.IllegalArgumentException {
        this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public java.lang.String getPaddingName() {
        return "ISO10126-2";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length - 1) {
            bArr[i] = (byte) this.random.nextInt();
            i++;
        }
        bArr[i] = length;
        return length;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        int i = bArr[bArr.length - 1] & 255;
        if (i > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("pad block corrupted");
        }
        return i;
    }
}
