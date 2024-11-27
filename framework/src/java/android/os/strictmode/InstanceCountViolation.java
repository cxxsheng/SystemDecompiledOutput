package android.os.strictmode;

/* loaded from: classes3.dex */
public class InstanceCountViolation extends android.os.strictmode.Violation {
    private static final java.lang.StackTraceElement[] FAKE_STACK = {new java.lang.StackTraceElement("android.os.StrictMode", "setClassInstanceLimit", "StrictMode.java", 1)};
    private final long mInstances;

    public InstanceCountViolation(java.lang.Class cls, long j, int i) {
        super(cls.toString() + "; instances=" + j + "; limit=" + i);
        setStackTrace(FAKE_STACK);
        this.mInstances = j;
    }

    public long getNumberOfInstances() {
        return this.mInstances;
    }
}
