package android.permission;

/* loaded from: classes3.dex */
public class PermissionCheckerManager {
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.permission.IPermissionChecker mService = android.permission.IPermissionChecker.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.PERMISSION_CHECKER_SERVICE));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    public PermissionCheckerManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public int checkPermission(java.lang.String str, android.content.AttributionSourceState attributionSourceState, java.lang.String str2, boolean z, boolean z2, boolean z3, int i) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(attributionSourceState);
        if (android.app.AppOpsManager.permissionToOpCode(str) == -1) {
            if (!z3) {
                return this.mContext.checkPermission(str, attributionSourceState.pid, attributionSourceState.uid) == 0 ? 0 : 2;
            }
            if (attributionSourceState.next != null && attributionSourceState.next.length > 0) {
                return this.mContext.checkPermission(str, attributionSourceState.next[0].pid, attributionSourceState.next[0].uid) == 0 ? 0 : 2;
            }
        }
        try {
            return this.mService.checkPermission(str, attributionSourceState, str2, z, z2, z3, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return 2;
        }
    }

    public void finishDataDelivery(int i, android.content.AttributionSourceState attributionSourceState, boolean z) {
        java.util.Objects.requireNonNull(attributionSourceState);
        try {
            this.mService.finishDataDelivery(i, attributionSourceState, z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public int checkOp(int i, android.content.AttributionSourceState attributionSourceState, java.lang.String str, boolean z, boolean z2) {
        java.util.Objects.requireNonNull(attributionSourceState);
        try {
            return this.mService.checkOp(i, attributionSourceState, str, z, z2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return 2;
        }
    }
}
