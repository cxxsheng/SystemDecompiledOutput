package com.android.internal.content;

/* loaded from: classes4.dex */
public class NativeLibraryHelper {
    private static final int BITCODE_PRESENT = 1;
    public static final java.lang.String CLEAR_ABI_OVERRIDE = "-";
    private static final boolean DEBUG_NATIVE = false;
    public static final java.lang.String LIB64_DIR_NAME = "lib64";
    public static final java.lang.String LIB_DIR_NAME = "lib";
    private static final java.lang.String TAG = "NativeHelper";

    private static native int hasRenderscriptBitcode(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClose(long j);

    private static native int nativeCopyNativeBinaries(long j, java.lang.String str, java.lang.String str2, boolean z, boolean z2);

    private static native int nativeFindSupportedAbi(long j, java.lang.String[] strArr, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenApk(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenApkFd(java.io.FileDescriptor fileDescriptor, java.lang.String str);

    private static native long nativeSumNativeBinaries(long j, java.lang.String str, boolean z);

    public static class Handle implements java.io.Closeable {
        final long[] apkHandles;
        final java.lang.String[] apkPaths;
        final boolean debuggable;
        final boolean extractNativeLibs;
        private volatile boolean mClosed;
        private final dalvik.system.CloseGuard mGuard = dalvik.system.CloseGuard.get();
        final boolean multiArch;

        public static com.android.internal.content.NativeLibraryHelper.Handle create(java.io.File file) throws java.io.IOException {
            android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), file, 0);
            if (parsePackageLite.isError()) {
                throw new java.io.IOException("Failed to parse package: " + file, parsePackageLite.getException());
            }
            return create(parsePackageLite.getResult());
        }

        public static com.android.internal.content.NativeLibraryHelper.Handle create(android.content.pm.parsing.PackageLite packageLite) throws java.io.IOException {
            return create(packageLite.getAllApkPaths(), packageLite.isMultiArch(), packageLite.isExtractNativeLibs(), packageLite.isDebuggable());
        }

        public static com.android.internal.content.NativeLibraryHelper.Handle create(java.util.List<java.lang.String> list, boolean z, boolean z2, boolean z3) throws java.io.IOException {
            int size = list.size();
            java.lang.String[] strArr = new java.lang.String[size];
            long[] jArr = new long[size];
            for (int i = 0; i < size; i++) {
                java.lang.String str = list.get(i);
                strArr[i] = str;
                jArr[i] = com.android.internal.content.NativeLibraryHelper.nativeOpenApk(str);
                if (jArr[i] == 0) {
                    for (int i2 = 0; i2 < i; i2++) {
                        com.android.internal.content.NativeLibraryHelper.nativeClose(jArr[i2]);
                    }
                    throw new java.io.IOException("Unable to open APK: " + str);
                }
            }
            return new com.android.internal.content.NativeLibraryHelper.Handle(strArr, jArr, z, z2, z3);
        }

        public static com.android.internal.content.NativeLibraryHelper.Handle createFd(android.content.pm.parsing.PackageLite packageLite, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
            java.lang.String baseApkPath = packageLite.getBaseApkPath();
            long[] jArr = {com.android.internal.content.NativeLibraryHelper.nativeOpenApkFd(fileDescriptor, baseApkPath)};
            if (jArr[0] == 0) {
                throw new java.io.IOException("Unable to open APK " + baseApkPath + " from fd " + fileDescriptor);
            }
            return new com.android.internal.content.NativeLibraryHelper.Handle(new java.lang.String[]{baseApkPath}, jArr, packageLite.isMultiArch(), packageLite.isExtractNativeLibs(), packageLite.isDebuggable());
        }

        Handle(java.lang.String[] strArr, long[] jArr, boolean z, boolean z2, boolean z3) {
            this.apkPaths = strArr;
            this.apkHandles = jArr;
            this.multiArch = z;
            this.extractNativeLibs = z2;
            this.debuggable = z3;
            this.mGuard.open("close");
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            for (long j : this.apkHandles) {
                com.android.internal.content.NativeLibraryHelper.nativeClose(j);
            }
            this.mGuard.close();
            this.mClosed = true;
        }

        protected void finalize() throws java.lang.Throwable {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
            }
            try {
                if (!this.mClosed) {
                    close();
                }
            } finally {
                super.finalize();
            }
        }
    }

    private static long sumNativeBinaries(com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String str) {
        long j = 0;
        for (long j2 : handle.apkHandles) {
            j += nativeSumNativeBinaries(j2, str, handle.debuggable);
        }
        return j;
    }

    public static int copyNativeBinaries(com.android.internal.content.NativeLibraryHelper.Handle handle, java.io.File file, java.lang.String str) {
        for (long j : handle.apkHandles) {
            int nativeCopyNativeBinaries = nativeCopyNativeBinaries(j, file.getPath(), str, handle.extractNativeLibs, handle.debuggable);
            if (nativeCopyNativeBinaries != 1) {
                return nativeCopyNativeBinaries;
            }
        }
        return 1;
    }

    public static int findSupportedAbi(com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String[] strArr) {
        int i = -114;
        for (long j : handle.apkHandles) {
            int nativeFindSupportedAbi = nativeFindSupportedAbi(j, strArr, handle.debuggable);
            if (nativeFindSupportedAbi != -114) {
                if (nativeFindSupportedAbi == -113) {
                    if (i < 0) {
                        i = -113;
                    }
                } else if (nativeFindSupportedAbi >= 0) {
                    if (i < 0 || nativeFindSupportedAbi < i) {
                        i = nativeFindSupportedAbi;
                    }
                } else {
                    return nativeFindSupportedAbi;
                }
            }
        }
        return i;
    }

    public static void removeNativeBinariesLI(java.lang.String str) {
        if (str == null) {
            return;
        }
        removeNativeBinariesFromDirLI(new java.io.File(str), false);
    }

    public static void removeNativeBinariesFromDirLI(java.io.File file, boolean z) {
        if (file.exists()) {
            java.io.File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isDirectory()) {
                        removeNativeBinariesFromDirLI(listFiles[i], true);
                    } else if (!listFiles[i].delete()) {
                        android.util.Slog.w(TAG, "Could not delete native binary: " + listFiles[i].getPath());
                    }
                }
            }
            if (z && !file.delete()) {
                android.util.Slog.w(TAG, "Could not delete native binary directory: " + file.getPath());
            }
        }
    }

    public static void createNativeLibrarySubdir(java.io.File file) throws java.io.IOException {
        if (!file.isDirectory()) {
            file.delete();
            if (!file.mkdir()) {
                throw new java.io.IOException("Cannot create " + file.getPath());
            }
            try {
                android.system.Os.chmod(file.getPath(), android.system.OsConstants.S_IRWXU | android.system.OsConstants.S_IRGRP | android.system.OsConstants.S_IXGRP | android.system.OsConstants.S_IROTH | android.system.OsConstants.S_IXOTH);
                return;
            } catch (android.system.ErrnoException e) {
                throw new java.io.IOException("Cannot chmod native library directory " + file.getPath(), e);
            }
        }
        if (!android.os.SELinux.restorecon(file)) {
            throw new java.io.IOException("Cannot set SELinux context for " + file.getPath());
        }
    }

    private static long sumNativeBinariesForSupportedAbi(com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String[] strArr) {
        int findSupportedAbi = findSupportedAbi(handle, strArr);
        if (findSupportedAbi >= 0) {
            return sumNativeBinaries(handle, strArr[findSupportedAbi]);
        }
        return 0L;
    }

    public static int copyNativeBinariesForSupportedAbi(com.android.internal.content.NativeLibraryHelper.Handle handle, java.io.File file, java.lang.String[] strArr, boolean z, boolean z2) throws java.io.IOException {
        java.io.File file2;
        int findSupportedAbi = findSupportedAbi(handle, strArr);
        if (findSupportedAbi < 0) {
            return findSupportedAbi;
        }
        java.lang.String str = strArr[findSupportedAbi];
        java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(str);
        if (z) {
            file2 = new java.io.File(file, instructionSet);
        } else {
            file2 = file;
        }
        if (z2) {
            int incrementalConfigureNativeBinariesForSupportedAbi = incrementalConfigureNativeBinariesForSupportedAbi(handle, file2, str);
            if (incrementalConfigureNativeBinariesForSupportedAbi != 1) {
                return incrementalConfigureNativeBinariesForSupportedAbi;
            }
            return findSupportedAbi;
        }
        createNativeLibrarySubdir(file);
        if (file2 != file) {
            createNativeLibrarySubdir(file2);
        }
        int copyNativeBinaries = copyNativeBinaries(handle, file2, str);
        if (copyNativeBinaries != 1) {
            return copyNativeBinaries;
        }
        return findSupportedAbi;
    }

    public static int copyNativeBinariesWithOverride(com.android.internal.content.NativeLibraryHelper.Handle handle, java.io.File file, java.lang.String str, boolean z) {
        int copyNativeBinariesForSupportedAbi;
        int copyNativeBinariesForSupportedAbi2;
        try {
            if (handle.multiArch) {
                if (str != null && !CLEAR_ABI_OVERRIDE.equals(str)) {
                    android.util.Slog.w(TAG, "Ignoring abiOverride for multi arch application.");
                }
                if (android.os.Build.SUPPORTED_32_BIT_ABIS.length > 0 && (copyNativeBinariesForSupportedAbi2 = copyNativeBinariesForSupportedAbi(handle, file, android.os.Build.SUPPORTED_32_BIT_ABIS, true, z)) < 0 && copyNativeBinariesForSupportedAbi2 != -114 && copyNativeBinariesForSupportedAbi2 != -113) {
                    android.util.Slog.w(TAG, "Failure copying 32 bit native libraries; copyRet=" + copyNativeBinariesForSupportedAbi2);
                    return copyNativeBinariesForSupportedAbi2;
                }
                if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0 && (copyNativeBinariesForSupportedAbi = copyNativeBinariesForSupportedAbi(handle, file, android.os.Build.SUPPORTED_64_BIT_ABIS, true, z)) < 0 && copyNativeBinariesForSupportedAbi != -114 && copyNativeBinariesForSupportedAbi != -113) {
                    android.util.Slog.w(TAG, "Failure copying 64 bit native libraries; copyRet=" + copyNativeBinariesForSupportedAbi);
                    return copyNativeBinariesForSupportedAbi;
                }
            } else {
                if (CLEAR_ABI_OVERRIDE.equals(str)) {
                    str = null;
                } else if (str == null) {
                    str = null;
                }
                java.lang.String[] strArr = str != null ? new java.lang.String[]{str} : android.os.Build.SUPPORTED_ABIS;
                if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0 && str == null && hasRenderscriptBitcode(handle)) {
                    strArr = android.os.Build.SUPPORTED_32_BIT_ABIS;
                }
                int copyNativeBinariesForSupportedAbi3 = copyNativeBinariesForSupportedAbi(handle, file, strArr, true, z);
                if (copyNativeBinariesForSupportedAbi3 < 0 && copyNativeBinariesForSupportedAbi3 != -114) {
                    android.util.Slog.w(TAG, "Failure copying native libraries [errorCode=" + copyNativeBinariesForSupportedAbi3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
                    return copyNativeBinariesForSupportedAbi3;
                }
            }
            return 1;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Copying native libraries failed", e);
            return -110;
        }
    }

    public static long sumNativeBinariesWithOverride(com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String str) throws java.io.IOException {
        if (handle.multiArch) {
            if (str != null && !CLEAR_ABI_OVERRIDE.equals(str)) {
                android.util.Slog.w(TAG, "Ignoring abiOverride for multi arch application.");
            }
            long sumNativeBinariesForSupportedAbi = android.os.Build.SUPPORTED_32_BIT_ABIS.length > 0 ? 0 + sumNativeBinariesForSupportedAbi(handle, android.os.Build.SUPPORTED_32_BIT_ABIS) : 0L;
            if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0) {
                return sumNativeBinariesForSupportedAbi + sumNativeBinariesForSupportedAbi(handle, android.os.Build.SUPPORTED_64_BIT_ABIS);
            }
            return sumNativeBinariesForSupportedAbi;
        }
        if (CLEAR_ABI_OVERRIDE.equals(str)) {
            str = null;
        } else if (str == null) {
            str = null;
        }
        java.lang.String[] strArr = str != null ? new java.lang.String[]{str} : android.os.Build.SUPPORTED_ABIS;
        if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0 && str == null && hasRenderscriptBitcode(handle)) {
            strArr = android.os.Build.SUPPORTED_32_BIT_ABIS;
        }
        return 0 + sumNativeBinariesForSupportedAbi(handle, strArr);
    }

    private static int incrementalConfigureNativeBinariesForSupportedAbi(com.android.internal.content.NativeLibraryHelper.Handle handle, java.io.File file, java.lang.String str) {
        java.lang.String[] strArr = handle.apkPaths;
        if (strArr == null || strArr.length == 0) {
            android.util.Slog.e(TAG, "No apks to extract native libraries from.");
            return -110;
        }
        android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.INCREMENTAL_SERVICE);
        if (service == null) {
            return -110;
        }
        android.os.incremental.IncrementalManager incrementalManager = new android.os.incremental.IncrementalManager(android.os.incremental.IIncrementalService.Stub.asInterface(service));
        java.io.File parentFile = new java.io.File(strArr[0]).getParentFile();
        android.os.incremental.IncrementalStorage openStorage = incrementalManager.openStorage(parentFile.getAbsolutePath());
        if (openStorage == null) {
            android.util.Slog.e(TAG, "Failed to find incremental storage");
            return -110;
        }
        java.lang.String relativePath = getRelativePath(parentFile, file);
        if (relativePath == null) {
            return -110;
        }
        for (java.lang.String str2 : strArr) {
            if (!openStorage.configureNativeBinaries(str2, relativePath, str, handle.extractNativeLibs)) {
                return -110;
            }
        }
        return 1;
    }

    private static java.lang.String getRelativePath(java.io.File file, java.io.File file2) {
        try {
            java.nio.file.Path relativize = file.toPath().relativize(file2.toPath());
            if (relativize.toString().isEmpty()) {
                return "";
            }
            return relativize.toString();
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Failed to find relative path between: " + file.getAbsolutePath() + " and: " + file2.getAbsolutePath());
            return null;
        }
    }

    public static boolean hasRenderscriptBitcode(com.android.internal.content.NativeLibraryHelper.Handle handle) throws java.io.IOException {
        for (long j : handle.apkHandles) {
            int hasRenderscriptBitcode = hasRenderscriptBitcode(j);
            if (hasRenderscriptBitcode < 0) {
                throw new java.io.IOException("Error scanning APK, code: " + hasRenderscriptBitcode);
            }
            if (hasRenderscriptBitcode == 1) {
                return true;
            }
        }
        return false;
    }
}
