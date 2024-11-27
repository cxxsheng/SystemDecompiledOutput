package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public abstract class GeneralDigest implements com.android.internal.org.bouncycastle.crypto.ExtendedDigest, com.android.internal.org.bouncycastle.util.Memoable {
    private static final int BYTE_LENGTH = 64;
    private long byteCount;
    private final byte[] xBuf;
    private int xBufOff;

    protected abstract void processBlock();

    protected abstract void processLength(long j);

    protected abstract void processWord(byte[] bArr, int i);

    protected GeneralDigest() {
        this.xBuf = new byte[4];
        this.xBufOff = 0;
    }

    protected GeneralDigest(com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest generalDigest) {
        this.xBuf = new byte[4];
        copyIn(generalDigest);
    }

    protected GeneralDigest(byte[] bArr) {
        this.xBuf = new byte[4];
        java.lang.System.arraycopy(bArr, 0, this.xBuf, 0, this.xBuf.length);
        this.xBufOff = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, 4);
        this.byteCount = com.android.internal.org.bouncycastle.util.Pack.bigEndianToLong(bArr, 8);
    }

    protected void copyIn(com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest generalDigest) {
        java.lang.System.arraycopy(generalDigest.xBuf, 0, this.xBuf, 0, generalDigest.xBuf.length);
        this.xBufOff = generalDigest.xBufOff;
        this.byteCount = generalDigest.byteCount;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        this.xBufOff = i + 1;
        bArr[i] = b;
        if (this.xBufOff == this.xBuf.length) {
            processWord(this.xBuf, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        int i3 = 0;
        int max = java.lang.Math.max(0, i2);
        if (this.xBufOff != 0) {
            int i4 = 0;
            while (true) {
                if (i4 >= max) {
                    i3 = i4;
                    break;
                }
                byte[] bArr2 = this.xBuf;
                int i5 = this.xBufOff;
                this.xBufOff = i5 + 1;
                int i6 = i4 + 1;
                bArr2[i5] = bArr[i4 + i];
                if (this.xBufOff == 4) {
                    processWord(this.xBuf, 0);
                    this.xBufOff = 0;
                    i3 = i6;
                    break;
                }
                i4 = i6;
            }
        }
        int i7 = ((max - i3) & (-4)) + i3;
        while (i3 < i7) {
            processWord(bArr, i + i3);
            i3 += 4;
        }
        while (i3 < max) {
            byte[] bArr3 = this.xBuf;
            int i8 = this.xBufOff;
            this.xBufOff = i8 + 1;
            bArr3[i8] = bArr[i3 + i];
            i3++;
        }
        this.byteCount += max;
    }

    public void finish() {
        long j = this.byteCount << 3;
        update(Byte.MIN_VALUE);
        while (this.xBufOff != 0) {
            update((byte) 0);
        }
        processLength(j);
        processBlock();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        for (int i = 0; i < this.xBuf.length; i++) {
            this.xBuf[i] = 0;
        }
    }

    protected void populateState(byte[] bArr) {
        java.lang.System.arraycopy(this.xBuf, 0, bArr, 0, this.xBufOff);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(this.xBufOff, bArr, 4);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(this.byteCount, bArr, 8);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 64;
    }
}
