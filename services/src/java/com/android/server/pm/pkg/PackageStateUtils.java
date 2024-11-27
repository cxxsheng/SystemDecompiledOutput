package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class PackageStateUtils {
    public static boolean isMatch(com.android.server.pm.pkg.PackageState packageState, long j) {
        if ((j & 1048576) != 0) {
            return packageState.isSystem();
        }
        return true;
    }

    public static int[] queryInstalledUsers(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int[] iArr, boolean z) {
        int i = 0;
        for (int i2 : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() == z) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i4).isInstalled() == z) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }

    public static boolean isEnabledAndMatches(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, android.content.pm.ComponentInfo componentInfo, long j, int i) {
        if (packageStateInternal == null) {
            return false;
        }
        return com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i), componentInfo, j);
    }

    public static boolean isEnabledAndMatches(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j, int i) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        if (packageStateInternal == null || (pkg = packageStateInternal.getPkg()) == null) {
            return false;
        }
        return com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i), packageStateInternal.isSystem(), pkg.isEnabled(), parsedMainComponent, j);
    }

    public static long getEarliestFirstInstallTime(@android.annotation.Nullable android.util.SparseArray<? extends com.android.server.pm.pkg.PackageUserStateInternal> sparseArray) {
        if (sparseArray == null || sparseArray.size() == 0) {
            return 0L;
        }
        long j = Long.MAX_VALUE;
        for (int i = 0; i < sparseArray.size(); i++) {
            long firstInstallTimeMillis = sparseArray.valueAt(i).getFirstInstallTimeMillis();
            if (firstInstallTimeMillis != 0 && firstInstallTimeMillis < j) {
                j = firstInstallTimeMillis;
            }
        }
        if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            return 0L;
        }
        return j;
    }
}
