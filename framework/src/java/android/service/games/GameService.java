package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class GameService extends android.app.Service {
    public static final java.lang.String ACTION_GAME_SERVICE = "android.service.games.action.GAME_SERVICE";
    public static final java.lang.String SERVICE_META_DATA = "android.game_service";
    private static final java.lang.String TAG = "GameService";
    private android.app.IGameManagerService mGameManagerService;
    private android.service.games.IGameServiceController mGameServiceController;
    private final android.service.games.IGameService mInterface = new android.service.games.GameService.AnonymousClass1();
    private final android.os.IBinder.DeathRecipient mGameManagerServiceDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.service.games.GameService$$ExternalSyntheticLambda1
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            android.service.games.GameService.this.lambda$new$0();
        }
    };

    /* renamed from: android.service.games.GameService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.games.IGameService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameService
        public void connected(android.service.games.IGameServiceController iGameServiceController) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.games.GameService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.games.GameService) obj).doOnConnected((android.service.games.IGameServiceController) obj2);
                }
            }, android.service.games.GameService.this, iGameServiceController));
        }

        @Override // android.service.games.IGameService
        public void disconnected() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.service.games.GameService$$ExternalSyntheticLambda0(), android.service.games.GameService.this));
        }

        @Override // android.service.games.IGameService
        public void gameStarted(android.service.games.GameStartedEvent gameStartedEvent) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.games.GameService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.games.GameService) obj).onGameStarted((android.service.games.GameStartedEvent) obj2);
                }
            }, android.service.games.GameService.this, gameStartedEvent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        android.util.Log.w(TAG, "System service binder died. Shutting down");
        android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.service.games.GameService$$ExternalSyntheticLambda0(), this));
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (ACTION_GAME_SERVICE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doOnConnected(android.service.games.IGameServiceController iGameServiceController) {
        this.mGameManagerService = android.app.IGameManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.GAME_SERVICE));
        java.util.Objects.requireNonNull(this.mGameManagerService);
        try {
            this.mGameManagerService.asBinder().linkToDeath(this.mGameManagerServiceDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Unable to link to death with system service");
        }
        this.mGameServiceController = iGameServiceController;
        onConnected();
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public void onGameStarted(android.service.games.GameStartedEvent gameStartedEvent) {
    }

    public final void createGameSession(int i) {
        if (this.mGameServiceController == null) {
            throw new java.lang.IllegalStateException("Can not call before connected()");
        }
        try {
            this.mGameServiceController.createGameSession(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Request for game session failed", e);
        }
    }
}
