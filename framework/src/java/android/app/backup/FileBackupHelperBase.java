package android.app.backup;

/* loaded from: classes.dex */
class FileBackupHelperBase {
    private static final java.lang.String TAG = "FileBackupHelperBase";
    android.content.Context mContext;
    boolean mExceptionLogged;
    long mPtr = ctor();

    private static native long ctor();

    private static native void dtor(long j);

    private static native int performBackup_native(java.io.FileDescriptor fileDescriptor, long j, java.io.FileDescriptor fileDescriptor2, java.lang.String[] strArr, java.lang.String[] strArr2);

    private static native int writeFile_native(long j, java.lang.String str, long j2);

    private static native int writeSnapshot_native(long j, java.io.FileDescriptor fileDescriptor);

    FileBackupHelperBase(android.content.Context context) {
        this.mContext = context;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dtor(this.mPtr);
        } finally {
            super.finalize();
        }
    }

    static void performBackup_checked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2, java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (strArr.length == 0) {
            return;
        }
        for (java.lang.String str : strArr) {
            if (str.charAt(0) != '/') {
                throw new java.lang.RuntimeException("files must have all absolute paths: " + str);
            }
        }
        if (strArr.length != strArr2.length) {
            throw new java.lang.RuntimeException("files.length=" + strArr.length + " keys.length=" + strArr2.length);
        }
        java.io.FileDescriptor fileDescriptor = parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null;
        java.io.FileDescriptor fileDescriptor2 = parcelFileDescriptor2.getFileDescriptor();
        if (fileDescriptor2 == null) {
            throw new java.lang.NullPointerException();
        }
        int performBackup_native = performBackup_native(fileDescriptor, backupDataOutput.mBackupWriter, fileDescriptor2, strArr, strArr2);
        if (performBackup_native != 0) {
            throw new java.lang.RuntimeException("Backup failed 0x" + java.lang.Integer.toHexString(performBackup_native));
        }
    }

    boolean writeFile(java.io.File file, android.app.backup.BackupDataInputStream backupDataInputStream) {
        file.getParentFile().mkdirs();
        int writeFile_native = writeFile_native(this.mPtr, file.getAbsolutePath(), backupDataInputStream.mData.mBackupReader);
        if (writeFile_native != 0 && !this.mExceptionLogged) {
            android.util.Log.e(TAG, "Failed restoring file '" + file + "' for app '" + this.mContext.getPackageName() + "' result=0x" + java.lang.Integer.toHexString(writeFile_native));
            this.mExceptionLogged = true;
        }
        return writeFile_native == 0;
    }

    public void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        writeSnapshot_native(this.mPtr, parcelFileDescriptor.getFileDescriptor());
    }

    boolean isKeyInList(java.lang.String str, java.lang.String[] strArr) {
        for (java.lang.String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
