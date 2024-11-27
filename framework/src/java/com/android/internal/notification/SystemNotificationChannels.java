package com.android.internal.notification;

/* loaded from: classes4.dex */
public class SystemNotificationChannels {

    @java.lang.Deprecated
    public static java.lang.String VIRTUAL_KEYBOARD = "VIRTUAL_KEYBOARD";
    public static java.lang.String PHYSICAL_KEYBOARD = "PHYSICAL_KEYBOARD";
    public static java.lang.String SECURITY = "SECURITY";
    public static java.lang.String CAR_MODE = "CAR_MODE";
    public static java.lang.String ACCOUNT = "ACCOUNT";
    public static java.lang.String DEVELOPER = "DEVELOPER";
    public static java.lang.String DEVELOPER_IMPORTANT = "DEVELOPER_IMPORTANT";
    public static java.lang.String UPDATES = "UPDATES";
    public static java.lang.String NETWORK_STATUS = "NETWORK_STATUS";
    public static java.lang.String NETWORK_ALERTS = "NETWORK_ALERTS";
    public static java.lang.String NETWORK_AVAILABLE = "NETWORK_AVAILABLE";
    public static java.lang.String VPN = android.net.VpnManager.NOTIFICATION_CHANNEL_VPN;

    @java.lang.Deprecated
    public static java.lang.String DEVICE_ADMIN_DEPRECATED = "DEVICE_ADMIN";
    public static java.lang.String DEVICE_ADMIN = "DEVICE_ADMIN_ALERTS";
    public static java.lang.String ALERTS = "ALERTS";
    public static java.lang.String RETAIL_MODE = "RETAIL_MODE";
    public static java.lang.String USB = "USB";
    public static java.lang.String FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
    public static java.lang.String HEAVY_WEIGHT_APP = "HEAVY_WEIGHT_APP";

    @java.lang.Deprecated
    public static java.lang.String SYSTEM_CHANGES_DEPRECATED = "SYSTEM_CHANGES";
    public static java.lang.String SYSTEM_CHANGES = "SYSTEM_CHANGES_ALERTS";
    public static java.lang.String DO_NOT_DISTURB = "DO_NOT_DISTURB";
    public static java.lang.String ACCESSIBILITY_MAGNIFICATION = "ACCESSIBILITY_MAGNIFICATION";
    public static java.lang.String ACCESSIBILITY_SECURITY_POLICY = "ACCESSIBILITY_SECURITY_POLICY";
    public static java.lang.String ABUSIVE_BACKGROUND_APPS = "ABUSIVE_BACKGROUND_APPS";

    public static void createAll(android.content.Context context) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(PHYSICAL_KEYBOARD, context.getString(com.android.internal.R.string.notification_channel_physical_keyboard), 2);
        notificationChannel.setBlockable(true);
        arrayList.add(notificationChannel);
        arrayList.add(new android.app.NotificationChannel(SECURITY, context.getString(com.android.internal.R.string.notification_channel_security), 2));
        android.app.NotificationChannel notificationChannel2 = new android.app.NotificationChannel(CAR_MODE, context.getString(com.android.internal.R.string.notification_channel_car_mode), 2);
        notificationChannel2.setBlockable(true);
        arrayList.add(notificationChannel2);
        arrayList.add(newAccountChannel(context));
        android.app.NotificationChannel notificationChannel3 = new android.app.NotificationChannel(DEVELOPER, context.getString(com.android.internal.R.string.notification_channel_developer), 2);
        notificationChannel3.setBlockable(true);
        arrayList.add(notificationChannel3);
        android.app.NotificationChannel notificationChannel4 = new android.app.NotificationChannel(DEVELOPER_IMPORTANT, context.getString(com.android.internal.R.string.notification_channel_developer_important), 4);
        notificationChannel3.setBlockable(true);
        arrayList.add(notificationChannel4);
        arrayList.add(new android.app.NotificationChannel(UPDATES, context.getString(com.android.internal.R.string.notification_channel_updates), 2));
        android.app.NotificationChannel notificationChannel5 = new android.app.NotificationChannel(NETWORK_STATUS, context.getString(com.android.internal.R.string.notification_channel_network_status), 2);
        notificationChannel5.setBlockable(true);
        arrayList.add(notificationChannel5);
        android.app.NotificationChannel notificationChannel6 = new android.app.NotificationChannel(NETWORK_ALERTS, context.getString(com.android.internal.R.string.notification_channel_network_alerts), 4);
        notificationChannel6.setBlockable(true);
        arrayList.add(notificationChannel6);
        android.app.NotificationChannel notificationChannel7 = new android.app.NotificationChannel(NETWORK_AVAILABLE, context.getString(com.android.internal.R.string.notification_channel_network_available), 2);
        notificationChannel7.setBlockable(true);
        arrayList.add(notificationChannel7);
        arrayList.add(new android.app.NotificationChannel(VPN, context.getString(com.android.internal.R.string.notification_channel_vpn), 2));
        arrayList.add(new android.app.NotificationChannel(DEVICE_ADMIN, getDeviceAdminNotificationChannelName(context), 4));
        arrayList.add(new android.app.NotificationChannel(ALERTS, context.getString(com.android.internal.R.string.notification_channel_alerts), 3));
        arrayList.add(new android.app.NotificationChannel(RETAIL_MODE, context.getString(com.android.internal.R.string.notification_channel_retail_mode), 2));
        arrayList.add(new android.app.NotificationChannel(USB, context.getString(com.android.internal.R.string.notification_channel_usb), 1));
        android.app.NotificationChannel notificationChannel8 = new android.app.NotificationChannel(FOREGROUND_SERVICE, context.getString(com.android.internal.R.string.notification_channel_foreground_service), 2);
        notificationChannel8.setBlockable(true);
        arrayList.add(notificationChannel8);
        android.app.NotificationChannel notificationChannel9 = new android.app.NotificationChannel(HEAVY_WEIGHT_APP, context.getString(com.android.internal.R.string.notification_channel_heavy_weight_app), 3);
        notificationChannel9.setShowBadge(false);
        notificationChannel9.setSound(null, new android.media.AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        arrayList.add(notificationChannel9);
        android.app.NotificationChannel notificationChannel10 = new android.app.NotificationChannel(SYSTEM_CHANGES, context.getString(com.android.internal.R.string.notification_channel_system_changes), 3);
        notificationChannel10.setSound(null, new android.media.AudioAttributes.Builder().setContentType(4).setUsage(5).build());
        arrayList.add(notificationChannel10);
        arrayList.add(new android.app.NotificationChannel(DO_NOT_DISTURB, context.getString(com.android.internal.R.string.notification_channel_do_not_disturb), 2));
        android.app.NotificationChannel notificationChannel11 = new android.app.NotificationChannel(ACCESSIBILITY_MAGNIFICATION, context.getString(com.android.internal.R.string.notification_channel_accessibility_magnification), 4);
        notificationChannel11.setBlockable(true);
        arrayList.add(notificationChannel11);
        arrayList.add(new android.app.NotificationChannel(ACCESSIBILITY_SECURITY_POLICY, context.getString(com.android.internal.R.string.notification_channel_accessibility_security_policy), 2));
        arrayList.add(new android.app.NotificationChannel(ABUSIVE_BACKGROUND_APPS, context.getString(com.android.internal.R.string.notification_channel_abusive_bg_apps), 2));
        notificationManager.createNotificationChannels(arrayList);
    }

    private static java.lang.String getDeviceAdminNotificationChannelName(final android.content.Context context) {
        return ((android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.NOTIFICATION_CHANNEL_DEVICE_ADMIN, new java.util.function.Supplier() { // from class: com.android.internal.notification.SystemNotificationChannels$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String string;
                string = android.content.Context.this.getString(com.android.internal.R.string.notification_channel_device_admin);
                return string;
            }
        });
    }

    public static void removeDeprecated(android.content.Context context) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        notificationManager.deleteNotificationChannel(VIRTUAL_KEYBOARD);
        notificationManager.deleteNotificationChannel(DEVICE_ADMIN_DEPRECATED);
        notificationManager.deleteNotificationChannel(SYSTEM_CHANGES_DEPRECATED);
    }

    public static void createAccountChannelForPackage(java.lang.String str, int i, android.content.Context context) {
        try {
            android.app.NotificationManager.getService().createNotificationChannelsForPackage(str, i, new android.content.pm.ParceledListSlice(java.util.Arrays.asList(newAccountChannel(context))));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static android.app.NotificationChannel newAccountChannel(android.content.Context context) {
        return new android.app.NotificationChannel(ACCOUNT, context.getString(com.android.internal.R.string.notification_channel_account), 2);
    }

    private SystemNotificationChannels() {
    }
}
