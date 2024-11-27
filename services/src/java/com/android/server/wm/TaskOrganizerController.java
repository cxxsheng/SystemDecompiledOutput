package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskOrganizerController extends android.window.ITaskOrganizerController.Stub {
    private static final java.lang.String TAG = "TaskOrganizerController";
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final java.util.ArrayDeque<android.window.ITaskOrganizer> mTaskOrganizers = new java.util.ArrayDeque<>();
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.TaskOrganizerController.TaskOrganizerState> mTaskOrganizerStates = new android.util.ArrayMap<>();
    private final java.util.HashSet<java.lang.Integer> mInterceptBackPressedOnRootTasks = new java.util.HashSet<>();

    @com.android.internal.annotations.VisibleForTesting
    class DeathRecipient implements android.os.IBinder.DeathRecipient {
        android.window.ITaskOrganizer mTaskOrganizer;

        DeathRecipient(android.window.ITaskOrganizer iTaskOrganizer) {
            this.mTaskOrganizer = iTaskOrganizer;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.TaskOrganizerController.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = (com.android.server.wm.TaskOrganizerController.TaskOrganizerState) com.android.server.wm.TaskOrganizerController.this.mTaskOrganizerStates.get(this.mTaskOrganizer.asBinder());
                    if (taskOrganizerState != null) {
                        taskOrganizerState.dispose();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    private static class TaskOrganizerCallbacks {
        final android.window.ITaskOrganizer mTaskOrganizer;

        TaskOrganizerCallbacks(android.window.ITaskOrganizer iTaskOrganizer) {
            this.mTaskOrganizer = iTaskOrganizer;
        }

        android.os.IBinder getBinder() {
            return this.mTaskOrganizer.asBinder();
        }

        android.view.SurfaceControl prepareLeash(com.android.server.wm.Task task, java.lang.String str) {
            return new android.view.SurfaceControl(task.getSurfaceControl(), str);
        }

        void onTaskAppeared(com.android.server.wm.Task task) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -6181189296332065162L, 1, null, java.lang.Long.valueOf(task.mTaskId));
            try {
                this.mTaskOrganizer.onTaskAppeared(task.getTaskInfo(), prepareLeash(task, "TaskOrganizerController.onTaskAppeared"));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.wm.TaskOrganizerController.TAG, "Exception sending onTaskAppeared callback", e);
            }
        }

        void onTaskVanished(com.android.server.wm.Task task) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 6535296991997214354L, 1, null, java.lang.Long.valueOf(task.mTaskId));
            try {
                this.mTaskOrganizer.onTaskVanished(task.getTaskInfo());
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.wm.TaskOrganizerController.TAG, "Exception sending onTaskVanished callback", e);
            }
        }

        void onTaskInfoChanged(com.android.server.wm.Task task, android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (!task.mTaskAppearedSent) {
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -6638141753476761854L, 1, null, java.lang.Long.valueOf(task.mTaskId));
            if (!task.isOrganized()) {
                return;
            }
            try {
                this.mTaskOrganizer.onTaskInfoChanged(runningTaskInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.wm.TaskOrganizerController.TAG, "Exception sending onTaskInfoChanged callback", e);
            }
        }

        void onBackPressedOnTaskRoot(com.android.server.wm.Task task) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -8100069665346602959L, 1, null, java.lang.Long.valueOf(task.mTaskId));
            if (!task.mTaskAppearedSent || !task.isOrganized()) {
                return;
            }
            try {
                this.mTaskOrganizer.onBackPressedOnTaskRoot(task.getTaskInfo());
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.wm.TaskOrganizerController.TAG, "Exception sending onBackPressedOnTaskRoot callback", e);
            }
        }
    }

    static final class TaskOrganizerPendingEventsQueue {
        private final com.android.server.wm.TaskOrganizerController.TaskOrganizerState mOrganizerState;
        private android.app.ActivityManager.RunningTaskInfo mTmpTaskInfo;
        private final java.util.WeakHashMap<com.android.server.wm.Task, android.app.ActivityManager.RunningTaskInfo> mLastSentTaskInfos = new java.util.WeakHashMap<>();
        private final java.util.ArrayList<com.android.server.wm.TaskOrganizerController.PendingTaskEvent> mPendingTaskEvents = new java.util.ArrayList<>();

        TaskOrganizerPendingEventsQueue(com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState) {
            this.mOrganizerState = taskOrganizerState;
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.util.ArrayList<com.android.server.wm.TaskOrganizerController.PendingTaskEvent> getPendingEventList() {
            return this.mPendingTaskEvents;
        }

        int numPendingTaskEvents() {
            return this.mPendingTaskEvents.size();
        }

        void clearPendingTaskEvents() {
            this.mPendingTaskEvents.clear();
        }

        void addPendingTaskEvent(com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent) {
            this.mPendingTaskEvents.add(pendingTaskEvent);
        }

        void removePendingTaskEvent(com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent) {
            this.mPendingTaskEvents.remove(pendingTaskEvent);
        }

        boolean removePendingTaskEvents(com.android.server.wm.Task task) {
            boolean z = false;
            for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
                com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent = this.mPendingTaskEvents.get(size);
                if (task.mTaskId == pendingTaskEvent.mTask.mTaskId) {
                    this.mPendingTaskEvents.remove(size);
                    if (pendingTaskEvent.mEventType == 0) {
                        z = true;
                    }
                }
            }
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public com.android.server.wm.TaskOrganizerController.PendingTaskEvent getPendingTaskEvent(com.android.server.wm.Task task, int i) {
            for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
                com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent = this.mPendingTaskEvents.get(size);
                if (task.mTaskId == pendingTaskEvent.mTask.mTaskId && i == pendingTaskEvent.mEventType) {
                    return pendingTaskEvent;
                }
            }
            return null;
        }

        @com.android.internal.annotations.VisibleForTesting
        @android.annotation.Nullable
        com.android.server.wm.TaskOrganizerController.PendingTaskEvent getPendingLifecycleTaskEvent(com.android.server.wm.Task task) {
            for (int size = this.mPendingTaskEvents.size() - 1; size >= 0; size--) {
                com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent = this.mPendingTaskEvents.get(size);
                if (task.mTaskId == pendingTaskEvent.mTask.mTaskId && pendingTaskEvent.isLifecycleEvent()) {
                    return pendingTaskEvent;
                }
            }
            return null;
        }

        void dispatchPendingEvents() {
            if (this.mPendingTaskEvents.isEmpty()) {
                return;
            }
            int size = this.mPendingTaskEvents.size();
            for (int i = 0; i < size; i++) {
                dispatchPendingEvent(this.mPendingTaskEvents.get(i));
            }
            this.mPendingTaskEvents.clear();
        }

        private void dispatchPendingEvent(com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent) {
            com.android.server.wm.Task task = pendingTaskEvent.mTask;
            switch (pendingTaskEvent.mEventType) {
                case 0:
                    if (task.taskAppearedReady()) {
                        this.mOrganizerState.mOrganizer.onTaskAppeared(task);
                        break;
                    }
                    break;
                case 1:
                    this.mOrganizerState.mOrganizer.onTaskVanished(task);
                    this.mLastSentTaskInfos.remove(task);
                    break;
                case 2:
                    dispatchTaskInfoChanged(pendingTaskEvent.mTask, pendingTaskEvent.mForce);
                    break;
                case 3:
                    this.mOrganizerState.mOrganizer.onBackPressedOnTaskRoot(task);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchTaskInfoChanged(com.android.server.wm.Task task, boolean z) {
            android.app.ActivityManager.RunningTaskInfo runningTaskInfo = this.mLastSentTaskInfos.get(task);
            if (this.mTmpTaskInfo == null) {
                this.mTmpTaskInfo = new android.app.ActivityManager.RunningTaskInfo();
            }
            this.mTmpTaskInfo.configuration.unset();
            task.fillTaskInfo(this.mTmpTaskInfo);
            if (!((this.mTmpTaskInfo.equalsForTaskOrganizer(runningTaskInfo) && com.android.server.wm.WindowOrganizerController.configurationsAreEqualForOrganizer(this.mTmpTaskInfo.configuration, runningTaskInfo.configuration)) ? false : true) && !z) {
                return;
            }
            android.app.ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTmpTaskInfo;
            this.mLastSentTaskInfos.put(task, this.mTmpTaskInfo);
            this.mTmpTaskInfo = null;
            if (task.isOrganized()) {
                this.mOrganizerState.mOrganizer.onTaskInfoChanged(task, runningTaskInfo2);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class TaskOrganizerState {
        private final com.android.server.wm.TaskOrganizerController.DeathRecipient mDeathRecipient;
        private final com.android.server.wm.TaskOrganizerController.TaskOrganizerCallbacks mOrganizer;
        private final int mUid;
        private final java.util.ArrayList<com.android.server.wm.Task> mOrganizedTasks = new java.util.ArrayList<>();
        private final com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue mPendingEventsQueue = new com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue(this);

        TaskOrganizerState(android.window.ITaskOrganizer iTaskOrganizer, int i) {
            this.mOrganizer = new com.android.server.wm.TaskOrganizerController.TaskOrganizerCallbacks(iTaskOrganizer);
            this.mDeathRecipient = com.android.server.wm.TaskOrganizerController.this.new DeathRecipient(iTaskOrganizer);
            try {
                iTaskOrganizer.asBinder().linkToDeath(this.mDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.wm.TaskOrganizerController.TAG, "TaskOrganizer failed to register death recipient");
            }
            this.mUid = i;
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.TaskOrganizerController.DeathRecipient getDeathRecipient() {
            return this.mDeathRecipient;
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue getPendingEventsQueue() {
            return this.mPendingEventsQueue;
        }

        android.view.SurfaceControl addTaskWithoutCallback(com.android.server.wm.Task task, java.lang.String str) {
            task.mTaskAppearedSent = true;
            if (!this.mOrganizedTasks.contains(task)) {
                this.mOrganizedTasks.add(task);
            }
            return this.mOrganizer.prepareLeash(task, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean addTask(com.android.server.wm.Task task) {
            if (task.mTaskAppearedSent) {
                return false;
            }
            if (!this.mOrganizedTasks.contains(task)) {
                this.mOrganizedTasks.add(task);
            }
            if (!task.taskAppearedReady()) {
                return false;
            }
            task.mTaskAppearedSent = true;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeTask(com.android.server.wm.Task task, boolean z) {
            this.mOrganizedTasks.remove(task);
            com.android.server.wm.TaskOrganizerController.this.mInterceptBackPressedOnRootTasks.remove(java.lang.Integer.valueOf(task.mTaskId));
            boolean z2 = task.mTaskAppearedSent;
            if (z2) {
                if (task.getSurfaceControl() != null) {
                    task.migrateToNewSurfaceControl(task.getSyncTransaction());
                }
                task.mTaskAppearedSent = false;
            }
            if (z) {
                com.android.server.wm.TaskOrganizerController.this.mService.removeTask(task.mTaskId);
            }
            return z2;
        }

        void dispose() {
            com.android.server.wm.TaskOrganizerController.this.mTaskOrganizers.remove(this.mOrganizer.mTaskOrganizer);
            while (!this.mOrganizedTasks.isEmpty()) {
                com.android.server.wm.Task task = this.mOrganizedTasks.get(0);
                if (task.mCreatedByOrganizer) {
                    task.removeImmediately();
                } else {
                    task.updateTaskOrganizerState();
                }
                if (this.mOrganizedTasks.contains(task) && removeTask(task, task.mRemoveWithTaskOrganizer)) {
                    com.android.server.wm.TaskOrganizerController.this.onTaskVanishedInternal(this, task);
                }
                if (com.android.server.wm.TaskOrganizerController.this.mService.getTransitionController().isShellTransitionsEnabled() && task.mTaskOrganizer != null && task.getSurfaceControl() != null) {
                    task.getSyncTransaction().show(task.getSurfaceControl());
                }
            }
            this.mPendingEventsQueue.clearPendingTaskEvents();
            com.android.server.wm.TaskOrganizerController.this.mTaskOrganizerStates.remove(this.mOrganizer.getBinder());
        }

        void unlinkDeath() {
            this.mOrganizer.getBinder().unlinkToDeath(this.mDeathRecipient, 0);
        }
    }

    static class PendingTaskEvent {
        static final int EVENT_APPEARED = 0;
        static final int EVENT_INFO_CHANGED = 2;
        static final int EVENT_ROOT_BACK_PRESSED = 3;
        static final int EVENT_VANISHED = 1;
        final int mEventType;
        boolean mForce;
        final com.android.server.wm.Task mTask;
        final android.window.ITaskOrganizer mTaskOrg;

        PendingTaskEvent(com.android.server.wm.Task task, int i) {
            this(task, task.mTaskOrganizer, i);
        }

        PendingTaskEvent(com.android.server.wm.Task task, android.window.ITaskOrganizer iTaskOrganizer, int i) {
            this.mTask = task;
            this.mTaskOrg = iTaskOrganizer;
            this.mEventType = i;
        }

        boolean isLifecycleEvent() {
            return this.mEventType == 0 || this.mEventType == 1 || this.mEventType == 2;
        }
    }

    TaskOrganizerController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            throw com.android.server.wm.ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(TAG, e);
        }
    }

    public android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> registerTaskOrganizer(final android.window.ITaskOrganizer iTaskOrganizer) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("registerTaskOrganizer()");
        final int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TaskOrganizerController.this.lambda$registerTaskOrganizer$1(iTaskOrganizer, callingUid, arrayList);
                }
            };
            if (this.mService.getTransitionController().isShellTransitionsEnabled()) {
                this.mService.getTransitionController().mRunningLock.runWhenIdle(1000L, runnable);
            } else {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        runnable.run();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
            android.content.pm.ParceledListSlice<android.window.TaskAppearedInfo> parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return parceledListSlice;
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerTaskOrganizer$1(android.window.ITaskOrganizer iTaskOrganizer, int i, final java.util.ArrayList arrayList) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -610138383571469481L, 4, null, java.lang.String.valueOf(iTaskOrganizer.asBinder()), java.lang.Long.valueOf(i));
        if (!this.mTaskOrganizerStates.containsKey(iTaskOrganizer.asBinder())) {
            this.mTaskOrganizers.add(iTaskOrganizer);
            this.mTaskOrganizerStates.put(iTaskOrganizer.asBinder(), new com.android.server.wm.TaskOrganizerController.TaskOrganizerState(iTaskOrganizer, i));
        }
        final com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
        this.mService.mRootWindowContainer.forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskOrganizerController.lambda$registerTaskOrganizer$0(com.android.server.wm.TaskOrganizerController.TaskOrganizerState.this, arrayList, (com.android.server.wm.Task) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$registerTaskOrganizer$0(com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState, java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        boolean z = !task.mCreatedByOrganizer;
        task.updateTaskOrganizerState(z);
        if (task.isOrganized() && z) {
            arrayList.add(new android.window.TaskAppearedInfo(task.getTaskInfo(), taskOrganizerState.addTaskWithoutCallback(task, "TaskOrganizerController.registerTaskOrganizer")));
        }
    }

    public void unregisterTaskOrganizer(final android.window.ITaskOrganizer iTaskOrganizer) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("unregisterTaskOrganizer()");
        final int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TaskOrganizerController.this.lambda$unregisterTaskOrganizer$2(iTaskOrganizer, callingUid);
                }
            };
            if (this.mService.getTransitionController().isShellTransitionsEnabled()) {
                this.mService.getTransitionController().mRunningLock.runWhenIdle(1000L, runnable);
            } else {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        runnable.run();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterTaskOrganizer$2(android.window.ITaskOrganizer iTaskOrganizer, int i) {
        com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
        if (taskOrganizerState == null) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 1705860547080436016L, 4, null, java.lang.String.valueOf(iTaskOrganizer.asBinder()), java.lang.Long.valueOf(i));
        taskOrganizerState.unlinkDeath();
        taskOrganizerState.dispose();
    }

    android.window.ITaskOrganizer getTaskOrganizer() {
        return this.mTaskOrganizers.peekLast();
    }

    static class StartingWindowAnimationAdaptor implements com.android.server.wm.AnimationAdapter {
        android.view.SurfaceControl mAnimationLeash;

        StartingWindowAnimationAdaptor() {
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            this.mAnimationLeash = surfaceControl;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
            if (this.mAnimationLeash == surfaceControl) {
                this.mAnimationLeash = null;
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getStatusBarTransitionsStartTime() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str + "StartingWindowAnimationAdaptor mCapturedLeash=");
            printWriter.print(this.mAnimationLeash);
            printWriter.println();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        }
    }

    static android.view.SurfaceControl applyStartingWindowAnimation(com.android.server.wm.WindowState windowState) {
        android.view.SurfaceControl.Transaction pendingTransaction = windowState.getPendingTransaction();
        android.graphics.Rect relativeFrame = windowState.getRelativeFrame();
        com.android.server.wm.TaskOrganizerController.StartingWindowAnimationAdaptor startingWindowAnimationAdaptor = new com.android.server.wm.TaskOrganizerController.StartingWindowAnimationAdaptor();
        windowState.startAnimation(pendingTransaction, startingWindowAnimationAdaptor, false, 128);
        if (startingWindowAnimationAdaptor.mAnimationLeash == null) {
            android.util.Slog.e(TAG, "Cannot start starting window animation, the window " + windowState + " was removed");
            return null;
        }
        pendingTransaction.setPosition(startingWindowAnimationAdaptor.mAnimationLeash, relativeFrame.left, relativeFrame.top);
        return startingWindowAnimationAdaptor.mAnimationLeash;
    }

    boolean addStartingWindow(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, int i, android.window.TaskSnapshot taskSnapshot) {
        android.window.ITaskOrganizer taskOrganizer;
        if (task.getRootTask() == null || activityRecord.mStartingData == null || (taskOrganizer = getTaskOrganizer()) == null) {
            return false;
        }
        android.window.StartingWindowInfo startingWindowInfo = task.getStartingWindowInfo(activityRecord);
        if (i != 0) {
            startingWindowInfo.splashScreenThemeResId = i;
        }
        startingWindowInfo.taskSnapshot = taskSnapshot;
        startingWindowInfo.appToken = activityRecord.token;
        try {
            taskOrganizer.addStartingWindow(startingWindowInfo);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending onTaskStart callback", e);
            return false;
        }
    }

    void removeStartingWindow(com.android.server.wm.Task task, android.window.ITaskOrganizer iTaskOrganizer, boolean z, boolean z2) {
        if (task.getRootTask() == null) {
            return;
        }
        if (iTaskOrganizer == null) {
            iTaskOrganizer = getTaskOrganizer();
        }
        if (iTaskOrganizer == null) {
            return;
        }
        android.window.StartingWindowRemovalInfo startingWindowRemovalInfo = new android.window.StartingWindowRemovalInfo();
        startingWindowRemovalInfo.taskId = task.mTaskId;
        startingWindowRemovalInfo.playRevealAnimation = z && task.getDisplayContent() != null && task.getDisplayInfo().state == 2;
        boolean z3 = !task.inMultiWindowMode();
        com.android.server.wm.ActivityRecord activityRecord = task.topActivityContainsStartingWindow();
        if (activityRecord != null) {
            com.android.server.wm.DisplayContent displayContent = activityRecord.getDisplayContent();
            if (z2) {
                if (activityRecord.isVisibleRequested() && displayContent.mInputMethodWindow != null && displayContent.isFixedRotationLaunchingApp(activityRecord)) {
                    startingWindowRemovalInfo.deferRemoveForImeMode = 2;
                } else {
                    startingWindowRemovalInfo.deferRemoveForImeMode = 1;
                }
            } else {
                startingWindowRemovalInfo.deferRemoveForImeMode = 0;
            }
            com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow(false);
            if (findMainWindow == null || findMainWindow.mRemoved) {
                startingWindowRemovalInfo.playRevealAnimation = false;
            } else if (startingWindowRemovalInfo.playRevealAnimation && z3) {
                startingWindowRemovalInfo.roundedCornerRadius = activityRecord.mLetterboxUiController.getRoundedCornersRadius(findMainWindow);
                startingWindowRemovalInfo.windowAnimationLeash = applyStartingWindowAnimation(findMainWindow);
                startingWindowRemovalInfo.mainFrame = findMainWindow.getRelativeFrame();
            }
        }
        try {
            iTaskOrganizer.removeStartingWindow(startingWindowRemovalInfo);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending onStartTaskFinished callback", e);
        }
    }

    int addWindowlessStartingSurface(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord, android.view.SurfaceControl surfaceControl, android.window.TaskSnapshot taskSnapshot, android.content.res.Configuration configuration, android.window.IWindowlessStartingSurfaceCallback iWindowlessStartingSurfaceCallback) {
        android.window.ITaskOrganizer peekLast;
        if (task.getRootTask() == null || (peekLast = this.mTaskOrganizers.peekLast()) == null) {
            return -1;
        }
        android.window.StartingWindowInfo startingWindowInfo = task.getStartingWindowInfo(activityRecord);
        startingWindowInfo.taskInfo.configuration.setTo(configuration);
        startingWindowInfo.taskInfo.taskDescription = activityRecord.taskDescription;
        startingWindowInfo.taskSnapshot = taskSnapshot;
        startingWindowInfo.windowlessStartingSurfaceCallback = iWindowlessStartingSurfaceCallback;
        startingWindowInfo.rootSurface = surfaceControl;
        try {
            peekLast.addStartingWindow(startingWindowInfo);
            return task.mTaskId;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending addWindowlessStartingSurface ", e);
            return -1;
        }
    }

    void removeWindowlessStartingSurface(int i, boolean z) {
        android.window.ITaskOrganizer peekLast = this.mTaskOrganizers.peekLast();
        if (peekLast == null || i == 0) {
            return;
        }
        android.window.StartingWindowRemovalInfo startingWindowRemovalInfo = new android.window.StartingWindowRemovalInfo();
        startingWindowRemovalInfo.taskId = i;
        startingWindowRemovalInfo.windowlessSurface = true;
        startingWindowRemovalInfo.removeImmediately = z;
        try {
            peekLast.removeStartingWindow(startingWindowRemovalInfo);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending removeWindowlessStartingSurface ", e);
        }
    }

    boolean copySplashScreenView(com.android.server.wm.Task task, android.window.ITaskOrganizer iTaskOrganizer) {
        if (task.getRootTask() == null) {
            return false;
        }
        if (iTaskOrganizer == null) {
            iTaskOrganizer = getTaskOrganizer();
        }
        if (iTaskOrganizer == null) {
            return false;
        }
        try {
            iTaskOrganizer.copySplashScreenView(task.mTaskId);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending copyStartingWindowView callback", e);
            return false;
        }
    }

    boolean isSupportWindowlessStartingSurface() {
        return this.mTaskOrganizers.peekLast() != null;
    }

    public void onAppSplashScreenViewRemoved(com.android.server.wm.Task task, android.window.ITaskOrganizer iTaskOrganizer) {
        if (task.getRootTask() == null) {
            return;
        }
        if (iTaskOrganizer == null) {
            iTaskOrganizer = getTaskOrganizer();
        }
        if (iTaskOrganizer == null) {
            return;
        }
        try {
            iTaskOrganizer.onAppSplashScreenViewRemoved(task.mTaskId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception sending onAppSplashScreenViewRemoved callback", e);
        }
    }

    void onTaskAppeared(android.window.ITaskOrganizer iTaskOrganizer, com.android.server.wm.Task task) {
        com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
        if (taskOrganizerState != null && taskOrganizerState.addTask(task)) {
            com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = taskOrganizerState.mPendingEventsQueue;
            if (taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 0) == null) {
                taskOrganizerPendingEventsQueue.addPendingTaskEvent(new com.android.server.wm.TaskOrganizerController.PendingTaskEvent(task, 0));
            }
        }
    }

    void onTaskVanished(android.window.ITaskOrganizer iTaskOrganizer, com.android.server.wm.Task task) {
        com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(iTaskOrganizer.asBinder());
        if (taskOrganizerState != null && taskOrganizerState.removeTask(task, task.mRemoveWithTaskOrganizer)) {
            onTaskVanishedInternal(taskOrganizerState, task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskVanishedInternal(com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState, com.android.server.wm.Task task) {
        if (taskOrganizerState == null) {
            android.util.Slog.i(TAG, "cannot send onTaskVanished because organizer state is not present for this organizer");
            return;
        }
        com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = taskOrganizerState.mPendingEventsQueue;
        if (taskOrganizerPendingEventsQueue.removePendingTaskEvents(task)) {
            return;
        }
        taskOrganizerPendingEventsQueue.addPendingTaskEvent(new com.android.server.wm.TaskOrganizerController.PendingTaskEvent(task, taskOrganizerState.mOrganizer.mTaskOrganizer, 1));
    }

    public void createRootTask(int i, int i2, @android.annotation.Nullable android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("createRootTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -2286607251115721394L, 1, null, java.lang.Long.valueOf(i));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        createRootTask(displayContent, i2, iBinder, z);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.Task createRootTask(com.android.server.wm.DisplayContent displayContent, int i, @android.annotation.Nullable android.os.IBinder iBinder) {
        return createRootTask(displayContent, i, iBinder, false);
    }

    com.android.server.wm.Task createRootTask(com.android.server.wm.DisplayContent displayContent, int i, @android.annotation.Nullable android.os.IBinder iBinder, boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 8466395828406204368L, 5, null, java.lang.Long.valueOf(displayContent.mDisplayId), java.lang.Long.valueOf(i));
        com.android.server.wm.Task build = new com.android.server.wm.Task.Builder(this.mService).setWindowingMode(i).setIntent(new android.content.Intent()).setCreatedByOrganizer(true).setDeferTaskAppear(true).setLaunchCookie(iBinder).setParent(displayContent.getDefaultTaskDisplayArea()).setRemoveWithTaskOrganizer(z).build();
        build.setDeferTaskAppear(false);
        return build;
    }

    public boolean deleteRootTask(android.window.WindowContainerToken windowContainerToken) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("deleteRootTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    com.android.server.wm.Task asTask = fromBinder.asTask();
                    if (asTask == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (!asTask.mCreatedByOrganizer) {
                        throw new java.lang.IllegalArgumentException("Attempt to delete task not created by organizer task=" + asTask);
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 6867170298997192615L, 5, null, java.lang.Long.valueOf(asTask.getDisplayId()), java.lang.Long.valueOf(asTask.getWindowingMode()));
                    asTask.remove(true, "deleteRootTask");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void dispatchPendingEvents() {
        if (this.mService.mWindowManager.mWindowPlacerLocked.isLayoutDeferred()) {
            return;
        }
        for (int i = 0; i < this.mTaskOrganizerStates.size(); i++) {
            this.mTaskOrganizerStates.valueAt(i).mPendingEventsQueue.dispatchPendingEvents();
        }
    }

    void reportImeDrawnOnTask(com.android.server.wm.Task task) {
        com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder());
        if (taskOrganizerState != null) {
            try {
                taskOrganizerState.mOrganizer.mTaskOrganizer.onImeDrawnOnTask(task.mTaskId);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Exception sending onImeDrawnOnTask callback", e);
            }
        }
    }

    void onTaskInfoChanged(com.android.server.wm.Task task, boolean z) {
        if (!task.mTaskAppearedSent) {
            return;
        }
        com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder()).mPendingEventsQueue;
        if (taskOrganizerPendingEventsQueue == null) {
            android.util.Slog.i(TAG, "cannot send onTaskInfoChanged because pending events queue is not present for this organizer");
            return;
        }
        if (z && taskOrganizerPendingEventsQueue.numPendingTaskEvents() == 0) {
            taskOrganizerPendingEventsQueue.dispatchTaskInfoChanged(task, true);
            return;
        }
        com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingLifecycleTaskEvent = taskOrganizerPendingEventsQueue.getPendingLifecycleTaskEvent(task);
        if (pendingLifecycleTaskEvent != null) {
            if (pendingLifecycleTaskEvent.mEventType != 2) {
                return;
            } else {
                taskOrganizerPendingEventsQueue.removePendingTaskEvent(pendingLifecycleTaskEvent);
            }
        } else {
            pendingLifecycleTaskEvent = new com.android.server.wm.TaskOrganizerController.PendingTaskEvent(task, 2);
        }
        pendingLifecycleTaskEvent.mForce |= z;
        taskOrganizerPendingEventsQueue.addPendingTaskEvent(pendingLifecycleTaskEvent);
    }

    public android.window.WindowContainerToken getImeTarget(int i) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getImeTarget()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mService.mWindowManager.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
                    if (imeTarget == null || imeTarget.getWindow() == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.Task task = imeTarget.getWindow().getTask();
                    if (task == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    android.window.WindowContainerToken windowContainerToken = task.mRemoteToken.toWindowContainerToken();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return windowContainerToken;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getChildTasks(android.window.WindowContainerToken windowContainerToken, @android.annotation.Nullable int[] iArr) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getChildTasks()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (windowContainerToken == null) {
                        throw new java.lang.IllegalArgumentException("Can't get children of null parent");
                    }
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        android.util.Slog.e(TAG, "Can't get children of " + windowContainerToken + " because it is not valid.");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.Task asTask = fromBinder.asTask();
                    if (asTask == null) {
                        android.util.Slog.e(TAG, fromBinder + " is not a task...");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (!asTask.mCreatedByOrganizer) {
                        android.util.Slog.w(TAG, "Can only get children of root tasks created via createRootTask");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (int childCount = asTask.getChildCount() - 1; childCount >= 0; childCount--) {
                        com.android.server.wm.Task asTask2 = asTask.getChildAt(childCount).asTask();
                        if (asTask2 != null && (iArr == null || com.android.internal.util.ArrayUtils.contains(iArr, asTask2.getActivityType()))) {
                            arrayList.add(asTask2.getTaskInfo());
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return arrayList;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getRootTasks(int i, @android.annotation.Nullable final int[] iArr) {
        final java.util.ArrayList arrayList;
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("getRootTasks()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        throw new java.lang.IllegalArgumentException("Display " + i + " doesn't exist");
                    }
                    arrayList = new java.util.ArrayList();
                    displayContent.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskOrganizerController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.TaskOrganizerController.lambda$getRootTasks$3(iArr, arrayList, (com.android.server.wm.Task) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return arrayList;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRootTasks$3(int[] iArr, java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        if (iArr != null && !com.android.internal.util.ArrayUtils.contains(iArr, task.getActivityType())) {
            return;
        }
        arrayList.add(task.getTaskInfo());
    }

    public void setInterceptBackPressedOnTaskRoot(android.window.WindowContainerToken windowContainerToken, boolean z) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("setInterceptBackPressedOnTaskRoot()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -4296644831871159510L, 3, null, java.lang.Boolean.valueOf(z));
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        android.util.Slog.w(TAG, "Could not resolve window from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.Task asTask = fromBinder.asTask();
                    if (asTask == null) {
                        android.util.Slog.w(TAG, "Could not resolve task from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        if (z) {
                            this.mInterceptBackPressedOnRootTasks.add(java.lang.Integer.valueOf(asTask.mTaskId));
                        } else {
                            this.mInterceptBackPressedOnRootTasks.remove(java.lang.Integer.valueOf(asTask.mTaskId));
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void restartTaskTopActivityProcessIfVisible(android.window.WindowContainerToken windowContainerToken) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("restartTopActivityProcessIfVisible()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        android.util.Slog.w(TAG, "Could not resolve window from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.Task asTask = fromBinder.asTask();
                    if (asTask == null) {
                        android.util.Slog.w(TAG, "Could not resolve task from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -558727273888268534L, 1, null, java.lang.Long.valueOf(asTask.mTaskId));
                    com.android.server.wm.ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
                    if (topNonFinishingActivity != null) {
                        topNonFinishingActivity.restartProcessIfVisible();
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateCameraCompatControlState(android.window.WindowContainerToken windowContainerToken, int i) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("updateCameraCompatControlState()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowContainer fromBinder = com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
                    if (fromBinder == null) {
                        android.util.Slog.w(TAG, "Could not resolve window from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.Task asTask = fromBinder.asTask();
                    if (asTask == null) {
                        android.util.Slog.w(TAG, "Could not resolve task from token");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -7064081458956324316L, 4, null, java.lang.String.valueOf(android.app.AppCompatTaskInfo.cameraCompatControlStateToString(i)), java.lang.Long.valueOf(asTask.mTaskId));
                    com.android.server.wm.ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
                    if (topNonFinishingActivity != null) {
                        topNonFinishingActivity.updateCameraCompatStateFromUser(i);
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean handleInterceptBackPressedOnTaskRoot(com.android.server.wm.Task task) {
        if (!shouldInterceptBackPressedOnRootTask(task)) {
            return false;
        }
        com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = this.mTaskOrganizerStates.get(task.mTaskOrganizer.asBinder()).mPendingEventsQueue;
        if (taskOrganizerPendingEventsQueue == null) {
            android.util.Slog.w(TAG, "cannot get handle BackPressedOnTaskRoot because organizerState is not present");
            return false;
        }
        if (taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 1) != null) {
            return false;
        }
        com.android.server.wm.TaskOrganizerController.PendingTaskEvent pendingTaskEvent = taskOrganizerPendingEventsQueue.getPendingTaskEvent(task, 3);
        if (pendingTaskEvent == null) {
            pendingTaskEvent = new com.android.server.wm.TaskOrganizerController.PendingTaskEvent(task, 3);
        } else {
            taskOrganizerPendingEventsQueue.removePendingTaskEvent(pendingTaskEvent);
        }
        taskOrganizerPendingEventsQueue.addPendingTaskEvent(pendingTaskEvent);
        this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
        return true;
    }

    boolean shouldInterceptBackPressedOnRootTask(com.android.server.wm.Task task) {
        return task != null && task.isOrganized() && this.mInterceptBackPressedOnRootTasks.contains(java.lang.Integer.valueOf(task.mTaskId));
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println("TaskOrganizerController:");
        android.window.ITaskOrganizer peekLast = this.mTaskOrganizers.peekLast();
        java.util.Iterator<android.window.ITaskOrganizer> it = this.mTaskOrganizers.iterator();
        while (it.hasNext()) {
            android.window.ITaskOrganizer next = it.next();
            com.android.server.wm.TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mTaskOrganizerStates.get(next.asBinder());
            java.util.ArrayList arrayList = taskOrganizerState.mOrganizedTasks;
            printWriter.print(str2 + "  ");
            printWriter.print(taskOrganizerState.mOrganizer.mTaskOrganizer + " uid=" + taskOrganizerState.mUid);
            if (peekLast == next) {
                printWriter.print(" (active)");
            }
            printWriter.println(':');
            for (int i = 0; i < arrayList.size(); i++) {
                com.android.server.wm.Task task = (com.android.server.wm.Task) arrayList.get(i);
                printWriter.println(str2 + "    (" + android.app.WindowConfiguration.windowingModeToString(task.getWindowingMode()) + ") " + task);
            }
        }
        printWriter.println();
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.TaskOrganizerController.TaskOrganizerState getTaskOrganizerState(android.os.IBinder iBinder) {
        return this.mTaskOrganizerStates.get(iBinder);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.TaskOrganizerController.TaskOrganizerPendingEventsQueue getTaskOrganizerPendingEvents(android.os.IBinder iBinder) {
        return this.mTaskOrganizerStates.get(iBinder).mPendingEventsQueue;
    }
}
