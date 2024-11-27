package android.content.rollback;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RollbackManager {
    public static final java.lang.String EXTRA_STATUS = "android.content.rollback.extra.STATUS";
    public static final java.lang.String EXTRA_STATUS_MESSAGE = "android.content.rollback.extra.STATUS_MESSAGE";
    public static final java.lang.String PROPERTY_ROLLBACK_LIFETIME_MILLIS = "rollback_lifetime_in_millis";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_INSTALL = 3;
    public static final int STATUS_FAILURE_ROLLBACK_UNAVAILABLE = 2;
    public static final int STATUS_SUCCESS = 0;
    private final android.content.rollback.IRollbackManager mBinder;
    private final java.lang.String mCallerPackageName;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public RollbackManager(android.content.Context context, android.content.rollback.IRollbackManager iRollbackManager) {
        this.mCallerPackageName = context.getPackageName();
        this.mBinder = iRollbackManager;
    }

    public java.util.List<android.content.rollback.RollbackInfo> getAvailableRollbacks() {
        try {
            return this.mBinder.getAvailableRollbacks().getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.rollback.RollbackInfo> getRecentlyCommittedRollbacks() {
        try {
            return this.mBinder.getRecentlyCommittedRollbacks().getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commitRollback(int i, java.util.List<android.content.pm.VersionedPackage> list, android.content.IntentSender intentSender) {
        try {
            this.mBinder.commitRollback(i, new android.content.pm.ParceledListSlice(list), this.mCallerPackageName, intentSender);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reloadPersistedData() {
        try {
            this.mBinder.reloadPersistedData();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void expireRollbackForPackage(java.lang.String str) {
        try {
            this.mBinder.expireRollbackForPackage(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void blockRollbackManager(long j) {
        try {
            this.mBinder.blockRollbackManager(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
