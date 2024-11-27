package android.app.backup;

/* loaded from: classes.dex */
public class BackupDataInputStream extends java.io.InputStream {
    int dataSize;
    java.lang.String key;
    android.app.backup.BackupDataInput mData;
    byte[] mOneByte;

    BackupDataInputStream(android.app.backup.BackupDataInput backupDataInput) {
        this.mData = backupDataInput;
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        byte[] bArr = this.mOneByte;
        if (this.mOneByte == null) {
            bArr = new byte[1];
            this.mOneByte = bArr;
        }
        this.mData.readEntityData(bArr, 0, 1);
        return bArr[0];
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        return this.mData.readEntityData(bArr, i, i2);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return this.mData.readEntityData(bArr, 0, bArr.length);
    }

    public java.lang.String getKey() {
        return this.key;
    }

    public int size() {
        return this.dataSize;
    }
}
