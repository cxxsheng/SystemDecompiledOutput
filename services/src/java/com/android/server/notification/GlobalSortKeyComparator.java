package com.android.server.notification;

/* loaded from: classes2.dex */
public class GlobalSortKeyComparator implements java.util.Comparator<com.android.server.notification.NotificationRecord> {
    private static final java.lang.String TAG = "GlobalSortComp";

    @Override // java.util.Comparator
    public int compare(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2) {
        if (notificationRecord.getGlobalSortKey() == null) {
            android.util.Slog.wtf(TAG, "Missing left global sort key: " + notificationRecord);
            return 1;
        }
        if (notificationRecord2.getGlobalSortKey() == null) {
            android.util.Slog.wtf(TAG, "Missing right global sort key: " + notificationRecord2);
            return -1;
        }
        return notificationRecord.getGlobalSortKey().compareTo(notificationRecord2.getGlobalSortKey());
    }
}
