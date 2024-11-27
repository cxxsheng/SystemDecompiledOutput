package android.app;

/* loaded from: classes.dex */
public interface IActivityTaskManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IActivityTaskManager";

    int addAppTask(android.os.IBinder iBinder, android.content.Intent intent, android.app.ActivityManager.TaskDescription taskDescription, android.graphics.Bitmap bitmap) throws android.os.RemoteException;

    void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) throws android.os.RemoteException;

    void cancelRecentsAnimation(boolean z) throws android.os.RemoteException;

    void cancelTaskWindowTransition(int i) throws android.os.RemoteException;

    void clearLaunchParamsForPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void detachNavigationBarFromApp(android.os.IBinder iBinder) throws android.os.RemoteException;

    void finishVoiceTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) throws android.os.RemoteException;

    void focusTopTask(int i) throws android.os.RemoteException;

    android.app.IActivityClientController getActivityClientController() throws android.os.RemoteException;

    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException;

    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws android.os.RemoteException;

    android.graphics.Point getAppTaskThumbnailSize() throws android.os.RemoteException;

    java.util.List<android.os.IBinder> getAppTasks(java.lang.String str) throws android.os.RemoteException;

    android.os.Bundle getAssistContextExtras(int i) throws android.os.RemoteException;

    android.content.pm.ConfigurationInfo getDeviceConfigurationInfo() throws android.os.RemoteException;

    android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException;

    int getFrontActivityScreenCompatMode() throws android.os.RemoteException;

    int getLastResumedActivityUid() throws android.os.RemoteException;

    int getLastResumedActivityUserId() throws android.os.RemoteException;

    int getLockTaskModeState() throws android.os.RemoteException;

    boolean getPackageAskScreenCompat(java.lang.String str) throws android.os.RemoteException;

    int getPackageScreenCompatMode(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException;

    android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws android.os.RemoteException;

    android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws android.os.RemoteException;

    android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException;

    android.app.ActivityManager.TaskDescription getTaskDescription(int i) throws android.os.RemoteException;

    android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str, int i) throws android.os.RemoteException;

    android.window.TaskSnapshot getTaskSnapshot(int i, boolean z) throws android.os.RemoteException;

    java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws android.os.RemoteException;

    java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.window.IWindowOrganizerController getWindowOrganizerController() throws android.os.RemoteException;

    boolean isActivityStartAllowedOnDisplay(int i, android.content.Intent intent, java.lang.String str, int i2) throws android.os.RemoteException;

    boolean isAssistDataAllowed() throws android.os.RemoteException;

    boolean isInLockTaskMode() throws android.os.RemoteException;

    boolean isTopActivityImmersive() throws android.os.RemoteException;

    void keyguardGoingAway(int i) throws android.os.RemoteException;

    void moveRootTaskToDisplay(int i, int i2) throws android.os.RemoteException;

    void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException;

    void onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState pictureInPictureUiState) throws android.os.RemoteException;

    void onSplashScreenViewCopyFinished(int i, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws android.os.RemoteException;

    void registerRemoteAnimationForNextActivityStart(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.os.IBinder iBinder) throws android.os.RemoteException;

    void registerRemoteAnimationsForDisplay(int i, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException;

    void registerScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException;

    void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException;

    void releaseSomeActivities(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException;

    void removeAllVisibleRecentTasks() throws android.os.RemoteException;

    void removeRootTasksInWindowingModes(int[] iArr) throws android.os.RemoteException;

    void removeRootTasksWithActivityTypes(int[] iArr) throws android.os.RemoteException;

    boolean removeTask(int i) throws android.os.RemoteException;

    void reportAssistContextExtras(android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, android.net.Uri uri) throws android.os.RemoteException;

    boolean requestAssistContextExtras(int i, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException;

    boolean requestAssistDataForTask(android.app.IAssistDataReceiver iAssistDataReceiver, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean requestAutofillData(android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException;

    void resumeAppSwitches() throws android.os.RemoteException;

    void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException;

    void setFocusedRootTask(int i) throws android.os.RemoteException;

    void setFocusedTask(int i) throws android.os.RemoteException;

    void setFrontActivityScreenCompatMode(int i) throws android.os.RemoteException;

    void setLockScreenShown(boolean z, boolean z2) throws android.os.RemoteException;

    void setPackageAskScreenCompat(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException;

    void setPersistentVrThread(int i) throws android.os.RemoteException;

    void setRunningRemoteTransitionDelegate(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException;

    void setTaskResizeable(int i, int i2) throws android.os.RemoteException;

    void setVoiceKeepAwake(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws android.os.RemoteException;

    void setVrThread(int i) throws android.os.RemoteException;

    int startActivities(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException;

    android.app.WaitResult startActivityAndWait(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    int startActivityAsCaller(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, boolean z, int i3) throws android.os.RemoteException;

    int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    int startActivityFromGameSession(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, int i3, int i4) throws android.os.RemoteException;

    int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    int startActivityIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder2, java.lang.String str2, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException;

    int startActivityWithConfig(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.content.res.Configuration configuration, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    int startAssistantActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle, int i3) throws android.os.RemoteException;

    android.window.BackNavigationInfo startBackNavigation(android.os.RemoteCallback remoteCallback, android.window.BackAnimationAdapter backAnimationAdapter) throws android.os.RemoteException;

    boolean startNextMatchingActivity(android.os.IBinder iBinder, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException;

    void startRecentsActivity(android.content.Intent intent, long j, android.view.IRecentsAnimationRunner iRecentsAnimationRunner) throws android.os.RemoteException;

    void startSystemLockTaskMode(int i) throws android.os.RemoteException;

    int startVoiceActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i4) throws android.os.RemoteException;

    void stopAppSwitches() throws android.os.RemoteException;

    void stopSystemLockTaskMode() throws android.os.RemoteException;

    boolean supportsLocalVoiceInteraction() throws android.os.RemoteException;

    void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException;

    android.window.TaskSnapshot takeTaskSnapshot(int i, boolean z) throws android.os.RemoteException;

    void unhandledBack() throws android.os.RemoteException;

    void unregisterScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException;

    void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException;

    boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException;

    void updateLockTaskFeatures(int i, int i2) throws android.os.RemoteException;

    void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    public static class Default implements android.app.IActivityTaskManager {
        @Override // android.app.IActivityTaskManager
        public int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivities(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public boolean startNextMatchingActivity(android.os.IBinder iBinder, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder2, java.lang.String str2, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public android.app.WaitResult startActivityAndWait(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityWithConfig(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.content.res.Configuration configuration, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startVoiceActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int startAssistantActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityFromGameSession(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, int i3, int i4) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void startRecentsActivity(android.content.Intent intent, long j, android.view.IRecentsAnimationRunner iRecentsAnimationRunner) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityAsCaller(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, boolean z, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isActivityStartAllowedOnDisplay(int i, android.content.Intent intent, java.lang.String str, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void unhandledBack() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.app.IActivityClientController getActivityClientController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getFrontActivityScreenCompatMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void setFrontActivityScreenCompatMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setFocusedTask(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean removeTask(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void removeAllVisibleRecentTasks() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isTopActivityImmersive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public android.app.ActivityManager.TaskDescription getTaskDescription(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void reportAssistContextExtras(android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setFocusedRootTask(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void focusTopTask(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void cancelRecentsAnimation(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean isInLockTaskMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public int getLockTaskModeState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public java.util.List<android.os.IBinder> getAppTasks(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void startSystemLockTaskMode(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void stopSystemLockTaskMode() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void finishVoiceTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int addAppTask(android.os.IBinder iBinder, android.content.Intent intent, android.app.ActivityManager.TaskDescription taskDescription, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public android.graphics.Point getAppTaskThumbnailSize() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void releaseSomeActivities(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setTaskResizeable(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void moveRootTaskToDisplay(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeRootTasksInWindowingModes(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeRootTasksWithActivityTypes(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void setLockScreenShown(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.os.Bundle getAssistContextExtras(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistContextExtras(int i, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAutofillData(android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isAssistDataAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistDataForTask(android.app.IAssistDataReceiver iAssistDataReceiver, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void keyguardGoingAway(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.window.IWindowOrganizerController getWindowOrganizerController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean supportsLocalVoiceInteraction() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public android.content.pm.ConfigurationInfo getDeviceConfigurationInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void cancelTaskWindowTransition(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.window.TaskSnapshot getTaskSnapshot(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public android.window.TaskSnapshot takeTaskSnapshot(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getLastResumedActivityUserId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int getLastResumedActivityUid() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void updateLockTaskFeatures(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerRemoteAnimationForNextActivityStart(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerRemoteAnimationsForDisplay(int i, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setVrThread(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setPersistentVrThread(int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void stopAppSwitches() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void resumeAppSwitches() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setVoiceKeepAwake(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int getPackageScreenCompatMode(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean getPackageAskScreenCompat(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void setPackageAskScreenCompat(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void clearLaunchParamsForPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void onSplashScreenViewCopyFinished(int i, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState pictureInPictureUiState) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void detachNavigationBarFromApp(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setRunningRemoteTransitionDelegate(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public android.window.BackNavigationInfo startBackNavigation(android.os.RemoteCallback remoteCallback, android.window.BackAnimationAdapter backAnimationAdapter) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void registerScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void unregisterScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IActivityTaskManager {
        static final int TRANSACTION_addAppTask = 41;
        static final int TRANSACTION_alwaysShowUnsupportedCompileSdkWarning = 77;
        static final int TRANSACTION_cancelRecentsAnimation = 33;
        static final int TRANSACTION_cancelTaskWindowTransition = 68;
        static final int TRANSACTION_clearLaunchParamsForPackages = 88;
        static final int TRANSACTION_detachNavigationBarFromApp = 91;
        static final int TRANSACTION_finishVoiceTask = 40;
        static final int TRANSACTION_focusTopTask = 32;
        static final int TRANSACTION_getActivityClientController = 17;
        static final int TRANSACTION_getAllRootTaskInfos = 53;
        static final int TRANSACTION_getAllRootTaskInfosOnDisplay = 55;
        static final int TRANSACTION_getAppTaskThumbnailSize = 42;
        static final int TRANSACTION_getAppTasks = 37;
        static final int TRANSACTION_getAssistContextExtras = 58;
        static final int TRANSACTION_getDeviceConfigurationInfo = 67;
        static final int TRANSACTION_getFocusedRootTaskInfo = 30;
        static final int TRANSACTION_getFrontActivityScreenCompatMode = 18;
        static final int TRANSACTION_getLastResumedActivityUid = 72;
        static final int TRANSACTION_getLastResumedActivityUserId = 71;
        static final int TRANSACTION_getLockTaskModeState = 36;
        static final int TRANSACTION_getPackageAskScreenCompat = 86;
        static final int TRANSACTION_getPackageScreenCompatMode = 84;
        static final int TRANSACTION_getRecentTasks = 25;
        static final int TRANSACTION_getRootTaskInfo = 54;
        static final int TRANSACTION_getRootTaskInfoOnDisplay = 56;
        static final int TRANSACTION_getTaskBounds = 31;
        static final int TRANSACTION_getTaskDescription = 27;
        static final int TRANSACTION_getTaskDescriptionIcon = 44;
        static final int TRANSACTION_getTaskSnapshot = 69;
        static final int TRANSACTION_getTasks = 23;
        static final int TRANSACTION_getVoiceInteractorPackageName = 9;
        static final int TRANSACTION_getWindowOrganizerController = 65;
        static final int TRANSACTION_isActivityStartAllowedOnDisplay = 15;
        static final int TRANSACTION_isAssistDataAllowed = 61;
        static final int TRANSACTION_isInLockTaskMode = 35;
        static final int TRANSACTION_isTopActivityImmersive = 26;
        static final int TRANSACTION_keyguardGoingAway = 63;
        static final int TRANSACTION_moveRootTaskToDisplay = 49;
        static final int TRANSACTION_moveTaskToFront = 24;
        static final int TRANSACTION_moveTaskToRootTask = 50;
        static final int TRANSACTION_onPictureInPictureUiStateChanged = 90;
        static final int TRANSACTION_onSplashScreenViewCopyFinished = 89;
        static final int TRANSACTION_registerRemoteAnimationForNextActivityStart = 75;
        static final int TRANSACTION_registerRemoteAnimationsForDisplay = 76;
        static final int TRANSACTION_registerScreenCaptureObserver = 94;
        static final int TRANSACTION_registerTaskStackListener = 45;
        static final int TRANSACTION_releaseSomeActivities = 43;
        static final int TRANSACTION_removeAllVisibleRecentTasks = 22;
        static final int TRANSACTION_removeRootTasksInWindowingModes = 51;
        static final int TRANSACTION_removeRootTasksWithActivityTypes = 52;
        static final int TRANSACTION_removeTask = 21;
        static final int TRANSACTION_reportAssistContextExtras = 28;
        static final int TRANSACTION_requestAssistContextExtras = 59;
        static final int TRANSACTION_requestAssistDataForTask = 62;
        static final int TRANSACTION_requestAutofillData = 60;
        static final int TRANSACTION_resizeTask = 48;
        static final int TRANSACTION_resumeAppSwitches = 81;
        static final int TRANSACTION_setActivityController = 82;
        static final int TRANSACTION_setFocusedRootTask = 29;
        static final int TRANSACTION_setFocusedTask = 20;
        static final int TRANSACTION_setFrontActivityScreenCompatMode = 19;
        static final int TRANSACTION_setLockScreenShown = 57;
        static final int TRANSACTION_setPackageAskScreenCompat = 87;
        static final int TRANSACTION_setPackageScreenCompatMode = 85;
        static final int TRANSACTION_setPersistentVrThread = 79;
        static final int TRANSACTION_setRunningRemoteTransitionDelegate = 92;
        static final int TRANSACTION_setTaskResizeable = 47;
        static final int TRANSACTION_setVoiceKeepAwake = 83;
        static final int TRANSACTION_setVrThread = 78;
        static final int TRANSACTION_startActivities = 2;
        static final int TRANSACTION_startActivity = 1;
        static final int TRANSACTION_startActivityAndWait = 6;
        static final int TRANSACTION_startActivityAsCaller = 14;
        static final int TRANSACTION_startActivityAsUser = 3;
        static final int TRANSACTION_startActivityFromGameSession = 11;
        static final int TRANSACTION_startActivityFromRecents = 13;
        static final int TRANSACTION_startActivityIntentSender = 5;
        static final int TRANSACTION_startActivityWithConfig = 7;
        static final int TRANSACTION_startAssistantActivity = 10;
        static final int TRANSACTION_startBackNavigation = 93;
        static final int TRANSACTION_startNextMatchingActivity = 4;
        static final int TRANSACTION_startRecentsActivity = 12;
        static final int TRANSACTION_startSystemLockTaskMode = 38;
        static final int TRANSACTION_startVoiceActivity = 8;
        static final int TRANSACTION_stopAppSwitches = 80;
        static final int TRANSACTION_stopSystemLockTaskMode = 39;
        static final int TRANSACTION_supportsLocalVoiceInteraction = 66;
        static final int TRANSACTION_suppressResizeConfigChanges = 64;
        static final int TRANSACTION_takeTaskSnapshot = 70;
        static final int TRANSACTION_unhandledBack = 16;
        static final int TRANSACTION_unregisterScreenCaptureObserver = 95;
        static final int TRANSACTION_unregisterTaskStackListener = 46;
        static final int TRANSACTION_updateConfiguration = 73;
        static final int TRANSACTION_updateLockTaskFeatures = 74;
        static final int TRANSACTION_updateLockTaskPackages = 34;

        public Stub() {
            attachInterface(this, android.app.IActivityTaskManager.DESCRIPTOR);
        }

        public static android.app.IActivityTaskManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IActivityTaskManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IActivityTaskManager)) {
                return (android.app.IActivityTaskManager) queryLocalInterface;
            }
            return new android.app.IActivityTaskManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startActivity";
                case 2:
                    return "startActivities";
                case 3:
                    return "startActivityAsUser";
                case 4:
                    return "startNextMatchingActivity";
                case 5:
                    return "startActivityIntentSender";
                case 6:
                    return "startActivityAndWait";
                case 7:
                    return "startActivityWithConfig";
                case 8:
                    return "startVoiceActivity";
                case 9:
                    return "getVoiceInteractorPackageName";
                case 10:
                    return "startAssistantActivity";
                case 11:
                    return "startActivityFromGameSession";
                case 12:
                    return "startRecentsActivity";
                case 13:
                    return "startActivityFromRecents";
                case 14:
                    return "startActivityAsCaller";
                case 15:
                    return "isActivityStartAllowedOnDisplay";
                case 16:
                    return "unhandledBack";
                case 17:
                    return "getActivityClientController";
                case 18:
                    return "getFrontActivityScreenCompatMode";
                case 19:
                    return "setFrontActivityScreenCompatMode";
                case 20:
                    return "setFocusedTask";
                case 21:
                    return "removeTask";
                case 22:
                    return "removeAllVisibleRecentTasks";
                case 23:
                    return "getTasks";
                case 24:
                    return "moveTaskToFront";
                case 25:
                    return "getRecentTasks";
                case 26:
                    return "isTopActivityImmersive";
                case 27:
                    return "getTaskDescription";
                case 28:
                    return "reportAssistContextExtras";
                case 29:
                    return "setFocusedRootTask";
                case 30:
                    return "getFocusedRootTaskInfo";
                case 31:
                    return "getTaskBounds";
                case 32:
                    return "focusTopTask";
                case 33:
                    return "cancelRecentsAnimation";
                case 34:
                    return "updateLockTaskPackages";
                case 35:
                    return "isInLockTaskMode";
                case 36:
                    return "getLockTaskModeState";
                case 37:
                    return "getAppTasks";
                case 38:
                    return "startSystemLockTaskMode";
                case 39:
                    return "stopSystemLockTaskMode";
                case 40:
                    return "finishVoiceTask";
                case 41:
                    return "addAppTask";
                case 42:
                    return "getAppTaskThumbnailSize";
                case 43:
                    return "releaseSomeActivities";
                case 44:
                    return "getTaskDescriptionIcon";
                case 45:
                    return "registerTaskStackListener";
                case 46:
                    return "unregisterTaskStackListener";
                case 47:
                    return "setTaskResizeable";
                case 48:
                    return "resizeTask";
                case 49:
                    return "moveRootTaskToDisplay";
                case 50:
                    return "moveTaskToRootTask";
                case 51:
                    return "removeRootTasksInWindowingModes";
                case 52:
                    return "removeRootTasksWithActivityTypes";
                case 53:
                    return "getAllRootTaskInfos";
                case 54:
                    return "getRootTaskInfo";
                case 55:
                    return "getAllRootTaskInfosOnDisplay";
                case 56:
                    return "getRootTaskInfoOnDisplay";
                case 57:
                    return "setLockScreenShown";
                case 58:
                    return "getAssistContextExtras";
                case 59:
                    return "requestAssistContextExtras";
                case 60:
                    return "requestAutofillData";
                case 61:
                    return "isAssistDataAllowed";
                case 62:
                    return "requestAssistDataForTask";
                case 63:
                    return "keyguardGoingAway";
                case 64:
                    return "suppressResizeConfigChanges";
                case 65:
                    return "getWindowOrganizerController";
                case 66:
                    return "supportsLocalVoiceInteraction";
                case 67:
                    return "getDeviceConfigurationInfo";
                case 68:
                    return "cancelTaskWindowTransition";
                case 69:
                    return "getTaskSnapshot";
                case 70:
                    return "takeTaskSnapshot";
                case 71:
                    return "getLastResumedActivityUserId";
                case 72:
                    return "getLastResumedActivityUid";
                case 73:
                    return "updateConfiguration";
                case 74:
                    return "updateLockTaskFeatures";
                case 75:
                    return "registerRemoteAnimationForNextActivityStart";
                case 76:
                    return "registerRemoteAnimationsForDisplay";
                case 77:
                    return "alwaysShowUnsupportedCompileSdkWarning";
                case 78:
                    return "setVrThread";
                case 79:
                    return "setPersistentVrThread";
                case 80:
                    return "stopAppSwitches";
                case 81:
                    return "resumeAppSwitches";
                case 82:
                    return "setActivityController";
                case 83:
                    return "setVoiceKeepAwake";
                case 84:
                    return "getPackageScreenCompatMode";
                case 85:
                    return "setPackageScreenCompatMode";
                case 86:
                    return "getPackageAskScreenCompat";
                case 87:
                    return "setPackageAskScreenCompat";
                case 88:
                    return "clearLaunchParamsForPackages";
                case 89:
                    return "onSplashScreenViewCopyFinished";
                case 90:
                    return "onPictureInPictureUiStateChanged";
                case 91:
                    return "detachNavigationBarFromApp";
                case 92:
                    return "setRunningRemoteTransitionDelegate";
                case 93:
                    return "startBackNavigation";
                case 94:
                    return "registerScreenCaptureObserver";
                case 95:
                    return "unregisterScreenCaptureObserver";
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
                parcel.enforceInterface(android.app.IActivityTaskManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IActivityTaskManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.IApplicationThread asInterface = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivity = startActivity(asInterface, readString, readString2, intent, readString3, readStrongBinder, readString4, readInt, readInt2, profilerInfo, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivity);
                    return true;
                case 2:
                    android.app.IApplicationThread asInterface2 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    android.content.Intent[] intentArr = (android.content.Intent[]) parcel.createTypedArray(android.content.Intent.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivities = startActivities(asInterface2, readString5, readString6, intentArr, createStringArray, readStrongBinder2, bundle2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivities);
                    return true;
                case 3:
                    android.app.IApplicationThread asInterface3 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString9 = parcel.readString();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString10 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo2 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsUser = startActivityAsUser(asInterface3, readString7, readString8, intent2, readString9, readStrongBinder3, readString10, readInt4, readInt5, profilerInfo2, bundle3, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsUser);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startNextMatchingActivity = startNextMatchingActivity(readStrongBinder4, intent3, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startNextMatchingActivity);
                    return true;
                case 5:
                    android.app.IApplicationThread asInterface4 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.content.IIntentSender asInterface5 = android.content.IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.content.Intent intent4 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    java.lang.String readString12 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityIntentSender = startActivityIntentSender(asInterface4, asInterface5, readStrongBinder5, intent4, readString11, readStrongBinder6, readString12, readInt7, readInt8, readInt9, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityIntentSender);
                    return true;
                case 6:
                    android.app.IApplicationThread asInterface6 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    android.content.Intent intent5 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    java.lang.String readString16 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo3 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.WaitResult startActivityAndWait = startActivityAndWait(asInterface6, readString13, readString14, intent5, readString15, readStrongBinder7, readString16, readInt10, readInt11, profilerInfo3, bundle6, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startActivityAndWait, 1);
                    return true;
                case 7:
                    android.app.IApplicationThread asInterface7 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    android.content.Intent intent6 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString19 = parcel.readString();
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    java.lang.String readString20 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    android.content.res.Configuration configuration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityWithConfig = startActivityWithConfig(asInterface7, readString17, readString18, intent6, readString19, readStrongBinder8, readString20, readInt13, readInt14, configuration, bundle7, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityWithConfig);
                    return true;
                case 8:
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    android.content.Intent intent7 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString23 = parcel.readString();
                    android.service.voice.IVoiceInteractionSession asInterface8 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.app.IVoiceInteractor asInterface9 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo4 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startVoiceActivity = startVoiceActivity(readString21, readString22, readInt16, readInt17, intent7, readString23, asInterface8, asInterface9, readInt18, profilerInfo4, bundle8, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(startVoiceActivity);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceInteractorPackageName = getVoiceInteractorPackageName(readStrongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceInteractorPackageName);
                    return true;
                case 10:
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    android.content.Intent intent8 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString26 = parcel.readString();
                    android.os.Bundle bundle9 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startAssistantActivity = startAssistantActivity(readString24, readString25, readInt20, readInt21, intent8, readString26, bundle9, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAssistantActivity);
                    return true;
                case 11:
                    android.app.IApplicationThread asInterface10 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    android.content.Intent intent9 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityFromGameSession = startActivityFromGameSession(asInterface10, readString27, readString28, readInt23, readInt24, intent9, readInt25, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromGameSession);
                    return true;
                case 12:
                    android.content.Intent intent10 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    long readLong = parcel.readLong();
                    android.view.IRecentsAnimationRunner asInterface11 = android.view.IRecentsAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startRecentsActivity(intent10, readLong, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt27 = parcel.readInt();
                    android.os.Bundle bundle10 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityFromRecents = startActivityFromRecents(readInt27, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromRecents);
                    return true;
                case 14:
                    android.app.IApplicationThread asInterface12 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString29 = parcel.readString();
                    android.content.Intent intent11 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString30 = parcel.readString();
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    java.lang.String readString31 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    android.app.ProfilerInfo profilerInfo5 = (android.app.ProfilerInfo) parcel.readTypedObject(android.app.ProfilerInfo.CREATOR);
                    android.os.Bundle bundle11 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsCaller = startActivityAsCaller(asInterface12, readString29, intent11, readString30, readStrongBinder10, readString31, readInt28, readInt29, profilerInfo5, bundle11, readBoolean, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsCaller);
                    return true;
                case 15:
                    int readInt31 = parcel.readInt();
                    android.content.Intent intent12 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString32 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isActivityStartAllowedOnDisplay = isActivityStartAllowedOnDisplay(readInt31, intent12, readString32, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityStartAllowedOnDisplay);
                    return true;
                case 16:
                    unhandledBack();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.app.IActivityClientController activityClientController = getActivityClientController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(activityClientController);
                    return true;
                case 18:
                    int frontActivityScreenCompatMode = getFrontActivityScreenCompatMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(frontActivityScreenCompatMode);
                    return true;
                case 19:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFrontActivityScreenCompatMode(readInt33);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedTask(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTask = removeTask(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTask);
                    return true;
                case 22:
                    removeAllVisibleRecentTasks();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt36 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityManager.RunningTaskInfo> tasks = getTasks(readInt36, readBoolean2, readBoolean3, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 24:
                    android.app.IApplicationThread asInterface13 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString33 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    android.os.Bundle bundle12 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    moveTaskToFront(asInterface13, readString33, readInt38, readInt39, bundle12);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> recentTasks = getRecentTasks(readInt40, readInt41, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentTasks, 1);
                    return true;
                case 26:
                    boolean isTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopActivityImmersive);
                    return true;
                case 27:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.ActivityManager.TaskDescription taskDescription = getTaskDescription(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskDescription, 1);
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.os.Bundle bundle13 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.app.assist.AssistStructure assistStructure = (android.app.assist.AssistStructure) parcel.readTypedObject(android.app.assist.AssistStructure.CREATOR);
                    android.app.assist.AssistContent assistContent = (android.app.assist.AssistContent) parcel.readTypedObject(android.app.assist.AssistContent.CREATOR);
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportAssistContextExtras(readStrongBinder11, bundle13, assistStructure, assistContent, uri);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(readInt44);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.app.ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = getFocusedRootTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(focusedRootTaskInfo, 1);
                    return true;
                case 31:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Rect taskBounds = getTaskBounds(readInt45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 32:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    focusTopTask(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelRecentsAnimation(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt47 = parcel.readInt();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(readInt47, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean isInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInLockTaskMode);
                    return true;
                case 36:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 37:
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.IBinder> appTasks = getAppTasks(readString34);
                    parcel2.writeNoException();
                    parcel2.writeBinderList(appTasks);
                    return true;
                case 38:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(readInt48);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    stopSystemLockTaskMode();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.service.voice.IVoiceInteractionSession asInterface14 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishVoiceTask(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.content.Intent intent13 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    android.app.ActivityManager.TaskDescription taskDescription2 = (android.app.ActivityManager.TaskDescription) parcel.readTypedObject(android.app.ActivityManager.TaskDescription.CREATOR);
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addAppTask = addAppTask(readStrongBinder12, intent13, taskDescription2, bitmap);
                    parcel2.writeNoException();
                    parcel2.writeInt(addAppTask);
                    return true;
                case 42:
                    android.graphics.Point appTaskThumbnailSize = getAppTaskThumbnailSize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appTaskThumbnailSize, 1);
                    return true;
                case 43:
                    android.app.IApplicationThread asInterface15 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseSomeActivities(asInterface15);
                    return true;
                case 44:
                    java.lang.String readString35 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Bitmap taskDescriptionIcon = getTaskDescriptionIcon(readString35, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskDescriptionIcon, 1);
                    return true;
                case 45:
                    android.app.ITaskStackListener asInterface16 = android.app.ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.app.ITaskStackListener asInterface17 = android.app.ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(readInt50, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt52 = parcel.readInt();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(readInt52, rect, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveRootTaskToDisplay(readInt54, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(readInt56, readInt57, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksInWindowingModes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksWithActivityTypes(createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 54:
                    int readInt58 = parcel.readInt();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo = getRootTaskInfo(readInt58, readInt59);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfo, 1);
                    return true;
                case 55:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.ActivityTaskManager.RootTaskInfo> allRootTaskInfosOnDisplay = getAllRootTaskInfosOnDisplay(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfosOnDisplay, 1);
                    return true;
                case 56:
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.ActivityTaskManager.RootTaskInfo rootTaskInfoOnDisplay = getRootTaskInfoOnDisplay(readInt61, readInt62, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfoOnDisplay, 1);
                    return true;
                case 57:
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockScreenShown(readBoolean6, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle assistContextExtras = getAssistContextExtras(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(assistContextExtras, 1);
                    return true;
                case 59:
                    int readInt65 = parcel.readInt();
                    android.app.IAssistDataReceiver asInterface18 = android.app.IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle14 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistContextExtras = requestAssistContextExtras(readInt65, asInterface18, bundle14, readStrongBinder13, readBoolean8, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistContextExtras);
                    return true;
                case 60:
                    android.app.IAssistDataReceiver asInterface19 = android.app.IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    android.os.Bundle bundle15 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestAutofillData = requestAutofillData(asInterface19, bundle15, readStrongBinder14, readInt66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAutofillData);
                    return true;
                case 61:
                    boolean isAssistDataAllowed = isAssistDataAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAssistDataAllowed);
                    return true;
                case 62:
                    android.app.IAssistDataReceiver asInterface20 = android.app.IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt67 = parcel.readInt();
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistDataForTask = requestAssistDataForTask(asInterface20, readInt67, readString36, readString37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistDataForTask);
                    return true;
                case 63:
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    keyguardGoingAway(readInt68);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    android.window.IWindowOrganizerController windowOrganizerController = getWindowOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowOrganizerController);
                    return true;
                case 66:
                    boolean supportsLocalVoiceInteraction = supportsLocalVoiceInteraction();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsLocalVoiceInteraction);
                    return true;
                case 67:
                    android.content.pm.ConfigurationInfo deviceConfigurationInfo = getDeviceConfigurationInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceConfigurationInfo, 1);
                    return true;
                case 68:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(readInt69);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt70 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.window.TaskSnapshot taskSnapshot = getTaskSnapshot(readInt70, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshot, 1);
                    return true;
                case 70:
                    int readInt71 = parcel.readInt();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.window.TaskSnapshot takeTaskSnapshot = takeTaskSnapshot(readInt71, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeTaskSnapshot, 1);
                    return true;
                case 71:
                    int lastResumedActivityUserId = getLastResumedActivityUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastResumedActivityUserId);
                    return true;
                case 72:
                    int lastResumedActivityUid = getLastResumedActivityUid();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastResumedActivityUid);
                    return true;
                case 73:
                    android.content.res.Configuration configuration2 = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 74:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateLockTaskFeatures(readInt72, readInt73);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    java.lang.String readString38 = parcel.readString();
                    android.view.RemoteAnimationAdapter remoteAnimationAdapter = (android.view.RemoteAnimationAdapter) parcel.readTypedObject(android.view.RemoteAnimationAdapter.CREATOR);
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationForNextActivityStart(readString38, remoteAnimationAdapter, readStrongBinder15);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt74 = parcel.readInt();
                    android.view.RemoteAnimationDefinition remoteAnimationDefinition = (android.view.RemoteAnimationDefinition) parcel.readTypedObject(android.view.RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationsForDisplay(readInt74, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    alwaysShowUnsupportedCompileSdkWarning(componentName);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVrThread(readInt75);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(readInt76);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    stopAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 81:
                    resumeAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 82:
                    android.app.IActivityController asInterface21 = android.app.IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(asInterface21, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    android.service.voice.IVoiceInteractionSession asInterface22 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoiceKeepAwake(asInterface22, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageScreenCompatMode = getPackageScreenCompatMode(readString39);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageScreenCompatMode);
                    return true;
                case 85:
                    java.lang.String readString40 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(readString40, readInt77);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean packageAskScreenCompat = getPackageAskScreenCompat(readString41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageAskScreenCompat);
                    return true;
                case 87:
                    java.lang.String readString42 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPackageAskScreenCompat(readString42, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    clearLaunchParamsForPackages(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt78 = parcel.readInt();
                    android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable = (android.window.SplashScreenView.SplashScreenViewParcelable) parcel.readTypedObject(android.window.SplashScreenView.SplashScreenViewParcelable.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSplashScreenViewCopyFinished(readInt78, splashScreenViewParcelable);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    android.app.PictureInPictureUiState pictureInPictureUiState = (android.app.PictureInPictureUiState) parcel.readTypedObject(android.app.PictureInPictureUiState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPictureInPictureUiStateChanged(pictureInPictureUiState);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachNavigationBarFromApp(readStrongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    android.app.IApplicationThread asInterface23 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRunningRemoteTransitionDelegate(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    android.window.BackAnimationAdapter backAnimationAdapter = (android.window.BackAnimationAdapter) parcel.readTypedObject(android.window.BackAnimationAdapter.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.window.BackNavigationInfo startBackNavigation = startBackNavigation(remoteCallback, backAnimationAdapter);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startBackNavigation, 1);
                    return true;
                case 94:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    android.app.IScreenCaptureObserver asInterface24 = android.app.IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerScreenCaptureObserver(readStrongBinder17, asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    android.app.IScreenCaptureObserver asInterface25 = android.app.IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenCaptureObserver(readStrongBinder18, asInterface25);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IActivityTaskManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IActivityTaskManager.DESCRIPTOR;
            }

            @Override // android.app.IActivityTaskManager
            public int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
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
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivities(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
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
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean startNextMatchingActivity(android.os.IBinder iBinder, android.content.Intent intent, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder2, java.lang.String str2, int i, int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.WaitResult startActivityAndWait(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
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
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.WaitResult) obtain2.readTypedObject(android.app.WaitResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityWithConfig(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.content.res.Configuration configuration, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startVoiceActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startAssistantActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromGameSession(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startRecentsActivity(android.content.Intent intent, long j, android.view.IRecentsAnimationRunner iRecentsAnimationRunner) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iRecentsAnimationRunner);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromRecents(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsCaller(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, boolean z, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
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
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isActivityStartAllowedOnDisplay(int i, android.content.Intent intent, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unhandledBack() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.IActivityClientController getActivityClientController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.app.IActivityClientController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getFrontActivityScreenCompatMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFrontActivityScreenCompatMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean removeTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllVisibleRecentTasks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isTopActivityImmersive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.ActivityManager.TaskDescription getTaskDescription(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityManager.TaskDescription) obtain2.readTypedObject(android.app.ActivityManager.TaskDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void reportAssistContextExtras(android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(assistStructure, 0);
                    obtain.writeTypedObject(assistContent, 0);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedRootTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.graphics.Rect getTaskBounds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Rect) obtain2.readTypedObject(android.graphics.Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void focusTopTask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void cancelRecentsAnimation(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskPackages(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isInLockTaskMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLockTaskModeState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public java.util.List<android.os.IBinder> getAppTasks(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBinderArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startSystemLockTaskMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopSystemLockTaskMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void finishVoiceTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int addAppTask(android.os.IBinder iBinder, android.content.Intent intent, android.app.ActivityManager.TaskDescription taskDescription, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(taskDescription, 0);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.graphics.Point getAppTaskThumbnailSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Point) obtain2.readTypedObject(android.graphics.Point.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void releaseSomeActivities(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Bitmap) obtain2.readTypedObject(android.graphics.Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setTaskResizeable(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resizeTask(int i, android.graphics.Rect rect, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveRootTaskToDisplay(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksInWindowingModes(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksWithActivityTypes(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(android.app.ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setLockScreenShown(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.os.Bundle getAssistContextExtras(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistContextExtras(int i, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAutofillData(android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAssistDataAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistDataForTask(android.app.IAssistDataReceiver iAssistDataReceiver, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void keyguardGoingAway(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.window.IWindowOrganizerController getWindowOrganizerController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.window.IWindowOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean supportsLocalVoiceInteraction() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.content.pm.ConfigurationInfo getDeviceConfigurationInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ConfigurationInfo) obtain2.readTypedObject(android.content.pm.ConfigurationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void cancelTaskWindowTransition(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.window.TaskSnapshot getTaskSnapshot(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.TaskSnapshot) obtain2.readTypedObject(android.window.TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.window.TaskSnapshot takeTaskSnapshot(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.TaskSnapshot) obtain2.readTypedObject(android.window.TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLastResumedActivityUserId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLastResumedActivityUid() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean updateConfiguration(android.content.res.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskFeatures(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationForNextActivityStart(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationsForDisplay(int i, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVrThread(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPersistentVrThread(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopAppSwitches() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resumeAppSwitches() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setActivityController(android.app.IActivityController iActivityController, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityController);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVoiceKeepAwake(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getPackageScreenCompatMode(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageScreenCompatMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean getPackageAskScreenCompat(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageAskScreenCompat(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void clearLaunchParamsForPackages(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onSplashScreenViewCopyFinished(int i, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(splashScreenViewParcelable, 0);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState pictureInPictureUiState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(pictureInPictureUiState, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void detachNavigationBarFromApp(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setRunningRemoteTransitionDelegate(android.app.IApplicationThread iApplicationThread) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public android.window.BackNavigationInfo startBackNavigation(android.os.RemoteCallback remoteCallback, android.window.BackAnimationAdapter backAnimationAdapter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(backAnimationAdapter, 0);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.BackNavigationInfo) obtain2.readTypedObject(android.window.BackNavigationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 94;
        }
    }
}
