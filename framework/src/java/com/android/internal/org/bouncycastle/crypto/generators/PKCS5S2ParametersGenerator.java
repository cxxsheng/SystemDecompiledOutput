package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class PKCS5S2ParametersGenerator extends com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator {
    private com.android.internal.org.bouncycastle.crypto.Mac hMac;
    private byte[] state;

    public PKCS5S2ParametersGenerator() {
        this(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1());
    }

    public PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this.hMac = new com.android.internal.org.bouncycastle.crypto.macs.HMac(digest);
        this.state = new byte[this.hMac.getMacSize()];
    }

    private void F(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2) {
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("iteration count must be at least 1.");
        }
        if (bArr != null) {
            this.hMac.update(bArr, 0, bArr.length);
        }
        this.hMac.update(bArr2, 0, bArr2.length);
        this.hMac.doFinal(this.state, 0);
        java.lang.System.arraycopy(this.state, 0, bArr3, i2, this.state.length);
        for (int i3 = 1; i3 < i; i3++) {
            this.hMac.update(this.state, 0, this.state.length);
            this.hMac.doFinal(this.state, 0);
            for (int i4 = 0; i4 != this.state.length; i4++) {
                int i5 = i2 + i4;
                bArr3[i5] = (byte) (bArr3[i5] ^ this.state[i4]);
            }
        }
    }

    private byte[] generateDerivedKey(int i) {
        int i2;
        int macSize = this.hMac.getMacSize();
        int i3 = ((i + macSize) - 1) / macSize;
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[i3 * macSize];
        this.hMac.init(new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(this.password));
        int i4 = 0;
        for (int i5 = 1; i5 <= i3; i5++) {
            while (true) {
                byte b = (byte) (bArr[i2] + 1);
                bArr[i2] = b;
                i2 = b == 0 ? i2 - 1 : 3;
            }
            F(this.salt, this.iterationCount, bArr, bArr2, i4);
            i4 += macSize;
        }
        return bArr2;
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
