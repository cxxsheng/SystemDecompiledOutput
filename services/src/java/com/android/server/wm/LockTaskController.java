package com.android.server.wm;

/* loaded from: classes3.dex */
public class LockTaskController {
    static final int LOCK_TASK_AUTH_ALLOWLISTED = 3;
    static final int LOCK_TASK_AUTH_DONT_LOCK = 0;
    static final int LOCK_TASK_AUTH_LAUNCHABLE = 2;
    static final int LOCK_TASK_AUTH_LAUNCHABLE_PRIV = 4;
    static final int LOCK_TASK_AUTH_PINNABLE = 1;
    private static final java.lang.String LOCK_TASK_TAG = "Lock-to-App";
    private static final android.util.SparseArray<android.util.Pair<java.lang.Integer, java.lang.Integer>> STATUS_BAR_FLAG_MAP_LOCKED = new android.util.SparseArray<>();

    @com.android.internal.annotations.VisibleForTesting
    static final int STATUS_BAR_MASK_LOCKED = 128319488;

    @com.android.internal.annotations.VisibleForTesting
    static final int STATUS_BAR_MASK_PINNED = 111083520;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_LOCKTASK = "ActivityTaskManager";
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    android.app.admin.IDevicePolicyManager mDevicePolicyManager;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.widget.LockPatternUtils mLockPatternUtils;

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.statusbar.IStatusBarService mStatusBarService;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private final com.android.server.wm.TaskChangeNotificationController mTaskChangeNotificationController;

    @com.android.internal.annotations.VisibleForTesting
    android.telecom.TelecomManager mTelecomManager;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.WindowManagerService mWindowManager;
    private final android.os.IBinder mToken = new com.android.server.wm.LockTaskController.LockTaskToken();
    private final java.util.ArrayList<com.android.server.wm.Task> mLockTaskModeTasks = new java.util.ArrayList<>();
    private final android.util.SparseArray<java.lang.String[]> mLockTaskPackages = new android.util.SparseArray<>();
    private final android.util.SparseIntArray mLockTaskFeatures = new android.util.SparseIntArray();
    private volatile int mLockTaskModeState = 0;
    private int mPendingDisableFromDismiss = com.android.server.am.ProcessList.INVALID_ADJ;

    static {
        STATUS_BAR_FLAG_MAP_LOCKED.append(1, new android.util.Pair<>(8388608, 2));
        STATUS_BAR_FLAG_MAP_LOCKED.append(2, new android.util.Pair<>(393216, 4));
        STATUS_BAR_FLAG_MAP_LOCKED.append(4, new android.util.Pair<>(2097152, 0));
        STATUS_BAR_FLAG_MAP_LOCKED.append(8, new android.util.Pair<>(16777216, 0));
        STATUS_BAR_FLAG_MAP_LOCKED.append(16, new android.util.Pair<>(0, 8));
    }

    LockTaskController(android.content.Context context, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.os.Handler handler, com.android.server.wm.TaskChangeNotificationController taskChangeNotificationController) {
        this.mContext = context;
        this.mSupervisor = activityTaskSupervisor;
        this.mHandler = handler;
        this.mTaskChangeNotificationController = taskChangeNotificationController;
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
    }

    int getLockTaskModeState() {
        return this.mLockTaskModeState;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isTaskLocked(com.android.server.wm.Task task) {
        return this.mLockTaskModeTasks.contains(task);
    }

    private boolean isRootTask(com.android.server.wm.Task task) {
        return this.mLockTaskModeTasks.indexOf(task) == 0;
    }

    boolean activityBlockedFromFinish(final com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.Task task = activityRecord.getTask();
        if (task.mLockTaskAuth == 4 || !isRootTask(task)) {
            return false;
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        if (activityRecord != task.getRootActivity() || activityRecord != topNonFinishingActivity) {
            com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
            final com.android.server.wm.TaskFragment adjacentTaskFragment = taskFragment.getAdjacentTaskFragment();
            if (taskFragment.asTask() != null || !taskFragment.isDelayLastActivityRemoval() || adjacentTaskFragment == null) {
                return false;
            }
            if (taskFragment.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$activityBlockedFromFinish$0;
                    lambda$activityBlockedFromFinish$0 = com.android.server.wm.LockTaskController.lambda$activityBlockedFromFinish$0(com.android.server.wm.ActivityRecord.this, (com.android.server.wm.ActivityRecord) obj);
                    return lambda$activityBlockedFromFinish$0;
                }
            }) != null) {
                return false;
            }
            if (task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$activityBlockedFromFinish$1;
                    lambda$activityBlockedFromFinish$1 = com.android.server.wm.LockTaskController.lambda$activityBlockedFromFinish$1(com.android.server.wm.ActivityRecord.this, adjacentTaskFragment, (com.android.server.wm.ActivityRecord) obj);
                    return lambda$activityBlockedFromFinish$1;
                }
            }) != null) {
                return false;
            }
        }
        android.util.Slog.i("ActivityTaskManager", "Not finishing task in lock task mode");
        showLockTaskToast();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$activityBlockedFromFinish$0(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return (activityRecord2.finishing || activityRecord2 == activityRecord) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$activityBlockedFromFinish$1(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.TaskFragment taskFragment, com.android.server.wm.ActivityRecord activityRecord2) {
        return (activityRecord2.finishing || activityRecord2 == activityRecord || activityRecord2.getTaskFragment() == taskFragment) ? false : true;
    }

    boolean canMoveTaskToBack(com.android.server.wm.Task task) {
        if (isRootTask(task)) {
            showLockTaskToast();
            return false;
        }
        return true;
    }

    static boolean isTaskAuthAllowlisted(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    boolean isLockTaskModeViolation(com.android.server.wm.Task task) {
        return isLockTaskModeViolation(task, false);
    }

    boolean isLockTaskModeViolation(com.android.server.wm.Task task, boolean z) {
        if ((!isTaskLocked(task) || z) && isLockTaskModeViolationInternal(task, task.mUserId, task.intent, task.mLockTaskAuth)) {
            showLockTaskToast();
            return true;
        }
        return false;
    }

    boolean isNewTaskLockTaskModeViolation(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.getTask() != null) {
            return isLockTaskModeViolation(activityRecord.getTask());
        }
        if (isLockTaskModeViolationInternal(activityRecord, activityRecord.mUserId, activityRecord.intent, getLockTaskAuth(activityRecord, null))) {
            showLockTaskToast();
            return true;
        }
        return false;
    }

    com.android.server.wm.Task getRootTask() {
        if (this.mLockTaskModeTasks.isEmpty()) {
            return null;
        }
        return this.mLockTaskModeTasks.get(0);
    }

    private boolean isLockTaskModeViolationInternal(com.android.server.wm.WindowContainer windowContainer, int i, android.content.Intent intent, int i2) {
        if (windowContainer.isActivityTypeRecents() && isRecentsAllowed(i)) {
            return false;
        }
        return ((isKeyguardAllowed(i) && isEmergencyCallIntent(intent)) || windowContainer.isActivityTypeDream() || isWirelessEmergencyAlert(intent) || isTaskAuthAllowlisted(i2) || this.mLockTaskModeTasks.isEmpty()) ? false : true;
    }

    private boolean isRecentsAllowed(int i) {
        return (getLockTaskFeaturesForUser(i) & 8) != 0;
    }

    private boolean isKeyguardAllowed(int i) {
        return (getLockTaskFeaturesForUser(i) & 32) != 0;
    }

    private boolean isBlockingInTaskEnabled(int i) {
        return (getLockTaskFeaturesForUser(i) & 64) != 0;
    }

    boolean isActivityAllowed(int i, java.lang.String str, int i2) {
        if (this.mLockTaskModeState != 1 || !isBlockingInTaskEnabled(i)) {
            return true;
        }
        switch (i2) {
            case 1:
                return false;
            case 2:
                return true;
            default:
                return isPackageAllowlisted(i, str);
        }
    }

    private boolean isWirelessEmergencyAlert(android.content.Intent intent) {
        android.content.ComponentName defaultCellBroadcastAlertDialogComponent;
        if (intent == null || (defaultCellBroadcastAlertDialogComponent = com.android.internal.telephony.CellBroadcastUtils.getDefaultCellBroadcastAlertDialogComponent(this.mContext)) == null || !defaultCellBroadcastAlertDialogComponent.equals(intent.getComponent())) {
            return false;
        }
        return true;
    }

    private boolean isEmergencyCallIntent(android.content.Intent intent) {
        if (intent == null) {
            return false;
        }
        if (android.telecom.TelecomManager.EMERGENCY_DIALER_COMPONENT.equals(intent.getComponent()) || "android.intent.action.CALL_EMERGENCY".equals(intent.getAction())) {
            return true;
        }
        android.telecom.TelecomManager telecomManager = getTelecomManager();
        java.lang.String systemDialerPackage = telecomManager != null ? telecomManager.getSystemDialerPackage() : null;
        return systemDialerPackage != null && systemDialerPackage.equals(intent.getComponent().getPackageName());
    }

    void stopLockTaskMode(@android.annotation.Nullable com.android.server.wm.Task task, boolean z, int i) {
        if (this.mLockTaskModeState == 0) {
            return;
        }
        if (z) {
            if (this.mLockTaskModeState == 2) {
                clearLockedTasks("stopAppPinning");
                return;
            } else {
                android.util.Slog.e("ActivityTaskManager", "Attempted to stop app pinning while fully locked");
                showLockTaskToast();
                return;
            }
        }
        if (task == null) {
            throw new java.lang.IllegalArgumentException("can't stop LockTask for null task");
        }
        if (i != task.mLockTaskUid && (task.mLockTaskUid != 0 || i != task.effectiveUid)) {
            throw new java.lang.SecurityException("Invalid uid, expected " + task.mLockTaskUid + " callingUid=" + i + " effectiveUid=" + task.effectiveUid);
        }
        clearLockedTask(task);
    }

    void clearLockedTasks(java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 8891808212671675155L, 0, null, java.lang.String.valueOf(str));
        if (!this.mLockTaskModeTasks.isEmpty()) {
            clearLockedTask(this.mLockTaskModeTasks.get(0));
        }
    }

    void clearLockedTask(com.android.server.wm.Task task) {
        if (task == null || this.mLockTaskModeTasks.isEmpty()) {
            return;
        }
        if (task == this.mLockTaskModeTasks.get(0)) {
            for (int size = this.mLockTaskModeTasks.size() - 1; size > 0; size--) {
                clearLockedTask(this.mLockTaskModeTasks.get(size));
            }
        }
        removeLockedTask(task);
        if (this.mLockTaskModeTasks.isEmpty()) {
            return;
        }
        task.performClearTaskForReuse(false);
        this.mSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    private void removeLockedTask(final com.android.server.wm.Task task) {
        if (!this.mLockTaskModeTasks.remove(task)) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 8970634498594714645L, 0, null, java.lang.String.valueOf(task));
        if (this.mLockTaskModeTasks.isEmpty()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 8735562128135241598L, 0, null, java.lang.String.valueOf(task), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.LockTaskController.this.lambda$removeLockedTask$2(task);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeLockedTask$2(com.android.server.wm.Task task) {
        performStopLockTask(task.mUserId);
    }

    private void performStopLockTask(int i) {
        com.android.internal.statusbar.IStatusBarService statusBarService;
        int i2 = this.mLockTaskModeState;
        this.mLockTaskModeState = 0;
        this.mTaskChangeNotificationController.notifyLockTaskModeChanged(this.mLockTaskModeState);
        try {
            setStatusBarState(this.mLockTaskModeState, i);
            setKeyguardState(this.mLockTaskModeState, i);
            if (i2 == 2) {
                lockKeyguardIfNeeded(i);
            }
            if (getDevicePolicyManager() != null) {
                getDevicePolicyManager().notifyLockTaskModeChanged(false, (java.lang.String) null, i);
            }
            if (i2 == 2 && (statusBarService = getStatusBarService()) != null) {
                statusBarService.showPinningEnterExitToast(false);
            }
            this.mWindowManager.onLockTaskStateChanged(this.mLockTaskModeState);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    void showLockTaskToast() {
        if (this.mLockTaskModeState == 2) {
            try {
                com.android.internal.statusbar.IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    statusBarService.showPinningEscapeToast();
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e("ActivityTaskManager", "Failed to send pinning escape toast", e);
            }
        }
    }

    void startLockTaskMode(@android.annotation.NonNull com.android.server.wm.Task task, boolean z, int i) {
        if (task.mLockTaskAuth == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 737192738184050156L, 0, null, null);
            return;
        }
        if (!z) {
            task.mLockTaskUid = i;
            if (task.mLockTaskAuth != 1) {
                if (this.mLockTaskModeState == 2) {
                    android.util.Slog.i("ActivityTaskManager", "Stop app pinning before entering full lock task mode");
                    stopLockTaskMode(null, true, i);
                }
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -7119521978513736788L, 0, null, null);
                com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
                if (statusBarManagerInternal != null) {
                    statusBarManagerInternal.showScreenPinningRequest(task.mTaskId);
                    return;
                }
                return;
            }
        }
        this.mSupervisor.mRootWindowContainer.removeRootTasksInWindowingModes(2);
        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -1557441750657584614L, 0, null, java.lang.String.valueOf(z ? "Locking pinned" : "Locking fully"));
        setLockTaskMode(task, z ? 2 : 1, "startLockTask", true);
    }

    private void setLockTaskMode(@android.annotation.NonNull final com.android.server.wm.Task task, final int i, java.lang.String str, boolean z) {
        if (task.mLockTaskAuth == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -4314079913933391851L, 0, null, null);
            return;
        }
        if (isLockTaskModeViolation(task)) {
            android.util.Slog.e("ActivityTaskManager", "setLockTaskMode: Attempt to start an unauthorized lock task.");
            return;
        }
        final android.content.Intent intent = task.intent;
        if (this.mLockTaskModeTasks.isEmpty() && intent != null) {
            this.mSupervisor.mRecentTasks.onLockTaskModeStateChanged(i, task.mUserId);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.LockTaskController.this.lambda$setLockTaskMode$3(intent, task, i);
                }
            });
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 3321878763832425380L, 0, null, java.lang.String.valueOf(task), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
        if (!this.mLockTaskModeTasks.contains(task)) {
            this.mLockTaskModeTasks.add(task);
        }
        if (task.mLockTaskUid == -1) {
            task.mLockTaskUid = task.effectiveUid;
        }
        if (!z) {
            if (i != 0) {
                this.mSupervisor.handleNonResizableTaskIfNeeded(task, 0, this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea(), task.getRootTask(), true);
            }
        } else {
            this.mSupervisor.findTaskToMoveToFront(task, 0, null, str, i != 0);
            this.mSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
            com.android.server.wm.Task rootTask = task.getRootTask();
            if (rootTask != null) {
                rootTask.mDisplayContent.executeAppTransition();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLockTaskMode$3(android.content.Intent intent, com.android.server.wm.Task task, int i) {
        performStartLockTask(intent.getComponent().getPackageName(), task.mUserId, i);
    }

    private void performStartLockTask(java.lang.String str, int i, int i2) {
        if (i2 == 2) {
            try {
                com.android.internal.statusbar.IStatusBarService statusBarService = getStatusBarService();
                if (statusBarService != null) {
                    statusBarService.showPinningEnterExitToast(true);
                }
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        this.mWindowManager.onLockTaskStateChanged(i2);
        this.mLockTaskModeState = i2;
        this.mTaskChangeNotificationController.notifyLockTaskModeChanged(this.mLockTaskModeState);
        setStatusBarState(i2, i);
        setKeyguardState(i2, i);
        if (getDevicePolicyManager() != null) {
            getDevicePolicyManager().notifyLockTaskModeChanged(true, str, i);
        }
    }

    void updateLockTaskPackages(int i, java.lang.String[] strArr) {
        this.mLockTaskPackages.put(i, strArr);
        boolean z = true;
        boolean z2 = false;
        for (int size = this.mLockTaskModeTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mLockTaskModeTasks.get(size);
            boolean z3 = task.mLockTaskAuth == 2 || task.mLockTaskAuth == 3;
            task.setLockTaskAuth();
            boolean z4 = task.mLockTaskAuth == 2 || task.mLockTaskAuth == 3;
            if (this.mLockTaskModeState == 1 && task.mUserId == i && z3 && !z4) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -4819015209006579825L, 0, null, java.lang.String.valueOf(task), java.lang.String.valueOf(task.lockTaskAuthToString()));
                removeLockedTask(task);
                task.performClearTaskForReuse(false);
                z2 = true;
            }
        }
        this.mSupervisor.mRootWindowContainer.forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.Task) obj).setLockTaskAuth();
            }
        });
        com.android.server.wm.ActivityRecord activityRecord = this.mSupervisor.mRootWindowContainer.topRunningActivity();
        com.android.server.wm.Task task2 = activityRecord != null ? activityRecord.getTask() : null;
        if (this.mLockTaskModeTasks.isEmpty() && task2 != null && task2.mLockTaskAuth == 2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 2119751067469297845L, 0, null, java.lang.String.valueOf(task2));
            setLockTaskMode(task2, 1, "package updated", false);
        } else {
            z = z2;
        }
        if (z) {
            this.mSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
    }

    int getLockTaskAuth(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task) {
        java.lang.String str;
        if (activityRecord == null && task == null) {
            return 0;
        }
        if (activityRecord == null) {
            return 1;
        }
        if (task == null || task.realActivity == null) {
            str = activityRecord.packageName;
        } else {
            str = task.realActivity.getPackageName();
        }
        int i = task != null ? task.mUserId : activityRecord.mUserId;
        switch (activityRecord.lockTaskLaunchMode) {
            case 0:
                if (!isPackageAllowlisted(i, str)) {
                    return 1;
                }
                return 3;
            case 1:
            default:
                return 0;
            case 2:
                return 4;
            case 3:
                if (!isPackageAllowlisted(i, str)) {
                    return 1;
                }
                return 2;
        }
    }

    boolean isPackageAllowlisted(int i, java.lang.String str) {
        java.lang.String[] strArr;
        if (str == null || (strArr = this.mLockTaskPackages.get(i)) == null) {
            return false;
        }
        for (java.lang.String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    void updateLockTaskFeatures(final int i, int i2) {
        if (i2 == getLockTaskFeaturesForUser(i)) {
            return;
        }
        this.mLockTaskFeatures.put(i, i2);
        if (!this.mLockTaskModeTasks.isEmpty() && i == this.mLockTaskModeTasks.get(0).mUserId) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.LockTaskController.this.lambda$updateLockTaskFeatures$4(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLockTaskFeatures$4(int i) {
        if (this.mLockTaskModeState == 1) {
            setStatusBarState(this.mLockTaskModeState, i);
            setKeyguardState(this.mLockTaskModeState, i);
        }
    }

    private void setStatusBarState(int i, int i2) {
        int i3;
        com.android.internal.statusbar.IStatusBarService statusBarService = getStatusBarService();
        if (statusBarService == null) {
            android.util.Slog.e("ActivityTaskManager", "Can't find StatusBarService");
            return;
        }
        int i4 = 0;
        if (i == 2) {
            i4 = STATUS_BAR_MASK_PINNED;
            i3 = 0;
        } else if (i != 1) {
            i3 = 0;
        } else {
            android.util.Pair<java.lang.Integer, java.lang.Integer> statusBarDisableFlags = getStatusBarDisableFlags(getLockTaskFeaturesForUser(i2));
            i4 = ((java.lang.Integer) statusBarDisableFlags.first).intValue();
            i3 = ((java.lang.Integer) statusBarDisableFlags.second).intValue();
        }
        try {
            statusBarService.disable(i4, this.mToken, this.mContext.getPackageName());
            statusBarService.disable2(i3, this.mToken, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e("ActivityTaskManager", "Failed to set status bar flags", e);
        }
    }

    private void setKeyguardState(int i, int i2) {
        this.mPendingDisableFromDismiss = com.android.server.am.ProcessList.INVALID_ADJ;
        if (i == 0) {
            this.mWindowManager.reenableKeyguard(this.mToken, i2);
            return;
        }
        if (i == 1) {
            if (isKeyguardAllowed(i2)) {
                this.mWindowManager.reenableKeyguard(this.mToken, i2);
                return;
            } else if (this.mWindowManager.isKeyguardLocked() && !this.mWindowManager.isKeyguardSecure(i2)) {
                this.mPendingDisableFromDismiss = i2;
                this.mWindowManager.dismissKeyguard(new com.android.server.wm.LockTaskController.AnonymousClass1(i2), null);
                return;
            } else {
                this.mWindowManager.disableKeyguard(this.mToken, LOCK_TASK_TAG, i2);
                return;
            }
        }
        this.mWindowManager.disableKeyguard(this.mToken, LOCK_TASK_TAG, i2);
    }

    /* renamed from: com.android.server.wm.LockTaskController$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.policy.IKeyguardDismissCallback.Stub {
        final /* synthetic */ int val$userId;

        AnonymousClass1(int i) {
            this.val$userId = i;
        }

        public void onDismissError() throws android.os.RemoteException {
            android.util.Slog.i("ActivityTaskManager", "setKeyguardState: failed to dismiss keyguard");
        }

        public void onDismissSucceeded() throws android.os.RemoteException {
            android.os.Handler handler = com.android.server.wm.LockTaskController.this.mHandler;
            final int i = this.val$userId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.LockTaskController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.LockTaskController.AnonymousClass1.this.lambda$onDismissSucceeded$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDismissSucceeded$0(int i) {
            if (com.android.server.wm.LockTaskController.this.mPendingDisableFromDismiss == i) {
                com.android.server.wm.LockTaskController.this.mWindowManager.disableKeyguard(com.android.server.wm.LockTaskController.this.mToken, com.android.server.wm.LockTaskController.LOCK_TASK_TAG, i);
                com.android.server.wm.LockTaskController.this.mPendingDisableFromDismiss = com.android.server.am.ProcessList.INVALID_ADJ;
            }
        }

        public void onDismissCancelled() throws android.os.RemoteException {
            android.util.Slog.i("ActivityTaskManager", "setKeyguardState: dismiss cancelled");
        }
    }

    private void lockKeyguardIfNeeded(int i) {
        if (shouldLockKeyguard(i)) {
            this.mWindowManager.lockNow(null);
            this.mWindowManager.dismissKeyguard(null, null);
            getLockPatternUtils().requireCredentialEntry(-1);
        }
    }

    private boolean shouldLockKeyguard(int i) {
        try {
            return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_to_app_exit_locked", -2) != 0;
        } catch (android.provider.Settings.SettingNotFoundException e) {
            android.util.EventLog.writeEvent(1397638484, "127605586", -1, "");
            return getLockPatternUtils().isSecure(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.Pair<java.lang.Integer, java.lang.Integer> getStatusBarDisableFlags(int i) {
        int i2 = 134152192;
        int i3 = 31;
        for (int size = STATUS_BAR_FLAG_MAP_LOCKED.size() - 1; size >= 0; size--) {
            android.util.Pair<java.lang.Integer, java.lang.Integer> valueAt = STATUS_BAR_FLAG_MAP_LOCKED.valueAt(size);
            if ((STATUS_BAR_FLAG_MAP_LOCKED.keyAt(size) & i) != 0) {
                i2 &= ~((java.lang.Integer) valueAt.first).intValue();
                i3 &= ~((java.lang.Integer) valueAt.second).intValue();
            }
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(STATUS_BAR_MASK_LOCKED & i2), java.lang.Integer.valueOf(i3));
    }

    boolean isBaseOfLockedTask(java.lang.String str) {
        for (int i = 0; i < this.mLockTaskModeTasks.size(); i++) {
            if (str.equals(this.mLockTaskModeTasks.get(i).getBasePackageName())) {
                return true;
            }
        }
        return false;
    }

    private int getLockTaskFeaturesForUser(int i) {
        return this.mLockTaskFeatures.get(i, 0);
    }

    @android.annotation.Nullable
    private com.android.internal.statusbar.IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            this.mStatusBarService = com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.checkService("statusbar"));
            if (this.mStatusBarService == null) {
                android.util.Slog.w("StatusBarManager", "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mStatusBarService;
    }

    @android.annotation.Nullable
    private android.app.admin.IDevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = android.app.admin.IDevicePolicyManager.Stub.asInterface(android.os.ServiceManager.checkService("device_policy"));
            if (this.mDevicePolicyManager == null) {
                android.util.Slog.w("ActivityTaskManager", "warning: no DEVICE_POLICY_SERVICE");
            }
        }
        return this.mDevicePolicyManager;
    }

    @android.annotation.NonNull
    private com.android.internal.widget.LockPatternUtils getLockPatternUtils() {
        if (this.mLockPatternUtils == null) {
            return new com.android.internal.widget.LockPatternUtils(this.mContext);
        }
        return this.mLockPatternUtils;
    }

    @android.annotation.Nullable
    private android.telecom.TelecomManager getTelecomManager() {
        if (this.mTelecomManager == null) {
            return (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class);
        }
        return this.mTelecomManager;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "LockTaskController:");
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mLockTaskModeState=" + lockTaskModeToString());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str2);
        sb.append("mLockTaskModeTasks=");
        printWriter.println(sb.toString());
        for (int i = 0; i < this.mLockTaskModeTasks.size(); i++) {
            printWriter.println(str2 + "  #" + i + " " + this.mLockTaskModeTasks.get(i));
        }
        printWriter.println(str2 + "mLockTaskPackages (userId:packages)=");
        for (int i2 = 0; i2 < this.mLockTaskPackages.size(); i2++) {
            printWriter.println(str2 + "  u" + this.mLockTaskPackages.keyAt(i2) + ":" + java.util.Arrays.toString(this.mLockTaskPackages.valueAt(i2)));
        }
        printWriter.println();
    }

    private java.lang.String lockTaskModeToString() {
        switch (this.mLockTaskModeState) {
            case 0:
                return "NONE";
            case 1:
                return "LOCKED";
            case 2:
                return "PINNED";
            default:
                return "unknown=" + this.mLockTaskModeState;
        }
    }

    static class LockTaskToken extends android.os.Binder {
        private LockTaskToken() {
        }
    }
}
