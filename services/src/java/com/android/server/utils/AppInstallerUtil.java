package com.android.server.utils;

/* loaded from: classes2.dex */
public class AppInstallerUtil {
    private static final java.lang.String LOG_TAG = "AppInstallerUtil";

    private static android.content.Intent resolveIntent(android.content.Context context, android.content.Intent intent) {
        android.content.pm.ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity != null) {
            return new android.content.Intent(intent.getAction()).setClassName(resolveActivity.activityInfo.packageName, resolveActivity.activityInfo.name);
        }
        return null;
    }

    public static java.lang.String getInstallerPackageName(android.content.Context context, java.lang.String str) {
        java.lang.String str2;
        try {
            str2 = context.getPackageManager().getInstallerPackageName(str);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(LOG_TAG, "Exception while retrieving the package installer of " + str, e);
            str2 = null;
        }
        if (str2 == null) {
            return null;
        }
        return str2;
    }

    public static android.content.Intent createIntent(android.content.Context context, java.lang.String str, java.lang.String str2) {
        android.content.Intent resolveIntent = resolveIntent(context, new android.content.Intent("android.intent.action.SHOW_APP_INFO").setPackage(str));
        if (resolveIntent != null) {
            resolveIntent.putExtra("android.intent.extra.PACKAGE_NAME", str2);
            resolveIntent.addFlags(268435456);
            return resolveIntent;
        }
        return null;
    }

    public static android.content.Intent createIntent(android.content.Context context, java.lang.String str) {
        return createIntent(context, getInstallerPackageName(context, str), str);
    }
}
