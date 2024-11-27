package android.app;

/* loaded from: classes.dex */
public interface IActivityManager extends android.os.IInterface {
    void addApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException;

    void addInstrumentationResults(android.app.IApplicationThread iApplicationThread, android.os.Bundle bundle) throws android.os.RemoteException;

    void addPackageDependency(java.lang.String str) throws android.os.RemoteException;

    void addStartInfoTimestamp(int i, long j, int i2) throws android.os.RemoteException;

    void addUidToObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void appNotResponding(java.lang.String str) throws android.os.RemoteException;

    void appNotRespondingViaProvider(android.os.IBinder iBinder) throws android.os.RemoteException;

    void attachApplication(android.app.IApplicationThread iApplicationThread, long j) throws android.os.RemoteException;

    void backgroundAllowlistUid(int i) throws android.os.RemoteException;

    void backupAgentCreated(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean bindBackupAgent(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    int bindService(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, int i) throws android.os.RemoteException;

    int bindServiceInstance(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    void bootAnimationComplete() throws android.os.RemoteException;

    @java.lang.Deprecated
    int broadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str2, android.os.Bundle bundle, java.lang.String[] strArr, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException;

    int broadcastIntentWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str3, android.os.Bundle bundle, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException;

    void cancelIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException;

    void cancelTaskWindowTransition(int i) throws android.os.RemoteException;

    int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    int checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    int checkPermissionForDevice(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException;

    int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean clearApplicationUserData(java.lang.String str, boolean z, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException;

    void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException;

    void crashApplicationWithType(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4) throws android.os.RemoteException;

    void crashApplicationWithTypeWithExtras(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean dumpHeap(java.lang.String str, int i, boolean z, boolean z2, boolean z3, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void dumpHeapFinished(java.lang.String str) throws android.os.RemoteException;

    boolean enableAppFreezer(boolean z) throws android.os.RemoteException;

    boolean enableFgsNotificationRateLimit(boolean z) throws android.os.RemoteException;

    void enterSafeMode() throws android.os.RemoteException;

    boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException;

    void finishAttachApplication(long j) throws android.os.RemoteException;

    void finishHeavyWeightApp() throws android.os.RemoteException;

    void finishInstrumentation(android.app.IApplicationThread iApplicationThread, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void finishReceiver(android.os.IBinder iBinder, int i, java.lang.String str, android.os.Bundle bundle, boolean z, int i2) throws android.os.RemoteException;

    void forceDelayBroadcastDelivery(java.lang.String str, long j) throws android.os.RemoteException;

    void forceStopPackage(java.lang.String str, int i) throws android.os.RemoteException;

    void forceStopPackageEvenWhenStopping(java.lang.String str, int i) throws android.os.RemoteException;

    void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException;

    int getBackgroundRestrictionExemptionReason(int i) throws android.os.RemoteException;

    int getBindingUidProcessState(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getBugreportWhitelistedPackages() throws android.os.RemoteException;

    android.content.res.Configuration getConfiguration() throws android.os.RemoteException;

    android.app.ContentProviderHolder getContentProvider(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException;

    android.app.ContentProviderHolder getContentProviderExternal(java.lang.String str, int i, android.os.IBinder iBinder, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.UserInfo getCurrentUser() throws android.os.RemoteException;

    int getCurrentUserId() throws android.os.RemoteException;

    java.util.List<java.lang.String> getDelegatedShellPermissions() throws android.os.RemoteException;

    int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws android.os.RemoteException;

    android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException;

    int getForegroundServiceType(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> getHistoricalProcessExitReasons(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> getHistoricalProcessStartReasons(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    android.app.ActivityManager.PendingIntentInfo getInfoForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException;

    android.content.Intent getIntentForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.content.IIntentSender getIntentSender(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.String str2, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException;

    android.content.IIntentSender getIntentSenderWithFeature(int i, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException;

    java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getLifeMonitor() throws android.os.RemoteException;

    int getLockTaskModeState() throws android.os.RemoteException;

    void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryInfo) throws android.os.RemoteException;

    int getMemoryTrimLevel() throws android.os.RemoteException;

    void getMimeTypeFilterAsync(android.net.Uri uri, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getMyMemoryState(android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws android.os.RemoteException;

    int getPackageProcessState(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getProcessLimit() throws android.os.RemoteException;

    android.os.Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws android.os.RemoteException;

    long[] getProcessPss(int[] iArr) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws android.os.RemoteException;

    java.util.List<android.content.pm.ApplicationInfo> getRunningExternalApplications() throws android.os.RemoteException;

    android.app.PendingIntent getRunningServiceControlPanel(android.content.ComponentName componentName) throws android.os.RemoteException;

    int[] getRunningUserIds() throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws android.os.RemoteException;

    java.lang.String getSwitchingFromUserMessage() throws android.os.RemoteException;

    java.lang.String getSwitchingToUserMessage() throws android.os.RemoteException;

    java.lang.String getTagForIntentSender(android.content.IIntentSender iIntentSender, java.lang.String str) throws android.os.RemoteException;

    android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException;

    int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i) throws android.os.RemoteException;

    int[] getUidFrozenState(int[] iArr) throws android.os.RemoteException;

    int getUidProcessCapabilities(int i, java.lang.String str) throws android.os.RemoteException;

    int getUidProcessState(int i, java.lang.String str) throws android.os.RemoteException;

    void grantUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException;

    void handleApplicationCrash(android.os.IBinder iBinder, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws android.os.RemoteException;

    void handleApplicationStrictModeViolation(android.os.IBinder iBinder, int i, android.os.StrictMode.ViolationInfo violationInfo) throws android.os.RemoteException;

    boolean handleApplicationWtf(android.os.IBinder iBinder, java.lang.String str, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws android.os.RemoteException;

    int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void hang(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    boolean hasServiceTimeLimitExceeded(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException;

    void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean isAppFreezerEnabled() throws android.os.RemoteException;

    boolean isAppFreezerSupported() throws android.os.RemoteException;

    boolean isBackgroundRestricted(java.lang.String str) throws android.os.RemoteException;

    boolean isInLockTaskMode() throws android.os.RemoteException;

    boolean isIntentSenderAnActivity(android.content.IIntentSender iIntentSender) throws android.os.RemoteException;

    boolean isIntentSenderTargetedToPackage(android.content.IIntentSender iIntentSender) throws android.os.RemoteException;

    boolean isModernBroadcastQueueEnabled() throws android.os.RemoteException;

    boolean isProcessFrozen(int i) throws android.os.RemoteException;

    boolean isTopActivityImmersive() throws android.os.RemoteException;

    boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isUidActive(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isUserAMonkey() throws android.os.RemoteException;

    boolean isUserRunning(int i, int i2) throws android.os.RemoteException;

    boolean isVrModePackageEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    void killAllBackgroundProcesses() throws android.os.RemoteException;

    void killApplication(java.lang.String str, int i, int i2, java.lang.String str2, int i3) throws android.os.RemoteException;

    void killApplicationProcess(java.lang.String str, int i) throws android.os.RemoteException;

    void killBackgroundProcesses(java.lang.String str, int i) throws android.os.RemoteException;

    void killPackageDependents(java.lang.String str, int i) throws android.os.RemoteException;

    boolean killPids(int[] iArr, java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean killProcessesBelowForeground(java.lang.String str) throws android.os.RemoteException;

    void killProcessesWhenImperceptible(int[] iArr, java.lang.String str) throws android.os.RemoteException;

    void killUid(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void killUidForPermissionChange(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean launchBugReportHandlerApp() throws android.os.RemoteException;

    void logFgsApiBegin(int i, int i2, int i3) throws android.os.RemoteException;

    void logFgsApiEnd(int i, int i2, int i3) throws android.os.RemoteException;

    void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void makePackageIdle(java.lang.String str, int i) throws android.os.RemoteException;

    boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException;

    void noteAlarmFinish(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException;

    void noteAlarmStart(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException;

    void noteWakeupAlarm(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void notifyCleartextNetwork(int i, byte[] bArr) throws android.os.RemoteException;

    void notifyLockedProfile(int i) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openContentUri(java.lang.String str) throws android.os.RemoteException;

    android.os.IBinder peekService(android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void performIdleMaintenance() throws android.os.RemoteException;

    boolean profileControl(java.lang.String str, int i, boolean z, android.app.ProfilerInfo profilerInfo, int i2) throws android.os.RemoteException;

    void publishContentProviders(android.app.IApplicationThread iApplicationThread, java.util.List<android.app.ContentProviderHolder> list) throws android.os.RemoteException;

    void publishService(android.os.IBinder iBinder, android.content.Intent intent, android.os.IBinder iBinder2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryIntentComponentsForIntentSender(android.content.IIntentSender iIntentSender, int i) throws android.os.RemoteException;

    boolean refContentProvider(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    boolean registerForegroundServiceObserver(android.app.IForegroundServiceObserver iForegroundServiceObserver) throws android.os.RemoteException;

    boolean registerIntentSenderCancelListenerEx(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void registerProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException;

    android.content.Intent registerReceiver(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    android.content.Intent registerReceiverWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str4, int i, int i2) throws android.os.RemoteException;

    void registerStrictModeCallback(android.os.IBinder iBinder) throws android.os.RemoteException;

    void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException;

    void registerUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException;

    void registerUidObserver(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str) throws android.os.RemoteException;

    android.os.IBinder registerUidObserverForUids(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str, int[] iArr) throws android.os.RemoteException;

    void registerUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver, java.lang.String str) throws android.os.RemoteException;

    void removeApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException;

    void removeContentProvider(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    @java.lang.Deprecated
    void removeContentProviderExternal(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void removeContentProviderExternalAsUser(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean removeTask(int i) throws android.os.RemoteException;

    void removeUidFromObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void requestBugReport(int i) throws android.os.RemoteException;

    void requestBugReportWithDescription(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void requestFullBugReport() throws android.os.RemoteException;

    void requestInteractiveBugReport() throws android.os.RemoteException;

    void requestInteractiveBugReportWithDescription(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void requestRemoteBugReport(long j) throws android.os.RemoteException;

    void requestSystemServerHeapDump() throws android.os.RemoteException;

    void requestTelephonyBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void requestWifiBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void resetAppErrors() throws android.os.RemoteException;

    void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException;

    void restart() throws android.os.RemoteException;

    int restartUserInBackground(int i, int i2) throws android.os.RemoteException;

    void resumeAppSwitches() throws android.os.RemoteException;

    void revokeUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException;

    void scheduleApplicationInfoChanged(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException;

    void sendIdleJobTrigger() throws android.os.RemoteException;

    int sendIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, int i, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void serviceDoneExecuting(android.os.IBinder iBinder, int i, int i2, int i3, android.content.Intent intent) throws android.os.RemoteException;

    void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException;

    void setActivityLocusContext(android.content.ComponentName componentName, android.content.LocusId locusId, android.os.IBinder iBinder) throws android.os.RemoteException;

    void setAgentApp(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setAlwaysFinish(boolean z) throws android.os.RemoteException;

    void setDebugApp(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    void setDeterministicUidIdle(boolean z) throws android.os.RemoteException;

    void setDumpHeapDebugLimit(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException;

    void setFocusedRootTask(int i) throws android.os.RemoteException;

    void setHasTopUi(boolean z) throws android.os.RemoteException;

    void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException;

    void setPersistentVrThread(int i) throws android.os.RemoteException;

    void setProcessImportant(android.os.IBinder iBinder, int i, boolean z, java.lang.String str) throws android.os.RemoteException;

    void setProcessLimit(int i) throws android.os.RemoteException;

    boolean setProcessMemoryTrimLevel(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setProcessStateSummary(byte[] bArr) throws android.os.RemoteException;

    void setRenderThread(int i) throws android.os.RemoteException;

    void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setServiceForeground(android.content.ComponentName componentName, android.os.IBinder iBinder, int i, android.app.Notification notification, int i2, int i3) throws android.os.RemoteException;

    void setStopUserOnSwitch(int i) throws android.os.RemoteException;

    void setTaskResizeable(int i, int i2) throws android.os.RemoteException;

    void setThemeOverlayReady(int i) throws android.os.RemoteException;

    void setUserIsMonkey(boolean z) throws android.os.RemoteException;

    boolean shouldServiceTimeOut(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException;

    void showBootMessage(java.lang.CharSequence charSequence, boolean z) throws android.os.RemoteException;

    void showWaitingForDebugger(android.app.IApplicationThread iApplicationThread, boolean z) throws android.os.RemoteException;

    boolean shutdown(int i) throws android.os.RemoteException;

    void signalPersistentProcesses(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException;

    @java.lang.Deprecated
    int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    int startActivityAsUserWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    int startActivityWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean startBinderTracking() throws android.os.RemoteException;

    void startConfirmDeviceCredentialIntent(android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException;

    void startDelegateShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, int i, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i2, java.lang.String str2) throws android.os.RemoteException;

    boolean startProfile(int i) throws android.os.RemoteException;

    boolean startProfileWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    android.content.ComponentName startService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    void startSystemLockTaskMode(int i) throws android.os.RemoteException;

    boolean startUserInBackground(int i) throws android.os.RemoteException;

    boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    boolean startUserInBackgroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    boolean startUserInForegroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    void stopAppForUser(java.lang.String str, int i) throws android.os.RemoteException;

    void stopAppSwitches() throws android.os.RemoteException;

    boolean stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void stopDelegateShellPermissionIdentity() throws android.os.RemoteException;

    boolean stopProfile(int i) throws android.os.RemoteException;

    int stopService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException;

    boolean stopServiceToken(android.content.ComponentName componentName, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    int stopUser(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException;

    int stopUserWithDelayedLocking(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException;

    void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException;

    boolean switchUser(int i) throws android.os.RemoteException;

    void unbindBackupAgent(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    void unbindFinished(android.os.IBinder iBinder, android.content.Intent intent, boolean z) throws android.os.RemoteException;

    boolean unbindService(android.app.IServiceConnection iServiceConnection) throws android.os.RemoteException;

    void unbroadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, int i) throws android.os.RemoteException;

    void unhandledBack() throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean unlockUser(int i, byte[] bArr, byte[] bArr2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    boolean unlockUser2(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException;

    void unregisterIntentSenderCancelListener(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException;

    void unregisterProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException;

    void unregisterReceiver(android.content.IIntentReceiver iIntentReceiver) throws android.os.RemoteException;

    void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException;

    void unregisterUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException;

    void unregisterUidObserver(android.app.IUidObserver iUidObserver) throws android.os.RemoteException;

    void unregisterUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver) throws android.os.RemoteException;

    void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException;

    void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    boolean updateMccMncConfiguration(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void updatePersistentConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException;

    void updatePersistentConfigurationWithAttribution(android.content.res.Configuration configuration, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void updateServiceGroup(android.app.IServiceConnection iServiceConnection, int i, int i2) throws android.os.RemoteException;

    void waitForBroadcastBarrier() throws android.os.RemoteException;

    void waitForBroadcastIdle() throws android.os.RemoteException;

    void waitForNetworkStateUpdate(long j) throws android.os.RemoteException;

    public static class Default implements android.app.IActivityManager {
        @Override // android.app.IActivityManager
        public android.os.ParcelFileDescriptor openContentUri(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerUidObserver(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUidObserver(android.app.IUidObserver iUidObserver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.os.IBinder registerUidObserverForUids(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void addUidToObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeUidFromObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isUidActive(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessState(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void logFgsApiBegin(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void logFgsApiEnd(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void handleApplicationCrash(android.os.IBinder iBinder, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unhandledBack() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public android.content.Intent registerReceiver(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.content.Intent registerReceiverWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str4, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void unregisterReceiver(android.content.IIntentReceiver iIntentReceiver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int broadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str2, android.os.Bundle bundle, java.lang.String[] strArr, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int broadcastIntentWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str3, android.os.Bundle bundle, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unbroadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishReceiver(android.os.IBinder iBinder, int i, java.lang.String str, android.os.Bundle bundle, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void attachApplication(android.app.IApplicationThread iApplicationThread, long j) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishAttachApplication(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public android.app.ContentProviderHolder getContentProvider(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void publishContentProviders(android.app.IApplicationThread iApplicationThread, java.util.List<android.app.ContentProviderHolder> list) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean refContentProvider(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public android.app.PendingIntent getRunningServiceControlPanel(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.content.ComponentName startService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int stopService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindService(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindServiceInstance(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void updateServiceGroup(android.app.IServiceConnection iServiceConnection, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean unbindService(android.app.IServiceConnection iServiceConnection) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void publishService(android.os.IBinder iBinder, android.content.Intent intent, android.os.IBinder iBinder2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDebugApp(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAgentApp(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAlwaysFinish(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, int i, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i2, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void addInstrumentationResults(android.app.IApplicationThread iApplicationThread, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishInstrumentation(android.app.IApplicationThread iApplicationThread, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.content.res.Configuration getConfiguration() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean updateMccMncConfiguration(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopServiceToken(android.content.ComponentName componentName, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setProcessLimit(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getProcessLimit() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3, int i4) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void grantUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void revokeUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void showWaitingForDebugger(android.app.IApplicationThread iApplicationThread, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void signalPersistentProcesses(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.content.pm.ParceledListSlice getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void serviceDoneExecuting(android.os.IBinder iBinder, int i, int i2, int i3, android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.content.IIntentSender getIntentSender(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.String str2, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.content.IIntentSender getIntentSenderWithFeature(int i, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void cancelIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.app.ActivityManager.PendingIntentInfo getInfoForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean registerIntentSenderCancelListenerEx(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void unregisterIntentSenderCancelListener(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void enterSafeMode() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteWakeupAlarm(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProvider(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unbindFinished(android.os.IBinder iBinder, android.content.Intent intent, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setProcessImportant(android.os.IBinder iBinder, int i, boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setServiceForeground(android.content.ComponentName componentName, android.os.IBinder iBinder, int i, android.app.Notification notification, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getForegroundServiceType(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.app.ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean clearApplicationUserData(java.lang.String str, boolean z, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppForUser(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean registerForegroundServiceObserver(android.app.IForegroundServiceObserver iForegroundServiceObserver) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void forceStopPackage(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceStopPackageEvenWhenStopping(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean killPids(int[] iArr, java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.app.ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.os.IBinder peekService(android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean profileControl(java.lang.String str, int i, boolean z, android.app.ProfilerInfo profilerInfo, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean shutdown(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppSwitches() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resumeAppSwitches() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean bindBackupAgent(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void backupAgentCreated(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unbindBackupAgent(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void addPackageDependency(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killApplication(java.lang.String str, int i, int i2, java.lang.String str2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.os.Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killApplicationProcess(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean handleApplicationWtf(android.os.IBinder iBinder, java.lang.String str, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killBackgroundProcesses(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isUserAMonkey() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.content.pm.ApplicationInfo> getRunningExternalApplications() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void finishHeavyWeightApp() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void handleApplicationStrictModeViolation(android.os.IBinder iBinder, int i, android.os.StrictMode.ViolationInfo violationInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void registerStrictModeCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isTopActivityImmersive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void crashApplicationWithType(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void crashApplicationWithTypeWithExtras(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void getMimeTypeFilterAsync(android.net.Uri uri, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean dumpHeap(java.lang.String str, int i, boolean z, boolean z2, boolean z3, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUserRunning(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean switchUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public java.lang.String getSwitchingFromUserMessage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public java.lang.String getSwitchingToUserMessage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void setStopUserOnSwitch(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean removeTask(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderTargetedToPackage(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfigurationWithAttribution(android.content.res.Configuration configuration, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public long[] getProcessPss(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void showBootMessage(java.lang.CharSequence charSequence, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killAllBackgroundProcesses() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.app.ContentProviderHolder getContentProviderExternal(java.lang.String str, int i, android.os.IBinder iBinder, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternal(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternalAsUser(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void getMyMemoryState(android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean killProcessesBelowForeground(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public android.content.pm.UserInfo getCurrentUser() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getCurrentUserId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderAnActivity(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUserWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUser(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserWithDelayedLocking(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void registerUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int[] getRunningUserIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void requestSystemServerHeapDump() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReport(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReportWithDescription(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestTelephonyBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestWifiBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReportWithDescription(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReport() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestFullBugReport() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestRemoteBugReport(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean launchBugReportHandlerApp() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public java.util.List<java.lang.String> getBugreportWhitelistedPackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.content.Intent getIntentForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killUid(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setUserIsMonkey(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void hang(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setFocusedRootTask(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void restart() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void performIdleMaintenance() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void appNotRespondingViaProvider(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean setProcessMemoryTrimLevel(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public java.lang.String getTagForIntentSender(android.content.IIntentSender iIntentSender, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackground(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isInLockTaskMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void startSystemLockTaskMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void bootAnimationComplete() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setThemeOverlayReady(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void notifyCleartextNetwork(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setTaskResizeable(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getLockTaskModeState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void setDumpHeapDebugLimit(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void dumpHeapFinished(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmStart(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmFinish(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getPackageProcessState(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean startBinderTracking() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser(int i, byte[] bArr, byte[] bArr2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser2(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killPackageDependents(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void makePackageIdle(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDeterministicUidIdle(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getMemoryTrimLevel() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isVrModePackageEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void notifyLockedProfile(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void startConfirmDeviceCredentialIntent(android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void sendIdleJobTrigger() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int sendIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, int i, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isBackgroundRestricted(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setRenderThread(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setHasTopUi(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void cancelTaskWindowTransition(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void scheduleApplicationInfoChanged(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setPersistentVrThread(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForNetworkStateUpdate(long j) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void backgroundAllowlistUid(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void startDelegateShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void stopDelegateShellPermissionIdentity() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public java.util.List<java.lang.String> getDelegatedShellPermissions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public android.os.ParcelFileDescriptor getLifeMonitor() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInForegroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void appNotResponding(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> getHistoricalProcessStartReasons(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void addApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void addStartInfoTimestamp(int i, long j, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> getHistoricalProcessExitReasons(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killProcessesWhenImperceptible(int[] iArr, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setActivityLocusContext(android.content.ComponentName componentName, android.content.LocusId locusId, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setProcessStateSummary(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isAppFreezerSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isAppFreezerEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killUidForPermissionChange(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resetAppErrors() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean enableAppFreezer(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean enableFgsNotificationRateLimit(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startProfile(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopProfile(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public android.content.pm.ParceledListSlice queryIntentComponentsForIntentSender(android.content.IIntentSender iIntentSender, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessCapabilities(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void waitForBroadcastIdle() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForBroadcastBarrier() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceDelayBroadcastDelivery(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isModernBroadcastQueueEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isProcessFrozen(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getBackgroundRestrictionExemptionReason(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startProfileWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int restartUserInBackground(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean shouldServiceTimeOut(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean hasServiceTimeLimitExceeded(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int[] getUidFrozenState(int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int checkPermissionForDevice(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getBindingUidProcessState(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IActivityManager {
        public static final java.lang.String DESCRIPTOR = "android.app.IActivityManager";
        static final int TRANSACTION_addApplicationStartInfoCompleteListener = 221;
        static final int TRANSACTION_addInstrumentationResults = 45;
        static final int TRANSACTION_addPackageDependency = 97;
        static final int TRANSACTION_addStartInfoTimestamp = 223;
        static final int TRANSACTION_addUidToObserver = 5;
        static final int TRANSACTION_appNotResponding = 219;
        static final int TRANSACTION_appNotRespondingViaProvider = 168;
        static final int TRANSACTION_attachApplication = 25;
        static final int TRANSACTION_backgroundAllowlistUid = 212;
        static final int TRANSACTION_backupAgentCreated = 94;
        static final int TRANSACTION_bindBackupAgent = 93;
        static final int TRANSACTION_bindService = 36;
        static final int TRANSACTION_bindServiceInstance = 37;
        static final int TRANSACTION_bootAnimationComplete = 177;
        static final int TRANSACTION_broadcastIntent = 21;
        static final int TRANSACTION_broadcastIntentWithFeature = 22;
        static final int TRANSACTION_cancelIntentSender = 65;
        static final int TRANSACTION_cancelTaskWindowTransition = 208;
        static final int TRANSACTION_checkContentUriPermissionFull = 54;
        static final int TRANSACTION_checkPermission = 9;
        static final int TRANSACTION_checkPermissionForDevice = 254;
        static final int TRANSACTION_checkUriPermission = 53;
        static final int TRANSACTION_checkUriPermissions = 55;
        static final int TRANSACTION_clearApplicationUserData = 80;
        static final int TRANSACTION_closeSystemDialogs = 99;
        static final int TRANSACTION_crashApplicationWithType = 110;
        static final int TRANSACTION_crashApplicationWithTypeWithExtras = 111;
        static final int TRANSACTION_dumpHeap = 113;
        static final int TRANSACTION_dumpHeapFinished = 186;
        static final int TRANSACTION_enableAppFreezer = 232;
        static final int TRANSACTION_enableFgsNotificationRateLimit = 233;
        static final int TRANSACTION_enterSafeMode = 69;
        static final int TRANSACTION_finishActivity = 17;
        static final int TRANSACTION_finishAttachApplication = 26;
        static final int TRANSACTION_finishHeavyWeightApp = 106;
        static final int TRANSACTION_finishInstrumentation = 46;
        static final int TRANSACTION_finishReceiver = 24;
        static final int TRANSACTION_forceDelayBroadcastDelivery = 241;
        static final int TRANSACTION_forceStopPackage = 83;
        static final int TRANSACTION_forceStopPackageEvenWhenStopping = 84;
        static final int TRANSACTION_frozenBinderTransactionDetected = 255;
        static final int TRANSACTION_getAllRootTaskInfos = 162;
        static final int TRANSACTION_getBackgroundRestrictionExemptionReason = 244;
        static final int TRANSACTION_getBindingUidProcessState = 256;
        static final int TRANSACTION_getBugreportWhitelistedPackages = 156;
        static final int TRANSACTION_getConfiguration = 47;
        static final int TRANSACTION_getContentProvider = 30;
        static final int TRANSACTION_getContentProviderExternal = 129;
        static final int TRANSACTION_getCurrentUser = 134;
        static final int TRANSACTION_getCurrentUserId = 135;
        static final int TRANSACTION_getDelegatedShellPermissions = 216;
        static final int TRANSACTION_getDisplayIdsForStartingVisibleBackgroundUsers = 248;
        static final int TRANSACTION_getFocusedRootTaskInfo = 165;
        static final int TRANSACTION_getForegroundServiceType = 76;
        static final int TRANSACTION_getHistoricalProcessExitReasons = 224;
        static final int TRANSACTION_getHistoricalProcessStartReasons = 220;
        static final int TRANSACTION_getInfoForIntentSender = 66;
        static final int TRANSACTION_getIntentForIntentSender = 157;
        static final int TRANSACTION_getIntentSender = 63;
        static final int TRANSACTION_getIntentSenderWithFeature = 64;
        static final int TRANSACTION_getLaunchedFromPackage = 158;
        static final int TRANSACTION_getLaunchedFromUid = 136;
        static final int TRANSACTION_getLifeMonitor = 217;
        static final int TRANSACTION_getLockTaskModeState = 184;
        static final int TRANSACTION_getMemoryInfo = 78;
        static final int TRANSACTION_getMemoryTrimLevel = 199;
        static final int TRANSACTION_getMimeTypeFilterAsync = 112;
        static final int TRANSACTION_getMyMemoryState = 132;
        static final int TRANSACTION_getPackageProcessState = 190;
        static final int TRANSACTION_getProcessLimit = 52;
        static final int TRANSACTION_getProcessMemoryInfo = 100;
        static final int TRANSACTION_getProcessPss = 126;
        static final int TRANSACTION_getProcessesInErrorState = 79;
        static final int TRANSACTION_getRecentTasks = 61;
        static final int TRANSACTION_getRunningAppProcesses = 87;
        static final int TRANSACTION_getRunningExternalApplications = 105;
        static final int TRANSACTION_getRunningServiceControlPanel = 33;
        static final int TRANSACTION_getRunningUserIds = 145;
        static final int TRANSACTION_getServices = 86;
        static final int TRANSACTION_getSwitchingFromUserMessage = 117;
        static final int TRANSACTION_getSwitchingToUserMessage = 118;
        static final int TRANSACTION_getTagForIntentSender = 171;
        static final int TRANSACTION_getTaskBounds = 169;
        static final int TRANSACTION_getTaskForActivity = 29;
        static final int TRANSACTION_getTasks = 27;
        static final int TRANSACTION_getUidFrozenState = 253;
        static final int TRANSACTION_getUidProcessCapabilities = 238;
        static final int TRANSACTION_getUidProcessState = 8;
        static final int TRANSACTION_grantUriPermission = 56;
        static final int TRANSACTION_handleApplicationCrash = 13;
        static final int TRANSACTION_handleApplicationStrictModeViolation = 107;
        static final int TRANSACTION_handleApplicationWtf = 102;
        static final int TRANSACTION_handleIncomingUser = 96;
        static final int TRANSACTION_hang = 161;
        static final int TRANSACTION_hasServiceTimeLimitExceeded = 250;
        static final int TRANSACTION_holdLock = 234;
        static final int TRANSACTION_isAppFreezerEnabled = 229;
        static final int TRANSACTION_isAppFreezerSupported = 228;
        static final int TRANSACTION_isBackgroundRestricted = 205;
        static final int TRANSACTION_isInLockTaskMode = 173;
        static final int TRANSACTION_isIntentSenderAnActivity = 138;
        static final int TRANSACTION_isIntentSenderTargetedToPackage = 123;
        static final int TRANSACTION_isModernBroadcastQueueEnabled = 242;
        static final int TRANSACTION_isProcessFrozen = 243;
        static final int TRANSACTION_isTopActivityImmersive = 109;
        static final int TRANSACTION_isTopOfTask = 176;
        static final int TRANSACTION_isUidActive = 7;
        static final int TRANSACTION_isUserAMonkey = 104;
        static final int TRANSACTION_isUserRunning = 114;
        static final int TRANSACTION_isVrModePackageEnabled = 200;
        static final int TRANSACTION_killAllBackgroundProcesses = 128;
        static final int TRANSACTION_killApplication = 98;
        static final int TRANSACTION_killApplicationProcess = 101;
        static final int TRANSACTION_killBackgroundProcesses = 103;
        static final int TRANSACTION_killPackageDependents = 196;
        static final int TRANSACTION_killPids = 85;
        static final int TRANSACTION_killProcessesBelowForeground = 133;
        static final int TRANSACTION_killProcessesWhenImperceptible = 225;
        static final int TRANSACTION_killUid = 159;
        static final int TRANSACTION_killUidForPermissionChange = 230;
        static final int TRANSACTION_launchBugReportHandlerApp = 155;
        static final int TRANSACTION_logFgsApiBegin = 10;
        static final int TRANSACTION_logFgsApiEnd = 11;
        static final int TRANSACTION_logFgsApiStateChanged = 12;
        static final int TRANSACTION_makePackageIdle = 197;
        static final int TRANSACTION_moveActivityTaskToBack = 77;
        static final int TRANSACTION_moveTaskToFront = 28;
        static final int TRANSACTION_moveTaskToRootTask = 163;
        static final int TRANSACTION_noteAlarmFinish = 189;
        static final int TRANSACTION_noteAlarmStart = 188;
        static final int TRANSACTION_noteWakeupAlarm = 70;
        static final int TRANSACTION_notifyCleartextNetwork = 181;
        static final int TRANSACTION_notifyLockedProfile = 201;
        static final int TRANSACTION_openContentUri = 1;
        static final int TRANSACTION_peekService = 88;
        static final int TRANSACTION_performIdleMaintenance = 167;
        static final int TRANSACTION_profileControl = 89;
        static final int TRANSACTION_publishContentProviders = 31;
        static final int TRANSACTION_publishService = 40;
        static final int TRANSACTION_queryIntentComponentsForIntentSender = 237;
        static final int TRANSACTION_refContentProvider = 32;
        static final int TRANSACTION_registerForegroundServiceObserver = 82;
        static final int TRANSACTION_registerIntentSenderCancelListenerEx = 67;
        static final int TRANSACTION_registerProcessObserver = 121;
        static final int TRANSACTION_registerReceiver = 18;
        static final int TRANSACTION_registerReceiverWithFeature = 19;
        static final int TRANSACTION_registerStrictModeCallback = 108;
        static final int TRANSACTION_registerTaskStackListener = 179;
        static final int TRANSACTION_registerUidFrozenStateChangedCallback = 251;
        static final int TRANSACTION_registerUidObserver = 2;
        static final int TRANSACTION_registerUidObserverForUids = 4;
        static final int TRANSACTION_registerUserSwitchObserver = 143;
        static final int TRANSACTION_removeApplicationStartInfoCompleteListener = 222;
        static final int TRANSACTION_removeContentProvider = 71;
        static final int TRANSACTION_removeContentProviderExternal = 130;
        static final int TRANSACTION_removeContentProviderExternalAsUser = 131;
        static final int TRANSACTION_removeTask = 120;
        static final int TRANSACTION_removeUidFromObserver = 6;
        static final int TRANSACTION_requestBugReport = 147;
        static final int TRANSACTION_requestBugReportWithDescription = 148;
        static final int TRANSACTION_requestFullBugReport = 153;
        static final int TRANSACTION_requestInteractiveBugReport = 152;
        static final int TRANSACTION_requestInteractiveBugReportWithDescription = 151;
        static final int TRANSACTION_requestRemoteBugReport = 154;
        static final int TRANSACTION_requestSystemServerHeapDump = 146;
        static final int TRANSACTION_requestTelephonyBugReport = 149;
        static final int TRANSACTION_requestWifiBugReport = 150;
        static final int TRANSACTION_resetAppErrors = 231;
        static final int TRANSACTION_resizeTask = 183;
        static final int TRANSACTION_restart = 166;
        static final int TRANSACTION_restartUserInBackground = 247;
        static final int TRANSACTION_resumeAppSwitches = 92;
        static final int TRANSACTION_revokeUriPermission = 57;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 209;
        static final int TRANSACTION_sendIdleJobTrigger = 203;
        static final int TRANSACTION_sendIntentSender = 204;
        static final int TRANSACTION_serviceDoneExecuting = 62;
        static final int TRANSACTION_setActivityController = 58;
        static final int TRANSACTION_setActivityLocusContext = 226;
        static final int TRANSACTION_setAgentApp = 42;
        static final int TRANSACTION_setAlwaysFinish = 43;
        static final int TRANSACTION_setDebugApp = 41;
        static final int TRANSACTION_setDeterministicUidIdle = 198;
        static final int TRANSACTION_setDumpHeapDebugLimit = 185;
        static final int TRANSACTION_setFocusedRootTask = 164;
        static final int TRANSACTION_setHasTopUi = 207;
        static final int TRANSACTION_setPackageScreenCompatMode = 115;
        static final int TRANSACTION_setPersistentVrThread = 210;
        static final int TRANSACTION_setProcessImportant = 74;
        static final int TRANSACTION_setProcessLimit = 51;
        static final int TRANSACTION_setProcessMemoryTrimLevel = 170;
        static final int TRANSACTION_setProcessStateSummary = 227;
        static final int TRANSACTION_setRenderThread = 206;
        static final int TRANSACTION_setRequestedOrientation = 72;
        static final int TRANSACTION_setServiceForeground = 75;
        static final int TRANSACTION_setStopUserOnSwitch = 119;
        static final int TRANSACTION_setTaskResizeable = 182;
        static final int TRANSACTION_setThemeOverlayReady = 178;
        static final int TRANSACTION_setUserIsMonkey = 160;
        static final int TRANSACTION_shouldServiceTimeOut = 249;
        static final int TRANSACTION_showBootMessage = 127;
        static final int TRANSACTION_showWaitingForDebugger = 59;
        static final int TRANSACTION_shutdown = 90;
        static final int TRANSACTION_signalPersistentProcesses = 60;
        static final int TRANSACTION_startActivity = 14;
        static final int TRANSACTION_startActivityAsUser = 139;
        static final int TRANSACTION_startActivityAsUserWithFeature = 140;
        static final int TRANSACTION_startActivityFromRecents = 174;
        static final int TRANSACTION_startActivityWithFeature = 15;
        static final int TRANSACTION_startBinderTracking = 191;
        static final int TRANSACTION_startConfirmDeviceCredentialIntent = 202;
        static final int TRANSACTION_startDelegateShellPermissionIdentity = 214;
        static final int TRANSACTION_startInstrumentation = 44;
        static final int TRANSACTION_startProfile = 235;
        static final int TRANSACTION_startProfileWithListener = 246;
        static final int TRANSACTION_startService = 34;
        static final int TRANSACTION_startSystemLockTaskMode = 175;
        static final int TRANSACTION_startUserInBackground = 172;
        static final int TRANSACTION_startUserInBackgroundVisibleOnDisplay = 245;
        static final int TRANSACTION_startUserInBackgroundWithListener = 213;
        static final int TRANSACTION_startUserInForegroundWithListener = 218;
        static final int TRANSACTION_stopAppForUser = 81;
        static final int TRANSACTION_stopAppSwitches = 91;
        static final int TRANSACTION_stopBinderTrackingAndDump = 192;
        static final int TRANSACTION_stopDelegateShellPermissionIdentity = 215;
        static final int TRANSACTION_stopProfile = 236;
        static final int TRANSACTION_stopService = 35;
        static final int TRANSACTION_stopServiceToken = 50;
        static final int TRANSACTION_stopUser = 141;
        static final int TRANSACTION_stopUserWithDelayedLocking = 142;
        static final int TRANSACTION_suppressResizeConfigChanges = 193;
        static final int TRANSACTION_switchUser = 116;
        static final int TRANSACTION_unbindBackupAgent = 95;
        static final int TRANSACTION_unbindFinished = 73;
        static final int TRANSACTION_unbindService = 39;
        static final int TRANSACTION_unbroadcastIntent = 23;
        static final int TRANSACTION_unhandledBack = 16;
        static final int TRANSACTION_unlockUser = 194;
        static final int TRANSACTION_unlockUser2 = 195;
        static final int TRANSACTION_unregisterIntentSenderCancelListener = 68;
        static final int TRANSACTION_unregisterProcessObserver = 122;
        static final int TRANSACTION_unregisterReceiver = 20;
        static final int TRANSACTION_unregisterTaskStackListener = 180;
        static final int TRANSACTION_unregisterUidFrozenStateChangedCallback = 252;
        static final int TRANSACTION_unregisterUidObserver = 3;
        static final int TRANSACTION_unregisterUserSwitchObserver = 144;
        static final int TRANSACTION_unstableProviderDied = 137;
        static final int TRANSACTION_updateConfiguration = 48;
        static final int TRANSACTION_updateLockTaskPackages = 187;
        static final int TRANSACTION_updateMccMncConfiguration = 49;
        static final int TRANSACTION_updatePersistentConfiguration = 124;
        static final int TRANSACTION_updatePersistentConfigurationWithAttribution = 125;
        static final int TRANSACTION_updateServiceGroup = 38;
        static final int TRANSACTION_waitForBroadcastBarrier = 240;
        static final int TRANSACTION_waitForBroadcastIdle = 239;
        static final int TRANSACTION_waitForNetworkStateUpdate = 211;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IActivityManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IActivityManager)) {
                return (android.app.IActivityManager) queryLocalInterface;
            }
            return new android.app.IActivityManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openContentUri";
                case 2:
                    return "registerUidObserver";
                case 3:
                    return "unregisterUidObserver";
                case 4:
                    return "registerUidObserverForUids";
                case 5:
                    return "addUidToObserver";
                case 6:
                    return "removeUidFromObserver";
                case 7:
                    return "isUidActive";
                case 8:
                    return "getUidProcessState";
                case 9:
                    return "checkPermission";
                case 10:
                    return "logFgsApiBegin";
                case 11:
                    return "logFgsApiEnd";
                case 12:
                    return "logFgsApiStateChanged";
                case 13:
                    return "handleApplicationCrash";
                case 14:
                    return "startActivity";
                case 15:
                    return "startActivityWithFeature";
                case 16:
                    return "unhandledBack";
                case 17:
                    return "finishActivity";
                case 18:
                    return "registerReceiver";
                case 19:
                    return "registerReceiverWithFeature";
                case 20:
                    return "unregisterReceiver";
                case 21:
                    return "broadcastIntent";
                case 22:
                    return "broadcastIntentWithFeature";
                case 23:
                    return "unbroadcastIntent";
                case 24:
                    return "finishReceiver";
                case 25:
                    return "attachApplication";
                case 26:
                    return "finishAttachApplication";
                case 27:
                    return "getTasks";
                case 28:
                    return "moveTaskToFront";
                case 29:
                    return "getTaskForActivity";
                case 30:
                    return "getContentProvider";
                case 31:
                    return "publishContentProviders";
                case 32:
                    return "refContentProvider";
                case 33:
                    return "getRunningServiceControlPanel";
                case 34:
                    return "startService";
                case 35:
                    return "stopService";
                case 36:
                    return "bindService";
                case 37:
                    return "bindServiceInstance";
                case 38:
                    return "updateServiceGroup";
                case 39:
                    return "unbindService";
                case 40:
                    return "publishService";
                case 41:
                    return "setDebugApp";
                case 42:
                    return "setAgentApp";
                case 43:
                    return "setAlwaysFinish";
                case 44:
                    return "startInstrumentation";
                case 45:
                    return "addInstrumentationResults";
                case 46:
                    return "finishInstrumentation";
                case 47:
                    return "getConfiguration";
                case 48:
                    return "updateConfiguration";
                case 49:
                    return "updateMccMncConfiguration";
                case 50:
                    return "stopServiceToken";
                case 51:
                    return "setProcessLimit";
                case 52:
                    return "getProcessLimit";
                case 53:
                    return "checkUriPermission";
                case 54:
                    return "checkContentUriPermissionFull";
                case 55:
                    return "checkUriPermissions";
                case 56:
                    return "grantUriPermission";
                case 57:
                    return "revokeUriPermission";
                case 58:
                    return "setActivityController";
                case 59:
                    return "showWaitingForDebugger";
                case 60:
                    return "signalPersistentProcesses";
                case 61:
                    return "getRecentTasks";
                case 62:
                    return "serviceDoneExecuting";
                case 63:
                    return "getIntentSender";
                case 64:
                    return "getIntentSenderWithFeature";
                case 65:
                    return "cancelIntentSender";
                case 66:
                    return "getInfoForIntentSender";
                case 67:
                    return "registerIntentSenderCancelListenerEx";
                case 68:
                    return "unregisterIntentSenderCancelListener";
                case 69:
                    return "enterSafeMode";
                case 70:
                    return "noteWakeupAlarm";
                case 71:
                    return "removeContentProvider";
                case 72:
                    return "setRequestedOrientation";
                case 73:
                    return "unbindFinished";
                case 74:
                    return "setProcessImportant";
                case 75:
                    return "setServiceForeground";
                case 76:
                    return "getForegroundServiceType";
                case 77:
                    return "moveActivityTaskToBack";
                case 78:
                    return "getMemoryInfo";
                case 79:
                    return "getProcessesInErrorState";
                case 80:
                    return "clearApplicationUserData";
                case 81:
                    return "stopAppForUser";
                case 82:
                    return "registerForegroundServiceObserver";
                case 83:
                    return "forceStopPackage";
                case 84:
                    return "forceStopPackageEvenWhenStopping";
                case 85:
                    return "killPids";
                case 86:
                    return "getServices";
                case 87:
                    return "getRunningAppProcesses";
                case 88:
                    return "peekService";
                case 89:
                    return "profileControl";
                case 90:
                    return "shutdown";
                case 91:
                    return "stopAppSwitches";
                case 92:
                    return "resumeAppSwitches";
                case 93:
                    return "bindBackupAgent";
                case 94:
                    return "backupAgentCreated";
                case 95:
                    return "unbindBackupAgent";
                case 96:
                    return "handleIncomingUser";
                case 97:
                    return "addPackageDependency";
                case 98:
                    return "killApplication";
                case 99:
                    return "closeSystemDialogs";
                case 100:
                    return "getProcessMemoryInfo";
                case 101:
                    return "killApplicationProcess";
                case 102:
                    return "handleApplicationWtf";
                case 103:
                    return "killBackgroundProcesses";
                case 104:
                    return "isUserAMonkey";
                case 105:
                    return "getRunningExternalApplications";
                case 106:
                    return "finishHeavyWeightApp";
                case 107:
                    return "handleApplicationStrictModeViolation";
                case 108:
                    return "registerStrictModeCallback";
                case 109:
                    return "isTopActivityImmersive";
                case 110:
                    return "crashApplicationWithType";
                case 111:
                    return "crashApplicationWithTypeWithExtras";
                case 112:
                    return "getMimeTypeFilterAsync";
                case 113:
                    return "dumpHeap";
                case 114:
                    return "isUserRunning";
                case 115:
                    return "setPackageScreenCompatMode";
                case 116:
                    return "switchUser";
                case 117:
                    return "getSwitchingFromUserMessage";
                case 118:
                    return "getSwitchingToUserMessage";
                case 119:
                    return "setStopUserOnSwitch";
                case 120:
                    return "removeTask";
                case 121:
                    return "registerProcessObserver";
                case 122:
                    return "unregisterProcessObserver";
                case 123:
                    return "isIntentSenderTargetedToPackage";
                case 124:
                    return "updatePersistentConfiguration";
                case 125:
                    return "updatePersistentConfigurationWithAttribution";
                case 126:
                    return "getProcessPss";
                case 127:
                    return "showBootMessage";
                case 128:
                    return "killAllBackgroundProcesses";
                case 129:
                    return "getContentProviderExternal";
                case 130:
                    return "removeContentProviderExternal";
                case 131:
                    return "removeContentProviderExternalAsUser";
                case 132:
                    return "getMyMemoryState";
                case 133:
                    return "killProcessesBelowForeground";
                case 134:
                    return "getCurrentUser";
                case 135:
                    return "getCurrentUserId";
                case 136:
                    return "getLaunchedFromUid";
                case 137:
                    return "unstableProviderDied";
                case 138:
                    return "isIntentSenderAnActivity";
                case 139:
                    return "startActivityAsUser";
                case 140:
                    return "startActivityAsUserWithFeature";
                case 141:
                    return "stopUser";
                case 142:
                    return "stopUserWithDelayedLocking";
                case 143:
                    return "registerUserSwitchObserver";
                case 144:
                    return "unregisterUserSwitchObserver";
                case 145:
                    return "getRunningUserIds";
                case 146:
                    return "requestSystemServerHeapDump";
                case 147:
                    return "requestBugReport";
                case 148:
                    return "requestBugReportWithDescription";
                case 149:
                    return "requestTelephonyBugReport";
                case 150:
                    return "requestWifiBugReport";
                case 151:
                    return "requestInteractiveBugReportWithDescription";
                case 152:
                    return "requestInteractiveBugReport";
                case 153:
                    return "requestFullBugReport";
                case 154:
                    return "requestRemoteBugReport";
                case 155:
                    return "launchBugReportHandlerApp";
                case 156:
                    return "getBugreportWhitelistedPackages";
                case 157:
                    return "getIntentForIntentSender";
                case 158:
                    return "getLaunchedFromPackage";
                case 159:
                    return "killUid";
                case 160:
                    return "setUserIsMonkey";
                case 161:
                    return "hang";
                case 162:
                    return "getAllRootTaskInfos";
                case 163:
                    return "moveTaskToRootTask";
                case 164:
                    return "setFocusedRootTask";
                case 165:
                    return "getFocusedRootTaskInfo";
                case 166:
                    return "restart";
                case 167:
                    return "performIdleMaintenance";
                case 168:
                    return "appNotRespondingViaProvider";
                case 169:
                    return "getTaskBounds";
                case 170:
                    return "setProcessMemoryTrimLevel";
                case 171:
                    return "getTagForIntentSender";
                case 172:
                    return "startUserInBackground";
                case 173:
                    return "isInLockTaskMode";
                case 174:
                    return "startActivityFromRecents";
                case 175:
                    return "startSystemLockTaskMode";
                case 176:
                    return "isTopOfTask";
                case 177:
                    return "bootAnimationComplete";
                case 178:
                    return "setThemeOverlayReady";
                case 179:
                    return "registerTaskStackListener";
                case 180:
                    return "unregisterTaskStackListener";
                case 181:
                    return "notifyCleartextNetwork";
                case 182:
                    return "setTaskResizeable";
                case 183:
                    return "resizeTask";
                case 184:
                    return "getLockTaskModeState";
                case 185:
                    return "setDumpHeapDebugLimit";
                case 186:
                    return "dumpHeapFinished";
                case 187:
                    return "updateLockTaskPackages";
                case 188:
                    return "noteAlarmStart";
                case 189:
                    return "noteAlarmFinish";
                case 190:
                    return "getPackageProcessState";
                case 191:
                    return "startBinderTracking";
                case 192:
                    return "stopBinderTrackingAndDump";
                case 193:
                    return "suppressResizeConfigChanges";
                case 194:
                    return "unlockUser";
                case 195:
                    return "unlockUser2";
                case 196:
                    return "killPackageDependents";
                case 197:
                    return "makePackageIdle";
                case 198:
                    return "setDeterministicUidIdle";
                case 199:
                    return "getMemoryTrimLevel";
                case 200:
                    return "isVrModePackageEnabled";
                case 201:
                    return "notifyLockedProfile";
                case 202:
                    return "startConfirmDeviceCredentialIntent";
                case 203:
                    return "sendIdleJobTrigger";
                case 204:
                    return "sendIntentSender";
                case 205:
                    return "isBackgroundRestricted";
                case 206:
                    return "setRenderThread";
                case 207:
                    return "setHasTopUi";
                case 208:
                    return "cancelTaskWindowTransition";
                case 209:
                    return "scheduleApplicationInfoChanged";
                case 210:
                    return "setPersistentVrThread";
                case 211:
                    return "waitForNetworkStateUpdate";
                case 212:
                    return "backgroundAllowlistUid";
                case 213:
                    return "startUserInBackgroundWithListener";
                case 214:
                    return "startDelegateShellPermissionIdentity";
                case 215:
                    return "stopDelegateShellPermissionIdentity";
                case 216:
                    return "getDelegatedShellPermissions";
                case 217:
                    return "getLifeMonitor";
                case 218:
                    return "startUserInForegroundWithListener";
                case 219:
                    return "appNotResponding";
                case 220:
                    return "getHistoricalProcessStartReasons";
                case 221:
                    return "addApplicationStartInfoCompleteListener";
                case 222:
                    return "removeApplicationStartInfoCompleteListener";
                case 223:
                    return "addStartInfoTimestamp";
                case 224:
                    return "getHistoricalProcessExitReasons";
                case 225:
                    return "killProcessesWhenImperceptible";
                case 226:
                    return "setActivityLocusContext";
                case 227:
                    return "setProcessStateSummary";
                case 228:
                    return "isAppFreezerSupported";
                case 229:
                    return "isAppFreezerEnabled";
                case 230:
                    return "killUidForPermissionChange";
                case 231:
                    return "resetAppErrors";
                case 232:
                    return "enableAppFreezer";
                case 233:
                    return "enableFgsNotificationRateLimit";
                case 234:
                    return "holdLock";
                case 235:
                    return "startProfile";
                case 236:
                    return "stopProfile";
                case 237:
                    return "queryIntentComponentsForIntentSender";
                case 238:
                    return "getUidProcessCapabilities";
                case 239:
                    return "waitForBroadcastIdle";
                case 240:
                    return "waitForBroadcastBarrier";
                case 241:
                    return "forceDelayBroadcastDelivery";
                case 242:
                    return "isModernBroadcastQueueEnabled";
                case 243:
                    return "isProcessFrozen";
                case 244:
                    return "getBackgroundRestrictionExemptionReason";
                case 245:
                    return "startUserInBackgroundVisibleOnDisplay";
                case 246:
                    return "startProfileWithListener";
                case 247:
                    return "restartUserInBackground";
                case 248:
                    return "getDisplayIdsForStartingVisibleBackgroundUsers";
                case 249:
                    return "shouldServiceTimeOut";
                case 250:
                    return "hasServiceTimeLimitExceeded";
                case 251:
                    return "registerUidFrozenStateChangedCallback";
                case 252:
                    return "unregisterUidFrozenStateChangedCallback";
                case 253:
                    return "getUidFrozenState";
                case 254:
                    return "checkPermissionForDevice";
                case 255:
                    return "frozenBinderTransactionDetected";
                case 256:
                    return "getBindingUidProcessState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
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
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openContentUri = openContentUri(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openContentUri, 1);
                    return true;
                case 2:
                    android.app.IUidObserver asInterface = android.app.IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUidObserver(asInterface, readInt, readInt2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.app.IUidObserver asInterface2 = android.app.IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.IUidObserver asInterface3 = android.app.IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder registerUidObserverForUids = registerUidObserverForUids(asInterface3, readInt3, readInt4, readString3, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(registerUidObserverForUids);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidToObserver(readStrongBinder, readString4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidFromObserver(readStrongBinder2, readString5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUidActive = isUidActive(readInt7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidActive);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessState = getUidProcessState(readInt8, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidProcessState);
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString8, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiBegin(readInt11, readInt12, readInt13);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiEnd(readInt14, readInt15, readInt16);
                    return true;
                case 12:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiStateChanged(readInt17, readInt18, readInt19, readInt20);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = (android.app.ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(android.app.ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationCrash(readStrongBinder3, parcelableCrashInfo);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.app.IApplicationThread asInterface4 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString9 = parcel.readString();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    java.lang.String readString11 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivity = startActivity(asInterface4, readString9, intent, readString10, readStrongBinder4, readString11, readInt21, readInt22, profilerInfo, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivity);
                    return true;
                case 15:
                    android.app.IApplicationThread asInterface5 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString14 = parcel.readString();
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    java.lang.String readString15 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo2 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityWithFeature = startActivityWithFeature(asInterface5, readString12, readString13, intent2, readString14, readStrongBinder5, readString15, readInt23, readInt24, profilerInfo2, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityWithFeature);
                    return true;
                case 16:
                    unhandledBack();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt25 = parcel.readInt();
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean finishActivity = finishActivity(readStrongBinder6, readInt25, intent3, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivity);
                    return true;
                case 18:
                    android.app.IApplicationThread asInterface6 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString16 = parcel.readString();
                    android.content.IIntentReceiver asInterface7 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    java.lang.String readString17 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.Intent registerReceiver = registerReceiver(asInterface6, readString16, asInterface7, intentFilter, readString17, readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerReceiver, 1);
                    return true;
                case 19:
                    android.app.IApplicationThread asInterface8 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    android.content.IIntentReceiver asInterface9 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.content.IntentFilter intentFilter2 = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
                    java.lang.String readString21 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.Intent registerReceiverWithFeature = registerReceiverWithFeature(asInterface8, readString18, readString19, readString20, asInterface9, intentFilter2, readString21, readInt29, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerReceiverWithFeature, 1);
                    return true;
                case 20:
                    android.content.IIntentReceiver asInterface10 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterReceiver(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.app.IApplicationThread asInterface11 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.Intent intent4 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString22 = parcel.readString();
                    android.content.IIntentReceiver asInterface12 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt31 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt32 = parcel.readInt();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int broadcastIntent = broadcastIntent(asInterface11, intent4, readString22, asInterface12, readInt31, readString23, bundle3, createStringArray, readInt32, bundle4, readBoolean, readBoolean2, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeInt(broadcastIntent);
                    return true;
                case 22:
                    android.app.IApplicationThread asInterface13 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString24 = parcel.readString();
                    android.content.Intent intent5 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString25 = parcel.readString();
                    android.content.IIntentReceiver asInterface14 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt34 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    int readInt35 = parcel.readInt();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int broadcastIntentWithFeature = broadcastIntentWithFeature(asInterface13, readString24, intent5, readString25, asInterface14, readInt34, readString26, bundle5, createStringArray2, createStringArray3, createStringArray4, readInt35, bundle6, readBoolean3, readBoolean4, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(broadcastIntentWithFeature);
                    return true;
                case 23:
                    android.app.IApplicationThread asInterface15 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.Intent intent6 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unbroadcastIntent(asInterface15, intent6, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt38 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishReceiver(readStrongBinder7, readInt38, readString27, bundle7, readBoolean5, readInt39);
                    return true;
                case 25:
                    android.app.IApplicationThread asInterface16 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    attachApplication(asInterface16, readLong);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    finishAttachApplication(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityManager.RunningTaskInfo> tasks = getTasks(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 28:
                    android.app.IApplicationThread asInterface17 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString28 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    moveTaskToFront(asInterface17, readString28, readInt41, readInt42, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(readStrongBinder8, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 30:
                    android.app.IApplicationThread asInterface18 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.ContentProviderHolder contentProvider = getContentProvider(asInterface18, readString29, readString30, readInt43, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contentProvider, 1);
                    return true;
                case 31:
                    android.app.IApplicationThread asInterface19 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.ContentProviderHolder.CREATOR);
                    parcel.enforceNoDataAvail();
                    publishContentProviders(asInterface19, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean refContentProvider = refContentProvider(readStrongBinder9, readInt44, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(refContentProvider);
                    return true;
                case 33:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent runningServiceControlPanel = getRunningServiceControlPanel(componentName);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(runningServiceControlPanel, 1);
                    return true;
                case 34:
                    android.app.IApplicationThread asInterface20 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.Intent intent7 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString31 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName startService = startService(asInterface20, intent7, readString31, readBoolean8, readString32, readString33, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startService, 1);
                    return true;
                case 35:
                    android.app.IApplicationThread asInterface21 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.Intent intent8 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString34 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopService = stopService(asInterface21, intent8, readString34, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopService);
                    return true;
                case 36:
                    android.app.IApplicationThread asInterface22 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    android.content.Intent intent9 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString35 = parcel.readString();
                    android.app.IServiceConnection asInterface23 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    long readLong3 = parcel.readLong();
                    java.lang.String readString36 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int bindService = bindService(asInterface22, readStrongBinder10, intent9, readString35, asInterface23, readLong3, readString36, readInt48);
                    parcel2.writeNoException();
                    parcel2.writeInt(bindService);
                    return true;
                case 37:
                    android.app.IApplicationThread asInterface24 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.content.Intent intent10 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString37 = parcel.readString();
                    android.app.IServiceConnection asInterface25 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    long readLong4 = parcel.readLong();
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int bindServiceInstance = bindServiceInstance(asInterface24, readStrongBinder11, intent10, readString37, asInterface25, readLong4, readString38, readString39, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeInt(bindServiceInstance);
                    return true;
                case 38:
                    android.app.IServiceConnection asInterface26 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateServiceGroup(asInterface26, readInt50, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.app.IServiceConnection asInterface27 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unbindService = unbindService(asInterface27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unbindService);
                    return true;
                case 40:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.content.Intent intent11 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    publishService(readStrongBinder12, intent11, readStrongBinder13);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    java.lang.String readString40 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDebugApp(readString40, readBoolean9, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    java.lang.String readString41 = parcel.readString();
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAgentApp(readString41, readString42);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAlwaysFinish(readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString43 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    android.os.Bundle bundle9 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.IInstrumentationWatcher asInterface28 = android.app.IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    android.app.IUiAutomationConnection asInterface29 = android.app.IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt53 = parcel.readInt();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startInstrumentation = startInstrumentation(componentName2, readString43, readInt52, bundle9, asInterface28, asInterface29, readInt53, readString44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startInstrumentation);
                    return true;
                case 45:
                    android.app.IApplicationThread asInterface30 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle10 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addInstrumentationResults(asInterface30, bundle10);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.app.IApplicationThread asInterface31 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    int readInt54 = parcel.readInt();
                    android.os.Bundle bundle11 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishInstrumentation(asInterface31, readInt54, bundle11);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.content.res.Configuration configuration = getConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configuration, 1);
                    return true;
                case 48:
                    android.content.res.Configuration configuration2 = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 49:
                    java.lang.String readString45 = parcel.readString();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean updateMccMncConfiguration = updateMccMncConfiguration(readString45, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateMccMncConfiguration);
                    return true;
                case 50:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean stopServiceToken = stopServiceToken(componentName3, readStrongBinder14, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopServiceToken);
                    return true;
                case 51:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProcessLimit(readInt56);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int processLimit = getProcessLimit();
                    parcel2.writeNoException();
                    parcel2.writeInt(processLimit);
                    return true;
                case 53:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int checkUriPermission = checkUriPermission(uri, readInt57, readInt58, readInt59, readInt60, readStrongBinder15);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUriPermission);
                    return true;
                case 54:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkContentUriPermissionFull = checkContentUriPermissionFull(uri2, readInt61, readInt62, readInt63, readInt64);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkContentUriPermissionFull);
                    return true;
                case 55:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int[] checkUriPermissions = checkUriPermissions(createTypedArrayList2, readInt65, readInt66, readInt67, readInt68, readStrongBinder16);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(checkUriPermissions);
                    return true;
                case 56:
                    android.app.IApplicationThread asInterface32 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString47 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantUriPermission(asInterface32, readString47, uri3, readInt69, readInt70);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    android.app.IApplicationThread asInterface33 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString48 = parcel.readString();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt71 = parcel.readInt();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeUriPermission(asInterface33, readString48, uri4, readInt71, readInt72);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    android.app.IActivityController asInterface34 = android.app.IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(asInterface34, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    android.app.IApplicationThread asInterface35 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showWaitingForDebugger(asInterface35, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    signalPersistentProcesses(readInt73);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice recentTasks = getRecentTasks(readInt74, readInt75, readInt76);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentTasks, 1);
                    return true;
                case 62:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    android.content.Intent intent12 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceDoneExecuting(readStrongBinder17, readInt77, readInt78, readInt79, intent12);
                    return true;
                case 63:
                    int readInt80 = parcel.readInt();
                    java.lang.String readString49 = parcel.readString();
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    java.lang.String readString50 = parcel.readString();
                    int readInt81 = parcel.readInt();
                    android.content.Intent[] intentArr = (android.content.Intent[]) parcel.createTypedArray(android.content.Intent.CREATOR);
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    int readInt82 = parcel.readInt();
                    android.os.Bundle bundle12 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.IIntentSender intentSender = getIntentSender(readInt80, readString49, readStrongBinder18, readString50, readInt81, intentArr, createStringArray5, readInt82, bundle12, readInt83);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(intentSender);
                    return true;
                case 64:
                    int readInt84 = parcel.readInt();
                    java.lang.String readString51 = parcel.readString();
                    java.lang.String readString52 = parcel.readString();
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    java.lang.String readString53 = parcel.readString();
                    int readInt85 = parcel.readInt();
                    android.content.Intent[] intentArr2 = (android.content.Intent[]) parcel.createTypedArray(android.content.Intent.CREATOR);
                    java.lang.String[] createStringArray6 = parcel.createStringArray();
                    int readInt86 = parcel.readInt();
                    android.os.Bundle bundle13 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.IIntentSender intentSenderWithFeature = getIntentSenderWithFeature(readInt84, readString51, readString52, readStrongBinder19, readString53, readInt85, intentArr2, createStringArray6, readInt86, bundle13, readInt87);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(intentSenderWithFeature);
                    return true;
                case 65:
                    android.content.IIntentSender asInterface36 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelIntentSender(asInterface36);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    android.content.IIntentSender asInterface37 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.app.ActivityManager.PendingIntentInfo infoForIntentSender = getInfoForIntentSender(asInterface37);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(infoForIntentSender, 1);
                    return true;
                case 67:
                    android.content.IIntentSender asInterface38 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.os.IResultReceiver asInterface39 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerIntentSenderCancelListenerEx = registerIntentSenderCancelListenerEx(asInterface38, asInterface39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerIntentSenderCancelListenerEx);
                    return true;
                case 68:
                    android.content.IIntentSender asInterface40 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.os.IResultReceiver asInterface41 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentSenderCancelListener(asInterface40, asInterface41);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    enterSafeMode();
                    parcel2.writeNoException();
                    return true;
                case 70:
                    android.content.IIntentSender asInterface42 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    android.os.WorkSource workSource = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt88 = parcel.readInt();
                    java.lang.String readString54 = parcel.readString();
                    java.lang.String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteWakeupAlarm(asInterface42, workSource, readInt88, readString54, readString55);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeContentProvider(readStrongBinder20, readBoolean14);
                    return true;
                case 72:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(readStrongBinder21, readInt89);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    android.os.IBinder readStrongBinder22 = parcel.readStrongBinder();
                    android.content.Intent intent13 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    unbindFinished(readStrongBinder22, intent13, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    android.os.IBinder readStrongBinder23 = parcel.readStrongBinder();
                    int readInt90 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    java.lang.String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProcessImportant(readStrongBinder23, readInt90, readBoolean16, readString56);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder24 = parcel.readStrongBinder();
                    int readInt91 = parcel.readInt();
                    android.app.Notification notification = (android.app.Notification) parcel.readTypedObject(android.app.Notification.CREATOR);
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setServiceForeground(componentName4, readStrongBinder24, readInt91, notification, readInt92, readInt93);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int foregroundServiceType = getForegroundServiceType(componentName5, readStrongBinder25);
                    parcel2.writeNoException();
                    parcel2.writeInt(foregroundServiceType);
                    return true;
                case 77:
                    android.os.IBinder readStrongBinder26 = parcel.readStrongBinder();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean moveActivityTaskToBack = moveActivityTaskToBack(readStrongBinder26, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveActivityTaskToBack);
                    return true;
                case 78:
                    android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
                    parcel.enforceNoDataAvail();
                    getMemoryInfo(memoryInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(memoryInfo, 1);
                    return true;
                case 79:
                    java.util.List<android.app.ActivityManager.ProcessErrorStateInfo> processesInErrorState = getProcessesInErrorState();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(processesInErrorState, 1);
                    return true;
                case 80:
                    java.lang.String readString57 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    android.content.pm.IPackageDataObserver asInterface43 = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearApplicationUserData = clearApplicationUserData(readString57, readBoolean18, asInterface43, readInt94);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearApplicationUserData);
                    return true;
                case 81:
                    java.lang.String readString58 = parcel.readString();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAppForUser(readString58, readInt95);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    android.app.IForegroundServiceObserver asInterface44 = android.app.IForegroundServiceObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerForegroundServiceObserver = registerForegroundServiceObserver(asInterface44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerForegroundServiceObserver);
                    return true;
                case 83:
                    java.lang.String readString59 = parcel.readString();
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackage(readString59, readInt96);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    java.lang.String readString60 = parcel.readString();
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackageEvenWhenStopping(readString60, readInt97);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    int[] createIntArray2 = parcel.createIntArray();
                    java.lang.String readString61 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean killPids = killPids(createIntArray2, readString61, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(killPids);
                    return true;
                case 86:
                    int readInt98 = parcel.readInt();
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityManager.RunningServiceInfo> services = getServices(readInt98, readInt99);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(services, 1);
                    return true;
                case 87:
                    java.util.List<android.app.ActivityManager.RunningAppProcessInfo> runningAppProcesses = getRunningAppProcesses();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(runningAppProcesses, 1);
                    return true;
                case 88:
                    android.content.Intent intent14 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString62 = parcel.readString();
                    java.lang.String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder peekService = peekService(intent14, readString62, readString63);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(peekService);
                    return true;
                case 89:
                    java.lang.String readString64 = parcel.readString();
                    int readInt100 = parcel.readInt();
                    boolean readBoolean20 = parcel.readBoolean();
                    android.app.ProfilerInfo profilerInfo3 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    int readInt101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileControl = profileControl(readString64, readInt100, readBoolean20, profilerInfo3, readInt101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileControl);
                    return true;
                case 90:
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shutdown = shutdown(readInt102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shutdown);
                    return true;
                case 91:
                    stopAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 92:
                    resumeAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 93:
                    java.lang.String readString65 = parcel.readString();
                    int readInt103 = parcel.readInt();
                    int readInt104 = parcel.readInt();
                    int readInt105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindBackupAgent = bindBackupAgent(readString65, readInt103, readInt104, readInt105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindBackupAgent);
                    return true;
                case 94:
                    java.lang.String readString66 = parcel.readString();
                    android.os.IBinder readStrongBinder27 = parcel.readStrongBinder();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupAgentCreated(readString66, readStrongBinder27, readInt106);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindBackupAgent(applicationInfo);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    int readInt107 = parcel.readInt();
                    int readInt108 = parcel.readInt();
                    int readInt109 = parcel.readInt();
                    boolean readBoolean21 = parcel.readBoolean();
                    boolean readBoolean22 = parcel.readBoolean();
                    java.lang.String readString67 = parcel.readString();
                    java.lang.String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int handleIncomingUser = handleIncomingUser(readInt107, readInt108, readInt109, readBoolean21, readBoolean22, readString67, readString68);
                    parcel2.writeNoException();
                    parcel2.writeInt(handleIncomingUser);
                    return true;
                case 97:
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addPackageDependency(readString69);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    java.lang.String readString70 = parcel.readString();
                    int readInt110 = parcel.readInt();
                    int readInt111 = parcel.readInt();
                    java.lang.String readString71 = parcel.readString();
                    int readInt112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killApplication(readString70, readInt110, readInt111, readString71, readInt112);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    java.lang.String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readString72);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    android.os.Debug.MemoryInfo[] processMemoryInfo = getProcessMemoryInfo(createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(processMemoryInfo, 1);
                    return true;
                case 101:
                    java.lang.String readString73 = parcel.readString();
                    int readInt113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killApplicationProcess(readString73, readInt113);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    android.os.IBinder readStrongBinder28 = parcel.readStrongBinder();
                    java.lang.String readString74 = parcel.readString();
                    boolean readBoolean23 = parcel.readBoolean();
                    android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo2 = (android.app.ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(android.app.ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean handleApplicationWtf = handleApplicationWtf(readStrongBinder28, readString74, readBoolean23, parcelableCrashInfo2, readInt114);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handleApplicationWtf);
                    return true;
                case 103:
                    java.lang.String readString75 = parcel.readString();
                    int readInt115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killBackgroundProcesses(readString75, readInt115);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    boolean isUserAMonkey = isUserAMonkey();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserAMonkey);
                    return true;
                case 105:
                    java.util.List<android.content.pm.ApplicationInfo> runningExternalApplications = getRunningExternalApplications();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(runningExternalApplications, 1);
                    return true;
                case 106:
                    finishHeavyWeightApp();
                    parcel2.writeNoException();
                    return true;
                case 107:
                    android.os.IBinder readStrongBinder29 = parcel.readStrongBinder();
                    int readInt116 = parcel.readInt();
                    android.os.StrictMode.ViolationInfo violationInfo = (android.os.StrictMode.ViolationInfo) parcel.readTypedObject(android.os.StrictMode.ViolationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationStrictModeViolation(readStrongBinder29, readInt116, violationInfo);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    android.os.IBinder readStrongBinder30 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerStrictModeCallback(readStrongBinder30);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    boolean isTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopActivityImmersive);
                    return true;
                case 110:
                    int readInt117 = parcel.readInt();
                    int readInt118 = parcel.readInt();
                    java.lang.String readString76 = parcel.readString();
                    int readInt119 = parcel.readInt();
                    java.lang.String readString77 = parcel.readString();
                    boolean readBoolean24 = parcel.readBoolean();
                    int readInt120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    crashApplicationWithType(readInt117, readInt118, readString76, readInt119, readString77, readBoolean24, readInt120);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    int readInt121 = parcel.readInt();
                    int readInt122 = parcel.readInt();
                    java.lang.String readString78 = parcel.readString();
                    int readInt123 = parcel.readInt();
                    java.lang.String readString79 = parcel.readString();
                    boolean readBoolean25 = parcel.readBoolean();
                    int readInt124 = parcel.readInt();
                    android.os.Bundle bundle14 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    crashApplicationWithTypeWithExtras(readInt121, readInt122, readString78, readInt123, readString79, readBoolean25, readInt124, bundle14);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt125 = parcel.readInt();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getMimeTypeFilterAsync(uri5, readInt125, remoteCallback);
                    return true;
                case 113:
                    java.lang.String readString80 = parcel.readString();
                    int readInt126 = parcel.readInt();
                    boolean readBoolean26 = parcel.readBoolean();
                    boolean readBoolean27 = parcel.readBoolean();
                    boolean readBoolean28 = parcel.readBoolean();
                    java.lang.String readString81 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dumpHeap = dumpHeap(readString80, readInt126, readBoolean26, readBoolean27, readBoolean28, readString81, parcelFileDescriptor, remoteCallback2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpHeap);
                    return true;
                case 114:
                    int readInt127 = parcel.readInt();
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserRunning = isUserRunning(readInt127, readInt128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRunning);
                    return true;
                case 115:
                    java.lang.String readString82 = parcel.readString();
                    int readInt129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(readString82, readInt129);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean switchUser = switchUser(readInt130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchUser);
                    return true;
                case 117:
                    java.lang.String switchingFromUserMessage = getSwitchingFromUserMessage();
                    parcel2.writeNoException();
                    parcel2.writeString(switchingFromUserMessage);
                    return true;
                case 118:
                    java.lang.String switchingToUserMessage = getSwitchingToUserMessage();
                    parcel2.writeNoException();
                    parcel2.writeString(switchingToUserMessage);
                    return true;
                case 119:
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStopUserOnSwitch(readInt131);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTask = removeTask(readInt132);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTask);
                    return true;
                case 121:
                    android.app.IProcessObserver asInterface45 = android.app.IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerProcessObserver(asInterface45);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    android.app.IProcessObserver asInterface46 = android.app.IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterProcessObserver(asInterface46);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    android.content.IIntentSender asInterface47 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isIntentSenderTargetedToPackage = isIntentSenderTargetedToPackage(asInterface47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntentSenderTargetedToPackage);
                    return true;
                case 124:
                    android.content.res.Configuration configuration3 = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePersistentConfiguration(configuration3);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    android.content.res.Configuration configuration4 = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    java.lang.String readString83 = parcel.readString();
                    java.lang.String readString84 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updatePersistentConfigurationWithAttribution(configuration4, readString83, readString84);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] processPss = getProcessPss(createIntArray4);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(processPss);
                    return true;
                case 127:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showBootMessage(charSequence, readBoolean29);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    killAllBackgroundProcesses();
                    parcel2.writeNoException();
                    return true;
                case 129:
                    java.lang.String readString85 = parcel.readString();
                    int readInt133 = parcel.readInt();
                    android.os.IBinder readStrongBinder31 = parcel.readStrongBinder();
                    java.lang.String readString86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.ContentProviderHolder contentProviderExternal = getContentProviderExternal(readString85, readInt133, readStrongBinder31, readString86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contentProviderExternal, 1);
                    return true;
                case 130:
                    java.lang.String readString87 = parcel.readString();
                    android.os.IBinder readStrongBinder32 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternal(readString87, readStrongBinder32);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    java.lang.String readString88 = parcel.readString();
                    android.os.IBinder readStrongBinder33 = parcel.readStrongBinder();
                    int readInt134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternalAsUser(readString88, readStrongBinder33, readInt134);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new android.app.ActivityManager.RunningAppProcessInfo();
                    parcel.enforceNoDataAvail();
                    getMyMemoryState(runningAppProcessInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(runningAppProcessInfo, 1);
                    return true;
                case 133:
                    java.lang.String readString89 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean killProcessesBelowForeground = killProcessesBelowForeground(readString89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(killProcessesBelowForeground);
                    return true;
                case 134:
                    android.content.pm.UserInfo currentUser = getCurrentUser();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentUser, 1);
                    return true;
                case 135:
                    int currentUserId = getCurrentUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentUserId);
                    return true;
                case 136:
                    android.os.IBinder readStrongBinder34 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(readStrongBinder34);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 137:
                    android.os.IBinder readStrongBinder35 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unstableProviderDied(readStrongBinder35);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    android.content.IIntentSender asInterface48 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isIntentSenderAnActivity = isIntentSenderAnActivity(asInterface48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntentSenderAnActivity);
                    return true;
                case 139:
                    android.app.IApplicationThread asInterface49 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString90 = parcel.readString();
                    android.content.Intent intent15 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString91 = parcel.readString();
                    android.os.IBinder readStrongBinder36 = parcel.readStrongBinder();
                    java.lang.String readString92 = parcel.readString();
                    int readInt135 = parcel.readInt();
                    int readInt136 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo4 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle15 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt137 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsUser = startActivityAsUser(asInterface49, readString90, intent15, readString91, readStrongBinder36, readString92, readInt135, readInt136, profilerInfo4, bundle15, readInt137);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsUser);
                    return true;
                case 140:
                    android.app.IApplicationThread asInterface50 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString93 = parcel.readString();
                    java.lang.String readString94 = parcel.readString();
                    android.content.Intent intent16 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString95 = parcel.readString();
                    android.os.IBinder readStrongBinder37 = parcel.readStrongBinder();
                    java.lang.String readString96 = parcel.readString();
                    int readInt138 = parcel.readInt();
                    int readInt139 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo5 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle16 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsUserWithFeature = startActivityAsUserWithFeature(asInterface50, readString93, readString94, intent16, readString95, readStrongBinder37, readString96, readInt138, readInt139, profilerInfo5, bundle16, readInt140);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsUserWithFeature);
                    return true;
                case 141:
                    int readInt141 = parcel.readInt();
                    boolean readBoolean30 = parcel.readBoolean();
                    android.app.IStopUserCallback asInterface51 = android.app.IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUser = stopUser(readInt141, readBoolean30, asInterface51);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUser);
                    return true;
                case 142:
                    int readInt142 = parcel.readInt();
                    boolean readBoolean31 = parcel.readBoolean();
                    android.app.IStopUserCallback asInterface52 = android.app.IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUserWithDelayedLocking = stopUserWithDelayedLocking(readInt142, readBoolean31, asInterface52);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUserWithDelayedLocking);
                    return true;
                case 143:
                    android.app.IUserSwitchObserver asInterface53 = android.app.IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString97 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUserSwitchObserver(asInterface53, readString97);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    android.app.IUserSwitchObserver asInterface54 = android.app.IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserSwitchObserver(asInterface54);
                    parcel2.writeNoException();
                    return true;
                case 145:
                    int[] runningUserIds = getRunningUserIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(runningUserIds);
                    return true;
                case 146:
                    requestSystemServerHeapDump();
                    parcel2.writeNoException();
                    return true;
                case 147:
                    int readInt143 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReport(readInt143);
                    parcel2.writeNoException();
                    return true;
                case 148:
                    java.lang.String readString98 = parcel.readString();
                    java.lang.String readString99 = parcel.readString();
                    int readInt144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReportWithDescription(readString98, readString99, readInt144);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    java.lang.String readString100 = parcel.readString();
                    java.lang.String readString101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestTelephonyBugReport(readString100, readString101);
                    parcel2.writeNoException();
                    return true;
                case 150:
                    java.lang.String readString102 = parcel.readString();
                    java.lang.String readString103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestWifiBugReport(readString102, readString103);
                    parcel2.writeNoException();
                    return true;
                case 151:
                    java.lang.String readString104 = parcel.readString();
                    java.lang.String readString105 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestInteractiveBugReportWithDescription(readString104, readString105);
                    parcel2.writeNoException();
                    return true;
                case 152:
                    requestInteractiveBugReport();
                    parcel2.writeNoException();
                    return true;
                case 153:
                    requestFullBugReport();
                    parcel2.writeNoException();
                    return true;
                case 154:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    requestRemoteBugReport(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    boolean launchBugReportHandlerApp = launchBugReportHandlerApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(launchBugReportHandlerApp);
                    return true;
                case 156:
                    java.util.List<java.lang.String> bugreportWhitelistedPackages = getBugreportWhitelistedPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(bugreportWhitelistedPackages);
                    return true;
                case 157:
                    android.content.IIntentSender asInterface55 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.content.Intent intentForIntentSender = getIntentForIntentSender(asInterface55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentForIntentSender, 1);
                    return true;
                case 158:
                    android.os.IBinder readStrongBinder38 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    java.lang.String launchedFromPackage = getLaunchedFromPackage(readStrongBinder38);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 159:
                    int readInt145 = parcel.readInt();
                    int readInt146 = parcel.readInt();
                    java.lang.String readString106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUid(readInt145, readInt146, readString106);
                    parcel2.writeNoException();
                    return true;
                case 160:
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserIsMonkey(readBoolean32);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    android.os.IBinder readStrongBinder39 = parcel.readStrongBinder();
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hang(readStrongBinder39, readBoolean33);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 163:
                    int readInt147 = parcel.readInt();
                    int readInt148 = parcel.readInt();
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(readInt147, readInt148, readBoolean34);
                    parcel2.writeNoException();
                    return true;
                case 164:
                    int readInt149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(readInt149);
                    parcel2.writeNoException();
                    return true;
                case 165:
                    android.app.ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = getFocusedRootTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(focusedRootTaskInfo, 1);
                    return true;
                case 166:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 167:
                    performIdleMaintenance();
                    parcel2.writeNoException();
                    return true;
                case 168:
                    android.os.IBinder readStrongBinder40 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    appNotRespondingViaProvider(readStrongBinder40);
                    parcel2.writeNoException();
                    return true;
                case 169:
                    int readInt150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Rect taskBounds = getTaskBounds(readInt150);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 170:
                    java.lang.String readString107 = parcel.readString();
                    int readInt151 = parcel.readInt();
                    int readInt152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processMemoryTrimLevel = setProcessMemoryTrimLevel(readString107, readInt151, readInt152);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processMemoryTrimLevel);
                    return true;
                case 171:
                    android.content.IIntentSender asInterface56 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString108 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String tagForIntentSender = getTagForIntentSender(asInterface56, readString108);
                    parcel2.writeNoException();
                    parcel2.writeString(tagForIntentSender);
                    return true;
                case 172:
                    int readInt153 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackground = startUserInBackground(readInt153);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackground);
                    return true;
                case 173:
                    boolean isInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInLockTaskMode);
                    return true;
                case 174:
                    int readInt154 = parcel.readInt();
                    android.os.Bundle bundle17 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityFromRecents = startActivityFromRecents(readInt154, bundle17);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromRecents);
                    return true;
                case 175:
                    int readInt155 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(readInt155);
                    parcel2.writeNoException();
                    return true;
                case 176:
                    android.os.IBinder readStrongBinder41 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isTopOfTask = isTopOfTask(readStrongBinder41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopOfTask);
                    return true;
                case 177:
                    bootAnimationComplete();
                    parcel2.writeNoException();
                    return true;
                case 178:
                    int readInt156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setThemeOverlayReady(readInt156);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    android.app.ITaskStackListener asInterface57 = android.app.ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(asInterface57);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    android.app.ITaskStackListener asInterface58 = android.app.ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(asInterface58);
                    parcel2.writeNoException();
                    return true;
                case 181:
                    int readInt157 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCleartextNetwork(readInt157, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    int readInt158 = parcel.readInt();
                    int readInt159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(readInt158, readInt159);
                    parcel2.writeNoException();
                    return true;
                case 183:
                    int readInt160 = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt161 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(readInt160, rect, readInt161);
                    parcel2.writeNoException();
                    return true;
                case 184:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 185:
                    java.lang.String readString109 = parcel.readString();
                    int readInt162 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    java.lang.String readString110 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDumpHeapDebugLimit(readString109, readInt162, readLong6, readString110);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    java.lang.String readString111 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dumpHeapFinished(readString111);
                    parcel2.writeNoException();
                    return true;
                case 187:
                    int readInt163 = parcel.readInt();
                    java.lang.String[] createStringArray7 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(readInt163, createStringArray7);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    android.content.IIntentSender asInterface59 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    android.os.WorkSource workSource2 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt164 = parcel.readInt();
                    java.lang.String readString112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteAlarmStart(asInterface59, workSource2, readInt164, readString112);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    android.content.IIntentSender asInterface60 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    android.os.WorkSource workSource3 = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
                    int readInt165 = parcel.readInt();
                    java.lang.String readString113 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteAlarmFinish(asInterface60, workSource3, readInt165, readString113);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    java.lang.String readString114 = parcel.readString();
                    java.lang.String readString115 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageProcessState = getPackageProcessState(readString114, readString115);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageProcessState);
                    return true;
                case 191:
                    boolean startBinderTracking = startBinderTracking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startBinderTracking);
                    return true;
                case 192:
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean stopBinderTrackingAndDump = stopBinderTrackingAndDump(parcelFileDescriptor2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopBinderTrackingAndDump);
                    return true;
                case 193:
                    boolean readBoolean35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(readBoolean35);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    int readInt166 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    android.os.IProgressListener asInterface61 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unlockUser = unlockUser(readInt166, createByteArray2, createByteArray3, asInterface61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockUser);
                    return true;
                case 195:
                    int readInt167 = parcel.readInt();
                    android.os.IProgressListener asInterface62 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unlockUser2 = unlockUser2(readInt167, asInterface62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockUser2);
                    return true;
                case 196:
                    java.lang.String readString116 = parcel.readString();
                    int readInt168 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killPackageDependents(readString116, readInt168);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    java.lang.String readString117 = parcel.readString();
                    int readInt169 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makePackageIdle(readString117, readInt169);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeterministicUidIdle(readBoolean36);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    int memoryTrimLevel = getMemoryTrimLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(memoryTrimLevel);
                    return true;
                case 200:
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isVrModePackageEnabled = isVrModePackageEnabled(componentName6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVrModePackageEnabled);
                    return true;
                case 201:
                    int readInt170 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyLockedProfile(readInt170);
                    parcel2.writeNoException();
                    return true;
                case 202:
                    android.content.Intent intent17 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.os.Bundle bundle18 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConfirmDeviceCredentialIntent(intent17, bundle18);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    sendIdleJobTrigger();
                    parcel2.writeNoException();
                    return true;
                case 204:
                    android.app.IApplicationThread asInterface63 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.IIntentSender asInterface64 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder42 = parcel.readStrongBinder();
                    int readInt171 = parcel.readInt();
                    android.content.Intent intent18 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString118 = parcel.readString();
                    android.content.IIntentReceiver asInterface65 = android.content.IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString119 = parcel.readString();
                    android.os.Bundle bundle19 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sendIntentSender = sendIntentSender(asInterface63, asInterface64, readStrongBinder42, readInt171, intent18, readString118, asInterface65, readString119, bundle19);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendIntentSender);
                    return true;
                case 205:
                    java.lang.String readString120 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBackgroundRestricted = isBackgroundRestricted(readString120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackgroundRestricted);
                    return true;
                case 206:
                    int readInt172 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRenderThread(readInt172);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    boolean readBoolean37 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHasTopUi(readBoolean37);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    int readInt173 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(readInt173);
                    parcel2.writeNoException();
                    return true;
                case 209:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt174 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(createStringArrayList, readInt174);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    int readInt175 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(readInt175);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForNetworkStateUpdate(readLong7);
                    parcel2.writeNoException();
                    return true;
                case 212:
                    int readInt176 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backgroundAllowlistUid(readInt176);
                    parcel2.writeNoException();
                    return true;
                case 213:
                    int readInt177 = parcel.readInt();
                    android.os.IProgressListener asInterface66 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackgroundWithListener = startUserInBackgroundWithListener(readInt177, asInterface66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackgroundWithListener);
                    return true;
                case 214:
                    int readInt178 = parcel.readInt();
                    java.lang.String[] createStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    startDelegateShellPermissionIdentity(readInt178, createStringArray8);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    stopDelegateShellPermissionIdentity();
                    parcel2.writeNoException();
                    return true;
                case 216:
                    java.util.List<java.lang.String> delegatedShellPermissions = getDelegatedShellPermissions();
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatedShellPermissions);
                    return true;
                case 217:
                    android.os.ParcelFileDescriptor lifeMonitor = getLifeMonitor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lifeMonitor, 1);
                    return true;
                case 218:
                    int readInt179 = parcel.readInt();
                    android.os.IProgressListener asInterface67 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInForegroundWithListener = startUserInForegroundWithListener(readInt179, asInterface67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInForegroundWithListener);
                    return true;
                case 219:
                    java.lang.String readString121 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    appNotResponding(readString121);
                    parcel2.writeNoException();
                    return true;
                case 220:
                    java.lang.String readString122 = parcel.readString();
                    int readInt180 = parcel.readInt();
                    int readInt181 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> historicalProcessStartReasons = getHistoricalProcessStartReasons(readString122, readInt180, readInt181);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(historicalProcessStartReasons, 1);
                    return true;
                case 221:
                    android.app.IApplicationStartInfoCompleteListener asInterface68 = android.app.IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt182 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addApplicationStartInfoCompleteListener(asInterface68, readInt182);
                    parcel2.writeNoException();
                    return true;
                case 222:
                    android.app.IApplicationStartInfoCompleteListener asInterface69 = android.app.IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt183 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeApplicationStartInfoCompleteListener(asInterface69, readInt183);
                    parcel2.writeNoException();
                    return true;
                case 223:
                    int readInt184 = parcel.readInt();
                    long readLong8 = parcel.readLong();
                    int readInt185 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addStartInfoTimestamp(readInt184, readLong8, readInt185);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    java.lang.String readString123 = parcel.readString();
                    int readInt186 = parcel.readInt();
                    int readInt187 = parcel.readInt();
                    int readInt188 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> historicalProcessExitReasons = getHistoricalProcessExitReasons(readString123, readInt186, readInt187, readInt188);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(historicalProcessExitReasons, 1);
                    return true;
                case 225:
                    int[] createIntArray5 = parcel.createIntArray();
                    java.lang.String readString124 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killProcessesWhenImperceptible(createIntArray5, readString124);
                    parcel2.writeNoException();
                    return true;
                case 226:
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.content.LocusId locusId = (android.content.LocusId) parcel.readTypedObject(android.content.LocusId.CREATOR);
                    android.os.IBinder readStrongBinder43 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setActivityLocusContext(componentName7, locusId, readStrongBinder43);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setProcessStateSummary(createByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 228:
                    boolean isAppFreezerSupported = isAppFreezerSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppFreezerSupported);
                    return true;
                case 229:
                    boolean isAppFreezerEnabled = isAppFreezerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppFreezerEnabled);
                    return true;
                case 230:
                    int readInt189 = parcel.readInt();
                    int readInt190 = parcel.readInt();
                    java.lang.String readString125 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUidForPermissionChange(readInt189, readInt190, readString125);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    resetAppErrors();
                    parcel2.writeNoException();
                    return true;
                case 232:
                    boolean readBoolean38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableAppFreezer = enableAppFreezer(readBoolean38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableAppFreezer);
                    return true;
                case 233:
                    boolean readBoolean39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableFgsNotificationRateLimit = enableFgsNotificationRateLimit(readBoolean39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableFgsNotificationRateLimit);
                    return true;
                case 234:
                    android.os.IBinder readStrongBinder44 = parcel.readStrongBinder();
                    int readInt191 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder44, readInt191);
                    parcel2.writeNoException();
                    return true;
                case 235:
                    int readInt192 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startProfile = startProfile(readInt192);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startProfile);
                    return true;
                case 236:
                    int readInt193 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean stopProfile = stopProfile(readInt193);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopProfile);
                    return true;
                case 237:
                    android.content.IIntentSender asInterface70 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    int readInt194 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryIntentComponentsForIntentSender = queryIntentComponentsForIntentSender(asInterface70, readInt194);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentComponentsForIntentSender, 1);
                    return true;
                case 238:
                    int readInt195 = parcel.readInt();
                    java.lang.String readString126 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessCapabilities = getUidProcessCapabilities(readInt195, readString126);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidProcessCapabilities);
                    return true;
                case 239:
                    waitForBroadcastIdle();
                    parcel2.writeNoException();
                    return true;
                case 240:
                    waitForBroadcastBarrier();
                    parcel2.writeNoException();
                    return true;
                case 241:
                    java.lang.String readString127 = parcel.readString();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    forceDelayBroadcastDelivery(readString127, readLong9);
                    parcel2.writeNoException();
                    return true;
                case 242:
                    boolean isModernBroadcastQueueEnabled = isModernBroadcastQueueEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isModernBroadcastQueueEnabled);
                    return true;
                case 243:
                    int readInt196 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProcessFrozen = isProcessFrozen(readInt196);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProcessFrozen);
                    return true;
                case 244:
                    int readInt197 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int backgroundRestrictionExemptionReason = getBackgroundRestrictionExemptionReason(readInt197);
                    parcel2.writeNoException();
                    parcel2.writeInt(backgroundRestrictionExemptionReason);
                    return true;
                case 245:
                    int readInt198 = parcel.readInt();
                    int readInt199 = parcel.readInt();
                    android.os.IProgressListener asInterface71 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackgroundVisibleOnDisplay = startUserInBackgroundVisibleOnDisplay(readInt198, readInt199, asInterface71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackgroundVisibleOnDisplay);
                    return true;
                case 246:
                    int readInt200 = parcel.readInt();
                    android.os.IProgressListener asInterface72 = android.os.IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startProfileWithListener = startProfileWithListener(readInt200, asInterface72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startProfileWithListener);
                    return true;
                case 247:
                    int readInt201 = parcel.readInt();
                    int readInt202 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int restartUserInBackground = restartUserInBackground(readInt201, readInt202);
                    parcel2.writeNoException();
                    parcel2.writeInt(restartUserInBackground);
                    return true;
                case 248:
                    int[] displayIdsForStartingVisibleBackgroundUsers = getDisplayIdsForStartingVisibleBackgroundUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIdsForStartingVisibleBackgroundUsers);
                    return true;
                case 249:
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder45 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean shouldServiceTimeOut = shouldServiceTimeOut(componentName8, readStrongBinder45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldServiceTimeOut);
                    return true;
                case 250:
                    android.content.ComponentName componentName9 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.IBinder readStrongBinder46 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean hasServiceTimeLimitExceeded = hasServiceTimeLimitExceeded(componentName9, readStrongBinder46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasServiceTimeLimitExceeded);
                    return true;
                case 251:
                    android.app.IUidFrozenStateChangedCallback asInterface73 = android.app.IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUidFrozenStateChangedCallback(asInterface73);
                    parcel2.writeNoException();
                    return true;
                case 252:
                    android.app.IUidFrozenStateChangedCallback asInterface74 = android.app.IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidFrozenStateChangedCallback(asInterface74);
                    parcel2.writeNoException();
                    return true;
                case 253:
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] uidFrozenState = getUidFrozenState(createIntArray6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uidFrozenState);
                    return true;
                case 254:
                    java.lang.String readString128 = parcel.readString();
                    int readInt203 = parcel.readInt();
                    int readInt204 = parcel.readInt();
                    int readInt205 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermissionForDevice = checkPermissionForDevice(readString128, readInt203, readInt204, readInt205);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermissionForDevice);
                    return true;
                case 255:
                    int readInt206 = parcel.readInt();
                    int readInt207 = parcel.readInt();
                    int readInt208 = parcel.readInt();
                    int readInt209 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    frozenBinderTransactionDetected(readInt206, readInt207, readInt208, readInt209);
                    return true;
                case 256:
                    int readInt210 = parcel.readInt();
                    java.lang.String readString129 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int bindingUidProcessState = getBindingUidProcessState(readInt210, readString129);
                    parcel2.writeNoException();
                    parcel2.writeInt(bindingUidProcessState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IActivityManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IActivityManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.IActivityManager
            public android.os.ParcelFileDescriptor openContentUri(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidObserver(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidObserver(android.app.IUidObserver iUidObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.os.IBinder registerUidObserverForUids(android.app.IUidObserver iUidObserver, int i, int i2, java.lang.String str, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addUidToObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeUidFromObserver(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUidActive(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessState(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermission(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiBegin(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiEnd(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationCrash(android.os.IBinder iBinder, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(parcelableCrashInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unhandledBack() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.Intent registerReceiver(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.Intent registerReceiverWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.IIntentReceiver iIntentReceiver, android.content.IntentFilter intentFilter, java.lang.String str4, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterReceiver(android.content.IIntentReceiver iIntentReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentReceiver);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str2, android.os.Bundle bundle, java.lang.String[] strArr, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntentWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str3, android.os.Bundle bundle, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeStringArray(strArr3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbroadcastIntent(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishReceiver(android.os.IBinder iBinder, int i, java.lang.String str, android.os.Bundle bundle, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void attachApplication(android.app.IApplicationThread iApplicationThread, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeLong(j);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishAttachApplication(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.app.ContentProviderHolder getContentProvider(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ContentProviderHolder) obtain2.readTypedObject(android.app.ContentProviderHolder.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishContentProviders(android.app.IApplicationThread iApplicationThread, java.util.List<android.app.ContentProviderHolder> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean refContentProvider(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.app.PendingIntent getRunningServiceControlPanel(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.ComponentName startService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopService(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindService(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindServiceInstance(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateServiceGroup(android.app.IServiceConnection iServiceConnection, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unbindService(android.app.IServiceConnection iServiceConnection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iServiceConnection);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishService(android.os.IBinder iBinder, android.content.Intent intent, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDebugApp(java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAgentApp(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAlwaysFinish(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, int i, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i2, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iInstrumentationWatcher);
                    obtain.writeStrongInterface(iUiAutomationConnection);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addInstrumentationResults(android.app.IApplicationThread iApplicationThread, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishInstrumentation(android.app.IApplicationThread iApplicationThread, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.res.Configuration getConfiguration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.res.Configuration) obtain2.readTypedObject(android.content.res.Configuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateMccMncConfiguration(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopServiceToken(android.content.ComponentName componentName, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessLimit(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getProcessLimit() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3, int i4, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void grantUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void revokeUriPermission(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityController);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showWaitingForDebugger(android.app.IApplicationThread iApplicationThread, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void signalPersistentProcesses(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.pm.ParceledListSlice getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void serviceDoneExecuting(android.os.IBinder iBinder, int i, int i2, int i3, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(62, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.IIntentSender getIntentSender(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.String str2, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.IIntentSender.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.IIntentSender getIntentSenderWithFeature(int i, java.lang.String str, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i2, android.content.Intent[] intentArr, java.lang.String[] strArr, int i3, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.IIntentSender.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.app.ActivityManager.PendingIntentInfo getInfoForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityManager.PendingIntentInfo) obtain2.readTypedObject(android.app.ActivityManager.PendingIntentInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerIntentSenderCancelListenerEx(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterIntentSenderCancelListener(android.content.IIntentSender iIntentSender, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void enterSafeMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteWakeupAlarm(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProvider(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindFinished(android.os.IBinder iBinder, android.content.Intent intent, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessImportant(android.os.IBinder iBinder, int i, boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setServiceForeground(android.content.ComponentName componentName, android.os.IBinder iBinder, int i, android.app.Notification notification, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notification, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getForegroundServiceType(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        memoryInfo.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.app.ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.ProcessErrorStateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearApplicationUserData(java.lang.String str, boolean z, android.content.pm.IPackageDataObserver iPackageDataObserver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerForegroundServiceObserver(android.app.IForegroundServiceObserver iForegroundServiceObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iForegroundServiceObserver);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackage(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageEvenWhenStopping(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killPids(int[] iArr, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.app.ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningAppProcessInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.os.IBinder peekService(android.content.Intent intent, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean profileControl(java.lang.String str, int i, boolean z, android.app.ProfilerInfo profilerInfo, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shutdown(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppSwitches() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resumeAppSwitches() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean bindBackupAgent(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backupAgentCreated(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindBackupAgent(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addPackageDependency(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplication(java.lang.String str, int i, int i2, java.lang.String str2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.os.Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Debug.MemoryInfo[]) obtain2.createTypedArray(android.os.Debug.MemoryInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplicationProcess(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean handleApplicationWtf(android.os.IBinder iBinder, java.lang.String str, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(parcelableCrashInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killBackgroundProcesses(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserAMonkey() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.content.pm.ApplicationInfo> getRunningExternalApplications() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishHeavyWeightApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationStrictModeViolation(android.os.IBinder iBinder, int i, android.os.StrictMode.ViolationInfo violationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(violationInfo, 0);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerStrictModeCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopActivityImmersive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithType(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithTypeWithExtras(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMimeTypeFilterAsync(android.net.Uri uri, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(112, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean dumpHeap(java.lang.String str, int i, boolean z, boolean z2, boolean z3, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserRunning(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean switchUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.lang.String getSwitchingFromUserMessage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.lang.String getSwitchingToUserMessage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setStopUserOnSwitch(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterProcessObserver(android.app.IProcessObserver iProcessObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderTargetedToPackage(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfigurationWithAttribution(android.content.res.Configuration configuration, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long[] getProcessPss(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showBootMessage(java.lang.CharSequence charSequence, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeBoolean(z);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killAllBackgroundProcesses() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.app.ContentProviderHolder getContentProviderExternal(java.lang.String str, int i, android.os.IBinder iBinder, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str2);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ContentProviderHolder) obtain2.readTypedObject(android.app.ContentProviderHolder.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternal(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternalAsUser(java.lang.String str, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMyMemoryState(android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        runningAppProcessInfo.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killProcessesBelowForeground(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.pm.UserInfo getCurrentUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getCurrentUserId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unstableProviderDied(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAnActivity(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUserWithFeature(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUser(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithDelayedLocking(int i, boolean z, android.app.IStopUserCallback iStopUserCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserSwitchObserver);
                    obtain.writeString(str);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserSwitchObserver);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getRunningUserIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestSystemServerHeapDump() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReport(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithDescription(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestTelephonyBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestWifiBugReport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReportWithDescription(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReport() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestFullBugReport() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestRemoteBugReport(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean launchBugReportHandlerApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<java.lang.String> getBugreportWhitelistedPackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.Intent getIntentForIntentSender(android.content.IIntentSender iIntentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUid(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setUserIsMonkey(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void hang(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setFocusedRootTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void restart() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void performIdleMaintenance() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotRespondingViaProvider(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Rect) obtain2.readTypedObject(android.graphics.Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessMemoryTrimLevel(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.lang.String getTagForIntentSender(android.content.IIntentSender iIntentSender, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeString(str);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackground(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isInLockTaskMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startSystemLockTaskMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void bootAnimationComplete() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setThemeOverlayReady(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyCleartextNetwork(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTaskResizeable(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLockTaskModeState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDumpHeapDebugLimit(java.lang.String str, int i, long j, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dumpHeapFinished(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmStart(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmFinish(android.content.IIntentSender iIntentSender, android.os.WorkSource workSource, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getPackageProcessState(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startBinderTracking() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser(int i, byte[] bArr, byte[] bArr2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser2(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killPackageDependents(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void makePackageIdle(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDeterministicUidIdle(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMemoryTrimLevel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isVrModePackageEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyLockedProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startConfirmDeviceCredentialIntent(android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void sendIdleJobTrigger() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int sendIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, int i, android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isBackgroundRestricted(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRenderThread(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setHasTopUi(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelTaskWindowTransition(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void scheduleApplicationInfoChanged(java.util.List<java.lang.String> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPersistentVrThread(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForNetworkStateUpdate(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backgroundAllowlistUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startDelegateShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopDelegateShellPermissionIdentity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public java.util.List<java.lang.String> getDelegatedShellPermissions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.os.ParcelFileDescriptor getLifeMonitor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInForegroundWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotResponding(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> getHistoricalProcessStartReasons(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeApplicationStartInfoCompleteListener(android.app.IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addStartInfoTimestamp(int i, long j, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> getHistoricalProcessExitReasons(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killProcessesWhenImperceptible(int[] iArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityLocusContext(android.content.ComponentName componentName, android.content.LocusId locusId, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(locusId, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessStateSummary(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUidForPermissionChange(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAppErrors() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableAppFreezer(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableFgsNotificationRateLimit(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public android.content.pm.ParceledListSlice queryIntentComponentsForIntentSender(android.content.IIntentSender iIntentSender, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeInt(i);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessCapabilities(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastIdle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastBarrier() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceDelayBroadcastDelivery(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isModernBroadcastQueueEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isProcessFrozen(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBackgroundRestrictionExemptionReason(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfileWithListener(int i, android.os.IProgressListener iProgressListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int restartUserInBackground(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shouldServiceTimeOut(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean hasServiceTimeLimitExceeded(android.content.ComponentName componentName, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidFrozenStateChangedCallback(android.app.IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getUidFrozenState(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermissionForDevice(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(255, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBindingUidProcessState(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 255;
        }
    }
}
