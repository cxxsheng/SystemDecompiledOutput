package android.net;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class NetworkStack {

    @android.annotation.SystemApi
    public static final java.lang.String PERMISSION_MAINLINE_NETWORK_STACK = "android.permission.MAINLINE_NETWORK_STACK";
    private static volatile android.os.IBinder sMockService;

    @android.annotation.SystemApi
    public static android.os.IBinder getService() {
        android.os.IBinder iBinder = sMockService;
        return iBinder != null ? iBinder : android.os.ServiceManager.getService(android.content.Context.NETWORK_STACK_SERVICE);
    }

    public static void setServiceForTest(android.os.IBinder iBinder) {
        sMockService = iBinder;
    }

    private NetworkStack() {
    }

    @java.lang.Deprecated
    public static void checkNetworkStackPermission(android.content.Context context) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(context);
    }

    @java.lang.Deprecated
    public static void checkNetworkStackPermissionOr(android.content.Context context, java.lang.String... strArr) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(context, strArr);
    }
}
