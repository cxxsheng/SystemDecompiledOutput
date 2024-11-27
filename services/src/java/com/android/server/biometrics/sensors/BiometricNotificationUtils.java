package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class BiometricNotificationUtils {
    private static final java.lang.String BAD_CALIBRATION_NOTIFICATION_TAG = "FingerprintBadCalibration";
    private static final java.lang.String FACE_ENROLL_ACTION = "android.settings.FACE_ENROLL";
    private static final java.lang.String FACE_ENROLL_CHANNEL = "FaceEnrollNotificationChannel";
    public static final java.lang.String FACE_ENROLL_NOTIFICATION_TAG = "FaceEnroll";
    private static final java.lang.String FACE_RE_ENROLL_CHANNEL = "FaceReEnrollNotificationChannel";
    private static final java.lang.String FACE_RE_ENROLL_NOTIFICATION_TAG = "FaceReEnroll";
    private static final java.lang.String FACE_SETTINGS_ACTION = "android.settings.FACE_SETTINGS";
    private static final java.lang.String FINGERPRINT_BAD_CALIBRATION_CHANNEL = "FingerprintBadCalibrationNotificationChannel";
    private static final java.lang.String FINGERPRINT_ENROLL_ACTION = "android.settings.FINGERPRINT_ENROLL";
    private static final java.lang.String FINGERPRINT_ENROLL_CHANNEL = "FingerprintEnrollNotificationChannel";
    public static final java.lang.String FINGERPRINT_ENROLL_NOTIFICATION_TAG = "FingerprintEnroll";
    private static final java.lang.String FINGERPRINT_SETTINGS_ACTION = "android.settings.FINGERPRINT_SETTINGS";
    private static final java.lang.String KEY_RE_ENROLL_FACE = "re_enroll_face_unlock";
    public static final int NOTIFICATION_ID = 1;
    private static final long NOTIFICATION_INTERVAL_MS = 86400000;
    private static final java.lang.String SETTINGS_PACKAGE = "com.android.settings";
    private static final java.lang.String TAG = "BiometricNotificationUtils";
    private static long sLastAlertTime = 0;
    private static final java.lang.String ACTION_BIOMETRIC_FRR_DISMISS = "action_biometric_frr_dismiss";
    private static final android.content.Intent DISMISS_FRR_INTENT = new android.content.Intent(ACTION_BIOMETRIC_FRR_DISMISS);

    public static void showReEnrollmentNotification(@android.annotation.NonNull android.content.Context context) {
        java.lang.String string = context.getString(android.R.string.face_dangling_notification_title);
        java.lang.String string2 = context.getString(android.R.string.face_dialog_default_subtitle);
        java.lang.String string3 = context.getString(android.R.string.face_dangling_notification_msg);
        android.content.Intent intent = new android.content.Intent(FACE_SETTINGS_ACTION);
        intent.setPackage(SETTINGS_PACKAGE);
        showNotificationHelper(context, string, string2, string3, android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT), FACE_RE_ENROLL_CHANNEL, "sys", FACE_RE_ENROLL_NOTIFICATION_TAG, -1, false);
    }

    public static void showFaceEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        android.util.Slog.d(TAG, "Showing Face Enroll Notification");
        java.lang.String string = context.getString(android.R.string.deleted_key);
        java.lang.String string2 = context.getString(android.R.string.alert_windows_notification_title);
        java.lang.String string3 = context.getString(android.R.string.alert_windows_notification_channel_name);
        android.content.Intent intent = new android.content.Intent(FACE_ENROLL_ACTION);
        intent.setPackage(SETTINGS_PACKAGE);
        intent.putExtra("enroll_reason", 1);
        showNotificationHelper(context, string, string2, string3, android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT), FACE_ENROLL_CHANNEL, "recommendation", FACE_ENROLL_NOTIFICATION_TAG, 1, true);
    }

    public static void showFingerprintEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        android.util.Slog.d(TAG, "Showing Fingerprint Enroll Notification");
        java.lang.String string = context.getString(android.R.string.deleted_key);
        java.lang.String string2 = context.getString(android.R.string.alert_windows_notification_title);
        java.lang.String string3 = context.getString(android.R.string.alert_windows_notification_message);
        android.content.Intent intent = new android.content.Intent(FINGERPRINT_ENROLL_ACTION);
        intent.setPackage(SETTINGS_PACKAGE);
        intent.putExtra("enroll_reason", 1);
        showNotificationHelper(context, string, string2, string3, android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT), "recommendation", FINGERPRINT_ENROLL_CHANNEL, FINGERPRINT_ENROLL_NOTIFICATION_TAG, 1, true);
    }

    public static void showBadCalibrationNotification(@android.annotation.NonNull android.content.Context context) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = elapsedRealtime - sLastAlertTime;
        if (sLastAlertTime != 0 && j < 86400000) {
            android.util.Slog.v(TAG, "Skipping calibration notification : " + j);
            return;
        }
        sLastAlertTime = elapsedRealtime;
        java.lang.String string = context.getString(android.R.string.fingerprint_dangling_notification_msg_2);
        java.lang.String string2 = context.getString(android.R.string.fingerprint_dangling_notification_msg_all_deleted_1);
        java.lang.String string3 = context.getString(android.R.string.fingerprint_dangling_notification_msg_1);
        android.content.Intent intent = new android.content.Intent(FINGERPRINT_SETTINGS_ACTION);
        intent.setPackage(SETTINGS_PACKAGE);
        showNotificationHelper(context, string, string2, string3, android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT), "sys", FINGERPRINT_BAD_CALIBRATION_CHANNEL, BAD_CALIBRATION_NOTIFICATION_TAG, -1, false);
    }

    private static void showNotificationHelper(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PendingIntent pendingIntent, java.lang.String str4, java.lang.String str5, java.lang.String str6, int i, boolean z) {
        android.util.Slog.v(TAG, " listenToDismissEvent = " + z);
        android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(context, 0, DISMISS_FRR_INTENT, 67108864, null, android.os.UserHandle.CURRENT);
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(str5, str, 4);
        android.app.Notification.Builder visibility = new android.app.Notification.Builder(context, str5).setSmallIcon(android.R.drawable.ic_jog_dial_vibrate_on).setContentTitle(str2).setContentText(str3).setStyle(new android.app.Notification.BigTextStyle().bigText(str3)).setSubText(str).setOnlyAlertOnce(true).setLocalOnly(true).setAutoCancel(true).setCategory(str4).setContentIntent(pendingIntent).setVisibility(i);
        if (z) {
            visibility.setDeleteIntent(activityAsUser);
        }
        android.app.Notification build = visibility.build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notifyAsUser(str6, 1, build, android.os.UserHandle.CURRENT);
    }

    public static void cancelFaceReEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).cancelAsUser(FACE_RE_ENROLL_NOTIFICATION_TAG, 1, android.os.UserHandle.CURRENT);
    }

    public static void cancelFaceEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).cancelAsUser(FACE_ENROLL_NOTIFICATION_TAG, 1, android.os.UserHandle.CURRENT);
    }

    public static void cancelFingerprintEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).cancelAsUser(FINGERPRINT_ENROLL_NOTIFICATION_TAG, 1, android.os.UserHandle.CURRENT);
    }

    public static void cancelBadCalibrationNotification(@android.annotation.NonNull android.content.Context context) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).cancelAsUser(BAD_CALIBRATION_NOTIFICATION_TAG, 1, android.os.UserHandle.CURRENT);
    }
}
