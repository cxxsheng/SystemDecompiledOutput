package com.android.server.notification;

/* loaded from: classes2.dex */
public class BubbleExtractor implements com.android.server.notification.NotificationSignalExtractor {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "BubbleExtractor";
    private android.app.ActivityManager mActivityManager;
    private com.android.server.notification.RankingConfig mConfig;
    private android.content.Context mContext;
    private com.android.server.notification.ShortcutHelper mShortcutHelper;
    boolean mSupportsBubble;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        this.mContext = context;
        this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        this.mSupportsBubble = android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_supportSpeakerNearUltrasound);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null || this.mConfig == null || this.mShortcutHelper == null) {
            return null;
        }
        boolean z = false;
        boolean z2 = (!canPresentAsBubble(notificationRecord) || this.mActivityManager.isLowRamDevice() || !notificationRecord.isConversation() || notificationRecord.getShortcutInfo() == null || notificationRecord.getNotification().isFgsOrUij()) ? false : true;
        boolean bubblesEnabled = this.mConfig.bubblesEnabled(notificationRecord.getUser());
        int bubblePreference = this.mConfig.getBubblePreference(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getUid());
        android.app.NotificationChannel channel = notificationRecord.getChannel();
        if (!bubblesEnabled || bubblePreference == 0 || !z2) {
            notificationRecord.setAllowBubble(false);
            if (!z2) {
                notificationRecord.getNotification().setBubbleMetadata(null);
            }
        } else if (channel == null) {
            notificationRecord.setAllowBubble(true);
        } else if (bubblePreference == 1) {
            notificationRecord.setAllowBubble(channel.getAllowBubbles() != 0);
        } else if (bubblePreference == 2) {
            notificationRecord.setAllowBubble(channel.canBubble());
        }
        if (notificationRecord.canBubble() && !notificationRecord.isFlagBubbleRemoved()) {
            z = true;
        }
        if (z) {
            notificationRecord.getNotification().flags |= 4096;
        } else {
            notificationRecord.getNotification().flags &= -4097;
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
    }

    public void setShortcutHelper(com.android.server.notification.ShortcutHelper shortcutHelper) {
        this.mShortcutHelper = shortcutHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setActivityManager(android.app.ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean canPresentAsBubble(com.android.server.notification.NotificationRecord notificationRecord) {
        java.lang.String str;
        boolean z;
        if (!this.mSupportsBubble) {
            return false;
        }
        android.app.Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        if (bubbleMetadata == null) {
            return false;
        }
        java.lang.String shortcutId = bubbleMetadata.getShortcutId();
        if (notificationRecord.getShortcutInfo() != null) {
            str = notificationRecord.getShortcutInfo().getId();
        } else {
            str = null;
        }
        if (str != null && shortcutId != null) {
            z = shortcutId.equals(str);
        } else if (shortcutId == null) {
            z = false;
        } else {
            z = this.mShortcutHelper.getValidShortcutInfo(shortcutId, packageName, notificationRecord.getUser()) != null;
        }
        if (bubbleMetadata.getIntent() == null && !z) {
            logBubbleError(notificationRecord.getKey(), "couldn't find valid shortcut for bubble with shortcutId: " + shortcutId);
            return false;
        }
        if (z) {
            return true;
        }
        return canLaunchInTaskView(this.mContext, bubbleMetadata.getIntent(), packageName);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean canLaunchInTaskView(android.content.Context context, android.app.PendingIntent pendingIntent, java.lang.String str) {
        android.content.pm.ActivityInfo activityInfo;
        if (pendingIntent == null) {
            android.util.Slog.w(TAG, "Unable to create bubble -- no intent");
            return false;
        }
        android.content.Intent intent = pendingIntent.getIntent();
        if (intent != null) {
            activityInfo = intent.resolveActivityInfo(context.getPackageManager(), 0);
        } else {
            activityInfo = null;
        }
        if (activityInfo == null) {
            com.android.internal.util.FrameworkStatsLog.write(173, str, 1);
            android.util.Slog.w(TAG, "Unable to send as bubble -- couldn't find activity info for intent: " + intent);
            return false;
        }
        if (android.content.pm.ActivityInfo.isResizeableMode(activityInfo.resizeMode)) {
            return true;
        }
        com.android.internal.util.FrameworkStatsLog.write(173, str, 2);
        android.util.Slog.w(TAG, "Unable to send as bubble -- activity is not resizable for intent: " + intent);
        return false;
    }

    private void logBubbleError(java.lang.String str, java.lang.String str2) {
    }
}
