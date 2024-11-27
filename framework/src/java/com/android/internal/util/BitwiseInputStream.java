package com.android.internal.util;

/* loaded from: classes5.dex */
public class BitwiseInputStream {
    private byte[] mBuf;
    private int mEnd;
    private int mPos = 0;

    public static class AccessException extends java.lang.Exception {
        public AccessException(java.lang.String str) {
            super("BitwiseInputStream access failed: " + str);
        }
    }

    public BitwiseInputStream(byte[] bArr) {
        this.mBuf = bArr;
        this.mEnd = bArr.length << 3;
    }

    public int available() {
        return this.mEnd - this.mPos;
    }

    public int read(int i) throws com.android.internal.util.BitwiseInputStream.AccessException {
        int i2 = this.mPos >>> 3;
        int i3 = (16 - (this.mPos & 7)) - i;
        if (i < 0 || i > 8 || this.mPos + i > this.mEnd) {
            throw new com.android.internal.util.BitwiseInputStream.AccessException("illegal read (pos " + this.mPos + ", end " + this.mEnd + ", bits " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        int i4 = (this.mBuf[i2] & 255) << 8;
        if (i3 < 8) {
            i4 |= this.mBuf[i2 + 1] & 255;
        }
        int i5 = (i4 >>> i3) & ((-1) >>> (32 - i));
        this.mPos += i;
        return i5;
    }

    public byte[] readByteArray(int i) throws com.android.internal.util.BitwiseInputStream.AccessException {
        int i2 = (i >>> 3) + ((i & 7) > 0 ? 1 : 0);
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int min = java.lang.Math.min(8, i - (i3 << 3));
            bArr[i3] = (byte) (read(min) << (8 - min));
        }
        return bArr;
    }

    public void skip(int i) throws com.android.internal.util.BitwiseInputStream.AccessException {
        if (this.mPos + i > this.mEnd) {
            throw new com.android.internal.util.BitwiseInputStream.AccessException("illegal skip (pos " + this.mPos + ", end " + this.mEnd + ", bits " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mPos += i;
    }
}
