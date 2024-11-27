package android.app.backup;

/* loaded from: classes.dex */
public class BackupDataInput {
    long mBackupReader;
    private android.app.backup.BackupDataInput.EntityHeader mHeader = new android.app.backup.BackupDataInput.EntityHeader();
    private boolean mHeaderReady;

    private static native long ctor(java.io.FileDescriptor fileDescriptor);

    private static native void dtor(long j);

    private native int readEntityData_native(long j, byte[] bArr, int i, int i2);

    private native int readNextHeader_native(long j, android.app.backup.BackupDataInput.EntityHeader entityHeader);

    private native int skipEntityData_native(long j);

    private static class EntityHeader {
        int dataSize;
        java.lang.String key;

        private EntityHeader() {
        }
    }

    @android.annotation.SystemApi
    public BackupDataInput(java.io.FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new java.lang.NullPointerException();
        }
        this.mBackupReader = ctor(fileDescriptor);
        if (this.mBackupReader == 0) {
            throw new java.lang.RuntimeException("Native initialization failed with fd=" + fileDescriptor);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dtor(this.mBackupReader);
        } finally {
            super.finalize();
        }
    }

    public boolean readNextHeader() throws java.io.IOException {
        int readNextHeader_native = readNextHeader_native(this.mBackupReader, this.mHeader);
        if (readNextHeader_native == 0) {
            this.mHeaderReady = true;
            return true;
        }
        if (readNextHeader_native > 0) {
            this.mHeaderReady = false;
            return false;
        }
        this.mHeaderReady = false;
        throw new java.io.IOException("failed: 0x" + java.lang.Integer.toHexString(readNextHeader_native));
    }

    public java.lang.String getKey() {
        if (this.mHeaderReady) {
            return this.mHeader.key;
        }
        throw new java.lang.IllegalStateException("Entity header not read");
    }

    public int getDataSize() {
        if (this.mHeaderReady) {
            return this.mHeader.dataSize;
        }
        throw new java.lang.IllegalStateException("Entity header not read");
    }

    public int readEntityData(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mHeaderReady) {
            int readEntityData_native = readEntityData_native(this.mBackupReader, bArr, i, i2);
            if (readEntityData_native >= 0) {
                return readEntityData_native;
            }
            throw new java.io.IOException("result=0x" + java.lang.Integer.toHexString(readEntityData_native));
        }
        throw new java.lang.IllegalStateException("Entity header not read");
    }

    public void skipEntityData() throws java.io.IOException {
        if (this.mHeaderReady) {
            skipEntityData_native(this.mBackupReader);
            return;
        }
        throw new java.lang.IllegalStateException("Entity header not read");
    }
}
