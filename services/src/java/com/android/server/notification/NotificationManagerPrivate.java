package com.android.server.notification;

/* loaded from: classes2.dex */
interface NotificationManagerPrivate {
    @android.annotation.Nullable
    com.android.server.notification.NotificationRecord getNotificationByKey(java.lang.String str);

    long getNotificationSoundTimeout(java.lang.String str, int i);
}
