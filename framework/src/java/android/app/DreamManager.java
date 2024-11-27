package android.app;

/* loaded from: classes.dex */
public class DreamManager {
    private final android.content.Context mContext;
    private final android.service.dreams.IDreamManager mService = android.service.dreams.IDreamManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.service.dreams.DreamService.DREAM_SERVICE));

    public DreamManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public boolean isScreensaverEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.SCREENSAVER_ENABLED, 0, -2) != 0;
    }

    public void setScreensaverEnabled(boolean z) {
        android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.SCREENSAVER_ENABLED, z ? 1 : 0, -2);
    }

    public boolean areDreamsSupported() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_dreamsSupported);
    }

    public void startDream() {
        try {
            this.mService.dream();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopDream() {
        try {
            this.mService.awaken();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setActiveDream(android.content.ComponentName componentName) {
        android.content.ComponentName[] componentNameArr = {componentName};
        try {
            android.service.dreams.IDreamManager iDreamManager = this.mService;
            int userId = this.mContext.getUserId();
            if (componentName == null) {
                componentNameArr = null;
            }
            iDreamManager.setDreamComponentsForUser(userId, componentNameArr);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setSystemDreamComponent(android.content.ComponentName componentName) {
        try {
            this.mService.setSystemDreamComponent(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDreamOverlay(android.content.ComponentName componentName) {
        try {
            this.mService.registerDreamOverlayService(componentName);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isDreaming() {
        try {
            return this.mService.isDreaming();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }
}
