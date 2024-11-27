package android.app;

/* loaded from: classes.dex */
public interface INotificationManager extends android.os.IInterface {
    java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule, java.lang.String str, boolean z) throws android.os.RemoteException;

    void applyAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException;

    void applyAdjustmentsFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.util.List<android.service.notification.Adjustment> list) throws android.os.RemoteException;

    void applyEnqueuedAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException;

    void applyRestore(byte[] bArr, int i) throws android.os.RemoteException;

    boolean areBubblesAllowed(java.lang.String str) throws android.os.RemoteException;

    boolean areBubblesEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean areChannelsBypassingDnd() throws android.os.RemoteException;

    boolean areNotificationsEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean areNotificationsEnabledForPackage(java.lang.String str, int i) throws android.os.RemoteException;

    boolean canNotifyAsPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean canShowBadge(java.lang.String str, int i) throws android.os.RemoteException;

    boolean canUseFullScreenIntent(android.content.AttributionSource attributionSource) throws android.os.RemoteException;

    void cancelAllNotifications(java.lang.String str, int i) throws android.os.RemoteException;

    void cancelNotificationFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void cancelNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException;

    void cancelNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException;

    void cancelToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void cleanUpCallersAfter(long j) throws android.os.RemoteException;

    void clearData(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void clearRequestedListenerHints(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException;

    void createConversationNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, java.lang.String str2) throws android.os.RemoteException;

    void createNotificationChannelGroups(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void createNotificationChannels(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void createNotificationChannelsForPackage(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void deleteNotificationChannel(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void deleteNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void deleteNotificationHistoryItem(java.lang.String str, int i, long j) throws android.os.RemoteException;

    void enqueueNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.app.Notification notification, int i2) throws android.os.RemoteException;

    void enqueueTextToast(java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, boolean z, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback) throws android.os.RemoteException;

    void enqueueToast(java.lang.String str, android.os.IBinder iBinder, android.app.ITransientNotification iTransientNotification, int i, boolean z, int i2) throws android.os.RemoteException;

    void finishToken(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getActiveNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr, int i) throws android.os.RemoteException;

    android.service.notification.StatusBarNotification[] getActiveNotificationsWithAttribution(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllowedAssistantAdjustments(java.lang.String str) throws android.os.RemoteException;

    android.content.ComponentName getAllowedNotificationAssistant() throws android.os.RemoteException;

    android.content.ComponentName getAllowedNotificationAssistantForUser(int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAppActiveNotifications(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) throws android.os.RemoteException;

    int getAutomaticZenRuleState(java.lang.String str) throws android.os.RemoteException;

    java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() throws android.os.RemoteException;

    byte[] getBackupPayload(int i) throws android.os.RemoteException;

    int getBlockedChannelCount(java.lang.String str, int i) throws android.os.RemoteException;

    int getBubblePreferenceForPackage(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() throws android.os.RemoteException;

    android.app.NotificationChannel getConversationNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getConversations(boolean z) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getConversationsForPackage(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.ComponentName getDefaultNotificationAssistant() throws android.os.RemoteException;

    android.service.notification.ZenPolicy getDefaultZenPolicy() throws android.os.RemoteException;

    int getDeletedChannelCount(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.ComponentName getEffectsSuppressor() throws android.os.RemoteException;

    java.util.List<java.lang.String> getEnabledNotificationListenerPackages() throws android.os.RemoteException;

    java.util.List<android.content.ComponentName> getEnabledNotificationListeners(int i) throws android.os.RemoteException;

    int getHintsFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException;

    int getHintsFromListenerNoToken() throws android.os.RemoteException;

    android.service.notification.StatusBarNotification[] getHistoricalNotifications(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    android.service.notification.StatusBarNotification[] getHistoricalNotificationsWithAttribution(java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException;

    int getInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException;

    android.service.notification.NotificationListenerFilter getListenerFilter(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.app.NotificationChannel getNotificationChannelForPackage(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException;

    android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.app.NotificationChannelGroup getNotificationChannelGroupForPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelGroups(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelGroupsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannels(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelsBypassingDnd(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getNotificationChannelsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.lang.String getNotificationDelegate(java.lang.String str) throws android.os.RemoteException;

    android.app.NotificationHistory getNotificationHistory(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.app.NotificationManager.Policy getNotificationPolicy(java.lang.String str) throws android.os.RemoteException;

    long getNotificationSoundTimeout(java.lang.String str, int i) throws android.os.RemoteException;

    int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    int getPackageImportance(java.lang.String str) throws android.os.RemoteException;

    android.app.NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(java.lang.String str, int i, java.lang.String str2, boolean z) throws android.os.RemoteException;

    boolean getPrivateNotificationsAllowed() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(java.lang.String str, int i) throws android.os.RemoteException;

    int getRuleInstanceCount(android.content.ComponentName componentName) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getSnoozedNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException;

    int getZenMode() throws android.os.RemoteException;

    android.service.notification.ZenModeConfig getZenModeConfig() throws android.os.RemoteException;

    java.util.List<android.service.notification.ZenModeConfig.ZenRule> getZenRules() throws android.os.RemoteException;

    boolean hasEnabledNotificationListener(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasSentValidBubble(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasSentValidMsg(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasUserDemotedInvalidMsgApp(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isImportanceLocked(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isInCall(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isInInvalidMsgState(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isNotificationAssistantAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isNotificationListenerAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean isNotificationPolicyAccessGranted(java.lang.String str) throws android.os.RemoteException;

    boolean isNotificationPolicyAccessGrantedForPackage(java.lang.String str) throws android.os.RemoteException;

    boolean isPackagePaused(java.lang.String str) throws android.os.RemoteException;

    boolean isPermissionFixed(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isSystemConditionProviderEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean matchesCallFilter(android.os.Bundle bundle) throws android.os.RemoteException;

    void migrateNotificationFilter(android.service.notification.INotificationListener iNotificationListener, int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void notifyConditions(java.lang.String str, android.service.notification.IConditionProvider iConditionProvider, android.service.notification.Condition[] conditionArr) throws android.os.RemoteException;

    boolean onlyHasDefaultChannel(java.lang.String str, int i) throws android.os.RemoteException;

    long pullStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException;

    void registerCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException;

    void registerListener(android.service.notification.INotificationListener iNotificationListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean removeAutomaticZenRule(java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean removeAutomaticZenRules(java.lang.String str, boolean z) throws android.os.RemoteException;

    void requestBindListener(android.content.ComponentName componentName) throws android.os.RemoteException;

    void requestBindProvider(android.content.ComponentName componentName) throws android.os.RemoteException;

    void requestHintsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException;

    void requestInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException;

    void requestUnbindListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException;

    void requestUnbindListenerComponent(android.content.ComponentName componentName) throws android.os.RemoteException;

    void requestUnbindProvider(android.service.notification.IConditionProvider iConditionProvider) throws android.os.RemoteException;

    void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition) throws android.os.RemoteException;

    void setBubblesAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setHideSilentStatusIcons(boolean z) throws android.os.RemoteException;

    void setInterruptionFilter(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setInvalidMsgAppDemoted(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setListenerFilter(android.content.ComponentName componentName, int i, android.service.notification.NotificationListenerFilter notificationListenerFilter) throws android.os.RemoteException;

    void setNASMigrationDoneAndResetDefault(int i, boolean z) throws android.os.RemoteException;

    void setNotificationAssistantAccessGranted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setNotificationAssistantAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setNotificationDelegate(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z, boolean z2) throws android.os.RemoteException;

    void setNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z, boolean z2) throws android.os.RemoteException;

    void setNotificationPolicy(java.lang.String str, android.app.NotificationManager.Policy policy, boolean z) throws android.os.RemoteException;

    void setNotificationPolicyAccessGranted(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setNotificationPolicyAccessGrantedForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setNotificationSoundTimeout(java.lang.String str, int i, long j) throws android.os.RemoteException;

    void setNotificationsEnabledForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setNotificationsEnabledWithImportanceLockForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setNotificationsShownFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException;

    void setOnNotificationPostedTrimFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException;

    void setPrivateNotificationsAllowed(boolean z) throws android.os.RemoteException;

    void setShowBadge(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setToastRateLimitingEnabled(boolean z) throws android.os.RemoteException;

    void setZenMode(int i, android.net.Uri uri, java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean shouldHideSilentStatusIcons(java.lang.String str) throws android.os.RemoteException;

    void silenceNotificationSound() throws android.os.RemoteException;

    void snoozeNotificationUntilContextFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void snoozeNotificationUntilFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, long j) throws android.os.RemoteException;

    void unlockAllNotificationChannels() throws android.os.RemoteException;

    void unlockNotificationChannel(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void unregisterCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException;

    void unregisterListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException;

    void unsnoozeNotificationFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException;

    void unsnoozeNotificationFromSystemListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException;

    boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, boolean z) throws android.os.RemoteException;

    void updateNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException;

    void updateNotificationChannelFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException;

    void updateNotificationChannelGroupForPackage(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException;

    void updateNotificationChannelGroupFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException;

    public static class Default implements android.app.INotificationManager {
        @Override // android.app.INotificationManager
        public void cancelAllNotifications(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void clearData(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void enqueueTextToast(java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, boolean z, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void enqueueToast(java.lang.String str, android.os.IBinder iBinder, android.app.ITransientNotification iTransientNotification, int i, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void finishToken(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void enqueueNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.app.Notification notification, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isInCall(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setShowBadge(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean canShowBadge(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean hasSentValidMsg(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isInInvalidMsgState(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean hasUserDemotedInvalidMsgApp(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setInvalidMsgAppDemoted(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean hasSentValidBubble(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setNotificationsEnabledForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationsEnabledWithImportanceLockForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean areNotificationsEnabledForPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areNotificationsEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public int getPackageImportance(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public boolean isImportanceLocked(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setNotificationSoundTimeout(java.lang.String str, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public long getNotificationSoundTimeout(java.lang.String str, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.INotificationManager
        public java.util.List<java.lang.String> getAllowedAssistantAdjustments(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean shouldHideSilentStatusIcons(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setHideSilentStatusIcons(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setBubblesAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean areBubblesAllowed(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areBubblesEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public int getBubblePreferenceForPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannelGroups(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannels(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannelsForPackage(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getConversations(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getConversationsForPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelGroupsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannelGroup getNotificationChannelGroupForPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(java.lang.String str, int i, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelGroupForPackage(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unlockNotificationChannel(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unlockAllNotificationChannels() throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannel getConversationNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void createConversationNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannel getNotificationChannelForPackage(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationChannel(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannels(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getDeletedChannelCount(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getBlockedChannelCount(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelGroups(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean onlyHasDefaultChannel(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areChannelsBypassingDnd() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelsBypassingDnd(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean isPackagePaused(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationHistoryItem(java.lang.String str, int i, long j) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isPermissionFixed(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void silenceNotificationSound() throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.service.notification.StatusBarNotification[] getActiveNotificationsWithAttribution(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.service.notification.StatusBarNotification[] getHistoricalNotifications(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.service.notification.StatusBarNotification[] getHistoricalNotificationsWithAttribution(java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationHistory getNotificationHistory(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void registerListener(android.service.notification.INotificationListener iNotificationListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unregisterListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void snoozeNotificationUntilContextFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void snoozeNotificationUntilFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestBindListener(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestUnbindListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestUnbindListenerComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestBindProvider(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestUnbindProvider(android.service.notification.IConditionProvider iConditionProvider) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationsShownFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getActiveNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getSnoozedNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void clearRequestedListenerHints(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestHintsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public int getHintsFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getHintsFromListenerNoToken() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public void requestInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public int getInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public void setOnNotificationPostedTrimFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setInterruptionFilter(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelGroupFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void applyEnqueuedAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void applyAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void applyAdjustmentsFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.util.List<android.service.notification.Adjustment> list) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unsnoozeNotificationFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unsnoozeNotificationFromSystemListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.ComponentName getEffectsSuppressor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean matchesCallFilter(android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void cleanUpCallersAfter(long j) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isSystemConditionProviderEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationListenerAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationAssistantAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationAssistantAccessGranted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationAssistantAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public java.util.List<java.lang.String> getEnabledNotificationListenerPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public java.util.List<android.content.ComponentName> getEnabledNotificationListeners(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.ComponentName getAllowedNotificationAssistantForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.ComponentName getAllowedNotificationAssistant() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.content.ComponentName getDefaultNotificationAssistant() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void setNASMigrationDoneAndResetDefault(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean hasEnabledNotificationListener(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public int getZenMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public android.service.notification.ZenModeConfig getZenModeConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void setZenMode(int i, android.net.Uri uri, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void notifyConditions(java.lang.String str, android.service.notification.IConditionProvider iConditionProvider, android.service.notification.Condition[] conditionArr) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationPolicyAccessGranted(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public android.app.NotificationManager.Policy getNotificationPolicy(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicy(java.lang.String str, android.app.NotificationManager.Policy policy, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationPolicyAccessGrantedForPackage(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicyAccessGranted(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicyAccessGrantedForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.service.notification.ZenPolicy getDefaultZenPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public java.util.List<android.service.notification.ZenModeConfig.ZenRule> getZenRules() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule, java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean removeAutomaticZenRule(java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean removeAutomaticZenRules(java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public int getRuleInstanceCount(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getAutomaticZenRuleState(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public byte[] getBackupPayload(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public android.content.pm.ParceledListSlice getAppActiveNotifications(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void setNotificationDelegate(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public java.lang.String getNotificationDelegate(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean canNotifyAsPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean canUseFullScreenIntent(android.content.AttributionSource attributionSource) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setPrivateNotificationsAllowed(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean getPrivateNotificationsAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public long pullStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.INotificationManager
        public android.service.notification.NotificationListenerFilter getListenerFilter(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void setListenerFilter(android.content.ComponentName componentName, int i, android.service.notification.NotificationListenerFilter notificationListenerFilter) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void migrateNotificationFilter(android.service.notification.INotificationListener iNotificationListener, int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setToastRateLimitingEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void registerCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unregisterCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.INotificationManager {
        public static final java.lang.String DESCRIPTOR = "android.app.INotificationManager";
        static final java.lang.String[] PERMISSIONS_registerCallNotificationEventListener = {android.Manifest.permission.INTERACT_ACROSS_USERS, android.Manifest.permission.ACCESS_NOTIFICATIONS};
        static final java.lang.String[] PERMISSIONS_unregisterCallNotificationEventListener = {android.Manifest.permission.INTERACT_ACROSS_USERS, android.Manifest.permission.ACCESS_NOTIFICATIONS};
        static final int TRANSACTION_addAutomaticZenRule = 134;
        static final int TRANSACTION_applyAdjustmentFromAssistant = 97;
        static final int TRANSACTION_applyAdjustmentsFromAssistant = 98;
        static final int TRANSACTION_applyEnqueuedAdjustmentFromAssistant = 96;
        static final int TRANSACTION_applyRestore = 142;
        static final int TRANSACTION_areBubblesAllowed = 29;
        static final int TRANSACTION_areBubblesEnabled = 30;
        static final int TRANSACTION_areChannelsBypassingDnd = 59;
        static final int TRANSACTION_areNotificationsEnabled = 20;
        static final int TRANSACTION_areNotificationsEnabledForPackage = 19;
        static final int TRANSACTION_canNotifyAsPackage = 146;
        static final int TRANSACTION_canShowBadge = 11;
        static final int TRANSACTION_canUseFullScreenIntent = 147;
        static final int TRANSACTION_cancelAllNotifications = 1;
        static final int TRANSACTION_cancelNotificationFromListener = 72;
        static final int TRANSACTION_cancelNotificationWithTag = 8;
        static final int TRANSACTION_cancelNotificationsFromListener = 73;
        static final int TRANSACTION_cancelToast = 5;
        static final int TRANSACTION_cleanUpCallersAfter = 103;
        static final int TRANSACTION_clearData = 2;
        static final int TRANSACTION_clearRequestedListenerHints = 84;
        static final int TRANSACTION_createConversationNotificationChannelForPackage = 47;
        static final int TRANSACTION_createNotificationChannelGroups = 32;
        static final int TRANSACTION_createNotificationChannels = 33;
        static final int TRANSACTION_createNotificationChannelsForPackage = 34;
        static final int TRANSACTION_deleteNotificationChannel = 49;
        static final int TRANSACTION_deleteNotificationChannelGroup = 55;
        static final int TRANSACTION_deleteNotificationHistoryItem = 62;
        static final int TRANSACTION_enqueueNotificationWithTag = 7;
        static final int TRANSACTION_enqueueTextToast = 3;
        static final int TRANSACTION_enqueueToast = 4;
        static final int TRANSACTION_finishToken = 6;
        static final int TRANSACTION_getActiveNotifications = 65;
        static final int TRANSACTION_getActiveNotificationsFromListener = 82;
        static final int TRANSACTION_getActiveNotificationsWithAttribution = 66;
        static final int TRANSACTION_getAllowedAssistantAdjustments = 25;
        static final int TRANSACTION_getAllowedNotificationAssistant = 115;
        static final int TRANSACTION_getAllowedNotificationAssistantForUser = 114;
        static final int TRANSACTION_getAppActiveNotifications = 143;
        static final int TRANSACTION_getAutomaticZenRule = 131;
        static final int TRANSACTION_getAutomaticZenRuleState = 139;
        static final int TRANSACTION_getAutomaticZenRules = 132;
        static final int TRANSACTION_getBackupPayload = 141;
        static final int TRANSACTION_getBlockedChannelCount = 54;
        static final int TRANSACTION_getBubblePreferenceForPackage = 31;
        static final int TRANSACTION_getConsolidatedNotificationPolicy = 121;
        static final int TRANSACTION_getConversationNotificationChannel = 46;
        static final int TRANSACTION_getConversations = 35;
        static final int TRANSACTION_getConversationsForPackage = 36;
        static final int TRANSACTION_getDefaultNotificationAssistant = 116;
        static final int TRANSACTION_getDefaultZenPolicy = 130;
        static final int TRANSACTION_getDeletedChannelCount = 53;
        static final int TRANSACTION_getEffectsSuppressor = 101;
        static final int TRANSACTION_getEnabledNotificationListenerPackages = 112;
        static final int TRANSACTION_getEnabledNotificationListeners = 113;
        static final int TRANSACTION_getHintsFromListener = 86;
        static final int TRANSACTION_getHintsFromListenerNoToken = 87;
        static final int TRANSACTION_getHistoricalNotifications = 67;
        static final int TRANSACTION_getHistoricalNotificationsWithAttribution = 68;
        static final int TRANSACTION_getInterruptionFilterFromListener = 89;
        static final int TRANSACTION_getListenerFilter = 151;
        static final int TRANSACTION_getNotificationChannel = 45;
        static final int TRANSACTION_getNotificationChannelForPackage = 48;
        static final int TRANSACTION_getNotificationChannelGroup = 56;
        static final int TRANSACTION_getNotificationChannelGroupForPackage = 38;
        static final int TRANSACTION_getNotificationChannelGroups = 57;
        static final int TRANSACTION_getNotificationChannelGroupsForPackage = 37;
        static final int TRANSACTION_getNotificationChannelGroupsFromPrivilegedListener = 95;
        static final int TRANSACTION_getNotificationChannels = 50;
        static final int TRANSACTION_getNotificationChannelsBypassingDnd = 60;
        static final int TRANSACTION_getNotificationChannelsForPackage = 51;
        static final int TRANSACTION_getNotificationChannelsFromPrivilegedListener = 94;
        static final int TRANSACTION_getNotificationDelegate = 145;
        static final int TRANSACTION_getNotificationHistory = 69;
        static final int TRANSACTION_getNotificationPolicy = 125;
        static final int TRANSACTION_getNotificationSoundTimeout = 24;
        static final int TRANSACTION_getNumNotificationChannelsForPackage = 52;
        static final int TRANSACTION_getPackageImportance = 21;
        static final int TRANSACTION_getPopulatedNotificationChannelGroupForPackage = 39;
        static final int TRANSACTION_getPrivateNotificationsAllowed = 149;
        static final int TRANSACTION_getRecentBlockedNotificationChannelGroupsForPackage = 40;
        static final int TRANSACTION_getRuleInstanceCount = 138;
        static final int TRANSACTION_getSnoozedNotificationsFromListener = 83;
        static final int TRANSACTION_getZenMode = 119;
        static final int TRANSACTION_getZenModeConfig = 120;
        static final int TRANSACTION_getZenRules = 133;
        static final int TRANSACTION_hasEnabledNotificationListener = 118;
        static final int TRANSACTION_hasSentValidBubble = 16;
        static final int TRANSACTION_hasSentValidMsg = 12;
        static final int TRANSACTION_hasUserDemotedInvalidMsgApp = 14;
        static final int TRANSACTION_isImportanceLocked = 22;
        static final int TRANSACTION_isInCall = 9;
        static final int TRANSACTION_isInInvalidMsgState = 13;
        static final int TRANSACTION_isNotificationAssistantAccessGranted = 107;
        static final int TRANSACTION_isNotificationListenerAccessGranted = 105;
        static final int TRANSACTION_isNotificationListenerAccessGrantedForUser = 106;
        static final int TRANSACTION_isNotificationPolicyAccessGranted = 124;
        static final int TRANSACTION_isNotificationPolicyAccessGrantedForPackage = 127;
        static final int TRANSACTION_isPackagePaused = 61;
        static final int TRANSACTION_isPermissionFixed = 63;
        static final int TRANSACTION_isSystemConditionProviderEnabled = 104;
        static final int TRANSACTION_matchesCallFilter = 102;
        static final int TRANSACTION_migrateNotificationFilter = 153;
        static final int TRANSACTION_notifyConditions = 123;
        static final int TRANSACTION_onlyHasDefaultChannel = 58;
        static final int TRANSACTION_pullStats = 150;
        static final int TRANSACTION_registerCallNotificationEventListener = 155;
        static final int TRANSACTION_registerListener = 70;
        static final int TRANSACTION_removeAutomaticZenRule = 136;
        static final int TRANSACTION_removeAutomaticZenRules = 137;
        static final int TRANSACTION_requestBindListener = 76;
        static final int TRANSACTION_requestBindProvider = 79;
        static final int TRANSACTION_requestHintsFromListener = 85;
        static final int TRANSACTION_requestInterruptionFilterFromListener = 88;
        static final int TRANSACTION_requestUnbindListener = 77;
        static final int TRANSACTION_requestUnbindListenerComponent = 78;
        static final int TRANSACTION_requestUnbindProvider = 80;
        static final int TRANSACTION_setAutomaticZenRuleState = 140;
        static final int TRANSACTION_setBubblesAllowed = 28;
        static final int TRANSACTION_setHideSilentStatusIcons = 27;
        static final int TRANSACTION_setInterruptionFilter = 91;
        static final int TRANSACTION_setInvalidMsgAppDemoted = 15;
        static final int TRANSACTION_setListenerFilter = 152;
        static final int TRANSACTION_setNASMigrationDoneAndResetDefault = 117;
        static final int TRANSACTION_setNotificationAssistantAccessGranted = 109;
        static final int TRANSACTION_setNotificationAssistantAccessGrantedForUser = 111;
        static final int TRANSACTION_setNotificationDelegate = 144;
        static final int TRANSACTION_setNotificationListenerAccessGranted = 108;
        static final int TRANSACTION_setNotificationListenerAccessGrantedForUser = 110;
        static final int TRANSACTION_setNotificationPolicy = 126;
        static final int TRANSACTION_setNotificationPolicyAccessGranted = 128;
        static final int TRANSACTION_setNotificationPolicyAccessGrantedForUser = 129;
        static final int TRANSACTION_setNotificationSoundTimeout = 23;
        static final int TRANSACTION_setNotificationsEnabledForPackage = 17;
        static final int TRANSACTION_setNotificationsEnabledWithImportanceLockForPackage = 18;
        static final int TRANSACTION_setNotificationsShownFromListener = 81;
        static final int TRANSACTION_setOnNotificationPostedTrimFromListener = 90;
        static final int TRANSACTION_setPrivateNotificationsAllowed = 148;
        static final int TRANSACTION_setShowBadge = 10;
        static final int TRANSACTION_setToastRateLimitingEnabled = 154;
        static final int TRANSACTION_setZenMode = 122;
        static final int TRANSACTION_shouldHideSilentStatusIcons = 26;
        static final int TRANSACTION_silenceNotificationSound = 64;
        static final int TRANSACTION_snoozeNotificationUntilContextFromListener = 74;
        static final int TRANSACTION_snoozeNotificationUntilFromListener = 75;
        static final int TRANSACTION_unlockAllNotificationChannels = 44;
        static final int TRANSACTION_unlockNotificationChannel = 43;
        static final int TRANSACTION_unregisterCallNotificationEventListener = 156;
        static final int TRANSACTION_unregisterListener = 71;
        static final int TRANSACTION_unsnoozeNotificationFromAssistant = 99;
        static final int TRANSACTION_unsnoozeNotificationFromSystemListener = 100;
        static final int TRANSACTION_updateAutomaticZenRule = 135;
        static final int TRANSACTION_updateNotificationChannelForPackage = 42;
        static final int TRANSACTION_updateNotificationChannelFromPrivilegedListener = 93;
        static final int TRANSACTION_updateNotificationChannelGroupForPackage = 41;
        static final int TRANSACTION_updateNotificationChannelGroupFromPrivilegedListener = 92;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.INotificationManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.INotificationManager)) {
                return (android.app.INotificationManager) queryLocalInterface;
            }
            return new android.app.INotificationManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "cancelAllNotifications";
                case 2:
                    return "clearData";
                case 3:
                    return "enqueueTextToast";
                case 4:
                    return "enqueueToast";
                case 5:
                    return "cancelToast";
                case 6:
                    return "finishToken";
                case 7:
                    return "enqueueNotificationWithTag";
                case 8:
                    return "cancelNotificationWithTag";
                case 9:
                    return "isInCall";
                case 10:
                    return "setShowBadge";
                case 11:
                    return "canShowBadge";
                case 12:
                    return "hasSentValidMsg";
                case 13:
                    return "isInInvalidMsgState";
                case 14:
                    return "hasUserDemotedInvalidMsgApp";
                case 15:
                    return "setInvalidMsgAppDemoted";
                case 16:
                    return "hasSentValidBubble";
                case 17:
                    return "setNotificationsEnabledForPackage";
                case 18:
                    return "setNotificationsEnabledWithImportanceLockForPackage";
                case 19:
                    return "areNotificationsEnabledForPackage";
                case 20:
                    return "areNotificationsEnabled";
                case 21:
                    return "getPackageImportance";
                case 22:
                    return "isImportanceLocked";
                case 23:
                    return "setNotificationSoundTimeout";
                case 24:
                    return "getNotificationSoundTimeout";
                case 25:
                    return "getAllowedAssistantAdjustments";
                case 26:
                    return "shouldHideSilentStatusIcons";
                case 27:
                    return "setHideSilentStatusIcons";
                case 28:
                    return "setBubblesAllowed";
                case 29:
                    return "areBubblesAllowed";
                case 30:
                    return "areBubblesEnabled";
                case 31:
                    return "getBubblePreferenceForPackage";
                case 32:
                    return "createNotificationChannelGroups";
                case 33:
                    return "createNotificationChannels";
                case 34:
                    return "createNotificationChannelsForPackage";
                case 35:
                    return "getConversations";
                case 36:
                    return "getConversationsForPackage";
                case 37:
                    return "getNotificationChannelGroupsForPackage";
                case 38:
                    return "getNotificationChannelGroupForPackage";
                case 39:
                    return "getPopulatedNotificationChannelGroupForPackage";
                case 40:
                    return "getRecentBlockedNotificationChannelGroupsForPackage";
                case 41:
                    return "updateNotificationChannelGroupForPackage";
                case 42:
                    return "updateNotificationChannelForPackage";
                case 43:
                    return "unlockNotificationChannel";
                case 44:
                    return "unlockAllNotificationChannels";
                case 45:
                    return "getNotificationChannel";
                case 46:
                    return "getConversationNotificationChannel";
                case 47:
                    return "createConversationNotificationChannelForPackage";
                case 48:
                    return "getNotificationChannelForPackage";
                case 49:
                    return "deleteNotificationChannel";
                case 50:
                    return "getNotificationChannels";
                case 51:
                    return "getNotificationChannelsForPackage";
                case 52:
                    return "getNumNotificationChannelsForPackage";
                case 53:
                    return "getDeletedChannelCount";
                case 54:
                    return "getBlockedChannelCount";
                case 55:
                    return "deleteNotificationChannelGroup";
                case 56:
                    return "getNotificationChannelGroup";
                case 57:
                    return "getNotificationChannelGroups";
                case 58:
                    return "onlyHasDefaultChannel";
                case 59:
                    return android.service.notification.ZenModeDiff.ConfigDiff.FIELD_ARE_CHANNELS_BYPASSING_DND;
                case 60:
                    return "getNotificationChannelsBypassingDnd";
                case 61:
                    return "isPackagePaused";
                case 62:
                    return "deleteNotificationHistoryItem";
                case 63:
                    return "isPermissionFixed";
                case 64:
                    return "silenceNotificationSound";
                case 65:
                    return "getActiveNotifications";
                case 66:
                    return "getActiveNotificationsWithAttribution";
                case 67:
                    return "getHistoricalNotifications";
                case 68:
                    return "getHistoricalNotificationsWithAttribution";
                case 69:
                    return "getNotificationHistory";
                case 70:
                    return "registerListener";
                case 71:
                    return "unregisterListener";
                case 72:
                    return "cancelNotificationFromListener";
                case 73:
                    return "cancelNotificationsFromListener";
                case 74:
                    return "snoozeNotificationUntilContextFromListener";
                case 75:
                    return "snoozeNotificationUntilFromListener";
                case 76:
                    return "requestBindListener";
                case 77:
                    return "requestUnbindListener";
                case 78:
                    return "requestUnbindListenerComponent";
                case 79:
                    return "requestBindProvider";
                case 80:
                    return "requestUnbindProvider";
                case 81:
                    return "setNotificationsShownFromListener";
                case 82:
                    return "getActiveNotificationsFromListener";
                case 83:
                    return "getSnoozedNotificationsFromListener";
                case 84:
                    return "clearRequestedListenerHints";
                case 85:
                    return "requestHintsFromListener";
                case 86:
                    return "getHintsFromListener";
                case 87:
                    return "getHintsFromListenerNoToken";
                case 88:
                    return "requestInterruptionFilterFromListener";
                case 89:
                    return "getInterruptionFilterFromListener";
                case 90:
                    return "setOnNotificationPostedTrimFromListener";
                case 91:
                    return "setInterruptionFilter";
                case 92:
                    return "updateNotificationChannelGroupFromPrivilegedListener";
                case 93:
                    return "updateNotificationChannelFromPrivilegedListener";
                case 94:
                    return "getNotificationChannelsFromPrivilegedListener";
                case 95:
                    return "getNotificationChannelGroupsFromPrivilegedListener";
                case 96:
                    return "applyEnqueuedAdjustmentFromAssistant";
                case 97:
                    return "applyAdjustmentFromAssistant";
                case 98:
                    return "applyAdjustmentsFromAssistant";
                case 99:
                    return "unsnoozeNotificationFromAssistant";
                case 100:
                    return "unsnoozeNotificationFromSystemListener";
                case 101:
                    return "getEffectsSuppressor";
                case 102:
                    return "matchesCallFilter";
                case 103:
                    return "cleanUpCallersAfter";
                case 104:
                    return "isSystemConditionProviderEnabled";
                case 105:
                    return "isNotificationListenerAccessGranted";
                case 106:
                    return "isNotificationListenerAccessGrantedForUser";
                case 107:
                    return "isNotificationAssistantAccessGranted";
                case 108:
                    return "setNotificationListenerAccessGranted";
                case 109:
                    return "setNotificationAssistantAccessGranted";
                case 110:
                    return "setNotificationListenerAccessGrantedForUser";
                case 111:
                    return "setNotificationAssistantAccessGrantedForUser";
                case 112:
                    return "getEnabledNotificationListenerPackages";
                case 113:
                    return "getEnabledNotificationListeners";
                case 114:
                    return "getAllowedNotificationAssistantForUser";
                case 115:
                    return "getAllowedNotificationAssistant";
                case 116:
                    return "getDefaultNotificationAssistant";
                case 117:
                    return "setNASMigrationDoneAndResetDefault";
                case 118:
                    return "hasEnabledNotificationListener";
                case 119:
                    return "getZenMode";
                case 120:
                    return "getZenModeConfig";
                case 121:
                    return "getConsolidatedNotificationPolicy";
                case 122:
                    return "setZenMode";
                case 123:
                    return "notifyConditions";
                case 124:
                    return "isNotificationPolicyAccessGranted";
                case 125:
                    return "getNotificationPolicy";
                case 126:
                    return "setNotificationPolicy";
                case 127:
                    return "isNotificationPolicyAccessGrantedForPackage";
                case 128:
                    return "setNotificationPolicyAccessGranted";
                case 129:
                    return "setNotificationPolicyAccessGrantedForUser";
                case 130:
                    return "getDefaultZenPolicy";
                case 131:
                    return "getAutomaticZenRule";
                case 132:
                    return "getAutomaticZenRules";
                case 133:
                    return "getZenRules";
                case 134:
                    return "addAutomaticZenRule";
                case 135:
                    return "updateAutomaticZenRule";
                case 136:
                    return "removeAutomaticZenRule";
                case 137:
                    return "removeAutomaticZenRules";
                case 138:
                    return "getRuleInstanceCount";
                case 139:
                    return "getAutomaticZenRuleState";
                case 140:
                    return "setAutomaticZenRuleState";
                case 141:
                    return "getBackupPayload";
                case 142:
                    return "applyRestore";
                case 143:
                    return "getAppActiveNotifications";
                case 144:
                    return "setNotificationDelegate";
                case 145:
                    return "getNotificationDelegate";
                case 146:
                    return "canNotifyAsPackage";
                case 147:
                    return "canUseFullScreenIntent";
                case 148:
                    return "setPrivateNotificationsAllowed";
                case 149:
                    return "getPrivateNotificationsAllowed";
                case 150:
                    return "pullStats";
                case 151:
                    return "getListenerFilter";
                case 152:
                    return "setListenerFilter";
                case 153:
                    return "migrateNotificationFilter";
                case 154:
                    return "setToastRateLimitingEnabled";
                case 155:
                    return "registerCallNotificationEventListener";
                case 156:
                    return "unregisterCallNotificationEventListener";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelAllNotifications(readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearData(readString2, readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    android.app.ITransientNotificationCallback asInterface = android.app.ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enqueueTextToast(readString3, readStrongBinder, charSequence, readInt3, readBoolean2, readInt4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.app.ITransientNotification asInterface2 = android.app.ITransientNotification.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueToast(readString4, readStrongBinder2, asInterface2, readInt5, readBoolean3, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelToast(readString5, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finishToken(readString6, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    android.app.Notification notification = (android.app.Notification) parcel.readTypedObject(android.app.Notification.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueNotificationWithTag(readString7, readString8, readString9, readInt7, notification, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationWithTag(readString10, readString11, readString12, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString13 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInCall = isInCall(readString13, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 10:
                    java.lang.String readString14 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowBadge(readString14, readInt12, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString15 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canShowBadge = canShowBadge(readString15, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canShowBadge);
                    return true;
                case 12:
                    java.lang.String readString16 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSentValidMsg = hasSentValidMsg(readString16, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSentValidMsg);
                    return true;
                case 13:
                    java.lang.String readString17 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInInvalidMsgState = isInInvalidMsgState(readString17, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInInvalidMsgState);
                    return true;
                case 14:
                    java.lang.String readString18 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUserDemotedInvalidMsgApp = hasUserDemotedInvalidMsgApp(readString18, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserDemotedInvalidMsgApp);
                    return true;
                case 15:
                    java.lang.String readString19 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInvalidMsgAppDemoted(readString19, readInt17, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString20 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSentValidBubble = hasSentValidBubble(readString20, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSentValidBubble);
                    return true;
                case 17:
                    java.lang.String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledForPackage(readString21, readInt19, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString22 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledWithImportanceLockForPackage(readString22, readInt20, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString23 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean areNotificationsEnabledForPackage = areNotificationsEnabledForPackage(readString23, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areNotificationsEnabledForPackage);
                    return true;
                case 20:
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean areNotificationsEnabled = areNotificationsEnabled(readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areNotificationsEnabled);
                    return true;
                case 21:
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageImportance = getPackageImportance(readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageImportance);
                    return true;
                case 22:
                    java.lang.String readString26 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImportanceLocked = isImportanceLocked(readString26, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImportanceLocked);
                    return true;
                case 23:
                    java.lang.String readString27 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setNotificationSoundTimeout(readString27, readInt23, readLong);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString28 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long notificationSoundTimeout = getNotificationSoundTimeout(readString28, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeLong(notificationSoundTimeout);
                    return true;
                case 25:
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> allowedAssistantAdjustments = getAllowedAssistantAdjustments(readString29);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedAssistantAdjustments);
                    return true;
                case 26:
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldHideSilentStatusIcons = shouldHideSilentStatusIcons(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHideSilentStatusIcons);
                    return true;
                case 27:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHideSilentStatusIcons(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    java.lang.String readString31 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBubblesAllowed(readString31, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean areBubblesAllowed = areBubblesAllowed(readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areBubblesAllowed);
                    return true;
                case 30:
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean areBubblesEnabled = areBubblesEnabled(userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areBubblesEnabled);
                    return true;
                case 31:
                    java.lang.String readString33 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int bubblePreferenceForPackage = getBubblePreferenceForPackage(readString33, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeInt(bubblePreferenceForPackage);
                    return true;
                case 32:
                    java.lang.String readString34 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelGroups(readString34, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString35 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannels(readString35, parceledListSlice2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString36 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    android.content.pm.ParceledListSlice parceledListSlice3 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelsForPackage(readString36, readInt28, parceledListSlice3);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice conversations = getConversations(readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversations, 1);
                    return true;
                case 36:
                    java.lang.String readString37 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice conversationsForPackage = getConversationsForPackage(readString37, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationsForPackage, 1);
                    return true;
                case 37:
                    java.lang.String readString38 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelGroupsForPackage = getNotificationChannelGroupsForPackage(readString38, readInt30, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsForPackage, 1);
                    return true;
                case 38:
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannelGroup notificationChannelGroupForPackage = getNotificationChannelGroupForPackage(readString39, readString40, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupForPackage, 1);
                    return true;
                case 39:
                    java.lang.String readString41 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannelGroup populatedNotificationChannelGroupForPackage = getPopulatedNotificationChannelGroupForPackage(readString41, readInt32, readString42, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(populatedNotificationChannelGroupForPackage, 1);
                    return true;
                case 40:
                    java.lang.String readString43 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice recentBlockedNotificationChannelGroupsForPackage = getRecentBlockedNotificationChannelGroupsForPackage(readString43, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentBlockedNotificationChannelGroupsForPackage, 1);
                    return true;
                case 41:
                    java.lang.String readString44 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    android.app.NotificationChannelGroup notificationChannelGroup = (android.app.NotificationChannelGroup) parcel.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupForPackage(readString44, readInt34, notificationChannelGroup);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    java.lang.String readString45 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) parcel.readTypedObject(android.app.NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelForPackage(readString45, readInt35, notificationChannel);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    java.lang.String readString46 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unlockNotificationChannel(readString46, readInt36, readString47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    unlockAllNotificationChannels();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    java.lang.String readString48 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    java.lang.String readString49 = parcel.readString();
                    java.lang.String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannel notificationChannel2 = getNotificationChannel(readString48, readInt37, readString49, readString50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannel2, 1);
                    return true;
                case 46:
                    java.lang.String readString51 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    java.lang.String readString52 = parcel.readString();
                    java.lang.String readString53 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    java.lang.String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannel conversationNotificationChannel = getConversationNotificationChannel(readString51, readInt38, readString52, readString53, readBoolean12, readString54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationNotificationChannel, 1);
                    return true;
                case 47:
                    java.lang.String readString55 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    android.app.NotificationChannel notificationChannel3 = (android.app.NotificationChannel) parcel.readTypedObject(android.app.NotificationChannel.CREATOR);
                    java.lang.String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createConversationNotificationChannelForPackage(readString55, readInt39, notificationChannel3, readString56);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    java.lang.String readString57 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    java.lang.String readString58 = parcel.readString();
                    java.lang.String readString59 = parcel.readString();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannel notificationChannelForPackage = getNotificationChannelForPackage(readString57, readInt40, readString58, readString59, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelForPackage, 1);
                    return true;
                case 49:
                    java.lang.String readString60 = parcel.readString();
                    java.lang.String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannel(readString60, readString61);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString62 = parcel.readString();
                    java.lang.String readString63 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannels = getNotificationChannels(readString62, readString63, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannels, 1);
                    return true;
                case 51:
                    java.lang.String readString64 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelsForPackage = getNotificationChannelsForPackage(readString64, readInt42, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsForPackage, 1);
                    return true;
                case 52:
                    java.lang.String readString65 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int numNotificationChannelsForPackage = getNumNotificationChannelsForPackage(readString65, readInt43, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeInt(numNotificationChannelsForPackage);
                    return true;
                case 53:
                    java.lang.String readString66 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deletedChannelCount = getDeletedChannelCount(readString66, readInt44);
                    parcel2.writeNoException();
                    parcel2.writeInt(deletedChannelCount);
                    return true;
                case 54:
                    java.lang.String readString67 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int blockedChannelCount = getBlockedChannelCount(readString67, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedChannelCount);
                    return true;
                case 55:
                    java.lang.String readString68 = parcel.readString();
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannelGroup(readString68, readString69);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    java.lang.String readString70 = parcel.readString();
                    java.lang.String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationChannelGroup notificationChannelGroup2 = getNotificationChannelGroup(readString70, readString71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroup2, 1);
                    return true;
                case 57:
                    java.lang.String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelGroups = getNotificationChannelGroups(readString72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroups, 1);
                    return true;
                case 58:
                    java.lang.String readString73 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean onlyHasDefaultChannel = onlyHasDefaultChannel(readString73, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(onlyHasDefaultChannel);
                    return true;
                case 59:
                    boolean areChannelsBypassingDnd = areChannelsBypassingDnd();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areChannelsBypassingDnd);
                    return true;
                case 60:
                    java.lang.String readString74 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelsBypassingDnd = getNotificationChannelsBypassingDnd(readString74, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsBypassingDnd, 1);
                    return true;
                case 61:
                    java.lang.String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackagePaused = isPackagePaused(readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackagePaused);
                    return true;
                case 62:
                    java.lang.String readString76 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteNotificationHistoryItem(readString76, readInt48, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    java.lang.String readString77 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionFixed = isPermissionFixed(readString77, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionFixed);
                    return true;
                case 64:
                    silenceNotificationSound();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    java.lang.String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.service.notification.StatusBarNotification[] activeNotifications = getActiveNotifications(readString78);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotifications, 1);
                    return true;
                case 66:
                    java.lang.String readString79 = parcel.readString();
                    java.lang.String readString80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.service.notification.StatusBarNotification[] activeNotificationsWithAttribution = getActiveNotificationsWithAttribution(readString79, readString80);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotificationsWithAttribution, 1);
                    return true;
                case 67:
                    java.lang.String readString81 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.service.notification.StatusBarNotification[] historicalNotifications = getHistoricalNotifications(readString81, readInt50, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotifications, 1);
                    return true;
                case 68:
                    java.lang.String readString82 = parcel.readString();
                    java.lang.String readString83 = parcel.readString();
                    int readInt51 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.service.notification.StatusBarNotification[] historicalNotificationsWithAttribution = getHistoricalNotificationsWithAttribution(readString82, readString83, readInt51, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotificationsWithAttribution, 1);
                    return true;
                case 69:
                    java.lang.String readString84 = parcel.readString();
                    java.lang.String readString85 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationHistory notificationHistory = getNotificationHistory(readString84, readString85);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationHistory, 1);
                    return true;
                case 70:
                    android.service.notification.INotificationListener asInterface3 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListener(asInterface3, componentName, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    android.service.notification.INotificationListener asInterface4 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(asInterface4, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    android.service.notification.INotificationListener asInterface5 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString86 = parcel.readString();
                    java.lang.String readString87 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationFromListener(asInterface5, readString86, readString87, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    android.service.notification.INotificationListener asInterface6 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    cancelNotificationsFromListener(asInterface6, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    android.service.notification.INotificationListener asInterface7 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString88 = parcel.readString();
                    java.lang.String readString89 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilContextFromListener(asInterface7, readString88, readString89);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    android.service.notification.INotificationListener asInterface8 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString90 = parcel.readString();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilFromListener(asInterface8, readString90, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBindListener(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    android.service.notification.INotificationListener asInterface9 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUnbindListenerComponent(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBindProvider(componentName4);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    android.service.notification.IConditionProvider asInterface10 = android.service.notification.IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindProvider(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    android.service.notification.INotificationListener asInterface11 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setNotificationsShownFromListener(asInterface11, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    android.service.notification.INotificationListener asInterface12 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice activeNotificationsFromListener = getActiveNotificationsFromListener(asInterface12, createStringArray3, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeNotificationsFromListener, 1);
                    return true;
                case 83:
                    android.service.notification.INotificationListener asInterface13 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice snoozedNotificationsFromListener = getSnoozedNotificationsFromListener(asInterface13, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(snoozedNotificationsFromListener, 1);
                    return true;
                case 84:
                    android.service.notification.INotificationListener asInterface14 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearRequestedListenerHints(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    android.service.notification.INotificationListener asInterface15 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestHintsFromListener(asInterface15, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    android.service.notification.INotificationListener asInterface16 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int hintsFromListener = getHintsFromListener(asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListener);
                    return true;
                case 87:
                    int hintsFromListenerNoToken = getHintsFromListenerNoToken();
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListenerNoToken);
                    return true;
                case 88:
                    android.service.notification.INotificationListener asInterface17 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestInterruptionFilterFromListener(asInterface17, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    android.service.notification.INotificationListener asInterface18 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int interruptionFilterFromListener = getInterruptionFilterFromListener(asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(interruptionFilterFromListener);
                    return true;
                case 90:
                    android.service.notification.INotificationListener asInterface19 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnNotificationPostedTrimFromListener(asInterface19, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    java.lang.String readString91 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterruptionFilter(readString91, readInt60, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    android.service.notification.INotificationListener asInterface20 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString92 = parcel.readString();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.NotificationChannelGroup notificationChannelGroup3 = (android.app.NotificationChannelGroup) parcel.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupFromPrivilegedListener(asInterface20, readString92, userHandle2, notificationChannelGroup3);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    android.service.notification.INotificationListener asInterface21 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString93 = parcel.readString();
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.NotificationChannel notificationChannel4 = (android.app.NotificationChannel) parcel.readTypedObject(android.app.NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelFromPrivilegedListener(asInterface21, readString93, userHandle3, notificationChannel4);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    android.service.notification.INotificationListener asInterface22 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString94 = parcel.readString();
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelsFromPrivilegedListener = getNotificationChannelsFromPrivilegedListener(asInterface22, readString94, userHandle4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsFromPrivilegedListener, 1);
                    return true;
                case 95:
                    android.service.notification.INotificationListener asInterface23 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString95 = parcel.readString();
                    android.os.UserHandle userHandle5 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice notificationChannelGroupsFromPrivilegedListener = getNotificationChannelGroupsFromPrivilegedListener(asInterface23, readString95, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsFromPrivilegedListener, 1);
                    return true;
                case 96:
                    android.service.notification.INotificationListener asInterface24 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    android.service.notification.Adjustment adjustment = (android.service.notification.Adjustment) parcel.readTypedObject(android.service.notification.Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyEnqueuedAdjustmentFromAssistant(asInterface24, adjustment);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    android.service.notification.INotificationListener asInterface25 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    android.service.notification.Adjustment adjustment2 = (android.service.notification.Adjustment) parcel.readTypedObject(android.service.notification.Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentFromAssistant(asInterface25, adjustment2);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    android.service.notification.INotificationListener asInterface26 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.service.notification.Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentsFromAssistant(asInterface26, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    android.service.notification.INotificationListener asInterface27 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromAssistant(asInterface27, readString96);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    android.service.notification.INotificationListener asInterface28 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString97 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromSystemListener(asInterface28, readString97);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    android.content.ComponentName effectsSuppressor = getEffectsSuppressor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(effectsSuppressor, 1);
                    return true;
                case 102:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean matchesCallFilter = matchesCallFilter(bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(matchesCallFilter);
                    return true;
                case 103:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cleanUpCallersAfter(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    java.lang.String readString98 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSystemConditionProviderEnabled = isSystemConditionProviderEnabled(readString98);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemConditionProviderEnabled);
                    return true;
                case 105:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerAccessGranted = isNotificationListenerAccessGranted(componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerAccessGranted);
                    return true;
                case 106:
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerAccessGrantedForUser = isNotificationListenerAccessGrantedForUser(componentName6, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerAccessGrantedForUser);
                    return true;
                case 107:
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNotificationAssistantAccessGranted = isNotificationAssistantAccessGranted(componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationAssistantAccessGranted);
                    return true;
                case 108:
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean19 = parcel.readBoolean();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGranted(componentName8, readBoolean19, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    android.content.ComponentName componentName9 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGranted(componentName9, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    android.content.ComponentName componentName10 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt62 = parcel.readInt();
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGrantedForUser(componentName10, readInt62, readBoolean22, readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    android.content.ComponentName componentName11 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt63 = parcel.readInt();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGrantedForUser(componentName11, readInt63, readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    java.util.List<java.lang.String> enabledNotificationListenerPackages = getEnabledNotificationListenerPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(enabledNotificationListenerPackages);
                    return true;
                case 113:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.ComponentName> enabledNotificationListeners = getEnabledNotificationListeners(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledNotificationListeners, 1);
                    return true;
                case 114:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName allowedNotificationAssistantForUser = getAllowedNotificationAssistantForUser(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedNotificationAssistantForUser, 1);
                    return true;
                case 115:
                    android.content.ComponentName allowedNotificationAssistant = getAllowedNotificationAssistant();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedNotificationAssistant, 1);
                    return true;
                case 116:
                    android.content.ComponentName defaultNotificationAssistant = getDefaultNotificationAssistant();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultNotificationAssistant, 1);
                    return true;
                case 117:
                    int readInt66 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNASMigrationDoneAndResetDefault(readInt66, readBoolean25);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    java.lang.String readString99 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasEnabledNotificationListener = hasEnabledNotificationListener(readString99, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnabledNotificationListener);
                    return true;
                case 119:
                    int zenMode = getZenMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(zenMode);
                    return true;
                case 120:
                    android.service.notification.ZenModeConfig zenModeConfig = getZenModeConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(zenModeConfig, 1);
                    return true;
                case 121:
                    android.app.NotificationManager.Policy consolidatedNotificationPolicy = getConsolidatedNotificationPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(consolidatedNotificationPolicy, 1);
                    return true;
                case 122:
                    int readInt68 = parcel.readInt();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString100 = parcel.readString();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setZenMode(readInt68, uri, readString100, readBoolean26);
                    return true;
                case 123:
                    java.lang.String readString101 = parcel.readString();
                    android.service.notification.IConditionProvider asInterface29 = android.service.notification.IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    android.service.notification.Condition[] conditionArr = (android.service.notification.Condition[]) parcel.createTypedArray(android.service.notification.Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyConditions(readString101, asInterface29, conditionArr);
                    return true;
                case 124:
                    java.lang.String readString102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationPolicyAccessGranted = isNotificationPolicyAccessGranted(readString102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationPolicyAccessGranted);
                    return true;
                case 125:
                    java.lang.String readString103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.NotificationManager.Policy notificationPolicy = getNotificationPolicy(readString103);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationPolicy, 1);
                    return true;
                case 126:
                    java.lang.String readString104 = parcel.readString();
                    android.app.NotificationManager.Policy policy = (android.app.NotificationManager.Policy) parcel.readTypedObject(android.app.NotificationManager.Policy.CREATOR);
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicy(readString104, policy, readBoolean27);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    java.lang.String readString105 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationPolicyAccessGrantedForPackage = isNotificationPolicyAccessGrantedForPackage(readString105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationPolicyAccessGrantedForPackage);
                    return true;
                case 128:
                    java.lang.String readString106 = parcel.readString();
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGranted(readString106, readBoolean28);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    java.lang.String readString107 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGrantedForUser(readString107, readInt69, readBoolean29);
                    parcel2.writeNoException();
                    return true;
                case 130:
                    android.service.notification.ZenPolicy defaultZenPolicy = getDefaultZenPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultZenPolicy, 1);
                    return true;
                case 131:
                    java.lang.String readString108 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.AutomaticZenRule automaticZenRule = getAutomaticZenRule(readString108);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(automaticZenRule, 1);
                    return true;
                case 132:
                    java.util.Map<java.lang.String, android.app.AutomaticZenRule> automaticZenRules = getAutomaticZenRules();
                    parcel2.writeNoException();
                    if (automaticZenRules == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(automaticZenRules.size());
                        automaticZenRules.forEach(new java.util.function.BiConsumer() { // from class: android.app.INotificationManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.app.INotificationManager.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (android.app.AutomaticZenRule) obj2);
                            }
                        });
                    }
                    return true;
                case 133:
                    java.util.List<android.service.notification.ZenModeConfig.ZenRule> zenRules = getZenRules();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(zenRules, 1);
                    return true;
                case 134:
                    android.app.AutomaticZenRule automaticZenRule2 = (android.app.AutomaticZenRule) parcel.readTypedObject(android.app.AutomaticZenRule.CREATOR);
                    java.lang.String readString109 = parcel.readString();
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.lang.String addAutomaticZenRule = addAutomaticZenRule(automaticZenRule2, readString109, readBoolean30);
                    parcel2.writeNoException();
                    parcel2.writeString(addAutomaticZenRule);
                    return true;
                case 135:
                    java.lang.String readString110 = parcel.readString();
                    android.app.AutomaticZenRule automaticZenRule3 = (android.app.AutomaticZenRule) parcel.readTypedObject(android.app.AutomaticZenRule.CREATOR);
                    boolean readBoolean31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean updateAutomaticZenRule = updateAutomaticZenRule(readString110, automaticZenRule3, readBoolean31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateAutomaticZenRule);
                    return true;
                case 136:
                    java.lang.String readString111 = parcel.readString();
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean removeAutomaticZenRule = removeAutomaticZenRule(readString111, readBoolean32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAutomaticZenRule);
                    return true;
                case 137:
                    java.lang.String readString112 = parcel.readString();
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean removeAutomaticZenRules = removeAutomaticZenRules(readString112, readBoolean33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAutomaticZenRules);
                    return true;
                case 138:
                    android.content.ComponentName componentName12 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int ruleInstanceCount = getRuleInstanceCount(componentName12);
                    parcel2.writeNoException();
                    parcel2.writeInt(ruleInstanceCount);
                    return true;
                case 139:
                    java.lang.String readString113 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int automaticZenRuleState = getAutomaticZenRuleState(readString113);
                    parcel2.writeNoException();
                    parcel2.writeInt(automaticZenRuleState);
                    return true;
                case 140:
                    java.lang.String readString114 = parcel.readString();
                    android.service.notification.Condition condition = (android.service.notification.Condition) parcel.readTypedObject(android.service.notification.Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAutomaticZenRuleState(readString114, condition);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 142:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    java.lang.String readString115 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice appActiveNotifications = getAppActiveNotifications(readString115, readInt72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appActiveNotifications, 1);
                    return true;
                case 144:
                    java.lang.String readString116 = parcel.readString();
                    java.lang.String readString117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setNotificationDelegate(readString116, readString117);
                    parcel2.writeNoException();
                    return true;
                case 145:
                    java.lang.String readString118 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String notificationDelegate = getNotificationDelegate(readString118);
                    parcel2.writeNoException();
                    parcel2.writeString(notificationDelegate);
                    return true;
                case 146:
                    java.lang.String readString119 = parcel.readString();
                    java.lang.String readString120 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canNotifyAsPackage = canNotifyAsPackage(readString119, readString120, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canNotifyAsPackage);
                    return true;
                case 147:
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canUseFullScreenIntent = canUseFullScreenIntent(attributionSource);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUseFullScreenIntent);
                    return true;
                case 148:
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPrivateNotificationsAllowed(readBoolean34);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    boolean privateNotificationsAllowed = getPrivateNotificationsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(privateNotificationsAllowed);
                    return true;
                case 150:
                    long readLong5 = parcel.readLong();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean35 = parcel.readBoolean();
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    parcel.enforceNoDataAvail();
                    long pullStats = pullStats(readLong5, readInt74, readBoolean35, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeLong(pullStats);
                    parcel2.writeTypedList(arrayList, 1);
                    return true;
                case 151:
                    android.content.ComponentName componentName13 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.service.notification.NotificationListenerFilter listenerFilter = getListenerFilter(componentName13, readInt75);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(listenerFilter, 1);
                    return true;
                case 152:
                    android.content.ComponentName componentName14 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt76 = parcel.readInt();
                    android.service.notification.NotificationListenerFilter notificationListenerFilter = (android.service.notification.NotificationListenerFilter) parcel.readTypedObject(android.service.notification.NotificationListenerFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    setListenerFilter(componentName14, readInt76, notificationListenerFilter);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    android.service.notification.INotificationListener asInterface30 = android.service.notification.INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt77 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    migrateNotificationFilter(asInterface30, readInt77, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 154:
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToastRateLimitingEnabled(readBoolean36);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    java.lang.String readString121 = parcel.readString();
                    android.os.UserHandle userHandle6 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.ICallNotificationEventCallback asInterface31 = android.app.ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallNotificationEventListener(readString121, userHandle6, asInterface31);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    java.lang.String readString122 = parcel.readString();
                    android.os.UserHandle userHandle7 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.app.ICallNotificationEventCallback asInterface32 = android.app.ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallNotificationEventListener(readString122, userHandle7, asInterface32);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, android.app.AutomaticZenRule automaticZenRule) {
            parcel.writeString(str);
            parcel.writeTypedObject(automaticZenRule, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.app.INotificationManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.INotificationManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.INotificationManager
            public void cancelAllNotifications(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearData(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueTextToast(java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, boolean z, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iTransientNotificationCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueToast(java.lang.String str, android.os.IBinder iBinder, android.app.ITransientNotification iTransientNotification, int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTransientNotification);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void finishToken(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.app.Notification notification, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notification, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInCall(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setShowBadge(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canShowBadge(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidMsg(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInInvalidMsgState(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasUserDemotedInvalidMsgApp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInvalidMsgAppDemoted(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidBubble(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledWithImportanceLockForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabledForPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getPackageImportance(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isImportanceLocked(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationSoundTimeout(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public long getNotificationSoundTimeout(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.util.List<java.lang.String> getAllowedAssistantAdjustments(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean shouldHideSilentStatusIcons(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setHideSilentStatusIcons(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setBubblesAllowed(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesAllowed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBubblePreferenceForPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelGroups(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannels(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelsForPackage(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getConversations(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getConversationsForPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelGroupsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannelGroup getNotificationChannelGroupForPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannelGroup) obtain2.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(java.lang.String str, int i, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannelGroup) obtain2.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupForPackage(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockNotificationChannel(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockAllNotificationChannels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannel) obtain2.readTypedObject(android.app.NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannel getConversationNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str4);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannel) obtain2.readTypedObject(android.app.NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createConversationNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannel, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannel getNotificationChannelForPackage(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannel) obtain2.readTypedObject(android.app.NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannel(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannels(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getDeletedChannelCount(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBlockedChannelCount(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationChannelGroup) obtain2.readTypedObject(android.app.NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelGroups(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean onlyHasDefaultChannel(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areChannelsBypassingDnd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelsBypassingDnd(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPackagePaused(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationHistoryItem(java.lang.String str, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPermissionFixed(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void silenceNotificationSound() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.StatusBarNotification[]) obtain2.createTypedArray(android.service.notification.StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.StatusBarNotification[] getActiveNotificationsWithAttribution(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.StatusBarNotification[]) obtain2.createTypedArray(android.service.notification.StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.StatusBarNotification[] getHistoricalNotifications(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.StatusBarNotification[]) obtain2.createTypedArray(android.service.notification.StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.StatusBarNotification[] getHistoricalNotificationsWithAttribution(java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.StatusBarNotification[]) obtain2.createTypedArray(android.service.notification.StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationHistory getNotificationHistory(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationHistory) obtain2.readTypedObject(android.app.NotificationHistory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerListener(android.service.notification.INotificationListener iNotificationListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilContextFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindListener(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListenerComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindProvider(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindProvider(android.service.notification.IConditionProvider iConditionProvider) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConditionProvider);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsShownFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getActiveNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getSnoozedNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearRequestedListenerHints(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestHintsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListenerNoToken() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setOnNotificationPostedTrimFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInterruptionFilter(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyEnqueuedAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentsFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.util.List<android.service.notification.Adjustment> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromSystemListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.ComponentName getEffectsSuppressor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean matchesCallFilter(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cleanUpCallersAfter(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isSystemConditionProviderEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationAssistantAccessGranted(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGranted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.util.List<java.lang.String> getEnabledNotificationListenerPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.util.List<android.content.ComponentName> getEnabledNotificationListeners(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.ComponentName getAllowedNotificationAssistantForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.ComponentName getAllowedNotificationAssistant() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.ComponentName getDefaultNotificationAssistant() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNASMigrationDoneAndResetDefault(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasEnabledNotificationListener(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getZenMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.ZenModeConfig getZenModeConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.ZenModeConfig) obtain2.readTypedObject(android.service.notification.ZenModeConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationManager.Policy) obtain2.readTypedObject(android.app.NotificationManager.Policy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setZenMode(int i, android.net.Uri uri, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(122, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void notifyConditions(java.lang.String str, android.service.notification.IConditionProvider iConditionProvider, android.service.notification.Condition[] conditionArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iConditionProvider);
                    obtain.writeTypedArray(conditionArr, 0);
                    this.mRemote.transact(123, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGranted(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.NotificationManager.Policy getNotificationPolicy(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.NotificationManager.Policy) obtain2.readTypedObject(android.app.NotificationManager.Policy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicy(java.lang.String str, android.app.NotificationManager.Policy policy, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(policy, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGrantedForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGranted(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGrantedForUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.ZenPolicy getDefaultZenPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.ZenPolicy) obtain2.readTypedObject(android.service.notification.ZenPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.AutomaticZenRule) obtain2.readTypedObject(android.app.AutomaticZenRule.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.app.INotificationManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), (android.app.AutomaticZenRule) android.os.Parcel.this.readTypedObject(android.app.AutomaticZenRule.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.util.List<android.service.notification.ZenModeConfig.ZenRule> getZenRules() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.service.notification.ZenModeConfig.ZenRule.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(automaticZenRule, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(automaticZenRule, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRule(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRules(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getRuleInstanceCount(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAutomaticZenRuleState(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(condition, 0);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public byte[] getBackupPayload(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.content.pm.ParceledListSlice getAppActiveNotifications(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationDelegate(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public java.lang.String getNotificationDelegate(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canNotifyAsPackage(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canUseFullScreenIntent(android.content.AttributionSource attributionSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setPrivateNotificationsAllowed(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean getPrivateNotificationsAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public long pullStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    obtain2.readTypedList(list, android.os.ParcelFileDescriptor.CREATOR);
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public android.service.notification.NotificationListenerFilter getListenerFilter(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.service.notification.NotificationListenerFilter) obtain2.readTypedObject(android.service.notification.NotificationListenerFilter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setListenerFilter(android.content.ComponentName componentName, int i, android.service.notification.NotificationListenerFilter notificationListenerFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationListenerFilter, 0);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void migrateNotificationFilter(android.service.notification.INotificationListener iNotificationListener, int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setToastRateLimitingEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.INotificationManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getActiveNotificationsWithAttribution_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void getHistoricalNotificationsWithAttribution_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void getNotificationHistory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void setToastRateLimitingEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_TOAST_RATE_LIMITING, getCallingPid(), getCallingUid());
        }

        protected void registerCallNotificationEventListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_registerCallNotificationEventListener, getCallingPid(), getCallingUid());
        }

        protected void unregisterCallNotificationEventListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_unregisterCallNotificationEventListener, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 155;
        }
    }
}
