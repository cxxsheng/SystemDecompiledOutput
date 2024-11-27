package com.android.internal.os;

/* loaded from: classes4.dex */
public final class AtomicDirectory {
    private static final java.lang.String LOG_TAG = com.android.internal.os.AtomicDirectory.class.getSimpleName();
    private final java.io.File mBackupDirectory;
    private final java.io.File mBaseDirectory;
    private final android.util.ArrayMap<java.io.File, java.io.FileOutputStream> mOpenFiles = new android.util.ArrayMap<>();

    public AtomicDirectory(java.io.File file) {
        com.android.internal.util.Preconditions.checkNotNull(file, "baseDirectory cannot be null");
        this.mBaseDirectory = file;
        this.mBackupDirectory = new java.io.File(file.getPath() + "_bak");
    }

    public java.io.File getBackupDirectory() {
        return this.mBackupDirectory;
    }

    public java.io.File startRead() throws java.io.IOException {
        restore();
        ensureBaseDirectory();
        return this.mBaseDirectory;
    }

    public void finishRead() {
    }

    public java.io.File startWrite() throws java.io.IOException {
        backup();
        ensureBaseDirectory();
        return this.mBaseDirectory;
    }

    public java.io.FileOutputStream openWrite(java.io.File file) throws java.io.IOException {
        if (file.isDirectory() || !file.getParentFile().equals(this.mBaseDirectory)) {
            throw new java.lang.IllegalArgumentException("Must be a file in " + this.mBaseDirectory);
        }
        if (this.mOpenFiles.containsKey(file)) {
            throw new java.lang.IllegalArgumentException("Already open file " + file.getAbsolutePath());
        }
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        this.mOpenFiles.put(file, fileOutputStream);
        return fileOutputStream;
    }

    public void closeWrite(java.io.FileOutputStream fileOutputStream) {
        int indexOfValue = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (indexOfValue < 0) {
            throw new java.lang.IllegalArgumentException("Unknown file stream " + fileOutputStream);
        }
        this.mOpenFiles.removeAt(indexOfValue);
        android.os.FileUtils.sync(fileOutputStream);
        android.os.FileUtils.closeQuietly(fileOutputStream);
    }

    public void failWrite(java.io.FileOutputStream fileOutputStream) {
        int indexOfValue = this.mOpenFiles.indexOfValue(fileOutputStream);
        if (indexOfValue < 0) {
            throw new java.lang.IllegalArgumentException("Unknown file stream " + fileOutputStream);
        }
        this.mOpenFiles.removeAt(indexOfValue);
        android.os.FileUtils.closeQuietly(fileOutputStream);
    }

    public void finishWrite() {
        throwIfSomeFilesOpen();
        syncDirectory(this.mBaseDirectory);
        syncParentDirectory();
        deleteDirectory(this.mBackupDirectory);
        syncParentDirectory();
    }

    public void failWrite() {
        throwIfSomeFilesOpen();
        try {
            restore();
        } catch (java.io.IOException e) {
            android.util.Log.e(LOG_TAG, "Failed to restore in failWrite()", e);
        }
    }

    public boolean exists() {
        return this.mBaseDirectory.exists() || this.mBackupDirectory.exists();
    }

    public void delete() {
        boolean deleteDirectory = this.mBaseDirectory.exists() ? false | deleteDirectory(this.mBaseDirectory) : false;
        if (this.mBackupDirectory.exists()) {
            deleteDirectory |= deleteDirectory(this.mBackupDirectory);
        }
        if (deleteDirectory) {
            syncParentDirectory();
        }
    }

    private void ensureBaseDirectory() throws java.io.IOException {
        if (this.mBaseDirectory.exists()) {
            return;
        }
        if (!this.mBaseDirectory.mkdirs()) {
            throw new java.io.IOException("Failed to create directory " + this.mBaseDirectory);
        }
        android.os.FileUtils.setPermissions(this.mBaseDirectory.getPath(), 505, -1, -1);
    }

    private void throwIfSomeFilesOpen() {
        if (!this.mOpenFiles.isEmpty()) {
            throw new java.lang.IllegalStateException("Unclosed files: " + java.util.Arrays.toString(this.mOpenFiles.keySet().toArray()));
        }
    }

    private void backup() throws java.io.IOException {
        if (!this.mBaseDirectory.exists()) {
            return;
        }
        if (this.mBackupDirectory.exists()) {
            deleteDirectory(this.mBackupDirectory);
        }
        if (!this.mBaseDirectory.renameTo(this.mBackupDirectory)) {
            throw new java.io.IOException("Failed to backup " + this.mBaseDirectory + " to " + this.mBackupDirectory);
        }
        syncParentDirectory();
    }

    private void restore() throws java.io.IOException {
        if (!this.mBackupDirectory.exists()) {
            return;
        }
        if (this.mBaseDirectory.exists()) {
            deleteDirectory(this.mBaseDirectory);
        }
        if (!this.mBackupDirectory.renameTo(this.mBaseDirectory)) {
            throw new java.io.IOException("Failed to restore " + this.mBackupDirectory + " to " + this.mBaseDirectory);
        }
        syncParentDirectory();
    }

    private static boolean deleteDirectory(java.io.File file) {
        return android.os.FileUtils.deleteContentsAndDir(file);
    }

    private void syncParentDirectory() {
        syncDirectory(this.mBaseDirectory.getParentFile());
    }

    private static void syncDirectory(java.io.File file) {
        java.lang.String absolutePath = file.getAbsolutePath();
        try {
            java.io.FileDescriptor open = android.system.Os.open(absolutePath, android.system.OsConstants.O_RDONLY, 0);
            try {
                try {
                    android.system.Os.fsync(open);
                } catch (android.system.ErrnoException e) {
                    android.util.Log.e(LOG_TAG, "Failed to fsync " + absolutePath, e);
                }
            } finally {
                android.os.FileUtils.closeQuietly(open);
            }
        } catch (android.system.ErrnoException e2) {
            android.util.Log.e(LOG_TAG, "Failed to open " + absolutePath, e2);
        }
    }
}
