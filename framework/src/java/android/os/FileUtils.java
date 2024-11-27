package android.os;

/* loaded from: classes3.dex */
public final class FileUtils {
    private static final long COPY_CHECKPOINT_BYTES = 524288;
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;
    private static final java.lang.String TAG = "FileUtils";
    private static boolean sEnableCopyOptimizations;
    private static volatile int sMediaProviderAppId = -1;

    public interface ProgressListener {
        void onProgress(long j);
    }

    private FileUtils() {
    }

    private static class NoImagePreloadHolder {
        public static final java.util.regex.Pattern SAFE_FILENAME_PATTERN = java.util.regex.Pattern.compile("[\\w%+,./=_-]+");

        private NoImagePreloadHolder() {
        }
    }

    static {
        sEnableCopyOptimizations = true;
        sEnableCopyOptimizations = shouldEnableCopyOptimizations();
    }

    private static boolean shouldEnableCopyOptimizations() {
        return true;
    }

    private static boolean shouldEnableCopyOptimizations$ravenwood() {
        return false;
    }

    public static int setPermissions(java.io.File file, int i, int i2, int i3) {
        return setPermissions(file.getAbsolutePath(), i, i2, i3);
    }

    public static int setPermissions(java.lang.String str, int i, int i2, int i3) {
        try {
            android.system.Os.chmod(str, i);
            if (i2 >= 0 || i3 >= 0) {
                try {
                    android.system.Os.chown(str, i2, i3);
                    return 0;
                } catch (android.system.ErrnoException e) {
                    android.util.Slog.w(TAG, "Failed to chown(" + str + "): " + e);
                    return e.errno;
                }
            }
            return 0;
        } catch (android.system.ErrnoException e2) {
            android.util.Slog.w(TAG, "Failed to chmod(" + str + "): " + e2);
            return e2.errno;
        }
    }

    public static int setPermissions(java.io.FileDescriptor fileDescriptor, int i, int i2, int i3) {
        try {
            android.system.Os.fchmod(fileDescriptor, i);
            if (i2 >= 0 || i3 >= 0) {
                try {
                    android.system.Os.fchown(fileDescriptor, i2, i3);
                    return 0;
                } catch (android.system.ErrnoException e) {
                    android.util.Slog.w(TAG, "Failed to fchown(): " + e);
                    return e.errno;
                }
            }
            return 0;
        } catch (android.system.ErrnoException e2) {
            android.util.Slog.w(TAG, "Failed to fchmod(): " + e2);
            return e2.errno;
        }
    }

    public static void copyPermissions(java.io.File file, java.io.File file2) throws java.io.IOException {
        try {
            android.system.StructStat stat = android.system.Os.stat(file.getAbsolutePath());
            android.system.Os.chmod(file2.getAbsolutePath(), stat.st_mode);
            android.system.Os.chown(file2.getAbsolutePath(), stat.st_uid, stat.st_gid);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @java.lang.Deprecated
    public static int getUid(java.lang.String str) {
        try {
            return android.system.Os.stat(str).st_uid;
        } catch (android.system.ErrnoException e) {
            return -1;
        }
    }

    public static boolean sync(java.io.FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
                return true;
            } catch (java.io.IOException e) {
                return false;
            }
        }
        return true;
    }

    @java.lang.Deprecated
    public static boolean copyFile(java.io.File file, java.io.File file2) {
        try {
            copyFileOrThrow(file, file2);
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    @java.lang.Deprecated
    public static void copyFileOrThrow(java.io.File file, java.io.File file2) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            copyToFileOrThrow(fileInputStream, file2);
            fileInputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @java.lang.Deprecated
    public static boolean copyToFile(java.io.InputStream inputStream, java.io.File file) {
        try {
            copyToFileOrThrow(inputStream, file);
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    @java.lang.Deprecated
    public static void copyToFileOrThrow(java.io.InputStream inputStream, java.io.File file) throws java.io.IOException {
        if (file.exists()) {
            file.delete();
        }
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        try {
            copy(inputStream, fileOutputStream);
            sync(fileOutputStream);
            fileOutputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long copy(java.io.File file, java.io.File file2) throws java.io.IOException {
        return copy(file, file2, (android.os.CancellationSignal) null, (java.util.concurrent.Executor) null, (android.os.FileUtils.ProgressListener) null);
    }

    public static long copy(java.io.File file, java.io.File file2, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file2);
            try {
                long copy = copy(fileInputStream, fileOutputStream, cancellationSignal, executor, progressListener);
                fileOutputStream.close();
                fileInputStream.close();
                return copy;
            } finally {
            }
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long copy(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        return copy(inputStream, outputStream, (android.os.CancellationSignal) null, (java.util.concurrent.Executor) null, (android.os.FileUtils.ProgressListener) null);
    }

    public static long copy(java.io.InputStream inputStream, java.io.OutputStream outputStream, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        if (sEnableCopyOptimizations && (inputStream instanceof java.io.FileInputStream) && (outputStream instanceof java.io.FileOutputStream)) {
            return copy(((java.io.FileInputStream) inputStream).getFD(), ((java.io.FileOutputStream) outputStream).getFD(), cancellationSignal, executor, progressListener);
        }
        return copyInternalUserspace(inputStream, outputStream, cancellationSignal, executor, progressListener);
    }

    public static long copy(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2) throws java.io.IOException {
        return copy(fileDescriptor, fileDescriptor2, (android.os.CancellationSignal) null, (java.util.concurrent.Executor) null, (android.os.FileUtils.ProgressListener) null);
    }

    public static long copy(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        return copy(fileDescriptor, fileDescriptor2, Long.MAX_VALUE, cancellationSignal, executor, progressListener);
    }

    public static long copy(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        if (sEnableCopyOptimizations) {
            try {
                android.system.StructStat fstat = android.system.Os.fstat(fileDescriptor);
                android.system.StructStat fstat2 = android.system.Os.fstat(fileDescriptor2);
                if (android.system.OsConstants.S_ISREG(fstat.st_mode) && android.system.OsConstants.S_ISREG(fstat2.st_mode)) {
                    try {
                        return copyInternalSendfile(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                    } catch (android.system.ErrnoException e) {
                        if (e.errno != android.system.OsConstants.EINVAL && e.errno != android.system.OsConstants.ENOSYS) {
                            throw e;
                        }
                        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                    }
                }
                if (!android.system.OsConstants.S_ISFIFO(fstat.st_mode) && !android.system.OsConstants.S_ISFIFO(fstat2.st_mode)) {
                    if (!android.system.OsConstants.S_ISSOCK(fstat.st_mode) && !android.system.OsConstants.S_ISSOCK(fstat2.st_mode)) {
                    }
                    return copyInternalSpliceSocket(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
                }
                return copyInternalSplice(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
            } catch (android.system.ErrnoException e2) {
                throw e2.rethrowAsIOException();
            }
        }
        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, executor, progressListener);
    }

    public static long copyInternalSplice(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.os.FileUtils.ProgressListener progressListener) throws android.system.ErrnoException {
        long j2 = j;
        final long j3 = 0;
        long j4 = 0;
        while (true) {
            long splice = android.system.Os.splice(fileDescriptor, null, fileDescriptor2, null, java.lang.Math.min(j2, 524288L), android.system.OsConstants.SPLICE_F_MOVE | android.system.OsConstants.SPLICE_F_MORE);
            if (splice == 0) {
                break;
            }
            j3 += splice;
            j4 += splice;
            j2 -= splice;
            if (j4 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.FileUtils.ProgressListener.this.onProgress(j3);
                        }
                    });
                }
                j4 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.FileUtils.ProgressListener.this.onProgress(j3);
                }
            });
        }
        return j3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0081, code lost:
    
        if (r36 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0084, code lost:
    
        r35.execute(new android.os.FileUtils$$ExternalSyntheticLambda7(r36, r11));
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x008c, code lost:
    
        android.system.Os.close(r4[0]);
        android.system.Os.close(r4[1]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0096, code lost:
    
        return r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x007d, code lost:
    
        if (r35 == null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long copyInternalSpliceSocket(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.os.FileUtils.ProgressListener progressListener) throws android.system.ErrnoException {
        java.util.concurrent.Executor executor2;
        long j2;
        long j3;
        final android.os.FileUtils.ProgressListener progressListener2;
        java.io.FileDescriptor[] pipe = android.system.Os.pipe();
        long j4 = 0;
        long j5 = j;
        long j6 = 0;
        final long j7 = 0;
        long j8 = 0;
        while (true) {
            if (j5 <= j4 && j6 <= j4) {
                break;
            }
            long j9 = j8;
            if (j5 <= j4) {
                executor2 = executor;
            } else {
                long splice = android.system.Os.splice(fileDescriptor, null, pipe[1], null, java.lang.Math.min(j5, 524288L), android.system.OsConstants.SPLICE_F_MOVE | android.system.OsConstants.SPLICE_F_MORE);
                if (splice < 0) {
                    android.util.Slog.e(TAG, "splice error, fdIn --> pipe, copy size:" + j + ", copied:" + j7 + ", read:" + (j - j5) + ", in pipe:" + j6);
                    break;
                }
                executor2 = executor;
                if (splice == 0) {
                    android.util.Slog.w(TAG, "Reached the end of the input file. The size to be copied exceeds the actual size, copy size:" + j + ", copied:" + j7 + ", read:" + (j - j5) + ", in pipe:" + j6);
                    j5 = 0;
                } else {
                    j6 += splice;
                    j5 -= splice;
                }
            }
            if (j6 <= 0) {
                j2 = 524288;
                j3 = 0;
            } else {
                j2 = 524288;
                long splice2 = android.system.Os.splice(pipe[0], null, fileDescriptor2, null, java.lang.Math.min(j6, 524288L), android.system.OsConstants.SPLICE_F_MOVE | android.system.OsConstants.SPLICE_F_MORE);
                j3 = 0;
                if (splice2 <= 0) {
                    android.util.Slog.e(TAG, "splice error, pipe --> fdOut, copy size:" + j + ", copied:" + j7 + ", read:" + (j - j5) + ", in pipe: " + j6);
                    android.system.Os.close(pipe[0]);
                    android.system.Os.close(pipe[1]);
                    throw new android.system.ErrnoException("splice, pipe --> fdOut", android.system.OsConstants.EIO);
                }
                j7 += splice2;
                j6 -= splice2;
                j9 += splice2;
            }
            if (j9 >= j2) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor2 != null) {
                    progressListener2 = progressListener;
                    if (progressListener2 != null) {
                        executor2.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.os.FileUtils.ProgressListener.this.onProgress(j7);
                            }
                        });
                    }
                } else {
                    progressListener2 = progressListener;
                }
                j4 = j3;
                j8 = j4;
            } else {
                j4 = j3;
                j8 = j9;
            }
        }
    }

    public static long copyInternalSendfile(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.os.FileUtils.ProgressListener progressListener) throws android.system.ErrnoException {
        long j2 = j;
        final long j3 = 0;
        long j4 = 0;
        while (true) {
            long sendfile = android.system.Os.sendfile(fileDescriptor2, fileDescriptor, null, java.lang.Math.min(j2, 524288L));
            if (sendfile == 0) {
                break;
            }
            j3 += sendfile;
            j4 += sendfile;
            j2 -= sendfile;
            if (j4 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.FileUtils.ProgressListener.this.onProgress(j3);
                        }
                    });
                }
                j4 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.FileUtils.ProgressListener.this.onProgress(j3);
                }
            });
        }
        return j3;
    }

    @java.lang.Deprecated
    public static long copyInternalUserspace(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, android.os.FileUtils.ProgressListener progressListener, android.os.CancellationSignal cancellationSignal, long j) throws java.io.IOException {
        return copyInternalUserspace(fileDescriptor, fileDescriptor2, j, cancellationSignal, new android.app.PendingIntent$$ExternalSyntheticLambda0(), progressListener);
    }

    public static long copyInternalUserspace(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, long j, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        if (j != Long.MAX_VALUE) {
            return copyInternalUserspace(new com.android.internal.util.SizedInputStream(new java.io.FileInputStream(fileDescriptor), j), new java.io.FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
        }
        return copyInternalUserspace(new java.io.FileInputStream(fileDescriptor), new java.io.FileOutputStream(fileDescriptor2), cancellationSignal, executor, progressListener);
    }

    public static long copyInternalUserspace(java.io.InputStream inputStream, java.io.OutputStream outputStream, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.os.FileUtils.ProgressListener progressListener) throws java.io.IOException {
        byte[] bArr = new byte[8192];
        final long j = 0;
        long j2 = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                break;
            }
            outputStream.write(bArr, 0, read);
            long j3 = read;
            j += j3;
            j2 += j3;
            if (j2 >= 524288) {
                if (cancellationSignal != null) {
                    cancellationSignal.throwIfCanceled();
                }
                if (executor != null && progressListener != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.FileUtils.ProgressListener.this.onProgress(j);
                        }
                    });
                }
                j2 = 0;
            }
        }
        if (executor != null && progressListener != null) {
            executor.execute(new java.lang.Runnable() { // from class: android.os.FileUtils$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.FileUtils.ProgressListener.this.onProgress(j);
                }
            });
        }
        return j;
    }

    public static boolean isFilenameSafe(java.io.File file) {
        return android.os.FileUtils.NoImagePreloadHolder.SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    public static java.lang.String readTextFile(java.io.File file, int i, java.lang.String str) throws java.io.IOException {
        int read;
        boolean z;
        int read2;
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(fileInputStream);
        try {
            long length = file.length();
            if (i > 0 || (length > 0 && i == 0)) {
                if (length > 0 && (i == 0 || length < i)) {
                    i = (int) length;
                }
                byte[] bArr = new byte[i + 1];
                int read3 = bufferedInputStream.read(bArr);
                return read3 <= 0 ? "" : read3 <= i ? new java.lang.String(bArr, 0, read3) : str == null ? new java.lang.String(bArr, 0, i) : new java.lang.String(bArr, 0, i) + str;
            }
            if (i >= 0) {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                byte[] bArr2 = new byte[1024];
                do {
                    read = bufferedInputStream.read(bArr2);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr2, 0, read);
                    }
                } while (read == 1024);
                return byteArrayOutputStream.toString();
            }
            byte[] bArr3 = null;
            byte[] bArr4 = null;
            boolean z2 = false;
            while (true) {
                z = true;
                if (bArr3 != null) {
                    z2 = true;
                }
                if (bArr3 == null) {
                    bArr3 = new byte[-i];
                }
                read2 = bufferedInputStream.read(bArr3);
                if (read2 != bArr3.length) {
                    break;
                }
                byte[] bArr5 = bArr4;
                bArr4 = bArr3;
                bArr3 = bArr5;
            }
            if (bArr4 == null && read2 <= 0) {
                return "";
            }
            if (bArr4 == null) {
                return new java.lang.String(bArr3, 0, read2);
            }
            if (read2 > 0) {
                java.lang.System.arraycopy(bArr4, read2, bArr4, 0, bArr4.length - read2);
                java.lang.System.arraycopy(bArr3, 0, bArr4, bArr4.length - read2, read2);
            } else {
                z = z2;
            }
            if (str != null && z) {
                return str + new java.lang.String(bArr4);
            }
            return new java.lang.String(bArr4);
        } finally {
            bufferedInputStream.close();
            fileInputStream.close();
        }
    }

    public static void stringToFile(java.io.File file, java.lang.String str) throws java.io.IOException {
        stringToFile(file.getAbsolutePath(), str);
    }

    public static void bytesToFile(java.lang.String str, byte[] bArr) throws java.io.IOException {
        if (str.startsWith("/proc/")) {
            int allowThreadDiskWritesMask = android.os.StrictMode.allowThreadDiskWritesMask();
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(str);
                try {
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                    return;
                } finally {
                }
            } finally {
                android.os.StrictMode.setThreadPolicyMask(allowThreadDiskWritesMask);
            }
        }
        java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(str);
        try {
            fileOutputStream2.write(bArr);
            fileOutputStream2.close();
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream2.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void stringToFile(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        bytesToFile(str, str2.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    @java.lang.Deprecated
    public static long checksumCrc32(java.io.File file) throws java.io.FileNotFoundException, java.io.IOException {
        java.util.zip.CheckedInputStream checkedInputStream;
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        java.util.zip.CheckedInputStream checkedInputStream2 = null;
        try {
            checkedInputStream = new java.util.zip.CheckedInputStream(new java.io.FileInputStream(file), crc32);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            while (checkedInputStream.read(new byte[128]) >= 0) {
            }
            long value = crc32.getValue();
            try {
                checkedInputStream.close();
            } catch (java.io.IOException e) {
            }
            return value;
        } catch (java.lang.Throwable th2) {
            th = th2;
            checkedInputStream2 = checkedInputStream;
            if (checkedInputStream2 != null) {
                try {
                    checkedInputStream2.close();
                } catch (java.io.IOException e2) {
                }
            }
            throw th;
        }
    }

    public static byte[] digest(java.io.File file, java.lang.String str) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        try {
            byte[] digest = digest(fileInputStream, str);
            fileInputStream.close();
            return digest;
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] digest(java.io.InputStream inputStream, java.lang.String str) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        return digestInternalUserspace(inputStream, str);
    }

    public static byte[] digest(java.io.FileDescriptor fileDescriptor, java.lang.String str) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        return digestInternalUserspace(new java.io.FileInputStream(fileDescriptor), str);
    }

    private static byte[] digestInternalUserspace(java.io.InputStream inputStream, java.lang.String str) throws java.io.IOException, java.security.NoSuchAlgorithmException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(str);
        java.security.DigestInputStream digestInputStream = new java.security.DigestInputStream(inputStream, messageDigest);
        try {
            do {
            } while (digestInputStream.read(new byte[8192]) != -1);
            digestInputStream.close();
            return messageDigest.digest();
        } catch (java.lang.Throwable th) {
            try {
                digestInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static boolean deleteOlderFiles(java.io.File file, int i, long j) {
        if (i < 0 || j < 0) {
            throw new java.lang.IllegalArgumentException("Constraints must be positive or 0");
        }
        java.io.File[] listFiles = file.listFiles();
        boolean z = false;
        if (listFiles == null) {
            return false;
        }
        java.util.Arrays.sort(listFiles, new java.util.Comparator<java.io.File>() { // from class: android.os.FileUtils.1
            @Override // java.util.Comparator
            public int compare(java.io.File file2, java.io.File file3) {
                return java.lang.Long.compare(file3.lastModified(), file2.lastModified());
            }
        });
        while (i < listFiles.length) {
            java.io.File file2 = listFiles[i];
            if (java.lang.System.currentTimeMillis() - file2.lastModified() > j && file2.delete()) {
                android.util.Log.d(TAG, "Deleted old file " + file2);
                z = true;
            }
            i++;
        }
        return z;
    }

    public static boolean contains(java.io.File[] fileArr, java.io.File file) {
        for (java.io.File file2 : fileArr) {
            if (contains(file2, file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(java.util.Collection<java.io.File> collection, java.io.File file) {
        java.util.Iterator<java.io.File> it = collection.iterator();
        while (it.hasNext()) {
            if (contains(it.next(), file)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(java.io.File file, java.io.File file2) {
        if (file == null || file2 == null) {
            return false;
        }
        return contains(file.getAbsolutePath(), file2.getAbsolutePath());
    }

    public static boolean contains(java.lang.String str, java.lang.String str2) {
        if (str.equals(str2)) {
            return true;
        }
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        return str2.startsWith(str);
    }

    public static boolean deleteContentsAndDir(java.io.File file) {
        if (deleteContents(file)) {
            return file.delete();
        }
        return false;
    }

    public static boolean deleteContents(java.io.File file) {
        java.io.File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                if (file2.isDirectory()) {
                    z &= deleteContents(file2);
                }
                if (!file2.delete()) {
                    android.util.Log.w(TAG, "Failed to delete " + file2);
                    z = false;
                }
            }
        }
        return z;
    }

    private static boolean isValidExtFilenameChar(char c) {
        switch (c) {
            case 0:
            case '/':
                return false;
            default:
                return true;
        }
    }

    public static boolean isValidExtFilename(java.lang.String str) {
        return str != null && str.equals(buildValidExtFilename(str));
    }

    public static java.lang.String buildValidExtFilename(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str) || android.media.MediaMetrics.SEPARATOR.equals(str) || "..".equals(str)) {
            return "(invalid)";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (isValidExtFilenameChar(charAt)) {
                sb.append(charAt);
            } else {
                sb.append('_');
            }
        }
        trimFilename(sb, 255);
        return sb.toString();
    }

    private static boolean isValidFatFilenameChar(char c) {
        if (c < 0 || c > 31) {
            switch (c) {
                case '\"':
                case '*':
                case '/':
                case ':':
                case '<':
                case '>':
                case '?':
                case '\\':
                case '|':
                case 127:
                    break;
            }
            return false;
        }
        return false;
    }

    public static boolean isValidFatFilename(java.lang.String str) {
        return str != null && str.equals(buildValidFatFilename(str));
    }

    public static java.lang.String buildValidFatFilename(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str) || android.media.MediaMetrics.SEPARATOR.equals(str) || "..".equals(str)) {
            return "(invalid)";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (isValidFatFilenameChar(charAt)) {
                sb.append(charAt);
            } else {
                sb.append('_');
            }
        }
        trimFilename(sb, 255);
        return sb.toString();
    }

    public static java.lang.String trimFilename(java.lang.String str, int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        trimFilename(sb, i);
        return sb.toString();
    }

    private static void trimFilename(java.lang.StringBuilder sb, int i) {
        byte[] bytes = sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        if (bytes.length > i) {
            int i2 = i - 3;
            while (bytes.length > i2) {
                sb.deleteCharAt(sb.length() / 2);
                bytes = sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
            }
            sb.insert(sb.length() / 2, android.telecom.Logging.Session.TRUNCATE_STRING);
        }
    }

    public static java.lang.String rewriteAfterRename(java.io.File file, java.io.File file2, java.lang.String str) {
        java.io.File rewriteAfterRename;
        if (str == null || (rewriteAfterRename = rewriteAfterRename(file, file2, new java.io.File(str))) == null) {
            return null;
        }
        return rewriteAfterRename.getAbsolutePath();
    }

    public static java.lang.String[] rewriteAfterRename(java.io.File file, java.io.File file2, java.lang.String[] strArr) {
        if (strArr == null) {
            return null;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = rewriteAfterRename(file, file2, strArr[i]);
        }
        return strArr2;
    }

    public static java.io.File rewriteAfterRename(java.io.File file, java.io.File file2, java.io.File file3) {
        if (file3 == null || file == null || file2 == null || !contains(file, file3)) {
            return null;
        }
        return new java.io.File(file2, file3.getAbsolutePath().substring(file.getAbsolutePath().length()));
    }

    private static java.io.File buildUniqueFileWithExtension(java.io.File file, java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        java.io.File buildFile = buildFile(file, str, str2);
        int i = 0;
        while (buildFile.exists()) {
            int i2 = i + 1;
            if (i >= 32) {
                throw new java.io.FileNotFoundException("Failed to create unique file");
            }
            i = i2;
            buildFile = buildFile(file, str + " (" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, str2);
        }
        return buildFile;
    }

    public static java.io.File buildUniqueFile(java.io.File file, java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        java.lang.String[] splitFileName = splitFileName(str, str2);
        return buildUniqueFileWithExtension(file, splitFileName[0], splitFileName[1]);
    }

    public static java.io.File buildNonUniqueFile(java.io.File file, java.lang.String str, java.lang.String str2) {
        java.lang.String[] splitFileName = splitFileName(str, str2);
        return buildFile(file, splitFileName[0], splitFileName[1]);
    }

    public static java.io.File buildUniqueFile(java.io.File file, java.lang.String str) throws java.io.FileNotFoundException {
        java.lang.String str2;
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            java.lang.String substring = str.substring(0, lastIndexOf);
            str2 = str.substring(lastIndexOf + 1);
            str = substring;
        } else {
            str2 = null;
        }
        return buildUniqueFileWithExtension(file, str, str2);
    }

    public static java.lang.String[] splitFileName(java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        if (!android.provider.DocumentsContract.Document.MIME_TYPE_DIR.equals(str)) {
            int lastIndexOf = str2.lastIndexOf(46);
            if (lastIndexOf >= 0) {
                str3 = str2.substring(0, lastIndexOf);
                str4 = str2.substring(lastIndexOf + 1);
                str5 = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str4.toLowerCase());
            } else {
                str3 = str2;
                str4 = null;
                str5 = null;
            }
            if (str5 == null) {
                str5 = "application/octet-stream";
            }
            r1 = "application/octet-stream".equals(str) ? null : android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
            if (java.util.Objects.equals(str, str5) || java.util.Objects.equals(str4, r1)) {
                r1 = str4;
                str2 = str3;
            }
        }
        if (r1 == null) {
            r1 = "";
        }
        return new java.lang.String[]{str2, r1};
    }

    private static java.io.File buildFile(java.io.File file, java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str2)) {
            return new java.io.File(file, str);
        }
        return new java.io.File(file, str + android.media.MediaMetrics.SEPARATOR + str2);
    }

    public static java.lang.String[] listOrEmpty(java.io.File file) {
        return file != null ? com.android.internal.util.ArrayUtils.defeatNullable(file.list()) : android.util.EmptyArray.STRING;
    }

    public static java.io.File[] listFilesOrEmpty(java.io.File file) {
        return file != null ? com.android.internal.util.ArrayUtils.defeatNullable(file.listFiles()) : com.android.internal.util.ArrayUtils.EMPTY_FILE;
    }

    public static java.io.File[] listFilesOrEmpty(java.io.File file, java.io.FilenameFilter filenameFilter) {
        return file != null ? com.android.internal.util.ArrayUtils.defeatNullable(file.listFiles(filenameFilter)) : com.android.internal.util.ArrayUtils.EMPTY_FILE;
    }

    public static java.io.File newFileOrNull(java.lang.String str) {
        if (str != null) {
            return new java.io.File(str);
        }
        return null;
    }

    public static java.io.File createDir(java.io.File file, java.lang.String str) {
        java.io.File file2 = new java.io.File(file, str);
        if (createDir(file2)) {
            return file2;
        }
        return null;
    }

    public static boolean createDir(java.io.File file) {
        if (file.mkdir()) {
            return true;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        return false;
    }

    public static long roundStorageSize(long j) {
        long j2 = 1;
        long j3 = 1;
        while (true) {
            long j4 = j2 * j3;
            if (j4 < j) {
                j2 <<= 1;
                if (j2 > 512) {
                    j3 *= 1000;
                    j2 = 1;
                }
            } else {
                android.util.Log.d(TAG, java.lang.String.format("Rounded bytes from %d to %d", java.lang.Long.valueOf(j), java.lang.Long.valueOf(j4)));
                return j4;
            }
        }
    }

    private static long toBytes(long j, java.lang.String str) {
        java.lang.String upperCase = str.toUpperCase();
        if (android.hardware.gnss.GnssSignalType.CODE_TYPE_B.equals(upperCase)) {
            return j;
        }
        if ("K".equals(upperCase) || "KB".equals(upperCase)) {
            return android.util.DataUnit.KILOBYTES.toBytes(j);
        }
        if (android.hardware.gnss.GnssSignalType.CODE_TYPE_M.equals(upperCase) || "MB".equals(upperCase)) {
            return android.util.DataUnit.MEGABYTES.toBytes(j);
        }
        if ("G".equals(upperCase) || "GB".equals(upperCase)) {
            return android.util.DataUnit.GIGABYTES.toBytes(j);
        }
        if ("KI".equals(upperCase) || "KIB".equals(upperCase)) {
            return android.util.DataUnit.KIBIBYTES.toBytes(j);
        }
        if ("MI".equals(upperCase) || "MIB".equals(upperCase)) {
            return android.util.DataUnit.MEBIBYTES.toBytes(j);
        }
        if ("GI".equals(upperCase) || "GIB".equals(upperCase)) {
            return android.util.DataUnit.GIBIBYTES.toBytes(j);
        }
        return Long.MIN_VALUE;
    }

    public static long parseSize(java.lang.String str) {
        int i;
        if (str == null || str.isBlank()) {
            return Long.MIN_VALUE;
        }
        java.lang.String trim = str.trim();
        char charAt = trim.charAt(0);
        int i2 = 1;
        if (charAt == '-' || charAt == '+') {
            if (charAt != '-') {
                i = 1;
            } else {
                i = -1;
            }
            trim = trim.substring(1);
            i2 = i;
        }
        int i3 = 0;
        while (i3 < trim.length() && java.lang.Character.isDigit(trim.charAt(i3))) {
            i3++;
        }
        if (i3 == 0 || i3 == trim.length()) {
            return Long.MIN_VALUE;
        }
        return toBytes(i2 * java.lang.Long.valueOf(trim.substring(0, i3)).longValue(), trim.substring(i3).trim());
    }

    @java.lang.Deprecated
    public static void closeQuietly(java.lang.AutoCloseable autoCloseable) {
        libcore.io.IoUtils.closeQuietly(autoCloseable);
    }

    @java.lang.Deprecated
    public static void closeQuietly(java.io.FileDescriptor fileDescriptor) {
        libcore.io.IoUtils.closeQuietly(fileDescriptor);
    }

    public static int translateModeStringToPosix(java.lang.String str) {
        int i;
        for (int i2 = 0; i2 < str.length(); i2++) {
            switch (str.charAt(i2)) {
                case 'a':
                case 'r':
                case 't':
                case 'w':
                default:
                    throw new java.lang.IllegalArgumentException("Bad mode: " + str);
            }
        }
        if (str.startsWith("rw")) {
            i = android.system.OsConstants.O_RDWR | android.system.OsConstants.O_CREAT;
        } else if (str.startsWith("w")) {
            i = android.system.OsConstants.O_WRONLY | android.system.OsConstants.O_CREAT;
        } else if (str.startsWith("r")) {
            i = android.system.OsConstants.O_RDONLY;
        } else {
            throw new java.lang.IllegalArgumentException("Bad mode: " + str);
        }
        if (str.indexOf(116) != -1) {
            i |= android.system.OsConstants.O_TRUNC;
        }
        if (str.indexOf(97) != -1) {
            return i | android.system.OsConstants.O_APPEND;
        }
        return i;
    }

    public static java.lang.String translateModePosixToString(int i) {
        java.lang.String str;
        if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_RDWR) {
            str = "rw";
        } else if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_WRONLY) {
            str = "w";
        } else if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_RDONLY) {
            str = "r";
        } else {
            throw new java.lang.IllegalArgumentException("Bad mode: " + i);
        }
        if ((android.system.OsConstants.O_TRUNC & i) == android.system.OsConstants.O_TRUNC) {
            str = str + "t";
        }
        if ((i & android.system.OsConstants.O_APPEND) == android.system.OsConstants.O_APPEND) {
            return str + android.app.backup.FullBackup.APK_TREE_TOKEN;
        }
        return str;
    }

    public static int translateModePosixToPfd(int i) {
        int i2;
        if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_RDWR) {
            i2 = 805306368;
        } else if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_WRONLY) {
            i2 = 536870912;
        } else if ((android.system.OsConstants.O_ACCMODE & i) == android.system.OsConstants.O_RDONLY) {
            i2 = 268435456;
        } else {
            throw new java.lang.IllegalArgumentException("Bad mode: " + i);
        }
        if ((android.system.OsConstants.O_CREAT & i) == android.system.OsConstants.O_CREAT) {
            i2 |= 134217728;
        }
        if ((android.system.OsConstants.O_TRUNC & i) == android.system.OsConstants.O_TRUNC) {
            i2 |= 67108864;
        }
        if ((i & android.system.OsConstants.O_APPEND) == android.system.OsConstants.O_APPEND) {
            return i2 | 33554432;
        }
        return i2;
    }

    public static int translateModePfdToPosix(int i) {
        int i2;
        if ((i & 805306368) == 805306368) {
            i2 = android.system.OsConstants.O_RDWR;
        } else if ((i & 536870912) == 536870912) {
            i2 = android.system.OsConstants.O_WRONLY;
        } else if ((i & 268435456) == 268435456) {
            i2 = android.system.OsConstants.O_RDONLY;
        } else {
            throw new java.lang.IllegalArgumentException("Bad mode: " + i);
        }
        if ((i & 134217728) == 134217728) {
            i2 |= android.system.OsConstants.O_CREAT;
        }
        if ((i & 67108864) == 67108864) {
            i2 |= android.system.OsConstants.O_TRUNC;
        }
        if ((i & 33554432) == 33554432) {
            return i2 | android.system.OsConstants.O_APPEND;
        }
        return i2;
    }

    public static int translateModeAccessToPosix(int i) {
        if (i == android.system.OsConstants.F_OK) {
            return android.system.OsConstants.O_RDONLY;
        }
        if (((android.system.OsConstants.R_OK | android.system.OsConstants.W_OK) & i) == (android.system.OsConstants.R_OK | android.system.OsConstants.W_OK)) {
            return android.system.OsConstants.O_RDWR;
        }
        if ((android.system.OsConstants.R_OK & i) == android.system.OsConstants.R_OK) {
            return android.system.OsConstants.O_RDONLY;
        }
        if ((android.system.OsConstants.W_OK & i) == android.system.OsConstants.W_OK) {
            return android.system.OsConstants.O_WRONLY;
        }
        throw new java.lang.IllegalArgumentException("Bad mode: " + i);
    }

    public static android.os.ParcelFileDescriptor convertToModernFd(java.io.FileDescriptor fileDescriptor) {
        android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
        if (android.os.UserHandle.getAppId(android.os.Process.myUid()) == getMediaProviderAppId(initialApplication)) {
            return null;
        }
        try {
            android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(fileDescriptor);
            try {
                android.os.ParcelFileDescriptor originalMediaFormatFileDescriptor = android.provider.MediaStore.getOriginalMediaFormatFileDescriptor(initialApplication, dup);
                if (dup != null) {
                    dup.close();
                }
                return originalMediaFormatFileDescriptor;
            } finally {
            }
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private static int getMediaProviderAppId(android.content.Context context) {
        if (sMediaProviderAppId != -1) {
            return sMediaProviderAppId;
        }
        context.getPackageManager();
        android.content.pm.ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider("media", android.os.BatteryStats.HistoryItem.MOST_INTERESTING_STATES);
        if (resolveContentProvider == null) {
            return -1;
        }
        sMediaProviderAppId = android.os.UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
        return sMediaProviderAppId;
    }

    public static class MemoryPipe extends java.lang.Thread implements java.lang.AutoCloseable {
        private final byte[] data;
        private final java.io.FileDescriptor[] pipe;
        private final boolean sink;

        private MemoryPipe(byte[] bArr, boolean z) throws java.io.IOException {
            try {
                this.pipe = android.system.Os.pipe();
                this.data = bArr;
                this.sink = z;
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        private android.os.FileUtils.MemoryPipe startInternal() {
            super.start();
            return this;
        }

        public static android.os.FileUtils.MemoryPipe createSource(byte[] bArr) throws java.io.IOException {
            return new android.os.FileUtils.MemoryPipe(bArr, false).startInternal();
        }

        public static android.os.FileUtils.MemoryPipe createSink(byte[] bArr) throws java.io.IOException {
            return new android.os.FileUtils.MemoryPipe(bArr, true).startInternal();
        }

        public java.io.FileDescriptor getFD() {
            return this.sink ? this.pipe[1] : this.pipe[0];
        }

        public java.io.FileDescriptor getInternalFD() {
            return this.sink ? this.pipe[0] : this.pipe[1];
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
        
            if (r6.sink != false) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x004d, code lost:
        
            libcore.io.IoUtils.closeQuietly(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0044, code lost:
        
            android.os.SystemClock.sleep(java.util.concurrent.TimeUnit.SECONDS.toMillis(1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0042, code lost:
        
            if (r6.sink == false) goto L24;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            java.io.FileDescriptor internalFD = getInternalFD();
            int i = 0;
            while (i < this.data.length) {
                try {
                    if (this.sink) {
                        i += android.system.Os.read(internalFD, this.data, i, this.data.length - i);
                    } else {
                        i += android.system.Os.write(internalFD, this.data, i, this.data.length - i);
                    }
                } catch (android.system.ErrnoException | java.io.IOException e) {
                } catch (java.lang.Throwable th) {
                    if (this.sink) {
                        android.os.SystemClock.sleep(java.util.concurrent.TimeUnit.SECONDS.toMillis(1L));
                    }
                    libcore.io.IoUtils.closeQuietly(internalFD);
                    throw th;
                }
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.lang.Exception {
            libcore.io.IoUtils.closeQuietly(getFD());
        }
    }
}
