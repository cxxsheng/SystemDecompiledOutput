package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class Fingerprint {
    private static char[] encodingTable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.AM_PM, 'b', 'c', android.text.format.DateFormat.DATE, 'e', 'f'};
    private final byte[] fingerprint;

    public Fingerprint(byte[] bArr) {
        this(bArr, 160);
    }

    public Fingerprint(byte[] bArr, int i) {
        this.fingerprint = calculateFingerprint(bArr, i);
    }

    public byte[] getFingerprint() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.fingerprint);
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        for (int i = 0; i != this.fingerprint.length; i++) {
            if (i > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(encodingTable[(this.fingerprint[i] >>> 4) & 15]);
            stringBuffer.append(encodingTable[this.fingerprint[i] & 15]);
        }
        return stringBuffer.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.util.Fingerprint) {
            return com.android.internal.org.bouncycastle.util.Arrays.areEqual(((com.android.internal.org.bouncycastle.util.Fingerprint) obj).fingerprint, this.fingerprint);
        }
        return false;
    }

    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.fingerprint);
    }

    public static byte[] calculateFingerprint(byte[] bArr) {
        return calculateFingerprint(bArr, 160);
    }

    public static byte[] calculateFingerprint(byte[] bArr, int i) {
        if (i % 8 != 0) {
            throw new java.lang.IllegalArgumentException("bitLength must be a multiple of 8");
        }
        com.android.internal.org.bouncycastle.crypto.Digest sha256 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256();
        sha256.update(bArr, 0, bArr.length);
        int i2 = i / 8;
        byte[] bArr2 = new byte[i2];
        byte[] bArr3 = new byte[32];
        sha256.doFinal(bArr3, 0);
        if (i2 >= 32) {
            return bArr3;
        }
        java.lang.System.arraycopy(bArr3, 0, bArr2, 0, i2);
        return bArr2;
    }
}
