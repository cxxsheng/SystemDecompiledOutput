package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public class WireBuffer {
    private static final int BUFFER_SIZE = 1048576;
    byte[] mBuffer;
    int mIndex;
    int mMaxSize;
    int mSize;
    int mStartingIndex;

    public WireBuffer(int i) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        this.mMaxSize = i;
        this.mBuffer = new byte[this.mMaxSize];
    }

    public WireBuffer() {
        this(1048576);
    }

    public void resize(int i) {
        if (this.mSize + i >= this.mMaxSize) {
            this.mMaxSize = java.lang.Math.max(this.mMaxSize * 2, this.mSize + i);
            this.mBuffer = java.util.Arrays.copyOf(this.mBuffer, this.mMaxSize);
        }
    }

    public byte[] getBuffer() {
        return this.mBuffer;
    }

    public int getMax_size() {
        return this.mMaxSize;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getSize() {
        return this.mSize;
    }

    public void setIndex(int i) {
        this.mIndex = i;
    }

    public void start(int i) {
        this.mStartingIndex = this.mIndex;
        writeByte(i);
    }

    public void startWithSize(int i) {
        this.mStartingIndex = this.mIndex;
        writeByte(i);
        this.mIndex += 4;
    }

    public void endWithSize() {
        int i = this.mIndex - this.mStartingIndex;
        int i2 = this.mIndex;
        this.mIndex = this.mStartingIndex + 1;
        writeInt(i);
        this.mIndex = i2;
    }

    public void reset(int i) {
        this.mIndex = 0;
        this.mStartingIndex = 0;
        this.mSize = 0;
        if (i > this.mMaxSize) {
            resize(i);
        }
    }

    public int size() {
        return this.mSize;
    }

    public boolean available() {
        return this.mSize - this.mIndex > 0;
    }

    public int readOperationType() {
        return readByte();
    }

    public boolean readBoolean() {
        byte b = this.mBuffer[this.mIndex];
        this.mIndex++;
        return b == 1;
    }

    public int readByte() {
        byte b = this.mBuffer[this.mIndex];
        this.mIndex++;
        return b;
    }

    public int readShort() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        int i2 = (bArr[i] & 255) << 8;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        return i2 + ((bArr2[i3] & 255) << 0);
    }

    public int readInt() {
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        int i4 = (bArr2[i3] & 255) << 16;
        byte[] bArr3 = this.mBuffer;
        int i5 = this.mIndex;
        this.mIndex = i5 + 1;
        int i6 = (bArr3[i5] & 255) << 8;
        byte[] bArr4 = this.mBuffer;
        int i7 = this.mIndex;
        this.mIndex = i7 + 1;
        return i2 + i4 + i6 + ((bArr4[i7] & 255) << 0);
    }

    public long readLong() {
        byte[] bArr = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j = (bArr[r2] & 255) << 56;
        byte[] bArr2 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j2 = (bArr2[r6] & 255) << 48;
        byte[] bArr3 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j3 = (bArr3[r8] & 255) << 40;
        byte[] bArr4 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j4 = (bArr4[r10] & 255) << 32;
        byte[] bArr5 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j5 = (bArr5[r12] & 255) << 24;
        byte[] bArr6 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j6 = (bArr6[r14] & 255) << 16;
        byte[] bArr7 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        long j7 = (bArr7[r3] & 255) << 8;
        byte[] bArr8 = this.mBuffer;
        this.mIndex = this.mIndex + 1;
        return j + j2 + j3 + j4 + j5 + j6 + j7 + ((bArr8[r3] & 255) << 0);
    }

    public float readFloat() {
        return java.lang.Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return java.lang.Double.longBitsToDouble(readLong());
    }

    public byte[] readBuffer() {
        int readInt = readInt();
        byte[] copyOfRange = java.util.Arrays.copyOfRange(this.mBuffer, this.mIndex, this.mIndex + readInt);
        this.mIndex += readInt;
        return copyOfRange;
    }

    public byte[] readBuffer(int i) {
        int readInt = readInt();
        if (readInt < 0 || readInt > i) {
            throw new java.lang.RuntimeException("attempt read a buff of invalid size 0 <= " + readInt + " > " + i);
        }
        byte[] copyOfRange = java.util.Arrays.copyOfRange(this.mBuffer, this.mIndex, this.mIndex + readInt);
        this.mIndex += readInt;
        return copyOfRange;
    }

    public java.lang.String readUTF8() {
        return new java.lang.String(readBuffer());
    }

    public java.lang.String readUTF8(int i) {
        return new java.lang.String(readBuffer(i));
    }

    public void writeBoolean(boolean z) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = z ? (byte) 1 : (byte) 0;
        this.mSize++;
    }

    public void writeByte(int i) {
        resize(1);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr[i2] = (byte) i;
        this.mSize++;
    }

    public void writeShort(int i) {
        resize(2);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        bArr2[i3] = (byte) (i & 255);
        this.mSize += 2;
    }

    public void writeInt(int i) {
        resize(4);
        byte[] bArr = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        byte[] bArr2 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        bArr2[i3] = (byte) ((i >>> 16) & 255);
        byte[] bArr3 = this.mBuffer;
        int i4 = this.mIndex;
        this.mIndex = i4 + 1;
        bArr3[i4] = (byte) ((i >>> 8) & 255);
        byte[] bArr4 = this.mBuffer;
        int i5 = this.mIndex;
        this.mIndex = i5 + 1;
        bArr4[i5] = (byte) (i & 255);
        this.mSize += 4;
    }

    public void writeLong(long j) {
        resize(8);
        byte[] bArr = this.mBuffer;
        int i = this.mIndex;
        this.mIndex = i + 1;
        bArr[i] = (byte) ((j >>> 56) & 255);
        byte[] bArr2 = this.mBuffer;
        int i2 = this.mIndex;
        this.mIndex = i2 + 1;
        bArr2[i2] = (byte) ((j >>> 48) & 255);
        byte[] bArr3 = this.mBuffer;
        int i3 = this.mIndex;
        this.mIndex = i3 + 1;
        bArr3[i3] = (byte) ((j >>> 40) & 255);
        byte[] bArr4 = this.mBuffer;
        int i4 = this.mIndex;
        this.mIndex = i4 + 1;
        bArr4[i4] = (byte) ((j >>> 32) & 255);
        byte[] bArr5 = this.mBuffer;
        int i5 = this.mIndex;
        this.mIndex = i5 + 1;
        bArr5[i5] = (byte) ((j >>> 24) & 255);
        byte[] bArr6 = this.mBuffer;
        int i6 = this.mIndex;
        this.mIndex = i6 + 1;
        bArr6[i6] = (byte) ((j >>> 16) & 255);
        byte[] bArr7 = this.mBuffer;
        int i7 = this.mIndex;
        this.mIndex = i7 + 1;
        bArr7[i7] = (byte) ((j >>> 8) & 255);
        byte[] bArr8 = this.mBuffer;
        int i8 = this.mIndex;
        this.mIndex = i8 + 1;
        bArr8[i8] = (byte) (j & 255);
        this.mSize += 8;
    }

    public void writeFloat(float f) {
        writeInt(java.lang.Float.floatToRawIntBits(f));
    }

    public void writeDouble(double d) {
        writeLong(java.lang.Double.doubleToRawLongBits(d));
    }

    public void writeBuffer(byte[] bArr) {
        resize(bArr.length + 4);
        writeInt(bArr.length);
        for (byte b : bArr) {
            byte[] bArr2 = this.mBuffer;
            int i = this.mIndex;
            this.mIndex = i + 1;
            bArr2[i] = b;
        }
        this.mSize += bArr.length;
    }

    public void writeUTF8(java.lang.String str) {
        writeBuffer(str.getBytes());
    }
}
