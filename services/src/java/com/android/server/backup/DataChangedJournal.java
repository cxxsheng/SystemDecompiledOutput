package com.android.server.backup;

/* loaded from: classes.dex */
public class DataChangedJournal {
    private static final int BUFFER_SIZE_BYTES = 8192;
    private static final java.lang.String FILE_NAME_PREFIX = "journal";
    private static final java.lang.String TAG = "DataChangedJournal";
    private final java.io.File mFile;

    DataChangedJournal(@android.annotation.NonNull java.io.File file) {
        java.util.Objects.requireNonNull(file);
        this.mFile = file;
    }

    public void addPackage(java.lang.String str) throws java.io.IOException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(this.mFile, "rws");
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

    public void forEach(java.util.function.Consumer<java.lang.String> consumer) throws java.io.IOException {
        try {
            try {
                try {
                    while (true) {
                        try {
                            consumer.accept(new java.io.DataInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(this.mFile), 8192)).readUTF());
                        } finally {
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (java.io.EOFException e) {
        }
    }

    public java.util.List<java.lang.String> getPackages() throws java.io.IOException {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Objects.requireNonNull(arrayList);
        forEach(new java.util.function.Consumer() { // from class: com.android.server.backup.DataChangedJournal$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((java.lang.String) obj);
            }
        });
        return arrayList;
    }

    public boolean delete() {
        return this.mFile.delete();
    }

    public int hashCode() {
        return this.mFile.hashCode();
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj instanceof com.android.server.backup.DataChangedJournal) {
            return this.mFile.equals(((com.android.server.backup.DataChangedJournal) obj).mFile);
        }
        return false;
    }

    public java.lang.String toString() {
        return this.mFile.toString();
    }

    static com.android.server.backup.DataChangedJournal newJournal(@android.annotation.NonNull java.io.File file) throws java.io.IOException {
        java.util.Objects.requireNonNull(file);
        return new com.android.server.backup.DataChangedJournal(java.io.File.createTempFile(FILE_NAME_PREFIX, null, file));
    }

    static java.util.ArrayList<com.android.server.backup.DataChangedJournal> listJournals(java.io.File file) {
        java.util.ArrayList<com.android.server.backup.DataChangedJournal> arrayList = new java.util.ArrayList<>();
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null) {
            android.util.Slog.w(TAG, "Failed to read journal files");
            return arrayList;
        }
        for (java.io.File file2 : listFiles) {
            arrayList.add(new com.android.server.backup.DataChangedJournal(file2));
        }
        return arrayList;
    }
}
