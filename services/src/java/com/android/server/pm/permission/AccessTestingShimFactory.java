package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class AccessTestingShimFactory {
    public static final java.lang.String DEVICE_CONFIG_SETTING = "selected_access_subsystem";
    private static final int RUN_BOTH_SUBSYSTEMS = 2;
    private static final int RUN_NEW_SUBSYSTEM = 1;
    private static final int RUN_OLD_SUBSYSTEM = 0;

    public static com.android.server.pm.permission.PermissionManagerServiceInterface getPms(android.content.Context context, java.util.function.Supplier<com.android.server.pm.permission.PermissionManagerServiceInterface> supplier, java.util.function.Supplier<com.android.server.pm.permission.PermissionManagerServiceInterface> supplier2) {
        switch (android.provider.DeviceConfig.getInt("privacy", DEVICE_CONFIG_SETTING, 0)) {
            case 1:
                return supplier2.get();
            case 2:
                return new com.android.server.pm.permission.PermissionManagerServiceTestingShim(supplier.get(), supplier2.get());
            default:
                return supplier.get();
        }
    }

    public static com.android.server.appop.AppOpsCheckingServiceInterface getAos(android.content.Context context, java.util.function.Supplier<com.android.server.appop.AppOpsCheckingServiceInterface> supplier, java.util.function.Supplier<com.android.server.appop.AppOpsCheckingServiceInterface> supplier2) {
        switch (android.provider.DeviceConfig.getInt("privacy", DEVICE_CONFIG_SETTING, 0)) {
            case 1:
                return supplier2.get();
            case 2:
                return new com.android.server.appop.AppOpsServiceTestingShim(supplier.get(), supplier2.get());
            default:
                return supplier.get();
        }
    }
}
