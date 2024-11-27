package android.os.strictmode;

/* loaded from: classes3.dex */
public final class IncorrectContextUseViolation extends android.os.strictmode.Violation {
    public IncorrectContextUseViolation(java.lang.String str, java.lang.Throwable th) {
        super(str);
        initCause(th);
    }
}
