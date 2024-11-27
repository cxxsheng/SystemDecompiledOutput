package android.app.backup;

/* loaded from: classes.dex */
public class BackupDataOutput {
    long mBackupWriter;
    private final long mQuota;
    private final int mTransportFlags;

    private static native long ctor(java.io.FileDescriptor fileDescriptor);

    private static native void dtor(long j);

    private static native void setKeyPrefix_native(long j, java.lang.String str);

    private static native int writeEntityData_native(long j, byte[] bArr, int i);

    private static native int writeEntityHeader_native(long j, java.lang.String str, int i);

    @android.annotation.SystemApi
    public BackupDataOutput(java.io.FileDescriptor fileDescriptor) {
        this(fileDescriptor, -1L, 0);
    }

    @android.annotation.SystemApi
    public BackupDataOutput(java.io.FileDescriptor fileDescriptor, long j) {
        this(fileDescriptor, j, 0);
    }

    public BackupDataOutput(java.io.FileDescriptor fileDescriptor, long j, int i) {
        if (fileDescriptor == null) {
            throw new java.lang.NullPointerException();
        }
        this.mQuota = j;
        this.mTransportFlags = i;
        this.mBackupWriter = ctor(fileDescriptor);
        if (this.mBackupWriter == 0) {
            throw new java.lang.RuntimeException("Native initialization failed with fd=" + fileDescriptor);
        }
    }

    public long getQuota() {
        return this.mQuota;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }

    public int writeEntityHeader(java.lang.String str, int i) throws java.io.IOException {
        int writeEntityHeader_native = writeEntityHeader_native(this.mBackupWriter, str, i);
        if (writeEntityHeader_native >= 0) {
            return writeEntityHeader_native;
        }
        throw new java.io.IOException("result=0x" + java.lang.Integer.toHexString(writeEntityHeader_native));
    }

    public int writeEntityData(byte[] bArr, int i) throws java.io.IOException {
        int writeEntityData_native = writeEntityData_native(this.mBackupWriter, bArr, i);
        if (writeEntityData_native >= 0) {
            return writeEntityData_native;
        }
        throw new java.io.IOException("result=0x" + java.lang.Integer.toHexString(writeEntityData_native));
    }

    public void setKeyPrefix(java.lang.String str) {
        setKeyPrefix_native(this.mBackupWriter, str);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dtor(this.mBackupWriter);
        } finally {
            super.finalize();
        }
    }
}
