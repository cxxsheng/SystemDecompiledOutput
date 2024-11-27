package com.android.server.wm;

import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;

/* loaded from: classes3.dex */
class RootWindowContainer extends com.android.server.wm.WindowContainer<com.android.server.wm.DisplayContent> implements android.hardware.display.DisplayManager.DisplayListener {
    private static final java.lang.String DISPLAY_OFF_SLEEP_TOKEN_TAG = "Display-off";
    static final int MATCH_ATTACHED_TASK_ONLY = 0;
    static final int MATCH_ATTACHED_TASK_OR_RECENT_TASKS = 1;
    static final int MATCH_ATTACHED_TASK_OR_RECENT_TASKS_AND_RESTORE = 2;
    private static final int MSG_SEND_SLEEP_TRANSITION = 3;
    private static final int SET_BUTTON_BRIGHTNESS_OVERRIDE = 0;
    private static final int SET_SCREEN_BRIGHTNESS_OVERRIDE = 1;
    private static final int SET_USER_ACTIVITY_TIMEOUT = 2;
    private static final long SLEEP_TRANSITION_WAIT_MILLIS = 1000;
    private static final java.lang.String TAG = "WindowManager";
    private static final java.lang.String TAG_RECENTS = "WindowManager";
    static final java.lang.String TAG_STATES = "WindowManager";
    static final java.lang.String TAG_TASKS = "WindowManager";
    private final com.android.server.wm.RootWindowContainer.AttachApplicationHelper mAttachApplicationHelper;
    private float mButtonBrightnessOverride;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mCloseSystemDialogsConsumer;
    private java.lang.String mCloseSystemDialogsReason;
    int mCurrentUser;
    private com.android.server.wm.DisplayContent mDefaultDisplay;
    private java.lang.String mDestroyAllActivitiesReason;
    private final java.lang.Runnable mDestroyAllActivitiesRunnable;

    @android.annotation.NonNull
    private final com.android.server.wm.DeviceStateController mDeviceStateController;
    private final android.util.SparseArray<android.util.IntArray> mDisplayAccessUIDs;
    android.hardware.display.DisplayManager mDisplayManager;
    private android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    final com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer mDisplayOffTokenAcquirer;

    @android.annotation.NonNull
    private final com.android.server.wm.DisplayRotationCoordinator mDisplayRotationCoordinator;
    private final android.util.SparseArray<android.view.SurfaceControl.Transaction> mDisplayTransactions;
    com.android.server.wm.RootWindowContainer.FinishDisabledPackageActivitiesHelper mFinishDisabledPackageActivitiesHelper;
    private final android.os.Handler mHandler;
    private java.lang.Object mLastWindowFreezeSource;
    private boolean mObscureApplicationContentOnSecondaryDisplays;
    boolean mOrientationChangeComplete;
    private final com.android.server.wm.RootWindowContainer.RankTaskLayersRunnable mRankTaskLayersRunnable;
    private float mScreenBrightnessOverride;
    com.android.server.wm.ActivityTaskManagerService mService;
    final android.util.SparseArray<com.android.server.wm.RootWindowContainer.SleepToken> mSleepTokens;
    private boolean mSustainedPerformanceModeCurrent;
    private boolean mSustainedPerformanceModeEnabled;
    private boolean mTaskLayersChanged;
    com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private final com.android.server.wm.RootWindowContainer.FindTaskResult mTmpFindTaskResult;
    private int mTmpTaskLayerRank;
    final android.util.ArrayMap<java.lang.Integer, com.android.server.wm.ActivityRecord> mTopFocusedAppByProcess;
    private int mTopFocusedDisplayId;
    private boolean mUpdateRotation;
    private long mUserActivityTimeout;
    android.util.SparseIntArray mUserRootTaskInFront;
    boolean mWallpaperActionPending;
    com.android.server.wm.WindowManagerService mWindowManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AnyTaskForIdMatchTaskMode {
    }

    /* renamed from: com.android.server.wm.RootWindowContainer$1, reason: invalid class name */
    class AnonymousClass1 implements java.lang.Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RootWindowContainer.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        com.android.server.wm.RootWindowContainer.this.mTaskSupervisor.beginDeferResume();
                        com.android.server.wm.RootWindowContainer.this.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.wm.RootWindowContainer.AnonymousClass1.this.lambda$run$0((com.android.server.wm.ActivityRecord) obj);
                            }
                        });
                    } finally {
                        com.android.server.wm.RootWindowContainer.this.mTaskSupervisor.endDeferResume();
                        com.android.server.wm.RootWindowContainer.this.resumeFocusedTasksTopActivities();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(com.android.server.wm.ActivityRecord activityRecord) {
            if (activityRecord.finishing || !activityRecord.isDestroyable()) {
                return;
            }
            activityRecord.destroyImmediately(com.android.server.wm.RootWindowContainer.this.mDestroyAllActivitiesReason);
        }
    }

    static class FindTaskResult implements java.util.function.Predicate<com.android.server.wm.Task> {
        private android.content.ComponentName cls;
        private android.net.Uri documentData;
        private boolean isDocument;
        private int mActivityType;
        com.android.server.wm.ActivityRecord mCandidateRecord;
        com.android.server.wm.ActivityRecord mIdealRecord;
        private android.content.pm.ActivityInfo mInfo;
        private android.content.Intent mIntent;
        private java.lang.String mTaskAffinity;
        private int userId;

        FindTaskResult() {
        }

        void init(int i, java.lang.String str, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo) {
            this.mActivityType = i;
            this.mTaskAffinity = str;
            this.mIntent = intent;
            this.mInfo = activityInfo;
            this.mIdealRecord = null;
            this.mCandidateRecord = null;
        }

        void process(com.android.server.wm.WindowContainer windowContainer) {
            this.cls = this.mIntent.getComponent();
            if (this.mInfo.targetActivity != null) {
                this.cls = new android.content.ComponentName(this.mInfo.packageName, this.mInfo.targetActivity);
            }
            this.userId = android.os.UserHandle.getUserId(this.mInfo.applicationInfo.uid);
            this.isDocument = (this.mIntent != null) & this.mIntent.isDocument();
            this.documentData = this.isDocument ? this.mIntent.getData() : null;
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -8961882615747561040L, 0, null, java.lang.String.valueOf(this.mInfo), java.lang.String.valueOf(windowContainer));
            windowContainer.forAllLeafTasks(this);
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.Task task) {
            android.net.Uri uri;
            boolean z;
            if (!com.android.server.wm.ConfigurationContainer.isCompatibleActivityType(this.mActivityType, task.getActivityType())) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 8899721161806265460L, 0, null, java.lang.String.valueOf(task));
                return false;
            }
            if (task.voiceSession != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 6841550641928224256L, 0, null, java.lang.String.valueOf(task));
                return false;
            }
            if (task.mUserId != this.userId) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 4468520936943270392L, 0, null, java.lang.String.valueOf(task));
                return false;
            }
            com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(false);
            if (topNonFinishingActivity == null || topNonFinishingActivity.finishing || topNonFinishingActivity.mUserId != this.userId || topNonFinishingActivity.launchMode == 3) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -4764624740388751268L, 0, null, java.lang.String.valueOf(task), java.lang.String.valueOf(topNonFinishingActivity));
                return false;
            }
            if (!com.android.server.wm.ConfigurationContainer.isCompatibleActivityType(topNonFinishingActivity.getActivityType(), this.mActivityType)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 9031436623838917667L, 0, null, java.lang.String.valueOf(task));
                return false;
            }
            android.content.Intent intent = task.intent;
            android.content.Intent intent2 = task.affinityIntent;
            if (intent != null && intent.isDocument()) {
                uri = intent.getData();
                z = true;
            } else if (intent2 != null && intent2.isDocument()) {
                uri = intent2.getData();
                z = true;
            } else {
                uri = null;
                z = false;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 6022828946761399284L, 0, null, java.lang.String.valueOf(task.realActivity != null ? task.realActivity.flattenToShortString() : ""), java.lang.String.valueOf(task.rootAffinity), java.lang.String.valueOf(this.mIntent.getComponent().flattenToShortString()), java.lang.String.valueOf(this.mTaskAffinity));
            if (task.realActivity != null && task.realActivity.compareTo(this.cls) == 0 && java.util.Objects.equals(this.documentData, uri)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -3413620974545388702L, 0, null, null);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2649361982747625232L, 0, null, java.lang.String.valueOf(this.mIntent), java.lang.String.valueOf(topNonFinishingActivity.intent));
                this.mIdealRecord = topNonFinishingActivity;
                return true;
            }
            if (intent2 != null && intent2.getComponent() != null && intent2.getComponent().compareTo(this.cls) == 0 && java.util.Objects.equals(this.documentData, uri)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -3413620974545388702L, 0, null, null);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2649361982747625232L, 0, null, java.lang.String.valueOf(this.mIntent), java.lang.String.valueOf(topNonFinishingActivity.intent));
                this.mIdealRecord = topNonFinishingActivity;
                return true;
            }
            if (this.isDocument || z || this.mIdealRecord != null || this.mCandidateRecord != null || task.rootAffinity == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 6481733556290926693L, 0, null, java.lang.String.valueOf(task));
            } else if (task.rootAffinity.equals(this.mTaskAffinity) && task.isSameRequiredDisplayCategory(this.mInfo)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 7046266138098744790L, 0, null, null);
                this.mCandidateRecord = topNonFinishingActivity;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.wm.WindowState windowState) {
        if (windowState.mHasSurface) {
            try {
                windowState.mClient.closeSystemDialogs(this.mCloseSystemDialogsReason);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    RootWindowContainer(com.android.server.wm.WindowManagerService windowManagerService) {
        super(windowManagerService);
        this.mLastWindowFreezeSource = null;
        this.mButtonBrightnessOverride = Float.NaN;
        this.mScreenBrightnessOverride = Float.NaN;
        this.mUserActivityTimeout = -1L;
        this.mUpdateRotation = false;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeEnabled = false;
        this.mSustainedPerformanceModeCurrent = false;
        this.mOrientationChangeComplete = true;
        this.mWallpaperActionPending = false;
        this.mTopFocusedDisplayId = -1;
        this.mTopFocusedAppByProcess = new android.util.ArrayMap<>();
        this.mDisplayAccessUIDs = new android.util.SparseArray<>();
        this.mDisplayTransactions = new android.util.SparseArray<>();
        this.mUserRootTaskInFront = new android.util.SparseIntArray(2);
        this.mSleepTokens = new android.util.SparseArray<>();
        this.mTaskLayersChanged = true;
        this.mRankTaskLayersRunnable = new com.android.server.wm.RootWindowContainer.RankTaskLayersRunnable();
        this.mAttachApplicationHelper = new com.android.server.wm.RootWindowContainer.AttachApplicationHelper();
        this.mDestroyAllActivitiesRunnable = new com.android.server.wm.RootWindowContainer.AnonymousClass1();
        this.mTmpFindTaskResult = new com.android.server.wm.RootWindowContainer.FindTaskResult();
        this.mCloseSystemDialogsConsumer = new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda46
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$new$0((com.android.server.wm.WindowState) obj);
            }
        };
        this.mFinishDisabledPackageActivitiesHelper = new com.android.server.wm.RootWindowContainer.FinishDisabledPackageActivitiesHelper();
        this.mHandler = new com.android.server.wm.RootWindowContainer.MyHandler(windowManagerService.mH.getLooper());
        this.mService = windowManagerService.mAtmService;
        this.mTaskSupervisor = this.mService.mTaskSupervisor;
        this.mTaskSupervisor.mRootWindowContainer = this;
        com.android.server.wm.ActivityTaskManagerService activityTaskManagerService = this.mService;
        java.util.Objects.requireNonNull(activityTaskManagerService);
        this.mDisplayOffTokenAcquirer = activityTaskManagerService.new SleepTokenAcquirerImpl(DISPLAY_OFF_SLEEP_TOKEN_TAG);
        this.mDeviceStateController = new com.android.server.wm.DeviceStateController(windowManagerService.mContext, windowManagerService.mGlobalLock);
        this.mDisplayRotationCoordinator = new com.android.server.wm.DisplayRotationCoordinator();
    }

    boolean updateFocusedWindowLocked(int i, boolean z) {
        this.mTopFocusedAppByProcess.clear();
        boolean z2 = false;
        int i2 = -1;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(size);
            z2 |= displayContent.updateFocusedWindowLocked(i, z, i2);
            com.android.server.wm.WindowState windowState = displayContent.mCurrentFocus;
            if (windowState != null) {
                int i3 = windowState.mSession.mPid;
                if (this.mTopFocusedAppByProcess.get(java.lang.Integer.valueOf(i3)) == null) {
                    this.mTopFocusedAppByProcess.put(java.lang.Integer.valueOf(i3), windowState.mActivityRecord);
                }
                if (i2 == -1) {
                    i2 = displayContent.getDisplayId();
                }
            } else if (i2 == -1 && displayContent.mFocusedApp != null) {
                i2 = displayContent.getDisplayId();
            }
        }
        int i4 = i2 != -1 ? i2 : 0;
        if (this.mTopFocusedDisplayId != i4) {
            this.mTopFocusedDisplayId = i4;
            this.mWmService.mInputManager.setFocusedDisplay(i4);
            this.mWmService.mPolicy.setTopFocusedDisplay(i4);
            this.mWmService.mAccessibilityController.setFocusedDisplay(i4);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 3331249072840061049L, 1, null, java.lang.Long.valueOf(i4));
        }
        return z2;
    }

    com.android.server.wm.DisplayContent getTopFocusedDisplayContent() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(this.mTopFocusedDisplayId);
        return displayContent != null ? displayContent : getDisplayContent(0);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isOnTop() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    void onChildPositionChanged(com.android.server.wm.WindowContainer windowContainer) {
        this.mWmService.updateFocusedWindowLocked(0, !this.mWmService.mPerDisplayFocusEnabled);
        this.mTaskSupervisor.updateTopResumedActivityIfNeeded("onChildPositionChanged");
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isAttached() {
        return true;
    }

    void onSettingsRetrieved() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(i);
            if (this.mWmService.mDisplayWindowSettings.updateSettingsForDisplay(displayContent)) {
                displayContent.reconfigureDisplayLocked();
                if (displayContent.isDefaultDisplay) {
                    this.mWmService.mAtmService.updateConfigurationLocked(this.mWmService.computeNewConfiguration(displayContent.getDisplayId()), null, false);
                }
            }
        }
    }

    boolean isLayoutNeeded() {
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            if (((com.android.server.wm.DisplayContent) this.mChildren.get(i)).isLayoutNeeded()) {
                return true;
            }
        }
        return false;
    }

    void getWindowsByName(java.util.ArrayList<com.android.server.wm.WindowState> arrayList, java.lang.String str) {
        int i;
        try {
            i = java.lang.Integer.parseInt(str, 16);
            str = null;
        } catch (java.lang.RuntimeException e) {
            i = 0;
        }
        getWindowsByName(arrayList, str, i);
    }

    private void getWindowsByName(final java.util.ArrayList<com.android.server.wm.WindowState> arrayList, final java.lang.String str, final int i) {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda36
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$getWindowsByName$1(str, arrayList, i, (com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getWindowsByName$1(java.lang.String str, java.util.ArrayList arrayList, int i, com.android.server.wm.WindowState windowState) {
        if (str != null) {
            if (windowState.mAttrs.getTitle().toString().contains(str)) {
                arrayList.add(windowState);
            }
        } else if (java.lang.System.identityHashCode(windowState) == i) {
            arrayList.add(windowState);
        }
    }

    com.android.server.wm.WindowToken getWindowToken(android.os.IBinder iBinder) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowToken windowToken = ((com.android.server.wm.DisplayContent) this.mChildren.get(size)).getWindowToken(iBinder);
            if (windowToken != null) {
                return windowToken;
            }
        }
        return null;
    }

    com.android.server.wm.DisplayContent getWindowTokenDisplay(com.android.server.wm.WindowToken windowToken) {
        if (windowToken == null) {
            return null;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(size);
            if (displayContent.getWindowToken(windowToken.token) == windowToken) {
                return displayContent;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.ConfigurationContainer
    public void dispatchConfigurationToChild(com.android.server.wm.DisplayContent displayContent, android.content.res.Configuration configuration) {
        if (displayContent.isDefaultDisplay) {
            displayContent.performDisplayOverrideConfigUpdate(configuration);
        } else {
            displayContent.onConfigurationChanged(configuration);
        }
    }

    void refreshSecureSurfaceState() {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda41
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$refreshSecureSurfaceState$2((com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$refreshSecureSurfaceState$2(com.android.server.wm.WindowState windowState) {
        windowState.setSecureLocked(windowState.isSecureLocked());
    }

    void updateHiddenWhileSuspendedState(final android.util.ArraySet<java.lang.String> arraySet, final boolean z) {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$updateHiddenWhileSuspendedState$3(arraySet, z, (com.android.server.wm.WindowState) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateHiddenWhileSuspendedState$3(android.util.ArraySet arraySet, boolean z, com.android.server.wm.WindowState windowState) {
        if (arraySet.contains(windowState.getOwningPackage())) {
            windowState.setHiddenWhileSuspended(z);
        }
    }

    void updateAppOpsState() {
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).updateAppOpsState();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$canShowStrictModeViolation$5(int i, com.android.server.wm.WindowState windowState) {
        return windowState.mSession.mPid == i && windowState.isVisible();
    }

    boolean canShowStrictModeViolation(final int i) {
        return getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda47
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$canShowStrictModeViolation$5;
                lambda$canShowStrictModeViolation$5 = com.android.server.wm.RootWindowContainer.lambda$canShowStrictModeViolation$5(i, (com.android.server.wm.WindowState) obj);
                return lambda$canShowStrictModeViolation$5;
            }
        }) != null;
    }

    void closeSystemDialogs(java.lang.String str) {
        this.mCloseSystemDialogsReason = str;
        forAllWindows(this.mCloseSystemDialogsConsumer, false);
    }

    boolean hasPendingLayoutChanges(com.android.server.wm.WindowAnimator windowAnimator) {
        int size = this.mChildren.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            int i2 = ((com.android.server.wm.DisplayContent) this.mChildren.get(i)).pendingLayoutChanges;
            if ((i2 & 4) != 0) {
                windowAnimator.mBulkUpdateParams |= 2;
            }
            if (i2 != 0) {
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean reclaimSomeSurfaceMemory(com.android.server.wm.WindowStateAnimator windowStateAnimator, java.lang.String str, boolean z) {
        boolean z2;
        com.android.server.wm.WindowSurfaceController windowSurfaceController = windowStateAnimator.mSurfaceController;
        com.android.server.wm.EventLogTags.writeWmNoSurfaceMemory(windowStateAnimator.mWin.toString(), windowStateAnimator.mSession.mPid, str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.Slog.i("WindowManager", "Out of memory for surface!  Looking for leaks...");
            int size = this.mChildren.size();
            boolean z3 = 0;
            boolean z4 = false;
            for (int i = 0; i < size; i++) {
                z4 |= ((com.android.server.wm.DisplayContent) this.mChildren.get(i)).destroyLeakedSurfaces();
            }
            if (z4) {
                z2 = false;
            } else {
                android.util.Slog.w("WindowManager", "No leaked surfaces; killing applications!");
                final android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                int i2 = 0;
                z2 = false;
                while (i2 < size) {
                    ((com.android.server.wm.DisplayContent) this.mChildren.get(i2)).forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda40
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.RootWindowContainer.this.lambda$reclaimSomeSurfaceMemory$6(sparseIntArray, (com.android.server.wm.WindowState) obj);
                        }
                    }, z3);
                    if (sparseIntArray.size() > 0) {
                        int size2 = sparseIntArray.size();
                        int[] iArr = new int[size2];
                        for (int i3 = z3; i3 < size2; i3++) {
                            iArr[i3] = sparseIntArray.keyAt(i3);
                        }
                        try {
                            try {
                                if (this.mWmService.mActivityManager.killPids(iArr, "Free memory", z)) {
                                    z2 = true;
                                }
                            } catch (android.os.RemoteException e) {
                            }
                        } catch (android.os.RemoteException e2) {
                        }
                    }
                    i2++;
                    z3 = 0;
                }
            }
            if (z4 || z2) {
                android.util.Slog.w("WindowManager", "Looks like we have reclaimed some memory, clearing surface for retry.");
                if (windowSurfaceController != null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 865845626039449679L, 0, null, java.lang.String.valueOf(windowStateAnimator.mWin));
                    android.view.SurfaceControl.Transaction transaction = this.mWmService.mTransactionFactory.get();
                    windowStateAnimator.destroySurface(transaction);
                    transaction.apply();
                    if (windowStateAnimator.mWin.mActivityRecord != null) {
                        windowStateAnimator.mWin.mActivityRecord.removeStartingWindow();
                    }
                }
                try {
                    windowStateAnimator.mWin.mClient.dispatchGetNewSurface();
                } catch (android.os.RemoteException e3) {
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return z4 || z2;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reclaimSomeSurfaceMemory$6(android.util.SparseIntArray sparseIntArray, com.android.server.wm.WindowState windowState) {
        if (this.mWmService.mForceRemoves.contains(windowState)) {
            return;
        }
        com.android.server.wm.WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        if (windowStateAnimator.mSurfaceController != null) {
            sparseIntArray.append(windowStateAnimator.mSession.mPid, windowStateAnimator.mSession.mPid);
        }
    }

    void performSurfacePlacement() {
        android.os.Trace.traceBegin(32L, "performSurfacePlacement");
        try {
            performSurfacePlacementNoTrace();
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    void performSurfacePlacementNoTrace() {
        float f;
        if (this.mWmService.mFocusMayChange) {
            this.mWmService.mFocusMayChange = false;
            this.mWmService.updateFocusedWindowLocked(3, false);
        }
        float f2 = Float.NaN;
        this.mButtonBrightnessOverride = Float.NaN;
        this.mScreenBrightnessOverride = Float.NaN;
        this.mUserActivityTimeout = -1L;
        this.mObscureApplicationContentOnSecondaryDisplays = false;
        this.mSustainedPerformanceModeCurrent = false;
        this.mWmService.mTransactionSequence++;
        com.android.server.wm.DisplayContent defaultDisplayContentLocked = this.mWmService.getDefaultDisplayContentLocked();
        com.android.server.wm.WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        android.os.Trace.traceBegin(32L, "applySurfaceChanges");
        try {
            try {
                applySurfaceChangesTransaction();
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.wtf("WindowManager", "Unhandled exception in Window Manager", e);
            }
            if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
                handleResizingWindows();
                this.mWmService.mAtmService.getLifecycleManager().dispatchPendingTransactions();
            }
            this.mWmService.mAtmService.mTaskOrganizerController.dispatchPendingEvents();
            this.mWmService.mAtmService.mTaskFragmentOrganizerController.dispatchPendingEvents();
            this.mWmService.mSyncEngine.onSurfacePlacement();
            checkAppTransitionReady(windowSurfacePlacer);
            com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
            if (recentsAnimationController != null) {
                recentsAnimationController.checkAnimationReady(defaultDisplayContentLocked.mWallpaperController);
            }
            this.mWmService.mAtmService.mBackNavigationController.checkAnimationReady(defaultDisplayContentLocked.mWallpaperController);
            for (int i = 0; i < this.mChildren.size(); i++) {
                com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(i);
                if (displayContent.mWallpaperMayChange) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -4150611780753674023L, 0, null, null);
                    displayContent.pendingLayoutChanges |= 4;
                }
            }
            if (this.mWmService.mFocusMayChange) {
                this.mWmService.mFocusMayChange = false;
                this.mWmService.updateFocusedWindowLocked(2, false);
            }
            if (isLayoutNeeded()) {
                defaultDisplayContentLocked.pendingLayoutChanges |= 1;
            }
            if (!com.android.window.flags.Flags.bundleClientTransactionFlag()) {
                handleResizingWindows();
            }
            clearFrameChangingWindows();
            if (this.mWmService.mDisplayFrozen) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 4177291132772627699L, 3, null, java.lang.Boolean.valueOf(this.mOrientationChangeComplete));
            }
            if (this.mOrientationChangeComplete) {
                if (this.mWmService.mWindowsFreezingScreen != 0) {
                    this.mWmService.mWindowsFreezingScreen = 0;
                    this.mWmService.mLastFinishedFreezeSource = this.mLastWindowFreezeSource;
                    this.mWmService.mH.removeMessages(11);
                }
                this.mWmService.stopFreezingDisplayLocked();
            }
            int size = this.mWmService.mDestroySurface.size();
            if (size > 0) {
                do {
                    size--;
                    com.android.server.wm.WindowState windowState = this.mWmService.mDestroySurface.get(size);
                    windowState.mDestroying = false;
                    com.android.server.wm.DisplayContent displayContent2 = windowState.getDisplayContent();
                    if (displayContent2.mInputMethodWindow == windowState) {
                        displayContent2.setInputMethodWindowLocked(null);
                    }
                    if (displayContent2.mWallpaperController.isWallpaperTarget(windowState)) {
                        displayContent2.pendingLayoutChanges |= 4;
                    }
                    windowState.destroySurfaceUnchecked();
                } while (size > 0);
                this.mWmService.mDestroySurface.clear();
            }
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                com.android.server.wm.DisplayContent displayContent3 = (com.android.server.wm.DisplayContent) this.mChildren.get(i2);
                if (displayContent3.pendingLayoutChanges != 0) {
                    displayContent3.setLayoutNeeded();
                }
            }
            if (!this.mWmService.mDisplayFrozen) {
                if (this.mButtonBrightnessOverride < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mButtonBrightnessOverride > 1.0f) {
                    f = Float.NaN;
                } else {
                    f = this.mButtonBrightnessOverride;
                }
                if (this.mScreenBrightnessOverride >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && this.mScreenBrightnessOverride <= 1.0f) {
                    f2 = this.mScreenBrightnessOverride;
                }
                int floatToIntBits = java.lang.Float.floatToIntBits(f);
                int floatToIntBits2 = java.lang.Float.floatToIntBits(f2);
                this.mHandler.obtainMessage(0, floatToIntBits, 0).sendToTarget();
                this.mHandler.obtainMessage(1, floatToIntBits2, 0).sendToTarget();
                this.mHandler.obtainMessage(2, java.lang.Long.valueOf(this.mUserActivityTimeout)).sendToTarget();
            }
            if (this.mSustainedPerformanceModeCurrent != this.mSustainedPerformanceModeEnabled) {
                this.mSustainedPerformanceModeEnabled = this.mSustainedPerformanceModeCurrent;
                this.mWmService.mPowerManagerInternal.setPowerMode(2, this.mSustainedPerformanceModeEnabled);
            }
            if (this.mUpdateRotation) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -5513616928833586179L, 0, null, null);
                this.mUpdateRotation = updateRotationUnchecked();
            }
            if (!this.mWmService.mWaitingForDrawnCallbacks.isEmpty() || (this.mOrientationChangeComplete && !isLayoutNeeded() && !this.mUpdateRotation)) {
                this.mWmService.checkDrawnWindowsLocked();
            }
            forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda37
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RootWindowContainer.lambda$performSurfacePlacementNoTrace$7((com.android.server.wm.DisplayContent) obj);
                }
            });
            this.mWmService.enableScreenIfNeededLocked();
            this.mWmService.scheduleAnimationLocked();
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$performSurfacePlacementNoTrace$7(com.android.server.wm.DisplayContent displayContent) {
        displayContent.getInputMonitor().updateInputWindowsLw(true);
        displayContent.updateSystemGestureExclusion();
        displayContent.updateKeepClearAreas();
        displayContent.updateTouchExcludeRegion();
    }

    private void checkAppTransitionReady(com.android.server.wm.WindowSurfacePlacer windowSurfacePlacer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(size);
            if (displayContent.mAppTransition.isReady()) {
                displayContent.mAppTransitionController.handleAppTransitionReady();
            }
            if (displayContent.mAppTransition.isRunning() && !displayContent.isAppTransitioning()) {
                displayContent.handleAnimatingStoppedAndTransition();
            }
        }
    }

    private void applySurfaceChangesTransaction() {
        com.android.server.wm.DisplayContent displayContent = this.mDefaultDisplay;
        android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i = displayInfo.logicalWidth;
        int i2 = displayInfo.logicalHeight;
        android.view.SurfaceControl.Transaction syncTransaction = displayContent.getSyncTransaction();
        if (this.mWmService.mWatermark != null) {
            this.mWmService.mWatermark.positionSurface(i, i2, syncTransaction);
        }
        if (this.mWmService.mStrictModeFlash != null) {
            this.mWmService.mStrictModeFlash.positionSurface(i, i2, syncTransaction);
        }
        if (this.mWmService.mEmulatorDisplayOverlay != null) {
            this.mWmService.mEmulatorDisplayOverlay.positionSurface(i, i2, displayContent.getRotation(), syncTransaction);
        }
        int size = this.mChildren.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.wm.DisplayContent displayContent2 = (com.android.server.wm.DisplayContent) this.mChildren.get(i3);
            displayContent2.applySurfaceChangesTransaction();
            this.mDisplayTransactions.append(displayContent2.mDisplayId, displayContent2.getSyncTransaction());
        }
        this.mWmService.mDisplayManagerInternal.performTraversal(syncTransaction, this.mDisplayTransactions);
        this.mDisplayTransactions.clear();
    }

    private void handleResizingWindows() {
        for (int size = this.mWmService.mResizingWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = this.mWmService.mResizingWindows.get(size);
            if (!windowState.mAppFreezing && !windowState.getDisplayContent().mWaitingForConfig) {
                windowState.reportResized();
                this.mWmService.mResizingWindows.remove(size);
            }
        }
    }

    private void clearFrameChangingWindows() {
        java.util.ArrayList<com.android.server.wm.WindowState> arrayList = this.mWmService.mFrameChangingWindows;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).updateLastFrames();
        }
        arrayList.clear();
    }

    boolean handleNotObscuredLocked(com.android.server.wm.WindowState windowState, boolean z, boolean z2) {
        boolean isOnScreen = windowState.isOnScreen();
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, -7698723716637247994L, 508, null, java.lang.String.valueOf(windowState), java.lang.Boolean.valueOf(windowState.mHasSurface), java.lang.Boolean.valueOf(isOnScreen), java.lang.Boolean.valueOf(windowState.isDisplayed()), java.lang.Long.valueOf(windowState.mAttrs.userActivityTimeout));
        boolean z3 = false;
        if (!isOnScreen) {
            return false;
        }
        if (!z2 && windowState.mAttrs.userActivityTimeout >= 0 && this.mUserActivityTimeout < 0) {
            this.mUserActivityTimeout = windowState.mAttrs.userActivityTimeout;
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 8621291657500572364L, 1, null, java.lang.Long.valueOf(this.mUserActivityTimeout));
        }
        if (windowState.isDrawn() || (windowState.mActivityRecord != null && windowState.mActivityRecord.firstWindowDrawn && windowState.mActivityRecord.isVisibleRequested())) {
            if (!z2 && windowState.mAttrs.buttonBrightness >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && java.lang.Float.isNaN(this.mButtonBrightnessOverride)) {
                this.mButtonBrightnessOverride = windowState.mAttrs.buttonBrightness;
            }
            if (!z2 && windowState.mAttrs.screenBrightness >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && java.lang.Float.isNaN(this.mScreenBrightnessOverride)) {
                this.mScreenBrightnessOverride = windowState.mAttrs.screenBrightness;
            }
            com.android.server.wm.DisplayContent displayContent = windowState.getDisplayContent();
            if (displayContent != null && displayContent.isDefaultDisplay) {
                if (windowState.isDreamWindow() || this.mWmService.mPolicy.isKeyguardShowing()) {
                    this.mObscureApplicationContentOnSecondaryDisplays = true;
                }
                z3 = true;
            } else if (displayContent != null && (!this.mObscureApplicationContentOnSecondaryDisplays || displayContent.isKeyguardAlwaysUnlocked() || (z && windowState.mAttrs.type == 2009))) {
                z3 = true;
            }
            if ((windowState.mAttrs.privateFlags & 65536) != 0) {
                this.mSustainedPerformanceModeCurrent = true;
            }
        }
        return z3;
    }

    boolean updateRotationUnchecked() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((com.android.server.wm.DisplayContent) this.mChildren.get(size)).getDisplayRotation().updateRotationAndSendNewConfigIfChanged()) {
                z = true;
            }
        }
        return z;
    }

    boolean copyAnimToLayoutParams() {
        boolean z;
        int i = this.mWmService.mAnimator.mBulkUpdateParams;
        if ((i & 1) == 0) {
            z = false;
        } else {
            this.mUpdateRotation = true;
            z = true;
        }
        if (this.mOrientationChangeComplete) {
            this.mLastWindowFreezeSource = this.mWmService.mAnimator.mLastWindowFreezeSource;
            if (this.mWmService.mWindowsFreezingScreen != 0) {
                z = true;
            }
        }
        if ((i & 2) != 0) {
            this.mWallpaperActionPending = true;
        }
        return z;
    }

    private final class MyHandler extends android.os.Handler {
        public MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.wm.RootWindowContainer.this.mWmService.mPowerManagerInternal.setButtonBrightnessOverrideFromWindowManager(java.lang.Float.intBitsToFloat(message.arg1));
                    return;
                case 1:
                    com.android.server.wm.RootWindowContainer.this.mWmService.mPowerManagerInternal.setScreenBrightnessOverrideFromWindowManager(java.lang.Float.intBitsToFloat(message.arg1));
                    return;
                case 2:
                    com.android.server.wm.RootWindowContainer.this.mWmService.mPowerManagerInternal.setUserActivityTimeoutOverrideFromWindowManager(((java.lang.Long) message.obj).longValue());
                    return;
                case 3:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RootWindowContainer.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.RootWindowContainer.this.sendSleepTransition((com.android.server.wm.DisplayContent) message.obj);
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

    void dumpDisplayContents(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER DISPLAY CONTENTS (dumpsys window displays)");
        if (this.mWmService.mDisplayReady) {
            int size = this.mChildren.size();
            for (int i = 0; i < size; i++) {
                ((com.android.server.wm.DisplayContent) this.mChildren.get(i)).dump(printWriter, "  ", true);
            }
            return;
        }
        printWriter.println("  NO DISPLAY");
    }

    void dumpTopFocusedDisplayId(java.io.PrintWriter printWriter) {
        printWriter.print("  mTopFocusedDisplayId=");
        printWriter.println(this.mTopFocusedDisplayId);
    }

    void dumpLayoutNeededDisplayIds(java.io.PrintWriter printWriter) {
        if (!isLayoutNeeded()) {
            return;
        }
        printWriter.print("  mLayoutNeeded on displays=");
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(i);
            if (displayContent.isLayoutNeeded()) {
                printWriter.print(displayContent.getDisplayId());
            }
        }
        printWriter.println();
    }

    void dumpWindowsNoHeader(final java.io.PrintWriter printWriter, final boolean z, final java.util.ArrayList<com.android.server.wm.WindowState> arrayList) {
        final int[] iArr = new int[1];
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$dumpWindowsNoHeader$8(arrayList, printWriter, iArr, z, (com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWindowsNoHeader$8(java.util.ArrayList arrayList, java.io.PrintWriter printWriter, int[] iArr, boolean z, com.android.server.wm.WindowState windowState) {
        if (arrayList == null || arrayList.contains(windowState)) {
            printWriter.println("  Window #" + iArr[0] + " " + windowState + ":");
            windowState.dump(printWriter, "    ", z || arrayList != null);
            iArr[0] = iArr[0] + 1;
        }
    }

    void dumpTokens(java.io.PrintWriter printWriter, boolean z) {
        printWriter.println("  All tokens:");
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.DisplayContent) this.mChildren.get(size)).dumpTokens(printWriter, z);
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        this.mTaskSupervisor.getKeyguardController().dumpDebug(protoOutputStream, 1146756268037L);
        protoOutputStream.write(1133871366150L, this.mTaskSupervisor.mRecentTasks.isRecentsComponentHomeActivity(this.mCurrentUser));
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return "ROOT";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.WindowContainer
    public void removeChild(com.android.server.wm.DisplayContent displayContent) {
        super.removeChild((com.android.server.wm.RootWindowContainer) displayContent);
        if (this.mTopFocusedDisplayId == displayContent.getDisplayId()) {
            this.mWmService.updateFocusedWindowLocked(0, true);
        }
    }

    void forAllDisplays(java.util.function.Consumer<com.android.server.wm.DisplayContent> consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept((com.android.server.wm.DisplayContent) this.mChildren.get(size));
        }
    }

    void forAllDisplayPolicies(java.util.function.Consumer<com.android.server.wm.DisplayPolicy> consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            consumer.accept(((com.android.server.wm.DisplayContent) this.mChildren.get(size)).getDisplayPolicy());
        }
    }

    com.android.server.wm.WindowState getCurrentInputMethodWindow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(size);
            if (displayContent.mInputMethodWindow != null) {
                return displayContent.mInputMethodWindow;
            }
        }
        return null;
    }

    void getDisplayContextsWithNonToastVisibleWindows(final int i, java.util.List<android.content.Context> list) {
        if (list == null) {
            return;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mChildren.get(size);
            if (displayContent.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda23
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getDisplayContextsWithNonToastVisibleWindows$9;
                    lambda$getDisplayContextsWithNonToastVisibleWindows$9 = com.android.server.wm.RootWindowContainer.lambda$getDisplayContextsWithNonToastVisibleWindows$9(i, (com.android.server.wm.WindowState) obj);
                    return lambda$getDisplayContextsWithNonToastVisibleWindows$9;
                }
            }) != null) {
                list.add(displayContent.getDisplayUiContext());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getDisplayContextsWithNonToastVisibleWindows$9(int i, com.android.server.wm.WindowState windowState) {
        return i == windowState.mSession.mPid && windowState.isVisibleNow() && windowState.mAttrs.type != 2005;
    }

    @android.annotation.Nullable
    android.content.Context getDisplayUiContext(int i) {
        if (getDisplayContent(i) != null) {
            return getDisplayContent(i).getDisplayUiContext();
        }
        return null;
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        this.mDisplayManager = (android.hardware.display.DisplayManager) this.mService.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        this.mDisplayManager.registerDisplayListener(this, this.mService.mUiHandler);
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        for (android.view.Display display : this.mDisplayManager.getDisplays()) {
            com.android.server.wm.DisplayContent displayContent = new com.android.server.wm.DisplayContent(display, this, this.mDeviceStateController);
            addChild((com.android.server.wm.RootWindowContainer) displayContent, Integer.MIN_VALUE);
            if (displayContent.mDisplayId == 0) {
                this.mDefaultDisplay = displayContent;
            }
        }
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = getDefaultTaskDisplayArea();
        defaultTaskDisplayArea.getOrCreateRootHomeTask(true);
        positionChildAt(Integer.MAX_VALUE, defaultTaskDisplayArea.mDisplayContent, false);
    }

    void onDisplayManagerReceivedDeviceState(int i) {
        this.mDeviceStateController.onDeviceStateReceivedByDisplayManager(i);
    }

    com.android.server.wm.DisplayContent getDefaultDisplay() {
        return this.mDefaultDisplay;
    }

    @android.annotation.NonNull
    com.android.server.wm.DisplayRotationCoordinator getDisplayRotationCoordinator() {
        return this.mDisplayRotationCoordinator;
    }

    com.android.server.wm.TaskDisplayArea getDefaultTaskDisplayArea() {
        return this.mDefaultDisplay.getDefaultTaskDisplayArea();
    }

    com.android.server.wm.DisplayContent getDisplayContent(java.lang.String str) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (childAt.mDisplay.isValid() && childAt.mDisplay.getUniqueId().equals(str)) {
                return childAt;
            }
        }
        return null;
    }

    com.android.server.wm.DisplayContent getDisplayContent(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (childAt.mDisplayId == i) {
                return childAt;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.DisplayContent getDisplayContentOrCreate(int i) {
        android.view.Display display;
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
        if (displayContent != null) {
            return displayContent;
        }
        if (this.mDisplayManager == null || (display = this.mDisplayManager.getDisplay(i)) == null) {
            return null;
        }
        com.android.server.wm.DisplayContent displayContent2 = new com.android.server.wm.DisplayContent(display, this, this.mDeviceStateController);
        addChild((com.android.server.wm.RootWindowContainer) displayContent2, Integer.MIN_VALUE);
        return displayContent2;
    }

    com.android.server.wm.ActivityRecord getDefaultDisplayHomeActivityForUser(int i) {
        return getDefaultTaskDisplayArea().getHomeActivityForUser(i);
    }

    boolean startHomeOnAllDisplays(int i, java.lang.String str) {
        boolean z = false;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            z |= startHomeOnDisplay(i, str, getChildAt(childCount).mDisplayId);
        }
        return z;
    }

    void startHomeOnEmptyDisplays(final java.lang.String str) {
        forAllTaskDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$startHomeOnEmptyDisplays$10(str, (com.android.server.wm.TaskDisplayArea) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startHomeOnEmptyDisplays$10(java.lang.String str, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.topRunningActivity() == null) {
            startHomeOnTaskDisplayArea(this.mWmService.getUserAssignedToDisplay(taskDisplayArea.getDisplayId()), str, taskDisplayArea, false, false);
        }
    }

    boolean startHomeOnDisplay(int i, java.lang.String str, int i2) {
        return startHomeOnDisplay(i, str, i2, false, false);
    }

    boolean startHomeOnDisplay(final int i, final java.lang.String str, int i2, final boolean z, final boolean z2) {
        if (i2 == -1) {
            com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            i2 = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDisplayId() : 0;
        }
        return ((java.lang.Boolean) getDisplayContent(i2).reduceOnAllTaskDisplayAreas(new java.util.function.BiFunction() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda48
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                java.lang.Boolean lambda$startHomeOnDisplay$11;
                lambda$startHomeOnDisplay$11 = com.android.server.wm.RootWindowContainer.this.lambda$startHomeOnDisplay$11(i, str, z, z2, (com.android.server.wm.TaskDisplayArea) obj, (java.lang.Boolean) obj2);
                return lambda$startHomeOnDisplay$11;
            }
        }, false)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$startHomeOnDisplay$11(int i, java.lang.String str, boolean z, boolean z2, com.android.server.wm.TaskDisplayArea taskDisplayArea, java.lang.Boolean bool) {
        return java.lang.Boolean.valueOf(startHomeOnTaskDisplayArea(i, str, taskDisplayArea, z, z2) | bool.booleanValue());
    }

    boolean startHomeOnTaskDisplayArea(int i, java.lang.String str, com.android.server.wm.TaskDisplayArea taskDisplayArea, boolean z, boolean z2) {
        android.content.Intent homeIntent;
        android.content.pm.ActivityInfo resolveHomeActivity;
        if (taskDisplayArea == null) {
            com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            taskDisplayArea = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getDisplayArea() : getDefaultTaskDisplayArea();
        }
        if (taskDisplayArea == getDefaultTaskDisplayArea() || this.mWmService.shouldPlacePrimaryHomeOnDisplay(taskDisplayArea.getDisplayId(), i)) {
            homeIntent = this.mService.getHomeIntent();
            resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        } else if (!shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
            resolveHomeActivity = null;
            homeIntent = null;
        } else {
            android.util.Pair<android.content.pm.ActivityInfo, android.content.Intent> resolveSecondaryHomeActivity = resolveSecondaryHomeActivity(i, taskDisplayArea);
            resolveHomeActivity = (android.content.pm.ActivityInfo) resolveSecondaryHomeActivity.first;
            homeIntent = (android.content.Intent) resolveSecondaryHomeActivity.second;
        }
        if (resolveHomeActivity == null || homeIntent == null || !canStartHomeOnDisplayArea(resolveHomeActivity, taskDisplayArea, z)) {
            return false;
        }
        com.android.systemui.shared.Flags.enableHomeDelay();
        homeIntent.setComponent(new android.content.ComponentName(resolveHomeActivity.applicationInfo.packageName, resolveHomeActivity.name));
        homeIntent.setFlags(homeIntent.getFlags() | 268435456);
        if (z2) {
            homeIntent.putExtra("android.intent.extra.FROM_HOME_KEY", true);
            if (this.mWindowManager.getRecentsAnimationController() != null) {
                this.mWindowManager.getRecentsAnimationController().cancelAnimationForHomeStart();
            }
        }
        homeIntent.putExtra("android.intent.extra.EXTRA_START_REASON", str);
        this.mService.getActivityStartController().startHomeActivity(homeIntent, resolveHomeActivity, str + ":" + i + ":" + android.os.UserHandle.getUserId(resolveHomeActivity.applicationInfo.uid) + ":" + taskDisplayArea.getDisplayId(), taskDisplayArea);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.pm.ActivityInfo resolveHomeActivity(int i, android.content.Intent intent) {
        android.content.pm.ActivityInfo activityInfo;
        android.content.ComponentName component = intent.getComponent();
        try {
            if (component != null) {
                activityInfo = android.app.AppGlobals.getPackageManager().getActivityInfo(component, 1024L, i);
            } else {
                android.content.pm.ResolveInfo resolveIntent = this.mTaskSupervisor.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver()), i, 1024, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
                if (resolveIntent == null) {
                    activityInfo = null;
                } else {
                    activityInfo = resolveIntent.activityInfo;
                }
            }
        } catch (android.os.RemoteException e) {
            activityInfo = null;
        }
        if (activityInfo == null) {
            com.android.server.utils.Slogf.wtf("WindowManager", new java.lang.Exception(), "No home screen found for %s and user %d", intent, java.lang.Integer.valueOf(i));
            return null;
        }
        android.content.pm.ActivityInfo activityInfo2 = new android.content.pm.ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = this.mService.getAppInfoForUser(activityInfo2.applicationInfo, i);
        return activityInfo2;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.Pair<android.content.pm.ActivityInfo, android.content.Intent> resolveSecondaryHomeActivity(int i, @android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        android.content.ComponentName componentName;
        if (taskDisplayArea == getDefaultTaskDisplayArea()) {
            throw new java.lang.IllegalArgumentException("resolveSecondaryHomeActivity: Should not be default task container");
        }
        android.content.Intent homeIntent = this.mService.getHomeIntent();
        android.content.pm.ActivityInfo resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        boolean z = resolveHomeActivity != null;
        if (android.companion.virtual.flags.Flags.vdmCustomHome()) {
            if (taskDisplayArea.getDisplayContent() != null) {
                componentName = taskDisplayArea.getDisplayContent().getCustomHomeComponent();
            } else {
                componentName = null;
            }
            if (componentName != null) {
                homeIntent.setComponent(componentName);
                android.content.pm.ActivityInfo resolveHomeActivity2 = resolveHomeActivity(i, homeIntent);
                if (resolveHomeActivity2 != null) {
                    z = false;
                    resolveHomeActivity = resolveHomeActivity2;
                }
            }
        }
        if (z) {
            if (com.android.internal.app.ResolverActivity.class.getName().equals(resolveHomeActivity.name)) {
                resolveHomeActivity = null;
            } else {
                homeIntent = this.mService.getSecondaryHomeIntent(resolveHomeActivity.applicationInfo.packageName);
                java.util.List<android.content.pm.ResolveInfo> resolveActivities = resolveActivities(i, homeIntent);
                int size = resolveActivities.size();
                java.lang.String str = resolveHomeActivity.name;
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        resolveHomeActivity = null;
                        break;
                    }
                    android.content.pm.ResolveInfo resolveInfo = resolveActivities.get(i2);
                    if (!resolveInfo.activityInfo.name.equals(str)) {
                        i2++;
                    } else {
                        resolveHomeActivity = resolveInfo.activityInfo;
                        break;
                    }
                }
                if (resolveHomeActivity == null && size > 0) {
                    resolveHomeActivity = resolveActivities.get(0).activityInfo;
                }
            }
        }
        if (resolveHomeActivity != null && !canStartHomeOnDisplayArea(resolveHomeActivity, taskDisplayArea, false)) {
            resolveHomeActivity = null;
        }
        if (resolveHomeActivity == null) {
            homeIntent = this.mService.getSecondaryHomeIntent(null);
            resolveHomeActivity = resolveHomeActivity(i, homeIntent);
        }
        return android.util.Pair.create(resolveHomeActivity, homeIntent);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.content.pm.ResolveInfo> resolveActivities(int i, android.content.Intent intent) {
        try {
            return android.app.AppGlobals.getPackageManager().queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mService.mContext.getContentResolver()), 1024L, i).getList();
        } catch (android.os.RemoteException e) {
            return new java.util.ArrayList();
        }
    }

    boolean resumeHomeActivity(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea2;
        if (!this.mService.isBooting() && !this.mService.isBooted()) {
            return false;
        }
        if (taskDisplayArea != null) {
            taskDisplayArea2 = taskDisplayArea;
        } else {
            taskDisplayArea2 = getDefaultTaskDisplayArea();
        }
        com.android.server.wm.ActivityRecord homeActivity = taskDisplayArea2.getHomeActivity();
        java.lang.String str2 = str + " resumeHomeActivity";
        if (homeActivity != null && !homeActivity.finishing) {
            homeActivity.moveFocusableActivityToTop(str2);
            return resumeFocusedTasksTopActivities(homeActivity.getRootTask(), activityRecord, null);
        }
        return startHomeOnTaskDisplayArea(this.mWmService.getUserAssignedToDisplay(taskDisplayArea2.getDisplayId()), str2, taskDisplayArea2, false, false);
    }

    boolean shouldPlacePrimaryHomeOnDisplay(int i) {
        return i == 0 || (i != -1 && (i == this.mService.mVr2dDisplayId || this.mWmService.shouldPlacePrimaryHomeOnDisplay(i)));
    }

    boolean shouldPlaceSecondaryHomeOnDisplayArea(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.DisplayContent displayContent;
        if (getDefaultTaskDisplayArea() == taskDisplayArea) {
            throw new java.lang.IllegalArgumentException("shouldPlaceSecondaryHomeOnDisplay: Should not be on default task container");
        }
        if (taskDisplayArea == null || !taskDisplayArea.canHostHomeTask()) {
            return false;
        }
        if (taskDisplayArea.getDisplayId() == 0 || this.mService.mSupportsMultiDisplay) {
            return (android.provider.Settings.Global.getInt(this.mService.mContext.getContentResolver(), "device_provisioned", 0) != 0) && android.os.storage.StorageManager.isCeStorageUnlocked(this.mCurrentUser) && (displayContent = taskDisplayArea.getDisplayContent()) != null && !displayContent.isRemoved() && displayContent.isHomeSupported();
        }
        return false;
    }

    boolean canStartHomeOnDisplayArea(android.content.pm.ActivityInfo activityInfo, com.android.server.wm.TaskDisplayArea taskDisplayArea, boolean z) {
        if (this.mService.mFactoryTest == 1 && this.mService.mTopAction == null) {
            return false;
        }
        com.android.server.wm.WindowProcessController processController = this.mService.getProcessController(activityInfo.processName, activityInfo.applicationInfo.uid);
        if (!z && processController != null && processController.isInstrumenting()) {
            return false;
        }
        if (shouldPlacePrimaryHomeOnDisplay(taskDisplayArea != null ? taskDisplayArea.getDisplayId() : -1)) {
            return true;
        }
        if (shouldPlaceSecondaryHomeOnDisplayArea(taskDisplayArea)) {
            return activityInfo.launchMode != 2 && activityInfo.launchMode != 3;
        }
        return false;
    }

    boolean ensureVisibilityAndConfig(com.android.server.wm.ActivityRecord activityRecord, int i, boolean z) {
        ensureActivitiesVisible(null, false);
        if (i == -1) {
            return true;
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
        android.content.res.Configuration updateOrientation = displayContent != null ? displayContent.updateOrientation(activityRecord, true) : null;
        if (activityRecord != null) {
            activityRecord.reportDescendantOrientationChangeIfNeeded();
        }
        if (displayContent != null) {
            return displayContent.updateDisplayOverrideConfigurationLocked(updateOrientation, activityRecord, z);
        }
        return true;
    }

    java.util.List<com.android.server.wm.ActivityAssistInfo> getTopVisibleActivities() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        final com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$getTopVisibleActivities$12(arrayList2, topDisplayFocusedRootTask, arrayList, (com.android.server.wm.Task) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getTopVisibleActivities$12(java.util.ArrayList arrayList, com.android.server.wm.Task task, java.util.ArrayList arrayList2, com.android.server.wm.Task task2) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        com.android.server.wm.ActivityRecord topNonFinishingActivity2;
        if (task2.shouldBeVisible(null) && (topNonFinishingActivity = task2.getTopNonFinishingActivity()) != null) {
            arrayList.clear();
            arrayList.add(new com.android.server.wm.ActivityAssistInfo(topNonFinishingActivity));
            com.android.server.wm.Task adjacentTask = topNonFinishingActivity.getTask().getAdjacentTask();
            if (adjacentTask != null && (topNonFinishingActivity2 = adjacentTask.getTopNonFinishingActivity()) != null) {
                arrayList.add(new com.android.server.wm.ActivityAssistInfo(topNonFinishingActivity2));
            }
            if (task2 == task) {
                arrayList2.addAll(0, arrayList);
            } else {
                arrayList2.addAll(arrayList);
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getTopDisplayFocusedRootTask() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.Task focusedRootTask = getChildAt(childCount).getFocusedRootTask();
            if (focusedRootTask != null) {
                return focusedRootTask;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getTopResumedActivity() {
        com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null) {
            return null;
        }
        com.android.server.wm.ActivityRecord topResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
        if (topResumedActivity != null && topResumedActivity.app != null) {
            return topResumedActivity;
        }
        return (com.android.server.wm.ActivityRecord) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.server.wm.TaskDisplayArea) obj).getFocusedActivity();
            }
        });
    }

    boolean isTopDisplayFocusedRootTask(com.android.server.wm.Task task) {
        return task != null && task == getTopDisplayFocusedRootTask();
    }

    boolean attachApplication(com.android.server.wm.WindowProcessController windowProcessController) throws android.os.RemoteException {
        try {
            return this.mAttachApplicationHelper.process(windowProcessController);
        } finally {
            this.mAttachApplicationHelper.reset();
        }
    }

    void ensureActivitiesVisible() {
        ensureActivitiesVisible(null);
    }

    void ensureActivitiesVisible(com.android.server.wm.ActivityRecord activityRecord) {
        ensureActivitiesVisible(activityRecord, true);
    }

    void ensureActivitiesVisible(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        if (this.mTaskSupervisor.inActivityVisibilityUpdate() || this.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
            return;
        }
        this.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                getChildAt(childCount).ensureActivitiesVisible(activityRecord, z);
            }
        } finally {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    boolean switchUser(final int i, com.android.server.am.UserState userState) {
        com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        int rootTaskId = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getRootTaskId() : -1;
        removeRootTasksInWindowingModes(2);
        this.mUserRootTaskInFront.put(this.mCurrentUser, rootTaskId);
        this.mCurrentUser = i;
        this.mTaskSupervisor.mStartingUsers.add(userState);
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.Task) obj).switchUser(i);
            }
        });
        if (topDisplayFocusedRootTask != null && isAlwaysVisibleUser(topDisplayFocusedRootTask.mUserId)) {
            android.util.Slog.i("WindowManager", "Persisting top task because it belongs to an always-visible user");
            this.mUserRootTaskInFront.put(this.mCurrentUser, rootTaskId);
        }
        com.android.server.wm.Task rootTask = getRootTask(this.mUserRootTaskInFront.get(i));
        if (rootTask == null) {
            rootTask = getDefaultTaskDisplayArea().getOrCreateRootHomeTask();
        }
        boolean isActivityTypeHome = rootTask.isActivityTypeHome();
        if (rootTask.isOnHomeDisplay()) {
            rootTask.moveToFront("switchUserOnHomeDisplay");
        } else {
            resumeHomeActivity(null, "switchUserOnOtherDisplay", getDefaultTaskDisplayArea());
        }
        return isActivityTypeHome;
    }

    private boolean isAlwaysVisibleUser(int i) {
        android.content.pm.UserProperties userProperties = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserProperties(i);
        return userProperties != null && userProperties.getAlwaysVisible();
    }

    void removeUser(int i) {
        this.mUserRootTaskInFront.delete(i);
    }

    void updateUserRootTask(int i, com.android.server.wm.Task task) {
        if (i != this.mCurrentUser) {
            if (task == null) {
                task = getDefaultTaskDisplayArea().getOrCreateRootHomeTask();
            }
            this.mUserRootTaskInFront.put(i, task.getRootTaskId());
        }
    }

    void moveRootTaskToTaskDisplayArea(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea, boolean z) {
        com.android.server.wm.Task rootTask = getRootTask(i);
        if (rootTask == null) {
            throw new java.lang.IllegalArgumentException("moveRootTaskToTaskDisplayArea: Unknown rootTaskId=" + i);
        }
        com.android.server.wm.TaskDisplayArea displayArea = rootTask.getDisplayArea();
        if (displayArea == null) {
            throw new java.lang.IllegalStateException("moveRootTaskToTaskDisplayArea: rootTask=" + rootTask + " is not attached to any task display area.");
        }
        if (taskDisplayArea == null) {
            throw new java.lang.IllegalArgumentException("moveRootTaskToTaskDisplayArea: Unknown taskDisplayArea=" + taskDisplayArea);
        }
        if (displayArea == taskDisplayArea) {
            throw new java.lang.IllegalArgumentException("Trying to move rootTask=" + rootTask + " to its current taskDisplayArea=" + taskDisplayArea);
        }
        rootTask.reparent(taskDisplayArea, z);
        rootTask.resumeNextFocusAfterReparent();
    }

    void moveRootTaskToDisplay(int i, int i2, boolean z) {
        com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2);
        if (displayContentOrCreate == null) {
            throw new java.lang.IllegalArgumentException("moveRootTaskToDisplay: Unknown displayId=" + i2);
        }
        moveRootTaskToTaskDisplayArea(i, displayContentOrCreate.getDefaultTaskDisplayArea(), z);
    }

    void moveActivityToPinnedRootTask(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, java.lang.String str) {
        moveActivityToPinnedRootTask(activityRecord, activityRecord2, str, null);
    }

    void moveActivityToPinnedRootTask(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, java.lang.String str, @android.annotation.Nullable com.android.server.wm.Transition transition) {
        moveActivityToPinnedRootTask(activityRecord, activityRecord2, str, transition, null);
    }

    void moveActivityToPinnedRootTask(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, java.lang.String str, @android.annotation.Nullable com.android.server.wm.Transition transition, @android.annotation.Nullable android.graphics.Rect rect) {
        boolean z;
        com.android.server.wm.Task build;
        com.android.server.wm.TaskDisplayArea displayArea = activityRecord.getDisplayArea();
        com.android.server.wm.Task task = activityRecord.getTask();
        com.android.server.wm.TransitionController transitionController = task.mTransitionController;
        com.android.server.wm.Transition createTransition = (transition != null || transitionController.isCollecting() || transitionController.getTransitionPlayer() == null) ? transition : transitionController.createTransition(10);
        transitionController.deferTransitionReady();
        transitionController.waitFor(new com.android.server.wm.Transition.ReadyCondition("movedToPip"));
        this.mService.deferWindowLayout();
        if (this.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
            z = false;
        } else {
            this.mTaskSupervisor.setDeferRootVisibilityUpdate(true);
            z = true;
        }
        try {
            com.android.server.wm.Task rootPinnedTask = displayArea.getRootPinnedTask();
            if (rootPinnedTask != null) {
                transitionController.collect(rootPinnedTask);
                removeRootTasksInWindowingModes(2);
            }
            activityRecord.getDisplayContent().prepareAppTransition(0);
            transitionController.collect(task);
            if (!com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                activityRecord.setWindowingMode(activityRecord.getWindowingMode());
            }
            com.android.server.wm.TaskFragment organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
            com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (task.getNonFinishingActivityCount() == 1) {
                task.maybeApplyLastRecentsAnimationTransaction();
                if (task.getParent() != displayArea) {
                    task.reparent(displayArea, true);
                }
                task.forAllTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda44
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.RootWindowContainer.lambda$moveActivityToPinnedRootTask$14((com.android.server.wm.TaskFragment) obj);
                    }
                });
                build = task;
            } else {
                build = new com.android.server.wm.Task.Builder(this.mService).setActivityType(activityRecord.getActivityType()).setOnTop(true).setActivityInfo(activityRecord.info).setParent(displayArea).setIntent(activityRecord.intent).setDeferTaskAppear(true).setHasBeenVisible(true).setWindowingMode(taskFragment.getWindowingMode()).build();
                activityRecord.setLastParentBeforePip(activityRecord2);
                build.setLastNonFullscreenBounds(task.mLastNonFullscreenBounds);
                build.setBoundsUnchecked(taskFragment.getBounds());
                if (task.mLastRecentsAnimationTransaction != null) {
                    build.setLastRecentsAnimationTransaction(task.mLastRecentsAnimationTransaction, task.mLastRecentsAnimationOverlay);
                    task.clearLastRecentsAnimationTransaction(false);
                } else {
                    task.resetSurfaceControlTransforms();
                }
                if (organizedTaskFragment != null && organizedTaskFragment.getNonFinishingActivityCount() == 1 && organizedTaskFragment.getTopNonFinishingActivity() == activityRecord) {
                    organizedTaskFragment.mClearedTaskFragmentForPip = true;
                }
                if (com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                    transitionController.collectExistenceChange(build);
                } else {
                    transitionController.collect(build);
                }
                if (transitionController.isShellTransitionsEnabled()) {
                    build.setWindowingMode(2);
                }
                build.setFocusable(false);
                activityRecord.reparent(build, Integer.MAX_VALUE, str);
                build.setFocusable(true);
                build.maybeApplyLastRecentsAnimationTransaction();
                com.android.server.wm.ActivityRecord topMostActivity = task.getTopMostActivity();
                if (topMostActivity != null && topMostActivity.isState(com.android.server.wm.ActivityRecord.State.STOPPED) && task.getDisplayContent().mAppTransition.containsTransitRequest(4)) {
                    task.getDisplayContent().mClosingApps.add(topMostActivity);
                    topMostActivity.mRequestForceTransition = true;
                }
            }
            build.setWindowingMode(2);
            if (com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled() && rect != null) {
                build.setBounds(rect);
            }
            if (activityRecord.getOptions() != null && activityRecord.getOptions().isLaunchIntoPip()) {
                this.mWindowManager.mTaskSnapshotController.recordSnapshot(task);
                build.setBounds(activityRecord.pictureInPictureArgs.getSourceRectHint());
            }
            build.setDeferTaskAppear(false);
            if (!com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                activityRecord.mWaitForEnteringPinnedMode = true;
            }
            activityRecord.supportsEnterPipOnTaskSwitch = false;
            if (organizedTaskFragment != null && organizedTaskFragment.mClearedTaskFragmentForPip && organizedTaskFragment.isTaskVisibleRequested()) {
                this.mService.mTaskFragmentOrganizerController.dispatchPendingInfoChangedEvent(organizedTaskFragment);
            }
            this.mService.continueWindowLayout();
            if (z) {
                try {
                    this.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                    ensureActivitiesVisible();
                } finally {
                }
            }
            if (createTransition != null) {
                transitionController.requestStartTransition(createTransition, build, null, null);
                createTransition.setReady(build, true);
            }
            if (!com.android.server.wm.ActivityTaskManagerService.isPip2ExperimentEnabled()) {
                resumeFocusedTasksTopActivities();
            }
            notifyActivityPipModeChanged(activityRecord.getTask(), activityRecord);
        } catch (java.lang.Throwable th) {
            this.mService.continueWindowLayout();
            if (z) {
                try {
                    this.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                    ensureActivitiesVisible();
                } finally {
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$moveActivityToPinnedRootTask$14(com.android.server.wm.TaskFragment taskFragment) {
        if (!taskFragment.isOrganizedTaskFragment()) {
            return;
        }
        taskFragment.resetAdjacentTaskFragment();
        taskFragment.setCompanionTaskFragment(null);
        taskFragment.setAnimationParams(android.window.TaskFragmentAnimationParams.DEFAULT);
        if (taskFragment.getTopNonFinishingActivity() != null) {
            taskFragment.setRelativeEmbeddedBounds(new android.graphics.Rect());
            taskFragment.updateRequestedOverrideConfiguration(android.content.res.Configuration.EMPTY);
        }
    }

    void notifyActivityPipModeChanged(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        boolean z = activityRecord != null;
        if (z) {
            this.mService.getTaskChangeNotificationController().notifyActivityPinned(activityRecord);
        } else {
            this.mService.getTaskChangeNotificationController().notifyActivityUnpinned();
        }
        this.mWindowManager.mPolicy.setPipVisibilityLw(z);
        if (task.getSurfaceControl() != null) {
            this.mWmService.mTransactionFactory.get().setTrustedOverlay(task.getSurfaceControl(), z).apply();
        }
    }

    void executeAppTransitionForAllDisplay() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).mDisplayContent.executeAppTransition();
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord findTask(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return findTask(activityRecord.getActivityType(), activityRecord.taskAffinity, activityRecord.intent, activityRecord.info, taskDisplayArea);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    com.android.server.wm.ActivityRecord findTask(int i, java.lang.String str, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, final com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.ActivityRecord activityRecord2;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -1418592110950138870L, 0, null, java.lang.String.valueOf(i), java.lang.String.valueOf(str), java.lang.String.valueOf(intent), java.lang.String.valueOf(activityInfo), java.lang.String.valueOf(taskDisplayArea));
        this.mTmpFindTaskResult.init(i, str, intent, activityInfo);
        if (taskDisplayArea != null) {
            this.mTmpFindTaskResult.process(taskDisplayArea);
            if (this.mTmpFindTaskResult.mIdealRecord != null) {
                return this.mTmpFindTaskResult.mIdealRecord;
            }
            if (this.mTmpFindTaskResult.mCandidateRecord != null) {
                activityRecord = this.mTmpFindTaskResult.mCandidateRecord;
                activityRecord2 = (com.android.server.wm.ActivityRecord) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda24
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.wm.ActivityRecord lambda$findTask$15;
                        lambda$findTask$15 = com.android.server.wm.RootWindowContainer.this.lambda$findTask$15(taskDisplayArea, (com.android.server.wm.TaskDisplayArea) obj);
                        return lambda$findTask$15;
                    }
                });
                if (activityRecord2 == null) {
                    return activityRecord2;
                }
                if (com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS.isEnabled() && activityRecord == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 2828976699481734755L, 0, null, null);
                }
                return activityRecord;
            }
        }
        activityRecord = null;
        activityRecord2 = (com.android.server.wm.ActivityRecord) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.ActivityRecord lambda$findTask$15;
                lambda$findTask$15 = com.android.server.wm.RootWindowContainer.this.lambda$findTask$15(taskDisplayArea, (com.android.server.wm.TaskDisplayArea) obj);
                return lambda$findTask$15;
            }
        });
        if (activityRecord2 == null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.wm.ActivityRecord lambda$findTask$15(com.android.server.wm.TaskDisplayArea taskDisplayArea, com.android.server.wm.TaskDisplayArea taskDisplayArea2) {
        if (taskDisplayArea2 == taskDisplayArea) {
            return null;
        }
        this.mTmpFindTaskResult.process(taskDisplayArea2);
        if (this.mTmpFindTaskResult.mIdealRecord == null) {
            return null;
        }
        return this.mTmpFindTaskResult.mIdealRecord;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task finishTopCrashedActivities(final com.android.server.wm.WindowProcessController windowProcessController, final java.lang.String str) {
        final com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        final com.android.server.wm.Task[] taskArr = new com.android.server.wm.Task[1];
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$finishTopCrashedActivities$16(taskArr, topDisplayFocusedRootTask, windowProcessController, str, (com.android.server.wm.Task) obj);
            }
        });
        return taskArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$finishTopCrashedActivities$16(com.android.server.wm.Task[] taskArr, com.android.server.wm.Task task, com.android.server.wm.WindowProcessController windowProcessController, java.lang.String str, com.android.server.wm.Task task2) {
        boolean z = taskArr[0] == null && (task == task2 || task2.isVisibleRequested());
        com.android.server.wm.Task finishTopCrashedActivityLocked = task2.finishTopCrashedActivityLocked(windowProcessController, str);
        if (z) {
            taskArr[0] = finishTopCrashedActivityLocked;
        }
    }

    void ensureVisibilityOnVisibleActivityDiedOrCrashed(java.lang.String str) {
        com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask != null && topDisplayFocusedRootTask.topRunningActivity(true) == null) {
            topDisplayFocusedRootTask.adjustFocusToNextFocusableTask(str);
        }
        if (!resumeFocusedTasksTopActivities()) {
            ensureActivitiesVisible();
        }
    }

    boolean resumeFocusedTasksTopActivities() {
        return resumeFocusedTasksTopActivities(null, null, null);
    }

    boolean resumeFocusedTasksTopActivities(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, android.app.ActivityOptions activityOptions) {
        return resumeFocusedTasksTopActivities(task, activityRecord, activityOptions, false);
    }

    boolean resumeFocusedTasksTopActivities(final com.android.server.wm.Task task, final com.android.server.wm.ActivityRecord activityRecord, final android.app.ActivityOptions activityOptions, boolean z) {
        boolean z2;
        if (!this.mTaskSupervisor.readyToResume()) {
            return false;
        }
        if (task != null && (task.isTopRootTaskInDisplayArea() || getTopDisplayFocusedRootTask() == task)) {
            z2 = task.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, z);
        } else {
            z2 = false;
        }
        boolean z3 = z2;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            final boolean[] zArr = new boolean[1];
            final boolean z4 = z3;
            childAt.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda38
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RootWindowContainer.lambda$resumeFocusedTasksTopActivities$17(com.android.server.wm.Task.this, zArr, z4, activityOptions, activityRecord, (com.android.server.wm.Task) obj);
                }
            });
            boolean z5 = zArr[0] | z3;
            if (!zArr[0]) {
                com.android.server.wm.Task focusedRootTask = childAt.getFocusedRootTask();
                if (focusedRootTask != null) {
                    z5 |= focusedRootTask.resumeTopActivityUncheckedLocked(activityRecord, activityOptions);
                } else if (task == null) {
                    z5 |= resumeHomeActivity(null, "no-focusable-task", childAt.getDefaultTaskDisplayArea());
                }
            }
            z3 = z5;
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resumeFocusedTasksTopActivities$17(com.android.server.wm.Task task, boolean[] zArr, boolean z, android.app.ActivityOptions activityOptions, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Task task2) {
        com.android.server.wm.ActivityRecord activityRecord2 = task2.topRunningActivity();
        if (!task2.isFocusableAndVisible() || activityRecord2 == null) {
            return;
        }
        if (task2 == task) {
            zArr[0] = zArr[0] | z;
        } else if (!activityRecord2.isState(com.android.server.wm.ActivityRecord.State.RESUMED) || activityRecord2 != task2.getDisplayArea().topRunningActivity()) {
            zArr[0] = zArr[0] | activityRecord2.makeActiveIfNeeded(activityRecord);
        } else {
            task2.executeAppTransition(activityOptions);
        }
    }

    void sendSleepTransition(final com.android.server.wm.DisplayContent displayContent) {
        final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(12, 0, displayContent.mTransitionController, this.mWmService.mSyncEngine);
        com.android.server.wm.TransitionController.OnStartCollect onStartCollect = new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda27
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z) {
                com.android.server.wm.RootWindowContainer.lambda$sendSleepTransition$18(com.android.server.wm.DisplayContent.this, transition, z);
            }
        };
        if (!displayContent.mTransitionController.isCollecting()) {
            if (this.mWindowManager.mSyncEngine.hasActiveSync()) {
                android.util.Slog.w("WindowManager", "Ongoing sync outside of a transition.");
            }
            displayContent.mTransitionController.moveToCollecting(transition);
            onStartCollect.onCollectStarted(false);
            return;
        }
        displayContent.mTransitionController.startCollectOrQueue(transition, onStartCollect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendSleepTransition$18(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.Transition transition, boolean z) {
        if (z && !displayContent.shouldSleep()) {
            transition.abort();
        } else {
            displayContent.mTransitionController.requestStartTransition(transition, null, null, null);
            transition.playNow();
        }
    }

    void applySleepTokens(boolean z) {
        int i;
        com.android.server.wm.Task task;
        boolean z2 = false;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            final com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            final boolean shouldSleep = childAt.shouldSleep();
            if (shouldSleep != childAt.isSleeping()) {
                boolean isSleeping = childAt.isSleeping();
                childAt.setIsSleeping(shouldSleep);
                if (childAt.mTransitionController.isShellTransitionsEnabled() && !z2 && shouldSleep && !childAt.mAllSleepTokens.isEmpty()) {
                    if (!this.mHandler.hasMessages(3)) {
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, childAt), 1000L);
                    }
                    z2 = true;
                }
                if (z) {
                    if (!shouldSleep && childAt.mTransitionController.isShellTransitionsEnabled() && !childAt.mTransitionController.isCollecting()) {
                        if (isSleeping) {
                            i = 11;
                            task = null;
                        } else if (!childAt.isKeyguardOccluded()) {
                            i = 0;
                            task = null;
                        } else {
                            task = childAt.getTaskOccludingKeyguard();
                            i = 8;
                        }
                        childAt.mTransitionController.requestStartTransition(childAt.mTransitionController.createTransition(i), task, null, null);
                    }
                    childAt.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.RootWindowContainer.this.lambda$applySleepTokens$20(shouldSleep, childAt, (com.android.server.wm.Task) obj);
                        }
                    });
                }
            }
        }
        if (!z2) {
            this.mHandler.removeMessages(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySleepTokens$20(boolean z, com.android.server.wm.DisplayContent displayContent, com.android.server.wm.Task task) {
        if (z) {
            task.goToSleepIfPossible(false);
            return;
        }
        task.forAllLeafTasksAndLeafTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.TaskFragment) obj).awakeFromSleeping();
            }
        }, true);
        if (task.isFocusedRootTaskOnDisplay() && !this.mTaskSupervisor.getKeyguardController().isKeyguardOrAodShowing(displayContent.mDisplayId)) {
            task.resumeTopActivityUncheckedLocked(null, null);
        }
        task.ensureActivitiesVisible(null);
    }

    protected com.android.server.wm.Task getRootTask(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.Task rootTask = getChildAt(childCount).getRootTask(i);
            if (rootTask != null) {
                return rootTask;
            }
        }
        return null;
    }

    com.android.server.wm.Task getRootTask(int i, int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.Task rootTask = getChildAt(childCount).getRootTask(i, i2);
            if (rootTask != null) {
                return rootTask;
            }
        }
        return null;
    }

    private com.android.server.wm.Task getRootTask(int i, int i2, int i3) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(i3);
        if (displayContent == null) {
            return null;
        }
        return displayContent.getRootTask(i, i2);
    }

    private android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(final com.android.server.wm.Task task) {
        final android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo = new android.app.ActivityTaskManager.RootTaskInfo();
        task.fillTaskInfo(rootTaskInfo);
        com.android.server.wm.DisplayContent displayContent = task.getDisplayContent();
        if (displayContent == null) {
            rootTaskInfo.position = -1;
        } else {
            final int[] iArr = new int[1];
            final boolean[] zArr = new boolean[1];
            displayContent.forAllRootTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda19
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getRootTaskInfo$21;
                    lambda$getRootTaskInfo$21 = com.android.server.wm.RootWindowContainer.lambda$getRootTaskInfo$21(com.android.server.wm.Task.this, zArr, iArr, (com.android.server.wm.Task) obj);
                    return lambda$getRootTaskInfo$21;
                }
            }, false);
            rootTaskInfo.position = zArr[0] ? iArr[0] : -1;
        }
        rootTaskInfo.visible = task.shouldBeVisible(null);
        task.getBounds(rootTaskInfo.bounds);
        int descendantTaskCount = task.getDescendantTaskCount();
        rootTaskInfo.childTaskIds = new int[descendantTaskCount];
        rootTaskInfo.childTaskNames = new java.lang.String[descendantTaskCount];
        rootTaskInfo.childTaskBounds = new android.graphics.Rect[descendantTaskCount];
        rootTaskInfo.childTaskUserIds = new int[descendantTaskCount];
        final int[] iArr2 = {0};
        task.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$getRootTaskInfo$22(iArr2, rootTaskInfo, (com.android.server.wm.Task) obj);
            }
        }, false);
        com.android.server.wm.ActivityRecord activityRecord = task.topRunningActivity();
        rootTaskInfo.topActivity = activityRecord != null ? activityRecord.intent.getComponent() : null;
        return rootTaskInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getRootTaskInfo$21(com.android.server.wm.Task task, boolean[] zArr, int[] iArr, com.android.server.wm.Task task2) {
        if (task != task2) {
            iArr[0] = iArr[0] + 1;
            return false;
        }
        zArr[0] = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRootTaskInfo$22(int[] iArr, android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo, com.android.server.wm.Task task) {
        java.lang.String str;
        int i = iArr[0];
        rootTaskInfo.childTaskIds[i] = task.mTaskId;
        java.lang.String[] strArr = rootTaskInfo.childTaskNames;
        if (task.origActivity != null) {
            str = task.origActivity.flattenToString();
        } else if (task.realActivity != null) {
            str = task.realActivity.flattenToString();
        } else {
            str = task.getTopNonFinishingActivity() != null ? task.getTopNonFinishingActivity().packageName : "unknown";
        }
        strArr[i] = str;
        rootTaskInfo.childTaskBounds[i] = task.mAtmService.getTaskBounds(task.mTaskId);
        rootTaskInfo.childTaskUserIds[i] = task.mUserId;
        iArr[0] = i + 1;
    }

    android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i) {
        com.android.server.wm.Task rootTask = getRootTask(i);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) {
        com.android.server.wm.Task rootTask = getRootTask(i, i2);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2, int i3) {
        com.android.server.wm.Task rootTask = getRootTask(i, i2, i3);
        if (rootTask != null) {
            return getRootTaskInfo(rootTask);
        }
        return null;
    }

    java.util.ArrayList<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos(int i) {
        final java.util.ArrayList<android.app.ActivityTaskManager.RootTaskInfo> arrayList = new java.util.ArrayList<>();
        if (i == -1) {
            forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RootWindowContainer.this.lambda$getAllRootTaskInfos$23(arrayList, (com.android.server.wm.Task) obj);
                }
            });
            return arrayList;
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            return arrayList;
        }
        displayContent.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$getAllRootTaskInfos$24(arrayList, (com.android.server.wm.Task) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAllRootTaskInfos$23(java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        arrayList.add(getRootTaskInfo(task));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAllRootTaskInfos$24(java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        arrayList.add(getRootTaskInfo(task));
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (this.mService.isBooted() || this.mService.isBooting()) {
                    startSystemDecorations(displayContentOrCreate);
                }
                this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void startSystemDecorations(com.android.server.wm.DisplayContent displayContent) {
        startHomeOnDisplay(this.mCurrentUser, "displayAdded", displayContent.getDisplayId());
        displayContent.getDisplayPolicy().notifyDisplayReady();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Can't remove the primary display.");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
                if (displayContent == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                displayContent.remove();
                this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(final int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.requestDisplayUpdate(new java.lang.Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda42
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.RootWindowContainer.this.lambda$onDisplayChanged$25(i);
                        }
                    });
                } else {
                    lambda$onDisplayChanged$25(i);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: clearDisplayInfoCaches, reason: merged with bridge method [inline-methods] */
    public void lambda$onDisplayChanged$25(int i) {
        this.mWmService.mPossibleDisplayInfoMapper.removePossibleDisplayInfos(i);
        updateDisplayImePolicyCache();
    }

    void updateDisplayImePolicyCache() {
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$updateDisplayImePolicyCache$26(arrayMap, (com.android.server.wm.DisplayContent) obj);
            }
        });
        this.mWmService.mDisplayImePolicyCache = java.util.Collections.unmodifiableMap(arrayMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateDisplayImePolicyCache$26(android.util.ArrayMap arrayMap, com.android.server.wm.DisplayContent displayContent) {
        arrayMap.put(java.lang.Integer.valueOf(displayContent.getDisplayId()), java.lang.Integer.valueOf(displayContent.getImePolicy()));
    }

    void updateUIDsPresentOnDisplay() {
        this.mDisplayAccessUIDs.clear();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (childAt.isPrivate()) {
                this.mDisplayAccessUIDs.append(childAt.mDisplayId, childAt.getPresentUIDs());
            }
        }
        this.mDisplayManagerInternal.setDisplayAccessUIDs(this.mDisplayAccessUIDs);
    }

    void prepareForShutdown() {
        for (int i = 0; i < getChildCount(); i++) {
            createSleepToken("shutdown", getChildAt(i).mDisplayId);
        }
    }

    com.android.server.wm.RootWindowContainer.SleepToken createSleepToken(java.lang.String str, int i) {
        return createSleepToken(str, i, false);
    }

    com.android.server.wm.RootWindowContainer.SleepToken createSleepToken(java.lang.String str, int i, boolean z) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(i);
        if (displayContent == null) {
            throw new java.lang.IllegalArgumentException("Invalid display: " + i);
        }
        int makeSleepTokenKey = makeSleepTokenKey(str, i);
        com.android.server.wm.RootWindowContainer.SleepToken sleepToken = this.mSleepTokens.get(makeSleepTokenKey);
        if (sleepToken == null) {
            com.android.server.wm.RootWindowContainer.SleepToken sleepToken2 = new com.android.server.wm.RootWindowContainer.SleepToken(str, i, z);
            this.mSleepTokens.put(makeSleepTokenKey, sleepToken2);
            displayContent.mAllSleepTokens.add(sleepToken2);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -4405347314716558580L, 4, null, java.lang.String.valueOf(str), java.lang.Long.valueOf(i));
            if (z) {
                displayContent.mWallpaperController.onDisplaySwitchStarted();
            }
            return sleepToken2;
        }
        throw new java.lang.RuntimeException("Create the same sleep token twice: " + sleepToken);
    }

    void removeSleepToken(com.android.server.wm.RootWindowContainer.SleepToken sleepToken) {
        if (!this.mSleepTokens.contains(sleepToken.mHashKey)) {
            android.util.Slog.d("WindowManager", "Remove non-exist sleep token: " + sleepToken + " from " + android.os.Debug.getCallers(6));
        }
        this.mSleepTokens.remove(sleepToken.mHashKey);
        com.android.server.wm.DisplayContent displayContent = getDisplayContent(sleepToken.mDisplayId);
        if (displayContent == null) {
            android.util.Slog.d("WindowManager", "Remove sleep token for non-existing display: " + sleepToken + " from " + android.os.Debug.getCallers(6));
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1329131651776855609L, 4, null, java.lang.String.valueOf(sleepToken.mTag), java.lang.Long.valueOf(sleepToken.mDisplayId));
        displayContent.mAllSleepTokens.remove(sleepToken);
        if (displayContent.mAllSleepTokens.isEmpty()) {
            this.mService.updateSleepIfNeededLocked();
            if ((!this.mTaskSupervisor.getKeyguardController().isKeyguardOccluded(displayContent.mDisplayId) && sleepToken.mTag.equals("keyguard")) || sleepToken.mTag.equals(DISPLAY_OFF_SLEEP_TOKEN_TAG)) {
                displayContent.mSkipAppTransitionAnimation = true;
            }
        }
    }

    void addStartingWindowsForVisibleActivities() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$addStartingWindowsForVisibleActivities$27(arrayList, (com.android.server.wm.ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addStartingWindowsForVisibleActivities$27(java.util.ArrayList arrayList, com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.Task task = activityRecord.getTask();
        if (activityRecord.isVisibleRequested() && activityRecord.mStartingData == null && !arrayList.contains(task)) {
            activityRecord.showStartingWindow(true);
            arrayList.add(task);
        }
    }

    void invalidateTaskLayers() {
        if (!this.mTaskLayersChanged) {
            this.mTaskLayersChanged = true;
            this.mService.mH.post(this.mRankTaskLayersRunnable);
        }
    }

    void rankTaskLayers() {
        if (this.mTaskLayersChanged) {
            this.mTaskLayersChanged = false;
            this.mService.mH.removeCallbacks(this.mRankTaskLayersRunnable);
        }
        this.mTmpTaskLayerRank = 0;
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$rankTaskLayers$29((com.android.server.wm.Task) obj);
            }
        }, true);
        if (!this.mTaskSupervisor.inActivityVisibilityUpdate()) {
            this.mTaskSupervisor.computeProcessActivityStateBatch();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rankTaskLayers$29(com.android.server.wm.Task task) {
        int i = task.mLayerRank;
        com.android.server.wm.ActivityRecord activityRecord = task.topRunningActivityLocked();
        if (activityRecord != null && activityRecord.isVisibleRequested()) {
            int i2 = this.mTmpTaskLayerRank + 1;
            this.mTmpTaskLayerRank = i2;
            task.mLayerRank = i2;
        } else {
            task.mLayerRank = -1;
        }
        if (task.mLayerRank != i) {
            task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RootWindowContainer.this.lambda$rankTaskLayers$28((com.android.server.wm.ActivityRecord) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rankTaskLayers$28(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.hasProcess()) {
            this.mTaskSupervisor.onProcessActivityStateChanged(activityRecord.app, true);
        }
    }

    void clearOtherAppTimeTrackers(final com.android.server.am.AppTimeTracker appTimeTracker) {
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$clearOtherAppTimeTrackers$30(com.android.server.am.AppTimeTracker.this, (com.android.server.wm.ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearOtherAppTimeTrackers$30(com.android.server.am.AppTimeTracker appTimeTracker, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.appTimeTracker != appTimeTracker) {
            activityRecord.appTimeTracker = null;
        }
    }

    void scheduleDestroyAllActivities(java.lang.String str) {
        this.mDestroyAllActivitiesReason = str;
        this.mService.mH.post(this.mDestroyAllActivitiesRunnable);
    }

    boolean putTasksToSleep(final boolean z, final boolean z2) {
        final boolean[] zArr = {true};
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda39
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$putTasksToSleep$31(z, zArr, z2, (com.android.server.wm.Task) obj);
            }
        });
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putTasksToSleep$31(boolean z, boolean[] zArr, boolean z2, com.android.server.wm.Task task) {
        if (z) {
            zArr[0] = task.goToSleepIfPossible(z2) & zArr[0];
        } else {
            task.ensureActivitiesVisible(null);
        }
    }

    com.android.server.wm.ActivityRecord findActivity(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, boolean z) {
        android.content.ComponentName componentName;
        android.content.ComponentName component = intent.getComponent();
        if (activityInfo.targetActivity == null) {
            componentName = component;
        } else {
            componentName = new android.content.ComponentName(activityInfo.packageName, activityInfo.targetActivity);
        }
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.internal.util.function.QuintPredicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda11
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                boolean matchesActivity;
                matchesActivity = com.android.server.wm.RootWindowContainer.matchesActivity((com.android.server.wm.ActivityRecord) obj, ((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue(), (android.content.Intent) obj4, (android.content.ComponentName) obj5);
                return matchesActivity;
            }
        }, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), java.lang.Integer.valueOf(android.os.UserHandle.getUserId(activityInfo.applicationInfo.uid)), java.lang.Boolean.valueOf(z), intent, componentName);
        com.android.server.wm.ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean matchesActivity(com.android.server.wm.ActivityRecord activityRecord, int i, boolean z, android.content.Intent intent, android.content.ComponentName componentName) {
        if (!activityRecord.canBeTopRunning() || activityRecord.mUserId != i) {
            return false;
        }
        if (z) {
            if (activityRecord.intent.filterEquals(intent)) {
                return true;
            }
        } else if (activityRecord.mActivityComponent.equals(componentName)) {
            return true;
        }
        return false;
    }

    boolean hasAwakeDisplay() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (!getChildAt(childCount).shouldSleep()) {
                return true;
            }
        }
        return false;
    }

    com.android.server.wm.Task getOrCreateRootTask(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task, boolean z) {
        return getOrCreateRootTask(activityRecord, activityOptions, task, null, z, null, 0);
    }

    com.android.server.wm.Task getOrCreateRootTask(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable com.android.server.wm.Task task2, boolean z, @android.annotation.Nullable com.android.server.wm.LaunchParamsController.LaunchParams launchParams, int i) {
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        com.android.server.wm.TaskDisplayArea taskDisplayArea2;
        int launchDisplayId;
        com.android.server.wm.DisplayContent displayContent;
        com.android.server.wm.Task task3;
        com.android.server.wm.TaskDisplayArea taskDisplayArea3;
        int launchTaskId;
        com.android.server.wm.Task fromWindowContainerToken;
        if (activityOptions != null && (fromWindowContainerToken = com.android.server.wm.Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())) != null && canLaunchOnDisplay(activityRecord, fromWindowContainerToken)) {
            return fromWindowContainerToken;
        }
        if (activityOptions != null && (launchTaskId = activityOptions.getLaunchTaskId()) != -1) {
            activityOptions.setLaunchTaskId(-1);
            com.android.server.wm.Task anyTaskForId = anyTaskForId(launchTaskId, 2, activityOptions, z);
            activityOptions.setLaunchTaskId(launchTaskId);
            if (canLaunchOnDisplay(activityRecord, anyTaskForId)) {
                return anyTaskForId.getRootTask();
            }
        }
        com.android.server.wm.TaskDisplayArea taskDisplayArea4 = null;
        if (launchParams != null && launchParams.mPreferredTaskDisplayArea != null) {
            taskDisplayArea = launchParams.mPreferredTaskDisplayArea;
        } else if (activityOptions == null) {
            taskDisplayArea = null;
        } else {
            android.window.WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
            if (launchTaskDisplayArea == null) {
                taskDisplayArea2 = null;
            } else {
                taskDisplayArea2 = (com.android.server.wm.TaskDisplayArea) com.android.server.wm.WindowContainer.fromBinder(launchTaskDisplayArea.asBinder());
            }
            if (taskDisplayArea2 == null && (launchDisplayId = activityOptions.getLaunchDisplayId()) != -1 && (displayContent = getDisplayContent(launchDisplayId)) != null) {
                taskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            } else {
                taskDisplayArea = taskDisplayArea2;
            }
        }
        int resolveActivityType = resolveActivityType(activityRecord, activityOptions, task);
        if (taskDisplayArea != null) {
            if (canLaunchOnDisplay(activityRecord, taskDisplayArea.getDisplayId())) {
                return taskDisplayArea.getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
            }
            taskDisplayArea = null;
        }
        if (task == null) {
            task3 = null;
        } else {
            task3 = task.getRootTask();
        }
        if (task3 == null && activityRecord != null) {
            task3 = activityRecord.getRootTask();
        }
        int i2 = launchParams != null ? launchParams.mWindowingMode : 0;
        if (task3 == null) {
            taskDisplayArea4 = taskDisplayArea;
        } else {
            com.android.server.wm.TaskDisplayArea displayArea = task3.getDisplayArea();
            if (displayArea != null && canLaunchOnDisplay(activityRecord, displayArea.mDisplayContent.mDisplayId)) {
                if (i2 == 0) {
                    i2 = displayArea.resolveWindowingMode(activityRecord, activityOptions, task);
                }
                if (task3.isCompatible(i2, resolveActivityType) || task3.mCreatedByOrganizer) {
                    return task3;
                }
                taskDisplayArea4 = displayArea;
            }
        }
        if (taskDisplayArea4 != null) {
            taskDisplayArea3 = taskDisplayArea4;
        } else {
            taskDisplayArea3 = getDefaultTaskDisplayArea();
        }
        return taskDisplayArea3.getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, resolveActivityType, z);
    }

    private boolean canLaunchOnDisplay(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Task task) {
        if (task == null) {
            android.util.Slog.w("WindowManager", "canLaunchOnDisplay(), invalid task: " + task);
            return false;
        }
        if (!task.isAttached()) {
            android.util.Slog.w("WindowManager", "canLaunchOnDisplay(), Task is not attached: " + task);
            return false;
        }
        return canLaunchOnDisplay(activityRecord, task.getTaskDisplayArea().getDisplayId());
    }

    private boolean canLaunchOnDisplay(com.android.server.wm.ActivityRecord activityRecord, int i) {
        if (activityRecord == null || activityRecord.canBeLaunchedOnDisplay(i)) {
            return true;
        }
        android.util.Slog.w("WindowManager", "Not allow to launch " + activityRecord + " on display " + i);
        return false;
    }

    int resolveActivityType(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.Task task) {
        int activityType = activityRecord != null ? activityRecord.getActivityType() : 0;
        if (activityType == 0 && task != null) {
            activityType = task.getActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        if (activityOptions != null) {
            activityType = activityOptions.getLaunchActivityType();
        }
        if (activityType != 0) {
            return activityType;
        }
        return 1;
    }

    com.android.server.wm.Task getNextFocusableRootTask(@android.annotation.NonNull com.android.server.wm.Task task, boolean z) {
        com.android.server.wm.Task nextFocusableRootTask;
        com.android.server.wm.TaskDisplayArea displayArea = task.getDisplayArea();
        if (displayArea == null) {
            displayArea = getDisplayContent(task.mPrevDisplayId).getDefaultTaskDisplayArea();
        }
        com.android.server.wm.Task nextFocusableRootTask2 = displayArea.getNextFocusableRootTask(task, z);
        if (nextFocusableRootTask2 != null) {
            return nextFocusableRootTask2;
        }
        if (displayArea.mDisplayContent.isHomeSupported()) {
            return null;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (childAt != displayArea.mDisplayContent && (nextFocusableRootTask = childAt.getDefaultTaskDisplayArea().getNextFocusableRootTask(task, z)) != null) {
                return nextFocusableRootTask;
            }
        }
        return null;
    }

    void closeSystemDialogActivities(final java.lang.String str) {
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$closeSystemDialogActivities$32(str, (com.android.server.wm.ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closeSystemDialogActivities$32(java.lang.String str, com.android.server.wm.ActivityRecord activityRecord) {
        if ((activityRecord.info.flags & 256) != 0 || shouldCloseAssistant(activityRecord, str)) {
            activityRecord.finishIfPossible(str, true);
        }
    }

    boolean hasVisibleWindowAboveButDoesNotOwnNotificationShade(final int i) {
        final boolean[] zArr = {false};
        return forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda43
            public final boolean apply(java.lang.Object obj) {
                boolean lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$33;
                lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$33 = com.android.server.wm.RootWindowContainer.lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$33(i, zArr, (com.android.server.wm.WindowState) obj);
                return lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$33;
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasVisibleWindowAboveButDoesNotOwnNotificationShade$33(int i, boolean[] zArr, com.android.server.wm.WindowState windowState) {
        if (windowState.mOwnerUid == i && windowState.isVisible()) {
            zArr[0] = true;
        }
        if (windowState.mAttrs.type == 2040) {
            return zArr[0] && windowState.mOwnerUid != i;
        }
        return false;
    }

    private boolean shouldCloseAssistant(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        if (activityRecord.isActivityTypeAssistant() && str != com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_ASSIST) {
            return this.mWmService.mAssistantOnTopOfDream;
        }
        return false;
    }

    class FinishDisabledPackageActivitiesHelper implements java.util.function.Predicate<com.android.server.wm.ActivityRecord> {
        private final java.util.ArrayList<com.android.server.wm.ActivityRecord> mCollectedActivities = new java.util.ArrayList<>();
        private boolean mDoit;
        private boolean mEvenPersistent;
        private java.util.Set<java.lang.String> mFilterByClasses;
        private com.android.server.wm.Task mLastTask;
        private boolean mOnlyRemoveNoProcess;
        private java.lang.String mPackageName;
        private int mUserId;

        FinishDisabledPackageActivitiesHelper() {
        }

        private void reset(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, int i, boolean z3) {
            this.mPackageName = str;
            this.mFilterByClasses = set;
            this.mDoit = z;
            this.mEvenPersistent = z2;
            this.mUserId = i;
            this.mOnlyRemoveNoProcess = z3;
            this.mLastTask = null;
        }

        boolean process(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, int i, boolean z3) {
            reset(str, set, z, z2, i, z3);
            com.android.server.wm.RootWindowContainer.this.forAllActivities(this);
            int size = this.mCollectedActivities.size();
            boolean z4 = false;
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.wm.ActivityRecord activityRecord = this.mCollectedActivities.get(i2);
                if (this.mOnlyRemoveNoProcess) {
                    if (!activityRecord.hasProcess()) {
                        android.util.Slog.i("WindowManager", "  Force removing " + activityRecord);
                        activityRecord.cleanUp(false, false);
                        activityRecord.removeFromHistory("force-stop");
                        z4 = true;
                    }
                } else {
                    android.util.Slog.i("WindowManager", "  Force finishing " + activityRecord);
                    activityRecord.finishIfPossible("force-stop", true);
                    z4 = true;
                }
            }
            this.mCollectedActivities.clear();
            return z4;
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.ActivityRecord activityRecord) {
            boolean z = (activityRecord.packageName.equals(this.mPackageName) && (this.mFilterByClasses == null || this.mFilterByClasses.contains(activityRecord.mActivityComponent.getClassName()))) || (this.mPackageName == null && activityRecord.mUserId == this.mUserId);
            boolean z2 = !activityRecord.hasProcess();
            if ((this.mUserId == -1 || activityRecord.mUserId == this.mUserId) && ((z || activityRecord.getTask() == this.mLastTask) && (z2 || this.mEvenPersistent || !activityRecord.app.isPersistent()))) {
                if (!this.mDoit) {
                    return !activityRecord.finishing;
                }
                this.mCollectedActivities.add(activityRecord);
                this.mLastTask = activityRecord.getTask();
            }
            return false;
        }
    }

    boolean finishDisabledPackageActivities(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, int i, boolean z3) {
        return this.mFinishDisabledPackageActivitiesHelper.process(str, set, z, z2, i, z3);
    }

    void updateActivityApplicationInfo(final android.content.pm.ApplicationInfo applicationInfo) {
        final java.lang.String str = applicationInfo.packageName;
        final int userId = android.os.UserHandle.getUserId(applicationInfo.uid);
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$updateActivityApplicationInfo$34(userId, str, applicationInfo, (com.android.server.wm.ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateActivityApplicationInfo$34(int i, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.mUserId == i && str.equals(activityRecord.packageName)) {
            activityRecord.updateApplicationInfo(applicationInfo);
        }
    }

    void updateActivityApplicationInfo(final int i, final android.util.ArrayMap<java.lang.String, android.content.pm.ApplicationInfo> arrayMap) {
        forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda51
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.lambda$updateActivityApplicationInfo$35(i, arrayMap, (com.android.server.wm.ActivityRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateActivityApplicationInfo$35(int i, android.util.ArrayMap arrayMap, com.android.server.wm.ActivityRecord activityRecord) {
        android.content.pm.ApplicationInfo applicationInfo;
        if (activityRecord.mUserId == i && (applicationInfo = (android.content.pm.ApplicationInfo) arrayMap.get(activityRecord.packageName)) != null) {
            activityRecord.updateApplicationInfo(applicationInfo);
        }
    }

    void finishVoiceTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) {
        final android.os.IBinder asBinder = iVoiceInteractionSession.asBinder();
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.Task) obj).finishIfVoiceTask(asBinder);
            }
        }, true);
    }

    void removeRootTasksInWindowingModes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).removeRootTasksInWindowingModes(iArr);
        }
    }

    void removeRootTasksWithActivityTypes(int... iArr) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).removeRootTasksWithActivityTypes(iArr);
        }
    }

    com.android.server.wm.ActivityRecord topRunningActivity() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.ActivityRecord activityRecord = getChildAt(childCount).topRunningActivity();
            if (activityRecord != null) {
                return activityRecord;
            }
        }
        return null;
    }

    boolean allResumedActivitiesIdle() {
        com.android.server.wm.Task focusedRootTask;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (!childAt.isSleeping() && (focusedRootTask = childAt.getFocusedRootTask()) != null && focusedRootTask.hasActivity()) {
                com.android.server.wm.ActivityRecord topResumedActivity = focusedRootTask.getTopResumedActivity();
                if (topResumedActivity == null || !topResumedActivity.idle) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1653728842643223887L, 1, null, java.lang.Long.valueOf(focusedRootTask.getRootTaskId()), java.lang.String.valueOf(topResumedActivity));
                    return false;
                }
                if (this.mTransitionController.isTransientLaunch(topResumedActivity)) {
                    return false;
                }
            }
        }
        this.mService.endPowerMode(1);
        return true;
    }

    boolean allResumedActivitiesVisible() {
        final boolean[] zArr = {false};
        if (forAllRootTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$allResumedActivitiesVisible$37;
                lambda$allResumedActivitiesVisible$37 = com.android.server.wm.RootWindowContainer.lambda$allResumedActivitiesVisible$37(zArr, (com.android.server.wm.Task) obj);
                return lambda$allResumedActivitiesVisible$37;
            }
        })) {
            return false;
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$allResumedActivitiesVisible$37(boolean[] zArr, com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord topResumedActivity = task.getTopResumedActivity();
        if (topResumedActivity != null) {
            if (!topResumedActivity.nowVisible) {
                return true;
            }
            zArr[0] = true;
        }
        return false;
    }

    boolean allPausedActivitiesComplete() {
        final boolean[] zArr = {true};
        if (forAllLeafTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$allPausedActivitiesComplete$38;
                lambda$allPausedActivitiesComplete$38 = com.android.server.wm.RootWindowContainer.lambda$allPausedActivitiesComplete$38(zArr, (com.android.server.wm.Task) obj);
                return lambda$allPausedActivitiesComplete$38;
            }
        })) {
            return false;
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$allPausedActivitiesComplete$38(boolean[] zArr, com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord topPausingActivity = task.getTopPausingActivity();
        if (topPausingActivity != null && !topPausingActivity.isState(com.android.server.wm.ActivityRecord.State.PAUSED, com.android.server.wm.ActivityRecord.State.STOPPED, com.android.server.wm.ActivityRecord.State.STOPPING, com.android.server.wm.ActivityRecord.State.FINISHING)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3785779399471740019L, 0, null, java.lang.String.valueOf(topPausingActivity), java.lang.String.valueOf(topPausingActivity.getState()));
            if (com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES.isEnabled()) {
                zArr[0] = false;
            } else {
                return true;
            }
        }
        return false;
    }

    void lockAllProfileTasks(final int i) {
        forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda45
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RootWindowContainer.this.lambda$lockAllProfileTasks$40(i, (com.android.server.wm.Task) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lockAllProfileTasks$40(final int i, com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord activityRecord = task.topRunningActivity();
        if ((activityRecord == null || activityRecord.finishing || !"android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER".equals(activityRecord.intent.getAction()) || !activityRecord.packageName.equals(this.mService.getSysUiServiceComponentLocked().getPackageName())) && task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda35
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$lockAllProfileTasks$39;
                lambda$lockAllProfileTasks$39 = com.android.server.wm.RootWindowContainer.lambda$lockAllProfileTasks$39(i, (com.android.server.wm.ActivityRecord) obj);
                return lambda$lockAllProfileTasks$39;
            }
        }) != null) {
            this.mService.getTaskChangeNotificationController().notifyTaskProfileLocked(task.getTaskInfo(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$lockAllProfileTasks$39(int i, com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing && activityRecord.mUserId == i;
    }

    com.android.server.wm.Task anyTaskForId(int i) {
        return anyTaskForId(i, 2);
    }

    com.android.server.wm.Task anyTaskForId(int i, int i2) {
        return anyTaskForId(i, i2, null, false);
    }

    com.android.server.wm.Task anyTaskForId(int i, int i2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, boolean z) {
        com.android.server.wm.Task task;
        com.android.server.wm.Task orCreateRootTask;
        int i3 = 2;
        if (i2 != 2 && activityOptions != null) {
            throw new java.lang.IllegalArgumentException("Should not specify activity options for non-restore lookup");
        }
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.server.wm.AppTransition$$ExternalSyntheticLambda1(), com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.Task.class), java.lang.Integer.valueOf(i));
        com.android.server.wm.Task task2 = getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            if (activityOptions != null && (orCreateRootTask = getOrCreateRootTask(null, activityOptions, task2, z)) != null && task2.getRootTask() != orCreateRootTask && task2.getParent() != orCreateRootTask) {
                if (z) {
                    i3 = 0;
                }
                task2.reparent(orCreateRootTask, z, i3, true, true, "anyTaskForId");
            }
            return task2;
        }
        if (i2 == 0 || (task = this.mTaskSupervisor.mRecentTasks.getTask(i)) == null) {
            return null;
        }
        if (i2 == 1) {
            return task;
        }
        if (!this.mTaskSupervisor.restoreRecentTaskLocked(task, activityOptions, z)) {
            return null;
        }
        return task;
    }

    @com.android.internal.annotations.VisibleForTesting
    void getRunningTasks(int i, java.util.List<android.app.ActivityManager.RunningTaskInfo> list, int i2, int i3, android.util.ArraySet<java.lang.Integer> arraySet, int i4) {
        com.android.server.wm.RootWindowContainer rootWindowContainer;
        if (i4 == -1) {
            rootWindowContainer = this;
        } else {
            com.android.server.wm.DisplayContent displayContent = getDisplayContent(i4);
            if (displayContent != null) {
                rootWindowContainer = displayContent;
            } else {
                return;
            }
        }
        this.mTaskSupervisor.getRunningTasks().getTasks(i, list, i2, this.mService.getRecentTasks(), rootWindowContainer, i3, arraySet);
    }

    void startPowerModeLaunchIfNeeded(boolean z, final com.android.server.wm.ActivityRecord activityRecord) {
        android.app.ActivityOptions options;
        int i = 1;
        if (!z && activityRecord != null && activityRecord.app != null) {
            final boolean[] zArr = {true};
            final boolean[] zArr2 = {true};
            forAllTaskDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RootWindowContainer.lambda$startPowerModeLaunchIfNeeded$41(zArr, zArr2, activityRecord, (com.android.server.wm.TaskDisplayArea) obj);
                }
            });
            if (!zArr[0] && !zArr2[0]) {
                return;
            }
        }
        if ((activityRecord != null ? activityRecord.isKeyguardLocked() : this.mDefaultDisplay.isKeyguardLocked()) && activityRecord != null && !activityRecord.isLaunchSourceType(3) && ((options = activityRecord.getOptions()) == null || options.getSourceInfo() == null || options.getSourceInfo().type != 3)) {
            i = 5;
        }
        this.mService.startPowerMode(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startPowerModeLaunchIfNeeded$41(boolean[] zArr, boolean[] zArr2, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        com.android.server.wm.ActivityRecord focusedActivity = taskDisplayArea.getFocusedActivity();
        com.android.server.wm.WindowProcessController windowProcessController = focusedActivity == null ? null : focusedActivity.app;
        zArr[0] = zArr[0] & (windowProcessController == null);
        if (windowProcessController != null) {
            zArr2[0] = zArr2[0] & (!windowProcessController.equals(activityRecord.app));
        }
    }

    public int getTaskToShowPermissionDialogOn(final java.lang.String str, final int i) {
        final com.android.server.policy.PermissionPolicyInternal permissionPolicyInternal = this.mService.getPermissionPolicyInternal();
        if (permissionPolicyInternal == null) {
            return -1;
        }
        final int[] iArr = {-1};
        forAllLeafTaskFragments(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTaskToShowPermissionDialogOn$43;
                lambda$getTaskToShowPermissionDialogOn$43 = com.android.server.wm.RootWindowContainer.lambda$getTaskToShowPermissionDialogOn$43(com.android.server.policy.PermissionPolicyInternal.this, i, str, iArr, (com.android.server.wm.TaskFragment) obj);
                return lambda$getTaskToShowPermissionDialogOn$43;
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTaskToShowPermissionDialogOn$43(final com.android.server.policy.PermissionPolicyInternal permissionPolicyInternal, int i, java.lang.String str, int[] iArr, com.android.server.wm.TaskFragment taskFragment) {
        com.android.server.wm.ActivityRecord activity = taskFragment.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTaskToShowPermissionDialogOn$42;
                lambda$getTaskToShowPermissionDialogOn$42 = com.android.server.wm.RootWindowContainer.lambda$getTaskToShowPermissionDialogOn$42(com.android.server.policy.PermissionPolicyInternal.this, (com.android.server.wm.ActivityRecord) obj);
                return lambda$getTaskToShowPermissionDialogOn$42;
            }
        });
        if (activity == null || !activity.isUid(i) || !java.util.Objects.equals(str, activity.packageName) || !permissionPolicyInternal.shouldShowNotificationDialogForTask(activity.getTask().getTaskInfo(), str, activity.launchedFromPackage, activity.intent, activity.getName())) {
            return false;
        }
        iArr[0] = activity.getTask().mTaskId;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTaskToShowPermissionDialogOn$42(com.android.server.policy.PermissionPolicyInternal permissionPolicyInternal, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.canBeTopRunning() && activityRecord.isVisibleRequested() && !permissionPolicyInternal.isIntentToPermissionDialog(activityRecord.intent);
    }

    java.util.ArrayList<com.android.server.wm.ActivityRecord> getDumpActivities(final java.lang.String str, final boolean z, boolean z2, final int i) {
        final int i2;
        if (z2) {
            com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
            if (topDisplayFocusedRootTask != null) {
                return topDisplayFocusedRootTask.getDumpActivitiesLocked(str, i);
            }
            return new java.util.ArrayList<>();
        }
        com.android.server.wm.RecentTasks recentTasks = this.mWindowManager.mAtmService.getRecentTasks();
        if (recentTasks != null) {
            i2 = recentTasks.getRecentsComponentUid();
        } else {
            i2 = -1;
        }
        final java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = new java.util.ArrayList<>();
        forAllLeafTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda49
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getDumpActivities$44;
                lambda$getDumpActivities$44 = com.android.server.wm.RootWindowContainer.lambda$getDumpActivities$44(i2, z, arrayList, str, i, (com.android.server.wm.Task) obj);
                return lambda$getDumpActivities$44;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getDumpActivities$44(int i, boolean z, java.util.ArrayList arrayList, java.lang.String str, int i2, com.android.server.wm.Task task) {
        boolean z2 = task.effectiveUid == i;
        if (!z || task.shouldBeVisible(null) || z2) {
            arrayList.addAll(task.getDumpActivitiesLocked(str, i2));
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.println("topDisplayFocusedRootTask=" + getTopDisplayFocusedRootTask());
        for (int childCount = getChildCount() + (-1); childCount >= 0; childCount--) {
            getChildAt(childCount).dump(printWriter, str, z);
        }
    }

    void dumpDisplayConfigs(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("Display override configurations:");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            com.android.server.wm.DisplayContent childAt = getChildAt(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.print(childAt.mDisplayId);
            printWriter.print(": ");
            printWriter.println(childAt.getRequestedOverrideConfiguration());
        }
    }

    boolean dumpActivities(final java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, final boolean z, final boolean z2, final java.lang.String str, int i) {
        int i2 = -1;
        final boolean[] zArr = {false};
        final boolean[] zArr2 = {false};
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            com.android.server.wm.DisplayContent childAt = getChildAt(childCount);
            if (zArr[0]) {
                printWriter.println();
            }
            if (i == i2 || childAt.mDisplayId == i) {
                printWriter.print("Display #");
                printWriter.print(childAt.mDisplayId);
                printWriter.println(" (activities from top to bottom):");
                childAt.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.RootWindowContainer.lambda$dumpActivities$45(zArr2, printWriter, fileDescriptor, z, z2, str, zArr, (com.android.server.wm.Task) obj);
                    }
                });
                childAt.forAllTaskDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.RootWindowContainer.lambda$dumpActivities$47(zArr, printWriter, str, zArr2, (com.android.server.wm.TaskDisplayArea) obj);
                    }
                });
            }
            childCount--;
            i2 = -1;
        }
        zArr[0] = zArr[0] | com.android.server.wm.ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, this.mTaskSupervisor.mFinishingActivities, "  ", "Fin", false, !z, false, str, true, new java.lang.Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Activities waiting to finish:");
            }
        }, null);
        zArr[0] = com.android.server.wm.ActivityTaskSupervisor.dumpHistoryList(fileDescriptor, printWriter, this.mTaskSupervisor.mStoppingActivities, "  ", "Stop", false, !z, false, str, true, new java.lang.Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Activities waiting to stop:");
            }
        }, null) | zArr[0];
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpActivities$45(boolean[] zArr, java.io.PrintWriter printWriter, java.io.FileDescriptor fileDescriptor, boolean z, boolean z2, java.lang.String str, boolean[] zArr2, com.android.server.wm.Task task) {
        if (zArr[0]) {
            printWriter.println();
        }
        zArr[0] = task.dump(fileDescriptor, printWriter, z, z2, str, false);
        zArr2[0] = zArr2[0] | zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpActivities$47(boolean[] zArr, final java.io.PrintWriter printWriter, java.lang.String str, boolean[] zArr2, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        zArr[0] = com.android.server.wm.ActivityTaskSupervisor.printThisActivity(printWriter, taskDisplayArea.getFocusedActivity(), str, zArr2[0], "    Resumed: ", new java.lang.Runnable() { // from class: com.android.server.wm.RootWindowContainer$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                printWriter.println("  Resumed activities in task display areas (from top to bottom):");
            }
        }) | zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int makeSleepTokenKey(java.lang.String str, int i) {
        return (str + i).hashCode();
    }

    static final class SleepToken {
        private static final long DISPLAY_SWAP_TIMEOUT = 1000;
        private final long mAcquireTime = android.os.SystemClock.uptimeMillis();
        private final int mDisplayId;
        final int mHashKey;
        private final boolean mIsSwappingDisplay;
        private final java.lang.String mTag;

        SleepToken(java.lang.String str, int i, boolean z) {
            this.mTag = str;
            this.mDisplayId = i;
            this.mIsSwappingDisplay = z;
            this.mHashKey = com.android.server.wm.RootWindowContainer.makeSleepTokenKey(this.mTag, this.mDisplayId);
        }

        public boolean isDisplaySwapping() {
            if (android.os.SystemClock.uptimeMillis() - this.mAcquireTime > 1000) {
                return false;
            }
            return this.mIsSwappingDisplay;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{\"");
            sb.append(this.mTag);
            sb.append("\", display ");
            sb.append(this.mDisplayId);
            sb.append(this.mIsSwappingDisplay ? " is swapping " : "");
            sb.append(", acquire at ");
            sb.append(android.util.TimeUtils.formatUptime(this.mAcquireTime));
            sb.append("}");
            return sb.toString();
        }

        void writeTagToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            protoOutputStream.write(j, this.mTag);
        }
    }

    private class RankTaskLayersRunnable implements java.lang.Runnable {
        private RankTaskLayersRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RootWindowContainer.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.RootWindowContainer.this.mTaskLayersChanged) {
                        com.android.server.wm.RootWindowContainer.this.mTaskLayersChanged = false;
                        com.android.server.wm.RootWindowContainer.this.rankTaskLayers();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    private class AttachApplicationHelper implements java.util.function.Consumer<com.android.server.wm.Task>, java.util.function.Predicate<com.android.server.wm.ActivityRecord> {
        private com.android.server.wm.WindowProcessController mApp;
        private boolean mHasActivityStarted;
        private android.os.RemoteException mRemoteException;
        private com.android.server.wm.ActivityRecord mTop;

        private AttachApplicationHelper() {
        }

        void reset() {
            this.mHasActivityStarted = false;
            this.mRemoteException = null;
            this.mApp = null;
            this.mTop = null;
        }

        boolean process(com.android.server.wm.WindowProcessController windowProcessController) throws android.os.RemoteException {
            this.mApp = windowProcessController;
            for (int childCount = com.android.server.wm.RootWindowContainer.this.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.RootWindowContainer.this.getChildAt(childCount).forAllRootTasks((java.util.function.Consumer<com.android.server.wm.Task>) this);
                if (this.mRemoteException != null) {
                    throw this.mRemoteException;
                }
            }
            if (!this.mHasActivityStarted) {
                com.android.server.wm.RootWindowContainer.this.ensureActivitiesVisible();
            }
            return this.mHasActivityStarted;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.wm.Task task) {
            if (this.mRemoteException != null || task.getVisibility(null) == 2) {
                return;
            }
            this.mTop = task.topRunningActivity();
            task.forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) this);
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.ActivityRecord activityRecord) {
            boolean z;
            if (activityRecord.finishing || !activityRecord.showToCurrentUser() || !activityRecord.visibleIgnoringKeyguard || activityRecord.app != null || this.mApp.mUid != activityRecord.info.applicationInfo.uid || !this.mApp.mName.equals(activityRecord.processName)) {
                return false;
            }
            try {
                com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor = com.android.server.wm.RootWindowContainer.this.mTaskSupervisor;
                com.android.server.wm.WindowProcessController windowProcessController = this.mApp;
                if (this.mTop == activityRecord && activityRecord.getTask().canBeResumed(activityRecord)) {
                    z = true;
                } else {
                    z = false;
                }
                if (activityTaskSupervisor.realStartActivityLocked(activityRecord, windowProcessController, z, true)) {
                    this.mHasActivityStarted = true;
                }
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("WindowManager", "Exception in new application when starting activity " + this.mTop, e);
                this.mRemoteException = e;
                return true;
            }
        }
    }
}
