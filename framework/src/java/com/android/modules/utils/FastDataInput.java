package com.android.modules.utils;

/* loaded from: classes5.dex */
public class FastDataInput implements java.io.DataInput, java.io.Closeable {
    protected static final int DEFAULT_BUFFER_SIZE = 32768;
    protected static final int MAX_UNSIGNED_SHORT = 65535;
    protected final byte[] mBuffer;
    protected final int mBufferCap;
    protected int mBufferLim;
    protected int mBufferPos;
    private java.io.InputStream mIn;
    private int mStringRefCount = 0;
    private java.lang.String[] mStringRefs = new java.lang.String[32];

    public FastDataInput(java.io.InputStream inputStream, int i) {
        this.mIn = (java.io.InputStream) java.util.Objects.requireNonNull(inputStream);
        if (i < 8) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mBuffer = newByteArray(i);
        this.mBufferCap = this.mBuffer.length;
    }

    public static com.android.modules.utils.FastDataInput obtain(java.io.InputStream inputStream) {
        return new com.android.modules.utils.FastDataInput(inputStream, 32768);
    }

    public void release() {
        this.mIn = null;
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    public byte[] newByteArray(int i) {
        return new byte[i];
    }

    protected void setInput(java.io.InputStream inputStream) {
        if (this.mIn != null) {
            throw new java.lang.IllegalStateException("setInput() called before calling release()");
        }
        this.mIn = (java.io.InputStream) java.util.Objects.requireNonNull(inputStream);
        this.mBufferPos = 0;
        this.mBufferLim = 0;
        this.mStringRefCount = 0;
    }

    protected void fill(int i) throws java.io.IOException {
        int i2 = this.mBufferLim - this.mBufferPos;
        java.lang.System.arraycopy(this.mBuffer, this.mBufferPos, this.mBuffer, 0, i2);
        this.mBufferPos = 0;
        this.mBufferLim = i2;
        int i3 = i - i2;
        while (i3 > 0) {
            int read = this.mIn.read(this.mBuffer, this.mBufferLim, this.mBufferCap - this.mBufferLim);
            if (read == -1) {
                throw new java.io.EOFException();
            }
            this.mBufferLim += read;
            i3 -= read;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mIn.close();
        release();
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) throws java.io.IOException {
        readFully(bArr, 0, bArr.length);
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mBufferCap >= i2) {
            if (this.mBufferLim - this.mBufferPos < i2) {
                fill(i2);
            }
            java.lang.System.arraycopy(this.mBuffer, this.mBufferPos, bArr, i, i2);
            this.mBufferPos += i2;
            return;
        }
        int i3 = this.mBufferLim - this.mBufferPos;
        java.lang.System.arraycopy(this.mBuffer, this.mBufferPos, bArr, i, i3);
        this.mBufferPos += i3;
        int i4 = i + i3;
        int i5 = i2 - i3;
        while (i5 > 0) {
            int read = this.mIn.read(bArr, i4, i5);
            if (read == -1) {
                throw new java.io.EOFException();
            }
            i4 += read;
            i5 -= read;
        }
    }

    @Override // java.io.DataInput
    public java.lang.String readUTF() throws java.io.IOException {
        int readUnsignedShort = readUnsignedShort();
        if (this.mBufferCap > readUnsignedShort) {
            if (this.mBufferLim - this.mBufferPos < readUnsignedShort) {
                fill(readUnsignedShort);
            }
            java.lang.String decode = com.android.modules.utils.ModifiedUtf8.decode(this.mBuffer, new char[readUnsignedShort], this.mBufferPos, readUnsignedShort);
            this.mBufferPos += readUnsignedShort;
            return decode;
        }
        byte[] newByteArray = newByteArray(readUnsignedShort + 1);
        readFully(newByteArray, 0, readUnsignedShort);
        return com.android.modules.utils.ModifiedUtf8.decode(newByteArray, new char[readUnsignedShort], 0, readUnsignedShort);
    }

    public java.lang.String readInternedUTF() throws java.io.IOException {
        int readUnsignedShort = readUnsignedShort();
        if (readUnsignedShort == 65535) {
            java.lang.String readUTF = readUTF();
            if (this.mStringRefCount < 65535) {
                if (this.mStringRefCount == this.mStringRefs.length) {
                    this.mStringRefs = (java.lang.String[]) java.util.Arrays.copyOf(this.mStringRefs, this.mStringRefCount + (this.mStringRefCount >> 1));
                }
                java.lang.String[] strArr = this.mStringRefs;
                int i = this.mStringRefCount;
                this.mStringRefCount = i + 1;
                strArr[i] = readUTF;
            }
            return readUTF;
        }
        if (readUnsignedShort >= this.mStringRefs.length) {
            throw new java.io.IOException("Invalid interned string reference " + readUnsignedShort + " for " + this.mStringRefs.length + " interned strings");
        }
        return this.mStringRefs[readUnsignedShort];
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws java.io.IOException {
        return readByte() != 0;
    }

    public byte peekByte() throws java.io.IOException {
        if (this.mBufferLim - this.mBufferPos < 1) {
            fill(1);
        }
        return this.mBuffer[this.mBufferPos];
    }

    @Override // java.io.DataInput
    public byte readByte() throws java.io.IOException {
        if (this.mBufferLim - this.mBufferPos < 1) {
            fill(1);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        return bArr[i];
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws java.io.IOException {
        return java.lang.Byte.toUnsignedInt(readByte());
    }

    @Override // java.io.DataInput
    public short readShort() throws java.io.IOException {
        if (this.mBufferLim - this.mBufferPos < 2) {
            fill(2);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        return (short) (i2 | ((bArr2[i3] & 255) << 0));
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws java.io.IOException {
        return java.lang.Short.toUnsignedInt(readShort());
    }

    @Override // java.io.DataInput
    public char readChar() throws java.io.IOException {
        return (char) readShort();
    }

    @Override // java.io.DataInput
    public int readInt() throws java.io.IOException {
        if (this.mBufferLim - this.mBufferPos < 4) {
            fill(4);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        int i4 = i2 | ((bArr2[i3] & 255) << 16);
        byte[] bArr3 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        int i6 = i4 | ((bArr3[i5] & 255) << 8);
        byte[] bArr4 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        return i6 | ((bArr4[i7] & 255) << 0);
    }

    @Override // java.io.DataInput
    public long readLong() throws java.io.IOException {
        if (this.mBufferLim - this.mBufferPos < 8) {
            fill(8);
        }
        byte[] bArr = this.mBuffer;
        int i = this.mBufferPos;
        this.mBufferPos = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        int i4 = i2 | ((bArr2[i3] & 255) << 16);
        byte[] bArr3 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        int i6 = i4 | ((bArr3[i5] & 255) << 8);
        byte[] bArr4 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        int i8 = i6 | ((bArr4[i7] & 255) << 0);
        byte[] bArr5 = this.mBuffer;
        int i9 = this.mBufferPos;
        this.mBufferPos = i9 + 1;
        int i10 = (bArr5[i9] & 255) << 24;
        byte[] bArr6 = this.mBuffer;
        int i11 = this.mBufferPos;
        this.mBufferPos = i11 + 1;
        int i12 = i10 | ((bArr6[i11] & 255) << 16);
        byte[] bArr7 = this.mBuffer;
        int i13 = this.mBufferPos;
        this.mBufferPos = i13 + 1;
        int i14 = ((bArr7[i13] & 255) << 8) | i12;
        byte[] bArr8 = this.mBuffer;
        int i15 = this.mBufferPos;
        this.mBufferPos = i15 + 1;
        return ((i14 | ((bArr8[i15] & 255) << 0)) & 4294967295L) | (i8 << 32);
    }

    @Override // java.io.DataInput
    public float readFloat() throws java.io.IOException {
        return java.lang.Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public double readDouble() throws java.io.IOException {
        return java.lang.Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.io.DataInput
    public java.lang.String readLine() throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }
}
