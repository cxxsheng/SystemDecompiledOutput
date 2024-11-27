package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface ConnectionlessHandwritingCallback {
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_NO_TEXT_RECOGNIZED = 0;
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_OTHER = 2;
    public static final int CONNECTIONLESS_HANDWRITING_ERROR_UNSUPPORTED = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionlessHandwritingError {
    }

    void onError(int i);

    void onResult(java.lang.CharSequence charSequence);
}
