package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class GameSession {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "GameSession";
    private android.content.Context mContext;
    private android.service.games.IGameSessionController mGameSessionController;
    private android.service.games.GameSession.GameSessionRootView mGameSessionRootView;
    private android.view.SurfaceControlViewHost mSurfaceControlViewHost;
    private int mTaskId;
    final android.service.games.IGameSession mInterface = new android.service.games.GameSession.AnonymousClass1();
    private android.service.games.GameSession.LifecycleState mLifecycleState = android.service.games.GameSession.LifecycleState.INITIALIZED;
    private boolean mAreTransientInsetsVisibleDueToGesture = false;

    public enum LifecycleState {
        INITIALIZED,
        CREATED,
        TASK_FOCUSED,
        TASK_UNFOCUSED,
        DESTROYED
    }

    public interface ScreenshotCallback {
        public static final int ERROR_TAKE_SCREENSHOT_INTERNAL_ERROR = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ScreenshotFailureStatus {
        }

        void onFailure(int i);

        void onSuccess();
    }

    /* renamed from: android.service.games.GameSession$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.games.IGameSession.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameSession
        public void onDestroyed() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.games.GameSession) obj).doDestroy();
                }
            }, android.service.games.GameSession.this));
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.games.GameSession) obj).dispatchTransientSystemBarVisibilityFromRevealGestureChanged(((java.lang.Boolean) obj2).booleanValue());
                }
            }, android.service.games.GameSession.this, java.lang.Boolean.valueOf(z)));
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean z) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.games.GameSession) obj).moveToState((android.service.games.GameSession.LifecycleState) obj2);
                }
            }, android.service.games.GameSession.this, z ? android.service.games.GameSession.LifecycleState.TASK_FOCUSED : android.service.games.GameSession.LifecycleState.TASK_UNFOCUSED));
        }
    }

    public void attach(android.service.games.IGameSessionController iGameSessionController, int i, android.content.Context context, android.view.SurfaceControlViewHost surfaceControlViewHost, int i2, int i3) {
        this.mGameSessionController = iGameSessionController;
        this.mTaskId = i;
        this.mContext = context;
        this.mSurfaceControlViewHost = surfaceControlViewHost;
        this.mGameSessionRootView = new android.service.games.GameSession.GameSessionRootView(context, this.mSurfaceControlViewHost);
        surfaceControlViewHost.setView(this.mGameSessionRootView, i2, i3);
    }

    void doCreate() {
        moveToState(android.service.games.GameSession.LifecycleState.CREATED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy() {
        this.mSurfaceControlViewHost.release();
        moveToState(android.service.games.GameSession.LifecycleState.DESTROYED);
    }

    public void dispatchTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
        boolean z2 = this.mAreTransientInsetsVisibleDueToGesture != z;
        this.mAreTransientInsetsVisibleDueToGesture = z;
        if (z2) {
            onTransientSystemBarVisibilityFromRevealGestureChanged(z);
        }
    }

    public void moveToState(android.service.games.GameSession.LifecycleState lifecycleState) {
        if (android.os.Looper.myLooper() != android.os.Looper.getMainLooper()) {
            throw new java.lang.RuntimeException("moveToState should be used only from the main thread");
        }
        if (this.mLifecycleState == lifecycleState) {
            return;
        }
        switch (this.mLifecycleState) {
            case INITIALIZED:
                if (lifecycleState == android.service.games.GameSession.LifecycleState.CREATED) {
                    onCreate();
                    break;
                } else if (lifecycleState == android.service.games.GameSession.LifecycleState.DESTROYED) {
                    onCreate();
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case CREATED:
                if (lifecycleState == android.service.games.GameSession.LifecycleState.TASK_FOCUSED) {
                    onGameTaskFocusChanged(true);
                    break;
                } else if (lifecycleState == android.service.games.GameSession.LifecycleState.DESTROYED) {
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case TASK_FOCUSED:
                if (lifecycleState == android.service.games.GameSession.LifecycleState.TASK_UNFOCUSED) {
                    onGameTaskFocusChanged(false);
                    break;
                } else if (lifecycleState == android.service.games.GameSession.LifecycleState.DESTROYED) {
                    onGameTaskFocusChanged(false);
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case TASK_UNFOCUSED:
                if (lifecycleState == android.service.games.GameSession.LifecycleState.TASK_FOCUSED) {
                    onGameTaskFocusChanged(true);
                    break;
                } else if (lifecycleState == android.service.games.GameSession.LifecycleState.DESTROYED) {
                    onDestroy();
                    break;
                } else {
                    return;
                }
            case DESTROYED:
                return;
        }
        this.mLifecycleState = lifecycleState;
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public void onGameTaskFocusChanged(boolean z) {
    }

    public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
    }

    public void setTaskOverlayView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        this.mGameSessionRootView.removeAllViews();
        this.mGameSessionRootView.addView(view, layoutParams);
    }

    public final boolean restartGame() {
        try {
            this.mGameSessionController.restartGame(this.mTaskId);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to restart game", e);
            return false;
        }
    }

    private static final class GameSessionRootView extends android.widget.FrameLayout {
        private final android.view.SurfaceControlViewHost mSurfaceControlViewHost;

        GameSessionRootView(android.content.Context context, android.view.SurfaceControlViewHost surfaceControlViewHost) {
            super(context);
            this.mSurfaceControlViewHost = surfaceControlViewHost;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(android.content.res.Configuration configuration) {
            super.onConfigurationChanged(configuration);
            android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
            this.mSurfaceControlViewHost.relayout(bounds.width(), bounds.height());
        }
    }

    public void takeScreenshot(java.util.concurrent.Executor executor, final android.service.games.GameSession.ScreenshotCallback screenshotCallback) {
        if (this.mGameSessionController == null) {
            throw new java.lang.IllegalStateException("Can not call before onCreate()");
        }
        com.android.internal.infra.AndroidFuture whenCompleteAsync = new com.android.internal.infra.AndroidFuture().whenCompleteAsync(new java.util.function.BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.service.games.GameSession.this.lambda$takeScreenshot$0(screenshotCallback, (android.service.games.GameScreenshotResult) obj, (java.lang.Throwable) obj2);
            }
        }, executor);
        try {
            this.mGameSessionController.takeScreenshot(this.mTaskId, whenCompleteAsync);
        } catch (android.os.RemoteException e) {
            whenCompleteAsync.completeExceptionally(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleScreenshotResult, reason: merged with bridge method [inline-methods] */
    public void lambda$takeScreenshot$0(android.service.games.GameSession.ScreenshotCallback screenshotCallback, android.service.games.GameScreenshotResult gameScreenshotResult, java.lang.Throwable th) {
        if (th != null) {
            android.util.Slog.w(TAG, th.getMessage(), th.getCause());
            screenshotCallback.onFailure(0);
        }
        switch (gameScreenshotResult.getStatus()) {
            case 0:
                screenshotCallback.onSuccess();
                break;
            case 1:
                android.util.Slog.w(TAG, "Error taking screenshot");
                screenshotCallback.onFailure(0);
                break;
        }
    }

    public final void startActivityFromGameSessionForResult(android.content.Intent intent, android.os.Bundle bundle, java.util.concurrent.Executor executor, final android.service.games.GameSessionActivityCallback gameSessionActivityCallback) {
        java.util.Objects.requireNonNull(intent);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(gameSessionActivityCallback);
        android.content.Intent createIntent = android.service.games.GameSessionTrampolineActivity.createIntent(intent, bundle, new com.android.internal.infra.AndroidFuture().whenCompleteAsync(new java.util.function.BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                android.service.games.GameSession.lambda$startActivityFromGameSessionForResult$1(android.service.games.GameSessionActivityCallback.this, (android.service.games.GameSessionActivityResult) obj, (java.lang.Throwable) obj2);
            }
        }, executor));
        try {
            android.app.Instrumentation.checkStartActivityResult(android.app.ActivityTaskManager.getService().startActivityFromGameSession(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), TAG, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), createIntent, this.mTaskId, android.os.UserHandle.myUserId()), createIntent);
        } catch (java.lang.Throwable th) {
            executor.execute(new java.lang.Runnable() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.games.GameSessionActivityCallback.this.onActivityStartFailed(th);
                }
            });
        }
    }

    static /* synthetic */ void lambda$startActivityFromGameSessionForResult$1(android.service.games.GameSessionActivityCallback gameSessionActivityCallback, android.service.games.GameSessionActivityResult gameSessionActivityResult, java.lang.Throwable th) {
        if (th != null) {
            gameSessionActivityCallback.onActivityStartFailed(th);
        } else {
            gameSessionActivityCallback.onActivityResult(gameSessionActivityResult.getResultCode(), gameSessionActivityResult.getData());
        }
    }
}
