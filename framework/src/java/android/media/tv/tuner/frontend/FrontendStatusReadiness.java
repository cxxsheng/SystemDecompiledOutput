package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class FrontendStatusReadiness {
    public static final int FRONTEND_STATUS_READINESS_STABLE = 3;
    public static final int FRONTEND_STATUS_READINESS_UNAVAILABLE = 1;
    public static final int FRONTEND_STATUS_READINESS_UNDEFINED = 0;
    public static final int FRONTEND_STATUS_READINESS_UNSTABLE = 2;
    public static final int FRONTEND_STATUS_READINESS_UNSUPPORTED = 4;
    private int mFrontendStatusType;
    private int mStatusReadiness;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Readiness {
    }

    private FrontendStatusReadiness(int i, int i2) {
        this.mFrontendStatusType = i;
        this.mStatusReadiness = i2;
    }

    public int getStatusType() {
        return this.mFrontendStatusType;
    }

    public int getStatusReadiness() {
        return this.mStatusReadiness;
    }
}
