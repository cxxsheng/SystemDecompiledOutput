package com.android.server.notification;

/* loaded from: classes2.dex */
public interface NotificationChannelLogger {
    void logAppEvent(@android.annotation.NonNull com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, int i, java.lang.String str);

    void logNotificationChannel(@android.annotation.NonNull com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, @android.annotation.NonNull android.app.NotificationChannel notificationChannel, int i, java.lang.String str, int i2, int i3);

    void logNotificationChannelGroup(@android.annotation.NonNull com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent, @android.annotation.NonNull android.app.NotificationChannelGroup notificationChannelGroup, int i, java.lang.String str, boolean z);

    default void logNotificationChannelCreated(@android.annotation.NonNull android.app.NotificationChannel notificationChannel, int i, java.lang.String str) {
        logNotificationChannel(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.getCreated(notificationChannel), notificationChannel, i, str, 0, getLoggingImportance(notificationChannel));
    }

    default void logNotificationChannelDeleted(@android.annotation.NonNull android.app.NotificationChannel notificationChannel, int i, java.lang.String str) {
        logNotificationChannel(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.getDeleted(notificationChannel), notificationChannel, i, str, getLoggingImportance(notificationChannel), 0);
    }

    default void logNotificationChannelModified(@android.annotation.NonNull android.app.NotificationChannel notificationChannel, int i, java.lang.String str, int i2, boolean z) {
        logNotificationChannel(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.getUpdated(z), notificationChannel, i, str, i2, getLoggingImportance(notificationChannel));
    }

    default void logNotificationChannelGroup(@android.annotation.NonNull android.app.NotificationChannelGroup notificationChannelGroup, int i, java.lang.String str, boolean z, boolean z2) {
        logNotificationChannelGroup(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.getGroupUpdated(z), notificationChannelGroup, i, str, z2);
    }

    default void logNotificationChannelGroupDeleted(@android.annotation.NonNull android.app.NotificationChannelGroup notificationChannelGroup, int i, java.lang.String str) {
        logNotificationChannelGroup(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_GROUP_DELETED, notificationChannelGroup, i, str, false);
    }

    default void logAppNotificationsAllowed(int i, java.lang.String str, boolean z) {
        logAppEvent(com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent.getBlocked(z), i, str);
    }

    public enum NotificationChannelEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        NOTIFICATION_CHANNEL_CREATED(219),
        NOTIFICATION_CHANNEL_UPDATED(com.android.server.usb.descriptors.UsbDescriptor.CLASSID_DIAGNOSTIC),
        NOTIFICATION_CHANNEL_UPDATED_BY_USER(221),
        NOTIFICATION_CHANNEL_DELETED(222),
        NOTIFICATION_CHANNEL_GROUP_CREATED(com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED),
        NOTIFICATION_CHANNEL_GROUP_UPDATED(com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS),
        NOTIFICATION_CHANNEL_GROUP_DELETED(226),
        NOTIFICATION_CHANNEL_CONVERSATION_CREATED(272),
        NOTIFICATION_CHANNEL_CONVERSATION_DELETED(274),
        APP_NOTIFICATIONS_BLOCKED(com.android.internal.util.FrameworkStatsLog.BEDTIME_MODE_STATE_CHANGED),
        APP_NOTIFICATIONS_UNBLOCKED(558);

        private final int mId;

        NotificationChannelEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }

        public static com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent getUpdated(boolean z) {
            if (z) {
                return NOTIFICATION_CHANNEL_UPDATED_BY_USER;
            }
            return NOTIFICATION_CHANNEL_UPDATED;
        }

        public static com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent getCreated(@android.annotation.NonNull android.app.NotificationChannel notificationChannel) {
            if (notificationChannel.getConversationId() != null) {
                return NOTIFICATION_CHANNEL_CONVERSATION_CREATED;
            }
            return NOTIFICATION_CHANNEL_CREATED;
        }

        public static com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent getDeleted(@android.annotation.NonNull android.app.NotificationChannel notificationChannel) {
            if (notificationChannel.getConversationId() != null) {
                return NOTIFICATION_CHANNEL_CONVERSATION_DELETED;
            }
            return NOTIFICATION_CHANNEL_DELETED;
        }

        public static com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent getGroupUpdated(boolean z) {
            if (z) {
                return NOTIFICATION_CHANNEL_GROUP_CREATED;
            }
            return NOTIFICATION_CHANNEL_GROUP_DELETED;
        }

        public static com.android.server.notification.NotificationChannelLogger.NotificationChannelEvent getBlocked(boolean z) {
            return z ? APP_NOTIFICATIONS_UNBLOCKED : APP_NOTIFICATIONS_BLOCKED;
        }
    }

    static int getIdHash(@android.annotation.NonNull android.app.NotificationChannel notificationChannel) {
        return com.android.server.notification.SmallHash.hash(notificationChannel.getId());
    }

    static int getConversationIdHash(@android.annotation.NonNull android.app.NotificationChannel notificationChannel) {
        return com.android.server.notification.SmallHash.hash(notificationChannel.getConversationId());
    }

    static int getIdHash(@android.annotation.NonNull android.app.NotificationChannelGroup notificationChannelGroup) {
        return com.android.server.notification.SmallHash.hash(notificationChannelGroup.getId());
    }

    static int getLoggingImportance(@android.annotation.NonNull android.app.NotificationChannel notificationChannel) {
        return getLoggingImportance(notificationChannel, notificationChannel.getImportance());
    }

    static int getLoggingImportance(@android.annotation.NonNull android.app.NotificationChannel notificationChannel, int i) {
        if (notificationChannel.getConversationId() == null || i < 4) {
            return i;
        }
        if (!notificationChannel.isImportantConversation()) {
            return i;
        }
        return 5;
    }

    static int getImportance(@android.annotation.NonNull android.app.NotificationChannelGroup notificationChannelGroup) {
        return getImportance(notificationChannelGroup.isBlocked());
    }

    static int getImportance(boolean z) {
        if (z) {
            return 0;
        }
        return 3;
    }
}
