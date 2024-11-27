package com.android.server.people.data;

/* loaded from: classes2.dex */
abstract class AbstractProtoDiskReadWriter<T> {
    private static final long DEFAULT_DISK_WRITE_DELAY = 120000;
    private static final long SHUTDOWN_DISK_WRITE_TIMEOUT = 5000;
    private static final java.lang.String TAG = com.android.server.people.data.AbstractProtoDiskReadWriter.class.getSimpleName();
    private final java.io.File mRootDir;
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.Map<java.lang.String, T> mScheduledFileDataMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.util.concurrent.ScheduledFuture<?> mScheduledFuture;

    interface ProtoStreamReader<T> {
        @android.annotation.Nullable
        T read(@android.annotation.NonNull android.util.proto.ProtoInputStream protoInputStream);
    }

    interface ProtoStreamWriter<T> {
        void write(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.NonNull T t);
    }

    abstract com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader<T> protoStreamReader();

    abstract com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter<T> protoStreamWriter();

    AbstractProtoDiskReadWriter(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this.mRootDir = file;
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    void delete(@android.annotation.NonNull java.lang.String str) {
        synchronized (this) {
            this.mScheduledFileDataMap.remove(str);
        }
        java.io.File file = getFile(str);
        if (file.exists() && !file.delete()) {
            android.util.Slog.e(TAG, "Failed to delete file: " + file.getPath());
        }
    }

    void writeTo(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull T t) {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(getFile(str));
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(startWrite);
                protoStreamWriter().write(protoOutputStream, t);
                protoOutputStream.flush();
                atomicFile.finishWrite(startWrite);
                atomicFile.failWrite(null);
            } catch (java.lang.Throwable th) {
                atomicFile.failWrite(startWrite);
                throw th;
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write to protobuf file.", e);
        }
    }

    @android.annotation.Nullable
    T read(@android.annotation.NonNull final java.lang.String str) {
        java.io.File[] listFiles = this.mRootDir.listFiles(new java.io.FileFilter() { // from class: com.android.server.people.data.AbstractProtoDiskReadWriter$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(java.io.File file) {
                boolean lambda$read$0;
                lambda$read$0 = com.android.server.people.data.AbstractProtoDiskReadWriter.lambda$read$0(str, file);
                return lambda$read$0;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        if (listFiles.length > 1) {
            android.util.Slog.w(TAG, "Found multiple files with the same name: " + java.util.Arrays.toString(listFiles));
        }
        return parseFile(listFiles[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$read$0(java.lang.String str, java.io.File file) {
        return file.isFile() && file.getName().equals(str);
    }

    synchronized void scheduleSave(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull T t) {
        this.mScheduledFileDataMap.put(str, t);
        if (this.mScheduledExecutorService.isShutdown()) {
            android.util.Slog.e(TAG, "Worker is shutdown, failed to schedule data saving.");
        } else {
            if (this.mScheduledFuture != null) {
                return;
            }
            this.mScheduledFuture = this.mScheduledExecutorService.schedule(new com.android.server.people.data.AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0(this), 120000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        }
    }

    void saveImmediately(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull T t) {
        synchronized (this) {
            this.mScheduledFileDataMap.put(str, t);
        }
        triggerScheduledFlushEarly();
    }

    private void triggerScheduledFlushEarly() {
        synchronized (this) {
            try {
                if (this.mScheduledFileDataMap.isEmpty() || this.mScheduledExecutorService.isShutdown()) {
                    return;
                }
                if (this.mScheduledFuture != null) {
                    this.mScheduledFuture.cancel(true);
                }
                try {
                    this.mScheduledExecutorService.submit(new com.android.server.people.data.AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0(this)).get(SHUTDOWN_DISK_WRITE_TIMEOUT, java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
                    android.util.Slog.e(TAG, "Failed to save data immediately.", e);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void flushScheduledData() {
        if (this.mScheduledFileDataMap.isEmpty()) {
            this.mScheduledFuture = null;
            return;
        }
        for (java.lang.String str : this.mScheduledFileDataMap.keySet()) {
            writeTo(str, this.mScheduledFileDataMap.get(str));
        }
        this.mScheduledFileDataMap.clear();
        this.mScheduledFuture = null;
    }

    @android.annotation.Nullable
    private T parseFile(@android.annotation.NonNull java.io.File file) {
        try {
            java.io.FileInputStream openRead = new android.util.AtomicFile(file).openRead();
            try {
                T read = protoStreamReader().read(new android.util.proto.ProtoInputStream(openRead));
                if (openRead != null) {
                    openRead.close();
                }
                return read;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to parse protobuf file.", e);
            return null;
        }
    }

    @android.annotation.NonNull
    private java.io.File getFile(java.lang.String str) {
        return new java.io.File(this.mRootDir, str);
    }
}
