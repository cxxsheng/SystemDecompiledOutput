package com.android.server.pm;

/* loaded from: classes2.dex */
final class ResilientAtomicFile implements java.io.Closeable {
    private static final java.lang.String LOG_TAG = "ResilientAtomicFile";
    private final java.lang.String mDebugName;
    private final java.io.File mFile;
    private final int mFileMode;
    private final com.android.server.pm.ResilientAtomicFile.ReadEventLogger mReadEventLogger;
    private final java.io.File mReserveCopy;
    private final java.io.File mTemporaryBackup;
    private java.io.FileOutputStream mMainOutStream = null;
    private java.io.FileInputStream mMainInStream = null;
    private java.io.FileOutputStream mReserveOutStream = null;
    private java.io.FileInputStream mReserveInStream = null;
    private java.io.File mCurrentFile = null;
    private java.io.FileInputStream mCurrentInStream = null;

    interface ReadEventLogger {
        void logEvent(int i, java.lang.String str);
    }

    private void finalizeOutStream(java.io.FileOutputStream fileOutputStream) throws java.io.IOException {
        fileOutputStream.flush();
        android.os.FileUtils.sync(fileOutputStream);
        android.os.FileUtils.setPermissions(fileOutputStream.getFD(), this.mFileMode, -1, -1);
    }

    ResilientAtomicFile(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.io.File file2, @android.annotation.NonNull java.io.File file3, int i, java.lang.String str, @android.annotation.Nullable com.android.server.pm.ResilientAtomicFile.ReadEventLogger readEventLogger) {
        this.mFile = file;
        this.mTemporaryBackup = file2;
        this.mReserveCopy = file3;
        this.mFileMode = i;
        this.mDebugName = str;
        this.mReadEventLogger = readEventLogger;
    }

    public java.io.File getBaseFile() {
        return this.mFile;
    }

    public java.io.FileOutputStream startWrite() throws java.io.IOException {
        if (this.mMainOutStream != null) {
            throw new java.lang.IllegalStateException("Duplicate startWrite call?");
        }
        new java.io.File(this.mFile.getParent()).mkdirs();
        if (this.mFile.exists()) {
            if (!this.mTemporaryBackup.exists()) {
                if (!this.mFile.renameTo(this.mTemporaryBackup)) {
                    throw new java.io.IOException("Unable to backup " + this.mDebugName + " file, current changes will be lost at reboot");
                }
            } else {
                this.mFile.delete();
                android.util.Slog.w(LOG_TAG, "Preserving older " + this.mDebugName + " backup");
            }
        }
        this.mReserveCopy.delete();
        try {
            this.mMainOutStream = new java.io.FileOutputStream(this.mFile);
            this.mMainInStream = new java.io.FileInputStream(this.mFile);
            this.mReserveOutStream = new java.io.FileOutputStream(this.mReserveCopy);
            this.mReserveInStream = new java.io.FileInputStream(this.mReserveCopy);
            return this.mMainOutStream;
        } catch (java.io.IOException e) {
            close();
            throw e;
        }
    }

    public void finishWrite(java.io.FileOutputStream fileOutputStream) throws java.io.IOException {
        if (this.mMainOutStream != fileOutputStream) {
            throw new java.lang.IllegalStateException("Invalid incoming stream.");
        }
        java.io.FileOutputStream fileOutputStream2 = this.mMainOutStream;
        try {
            this.mMainOutStream = null;
            finalizeOutStream(fileOutputStream2);
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            this.mTemporaryBackup.delete();
            try {
                java.io.FileInputStream fileInputStream = this.mMainInStream;
                try {
                    java.io.FileInputStream fileInputStream2 = this.mReserveInStream;
                    try {
                        this.mMainInStream = null;
                        this.mReserveInStream = null;
                        java.io.FileOutputStream fileOutputStream3 = this.mReserveOutStream;
                        try {
                            this.mReserveOutStream = null;
                            android.os.FileUtils.copy(fileInputStream, fileOutputStream3);
                            finalizeOutStream(fileOutputStream3);
                            if (fileOutputStream3 != null) {
                                fileOutputStream3.close();
                            }
                            try {
                                android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(fileInputStream.getFD());
                                try {
                                    android.os.ParcelFileDescriptor dup2 = android.os.ParcelFileDescriptor.dup(fileInputStream2.getFD());
                                    try {
                                        com.android.server.security.FileIntegrity.setUpFsVerity(dup);
                                        com.android.server.security.FileIntegrity.setUpFsVerity(dup2);
                                        if (dup2 != null) {
                                            dup2.close();
                                        }
                                        if (dup != null) {
                                            dup.close();
                                        }
                                    } finally {
                                    }
                                } catch (java.lang.Throwable th) {
                                    if (dup != null) {
                                        try {
                                            dup.close();
                                        } catch (java.lang.Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    }
                                    throw th;
                                }
                            } catch (java.io.IOException e) {
                                android.util.Slog.e(LOG_TAG, "Failed to verity-protect " + this.mDebugName, e);
                            }
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(LOG_TAG, "Failed to write reserve copy " + this.mDebugName + ": " + this.mReserveCopy, e2);
            }
        } catch (java.lang.Throwable th3) {
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (java.lang.Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public void failWrite(java.io.FileOutputStream fileOutputStream) {
        if (this.mMainOutStream != fileOutputStream) {
            throw new java.lang.IllegalStateException("Invalid incoming stream.");
        }
        close();
        if (this.mFile.exists() && !this.mFile.delete()) {
            android.util.Slog.i(LOG_TAG, "Failed to clean up mangled file: " + this.mFile);
        }
    }

    public java.io.FileInputStream openRead() throws java.io.IOException {
        if (this.mTemporaryBackup.exists()) {
            try {
                this.mCurrentFile = this.mTemporaryBackup;
                this.mCurrentInStream = new java.io.FileInputStream(this.mCurrentFile);
                if (this.mReadEventLogger != null) {
                    this.mReadEventLogger.logEvent(4, "Need to read from backup " + this.mDebugName + " file");
                }
                if (this.mFile.exists()) {
                    android.util.Slog.w(LOG_TAG, "Cleaning up " + this.mDebugName + " file " + this.mFile);
                    this.mFile.delete();
                }
                this.mReserveCopy.delete();
            } catch (java.io.IOException e) {
            }
        }
        if (this.mCurrentInStream != null) {
            return this.mCurrentInStream;
        }
        if (this.mFile.exists()) {
            this.mCurrentFile = this.mFile;
            this.mCurrentInStream = new java.io.FileInputStream(this.mCurrentFile);
        } else if (this.mReserveCopy.exists()) {
            this.mCurrentFile = this.mReserveCopy;
            this.mCurrentInStream = new java.io.FileInputStream(this.mCurrentFile);
            if (this.mReadEventLogger != null) {
                this.mReadEventLogger.logEvent(4, "Need to read from reserve copy " + this.mDebugName + " file");
            }
        }
        if (this.mCurrentInStream == null && this.mReadEventLogger != null) {
            this.mReadEventLogger.logEvent(4, "No " + this.mDebugName + " file");
        }
        return this.mCurrentInStream;
    }

    public void failRead(java.io.FileInputStream fileInputStream, java.lang.Exception exc) {
        if (this.mCurrentInStream != fileInputStream) {
            throw new java.lang.IllegalStateException("Invalid incoming stream.");
        }
        this.mCurrentInStream = null;
        libcore.io.IoUtils.closeQuietly(fileInputStream);
        if (this.mReadEventLogger != null) {
            this.mReadEventLogger.logEvent(6, "Error reading " + this.mDebugName + ", removing " + this.mCurrentFile + '\n' + android.util.Log.getStackTraceString(exc));
        }
        if (!this.mCurrentFile.delete()) {
            throw new java.lang.IllegalStateException("Failed to remove " + this.mCurrentFile);
        }
        this.mCurrentFile = null;
    }

    public void delete() {
        this.mFile.delete();
        this.mTemporaryBackup.delete();
        this.mReserveCopy.delete();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        libcore.io.IoUtils.closeQuietly(this.mMainOutStream);
        libcore.io.IoUtils.closeQuietly(this.mMainInStream);
        libcore.io.IoUtils.closeQuietly(this.mReserveOutStream);
        libcore.io.IoUtils.closeQuietly(this.mReserveInStream);
        libcore.io.IoUtils.closeQuietly(this.mCurrentInStream);
        this.mMainOutStream = null;
        this.mMainInStream = null;
        this.mReserveOutStream = null;
        this.mReserveInStream = null;
        this.mCurrentInStream = null;
        this.mCurrentFile = null;
    }

    public java.lang.String toString() {
        return this.mFile.getPath();
    }
}
