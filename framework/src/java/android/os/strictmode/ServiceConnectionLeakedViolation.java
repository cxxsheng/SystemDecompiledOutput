package android.os.strictmode;

/* loaded from: classes3.dex */
public final class ServiceConnectionLeakedViolation extends android.os.strictmode.Violation {
    public ServiceConnectionLeakedViolation(java.lang.Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}
