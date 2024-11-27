package com.android.server.notification;

/* loaded from: classes2.dex */
class NotificationRecordLoggerImpl implements com.android.server.notification.NotificationRecordLogger {
    private com.android.internal.logging.UiEventLogger mUiEventLogger = new com.android.internal.logging.UiEventLoggerImpl();

    NotificationRecordLoggerImpl() {
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void logNotificationPosted(com.android.server.notification.NotificationRecordLogger.NotificationReported notificationReported) {
        writeNotificationReportedAtom(notificationReported);
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void logNotificationAdjusted(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, int i, int i2, com.android.internal.logging.InstanceId instanceId) {
        writeNotificationReportedAtom(new com.android.server.notification.NotificationRecordLogger.NotificationReported(new com.android.server.notification.NotificationRecordLogger.NotificationRecordPair(notificationRecord, null), com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent.NOTIFICATION_ADJUSTED, i, i2, instanceId));
    }

    private void writeNotificationReportedAtom(com.android.server.notification.NotificationRecordLogger.NotificationReported notificationReported) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.NOTIFICATION_REPORTED, notificationReported.event_id, notificationReported.uid, notificationReported.package_name, notificationReported.instance_id, notificationReported.notification_id_hash, notificationReported.channel_id_hash, notificationReported.group_id_hash, notificationReported.group_instance_id, notificationReported.is_group_summary, notificationReported.category, notificationReported.style, notificationReported.num_people, notificationReported.position, notificationReported.importance, notificationReported.alerting, notificationReported.importance_source, notificationReported.importance_initial, notificationReported.importance_initial_source, notificationReported.importance_asst, notificationReported.assistant_hash, notificationReported.assistant_ranking_score, notificationReported.is_ongoing, notificationReported.is_foreground_service, notificationReported.timeout_millis, notificationReported.is_non_dismissible, notificationReported.post_duration_millis, notificationReported.fsi_state, notificationReported.is_locked, notificationReported.age_in_minutes);
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null) {
            return;
        }
        this.mUiEventLogger.logWithInstanceId(uiEventEnum, notificationRecord.getUid(), notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getInstanceId());
    }

    @Override // com.android.server.notification.NotificationRecordLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum) {
        this.mUiEventLogger.log(uiEventEnum);
    }
}
