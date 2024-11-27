package android.debug;

/* loaded from: classes.dex */
public final class AdbNotifications {
    private static final java.lang.String ADB_NOTIFICATION_CHANNEL_ID_TV = "usbdevicemanager.adb.tv";

    public static android.app.Notification createNotification(android.content.Context context, byte b) {
        int i;
        int i2;
        android.app.PendingIntent pendingIntent;
        android.content.res.Resources resources = context.getResources();
        if (b == 0) {
            i = com.android.internal.R.string.adb_active_notification_title;
            i2 = com.android.internal.R.string.adb_active_notification_message;
        } else if (b == 1) {
            i = com.android.internal.R.string.adbwifi_active_notification_title;
            i2 = com.android.internal.R.string.adbwifi_active_notification_message;
        } else {
            throw new java.lang.IllegalArgumentException("createNotification called with unknown transport type=" + ((int) b));
        }
        java.lang.CharSequence text = resources.getText(i);
        java.lang.CharSequence text2 = resources.getText(i2);
        android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        intent.addFlags(268468224);
        android.content.pm.ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 1048576);
        if (resolveActivity == null) {
            pendingIntent = null;
        } else {
            intent.setPackage(resolveActivity.activityInfo.packageName);
            pendingIntent = android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT);
        }
        return new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.DEVELOPER_IMPORTANT).setSmallIcon(com.android.internal.R.drawable.stat_sys_adb).setWhen(0L).setOngoing(true).setTicker(text).setDefaults(0).setColor(context.getColor(17170460)).setContentTitle(text).setContentText(text2).setContentIntent(pendingIntent).setVisibility(1).extend(new android.app.Notification.TvExtender().setChannelId(ADB_NOTIFICATION_CHANNEL_ID_TV)).build();
    }
}
