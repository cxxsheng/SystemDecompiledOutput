package android.os.strictmode;

/* loaded from: classes3.dex */
public final class WebViewMethodCalledOnWrongThreadViolation extends android.os.strictmode.Violation {
    public WebViewMethodCalledOnWrongThreadViolation(java.lang.Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}
