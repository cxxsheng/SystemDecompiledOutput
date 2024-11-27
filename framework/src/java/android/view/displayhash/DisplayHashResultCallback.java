package android.view.displayhash;

/* loaded from: classes4.dex */
public interface DisplayHashResultCallback {
    public static final int DISPLAY_HASH_ERROR_INVALID_BOUNDS = -2;
    public static final int DISPLAY_HASH_ERROR_INVALID_HASH_ALGORITHM = -5;
    public static final int DISPLAY_HASH_ERROR_MISSING_WINDOW = -3;
    public static final int DISPLAY_HASH_ERROR_NOT_VISIBLE_ON_SCREEN = -4;
    public static final int DISPLAY_HASH_ERROR_TOO_MANY_REQUESTS = -6;
    public static final int DISPLAY_HASH_ERROR_UNKNOWN = -1;
    public static final java.lang.String EXTRA_DISPLAY_HASH = "DISPLAY_HASH";
    public static final java.lang.String EXTRA_DISPLAY_HASH_ERROR_CODE = "DISPLAY_HASH_ERROR_CODE";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayHashErrorCode {
    }

    void onDisplayHashError(int i);

    void onDisplayHashResult(android.view.displayhash.DisplayHash displayHash);
}
