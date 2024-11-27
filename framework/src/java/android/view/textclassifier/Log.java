package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class Log {
    static final boolean ENABLE_FULL_LOGGING = android.util.Log.isLoggable(android.view.textclassifier.TextClassifier.LOG_TAG, 2);

    private Log() {
    }

    public static void v(java.lang.String str, java.lang.String str2) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.v(str, str2);
        }
    }

    public static void d(java.lang.String str, java.lang.String str2) {
        android.util.Log.d(str, str2);
    }

    public static void w(java.lang.String str, java.lang.String str2) {
        android.util.Log.w(str, str2);
    }

    public static void e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.e(str, str2, th);
        } else {
            android.util.Log.d(str, java.lang.String.format("%s (%s)", str2, th != null ? th.getClass().getSimpleName() : "??"));
        }
    }
}
