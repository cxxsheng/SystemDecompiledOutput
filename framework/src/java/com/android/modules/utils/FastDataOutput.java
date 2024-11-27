package com.android.modules.utils;

/* loaded from: classes5.dex */
public class FastDataOutput implements java.io.DataOutput, java.io.Flushable, java.io.Closeable {
    protected static final int DEFAULT_BUFFER_SIZE = 32768;
    protected static final int MAX_UNSIGNED_SHORT = 65535;
    protected final byte[] mBuffer;
    protected final int mBufferCap;
    protected int mBufferPos;
    private java.io.OutputStream mOut;
    private final java.util.HashMap<java.lang.String, java.lang.Integer> mStringRefs = new java.util.HashMap<>();

    public FastDataOutput(java.io.OutputStream outputStream, int i) {
        if (i < 8) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mBuffer = newByteArray(i);
        this.mBufferCap = this.mBuffer.length;
        setOutput(outputStream);
    }

    public static com.android.modules.utils.FastDataOutput obtain(java.io.OutputStream outputStream) {
        return new com.android.modules.utils.FastDataOutput(outputStream, 32768);
    }

    public void release() {
        if (this.mBufferPos > 0) {
            throw new java.lang.IllegalStateException("Lingering data, call flush() before releasing.");
        }
        this.mOut = null;
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    public byte[] newByteArray(int i) {
        return new byte[i];
    }

    protected void setOutput(java.io.OutputStream outputStream) {
        if (this.mOut != null) {
            throw new java.lang.IllegalStateException("setOutput() called before calling release()");
        }
        this.mOut = (java.io.OutputStream) java.util.Objects.requireNonNull(outputStream);
        this.mBufferPos = 0;
        this.mStringRefs.clear();
    }

    protected void drain() throws java.io.IOException {
        if (this.mBufferPos > 0) {
            this.mOut.write(this.mBuffer, 0, this.mBufferPos);
            this.mBufferPos = 0;
        }
    }

    @Override // java.io.Flushable
    public void flush() throws java.io.IOException {
        drain();
        this.mOut.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mOut.close();
        release();
    }

    @Override // java.io.DataOutput
    public void write(int i) throws java.io.IOException {
        writeByte(i);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr) throws java.io.IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mBufferCap < i2) {
            drain();
            this.mOut.write(bArr, i, i2);
        } else {
            if (this.mBufferCap - this.mBufferPos < i2) {
                drain();
            }
            java.lang.System.arraycopy(bArr, i, this.mBuffer, this.mBufferPos, i2);
            this.mBufferPos += i2;
        }
    }

    @Override // java.io.DataOutput
    public void writeUTF(java.lang.String str) throws java.io.IOException {
        int countBytes = (int) com.android.modules.utils.ModifiedUtf8.countBytes(str, false);
        if (countBytes > 65535) {
            throw new java.io.IOException("Modified UTF-8 length too large: " + countBytes);
        }
        int i = countBytes + 2;
        if (this.mBufferCap >= i) {
            if (this.mBufferCap - this.mBufferPos < i) {
                drain();
            }
            writeShort(countBytes);
            com.android.modules.utils.ModifiedUtf8.encode(this.mBuffer, this.mBufferPos, str);
            this.mBufferPos += countBytes;
            return;
        }
        byte[] newByteArray = newByteArray(countBytes + 1);
        com.android.modules.utils.ModifiedUtf8.encode(newByteArray, 0, str);
        writeShort(countBytes);
        write(newByteArray, 0, countBytes);
    }

    public void writeInternedUTF(java.lang.String str) throws java.io.IOException {
        java.lang.Integer num = this.mStringRefs.get(str);
        if (num != null) {
            writeShort(num.intValue());
            return;
        }
        writeShort(65535);
        writeUTF(str);
        java.lang.Integer valueOf = java.lang.Integer.valueOf(this.mStringRefs.size());
        if (valueOf.intValue() < 65535) {
            this.mStringRefs.put(str, valueOf);
        }
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws java.io.IOException {
        writeByte(z ? 1 : 0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i) throws java.io.IOException {
        if (this.mBufferCap - this.mBufferPos < 1) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i) throws java.io.IOException {
        if (this.mBufferCap - this.mBufferPos < 2) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 8) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        bArr2[i3] = (byte) ((i >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeChar(int i) throws java.io.IOException {
        writeShort((short) i);
    }

    @Override // java.io.DataOutput
    public void writeInt(int i) throws java.io.IOException {
        if (this.mBufferCap - this.mBufferPos < 4) {
            drain();
        }
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        bArr2[i3] = (byte) ((i >> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i4 = this.mBufferPos;
        this.mBufferPos = i4 + 1;
        bArr3[i4] = (byte) ((i >> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        bArr4[i5] = (byte) ((i >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j) throws java.io.IOException {
        if (this.mBufferCap - this.mBufferPos < 8) {
            drain();
        }
        int i = (int) (j >> 32);
        byte[] bArr = this.mBuffer;
        int i2 = this.mBufferPos;
        this.mBufferPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mBufferPos;
        this.mBufferPos = i3 + 1;
        bArr2[i3] = (byte) ((i >> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i4 = this.mBufferPos;
        this.mBufferPos = i4 + 1;
        bArr3[i4] = (byte) ((i >> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i5 = this.mBufferPos;
        this.mBufferPos = i5 + 1;
        bArr4[i5] = (byte) ((i >> 0) & 255);
        int i6 = (int) j;
        byte[] bArr5 = this.mBuffer;
        int i7 = this.mBufferPos;
        this.mBufferPos = i7 + 1;
        bArr5[i7] = (byte) ((i6 >> 24) & 255);
        byte[] bArr6 = this.mBuffer;
        int i8 = this.mBufferPos;
        this.mBufferPos = i8 + 1;
        bArr6[i8] = (byte) ((i6 >> 16) & 255);
        byte[] bArr7 = this.mBuffer;
        int i9 = this.mBufferPos;
        this.mBufferPos = i9 + 1;
        bArr7[i9] = (byte) ((i6 >> 8) & 255);
        byte[] bArr8 = this.mBuffer;
        int i10 = this.mBufferPos;
        this.mBufferPos = i10 + 1;
        bArr8[i10] = (byte) ((i6 >> 0) & 255);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f) throws java.io.IOException {
        writeInt(java.lang.Float.floatToIntBits(f));
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d) throws java.io.IOException {
        writeLong(java.lang.Double.doubleToLongBits(d));
    }

    @Override // java.io.DataOutput
    public void writeBytes(java.lang.String str) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // java.io.DataOutput
    public void writeChars(java.lang.String str) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException();
    }
}
