package android.transparency;

/* loaded from: classes3.dex */
public class BinaryTransparencyManager {
    private static final java.lang.String TAG = "TransparencyManager";
    private final android.content.Context mContext;
    private final com.android.internal.os.IBinaryTransparencyService mService;

    public BinaryTransparencyManager(android.content.Context context, com.android.internal.os.IBinaryTransparencyService iBinaryTransparencyService) {
        this.mContext = context;
        this.mService = iBinaryTransparencyService;
    }

    public java.lang.String getSignedImageInfo() {
        try {
            return this.mService.getSignedImageInfo();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<com.android.internal.os.IBinaryTransparencyService.ApexInfo> collectAllApexInfo(boolean z) {
        try {
            return this.mService.collectAllApexInfo(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllUpdatedPreloadInfo(android.os.Bundle bundle) {
        try {
            android.util.Slog.d(TAG, "Calling backend's collectAllUpdatedPreloadInfo()");
            return this.mService.collectAllUpdatedPreloadInfo(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<com.android.internal.os.IBinaryTransparencyService.AppInfo> collectAllSilentInstalledMbaInfo(android.os.Bundle bundle) {
        try {
            android.util.Slog.d(TAG, "Calling backend's collectAllSilentInstalledMbaInfo()");
            return this.mService.collectAllSilentInstalledMbaInfo(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
