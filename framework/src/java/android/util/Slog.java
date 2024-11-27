package android.util;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public final class Slog {
    private Slog() {
    }

    public static int v(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, 2, str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 2, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int d(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, 3, str, str2);
    }

    public static int d(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 3, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int i(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, 4, str, str2);
    }

    public static int i(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 4, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, 5, str, str2);
    }

    public static int w(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 5, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int w(java.lang.String str, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 5, str, android.util.Log.getStackTraceString(th));
    }

    public static int e(java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, 6, str, str2);
    }

    public static int e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.println_native(3, 6, str, str2 + '\n' + android.util.Log.getStackTraceString(th));
    }

    public static int wtf(java.lang.String str, java.lang.String str2) {
        return android.util.Log.wtf(3, str, str2, null, false, true);
    }

    public static void wtfQuiet(java.lang.String str, java.lang.String str2) {
        android.util.Log.wtfQuiet(3, str, str2, true);
    }

    public static int wtfStack(java.lang.String str, java.lang.String str2) {
        return android.util.Log.wtf(3, str, str2, null, true, true);
    }

    public static int wtf(java.lang.String str, java.lang.Throwable th) {
        return android.util.Log.wtf(3, str, th.getMessage(), th, false, true);
    }

    public static int wtf(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.wtf(3, str, str2, th, false, true);
    }

    public static int println(int i, java.lang.String str, java.lang.String str2) {
        return android.util.Log.println_native(3, i, str, str2);
    }
}
