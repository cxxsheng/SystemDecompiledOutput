package com.android.server.integrity.model;

/* loaded from: classes2.dex */
public class BitInputStream {
    private long mBitsRead;
    private byte mCurrentByte;
    private java.io.InputStream mInputStream;

    public BitInputStream(java.io.InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    public int getNext(int i) throws java.io.IOException {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i2 + 1;
            if (i2 < i) {
                if (this.mBitsRead % 8 == 0) {
                    this.mCurrentByte = getNextByte();
                }
                i3 = (i3 << 1) | ((this.mCurrentByte >>> (7 - ((int) (this.mBitsRead % 8)))) & 1);
                this.mBitsRead++;
                i2 = i4;
            } else {
                return i3;
            }
        }
    }

    public boolean hasNext() throws java.io.IOException {
        return this.mInputStream.available() > 0;
    }

    private byte getNextByte() throws java.io.IOException {
        return (byte) this.mInputStream.read();
    }
}
