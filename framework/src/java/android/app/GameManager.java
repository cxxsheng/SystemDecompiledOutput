package android.app;

/* loaded from: classes.dex */
public final class GameManager {
    public static final int GAME_MODE_BATTERY = 3;
    public static final int GAME_MODE_CUSTOM = 4;
    public static final int GAME_MODE_PERFORMANCE = 2;
    public static final int GAME_MODE_STANDARD = 1;
    public static final int GAME_MODE_UNSUPPORTED = 0;
    private static final java.lang.String TAG = "GameManager";
    private final android.content.Context mContext;
    private final android.app.IGameManagerService mService = android.app.IGameManagerService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.GAME_SERVICE));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GameMode {
    }

    GameManager(android.content.Context context, android.os.Handler handler) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public int getGameMode() {
        return getGameModeImpl(this.mContext.getPackageName(), this.mContext.getApplicationInfo().targetSdkVersion);
    }

    public int getGameMode(java.lang.String str) {
        try {
            return getGameModeImpl(str, this.mContext.getPackageManager().getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)).targetSdkVersion);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    private int getGameModeImpl(java.lang.String str, int i) {
        try {
            int gameMode = this.mService.getGameMode(str, this.mContext.getUserId());
            if (gameMode == 4 && i <= 33) {
                return 1;
            }
            return gameMode;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.app.GameModeInfo getGameModeInfo(java.lang.String str) {
        try {
            return this.mService.getGameModeInfo(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setGameMode(java.lang.String str, int i) {
        try {
            this.mService.setGameMode(str, i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getAvailableGameModes(java.lang.String str) {
        try {
            return this.mService.getAvailableGameModes(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAngleEnabled(java.lang.String str) {
        try {
            return this.mService.isAngleEnabled(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyGraphicsEnvironmentSetup() {
        try {
            this.mService.notifyGraphicsEnvironmentSetup(this.mContext.getPackageName(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGameState(android.app.GameState gameState) {
        try {
            this.mService.setGameState(this.mContext.getPackageName(), gameState, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setGameServiceProvider(java.lang.String str) {
        try {
            this.mService.setGameServiceProvider(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void updateCustomGameModeConfiguration(java.lang.String str, android.app.GameModeConfiguration gameModeConfiguration) {
        try {
            this.mService.updateCustomGameModeConfiguration(str, gameModeConfiguration, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
