package android.app;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
/* loaded from: classes.dex */
public final class BackgroundInstallControlManager {
    private static final java.lang.String TAG = "BackgroundInstallControlManager";
    private static android.content.pm.IBackgroundInstallControlService sService;
    private final android.content.Context mContext;

    BackgroundInstallControlManager(android.content.Context context) {
        this.mContext = context;
    }

    private static android.content.pm.IBackgroundInstallControlService getService() {
        if (sService == null) {
            sService = android.content.pm.IBackgroundInstallControlService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.BACKGROUND_INSTALL_CONTROL_SERVICE));
        }
        return sService;
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.PackageInfo> getBackgroundInstalledPackages(long j) {
        try {
            return getService().getBackgroundInstalledPackages(j, this.mContext.getUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
