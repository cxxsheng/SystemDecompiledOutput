package com.android.internal.util;

/* loaded from: classes5.dex */
public class BitwiseOutputStream {
    private byte[] mBuf;
    private int mEnd;
    private int mPos = 0;

    public static class AccessException extends java.lang.Exception {
        public AccessException(java.lang.String str) {
            super("BitwiseOutputStream access failed: " + str);
        }
    }

    public BitwiseOutputStream(int i) {
        this.mBuf = new byte[i];
        this.mEnd = i << 3;
    }

    public byte[] toByteArray() {
        int i = (this.mPos >>> 3) + ((this.mPos & 7) > 0 ? 1 : 0);
        byte[] bArr = new byte[i];
        java.lang.System.arraycopy(this.mBuf, 0, bArr, 0, i);
        return bArr;
    }

    private void possExpand(int i) {
        if (this.mPos + i < this.mEnd) {
            return;
        }
        int i2 = (this.mPos + i) >>> 2;
        byte[] bArr = new byte[i2];
        java.lang.System.arraycopy(this.mBuf, 0, bArr, 0, this.mEnd >>> 3);
        this.mBuf = bArr;
        this.mEnd = i2 << 3;
    }

    public void write(int i, int i2) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        if (i < 0 || i > 8) {
            throw new com.android.internal.util.BitwiseOutputStream.AccessException("illegal write (" + i + " bits)");
        }
        possExpand(i);
        int i3 = this.mPos >>> 3;
        int i4 = (16 - (this.mPos & 7)) - i;
        int i5 = (i2 & ((-1) >>> (32 - i))) << i4;
        this.mPos += i;
        byte[] bArr = this.mBuf;
        bArr[i3] = (byte) (bArr[i3] | (i5 >>> 8));
        if (i4 < 8) {
            byte[] bArr2 = this.mBuf;
            int i6 = i3 + 1;
            bArr2[i6] = (byte) ((i5 & 255) | bArr2[i6]);
        }
    }

    public void writeByteArray(int i, byte[] bArr) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int min = java.lang.Math.min(8, i - (i2 << 3));
            if (min > 0) {
                write(min, (byte) (bArr[i2] >>> (8 - min)));
            }
        }
    }

    public void skip(int i) {
        possExpand(i);
        this.mPos += i;
    }
}
