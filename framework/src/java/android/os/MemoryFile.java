package android.os;

/* loaded from: classes3.dex */
public class MemoryFile {
    private static java.lang.String TAG = "MemoryFile";
    private boolean mAllowPurging = false;
    private java.nio.ByteBuffer mMapping;
    private android.os.SharedMemory mSharedMemory;

    private static native int native_get_size(java.io.FileDescriptor fileDescriptor) throws java.io.IOException;

    private static native boolean native_pin(java.io.FileDescriptor fileDescriptor, boolean z) throws java.io.IOException;

    public MemoryFile(java.lang.String str, int i) throws java.io.IOException {
        try {
            this.mSharedMemory = android.os.SharedMemory.create(str, i);
            this.mMapping = this.mSharedMemory.mapReadWrite();
        } catch (android.system.ErrnoException e) {
            e.rethrowAsIOException();
        }
    }

    public void close() {
        deactivate();
        this.mSharedMemory.close();
    }

    void deactivate() {
        if (this.mMapping != null) {
            android.os.SharedMemory.unmap(this.mMapping);
            this.mMapping = null;
        }
    }

    private void checkActive() throws java.io.IOException {
        if (this.mMapping == null) {
            throw new java.io.IOException("MemoryFile has been deactivated");
        }
    }

    private void beginAccess() throws java.io.IOException {
        checkActive();
        if (this.mAllowPurging && native_pin(this.mSharedMemory.getFileDescriptor(), true)) {
            throw new java.io.IOException("MemoryFile has been purged");
        }
    }

    private void endAccess() throws java.io.IOException {
        if (this.mAllowPurging) {
            native_pin(this.mSharedMemory.getFileDescriptor(), false);
        }
    }

    public int length() {
        return this.mSharedMemory.getSize();
    }

    @java.lang.Deprecated
    public boolean isPurgingAllowed() {
        return this.mAllowPurging;
    }

    @java.lang.Deprecated
    public synchronized boolean allowPurging(boolean z) throws java.io.IOException {
        boolean z2;
        z2 = this.mAllowPurging;
        if (z2 != z) {
            native_pin(this.mSharedMemory.getFileDescriptor(), !z);
            this.mAllowPurging = z;
        }
        return z2;
    }

    public java.io.InputStream getInputStream() {
        return new android.os.MemoryFile.MemoryInputStream();
    }

    public java.io.OutputStream getOutputStream() {
        return new android.os.MemoryFile.MemoryOutputStream();
    }

    public int readBytes(byte[] bArr, int i, int i2, int i3) throws java.io.IOException {
        beginAccess();
        try {
            this.mMapping.position(i);
            this.mMapping.get(bArr, i2, i3);
            return i3;
        } finally {
            endAccess();
        }
    }

    public void writeBytes(byte[] bArr, int i, int i2, int i3) throws java.io.IOException {
        beginAccess();
        try {
            this.mMapping.position(i2);
            this.mMapping.put(bArr, i, i3);
        } finally {
            endAccess();
        }
    }

    public java.io.FileDescriptor getFileDescriptor() throws java.io.IOException {
        return this.mSharedMemory.getFileDescriptor();
    }

    public static int getSize(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        return native_get_size(fileDescriptor);
    }

    private class MemoryInputStream extends java.io.InputStream {
        private int mMark;
        private int mOffset;
        private byte[] mSingleByte;

        private MemoryInputStream() {
            this.mMark = 0;
            this.mOffset = 0;
        }

        @Override // java.io.InputStream
        public int available() throws java.io.IOException {
            if (this.mOffset >= android.os.MemoryFile.this.mSharedMemory.getSize()) {
                return 0;
            }
            return android.os.MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.mMark = this.mOffset;
        }

        @Override // java.io.InputStream
        public void reset() throws java.io.IOException {
            this.mOffset = this.mMark;
        }

        @Override // java.io.InputStream
        public int read() throws java.io.IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            if (read(this.mSingleByte, 0, 1) != 1) {
                return -1;
            }
            return this.mSingleByte[0];
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            int min = java.lang.Math.min(i2, available());
            if (min < 1) {
                return -1;
            }
            int readBytes = android.os.MemoryFile.this.readBytes(bArr, this.mOffset, i, min);
            if (readBytes > 0) {
                this.mOffset += readBytes;
            }
            return readBytes;
        }

        @Override // java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            if (this.mOffset + j > android.os.MemoryFile.this.mSharedMemory.getSize()) {
                j = android.os.MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
            }
            this.mOffset = (int) (this.mOffset + j);
            return j;
        }
    }

    private class MemoryOutputStream extends java.io.OutputStream {
        private int mOffset;
        private byte[] mSingleByte;

        private MemoryOutputStream() {
            this.mOffset = 0;
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            android.os.MemoryFile.this.writeBytes(bArr, i, this.mOffset, i2);
            this.mOffset += i2;
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            this.mSingleByte[0] = (byte) i;
            write(this.mSingleByte, 0, 1);
        }
    }
}
