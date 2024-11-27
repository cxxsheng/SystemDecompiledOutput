package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface NumberVerificationCallback {
    public static final int REASON_CONCURRENT_REQUESTS = 4;
    public static final int REASON_IN_ECBM = 5;
    public static final int REASON_IN_EMERGENCY_CALL = 6;
    public static final int REASON_NETWORK_NOT_AVAILABLE = 2;
    public static final int REASON_TIMED_OUT = 1;
    public static final int REASON_TOO_MANY_CALLS = 3;
    public static final int REASON_UNSPECIFIED = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NumberVerificationFailureReason {
    }

    default void onCallReceived(java.lang.String str) {
    }

    default void onVerificationFailed(int i) {
    }
}
