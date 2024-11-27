package android.content.pm.parsing.result;

/* loaded from: classes.dex */
public class ParseTypeImpl implements android.content.pm.parsing.result.ParseInput, android.content.pm.parsing.result.ParseResult<java.lang.Object> {
    public static final boolean DEBUG_FILL_STACK_TRACE = false;
    public static final boolean DEBUG_LOG_ON_ERROR = false;
    public static final boolean DEBUG_THROW_ALL_ERRORS = false;
    private static final java.lang.String TAG = "ParseTypeImpl";
    private final android.content.pm.parsing.result.ParseInput.Callback mCallback;
    private java.lang.String mErrorMessage;
    private java.lang.Exception mException;
    private java.lang.String mPackageName;
    private java.lang.Object mResult;
    private int mErrorCode = 1;
    private android.util.ArrayMap<java.lang.Long, java.lang.String> mDeferredErrors = null;
    private int mTargetSdkVersion = -1;

    public static android.content.pm.parsing.result.ParseTypeImpl forParsingWithoutPlatformCompat() {
        return new android.content.pm.parsing.result.ParseTypeImpl(new android.content.pm.parsing.result.ParseInput.Callback() { // from class: android.content.pm.parsing.result.ParseTypeImpl$$ExternalSyntheticLambda1
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, java.lang.String str, int i) {
                return android.content.pm.parsing.result.ParseTypeImpl.lambda$forParsingWithoutPlatformCompat$0(j, str, i);
            }
        });
    }

    static /* synthetic */ boolean lambda$forParsingWithoutPlatformCompat$0(long j, java.lang.String str, int i) {
        int targetSdkForChange = android.content.pm.parsing.result.ParseInput.DeferredError.getTargetSdkForChange(j);
        return targetSdkForChange != -1 && i > targetSdkForChange;
    }

    public static android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing() {
        final com.android.internal.compat.IPlatformCompat asInterface = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE));
        return new android.content.pm.parsing.result.ParseTypeImpl(new android.content.pm.parsing.result.ParseInput.Callback() { // from class: android.content.pm.parsing.result.ParseTypeImpl$$ExternalSyntheticLambda0
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, java.lang.String str, int i) {
                return android.content.pm.parsing.result.ParseTypeImpl.lambda$forDefaultParsing$1(com.android.internal.compat.IPlatformCompat.this, j, str, i);
            }
        });
    }

    static /* synthetic */ boolean lambda$forDefaultParsing$1(com.android.internal.compat.IPlatformCompat iPlatformCompat, long j, java.lang.String str, int i) {
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        try {
            return iPlatformCompat.isChangeEnabled(j, applicationInfo);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, "IPlatformCompat query failed", e);
            return true;
        }
    }

    public ParseTypeImpl(android.content.pm.parsing.result.ParseInput.Callback callback) {
        this.mCallback = callback;
    }

    public android.content.pm.parsing.result.ParseInput reset() {
        this.mResult = null;
        this.mErrorCode = 1;
        this.mErrorMessage = null;
        this.mException = null;
        if (this.mDeferredErrors != null) {
            this.mDeferredErrors.erase();
        }
        this.mTargetSdkVersion = -1;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> success(ResultType resulttype) {
        if (this.mErrorCode != 1) {
            android.util.Slog.wtf(TAG, "Cannot set to success after set to error, was " + this.mErrorMessage, this.mException);
        }
        this.mResult = resulttype;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public android.content.pm.parsing.result.ParseResult<?> deferError(java.lang.String str, long j) {
        if (this.mTargetSdkVersion != -1) {
            if (this.mDeferredErrors != null && this.mDeferredErrors.containsKey(java.lang.Long.valueOf(j))) {
                return success(null);
            }
            if (this.mCallback.isChangeEnabled(j, this.mPackageName, this.mTargetSdkVersion)) {
                return error(str);
            }
            if (this.mDeferredErrors == null) {
                this.mDeferredErrors = new android.util.ArrayMap<>();
            }
            this.mDeferredErrors.put(java.lang.Long.valueOf(j), null);
            return success(null);
        }
        if (this.mDeferredErrors == null) {
            this.mDeferredErrors = new android.util.ArrayMap<>();
        }
        this.mDeferredErrors.putIfAbsent(java.lang.Long.valueOf(j), str);
        return success(null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public android.content.pm.parsing.result.ParseResult<?> enableDeferredError(java.lang.String str, int i) {
        this.mPackageName = str;
        this.mTargetSdkVersion = i;
        int size = com.android.internal.util.CollectionUtils.size(this.mDeferredErrors);
        while (true) {
            size--;
            if (size >= 0) {
                long longValue = this.mDeferredErrors.keyAt(size).longValue();
                java.lang.String valueAt = this.mDeferredErrors.valueAt(size);
                if (this.mCallback.isChangeEnabled(longValue, this.mPackageName, this.mTargetSdkVersion)) {
                    return error(valueAt);
                }
                this.mDeferredErrors.setValueAt(size, null);
            } else {
                return success(null);
            }
        }
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> skip(java.lang.String str) {
        return error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_SKIPPED, str);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i) {
        return error(i, null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(java.lang.String str) {
        return error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, str);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i, java.lang.String str) {
        return error(i, str, null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(android.content.pm.parsing.result.ParseResult<?> parseResult) {
        return error(parseResult.getErrorCode(), parseResult.getErrorMessage(), parseResult.getException());
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i, java.lang.String str, java.lang.Exception exc) {
        this.mErrorCode = i;
        this.mErrorMessage = str;
        this.mException = exc;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public java.lang.Object getResult() {
        return this.mResult;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public boolean isSuccess() {
        return this.mErrorCode == 1;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public boolean isError() {
        return !isSuccess();
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public java.lang.Exception getException() {
        return this.mException;
    }
}
