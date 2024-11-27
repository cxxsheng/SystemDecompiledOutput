package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class SHA512Digest extends com.android.internal.org.bouncycastle.crypto.digests.LongDigest {
    private static final int DIGEST_LENGTH = 64;

    public SHA512Digest() {
    }

    public SHA512Digest(com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest sHA512Digest) {
        super(sHA512Digest);
    }

    public SHA512Digest(byte[] bArr) {
        restoreState(bArr);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public java.lang.String getAlgorithmName() {
        return android.security.keystore.KeyProperties.DIGEST_SHA512;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 64;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        finish();
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H1, bArr, i);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H2, bArr, i + 8);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H3, bArr, i + 16);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H4, bArr, i + 24);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H5, bArr, i + 32);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H6, bArr, i + 40);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H7, bArr, i + 48);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.H8, bArr, i + 56);
        reset();
        return 64;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.LongDigest, com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 7640891576956012808L;
        this.H2 = -4942790177534073029L;
        this.H3 = 4354685564936845355L;
        this.H4 = -6534734903238641935L;
        this.H5 = 5840696475078001361L;
        this.H6 = -7276294671716946913L;
        this.H7 = 2270897969802886507L;
        this.H8 = 6620516959819538809L;
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public com.android.internal.org.bouncycastle.util.Memoable copy() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public void reset(com.android.internal.org.bouncycastle.util.Memoable memoable) {
        copyIn((com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest) memoable);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[getEncodedStateSize()];
        super.populateState(bArr);
        return bArr;
    }
}
