package com.android.server.permission.access.appop;

/* compiled from: PackageAppOpUpgrade.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpUpgrade {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.appop.PackageAppOpPolicy policy;

    public PackageAppOpUpgrade(@org.jetbrains.annotations.NotNull com.android.server.permission.access.appop.PackageAppOpPolicy policy) {
        this.policy = policy;
    }

    public final void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        if (version <= 2) {
            com.android.server.permission.access.appop.PackageAppOpPolicy $this$upgradePackageState_u24lambda_u240 = this.policy;
            int appOpMode = $this$upgradePackageState_u24lambda_u240.getAppOpMode($this$upgradePackageState, packageState.getPackageName(), userId, "android:run_in_background");
            $this$upgradePackageState_u24lambda_u240.setAppOpMode($this$upgradePackageState, packageState.getPackageName(), userId, "android:run_any_in_background", appOpMode);
        }
    }
}
