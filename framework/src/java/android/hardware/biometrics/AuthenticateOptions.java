package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface AuthenticateOptions {
    public static final int DISPLAY_STATE_AOD = 4;
    public static final int DISPLAY_STATE_LOCKSCREEN = 1;
    public static final int DISPLAY_STATE_NO_UI = 2;
    public static final int DISPLAY_STATE_SCREENSAVER = 3;
    public static final int DISPLAY_STATE_UNKNOWN = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayState {
    }

    java.lang.String getAttributionTag();

    int getDisplayState();

    java.lang.String getOpPackageName();

    int getSensorId();

    int getUserId();
}
