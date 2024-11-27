package android.app;

/* loaded from: classes.dex */
public class NotificationManager {
    public static final java.lang.String ACTION_APP_BLOCK_STATE_CHANGED = "android.app.action.APP_BLOCK_STATE_CHANGED";
    public static final java.lang.String ACTION_AUTOMATIC_ZEN_RULE = "android.app.action.AUTOMATIC_ZEN_RULE";
    public static final java.lang.String ACTION_AUTOMATIC_ZEN_RULE_STATUS_CHANGED = "android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CLOSE_NOTIFICATION_HANDLER_PANEL = "android.app.action.CLOSE_NOTIFICATION_HANDLER_PANEL";
    public static final java.lang.String ACTION_CONSOLIDATED_NOTIFICATION_POLICY_CHANGED = "android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED";
    public static final java.lang.String ACTION_EFFECTS_SUPPRESSOR_CHANGED = "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED";
    public static final java.lang.String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
    public static final java.lang.String ACTION_INTERRUPTION_FILTER_CHANGED_INTERNAL = "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL";
    public static final java.lang.String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED";
    public static final java.lang.String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String ACTION_NOTIFICATION_LISTENER_ENABLED_CHANGED = "android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED";
    public static final java.lang.String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
    public static final java.lang.String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_OPEN_NOTIFICATION_HANDLER_PANEL = "android.app.action.OPEN_NOTIFICATION_HANDLER_PANEL";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_TOGGLE_NOTIFICATION_HANDLER_PANEL = "android.app.action.TOGGLE_NOTIFICATION_HANDLER_PANEL";
    public static final int AUTOMATIC_RULE_STATUS_ACTIVATED = 4;
    public static final int AUTOMATIC_RULE_STATUS_DEACTIVATED = 5;
    public static final int AUTOMATIC_RULE_STATUS_DISABLED = 2;
    public static final int AUTOMATIC_RULE_STATUS_ENABLED = 1;
    public static final int AUTOMATIC_RULE_STATUS_REMOVED = 3;
    public static final int AUTOMATIC_RULE_STATUS_UNKNOWN = -1;
    public static final int BUBBLE_PREFERENCE_ALL = 1;
    public static final int BUBBLE_PREFERENCE_NONE = 0;
    public static final int BUBBLE_PREFERENCE_SELECTED = 2;
    public static final java.lang.String EXTRA_AUTOMATIC_RULE_ID = "android.app.extra.AUTOMATIC_RULE_ID";
    public static final java.lang.String EXTRA_AUTOMATIC_ZEN_RULE_ID = "android.app.extra.AUTOMATIC_ZEN_RULE_ID";
    public static final java.lang.String EXTRA_AUTOMATIC_ZEN_RULE_STATUS = "android.app.extra.AUTOMATIC_ZEN_RULE_STATUS";
    public static final java.lang.String EXTRA_BLOCKED_STATE = "android.app.extra.BLOCKED_STATE";
    public static final java.lang.String EXTRA_NOTIFICATION_CHANNEL_GROUP_ID = "android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID";
    public static final java.lang.String EXTRA_NOTIFICATION_CHANNEL_ID = "android.app.extra.NOTIFICATION_CHANNEL_ID";
    public static final java.lang.String EXTRA_NOTIFICATION_POLICY = "android.app.extra.NOTIFICATION_POLICY";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final java.lang.String META_DATA_AUTOMATIC_RULE_TYPE = "android.service.zen.automatic.ruleType";
    public static final java.lang.String META_DATA_RULE_INSTANCE_LIMIT = "android.service.zen.automatic.ruleInstanceLimit";
    public static final long SET_LISTENER_ACCESS_GRANTED_IS_USER_AWARE = 302563478;
    public static final int VISIBILITY_NO_OVERRIDE = -1000;
    private static android.app.INotificationManager sService;
    private final java.util.Map<android.app.NotificationManager.CallNotificationEventListener, android.app.NotificationManager.CallNotificationEventCallbackStub> mCallNotificationEventCallbacks = new java.util.HashMap();
    private android.content.Context mContext;
    private static java.lang.String TAG = "NotificationManager";
    private static boolean localLOGV = false;
    public static int MAX_SERVICE_COMPONENT_NAME_LENGTH = 500;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutomaticZenRuleStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BubblePreference {
    }

    @android.annotation.SystemApi
    public interface CallNotificationEventListener {
        void onCallNotificationPosted(java.lang.String str, android.os.UserHandle userHandle);

        void onCallNotificationRemoved(java.lang.String str, android.os.UserHandle userHandle);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Importance {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InterruptionFilter {
    }

    public static android.app.INotificationManager getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
        return sService;
    }

    NotificationManager(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
    }

    public static android.app.NotificationManager from(android.content.Context context) {
        return (android.app.NotificationManager) context.getSystemService("notification");
    }

    public void notify(int i, android.app.Notification notification) {
        notify(null, i, notification);
    }

    public void notify(java.lang.String str, int i, android.app.Notification notification) {
        notifyAsUser(str, i, notification, this.mContext.getUser());
    }

    public void notifyAsPackage(java.lang.String str, java.lang.String str2, int i, android.app.Notification notification) {
        android.app.INotificationManager service = getService();
        java.lang.String packageName = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                android.util.Log.v(TAG, packageName + ": notify(" + i + ", " + notification + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            service.enqueueNotificationWithTag(str, packageName, str2, i, fixNotification(notification), this.mContext.getUser().getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAsUser(java.lang.String str, int i, android.app.Notification notification, android.os.UserHandle userHandle) {
        android.app.INotificationManager service = getService();
        java.lang.String packageName = this.mContext.getPackageName();
        try {
            if (localLOGV) {
                android.util.Log.v(TAG, packageName + ": notify(" + i + ", " + notification + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            service.enqueueNotificationWithTag(packageName, this.mContext.getOpPackageName(), str, i, fixNotification(notification), userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.app.Notification fixNotification(android.app.Notification notification) {
        java.lang.String packageName = this.mContext.getPackageName();
        android.app.Notification.addFieldsFromContext(this.mContext, notification);
        if (notification.sound != null) {
            notification.sound = notification.sound.getCanonicalUri();
            if (android.os.StrictMode.vmFileUriExposureEnabled()) {
                notification.sound.checkFileUriExposed("Notification.sound");
            }
        }
        fixLegacySmallIcon(notification, packageName);
        if (this.mContext.getApplicationInfo().targetSdkVersion > 22 && notification.getSmallIcon() == null) {
            throw new java.lang.IllegalArgumentException("Invalid notification (no valid small icon): " + notification);
        }
        notification.reduceImageSizes(this.mContext);
        return android.app.Notification.Builder.maybeCloneStrippedForDelivery(notification);
    }

    private void fixLegacySmallIcon(android.app.Notification notification, java.lang.String str) {
        if (notification.getSmallIcon() == null && notification.icon != 0) {
            notification.setSmallIcon(android.graphics.drawable.Icon.createWithResource(str, notification.icon));
        }
    }

    public void cancel(int i) {
        cancel(null, i);
    }

    public void cancel(java.lang.String str, int i) {
        cancelAsUser(str, i, this.mContext.getUser());
    }

    public void cancelAsPackage(java.lang.String str, java.lang.String str2, int i) {
        try {
            getService().cancelNotificationWithTag(str, this.mContext.getOpPackageName(), str2, i, this.mContext.getUser().getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelAsUser(java.lang.String str, int i, android.os.UserHandle userHandle) {
        android.app.INotificationManager service = getService();
        java.lang.String packageName = this.mContext.getPackageName();
        if (localLOGV) {
            android.util.Log.v(TAG, packageName + ": cancel(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        try {
            service.cancelNotificationWithTag(packageName, this.mContext.getOpPackageName(), str, i, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelAll() {
        android.app.INotificationManager service = getService();
        java.lang.String packageName = this.mContext.getPackageName();
        if (localLOGV) {
            android.util.Log.v(TAG, packageName + ": cancelAll()");
        }
        try {
            service.cancelAllNotifications(packageName, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationDelegate(java.lang.String str) {
        android.app.INotificationManager service = getService();
        java.lang.String packageName = this.mContext.getPackageName();
        if (localLOGV) {
            android.util.Log.v(TAG, packageName + ": cancelAll()");
        }
        try {
            service.setNotificationDelegate(packageName, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getNotificationDelegate() {
        try {
            return getService().getNotificationDelegate(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canNotifyAsPackage(java.lang.String str) {
        try {
            return getService().canNotifyAsPackage(this.mContext.getPackageName(), str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canUseFullScreenIntent() {
        try {
            return getService().canUseFullScreenIntent(this.mContext.getAttributionSource());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannelGroup(android.app.NotificationChannelGroup notificationChannelGroup) {
        createNotificationChannelGroups(java.util.Arrays.asList(notificationChannelGroup));
    }

    public void createNotificationChannelGroups(java.util.List<android.app.NotificationChannelGroup> list) {
        try {
            getService().createNotificationChannelGroups(this.mContext.getPackageName(), new android.content.pm.ParceledListSlice(list));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createNotificationChannel(android.app.NotificationChannel notificationChannel) {
        createNotificationChannels(java.util.Arrays.asList(notificationChannel));
    }

    public void createNotificationChannels(java.util.List<android.app.NotificationChannel> list) {
        try {
            getService().createNotificationChannels(this.mContext.getPackageName(), new android.content.pm.ParceledListSlice(list));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.NotificationChannel getNotificationChannel(java.lang.String str) {
        try {
            return getService().getNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.NotificationChannel getNotificationChannel(java.lang.String str, java.lang.String str2) {
        try {
            return getService().getConversationNotificationChannel(this.mContext.getOpPackageName(), this.mContext.getUserId(), this.mContext.getPackageName(), str, true, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.NotificationChannel> getNotificationChannels() {
        try {
            return getService().getNotificationChannels(this.mContext.getOpPackageName(), this.mContext.getPackageName(), this.mContext.getUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannel(java.lang.String str) {
        try {
            getService().deleteNotificationChannel(this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str) {
        try {
            return getService().getNotificationChannelGroup(this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.NotificationChannelGroup> getNotificationChannelGroups() {
        try {
            android.content.pm.ParceledListSlice notificationChannelGroups = getService().getNotificationChannelGroups(this.mContext.getPackageName());
            if (notificationChannelGroups != null) {
                return notificationChannelGroups.getList();
            }
            return new java.util.ArrayList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteNotificationChannelGroup(java.lang.String str) {
        try {
            getService().deleteNotificationChannelGroup(this.mContext.getPackageName(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateNotificationChannel(java.lang.String str, int i, android.app.NotificationChannel notificationChannel) {
        try {
            getService().updateNotificationChannelForPackage(str, i, notificationChannel);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.ComponentName getEffectsSuppressor() {
        try {
            return getService().getEffectsSuppressor();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(android.os.Bundle bundle) {
        try {
            return getService().matchesCallFilter(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cleanUpCallersAfter(long j) {
        try {
            getService().cleanUpCallersAfter(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSystemConditionProviderEnabled(java.lang.String str) {
        try {
            return getService().isSystemConditionProviderEnabled(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setZenMode(int i, android.net.Uri uri, java.lang.String str) {
        setZenMode(i, uri, str, false);
    }

    public void setZenMode(int i, android.net.Uri uri, java.lang.String str, boolean z) {
        try {
            getService().setZenMode(i, uri, str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getZenMode() {
        try {
            return getService().getZenMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.service.notification.ZenModeConfig getZenModeConfig() {
        try {
            return getService().getZenModeConfig();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() {
        try {
            return getService().getConsolidatedNotificationPolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRuleInstanceCount(android.content.ComponentName componentName) {
        try {
            return getService().getRuleInstanceCount(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areAutomaticZenRulesUserManaged() {
        return android.app.Flags.modesApi() && android.app.Flags.modesUi();
    }

    public java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() {
        android.app.INotificationManager service = getService();
        try {
            if (android.app.Flags.modesApi()) {
                return service.getAutomaticZenRules();
            }
            java.util.List<android.service.notification.ZenModeConfig.ZenRule> zenRules = service.getZenRules();
            java.util.HashMap hashMap = new java.util.HashMap();
            for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenRules) {
                android.app.AutomaticZenRule automaticZenRule = new android.app.AutomaticZenRule(zenRule.name, zenRule.component, zenRule.configurationActivity, zenRule.conditionId, zenRule.zenPolicy, zenModeToInterruptionFilter(zenRule.zenMode), zenRule.enabled, zenRule.creationTime);
                automaticZenRule.setPackageName(zenRule.pkg);
                hashMap.put(zenRule.id, automaticZenRule);
            }
            return hashMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) {
        try {
            return getService().getAutomaticZenRule(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule) {
        return addAutomaticZenRule(automaticZenRule, false);
    }

    public java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule, boolean z) {
        try {
            return getService().addAutomaticZenRule(automaticZenRule, this.mContext.getPackageName(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule) {
        return updateAutomaticZenRule(str, automaticZenRule, false);
    }

    public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, boolean z) {
        try {
            return getService().updateAutomaticZenRule(str, automaticZenRule, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAutomaticZenRuleState(java.lang.String str) {
        try {
            return getService().getAutomaticZenRuleState(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition) {
        try {
            getService().setAutomaticZenRuleState(str, condition);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRule(java.lang.String str) {
        return removeAutomaticZenRule(str, false);
    }

    public boolean removeAutomaticZenRule(java.lang.String str, boolean z) {
        try {
            return getService().removeAutomaticZenRule(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAutomaticZenRules(java.lang.String str) {
        return removeAutomaticZenRules(str, false);
    }

    public boolean removeAutomaticZenRules(java.lang.String str, boolean z) {
        try {
            return getService().removeAutomaticZenRules(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getImportance() {
        try {
            return getService().getPackageImportance(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsEnabled() {
        try {
            return getService().areNotificationsEnabled(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean areBubblesAllowed() {
        try {
            return getService().areBubblesAllowed(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areBubblesEnabled() {
        try {
            return getService().areBubblesEnabled(this.mContext.getUser());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBubblePreference() {
        try {
            return getService().getBubblePreferenceForPackage(this.mContext.getPackageName(), android.os.Binder.getCallingUid());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void silenceNotificationSound() {
        try {
            getService().silenceNotificationSound();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areNotificationsPaused() {
        try {
            return getService().isPackagePaused(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGranted() {
        try {
            return getService().isNotificationPolicyAccessGranted(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerAccessGranted(android.content.ComponentName componentName) {
        try {
            return getService().isNotificationListenerAccessGranted(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isNotificationAssistantAccessGranted(android.content.ComponentName componentName) {
        try {
            return getService().isNotificationAssistantAccessGranted(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideSilentStatusBarIcons() {
        try {
            return getService().shouldHideSilentStatusIcons(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getAllowedAssistantAdjustments() {
        try {
            return getService().getAllowedAssistantAdjustments(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationPolicyAccessGrantedForPackage(java.lang.String str) {
        try {
            return getService().isNotificationPolicyAccessGrantedForPackage(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getEnabledNotificationListenerPackages() {
        try {
            return getService().getEnabledNotificationListenerPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.NotificationManager.Policy getNotificationPolicy() {
        try {
            return getService().getNotificationPolicy(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicy(android.app.NotificationManager.Policy policy) {
        setNotificationPolicy(policy, false);
    }

    public void setNotificationPolicy(android.app.NotificationManager.Policy policy, boolean z) {
        checkRequired("policy", policy);
        try {
            getService().setNotificationPolicy(this.mContext.getOpPackageName(), policy, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationPolicyAccessGranted(java.lang.String str, boolean z) {
        try {
            getService().setNotificationPolicyAccessGranted(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z) {
        setNotificationListenerAccessGranted(componentName, z, true);
    }

    public android.service.notification.ZenPolicy getDefaultZenPolicy() {
        try {
            return getService().getDefaultZenPolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z, boolean z2) {
        android.app.INotificationManager service = getService();
        try {
            if (android.app.compat.CompatChanges.isChangeEnabled(SET_LISTENER_ACCESS_GRANTED_IS_USER_AWARE)) {
                service.setNotificationListenerAccessGrantedForUser(componentName, this.mContext.getUserId(), z, z2);
            } else {
                service.setNotificationListenerAccessGranted(componentName, z, z2);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z) {
        try {
            getService().setNotificationListenerAccessGrantedForUser(componentName, i, z, true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setNotificationAssistantAccessGranted(android.content.ComponentName componentName, boolean z) {
        try {
            getService().setNotificationAssistantAccessGranted(componentName, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.ComponentName> getEnabledNotificationListeners() {
        return getEnabledNotificationListeners(this.mContext.getUserId());
    }

    public java.util.List<android.content.ComponentName> getEnabledNotificationListeners(int i) {
        try {
            return getService().getEnabledNotificationListeners(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getAllowedNotificationAssistant() {
        try {
            return getService().getAllowedNotificationAssistant();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean hasEnabledNotificationListener(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return getService().hasEnabledNotificationListener(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static void checkRequired(java.lang.String str, java.lang.Object obj) {
        if (obj == null) {
            throw new java.lang.IllegalArgumentException(str + " is required");
        }
    }

    public void setToastRateLimitingEnabled(boolean z) {
        try {
            getService().setToastRateLimitingEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class Policy implements android.os.Parcelable {
        public static final int CONVERSATION_SENDERS_ANYONE = 1;
        public static final int CONVERSATION_SENDERS_IMPORTANT = 2;
        public static final int CONVERSATION_SENDERS_NONE = 3;
        public static final int CONVERSATION_SENDERS_UNSET = -1;
        public static final int PRIORITY_CATEGORY_ALARMS = 32;
        public static final int PRIORITY_CATEGORY_CALLS = 8;
        public static final int PRIORITY_CATEGORY_CONVERSATIONS = 256;
        public static final int PRIORITY_CATEGORY_EVENTS = 2;
        public static final int PRIORITY_CATEGORY_MEDIA = 64;
        public static final int PRIORITY_CATEGORY_MESSAGES = 4;
        public static final int PRIORITY_CATEGORY_REMINDERS = 1;
        public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
        public static final int PRIORITY_CATEGORY_SYSTEM = 128;
        public static final int PRIORITY_SENDERS_ANY = 0;
        public static final int PRIORITY_SENDERS_CONTACTS = 1;
        public static final int PRIORITY_SENDERS_STARRED = 2;
        public static final int STATE_CHANNELS_BYPASSING_DND = 1;
        public static final int STATE_PRIORITY_CHANNELS_BLOCKED = 2;
        public static final int STATE_UNSET = -1;
        public static final int SUPPRESSED_EFFECTS_UNSET = -1;
        public static final int SUPPRESSED_EFFECT_AMBIENT = 128;
        public static final int SUPPRESSED_EFFECT_BADGE = 64;
        public static final int SUPPRESSED_EFFECT_FULL_SCREEN_INTENT = 4;
        public static final int SUPPRESSED_EFFECT_LIGHTS = 8;
        public static final int SUPPRESSED_EFFECT_NOTIFICATION_LIST = 256;
        public static final int SUPPRESSED_EFFECT_PEEK = 16;

        @java.lang.Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;

        @java.lang.Deprecated
        public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
        public static final int SUPPRESSED_EFFECT_STATUS_BAR = 32;
        public final int priorityCallSenders;
        public final int priorityCategories;
        public final int priorityConversationSenders;
        public final int priorityMessageSenders;
        public final int state;
        public final int suppressedVisualEffects;
        public static final int[] ALL_PRIORITY_CATEGORIES = {32, 64, 128, 1, 2, 4, 8, 16, 256};
        private static final int[] ALL_SUPPRESSED_EFFECTS = {1, 2, 4, 8, 16, 32, 64, 128, 256};
        public static final android.os.Parcelable.Creator<android.app.NotificationManager.Policy> CREATOR = new android.os.Parcelable.Creator<android.app.NotificationManager.Policy>() { // from class: android.app.NotificationManager.Policy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.NotificationManager.Policy createFromParcel(android.os.Parcel parcel) {
                return new android.app.NotificationManager.Policy(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.NotificationManager.Policy[] newArray(int i) {
                return new android.app.NotificationManager.Policy[i];
            }
        };

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ConversationSenders {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PrioritySenders {
        }

        public Policy(int i, int i2, int i3) {
            this(i, i2, i3, -1, -1, -1);
        }

        public Policy(int i, int i2, int i3, int i4) {
            this(i, i2, i3, i4, -1, -1);
        }

        public Policy(int i, int i2, int i3, int i4, int i5) {
            this(i, i2, i3, i4, -1, i5);
        }

        public Policy(int i, int i2, int i3, int i4, int i5, int i6) {
            this.priorityCategories = i;
            this.priorityCallSenders = i2;
            this.priorityMessageSenders = i3;
            this.suppressedVisualEffects = i4;
            this.state = i5;
            this.priorityConversationSenders = i6;
        }

        public Policy(android.os.Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.priorityCategories);
            parcel.writeInt(this.priorityCallSenders);
            parcel.writeInt(this.priorityMessageSenders);
            parcel.writeInt(this.suppressedVisualEffects);
            parcel.writeInt(this.state);
            parcel.writeInt(this.priorityConversationSenders);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.priorityCategories), java.lang.Integer.valueOf(this.priorityCallSenders), java.lang.Integer.valueOf(this.priorityMessageSenders), java.lang.Integer.valueOf(this.suppressedVisualEffects), java.lang.Integer.valueOf(this.state), java.lang.Integer.valueOf(this.priorityConversationSenders));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.NotificationManager.Policy)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.app.NotificationManager.Policy policy = (android.app.NotificationManager.Policy) obj;
            return policy.priorityCategories == this.priorityCategories && policy.priorityCallSenders == this.priorityCallSenders && policy.priorityMessageSenders == this.priorityMessageSenders && suppressedVisualEffectsEqual(this.suppressedVisualEffects, policy.suppressedVisualEffects) && policy.state == this.state && policy.priorityConversationSenders == this.priorityConversationSenders;
        }

        private boolean suppressedVisualEffectsEqual(int i, int i2) {
            if (i == i2) {
                return true;
            }
            if ((i & 2) != 0) {
                i |= 16;
            }
            if ((i & 1) != 0) {
                i = i | 4 | 8 | 128;
            }
            if ((i2 & 2) != 0) {
                i2 |= 16;
            }
            if ((i2 & 1) != 0) {
                i2 = i2 | 4 | 8 | 128;
            }
            int i3 = i & 2;
            if (i3 != (i2 & 2)) {
                if (((i3 != 0 ? i2 : i) & 16) == 0) {
                    return false;
                }
            }
            int i4 = i & 1;
            if (i4 != (i2 & 1)) {
                int i5 = i4 != 0 ? i2 : i;
                if ((i5 & 4) == 0 || (i5 & 8) == 0 || (i5 & 128) == 0) {
                    return false;
                }
            }
            return ((i & (-3)) & (-2)) == ((i2 & (-3)) & (-2));
        }

        public java.lang.String toString() {
            java.lang.String str;
            java.lang.StringBuilder append = new java.lang.StringBuilder().append("NotificationManager.Policy[").append("priorityCategories=").append(priorityCategoriesToString(this.priorityCategories)).append(",priorityCallSenders=").append(prioritySendersToString(this.priorityCallSenders)).append(",priorityMessageSenders=").append(prioritySendersToString(this.priorityMessageSenders)).append(",priorityConvSenders=").append(conversationSendersToString(this.priorityConversationSenders)).append(",suppressedVisualEffects=").append(suppressedEffectsToString(this.suppressedVisualEffects));
            if (android.app.Flags.modesApi()) {
                append.append(",hasPriorityChannels=");
            } else {
                append.append(",areChannelsBypassingDnd=");
            }
            java.lang.String str2 = "true";
            if (this.state == -1) {
                str = "unset";
            } else if ((this.state & 1) != 0) {
                str = "true";
            } else {
                str = "false";
            }
            append.append(str);
            if (android.app.Flags.modesApi()) {
                java.lang.StringBuilder append2 = append.append(",allowPriorityChannels=");
                if (this.state == -1) {
                    str2 = "unset";
                } else if (!allowPriorityChannels()) {
                    str2 = "false";
                }
                append2.append(str2);
            }
            return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            bitwiseToProtoEnum(protoOutputStream, 2259152797697L, this.priorityCategories);
            protoOutputStream.write(1159641169922L, this.priorityCallSenders);
            protoOutputStream.write(1159641169923L, this.priorityMessageSenders);
            bitwiseToProtoEnum(protoOutputStream, 2259152797700L, this.suppressedVisualEffects);
            protoOutputStream.end(start);
        }

        private static void bitwiseToProtoEnum(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
            int i2 = 1;
            while (i > 0) {
                if ((i & 1) == 1) {
                    protoOutputStream.write(j, i2);
                }
                i2++;
                i >>>= 1;
            }
        }

        public static int getAllSuppressedVisualEffects() {
            int i = 0;
            for (int i2 = 0; i2 < ALL_SUPPRESSED_EFFECTS.length; i2++) {
                i |= ALL_SUPPRESSED_EFFECTS[i2];
            }
            return i;
        }

        public static boolean areAllVisualEffectsSuppressed(int i) {
            for (int i2 = 0; i2 < ALL_SUPPRESSED_EFFECTS.length; i2++) {
                if ((ALL_SUPPRESSED_EFFECTS[i2] & i) == 0) {
                    return false;
                }
            }
            return true;
        }

        private static int toggleEffects(int i, int[] iArr, boolean z) {
            for (int i2 : iArr) {
                if (z) {
                    i |= i2;
                } else {
                    i &= ~i2;
                }
            }
            return i;
        }

        public static java.lang.String suppressedEffectsToString(int i) {
            if (i <= 0) {
                return "";
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i2 = 0; i2 < ALL_SUPPRESSED_EFFECTS.length; i2++) {
                int i3 = ALL_SUPPRESSED_EFFECTS[i2];
                if ((i & i3) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(effectToString(i3));
                }
                i &= ~i3;
            }
            if (i != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("UNKNOWN_").append(i);
            }
            return sb.toString();
        }

        public static java.lang.String priorityCategoriesToString(int i) {
            if (i == 0) {
                return "";
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i2 = 0; i2 < ALL_PRIORITY_CATEGORIES.length; i2++) {
                int i3 = ALL_PRIORITY_CATEGORIES[i2];
                if ((i & i3) != 0) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(priorityCategoryToString(i3));
                }
                i &= ~i3;
            }
            if (i != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append("PRIORITY_CATEGORY_UNKNOWN_").append(i);
            }
            return sb.toString();
        }

        private static java.lang.String effectToString(int i) {
            switch (i) {
                case -1:
                    return "SUPPRESSED_EFFECTS_UNSET";
                case 1:
                    return "SUPPRESSED_EFFECT_SCREEN_OFF";
                case 2:
                    return "SUPPRESSED_EFFECT_SCREEN_ON";
                case 4:
                    return "SUPPRESSED_EFFECT_FULL_SCREEN_INTENT";
                case 8:
                    return "SUPPRESSED_EFFECT_LIGHTS";
                case 16:
                    return "SUPPRESSED_EFFECT_PEEK";
                case 32:
                    return "SUPPRESSED_EFFECT_STATUS_BAR";
                case 64:
                    return "SUPPRESSED_EFFECT_BADGE";
                case 128:
                    return "SUPPRESSED_EFFECT_AMBIENT";
                case 256:
                    return "SUPPRESSED_EFFECT_NOTIFICATION_LIST";
                default:
                    return "UNKNOWN_" + i;
            }
        }

        private static java.lang.String priorityCategoryToString(int i) {
            switch (i) {
                case 1:
                    return "PRIORITY_CATEGORY_REMINDERS";
                case 2:
                    return "PRIORITY_CATEGORY_EVENTS";
                case 4:
                    return "PRIORITY_CATEGORY_MESSAGES";
                case 8:
                    return "PRIORITY_CATEGORY_CALLS";
                case 16:
                    return "PRIORITY_CATEGORY_REPEAT_CALLERS";
                case 32:
                    return "PRIORITY_CATEGORY_ALARMS";
                case 64:
                    return "PRIORITY_CATEGORY_MEDIA";
                case 128:
                    return "PRIORITY_CATEGORY_SYSTEM";
                case 256:
                    return "PRIORITY_CATEGORY_CONVERSATIONS";
                default:
                    return "PRIORITY_CATEGORY_UNKNOWN_" + i;
            }
        }

        public static java.lang.String prioritySendersToString(int i) {
            switch (i) {
                case 0:
                    return "PRIORITY_SENDERS_ANY";
                case 1:
                    return "PRIORITY_SENDERS_CONTACTS";
                case 2:
                    return "PRIORITY_SENDERS_STARRED";
                default:
                    return "PRIORITY_SENDERS_UNKNOWN_" + i;
            }
        }

        public static java.lang.String conversationSendersToString(int i) {
            switch (i) {
                case -1:
                    return "unset";
                case 0:
                default:
                    return "invalidConversationType{" + i + "}";
                case 1:
                    return "anyone";
                case 2:
                    return "important";
                case 3:
                    return "none";
            }
        }

        public boolean allowAlarms() {
            return (this.priorityCategories & 32) != 0;
        }

        public boolean allowMedia() {
            return (this.priorityCategories & 64) != 0;
        }

        public boolean allowSystem() {
            return (this.priorityCategories & 128) != 0;
        }

        public boolean allowRepeatCallers() {
            return (this.priorityCategories & 16) != 0;
        }

        public boolean allowCalls() {
            return (this.priorityCategories & 8) != 0;
        }

        public boolean allowConversations() {
            return (this.priorityCategories & 256) != 0;
        }

        public boolean allowMessages() {
            return (this.priorityCategories & 4) != 0;
        }

        public boolean allowEvents() {
            return (this.priorityCategories & 2) != 0;
        }

        public boolean allowReminders() {
            return (this.priorityCategories & 1) != 0;
        }

        public int allowCallsFrom() {
            return this.priorityCallSenders;
        }

        public int allowMessagesFrom() {
            return this.priorityMessageSenders;
        }

        public int allowConversationsFrom() {
            return this.priorityConversationSenders;
        }

        public boolean showFullScreenIntents() {
            return (this.suppressedVisualEffects & 4) == 0;
        }

        public boolean showLights() {
            return (this.suppressedVisualEffects & 8) == 0;
        }

        public boolean showPeeking() {
            return (this.suppressedVisualEffects & 16) == 0;
        }

        public boolean showStatusBarIcons() {
            return (this.suppressedVisualEffects & 32) == 0;
        }

        public boolean showAmbient() {
            return (this.suppressedVisualEffects & 128) == 0;
        }

        public boolean showBadges() {
            return (this.suppressedVisualEffects & 64) == 0;
        }

        public boolean showInNotificationList() {
            return (this.suppressedVisualEffects & 256) == 0;
        }

        public boolean allowPriorityChannels() {
            return this.state == -1 || (this.state & 2) == 0;
        }

        public boolean hasPriorityChannels() {
            return (this.state & 1) != 0;
        }

        public static int policyState(boolean z, boolean z2) {
            int i;
            if (!z) {
                i = 0;
            } else {
                i = 1;
            }
            if (!z2) {
                return i | 2;
            }
            return i;
        }

        public android.app.NotificationManager.Policy copy() {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                return new android.app.NotificationManager.Policy(obtain);
            } finally {
                obtain.recycle();
            }
        }
    }

    public android.service.notification.StatusBarNotification[] getActiveNotifications() {
        try {
            android.content.pm.ParceledListSlice appActiveNotifications = getService().getAppActiveNotifications(this.mContext.getPackageName(), this.mContext.getUserId());
            if (appActiveNotifications != null) {
                java.util.List list = appActiveNotifications.getList();
                return (android.service.notification.StatusBarNotification[]) list.toArray(new android.service.notification.StatusBarNotification[list.size()]);
            }
            return new android.service.notification.StatusBarNotification[0];
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final int getCurrentInterruptionFilter() {
        try {
            return zenModeToInterruptionFilter(getService().getZenMode());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void setInterruptionFilter(int i) {
        setInterruptionFilter(i, false);
    }

    public final void setInterruptionFilter(int i, boolean z) {
        try {
            getService().setInterruptionFilter(this.mContext.getOpPackageName(), i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean matchesCallFilter(android.net.Uri uri) {
        android.os.Bundle bundle = new android.os.Bundle();
        java.util.ArrayList<? extends android.os.Parcelable> arrayList = new java.util.ArrayList<>();
        arrayList.add(new android.app.Person.Builder().setUri(uri.toString()).build());
        bundle.putParcelableArrayList(android.app.Notification.EXTRA_PEOPLE_LIST, arrayList);
        return matchesCallFilter(bundle);
    }

    public static int zenModeToInterruptionFilter(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    public static int zenModeFromInterruptionFilter(int i, int i2) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            default:
                return i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallNotificationEventCallbackStub extends android.app.ICallNotificationEventCallback.Stub {
        final java.util.concurrent.Executor mExecutor;
        final android.app.NotificationManager.CallNotificationEventListener mListener;
        final java.lang.String mPackageName;
        final android.os.UserHandle mUserHandle;

        CallNotificationEventCallbackStub(java.lang.String str, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, android.app.NotificationManager.CallNotificationEventListener callNotificationEventListener) {
            this.mPackageName = str;
            this.mUserHandle = userHandle;
            this.mExecutor = executor;
            this.mListener = callNotificationEventListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallNotificationPosted$0(java.lang.String str, android.os.UserHandle userHandle) {
            this.mListener.onCallNotificationPosted(str, userHandle);
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationPosted(final java.lang.String str, final android.os.UserHandle userHandle) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.NotificationManager$CallNotificationEventCallbackStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.NotificationManager.CallNotificationEventCallbackStub.this.lambda$onCallNotificationPosted$0(str, userHandle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallNotificationRemoved$1(java.lang.String str, android.os.UserHandle userHandle) {
            this.mListener.onCallNotificationRemoved(str, userHandle);
        }

        @Override // android.app.ICallNotificationEventCallback
        public void onCallNotificationRemoved(final java.lang.String str, final android.os.UserHandle userHandle) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.NotificationManager$CallNotificationEventCallbackStub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.NotificationManager.CallNotificationEventCallbackStub.this.lambda$onCallNotificationRemoved$1(str, userHandle);
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void registerCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, android.app.NotificationManager.CallNotificationEventListener callNotificationEventListener) {
        checkRequired("packageName", str);
        checkRequired("userHandle", userHandle);
        checkRequired("executor", executor);
        checkRequired("listener", callNotificationEventListener);
        android.app.INotificationManager service = getService();
        try {
            synchronized (this.mCallNotificationEventCallbacks) {
                android.app.NotificationManager.CallNotificationEventCallbackStub callNotificationEventCallbackStub = new android.app.NotificationManager.CallNotificationEventCallbackStub(str, userHandle, executor, callNotificationEventListener);
                this.mCallNotificationEventCallbacks.put(callNotificationEventListener, callNotificationEventCallbackStub);
                service.registerCallNotificationEventListener(str, userHandle, callNotificationEventCallbackStub);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterCallNotificationEventListener(android.app.NotificationManager.CallNotificationEventListener callNotificationEventListener) {
        checkRequired("listener", callNotificationEventListener);
        android.app.INotificationManager service = getService();
        try {
            synchronized (this.mCallNotificationEventCallbacks) {
                android.app.NotificationManager.CallNotificationEventCallbackStub remove = this.mCallNotificationEventCallbacks.remove(callNotificationEventListener);
                if (remove != null) {
                    service.unregisterCallNotificationEventListener(remove.mPackageName, remove.mUserHandle, remove);
                }
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
