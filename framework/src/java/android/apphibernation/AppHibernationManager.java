package android.apphibernation;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class AppHibernationManager {
    private static final java.lang.String TAG = "AppHibernationManager";
    private final android.content.Context mContext;
    private final android.apphibernation.IAppHibernationService mIAppHibernationService = android.apphibernation.IAppHibernationService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.APP_HIBERNATION_SERVICE));

    public AppHibernationManager(android.content.Context context) {
        this.mContext = context;
    }

    @android.annotation.SystemApi
    public boolean isHibernatingForUser(java.lang.String str) {
        try {
            return this.mIAppHibernationService.isHibernatingForUser(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setHibernatingForUser(java.lang.String str, boolean z) {
        try {
            this.mIAppHibernationService.setHibernatingForUser(str, this.mContext.getUserId(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isHibernatingGlobally(java.lang.String str) {
        try {
            return this.mIAppHibernationService.isHibernatingGlobally(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setHibernatingGlobally(java.lang.String str, boolean z) {
        try {
            this.mIAppHibernationService.setHibernatingGlobally(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getHibernatingPackagesForUser() {
        try {
            return this.mIAppHibernationService.getHibernatingPackagesForUser(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(java.util.Set<java.lang.String> set) {
        try {
            return this.mIAppHibernationService.getHibernationStatsForUser(new java.util.ArrayList(set), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser() {
        try {
            return this.mIAppHibernationService.getHibernationStatsForUser(null, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isOatArtifactDeletionEnabled() {
        try {
            return this.mIAppHibernationService.isOatArtifactDeletionEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
