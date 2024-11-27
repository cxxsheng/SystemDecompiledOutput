package com.android.server.wm;

/* loaded from: classes3.dex */
final class ActivityRecord extends com.android.server.wm.WindowToken implements com.android.server.wm.WindowManagerService.AppFreezeListener {
    static final java.lang.String ACTIVITY_ICON_SUFFIX = "_activity_icon_";
    private static final float ASPECT_RATIO_ROUNDING_TOLERANCE = 0.005f;
    private static final java.lang.String ATTR_COMPONENTSPECIFIED = "component_specified";
    private static final java.lang.String ATTR_ID = "id";
    private static final java.lang.String ATTR_LAUNCHEDFROMFEATURE = "launched_from_feature";
    private static final java.lang.String ATTR_LAUNCHEDFROMPACKAGE = "launched_from_package";
    private static final java.lang.String ATTR_LAUNCHEDFROMUID = "launched_from_uid";
    private static final java.lang.String ATTR_RESOLVEDTYPE = "resolved_type";
    private static final java.lang.String ATTR_USERID = "user_id";
    private static final int DESTROY_TIMEOUT = 10000;
    static final int FINISH_RESULT_CANCELLED = 0;
    static final int FINISH_RESULT_REMOVED = 2;
    static final int FINISH_RESULT_REQUESTED = 1;
    static final int INVALID_PID = -1;
    static final int LAUNCH_SOURCE_TYPE_APPLICATION = 4;
    static final int LAUNCH_SOURCE_TYPE_HOME = 2;
    static final int LAUNCH_SOURCE_TYPE_SYSTEM = 1;
    static final int LAUNCH_SOURCE_TYPE_SYSTEMUI = 3;
    private static final int LAUNCH_TICK = 500;
    private static final int MAX_STOPPING_TO_FORCE = 3;
    private static final int PAUSE_TIMEOUT = 500;
    private static final int SPLASH_SCREEN_BEHAVIOR_DEFAULT = 0;
    private static final int SPLASH_SCREEN_BEHAVIOR_ICON_PREFERRED = 1;
    static final int STARTING_WINDOW_TYPE_NONE = 0;
    static final int STARTING_WINDOW_TYPE_SNAPSHOT = 1;
    static final int STARTING_WINDOW_TYPE_SPLASH_SCREEN = 2;
    private static final int STOP_TIMEOUT = 11000;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_ADD_REMOVE = "ActivityTaskManager";
    private static final java.lang.String TAG_APP = "ActivityTaskManager";
    private static final java.lang.String TAG_CONFIGURATION = "ActivityTaskManager";
    private static final java.lang.String TAG_CONTAINERS = "ActivityTaskManager";
    private static final java.lang.String TAG_FOCUS = "ActivityTaskManager";
    private static final java.lang.String TAG_INITIAL_CALLER_INFO = "initial_caller_info";
    private static final java.lang.String TAG_INTENT = "intent";
    private static final java.lang.String TAG_PAUSE = "ActivityTaskManager";
    private static final java.lang.String TAG_PERSISTABLEBUNDLE = "persistable_bundle";
    private static final java.lang.String TAG_RESULTS = "ActivityTaskManager";
    private static final java.lang.String TAG_SAVED_STATE = "ActivityTaskManager";
    private static final java.lang.String TAG_STATES = "ActivityTaskManager";
    private static final java.lang.String TAG_SWITCH = "ActivityTaskManager";
    private static final java.lang.String TAG_TRANSITION = "ActivityTaskManager";
    private static final java.lang.String TAG_USER_LEAVING = "ActivityTaskManager";
    private static final java.lang.String TAG_VISIBILITY = "ActivityTaskManager";
    static final int TRANSFER_SPLASH_SCREEN_ATTACH_TO_CLIENT = 2;
    static final int TRANSFER_SPLASH_SCREEN_COPYING = 1;
    static final int TRANSFER_SPLASH_SCREEN_FINISH = 3;
    static final int TRANSFER_SPLASH_SCREEN_IDLE = 0;
    private static final int TRANSFER_SPLASH_SCREEN_TIMEOUT = 2000;
    private static android.content.pm.ConstrainDisplayApisConfig sConstrainDisplayApisConfig;
    boolean allDrawn;
    com.android.server.wm.WindowProcessController app;
    com.android.server.am.AppTimeTracker appTimeTracker;
    final android.os.Binder assistToken;
    private final boolean componentSpecified;
    int configChangeFlags;
    private long createTime;
    boolean delayedResume;
    boolean finishing;
    boolean firstWindowDrawn;
    boolean hasBeenLaunched;
    boolean idle;
    boolean immersive;
    volatile boolean inHistory;

    @android.annotation.NonNull
    final android.content.pm.ActivityInfo info;
    final android.os.IBinder initialCallerInfoAccessToken;
    final android.content.Intent intent;
    private boolean keysPaused;
    long lastLaunchTime;
    long lastVisibleTime;
    int launchCount;
    boolean launchFailed;
    int launchMode;
    long launchTickTime;

    @android.annotation.Nullable
    final java.lang.String launchedFromFeatureId;
    final java.lang.String launchedFromPackage;
    final int launchedFromPid;
    final int launchedFromUid;
    int lockTaskLaunchMode;
    final android.content.ComponentName mActivityComponent;
    final com.android.server.wm.ActivityRecordInputSink mActivityRecordInputSink;
    boolean mActivityRecordInputSinkEnabled;
    private final com.android.server.wm.ActivityRecord.AddStartingWindow mAddStartingWindow;
    boolean mAllowCrossUidActivitySwitchFromBelow;
    private final boolean mAllowUntrustedEmbeddingStateSharing;
    int mAllowedTouchUid;
    com.android.server.wm.AnimatingActivityRegistry mAnimatingActivityRegistry;
    private final boolean mAppActivityEmbeddingSplitsEnabled;
    boolean mAppStopped;
    final com.android.server.wm.ActivityTaskManagerService mAtmService;
    boolean mAutoEnteringPip;
    final com.android.server.wm.ActivityCallerState mCallerState;
    private boolean mCameraCompatControlClickedByUser;
    private final boolean mCameraCompatControlEnabled;
    private int mCameraCompatControlState;
    private android.os.RemoteCallbackList<android.app.IScreenCaptureObserver> mCaptureCallbacks;
    boolean mClientVisibilityDeferred;
    private final com.android.server.display.color.ColorDisplayService.ColorTransformController mColorTransformController;

    @android.annotation.Nullable
    private android.app.ICompatCameraControlCallback mCompatCameraControlCallback;
    private com.android.server.wm.ActivityRecord.CompatDisplayInsets mCompatDisplayInsets;
    private int mConfigurationSeq;
    private boolean mCurrentLaunchCanTurnScreenOn;
    private com.android.server.wm.ActivityRecord.CustomAppTransition mCustomCloseTransition;
    private com.android.server.wm.ActivityRecord.CustomAppTransition mCustomOpenTransition;
    private boolean mDeferHidingClient;
    private final java.lang.Runnable mDestroyTimeoutRunnable;
    boolean mDismissKeyguardIfInsecure;
    boolean mEnableRecentsScreenshot;
    boolean mEnteringAnimation;
    private boolean mForceSendResultForMediaProjection;
    private boolean mFreezingScreen;
    boolean mHandleExitSplashScreen;

    @com.android.internal.annotations.VisibleForTesting
    int mHandoverLaunchDisplayId;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.TaskDisplayArea mHandoverTaskDisplayArea;

    @android.annotation.Nullable
    private java.lang.Boolean mHasDeskResources;
    final boolean mHasSceneTransition;
    private boolean mHaveState;
    private android.os.Bundle mIcicle;
    boolean mImeInsetsFrozenUntilStartInput;
    private boolean mInSizeCompatModeForBounds;
    private boolean mInheritShownWhenLocked;
    private android.view.InputApplicationHandle mInputApplicationHandle;
    long mInputDispatchingTimeoutMillis;
    private boolean mIsAspectRatioApplied;
    private boolean mIsEligibleForFixedOrientationLetterbox;
    boolean mIsExiting;
    private boolean mIsInputDroppedForAnimation;
    private final boolean mIsUserAlwaysVisible;
    private boolean mLastAllDrawn;
    boolean mLastAllReadyAtSync;
    private com.android.server.wm.ActivityRecord.AppSaturationInfo mLastAppSaturationInfo;
    private boolean mLastContainsDismissKeyguardWindow;
    private boolean mLastContainsShowWhenLockedWindow;
    private boolean mLastContainsTurnScreenOnWindow;
    private boolean mLastDeferHidingClient;

    @android.gui.DropInputMode
    private int mLastDropInputMode;
    boolean mLastImeShown;
    android.content.Intent mLastNewIntent;
    private com.android.server.wm.Task mLastParentBeforePip;
    private final android.window.ActivityWindowInfo mLastReportedActivityWindowInfo;
    private final android.util.MergedConfiguration mLastReportedConfiguration;
    private int mLastReportedDisplayId;
    boolean mLastReportedMultiWindowMode;
    boolean mLastReportedPictureInPictureMode;
    boolean mLastSurfaceShowing;

    @android.annotation.Nullable
    android.window.ITaskFragmentOrganizer mLastTaskFragmentOrganizerBeforePip;
    private long mLastTransactionSequence;
    android.os.IBinder mLaunchCookie;
    private com.android.server.wm.ActivityRecord mLaunchIntoPipHostActivity;
    android.window.WindowContainerToken mLaunchRootTask;
    private final int mLaunchSourceType;
    private final java.lang.Runnable mLaunchTickRunnable;
    private boolean mLaunchedFromBubble;

    @android.annotation.Nullable
    private android.graphics.Rect mLetterboxBoundsForFixedOrientationAndAspectRatio;
    final com.android.server.wm.LetterboxUiController mLetterboxUiController;
    private android.content.LocusId mLocusId;
    private boolean mNeedsLetterboxedAnimation;
    private int mNumDrawnWindows;
    private int mNumInterestingWindows;
    private boolean mOccludesParent;
    final boolean mOptInOnBackInvoked;
    boolean mOverrideTaskTransition;
    int mPauseConfigurationDispatchCount;
    boolean mPauseSchedulePendingForPip;
    private final java.lang.Runnable mPauseTimeoutRunnable;
    private android.app.ActivityOptions mPendingOptions;
    private int mPendingRelaunchCount;
    android.view.RemoteAnimationAdapter mPendingRemoteAnimation;
    private android.window.RemoteTransition mPendingRemoteTransition;
    private android.os.PersistableBundle mPersistentState;
    int mRelaunchReason;
    long mRelaunchStartTime;
    private android.view.RemoteAnimationDefinition mRemoteAnimationDefinition;
    private boolean mRemovingFromDisplay;
    private boolean mReportedDrawn;
    private final com.android.server.wm.WindowState.UpdateReportedVisibilityResults mReportedVisibilityResults;
    boolean mRequestForceTransition;
    android.os.IBinder mRequestedLaunchingTaskFragmentToken;
    final com.android.server.wm.RootWindowContainer mRootWindowContainer;
    int mRotationAnimationHint;

    @com.android.internal.annotations.GuardedBy({"this"})
    com.android.server.wm.ActivityServiceConnectionsHolder mServiceConnectionsHolder;
    boolean mShareIdentity;
    final boolean mShowForAllUsers;
    private boolean mShowWhenLocked;
    private android.graphics.Rect mSizeCompatBounds;
    private float mSizeCompatScale;
    private android.window.SizeConfigurationBuckets mSizeConfigurations;
    boolean mSplashScreenStyleSolidColor;
    com.android.server.wm.StartingData mStartingData;
    com.android.server.wm.StartingSurfaceController.StartingSurface mStartingSurface;
    com.android.server.wm.WindowState mStartingWindow;
    private com.android.server.wm.ActivityRecord.State mState;
    private final java.lang.Runnable mStopTimeoutRunnable;
    final boolean mStyleFillsParent;
    int mTargetSdk;
    private boolean mTaskOverlay;
    final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private final android.window.ActivityWindowInfo mTmpActivityWindowInfo;
    private final android.graphics.Rect mTmpBounds;
    private final android.content.res.Configuration mTmpConfig;
    private final java.lang.Runnable mTransferSplashScreenTimeoutRunnable;

    @com.android.server.wm.ActivityRecord.TransferSplashScreenState
    int mTransferringSplashScreenState;
    int mTransitionChangeFlags;
    private boolean mTurnScreenOn;
    final int mUserId;
    private boolean mVisible;
    volatile boolean mVisibleForServiceConnection;
    private boolean mVisibleSetFromTransferredStartingWindow;
    boolean mVoiceInteraction;
    boolean mWaitForEnteringPinnedMode;
    private boolean mWillCloseOrEnterPip;
    java.util.ArrayList<com.android.internal.content.ReferrerIntent> newIntents;

    @com.android.internal.annotations.VisibleForTesting
    boolean noDisplay;
    boolean nowVisible;
    final java.lang.String packageName;
    long pauseTime;
    java.util.HashSet<java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> pendingResults;
    boolean pendingVoiceInteractionStart;
    android.app.PictureInPictureParams pictureInPictureArgs;
    final java.lang.String processName;
    boolean reportedVisible;
    final int requestCode;
    android.content.ComponentName requestedVrComponent;
    final java.lang.String resolvedType;
    com.android.server.wm.ActivityRecord resultTo;
    final java.lang.String resultWho;
    java.util.ArrayList<android.app.ResultInfo> results;
    android.app.ActivityOptions returningOptions;
    final boolean rootVoiceInteraction;
    final android.os.Binder shareableActivityToken;
    final java.lang.String shortComponentName;
    boolean shouldDockBigOverlays;
    boolean startingMoved;
    final boolean stateNotNeeded;
    boolean supportsEnterPipOnTaskSwitch;
    private com.android.server.wm.Task task;
    final java.lang.String taskAffinity;
    android.app.ActivityManager.TaskDescription taskDescription;
    private final int theme;
    long topResumedStateLossTime;
    com.android.server.uri.UriPermissionOwner uriPermissions;
    boolean visibleIgnoringKeyguard;
    android.service.voice.IVoiceInteractionSession voiceSession;

    static class CustomAppTransition {
        int mBackgroundColor;
        int mEnterAnim;
        int mExitAnim;

        CustomAppTransition() {
        }
    }

    @interface FinishRequest {
    }

    @interface LaunchSourceType {
    }

    @interface SplashScreenBehavior {
    }

    enum State {
        INITIALIZING,
        STARTED,
        RESUMED,
        PAUSING,
        PAUSED,
        STOPPING,
        STOPPED,
        FINISHING,
        DESTROYING,
        DESTROYED,
        RESTARTING_PROCESS
    }

    @interface TransferSplashScreenState {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final float[] fArr, final float[] fArr2) {
        this.mWmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityRecord.this.lambda$new$0(fArr, fArr2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(float[] fArr, float[] fArr2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mLastAppSaturationInfo == null) {
                    this.mLastAppSaturationInfo = new com.android.server.wm.ActivityRecord.AppSaturationInfo();
                }
                this.mLastAppSaturationInfo.setSaturation(fArr, fArr2);
                updateColorTransform();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    @dalvik.annotation.optimization.NeverCompile
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        printWriter.print(str);
        printWriter.print("packageName=");
        printWriter.print(this.packageName);
        printWriter.print(" processName=");
        printWriter.println(this.processName);
        printWriter.print(str);
        printWriter.print("launchedFromUid=");
        printWriter.print(this.launchedFromUid);
        printWriter.print(" launchedFromPackage=");
        printWriter.print(this.launchedFromPackage);
        printWriter.print(" launchedFromFeature=");
        printWriter.print(this.launchedFromFeatureId);
        printWriter.print(" userId=");
        printWriter.println(this.mUserId);
        printWriter.print(str);
        printWriter.print("app=");
        printWriter.println(this.app);
        printWriter.print(str);
        printWriter.println(this.intent.toInsecureString());
        printWriter.print(str);
        printWriter.print("rootOfTask=");
        printWriter.print(isRootOfTask());
        printWriter.print(" task=");
        printWriter.println(this.task);
        printWriter.print(str);
        printWriter.print("taskAffinity=");
        printWriter.println(this.taskAffinity);
        printWriter.print(str);
        printWriter.print("mActivityComponent=");
        printWriter.println(this.mActivityComponent.flattenToShortString());
        android.content.pm.ApplicationInfo applicationInfo = this.info.applicationInfo;
        printWriter.print(str);
        printWriter.print("baseDir=");
        printWriter.println(applicationInfo.sourceDir);
        if (!java.util.Objects.equals(applicationInfo.sourceDir, applicationInfo.publicSourceDir)) {
            printWriter.print(str);
            printWriter.print("resDir=");
            printWriter.println(applicationInfo.publicSourceDir);
        }
        printWriter.print(str);
        printWriter.print("dataDir=");
        printWriter.println(applicationInfo.dataDir);
        if (applicationInfo.splitSourceDirs != null) {
            printWriter.print(str);
            printWriter.print("splitDir=");
            printWriter.println(java.util.Arrays.toString(applicationInfo.splitSourceDirs));
        }
        printWriter.print(str);
        printWriter.print("stateNotNeeded=");
        printWriter.print(this.stateNotNeeded);
        printWriter.print(" componentSpecified=");
        printWriter.print(this.componentSpecified);
        printWriter.print(" mActivityType=");
        printWriter.println(android.app.WindowConfiguration.activityTypeToString(getActivityType()));
        if (this.rootVoiceInteraction) {
            printWriter.print(str);
            printWriter.print("rootVoiceInteraction=");
            printWriter.println(this.rootVoiceInteraction);
        }
        printWriter.print(str);
        printWriter.print("compat=");
        printWriter.print(this.mAtmService.compatibilityInfoForPackageLocked(this.info.applicationInfo));
        printWriter.print(" theme=0x");
        printWriter.println(java.lang.Integer.toHexString(this.theme));
        printWriter.println(str + "mLastReportedConfigurations:");
        this.mLastReportedConfiguration.dump(printWriter, str + "  ");
        if (com.android.window.flags.Flags.activityWindowInfoFlag()) {
            printWriter.print(str);
            printWriter.print("mLastReportedActivityWindowInfo=");
            printWriter.println(this.mLastReportedActivityWindowInfo);
        }
        printWriter.print(str);
        printWriter.print("CurrentConfiguration=");
        printWriter.println(getConfiguration());
        if (!getRequestedOverrideConfiguration().equals(android.content.res.Configuration.EMPTY)) {
            printWriter.println(str + "RequestedOverrideConfiguration=" + getRequestedOverrideConfiguration());
        }
        if (!getResolvedOverrideConfiguration().equals(getRequestedOverrideConfiguration())) {
            printWriter.println(str + "ResolvedOverrideConfiguration=" + getResolvedOverrideConfiguration());
        }
        if (!matchParentBounds()) {
            printWriter.println(str + "bounds=" + getBounds());
        }
        if (this.resultTo != null || this.resultWho != null) {
            printWriter.print(str);
            printWriter.print("resultTo=");
            printWriter.print(this.resultTo);
            printWriter.print(" resultWho=");
            printWriter.print(this.resultWho);
            printWriter.print(" resultCode=");
            printWriter.println(this.requestCode);
        }
        if (this.taskDescription != null && (this.taskDescription.getIconFilename() != null || this.taskDescription.getLabel() != null || this.taskDescription.getPrimaryColor() != 0)) {
            printWriter.print(str);
            printWriter.print("taskDescription:");
            printWriter.print(" label=\"");
            printWriter.print(this.taskDescription.getLabel());
            printWriter.print("\"");
            printWriter.print(" icon=");
            printWriter.print(this.taskDescription.getInMemoryIcon() != null ? this.taskDescription.getInMemoryIcon().getByteCount() + " bytes" : "null");
            printWriter.print(" iconResource=");
            printWriter.print(this.taskDescription.getIconResourcePackage());
            printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            printWriter.print(this.taskDescription.getIconResource());
            printWriter.print(" iconFilename=");
            printWriter.print(this.taskDescription.getIconFilename());
            printWriter.print(" primaryColor=");
            printWriter.println(java.lang.Integer.toHexString(this.taskDescription.getPrimaryColor()));
            printWriter.print(str);
            printWriter.print("  backgroundColor=");
            printWriter.print(java.lang.Integer.toHexString(this.taskDescription.getBackgroundColor()));
            printWriter.print(" statusBarColor=");
            printWriter.print(java.lang.Integer.toHexString(this.taskDescription.getStatusBarColor()));
            printWriter.print(" navigationBarColor=");
            printWriter.println(java.lang.Integer.toHexString(this.taskDescription.getNavigationBarColor()));
            printWriter.print(str);
            printWriter.print(" backgroundColorFloating=");
            printWriter.println(java.lang.Integer.toHexString(this.taskDescription.getBackgroundColorFloating()));
        }
        if (this.results != null) {
            printWriter.print(str);
            printWriter.print("results=");
            printWriter.println(this.results);
        }
        if (this.pendingResults != null && this.pendingResults.size() > 0) {
            printWriter.print(str);
            printWriter.println("Pending Results:");
            java.util.Iterator<java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> it = this.pendingResults.iterator();
            while (it.hasNext()) {
                java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> next = it.next();
                com.android.server.am.PendingIntentRecord pendingIntentRecord = next != null ? next.get() : null;
                printWriter.print(str);
                printWriter.print("  - ");
                if (pendingIntentRecord == null) {
                    printWriter.println("null");
                } else {
                    printWriter.println(pendingIntentRecord);
                    pendingIntentRecord.dump(printWriter, str + "    ");
                }
            }
        }
        if (this.newIntents != null && this.newIntents.size() > 0) {
            printWriter.print(str);
            printWriter.println("Pending New Intents:");
            for (int i = 0; i < this.newIntents.size(); i++) {
                android.content.Intent intent = this.newIntents.get(i);
                printWriter.print(str);
                printWriter.print("  - ");
                if (intent == null) {
                    printWriter.println("null");
                } else {
                    printWriter.println(intent.toShortString(false, true, false, false));
                }
            }
        }
        if (this.mPendingOptions != null) {
            printWriter.print(str);
            printWriter.print("pendingOptions=");
            printWriter.println(this.mPendingOptions);
        }
        if (this.mPendingRemoteAnimation != null) {
            printWriter.print(str);
            printWriter.print("pendingRemoteAnimationCallingPid=");
            printWriter.println(this.mPendingRemoteAnimation.getCallingPid());
        }
        if (this.mPendingRemoteTransition != null) {
            printWriter.print(str + " pendingRemoteTransition=" + this.mPendingRemoteTransition.getRemoteTransition());
        }
        if (this.appTimeTracker != null) {
            this.appTimeTracker.dumpWithHeader(printWriter, str, false);
        }
        if (this.uriPermissions != null) {
            this.uriPermissions.dump(printWriter, str);
        }
        printWriter.print(str);
        printWriter.print("launchFailed=");
        printWriter.print(this.launchFailed);
        printWriter.print(" launchCount=");
        printWriter.print(this.launchCount);
        printWriter.print(" lastLaunchTime=");
        if (this.lastLaunchTime == 0) {
            printWriter.print("0");
        } else {
            android.util.TimeUtils.formatDuration(this.lastLaunchTime, uptimeMillis, printWriter);
        }
        printWriter.println();
        if (this.mLaunchCookie != null) {
            printWriter.print(str);
            printWriter.print("launchCookie=");
            printWriter.println(this.mLaunchCookie);
        }
        if (this.mLaunchRootTask != null) {
            printWriter.print(str);
            printWriter.print("mLaunchRootTask=");
            printWriter.println(this.mLaunchRootTask);
        }
        printWriter.print(str);
        printWriter.print("mHaveState=");
        printWriter.print(this.mHaveState);
        printWriter.print(" mIcicle=");
        printWriter.println(this.mIcicle);
        printWriter.print(str);
        printWriter.print("state=");
        printWriter.print(this.mState);
        printWriter.print(" delayedResume=");
        printWriter.print(this.delayedResume);
        printWriter.print(" finishing=");
        printWriter.println(this.finishing);
        printWriter.print(str);
        printWriter.print("keysPaused=");
        printWriter.print(this.keysPaused);
        printWriter.print(" inHistory=");
        printWriter.print(this.inHistory);
        printWriter.print(" idle=");
        printWriter.println(this.idle);
        printWriter.print(str);
        printWriter.print("occludesParent=");
        printWriter.print(occludesParent());
        printWriter.print(" noDisplay=");
        printWriter.print(this.noDisplay);
        printWriter.print(" immersive=");
        printWriter.print(this.immersive);
        printWriter.print(" launchMode=");
        printWriter.println(this.launchMode);
        printWriter.print(str);
        printWriter.print("mActivityType=");
        printWriter.println(android.app.WindowConfiguration.activityTypeToString(getActivityType()));
        printWriter.print(str);
        printWriter.print("mImeInsetsFrozenUntilStartInput=");
        printWriter.println(this.mImeInsetsFrozenUntilStartInput);
        if (this.requestedVrComponent != null) {
            printWriter.print(str);
            printWriter.print("requestedVrComponent=");
            printWriter.println(this.requestedVrComponent);
        }
        super.dump(printWriter, str, z);
        if (this.mVoiceInteraction) {
            printWriter.println(str + "mVoiceInteraction=true");
        }
        printWriter.print(str);
        printWriter.print("mOccludesParent=");
        printWriter.println(this.mOccludesParent);
        printWriter.print(str);
        printWriter.print("overrideOrientation=");
        printWriter.println(android.content.pm.ActivityInfo.screenOrientationToString(getOverrideOrientation()));
        printWriter.print(str);
        printWriter.print("requestedOrientation=");
        printWriter.println(android.content.pm.ActivityInfo.screenOrientationToString(super.getOverrideOrientation()));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("mVisibleRequested=");
        sb.append(this.mVisibleRequested);
        sb.append(" mVisible=");
        sb.append(this.mVisible);
        sb.append(" mClientVisible=");
        sb.append(isClientVisible());
        sb.append(this.mDeferHidingClient ? " mDeferHidingClient=" + this.mDeferHidingClient : "");
        sb.append(" reportedDrawn=");
        sb.append(this.mReportedDrawn);
        sb.append(" reportedVisible=");
        sb.append(this.reportedVisible);
        printWriter.println(sb.toString());
        if (this.paused) {
            printWriter.print(str);
            printWriter.print("paused=");
            printWriter.println(this.paused);
        }
        if (this.mAppStopped) {
            printWriter.print(str);
            printWriter.print("mAppStopped=");
            printWriter.println(this.mAppStopped);
        }
        if (this.mNumInterestingWindows != 0 || this.mNumDrawnWindows != 0 || this.allDrawn || this.mLastAllDrawn) {
            printWriter.print(str);
            printWriter.print("mNumInterestingWindows=");
            printWriter.print(this.mNumInterestingWindows);
            printWriter.print(" mNumDrawnWindows=");
            printWriter.print(this.mNumDrawnWindows);
            printWriter.print(" allDrawn=");
            printWriter.print(this.allDrawn);
            printWriter.print(" lastAllDrawn=");
            printWriter.print(this.mLastAllDrawn);
            printWriter.println(")");
        }
        if (this.mStartingData != null || this.firstWindowDrawn || this.mIsExiting) {
            printWriter.print(str);
            printWriter.print("startingData=");
            printWriter.print(this.mStartingData);
            printWriter.print(" firstWindowDrawn=");
            printWriter.print(this.firstWindowDrawn);
            printWriter.print(" mIsExiting=");
            printWriter.println(this.mIsExiting);
        }
        if (this.mStartingWindow != null || this.mStartingData != null || this.mStartingSurface != null || this.startingMoved || this.mVisibleSetFromTransferredStartingWindow) {
            printWriter.print(str);
            printWriter.print("startingWindow=");
            printWriter.print(this.mStartingWindow);
            printWriter.print(" startingSurface=");
            printWriter.print(this.mStartingSurface);
            printWriter.print(" startingDisplayed=");
            printWriter.print(isStartingWindowDisplayed());
            printWriter.print(" startingMoved=");
            printWriter.print(this.startingMoved);
            printWriter.println(" mVisibleSetFromTransferredStartingWindow=" + this.mVisibleSetFromTransferredStartingWindow);
        }
        if (this.mPendingRelaunchCount != 0) {
            printWriter.print(str);
            printWriter.print("mPendingRelaunchCount=");
            printWriter.println(this.mPendingRelaunchCount);
        }
        if (this.mSizeCompatScale != 1.0f || this.mSizeCompatBounds != null) {
            printWriter.println(str + "mSizeCompatScale=" + this.mSizeCompatScale + " mSizeCompatBounds=" + this.mSizeCompatBounds);
        }
        if (this.mRemovingFromDisplay) {
            printWriter.println(str + "mRemovingFromDisplay=" + this.mRemovingFromDisplay);
        }
        if (this.lastVisibleTime != 0 || this.nowVisible) {
            printWriter.print(str);
            printWriter.print("nowVisible=");
            printWriter.print(this.nowVisible);
            printWriter.print(" lastVisibleTime=");
            if (this.lastVisibleTime == 0) {
                printWriter.print("0");
            } else {
                android.util.TimeUtils.formatDuration(this.lastVisibleTime, uptimeMillis, printWriter);
            }
            printWriter.println();
        }
        if (this.mDeferHidingClient) {
            printWriter.println(str + "mDeferHidingClient=" + this.mDeferHidingClient);
        }
        if (this.configChangeFlags != 0) {
            printWriter.print(str);
            printWriter.print(" configChangeFlags=");
            printWriter.println(java.lang.Integer.toHexString(this.configChangeFlags));
        }
        if (this.mServiceConnectionsHolder != null) {
            printWriter.print(str);
            printWriter.print("connections=");
            printWriter.println(this.mServiceConnectionsHolder);
        }
        if (this.info != null) {
            printWriter.println(str + "resizeMode=" + android.content.pm.ActivityInfo.resizeModeToString(this.info.resizeMode));
            printWriter.println(str + "mLastReportedMultiWindowMode=" + this.mLastReportedMultiWindowMode + " mLastReportedPictureInPictureMode=" + this.mLastReportedPictureInPictureMode);
            if (this.info.supportsPictureInPicture()) {
                printWriter.println(str + "supportsPictureInPicture=" + this.info.supportsPictureInPicture());
                printWriter.println(str + "supportsEnterPipOnTaskSwitch: " + this.supportsEnterPipOnTaskSwitch);
            }
            if (getMaxAspectRatio() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                printWriter.println(str + "maxAspectRatio=" + getMaxAspectRatio());
            }
            float minAspectRatio = getMinAspectRatio();
            if (minAspectRatio != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                printWriter.println(str + "minAspectRatio=" + minAspectRatio);
            }
            if (minAspectRatio != this.info.getManifestMinAspectRatio()) {
                printWriter.println(str + "manifestMinAspectRatio=" + this.info.getManifestMinAspectRatio());
            }
            printWriter.println(str + "supportsSizeChanges=" + android.content.pm.ActivityInfo.sizeChangesSupportModeToString(supportsSizeChanges()));
            if (this.info.configChanges != 0) {
                printWriter.println(str + "configChanges=0x" + java.lang.Integer.toHexString(this.info.configChanges));
            }
            printWriter.println(str + "neverSandboxDisplayApis=" + this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig));
            printWriter.println(str + "alwaysSandboxDisplayApis=" + this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig));
        }
        if (this.mLastParentBeforePip != null) {
            printWriter.println(str + "lastParentTaskIdBeforePip=" + this.mLastParentBeforePip.mTaskId);
        }
        if (this.mLaunchIntoPipHostActivity != null) {
            printWriter.println(str + "launchIntoPipHostActivity=" + this.mLaunchIntoPipHostActivity);
        }
        if (this.mWaitForEnteringPinnedMode) {
            printWriter.print(str);
            printWriter.println("mWaitForEnteringPinnedMode=true");
        }
        this.mLetterboxUiController.dump(printWriter, str);
        printWriter.println(str + "mCameraCompatControlState=" + android.app.AppCompatTaskInfo.cameraCompatControlStateToString(this.mCameraCompatControlState));
        printWriter.println(str + "mCameraCompatControlEnabled=" + this.mCameraCompatControlEnabled);
    }

    static boolean dumpActivity(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, int i, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, java.lang.String str2, boolean z, boolean z2, boolean z3, java.lang.String str3, boolean z4, java.lang.Runnable runnable, com.android.server.wm.Task task) {
        boolean z5;
        com.android.internal.os.TransferPipe transferPipe;
        if (str3 != null && !str3.equals(activityRecord.packageName)) {
            return false;
        }
        if (z2 || (!z && activityRecord.isInHistory())) {
            z5 = false;
        } else {
            z5 = true;
        }
        if (z4) {
            printWriter.println("");
        }
        if (runnable != null) {
            runnable.run();
        }
        java.lang.String str4 = str + "  ";
        java.lang.String[] strArr = new java.lang.String[0];
        if (task != activityRecord.getTask()) {
            com.android.server.wm.Task task2 = activityRecord.getTask();
            printWriter.print(str);
            printWriter.print(z5 ? "* " : "  ");
            printWriter.println(task2);
            if (z5) {
                task2.dump(printWriter, str + "  ");
            } else if (z && task2.intent != null) {
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.println(task2.intent.toInsecureString());
            }
        }
        printWriter.print(str);
        printWriter.print(z5 ? "* " : "    ");
        printWriter.print(str2);
        printWriter.print(" #");
        printWriter.print(i);
        printWriter.print(": ");
        printWriter.println(activityRecord);
        if (z5) {
            activityRecord.dump(printWriter, str4, true);
        } else if (z) {
            printWriter.print(str4);
            printWriter.println(activityRecord.intent.toInsecureString());
            if (activityRecord.app != null) {
                printWriter.print(str4);
                printWriter.println(activityRecord.app);
            }
        }
        if (z3 && activityRecord.attachedToProcess()) {
            printWriter.flush();
            try {
                transferPipe = new com.android.internal.os.TransferPipe();
            } catch (android.os.RemoteException e) {
                printWriter.println(str4 + "Got a RemoteException while dumping the activity");
            } catch (java.io.IOException e2) {
                printWriter.println(str4 + "Failure while dumping the activity: " + e2);
            }
            try {
                activityRecord.app.getThread().dumpActivity(transferPipe.getWriteFd(), activityRecord.token, str4, strArr);
                transferPipe.go(fileDescriptor, 2000L);
            } finally {
                transferPipe.kill();
            }
        }
        return true;
    }

    void setSavedState(@android.annotation.Nullable android.os.Bundle bundle) {
        this.mIcicle = bundle;
        this.mHaveState = this.mIcicle != null;
    }

    @android.annotation.Nullable
    android.os.Bundle getSavedState() {
        return this.mIcicle;
    }

    boolean hasSavedState() {
        return this.mHaveState;
    }

    @android.annotation.Nullable
    android.os.PersistableBundle getPersistentSavedState() {
        return this.mPersistentState;
    }

    void updateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo) {
        this.info.applicationInfo = applicationInfo;
    }

    void setSizeConfigurations(android.window.SizeConfigurationBuckets sizeConfigurationBuckets) {
        this.mSizeConfigurations = sizeConfigurationBuckets;
    }

    private void scheduleActivityMovedToDisplay(int i, @android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.window.ActivityWindowInfo activityWindowInfo) {
        if (!attachedToProcess()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SWITCH, -6509265758887333864L, 4, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(i));
            return;
        }
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SWITCH, -4183059578873561863L, 4, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(i), java.lang.String.valueOf(configuration));
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.MoveToDisplayItem.obtain(this.token, i, configuration, activityWindowInfo));
        } catch (android.os.RemoteException e) {
        }
    }

    private void scheduleConfigurationChanged(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.window.ActivityWindowInfo activityWindowInfo) {
        if (!attachedToProcess()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 7435279034964784633L, 0, null, java.lang.String.valueOf(this));
            return;
        }
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -7418876140361338495L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(configuration));
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.ActivityConfigurationChangeItem.obtain(this.token, configuration, activityWindowInfo));
        } catch (android.os.RemoteException e) {
        }
    }

    boolean scheduleTopResumedActivityChanged(boolean z) {
        if (!attachedToProcess()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -4284934398288119962L, 0, null, java.lang.String.valueOf(this));
            return false;
        }
        if (z) {
            this.app.addToPendingTop();
        }
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 7244227111034368231L, 12, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(z));
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.TopResumedActivityChangeItem.obtain(this.token, z));
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("ActivityTaskManager", "Failed to send top-resumed=" + z + " to " + this, e);
            return false;
        }
    }

    void updateMultiWindowMode() {
        boolean inMultiWindowMode;
        if (this.task != null && this.task.getRootTask() != null && attachedToProcess() && (inMultiWindowMode = inMultiWindowMode()) != this.mLastReportedMultiWindowMode) {
            if (!inMultiWindowMode && this.mLastReportedPictureInPictureMode) {
                updatePictureInPictureMode(null, false);
            } else {
                this.mLastReportedMultiWindowMode = inMultiWindowMode;
                ensureActivityConfiguration();
            }
        }
    }

    void updatePictureInPictureMode(android.graphics.Rect rect, boolean z) {
        if (this.task == null || this.task.getRootTask() == null || !attachedToProcess()) {
            return;
        }
        boolean z2 = inPinnedWindowingMode() && rect != null;
        if (z2 != this.mLastReportedPictureInPictureMode || z) {
            this.mLastReportedPictureInPictureMode = z2;
            this.mLastReportedMultiWindowMode = z2;
            ensureActivityConfiguration(true);
            if (z2 && findMainWindow() == null && this.task.topRunningActivity() == this) {
                android.util.EventLog.writeEvent(1397638484, "265293293", -1, "");
                removeImmediately();
            }
        }
    }

    com.android.server.wm.Task getTask() {
        return this.task;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getTaskFragment() {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            return parent.asTaskFragment();
        }
        return null;
    }

    private boolean shouldStartChangeTransition(@android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, @android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment2) {
        if (taskFragment == null || taskFragment2 == null || !canStartChangeTransition()) {
            return false;
        }
        boolean z = com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled() && inPinnedWindowingMode();
        if (!taskFragment.isOrganizedTaskFragment() && !z) {
            return false;
        }
        return !taskFragment.getBounds().equals(taskFragment2.getBounds());
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canStartChangeTransition() {
        com.android.server.wm.Task task = getTask();
        return (task == null || task.isDragResizing() || !super.canStartChangeTransition()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        com.android.server.wm.TaskFragment taskFragment = (com.android.server.wm.TaskFragment) configurationContainer2;
        com.android.server.wm.TaskFragment taskFragment2 = (com.android.server.wm.TaskFragment) configurationContainer;
        com.android.server.wm.Task task = taskFragment != null ? taskFragment.getTask() : null;
        com.android.server.wm.Task task2 = taskFragment2 != null ? taskFragment2.getTask() : null;
        this.task = task2;
        if (shouldStartChangeTransition(taskFragment2, taskFragment)) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                initializeChangeTransition(getBounds());
            } else {
                taskFragment2.initializeChangeTransition(getBounds(), getSurfaceControl());
            }
        }
        super.onParentChanged(taskFragment2, taskFragment);
        if (isPersistable()) {
            if (task != null) {
                this.mAtmService.notifyTaskPersisterLocked(task, false);
            }
            if (task2 != null) {
                this.mAtmService.notifyTaskPersisterLocked(task2, false);
            }
        }
        if (taskFragment == null && taskFragment2 != null) {
            this.mVoiceInteraction = task2.voiceSession != null;
            task2.updateOverrideConfigurationFromLaunchBounds();
            this.mLastReportedMultiWindowMode = inMultiWindowMode();
            this.mLastReportedPictureInPictureMode = inPinnedWindowingMode();
        }
        if (this.task == null && getDisplayContent() != null) {
            getDisplayContent().mClosingApps.remove(this);
        }
        com.android.server.wm.Task rootTask = getRootTask();
        updateAnimatingActivityRegistry();
        if (this.task == this.mLastParentBeforePip && this.task != null) {
            this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController.onActivityReparentedToTask(this);
            clearLastParentBeforePip();
        }
        updateColorTransform();
        if (taskFragment != null) {
            taskFragment.cleanUpActivityReferences(this);
            this.mRequestedLaunchingTaskFragmentToken = null;
        }
        if (taskFragment2 != null) {
            if (isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                taskFragment2.setResumedActivity(this, "onParentChanged");
            }
            this.mLetterboxUiController.updateInheritedLetterbox();
        }
        if (rootTask != null && rootTask.topRunningActivity() == this && this.firstWindowDrawn) {
            rootTask.setHasBeenVisible(true);
        }
        updateUntrustedEmbeddingInputProtection();
    }

    @Override // com.android.server.wm.WindowContainer
    void setSurfaceControl(android.view.SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        if (surfaceControl != null) {
            this.mLastDropInputMode = 0;
            updateUntrustedEmbeddingInputProtection();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDropInputForAnimation(boolean z) {
        if (this.mIsInputDroppedForAnimation == z) {
            return;
        }
        this.mIsInputDroppedForAnimation = z;
        updateUntrustedEmbeddingInputProtection();
    }

    private void updateUntrustedEmbeddingInputProtection() {
        if (getSurfaceControl() == null) {
            return;
        }
        if (this.mIsInputDroppedForAnimation) {
            setDropInputMode(1);
        } else if (isEmbeddedInUntrustedMode()) {
            setDropInputMode(2);
        } else {
            setDropInputMode(0);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDropInputMode(@android.gui.DropInputMode int i) {
        if (this.mLastDropInputMode != i) {
            this.mLastDropInputMode = i;
            this.mWmService.mTransactionFactory.get().setDropInputMode(getSurfaceControl(), i).apply();
        }
    }

    private boolean isEmbeddedInUntrustedMode() {
        if (getOrganizedTaskFragment() == null) {
            return false;
        }
        return !r0.isAllowedToEmbedActivityInTrustedMode(this);
    }

    void updateAnimatingActivityRegistry() {
        com.android.server.wm.AnimatingActivityRegistry animatingActivityRegistry;
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask != null) {
            animatingActivityRegistry = rootTask.getAnimatingActivityRegistry();
        } else {
            animatingActivityRegistry = null;
        }
        if (this.mAnimatingActivityRegistry != null && this.mAnimatingActivityRegistry != animatingActivityRegistry) {
            this.mAnimatingActivityRegistry.notifyFinished(this);
        }
        this.mAnimatingActivityRegistry = animatingActivityRegistry;
    }

    boolean canAutoEnterPip() {
        return checkEnterPictureInPictureState("startActivityUnchecked", false) && this.pictureInPictureArgs != null && this.pictureInPictureArgs.isAutoEnterEnabled();
    }

    void setLastParentBeforePip(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.Task task;
        com.android.server.wm.TaskFragment organizedTaskFragment;
        android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer;
        if (activityRecord == null) {
            task = getTask();
        } else {
            task = activityRecord.getTask();
        }
        this.mLastParentBeforePip = task;
        this.mLastParentBeforePip.mChildPipActivity = this;
        this.mLaunchIntoPipHostActivity = activityRecord;
        if (activityRecord == null) {
            organizedTaskFragment = getOrganizedTaskFragment();
        } else {
            organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
        }
        if (organizedTaskFragment != null) {
            iTaskFragmentOrganizer = organizedTaskFragment.getTaskFragmentOrganizer();
        } else {
            iTaskFragmentOrganizer = null;
        }
        this.mLastTaskFragmentOrganizerBeforePip = iTaskFragmentOrganizer;
    }

    void clearLastParentBeforePip() {
        if (this.mLastParentBeforePip != null) {
            this.mLastParentBeforePip.mChildPipActivity = null;
            this.mLastParentBeforePip = null;
        }
        this.mLaunchIntoPipHostActivity = null;
        this.mLastTaskFragmentOrganizerBeforePip = null;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getLastParentBeforePip() {
        return this.mLastParentBeforePip;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getLaunchIntoPipHostActivity() {
        return this.mLaunchIntoPipHostActivity;
    }

    private void updateColorTransform() {
        if (this.mSurfaceControl != null && this.mLastAppSaturationInfo != null) {
            getPendingTransaction().setColorTransform(this.mSurfaceControl, this.mLastAppSaturationInfo.mMatrix, this.mLastAppSaturationInfo.mTranslation);
            this.mWmService.scheduleAnimationLocked();
        }
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.DisplayContent displayContent2 = this.mDisplayContent;
        super.onDisplayChanged(displayContent);
        if (displayContent2 == this.mDisplayContent) {
            return;
        }
        this.mDisplayContent.onRunningActivityChanged();
        if (displayContent2 == null) {
            return;
        }
        displayContent2.onRunningActivityChanged();
        this.mTransitionController.collect(this);
        if (displayContent2.mOpeningApps.remove(this)) {
            this.mDisplayContent.mOpeningApps.add(this);
            this.mDisplayContent.transferAppTransitionFrom(displayContent2);
            this.mDisplayContent.executeAppTransition();
        }
        displayContent2.mClosingApps.remove(this);
        displayContent2.getDisplayPolicy().removeRelaunchingApp(this);
        if (displayContent2.mFocusedApp == this) {
            displayContent2.setFocusedApp(null);
            if (displayContent.getTopMostActivity() == this) {
                displayContent.setFocusedApp(this);
            }
        }
        this.mLetterboxUiController.onMovedToDisplay(this.mDisplayContent.getDisplayId());
    }

    void layoutLetterboxIfNeeded(com.android.server.wm.WindowState windowState) {
        this.mLetterboxUiController.layoutLetterboxIfNeeded(windowState);
    }

    boolean hasWallpaperBackgroundForLetterbox() {
        return this.mLetterboxUiController.hasWallpaperBackgroundForLetterbox();
    }

    void updateLetterboxSurfaceIfNeeded(com.android.server.wm.WindowState windowState, android.view.SurfaceControl.Transaction transaction) {
        this.mLetterboxUiController.updateLetterboxSurfaceIfNeeded(windowState, transaction);
    }

    void updateLetterboxSurfaceIfNeeded(com.android.server.wm.WindowState windowState) {
        this.mLetterboxUiController.updateLetterboxSurfaceIfNeeded(windowState);
    }

    android.graphics.Rect getLetterboxInsets() {
        return this.mLetterboxUiController.getLetterboxInsets();
    }

    void getLetterboxInnerBounds(android.graphics.Rect rect) {
        this.mLetterboxUiController.getLetterboxInnerBounds(rect);
    }

    void updateCameraCompatState(boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) {
        int i;
        if (!isCameraCompatControlEnabled()) {
            return;
        }
        if (this.mCameraCompatControlClickedByUser && (z || this.mCameraCompatControlState == 3)) {
            return;
        }
        this.mCompatCameraControlCallback = iCompatCameraControlCallback;
        if (!z) {
            i = 0;
        } else if (z2) {
            i = 2;
        } else {
            i = 1;
        }
        if (!setCameraCompatControlState(i)) {
            return;
        }
        this.mTaskSupervisor.getActivityMetricsLogger().logCameraCompatControlAppearedEventReported(i, this.info.applicationInfo.uid);
        if (i == 0) {
            this.mCameraCompatControlClickedByUser = false;
            this.mCompatCameraControlCallback = null;
        }
        getTask().dispatchTaskInfoChangedIfNeeded(true);
        getDisplayContent().setLayoutNeeded();
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }

    void updateCameraCompatStateFromUser(int i) {
        if (isCameraCompatControlEnabled()) {
            if (i == 0) {
                android.util.Slog.w("ActivityTaskManager", "Unexpected hidden state in updateCameraCompatState");
                return;
            }
            boolean cameraCompatControlState = setCameraCompatControlState(i);
            this.mCameraCompatControlClickedByUser = true;
            if (!cameraCompatControlState) {
                return;
            }
            this.mTaskSupervisor.getActivityMetricsLogger().logCameraCompatControlClickedEventReported(i, this.info.applicationInfo.uid);
            if (i == 3) {
                this.mCompatCameraControlCallback = null;
                return;
            }
            if (this.mCompatCameraControlCallback == null) {
                android.util.Slog.w("ActivityTaskManager", "Callback for a camera compat control is null");
                return;
            }
            try {
                if (i == 2) {
                    this.mCompatCameraControlCallback.applyCameraCompatTreatment();
                } else {
                    this.mCompatCameraControlCallback.revertCameraCompatTreatment();
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e("ActivityTaskManager", "Unable to apply or revert camera compat treatment", e);
            }
        }
    }

    private boolean setCameraCompatControlState(int i) {
        if (!isCameraCompatControlEnabled() || this.mCameraCompatControlState == i) {
            return false;
        }
        this.mCameraCompatControlState = i;
        return true;
    }

    int getCameraCompatControlState() {
        return this.mCameraCompatControlState;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isCameraCompatControlEnabled() {
        return this.mCameraCompatControlEnabled;
    }

    boolean isFullyTransparentBarAllowed(android.graphics.Rect rect) {
        return this.mLetterboxUiController.isFullyTransparentBarAllowed(rect);
    }

    private static class Token extends android.os.Binder {

        @android.annotation.NonNull
        java.lang.ref.WeakReference<com.android.server.wm.ActivityRecord> mActivityRef;

        private Token() {
        }

        public java.lang.String toString() {
            return "Token{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mActivityRef.get() + "}";
        }
    }

    @android.annotation.Nullable
    static com.android.server.wm.ActivityRecord forToken(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            return ((com.android.server.wm.ActivityRecord.Token) iBinder).mActivityRef.get();
        } catch (java.lang.ClassCastException e) {
            android.util.Slog.w("ActivityTaskManager", "Bad activity token: " + iBinder, e);
            return null;
        }
    }

    @android.annotation.Nullable
    static com.android.server.wm.ActivityRecord forTokenLocked(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forToken = forToken(iBinder);
        if (forToken == null || forToken.getRootTask() == null) {
            return null;
        }
        return forToken;
    }

    static boolean isResolverActivity(java.lang.String str) {
        return com.android.internal.app.ResolverActivity.class.getName().equals(str);
    }

    boolean isResolverOrDelegateActivity() {
        return isResolverActivity(this.mActivityComponent.getClassName()) || java.util.Objects.equals(this.mActivityComponent, this.mAtmService.mTaskSupervisor.getSystemChooserActivity());
    }

    boolean isResolverOrChildActivity() {
        if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(this.packageName)) {
            return false;
        }
        try {
            return com.android.internal.app.ResolverActivity.class.isAssignableFrom(java.lang.Object.class.getClassLoader().loadClass(this.mActivityComponent.getClassName()));
        } catch (java.lang.ClassNotFoundException e) {
            return false;
        }
    }

    boolean hasCaller(android.os.IBinder iBinder) {
        return this.mCallerState.hasCaller(iBinder);
    }

    int getCallerUid(android.os.IBinder iBinder) {
        return this.mCallerState.getUid(iBinder);
    }

    java.lang.String getCallerPackage(android.os.IBinder iBinder) {
        return this.mCallerState.getPackage(iBinder);
    }

    boolean isCallerShareIdentityEnabled(android.os.IBinder iBinder) {
        return this.mCallerState.isShareIdentityEnabled(iBinder);
    }

    void computeInitialCallerInfo() {
        computeCallerInfo(this.initialCallerInfoAccessToken, this.intent, this.launchedFromUid, this.launchedFromPackage, this.mShareIdentity);
    }

    void computeCallerInfo(android.os.IBinder iBinder, android.content.Intent intent, int i, java.lang.String str, boolean z) {
        this.mCallerState.computeCallerInfo(iBinder, intent, i, str, z);
    }

    boolean checkContentUriPermission(android.os.IBinder iBinder, com.android.server.uri.GrantUri grantUri, int i) {
        return this.mCallerState.checkContentUriPermission(iBinder, grantUri, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x043d, code lost:
    
        if (r17.mAtmService.mContext.getPackageManager().getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED", r17.packageName).getBoolean() != false) goto L130;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ActivityRecord(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.WindowProcessController windowProcessController, int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.content.pm.ActivityInfo activityInfo, android.content.res.Configuration configuration, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str4, int i3, boolean z, boolean z2, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.app.ActivityOptions activityOptions, com.android.server.wm.ActivityRecord activityRecord2, android.os.PersistableBundle persistableBundle, android.app.ActivityManager.TaskDescription taskDescription, long j) {
        super(activityTaskManagerService.mWindowManager, new com.android.server.wm.ActivityRecord.Token(), 2, true, null, false);
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        this.mHandoverLaunchDisplayId = -1;
        this.createTime = java.lang.System.currentTimeMillis();
        this.mLastReportedActivityWindowInfo = new android.window.ActivityWindowInfo();
        boolean z12 = true;
        this.mHaveState = true;
        this.pictureInPictureArgs = new android.app.PictureInPictureParams.Builder().build();
        boolean z13 = false;
        this.mSplashScreenStyleSolidColor = false;
        this.mPauseSchedulePendingForPip = false;
        this.mAutoEnteringPip = false;
        this.mTaskOverlay = false;
        this.mRelaunchReason = 0;
        this.mForceSendResultForMediaProjection = false;
        this.mRemovingFromDisplay = false;
        this.mReportedVisibilityResults = new com.android.server.wm.WindowState.UpdateReportedVisibilityResults();
        this.mCurrentLaunchCanTurnScreenOn = true;
        this.mInputDispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        this.mLastTransactionSequence = Long.MIN_VALUE;
        this.mLastAllReadyAtSync = false;
        this.mSizeCompatScale = 1.0f;
        this.mInSizeCompatModeForBounds = false;
        this.mIsAspectRatioApplied = false;
        this.mCameraCompatControlState = 0;
        this.mEnableRecentsScreenshot = true;
        this.mLastDropInputMode = 0;
        this.mTransferringSplashScreenState = 0;
        this.mRotationAnimationHint = -1;
        this.mColorTransformController = new com.android.server.display.color.ColorDisplayService.ColorTransformController() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda26
            @Override // com.android.server.display.color.ColorDisplayService.ColorTransformController
            public final void applyAppSaturation(float[] fArr, float[] fArr2) {
                com.android.server.wm.ActivityRecord.this.lambda$new$1(fArr, fArr2);
            }
        };
        this.mTmpConfig = new android.content.res.Configuration();
        this.mTmpBounds = new android.graphics.Rect();
        this.mTmpActivityWindowInfo = new android.window.ActivityWindowInfo();
        this.assistToken = new android.os.Binder();
        this.shareableActivityToken = new android.os.Binder();
        this.initialCallerInfoAccessToken = new android.os.Binder();
        this.mActivityRecordInputSinkEnabled = true;
        this.mPauseConfigurationDispatchCount = 0;
        this.mPauseTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord.1
            @Override // java.lang.Runnable
            public void run() {
                android.util.Slog.w("ActivityTaskManager", "Activity pause timeout for " + com.android.server.wm.ActivityRecord.this);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mAtmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (!com.android.server.wm.ActivityRecord.this.hasProcess()) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        com.android.server.wm.ActivityRecord.this.mAtmService.logAppTooSlow(com.android.server.wm.ActivityRecord.this.app, com.android.server.wm.ActivityRecord.this.pauseTime, "pausing " + com.android.server.wm.ActivityRecord.this);
                        com.android.server.wm.ActivityRecord.this.activityPaused(true);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
        };
        this.mLaunchTickRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord.2
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mAtmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (com.android.server.wm.ActivityRecord.this.continueLaunchTicking()) {
                            com.android.server.wm.ActivityRecord.this.mAtmService.logAppTooSlow(com.android.server.wm.ActivityRecord.this.app, com.android.server.wm.ActivityRecord.this.launchTickTime, "launching " + com.android.server.wm.ActivityRecord.this);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        };
        this.mDestroyTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mAtmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        android.util.Slog.w("ActivityTaskManager", "Activity destroy timeout for " + com.android.server.wm.ActivityRecord.this);
                        com.android.server.wm.ActivityRecord.this.destroyed("destroyTimeout");
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        };
        this.mStopTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mAtmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        android.util.Slog.w("ActivityTaskManager", "Activity stop timeout for " + com.android.server.wm.ActivityRecord.this);
                        if (com.android.server.wm.ActivityRecord.this.isInHistory()) {
                            com.android.server.wm.ActivityRecord.this.activityStopped(null, null, null);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        };
        this.mAddStartingWindow = new com.android.server.wm.ActivityRecord.AddStartingWindow();
        this.mTransferSplashScreenTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord.5
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mAtmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        android.util.Slog.w("ActivityTaskManager", "Activity transferring splash screen timeout for " + com.android.server.wm.ActivityRecord.this + " state " + com.android.server.wm.ActivityRecord.this.mTransferringSplashScreenState);
                        if (com.android.server.wm.ActivityRecord.this.isTransferringSplashScreen()) {
                            com.android.server.wm.ActivityRecord.this.mTransferringSplashScreenState = 3;
                            com.android.server.wm.ActivityRecord.this.removeStartingWindow();
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        };
        this.mAtmService = activityTaskManagerService;
        ((com.android.server.wm.ActivityRecord.Token) this.token).mActivityRef = new java.lang.ref.WeakReference<>(this);
        this.info = activityInfo;
        this.mUserId = android.os.UserHandle.getUserId(this.info.applicationInfo.uid);
        this.packageName = this.info.applicationInfo.packageName;
        this.intent = intent;
        if (this.info.targetActivity == null || (this.info.targetActivity.equals(this.intent.getComponent().getClassName()) && (this.info.launchMode == 0 || this.info.launchMode == 1))) {
            this.mActivityComponent = this.intent.getComponent();
        } else {
            this.mActivityComponent = new android.content.ComponentName(this.info.packageName, this.info.targetActivity);
        }
        this.mLetterboxUiController = new com.android.server.wm.LetterboxUiController(this.mWmService, this);
        this.mCameraCompatControlEnabled = this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_ignoreUdfpsVote);
        this.mTargetSdk = this.info.applicationInfo.targetSdkVersion;
        android.content.pm.UserProperties userProperties = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserProperties(this.mUserId);
        if (userProperties != null && userProperties.getAlwaysVisible()) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mIsUserAlwaysVisible = z3;
        if ((this.info.flags & 1024) != 0 || this.mIsUserAlwaysVisible) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.mShowForAllUsers = z4;
        setOrientation(this.info.screenOrientation);
        this.mRotationAnimationHint = this.info.rotationAnimation;
        if ((activityInfo.flags & 8388608) != 0) {
            z5 = true;
        } else {
            z5 = false;
        }
        this.mShowWhenLocked = z5;
        if ((activityInfo.privateFlags & 1) != 0) {
            z6 = true;
        } else {
            z6 = false;
        }
        this.mInheritShownWhenLocked = z6;
        if ((activityInfo.flags & 16777216) != 0) {
            z7 = true;
        } else {
            z7 = false;
        }
        this.mTurnScreenOn = z7;
        int themeResource = this.info.getThemeResource();
        com.android.internal.policy.AttributeCache.Entry entry = com.android.internal.policy.AttributeCache.instance().get(this.packageName, themeResource == 0 ? activityInfo.applicationInfo.targetSdkVersion < 11 ? android.R.style.Theme : android.R.style.Theme.Holo : themeResource, com.android.internal.R.styleable.Window, this.mUserId);
        if (entry != null) {
            if (!android.content.pm.ActivityInfo.isTranslucentOrFloating(entry.array) || entry.array.getBoolean(14, false)) {
                z11 = true;
            } else {
                z11 = false;
            }
            this.mOccludesParent = z11;
            this.mStyleFillsParent = this.mOccludesParent;
            this.noDisplay = entry.array.getBoolean(10, false);
        } else {
            this.mOccludesParent = true;
            this.mStyleFillsParent = true;
            this.noDisplay = false;
        }
        if (activityOptions != null) {
            this.mLaunchTaskBehind = activityOptions.getLaunchTaskBehind();
            int rotationAnimationHint = activityOptions.getRotationAnimationHint();
            if (rotationAnimationHint >= 0) {
                this.mRotationAnimationHint = rotationAnimationHint;
            }
            if (activityOptions.getLaunchIntoPipParams() != null) {
                this.pictureInPictureArgs = activityOptions.getLaunchIntoPipParams();
                if (activityRecord2 != null) {
                    adjustPictureInPictureParamsIfNeeded(activityRecord2.getBounds());
                }
            }
            this.mOverrideTaskTransition = activityOptions.getOverrideTaskTransition();
            this.mDismissKeyguardIfInsecure = activityOptions.getDismissKeyguardIfInsecure();
            this.mShareIdentity = activityOptions.isShareIdentityEnabled();
        }
        ((com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal) com.android.server.LocalServices.getService(com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal.class)).attachColorTransformController(this.packageName, this.mUserId, new java.lang.ref.WeakReference<>(this.mColorTransformController));
        this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        this.launchedFromPid = i;
        this.launchedFromUid = i2;
        this.launchedFromPackage = str;
        this.launchedFromFeatureId = str2;
        this.mLaunchSourceType = determineLaunchSourceType(i2, windowProcessController);
        this.shortComponentName = intent.getComponent().flattenToShortString();
        this.resolvedType = str3;
        this.componentSpecified = z;
        this.rootVoiceInteraction = z2;
        this.mLastReportedConfiguration = new android.util.MergedConfiguration(configuration);
        this.resultTo = activityRecord;
        this.resultWho = str4;
        this.requestCode = i3;
        setState(com.android.server.wm.ActivityRecord.State.INITIALIZING, "ActivityRecord ctor");
        this.launchFailed = false;
        this.delayedResume = false;
        this.finishing = false;
        this.keysPaused = false;
        this.inHistory = false;
        this.nowVisible = false;
        super.setClientVisible(true);
        this.idle = false;
        this.hasBeenLaunched = false;
        this.mTaskSupervisor = activityTaskSupervisor;
        this.info.taskAffinity = computeTaskAffinity(this.info.taskAffinity, this.info.applicationInfo.uid);
        this.taskAffinity = this.info.taskAffinity;
        java.lang.String num = java.lang.Integer.toString(this.info.applicationInfo.uid);
        if (this.info.windowLayout != null && this.info.windowLayout.windowLayoutAffinity != null && !this.info.windowLayout.windowLayoutAffinity.startsWith(num)) {
            this.info.windowLayout.windowLayoutAffinity = num + ":" + this.info.windowLayout.windowLayoutAffinity;
        }
        if (sConstrainDisplayApisConfig == null) {
            sConstrainDisplayApisConfig = new android.content.pm.ConstrainDisplayApisConfig();
        }
        if ((activityInfo.flags & 16) != 0) {
            z8 = true;
        } else {
            z8 = false;
        }
        this.stateNotNeeded = z8;
        this.theme = activityInfo.getThemeResource();
        if ((activityInfo.flags & 1) == 0 || windowProcessController == null || (activityInfo.applicationInfo.uid != 1000 && activityInfo.applicationInfo.uid != windowProcessController.mInfo.uid)) {
            this.processName = activityInfo.processName;
        } else {
            this.processName = windowProcessController.mName;
        }
        if ((activityInfo.flags & 32) != 0) {
            this.intent.addFlags(8388608);
        }
        this.launchMode = activityInfo.launchMode;
        setActivityType(z, i2, intent, activityOptions, activityRecord2);
        if ((activityInfo.flags & 2048) != 0) {
            z9 = true;
        } else {
            z9 = false;
        }
        this.immersive = z9;
        this.requestedVrComponent = activityInfo.requestedVrComponent == null ? null : android.content.ComponentName.unflattenFromString(activityInfo.requestedVrComponent);
        this.lockTaskLaunchMode = getLockTaskLaunchMode(activityInfo, activityOptions);
        if (activityOptions != null) {
            setOptions(activityOptions);
            if (activityOptions.getAnimationType() == 5 && activityOptions.getSceneTransitionInfo() != null && activityOptions.getSceneTransitionInfo().getResultReceiver() != null) {
                z10 = true;
            } else {
                z10 = false;
            }
            this.mHasSceneTransition = z10;
            android.app.PendingIntent usageTimeReport = activityOptions.getUsageTimeReport();
            if (usageTimeReport != null) {
                this.appTimeTracker = new com.android.server.am.AppTimeTracker(usageTimeReport);
            }
            android.window.WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
            this.mHandoverTaskDisplayArea = launchTaskDisplayArea != null ? (com.android.server.wm.TaskDisplayArea) com.android.server.wm.WindowContainer.fromBinder(launchTaskDisplayArea.asBinder()) : null;
            this.mHandoverLaunchDisplayId = activityOptions.getLaunchDisplayId();
            this.mLaunchCookie = activityOptions.getLaunchCookie();
            this.mLaunchRootTask = activityOptions.getLaunchRootTask();
        } else {
            this.mHasSceneTransition = false;
        }
        this.mPersistentState = persistableBundle;
        this.taskDescription = taskDescription;
        this.shouldDockBigOverlays = this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_displayWhiteBalanceLightModeAllowed);
        if (j > 0) {
            this.createTime = j;
        }
        this.mAtmService.mPackageConfigPersister.updateConfigIfNeeded(this, this.mUserId, this.packageName);
        this.mActivityRecordInputSink = new com.android.server.wm.ActivityRecordInputSink(this, activityRecord2);
        try {
            if (android.view.WindowManager.hasWindowExtensionsEnabled()) {
            }
            z12 = false;
            z13 = z12;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        this.mAppActivityEmbeddingSplitsEnabled = z13;
        this.mAllowUntrustedEmbeddingStateSharing = getAllowUntrustedEmbeddingStateSharingProperty();
        this.mOptInOnBackInvoked = android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.info, this.info.applicationInfo, new java.util.function.Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda27
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.Context lambda$new$2;
                lambda$new$2 = com.android.server.wm.ActivityRecord.this.lambda$new$2();
                return lambda$new$2;
            }
        });
        this.mCallerState = new com.android.server.wm.ActivityCallerState(this.mAtmService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Context lambda$new$2() {
        android.content.Context context = null;
        try {
            context = this.mAtmService.mContext.createPackageContextAsUser(this.info.packageName, 4, android.os.UserHandle.of(this.mUserId));
            context.setTheme(this.theme);
            return context;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return context;
        }
    }

    static java.lang.String computeTaskAffinity(java.lang.String str, int i) {
        java.lang.String num = java.lang.Integer.toString(i);
        if (str != null && !str.startsWith(num)) {
            return num + ":" + str;
        }
        return str;
    }

    static int getLockTaskLaunchMode(android.content.pm.ActivityInfo activityInfo, @android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        int i = activityInfo.lockTaskLaunchMode;
        if (!activityInfo.applicationInfo.isPrivilegedApp() && (i == 2 || i == 1)) {
            i = 0;
        }
        if (activityOptions != null && activityOptions.getLockTaskMode() && i == 0) {
            return 3;
        }
        return i;
    }

    @android.annotation.NonNull
    android.view.InputApplicationHandle getInputApplicationHandle(boolean z) {
        if (this.mInputApplicationHandle == null) {
            this.mInputApplicationHandle = new android.view.InputApplicationHandle(this.token, toString(), this.mInputDispatchingTimeoutMillis);
        } else if (z) {
            java.lang.String activityRecord = toString();
            if (this.mInputDispatchingTimeoutMillis != this.mInputApplicationHandle.dispatchingTimeoutMillis || !activityRecord.equals(this.mInputApplicationHandle.name)) {
                this.mInputApplicationHandle = new android.view.InputApplicationHandle(this.token, activityRecord, this.mInputDispatchingTimeoutMillis);
            }
        }
        return this.mInputApplicationHandle;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.ActivityRecord asActivityRecord() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean hasActivity() {
        return true;
    }

    void setProcess(com.android.server.wm.WindowProcessController windowProcessController) {
        this.app = windowProcessController;
        if ((this.task != null ? this.task.getRootActivity() : null) == this) {
            this.task.setRootProcess(windowProcessController);
        }
        windowProcessController.addActivityIfNeeded(this);
        this.mInputDispatchingTimeoutMillis = com.android.server.wm.ActivityTaskManagerService.getInputDispatchingTimeoutMillisLocked(this);
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            taskFragment.sendTaskFragmentInfoChanged();
        }
    }

    boolean hasProcess() {
        return this.app != null;
    }

    boolean attachedToProcess() {
        return hasProcess() && this.app.hasThread();
    }

    private int evaluateStartingWindowTheme(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i, int i2) {
        if (!validateStartingWindowTheme(activityRecord, str, i)) {
            return 0;
        }
        if (i2 != 0 && validateStartingWindowTheme(activityRecord, str, i2)) {
            return i2;
        }
        return i;
    }

    private boolean launchedFromSystemSurface() {
        return this.mLaunchSourceType == 1 || this.mLaunchSourceType == 2 || this.mLaunchSourceType == 3;
    }

    boolean isLaunchSourceType(@com.android.server.wm.ActivityRecord.LaunchSourceType int i) {
        return this.mLaunchSourceType == i;
    }

    private int determineLaunchSourceType(int i, com.android.server.wm.WindowProcessController windowProcessController) {
        if (i == 1000 || i == 0) {
            return 1;
        }
        if (windowProcessController != null) {
            if (windowProcessController.isHomeProcess()) {
                return 2;
            }
            if (this.mAtmService.getSysUiServiceComponentLocked().getPackageName().equals(windowProcessController.mInfo.packageName)) {
                return 3;
            }
            return 4;
        }
        return 4;
    }

    private boolean validateStartingWindowTheme(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i) {
        com.android.internal.policy.AttributeCache.Entry entry;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 338586566486930495L, 1, null, java.lang.Long.valueOf(i));
        if (i != 0 && (entry = com.android.internal.policy.AttributeCache.instance().get(str, i, com.android.internal.R.styleable.Window, this.mWmService.mCurrentUserId)) != null) {
            boolean z = entry.array.getBoolean(5, false);
            boolean z2 = entry.array.getBoolean(4, false);
            boolean z3 = entry.array.getBoolean(14, false);
            boolean z4 = entry.array.getBoolean(12, false);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -2561793317091789573L, 0, null, java.lang.String.valueOf(z), java.lang.String.valueOf(z2), java.lang.String.valueOf(z3), java.lang.String.valueOf(z4));
            if (z || z2) {
                return false;
            }
            if (z3 && getDisplayContent().mWallpaperController.getWallpaperTarget() != null) {
                return false;
            }
            if (!z4 || launchedFromSystemSurface()) {
                return true;
            }
            if (activityRecord != null && activityRecord.getActivityType() == 1 && activityRecord.mTransferringSplashScreenState == 0) {
                if (activityRecord.mStartingData == null && (activityRecord.mStartingWindow == null || activityRecord.mStartingSurface == null)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean addStartingWindow(java.lang.String str, int i, com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        if (!okToDisplay() || hasStartingWindow()) {
            return false;
        }
        com.android.server.wm.WindowState findMainWindow = findMainWindow(false);
        if (findMainWindow != null && findMainWindow.mWinAnimator.getShown()) {
            return false;
        }
        android.window.TaskSnapshot snapshot = this.mWmService.mTaskSnapshotController.getSnapshot(this.task.mTaskId, this.task.mUserId, false, false);
        int startingWindowType = getStartingWindowType(z, z2, z3, z4, z5, z7, snapshot);
        int makeStartingWindowTypeParameter = com.android.server.wm.StartingSurfaceController.makeStartingWindowTypeParameter(z, z2, z3, z4, z5, z6, startingWindowType == 2 && this.mWmService.mStartingSurfaceController.isExceptionApp(this.packageName, this.mTargetSdk, new java.util.function.Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.pm.ApplicationInfo lambda$addStartingWindow$3;
                lambda$addStartingWindow$3 = com.android.server.wm.ActivityRecord.this.lambda$addStartingWindow$3();
                return lambda$addStartingWindow$3;
            }
        }), z7, startingWindowType, isIconStylePreferred(i), this.packageName, this.mUserId);
        if (startingWindowType == 1) {
            if (isActivityTypeHome()) {
                this.mWmService.mTaskSnapshotController.removeSnapshotCache(this.task.mTaskId);
                if ((this.mDisplayContent.mAppTransition.getTransitFlags() & 2) == 0) {
                    return false;
                }
            }
            return createSnapshot(snapshot, makeStartingWindowTypeParameter);
        }
        if (i == 0 && this.theme != 0) {
            return false;
        }
        if (activityRecord != null && transferStartingWindow(activityRecord)) {
            return true;
        }
        if (startingWindowType != 2) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 7269690012594027154L, 0, null, null);
        this.mStartingData = new com.android.server.wm.SplashScreenStartingData(this.mWmService, i, makeStartingWindowTypeParameter);
        scheduleAddStartingWindow();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ApplicationInfo lambda$addStartingWindow$3() {
        android.content.pm.ActivityInfo resolveActivityInfo = this.intent.resolveActivityInfo(this.mAtmService.mContext.getPackageManager(), 128);
        if (resolveActivityInfo != null) {
            return resolveActivityInfo.applicationInfo;
        }
        return null;
    }

    private boolean createSnapshot(android.window.TaskSnapshot taskSnapshot, int i) {
        if (taskSnapshot == null) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -3432060893368468911L, 0, null, null);
        this.mStartingData = new com.android.server.wm.SnapshotStartingData(this.mWmService, taskSnapshot, i);
        if ((!this.mStyleFillsParent && this.task.getChildCount() > 1) || this.task.forAllLeafTaskFragments(new com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda5())) {
            associateStartingDataWithTask();
        }
        scheduleAddStartingWindow();
        return true;
    }

    void scheduleAddStartingWindow() {
        this.mAddStartingWindow.run();
    }

    private class AddStartingWindow implements java.lang.Runnable {
        private AddStartingWindow() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.StartingSurfaceController.StartingSurface startingSurface;
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityRecord.this.mWmService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityRecord.this.mStartingData == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 1789854065584848502L, 0, null, java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.StartingData startingData = com.android.server.wm.ActivityRecord.this.mStartingData;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 5659016061937922595L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(startingData));
                    try {
                        startingSurface = startingData.createStartingSurface(com.android.server.wm.ActivityRecord.this);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.w("ActivityTaskManager", "Exception when adding starting window", e);
                        startingSurface = null;
                    }
                    if (startingSurface != null) {
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.ActivityRecord.this.mWmService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                if (com.android.server.wm.ActivityRecord.this.mStartingData == null) {
                                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -9066702108316454290L, 0, null, java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this), java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this.mStartingData));
                                    com.android.server.wm.ActivityRecord.this.mStartingWindow = null;
                                    com.android.server.wm.ActivityRecord.this.mStartingData = null;
                                    z = true;
                                } else {
                                    com.android.server.wm.ActivityRecord.this.mStartingSurface = startingSurface;
                                    z = false;
                                }
                                if (!z) {
                                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 7506106334102501360L, 0, null, java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this), java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this.mStartingWindow), java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this.mStartingSurface));
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        if (z) {
                            startingSurface.remove(false, false);
                            return;
                        }
                        return;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 1048048288756547220L, 0, null, java.lang.String.valueOf(com.android.server.wm.ActivityRecord.this));
                } finally {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }
    }

    private int getStartingWindowType(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, android.window.TaskSnapshot taskSnapshot) {
        com.android.server.wm.ActivityRecord activity;
        if (!z && z2 && z3 && !z5 && this.task.intent != null && this.mActivityComponent.equals(this.task.intent.getComponent()) && (activity = this.task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).attachedToProcess();
            }
        })) != null) {
            return (activity.isSnapshotCompatible(taskSnapshot) && this.mDisplayContent.getDisplayRotation().rotationForOrientation(getOverrideOrientation(), this.mDisplayContent.getRotation()) == taskSnapshot.getRotation()) ? 1 : 0;
        }
        boolean isActivityTypeHome = isActivityTypeHome();
        if ((z || !z3 || (z2 && !z5)) && !isActivityTypeHome) {
            return 2;
        }
        if (z2) {
            if (z4) {
                if (isSnapshotCompatible(taskSnapshot)) {
                    return 1;
                }
                if (!isActivityTypeHome) {
                    return 2;
                }
            }
            if (!z6 && !isActivityTypeHome) {
                return 2;
            }
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSnapshotCompatible(android.window.TaskSnapshot taskSnapshot) {
        return taskSnapshot != null && isSnapshotComponentCompatible(taskSnapshot) && isSnapshotOrientationCompatible(taskSnapshot);
    }

    boolean isSnapshotComponentCompatible(@android.annotation.NonNull android.window.TaskSnapshot taskSnapshot) {
        return taskSnapshot.getTopActivityComponent().equals(this.mActivityComponent);
    }

    boolean isSnapshotOrientationCompatible(@android.annotation.NonNull android.window.TaskSnapshot taskSnapshot) {
        int rotationForActivityInDifferentOrientation = this.mDisplayContent.rotationForActivityInDifferentOrientation(this);
        int rotation = this.task.getWindowConfiguration().getRotation();
        if (rotationForActivityInDifferentOrientation == -1) {
            rotationForActivityInDifferentOrientation = rotation;
        }
        if (taskSnapshot.getRotation() != rotationForActivityInDifferentOrientation) {
            return false;
        }
        android.graphics.Rect bounds = this.task.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        android.graphics.Point taskSize = taskSnapshot.getTaskSize();
        if (java.lang.Math.abs(rotation - rotationForActivityInDifferentOrientation) % 2 == 1) {
            width = height;
            height = width;
        }
        return java.lang.Math.abs((((float) taskSize.x) / ((float) java.lang.Math.max(taskSize.y, 1))) - (((float) width) / ((float) java.lang.Math.max(height, 1)))) <= 0.01f;
    }

    void setCustomizeSplashScreenExitAnimation(boolean z) {
        if (this.mHandleExitSplashScreen == z) {
            return;
        }
        this.mHandleExitSplashScreen = z;
    }

    private void scheduleTransferSplashScreenTimeout() {
        this.mAtmService.mH.postDelayed(this.mTransferSplashScreenTimeoutRunnable, 2000L);
    }

    private void removeTransferSplashScreenTimeout() {
        this.mAtmService.mH.removeCallbacks(this.mTransferSplashScreenTimeoutRunnable);
    }

    private boolean transferSplashScreenIfNeeded() {
        if (this.finishing || !this.mHandleExitSplashScreen || this.mStartingSurface == null || this.mStartingWindow == null || this.mTransferringSplashScreenState == 3) {
            return false;
        }
        if (this.mStartingData != null && this.mStartingData.mResizedFromTransfer) {
            return false;
        }
        if (isTransferringSplashScreen()) {
            return true;
        }
        if (this.mStartingData != null && this.mStartingData.mWaitForSyncTransactionCommit) {
            this.mStartingData.mRemoveAfterTransaction = 2;
            return true;
        }
        requestCopySplashScreen();
        return isTransferringSplashScreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTransferringSplashScreen() {
        return this.mTransferringSplashScreenState == 2 || this.mTransferringSplashScreenState == 1;
    }

    private void requestCopySplashScreen() {
        this.mTransferringSplashScreenState = 1;
        if (this.mStartingSurface == null || !this.mAtmService.mTaskOrganizerController.copySplashScreenView(getTask(), this.mStartingSurface.mTaskOrganizer)) {
            this.mTransferringSplashScreenState = 3;
            removeStartingWindow();
        }
        scheduleTransferSplashScreenTimeout();
    }

    void onCopySplashScreenFinish(@android.annotation.Nullable android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
        android.view.SurfaceControl surfaceControl;
        removeTransferSplashScreenTimeout();
        if (splashScreenViewParcelable == null || this.mTransferringSplashScreenState != 1 || this.mStartingWindow == null || this.mStartingWindow.mRemoved || this.finishing) {
            surfaceControl = null;
        } else {
            surfaceControl = com.android.server.wm.TaskOrganizerController.applyStartingWindowAnimation(this.mStartingWindow);
        }
        if (surfaceControl == null) {
            if (splashScreenViewParcelable != null) {
                splashScreenViewParcelable.clearIfNeeded();
            }
            this.mTransferringSplashScreenState = 3;
            removeStartingWindow();
            return;
        }
        try {
            this.mTransferringSplashScreenState = 2;
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.TransferSplashScreenViewStateItem.obtain(this.token, splashScreenViewParcelable, surfaceControl));
            scheduleTransferSplashScreenTimeout();
        } catch (java.lang.Exception e) {
            android.util.Slog.w("ActivityTaskManager", "onCopySplashScreenComplete fail: " + this);
            this.mStartingWindow.cancelAnimation();
            splashScreenViewParcelable.clearIfNeeded();
            this.mTransferringSplashScreenState = 3;
        }
    }

    private void onSplashScreenAttachComplete() {
        removeTransferSplashScreenTimeout();
        if (this.mStartingWindow != null) {
            this.mStartingWindow.cancelAnimation();
            this.mStartingWindow.hide(false, false);
        }
        this.mTransferringSplashScreenState = 3;
        removeStartingWindowAnimation(false);
    }

    void cleanUpSplashScreen() {
        if (!this.mHandleExitSplashScreen || this.startingMoved) {
            return;
        }
        if (this.mTransferringSplashScreenState == 3 || this.mTransferringSplashScreenState == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -1298801500610545721L, 0, null, java.lang.String.valueOf(this));
            this.mAtmService.mTaskOrganizerController.onAppSplashScreenViewRemoved(getTask(), this.mStartingSurface != null ? this.mStartingSurface.mTaskOrganizer : null);
        }
    }

    boolean isStartingWindowDisplayed() {
        com.android.server.wm.StartingData startingData;
        if (this.mStartingData != null) {
            startingData = this.mStartingData;
        } else {
            startingData = this.task != null ? this.task.mSharedStartingData : null;
        }
        return startingData != null && startingData.mIsDisplayed;
    }

    void attachStartingWindow(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        windowState.mStartingData = this.mStartingData;
        this.mStartingWindow = windowState;
        if (this.mStartingData != null) {
            if (this.mStartingData.mAssociatedTask != null) {
                attachStartingSurfaceToAssociatedTask();
            } else if (isEmbedded()) {
                associateStartingWindowWithTaskIfNeeded();
            }
            if (this.mTransitionController.isCollecting()) {
                this.mStartingData.mTransitionId = this.mTransitionController.getCollectingTransitionId();
            }
        }
    }

    private void attachStartingSurfaceToAssociatedTask() {
        if (this.mSyncState == 0 && isEmbedded()) {
            this.mTransitionController.collect(this);
        }
        com.android.server.wm.WindowContainer.overrideConfigurationPropagation(this.mStartingWindow, this.mStartingData.mAssociatedTask);
        getSyncTransaction().reparent(this.mStartingWindow.mSurfaceControl, this.mStartingData.mAssociatedTask.mSurfaceControl);
    }

    private void associateStartingDataWithTask() {
        this.mStartingData.mAssociatedTask = this.task;
        this.task.mSharedStartingData = this.mStartingData;
    }

    void associateStartingWindowWithTaskIfNeeded() {
        if (this.mStartingWindow == null || this.mStartingData == null || this.mStartingData.mAssociatedTask != null) {
            return;
        }
        associateStartingDataWithTask();
        attachStartingSurfaceToAssociatedTask();
    }

    void removeStartingWindow() {
        boolean isEligibleForLetterboxEducation = isEligibleForLetterboxEducation();
        if (transferSplashScreenIfNeeded()) {
            return;
        }
        removeStartingWindowAnimation(true);
        com.android.server.wm.Task task = getTask();
        if (isEligibleForLetterboxEducation != isEligibleForLetterboxEducation() && task != null) {
            task.dispatchTaskInfoChangedIfNeeded(true);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void waitForSyncTransactionCommit(android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet) {
        super.waitForSyncTransactionCommit(arraySet);
        if (this.mStartingData != null) {
            this.mStartingData.mWaitForSyncTransactionCommit = true;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void onSyncTransactionCommitted(android.view.SurfaceControl.Transaction transaction) {
        super.onSyncTransactionCommitted(transaction);
        if (this.mStartingData == null) {
            return;
        }
        com.android.server.wm.StartingData startingData = this.mStartingData;
        startingData.mWaitForSyncTransactionCommit = false;
        if (startingData.mRemoveAfterTransaction == 1) {
            removeStartingWindowAnimation(startingData.mPrepareRemoveAnimation);
        } else if (startingData.mRemoveAfterTransaction == 2) {
            removeStartingWindow();
        }
    }

    void removeStartingWindowAnimation(boolean z) {
        boolean z2 = false;
        this.mTransferringSplashScreenState = 0;
        if (this.task != null) {
            this.task.mSharedStartingData = null;
        }
        if (this.mStartingWindow == null) {
            if (this.mStartingData != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -1948849214526113495L, 0, null, java.lang.String.valueOf(this));
                this.mStartingData = null;
                this.mStartingSurface = null;
                return;
            }
            return;
        }
        if (this.mStartingData != null) {
            if (this.mStartingData.mWaitForSyncTransactionCommit || this.mTransitionController.isCollecting(this)) {
                this.mStartingData.mRemoveAfterTransaction = 1;
                this.mStartingData.mPrepareRemoveAnimation = z;
                return;
            }
            if (z && this.mStartingData.needRevealAnimation() && this.mStartingWindow.isVisibleByPolicy()) {
                z2 = true;
            }
            boolean hasImeSurface = this.mStartingData.hasImeSurface();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 5545923784327902026L, 48, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mStartingWindow), java.lang.Boolean.valueOf(z2), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
            com.android.server.wm.StartingSurfaceController.StartingSurface startingSurface = this.mStartingSurface;
            this.mStartingData = null;
            this.mStartingSurface = null;
            this.mStartingWindow = null;
            this.mTransitionChangeFlags &= -9;
            if (startingSurface == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -5150982660941074218L, 0, null, null);
                return;
            } else {
                startingSurface.remove(z2, hasImeSurface);
                return;
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -2178757341169633804L, 0, null, java.lang.String.valueOf(this));
    }

    void reparent(com.android.server.wm.TaskFragment taskFragment, int i, java.lang.String str) {
        if (getParent() == null) {
            android.util.Slog.w("ActivityTaskManager", "reparent: Attempted to reparent non-existing app token: " + this.token);
            return;
        }
        if (getTaskFragment() == taskFragment) {
            throw new java.lang.IllegalArgumentException(str + ": task fragment =" + taskFragment + " is already the parent of r=" + this);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 5521236266092347335L, 20, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(this.task.mTaskId), java.lang.Long.valueOf(i));
        reparent(taskFragment, i);
    }

    static boolean isHomeIntent(android.content.Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && (intent.hasCategory("android.intent.category.HOME") || intent.hasCategory("android.intent.category.SECONDARY_HOME")) && intent.getCategories().size() == 1 && intent.getData() == null && intent.getType() == null;
    }

    static boolean isMainIntent(android.content.Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.LAUNCHER") && intent.getCategories().size() == 1 && intent.getData() == null && intent.getType() == null;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean canLaunchHomeActivity(int i, com.android.server.wm.ActivityRecord activityRecord) {
        if (i == 1000 || i == 0) {
            return true;
        }
        com.android.server.wm.RecentTasks recentTasks = this.mTaskSupervisor.mService.getRecentTasks();
        if (recentTasks == null || !recentTasks.isCallerRecents(i)) {
            return activityRecord != null && activityRecord.isResolverOrDelegateActivity();
        }
        return true;
    }

    private boolean canLaunchAssistActivity(java.lang.String str) {
        android.content.ComponentName componentName = this.mAtmService.mActiveVoiceInteractionServiceComponent;
        if (componentName != null) {
            return componentName.getPackageName().equals(str);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0070, code lost:
    
        if (android.service.dreams.DreamActivity.class.getName() == r2.info.name) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setActivityType(boolean z, int i, android.content.Intent intent, android.app.ActivityOptions activityOptions, com.android.server.wm.ActivityRecord activityRecord) {
        int i2 = 4;
        if ((!z || canLaunchHomeActivity(i, activityRecord)) && isHomeIntent(intent) && !isResolverOrDelegateActivity()) {
            if (this.info.resizeMode == 4 || this.info.resizeMode == 1) {
                this.info.resizeMode = 0;
            }
            i2 = 2;
        } else if (this.mAtmService.getRecentTasks().isRecentsComponent(this.mActivityComponent, this.info.applicationInfo.uid)) {
            i2 = 3;
        } else if (activityOptions == null || activityOptions.getLaunchActivityType() != 4 || !canLaunchAssistActivity(this.launchedFromPackage)) {
            if (activityOptions != null) {
                i2 = 5;
                if (activityOptions.getLaunchActivityType() == 5) {
                    if (this.mAtmService.canLaunchDreamActivity(this.launchedFromPackage)) {
                    }
                }
            }
            i2 = 0;
        }
        setActivityType(i2);
    }

    void setTaskToAffiliateWith(com.android.server.wm.Task task) {
        if (this.launchMode != 3 && this.launchMode != 2) {
            this.task.setTaskToAffiliateWith(task);
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask() {
        if (this.task != null) {
            return this.task.getRootTask();
        }
        return null;
    }

    int getRootTaskId() {
        if (this.task != null) {
            return this.task.getRootTaskId();
        }
        return -1;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getOrganizedTask() {
        if (this.task != null) {
            return this.task.getOrganizedTask();
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getOrganizedTaskFragment() {
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            return taskFragment.getOrganizedTaskFragment();
        }
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isEmbedded() {
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        return taskFragment != null && taskFragment.isEmbedded();
    }

    boolean isUntrustedEmbeddingStateSharingAllowed() {
        if (!com.android.window.flags.Flags.untrustedEmbeddingStateSharing()) {
            return false;
        }
        return this.mAllowUntrustedEmbeddingStateSharing;
    }

    private boolean getAllowUntrustedEmbeddingStateSharingProperty() {
        if (!com.android.window.flags.Flags.untrustedEmbeddingStateSharing()) {
            return false;
        }
        try {
            return this.mAtmService.mContext.getPackageManager().getProperty("android.window.PROPERTY_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING_STATE_SHARING", this.mActivityComponent).getBoolean();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    boolean isEmbeddedInHostContainer() {
        com.android.server.wm.TaskFragment organizedTaskFragment = getOrganizedTaskFragment();
        return organizedTaskFragment != null && organizedTaskFragment.isEmbeddedWithBoundsOverride();
    }

    @android.annotation.NonNull
    android.window.ActivityWindowInfo getActivityWindowInfo() {
        if (!com.android.window.flags.Flags.activityWindowInfoFlag() || !isAttached()) {
            return this.mTmpActivityWindowInfo;
        }
        this.mTmpActivityWindowInfo.set(isEmbeddedInHostContainer(), getTask().getBounds(), getTaskFragment().getBounds());
        return this.mTmpActivityWindowInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    public com.android.server.wm.TaskDisplayArea getDisplayArea() {
        return (com.android.server.wm.TaskDisplayArea) super.getDisplayArea();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean providesOrientation() {
        return this.mStyleFillsParent || this.mOccludesParent;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return occludesParent(true);
    }

    boolean occludesParent() {
        return occludesParent(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean occludesParent(boolean z) {
        if (z || !this.finishing) {
            return this.mOccludesParent || showWallpaper();
        }
        return false;
    }

    boolean setOccludesParent(boolean z) {
        boolean z2 = z != this.mOccludesParent;
        this.mOccludesParent = z;
        setMainWindowOpaque(z);
        if (z2 && this.task != null && !z) {
            getRootTask().convertActivityToTranslucent(this);
        }
        if (z2 || !z) {
            this.mRootWindowContainer.ensureActivitiesVisible();
        }
        return z2;
    }

    void setMainWindowOpaque(boolean z) {
        com.android.server.wm.WindowState findMainWindow = findMainWindow();
        if (findMainWindow == null) {
            return;
        }
        findMainWindow.mWinAnimator.setOpaqueLocked(z & (!android.graphics.PixelFormat.formatHasAlpha(findMainWindow.getAttrs().format)));
    }

    void takeFromHistory() {
        if (this.inHistory) {
            this.inHistory = false;
            if (this.task != null && !this.finishing) {
                this.task = null;
            }
            abortAndClearOptionsAnimation();
        }
    }

    boolean isInHistory() {
        return this.inHistory;
    }

    boolean isInRootTaskLocked() {
        com.android.server.wm.Task rootTask = getRootTask();
        return (rootTask == null || rootTask.isInTask(this) == null) ? false : true;
    }

    boolean isPersistable() {
        return (this.info.persistableMode == 0 || this.info.persistableMode == 2) && (this.intent == null || (this.intent.getFlags() & 8388608) == 0);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isFocusable() {
        return super.isFocusable() && (canReceiveKeys() || isAlwaysFocusable());
    }

    boolean canReceiveKeys() {
        return getWindowConfiguration().canReceiveKeys() && !this.mWaitForEnteringPinnedMode;
    }

    boolean isResizeable() {
        return isResizeable(true);
    }

    boolean isResizeable(boolean z) {
        return this.mAtmService.mForceResizableActivities || android.content.pm.ActivityInfo.isResizeableMode(this.info.resizeMode) || (this.info.supportsPictureInPicture() && z) || isEmbedded();
    }

    boolean canForceResizeNonResizable(int i) {
        boolean supportsMultiWindow;
        if (i == 2 && this.info.supportsPictureInPicture()) {
            return false;
        }
        if (this.task != null) {
            supportsMultiWindow = this.task.supportsMultiWindow() || supportsMultiWindow();
        } else {
            supportsMultiWindow = supportsMultiWindow();
        }
        return ((android.app.WindowConfiguration.inMultiWindowMode(i) && supportsMultiWindow && !this.mAtmService.mForceResizableActivities) || this.info.resizeMode == 2 || this.info.resizeMode == 1) ? false : true;
    }

    boolean supportsPictureInPicture() {
        return this.mAtmService.mSupportsPictureInPicture && isActivityTypeStandardOrUndefined() && this.info.supportsPictureInPicture();
    }

    boolean supportsFreeform() {
        return supportsFreeformInDisplayArea(getDisplayArea());
    }

    boolean supportsFreeformInDisplayArea(@android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return this.mAtmService.mSupportsFreeformWindowManagement && supportsMultiWindowInDisplayArea(taskDisplayArea);
    }

    boolean supportsMultiWindow() {
        return supportsMultiWindowInDisplayArea(getDisplayArea());
    }

    boolean supportsMultiWindowInDisplayArea(@android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (isActivityTypeHome() || !this.mAtmService.mSupportsMultiWindow || taskDisplayArea == null) {
            return false;
        }
        if (!isResizeable() && !taskDisplayArea.supportsNonResizableMultiWindow()) {
            return false;
        }
        android.content.pm.ActivityInfo.WindowLayout windowLayout = this.info.windowLayout;
        return windowLayout == null || taskDisplayArea.supportsActivityMinWidthHeightMultiWindow(windowLayout.minWidth, windowLayout.minHeight, this.info);
    }

    boolean canBeLaunchedOnDisplay(int i) {
        return this.mAtmService.mTaskSupervisor.canPlaceEntityOnDisplay(i, this.launchedFromPid, this.launchedFromUid, this.info);
    }

    boolean checkEnterPictureInPictureState(java.lang.String str, boolean z) {
        if (!supportsPictureInPicture() || !checkEnterPictureInPictureAppOpsState() || this.mAtmService.shouldDisableNonVrUiLocked()) {
            return false;
        }
        if (this.mDisplayContent != null && !this.mDisplayContent.mDwpcHelper.isEnteringPipAllowed(getUid())) {
            android.util.Slog.w("ActivityTaskManager", "Display " + this.mDisplayContent.getDisplayId() + " doesn't support enter picture-in-picture mode. caller = " + str);
            return false;
        }
        boolean z2 = this.mAtmService.getLockTaskModeState() != 0;
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        boolean z3 = displayArea != null && displayArea.hasPinnedTask();
        boolean z4 = (isKeyguardLocked() || z2) ? false : true;
        if (z && z3) {
            return false;
        }
        switch (com.android.server.wm.ActivityRecord.AnonymousClass6.$SwitchMap$com$android$server$wm$ActivityRecord$State[this.mState.ordinal()]) {
            case 1:
                if (z2) {
                    return false;
                }
                return this.supportsEnterPipOnTaskSwitch || !z;
            case 2:
            case 3:
                return z4 && !z3 && this.supportsEnterPipOnTaskSwitch;
            case 4:
                return this.supportsEnterPipOnTaskSwitch && z4 && !z3;
            default:
                return false;
        }
    }

    /* renamed from: com.android.server.wm.ActivityRecord$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$wm$ActivityRecord$State = new int[com.android.server.wm.ActivityRecord.State.values().length];

        static {
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.RESUMED.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.PAUSING.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.PAUSED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.STOPPING.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.STARTED.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.STOPPED.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.DESTROYED.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.DESTROYING.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$server$wm$ActivityRecord$State[com.android.server.wm.ActivityRecord.State.INITIALIZING.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
        }
    }

    void setWillCloseOrEnterPip(boolean z) {
        this.mWillCloseOrEnterPip = z;
    }

    boolean willCloseOrEnterPip() {
        return this.mWillCloseOrEnterPip;
    }

    boolean checkEnterPictureInPictureAppOpsState() {
        return this.mAtmService.getAppOpsManager().checkOpNoThrow(67, this.info.applicationInfo.uid, this.packageName) == 0;
    }

    private boolean isAlwaysFocusable() {
        return (this.info.flags & 262144) != 0;
    }

    boolean windowsAreFocusable() {
        return windowsAreFocusable(false);
    }

    boolean windowsAreFocusable(boolean z) {
        if (!z && this.mTargetSdk < 29) {
            com.android.server.wm.ActivityRecord activityRecord = this.mWmService.mRoot.mTopFocusedAppByProcess.get(java.lang.Integer.valueOf(getPid()));
            if (activityRecord != null && activityRecord != this) {
                return false;
            }
        }
        return (canReceiveKeys() || isAlwaysFocusable()) && isAttached();
    }

    boolean moveFocusableActivityToTop(java.lang.String str) {
        int i;
        if (!isFocusable()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -9024836052864189016L, 0, null, java.lang.String.valueOf(this));
            return false;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask == null) {
            android.util.Slog.w("ActivityTaskManager", "moveFocusableActivityToTop: invalid root task: activity=" + this + " task=" + this.task);
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.mFocusedApp;
        if (this.mRootWindowContainer.getTopFocusedDisplayContent() != null) {
            i = this.mRootWindowContainer.getTopFocusedDisplayContent().getDisplayId();
        } else {
            i = -1;
        }
        if (activityRecord != null && activityRecord.task == this.task && i == this.mDisplayContent.getDisplayId()) {
            if (this.task == this.mDisplayContent.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda29
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$moveFocusableActivityToTop$4;
                    lambda$moveFocusableActivityToTop$4 = com.android.server.wm.ActivityRecord.lambda$moveFocusableActivityToTop$4((com.android.server.wm.Task) obj);
                    return lambda$moveFocusableActivityToTop$4;
                }
            }, true)) {
                if (activityRecord == this) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 134255351804410010L, 0, null, java.lang.String.valueOf(this));
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -1058622321669556178L, 0, null, java.lang.String.valueOf(this));
                    this.mDisplayContent.setFocusedApp(this);
                    this.mAtmService.mWindowManager.updateFocusedWindowLocked(0, true);
                }
                return !isState(com.android.server.wm.ActivityRecord.State.RESUMED);
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 731006689098152100L, 0, null, java.lang.String.valueOf(this));
        rootTask.moveToFront(str, this.task);
        if (this.mState == com.android.server.wm.ActivityRecord.State.RESUMED && this.mRootWindowContainer.getTopResumedActivity() == this) {
            this.mAtmService.setLastResumedActivityUncheckLocked(this, str);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$moveFocusableActivityToTop$4(com.android.server.wm.Task task) {
        return task.isLeafTask() && task.isFocusable() && !task.inPinnedWindowingMode();
    }

    void finishIfSubActivity(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i) {
        if (this.resultTo != activityRecord || this.requestCode != i || !java.util.Objects.equals(this.resultWho, str)) {
            return;
        }
        finishIfPossible("request-sub", false);
    }

    boolean finishIfSameAffinity(com.android.server.wm.ActivityRecord activityRecord) {
        if (!java.util.Objects.equals(activityRecord.taskAffinity, this.taskAffinity)) {
            return true;
        }
        activityRecord.finishIfPossible("request-affinity", true);
        return false;
    }

    private void finishActivityResults(final int i, final android.content.Intent intent, final com.android.server.uri.NeededUriGrants neededUriGrants) {
        if (this.resultTo != null) {
            if (this.resultTo.mUserId != this.mUserId && intent != null) {
                intent.prepareToLeaveUser(this.mUserId);
            }
            if (this.info.applicationInfo.uid > 0) {
                this.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(neededUriGrants, this.resultTo.getUriPermissionsLocked());
            }
            final android.os.Binder binder = new android.os.Binder();
            if (android.security.Flags.contentUriPermissionApis()) {
                try {
                    this.resultTo.computeCallerInfo(binder, intent, getUid(), this.mAtmService.getPackageManager().getNameForUid(getUid()), false);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            if (this.mForceSendResultForMediaProjection || this.resultTo.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                final com.android.server.wm.ActivityRecord activityRecord = this.resultTo;
                this.mAtmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.ActivityRecord.this.lambda$finishActivityResults$5(activityRecord, i, intent, binder, neededUriGrants);
                    }
                });
            } else {
                this.resultTo.addResultLocked(this, this.resultWho, this.requestCode, i, intent, binder);
            }
            this.resultTo = null;
        }
        this.results = null;
        this.pendingResults = null;
        this.newIntents = null;
        setSavedState(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishActivityResults$5(com.android.server.wm.ActivityRecord activityRecord, int i, android.content.Intent intent, android.os.IBinder iBinder, com.android.server.uri.NeededUriGrants neededUriGrants) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                activityRecord.sendResult(getUid(), this.resultWho, this.requestCode, i, intent, iBinder, neededUriGrants, this.mForceSendResultForMediaProjection);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.server.wm.ActivityRecord.FinishRequest
    int finishIfPossible(java.lang.String str, boolean z) {
        return finishIfPossible(0, null, null, str, z);
    }

    @com.android.server.wm.ActivityRecord.FinishRequest
    int finishIfPossible(int i, android.content.Intent intent, com.android.server.uri.NeededUriGrants neededUriGrants, java.lang.String str, boolean z) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3707721620395081349L, 4, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(i), java.lang.String.valueOf(intent), java.lang.String.valueOf(str));
        if (this.finishing) {
            android.util.Slog.w("ActivityTaskManager", "Duplicate finish request for r=" + this);
            return 0;
        }
        if (!isInRootTaskLocked()) {
            android.util.Slog.w("ActivityTaskManager", "Finish request when not in root task for r=" + this);
            return 0;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        boolean z2 = (isState(com.android.server.wm.ActivityRecord.State.RESUMED) || rootTask.getTopResumedActivity() == null) && rootTask.isFocusedRootTaskOnDisplay() && !this.task.isClearingToReuseTask();
        boolean z3 = z2 && this.mRootWindowContainer.isTopDisplayFocusedRootTask(rootTask);
        this.mAtmService.deferWindowLayout();
        try {
            this.mTaskSupervisor.mNoHistoryActivities.remove(this);
            makeFinishingLocked();
            com.android.server.wm.Task task = getTask();
            com.android.server.wm.EventLogTags.writeWmFinishActivity(this.mUserId, java.lang.System.identityHashCode(this), task.mTaskId, this.shortComponentName, str);
            com.android.server.wm.ActivityRecord activityAbove = task.getActivityAbove(this);
            if (activityAbove != null && (this.intent.getFlags() & 524288) != 0) {
                activityAbove.intent.addFlags(524288);
            }
            pauseKeyDispatchingLocked();
            if (z2 && task.topRunningActivity(true) == null) {
                task.adjustFocusToNextFocusableTask("finish-top", false, z3);
            }
            finishActivityResults(i, intent, neededUriGrants);
            boolean z4 = task.getTopNonFinishingActivity() == null && !task.isClearingToReuseTask();
            com.android.server.wm.Transition requestCloseTransitionIfNeeded = this.mTransitionController.requestCloseTransitionIfNeeded(z4 ? task : this);
            if (isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                if (z4) {
                    this.mAtmService.getTaskChangeNotificationController().notifyTaskRemovalStarted(task.getTaskInfo());
                }
                this.mDisplayContent.prepareAppTransition(2);
                if (!this.mTransitionController.isShellTransitionsEnabled() && !task.isAnimatingByRecents()) {
                    android.util.ArraySet<com.android.server.wm.Task> newArraySet = com.google.android.collect.Sets.newArraySet(new com.android.server.wm.Task[]{task});
                    this.mAtmService.mWindowManager.mTaskSnapshotController.snapshotTasks(newArraySet);
                    this.mAtmService.mWindowManager.mTaskSnapshotController.addSkipClosingAppSnapshotTasks(newArraySet);
                }
                setVisibility(false);
                if (this.mLastImeShown && this.mTransitionController.isShellTransitionsEnabled() && (activityRecord = task.topRunningActivity()) != null) {
                    activityRecord.mLastImeShown = true;
                }
                if (getTaskFragment().getPausingActivity() == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -3691592300155948194L, 0, null, java.lang.String.valueOf(this));
                    getTaskFragment().startPausing(false, false, null, "finish");
                }
                if (z4) {
                    this.mAtmService.getLockTaskController().clearLockedTask(task);
                    if (z2) {
                        this.mNeedsZBoost = true;
                        this.mDisplayContent.assignWindowLayers(false);
                    }
                }
            } else {
                if (!isState(com.android.server.wm.ActivityRecord.State.PAUSING)) {
                    if (this.mVisibleRequested) {
                        if (this.mTransitionController.isShellTransitionsEnabled()) {
                            setVisibility(false);
                            if (requestCloseTransitionIfNeeded != null) {
                                requestCloseTransitionIfNeeded.setReady(this.mDisplayContent, true);
                            }
                        } else {
                            prepareActivityHideTransitionAnimation();
                        }
                    }
                    boolean z5 = completeFinishing("finishIfPossible") == null;
                    if (z && isState(com.android.server.wm.ActivityRecord.State.STOPPING)) {
                        this.mAtmService.updateOomAdj();
                    }
                    if (task.onlyHasTaskOverlayActivities(false)) {
                        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda17
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                ((com.android.server.wm.ActivityRecord) obj).prepareActivityHideTransitionAnimationIfOvarlay();
                            }
                        });
                    }
                    int i2 = z5 ? 2 : 1;
                    this.mAtmService.continueWindowLayout();
                    return i2;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 5813636479397543744L, 0, null, java.lang.String.valueOf(this));
            }
            this.mAtmService.continueWindowLayout();
            return 1;
        } catch (java.lang.Throwable th) {
            this.mAtmService.continueWindowLayout();
            throw th;
        }
    }

    void setForceSendResultForMediaProjection() {
        this.mForceSendResultForMediaProjection = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareActivityHideTransitionAnimationIfOvarlay() {
        if (this.mTaskOverlay) {
            prepareActivityHideTransitionAnimation();
        }
    }

    private void prepareActivityHideTransitionAnimation() {
        com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
        displayContent.prepareAppTransition(2);
        setVisibility(false);
        displayContent.executeAppTransition();
    }

    com.android.server.wm.ActivityRecord completeFinishing(java.lang.String str) {
        return completeFinishing(true, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    com.android.server.wm.ActivityRecord completeFinishing(boolean z, java.lang.String str) {
        boolean z2;
        boolean z3;
        if (!this.finishing || isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
            throw new java.lang.IllegalArgumentException("Activity must be finishing and not resumed to complete, r=" + this + ", finishing=" + this.finishing + ", state=" + this.mState);
        }
        if (isState(com.android.server.wm.ActivityRecord.State.PAUSING)) {
            return this;
        }
        boolean z4 = true;
        boolean z5 = false;
        boolean z6 = this.mVisibleRequested || isState(com.android.server.wm.ActivityRecord.State.PAUSED, com.android.server.wm.ActivityRecord.State.STARTED);
        if (z && z6 && !this.task.isClearingToReuseTask()) {
            if (occludesParent(true)) {
                z3 = true;
            } else if (isKeyguardLocked() && this.mTaskSupervisor.getKeyguardController().topActivityOccludesKeyguard(this)) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                this.mDisplayContent.ensureActivitiesVisible(null, true);
            }
        }
        com.android.server.wm.ActivityRecord activityRecord = getDisplayArea().topRunningActivity(true);
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (activityRecord != null && taskFragment != null && taskFragment.isEmbedded()) {
            com.android.server.wm.TaskFragment organizedTaskFragment = taskFragment.getOrganizedTaskFragment();
            com.android.server.wm.TaskFragment adjacentTaskFragment = organizedTaskFragment != null ? organizedTaskFragment.getAdjacentTaskFragment() : null;
            if (adjacentTaskFragment != null && activityRecord.isDescendantOf(adjacentTaskFragment) && organizedTaskFragment.topRunningActivity() == null) {
                z2 = organizedTaskFragment.isDelayLastActivityRemoval();
                if (activityRecord != null || (activityRecord.nowVisible && activityRecord.isVisibleRequested())) {
                    z4 = false;
                }
                if (z4 && this.mDisplayContent.isSleeping() && activityRecord == activityRecord.getTaskFragment().mLastPausedActivity) {
                    activityRecord.getTaskFragment().clearLastPausedActivity();
                }
                if (!z6) {
                    if (z4 || z2) {
                        addToStopping(false, false, "completeFinishing");
                        setState(com.android.server.wm.ActivityRecord.State.STOPPING, "completeFinishing");
                    } else if (!addToFinishingAndWaitForIdle()) {
                        z5 = destroyIfPossible(str);
                    }
                } else {
                    addToFinishingAndWaitForIdle();
                    z5 = destroyIfPossible(str);
                }
                if (z5) {
                    return this;
                }
                return null;
            }
        }
        z2 = false;
        if (activityRecord != null) {
        }
        z4 = false;
        if (z4) {
            activityRecord.getTaskFragment().clearLastPausedActivity();
        }
        if (!z6) {
        }
        if (z5) {
        }
    }

    boolean destroyIfPossible(java.lang.String str) {
        setState(com.android.server.wm.ActivityRecord.State.FINISHING, "destroyIfPossible");
        this.mTaskSupervisor.mStoppingActivities.remove(this);
        com.android.server.wm.Task rootTask = getRootTask();
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        com.android.server.wm.ActivityRecord activityRecord = displayArea.topRunningActivity();
        if (activityRecord == null && rootTask.isFocusedRootTaskOnDisplay() && displayArea.getOrCreateRootHomeTask() != null) {
            addToFinishingAndWaitForIdle();
            return false;
        }
        makeFinishingLocked();
        boolean destroyImmediately = destroyImmediately("finish-imm:" + str);
        if (activityRecord == null) {
            this.mRootWindowContainer.ensureVisibilityAndConfig(activityRecord, getDisplayId(), true);
        }
        if (destroyImmediately) {
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTAINERS, -2989211291975863399L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(destroyImmediately));
        return destroyImmediately;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean addToFinishingAndWaitForIdle() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3169053633576517098L, 0, null, java.lang.String.valueOf(this));
        setState(com.android.server.wm.ActivityRecord.State.FINISHING, "addToFinishingAndWaitForIdle");
        if (!this.mTaskSupervisor.mFinishingActivities.contains(this)) {
            this.mTaskSupervisor.mFinishingActivities.add(this);
        }
        resumeKeyDispatchingLocked();
        return this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    boolean destroyImmediately(java.lang.String str) {
        boolean z;
        if (isState(com.android.server.wm.ActivityRecord.State.DESTROYING, com.android.server.wm.ActivityRecord.State.DESTROYED)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 9050478058743283018L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(str));
            return false;
        }
        com.android.server.wm.EventLogTags.writeWmDestroyActivity(this.mUserId, java.lang.System.identityHashCode(this), this.task.mTaskId, this.shortComponentName, str);
        cleanUp(false, false);
        boolean z2 = true;
        if (hasProcess()) {
            this.app.removeActivity(this, true);
            if (!this.app.hasActivities()) {
                this.mAtmService.clearHeavyWeightProcessIfEquals(this.app);
            }
            try {
                this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.DestroyActivityItem.obtain(this.token, this.finishing, this.configChangeFlags));
            } catch (java.lang.Exception e) {
                if (this.finishing) {
                    removeFromHistory(str + " exceptionInScheduleDestroy");
                    z = true;
                }
            }
            z = false;
            z2 = false;
            this.nowVisible = false;
            if (this.finishing && !z2) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 5672598223877126839L, 0, null, java.lang.String.valueOf(this));
                setState(com.android.server.wm.ActivityRecord.State.DESTROYING, "destroyActivityLocked. finishing and not skipping destroy");
                this.mAtmService.mH.postDelayed(this.mDestroyTimeoutRunnable, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -1834399855266808961L, 0, null, java.lang.String.valueOf(this));
                setState(com.android.server.wm.ActivityRecord.State.DESTROYED, "destroyActivityLocked. not finishing or skipping destroy");
                detachFromProcess();
            }
            z2 = z;
        } else if (this.finishing) {
            removeFromHistory(str + " hadNoApp");
        } else {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3282063745558462269L, 0, null, java.lang.String.valueOf(this));
            setState(com.android.server.wm.ActivityRecord.State.DESTROYED, "destroyActivityLocked. not finishing and had no app");
            z2 = false;
        }
        this.configChangeFlags = 0;
        return z2;
    }

    void removeFromHistory(java.lang.String str) {
        finishActivityResults(0, null, null);
        makeFinishingLocked();
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 8836546031252812807L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(str), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        takeFromHistory();
        removeTimeouts();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 8348126473928520781L, 0, null, java.lang.String.valueOf(this));
        setState(com.android.server.wm.ActivityRecord.State.DESTROYED, "removeFromHistory");
        detachFromProcess();
        resumeKeyDispatchingLocked();
        this.mDisplayContent.removeAppToken(this.token);
        cleanUpActivityServices();
        removeUriPermissionsLocked();
    }

    void detachFromProcess() {
        if (this.app != null) {
            this.app.removeActivity(this, false);
        }
        this.app = null;
        this.mInputDispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }

    void makeFinishingLocked() {
        com.android.server.wm.ActivityRecord activity;
        if (this.finishing) {
            return;
        }
        this.finishing = true;
        if (this.mLaunchCookie != null && this.mState != com.android.server.wm.ActivityRecord.State.RESUMED && this.task != null && !this.task.mInRemoveTask && !this.task.isClearingToReuseTask() && (activity = this.task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$makeFinishingLocked$7;
                lambda$makeFinishingLocked$7 = com.android.server.wm.ActivityRecord.this.lambda$makeFinishingLocked$7((com.android.server.wm.ActivityRecord) obj);
                return lambda$makeFinishingLocked$7;
            }
        }, this, false, false)) != null) {
            activity.mLaunchCookie = this.mLaunchCookie;
            this.mLaunchCookie = null;
        }
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            com.android.server.wm.Task task = taskFragment.getTask();
            if (task != null && task.isClearingToReuseTask() && taskFragment.getTopNonFinishingActivity() == null) {
                taskFragment.mClearedTaskForReuse = true;
            }
            taskFragment.sendTaskFragmentInfoChanged();
        }
        if (this.mAppStopped) {
            abortAndClearOptionsAnimation();
        }
        if (this.mDisplayContent != null) {
            this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$makeFinishingLocked$7(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.mLaunchCookie == null && !activityRecord.finishing && activityRecord.isUid(getUid());
    }

    void destroyed(java.lang.String str) {
        removeDestroyTimeout();
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTAINERS, -8001673213497887656L, 0, null, java.lang.String.valueOf(this));
        if (!isState(com.android.server.wm.ActivityRecord.State.DESTROYING, com.android.server.wm.ActivityRecord.State.DESTROYED)) {
            throw new java.lang.IllegalStateException("Reported destroyed for activity that is not destroying: r=" + this);
        }
        this.mTaskSupervisor.killTaskProcessesOnDestroyedIfNeeded(this.task);
        if (isInRootTaskLocked()) {
            cleanUp(true, false);
            removeFromHistory(str);
        }
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    void cleanUp(boolean z, boolean z2) {
        getTaskFragment().cleanUpActivityReferences(this);
        clearLastParentBeforePip();
        cleanUpSplashScreen();
        if (z2) {
            setState(com.android.server.wm.ActivityRecord.State.DESTROYED, "cleanUp");
            detachFromProcess();
        }
        this.mTaskSupervisor.cleanupActivity(this);
        if (this.finishing && this.pendingResults != null) {
            java.util.Iterator<java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord>> it = this.pendingResults.iterator();
            while (it.hasNext()) {
                com.android.server.am.PendingIntentRecord pendingIntentRecord = it.next().get();
                if (pendingIntentRecord != null) {
                    this.mAtmService.mPendingIntentController.cancelIntentSender(pendingIntentRecord, false);
                }
            }
            this.pendingResults = null;
        }
        if (z) {
            cleanUpActivityServices();
        }
        removeTimeouts();
        clearRelaunching();
    }

    boolean isRelaunching() {
        return this.mPendingRelaunchCount > 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    void startRelaunching() {
        if (this.mPendingRelaunchCount == 0) {
            this.mRelaunchStartTime = android.os.SystemClock.elapsedRealtime();
            if (this.mVisibleRequested) {
                this.mDisplayContent.getDisplayPolicy().addRelaunchingApp(this);
            }
        }
        clearAllDrawn();
        this.mPendingRelaunchCount++;
    }

    void finishRelaunching() {
        this.mLetterboxUiController.setRelaunchingAfterRequestedOrientationChanged(false);
        this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityRelaunched(this);
        if (this.mPendingRelaunchCount > 0) {
            this.mPendingRelaunchCount--;
            if (this.mPendingRelaunchCount == 0 && !isClientVisible()) {
                finishOrAbortReplacingWindow();
            }
        } else {
            checkKeyguardFlagsChanged();
        }
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask != null && rootTask.shouldSleepOrShutDownActivities()) {
            rootTask.ensureActivitiesVisible(null);
        }
    }

    void clearRelaunching() {
        if (this.mPendingRelaunchCount == 0) {
            return;
        }
        this.mPendingRelaunchCount = 0;
        finishOrAbortReplacingWindow();
    }

    void finishOrAbortReplacingWindow() {
        this.mRelaunchStartTime = 0L;
        this.mDisplayContent.getDisplayPolicy().removeRelaunchingApp(this);
    }

    com.android.server.wm.ActivityServiceConnectionsHolder getOrCreateServiceConnectionsHolder() {
        com.android.server.wm.ActivityServiceConnectionsHolder activityServiceConnectionsHolder;
        synchronized (this) {
            try {
                if (this.mServiceConnectionsHolder == null) {
                    this.mServiceConnectionsHolder = new com.android.server.wm.ActivityServiceConnectionsHolder(this);
                }
                activityServiceConnectionsHolder = this.mServiceConnectionsHolder;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return activityServiceConnectionsHolder;
    }

    private void cleanUpActivityServices() {
        synchronized (this) {
            try {
                if (this.mServiceConnectionsHolder == null) {
                    return;
                }
                this.mServiceConnectionsHolder.disconnectActivityFromServices();
                this.mServiceConnectionsHolder = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateVisibleForServiceConnection() {
        this.mVisibleForServiceConnection = this.mVisibleRequested || this.mState == com.android.server.wm.ActivityRecord.State.RESUMED || this.mState == com.android.server.wm.ActivityRecord.State.PAUSING;
    }

    void handleAppDied() {
        boolean z;
        com.android.server.wm.ActivityRecord activityRecord;
        if (android.os.Process.isSdkSandboxUid(getUid())) {
            z = true;
        } else {
            z = false;
            if ((this.mRelaunchReason != 1 && this.mRelaunchReason != 2) || this.launchCount >= 3 || this.finishing) {
                if ((!this.mHaveState && !this.stateNotNeeded && !isState(com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS)) || this.finishing) {
                    z = true;
                } else if (!this.mVisibleRequested && this.launchCount > 2 && this.lastLaunchTime > android.os.SystemClock.uptimeMillis() - 60000) {
                    z = true;
                }
            }
        }
        if (z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 587363723665813898L, 204, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(this.mHaveState), java.lang.String.valueOf(this.stateNotNeeded), java.lang.Boolean.valueOf(this.finishing), java.lang.String.valueOf(this.mState), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
            if (!this.finishing || (this.app != null && this.app.isRemoved())) {
                android.util.Slog.w("ActivityTaskManager", "Force removing " + this + ": app died, no saved state");
                com.android.server.wm.EventLogTags.writeWmFinishActivity(this.mUserId, java.lang.System.identityHashCode(this), this.task != null ? this.task.mTaskId : -1, this.shortComponentName, "proc died without state saved");
            }
        }
        if (this.task != null && this.task.mKillProcessesOnDestroyed) {
            this.mTaskSupervisor.removeTimeoutOfKillProcessesOnProcessDied(this, this.task);
        }
        this.mTransitionController.requestCloseTransitionIfNeeded((z && this.task != null && this.task.getChildCount() == 1) ? this.task : this);
        cleanUp(true, true);
        if (z) {
            if (this.mStartingData != null && this.mVisible && this.task != null && (activityRecord = this.task.topRunningActivity()) != null && !activityRecord.mVisible && activityRecord.shouldBeVisible()) {
                activityRecord.transferStartingWindow(this);
            }
            removeFromHistory("appDied");
        }
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    void removeImmediately() {
        if (this.mState != com.android.server.wm.ActivityRecord.State.DESTROYED) {
            android.util.Slog.w("ActivityTaskManager", "Force remove immediately " + this + " state=" + this.mState);
            destroyImmediately("removeImmediately");
            destroyed("removeImmediately");
        } else {
            onRemovedFromDisplay();
        }
        this.mActivityRecordInputSink.releaseSurfaceControl();
        super.removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    void removeIfPossible() {
        this.mIsExiting = false;
        removeAllWindowsIfPossible();
        removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handleCompleteDeferredRemoval() {
        if (this.mIsExiting) {
            removeIfPossible();
        }
        return super.handleCompleteDeferredRemoval();
    }

    void onRemovedFromDisplay() {
        if (this.mRemovingFromDisplay) {
            return;
        }
        this.mRemovingFromDisplay = true;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -1842512343787359105L, 0, null, java.lang.String.valueOf(this));
        getDisplayContent().mOpeningApps.remove(this);
        getDisplayContent().mUnknownAppVisibilityController.appRemovedOrHidden(this);
        this.mWmService.mSnapshotController.onAppRemoved(this);
        this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityRemoved(this);
        this.mTaskSupervisor.mStoppingActivities.remove(this);
        this.mLetterboxUiController.destroy();
        boolean isAnimating = isAnimating(7, 17);
        if (getDisplayContent().mClosingApps.contains(this)) {
            isAnimating = true;
        } else if (getDisplayContent().mAppTransition.isTransitionSet()) {
            getDisplayContent().mClosingApps.add(this);
            isAnimating = true;
        } else if (this.mTransitionController.inTransition()) {
            isAnimating = true;
        }
        if (!isAnimating) {
            commitVisibility(false, true);
        } else {
            setVisibleRequested(false);
        }
        this.mTransitionController.collect(this);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5548174277852675449L, 204, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(isAnimating), java.lang.String.valueOf(getAnimation()), java.lang.Boolean.valueOf(isAnimating(3, 1)));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -601582700132879947L, 12, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(isAnimating), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
        if (this.mStartingData != null) {
            removeStartingWindow();
        }
        if (isAnimating(3, 1)) {
            getDisplayContent().mNoAnimationNotifyOnTransitionFinished.add(this.token);
        }
        if (isAnimating && !isEmpty()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 3478214322581157355L, 0, null, java.lang.String.valueOf(this));
            this.mIsExiting = true;
        } else {
            cancelAnimation();
            removeIfPossible();
        }
        stopFreezingScreen(true, true);
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent.mFocusedApp == this) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -7226216420432530281L, 4, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(displayContent.getDisplayId()));
            displayContent.setFocusedApp(null);
            this.mWmService.updateFocusedWindowLocked(0, true);
        }
        if (!isAnimating) {
            updateReportedVisibilityLocked();
        }
        this.mDisplayContent.mPinnedTaskController.onActivityHidden(this.mActivityComponent);
        this.mDisplayContent.onRunningActivityChanged();
        this.mRemovingFromDisplay = false;
    }

    @Override // com.android.server.wm.WindowToken
    protected boolean isFirstChildWindowGreaterThanSecond(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        int i = windowState.mAttrs.type;
        int i2 = windowState2.mAttrs.type;
        if (i == 1 && i2 != 1) {
            return false;
        }
        if (i == 1 || i2 != 1) {
            return (i == 3 && i2 != 3) || i == 3 || i2 != 3;
        }
        return true;
    }

    boolean hasStartingWindow() {
        if (this.mStartingData != null) {
            return true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (getChildAt(size).mAttrs.type == 3) {
                return true;
            }
        }
        return false;
    }

    boolean isLastWindow(com.android.server.wm.WindowState windowState) {
        return this.mChildren.size() == 1 && this.mChildren.get(0) == windowState;
    }

    @Override // com.android.server.wm.WindowToken
    void addWindow(com.android.server.wm.WindowState windowState) {
        super.addWindow(windowState);
        checkKeyguardFlagsChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.WindowContainer
    public void removeChild(com.android.server.wm.WindowState windowState) {
        if (!this.mChildren.contains(windowState)) {
            return;
        }
        super.removeChild((com.android.server.wm.ActivityRecord) windowState);
        checkKeyguardFlagsChanged();
        updateLetterboxSurfaceIfNeeded(windowState);
    }

    void setAppLayoutChanges(int i, java.lang.String str) {
        if (!this.mChildren.isEmpty()) {
            com.android.server.wm.DisplayContent displayContent = getDisplayContent();
            displayContent.pendingLayoutChanges = i | displayContent.pendingLayoutChanges;
        }
    }

    private boolean transferStartingWindow(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowState windowState = activityRecord.mStartingWindow;
        if (windowState != null && activityRecord.mStartingSurface != null) {
            if (windowState.getParent() == null) {
                return false;
            }
            if (activityRecord.mVisible) {
                this.mDisplayContent.mSkipAppTransitionAnimation = true;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 8361394136152947990L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(activityRecord), java.lang.String.valueOf(this));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (activityRecord.hasFixedRotationTransform()) {
                    this.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(this, false);
                }
                this.mStartingData = activityRecord.mStartingData;
                this.mStartingSurface = activityRecord.mStartingSurface;
                this.mStartingWindow = windowState;
                this.reportedVisible = activityRecord.reportedVisible;
                activityRecord.mStartingData = null;
                activityRecord.mStartingSurface = null;
                activityRecord.mStartingWindow = null;
                activityRecord.startingMoved = true;
                windowState.mToken = this;
                windowState.mActivityRecord = this;
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -3450064502566932331L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(activityRecord));
                this.mTransitionController.collect(windowState);
                windowState.reparent(this, Integer.MAX_VALUE);
                windowState.clearFrozenInsetsState();
                if (activityRecord.allDrawn) {
                    this.allDrawn = true;
                }
                if (activityRecord.firstWindowDrawn) {
                    this.firstWindowDrawn = true;
                }
                if (activityRecord.isVisible()) {
                    setVisible(true);
                    setVisibleRequested(true);
                    this.mVisibleSetFromTransferredStartingWindow = true;
                }
                setClientVisible(activityRecord.isClientVisible());
                if (activityRecord.isAnimating()) {
                    transferAnimation(activityRecord);
                    this.mTransitionChangeFlags |= 8;
                } else if (this.mTransitionController.getTransitionPlayer() != null) {
                    this.mTransitionChangeFlags |= 8;
                }
                activityRecord.postWindowRemoveStartingWindowCleanup(windowState);
                activityRecord.mVisibleSetFromTransferredStartingWindow = false;
                this.mWmService.updateFocusedWindowLocked(3, true);
                getDisplayContent().setLayoutNeeded();
                this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (activityRecord.mStartingData == null) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 8639603536400037285L, 0, null, java.lang.String.valueOf(activityRecord), java.lang.String.valueOf(this));
        this.mStartingData = activityRecord.mStartingData;
        activityRecord.mStartingData = null;
        activityRecord.startingMoved = true;
        scheduleAddStartingWindow();
        return true;
    }

    void transferStartingWindowFromHiddenAboveTokenIfNeeded() {
        com.android.server.wm.WindowState findMainWindow = findMainWindow(false);
        if (findMainWindow != null && findMainWindow.mWinAnimator.getShown()) {
            return;
        }
        this.task.forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8;
                lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8 = com.android.server.wm.ActivityRecord.this.lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8((com.android.server.wm.ActivityRecord) obj);
                return lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$transferStartingWindowFromHiddenAboveTokenIfNeeded$8(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == this) {
            return true;
        }
        com.android.server.wm.StartingData startingData = activityRecord.mStartingData;
        if (startingData == null || startingData.mAssociatedTask != null || !this.mTransitionController.isCollecting(activityRecord) || !(startingData instanceof com.android.server.wm.SnapshotStartingData) || activityRecord.getBounds().equals(getBounds())) {
            return !activityRecord.isVisibleRequested() && transferStartingWindow(activityRecord);
        }
        if (this.mTransitionController.inPlayingTransition(activityRecord)) {
            this.mTransitionController.setNoAnimation(this);
            this.mTransitionController.setNoAnimation(activityRecord);
        }
        activityRecord.removeStartingWindow();
        return true;
    }

    boolean isKeyguardLocked() {
        return this.mDisplayContent != null ? this.mDisplayContent.isKeyguardLocked() : this.mRootWindowContainer.getDefaultDisplay().isKeyguardLocked();
    }

    void checkKeyguardFlagsChanged() {
        boolean containsDismissKeyguardWindow = containsDismissKeyguardWindow();
        boolean containsShowWhenLockedWindow = containsShowWhenLockedWindow();
        if (containsDismissKeyguardWindow != this.mLastContainsDismissKeyguardWindow || containsShowWhenLockedWindow != this.mLastContainsShowWhenLockedWindow) {
            this.mDisplayContent.notifyKeyguardFlagsChanged();
        }
        this.mLastContainsDismissKeyguardWindow = containsDismissKeyguardWindow;
        this.mLastContainsShowWhenLockedWindow = containsShowWhenLockedWindow;
        this.mLastContainsTurnScreenOnWindow = containsTurnScreenOnWindow();
    }

    boolean containsDismissKeyguardWindow() {
        if (isRelaunching()) {
            return this.mLastContainsDismissKeyguardWindow;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if ((((com.android.server.wm.WindowState) this.mChildren.get(size)).mAttrs.flags & 4194304) != 0) {
                return true;
            }
        }
        return false;
    }

    boolean containsShowWhenLockedWindow() {
        if (isRelaunching()) {
            return this.mLastContainsShowWhenLockedWindow;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if ((((com.android.server.wm.WindowState) this.mChildren.get(size)).mAttrs.flags & 524288) != 0) {
                return true;
            }
        }
        return false;
    }

    void setShowWhenLocked(boolean z) {
        this.mShowWhenLocked = z;
        this.mAtmService.mRootWindowContainer.ensureActivitiesVisible();
    }

    void setInheritShowWhenLocked(boolean z) {
        this.mInheritShownWhenLocked = z;
        this.mAtmService.mRootWindowContainer.ensureActivitiesVisible();
    }

    private static boolean canShowWhenLocked(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityRecord activityBelow;
        if (activityRecord == null || activityRecord.getTaskFragment() == null) {
            return false;
        }
        if (canShowWhenLockedInner(activityRecord)) {
            return true;
        }
        return activityRecord.mInheritShownWhenLocked && (activityBelow = activityRecord.getTaskFragment().getActivityBelow(activityRecord)) != null && canShowWhenLockedInner(activityBelow);
    }

    private static boolean canShowWhenLockedInner(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.inPinnedWindowingMode() && (activityRecord.mShowWhenLocked || activityRecord.containsShowWhenLockedWindow() || activityRecord.mIsUserAlwaysVisible);
    }

    boolean canShowWhenLocked() {
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (taskFragment == null || taskFragment.getAdjacentTaskFragment() == null || !taskFragment.isEmbedded()) {
            return canShowWhenLocked(this);
        }
        return canShowWhenLocked(this) && canShowWhenLocked(taskFragment.getAdjacentTaskFragment().getTopNonFinishingActivity());
    }

    boolean canShowWindows() {
        return this.mTransitionController.isShellTransitionsEnabled() ? this.mSyncState != 1 : this.allDrawn;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllActivities(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z) {
        return predicate.test(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllActivities(java.util.function.Consumer<com.android.server.wm.ActivityRecord> consumer, boolean z) {
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate, boolean z, com.android.server.wm.ActivityRecord activityRecord) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    void logStartActivity(int i, com.android.server.wm.Task task) {
        android.net.Uri data = this.intent.getData();
        android.util.EventLog.writeEvent(i, java.lang.Integer.valueOf(this.mUserId), java.lang.Integer.valueOf(java.lang.System.identityHashCode(this)), java.lang.Integer.valueOf(task.mTaskId), this.shortComponentName, this.intent.getAction(), this.intent.getType(), data != null ? data.toSafeString() : null, java.lang.Integer.valueOf(this.intent.getFlags()));
    }

    com.android.server.uri.UriPermissionOwner getUriPermissionsLocked() {
        if (this.uriPermissions == null) {
            this.uriPermissions = new com.android.server.uri.UriPermissionOwner(this.mAtmService.mUgmInternal, this);
        }
        return this.uriPermissions;
    }

    void addResultLocked(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i, int i2, android.content.Intent intent, android.os.IBinder iBinder) {
        com.android.server.wm.ActivityResult activityResult = new com.android.server.wm.ActivityResult(activityRecord, str, i, i2, intent, iBinder);
        if (this.results == null) {
            this.results = new java.util.ArrayList<>();
        }
        this.results.add(activityResult);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0035 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void removeResultsLocked(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i) {
        if (this.results != null) {
            for (int size = this.results.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityResult activityResult = (com.android.server.wm.ActivityResult) this.results.get(size);
                if (activityResult.mFrom == activityRecord) {
                    if (((android.app.ResultInfo) activityResult).mResultWho == null) {
                        if (str != null) {
                        }
                        if (((android.app.ResultInfo) activityResult).mRequestCode != i) {
                            this.results.remove(size);
                        }
                    } else {
                        if (!((android.app.ResultInfo) activityResult).mResultWho.equals(str)) {
                        }
                        if (((android.app.ResultInfo) activityResult).mRequestCode != i) {
                        }
                    }
                }
            }
        }
    }

    void sendResult(int i, java.lang.String str, int i2, int i3, android.content.Intent intent, android.os.IBinder iBinder, com.android.server.uri.NeededUriGrants neededUriGrants) {
        sendResult(i, str, i2, i3, intent, iBinder, neededUriGrants, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0036, code lost:
    
        r16.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(r23, getUriPermissionsLocked());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void sendResult(int i, java.lang.String str, int i2, int i3, android.content.Intent intent, android.os.IBinder iBinder, com.android.server.uri.NeededUriGrants neededUriGrants, boolean z) {
        if (android.security.Flags.contentUriPermissionApis() && !this.mCallerState.hasCaller(iBinder)) {
            try {
                computeCallerInfo(iBinder, intent, i, this.mAtmService.getPackageManager().getNameForUid(i), false);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        if (isState(com.android.server.wm.ActivityRecord.State.RESUMED) && attachedToProcess()) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(new android.app.ResultInfo(str, i2, i3, intent, iBinder));
                this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.ActivityResultItem.obtain(this.token, arrayList));
                return;
            } catch (java.lang.Exception e2) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending result to " + this, e2);
            }
        }
        if (z && attachedToProcess() && isState(com.android.server.wm.ActivityRecord.State.STARTED, com.android.server.wm.ActivityRecord.State.PAUSING, com.android.server.wm.ActivityRecord.State.PAUSED, com.android.server.wm.ActivityRecord.State.STOPPING, com.android.server.wm.ActivityRecord.State.STOPPED)) {
            android.app.servertransaction.ClientTransactionItem obtain = android.app.servertransaction.ActivityResultItem.obtain(this.token, java.util.List.of(new android.app.ResultInfo(str, i2, i3, intent, iBinder)));
            android.app.servertransaction.ActivityLifecycleItem lifecycleItemForCurrentStateForResult = getLifecycleItemForCurrentStateForResult();
            try {
                if (lifecycleItemForCurrentStateForResult != null) {
                    this.mAtmService.getLifecycleManager().scheduleTransactionAndLifecycleItems(this.app.getThread(), obtain, lifecycleItemForCurrentStateForResult);
                } else {
                    android.util.Slog.w("ActivityTaskManager", "Unable to get the lifecycle item for state " + this.mState + " so couldn't immediately send result");
                    this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), obtain);
                }
                return;
            } catch (android.os.RemoteException e3) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending result to " + this, e3);
                return;
            }
        }
        addResultLocked(null, str, i2, i3, intent, iBinder);
    }

    @android.annotation.Nullable
    private android.app.servertransaction.ActivityLifecycleItem getLifecycleItemForCurrentStateForResult() {
        switch (com.android.server.wm.ActivityRecord.AnonymousClass6.$SwitchMap$com$android$server$wm$ActivityRecord$State[this.mState.ordinal()]) {
            case 2:
            case 3:
                return android.app.servertransaction.PauseActivityItem.obtain(this.token);
            case 4:
            case 6:
                return android.app.servertransaction.StopActivityItem.obtain(this.token, this.configChangeFlags);
            case 5:
                return android.app.servertransaction.StartActivityItem.obtain(this.token, (android.app.ActivityOptions.SceneTransitionInfo) null);
            default:
                return null;
        }
    }

    private void addNewIntentLocked(com.android.internal.content.ReferrerIntent referrerIntent) {
        if (this.newIntents == null) {
            this.newIntents = new java.util.ArrayList<>();
        }
        this.newIntents.add(referrerIntent);
    }

    final boolean isSleeping() {
        com.android.server.wm.Task rootTask = getRootTask();
        return rootTask != null ? rootTask.shouldSleepActivities() : this.mAtmService.isSleepingLocked();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void deliverNewIntentLocked(int i, android.content.Intent intent, com.android.server.uri.NeededUriGrants neededUriGrants, java.lang.String str, boolean z, int i2, int i3) {
        android.os.Binder binder = new android.os.Binder();
        if (android.security.Flags.contentUriPermissionApis()) {
            computeCallerInfo(binder, intent, i, str, z);
        }
        this.mAtmService.mUgmInternal.grantUriPermissionUncheckedFromIntent(neededUriGrants, getUriPermissionsLocked());
        if (z && android.security.Flags.contentUriPermissionApis()) {
            this.mAtmService.getPackageManagerInternalLocked().grantImplicitAccess(i2, intent, i3, i, true);
        }
        com.android.internal.content.ReferrerIntent referrerIntent = new com.android.internal.content.ReferrerIntent(intent, getFilteredReferrer(str), binder);
        boolean z2 = false;
        boolean z3 = isTopRunningActivity() && isSleeping();
        if ((this.mState == com.android.server.wm.ActivityRecord.State.RESUMED || this.mState == com.android.server.wm.ActivityRecord.State.PAUSED || z3) && attachedToProcess()) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList(1);
                arrayList.add(referrerIntent);
                this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.NewIntentItem.obtain(this.token, arrayList, this.mState == com.android.server.wm.ActivityRecord.State.RESUMED));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending new intent to " + this, e);
            } catch (java.lang.NullPointerException e2) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending new intent to " + this, e2);
            }
            if (!z2) {
                addNewIntentLocked(referrerIntent);
                return;
            }
            return;
        }
        z2 = true;
        if (!z2) {
        }
    }

    void updateOptionsLocked(android.app.ActivityOptions activityOptions) {
        if (activityOptions != null) {
            if (this.mPendingOptions != null) {
                this.mPendingOptions.abort();
            }
            setOptions(activityOptions);
        }
    }

    boolean getLaunchedFromBubble() {
        return this.mLaunchedFromBubble;
    }

    private void setOptions(@android.annotation.NonNull android.app.ActivityOptions activityOptions) {
        this.mLaunchedFromBubble = activityOptions.getLaunchedFromBubble();
        this.mPendingOptions = activityOptions;
        if (activityOptions.getAnimationType() == 13) {
            this.mPendingRemoteAnimation = activityOptions.getRemoteAnimationAdapter();
        }
        this.mPendingRemoteTransition = activityOptions.getRemoteTransition();
    }

    void applyOptionsAnimation() {
        if (this.mPendingRemoteAnimation != null) {
            this.mDisplayContent.mAppTransition.overridePendingAppTransitionRemote(this.mPendingRemoteAnimation);
            this.mTransitionController.setStatusBarTransitionDelay(this.mPendingRemoteAnimation.getStatusBarTransitionDelay());
        } else {
            if (this.mPendingOptions == null) {
                return;
            }
            if (this.mPendingOptions.getAnimationType() == 5) {
                this.mTransitionController.setOverrideAnimation(android.window.TransitionInfo.AnimationOptions.makeSceneTransitionAnimOptions(), null, null);
                return;
            }
            applyOptionsAnimation(this.mPendingOptions, this.intent);
        }
        clearOptionsAnimationForSiblings();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void applyOptionsAnimation(android.app.ActivityOptions activityOptions, android.content.Intent intent) {
        android.os.IRemoteCallback iRemoteCallback;
        android.os.IRemoteCallback animationFinishedListener;
        android.window.TransitionInfo.AnimationOptions makeScaleUpAnimOptions;
        int animationType = activityOptions.getAnimationType();
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        android.window.TransitionInfo.AnimationOptions animationOptions = null;
        switch (animationType) {
            case -1:
            case 0:
                iRemoteCallback = null;
                animationFinishedListener = iRemoteCallback;
                break;
            case 1:
                displayContent.mAppTransition.overridePendingAppTransition(activityOptions.getPackageName(), activityOptions.getCustomEnterResId(), activityOptions.getCustomExitResId(), activityOptions.getCustomBackgroundColor(), activityOptions.getAnimationStartedListener(), activityOptions.getAnimationFinishedListener(), activityOptions.getOverrideTaskTransition());
                animationOptions = android.window.TransitionInfo.AnimationOptions.makeCustomAnimOptions(activityOptions.getPackageName(), activityOptions.getCustomEnterResId(), activityOptions.getCustomExitResId(), activityOptions.getCustomBackgroundColor(), activityOptions.getOverrideTaskTransition());
                iRemoteCallback = activityOptions.getAnimationStartedListener();
                animationFinishedListener = activityOptions.getAnimationFinishedListener();
                break;
            case 2:
                displayContent.mAppTransition.overridePendingAppTransitionScaleUp(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getWidth(), activityOptions.getHeight());
                makeScaleUpAnimOptions = android.window.TransitionInfo.AnimationOptions.makeScaleUpAnimOptions(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getWidth(), activityOptions.getHeight());
                if (intent.getSourceBounds() == null) {
                    intent.setSourceBounds(new android.graphics.Rect(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getStartX() + activityOptions.getWidth(), activityOptions.getStartY() + activityOptions.getHeight()));
                }
                iRemoteCallback = null;
                animationOptions = makeScaleUpAnimOptions;
                animationFinishedListener = iRemoteCallback;
                break;
            case 3:
            case 4:
                boolean z = animationType == 3;
                android.hardware.HardwareBuffer thumbnail = activityOptions.getThumbnail();
                displayContent.mAppTransition.overridePendingAppTransitionThumb(thumbnail, activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getAnimationStartedListener(), z);
                android.window.TransitionInfo.AnimationOptions makeThumbnailAnimOptions = android.window.TransitionInfo.AnimationOptions.makeThumbnailAnimOptions(thumbnail, activityOptions.getStartX(), activityOptions.getStartY(), z);
                android.os.IRemoteCallback animationStartedListener = activityOptions.getAnimationStartedListener();
                if (intent.getSourceBounds() == null && thumbnail != null) {
                    intent.setSourceBounds(new android.graphics.Rect(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getStartX() + thumbnail.getWidth(), activityOptions.getStartY() + thumbnail.getHeight()));
                }
                iRemoteCallback = animationStartedListener;
                animationFinishedListener = null;
                animationOptions = makeThumbnailAnimOptions;
                break;
            case 5:
            case 6:
            case 7:
            case 10:
            default:
                android.util.Slog.e("WindowManager", "applyOptionsLocked: Unknown animationType=" + animationType);
                iRemoteCallback = null;
                animationFinishedListener = iRemoteCallback;
                break;
            case 8:
            case 9:
                android.view.AppTransitionAnimationSpec[] animSpecs = activityOptions.getAnimSpecs();
                android.view.IAppTransitionAnimationSpecsFuture specsFuture = activityOptions.getSpecsFuture();
                if (specsFuture != null) {
                    displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(specsFuture, activityOptions.getAnimationStartedListener(), animationType == 8);
                } else if (animationType == 9 && animSpecs != null) {
                    displayContent.mAppTransition.overridePendingAppTransitionMultiThumb(animSpecs, activityOptions.getAnimationStartedListener(), activityOptions.getAnimationFinishedListener(), false);
                } else {
                    displayContent.mAppTransition.overridePendingAppTransitionAspectScaledThumb(activityOptions.getThumbnail(), activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getWidth(), activityOptions.getHeight(), activityOptions.getAnimationStartedListener(), animationType == 8);
                    if (intent.getSourceBounds() == null) {
                        intent.setSourceBounds(new android.graphics.Rect(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getStartX() + activityOptions.getWidth(), activityOptions.getStartY() + activityOptions.getHeight()));
                    }
                }
                iRemoteCallback = null;
                animationFinishedListener = iRemoteCallback;
                break;
            case 11:
                displayContent.mAppTransition.overridePendingAppTransitionClipReveal(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getWidth(), activityOptions.getHeight());
                makeScaleUpAnimOptions = android.window.TransitionInfo.AnimationOptions.makeClipRevealAnimOptions(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getWidth(), activityOptions.getHeight());
                if (intent.getSourceBounds() == null) {
                    intent.setSourceBounds(new android.graphics.Rect(activityOptions.getStartX(), activityOptions.getStartY(), activityOptions.getStartX() + activityOptions.getWidth(), activityOptions.getStartY() + activityOptions.getHeight()));
                }
                iRemoteCallback = null;
                animationOptions = makeScaleUpAnimOptions;
                animationFinishedListener = iRemoteCallback;
                break;
            case 12:
                displayContent.mAppTransition.overridePendingAppTransitionStartCrossProfileApps();
                animationFinishedListener = null;
                animationOptions = android.window.TransitionInfo.AnimationOptions.makeCrossProfileAnimOptions();
                iRemoteCallback = null;
                break;
        }
        if (animationOptions != null) {
            this.mTransitionController.setOverrideAnimation(animationOptions, iRemoteCallback, animationFinishedListener);
        }
    }

    void clearAllDrawn() {
        this.allDrawn = false;
        this.mLastAllDrawn = false;
    }

    private boolean allDrawnStatesConsidered() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
            if (windowState.mightAffectAllDrawn() && !windowState.getDrawnStateEvaluated()) {
                return false;
            }
        }
        return true;
    }

    void updateAllDrawn() {
        int i;
        if (!this.allDrawn && (i = this.mNumInterestingWindows) > 0 && allDrawnStatesConsidered() && this.mNumDrawnWindows >= i && !isRelaunching()) {
            this.allDrawn = true;
            if (this.mDisplayContent != null) {
                this.mDisplayContent.setLayoutNeeded();
            }
            this.mWmService.mH.obtainMessage(32, this).sendToTarget();
        }
    }

    void abortAndClearOptionsAnimation() {
        if (this.mPendingOptions != null) {
            this.mPendingOptions.abort();
        }
        clearOptionsAnimation();
    }

    void clearOptionsAnimation() {
        this.mPendingOptions = null;
        this.mPendingRemoteAnimation = null;
        this.mPendingRemoteTransition = null;
    }

    void clearOptionsAnimationForSiblings() {
        if (this.task == null) {
            clearOptionsAnimation();
        } else {
            this.task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.ActivityRecord) obj).clearOptionsAnimation();
                }
            });
        }
    }

    android.app.ActivityOptions getOptions() {
        return this.mPendingOptions;
    }

    android.app.ActivityOptions.SceneTransitionInfo takeSceneTransitionInfo() {
        if (this.mPendingOptions == null) {
            return null;
        }
        android.app.ActivityOptions activityOptions = this.mPendingOptions;
        this.mPendingOptions = null;
        return activityOptions.getSceneTransitionInfo();
    }

    android.window.RemoteTransition takeRemoteTransition() {
        android.window.RemoteTransition remoteTransition = this.mPendingRemoteTransition;
        this.mPendingRemoteTransition = null;
        return remoteTransition;
    }

    boolean allowMoveToFront() {
        return this.mPendingOptions == null || !this.mPendingOptions.getAvoidMoveToFront();
    }

    void removeUriPermissionsLocked() {
        if (this.uriPermissions != null) {
            this.uriPermissions.removeUriPermissions();
            this.uriPermissions = null;
        }
    }

    void pauseKeyDispatchingLocked() {
        if (!this.keysPaused) {
            this.keysPaused = true;
            if (getDisplayContent() != null) {
                getDisplayContent().getInputMonitor().pauseDispatchingLw(this);
            }
        }
    }

    void resumeKeyDispatchingLocked() {
        if (this.keysPaused) {
            this.keysPaused = false;
            if (getDisplayContent() != null) {
                getDisplayContent().getInputMonitor().resumeDispatchingLw(this);
            }
        }
    }

    private void updateTaskDescription(java.lang.CharSequence charSequence) {
        this.task.lastDescription = charSequence;
    }

    void setDeferHidingClient(boolean z) {
        if (this.mDeferHidingClient == z) {
            return;
        }
        this.mDeferHidingClient = z;
        if (!this.mDeferHidingClient && !this.mVisibleRequested) {
            setVisibility(false);
        }
    }

    boolean getDeferHidingClient() {
        return this.mDeferHidingClient;
    }

    boolean canAffectSystemUiFlags() {
        return (this.task == null || !this.task.canAffectSystemUiFlags() || !isVisible() || this.mWaitForEnteringPinnedMode || inPinnedWindowingMode()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisible() {
        return this.mVisible;
    }

    void setVisible(boolean z) {
        if (z != this.mVisible) {
            this.mVisible = z;
            if (this.app != null) {
                this.mTaskSupervisor.onProcessActivityStateChanged(this.app, false);
            }
            scheduleAnimation();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean setVisibleRequested(boolean z) {
        boolean z2 = false;
        if (!super.setVisibleRequested(z)) {
            return false;
        }
        setInsetsFrozen(!z);
        updateVisibleForServiceConnection();
        if (this.app != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(this.app, false);
        }
        logAppCompatState();
        if (!z) {
            com.android.server.wm.InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
            if (imeInputTarget != null && imeInputTarget.getWindowState() != null && imeInputTarget.getWindowState().mActivityRecord == this && this.mDisplayContent.mInputMethodWindow != null && this.mDisplayContent.mInputMethodWindow.isVisible()) {
                z2 = true;
            }
            this.mLastImeShown = z2;
            finishOrAbortReplacingWindow();
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean onChildVisibleRequestedChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        return false;
    }

    void setVisibility(boolean z) {
        if (getParent() == null) {
            android.util.Slog.w("WindowManager", "Attempted to set visibility of non-existing app token: " + this.token);
            return;
        }
        if (z == this.mVisibleRequested && z == this.mVisible && z == isClientVisible() && this.mTransitionController.isShellTransitionsEnabled()) {
            return;
        }
        if (z) {
            this.mDeferHidingClient = false;
        }
        setVisibility(z, this.mDeferHidingClient);
        this.mAtmService.addWindowLayoutReasons(2);
        this.mTaskSupervisor.getActivityMetricsLogger().notifyVisibilityChanged(this);
        this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = true;
    }

    private void setVisibility(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        com.android.server.wm.AppTransition appTransition = getDisplayContent().mAppTransition;
        if (!z && !this.mVisibleRequested) {
            if (!z2 && this.mLastDeferHidingClient) {
                this.mLastDeferHidingClient = z2;
                setClientVisible(false);
                return;
            }
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -3452055378690362514L, 972, null, java.lang.String.valueOf(this.token), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(appTransition), java.lang.Boolean.valueOf(isVisible()), java.lang.Boolean.valueOf(this.mVisibleRequested), java.lang.String.valueOf(android.os.Debug.getCallers(6)));
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            z3 = false;
            z4 = false;
        } else {
            z3 = this.mTransitionController.isCollecting();
            if (z3) {
                this.mTransitionController.collect(this);
                z4 = false;
            } else {
                z4 = this.mTransitionController.inFinishingTransition(this);
                if (!z4) {
                    if (z) {
                        if (!this.mDisplayContent.isSleeping() || canShowWhenLocked()) {
                            this.mTransitionController.onVisibleWithoutCollectingTransition(this, android.os.Debug.getCallers(1, 1));
                        }
                    } else if (!this.mDisplayContent.isSleeping()) {
                        android.util.Slog.w("ActivityTaskManager", "Set invisible without transition " + this);
                    }
                }
            }
        }
        onChildVisibilityRequested(z);
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        displayContent.mOpeningApps.remove(this);
        displayContent.mClosingApps.remove(this);
        setVisibleRequested(z);
        this.mLastDeferHidingClient = z2;
        if (!z) {
            if (this.startingMoved && !this.firstWindowDrawn && hasChild()) {
                setClientVisible(false);
            }
        } else {
            if (!appTransition.isTransitionSet() && appTransition.isReady()) {
                displayContent.mOpeningApps.add(this);
            }
            this.startingMoved = false;
            if (!isVisible() || this.mAppStopped) {
                clearAllDrawn();
                if (!isVisible() && !isClientVisible()) {
                    forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda35
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.ActivityRecord.lambda$setVisibility$9((com.android.server.wm.WindowState) obj);
                        }
                    }, true);
                }
            }
            setClientVisible(true);
            requestUpdateWallpaperIfNeeded();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1728033820691545386L, 0, null, java.lang.String.valueOf(this));
            this.mAppStopped = false;
            transferStartingWindowFromHiddenAboveTokenIfNeeded();
        }
        if (z3) {
            if (!z) {
                if (this.mTransitionController.inPlayingTransition(this)) {
                    this.mTransitionChangeFlags |= 32768;
                    return;
                } else {
                    if (this.mTransitionController.inFinishingTransition(this)) {
                        this.mTransitionChangeFlags |= 294912;
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (z4) {
            this.mTransitionController.mValidateCommitVis.add(this);
        } else {
            if (deferCommitVisibilityChange(z)) {
                return;
            }
            commitVisibility(z, true);
            updateReportedVisibilityLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setVisibility$9(com.android.server.wm.WindowState windowState) {
        if (windowState.mWinAnimator.mDrawState == 4) {
            windowState.mWinAnimator.resetDrawState();
            windowState.forceReportingResized();
        }
    }

    private boolean deferCommitVisibilityChange(boolean z) {
        com.android.server.wm.WindowState findFocusedWindow;
        com.android.server.wm.ActivityRecord activityRecord;
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        if (!this.mDisplayContent.mAppTransition.isTransitionSet() && (isActivityTypeHome() || !isAnimating(2, 8))) {
            return false;
        }
        if (this.mWaitForEnteringPinnedMode && this.mVisible == z) {
            return false;
        }
        if (!okToAnimate(true, canTurnScreenOn() || this.mTaskSupervisor.getKeyguardController().isKeyguardGoingAway(this.mDisplayContent.mDisplayId))) {
            return false;
        }
        if (z) {
            this.mDisplayContent.mOpeningApps.add(this);
            this.mEnteringAnimation = true;
        } else if (this.mVisible) {
            this.mDisplayContent.mClosingApps.add(this);
            this.mEnteringAnimation = false;
        }
        if ((this.mDisplayContent.mAppTransition.getTransitFlags() & 32) != 0 && (findFocusedWindow = this.mDisplayContent.findFocusedWindow()) != null && (activityRecord = findFocusedWindow.mActivityRecord) != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5062176994575790703L, 0, null, java.lang.String.valueOf(activityRecord));
            this.mDisplayContent.mOpeningApps.add(activityRecord);
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean applyAnimation(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, @android.annotation.Nullable java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        if ((this.mTransitionChangeFlags & 8) != 0) {
            return false;
        }
        this.mRequestForceTransition = false;
        return super.applyAnimation(layoutParams, i, z, z2, arrayList);
    }

    void commitVisibility(boolean z, boolean z2, boolean z3) {
        this.mVisibleSetFromTransferredStartingWindow = false;
        if (z == isVisible()) {
            return;
        }
        int size = this.mChildren.size();
        boolean isAnimating = com.android.server.wm.WindowManagerService.sEnableShellTransitions ? z : isAnimating(2, 1);
        for (int i = 0; i < size; i++) {
            ((com.android.server.wm.WindowState) this.mChildren.get(i)).onAppVisibilityChanged(z, isAnimating);
        }
        setVisible(z);
        setVisibleRequested(z);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -477271988506706928L, 1020, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(isVisible()), java.lang.Boolean.valueOf(this.mVisibleRequested), java.lang.Boolean.valueOf(isInTransition()), java.lang.Boolean.valueOf(isAnimating), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        if (!z) {
            stopFreezingScreen(true, true);
        } else {
            if (this.mStartingWindow != null && !this.mStartingWindow.isDrawn() && (this.firstWindowDrawn || this.allDrawn)) {
                this.mStartingWindow.clearPolicyVisibilityFlag(1);
                this.mStartingWindow.mLegacyPolicyVisibilityAfterAnim = false;
            }
            final com.android.server.wm.WindowManagerService windowManagerService = this.mWmService;
            java.util.Objects.requireNonNull(windowManagerService);
            forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.WindowManagerService.this.makeWindowFreezingScreenIfNeededLocked((com.android.server.wm.WindowState) obj);
                }
            }, true);
        }
        for (com.android.server.wm.Task organizedTask = getOrganizedTask(); organizedTask != null; organizedTask = organizedTask.getParent().asTask()) {
            organizedTask.dispatchTaskInfoChangedIfNeeded(false);
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        displayContent.getInputMonitor().setUpdateInputWindowsNeededLw();
        if (z2) {
            this.mWmService.updateFocusedWindowLocked(3, false);
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        }
        displayContent.getInputMonitor().updateInputWindowsLw(false);
        this.mTransitionChangeFlags = 0;
        postApplyAnimation(z, z3);
    }

    void commitVisibility(boolean z, boolean z2) {
        commitVisibility(z, z2, false);
    }

    void setNeedsLetterboxedAnimation(boolean z) {
        this.mNeedsLetterboxedAnimation = z;
    }

    boolean isNeedsLetterboxedAnimation() {
        return this.mNeedsLetterboxedAnimation;
    }

    boolean isInLetterboxAnimation() {
        return this.mNeedsLetterboxedAnimation && isAnimating();
    }

    private void postApplyAnimation(boolean z, boolean z2) {
        boolean isShellTransitionsEnabled = this.mTransitionController.isShellTransitionsEnabled();
        boolean z3 = !isShellTransitionsEnabled && isAnimating(6, 25);
        if (!z3 && !isShellTransitionsEnabled) {
            onAnimationFinished(1, null);
            if (z) {
                this.mEnteringAnimation = true;
                this.mWmService.mActivityManagerAppTransitionNotifier.onAppTransitionFinishedLocked(this.token);
            }
        }
        if (z || (this.mState != com.android.server.wm.ActivityRecord.State.RESUMED && (isShellTransitionsEnabled || !isAnimating(2, 9)))) {
            setClientVisible(z);
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (!z) {
            this.mImeInsetsFrozenUntilStartInput = true;
        }
        if (!displayContent.mClosingApps.contains(this) && !displayContent.mOpeningApps.contains(this) && !z2) {
            this.mWmService.mSnapshotController.notifyAppVisibilityChanged(this, z);
        }
        if (!isShellTransitionsEnabled && !isVisible() && !z3 && !displayContent.mAppTransition.isTransitionSet()) {
            forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda36
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.ActivityRecord.this.lambda$postApplyAnimation$10((com.android.server.wm.WindowState) obj);
                }
            }, true);
            scheduleAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postApplyAnimation$10(com.android.server.wm.WindowState windowState) {
        windowState.mWinAnimator.hide(getPendingTransaction(), "immediately hidden");
    }

    void commitFinishDrawing(android.view.SurfaceControl.Transaction transaction) {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).commitFinishDrawing(transaction);
        }
        if (z) {
            requestUpdateWallpaperIfNeeded();
        }
    }

    boolean shouldApplyAnimation(boolean z) {
        return isVisible() != z || this.mRequestForceTransition || (!isVisible() && this.mIsExiting);
    }

    void setRecentsScreenshotEnabled(boolean z) {
        this.mEnableRecentsScreenshot = z;
    }

    boolean shouldUseAppThemeSnapshot() {
        return !this.mEnableRecentsScreenshot || forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda28
            public final boolean apply(java.lang.Object obj) {
                return ((com.android.server.wm.WindowState) obj).isSecureLocked();
            }
        }, true);
    }

    void setCurrentLaunchCanTurnScreenOn(boolean z) {
        this.mCurrentLaunchCanTurnScreenOn = z;
    }

    boolean currentLaunchCanTurnScreenOn() {
        return this.mCurrentLaunchCanTurnScreenOn;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setState(com.android.server.wm.ActivityRecord.State state, java.lang.String str) {
        com.android.server.contentcapture.ContentCaptureManagerInternal contentCaptureManagerInternal;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -6873410057142191118L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(getState()), java.lang.String.valueOf(state), java.lang.String.valueOf(str));
        if (state == this.mState) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4437231720834282527L, 0, null, java.lang.String.valueOf(state));
        }
        this.mState = state;
        if (getTaskFragment() != null) {
            getTaskFragment().onActivityStateChanged(this, state, str);
        }
        if (state == com.android.server.wm.ActivityRecord.State.STOPPING && !isSleeping() && getParent() == null) {
            android.util.Slog.w("WindowManager", "Attempted to notify stopping on non-existing app token: " + this.token);
            return;
        }
        updateVisibleForServiceConnection();
        if (this.app != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(this.app, false);
        }
        switch (com.android.server.wm.ActivityRecord.AnonymousClass6.$SwitchMap$com$android$server$wm$ActivityRecord$State[state.ordinal()]) {
            case 1:
                this.mAtmService.updateBatteryStats(this, true);
                this.mAtmService.updateActivityUsageStats(this, 1);
                if (this.app != null) {
                    this.app.updateProcessInfo(false, true, true, true);
                }
                contentCaptureManagerInternal = (com.android.server.contentcapture.ContentCaptureManagerInternal) com.android.server.LocalServices.getService(com.android.server.contentcapture.ContentCaptureManagerInternal.class);
                if (contentCaptureManagerInternal == null) {
                    contentCaptureManagerInternal.notifyActivityEvent(this.mUserId, this.mActivityComponent, 10000, new android.app.assist.ActivityId(getTask() != null ? getTask().mTaskId : -1, this.shareableActivityToken));
                    break;
                }
                break;
            case 3:
                this.mAtmService.updateBatteryStats(this, false);
                this.mAtmService.updateActivityUsageStats(this, 2);
                break;
            case 5:
                if (this.app != null) {
                }
                contentCaptureManagerInternal = (com.android.server.contentcapture.ContentCaptureManagerInternal) com.android.server.LocalServices.getService(com.android.server.contentcapture.ContentCaptureManagerInternal.class);
                if (contentCaptureManagerInternal == null) {
                }
                break;
            case 6:
                this.mAtmService.updateActivityUsageStats(this, 23);
                if (this.mDisplayContent != null) {
                    this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
                    break;
                }
                break;
            case 7:
                if (this.app != null && (this.mVisible || this.mVisibleRequested)) {
                    this.mAtmService.updateBatteryStats(this, false);
                }
                this.mAtmService.updateActivityUsageStats(this, 24);
                if (this.app == null && !this.app.hasActivities()) {
                    this.app.updateProcessInfo(true, false, true, false);
                    break;
                }
                break;
            case 8:
                if (this.app == null) {
                    break;
                }
                break;
        }
    }

    com.android.server.wm.ActivityRecord.State getState() {
        return this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state) {
        return state == this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state, com.android.server.wm.ActivityRecord.State state2) {
        return state == this.mState || state2 == this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state, com.android.server.wm.ActivityRecord.State state2, com.android.server.wm.ActivityRecord.State state3) {
        return state == this.mState || state2 == this.mState || state3 == this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state, com.android.server.wm.ActivityRecord.State state2, com.android.server.wm.ActivityRecord.State state3, com.android.server.wm.ActivityRecord.State state4) {
        return state == this.mState || state2 == this.mState || state3 == this.mState || state4 == this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state, com.android.server.wm.ActivityRecord.State state2, com.android.server.wm.ActivityRecord.State state3, com.android.server.wm.ActivityRecord.State state4, com.android.server.wm.ActivityRecord.State state5) {
        return state == this.mState || state2 == this.mState || state3 == this.mState || state4 == this.mState || state5 == this.mState;
    }

    boolean isState(com.android.server.wm.ActivityRecord.State state, com.android.server.wm.ActivityRecord.State state2, com.android.server.wm.ActivityRecord.State state3, com.android.server.wm.ActivityRecord.State state4, com.android.server.wm.ActivityRecord.State state5, com.android.server.wm.ActivityRecord.State state6) {
        return state == this.mState || state2 == this.mState || state3 == this.mState || state4 == this.mState || state5 == this.mState || state6 == this.mState;
    }

    void destroySurfaces() {
        destroySurfaces(false);
    }

    private void destroySurfaces(boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mChildren);
        boolean z2 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            z2 |= ((com.android.server.wm.WindowState) arrayList.get(size)).destroySurface(z, this.mAppStopped);
        }
        if (z2) {
            getDisplayContent().assignWindowLayers(true);
            updateLetterboxSurfaceIfNeeded(null);
        }
    }

    void notifyAppResumed() {
        if (getParent() == null) {
            android.util.Slog.w("WindowManager", "Attempted to notify resumed of non-existing app token: " + this.token);
            return;
        }
        boolean z = this.mAppStopped;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 926038819327785799L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this));
        this.mAppStopped = false;
        if (this.mAtmService.getActivityStartController().isInExecution()) {
            setCurrentLaunchCanTurnScreenOn(true);
        }
        if (!z) {
            destroySurfaces(true);
        }
    }

    void notifyUnknownVisibilityLaunchedForKeyguardTransition() {
        if (this.noDisplay || !isKeyguardLocked()) {
            return;
        }
        this.mDisplayContent.mUnknownAppVisibilityController.notifyLaunched(this);
    }

    private boolean shouldBeVisible(boolean z, boolean z2) {
        updateVisibilityIgnoringKeyguard(z);
        if (z2) {
            return this.visibleIgnoringKeyguard;
        }
        return shouldBeVisibleUnchecked();
    }

    boolean shouldBeVisibleUnchecked() {
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask == null || !this.visibleIgnoringKeyguard) {
            return false;
        }
        if ((inPinnedWindowingMode() && rootTask.isForceHidden()) || hasOverlayOverUntrustedModeEmbedded()) {
            return false;
        }
        if (this.mDisplayContent.isSleeping()) {
            return canTurnScreenOn();
        }
        return this.mTaskSupervisor.getKeyguardController().checkKeyguardVisibility(this);
    }

    boolean hasOverlayOverUntrustedModeEmbedded() {
        return (!isEmbeddedInUntrustedMode() || getTask() == null || getTask().getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$hasOverlayOverUntrustedModeEmbedded$11;
                lambda$hasOverlayOverUntrustedModeEmbedded$11 = com.android.server.wm.ActivityRecord.this.lambda$hasOverlayOverUntrustedModeEmbedded$11((com.android.server.wm.ActivityRecord) obj);
                return lambda$hasOverlayOverUntrustedModeEmbedded$11;
            }
        }, this, false, false) == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$hasOverlayOverUntrustedModeEmbedded$11(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.getUid() == getUid()) ? false : true;
    }

    void updateVisibilityIgnoringKeyguard(boolean z) {
        this.visibleIgnoringKeyguard = (!z || this.mLaunchTaskBehind) && showToCurrentUser();
    }

    boolean shouldBeVisible() {
        com.android.server.wm.Task task = getTask();
        if (task == null) {
            return false;
        }
        return shouldBeVisible((task.shouldBeVisible(null) && task.getOccludingActivityAbove(this) == null) ? false : true, false);
    }

    void makeVisibleIfNeeded(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        if ((this.mState == com.android.server.wm.ActivityRecord.State.RESUMED && this.mVisibleRequested) || this == activityRecord) {
            return;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        try {
            if (rootTask.mTranslucentActivityWaiting != null) {
                updateOptionsLocked(this.returningOptions);
                rootTask.mUndrawnActivitiesBelowTopTranslucent.add(this);
            }
            setVisibility(true);
            this.app.postPendingUiCleanMsg(true);
            if (z) {
                this.mClientVisibilityDeferred = false;
                makeActiveIfNeeded(activityRecord);
            } else {
                this.mClientVisibilityDeferred = true;
            }
            this.mTaskSupervisor.mStoppingActivities.remove(this);
        } catch (java.lang.Exception e) {
            android.util.Slog.w("ActivityTaskManager", "Exception thrown making visible: " + this.intent.getComponent(), e);
        }
        handleAlreadyVisible();
    }

    void makeInvisible() {
        boolean z;
        if (this.mVisibleRequested) {
            try {
                boolean checkEnterPictureInPictureState = checkEnterPictureInPictureState("makeInvisible", true);
                if (checkEnterPictureInPictureState && !isState(com.android.server.wm.ActivityRecord.State.STARTED, com.android.server.wm.ActivityRecord.State.STOPPING, com.android.server.wm.ActivityRecord.State.STOPPED, com.android.server.wm.ActivityRecord.State.PAUSED)) {
                    z = true;
                } else {
                    z = false;
                }
                setDeferHidingClient(z);
                setVisibility(false);
                switch (com.android.server.wm.ActivityRecord.AnonymousClass6.$SwitchMap$com$android$server$wm$ActivityRecord$State[getState().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 9:
                        addToStopping(true, checkEnterPictureInPictureState, "makeInvisible");
                        break;
                    case 4:
                    case 6:
                        this.supportsEnterPipOnTaskSwitch = false;
                        break;
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown making hidden: " + this.intent.getComponent(), e);
            }
        }
    }

    boolean makeActiveIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        if (shouldResumeActivity(activityRecord)) {
            return getRootTask().resumeTopActivityUncheckedLocked(activityRecord, null);
        }
        if (shouldPauseActivity(activityRecord)) {
            setState(com.android.server.wm.ActivityRecord.State.PAUSING, "makeActiveIfNeeded");
            com.android.server.wm.EventLogTags.writeWmPauseActivity(this.mUserId, java.lang.System.identityHashCode(this), this.shortComponentName, "userLeaving=false", "make-active");
            try {
                this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.PauseActivityItem.obtain(this.token, this.finishing, false, this.configChangeFlags, false, this.mAutoEnteringPip));
                return false;
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending pause: " + this.intent.getComponent(), e);
                return false;
            }
        }
        if (shouldStartActivity()) {
            setState(com.android.server.wm.ActivityRecord.State.STARTED, "makeActiveIfNeeded");
            try {
                this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.StartActivityItem.obtain(this.token, takeSceneTransitionInfo()));
            } catch (java.lang.Exception e2) {
                android.util.Slog.w("ActivityTaskManager", "Exception thrown sending start: " + this.intent.getComponent(), e2);
            }
            this.mTaskSupervisor.mStoppingActivities.remove(this);
            return false;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldPauseActivity(com.android.server.wm.ActivityRecord activityRecord) {
        return shouldMakeActive(activityRecord) && !isFocusable() && !isState(com.android.server.wm.ActivityRecord.State.PAUSING, com.android.server.wm.ActivityRecord.State.PAUSED) && this.results == null;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldResumeActivity(com.android.server.wm.ActivityRecord activityRecord) {
        return shouldBeResumed(activityRecord) && !isState(com.android.server.wm.ActivityRecord.State.RESUMED);
    }

    private boolean shouldBeResumed(com.android.server.wm.ActivityRecord activityRecord) {
        return shouldMakeActive(activityRecord) && isFocusable() && getTaskFragment().getVisibility(activityRecord) == 0 && canResumeByCompat();
    }

    private boolean shouldStartActivity() {
        return this.mVisibleRequested && (isState(com.android.server.wm.ActivityRecord.State.STOPPED) || isState(com.android.server.wm.ActivityRecord.State.STOPPING));
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldMakeActive(com.android.server.wm.ActivityRecord activityRecord) {
        if (!isState(com.android.server.wm.ActivityRecord.State.STARTED, com.android.server.wm.ActivityRecord.State.RESUMED, com.android.server.wm.ActivityRecord.State.PAUSED, com.android.server.wm.ActivityRecord.State.STOPPED, com.android.server.wm.ActivityRecord.State.STOPPING) || getRootTask().mTranslucentActivityWaiting != null || this == activityRecord || !this.mTaskSupervisor.readyToResume() || this.mLaunchTaskBehind) {
            return false;
        }
        if (this.task.hasChild(this)) {
            return getTaskFragment().topRunningActivity() == this;
        }
        throw new java.lang.IllegalStateException("Activity not found in its task");
    }

    void handleAlreadyVisible() {
        try {
            if (this.returningOptions != null && this.returningOptions.getAnimationType() == 5 && this.returningOptions.getSceneTransitionInfo() != null) {
                this.app.getThread().scheduleOnNewSceneTransitionInfo(this.token, this.returningOptions.getSceneTransitionInfo());
            }
        } catch (android.os.RemoteException e) {
        }
    }

    static void activityResumedLocked(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1734586111478674085L, 0, null, java.lang.String.valueOf(forTokenLocked));
        if (forTokenLocked == null) {
            return;
        }
        forTokenLocked.setCustomizeSplashScreenExitAnimation(z);
        forTokenLocked.setSavedState(null);
        forTokenLocked.mDisplayContent.handleActivitySizeCompatModeIfNeeded(forTokenLocked);
        forTokenLocked.mDisplayContent.mUnknownAppVisibilityController.notifyAppResumedFinished(forTokenLocked);
    }

    static void activityRefreshedLocked(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -69666241054231397L, 0, null, java.lang.String.valueOf(forTokenLocked));
        if (forTokenLocked != null && forTokenLocked.mDisplayContent.mDisplayRotationCompatPolicy != null) {
            forTokenLocked.mDisplayContent.mDisplayRotationCompatPolicy.lambda$onActivityConfigurationChanging$0(forTokenLocked);
        }
    }

    static void splashScreenAttachedLocked(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            android.util.Slog.w("ActivityTaskManager", "splashScreenTransferredLocked cannot find activity");
        } else {
            forTokenLocked.onSplashScreenAttachComplete();
        }
    }

    void completeResumeLocked() {
        boolean z = this.mVisibleRequested;
        setVisibility(true);
        if (!z) {
            this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = true;
        }
        this.idle = false;
        this.results = null;
        if (this.newIntents != null && this.newIntents.size() > 0) {
            this.mLastNewIntent = this.newIntents.get(this.newIntents.size() - 1);
        }
        this.newIntents = null;
        if (isActivityTypeHome()) {
            this.mTaskSupervisor.updateHomeProcess(this.task.getBottomMostActivity().app);
        }
        if (this.nowVisible) {
            this.mTaskSupervisor.stopWaitingForActivityVisible(this);
        }
        this.mTaskSupervisor.scheduleIdleTimeout(this);
        this.mTaskSupervisor.reportResumedActivityLocked(this);
        resumeKeyDispatchingLocked();
        com.android.server.wm.Task rootTask = getRootTask();
        this.mTaskSupervisor.mNoAnimActivities.clear();
        this.returningOptions = null;
        if (canTurnScreenOn()) {
            this.mTaskSupervisor.wakeUp("turnScreenOnFlag");
        } else {
            rootTask.checkReadyForSleep();
        }
    }

    void activityPaused(boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1256300416726217367L, 12, null, java.lang.String.valueOf(this.token), java.lang.Boolean.valueOf(z));
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        if (taskFragment != null) {
            removePauseTimeout();
            com.android.server.wm.ActivityRecord pausingActivity = taskFragment.getPausingActivity();
            if (pausingActivity == this) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 6879640870754727133L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(z ? "(due to timeout)" : " (pause complete)"));
                this.mAtmService.deferWindowLayout();
                try {
                    taskFragment.completePause(true, null);
                    return;
                } finally {
                    this.mAtmService.continueWindowLayout();
                }
            }
            com.android.server.wm.EventLogTags.writeWmFailedToPause(this.mUserId, java.lang.System.identityHashCode(this), this.shortComponentName, pausingActivity != null ? pausingActivity.shortComponentName : "(none)");
            if (isState(com.android.server.wm.ActivityRecord.State.PAUSING)) {
                setState(com.android.server.wm.ActivityRecord.State.PAUSED, "activityPausedLocked");
                if (this.finishing) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 2737811012914917932L, 0, null, java.lang.String.valueOf(this));
                    completeFinishing("activityPausedLocked");
                }
            }
        }
        this.mDisplayContent.handleActivitySizeCompatModeIfNeeded(this);
        this.mRootWindowContainer.ensureActivitiesVisible();
    }

    void schedulePauseTimeout() {
        this.pauseTime = android.os.SystemClock.uptimeMillis();
        this.mAtmService.mH.postDelayed(this.mPauseTimeoutRunnable, 500L);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -2566496855129705006L, 0, null, null);
    }

    private void removePauseTimeout() {
        this.mAtmService.mH.removeCallbacks(this.mPauseTimeoutRunnable);
    }

    private void removeDestroyTimeout() {
        this.mAtmService.mH.removeCallbacks(this.mDestroyTimeoutRunnable);
    }

    private void removeStopTimeout() {
        this.mAtmService.mH.removeCallbacks(this.mStopTimeoutRunnable);
    }

    void removeTimeouts() {
        this.mTaskSupervisor.removeIdleTimeoutForActivity(this);
        removePauseTimeout();
        removeStopTimeout();
        removeDestroyTimeout();
        finishLaunchTickingLocked();
    }

    void stopIfPossible() {
        if (this.finishing) {
            android.util.Slog.e("ActivityTaskManager", "Request to stop a finishing activity: " + this);
            destroyIfPossible("stopIfPossible-finishing");
            return;
        }
        if (isNoHistory()) {
            if (!this.task.shouldSleepActivities()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 7498807658620137882L, 0, null, java.lang.String.valueOf(this));
                if (finishIfPossible("stop-no-history", false) != 0) {
                    resumeKeyDispatchingLocked();
                    return;
                }
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3207149655622038378L, 0, null, java.lang.String.valueOf(this));
            }
        }
        if (!attachedToProcess()) {
            return;
        }
        resumeKeyDispatchingLocked();
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -2530718588485487045L, 0, null, java.lang.String.valueOf(this));
            setState(com.android.server.wm.ActivityRecord.State.STOPPING, "stopIfPossible");
            com.android.server.wm.EventLogTags.writeWmStopActivity(this.mUserId, java.lang.System.identityHashCode(this), this.shortComponentName);
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.StopActivityItem.obtain(this.token, this.configChangeFlags));
            this.mAtmService.mH.postDelayed(this.mStopTimeoutRunnable, 11000L);
        } catch (java.lang.Exception e) {
            android.util.Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
            this.mAppStopped = true;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -8424334454318351870L, 0, null, java.lang.String.valueOf(this));
            setState(com.android.server.wm.ActivityRecord.State.STOPPED, "stopIfPossible");
        }
    }

    void activityStopped(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) {
        removeStopTimeout();
        boolean z = this.mState == com.android.server.wm.ActivityRecord.State.STOPPING;
        if (!z && this.mState != com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS) {
            android.util.Slog.i("ActivityTaskManager", "Activity reported stop, but no longer stopping: " + this + " " + this.mState);
            return;
        }
        if (persistableBundle != null) {
            this.mPersistentState = persistableBundle;
            this.mAtmService.notifyTaskPersisterLocked(this.task, false);
        }
        if (bundle != null) {
            setSavedState(bundle);
            this.launchCount = 0;
            updateTaskDescription(charSequence);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -4913512058893421188L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mIcicle));
        if (z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 7613353074402340933L, 0, null, java.lang.String.valueOf(this));
            setState(com.android.server.wm.ActivityRecord.State.STOPPED, "activityStopped");
        }
        this.mAppStopped = true;
        this.firstWindowDrawn = false;
        if (this.task.mLastRecentsAnimationTransaction != null) {
            this.task.clearLastRecentsAnimationTransaction(true);
        }
        this.mDisplayContent.mPinnedTaskController.onActivityHidden(this.mActivityComponent);
        if (isClientVisible()) {
            setClientVisible(false);
        }
        destroySurfaces();
        removeStartingWindow();
        if (this.finishing) {
            abortAndClearOptionsAnimation();
        } else {
            this.mAtmService.updatePreviousProcess(this);
        }
        this.mTaskSupervisor.checkReadyForSleepLocked(true);
    }

    void addToStopping(boolean z, boolean z2, java.lang.String str) {
        if (!this.mTaskSupervisor.mStoppingActivities.contains(this)) {
            com.android.server.wm.EventLogTags.writeWmAddToStopping(this.mUserId, java.lang.System.identityHashCode(this), this.shortComponentName, str);
            this.mTaskSupervisor.mStoppingActivities.add(this);
        }
        com.android.server.wm.Task rootTask = getRootTask();
        boolean z3 = true;
        if (this.mTaskSupervisor.mStoppingActivities.size() <= 3 && (!isRootOfTask() || rootTask.getChildCount() > 1)) {
            z3 = false;
        }
        if (z || z3) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3981777934616509782L, 15, null, java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(!z2));
            if (!z2) {
                this.mTaskSupervisor.scheduleIdle();
                return;
            } else {
                this.mTaskSupervisor.scheduleIdleTimeout(this);
                return;
            }
        }
        rootTask.checkReadyForSleep();
    }

    void startLaunchTickingLocked() {
        if (!android.os.Build.IS_USER && this.launchTickTime == 0) {
            this.launchTickTime = android.os.SystemClock.uptimeMillis();
            continueLaunchTicking();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean continueLaunchTicking() {
        com.android.server.wm.Task rootTask;
        if (this.launchTickTime == 0 || (rootTask = getRootTask()) == null) {
            return false;
        }
        rootTask.removeLaunchTickMessages();
        this.mAtmService.mH.postDelayed(this.mLaunchTickRunnable, 500L);
        return true;
    }

    void removeLaunchTickRunnable() {
        this.mAtmService.mH.removeCallbacks(this.mLaunchTickRunnable);
    }

    void finishLaunchTickingLocked() {
        this.launchTickTime = 0L;
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask == null) {
            return;
        }
        rootTask.removeLaunchTickMessages();
    }

    boolean mayFreezeScreenLocked() {
        return mayFreezeScreenLocked(this.app);
    }

    private boolean mayFreezeScreenLocked(com.android.server.wm.WindowProcessController windowProcessController) {
        return (!hasProcess() || windowProcessController.isCrashing() || windowProcessController.isNotResponding()) ? false : true;
    }

    void startFreezingScreenLocked(com.android.server.wm.WindowProcessController windowProcessController, int i) {
        if (mayFreezeScreenLocked(windowProcessController)) {
            if (getParent() == null) {
                android.util.Slog.w("WindowManager", "Attempted to freeze screen with non-existing app token: " + this.token);
                return;
            }
            if (((-536870913) & i) == 0 && okToDisplay()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 1083992181663415298L, 0, null, java.lang.String.valueOf(this.token));
            } else {
                startFreezingScreen();
            }
        }
    }

    void startFreezingScreen() {
        startFreezingScreen(-1);
    }

    void startFreezingScreen(int i) {
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 3713860954819212080L, android.hardware.audio.common.V2_0.AudioChannelMask.IN_6, null, java.lang.String.valueOf(this.token), java.lang.Boolean.valueOf(isVisible()), java.lang.Boolean.valueOf(this.mFreezingScreen), java.lang.Boolean.valueOf(this.mVisibleRequested), java.lang.String.valueOf(new java.lang.RuntimeException().fillInStackTrace()));
        if (!this.mVisibleRequested) {
            return;
        }
        boolean z = i != -1;
        if (!this.mFreezingScreen) {
            this.mFreezingScreen = true;
            this.mWmService.registerAppFreezeListener(this);
            this.mWmService.mAppsFreezingScreen++;
            if (this.mWmService.mAppsFreezingScreen == 1) {
                if (z) {
                    this.mDisplayContent.getDisplayRotation().cancelSeamlessRotation();
                }
                this.mWmService.startFreezingDisplay(0, 0, this.mDisplayContent, i);
                this.mWmService.mH.removeMessages(17);
                this.mWmService.mH.sendEmptyMessageDelayed(17, 2000L);
            }
        }
        if (z) {
            return;
        }
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((com.android.server.wm.WindowState) this.mChildren.get(i2)).onStartFreezingScreen();
        }
    }

    boolean isFreezingScreen() {
        return this.mFreezingScreen;
    }

    @Override // com.android.server.wm.WindowManagerService.AppFreezeListener
    public void onAppFreezeTimeout() {
        android.util.Slog.w("WindowManager", "Force clearing freeze: " + this);
        stopFreezingScreen(true, true);
    }

    void stopFreezingScreen(boolean z, boolean z2) {
        if (!this.mFreezingScreen) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7696002120820208745L, 12, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(z2));
        int size = this.mChildren.size();
        boolean z3 = false;
        for (int i = 0; i < size; i++) {
            z3 |= ((com.android.server.wm.WindowState) this.mChildren.get(i)).onStopFreezingScreen();
        }
        if (z2 || z3) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -8387262166329116492L, 0, null, java.lang.String.valueOf(this));
            this.mFreezingScreen = false;
            this.mWmService.unregisterAppFreezeListener(this);
            com.android.server.wm.WindowManagerService windowManagerService = this.mWmService;
            windowManagerService.mAppsFreezingScreen--;
            this.mWmService.mLastFinishedFreezeSource = this;
        }
        if (z) {
            if (z3) {
                this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
            }
            this.mWmService.stopFreezingDisplayLocked();
        }
    }

    void onFirstWindowDrawn(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.ActivityRecord activityRecord;
        this.firstWindowDrawn = true;
        this.mSplashScreenStyleSolidColor = true;
        this.mAtmService.mBackNavigationController.removePredictiveSurfaceIfNeeded(this);
        if (this.mStartingWindow != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -6965298896142649709L, 0, null, java.lang.String.valueOf(windowState.mToken));
            windowState.cancelAnimation();
        }
        com.android.server.wm.Task task = this.task.mSharedStartingData != null ? this.task : null;
        if (task == null) {
            removeStartingWindow();
        } else if (task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onFirstWindowDrawn$12;
                lambda$onFirstWindowDrawn$12 = com.android.server.wm.ActivityRecord.lambda$onFirstWindowDrawn$12((com.android.server.wm.ActivityRecord) obj);
                return lambda$onFirstWindowDrawn$12;
            }
        }) == null && (activityRecord = task.topActivityContainsStartingWindow()) != null) {
            activityRecord.removeStartingWindow();
        }
        updateReportedVisibilityLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onFirstWindowDrawn$12(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.isVisibleRequested() && !activityRecord.firstWindowDrawn;
    }

    private boolean setTaskHasBeenVisible() {
        if (this.task.getHasBeenVisible()) {
            return false;
        }
        if (inTransition()) {
            this.task.setDeferTaskAppear(true);
        }
        this.task.setHasBeenVisible(true);
        return true;
    }

    void onStartingWindowDrawn() {
        boolean z;
        if (this.task == null) {
            z = false;
        } else {
            this.mSplashScreenStyleSolidColor = true;
            z = !setTaskHasBeenVisible();
        }
        if (!z && this.mStartingData != null && !this.finishing && !this.mLaunchedFromBubble && this.mVisibleRequested && !this.mDisplayContent.mAppTransition.isReady() && !this.mDisplayContent.mAppTransition.isRunning() && this.mDisplayContent.isNextTransitionForward()) {
            this.mStartingData.mIsTransitionForward = true;
            if (this != this.mDisplayContent.getLastOrientationSource()) {
                this.mDisplayContent.updateOrientation();
            }
            this.mDisplayContent.executeAppTransition();
        }
    }

    private void onWindowsDrawn() {
        com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot notifyWindowsDrawn = this.mTaskSupervisor.getActivityMetricsLogger().notifyWindowsDrawn(this);
        boolean z = notifyWindowsDrawn != null;
        int i = z ? notifyWindowsDrawn.windowsDrawnDelayMs : -1;
        int launchState = z ? notifyWindowsDrawn.getLaunchState() : 0;
        if (z || this == getDisplayArea().topRunningActivity()) {
            this.mTaskSupervisor.reportActivityLaunched(false, this, i, launchState);
        }
        finishLaunchTickingLocked();
        if (this.task != null) {
            setTaskHasBeenVisible();
        }
        this.mLaunchRootTask = null;
    }

    void onWindowsVisible() {
        this.mTaskSupervisor.stopWaitingForActivityVisible(this);
        if (!this.nowVisible) {
            this.nowVisible = true;
            this.lastVisibleTime = android.os.SystemClock.uptimeMillis();
            this.mAtmService.scheduleAppGcsLocked();
            this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
            if (this.mImeInsetsFrozenUntilStartInput && getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onWindowsVisible$13;
                    lambda$onWindowsVisible$13 = com.android.server.wm.ActivityRecord.lambda$onWindowsVisible$13((com.android.server.wm.WindowState) obj);
                    return lambda$onWindowsVisible$13;
                }
            }) == null) {
                this.mImeInsetsFrozenUntilStartInput = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onWindowsVisible$13(com.android.server.wm.WindowState windowState) {
        return android.view.WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags);
    }

    void onWindowsGone() {
        this.nowVisible = false;
    }

    @Override // com.android.server.wm.WindowContainer
    void checkAppWindowsReadyToShow() {
        if (this.allDrawn == this.mLastAllDrawn) {
            return;
        }
        this.mLastAllDrawn = this.allDrawn;
        if (!this.allDrawn) {
            return;
        }
        if (this.mFreezingScreen) {
            showAllWindowsLocked();
            stopFreezingScreen(false, true);
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 3235691043029201724L, 20, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(this.mNumInterestingWindows), java.lang.Long.valueOf(this.mNumDrawnWindows));
            setAppLayoutChanges(4, "checkAppWindowsReadyToShow: freezingScreen");
            return;
        }
        setAppLayoutChanges(8, "checkAppWindowsReadyToShow");
        if (!getDisplayContent().mOpeningApps.contains(this) && canShowWindows()) {
            showAllWindowsLocked();
        }
    }

    void showAllWindowsLocked() {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).performShowLocked();
            }
        }, false);
    }

    void updateReportedVisibilityLocked() {
        boolean z;
        int size = this.mChildren.size();
        this.mReportedVisibilityResults.reset();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            ((com.android.server.wm.WindowState) this.mChildren.get(i)).updateReportedVisibility(this.mReportedVisibilityResults);
        }
        int i2 = this.mReportedVisibilityResults.numInteresting;
        int i3 = this.mReportedVisibilityResults.numVisible;
        int i4 = this.mReportedVisibilityResults.numDrawn;
        boolean z3 = this.mReportedVisibilityResults.nowGone;
        if (i2 <= 0 || i4 < i2) {
            z = false;
        } else {
            z = true;
        }
        if (i2 > 0 && i3 >= i2 && isVisible()) {
            z2 = true;
        }
        if (!z3) {
            if (!z) {
                z = this.mReportedDrawn;
            }
            if (!z2) {
                z2 = this.reportedVisible;
            }
        }
        if (z != this.mReportedDrawn) {
            if (z) {
                onWindowsDrawn();
            }
            this.mReportedDrawn = z;
        }
        if (z2 != this.reportedVisible) {
            this.reportedVisible = z2;
            if (z2) {
                onWindowsVisible();
            } else {
                onWindowsGone();
            }
        }
    }

    boolean isReportedDrawn() {
        return this.mReportedDrawn;
    }

    @Override // com.android.server.wm.WindowToken
    void setClientVisible(boolean z) {
        if (z || !this.mDeferHidingClient) {
            super.setClientVisible(z);
        }
    }

    boolean updateDrawnWindowStates(com.android.server.wm.WindowState windowState) {
        windowState.setDrawnStateEvaluated(true);
        if (this.allDrawn && !this.mFreezingScreen) {
            return false;
        }
        if (this.mLastTransactionSequence != this.mWmService.mTransactionSequence) {
            this.mLastTransactionSequence = this.mWmService.mTransactionSequence;
            this.mNumDrawnWindows = 0;
            this.mNumInterestingWindows = findMainWindow(false) != null ? 1 : 0;
        }
        com.android.server.wm.WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        if (!this.allDrawn && windowState.mightAffectAllDrawn()) {
            if (com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION.isLogToLogcat()) {
                boolean isAnimating = isAnimating(3, 1);
                android.util.Slog.v("ActivityTaskManager", "Eval win " + windowState + ": isDrawn=" + windowState.isDrawn() + ", isAnimationSet=" + isAnimating);
                if (!windowState.isDrawn()) {
                    android.util.Slog.v("ActivityTaskManager", "Not displayed: s=" + windowStateAnimator.mSurfaceController + " pv=" + windowState.isVisibleByPolicy() + " mDrawState=" + windowStateAnimator.drawStateToString() + " ph=" + windowState.isParentWindowHidden() + " th=" + this.mVisibleRequested + " a=" + isAnimating);
                }
            }
            if (windowState != this.mStartingWindow) {
                if (windowState.isInteresting()) {
                    if (findMainWindow(false) != windowState) {
                        this.mNumInterestingWindows++;
                    }
                    if (windowState.isDrawn()) {
                        this.mNumDrawnWindows++;
                        if (!com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION.isLogToLogcat()) {
                            return true;
                        }
                        android.util.Slog.v("ActivityTaskManager", "tokenMayBeDrawn: " + this + " w=" + windowState + " numInteresting=" + this.mNumInterestingWindows + " freezingScreen=" + this.mFreezingScreen + " mAppFreezing=" + windowState.mAppFreezing);
                        return true;
                    }
                }
            } else if (this.mStartingData != null && windowState.isDrawn()) {
                this.mStartingData.mIsDisplayed = true;
            }
        }
        return false;
    }

    public boolean inputDispatchingTimedOut(com.android.internal.os.TimeoutRecord timeoutRecord, int i) {
        com.android.server.wm.ActivityRecord waitingHistoryRecordLocked;
        com.android.server.wm.WindowProcessController windowProcessController;
        boolean z;
        try {
            android.os.Trace.traceBegin(64L, "ActivityRecord#inputDispatchingTimedOut()");
            timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
                    waitingHistoryRecordLocked = getWaitingHistoryRecordLocked();
                    windowProcessController = this.app;
                    z = hasProcess() && (this.app.getPid() == i || i == -1);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            if (z) {
                return this.mAtmService.mAmInternal.inputDispatchingTimedOut(windowProcessController.mOwner, waitingHistoryRecordLocked.shortComponentName, waitingHistoryRecordLocked.info.applicationInfo, this.shortComponentName, this.app, false, timeoutRecord);
            }
            return this.mAtmService.mAmInternal.inputDispatchingTimedOut(i, false, timeoutRecord) <= 0;
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    private com.android.server.wm.ActivityRecord getWaitingHistoryRecordLocked() {
        if (this.mAppStopped) {
            com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask == null) {
                return this;
            }
            com.android.server.wm.ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
            if (topResumedActivity == null) {
                topResumedActivity = topDisplayFocusedRootTask.getTopPausingActivity();
            }
            if (topResumedActivity != null) {
                return topResumedActivity;
            }
        }
        return this;
    }

    boolean canBeTopRunning() {
        return !this.finishing && showToCurrentUser();
    }

    public boolean isInterestingToUserLocked() {
        return this.mVisibleRequested || this.nowVisible || this.mState == com.android.server.wm.ActivityRecord.State.PAUSING || this.mState == com.android.server.wm.ActivityRecord.State.RESUMED;
    }

    static int getTaskForActivityLocked(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked == null || forTokenLocked.getParent() == null) {
            return -1;
        }
        com.android.server.wm.Task task = forTokenLocked.task;
        if (z && forTokenLocked.compareTo((com.android.server.wm.WindowContainer) task.getRootActivity(false, true)) > 0) {
            return -1;
        }
        return task.mTaskId;
    }

    static com.android.server.wm.ActivityRecord isInRootTaskLocked(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked != null) {
            return forTokenLocked.getRootTask().isInTask(forTokenLocked);
        }
        return null;
    }

    static com.android.server.wm.Task getRootTask(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord isInRootTaskLocked = isInRootTaskLocked(iBinder);
        if (isInRootTaskLocked != null) {
            return isInRootTaskLocked.getRootTask();
        }
        return null;
    }

    @android.annotation.Nullable
    static com.android.server.wm.ActivityRecord isInAnyTask(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.isAttached()) {
            return null;
        }
        return forTokenLocked;
    }

    int getDisplayId() {
        if (this.task == null || this.task.mDisplayContent == null) {
            return -1;
        }
        return this.task.mDisplayContent.mDisplayId;
    }

    final boolean isDestroyable() {
        return (this.finishing || !hasProcess() || isState(com.android.server.wm.ActivityRecord.State.RESUMED) || getRootTask() == null || this == getTaskFragment().getPausingActivity() || !this.mHaveState || !this.mAppStopped || this.mVisibleRequested) ? false : true;
    }

    private static java.lang.String createImageFilename(long j, int i) {
        return java.lang.String.valueOf(i) + ACTIVITY_ICON_SUFFIX + j + ".png";
    }

    void setTaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
        android.graphics.Bitmap icon;
        if (taskDescription.getIconFilename() == null && (icon = taskDescription.getIcon()) != null) {
            java.lang.String absolutePath = new java.io.File(com.android.server.wm.TaskPersister.getUserImagesDir(this.task.mUserId), createImageFilename(this.createTime, this.task.mTaskId)).getAbsolutePath();
            this.mAtmService.getRecentTasks().saveImage(icon, absolutePath);
            taskDescription.setIconFilename(absolutePath);
        }
        this.taskDescription = taskDescription;
        getTask().updateTaskDescription();
    }

    void setLocusId(android.content.LocusId locusId) {
        if (java.util.Objects.equals(locusId, this.mLocusId)) {
            return;
        }
        this.mLocusId = locusId;
        if (getTask() != null) {
            getTask().dispatchTaskInfoChangedIfNeeded(false);
        }
    }

    android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    public void reportScreenCaptured() {
        if (this.mCaptureCallbacks != null) {
            int beginBroadcast = this.mCaptureCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mCaptureCallbacks.getBroadcastItem(i).onScreenCaptured();
                } catch (android.os.RemoteException e) {
                }
            }
            this.mCaptureCallbacks.finishBroadcast();
        }
    }

    public void registerCaptureObserver(android.app.IScreenCaptureObserver iScreenCaptureObserver) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mCaptureCallbacks == null) {
                    this.mCaptureCallbacks = new android.os.RemoteCallbackList<>();
                }
                this.mCaptureCallbacks.register(iScreenCaptureObserver);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterCaptureObserver(android.app.IScreenCaptureObserver iScreenCaptureObserver) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mCaptureCallbacks != null) {
                    this.mCaptureCallbacks.unregister(iScreenCaptureObserver);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean isRegisteredForScreenCaptureCallback() {
        return this.mCaptureCallbacks != null && this.mCaptureCallbacks.getRegisteredCallbackCount() > 0;
    }

    void setVoiceSessionLocked(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) {
        this.voiceSession = iVoiceInteractionSession;
        this.pendingVoiceInteractionStart = false;
    }

    void clearVoiceSessionLocked() {
        this.voiceSession = null;
        this.pendingVoiceInteractionStart = false;
    }

    void showStartingWindow(boolean z) {
        showStartingWindow(this.task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$showStartingWindow$15;
                lambda$showStartingWindow$15 = com.android.server.wm.ActivityRecord.this.lambda$showStartingWindow$15((com.android.server.wm.ActivityRecord) obj);
                return lambda$showStartingWindow$15;
            }
        }), false, z, false, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showStartingWindow$15(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord == this || activityRecord.mStartingData == null || !activityRecord.showToCurrentUser()) ? false : true;
    }

    private com.android.server.wm.ActivityRecord searchCandidateLaunchingActivity() {
        com.android.server.wm.WindowProcessController windowProcessController;
        com.android.server.wm.ActivityRecord activityBelow = this.task.getActivityBelow(this);
        if (activityBelow == null) {
            activityBelow = this.task.getParent().getActivityBelow(this);
        }
        if (activityBelow == null || activityBelow.isActivityTypeHome()) {
            return null;
        }
        com.android.server.wm.WindowProcessController windowProcessController2 = this.app != null ? this.app : (com.android.server.wm.WindowProcessController) this.mAtmService.mProcessNames.get(this.processName, this.info.applicationInfo.uid);
        if (activityBelow.app != null) {
            windowProcessController = activityBelow.app;
        } else {
            windowProcessController = (com.android.server.wm.WindowProcessController) this.mAtmService.mProcessNames.get(activityBelow.processName, activityBelow.info.applicationInfo.uid);
        }
        if (windowProcessController != windowProcessController2 && !this.mActivityComponent.getPackageName().equals(activityBelow.mActivityComponent.getPackageName())) {
            return null;
        }
        return activityBelow;
    }

    private boolean isIconStylePreferred(int i) {
        com.android.internal.policy.AttributeCache.Entry entry;
        return i != 0 && (entry = com.android.internal.policy.AttributeCache.instance().get(this.packageName, i, com.android.internal.R.styleable.Window, this.mWmService.mCurrentUserId)) != null && entry.array.hasValue(61) && entry.array.getInt(61, 0) == 1;
    }

    private boolean shouldUseSolidColorSplashScreen(com.android.server.wm.ActivityRecord activityRecord, boolean z, android.app.ActivityOptions activityOptions, int i) {
        if (activityRecord == null && !z && this.task.getActivityAbove(this) != null) {
            return true;
        }
        int splashScreenStyle = activityOptions != null ? activityOptions.getSplashScreenStyle() : -1;
        if (splashScreenStyle == 0) {
            return true;
        }
        if (splashScreenStyle == 1 || isIconStylePreferred(i) || this.mLaunchSourceType == 2 || this.launchedFromUid == 2000) {
            return false;
        }
        if (this.mLaunchSourceType == 3) {
            return true;
        }
        if (activityRecord == null) {
            activityRecord = searchCandidateLaunchingActivity();
        }
        if (activityRecord == null) {
            if (this.mLaunchSourceType != 1 || !z) {
                return true;
            }
            return false;
        }
        return activityRecord.mSplashScreenStyleSolidColor;
    }

    private int getSplashscreenTheme(android.app.ActivityOptions activityOptions) {
        java.lang.String str;
        if (activityOptions == null) {
            str = null;
        } else {
            str = activityOptions.getSplashScreenThemeResName();
        }
        if (str == null || str.isEmpty()) {
            try {
                str = this.mAtmService.getPackageManager().getSplashScreenTheme(this.packageName, this.mUserId);
            } catch (android.os.RemoteException e) {
            }
        }
        if (str == null || str.isEmpty()) {
            return 0;
        }
        try {
            return this.mAtmService.mContext.createPackageContext(this.packageName, 0).getResources().getIdentifier(str, null, null);
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e2) {
            return 0;
        }
    }

    void showStartingWindow(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, com.android.server.wm.ActivityRecord activityRecord2) {
        showStartingWindow(activityRecord, z, z2, isProcessRunning(), z3, activityRecord2, null);
    }

    void showStartingWindow(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, boolean z4, com.android.server.wm.ActivityRecord activityRecord2, android.app.ActivityOptions activityOptions) {
        android.app.ActivityOptions activityOptions2;
        if (this.mTaskOverlay) {
            return;
        }
        if (activityOptions == null) {
            activityOptions2 = this.mPendingOptions;
        } else {
            activityOptions2 = activityOptions;
        }
        if (activityOptions2 == null || activityOptions2.getAnimationType() != 5) {
            int evaluateStartingWindowTheme = evaluateStartingWindowTheme(activityRecord, this.packageName, this.theme, z4 ? getSplashscreenTheme(activityOptions2) : 0);
            this.mSplashScreenStyleSolidColor = shouldUseSolidColorSplashScreen(activityRecord2, z4, activityOptions2, evaluateStartingWindowTheme);
            boolean z5 = true;
            boolean z6 = this.mState.ordinal() >= com.android.server.wm.ActivityRecord.State.STARTED.ordinal() && this.mState.ordinal() <= com.android.server.wm.ActivityRecord.State.STOPPED.ordinal();
            boolean z7 = (z || z6 || this.task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda25
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$showStartingWindow$16;
                    lambda$showStartingWindow$16 = com.android.server.wm.ActivityRecord.this.lambda$showStartingWindow$16((com.android.server.wm.ActivityRecord) obj);
                    return lambda$showStartingWindow$16;
                }
            }) != null) ? false : true;
            java.lang.String str = this.packageName;
            if (!z && !z7) {
                z5 = false;
            }
            addStartingWindow(str, evaluateStartingWindowTheme, activityRecord, z5, z2, z3, allowTaskSnapshot(), z6, this.mSplashScreenStyleSolidColor, this.allDrawn);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showStartingWindow$16(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord == this) ? false : true;
    }

    void cancelInitializing() {
        if (this.mStartingData != null) {
            removeStartingWindowAnimation(false);
        }
        if (!this.mDisplayContent.mUnknownAppVisibilityController.allResolved()) {
            this.mDisplayContent.mUnknownAppVisibilityController.appRemovedOrHidden(this);
        }
    }

    void postWindowRemoveStartingWindowCleanup(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        if (this.mStartingWindow == windowState) {
            this.mStartingWindow = null;
        }
        if (this.mChildren.size() == 0 && this.mVisibleSetFromTransferredStartingWindow) {
            setVisible(false);
        }
    }

    void requestUpdateWallpaperIfNeeded() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    com.android.server.wm.WindowState getTopFullscreenOpaqueWindow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
            if (windowState != null && windowState.mAttrs.isFullscreen() && !windowState.isFullyTransparent()) {
                return windowState;
            }
        }
        return null;
    }

    com.android.server.wm.WindowState findMainWindow() {
        return findMainWindow(true);
    }

    com.android.server.wm.WindowState findMainWindow(boolean z) {
        com.android.server.wm.WindowState windowState = null;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState2 = (com.android.server.wm.WindowState) this.mChildren.get(size);
            int i = windowState2.mAttrs.type;
            if (i == 1 || (z && i == 3)) {
                if (windowState2.mAnimatingExit) {
                    windowState = windowState2;
                } else {
                    return windowState2;
                }
            }
        }
        return windowState;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean needsZBoost() {
        return this.mNeedsZBoost || super.needsZBoost();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getAnimationLeashParent() {
        if (inPinnedWindowingMode()) {
            return getRootTask().getSurfaceControl();
        }
        return super.getAnimationLeashParent();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldAnimate() {
        return this.task == null || this.task.shouldAnimate();
    }

    private android.view.SurfaceControl createAnimationBoundsLayer(android.view.SurfaceControl.Transaction transaction) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 5991628884266137609L, 0, null, null);
        android.view.SurfaceControl.Builder callsite = makeAnimationLeash().setParent(getAnimationLeashParent()).setName(getSurfaceControl() + " - animation-bounds").setCallsite("ActivityRecord.createAnimationBoundsLayer");
        if (this.mNeedsLetterboxedAnimation) {
            callsite.setEffectLayer();
        }
        android.view.SurfaceControl build = callsite.build();
        transaction.show(build);
        return build;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
        return this.mAnimatingActivityRegistry != null && this.mAnimatingActivityRegistry.notifyAboutToFinish(this, runnable);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isWaitingForTransitionStart() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.mAppTransition.isTransitionSet() && (displayContent.mOpeningApps.contains(this) || displayContent.mClosingApps.contains(this) || displayContent.mChangingContainers.contains(this));
    }

    boolean isTransitionForward() {
        return (this.mStartingData != null && this.mStartingData.mIsTransitionForward) || this.mDisplayContent.isNextTransitionForward();
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    void resetSurfacePositionForAnimationLeash(android.view.SurfaceControl.Transaction transaction) {
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onLeashAnimationStarting(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (this.mAnimatingActivityRegistry != null) {
            this.mAnimatingActivityRegistry.notifyStarting(this);
        }
        if (this.mNeedsLetterboxedAnimation) {
            updateLetterboxSurfaceIfNeeded(findMainWindow(), transaction);
            this.mNeedsAnimationBoundsLayer = true;
        }
        if (this.mNeedsAnimationBoundsLayer) {
            ((com.android.server.wm.WindowContainer) this).mTmpRect.setEmpty();
            if (getDisplayContent().mAppTransitionController.isTransitWithinTask(getTransit(), this.task)) {
                this.task.getBounds(((com.android.server.wm.WindowContainer) this).mTmpRect);
            } else {
                com.android.server.wm.Task rootTask = getRootTask();
                if (rootTask == null) {
                    return;
                } else {
                    rootTask.getBounds(((com.android.server.wm.WindowContainer) this).mTmpRect);
                }
            }
            this.mAnimationBoundsLayer = createAnimationBoundsLayer(transaction);
            transaction.setLayer(surfaceControl, 0);
            transaction.setLayer(this.mAnimationBoundsLayer, getLastLayer());
            if (this.mNeedsLetterboxedAnimation) {
                int roundedCornersRadius = this.mLetterboxUiController.getRoundedCornersRadius(findMainWindow());
                android.graphics.Rect rect = new android.graphics.Rect();
                getLetterboxInnerBounds(rect);
                transaction.setCornerRadius(this.mAnimationBoundsLayer, roundedCornersRadius).setCrop(this.mAnimationBoundsLayer, rect);
            }
            transaction.reparent(surfaceControl, this.mAnimationBoundsLayer);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showSurfaceOnCreation() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void prepareSurfaces() {
        boolean z = isVisible() || isAnimating(2, com.android.internal.util.FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED);
        if (this.mSurfaceControl != null) {
            if (z && !this.mLastSurfaceShowing) {
                getSyncTransaction().show(this.mSurfaceControl);
            } else if (!z && this.mLastSurfaceShowing) {
                getSyncTransaction().hide(this.mSurfaceControl);
            }
            if (z && this.mSyncState == 0) {
                this.mActivityRecordInputSink.applyChangesToSurfaceIfChanged(getPendingTransaction());
            }
        }
        if (this.mThumbnail != null) {
            this.mThumbnail.setShowing(getPendingTransaction(), z);
        }
        this.mLastSurfaceShowing = z;
        super.prepareSurfaces();
    }

    boolean isSurfaceShowing() {
        return this.mLastSurfaceShowing;
    }

    void attachThumbnailAnimation() {
        if (!isAnimating(2, 1)) {
            return;
        }
        android.hardware.HardwareBuffer appTransitionThumbnailHeader = getDisplayContent().mAppTransition.getAppTransitionThumbnailHeader(this.task);
        if (appTransitionThumbnailHeader == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -1836789237982086339L, 0, null, java.lang.String.valueOf(this.task));
        } else {
            clearThumbnail();
            android.view.SurfaceControl.Transaction pendingTransaction = getAnimatingContainer().getPendingTransaction();
            this.mThumbnail = new com.android.server.wm.WindowContainerThumbnail(pendingTransaction, getAnimatingContainer(), appTransitionThumbnailHeader);
            this.mThumbnail.startAnimation(pendingTransaction, loadThumbnailAnimation(appTransitionThumbnailHeader));
        }
    }

    void attachCrossProfileAppsThumbnailAnimation() {
        android.graphics.drawable.Drawable drawable;
        if (!isAnimating(2, 1)) {
            return;
        }
        clearThumbnail();
        com.android.server.wm.WindowState findMainWindow = findMainWindow();
        if (findMainWindow == null) {
            return;
        }
        android.graphics.Rect relativeFrame = findMainWindow.getRelativeFrame();
        final android.content.Context uiContext = this.mAtmService.getUiContext();
        if (this.task.mUserId == this.mWmService.mCurrentUserId) {
            drawable = uiContext.getDrawable(android.R.drawable.ic_accessibility_reduce_bright_colors_foreground);
        } else {
            drawable = ((android.app.admin.DevicePolicyManager) uiContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new java.util.function.Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.graphics.drawable.Drawable lambda$attachCrossProfileAppsThumbnailAnimation$17;
                    lambda$attachCrossProfileAppsThumbnailAnimation$17 = com.android.server.wm.ActivityRecord.lambda$attachCrossProfileAppsThumbnailAnimation$17(uiContext);
                    return lambda$attachCrossProfileAppsThumbnailAnimation$17;
                }
            });
        }
        android.hardware.HardwareBuffer createCrossProfileAppsThumbnail = getDisplayContent().mAppTransition.createCrossProfileAppsThumbnail(drawable, relativeFrame);
        if (createCrossProfileAppsThumbnail == null) {
            return;
        }
        android.view.SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        this.mThumbnail = new com.android.server.wm.WindowContainerThumbnail(pendingTransaction, getTask(), createCrossProfileAppsThumbnail);
        this.mThumbnail.startAnimation(pendingTransaction, getDisplayContent().mAppTransition.createCrossProfileAppsThumbnailAnimationLocked(relativeFrame), new android.graphics.Point(relativeFrame.left, relativeFrame.top));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.graphics.drawable.Drawable lambda$attachCrossProfileAppsThumbnailAnimation$17(android.content.Context context) {
        return context.getDrawable(android.R.drawable.ic_contact_picture_holo_dark);
    }

    private android.view.animation.Animation loadThumbnailAnimation(android.hardware.HardwareBuffer hardwareBuffer) {
        android.graphics.Rect rect;
        android.graphics.Rect rect2;
        android.view.DisplayInfo displayInfo = this.mDisplayContent.getDisplayInfo();
        com.android.server.wm.WindowState findMainWindow = findMainWindow();
        if (findMainWindow != null) {
            android.graphics.Rect rect3 = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(findMainWindow.getFrame(), android.view.WindowInsets.Type.systemBars(), false).toRect();
            android.graphics.Rect rect4 = new android.graphics.Rect(findMainWindow.getFrame());
            rect4.inset(rect3);
            rect = rect3;
            rect2 = rect4;
        } else {
            rect = null;
            rect2 = new android.graphics.Rect(0, 0, displayInfo.appWidth, displayInfo.appHeight);
        }
        return getDisplayContent().mAppTransition.createThumbnailAspectScaleAnimationLocked(rect2, rect, hardwareBuffer, this.task, this.mDisplayContent.getConfiguration().orientation);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
        if (this.mAnimationBoundsLayer != null) {
            transaction.remove(this.mAnimationBoundsLayer);
            this.mAnimationBoundsLayer = null;
        }
        this.mNeedsAnimationBoundsLayer = false;
        if (this.mNeedsLetterboxedAnimation) {
            this.mNeedsLetterboxedAnimation = false;
            updateLetterboxSurfaceIfNeeded(findMainWindow(), transaction);
        }
        if (this.mAnimatingActivityRegistry != null) {
            this.mAnimatingActivityRegistry.notifyFinished(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    protected void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        com.android.server.wm.WindowState window;
        super.onAnimationFinished(i, animationAdapter);
        android.os.Trace.traceBegin(32L, "AR#onAnimationFinished");
        this.mTransit = -1;
        this.mTransitFlags = 0;
        setAppLayoutChanges(12, "ActivityRecord");
        clearThumbnail();
        setClientVisible(isVisible() || this.mVisibleRequested);
        getDisplayContent().computeImeTargetIfNeeded(this);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -8809523216004991008L, 1020, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(this.reportedVisible), java.lang.Boolean.valueOf(okToDisplay()), java.lang.Boolean.valueOf(okToAnimate()), java.lang.Boolean.valueOf(isStartingWindowDisplayed()));
        if (this.mThumbnail != null) {
            this.mThumbnail.destroy();
            this.mThumbnail = null;
        }
        new java.util.ArrayList(this.mChildren).forEach(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).onExitAnimationDone();
            }
        });
        if (this.task != null && this.startingMoved && (window = this.task.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onAnimationFinished$18;
                lambda$onAnimationFinished$18 = com.android.server.wm.ActivityRecord.lambda$onAnimationFinished$18((com.android.server.wm.WindowState) obj);
                return lambda$onAnimationFinished$18;
            }
        })) != null && window.mAnimatingExit && !window.isSelfAnimating(0, 16)) {
            window.onExitAnimationDone();
        }
        getDisplayContent().mAppTransition.notifyAppTransitionFinishedLocked(this.token);
        scheduleAnimation();
        this.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
        android.os.Trace.traceEnd(32L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onAnimationFinished$18(com.android.server.wm.WindowState windowState) {
        return windowState.mAttrs.type == 3;
    }

    void clearAnimatingFlags() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).clearAnimatingFlags();
        }
        if (z) {
            requestUpdateWallpaperIfNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void cancelAnimation() {
        super.cancelAnimation();
        clearThumbnail();
    }

    private void clearThumbnail() {
        if (this.mThumbnail == null) {
            return;
        }
        this.mThumbnail.destroy();
        this.mThumbnail = null;
    }

    public int getTransit() {
        return this.mTransit;
    }

    void registerRemoteAnimations(android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mRemoteAnimationDefinition = remoteAnimationDefinition;
        if (remoteAnimationDefinition != null) {
            remoteAnimationDefinition.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda14
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.wm.ActivityRecord.this.unregisterRemoteAnimations();
                }
            });
        }
    }

    void unregisterRemoteAnimations() {
        this.mRemoteAnimationDefinition = null;
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.RemoteAnimationDefinition getRemoteAnimationDefinition() {
        return this.mRemoteAnimationDefinition;
    }

    @Override // com.android.server.wm.WindowToken
    void applyFixedRotationTransform(android.view.DisplayInfo displayInfo, com.android.server.wm.DisplayFrames displayFrames, android.content.res.Configuration configuration) {
        super.applyFixedRotationTransform(displayInfo, displayFrames, configuration);
        ensureActivityConfiguration();
    }

    @Override // com.android.server.wm.WindowContainer
    int getRequestedConfigurationOrientation(boolean z) {
        return getRequestedConfigurationOrientation(z, getOverrideOrientation());
    }

    @Override // com.android.server.wm.WindowContainer
    int getRequestedConfigurationOrientation(boolean z, int i) {
        com.android.server.wm.ActivityRecord activity;
        if (this.mLetterboxUiController.hasInheritedOrientation()) {
            com.android.server.wm.RootDisplayArea rootDisplayArea = getRootDisplayArea();
            if (z && rootDisplayArea != null && rootDisplayArea.isOrientationDifferentFromDisplay()) {
                return reverseConfigurationOrientation(this.mLetterboxUiController.getInheritedOrientation());
            }
            return this.mLetterboxUiController.getInheritedOrientation();
        }
        if (this.task != null && i == 3 && (activity = this.task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean canDefineOrientationForActivitiesAbove;
                canDefineOrientationForActivitiesAbove = ((com.android.server.wm.ActivityRecord) obj).canDefineOrientationForActivitiesAbove();
                return canDefineOrientationForActivitiesAbove;
            }
        }, this, false, true)) != null) {
            return activity.getRequestedConfigurationOrientation(z);
        }
        return super.getRequestedConfigurationOrientation(z, i);
    }

    public static int reverseConfigurationOrientation(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean canDefineOrientationForActivitiesAbove() {
        int overrideOrientation;
        return (this.finishing || (overrideOrientation = getOverrideOrientation()) == -2 || overrideOrientation == 3) ? false : true;
    }

    @Override // com.android.server.wm.WindowToken
    void onCancelFixedRotationTransform(int i) {
        if (this != this.mDisplayContent.getLastOrientationSource()) {
            return;
        }
        int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
        if (requestedConfigurationOrientation != 0 && requestedConfigurationOrientation != this.mDisplayContent.getConfiguration().orientation) {
            return;
        }
        this.mDisplayContent.mPinnedTaskController.onCancelFixedRotationTransform();
        startFreezingScreen(i);
        ensureActivityConfiguration();
        if (this.mTransitionController.isCollecting(this)) {
            this.task.resetSurfaceControlTransforms();
        }
    }

    void setRequestedOrientation(int i) {
        if (this.mLetterboxUiController.shouldIgnoreRequestedOrientation(i)) {
            return;
        }
        int i2 = this.mPendingRelaunchCount;
        if (getRequestedConfigurationOrientation(false, i) != getRequestedConfigurationOrientation(false)) {
            clearSizeCompatModeAttributes();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -9178011226407552682L, 0, null, java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(i)), java.lang.String.valueOf(this));
        setOrientation(i, this);
        if (!getMergedOverrideConfiguration().equals(this.mLastReportedConfiguration.getMergedConfiguration())) {
            ensureActivityConfiguration(false);
            if (this.mPendingRelaunchCount > i2) {
                this.mLetterboxUiController.setRelaunchingAfterRequestedOrientationChanged(true);
            }
            if (this.mTransitionController.inPlayingTransition(this)) {
                this.mTransitionController.mValidateActivityCompat.add(this);
            }
        }
        this.mAtmService.getTaskChangeNotificationController().notifyActivityRequestedOrientationChanged(this.task.mTaskId, i);
        this.mDisplayContent.getDisplayRotation().onSetRequestedOrientation();
    }

    void reportDescendantOrientationChangeIfNeeded() {
        if (onDescendantOrientationChanged(this)) {
            this.task.dispatchTaskInfoChangedIfNeeded(true);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldIgnoreOrientationRequests() {
        return this.mAppActivityEmbeddingSplitsEnabled && android.content.pm.ActivityInfo.isFixedOrientationPortrait(getOverrideOrientation()) && !this.task.inMultiWindowMode() && getTask().getConfiguration().smallestScreenWidthDp >= 600;
    }

    @Override // com.android.server.wm.WindowContainer
    int getOrientation(int i) {
        if (this.finishing || shouldIgnoreOrientationRequests()) {
            return -2;
        }
        if (i == 3) {
            return getOverrideOrientation();
        }
        if (isVisibleRequested()) {
            return getOverrideOrientation();
        }
        return -2;
    }

    @Override // com.android.server.wm.WindowContainer
    protected int getOverrideOrientation() {
        return this.mLetterboxUiController.overrideOrientationIfNeeded(super.getOverrideOrientation());
    }

    int getRequestedOrientation() {
        return super.getOverrideOrientation();
    }

    void setLastReportedGlobalConfiguration(@android.annotation.NonNull android.content.res.Configuration configuration) {
        this.mLastReportedConfiguration.setGlobalConfiguration(configuration);
    }

    void setLastReportedConfiguration(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.content.res.Configuration configuration2) {
        this.mLastReportedConfiguration.setConfiguration(configuration, configuration2);
    }

    void setLastReportedActivityWindowInfo(@android.annotation.NonNull android.window.ActivityWindowInfo activityWindowInfo) {
        if (com.android.window.flags.Flags.activityWindowInfoFlag()) {
            this.mLastReportedActivityWindowInfo.set(activityWindowInfo);
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord.CompatDisplayInsets getCompatDisplayInsets() {
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return this.mLetterboxUiController.getInheritedCompatDisplayInsets();
        }
        return this.mCompatDisplayInsets;
    }

    boolean hasCompatDisplayInsetsWithoutInheritance() {
        return this.mCompatDisplayInsets != null;
    }

    boolean inSizeCompatMode() {
        com.android.server.wm.WindowContainer parent;
        if (this.mInSizeCompatModeForBounds) {
            return true;
        }
        return (getCompatDisplayInsets() == null || !shouldCreateCompatDisplayInsets() || isFixedRotationTransforming() || getConfiguration().windowConfiguration.getAppBounds() == null || (parent = getParent()) == null || parent.getConfiguration().densityDpi == getConfiguration().densityDpi) ? false : true;
    }

    boolean shouldCreateCompatDisplayInsets() {
        if (this.mLetterboxUiController.shouldApplyUserFullscreenOverride()) {
            return false;
        }
        switch (supportsSizeChanges()) {
            case 1:
                return true;
            case 2:
            case 3:
                return false;
            default:
                if (inMultiWindowMode() || getWindowConfiguration().hasWindowDecorCaption()) {
                    com.android.server.wm.ActivityRecord rootActivity = this.task != null ? this.task.getRootActivity() : null;
                    if (rootActivity != null && rootActivity != this && !rootActivity.shouldCreateCompatDisplayInsets()) {
                        return false;
                    }
                }
                if (isResizeable()) {
                    return false;
                }
                return (this.info.isFixedOrientation() || hasFixedAspectRatio()) && isActivityTypeStandardOrUndefined();
        }
    }

    private int supportsSizeChanges() {
        if (this.mLetterboxUiController.shouldOverrideForceNonResizeApp()) {
            return 1;
        }
        if (this.info.supportsSizeChanges) {
            return 2;
        }
        if (this.mLetterboxUiController.shouldOverrideForceResizeApp()) {
            return 3;
        }
        return 0;
    }

    @Override // com.android.server.wm.WindowToken
    boolean hasSizeCompatBounds() {
        return this.mSizeCompatBounds != null;
    }

    private void updateCompatDisplayInsets() {
        if (getCompatDisplayInsets() != null || !shouldCreateCompatDisplayInsets()) {
            return;
        }
        android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        android.content.res.Configuration configuration = getConfiguration();
        requestedOverrideConfiguration.colorMode = configuration.colorMode;
        requestedOverrideConfiguration.densityDpi = configuration.densityDpi;
        requestedOverrideConfiguration.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        if (android.content.pm.ActivityInfo.isFixedOrientation(getOverrideOrientation())) {
            requestedOverrideConfiguration.windowConfiguration.setRotation(configuration.windowConfiguration.getRotation());
        }
        this.mCompatDisplayInsets = new com.android.server.wm.ActivityRecord.CompatDisplayInsets(this.mDisplayContent, this, this.mLetterboxBoundsForFixedOrientationAndAspectRatio);
    }

    private void clearSizeCompatModeAttributes() {
        this.mInSizeCompatModeForBounds = false;
        float f = this.mSizeCompatScale;
        this.mSizeCompatScale = 1.0f;
        if (this.mSizeCompatScale != f) {
            forAllWindows((java.util.function.Consumer<com.android.server.wm.WindowState>) new com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda7(), false);
        }
        this.mSizeCompatBounds = null;
        this.mCompatDisplayInsets = null;
        this.mLetterboxUiController.clearInheritedCompatDisplayInsets();
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearSizeCompatMode() {
        clearSizeCompatModeAttributes();
        int activityType = getActivityType();
        android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        requestedOverrideConfiguration.unset();
        requestedOverrideConfiguration.windowConfiguration.setActivityType(activityType);
        onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean matchParentBounds() {
        com.android.server.wm.WindowContainer parent;
        android.graphics.Rect resolvedOverrideBounds = getResolvedOverrideBounds();
        return resolvedOverrideBounds.isEmpty() || (parent = getParent()) == null || parent.getBounds().equals(resolvedOverrideBounds);
    }

    @Override // com.android.server.wm.WindowToken
    float getCompatScale() {
        return hasSizeCompatBounds() ? this.mSizeCompatScale : super.getCompatScale();
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        boolean z = false;
        if (requestedOverrideConfiguration.assetsSeq != 0 && configuration.assetsSeq > requestedOverrideConfiguration.assetsSeq) {
            requestedOverrideConfiguration.assetsSeq = 0;
        }
        super.resolveOverrideConfiguration(configuration);
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        applyLocaleOverrideIfNeeded(resolvedOverrideConfiguration);
        if (isFixedRotationTransforming()) {
            this.mTmpConfig.setTo(configuration);
            this.mTmpConfig.updateFrom(resolvedOverrideConfiguration);
            configuration = this.mTmpConfig;
        }
        this.mIsAspectRatioApplied = false;
        this.mIsEligibleForFixedOrientationLetterbox = false;
        this.mLetterboxBoundsForFixedOrientationAndAspectRatio = null;
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        boolean z2 = windowingMode == 6 || windowingMode == 1 || (!this.mWaitForEnteringPinnedMode && windowingMode == 2 && resolvedOverrideConfiguration.windowConfiguration.getWindowingMode() == 1);
        if (z2) {
            resolveFixedOrientationConfiguration(configuration);
        }
        com.android.server.wm.ActivityRecord.CompatDisplayInsets compatDisplayInsets = getCompatDisplayInsets();
        if (compatDisplayInsets != null) {
            resolveSizeCompatModeConfiguration(configuration, compatDisplayInsets);
        } else if (inMultiWindowMode() && !z2) {
            resolvedOverrideConfiguration.orientation = 0;
            if (!matchParentBounds()) {
                getTaskFragment().computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
            }
        } else if (!isLetterboxedForFixedOrientationAndAspectRatio()) {
            resolveAspectRatioRestriction(configuration);
        }
        if (z2 || compatDisplayInsets != null || !inMultiWindowMode()) {
            updateResolvedBoundsPosition(configuration);
        }
        if (this.mDisplayContent != null && this.mDisplayContent.getIgnoreOrientationRequest()) {
            z = true;
        }
        if (compatDisplayInsets == null && (this.mLetterboxBoundsForFixedOrientationAndAspectRatio != null || (z && this.mIsAspectRatioApplied))) {
            resolvedOverrideConfiguration.smallestScreenWidthDp = java.lang.Math.min(resolvedOverrideConfiguration.screenWidthDp, resolvedOverrideConfiguration.screenHeightDp);
        }
        int i = this.mConfigurationSeq + 1;
        this.mConfigurationSeq = i;
        this.mConfigurationSeq = java.lang.Math.max(i, 1);
        getResolvedOverrideConfiguration().seq = this.mConfigurationSeq;
        if (providesMaxBounds()) {
            this.mTmpBounds.set(resolvedOverrideConfiguration.windowConfiguration.getBounds());
            if (this.mTmpBounds.isEmpty()) {
                this.mTmpBounds.set(configuration.windowConfiguration.getBounds());
            }
            resolvedOverrideConfiguration.windowConfiguration.setMaxBounds(this.mTmpBounds);
        }
        logAppCompatState();
    }

    int getOrientationForReachability() {
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return this.mLetterboxUiController.getInheritedOrientation();
        }
        return getRequestedConfigurationOrientation();
    }

    boolean areBoundsLetterboxed() {
        return getAppCompatState(true) != 2;
    }

    private void logAppCompatState() {
        this.mTaskSupervisor.getActivityMetricsLogger().logAppCompatState(this);
    }

    int getAppCompatState() {
        return getAppCompatState(false);
    }

    private int getAppCompatState(boolean z) {
        if (!z && !this.mVisibleRequested) {
            return 1;
        }
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return this.mLetterboxUiController.getInheritedAppCompatState();
        }
        if (this.mInSizeCompatModeForBounds) {
            return 3;
        }
        if (isLetterboxedForFixedOrientationAndAspectRatio()) {
            return 4;
        }
        if (this.mIsAspectRatioApplied) {
            return 5;
        }
        return 2;
    }

    private void updateResolvedBoundsPosition(android.content.res.Configuration configuration) {
        int i;
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        android.graphics.Rect bounds = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        android.graphics.Rect rect = this.mSizeCompatBounds != null ? this.mSizeCompatBounds : bounds;
        android.graphics.Rect appBounds = configuration.windowConfiguration.getAppBounds();
        android.graphics.Rect bounds2 = configuration.windowConfiguration.getBounds();
        float width = rect.width();
        float width2 = appBounds.width();
        int i2 = 0;
        if (bounds2.width() != width && width <= width2) {
            i = java.lang.Math.max(0, (((int) java.lang.Math.ceil((width2 - width) * this.mLetterboxUiController.getHorizontalPositionMultiplier(configuration))) - rect.left) + appBounds.left);
        } else {
            i = 0;
        }
        float height = appBounds.height();
        float height2 = bounds2.height();
        float height3 = rect.height();
        if (height2 != height3 && height3 <= height) {
            float verticalPositionMultiplier = this.mLetterboxUiController.getVerticalPositionMultiplier(configuration);
            if (this.mDisplayContent.getDisplayPolicy().isImmersiveMode()) {
                height = height2;
            }
            i2 = java.lang.Math.max(0, (((int) java.lang.Math.ceil((height - height3) * verticalPositionMultiplier)) - rect.top) + appBounds.top);
        }
        if (this.mSizeCompatBounds != null) {
            this.mSizeCompatBounds.offset(i, i2);
            offsetBounds(resolvedOverrideConfiguration, this.mSizeCompatBounds.left - bounds.left, this.mSizeCompatBounds.top - bounds.top);
        } else {
            offsetBounds(resolvedOverrideConfiguration, i, i2);
        }
        if (resolvedOverrideConfiguration.windowConfiguration.getAppBounds().top == appBounds.top) {
            resolvedOverrideConfiguration.windowConfiguration.getBounds().top = bounds2.top;
            if (this.mSizeCompatBounds != null) {
                this.mSizeCompatBounds.top = bounds2.top;
            }
        }
        getTaskFragment().computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
        if (this.mSizeCompatScale != 1.0f) {
            int i3 = bounds.left;
            int i4 = bounds.top;
            offsetBounds(resolvedOverrideConfiguration, ((int) ((i3 / this.mSizeCompatScale) + 0.5f)) - i3, ((int) ((i4 / this.mSizeCompatScale) + 0.5f)) - i4);
        }
    }

    @android.annotation.NonNull
    android.graphics.Rect getScreenResolvedBounds() {
        return this.mSizeCompatBounds != null ? this.mSizeCompatBounds : getResolvedOverrideConfiguration().windowConfiguration.getBounds();
    }

    void recomputeConfiguration() {
        if (!this.mLetterboxUiController.applyOnOpaqueActivityBelow(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.ActivityRecord) obj).recomputeConfiguration();
            }
        })) {
            onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
        }
    }

    boolean isInTransition() {
        return inTransitionSelfOrParent();
    }

    boolean isDisplaySleepingAndSwapping() {
        for (int size = this.mDisplayContent.mAllSleepTokens.size() - 1; size >= 0; size--) {
            if (this.mDisplayContent.mAllSleepTokens.get(size).isDisplaySwapping()) {
                return true;
            }
        }
        return false;
    }

    boolean isLetterboxedForFixedOrientationAndAspectRatio() {
        return this.mLetterboxBoundsForFixedOrientationAndAspectRatio != null;
    }

    boolean isAspectRatioApplied() {
        return this.mIsAspectRatioApplied;
    }

    boolean isEligibleForLetterboxEducation() {
        return this.mWmService.mLetterboxConfiguration.getIsEducationEnabled() && this.mIsEligibleForFixedOrientationLetterbox && getWindowingMode() == 1 && getRequestedConfigurationOrientation() == 1 && this.mStartingWindow == null;
    }

    private boolean orientationRespectedWithInsets(android.graphics.Rect rect, android.graphics.Rect rect2) {
        int requestedConfigurationOrientation;
        android.view.DisplayInfo displayInfo;
        rect2.setEmpty();
        boolean z = true;
        if (this.mDisplayContent == null || (requestedConfigurationOrientation = getRequestedConfigurationOrientation()) == 0) {
            return true;
        }
        int i = rect.height() >= rect.width() ? 1 : 2;
        if (isFixedRotationTransforming()) {
            displayInfo = getFixedRotationTransformDisplayInfo();
        } else {
            displayInfo = this.mDisplayContent.getDisplayInfo();
        }
        getTask().calculateInsetFrames(this.mTmpBounds, rect2, rect, displayInfo);
        int i2 = rect2.height() >= rect2.width() ? 1 : 2;
        if (i != i2 && i2 != requestedConfigurationOrientation) {
            z = false;
        }
        if (z) {
            rect2.setEmpty();
        }
        return z;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handlesOrientationChangeFromDescendant(int i) {
        if (shouldIgnoreOrientationRequests()) {
            return false;
        }
        return super.handlesOrientationChangeFromDescendant(i);
    }

    private void resolveFixedOrientationConfiguration(@android.annotation.NonNull android.content.res.Configuration configuration) {
        android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
        android.graphics.Rect rect = new android.graphics.Rect();
        boolean orientationRespectedWithInsets = orientationRespectedWithInsets(bounds, rect);
        if (orientationRespectedWithInsets && handlesOrientationChangeFromDescendant(getOverrideOrientation())) {
            return;
        }
        com.android.server.wm.TaskFragment organizedTaskFragment = getOrganizedTaskFragment();
        if (organizedTaskFragment != null && !organizedTaskFragment.fillsParent()) {
            return;
        }
        android.graphics.Rect bounds2 = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
        int i = configuration.orientation;
        int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
        this.mIsEligibleForFixedOrientationLetterbox = (requestedConfigurationOrientation == 0 || requestedConfigurationOrientation == i) ? false : true;
        if (!this.mIsEligibleForFixedOrientationLetterbox && (requestedConfigurationOrientation == 0 || orientationRespectedWithInsets)) {
            return;
        }
        com.android.server.wm.ActivityRecord.CompatDisplayInsets compatDisplayInsets = getCompatDisplayInsets();
        if (compatDisplayInsets != null && !compatDisplayInsets.mIsInFixedOrientationLetterbox) {
            return;
        }
        if (orientationRespectedWithInsets) {
            rect = configuration.windowConfiguration.getAppBounds();
        }
        android.graphics.Rect rect2 = new android.graphics.Rect();
        android.graphics.Rect rect3 = new android.graphics.Rect();
        if (requestedConfigurationOrientation == 2) {
            int min = java.lang.Math.min((rect.top + bounds.width()) - 1, rect.bottom);
            rect2.set(bounds.left, rect.top, bounds.right, min);
            rect3.set(rect.left, rect.top, rect.right, min);
        } else {
            int min2 = java.lang.Math.min(rect.left + bounds.height(), rect.right);
            rect2.set(rect.left, bounds.top, min2, bounds.bottom);
            rect3.set(rect.left, rect.top, min2, rect.bottom);
        }
        android.graphics.Rect rect4 = new android.graphics.Rect(bounds2);
        bounds2.set(rect2);
        float fixedOrientationLetterboxAspectRatio = this.mLetterboxUiController.getFixedOrientationLetterboxAspectRatio(configuration);
        if (isDefaultMultiWindowLetterboxAspectRatioDesired(configuration)) {
            fixedOrientationLetterboxAspectRatio = 1.01f;
        } else if (fixedOrientationLetterboxAspectRatio <= 1.0f) {
            fixedOrientationLetterboxAspectRatio = computeAspectRatio(bounds);
        }
        this.mIsAspectRatioApplied = applyAspectRatio(bounds2, rect3, rect2, fixedOrientationLetterboxAspectRatio);
        if (compatDisplayInsets != null) {
            compatDisplayInsets.getBoundsByRotation(this.mTmpBounds, configuration.windowConfiguration.getRotation());
            if (bounds2.width() != this.mTmpBounds.width() || bounds2.height() != this.mTmpBounds.height()) {
                bounds2.set(rect4);
                return;
            }
        }
        if (bounds2.equals(bounds)) {
            bounds2.set(rect4);
        } else {
            getTaskFragment().computeConfigResourceOverrides(getResolvedOverrideConfiguration(), configuration, compatDisplayInsets);
            this.mLetterboxBoundsForFixedOrientationAndAspectRatio = new android.graphics.Rect(bounds2);
        }
    }

    private boolean isDefaultMultiWindowLetterboxAspectRatioDesired(@android.annotation.NonNull android.content.res.Configuration configuration) {
        return (this.mDisplayContent == null || !android.app.WindowConfiguration.inMultiWindowMode(configuration.windowConfiguration.getWindowingMode()) || this.mDisplayContent.getIgnoreOrientationRequest()) ? false : true;
    }

    private void resolveAspectRatioRestriction(android.content.res.Configuration configuration) {
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        android.graphics.Rect appBounds = configuration.windowConfiguration.getAppBounds();
        android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
        android.graphics.Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        this.mTmpBounds.setEmpty();
        this.mIsAspectRatioApplied = applyAspectRatio(this.mTmpBounds, appBounds, bounds);
        if (!this.mTmpBounds.isEmpty()) {
            bounds2.set(this.mTmpBounds);
        }
        if (!bounds2.isEmpty() && !bounds2.equals(bounds)) {
            getTaskFragment().computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration, getFixedRotationTransformDisplayInfo());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void resolveSizeCompatModeConfiguration(android.content.res.Configuration configuration, @android.annotation.NonNull com.android.server.wm.ActivityRecord.CompatDisplayInsets compatDisplayInsets) {
        android.graphics.Rect bounds;
        android.graphics.Rect appBounds;
        int i;
        int i2;
        android.graphics.Rect appBounds2;
        float f;
        int i3;
        int i4;
        int i5;
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        android.graphics.Rect bounds2 = resolvedOverrideConfiguration.windowConfiguration.getBounds();
        if (isLetterboxedForFixedOrientationAndAspectRatio()) {
            bounds = new android.graphics.Rect(bounds2);
        } else {
            bounds = configuration.windowConfiguration.getBounds();
        }
        if (isLetterboxedForFixedOrientationAndAspectRatio()) {
            appBounds = new android.graphics.Rect(getResolvedOverrideConfiguration().windowConfiguration.getAppBounds());
        } else {
            appBounds = configuration.windowConfiguration.getAppBounds();
        }
        int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
        boolean z = requestedConfigurationOrientation != 0;
        if (z) {
            i = requestedConfigurationOrientation;
        } else if (compatDisplayInsets.mOriginalRequestedOrientation != 0) {
            i = compatDisplayInsets.mOriginalRequestedOrientation;
        } else {
            i = configuration.orientation;
        }
        int rotation = configuration.windowConfiguration.getRotation();
        boolean z2 = this.mDisplayContent == null || this.mDisplayContent.getDisplayRotation().isFixedToUserRotation();
        if (!z2 && !compatDisplayInsets.mIsFloating) {
            resolvedOverrideConfiguration.windowConfiguration.setRotation(rotation);
        } else {
            int rotation2 = resolvedOverrideConfiguration.windowConfiguration.getRotation();
            if (rotation2 != -1) {
                i2 = rotation2;
                android.graphics.Rect rect = new android.graphics.Rect();
                android.graphics.Rect rect2 = this.mTmpBounds;
                compatDisplayInsets.getContainerBounds(rect, rect2, i2, i, z, z2);
                bounds2.set(rect2);
                if (!compatDisplayInsets.mIsFloating) {
                    this.mIsAspectRatioApplied = applyAspectRatio(bounds2, rect, rect2);
                }
                getTaskFragment().computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration, compatDisplayInsets);
                resolvedOverrideConfiguration.screenLayout = com.android.server.wm.WindowContainer.computeScreenLayout(getConfiguration().screenLayout, resolvedOverrideConfiguration.screenWidthDp, resolvedOverrideConfiguration.screenHeightDp);
                if (resolvedOverrideConfiguration.screenWidthDp == resolvedOverrideConfiguration.screenHeightDp) {
                    resolvedOverrideConfiguration.orientation = configuration.orientation;
                }
                appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
                f = this.mSizeCompatScale;
                updateSizeCompatScale(appBounds2, appBounds);
                i3 = appBounds.top - bounds.top;
                boolean z3 = i3 == appBounds2.top - bounds2.top;
                if (this.mSizeCompatScale != 1.0f && !z3) {
                    this.mSizeCompatBounds = null;
                } else {
                    if (this.mSizeCompatBounds == null) {
                        this.mSizeCompatBounds = new android.graphics.Rect();
                    }
                    this.mSizeCompatBounds.set(appBounds2);
                    this.mSizeCompatBounds.offsetTo(0, 0);
                    this.mSizeCompatBounds.scale(this.mSizeCompatScale);
                    this.mSizeCompatBounds.bottom += i3;
                }
                if (this.mSizeCompatScale != f) {
                    forAllWindows((java.util.function.Consumer<com.android.server.wm.WindowState>) new com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda7(), false);
                }
                boolean equals = bounds2.equals(rect2);
                i4 = !equals ? bounds.left : appBounds.left;
                i5 = !equals ? bounds.top : appBounds.top;
                if (i4 == 0 || i5 != 0) {
                    if (this.mSizeCompatBounds != null) {
                        this.mSizeCompatBounds.offset(i4, i5);
                    }
                    offsetBounds(resolvedOverrideConfiguration, i4 - bounds2.left, i5 - bounds2.top);
                }
                this.mInSizeCompatModeForBounds = isInSizeCompatModeForBounds(appBounds2, appBounds);
            }
        }
        i2 = rotation;
        android.graphics.Rect rect3 = new android.graphics.Rect();
        android.graphics.Rect rect22 = this.mTmpBounds;
        compatDisplayInsets.getContainerBounds(rect3, rect22, i2, i, z, z2);
        bounds2.set(rect22);
        if (!compatDisplayInsets.mIsFloating) {
        }
        getTaskFragment().computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration, compatDisplayInsets);
        resolvedOverrideConfiguration.screenLayout = com.android.server.wm.WindowContainer.computeScreenLayout(getConfiguration().screenLayout, resolvedOverrideConfiguration.screenWidthDp, resolvedOverrideConfiguration.screenHeightDp);
        if (resolvedOverrideConfiguration.screenWidthDp == resolvedOverrideConfiguration.screenHeightDp) {
        }
        appBounds2 = resolvedOverrideConfiguration.windowConfiguration.getAppBounds();
        f = this.mSizeCompatScale;
        updateSizeCompatScale(appBounds2, appBounds);
        i3 = appBounds.top - bounds.top;
        if (i3 == appBounds2.top - bounds2.top) {
        }
        if (this.mSizeCompatScale != 1.0f) {
        }
        if (this.mSizeCompatBounds == null) {
        }
        this.mSizeCompatBounds.set(appBounds2);
        this.mSizeCompatBounds.offsetTo(0, 0);
        this.mSizeCompatBounds.scale(this.mSizeCompatScale);
        this.mSizeCompatBounds.bottom += i3;
        if (this.mSizeCompatScale != f) {
        }
        boolean equals2 = bounds2.equals(rect22);
        if (!equals2) {
        }
        if (!equals2) {
        }
        if (i4 == 0) {
        }
        if (this.mSizeCompatBounds != null) {
        }
        offsetBounds(resolvedOverrideConfiguration, i4 - bounds2.left, i5 - bounds2.top);
        this.mInSizeCompatModeForBounds = isInSizeCompatModeForBounds(appBounds2, appBounds);
    }

    void updateSizeCompatScale(final android.graphics.Rect rect, final android.graphics.Rect rect2) {
        this.mSizeCompatScale = ((java.lang.Float) this.mLetterboxUiController.findOpaqueNotFinishingActivityBelow().map(new java.util.function.Function() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda30
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Float lambda$updateSizeCompatScale$20;
                lambda$updateSizeCompatScale$20 = com.android.server.wm.ActivityRecord.lambda$updateSizeCompatScale$20((com.android.server.wm.ActivityRecord) obj);
                return lambda$updateSizeCompatScale$20;
            }
        }).orElseGet(new java.util.function.Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda31
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Float lambda$updateSizeCompatScale$21;
                lambda$updateSizeCompatScale$21 = com.android.server.wm.ActivityRecord.lambda$updateSizeCompatScale$21(rect, rect2);
                return lambda$updateSizeCompatScale$21;
            }
        })).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Float lambda$updateSizeCompatScale$20(com.android.server.wm.ActivityRecord activityRecord) {
        return java.lang.Float.valueOf(activityRecord.mSizeCompatScale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Float lambda$updateSizeCompatScale$21(android.graphics.Rect rect, android.graphics.Rect rect2) {
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        return java.lang.Float.valueOf((width > width2 || height > height2) ? java.lang.Math.min(width2 / width, height2 / height) : 1.0f);
    }

    private boolean isInSizeCompatModeForBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return false;
        }
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if (width2 == width && height2 == height) {
            return false;
        }
        if ((width2 > width && height2 > height) || width2 < width || height2 < height) {
            return true;
        }
        float maxAspectRatio = getMaxAspectRatio();
        if (maxAspectRatio > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && (java.lang.Math.max(width, height) + 0.5f) / java.lang.Math.min(width, height) >= maxAspectRatio) {
            return false;
        }
        float minAspectRatio = getMinAspectRatio();
        return minAspectRatio <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || (((float) java.lang.Math.max(width2, height2)) + 0.5f) / ((float) java.lang.Math.min(width2, height2)) > minAspectRatio;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCenterOffset(int i, int i2) {
        return (int) (((i - i2) + 1) * 0.5f);
    }

    private static void offsetBounds(android.content.res.Configuration configuration, int i, int i2) {
        configuration.windowConfiguration.getBounds().offset(i, i2);
        configuration.windowConfiguration.getAppBounds().offset(i, i2);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public android.graphics.Rect getBounds() {
        final android.graphics.Rect bounds = super.getBounds();
        return (android.graphics.Rect) this.mLetterboxUiController.findOpaqueNotFinishingActivityBelow().map(new java.util.function.Function() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).getBounds();
            }
        }).orElseGet(new java.util.function.Supplier() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda22
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.graphics.Rect lambda$getBounds$22;
                lambda$getBounds$22 = com.android.server.wm.ActivityRecord.this.lambda$getBounds$22(bounds);
                return lambda$getBounds$22;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.graphics.Rect lambda$getBounds$22(android.graphics.Rect rect) {
        if (this.mSizeCompatBounds != null) {
            return this.mSizeCompatBounds;
        }
        return rect;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean providesMaxBounds() {
        if (getUid() == 1000) {
            return false;
        }
        if ((this.mDisplayContent == null || this.mDisplayContent.sandboxDisplayApis()) && !this.info.neverSandboxDisplayApis(sConstrainDisplayApisConfig)) {
            return this.info.alwaysSandboxDisplayApis(sConstrainDisplayApisConfig) || getCompatDisplayInsets() != null || shouldCreateCompatDisplayInsets();
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Rect getAnimationBounds(int i) {
        com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
        return taskFragment != null ? taskFragment.getBounds() : getBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    void getAnimationPosition(android.graphics.Point point) {
        point.set(0, 0);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        int rotationForActivityInDifferentOrientation;
        int requestedOverrideWindowingMode;
        if (this.mTransitionController.isShellTransitionsEnabled() && isVisible() && isVisibleRequested()) {
            if (getRequestedOverrideWindowingMode() == 0) {
                requestedOverrideWindowingMode = configuration.windowConfiguration.getWindowingMode();
            } else {
                requestedOverrideWindowingMode = getRequestedOverrideWindowingMode();
            }
            if (getWindowingMode() != requestedOverrideWindowingMode && (!this.mWaitForEnteringPinnedMode || !this.mTransitionController.inFinishingTransition(this))) {
                this.mTransitionController.collect(this);
            }
        }
        if (getCompatDisplayInsets() != null) {
            android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
            boolean z = requestedOverrideConfiguration.windowConfiguration.getRotation() != -1;
            int requestedConfigurationOrientation = getRequestedConfigurationOrientation();
            if (requestedConfigurationOrientation != 0 && requestedConfigurationOrientation != getConfiguration().orientation && requestedConfigurationOrientation == getParent().getConfiguration().orientation && requestedOverrideConfiguration.windowConfiguration.getRotation() != getParent().getWindowConfiguration().getRotation()) {
                requestedOverrideConfiguration.windowConfiguration.setRotation(getParent().getWindowConfiguration().getRotation());
                onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
                return;
            } else if (z && requestedConfigurationOrientation == 0 && requestedOverrideConfiguration.windowConfiguration.getRotation() != -1) {
                requestedOverrideConfiguration.windowConfiguration.setRotation(-1);
                onRequestedOverrideConfigurationChanged(requestedOverrideConfiguration);
                return;
            }
        }
        boolean inPinnedWindowingMode = inPinnedWindowingMode();
        com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
        int activityType = getActivityType();
        if (inPinnedWindowingMode && attachedToProcess() && displayContent != null) {
            try {
                this.app.pauseConfigurationDispatch();
                super.onConfigurationChanged(configuration);
                if (this.mVisibleRequested && !inMultiWindowMode() && (rotationForActivityInDifferentOrientation = displayContent.rotationForActivityInDifferentOrientation(this)) != -1) {
                    this.app.resumeConfigurationDispatch();
                    displayContent.setFixedRotationLaunchingApp(this, rotationForActivityInDifferentOrientation);
                }
            } finally {
                if (this.app.resumeConfigurationDispatch()) {
                    this.app.dispatchConfiguration(this.app.getConfiguration());
                }
            }
        } else {
            super.onConfigurationChanged(configuration);
        }
        if (activityType != 0 && activityType != getActivityType()) {
            java.lang.String str = "Can't change activity type once set: " + this + " activityType=" + android.app.WindowConfiguration.activityTypeToString(getActivityType()) + ", was " + android.app.WindowConfiguration.activityTypeToString(activityType);
            if (android.os.Build.IS_DEBUGGABLE) {
                throw new java.lang.IllegalStateException(str);
            }
            android.util.Slog.w("ActivityTaskManager", str);
        }
        if (!inPinnedWindowingMode && inPinnedWindowingMode() && this.task != null) {
            this.mWaitForEnteringPinnedMode = false;
            this.mTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(this.task, this.task.getBounds());
        }
        if (displayContent == null) {
            return;
        }
        if (this.mVisibleRequested) {
            displayContent.handleActivitySizeCompatModeIfNeeded(this);
            return;
        }
        if (getCompatDisplayInsets() != null && !this.visibleIgnoringKeyguard) {
            if (this.app == null || !this.app.hasVisibleActivities()) {
                int currentOverrideConfigurationChanges = displayContent.getCurrentOverrideConfigurationChanges();
                if ((hasResizeChange(currentOverrideConfigurationChanges) && (currentOverrideConfigurationChanges & 536872064) != 536872064) || (currentOverrideConfigurationChanges & 4096) != 0) {
                    restartProcessIfVisible();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.ConfigurationContainer
    public void dispatchConfigurationToChild(com.android.server.wm.WindowState windowState, android.content.res.Configuration configuration) {
        if (isConfigurationDispatchPaused()) {
            return;
        }
        super.dispatchConfigurationToChild((com.android.server.wm.ActivityRecord) windowState, configuration);
    }

    void pauseConfigurationDispatch() {
        this.mPauseConfigurationDispatchCount++;
        if (this.mPauseConfigurationDispatchCount == 1) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 2612201759169917322L, 0, "Pausing configuration dispatch for  %s", java.lang.String.valueOf(this));
        }
    }

    boolean resumeConfigurationDispatch() {
        this.mPauseConfigurationDispatchCount--;
        if (this.mPauseConfigurationDispatchCount > 0) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 5153784493059555057L, 0, "Resuming configuration dispatch for %s", java.lang.String.valueOf(this));
        if (this.mPauseConfigurationDispatchCount < 0) {
            android.util.Slog.wtf("ActivityTaskManager", "Trying to resume non-paused configuration dispatch");
            this.mPauseConfigurationDispatchCount = 0;
            return false;
        }
        if (this.mLastReportedDisplayId == getDisplayId() && getConfiguration().equals(this.mLastReportedConfiguration.getMergedConfiguration())) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            dispatchConfigurationToChild(getChildAt(childCount), getConfiguration());
        }
        updateReportedConfigurationAndSend();
        return true;
    }

    boolean isConfigurationDispatchPaused() {
        return this.mPauseConfigurationDispatchCount > 0;
    }

    private boolean applyAspectRatio(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        return applyAspectRatio(rect, rect2, rect3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    private boolean applyAspectRatio(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, float f) {
        boolean z;
        int i;
        int i2;
        float maxAspectRatio = getMaxAspectRatio();
        com.android.server.wm.Task rootTask = getRootTask();
        float minAspectRatio = getMinAspectRatio();
        com.android.server.wm.TaskFragment organizedTaskFragment = getOrganizedTaskFragment();
        if (this.task == null || rootTask == null || ((maxAspectRatio < 1.0f && minAspectRatio < 1.0f && f < 1.0f) || isInVrUiMode(getConfiguration()) || !(organizedTaskFragment == null || organizedTaskFragment.fillsParent()))) {
            return false;
        }
        int width = rect2.width();
        int height = rect2.height();
        float computeAspectRatio = computeAspectRatio(rect2);
        if (f < 1.0f) {
            f = computeAspectRatio;
        }
        if (maxAspectRatio < 1.0f || f <= maxAspectRatio) {
            if (minAspectRatio >= 1.0f && f < minAspectRatio) {
                maxAspectRatio = minAspectRatio;
            } else {
                maxAspectRatio = f;
            }
        }
        if (computeAspectRatio - maxAspectRatio > ASPECT_RATIO_ROUNDING_TOLERANCE) {
            if (!this.mAtmService.shouldForceLongScreen(this.packageName)) {
                if (width < height) {
                    i = (int) ((width * maxAspectRatio) + 0.5f);
                    i2 = width;
                } else {
                    i2 = (int) ((height * maxAspectRatio) + 0.5f);
                    i = height;
                }
            }
            i2 = width;
            i = height;
        } else {
            if (maxAspectRatio - computeAspectRatio > ASPECT_RATIO_ROUNDING_TOLERANCE) {
                switch (getRequestedConfigurationOrientation()) {
                    case 1:
                        z = true;
                        break;
                    case 2:
                        z = false;
                        break;
                    default:
                        if (width < height) {
                            z = true;
                            break;
                        } else {
                            z = false;
                            break;
                        }
                }
                if (z) {
                    i2 = (int) ((height / maxAspectRatio) + 0.5f);
                    i = height;
                } else {
                    i = (int) ((width / maxAspectRatio) + 0.5f);
                    i2 = width;
                }
            }
            i2 = width;
            i = height;
        }
        if (width <= i2 && height <= i) {
            return false;
        }
        int i3 = i2 + rect2.left;
        int i4 = rect2.left;
        if (i3 >= rect2.right) {
            i3 = rect3.right;
            i4 = rect3.left;
        }
        int i5 = i + rect2.top;
        int i6 = rect2.top;
        if (i5 >= rect2.bottom) {
            i5 = rect3.bottom;
            i6 = rect3.top;
        }
        rect.set(i4, i6, i3, i5);
        return true;
    }

    float getMinAspectRatio() {
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return this.mLetterboxUiController.getInheritedMinAspectRatio();
        }
        if (this.info.applicationInfo == null) {
            return this.info.getMinAspectRatio();
        }
        if (this.mLetterboxUiController.shouldApplyUserMinAspectRatioOverride()) {
            return this.mLetterboxUiController.getUserMinAspectRatio();
        }
        if (!this.mLetterboxUiController.shouldOverrideMinAspectRatio()) {
            return this.info.getMinAspectRatio();
        }
        if (this.info.isChangeEnabled(203647190L) && !android.content.pm.ActivityInfo.isFixedOrientationPortrait(getOverrideOrientation())) {
            return this.info.getMinAspectRatio();
        }
        if (this.info.isChangeEnabled(218959984L) && isParentFullscreenPortrait()) {
            return this.info.getMinAspectRatio();
        }
        if (this.info.isChangeEnabled(208648326L)) {
            return java.lang.Math.max(this.mLetterboxUiController.getSplitScreenAspectRatio(), this.info.getMinAspectRatio());
        }
        if (this.info.isChangeEnabled(180326787L)) {
            return java.lang.Math.max(1.7777778f, this.info.getMinAspectRatio());
        }
        if (this.info.isChangeEnabled(180326845L)) {
            return java.lang.Math.max(1.5f, this.info.getMinAspectRatio());
        }
        return this.info.getMinAspectRatio();
    }

    private boolean isParentFullscreenPortrait() {
        com.android.server.wm.WindowContainer parent = getParent();
        return parent != null && parent.getConfiguration().orientation == 1 && parent.getWindowConfiguration().getWindowingMode() == 1;
    }

    float getMaxAspectRatio() {
        if (this.mLetterboxUiController.hasInheritedLetterboxBehavior()) {
            return this.mLetterboxUiController.getInheritedMaxAspectRatio();
        }
        return this.info.getMaxAspectRatio();
    }

    private boolean hasFixedAspectRatio() {
        return (getMaxAspectRatio() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && getMinAspectRatio() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) ? false : true;
    }

    static float computeAspectRatio(android.graphics.Rect rect) {
        int width = rect.width();
        int height = rect.height();
        if (width == 0 || height == 0) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        return java.lang.Math.max(width, height) / java.lang.Math.min(width, height);
    }

    boolean shouldUpdateConfigForDisplayChanged() {
        return this.mLastReportedDisplayId != getDisplayId();
    }

    boolean ensureActivityConfiguration() {
        return ensureActivityConfiguration(false);
    }

    boolean ensureActivityConfiguration(boolean z) {
        if (getRootTask().mConfigWillChange) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -8630021188868292872L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        if (this.finishing) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -3976984054291875926L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        if (isState(com.android.server.wm.ActivityRecord.State.DESTROYED)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -1036762753077003128L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        if (!z && (this.mState == com.android.server.wm.ActivityRecord.State.STOPPING || this.mState == com.android.server.wm.ActivityRecord.State.STOPPED || !shouldBeVisible())) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -6543078196636665108L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        if (isConfigurationDispatchPaused()) {
            return true;
        }
        return updateReportedConfigurationAndSend();
    }

    boolean updateReportedConfigurationAndSend() {
        if (isConfigurationDispatchPaused()) {
            android.util.Slog.wtf("ActivityTaskManager", "trying to update reported(client) config while dispatch is paused");
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -3588725633248053181L, 0, null, java.lang.String.valueOf(this));
        int displayId = getDisplayId();
        boolean z = this.mLastReportedDisplayId != displayId;
        if (z) {
            this.mLastReportedDisplayId = displayId;
        }
        if (this.mVisibleRequested) {
            updateCompatDisplayInsets();
        }
        this.mTmpConfig.setTo(this.mLastReportedConfiguration.getMergedConfiguration());
        android.window.ActivityWindowInfo activityWindowInfo = getActivityWindowInfo();
        boolean z2 = com.android.window.flags.Flags.activityWindowInfoFlag() && !this.mLastReportedActivityWindowInfo.equals(activityWindowInfo);
        if (!z && !z2 && getConfiguration().equals(this.mTmpConfig)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 4672360193194734037L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        int configurationChanges = getConfigurationChanges(this.mTmpConfig);
        android.content.res.Configuration mergedOverrideConfiguration = getMergedOverrideConfiguration();
        setLastReportedConfiguration(getProcessGlobalConfiguration(), mergedOverrideConfiguration);
        setLastReportedActivityWindowInfo(activityWindowInfo);
        if (this.mState == com.android.server.wm.ActivityRecord.State.INITIALIZING) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -8624278141553396410L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        if (configurationChanges == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 2485365009287691179L, 0, null, java.lang.String.valueOf(this));
            if (z) {
                scheduleActivityMovedToDisplay(displayId, mergedOverrideConfiguration, activityWindowInfo);
            } else {
                scheduleConfigurationChanged(mergedOverrideConfiguration, activityWindowInfo);
            }
            notifyDisplayCompatPolicyAboutConfigurationChange(this.mLastReportedConfiguration.getMergedConfiguration(), this.mTmpConfig);
            return true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -8909639363543223474L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(android.content.res.Configuration.configurationDiffToString(configurationChanges)));
        if (!attachedToProcess()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -8048404379899908050L, 0, null, java.lang.String.valueOf(this));
            return true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 4979286847769557939L, 0, null, java.lang.String.valueOf(this.info.name), java.lang.String.valueOf(java.lang.Integer.toHexString(configurationChanges)), java.lang.String.valueOf(java.lang.Integer.toHexString(this.info.getRealConfigChanged())), java.lang.String.valueOf(this.mLastReportedConfiguration));
        if (shouldRelaunchLocked(configurationChanges, this.mTmpConfig)) {
            this.configChangeFlags |= configurationChanges;
            if (this.mVisible && this.mAtmService.mTmpUpdateConfigurationResult.mIsUpdating && !this.mTransitionController.isShellTransitionsEnabled()) {
                startFreezingScreenLocked(this.app, this.mAtmService.mTmpUpdateConfigurationResult.changes);
            }
            boolean z3 = (!(this.mTmpConfig.windowConfiguration.getDisplayRotation() != getWindowConfiguration().getDisplayRotation() || !this.mTmpConfig.windowConfiguration.getMaxBounds().equals(getWindowConfiguration().getMaxBounds())) && (configurationChanges & (-3457)) == 0) && !this.mFreezingScreen;
            if (hasResizeChange((~this.info.getRealConfigChanged()) & configurationChanges)) {
                this.mRelaunchReason = this.task.isDragResizing() ? 2 : 1;
            } else {
                this.mRelaunchReason = 0;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 6779426581354721909L, 0, null, java.lang.String.valueOf(this));
            if (!this.mVisibleRequested) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 8969401915706456725L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
            }
            relaunchActivityLocked(z3);
            return false;
        }
        if (z) {
            scheduleActivityMovedToDisplay(displayId, mergedOverrideConfiguration, activityWindowInfo);
        } else {
            scheduleConfigurationChanged(mergedOverrideConfiguration, activityWindowInfo);
        }
        notifyDisplayCompatPolicyAboutConfigurationChange(this.mLastReportedConfiguration.getMergedConfiguration(), this.mTmpConfig);
        return true;
    }

    private void notifyDisplayCompatPolicyAboutConfigurationChange(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        if (this.mDisplayContent.mDisplayRotationCompatPolicy == null || !shouldBeResumed(null)) {
            return;
        }
        this.mDisplayContent.mDisplayRotationCompatPolicy.onActivityConfigurationChanging(this, configuration, configuration2);
    }

    private android.content.res.Configuration getProcessGlobalConfiguration() {
        return this.app != null ? this.app.getConfiguration() : this.mAtmService.getGlobalConfiguration();
    }

    private boolean shouldRelaunchLocked(int i, android.content.res.Configuration configuration) {
        int realConfigChanged = this.info.getRealConfigChanged();
        boolean onlyVrUiModeChanged = onlyVrUiModeChanged(i, configuration);
        if (this.info.applicationInfo.targetSdkVersion < 26 && this.requestedVrComponent != null && onlyVrUiModeChanged) {
            realConfigChanged |= 512;
        }
        if (this.mWmService.mSkipActivityRelaunchWhenDocking && onlyDeskInUiModeChanged(configuration) && !hasDeskResources()) {
            realConfigChanged |= 512;
        }
        return (i & (~realConfigChanged)) != 0;
    }

    private boolean onlyVrUiModeChanged(int i, android.content.res.Configuration configuration) {
        return i == 512 && isInVrUiMode(getConfiguration()) != isInVrUiMode(configuration);
    }

    private boolean onlyDeskInUiModeChanged(android.content.res.Configuration configuration) {
        android.content.res.Configuration configuration2 = getConfiguration();
        return (isInDeskUiMode(configuration2) != isInDeskUiMode(configuration)) && !((configuration2.uiMode & (-16)) != (configuration.uiMode & (-16)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        r4.mHasDeskResources = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean hasDeskResources() {
        if (this.mHasDeskResources != null) {
            return this.mHasDeskResources.booleanValue();
        }
        int i = 0;
        this.mHasDeskResources = false;
        try {
            android.content.res.Configuration[] sizeAndUiModeConfigurations = this.mAtmService.mContext.createPackageContextAsUser(this.packageName, 0, android.os.UserHandle.of(this.mUserId)).getResources().getSizeAndUiModeConfigurations();
            int length = sizeAndUiModeConfigurations.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (isInDeskUiMode(sizeAndUiModeConfigurations[i])) {
                    break;
                }
                i++;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w("ActivityTaskManager", "Exception thrown during checking for desk resources " + this, e);
        }
        return this.mHasDeskResources.booleanValue();
    }

    private int getConfigurationChanges(android.content.res.Configuration configuration) {
        int filterDiff = android.window.SizeConfigurationBuckets.filterDiff(configuration.diff(getConfiguration()), configuration, getConfiguration(), this.mSizeConfigurations);
        if ((536870912 & filterDiff) != 0) {
            return filterDiff & (-536870913);
        }
        return filterDiff;
    }

    private static boolean hasResizeChange(int i) {
        return (i & 3456) != 0;
    }

    void relaunchActivityLocked(boolean z) {
        java.util.ArrayList<android.app.ResultInfo> arrayList;
        java.util.ArrayList<com.android.internal.content.ReferrerIntent> arrayList2;
        android.app.servertransaction.ResumeActivityItem obtain;
        if (this.mAtmService.mSuppressResizeConfigChanges && z) {
            this.configChangeFlags = 0;
            return;
        }
        if (!z) {
            com.android.server.wm.InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
            this.mLastImeShown = (imeInputTarget == null || imeInputTarget.getWindowState() == null || imeInputTarget.getWindowState().mActivityRecord != this || this.mDisplayContent.mInputMethodWindow == null || !this.mDisplayContent.mInputMethodWindow.isVisible()) ? false : true;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask != null && rootTask.mTranslucentActivityWaiting == this) {
            rootTask.checkTranslucentActivityWaiting(null);
        }
        boolean shouldBeResumed = shouldBeResumed(null);
        if (!shouldBeResumed) {
            arrayList = null;
            arrayList2 = null;
        } else {
            arrayList = this.results;
            arrayList2 = this.newIntents;
        }
        if (shouldBeResumed) {
            com.android.server.wm.EventLogTags.writeWmRelaunchResumeActivity(this.mUserId, java.lang.System.identityHashCode(this), this.task.mTaskId, this.shortComponentName, java.lang.Integer.toHexString(this.configChangeFlags));
        } else {
            com.android.server.wm.EventLogTags.writeWmRelaunchActivity(this.mUserId, java.lang.System.identityHashCode(this), this.task.mTaskId, this.shortComponentName, java.lang.Integer.toHexString(this.configChangeFlags));
        }
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 328802837600679598L, 0, null, java.lang.String.valueOf(shouldBeResumed ? "RESUMED" : "PAUSED"), java.lang.String.valueOf(this), java.lang.String.valueOf(android.os.Debug.getCallers(6)));
            android.app.servertransaction.ClientTransactionItem obtain2 = android.app.servertransaction.ActivityRelaunchItem.obtain(this.token, arrayList, arrayList2, this.configChangeFlags, new android.util.MergedConfiguration(getProcessGlobalConfiguration(), getMergedOverrideConfiguration()), z, getActivityWindowInfo());
            if (shouldBeResumed) {
                obtain = android.app.servertransaction.ResumeActivityItem.obtain(this.token, isTransitionForward(), shouldSendCompatFakeFocus());
            } else {
                obtain = android.app.servertransaction.PauseActivityItem.obtain(this.token);
            }
            this.mAtmService.getLifecycleManager().scheduleTransactionAndLifecycleItems(this.app.getThread(), obtain2, obtain);
            startRelaunching();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("ActivityTaskManager", "Failed to relaunch " + this + ": " + e);
        }
        if (shouldBeResumed) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -3997125892953197985L, 0, null, java.lang.String.valueOf(this));
            this.results = null;
            this.newIntents = null;
            this.mAtmService.getAppWarningsLocked().onResumeActivity(this);
        } else {
            removePauseTimeout();
            setState(com.android.server.wm.ActivityRecord.State.PAUSED, "relaunchActivityLocked");
        }
        this.mTaskSupervisor.mStoppingActivities.remove(this);
        this.configChangeFlags = 0;
    }

    void restartProcessIfVisible() {
        if (this.finishing) {
            return;
        }
        android.util.Slog.i("ActivityTaskManager", "Request to restart process of " + this);
        clearSizeCompatMode();
        if (!attachedToProcess()) {
            return;
        }
        setState(com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS, "restartActivityProcess");
        if (!this.mVisibleRequested || this.mHaveState) {
            this.mAtmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.ActivityRecord.this.lambda$restartProcessIfVisible$23();
                }
            });
        } else if (this.mTransitionController.isShellTransitionsEnabled()) {
            final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(5, 0, this.mTransitionController, this.mWmService.mSyncEngine);
            this.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda11
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    com.android.server.wm.ActivityRecord.this.lambda$restartProcessIfVisible$24(transition, z);
                }
            });
        } else {
            startFreezingScreen();
            scheduleStopForRestartProcess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartProcessIfVisible$23() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (hasProcess() && this.app.getReportedProcState() > 6) {
                    com.android.server.wm.WindowProcessController windowProcessController = this.app;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    this.mAtmService.mAmInternal.killProcess(windowProcessController.mName, windowProcessController.mUid, "resetConfig");
                    return;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartProcessIfVisible$24(com.android.server.wm.Transition transition, boolean z) {
        if (this.mState != com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS || !attachedToProcess()) {
            transition.abort();
            return;
        }
        setVisibleRequested(false);
        transition.collect(this);
        this.mTransitionController.requestStartTransition(transition, this.task, null, null);
        scheduleStopForRestartProcess();
    }

    private void scheduleStopForRestartProcess() {
        try {
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(this.app.getThread(), android.app.servertransaction.StopActivityItem.obtain(this.token, 0));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("ActivityTaskManager", "Exception thrown during restart " + this, e);
        }
        this.mTaskSupervisor.scheduleRestartTimeout(this);
    }

    boolean isProcessRunning() {
        com.android.server.wm.WindowProcessController windowProcessController = this.app;
        if (windowProcessController == null) {
            windowProcessController = (com.android.server.wm.WindowProcessController) this.mAtmService.mProcessNames.get(this.processName, this.info.applicationInfo.uid);
        }
        return windowProcessController != null && windowProcessController.hasThread();
    }

    private boolean allowTaskSnapshot() {
        if (this.newIntents == null) {
            return true;
        }
        for (int size = this.newIntents.size() - 1; size >= 0; size--) {
            android.content.Intent intent = this.newIntents.get(size);
            if (intent != null && !isMainIntent(intent)) {
                if (!(this.mLastNewIntent != null ? this.mLastNewIntent.filterEquals(intent) : this.intent.filterEquals(intent)) || intent.getExtras() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isNoHistory() {
        return ((this.intent.getFlags() & 1073741824) == 0 && (this.info.flags & 128) == 0) ? false : true;
    }

    void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfoOrNull;
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_ID, this.createTime);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_LAUNCHEDFROMUID, this.launchedFromUid);
        if (this.launchedFromPackage != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LAUNCHEDFROMPACKAGE, this.launchedFromPackage);
        }
        if (this.launchedFromFeatureId != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LAUNCHEDFROMFEATURE, this.launchedFromFeatureId);
        }
        if (this.resolvedType != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_RESOLVEDTYPE, this.resolvedType);
        }
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_COMPONENTSPECIFIED, this.componentSpecified);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_USERID, this.mUserId);
        if (this.taskDescription != null) {
            this.taskDescription.saveToXml(typedXmlSerializer);
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_INTENT);
        this.intent.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_INTENT);
        if (isPersistable() && this.mPersistentState != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_PERSISTABLEBUNDLE);
            this.mPersistentState.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_PERSISTABLEBUNDLE);
        }
        if (android.security.Flags.contentUriPermissionApis() && (callerInfoOrNull = this.mCallerState.getCallerInfoOrNull(this.initialCallerInfoAccessToken)) != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_INITIAL_CALLER_INFO);
            callerInfoOrNull.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_INITIAL_CALLER_INFO);
        }
    }

    static com.android.server.wm.ActivityRecord restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.Intent intent = null;
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_LAUNCHEDFROMUID, 0);
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_LAUNCHEDFROMPACKAGE);
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_LAUNCHEDFROMFEATURE);
        java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_RESOLVEDTYPE);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_COMPONENTSPECIFIED, false);
        int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_USERID, 0);
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_ID, -1L);
        int depth = typedXmlPullParser.getDepth();
        android.app.ActivityManager.TaskDescription taskDescription = new android.app.ActivityManager.TaskDescription();
        taskDescription.restoreFromXml(typedXmlPullParser);
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfo = null;
        android.os.PersistableBundle persistableBundle = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() < depth)) {
                break;
            }
            if (next == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                if (TAG_INTENT.equals(name)) {
                    intent = android.content.Intent.restoreFromXml(typedXmlPullParser);
                } else if (TAG_PERSISTABLEBUNDLE.equals(name)) {
                    persistableBundle = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                } else if (android.security.Flags.contentUriPermissionApis() && TAG_INITIAL_CALLER_INFO.equals(name)) {
                    callerInfo = com.android.server.wm.ActivityCallerState.CallerInfo.restoreFromXml(typedXmlPullParser);
                } else {
                    android.util.Slog.w("ActivityTaskManager", "restoreActivity: unexpected name=" + name);
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        if (intent == null) {
            throw new org.xmlpull.v1.XmlPullParserException("restoreActivity error intent=" + intent);
        }
        com.android.server.wm.ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
        com.android.server.wm.ActivityCallerState.CallerInfo callerInfo2 = callerInfo;
        android.os.PersistableBundle persistableBundle2 = persistableBundle;
        android.content.pm.ActivityInfo resolveActivity = activityTaskSupervisor.resolveActivity(intent, attributeValue3, 0, null, attributeInt2, android.os.Binder.getCallingUid(), 0);
        if (resolveActivity == null) {
            throw new org.xmlpull.v1.XmlPullParserException("restoreActivity resolver error. Intent=" + intent + " resolvedType=" + attributeValue3);
        }
        com.android.server.wm.ActivityRecord build = new com.android.server.wm.ActivityRecord.Builder(activityTaskManagerService).setLaunchedFromUid(attributeInt).setLaunchedFromPackage(attributeValue).setLaunchedFromFeature(attributeValue2).setIntent(intent).setResolvedType(attributeValue3).setActivityInfo(resolveActivity).setComponentSpecified(attributeBoolean).setPersistentState(persistableBundle2).setTaskDescription(taskDescription).setCreateTime(attributeLong).build();
        if (android.security.Flags.contentUriPermissionApis() && callerInfo2 != null) {
            build.mCallerState.add(build.initialCallerInfoAccessToken, callerInfo2);
        }
        return build;
    }

    private static boolean isInVrUiMode(android.content.res.Configuration configuration) {
        return (configuration.uiMode & 15) == 7;
    }

    private static boolean isInDeskUiMode(android.content.res.Configuration configuration) {
        return (configuration.uiMode & 15) == 2;
    }

    java.lang.String getProcessName() {
        return this.info.applicationInfo.processName;
    }

    int getUid() {
        return this.info.applicationInfo.uid;
    }

    boolean isUid(int i) {
        return this.info.applicationInfo.uid == i;
    }

    int getPid() {
        if (this.app != null) {
            return this.app.getPid();
        }
        return 0;
    }

    int getLaunchedFromPid() {
        return this.launchedFromPid;
    }

    int getLaunchedFromUid() {
        return this.launchedFromUid;
    }

    java.lang.String getFilteredReferrer(java.lang.String str) {
        if (str != null) {
            if (!str.equals(this.packageName) && this.mWmService.mPmInternal.filterAppAccess(str, this.info.applicationInfo.uid, this.mUserId)) {
                return null;
            }
            return str;
        }
        return null;
    }

    boolean canTurnScreenOn() {
        if (getTurnScreenOnFlag()) {
            return this.mCurrentLaunchCanTurnScreenOn && getRootTask() != null && this.mTaskSupervisor.getKeyguardController().checkKeyguardVisibility(this);
        }
        return false;
    }

    void setTurnScreenOn(boolean z) {
        this.mTurnScreenOn = z;
    }

    void setAllowCrossUidActivitySwitchFromBelow(boolean z) {
        this.mAllowCrossUidActivitySwitchFromBelow = z;
    }

    boolean getTurnScreenOnFlag() {
        return this.mTurnScreenOn || containsTurnScreenOnWindow();
    }

    private boolean containsTurnScreenOnWindow() {
        if (isRelaunching()) {
            return this.mLastContainsTurnScreenOnWindow;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if ((((com.android.server.wm.WindowState) this.mChildren.get(size)).mAttrs.flags & 2097152) != 0) {
                return true;
            }
        }
        return false;
    }

    boolean canResumeByCompat() {
        return this.app == null || this.app.updateTopResumingActivityInProcessIfNeeded(this);
    }

    boolean isTopRunningActivity() {
        return this.mRootWindowContainer.topRunningActivity() == this;
    }

    boolean isFocusedActivityOnDisplay() {
        return this.mDisplayContent.forAllTaskDisplayAreas(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isFocusedActivityOnDisplay$25;
                lambda$isFocusedActivityOnDisplay$25 = com.android.server.wm.ActivityRecord.this.lambda$isFocusedActivityOnDisplay$25((com.android.server.wm.TaskDisplayArea) obj);
                return lambda$isFocusedActivityOnDisplay$25;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isFocusedActivityOnDisplay$25(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return taskDisplayArea.getFocusedActivity() == this;
    }

    boolean isRootOfTask() {
        return this.task != null && this == this.task.getRootActivity(true);
    }

    void setTaskOverlay(boolean z) {
        this.mTaskOverlay = z;
        setAlwaysOnTop(this.mTaskOverlay);
    }

    boolean isTaskOverlay() {
        return this.mTaskOverlay;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean isAlwaysOnTop() {
        return this.mTaskOverlay || super.isAlwaysOnTop();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showToCurrentUser() {
        return this.mShowForAllUsers || this.mWmService.isUserVisible(this.mUserId);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canCustomizeAppTransition() {
        return true;
    }

    @Override // com.android.server.wm.WindowToken
    public java.lang.String toString() {
        if (this.stringName != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.stringName);
            sb.append(" t");
            sb.append(this.task == null ? -1 : this.task.mTaskId);
            sb.append(this.finishing ? " f}" : "");
            sb.append(this.mIsExiting ? " isExiting" : "");
            sb.append("}");
            return sb.toString();
        }
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder(128);
        sb2.append("ActivityRecord{");
        sb2.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb2.append(" u");
        sb2.append(this.mUserId);
        sb2.append(' ');
        sb2.append(this.intent.getComponent().flattenToShortString());
        this.stringName = sb2.toString();
        return this.stringName;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        writeNameToProto(protoOutputStream, 1138166333441L);
        super.dumpDebug(protoOutputStream, 1146756268034L, i);
        protoOutputStream.write(1133871366147L, this.mLastSurfaceShowing);
        protoOutputStream.write(1133871366148L, isWaitingForTransitionStart());
        protoOutputStream.write(1133871366149L, isAnimating(7, 17));
        if (this.mThumbnail != null) {
            this.mThumbnail.dumpDebug(protoOutputStream, 1146756268038L);
        }
        protoOutputStream.write(1133871366151L, fillsParent());
        protoOutputStream.write(1133871366152L, this.mAppStopped);
        protoOutputStream.write(1133871366174L, !occludesParent());
        protoOutputStream.write(1133871366168L, this.mVisible);
        protoOutputStream.write(1133871366153L, this.mVisibleRequested);
        protoOutputStream.write(1133871366154L, isClientVisible());
        protoOutputStream.write(1133871366155L, this.mDeferHidingClient);
        protoOutputStream.write(1133871366156L, this.mReportedDrawn);
        protoOutputStream.write(1133871366157L, this.reportedVisible);
        protoOutputStream.write(1120986464270L, this.mNumInterestingWindows);
        protoOutputStream.write(1120986464271L, this.mNumDrawnWindows);
        protoOutputStream.write(1133871366160L, this.allDrawn);
        protoOutputStream.write(1133871366161L, this.mLastAllDrawn);
        if (this.mStartingWindow != null) {
            this.mStartingWindow.writeIdentifierToProto(protoOutputStream, 1146756268051L);
        }
        protoOutputStream.write(1133871366164L, isStartingWindowDisplayed());
        protoOutputStream.write(1133871366345L, this.startingMoved);
        protoOutputStream.write(1133871366166L, this.mVisibleSetFromTransferredStartingWindow);
        protoOutputStream.write(1138166333467L, this.mState.toString());
        protoOutputStream.write(1133871366172L, isRootOfTask());
        if (hasProcess()) {
            protoOutputStream.write(1120986464285L, this.app.getPid());
        }
        protoOutputStream.write(1133871366175L, this.pictureInPictureArgs.isAutoEnterEnabled());
        protoOutputStream.write(1133871366176L, inSizeCompatMode());
        protoOutputStream.write(1108101562401L, getMinAspectRatio());
        protoOutputStream.write(1133871366178L, providesMaxBounds());
        protoOutputStream.write(1133871366179L, this.mEnableRecentsScreenshot);
        protoOutputStream.write(1120986464292L, this.mLastDropInputMode);
        protoOutputStream.write(1120986464293L, getOverrideOrientation());
        protoOutputStream.write(1133871366182L, shouldSendCompatFakeFocus());
        protoOutputStream.write(1133871366183L, this.mLetterboxUiController.shouldForceRotateForCameraCompat());
        protoOutputStream.write(1133871366184L, this.mLetterboxUiController.shouldRefreshActivityForCameraCompat());
        protoOutputStream.write(1133871366185L, this.mLetterboxUiController.shouldRefreshActivityViaPauseForCameraCompat());
        protoOutputStream.write(1133871366186L, this.mLetterboxUiController.shouldOverrideMinAspectRatio());
        protoOutputStream.write(1133871366187L, this.mLetterboxUiController.shouldIgnoreOrientationRequestLoop());
        protoOutputStream.write(1133871366188L, this.mLetterboxUiController.shouldOverrideForceResizeApp());
        protoOutputStream.write(1133871366189L, this.mLetterboxUiController.shouldEnableUserAspectRatioSettings());
        protoOutputStream.write(1133871366190L, this.mLetterboxUiController.isUserFullscreenOverrideEnabled());
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268038L;
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        dumpDebug(protoOutputStream, i);
        protoOutputStream.end(start);
    }

    void writeNameToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        protoOutputStream.write(j, this.shortComponentName);
    }

    @Override // com.android.server.wm.WindowContainer
    void writeIdentifierToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mUserId);
        protoOutputStream.write(1138166333443L, this.intent.getComponent().flattenToShortString());
        protoOutputStream.end(start);
    }

    static class CompatDisplayInsets {
        private final int mHeight;
        final boolean mIsFloating;
        final boolean mIsInFixedOrientationLetterbox;
        final int mOriginalRequestedOrientation;
        final int mOriginalRotation;
        private final int mWidth;
        final android.graphics.Rect[] mNonDecorInsets = new android.graphics.Rect[4];
        final android.graphics.Rect[] mStableInsets = new android.graphics.Rect[4];

        CompatDisplayInsets(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.graphics.Rect rect) {
            int rotation;
            this.mOriginalRotation = displayContent.getRotation();
            this.mIsFloating = activityRecord.getWindowConfiguration().tasksAreFloating();
            this.mOriginalRequestedOrientation = activityRecord.getRequestedConfigurationOrientation();
            if (this.mIsFloating) {
                android.graphics.Rect bounds = activityRecord.getWindowConfiguration().getBounds();
                this.mWidth = bounds.width();
                this.mHeight = bounds.height();
                android.graphics.Rect rect2 = new android.graphics.Rect();
                for (int i = 0; i < 4; i++) {
                    this.mNonDecorInsets[i] = rect2;
                    this.mStableInsets[i] = rect2;
                }
                this.mIsInFixedOrientationLetterbox = false;
                return;
            }
            com.android.server.wm.Task task = activityRecord.getTask();
            this.mIsInFixedOrientationLetterbox = rect != null;
            rect = this.mIsInFixedOrientationLetterbox ? rect : task != null ? task.getBounds() : displayContent.getBounds();
            if (task != null) {
                rotation = task.getConfiguration().windowConfiguration.getRotation();
            } else {
                rotation = displayContent.getConfiguration().windowConfiguration.getRotation();
            }
            android.graphics.Point rotationZeroDimensions = getRotationZeroDimensions(rect, rotation);
            this.mWidth = rotationZeroDimensions.x;
            this.mHeight = rotationZeroDimensions.y;
            android.graphics.Rect rect3 = rect.equals(displayContent.getBounds()) ? null : new android.graphics.Rect();
            com.android.server.wm.DisplayPolicy displayPolicy = displayContent.getDisplayPolicy();
            int i2 = 0;
            while (i2 < 4) {
                this.mNonDecorInsets[i2] = new android.graphics.Rect();
                this.mStableInsets[i2] = new android.graphics.Rect();
                boolean z = i2 == 1 || i2 == 3;
                int i3 = z ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth;
                int i4 = z ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight;
                com.android.server.wm.DisplayPolicy.DecorInsets.Info decorInsetsInfo = displayPolicy.getDecorInsetsInfo(i2, i3, i4);
                this.mNonDecorInsets[i2].set(decorInsetsInfo.mNonDecorInsets);
                this.mStableInsets[i2].set(decorInsetsInfo.mConfigInsets);
                if (rect3 != null) {
                    rect3.set(rect);
                    displayContent.rotateBounds(rotation, i2, rect3);
                    updateInsetsForBounds(rect3, i3, i4, this.mNonDecorInsets[i2]);
                    updateInsetsForBounds(rect3, i3, i4, this.mStableInsets[i2]);
                }
                i2++;
            }
        }

        private static android.graphics.Point getRotationZeroDimensions(android.graphics.Rect rect, int i) {
            boolean z = true;
            if (i != 1 && i != 3) {
                z = false;
            }
            int width = rect.width();
            int height = rect.height();
            return z ? new android.graphics.Point(height, width) : new android.graphics.Point(width, height);
        }

        private static void updateInsetsForBounds(android.graphics.Rect rect, int i, int i2, android.graphics.Rect rect2) {
            rect2.left = java.lang.Math.max(0, rect2.left - rect.left);
            rect2.top = java.lang.Math.max(0, rect2.top - rect.top);
            rect2.right = java.lang.Math.max(0, (rect.right - i) + rect2.right);
            rect2.bottom = java.lang.Math.max(0, (rect.bottom - i2) + rect2.bottom);
        }

        void getBoundsByRotation(android.graphics.Rect rect, int i) {
            boolean z = true;
            if (i != 1 && i != 3) {
                z = false;
            }
            rect.set(0, 0, z ? this.mHeight : this.mWidth, z ? this.mWidth : this.mHeight);
        }

        void getFrameByOrientation(android.graphics.Rect rect, int i) {
            int max = java.lang.Math.max(this.mWidth, this.mHeight);
            int min = java.lang.Math.min(this.mWidth, this.mHeight);
            boolean z = i == 2;
            int i2 = z ? max : min;
            if (z) {
                max = min;
            }
            rect.set(0, 0, i2, max);
        }

        void getContainerBounds(android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2, boolean z, boolean z2) {
            getFrameByOrientation(rect2, i2);
            if (this.mIsFloating) {
                rect.set(rect2);
                return;
            }
            getBoundsByRotation(rect, i);
            int width = rect.width();
            int height = rect.height();
            boolean z3 = (rect2.width() > rect2.height()) != (width > height);
            if (z3 && z2 && z) {
                if (i2 == 2) {
                    float f = width;
                    rect2.bottom = (int) ((f * f) / height);
                    rect2.right = width;
                } else {
                    rect2.bottom = height;
                    float f2 = height;
                    rect2.right = (int) ((f2 * f2) / width);
                }
                rect2.offset(com.android.server.wm.ActivityRecord.getCenterOffset(this.mWidth, rect2.width()), 0);
            }
            rect.set(rect2);
            if (z3) {
                android.graphics.Rect rect3 = this.mNonDecorInsets[i];
                rect2.offset(rect3.left, rect3.top);
                rect.offset(rect3.left, rect3.top);
            } else if (i != -1) {
                com.android.server.wm.TaskFragment.intersectWithInsetsIfFits(rect, rect2, this.mNonDecorInsets[i]);
            }
        }
    }

    private static class AppSaturationInfo {
        float[] mMatrix;
        float[] mTranslation;

        private AppSaturationInfo() {
            this.mMatrix = new float[9];
            this.mTranslation = new float[3];
        }

        void setSaturation(float[] fArr, float[] fArr2) {
            java.lang.System.arraycopy(fArr, 0, this.mMatrix, 0, this.mMatrix.length);
            java.lang.System.arraycopy(fArr2, 0, this.mTranslation, 0, this.mTranslation.length);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.RemoteAnimationTarget createRemoteAnimationTarget(com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        com.android.server.wm.WindowState findMainWindow = findMainWindow();
        if (this.task == null || findMainWindow == null) {
            return null;
        }
        boolean z = false;
        android.graphics.Rect rect = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(this.task.getBounds(), android.view.WindowInsets.Type.systemBars(), false).toRect();
        com.android.server.wm.utils.InsetUtils.addInsets(rect, getLetterboxInsets());
        android.view.RemoteAnimationTarget remoteAnimationTarget = new android.view.RemoteAnimationTarget(this.task.mTaskId, remoteAnimationRecord.getMode(), remoteAnimationRecord.mAdapter.mCapturedLeash, !fillsParent(), new android.graphics.Rect(), rect, getPrefixOrderIndex(), remoteAnimationRecord.mAdapter.mPosition, remoteAnimationRecord.mAdapter.mLocalBounds, remoteAnimationRecord.mAdapter.mEndBounds, this.task.getWindowConfiguration(), false, remoteAnimationRecord.mThumbnailAdapter != null ? remoteAnimationRecord.mThumbnailAdapter.mCapturedLeash : null, remoteAnimationRecord.mStartBounds, this.task.getTaskInfo(), checkEnterPictureInPictureAppOpsState());
        remoteAnimationTarget.setShowBackdrop(remoteAnimationRecord.mShowBackdrop);
        if (this.mStartingData != null && this.mStartingData.hasImeSurface()) {
            z = true;
        }
        remoteAnimationTarget.setWillShowImeOnTarget(z);
        remoteAnimationTarget.hasAnimatingParent = remoteAnimationRecord.hasAnimatingParent();
        return remoteAnimationTarget;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canCreateRemoteAnimationTarget() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    void getAnimationFrames(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        com.android.server.wm.WindowState findMainWindow = findMainWindow();
        if (findMainWindow == null) {
            return;
        }
        findMainWindow.getAnimationFrames(rect, rect2, rect3, rect4);
    }

    void setPictureInPictureParams(android.app.PictureInPictureParams pictureInPictureParams) {
        this.pictureInPictureArgs.copyOnlySet(pictureInPictureParams);
        adjustPictureInPictureParamsIfNeeded(getBounds());
        getTask().getRootTask().onPictureInPictureParamsChanged();
    }

    void setShouldDockBigOverlays(boolean z) {
        this.shouldDockBigOverlays = z;
        getTask().getRootTask().onShouldDockBigOverlaysChanged();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isSyncFinished(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        com.android.server.wm.WindowState windowState;
        if (this.task != null && this.task.mSharedStartingData != null && (windowState = this.task.topStartingWindow()) != null && windowState.mSyncState == 2 && this.mDisplayContent.mUnknownAppVisibilityController.allResolved()) {
            return true;
        }
        if (!super.isSyncFinished(syncGroup)) {
            return false;
        }
        if (this.mDisplayContent != null && this.mDisplayContent.mUnknownAppVisibilityController.isVisibilityUnknown(this)) {
            return false;
        }
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mPendingRelaunchCount > 0 || !isAttached()) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.WindowState) this.mChildren.get(size)).isVisibleRequested()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void finishSync(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        if (getSyncGroup() == null || syncGroup == getSyncGroup()) {
            this.mLastAllReadyAtSync = allSyncFinished();
            super.finishSync(transaction, syncGroup, z);
        }
    }

    @android.annotation.Nullable
    android.graphics.Point getMinDimensions() {
        android.content.pm.ActivityInfo.WindowLayout windowLayout = this.info.windowLayout;
        if (windowLayout == null) {
            return null;
        }
        return new android.graphics.Point(windowLayout.minWidth, windowLayout.minHeight);
    }

    private void adjustPictureInPictureParamsIfNeeded(android.graphics.Rect rect) {
        if (this.pictureInPictureArgs != null && this.pictureInPictureArgs.hasSourceBoundsHint()) {
            this.pictureInPictureArgs.getSourceRectHint().offset(rect.left, rect.top);
        }
    }

    private void applyLocaleOverrideIfNeeded(android.content.res.Configuration configuration) {
        com.android.server.wm.ActivityTaskManagerInternal.PackageConfig findPackageConfiguration;
        if (!(isEmbedded() || (this.task != null && this.task.mAlignActivityLocaleWithTask))) {
            return;
        }
        if (((this.task == null || this.task.realActivity == null || this.task.realActivity.getPackageName().equals(this.packageName)) ? false : true) && (findPackageConfiguration = this.mAtmService.mPackageConfigPersister.findPackageConfiguration(this.task.realActivity.getPackageName(), this.mUserId)) != null && findPackageConfiguration.mLocales != null && !findPackageConfiguration.mLocales.isEmpty()) {
            configuration.setLocales(findPackageConfiguration.mLocales);
        }
    }

    boolean shouldSendCompatFakeFocus() {
        return this.mLetterboxUiController.shouldSendFakeFocus() && inMultiWindowMode() && !inPinnedWindowingMode() && !inFreeformWindowingMode();
    }

    boolean canCaptureSnapshot() {
        if (!isSurfaceShowing() || findMainWindow() == null) {
            return false;
        }
        return forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda24
            public final boolean apply(java.lang.Object obj) {
                boolean lambda$canCaptureSnapshot$26;
                lambda$canCaptureSnapshot$26 = com.android.server.wm.ActivityRecord.lambda$canCaptureSnapshot$26((com.android.server.wm.WindowState) obj);
                return lambda$canCaptureSnapshot$26;
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$canCaptureSnapshot$26(com.android.server.wm.WindowState windowState) {
        return windowState.mWinAnimator != null && windowState.mWinAnimator.getShown() && windowState.mWinAnimator.mLastAlpha > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    void overrideCustomTransition(boolean z, int i, int i2, int i3) {
        com.android.server.wm.ActivityRecord.CustomAppTransition customAnimation = getCustomAnimation(z);
        if (customAnimation == null) {
            customAnimation = new com.android.server.wm.ActivityRecord.CustomAppTransition();
            if (z) {
                this.mCustomOpenTransition = customAnimation;
            } else {
                this.mCustomCloseTransition = customAnimation;
            }
        }
        customAnimation.mEnterAnim = i;
        customAnimation.mExitAnim = i2;
        customAnimation.mBackgroundColor = i3;
    }

    void clearCustomTransition(boolean z) {
        if (z) {
            this.mCustomOpenTransition = null;
        } else {
            this.mCustomCloseTransition = null;
        }
    }

    com.android.server.wm.ActivityRecord.CustomAppTransition getCustomAnimation(boolean z) {
        return z ? this.mCustomOpenTransition : this.mCustomCloseTransition;
    }

    static class Builder {
        private android.content.pm.ActivityInfo mActivityInfo;
        private final com.android.server.wm.ActivityTaskManagerService mAtmService;
        private com.android.server.wm.WindowProcessController mCallerApp;
        private boolean mComponentSpecified;
        private android.content.res.Configuration mConfiguration;
        private long mCreateTime;
        private android.content.Intent mIntent;
        private java.lang.String mLaunchedFromFeature;
        private java.lang.String mLaunchedFromPackage;
        private int mLaunchedFromPid;
        private int mLaunchedFromUid;
        private android.app.ActivityOptions mOptions;
        private android.os.PersistableBundle mPersistentState;
        private int mRequestCode;
        private java.lang.String mResolvedType;
        private com.android.server.wm.ActivityRecord mResultTo;
        private java.lang.String mResultWho;
        private boolean mRootVoiceInteraction;
        private com.android.server.wm.ActivityRecord mSourceRecord;
        private android.app.ActivityManager.TaskDescription mTaskDescription;

        Builder(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
            this.mAtmService = activityTaskManagerService;
        }

        com.android.server.wm.ActivityRecord.Builder setCaller(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController) {
            this.mCallerApp = windowProcessController;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setLaunchedFromPid(int i) {
            this.mLaunchedFromPid = i;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setLaunchedFromUid(int i) {
            this.mLaunchedFromUid = i;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setLaunchedFromPackage(java.lang.String str) {
            this.mLaunchedFromPackage = str;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setLaunchedFromFeature(java.lang.String str) {
            this.mLaunchedFromFeature = str;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setResolvedType(java.lang.String str) {
            this.mResolvedType = str;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setActivityInfo(android.content.pm.ActivityInfo activityInfo) {
            this.mActivityInfo = activityInfo;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setResultTo(com.android.server.wm.ActivityRecord activityRecord) {
            this.mResultTo = activityRecord;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setResultWho(java.lang.String str) {
            this.mResultWho = str;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setRequestCode(int i) {
            this.mRequestCode = i;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setComponentSpecified(boolean z) {
            this.mComponentSpecified = z;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setRootVoiceInteraction(boolean z) {
            this.mRootVoiceInteraction = z;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setActivityOptions(android.app.ActivityOptions activityOptions) {
            this.mOptions = activityOptions;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setConfiguration(android.content.res.Configuration configuration) {
            this.mConfiguration = configuration;
            return this;
        }

        com.android.server.wm.ActivityRecord.Builder setSourceRecord(com.android.server.wm.ActivityRecord activityRecord) {
            this.mSourceRecord = activityRecord;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.ActivityRecord.Builder setPersistentState(android.os.PersistableBundle persistableBundle) {
            this.mPersistentState = persistableBundle;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.ActivityRecord.Builder setTaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
            this.mTaskDescription = taskDescription;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.ActivityRecord.Builder setCreateTime(long j) {
            this.mCreateTime = j;
            return this;
        }

        com.android.server.wm.ActivityRecord build() {
            if (this.mConfiguration == null) {
                this.mConfiguration = this.mAtmService.getConfiguration();
            }
            return new com.android.server.wm.ActivityRecord(this.mAtmService, this.mCallerApp, this.mLaunchedFromPid, this.mLaunchedFromUid, this.mLaunchedFromPackage, this.mLaunchedFromFeature, this.mIntent, this.mResolvedType, this.mActivityInfo, this.mConfiguration, this.mResultTo, this.mResultWho, this.mRequestCode, this.mComponentSpecified, this.mRootVoiceInteraction, this.mAtmService.mTaskSupervisor, this.mOptions, this.mSourceRecord, this.mPersistentState, this.mTaskDescription, this.mCreateTime);
        }
    }
}
