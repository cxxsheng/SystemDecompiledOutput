package android.app;

/* loaded from: classes.dex */
public abstract class ActivityManagerInternal {
    public static final int ALLOW_FULL_ONLY = 2;
    public static final int ALLOW_NON_FULL = 0;
    public static final int ALLOW_NON_FULL_IN_PROFILE = 1;
    public static final int ALLOW_PROFILES_OR_NON_FULL = 3;
    public static final int MEDIA_PROJECTION_TOKEN_EVENT_CREATED = 0;
    public static final int MEDIA_PROJECTION_TOKEN_EVENT_DESTROYED = 1;
    public static final int OOM_ADJ_REASON_ACTIVITY = 1;
    public static final int OOM_ADJ_REASON_ALLOWLIST = 10;
    public static final int OOM_ADJ_REASON_BACKUP = 15;
    public static final int OOM_ADJ_REASON_BIND_SERVICE = 4;
    public static final int OOM_ADJ_REASON_COMPONENT_DISABLED = 22;
    public static final int OOM_ADJ_REASON_EXECUTING_SERVICE = 20;
    public static final int OOM_ADJ_REASON_FINISH_RECEIVER = 2;
    public static final int OOM_ADJ_REASON_GET_PROVIDER = 7;
    public static final int OOM_ADJ_REASON_NONE = 0;
    public static final int OOM_ADJ_REASON_PROCESS_BEGIN = 11;
    public static final int OOM_ADJ_REASON_PROCESS_END = 12;
    public static final int OOM_ADJ_REASON_REMOVE_PROVIDER = 8;
    public static final int OOM_ADJ_REASON_REMOVE_TASK = 17;
    public static final int OOM_ADJ_REASON_RESTRICTION_CHANGE = 21;
    public static final int OOM_ADJ_REASON_SHELL = 16;
    public static final int OOM_ADJ_REASON_SHORT_FGS_TIMEOUT = 13;
    public static final int OOM_ADJ_REASON_START_RECEIVER = 3;
    public static final int OOM_ADJ_REASON_START_SERVICE = 6;
    public static final int OOM_ADJ_REASON_STOP_SERVICE = 19;
    public static final int OOM_ADJ_REASON_SYSTEM_INIT = 14;
    public static final int OOM_ADJ_REASON_UID_IDLE = 18;
    public static final int OOM_ADJ_REASON_UI_VISIBILITY = 9;
    public static final int OOM_ADJ_REASON_UNBIND_SERVICE = 5;

    public interface BindServiceEventListener {
        void onBindingService(java.lang.String str, int i);
    }

    public interface BroadcastEventListener {
        void onSendingBroadcast(java.lang.String str, int i);
    }

    public interface ForegroundServiceStateListener {
        void onForegroundServiceNotificationUpdated(java.lang.String str, int i, int i2, boolean z);

        void onForegroundServiceStateChanged(java.lang.String str, int i, int i2, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaProjectionTokenEvent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OomAdjReason {
    }

    public enum ServiceNotificationPolicy {
        NOT_FOREGROUND_SERVICE,
        SHOW_IMMEDIATELY,
        UPDATE_ONLY
    }

    public interface VoiceInteractionManagerProvider {
        void notifyActivityDestroyed(android.os.IBinder iBinder);
    }

    public abstract void addAppBackgroundRestrictionListener(android.app.ActivityManagerInternal.AppBackgroundRestrictionListener appBackgroundRestrictionListener);

    public abstract void addBindServiceEventListener(android.app.ActivityManagerInternal.BindServiceEventListener bindServiceEventListener);

    public abstract void addBroadcastEventListener(android.app.ActivityManagerInternal.BroadcastEventListener broadcastEventListener);

    public abstract void addForegroundServiceStateListener(android.app.ActivityManagerInternal.ForegroundServiceStateListener foregroundServiceStateListener);

    public abstract void addPendingTopUid(int i, int i2, android.app.IApplicationThread iApplicationThread);

    public abstract void appNotResponding(java.lang.String str, int i, com.android.internal.os.TimeoutRecord timeoutRecord);

    public abstract android.app.ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotification(android.app.Notification notification, java.lang.String str, int i, java.lang.String str2, int i2);

    public abstract void broadcastCloseSystemDialogs(java.lang.String str);

    public abstract void broadcastGlobalConfigurationChanged(int i, boolean z);

    public abstract int broadcastIntent(android.content.Intent intent, android.content.IIntentReceiver iIntentReceiver, java.lang.String[] strArr, boolean z, int i, int[] iArr, java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, android.os.Bundle bundle);

    public abstract int broadcastIntentInPackage(java.lang.String str, java.lang.String str2, int i, int i2, int i3, android.content.Intent intent, java.lang.String str3, android.app.IApplicationThread iApplicationThread, android.content.IIntentReceiver iIntentReceiver, int i4, java.lang.String str4, android.os.Bundle bundle, java.lang.String str5, android.os.Bundle bundle2, boolean z, boolean z2, int i5, android.app.BackgroundStartPrivileges backgroundStartPrivileges, int[] iArr);

    public abstract int broadcastIntentWithCallback(android.content.Intent intent, android.content.IIntentReceiver iIntentReceiver, java.lang.String[] strArr, int i, int[] iArr, java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, android.os.Bundle bundle);

    public abstract boolean canAllowWhileInUsePermissionInFgs(int i, int i2, java.lang.String str);

    public abstract boolean canScheduleUserInitiatedJobs(int i, int i2, java.lang.String str);

    public abstract boolean canStartForegroundService(int i, int i2, java.lang.String str);

    public abstract boolean canStartMoreUsers();

    public abstract java.lang.String checkContentProviderAccess(java.lang.String str, int i);

    public abstract int checkContentProviderUriPermission(android.net.Uri uri, int i, int i2, int i3);

    public abstract void cleanUpServices(int i, android.content.ComponentName componentName, android.content.Intent intent);

    public abstract boolean clearApplicationUserData(java.lang.String str, boolean z, boolean z2, android.content.pm.IPackageDataObserver iPackageDataObserver, int i);

    public abstract void clearPendingBackup(int i);

    public abstract void clearPendingIntentAllowBgActivityStarts(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder);

    public abstract void deletePendingTopUid(int i, long j);

    public abstract void disconnectActivityFromServices(java.lang.Object obj);

    public abstract void enforceBroadcastOptionsPermissions(android.os.Bundle bundle, int i);

    public abstract void enforceCallingPermission(java.lang.String str, java.lang.String str2);

    public abstract void ensureBootCompleted();

    public abstract void ensureNotSpecialUser(int i);

    public abstract void finishBooting();

    public abstract void finishUserSwitch(java.lang.Object obj);

    public abstract android.content.pm.ActivityInfo getActivityInfoForUser(android.content.pm.ActivityInfo activityInfo, int i);

    public abstract android.content.pm.ActivityPresentationInfo getActivityPresentationInfo(android.os.IBinder iBinder);

    public abstract android.util.Pair<java.lang.String, java.lang.String> getAppProfileStatsForDebugging(long j, int i);

    public abstract android.app.BackgroundStartPrivileges getBackgroundStartPrivileges(int i);

    public abstract long getBootTimeTempAllowListDuration();

    public abstract java.lang.Object getCachedAppsHighWatermarkStats(int i, boolean z);

    public abstract android.util.ArraySet<java.lang.String> getClientPackages(java.lang.String str);

    public abstract android.util.Pair<java.lang.Integer, java.lang.Integer> getCurrentAndTargetUserIds();

    public abstract int[] getCurrentProfileIds();

    public abstract android.content.pm.UserInfo getCurrentUser();

    public abstract int getCurrentUserId();

    public abstract int getInstrumentationSourceUid(int i);

    public abstract android.content.Intent getIntentForIntentSender(android.content.IIntentSender iIntentSender);

    public abstract java.util.List<java.lang.Integer> getIsolatedProcesses(int i);

    public abstract int getMaxRunningUsers();

    public abstract java.util.List<android.app.ProcessMemoryState> getMemoryStateForProcesses();

    public abstract java.lang.String getPackageNameByPid(int i);

    public abstract android.app.PendingIntent getPendingIntentActivityAsApp(int i, android.content.Intent intent, int i2, android.os.Bundle bundle, java.lang.String str, int i3);

    public abstract android.app.PendingIntent getPendingIntentActivityAsApp(int i, android.content.Intent[] intentArr, int i2, android.os.Bundle bundle, java.lang.String str, int i3);

    public abstract int getPendingIntentFlags(android.content.IIntentSender iIntentSender);

    public abstract java.util.List<android.app.PendingIntentStats> getPendingIntentStats();

    public abstract java.util.Map<java.lang.Integer, java.lang.String> getProcessesWithPendingBindMounts(int i);

    public abstract int getPushMessagingOverQuotaBehavior();

    public abstract android.app.IUnsafeIntentStrictModeCallback getRegisteredStrictModeCallback(int i);

    public abstract int getRestrictionLevel(int i);

    public abstract int getRestrictionLevel(java.lang.String str, int i);

    public abstract int getServiceStartForegroundTimeout();

    public abstract int[] getStartedUserIds();

    public abstract int getStorageMountMode(int i, int i2);

    public abstract int getTaskIdForActivity(android.os.IBinder iBinder, boolean z);

    public abstract int getUidCapability(int i);

    public abstract int getUidProcessState(int i);

    public abstract int handleIncomingUser(int i, int i2, int i3, boolean z, int i4, java.lang.String str, java.lang.String str2);

    public abstract boolean hasForegroundServiceNotification(java.lang.String str, int i, java.lang.String str2);

    public abstract boolean hasRunningActivity(int i, java.lang.String str);

    public abstract boolean hasRunningForegroundService(int i, int i2);

    public abstract boolean hasStartedUserState(int i);

    public abstract void inputDispatchingResumed(int i);

    public abstract long inputDispatchingTimedOut(int i, boolean z, com.android.internal.os.TimeoutRecord timeoutRecord);

    public abstract boolean inputDispatchingTimedOut(java.lang.Object obj, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, java.lang.Object obj2, boolean z, com.android.internal.os.TimeoutRecord timeoutRecord);

    public abstract boolean isActivityStartsLoggingEnabled();

    public abstract boolean isAppBad(java.lang.String str, int i);

    public abstract boolean isAppForeground(int i);

    public abstract boolean isAppStartModeDisabled(int i, java.lang.String str);

    public abstract boolean isAssociatedCompanionApp(int i, int i2);

    public abstract boolean isBackgroundActivityStartsEnabled();

    public abstract boolean isBgAutoRestrictedBucketFeatureFlagEnabled();

    public abstract boolean isBooted();

    public abstract boolean isBooting();

    public abstract boolean isCurrentProfile(int i);

    public abstract boolean isDeviceOwner(int i);

    public abstract boolean isModernQueueEnabled();

    public abstract boolean isPendingTopUid(int i);

    public abstract boolean isProfileOwner(int i);

    public abstract boolean isRuntimeRestarted();

    public abstract boolean isSystemReady();

    public abstract boolean isTempAllowlistedForFgsWhileInUse(int i);

    public abstract boolean isThemeOverlayReady(int i);

    public abstract boolean isUidActive(int i);

    public abstract boolean isUserRunning(int i, int i2);

    public abstract void killAllBackgroundProcessesExcept(int i, int i2);

    public abstract void killForegroundAppsForUser(int i);

    public abstract void killProcess(java.lang.String str, int i, java.lang.String str2);

    public abstract void killProcessesForRemovedTask(java.util.ArrayList<java.lang.Object> arrayList);

    public abstract void logFgsApiBegin(int i, int i2, int i3);

    public abstract void logFgsApiEnd(int i, int i2, int i3);

    public abstract void monitor();

    public abstract void noteAlarmFinish(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str);

    public abstract void noteAlarmStart(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str);

    public abstract void noteWakeupAlarm(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2);

    public abstract void notifyMediaProjectionEvent(int i, android.os.IBinder iBinder, int i2);

    public abstract void notifyNetworkPolicyRulesUpdated(int i, long j);

    public abstract void onForegroundServiceNotificationUpdate(boolean z, android.app.Notification notification, int i, java.lang.String str, int i2);

    public abstract void onUidBlockedReasonsChanged(int i, int i2);

    public abstract void onUserRemoved(int i);

    public abstract void onWakefulnessChanged(int i);

    public abstract void prepareForPossibleShutdown();

    public abstract void registerAnrController(android.app.AnrController anrController);

    public abstract void registerNetworkPolicyUidObserver(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str);

    public abstract void registerProcessObserver(android.app.IProcessObserver iProcessObserver);

    public abstract void reportCurKeyguardUsageEvent(boolean z);

    public abstract void rescheduleAnrDialog(java.lang.Object obj);

    public abstract void restart();

    public abstract void scheduleAppGcs();

    public abstract void sendForegroundProfileChanged(int i);

    public abstract int sendIntentSender(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, int i, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle);

    public abstract void setBooted(boolean z);

    public abstract void setBooting(boolean z);

    public abstract void setCompanionAppUids(int i, java.util.Set<java.lang.Integer> set);

    public abstract void setDebugFlagsForStartingActivity(android.content.pm.ActivityInfo activityInfo, int i, android.app.ProfilerInfo profilerInfo, java.lang.Object obj);

    public abstract void setDeviceIdleAllowlist(int[] iArr, int[] iArr2);

    public abstract void setDeviceOwnerUid(int i);

    public abstract void setHasOverlayUi(int i, boolean z);

    public abstract void setPendingIntentAllowBgActivityStarts(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, int i);

    public abstract void setPendingIntentAllowlistDuration(android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, long j, int i, int i2, java.lang.String str);

    public abstract void setProfileOwnerUid(android.util.ArraySet<java.lang.Integer> arraySet);

    public abstract void setStopUserOnSwitch(int i);

    public abstract void setSwitchingFromSystemUserMessage(java.lang.String str);

    public abstract void setSwitchingToSystemUserMessage(java.lang.String str);

    public abstract void setVoiceInteractionManagerProvider(android.app.ActivityManagerInternal.VoiceInteractionManagerProvider voiceInteractionManagerProvider);

    public abstract boolean shouldConfirmCredentials(int i);

    public abstract boolean startForegroundServiceDelegate(android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, android.content.ServiceConnection serviceConnection);

    public abstract boolean startIsolatedProcess(java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String str3, int i, java.lang.Runnable runnable);

    public abstract void startProcess(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, boolean z, boolean z2, java.lang.String str2, android.content.ComponentName componentName);

    public abstract boolean startProfileEvenWhenDisabled(int i);

    public abstract android.content.ComponentName startServiceInPackage(int i, android.content.Intent intent, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, int i2, android.app.BackgroundStartPrivileges backgroundStartPrivileges) throws android.os.TransactionTooLargeException;

    public abstract void stopAppForUser(java.lang.String str, int i);

    public abstract void stopForegroundServiceDelegate(android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions);

    public abstract void stopForegroundServiceDelegate(android.content.ServiceConnection serviceConnection);

    public abstract void tempAllowWhileInUsePermissionInFgs(int i, long j);

    public abstract void tempAllowlistForPendingIntent(int i, int i2, int i3, long j, int i4, int i5, java.lang.String str);

    public abstract void trimApplications();

    public abstract void unregisterAnrController(android.app.AnrController anrController);

    public abstract void unregisterProcessObserver(android.app.IProcessObserver iProcessObserver);

    public abstract void unregisterStrictModeCallback(int i);

    public abstract void updateActivityUsageStats(android.content.ComponentName componentName, int i, int i2, android.os.IBinder iBinder, android.content.ComponentName componentName2, android.app.assist.ActivityId activityId);

    public abstract void updateBatteryStats(android.content.ComponentName componentName, int i, int i2, boolean z);

    public abstract void updateCpuStats();

    public abstract void updateDeviceIdleTempAllowlist(int[] iArr, int i, boolean z, long j, int i2, int i3, java.lang.String str, int i4);

    public abstract void updateForegroundTimeIfOnBattery(java.lang.String str, int i, long j);

    public abstract void updateOomAdj(int i);

    public abstract void updateOomLevelsForDisplay(int i);

    public interface AppBackgroundRestrictionListener {
        default void onRestrictionLevelChanged(int i, java.lang.String str, int i2) {
        }

        default void onAutoRestrictedBucketFeatureFlagChanged(boolean z) {
        }
    }
}
