package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class PKCS5S1ParametersGenerator extends com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator {
    private com.android.internal.org.bouncycastle.crypto.Digest digest;

    public PKCS5S1ParametersGenerator(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this.digest = digest;
    }

    private byte[] generateDerivedKey() {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.update(this.password, 0, this.password.length);
        this.digest.update(this.salt, 0, this.salt.length);
        this.digest.doFinal(bArr, 0);
        for (int i = 1; i < this.iterationCount; i++) {
            this.digest.update(bArr, 0, digestSize);
            this.digest.doFinal(bArr, 0);
        }
        return bArr;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        if (i2 > this.digest.getDigestSize()) {
            throw new java.lang.IllegalArgumentException("Can't generate a derived key " + i2 + " bytes long.");
        }
        return new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey(), 0, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        int i5 = i3 + i4;
        if (i5 > this.digest.getDigestSize()) {
            throw new java.lang.IllegalArgumentException("Can't generate a derived key " + i5 + " bytes long.");
        }
        byte[] generateDerivedKey = generateDerivedKey();
        return new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(generateDerivedKey, 0, i3), generateDerivedKey, i3, i4);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator
    public com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters(int i) {
        return generateDerivedParameters(i);
    }
}
