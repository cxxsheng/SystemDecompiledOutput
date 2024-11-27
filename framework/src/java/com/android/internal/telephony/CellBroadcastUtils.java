package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class CellBroadcastUtils {
    private static final java.lang.String TAG = "CellBroadcastUtils";
    private static final boolean VDBG = false;

    public static java.lang.String getDefaultCellBroadcastReceiverPackageName(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.pm.ResolveInfo resolveActivity = packageManager.resolveActivity(new android.content.Intent(android.provider.Telephony.Sms.Intents.SMS_CB_RECEIVED_ACTION), 1048576);
        if (resolveActivity == null) {
            android.util.Log.e(TAG, "getDefaultCellBroadcastReceiverPackageName: no package found");
            return null;
        }
        java.lang.String str = resolveActivity.activityInfo.applicationInfo.packageName;
        if (android.text.TextUtils.isEmpty(str) || packageManager.checkPermission(android.Manifest.permission.READ_CELL_BROADCASTS, str) == -1) {
            android.util.Log.e(TAG, "getDefaultCellBroadcastReceiverPackageName: returning null; permission check failed for : " + str);
            return null;
        }
        return str;
    }

    public static android.content.ComponentName getDefaultCellBroadcastAlertDialogComponent(android.content.Context context) {
        java.lang.String defaultCellBroadcastReceiverPackageName = getDefaultCellBroadcastReceiverPackageName(context);
        if (android.text.TextUtils.isEmpty(defaultCellBroadcastReceiverPackageName)) {
            return null;
        }
        return android.content.ComponentName.createRelative(defaultCellBroadcastReceiverPackageName, "com.android.cellbroadcastreceiver.CellBroadcastAlertDialog");
    }
}
