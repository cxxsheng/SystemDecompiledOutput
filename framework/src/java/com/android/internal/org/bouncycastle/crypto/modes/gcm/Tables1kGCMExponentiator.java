package com.android.internal.org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes4.dex */
public class Tables1kGCMExponentiator implements com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator {
    private java.util.Vector lookupPowX2;

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void init(byte[] bArr) {
        long[] asLongs = com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asLongs(bArr);
        if (this.lookupPowX2 != null && com.android.internal.org.bouncycastle.util.Arrays.areEqual(asLongs, (long[]) this.lookupPowX2.elementAt(0))) {
            return;
        }
        this.lookupPowX2 = new java.util.Vector(8);
        this.lookupPowX2.addElement(asLongs);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator
    public void exponentiateX(long j, byte[] bArr) {
        long[] oneAsLongs = com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.oneAsLongs();
        int i = 0;
        while (j > 0) {
            if ((1 & j) != 0) {
                ensureAvailable(i);
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.multiply(oneAsLongs, (long[]) this.lookupPowX2.elementAt(i));
            }
            i++;
            j >>>= 1;
        }
        com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.asBytes(oneAsLongs, bArr);
    }

    private void ensureAvailable(int i) {
        int size = this.lookupPowX2.size();
        if (size <= i) {
            long[] jArr = (long[]) this.lookupPowX2.elementAt(size - 1);
            do {
                jArr = com.android.internal.org.bouncycastle.util.Arrays.clone(jArr);
                com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil.square(jArr, jArr);
                this.lookupPowX2.addElement(jArr);
                size++;
            } while (size <= i);
        }
    }
}
