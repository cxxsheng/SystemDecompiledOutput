package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class OpenSSLPBEParametersGenerator extends com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator {
    private com.android.internal.org.bouncycastle.crypto.Digest digest = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5();

    public void init(byte[] bArr, byte[] bArr2) {
        super.init(bArr, bArr2, 1);
    }

    private byte[] generateDerivedKey(int i) {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        while (true) {
            this.digest.update(this.password, 0, this.password.length);
            this.digest.update(this.salt, 0, this.salt.length);
            this.digest.doFinal(bArr, 0);
            int i3 = i > digestSize ? digestSize : i;
            java.lang.System.arraycopy(bArr, 0, bArr2, i2, i3);
            i2 += i3;
            i -= i3;
            if (i != 0) {
                this.digest.reset();
                this.digest.update(bArr, 0, digestSize);
            } else {
                return bArr2;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey(i2), 0, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] generateDerivedKey = generateDerivedKey(i3 + i4);
        return new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey, 0, i3), generateDerivedKey, i3, i4);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters(int i) {
        return generateDerivedParameters(i);
    }
}
