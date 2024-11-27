package android.os;

/* loaded from: classes3.dex */
public class PermissionEnforcer {
    private static final java.lang.String ACCESS_DENIED = "Access denied, requires: ";
    private final android.content.Context mContext;

    protected PermissionEnforcer() {
        this.mContext = null;
    }

    public PermissionEnforcer(android.content.Context context) {
        this.mContext = context;
    }

    protected int checkPermission(java.lang.String str, android.content.AttributionSource attributionSource) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, "");
    }

    protected int checkPermission(java.lang.String str, int i, int i2) {
        if (this.mContext.checkPermission(str, i, i2) == 0) {
            return 0;
        }
        return 2;
    }

    private static int permissionToOpCode(java.lang.String str) {
        return android.app.AppOpsManager.permissionToOpCode(str);
    }

    private static int permissionToOpCode$ravenwood(java.lang.String str) {
        return -1;
    }

    private boolean anyAppOps(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (permissionToOpCode(str) != -1) {
                return true;
            }
        }
        return false;
    }

    public void enforcePermission(java.lang.String str, android.content.AttributionSource attributionSource) throws java.lang.SecurityException {
        if (checkPermission(str, attributionSource) != 0) {
            throw new java.lang.SecurityException(ACCESS_DENIED + str);
        }
    }

    public void enforcePermission(java.lang.String str, int i, int i2) throws java.lang.SecurityException {
        if (permissionToOpCode(str) != -1) {
            enforcePermission(str, new android.content.AttributionSource(i2, null, null));
        } else if (checkPermission(str, i, i2) != 0) {
            throw new java.lang.SecurityException(ACCESS_DENIED + str);
        }
    }

    public void enforcePermissionAllOf(java.lang.String[] strArr, android.content.AttributionSource attributionSource) throws java.lang.SecurityException {
        for (java.lang.String str : strArr) {
            if (checkPermission(str, attributionSource) != 0) {
                throw new java.lang.SecurityException("Access denied, requires: allOf={" + java.lang.String.join(", ", strArr) + "}");
            }
        }
    }

    public void enforcePermissionAllOf(java.lang.String[] strArr, int i, int i2) throws java.lang.SecurityException {
        if (anyAppOps(strArr)) {
            enforcePermissionAllOf(strArr, new android.content.AttributionSource(i2, null, null));
            return;
        }
        for (java.lang.String str : strArr) {
            if (checkPermission(str, i, i2) != 0) {
                throw new java.lang.SecurityException("Access denied, requires: allOf={" + java.lang.String.join(", ", strArr) + "}");
            }
        }
    }

    public void enforcePermissionAnyOf(java.lang.String[] strArr, android.content.AttributionSource attributionSource) throws java.lang.SecurityException {
        for (java.lang.String str : strArr) {
            if (checkPermission(str, attributionSource) == 0) {
                return;
            }
        }
        throw new java.lang.SecurityException("Access denied, requires: anyOf={" + java.lang.String.join(", ", strArr) + "}");
    }

    public void enforcePermissionAnyOf(java.lang.String[] strArr, int i, int i2) throws java.lang.SecurityException {
        if (anyAppOps(strArr)) {
            enforcePermissionAnyOf(strArr, new android.content.AttributionSource(i2, null, null));
            return;
        }
        for (java.lang.String str : strArr) {
            if (checkPermission(str, i, i2) == 0) {
                return;
            }
        }
        throw new java.lang.SecurityException("Access denied, requires: anyOf={" + java.lang.String.join(", ", strArr) + "}");
    }

    public static android.os.PermissionEnforcer fromContext(android.content.Context context) {
        return (android.os.PermissionEnforcer) context.getSystemService(android.content.Context.PERMISSION_ENFORCER_SERVICE);
    }
}
