package android.os.strictmode;

/* loaded from: classes3.dex */
public final class LeakedClosableViolation extends android.os.strictmode.Violation {
    public LeakedClosableViolation(java.lang.String str, java.lang.Throwable th) {
        super(str);
        initCause(th);
    }

    public LeakedClosableViolation(java.lang.String str) {
        super(str);
    }
}
