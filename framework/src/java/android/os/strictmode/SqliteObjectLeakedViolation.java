package android.os.strictmode;

/* loaded from: classes3.dex */
public final class SqliteObjectLeakedViolation extends android.os.strictmode.Violation {
    public SqliteObjectLeakedViolation(java.lang.String str, java.lang.Throwable th) {
        super(str);
        initCause(th);
    }
}
