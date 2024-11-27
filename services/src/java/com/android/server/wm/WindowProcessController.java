package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowProcessController extends com.android.server.wm.ConfigurationContainer<com.android.server.wm.ConfigurationContainer> implements com.android.server.wm.ConfigurationContainerListener {
    private static final int ACTIVITY_STATE_FLAG_HAS_ACTIVITY_IN_VISIBLE_TASK = 4194304;
    private static final int ACTIVITY_STATE_FLAG_HAS_RESUMED = 2097152;
    private static final int ACTIVITY_STATE_FLAG_IS_PAUSING_OR_PAUSED = 131072;
    private static final int ACTIVITY_STATE_FLAG_IS_STOPPING = 262144;
    private static final int ACTIVITY_STATE_FLAG_IS_STOPPING_FINISHING = 524288;
    private static final int ACTIVITY_STATE_FLAG_IS_VISIBLE = 65536;
    private static final int ACTIVITY_STATE_FLAG_IS_WINDOW_VISIBLE = 1048576;
    private static final int ACTIVITY_STATE_FLAG_MASK_MIN_TASK_LAYER = 65535;
    static final int ANIMATING_REASON_LEGACY_RECENT_ANIMATION = 4;
    static final int ANIMATING_REASON_REMOTE_ANIMATION = 1;
    static final int ANIMATING_REASON_WAKEFULNESS_CHANGE = 2;
    private static final int CACHED_CONFIG_PROC_STATE = 16;
    private static final int MAX_RAPID_ACTIVITY_LAUNCH_COUNT = 500;
    private static final long RAPID_ACTIVITY_LAUNCH_MS = 300;
    private static final int REMOTE_ACTIVITY_FLAG_EMBEDDED_ACTIVITY = 2;
    private static final int REMOTE_ACTIVITY_FLAG_HOST_ACTIVITY = 1;
    private static final long RESET_RAPID_ACTIVITY_LAUNCH_MS = 1500;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_CONFIGURATION = "ActivityTaskManager";
    private static final java.lang.String TAG_RELEASE = "ActivityTaskManager";
    private int mAnimatingReasons;
    private final com.android.server.wm.ActivityTaskManagerService mAtm;
    private final com.android.server.wm.BackgroundLaunchProcessController mBgLaunchController;
    private final boolean mCanUseSystemGrammaticalGender;
    private com.android.server.wm.ActivityRecord mConfigActivityRecord;
    private volatile boolean mCrashing;
    private volatile int mCurSchedGroup;
    private volatile boolean mDebugging;

    @android.annotation.Nullable
    private com.android.server.wm.DisplayArea mDisplayArea;
    private volatile long mFgInteractionTime;
    private volatile boolean mHasActivities;
    private volatile boolean mHasCachedConfiguration;
    private volatile boolean mHasClientActivities;
    private volatile boolean mHasForegroundServices;
    private volatile boolean mHasImeService;
    private volatile boolean mHasOverlayUi;
    private boolean mHasPendingConfigurationChange;
    private volatile boolean mHasRecentTasks;
    private volatile boolean mHasTopUi;
    private java.util.ArrayList<com.android.server.wm.ActivityRecord> mInactiveActivities;
    final android.content.pm.ApplicationInfo mInfo;
    private volatile boolean mInstrumenting;
    private volatile boolean mInstrumentingWithBackgroundActivityStartPrivileges;
    private volatile long mInteractionEventTime;
    private volatile boolean mIsActivityConfigOverrideAllowed;
    private volatile long mLastActivityFinishTime;
    private volatile long mLastActivityLaunchTime;
    private final com.android.server.wm.WindowProcessListener mListener;
    final java.lang.String mName;
    private volatile boolean mNotResponding;
    public final java.lang.Object mOwner;
    private int mPauseConfigurationDispatchCount;
    private volatile boolean mPendingUiClean;
    private volatile boolean mPerceptible;
    private volatile boolean mPersistent;
    private volatile int mPid;
    private int mRapidActivityLaunchCount;

    @android.annotation.Nullable
    private android.util.ArrayMap<com.android.server.wm.ActivityRecord, int[]> mRemoteActivities;
    private volatile java.lang.String mRequiredAbi;
    private android.app.IApplicationThread mThread;
    final int mUid;
    final int mUserId;
    private volatile boolean mUsingWrapper;
    int mVrThreadTid;
    private volatile long mWhenUnimportant;

    @android.annotation.Nullable
    com.android.server.wm.Session mWindowSession;

    @com.android.internal.annotations.GuardedBy({"itself"})
    private final java.util.ArrayList<java.lang.String> mPkgList = new java.util.ArrayList<>(1);
    private volatile int mCurProcState = 20;
    private volatile int mRepProcState = 20;
    private volatile int mCurAdj = com.android.server.am.ProcessList.INVALID_ADJ;
    private volatile int mInstrumentationSourceUid = -1;
    private final java.util.ArrayList<com.android.server.wm.ActivityRecord> mActivities = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mRecentTasks = new java.util.ArrayList<>();
    private com.android.server.wm.ActivityRecord mPreQTopResumedActivity = null;
    private final android.content.res.Configuration mLastReportedConfiguration = new android.content.res.Configuration();
    private int mLastTopActivityDeviceId = 0;
    private volatile int mActivityStateFlags = 65535;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AnimatingReason {
    }

    public interface ComputeOomAdjCallback {
        void onOtherActivity();

        void onPausedActivity();

        void onStoppingActivity(boolean z);

        void onVisibleActivity();
    }

    public WindowProcessController(@android.annotation.NonNull final com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, int i, int i2, java.lang.Object obj, @android.annotation.NonNull com.android.server.wm.WindowProcessListener windowProcessListener) {
        this.mIsActivityConfigOverrideAllowed = true;
        this.mInfo = applicationInfo;
        this.mName = str;
        this.mUid = i;
        this.mUserId = i2;
        this.mOwner = obj;
        this.mListener = windowProcessListener;
        this.mAtm = activityTaskManagerService;
        java.util.Objects.requireNonNull(activityTaskManagerService);
        this.mBgLaunchController = new com.android.server.wm.BackgroundLaunchProcessController(new java.util.function.IntPredicate() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda12
            @Override // java.util.function.IntPredicate
            public final boolean test(int i3) {
                return com.android.server.wm.ActivityTaskManagerService.this.hasActiveVisibleWindow(i3);
            }
        }, activityTaskManagerService.getBackgroundActivityStartCallback());
        if (applicationInfo.packageName.equals(this.mAtm.getSysUiServiceComponentLocked().getPackageName()) || android.os.UserHandle.getAppId(this.mUid) == 1000) {
            this.mIsActivityConfigOverrideAllowed = false;
        }
        this.mCanUseSystemGrammaticalGender = this.mAtm.mGrammaticalManagerInternal != null && this.mAtm.mGrammaticalManagerInternal.canGetSystemGrammaticalGender(this.mUid, this.mInfo.packageName);
        onConfigurationChanged(activityTaskManagerService.getGlobalConfiguration());
        this.mAtm.mPackageConfigPersister.updateConfigIfNeeded(this, this.mUserId, this.mInfo.packageName);
    }

    public void setPid(int i) {
        this.mPid = i;
    }

    public int getPid() {
        return this.mPid;
    }

    public void setThread(android.app.IApplicationThread iApplicationThread) {
        synchronized (this.mAtm.mGlobalLockWithoutBoost) {
            try {
                this.mThread = iApplicationThread;
                if (iApplicationThread != null) {
                    setLastReportedConfiguration(getConfiguration());
                } else {
                    this.mAtm.mVisibleActivityProcessTracker.removeProcess(this);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.app.IApplicationThread getThread() {
        return this.mThread;
    }

    boolean hasThread() {
        return this.mThread != null;
    }

    public void setCurrentSchedulingGroup(int i) {
        this.mCurSchedGroup = i;
    }

    int getCurrentSchedulingGroup() {
        return this.mCurSchedGroup;
    }

    public void setCurrentProcState(int i) {
        this.mCurProcState = i;
    }

    int getCurrentProcState() {
        return this.mCurProcState;
    }

    public void setCurrentAdj(int i) {
        this.mCurAdj = i;
    }

    int getCurrentAdj() {
        return this.mCurAdj;
    }

    public void setReportedProcState(int i) {
        android.app.servertransaction.ClientTransactionItem obtain;
        int i2 = this.mRepProcState;
        this.mRepProcState = i;
        android.app.IApplicationThread iApplicationThread = this.mThread;
        if (i2 >= 16 && i < 16 && iApplicationThread != null && this.mHasCachedConfiguration) {
            synchronized (this.mLastReportedConfiguration) {
                onConfigurationChangePreScheduled(this.mLastReportedConfiguration);
                obtain = android.app.servertransaction.ConfigurationChangeItem.obtain(this.mLastReportedConfiguration, this.mLastTopActivityDeviceId);
            }
            try {
                this.mAtm.getLifecycleManager().scheduleTransactionItemNow(iApplicationThread, obtain);
            } catch (java.lang.Exception e) {
                android.util.Slog.e("ActivityTaskManager", "Failed to schedule ConfigurationChangeItem=" + obtain + " owner=" + this.mOwner, e);
            }
        }
    }

    int getReportedProcState() {
        return this.mRepProcState;
    }

    public void setCrashing(boolean z) {
        this.mCrashing = z;
    }

    void handleAppCrash() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mActivities);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) arrayList.get(size);
            android.util.Slog.w("ActivityTaskManager", "  Force finishing activity " + activityRecord.mActivityComponent.flattenToShortString());
            activityRecord.detachFromProcess();
            activityRecord.mDisplayContent.requestTransitionAndLegacyPrepare(2, 16);
            activityRecord.destroyIfPossible("handleAppCrashed");
        }
    }

    boolean isCrashing() {
        return this.mCrashing;
    }

    public void setNotResponding(boolean z) {
        this.mNotResponding = z;
    }

    boolean isNotResponding() {
        return this.mNotResponding;
    }

    public void setPersistent(boolean z) {
        this.mPersistent = z;
    }

    boolean isPersistent() {
        return this.mPersistent;
    }

    public void setHasForegroundServices(boolean z) {
        this.mHasForegroundServices = z;
    }

    boolean hasForegroundServices() {
        return this.mHasForegroundServices;
    }

    boolean hasForegroundActivities() {
        return this.mAtm.mTopApp == this || (this.mActivityStateFlags & 458752) != 0;
    }

    public void setHasClientActivities(boolean z) {
        this.mHasClientActivities = z;
    }

    boolean hasClientActivities() {
        return this.mHasClientActivities;
    }

    public void setHasTopUi(boolean z) {
        this.mHasTopUi = z;
    }

    boolean hasTopUi() {
        return this.mHasTopUi;
    }

    public void setHasOverlayUi(boolean z) {
        this.mHasOverlayUi = z;
    }

    boolean hasOverlayUi() {
        return this.mHasOverlayUi;
    }

    public void setPendingUiClean(boolean z) {
        this.mPendingUiClean = z;
    }

    boolean hasPendingUiClean() {
        return this.mPendingUiClean;
    }

    boolean registeredForDisplayAreaConfigChanges() {
        return this.mDisplayArea != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean registeredForActivityConfigChanges() {
        return this.mConfigActivityRecord != null;
    }

    void postPendingUiCleanMsg(boolean z) {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.WindowProcessListener) obj).setPendingUiClean(((java.lang.Boolean) obj2).booleanValue());
            }
        }, this.mListener, java.lang.Boolean.valueOf(z)));
    }

    public void setInteractionEventTime(long j) {
        this.mInteractionEventTime = j;
    }

    long getInteractionEventTime() {
        return this.mInteractionEventTime;
    }

    public void setFgInteractionTime(long j) {
        this.mFgInteractionTime = j;
    }

    long getFgInteractionTime() {
        return this.mFgInteractionTime;
    }

    public void setWhenUnimportant(long j) {
        this.mWhenUnimportant = j;
    }

    long getWhenUnimportant() {
        return this.mWhenUnimportant;
    }

    public void setRequiredAbi(java.lang.String str) {
        this.mRequiredAbi = str;
    }

    java.lang.String getRequiredAbi() {
        return this.mRequiredAbi;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wm.DisplayArea getDisplayArea() {
        return this.mDisplayArea;
    }

    public void setDebugging(boolean z) {
        this.mDebugging = z;
    }

    boolean isDebugging() {
        return this.mDebugging;
    }

    public void setUsingWrapper(boolean z) {
        this.mUsingWrapper = z;
    }

    boolean isUsingWrapper() {
        return this.mUsingWrapper;
    }

    boolean hasEverLaunchedActivity() {
        return this.mLastActivityLaunchTime > 0;
    }

    void setLastActivityLaunchTime(com.android.server.wm.ActivityRecord activityRecord) {
        long j = activityRecord.lastLaunchTime;
        if (j > this.mLastActivityLaunchTime) {
            updateRapidActivityLaunch(activityRecord, j, this.mLastActivityLaunchTime);
            this.mLastActivityLaunchTime = j;
        } else if (j < this.mLastActivityLaunchTime) {
            android.util.Slog.w("ActivityTaskManager", "Tried to set launchTime (" + j + ") < mLastActivityLaunchTime (" + this.mLastActivityLaunchTime + ")");
        }
    }

    void updateRapidActivityLaunch(com.android.server.wm.ActivityRecord activityRecord, long j, long j2) {
        if (this.mInstrumenting || this.mDebugging || j2 <= 0) {
            return;
        }
        long j3 = j - j2;
        if (j3 < RAPID_ACTIVITY_LAUNCH_MS) {
            this.mRapidActivityLaunchCount++;
        } else if (j3 >= RESET_RAPID_ACTIVITY_LAUNCH_MS) {
            this.mRapidActivityLaunchCount = 0;
        }
        if (this.mRapidActivityLaunchCount > 500) {
            android.util.Slog.w("ActivityTaskManager", "Killing " + this.mPid + " because of rapid activity launch");
            activityRecord.getRootTask().moveTaskToBack(activityRecord.getTask());
            this.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowProcessController.this.lambda$updateRapidActivityLaunch$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRapidActivityLaunch$0() {
        this.mAtm.mAmInternal.killProcess(this.mName, this.mUid, "rapidActivityLaunch");
    }

    void setLastActivityFinishTimeIfNeeded(long j) {
        if (j <= this.mLastActivityFinishTime || !hasActivityInVisibleTask()) {
            return;
        }
        this.mLastActivityFinishTime = j;
    }

    public void addOrUpdateBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        java.util.Objects.requireNonNull(binder, "entity");
        java.util.Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
        com.android.internal.util.Preconditions.checkArgument(backgroundStartPrivileges.allowsAny(), "backgroundStartPrivileges does not allow anything");
        this.mBgLaunchController.addOrUpdateAllowBackgroundStartPrivileges(binder, backgroundStartPrivileges);
    }

    public void removeBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder) {
        java.util.Objects.requireNonNull(binder, "entity");
        this.mBgLaunchController.removeAllowBackgroundStartPrivileges(binder);
    }

    public boolean areBackgroundFgsStartsAllowed() {
        return areBackgroundActivityStartsAllowed(this.mAtm.getBalAppSwitchesState(), true).allows();
    }

    com.android.server.wm.BackgroundActivityStartController.BalVerdict areBackgroundActivityStartsAllowed(int i) {
        return areBackgroundActivityStartsAllowed(i, false);
    }

    private com.android.server.wm.BackgroundActivityStartController.BalVerdict areBackgroundActivityStartsAllowed(int i, boolean z) {
        return this.mBgLaunchController.areBackgroundActivityStartsAllowed(this.mPid, this.mUid, this.mInfo.packageName, i, z, hasActivityInVisibleTask(), this.mInstrumentingWithBackgroundActivityStartPrivileges, this.mAtm.getLastStopAppSwitchesTime(), this.mLastActivityLaunchTime, this.mLastActivityFinishTime);
    }

    boolean canCloseSystemDialogsByToken() {
        return this.mBgLaunchController.canCloseSystemDialogsByToken(this.mUid);
    }

    public void clearBoundClientUids() {
        this.mBgLaunchController.clearBalOptInBoundClientUids();
    }

    public void addBoundClientUid(int i, java.lang.String str, long j) {
        this.mBgLaunchController.addBoundClientUid(i, str, j);
    }

    public void setInstrumenting(boolean z, int i, boolean z2) {
        com.android.internal.util.Preconditions.checkArgument(z || i == -1);
        this.mInstrumenting = z;
        this.mInstrumentationSourceUid = i;
        this.mInstrumentingWithBackgroundActivityStartPrivileges = z2;
    }

    boolean isInstrumenting() {
        return this.mInstrumenting;
    }

    int getInstrumentationSourceUid() {
        return this.mInstrumentationSourceUid;
    }

    public void setPerceptible(boolean z) {
        this.mPerceptible = z;
    }

    boolean isPerceptible() {
        return this.mPerceptible;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    protected int getChildCount() {
        return 0;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    protected com.android.server.wm.ConfigurationContainer getChildAt(int i) {
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    protected com.android.server.wm.ConfigurationContainer getParent() {
        return this.mAtm.mRootWindowContainer;
    }

    public void addPackage(java.lang.String str) {
        synchronized (this.mPkgList) {
            try {
                if (!this.mPkgList.contains(str)) {
                    this.mPkgList.add(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void clearPackageList() {
        synchronized (this.mPkgList) {
            this.mPkgList.clear();
        }
    }

    boolean containsPackage(java.lang.String str) {
        boolean contains;
        synchronized (this.mPkgList) {
            contains = this.mPkgList.contains(str);
        }
        return contains;
    }

    java.util.List<java.lang.String> getPackageList() {
        java.util.ArrayList arrayList;
        synchronized (this.mPkgList) {
            arrayList = new java.util.ArrayList(this.mPkgList);
        }
        return arrayList;
    }

    void addActivityIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        setLastActivityLaunchTime(activityRecord);
        if (this.mActivities.contains(activityRecord)) {
            return;
        }
        this.mActivities.add(activityRecord);
        this.mHasActivities = true;
        if (this.mInactiveActivities != null) {
            this.mInactiveActivities.remove(activityRecord);
        }
        updateActivityConfigurationListener();
    }

    void removeActivity(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        if (z) {
            if (this.mInactiveActivities == null) {
                this.mInactiveActivities = new java.util.ArrayList<>();
                this.mInactiveActivities.add(activityRecord);
            } else if (!this.mInactiveActivities.contains(activityRecord)) {
                this.mInactiveActivities.add(activityRecord);
            }
        } else if (this.mInactiveActivities != null) {
            this.mInactiveActivities.remove(activityRecord);
        }
        this.mActivities.remove(activityRecord);
        this.mHasActivities = !this.mActivities.isEmpty();
        updateActivityConfigurationListener();
    }

    void clearActivities() {
        this.mInactiveActivities = null;
        this.mActivities.clear();
        this.mHasActivities = false;
        updateActivityConfigurationListener();
    }

    public boolean hasActivities() {
        return this.mHasActivities;
    }

    public boolean hasVisibleActivities() {
        return (this.mActivityStateFlags & 65536) != 0;
    }

    boolean hasActivityInVisibleTask() {
        return (this.mActivityStateFlags & 4194304) != 0;
    }

    public boolean hasActivitiesOrRecentTasks() {
        return this.mHasActivities || this.mHasRecentTasks;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskDisplayArea getTopActivityDisplayArea() {
        if (this.mActivities.isEmpty()) {
            return null;
        }
        int size = this.mActivities.size() - 1;
        com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
        com.android.server.wm.TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        for (int i = size - 1; i >= 0; i--) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mActivities.get(i);
            com.android.server.wm.TaskDisplayArea displayArea2 = activityRecord2.getDisplayArea();
            if (activityRecord2.compareTo((com.android.server.wm.WindowContainer) activityRecord) > 0 && displayArea2 != null) {
                activityRecord = activityRecord2;
                displayArea = displayArea2;
            }
        }
        return displayArea;
    }

    boolean updateTopResumingActivityInProcessIfNeeded(@android.annotation.NonNull final com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.DisplayContent displayContent;
        com.android.server.wm.TaskFragment taskFragment;
        com.android.server.wm.ActivityRecord activity;
        if (this.mInfo.targetSdkVersion >= 29 || this.mPreQTopResumedActivity == activityRecord) {
            return true;
        }
        if (!activityRecord.isAttached()) {
            return false;
        }
        if (this.mPreQTopResumedActivity != null && this.mPreQTopResumedActivity.isAttached()) {
            displayContent = this.mPreQTopResumedActivity.mDisplayContent;
        } else {
            displayContent = null;
        }
        boolean z = (displayContent != null && this.mPreQTopResumedActivity.isVisibleRequested() && this.mPreQTopResumedActivity.isFocusable()) ? false : true;
        com.android.server.wm.DisplayContent displayContent2 = activityRecord.mDisplayContent;
        if (!z && displayContent.compareTo((com.android.server.wm.WindowContainer) displayContent2) < 0) {
            z = true;
        }
        boolean z2 = (z || (activity = displayContent.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateTopResumingActivityInProcessIfNeeded$1;
                lambda$updateTopResumingActivityInProcessIfNeeded$1 = com.android.server.wm.WindowProcessController.lambda$updateTopResumingActivityInProcessIfNeeded$1(com.android.server.wm.ActivityRecord.this, (com.android.server.wm.ActivityRecord) obj);
                return lambda$updateTopResumingActivityInProcessIfNeeded$1;
            }
        }, true, this.mPreQTopResumedActivity)) == null || activity == this.mPreQTopResumedActivity) ? z : true;
        if (z2) {
            if (this.mPreQTopResumedActivity != null && this.mPreQTopResumedActivity.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && (taskFragment = this.mPreQTopResumedActivity.getTaskFragment()) != null) {
                taskFragment.startPausing(taskFragment.shouldBeVisible(null), false, activityRecord, "top-resumed-changed");
            }
            this.mPreQTopResumedActivity = activityRecord;
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateTopResumingActivityInProcessIfNeeded$1(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return activityRecord2 == activityRecord;
    }

    public void stopFreezingActivities() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int size = this.mActivities.size();
                while (size > 0) {
                    size--;
                    this.mActivities.get(size).stopFreezingScreen(true, true);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void finishActivities() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mActivities);
        for (int i = 0; i < arrayList.size(); i++) {
            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) arrayList.get(i);
            if (!activityRecord.finishing && activityRecord.isInRootTaskLocked()) {
                activityRecord.finishIfPossible("finish-heavy", true);
            }
        }
    }

    public boolean isInterestingToUser() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int size = this.mActivities.size();
                for (int i = 0; i < size; i++) {
                    if (this.mActivities.get(i).isInterestingToUserLocked()) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                }
                if (hasEmbeddedWindow()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private boolean hasEmbeddedWindow() {
        if (this.mRemoteActivities == null) {
            return false;
        }
        for (int size = this.mRemoteActivities.size() - 1; size >= 0; size--) {
            if ((this.mRemoteActivities.valueAt(size)[0] & 1) != 0 && this.mRemoteActivities.keyAt(size).isInterestingToUserLocked()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRunningActivity(java.lang.String str) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    if (str.equals(this.mActivities.get(size).packageName)) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void updateAppSpecificSettingsForAllActivitiesInPackage(java.lang.String str, java.lang.Integer num, android.os.LocaleList localeList, int i) {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
            if (str.equals(activityRecord.packageName) && activityRecord.applyAppSpecificConfig(num, localeList, java.lang.Integer.valueOf(i)) && activityRecord.isVisibleRequested()) {
                activityRecord.ensureActivityConfiguration();
            }
        }
    }

    public void clearPackagePreferredForHomeActivities() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
                    if (activityRecord.isActivityTypeHome()) {
                        android.util.Log.i("ActivityTaskManager", "Clearing package preferred activities from " + activityRecord.packageName);
                        try {
                            android.app.ActivityThread.getPackageManager().clearPackagePreferredActivities(activityRecord.packageName);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean hasStartedActivity(com.android.server.wm.ActivityRecord activityRecord) {
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mActivities.get(size);
            if (activityRecord != activityRecord2 && !activityRecord2.mAppStopped) {
                return true;
            }
        }
        return false;
    }

    boolean hasResumedActivity() {
        return (this.mActivityStateFlags & 2097152) != 0;
    }

    void updateIntentForHeavyWeightActivity(android.content.Intent intent) {
        if (this.mActivities.isEmpty()) {
            return;
        }
        com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(0);
        intent.putExtra("cur_app", activityRecord.packageName);
        intent.putExtra("cur_task", activityRecord.getTask().mTaskId);
    }

    boolean shouldKillProcessForRemovedTask(com.android.server.wm.Task task) {
        for (int i = 0; i < this.mActivities.size(); i++) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(i);
            if (!activityRecord.mAppStopped) {
                return false;
            }
            com.android.server.wm.Task task2 = activityRecord.getTask();
            if (task.mTaskId != task2.mTaskId && task2.inRecents) {
                return false;
            }
        }
        return true;
    }

    void releaseSomeActivities(java.lang.String str) {
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < this.mActivities.size(); i++) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(i);
            if (activityRecord.finishing || activityRecord.isState(com.android.server.wm.ActivityRecord.State.DESTROYING, com.android.server.wm.ActivityRecord.State.DESTROYED)) {
                return;
            }
            if (!activityRecord.isVisibleRequested() && activityRecord.mAppStopped && activityRecord.hasSavedState() && activityRecord.isDestroyable() && !activityRecord.isState(com.android.server.wm.ActivityRecord.State.STARTED, com.android.server.wm.ActivityRecord.State.RESUMED, com.android.server.wm.ActivityRecord.State.PAUSING, com.android.server.wm.ActivityRecord.State.PAUSED, com.android.server.wm.ActivityRecord.State.STOPPING) && activityRecord.getParent() != null) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(activityRecord);
            }
        }
        if (arrayList != null) {
            arrayList.sort(new java.util.Comparator() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    return ((com.android.server.wm.ActivityRecord) obj).compareTo((com.android.server.wm.WindowContainer) obj2);
                }
            });
            int max = java.lang.Math.max(arrayList.size(), 1);
            do {
                ((com.android.server.wm.ActivityRecord) arrayList.remove(0)).destroyImmediately(str);
                max--;
            } while (max > 0);
        }
    }

    public void getDisplayContextsWithErrorDialogs(java.util.List<android.content.Context> list) {
        if (list == null) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.RootWindowContainer rootWindowContainer = this.mAtm.mWindowManager.mRoot;
                rootWindowContainer.getDisplayContextsWithNonToastVisibleWindows(this.mPid, list);
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
                    android.content.Context displayUiContext = rootWindowContainer.getDisplayUiContext(activityRecord.getDisplayId());
                    if (displayUiContext != null && activityRecord.isVisibleRequested() && !list.contains(displayUiContext)) {
                        list.add(displayUiContext);
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void addHostActivity(com.android.server.wm.ActivityRecord activityRecord) {
        int[] remoteActivityFlags = getRemoteActivityFlags(activityRecord);
        remoteActivityFlags[0] = remoteActivityFlags[0] | 1;
    }

    void removeHostActivity(com.android.server.wm.ActivityRecord activityRecord) {
        removeRemoteActivityFlags(activityRecord, 1);
    }

    void addEmbeddedActivity(com.android.server.wm.ActivityRecord activityRecord) {
        int[] remoteActivityFlags = getRemoteActivityFlags(activityRecord);
        remoteActivityFlags[0] = remoteActivityFlags[0] | 2;
    }

    void removeEmbeddedActivity(com.android.server.wm.ActivityRecord activityRecord) {
        removeRemoteActivityFlags(activityRecord, 2);
    }

    private int[] getRemoteActivityFlags(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mRemoteActivities == null) {
            this.mRemoteActivities = new android.util.ArrayMap<>();
        }
        int[] iArr = this.mRemoteActivities.get(activityRecord);
        if (iArr == null) {
            int[] iArr2 = new int[1];
            this.mRemoteActivities.put(activityRecord, iArr2);
            return iArr2;
        }
        return iArr;
    }

    private void removeRemoteActivityFlags(com.android.server.wm.ActivityRecord activityRecord, int i) {
        int indexOfKey;
        if (this.mRemoteActivities != null && (indexOfKey = this.mRemoteActivities.indexOfKey(activityRecord)) >= 0) {
            int[] valueAt = this.mRemoteActivities.valueAt(indexOfKey);
            valueAt[0] = (~i) & valueAt[0];
            if (valueAt[0] == 0) {
                this.mRemoteActivities.removeAt(indexOfKey);
            }
        }
    }

    public int computeOomAdjFromActivities(com.android.server.wm.WindowProcessController.ComputeOomAdjCallback computeOomAdjCallback) {
        int i = this.mActivityStateFlags;
        if ((65536 & i) != 0) {
            computeOomAdjCallback.onVisibleActivity();
        } else if ((131072 & i) != 0) {
            computeOomAdjCallback.onPausedActivity();
        } else if ((262144 & i) != 0) {
            computeOomAdjCallback.onStoppingActivity((524288 & i) != 0);
        } else {
            computeOomAdjCallback.onOtherActivity();
        }
        return 65535 & i;
    }

    void computeProcessActivityState() {
        int i;
        com.android.server.wm.ActivityRecord.State state = com.android.server.wm.ActivityRecord.State.DESTROYED;
        boolean hasResumedActivity = hasResumedActivity();
        boolean z = (this.mActivityStateFlags & 1114112) != 0;
        int i2 = Integer.MAX_VALUE;
        boolean z2 = true;
        int i3 = 0;
        boolean z3 = false;
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
            if (activityRecord.isVisible()) {
                i3 |= 1048576;
            }
            com.android.server.wm.Task task = activityRecord.getTask();
            if (task != null && task.mLayerRank != -1) {
                i3 |= 4194304;
            }
            if (activityRecord.isVisibleRequested()) {
                if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                    i3 |= 2097152;
                }
                if (task != null && i2 > 0 && (i = task.mLayerRank) >= 0 && i2 > i) {
                    i2 = i;
                }
                z3 = true;
            } else if (!z3 && state != com.android.server.wm.ActivityRecord.State.PAUSING) {
                if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.PAUSING, com.android.server.wm.ActivityRecord.State.PAUSED)) {
                    state = com.android.server.wm.ActivityRecord.State.PAUSING;
                } else if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.STOPPING)) {
                    state = com.android.server.wm.ActivityRecord.State.STOPPING;
                    z2 &= activityRecord.finishing;
                }
            }
        }
        if (this.mRemoteActivities != null) {
            for (int size2 = this.mRemoteActivities.size() - 1; size2 >= 0; size2--) {
                if ((this.mRemoteActivities.valueAt(size2)[0] & 2) != 0 && this.mRemoteActivities.keyAt(size2).isVisibleRequested()) {
                    i3 |= 65536;
                }
            }
        }
        int i4 = (65535 & i2) | i3;
        if (z3) {
            i4 |= 65536;
        } else if (state == com.android.server.wm.ActivityRecord.State.PAUSING) {
            i4 |= 131072;
        } else if (state == com.android.server.wm.ActivityRecord.State.STOPPING) {
            i4 |= 262144;
            if (z2) {
                i4 |= 524288;
            }
        }
        this.mActivityStateFlags = i4;
        boolean z4 = (i4 & 1114112) != 0;
        if (!z && z4) {
            this.mAtm.mVisibleActivityProcessTracker.onAnyActivityVisible(this);
            this.mAtm.mWindowManager.onProcessActivityVisibilityChanged(this.mUid, true);
        } else if (z && !z4) {
            this.mAtm.mVisibleActivityProcessTracker.onAllActivitiesInvisible(this);
            this.mAtm.mWindowManager.onProcessActivityVisibilityChanged(this.mUid, false);
        } else if (z && !hasResumedActivity && hasResumedActivity()) {
            this.mAtm.mVisibleActivityProcessTracker.onActivityResumedWhileVisible(this);
        }
    }

    private void prepareOomAdjustment() {
        this.mAtm.mRootWindowContainer.rankTaskLayers();
        this.mAtm.mTaskSupervisor.computeProcessActivityStateBatch();
    }

    public int computeRelaunchReason() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (int size = this.mActivities.size() - 1; size >= 0; size--) {
                    com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
                    if (activityRecord.mRelaunchReason != 0) {
                        int i = activityRecord.mRelaunchReason;
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return 0;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public long getInputDispatchingTimeoutMillis() {
        long j;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (isInstrumenting() || isUsingWrapper()) {
                    j = 60000;
                } else {
                    j = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return j;
    }

    void clearProfilerIfNeeded() {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowProcessListener) obj).clearProfilerIfNeeded();
            }
        }, this.mListener));
    }

    void updateProcessInfo(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z4) {
            addToPendingTop();
        }
        if (z3) {
            prepareOomAdjustment();
        }
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda6(), this.mListener, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3)));
    }

    void scheduleUpdateOomAdj() {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda6(), this.mListener, false, false, true));
    }

    void addToPendingTop() {
        this.mAtm.mAmInternal.addPendingTopUid(this.mUid, this.mPid, this.mThread);
    }

    void updateServiceConnectionActivities() {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowProcessListener) obj).updateServiceConnectionActivities();
            }
        }, this.mListener));
    }

    void setPendingUiCleanAndForceProcessStateUpTo(int i) {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.WindowProcessListener) obj).setPendingUiCleanAndForceProcessStateUpTo(((java.lang.Integer) obj2).intValue());
            }
        }, this.mListener, java.lang.Integer.valueOf(i)));
    }

    boolean isRemoved() {
        return this.mListener.isRemoved();
    }

    private boolean shouldSetProfileProc() {
        return this.mAtm.mProfileApp != null && this.mAtm.mProfileApp.equals(this.mName) && (this.mAtm.mProfileProc == null || this.mAtm.mProfileProc == this);
    }

    android.app.ProfilerInfo createProfilerInfoIfNeeded() {
        android.app.ProfilerInfo profilerInfo = this.mAtm.mProfilerInfo;
        if (profilerInfo == null || profilerInfo.profileFile == null || !shouldSetProfileProc()) {
            return null;
        }
        if (profilerInfo.profileFd != null) {
            try {
                profilerInfo.profileFd = profilerInfo.profileFd.dup();
            } catch (java.io.IOException e) {
                profilerInfo.closeFd();
            }
        }
        return new android.app.ProfilerInfo(profilerInfo);
    }

    void onStartActivity(int i, android.content.pm.ActivityInfo activityInfo) {
        java.lang.String str = ((activityInfo.flags & 1) == 0 || !com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(activityInfo.packageName)) ? activityInfo.packageName : null;
        if (i == 2) {
            this.mAtm.mAmInternal.addPendingTopUid(this.mUid, this.mPid, this.mThread);
        }
        prepareOomAdjustment();
        this.mAtm.mH.sendMessageAtFrontOfQueue(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda10
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                ((com.android.server.wm.WindowProcessListener) obj).onStartActivity(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue(), (java.lang.String) obj4, ((java.lang.Long) obj5).longValue());
            }
        }, this.mListener, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(shouldSetProfileProc()), str, java.lang.Long.valueOf(activityInfo.applicationInfo.longVersionCode)));
    }

    void appDied(java.lang.String str) {
        this.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.WindowProcessListener) obj).appDied((java.lang.String) obj2);
            }
        }, this.mListener, str));
    }

    boolean handleAppDied() {
        this.mAtm.mTaskSupervisor.removeHistoryRecords(this);
        boolean z = false;
        boolean z2 = (this.mInactiveActivities == null || this.mInactiveActivities.isEmpty()) ? false : true;
        java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = (this.mHasActivities || z2) ? new java.util.ArrayList<>() : this.mActivities;
        if (this.mHasActivities) {
            arrayList.addAll(this.mActivities);
        }
        if (z2) {
            arrayList.addAll(this.mInactiveActivities);
        }
        if (isRemoved()) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).makeFinishingLocked();
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.ActivityRecord activityRecord = arrayList.get(size2);
            if (activityRecord.isVisibleRequested() || activityRecord.isVisible()) {
                z = true;
            }
            com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (taskFragment != null) {
                z |= taskFragment.handleAppDied(this);
            }
            activityRecord.handleAppDied();
        }
        clearRecentTasks();
        clearActivities();
        return z;
    }

    void registerDisplayAreaConfigurationListener(@android.annotation.Nullable com.android.server.wm.DisplayArea displayArea) {
        if (displayArea == null || displayArea.containsListener(this)) {
            return;
        }
        unregisterConfigurationListeners();
        this.mDisplayArea = displayArea;
        displayArea.registerConfigurationChangeListener(this);
    }

    @com.android.internal.annotations.VisibleForTesting
    void unregisterDisplayAreaConfigurationListener() {
        if (this.mDisplayArea == null) {
            return;
        }
        this.mDisplayArea.unregisterConfigurationChangeListener(this);
        this.mDisplayArea = null;
        onMergedOverrideConfigurationChanged(android.content.res.Configuration.EMPTY);
    }

    void registerActivityConfigurationListener(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || activityRecord.containsListener(this) || !this.mIsActivityConfigOverrideAllowed) {
            return;
        }
        unregisterConfigurationListeners();
        this.mConfigActivityRecord = activityRecord;
        activityRecord.registerConfigurationChangeListener(this);
    }

    private void unregisterActivityConfigurationListener() {
        if (this.mConfigActivityRecord == null) {
            return;
        }
        this.mConfigActivityRecord.unregisterConfigurationChangeListener(this);
        this.mConfigActivityRecord = null;
        onMergedOverrideConfigurationChanged(android.content.res.Configuration.EMPTY);
    }

    private void unregisterConfigurationListeners() {
        unregisterActivityConfigurationListener();
        unregisterDisplayAreaConfigurationListener();
    }

    void destroy() {
        unregisterConfigurationListeners();
    }

    private void updateActivityConfigurationListener() {
        if (!this.mIsActivityConfigOverrideAllowed) {
            return;
        }
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
            if (!activityRecord.finishing) {
                registerActivityConfigurationListener(activityRecord);
                return;
            }
        }
        unregisterActivityConfigurationListener();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        int topActivityDeviceId = getTopActivityDeviceId();
        if (topActivityDeviceId == this.mLastTopActivityDeviceId) {
            z = false;
        } else {
            this.mLastTopActivityDeviceId = topActivityDeviceId;
            z = true;
        }
        android.content.res.Configuration configuration2 = getConfiguration();
        if ((!z) & this.mLastReportedConfiguration.equals(configuration2)) {
            if (android.os.Build.IS_DEBUGGABLE && this.mHasImeService) {
                android.util.Slog.w("ActivityTaskManager", "Current config: " + configuration2 + " unchanged for IME proc " + this.mName);
                return;
            }
            return;
        }
        if (this.mCanUseSystemGrammaticalGender) {
            configuration2.setGrammaticalGender(this.mAtm.mGrammaticalManagerInternal.getSystemGrammaticalGender(this.mUserId));
        }
        if (this.mPauseConfigurationDispatchCount > 0) {
            this.mHasPendingConfigurationChange = true;
        } else {
            dispatchConfiguration(configuration2);
        }
    }

    private int getTopActivityDeviceId() {
        com.android.server.wm.ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity();
        if (topNonFinishingActivity != null && topNonFinishingActivity.mDisplayContent != null) {
            return this.mAtm.mTaskSupervisor.getDeviceIdForDisplayId(topNonFinishingActivity.mDisplayContent.mDisplayId);
        }
        return 0;
    }

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord getTopNonFinishingActivity() {
        if (this.mActivities.isEmpty()) {
            return null;
        }
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            if (!this.mActivities.get(size).finishing) {
                return this.mActivities.get(size);
            }
        }
        return null;
    }

    @Override // com.android.server.wm.ConfigurationContainerListener
    public void onMergedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        super.onRequestedOverrideConfigurationChanged(configuration);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void resolveOverrideConfiguration(android.content.res.Configuration configuration) {
        android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        if (requestedOverrideConfiguration.assetsSeq != 0 && configuration.assetsSeq > requestedOverrideConfiguration.assetsSeq) {
            requestedOverrideConfiguration.assetsSeq = 0;
        }
        super.resolveOverrideConfiguration(configuration);
        android.content.res.Configuration resolvedOverrideConfiguration = getResolvedOverrideConfiguration();
        resolvedOverrideConfiguration.windowConfiguration.setActivityType(0);
        resolvedOverrideConfiguration.seq = configuration.seq;
    }

    void dispatchConfiguration(@android.annotation.NonNull android.content.res.Configuration configuration) {
        this.mHasPendingConfigurationChange = false;
        android.app.IApplicationThread iApplicationThread = this.mThread;
        if (iApplicationThread == null) {
            if (android.os.Build.IS_DEBUGGABLE && this.mHasImeService) {
                android.util.Slog.w("ActivityTaskManager", "Unable to send config for IME proc " + this.mName + ": no app thread");
                return;
            }
            return;
        }
        configuration.seq = this.mAtm.increaseConfigurationSeqLocked();
        setLastReportedConfiguration(configuration);
        if (this.mRepProcState >= 16) {
            this.mHasCachedConfiguration = true;
            if (this.mRepProcState >= 16) {
                return;
            }
        }
        onConfigurationChangePreScheduled(configuration);
        scheduleClientTransactionItem(iApplicationThread, android.app.servertransaction.ConfigurationChangeItem.obtain(configuration, this.mLastTopActivityDeviceId));
    }

    private void onConfigurationChangePreScheduled(@android.annotation.NonNull android.content.res.Configuration configuration) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -4629255026637000251L, 0, null, java.lang.String.valueOf(this.mName), java.lang.String.valueOf(configuration));
        if (android.os.Build.IS_DEBUGGABLE && this.mHasImeService) {
            android.util.Slog.v("ActivityTaskManager", "Sending to IME proc " + this.mName + " new config " + configuration);
        }
        this.mHasCachedConfiguration = false;
    }

    @com.android.internal.annotations.VisibleForTesting
    void scheduleClientTransactionItem(@android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem) {
        android.app.IApplicationThread iApplicationThread = this.mThread;
        if (iApplicationThread == null) {
            if (android.os.Build.IS_DEBUGGABLE) {
                android.util.Slog.w("ActivityTaskManager", "Unable to send transaction to client proc " + this.mName + ": no app thread");
                return;
            }
            return;
        }
        scheduleClientTransactionItem(iApplicationThread, clientTransactionItem);
    }

    private void scheduleClientTransactionItem(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem) {
        try {
            if (this.mWindowSession != null && this.mWindowSession.hasWindow()) {
                this.mAtm.getLifecycleManager().scheduleTransactionItem(iApplicationThread, clientTransactionItem);
            } else {
                this.mAtm.getLifecycleManager().scheduleTransactionItemNow(iApplicationThread, clientTransactionItem);
            }
        } catch (android.os.DeadObjectException e) {
            android.util.Slog.w("ActivityTaskManager", "Failed for dead process. ClientTransactionItem=" + clientTransactionItem + " owner=" + this.mOwner);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e("ActivityTaskManager", "Failed to schedule ClientTransactionItem=" + clientTransactionItem + " owner=" + this.mOwner, e2);
        }
    }

    void setLastReportedConfiguration(android.content.res.Configuration configuration) {
        synchronized (this.mLastReportedConfiguration) {
            this.mLastReportedConfiguration.setTo(configuration);
        }
    }

    void pauseConfigurationDispatch() {
        this.mPauseConfigurationDispatchCount++;
    }

    boolean resumeConfigurationDispatch() {
        if (this.mPauseConfigurationDispatchCount == 0) {
            return false;
        }
        this.mPauseConfigurationDispatchCount--;
        return this.mHasPendingConfigurationChange;
    }

    void updateAssetConfiguration(int i) {
        if (!this.mHasActivities || !this.mIsActivityConfigOverrideAllowed) {
            android.content.res.Configuration configuration = new android.content.res.Configuration(getRequestedOverrideConfiguration());
            configuration.assetsSeq = i;
            onRequestedOverrideConfigurationChanged(configuration);
            return;
        }
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = this.mActivities.get(size);
            android.content.res.Configuration configuration2 = new android.content.res.Configuration(activityRecord.getRequestedOverrideConfiguration());
            configuration2.assetsSeq = i;
            activityRecord.onRequestedOverrideConfigurationChanged(configuration2);
            if (activityRecord.isVisibleRequested()) {
                activityRecord.ensureActivityConfiguration();
            }
        }
    }

    android.content.res.Configuration prepareConfigurationForLaunchingActivity() {
        android.content.res.Configuration configuration = getConfiguration();
        if (this.mHasPendingConfigurationChange) {
            this.mHasPendingConfigurationChange = false;
            configuration.seq = this.mAtm.increaseConfigurationSeqLocked();
        }
        this.mHasCachedConfiguration = false;
        return configuration;
    }

    public long getCpuTime() {
        return this.mListener.getCpuTime();
    }

    void addRecentTask(com.android.server.wm.Task task) {
        this.mRecentTasks.add(task);
        this.mHasRecentTasks = true;
    }

    void removeRecentTask(com.android.server.wm.Task task) {
        this.mRecentTasks.remove(task);
        this.mHasRecentTasks = !this.mRecentTasks.isEmpty();
    }

    public boolean hasRecentTasks() {
        return this.mHasRecentTasks;
    }

    void clearRecentTasks() {
        for (int size = this.mRecentTasks.size() - 1; size >= 0; size--) {
            this.mRecentTasks.get(size).clearRootProcess();
        }
        this.mRecentTasks.clear();
        this.mHasRecentTasks = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:
    
        if (r5.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void appEarlyNotResponding(java.lang.String str, java.lang.Runnable runnable) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mAtm.mController == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                java.lang.Runnable runnable2 = null;
                try {
                    if (this.mAtm.mController.appEarlyNotResponding(this.mName, this.mPid, str) < 0) {
                    }
                    runnable = null;
                    runnable2 = runnable;
                } catch (android.os.RemoteException e) {
                    this.mAtm.mController = null;
                    com.android.server.Watchdog.getInstance().setActivityController(null);
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                if (runnable2 != null) {
                    runnable2.run();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        if (r6.mPid != com.android.server.wm.WindowManagerService.MY_PID) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean appNotResponding(java.lang.String str, java.lang.Runnable runnable, java.lang.Runnable runnable2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mAtm.mController == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                try {
                    int appNotResponding = this.mAtm.mController.appNotResponding(this.mName, this.mPid, str);
                    if (appNotResponding == 0) {
                        runnable = null;
                    } else {
                        if (appNotResponding < 0) {
                        }
                        runnable = runnable2;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    if (runnable == null) {
                        return false;
                    }
                    runnable.run();
                    return true;
                } catch (android.os.RemoteException e) {
                    this.mAtm.mController = null;
                    com.android.server.Watchdog.getInstance().setActivityController(null);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onServiceStarted(android.content.pm.ServiceInfo serviceInfo) {
        char c;
        java.lang.String str = serviceInfo.permission;
        if (str == null) {
            return;
        }
        switch (str.hashCode()) {
            case -769871357:
                if (str.equals("android.permission.BIND_VOICE_INTERACTION")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1412417858:
                if (str.equals("android.permission.BIND_ACCESSIBILITY_SERVICE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1448369304:
                if (str.equals("android.permission.BIND_INPUT_METHOD")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mHasImeService = true;
                break;
            case 1:
            case 2:
                break;
            default:
                return;
        }
        this.mIsActivityConfigOverrideAllowed = false;
        unregisterActivityConfigurationListener();
    }

    public void onTopProcChanged() {
        if (this.mAtm.mVrController.isInterestingToSchedGroup()) {
            this.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowProcessController.this.lambda$onTopProcChanged$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTopProcChanged$2() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtm.mVrController.onTopProcChangedLocked(this);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean isHomeProcess() {
        return this == this.mAtm.mHomeProcess;
    }

    public boolean isShowingUiWhileDozing() {
        return this == this.mAtm.mVisibleDozeUiProcess;
    }

    public boolean isPreviousProcess() {
        return this == this.mAtm.mPreviousProcess;
    }

    public boolean isHeavyWeightProcess() {
        return this == this.mAtm.mHeavyWeightProcess;
    }

    public boolean isFactoryTestProcess() {
        android.content.ComponentName componentName;
        int i = this.mAtm.mFactoryTest;
        if (i == 0) {
            return false;
        }
        if (i == 1 && (componentName = this.mAtm.mTopComponent) != null && this.mName.equals(componentName.getPackageName())) {
            return true;
        }
        if (i != 2 || (this.mInfo.flags & 16) == 0) {
            return false;
        }
        return true;
    }

    void setRunningRecentsAnimation(boolean z) {
        if (z) {
            addAnimatingReason(4);
        } else {
            removeAnimatingReason(4);
        }
    }

    void setRunningRemoteAnimation(boolean z) {
        if (z) {
            addAnimatingReason(1);
        } else {
            removeAnimatingReason(1);
        }
    }

    void addAnimatingReason(int i) {
        int i2 = this.mAnimatingReasons;
        this.mAnimatingReasons = i | this.mAnimatingReasons;
        if (i2 == 0) {
            setAnimating(true);
        }
    }

    void removeAnimatingReason(int i) {
        int i2 = this.mAnimatingReasons;
        this.mAnimatingReasons = (~i) & this.mAnimatingReasons;
        if (i2 != 0 && this.mAnimatingReasons == 0) {
            setAnimating(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAnimating$3(boolean z) {
        this.mListener.setRunningRemoteAnimation(z);
    }

    private void setAnimating(final boolean z) {
        this.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowProcessController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowProcessController.this.lambda$setAnimating$3(z);
            }
        });
    }

    boolean isRunningRemoteTransition() {
        return (this.mAnimatingReasons & 1) != 0;
    }

    void setRunningAnimationUnsafe() {
        this.mListener.setRunningRemoteAnimation(true);
    }

    public java.lang.String toString() {
        if (this.mOwner != null) {
            return this.mOwner.toString();
        }
        return null;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mActivities.size() > 0) {
                    printWriter.print(str);
                    printWriter.println("Activities:");
                    for (int i = 0; i < this.mActivities.size(); i++) {
                        printWriter.print(str);
                        printWriter.print("  - ");
                        printWriter.println(this.mActivities.get(i));
                    }
                }
                if (this.mRemoteActivities != null && !this.mRemoteActivities.isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("Remote Activities:");
                    for (int size = this.mRemoteActivities.size() - 1; size >= 0; size--) {
                        printWriter.print(str);
                        printWriter.print("  - ");
                        printWriter.print(this.mRemoteActivities.keyAt(size));
                        printWriter.print(" flags=");
                        int i2 = this.mRemoteActivities.valueAt(size)[0];
                        if ((i2 & 1) != 0) {
                            printWriter.print("host ");
                        }
                        if ((i2 & 2) != 0) {
                            printWriter.print("embedded");
                        }
                        printWriter.println();
                    }
                }
                if (this.mRecentTasks.size() > 0) {
                    printWriter.println(str + "Recent Tasks:");
                    for (int i3 = 0; i3 < this.mRecentTasks.size(); i3++) {
                        printWriter.println(str + "  - " + this.mRecentTasks.get(i3));
                    }
                }
                if (this.mVrThreadTid != 0) {
                    printWriter.print(str);
                    printWriter.print("mVrThreadTid=");
                    printWriter.println(this.mVrThreadTid);
                }
                this.mBgLaunchController.dump(printWriter, str);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        printWriter.println(str + " Configuration=" + getConfiguration());
        printWriter.println(str + " OverrideConfiguration=" + getRequestedOverrideConfiguration());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(" mLastReportedConfiguration=");
        sb.append(this.mHasCachedConfiguration ? "(cached) " + this.mLastReportedConfiguration : this.mLastReportedConfiguration);
        printWriter.println(sb.toString());
        int i4 = this.mAnimatingReasons;
        if (i4 != 0) {
            printWriter.print(str + " mAnimatingReasons=");
            if ((i4 & 1) != 0) {
                printWriter.print("remote-animation|");
            }
            if ((i4 & 2) != 0) {
                printWriter.print("wakefulness|");
            }
            if ((i4 & 4) != 0) {
                printWriter.print("legacy-recents");
            }
            printWriter.println();
        }
        int i5 = this.mActivityStateFlags;
        if (i5 != 65535) {
            printWriter.print(str + " mActivityStateFlags=");
            if ((1048576 & i5) != 0) {
                printWriter.print("W|");
            }
            if ((65536 & i5) != 0) {
                printWriter.print("V|");
                if ((2097152 & i5) != 0) {
                    printWriter.print("R|");
                }
            } else if ((131072 & i5) != 0) {
                printWriter.print("P|");
            } else if ((262144 & i5) != 0) {
                printWriter.print("S|");
                if ((524288 & i5) != 0) {
                    printWriter.print("F|");
                }
            }
            if ((4194304 & i5) != 0) {
                printWriter.print("VT|");
            }
            int i6 = i5 & 65535;
            if (i6 != 65535) {
                printWriter.print("taskLayer=" + i6);
            }
            printWriter.println();
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        this.mListener.dumpDebug(protoOutputStream, j);
    }
}
