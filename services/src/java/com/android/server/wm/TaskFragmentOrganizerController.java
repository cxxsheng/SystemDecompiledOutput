package com.android.server.wm;

/* loaded from: classes3.dex */
public class TaskFragmentOrganizerController extends android.window.ITaskFragmentOrganizerController.Stub {
    private static final java.lang.String TAG = "TaskFragmentOrganizerController";
    private static final long TEMPORARY_ACTIVITY_TOKEN_TIMEOUT_MS = 5000;
    private final com.android.server.wm.ActivityTaskManagerService mAtmService;
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final com.android.server.wm.WindowOrganizerController mWindowOrganizerController;
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState> mTaskFragmentOrganizerState = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.os.IBinder, java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent>> mPendingTaskFragmentEvents = new android.util.ArrayMap<>();
    private final android.util.ArraySet<com.android.server.wm.Task> mTmpTaskSet = new android.util.ArraySet<>();

    TaskFragmentOrganizerController(@android.annotation.NonNull com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, @android.annotation.NonNull com.android.server.wm.WindowOrganizerController windowOrganizerController) {
        java.util.Objects.requireNonNull(activityTaskManagerService);
        this.mAtmService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        java.util.Objects.requireNonNull(windowOrganizerController);
        this.mWindowOrganizerController = windowOrganizerController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TaskFragmentOrganizerState implements android.os.IBinder.DeathRecipient {
        private final android.app.IApplicationThread mAppThread;
        private final boolean mIsSystemOrganizer;
        private final android.window.ITaskFragmentOrganizer mOrganizer;
        private final int mOrganizerPid;
        private final int mOrganizerUid;

        @android.annotation.Nullable
        private android.view.RemoteAnimationDefinition mRemoteAnimationDefinition;
        private final java.util.ArrayList<com.android.server.wm.TaskFragment> mOrganizedTaskFragments = new java.util.ArrayList<>();
        private final java.util.Map<com.android.server.wm.TaskFragment, android.window.TaskFragmentInfo> mLastSentTaskFragmentInfos = new java.util.WeakHashMap();
        private final java.util.Map<com.android.server.wm.TaskFragment, java.lang.Integer> mTaskFragmentTaskIds = new java.util.WeakHashMap();
        private final android.util.SparseArray<android.window.TaskFragmentParentInfo> mLastSentTaskFragmentParentInfos = new android.util.SparseArray<>();
        private final java.util.Map<android.os.IBinder, com.android.server.wm.ActivityRecord> mTemporaryActivityTokens = new java.util.WeakHashMap();
        private final android.util.ArrayMap<android.os.IBinder, java.lang.Integer> mDeferredTransitions = new android.util.ArrayMap<>();
        private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.Transition.ReadyCondition> mInFlightTransactions = new android.util.ArrayMap<>();

        TaskFragmentOrganizerState(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, int i, int i2, boolean z) {
            if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
                this.mAppThread = com.android.server.wm.TaskFragmentOrganizerController.this.getAppThread(i, i2);
            } else {
                this.mAppThread = null;
            }
            this.mOrganizer = iTaskFragmentOrganizer;
            this.mOrganizerPid = i;
            this.mOrganizerUid = i2;
            this.mIsSystemOrganizer = z;
            try {
                this.mOrganizer.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.wm.TaskFragmentOrganizerController.TAG, "TaskFragmentOrganizer failed to register death recipient");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.TaskFragmentOrganizerController.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.TaskFragmentOrganizerController.this.removeOrganizer(this.mOrganizer, "client died");
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        boolean addTaskFragment(com.android.server.wm.TaskFragment taskFragment) {
            if (taskFragment.mTaskFragmentAppearedSent || this.mOrganizedTaskFragments.contains(taskFragment)) {
                return false;
            }
            this.mOrganizedTaskFragments.add(taskFragment);
            return true;
        }

        void removeTaskFragment(com.android.server.wm.TaskFragment taskFragment) {
            this.mOrganizedTaskFragments.remove(taskFragment);
        }

        void dispose(@android.annotation.NonNull java.lang.String str) {
            boolean z = false;
            for (int size = this.mOrganizedTaskFragments.size() - 1; size >= 0; size--) {
                com.android.server.wm.TaskFragment taskFragment = this.mOrganizedTaskFragments.get(size);
                if (taskFragment.isVisibleRequested()) {
                    z = true;
                }
                taskFragment.onTaskFragmentOrganizerRemoved();
            }
            com.android.server.wm.TransitionController transitionController = com.android.server.wm.TaskFragmentOrganizerController.this.mAtmService.getTransitionController();
            if (z && transitionController.isShellTransitionsEnabled() && !transitionController.isCollecting()) {
                com.android.server.wm.Task task = this.mOrganizedTaskFragments.get(0).getTask();
                boolean z2 = (task == null || task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragmentOrganizerController$TaskFragmentOrganizerState$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$dispose$0;
                        lambda$dispose$0 = com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState.lambda$dispose$0((com.android.server.wm.ActivityRecord) obj);
                        return lambda$dispose$0;
                    }
                }) == null) ? false : true;
                com.android.server.wm.Transition createTransition = transitionController.createTransition(2);
                if (z2) {
                    task = null;
                }
                transitionController.requestStartTransition(createTransition, task, null, null);
            }
            com.android.server.wm.TaskFragmentOrganizerController.this.mAtmService.deferWindowLayout();
            while (!this.mOrganizedTaskFragments.isEmpty()) {
                try {
                    this.mOrganizedTaskFragments.remove(0).removeImmediately();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.TaskFragmentOrganizerController.this.mAtmService.continueWindowLayout();
                    throw th;
                }
            }
            com.android.server.wm.TaskFragmentOrganizerController.this.mAtmService.continueWindowLayout();
            for (int size2 = this.mDeferredTransitions.size() - 1; size2 >= 0; size2--) {
                onTransactionFinished(this.mDeferredTransitions.keyAt(size2));
            }
            for (int size3 = this.mInFlightTransactions.size() - 1; size3 >= 0; size3 += -1) {
                this.mInFlightTransactions.valueAt(size3).meetAlternate("disposed(" + str + ")");
            }
            this.mOrganizer.asBinder().unlinkToDeath(this, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$dispose$0(com.android.server.wm.ActivityRecord activityRecord) {
            return !activityRecord.isEmbedded();
        }

        @android.annotation.NonNull
        android.window.TaskFragmentTransaction.Change prepareTaskFragmentAppeared(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -2808577027789344626L, 0, null, java.lang.String.valueOf(taskFragment.getName()));
            android.window.TaskFragmentInfo taskFragmentInfo = taskFragment.getTaskFragmentInfo();
            int i = taskFragment.getTask().mTaskId;
            taskFragment.mTaskFragmentAppearedSent = true;
            this.mLastSentTaskFragmentInfos.put(taskFragment, taskFragmentInfo);
            this.mTaskFragmentTaskIds.put(taskFragment, java.lang.Integer.valueOf(i));
            android.window.TaskFragmentTransaction.Change taskId = new android.window.TaskFragmentTransaction.Change(1).setTaskFragmentToken(taskFragment.getFragmentToken()).setTaskFragmentInfo(taskFragmentInfo).setTaskId(i);
            if (this.mIsSystemOrganizer) {
                taskId.setTaskFragmentSurfaceControl(taskFragment.getSurfaceControl());
            }
            return taskId;
        }

        @android.annotation.NonNull
        android.window.TaskFragmentTransaction.Change prepareTaskFragmentVanished(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
            int i;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -3582112419663037270L, 0, null, java.lang.String.valueOf(taskFragment.getName()));
            taskFragment.mTaskFragmentAppearedSent = false;
            this.mLastSentTaskFragmentInfos.remove(taskFragment);
            if (this.mTaskFragmentTaskIds.containsKey(taskFragment)) {
                i = this.mTaskFragmentTaskIds.remove(taskFragment).intValue();
                if (!this.mTaskFragmentTaskIds.containsValue(java.lang.Integer.valueOf(i))) {
                    this.mLastSentTaskFragmentParentInfos.remove(i);
                }
            } else {
                i = -1;
            }
            return new android.window.TaskFragmentTransaction.Change(3).setTaskFragmentToken(taskFragment.getFragmentToken()).setTaskFragmentInfo(taskFragment.getTaskFragmentInfo()).setTaskId(i);
        }

        @android.annotation.Nullable
        android.window.TaskFragmentTransaction.Change prepareTaskFragmentInfoChanged(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
            android.window.TaskFragmentInfo taskFragmentInfo = taskFragment.getTaskFragmentInfo();
            android.window.TaskFragmentInfo taskFragmentInfo2 = this.mLastSentTaskFragmentInfos.get(taskFragment);
            if (taskFragmentInfo.equalsForTaskFragmentOrganizer(taskFragmentInfo2) && com.android.server.wm.WindowOrganizerController.configurationsAreEqualForOrganizer(taskFragmentInfo.getConfiguration(), taskFragmentInfo2.getConfiguration())) {
                return null;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 3294593748816836746L, 0, null, java.lang.String.valueOf(taskFragment.getName()));
            this.mLastSentTaskFragmentInfos.put(taskFragment, taskFragmentInfo);
            return new android.window.TaskFragmentTransaction.Change(2).setTaskFragmentToken(taskFragment.getFragmentToken()).setTaskFragmentInfo(taskFragmentInfo).setTaskId(taskFragment.getTask().mTaskId);
        }

        @android.annotation.Nullable
        android.window.TaskFragmentTransaction.Change prepareTaskFragmentParentInfoChanged(@android.annotation.NonNull com.android.server.wm.Task task) {
            android.content.res.Configuration configuration;
            int i = task.mTaskId;
            android.window.TaskFragmentParentInfo taskFragmentParentInfo = task.getTaskFragmentParentInfo();
            android.window.TaskFragmentParentInfo taskFragmentParentInfo2 = this.mLastSentTaskFragmentParentInfos.get(i);
            if (taskFragmentParentInfo2 == null) {
                configuration = null;
            } else {
                configuration = taskFragmentParentInfo2.getConfiguration();
            }
            if (taskFragmentParentInfo.equalsForTaskFragmentOrganizer(taskFragmentParentInfo2) && com.android.server.wm.WindowOrganizerController.configurationsAreEqualForOrganizer(taskFragmentParentInfo.getConfiguration(), configuration)) {
                return null;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 5007230330523630579L, 4, null, java.lang.String.valueOf(task.getName()), java.lang.Long.valueOf(i));
            this.mLastSentTaskFragmentParentInfos.put(i, new android.window.TaskFragmentParentInfo(taskFragmentParentInfo));
            return new android.window.TaskFragmentTransaction.Change(4).setTaskId(i).setTaskFragmentParentInfo(taskFragmentParentInfo);
        }

        @android.annotation.NonNull
        android.window.TaskFragmentTransaction.Change prepareTaskFragmentError(@android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, int i, @android.annotation.NonNull java.lang.Throwable th) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 6475066005515810081L, 0, null, java.lang.String.valueOf(th.toString()));
            return new android.window.TaskFragmentTransaction.Change(5).setErrorCallbackToken(iBinder).setErrorBundle(android.window.TaskFragmentOrganizer.putErrorInfoInBundle(th, taskFragment != null ? taskFragment.getTaskFragmentInfo() : null, i));
        }

        @android.annotation.Nullable
        android.window.TaskFragmentTransaction.Change prepareActivityReparentedToTask(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
            final android.os.IBinder binder;
            if (activityRecord.finishing) {
                android.util.Slog.d(com.android.server.wm.TaskFragmentOrganizerController.TAG, "Reparent activity=" + activityRecord.token + " is finishing");
                return null;
            }
            com.android.server.wm.Task task = activityRecord.getTask();
            if (task == null || task.effectiveUid != this.mOrganizerUid) {
                android.util.Slog.d(com.android.server.wm.TaskFragmentOrganizerController.TAG, "Reparent activity=" + activityRecord.token + " is not in a task belong to the organizer app.");
                return null;
            }
            if (task.isAllowedToEmbedActivity(activityRecord, this.mOrganizerUid) != 0) {
                android.util.Slog.d(com.android.server.wm.TaskFragmentOrganizerController.TAG, "Reparent activity=" + activityRecord.token + " is not allowed to be embedded.");
                return null;
            }
            if (!task.isAllowedToEmbedActivityInTrustedMode(activityRecord, this.mOrganizerUid) && !activityRecord.isUntrustedEmbeddingStateSharingAllowed()) {
                android.util.Slog.d(com.android.server.wm.TaskFragmentOrganizerController.TAG, "Reparent activity=" + activityRecord.token + " is not allowed to be shared with untrusted host.");
                return null;
            }
            if (activityRecord.getPid() == this.mOrganizerPid) {
                binder = activityRecord.token;
            } else {
                binder = new android.os.Binder("TemporaryActivityToken");
                this.mTemporaryActivityTokens.put(binder, activityRecord);
                com.android.server.wm.TaskFragmentOrganizerController.this.mAtmService.mWindowManager.mH.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.TaskFragmentOrganizerController$TaskFragmentOrganizerState$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState.this.lambda$prepareActivityReparentedToTask$1(binder);
                    }
                }, com.android.server.wm.TaskFragmentOrganizerController.TEMPORARY_ACTIVITY_TOKEN_TIMEOUT_MS);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -7893265697482064583L, 4, null, java.lang.String.valueOf(activityRecord.token), java.lang.Long.valueOf(task.mTaskId));
            return new android.window.TaskFragmentTransaction.Change(6).setTaskId(task.mTaskId).setActivityIntent(com.android.server.wm.TaskFragmentOrganizerController.trimIntent(activityRecord.intent)).setActivityToken(binder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$prepareActivityReparentedToTask$1(android.os.IBinder iBinder) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.TaskFragmentOrganizerController.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mTemporaryActivityTokens.remove(iBinder);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        void dispatchTransaction(@android.annotation.NonNull android.window.TaskFragmentTransaction taskFragmentTransaction) {
            if (taskFragmentTransaction.isEmpty()) {
                return;
            }
            try {
                if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
                    this.mAppThread.scheduleTaskFragmentTransaction(this.mOrganizer, taskFragmentTransaction);
                } else {
                    this.mOrganizer.onTransactionReady(taskFragmentTransaction);
                }
                if (!com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().isCollecting()) {
                    return;
                }
                int collectingTransitionId = com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().getCollectingTransitionId();
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 7048981249808281819L, 1, null, java.lang.Long.valueOf(collectingTransitionId), java.lang.String.valueOf(taskFragmentTransaction.getTransactionToken()));
                this.mDeferredTransitions.put(taskFragmentTransaction.getTransactionToken(), java.lang.Integer.valueOf(collectingTransitionId));
                com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().deferTransitionReady();
                com.android.server.wm.Transition.ReadyCondition readyCondition = new com.android.server.wm.Transition.ReadyCondition("task-fragment transaction", taskFragmentTransaction);
                com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().waitFor(readyCondition);
                this.mInFlightTransactions.put(taskFragmentTransaction.getTransactionToken(), readyCondition);
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(com.android.server.wm.TaskFragmentOrganizerController.TAG, "Exception sending TaskFragmentTransaction", e);
            }
        }

        void onTransactionFinished(@android.annotation.NonNull android.os.IBinder iBinder) {
            if (!this.mDeferredTransitions.containsKey(iBinder)) {
                return;
            }
            int intValue = this.mDeferredTransitions.remove(iBinder).intValue();
            if (!com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().isCollecting() || com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().getCollectingTransitionId() != intValue) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1315509853595025526L, 1, null, java.lang.Long.valueOf(intValue), java.lang.String.valueOf(iBinder));
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 7421521217481553621L, 1, null, java.lang.Long.valueOf(intValue), java.lang.String.valueOf(iBinder));
                com.android.server.wm.TaskFragmentOrganizerController.this.mWindowOrganizerController.getTransitionController().continueTransitionReady();
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getReparentActivityFromTemporaryToken(@android.annotation.Nullable android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.Nullable android.os.IBinder iBinder) {
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState;
        if (iTaskFragmentOrganizer == null || iBinder == null || (taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder())) == null) {
            return null;
        }
        return (com.android.server.wm.ActivityRecord) taskFragmentOrganizerState.mTemporaryActivityTokens.remove(iBinder);
    }

    public void registerOrganizer(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) {
        registerOrganizerInternal(iTaskFragmentOrganizer, com.android.window.flags.Flags.taskFragmentSystemOrganizerFlag() && z);
    }

    private void registerOrganizerInternal(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z) {
        if (z) {
            com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("registerSystemOrganizer()");
        }
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 3509684748201636981L, 20, null, java.lang.String.valueOf(iTaskFragmentOrganizer.asBinder()), java.lang.Long.valueOf(callingUid), java.lang.Long.valueOf(callingPid));
                if (isOrganizerRegistered(iTaskFragmentOrganizer)) {
                    throw new java.lang.IllegalStateException("Replacing existing organizer currently unsupported");
                }
                this.mTaskFragmentOrganizerState.put(iTaskFragmentOrganizer.asBinder(), new com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState(iTaskFragmentOrganizer, callingPid, callingUid, z));
                this.mPendingTaskFragmentEvents.put(iTaskFragmentOrganizer.asBinder(), new java.util.ArrayList());
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterOrganizer(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        int callingPid = android.os.Binder.getCallingPid();
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -6777461169027010201L, 20, null, java.lang.String.valueOf(iTaskFragmentOrganizer.asBinder()), java.lang.Long.valueOf(callingUid), java.lang.Long.valueOf(callingPid));
                    removeOrganizer(iTaskFragmentOrganizer, "unregistered");
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerRemoteAnimations(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 1327792561585467865L, 20, null, java.lang.String.valueOf(iTaskFragmentOrganizer.asBinder()), java.lang.Long.valueOf(callingUid), java.lang.Long.valueOf(callingPid));
                com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
                if (taskFragmentOrganizerState == null) {
                    throw new java.lang.IllegalStateException("The organizer hasn't been registered.");
                }
                if (taskFragmentOrganizerState.mRemoteAnimationDefinition != null) {
                    throw new java.lang.IllegalStateException("The organizer has already registered remote animations=" + taskFragmentOrganizerState.mRemoteAnimationDefinition);
                }
                remoteAnimationDefinition.setCallingPidUid(callingPid, callingUid);
                taskFragmentOrganizerState.mRemoteAnimationDefinition = remoteAnimationDefinition;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterRemoteAnimations(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        int callingPid = android.os.Binder.getCallingPid();
        long callingUid = android.os.Binder.getCallingUid();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -2524361347368208519L, 20, null, java.lang.String.valueOf(iTaskFragmentOrganizer.asBinder()), java.lang.Long.valueOf(callingUid), java.lang.Long.valueOf(callingPid));
                com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
                if (taskFragmentOrganizerState == null) {
                    android.util.Slog.e(TAG, "The organizer hasn't been registered.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    taskFragmentOrganizerState.mRemoteAnimationDefinition = null;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void onTransactionHandled(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (isValidTransaction(windowContainerTransaction)) {
                    applyTransaction(windowContainerTransaction, i, z, null);
                }
                android.window.ITaskFragmentOrganizer taskFragmentOrganizer = windowContainerTransaction.getTaskFragmentOrganizer();
                com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = taskFragmentOrganizer != null ? this.mTaskFragmentOrganizerState.get(taskFragmentOrganizer.asBinder()) : null;
                if (taskFragmentOrganizerState != null) {
                    taskFragmentOrganizerState.onTransactionFinished(iBinder);
                    com.android.server.wm.Transition.ReadyCondition readyCondition = (com.android.server.wm.Transition.ReadyCondition) taskFragmentOrganizerState.mInFlightTransactions.remove(iBinder);
                    if (readyCondition != null) {
                        readyCondition.meet();
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void applyTransaction(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z, @android.annotation.Nullable android.window.RemoteTransition remoteTransition) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!isValidTransaction(windowContainerTransaction)) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mWindowOrganizerController.applyTaskFragmentTransactionLocked(windowContainerTransaction, i, z, remoteTransition);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.view.RemoteAnimationDefinition getRemoteAnimationDefinition(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
                if (taskFragmentOrganizerState == null) {
                    android.util.Slog.e(TAG, "TaskFragmentOrganizer has been unregistered or died when trying to play animation on its organized windows.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.view.RemoteAnimationDefinition remoteAnimationDefinition = taskFragmentOrganizerState.mRemoteAnimationDefinition;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return remoteAnimationDefinition;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    int getTaskFragmentOrganizerUid(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        return validateAndGetState(iTaskFragmentOrganizer).mOrganizerUid;
    }

    void onTaskFragmentAppeared(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
        if (taskFragment.mTaskFragmentVanishedSent) {
            return;
        }
        if (taskFragment.getTask() == null) {
            android.util.Slog.w(TAG, "onTaskFragmentAppeared failed because it is not attached tf=" + taskFragment);
            return;
        }
        if (validateAndGetState(iTaskFragmentOrganizer).addTaskFragment(taskFragment) && getPendingTaskFragmentEvent(taskFragment, 0) == null) {
            addPendingEvent(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(0, iTaskFragmentOrganizer).setTaskFragment(taskFragment).build());
        }
    }

    void onTaskFragmentInfoChanged(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
        if (taskFragment.mTaskFragmentVanishedSent) {
            return;
        }
        validateAndGetState(iTaskFragmentOrganizer);
        if (!taskFragment.mTaskFragmentAppearedSent) {
            return;
        }
        com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent lastPendingLifecycleEvent = getLastPendingLifecycleEvent(taskFragment);
        if (lastPendingLifecycleEvent == null) {
            lastPendingLifecycleEvent = new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(2, iTaskFragmentOrganizer).setTaskFragment(taskFragment).build();
        } else {
            removePendingEvent(lastPendingLifecycleEvent);
            lastPendingLifecycleEvent.mDeferTime = 0L;
        }
        addPendingEvent(lastPendingLifecycleEvent);
    }

    void onTaskFragmentVanished(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
        if (taskFragment.mTaskFragmentVanishedSent) {
            return;
        }
        taskFragment.mTaskFragmentVanishedSent = true;
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState validateAndGetState = validateAndGetState(iTaskFragmentOrganizer);
        java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list = this.mPendingTaskFragmentEvents.get(iTaskFragmentOrganizer.asBinder());
        for (int size = list.size() - 1; size >= 0; size--) {
            if (taskFragment == list.get(size).mTaskFragment) {
                list.remove(size);
            }
        }
        addPendingEvent(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(1, iTaskFragmentOrganizer).setTaskFragment(taskFragment).build());
        validateAndGetState.removeTaskFragment(taskFragment);
        this.mAtmService.mWindowManager.mWindowPlacerLocked.requestTraversal();
    }

    void onTaskFragmentError(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, int i, @android.annotation.NonNull java.lang.Throwable th) {
        if (taskFragment != null && taskFragment.mTaskFragmentVanishedSent) {
            return;
        }
        validateAndGetState(iTaskFragmentOrganizer);
        android.util.Slog.w(TAG, "onTaskFragmentError ", th);
        addPendingEvent(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(4, iTaskFragmentOrganizer).setErrorCallbackToken(iBinder).setTaskFragment(taskFragment).setException(th).setOpType(i).build());
        this.mAtmService.mWindowManager.mWindowPlacerLocked.requestTraversal();
    }

    void onActivityReparentedToTask(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        android.window.ITaskFragmentOrganizer taskFragmentOrganizer;
        if (activityRecord.mLastTaskFragmentOrganizerBeforePip != null) {
            taskFragmentOrganizer = activityRecord.mLastTaskFragmentOrganizerBeforePip;
        } else {
            final com.android.server.wm.TaskFragment[] taskFragmentArr = new com.android.server.wm.TaskFragment[1];
            activityRecord.getTask().forAllLeafTaskFragments(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskFragmentOrganizerController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onActivityReparentedToTask$0;
                    lambda$onActivityReparentedToTask$0 = com.android.server.wm.TaskFragmentOrganizerController.lambda$onActivityReparentedToTask$0(taskFragmentArr, (com.android.server.wm.TaskFragment) obj);
                    return lambda$onActivityReparentedToTask$0;
                }
            });
            if (taskFragmentArr[0] == null) {
                return;
            } else {
                taskFragmentOrganizer = taskFragmentArr[0].getTaskFragmentOrganizer();
            }
        }
        if (!isOrganizerRegistered(taskFragmentOrganizer)) {
            android.util.Slog.w(TAG, "The last TaskFragmentOrganizer no longer exists");
        } else {
            addPendingEvent(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(5, taskFragmentOrganizer).setActivity(activityRecord).build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onActivityReparentedToTask$0(com.android.server.wm.TaskFragment[] taskFragmentArr, com.android.server.wm.TaskFragment taskFragment) {
        if (!taskFragment.isOrganizedTaskFragment()) {
            return false;
        }
        taskFragmentArr[0] = taskFragment;
        return true;
    }

    void onTaskFragmentParentInfoChanged(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull com.android.server.wm.Task task) {
        validateAndGetState(iTaskFragmentOrganizer);
        if (getLastPendingParentInfoChangedEvent(iTaskFragmentOrganizer, task) == null) {
            addPendingEvent(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(3, iTaskFragmentOrganizer).setTask(task).build());
        }
        this.mAtmService.mWindowManager.mWindowPlacerLocked.requestTraversal();
    }

    boolean isSystemOrganizer(@android.annotation.NonNull android.os.IBinder iBinder) {
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iBinder);
        return taskFragmentOrganizerState != null && taskFragmentOrganizerState.mIsSystemOrganizer;
    }

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent getLastPendingParentInfoChangedEvent(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull com.android.server.wm.Task task) {
        java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list = this.mPendingTaskFragmentEvents.get(iTaskFragmentOrganizer.asBinder());
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = list.get(size);
            if (task == pendingTaskFragmentEvent.mTask && pendingTaskFragmentEvent.mEventType == 3) {
                return pendingTaskFragmentEvent;
            }
        }
        return null;
    }

    private void addPendingEvent(@android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        this.mPendingTaskFragmentEvents.get(pendingTaskFragmentEvent.mTaskFragmentOrg.asBinder()).add(pendingTaskFragmentEvent);
    }

    private void removePendingEvent(@android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        this.mPendingTaskFragmentEvents.get(pendingTaskFragmentEvent.mTaskFragmentOrg.asBinder()).remove(pendingTaskFragmentEvent);
    }

    private boolean isOrganizerRegistered(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        return this.mTaskFragmentOrganizerState.containsKey(iTaskFragmentOrganizer.asBinder());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOrganizer(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.NonNull java.lang.String str) {
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
        if (taskFragmentOrganizerState == null) {
            android.util.Slog.w(TAG, "The organizer has already been removed.");
            return;
        }
        this.mPendingTaskFragmentEvents.remove(iTaskFragmentOrganizer.asBinder());
        taskFragmentOrganizerState.dispose(str);
        this.mTaskFragmentOrganizerState.remove(iTaskFragmentOrganizer.asBinder());
    }

    @android.annotation.NonNull
    private com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState validateAndGetState(@android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
        if (taskFragmentOrganizerState == null) {
            throw new java.lang.IllegalArgumentException("TaskFragmentOrganizer has not been registered. Organizer=" + iTaskFragmentOrganizer);
        }
        return taskFragmentOrganizerState;
    }

    boolean isValidTransaction(@android.annotation.NonNull android.window.WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction.isEmpty()) {
            return false;
        }
        android.window.ITaskFragmentOrganizer taskFragmentOrganizer = windowContainerTransaction.getTaskFragmentOrganizer();
        if (windowContainerTransaction.getTaskFragmentOrganizer() == null || !isOrganizerRegistered(taskFragmentOrganizer)) {
            android.util.Slog.e(TAG, "Caller organizer=" + taskFragmentOrganizer + " is no longer registered");
            return false;
        }
        return true;
    }

    private static class PendingTaskFragmentEvent {
        static final int EVENT_ACTIVITY_REPARENTED_TO_TASK = 5;
        static final int EVENT_APPEARED = 0;
        static final int EVENT_ERROR = 4;
        static final int EVENT_INFO_CHANGED = 2;
        static final int EVENT_PARENT_INFO_CHANGED = 3;
        static final int EVENT_VANISHED = 1;

        @android.annotation.Nullable
        private final com.android.server.wm.ActivityRecord mActivity;
        private long mDeferTime;

        @android.annotation.Nullable
        private final android.os.IBinder mErrorCallbackToken;
        private final int mEventType;

        @android.annotation.Nullable
        private final java.lang.Throwable mException;
        private int mOpType;

        @android.annotation.Nullable
        private final com.android.server.wm.Task mTask;

        @android.annotation.Nullable
        private final com.android.server.wm.TaskFragment mTaskFragment;
        private final android.window.ITaskFragmentOrganizer mTaskFragmentOrg;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EventType {
        }

        private PendingTaskFragmentEvent(int i, android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, @android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable java.lang.Throwable th, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task, int i2) {
            this.mEventType = i;
            this.mTaskFragmentOrg = iTaskFragmentOrganizer;
            this.mTaskFragment = taskFragment;
            this.mErrorCallbackToken = iBinder;
            this.mException = th;
            this.mActivity = activityRecord;
            this.mTask = task;
            this.mOpType = i2;
        }

        boolean isLifecycleEvent() {
            switch (this.mEventType) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return true;
                default:
                    return false;
            }
        }

        private static class Builder {

            @android.annotation.Nullable
            private com.android.server.wm.ActivityRecord mActivity;

            @android.annotation.Nullable
            private android.os.IBinder mErrorCallbackToken;
            private final int mEventType;

            @android.annotation.Nullable
            private java.lang.Throwable mException;
            private int mOpType;

            @android.annotation.Nullable
            private com.android.server.wm.Task mTask;

            @android.annotation.Nullable
            private com.android.server.wm.TaskFragment mTaskFragment;
            private final android.window.ITaskFragmentOrganizer mTaskFragmentOrg;

            Builder(int i, @android.annotation.NonNull android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
                this.mEventType = i;
                java.util.Objects.requireNonNull(iTaskFragmentOrganizer);
                this.mTaskFragmentOrg = iTaskFragmentOrganizer;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setTaskFragment(@android.annotation.Nullable com.android.server.wm.TaskFragment taskFragment) {
                this.mTaskFragment = taskFragment;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setErrorCallbackToken(@android.annotation.Nullable android.os.IBinder iBinder) {
                this.mErrorCallbackToken = iBinder;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setException(@android.annotation.NonNull java.lang.Throwable th) {
                java.util.Objects.requireNonNull(th);
                this.mException = th;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setActivity(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
                java.util.Objects.requireNonNull(activityRecord);
                this.mActivity = activityRecord;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setTask(@android.annotation.NonNull com.android.server.wm.Task task) {
                java.util.Objects.requireNonNull(task);
                this.mTask = task;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder setOpType(int i) {
                this.mOpType = i;
                return this;
            }

            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent build() {
                return new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent(this.mEventType, this.mTaskFragmentOrg, this.mTaskFragment, this.mErrorCallbackToken, this.mException, this.mActivity, this.mTask, this.mOpType);
            }
        }
    }

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent getLastPendingLifecycleEvent(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
        java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list = this.mPendingTaskFragmentEvents.get(taskFragment.getTaskFragmentOrganizer().asBinder());
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = list.get(size);
            if (taskFragment == pendingTaskFragmentEvent.mTaskFragment && pendingTaskFragmentEvent.isLifecycleEvent()) {
                return pendingTaskFragmentEvent;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent getPendingTaskFragmentEvent(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment, int i) {
        java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list = this.mPendingTaskFragmentEvents.get(taskFragment.getTaskFragmentOrganizer().asBinder());
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = list.get(size);
            if (taskFragment == pendingTaskFragmentEvent.mTaskFragment && i == pendingTaskFragmentEvent.mEventType) {
                return pendingTaskFragmentEvent;
            }
        }
        return null;
    }

    void dispatchPendingEvents() {
        if (this.mAtmService.mWindowManager.mWindowPlacerLocked.isLayoutDeferred() || this.mPendingTaskFragmentEvents.isEmpty()) {
            return;
        }
        int size = this.mPendingTaskFragmentEvents.size();
        for (int i = 0; i < size; i++) {
            dispatchPendingEvents(this.mTaskFragmentOrganizerState.get(this.mPendingTaskFragmentEvents.keyAt(i)), this.mPendingTaskFragmentEvents.valueAt(i));
        }
    }

    private void dispatchPendingEvents(@android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState, @android.annotation.NonNull java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list) {
        if (list.isEmpty() || shouldDeferPendingEvents(taskFragmentOrganizerState, list)) {
            return;
        }
        this.mTmpTaskSet.clear();
        int size = list.size();
        android.window.TaskFragmentTransaction taskFragmentTransaction = new android.window.TaskFragmentTransaction();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = list.get(i);
            if (pendingTaskFragmentEvent.mEventType == 0 || pendingTaskFragmentEvent.mEventType == 2) {
                com.android.server.wm.Task task = pendingTaskFragmentEvent.mTaskFragment.getTask();
                if (this.mTmpTaskSet.add(task)) {
                    taskFragmentTransaction.addChange(prepareChange(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(3, taskFragmentOrganizerState.mOrganizer).setTask(task).build()));
                }
            }
            taskFragmentTransaction.addChange(prepareChange(pendingTaskFragmentEvent));
        }
        this.mTmpTaskSet.clear();
        taskFragmentOrganizerState.dispatchTransaction(taskFragmentTransaction);
        list.clear();
    }

    private boolean shouldDeferPendingEvents(@android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState, @android.annotation.NonNull java.util.List<com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent> list) {
        com.android.server.wm.Task task;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = list.get(i);
            if (pendingTaskFragmentEvent.mEventType != 3 && pendingTaskFragmentEvent.mEventType != 2 && pendingTaskFragmentEvent.mEventType != 0) {
                return false;
            }
            if (pendingTaskFragmentEvent.mEventType == 3) {
                task = pendingTaskFragmentEvent.mTask;
            } else {
                task = pendingTaskFragmentEvent.mTaskFragment.getTask();
            }
            if ((task.lastActiveTime > pendingTaskFragmentEvent.mDeferTime && isTaskVisible(task, arrayList, arrayList2)) || shouldSendEventWhenTaskInvisible(task, taskFragmentOrganizerState, pendingTaskFragmentEvent)) {
                return false;
            }
            pendingTaskFragmentEvent.mDeferTime = task.lastActiveTime;
        }
        return true;
    }

    private static boolean isTaskVisible(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.Task> arrayList, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.Task> arrayList2) {
        if (arrayList.contains(task)) {
            return true;
        }
        if (arrayList2.contains(task)) {
            return false;
        }
        if (task.shouldBeVisible(null)) {
            arrayList.add(task);
            return true;
        }
        arrayList2.add(task);
        return false;
    }

    private boolean shouldSendEventWhenTaskInvisible(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState, @android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        android.window.TaskFragmentParentInfo taskFragmentParentInfo = (android.window.TaskFragmentParentInfo) taskFragmentOrganizerState.mLastSentTaskFragmentParentInfos.get(task.mTaskId);
        if (taskFragmentParentInfo == null || taskFragmentParentInfo.isVisible()) {
            return true;
        }
        if (pendingTaskFragmentEvent.mEventType != 2) {
            return false;
        }
        android.window.TaskFragmentInfo taskFragmentInfo = (android.window.TaskFragmentInfo) taskFragmentOrganizerState.mLastSentTaskFragmentInfos.get(pendingTaskFragmentEvent.mTaskFragment);
        return taskFragmentInfo == null || taskFragmentInfo.isEmpty() != (pendingTaskFragmentEvent.mTaskFragment.getNonFinishingActivityCount() == 0);
    }

    void dispatchPendingInfoChangedEvent(@android.annotation.NonNull com.android.server.wm.TaskFragment taskFragment) {
        com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent = getPendingTaskFragmentEvent(taskFragment, 2);
        if (pendingTaskFragmentEvent == null) {
            return;
        }
        android.window.ITaskFragmentOrganizer taskFragmentOrganizer = taskFragment.getTaskFragmentOrganizer();
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState validateAndGetState = validateAndGetState(taskFragmentOrganizer);
        android.window.TaskFragmentTransaction taskFragmentTransaction = new android.window.TaskFragmentTransaction();
        taskFragmentTransaction.addChange(prepareChange(new com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent.Builder(3, taskFragmentOrganizer).setTask(taskFragment.getTask()).build()));
        taskFragmentTransaction.addChange(prepareChange(pendingTaskFragmentEvent));
        validateAndGetState.dispatchTransaction(taskFragmentTransaction);
        this.mPendingTaskFragmentEvents.get(taskFragmentOrganizer.asBinder()).remove(pendingTaskFragmentEvent);
    }

    @android.annotation.Nullable
    private android.window.TaskFragmentTransaction.Change prepareChange(@android.annotation.NonNull com.android.server.wm.TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent) {
        android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer = pendingTaskFragmentEvent.mTaskFragmentOrg;
        com.android.server.wm.TaskFragment taskFragment = pendingTaskFragmentEvent.mTaskFragment;
        com.android.server.wm.TaskFragmentOrganizerController.TaskFragmentOrganizerState taskFragmentOrganizerState = this.mTaskFragmentOrganizerState.get(iTaskFragmentOrganizer.asBinder());
        if (taskFragmentOrganizerState == null) {
            return null;
        }
        switch (pendingTaskFragmentEvent.mEventType) {
            case 0:
                return taskFragmentOrganizerState.prepareTaskFragmentAppeared(taskFragment);
            case 1:
                return taskFragmentOrganizerState.prepareTaskFragmentVanished(taskFragment);
            case 2:
                return taskFragmentOrganizerState.prepareTaskFragmentInfoChanged(taskFragment);
            case 3:
                return taskFragmentOrganizerState.prepareTaskFragmentParentInfoChanged(pendingTaskFragmentEvent.mTask);
            case 4:
                return taskFragmentOrganizerState.prepareTaskFragmentError(pendingTaskFragmentEvent.mErrorCallbackToken, taskFragment, pendingTaskFragmentEvent.mOpType, pendingTaskFragmentEvent.mException);
            case 5:
                return taskFragmentOrganizerState.prepareActivityReparentedToTask(pendingTaskFragmentEvent.mActivity);
            default:
                throw new java.lang.IllegalArgumentException("Unknown TaskFragmentEvent=" + pendingTaskFragmentEvent.mEventType);
        }
    }

    public boolean isActivityEmbedded(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    z = forTokenLocked.isEmbeddedInHostContainer();
                } else {
                    z = false;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    android.app.IApplicationThread getAppThread(int i, int i2) {
        android.app.IApplicationThread iApplicationThread;
        com.android.server.wm.WindowProcessController process = this.mAtmService.mProcessMap.getProcess(i);
        if (process != null && process.mUid == i2) {
            iApplicationThread = process.getThread();
        } else {
            iApplicationThread = null;
        }
        if (iApplicationThread == null) {
            throw new java.lang.IllegalArgumentException("Cannot find process for pid=" + i + " uid=" + i2);
        }
        return iApplicationThread;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.Intent trimIntent(@android.annotation.NonNull android.content.Intent intent) {
        return new android.content.Intent().setComponent(intent.getComponent()).setPackage(intent.getPackage()).setAction(intent.getAction());
    }
}
