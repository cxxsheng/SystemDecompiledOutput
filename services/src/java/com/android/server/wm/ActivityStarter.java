package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivityStarter {
    static final long ASM_RESTRICTIONS = 230590090;
    static final long ENABLE_PENDING_INTENT_BAL_OPTION = 192341120;
    private static final int INVALID_LAUNCH_MODE = -1;
    private static final long MAX_TASK_WEIGHT_FOR_ADDING_ACTIVITY = 300;
    private static final int MOVE_TO_FRONT_ALLOWED = 0;
    private static final int MOVE_TO_FRONT_AVOID_LEGACY = 2;
    private static final int MOVE_TO_FRONT_AVOID_PI_ONLY_CREATOR_ALLOWS = 1;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_CONFIGURATION = "ActivityTaskManager";
    private static final java.lang.String TAG_FOCUS = "ActivityTaskManager";
    private static final java.lang.String TAG_RESULTS = "ActivityTaskManager";
    private static final java.lang.String TAG_USER_LEAVING = "ActivityTaskManager";

    @com.android.internal.annotations.VisibleForTesting
    boolean mAddingToTask;
    private com.android.server.wm.TaskFragment mAddingToTaskFragment;
    private int mBalCode;
    private int mCallingUid;
    private final com.android.server.wm.ActivityStartController mController;
    private boolean mDisplayLockAndOccluded;
    private boolean mDoResume;
    private boolean mFrozeTaskList;
    private com.android.server.wm.Task mInTask;
    private com.android.server.wm.TaskFragment mInTaskFragment;
    private android.content.Intent mIntent;
    private boolean mIntentDelivered;
    private final com.android.server.wm.ActivityStartInterceptor mInterceptor;
    private boolean mIsTaskCleared;
    private com.android.server.wm.ActivityRecord mLastStartActivityRecord;
    private int mLastStartActivityResult;
    private long mLastStartActivityTimeMs;
    private java.lang.String mLastStartReason;
    private int mLaunchFlags;
    private int mLaunchMode;
    private boolean mLaunchTaskBehind;
    private boolean mMovedToFront;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.ActivityRecord mMovedToTopActivity;
    private boolean mNoAnimation;
    private com.android.server.wm.ActivityRecord mNotTop;
    private android.app.ActivityOptions mOptions;
    private com.android.server.wm.TaskDisplayArea mPreferredTaskDisplayArea;
    private int mPreferredWindowingMode;
    private com.android.server.wm.Task mPriorAboveTask;
    private int mRealCallingUid;
    private final com.android.server.wm.RootWindowContainer mRootWindowContainer;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private com.android.server.wm.ActivityRecord mSourceRecord;
    private com.android.server.wm.Task mSourceRootTask;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.ActivityRecord mStartActivity;
    private int mStartFlags;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private com.android.server.wm.Task mTargetRootTask;
    private com.android.server.wm.Task mTargetTask;
    private boolean mTransientLaunch;
    private com.android.internal.app.IVoiceInteractor mVoiceInteractor;
    private android.service.voice.IVoiceInteractionSession mVoiceSession;
    private com.android.server.wm.LaunchParamsController.LaunchParams mLaunchParams = new com.android.server.wm.LaunchParamsController.LaunchParams();
    private int mCanMoveToFrontCode = 0;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.ActivityStarter.Request mRequest = new com.android.server.wm.ActivityStarter.Request();

    @com.android.internal.annotations.VisibleForTesting
    interface Factory {
        com.android.server.wm.ActivityStarter obtain();

        void recycle(com.android.server.wm.ActivityStarter activityStarter);

        void setController(com.android.server.wm.ActivityStartController activityStartController);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MoveToFrontCode {
    }

    static class DefaultFactory implements com.android.server.wm.ActivityStarter.Factory {
        private com.android.server.wm.ActivityStartController mController;
        private com.android.server.wm.ActivityStartInterceptor mInterceptor;
        private com.android.server.wm.ActivityTaskManagerService mService;
        private com.android.server.wm.ActivityTaskSupervisor mSupervisor;
        private final int MAX_STARTER_COUNT = 3;
        private android.util.Pools.SynchronizedPool<com.android.server.wm.ActivityStarter> mStarterPool = new android.util.Pools.SynchronizedPool<>(3);

        DefaultFactory(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, com.android.server.wm.ActivityStartInterceptor activityStartInterceptor) {
            this.mService = activityTaskManagerService;
            this.mSupervisor = activityTaskSupervisor;
            this.mInterceptor = activityStartInterceptor;
        }

        @Override // com.android.server.wm.ActivityStarter.Factory
        public void setController(com.android.server.wm.ActivityStartController activityStartController) {
            this.mController = activityStartController;
        }

        @Override // com.android.server.wm.ActivityStarter.Factory
        public com.android.server.wm.ActivityStarter obtain() {
            com.android.server.wm.ActivityStarter activityStarter = (com.android.server.wm.ActivityStarter) this.mStarterPool.acquire();
            if (activityStarter == null) {
                if (this.mService.mRootWindowContainer == null) {
                    throw new java.lang.IllegalStateException("Too early to start activity.");
                }
                return new com.android.server.wm.ActivityStarter(this.mController, this.mService, this.mSupervisor, this.mInterceptor);
            }
            return activityStarter;
        }

        @Override // com.android.server.wm.ActivityStarter.Factory
        public void recycle(com.android.server.wm.ActivityStarter activityStarter) {
            activityStarter.reset(true);
            this.mStarterPool.release(activityStarter);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Request {
        private static final int DEFAULT_CALLING_PID = 0;
        private static final int DEFAULT_CALLING_UID = -1;
        static final int DEFAULT_REAL_CALLING_PID = 0;
        static final int DEFAULT_REAL_CALLING_UID = -1;
        android.content.pm.ActivityInfo activityInfo;
        com.android.server.wm.SafeActivityOptions activityOptions;
        boolean allowPendingRemoteAnimationRegistryLookup;
        boolean avoidMoveToFront;
        android.app.IApplicationThread caller;

        @android.annotation.Nullable
        java.lang.String callingFeatureId;
        java.lang.String callingPackage;
        boolean componentSpecified;
        android.content.Intent ephemeralIntent;

        @android.annotation.Nullable
        android.os.IBinder errorCallbackToken;
        int filterCallingUid;
        android.app.BackgroundStartPrivileges forcedBalByPiSender;
        boolean freezeScreen;
        android.content.res.Configuration globalConfig;
        boolean ignoreTargetSecurity;
        com.android.server.wm.Task inTask;
        com.android.server.wm.TaskFragment inTaskFragment;
        android.content.Intent intent;
        com.android.server.uri.NeededUriGrants intentGrants;
        com.android.server.am.PendingIntentRecord originatingPendingIntent;
        com.android.server.wm.ActivityRecord[] outActivity;
        android.app.ProfilerInfo profilerInfo;
        java.lang.String reason;
        int requestCode;
        android.content.pm.ResolveInfo resolveInfo;
        java.lang.String resolvedType;
        android.os.IBinder resultTo;
        java.lang.String resultWho;
        int startFlags;
        int userId;
        com.android.internal.app.IVoiceInteractor voiceInteractor;
        android.service.voice.IVoiceInteractionSession voiceSession;
        android.app.WaitResult waitResult;
        int callingPid = 0;
        int callingUid = -1;
        int realCallingPid = 0;
        int realCallingUid = -1;
        final java.lang.StringBuilder logMessage = new java.lang.StringBuilder();

        Request() {
            reset();
        }

        void reset() {
            this.caller = null;
            this.intent = null;
            this.intentGrants = null;
            this.ephemeralIntent = null;
            this.resolvedType = null;
            this.activityInfo = null;
            this.resolveInfo = null;
            this.voiceSession = null;
            this.voiceInteractor = null;
            this.resultTo = null;
            this.resultWho = null;
            this.requestCode = 0;
            this.callingPid = 0;
            this.callingUid = -1;
            this.callingPackage = null;
            this.callingFeatureId = null;
            this.realCallingPid = 0;
            this.realCallingUid = -1;
            this.startFlags = 0;
            this.activityOptions = null;
            this.ignoreTargetSecurity = false;
            this.componentSpecified = false;
            this.outActivity = null;
            this.inTask = null;
            this.inTaskFragment = null;
            this.reason = null;
            this.profilerInfo = null;
            this.globalConfig = null;
            this.userId = 0;
            this.waitResult = null;
            this.avoidMoveToFront = false;
            this.allowPendingRemoteAnimationRegistryLookup = true;
            this.filterCallingUid = com.android.server.am.ProcessList.INVALID_ADJ;
            this.originatingPendingIntent = null;
            this.forcedBalByPiSender = android.app.BackgroundStartPrivileges.NONE;
            this.freezeScreen = false;
            this.errorCallbackToken = null;
        }

        void set(@android.annotation.NonNull com.android.server.wm.ActivityStarter.Request request) {
            this.caller = request.caller;
            this.intent = request.intent;
            this.intentGrants = request.intentGrants;
            this.ephemeralIntent = request.ephemeralIntent;
            this.resolvedType = request.resolvedType;
            this.activityInfo = request.activityInfo;
            this.resolveInfo = request.resolveInfo;
            this.voiceSession = request.voiceSession;
            this.voiceInteractor = request.voiceInteractor;
            this.resultTo = request.resultTo;
            this.resultWho = request.resultWho;
            this.requestCode = request.requestCode;
            this.callingPid = request.callingPid;
            this.callingUid = request.callingUid;
            this.callingPackage = request.callingPackage;
            this.callingFeatureId = request.callingFeatureId;
            this.realCallingPid = request.realCallingPid;
            this.realCallingUid = request.realCallingUid;
            this.startFlags = request.startFlags;
            this.activityOptions = request.activityOptions;
            this.ignoreTargetSecurity = request.ignoreTargetSecurity;
            this.componentSpecified = request.componentSpecified;
            this.outActivity = request.outActivity;
            this.inTask = request.inTask;
            this.inTaskFragment = request.inTaskFragment;
            this.reason = request.reason;
            this.profilerInfo = request.profilerInfo;
            this.globalConfig = request.globalConfig;
            this.userId = request.userId;
            this.waitResult = request.waitResult;
            this.avoidMoveToFront = request.avoidMoveToFront;
            this.allowPendingRemoteAnimationRegistryLookup = request.allowPendingRemoteAnimationRegistryLookup;
            this.filterCallingUid = request.filterCallingUid;
            this.originatingPendingIntent = request.originatingPendingIntent;
            this.forcedBalByPiSender = request.forcedBalByPiSender;
            this.freezeScreen = request.freezeScreen;
            this.errorCallbackToken = request.errorCallbackToken;
        }

        void resolveActivity(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
            int i;
            if (this.realCallingPid == 0) {
                this.realCallingPid = android.os.Binder.getCallingPid();
            }
            if (this.realCallingUid == -1) {
                this.realCallingUid = android.os.Binder.getCallingUid();
            }
            if (this.callingUid >= 0) {
                this.callingPid = -1;
            } else if (this.caller == null) {
                this.callingPid = this.realCallingPid;
                this.callingUid = this.realCallingUid;
            } else {
                this.callingUid = -1;
                this.callingPid = -1;
            }
            int i2 = this.callingUid;
            if (this.caller == null) {
                i = i2;
            } else {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = activityTaskSupervisor.mService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.WindowProcessController processController = activityTaskSupervisor.mService.getProcessController(this.caller);
                        if (processController != null) {
                            i2 = processController.mInfo.uid;
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                i = i2;
            }
            this.ephemeralIntent = new android.content.Intent(this.intent);
            this.intent = new android.content.Intent(this.intent);
            if (this.intent.getComponent() != null && ((!"android.intent.action.VIEW".equals(this.intent.getAction()) || this.intent.getData() != null) && !"android.intent.action.INSTALL_INSTANT_APP_PACKAGE".equals(this.intent.getAction()) && !"android.intent.action.RESOLVE_INSTANT_APP_PACKAGE".equals(this.intent.getAction()) && activityTaskSupervisor.mService.getPackageManagerInternalLocked().isInstantAppInstallerComponent(this.intent.getComponent()))) {
                this.intent.setComponent(null);
            }
            this.resolveInfo = activityTaskSupervisor.resolveIntent(this.intent, this.resolvedType, this.userId, 0, com.android.server.wm.ActivityStarter.computeResolveFilterUid(this.callingUid, this.realCallingUid, this.filterCallingUid), this.realCallingPid);
            if (this.resolveInfo == null) {
                this.resolveInfo = resolveIntentForLockedOrStoppedProfiles(activityTaskSupervisor);
            }
            this.activityInfo = activityTaskSupervisor.resolveActivity(this.intent, this.resolveInfo, this.startFlags, this.profilerInfo);
            if (this.activityInfo != null) {
                if (android.security.Flags.contentUriPermissionApis()) {
                    this.intentGrants = activityTaskSupervisor.mService.mUgmInternal.checkGrantUriPermissionFromIntent(this.intent, i, this.activityInfo.applicationInfo.packageName, android.os.UserHandle.getUserId(this.activityInfo.applicationInfo.uid), this.activityInfo.requireContentUriPermissionFromCaller);
                } else {
                    this.intentGrants = activityTaskSupervisor.mService.mUgmInternal.checkGrantUriPermissionFromIntent(this.intent, i, this.activityInfo.applicationInfo.packageName, android.os.UserHandle.getUserId(this.activityInfo.applicationInfo.uid));
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        android.content.pm.ResolveInfo resolveIntentForLockedOrStoppedProfiles(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
            boolean z;
            android.content.pm.UserInfo userInfo = activityTaskSupervisor.getUserInfo(this.userId);
            if (userInfo != null && userInfo.isProfile()) {
                android.os.UserManager userManager = android.os.UserManager.get(activityTaskSupervisor.mService.mContext);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.pm.UserInfo profileParent = userManager.getProfileParent(this.userId);
                    if (profileParent != null && userManager.isUserUnlockingOrUnlocked(profileParent.id)) {
                        if (!userManager.isUserUnlockingOrUnlocked(this.userId)) {
                            z = true;
                            if (!z) {
                                return activityTaskSupervisor.resolveIntent(this.intent, this.resolvedType, this.userId, 786432, com.android.server.wm.ActivityStarter.computeResolveFilterUid(this.callingUid, this.realCallingUid, this.filterCallingUid), this.realCallingPid);
                            }
                            return null;
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                return null;
            }
        }
    }

    ActivityStarter(com.android.server.wm.ActivityStartController activityStartController, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, com.android.server.wm.ActivityStartInterceptor activityStartInterceptor) {
        this.mController = activityStartController;
        this.mService = activityTaskManagerService;
        this.mRootWindowContainer = activityTaskManagerService.mRootWindowContainer;
        this.mSupervisor = activityTaskSupervisor;
        this.mInterceptor = activityStartInterceptor;
        reset(true);
    }

    void set(com.android.server.wm.ActivityStarter activityStarter) {
        this.mStartActivity = activityStarter.mStartActivity;
        this.mIntent = activityStarter.mIntent;
        this.mCallingUid = activityStarter.mCallingUid;
        this.mRealCallingUid = activityStarter.mRealCallingUid;
        this.mOptions = activityStarter.mOptions;
        this.mBalCode = activityStarter.mBalCode;
        this.mLaunchTaskBehind = activityStarter.mLaunchTaskBehind;
        this.mLaunchFlags = activityStarter.mLaunchFlags;
        this.mLaunchMode = activityStarter.mLaunchMode;
        this.mLaunchParams.set(activityStarter.mLaunchParams);
        this.mNotTop = activityStarter.mNotTop;
        this.mDoResume = activityStarter.mDoResume;
        this.mStartFlags = activityStarter.mStartFlags;
        this.mSourceRecord = activityStarter.mSourceRecord;
        this.mPreferredTaskDisplayArea = activityStarter.mPreferredTaskDisplayArea;
        this.mPreferredWindowingMode = activityStarter.mPreferredWindowingMode;
        this.mInTask = activityStarter.mInTask;
        this.mInTaskFragment = activityStarter.mInTaskFragment;
        this.mAddingToTask = activityStarter.mAddingToTask;
        this.mSourceRootTask = activityStarter.mSourceRootTask;
        this.mTargetTask = activityStarter.mTargetTask;
        this.mTargetRootTask = activityStarter.mTargetRootTask;
        this.mIsTaskCleared = activityStarter.mIsTaskCleared;
        this.mMovedToFront = activityStarter.mMovedToFront;
        this.mNoAnimation = activityStarter.mNoAnimation;
        this.mCanMoveToFrontCode = activityStarter.mCanMoveToFrontCode;
        this.mFrozeTaskList = activityStarter.mFrozeTaskList;
        this.mVoiceSession = activityStarter.mVoiceSession;
        this.mVoiceInteractor = activityStarter.mVoiceInteractor;
        this.mIntentDelivered = activityStarter.mIntentDelivered;
        this.mLastStartActivityResult = activityStarter.mLastStartActivityResult;
        this.mLastStartActivityTimeMs = activityStarter.mLastStartActivityTimeMs;
        this.mLastStartReason = activityStarter.mLastStartReason;
        this.mRequest.set(activityStarter.mRequest);
    }

    boolean relatedToPackage(java.lang.String str) {
        return (this.mLastStartActivityRecord != null && str.equals(this.mLastStartActivityRecord.packageName)) || (this.mStartActivity != null && str.equals(this.mStartActivity.packageName));
    }

    int execute() {
        com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching;
        int i;
        try {
            onExecutionStarted();
            if (this.mRequest.intent != null) {
                if (this.mRequest.intent.hasFileDescriptors()) {
                    throw new java.lang.IllegalArgumentException("File descriptors passed in Intent");
                }
                this.mRequest.intent.removeExtendedFlags(1);
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    notifyActivityLaunching = this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunching(this.mRequest.intent, com.android.server.wm.ActivityRecord.forTokenLocked(this.mRequest.resultTo), this.mRequest.realCallingUid == -1 ? android.os.Binder.getCallingUid() : this.mRequest.realCallingUid);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            if (this.mRequest.intent != null) {
                this.mRequest.componentSpecified |= this.mRequest.intent.getComponent() != null;
            }
            if (this.mRequest.activityInfo == null) {
                this.mRequest.resolveActivity(this.mSupervisor);
            }
            if (this.mRequest.intent != null) {
                java.lang.String action = this.mRequest.intent.getAction();
                java.lang.String str = this.mRequest.callingPackage;
                if (action != null && str != null && ("com.android.internal.intent.action.REQUEST_SHUTDOWN".equals(action) || "android.intent.action.ACTION_SHUTDOWN".equals(action) || "android.intent.action.REBOOT".equals(action))) {
                    com.android.server.power.ShutdownCheckPoints.recordCheckPoint(action, str, null);
                }
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    boolean z = (this.mRequest.globalConfig == null || this.mService.getGlobalConfiguration().diff(this.mRequest.globalConfig) == 0) ? false : true;
                    com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
                    if (topDisplayFocusedRootTask != null) {
                        topDisplayFocusedRootTask.mConfigWillChange = z;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 1665699123574159131L, 3, null, java.lang.Boolean.valueOf(z));
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        i = resolveToHeavyWeightSwitcherIfNeeded();
                        if (i != 0) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            java.lang.StringBuilder sb = this.mRequest.logMessage;
                            sb.append(" result code=");
                            sb.append(i);
                            android.util.Slog.i("ActivityTaskManager", this.mRequest.logMessage.toString());
                            this.mRequest.logMessage.setLength(0);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            onExecutionComplete();
                            return i;
                        }
                        try {
                            int executeRequest = executeRequest(this.mRequest);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            java.lang.StringBuilder sb2 = this.mRequest.logMessage;
                            sb2.append(" result code=");
                            sb2.append(executeRequest);
                            android.util.Slog.i("ActivityTaskManager", this.mRequest.logMessage.toString());
                            this.mRequest.logMessage.setLength(0);
                            if (z) {
                                this.mService.mAmInternal.enforceCallingPermission("android.permission.CHANGE_CONFIGURATION", "updateConfiguration()");
                                if (topDisplayFocusedRootTask != null) {
                                    topDisplayFocusedRootTask.mConfigWillChange = false;
                                }
                                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 4748139468532105082L, 0, null, null);
                                this.mService.updateConfigurationLocked(this.mRequest.globalConfig, null, false);
                            }
                            android.app.ActivityOptions originalOptions = this.mRequest.activityOptions != null ? this.mRequest.activityOptions.getOriginalOptions() : null;
                            com.android.server.wm.ActivityRecord activityRecord = this.mDoResume ? this.mLastStartActivityRecord : null;
                            this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunched(notifyActivityLaunching, executeRequest, this.mStartActivity == activityRecord, activityRecord, originalOptions);
                            if (this.mRequest.waitResult != null) {
                                this.mRequest.waitResult.result = executeRequest;
                                executeRequest = waitResultIfNeeded(this.mRequest.waitResult, this.mLastStartActivityRecord, notifyActivityLaunching);
                            }
                            int externalResult = getExternalResult(executeRequest);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            onExecutionComplete();
                            return externalResult;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            java.lang.StringBuilder sb3 = this.mRequest.logMessage;
                            sb3.append(" result code=");
                            sb3.append(i);
                            android.util.Slog.i("ActivityTaskManager", this.mRequest.logMessage.toString());
                            this.mRequest.logMessage.setLength(0);
                            throw th;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        i = -96;
                    }
                } finally {
                }
            }
        } catch (java.lang.Throwable th3) {
            onExecutionComplete();
            throw th3;
        }
    }

    private int resolveToHeavyWeightSwitcherIfNeeded() {
        com.android.server.wm.WindowProcessController windowProcessController;
        int i;
        if (this.mRequest.activityInfo == null || !this.mService.mHasHeavyWeightFeature) {
            return 0;
        }
        if ((this.mRequest.activityInfo.applicationInfo.privateFlags & 2) != 0 && this.mRequest.activityInfo.processName.equals(this.mRequest.activityInfo.applicationInfo.packageName) && (windowProcessController = this.mService.mHeavyWeightProcess) != null) {
            if (windowProcessController.mInfo.uid == this.mRequest.activityInfo.applicationInfo.uid && windowProcessController.mName.equals(this.mRequest.activityInfo.processName)) {
                return 0;
            }
            int i2 = this.mRequest.callingUid;
            if (this.mRequest.caller != null) {
                com.android.server.wm.WindowProcessController processController = this.mService.getProcessController(this.mRequest.caller);
                if (processController != null) {
                    i = processController.mInfo.uid;
                } else {
                    android.util.Slog.w("ActivityTaskManager", "Unable to find app for caller " + this.mRequest.caller + " (pid=" + this.mRequest.callingPid + ") when starting: " + this.mRequest.intent.toString());
                    com.android.server.wm.SafeActivityOptions.abort(this.mRequest.activityOptions);
                    return -94;
                }
            } else {
                i = i2;
            }
            android.content.IIntentSender intentSenderLocked = this.mService.getIntentSenderLocked(2, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, null, i, this.mRequest.userId, null, null, 0, new android.content.Intent[]{this.mRequest.intent}, new java.lang.String[]{this.mRequest.resolvedType}, com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, null);
            android.content.Intent intent = new android.content.Intent();
            if (this.mRequest.requestCode >= 0) {
                intent.putExtra("has_result", true);
            }
            intent.putExtra("intent", new android.content.IntentSender(intentSenderLocked));
            windowProcessController.updateIntentForHeavyWeightActivity(intent);
            intent.putExtra("new_app", this.mRequest.activityInfo.packageName);
            intent.setFlags(this.mRequest.intent.getFlags());
            intent.setClassName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.internal.app.HeavyWeightSwitcherActivity.class.getName());
            this.mRequest.intent = intent;
            this.mRequest.resolvedType = null;
            this.mRequest.caller = null;
            this.mRequest.callingUid = android.os.Binder.getCallingUid();
            this.mRequest.callingPid = android.os.Binder.getCallingPid();
            this.mRequest.componentSpecified = true;
            this.mRequest.resolveInfo = this.mSupervisor.resolveIntent(this.mRequest.intent, null, this.mRequest.userId, 0, computeResolveFilterUid(this.mRequest.callingUid, this.mRequest.realCallingUid, this.mRequest.filterCallingUid), this.mRequest.realCallingPid);
            this.mRequest.activityInfo = this.mRequest.resolveInfo != null ? this.mRequest.resolveInfo.activityInfo : null;
            if (this.mRequest.activityInfo != null) {
                this.mRequest.activityInfo = this.mService.mAmInternal.getActivityInfoForUser(this.mRequest.activityInfo, this.mRequest.userId);
                return 0;
            }
            return 0;
        }
        return 0;
    }

    private int waitResultIfNeeded(android.app.WaitResult waitResult, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState) {
        int i = waitResult.result;
        if (i == 3 || (i == 2 && activityRecord.nowVisible && activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED))) {
            waitResult.timeout = false;
            waitResult.who = activityRecord.mActivityComponent;
            waitResult.totalTime = 0L;
            return i;
        }
        this.mSupervisor.waitActivityVisibleOrLaunched(waitResult, activityRecord, launchingState);
        if (i == 0 && waitResult.result == 2) {
            return 2;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x02cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x05ee  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0594  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0267 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int executeRequest(com.android.server.wm.ActivityStarter.Request request) {
        com.android.server.wm.TaskFragment taskFragment;
        int i;
        int i2;
        com.android.server.wm.WindowProcessController windowProcessController;
        com.android.server.wm.WindowProcessController windowProcessController2;
        int i3;
        int i4;
        int i5;
        java.lang.String str;
        int i6;
        java.lang.String str2;
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.ActivityRecord activityRecord2;
        int flags;
        com.android.server.wm.ActivityRecord activityRecord3;
        int i7;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.String str6;
        int i8;
        int i9;
        com.android.server.wm.ActivityRecord activityRecord4;
        int i10;
        java.lang.String str7;
        int i11;
        java.lang.String str8;
        int i12;
        java.lang.String str9;
        java.lang.String str10;
        com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict;
        android.app.ActivityOptions activityOptions;
        android.app.ActivityOptions activityOptions2;
        com.android.server.wm.Task task;
        int i13;
        com.android.server.uri.NeededUriGrants neededUriGrants;
        int i14;
        java.lang.String str11;
        com.android.server.uri.NeededUriGrants neededUriGrants2;
        com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict2;
        int i15;
        int i16;
        int i17;
        android.content.pm.ResolveInfo resolveInfo;
        java.lang.String str12;
        android.content.Intent intent;
        com.android.server.wm.ActivityRecord activityRecord5;
        android.app.ActivityOptions activityOptions3;
        int i18;
        boolean z;
        java.lang.String str13;
        java.lang.String str14;
        int i19;
        com.android.server.wm.ActivityRecord activityRecord6;
        com.android.server.wm.ActivityRecord build;
        com.android.server.wm.WindowProcessController process;
        if (!android.text.TextUtils.isEmpty(request.reason)) {
            this.mLastStartReason = request.reason;
            this.mLastStartActivityTimeMs = java.lang.System.currentTimeMillis();
            if (this.mLastStartActivityRecord != null) {
                this.mLastStartActivityRecord.setCurrentLaunchCanTurnScreenOn(false);
            }
            this.mLastStartActivityRecord = null;
            android.app.IApplicationThread iApplicationThread = request.caller;
            android.content.Intent intent2 = request.intent;
            com.android.server.uri.NeededUriGrants neededUriGrants3 = request.intentGrants;
            java.lang.String str15 = request.resolvedType;
            android.content.pm.ActivityInfo activityInfo = request.activityInfo;
            android.content.pm.ResolveInfo resolveInfo2 = request.resolveInfo;
            android.service.voice.IVoiceInteractionSession iVoiceInteractionSession = request.voiceSession;
            android.os.IBinder iBinder = request.resultTo;
            java.lang.String str16 = request.resultWho;
            int i20 = request.requestCode;
            int i21 = request.callingPid;
            int i22 = request.callingUid;
            java.lang.String str17 = request.callingPackage;
            java.lang.String str18 = request.callingFeatureId;
            int i23 = request.realCallingPid;
            int i24 = request.realCallingUid;
            int i25 = request.startFlags;
            android.content.pm.ResolveInfo resolveInfo3 = resolveInfo2;
            com.android.server.wm.SafeActivityOptions safeActivityOptions = request.activityOptions;
            com.android.server.wm.Task task2 = request.inTask;
            com.android.server.wm.TaskFragment taskFragment2 = request.inTaskFragment;
            android.os.Bundle popAppVerificationBundle = safeActivityOptions != null ? safeActivityOptions.popAppVerificationBundle() : null;
            if (iApplicationThread != null) {
                com.android.server.wm.WindowProcessController processController = this.mService.getProcessController(iApplicationThread);
                if (processController != null) {
                    taskFragment = taskFragment2;
                    i = processController.getPid();
                    windowProcessController = processController;
                    i22 = processController.mInfo.uid;
                    i2 = 0;
                } else {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    taskFragment = taskFragment2;
                    sb.append("Unable to find app for caller ");
                    sb.append(iApplicationThread);
                    sb.append(" (pid=");
                    sb.append(i21);
                    sb.append(") when starting: ");
                    sb.append(intent2.toString());
                    android.util.Slog.w("ActivityTaskManager", sb.toString());
                    i2 = -94;
                    i = i21;
                    i22 = i22;
                    windowProcessController = processController;
                }
            } else {
                taskFragment = taskFragment2;
                i = i21;
                i2 = 0;
                windowProcessController = null;
            }
            if (activityInfo != null) {
                windowProcessController2 = windowProcessController;
                if (activityInfo.applicationInfo != null) {
                    i3 = android.os.UserHandle.getUserId(activityInfo.applicationInfo.uid);
                    if (activityInfo == null) {
                        i4 = i;
                        i5 = activityInfo.launchMode;
                    } else {
                        i4 = i;
                        i5 = 0;
                    }
                    if (i2 == 0) {
                        str = "ActivityTaskManager";
                        i6 = i3;
                        str2 = str15;
                    } else {
                        java.lang.StringBuilder sb2 = request.logMessage;
                        str = "ActivityTaskManager";
                        sb2.append("START u");
                        sb2.append(i3);
                        sb2.append(" {");
                        i6 = i3;
                        str2 = str15;
                        sb2.append(intent2.toShortString(true, true, true, false));
                        sb2.append("} with ");
                        sb2.append(android.content.pm.ActivityInfo.launchModeToString(i5));
                        sb2.append(" from uid ");
                        sb2.append(i22);
                        if (i22 != i24 && i24 != -1) {
                            java.lang.StringBuilder sb3 = request.logMessage;
                            sb3.append(" (realCallingUid=");
                            sb3.append(i24);
                            sb3.append(")");
                        }
                    }
                    if (iBinder != null) {
                        activityRecord = null;
                        activityRecord2 = null;
                    } else {
                        activityRecord = com.android.server.wm.ActivityRecord.isInAnyTask(iBinder);
                        if (activityRecord != null && i20 >= 0 && !activityRecord.finishing) {
                            activityRecord2 = activityRecord;
                        } else {
                            activityRecord2 = activityRecord;
                            activityRecord = null;
                        }
                    }
                    flags = intent2.getFlags();
                    if ((33554432 & flags) == 0 && activityRecord2 != null) {
                        if (i20 >= 0) {
                            com.android.server.wm.SafeActivityOptions.abort(safeActivityOptions);
                            return -93;
                        }
                        com.android.server.wm.ActivityRecord activityRecord7 = activityRecord2.resultTo;
                        if (activityRecord7 != null && !activityRecord7.isInRootTaskLocked()) {
                            activityRecord7 = null;
                        }
                        str5 = activityRecord2.resultWho;
                        int i26 = activityRecord2.requestCode;
                        activityRecord2.resultTo = null;
                        if (activityRecord7 != null) {
                            activityRecord7.removeResultsLocked(activityRecord2, str5, i26);
                        }
                        if (activityRecord2.launchedFromUid != i22) {
                            activityRecord3 = activityRecord7;
                            i7 = i26;
                            str3 = str17;
                            str4 = str18;
                        } else {
                            str3 = activityRecord2.launchedFromPackage;
                            activityRecord3 = activityRecord7;
                            i7 = i26;
                            str4 = activityRecord2.launchedFromFeatureId;
                        }
                    } else {
                        activityRecord3 = activityRecord;
                        i7 = i20;
                        str3 = str17;
                        str4 = str18;
                        str5 = str16;
                    }
                    if (i2 == 0 && intent2.getComponent() == null) {
                        i2 = -91;
                    }
                    if (i2 == 0 || activityInfo != null) {
                        str6 = ")";
                    } else {
                        if (!com.android.server.pm.PackageArchiver.isArchivingEnabled()) {
                            str6 = ")";
                        } else {
                            com.android.server.pm.PackageArchiver packageArchiver = this.mService.getPackageManagerInternalLocked().getPackageArchiver();
                            str6 = ")";
                            if (packageArchiver.isIntentResolvedToArchivedApp(intent2, this.mRequest.userId)) {
                                i2 = packageArchiver.requestUnarchiveOnActivityStart(intent2, str3, this.mRequest.userId, i24);
                            }
                        }
                        i2 = -92;
                    }
                    i8 = -97;
                    if (i2 == 0 || activityRecord2 == null) {
                        i9 = i2;
                        activityRecord4 = activityRecord2;
                        i10 = i24;
                        str7 = str;
                        i11 = i6;
                        str8 = str2;
                    } else {
                        i9 = i2;
                        if (activityRecord2.getTask().voiceSession == null) {
                            activityRecord4 = activityRecord2;
                            i10 = i24;
                            str7 = str;
                            i11 = i6;
                            str8 = str2;
                        } else if ((268435456 & flags) != 0 || activityRecord2.info.applicationInfo.uid == activityInfo.applicationInfo.uid) {
                            activityRecord4 = activityRecord2;
                            i10 = i24;
                            str7 = str;
                            i11 = i6;
                            str8 = str2;
                        } else {
                            try {
                                intent2.addCategory("android.intent.category.VOICE");
                                activityRecord4 = activityRecord2;
                                i10 = i24;
                                i11 = i6;
                                str8 = str2;
                                try {
                                    if (this.mService.getPackageManager().activitySupportsIntentAsUser(intent2.getComponent(), intent2, str8, i11)) {
                                        str7 = str;
                                        i12 = i9;
                                    } else {
                                        str7 = str;
                                        try {
                                            android.util.Slog.w(str7, "Activity being started in current voice task does not support voice: " + intent2);
                                            i12 = -97;
                                        } catch (android.os.RemoteException e) {
                                            e = e;
                                            android.util.Slog.w(str7, "Failure checking voice capabilities", e);
                                            i12 = -97;
                                            if (i12 != 0) {
                                            }
                                            str9 = str4;
                                            i8 = i12;
                                            if (activityRecord3 == null) {
                                            }
                                            if (i8 != 0) {
                                            }
                                        }
                                    }
                                } catch (android.os.RemoteException e2) {
                                    e = e2;
                                    str7 = str;
                                }
                            } catch (android.os.RemoteException e3) {
                                e = e3;
                                activityRecord4 = activityRecord2;
                                i10 = i24;
                                str7 = str;
                                i11 = i6;
                                str8 = str2;
                            }
                            if (i12 != 0 && iVoiceInteractionSession != null) {
                                int i27 = i12;
                                try {
                                    str9 = str4;
                                } catch (android.os.RemoteException e4) {
                                    e = e4;
                                    str9 = str4;
                                }
                                try {
                                    if (this.mService.getPackageManager().activitySupportsIntentAsUser(intent2.getComponent(), intent2, str8, i11)) {
                                        i8 = i27;
                                    } else {
                                        android.util.Slog.w(str7, "Activity being started in new voice task does not support: " + intent2);
                                    }
                                } catch (android.os.RemoteException e5) {
                                    e = e5;
                                    android.util.Slog.w(str7, "Failure checking voice capabilities", e);
                                    if (activityRecord3 == null) {
                                    }
                                    if (i8 != 0) {
                                    }
                                }
                            } else {
                                str9 = str4;
                                i8 = i12;
                            }
                            com.android.server.wm.Task rootTask = activityRecord3 == null ? null : activityRecord3.getRootTask();
                            if (i8 != 0) {
                                if (activityRecord3 != null) {
                                    activityRecord3.sendResult(-1, str5, i7, 0, null, null, null);
                                }
                                com.android.server.wm.SafeActivityOptions.abort(safeActivityOptions);
                                return i8;
                            }
                            try {
                                com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
                                boolean z2 = request.ignoreTargetSecurity;
                                boolean z3 = task2 != null;
                                int i28 = i7;
                                java.lang.String str19 = str5;
                                java.lang.String str20 = str9;
                                com.android.server.wm.WindowProcessController windowProcessController3 = windowProcessController2;
                                int i29 = i11;
                                com.android.server.wm.WindowProcessController windowProcessController4 = windowProcessController3;
                                int i30 = i22;
                                java.lang.String str21 = str3;
                                java.lang.String str22 = str8;
                                boolean z4 = (!this.mService.getPermissionPolicyInternal().checkStartActivity(intent2, i30, str21)) | (!activityTaskSupervisor.checkStartAnyActivityPermission(intent2, activityInfo, str5, i7, i4, i22, str3, str9, z2, z3, windowProcessController2, activityRecord3, rootTask)) | (!this.mService.mIntentFirewall.checkStartActivity(intent2, i22, i4, str8, activityInfo.applicationInfo));
                                android.app.ActivityOptions options = safeActivityOptions != null ? safeActivityOptions.getOptions(intent2, activityInfo, windowProcessController4, this.mSupervisor) : null;
                                if (!z4) {
                                    try {
                                        android.os.Trace.traceBegin(32L, "shouldAbortBackgroundActivityStart");
                                        com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart = this.mSupervisor.getBackgroundActivityLaunchController().checkBackgroundActivityStart(i30, i4, str21, i10, i23, windowProcessController4, request.originatingPendingIntent, request.forcedBalByPiSender, activityRecord3, intent2, options);
                                        java.lang.StringBuilder sb4 = request.logMessage;
                                        sb4.append(" (");
                                        sb4.append(checkBackgroundActivityStart);
                                        sb4.append(str6);
                                        android.os.Trace.traceEnd(32L);
                                        balVerdict = checkBackgroundActivityStart;
                                    } catch (java.lang.Throwable th) {
                                        android.os.Trace.traceEnd(32L);
                                        throw th;
                                    }
                                } else {
                                    balVerdict = com.android.server.wm.BackgroundActivityStartController.BalVerdict.ALLOW_BY_DEFAULT;
                                }
                                if (!request.allowPendingRemoteAnimationRegistryLookup) {
                                    activityOptions = options;
                                } else {
                                    activityOptions = this.mService.getActivityStartController().getPendingRemoteAnimationRegistry().overrideOptionsIfNeeded(str21, options);
                                }
                                if (this.mService.mController != null) {
                                    try {
                                        z4 |= !this.mService.mController.activityStarting(intent2.cloneFilter(), activityInfo.applicationInfo.packageName);
                                    } catch (android.os.RemoteException e6) {
                                        this.mService.mController = null;
                                    }
                                }
                                com.android.server.wm.ActivityRecord activityRecord8 = activityRecord4;
                                com.android.server.wm.TaskDisplayArea computeSuggestedLaunchDisplayArea = computeSuggestedLaunchDisplayArea(task2, activityRecord8, activityOptions);
                                this.mInterceptor.setStates(i29, i23, i10, i25, str21, str20);
                                if (!this.mInterceptor.intercept(intent2, resolveInfo3, activityInfo, str22, task2, taskFragment, i4, i30, activityOptions, computeSuggestedLaunchDisplayArea)) {
                                    activityOptions2 = activityOptions;
                                    task = task2;
                                    i13 = i30;
                                    neededUriGrants = neededUriGrants3;
                                    i14 = i4;
                                    str11 = str22;
                                } else {
                                    intent2 = this.mInterceptor.mIntent;
                                    android.content.pm.ResolveInfo resolveInfo4 = this.mInterceptor.mRInfo;
                                    activityInfo = this.mInterceptor.mAInfo;
                                    str11 = this.mInterceptor.mResolvedType;
                                    com.android.server.wm.Task task3 = this.mInterceptor.mInTask;
                                    i14 = this.mInterceptor.mCallingPid;
                                    int i31 = this.mInterceptor.mCallingUid;
                                    activityOptions2 = this.mInterceptor.mActivityOptions;
                                    resolveInfo3 = resolveInfo4;
                                    task = task3;
                                    i13 = i31;
                                    neededUriGrants = null;
                                }
                                if (z4) {
                                    if (activityRecord3 != null) {
                                        activityRecord3.sendResult(-1, str19, i28, 0, null, null, null);
                                    }
                                    android.app.ActivityOptions.abort(activityOptions2);
                                    return 102;
                                }
                                if (activityInfo == null) {
                                    neededUriGrants2 = neededUriGrants;
                                    balVerdict2 = balVerdict;
                                    i15 = i25;
                                    i16 = i29;
                                    i17 = i10;
                                } else {
                                    neededUriGrants2 = neededUriGrants;
                                    i16 = i29;
                                    if (!this.mService.getPackageManagerInternalLocked().isPermissionsReviewRequired(activityInfo.packageName, i16)) {
                                        balVerdict2 = balVerdict;
                                        i15 = i25;
                                        i17 = i10;
                                    } else {
                                        android.content.IIntentSender intentSenderLocked = this.mService.getIntentSenderLocked(2, str21, str20, i13, i16, null, null, 0, new android.content.Intent[]{intent2}, new java.lang.String[]{str11}, com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, null);
                                        intent = new android.content.Intent("android.intent.action.REVIEW_PERMISSIONS");
                                        int flags2 = intent2.getFlags() | 8388608;
                                        if ((268959744 & flags2) != 0) {
                                            flags2 |= 134217728;
                                        }
                                        intent.setFlags(flags2);
                                        intent.putExtra("android.intent.extra.PACKAGE_NAME", activityInfo.packageName);
                                        intent.putExtra("android.intent.extra.INTENT", new android.content.IntentSender(intentSenderLocked));
                                        if (activityRecord3 != null) {
                                            intent.putExtra("android.intent.extra.RESULT_NEEDED", true);
                                        }
                                        int i32 = i10;
                                        android.content.pm.ResolveInfo resolveIntent = this.mSupervisor.resolveIntent(intent, null, i16, 0, computeResolveFilterUid(i32, i32, request.filterCallingUid), i23);
                                        balVerdict2 = balVerdict;
                                        i15 = i25;
                                        activityInfo = this.mSupervisor.resolveActivity(intent, resolveIntent, i15, null);
                                        resolveInfo = resolveIntent;
                                        i17 = i32;
                                        i13 = i17;
                                        neededUriGrants2 = null;
                                        str12 = null;
                                        i14 = i23;
                                        if (resolveInfo != null || resolveInfo.auxiliaryInfo == null) {
                                            activityRecord5 = activityRecord8;
                                            activityOptions3 = activityOptions2;
                                            i18 = i15;
                                            z = true;
                                            str13 = str21;
                                            str14 = str12;
                                            i19 = i13;
                                        } else {
                                            android.content.pm.ResolveInfo resolveInfo5 = resolveInfo;
                                            z = true;
                                            int i33 = i15;
                                            activityRecord5 = activityRecord8;
                                            str13 = str21;
                                            activityOptions3 = activityOptions2;
                                            i18 = i33;
                                            intent = createLaunchIntent(resolveInfo.auxiliaryInfo, request.ephemeralIntent, str21, str20, popAppVerificationBundle, str12, i16);
                                            str14 = null;
                                            activityInfo = this.mSupervisor.resolveActivity(intent, resolveInfo5, i18, null);
                                            neededUriGrants2 = null;
                                            i19 = i17;
                                            i14 = i23;
                                        }
                                        if (windowProcessController4 == null && i23 > 0 && (process = this.mService.mProcessMap.getProcess(i23)) != null) {
                                            windowProcessController4 = process;
                                        }
                                        com.android.server.wm.ActivityRecord.Builder componentSpecified = new com.android.server.wm.ActivityRecord.Builder(this.mService).setCaller(windowProcessController4).setLaunchedFromPid(i14).setLaunchedFromUid(i19).setLaunchedFromPackage(str13).setLaunchedFromFeature(str20).setIntent(intent).setResolvedType(str14).setActivityInfo(activityInfo).setConfiguration(this.mService.getGlobalConfiguration()).setResultTo(activityRecord3).setResultWho(str19).setRequestCode(i28).setComponentSpecified(request.componentSpecified);
                                        android.app.ActivityOptions activityOptions4 = activityOptions3;
                                        activityRecord6 = activityRecord5;
                                        build = componentSpecified.setRootVoiceInteraction(iVoiceInteractionSession == null ? z : false).setActivityOptions(activityOptions4).setSourceRecord(activityRecord6).build();
                                        this.mLastStartActivityRecord = build;
                                        if (build.appTimeTracker == null && activityRecord6 != null) {
                                            build.appTimeTracker = activityRecord6.appTimeTracker;
                                        }
                                        com.android.server.wm.WindowProcessController windowProcessController5 = this.mService.mHomeProcess;
                                        boolean z5 = (windowProcessController5 == null && activityInfo.applicationInfo.uid == windowProcessController5.mUid) ? z : false;
                                        if (balVerdict2.allows() && !z5) {
                                            this.mService.resumeAppSwitches();
                                        }
                                        this.mLastStartActivityResult = startActivityUnchecked(build, activityRecord6, iVoiceInteractionSession, request.voiceInteractor, i18, activityOptions4, task, taskFragment, balVerdict2, neededUriGrants2, i17);
                                        if (request.outActivity != null) {
                                            request.outActivity[0] = this.mLastStartActivityRecord;
                                        }
                                        return this.mLastStartActivityResult;
                                    }
                                }
                                resolveInfo = resolveInfo3;
                                android.content.Intent intent3 = intent2;
                                str12 = str11;
                                intent = intent3;
                                if (resolveInfo != null) {
                                }
                                activityRecord5 = activityRecord8;
                                activityOptions3 = activityOptions2;
                                i18 = i15;
                                z = true;
                                str13 = str21;
                                str14 = str12;
                                i19 = i13;
                                if (windowProcessController4 == null) {
                                    windowProcessController4 = process;
                                }
                                com.android.server.wm.ActivityRecord.Builder componentSpecified2 = new com.android.server.wm.ActivityRecord.Builder(this.mService).setCaller(windowProcessController4).setLaunchedFromPid(i14).setLaunchedFromUid(i19).setLaunchedFromPackage(str13).setLaunchedFromFeature(str20).setIntent(intent).setResolvedType(str14).setActivityInfo(activityInfo).setConfiguration(this.mService.getGlobalConfiguration()).setResultTo(activityRecord3).setResultWho(str19).setRequestCode(i28).setComponentSpecified(request.componentSpecified);
                                android.app.ActivityOptions activityOptions42 = activityOptions3;
                                activityRecord6 = activityRecord5;
                                build = componentSpecified2.setRootVoiceInteraction(iVoiceInteractionSession == null ? z : false).setActivityOptions(activityOptions42).setSourceRecord(activityRecord6).build();
                                this.mLastStartActivityRecord = build;
                                if (build.appTimeTracker == null) {
                                    build.appTimeTracker = activityRecord6.appTimeTracker;
                                }
                                com.android.server.wm.WindowProcessController windowProcessController52 = this.mService.mHomeProcess;
                                if (windowProcessController52 == null) {
                                }
                                if (balVerdict2.allows()) {
                                    this.mService.resumeAppSwitches();
                                }
                                this.mLastStartActivityResult = startActivityUnchecked(build, activityRecord6, iVoiceInteractionSession, request.voiceInteractor, i18, activityOptions42, task, taskFragment, balVerdict2, neededUriGrants2, i17);
                                if (request.outActivity != null) {
                                }
                                return this.mLastStartActivityResult;
                            } catch (java.lang.SecurityException e7) {
                                int i34 = i7;
                                int i35 = i22;
                                int i36 = i11;
                                android.content.Intent intent4 = request.ephemeralIntent;
                                if (intent4 != null && (intent4.getComponent() != null || intent4.getPackage() != null)) {
                                    if (intent4.getComponent() != null) {
                                        str10 = intent4.getComponent().getPackageName();
                                    } else {
                                        str10 = intent4.getPackage();
                                    }
                                    if (this.mService.getPackageManagerInternalLocked().filterAppAccess(str10, i35, i36)) {
                                        if (activityRecord3 != null) {
                                            activityRecord3.sendResult(-1, str5, i34, 0, null, null, null);
                                        }
                                        com.android.server.wm.SafeActivityOptions.abort(safeActivityOptions);
                                        return -92;
                                    }
                                }
                                throw e7;
                            }
                        }
                    }
                    i12 = i9;
                    if (i12 != 0) {
                    }
                    str9 = str4;
                    i8 = i12;
                    if (activityRecord3 == null) {
                    }
                    if (i8 != 0) {
                    }
                }
            } else {
                windowProcessController2 = windowProcessController;
            }
            i3 = 0;
            if (activityInfo == null) {
            }
            if (i2 == 0) {
            }
            if (iBinder != null) {
            }
            flags = intent2.getFlags();
            if ((33554432 & flags) == 0) {
            }
            activityRecord3 = activityRecord;
            i7 = i20;
            str3 = str17;
            str4 = str18;
            str5 = str16;
            if (i2 == 0) {
                i2 = -91;
            }
            if (i2 == 0) {
            }
            str6 = ")";
            i8 = -97;
            if (i2 == 0) {
            }
            i9 = i2;
            activityRecord4 = activityRecord2;
            i10 = i24;
            str7 = str;
            i11 = i6;
            str8 = str2;
            i12 = i9;
            if (i12 != 0) {
            }
            str9 = str4;
            i8 = i12;
            if (activityRecord3 == null) {
            }
            if (i8 != 0) {
            }
        } else {
            throw new java.lang.IllegalArgumentException("Need to specify a reason.");
        }
    }

    private boolean handleBackgroundActivityAbort(com.android.server.wm.ActivityRecord activityRecord) {
        if (!(!this.mService.isBackgroundActivityStartsEnabled())) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord2 = activityRecord.resultTo;
        java.lang.String str = activityRecord.resultWho;
        int i = activityRecord.requestCode;
        if (activityRecord2 != null) {
            activityRecord2.sendResult(-1, str, i, 0, null, null, null);
        }
        android.app.ActivityOptions.abort(activityRecord.getOptions());
        return true;
    }

    static int getExternalResult(int i) {
        if (i != 102) {
            return i;
        }
        return 0;
    }

    private void onExecutionComplete() {
        this.mController.onExecutionComplete(this);
    }

    private void onExecutionStarted() {
        this.mController.onExecutionStarted();
    }

    @android.annotation.NonNull
    private android.content.Intent createLaunchIntent(@android.annotation.Nullable android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo, android.content.Intent intent, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.os.Bundle bundle, java.lang.String str3, int i) {
        android.content.Intent intent2;
        android.content.ComponentName componentName;
        java.lang.String str4;
        if (auxiliaryResolveInfo != null && auxiliaryResolveInfo.needsPhaseTwo) {
            android.content.pm.PackageManagerInternal packageManagerInternalLocked = this.mService.getPackageManagerInternalLocked();
            packageManagerInternalLocked.requestInstantAppResolutionPhaseTwo(auxiliaryResolveInfo, intent, str3, str, str2, packageManagerInternalLocked.isInstantApp(str, i), bundle, i);
        }
        android.content.Intent sanitizeIntent = com.android.server.pm.InstantAppResolver.sanitizeIntent(intent);
        java.util.List list = null;
        if (auxiliaryResolveInfo != null) {
            intent2 = auxiliaryResolveInfo.failureIntent;
        } else {
            intent2 = null;
        }
        if (auxiliaryResolveInfo != null) {
            componentName = auxiliaryResolveInfo.installFailureActivity;
        } else {
            componentName = null;
        }
        if (auxiliaryResolveInfo != null) {
            str4 = auxiliaryResolveInfo.token;
        } else {
            str4 = null;
        }
        boolean z = auxiliaryResolveInfo != null && auxiliaryResolveInfo.needsPhaseTwo;
        if (auxiliaryResolveInfo != null) {
            list = auxiliaryResolveInfo.filters;
        }
        return com.android.server.pm.InstantAppResolver.buildEphemeralInstallerIntent(intent, sanitizeIntent, intent2, str, str2, bundle, str3, i, componentName, str4, z, list);
    }

    void postStartActivityProcessing(com.android.server.wm.ActivityRecord activityRecord, int i, com.android.server.wm.Task task) {
        com.android.server.wm.Task task2;
        if (!android.app.ActivityManager.isStartResultSuccessful(i) && this.mFrozeTaskList) {
            this.mSupervisor.mRecentTasks.resetFreezeTaskListReorderingOnTimeout();
        }
        if (android.app.ActivityManager.isStartResultFatalError(i)) {
            return;
        }
        this.mSupervisor.reportWaitingActivityLaunchedIfNeeded(activityRecord, i);
        if (activityRecord.getTask() != null) {
            task2 = activityRecord.getTask();
        } else {
            task2 = this.mTargetTask;
        }
        if (task == null || task2 == null || !task2.isAttached()) {
            return;
        }
        if (i == 2 || i == 3) {
            com.android.server.wm.Task rootHomeTask = task2.getDisplayArea().getRootHomeTask();
            boolean z = false;
            boolean z2 = rootHomeTask != null && rootHomeTask.shouldBeVisible(null);
            com.android.server.wm.ActivityRecord topNonFinishingActivity = task2.getTopNonFinishingActivity();
            if (topNonFinishingActivity != null && topNonFinishingActivity.isVisible()) {
                z = true;
            }
            this.mService.getTaskChangeNotificationController().notifyActivityRestartAttempt(task2.getTaskInfo(), z2, this.mIsTaskCleared, z);
        }
        if (android.app.ActivityManager.isStartResultSuccessful(i)) {
            this.mInterceptor.onActivityLaunched(task2.getTaskInfo(), activityRecord);
        }
    }

    static int computeResolveFilterUid(int i, int i2, int i3) {
        if (i3 != -10000) {
            return i3;
        }
        return i >= 0 ? i : i2;
    }

    private int startActivityUnchecked(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i, android.app.ActivityOptions activityOptions, com.android.server.wm.Task task, com.android.server.wm.TaskFragment taskFragment, com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict, com.android.server.uri.NeededUriGrants neededUriGrants, int i2) {
        com.android.server.wm.TransitionController transitionController = activityRecord.mTransitionController;
        com.android.server.wm.Transition createAndStartCollecting = transitionController.isShellTransitionsEnabled() ? transitionController.createAndStartCollecting(1) : null;
        android.window.RemoteTransition takeRemoteTransition = activityRecord.takeRemoteTransition();
        if (createAndStartCollecting != null && this.mRequest.freezeScreen) {
            com.android.server.wm.DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate((this.mLaunchParams.hasPreferredTaskDisplayArea() ? this.mLaunchParams.mPreferredTaskDisplayArea : this.mRootWindowContainer.getDefaultTaskDisplayArea()).getDisplayId());
            if (displayContentOrCreate != null) {
                transitionController.collect(displayContentOrCreate);
                transitionController.collectVisibleChange(displayContentOrCreate);
            }
        }
        try {
            this.mService.deferWindowLayout();
            transitionController.collect(activityRecord);
            int i3 = -96;
            try {
                try {
                    android.os.Trace.traceBegin(32L, "startActivityInner");
                    i3 = startActivityInner(activityRecord, activityRecord2, iVoiceInteractionSession, iVoiceInteractor, i, activityOptions, task, taskFragment, balVerdict, neededUriGrants, i2);
                    android.os.Trace.traceEnd(32L);
                } catch (java.lang.Throwable th) {
                    android.os.Trace.traceEnd(32L);
                    handleStartResult(activityRecord, activityOptions, -96, createAndStartCollecting, takeRemoteTransition);
                    throw th;
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e("ActivityTaskManager", "Exception on startActivityInner", e);
                android.os.Trace.traceEnd(32L);
            }
            com.android.server.wm.Task handleStartResult = handleStartResult(activityRecord, activityOptions, i3, createAndStartCollecting, takeRemoteTransition);
            this.mService.continueWindowLayout();
            postStartActivityProcessing(activityRecord, i3, handleStartResult);
            return i3;
        } catch (java.lang.Throwable th2) {
            this.mService.continueWindowLayout();
            throw th2;
        }
    }

    private boolean avoidMoveToFront() {
        return this.mCanMoveToFrontCode != 0;
    }

    private boolean avoidMoveToFrontPIOnlyCreatorAllows() {
        return this.mCanMoveToFrontCode == 1;
    }

    @android.annotation.Nullable
    private com.android.server.wm.Task handleStartResult(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions, int i, com.android.server.wm.Transition transition, android.window.RemoteTransition remoteTransition) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal;
        boolean z = this.mSupervisor.mUserLeaving;
        this.mSupervisor.mUserLeaving = false;
        com.android.server.wm.Task rootTask = activityRecord.getRootTask();
        if (rootTask == null) {
            rootTask = this.mTargetRootTask;
        }
        if (!android.app.ActivityManager.isStartResultSuccessful(i) || rootTask == null) {
            if (this.mStartActivity.getTask() != null) {
                this.mStartActivity.finishIfPossible("startActivity", true);
            } else if (this.mStartActivity.getParent() != null) {
                this.mStartActivity.getParent().removeChild(this.mStartActivity);
            }
            if (rootTask != null && rootTask.isAttached() && !rootTask.hasActivity() && !rootTask.isActivityTypeHome() && !rootTask.mCreatedByOrganizer) {
                rootTask.removeIfPossible("handleStartResult");
            }
            if (transition != null) {
                transition.abort();
            }
            return null;
        }
        if (android.security.Flags.contentUriPermissionApis() && activityRecord.isAttached()) {
            activityRecord.computeInitialCallerInfo();
        }
        if (activityOptions != null && activityOptions.getTaskAlwaysOnTop()) {
            rootTask.setAlwaysOnTop(true);
        }
        com.android.server.wm.ActivityRecord activityRecord2 = rootTask.topRunningActivity();
        if (activityRecord2 != null && activityRecord2.shouldUpdateConfigForDisplayChanged()) {
            this.mRootWindowContainer.ensureVisibilityAndConfig(activityRecord2, activityRecord2.getDisplayId(), false);
        }
        if (!avoidMoveToFront() && this.mDoResume && this.mRootWindowContainer.hasVisibleWindowAboveButDoesNotOwnNotificationShade(activityRecord.launchedFromUid) && (statusBarManagerInternal = this.mService.getStatusBarManagerInternal()) != null) {
            statusBarManagerInternal.collapsePanels();
        }
        com.android.server.wm.TransitionController transitionController = activityRecord.mTransitionController;
        boolean z2 = i == 0 || i == 2;
        boolean z3 = (activityOptions != null && activityOptions.getTransientLaunch()) && this.mPriorAboveTask != null && this.mDisplayLockAndOccluded;
        if (z2) {
            transitionController.collectExistenceChange(activityRecord);
        } else if (i == 3 && transition != null && this.mMovedToTopActivity == null && !transitionController.hasOrderChanges() && !transitionController.isTransientHide(rootTask) && !z3) {
            transition.abort();
            transition = null;
        }
        if (z3) {
            transitionController.collect(this.mLastStartActivityRecord);
            transitionController.collect(this.mPriorAboveTask);
            transitionController.setTransientLaunch(this.mLastStartActivityRecord, this.mPriorAboveTask);
            com.android.server.wm.DisplayContent displayContent = this.mLastStartActivityRecord.getDisplayContent();
            displayContent.mWallpaperController.adjustWallpaperWindows();
            transitionController.setReady(displayContent, true);
        }
        if (!z) {
            transitionController.setCanPipOnFinish(false);
        }
        if (transition != null) {
            transitionController.requestStartTransition(transition, this.mTargetTask == null ? activityRecord.getTask() : this.mTargetTask, remoteTransition, null);
        } else if ((i != 0 || !this.mStartActivity.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) && z2) {
            transitionController.setReady(activityRecord, false);
        }
        return rootTask;
    }

    @com.android.internal.annotations.VisibleForTesting
    int startActivityInner(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i, android.app.ActivityOptions activityOptions, com.android.server.wm.Task task, com.android.server.wm.TaskFragment taskFragment, com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict, com.android.server.uri.NeededUriGrants neededUriGrants, int i2) {
        boolean z;
        com.android.server.wm.Task task2;
        int deliverToCurrentTopIfNeeded;
        com.android.server.wm.ActivityRecord findActivity;
        setInitialState(activityRecord, activityOptions, task, taskFragment, i, activityRecord2, iVoiceInteractionSession, iVoiceInteractor, balVerdict.getCode(), i2);
        computeLaunchingTaskFlags();
        this.mIntent.setFlags(this.mLaunchFlags);
        java.util.Iterator<com.android.server.wm.ActivityRecord> it = this.mSupervisor.mStoppingActivities.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (it.next().getActivityType() == 5) {
                z = true;
                break;
            }
        }
        com.android.server.wm.Task focusedRootTask = this.mPreferredTaskDisplayArea.getFocusedRootTask();
        com.android.server.wm.Task topLeafTask = focusedRootTask != null ? focusedRootTask.getTopLeafTask() : null;
        com.android.server.wm.Task reusableTask = getReusableTask();
        if (this.mOptions != null && this.mOptions.freezeRecentTasksReordering() && this.mSupervisor.mRecentTasks.isCallerRecents(activityRecord.launchedFromUid) && !this.mSupervisor.mRecentTasks.isFreezeTaskListReorderingSet()) {
            this.mFrozeTaskList = true;
            this.mSupervisor.mRecentTasks.setFreezeTaskListReordering();
        }
        com.android.server.wm.Task computeTargetTask = reusableTask != null ? reusableTask : computeTargetTask();
        boolean z2 = computeTargetTask == null;
        this.mTargetTask = computeTargetTask;
        computeLaunchParams(activityRecord, activityRecord2, computeTargetTask);
        int isAllowedToStart = isAllowedToStart(activityRecord, z2, computeTargetTask);
        if (isAllowedToStart != 0) {
            if (activityRecord.resultTo != null) {
                activityRecord.resultTo.sendResult(-1, activityRecord.resultWho, activityRecord.requestCode, 0, null, null, null);
            }
            return isAllowedToStart;
        }
        if (computeTargetTask != null) {
            if (computeTargetTask.getTreeWeight() > MAX_TASK_WEIGHT_FOR_ADDING_ACTIVITY) {
                android.util.Slog.e("ActivityTaskManager", "Remove " + computeTargetTask + " because it has contained too many activities or windows (abort starting " + activityRecord + " from uid=" + this.mCallingUid);
                computeTargetTask.removeImmediately("bulky-task");
                return 102;
            }
            if (!avoidMoveToFront() && ((this.mService.mHomeProcess == null || this.mService.mHomeProcess.mUid != i2) && topLeafTask != null && topLeafTask.isActivityTypeHomeOrRecents() && activityRecord.mTransitionController.isTransientHide(computeTargetTask))) {
                this.mCanMoveToFrontCode = 2;
            }
            if (com.android.window.flags.Flags.balDontBringExistingBackgroundTaskStackToFg() && !avoidMoveToFront() && balVerdict.onlyCreatorAllows()) {
                this.mCanMoveToFrontCode = 1;
            }
            this.mPriorAboveTask = com.android.server.wm.TaskDisplayArea.getRootTaskAbove(computeTargetTask.getRootTask());
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = z2 ? null : computeTargetTask.getTopNonFinishingActivity();
        if (topNonFinishingActivity != null) {
            if (3 == this.mLaunchMode && this.mSourceRecord != null && computeTargetTask == this.mSourceRecord.getTask() && (findActivity = this.mRootWindowContainer.findActivity(this.mIntent, this.mStartActivity.info, false)) != null && findActivity.getTask() != computeTargetTask) {
                findActivity.destroyIfPossible("Removes redundant singleInstance");
            }
            recordTransientLaunchIfNeeded(topNonFinishingActivity);
            int recycleTask = recycleTask(computeTargetTask, topNonFinishingActivity, reusableTask, neededUriGrants, balVerdict);
            if (recycleTask != 0) {
                return recycleTask;
            }
        } else {
            this.mAddingToTask = true;
        }
        com.android.server.wm.Task focusedRootTask2 = this.mPreferredTaskDisplayArea.getFocusedRootTask();
        if (focusedRootTask2 != null && (deliverToCurrentTopIfNeeded = deliverToCurrentTopIfNeeded(focusedRootTask2, neededUriGrants)) != 0) {
            return deliverToCurrentTopIfNeeded;
        }
        if (this.mTargetRootTask == null) {
            this.mTargetRootTask = getOrCreateRootTask(this.mStartActivity, this.mLaunchFlags, computeTargetTask, this.mOptions);
        }
        if (!z2) {
            if (this.mAddingToTask) {
                addOrReparentStartingActivity(computeTargetTask, "adding to task");
            }
        } else {
            if (!this.mLaunchTaskBehind || this.mSourceRecord == null) {
                task2 = null;
            } else {
                task2 = this.mSourceRecord.getTask();
            }
            setNewTask(task2);
        }
        recordTransientLaunchIfNeeded(this.mLastStartActivityRecord);
        if (this.mDoResume) {
            if (!avoidMoveToFront()) {
                this.mTargetRootTask.getRootTask().moveToFront("reuseOrNewTask", computeTargetTask);
                if (!this.mTargetRootTask.isTopRootTaskInDisplayArea() && this.mService.isDreaming() && !z) {
                    this.mLaunchTaskBehind = true;
                    activityRecord.mLaunchTaskBehind = true;
                }
            } else {
                logPIOnlyCreatorAllowsBAL();
            }
        }
        this.mService.mUgmInternal.grantUriPermissionUncheckedFromIntent(neededUriGrants, this.mStartActivity.getUriPermissionsLocked());
        if (this.mStartActivity.resultTo == null || this.mStartActivity.resultTo.info == null) {
            if (this.mStartActivity.mShareIdentity) {
                this.mService.getPackageManagerInternalLocked().grantImplicitAccess(this.mStartActivity.mUserId, this.mIntent, android.os.UserHandle.getAppId(this.mStartActivity.info.applicationInfo.uid), activityRecord.launchedFromUid, true);
            }
        } else {
            android.content.pm.PackageManagerInternal packageManagerInternalLocked = this.mService.getPackageManagerInternalLocked();
            packageManagerInternalLocked.grantImplicitAccess(this.mStartActivity.mUserId, this.mIntent, android.os.UserHandle.getAppId(this.mStartActivity.info.applicationInfo.uid), packageManagerInternalLocked.getPackageUid(this.mStartActivity.resultTo.info.packageName, 0L, this.mStartActivity.mUserId), true);
        }
        com.android.server.wm.Task task3 = this.mStartActivity.getTask();
        if (z2) {
            com.android.server.wm.EventLogTags.writeWmCreateTask(this.mStartActivity.mUserId, task3.mTaskId, task3.getRootTaskId(), task3.getDisplayId());
        }
        this.mStartActivity.logStartActivity(com.android.server.wm.EventLogTags.WM_CREATE_ACTIVITY, task3);
        this.mStartActivity.getTaskFragment().clearLastPausedActivity();
        this.mRootWindowContainer.startPowerModeLaunchIfNeeded(false, this.mStartActivity);
        this.mTargetRootTask.startActivityLocked(this.mStartActivity, focusedRootTask2, z2, task3 != topLeafTask, this.mOptions, activityRecord2);
        if (this.mDoResume) {
            com.android.server.wm.ActivityRecord activityRecord3 = task3.topRunningActivityLocked();
            if (!this.mTargetRootTask.isTopActivityFocusable() || (activityRecord3 != null && activityRecord3.isTaskOverlay() && this.mStartActivity != activityRecord3)) {
                this.mTargetRootTask.ensureActivitiesVisible(null);
                this.mTargetRootTask.mDisplayContent.executeAppTransition();
            } else {
                if (this.mTargetRootTask.isTopActivityFocusable() && !this.mRootWindowContainer.isTopDisplayFocusedRootTask(this.mTargetRootTask)) {
                    if (!avoidMoveToFront()) {
                        this.mTargetRootTask.moveToFront("startActivityInner");
                    } else {
                        logPIOnlyCreatorAllowsBAL();
                    }
                }
                this.mRootWindowContainer.resumeFocusedTasksTopActivities(this.mTargetRootTask, this.mStartActivity, this.mOptions, this.mTransientLaunch);
            }
        }
        this.mRootWindowContainer.updateUserRootTask(this.mStartActivity.mUserId, this.mTargetRootTask);
        this.mSupervisor.mRecentTasks.add(task3);
        this.mSupervisor.handleNonResizableTaskIfNeeded(task3, this.mPreferredWindowingMode, this.mPreferredTaskDisplayArea, this.mTargetRootTask);
        if (this.mOptions != null && this.mOptions.isLaunchIntoPip() && activityRecord2 != null && activityRecord2.getTask() == this.mStartActivity.getTask() && balVerdict.allows()) {
            this.mRootWindowContainer.moveActivityToPinnedRootTask(this.mStartActivity, activityRecord2, "launch-into-pip");
        }
        this.mSupervisor.getBackgroundActivityLaunchController().onNewActivityLaunched(this.mStartActivity);
        return 0;
    }

    private void logPIOnlyCreatorAllowsBAL() {
        if (avoidMoveToFrontPIOnlyCreatorAllows()) {
            java.lang.String nameForUid = this.mService.mContext.getPackageManager().getNameForUid(this.mRealCallingUid);
            if (nameForUid == null) {
                nameForUid = "uid=" + this.mRealCallingUid;
            }
            android.util.Slog.wtf("ActivityTaskManager", "Without Android 15 BAL hardening this activity would be moved to the foreground. The activity is started by a PendingIntent. However, only the creator of the PendingIntent allows BAL while the sender does not allow BAL. realCallingPackage: " + nameForUid + "; callingPackage: " + this.mRequest.callingPackage + "; mTargetRootTask:" + this.mTargetRootTask + "; mIntent: " + this.mIntent + "; mTargetRootTask.getTopNonFinishingActivity: " + this.mTargetRootTask.getTopNonFinishingActivity() + "; mTargetRootTask.getRootActivity: " + this.mTargetRootTask.getRootActivity());
        }
    }

    private void recordTransientLaunchIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || !this.mTransientLaunch) {
            return;
        }
        com.android.server.wm.TransitionController transitionController = activityRecord.mTransitionController;
        if (transitionController.isCollecting() && !transitionController.isTransientCollect(activityRecord)) {
            transitionController.setTransientLaunch(activityRecord, this.mPriorAboveTask);
        }
    }

    private com.android.server.wm.Task computeTargetTask() {
        if (this.mStartActivity.resultTo == null && this.mInTask == null && !this.mAddingToTask && (this.mLaunchFlags & 268435456) != 0) {
            return null;
        }
        if (this.mSourceRecord != null) {
            return this.mSourceRecord.getTask();
        }
        if (this.mInTask != null) {
            if (!this.mInTask.isAttached()) {
                getOrCreateRootTask(this.mStartActivity, this.mLaunchFlags, this.mInTask, this.mOptions);
            }
            return this.mInTask;
        }
        com.android.server.wm.Task orCreateRootTask = getOrCreateRootTask(this.mStartActivity, this.mLaunchFlags, null, this.mOptions);
        com.android.server.wm.ActivityRecord topNonFinishingActivity = orCreateRootTask.getTopNonFinishingActivity();
        if (topNonFinishingActivity != null) {
            return topNonFinishingActivity.getTask();
        }
        orCreateRootTask.removeIfPossible("computeTargetTask");
        return null;
    }

    private void computeLaunchParams(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, com.android.server.wm.Task task) {
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea;
        this.mSupervisor.getLaunchParamsController().calculate(task, activityRecord.info.windowLayout, activityRecord, activityRecord2, this.mOptions, this.mRequest, 3, this.mLaunchParams);
        if (this.mLaunchParams.hasPreferredTaskDisplayArea()) {
            defaultTaskDisplayArea = this.mLaunchParams.mPreferredTaskDisplayArea;
        } else {
            defaultTaskDisplayArea = this.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        this.mPreferredTaskDisplayArea = defaultTaskDisplayArea;
        this.mPreferredWindowingMode = this.mLaunchParams.mWindowingMode;
    }

    private com.android.server.wm.TaskDisplayArea computeSuggestedLaunchDisplayArea(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions) {
        this.mSupervisor.getLaunchParamsController().calculate(task, null, null, activityRecord, activityOptions, this.mRequest, 0, this.mLaunchParams);
        if (this.mLaunchParams.hasPreferredTaskDisplayArea()) {
            return this.mLaunchParams.mPreferredTaskDisplayArea;
        }
        return this.mRootWindowContainer.getDefaultTaskDisplayArea();
    }

    @com.android.internal.annotations.VisibleForTesting
    int isAllowedToStart(com.android.server.wm.ActivityRecord activityRecord, boolean z, com.android.server.wm.Task task) {
        com.android.server.wm.DisplayContent displayContentOrCreate;
        int i;
        if (activityRecord.packageName == null) {
            android.app.ActivityOptions.abort(this.mOptions);
            return -92;
        }
        if (activityRecord.isActivityTypeHome() && !this.mRootWindowContainer.canStartHomeOnDisplayArea(activityRecord.info, this.mPreferredTaskDisplayArea, true)) {
            android.util.Slog.w("ActivityTaskManager", "Cannot launch home on display area " + this.mPreferredTaskDisplayArea);
            return -96;
        }
        boolean z2 = z || !task.isUidPresent(this.mCallingUid) || (3 == this.mLaunchMode && task.inPinnedWindowingMode());
        if (this.mBalCode == 0 && z2 && handleBackgroundActivityAbort(activityRecord)) {
            android.util.Slog.e("ActivityTaskManager", "Abort background activity starts from " + this.mCallingUid);
            return 102;
        }
        boolean z3 = (this.mLaunchFlags & 268468224) == 268468224;
        if (!z) {
            if (this.mService.getLockTaskController().isLockTaskModeViolation(task, z3)) {
                android.util.Slog.e("ActivityTaskManager", "Attempted Lock Task Mode violation r=" + activityRecord);
                return 101;
            }
        } else if (this.mService.getLockTaskController().isNewTaskLockTaskModeViolation(activityRecord)) {
            android.util.Slog.e("ActivityTaskManager", "Attempted Lock Task Mode violation r=" + activityRecord);
            return 101;
        }
        if (this.mPreferredTaskDisplayArea != null && (displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(this.mPreferredTaskDisplayArea.getDisplayId())) != null) {
            int windowingMode = task != null ? task.getWindowingMode() : displayContentOrCreate.getWindowingMode();
            if (this.mSourceRecord != null) {
                i = this.mSourceRecord.getDisplayId();
            } else {
                i = 0;
            }
            if (!displayContentOrCreate.mDwpcHelper.canActivityBeLaunched(activityRecord.info, activityRecord.intent, windowingMode, i, z)) {
                android.util.Slog.w("ActivityTaskManager", "Abort to launch " + activityRecord.info.getComponentName() + " on display area " + this.mPreferredTaskDisplayArea);
                return 102;
            }
        }
        return !this.mSupervisor.getBackgroundActivityLaunchController().checkActivityAllowedToStart(this.mSourceRecord, activityRecord, z, avoidMoveToFront(), task, this.mLaunchFlags, this.mBalCode, this.mCallingUid, this.mRealCallingUid, this.mPreferredTaskDisplayArea) ? 102 : 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    @com.android.server.wm.TaskFragment.EmbeddingCheckResult
    static int canEmbedActivity(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.Task task) {
        com.android.server.wm.Task task2 = taskFragment.getTask();
        if (task2 == null || task != task2) {
            return 3;
        }
        return taskFragment.isAllowedToEmbedActivity(activityRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    int recycleTask(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Task task2, com.android.server.uri.NeededUriGrants neededUriGrants, com.android.server.wm.BackgroundActivityStartController.BalVerdict balVerdict) {
        if (task.mUserId != this.mStartActivity.mUserId) {
            this.mTargetRootTask = task.getRootTask();
            this.mAddingToTask = true;
            return 0;
        }
        if (task2 != null) {
            if (task.intent == null) {
                task.setIntent(this.mStartActivity);
            } else {
                if ((this.mStartActivity.intent.getFlags() & 16384) != 0) {
                    task.intent.addFlags(16384);
                } else {
                    task.intent.removeFlags(16384);
                }
            }
        }
        this.mRootWindowContainer.startPowerModeLaunchIfNeeded(false, activityRecord);
        setTargetRootTaskIfNeeded(activityRecord);
        if (this.mLastStartActivityRecord != null && (this.mLastStartActivityRecord.finishing || this.mLastStartActivityRecord.noDisplay)) {
            this.mLastStartActivityRecord = activityRecord;
        }
        if ((this.mStartFlags & 1) != 0) {
            if (!this.mMovedToFront && this.mDoResume) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2867366986304729L, 0, null, java.lang.String.valueOf(this.mTargetRootTask), java.lang.String.valueOf(activityRecord));
                this.mTargetRootTask.moveToFront("intentActivityFound");
            }
            resumeTargetRootTaskIfNeeded();
            return 1;
        }
        complyActivityFlags(task, task2 != null ? task2.getTopNonFinishingActivity() : null, neededUriGrants);
        if (this.mAddingToTask) {
            this.mSupervisor.getBackgroundActivityLaunchController().clearTopIfNeeded(task, this.mSourceRecord, this.mStartActivity, this.mCallingUid, this.mRealCallingUid, this.mLaunchFlags, this.mBalCode);
            return 0;
        }
        if (activityRecord.finishing) {
            activityRecord = task.getTopNonFinishingActivity();
        }
        if (this.mMovedToFront) {
            activityRecord.showStartingWindow(true);
        } else if (this.mDoResume) {
            this.mTargetRootTask.moveToFront("intentActivityFound");
        }
        resumeTargetRootTaskIfNeeded();
        if (this.mService.isDreaming() && activityRecord.canTurnScreenOn()) {
            activityRecord.mTaskSupervisor.wakeUp("recycleTask#turnScreenOnFlag");
        }
        this.mLastStartActivityRecord = activityRecord;
        return this.mMovedToFront ? 2 : 3;
    }

    private int deliverToCurrentTopIfNeeded(com.android.server.wm.Task task, com.android.server.uri.NeededUriGrants neededUriGrants) {
        com.android.server.wm.ActivityRecord activityRecord = task.topRunningNonDelayedActivityLocked(this.mNotTop);
        if (!(activityRecord != null && activityRecord.mActivityComponent.equals(this.mStartActivity.mActivityComponent) && activityRecord.mUserId == this.mStartActivity.mUserId && activityRecord.attachedToProcess() && ((this.mLaunchFlags & 536870912) != 0 || 1 == this.mLaunchMode) && (!activityRecord.isActivityTypeHome() || activityRecord.getDisplayArea() == this.mPreferredTaskDisplayArea))) {
            return 0;
        }
        activityRecord.getTaskFragment().clearLastPausedActivity();
        if (this.mDoResume) {
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
        android.app.ActivityOptions.abort(this.mOptions);
        if ((this.mStartFlags & 1) != 0) {
            return 1;
        }
        if (this.mStartActivity.resultTo != null) {
            this.mStartActivity.resultTo.sendResult(-1, this.mStartActivity.resultWho, this.mStartActivity.requestCode, 0, null, null, null);
            this.mStartActivity.resultTo = null;
        }
        deliverNewIntent(activityRecord, neededUriGrants);
        this.mSupervisor.handleNonResizableTaskIfNeeded(activityRecord.getTask(), this.mLaunchParams.mWindowingMode, this.mPreferredTaskDisplayArea, task);
        return 3;
    }

    private void complyActivityFlags(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, com.android.server.uri.NeededUriGrants neededUriGrants) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        boolean z = (activityRecord == null || (this.mLaunchFlags & 2097152) == 0) ? false : true;
        if (z) {
            topNonFinishingActivity = this.mTargetRootTask.resetTaskIfNeeded(topNonFinishingActivity, this.mStartActivity);
        }
        if ((this.mLaunchFlags & 268468224) == 268468224) {
            task.performClearTaskForReuse(true);
            task.setIntent(this.mStartActivity);
            this.mAddingToTask = true;
            this.mIsTaskCleared = true;
            return;
        }
        if ((this.mLaunchFlags & 67108864) != 0 || isDocumentLaunchesIntoExisting(this.mLaunchFlags) || isLaunchModeOneOf(3, 2, 4)) {
            int[] iArr = new int[1];
            com.android.server.wm.ActivityRecord performClearTop = task.performClearTop(this.mStartActivity, this.mLaunchFlags, iArr);
            if (performClearTop != null && !performClearTop.finishing) {
                if (iArr[0] > 0) {
                    this.mMovedToTopActivity = performClearTop;
                }
                if (performClearTop.isRootOfTask()) {
                    performClearTop.getTask().setIntent(this.mStartActivity);
                }
                deliverNewIntent(performClearTop, neededUriGrants);
                return;
            }
            this.mAddingToTask = true;
            if (performClearTop != null && performClearTop.getTaskFragment() != null && performClearTop.getTaskFragment().isEmbedded()) {
                this.mAddingToTaskFragment = performClearTop.getTaskFragment();
            }
            if (task.getRootTask() == null) {
                this.mTargetRootTask = getOrCreateRootTask(this.mStartActivity, this.mLaunchFlags, null, this.mOptions);
                this.mTargetRootTask.addChild(task, !this.mLaunchTaskBehind, (this.mStartActivity.info.flags & 1024) != 0);
                return;
            }
            return;
        }
        if ((67108864 & this.mLaunchFlags) == 0 && !this.mAddingToTask && (this.mLaunchFlags & 131072) != 0) {
            com.android.server.wm.ActivityRecord findActivityInHistory = task.findActivityInHistory(this.mStartActivity.mActivityComponent, this.mStartActivity.mUserId);
            if (findActivityInHistory != null) {
                if (findActivityInHistory.getTask().moveActivityToFront(findActivityInHistory)) {
                    this.mMovedToTopActivity = findActivityInHistory;
                    if (this.mNoAnimation) {
                        findActivityInHistory.mDisplayContent.prepareAppTransition(0);
                    } else {
                        findActivityInHistory.mDisplayContent.prepareAppTransition(3);
                    }
                }
                findActivityInHistory.updateOptionsLocked(this.mOptions);
                deliverNewIntent(findActivityInHistory, neededUriGrants);
                findActivityInHistory.getTaskFragment().clearLastPausedActivity();
                return;
            }
            this.mAddingToTask = true;
            return;
        }
        if (this.mStartActivity.mActivityComponent.equals(task.realActivity)) {
            if (task != this.mInTask) {
                if (((this.mLaunchFlags & 536870912) != 0 || 1 == this.mLaunchMode) && topNonFinishingActivity.mActivityComponent.equals(this.mStartActivity.mActivityComponent) && this.mStartActivity.resultTo == null) {
                    if (topNonFinishingActivity.isRootOfTask()) {
                        topNonFinishingActivity.getTask().setIntent(this.mStartActivity);
                    }
                    deliverNewIntent(topNonFinishingActivity, neededUriGrants);
                    return;
                } else if (!task.isSameIntentFilter(this.mStartActivity)) {
                    this.mAddingToTask = true;
                    return;
                } else {
                    if (activityRecord == null) {
                        this.mAddingToTask = true;
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (!z) {
            this.mAddingToTask = true;
        } else if (!task.rootWasReset) {
            task.setIntent(this.mStartActivity);
        }
    }

    void reset(boolean z) {
        this.mStartActivity = null;
        this.mIntent = null;
        this.mCallingUid = -1;
        this.mRealCallingUid = -1;
        this.mOptions = null;
        this.mBalCode = 1;
        this.mLaunchTaskBehind = false;
        this.mLaunchFlags = 0;
        this.mLaunchMode = -1;
        this.mLaunchParams.reset();
        this.mNotTop = null;
        this.mDoResume = false;
        this.mStartFlags = 0;
        this.mSourceRecord = null;
        this.mPreferredTaskDisplayArea = null;
        this.mPreferredWindowingMode = 0;
        this.mInTask = null;
        this.mInTaskFragment = null;
        this.mAddingToTaskFragment = null;
        this.mAddingToTask = false;
        this.mSourceRootTask = null;
        this.mTargetRootTask = null;
        this.mTargetTask = null;
        this.mIsTaskCleared = false;
        this.mMovedToFront = false;
        this.mNoAnimation = false;
        this.mCanMoveToFrontCode = 0;
        this.mFrozeTaskList = false;
        this.mTransientLaunch = false;
        this.mPriorAboveTask = null;
        this.mDisplayLockAndOccluded = false;
        this.mVoiceSession = null;
        this.mVoiceInteractor = null;
        this.mIntentDelivered = false;
        if (z) {
            this.mRequest.reset();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0237  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setInitialState(com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions, com.android.server.wm.Task task, com.android.server.wm.TaskFragment taskFragment, int i, com.android.server.wm.ActivityRecord activityRecord2, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i2, int i3) {
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea;
        com.android.server.wm.TaskFragment taskFragment2;
        com.android.server.wm.Task topDisplayFocusedRootTask;
        com.android.server.wm.ActivityRecord activityRecord3 = activityRecord2;
        reset(false);
        this.mStartActivity = activityRecord;
        this.mIntent = activityRecord.intent;
        this.mOptions = activityOptions;
        this.mCallingUid = activityRecord.launchedFromUid;
        this.mRealCallingUid = i3;
        this.mSourceRecord = activityRecord3;
        this.mSourceRootTask = this.mSourceRecord != null ? this.mSourceRecord.getRootTask() : null;
        this.mVoiceSession = iVoiceInteractionSession;
        this.mVoiceInteractor = iVoiceInteractor;
        this.mBalCode = i2;
        this.mLaunchParams.reset();
        this.mSupervisor.getLaunchParamsController().calculate(task, activityRecord.info.windowLayout, activityRecord, activityRecord2, activityOptions, this.mRequest, 0, this.mLaunchParams);
        if (this.mLaunchParams.hasPreferredTaskDisplayArea()) {
            defaultTaskDisplayArea = this.mLaunchParams.mPreferredTaskDisplayArea;
        } else {
            defaultTaskDisplayArea = this.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        this.mPreferredTaskDisplayArea = defaultTaskDisplayArea;
        this.mPreferredWindowingMode = this.mLaunchParams.mWindowingMode;
        this.mLaunchMode = activityRecord.launchMode;
        this.mLaunchFlags = adjustLaunchFlagsToDocumentMode(activityRecord, 3 == this.mLaunchMode, 2 == this.mLaunchMode, this.mIntent.getFlags());
        this.mLaunchTaskBehind = (!activityRecord.mLaunchTaskBehind || isLaunchModeOneOf(2, 3) || (this.mLaunchFlags & 524288) == 0) ? false : true;
        if (this.mLaunchMode == 4) {
            this.mLaunchFlags |= 268435456;
        }
        if (activityRecord.info.requiredDisplayCategory != null && this.mSourceRecord != null && !activityRecord.info.requiredDisplayCategory.equals(this.mSourceRecord.info.requiredDisplayCategory)) {
            this.mLaunchFlags |= 268435456;
        }
        sendNewTaskResultRequestIfNeeded();
        if ((this.mLaunchFlags & 524288) != 0 && activityRecord.resultTo == null) {
            this.mLaunchFlags |= 268435456;
        }
        if ((this.mLaunchFlags & 268435456) != 0 && (this.mLaunchTaskBehind || activityRecord.info.documentLaunchMode == 2)) {
            this.mLaunchFlags |= 134217728;
        }
        this.mSupervisor.mUserLeaving = (this.mLaunchFlags & 262144) == 0;
        if (!activityRecord.showToCurrentUser() || this.mLaunchTaskBehind) {
            activityRecord.delayedResume = true;
            this.mDoResume = false;
        } else {
            this.mDoResume = true;
        }
        if (this.mOptions != null) {
            if (this.mOptions.getLaunchTaskId() != -1 && this.mOptions.getTaskOverlay()) {
                activityRecord.setTaskOverlay(true);
                if (!this.mOptions.canTaskOverlayResume()) {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(this.mOptions.getLaunchTaskId());
                    com.android.server.wm.ActivityRecord topNonFinishingActivity = anyTaskForId != null ? anyTaskForId.getTopNonFinishingActivity() : null;
                    if (topNonFinishingActivity != null && !topNonFinishingActivity.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                        this.mDoResume = false;
                        this.mCanMoveToFrontCode = 2;
                    }
                }
            } else if (this.mOptions.getAvoidMoveToFront()) {
                this.mDoResume = false;
                this.mCanMoveToFrontCode = 2;
            }
            this.mTransientLaunch = this.mOptions.getTransientLaunch();
            this.mDisplayLockAndOccluded = this.mSupervisor.getKeyguardController().isKeyguardOccluded(this.mPreferredTaskDisplayArea.getDisplayId());
            if (this.mTransientLaunch && this.mDisplayLockAndOccluded && this.mService.getTransitionController().isShellTransitionsEnabled()) {
                this.mDoResume = false;
                this.mCanMoveToFrontCode = 2;
            }
            this.mTargetRootTask = com.android.server.wm.Task.fromWindowContainerToken(this.mOptions.getLaunchRootTask());
            if (taskFragment == null) {
                taskFragment2 = com.android.server.wm.TaskFragment.fromTaskFragmentToken(this.mOptions.getLaunchTaskFragmentToken(), this.mService);
                if (taskFragment2 != null && taskFragment2.isEmbeddedTaskFragmentInPip()) {
                    android.util.Slog.w("ActivityTaskManager", "Can not start activity in TaskFragment in PIP: " + taskFragment2);
                    taskFragment2 = null;
                }
                this.mNotTop = (this.mLaunchFlags & 16777216) == 0 ? activityRecord3 : null;
                this.mInTask = task;
                if (task != null && !task.inRecents) {
                    android.util.Slog.w("ActivityTaskManager", "Starting activity in task not in recents: " + task);
                    this.mInTask = null;
                }
                if (this.mInTask != null && !this.mInTask.isSameRequiredDisplayCategory(activityRecord.info)) {
                    android.util.Slog.w("ActivityTaskManager", "Starting activity in task with different display category: " + this.mInTask);
                    this.mInTask = null;
                }
                this.mInTaskFragment = taskFragment2;
                this.mStartFlags = i;
                if ((i & 1) != 0) {
                    if (activityRecord3 == null && (topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask()) != null) {
                        activityRecord3 = topDisplayFocusedRootTask.topRunningNonDelayedActivityLocked(this.mNotTop);
                    }
                    if (activityRecord3 == null || !activityRecord3.mActivityComponent.equals(activityRecord.mActivityComponent)) {
                        this.mStartFlags &= -2;
                    }
                }
                this.mNoAnimation = (this.mLaunchFlags & 65536) != 0;
                if (this.mBalCode != 0 && !this.mService.isBackgroundActivityStartsEnabled()) {
                    this.mCanMoveToFrontCode = 2;
                    this.mDoResume = false;
                    return;
                }
            }
        }
        taskFragment2 = taskFragment;
        this.mNotTop = (this.mLaunchFlags & 16777216) == 0 ? activityRecord3 : null;
        this.mInTask = task;
        if (task != null) {
            android.util.Slog.w("ActivityTaskManager", "Starting activity in task not in recents: " + task);
            this.mInTask = null;
        }
        if (this.mInTask != null) {
            android.util.Slog.w("ActivityTaskManager", "Starting activity in task with different display category: " + this.mInTask);
            this.mInTask = null;
        }
        this.mInTaskFragment = taskFragment2;
        this.mStartFlags = i;
        if ((i & 1) != 0) {
        }
        this.mNoAnimation = (this.mLaunchFlags & 65536) != 0;
        if (this.mBalCode != 0) {
        }
    }

    private void sendNewTaskResultRequestIfNeeded() {
        if (this.mStartActivity.resultTo != null && (this.mLaunchFlags & 268435456) != 0) {
            android.util.Slog.w("ActivityTaskManager", "Activity is launching as a new task, so cancelling activity result.");
            this.mStartActivity.resultTo.sendResult(-1, this.mStartActivity.resultWho, this.mStartActivity.requestCode, 0, null, null, null);
            this.mStartActivity.resultTo = null;
        }
    }

    private void computeLaunchingTaskFlags() {
        if (this.mSourceRecord == null && this.mInTask != null && this.mInTask.getRootTask() != null) {
            android.content.Intent baseIntent = this.mInTask.getBaseIntent();
            com.android.server.wm.ActivityRecord rootActivity = this.mInTask.getRootActivity();
            if (baseIntent == null) {
                android.app.ActivityOptions.abort(this.mOptions);
                throw new java.lang.IllegalArgumentException("Launching into task without base intent: " + this.mInTask);
            }
            if (isLaunchModeOneOf(3, 2)) {
                if (!baseIntent.getComponent().equals(this.mStartActivity.intent.getComponent())) {
                    android.app.ActivityOptions.abort(this.mOptions);
                    throw new java.lang.IllegalArgumentException("Trying to launch singleInstance/Task " + this.mStartActivity + " into different task " + this.mInTask);
                }
                if (rootActivity != null) {
                    android.app.ActivityOptions.abort(this.mOptions);
                    throw new java.lang.IllegalArgumentException("Caller with mInTask " + this.mInTask + " has root " + rootActivity + " but target is singleInstance/Task");
                }
            }
            if (rootActivity == null) {
                this.mLaunchFlags = (baseIntent.getFlags() & 403185664) | (this.mLaunchFlags & (-403185665));
                this.mIntent.setFlags(this.mLaunchFlags);
                this.mInTask.setIntent(this.mStartActivity);
                this.mAddingToTask = true;
            } else if ((this.mLaunchFlags & 268435456) != 0) {
                this.mAddingToTask = false;
            } else {
                this.mAddingToTask = true;
            }
        } else {
            this.mInTask = null;
            if ((this.mStartActivity.isResolverOrDelegateActivity() || this.mStartActivity.noDisplay) && this.mSourceRecord != null && this.mSourceRecord.inFreeformWindowingMode()) {
                this.mAddingToTask = true;
            }
        }
        if (this.mInTask == null) {
            if (this.mSourceRecord == null) {
                if ((this.mLaunchFlags & 268435456) == 0 && this.mInTask == null) {
                    android.util.Slog.w("ActivityTaskManager", "startActivity called from non-Activity context; forcing Intent.FLAG_ACTIVITY_NEW_TASK for: " + this.mIntent);
                    this.mLaunchFlags = this.mLaunchFlags | 268435456;
                }
            } else if (this.mSourceRecord.launchMode == 3) {
                this.mLaunchFlags |= 268435456;
            } else if (isLaunchModeOneOf(3, 2)) {
                this.mLaunchFlags |= 268435456;
            }
        }
        if ((this.mLaunchFlags & 4096) != 0) {
            if ((this.mLaunchFlags & 268435456) == 0 || this.mSourceRecord == null) {
                this.mLaunchFlags &= -4097;
            }
        }
    }

    private com.android.server.wm.Task getReusableTask() {
        com.android.server.wm.ActivityRecord activityRecord;
        if (this.mOptions != null && this.mOptions.getLaunchTaskId() != -1) {
            com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(this.mOptions.getLaunchTaskId());
            if (anyTaskForId != null) {
                return anyTaskForId;
            }
            return null;
        }
        if (!((((this.mLaunchFlags & 268435456) != 0 && (this.mLaunchFlags & 134217728) == 0) || isLaunchModeOneOf(3, 2)) & (this.mInTask == null && this.mStartActivity.resultTo == null))) {
            activityRecord = null;
        } else if (3 == this.mLaunchMode) {
            activityRecord = this.mRootWindowContainer.findActivity(this.mIntent, this.mStartActivity.info, this.mStartActivity.isActivityTypeHome());
        } else if ((this.mLaunchFlags & 4096) != 0) {
            activityRecord = this.mRootWindowContainer.findActivity(this.mIntent, this.mStartActivity.info, 2 != this.mLaunchMode);
        } else {
            activityRecord = this.mRootWindowContainer.findTask(this.mStartActivity, this.mPreferredTaskDisplayArea);
        }
        if (activityRecord != null && this.mLaunchMode == 4 && !activityRecord.getTask().getRootActivity().mActivityComponent.equals(this.mStartActivity.mActivityComponent)) {
            activityRecord = null;
        }
        if (activityRecord != null && ((this.mStartActivity.isActivityTypeHome() || activityRecord.isActivityTypeHome()) && activityRecord.getDisplayArea() != this.mPreferredTaskDisplayArea)) {
            activityRecord = null;
        }
        if (activityRecord != null) {
            return activityRecord.getTask();
        }
        return null;
    }

    private void setTargetRootTaskIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        boolean z;
        activityRecord.getTaskFragment().clearLastPausedActivity();
        com.android.server.wm.Task task = activityRecord.getTask();
        com.android.server.wm.Task rootTask = task != null ? task.getRootTask() : null;
        if (this.mTargetRootTask == null) {
            if (this.mSourceRecord != null && this.mSourceRecord.mLaunchRootTask != null) {
                this.mTargetRootTask = com.android.server.wm.Task.fromWindowContainerToken(this.mSourceRecord.mLaunchRootTask);
            } else {
                this.mTargetRootTask = getOrCreateRootTask(this.mStartActivity, this.mLaunchFlags, task, this.mOptions);
            }
        }
        if (this.mTargetRootTask.getDisplayArea() == this.mPreferredTaskDisplayArea) {
            com.android.server.wm.Task focusedRootTask = this.mTargetRootTask.mDisplayContent.getFocusedRootTask();
            com.android.server.wm.ActivityRecord activityRecord2 = focusedRootTask == null ? null : focusedRootTask.topRunningNonDelayedActivityLocked(this.mNotTop);
            com.android.server.wm.Task task2 = activityRecord2 != null ? activityRecord2.getTask() : null;
            z = (task2 == task && (focusedRootTask == null || task2 == focusedRootTask.getTopMostTask()) && (focusedRootTask == null || focusedRootTask == rootTask)) ? false : true;
        } else {
            z = true;
        }
        if (z && !avoidMoveToFront()) {
            this.mStartActivity.intent.addFlags(4194304);
            if (this.mLaunchTaskBehind && this.mSourceRecord != null) {
                activityRecord.setTaskToAffiliateWith(this.mSourceRecord.getTask());
            }
            if (activityRecord.isDescendantOf(this.mTargetRootTask)) {
                if (this.mTargetRootTask != task && this.mTargetRootTask != task.getParent().asTask()) {
                    task.getParent().positionChildAt(Integer.MAX_VALUE, task, false);
                    task = task.getParent().asTaskFragment().getTask();
                }
                boolean z2 = activityRecord.isVisibleRequested() && activityRecord.inMultiWindowMode() && activityRecord == this.mTargetRootTask.topRunningActivity() && !activityRecord.mTransitionController.isTransientHide(this.mTargetRootTask);
                this.mTargetRootTask.moveTaskToFront(task, this.mNoAnimation, this.mOptions, this.mStartActivity.appTimeTracker, true, "bringingFoundTaskToFront");
                this.mMovedToFront = !z2;
            } else if (activityRecord.getWindowingMode() != 2) {
                task.reparent(this.mTargetRootTask, true, 0, true, true, "reparentToTargetRootTask");
                this.mMovedToFront = true;
            }
            this.mOptions = null;
        }
        if (z) {
            logPIOnlyCreatorAllowsBAL();
        }
        if (this.mStartActivity.mLaunchCookie != null) {
            activityRecord.mLaunchCookie = this.mStartActivity.mLaunchCookie;
        }
        if (this.mStartActivity.mPendingRemoteAnimation != null) {
            activityRecord.mPendingRemoteAnimation = this.mStartActivity.mPendingRemoteAnimation;
        }
        this.mTargetRootTask = activityRecord.getRootTask();
        this.mSupervisor.handleNonResizableTaskIfNeeded(task, 0, this.mRootWindowContainer.getDefaultTaskDisplayArea(), this.mTargetRootTask);
    }

    private void resumeTargetRootTaskIfNeeded() {
        if (this.mDoResume) {
            com.android.server.wm.ActivityRecord activityRecord = this.mTargetRootTask.topRunningActivity(true);
            if (activityRecord != null) {
                activityRecord.setCurrentLaunchCanTurnScreenOn(true);
            }
            if (this.mTargetRootTask.isFocusable()) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities(this.mTargetRootTask, null, this.mOptions, this.mTransientLaunch);
            } else {
                this.mRootWindowContainer.ensureActivitiesVisible();
            }
        } else {
            android.app.ActivityOptions.abort(this.mOptions);
        }
        this.mRootWindowContainer.updateUserRootTask(this.mStartActivity.mUserId, this.mTargetRootTask);
    }

    private void setNewTask(com.android.server.wm.Task task) {
        com.android.server.wm.Task reuseOrCreateTask = this.mTargetRootTask.reuseOrCreateTask(this.mStartActivity.info, this.mIntent, this.mVoiceSession, this.mVoiceInteractor, (this.mLaunchTaskBehind || avoidMoveToFront()) ? false : true, this.mStartActivity, this.mSourceRecord, this.mOptions);
        reuseOrCreateTask.mTransitionController.collectExistenceChange(reuseOrCreateTask);
        addOrReparentStartingActivity(reuseOrCreateTask, "setTaskFromReuseOrCreateNewTask");
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2190454940975874759L, 0, null, java.lang.String.valueOf(this.mStartActivity), java.lang.String.valueOf(this.mStartActivity.getTask()));
        if (task != null) {
            this.mStartActivity.setTaskToAffiliateWith(task);
        }
    }

    private void deliverNewIntent(com.android.server.wm.ActivityRecord activityRecord, com.android.server.uri.NeededUriGrants neededUriGrants) {
        if (this.mIntentDelivered) {
            return;
        }
        activityRecord.logStartActivity(com.android.server.wm.EventLogTags.WM_NEW_INTENT, activityRecord.getTask());
        activityRecord.deliverNewIntentLocked(this.mCallingUid, this.mStartActivity.intent, neededUriGrants, this.mStartActivity.launchedFromPackage, this.mStartActivity.mShareIdentity, this.mStartActivity.mUserId, android.os.UserHandle.getAppId(this.mStartActivity.info.applicationInfo.uid));
        this.mIntentDelivered = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.server.wm.ActivityRecord] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.server.wm.Task, com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.wm.TaskFragment] */
    private void addOrReparentStartingActivity(@android.annotation.NonNull com.android.server.wm.Task task, java.lang.String str) {
        if (this.mInTaskFragment != null) {
            int canEmbedActivity = canEmbedActivity(this.mInTaskFragment, this.mStartActivity, task);
            if (canEmbedActivity == 0) {
                task = this.mInTaskFragment;
                this.mStartActivity.mRequestedLaunchingTaskFragmentToken = this.mInTaskFragment.getFragmentToken();
            } else {
                sendCanNotEmbedActivityError(this.mInTaskFragment, canEmbedActivity);
            }
        } else {
            com.android.server.wm.TaskFragment taskFragment = this.mAddingToTaskFragment != null ? this.mAddingToTaskFragment : null;
            if (taskFragment == null) {
                final com.android.server.wm.TaskFragment taskFragment2 = this.mSourceRecord != null ? this.mSourceRecord.getTaskFragment() : null;
                com.android.server.wm.ActivityRecord activity = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityStarter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$addOrReparentStartingActivity$0;
                        lambda$addOrReparentStartingActivity$0 = com.android.server.wm.ActivityStarter.lambda$addOrReparentStartingActivity$0(com.android.server.wm.TaskFragment.this, (com.android.server.wm.ActivityRecord) obj);
                        return lambda$addOrReparentStartingActivity$0;
                    }
                });
                if (activity != null) {
                    taskFragment = activity.getTaskFragment();
                }
            }
            if (taskFragment != null && taskFragment.isEmbedded() && canEmbedActivity(taskFragment, this.mStartActivity, task) == 0) {
                task = taskFragment;
            }
        }
        if (this.mStartActivity.getTaskFragment() == null || this.mStartActivity.getTaskFragment() == task) {
            task.addChild(this.mStartActivity, Integer.MAX_VALUE);
        } else {
            this.mStartActivity.reparent(task, task.getChildCount(), str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$addOrReparentStartingActivity$0(com.android.server.wm.TaskFragment taskFragment, com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.canBeTopRunning()) {
            return false;
        }
        com.android.server.wm.TaskFragment taskFragment2 = activityRecord.getTaskFragment();
        return !taskFragment2.isIsolatedNav() || (taskFragment != null && taskFragment == taskFragment2);
    }

    private void sendCanNotEmbedActivityError(com.android.server.wm.TaskFragment taskFragment, @com.android.server.wm.TaskFragment.EmbeddingCheckResult int i) {
        java.lang.String str;
        switch (i) {
            case 1:
                str = "The app:" + this.mCallingUid + "is not trusted to " + this.mStartActivity;
                break;
            case 2:
                str = "Cannot embed " + this.mStartActivity + ". TaskFragment's bounds:" + taskFragment.getBounds() + ", minimum dimensions:" + this.mStartActivity.getMinDimensions();
                break;
            case 3:
                str = "Cannot embed " + this.mStartActivity + " that launched on another task,mLaunchMode=" + android.content.pm.ActivityInfo.launchModeToString(this.mLaunchMode) + ",mLaunchFlag=" + java.lang.Integer.toHexString(this.mLaunchFlags);
                break;
            default:
                str = "Unhandled embed result:" + i;
                break;
        }
        if (taskFragment.isOrganized()) {
            this.mService.mWindowOrganizerController.sendTaskFragmentOperationFailure(taskFragment.getTaskFragmentOrganizer(), this.mRequest.errorCallbackToken, taskFragment, 2, new java.lang.SecurityException(str));
        } else {
            android.util.Slog.w("ActivityTaskManager", str);
        }
    }

    private int adjustLaunchFlagsToDocumentMode(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, int i) {
        int i2 = i & 524288;
        if (i2 != 0 && (z || z2)) {
            android.util.Slog.i("ActivityTaskManager", "Ignoring FLAG_ACTIVITY_NEW_DOCUMENT, launchMode is \"singleInstance\" or \"singleTask\"");
            return i & (-134742017);
        }
        switch (activityRecord.info.documentLaunchMode) {
            case 0:
            default:
                return i;
            case 1:
                return i | 524288;
            case 2:
                return i | 524288;
            case 3:
                if (this.mLaunchMode == 4) {
                    if (i2 != 0) {
                        return i & (-134742017);
                    }
                    return i;
                }
                return i & (-134742017);
        }
    }

    private com.android.server.wm.Task getOrCreateRootTask(com.android.server.wm.ActivityRecord activityRecord, int i, com.android.server.wm.Task task, android.app.ActivityOptions activityOptions) {
        return this.mRootWindowContainer.getOrCreateRootTask(activityRecord, activityOptions, task, this.mSourceRecord != null ? this.mSourceRecord.getTask() : null, (activityOptions == null || !activityOptions.getAvoidMoveToFront()) && !this.mLaunchTaskBehind, this.mLaunchParams, i);
    }

    private boolean isLaunchModeOneOf(int i, int i2) {
        return i == this.mLaunchMode || i2 == this.mLaunchMode;
    }

    private boolean isLaunchModeOneOf(int i, int i2, int i3) {
        return i == this.mLaunchMode || i2 == this.mLaunchMode || i3 == this.mLaunchMode;
    }

    static boolean isDocumentLaunchesIntoExisting(int i) {
        return (524288 & i) != 0 && (i & 134217728) == 0;
    }

    com.android.server.wm.ActivityStarter setIntent(android.content.Intent intent) {
        this.mRequest.intent = intent;
        return this;
    }

    android.content.Intent getIntent() {
        return this.mRequest.intent;
    }

    com.android.server.wm.ActivityStarter setIntentGrants(com.android.server.uri.NeededUriGrants neededUriGrants) {
        this.mRequest.intentGrants = neededUriGrants;
        return this;
    }

    com.android.server.wm.ActivityStarter setReason(java.lang.String str) {
        this.mRequest.reason = str;
        return this;
    }

    com.android.server.wm.ActivityStarter setCaller(android.app.IApplicationThread iApplicationThread) {
        this.mRequest.caller = iApplicationThread;
        return this;
    }

    com.android.server.wm.ActivityStarter setResolvedType(java.lang.String str) {
        this.mRequest.resolvedType = str;
        return this;
    }

    com.android.server.wm.ActivityStarter setActivityInfo(android.content.pm.ActivityInfo activityInfo) {
        this.mRequest.activityInfo = activityInfo;
        return this;
    }

    com.android.server.wm.ActivityStarter setResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        this.mRequest.resolveInfo = resolveInfo;
        return this;
    }

    com.android.server.wm.ActivityStarter setVoiceSession(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) {
        this.mRequest.voiceSession = iVoiceInteractionSession;
        return this;
    }

    com.android.server.wm.ActivityStarter setVoiceInteractor(com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        this.mRequest.voiceInteractor = iVoiceInteractor;
        return this;
    }

    com.android.server.wm.ActivityStarter setResultTo(android.os.IBinder iBinder) {
        this.mRequest.resultTo = iBinder;
        return this;
    }

    com.android.server.wm.ActivityStarter setResultWho(java.lang.String str) {
        this.mRequest.resultWho = str;
        return this;
    }

    com.android.server.wm.ActivityStarter setRequestCode(int i) {
        this.mRequest.requestCode = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setCallingPid(int i) {
        this.mRequest.callingPid = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setCallingUid(int i) {
        this.mRequest.callingUid = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setCallingPackage(java.lang.String str) {
        this.mRequest.callingPackage = str;
        return this;
    }

    com.android.server.wm.ActivityStarter setCallingFeatureId(java.lang.String str) {
        this.mRequest.callingFeatureId = str;
        return this;
    }

    com.android.server.wm.ActivityStarter setRealCallingPid(int i) {
        this.mRequest.realCallingPid = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setRealCallingUid(int i) {
        this.mRequest.realCallingUid = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setStartFlags(int i) {
        this.mRequest.startFlags = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setActivityOptions(com.android.server.wm.SafeActivityOptions safeActivityOptions) {
        this.mRequest.activityOptions = safeActivityOptions;
        return this;
    }

    com.android.server.wm.ActivityStarter setActivityOptions(android.os.Bundle bundle) {
        return setActivityOptions(com.android.server.wm.SafeActivityOptions.fromBundle(bundle));
    }

    com.android.server.wm.ActivityStarter setIgnoreTargetSecurity(boolean z) {
        this.mRequest.ignoreTargetSecurity = z;
        return this;
    }

    com.android.server.wm.ActivityStarter setFilterCallingUid(int i) {
        this.mRequest.filterCallingUid = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setComponentSpecified(boolean z) {
        this.mRequest.componentSpecified = z;
        return this;
    }

    com.android.server.wm.ActivityStarter setOutActivity(com.android.server.wm.ActivityRecord[] activityRecordArr) {
        this.mRequest.outActivity = activityRecordArr;
        return this;
    }

    com.android.server.wm.ActivityStarter setInTask(com.android.server.wm.Task task) {
        this.mRequest.inTask = task;
        return this;
    }

    com.android.server.wm.ActivityStarter setInTaskFragment(com.android.server.wm.TaskFragment taskFragment) {
        this.mRequest.inTaskFragment = taskFragment;
        return this;
    }

    com.android.server.wm.ActivityStarter setWaitResult(android.app.WaitResult waitResult) {
        this.mRequest.waitResult = waitResult;
        return this;
    }

    com.android.server.wm.ActivityStarter setProfilerInfo(android.app.ProfilerInfo profilerInfo) {
        this.mRequest.profilerInfo = profilerInfo;
        return this;
    }

    com.android.server.wm.ActivityStarter setGlobalConfiguration(android.content.res.Configuration configuration) {
        this.mRequest.globalConfig = configuration;
        return this;
    }

    com.android.server.wm.ActivityStarter setUserId(int i) {
        this.mRequest.userId = i;
        return this;
    }

    com.android.server.wm.ActivityStarter setAllowPendingRemoteAnimationRegistryLookup(boolean z) {
        this.mRequest.allowPendingRemoteAnimationRegistryLookup = z;
        return this;
    }

    com.android.server.wm.ActivityStarter setOriginatingPendingIntent(com.android.server.am.PendingIntentRecord pendingIntentRecord) {
        this.mRequest.originatingPendingIntent = pendingIntentRecord;
        return this;
    }

    com.android.server.wm.ActivityStarter setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        this.mRequest.forcedBalByPiSender = backgroundStartPrivileges;
        return this;
    }

    com.android.server.wm.ActivityStarter setFreezeScreen(boolean z) {
        this.mRequest.freezeScreen = z;
        return this;
    }

    com.android.server.wm.ActivityStarter setErrorCallbackToken(@android.annotation.Nullable android.os.IBinder iBinder) {
        this.mRequest.errorCallbackToken = iBinder;
        return this;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mCurrentUser=");
        printWriter.println(this.mRootWindowContainer.mCurrentUser);
        printWriter.print(str);
        printWriter.print("mLastStartReason=");
        printWriter.println(this.mLastStartReason);
        printWriter.print(str);
        printWriter.print("mLastStartActivityTimeMs=");
        printWriter.println(java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(this.mLastStartActivityTimeMs)));
        printWriter.print(str);
        printWriter.print("mLastStartActivityResult=");
        printWriter.println(this.mLastStartActivityResult);
        if (this.mLastStartActivityRecord != null) {
            printWriter.print(str);
            printWriter.println("mLastStartActivityRecord:");
            this.mLastStartActivityRecord.dump(printWriter, str + "  ", true);
        }
        if (this.mStartActivity != null) {
            printWriter.print(str);
            printWriter.println("mStartActivity:");
            this.mStartActivity.dump(printWriter, str + "  ", true);
        }
        if (this.mIntent != null) {
            printWriter.print(str);
            printWriter.print("mIntent=");
            printWriter.println(this.mIntent);
        }
        if (this.mOptions != null) {
            printWriter.print(str);
            printWriter.print("mOptions=");
            printWriter.println(this.mOptions);
        }
        printWriter.print(str);
        printWriter.print("mLaunchMode=");
        printWriter.print(android.content.pm.ActivityInfo.launchModeToString(this.mLaunchMode));
        printWriter.print(str);
        printWriter.print("mLaunchFlags=0x");
        printWriter.print(java.lang.Integer.toHexString(this.mLaunchFlags));
        printWriter.print(" mDoResume=");
        printWriter.print(this.mDoResume);
        printWriter.print(" mAddingToTask=");
        printWriter.print(this.mAddingToTask);
        printWriter.print(" mInTaskFragment=");
        printWriter.println(this.mInTaskFragment);
    }
}
