package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class BugreportParams {
    public static final int BUGREPORT_FLAG_DEFER_CONSENT = 2;
    public static final int BUGREPORT_FLAG_KEEP_BUGREPORT_ON_RETRIEVAL = 4;
    public static final int BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA = 1;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_MAX_VALUE = 7;
    public static final int BUGREPORT_MODE_ONBOARDING = 7;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;
    private final int mFlags;
    private final int mMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BugreportFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BugreportMode {
    }

    public BugreportParams(int i) {
        this.mMode = i;
        this.mFlags = 0;
    }

    public BugreportParams(int i, int i2) {
        this.mMode = i;
        this.mFlags = i2;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getFlags() {
        return this.mFlags;
    }
}
