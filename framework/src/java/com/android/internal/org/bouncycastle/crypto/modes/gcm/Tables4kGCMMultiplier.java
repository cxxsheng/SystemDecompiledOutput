package com.android.internal.org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes4.dex */
public class Tables4kGCMMultiplier implements com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier {
    private byte[] H;
    private long[][] T;

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        if (this.T == null) {
            this.T = (long[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Long.TYPE, 256, 2);
        } else if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.H, bArr)) {
            return;
        }
        this.H = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asLongs(this.H, this.T[1]);
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiplyP7(this.T[1], this.T[1]);
        for (int i = 2; i < 256; i += 2) {
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.divideP(this.T[i >> 1], this.T[i]);
            com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.xor(this.T[i], this.T[1], this.T[i + 1]);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        long[] jArr = this.T[bArr[15] & 255];
        long j = jArr[0];
        long j2 = jArr[1];
        for (int i = 14; i >= 0; i--) {
            long[] jArr2 = this.T[bArr[i] & 255];
            long j3 = j2 << 56;
            j2 = ((j2 >>> 8) | (j << 56)) ^ jArr2[1];
            j = (((((j >>> 8) ^ jArr2[0]) ^ j3) ^ (j3 >>> 1)) ^ (j3 >>> 2)) ^ (j3 >>> 7);
        }
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(j, bArr, 0);
        com.android.internal.org.bouncycastle.util.Pack.longToBigEndian(j2, bArr, 8);
    }
}
