package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class ContentCaptureHelper {
    private static final java.lang.String TAG = android.view.contentcapture.ContentCaptureHelper.class.getSimpleName();
    public static boolean sVerbose = false;
    public static boolean sDebug = true;

    public static java.lang.String getSanitizedString(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return charSequence.length() + "_chars";
    }

    public static int getDefaultLoggingLevel() {
        return android.os.Build.IS_DEBUGGABLE ? 1 : 0;
    }

    public static void setLoggingLevel() {
        setLoggingLevel(android.provider.DeviceConfig.getInt(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE, android.view.contentcapture.ContentCaptureManager.DEVICE_CONFIG_PROPERTY_LOGGING_LEVEL, getDefaultLoggingLevel()));
    }

    public static void setLoggingLevel(int i) {
        android.util.Log.i(TAG, "Setting logging level to " + getLoggingLevelAsString(i));
        sDebug = false;
        sVerbose = false;
        switch (i) {
            case 0:
                return;
            case 1:
                break;
            case 2:
                sVerbose = true;
                break;
            default:
                android.util.Log.w(TAG, "setLoggingLevel(): invalud level: " + i);
                return;
        }
        sDebug = true;
    }

    public static java.lang.String getLoggingLevelAsString(int i) {
        switch (i) {
            case 0:
                return "OFF";
            case 1:
                return "DEBUG";
            case 2:
                return "VERBOSE";
            default:
                return "UNKNOWN-" + i;
        }
    }

    public static <T> java.util.ArrayList<T> toList(java.util.Set<T> set) {
        if (set == null) {
            return null;
        }
        return new java.util.ArrayList<>(set);
    }

    public static <T> android.util.ArraySet<T> toSet(java.util.List<T> list) {
        if (list == null) {
            return null;
        }
        return new android.util.ArraySet<>(list);
    }

    private ContentCaptureHelper() {
        throw new java.lang.UnsupportedOperationException("contains only static methods");
    }
}
