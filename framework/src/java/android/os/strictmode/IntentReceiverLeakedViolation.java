package android.os.strictmode;

/* loaded from: classes3.dex */
public final class IntentReceiverLeakedViolation extends android.os.strictmode.Violation {
    public IntentReceiverLeakedViolation(java.lang.Throwable th) {
        super(null);
        setStackTrace(th.getStackTrace());
    }
}
