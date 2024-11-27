package android.app;

/* loaded from: classes.dex */
public interface IActivityClientController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IActivityClientController";

    void activityDestroyed(android.os.IBinder iBinder) throws android.os.RemoteException;

    void activityIdle(android.os.IBinder iBinder, android.content.res.Configuration configuration, boolean z) throws android.os.RemoteException;

    void activityLocalRelaunch(android.os.IBinder iBinder) throws android.os.RemoteException;

    void activityPaused(android.os.IBinder iBinder) throws android.os.RemoteException;

    void activityRefreshed(android.os.IBinder iBinder) throws android.os.RemoteException;

    void activityRelaunched(android.os.IBinder iBinder) throws android.os.RemoteException;

    void activityResumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void activityStopped(android.os.IBinder iBinder, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void activityTopResumedStateLost() throws android.os.RemoteException;

    int checkActivityCallerContentUriPermission(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.net.Uri uri, int i, int i2) throws android.os.RemoteException;

    void clearOverrideActivityTransition(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    boolean convertFromTranslucent(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean convertToTranslucent(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void enableTaskLocaleOverride(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean enterPictureInPictureMode(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException;

    boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException;

    boolean finishActivityAffinity(android.os.IBinder iBinder) throws android.os.RemoteException;

    void finishSubActivity(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getActivityCallerPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    int getActivityCallerUid(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    android.os.IBinder getActivityTokenBelow(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.content.ComponentName getCallingActivity(android.os.IBinder iBinder) throws android.os.RemoteException;

    java.lang.String getCallingPackage(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getDisplayId(android.os.IBinder iBinder) throws android.os.RemoteException;

    java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getRequestedOrientation(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.content.res.Configuration getTaskConfiguration(android.os.IBinder iBinder) throws android.os.RemoteException;

    int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void invalidateHomeTaskSnapshot(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isImmersive(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isRequestedToLaunchInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    boolean isRootVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    boolean navigateUpTo(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, int i, android.content.Intent intent2) throws android.os.RemoteException;

    void onBackPressed(android.os.IBinder iBinder, android.app.IRequestFinishCallback iRequestFinishCallback) throws android.os.RemoteException;

    void overrideActivityTransition(android.os.IBinder iBinder, boolean z, int i, int i2, int i3) throws android.os.RemoteException;

    void overridePendingTransition(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void registerRemoteAnimations(android.os.IBinder iBinder, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException;

    boolean releaseActivityInstance(android.os.IBinder iBinder) throws android.os.RemoteException;

    void reportActivityFullyDrawn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void reportSizeConfigurations(android.os.IBinder iBinder, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) throws android.os.RemoteException;

    void requestCompatCameraControl(android.os.IBinder iBinder, boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) throws android.os.RemoteException;

    void requestMultiwindowFullscreen(android.os.IBinder iBinder, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException;

    void setActivityRecordInputSinkEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setAllowCrossUidActivitySwitchFromBelow(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setForceSendResultForMediaProjection(android.os.IBinder iBinder) throws android.os.RemoteException;

    void setImmersive(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setInheritShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setPictureInPictureParams(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException;

    void setRecentsScreenshotEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setShouldDockBigOverlays(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setTaskDescription(android.os.IBinder iBinder, android.app.ActivityManager.TaskDescription taskDescription) throws android.os.RemoteException;

    void setTurnScreenOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    int setVrMode(android.os.IBinder iBinder, boolean z, android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean shouldUpRecreateTask(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    boolean showAssistFromActivity(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    void showLockTaskEscapeMessage(android.os.IBinder iBinder) throws android.os.RemoteException;

    void splashScreenAttached(android.os.IBinder iBinder) throws android.os.RemoteException;

    void startLocalVoiceInteraction(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException;

    void startLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException;

    void stopLocalVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException;

    void stopLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException;

    void toggleFreeformWindowingMode(android.os.IBinder iBinder) throws android.os.RemoteException;

    void unregisterRemoteAnimations(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean willActivityBeVisible(android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.app.IActivityClientController {
        @Override // android.app.IActivityClientController
        public void activityIdle(android.os.IBinder iBinder, android.content.res.Configuration configuration, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityResumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityRefreshed(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityTopResumedStateLost() throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityPaused(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityStopped(android.os.IBinder iBinder, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityDestroyed(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityLocalRelaunch(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityRelaunched(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void reportSizeConfigurations(android.os.IBinder iBinder, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean shouldUpRecreateTask(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean navigateUpTo(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, int i, android.content.Intent intent2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean releaseActivityInstance(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivityAffinity(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void finishSubActivity(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setForceSendResultForMediaProjection(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean willActivityBeVisible(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public int getDisplayId(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public android.content.res.Configuration getTaskConfiguration(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public android.os.IBinder getActivityTokenBelow(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public android.content.ComponentName getCallingActivity(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public java.lang.String getCallingPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public int getActivityCallerUid(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public java.lang.String getActivityCallerPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int checkActivityCallerContentUriPermission(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public int getRequestedOrientation(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public boolean convertFromTranslucent(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean convertToTranslucent(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isImmersive(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setImmersive(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean enterPictureInPictureMode(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setPictureInPictureParams(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setShouldDockBigOverlays(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void toggleFreeformWindowingMode(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void requestMultiwindowFullscreen(android.os.IBinder iBinder, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void startLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void stopLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void showLockTaskEscapeMessage(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setTaskDescription(android.os.IBinder iBinder, android.app.ActivityManager.TaskDescription taskDescription) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean showAssistFromActivity(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isRootVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void startLocalVoiceInteraction(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void stopLocalVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setInheritShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setTurnScreenOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setAllowCrossUidActivitySwitchFromBelow(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void reportActivityFullyDrawn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void overrideActivityTransition(android.os.IBinder iBinder, boolean z, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void clearOverrideActivityTransition(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void overridePendingTransition(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public int setVrMode(android.os.IBinder iBinder, boolean z, android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void setRecentsScreenshotEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void invalidateHomeTaskSnapshot(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void registerRemoteAnimations(android.os.IBinder iBinder, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void unregisterRemoteAnimations(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void onBackPressed(android.os.IBinder iBinder, android.app.IRequestFinishCallback iRequestFinishCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void splashScreenAttached(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void requestCompatCameraControl(android.os.IBinder iBinder, boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void enableTaskLocaleOverride(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean isRequestedToLaunchInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setActivityRecordInputSinkEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IActivityClientController {
        static final int TRANSACTION_activityDestroyed = 7;
        static final int TRANSACTION_activityIdle = 1;
        static final int TRANSACTION_activityLocalRelaunch = 8;
        static final int TRANSACTION_activityPaused = 5;
        static final int TRANSACTION_activityRefreshed = 3;
        static final int TRANSACTION_activityRelaunched = 9;
        static final int TRANSACTION_activityResumed = 2;
        static final int TRANSACTION_activityStopped = 6;
        static final int TRANSACTION_activityTopResumedStateLost = 4;
        static final int TRANSACTION_checkActivityCallerContentUriPermission = 31;
        static final int TRANSACTION_clearOverrideActivityTransition = 57;
        static final int TRANSACTION_convertFromTranslucent = 34;
        static final int TRANSACTION_convertToTranslucent = 35;
        static final int TRANSACTION_dismissKeyguard = 62;
        static final int TRANSACTION_enableTaskLocaleOverride = 68;
        static final int TRANSACTION_enterPictureInPictureMode = 38;
        static final int TRANSACTION_finishActivity = 15;
        static final int TRANSACTION_finishActivityAffinity = 16;
        static final int TRANSACTION_finishSubActivity = 17;
        static final int TRANSACTION_getActivityCallerPackage = 30;
        static final int TRANSACTION_getActivityCallerUid = 28;
        static final int TRANSACTION_getActivityTokenBelow = 24;
        static final int TRANSACTION_getCallingActivity = 25;
        static final int TRANSACTION_getCallingPackage = 26;
        static final int TRANSACTION_getDisplayId = 21;
        static final int TRANSACTION_getLaunchedFromPackage = 29;
        static final int TRANSACTION_getLaunchedFromUid = 27;
        static final int TRANSACTION_getRequestedOrientation = 33;
        static final int TRANSACTION_getTaskConfiguration = 23;
        static final int TRANSACTION_getTaskForActivity = 22;
        static final int TRANSACTION_invalidateHomeTaskSnapshot = 61;
        static final int TRANSACTION_isImmersive = 36;
        static final int TRANSACTION_isRequestedToLaunchInTaskFragment = 69;
        static final int TRANSACTION_isRootVoiceInteraction = 48;
        static final int TRANSACTION_isTopOfTask = 19;
        static final int TRANSACTION_moveActivityTaskToBack = 11;
        static final int TRANSACTION_navigateUpTo = 13;
        static final int TRANSACTION_onBackPressed = 65;
        static final int TRANSACTION_overrideActivityTransition = 56;
        static final int TRANSACTION_overridePendingTransition = 58;
        static final int TRANSACTION_registerRemoteAnimations = 63;
        static final int TRANSACTION_releaseActivityInstance = 14;
        static final int TRANSACTION_reportActivityFullyDrawn = 55;
        static final int TRANSACTION_reportSizeConfigurations = 10;
        static final int TRANSACTION_requestCompatCameraControl = 67;
        static final int TRANSACTION_requestMultiwindowFullscreen = 42;
        static final int TRANSACTION_setActivityRecordInputSinkEnabled = 70;
        static final int TRANSACTION_setAllowCrossUidActivitySwitchFromBelow = 54;
        static final int TRANSACTION_setForceSendResultForMediaProjection = 18;
        static final int TRANSACTION_setImmersive = 37;
        static final int TRANSACTION_setInheritShowWhenLocked = 52;
        static final int TRANSACTION_setPictureInPictureParams = 39;
        static final int TRANSACTION_setRecentsScreenshotEnabled = 60;
        static final int TRANSACTION_setRequestedOrientation = 32;
        static final int TRANSACTION_setShouldDockBigOverlays = 40;
        static final int TRANSACTION_setShowWhenLocked = 51;
        static final int TRANSACTION_setTaskDescription = 46;
        static final int TRANSACTION_setTurnScreenOn = 53;
        static final int TRANSACTION_setVrMode = 59;
        static final int TRANSACTION_shouldUpRecreateTask = 12;
        static final int TRANSACTION_showAssistFromActivity = 47;
        static final int TRANSACTION_showLockTaskEscapeMessage = 45;
        static final int TRANSACTION_splashScreenAttached = 66;
        static final int TRANSACTION_startLocalVoiceInteraction = 49;
        static final int TRANSACTION_startLockTaskModeByToken = 43;
        static final int TRANSACTION_stopLocalVoiceInteraction = 50;
        static final int TRANSACTION_stopLockTaskModeByToken = 44;
        static final int TRANSACTION_toggleFreeformWindowingMode = 41;
        static final int TRANSACTION_unregisterRemoteAnimations = 64;
        static final int TRANSACTION_willActivityBeVisible = 20;

        public Stub() {
            attachInterface(this, android.app.IActivityClientController.DESCRIPTOR);
        }

        public static android.app.IActivityClientController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IActivityClientController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IActivityClientController)) {
                return (android.app.IActivityClientController) queryLocalInterface;
            }
            return new android.app.IActivityClientController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "activityIdle";
                case 2:
                    return "activityResumed";
                case 3:
                    return "activityRefreshed";
                case 4:
                    return "activityTopResumedStateLost";
                case 5:
                    return "activityPaused";
                case 6:
                    return "activityStopped";
                case 7:
                    return "activityDestroyed";
                case 8:
                    return "activityLocalRelaunch";
                case 9:
                    return "activityRelaunched";
                case 10:
                    return "reportSizeConfigurations";
                case 11:
                    return "moveActivityTaskToBack";
                case 12:
                    return "shouldUpRecreateTask";
                case 13:
                    return "navigateUpTo";
                case 14:
                    return "releaseActivityInstance";
                case 15:
                    return "finishActivity";
                case 16:
                    return "finishActivityAffinity";
                case 17:
                    return "finishSubActivity";
                case 18:
                    return "setForceSendResultForMediaProjection";
                case 19:
                    return "isTopOfTask";
                case 20:
                    return "willActivityBeVisible";
                case 21:
                    return "getDisplayId";
                case 22:
                    return "getTaskForActivity";
                case 23:
                    return "getTaskConfiguration";
                case 24:
                    return "getActivityTokenBelow";
                case 25:
                    return "getCallingActivity";
                case 26:
                    return "getCallingPackage";
                case 27:
                    return "getLaunchedFromUid";
                case 28:
                    return "getActivityCallerUid";
                case 29:
                    return "getLaunchedFromPackage";
                case 30:
                    return "getActivityCallerPackage";
                case 31:
                    return "checkActivityCallerContentUriPermission";
                case 32:
                    return "setRequestedOrientation";
                case 33:
                    return "getRequestedOrientation";
                case 34:
                    return "convertFromTranslucent";
                case 35:
                    return "convertToTranslucent";
                case 36:
                    return "isImmersive";
                case 37:
                    return "setImmersive";
                case 38:
                    return "enterPictureInPictureMode";
                case 39:
                    return "setPictureInPictureParams";
                case 40:
                    return "setShouldDockBigOverlays";
                case 41:
                    return "toggleFreeformWindowingMode";
                case 42:
                    return "requestMultiwindowFullscreen";
                case 43:
                    return "startLockTaskModeByToken";
                case 44:
                    return "stopLockTaskModeByToken";
                case 45:
                    return "showLockTaskEscapeMessage";
                case 46:
                    return "setTaskDescription";
                case 47:
                    return "showAssistFromActivity";
                case 48:
                    return "isRootVoiceInteraction";
                case 49:
                    return "startLocalVoiceInteraction";
                case 50:
                    return "stopLocalVoiceInteraction";
                case 51:
                    return "setShowWhenLocked";
                case 52:
                    return "setInheritShowWhenLocked";
                case 53:
                    return "setTurnScreenOn";
                case 54:
                    return "setAllowCrossUidActivitySwitchFromBelow";
                case 55:
                    return "reportActivityFullyDrawn";
                case 56:
                    return "overrideActivityTransition";
                case 57:
                    return "clearOverrideActivityTransition";
                case 58:
                    return "overridePendingTransition";
                case 59:
                    return "setVrMode";
                case 60:
                    return "setRecentsScreenshotEnabled";
                case 61:
                    return "invalidateHomeTaskSnapshot";
                case 62:
                    return "dismissKeyguard";
                case 63:
                    return "registerRemoteAnimations";
                case 64:
                    return "unregisterRemoteAnimations";
                case 65:
                    return "onBackPressed";
                case 66:
                    return "splashScreenAttached";
                case 67:
                    return "requestCompatCameraControl";
                case 68:
                    return "enableTaskLocaleOverride";
                case 69:
                    return "isRequestedToLaunchInTaskFragment";
                case 70:
                    return "setActivityRecordInputSinkEnabled";
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
                parcel.enforceInterface(android.app.IActivityClientController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IActivityClientController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.content.res.Configuration configuration = (android.content.res.Configuration) parcel.readTypedObject(android.content.res.Configuration.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityIdle(readStrongBinder, configuration, readBoolean);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityResumed(readStrongBinder2, readBoolean2);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRefreshed(readStrongBinder3);
                    return true;
                case 4:
                    activityTopResumedStateLost();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityPaused(readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    activityStopped(readStrongBinder5, bundle, persistableBundle, charSequence);
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityDestroyed(readStrongBinder6);
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityLocalRelaunch(readStrongBinder7);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRelaunched(readStrongBinder8);
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    android.window.SizeConfigurationBuckets sizeConfigurationBuckets = (android.window.SizeConfigurationBuckets) parcel.readTypedObject(android.window.SizeConfigurationBuckets.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSizeConfigurations(readStrongBinder9, sizeConfigurationBuckets);
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean moveActivityTaskToBack = moveActivityTaskToBack(readStrongBinder10, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveActivityTaskToBack);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldUpRecreateTask = shouldUpRecreateTask(readStrongBinder11, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldUpRecreateTask);
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean navigateUpTo = navigateUpTo(readStrongBinder12, intent, readString2, readInt, intent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(navigateUpTo);
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean releaseActivityInstance = releaseActivityInstance(readStrongBinder13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseActivityInstance);
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    android.content.Intent intent3 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean finishActivity = finishActivity(readStrongBinder14, readInt2, intent3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivity);
                    return true;
                case 16:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean finishActivityAffinity = finishActivityAffinity(readStrongBinder15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivityAffinity);
                    return true;
                case 17:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    java.lang.String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSubActivity(readStrongBinder16, readString3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setForceSendResultForMediaProjection(readStrongBinder17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isTopOfTask = isTopOfTask(readStrongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopOfTask);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean willActivityBeVisible = willActivityBeVisible(readStrongBinder19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(willActivityBeVisible);
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int displayId = getDisplayId(readStrongBinder20);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(readStrongBinder21, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder22 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.content.res.Configuration taskConfiguration = getTaskConfiguration(readStrongBinder22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskConfiguration, 1);
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder activityTokenBelow = getActivityTokenBelow(readStrongBinder23);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(activityTokenBelow);
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder24 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName callingActivity = getCallingActivity(readStrongBinder24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callingActivity, 1);
                    return true;
                case 26:
                    android.os.IBinder readStrongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    java.lang.String callingPackage = getCallingPackage(readStrongBinder25);
                    parcel2.writeNoException();
                    parcel2.writeString(callingPackage);
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder26 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(readStrongBinder26);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder27 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder28 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int activityCallerUid = getActivityCallerUid(readStrongBinder27, readStrongBinder28);
                    parcel2.writeNoException();
                    parcel2.writeInt(activityCallerUid);
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder29 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    java.lang.String launchedFromPackage = getLaunchedFromPackage(readStrongBinder29);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 30:
                    android.os.IBinder readStrongBinder30 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder31 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    java.lang.String activityCallerPackage = getActivityCallerPackage(readStrongBinder30, readStrongBinder31);
                    parcel2.writeNoException();
                    parcel2.writeString(activityCallerPackage);
                    return true;
                case 31:
                    android.os.IBinder readStrongBinder32 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder33 = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkActivityCallerContentUriPermission = checkActivityCallerContentUriPermission(readStrongBinder32, readStrongBinder33, uri, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkActivityCallerContentUriPermission);
                    return true;
                case 32:
                    android.os.IBinder readStrongBinder34 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(readStrongBinder34, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.os.IBinder readStrongBinder35 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int requestedOrientation = getRequestedOrientation(readStrongBinder35);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestedOrientation);
                    return true;
                case 34:
                    android.os.IBinder readStrongBinder36 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean convertFromTranslucent = convertFromTranslucent(readStrongBinder36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(convertFromTranslucent);
                    return true;
                case 35:
                    android.os.IBinder readStrongBinder37 = parcel.readStrongBinder();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean convertToTranslucent = convertToTranslucent(readStrongBinder37, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(convertToTranslucent);
                    return true;
                case 36:
                    android.os.IBinder readStrongBinder38 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isImmersive = isImmersive(readStrongBinder38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImmersive);
                    return true;
                case 37:
                    android.os.IBinder readStrongBinder39 = parcel.readStrongBinder();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImmersive(readStrongBinder39, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.os.IBinder readStrongBinder40 = parcel.readStrongBinder();
                    android.app.PictureInPictureParams pictureInPictureParams = (android.app.PictureInPictureParams) parcel.readTypedObject(android.app.PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enterPictureInPictureMode = enterPictureInPictureMode(readStrongBinder40, pictureInPictureParams);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enterPictureInPictureMode);
                    return true;
                case 39:
                    android.os.IBinder readStrongBinder41 = parcel.readStrongBinder();
                    android.app.PictureInPictureParams pictureInPictureParams2 = (android.app.PictureInPictureParams) parcel.readTypedObject(android.app.PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPictureInPictureParams(readStrongBinder41, pictureInPictureParams2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.os.IBinder readStrongBinder42 = parcel.readStrongBinder();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldDockBigOverlays(readStrongBinder42, readBoolean6);
                    return true;
                case 41:
                    android.os.IBinder readStrongBinder43 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    toggleFreeformWindowingMode(readStrongBinder43);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.os.IBinder readStrongBinder44 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    android.os.IRemoteCallback asInterface = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestMultiwindowFullscreen(readStrongBinder44, readInt8, asInterface);
                    return true;
                case 43:
                    android.os.IBinder readStrongBinder45 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startLockTaskModeByToken(readStrongBinder45);
                    return true;
                case 44:
                    android.os.IBinder readStrongBinder46 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLockTaskModeByToken(readStrongBinder46);
                    return true;
                case 45:
                    android.os.IBinder readStrongBinder47 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    showLockTaskEscapeMessage(readStrongBinder47);
                    return true;
                case 46:
                    android.os.IBinder readStrongBinder48 = parcel.readStrongBinder();
                    android.app.ActivityManager.TaskDescription taskDescription = (android.app.ActivityManager.TaskDescription) parcel.readTypedObject(android.app.ActivityManager.TaskDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTaskDescription(readStrongBinder48, taskDescription);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.os.IBinder readStrongBinder49 = parcel.readStrongBinder();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean showAssistFromActivity = showAssistFromActivity(readStrongBinder49, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showAssistFromActivity);
                    return true;
                case 48:
                    android.os.IBinder readStrongBinder50 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isRootVoiceInteraction = isRootVoiceInteraction(readStrongBinder50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRootVoiceInteraction);
                    return true;
                case 49:
                    android.os.IBinder readStrongBinder51 = parcel.readStrongBinder();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLocalVoiceInteraction(readStrongBinder51, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    android.os.IBinder readStrongBinder52 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLocalVoiceInteraction(readStrongBinder52);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.os.IBinder readStrongBinder53 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowWhenLocked(readStrongBinder53, readBoolean7);
                    return true;
                case 52:
                    android.os.IBinder readStrongBinder54 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInheritShowWhenLocked(readStrongBinder54, readBoolean8);
                    return true;
                case 53:
                    android.os.IBinder readStrongBinder55 = parcel.readStrongBinder();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTurnScreenOn(readStrongBinder55, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    android.os.IBinder readStrongBinder56 = parcel.readStrongBinder();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowCrossUidActivitySwitchFromBelow(readStrongBinder56, readBoolean10);
                    return true;
                case 55:
                    android.os.IBinder readStrongBinder57 = parcel.readStrongBinder();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportActivityFullyDrawn(readStrongBinder57, readBoolean11);
                    return true;
                case 56:
                    android.os.IBinder readStrongBinder58 = parcel.readStrongBinder();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideActivityTransition(readStrongBinder58, readBoolean12, readInt9, readInt10, readInt11);
                    return true;
                case 57:
                    android.os.IBinder readStrongBinder59 = parcel.readStrongBinder();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearOverrideActivityTransition(readStrongBinder59, readBoolean13);
                    return true;
                case 58:
                    android.os.IBinder readStrongBinder60 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingTransition(readStrongBinder60, readString4, readInt12, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    android.os.IBinder readStrongBinder61 = parcel.readStrongBinder();
                    boolean readBoolean14 = parcel.readBoolean();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int vrMode = setVrMode(readStrongBinder61, readBoolean14, componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(vrMode);
                    return true;
                case 60:
                    android.os.IBinder readStrongBinder62 = parcel.readStrongBinder();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsScreenshotEnabled(readStrongBinder62, readBoolean15);
                    return true;
                case 61:
                    android.os.IBinder readStrongBinder63 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    invalidateHomeTaskSnapshot(readStrongBinder63);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    android.os.IBinder readStrongBinder64 = parcel.readStrongBinder();
                    com.android.internal.policy.IKeyguardDismissCallback asInterface2 = com.android.internal.policy.IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(readStrongBinder64, asInterface2, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    android.os.IBinder readStrongBinder65 = parcel.readStrongBinder();
                    android.view.RemoteAnimationDefinition remoteAnimationDefinition = (android.view.RemoteAnimationDefinition) parcel.readTypedObject(android.view.RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(readStrongBinder65, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    android.os.IBinder readStrongBinder66 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(readStrongBinder66);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    android.os.IBinder readStrongBinder67 = parcel.readStrongBinder();
                    android.app.IRequestFinishCallback asInterface3 = android.app.IRequestFinishCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onBackPressed(readStrongBinder67, asInterface3);
                    return true;
                case 66:
                    android.os.IBinder readStrongBinder68 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    splashScreenAttached(readStrongBinder68);
                    return true;
                case 67:
                    android.os.IBinder readStrongBinder69 = parcel.readStrongBinder();
                    boolean readBoolean16 = parcel.readBoolean();
                    boolean readBoolean17 = parcel.readBoolean();
                    android.app.ICompatCameraControlCallback asInterface4 = android.app.ICompatCameraControlCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestCompatCameraControl(readStrongBinder69, readBoolean16, readBoolean17, asInterface4);
                    return true;
                case 68:
                    android.os.IBinder readStrongBinder70 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    enableTaskLocaleOverride(readStrongBinder70);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    android.os.IBinder readStrongBinder71 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder72 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isRequestedToLaunchInTaskFragment = isRequestedToLaunchInTaskFragment(readStrongBinder71, readStrongBinder72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestedToLaunchInTaskFragment);
                    return true;
                case 70:
                    android.os.IBinder readStrongBinder73 = parcel.readStrongBinder();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityRecordInputSinkEnabled(readStrongBinder73, readBoolean18);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IActivityClientController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IActivityClientController.DESCRIPTOR;
            }

            @Override // android.app.IActivityClientController
            public void activityIdle(android.os.IBinder iBinder, android.content.res.Configuration configuration, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityResumed(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRefreshed(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityTopResumedStateLost() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityPaused(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityStopped(android.os.IBinder iBinder, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityDestroyed(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityLocalRelaunch(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRelaunched(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportSizeConfigurations(android.os.IBinder iBinder, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(sizeConfigurationBuckets, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean shouldUpRecreateTask(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean navigateUpTo(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, int i, android.content.Intent intent2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent2, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean releaseActivityInstance(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivityAffinity(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void finishSubActivity(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setForceSendResultForMediaProjection(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isTopOfTask(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean willActivityBeVisible(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getDisplayId(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getTaskForActivity(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public android.content.res.Configuration getTaskConfiguration(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.res.Configuration) obtain2.readTypedObject(android.content.res.Configuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public android.os.IBinder getActivityTokenBelow(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public android.content.ComponentName getCallingActivity(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public java.lang.String getCallingPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getLaunchedFromUid(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getActivityCallerUid(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public java.lang.String getActivityCallerPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int checkActivityCallerContentUriPermission(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRequestedOrientation(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getRequestedOrientation(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucent(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertToTranslucent(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isImmersive(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setImmersive(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean enterPictureInPictureMode(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setPictureInPictureParams(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShouldDockBigOverlays(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void toggleFreeformWindowingMode(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void requestMultiwindowFullscreen(android.os.IBinder iBinder, int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLockTaskModeByToken(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void showLockTaskEscapeMessage(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTaskDescription(android.os.IBinder iBinder, android.app.ActivityManager.TaskDescription taskDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(taskDescription, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean showAssistFromActivity(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRootVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLocalVoiceInteraction(android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLocalVoiceInteraction(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setInheritShowWhenLocked(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTurnScreenOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setAllowCrossUidActivitySwitchFromBelow(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportActivityFullyDrawn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overrideActivityTransition(android.os.IBinder iBinder, boolean z, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void clearOverrideActivityTransition(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTransition(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int setVrMode(android.os.IBinder iBinder, boolean z, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRecentsScreenshotEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void invalidateHomeTaskSnapshot(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void registerRemoteAnimations(android.os.IBinder iBinder, android.view.RemoteAnimationDefinition remoteAnimationDefinition) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void unregisterRemoteAnimations(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void onBackPressed(android.os.IBinder iBinder, android.app.IRequestFinishCallback iRequestFinishCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iRequestFinishCallback);
                    this.mRemote.transact(65, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void splashScreenAttached(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(66, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void requestCompatCameraControl(android.os.IBinder iBinder, boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iCompatCameraControlCallback);
                    this.mRemote.transact(67, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void enableTaskLocaleOverride(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRequestedToLaunchInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setActivityRecordInputSinkEnabled(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 69;
        }
    }
}
