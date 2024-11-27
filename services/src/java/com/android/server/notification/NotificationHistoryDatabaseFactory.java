package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationHistoryDatabaseFactory {
    private static com.android.server.notification.NotificationHistoryDatabase sTestingNotificationHistoryDb;

    public static void setTestingNotificationHistoryDatabase(com.android.server.notification.NotificationHistoryDatabase notificationHistoryDatabase) {
        sTestingNotificationHistoryDb = notificationHistoryDatabase;
    }

    public static com.android.server.notification.NotificationHistoryDatabase create(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.io.File file) {
        if (sTestingNotificationHistoryDb != null) {
            return sTestingNotificationHistoryDb;
        }
        return new com.android.server.notification.NotificationHistoryDatabase(handler, file);
    }
}
