package com.android.internal.pm.parsing;

/* loaded from: classes5.dex */
public class PackageParser2 implements java.lang.AutoCloseable {
    private static final boolean LOG_PARSE_TIMINGS = android.os.Build.IS_DEBUGGABLE;
    private static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    private static final java.lang.String TAG = "PackageParsing";
    protected com.android.internal.pm.parsing.IPackageCacher mCacher;
    private final com.android.internal.pm.pkg.parsing.ParsingPackageUtils mParsingUtils;
    private final java.lang.ThreadLocal<android.content.pm.ApplicationInfo> mSharedAppInfo = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return com.android.internal.pm.parsing.PackageParser2.lambda$new$0();
        }
    });
    private final java.lang.ThreadLocal<android.content.pm.parsing.result.ParseTypeImpl> mSharedResult;

    static /* synthetic */ android.content.pm.ApplicationInfo lambda$new$0() {
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.uid = -1;
        return applicationInfo;
    }

    public PackageParser2(java.lang.String[] strArr, android.util.DisplayMetrics displayMetrics, com.android.internal.pm.parsing.IPackageCacher iPackageCacher, final com.android.internal.pm.parsing.PackageParser2.Callback callback) {
        java.util.List<android.permission.PermissionManager.SplitPermissionInfo> list;
        android.permission.PermissionManager permissionManager;
        if (displayMetrics == null) {
            displayMetrics = new android.util.DisplayMetrics();
            displayMetrics.setToDefaults();
        }
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication != null && (permissionManager = (android.permission.PermissionManager) currentApplication.getSystemService(android.permission.PermissionManager.class)) != null) {
            list = permissionManager.getSplitPermissions();
        } else {
            list = null;
        }
        list = list == null ? new java.util.ArrayList<>() : list;
        this.mCacher = iPackageCacher;
        this.mParsingUtils = new com.android.internal.pm.pkg.parsing.ParsingPackageUtils(strArr, displayMetrics, list, callback);
        final android.content.pm.parsing.result.ParseInput.Callback callback2 = new android.content.pm.parsing.result.ParseInput.Callback() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda1
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, java.lang.String str, int i) {
                boolean lambda$new$1;
                lambda$new$1 = com.android.internal.pm.parsing.PackageParser2.this.lambda$new$1(callback, j, str, i);
                return lambda$new$1;
            }
        };
        this.mSharedResult = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.internal.pm.parsing.PackageParser2.lambda$new$2(android.content.pm.parsing.result.ParseInput.Callback.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(com.android.internal.pm.parsing.PackageParser2.Callback callback, long j, java.lang.String str, int i) {
        android.content.pm.ApplicationInfo applicationInfo = this.mSharedAppInfo.get();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        return callback.isChangeEnabled(j, applicationInfo);
    }

    static /* synthetic */ android.content.pm.parsing.result.ParseTypeImpl lambda$new$2(android.content.pm.parsing.result.ParseInput.Callback callback) {
        return new android.content.pm.parsing.result.ParseTypeImpl(callback);
    }

    public com.android.internal.pm.parsing.pkg.ParsedPackage parsePackage(java.io.File file, int i, boolean z) throws com.android.internal.pm.parsing.PackageParserException {
        com.android.internal.pm.parsing.pkg.ParsedPackage cachedResult;
        java.io.File[] listFiles = file.listFiles();
        if (com.android.internal.util.ArrayUtils.size(listFiles) == 1 && listFiles[0].isDirectory()) {
            file = listFiles[0];
        }
        if (z && this.mCacher != null && (cachedResult = this.mCacher.getCachedResult(file, i)) != null) {
            return cachedResult;
        }
        long uptimeMillis = LOG_PARSE_TIMINGS ? android.os.SystemClock.uptimeMillis() : 0L;
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePackage = this.mParsingUtils.parsePackage(this.mSharedResult.get().reset(), file, i);
        if (parsePackage.isError()) {
            throw new com.android.internal.pm.parsing.PackageParserException(parsePackage.getErrorCode(), parsePackage.getErrorMessage(), parsePackage.getException());
        }
        com.android.internal.pm.parsing.pkg.ParsedPackage hideAsParsed = parsePackage.getResult().hideAsParsed();
        long uptimeMillis2 = LOG_PARSE_TIMINGS ? android.os.SystemClock.uptimeMillis() : 0L;
        if (this.mCacher != null) {
            this.mCacher.cacheResult(file, i, hideAsParsed);
        }
        if (LOG_PARSE_TIMINGS) {
            long j = uptimeMillis2 - uptimeMillis;
            long uptimeMillis3 = android.os.SystemClock.uptimeMillis() - uptimeMillis2;
            if (j + uptimeMillis3 > 100) {
                android.util.Slog.i("PackageParsing", "Parse times for '" + file + "': parse=" + j + "ms, update_cache=" + uptimeMillis3 + " ms");
            }
        }
        return hideAsParsed;
    }

    public com.android.internal.pm.parsing.pkg.ParsedPackage parsePackageFromPackageLite(android.content.pm.parsing.PackageLite packageLite, int i) throws com.android.internal.pm.parsing.PackageParserException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePackageFromPackageLite = this.mParsingUtils.parsePackageFromPackageLite(this.mSharedResult.get().reset(), packageLite, i);
        if (parsePackageFromPackageLite.isError()) {
            throw new com.android.internal.pm.parsing.PackageParserException(parsePackageFromPackageLite.getErrorCode(), parsePackageFromPackageLite.getErrorMessage(), parsePackageFromPackageLite.getException());
        }
        return parsePackageFromPackageLite.getResult().hideAsParsed();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mSharedResult.remove();
        this.mSharedAppInfo.remove();
    }

    public static abstract class Callback implements com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback {
        public abstract boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo);

        @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
        public final com.android.internal.pm.pkg.parsing.ParsingPackage startParsingPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.res.TypedArray typedArray, boolean z) {
            return com.android.internal.pm.parsing.pkg.PackageImpl.forParsing(str, str2, str3, typedArray, z, this);
        }
    }
}
