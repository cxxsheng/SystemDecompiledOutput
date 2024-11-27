package com.android.server.companion.virtual;

/* loaded from: classes.dex */
class PermissionUtils {
    private static final java.lang.String LOG_TAG = "VDM.PermissionUtils";

    PermissionUtils() {
    }

    public static boolean validateCallingPackageName(android.content.Context context, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int packageUidAsUser = context.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getUserId(callingUid));
            if (packageUidAsUser == callingUid) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            android.util.Slog.e(LOG_TAG, "validatePackageName: App with package name " + str + " is UID " + packageUidAsUser + " but caller is " + callingUid);
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(LOG_TAG, "validatePackageName: App with package name " + str + " does not exist");
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
