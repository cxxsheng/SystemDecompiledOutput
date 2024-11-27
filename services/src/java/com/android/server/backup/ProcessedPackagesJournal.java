package com.android.server.backup;

/* loaded from: classes.dex */
final class ProcessedPackagesJournal {
    private static final boolean DEBUG = true;
    private static final java.lang.String JOURNAL_FILE_NAME = "processed";
    private static final java.lang.String TAG = "ProcessedPackagesJournal";

    @com.android.internal.annotations.GuardedBy({"mProcessedPackages"})
    private final java.util.Set<java.lang.String> mProcessedPackages = new java.util.HashSet();
    private final java.io.File mStateDirectory;

    ProcessedPackagesJournal(java.io.File file) {
        this.mStateDirectory = file;
    }

    void init() {
        synchronized (this.mProcessedPackages) {
            loadFromDisk();
        }
    }

    boolean hasBeenProcessed(java.lang.String str) {
        boolean contains;
        synchronized (this.mProcessedPackages) {
            contains = this.mProcessedPackages.contains(str);
        }
        return contains;
    }

    void addPackage(java.lang.String str) {
        java.io.RandomAccessFile randomAccessFile;
        synchronized (this.mProcessedPackages) {
            try {
                if (this.mProcessedPackages.add(str)) {
                    java.io.File file = new java.io.File(this.mStateDirectory, JOURNAL_FILE_NAME);
                    try {
                        randomAccessFile = new java.io.RandomAccessFile(file, "rws");
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Can't log backup of " + str + " to " + file);
                    }
                    try {
                        randomAccessFile.seek(randomAccessFile.length());
                        randomAccessFile.writeUTF(str);
                        randomAccessFile.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            randomAccessFile.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }

    java.util.Set<java.lang.String> getPackagesCopy() {
        java.util.HashSet hashSet;
        synchronized (this.mProcessedPackages) {
            hashSet = new java.util.HashSet(this.mProcessedPackages);
        }
        return hashSet;
    }

    void reset() {
        synchronized (this.mProcessedPackages) {
            this.mProcessedPackages.clear();
            new java.io.File(this.mStateDirectory, JOURNAL_FILE_NAME).delete();
        }
    }

    private void loadFromDisk() {
        java.io.File file = new java.io.File(this.mStateDirectory, JOURNAL_FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try {
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
            while (true) {
                try {
                    java.lang.String readUTF = dataInputStream.readUTF();
                    android.util.Slog.v(TAG, "   + " + readUTF);
                    this.mProcessedPackages.add(readUTF);
                } catch (java.lang.Throwable th) {
                    try {
                        dataInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (java.io.EOFException e) {
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Error reading processed packages journal", e2);
        }
    }
}
