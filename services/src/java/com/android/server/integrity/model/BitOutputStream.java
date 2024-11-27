package com.android.server.integrity.model;

/* loaded from: classes2.dex */
public class BitOutputStream {
    private static final int BUFFER_SIZE = 4096;
    private final byte[] mBuffer = new byte[4096];
    private int mNextBitIndex = 0;
    private final java.io.OutputStream mOutputStream;

    public BitOutputStream(java.io.OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    public void setNext(int i, int i2) throws java.io.IOException {
        if (i <= 0) {
            return;
        }
        int i3 = 1 << (i - 1);
        while (true) {
            int i4 = i - 1;
            if (i > 0) {
                setNext((i2 & i3) != 0);
                i3 >>>= 1;
                i = i4;
            } else {
                return;
            }
        }
    }

    public void setNext(boolean z) throws java.io.IOException {
        int i = this.mNextBitIndex / 8;
        if (i == 4096) {
            this.mOutputStream.write(this.mBuffer);
            reset();
            i = 0;
        }
        if (z) {
            byte[] bArr = this.mBuffer;
            bArr[i] = (byte) (bArr[i] | (1 << (7 - (this.mNextBitIndex % 8))));
        }
        this.mNextBitIndex++;
    }

    public void setNext() throws java.io.IOException {
        setNext(true);
    }

    public void flush() throws java.io.IOException {
        int i = this.mNextBitIndex / 8;
        if (this.mNextBitIndex % 8 != 0) {
            i++;
        }
        this.mOutputStream.write(this.mBuffer, 0, i);
        reset();
    }

    private void reset() {
        this.mNextBitIndex = 0;
        java.util.Arrays.fill(this.mBuffer, (byte) 0);
    }
}
