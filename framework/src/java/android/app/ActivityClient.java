package android.app;

/* loaded from: classes.dex */
public class ActivityClient {
    private static final android.util.Singleton<android.app.ActivityClient> sInstance = new android.util.Singleton<android.app.ActivityClient>() { // from class: android.app.ActivityClient.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.ActivityClient create() {
            return new android.app.ActivityClient();
        }
    };
    private static final android.app.ActivityClient.ActivityClientControllerSingleton INTERFACE_SINGLETON = new android.app.ActivityClient.ActivityClientControllerSingleton();

    private ActivityClient() {
    }

    public void activityIdle(android.os.IBinder iBinder, android.content.res.Configuration configuration, boolean z) {
        try {
            getActivityClientController().activityIdle(iBinder, configuration, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityResumed(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().activityResumed(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityRefreshed(android.os.IBinder iBinder) {
        try {
            getActivityClientController().activityRefreshed(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityTopResumedStateLost() {
        try {
            getActivityClientController().activityTopResumedStateLost();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityPaused(android.os.IBinder iBinder) {
        try {
            getActivityClientController().activityPaused(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityStopped(android.os.IBinder iBinder, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) {
        try {
            getActivityClientController().activityStopped(iBinder, bundle, persistableBundle, charSequence);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityDestroyed(android.os.IBinder iBinder) {
        try {
            getActivityClientController().activityDestroyed(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityLocalRelaunch(android.os.IBinder iBinder) {
        try {
            getActivityClientController().activityLocalRelaunch(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityRelaunched(android.os.IBinder iBinder) {
        try {
            getActivityClientController().activityRelaunched(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void reportSizeConfigurations(android.os.IBinder iBinder, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) {
        try {
            getActivityClientController().reportSizeConfigurations(iBinder, sizeConfigurationBuckets);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) {
        try {
            return getActivityClientController().moveActivityTaskToBack(iBinder, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean shouldUpRecreateTask(android.os.IBinder iBinder, java.lang.String str) {
        try {
            return getActivityClientController().shouldUpRecreateTask(iBinder, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean navigateUpTo(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, int i, android.content.Intent intent2) {
        try {
            return getActivityClientController().navigateUpTo(iBinder, intent, str, i, intent2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean releaseActivityInstance(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().releaseActivityInstance(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) {
        try {
            return getActivityClientController().finishActivity(iBinder, i, intent, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean finishActivityAffinity(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().finishActivityAffinity(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void finishSubActivity(android.os.IBinder iBinder, java.lang.String str, int i) {
        try {
            getActivityClientController().finishSubActivity(iBinder, str, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setForceSendResultForMediaProjection(android.os.IBinder iBinder) {
        try {
            getActivityClientController().setForceSendResultForMediaProjection(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isTopOfTask(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().isTopOfTask(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean willActivityBeVisible(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().willActivityBeVisible(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDisplayId(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getDisplayId(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTaskForActivity(android.os.IBinder iBinder, boolean z) {
        try {
            return getActivityClientController().getTaskForActivity(iBinder, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.res.Configuration getTaskConfiguration(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getTaskConfiguration(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.IBinder getActivityTokenBelow(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getActivityTokenBelow(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.content.ComponentName getCallingActivity(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getCallingActivity(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    java.lang.String getCallingPackage(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getCallingPackage(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLaunchedFromUid(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getLaunchedFromUid(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getLaunchedFromPackage(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getActivityCallerUid(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        try {
            return getActivityClientController().getActivityCallerUid(iBinder, iBinder2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getActivityCallerPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        try {
            return getActivityClientController().getActivityCallerPackage(iBinder, iBinder2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkActivityCallerContentUriPermission(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.net.Uri uri, int i) {
        try {
            return getActivityClientController().checkActivityCallerContentUriPermission(iBinder, iBinder2, android.content.ContentProvider.getUriWithoutUserId(uri), i, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getCallingUserId()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRequestedOrientation(android.os.IBinder iBinder, int i) {
        try {
            getActivityClientController().setRequestedOrientation(iBinder, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    int getRequestedOrientation(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().getRequestedOrientation(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean convertFromTranslucent(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().convertFromTranslucent(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean convertToTranslucent(android.os.IBinder iBinder, android.os.Bundle bundle) {
        try {
            return getActivityClientController().convertToTranslucent(iBinder, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void reportActivityFullyDrawn(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().reportActivityFullyDrawn(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean isImmersive(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().isImmersive(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setImmersive(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setImmersive(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean enterPictureInPictureMode(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) {
        try {
            return getActivityClientController().enterPictureInPictureMode(iBinder, pictureInPictureParams);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setPictureInPictureParams(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) {
        try {
            getActivityClientController().setPictureInPictureParams(iBinder, pictureInPictureParams);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setShouldDockBigOverlays(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setShouldDockBigOverlays(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void toggleFreeformWindowingMode(android.os.IBinder iBinder) {
        try {
            getActivityClientController().toggleFreeformWindowingMode(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void requestMultiwindowFullscreen(android.os.IBinder iBinder, int i, android.os.IRemoteCallback iRemoteCallback) {
        try {
            getActivityClientController().requestMultiwindowFullscreen(iBinder, i, iRemoteCallback);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void startLockTaskModeByToken(android.os.IBinder iBinder) {
        try {
            getActivityClientController().startLockTaskModeByToken(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void stopLockTaskModeByToken(android.os.IBinder iBinder) {
        try {
            getActivityClientController().stopLockTaskModeByToken(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void showLockTaskEscapeMessage(android.os.IBinder iBinder) {
        try {
            getActivityClientController().showLockTaskEscapeMessage(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setTaskDescription(android.os.IBinder iBinder, android.app.ActivityManager.TaskDescription taskDescription) {
        try {
            getActivityClientController().setTaskDescription(iBinder, taskDescription);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean showAssistFromActivity(android.os.IBinder iBinder, android.os.Bundle bundle) {
        try {
            return getActivityClientController().showAssistFromActivity(iBinder, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isRootVoiceInteraction(android.os.IBinder iBinder) {
        try {
            return getActivityClientController().isRootVoiceInteraction(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void startLocalVoiceInteraction(android.os.IBinder iBinder, android.os.Bundle bundle) {
        try {
            getActivityClientController().startLocalVoiceInteraction(iBinder, bundle);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void stopLocalVoiceInteraction(android.os.IBinder iBinder) {
        try {
            getActivityClientController().stopLocalVoiceInteraction(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setShowWhenLocked(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setShowWhenLocked(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setInheritShowWhenLocked(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setInheritShowWhenLocked(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setTurnScreenOn(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setTurnScreenOn(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setAllowCrossUidActivitySwitchFromBelow(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setAllowCrossUidActivitySwitchFromBelow(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    int setVrMode(android.os.IBinder iBinder, boolean z, android.content.ComponentName componentName) {
        try {
            return getActivityClientController().setVrMode(iBinder, z, componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void overrideActivityTransition(android.os.IBinder iBinder, boolean z, int i, int i2, int i3) {
        try {
            getActivityClientController().overrideActivityTransition(iBinder, z, i, i2, i3);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void clearOverrideActivityTransition(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().clearOverrideActivityTransition(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void overridePendingTransition(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) {
        try {
            getActivityClientController().overridePendingTransition(iBinder, str, i, i2, i3);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setRecentsScreenshotEnabled(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setRecentsScreenshotEnabled(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void invalidateHomeTaskSnapshot(android.os.IBinder iBinder) {
        try {
            getActivityClientController().invalidateHomeTaskSnapshot(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        try {
            getActivityClientController().dismissKeyguard(iBinder, iKeyguardDismissCallback, charSequence);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void registerRemoteAnimations(android.os.IBinder iBinder, android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getActivityClientController().registerRemoteAnimations(iBinder, remoteAnimationDefinition);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void unregisterRemoteAnimations(android.os.IBinder iBinder) {
        try {
            getActivityClientController().unregisterRemoteAnimations(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void onBackPressed(android.os.IBinder iBinder, android.app.IRequestFinishCallback iRequestFinishCallback) {
        try {
            getActivityClientController().onBackPressed(iBinder, iRequestFinishCallback);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void reportSplashScreenAttached(android.os.IBinder iBinder) {
        try {
            getActivityClientController().splashScreenAttached(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void enableTaskLocaleOverride(android.os.IBinder iBinder) {
        try {
            getActivityClientController().enableTaskLocaleOverride(iBinder);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestedToLaunchInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        try {
            return getActivityClientController().isRequestedToLaunchInTaskFragment(iBinder, iBinder2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setActivityRecordInputSinkEnabled(android.os.IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setActivityRecordInputSinkEnabled(iBinder, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void requestCompatCameraControl(android.content.res.Resources resources, android.os.IBinder iBinder, boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) {
        if (!resources.getBoolean(com.android.internal.R.bool.config_isCameraCompatControlForStretchedIssuesEnabled)) {
            return;
        }
        try {
            getActivityClientController().requestCompatCameraControl(iBinder, z, z2, iCompatCameraControlCallback);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static android.app.ActivityClient getInstance() {
        return sInstance.get();
    }

    public static android.app.IActivityClientController setActivityClientController(android.app.IActivityClientController iActivityClientController) {
        INTERFACE_SINGLETON.mKnownInstance = iActivityClientController;
        return iActivityClientController;
    }

    private static android.app.IActivityClientController getActivityClientController() {
        android.app.IActivityClientController iActivityClientController = INTERFACE_SINGLETON.mKnownInstance;
        return iActivityClientController != null ? iActivityClientController : INTERFACE_SINGLETON.get();
    }

    private static class ActivityClientControllerSingleton extends android.util.Singleton<android.app.IActivityClientController> {
        android.app.IActivityClientController mKnownInstance;

        private ActivityClientControllerSingleton() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.IActivityClientController create() {
            try {
                return android.app.ActivityTaskManager.getService().getActivityClientController();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
