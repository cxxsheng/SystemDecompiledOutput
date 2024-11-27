package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class TextBoundsInfoResult {
    public static final int CODE_CANCELLED = 3;
    public static final int CODE_FAILED = 2;
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_UNSUPPORTED = 0;
    private final int mResultCode;
    private final android.view.inputmethod.TextBoundsInfo mTextBoundsInfo;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public TextBoundsInfoResult(int i) {
        this(i, null);
    }

    public TextBoundsInfoResult(int i, android.view.inputmethod.TextBoundsInfo textBoundsInfo) {
        if (i == 1 && textBoundsInfo == null) {
            throw new java.lang.IllegalStateException("TextBoundsInfo must be provided when the resultCode is CODE_SUCCESS.");
        }
        this.mResultCode = i;
        this.mTextBoundsInfo = textBoundsInfo;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public android.view.inputmethod.TextBoundsInfo getTextBoundsInfo() {
        return this.mTextBoundsInfo;
    }
}
