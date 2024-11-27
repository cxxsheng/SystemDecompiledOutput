package com.android.internal.org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes4.dex */
public class BasicGCMExponentiator implements com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator {
    private long[] x;

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        this.x = com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asLongs(bArr);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j, byte[] bArr) {
        long[] oneAsLongs = com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.oneAsLongs();
        if (j > 0) {
            long[] clone = com.android.internal.org.bouncycastle.util.Arrays.clone(this.x);
            do {
                if ((1 & j) != 0) {
                    com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiply(oneAsLongs, clone);
                }
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.square(clone, clone);
                j >>>= 1;
            } while (j > 0);
        }
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asBytes(oneAsLongs, bArr);
    }
}
