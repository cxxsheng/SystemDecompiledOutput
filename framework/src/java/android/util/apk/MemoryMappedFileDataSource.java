package android.util.apk;

/* loaded from: classes3.dex */
class MemoryMappedFileDataSource implements android.util.apk.DataSource {
    private static final long MEMORY_PAGE_SIZE_BYTES = android.system.Os.sysconf(android.system.OsConstants._SC_PAGESIZE);
    private final java.io.FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    MemoryMappedFileDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) {
        this.mFd = fileDescriptor;
        this.mFilePosition = j;
        this.mSize = j2;
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.util.apk.DataSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void feedIntoDataDigester(android.util.apk.DataDigester dataDigester, long j, int i) throws java.io.IOException, java.security.DigestException {
        long j2;
        java.lang.Throwable th;
        long j3;
        java.lang.Throwable th2;
        long j4 = this.mFilePosition + j;
        long j5 = (j4 / MEMORY_PAGE_SIZE_BYTES) * MEMORY_PAGE_SIZE_BYTES;
        int i2 = (int) (j4 - j5);
        long j6 = i + i2;
        try {
            j3 = android.system.Os.mmap(0L, j6, android.system.OsConstants.PROT_READ, android.system.OsConstants.MAP_SHARED | android.system.OsConstants.MAP_POPULATE, this.mFd, j5);
        } catch (android.system.ErrnoException e) {
            e = e;
            j2 = j6;
            j3 = 0;
        } catch (java.lang.Throwable th3) {
            j2 = j6;
            th = th3;
            j3 = 0;
            if (j3 == 0) {
            }
        }
        try {
            j2 = j6;
        } catch (android.system.ErrnoException e2) {
            e = e2;
            j2 = j6;
        } catch (java.lang.Throwable th4) {
            th2 = th4;
            j2 = j6;
            th = th2;
            if (j3 == 0) {
            }
        }
        try {
            try {
                dataDigester.consume(new java.nio.DirectByteBuffer(i, j3 + i2, this.mFd, (java.lang.Runnable) null, true));
                if (j3 != 0) {
                    try {
                        android.system.Os.munmap(j3, j2);
                    } catch (android.system.ErrnoException e3) {
                    }
                }
            } catch (java.lang.Throwable th5) {
                th2 = th5;
                th = th2;
                if (j3 == 0) {
                    throw th;
                }
                try {
                    android.system.Os.munmap(j3, j2);
                    throw th;
                } catch (android.system.ErrnoException e4) {
                    throw th;
                }
            }
        } catch (android.system.ErrnoException e5) {
            e = e5;
            throw new java.io.IOException("Failed to mmap " + j2 + " bytes", e);
        }
    }
}
