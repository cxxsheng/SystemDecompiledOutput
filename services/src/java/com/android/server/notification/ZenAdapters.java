package com.android.server.notification;

/* loaded from: classes2.dex */
class ZenAdapters {
    ZenAdapters() {
    }

    static android.service.notification.ZenPolicy notificationPolicyToZenPolicy(android.app.NotificationManager.Policy policy) {
        int i;
        int i2;
        android.service.notification.ZenPolicy.Builder allowAlarms = new android.service.notification.ZenPolicy.Builder().allowAlarms(policy.allowAlarms());
        if (policy.allowCalls()) {
            i = android.service.notification.ZenModeConfig.getZenPolicySenders(policy.allowCallsFrom());
        } else {
            i = 4;
        }
        android.service.notification.ZenPolicy.Builder allowCalls = allowAlarms.allowCalls(i);
        if (policy.allowConversations()) {
            i2 = notificationPolicyConversationSendersToZenPolicy(policy.allowConversationsFrom());
        } else {
            i2 = 3;
        }
        android.service.notification.ZenPolicy.Builder allowSystem = allowCalls.allowConversations(i2).allowEvents(policy.allowEvents()).allowMedia(policy.allowMedia()).allowMessages(policy.allowMessages() ? android.service.notification.ZenModeConfig.getZenPolicySenders(policy.allowMessagesFrom()) : 4).allowReminders(policy.allowReminders()).allowRepeatCallers(policy.allowRepeatCallers()).allowSystem(policy.allowSystem());
        if (policy.suppressedVisualEffects != -1) {
            allowSystem.showBadges(policy.showBadges()).showFullScreenIntent(policy.showFullScreenIntents()).showInAmbientDisplay(policy.showAmbient()).showInNotificationList(policy.showInNotificationList()).showLights(policy.showLights()).showPeeking(policy.showPeeking()).showStatusBarIcons(policy.showStatusBarIcons());
        }
        if (android.app.Flags.modesApi()) {
            allowSystem.allowPriorityChannels(policy.allowPriorityChannels());
        }
        return allowSystem.build();
    }

    private static int notificationPolicyConversationSendersToZenPolicy(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }
}
