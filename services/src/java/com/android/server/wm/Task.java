package com.android.server.wm;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class Task extends com.android.server.wm.TaskFragment {
    private static final java.lang.String ATTR_AFFINITY = "affinity";
    private static final java.lang.String ATTR_AUTOREMOVERECENTS = "auto_remove_recents";
    private static final java.lang.String ATTR_CALLING_FEATURE_ID = "calling_feature_id";
    private static final java.lang.String ATTR_CALLING_PACKAGE = "calling_package";
    private static final java.lang.String ATTR_CALLING_UID = "calling_uid";
    private static final java.lang.String ATTR_EFFECTIVE_UID = "effective_uid";
    private static final java.lang.String ATTR_LASTDESCRIPTION = "last_description";
    private static final java.lang.String ATTR_LASTTIMEMOVED = "last_time_moved";
    private static final java.lang.String ATTR_LAST_SNAPSHOT_BUFFER_SIZE = "last_snapshot_buffer_size";
    private static final java.lang.String ATTR_LAST_SNAPSHOT_CONTENT_INSETS = "last_snapshot_content_insets";
    private static final java.lang.String ATTR_LAST_SNAPSHOT_TASK_SIZE = "last_snapshot_task_size";
    private static final java.lang.String ATTR_MIN_HEIGHT = "min_height";
    private static final java.lang.String ATTR_MIN_WIDTH = "min_width";
    private static final java.lang.String ATTR_NEVERRELINQUISH = "never_relinquish_identity";
    private static final java.lang.String ATTR_NEXT_AFFILIATION = "next_affiliation";
    private static final java.lang.String ATTR_NON_FULLSCREEN_BOUNDS = "non_fullscreen_bounds";
    private static final java.lang.String ATTR_ORIGACTIVITY = "orig_activity";
    private static final java.lang.String ATTR_PERSIST_TASK_VERSION = "persist_task_version";
    private static final java.lang.String ATTR_PREV_AFFILIATION = "prev_affiliation";
    private static final java.lang.String ATTR_REALACTIVITY = "real_activity";
    private static final java.lang.String ATTR_REALACTIVITY_SUSPENDED = "real_activity_suspended";
    private static final java.lang.String ATTR_RESIZE_MODE = "resize_mode";
    private static final java.lang.String ATTR_ROOTHASRESET = "root_has_reset";
    private static final java.lang.String ATTR_ROOT_AFFINITY = "root_affinity";
    private static final java.lang.String ATTR_SUPPORTS_PICTURE_IN_PICTURE = "supports_picture_in_picture";
    private static final java.lang.String ATTR_TASKID = "task_id";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_TASKTYPE = "task_type";
    private static final java.lang.String ATTR_TASK_AFFILIATION = "task_affiliation";
    private static final java.lang.String ATTR_USERID = "user_id";
    private static final java.lang.String ATTR_USER_SETUP_COMPLETE = "user_setup_complete";
    private static final java.lang.String ATTR_WINDOW_LAYOUT_AFFINITY = "window_layout_affinity";
    private static final int DEFAULT_MIN_TASK_SIZE_DP = 220;
    static final int LAYER_RANK_INVISIBLE = -1;
    static final int PERSIST_TASK_VERSION = 1;
    static final int REPARENT_KEEP_ROOT_TASK_AT_FRONT = 1;
    static final int REPARENT_LEAVE_ROOT_TASK_IN_PLACE = 2;
    static final int REPARENT_MOVE_ROOT_TASK_TO_FRONT = 0;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_ACTIVITY = "activity";
    private static final java.lang.String TAG_AFFINITYINTENT = "affinity_intent";
    static final java.lang.String TAG_CLEANUP = "ActivityTaskManager";
    private static final java.lang.String TAG_INTENT = "intent";
    private static final java.lang.String TAG_RECENTS = "ActivityTaskManager";
    private static final java.lang.String TAG_SWITCH = "ActivityTaskManager";
    static final java.lang.String TAG_TASKS = "ActivityTaskManager";
    private static final java.lang.String TAG_TRANSITION = "ActivityTaskManager";
    private static final java.lang.String TAG_USER_LEAVING = "ActivityTaskManager";
    static final java.lang.String TAG_VISIBILITY = "ActivityTaskManager";
    private static final long TRANSLUCENT_CONVERSION_TIMEOUT = 2000;
    private static final int TRANSLUCENT_TIMEOUT_MSG = 101;
    private static java.lang.Exception sTmpException;
    java.lang.String affinity;
    android.content.Intent affinityIntent;
    boolean autoRemoveRecents;
    int effectiveUid;
    boolean inRecents;
    android.content.Intent intent;
    boolean isAvailable;
    boolean isPersistable;
    long lastActiveTime;
    java.lang.CharSequence lastDescription;
    int mAffiliatedTaskId;
    boolean mAlignActivityLocaleWithTask;
    private final com.android.server.wm.AnimatingActivityRegistry mAnimatingActivityRegistry;
    java.lang.String mCallingFeatureId;
    java.lang.String mCallingPackage;
    int mCallingUid;
    private boolean mCanAffectSystemUiFlags;
    com.android.server.wm.ActivityRecord mChildPipActivity;
    boolean mConfigWillChange;
    int mCurrentUser;

    @android.annotation.Nullable
    com.android.server.wm.Task.DecorSurfaceContainer mDecorSurfaceContainer;
    private boolean mDeferTaskAppear;
    private boolean mDragResizing;
    private final com.android.server.wm.Task.FindRootHelper mFindRootHelper;
    private boolean mForceShowForAllUsers;
    private final android.os.Handler mHandler;
    private boolean mHasBeenVisible;
    boolean mInRemoveTask;
    boolean mInResumeTopActivity;
    boolean mIsEffectivelySystemApp;
    boolean mKillProcessesOnDestroyed;
    android.graphics.Rect mLastNonFullscreenBounds;
    android.view.SurfaceControl mLastRecentsAnimationOverlay;
    android.window.PictureInPictureSurfaceTransaction mLastRecentsAnimationTransaction;
    int mLastReportedRequestedOrientation;
    private int mLastRotationDisplayId;
    boolean mLastSurfaceShowing;
    final android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData mLastTaskSnapshotData;
    long mLastTimeMoved;
    android.os.IBinder mLaunchCookie;
    int mLayerRank;
    int mLockTaskAuth;
    int mLockTaskUid;
    int mMultiWindowRestoreWindowingMode;
    private boolean mNeverRelinquishIdentity;
    com.android.server.wm.Task mNextAffiliate;
    int mNextAffiliateTaskId;
    com.android.server.wm.Task mPrevAffiliate;
    int mPrevAffiliateTaskId;
    int mPrevDisplayId;
    boolean mRemoveWithTaskOrganizer;
    private boolean mRemoving;
    boolean mReparentLeafTaskIfRelaunch;
    java.lang.String mRequiredDisplayCategory;
    int mResizeMode;
    private boolean mReuseTask;
    private com.android.server.wm.WindowProcessController mRootProcess;
    private int mRotation;
    com.android.server.wm.StartingData mSharedStartingData;
    boolean mSupportsPictureInPicture;
    boolean mTaskAppearedSent;
    private android.app.ActivityManager.TaskDescription mTaskDescription;
    final int mTaskId;
    android.window.ITaskOrganizer mTaskOrganizer;
    private android.graphics.Rect mTmpRect;
    private android.graphics.Rect mTmpRect2;
    com.android.server.wm.ActivityRecord mTranslucentActivityWaiting;
    java.util.ArrayList<com.android.server.wm.ActivityRecord> mUndrawnActivitiesBelowTopTranslucent;
    int mUserId;
    boolean mUserSetupComplete;
    java.lang.String mWindowLayoutAffinity;
    int maxRecents;
    android.content.ComponentName origActivity;
    android.content.ComponentName realActivity;
    boolean realActivitySuspended;
    java.lang.String rootAffinity;
    boolean rootWasReset;
    java.lang.String stringName;
    com.android.internal.app.IVoiceInteractor voiceInteractor;
    android.service.voice.IVoiceInteractionSession voiceSession;
    private static final android.graphics.Rect sTmpBounds = new android.graphics.Rect();
    private static final com.android.server.wm.ResetTargetTaskHelper sResetTargetTaskHelper = new com.android.server.wm.ResetTargetTaskHelper();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ReparentMoveRootTaskMode {
    }

    private class ActivityTaskHandler extends android.os.Handler {
        ActivityTaskHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 101:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.Task.this.mAtmService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.Task.this.notifyActivityDrawnLocked(null);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }
    }

    private class FindRootHelper implements java.util.function.Predicate<com.android.server.wm.ActivityRecord> {
        private boolean mIgnoreRelinquishIdentity;
        private com.android.server.wm.ActivityRecord mRoot;
        private boolean mSetToBottomIfNone;

        private FindRootHelper() {
        }

        com.android.server.wm.ActivityRecord findRoot(boolean z, boolean z2) {
            this.mIgnoreRelinquishIdentity = z;
            this.mSetToBottomIfNone = z2;
            com.android.server.wm.Task.this.forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) this, false);
            com.android.server.wm.ActivityRecord activityRecord = this.mRoot;
            this.mRoot = null;
            return activityRecord;
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.ActivityRecord activityRecord) {
            if (this.mRoot == null && this.mSetToBottomIfNone) {
                this.mRoot = activityRecord;
            }
            if (activityRecord.finishing) {
                return false;
            }
            if (this.mRoot == null || this.mRoot.finishing) {
                this.mRoot = activityRecord;
            }
            int i = this.mRoot == activityRecord ? com.android.server.wm.Task.this.effectiveUid : activityRecord.info.applicationInfo.uid;
            if (!this.mIgnoreRelinquishIdentity && (this.mRoot.info.flags & 4096) != 0) {
                if (this.mRoot.info.applicationInfo.uid != 1000 && !this.mRoot.info.applicationInfo.isSystemApp() && this.mRoot.info.applicationInfo.uid != i) {
                    return true;
                }
                this.mRoot = activityRecord;
                return false;
            }
            return true;
        }
    }

    private Task(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, int i, android.content.Intent intent, android.content.Intent intent2, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.content.ComponentName componentName2, boolean z, boolean z2, int i2, int i3, java.lang.String str3, long j, boolean z3, android.app.ActivityManager.TaskDescription taskDescription, android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData, int i4, int i5, int i6, int i7, java.lang.String str4, @android.annotation.Nullable java.lang.String str5, int i8, boolean z4, boolean z5, boolean z6, int i9, int i10, android.content.pm.ActivityInfo activityInfo, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, boolean z7, android.os.IBinder iBinder, boolean z8, boolean z9) {
        super(activityTaskManagerService, null, z7, false);
        android.app.ActivityManager.TaskDescription taskDescription2;
        android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData2;
        this.mTranslucentActivityWaiting = null;
        this.mUndrawnActivitiesBelowTopTranslucent = new java.util.ArrayList<>();
        this.mInResumeTopActivity = false;
        this.mLockTaskAuth = 1;
        this.mLockTaskUid = -1;
        this.isPersistable = false;
        this.mNeverRelinquishIdentity = true;
        this.mReuseTask = false;
        this.mPrevAffiliateTaskId = -1;
        this.mNextAffiliateTaskId = -1;
        this.mLastNonFullscreenBounds = null;
        this.mLayerRank = -1;
        this.mPrevDisplayId = -1;
        this.mLastRotationDisplayId = -1;
        this.mMultiWindowRestoreWindowingMode = -1;
        this.mLastReportedRequestedOrientation = -1;
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpRect2 = new android.graphics.Rect();
        this.mCanAffectSystemUiFlags = true;
        this.mAnimatingActivityRegistry = new com.android.server.wm.AnimatingActivityRegistry();
        this.mFindRootHelper = new com.android.server.wm.Task.FindRootHelper();
        this.mAlignActivityLocaleWithTask = false;
        this.mTaskId = i;
        this.mUserId = i2;
        this.mResizeMode = i8;
        this.mSupportsPictureInPicture = z4;
        if (taskDescription != null) {
            taskDescription2 = taskDescription;
        } else {
            taskDescription2 = new android.app.ActivityManager.TaskDescription();
        }
        this.mTaskDescription = taskDescription2;
        if (persistedTaskSnapshotData != null) {
            persistedTaskSnapshotData2 = persistedTaskSnapshotData;
        } else {
            persistedTaskSnapshotData2 = new android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData();
        }
        this.mLastTaskSnapshotData = persistedTaskSnapshotData2;
        setOrientation(-2);
        this.affinityIntent = intent2;
        this.affinity = str;
        this.rootAffinity = str2;
        this.voiceSession = iVoiceInteractionSession;
        this.voiceInteractor = iVoiceInteractor;
        this.realActivity = componentName;
        this.realActivitySuspended = z5;
        this.origActivity = componentName2;
        this.rootWasReset = z;
        this.isAvailable = true;
        this.autoRemoveRecents = z2;
        this.mUserSetupComplete = z6;
        this.effectiveUid = i3;
        touchActiveTime();
        this.lastDescription = str3;
        this.mLastTimeMoved = j;
        this.mNeverRelinquishIdentity = z3;
        this.mAffiliatedTaskId = i4;
        this.mPrevAffiliateTaskId = i5;
        this.mNextAffiliateTaskId = i6;
        this.mCallingUid = i7;
        this.mCallingPackage = str4;
        this.mCallingFeatureId = str5;
        this.mResizeMode = i8;
        if (activityInfo != null) {
            setIntent(intent, activityInfo);
            setMinDimensions(activityInfo);
        } else {
            this.intent = intent;
            this.mMinWidth = i9;
            this.mMinHeight = i10;
        }
        this.mAtmService.getTaskChangeNotificationController().notifyTaskCreated(i, this.realActivity);
        this.mHandler = new com.android.server.wm.Task.ActivityTaskHandler(this.mTaskSupervisor.mLooper);
        this.mCurrentUser = this.mAtmService.mAmInternal.getCurrentUserId();
        this.mLaunchCookie = iBinder;
        this.mDeferTaskAppear = z8;
        this.mRemoveWithTaskOrganizer = z9;
        com.android.server.wm.EventLogTags.writeWmTaskCreated(this.mTaskId);
    }

    static com.android.server.wm.Task fromWindowContainerToken(android.window.WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return null;
        }
        return com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder()).asTask();
    }

    com.android.server.wm.Task reuseAsLeafTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, com.android.server.wm.ActivityRecord activityRecord) {
        this.voiceSession = iVoiceInteractionSession;
        this.voiceInteractor = iVoiceInteractor;
        setIntent(activityRecord, intent, activityInfo);
        setMinDimensions(activityInfo);
        this.mAtmService.getTaskChangeNotificationController().notifyTaskCreated(this.mTaskId, this.realActivity);
        return this;
    }

    private void cleanUpResourcesForDestroy(com.android.server.wm.WindowContainer<?> windowContainer) {
        if (hasChild()) {
            return;
        }
        saveLaunchingStateIfNeeded(windowContainer.getDisplayContent());
        boolean z = this.voiceSession != null;
        if (z) {
            try {
                this.voiceSession.taskFinished(this.intent, this.mTaskId);
            } catch (android.os.RemoteException e) {
            }
        }
        if (shouldAutoRemoveFromRecents(windowContainer.asTaskFragment()) || z) {
            this.mTaskSupervisor.mRecentTasks.remove(this);
        }
        removeIfPossible("cleanUpResourcesForDestroy");
    }

    @Override // com.android.server.wm.WindowContainer
    @com.android.internal.annotations.VisibleForTesting
    void removeIfPossible() {
        removeIfPossible("removeTaskIfPossible");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeIfPossible(java.lang.String str) {
        this.mAtmService.getLockTaskController().clearLockedTask(this);
        if (shouldDeferRemoval()) {
            return;
        }
        boolean isLeafTask = isLeafTask();
        removeImmediately(str);
        if (isLeafTask) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskRemoved(this.mTaskId);
            com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
            if (displayArea != null) {
                displayArea.onLeafTaskRemoved(this.mTaskId);
            }
        }
    }

    void setResizeMode(int i) {
        if (this.mResizeMode == i) {
            return;
        }
        this.mResizeMode = i;
        this.mRootWindowContainer.ensureActivitiesVisible();
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        updateTaskDescription();
    }

    boolean resize(android.graphics.Rect rect, int i, boolean z) {
        com.android.server.wm.ActivityRecord activityRecord;
        this.mAtmService.deferWindowLayout();
        boolean z2 = true;
        boolean z3 = (i & 2) != 0;
        try {
            if (getParent() == null) {
                setBounds(rect);
                if (!inFreeformWindowingMode()) {
                    this.mTaskSupervisor.restoreRecentTaskLocked(this, null, false);
                }
                return true;
            }
            if (!canResizeToBounds(rect)) {
                throw new java.lang.IllegalArgumentException("resizeTask: Can not resize task=" + this + " to bounds=" + rect + " resizeMode=" + this.mResizeMode);
            }
            android.os.Trace.traceBegin(32L, "resizeTask_" + this.mTaskId);
            if (setBounds(rect, z3) != 0 && (activityRecord = topRunningActivityLocked()) != null) {
                z2 = activityRecord.ensureActivityConfiguration();
                this.mRootWindowContainer.ensureActivitiesVisible(activityRecord);
                if (!z2) {
                    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                }
            }
            saveLaunchingStateIfNeeded();
            android.os.Trace.traceEnd(32L);
            return z2;
        } finally {
            this.mAtmService.continueWindowLayout();
        }
    }

    boolean reparent(com.android.server.wm.Task task, boolean z, int i, boolean z2, boolean z3, java.lang.String str) {
        return reparent(task, z ? Integer.MAX_VALUE : 0, i, z2, z3, true, str);
    }

    boolean reparent(com.android.server.wm.Task task, int i, int i2, boolean z, boolean z2, boolean z3, java.lang.String str) {
        com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
        com.android.server.wm.RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        com.android.server.wm.WindowManagerService windowManagerService = this.mAtmService.mWindowManager;
        com.android.server.wm.Task rootTask = getRootTask();
        com.android.server.wm.Task reparentTargetRootTask = activityTaskSupervisor.getReparentTargetRootTask(this, task, i == Integer.MAX_VALUE);
        if (reparentTargetRootTask == rootTask || !canBeLaunchedOnDisplay(reparentTargetRootTask.getDisplayId())) {
            return false;
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity();
        this.mAtmService.deferWindowLayout();
        try {
            com.android.server.wm.ActivityRecord activityRecord = topRunningActivityLocked();
            boolean z4 = i2 == 0 || (i2 == 1 && ((activityRecord != null && rootWindowContainer.isTopDisplayFocusedRootTask(rootTask) && topRunningActivityLocked() == activityRecord) || (activityRecord != null && rootTask.isTopRootTaskInDisplayArea() && rootTask.topRunningActivity() == activityRecord)));
            reparent(reparentTargetRootTask, i, z4, str);
            if (z3) {
                activityTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(this, rootTask);
            }
            if (activityRecord != null && z4) {
                reparentTargetRootTask.moveToFront(str);
                if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && activityRecord == this.mRootWindowContainer.getTopResumedActivity()) {
                    this.mAtmService.setLastResumedActivityUncheckLocked(activityRecord, str);
                }
            }
            if (!z) {
                this.mTaskSupervisor.mNoAnimActivities.add(topNonFinishingActivity);
            }
            this.mAtmService.continueWindowLayout();
            if (!z2) {
                rootWindowContainer.ensureActivitiesVisible();
                rootWindowContainer.resumeFocusedTasksTopActivities();
            }
            activityTaskSupervisor.handleNonResizableTaskIfNeeded(this, task.getWindowingMode(), this.mRootWindowContainer.getDefaultTaskDisplayArea(), reparentTargetRootTask);
            return task == reparentTargetRootTask;
        } catch (java.lang.Throwable th) {
            this.mAtmService.continueWindowLayout();
            throw th;
        }
    }

    void touchActiveTime() {
        this.lastActiveTime = android.os.SystemClock.elapsedRealtime();
    }

    long getInactiveDuration() {
        return android.os.SystemClock.elapsedRealtime() - this.lastActiveTime;
    }

    void setIntent(com.android.server.wm.ActivityRecord activityRecord) {
        setIntent(activityRecord, null, null);
    }

    void setIntent(com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.content.Intent intent, @android.annotation.Nullable android.content.pm.ActivityInfo activityInfo) {
        boolean z = true;
        if (this.intent != null) {
            if (this.mNeverRelinquishIdentity) {
                z = false;
            } else {
                android.content.pm.ActivityInfo activityInfo2 = activityInfo != null ? activityInfo : activityRecord.info;
                if (this.effectiveUid != 1000 && !this.mIsEffectivelySystemApp && this.effectiveUid != activityInfo2.applicationInfo.uid) {
                    z = false;
                }
            }
        }
        if (z) {
            this.mCallingUid = activityRecord.launchedFromUid;
            this.mCallingPackage = activityRecord.launchedFromPackage;
            this.mCallingFeatureId = activityRecord.launchedFromFeatureId;
            if (intent == null) {
                intent = activityRecord.intent;
            }
            if (activityInfo == null) {
                activityInfo = activityRecord.info;
            }
            setIntent(intent, activityInfo);
        }
        setLockTaskAuth(activityRecord);
    }

    private void setIntent(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo) {
        if (isLeafTask()) {
            this.mNeverRelinquishIdentity = (activityInfo.flags & 4096) == 0;
            this.affinity = activityInfo.taskAffinity;
            if (this.intent == null) {
                this.rootAffinity = this.affinity;
                this.mRequiredDisplayCategory = activityInfo.requiredDisplayCategory;
            }
            this.effectiveUid = activityInfo.applicationInfo.uid;
            this.mIsEffectivelySystemApp = activityInfo.applicationInfo.isSystemApp();
            this.stringName = null;
            if (activityInfo.targetActivity == null) {
                if (intent != null && (intent.getSelector() != null || intent.getSourceBounds() != null)) {
                    android.content.Intent intent2 = new android.content.Intent(intent);
                    intent2.setSelector(null);
                    intent2.setSourceBounds(null);
                    intent = intent2;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -8609432747982701423L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(intent));
                this.intent = intent;
                this.realActivity = intent != null ? intent.getComponent() : null;
                this.origActivity = null;
            } else {
                android.content.ComponentName componentName = new android.content.ComponentName(activityInfo.packageName, activityInfo.targetActivity);
                if (intent != null) {
                    android.content.Intent intent3 = new android.content.Intent(intent);
                    intent3.setSelector(null);
                    intent3.setSourceBounds(null);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -9155008290180285590L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(intent3));
                    this.intent = intent3;
                    this.realActivity = componentName;
                    this.origActivity = intent.getComponent();
                } else {
                    this.intent = null;
                    this.realActivity = componentName;
                    this.origActivity = new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
                }
            }
            this.mWindowLayoutAffinity = activityInfo.windowLayout != null ? activityInfo.windowLayout.windowLayoutAffinity : null;
            int flags = this.intent == null ? 0 : this.intent.getFlags();
            if ((2097152 & flags) != 0) {
                this.rootWasReset = true;
            }
            this.mUserId = android.os.UserHandle.getUserId(activityInfo.applicationInfo.uid);
            this.mUserSetupComplete = android.provider.Settings.Secure.getIntForUser(this.mAtmService.mContext.getContentResolver(), ATTR_USER_SETUP_COMPLETE, 0, this.mUserId) != 0;
            if ((activityInfo.flags & 8192) != 0) {
                this.autoRemoveRecents = true;
            } else if ((flags & 532480) == 524288) {
                if (activityInfo.documentLaunchMode != 0) {
                    this.autoRemoveRecents = false;
                } else {
                    this.autoRemoveRecents = true;
                }
            } else {
                this.autoRemoveRecents = false;
            }
            if (this.mResizeMode != activityInfo.resizeMode) {
                this.mResizeMode = activityInfo.resizeMode;
                updateTaskDescription();
            }
            this.mSupportsPictureInPicture = activityInfo.supportsPictureInPicture();
            if (this.inRecents) {
                this.mTaskSupervisor.mRecentTasks.remove(this);
                this.mTaskSupervisor.mRecentTasks.add(this);
            }
        }
    }

    void setMinDimensions(android.content.pm.ActivityInfo activityInfo) {
        if (activityInfo != null && activityInfo.windowLayout != null) {
            this.mMinWidth = activityInfo.windowLayout.minWidth;
            this.mMinHeight = activityInfo.windowLayout.minHeight;
        } else {
            this.mMinWidth = -1;
            this.mMinHeight = -1;
        }
    }

    boolean isSameIntentFilter(com.android.server.wm.ActivityRecord activityRecord) {
        android.content.Intent intent = new android.content.Intent(activityRecord.intent);
        if (java.util.Objects.equals(this.realActivity, activityRecord.mActivityComponent) && this.intent != null) {
            intent.setComponent(this.intent.getComponent());
            intent.setPackage(this.intent.getPackage());
        }
        return intent.filterEquals(this.intent);
    }

    boolean returnsToHomeRootTask() {
        if (inMultiWindowMode() || !hasChild()) {
            return false;
        }
        if (this.intent != null) {
            if ((this.intent.getFlags() & 268451840) != 268451840) {
                return false;
            }
            com.android.server.wm.Task rootHomeTask = getDisplayArea() != null ? getDisplayArea().getRootHomeTask() : null;
            return rootHomeTask == null || !this.mAtmService.getLockTaskController().isLockTaskModeViolation(rootHomeTask);
        }
        com.android.server.wm.Task bottomMostTask = getBottomMostTask();
        return bottomMostTask != this && bottomMostTask.returnsToHomeRootTask();
    }

    void setPrevAffiliate(com.android.server.wm.Task task) {
        this.mPrevAffiliate = task;
        this.mPrevAffiliateTaskId = task == null ? -1 : task.mTaskId;
    }

    void setNextAffiliate(com.android.server.wm.Task task) {
        this.mNextAffiliate = task;
        this.mNextAffiliateTaskId = task == null ? -1 : task.mTaskId;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) configurationContainer;
        com.android.server.wm.WindowContainer<?> windowContainer2 = (com.android.server.wm.WindowContainer) configurationContainer2;
        com.android.server.wm.DisplayContent displayContent = windowContainer != null ? windowContainer.getDisplayContent() : null;
        com.android.server.wm.DisplayContent displayContent2 = windowContainer2 != null ? windowContainer2.getDisplayContent() : null;
        this.mPrevDisplayId = displayContent2 != null ? displayContent2.mDisplayId : -1;
        if (windowContainer2 != null && windowContainer == null) {
            cleanUpResourcesForDestroy(windowContainer2);
        }
        if (displayContent != null) {
            getConfiguration().windowConfiguration.setRotation(displayContent.getWindowConfiguration().getRotation());
        }
        super.onParentChanged(windowContainer, windowContainer2);
        updateTaskOrganizerState();
        if (getParent() == null && this.mDisplayContent != null) {
            this.mDisplayContent = null;
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
        if (windowContainer2 != null) {
            final com.android.server.wm.Task asTask = windowContainer2.asTask();
            if (asTask != null) {
                java.util.Objects.requireNonNull(asTask);
                forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda40
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.Task.this.cleanUpActivityReferences((com.android.server.wm.ActivityRecord) obj);
                    }
                });
            }
            if (windowContainer == null || !windowContainer.inPinnedWindowingMode()) {
                if (windowContainer2.inPinnedWindowingMode()) {
                    this.mRootWindowContainer.notifyActivityPipModeChanged(this, null);
                } else if (inPinnedWindowingMode()) {
                    android.util.Slog.e("ActivityTaskManager", "Pinned task is removed t=" + this);
                    this.mRootWindowContainer.notifyActivityPipModeChanged(this, null);
                }
            }
        }
        if (windowContainer != null) {
            if (!this.mCreatedByOrganizer && !canBeOrganized()) {
                getSyncTransaction().show(this.mSurfaceControl);
            }
            if (this.voiceSession != null) {
                try {
                    this.voiceSession.taskStarted(this.intent, this.mTaskId);
                } catch (android.os.RemoteException e) {
                }
            }
        }
        if (windowContainer2 == null && windowContainer != null) {
            updateOverrideConfigurationFromLaunchBounds();
        }
        adjustBoundsForDisplayChangeIfNeeded(getDisplayContent());
        this.mRootWindowContainer.updateUIDsPresentOnDisplay();
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda41
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.ActivityRecord) obj).updateAnimatingActivityRegistry();
            }
        });
    }

    @Override // com.android.server.wm.TaskFragment
    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getTopResumedActivity() {
        if (!isLeafTask()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityRecord topResumedActivity = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask().getTopResumedActivity();
                if (topResumedActivity != null) {
                    return topResumedActivity;
                }
            }
        }
        com.android.server.wm.ActivityRecord resumedActivity = getResumedActivity();
        com.android.server.wm.ActivityRecord activityRecord = null;
        for (int size2 = this.mChildren.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size2);
            if (windowContainer.asTaskFragment() != null) {
                activityRecord = windowContainer.asTaskFragment().getTopResumedActivity();
            } else if (resumedActivity != null && windowContainer.asActivityRecord() == resumedActivity) {
                activityRecord = resumedActivity;
            }
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    @Override // com.android.server.wm.TaskFragment
    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getTopPausingActivity() {
        if (!isLeafTask()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityRecord topPausingActivity = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask().getTopPausingActivity();
                if (topPausingActivity != null) {
                    return topPausingActivity;
                }
            }
        }
        com.android.server.wm.ActivityRecord pausingActivity = getPausingActivity();
        com.android.server.wm.ActivityRecord activityRecord = null;
        for (int size2 = this.mChildren.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size2);
            if (windowContainer.asTaskFragment() != null) {
                activityRecord = windowContainer.asTaskFragment().getTopPausingActivity();
            } else if (pausingActivity != null && windowContainer.asActivityRecord() == pausingActivity) {
                activityRecord = pausingActivity;
            }
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    boolean pauseActivityIfNeeded(@android.annotation.Nullable final com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull final java.lang.String str) {
        if (!isLeafTask()) {
            return false;
        }
        final int[] iArr = {0};
        if (!isLeafTaskFragment()) {
            com.android.server.wm.ActivityRecord activityRecord2 = topRunningActivity();
            if (getResumedActivity() != null && activityRecord2.getTaskFragment() != this && startPausing(false, activityRecord, str)) {
                iArr[0] = iArr[0] + 1;
            }
        }
        forAllLeafTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$pauseActivityIfNeeded$0(com.android.server.wm.ActivityRecord.this, str, iArr, (com.android.server.wm.TaskFragment) obj);
            }
        }, true);
        return iArr[0] > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pauseActivityIfNeeded$0(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int[] iArr, com.android.server.wm.TaskFragment taskFragment) {
        if (taskFragment.getResumedActivity() != null && !taskFragment.canBeResumed(activityRecord) && taskFragment.startPausing(false, activityRecord, str)) {
            iArr[0] = iArr[0] + 1;
        }
    }

    void updateTaskMovement(boolean z, boolean z2, int i) {
        com.android.server.wm.EventLogTags.writeWmTaskMoved(this.mTaskId, getRootTaskId(), getDisplayId(), z ? 1 : 0, i);
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea != null && isLeafTask()) {
            displayArea.onLeafTaskMoved(this, z, z2);
        }
        if (this.isPersistable) {
            this.mLastTimeMoved = java.lang.System.currentTimeMillis();
        }
        if (z && this.inRecents) {
            this.mTaskSupervisor.mRecentTasks.add(this);
        }
    }

    private void closeRecentsChain() {
        if (this.mPrevAffiliate != null) {
            this.mPrevAffiliate.setNextAffiliate(this.mNextAffiliate);
        }
        if (this.mNextAffiliate != null) {
            this.mNextAffiliate.setPrevAffiliate(this.mPrevAffiliate);
        }
        setPrevAffiliate(null);
        setNextAffiliate(null);
    }

    void removedFromRecents() {
        closeRecentsChain();
        if (this.inRecents) {
            this.inRecents = false;
            this.mAtmService.notifyTaskPersisterLocked(this, false);
        }
        clearRootProcess();
        this.mAtmService.mWindowManager.mTaskSnapshotController.removeAndDeleteSnapshot(this.mTaskId, this.mUserId);
    }

    void setTaskToAffiliateWith(com.android.server.wm.Task task) {
        closeRecentsChain();
        this.mAffiliatedTaskId = task.mAffiliatedTaskId;
        while (true) {
            if (task.mNextAffiliate == null) {
                break;
            }
            com.android.server.wm.Task task2 = task.mNextAffiliate;
            if (task2.mAffiliatedTaskId != this.mAffiliatedTaskId) {
                android.util.Slog.e("ActivityTaskManager", "setTaskToAffiliateWith: nextRecents=" + task2 + " affilTaskId=" + task2.mAffiliatedTaskId + " should be " + this.mAffiliatedTaskId);
                if (task2.mPrevAffiliate == task) {
                    task2.setPrevAffiliate(null);
                }
                task.setNextAffiliate(null);
            } else {
                task = task2;
            }
        }
        task.setNextAffiliate(this);
        setPrevAffiliate(task);
        setNextAffiliate(null);
    }

    android.content.Intent getBaseIntent() {
        if (this.intent != null) {
            return this.intent;
        }
        if (this.affinityIntent != null) {
            return this.affinityIntent;
        }
        com.android.server.wm.Task topMostTask = getTopMostTask();
        if (topMostTask == this || topMostTask == null) {
            return null;
        }
        return topMostTask.getBaseIntent();
    }

    @android.annotation.NonNull
    java.lang.String getBasePackageName() {
        android.content.ComponentName component;
        android.content.Intent baseIntent = getBaseIntent();
        return (baseIntent == null || (component = baseIntent.getComponent()) == null) ? "" : component.getPackageName();
    }

    com.android.server.wm.ActivityRecord getRootActivity() {
        return getRootActivity(true, false);
    }

    com.android.server.wm.ActivityRecord getRootActivity(boolean z) {
        return getRootActivity(false, z);
    }

    com.android.server.wm.ActivityRecord getRootActivity(boolean z, boolean z2) {
        return this.mFindRootHelper.findRoot(z, z2);
    }

    com.android.server.wm.ActivityRecord topRunningActivityLocked() {
        if (getParent() == null) {
            return null;
        }
        return getActivity(new com.android.server.wm.Task$$ExternalSyntheticLambda21());
    }

    boolean isUidPresent(int i) {
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.server.wm.DisplayContent$$ExternalSyntheticLambda12(), com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), java.lang.Integer.valueOf(i));
        boolean z = getActivity(obtainPredicate) != null;
        obtainPredicate.recycle();
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$topStartingWindow$1(com.android.server.wm.WindowState windowState) {
        return windowState.mAttrs.type == 3;
    }

    com.android.server.wm.WindowState topStartingWindow() {
        return getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$topStartingWindow$1;
                lambda$topStartingWindow$1 = com.android.server.wm.Task.lambda$topStartingWindow$1((com.android.server.wm.WindowState) obj);
                return lambda$topStartingWindow$1;
            }
        });
    }

    com.android.server.wm.ActivityRecord topActivityContainsStartingWindow() {
        com.android.server.wm.WindowState windowState = topStartingWindow();
        if (windowState != null) {
            return windowState.mActivityRecord;
        }
        return null;
    }

    final boolean moveActivityToFront(com.android.server.wm.ActivityRecord activityRecord) {
        boolean moveChildToFront;
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 6424220442758232673L, 0, null, java.lang.String.valueOf(activityRecord), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
        com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
        if (taskFragment != this) {
            moveChildToFront = true;
            if (taskFragment.isEmbedded() && taskFragment.getNonFinishingActivityCount() == 1) {
                taskFragment.mClearedForReorderActivityToFront = true;
            }
            activityRecord.reparent(this, Integer.MAX_VALUE);
            if (taskFragment.isEmbedded()) {
                this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController.onActivityReparentedToTask(activityRecord);
            }
        } else {
            moveChildToFront = moveChildToFront(activityRecord);
        }
        updateEffectiveIntent();
        return moveChildToFront;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void addChild(com.android.server.wm.WindowContainer windowContainer, int i) {
        super.addChild(windowContainer, getAdjustedChildPosition(windowContainer, i));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1028890010429408946L, 0, null, java.lang.String.valueOf(this));
        if (this.mTaskOrganizer != null && this.mCreatedByOrganizer && windowContainer.asTask() != null) {
            getDisplayArea().addRootTaskReferenceIfNeeded((com.android.server.wm.Task) windowContainer);
        }
        this.mRootWindowContainer.updateUIDsPresentOnDisplay();
        com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment != null && asTaskFragment.asTask() == null) {
            asTaskFragment.setMinDimensions(this.mMinWidth, this.mMinHeight);
            com.android.server.wm.ActivityRecord topMostActivity = getTopMostActivity();
            if (topMostActivity != null) {
                topMostActivity.associateStartingWindowWithTaskIfNeeded();
            }
        }
    }

    void onDescendantActivityAdded(boolean z, int i, com.android.server.wm.ActivityRecord activityRecord) {
        warnForNonLeafTask("onDescendantActivityAdded");
        if (!z) {
            int activityType = activityRecord.getRequestedOverrideConfiguration().windowConfiguration.getActivityType();
            if (activityType == 0) {
                if (i == 0) {
                    i = 1;
                }
                activityRecord.getRequestedOverrideConfiguration().windowConfiguration.setActivityType(i);
                activityType = i;
            }
            setActivityType(activityType);
            this.isPersistable = activityRecord.isPersistable();
            this.mCallingUid = activityRecord.launchedFromUid;
            this.mCallingPackage = activityRecord.launchedFromPackage;
            this.mCallingFeatureId = activityRecord.launchedFromFeatureId;
            this.maxRecents = java.lang.Math.min(java.lang.Math.max(activityRecord.info.maxRecents, 1), android.app.ActivityTaskManager.getMaxAppRecentsLimitStatic());
        } else {
            activityRecord.setActivityType(i);
        }
        updateEffectiveIntent();
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void removeChild(com.android.server.wm.WindowContainer windowContainer) {
        removeChild(windowContainer, "removeChild");
    }

    void removeChild(com.android.server.wm.WindowContainer windowContainer, java.lang.String str) {
        if (this.mCreatedByOrganizer && windowContainer.asTask() != null) {
            getDisplayArea().removeRootTaskReferenceIfNeeded((com.android.server.wm.Task) windowContainer);
        }
        if (!this.mChildren.contains(windowContainer)) {
            android.util.Slog.e("ActivityTaskManager", "removeChild: r=" + windowContainer + " not found in t=" + this);
            return;
        }
        super.removeChild(windowContainer, false);
        if (inPinnedWindowingMode()) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskStackChanged();
        }
        if (this.mDecorSurfaceContainer != null && windowContainer == this.mDecorSurfaceContainer.mOwnerTaskFragment) {
            removeDecorSurface();
        }
        if (hasChild()) {
            updateEffectiveIntent();
            if (onlyHasTaskOverlayActivities(true)) {
                this.mTaskSupervisor.removeTask(this, false, false, str);
                return;
            }
            return;
        }
        if (!this.mReuseTask && shouldRemoveSelfOnLastChildRemoval()) {
            removeIfPossible(str + ", last child = " + windowContainer + " in " + this);
        }
    }

    boolean onlyHasTaskOverlayActivities(boolean z) {
        int i = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.ActivityRecord asActivityRecord = getChildAt(childCount).asActivityRecord();
            if (asActivityRecord == null) {
                return false;
            }
            if (z || !asActivityRecord.finishing) {
                if (!asActivityRecord.isTaskOverlay()) {
                    return false;
                }
                i++;
            }
        }
        return i > 0;
    }

    private boolean shouldAutoRemoveFromRecents(com.android.server.wm.TaskFragment taskFragment) {
        return this.autoRemoveRecents || !(hasChild() || getHasBeenVisible()) || ((taskFragment != null && taskFragment.isEmbedded()) || !(this.mDisplayContent == null || this.mDisplayContent.canShowTasksInHostDeviceRecents()));
    }

    private void clearPinnedTaskIfNeed() {
        if (this.mChildPipActivity != null && this.mChildPipActivity.getTask() != null) {
            this.mTaskSupervisor.removeRootTask(this.mChildPipActivity.getTask());
        }
    }

    void removeActivities(final java.lang.String str, final boolean z) {
        clearPinnedTaskIfNeed();
        if (getRootTask() == null) {
            forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda18
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.Task.this.lambda$removeActivities$2(z, str, (com.android.server.wm.ActivityRecord) obj);
                }
            });
            return;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$removeActivities$3(z, arrayList, (com.android.server.wm.ActivityRecord) obj);
            }
        });
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) arrayList.get(size);
            if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) || (activityRecord.isVisible() && !this.mDisplayContent.mAppTransition.containsTransitRequest(2))) {
                activityRecord.finishIfPossible(str, false);
            } else {
                activityRecord.destroyIfPossible(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeActivities$2(boolean z, java.lang.String str, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.finishing) {
            return;
        }
        if (z && activityRecord.isTaskOverlay()) {
            return;
        }
        activityRecord.takeFromHistory();
        removeChild(activityRecord, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeActivities$3(boolean z, java.util.ArrayList arrayList, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.finishing) {
            return;
        }
        if (z && activityRecord.isTaskOverlay()) {
            return;
        }
        arrayList.add(activityRecord);
    }

    void performClearTaskForReuse(boolean z) {
        this.mReuseTask = true;
        this.mTaskSupervisor.beginDeferResume();
        try {
            removeActivities("clear-task-all", z);
        } finally {
            this.mTaskSupervisor.endDeferResume();
            this.mReuseTask = false;
        }
    }

    com.android.server.wm.ActivityRecord performClearTop(com.android.server.wm.ActivityRecord activityRecord, int i, int[] iArr) {
        this.mReuseTask = true;
        this.mTaskSupervisor.beginDeferResume();
        try {
            return clearTopActivities(activityRecord, i, iArr);
        } finally {
            this.mTaskSupervisor.endDeferResume();
            this.mReuseTask = false;
        }
    }

    private com.android.server.wm.ActivityRecord clearTopActivities(com.android.server.wm.ActivityRecord activityRecord, int i, final int[] iArr) {
        com.android.server.wm.ActivityRecord findActivityInHistory = findActivityInHistory(activityRecord.mActivityComponent, activityRecord.mUserId);
        if (findActivityInHistory == null) {
            return null;
        }
        moveTaskFragmentsToBottomIfNeeded(findActivityInHistory, iArr);
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new java.util.function.BiPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda10
            @Override // java.util.function.BiPredicate
            public final boolean test(java.lang.Object obj, java.lang.Object obj2) {
                boolean lambda$clearTopActivities$4;
                lambda$clearTopActivities$4 = com.android.server.wm.Task.lambda$clearTopActivities$4(iArr, (com.android.server.wm.ActivityRecord) obj, (com.android.server.wm.ActivityRecord) obj2);
                return lambda$clearTopActivities$4;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), findActivityInHistory);
        forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) obtainPredicate);
        obtainPredicate.recycle();
        if (findActivityInHistory.launchMode == 0 && (536870912 & i) == 0 && !com.android.server.wm.ActivityStarter.isDocumentLaunchesIntoExisting(i) && !findActivityInHistory.finishing) {
            findActivityInHistory.finishIfPossible("clear-task-top", false);
        }
        return findActivityInHistory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$clearTopActivities$4(int[] iArr, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return finishActivityAbove(activityRecord, activityRecord2, iArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    void moveTaskFragmentsToBottomIfNeeded(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull int[] iArr) {
        int indexOf = this.mChildren.indexOf(activityRecord);
        if (indexOf < 0) {
            return;
        }
        java.util.ArrayList arrayList = null;
        for (int size = this.mChildren.size() - 1; size > indexOf; size--) {
            com.android.server.wm.TaskFragment asTaskFragment = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTaskFragment();
            if (asTaskFragment != null && asTaskFragment.isMoveToBottomIfClearWhenLaunch()) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(asTaskFragment);
            }
        }
        if (arrayList == null) {
            return;
        }
        int size2 = arrayList.size();
        for (int i = 0; i < size2; i++) {
            com.android.server.wm.TaskFragment taskFragment = (com.android.server.wm.TaskFragment) arrayList.get(i);
            this.mTransitionController.collect(taskFragment);
            positionChildAt(Integer.MIN_VALUE, taskFragment, false);
        }
        iArr[0] = iArr[0] + size2;
    }

    private static boolean finishActivityAbove(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.NonNull int[] iArr) {
        if (activityRecord == activityRecord2) {
            return true;
        }
        if (!activityRecord.finishing && !activityRecord.isTaskOverlay()) {
            android.app.ActivityOptions options = activityRecord.getOptions();
            if (options != null) {
                activityRecord.clearOptionsAnimation();
                activityRecord2.updateOptionsLocked(options);
            }
            iArr[0] = iArr[0] + 1;
            activityRecord.finishIfPossible("clear-task-stack", false);
        }
        return false;
    }

    java.lang.String lockTaskAuthToString() {
        switch (this.mLockTaskAuth) {
            case 0:
                return "LOCK_TASK_AUTH_DONT_LOCK";
            case 1:
                return "LOCK_TASK_AUTH_PINNABLE";
            case 2:
                return "LOCK_TASK_AUTH_LAUNCHABLE";
            case 3:
                return "LOCK_TASK_AUTH_ALLOWLISTED";
            case 4:
                return "LOCK_TASK_AUTH_LAUNCHABLE_PRIV";
            default:
                return "unknown=" + this.mLockTaskAuth;
        }
    }

    void setLockTaskAuth() {
        setLockTaskAuth(getRootActivity());
    }

    private void setLockTaskAuth(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        this.mLockTaskAuth = this.mAtmService.getLockTaskController().getLockTaskAuth(activityRecord, this);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 38991867929900764L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(lockTaskAuthToString()));
    }

    boolean supportsFreeform() {
        return supportsFreeformInDisplayArea(getDisplayArea());
    }

    boolean supportsFreeformInDisplayArea(@android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return this.mAtmService.mSupportsFreeformWindowManagement && supportsMultiWindowInDisplayArea(taskDisplayArea);
    }

    boolean canBeLaunchedOnDisplay(int i) {
        return this.mTaskSupervisor.canPlaceEntityOnDisplay(i, -1, -1, this);
    }

    private boolean canResizeToBounds(android.graphics.Rect rect) {
        if (rect == null || !inFreeformWindowingMode()) {
            return true;
        }
        boolean z = rect.width() > rect.height();
        android.graphics.Rect requestedOverrideBounds = getRequestedOverrideBounds();
        if (this.mResizeMode != 7) {
            return !(this.mResizeMode == 6 && z) && (this.mResizeMode != 5 || z);
        }
        if (requestedOverrideBounds.isEmpty()) {
            return true;
        }
        return z == (requestedOverrideBounds.width() > requestedOverrideBounds.height());
    }

    boolean isClearingToReuseTask() {
        return this.mReuseTask;
    }

    com.android.server.wm.ActivityRecord findActivityInHistory(android.content.ComponentName componentName, int i) {
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.internal.util.function.TriPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda35
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                boolean matchesActivityInHistory;
                matchesActivityInHistory = com.android.server.wm.Task.matchesActivityInHistory((com.android.server.wm.ActivityRecord) obj, (android.content.ComponentName) obj2, ((java.lang.Integer) obj3).intValue());
                return matchesActivityInHistory;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), componentName, java.lang.Integer.valueOf(i));
        com.android.server.wm.ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean matchesActivityInHistory(com.android.server.wm.ActivityRecord activityRecord, android.content.ComponentName componentName, int i) {
        return !activityRecord.finishing && activityRecord.mActivityComponent.equals(componentName) && activityRecord.mUserId == i;
    }

    void updateTaskDescription() {
        com.android.server.wm.Task asTask;
        com.android.server.wm.ActivityRecord rootActivity = getRootActivity(true);
        if (rootActivity == null) {
            return;
        }
        android.app.ActivityManager.TaskDescription taskDescription = new android.app.ActivityManager.TaskDescription();
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.internal.util.function.TriPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda17
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                boolean taskDescriptionFromActivityAboveRoot;
                taskDescriptionFromActivityAboveRoot = com.android.server.wm.Task.setTaskDescriptionFromActivityAboveRoot((com.android.server.wm.ActivityRecord) obj, (com.android.server.wm.ActivityRecord) obj2, (android.app.ActivityManager.TaskDescription) obj3);
                return taskDescriptionFromActivityAboveRoot;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), rootActivity, taskDescription);
        forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) obtainPredicate);
        obtainPredicate.recycle();
        taskDescription.setResizeMode(this.mResizeMode);
        taskDescription.setMinWidth(this.mMinWidth);
        taskDescription.setMinHeight(this.mMinHeight);
        setTaskDescription(taskDescription);
        this.mAtmService.getTaskChangeNotificationController().notifyTaskDescriptionChanged(getTaskInfo());
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null && (asTask = parent.asTask()) != null) {
            asTask.updateTaskDescription();
        }
        dispatchTaskInfoChangedIfNeeded(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean setTaskDescriptionFromActivityAboveRoot(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.app.ActivityManager.TaskDescription taskDescription) {
        if (!activityRecord.isTaskOverlay() && activityRecord.taskDescription != null) {
            android.app.ActivityManager.TaskDescription taskDescription2 = activityRecord.taskDescription;
            if (taskDescription.getLabel() == null) {
                taskDescription.setLabel(taskDescription2.getLabel());
            }
            if (taskDescription.getRawIcon() == null) {
                taskDescription.setIcon(taskDescription2.getRawIcon());
            }
            if (taskDescription.getIconFilename() == null) {
                taskDescription.setIconFilename(taskDescription2.getIconFilename());
            }
            if (taskDescription.getPrimaryColor() == 0) {
                taskDescription.setPrimaryColor(taskDescription2.getPrimaryColor());
            }
            if (taskDescription.getBackgroundColor() == 0) {
                taskDescription.setBackgroundColor(taskDescription2.getBackgroundColor());
            }
            if (taskDescription.getStatusBarColor() == 0) {
                taskDescription.setStatusBarColor(taskDescription2.getStatusBarColor());
                taskDescription.setEnsureStatusBarContrastWhenTransparent(taskDescription2.getEnsureStatusBarContrastWhenTransparent());
            }
            if (taskDescription.getStatusBarAppearance() == 0) {
                taskDescription.setStatusBarAppearance(taskDescription2.getStatusBarAppearance());
            }
            if (taskDescription.getNavigationBarColor() == 0) {
                taskDescription.setNavigationBarColor(taskDescription2.getNavigationBarColor());
                taskDescription.setEnsureNavigationBarContrastWhenTransparent(taskDescription2.getEnsureNavigationBarContrastWhenTransparent());
            }
            if (taskDescription.getBackgroundColorFloating() == 0) {
                taskDescription.setBackgroundColorFloating(taskDescription2.getBackgroundColorFloating());
            }
        }
        return activityRecord == activityRecord2;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateEffectiveIntent() {
        com.android.server.wm.ActivityRecord rootActivity = getRootActivity(true);
        if (rootActivity != null) {
            setIntent(rootActivity);
            updateTaskDescription();
        }
    }

    void setLastNonFullscreenBounds(android.graphics.Rect rect) {
        if (this.mLastNonFullscreenBounds == null) {
            this.mLastNonFullscreenBounds = new android.graphics.Rect(rect);
        } else {
            this.mLastNonFullscreenBounds.set(rect);
        }
    }

    private void onConfigurationChangedInner(android.content.res.Configuration configuration) {
        com.android.server.wm.ActivityRecord activityRecord;
        boolean persistTaskBounds = getWindowConfiguration().persistTaskBounds();
        boolean persistTaskBounds2 = getRequestedOverrideConfiguration().windowConfiguration.persistTaskBounds();
        if (getRequestedOverrideWindowingMode() == 0) {
            persistTaskBounds2 = configuration.windowConfiguration.persistTaskBounds();
        }
        boolean z = persistTaskBounds2 & (getRequestedOverrideConfiguration().windowConfiguration.getBounds() == null || getRequestedOverrideConfiguration().windowConfiguration.getBounds().isEmpty());
        if (!persistTaskBounds && z && this.mLastNonFullscreenBounds != null && !this.mLastNonFullscreenBounds.isEmpty()) {
            getRequestedOverrideConfiguration().windowConfiguration.setBounds(this.mLastNonFullscreenBounds);
        }
        int windowingMode = getWindowingMode();
        this.mTmpPrevBounds.set(getBounds());
        boolean inMultiWindowMode = inMultiWindowMode();
        boolean inPinnedWindowingMode = inPinnedWindowingMode();
        super.onConfigurationChanged(configuration);
        updateSurfaceSize(getSyncTransaction());
        boolean z2 = inPinnedWindowingMode != inPinnedWindowingMode();
        if (z2) {
            this.mTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(this, getRootTask());
        } else if (inMultiWindowMode != inMultiWindowMode()) {
            this.mTaskSupervisor.scheduleUpdateMultiWindowMode(this);
        }
        if (shouldStartChangeTransition(windowingMode, this.mTmpPrevBounds)) {
            initializeChangeTransition(this.mTmpPrevBounds);
        }
        if (getWindowConfiguration().persistTaskBounds()) {
            android.graphics.Rect requestedOverrideBounds = getRequestedOverrideBounds();
            if (!requestedOverrideBounds.isEmpty()) {
                setLastNonFullscreenBounds(requestedOverrideBounds);
            }
        }
        if (z2 && inPinnedWindowingMode && !this.mTransitionController.isShellTransitionsEnabled() && (activityRecord = topRunningActivity()) != null && this.mDisplayContent.isFixedRotationLaunchingApp(activityRecord)) {
            resetSurfaceControlTransforms();
        }
        saveLaunchingStateIfNeeded();
        boolean updateTaskOrganizerState = updateTaskOrganizerState();
        if (updateTaskOrganizerState) {
            updateSurfacePosition(getSyncTransaction());
            if (!isOrganized()) {
                updateSurfaceSize(getSyncTransaction());
            }
        }
        if (!updateTaskOrganizerState) {
            dispatchTaskInfoChangedIfNeeded(false);
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        if (this.mDisplayContent != null && this.mDisplayContent.mPinnedTaskController.isFreezingTaskConfig(this)) {
            return;
        }
        if (!isRootTask()) {
            onConfigurationChangedInner(configuration);
            return;
        }
        int windowingMode = getWindowingMode();
        boolean isAlwaysOnTop = isAlwaysOnTop();
        int rotation = getWindowConfiguration().getRotation();
        android.graphics.Rect rect = this.mTmpRect;
        getBounds(rect);
        onConfigurationChangedInner(configuration);
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            return;
        }
        if (windowingMode != getWindowingMode()) {
            displayArea.onRootTaskWindowingModeChanged(this);
        }
        if (!isOrganized() && !getRequestedOverrideBounds().isEmpty() && this.mDisplayContent != null) {
            int rotation2 = getWindowConfiguration().getRotation();
            if (rotation != rotation2) {
                this.mDisplayContent.rotateBounds(rotation, rotation2, rect);
                setBounds(rect);
            }
        }
        if (isAlwaysOnTop != isAlwaysOnTop()) {
            displayArea.positionChildAt(Integer.MAX_VALUE, this, false);
        }
    }

    void resolveLeafTaskOnlyOverrideConfigs(android.content.res.Configuration configuration, android.graphics.Rect rect) {
        if (!isLeafTask()) {
            return;
        }
        int windowingMode = getResolvedOverrideConfiguration().windowConfiguration.getWindowingMode();
        if (windowingMode == 0) {
            windowingMode = configuration.windowConfiguration.getWindowingMode();
        }
        getConfiguration().windowConfiguration.setWindowingMode(windowingMode);
        android.graphics.Rect bounds = getResolvedOverrideConfiguration().windowConfiguration.getBounds();
        if (windowingMode == 1) {
            if (!this.mCreatedByOrganizer) {
                bounds.setEmpty();
            }
        } else {
            adjustForMinimalTaskDimensions(bounds, rect, configuration);
            if (windowingMode == 5) {
                computeFreeformBounds(bounds, configuration);
            }
        }
    }

    void adjustForMinimalTaskDimensions(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2, @android.annotation.NonNull android.content.res.Configuration configuration) {
        int i = this.mMinWidth;
        int i2 = this.mMinHeight;
        if (!inPinnedWindowingMode()) {
            int i3 = (int) ((this.mDisplayContent == null ? 220 : this.mDisplayContent.mMinSizeOfResizeableTaskDp) * (configuration.densityDpi / 160.0f));
            if (i == -1) {
                i = i3;
            }
            if (i2 == -1) {
                i2 = i3;
            }
        }
        if (rect.isEmpty()) {
            android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
            if (bounds.width() >= i && bounds.height() >= i2) {
                return;
            } else {
                rect.set(bounds);
            }
        }
        boolean z = i > rect.width();
        boolean z2 = i2 > rect.height();
        if (!z && !z2) {
            return;
        }
        if (z) {
            if (!rect2.isEmpty() && rect.right == rect2.right) {
                rect.left = rect.right - i;
            } else {
                rect.right = rect.left + i;
            }
        }
        if (z2) {
            if (!rect2.isEmpty() && rect.bottom == rect2.bottom) {
                rect.top = rect.bottom - i2;
            } else {
                rect.bottom = rect.top + i2;
            }
        }
    }

    private void computeFreeformBounds(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.content.res.Configuration configuration) {
        float f = configuration.densityDpi / 160.0f;
        android.graphics.Rect rect2 = new android.graphics.Rect(configuration.windowConfiguration.getBounds());
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            android.graphics.Rect rect3 = new android.graphics.Rect();
            displayContent.getStableRect(rect3);
            rect2.intersect(rect3);
        }
        fitWithinBounds(rect, rect2, (int) (48.0f * f), (int) (f * 32.0f));
        int i = rect2.top - rect.top;
        if (i > 0) {
            rect.offset(0, i);
        }
    }

    private static void fitWithinBounds(android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2) {
        int i3;
        if (rect2 == null || rect2.isEmpty() || rect2.contains(rect)) {
            return;
        }
        int min = java.lang.Math.min(i, rect.width());
        int i4 = 0;
        if (rect.right < rect2.left + min) {
            i3 = min - (rect.right - rect2.left);
        } else if (rect.left <= rect2.right - min) {
            i3 = 0;
        } else {
            i3 = -(min - (rect2.right - rect.left));
        }
        int min2 = java.lang.Math.min(i2, rect.width());
        if (rect.bottom < rect2.top + min2) {
            i4 = min2 - (rect.bottom - rect2.top);
        } else if (rect.top > rect2.bottom - min2) {
            i4 = -(min2 - (rect2.bottom - rect.top));
        }
        rect.offset(i3, i4);
    }

    private boolean shouldStartChangeTransition(int i, @android.annotation.NonNull android.graphics.Rect rect) {
        if ((!isLeafTask() && !this.mCreatedByOrganizer) || !canStartChangeTransition()) {
            return false;
        }
        int windowingMode = getWindowingMode();
        if (!this.mTransitionController.inTransition(this)) {
            return (i == 5) != (windowingMode == 5);
        }
        android.graphics.Rect bounds = getConfiguration().windowConfiguration.getBounds();
        return (i == windowingMode && rect.width() == bounds.width() && rect.height() == bounds.height()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    void migrateToNewSurfaceControl(android.view.SurfaceControl.Transaction transaction) {
        super.migrateToNewSurfaceControl(transaction);
        this.mLastSurfaceSize.x = 0;
        this.mLastSurfaceSize.y = 0;
        updateSurfaceSize(transaction);
    }

    void updateSurfaceSize(android.view.SurfaceControl.Transaction transaction) {
        int i;
        int i2;
        if (this.mSurfaceControl == null || isOrganized()) {
            return;
        }
        if (!isRootTask()) {
            i = 0;
            i2 = 0;
        } else {
            android.graphics.Rect bounds = getBounds();
            i = bounds.width();
            i2 = bounds.height();
        }
        if (i == this.mLastSurfaceSize.x && i2 == this.mLastSurfaceSize.y) {
            return;
        }
        transaction.setWindowCrop(this.mSurfaceControl, i, i2);
        this.mLastSurfaceSize.set(i, i2);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Point getLastSurfaceSize() {
        return this.mLastSurfaceSize;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isInChangeTransition() {
        return this.mSurfaceFreezer.hasLeash() || com.android.server.wm.AppTransition.isChangeTransitOld(this.mTransit);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceFreezer.Freezable
    public android.view.SurfaceControl getFreezeSnapshotTarget() {
        if (!this.mDisplayContent.mAppTransition.containsTransitRequest(6)) {
            return null;
        }
        android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        arraySet.add(java.lang.Integer.valueOf(getActivityType()));
        android.view.RemoteAnimationAdapter remoteAnimationOverride = this.mDisplayContent.mAppTransitionController.getRemoteAnimationOverride(this, 27, arraySet);
        if (remoteAnimationOverride == null || remoteAnimationOverride.getChangeNeedsSnapshot()) {
            return getSurfaceControl();
        }
        return null;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void writeIdentifierToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mUserId);
        protoOutputStream.write(1138166333443L, (this.intent == null || this.intent.getComponent() == null) ? "Task" : this.intent.getComponent().flattenToShortString());
        protoOutputStream.end(start);
    }

    private void saveLaunchingStateIfNeeded() {
        saveLaunchingStateIfNeeded(getDisplayContent());
    }

    private void saveLaunchingStateIfNeeded(com.android.server.wm.DisplayContent displayContent) {
        if (!isLeafTask() || !getHasBeenVisible()) {
            return;
        }
        int windowingMode = getWindowingMode();
        if ((windowingMode != 1 && windowingMode != 5) || getTaskDisplayArea() == null || getTaskDisplayArea().getWindowingMode() != 5) {
            return;
        }
        this.mTaskSupervisor.mLaunchParamsPersister.saveTask(this, displayContent);
    }

    android.graphics.Rect updateOverrideConfigurationFromLaunchBounds() {
        com.android.server.wm.Task rootTask = getRootTask();
        android.graphics.Rect launchBounds = (rootTask == this || !rootTask.isOrganized()) ? getLaunchBounds() : null;
        setBounds(launchBounds);
        if (launchBounds != null && !launchBounds.isEmpty()) {
            launchBounds.set(getRequestedOverrideBounds());
        }
        return launchBounds;
    }

    android.graphics.Rect getLaunchBounds() {
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask == null) {
            return null;
        }
        int windowingMode = getWindowingMode();
        if (!isActivityTypeStandardOrUndefined() || windowingMode == 1) {
            if (isResizeable()) {
                return rootTask.getRequestedOverrideBounds();
            }
            return null;
        }
        if (!getWindowConfiguration().persistTaskBounds()) {
            return rootTask.getRequestedOverrideBounds();
        }
        return this.mLastNonFullscreenBounds;
    }

    void setRootProcess(com.android.server.wm.WindowProcessController windowProcessController) {
        clearRootProcess();
        if (this.intent != null && (this.intent.getFlags() & 8388608) == 0) {
            this.mRootProcess = windowProcessController;
            this.mRootProcess.addRecentTask(this);
        }
    }

    void clearRootProcess() {
        if (this.mRootProcess != null) {
            this.mRootProcess.removeRecentTask(this);
            this.mRootProcess = null;
        }
    }

    int getRootTaskId() {
        return getRootTask().mTaskId;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getOrganizedTask() {
        com.android.server.wm.Task asTask;
        if (isOrganized()) {
            return this;
        }
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null || (asTask = parent.asTask()) == null) {
            return null;
        }
        return asTask.getOrganizedTask();
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getCreatedByOrganizerTask() {
        com.android.server.wm.Task asTask;
        if (this.mCreatedByOrganizer) {
            return this;
        }
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null || (asTask = parent.asTask()) == null) {
            return null;
        }
        return asTask.getCreatedByOrganizerTask();
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getAdjacentTask() {
        com.android.server.wm.TaskFragment adjacentTaskFragment = getAdjacentTaskFragment();
        if (adjacentTaskFragment != null && adjacentTaskFragment.asTask() != null) {
            return adjacentTaskFragment.asTask();
        }
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null || parent.asTask() == null) {
            return null;
        }
        return parent.asTask().getAdjacentTask();
    }

    boolean isRootTask() {
        return getRootTask() == this;
    }

    boolean isLeafTask() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask() != null) {
                return false;
            }
        }
        return true;
    }

    public com.android.server.wm.Task getTopLeafTask() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask();
            if (asTask != null) {
                return asTask.getTopLeafTask();
            }
        }
        return this;
    }

    int getDescendantTaskCount() {
        final int[] iArr = {0};
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$getDescendantTaskCount$5(iArr, (com.android.server.wm.Task) obj);
            }
        }, false);
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDescendantTaskCount$5(int[] iArr, com.android.server.wm.Task task) {
        iArr[0] = iArr[0] + 1;
    }

    com.android.server.wm.Task adjustFocusToNextFocusableTask(java.lang.String str) {
        return adjustFocusToNextFocusableTask(str, false, true);
    }

    private com.android.server.wm.Task getNextFocusableTask(final boolean z) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null) {
            return null;
        }
        com.android.server.wm.Task task = parent.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getNextFocusableTask$6;
                lambda$getNextFocusableTask$6 = com.android.server.wm.Task.this.lambda$getNextFocusableTask$6(z, obj);
                return lambda$getNextFocusableTask$6;
            }
        });
        if (task == null && parent.asTask() != null) {
            return parent.asTask().getNextFocusableTask(z);
        }
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getNextFocusableTask$6(boolean z, java.lang.Object obj) {
        return (z || obj != this) && ((com.android.server.wm.Task) obj).isFocusableAndVisible();
    }

    com.android.server.wm.Task adjustFocusToNextFocusableTask(java.lang.String str, boolean z, boolean z2) {
        com.android.server.wm.Task nextFocusableTask = getNextFocusableTask(z);
        if (nextFocusableTask == null) {
            nextFocusableTask = this.mRootWindowContainer.getNextFocusableRootTask(this, !z);
        }
        if (nextFocusableTask == null) {
            com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
            if (displayArea != null) {
                displayArea.clearPreferredTopFocusableRootTask();
                return null;
            }
            return null;
        }
        com.android.server.wm.Task rootTask = nextFocusableTask.getRootTask();
        if (!z2) {
            com.android.server.wm.WindowContainer parent = nextFocusableTask.getParent();
            do {
                com.android.server.wm.Task task = nextFocusableTask;
                nextFocusableTask = parent;
                nextFocusableTask.positionChildAt(Integer.MAX_VALUE, task, false);
                parent = nextFocusableTask.getParent();
                if (nextFocusableTask.asTask() == null) {
                    break;
                }
            } while (parent != null);
            return rootTask;
        }
        java.lang.String str2 = str + " adjustFocusToNextFocusableTask";
        com.android.server.wm.ActivityRecord activityRecord = nextFocusableTask.topRunningActivity();
        if (nextFocusableTask.isActivityTypeHome() && (activityRecord == null || !activityRecord.isVisibleRequested())) {
            nextFocusableTask.getDisplayArea().moveHomeActivityToTop(str2);
            return rootTask;
        }
        nextFocusableTask.moveToFront(str2);
        if (rootTask.getTopResumedActivity() != null) {
            this.mTaskSupervisor.updateTopResumedActivityIfNeeded(str);
        }
        return rootTask;
    }

    private int computeMinUserPosition(int i, int i2) {
        while (i < i2 && !((com.android.server.wm.WindowContainer) this.mChildren.get(i)).showToCurrentUser()) {
            i++;
        }
        return i;
    }

    private int computeMaxUserPosition(int i) {
        while (i > 0 && ((com.android.server.wm.WindowContainer) this.mChildren.get(i)).showToCurrentUser()) {
            i--;
        }
        return i;
    }

    private int getAdjustedChildPosition(com.android.server.wm.WindowContainer windowContainer, int i) {
        int i2;
        boolean showToCurrentUser = windowContainer.showToCurrentUser();
        int size = this.mChildren.size();
        int computeMinUserPosition = showToCurrentUser ? computeMinUserPosition(0, size) : 0;
        if (size <= 0) {
            i2 = computeMinUserPosition;
        } else {
            i2 = showToCurrentUser ? size - 1 : computeMaxUserPosition(size - 1);
        }
        if (!windowContainer.isAlwaysOnTop()) {
            while (i2 > computeMinUserPosition && ((com.android.server.wm.WindowContainer) this.mChildren.get(i2)).isAlwaysOnTop()) {
                i2--;
            }
        }
        if (i == Integer.MIN_VALUE && computeMinUserPosition == 0) {
            return Integer.MIN_VALUE;
        }
        if (i == Integer.MAX_VALUE && i2 >= size - 1) {
            return Integer.MAX_VALUE;
        }
        if (!hasChild(windowContainer)) {
            i2++;
        }
        return java.lang.Math.min(java.lang.Math.max(i, computeMinUserPosition), i2);
    }

    @Override // com.android.server.wm.WindowContainer
    void positionChildAt(int i, com.android.server.wm.WindowContainer windowContainer, boolean z) {
        boolean z2 = i >= this.mChildren.size() - 1;
        int adjustedChildPosition = getAdjustedChildPosition(windowContainer, i);
        super.positionChildAt(adjustedChildPosition, windowContainer, z);
        com.android.server.wm.Task asTask = windowContainer.asTask();
        if (asTask != null) {
            asTask.updateTaskMovement(z2, adjustedChildPosition == Integer.MIN_VALUE, adjustedChildPosition);
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void removeImmediately() {
        removeImmediately("removeTask");
    }

    @Override // com.android.server.wm.TaskFragment
    void removeImmediately(java.lang.String str) {
        if (this.mRemoving) {
            return;
        }
        this.mRemoving = true;
        com.android.server.wm.EventLogTags.writeWmTaskRemoved(this.mTaskId, getRootTaskId(), getDisplayId(), str);
        clearPinnedTaskIfNeed();
        if (this.mChildPipActivity != null) {
            this.mChildPipActivity.clearLastParentBeforePip();
        }
        setTaskOrganizer(null);
        if (this.mDecorSurfaceContainer != null) {
            this.mDecorSurfaceContainer.release();
        }
        super.removeImmediately();
        this.mRemoving = false;
    }

    void reparent(com.android.server.wm.Task task, int i, boolean z, java.lang.String str) {
        com.android.server.wm.EventLogTags.writeWmTaskRemoved(this.mTaskId, getRootTaskId(), getDisplayId(), "reParentTask:" + str);
        reparent(task, i);
        task.positionChildAt(i, this, z);
    }

    public int setBounds(android.graphics.Rect rect, boolean z) {
        int bounds = setBounds(rect);
        if (z && (bounds & 2) != 2) {
            onResize();
            return bounds | 2;
        }
        return bounds;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public int setBounds(android.graphics.Rect rect) {
        int i;
        if (isRootTask()) {
            return setBounds(getRequestedOverrideBounds(), rect);
        }
        com.android.server.wm.DisplayContent displayContent = getRootTask() != null ? getRootTask().getDisplayContent() : null;
        if (displayContent == null) {
            i = 0;
        } else {
            i = displayContent.getDisplayInfo().rotation;
        }
        int bounds = super.setBounds(rect);
        this.mRotation = i;
        updateSurfacePositionNonOrganized();
        return bounds;
    }

    int setBoundsUnchecked(@android.annotation.NonNull android.graphics.Rect rect) {
        int bounds = super.setBounds(rect);
        updateSurfaceBounds();
        return bounds;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean isCompatible(int i, int i2) {
        if (i2 == 0) {
            i2 = 1;
        }
        return super.isCompatible(i, i2);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onDescendantOrientationChanged(com.android.server.wm.WindowContainer windowContainer) {
        if (super.onDescendantOrientationChanged(windowContainer)) {
            return true;
        }
        if (getParent() != null) {
            onConfigurationChanged(getParent().getConfiguration());
            return true;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handlesOrientationChangeFromDescendant(int i) {
        if (!super.handlesOrientationChangeFromDescendant(i)) {
            return false;
        }
        if (isLeafTask()) {
            return canSpecifyOrientation() && getDisplayArea().canSpecifyOrientation(i);
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        if (!isRootTask() && !this.mCreatedByOrganizer) {
            adjustBoundsForDisplayChangeIfNeeded(displayContent);
        }
        super.onDisplayChanged(displayContent);
        if (isLeafTask()) {
            this.mWmService.mAtmService.getTaskChangeNotificationController().notifyTaskDisplayChanged(this.mTaskId, displayContent != null ? displayContent.getDisplayId() : -1);
        }
        if (isRootTask()) {
            updateSurfaceBounds();
        }
        sendTaskFragmentParentInfoChangedIfNeeded();
    }

    boolean isResizeable() {
        return isResizeable(true);
    }

    boolean isResizeable(boolean z) {
        return (this.mAtmService.mForceResizableActivities && getActivityType() == 1) || android.content.pm.ActivityInfo.isResizeableMode(this.mResizeMode) || (this.mSupportsPictureInPicture && z);
    }

    boolean preserveOrientationOnResize() {
        return this.mResizeMode == 6 || this.mResizeMode == 5 || this.mResizeMode == 7;
    }

    boolean cropWindowsToRootTaskBounds() {
        if (isActivityTypeHomeOrRecents()) {
            com.android.server.wm.Task rootTask = getRootTask();
            if (rootTask.mCreatedByOrganizer) {
                rootTask = rootTask.getTopMostTask();
            }
            if (this == rootTask || isDescendantOf(rootTask)) {
                return false;
            }
        }
        return isResizeable();
    }

    @Override // com.android.server.wm.WindowContainer
    void getAnimationFrames(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        if (getAdjacentTask() != null) {
            super.getAnimationFrames(rect, rect2, rect3, rect4);
            return;
        }
        com.android.server.wm.WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow();
        if (topVisibleAppMainWindow != null) {
            topVisibleAppMainWindow.getAnimationFrames(rect, rect2, rect3, rect4);
        } else {
            super.getAnimationFrames(rect, rect2, rect3, rect4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getMaxVisibleBounds(com.android.server.wm.ActivityRecord activityRecord, android.graphics.Rect rect, boolean[] zArr) {
        com.android.server.wm.WindowState findMainWindow;
        if (activityRecord.mIsExiting || !activityRecord.isClientVisible() || !activityRecord.isVisibleRequested() || (findMainWindow = activityRecord.findMainWindow()) == null) {
            return;
        }
        if (!zArr[0]) {
            zArr[0] = true;
            rect.setEmpty();
        }
        android.graphics.Rect rect2 = sTmpBounds;
        android.view.WindowManager.LayoutParams layoutParams = findMainWindow.mAttrs;
        rect2.set(findMainWindow.getFrame());
        rect2.inset(findMainWindow.getInsetsStateWithVisibilityOverride().calculateVisibleInsets(rect2, layoutParams.type, findMainWindow.getActivityType(), layoutParams.softInputMode, layoutParams.flags));
        rect.union(rect2);
    }

    @Override // com.android.server.wm.TaskFragment
    void getDimBounds(@android.annotation.NonNull final android.graphics.Rect rect) {
        if (isRootTask()) {
            getBounds(rect);
            return;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        if (inFreeformWindowingMode()) {
            final boolean[] zArr = {false};
            forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda31
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.Task.getMaxVisibleBounds((com.android.server.wm.ActivityRecord) obj, rect, zArr);
                }
            });
            if (zArr[0]) {
                return;
            }
        }
        if (!matchParentBounds()) {
            rootTask.getBounds(this.mTmpRect);
            this.mTmpRect.intersect(getBounds());
            rect.set(this.mTmpRect);
            return;
        }
        rect.set(getBounds());
    }

    void adjustAnimationBoundsForTransition(android.graphics.Rect rect) {
        if (this.mWmService.mTaskTransitionSpec != null) {
            android.view.InsetsState rawInsetsState = getDisplayContent().getInsetsStateController().getRawInsetsState();
            for (int sourceSize = rawInsetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                android.view.InsetsSource sourceAt = rawInsetsState.sourceAt(sourceSize);
                if (sourceAt.hasFlags(2)) {
                    rect.inset(sourceAt.calculateVisibleInsets(rect));
                }
            }
        }
    }

    void setDragResizing(boolean z) {
        if (this.mDragResizing != z) {
            if (z && !getRootTask().getWindowConfiguration().canResizeTask()) {
                android.util.Slog.e("ActivityTaskManager", "Drag resize isn't allowed for root task id=" + getRootTaskId());
                return;
            }
            this.mDragResizing = z;
            resetDragResizingChangeReported();
        }
    }

    boolean isDragResizing() {
        return this.mDragResizing;
    }

    void adjustBoundsForDisplayChangeIfNeeded(com.android.server.wm.DisplayContent displayContent) {
        if (displayContent == null || getRequestedOverrideBounds().isEmpty()) {
            return;
        }
        int displayId = displayContent.getDisplayId();
        int i = displayContent.getDisplayInfo().rotation;
        if (displayId != this.mLastRotationDisplayId) {
            this.mLastRotationDisplayId = displayId;
            this.mRotation = i;
        } else {
            if (this.mRotation == i) {
                return;
            }
            this.mTmpRect2.set(getBounds());
            if (!getWindowConfiguration().canResizeTask()) {
                setBounds(this.mTmpRect2);
                return;
            }
            displayContent.rotateBounds(this.mRotation, i, this.mTmpRect2);
            if (setBounds(this.mTmpRect2) != 0) {
                this.mAtmService.resizeTask(this.mTaskId, getBounds(), 1);
            }
        }
    }

    void cancelTaskWindowTransition() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).cancelAnimation();
        }
    }

    boolean showForAllUsers() {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        return (this.mChildren.isEmpty() || (topNonFinishingActivity = getTopNonFinishingActivity()) == null || !topNonFinishingActivity.mShowForAllUsers) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showToCurrentUser() {
        return this.mForceShowForAllUsers || showForAllUsers() || this.mWmService.isUserVisible(getTopMostTask().mUserId);
    }

    void setForceShowForAllUsers(boolean z) {
        this.mForceShowForAllUsers = z;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getOccludingActivityAbove(final com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityRecord activity = getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getOccludingActivityAbove$8;
                lambda$getOccludingActivityAbove$8 = com.android.server.wm.Task.lambda$getOccludingActivityAbove$8(com.android.server.wm.ActivityRecord.this, (com.android.server.wm.ActivityRecord) obj);
                return lambda$getOccludingActivityAbove$8;
            }
        });
        if (activity != activityRecord) {
            return activity;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getOccludingActivityAbove$8(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        if (activityRecord2 == activityRecord) {
            return true;
        }
        if (!activityRecord2.occludesParent()) {
            return false;
        }
        com.android.server.wm.TaskFragment taskFragment = activityRecord2.getTaskFragment();
        if (taskFragment == activityRecord.getTaskFragment()) {
            return true;
        }
        if (taskFragment != null && taskFragment.asTask() != null) {
            return true;
        }
        com.android.server.wm.TaskFragment asTaskFragment = taskFragment.getParent().asTaskFragment();
        com.android.server.wm.TaskFragment taskFragment2 = taskFragment;
        while (asTaskFragment != null && taskFragment2.getBounds().equals(asTaskFragment.getBounds())) {
            if (asTaskFragment.asTask() != null) {
                return true;
            }
            com.android.server.wm.TaskFragment taskFragment3 = asTaskFragment;
            asTaskFragment = asTaskFragment.getParent().asTaskFragment();
            taskFragment2 = taskFragment3;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Builder makeAnimationLeash() {
        return super.makeAnimationLeash().setMetadata(3, this.mTaskId);
    }

    boolean shouldAnimate() {
        if (isOrganized()) {
            return false;
        }
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        return (recentsAnimationController != null && recentsAnimationController.isAnimatingTask(this) && recentsAnimationController.shouldDeferCancelUntilNextTransition()) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    void setInitialSurfaceControlProperties(android.view.SurfaceControl.Builder builder) {
        builder.setEffectLayer().setMetadata(3, this.mTaskId);
        super.setInitialSurfaceControlProperties(builder);
    }

    boolean isAnimatingByRecents() {
        return isAnimating(4, 8) || this.mTransitionController.isTransientHide(this);
    }

    com.android.server.wm.WindowState getTopVisibleAppMainWindow() {
        com.android.server.wm.ActivityRecord topVisibleActivity = getTopVisibleActivity();
        if (topVisibleActivity != null) {
            return topVisibleActivity.findMainWindow();
        }
        return null;
    }

    com.android.server.wm.ActivityRecord topRunningNonDelayedActivityLocked(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new java.util.function.BiPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda20
            @Override // java.util.function.BiPredicate
            public final boolean test(java.lang.Object obj, java.lang.Object obj2) {
                boolean isTopRunningNonDelayed;
                isTopRunningNonDelayed = com.android.server.wm.Task.isTopRunningNonDelayed((com.android.server.wm.ActivityRecord) obj, (com.android.server.wm.ActivityRecord) obj2);
                return isTopRunningNonDelayed;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), activityRecord);
        com.android.server.wm.ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTopRunningNonDelayed(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return (activityRecord.delayedResume || activityRecord == activityRecord2 || !activityRecord.canBeTopRunning()) ? false : true;
    }

    com.android.server.wm.ActivityRecord topRunningActivity(android.os.IBinder iBinder, int i) {
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.internal.util.function.TriPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda9
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                boolean isTopRunning;
                isTopRunning = com.android.server.wm.Task.isTopRunning((com.android.server.wm.ActivityRecord) obj, ((java.lang.Integer) obj2).intValue(), (android.os.IBinder) obj3);
                return isTopRunning;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), java.lang.Integer.valueOf(i), iBinder);
        com.android.server.wm.ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTopRunning(com.android.server.wm.ActivityRecord activityRecord, int i, android.os.IBinder iBinder) {
        return (activityRecord.getTask().mTaskId == i || activityRecord.token == iBinder || !activityRecord.canBeTopRunning()) ? false : true;
    }

    com.android.server.wm.ActivityRecord getTopFullscreenActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopFullscreenActivity$9;
                lambda$getTopFullscreenActivity$9 = com.android.server.wm.Task.lambda$getTopFullscreenActivity$9((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopFullscreenActivity$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopFullscreenActivity$9(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow();
        return findMainWindow != null && findMainWindow.mAttrs.isFullscreen();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopVisibleActivity$10(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.mIsExiting && activityRecord.isClientVisible() && activityRecord.isVisibleRequested();
    }

    com.android.server.wm.ActivityRecord getTopVisibleActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopVisibleActivity$10;
                lambda$getTopVisibleActivity$10 = com.android.server.wm.Task.lambda$getTopVisibleActivity$10((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopVisibleActivity$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopRealVisibleActivity$11(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.mIsExiting && activityRecord.isClientVisible() && activityRecord.isVisible();
    }

    com.android.server.wm.ActivityRecord getTopRealVisibleActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopRealVisibleActivity$11;
                lambda$getTopRealVisibleActivity$11 = com.android.server.wm.Task.lambda$getTopRealVisibleActivity$11((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopRealVisibleActivity$11;
            }
        });
    }

    com.android.server.wm.ActivityRecord getTopWaitSplashScreenActivity() {
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopWaitSplashScreenActivity$12;
                lambda$getTopWaitSplashScreenActivity$12 = com.android.server.wm.Task.lambda$getTopWaitSplashScreenActivity$12((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopWaitSplashScreenActivity$12;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopWaitSplashScreenActivity$12(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.mHandleExitSplashScreen && activityRecord.mTransferringSplashScreenState == 1;
    }

    void setTaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
        this.mTaskDescription = taskDescription;
    }

    void onSnapshotChanged(android.window.TaskSnapshot taskSnapshot) {
        this.mLastTaskSnapshotData.set(taskSnapshot);
        this.mAtmService.getTaskChangeNotificationController().notifyTaskSnapshotChanged(this.mTaskId, taskSnapshot);
    }

    android.app.ActivityManager.TaskDescription getTaskDescription() {
        return this.mTaskDescription;
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllLeafTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        int size = this.mChildren.size();
        boolean z2 = true;
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(i)).asTask();
                if (asTask != null) {
                    asTask.forAllLeafTasks(consumer, z);
                    z2 = false;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.wm.Task asTask2 = ((com.android.server.wm.WindowContainer) this.mChildren.get(i2)).asTask();
                if (asTask2 != null) {
                    asTask2.forAllLeafTasks(consumer, z);
                    z2 = false;
                }
            }
        }
        if (z2) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        super.forAllTasks(consumer, z);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllRootTasks(java.util.function.Consumer<com.android.server.wm.Task> consumer, boolean z) {
        if (isRootTask()) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        if (super.forAllTasks(predicate)) {
            return true;
        }
        return predicate.test(this);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllLeafTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate) {
        boolean z = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task asTask = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTask();
            if (asTask != null) {
                if (asTask.forAllLeafTasks(predicate)) {
                    return true;
                }
                z = false;
            }
        }
        if (z) {
            return predicate.test(this);
        }
        return false;
    }

    void forAllLeafTasksAndLeafTaskFragments(final java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer, final boolean z) {
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$forAllLeafTasksAndLeafTaskFragments$13(consumer, z, (com.android.server.wm.Task) obj);
            }
        }, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$forAllLeafTasksAndLeafTaskFragments$13(java.util.function.Consumer consumer, boolean z, com.android.server.wm.Task task) {
        if (task.isLeafTaskFragment()) {
            consumer.accept(task);
            return;
        }
        int i = 0;
        if (z) {
            for (int size = task.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) task.mChildren.get(size);
                if (windowContainer.asTaskFragment() != null) {
                    windowContainer.forAllLeafTaskFragments(consumer, z);
                } else if (windowContainer.asActivityRecord() != null && i == 0) {
                    consumer.accept(task);
                    i = 1;
                }
            }
            return;
        }
        boolean z2 = false;
        while (i < task.mChildren.size()) {
            com.android.server.wm.WindowContainer windowContainer2 = (com.android.server.wm.WindowContainer) task.mChildren.get(i);
            if (windowContainer2.asTaskFragment() != null) {
                windowContainer2.forAllLeafTaskFragments(consumer, z);
            } else if (windowContainer2.asActivityRecord() != null && !z2) {
                consumer.accept(task);
                z2 = true;
            }
            i++;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllRootTasks(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (isRootTask()) {
            return predicate.test(this);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.Task getTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        com.android.server.wm.Task task = super.getTask(predicate, z);
        if (task != null) {
            return task;
        }
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(java.util.function.Predicate<com.android.server.wm.Task> predicate, boolean z) {
        if (isRootTask() && predicate.test(this)) {
            return this;
        }
        return null;
    }

    void setCanAffectSystemUiFlags(boolean z) {
        this.mCanAffectSystemUiFlags = z;
    }

    boolean canAffectSystemUiFlags() {
        return this.mCanAffectSystemUiFlags;
    }

    void dontAnimateDimExit() {
        this.mDimmer.dontAnimateExit();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return "Task=" + this.mTaskId;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    com.android.server.wm.Dimmer getDimmer() {
        if (inMultiWindowMode()) {
            return this.mDimmer;
        }
        if (!isRootTask() || ((com.android.server.wm.Dimmer.DIMMER_REFACTOR && isTranslucentAndVisible()) || isTranslucent(null))) {
            return super.getDimmer();
        }
        return this.mDimmer;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void prepareSurfaces() {
        this.mDimmer.resetDimStates();
        super.prepareSurfaces();
        android.graphics.Rect dimBounds = this.mDimmer.getDimBounds();
        if (dimBounds != null) {
            getDimBounds(dimBounds);
            if (inFreeformWindowingMode()) {
                getBounds(this.mTmpRect);
                dimBounds.offsetTo(dimBounds.left - this.mTmpRect.left, dimBounds.top - this.mTmpRect.top);
            } else {
                dimBounds.offsetTo(0, 0);
            }
        }
        android.view.SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (dimBounds != null && this.mDimmer.updateDims(syncTransaction)) {
            scheduleAnimation();
        }
        if (this.mTransitionController.isCollecting() && this.mCreatedByOrganizer) {
            return;
        }
        boolean isVisible = isVisible();
        boolean z = isVisible || isAnimating(7);
        if (this.mSurfaceControl != null && z != this.mLastSurfaceShowing) {
            syncTransaction.setVisibility(this.mSurfaceControl, z);
        }
        if (this.mOverlayHost != null) {
            this.mOverlayHost.setVisibility(syncTransaction, isVisible);
        }
        this.mLastSurfaceShowing = z;
    }

    @Override // com.android.server.wm.WindowContainer
    protected void applyAnimationUnchecked(android.view.WindowManager.LayoutParams layoutParams, boolean z, int i, boolean z2, @android.annotation.Nullable final java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        if (recentsAnimationController != null) {
            if (z && !isActivityTypeHomeOrRecents()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3401780415681318335L, 0, null, java.lang.String.valueOf(recentsAnimationController), java.lang.String.valueOf(asTask()), java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(i)));
                final int size = arrayList != null ? arrayList.size() : 0;
                recentsAnimationController.addTaskToTargets(this, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda2
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(int i2, com.android.server.wm.AnimationAdapter animationAdapter) {
                        com.android.server.wm.Task.lambda$applyAnimationUnchecked$14(size, arrayList, i2, animationAdapter);
                    }
                });
                return;
            }
            return;
        }
        super.applyAnimationUnchecked(layoutParams, z, i, z2, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyAnimationUnchecked$14(int i, java.util.ArrayList arrayList, int i2, com.android.server.wm.AnimationAdapter animationAdapter) {
        for (int i3 = 0; i3 < i; i3++) {
            ((com.android.server.wm.WindowContainer) arrayList.get(i3)).onAnimationFinished(i2, animationAdapter);
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        super.dump(printWriter, str, z);
        this.mAnimatingActivityRegistry.dump(printWriter, "AnimatingApps:", str);
    }

    void fillTaskInfo(android.app.TaskInfo taskInfo) {
        fillTaskInfo(taskInfo, true);
    }

    void fillTaskInfo(android.app.TaskInfo taskInfo, boolean z) {
        fillTaskInfo(taskInfo, z, getDisplayArea());
    }

    void fillTaskInfo(android.app.TaskInfo taskInfo, boolean z, @android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        android.content.Intent cloneFilter;
        boolean z2;
        int i;
        int i2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        int i3;
        int i4;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        taskInfo.launchCookies.clear();
        taskInfo.addLaunchCookie(this.mLaunchCookie);
        com.android.server.wm.ActivityRecord fillAndReturnTop = this.mTaskSupervisor.mTaskInfoHelper.fillAndReturnTop(this, taskInfo);
        taskInfo.userId = isLeafTask() ? this.mUserId : this.mCurrentUser;
        taskInfo.taskId = this.mTaskId;
        taskInfo.displayId = getDisplayId();
        taskInfo.displayAreaFeatureId = taskDisplayArea != null ? taskDisplayArea.mFeatureId : -1;
        android.content.Intent baseIntent = getBaseIntent();
        boolean z14 = false;
        int flags = baseIntent == null ? 0 : baseIntent.getFlags();
        if (baseIntent == null) {
            cloneFilter = new android.content.Intent();
        } else {
            cloneFilter = z ? baseIntent.cloneFilter() : new android.content.Intent(baseIntent);
        }
        taskInfo.baseIntent = cloneFilter;
        taskInfo.baseIntent.setFlags(flags);
        if (fillAndReturnTop == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        taskInfo.isRunning = z2;
        taskInfo.topActivity = fillAndReturnTop != null ? fillAndReturnTop.mActivityComponent : null;
        taskInfo.origActivity = this.origActivity;
        taskInfo.realActivity = this.realActivity;
        taskInfo.lastActiveTime = this.lastActiveTime;
        taskInfo.taskDescription = new android.app.ActivityManager.TaskDescription(getTaskDescription());
        taskInfo.supportsMultiWindow = supportsMultiWindowInDisplayArea(taskDisplayArea);
        taskInfo.configuration.setTo(getConfiguration());
        taskInfo.configuration.windowConfiguration.setActivityType(getActivityType());
        taskInfo.configuration.windowConfiguration.setWindowingMode(getWindowingMode());
        taskInfo.token = this.mRemoteToken.toWindowContainerToken();
        com.android.server.wm.Task task = fillAndReturnTop != null ? fillAndReturnTop.getTask() : this;
        taskInfo.resizeMode = task.mResizeMode;
        taskInfo.topActivityType = task.getActivityType();
        taskInfo.displayCutoutInsets = task.getDisplayCutoutInsets();
        taskInfo.isResizeable = isResizeable();
        taskInfo.minWidth = this.mMinWidth;
        taskInfo.minHeight = this.mMinHeight;
        taskInfo.defaultMinSize = this.mDisplayContent == null ? 220 : this.mDisplayContent.mMinSizeOfResizeableTaskDp;
        taskInfo.positionInParent = getRelativePosition();
        taskInfo.topActivityInfo = fillAndReturnTop != null ? fillAndReturnTop.info : null;
        taskInfo.pictureInPictureParams = getPictureInPictureParams(fillAndReturnTop);
        if (taskInfo.pictureInPictureParams == null || !taskInfo.pictureInPictureParams.isLaunchIntoPip() || fillAndReturnTop.getLastParentBeforePip() == null) {
            i = -1;
        } else {
            i = fillAndReturnTop.getLastParentBeforePip().mTaskId;
        }
        taskInfo.launchIntoPipHostTaskId = i;
        if (fillAndReturnTop == null || fillAndReturnTop.getLastParentBeforePip() == null) {
            i2 = -1;
        } else {
            i2 = fillAndReturnTop.getLastParentBeforePip().mTaskId;
        }
        taskInfo.lastParentTaskIdBeforePip = i2;
        if (fillAndReturnTop == null || !fillAndReturnTop.shouldDockBigOverlays) {
            z3 = false;
        } else {
            z3 = true;
        }
        taskInfo.shouldDockBigOverlays = z3;
        taskInfo.mTopActivityLocusId = fillAndReturnTop != null ? fillAndReturnTop.getLocusId() : null;
        if (fillAndReturnTop == null || fillAndReturnTop.getOrganizedTask() != this || !fillAndReturnTop.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (fillAndReturnTop == null || fillAndReturnTop.getOrganizedTask() != this || !fillAndReturnTop.isVisible()) {
            z5 = false;
        } else {
            z5 = true;
        }
        android.app.AppCompatTaskInfo appCompatTaskInfo = taskInfo.appCompatTaskInfo;
        if (!z5 || !fillAndReturnTop.inSizeCompatMode()) {
            z6 = false;
        } else {
            z6 = true;
        }
        appCompatTaskInfo.topActivityInSizeCompat = z6;
        if (appCompatTaskInfo.topActivityInSizeCompat && this.mWmService.mLetterboxConfiguration.isTranslucentLetterboxingEnabled()) {
            appCompatTaskInfo.topActivityInSizeCompat = fillAndReturnTop.fillsParent();
        }
        if (!z4 || !fillAndReturnTop.isEligibleForLetterboxEducation()) {
            z7 = false;
        } else {
            z7 = true;
        }
        appCompatTaskInfo.topActivityEligibleForLetterboxEducation = z7;
        if (z4) {
            i3 = fillAndReturnTop.getCameraCompatControlState();
        } else {
            i3 = 0;
        }
        appCompatTaskInfo.cameraCompatControlState = i3;
        com.android.server.wm.Task asTask = getParent() != null ? getParent().asTask() : null;
        if (asTask != null && asTask.mCreatedByOrganizer) {
            i4 = asTask.mTaskId;
        } else {
            i4 = -1;
        }
        taskInfo.parentTaskId = i4;
        taskInfo.isFocused = isFocused();
        taskInfo.isVisible = hasVisibleChildren();
        taskInfo.isVisibleRequested = isVisibleRequested();
        taskInfo.isSleeping = shouldSleepActivities();
        if (fillAndReturnTop == null || fillAndReturnTop.fillsParent()) {
            z8 = false;
        } else {
            z8 = true;
        }
        taskInfo.isTopActivityTransparent = z8;
        if (fillAndReturnTop == null || !fillAndReturnTop.mLetterboxUiController.isLetterboxDoubleTapEducationEnabled()) {
            z9 = false;
        } else {
            z9 = true;
        }
        appCompatTaskInfo.isLetterboxDoubleTapEnabled = z9;
        appCompatTaskInfo.topActivityLetterboxVerticalPosition = -1;
        appCompatTaskInfo.topActivityLetterboxHorizontalPosition = -1;
        appCompatTaskInfo.topActivityLetterboxWidth = -1;
        appCompatTaskInfo.topActivityLetterboxHeight = -1;
        if (fillAndReturnTop == null || !fillAndReturnTop.mLetterboxUiController.shouldApplyUserFullscreenOverride()) {
            z10 = false;
        } else {
            z10 = true;
        }
        appCompatTaskInfo.isUserFullscreenOverrideEnabled = z10;
        if (fillAndReturnTop == null || !fillAndReturnTop.mLetterboxUiController.isSystemOverrideToFullscreenEnabled()) {
            z11 = false;
        } else {
            z11 = true;
        }
        appCompatTaskInfo.isSystemFullscreenOverrideEnabled = z11;
        if (fillAndReturnTop == null || !fillAndReturnTop.mLetterboxUiController.isFromDoubleTap()) {
            z12 = false;
        } else {
            z12 = true;
        }
        appCompatTaskInfo.isFromLetterboxDoubleTap = z12;
        if (fillAndReturnTop != null) {
            appCompatTaskInfo.topActivityLetterboxWidth = fillAndReturnTop.getBounds().width();
            appCompatTaskInfo.topActivityLetterboxHeight = fillAndReturnTop.getBounds().height();
        }
        if (appCompatTaskInfo.isLetterboxDoubleTapEnabled) {
            if (appCompatTaskInfo.isTopActivityPillarboxed()) {
                appCompatTaskInfo.topActivityLetterboxHorizontalPosition = fillAndReturnTop.mLetterboxUiController.getLetterboxPositionForHorizontalReachability();
            } else {
                appCompatTaskInfo.topActivityLetterboxVerticalPosition = fillAndReturnTop.mLetterboxUiController.getLetterboxPositionForVerticalReachability();
            }
        }
        if (fillAndReturnTop == null || appCompatTaskInfo.topActivityInSizeCompat || !fillAndReturnTop.mLetterboxUiController.shouldEnableUserAspectRatioSettings() || taskInfo.isTopActivityTransparent) {
            z13 = false;
        } else {
            z13 = true;
        }
        appCompatTaskInfo.topActivityEligibleForUserAspectRatioButton = z13;
        if (fillAndReturnTop != null && fillAndReturnTop.areBoundsLetterboxed()) {
            z14 = true;
        }
        appCompatTaskInfo.topActivityBoundsLetterboxed = z14;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$trimIneffectiveInfo$15(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    static void trimIneffectiveInfo(com.android.server.wm.Task task, android.app.TaskInfo taskInfo) {
        com.android.server.wm.ActivityRecord activity = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$trimIneffectiveInfo$15;
                lambda$trimIneffectiveInfo$15 = com.android.server.wm.Task.lambda$trimIneffectiveInfo$15((com.android.server.wm.ActivityRecord) obj);
                return lambda$trimIneffectiveInfo$15;
            }
        }, false);
        int uid = activity != null ? activity.getUid() : task.effectiveUid;
        if (taskInfo.topActivityInfo != null && task.effectiveUid != taskInfo.topActivityInfo.applicationInfo.uid) {
            taskInfo.topActivityInfo = new android.content.pm.ActivityInfo(taskInfo.topActivityInfo);
            taskInfo.topActivityInfo.applicationInfo = new android.content.pm.ApplicationInfo(taskInfo.topActivityInfo.applicationInfo);
            taskInfo.topActivity = new android.content.ComponentName("", "");
            taskInfo.topActivityInfo.packageName = "";
            taskInfo.topActivityInfo.taskAffinity = "";
            taskInfo.topActivityInfo.processName = "";
            taskInfo.topActivityInfo.name = "";
            taskInfo.topActivityInfo.parentActivityName = "";
            taskInfo.topActivityInfo.targetActivity = "";
            taskInfo.topActivityInfo.splitName = "";
            taskInfo.topActivityInfo.applicationInfo.className = "";
            taskInfo.topActivityInfo.applicationInfo.credentialProtectedDataDir = "";
            taskInfo.topActivityInfo.applicationInfo.dataDir = "";
            taskInfo.topActivityInfo.applicationInfo.deviceProtectedDataDir = "";
            taskInfo.topActivityInfo.applicationInfo.manageSpaceActivityName = "";
            taskInfo.topActivityInfo.applicationInfo.nativeLibraryDir = "";
            taskInfo.topActivityInfo.applicationInfo.nativeLibraryRootDir = "";
            taskInfo.topActivityInfo.applicationInfo.processName = "";
            taskInfo.topActivityInfo.applicationInfo.publicSourceDir = "";
            taskInfo.topActivityInfo.applicationInfo.scanPublicSourceDir = "";
            taskInfo.topActivityInfo.applicationInfo.scanSourceDir = "";
            taskInfo.topActivityInfo.applicationInfo.sourceDir = "";
            taskInfo.topActivityInfo.applicationInfo.taskAffinity = "";
            taskInfo.topActivityInfo.applicationInfo.name = "";
            taskInfo.topActivityInfo.applicationInfo.packageName = "";
        }
        if (task.effectiveUid != uid) {
            taskInfo.baseActivity = new android.content.ComponentName("", "");
        }
    }

    @android.annotation.Nullable
    android.app.PictureInPictureParams getPictureInPictureParams() {
        com.android.server.wm.Task topMostTask = getTopMostTask();
        if (topMostTask == null) {
            return null;
        }
        return getPictureInPictureParams(topMostTask.getTopMostActivity());
    }

    @android.annotation.Nullable
    private static android.app.PictureInPictureParams getPictureInPictureParams(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || activityRecord.pictureInPictureArgs.empty()) {
            return null;
        }
        return new android.app.PictureInPictureParams(activityRecord.pictureInPictureArgs);
    }

    android.graphics.Rect getDisplayCutoutInsets() {
        int i;
        if (this.mDisplayContent == null || getDisplayInfo().displayCutout == null) {
            return null;
        }
        com.android.server.wm.WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow();
        if (topVisibleAppMainWindow == null) {
            i = 0;
        } else {
            i = topVisibleAppMainWindow.getAttrs().layoutInDisplayCutoutMode;
        }
        if (i == 3 || i == 1) {
            return null;
        }
        return getDisplayInfo().displayCutout.getSafeInsets();
    }

    android.app.ActivityManager.RunningTaskInfo getTaskInfo() {
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = new android.app.ActivityManager.RunningTaskInfo();
        fillTaskInfo(runningTaskInfo);
        return runningTaskInfo;
    }

    android.window.StartingWindowInfo getStartingWindowInfo(com.android.server.wm.ActivityRecord activityRecord) {
        int i;
        com.android.server.wm.WindowState topFullscreenOpaqueWindow;
        com.android.server.wm.WindowState window;
        android.window.StartingWindowInfo startingWindowInfo = new android.window.StartingWindowInfo();
        startingWindowInfo.taskInfo = getTaskInfo();
        startingWindowInfo.targetActivityInfo = (startingWindowInfo.taskInfo.topActivityInfo == null || activityRecord.info == startingWindowInfo.taskInfo.topActivityInfo) ? null : activityRecord.info;
        startingWindowInfo.isKeyguardOccluded = this.mAtmService.mKeyguardController.isKeyguardOccluded(startingWindowInfo.taskInfo.displayId);
        if (activityRecord.mStartingData != null) {
            i = activityRecord.mStartingData.mTypeParams;
        } else {
            i = 272;
        }
        startingWindowInfo.startingWindowTypeParameter = i;
        if ((startingWindowInfo.startingWindowTypeParameter & 16) != 0 && (window = getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getStartingWindowInfo$16;
                lambda$getStartingWindowInfo$16 = com.android.server.wm.Task.lambda$getStartingWindowInfo$16((com.android.server.wm.WindowState) obj);
                return lambda$getStartingWindowInfo$16;
            }
        })) != null) {
            startingWindowInfo.mainWindowLayoutParams = window.getAttrs();
            startingWindowInfo.requestedVisibleTypes = window.getRequestedVisibleTypes();
        }
        startingWindowInfo.taskInfo.configuration.setTo(activityRecord.getConfiguration());
        com.android.server.wm.ActivityRecord topFullscreenActivity = getTopFullscreenActivity();
        if (topFullscreenActivity != null && (topFullscreenOpaqueWindow = topFullscreenActivity.getTopFullscreenOpaqueWindow()) != null) {
            startingWindowInfo.topOpaqueWindowInsetsState = topFullscreenOpaqueWindow.getInsetsStateWithVisibilityOverride();
            startingWindowInfo.topOpaqueWindowLayoutParams = topFullscreenOpaqueWindow.getAttrs();
        }
        return startingWindowInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getStartingWindowInfo$16(com.android.server.wm.WindowState windowState) {
        return windowState.mAttrs.type == 1;
    }

    android.window.TaskFragmentParentInfo getTaskFragmentParentInfo() {
        return new android.window.TaskFragmentParentInfo(getConfiguration(), getDisplayId(), shouldBeVisible(null), hasNonFinishingDirectActivity(), getDecorSurface());
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    protected boolean onChildVisibleRequestedChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        if (!super.onChildVisibleRequestedChanged(windowContainer)) {
            return false;
        }
        sendTaskFragmentParentInfoChangedIfNeeded();
        return true;
    }

    void sendTaskFragmentParentInfoChangedIfNeeded() {
        com.android.server.wm.TaskFragment taskFragment;
        if (isLeafTask() && (taskFragment = getTaskFragment(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.TaskFragment) obj).isOrganizedTaskFragment();
            }
        })) != null) {
            taskFragment.sendTaskFragmentParentInfoChanged();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void assignChildLayers(@android.annotation.NonNull android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.TaskFragment adjacentTaskFragment;
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(i2);
            windowContainer.assignChildLayers(transaction);
            if (!windowContainer.needsZBoost()) {
                if (this.mDecorSurfaceContainer != null && !z && shouldPlaceDecorSurfaceBelowContainer(windowContainer)) {
                    this.mDecorSurfaceContainer.assignLayer(transaction, i);
                    i++;
                    z = true;
                }
                int i3 = i + 1;
                windowContainer.assignLayer(transaction, i);
                com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
                if (asTaskFragment != null && asTaskFragment.isEmbedded() && (adjacentTaskFragment = asTaskFragment.getAdjacentTaskFragment()) != null && adjacentTaskFragment.shouldBoostDimmer()) {
                    adjacentTaskFragment.assignLayer(transaction, i3);
                    i = i3 + 1;
                } else {
                    i = i3;
                }
                if (this.mDecorSurfaceContainer != null && !z && windowContainer == this.mDecorSurfaceContainer.mOwnerTaskFragment) {
                    this.mDecorSurfaceContainer.assignLayer(transaction, i);
                    i++;
                    z = true;
                }
            }
        }
        if (this.mDecorSurfaceContainer != null && !z) {
            this.mDecorSurfaceContainer.assignLayer(transaction, i);
            i++;
        }
        for (int i4 = 0; i4 < this.mChildren.size(); i4++) {
            com.android.server.wm.WindowContainer windowContainer2 = (com.android.server.wm.WindowContainer) this.mChildren.get(i4);
            if (windowContainer2.needsZBoost()) {
                windowContainer2.assignLayer(transaction, i);
                i++;
            }
        }
        if (this.mOverlayHost != null) {
            this.mOverlayHost.setLayer(transaction, i);
        }
    }

    boolean shouldPlaceDecorSurfaceBelowContainer(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        return ((windowContainer.asActivityRecord() != null && windowContainer.asActivityRecord().isUid(this.effectiveUid)) || (windowContainer.asTaskFragment() != null && windowContainer.asTaskFragment().isEmbedded() && windowContainer.asTaskFragment().isAllowedToBeEmbeddedInTrustedMode())) ? false : true;
    }

    boolean isTaskId(int i) {
        return this.mTaskId == i;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.Task asTask() {
        return this;
    }

    com.android.server.wm.ActivityRecord isInTask(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || !activityRecord.isDescendantOf(this)) {
            return null;
        }
        return activityRecord;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("userId=");
        printWriter.print(this.mUserId);
        printWriter.print(" effectiveUid=");
        android.os.UserHandle.formatUid(printWriter, this.effectiveUid);
        printWriter.print(" mCallingUid=");
        android.os.UserHandle.formatUid(printWriter, this.mCallingUid);
        printWriter.print(" mUserSetupComplete=");
        printWriter.print(this.mUserSetupComplete);
        printWriter.print(" mCallingPackage=");
        printWriter.print(this.mCallingPackage);
        printWriter.print(" mCallingFeatureId=");
        printWriter.println(this.mCallingFeatureId);
        if (this.affinity != null || this.rootAffinity != null) {
            printWriter.print(str);
            printWriter.print("affinity=");
            printWriter.print(this.affinity);
            if (this.affinity == null || !this.affinity.equals(this.rootAffinity)) {
                printWriter.print(" root=");
                printWriter.println(this.rootAffinity);
            } else {
                printWriter.println();
            }
        }
        if (this.mWindowLayoutAffinity != null) {
            printWriter.print(str);
            printWriter.print("windowLayoutAffinity=");
            printWriter.println(this.mWindowLayoutAffinity);
        }
        if (this.voiceSession != null || this.voiceInteractor != null) {
            printWriter.print(str);
            printWriter.print("VOICE: session=0x");
            printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.voiceSession)));
            printWriter.print(" interactor=0x");
            printWriter.println(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.voiceInteractor)));
        }
        if (this.intent != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append(str);
            sb.append("intent={");
            this.intent.toShortString(sb, false, true, false, false);
            sb.append('}');
            printWriter.println(sb.toString());
        }
        if (this.affinityIntent != null) {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder(128);
            sb2.append(str);
            sb2.append("affinityIntent={");
            this.affinityIntent.toShortString(sb2, false, true, false, false);
            sb2.append('}');
            printWriter.println(sb2.toString());
        }
        if (this.origActivity != null) {
            printWriter.print(str);
            printWriter.print("origActivity=");
            printWriter.println(this.origActivity.flattenToShortString());
        }
        if (this.realActivity != null) {
            printWriter.print(str);
            printWriter.print("mActivityComponent=");
            printWriter.println(this.realActivity.flattenToShortString());
        }
        if (this.autoRemoveRecents || this.isPersistable || !isActivityTypeStandard()) {
            printWriter.print(str);
            printWriter.print("autoRemoveRecents=");
            printWriter.print(this.autoRemoveRecents);
            printWriter.print(" isPersistable=");
            printWriter.print(this.isPersistable);
            printWriter.print(" activityType=");
            printWriter.println(getActivityType());
        }
        if (this.rootWasReset || this.mNeverRelinquishIdentity || this.mReuseTask || this.mLockTaskAuth != 1) {
            printWriter.print(str);
            printWriter.print("rootWasReset=");
            printWriter.print(this.rootWasReset);
            printWriter.print(" mNeverRelinquishIdentity=");
            printWriter.print(this.mNeverRelinquishIdentity);
            printWriter.print(" mReuseTask=");
            printWriter.print(this.mReuseTask);
            printWriter.print(" mLockTaskAuth=");
            printWriter.println(lockTaskAuthToString());
        }
        if (this.mAffiliatedTaskId != this.mTaskId || this.mPrevAffiliateTaskId != -1 || this.mPrevAffiliate != null || this.mNextAffiliateTaskId != -1 || this.mNextAffiliate != null) {
            printWriter.print(str);
            printWriter.print("affiliation=");
            printWriter.print(this.mAffiliatedTaskId);
            printWriter.print(" prevAffiliation=");
            printWriter.print(this.mPrevAffiliateTaskId);
            printWriter.print(" (");
            if (this.mPrevAffiliate == null) {
                printWriter.print("null");
            } else {
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mPrevAffiliate)));
            }
            printWriter.print(") nextAffiliation=");
            printWriter.print(this.mNextAffiliateTaskId);
            printWriter.print(" (");
            if (this.mNextAffiliate == null) {
                printWriter.print("null");
            } else {
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mNextAffiliate)));
            }
            printWriter.println(")");
        }
        printWriter.print(str);
        printWriter.print("Activities=");
        printWriter.println(this.mChildren);
        if (!this.inRecents || !this.isAvailable) {
            printWriter.print(str);
            printWriter.print(" inRecents=");
            printWriter.print(this.inRecents);
            printWriter.print(" isAvailable=");
            printWriter.println(this.isAvailable);
        }
        if (this.lastDescription != null) {
            printWriter.print(str);
            printWriter.print("lastDescription=");
            printWriter.println(this.lastDescription);
        }
        if (this.mRootProcess != null) {
            printWriter.print(str);
            printWriter.print("mRootProcess=");
            printWriter.println(this.mRootProcess);
        }
        if (this.mSharedStartingData != null) {
            printWriter.println(str + "mSharedStartingData=" + this.mSharedStartingData);
        }
        if (this.mKillProcessesOnDestroyed) {
            printWriter.println(str + "mKillProcessesOnDestroyed=true");
        }
        printWriter.print(str);
        printWriter.print("taskId=" + this.mTaskId);
        printWriter.println(" rootTaskId=" + getRootTaskId());
        printWriter.print(str);
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append("hasChildPipActivity=");
        sb3.append(this.mChildPipActivity != null);
        printWriter.println(sb3.toString());
        printWriter.print(str);
        printWriter.print("mHasBeenVisible=");
        printWriter.println(getHasBeenVisible());
        printWriter.print(str);
        printWriter.print("mResizeMode=");
        printWriter.print(android.content.pm.ActivityInfo.resizeModeToString(this.mResizeMode));
        printWriter.print(" mSupportsPictureInPicture=");
        printWriter.print(this.mSupportsPictureInPicture);
        printWriter.print(" isResizeable=");
        printWriter.println(isResizeable());
        printWriter.print(str);
        printWriter.print("lastActiveTime=");
        printWriter.print(this.lastActiveTime);
        printWriter.println(" (inactive for " + (getInactiveDuration() / 1000) + "s)");
    }

    @Override // com.android.server.wm.TaskFragment
    java.lang.String toFullString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(192);
        sb.append(this);
        sb.setLength(sb.length() - 1);
        sb.append(" U=");
        sb.append(this.mUserId);
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask != this) {
            sb.append(" rootTaskId=");
            sb.append(rootTask.mTaskId);
        }
        sb.append(" visible=");
        sb.append(shouldBeVisible(null));
        sb.append(" visibleRequested=");
        sb.append(isVisibleRequested());
        sb.append(" mode=");
        sb.append(android.app.WindowConfiguration.windowingModeToString(getWindowingMode()));
        sb.append(" translucent=");
        sb.append(isTranslucent(null));
        sb.append(" sz=");
        sb.append(getChildCount());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.server.wm.TaskFragment
    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Task{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" #");
        sb.append(this.mTaskId);
        sb.append(" type=" + android.app.WindowConfiguration.activityTypeToString(getActivityType()));
        if (this.affinity != null) {
            sb.append(" A=");
            sb.append(this.affinity);
        } else if (this.intent != null && this.intent.getComponent() != null) {
            sb.append(" I=");
            sb.append(this.intent.getComponent().flattenToShortString());
        } else if (this.affinityIntent != null && this.affinityIntent.getComponent() != null) {
            sb.append(" aI=");
            sb.append(this.affinityIntent.getComponent().flattenToShortString());
        }
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_TASKID, this.mTaskId);
        if (this.realActivity != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_REALACTIVITY, this.realActivity.flattenToShortString());
        }
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_REALACTIVITY_SUSPENDED, this.realActivitySuspended);
        if (this.origActivity != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_ORIGACTIVITY, this.origActivity.flattenToShortString());
        }
        if (this.affinity != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_AFFINITY, this.affinity);
            if (!this.affinity.equals(this.rootAffinity)) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_ROOT_AFFINITY, this.rootAffinity != null ? this.rootAffinity : "@");
            }
        } else if (this.rootAffinity != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_ROOT_AFFINITY, this.rootAffinity != null ? this.rootAffinity : "@");
        }
        if (this.mWindowLayoutAffinity != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_WINDOW_LAYOUT_AFFINITY, this.mWindowLayoutAffinity);
        }
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_ROOTHASRESET, this.rootWasReset);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_AUTOREMOVERECENTS, this.autoRemoveRecents);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_USERID, this.mUserId);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_USER_SETUP_COMPLETE, this.mUserSetupComplete);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_EFFECTIVE_UID, this.effectiveUid);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LASTTIMEMOVED, this.mLastTimeMoved);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_NEVERRELINQUISH, this.mNeverRelinquishIdentity);
        if (this.lastDescription != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LASTDESCRIPTION, this.lastDescription.toString());
        }
        if (getTaskDescription() != null) {
            getTaskDescription().saveToXml(typedXmlSerializer);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_TASK_AFFILIATION, this.mAffiliatedTaskId);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PREV_AFFILIATION, this.mPrevAffiliateTaskId);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_NEXT_AFFILIATION, this.mNextAffiliateTaskId);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_CALLING_UID, this.mCallingUid);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_CALLING_PACKAGE, this.mCallingPackage == null ? "" : this.mCallingPackage);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_CALLING_FEATURE_ID, this.mCallingFeatureId != null ? this.mCallingFeatureId : "");
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_RESIZE_MODE, this.mResizeMode);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_SUPPORTS_PICTURE_IN_PICTURE, this.mSupportsPictureInPicture);
        if (this.mLastNonFullscreenBounds != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_NON_FULLSCREEN_BOUNDS, this.mLastNonFullscreenBounds.flattenToString());
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_WIDTH, this.mMinWidth);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MIN_HEIGHT, this.mMinHeight);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PERSIST_TASK_VERSION, 1);
        if (this.mLastTaskSnapshotData.taskSize != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LAST_SNAPSHOT_TASK_SIZE, this.mLastTaskSnapshotData.taskSize.flattenToString());
        }
        if (this.mLastTaskSnapshotData.contentInsets != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LAST_SNAPSHOT_CONTENT_INSETS, this.mLastTaskSnapshotData.contentInsets.flattenToString());
        }
        if (this.mLastTaskSnapshotData.bufferSize != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_LAST_SNAPSHOT_BUFFER_SIZE, this.mLastTaskSnapshotData.bufferSize.flattenToString());
        }
        if (this.affinityIntent != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_AFFINITYINTENT);
            this.affinityIntent.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_AFFINITYINTENT);
        }
        if (this.intent != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_INTENT);
            this.intent.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_INTENT);
        }
        sTmpException = null;
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.internal.util.function.TriPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda30
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                boolean saveActivityToXml;
                saveActivityToXml = com.android.server.wm.Task.saveActivityToXml((com.android.server.wm.ActivityRecord) obj, (com.android.server.wm.ActivityRecord) obj2, (com.android.modules.utils.TypedXmlSerializer) obj3);
                return saveActivityToXml;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), getBottomMostActivity(), typedXmlSerializer);
        forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) obtainPredicate);
        obtainPredicate.recycle();
        if (sTmpException != null) {
            throw sTmpException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean saveActivityToXml(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
        if (activityRecord.info.persistableMode == 0 || !activityRecord.isPersistable() || (((activityRecord.intent.getFlags() & 524288) | 8192) == 524288 && activityRecord != activityRecord2)) {
            return true;
        }
        try {
            typedXmlSerializer.startTag((java.lang.String) null, "activity");
            activityRecord.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, "activity");
            return false;
        } catch (java.lang.Exception e) {
            sTmpException = e;
            return true;
        }
    }

    static com.android.server.wm.Task restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList;
        int i;
        int i2;
        int i3;
        boolean z;
        java.lang.String str;
        char c;
        long j;
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int depth = typedXmlPullParser.getDepth();
        android.app.ActivityManager.TaskDescription taskDescription = new android.app.ActivityManager.TaskDescription();
        android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData = new android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData();
        long j2 = 0;
        java.lang.String str2 = "";
        boolean z2 = true;
        boolean z3 = true;
        java.lang.String str3 = null;
        android.content.ComponentName componentName = null;
        java.lang.String str4 = null;
        int i4 = -1;
        int i5 = -1;
        boolean z4 = false;
        boolean z5 = false;
        android.content.ComponentName componentName2 = null;
        java.lang.String str5 = null;
        boolean z6 = false;
        boolean z7 = false;
        int i6 = 0;
        int i7 = 0;
        java.lang.String str6 = null;
        int i8 = -1;
        int i9 = -1;
        int i10 = -1;
        int i11 = -1;
        java.lang.String str7 = null;
        int i12 = 4;
        boolean z8 = false;
        android.graphics.Rect rect = null;
        int i13 = -1;
        int i14 = -1;
        int i15 = 0;
        for (int attributeCount = typedXmlPullParser.getAttributeCount() - 1; attributeCount >= 0; attributeCount--) {
            java.lang.String attributeName = typedXmlPullParser.getAttributeName(attributeCount);
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(attributeCount);
            switch (attributeName.hashCode()) {
                case -1588736338:
                    str = str2;
                    if (attributeName.equals(ATTR_LAST_SNAPSHOT_CONTENT_INSETS)) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -1556983798:
                    str = str2;
                    if (attributeName.equals(ATTR_LASTTIMEMOVED)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1537240555:
                    str = str2;
                    if (attributeName.equals(ATTR_TASKID)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1494902876:
                    str = str2;
                    if (attributeName.equals(ATTR_NEXT_AFFILIATION)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1138503444:
                    str = str2;
                    if (attributeName.equals(ATTR_REALACTIVITY_SUSPENDED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1124927690:
                    str = str2;
                    if (attributeName.equals(ATTR_TASK_AFFILIATION)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -974080081:
                    str = str2;
                    if (attributeName.equals(ATTR_USER_SETUP_COMPLETE)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -929566280:
                    str = str2;
                    if (attributeName.equals(ATTR_EFFECTIVE_UID)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -865458610:
                    str = str2;
                    if (attributeName.equals(ATTR_RESIZE_MODE)) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case -826243148:
                    str = str2;
                    if (attributeName.equals(ATTR_MIN_HEIGHT)) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -801863159:
                    str = str2;
                    if (attributeName.equals(ATTR_LAST_SNAPSHOT_TASK_SIZE)) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -707249465:
                    str = str2;
                    if (attributeName.equals(ATTR_NON_FULLSCREEN_BOUNDS)) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -705269939:
                    str = str2;
                    if (attributeName.equals(ATTR_ORIGACTIVITY)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -551322450:
                    str = str2;
                    if (attributeName.equals(ATTR_LAST_SNAPSHOT_BUFFER_SIZE)) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -502399667:
                    str = str2;
                    if (attributeName.equals(ATTR_AUTOREMOVERECENTS)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -360792224:
                    str = str2;
                    if (attributeName.equals(ATTR_SUPPORTS_PICTURE_IN_PICTURE)) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -162744347:
                    str = str2;
                    if (attributeName.equals(ATTR_ROOT_AFFINITY)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -147132913:
                    str = str2;
                    if (attributeName.equals(ATTR_USERID)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -132216235:
                    str = str2;
                    if (attributeName.equals(ATTR_CALLING_UID)) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 180927924:
                    str = str2;
                    if (attributeName.equals(ATTR_TASKTYPE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 331206372:
                    str = str2;
                    if (attributeName.equals(ATTR_PREV_AFFILIATION)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 394454367:
                    str = str2;
                    if (attributeName.equals(ATTR_CALLING_FEATURE_ID)) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 541503897:
                    str = str2;
                    if (attributeName.equals(ATTR_MIN_WIDTH)) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 605497640:
                    str = str2;
                    if (attributeName.equals(ATTR_AFFINITY)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 869221331:
                    str = str2;
                    if (attributeName.equals(ATTR_LASTDESCRIPTION)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 1007873193:
                    str = str2;
                    if (attributeName.equals(ATTR_PERSIST_TASK_VERSION)) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 1081438155:
                    str = str2;
                    if (attributeName.equals(ATTR_CALLING_PACKAGE)) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1457608782:
                    str = str2;
                    if (attributeName.equals(ATTR_NEVERRELINQUISH)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1539554448:
                    str = str2;
                    if (attributeName.equals(ATTR_REALACTIVITY)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1999609934:
                    str = str2;
                    if (attributeName.equals(ATTR_WINDOW_LAYOUT_AFFINITY)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2023391309:
                    str = str2;
                    if (attributeName.equals(ATTR_ROOTHASRESET)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    str = str2;
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    j = j2;
                    if (i4 == -1) {
                        i4 = java.lang.Integer.parseInt(attributeValue);
                        str2 = str;
                        j2 = j;
                        break;
                    }
                    break;
                case 1:
                    componentName = android.content.ComponentName.unflattenFromString(attributeValue);
                    str2 = str;
                    continue;
                case 2:
                    z5 = java.lang.Boolean.valueOf(attributeValue).booleanValue();
                    str2 = str;
                    continue;
                case 3:
                    componentName2 = android.content.ComponentName.unflattenFromString(attributeValue);
                    str2 = str;
                    continue;
                case 4:
                    str3 = attributeValue;
                    str2 = str;
                    continue;
                case 5:
                    str4 = attributeValue;
                    str2 = str;
                    z4 = true;
                    continue;
                case 6:
                    str5 = attributeValue;
                    str2 = str;
                    continue;
                case 7:
                    z6 = java.lang.Boolean.parseBoolean(attributeValue);
                    str2 = str;
                    continue;
                case '\b':
                    z7 = java.lang.Boolean.parseBoolean(attributeValue);
                    str2 = str;
                    continue;
                case '\t':
                    i7 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case '\n':
                    z2 = java.lang.Boolean.parseBoolean(attributeValue);
                    str2 = str;
                    continue;
                case 11:
                    i5 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case '\f':
                    i6 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case '\r':
                    str6 = attributeValue;
                    str2 = str;
                    continue;
                case 14:
                    j2 = java.lang.Long.parseLong(attributeValue);
                    str2 = str;
                    continue;
                case 15:
                    z3 = java.lang.Boolean.parseBoolean(attributeValue);
                    str2 = str;
                    continue;
                case 16:
                    i8 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 17:
                    i9 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 18:
                    i10 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 19:
                    i11 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 20:
                    str2 = attributeValue;
                    continue;
                case 21:
                    str7 = attributeValue;
                    str2 = str;
                    continue;
                case 22:
                    i12 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 23:
                    z8 = java.lang.Boolean.parseBoolean(attributeValue);
                    str2 = str;
                    continue;
                case 24:
                    rect = android.graphics.Rect.unflattenFromString(attributeValue);
                    str2 = str;
                    continue;
                case 25:
                    i13 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 26:
                    i14 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 27:
                    i15 = java.lang.Integer.parseInt(attributeValue);
                    str2 = str;
                    continue;
                case 28:
                    j = j2;
                    persistedTaskSnapshotData.taskSize = android.graphics.Point.unflattenFromString(attributeValue);
                    break;
                case 29:
                    j = j2;
                    persistedTaskSnapshotData.contentInsets = android.graphics.Rect.unflattenFromString(attributeValue);
                    break;
                case 30:
                    j = j2;
                    persistedTaskSnapshotData.bufferSize = android.graphics.Point.unflattenFromString(attributeValue);
                    break;
                default:
                    if (attributeName.startsWith("task_description_")) {
                        j = j2;
                        break;
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        j = j2;
                        sb.append("Task: Unknown attribute=");
                        sb.append(attributeName);
                        android.util.Slog.w("ActivityTaskManager", sb.toString());
                        break;
                    }
            }
            str2 = str;
            j2 = j;
        }
        long j3 = j2;
        java.lang.String str8 = str2;
        taskDescription.restoreFromXml(typedXmlPullParser);
        android.content.Intent intent = null;
        android.content.Intent intent2 = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1 && (next != 3 || typedXmlPullParser.getDepth() >= depth)) {
                if (next == 2) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (TAG_AFFINITYINTENT.equals(name)) {
                        intent2 = android.content.Intent.restoreFromXml(typedXmlPullParser);
                    } else if (TAG_INTENT.equals(name)) {
                        intent = android.content.Intent.restoreFromXml(typedXmlPullParser);
                    } else if ("activity".equals(name)) {
                        com.android.server.wm.ActivityRecord restoreFromXml = com.android.server.wm.ActivityRecord.restoreFromXml(typedXmlPullParser, activityTaskSupervisor);
                        if (restoreFromXml != null) {
                            arrayList2.add(restoreFromXml);
                        }
                    } else {
                        android.util.Slog.e("ActivityTaskManager", "restoreTask: Unexpected name=" + name);
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
        }
        if (!z4) {
            str4 = str3;
        } else if ("@".equals(str4)) {
            str4 = null;
        }
        if (i5 > 0) {
            arrayList = arrayList2;
            i = i7;
            i2 = i5;
        } else {
            android.content.Intent intent3 = intent != null ? intent : intent2;
            if (intent3 == null) {
                arrayList = arrayList2;
                i = i7;
            } else {
                try {
                    arrayList = arrayList2;
                    i = i7;
                    try {
                        android.content.pm.ApplicationInfo applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(intent3.getComponent().getPackageName(), 8704L, i);
                        if (applicationInfo == null) {
                            i2 = 0;
                        } else {
                            i2 = applicationInfo.uid;
                        }
                    } catch (android.os.RemoteException e) {
                    }
                } catch (android.os.RemoteException e2) {
                    arrayList = arrayList2;
                    i = i7;
                }
                android.util.Slog.w("ActivityTaskManager", "Updating task #" + i4 + " for " + intent3 + ": effectiveUid=" + i2);
            }
            i2 = 0;
            android.util.Slog.w("ActivityTaskManager", "Updating task #" + i4 + " for " + intent3 + ": effectiveUid=" + i2);
        }
        if (i15 < 1) {
            if (i6 == 1) {
                i3 = i12;
                if (i3 == 2) {
                    z = z8;
                    i3 = 1;
                }
            } else {
                i3 = i12;
            }
            z = z8;
        } else {
            i3 = i12;
            if (i3 == 3) {
                i3 = 2;
                z = true;
            }
            z = z8;
        }
        com.android.server.wm.Task buildInner = new com.android.server.wm.Task.Builder(activityTaskSupervisor.mService).setTaskId(i4).setIntent(intent).setAffinityIntent(intent2).setAffinity(str3).setRootAffinity(str4).setRealActivity(componentName).setOrigActivity(componentName2).setRootWasReset(z6).setAutoRemoveRecents(z7).setUserId(i).setEffectiveUid(i2).setLastDescription(str6).setLastTimeMoved(j3).setNeverRelinquishIdentity(z3).setLastTaskDescription(taskDescription).setLastSnapshotData(persistedTaskSnapshotData).setTaskAffiliation(i8).setPrevAffiliateTaskId(i9).setNextAffiliateTaskId(i10).setCallingUid(i11).setCallingPackage(str8).setCallingFeatureId(str7).setResizeMode(i3).setSupportsPictureInPicture(z).setRealActivitySuspended(z5).setUserSetupComplete(z2).setMinWidth(i13).setMinHeight(i14).buildInner();
        android.graphics.Rect rect2 = rect;
        buildInner.mLastNonFullscreenBounds = rect2;
        buildInner.setBounds(rect2);
        buildInner.mWindowLayoutAffinity = str5;
        if (arrayList.size() > 0) {
            activityTaskSupervisor.mRootWindowContainer.getDisplayContent(0).getDefaultTaskDisplayArea().addChild(buildInner, Integer.MIN_VALUE);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                buildInner.addChild((com.android.server.wm.ActivityRecord) arrayList.get(size));
            }
        }
        return buildInner;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    boolean isOrganized() {
        return this.mTaskOrganizer != null;
    }

    private boolean canBeOrganized() {
        if (isRootTask() || this.mCreatedByOrganizer) {
            return true;
        }
        com.android.server.wm.Task asTask = getParent().asTask();
        return asTask != null && asTask.mCreatedByOrganizer;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showSurfaceOnCreation() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    protected void reparentSurfaceControl(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (isOrganized() && isAlwaysOnTop()) {
            return;
        }
        super.reparentSurfaceControl(transaction, surfaceControl);
    }

    void setHasBeenVisible(boolean z) {
        this.mHasBeenVisible = z;
        if (!z) {
            return;
        }
        if (!this.mDeferTaskAppear) {
            sendTaskAppeared();
        }
        for (com.android.server.wm.WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            com.android.server.wm.Task asTask = parent.asTask();
            if (asTask != null) {
                asTask.setHasBeenVisible(true);
            } else {
                return;
            }
        }
    }

    boolean getHasBeenVisible() {
        return this.mHasBeenVisible;
    }

    void setDeferTaskAppear(boolean z) {
        boolean z2 = this.mDeferTaskAppear;
        this.mDeferTaskAppear = z;
        if (z2 && !z) {
            sendTaskAppeared();
        }
    }

    boolean taskAppearedReady() {
        if (this.mTaskOrganizer == null || this.mDeferTaskAppear) {
            return false;
        }
        if (this.mCreatedByOrganizer) {
            return true;
        }
        return this.mSurfaceControl != null && getHasBeenVisible();
    }

    private void sendTaskAppeared() {
        if (this.mTaskOrganizer != null) {
            this.mAtmService.mTaskOrganizerController.onTaskAppeared(this.mTaskOrganizer, this);
        }
    }

    private void sendTaskVanished(android.window.ITaskOrganizer iTaskOrganizer) {
        if (iTaskOrganizer != null) {
            this.mAtmService.mTaskOrganizerController.onTaskVanished(iTaskOrganizer, this);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer) {
        return setTaskOrganizer(iTaskOrganizer, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setTaskOrganizer(android.window.ITaskOrganizer iTaskOrganizer, boolean z) {
        if (this.mTaskOrganizer == iTaskOrganizer) {
            return false;
        }
        android.window.ITaskOrganizer iTaskOrganizer2 = this.mTaskOrganizer;
        this.mTaskOrganizer = iTaskOrganizer;
        sendTaskVanished(iTaskOrganizer2);
        if (this.mTaskOrganizer != null) {
            if (!z) {
                sendTaskAppeared();
                return true;
            }
            return true;
        }
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea != null) {
            displayArea.removeLaunchRootTask(this);
        }
        setForceHidden(2, false);
        if (this.mCreatedByOrganizer) {
            removeImmediately("setTaskOrganizer");
            return true;
        }
        return true;
    }

    boolean updateTaskOrganizerState() {
        return updateTaskOrganizerState(false);
    }

    boolean updateTaskOrganizerState(boolean z) {
        if (getSurfaceControl() == null) {
            return false;
        }
        if (!canBeOrganized()) {
            return setTaskOrganizer(null);
        }
        android.window.ITaskOrganizer taskOrganizer = this.mWmService.mAtmService.mTaskOrganizerController.getTaskOrganizer();
        if (!this.mCreatedByOrganizer || this.mTaskOrganizer == null || taskOrganizer == null || this.mTaskOrganizer == taskOrganizer) {
            return setTaskOrganizer(taskOrganizer, z);
        }
        return false;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void setSurfaceControl(android.view.SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        sendTaskAppeared();
    }

    boolean isFocused() {
        if (this.mDisplayContent == null || this.mDisplayContent.mFocusedApp == null) {
            return false;
        }
        com.android.server.wm.Task task = this.mDisplayContent.mFocusedApp.getTask();
        return task == this || (task != null && task.getParent() == this);
    }

    private boolean hasVisibleChildren() {
        return (!isAttached() || isForceHidden() || getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda36
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).isVisible();
            }
        }) == null) ? false : true;
    }

    void onAppFocusChanged(boolean z) {
        dispatchTaskInfoChangedIfNeeded(false);
        com.android.server.wm.Task asTask = getParent().asTask();
        if (asTask != null) {
            asTask.dispatchTaskInfoChangedIfNeeded(false);
        }
        this.mAtmService.getTaskChangeNotificationController().notifyTaskFocusChanged(this.mTaskId, z);
    }

    void onPictureInPictureParamsChanged() {
        if (inPinnedWindowingMode()) {
            dispatchTaskInfoChangedIfNeeded(true);
        }
    }

    void onShouldDockBigOverlaysChanged() {
        dispatchTaskInfoChangedIfNeeded(true);
    }

    void onSizeCompatActivityChanged() {
        dispatchTaskInfoChangedIfNeeded(true);
    }

    void setMainWindowSizeChangeTransaction(android.view.SurfaceControl.Transaction transaction) {
        setMainWindowSizeChangeTransaction(transaction, this);
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).requestRedrawForSync();
            }
        }, true);
    }

    private void setMainWindowSizeChangeTransaction(final android.view.SurfaceControl.Transaction transaction, com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity();
        com.android.server.wm.Task task2 = topNonFinishingActivity != null ? topNonFinishingActivity.getTask() : null;
        if (task2 == null) {
            return;
        }
        if (task2 != this) {
            task2.setMainWindowSizeChangeTransaction(transaction, task);
            return;
        }
        com.android.server.wm.WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow();
        if (topVisibleAppMainWindow != null) {
            topVisibleAppMainWindow.applyWithNextDraw(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda42
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.view.SurfaceControl.Transaction) obj).merge(transaction);
                }
            });
        } else {
            transaction.apply();
        }
    }

    @Override // com.android.server.wm.TaskFragment
    boolean setForceHidden(@com.android.server.wm.TaskFragment.FlagForceHidden int i, boolean z) {
        boolean isForceHidden = isForceHidden();
        boolean isVisible = isVisible();
        if (!super.setForceHidden(i, z)) {
            return false;
        }
        boolean isForceHidden2 = isForceHidden();
        if (isForceHidden == isForceHidden2) {
            return true;
        }
        if (isVisible && isForceHidden2) {
            moveToBack("setForceHidden", null);
            return true;
        }
        if (isAlwaysOnTop()) {
            moveToFront("setForceHidden");
            return true;
        }
        return true;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public boolean isAlwaysOnTop() {
        return !isForceHidden() && super.isAlwaysOnTop();
    }

    public boolean isAlwaysOnTopWhenVisible() {
        return super.isAlwaysOnTop();
    }

    boolean isForceHiddenForPinnedTask() {
        return (this.mForceHiddenFlags & 1) != 0;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268037L;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setWindowingMode(int i) {
        if (!isRootTask()) {
            super.setWindowingMode(i);
        } else {
            setWindowingMode(i, false);
        }
    }

    void setWindowingMode(int i, boolean z) {
        int i2;
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            android.util.Slog.d("ActivityTaskManager", "taskDisplayArea is null, bail early");
            return;
        }
        int windowingMode = getWindowingMode();
        com.android.server.wm.Task topMostTask = getTopMostTask();
        if (!z && !displayArea.isValidWindowingMode(i, null, topMostTask)) {
            i = 0;
        }
        if (windowingMode == i) {
            getRequestedOverrideConfiguration().windowConfiguration.setWindowingMode(i);
            return;
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity();
        if (i == 0) {
            com.android.server.wm.WindowContainer parent = getParent();
            i2 = parent != null ? parent.getWindowingMode() : 1;
        } else {
            i2 = i;
        }
        if (windowingMode == 2) {
            setCanAffectSystemUiFlags(true);
            this.mTaskSupervisor.mUserLeaving = true;
            com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null) {
                com.android.server.wm.ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
                enableEnterPipOnTaskSwitch(topResumedActivity, null, topResumedActivity, null);
            }
            this.mRootWindowContainer.notifyActivityPipModeChanged(this, null);
        }
        if (i2 == 2 && displayArea.getRootPinnedTask() != null) {
            displayArea.getRootPinnedTask().dismissPip();
        }
        if (i2 != 1 && topNonFinishingActivity != null && !topNonFinishingActivity.noDisplay && topNonFinishingActivity.canForceResizeNonResizable(i2)) {
            this.mAtmService.getTaskChangeNotificationController().notifyActivityForcedResizable(topMostTask.mTaskId, 1, topNonFinishingActivity.info.applicationInfo.packageName);
        }
        this.mAtmService.deferWindowLayout();
        if (topNonFinishingActivity != null) {
            try {
                this.mTaskSupervisor.mNoAnimActivities.add(topNonFinishingActivity);
            } catch (java.lang.Throwable th) {
                this.mAtmService.continueWindowLayout();
                throw th;
            }
        }
        boolean isPip2ExperimentEnabled = com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled();
        if (!isPip2ExperimentEnabled) {
            super.setWindowingMode(i);
        }
        if (windowingMode == 2 && topNonFinishingActivity != null) {
            if (topNonFinishingActivity.getLastParentBeforePip() != null && !isForceHidden() && topNonFinishingActivity.getLastParentBeforePip().isAttached()) {
                this.mTransitionController.collect(topNonFinishingActivity);
                com.android.server.wm.Task lastParentBeforePip = topNonFinishingActivity.getLastParentBeforePip();
                topNonFinishingActivity.reparent(lastParentBeforePip, lastParentBeforePip.getChildCount(), "movePinnedActivityToOriginalTask");
                com.android.server.wm.DisplayContent displayContent = topNonFinishingActivity.getDisplayContent();
                if (displayContent != null && displayContent.isFixedRotationLaunchingApp(topNonFinishingActivity)) {
                    topNonFinishingActivity.getOrCreateFixedRotationLeash(topNonFinishingActivity.getSyncTransaction());
                }
                lastParentBeforePip.moveToFront("movePinnedActivityToOriginalTask");
            }
            if (isPip2ExperimentEnabled) {
                super.setWindowingMode(i);
            }
            if (topNonFinishingActivity.shouldBeVisible()) {
                this.mAtmService.resumeAppSwitches();
            }
        } else if (isPip2ExperimentEnabled) {
            super.setWindowingMode(i);
        }
        if (z) {
            this.mAtmService.continueWindowLayout();
            return;
        }
        if (topNonFinishingActivity != null && windowingMode == 1 && i == 2 && !this.mTransitionController.isShellTransitionsEnabled()) {
            this.mDisplayContent.mPinnedTaskController.deferOrientationChangeForEnteringPipFromFullScreenIfNeeded();
        }
        this.mAtmService.continueWindowLayout();
        if (this.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
            return;
        }
        this.mRootWindowContainer.ensureActivitiesVisible();
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    void abortPipEnter(com.android.server.wm.ActivityRecord activityRecord) {
        if (!inPinnedWindowingMode() || activityRecord.inPinnedWindowingMode() || !canMoveTaskToBack(this)) {
            return;
        }
        com.android.server.wm.Transition transition = new com.android.server.wm.Transition(4, 0, this.mTransitionController, this.mWmService.mSyncEngine);
        this.mTransitionController.moveToCollecting(transition);
        this.mTransitionController.requestStartTransition(transition, this, null, null);
        if (activityRecord.getLastParentBeforePip() != null) {
            com.android.server.wm.Task lastParentBeforePip = activityRecord.getLastParentBeforePip();
            if (lastParentBeforePip.isAttached()) {
                activityRecord.reparent(lastParentBeforePip, lastParentBeforePip.getChildCount(), "movePinnedActivityToOriginalTask");
            }
        }
        if (isAttached()) {
            setWindowingMode(0);
            moveTaskToBackInner(this, null);
        }
        if (activityRecord.isAttached()) {
            activityRecord.setWindowingMode(0);
            activityRecord.mWaitForEnteringPinnedMode = false;
        }
    }

    void resumeNextFocusAfterReparent() {
        adjustFocusToNextFocusableTask("reparent", true, true);
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        this.mRootWindowContainer.ensureActivitiesVisible();
    }

    final boolean isOnHomeDisplay() {
        return getDisplayId() == 0;
    }

    void moveToFront(java.lang.String str) {
        moveToFront(str, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    void moveToFront(java.lang.String str, com.android.server.wm.Task task) {
        if (!isAttached()) {
            return;
        }
        this.mTransitionController.recordTaskOrder(this);
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (!isActivityTypeHome() && returnsToHomeRootTask()) {
            displayArea.moveHomeRootTaskToFront(str + " returnToHome");
        }
        com.android.server.wm.Task focusedRootTask = isRootTask() ? displayArea.getFocusedRootTask() : null;
        if (task == null) {
            task = this;
        }
        task.getParent().positionChildAt(Integer.MAX_VALUE, task, true);
        displayArea.updateLastFocusedRootTask(focusedRootTask, str);
    }

    void moveToBack(java.lang.String str, com.android.server.wm.Task task) {
        if (!isAttached()) {
            return;
        }
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (!this.mCreatedByOrganizer) {
            com.android.server.wm.WindowContainer parent = getParent();
            com.android.server.wm.Task asTask = parent != null ? parent.asTask() : null;
            if (asTask != null) {
                asTask.moveToBack(str, this);
            } else {
                com.android.server.wm.Task focusedRootTask = displayArea.getFocusedRootTask();
                displayArea.positionChildAt(Integer.MIN_VALUE, this, false);
                displayArea.updateLastFocusedRootTask(focusedRootTask, str);
            }
            if (task != null && task != this) {
                positionChildAtBottom(task);
                return;
            }
            return;
        }
        if (task == null || task == this) {
            return;
        }
        displayArea.positionTaskBehindHome(task);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.WindowContainer
    public void switchUser(int i) {
        if (this.mCurrentUser == i) {
            return;
        }
        this.mCurrentUser = i;
        super.switchUser(i);
        if (!isRootTask() && showToCurrentUser()) {
            getParent().positionChildAt(Integer.MAX_VALUE, this, false);
        }
    }

    void minimalResumeActivityLocked(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4446998544419008924L, 0, null, java.lang.String.valueOf(activityRecord), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        activityRecord.setState(com.android.server.wm.ActivityRecord.State.RESUMED, "minimalResumeActivityLocked");
        activityRecord.completeResumeLocked();
    }

    void checkReadyForSleep() {
        if (shouldSleepActivities() && goToSleepIfPossible(false)) {
            this.mTaskSupervisor.checkReadyForSleepLocked(true);
        }
    }

    boolean goToSleepIfPossible(final boolean z) {
        final int[] iArr = {0};
        forAllLeafTasksAndLeafTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$goToSleepIfPossible$18(z, iArr, (com.android.server.wm.TaskFragment) obj);
            }
        }, true);
        return iArr[0] == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$goToSleepIfPossible$18(boolean z, int[] iArr, com.android.server.wm.TaskFragment taskFragment) {
        if (!taskFragment.sleepIfPossible(z)) {
            iArr[0] = iArr[0] + 1;
        }
    }

    boolean isTopRootTaskInDisplayArea() {
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        return displayArea != null && displayArea.isTopRootTask(this);
    }

    boolean isFocusedRootTaskOnDisplay() {
        return this.mDisplayContent != null && this == this.mDisplayContent.getFocusedRootTask();
    }

    void ensureActivitiesVisible(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        ensureActivitiesVisible(activityRecord, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void ensureActivitiesVisible(@android.annotation.Nullable final com.android.server.wm.ActivityRecord activityRecord, final boolean z) {
        this.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.Task) obj).updateActivityVisibilities(com.android.server.wm.ActivityRecord.this, z);
                }
            }, true);
            if (this.mTranslucentActivityWaiting != null && this.mUndrawnActivitiesBelowTopTranslucent.isEmpty()) {
                notifyActivityDrawnLocked(null);
            }
        } finally {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    void checkTranslucentActivityWaiting(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mTranslucentActivityWaiting != activityRecord) {
            this.mUndrawnActivitiesBelowTopTranslucent.clear();
            if (this.mTranslucentActivityWaiting != null) {
                notifyActivityDrawnLocked(null);
                this.mTranslucentActivityWaiting = null;
            }
            this.mHandler.removeMessages(101);
        }
    }

    void convertActivityToTranslucent(com.android.server.wm.ActivityRecord activityRecord) {
        this.mTranslucentActivityWaiting = activityRecord;
        this.mUndrawnActivitiesBelowTopTranslucent.clear();
        this.mHandler.sendEmptyMessageDelayed(101, TRANSLUCENT_CONVERSION_TIMEOUT);
    }

    void notifyActivityDrawnLocked(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || (this.mUndrawnActivitiesBelowTopTranslucent.remove(activityRecord) && this.mUndrawnActivitiesBelowTopTranslucent.isEmpty())) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mTranslucentActivityWaiting;
            this.mTranslucentActivityWaiting = null;
            this.mUndrawnActivitiesBelowTopTranslucent.clear();
            this.mHandler.removeMessages(101);
            if (activityRecord2 != null) {
                activityRecord2.setMainWindowOpaque(false);
                if (activityRecord2.attachedToProcess()) {
                    try {
                        activityRecord2.app.getThread().scheduleTranslucentConversionComplete(activityRecord2.token, activityRecord != null);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean resumeTopActivityUncheckedLocked(com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions, boolean z) {
        boolean z2;
        if (this.mInResumeTopActivity) {
            return false;
        }
        try {
            this.mInResumeTopActivity = true;
            if (!isLeafTask()) {
                int size = this.mChildren.size() - 1;
                boolean z3 = false;
                while (size >= 0) {
                    int i = size - 1;
                    com.android.server.wm.Task task = (com.android.server.wm.Task) getChildAt(size);
                    if (task.isTopActivityFocusable()) {
                        if (task.getVisibility(null) != 0) {
                            if (task.topRunningActivity() != null) {
                                break;
                            }
                        } else {
                            z3 |= task.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, z);
                            if (i >= this.mChildren.size()) {
                                size = this.mChildren.size() - 1;
                            } else {
                                size = i;
                            }
                        }
                    }
                    size = i;
                }
                z2 = z3;
            } else if (!isFocusableAndVisible()) {
                z2 = false;
            } else {
                z2 = resumeTopActivityInnerLocked(activityRecord, activityOptions, z);
            }
            com.android.server.wm.ActivityRecord activityRecord2 = topRunningActivity(true);
            if (activityRecord2 == null || !activityRecord2.canTurnScreenOn()) {
                checkReadyForSleep();
            }
            this.mInResumeTopActivity = false;
            return z2;
        } catch (java.lang.Throwable th) {
            this.mInResumeTopActivity = false;
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean resumeTopActivityUncheckedLocked(com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions) {
        return resumeTopActivityUncheckedLocked(activityRecord, activityOptions, false);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean resumeTopActivityInnerLocked(final com.android.server.wm.ActivityRecord activityRecord, final android.app.ActivityOptions activityOptions, final boolean z) {
        if (!this.mAtmService.isBooting() && !this.mAtmService.isBooted()) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord2 = topRunningActivity(true);
        if (activityRecord2 == null) {
            return resumeNextFocusableActivityWhenRootTaskIsEmpty(activityRecord, activityOptions);
        }
        final com.android.server.wm.TaskFragment taskFragment = activityRecord2.getTaskFragment();
        final boolean[] zArr = {taskFragment.resumeTopActivity(activityRecord, activityOptions, z)};
        forAllLeafTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda43
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Task.lambda$resumeTopActivityInnerLocked$20(com.android.server.wm.TaskFragment.this, zArr, activityRecord, activityOptions, z, (com.android.server.wm.TaskFragment) obj);
            }
        }, true);
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resumeTopActivityInnerLocked$20(com.android.server.wm.TaskFragment taskFragment, boolean[] zArr, com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions, boolean z, com.android.server.wm.TaskFragment taskFragment2) {
        if (taskFragment == taskFragment2 || !taskFragment2.canBeResumed(null)) {
            return;
        }
        zArr[0] = taskFragment2.resumeTopActivity(activityRecord, activityOptions, z) | zArr[0];
    }

    private boolean resumeNextFocusableActivityWhenRootTaskIsEmpty(com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions) {
        com.android.server.wm.Task adjustFocusToNextFocusableTask;
        if (!isActivityTypeHome() && (adjustFocusToNextFocusableTask = adjustFocusToNextFocusableTask("noMoreActivities")) != null) {
            return this.mRootWindowContainer.resumeFocusedTasksTopActivities(adjustFocusToNextFocusableTask, activityRecord, null);
        }
        android.app.ActivityOptions.abort(activityOptions);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4037728373502324767L, 0, null, java.lang.String.valueOf("noMoreActivities"));
        return this.mRootWindowContainer.resumeHomeActivity(activityRecord, "noMoreActivities", getDisplayArea());
    }

    void startActivityLocked(com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task, boolean z, boolean z2, android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2) {
        boolean z3;
        com.android.server.wm.Task task2 = activityRecord.getTask();
        boolean z4 = activityOptions == null || !activityOptions.getAvoidMoveToFront();
        boolean z5 = task2 == this || hasChild(task2);
        if (!activityRecord.mLaunchTaskBehind && z4 && (!z5 || z)) {
            positionChildAtTop(task2);
        }
        if (!z && z5 && !activityRecord.shouldBeVisible()) {
            android.app.ActivityOptions.abort(activityOptions);
            return;
        }
        com.android.server.wm.Task task3 = activityRecord.getTask();
        if (task3 == null && this.mChildren.indexOf(null) != getChildCount() - 1) {
            this.mTaskSupervisor.mUserLeaving = false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -2261257617975724313L, 0, null, java.lang.String.valueOf(activityRecord), java.lang.String.valueOf(task3), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
        if (isActivityTypeHomeOrRecents() && getActivityBelow(activityRecord) == null) {
            android.app.ActivityOptions.abort(activityOptions);
            return;
        }
        if (!z4) {
            android.app.ActivityOptions.abort(activityOptions);
            return;
        }
        com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
        if ((activityRecord.intent.getFlags() & 65536) != 0) {
            displayContent.prepareAppTransition(0);
            this.mTaskSupervisor.mNoAnimActivities.add(activityRecord);
            this.mTransitionController.setNoAnimation(activityRecord);
        } else {
            displayContent.prepareAppTransition(1);
            this.mTaskSupervisor.mNoAnimActivities.remove(activityRecord);
        }
        if (z && !activityRecord.mLaunchTaskBehind && !com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
            enableEnterPipOnTaskSwitch(findEnterPipOnTaskSwitchCandidate(task), null, activityRecord, activityOptions);
        }
        if (z) {
            if ((activityRecord.intent.getFlags() & 2097152) != 0) {
                resetTaskIfNeeded(activityRecord, activityRecord);
                z3 = topRunningNonDelayedActivityLocked(null) == activityRecord;
            }
            z3 = true;
        } else {
            if (activityOptions != null && activityOptions.getAnimationType() == 5) {
                z3 = false;
            }
            z3 = true;
        }
        boolean z6 = (activityOptions == null || !activityOptions.getDisableStartingWindow()) ? z3 : false;
        if (activityRecord.mLaunchTaskBehind) {
            activityRecord.setVisibility(true);
            ensureActivitiesVisible(null);
            if (!activityRecord.isVisibleRequested()) {
                activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
            }
            this.mDisplayContent.executeAppTransition();
            return;
        }
        if (z6) {
            this.mWmService.mStartingSurfaceController.showStartingWindow(activityRecord, activityRecord.getTask().getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda26
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$startActivityLocked$21;
                    lambda$startActivityLocked$21 = com.android.server.wm.Task.lambda$startActivityLocked$21((com.android.server.wm.ActivityRecord) obj);
                    return lambda$startActivityLocked$21;
                }
            }), z, z2, activityRecord2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$startActivityLocked$21(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.mStartingData != null && activityRecord.showToCurrentUser();
    }

    @android.annotation.Nullable
    static com.android.server.wm.ActivityRecord findEnterPipOnTaskSwitchCandidate(@android.annotation.Nullable com.android.server.wm.Task task) {
        if (task == null) {
            return null;
        }
        final com.android.server.wm.ActivityRecord[] activityRecordArr = new com.android.server.wm.ActivityRecord[1];
        task.forAllLeafTaskFragments(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findEnterPipOnTaskSwitchCandidate$22;
                lambda$findEnterPipOnTaskSwitchCandidate$22 = com.android.server.wm.Task.lambda$findEnterPipOnTaskSwitchCandidate$22(activityRecordArr, (com.android.server.wm.TaskFragment) obj);
                return lambda$findEnterPipOnTaskSwitchCandidate$22;
            }
        });
        return activityRecordArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findEnterPipOnTaskSwitchCandidate$22(com.android.server.wm.ActivityRecord[] activityRecordArr, com.android.server.wm.TaskFragment taskFragment) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity = taskFragment.getTopNonFinishingActivity();
        if (topNonFinishingActivity == null || !topNonFinishingActivity.isState(com.android.server.wm.ActivityRecord.State.RESUMED, com.android.server.wm.ActivityRecord.State.PAUSING) || !topNonFinishingActivity.supportsPictureInPicture()) {
            return false;
        }
        activityRecordArr[0] = topNonFinishingActivity;
        return true;
    }

    static void enableEnterPipOnTaskSwitch(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        com.android.server.wm.Task rootTask;
        if (activityRecord == null) {
            return;
        }
        if ((activityOptions != null && activityOptions.disallowEnterPictureInPictureWhileLaunching()) || activityRecord.inPinnedWindowingMode()) {
            return;
        }
        if (task != null) {
            rootTask = task.getRootTask();
        } else {
            rootTask = activityRecord2 != null ? activityRecord2.getRootTask() : null;
        }
        if (rootTask == null) {
            android.util.Slog.e("ActivityTaskManager", "No root task for enter pip, both to front task and activity are null?");
            return;
        }
        boolean z = false;
        boolean z2 = (activityOptions != null && activityOptions.getTransientLaunch()) || rootTask.mTransitionController.isTransientHide(rootTask);
        if (!rootTask.isActivityTypeAssistant() && !z2) {
            z = true;
        }
        activityRecord.supportsEnterPipOnTaskSwitch = z;
    }

    com.android.server.wm.ActivityRecord resetTaskIfNeeded(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        boolean z = (activityRecord2.info.flags & 4) != 0;
        com.android.server.wm.Task task = activityRecord.getTask();
        this.mReuseTask = true;
        try {
            android.app.ActivityOptions process = sResetTargetTaskHelper.process(task, z);
            this.mReuseTask = false;
            if (this.mChildren.contains(task) && (topNonFinishingActivity = task.getTopNonFinishingActivity()) != null) {
                activityRecord = topNonFinishingActivity;
            }
            if (process != null) {
                activityRecord.updateOptionsLocked(process);
            }
            return activityRecord;
        } catch (java.lang.Throwable th) {
            this.mReuseTask = false;
            throw th;
        }
    }

    final com.android.server.wm.Task finishTopCrashedActivityLocked(com.android.server.wm.WindowProcessController windowProcessController, java.lang.String str) {
        com.android.server.wm.ActivityRecord activityRecord = topRunningActivity();
        if (activityRecord == null || activityRecord.app != windowProcessController) {
            return null;
        }
        if (activityRecord.isActivityTypeHome() && this.mAtmService.mHomeProcess == windowProcessController) {
            android.util.Slog.w("ActivityTaskManager", "  Not force finishing home activity " + activityRecord.intent.getComponent().flattenToShortString());
            return null;
        }
        android.util.Slog.w("ActivityTaskManager", "  Force finishing activity " + activityRecord.intent.getComponent().flattenToShortString());
        com.android.server.wm.Task task = activityRecord.getTask();
        this.mDisplayContent.requestTransitionAndLegacyPrepare(2, 16);
        activityRecord.finishIfPossible(str, false);
        com.android.server.wm.ActivityRecord activityBelow = getActivityBelow(activityRecord);
        if (activityBelow != null && activityBelow.isState(com.android.server.wm.ActivityRecord.State.STARTED, com.android.server.wm.ActivityRecord.State.RESUMED, com.android.server.wm.ActivityRecord.State.PAUSING, com.android.server.wm.ActivityRecord.State.PAUSED) && (!activityBelow.isActivityTypeHome() || this.mAtmService.mHomeProcess != activityBelow.app)) {
            android.util.Slog.w("ActivityTaskManager", "  Force finishing activity " + activityBelow.intent.getComponent().flattenToShortString());
            activityBelow.finishIfPossible(str, false);
        }
        return task;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void finishIfVoiceTask(android.os.IBinder iBinder) {
        if (this.voiceSession != null && this.voiceSession.asBinder() == iBinder) {
            forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.Task.this.lambda$finishIfVoiceTask$23((com.android.server.wm.ActivityRecord) obj);
                }
            });
            return;
        }
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new java.util.function.BiPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda6
            @Override // java.util.function.BiPredicate
            public final boolean test(java.lang.Object obj, java.lang.Object obj2) {
                boolean finishIfVoiceActivity;
                finishIfVoiceActivity = com.android.server.wm.Task.finishIfVoiceActivity((com.android.server.wm.ActivityRecord) obj, (android.os.IBinder) obj2);
                return finishIfVoiceActivity;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), iBinder);
        forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) obtainPredicate);
        obtainPredicate.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishIfVoiceTask$23(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.finishing) {
            return;
        }
        activityRecord.finishIfPossible("finish-voice", false);
        this.mAtmService.updateOomAdj();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean finishIfVoiceActivity(com.android.server.wm.ActivityRecord activityRecord, android.os.IBinder iBinder) {
        if (activityRecord.voiceSession == null || activityRecord.voiceSession.asBinder() != iBinder) {
            return false;
        }
        activityRecord.clearVoiceSessionLocked();
        try {
            activityRecord.app.getThread().scheduleLocalVoiceInteractionStarted(activityRecord.token, (com.android.internal.app.IVoiceInteractor) null);
        } catch (android.os.RemoteException e) {
        }
        activityRecord.mAtmService.finishRunningVoiceLocked();
        return true;
    }

    private boolean inFrontOfStandardRootTask() {
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            return false;
        }
        final boolean[] zArr = new boolean[1];
        com.android.server.wm.Task rootTask = displayArea.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$inFrontOfStandardRootTask$24;
                lambda$inFrontOfStandardRootTask$24 = com.android.server.wm.Task.this.lambda$inFrontOfStandardRootTask$24(zArr, (com.android.server.wm.Task) obj);
                return lambda$inFrontOfStandardRootTask$24;
            }
        });
        if (rootTask == null || !rootTask.isActivityTypeStandard()) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$inFrontOfStandardRootTask$24(boolean[] zArr, com.android.server.wm.Task task) {
        if (zArr[0]) {
            return true;
        }
        if (task == this) {
            zArr[0] = true;
        }
        return false;
    }

    boolean shouldUpRecreateTaskLocked(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        java.lang.String computeTaskAffinity = com.android.server.wm.ActivityRecord.computeTaskAffinity(str, activityRecord.getUid());
        if (activityRecord.getTask().affinity == null || !activityRecord.getTask().affinity.equals(computeTaskAffinity)) {
            return true;
        }
        com.android.server.wm.Task task = activityRecord.getTask();
        if (activityRecord.isRootOfTask() && task.getBaseIntent() != null && task.getBaseIntent().isDocument()) {
            if (!inFrontOfStandardRootTask()) {
                return true;
            }
            com.android.server.wm.Task taskBelow = getTaskBelow(task);
            if (taskBelow == null) {
                android.util.Slog.w("ActivityTaskManager", "shouldUpRecreateTask: task not in history for " + activityRecord);
                return false;
            }
            if (!task.affinity.equals(taskBelow.affinity)) {
                return true;
            }
        }
        return false;
    }

    boolean navigateUpTo(com.android.server.wm.ActivityRecord activityRecord, android.content.Intent intent, java.lang.String str, com.android.server.uri.NeededUriGrants neededUriGrants, int i, android.content.Intent intent2, com.android.server.uri.NeededUriGrants neededUriGrants2) {
        final com.android.server.wm.ActivityRecord activityRecord2;
        boolean z;
        com.android.server.wm.ActivityRecord activityRecord3;
        boolean z2;
        if (!activityRecord.attachedToProcess()) {
            return false;
        }
        com.android.server.wm.Task task = activityRecord.getTask();
        if (!activityRecord.isDescendantOf(this)) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityBelow = task.getActivityBelow(activityRecord);
        final android.content.ComponentName component = intent.getComponent();
        if (task.getBottomMostActivity() != activityRecord && component != null && (activityRecord2 = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$navigateUpTo$25;
                lambda$navigateUpTo$25 = com.android.server.wm.Task.lambda$navigateUpTo$25(component, (com.android.server.wm.ActivityRecord) obj);
                return lambda$navigateUpTo$25;
            }
        }, activityRecord, false, true)) != null) {
            z = true;
        } else {
            activityRecord2 = activityBelow;
            z = false;
        }
        android.app.IActivityController iActivityController = this.mAtmService.mController;
        if (iActivityController != null && (activityRecord3 = topRunningActivity(activityRecord.token, -1)) != null) {
            try {
                z2 = iActivityController.activityResuming(activityRecord3.packageName);
            } catch (android.os.RemoteException e) {
                this.mAtmService.mController = null;
                com.android.server.Watchdog.getInstance().setActivityController(null);
                z2 = true;
            }
            if (!z2) {
                return false;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        final int[] iArr = {i};
        final android.content.Intent[] intentArr = {intent2};
        final com.android.server.uri.NeededUriGrants[] neededUriGrantsArr = {neededUriGrants2};
        task.forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$navigateUpTo$26;
                lambda$navigateUpTo$26 = com.android.server.wm.Task.lambda$navigateUpTo$26(com.android.server.wm.ActivityRecord.this, iArr, intentArr, neededUriGrantsArr, (com.android.server.wm.ActivityRecord) obj);
                return lambda$navigateUpTo$26;
            }
        }, activityRecord, true, true);
        int i2 = iArr[0];
        android.content.Intent intent3 = intentArr[0];
        if (activityRecord2 != null && z) {
            int i3 = activityRecord.info.applicationInfo.uid;
            int execute = this.mAtmService.getActivityStartController().obtainStarter(intent, "navigateUpTo").setResolvedType(str).setUserId(activityRecord.mUserId).setCaller(activityRecord.app.getThread()).setResultTo(activityRecord2.token).setIntentGrants(neededUriGrants).setCallingPid(-1).setCallingUid(i3).setCallingPackage(activityRecord.packageName).setCallingFeatureId(activityRecord2.launchedFromFeatureId).setRealCallingPid(-1).setRealCallingUid(i3).setComponentSpecified(true).execute();
            z = android.app.ActivityManager.isStartResultSuccessful(execute);
            if (execute == 0) {
                activityRecord2.finishIfPossible(i2, intent3, neededUriGrants2, "navigate-top", true);
            }
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$navigateUpTo$25(android.content.ComponentName componentName, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.info.packageName.equals(componentName.getPackageName()) && activityRecord.info.name.equals(componentName.getClassName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$navigateUpTo$26(com.android.server.wm.ActivityRecord activityRecord, int[] iArr, android.content.Intent[] intentArr, com.android.server.uri.NeededUriGrants[] neededUriGrantsArr, com.android.server.wm.ActivityRecord activityRecord2) {
        if (activityRecord2 == activityRecord) {
            return true;
        }
        activityRecord2.finishIfPossible(iArr[0], intentArr[0], neededUriGrantsArr[0], "navigate-up", true);
        iArr[0] = 0;
        intentArr[0] = null;
        return false;
    }

    void removeLaunchTickMessages() {
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.ActivityRecord) obj).removeLaunchTickRunnable();
            }
        });
    }

    private void updateTransitLocked(int i, android.app.ActivityOptions activityOptions) {
        if (activityOptions != null) {
            com.android.server.wm.ActivityRecord activityRecord = topRunningActivity();
            if (activityRecord != null && !activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                activityRecord.updateOptionsLocked(activityOptions);
            } else {
                android.app.ActivityOptions.abort(activityOptions);
            }
        }
        this.mDisplayContent.prepareAppTransition(i);
    }

    final void moveTaskToFront(com.android.server.wm.Task task, boolean z, android.app.ActivityOptions activityOptions, com.android.server.am.AppTimeTracker appTimeTracker, java.lang.String str) {
        moveTaskToFront(task, z, activityOptions, appTimeTracker, false, str);
    }

    final void moveTaskToFront(com.android.server.wm.Task task, boolean z, android.app.ActivityOptions activityOptions, final com.android.server.am.AppTimeTracker appTimeTracker, boolean z2, java.lang.String str) {
        com.android.server.wm.ActivityRecord findEnterPipOnTaskSwitchCandidate = findEnterPipOnTaskSwitchCandidate(getDisplayArea().getTopRootTask());
        if (task != this && !task.isDescendantOf(this)) {
            if (z) {
                android.app.ActivityOptions.abort(activityOptions);
                return;
            } else {
                updateTransitLocked(3, activityOptions);
                return;
            }
        }
        if (appTimeTracker != null) {
            task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.ActivityRecord) obj).appTimeTracker = com.android.server.am.AppTimeTracker.this;
                }
            });
        }
        try {
            this.mDisplayContent.deferUpdateImeTarget();
            com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
            if (topNonFinishingActivity == null || !topNonFinishingActivity.showToCurrentUser()) {
                positionChildAtTop(task);
                if (topNonFinishingActivity != null) {
                    this.mTaskSupervisor.mRecentTasks.add(topNonFinishingActivity.getTask());
                }
                android.app.ActivityOptions.abort(activityOptions);
                this.mDisplayContent.continueUpdateImeTarget();
                return;
            }
            topNonFinishingActivity.moveFocusableActivityToTop(str);
            if (z) {
                this.mDisplayContent.prepareAppTransition(0);
                this.mTaskSupervisor.mNoAnimActivities.add(topNonFinishingActivity);
                this.mTransitionController.collect(topNonFinishingActivity);
                this.mTransitionController.setNoAnimation(topNonFinishingActivity);
                android.app.ActivityOptions.abort(activityOptions);
            } else {
                updateTransitLocked(3, activityOptions);
            }
            enableEnterPipOnTaskSwitch(findEnterPipOnTaskSwitchCandidate, task, null, activityOptions);
            if (!z2) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
            this.mDisplayContent.continueUpdateImeTarget();
        } catch (java.lang.Throwable th) {
            this.mDisplayContent.continueUpdateImeTarget();
            throw th;
        }
    }

    private boolean canMoveTaskToBack(com.android.server.wm.Task task) {
        boolean z;
        if (!this.mAtmService.getLockTaskController().canMoveTaskToBack(task)) {
            return false;
        }
        if (this.mAtmService.mController != null && isTopRootTaskInDisplayArea()) {
            com.android.server.wm.ActivityRecord activityRecord = topRunningActivity(null, task.mTaskId);
            if (activityRecord == null) {
                activityRecord = topRunningActivity(null, -1);
            }
            if (activityRecord != null) {
                try {
                    z = this.mAtmService.mController.activityResuming(activityRecord.packageName);
                } catch (android.os.RemoteException e) {
                    this.mAtmService.mController = null;
                    com.android.server.Watchdog.getInstance().setActivityController(null);
                    z = true;
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean moveTaskToBack(final com.android.server.wm.Task task) {
        android.util.Slog.i("ActivityTaskManager", "moveTaskToBack: " + task);
        if (!canMoveTaskToBack(task)) {
            return false;
        }
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            com.android.server.wm.Transition collectingTransition = this.mTransitionController.getCollectingTransition();
            if (collectingTransition != null && collectingTransition.mType == 1) {
                collectingTransition.collect(task);
                moveTaskToBackInner(task, collectingTransition);
                return true;
            }
            final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(4, 0, this.mTransitionController, this.mWmService.mSyncEngine);
            this.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda32
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    com.android.server.wm.Task.this.lambda$moveTaskToBack$28(task, transition, z);
                }
            });
        } else {
            if (!inPinnedWindowingMode()) {
                this.mDisplayContent.prepareAppTransition(4);
            }
            moveTaskToBackInner(task, null);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveTaskToBack$28(com.android.server.wm.Task task, com.android.server.wm.Transition transition, boolean z) {
        if (!isAttached() || (z && !canMoveTaskToBack(task))) {
            android.util.Slog.e("ActivityTaskManager", "Failed to move task to back after saying we could: " + task.mTaskId);
            transition.abort();
            return;
        }
        this.mTransitionController.requestStartTransition(transition, task, null, null);
        this.mTransitionController.collect(task);
        moveTaskToBackInner(task, transition);
    }

    private void moveTaskToBackInner(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.Transition transition) {
        com.android.server.wm.Transition.ReadyCondition readyCondition = new com.android.server.wm.Transition.ReadyCondition("moved-to-back", task);
        if (transition != null) {
            this.mAtmService.deferWindowLayout();
            transition.mReadyTracker.add(readyCondition);
        }
        try {
            moveToBack("moveTaskToBackInner", task);
            if (inPinnedWindowingMode()) {
                this.mTaskSupervisor.removeRootTask(this);
                if (transition != null) {
                    return;
                } else {
                    return;
                }
            }
            this.mRootWindowContainer.ensureVisibilityAndConfig(null, this.mDisplayContent.mDisplayId, false);
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mAtmService.continueWindowLayout();
            }
            if (transition != null) {
                readyCondition.meet();
            }
            com.android.server.wm.ActivityRecord activityRecord = getDisplayArea().topRunningActivity();
            com.android.server.wm.Task rootTask = activityRecord != null ? activityRecord.getRootTask() : null;
            if (rootTask == null || rootTask == this || !activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            } else {
                this.mDisplayContent.executeAppTransition();
                this.mDisplayContent.setFocusedApp(activityRecord);
            }
        } finally {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mAtmService.continueWindowLayout();
            }
            if (transition != null) {
                readyCondition.meet();
            }
        }
    }

    boolean willActivityBeVisible(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.shouldBeVisible()) {
            return false;
        }
        if (forTokenLocked.finishing) {
            android.util.Slog.e("ActivityTaskManager", "willActivityBeVisible: Returning false, would have returned true for r=" + forTokenLocked);
        }
        return !forTokenLocked.finishing;
    }

    void unhandledBackLocked() {
        com.android.server.wm.ActivityRecord topMostActivity = getTopMostActivity();
        if (topMostActivity != null) {
            topMostActivity.finishIfPossible("unhandled-back", true);
        }
    }

    boolean dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, boolean z, boolean z2, java.lang.String str, boolean z3) {
        return dump("  ", fileDescriptor, printWriter, z, z2, str, z3, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.TaskFragment
    public void dumpInner(java.lang.String str, java.io.PrintWriter printWriter, boolean z, java.lang.String str2) {
        super.dumpInner(str, printWriter, z, str2);
        if (this.mCreatedByOrganizer) {
            printWriter.println(str + "  mCreatedByOrganizer=true");
        }
        if (this.mLastNonFullscreenBounds != null) {
            printWriter.print(str);
            printWriter.print("  mLastNonFullscreenBounds=");
            printWriter.println(this.mLastNonFullscreenBounds);
        }
        if (isLeafTask()) {
            printWriter.println(str + "  isSleeping=" + shouldSleepActivities());
            com.android.server.wm.ActivityTaskSupervisor.printThisActivity(printWriter, getTopPausingActivity(), str2, false, str + "  topPausingActivity=", null);
            com.android.server.wm.ActivityTaskSupervisor.printThisActivity(printWriter, getTopResumedActivity(), str2, false, str + "  topResumedActivity=", null);
            if (this.mMinWidth != -1 || this.mMinHeight != -1) {
                printWriter.print(str);
                printWriter.print("  mMinWidth=");
                printWriter.print(this.mMinWidth);
                printWriter.print(" mMinHeight=");
                printWriter.println(this.mMinHeight);
            }
        }
    }

    java.util.ArrayList<com.android.server.wm.ActivityRecord> getDumpActivitiesLocked(java.lang.String str, int i) {
        final java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = new java.util.ArrayList<>();
        if ("all".equals(str)) {
            java.util.Objects.requireNonNull(arrayList);
            forAllActivities(new com.android.server.wm.Task$$ExternalSyntheticLambda38(arrayList));
        } else if ("top".equals(str)) {
            com.android.server.wm.ActivityRecord topMostActivity = getTopMostActivity();
            if (topMostActivity != null) {
                arrayList.add(topMostActivity);
            }
        } else {
            final com.android.server.am.ActivityManagerService.ItemMatcher itemMatcher = new com.android.server.am.ActivityManagerService.ItemMatcher();
            itemMatcher.build(str);
            forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda39
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.Task.lambda$getDumpActivitiesLocked$29(com.android.server.am.ActivityManagerService.ItemMatcher.this, arrayList, (com.android.server.wm.ActivityRecord) obj);
                }
            });
        }
        if (i != -1) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size).mUserId != i) {
                    arrayList.remove(size);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDumpActivitiesLocked$29(com.android.server.am.ActivityManagerService.ItemMatcher itemMatcher, java.util.ArrayList arrayList, com.android.server.wm.ActivityRecord activityRecord) {
        if (itemMatcher.match(activityRecord, activityRecord.intent.getComponent())) {
            arrayList.add(activityRecord);
        }
    }

    com.android.server.wm.Task reuseOrCreateTask(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent, boolean z) {
        return reuseOrCreateTask(activityInfo, intent, null, null, z, null, null, null);
    }

    com.android.server.wm.Task reuseOrCreateTask(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, boolean z, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.app.ActivityOptions activityOptions) {
        int nextTaskIdForUser;
        com.android.server.wm.Task build;
        if (canReuseAsLeafTask()) {
            build = reuseAsLeafTask(iVoiceInteractionSession, iVoiceInteractor, intent, activityInfo, activityRecord);
        } else {
            if (activityRecord != null) {
                nextTaskIdForUser = this.mTaskSupervisor.getNextTaskIdForUser(activityRecord.mUserId);
            } else {
                nextTaskIdForUser = this.mTaskSupervisor.getNextTaskIdForUser();
            }
            getActivityType();
            build = new com.android.server.wm.Task.Builder(this.mAtmService).setTaskId(nextTaskIdForUser).setActivityInfo(activityInfo).setActivityOptions(activityOptions).setIntent(intent).setVoiceSession(iVoiceInteractionSession).setVoiceInteractor(iVoiceInteractor).setOnTop(z).setParent(this).build();
        }
        int displayId = getDisplayId();
        if (displayId == -1) {
            displayId = 0;
        }
        boolean isKeyguardOrAodShowing = this.mAtmService.mTaskSupervisor.getKeyguardController().isKeyguardOrAodShowing(displayId);
        if (!this.mTaskSupervisor.getLaunchParamsController().layoutTask(build, activityInfo.windowLayout, activityRecord, activityRecord2, activityOptions) && !getRequestedOverrideBounds().isEmpty() && build.isResizeable() && !isKeyguardOrAodShowing) {
            build.setBounds(getRequestedOverrideBounds());
        }
        return build;
    }

    private boolean canReuseAsLeafTask() {
        if (this.mCreatedByOrganizer || !isLeafTask()) {
            return false;
        }
        return com.android.server.wm.DisplayContent.alwaysCreateRootTask(getWindowingMode(), getActivityType());
    }

    void addChild(com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2) {
        com.android.server.wm.Task asTask = windowContainer.asTask();
        if (asTask != null) {
            try {
                asTask.setForceShowForAllUsers(z2);
            } catch (java.lang.Throwable th) {
                if (asTask != null) {
                    asTask.setForceShowForAllUsers(false);
                }
                throw th;
            }
        }
        addChild(windowContainer, z ? Integer.MAX_VALUE : 0, z);
        if (asTask != null) {
            asTask.setForceShowForAllUsers(false);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setAlwaysOnTop(boolean z) {
        if (super.isAlwaysOnTop() == z) {
            return;
        }
        super.setAlwaysOnTop(z);
        if (!isForceHidden()) {
            getDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
        }
    }

    void dismissPip() {
        if (!isActivityTypeStandardOrUndefined()) {
            throw new java.lang.IllegalArgumentException("You can't move tasks from non-standard root tasks.");
        }
        if (getWindowingMode() != 2) {
            throw new java.lang.IllegalArgumentException("Can't exit pinned mode if it's not pinned already.");
        }
        com.android.server.wm.Task bottomMostTask = getBottomMostTask();
        setWindowingMode(0);
        if (isAttached()) {
            getDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
        }
        this.mTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(bottomMostTask, this);
    }

    private int setBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (com.android.server.wm.ConfigurationContainer.equivalentBounds(rect, rect2)) {
            return 0;
        }
        if (!inMultiWindowMode()) {
            rect2 = null;
        }
        return setBoundsUnchecked(rect2);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void getBounds(android.graphics.Rect rect) {
        rect.set(getBounds());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChild(com.android.server.wm.WindowContainer windowContainer, int i, boolean z) {
        addChild((com.android.server.wm.Task) windowContainer, (java.util.Comparator<com.android.server.wm.Task>) null);
        positionChildAt(i, windowContainer, z);
    }

    void positionChildAtTop(com.android.server.wm.Task task) {
        if (task == null) {
            return;
        }
        if (task == this) {
            moveToFront("positionChildAtTop");
        } else {
            positionChildAt(Integer.MAX_VALUE, task, true);
        }
    }

    void positionChildAtBottom(com.android.server.wm.Task task) {
        positionChildAtBottom(task, getDisplayArea().getNextFocusableRootTask(task.getRootTask(), true) == null);
    }

    @com.android.internal.annotations.VisibleForTesting
    void positionChildAtBottom(com.android.server.wm.Task task, boolean z) {
        if (task == null) {
            return;
        }
        positionChildAt(Integer.MIN_VALUE, task, z);
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
        dispatchTaskInfoChangedIfNeeded(false);
        if (!this.mChildren.contains(windowContainer)) {
            return;
        }
        if (windowContainer.asTask() != null) {
            this.mRootWindowContainer.invalidateTaskLayers();
        }
        if (windowContainer.asActivityRecord() != null) {
            sendTaskFragmentParentInfoChangedIfNeeded();
        }
    }

    void reparent(com.android.server.wm.TaskDisplayArea taskDisplayArea, boolean z) {
        if (taskDisplayArea == null) {
            throw new java.lang.IllegalArgumentException("Task can't reparent to null " + this);
        }
        if (getParent() == taskDisplayArea) {
            throw new java.lang.IllegalArgumentException("Task=" + this + " already child of " + taskDisplayArea);
        }
        if (canBeLaunchedOnDisplay(taskDisplayArea.getDisplayId())) {
            reparent(taskDisplayArea, z ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            if (isLeafTask()) {
                taskDisplayArea.onLeafTaskMoved(this, z, !z);
                return;
            }
            return;
        }
        android.util.Slog.w("ActivityTaskManager", "Task=" + this + " can't reparent to " + taskDisplayArea);
    }

    void setLastRecentsAnimationTransaction(@android.annotation.NonNull android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, @android.annotation.Nullable android.view.SurfaceControl surfaceControl) {
        this.mLastRecentsAnimationTransaction = new android.window.PictureInPictureSurfaceTransaction(pictureInPictureSurfaceTransaction);
        this.mLastRecentsAnimationOverlay = surfaceControl;
    }

    void clearLastRecentsAnimationTransaction(boolean z) {
        if (z && this.mLastRecentsAnimationOverlay != null) {
            getPendingTransaction().remove(this.mLastRecentsAnimationOverlay);
        }
        this.mLastRecentsAnimationTransaction = null;
        this.mLastRecentsAnimationOverlay = null;
        resetSurfaceControlTransforms();
    }

    void resetSurfaceControlTransforms() {
        getSyncTransaction().setMatrix(this.mSurfaceControl, android.graphics.Matrix.IDENTITY_MATRIX, new float[9]).setWindowCrop(this.mSurfaceControl, null).setShadowRadius(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).setCornerRadius(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    void maybeApplyLastRecentsAnimationTransaction() {
        if (this.mLastRecentsAnimationTransaction != null) {
            android.view.SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            if (this.mLastRecentsAnimationOverlay != null) {
                pendingTransaction.reparent(this.mLastRecentsAnimationOverlay, this.mSurfaceControl);
            }
            android.window.PictureInPictureSurfaceTransaction.apply(this.mLastRecentsAnimationTransaction, this.mSurfaceControl, pendingTransaction);
            pendingTransaction.show(this.mSurfaceControl);
            this.mLastRecentsAnimationTransaction = null;
            this.mLastRecentsAnimationOverlay = null;
        }
    }

    private void updateSurfaceBounds() {
        updateSurfaceSize(getSyncTransaction());
        updateSurfacePositionNonOrganized();
        scheduleAnimation();
    }

    private android.graphics.Point getRelativePosition() {
        android.graphics.Point point = new android.graphics.Point();
        getRelativePosition(point);
        return point;
    }

    boolean shouldIgnoreInput() {
        if (this.mAtmService.mHasLeanbackFeature && inPinnedWindowingMode() && !isFocusedRootTaskOnDisplay()) {
            return true;
        }
        return false;
    }

    private void warnForNonLeafTask(java.lang.String str) {
        if (!isLeafTask()) {
            android.util.Slog.w("ActivityTaskManager", str + " on non-leaf task " + this);
        }
    }

    public android.view.DisplayInfo getDisplayInfo() {
        return this.mDisplayContent.getDisplayInfo();
    }

    com.android.server.wm.AnimatingActivityRegistry getAnimatingActivityRegistry() {
        return this.mAnimatingActivityRegistry;
    }

    @Override // com.android.server.wm.TaskFragment
    void executeAppTransition(android.app.ActivityOptions activityOptions) {
        this.mDisplayContent.executeAppTransition();
        android.app.ActivityOptions.abort(activityOptions);
    }

    @Override // com.android.server.wm.TaskFragment
    boolean shouldSleepActivities() {
        boolean isKeyguardGoingAway;
        com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
        if (this.mDisplayContent != null) {
            isKeyguardGoingAway = this.mDisplayContent.isKeyguardGoingAway();
        } else {
            isKeyguardGoingAway = this.mRootWindowContainer.getDefaultDisplay().isKeyguardGoingAway();
        }
        if (isKeyguardGoingAway && isFocusedRootTaskOnDisplay() && displayContent != null && displayContent.isDefaultDisplay) {
            return false;
        }
        return displayContent != null ? displayContent.isSleeping() : this.mAtmService.isSleepingLocked();
    }

    private android.graphics.Rect getRawBounds() {
        return super.getBounds();
    }

    void dispatchTaskInfoChangedIfNeeded(boolean z) {
        if (isOrganized()) {
            this.mAtmService.mTaskOrganizerController.onTaskInfoChanged(this, z);
        }
    }

    void setReparentLeafTaskIfRelaunch(boolean z) {
        if (isOrganized()) {
            this.mReparentLeafTaskIfRelaunch = z;
        }
    }

    boolean isSameRequiredDisplayCategory(@android.annotation.NonNull android.content.pm.ActivityInfo activityInfo) {
        return (this.mRequiredDisplayCategory != null && this.mRequiredDisplayCategory.equals(activityInfo.requiredDisplayCategory)) || (this.mRequiredDisplayCategory == null && activityInfo.requiredDisplayCategory == null);
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464258L, this.mTaskId);
        protoOutputStream.write(1120986464272L, getRootTaskId());
        if (getTopResumedActivity() != null) {
            getTopResumedActivity().writeIdentifierToProto(protoOutputStream, 1146756268044L);
        }
        if (this.realActivity != null) {
            protoOutputStream.write(1138166333453L, this.realActivity.flattenToShortString());
        }
        if (this.origActivity != null) {
            protoOutputStream.write(1138166333454L, this.origActivity.flattenToShortString());
        }
        protoOutputStream.write(1120986464274L, this.mResizeMode);
        protoOutputStream.write(1133871366148L, matchParentBounds());
        getRawBounds().dumpDebug(protoOutputStream, 1146756268037L);
        if (this.mLastNonFullscreenBounds != null) {
            this.mLastNonFullscreenBounds.dumpDebug(protoOutputStream, 1146756268054L);
        }
        if (this.mSurfaceControl != null) {
            protoOutputStream.write(1120986464264L, this.mSurfaceControl.getWidth());
            protoOutputStream.write(1120986464265L, this.mSurfaceControl.getHeight());
        }
        protoOutputStream.write(1133871366172L, this.mCreatedByOrganizer);
        protoOutputStream.write(1138166333469L, this.affinity);
        protoOutputStream.write(1133871366174L, this.mChildPipActivity != null);
        super.dumpDebug(protoOutputStream, 1146756268063L, i);
        protoOutputStream.end(start);
    }

    static class Builder {
        private android.content.pm.ActivityInfo mActivityInfo;
        private android.app.ActivityOptions mActivityOptions;
        private int mActivityType;
        private java.lang.String mAffinity;
        private android.content.Intent mAffinityIntent;
        private final com.android.server.wm.ActivityTaskManagerService mAtmService;
        private boolean mAutoRemoveRecents;
        private java.lang.String mCallingFeatureId;
        private java.lang.String mCallingPackage;
        private int mCallingUid;
        private boolean mCreatedByOrganizer;
        private boolean mDeferTaskAppear;
        private int mEffectiveUid;
        private boolean mHasBeenVisible;
        private android.content.Intent mIntent;
        private java.lang.String mLastDescription;
        private android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData mLastSnapshotData;
        private android.app.ActivityManager.TaskDescription mLastTaskDescription;
        private long mLastTimeMoved;
        private android.os.IBinder mLaunchCookie;
        private int mLaunchFlags;
        private boolean mNeverRelinquishIdentity;
        private boolean mOnTop;
        private android.content.ComponentName mOrigActivity;
        private com.android.server.wm.WindowContainer mParent;
        private android.content.ComponentName mRealActivity;
        private boolean mRealActivitySuspended;
        private boolean mRemoveWithTaskOrganizer;
        private int mResizeMode;
        private java.lang.String mRootAffinity;
        private boolean mRootWasReset;
        private com.android.server.wm.Task mSourceTask;
        private boolean mSupportsPictureInPicture;
        private int mTaskAffiliation;
        private int mTaskId;
        private int mUserId;
        private boolean mUserSetupComplete;
        private com.android.internal.app.IVoiceInteractor mVoiceInteractor;
        private android.service.voice.IVoiceInteractionSession mVoiceSession;
        private int mPrevAffiliateTaskId = -1;
        private int mNextAffiliateTaskId = -1;
        private int mMinWidth = -1;
        private int mMinHeight = -1;
        private int mWindowingMode = 0;

        Builder(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
            this.mAtmService = activityTaskManagerService;
        }

        com.android.server.wm.Task.Builder setParent(com.android.server.wm.WindowContainer windowContainer) {
            this.mParent = windowContainer;
            return this;
        }

        com.android.server.wm.Task.Builder setSourceTask(com.android.server.wm.Task task) {
            this.mSourceTask = task;
            return this;
        }

        com.android.server.wm.Task.Builder setLaunchFlags(int i) {
            this.mLaunchFlags = i;
            return this;
        }

        com.android.server.wm.Task.Builder setTaskId(int i) {
            this.mTaskId = i;
            return this;
        }

        com.android.server.wm.Task.Builder setIntent(android.content.Intent intent) {
            this.mIntent = intent;
            return this;
        }

        com.android.server.wm.Task.Builder setRealActivity(android.content.ComponentName componentName) {
            this.mRealActivity = componentName;
            return this;
        }

        com.android.server.wm.Task.Builder setEffectiveUid(int i) {
            this.mEffectiveUid = i;
            return this;
        }

        com.android.server.wm.Task.Builder setMinWidth(int i) {
            this.mMinWidth = i;
            return this;
        }

        com.android.server.wm.Task.Builder setMinHeight(int i) {
            this.mMinHeight = i;
            return this;
        }

        com.android.server.wm.Task.Builder setActivityInfo(android.content.pm.ActivityInfo activityInfo) {
            this.mActivityInfo = activityInfo;
            return this;
        }

        com.android.server.wm.Task.Builder setActivityOptions(android.app.ActivityOptions activityOptions) {
            this.mActivityOptions = activityOptions;
            return this;
        }

        com.android.server.wm.Task.Builder setVoiceSession(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) {
            this.mVoiceSession = iVoiceInteractionSession;
            return this;
        }

        com.android.server.wm.Task.Builder setActivityType(int i) {
            this.mActivityType = i;
            return this;
        }

        int getActivityType() {
            return this.mActivityType;
        }

        com.android.server.wm.Task.Builder setWindowingMode(int i) {
            this.mWindowingMode = i;
            return this;
        }

        int getWindowingMode() {
            return this.mWindowingMode;
        }

        com.android.server.wm.Task.Builder setCreatedByOrganizer(boolean z) {
            this.mCreatedByOrganizer = z;
            return this;
        }

        boolean getCreatedByOrganizer() {
            return this.mCreatedByOrganizer;
        }

        com.android.server.wm.Task.Builder setDeferTaskAppear(boolean z) {
            this.mDeferTaskAppear = z;
            return this;
        }

        com.android.server.wm.Task.Builder setLaunchCookie(android.os.IBinder iBinder) {
            this.mLaunchCookie = iBinder;
            return this;
        }

        com.android.server.wm.Task.Builder setOnTop(boolean z) {
            this.mOnTop = z;
            return this;
        }

        com.android.server.wm.Task.Builder setHasBeenVisible(boolean z) {
            this.mHasBeenVisible = z;
            return this;
        }

        com.android.server.wm.Task.Builder setRemoveWithTaskOrganizer(boolean z) {
            this.mRemoveWithTaskOrganizer = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setLastTimeMoved(long j) {
            this.mLastTimeMoved = j;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setNeverRelinquishIdentity(boolean z) {
            this.mNeverRelinquishIdentity = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setCallingUid(int i) {
            this.mCallingUid = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setCallingPackage(java.lang.String str) {
            this.mCallingPackage = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setResizeMode(int i) {
            this.mResizeMode = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setSupportsPictureInPicture(boolean z) {
            this.mSupportsPictureInPicture = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setUserSetupComplete(boolean z) {
            this.mUserSetupComplete = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setTaskAffiliation(int i) {
            this.mTaskAffiliation = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setPrevAffiliateTaskId(int i) {
            this.mPrevAffiliateTaskId = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setNextAffiliateTaskId(int i) {
            this.mNextAffiliateTaskId = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setCallingFeatureId(java.lang.String str) {
            this.mCallingFeatureId = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setRealActivitySuspended(boolean z) {
            this.mRealActivitySuspended = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setLastDescription(java.lang.String str) {
            this.mLastDescription = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setLastTaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
            this.mLastTaskDescription = taskDescription;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setLastSnapshotData(android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData) {
            this.mLastSnapshotData = persistedTaskSnapshotData;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setOrigActivity(android.content.ComponentName componentName) {
            this.mOrigActivity = componentName;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setRootWasReset(boolean z) {
            this.mRootWasReset = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setAutoRemoveRecents(boolean z) {
            this.mAutoRemoveRecents = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setAffinityIntent(android.content.Intent intent) {
            this.mAffinityIntent = intent;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setAffinity(java.lang.String str) {
            this.mAffinity = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setRootAffinity(java.lang.String str) {
            this.mRootAffinity = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.wm.Task.Builder setVoiceInteractor(com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
            this.mVoiceInteractor = iVoiceInteractor;
            return this;
        }

        private void validateRootTask(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
            com.android.server.wm.Task rootTask;
            if (this.mActivityType == 0 && !this.mCreatedByOrganizer) {
                this.mActivityType = 1;
            }
            if (!com.android.server.wm.DisplayContent.alwaysCreateRootTask(taskDisplayArea.getWindowingMode(), this.mActivityType) && this.mActivityType != 0 && (rootTask = taskDisplayArea.getRootTask(0, this.mActivityType)) != null) {
                throw new java.lang.IllegalArgumentException("Root task=" + rootTask + " of activityType=" + this.mActivityType + " already on display=" + taskDisplayArea + ". Can't have multiple.");
            }
            if (!com.android.server.wm.TaskDisplayArea.isWindowingModeSupported(this.mWindowingMode, this.mAtmService.mSupportsMultiWindow, this.mAtmService.mSupportsFreeformWindowManagement, this.mAtmService.mSupportsPictureInPicture)) {
                throw new java.lang.IllegalArgumentException("Can't create root task for unsupported windowingMode=" + this.mWindowingMode);
            }
            if (this.mWindowingMode == 2 && this.mActivityType != 1) {
                throw new java.lang.IllegalArgumentException("Root task with pinned windowing mode cannot with non-standard activity type.");
            }
            if (this.mWindowingMode == 2 && taskDisplayArea.getRootPinnedTask() != null) {
                taskDisplayArea.getRootPinnedTask().dismissPip();
            }
            if (this.mIntent != null) {
                this.mLaunchFlags |= this.mIntent.getFlags();
            }
            com.android.server.wm.Task launchRootTask = this.mCreatedByOrganizer ? null : taskDisplayArea.getLaunchRootTask(this.mWindowingMode, this.mActivityType, this.mActivityOptions, this.mSourceTask, this.mLaunchFlags);
            if (launchRootTask != null) {
                this.mWindowingMode = 0;
                this.mParent = launchRootTask;
            }
            this.mTaskId = taskDisplayArea.getNextRootTaskId();
        }

        com.android.server.wm.Task build() {
            if (this.mParent != null && (this.mParent instanceof com.android.server.wm.TaskDisplayArea)) {
                validateRootTask((com.android.server.wm.TaskDisplayArea) this.mParent);
            }
            if (this.mActivityInfo == null) {
                this.mActivityInfo = new android.content.pm.ActivityInfo();
                this.mActivityInfo.applicationInfo = new android.content.pm.ApplicationInfo();
            }
            this.mUserId = android.os.UserHandle.getUserId(this.mActivityInfo.applicationInfo.uid);
            this.mTaskAffiliation = this.mTaskId;
            this.mLastTimeMoved = java.lang.System.currentTimeMillis();
            this.mNeverRelinquishIdentity = true;
            this.mCallingUid = this.mActivityInfo.applicationInfo.uid;
            this.mCallingPackage = this.mActivityInfo.packageName;
            this.mResizeMode = this.mActivityInfo.resizeMode;
            this.mSupportsPictureInPicture = this.mActivityInfo.supportsPictureInPicture();
            if (!this.mRemoveWithTaskOrganizer && this.mActivityOptions != null) {
                this.mRemoveWithTaskOrganizer = this.mActivityOptions.getRemoveWithTaskOranizer();
            }
            com.android.server.wm.Task buildInner = buildInner();
            buildInner.mHasBeenVisible = this.mHasBeenVisible;
            if (this.mActivityType != 0) {
                buildInner.setActivityType(this.mActivityType);
            }
            if (this.mParent != null) {
                if (this.mParent instanceof com.android.server.wm.Task) {
                    ((com.android.server.wm.Task) this.mParent).addChild(buildInner, this.mOnTop ? Integer.MAX_VALUE : Integer.MIN_VALUE, (this.mActivityInfo.flags & 1024) != 0);
                } else {
                    this.mParent.addChild(buildInner, this.mOnTop ? Integer.MAX_VALUE : Integer.MIN_VALUE);
                }
            }
            if (this.mWindowingMode != 0) {
                buildInner.setWindowingMode(this.mWindowingMode, true);
            }
            return buildInner;
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.Task buildInner() {
            return new com.android.server.wm.Task(this.mAtmService, this.mTaskId, this.mIntent, this.mAffinityIntent, this.mAffinity, this.mRootAffinity, this.mRealActivity, this.mOrigActivity, this.mRootWasReset, this.mAutoRemoveRecents, this.mUserId, this.mEffectiveUid, this.mLastDescription, this.mLastTimeMoved, this.mNeverRelinquishIdentity, this.mLastTaskDescription, this.mLastSnapshotData, this.mTaskAffiliation, this.mPrevAffiliateTaskId, this.mNextAffiliateTaskId, this.mCallingUid, this.mCallingPackage, this.mCallingFeatureId, this.mResizeMode, this.mSupportsPictureInPicture, this.mRealActivitySuspended, this.mUserSetupComplete, this.mMinWidth, this.mMinHeight, this.mActivityInfo, this.mVoiceSession, this.mVoiceInteractor, this.mCreatedByOrganizer, this.mLaunchCookie, this.mDeferTaskAppear, this.mRemoveWithTaskOrganizer);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void updateOverlayInsetsState(com.android.server.wm.WindowState windowState) {
        super.updateOverlayInsetsState(windowState);
        if (windowState == getTopVisibleAppMainWindow() && this.mOverlayHost != null) {
            android.view.InsetsState insetsState = windowState.getInsetsState(true);
            getBounds(this.mTmpRect);
            this.mOverlayHost.dispatchInsetsChanged(insetsState, this.mTmpRect);
        }
    }

    void moveOrCreateDecorSurfaceFor(com.android.server.wm.TaskFragment taskFragment) {
        if (this.mDecorSurfaceContainer != null) {
            this.mDecorSurfaceContainer.mOwnerTaskFragment = taskFragment;
            return;
        }
        this.mDecorSurfaceContainer = new com.android.server.wm.Task.DecorSurfaceContainer(taskFragment);
        assignChildLayers();
        sendTaskFragmentParentInfoChangedIfNeeded();
    }

    void removeDecorSurface() {
        if (this.mDecorSurfaceContainer == null) {
            return;
        }
        this.mDecorSurfaceContainer.release();
        this.mDecorSurfaceContainer = null;
        sendTaskFragmentParentInfoChangedIfNeeded();
    }

    @android.annotation.Nullable
    android.view.SurfaceControl getDecorSurface() {
        if (this.mDecorSurfaceContainer != null) {
            return this.mDecorSurfaceContainer.mDecorSurface;
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    class DecorSurfaceContainer {

        @android.annotation.NonNull
        @com.android.internal.annotations.VisibleForTesting
        final android.view.SurfaceControl mContainerSurface;

        @android.annotation.NonNull
        @com.android.internal.annotations.VisibleForTesting
        final android.view.SurfaceControl mDecorSurface;

        @android.annotation.NonNull
        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.TaskFragment mOwnerTaskFragment;

        private DecorSurfaceContainer(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
            this.mOwnerTaskFragment = taskFragment;
            this.mContainerSurface = com.android.server.wm.Task.this.makeSurface().setContainerLayer().setParent(com.android.server.wm.Task.this.mSurfaceControl).setName(com.android.server.wm.Task.this.mSurfaceControl + " - decor surface container").setEffectLayer().setHidden(false).setCallsite("Task.DecorSurfaceContainer").build();
            this.mDecorSurface = com.android.server.wm.Task.this.makeSurface().setParent(this.mContainerSurface).setName(com.android.server.wm.Task.this.mSurfaceControl + " - decor surface").setHidden(false).setCallsite("Task.DecorSurfaceContainer").build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void assignLayer(@android.annotation.NonNull android.view.SurfaceControl.Transaction transaction, int i) {
            transaction.setLayer(this.mContainerSurface, i);
            transaction.setVisibility(this.mContainerSurface, this.mOwnerTaskFragment.isVisible());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void release() {
            this.mDecorSurface.release();
            this.mContainerSurface.release();
        }
    }
}
