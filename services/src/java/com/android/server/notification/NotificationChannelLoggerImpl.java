package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationChannelLoggerImpl implements com.android.server.notification.NotificationChannelLogger {
    com.android.internal.logging.UiEventLogger mUiEventLogger = new com.android.internal.logging.UiEventLoggerImpl();

    @Override // com.android.server.notification.NotificationChannelLogger
    public void logNotificationChannel(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, android.app.NotificationChannel notificationChannel, int i, java.lang.String str, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.NOTIFICATION_CHANNEL_MODIFIED, notificationChannelEvent.getId(), i, str, com.android.server.notification.NotificationChannelLogger.getIdHash(notificationChannel), i2, i3, notificationChannel.isConversation(), com.android.server.notification.NotificationChannelLogger.getConversationIdHash(notificationChannel), notificationChannel.isDemoted(), notificationChannel.isImportantConversation());
    }

    @Override // com.android.server.notification.NotificationChannelLogger
    public void logNotificationChannelGroup(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, android.app.NotificationChannelGroup notificationChannelGroup, int i, java.lang.String str, boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.NOTIFICATION_CHANNEL_MODIFIED, notificationChannelEvent.getId(), i, str, com.android.server.notification.NotificationChannelLogger.getIdHash(notificationChannelGroup), com.android.server.notification.NotificationChannelLogger.getImportance(z), com.android.server.notification.NotificationChannelLogger.getImportance(notificationChannelGroup), false, 0, false, false);
    }

    @Override // com.android.server.notification.NotificationChannelLogger
    public void logAppEvent(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, int i, java.lang.String str) {
        this.mUiEventLogger.log(notificationChannelEvent, i, str);
    }
}
