package com.android.internal.org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes4.dex */
public class Tables8kGCMMultiplier implements com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier {
    private byte[] H;
    private long[][][] T;

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        if (this.T == null) {
            this.T = (long[][][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Long.TYPE, 32, 16, 2);
        } else if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.H, bArr)) {
            return;
        }
        this.H = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        for (int i = 0; i < 32; i++) {
            long[][] jArr = this.T[i];
            if (i == 0) {
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asLongs(this.H, jArr[1]);
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiplyP3(jArr[1], jArr[1]);
            } else {
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiplyP4(this.T[i - 1][1], jArr[1]);
            }
            for (int i2 = 2; i2 < 16; i2 += 2) {
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.divideP(jArr[i2 >> 1], jArr[i2]);
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(jArr[i2], jArr[1], jArr[i2 + 1]);
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        long j = 0;
        long j2 = 0;
        for (int i = 15; i >= 0; i--) {
            int i2 = i + i;
            long[] jArr = this.T[i2 + 1][bArr[i] & 15];
            long[] jArr2 = this.T[i2][(bArr[i] & 240) >>> 4];
            j ^= jArr[0] ^ jArr2[0];
            j2 ^= jArr2[1] ^ jArr[1];
        }
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(j, bArr, 0);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(j2, bArr, 8);
    }
}
