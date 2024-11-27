package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DESKeyGenerator extends com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator {
    @Override // com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        super.init(keyGenerationParameters);
        if (this.strength == 0 || this.strength == 7) {
            this.strength = 8;
        } else if (this.strength != 8) {
            throw new java.lang.IllegalArgumentException("DES key must be 64 bits long.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public byte[] generateKey() {
        byte[] bArr = new byte[8];
        do {
            this.random.nextBytes(bArr);
            com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(bArr);
        } while (com.android.internal.org.bouncycastle.crypto.params.DESParameters.isWeakKey(bArr, 0));
        return bArr;
    }
}
