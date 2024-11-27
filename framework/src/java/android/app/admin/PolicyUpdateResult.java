package android.app.admin;

/* loaded from: classes.dex */
public final class PolicyUpdateResult {
    public static final int RESULT_FAILURE_CONFLICTING_ADMIN_POLICY = 1;
    public static final int RESULT_FAILURE_HARDWARE_LIMITATION = 4;
    public static final int RESULT_FAILURE_STORAGE_LIMIT_REACHED = 3;
    public static final int RESULT_FAILURE_UNKNOWN = -1;
    public static final int RESULT_POLICY_CLEARED = 2;
    public static final int RESULT_POLICY_SET = 0;
    private final int mResultCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public PolicyUpdateResult(int i) {
        this.mResultCode = i;
    }

    public int getResultCode() {
        return this.mResultCode;
    }
}
