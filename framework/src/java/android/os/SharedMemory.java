package android.os;

/* loaded from: classes3.dex */
public final class SharedMemory implements android.os.Parcelable, java.io.Closeable {
    private sun.misc.Cleaner mCleaner;
    private final java.io.FileDescriptor mFileDescriptor;
    private final android.os.SharedMemory.MemoryRegistration mMemoryRegistration;
    private final int mSize;
    private static final int PROT_MASK = ((android.system.OsConstants.PROT_READ | android.system.OsConstants.PROT_WRITE) | android.system.OsConstants.PROT_EXEC) | android.system.OsConstants.PROT_NONE;
    public static final android.os.Parcelable.Creator<android.os.SharedMemory> CREATOR = new android.os.Parcelable.Creator<android.os.SharedMemory>() { // from class: android.os.SharedMemory.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.SharedMemory createFromParcel(android.os.Parcel parcel) {
            return new android.os.SharedMemory(parcel.readRawFileDescriptor());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.SharedMemory[] newArray(int i) {
            return new android.os.SharedMemory[i];
        }
    };

    private static native java.io.FileDescriptor nCreate(java.lang.String str, int i) throws android.system.ErrnoException;

    private static native int nGetSize(java.io.FileDescriptor fileDescriptor);

    private static native int nSetProt(java.io.FileDescriptor fileDescriptor, int i);

    private SharedMemory(java.io.FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new java.lang.IllegalArgumentException("Unable to create SharedMemory from a null FileDescriptor");
        }
        if (!fileDescriptor.valid()) {
            throw new java.lang.IllegalArgumentException("Unable to create SharedMemory from closed FileDescriptor");
        }
        this.mFileDescriptor = fileDescriptor;
        this.mSize = nGetSize(this.mFileDescriptor);
        if (this.mSize <= 0) {
            throw new java.lang.IllegalArgumentException("FileDescriptor is not a valid ashmem fd");
        }
        this.mMemoryRegistration = new android.os.SharedMemory.MemoryRegistration(this.mSize);
        this.mCleaner = sun.misc.Cleaner.create(this.mFileDescriptor, new android.os.SharedMemory.Closer(this.mFileDescriptor.getInt$(), this.mMemoryRegistration));
    }

    public static android.os.SharedMemory create(java.lang.String str, int i) throws android.system.ErrnoException {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Size must be greater than zero");
        }
        return new android.os.SharedMemory(nCreate(str, i));
    }

    private void checkOpen() {
        if (!this.mFileDescriptor.valid()) {
            throw new java.lang.IllegalStateException("SharedMemory is closed");
        }
    }

    public static android.os.SharedMemory fromFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        fileDescriptor.setInt$(parcelFileDescriptor.detachFd());
        return new android.os.SharedMemory(fileDescriptor);
    }

    private static void validateProt(int i) {
        if ((i & (~PROT_MASK)) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid prot value");
        }
    }

    public boolean setProtect(int i) {
        checkOpen();
        validateProt(i);
        return nSetProt(this.mFileDescriptor, i) == 0;
    }

    public java.io.FileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public int getFd() {
        return this.mFileDescriptor.getInt$();
    }

    public int getSize() {
        checkOpen();
        return this.mSize;
    }

    public java.nio.ByteBuffer mapReadWrite() throws android.system.ErrnoException {
        return map(android.system.OsConstants.PROT_READ | android.system.OsConstants.PROT_WRITE, 0, this.mSize);
    }

    public java.nio.ByteBuffer mapReadOnly() throws android.system.ErrnoException {
        return map(android.system.OsConstants.PROT_READ, 0, this.mSize);
    }

    public java.nio.ByteBuffer map(int i, int i2, int i3) throws android.system.ErrnoException {
        checkOpen();
        validateProt(i);
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Offset must be >= 0");
        }
        if (i3 > 0) {
            if (i2 + i3 <= this.mSize) {
                long mmap = android.system.Os.mmap(0L, i3, i, android.system.OsConstants.MAP_SHARED, this.mFileDescriptor, i2);
                return new java.nio.DirectByteBuffer(i3, mmap, this.mFileDescriptor, new android.os.SharedMemory.Unmapper(mmap, i3, this.mMemoryRegistration.acquire()), (i & android.system.OsConstants.PROT_WRITE) == 0);
            }
            throw new java.lang.IllegalArgumentException("offset + length must not exceed getSize()");
        }
        throw new java.lang.IllegalArgumentException("Length must be > 0");
    }

    public static void unmap(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer instanceof java.nio.DirectByteBuffer) {
            java.nio.NioUtils.freeDirectBuffer(byteBuffer);
            return;
        }
        throw new java.lang.IllegalArgumentException("ByteBuffer wasn't created by #map(int, int, int); can't unmap");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mFileDescriptor.setInt$(-1);
        if (this.mCleaner != null) {
            this.mCleaner.clean();
            this.mCleaner = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        checkOpen();
        parcel.writeFileDescriptor(this.mFileDescriptor);
    }

    public android.os.ParcelFileDescriptor getFdDup() throws java.io.IOException {
        return android.os.ParcelFileDescriptor.dup(this.mFileDescriptor);
    }

    private static final class Closer implements java.lang.Runnable {
        private int mFd;
        private android.os.SharedMemory.MemoryRegistration mMemoryReference;

        private Closer(int i, android.os.SharedMemory.MemoryRegistration memoryRegistration) {
            this.mFd = i;
            this.mMemoryReference = memoryRegistration;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
                fileDescriptor.setInt$(this.mFd);
                android.system.Os.close(fileDescriptor);
            } catch (android.system.ErrnoException e) {
            }
            this.mMemoryReference.release();
            this.mMemoryReference = null;
        }
    }

    private static final class Unmapper implements java.lang.Runnable {
        private long mAddress;
        private android.os.SharedMemory.MemoryRegistration mMemoryReference;
        private int mSize;

        private Unmapper(long j, int i, android.os.SharedMemory.MemoryRegistration memoryRegistration) {
            this.mAddress = j;
            this.mSize = i;
            this.mMemoryReference = memoryRegistration;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                android.system.Os.munmap(this.mAddress, this.mSize);
            } catch (android.system.ErrnoException e) {
            }
            this.mMemoryReference.release();
            this.mMemoryReference = null;
        }
    }

    private static final class MemoryRegistration {
        private int mReferenceCount;
        private int mSize;

        private MemoryRegistration(int i) {
            this.mSize = i;
            this.mReferenceCount = 1;
            dalvik.system.VMRuntime.getRuntime().registerNativeAllocation(this.mSize);
        }

        public synchronized android.os.SharedMemory.MemoryRegistration acquire() {
            this.mReferenceCount++;
            return this;
        }

        public synchronized void release() {
            this.mReferenceCount--;
            if (this.mReferenceCount == 0) {
                dalvik.system.VMRuntime.getRuntime().registerNativeFree(this.mSize);
            }
        }
    }
}
