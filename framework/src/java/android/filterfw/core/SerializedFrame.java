package android.filterfw.core;

/* loaded from: classes.dex */
public class SerializedFrame extends android.filterfw.core.Frame {
    private static final int INITIAL_CAPACITY = 64;
    private android.filterfw.core.SerializedFrame.DirectByteOutputStream mByteOutputStream;
    private java.io.ObjectOutputStream mObjectOut;

    private class DirectByteOutputStream extends java.io.OutputStream {
        private byte[] mBuffer;
        private int mOffset = 0;
        private int mDataOffset = 0;

        public DirectByteOutputStream(int i) {
            this.mBuffer = null;
            this.mBuffer = new byte[i];
        }

        private final void ensureFit(int i) {
            if (this.mOffset + i > this.mBuffer.length) {
                byte[] bArr = this.mBuffer;
                this.mBuffer = new byte[java.lang.Math.max(this.mOffset + i, this.mBuffer.length * 2)];
                java.lang.System.arraycopy(bArr, 0, this.mBuffer, 0, this.mOffset);
            }
        }

        public final void markHeaderEnd() {
            this.mDataOffset = this.mOffset;
        }

        public final int getSize() {
            return this.mOffset;
        }

        public byte[] getByteArray() {
            return this.mBuffer;
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr) {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) {
            ensureFit(i2);
            java.lang.System.arraycopy(bArr, i, this.mBuffer, this.mOffset, i2);
            this.mOffset += i2;
        }

        @Override // java.io.OutputStream
        public final void write(int i) {
            ensureFit(1);
            byte[] bArr = this.mBuffer;
            int i2 = this.mOffset;
            this.mOffset = i2 + 1;
            bArr[i2] = (byte) i;
        }

        public final void reset() {
            this.mOffset = this.mDataOffset;
        }

        public final android.filterfw.core.SerializedFrame.DirectByteInputStream getInputStream() {
            return android.filterfw.core.SerializedFrame.this.new DirectByteInputStream(this.mBuffer, this.mOffset);
        }
    }

    private class DirectByteInputStream extends java.io.InputStream {
        private byte[] mBuffer;
        private int mPos = 0;
        private int mSize;

        public DirectByteInputStream(byte[] bArr, int i) {
            this.mBuffer = bArr;
            this.mSize = i;
        }

        @Override // java.io.InputStream
        public final int available() {
            return this.mSize - this.mPos;
        }

        @Override // java.io.InputStream
        public final int read() {
            if (this.mPos >= this.mSize) {
                return -1;
            }
            byte[] bArr = this.mBuffer;
            int i = this.mPos;
            this.mPos = i + 1;
            return bArr[i] & 255;
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) {
            if (this.mPos >= this.mSize) {
                return -1;
            }
            if (this.mPos + i2 > this.mSize) {
                i2 = this.mSize - this.mPos;
            }
            java.lang.System.arraycopy(this.mBuffer, this.mPos, bArr, i, i2);
            this.mPos += i2;
            return i2;
        }

        @Override // java.io.InputStream
        public final long skip(long j) {
            if (this.mPos + j > this.mSize) {
                j = this.mSize - this.mPos;
            }
            if (j < 0) {
                return 0L;
            }
            this.mPos = (int) (this.mPos + j);
            return j;
        }
    }

    SerializedFrame(android.filterfw.core.FrameFormat frameFormat, android.filterfw.core.FrameManager frameManager) {
        super(frameFormat, frameManager);
        setReusable(false);
        try {
            this.mByteOutputStream = new android.filterfw.core.SerializedFrame.DirectByteOutputStream(64);
            this.mObjectOut = new java.io.ObjectOutputStream(this.mByteOutputStream);
            this.mByteOutputStream.markHeaderEnd();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Could not create serialization streams for SerializedFrame!", e);
        }
    }

    static android.filterfw.core.SerializedFrame wrapObject(java.lang.Object obj, android.filterfw.core.FrameManager frameManager) {
        android.filterfw.core.SerializedFrame serializedFrame = new android.filterfw.core.SerializedFrame(android.filterfw.format.ObjectFormat.fromObject(obj, 1), frameManager);
        serializedFrame.setObjectValue(obj);
        return serializedFrame;
    }

    @Override // android.filterfw.core.Frame
    protected boolean hasNativeAllocation() {
        return false;
    }

    @Override // android.filterfw.core.Frame
    protected void releaseNativeAllocation() {
    }

    @Override // android.filterfw.core.Frame
    public java.lang.Object getObjectValue() {
        return deserializeObjectValue();
    }

    @Override // android.filterfw.core.Frame
    public void setInts(int[] iArr) {
        assertFrameMutable();
        setGenericObjectValue(iArr);
    }

    @Override // android.filterfw.core.Frame
    public int[] getInts() {
        java.lang.Object deserializeObjectValue = deserializeObjectValue();
        if (deserializeObjectValue instanceof int[]) {
            return (int[]) deserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setFloats(float[] fArr) {
        assertFrameMutable();
        setGenericObjectValue(fArr);
    }

    @Override // android.filterfw.core.Frame
    public float[] getFloats() {
        java.lang.Object deserializeObjectValue = deserializeObjectValue();
        if (deserializeObjectValue instanceof float[]) {
            return (float[]) deserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setData(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        assertFrameMutable();
        setGenericObjectValue(java.nio.ByteBuffer.wrap(byteBuffer.array(), i, i2));
    }

    @Override // android.filterfw.core.Frame
    public java.nio.ByteBuffer getData() {
        java.lang.Object deserializeObjectValue = deserializeObjectValue();
        if (deserializeObjectValue instanceof java.nio.ByteBuffer) {
            return (java.nio.ByteBuffer) deserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    public void setBitmap(android.graphics.Bitmap bitmap) {
        assertFrameMutable();
        setGenericObjectValue(bitmap);
    }

    @Override // android.filterfw.core.Frame
    public android.graphics.Bitmap getBitmap() {
        java.lang.Object deserializeObjectValue = deserializeObjectValue();
        if (deserializeObjectValue instanceof android.graphics.Bitmap) {
            return (android.graphics.Bitmap) deserializeObjectValue;
        }
        return null;
    }

    @Override // android.filterfw.core.Frame
    protected void setGenericObjectValue(java.lang.Object obj) {
        serializeObjectValue(obj);
    }

    private final void serializeObjectValue(java.lang.Object obj) {
        try {
            this.mByteOutputStream.reset();
            this.mObjectOut.writeObject(obj);
            this.mObjectOut.flush();
            this.mObjectOut.close();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Could not serialize object " + obj + " in " + this + "!", e);
        }
    }

    private final java.lang.Object deserializeObjectValue() {
        try {
            return new java.io.ObjectInputStream(this.mByteOutputStream.getInputStream()).readObject();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Could not deserialize object in " + this + "!", e);
        } catch (java.lang.ClassNotFoundException e2) {
            throw new java.lang.RuntimeException("Unable to deserialize object of unknown class in " + this + "!", e2);
        }
    }

    public java.lang.String toString() {
        return "SerializedFrame (" + getFormat() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
