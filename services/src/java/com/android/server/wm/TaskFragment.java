package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskFragment extends com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> {
    static final int EMBEDDED_DIM_AREA_PARENT_TASK = 1;
    static final int EMBEDDED_DIM_AREA_TASK_FRAGMENT = 0;
    static final int EMBEDDING_ALLOWED = 0;
    static final int EMBEDDING_DISALLOWED_MIN_DIMENSION_VIOLATION = 2;
    static final int EMBEDDING_DISALLOWED_NEW_TASK = 3;
    static final int EMBEDDING_DISALLOWED_UNTRUSTED_HOST = 1;
    static final int FLAG_FORCE_HIDDEN_FOR_PINNED_TASK = 1;
    static final int FLAG_FORCE_HIDDEN_FOR_TASK_FRAGMENT_ORG = 4;
    static final int FLAG_FORCE_HIDDEN_FOR_TASK_ORG = 2;
    static final int INVALID_MIN_SIZE = -1;
    static final boolean SHOW_APP_STARTING_PREVIEW = true;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_RESULTS = "ActivityTaskManager";
    private static final java.lang.String TAG_SWITCH = "ActivityTaskManager";
    private static final java.lang.String TAG_TRANSITION = "ActivityTaskManager";
    static final int TASK_FRAGMENT_VISIBILITY_INVISIBLE = 2;
    static final int TASK_FRAGMENT_VISIBILITY_VISIBLE = 0;
    static final int TASK_FRAGMENT_VISIBILITY_VISIBLE_BEHIND_TRANSLUCENT = 1;

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragment mAdjacentTaskFragment;
    private boolean mAllowTransitionWhenEmpty;

    @android.annotation.NonNull
    private android.window.TaskFragmentAnimationParams mAnimationParams;
    final com.android.server.wm.ActivityTaskManagerService mAtmService;
    boolean mClearedForReorderActivityToFront;
    boolean mClearedTaskForReuse;
    boolean mClearedTaskFragmentForPip;

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragment mCompanionTaskFragment;

    @com.android.internal.annotations.VisibleForTesting
    boolean mCreatedByOrganizer;
    private boolean mDelayLastActivityRemoval;
    private boolean mDelayOrganizedTaskFragmentSurfaceUpdate;
    com.android.server.wm.Dimmer mDimmer;

    @com.android.server.wm.TaskFragment.EmbeddedDimArea
    private int mEmbeddedDimArea;
    private final com.android.server.wm.EnsureActivitiesVisibleHelper mEnsureActivitiesVisibleHelper;
    protected int mForceHiddenFlags;
    private boolean mForceTranslucent;

    @android.annotation.Nullable
    private final android.os.IBinder mFragmentToken;
    private final boolean mIsEmbedded;
    private boolean mIsRemovalRequested;
    private boolean mIsolatedNav;
    com.android.server.wm.ActivityRecord mLastPausedActivity;
    final android.graphics.Point mLastSurfaceSize;
    int mMinHeight;
    int mMinWidth;
    private boolean mMoveToBottomIfClearWhenLaunch;

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord mPausingActivity;

    @android.annotation.Nullable
    private final android.graphics.Rect mRelativeEmbeddedBounds;

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord mResumedActivity;
    final com.android.server.wm.RootWindowContainer mRootWindowContainer;
    boolean mTaskFragmentAppearedSent;

    @android.annotation.Nullable
    private android.window.ITaskFragmentOrganizer mTaskFragmentOrganizer;
    private final com.android.server.wm.TaskFragmentOrganizerController mTaskFragmentOrganizerController;

    @android.annotation.Nullable
    private java.lang.String mTaskFragmentOrganizerProcessName;

    @com.android.internal.annotations.VisibleForTesting
    int mTaskFragmentOrganizerUid;
    boolean mTaskFragmentVanishedSent;
    final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private final android.graphics.Rect mTmpAbsBounds;
    private final android.graphics.Rect mTmpBounds;
    private final android.graphics.Rect mTmpFullBounds;
    private final android.graphics.Rect mTmpNonDecorBounds;
    private final android.graphics.Rect mTmpStableBounds;

    @interface EmbeddedDimArea {
    }

    @interface EmbeddingCheckResult {
    }

    @interface FlagForceHidden {
    }

    @interface TaskFragmentVisibility {
    }

    TaskFragment(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, android.os.IBinder iBinder, boolean z) {
        this(activityTaskManagerService, iBinder, z, true);
    }

    TaskFragment(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, android.os.IBinder iBinder, boolean z, boolean z2) {
        super(activityTaskManagerService.mWindowManager);
        this.mDimmer = com.android.server.wm.Dimmer.DIMMER_REFACTOR ? new com.android.server.wm.SmoothDimmer(this) : new com.android.server.wm.LegacyDimmer(this);
        this.mEmbeddedDimArea = 0;
        this.mPausingActivity = null;
        this.mLastPausedActivity = null;
        this.mResumedActivity = null;
        this.mTaskFragmentOrganizerUid = -1;
        this.mAnimationParams = android.window.TaskFragmentAnimationParams.DEFAULT;
        this.mForceHiddenFlags = 0;
        this.mForceTranslucent = false;
        this.mLastSurfaceSize = new android.graphics.Point();
        this.mTmpBounds = new android.graphics.Rect();
        this.mTmpAbsBounds = new android.graphics.Rect();
        this.mTmpFullBounds = new android.graphics.Rect();
        this.mTmpStableBounds = new android.graphics.Rect();
        this.mTmpNonDecorBounds = new android.graphics.Rect();
        this.mEnsureActivitiesVisibleHelper = new com.android.server.wm.EnsureActivitiesVisibleHelper(this);
        this.mAtmService = activityTaskManagerService;
        this.mTaskSupervisor = this.mAtmService.mTaskSupervisor;
        this.mRootWindowContainer = this.mAtmService.mRootWindowContainer;
        this.mCreatedByOrganizer = z;
        this.mIsEmbedded = z2;
        this.mRelativeEmbeddedBounds = z2 ? new android.graphics.Rect() : null;
        this.mTaskFragmentOrganizerController = this.mAtmService.mWindowOrganizerController.mTaskFragmentOrganizerController;
        this.mFragmentToken = iBinder;
        this.mRemoteToken = new com.android.server.wm.WindowContainer.RemoteToken(this);
    }

    @android.annotation.NonNull
    static com.android.server.wm.TaskFragment fromTaskFragmentToken(@android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        if (iBinder == null) {
            return null;
        }
        return activityTaskManagerService.mWindowOrganizerController.getTaskFragment(iBinder);
    }

    void setAdjacentTaskFragment(@android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment) {
        if (this.mAdjacentTaskFragment == taskFragment) {
            return;
        }
        resetAdjacentTaskFragment();
        if (taskFragment != null) {
            this.mAdjacentTaskFragment = taskFragment;
            taskFragment.setAdjacentTaskFragment(this);
        }
    }

    void setCompanionTaskFragment(@android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment) {
        this.mCompanionTaskFragment = taskFragment;
    }

    com.android.server.wm.TaskFragment getCompanionTaskFragment() {
        return this.mCompanionTaskFragment;
    }

    void resetAdjacentTaskFragment() {
        if (this.mAdjacentTaskFragment != null && this.mAdjacentTaskFragment.mAdjacentTaskFragment == this) {
            this.mAdjacentTaskFragment.mAdjacentTaskFragment = null;
            this.mAdjacentTaskFragment.mDelayLastActivityRemoval = false;
        }
        this.mAdjacentTaskFragment = null;
        this.mDelayLastActivityRemoval = false;
    }

    void setTaskFragmentOrganizer(@android.annotation.NonNull android.window.TaskFragmentOrganizerToken taskFragmentOrganizerToken, int i, @android.annotation.NonNull java.lang.String str) {
        this.mTaskFragmentOrganizer = android.window.ITaskFragmentOrganizer.Stub.asInterface(taskFragmentOrganizerToken.asBinder());
        this.mTaskFragmentOrganizerUid = i;
        this.mTaskFragmentOrganizerProcessName = str;
    }

    void onTaskFragmentOrganizerRemoved() {
        this.mTaskFragmentOrganizer = null;
    }

    boolean hasTaskFragmentOrganizer(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        return (iTaskFragmentOrganizer == null || this.mTaskFragmentOrganizer == null || !iTaskFragmentOrganizer.asBinder().equals(this.mTaskFragmentOrganizer.asBinder())) ? false : true;
    }

    @android.annotation.Nullable
    private com.android.server.wm.WindowProcessController getOrganizerProcessIfDifferent(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord != null && this.mTaskFragmentOrganizerProcessName != null) {
            if (this.mTaskFragmentOrganizerProcessName.equals(activityRecord.processName) && this.mTaskFragmentOrganizerUid == activityRecord.getUid()) {
                return null;
            }
            return this.mAtmService.getProcessController(this.mTaskFragmentOrganizerProcessName, this.mTaskFragmentOrganizerUid);
        }
        return null;
    }

    void setAnimationParams(@android.annotation.NonNull android.window.TaskFragmentAnimationParams taskFragmentAnimationParams) {
        this.mAnimationParams = taskFragmentAnimationParams;
    }

    @android.annotation.NonNull
    android.window.TaskFragmentAnimationParams getAnimationParams() {
        return this.mAnimationParams;
    }

    void setIsolatedNav(boolean z) {
        if (!isEmbedded()) {
            return;
        }
        this.mIsolatedNav = z;
    }

    void setAllowTransitionWhenEmpty(boolean z) {
        if (!isEmbedded()) {
            return;
        }
        this.mAllowTransitionWhenEmpty = z;
    }

    boolean isIsolatedNav() {
        return isEmbedded() && this.mIsolatedNav;
    }

    com.android.server.wm.TaskFragment getAdjacentTaskFragment() {
        return this.mAdjacentTaskFragment;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getTopResumedActivity() {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.ActivityRecord resumedActivity = getResumedActivity();
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            com.android.server.wm.WindowContainer childAt = getChildAt(childCount);
            if (resumedActivity != null && childAt == resumedActivity) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopResumedActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    com.android.server.wm.ActivityRecord getResumedActivity() {
        return this.mResumedActivity;
    }

    void setResumedActivity(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        warnForNonLeafTaskFragment("setResumedActivity");
        if (this.mResumedActivity == activityRecord) {
            return;
        }
        if (activityRecord != null && this.mResumedActivity == null) {
            getTask().touchActiveTime();
        }
        com.android.server.wm.ActivityRecord activityRecord2 = this.mResumedActivity;
        this.mResumedActivity = activityRecord;
        this.mTaskSupervisor.updateTopResumedActivityIfNeeded(str);
        if (activityRecord == null && activityRecord2.mDisplayContent != null && activityRecord2.mDisplayContent.getFocusedRootTask() == null) {
            activityRecord2.mDisplayContent.onRunningActivityChanged();
        } else if (activityRecord != null) {
            activityRecord.mDisplayContent.onRunningActivityChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPausingActivity(com.android.server.wm.ActivityRecord activityRecord) {
        this.mPausingActivity = activityRecord;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getTopPausingActivity() {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.ActivityRecord pausingActivity = getPausingActivity();
        int childCount = getChildCount();
        do {
            childCount--;
            activityRecord = null;
            if (childCount < 0) {
                return null;
            }
            com.android.server.wm.WindowContainer childAt = getChildAt(childCount);
            if (pausingActivity != null && childAt == pausingActivity) {
                activityRecord = childAt.asActivityRecord();
            } else if (childAt.asTaskFragment() != null) {
                activityRecord = childAt.asTaskFragment().getTopPausingActivity();
            }
        } while (activityRecord == null);
        return activityRecord;
    }

    com.android.server.wm.ActivityRecord getPausingActivity() {
        return this.mPausingActivity;
    }

    int getDisplayId() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            return displayContent.mDisplayId;
        }
        return -1;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getTask() {
        if (asTask() != null) {
            return asTask();
        }
        com.android.server.wm.TaskFragment asTaskFragment = getParent() != null ? getParent().asTaskFragment() : null;
        if (asTaskFragment != null) {
            return asTaskFragment.getTask();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    public com.android.server.wm.TaskDisplayArea getDisplayArea() {
        return (com.android.server.wm.TaskDisplayArea) super.getDisplayArea();
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isAttached() {
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        return (displayArea == null || displayArea.isRemoved()) ? false : true;
    }

    @android.annotation.NonNull
    com.android.server.wm.TaskFragment getRootTaskFragment() {
        com.android.server.wm.TaskFragment asTaskFragment;
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null && (asTaskFragment = parent.asTaskFragment()) != null) {
            return asTaskFragment.getRootTaskFragment();
        }
        return this;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask() {
        return getRootTaskFragment().asTask();
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.TaskFragment asTaskFragment() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    @com.android.server.wm.TaskFragment.EmbeddingCheckResult
    int isAllowedToEmbedActivity(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return isAllowedToEmbedActivity(activityRecord, this.mTaskFragmentOrganizerUid);
    }

    @com.android.server.wm.TaskFragment.EmbeddingCheckResult
    int isAllowedToEmbedActivity(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i) {
        if (!isAllowedToEmbedActivityInUntrustedMode(activityRecord) && !isAllowedToEmbedActivityInTrustedMode(activityRecord, i)) {
            return 1;
        }
        if (smallerThanMinDimension(activityRecord)) {
            return 2;
        }
        return 0;
    }

    boolean smallerThanMinDimension(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        android.graphics.Point minDimensions;
        android.graphics.Rect bounds = getBounds();
        com.android.server.wm.Task task = getTask();
        if (task == null || bounds.equals(task.getBounds()) || (minDimensions = activityRecord.getMinDimensions()) == null) {
            return false;
        }
        return bounds.width() < minDimensions.x || bounds.height() < minDimensions.y;
    }

    boolean isAllowedToEmbedActivityInUntrustedMode(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent == null || !parent.getBounds().contains(getBounds())) {
            return false;
        }
        return hasEmbedAnyAppInUntrustedModePermission(this.mTaskFragmentOrganizerUid) || (activityRecord.info.flags & 268435456) == 268435456;
    }

    boolean isAllowedToEmbedActivityInTrustedMode(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return isAllowedToEmbedActivityInTrustedMode(activityRecord, this.mTaskFragmentOrganizerUid);
    }

    boolean isAllowedToEmbedActivityInTrustedMode(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i) {
        if (isFullyTrustedEmbedding(activityRecord, i)) {
            return true;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mAtmService.getPackageManagerInternalLocked().getPackage(i);
        return androidPackage != null && isAllowedToEmbedActivityInTrustedModeByHostPackage(activityRecord, androidPackage);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isAllowedToEmbedActivityInTrustedModeByHostPackage(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.util.Set<java.lang.String> knownActivityEmbeddingCerts = activityRecord.info.getKnownActivityEmbeddingCerts();
        if (knownActivityEmbeddingCerts.isEmpty()) {
            knownActivityEmbeddingCerts = activityRecord.info.applicationInfo.getKnownActivityEmbeddingCerts();
        }
        return androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(knownActivityEmbeddingCerts);
    }

    private static boolean isFullyTrustedEmbedding(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i) {
        return android.os.UserHandle.getAppId(i) == 1000 || activityRecord.isUid(i) || hasManageTaskPermission(i);
    }

    private static boolean hasManageTaskPermission(int i) {
        return com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.MANAGE_ACTIVITY_TASKS", -1, i) == 0;
    }

    private static boolean hasEmbedAnyAppInUntrustedModePermission(int i) {
        return com.android.window.flags.Flags.untrustedEmbeddingAnyAppPermission() && com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.EMBED_ANY_APP_IN_UNTRUSTED_MODE", -1, i) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isFullyTrustedEmbedding$0(int i, com.android.server.wm.ActivityRecord activityRecord) {
        return !isFullyTrustedEmbedding(activityRecord, i);
    }

    boolean isFullyTrustedEmbedding(final int i) {
        return !forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isFullyTrustedEmbedding$0;
                lambda$isFullyTrustedEmbedding$0 = com.android.server.wm.TaskFragment.lambda$isFullyTrustedEmbedding$0(i, (com.android.server.wm.ActivityRecord) obj);
                return lambda$isFullyTrustedEmbedding$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isAllowedToBeEmbeddedInTrustedMode$1(com.android.server.wm.ActivityRecord activityRecord) {
        return !isAllowedToEmbedActivityInTrustedMode(activityRecord);
    }

    boolean isAllowedToBeEmbeddedInTrustedMode() {
        return !forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isAllowedToBeEmbeddedInTrustedMode$1;
                lambda$isAllowedToBeEmbeddedInTrustedMode$1 = com.android.server.wm.TaskFragment.this.lambda$isAllowedToBeEmbeddedInTrustedMode$1((com.android.server.wm.ActivityRecord) obj);
                return lambda$isAllowedToBeEmbeddedInTrustedMode$1;
            }
        });
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getOrganizedTaskFragment() {
        if (this.mTaskFragmentOrganizer != null) {
            return this;
        }
        com.android.server.wm.TaskFragment asTaskFragment = getParent() != null ? getParent().asTaskFragment() : null;
        if (asTaskFragment != null) {
            return asTaskFragment.getOrganizedTaskFragment();
        }
        return null;
    }

    private void warnForNonLeafTaskFragment(java.lang.String str) {
        if (!isLeafTaskFragment()) {
            android.util.Slog.w("ActivityTaskManager", str + " on non-leaf task fragment " + this);
        }
    }

    boolean hasDirectChildActivities() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asActivityRecord() != null) {
                return true;
            }
        }
        return false;
    }

    void cleanUpActivityReferences(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mPausingActivity != null && this.mPausingActivity == activityRecord) {
            this.mPausingActivity = null;
        }
        if (this.mResumedActivity != null && this.mResumedActivity == activityRecord) {
            setResumedActivity(null, "cleanUpActivityReferences");
        }
        activityRecord.removeTimeouts();
    }

    protected boolean isForceHidden() {
        return this.mForceHiddenFlags != 0;
    }

    boolean setForceHidden(@com.android.server.wm.TaskFragment.FlagForceHidden int i, boolean z) {
        int i2;
        int i3 = this.mForceHiddenFlags;
        if (z) {
            i2 = i | i3;
        } else {
            i2 = (~i) & i3;
        }
        if (this.mForceHiddenFlags == i2) {
            return false;
        }
        this.mForceHiddenFlags = i2;
        return true;
    }

    boolean isForceTranslucent() {
        return this.mForceTranslucent;
    }

    void setForceTranslucent(boolean z) {
        this.mForceTranslucent = z;
    }

    boolean isLeafTaskFragment() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTaskFragment() != null) {
                return false;
            }
        }
        return true;
    }

    void onActivityStateChanged(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord.State state, java.lang.String str) {
        warnForNonLeafTaskFragment("onActivityStateChanged");
        if (activityRecord == this.mResumedActivity && state != com.android.server.wm.ActivityRecord.State.RESUMED) {
            setResumedActivity(null, str + " - onActivityStateChanged");
        }
        if (state == com.android.server.wm.ActivityRecord.State.RESUMED) {
            setResumedActivity(activityRecord, str + " - onActivityStateChanged");
            this.mTaskSupervisor.mRecentTasks.add(activityRecord.getTask());
        }
        com.android.server.wm.WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(activityRecord);
        if (organizerProcessIfDifferent != null) {
            this.mTaskSupervisor.onProcessActivityStateChanged(organizerProcessIfDifferent, false);
            organizerProcessIfDifferent.updateProcessInfo(false, true, true, false);
        }
    }

    boolean handleAppDied(com.android.server.wm.WindowProcessController windowProcessController) {
        boolean z;
        warnForNonLeafTaskFragment("handleAppDied");
        if (this.mPausingActivity != null && this.mPausingActivity.app == windowProcessController) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 646076184396185067L, 0, null, java.lang.String.valueOf(this.mPausingActivity));
            this.mPausingActivity = null;
            z = true;
        } else {
            z = false;
        }
        if (this.mLastPausedActivity != null && this.mLastPausedActivity.app == windowProcessController) {
            this.mLastPausedActivity = null;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void awakeFromSleeping() {
        if (this.mPausingActivity != null) {
            android.util.Slog.d("ActivityTaskManager", "awakeFromSleeping: previously pausing activity didn't pause");
            this.mPausingActivity.activityPaused(true);
        }
    }

    boolean sleepIfPossible(boolean z) {
        boolean z2;
        boolean z3 = false;
        if (this.mResumedActivity != null) {
            if (!z && this.mResumedActivity.canTurnScreenOn()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -7596917112222697106L, 0, null, java.lang.String.valueOf(this.mResumedActivity));
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -8472961767591168851L, 0, null, java.lang.String.valueOf(this.mResumedActivity));
                startPausing(false, true, null, "sleep");
            }
            z2 = false;
        } else if (this.mPausingActivity == null) {
            z2 = true;
        } else {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -1472885369931482317L, 0, null, java.lang.String.valueOf(this.mPausingActivity));
            z2 = false;
        }
        if (!z && containsStoppingActivity()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -2693016397674039814L, 1, null, java.lang.Long.valueOf(this.mTaskSupervisor.mStoppingActivities.size()));
            this.mTaskSupervisor.scheduleIdle();
        } else {
            z3 = z2;
        }
        if (z3) {
            updateActivityVisibilities(null, true);
        }
        return z3;
    }

    private boolean containsStoppingActivity() {
        for (int size = this.mTaskSupervisor.mStoppingActivities.size() - 1; size >= 0; size--) {
            if (this.mTaskSupervisor.mStoppingActivities.get(size).getTaskFragment() == this) {
                return true;
            }
        }
        return false;
    }

    boolean isTranslucent(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        return !isAttached() || isForceHidden() || isForceTranslucent() || this.mTaskSupervisor.mOpaqueActivityHelper.getVisibleOpaqueActivity(this, activityRecord, true) == null;
    }

    boolean isTranslucentForTransition() {
        return !isAttached() || isForceHidden() || isForceTranslucent() || this.mTaskSupervisor.mOpaqueActivityHelper.getOpaqueActivity(this, true) == null;
    }

    boolean isTranslucentAndVisible() {
        return !isAttached() || isForceHidden() || isForceTranslucent() || this.mTaskSupervisor.mOpaqueActivityHelper.getVisibleOpaqueActivity(this, null, false) == null;
    }

    com.android.server.wm.ActivityRecord getTopNonFinishingActivity() {
        return getTopNonFinishingActivity(true);
    }

    com.android.server.wm.ActivityRecord getTopNonFinishingActivity(boolean z) {
        if (z) {
            return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getTopNonFinishingActivity$2;
                    lambda$getTopNonFinishingActivity$2 = com.android.server.wm.TaskFragment.lambda$getTopNonFinishingActivity$2((com.android.server.wm.ActivityRecord) obj);
                    return lambda$getTopNonFinishingActivity$2;
                }
            });
        }
        return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopNonFinishingActivity$3;
                lambda$getTopNonFinishingActivity$3 = com.android.server.wm.TaskFragment.lambda$getTopNonFinishingActivity$3((com.android.server.wm.ActivityRecord) obj);
                return lambda$getTopNonFinishingActivity$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopNonFinishingActivity$2(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopNonFinishingActivity$3(com.android.server.wm.ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isTaskOverlay()) ? false : true;
    }

    com.android.server.wm.ActivityRecord topRunningActivity() {
        return topRunningActivity(false);
    }

    com.android.server.wm.ActivityRecord topRunningActivity(boolean z) {
        if (z) {
            return getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$topRunningActivity$4;
                    lambda$topRunningActivity$4 = com.android.server.wm.TaskFragment.lambda$topRunningActivity$4((com.android.server.wm.ActivityRecord) obj);
                    return lambda$topRunningActivity$4;
                }
            });
        }
        return getActivity(new com.android.server.wm.Task$$ExternalSyntheticLambda21());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$topRunningActivity$4(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.canBeTopRunning() && activityRecord.isFocusable();
    }

    int getNonFinishingActivityCount() {
        final int[] iArr = new int[1];
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskFragment.lambda$getNonFinishingActivityCount$5(iArr, (com.android.server.wm.ActivityRecord) obj);
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getNonFinishingActivityCount$5(int[] iArr, com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.finishing) {
            iArr[0] = iArr[0] + 1;
        }
    }

    boolean hasNonFinishingDirectActivity() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.ActivityRecord asActivityRecord = getChildAt(childCount).asActivityRecord();
            if (asActivityRecord != null && !asActivityRecord.finishing) {
                return true;
            }
        }
        return false;
    }

    boolean isTopActivityFocusable() {
        com.android.server.wm.ActivityRecord activityRecord = topRunningActivity();
        return activityRecord != null ? activityRecord.isFocusable() : isFocusable() && getWindowConfiguration().canReceiveKeys();
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x010b, code lost:
    
        if (r11 != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x010d, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x010e, code lost:
    
        if (r3 == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0113, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:?, code lost:
    
        return 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x010a A[SYNTHETIC] */
    @com.android.server.wm.TaskFragment.TaskFragmentVisibility
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int getVisibility(com.android.server.wm.ActivityRecord activityRecord) {
        boolean z;
        int childCount;
        boolean z2;
        if (!isAttached() || isForceHidden()) {
            return 2;
        }
        if (isTopActivityLaunchedBehind()) {
            return 0;
        }
        com.android.server.wm.WindowContainer parent = getParent();
        com.android.server.wm.Task asTask = asTask();
        if (asTask != null && parent.asTask() == null && this.mTransitionController.isTransientVisible(asTask)) {
            return 0;
        }
        if (parent.asTaskFragment() != null) {
            int visibility = parent.asTaskFragment().getVisibility(activityRecord);
            if (visibility == 2) {
                return 2;
            }
            if (visibility == 1) {
                z = true;
                java.util.ArrayList arrayList = new java.util.ArrayList();
                childCount = parent.getChildCount() - 1;
                boolean z3 = false;
                while (true) {
                    if (childCount < 0) {
                        z2 = true;
                        break;
                    }
                    com.android.server.wm.WindowContainer childAt = parent.getChildAt(childCount);
                    if (childAt != null) {
                        boolean hasRunningActivity = hasRunningActivity(childAt);
                        if (childAt == this) {
                            if (!arrayList.isEmpty() && !z3) {
                                ((com.android.server.wm.WindowContainer) this).mTmpRect.set(getBounds());
                                for (int size = arrayList.size() - 1; size >= 0; size--) {
                                    com.android.server.wm.TaskFragment taskFragment = (com.android.server.wm.TaskFragment) arrayList.get(size);
                                    com.android.server.wm.TaskFragment taskFragment2 = taskFragment.mAdjacentTaskFragment;
                                    if (taskFragment2 != this && (((com.android.server.wm.WindowContainer) this).mTmpRect.intersect(taskFragment.getBounds()) || ((com.android.server.wm.WindowContainer) this).mTmpRect.intersect(taskFragment2.getBounds()))) {
                                        return 2;
                                    }
                                }
                            }
                            z2 = hasRunningActivity || (activityRecord != null && activityRecord.isDescendantOf(this)) || isActivityTypeHome();
                        } else if (hasRunningActivity) {
                            int windowingMode = childAt.getWindowingMode();
                            if (windowingMode == 1 || (windowingMode != 2 && childAt.matchParentBounds())) {
                                if (!isTranslucent(childAt, activityRecord)) {
                                    return 2;
                                }
                                z = true;
                            } else {
                                com.android.server.wm.TaskFragment asTaskFragment = childAt.asTaskFragment();
                                if (asTaskFragment != null && asTaskFragment.mAdjacentTaskFragment != null) {
                                    if (arrayList.contains(asTaskFragment.mAdjacentTaskFragment)) {
                                        if (!asTaskFragment.isTranslucent(activityRecord) && !asTaskFragment.mAdjacentTaskFragment.isTranslucent(activityRecord)) {
                                            return 2;
                                        }
                                        z = true;
                                        z3 = true;
                                    } else {
                                        arrayList.add(asTaskFragment);
                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    childCount--;
                }
            }
        }
        z = false;
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        childCount = parent.getChildCount() - 1;
        boolean z32 = false;
        while (true) {
            if (childCount < 0) {
            }
            childCount--;
        }
    }

    private static boolean hasRunningActivity(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.asTaskFragment() != null ? windowContainer.asTaskFragment().topRunningActivity() != null : (windowContainer.asActivityRecord() == null || windowContainer.asActivityRecord().finishing) ? false : true;
    }

    private static boolean isTranslucent(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.ActivityRecord activityRecord) {
        if (windowContainer.asTaskFragment() != null) {
            return windowContainer.asTaskFragment().isTranslucent(activityRecord);
        }
        if (windowContainer.asActivityRecord() != null) {
            return !windowContainer.asActivityRecord().occludesParent();
        }
        return false;
    }

    private boolean isTopActivityLaunchedBehind() {
        com.android.server.wm.ActivityRecord activityRecord = topRunningActivity();
        return activityRecord != null && activityRecord.mLaunchTaskBehind;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void updateActivityVisibilities(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        this.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            this.mEnsureActivitiesVisibleHelper.process(activityRecord, z);
        } finally {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    final boolean resumeTopActivity(com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions, boolean z) {
        com.android.server.wm.ActivityRecord activityRecord2;
        boolean z2;
        boolean z3;
        com.android.server.wm.ActivityRecord activityRecord3 = topRunningActivity(true);
        if (activityRecord3 == null || !activityRecord3.canResumeByCompat()) {
            return false;
        }
        activityRecord3.delayedResume = false;
        if (!z && !this.mRootWindowContainer.allPausedActivitiesComplete()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 8892147402270850613L, 0, null, null);
            return false;
        }
        com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
        android.app.servertransaction.ClientTransaction clientTransaction = null;
        if (this.mResumedActivity == activityRecord3 && activityRecord3.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && displayArea.allResumedActivitiesComplete()) {
            displayArea.ensureActivitiesVisible(null, true);
            executeAppTransition(activityOptions);
            if (displayArea.inMultiWindowMode() && displayArea.mDisplayContent != null && displayArea.mDisplayContent.mFocusedApp != activityRecord3) {
                displayArea.mDisplayContent.setFocusedApp(activityRecord3);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 958293038551087087L, 0, null, java.lang.String.valueOf(activityRecord3));
            return false;
        }
        if (this.mLastPausedActivity == activityRecord3 && shouldSleepOrShutDownActivities()) {
            executeAppTransition(activityOptions);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4340810061306869942L, 0, null, null);
            return false;
        }
        if (!this.mAtmService.mAmInternal.hasStartedUserState(activityRecord3.mUserId)) {
            android.util.Slog.w("ActivityTaskManager", "Skipping resume of top activity " + activityRecord3 + ": user " + activityRecord3.mUserId + " is stopped");
            return false;
        }
        this.mTaskSupervisor.mStoppingActivities.remove(activityRecord3);
        this.mTaskSupervisor.setLaunchSource(activityRecord3.info.applicationInfo.uid);
        com.android.server.wm.Task lastFocusedRootTask = displayArea.getLastFocusedRootTask();
        if (lastFocusedRootTask != null && lastFocusedRootTask != getRootTaskFragment().asTask()) {
            activityRecord2 = lastFocusedRootTask.getTopResumedActivity();
        } else {
            activityRecord2 = null;
        }
        boolean z4 = !z && displayArea.pauseBackTasks(activityRecord3);
        if (this.mResumedActivity != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -7681635901109618685L, 0, null, java.lang.String.valueOf(this.mResumedActivity));
            z4 |= startPausing(this.mTaskSupervisor.mUserLeaving, false, activityRecord3, "resumeTopActivity");
        }
        if (z4) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -3463034909521330970L, 0, null, null);
            if (activityRecord3.attachedToProcess()) {
                activityRecord3.app.updateProcessInfo(false, true, false, false);
            } else if (!activityRecord3.isProcessRunning()) {
                boolean z5 = this == displayArea.getFocusedRootTask();
                this.mAtmService.startProcessAsync(activityRecord3, false, z5, z5 ? com.android.server.am.HostingRecord.HOSTING_TYPE_NEXT_TOP_ACTIVITY : com.android.server.am.HostingRecord.HOSTING_TYPE_NEXT_ACTIVITY);
            }
            if (activityRecord2 != null) {
                activityRecord2.setWillCloseOrEnterPip(true);
            }
            return true;
        }
        if (this.mResumedActivity == activityRecord3 && activityRecord3.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && displayArea.allResumedActivitiesComplete()) {
            executeAppTransition(activityOptions);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -2264725269594226780L, 0, null, java.lang.String.valueOf(activityRecord3));
            return true;
        }
        if (shouldSleepActivities()) {
            this.mTaskSupervisor.finishNoHistoryActivitiesIfNeeded(activityRecord3);
        }
        if (activityRecord != null && activityRecord != activityRecord3 && activityRecord3.nowVisible && activityRecord.finishing) {
            activityRecord.setVisibility(false);
        }
        try {
            this.mTaskSupervisor.getActivityMetricsLogger().notifyBeforePackageUnstopped(activityRecord3.packageName);
            this.mAtmService.getPackageManagerInternalLocked().notifyComponentUsed(activityRecord3.packageName, activityRecord3.mUserId, activityRecord3.packageName, activityRecord3.toString());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w("ActivityTaskManager", "Failed trying to unstop package " + activityRecord3.packageName + ": " + e);
        }
        com.android.server.wm.DisplayContent displayContent = displayArea.mDisplayContent;
        if (activityRecord != null) {
            if (activityRecord.finishing) {
                if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord)) {
                    displayContent.prepareAppTransition(0);
                    z2 = false;
                } else {
                    displayContent.prepareAppTransition(2);
                    z2 = true;
                }
                activityRecord.setVisibility(false);
            } else if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord3)) {
                displayContent.prepareAppTransition(0);
                z2 = false;
            } else {
                displayContent.prepareAppTransition(1, activityRecord3.mLaunchTaskBehind ? 32 : 0);
                z2 = true;
            }
        } else if (this.mTaskSupervisor.mNoAnimActivities.contains(activityRecord3)) {
            displayContent.prepareAppTransition(0);
            z2 = false;
        } else {
            displayContent.prepareAppTransition(1);
            z2 = true;
        }
        if (z2) {
            activityRecord3.applyOptionsAnimation();
        } else {
            activityRecord3.abortAndClearOptionsAnimation();
        }
        this.mTaskSupervisor.mNoAnimActivities.clear();
        if (activityRecord3.attachedToProcess()) {
            boolean z6 = inMultiWindowMode() || !(this.mLastPausedActivity == null || this.mLastPausedActivity.occludesParent());
            if (!activityRecord3.isVisibleRequested() || activityRecord3.mAppStopped || z6) {
                activityRecord3.app.addToPendingTop();
                activityRecord3.setVisibility(true);
            }
            activityRecord3.startLaunchTickingLocked();
            com.android.server.wm.ActivityRecord topResumedActivity = lastFocusedRootTask == null ? null : lastFocusedRootTask.getTopResumedActivity();
            com.android.server.wm.ActivityRecord.State state = activityRecord3.getState();
            this.mAtmService.updateCpuStats();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -8359248677489986541L, 0, null, java.lang.String.valueOf(activityRecord3));
            activityRecord3.setState(com.android.server.wm.ActivityRecord.State.RESUMED, "resumeTopActivity");
            if (shouldBeVisible(activityRecord3)) {
                z3 = !this.mRootWindowContainer.ensureVisibilityAndConfig(activityRecord3, getDisplayId(), false);
            } else {
                z3 = true;
            }
            if (z3) {
                com.android.server.wm.ActivityRecord activityRecord4 = topRunningActivity();
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 2088177629189452176L, 0, null, java.lang.String.valueOf(activityRecord3), java.lang.String.valueOf(activityRecord4));
                if (activityRecord4 != activityRecord3) {
                    this.mTaskSupervisor.scheduleResumeTopActivities();
                }
                if (!activityRecord3.isVisibleRequested() || activityRecord3.mAppStopped) {
                    activityRecord3.setVisibility(true);
                }
                activityRecord3.completeResumeLocked();
                return true;
            }
            try {
                android.app.IApplicationThread thread = activityRecord3.app.getThread();
                if (!com.android.window.flags.Flags.bundleClientTransactionFlag()) {
                    clientTransaction = android.app.servertransaction.ClientTransaction.obtain(thread);
                }
                java.util.ArrayList<android.app.ResultInfo> arrayList = activityRecord3.results;
                if (arrayList != null) {
                    int size = arrayList.size();
                    if (!activityRecord3.finishing && size > 0) {
                        android.app.servertransaction.ClientTransactionItem obtain = android.app.servertransaction.ActivityResultItem.obtain(activityRecord3.token, arrayList);
                        if (clientTransaction == null) {
                            this.mAtmService.getLifecycleManager().scheduleTransactionItem(thread, obtain);
                        } else {
                            clientTransaction.addTransactionItem(obtain);
                        }
                    }
                }
                if (activityRecord3.newIntents != null) {
                    android.app.servertransaction.ClientTransactionItem obtain2 = android.app.servertransaction.NewIntentItem.obtain(activityRecord3.token, activityRecord3.newIntents, true);
                    if (clientTransaction == null) {
                        this.mAtmService.getLifecycleManager().scheduleTransactionItem(thread, obtain2);
                    } else {
                        clientTransaction.addTransactionItem(obtain2);
                    }
                }
                activityRecord3.notifyAppResumed();
                com.android.server.wm.EventLogTags.writeWmResumeActivity(activityRecord3.mUserId, java.lang.System.identityHashCode(activityRecord3), activityRecord3.getTask().mTaskId, activityRecord3.shortComponentName);
                this.mAtmService.getAppWarningsLocked().onResumeActivity(activityRecord3);
                int i = this.mAtmService.mTopProcessState;
                activityRecord3.app.setPendingUiCleanAndForceProcessStateUpTo(i);
                activityRecord3.abortAndClearOptionsAnimation();
                android.app.servertransaction.ClientTransactionItem obtain3 = android.app.servertransaction.ResumeActivityItem.obtain(activityRecord3.token, i, displayContent.isNextTransitionForward(), activityRecord3.shouldSendCompatFakeFocus());
                if (clientTransaction == null) {
                    this.mAtmService.getLifecycleManager().scheduleTransactionItem(thread, obtain3);
                } else {
                    clientTransaction.addTransactionItem(obtain3);
                    this.mAtmService.getLifecycleManager().scheduleTransaction(clientTransaction);
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -8483536760290526299L, 0, null, java.lang.String.valueOf(activityRecord3));
                try {
                    activityRecord3.completeResumeLocked();
                } catch (java.lang.Exception e2) {
                    android.util.Slog.w("ActivityTaskManager", "Exception thrown during resume of " + activityRecord3, e2);
                    activityRecord3.finishIfPossible("resume-exception", true);
                    return true;
                }
            } catch (java.lang.Exception e3) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -4911500660485375799L, 0, null, java.lang.String.valueOf(state), java.lang.String.valueOf(activityRecord3));
                activityRecord3.setState(state, "resumeTopActivityInnerLocked");
                if (topResumedActivity != null) {
                    topResumedActivity.setState(com.android.server.wm.ActivityRecord.State.RESUMED, "resumeTopActivityInnerLocked");
                }
                android.util.Slog.i("ActivityTaskManager", "Restarting because process died: " + activityRecord3);
                if (!activityRecord3.hasBeenLaunched) {
                    activityRecord3.hasBeenLaunched = true;
                } else if (lastFocusedRootTask != null && lastFocusedRootTask.isTopRootTaskInDisplayArea()) {
                    activityRecord3.showStartingWindow(false);
                }
                this.mTaskSupervisor.startSpecificActivity(activityRecord3, true, false);
                return true;
            }
        } else {
            if (!activityRecord3.hasBeenLaunched) {
                activityRecord3.hasBeenLaunched = true;
            } else {
                activityRecord3.showStartingWindow(false);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3723891427717889172L, 0, null, java.lang.String.valueOf(activityRecord3));
            this.mTaskSupervisor.startSpecificActivity(activityRecord3, true, true);
        }
        return true;
    }

    boolean shouldSleepOrShutDownActivities() {
        return shouldSleepActivities() || this.mAtmService.mShuttingDown;
    }

    boolean shouldBeVisible(com.android.server.wm.ActivityRecord activityRecord) {
        return getVisibility(activityRecord) != 2;
    }

    boolean canBeResumed(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        return isTopActivityFocusable() && getVisibility(activityRecord) == 0;
    }

    boolean isFocusableAndVisible() {
        return isTopActivityFocusable() && shouldBeVisible(null);
    }

    final boolean startPausing(boolean z, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        return startPausing(this.mTaskSupervisor.mUserLeaving, z, activityRecord, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x015b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean startPausing(boolean z, boolean z2, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        boolean z3;
        boolean z4;
        if (!hasDirectChildActivities()) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1529152423206006904L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mResumedActivity));
        if (this.mPausingActivity != null) {
            android.util.Slog.wtf("ActivityTaskManager", "Going to pause when pause is already pending for " + this.mPausingActivity + " state=" + this.mPausingActivity.getState());
            if (!shouldSleepActivities()) {
                completePause(false, activityRecord);
            }
        }
        com.android.server.wm.ActivityRecord activityRecord2 = this.mResumedActivity;
        if (activityRecord2 == null) {
            if (activityRecord == null) {
                android.util.Slog.wtf("ActivityTaskManager", "Trying to pause when nothing is resumed");
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
            return false;
        }
        if (activityRecord2 == activityRecord) {
            android.util.Slog.wtf("ActivityTaskManager", "Trying to pause activity that is in process of being resumed");
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 136971836458873178L, 0, null, java.lang.String.valueOf(activityRecord2));
        this.mPausingActivity = activityRecord2;
        this.mLastPausedActivity = activityRecord2;
        if (!activityRecord2.finishing && activityRecord2.isNoHistory() && !this.mTaskSupervisor.mNoHistoryActivities.contains(activityRecord2)) {
            this.mTaskSupervisor.mNoHistoryActivities.add(activityRecord2);
        }
        activityRecord2.setState(com.android.server.wm.ActivityRecord.State.PAUSING, "startPausingLocked");
        activityRecord2.getTask().touchActiveTime();
        this.mAtmService.updateCpuStats();
        if (activityRecord != null) {
            boolean occludesParent = activityRecord.occludesParent();
            boolean checkEnterPictureInPictureState = activityRecord2.checkEnterPictureInPictureState("shouldAutoPipWhilePausing", z);
            if (com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                com.android.server.wm.Task.enableEnterPipOnTaskSwitch(activityRecord2, activityRecord.getTask(), activityRecord, activityRecord.getOptions());
            }
            if (activityRecord2.supportsEnterPipOnTaskSwitch && z && occludesParent && checkEnterPictureInPictureState && activityRecord2.pictureInPictureArgs.isAutoEnterEnabled()) {
                z4 = false;
                z3 = true;
            } else if (!checkEnterPictureInPictureState) {
                z4 = (activityRecord.info.flags & 16384) != 0;
                z3 = false;
            }
            if (!activityRecord2.attachedToProcess()) {
                if (z3 && com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                    activityRecord2.mPauseSchedulePendingForPip = true;
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -208996201631695262L, 12, null, java.lang.String.valueOf(activityRecord2), java.lang.Boolean.valueOf(this.mAtmService.prepareAutoEnterPictureAndPictureMode(activityRecord2)));
                } else if (z3) {
                    activityRecord2.mPauseSchedulePendingForPip = true;
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -4123447037565780632L, 12, null, java.lang.String.valueOf(activityRecord2), java.lang.Boolean.valueOf(this.mAtmService.enterPictureInPictureMode(activityRecord2, activityRecord2.pictureInPictureArgs, false)));
                } else {
                    schedulePauseActivity(activityRecord2, z, z4, false, str);
                }
            } else {
                this.mPausingActivity = null;
                this.mLastPausedActivity = null;
                this.mTaskSupervisor.mNoHistoryActivities.remove(activityRecord2);
            }
            if (!z2 && !this.mAtmService.isSleepingOrShuttingDownLocked()) {
                this.mTaskSupervisor.acquireLaunchWakelock();
            }
            if (this.mPausingActivity == null) {
                if (!z2) {
                    activityRecord2.pauseKeyDispatchingLocked();
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -3710776151994843320L, 0, null, null);
                }
                if (z4) {
                    completePause(false, activityRecord);
                    return false;
                }
                activityRecord2.schedulePauseTimeout();
                if (!z2) {
                    this.mTransitionController.setReady(this, false);
                }
                return true;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 8543865526552245064L, 0, null, null);
            if (activityRecord == null) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
            return false;
        }
        z3 = false;
        z4 = false;
        if (!activityRecord2.attachedToProcess()) {
        }
        if (!z2) {
            this.mTaskSupervisor.acquireLaunchWakelock();
        }
        if (this.mPausingActivity == null) {
        }
    }

    void schedulePauseActivity(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, boolean z3, java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1917394294249960915L, 0, null, java.lang.String.valueOf(activityRecord));
        try {
            activityRecord.mPauseSchedulePendingForPip = false;
            com.android.server.wm.EventLogTags.writeWmPauseActivity(activityRecord.mUserId, java.lang.System.identityHashCode(activityRecord), activityRecord.shortComponentName, "userLeaving=" + z, str);
            this.mAtmService.getLifecycleManager().scheduleTransactionItem(activityRecord.app.getThread(), android.app.servertransaction.PauseActivityItem.obtain(activityRecord.token, activityRecord.finishing, z, activityRecord.configChangeFlags, z2, z3));
        } catch (java.lang.Exception e) {
            android.util.Slog.w("ActivityTaskManager", "Exception thrown during pause", e);
            this.mPausingActivity = null;
            this.mLastPausedActivity = null;
            this.mTaskSupervisor.mNoHistoryActivities.remove(activityRecord);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void completePause(boolean z, com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityRecord activityRecord2 = this.mPausingActivity;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -8936154984341817384L, 0, null, java.lang.String.valueOf(activityRecord2));
        if (activityRecord2 != null) {
            activityRecord2.setWillCloseOrEnterPip(false);
            boolean isState = activityRecord2.isState(com.android.server.wm.ActivityRecord.State.STOPPING);
            activityRecord2.setState(com.android.server.wm.ActivityRecord.State.PAUSED, "completePausedLocked");
            if (activityRecord2.finishing) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4971958459026584561L, 0, null, java.lang.String.valueOf(activityRecord2));
                activityRecord2 = activityRecord2.completeFinishing(false, "completePausedLocked");
            } else if (activityRecord2.attachedToProcess()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -7113165071559345173L, 60, null, java.lang.String.valueOf(activityRecord2), java.lang.Boolean.valueOf(isState), java.lang.Boolean.valueOf(activityRecord2.isVisibleRequested()));
                if (isState) {
                    activityRecord2.setState(com.android.server.wm.ActivityRecord.State.STOPPING, "completePausedLocked");
                } else if (!activityRecord2.isVisibleRequested() || shouldSleepOrShutDownActivities()) {
                    activityRecord2.setDeferHidingClient(false);
                    activityRecord2.addToStopping(true, false, "completePauseLocked");
                }
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -3777748052684097788L, 0, null, java.lang.String.valueOf(activityRecord2));
                activityRecord2 = null;
            }
            if (activityRecord2 != null) {
                activityRecord2.stopFreezingScreen(true, true);
            }
            this.mPausingActivity = null;
        }
        if (z) {
            com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null && !topDisplayFocusedRootTask.shouldSleepOrShutDownActivities()) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities(topDisplayFocusedRootTask, activityRecord2, null);
            } else {
                com.android.server.wm.ActivityRecord activityRecord3 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity() : null;
                if (activityRecord3 == null || (activityRecord2 != null && activityRecord3 != activityRecord2)) {
                    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                }
            }
        }
        if (activityRecord2 != null) {
            activityRecord2.resumeKeyDispatchingLocked();
        }
        this.mRootWindowContainer.ensureActivitiesVisible(activityRecord);
        if (this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause || (getDisplayArea() != null && getDisplayArea().hasPinnedTask())) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskStackChanged();
            this.mTaskSupervisor.mAppVisibilitiesChangedSinceLastPause = false;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    int getOrientation(int i) {
        if (shouldReportOrientationUnspecified()) {
            return -1;
        }
        if (canSpecifyOrientation()) {
            return super.getOrientation(i);
        }
        return -2;
    }

    boolean canSpecifyOrientation() {
        int windowingMode = getWindowingMode();
        int activityType = getActivityType();
        return windowingMode == 1 || activityType == 2 || activityType == 3 || activityType == 4;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean providesOrientation() {
        return super.providesOrientation() || shouldReportOrientationUnspecified();
    }

    private boolean shouldReportOrientationUnspecified() {
        return getAdjacentTaskFragment() != null && isVisibleRequested();
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllTaskFragments(java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer, boolean z) {
        super.forAllTaskFragments(consumer, z);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllLeafTaskFragments(java.util.function.Consumer<com.android.server.wm.TaskFragment> consumer, boolean z) {
        int size = this.mChildren.size();
        boolean z2 = true;
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                com.android.server.wm.TaskFragment asTaskFragment = ((com.android.server.wm.WindowContainer) this.mChildren.get(i)).asTaskFragment();
                if (asTaskFragment != null) {
                    asTaskFragment.forAllLeafTaskFragments(consumer, z);
                    z2 = false;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.wm.TaskFragment asTaskFragment2 = ((com.android.server.wm.WindowContainer) this.mChildren.get(i2)).asTaskFragment();
                if (asTaskFragment2 != null) {
                    asTaskFragment2.forAllLeafTaskFragments(consumer, z);
                    z2 = false;
                }
            }
        }
        if (z2) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllLeafTaskFragments(java.util.function.Predicate<com.android.server.wm.TaskFragment> predicate) {
        boolean z = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.TaskFragment asTaskFragment = ((com.android.server.wm.WindowContainer) this.mChildren.get(size)).asTaskFragment();
            if (asTaskFragment != null) {
                if (asTaskFragment.forAllLeafTaskFragments(predicate)) {
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

    void addChild(com.android.server.wm.ActivityRecord activityRecord) {
        addChild(activityRecord, Integer.MAX_VALUE);
    }

    @Override // com.android.server.wm.WindowContainer
    void addChild(com.android.server.wm.WindowContainer windowContainer, int i) {
        boolean z;
        boolean z2;
        topRunningActivity();
        this.mClearedTaskForReuse = false;
        this.mClearedTaskFragmentForPip = false;
        this.mClearedForReorderActivityToFront = false;
        com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord == null) {
            z = false;
        } else {
            z = true;
        }
        com.android.server.wm.Task task = z ? getTask() : null;
        if (task == null || task.getTopMostActivity() == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        int activityType = task != null ? task.getActivityType() : 0;
        super.addChild((com.android.server.wm.TaskFragment) windowContainer, i);
        if (z && task != null) {
            asActivityRecord.inHistory = true;
            task.onDescendantActivityAdded(z2, activityType, asActivityRecord);
        }
        com.android.server.wm.WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            organizerProcessIfDifferent.addEmbeddedActivity(asActivityRecord);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        sendTaskFragmentInfoChanged();
    }

    void executeAppTransition(android.app.ActivityOptions activityOptions) {
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.RemoteAnimationTarget createRemoteAnimationTarget(com.android.server.wm.RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        com.android.server.wm.ActivityRecord topMostActivity;
        if (remoteAnimationRecord.getMode() == 0) {
            topMostActivity = getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$createRemoteAnimationTarget$6;
                    lambda$createRemoteAnimationTarget$6 = com.android.server.wm.TaskFragment.lambda$createRemoteAnimationTarget$6((com.android.server.wm.ActivityRecord) obj);
                    return lambda$createRemoteAnimationTarget$6;
                }
            });
        } else {
            topMostActivity = getTopMostActivity();
        }
        if (topMostActivity != null) {
            return topMostActivity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createRemoteAnimationTarget$6(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing && activityRecord.hasChild();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canCreateRemoteAnimationTarget() {
        return true;
    }

    boolean shouldSleepActivities() {
        com.android.server.wm.Task rootTask = getRootTask();
        return rootTask != null && rootTask.shouldSleepActivities();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        this.mTmpBounds.set(getResolvedOverrideConfiguration().windowConfiguration.getBounds());
        super.resolveOverrideConfiguration(configuration);
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        if (this.mRelativeEmbeddedBounds != null && !this.mRelativeEmbeddedBounds.isEmpty()) {
            resolvedOverrideConfiguration.windowConfiguration.setBounds(translateRelativeBoundsToAbsoluteBounds(this.mRelativeEmbeddedBounds, configuration.windowConfiguration.getBounds()));
        }
        int windowingMode = resolvedOverrideConfiguration.windowConfiguration.getWindowingMode();
        int windowingMode2 = configuration.windowConfiguration.getWindowingMode();
        if (getActivityType() == 2 && windowingMode == 0) {
            resolvedOverrideConfiguration.windowConfiguration.setWindowingMode(1);
            windowingMode = 1;
        }
        if (!supportsMultiWindow()) {
            if (windowingMode != 0) {
                windowingMode2 = windowingMode;
            }
            if (android.app.WindowConfiguration.inMultiWindowMode(windowingMode2) && windowingMode2 != 2) {
                resolvedOverrideConfiguration.windowConfiguration.setWindowingMode(1);
            }
        }
        com.android.server.wm.Task asTask = asTask();
        if (asTask != null) {
            asTask.resolveLeafTaskOnlyOverrideConfigs(configuration, this.mTmpBounds);
        }
        computeConfigResourceOverrides(resolvedOverrideConfiguration, configuration);
    }

    boolean supportsMultiWindow() {
        return supportsMultiWindowInDisplayArea(getDisplayArea());
    }

    boolean supportsMultiWindowInDisplayArea(@android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.Task task;
        if (!this.mAtmService.mSupportsMultiWindow || taskDisplayArea == null || (task = getTask()) == null) {
            return false;
        }
        if (!task.isResizeable() && !taskDisplayArea.supportsNonResizableMultiWindow()) {
            return false;
        }
        com.android.server.wm.ActivityRecord rootActivity = task.getRootActivity();
        return taskDisplayArea.supportsActivityMinWidthHeightMultiWindow(this.mMinWidth, this.mMinHeight, rootActivity != null ? rootActivity.info : null);
    }

    private int getTaskId() {
        if (getTask() != null) {
            return getTask().mTaskId;
        }
        return -1;
    }

    void computeConfigResourceOverrides(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.content.res.Configuration configuration2) {
        computeConfigResourceOverrides(configuration, configuration2, null, null);
    }

    void computeConfigResourceOverrides(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.content.res.Configuration configuration2, @android.annotation.Nullable android.view.DisplayInfo displayInfo) {
        if (displayInfo != null) {
            configuration.screenLayout = 0;
            invalidateAppBoundsConfig(configuration);
        }
        computeConfigResourceOverrides(configuration, configuration2, displayInfo, null);
    }

    void computeConfigResourceOverrides(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.content.res.Configuration configuration2, @android.annotation.Nullable com.android.server.wm.ActivityRecord.CompatDisplayInsets compatDisplayInsets) {
        if (compatDisplayInsets != null) {
            invalidateAppBoundsConfig(configuration);
        }
        computeConfigResourceOverrides(configuration, configuration2, null, compatDisplayInsets);
    }

    private static void invalidateAppBoundsConfig(@android.annotation.NonNull android.content.res.Configuration configuration) {
        android.graphics.Rect appBounds = configuration.windowConfiguration.getAppBounds();
        if (appBounds != null) {
            appBounds.setEmpty();
        }
        configuration.screenWidthDp = 0;
        configuration.screenHeightDp = 0;
    }

    void computeConfigResourceOverrides(@android.annotation.NonNull android.content.res.Configuration configuration, @android.annotation.NonNull android.content.res.Configuration configuration2, @android.annotation.Nullable android.view.DisplayInfo displayInfo, @android.annotation.Nullable com.android.server.wm.ActivityRecord.CompatDisplayInsets compatDisplayInsets) {
        boolean contains;
        android.graphics.Rect appBounds;
        android.view.DisplayInfo displayInfo2;
        int i;
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        if (windowingMode == 0) {
            windowingMode = configuration2.windowConfiguration.getWindowingMode();
        }
        float f = configuration.densityDpi;
        if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f = configuration2.densityDpi;
        }
        float f2 = f * 0.00625f;
        android.graphics.Rect bounds = configuration2.windowConfiguration.getBounds();
        android.graphics.Rect bounds2 = configuration.windowConfiguration.getBounds();
        if (bounds2.isEmpty()) {
            this.mTmpFullBounds.set(bounds);
            contains = true;
        } else {
            this.mTmpFullBounds.set(bounds2);
            contains = bounds.contains(bounds2);
        }
        boolean z = compatDisplayInsets != null;
        android.graphics.Rect appBounds2 = configuration.windowConfiguration.getAppBounds();
        if (appBounds2 == null || appBounds2.isEmpty()) {
            configuration.windowConfiguration.setAppBounds(this.mTmpFullBounds);
            appBounds2 = configuration.windowConfiguration.getAppBounds();
            if (!z && windowingMode != 5) {
                if (contains) {
                    appBounds = configuration2.windowConfiguration.getAppBounds();
                } else {
                    com.android.server.wm.TaskDisplayArea displayArea = getDisplayArea();
                    appBounds = displayArea != null ? displayArea.getWindowConfiguration().getAppBounds() : null;
                }
                if (appBounds != null && !appBounds.isEmpty()) {
                    appBounds2.intersect(appBounds);
                }
            }
        }
        if (configuration.screenWidthDp == 0 || configuration.screenHeightDp == 0) {
            if (!z && android.app.WindowConfiguration.isFloating(windowingMode)) {
                this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                this.mTmpStableBounds.set(this.mTmpFullBounds);
            } else if (!z && (displayInfo != null || getDisplayContent() != null)) {
                if (displayInfo != null) {
                    displayInfo2 = displayInfo;
                } else {
                    displayInfo2 = getDisplayContent().getDisplayInfo();
                }
                calculateInsetFrames(this.mTmpNonDecorBounds, this.mTmpStableBounds, this.mTmpFullBounds, displayInfo2);
            } else {
                int rotation = configuration.windowConfiguration.getRotation();
                if (rotation == -1) {
                    rotation = configuration2.windowConfiguration.getRotation();
                }
                if (rotation != -1 && z) {
                    this.mTmpNonDecorBounds.set(this.mTmpFullBounds);
                    this.mTmpStableBounds.set(this.mTmpFullBounds);
                    compatDisplayInsets.getBoundsByRotation(this.mTmpBounds, rotation);
                    intersectWithInsetsIfFits(this.mTmpNonDecorBounds, this.mTmpBounds, compatDisplayInsets.mNonDecorInsets[rotation]);
                    intersectWithInsetsIfFits(this.mTmpStableBounds, this.mTmpBounds, compatDisplayInsets.mStableInsets[rotation]);
                    appBounds2.set(this.mTmpNonDecorBounds);
                } else {
                    this.mTmpNonDecorBounds.set(appBounds2);
                    this.mTmpStableBounds.set(appBounds2);
                }
            }
            if (configuration.screenWidthDp == 0) {
                int width = (int) ((this.mTmpStableBounds.width() / f2) + 0.5f);
                if (contains && !z) {
                    width = java.lang.Math.min(width, configuration2.screenWidthDp);
                }
                configuration.screenWidthDp = width;
            }
            if (configuration.screenHeightDp == 0) {
                int height = (int) ((this.mTmpStableBounds.height() / f2) + 0.5f);
                if (contains && !z) {
                    height = java.lang.Math.min(height, configuration2.screenHeightDp);
                }
                configuration.screenHeightDp = height;
            }
            if (configuration.smallestScreenWidthDp != 0) {
                i = 2;
            } else {
                i = 2;
                boolean z2 = windowingMode == 2 && !this.mTmpFullBounds.isEmpty() && this.mTmpFullBounds.equals(bounds);
                if (android.app.WindowConfiguration.isFloating(windowingMode) && !z2) {
                    configuration.smallestScreenWidthDp = (int) ((java.lang.Math.min(this.mTmpFullBounds.width(), this.mTmpFullBounds.height()) / f2) + 0.5f);
                } else if (windowingMode == 6 && this.mIsEmbedded && contains && !bounds2.equals(bounds)) {
                    configuration.smallestScreenWidthDp = java.lang.Math.min(configuration.screenWidthDp, configuration.screenHeightDp);
                }
            }
        } else {
            i = 2;
        }
        if (configuration.orientation == 0) {
            configuration.orientation = configuration.screenWidthDp <= configuration.screenHeightDp ? 1 : i;
        }
        if (configuration.screenLayout == 0) {
            int width2 = (int) ((this.mTmpNonDecorBounds.width() / f2) + 0.5f);
            int height2 = (int) ((this.mTmpNonDecorBounds.height() / f2) + 0.5f);
            if (configuration.screenWidthDp != 0) {
                width2 = configuration.screenWidthDp;
            }
            if (configuration.screenHeightDp != 0) {
                height2 = configuration.screenHeightDp;
            }
            configuration.screenLayout = com.android.server.wm.WindowContainer.computeScreenLayout(configuration2.screenLayout, width2, height2);
        }
    }

    void calculateInsetFrames(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.view.DisplayInfo displayInfo) {
        rect.set(rect3);
        rect2.set(rect3);
        if (this.mDisplayContent == null) {
            return;
        }
        this.mTmpBounds.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        com.android.server.wm.DisplayPolicy.DecorInsets.Info decorInsetsInfo = this.mDisplayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight);
        intersectWithInsetsIfFits(rect, this.mTmpBounds, decorInsetsInfo.mNonDecorInsets);
        intersectWithInsetsIfFits(rect2, this.mTmpBounds, decorInsetsInfo.mConfigInsets);
    }

    static void intersectWithInsetsIfFits(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (rect.right <= rect2.right) {
            rect.right = java.lang.Math.min(rect2.right - rect3.right, rect.right);
        }
        if (rect.bottom <= rect2.bottom) {
            rect.bottom = java.lang.Math.min(rect2.bottom - rect3.bottom, rect.bottom);
        }
        if (rect.left >= rect2.left) {
            rect.left = java.lang.Math.max(rect2.left + rect3.left, rect.left);
        }
        if (rect.top >= rect2.top) {
            rect.top = java.lang.Math.max(rect2.top + rect3.top, rect.top);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public int getActivityType() {
        int activityType = super.getActivityType();
        if (activityType != 0 || !hasChild()) {
            return activityType;
        }
        com.android.server.wm.ConfigurationContainer topMostActivity = getTopMostActivity();
        if (topMostActivity == null) {
            topMostActivity = getTopChild();
        }
        return topMostActivity.getActivityType();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateOrganizedTaskFragmentSurface();
        sendTaskFragmentInfoChanged();
    }

    void deferOrganizedTaskFragmentSurfaceUpdate() {
        this.mDelayOrganizedTaskFragmentSurfaceUpdate = true;
    }

    void continueOrganizedTaskFragmentSurfaceUpdate() {
        this.mDelayOrganizedTaskFragmentSurfaceUpdate = false;
        updateOrganizedTaskFragmentSurface();
    }

    void updateOrganizedTaskFragmentSurface() {
        if (this.mDelayOrganizedTaskFragmentSurfaceUpdate || this.mTaskFragmentOrganizer == null) {
            return;
        }
        if (this.mTransitionController.isShellTransitionsEnabled() && !this.mTransitionController.isCollecting(this)) {
            updateOrganizedTaskFragmentSurfaceUnchecked();
        } else if (!this.mTransitionController.isShellTransitionsEnabled() && !isAnimating()) {
            updateOrganizedTaskFragmentSurfaceUnchecked();
        }
    }

    private void updateOrganizedTaskFragmentSurfaceUnchecked() {
        android.view.SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        updateSurfacePosition(syncTransaction);
        updateOrganizedTaskFragmentSurfaceSize(syncTransaction, false);
    }

    private void updateOrganizedTaskFragmentSurfaceSize(android.view.SurfaceControl.Transaction transaction, boolean z) {
        android.graphics.Rect bounds;
        if (this.mTaskFragmentOrganizer == null || this.mSurfaceControl == null || this.mSurfaceAnimator.hasLeash() || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        if (isClosingWhenResizing()) {
            bounds = this.mDisplayContent.mClosingChangingContainers.get(this);
        } else {
            bounds = getBounds();
        }
        int width = bounds.width();
        int height = bounds.height();
        if (!z && width == this.mLastSurfaceSize.x && height == this.mLastSurfaceSize.y) {
            return;
        }
        transaction.setWindowCrop(this.mSurfaceControl, width, height);
        this.mLastSurfaceSize.set(width, height);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        if (this.mTaskFragmentOrganizer != null) {
            if (this.mLastSurfaceSize.x != 0 || this.mLastSurfaceSize.y != 0) {
                transaction.setWindowCrop(this.mSurfaceControl, 0, 0);
                android.view.SurfaceControl.Transaction syncTransaction = getSyncTransaction();
                if (transaction != syncTransaction) {
                    syncTransaction.setWindowCrop(this.mSurfaceControl, 0, 0);
                }
                this.mLastSurfaceSize.set(0, 0);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        super.onAnimationLeashLost(transaction);
        if (this.mTaskFragmentOrganizer != null) {
            updateOrganizedTaskFragmentSurfaceSize(transaction, true);
        }
    }

    @android.annotation.NonNull
    android.graphics.Rect getRelativeEmbeddedBounds() {
        if (this.mRelativeEmbeddedBounds == null) {
            throw new java.lang.IllegalStateException("The TaskFragment is not embedded");
        }
        return this.mRelativeEmbeddedBounds;
    }

    android.graphics.Rect translateRelativeBoundsToAbsoluteBounds(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2) {
        if (rect.isEmpty()) {
            this.mTmpAbsBounds.setEmpty();
            return this.mTmpAbsBounds;
        }
        this.mTmpAbsBounds.set(rect);
        this.mTmpAbsBounds.offset(rect2.left, rect2.top);
        if (!isAllowedToBeEmbeddedInTrustedMode() && !rect2.contains(this.mTmpAbsBounds) && !this.mTmpAbsBounds.intersect(rect2)) {
            this.mTmpAbsBounds.setEmpty();
        }
        return this.mTmpAbsBounds;
    }

    void recomputeConfiguration() {
        onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
    }

    void setRelativeEmbeddedBounds(@android.annotation.NonNull android.graphics.Rect rect) {
        if (this.mRelativeEmbeddedBounds == null) {
            throw new java.lang.IllegalStateException("The TaskFragment is not embedded");
        }
        if (this.mRelativeEmbeddedBounds.equals(rect)) {
            return;
        }
        this.mRelativeEmbeddedBounds.set(rect);
    }

    boolean shouldStartChangeTransition(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2) {
        if (this.mTaskFragmentOrganizer == null || !canStartChangeTransition()) {
            return false;
        }
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            android.graphics.Rect bounds = getConfiguration().windowConfiguration.getBounds();
            return (bounds.width() == rect.width() && bounds.height() == rect.height()) ? false : true;
        }
        return !rect2.equals(this.mRelativeEmbeddedBounds);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canStartChangeTransition() {
        com.android.server.wm.Task task = getTask();
        return (task == null || task.isDragResizing() || !super.canStartChangeTransition()) ? false : true;
    }

    boolean setClosingChangingStartBoundsIfNeeded() {
        if (isOrganizedTaskFragment() && this.mDisplayContent != null && this.mDisplayContent.mChangingContainers.remove(this)) {
            this.mDisplayContent.mClosingChangingContainers.put(this, new android.graphics.Rect(this.mSurfaceFreezer.mFreezeBounds));
            return true;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isSyncFinished(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        return super.isSyncFinished(syncGroup) && isReadyToTransit();
    }

    @Override // com.android.server.wm.WindowContainer
    void setSurfaceControl(android.view.SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        if (this.mTaskFragmentOrganizer != null) {
            updateOrganizedTaskFragmentSurfaceUnchecked();
            sendTaskFragmentAppeared();
        }
    }

    void sendTaskFragmentInfoChanged() {
        if (this.mTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentInfoChanged(this.mTaskFragmentOrganizer, this);
        }
    }

    void sendTaskFragmentParentInfoChanged() {
        com.android.server.wm.Task asTask = getParent().asTask();
        if (this.mTaskFragmentOrganizer != null && asTask != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentParentInfoChanged(this.mTaskFragmentOrganizer, asTask);
        }
    }

    private void sendTaskFragmentAppeared() {
        if (this.mTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentAppeared(this.mTaskFragmentOrganizer, this);
        }
    }

    private void sendTaskFragmentVanished() {
        if (this.mTaskFragmentOrganizer != null) {
            this.mTaskFragmentOrganizerController.onTaskFragmentVanished(this.mTaskFragmentOrganizer, this);
        }
    }

    android.window.TaskFragmentInfo getTaskFragmentInfo() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            com.android.server.wm.ActivityRecord asActivityRecord = getChildAt(i).asActivityRecord();
            if (this.mTaskFragmentOrganizerUid != -1 && asActivityRecord != null && asActivityRecord.info.processName.equals(this.mTaskFragmentOrganizerProcessName) && asActivityRecord.getUid() == this.mTaskFragmentOrganizerUid && !asActivityRecord.finishing) {
                arrayList.add(asActivityRecord.token);
                if (asActivityRecord.mRequestedLaunchingTaskFragmentToken == this.mFragmentToken) {
                    arrayList2.add(asActivityRecord.token);
                }
            }
        }
        android.graphics.Point point = new android.graphics.Point();
        getRelativePosition(point);
        return new android.window.TaskFragmentInfo(this.mFragmentToken, this.mRemoteToken.toWindowContainerToken(), getConfiguration(), getNonFinishingActivityCount(), shouldBeVisible(null), arrayList, arrayList2, point, this.mClearedTaskForReuse, this.mClearedTaskFragmentForPip, this.mClearedForReorderActivityToFront, calculateMinDimension());
    }

    android.graphics.Point calculateMinDimension() {
        final int[] iArr = new int[1];
        final int[] iArr2 = new int[1];
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskFragment.lambda$calculateMinDimension$7(iArr, iArr2, (com.android.server.wm.ActivityRecord) obj);
            }
        });
        return new android.graphics.Point(iArr[0], iArr2[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$calculateMinDimension$7(int[] iArr, int[] iArr2, com.android.server.wm.ActivityRecord activityRecord) {
        android.graphics.Point minDimensions;
        if (activityRecord.finishing || (minDimensions = activityRecord.getMinDimensions()) == null) {
            return;
        }
        iArr[0] = java.lang.Math.max(iArr[0], minDimensions.x);
        iArr2[0] = java.lang.Math.max(iArr2[0], minDimensions.y);
    }

    @android.annotation.Nullable
    android.os.IBinder getFragmentToken() {
        return this.mFragmentToken;
    }

    @android.annotation.Nullable
    android.window.ITaskFragmentOrganizer getTaskFragmentOrganizer() {
        return this.mTaskFragmentOrganizer;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isOrganized() {
        return this.mTaskFragmentOrganizer != null;
    }

    final boolean isOrganizedTaskFragment() {
        return this.mTaskFragmentOrganizer != null;
    }

    boolean isEmbeddedWithBoundsOverride() {
        com.android.server.wm.Task task;
        if (!this.mIsEmbedded || (task = getTask()) == null) {
            return false;
        }
        android.graphics.Rect bounds = task.getBounds();
        android.graphics.Rect bounds2 = getBounds();
        return !bounds.equals(bounds2) && bounds.contains(bounds2);
    }

    boolean isTaskVisibleRequested() {
        com.android.server.wm.Task task = getTask();
        return task != null && task.isVisibleRequested();
    }

    boolean isReadyToTransit() {
        if (!isOrganizedTaskFragment() || getTopNonFinishingActivity() != null || this.mIsRemovalRequested || this.mAllowTransitionWhenEmpty || isEmbeddedTaskFragmentInPip()) {
            return true;
        }
        return this.mClearedTaskFragmentForPip && !isTaskVisibleRequested();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean canCustomizeAppTransition() {
        return isEmbedded() && matchParentBounds();
    }

    void clearLastPausedActivity() {
        forAllTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.TaskFragment) obj).mLastPausedActivity = null;
            }
        });
    }

    void setMinDimensions(int i, int i2) {
        if (asTask() != null) {
            throw new java.lang.UnsupportedOperationException("This method must not be used to Task. The  minimum dimension of Task should be passed from Task constructor.");
        }
        this.mMinWidth = i;
        this.mMinHeight = i2;
    }

    boolean isEmbeddedTaskFragmentInPip() {
        return isOrganizedTaskFragment() && getTask() != null && getTask().inPinnedWindowingMode();
    }

    boolean shouldRemoveSelfOnLastChildRemoval() {
        return !this.mCreatedByOrganizer || this.mIsRemovalRequested;
    }

    @Override // com.android.server.wm.WindowContainer
    void removeChild(com.android.server.wm.WindowContainer windowContainer) {
        removeChild(windowContainer, true);
    }

    void removeChild(com.android.server.wm.WindowContainer windowContainer, boolean z) {
        super.removeChild(windowContainer);
        com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        com.android.server.wm.WindowProcessController organizerProcessIfDifferent = getOrganizerProcessIfDifferent(asActivityRecord);
        if (organizerProcessIfDifferent != null) {
            organizerProcessIfDifferent.removeEmbeddedActivity(asActivityRecord);
        }
        if (z && shouldRemoveSelfOnLastChildRemoval() && !hasChild()) {
            removeImmediately("removeLastChild " + windowContainer);
        }
    }

    void remove(boolean z, java.lang.String str) {
        if (!hasChild()) {
            removeImmediately(str);
            return;
        }
        this.mIsRemovalRequested = true;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Objects.requireNonNull(arrayList);
        forAllActivities(new com.android.server.wm.Task$$ExternalSyntheticLambda38(arrayList));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) arrayList.get(size);
            if (z && activityRecord.isVisible()) {
                activityRecord.finishIfPossible(str, false);
            } else {
                activityRecord.destroyIfPossible(str);
            }
        }
    }

    void setDelayLastActivityRemoval(boolean z) {
        if (!this.mIsEmbedded) {
            android.util.Slog.w("ActivityTaskManager", "Set delaying last activity removal on a non-embedded TF.");
        }
        this.mDelayLastActivityRemoval = z;
    }

    boolean isDelayLastActivityRemoval() {
        return this.mDelayLastActivityRemoval;
    }

    boolean shouldDeferRemoval() {
        if (!hasChild()) {
            return false;
        }
        return isExitAnimationRunningSelfOrChild();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handleCompleteDeferredRemoval() {
        if (shouldDeferRemoval()) {
            return true;
        }
        return super.handleCompleteDeferredRemoval();
    }

    void removeImmediately(java.lang.String str) {
        android.util.Slog.d("ActivityTaskManager", "Remove task fragment: " + str);
        removeImmediately();
    }

    @Override // com.android.server.wm.WindowContainer
    void removeImmediately() {
        boolean z = false;
        this.mIsRemovalRequested = false;
        resetAdjacentTaskFragment();
        cleanUpEmbeddedTaskFragment();
        if (this.mClearedTaskFragmentForPip && isTaskVisibleRequested()) {
            z = true;
        }
        super.removeImmediately();
        sendTaskFragmentVanished();
        if (z && this.mDisplayContent != null) {
            this.mAtmService.addWindowLayoutReasons(2);
            this.mDisplayContent.executeAppTransition();
        }
    }

    private void cleanUpEmbeddedTaskFragment() {
        if (!this.mIsEmbedded) {
            return;
        }
        this.mAtmService.mWindowOrganizerController.cleanUpEmbeddedTaskFragment(this);
        com.android.server.wm.Task task = getTask();
        if (task == null) {
            return;
        }
        task.forAllLeafTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskFragment.this.lambda$cleanUpEmbeddedTaskFragment$9((com.android.server.wm.TaskFragment) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanUpEmbeddedTaskFragment$9(com.android.server.wm.TaskFragment taskFragment) {
        if (taskFragment.getCompanionTaskFragment() == this) {
            taskFragment.setCompanionTaskFragment(null);
        }
    }

    boolean shouldBoostDimmer() {
        com.android.server.wm.TaskFragment adjacentTaskFragment;
        if (asTask() != null || !isDimmingOnParentTask() || (adjacentTaskFragment = getAdjacentTaskFragment()) == null || getParent().mChildren.indexOf(adjacentTaskFragment) < getParent().mChildren.indexOf(this)) {
            return false;
        }
        com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction = new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda11
            public final boolean apply(java.lang.Object obj) {
                boolean lambda$shouldBoostDimmer$10;
                lambda$shouldBoostDimmer$10 = com.android.server.wm.TaskFragment.lambda$shouldBoostDimmer$10((com.android.server.wm.WindowState) obj);
                return lambda$shouldBoostDimmer$10;
            }
        };
        if (adjacentTaskFragment.forAllWindows(toBooleanFunction, true)) {
            return false;
        }
        return forAllWindows(toBooleanFunction, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$shouldBoostDimmer$10(com.android.server.wm.WindowState windowState) {
        return (windowState.mAttrs.flags & 2) != 0 && windowState.mActivityRecord != null && windowState.mActivityRecord.isEmbedded() && (windowState.mActivityRecord.isVisibleRequested() || windowState.mActivityRecord.isVisible());
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.Dimmer getDimmer() {
        if (this.mIsEmbedded && !isDimmingOnParentTask()) {
            return this.mDimmer;
        }
        return super.getDimmer();
    }

    void getDimBounds(@android.annotation.NonNull android.graphics.Rect rect) {
        if (this.mIsEmbedded && isDimmingOnParentTask() && getDimmer().getDimBounds() != null) {
            rect.set(getTask().getBounds());
        } else {
            rect.set(getBounds());
        }
    }

    void setEmbeddedDimArea(@com.android.server.wm.TaskFragment.EmbeddedDimArea int i) {
        this.mEmbeddedDimArea = i;
    }

    void setMoveToBottomIfClearWhenLaunch(boolean z) {
        this.mMoveToBottomIfClearWhenLaunch = z;
    }

    boolean isMoveToBottomIfClearWhenLaunch() {
        return this.mMoveToBottomIfClearWhenLaunch;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isDimmingOnParentTask() {
        return this.mEmbeddedDimArea == 1;
    }

    @Override // com.android.server.wm.WindowContainer
    void prepareSurfaces() {
        if (asTask() != null) {
            super.prepareSurfaces();
            return;
        }
        this.mDimmer.resetDimStates();
        super.prepareSurfaces();
        android.graphics.Rect dimBounds = this.mDimmer.getDimBounds();
        if (dimBounds != null) {
            dimBounds.offsetTo(0, 0);
            if (this.mDimmer.updateDims(getSyncTransaction())) {
                scheduleAnimation();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return getWindowingMode() == 1 || matchParentBounds();
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean onChildVisibleRequestedChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        if (!super.onChildVisibleRequestedChanged(windowContainer)) {
            return false;
        }
        sendTaskFragmentInfoChanged();
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getTaskFragment(java.util.function.Predicate<com.android.server.wm.TaskFragment> predicate) {
        com.android.server.wm.TaskFragment taskFragment = super.getTaskFragment(predicate);
        if (taskFragment != null) {
            return taskFragment;
        }
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    boolean moveChildToFront(com.android.server.wm.WindowContainer windowContainer) {
        int distanceFromTop = getDistanceFromTop(windowContainer);
        positionChildAt(Integer.MAX_VALUE, windowContainer, false);
        return getDistanceFromTop(windowContainer) != distanceFromTop;
    }

    java.lang.String toFullString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append(this);
        sb.setLength(sb.length() - 1);
        if (this.mTaskFragmentOrganizerUid != -1) {
            sb.append(" organizerUid=");
            sb.append(this.mTaskFragmentOrganizerUid);
        }
        if (this.mTaskFragmentOrganizerProcessName != null) {
            sb.append(" organizerProc=");
            sb.append(this.mTaskFragmentOrganizerProcessName);
        }
        if (this.mAdjacentTaskFragment != null) {
            sb.append(" adjacent=");
            sb.append(this.mAdjacentTaskFragment);
        }
        sb.append('}');
        return sb.toString();
    }

    public java.lang.String toString() {
        return "TaskFragment{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " mode=" + android.app.WindowConfiguration.windowingModeToString(getWindowingMode()) + "}";
    }

    boolean dump(final java.lang.String str, java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, final boolean z, boolean z2, final java.lang.String str2, final boolean z3, final java.lang.Runnable runnable) {
        boolean z4;
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.server.wm.TaskFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TaskFragment.this.lambda$dump$11(z3, printWriter, runnable, str, z, str2);
            }
        };
        if (str2 != null) {
            z4 = false;
        } else {
            runnable2.run();
            runnable2 = null;
            z4 = true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskFragment() != null) {
                z4 = windowContainer.asTaskFragment().dump(str + "  ", fileDescriptor, printWriter, z, z2, str2, z3, runnable2) | z4;
            } else if (windowContainer.asActivityRecord() != null) {
                com.android.server.wm.ActivityRecord.dumpActivity(fileDescriptor, printWriter, size, windowContainer.asActivityRecord(), str + "  ", "Hist ", true, !z, z2, str2, false, runnable2, getTask());
            }
        }
        return z4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$11(boolean z, java.io.PrintWriter printWriter, java.lang.Runnable runnable, java.lang.String str, boolean z2, java.lang.String str2) {
        if (z) {
            printWriter.println();
        }
        if (runnable != null) {
            runnable.run();
        }
        dumpInner(str, printWriter, z2, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dumpInner(java.lang.String str, java.io.PrintWriter printWriter, boolean z, java.lang.String str2) {
        printWriter.print(str);
        printWriter.print("* ");
        printWriter.println(toFullString());
        android.graphics.Rect requestedOverrideBounds = getRequestedOverrideBounds();
        if (!requestedOverrideBounds.isEmpty()) {
            printWriter.println(str + "  mBounds=" + requestedOverrideBounds);
        }
        if (this.mIsRemovalRequested) {
            printWriter.println(str + "  mIsRemovalRequested=true");
        }
        if (z) {
            com.android.server.wm.ActivityTaskSupervisor.printThisActivity(printWriter, this.mLastPausedActivity, str2, false, str + "  mLastPausedActivity: ", null);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        super.dump(printWriter, str, z);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("bounds=");
        sb.append(getBounds().toShortString());
        sb.append(this.mIsolatedNav ? ", isolatedNav" : "");
        printWriter.println(sb.toString());
        java.lang.String str2 = str + "  ";
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) this.mChildren.get(size);
            com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append(str);
            sb2.append("* ");
            sb2.append((java.lang.Object) (asTaskFragment != null ? asTaskFragment.toFullString() : windowContainer));
            printWriter.println(sb2.toString());
            if (asTaskFragment != null) {
                windowContainer.dump(printWriter, str2, z);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void writeIdentifierToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        com.android.server.wm.ActivityRecord activityRecord = topRunningActivity();
        protoOutputStream.write(1120986464258L, activityRecord != null ? activityRecord.mUserId : com.android.server.am.ProcessList.INVALID_ADJ);
        protoOutputStream.write(1138166333443L, activityRecord != null ? activityRecord.intent.getComponent().flattenToShortString() : "TaskFragment");
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268041L;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1120986464258L, getDisplayId());
        protoOutputStream.write(1120986464259L, getActivityType());
        protoOutputStream.write(1120986464260L, this.mMinWidth);
        protoOutputStream.write(1120986464261L, this.mMinHeight);
        protoOutputStream.end(start);
    }
}
