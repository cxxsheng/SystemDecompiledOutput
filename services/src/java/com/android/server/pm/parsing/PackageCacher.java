package com.android.server.pm.parsing;

/* loaded from: classes2.dex */
public class PackageCacher implements com.android.internal.pm.parsing.IPackageCacher {
    private static final java.lang.String TAG = "PackageCacher";
    public static final java.util.concurrent.atomic.AtomicInteger sCachedPackageReadCount = new java.util.concurrent.atomic.AtomicInteger();

    @android.annotation.NonNull
    private final java.io.File mCacheDir;

    @android.annotation.Nullable
    private final com.android.internal.pm.parsing.PackageParser2.Callback mCallback;

    public PackageCacher(java.io.File file) {
        this(file, null);
    }

    public PackageCacher(java.io.File file, @android.annotation.Nullable com.android.internal.pm.parsing.PackageParser2.Callback callback) {
        this.mCacheDir = file;
        this.mCallback = callback;
    }

    private java.lang.String getCacheKey(java.io.File file, int i) {
        return file.getName() + '-' + i + '-' + file.getAbsolutePath().hashCode();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.pm.parsing.pkg.ParsedPackage fromCacheEntry(byte[] bArr) {
        return fromCacheEntryStatic(bArr, this.mCallback);
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.internal.pm.parsing.pkg.ParsedPackage fromCacheEntryStatic(byte[] bArr) {
        return fromCacheEntryStatic(bArr, null);
    }

    private static com.android.internal.pm.parsing.pkg.ParsedPackage fromCacheEntryStatic(byte[] bArr, @android.annotation.Nullable com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        new android.content.pm.PackageParserCacheHelper.ReadHelper(obtain).startAndInstall();
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = new com.android.internal.pm.parsing.pkg.PackageImpl(obtain, callback);
        obtain.recycle();
        sCachedPackageReadCount.incrementAndGet();
        return packageImpl;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected byte[] toCacheEntry(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        return toCacheEntryStatic(parsedPackage);
    }

    @com.android.internal.annotations.VisibleForTesting
    public static byte[] toCacheEntryStatic(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.content.pm.PackageParserCacheHelper.WriteHelper writeHelper = new android.content.pm.PackageParserCacheHelper.WriteHelper(obtain);
        ((com.android.internal.pm.parsing.pkg.PackageImpl) parsedPackage).writeToParcel(obtain, 0);
        writeHelper.finishAndUninstall();
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    private static boolean isCacheUpToDate(java.io.File file, java.io.File file2) {
        try {
            if (file.toPath().startsWith(android.os.Environment.getApexDirectory().toPath())) {
                java.io.File backingApexFile = com.android.server.pm.ApexManager.getInstance().getBackingApexFile(file);
                if (backingApexFile == null) {
                    android.util.Slog.w(TAG, "Failed to find APEX file backing " + file.getAbsolutePath());
                } else {
                    file = backingApexFile;
                }
            }
            return android.system.Os.stat(file.getAbsolutePath()).st_mtime < android.system.Os.stat(file2.getAbsolutePath()).st_mtime;
        } catch (android.system.ErrnoException e) {
            if (e.errno != android.system.OsConstants.ENOENT) {
                android.util.Slog.w("Error while stating package cache : ", e);
            }
            return false;
        }
    }

    public com.android.internal.pm.parsing.pkg.ParsedPackage getCachedResult(java.io.File file, int i) {
        java.io.File file2 = new java.io.File(this.mCacheDir, getCacheKey(file, i));
        try {
            if (!isCacheUpToDate(file, file2)) {
                return null;
            }
            com.android.internal.pm.parsing.pkg.ParsedPackage fromCacheEntry = fromCacheEntry(libcore.io.IoUtils.readFileAsByteArray(file2.getAbsolutePath()));
            if (!file.getAbsolutePath().equals(fromCacheEntry.getPath())) {
                return null;
            }
            return fromCacheEntry;
        } catch (java.lang.Throwable th) {
            android.util.Slog.w(TAG, "Error reading package cache: ", th);
            file2.delete();
            return null;
        }
    }

    public void cacheResult(java.io.File file, int i, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        try {
            java.io.File file2 = new java.io.File(this.mCacheDir, getCacheKey(file, i));
            if (file2.exists() && !file2.delete()) {
                android.util.Slog.e(TAG, "Unable to delete cache file: " + file2);
            }
            byte[] cacheEntry = toCacheEntry(parsedPackage);
            if (cacheEntry == null) {
                return;
            }
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file2);
                try {
                    fileOutputStream.write(cacheEntry);
                    fileOutputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        fileOutputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Error writing cache entry.", e);
                file2.delete();
            }
        } catch (java.lang.Throwable th3) {
            android.util.Slog.w(TAG, "Error saving package cache.", th3);
        }
    }

    public void cleanCachedResult(@android.annotation.NonNull java.io.File file) {
        final java.lang.String name = file.getName();
        for (java.io.File file2 : android.os.FileUtils.listFilesOrEmpty(this.mCacheDir, new java.io.FilenameFilter() { // from class: com.android.server.pm.parsing.PackageCacher$$ExternalSyntheticLambda0
            @Override // java.io.FilenameFilter
            public final boolean accept(java.io.File file3, java.lang.String str) {
                boolean lambda$cleanCachedResult$0;
                lambda$cleanCachedResult$0 = com.android.server.pm.parsing.PackageCacher.lambda$cleanCachedResult$0(name, file3, str);
                return lambda$cleanCachedResult$0;
            }
        })) {
            if (!file2.delete()) {
                android.util.Slog.e(TAG, "Unable to clean cache file: " + file2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$cleanCachedResult$0(java.lang.String str, java.io.File file, java.lang.String str2) {
        return str2.startsWith(str);
    }
}
