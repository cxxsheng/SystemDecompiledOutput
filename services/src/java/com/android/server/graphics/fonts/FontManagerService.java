package com.android.server.graphics.fonts;

/* loaded from: classes.dex */
public final class FontManagerService extends com.android.internal.graphics.fonts.IFontManager.Stub {
    private static final java.lang.String CONFIG_XML_FILE = "/data/fonts/config/config.xml";
    private static final java.lang.String FONT_FILES_DIR = "/data/fonts/files";
    private static final java.lang.String TAG = "FontManagerService";

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private java.lang.String mDebugCertFilePath;
    private final boolean mIsSafeMode;

    @com.android.internal.annotations.GuardedBy({"mSerializedFontMapLock"})
    @android.annotation.Nullable
    private android.os.SharedMemory mSerializedFontMap;
    private final java.lang.Object mSerializedFontMapLock;

    @com.android.internal.annotations.GuardedBy({"mUpdatableFontDirLock"})
    @android.annotation.Nullable
    private com.android.server.graphics.fonts.UpdatableFontDir mUpdatableFontDir;
    private final java.lang.Object mUpdatableFontDirLock;

    @android.annotation.EnforcePermission("android.permission.UPDATE_FONTS")
    @android.annotation.RequiresPermission("android.permission.UPDATE_FONTS")
    public android.text.FontConfig getFontConfig() {
        super.getFontConfig_enforcePermission();
        return getSystemFontConfig();
    }

    @android.annotation.RequiresPermission("android.permission.UPDATE_FONTS")
    public int updateFontFamily(@android.annotation.NonNull java.util.List<android.graphics.fonts.FontUpdateRequest> list, int i) {
        try {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
            java.util.Objects.requireNonNull(list);
            getContext().enforceCallingPermission("android.permission.UPDATE_FONTS", "UPDATE_FONTS permission required.");
            try {
                update(i, list);
                closeFileDescriptors(list);
                return 0;
            } catch (com.android.server.graphics.fonts.FontManagerService.SystemFontException e) {
                android.util.Slog.e(TAG, "Failed to update font family", e);
                int errorCode = e.getErrorCode();
                closeFileDescriptors(list);
                return errorCode;
            }
        } catch (java.lang.Throwable th) {
            closeFileDescriptors(list);
            throw th;
        }
    }

    private static void closeFileDescriptors(@android.annotation.Nullable java.util.List<android.graphics.fonts.FontUpdateRequest> list) {
        android.os.ParcelFileDescriptor fd;
        if (list == null) {
            return;
        }
        for (android.graphics.fonts.FontUpdateRequest fontUpdateRequest : list) {
            if (fontUpdateRequest != null && (fd = fontUpdateRequest.getFd()) != null) {
                try {
                    fd.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Failed to close fd", e);
                }
            }
        }
    }

    static class SystemFontException extends android.util.AndroidException {
        private final int mErrorCode;

        SystemFontException(int i, java.lang.String str, java.lang.Throwable th) {
            super(str, th);
            this.mErrorCode = i;
        }

        SystemFontException(int i, java.lang.String str) {
            super(str);
            this.mErrorCode = i;
        }

        int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.graphics.fonts.FontManagerService mService;
        private final java.util.concurrent.CompletableFuture<java.lang.Void> mServiceStarted;

        public Lifecycle(@android.annotation.NonNull android.content.Context context, boolean z) {
            super(context);
            this.mServiceStarted = new java.util.concurrent.CompletableFuture<>();
            this.mService = new com.android.server.graphics.fonts.FontManagerService(context, z, this.mServiceStarted);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.server.LocalServices.addService(com.android.server.graphics.fonts.FontManagerInternal.class, new com.android.server.graphics.fonts.FontManagerInternal() { // from class: com.android.server.graphics.fonts.FontManagerService.Lifecycle.1
                @Override // com.android.server.graphics.fonts.FontManagerInternal
                @android.annotation.Nullable
                public android.os.SharedMemory getSerializedSystemFontMap() {
                    com.android.server.graphics.fonts.FontManagerService.Lifecycle.this.mServiceStarted.join();
                    return com.android.server.graphics.fonts.FontManagerService.Lifecycle.this.mService.getCurrentFontMap();
                }
            });
            publishBinderService("font", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mServiceStarted.join();
            }
        }
    }

    private static class FsverityUtilImpl implements com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil {
        private final java.lang.String[] mDerCertPaths;

        FsverityUtilImpl(java.lang.String[] strArr) {
            this.mDerCertPaths = strArr;
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public boolean isFromTrustedProvider(java.lang.String str, byte[] bArr) {
            java.io.FileInputStream fileInputStream;
            byte[] fsverityDigest = com.android.internal.security.VerityUtils.getFsverityDigest(str);
            if (fsverityDigest == null) {
                android.util.Log.w(com.android.server.graphics.fonts.FontManagerService.TAG, "Failed to get fs-verity digest for " + str);
                return false;
            }
            for (java.lang.String str2 : this.mDerCertPaths) {
                try {
                    fileInputStream = new java.io.FileInputStream(str2);
                    try {
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    android.util.Log.w(com.android.server.graphics.fonts.FontManagerService.TAG, "Failed to read certificate file: " + str2);
                }
                if (!com.android.internal.security.VerityUtils.verifyPkcs7DetachedSignature(bArr, fsverityDigest, fileInputStream)) {
                    fileInputStream.close();
                } else {
                    fileInputStream.close();
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public void setUpFsverity(java.lang.String str) throws java.io.IOException {
            com.android.internal.security.VerityUtils.setUpFsverity(str);
        }

        @Override // com.android.server.graphics.fonts.UpdatableFontDir.FsverityUtil
        public boolean rename(java.io.File file, java.io.File file2) {
            return file.renameTo(file2);
        }
    }

    private FontManagerService(android.content.Context context, boolean z, final java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture) {
        this.mUpdatableFontDirLock = new java.lang.Object();
        this.mDebugCertFilePath = null;
        this.mSerializedFontMapLock = new java.lang.Object();
        this.mSerializedFontMap = null;
        if (z) {
            android.util.Slog.i(TAG, "Entering safe mode. Deleting all font updates.");
            com.android.server.graphics.fonts.UpdatableFontDir.deleteAllFiles(new java.io.File(FONT_FILES_DIR), new java.io.File(CONFIG_XML_FILE));
        }
        this.mContext = context;
        this.mIsSafeMode = z;
        if (com.android.text.flags.Flags.useOptimizedBoottimeFontLoading()) {
            android.util.Slog.i(TAG, "Using optimized boot-time font loading.");
            com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.graphics.fonts.FontManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.graphics.fonts.FontManagerService.this.lambda$new$0(completableFuture);
                }
            }, "FontManagerService_create");
        } else {
            android.util.Slog.i(TAG, "Not using optimized boot-time font loading.");
            initialize();
            setSystemFontMap();
            completableFuture.complete(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.util.concurrent.CompletableFuture completableFuture) {
        initialize();
        synchronized (this.mUpdatableFontDirLock) {
            try {
                if (this.mUpdatableFontDir != null) {
                    setSystemFontMap();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        completableFuture.complete(null);
    }

    private void setSystemFontMap() {
        try {
            android.graphics.Typeface.setSystemFontMap(getCurrentFontMap());
        } catch (android.system.ErrnoException | java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to set system font map of system_server");
        }
    }

    @android.annotation.Nullable
    private com.android.server.graphics.fonts.UpdatableFontDir createUpdatableFontDir() {
        if (this.mIsSafeMode || !com.android.internal.security.VerityUtils.isFsVeritySupported()) {
            return null;
        }
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_fillBuiltInDisplayCutoutArray);
        if (this.mDebugCertFilePath != null && android.os.Build.IS_DEBUGGABLE) {
            java.lang.String[] strArr = new java.lang.String[stringArray.length + 1];
            java.lang.System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
            strArr[stringArray.length] = this.mDebugCertFilePath;
            stringArray = strArr;
        }
        return new com.android.server.graphics.fonts.UpdatableFontDir(new java.io.File(FONT_FILES_DIR), new com.android.server.graphics.fonts.OtfFontFileParser(), new com.android.server.graphics.fonts.FontManagerService.FsverityUtilImpl(stringArray), new java.io.File(CONFIG_XML_FILE));
    }

    public void addDebugCertificate(@android.annotation.Nullable java.lang.String str) {
        this.mDebugCertFilePath = str;
    }

    private void initialize() {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                this.mUpdatableFontDir = createUpdatableFontDir();
                if (this.mUpdatableFontDir == null) {
                    if (com.android.text.flags.Flags.useOptimizedBoottimeFontLoading()) {
                        android.graphics.Typeface.loadPreinstalledSystemFontMap();
                    }
                    setSerializedFontMap(serializeSystemServerFontMap());
                } else {
                    this.mUpdatableFontDir.loadFontFileMap();
                    updateSerializedFontMap();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public android.content.Context getContext() {
        return this.mContext;
    }

    @android.annotation.Nullable
    android.os.SharedMemory getCurrentFontMap() {
        android.os.SharedMemory sharedMemory;
        synchronized (this.mSerializedFontMapLock) {
            sharedMemory = this.mSerializedFontMap;
        }
        return sharedMemory;
    }

    void update(int i, java.util.List<android.graphics.fonts.FontUpdateRequest> list) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                if (this.mUpdatableFontDir == null) {
                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-7, "The font updater is disabled.");
                }
                if (i != -1 && this.mUpdatableFontDir.getConfigVersion() != i) {
                    throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-8, "The base config version is older than current.");
                }
                this.mUpdatableFontDir.update(list);
                updateSerializedFontMap();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearUpdates() {
        com.android.server.graphics.fonts.UpdatableFontDir.deleteAllFiles(new java.io.File(FONT_FILES_DIR), new java.io.File(CONFIG_XML_FILE));
        initialize();
    }

    void restart() {
        initialize();
    }

    java.util.Map<java.lang.String, java.io.File> getFontFileMap() {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                if (this.mUpdatableFontDir == null) {
                    return java.util.Collections.emptyMap();
                }
                return this.mUpdatableFontDir.getPostScriptMap();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            new com.android.server.graphics.fonts.FontManagerShellCommand(this).dumpAll(new android.util.IndentingPrintWriter(printWriter, "  "));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
        new com.android.server.graphics.fonts.FontManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @android.annotation.NonNull
    public android.text.FontConfig getSystemFontConfig() {
        synchronized (this.mUpdatableFontDirLock) {
            try {
                if (this.mUpdatableFontDir == null) {
                    return android.graphics.fonts.SystemFonts.getSystemPreinstalledFontConfig();
                }
                return this.mUpdatableFontDir.getSystemFontConfig();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateSerializedFontMap() {
        android.os.SharedMemory serializeFontMap = serializeFontMap(getSystemFontConfig());
        if (serializeFontMap == null) {
            serializeFontMap = serializeSystemServerFontMap();
        }
        setSerializedFontMap(serializeFontMap);
    }

    @android.annotation.Nullable
    private static android.os.SharedMemory serializeFontMap(android.text.FontConfig fontConfig) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        try {
            try {
                android.os.SharedMemory serializeFontMap = android.graphics.Typeface.serializeFontMap(android.graphics.fonts.SystemFonts.buildSystemTypefaces(fontConfig, android.graphics.fonts.SystemFonts.buildSystemFallback(fontConfig, arrayMap)));
                for (java.nio.ByteBuffer byteBuffer : arrayMap.values()) {
                    if (byteBuffer instanceof java.nio.DirectByteBuffer) {
                        java.nio.NioUtils.freeDirectBuffer(byteBuffer);
                    }
                }
                return serializeFontMap;
            } catch (android.system.ErrnoException | java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to serialize updatable font map. Retrying with system image fonts.", e);
                for (java.nio.ByteBuffer byteBuffer2 : arrayMap.values()) {
                    if (byteBuffer2 instanceof java.nio.DirectByteBuffer) {
                        java.nio.NioUtils.freeDirectBuffer(byteBuffer2);
                    }
                }
                return null;
            }
        } catch (java.lang.Throwable th) {
            for (java.nio.ByteBuffer byteBuffer3 : arrayMap.values()) {
                if (byteBuffer3 instanceof java.nio.DirectByteBuffer) {
                    java.nio.NioUtils.freeDirectBuffer(byteBuffer3);
                }
            }
            throw th;
        }
    }

    @android.annotation.Nullable
    private static android.os.SharedMemory serializeSystemServerFontMap() {
        try {
            return android.graphics.Typeface.serializeFontMap(android.graphics.Typeface.getSystemFontMap());
        } catch (android.system.ErrnoException | java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to serialize SystemServer system font map", e);
            return null;
        }
    }

    private void setSerializedFontMap(android.os.SharedMemory sharedMemory) {
        android.os.SharedMemory sharedMemory2;
        synchronized (this.mSerializedFontMapLock) {
            sharedMemory2 = this.mSerializedFontMap;
            this.mSerializedFontMap = sharedMemory;
        }
        if (sharedMemory2 != null) {
            sharedMemory2.close();
        }
    }
}
