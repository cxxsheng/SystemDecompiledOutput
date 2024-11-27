package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class SHA384Digest extends com.android.internal.org.bouncycastle.crypto.digests.LongDigest {
    private static final int DIGEST_LENGTH = 48;

    public SHA384Digest() {
    }

    public SHA384Digest(com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest sHA384Digest) {
        super(sHA384Digest);
    }

    public SHA384Digest(byte[] bArr) {
        restoreState(bArr);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public java.lang.String getAlgorithmName() {
        return android.security.keystore.KeyProperties.DIGEST_SHA384;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 48;
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
        reset();
        return 48;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.LongDigest, com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = -3766243637369397544L;
        this.H2 = 7105036623409894663L;
        this.H3 = -7973340178411365097L;
        this.H4 = 1526699215303891257L;
        this.H5 = 7436329637833083697L;
        this.H6 = -8163818279084223215L;
        this.H7 = -2662702644619276377L;
        this.H8 = 5167115440072839076L;
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public com.android.internal.org.bouncycastle.util.Memoable copy() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public void reset(com.android.internal.org.bouncycastle.util.Memoable memoable) {
        super.copyIn((com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest) memoable);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[getEncodedStateSize()];
        super.populateState(bArr);
        return bArr;
    }
}
