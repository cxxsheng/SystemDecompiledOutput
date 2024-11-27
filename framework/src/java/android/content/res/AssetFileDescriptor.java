package android.content.res;

/* loaded from: classes.dex */
public class AssetFileDescriptor implements android.os.Parcelable, java.io.Closeable {
    public static final android.os.Parcelable.Creator<android.content.res.AssetFileDescriptor> CREATOR = new android.os.Parcelable.Creator<android.content.res.AssetFileDescriptor>() { // from class: android.content.res.AssetFileDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.AssetFileDescriptor createFromParcel(android.os.Parcel parcel) {
            return new android.content.res.AssetFileDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.AssetFileDescriptor[] newArray(int i) {
            return new android.content.res.AssetFileDescriptor[i];
        }
    };
    public static final long UNKNOWN_LENGTH = -1;
    private final android.os.Bundle mExtras;
    private final android.os.ParcelFileDescriptor mFd;
    private final long mLength;
    private final long mStartOffset;

    public AssetFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        this(parcelFileDescriptor, j, j2, null);
    }

    public AssetFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, android.os.Bundle bundle) {
        if (parcelFileDescriptor == null) {
            throw new java.lang.IllegalArgumentException("fd must not be null");
        }
        if (j2 < 0 && j != 0) {
            throw new java.lang.IllegalArgumentException("startOffset must be 0 when using UNKNOWN_LENGTH");
        }
        this.mFd = parcelFileDescriptor;
        this.mStartOffset = j;
        this.mLength = j2;
        this.mExtras = bundle;
    }

    public android.os.ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mFd;
    }

    public java.io.FileDescriptor getFileDescriptor() {
        return this.mFd.getFileDescriptor();
    }

    public long getStartOffset() {
        return this.mStartOffset;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public long getLength() {
        if (this.mLength >= 0) {
            return this.mLength;
        }
        long statSize = this.mFd.getStatSize();
        if (statSize >= 0) {
            return statSize;
        }
        return -1L;
    }

    public long getDeclaredLength() {
        return this.mLength;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mFd.close();
    }

    public java.io.FileInputStream createInputStream() throws java.io.IOException {
        if (this.mLength < 0) {
            return new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mFd);
        }
        return new android.content.res.AssetFileDescriptor.AutoCloseInputStream(this);
    }

    public java.io.FileOutputStream createOutputStream() throws java.io.IOException {
        if (this.mLength < 0) {
            return new android.os.ParcelFileDescriptor.AutoCloseOutputStream(this.mFd);
        }
        return new android.content.res.AssetFileDescriptor.AutoCloseOutputStream(this);
    }

    public java.lang.String toString() {
        return "{AssetFileDescriptor: " + this.mFd + " start=" + this.mStartOffset + " len=" + this.mLength + "}";
    }

    public static class AutoCloseInputStream extends android.os.ParcelFileDescriptor.AutoCloseInputStream {
        private android.os.ParcelFileDescriptor.AutoCloseInputStream mDelegateInputStream;

        public AutoCloseInputStream(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            try {
                android.system.StructStat fstat = android.system.Os.fstat(assetFileDescriptor.getParcelFileDescriptor().getFileDescriptor());
                if (android.system.OsConstants.S_ISSOCK(fstat.st_mode) || android.system.OsConstants.S_ISFIFO(fstat.st_mode)) {
                    this.mDelegateInputStream = new android.content.res.AssetFileDescriptor.NonSeekableAutoCloseInputStream(assetFileDescriptor);
                } else {
                    this.mDelegateInputStream = new android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream(assetFileDescriptor);
                }
            } catch (android.system.ErrnoException e) {
                throw new java.io.IOException(e);
            }
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws java.io.IOException {
            return this.mDelegateInputStream.available();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws java.io.IOException {
            return this.mDelegateInputStream.read();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            return this.mDelegateInputStream.read(bArr, i, i2);
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws java.io.IOException {
            return this.mDelegateInputStream.read(bArr);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            return this.mDelegateInputStream.skip(j);
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.mDelegateInputStream.mark(i);
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return this.mDelegateInputStream.markSupported();
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws java.io.IOException {
            this.mDelegateInputStream.reset();
        }

        @Override // java.io.FileInputStream
        public java.nio.channels.FileChannel getChannel() {
            return this.mDelegateInputStream.getChannel();
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            this.mDelegateInputStream.close();
        }
    }

    private static class NonSeekableAutoCloseInputStream extends android.os.ParcelFileDescriptor.AutoCloseInputStream {
        private long mRemaining;

        NonSeekableAutoCloseInputStream(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            super.skip(assetFileDescriptor.getStartOffset());
            this.mRemaining = (int) assetFileDescriptor.getLength();
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws java.io.IOException {
            if (this.mRemaining < 0) {
                return super.available();
            }
            if (this.mRemaining < 2147483647L) {
                return (int) this.mRemaining;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws java.io.IOException {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) != -1) {
                return bArr[0] & 255;
            }
            return -1;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            if (this.mRemaining < 0) {
                return super.read(bArr, i, i2);
            }
            if (this.mRemaining == 0) {
                return -1;
            }
            if (i2 > this.mRemaining) {
                i2 = (int) this.mRemaining;
            }
            int read = super.read(bArr, i, i2);
            if (read >= 0) {
                this.mRemaining -= read;
            }
            return read;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws java.io.IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            if (this.mRemaining < 0) {
                return super.skip(j);
            }
            if (this.mRemaining == 0) {
                return -1L;
            }
            if (j > this.mRemaining) {
                j = this.mRemaining;
            }
            long skip = super.skip(j);
            if (skip >= 0) {
                this.mRemaining -= skip;
            }
            return skip;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            if (this.mRemaining >= 0) {
                return;
            }
            super.mark(i);
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            if (this.mRemaining >= 0) {
                return false;
            }
            return super.markSupported();
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws java.io.IOException {
            if (this.mRemaining >= 0) {
                return;
            }
            super.reset();
        }
    }

    private static class SeekableAutoCloseInputStream extends android.os.ParcelFileDescriptor.AutoCloseInputStream {
        private final long mFileOffset;
        private long mOffset;
        private android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.OffsetCorrectFileChannel mOffsetCorrectFileChannel;
        private long mTotalSize;

        SeekableAutoCloseInputStream(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            this.mTotalSize = assetFileDescriptor.getLength();
            this.mFileOffset = assetFileDescriptor.getStartOffset();
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public int available() throws java.io.IOException {
            long j = this.mTotalSize - this.mOffset;
            if (j < 0) {
                return 0;
            }
            if (j < 2147483647L) {
                return (int) j;
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read() throws java.io.IOException {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) != -1) {
                return bArr[0] & 255;
            }
            return -1;
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
            int available = available();
            int i3 = -1;
            if (available <= 0) {
                return -1;
            }
            try {
                int pread = android.system.Os.pread(getFD(), bArr, i, i2 > available ? available : i2, this.mOffset + this.mFileOffset);
                if (pread != 0) {
                    i3 = pread;
                }
                if (i3 > 0) {
                    this.mOffset += i3;
                    updateChannelPosition(this.mOffset + this.mFileOffset);
                }
                return i3;
            } catch (android.system.ErrnoException e) {
                throw new java.io.IOException(e);
            }
        }

        @Override // android.os.ParcelFileDescriptor.AutoCloseInputStream, java.io.FileInputStream, java.io.InputStream
        public int read(byte[] bArr) throws java.io.IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.FileInputStream, java.io.InputStream
        public long skip(long j) throws java.io.IOException {
            int available = available();
            if (available <= 0) {
                return -1L;
            }
            long j2 = available;
            if (j > j2) {
                j = j2;
            }
            this.mOffset += j;
            updateChannelPosition(this.mOffset + this.mFileOffset);
            return j;
        }

        @Override // java.io.InputStream
        public void mark(int i) {
        }

        @Override // java.io.InputStream
        public boolean markSupported() {
            return false;
        }

        @Override // java.io.InputStream
        public synchronized void reset() throws java.io.IOException {
        }

        @Override // java.io.FileInputStream
        public java.nio.channels.FileChannel getChannel() {
            if (this.mOffsetCorrectFileChannel == null) {
                this.mOffsetCorrectFileChannel = new android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.OffsetCorrectFileChannel(super.getChannel());
            }
            try {
                updateChannelPosition(this.mOffset + this.mFileOffset);
                return this.mOffsetCorrectFileChannel;
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        private void updateChannelPosition(long j) throws java.io.IOException {
            if (this.mOffsetCorrectFileChannel != null) {
                this.mOffsetCorrectFileChannel.position(j);
            }
        }

        private class OffsetCorrectFileChannel extends java.nio.channels.FileChannel {
            private static final java.lang.String METHOD_NOT_SUPPORTED_MESSAGE = "This Method is not supported in AutoCloseInputStream FileChannel.";
            private final java.nio.channels.FileChannel mDelegate;

            OffsetCorrectFileChannel(java.nio.channels.FileChannel fileChannel) {
                this.mDelegate = fileChannel;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.ReadableByteChannel
            public int read(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
                if (android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1;
                }
                int read = this.mDelegate.read(byteBuffer);
                if (read != -1) {
                    android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mOffset += read;
                }
                return read;
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.ScatteringByteChannel
            public long read(java.nio.ByteBuffer[] byteBufferArr, int i, int i2) throws java.io.IOException {
                if (android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.available() <= 0) {
                    return -1L;
                }
                if (android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mOffset + i2 > android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    i2 = (int) (android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mOffset);
                }
                long read = this.mDelegate.read(byteBufferArr, i, i2);
                if (read != -1) {
                    android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mOffset += read;
                }
                return read;
            }

            @Override // java.nio.channels.FileChannel
            public int read(java.nio.ByteBuffer byteBuffer, long j) throws java.io.IOException {
                if (j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset > android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    return -1;
                }
                return this.mDelegate.read(byteBuffer, j);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long position() throws java.io.IOException {
                return this.mDelegate.position();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public java.nio.channels.FileChannel position(long j) throws java.io.IOException {
                android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mOffset = j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset;
                return this.mDelegate.position(j);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public long size() throws java.io.IOException {
                return android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize;
            }

            @Override // java.nio.channels.FileChannel
            public long transferTo(long j, long j2, java.nio.channels.WritableByteChannel writableByteChannel) throws java.io.IOException {
                long j3;
                if (j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset > android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    return 0L;
                }
                if ((j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset) + j2 <= android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    j3 = j2;
                } else {
                    j3 = android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize - (j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.transferTo(j, j3, writableByteChannel);
            }

            @Override // java.nio.channels.FileChannel
            public java.nio.MappedByteBuffer map(java.nio.channels.FileChannel.MapMode mapMode, long j, long j2) throws java.io.IOException {
                long j3;
                if (j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset > android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    throw new java.io.IOException("Cannot map to buffer because position exceed current file size.");
                }
                if ((j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset) + j2 <= android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize) {
                    j3 = j2;
                } else {
                    j3 = android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mTotalSize - (j - android.content.res.AssetFileDescriptor.SeekableAutoCloseInputStream.this.mFileOffset);
                }
                return this.mDelegate.map(mapMode, j, j3);
            }

            @Override // java.nio.channels.spi.AbstractInterruptibleChannel
            protected void implCloseChannel() throws java.io.IOException {
                this.mDelegate.close();
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel, java.nio.channels.WritableByteChannel
            public int write(java.nio.ByteBuffer byteBuffer) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.GatheringByteChannel
            public long write(java.nio.ByteBuffer[] byteBufferArr, int i, int i2) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public int write(java.nio.ByteBuffer byteBuffer, long j) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public long transferFrom(java.nio.channels.ReadableByteChannel readableByteChannel, long j, long j2) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel, java.nio.channels.SeekableByteChannel
            public java.nio.channels.FileChannel truncate(long j) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public void force(boolean z) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public java.nio.channels.FileLock lock(long j, long j2, boolean z) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }

            @Override // java.nio.channels.FileChannel
            public java.nio.channels.FileLock tryLock(long j, long j2, boolean z) throws java.io.IOException {
                throw new java.lang.UnsupportedOperationException(METHOD_NOT_SUPPORTED_MESSAGE);
            }
        }
    }

    public static class AutoCloseOutputStream extends android.os.ParcelFileDescriptor.AutoCloseOutputStream {
        private long mRemaining;

        public AutoCloseOutputStream(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException {
            super(assetFileDescriptor.getParcelFileDescriptor());
            if (assetFileDescriptor.getParcelFileDescriptor().seekTo(assetFileDescriptor.getStartOffset()) < 0) {
                throw new java.io.IOException("Unable to seek");
            }
            this.mRemaining = (int) assetFileDescriptor.getLength();
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            if (this.mRemaining < 0) {
                super.write(bArr, i, i2);
            } else {
                if (this.mRemaining == 0) {
                    return;
                }
                if (i2 > this.mRemaining) {
                    i2 = (int) this.mRemaining;
                }
                super.write(bArr, i, i2);
                this.mRemaining -= i2;
            }
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            if (this.mRemaining < 0) {
                super.write(bArr);
                return;
            }
            if (this.mRemaining == 0) {
                return;
            }
            int length = bArr.length;
            if (length > this.mRemaining) {
                length = (int) this.mRemaining;
            }
            super.write(bArr);
            this.mRemaining -= length;
        }

        @Override // java.io.FileOutputStream, java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            if (this.mRemaining < 0) {
                super.write(i);
            } else {
                if (this.mRemaining == 0) {
                    return;
                }
                super.write(i);
                this.mRemaining--;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mFd.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mFd.writeToParcel(parcel, i);
        parcel.writeLong(this.mStartOffset);
        parcel.writeLong(this.mLength);
        if (this.mExtras != null) {
            parcel.writeInt(1);
            parcel.writeBundle(this.mExtras);
        } else {
            parcel.writeInt(0);
        }
    }

    AssetFileDescriptor(android.os.Parcel parcel) {
        this.mFd = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
        this.mStartOffset = parcel.readLong();
        this.mLength = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mExtras = parcel.readBundle();
        } else {
            this.mExtras = null;
        }
    }
}
