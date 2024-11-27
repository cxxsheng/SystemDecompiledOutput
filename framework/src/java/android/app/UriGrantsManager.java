package android.app;

/* loaded from: classes.dex */
public class UriGrantsManager {
    private static final android.util.Singleton<android.app.IUriGrantsManager> IUriGrantsManagerSingleton = new android.util.Singleton<android.app.IUriGrantsManager>() { // from class: android.app.UriGrantsManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.IUriGrantsManager create() {
            return android.app.IUriGrantsManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.URI_GRANTS_SERVICE));
        }
    };
    private final android.content.Context mContext;

    UriGrantsManager(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
    }

    public static android.app.IUriGrantsManager getService() {
        return IUriGrantsManagerSingleton.get();
    }

    public void clearGrantedUriPermissions(java.lang.String str) {
        try {
            getService().clearGrantedUriPermissions(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.ParceledListSlice<android.app.GrantedUriPermission> getGrantedUriPermissions(java.lang.String str) {
        try {
            return getService().getGrantedUriPermissions(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
