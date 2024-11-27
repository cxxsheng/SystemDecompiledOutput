package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public abstract class RandomAccessObject {
    public abstract void close() throws java.io.IOException;

    public abstract int length();

    public abstract int read() throws java.io.IOException;

    public abstract int read(byte[] bArr, int i, int i2) throws java.io.IOException;

    public abstract void seek(int i) throws java.io.IOException;

    public static com.android.server.integrity.parser.RandomAccessObject ofFile(java.io.File file) throws java.io.IOException {
        return new com.android.server.integrity.parser.RandomAccessObject.RandomAccessFileObject(file);
    }

    public static com.android.server.integrity.parser.RandomAccessObject ofBytes(byte[] bArr) {
        return new com.android.server.integrity.parser.RandomAccessObject.RandomAccessByteArrayObject(bArr);
    }

    private static class RandomAccessFileObject extends com.android.server.integrity.parser.RandomAccessObject {
        private final int mLength;
        private final java.io.RandomAccessFile mRandomAccessFile;

        RandomAccessFileObject(java.io.File file) throws java.io.IOException {
            long length = file.length();
            if (length > 2147483647L) {
                throw new java.io.IOException("Unsupported file size (too big) " + length);
            }
            this.mRandomAccessFile = new java.io.RandomAccessFile(file, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            this.mLength = (int) length;
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public void seek(int i) throws java.io.IOException {
            this.mRandomAccessFile.seek(i);
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int read() throws java.io.IOException {
            return this.mRandomAccessFile.read();
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            return this.mRandomAccessFile.read(bArr, i, i2);
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public void close() throws java.io.IOException {
            this.mRandomAccessFile.close();
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int length() {
            return this.mLength;
        }
    }

    private static class RandomAccessByteArrayObject extends com.android.server.integrity.parser.RandomAccessObject {
        private final java.nio.ByteBuffer mBytes;

        RandomAccessByteArrayObject(byte[] bArr) {
            this.mBytes = java.nio.ByteBuffer.wrap(bArr);
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public void seek(int i) throws java.io.IOException {
            this.mBytes.position(i);
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int read() throws java.io.IOException {
            if (!this.mBytes.hasRemaining()) {
                return -1;
            }
            return this.mBytes.get() & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            int min = java.lang.Math.min(i2, this.mBytes.remaining());
            if (min <= 0) {
                return 0;
            }
            this.mBytes.get(bArr, i, i2);
            return min;
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public void close() throws java.io.IOException {
        }

        @Override // com.android.server.integrity.parser.RandomAccessObject
        public int length() {
            return this.mBytes.capacity();
        }
    }
}
