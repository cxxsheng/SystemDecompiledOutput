package com.android.server.permission.access.appop;

/* compiled from: AppIdAppOpUpgrade.kt */
/* loaded from: classes2.dex */
public final class AppIdAppOpUpgrade {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.AppIdAppOpPolicy policy;

    public AppIdAppOpUpgrade(@org.jetbrains.annotations.NotNull com.android.server.permission.access.appop.AppIdAppOpPolicy policy) {
        this.policy = policy;
    }

    public final void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        if (version <= 2) {
            com.android.server.permission.access.appop.AppIdAppOpPolicy $this$upgradePackageState_u24lambda_u240 = this.policy;
            int appOpMode = $this$upgradePackageState_u24lambda_u240.getAppOpMode($this$upgradePackageState, packageState.getAppId(), userId, "android:run_in_background");
            $this$upgradePackageState_u24lambda_u240.setAppOpMode($this$upgradePackageState, packageState.getAppId(), userId, "android:run_any_in_background", appOpMode);
        }
        if (version <= 13) {
            java.lang.String permissionName = android.app.AppOpsManager.opToPermission(107);
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
            if (androidPackage.getRequestedPermissions().contains(permissionName)) {
                com.android.server.permission.access.appop.AppIdAppOpPolicy $this$upgradePackageState_u24lambda_u241 = this.policy;
                int appOpMode2 = $this$upgradePackageState_u24lambda_u241.getAppOpMode($this$upgradePackageState, packageState.getAppId(), userId, "android:schedule_exact_alarm");
                int defaultAppOpMode = android.app.AppOpsManager.opToDefaultMode(107);
                if (appOpMode2 == defaultAppOpMode) {
                    $this$upgradePackageState_u24lambda_u241.setAppOpMode($this$upgradePackageState, packageState.getAppId(), userId, "android:schedule_exact_alarm", 0);
                }
            }
        }
    }
}
