package com.android.server;

/* loaded from: classes.dex */
public final class SystemTimeZone {
    private static final boolean DEBUG = false;
    private static final java.lang.String DEFAULT_TIME_ZONE_ID = "GMT";
    private static final java.lang.String TAG = "SystemTimeZone";
    public static final int TIME_ZONE_CONFIDENCE_HIGH = 100;
    public static final int TIME_ZONE_CONFIDENCE_LOW = 0;
    private static final java.lang.String TIME_ZONE_CONFIDENCE_SYSTEM_PROPERTY = "persist.sys.timezone_confidence";
    private static final java.lang.String TIME_ZONE_SYSTEM_PROPERTY = "persist.sys.timezone";

    @android.annotation.NonNull
    private static final android.util.LocalLog sTimeZoneDebugLog = new android.util.LocalLog(30, false);

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeZoneConfidence {
    }

    private SystemTimeZone() {
    }

    public static void initializeTimeZoneSettingsIfRequired() {
        java.lang.String str = android.os.SystemProperties.get(TIME_ZONE_SYSTEM_PROPERTY);
        if (!isValidTimeZoneId(str)) {
            java.lang.String str2 = "initializeTimeZoneSettingsIfRequired():persist.sys.timezone is not valid (" + str + "); setting to " + DEFAULT_TIME_ZONE_ID;
            android.util.Slog.w(TAG, str2);
            setTimeZoneId(DEFAULT_TIME_ZONE_ID, 0, str2);
        }
    }

    public static void addDebugLogEntry(@android.annotation.NonNull java.lang.String str) {
        sTimeZoneDebugLog.log(str);
    }

    public static boolean setTimeZoneId(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        boolean z = false;
        if (android.text.TextUtils.isEmpty(str) || !isValidTimeZoneId(str)) {
            addDebugLogEntry("setTimeZoneId: Invalid time zone ID. timeZoneId=" + str + ", confidence=" + i + ", logInfo=" + str2);
            return false;
        }
        synchronized (com.android.server.SystemTimeZone.class) {
            try {
                java.lang.String timeZoneId = getTimeZoneId();
                if (timeZoneId == null || !timeZoneId.equals(str)) {
                    android.os.SystemProperties.set(TIME_ZONE_SYSTEM_PROPERTY, str);
                    z = true;
                }
                boolean timeZoneConfidence = setTimeZoneConfidence(i);
                if (z || timeZoneConfidence) {
                    addDebugLogEntry("Time zone or confidence set:  (new) timeZoneId=" + str + ", (new) confidence=" + i + ", logInfo=" + str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    private static boolean setTimeZoneConfidence(int i) {
        if (getTimeZoneConfidence() != i) {
            android.os.SystemProperties.set(TIME_ZONE_CONFIDENCE_SYSTEM_PROPERTY, java.lang.Integer.toString(i));
            return true;
        }
        return false;
    }

    public static int getTimeZoneConfidence() {
        int i = android.os.SystemProperties.getInt(TIME_ZONE_CONFIDENCE_SYSTEM_PROPERTY, 0);
        if (isValidTimeZoneConfidence(i)) {
            return i;
        }
        return 0;
    }

    public static java.lang.String getTimeZoneId() {
        return android.os.SystemProperties.get(TIME_ZONE_SYSTEM_PROPERTY);
    }

    public static void dump(java.io.PrintWriter printWriter) {
        sTimeZoneDebugLog.dump(printWriter);
    }

    private static boolean isValidTimeZoneConfidence(int i) {
        return i >= 0 && i <= 100;
    }

    private static boolean isValidTimeZoneId(java.lang.String str) {
        return (str == null || str.isEmpty() || !com.android.i18n.timezone.ZoneInfoDb.getInstance().hasTimeZone(str)) ? false : true;
    }
}
