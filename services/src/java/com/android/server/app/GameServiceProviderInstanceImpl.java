package com.android.server.app;

/* loaded from: classes.dex */
final class GameServiceProviderInstanceImpl implements com.android.server.app.GameServiceProviderInstance {
    private static final int CREATE_GAME_SESSION_TIMEOUT_MS = 10000;
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "GameServiceProviderInstance";
    private final android.app.IActivityManager mActivityManager;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.app.IActivityTaskManager mActivityTaskManager;
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final android.content.Context mContext;
    private final com.android.internal.infra.ServiceConnector<android.service.games.IGameService> mGameServiceConnector;
    private final com.android.internal.infra.ServiceConnector<android.service.games.IGameSessionService> mGameSessionServiceConnector;
    private final com.android.server.app.GameTaskInfoProvider mGameTaskInfoProvider;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private volatile boolean mIsRunning;
    private final com.android.internal.util.ScreenshotHelper mScreenshotHelper;
    private final android.os.UserHandle mUserHandle;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private final com.android.server.wm.WindowManagerService mWindowManagerService;
    private final com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.games.IGameService> mGameServiceLifecycleCallbacks = new com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.games.IGameService>() { // from class: com.android.server.app.GameServiceProviderInstanceImpl.1
        public void onConnected(@android.annotation.NonNull android.service.games.IGameService iGameService) {
            try {
                iGameService.connected(com.android.server.app.GameServiceProviderInstanceImpl.this.mGameServiceController);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.app.GameServiceProviderInstanceImpl.TAG, "Failed to send connected event", e);
            }
        }
    };
    private final com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.games.IGameSessionService> mGameSessionServiceLifecycleCallbacks = new com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass2();
    private final com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener mTaskSystemBarsVisibilityListener = new com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener() { // from class: com.android.server.app.GameServiceProviderInstanceImpl.3
        @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
        public void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onTransientSystemBarsVisibilityChanged(i, z, z2);
        }
    };
    private final android.app.TaskStackListener mTaskStackListener = new com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass4();
    private final android.app.IProcessObserver mProcessObserver = new com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass5();
    private final android.service.games.IGameServiceController mGameServiceController = new com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass6();
    private final android.service.games.IGameSessionController mGameSessionController = new com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass7();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, com.android.server.app.GameSessionRecord> mGameSessions = new java.util.concurrent.ConcurrentHashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, java.lang.String> mPidToPackageMap = new java.util.concurrent.ConcurrentHashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> mPackageNameToProcessCountMap = new java.util.concurrent.ConcurrentHashMap<>();

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$2, reason: invalid class name */
    class AnonymousClass2 implements com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks<android.service.games.IGameSessionService> {
        AnonymousClass2() {
        }

        public void onBinderDied() {
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass2.this.lambda$onBinderDied$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBinderDied$0() {
            synchronized (com.android.server.app.GameServiceProviderInstanceImpl.this.mLock) {
                com.android.server.app.GameServiceProviderInstanceImpl.this.destroyAndClearAllGameSessionsLocked();
            }
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$4, reason: invalid class name */
    class AnonymousClass4 extends android.app.TaskStackListener {
        AnonymousClass4() {
        }

        public void onTaskCreated(final int i, final android.content.ComponentName componentName) throws android.os.RemoteException {
            if (componentName == null) {
                return;
            }
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass4.this.lambda$onTaskCreated$0(i, componentName);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskCreated$0(int i, android.content.ComponentName componentName) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onTaskCreated(i, componentName);
        }

        public void onTaskRemoved(final int i) throws android.os.RemoteException {
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass4.this.lambda$onTaskRemoved$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskRemoved$1(int i) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onTaskRemoved(i);
        }

        public void onTaskFocusChanged(final int i, final boolean z) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass4.this.lambda$onTaskFocusChanged$2(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskFocusChanged$2(int i, boolean z) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onTaskFocusChanged(i, z);
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$5, reason: invalid class name */
    class AnonymousClass5 extends android.app.IProcessObserver.Stub {
        AnonymousClass5() {
        }

        public void onForegroundActivitiesChanged(final int i, int i2, boolean z) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass5.this.lambda$onForegroundActivitiesChanged$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onForegroundActivitiesChanged$0(int i) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onForegroundActivitiesChanged(i);
        }

        public void onProcessDied(final int i, int i2) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass5.this.lambda$onProcessDied$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessDied$1(int i) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.onProcessDied(i);
        }

        public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        }

        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$6, reason: invalid class name */
    class AnonymousClass6 extends android.service.games.IGameServiceController.Stub {
        AnonymousClass6() {
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_GAME_ACTIVITY")
        public void createGameSession(final int i) {
            super.createGameSession_enforcePermission();
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass6.this.lambda$createGameSession$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createGameSession$0(int i) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.createGameSession(i);
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$7, reason: invalid class name */
    class AnonymousClass7 extends android.service.games.IGameSessionController.Stub {
        AnonymousClass7() {
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_GAME_ACTIVITY")
        public void takeScreenshot(final int i, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture androidFuture) {
            super.takeScreenshot_enforcePermission();
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$7$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass7.this.lambda$takeScreenshot$0(i, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$takeScreenshot$0(int i, com.android.internal.infra.AndroidFuture androidFuture) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.takeScreenshot(i, androidFuture);
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_GAME_ACTIVITY")
        public void restartGame(final int i) {
            super.restartGame_enforcePermission();
            com.android.server.app.GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.app.GameServiceProviderInstanceImpl.AnonymousClass7.this.lambda$restartGame$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$restartGame$1(int i) {
            com.android.server.app.GameServiceProviderInstanceImpl.this.restartGame(i);
        }
    }

    GameServiceProviderInstanceImpl(@android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.app.GameTaskInfoProvider gameTaskInfoProvider, @android.annotation.NonNull android.app.IActivityManager iActivityManager, @android.annotation.NonNull android.app.ActivityManagerInternal activityManagerInternal, @android.annotation.NonNull android.app.IActivityTaskManager iActivityTaskManager, @android.annotation.NonNull com.android.server.wm.WindowManagerService windowManagerService, @android.annotation.NonNull com.android.server.wm.WindowManagerInternal windowManagerInternal, @android.annotation.NonNull com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal, @android.annotation.NonNull com.android.internal.infra.ServiceConnector<android.service.games.IGameService> serviceConnector, @android.annotation.NonNull com.android.internal.infra.ServiceConnector<android.service.games.IGameSessionService> serviceConnector2, @android.annotation.NonNull com.android.internal.util.ScreenshotHelper screenshotHelper) {
        this.mUserHandle = userHandle;
        this.mBackgroundExecutor = executor;
        this.mContext = context;
        this.mGameTaskInfoProvider = gameTaskInfoProvider;
        this.mActivityManager = iActivityManager;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mWindowManagerService = windowManagerService;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mGameServiceConnector = serviceConnector;
        this.mGameSessionServiceConnector = serviceConnector2;
        this.mScreenshotHelper = screenshotHelper;
    }

    @Override // com.android.server.app.GameServiceProviderInstance
    public void start() {
        synchronized (this.mLock) {
            startLocked();
        }
    }

    @Override // com.android.server.app.GameServiceProviderInstance
    public void stop() {
        synchronized (this.mLock) {
            stopLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startLocked() {
        if (this.mIsRunning) {
            return;
        }
        this.mIsRunning = true;
        this.mGameServiceConnector.setServiceLifecycleCallbacks(this.mGameServiceLifecycleCallbacks);
        this.mGameSessionServiceConnector.setServiceLifecycleCallbacks(this.mGameSessionServiceLifecycleCallbacks);
        this.mGameServiceConnector.connect();
        try {
            this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to register task stack listener", e);
        }
        try {
            this.mActivityManager.registerProcessObserver(this.mProcessObserver);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to register process observer", e2);
        }
        this.mWindowManagerInternal.registerTaskSystemBarsListener(this.mTaskSystemBarsVisibilityListener);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopLocked() {
        if (!this.mIsRunning) {
            return;
        }
        this.mIsRunning = false;
        try {
            this.mActivityManager.unregisterProcessObserver(this.mProcessObserver);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to unregister process observer", e);
        }
        try {
            this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to unregister task stack listener", e2);
        }
        this.mWindowManagerInternal.unregisterTaskSystemBarsListener(this.mTaskSystemBarsVisibilityListener);
        destroyAndClearAllGameSessionsLocked();
        this.mGameServiceConnector.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.games.IGameService) obj).disconnected();
            }
        }).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.app.GameServiceProviderInstanceImpl.this.lambda$stopLocked$0((java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
        this.mGameSessionServiceConnector.unbind();
        this.mGameServiceConnector.setServiceLifecycleCallbacks((com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks) null);
        this.mGameSessionServiceConnector.setServiceLifecycleCallbacks((com.android.internal.infra.ServiceConnector.ServiceLifecycleCallbacks) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopLocked$0(java.lang.Void r1, java.lang.Throwable th) {
        this.mGameServiceConnector.unbind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskCreated(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.server.app.GameTaskInfo gameTaskInfo = this.mGameTaskInfoProvider.get(i, componentName);
        if (!gameTaskInfo.mIsGameTask) {
            return;
        }
        synchronized (this.mLock) {
            gameTaskStartedLocked(gameTaskInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskFocusChanged(int i, boolean z) {
        synchronized (this.mLock) {
            onTaskFocusChangedLocked(i, z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onTaskFocusChangedLocked(int i, boolean z) {
        com.android.server.app.GameSessionRecord gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
        if (gameSessionRecord == null) {
            if (z) {
                maybeCreateGameSessionForFocusedTaskLocked(i);
            }
        } else {
            if (gameSessionRecord.getGameSession() == null) {
                return;
            }
            try {
                gameSessionRecord.getGameSession().onTaskFocusChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to notify session of task focus change: " + gameSessionRecord);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeCreateGameSessionForFocusedTaskLocked(int i) {
        com.android.server.app.GameTaskInfo gameTaskInfo = this.mGameTaskInfoProvider.get(i);
        if (gameTaskInfo == null) {
            android.util.Slog.w(TAG, "No task info for focused task: " + i);
            return;
        }
        if (!gameTaskInfo.mIsGameTask) {
            return;
        }
        gameTaskStartedLocked(gameTaskInfo);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void gameTaskStartedLocked(@android.annotation.NonNull final com.android.server.app.GameTaskInfo gameTaskInfo) {
        if (!this.mIsRunning) {
            return;
        }
        if (this.mGameSessions.get(java.lang.Integer.valueOf(gameTaskInfo.mTaskId)) != null) {
            android.util.Slog.w(TAG, "Existing game session found for task (id: " + gameTaskInfo.mTaskId + ") creation. Ignoring.");
            return;
        }
        this.mGameSessions.put(java.lang.Integer.valueOf(gameTaskInfo.mTaskId), com.android.server.app.GameSessionRecord.awaitingGameSessionRequest(gameTaskInfo.mTaskId, gameTaskInfo.mComponentName));
        this.mGameServiceConnector.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda3
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.app.GameServiceProviderInstanceImpl.lambda$gameTaskStartedLocked$1(com.android.server.app.GameTaskInfo.this, (android.service.games.IGameService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$gameTaskStartedLocked$1(com.android.server.app.GameTaskInfo gameTaskInfo, android.service.games.IGameService iGameService) throws java.lang.Exception {
        iGameService.gameStarted(new android.service.games.GameStartedEvent(gameTaskInfo.mTaskId, gameTaskInfo.mComponentName.getPackageName()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskRemoved(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mGameSessions.containsKey(java.lang.Integer.valueOf(i))) {
                    removeAndDestroyGameSessionIfNecessaryLocked(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
        com.android.server.app.GameSessionRecord gameSessionRecord;
        android.service.games.IGameSession gameSession;
        if (z && !z2) {
            return;
        }
        synchronized (this.mLock) {
            gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
        }
        if (gameSessionRecord == null || (gameSession = gameSessionRecord.getGameSession()) == null) {
            return;
        }
        try {
            gameSession.onTransientSystemBarVisibilityFromRevealGestureChanged(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to send transient system bars visibility from reveal gesture for task: " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createGameSession(int i) {
        synchronized (this.mLock) {
            createGameSessionLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void createGameSessionLocked(final int i) {
        if (!this.mIsRunning) {
            return;
        }
        final com.android.server.app.GameSessionRecord gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
        if (gameSessionRecord == null) {
            android.util.Slog.w(TAG, "No existing game session record found for task (id: " + i + ") creation. Ignoring.");
            return;
        }
        if (!gameSessionRecord.isAwaitingGameSessionRequest()) {
            android.util.Slog.w(TAG, "Existing game session for task (id: " + i + ") is not awaiting game session request. Ignoring.");
            return;
        }
        final android.service.games.GameSessionViewHostConfiguration createViewHostConfigurationForTask = createViewHostConfigurationForTask(i);
        if (createViewHostConfigurationForTask == null) {
            android.util.Slog.w(TAG, "Failed to create view host configuration for task (id" + i + ") creation. Ignoring.");
            return;
        }
        this.mGameSessions.put(java.lang.Integer.valueOf(i), gameSessionRecord.withGameSessionRequested());
        final com.android.internal.infra.AndroidFuture whenCompleteAsync = new com.android.internal.infra.AndroidFuture().orTimeout(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS).whenCompleteAsync(new java.util.function.BiConsumer() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.app.GameServiceProviderInstanceImpl.this.lambda$createGameSessionLocked$2(gameSessionRecord, i, (android.service.games.CreateGameSessionResult) obj, (java.lang.Throwable) obj2);
            }
        }, this.mBackgroundExecutor);
        this.mGameSessionServiceConnector.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda5
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.app.GameServiceProviderInstanceImpl.this.lambda$createGameSessionLocked$3(i, gameSessionRecord, createViewHostConfigurationForTask, whenCompleteAsync, (android.service.games.IGameSessionService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGameSessionLocked$2(com.android.server.app.GameSessionRecord gameSessionRecord, int i, android.service.games.CreateGameSessionResult createGameSessionResult, java.lang.Throwable th) {
        if (th != null || createGameSessionResult == null) {
            android.util.Slog.w(TAG, "Failed to create GameSession: " + gameSessionRecord, th);
            synchronized (this.mLock) {
                removeAndDestroyGameSessionIfNecessaryLocked(i);
            }
            return;
        }
        synchronized (this.mLock) {
            attachGameSessionLocked(i, createGameSessionResult);
        }
        setGameSessionFocusedIfNecessary(i, createGameSessionResult.getGameSession());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGameSessionLocked$3(int i, com.android.server.app.GameSessionRecord gameSessionRecord, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture androidFuture, android.service.games.IGameSessionService iGameSessionService) throws java.lang.Exception {
        iGameSessionService.create(this.mGameSessionController, new android.service.games.CreateGameSessionRequest(i, gameSessionRecord.getComponentName().getPackageName()), gameSessionViewHostConfiguration, androidFuture);
    }

    private void setGameSessionFocusedIfNecessary(int i, android.service.games.IGameSession iGameSession) {
        try {
            android.app.ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = this.mActivityTaskManager.getFocusedRootTaskInfo();
            if (focusedRootTaskInfo != null && focusedRootTaskInfo.taskId == i) {
                iGameSession.onTaskFocusChanged(true);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to set task focused for ID: " + i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void attachGameSessionLocked(int i, @android.annotation.NonNull android.service.games.CreateGameSessionResult createGameSessionResult) {
        com.android.server.app.GameSessionRecord gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
        if (gameSessionRecord == null) {
            android.util.Slog.w(TAG, "No associated game session record. Destroying id: " + i);
            destroyGameSessionDuringAttach(i, createGameSessionResult);
            return;
        }
        if (!gameSessionRecord.isGameSessionRequested()) {
            destroyGameSessionDuringAttach(i, createGameSessionResult);
            return;
        }
        try {
            this.mWindowManagerInternal.addTrustedTaskOverlay(i, createGameSessionResult.getSurfacePackage());
            this.mGameSessions.put(java.lang.Integer.valueOf(i), gameSessionRecord.withGameSession(createGameSessionResult.getGameSession(), createGameSessionResult.getSurfacePackage()));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Failed to add task overlay. Destroying id: " + i);
            destroyGameSessionDuringAttach(i, createGameSessionResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void destroyAndClearAllGameSessionsLocked() {
        java.util.Iterator<com.android.server.app.GameSessionRecord> it = this.mGameSessions.values().iterator();
        while (it.hasNext()) {
            destroyGameSessionFromRecordLocked(it.next());
        }
        this.mGameSessions.clear();
    }

    private void destroyGameSessionDuringAttach(int i, android.service.games.CreateGameSessionResult createGameSessionResult) {
        try {
            createGameSessionResult.getGameSession().onDestroyed();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to destroy session: " + i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void removeAndDestroyGameSessionIfNecessaryLocked(int i) {
        com.android.server.app.GameSessionRecord remove = this.mGameSessions.remove(java.lang.Integer.valueOf(i));
        if (remove == null) {
            return;
        }
        destroyGameSessionFromRecordLocked(remove);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void destroyGameSessionFromRecordLocked(@android.annotation.NonNull com.android.server.app.GameSessionRecord gameSessionRecord) {
        android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = gameSessionRecord.getSurfacePackage();
        if (surfacePackage != null) {
            try {
                this.mWindowManagerInternal.removeTrustedTaskOverlay(gameSessionRecord.getTaskId(), surfacePackage);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.i(TAG, "Failed to remove task overlay. This is expected if the task is already destroyed: " + gameSessionRecord);
            }
        }
        android.service.games.IGameSession gameSession = gameSessionRecord.getGameSession();
        if (gameSession != null) {
            try {
                gameSession.onDestroyed();
            } catch (android.os.RemoteException e2) {
                android.util.Slog.w(TAG, "Failed to destroy session: " + gameSessionRecord, e2);
            }
        }
        if (this.mGameSessions.isEmpty()) {
            this.mGameSessionServiceConnector.unbind();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onForegroundActivitiesChanged(int i) {
        synchronized (this.mLock) {
            onForegroundActivitiesChangedLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onForegroundActivitiesChangedLocked(int i) {
        if (this.mPidToPackageMap.containsKey(java.lang.Integer.valueOf(i))) {
            return;
        }
        java.lang.String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
        if (android.text.TextUtils.isEmpty(packageNameByPid) || !gameSessionExistsForPackageNameLocked(packageNameByPid)) {
            return;
        }
        this.mPidToPackageMap.put(java.lang.Integer.valueOf(i), packageNameByPid);
        int intValue = this.mPackageNameToProcessCountMap.getOrDefault(packageNameByPid, 0).intValue() + 1;
        this.mPackageNameToProcessCountMap.put(packageNameByPid, java.lang.Integer.valueOf(intValue));
        if (intValue > 0) {
            recreateEndedGameSessionsLocked(packageNameByPid);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void recreateEndedGameSessionsLocked(java.lang.String str) {
        for (com.android.server.app.GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
            if (gameSessionRecord.isGameSessionEndedForProcessDeath() && str.equals(gameSessionRecord.getComponentName().getPackageName())) {
                int taskId = gameSessionRecord.getTaskId();
                this.mGameSessions.put(java.lang.Integer.valueOf(taskId), com.android.server.app.GameSessionRecord.awaitingGameSessionRequest(taskId, gameSessionRecord.getComponentName()));
                createGameSessionLocked(gameSessionRecord.getTaskId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessDied(int i) {
        synchronized (this.mLock) {
            onProcessDiedLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onProcessDiedLocked(int i) {
        java.lang.String remove = this.mPidToPackageMap.remove(java.lang.Integer.valueOf(i));
        if (remove == null) {
            return;
        }
        java.lang.Integer num = this.mPackageNameToProcessCountMap.get(remove);
        if (num == null) {
            android.util.Slog.w(TAG, "onProcessDiedLocked(): Missing process count for package");
            return;
        }
        int intValue = num.intValue() - 1;
        this.mPackageNameToProcessCountMap.put(remove, java.lang.Integer.valueOf(intValue));
        if (intValue <= 0) {
            endGameSessionsForPackageLocked(remove);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void endGameSessionsForPackageLocked(java.lang.String str) {
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo;
        for (com.android.server.app.GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
            if (gameSessionRecord.getGameSession() != null && str.equals(gameSessionRecord.getComponentName().getPackageName()) && ((runningTaskInfo = this.mGameTaskInfoProvider.getRunningTaskInfo(gameSessionRecord.getTaskId())) == null || !runningTaskInfo.isVisible)) {
                this.mGameSessions.put(java.lang.Integer.valueOf(gameSessionRecord.getTaskId()), gameSessionRecord.withGameSessionEndedOnProcessDeath());
                destroyGameSessionFromRecordLocked(gameSessionRecord);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean gameSessionExistsForPackageNameLocked(java.lang.String str) {
        java.util.Iterator<com.android.server.app.GameSessionRecord> it = this.mGameSessions.values().iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getComponentName().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.Nullable
    private android.service.games.GameSessionViewHostConfiguration createViewHostConfigurationForTask(int i) {
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = this.mGameTaskInfoProvider.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            return null;
        }
        android.graphics.Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
        return new android.service.games.GameSessionViewHostConfiguration(runningTaskInfo.displayId, bounds.width(), bounds.height());
    }

    @com.android.internal.annotations.VisibleForTesting
    void takeScreenshot(final int i, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture androidFuture) {
        synchronized (this.mLock) {
            try {
                final com.android.server.app.GameSessionRecord gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
                if (gameSessionRecord == null) {
                    android.util.Slog.w(TAG, "No game session found for id: " + i);
                    androidFuture.complete(android.service.games.GameScreenshotResult.createInternalErrorResult());
                    return;
                }
                android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = gameSessionRecord.getSurfacePackage();
                final android.view.SurfaceControl surfaceControl = surfacePackage != null ? surfacePackage.getSurfaceControl() : null;
                this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.app.GameServiceProviderInstanceImpl.this.lambda$takeScreenshot$5(surfaceControl, i, androidFuture, gameSessionRecord);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$5(android.view.SurfaceControl surfaceControl, int i, final com.android.internal.infra.AndroidFuture androidFuture, com.android.server.app.GameSessionRecord gameSessionRecord) {
        android.window.ScreenCapture.LayerCaptureArgs.Builder builder = new android.window.ScreenCapture.LayerCaptureArgs.Builder((android.view.SurfaceControl) null);
        if (surfaceControl != null) {
            builder.setExcludeLayers(new android.view.SurfaceControl[]{surfaceControl});
        }
        android.graphics.Bitmap captureTaskBitmap = this.mWindowManagerService.captureTaskBitmap(i, builder);
        if (captureTaskBitmap == null) {
            android.util.Slog.w(TAG, "Could not get bitmap for id: " + i);
            androidFuture.complete(android.service.games.GameScreenshotResult.createInternalErrorResult());
            return;
        }
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = this.mGameTaskInfoProvider.getRunningTaskInfo(i);
        if (runningTaskInfo == null) {
            android.util.Slog.w(TAG, "Could not get running task info for id: " + i);
            androidFuture.complete(android.service.games.GameScreenshotResult.createInternalErrorResult());
        }
        this.mScreenshotHelper.takeScreenshot(new com.android.internal.util.ScreenshotRequest.Builder(3, 5).setTopComponent(gameSessionRecord.getComponentName()).setTaskId(i).setUserId(this.mUserHandle.getIdentifier()).setBitmap(captureTaskBitmap).setBoundsOnScreen(runningTaskInfo.configuration.windowConfiguration.getBounds()).setInsets(android.graphics.Insets.NONE).build(), com.android.internal.os.BackgroundThread.getHandler(), new java.util.function.Consumer() { // from class: com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.app.GameServiceProviderInstanceImpl.lambda$takeScreenshot$4(androidFuture, (android.net.Uri) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$takeScreenshot$4(com.android.internal.infra.AndroidFuture androidFuture, android.net.Uri uri) {
        if (uri == null) {
            androidFuture.complete(android.service.games.GameScreenshotResult.createInternalErrorResult());
        } else {
            androidFuture.complete(android.service.games.GameScreenshotResult.createSuccessResult());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartGame(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.app.GameSessionRecord gameSessionRecord = this.mGameSessions.get(java.lang.Integer.valueOf(i));
                if (gameSessionRecord == null) {
                    return;
                }
                java.lang.String packageName = gameSessionRecord.getComponentName().getPackageName();
                if (packageName == null) {
                    return;
                }
                this.mActivityTaskManagerInternal.restartTaskActivityProcessIfVisible(i, packageName);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
