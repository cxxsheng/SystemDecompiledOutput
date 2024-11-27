package com.android.server.notification;

/* loaded from: classes2.dex */
interface NotificationRecordLogger {
    public static final java.lang.String TAG = "NotificationRecordLogger";

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum);

    void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, com.android.server.notification.NotificationRecord notificationRecord);

    void logNotificationAdjusted(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, int i, int i2, com.android.internal.logging.InstanceId instanceId);

    void logNotificationPosted(com.android.server.notification.NotificationRecordLogger.NotificationReported notificationReported);

    @android.annotation.Nullable
    default com.android.server.notification.NotificationRecordLogger.NotificationReported prepareToLogNotificationPosted(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, @android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord2, int i, int i2, com.android.internal.logging.InstanceId instanceId) {
        com.android.server.notification.NotificationRecordLogger.NotificationRecordPair notificationRecordPair = new com.android.server.notification.NotificationRecordLogger.NotificationRecordPair(notificationRecord, notificationRecord2);
        if (!notificationRecordPair.shouldLogReported(i2)) {
            return null;
        }
        return new com.android.server.notification.NotificationRecordLogger.NotificationReported(notificationRecordPair, com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent.fromRecordPair(notificationRecordPair), i, i2, instanceId);
    }

    default void logNotificationCancelled(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, int i, int i2) {
        log(com.android.server.notification.NotificationRecordLogger.NotificationCancelledEvent.fromCancelReason(i, i2), notificationRecord);
    }

    default void logNotificationVisibility(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.fromVisibility(z), notificationRecord);
    }

    public enum NotificationReportedEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        NOTIFICATION_POSTED(162),
        NOTIFICATION_UPDATED(163),
        NOTIFICATION_ADJUSTED(908);

        private final int mId;

        NotificationReportedEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }

        public static com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent fromRecordPair(com.android.server.notification.NotificationRecordLogger.NotificationRecordPair notificationRecordPair) {
            return notificationRecordPair.old != null ? NOTIFICATION_UPDATED : NOTIFICATION_POSTED;
        }
    }

    public enum NotificationCancelledEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        INVALID(0),
        NOTIFICATION_CANCEL_CLICK(164),
        NOTIFICATION_CANCEL_USER_OTHER(165),
        NOTIFICATION_CANCEL_USER_CANCEL_ALL(166),
        NOTIFICATION_CANCEL_ERROR(167),
        NOTIFICATION_CANCEL_PACKAGE_CHANGED(168),
        NOTIFICATION_CANCEL_USER_STOPPED(169),
        NOTIFICATION_CANCEL_PACKAGE_BANNED(170),
        NOTIFICATION_CANCEL_APP_CANCEL(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT),
        NOTIFICATION_CANCEL_APP_CANCEL_ALL(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED),
        NOTIFICATION_CANCEL_LISTENER_CANCEL(173),
        NOTIFICATION_CANCEL_LISTENER_CANCEL_ALL(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_EMPTY_STATE_QUIET_MODE),
        NOTIFICATION_CANCEL_GROUP_SUMMARY_CANCELED(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP),
        NOTIFICATION_CANCEL_GROUP_OPTIMIZATION(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT),
        NOTIFICATION_CANCEL_PACKAGE_SUSPENDED(177),
        NOTIFICATION_CANCEL_PROFILE_TURNED_OFF(178),
        NOTIFICATION_CANCEL_UNAUTOBUNDLED(179),
        NOTIFICATION_CANCEL_CHANNEL_BANNED(180),
        NOTIFICATION_CANCEL_SNOOZED(181),
        NOTIFICATION_CANCEL_TIMEOUT(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REQUEST_FAILED),
        NOTIFICATION_CANCEL_CHANNEL_REMOVED(1261),
        NOTIFICATION_CANCEL_CLEAR_DATA(1262),
        NOTIFICATION_CANCEL_USER_PEEK(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_COPY_ACCOUNT_MS),
        NOTIFICATION_CANCEL_USER_AOD(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_CREATE_PROFILE_MS),
        NOTIFICATION_CANCEL_USER_BUBBLE(1228),
        NOTIFICATION_CANCEL_USER_LOCKSCREEN(193),
        NOTIFICATION_CANCEL_USER_SHADE(192),
        NOTIFICATION_CANCEL_ASSISTANT(906);

        private final int mId;

        NotificationCancelledEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }

        public static com.android.server.notification.NotificationRecordLogger.NotificationCancelledEvent fromCancelReason(int i, int i2) {
            if (i2 == -1) {
                android.util.Log.wtf(com.android.server.notification.NotificationRecordLogger.TAG, "Unexpected surface: " + i2 + " with reason " + i);
                return INVALID;
            }
            if (i == 2) {
                switch (i2) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        android.util.Log.wtf(com.android.server.notification.NotificationRecordLogger.TAG, "Unexpected surface: " + i2 + " with reason " + i);
                        break;
                }
                return INVALID;
            }
            if (1 <= i && i <= 21) {
                return values()[i];
            }
            if (i == 22) {
                return NOTIFICATION_CANCEL_ASSISTANT;
            }
            android.util.Log.wtf(com.android.server.notification.NotificationRecordLogger.TAG, "Unexpected reason: " + i + " with surface " + i2);
            return INVALID;
        }
    }

    public enum NotificationEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        NOTIFICATION_OPEN(197),
        NOTIFICATION_CLOSE(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_USB_DATA_SIGNALING),
        NOTIFICATION_SNOOZED(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK),
        NOTIFICATION_NOT_POSTED_SNOOZED(319),
        NOTIFICATION_CLICKED(320),
        NOTIFICATION_ACTION_CLICKED(321),
        NOTIFICATION_DETAIL_OPEN_SYSTEM(com.android.internal.util.FrameworkStatsLog.TIF_TUNE_CHANGED),
        NOTIFICATION_DETAIL_CLOSE_SYSTEM(com.android.internal.util.FrameworkStatsLog.AUTO_ROTATE_REPORTED),
        NOTIFICATION_DETAIL_OPEN_USER(329),
        NOTIFICATION_DETAIL_CLOSE_USER(330),
        NOTIFICATION_DIRECT_REPLIED(331),
        NOTIFICATION_SMART_REPLIED(com.android.internal.art.ArtStatsLog.ART_DATUM_REPORTED),
        NOTIFICATION_SMART_REPLY_VISIBLE(com.android.internal.util.FrameworkStatsLog.DEVICE_ROTATED),
        NOTIFICATION_ACTION_CLICKED_0(450),
        NOTIFICATION_ACTION_CLICKED_1(com.android.internal.util.FrameworkStatsLog.CDM_ASSOCIATION_ACTION),
        NOTIFICATION_ACTION_CLICKED_2(com.android.internal.util.FrameworkStatsLog.MAGNIFICATION_TRIPLE_TAP_AND_HOLD_ACTIVATED_SESSION_REPORTED),
        NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_0(com.android.internal.util.FrameworkStatsLog.MAGNIFICATION_FOLLOW_TYPING_FOCUS_ACTIVATED_SESSION_REPORTED),
        NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_1(454),
        NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_2(455),
        NOTIFICATION_ASSIST_ACTION_CLICKED_0(456),
        NOTIFICATION_ASSIST_ACTION_CLICKED_1(com.android.internal.art.ArtStatsLog.ISOLATED_COMPILATION_SCHEDULED),
        NOTIFICATION_ASSIST_ACTION_CLICKED_2(com.android.internal.art.ArtStatsLog.ISOLATED_COMPILATION_ENDED);

        private final int mId;

        NotificationEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }

        public static com.android.server.notification.NotificationRecordLogger.NotificationEvent fromVisibility(boolean z) {
            return z ? NOTIFICATION_OPEN : NOTIFICATION_CLOSE;
        }

        public static com.android.server.notification.NotificationRecordLogger.NotificationEvent fromExpanded(boolean z, boolean z2) {
            return z2 ? z ? NOTIFICATION_DETAIL_OPEN_USER : NOTIFICATION_DETAIL_CLOSE_USER : z ? NOTIFICATION_DETAIL_OPEN_SYSTEM : NOTIFICATION_DETAIL_CLOSE_SYSTEM;
        }

        public static com.android.server.notification.NotificationRecordLogger.NotificationEvent fromAction(int i, boolean z, boolean z2) {
            if (i < 0 || i > 2) {
                return NOTIFICATION_ACTION_CLICKED;
            }
            if (z) {
                return values()[NOTIFICATION_ASSIST_ACTION_CLICKED_0.ordinal() + i];
            }
            if (z2) {
                return values()[NOTIFICATION_CONTEXTUAL_ACTION_CLICKED_0.ordinal() + i];
            }
            return values()[NOTIFICATION_ACTION_CLICKED_0.ordinal() + i];
        }
    }

    public enum NotificationPanelEvent implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        NOTIFICATION_PANEL_OPEN(com.android.internal.util.FrameworkStatsLog.APP_PROCESS_DIED__IMPORTANCE__IMPORTANCE_TOP_SLEEPING),
        NOTIFICATION_PANEL_CLOSE(326);

        private final int mId;

        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public static class NotificationRecordPair {
        public final com.android.server.notification.NotificationRecord old;
        public final com.android.server.notification.NotificationRecord r;

        NotificationRecordPair(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, @android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord2) {
            this.r = notificationRecord;
            this.old = notificationRecord2;
        }

        boolean shouldLogReported(int i) {
            if (this.r == null) {
                return false;
            }
            if (this.old == null || i > 0) {
                return true;
            }
            return (java.util.Objects.equals(this.r.getSbn().getChannelIdLogTag(), this.old.getSbn().getChannelIdLogTag()) && java.util.Objects.equals(this.r.getSbn().getGroupLogTag(), this.old.getSbn().getGroupLogTag()) && this.r.getSbn().getNotification().isGroupSummary() == this.old.getSbn().getNotification().isGroupSummary() && java.util.Objects.equals(this.r.getSbn().getNotification().category, this.old.getSbn().getNotification().category) && this.r.getImportance() == this.old.getImportance() && com.android.server.notification.NotificationRecordLogger.getLoggingImportance(this.r) == com.android.server.notification.NotificationRecordLogger.getLoggingImportance(this.old) && this.r.rankingScoreMatches(this.old.getRankingScore())) ? false : true;
        }

        public int getStyle() {
            return getStyle(this.r.getSbn().getNotification().extras);
        }

        private int getStyle(@android.annotation.Nullable android.os.Bundle bundle) {
            java.lang.String string;
            if (bundle != null && (string = bundle.getString("android.template")) != null && !string.isEmpty()) {
                return string.hashCode();
            }
            return 0;
        }

        int getNumPeople() {
            return getNumPeople(this.r.getSbn().getNotification().extras);
        }

        private int getNumPeople(@android.annotation.Nullable android.os.Bundle bundle) {
            java.util.ArrayList parcelableArrayList;
            if (bundle != null && (parcelableArrayList = bundle.getParcelableArrayList("android.people.list", android.app.Person.class)) != null && !parcelableArrayList.isEmpty()) {
                return parcelableArrayList.size();
            }
            return 0;
        }

        int getAssistantHash() {
            java.lang.String adjustmentIssuer = this.r.getAdjustmentIssuer();
            if (adjustmentIssuer == null) {
                return 0;
            }
            return adjustmentIssuer.hashCode();
        }

        int getInstanceId() {
            if (this.r.getSbn().getInstanceId() == null) {
                return 0;
            }
            return this.r.getSbn().getInstanceId().getId();
        }

        int getNotificationIdHash() {
            return com.android.server.notification.SmallHash.hash(java.util.Objects.hashCode(this.r.getSbn().getTag()) ^ this.r.getSbn().getId());
        }

        int getChannelIdHash() {
            return com.android.server.notification.SmallHash.hash(this.r.getSbn().getNotification().getChannelId());
        }

        int getGroupIdHash() {
            return com.android.server.notification.SmallHash.hash(this.r.getSbn().getGroup());
        }
    }

    public static class NotificationReported {
        final int age_in_minutes;
        final int alerting;
        final int assistant_hash;
        final float assistant_ranking_score;
        final java.lang.String category;
        final int channel_id_hash;
        final int event_id;
        final int fsi_state;
        final int group_id_hash;
        final int group_instance_id;
        final int importance;
        final int importance_asst;
        final int importance_initial;
        final int importance_initial_source;
        final int importance_source;
        final int instance_id;
        final boolean is_foreground_service;
        final boolean is_group_summary;
        final boolean is_locked;
        final boolean is_non_dismissible;
        final boolean is_ongoing;
        final int notification_id_hash;
        final int num_people;
        final java.lang.String package_name;
        final int position;
        long post_duration_millis;
        final int style;
        final long timeout_millis;
        final int uid;

        NotificationReported(com.android.server.notification.NotificationRecordLogger.NotificationRecordPair notificationRecordPair, com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent notificationReportedEvent, int i, int i2, com.android.internal.logging.InstanceId instanceId) {
            this.event_id = notificationReportedEvent.getId();
            this.uid = notificationRecordPair.r.getUid();
            this.package_name = notificationRecordPair.r.getSbn().getPackageName();
            this.instance_id = notificationRecordPair.getInstanceId();
            this.notification_id_hash = notificationRecordPair.getNotificationIdHash();
            this.channel_id_hash = notificationRecordPair.getChannelIdHash();
            this.group_id_hash = notificationRecordPair.getGroupIdHash();
            this.group_instance_id = instanceId == null ? 0 : instanceId.getId();
            this.is_group_summary = notificationRecordPair.r.getSbn().getNotification().isGroupSummary();
            this.category = notificationRecordPair.r.getSbn().getNotification().category;
            this.style = notificationRecordPair.getStyle();
            this.num_people = notificationRecordPair.getNumPeople();
            this.position = i;
            this.importance = com.android.server.notification.NotificationRecordLogger.getLoggingImportance(notificationRecordPair.r);
            this.alerting = i2;
            this.importance_source = notificationRecordPair.r.getImportanceExplanationCode();
            this.importance_initial = notificationRecordPair.r.getInitialImportance();
            this.importance_initial_source = notificationRecordPair.r.getInitialImportanceExplanationCode();
            this.importance_asst = notificationRecordPair.r.getAssistantImportance();
            this.assistant_hash = notificationRecordPair.getAssistantHash();
            this.assistant_ranking_score = notificationRecordPair.r.getRankingScore();
            this.is_ongoing = notificationRecordPair.r.getSbn().isOngoing();
            this.is_foreground_service = com.android.server.notification.NotificationRecordLogger.isForegroundService(notificationRecordPair.r);
            this.timeout_millis = notificationRecordPair.r.getSbn().getNotification().getTimeoutAfter();
            this.is_non_dismissible = com.android.server.notification.NotificationRecordLogger.isNonDismissible(notificationRecordPair.r);
            this.fsi_state = com.android.server.notification.NotificationRecordLogger.getFsiState(notificationRecordPair.r.getSbn().getNotification().fullScreenIntent != null, (notificationRecordPair.r.getSbn().getNotification().flags & 16384) != 0, notificationReportedEvent);
            this.is_locked = notificationRecordPair.r.isLocked();
            this.age_in_minutes = com.android.server.notification.NotificationRecordLogger.getAgeInMinutes(notificationRecordPair.r.getSbn().getPostTime(), notificationRecordPair.r.getSbn().getNotification().when);
        }
    }

    static int getLoggingImportance(@android.annotation.NonNull com.android.server.notification.NotificationRecord notificationRecord) {
        int importance = notificationRecord.getImportance();
        android.app.NotificationChannel channel = notificationRecord.getChannel();
        if (channel == null) {
            return importance;
        }
        return com.android.server.notification.NotificationChannelLogger.getLoggingImportance(channel, importance);
    }

    static boolean isForegroundService(@android.annotation.NonNull com.android.server.notification.NotificationRecord notificationRecord) {
        return (notificationRecord.getSbn() == null || notificationRecord.getSbn().getNotification() == null || (notificationRecord.getSbn().getNotification().flags & 64) == 0) ? false : true;
    }

    static boolean isNonDismissible(@android.annotation.NonNull com.android.server.notification.NotificationRecord notificationRecord) {
        return (notificationRecord.getSbn() == null || notificationRecord.getSbn().getNotification() == null || (notificationRecord.getNotification().flags & 8192) == 0) ? false : true;
    }

    static int getFsiState(boolean z, boolean z2, com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent notificationReportedEvent) {
        if (notificationReportedEvent == com.android.server.notification.NotificationRecordLogger.NotificationReportedEvent.NOTIFICATION_UPDATED) {
            return 0;
        }
        if (z) {
            return 1;
        }
        return z2 ? 2 : 0;
    }

    static int getAgeInMinutes(long j, long j2) {
        return (int) java.time.Duration.ofMillis(j - j2).toMinutes();
    }
}
