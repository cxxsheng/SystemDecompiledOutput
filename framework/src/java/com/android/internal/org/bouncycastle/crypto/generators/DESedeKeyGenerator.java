package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DESedeKeyGenerator extends com.android.internal.org.bouncycastle.crypto.generators.DESKeyGenerator {
    private static final int MAX_IT = 20;

    @Override // com.android.internal.org.bouncycastle.crypto.generators.DESKeyGenerator, com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        this.strength = (keyGenerationParameters.getStrength() + 7) / 8;
        if (this.strength == 0 || this.strength == 21) {
            this.strength = 24;
        } else if (this.strength == 14) {
            this.strength = 16;
        } else if (this.strength != 24 && this.strength != 16) {
            throw new java.lang.IllegalArgumentException("DESede key must be 192 or 128 bits long.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.generators.DESKeyGenerator, com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public byte[] generateKey() {
        int i = this.strength;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (true) {
            this.random.nextBytes(bArr);
            com.android.internal.org.bouncycastle.crypto.params.DESedeParameters.setOddParity(bArr);
            i2++;
            if (i2 >= 20 || (!com.android.internal.org.bouncycastle.crypto.params.DESedeParameters.isWeakKey(bArr, 0, i) && com.android.internal.org.bouncycastle.crypto.params.DESedeParameters.isRealEDEKey(bArr, 0))) {
                break;
            }
        }
        if (com.android.internal.org.bouncycastle.crypto.params.DESedeParameters.isWeakKey(bArr, 0, i) || !com.android.internal.org.bouncycastle.crypto.params.DESedeParameters.isRealEDEKey(bArr, 0)) {
            throw new java.lang.IllegalStateException("Unable to generate DES-EDE key");
        }
        return bArr;
    }
}
