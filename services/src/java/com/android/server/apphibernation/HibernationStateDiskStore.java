package com.android.server.apphibernation;

/* loaded from: classes.dex */
class HibernationStateDiskStore<T> {
    private static final long DISK_WRITE_DELAY = 60000;
    private static final java.lang.String STATES_FILE_NAME = "states";
    private static final java.lang.String TAG = "HibernationStateDiskStore";
    private final java.util.concurrent.ScheduledExecutorService mExecutorService;
    private java.util.concurrent.ScheduledFuture<?> mFuture;
    private final java.io.File mHibernationFile;
    private final com.android.server.apphibernation.ProtoReadWriter<java.util.List<T>> mProtoReadWriter;
    private java.util.List<T> mScheduledStatesToWrite;

    HibernationStateDiskStore(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.apphibernation.ProtoReadWriter<java.util.List<T>> protoReadWriter, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this(file, protoReadWriter, scheduledExecutorService, STATES_FILE_NAME);
    }

    @com.android.internal.annotations.VisibleForTesting
    HibernationStateDiskStore(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.apphibernation.ProtoReadWriter<java.util.List<T>> protoReadWriter, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, @android.annotation.NonNull java.lang.String str) {
        this.mScheduledStatesToWrite = new java.util.ArrayList();
        this.mHibernationFile = new java.io.File(file, str);
        this.mExecutorService = scheduledExecutorService;
        this.mProtoReadWriter = protoReadWriter;
    }

    void scheduleWriteHibernationStates(@android.annotation.NonNull java.util.List<T> list) {
        synchronized (this) {
            try {
                this.mScheduledStatesToWrite = list;
                if (this.mExecutorService.isShutdown()) {
                    android.util.Slog.e(TAG, "Scheduled executor service is shut down.");
                } else if (this.mFuture != null) {
                    android.util.Slog.i(TAG, "Write already scheduled. Skipping schedule.");
                } else {
                    this.mFuture = this.mExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.apphibernation.HibernationStateDiskStore$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.apphibernation.HibernationStateDiskStore.this.writeHibernationStates();
                        }
                    }, 60000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    java.util.List<T> readHibernationStates() {
        synchronized (this) {
            try {
                if (!this.mHibernationFile.exists()) {
                    android.util.Slog.i(TAG, "No hibernation file on disk for file " + this.mHibernationFile.getPath());
                    return null;
                }
                try {
                    return this.mProtoReadWriter.readFromProto(new android.util.proto.ProtoInputStream(new android.util.AtomicFile(this.mHibernationFile).openRead()));
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Failed to read states protobuf.", e);
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeHibernationStates() {
        synchronized (this) {
            writeStateProto(this.mScheduledStatesToWrite);
            this.mScheduledStatesToWrite.clear();
            this.mFuture = null;
        }
    }

    private void writeStateProto(java.util.List<T> list) {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mHibernationFile);
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(startWrite);
                this.mProtoReadWriter.writeToProto(protoOutputStream, list);
                protoOutputStream.flush();
                atomicFile.finishWrite(startWrite);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to finish write to states protobuf.", e);
                atomicFile.failWrite(startWrite);
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to start write to states protobuf.", e2);
        }
    }
}
