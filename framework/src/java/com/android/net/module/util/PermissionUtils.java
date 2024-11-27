package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class PermissionUtils {
    public static boolean hasAnyPermissionOf(android.content.Context context, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (context.checkCallingOrSelfPermission(str) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnyPermissionOf(android.content.Context context, int i, int i2, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (context.checkPermission(str, i, i2) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void enforceAnyPermissionOf(android.content.Context context, java.lang.String... strArr) {
        if (!hasAnyPermissionOf(context, strArr)) {
            throw new java.lang.SecurityException("Requires one of the following permissions: " + java.lang.String.join(", ", strArr) + android.media.MediaMetrics.SEPARATOR);
        }
    }

    public static void enforceNetworkStackPermission(android.content.Context context) {
        enforceNetworkStackPermissionOr(context, new java.lang.String[0]);
    }

    public static void enforceNetworkStackPermissionOr(android.content.Context context, java.lang.String... strArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(java.util.Arrays.asList(strArr));
        arrayList.add(android.Manifest.permission.NETWORK_STACK);
        arrayList.add(android.net.NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK);
        enforceAnyPermissionOf(context, (java.lang.String[]) arrayList.toArray(new java.lang.String[0]));
    }

    public static void enforceRestrictedNetworkPermission(android.content.Context context, java.lang.String str) {
        context.enforceCallingOrSelfPermission(android.Manifest.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS, str);
    }

    public static void enforceAccessNetworkStatePermission(android.content.Context context, java.lang.String str) {
        context.enforceCallingOrSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE, str);
    }

    public static boolean hasDumpPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.DUMP) != 0) {
            printWriter.println("Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }

    public static void enforceSystemFeature(android.content.Context context, java.lang.String str, java.lang.String str2) {
        if (!context.getPackageManager().hasSystemFeature(str)) {
            if (str2 == null) {
                throw new java.lang.UnsupportedOperationException();
            }
            throw new java.lang.UnsupportedOperationException(str2);
        }
    }

    public static java.util.List<java.lang.String> getGrantedPermissions(android.content.pm.PackageInfo packageInfo) {
        if (packageInfo.requestedPermissions == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(packageInfo.requestedPermissions.length);
        for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
            if ((packageInfo.requestedPermissionsFlags[i] & 2) != 0) {
                arrayList.add(packageInfo.requestedPermissions[i]);
            }
        }
        return arrayList;
    }

    public static void enforcePackageNameMatchesUid(android.content.Context context, int i, java.lang.String str) {
        if (getAppUid(context, str, android.os.UserHandle.getUserHandleForUid(i)) != i) {
            throw new java.lang.SecurityException(str + " does not belong to uid " + i);
        }
    }

    private static int getAppUid(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        android.content.pm.PackageManager packageManager = context.createContextAsUser(userHandle, 0).getPackageManager();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int packageUid = packageManager.getPackageUid(str, 0);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return packageUid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
