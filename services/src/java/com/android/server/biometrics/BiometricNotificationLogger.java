package com.android.server.biometrics;

/* loaded from: classes.dex */
public class BiometricNotificationLogger extends android.service.notification.NotificationListenerService {
    private static final java.lang.String TAG = "FRRNotificationListener";
    private com.android.server.biometrics.log.BiometricFrameworkStatsLogger mLogger;

    BiometricNotificationLogger() {
        this(com.android.server.biometrics.log.BiometricFrameworkStatsLogger.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    BiometricNotificationLogger(com.android.server.biometrics.log.BiometricFrameworkStatsLogger biometricFrameworkStatsLogger) {
        this.mLogger = biometricFrameworkStatsLogger;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        char c;
        int i;
        if (statusBarNotification == null || statusBarNotification.getTag() == null) {
            return;
        }
        java.lang.String tag = statusBarNotification.getTag();
        switch (tag.hashCode()) {
            case -2131839613:
                if (tag.equals(com.android.server.biometrics.sensors.BiometricNotificationUtils.FACE_ENROLL_NOTIFICATION_TAG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1786899082:
                if (tag.equals(com.android.server.biometrics.sensors.BiometricNotificationUtils.FINGERPRINT_ENROLL_NOTIFICATION_TAG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                if (statusBarNotification.getTag() == com.android.server.biometrics.sensors.BiometricNotificationUtils.FACE_ENROLL_NOTIFICATION_TAG) {
                    i = 4;
                } else {
                    i = 1;
                }
                android.util.Slog.d(TAG, "onNotificationPosted, tag=(" + statusBarNotification.getTag() + ")");
                this.mLogger.logFrameworkNotification(1, i);
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, int i) {
        char c;
        if (statusBarNotification == null || statusBarNotification.getTag() == null) {
            return;
        }
        java.lang.String tag = statusBarNotification.getTag();
        switch (tag.hashCode()) {
            case -2131839613:
                if (tag.equals(com.android.server.biometrics.sensors.BiometricNotificationUtils.FACE_ENROLL_NOTIFICATION_TAG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1786899082:
                if (tag.equals(com.android.server.biometrics.sensors.BiometricNotificationUtils.FINGERPRINT_ENROLL_NOTIFICATION_TAG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                android.util.Slog.d(TAG, "onNotificationRemoved, tag=(" + statusBarNotification.getTag() + "), reason=(" + i + ")");
                int i2 = statusBarNotification.getTag() == com.android.server.biometrics.sensors.BiometricNotificationUtils.FACE_ENROLL_NOTIFICATION_TAG ? 4 : 1;
                switch (i) {
                    case 1:
                        this.mLogger.logFrameworkNotification(2, i2);
                        break;
                    case 2:
                        this.mLogger.logFrameworkNotification(3, i2);
                        break;
                    default:
                        android.util.Slog.d(TAG, "unhandled reason, ignoring logging");
                        break;
                }
        }
    }
}
