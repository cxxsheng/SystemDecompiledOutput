package android.server.app;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.server.app.FeatureFlags FEATURE_FLAGS = new android.server.app.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DISABLE_GAME_MODE_WHEN_APP_TOP = "android.server.app.disable_game_mode_when_app_top";
    public static final java.lang.String FLAG_GAME_DEFAULT_FRAME_RATE = "android.server.app.game_default_frame_rate";

    public static boolean disableGameModeWhenAppTop() {
        return FEATURE_FLAGS.disableGameModeWhenAppTop();
    }

    public static boolean gameDefaultFrameRate() {
        return FEATURE_FLAGS.gameDefaultFrameRate();
    }
}
