package com.android.internal.util;

/* loaded from: classes5.dex */
public class FileRotator {
    private static final boolean LOGD = false;
    private static final java.lang.String SUFFIX_BACKUP = ".backup";
    private static final java.lang.String SUFFIX_NO_BACKUP = ".no_backup";
    private static final java.lang.String TAG = "FileRotator";
    private final java.io.File mBasePath;
    private final long mDeleteAgeMillis;
    private final java.lang.String mPrefix;
    private final long mRotateAgeMillis;

    public interface Reader {
        void read(java.io.InputStream inputStream) throws java.io.IOException;
    }

    public interface Rewriter extends com.android.internal.util.FileRotator.Reader, com.android.internal.util.FileRotator.Writer {
        void reset();

        boolean shouldWrite();
    }

    public interface Writer {
        void write(java.io.OutputStream outputStream) throws java.io.IOException;
    }

    public FileRotator(java.io.File file, java.lang.String str, long j, long j2) {
        this.mBasePath = (java.io.File) java.util.Objects.requireNonNull(file);
        this.mPrefix = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mRotateAgeMillis = j;
        this.mDeleteAgeMillis = j2;
        this.mBasePath.mkdirs();
        for (java.lang.String str2 : this.mBasePath.list()) {
            if (str2.startsWith(this.mPrefix)) {
                if (str2.endsWith(SUFFIX_BACKUP)) {
                    new java.io.File(this.mBasePath, str2).renameTo(new java.io.File(this.mBasePath, str2.substring(0, str2.length() - SUFFIX_BACKUP.length())));
                } else if (str2.endsWith(SUFFIX_NO_BACKUP)) {
                    java.io.File file2 = new java.io.File(this.mBasePath, str2);
                    java.io.File file3 = new java.io.File(this.mBasePath, str2.substring(0, str2.length() - SUFFIX_NO_BACKUP.length()));
                    file2.delete();
                    file3.delete();
                }
            }
        }
    }

    public void deleteAll() {
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        for (java.lang.String str : this.mBasePath.list()) {
            if (fileInfo.parse(str)) {
                new java.io.File(this.mBasePath, str).delete();
            }
        }
    }

    public void dumpAll(java.io.OutputStream outputStream) throws java.io.IOException {
        java.util.zip.ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(outputStream);
        try {
            com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
            for (java.lang.String str : this.mBasePath.list()) {
                if (fileInfo.parse(str)) {
                    zipOutputStream.putNextEntry(new java.util.zip.ZipEntry(str));
                    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(new java.io.File(this.mBasePath, str));
                    try {
                        android.os.FileUtils.copy(fileInputStream, zipOutputStream);
                        libcore.io.IoUtils.closeQuietly(fileInputStream);
                        zipOutputStream.closeEntry();
                    } finally {
                    }
                }
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(zipOutputStream);
        }
    }

    public void rewriteActive(com.android.internal.util.FileRotator.Rewriter rewriter, long j) throws java.io.IOException {
        rewriteSingle(rewriter, getActiveName(j));
    }

    @java.lang.Deprecated
    public void combineActive(final com.android.internal.util.FileRotator.Reader reader, final com.android.internal.util.FileRotator.Writer writer, long j) throws java.io.IOException {
        rewriteActive(new com.android.internal.util.FileRotator.Rewriter() { // from class: com.android.internal.util.FileRotator.1
            @Override // com.android.internal.util.FileRotator.Rewriter
            public void reset() {
            }

            @Override // com.android.internal.util.FileRotator.Reader
            public void read(java.io.InputStream inputStream) throws java.io.IOException {
                reader.read(inputStream);
            }

            @Override // com.android.internal.util.FileRotator.Rewriter
            public boolean shouldWrite() {
                return true;
            }

            @Override // com.android.internal.util.FileRotator.Writer
            public void write(java.io.OutputStream outputStream) throws java.io.IOException {
                writer.write(outputStream);
            }
        }, j);
    }

    public void rewriteAll(com.android.internal.util.FileRotator.Rewriter rewriter) throws java.io.IOException {
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        for (java.lang.String str : this.mBasePath.list()) {
            if (fileInfo.parse(str)) {
                rewriteSingle(rewriter, str);
            }
        }
    }

    private void rewriteSingle(com.android.internal.util.FileRotator.Rewriter rewriter, java.lang.String str) throws java.io.IOException {
        java.io.File file = new java.io.File(this.mBasePath, str);
        rewriter.reset();
        if (file.exists()) {
            readFile(file, rewriter);
            if (rewriter.shouldWrite()) {
                java.io.File file2 = new java.io.File(this.mBasePath, str + SUFFIX_BACKUP);
                file.renameTo(file2);
                try {
                    writeFile(file, rewriter);
                    file2.delete();
                    return;
                } catch (java.lang.Throwable th) {
                    file.delete();
                    file2.renameTo(file);
                    throw rethrowAsIoException(th);
                }
            }
            return;
        }
        java.io.File file3 = new java.io.File(this.mBasePath, str + SUFFIX_NO_BACKUP);
        file3.createNewFile();
        try {
            writeFile(file, rewriter);
            file3.delete();
        } catch (java.lang.Throwable th2) {
            file.delete();
            file3.delete();
            throw rethrowAsIoException(th2);
        }
    }

    public void rewriteSingle(com.android.internal.util.FileRotator.Rewriter rewriter, long j, long j2) throws java.io.IOException {
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        fileInfo.startMillis = j;
        fileInfo.endMillis = j2;
        rewriteSingle(rewriter, fileInfo.build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void readMatching(com.android.internal.util.FileRotator.Reader reader, long j, long j2) throws java.io.IOException {
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        java.util.TreeSet treeSet = new java.util.TreeSet(java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.internal.util.FileRotator$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                long longValue;
                longValue = ((java.lang.Long) ((android.util.Pair) obj).first).longValue();
                return longValue;
            }
        }));
        for (java.lang.String str : this.mBasePath.list()) {
            if (fileInfo.parse(str) && fileInfo.startMillis <= j2 && j <= fileInfo.endMillis) {
                treeSet.add(new android.util.Pair(java.lang.Long.valueOf(fileInfo.startMillis), str));
            }
        }
        java.util.Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            readFile(new java.io.File(this.mBasePath, (java.lang.String) ((android.util.Pair) it.next()).second), reader);
        }
    }

    private java.lang.String getActiveName(long j) {
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        java.lang.String str = null;
        long j2 = Long.MAX_VALUE;
        for (java.lang.String str2 : this.mBasePath.list()) {
            if (fileInfo.parse(str2) && fileInfo.isActive() && fileInfo.startMillis < j && fileInfo.startMillis < j2) {
                j2 = fileInfo.startMillis;
                str = str2;
            }
        }
        if (str != null) {
            return str;
        }
        fileInfo.startMillis = j;
        fileInfo.endMillis = Long.MAX_VALUE;
        return fileInfo.build();
    }

    public void maybeRotate(long j) {
        long j2 = j - this.mRotateAgeMillis;
        long j3 = j - this.mDeleteAgeMillis;
        com.android.internal.util.FileRotator.FileInfo fileInfo = new com.android.internal.util.FileRotator.FileInfo(this.mPrefix);
        java.lang.String[] list = this.mBasePath.list();
        if (list == null) {
            return;
        }
        for (java.lang.String str : list) {
            if (fileInfo.parse(str)) {
                if (fileInfo.isActive()) {
                    if (fileInfo.startMillis <= j2) {
                        fileInfo.endMillis = j;
                        new java.io.File(this.mBasePath, str).renameTo(new java.io.File(this.mBasePath, fileInfo.build()));
                    }
                } else if (fileInfo.endMillis <= j3) {
                    new java.io.File(this.mBasePath, str).delete();
                }
            }
        }
    }

    private static void readFile(java.io.File file, com.android.internal.util.FileRotator.Reader reader) throws java.io.IOException {
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
        try {
            reader.read(bufferedInputStream);
        } finally {
            libcore.io.IoUtils.closeQuietly(bufferedInputStream);
        }
    }

    private static void writeFile(java.io.File file, com.android.internal.util.FileRotator.Writer writer) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(fileOutputStream);
        try {
            writer.write(bufferedOutputStream);
            bufferedOutputStream.flush();
        } finally {
            try {
                fileOutputStream.getFD().sync();
            } catch (java.io.IOException e) {
            }
            libcore.io.IoUtils.closeQuietly(bufferedOutputStream);
        }
    }

    private static java.io.IOException rethrowAsIoException(java.lang.Throwable th) throws java.io.IOException {
        if (th instanceof java.io.IOException) {
            throw ((java.io.IOException) th);
        }
        throw new java.io.IOException(th.getMessage(), th);
    }

    private static class FileInfo {
        public long endMillis;
        public final java.lang.String prefix;
        public long startMillis;

        public FileInfo(java.lang.String str) {
            this.prefix = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        public boolean parse(java.lang.String str) {
            this.endMillis = -1L;
            this.startMillis = -1L;
            int lastIndexOf = str.lastIndexOf(46);
            int lastIndexOf2 = str.lastIndexOf(45);
            if (lastIndexOf == -1 || lastIndexOf2 == -1 || !this.prefix.equals(str.substring(0, lastIndexOf))) {
                return false;
            }
            try {
                this.startMillis = java.lang.Long.parseLong(str.substring(lastIndexOf + 1, lastIndexOf2));
                if (str.length() - lastIndexOf2 == 1) {
                    this.endMillis = Long.MAX_VALUE;
                } else {
                    this.endMillis = java.lang.Long.parseLong(str.substring(lastIndexOf2 + 1));
                }
                return true;
            } catch (java.lang.NumberFormatException e) {
                return false;
            }
        }

        public java.lang.String build() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.prefix).append('.').append(this.startMillis).append('-');
            if (this.endMillis != Long.MAX_VALUE) {
                sb.append(this.endMillis);
            }
            return sb.toString();
        }

        public boolean isActive() {
            return this.endMillis == Long.MAX_VALUE;
        }
    }
}
