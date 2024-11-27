package com.android.server.media;

/* loaded from: classes2.dex */
class MediaServerUtils {
    MediaServerUtils() {
    }

    public static boolean isValidActivityComponentName(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        new android.content.Intent(str).setComponent(componentName);
        return !context.getPackageManager().queryIntentActivitiesAsUser(r0, 0, userHandle).isEmpty();
    }

    public static void enforcePackageName(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.lang.String str, int i) {
        if (i == 0 || i == 2000) {
            return;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("packageName may not be empty");
        }
        if (!android.os.UserHandle.isSameApp(i, ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)))) {
            throw new java.lang.IllegalArgumentException("packageName does not belong to the calling uid; pkg=" + str + ", uid=" + i + " (" + java.util.Arrays.toString(context.getPackageManager().getPackagesForUid(i)) + ")");
        }
    }

    public static boolean checkDumpPermission(android.content.Context context, java.lang.String str, java.io.PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump " + str + " from from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }
}
