package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskChangeNotificationController {
    private static final int NOTIFY_ACTIVITY_DISMISSING_DOCKED_ROOT_TASK_MSG = 7;
    private static final int NOTIFY_ACTIVITY_LAUNCH_ON_SECONDARY_DISPLAY_FAILED_MSG = 18;
    private static final int NOTIFY_ACTIVITY_LAUNCH_ON_SECONDARY_DISPLAY_REROUTED_MSG = 19;
    private static final int NOTIFY_ACTIVITY_PINNED_LISTENERS_MSG = 3;
    private static final int NOTIFY_ACTIVITY_REQUESTED_ORIENTATION_CHANGED_LISTENERS = 12;
    private static final int NOTIFY_ACTIVITY_RESTART_ATTEMPT_LISTENERS_MSG = 4;
    private static final int NOTIFY_ACTIVITY_ROTATED_MSG = 26;
    private static final int NOTIFY_ACTIVITY_UNPINNED_LISTENERS_MSG = 17;
    private static final int NOTIFY_BACK_PRESSED_ON_TASK_ROOT = 20;
    private static final int NOTIFY_FORCED_RESIZABLE_MSG = 6;
    private static final int NOTIFY_LOCK_TASK_MODE_CHANGED_MSG = 28;
    private static final int NOTIFY_TASK_ADDED_LISTENERS_MSG = 8;
    private static final int NOTIFY_TASK_DESCRIPTION_CHANGED_LISTENERS_MSG = 11;
    private static final int NOTIFY_TASK_DISPLAY_CHANGED_LISTENERS_MSG = 21;
    private static final int NOTIFY_TASK_FOCUS_CHANGED_MSG = 24;
    private static final int NOTIFY_TASK_LIST_FROZEN_UNFROZEN_MSG = 23;
    private static final int NOTIFY_TASK_LIST_UPDATED_LISTENERS_MSG = 22;
    private static final int NOTIFY_TASK_MOVED_TO_BACK_LISTENERS_MSG = 27;
    private static final int NOTIFY_TASK_MOVED_TO_FRONT_LISTENERS_MSG = 10;
    private static final int NOTIFY_TASK_PROFILE_LOCKED_LISTENERS_MSG = 14;
    private static final int NOTIFY_TASK_REMOVAL_STARTED_LISTENERS = 13;
    private static final int NOTIFY_TASK_REMOVED_LISTENERS_MSG = 9;
    private static final int NOTIFY_TASK_REQUESTED_ORIENTATION_CHANGED_MSG = 25;
    private static final int NOTIFY_TASK_SNAPSHOT_CHANGED_LISTENERS_MSG = 15;
    private static final int NOTIFY_TASK_STACK_CHANGE_LISTENERS_DELAY = 100;
    private static final int NOTIFY_TASK_STACK_CHANGE_LISTENERS_MSG = 2;
    private final android.os.Handler mHandler;
    private final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;

    @com.android.internal.annotations.GuardedBy({"mRemoteTaskStackListeners"})
    private final android.os.RemoteCallbackList<android.app.ITaskStackListener> mRemoteTaskStackListeners = new android.os.RemoteCallbackList<>();

    @com.android.internal.annotations.GuardedBy({"mLocalTaskStackListeners"})
    private final java.util.ArrayList<android.app.ITaskStackListener> mLocalTaskStackListeners = new java.util.ArrayList<>();
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskStackChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            iTaskStackListener.onTaskStackChanged();
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskCreated = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda11
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$1(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskRemoved = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda17
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$2(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskMovedToFront = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda18
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$3(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskDescriptionChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda19
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$4(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyBackPressedOnTaskRoot = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda20
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$5(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityRequestedOrientationChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda21
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$6(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskRemovalStarted = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda22
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$7(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityPinned = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda23
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$8(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityUnpinned = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda24
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            iTaskStackListener.onActivityUnpinned();
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityRestartAttempt = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda1
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$10(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityForcedResizable = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda2
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$11(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityDismissingDockedTask = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda3
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            iTaskStackListener.onActivityDismissingDockedTask();
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityLaunchOnSecondaryDisplayFailed = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda4
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$13(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyActivityLaunchOnSecondaryDisplayRerouted = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda5
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$14(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskProfileLocked = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda6
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$15(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskSnapshotChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda7
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$16(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskDisplayChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda8
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$17(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskListUpdated = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda9
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            iTaskStackListener.onRecentTaskListUpdated();
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskListFrozen = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda10
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$19(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskFocusChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda12
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$20(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskRequestedOrientationChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda13
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$21(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyOnActivityRotation = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda14
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$22(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyTaskMovedToBack = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda15
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$23(iTaskStackListener, message);
        }
    };
    private final com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer mNotifyLockTaskModeChanged = new com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer() { // from class: com.android.server.wm.TaskChangeNotificationController$$ExternalSyntheticLambda16
        @Override // com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer
        public final void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) {
            com.android.server.wm.TaskChangeNotificationController.lambda$new$24(iTaskStackListener, message);
        }
    };

    @java.lang.FunctionalInterface
    public interface TaskStackConsumer {
        void accept(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$1(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskCreated(message.arg1, (android.content.ComponentName) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskRemoved(message.arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$3(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskMovedToFront((android.app.ActivityManager.RunningTaskInfo) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$4(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskDescriptionChanged((android.app.ActivityManager.RunningTaskInfo) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$5(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onBackPressedOnTaskRoot((android.app.ActivityManager.RunningTaskInfo) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$6(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityRequestedOrientationChanged(message.arg1, message.arg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$7(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskRemovalStarted((android.app.ActivityManager.RunningTaskInfo) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$8(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityPinned((java.lang.String) message.obj, message.sendingUid, message.arg1, message.arg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$10(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        iTaskStackListener.onActivityRestartAttempt((android.app.ActivityManager.RunningTaskInfo) someArgs.arg1, someArgs.argi1 != 0, someArgs.argi2 != 0, someArgs.argi3 != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$11(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityForcedResizable((java.lang.String) message.obj, message.arg1, message.arg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$13(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityLaunchOnSecondaryDisplayFailed((android.app.ActivityManager.RunningTaskInfo) message.obj, message.arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$14(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityLaunchOnSecondaryDisplayRerouted((android.app.ActivityManager.RunningTaskInfo) message.obj, message.arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$15(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskProfileLocked((android.app.ActivityManager.RunningTaskInfo) message.obj, message.arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$16(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskSnapshotChanged(message.arg1, (android.window.TaskSnapshot) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$17(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskDisplayChanged(message.arg1, message.arg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$19(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onRecentTaskListFrozenChanged(message.arg1 != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$20(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskFocusChanged(message.arg1, message.arg2 != 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$21(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskRequestedOrientationChanged(message.arg1, message.arg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$22(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onActivityRotation(message.arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$23(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onTaskMovedToBack((android.app.ActivityManager.RunningTaskInfo) message.obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$24(android.app.ITaskStackListener iTaskStackListener, android.os.Message message) throws android.os.RemoteException {
        iTaskStackListener.onLockTaskModeChanged(message.arg1);
    }

    private class MainHandler extends android.os.Handler {
        public MainHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 2:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskStackChanged, message);
                    break;
                case 3:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityPinned, message);
                    break;
                case 4:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityRestartAttempt, message);
                    break;
                case 6:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityForcedResizable, message);
                    break;
                case 7:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityDismissingDockedTask, message);
                    break;
                case 8:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskCreated, message);
                    break;
                case 9:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskRemoved, message);
                    break;
                case 10:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskMovedToFront, message);
                    break;
                case 11:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskDescriptionChanged, message);
                    break;
                case 12:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityRequestedOrientationChanged, message);
                    break;
                case 13:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskRemovalStarted, message);
                    break;
                case 14:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskProfileLocked, message);
                    break;
                case 15:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskSnapshotChanged, message);
                    break;
                case 17:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityUnpinned, message);
                    break;
                case 18:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityLaunchOnSecondaryDisplayFailed, message);
                    break;
                case 19:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyActivityLaunchOnSecondaryDisplayRerouted, message);
                    break;
                case 20:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyBackPressedOnTaskRoot, message);
                    break;
                case 21:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskDisplayChanged, message);
                    break;
                case 22:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskListUpdated, message);
                    break;
                case 23:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskListFrozen, message);
                    break;
                case 24:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskFocusChanged, message);
                    break;
                case 25:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskRequestedOrientationChanged, message);
                    break;
                case 26:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyOnActivityRotation, message);
                    break;
                case 27:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyTaskMovedToBack, message);
                    break;
                case 28:
                    com.android.server.wm.TaskChangeNotificationController.this.forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.this.mNotifyLockTaskModeChanged, message);
                    break;
            }
            if (message.obj instanceof com.android.internal.os.SomeArgs) {
                ((com.android.internal.os.SomeArgs) message.obj).recycle();
            }
        }
    }

    TaskChangeNotificationController(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.os.Handler handler) {
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mHandler = new com.android.server.wm.TaskChangeNotificationController.MainHandler(handler.getLooper());
    }

    public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
        if (iTaskStackListener instanceof android.os.Binder) {
            synchronized (this.mLocalTaskStackListeners) {
                try {
                    if (!this.mLocalTaskStackListeners.contains(iTaskStackListener)) {
                        if (iTaskStackListener instanceof android.app.TaskStackListener) {
                            ((android.app.TaskStackListener) iTaskStackListener).setIsLocal();
                        }
                        this.mLocalTaskStackListeners.add(iTaskStackListener);
                    }
                } finally {
                }
            }
            return;
        }
        if (iTaskStackListener != null) {
            synchronized (this.mRemoteTaskStackListeners) {
                this.mRemoteTaskStackListeners.register(iTaskStackListener);
            }
        }
    }

    public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
        if (iTaskStackListener instanceof android.os.Binder) {
            synchronized (this.mLocalTaskStackListeners) {
                this.mLocalTaskStackListeners.remove(iTaskStackListener);
            }
        } else if (iTaskStackListener != null) {
            synchronized (this.mRemoteTaskStackListeners) {
                this.mRemoteTaskStackListeners.unregister(iTaskStackListener);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forAllRemoteListeners(com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer taskStackConsumer, android.os.Message message) {
        synchronized (this.mRemoteTaskStackListeners) {
            for (int beginBroadcast = this.mRemoteTaskStackListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    taskStackConsumer.accept(this.mRemoteTaskStackListeners.getBroadcastItem(beginBroadcast), message);
                } catch (android.os.RemoteException e) {
                }
            }
            this.mRemoteTaskStackListeners.finishBroadcast();
        }
    }

    private void forAllLocalListeners(com.android.server.wm.TaskChangeNotificationController.TaskStackConsumer taskStackConsumer, android.os.Message message) {
        synchronized (this.mLocalTaskStackListeners) {
            for (int size = this.mLocalTaskStackListeners.size() - 1; size >= 0; size--) {
                try {
                    taskStackConsumer.accept(this.mLocalTaskStackListeners.get(size), message);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    void notifyTaskStackChanged() {
        this.mTaskSupervisor.getActivityMetricsLogger().logWindowState();
        this.mHandler.removeMessages(2);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(2);
        forAllLocalListeners(this.mNotifyTaskStackChanged, obtainMessage);
        this.mHandler.sendMessageDelayed(obtainMessage, 100L);
    }

    void notifyActivityPinned(com.android.server.wm.ActivityRecord activityRecord) {
        this.mHandler.removeMessages(3);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(3, activityRecord.getTask().mTaskId, activityRecord.getRootTaskId(), activityRecord.packageName);
        obtainMessage.sendingUid = activityRecord.mUserId;
        forAllLocalListeners(this.mNotifyActivityPinned, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityUnpinned() {
        this.mHandler.removeMessages(17);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(17);
        forAllLocalListeners(this.mNotifyActivityUnpinned, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityRestartAttempt(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
        this.mHandler.removeMessages(4);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = runningTaskInfo;
        obtain.argi1 = z ? 1 : 0;
        obtain.argi2 = z2 ? 1 : 0;
        obtain.argi3 = z3 ? 1 : 0;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(4, obtain);
        forAllLocalListeners(this.mNotifyActivityRestartAttempt, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityDismissingDockedRootTask() {
        this.mHandler.removeMessages(7);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(7);
        forAllLocalListeners(this.mNotifyActivityDismissingDockedTask, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityForcedResizable(int i, int i2, java.lang.String str) {
        this.mHandler.removeMessages(6);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(6, i, i2, str);
        forAllLocalListeners(this.mNotifyActivityForcedResizable, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityLaunchOnSecondaryDisplayFailed(android.app.TaskInfo taskInfo, int i) {
        this.mHandler.removeMessages(18);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(18, i, 0, taskInfo);
        forAllLocalListeners(this.mNotifyActivityLaunchOnSecondaryDisplayFailed, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityLaunchOnSecondaryDisplayRerouted(android.app.TaskInfo taskInfo, int i) {
        this.mHandler.removeMessages(19);
        android.os.Message obtainMessage = this.mHandler.obtainMessage(19, i, 0, taskInfo);
        forAllLocalListeners(this.mNotifyActivityLaunchOnSecondaryDisplayRerouted, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskCreated(int i, android.content.ComponentName componentName) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(8, i, 0, componentName);
        forAllLocalListeners(this.mNotifyTaskCreated, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskRemoved(int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(9, i, 0);
        forAllLocalListeners(this.mNotifyTaskRemoved, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskMovedToFront(android.app.TaskInfo taskInfo) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(10, taskInfo);
        forAllLocalListeners(this.mNotifyTaskMovedToFront, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskDescriptionChanged(android.app.TaskInfo taskInfo) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(11, taskInfo);
        forAllLocalListeners(this.mNotifyTaskDescriptionChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyActivityRequestedOrientationChanged(int i, int i2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(12, i, i2);
        forAllLocalListeners(this.mNotifyActivityRequestedOrientationChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskRemovalStarted(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(13, runningTaskInfo);
        forAllLocalListeners(this.mNotifyTaskRemovalStarted, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskProfileLocked(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(14, i, 0, runningTaskInfo);
        forAllLocalListeners(this.mNotifyTaskProfileLocked, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskSnapshotChanged(int i, android.window.TaskSnapshot taskSnapshot) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(15, i, 0, taskSnapshot);
        forAllLocalListeners(this.mNotifyTaskSnapshotChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyBackPressedOnTaskRoot(android.app.TaskInfo taskInfo) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(20, taskInfo);
        forAllLocalListeners(this.mNotifyBackPressedOnTaskRoot, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskDisplayChanged(int i, int i2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(21, i, i2);
        forAllLocalListeners(this.mNotifyTaskDisplayChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskListUpdated() {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(22);
        forAllLocalListeners(this.mNotifyTaskListUpdated, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskListFrozen(boolean z) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(23, z ? 1 : 0, 0);
        forAllLocalListeners(this.mNotifyTaskListFrozen, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskFocusChanged(int i, boolean z) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(24, i, z ? 1 : 0);
        forAllLocalListeners(this.mNotifyTaskFocusChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskRequestedOrientationChanged(int i, int i2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(25, i, i2);
        forAllLocalListeners(this.mNotifyTaskRequestedOrientationChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyOnActivityRotation(int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(26, i, 0);
        forAllLocalListeners(this.mNotifyOnActivityRotation, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyTaskMovedToBack(android.app.TaskInfo taskInfo) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(27, taskInfo);
        forAllLocalListeners(this.mNotifyTaskMovedToBack, obtainMessage);
        obtainMessage.sendToTarget();
    }

    void notifyLockTaskModeChanged(int i) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(28, i, 0);
        forAllLocalListeners(this.mNotifyLockTaskModeChanged, obtainMessage);
        obtainMessage.sendToTarget();
    }
}
