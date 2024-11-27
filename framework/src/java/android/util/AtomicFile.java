package android.util;

/* loaded from: classes3.dex */
public class AtomicFile {
    private static final java.lang.String LOG_TAG = "AtomicFile";
    private final java.io.File mBaseName;
    private android.util.SystemConfigFileCommitEventLogger mCommitEventLogger;
    private final java.io.File mLegacyBackupName;
    private final java.io.File mNewName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AtomicFile(java.io.File file) {
        this(file, (android.util.SystemConfigFileCommitEventLogger) null);
    }

    public AtomicFile(java.io.File file, java.lang.String str) {
        this(file, new android.util.SystemConfigFileCommitEventLogger(str));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public AtomicFile(java.io.File file, android.util.SystemConfigFileCommitEventLogger systemConfigFileCommitEventLogger) {
        this.mBaseName = file;
        this.mNewName = new java.io.File(file.getPath() + ".new");
        this.mLegacyBackupName = new java.io.File(file.getPath() + ".bak");
        this.mCommitEventLogger = systemConfigFileCommitEventLogger;
    }

    public java.io.File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mNewName.delete();
        this.mLegacyBackupName.delete();
    }

    public java.io.FileOutputStream startWrite() throws java.io.IOException {
        return startWrite(0L);
    }

    @java.lang.Deprecated
    public java.io.FileOutputStream startWrite(long j) throws java.io.IOException {
        if (this.mCommitEventLogger != null) {
            this.mCommitEventLogger.setStartTime(j);
            this.mCommitEventLogger.onStartWrite();
        }
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        try {
            return new java.io.FileOutputStream(this.mNewName);
        } catch (java.io.FileNotFoundException e) {
            java.io.File parentFile = this.mNewName.getParentFile();
            if (!parentFile.mkdirs()) {
                throw new java.io.IOException("Failed to create directory for " + this.mNewName);
            }
            android.os.FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            try {
                return new java.io.FileOutputStream(this.mNewName);
            } catch (java.io.FileNotFoundException e2) {
                throw new java.io.IOException("Failed to create new file " + this.mNewName, e2);
            }
        }
    }

    public void finishWrite(java.io.FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return;
        }
        if (!android.os.FileUtils.sync(fileOutputStream)) {
            android.util.Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        rename(this.mNewName, this.mBaseName);
        if (this.mCommitEventLogger != null) {
            this.mCommitEventLogger.onFinishWrite();
        }
    }

    public void failWrite(java.io.FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return;
        }
        if (!android.os.FileUtils.sync(fileOutputStream)) {
            android.util.Log.e(LOG_TAG, "Failed to sync file output stream");
        }
        try {
            fileOutputStream.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(LOG_TAG, "Failed to close file output stream", e);
        }
        if (!this.mNewName.delete()) {
            android.util.Log.e(LOG_TAG, "Failed to delete new file " + this.mNewName);
        }
    }

    @java.lang.Deprecated
    public void truncate() throws java.io.IOException {
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(this.mBaseName);
            android.os.FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
        } catch (java.io.FileNotFoundException e) {
            throw new java.io.IOException("Couldn't append " + this.mBaseName);
        } catch (java.io.IOException e2) {
        }
    }

    @java.lang.Deprecated
    public java.io.FileOutputStream openAppend() throws java.io.IOException {
        try {
            return new java.io.FileOutputStream(this.mBaseName, true);
        } catch (java.io.FileNotFoundException e) {
            throw new java.io.IOException("Couldn't append " + this.mBaseName);
        }
    }

    public java.io.FileInputStream openRead() throws java.io.FileNotFoundException {
        if (this.mLegacyBackupName.exists()) {
            rename(this.mLegacyBackupName, this.mBaseName);
        }
        if (this.mNewName.exists() && this.mBaseName.exists() && !this.mNewName.delete()) {
            android.util.Log.e(LOG_TAG, "Failed to delete outdated new file " + this.mNewName);
        }
        return new java.io.FileInputStream(this.mBaseName);
    }

    public boolean exists() {
        return this.mBaseName.exists() || this.mLegacyBackupName.exists();
    }

    public long getLastModifiedTime() {
        if (this.mLegacyBackupName.exists()) {
            return this.mLegacyBackupName.lastModified();
        }
        return this.mBaseName.lastModified();
    }

    public byte[] readFully() throws java.io.IOException {
        java.io.FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    return bArr;
                }
                i += read;
                int available = openRead.available();
                if (available > bArr.length - i) {
                    byte[] bArr2 = new byte[available + i];
                    java.lang.System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            }
        } finally {
            openRead.close();
        }
    }

    public void write(java.util.function.Consumer<java.io.FileOutputStream> consumer) {
        java.io.FileOutputStream fileOutputStream;
        try {
            fileOutputStream = startWrite();
        } catch (java.lang.Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        try {
            consumer.accept(fileOutputStream);
            finishWrite(fileOutputStream);
        } catch (java.lang.Throwable th2) {
            th = th2;
            try {
                failWrite(fileOutputStream);
                throw android.util.ExceptionUtils.propagate(th);
            } finally {
                libcore.io.IoUtils.closeQuietly(fileOutputStream);
            }
        }
    }

    public java.lang.String toString() {
        return "AtomicFile[" + this.mBaseName + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static void rename(java.io.File file, java.io.File file2) {
        if (file2.isDirectory() && !file2.delete()) {
            android.util.Log.e(LOG_TAG, "Failed to delete file which is a directory " + file2);
        }
        if (!file.renameTo(file2)) {
            android.util.Log.e(LOG_TAG, "Failed to rename " + file + " to " + file2);
        }
    }
}
