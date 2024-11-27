package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class PlayerProxy {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PlayerProxy";
    private final android.media.AudioPlaybackConfiguration mConf;

    PlayerProxy(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (audioPlaybackConfiguration == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPlaybackConfiguration");
        }
        this.mConf = audioPlaybackConfiguration;
    }

    @android.annotation.SystemApi
    public void start() {
        try {
            this.mConf.getIPlayer().start();
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for start operation, player already released?", e);
        }
    }

    @android.annotation.SystemApi
    public void pause() {
        try {
            this.mConf.getIPlayer().pause();
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for pause operation, player already released?", e);
        }
    }

    @android.annotation.SystemApi
    public void stop() {
        try {
            this.mConf.getIPlayer().stop();
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for stop operation, player already released?", e);
        }
    }

    @android.annotation.SystemApi
    public void setVolume(float f) {
        try {
            this.mConf.getIPlayer().setVolume(f);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for setVolume operation, player already released?", e);
        }
    }

    @android.annotation.SystemApi
    public void setPan(float f) {
        try {
            this.mConf.getIPlayer().setPan(f);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for setPan operation, player already released?", e);
        }
    }

    @android.annotation.SystemApi
    public void setStartDelayMs(int i) {
        try {
            this.mConf.getIPlayer().setStartDelayMs(i);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for setStartDelayMs operation, player already released?", e);
        }
    }

    public void applyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
        try {
            this.mConf.getIPlayer().applyVolumeShaper(configuration.toParcelable(), operation.toParcelable());
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("No player to proxy for applyVolumeShaper operation, player already released?", e);
        }
    }
}
