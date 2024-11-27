package android.content;

/* loaded from: classes.dex */
public final class PermissionChecker {
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    public static final int PID_UNKNOWN = -1;
    private static volatile android.permission.IPermissionChecker sService;

    private PermissionChecker() {
    }

    public static int checkPermissionForDataDelivery(android.content.Context context, java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z) {
        return checkPermissionForDataDelivery(context, str, i, new android.content.AttributionSource(i2, i, str2, str3), str4, z);
    }

    public static int checkPermissionForDataDelivery(android.content.Context context, java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return checkPermissionForDataDelivery(context, str, i, i2, str2, str3, str4, false);
    }

    public static int checkPermissionForDataDeliveryFromDataSource(android.content.Context context, java.lang.String str, int i, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return checkPermissionForDataDeliveryCommon(context, str, attributionSource, str2, false, true);
    }

    public static int checkPermissionForDataDelivery(android.content.Context context, java.lang.String str, int i, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return checkPermissionForDataDelivery(context, str, i, attributionSource, str2, false);
    }

    public static int checkPermissionForDataDelivery(android.content.Context context, java.lang.String str, int i, android.content.AttributionSource attributionSource, java.lang.String str2, boolean z) {
        return checkPermissionForDataDeliveryCommon(context, str, attributionSource, str2, z, false);
    }

    private static int checkPermissionForDataDeliveryCommon(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2, boolean z, boolean z2) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), str2, true, z, z2, -1);
    }

    public static int checkPermissionAndStartDataDelivery(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), str2, true, true, false, -1);
    }

    public static int startOpForDataDelivery(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkOp(android.app.AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, true, true);
    }

    public static void finishDataDelivery(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource) {
        ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).finishDataDelivery(android.app.AppOpsManager.strOpToOp(str), attributionSource.asState(), false);
    }

    public static void finishDataDeliveryFromDatasource(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource) {
        ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).finishDataDelivery(android.app.AppOpsManager.strOpToOp(str), attributionSource.asState(), true);
    }

    public static int checkOpForPreflight(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkOp(android.app.AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, false, false);
    }

    public static int checkOpForDataDelivery(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkOp(android.app.AppOpsManager.strOpToOp(str), attributionSource.asState(), str2, true, false);
    }

    public static int checkPermissionForPreflight(android.content.Context context, java.lang.String str, int i, int i2, java.lang.String str2) {
        return checkPermissionForPreflight(context, str, new android.content.AttributionSource(i2, str2, null));
    }

    public static int checkPermissionForPreflight(android.content.Context context, java.lang.String str, android.content.AttributionSource attributionSource) {
        return ((android.permission.PermissionCheckerManager) context.getSystemService(android.permission.PermissionCheckerManager.class)).checkPermission(str, attributionSource.asState(), null, false, false, false, -1);
    }

    public static int checkSelfPermissionForDataDelivery(android.content.Context context, java.lang.String str, java.lang.String str2) {
        return checkPermissionForDataDelivery(context, str, android.os.Process.myPid(), android.os.Process.myUid(), context.getPackageName(), context.getAttributionTag(), str2, false);
    }

    public static int checkSelfPermissionForPreflight(android.content.Context context, java.lang.String str) {
        return checkPermissionForPreflight(context, str, android.os.Process.myPid(), android.os.Process.myUid(), context.getPackageName());
    }

    public static int checkCallingPermissionForDataDelivery(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return 2;
        }
        return checkPermissionForDataDelivery(context, str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, str3, str4, false);
    }

    public static int checkCallingPermissionForPreflight(android.content.Context context, java.lang.String str, java.lang.String str2) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return 2;
        }
        return checkPermissionForPreflight(context, str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2);
    }

    public static int checkCallingOrSelfPermissionForDataDelivery(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            str2 = context.getPackageName();
        }
        java.lang.String str5 = str2;
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            str3 = context.getAttributionTag();
        }
        return checkPermissionForDataDelivery(context, str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str5, str3, str4, false);
    }

    public static int checkCallingOrSelfPermissionForPreflight(android.content.Context context, java.lang.String str) {
        return checkPermissionForPreflight(context, str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid() == android.os.Process.myPid() ? context.getPackageName() : null);
    }
}
