package android.util.apk;

/* loaded from: classes3.dex */
class ReadFileDataSource implements android.util.apk.DataSource {
    private static final int CHUNK_SIZE = 1048576;
    private final java.io.FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    ReadFileDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) {
        this.mFd = fileDescriptor;
        this.mFilePosition = j;
        this.mSize = j2;
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    @Override // android.util.apk.DataSource
    public void feedIntoDataDigester(android.util.apk.DataDigester dataDigester, long j, int i) throws java.io.IOException, java.security.DigestException {
        try {
            byte[] bArr = new byte[java.lang.Math.min(i, 1048576)];
            long j2 = this.mFilePosition + j;
            long j3 = i + j2;
            long min = java.lang.Math.min(i, 1048576);
            long j4 = j2;
            while (j4 < j3) {
                int pread = android.system.Os.pread(this.mFd, bArr, 0, (int) min, j4);
                dataDigester.consume(java.nio.ByteBuffer.wrap(bArr, 0, pread));
                j4 += pread;
                min = java.lang.Math.min(j3 - j4, 1048576L);
            }
        } catch (android.system.ErrnoException e) {
            throw new java.io.IOException(e);
        }
    }
}
