package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class PackageUserStateUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PackageUserStateUtils";

    public static boolean isMatch(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, android.content.pm.ComponentInfo componentInfo, long j) {
        return isMatch(packageUserState, componentInfo.applicationInfo.isSystemApp(), componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.directBootAware, componentInfo.name, j);
    }

    public static boolean isMatch(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, boolean z, boolean z2, com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j) {
        return isMatch(packageUserState, z, z2, parsedMainComponent.isEnabled(), parsedMainComponent.isDirectBootAware(), parsedMainComponent.getName(), j);
    }

    public static boolean isMatch(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String str, long j) {
        boolean z5 = true;
        boolean z6 = (4202496 & j) != 0;
        if (!isAvailable(packageUserState, j) && (!z || !z6)) {
            return reportIfDebug(false, j);
        }
        if (!isEnabled(packageUserState, z2, z3, str, j)) {
            return reportIfDebug(false, j);
        }
        if ((1048576 & j) != 0 && !z) {
            return reportIfDebug(false, j);
        }
        boolean z7 = ((262144 & j) == 0 || z4) ? false : true;
        boolean z8 = (524288 & j) != 0 && z4;
        if (!z7 && !z8) {
            z5 = false;
        }
        return reportIfDebug(z5, j);
    }

    public static boolean isAvailable(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, long j) {
        boolean z = (4194304 & j) != 0;
        boolean z2 = (((8192 & j) > 0L ? 1 : ((8192 & j) == 0L ? 0 : -1)) != 0) || (((j & 4294967296L) > 0L ? 1 : ((j & 4294967296L) == 0L ? 0 : -1)) != 0);
        if (z) {
            return true;
        }
        if (!packageUserState.isInstalled()) {
            return z2 && packageUserState.dataExists();
        }
        if (packageUserState.isHidden()) {
            return z2;
        }
        return true;
    }

    public static boolean reportIfDebug(boolean z, long j) {
        return z;
    }

    public static boolean isEnabled(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, android.content.pm.ComponentInfo componentInfo, long j) {
        return isEnabled(packageUserState, componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.name, j);
    }

    public static boolean isEnabled(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, boolean z, com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j) {
        return isEnabled(packageUserState, z, parsedMainComponent.isEnabled(), parsedMainComponent.getName(), j);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0031 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0038 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isEnabled(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, boolean z, boolean z2, java.lang.String str, long j) {
        if ((512 & j) != 0) {
            return true;
        }
        if ((8589934592L & j) != 0 || !packageUserState.isQuarantined()) {
            switch (packageUserState.getEnabledState()) {
                case 0:
                    if (!z) {
                        return false;
                    }
                    if (packageUserState.isComponentEnabled(str)) {
                        return true;
                    }
                    if (packageUserState.isComponentDisabled(str)) {
                        return false;
                    }
                    return z2;
                case 1:
                default:
                    if (packageUserState.isComponentEnabled(str)) {
                    }
                    break;
                case 2:
                case 3:
                    return false;
                case 4:
                    if ((j & 32768) == 0) {
                        return false;
                    }
                    if (!z) {
                    }
                    if (packageUserState.isComponentEnabled(str)) {
                    }
                    break;
            }
        } else {
            return false;
        }
    }

    public static boolean isPackageEnabled(@android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        switch (packageUserState.getEnabledState()) {
            case 1:
                return true;
            case 2:
            case 3:
            case 4:
                return false;
            default:
                return androidPackage.isEnabled();
        }
    }
}
