package android.telephony.ims;

/* loaded from: classes3.dex */
public final class ImsException extends java.lang.Exception {
    public static final int CODE_ERROR_INVALID_SUBSCRIPTION = 3;
    public static final int CODE_ERROR_SERVICE_UNAVAILABLE = 1;
    public static final int CODE_ERROR_UNSPECIFIED = 0;
    public static final int CODE_ERROR_UNSUPPORTED_OPERATION = 2;
    private int mCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsErrorCode {
    }

    @android.annotation.SystemApi
    public ImsException(java.lang.String str) {
        super(getMessage(str, 0));
        this.mCode = 0;
    }

    @android.annotation.SystemApi
    public ImsException(java.lang.String str, int i) {
        super(getMessage(str, i));
        this.mCode = 0;
        this.mCode = i;
    }

    @android.annotation.SystemApi
    public ImsException(java.lang.String str, int i, java.lang.Throwable th) {
        super(getMessage(str, i), th);
        this.mCode = 0;
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }

    private static java.lang.String getMessage(java.lang.String str, int i) {
        if (!android.text.TextUtils.isEmpty(str)) {
            return str + " (code: " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        return "code: " + i;
    }
}
