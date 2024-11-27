package android.window;

/* loaded from: classes4.dex */
public class TaskOrganizer extends android.window.WindowOrganizer {
    private final java.util.concurrent.Executor mExecutor;
    private final android.window.ITaskOrganizer mInterface;
    private final android.window.ITaskOrganizerController mTaskOrganizerController;

    public TaskOrganizer() {
        this(null, null);
    }

    public TaskOrganizer(android.window.ITaskOrganizerController iTaskOrganizerController, java.util.concurrent.Executor executor) {
        this.mInterface = new android.window.TaskOrganizer.AnonymousClass1();
        this.mExecutor = executor == null ? new android.app.PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mTaskOrganizerController = iTaskOrganizerController == null ? getController() : iTaskOrganizerController;
    }

    public java.util.List<android.window.TaskAppearedInfo> registerOrganizer() {
        try {
            return this.mTaskOrganizerController.registerTaskOrganizer(this.mInterface).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterOrganizer() {
        try {
            this.mTaskOrganizerController.unregisterTaskOrganizer(this.mInterface);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addStartingWindow(android.window.StartingWindowInfo startingWindowInfo) {
    }

    public void removeStartingWindow(android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) {
    }

    public void copySplashScreenView(int i) {
    }

    public void onAppSplashScreenViewRemoved(int i) {
    }

    public void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) {
    }

    public void onTaskVanished(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void onTaskInfoChanged(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void onBackPressedOnTaskRoot(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void onImeDrawnOnTask(int i) {
    }

    public void createRootTask(int i, int i2, android.os.IBinder iBinder, boolean z) {
        try {
            this.mTaskOrganizerController.createRootTask(i, i2, iBinder, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createRootTask(int i, int i2, android.os.IBinder iBinder) {
        createRootTask(i, i2, iBinder, false);
    }

    public boolean deleteRootTask(android.window.WindowContainerToken windowContainerToken) {
        try {
            return this.mTaskOrganizerController.deleteRootTask(windowContainerToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getChildTasks(android.window.WindowContainerToken windowContainerToken, int[] iArr) {
        try {
            return this.mTaskOrganizerController.getChildTasks(windowContainerToken, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) {
        try {
            return this.mTaskOrganizerController.getRootTasks(i, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.window.WindowContainerToken getImeTarget(int i) {
        try {
            return this.mTaskOrganizerController.getImeTarget(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInterceptBackPressedOnTaskRoot(android.window.WindowContainerToken windowContainerToken, boolean z) {
        try {
            this.mTaskOrganizerController.setInterceptBackPressedOnTaskRoot(windowContainerToken, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restartTaskTopActivityProcessIfVisible(android.window.WindowContainerToken windowContainerToken) {
        try {
            this.mTaskOrganizerController.restartTaskTopActivityProcessIfVisible(windowContainerToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateCameraCompatControlState(android.window.WindowContainerToken windowContainerToken, int i) {
        try {
            this.mTaskOrganizerController.updateCameraCompatControlState(windowContainerToken, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    /* renamed from: android.window.TaskOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends android.window.ITaskOrganizer.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addStartingWindow$0(android.window.StartingWindowInfo startingWindowInfo) {
            android.window.TaskOrganizer.this.addStartingWindow(startingWindowInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void addStartingWindow(final android.window.StartingWindowInfo startingWindowInfo) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$addStartingWindow$0(startingWindowInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeStartingWindow$1(android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) {
            android.window.TaskOrganizer.this.removeStartingWindow(startingWindowRemovalInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void removeStartingWindow(final android.window.StartingWindowRemovalInfo startingWindowRemovalInfo) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$removeStartingWindow$1(startingWindowRemovalInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copySplashScreenView$2(int i) {
            android.window.TaskOrganizer.this.copySplashScreenView(i);
        }

        @Override // android.window.ITaskOrganizer
        public void copySplashScreenView(final int i) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$copySplashScreenView$2(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppSplashScreenViewRemoved$3(int i) {
            android.window.TaskOrganizer.this.onAppSplashScreenViewRemoved(i);
        }

        @Override // android.window.ITaskOrganizer
        public void onAppSplashScreenViewRemoved(final int i) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onAppSplashScreenViewRemoved$3(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskAppeared$4(android.app.ActivityManager.RunningTaskInfo runningTaskInfo, android.view.SurfaceControl surfaceControl) {
            android.window.TaskOrganizer.this.onTaskAppeared(runningTaskInfo, surfaceControl);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskAppeared(final android.app.ActivityManager.RunningTaskInfo runningTaskInfo, final android.view.SurfaceControl surfaceControl) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onTaskAppeared$4(runningTaskInfo, surfaceControl);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskVanished$5(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.onTaskVanished(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskVanished(final android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onTaskVanished$5(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskInfoChanged$6(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.onTaskInfoChanged(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskInfoChanged(final android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onTaskInfoChanged$6(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackPressedOnTaskRoot$7(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.onBackPressedOnTaskRoot(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onBackPressedOnTaskRoot(final android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onBackPressedOnTaskRoot$7(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImeDrawnOnTask$8(int i) {
            android.window.TaskOrganizer.this.onImeDrawnOnTask(i);
        }

        @Override // android.window.ITaskOrganizer
        public void onImeDrawnOnTask(final int i) {
            android.window.TaskOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskOrganizer.AnonymousClass1.this.lambda$onImeDrawnOnTask$8(i);
                }
            });
        }
    }

    private android.window.ITaskOrganizerController getController() {
        try {
            return getWindowOrganizerController().getTaskOrganizerController();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }
}
