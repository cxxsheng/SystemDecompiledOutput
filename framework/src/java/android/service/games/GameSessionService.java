package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class GameSessionService extends android.app.Service {
    public static final java.lang.String ACTION_GAME_SESSION_SERVICE = "android.service.games.action.GAME_SESSION_SERVICE";
    private android.hardware.display.DisplayManager mDisplayManager;
    private final android.service.games.IGameSessionService mInterface = new android.service.games.GameSessionService.AnonymousClass1();

    public abstract android.service.games.GameSession onNewSession(android.service.games.CreateGameSessionRequest createGameSessionRequest);

    /* renamed from: android.service.games.GameSessionService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.games.IGameSessionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameSessionService
        public void create(android.service.games.IGameSessionController iGameSessionController, android.service.games.CreateGameSessionRequest createGameSessionRequest, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture androidFuture) {
            android.os.Handler.getMain().post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.QuintConsumer() { // from class: android.service.games.GameSessionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.service.games.GameSessionService) obj).doCreate((android.service.games.IGameSessionController) obj2, (android.service.games.CreateGameSessionRequest) obj3, (android.service.games.GameSessionViewHostConfiguration) obj4, (com.android.internal.infra.AndroidFuture) obj5);
                }
            }, android.service.games.GameSessionService.this, iGameSessionController, createGameSessionRequest, gameSessionViewHostConfiguration, androidFuture));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mDisplayManager = (android.hardware.display.DisplayManager) getSystemService(android.hardware.display.DisplayManager.class);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (intent == null || !ACTION_GAME_SESSION_SERVICE.equals(intent.getAction())) {
            return null;
        }
        return this.mInterface.asBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreate(android.service.games.IGameSessionController iGameSessionController, android.service.games.CreateGameSessionRequest createGameSessionRequest, android.service.games.GameSessionViewHostConfiguration gameSessionViewHostConfiguration, com.android.internal.infra.AndroidFuture<android.service.games.CreateGameSessionResult> androidFuture) {
        android.service.games.GameSession onNewSession = onNewSession(createGameSessionRequest);
        java.util.Objects.requireNonNull(onNewSession);
        android.view.Display display = this.mDisplayManager.getDisplay(gameSessionViewHostConfiguration.mDisplayId);
        if (display == null) {
            androidFuture.completeExceptionally(new java.lang.IllegalStateException("No display found for id: " + gameSessionViewHostConfiguration.mDisplayId));
            return;
        }
        android.content.Context createWindowContext = createWindowContext(display, 2038, null);
        android.view.SurfaceControlViewHost surfaceControlViewHost = new android.view.SurfaceControlViewHost(createWindowContext, display, new android.window.InputTransferToken(), "GameSessionService");
        onNewSession.attach(iGameSessionController, createGameSessionRequest.getTaskId(), createWindowContext, surfaceControlViewHost, gameSessionViewHostConfiguration.mWidthPx, gameSessionViewHostConfiguration.mHeightPx);
        androidFuture.complete(new android.service.games.CreateGameSessionResult(onNewSession.mInterface, surfaceControlViewHost.getSurfacePackage()));
        onNewSession.doCreate();
    }
}
