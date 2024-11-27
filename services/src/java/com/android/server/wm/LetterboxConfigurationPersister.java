package com.android.server.wm;

/* loaded from: classes3.dex */
class LetterboxConfigurationPersister {
    private static final java.lang.String LETTERBOX_CONFIGURATION_FILENAME = "letterbox_config";
    private static final java.lang.String TAG = "WindowManager";

    @android.annotation.Nullable
    private final java.util.function.Consumer<java.lang.String> mCompletionCallback;

    @android.annotation.NonNull
    private final android.util.AtomicFile mConfigurationFile;
    private final java.util.function.Supplier<java.lang.Integer> mDefaultBookModeReachabilitySupplier;
    private final java.util.function.Supplier<java.lang.Integer> mDefaultHorizontalReachabilitySupplier;
    private final java.util.function.Supplier<java.lang.Integer> mDefaultTabletopModeReachabilitySupplier;
    private final java.util.function.Supplier<java.lang.Integer> mDefaultVerticalReachabilitySupplier;
    private volatile int mLetterboxPositionForBookModeReachability;
    private volatile int mLetterboxPositionForHorizontalReachability;
    private volatile int mLetterboxPositionForTabletopModeReachability;
    private volatile int mLetterboxPositionForVerticalReachability;

    @android.annotation.NonNull
    private final com.android.server.wm.PersisterQueue mPersisterQueue;

    LetterboxConfigurationPersister(@android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier2, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier3, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier4) {
        this(supplier, supplier2, supplier3, supplier4, android.os.Environment.getDataSystemDirectory(), new com.android.server.wm.PersisterQueue(), null, LETTERBOX_CONFIGURATION_FILENAME);
    }

    @com.android.internal.annotations.VisibleForTesting
    LetterboxConfigurationPersister(@android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier2, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier3, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier4, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.wm.PersisterQueue persisterQueue, @android.annotation.Nullable java.util.function.Consumer<java.lang.String> consumer, @android.annotation.NonNull java.lang.String str) {
        this.mDefaultHorizontalReachabilitySupplier = supplier;
        this.mDefaultVerticalReachabilitySupplier = supplier2;
        this.mDefaultBookModeReachabilitySupplier = supplier3;
        this.mDefaultTabletopModeReachabilitySupplier = supplier4;
        this.mCompletionCallback = consumer;
        this.mConfigurationFile = new android.util.AtomicFile(new java.io.File(file, str));
        this.mPersisterQueue = persisterQueue;
        runWithDiskReadsThreadPolicy(new java.lang.Runnable() { // from class: com.android.server.wm.LetterboxConfigurationPersister$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.LetterboxConfigurationPersister.this.readCurrentConfiguration();
            }
        });
    }

    void start() {
        this.mPersisterQueue.startPersisting();
    }

    int getLetterboxPositionForHorizontalReachability(boolean z) {
        if (z) {
            return this.mLetterboxPositionForBookModeReachability;
        }
        return this.mLetterboxPositionForHorizontalReachability;
    }

    int getLetterboxPositionForVerticalReachability(boolean z) {
        if (z) {
            return this.mLetterboxPositionForTabletopModeReachability;
        }
        return this.mLetterboxPositionForVerticalReachability;
    }

    void setLetterboxPositionForHorizontalReachability(boolean z, int i) {
        if (z) {
            if (this.mLetterboxPositionForBookModeReachability != i) {
                this.mLetterboxPositionForBookModeReachability = i;
                updateConfiguration();
                return;
            }
            return;
        }
        if (this.mLetterboxPositionForHorizontalReachability != i) {
            this.mLetterboxPositionForHorizontalReachability = i;
            updateConfiguration();
        }
    }

    void setLetterboxPositionForVerticalReachability(boolean z, int i) {
        if (z) {
            if (this.mLetterboxPositionForTabletopModeReachability != i) {
                this.mLetterboxPositionForTabletopModeReachability = i;
                updateConfiguration();
                return;
            }
            return;
        }
        if (this.mLetterboxPositionForVerticalReachability != i) {
            this.mLetterboxPositionForVerticalReachability = i;
            updateConfiguration();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void useDefaultValue() {
        this.mLetterboxPositionForHorizontalReachability = this.mDefaultHorizontalReachabilitySupplier.get().intValue();
        this.mLetterboxPositionForVerticalReachability = this.mDefaultVerticalReachabilitySupplier.get().intValue();
        this.mLetterboxPositionForBookModeReachability = this.mDefaultBookModeReachabilitySupplier.get().intValue();
        this.mLetterboxPositionForTabletopModeReachability = this.mDefaultTabletopModeReachabilitySupplier.get().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0038 -> B:11:0x0051). Please report as a decompilation issue!!! */
    public void readCurrentConfiguration() {
        if (!this.mConfigurationFile.exists()) {
            useDefaultValue();
            return;
        }
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStream = this.mConfigurationFile.openRead();
                    com.android.server.wm.nano.WindowManagerProtos.LetterboxProto parseFrom = com.android.server.wm.nano.WindowManagerProtos.LetterboxProto.parseFrom(readInputStream(fileInputStream));
                    this.mLetterboxPositionForHorizontalReachability = parseFrom.letterboxPositionForHorizontalReachability;
                    this.mLetterboxPositionForVerticalReachability = parseFrom.letterboxPositionForVerticalReachability;
                    this.mLetterboxPositionForBookModeReachability = parseFrom.letterboxPositionForBookModeReachability;
                    this.mLetterboxPositionForTabletopModeReachability = parseFrom.letterboxPositionForTabletopModeReachability;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (java.io.IOException e) {
                            useDefaultValue();
                            android.util.Slog.e(TAG, "Error reading from LetterboxConfigurationPersister ", e);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Error reading from LetterboxConfigurationPersister. Using default values!", e2);
                useDefaultValue();
                if (fileInputStream == null) {
                } else {
                    fileInputStream.close();
                }
            }
        } catch (java.io.IOException e3) {
            useDefaultValue();
            android.util.Slog.e(TAG, "Error reading from LetterboxConfigurationPersister ", e3);
        }
    }

    private void updateConfiguration() {
        this.mPersisterQueue.addItem(new com.android.server.wm.LetterboxConfigurationPersister.UpdateValuesCommand(this.mConfigurationFile, this.mLetterboxPositionForHorizontalReachability, this.mLetterboxPositionForVerticalReachability, this.mLetterboxPositionForBookModeReachability, this.mLetterboxPositionForTabletopModeReachability, this.mCompletionCallback), true);
    }

    private static byte[] readInputStream(java.io.InputStream inputStream) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[1024];
            int read = inputStream.read(bArr);
            while (read > 0) {
                byteArrayOutputStream.write(bArr, 0, read);
                read = inputStream.read(bArr);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (java.lang.Throwable th) {
            byteArrayOutputStream.close();
            throw th;
        }
    }

    private void runWithDiskReadsThreadPolicy(java.lang.Runnable runnable) {
        android.os.StrictMode.ThreadPolicy threadPolicy = android.os.StrictMode.getThreadPolicy();
        android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().permitDiskReads().build());
        runnable.run();
        android.os.StrictMode.setThreadPolicy(threadPolicy);
    }

    private static class UpdateValuesCommand implements com.android.server.wm.PersisterQueue.WriteQueueItem<com.android.server.wm.LetterboxConfigurationPersister.UpdateValuesCommand> {
        private final int mBookModeReachability;

        @android.annotation.NonNull
        private final android.util.AtomicFile mFileToUpdate;
        private final int mHorizontalReachability;

        @android.annotation.Nullable
        private final java.util.function.Consumer<java.lang.String> mOnComplete;
        private final int mTabletopModeReachability;
        private final int mVerticalReachability;

        UpdateValuesCommand(@android.annotation.NonNull android.util.AtomicFile atomicFile, int i, int i2, int i3, int i4, @android.annotation.Nullable java.util.function.Consumer<java.lang.String> consumer) {
            this.mFileToUpdate = atomicFile;
            this.mHorizontalReachability = i;
            this.mVerticalReachability = i2;
            this.mBookModeReachability = i3;
            this.mTabletopModeReachability = i4;
            this.mOnComplete = consumer;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public void process() {
            com.android.server.wm.nano.WindowManagerProtos.LetterboxProto letterboxProto = new com.android.server.wm.nano.WindowManagerProtos.LetterboxProto();
            letterboxProto.letterboxPositionForHorizontalReachability = this.mHorizontalReachability;
            letterboxProto.letterboxPositionForVerticalReachability = this.mVerticalReachability;
            letterboxProto.letterboxPositionForBookModeReachability = this.mBookModeReachability;
            letterboxProto.letterboxPositionForTabletopModeReachability = this.mTabletopModeReachability;
            byte[] byteArray = com.android.server.wm.nano.WindowManagerProtos.LetterboxProto.toByteArray(letterboxProto);
            java.io.FileOutputStream fileOutputStream = null;
            try {
                try {
                    fileOutputStream = this.mFileToUpdate.startWrite();
                    fileOutputStream.write(byteArray);
                    this.mFileToUpdate.finishWrite(fileOutputStream);
                    if (this.mOnComplete == null) {
                        return;
                    }
                } catch (java.io.IOException e) {
                    this.mFileToUpdate.failWrite(fileOutputStream);
                    android.util.Slog.e(com.android.server.wm.LetterboxConfigurationPersister.TAG, "Error writing to LetterboxConfigurationPersister. Using default values!", e);
                    if (this.mOnComplete == null) {
                        return;
                    }
                }
                this.mOnComplete.accept("UpdateValuesCommand");
            } catch (java.lang.Throwable th) {
                if (this.mOnComplete != null) {
                    this.mOnComplete.accept("UpdateValuesCommand");
                }
                throw th;
            }
        }
    }
}
