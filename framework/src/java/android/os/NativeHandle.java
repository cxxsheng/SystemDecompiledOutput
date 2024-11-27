package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NativeHandle implements java.io.Closeable {
    private java.io.FileDescriptor[] mFds;
    private int[] mInts;
    private boolean mOwn;

    public NativeHandle() {
        this(new java.io.FileDescriptor[0], new int[0], false);
    }

    public NativeHandle(java.io.FileDescriptor fileDescriptor, boolean z) {
        this(new java.io.FileDescriptor[]{fileDescriptor}, new int[0], z);
    }

    private static java.io.FileDescriptor[] createFileDescriptorArray(int[] iArr) {
        java.io.FileDescriptor[] fileDescriptorArr = new java.io.FileDescriptor[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            fileDescriptor.setInt$(iArr[i]);
            fileDescriptorArr[i] = fileDescriptor;
        }
        return fileDescriptorArr;
    }

    private NativeHandle(int[] iArr, int[] iArr2, boolean z) {
        this(createFileDescriptorArray(iArr), iArr2, z);
    }

    public NativeHandle(java.io.FileDescriptor[] fileDescriptorArr, int[] iArr, boolean z) {
        this.mOwn = false;
        this.mFds = (java.io.FileDescriptor[]) fileDescriptorArr.clone();
        this.mInts = (int[]) iArr.clone();
        this.mOwn = z;
    }

    public boolean hasSingleFileDescriptor() {
        checkOpen();
        return this.mFds.length == 1 && this.mInts.length == 0;
    }

    public android.os.NativeHandle dup() throws java.io.IOException {
        java.io.FileDescriptor[] fileDescriptorArr = new java.io.FileDescriptor[this.mFds.length];
        for (int i = 0; i < this.mFds.length; i++) {
            try {
                java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
                fileDescriptor.setInt$(android.system.Os.fcntlInt(this.mFds[i], android.system.OsConstants.F_DUPFD_CLOEXEC, 0));
                fileDescriptorArr[i] = fileDescriptor;
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
        }
        return new android.os.NativeHandle(fileDescriptorArr, this.mInts, true);
    }

    private void checkOpen() {
        if (this.mFds == null) {
            throw new java.lang.IllegalStateException("NativeHandle is invalidated after close.");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        checkOpen();
        if (this.mOwn) {
            try {
                for (java.io.FileDescriptor fileDescriptor : this.mFds) {
                    android.system.Os.close(fileDescriptor);
                }
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.mOwn = false;
        }
        this.mFds = null;
        this.mInts = null;
    }

    public java.io.FileDescriptor getFileDescriptor() {
        checkOpen();
        if (!hasSingleFileDescriptor()) {
            throw new java.lang.IllegalStateException("NativeHandle is not single file descriptor. Contents must be retreived through getFileDescriptors and getInts.");
        }
        return this.mFds[0];
    }

    private int[] getFdsAsIntArray() {
        checkOpen();
        int length = this.mFds.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = this.mFds[i].getInt$();
        }
        return iArr;
    }

    public java.io.FileDescriptor[] getFileDescriptors() {
        checkOpen();
        return this.mFds;
    }

    public int[] getInts() {
        checkOpen();
        return this.mInts;
    }
}
