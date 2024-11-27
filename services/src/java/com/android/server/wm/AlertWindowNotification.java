package com.android.server.wm;

/* loaded from: classes3.dex */
class AlertWindowNotification {
    private static final java.lang.String CHANNEL_PREFIX = "com.android.server.wm.AlertWindowNotification - ";
    private static final int NOTIFICATION_ID = 0;
    private static android.app.NotificationChannelGroup sChannelGroup;
    private static int sNextRequestCode = 0;
    private final android.app.NotificationManager mNotificationManager;
    private java.lang.String mNotificationTag;
    private final java.lang.String mPackageName;
    private boolean mPosted;
    private final int mRequestCode;
    private final com.android.server.wm.WindowManagerService mService;
    private final int mUserId;

    AlertWindowNotification(com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i) {
        this.mService = windowManagerService;
        this.mPackageName = str;
        this.mUserId = i;
        this.mNotificationManager = (android.app.NotificationManager) this.mService.mContext.getSystemService("notification");
        this.mNotificationTag = CHANNEL_PREFIX + this.mPackageName;
        int i2 = sNextRequestCode;
        sNextRequestCode = i2 + 1;
        this.mRequestCode = i2;
    }

    void post() {
        this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.AlertWindowNotification$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.AlertWindowNotification.this.onPostNotification();
            }
        });
    }

    void cancel(final boolean z) {
        this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.AlertWindowNotification$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.AlertWindowNotification.this.lambda$cancel$0(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onCancelNotification, reason: merged with bridge method [inline-methods] */
    public void lambda$cancel$0(boolean z) {
        if (!this.mPosted) {
            return;
        }
        this.mPosted = false;
        this.mNotificationManager.cancel(this.mNotificationTag, 0);
        if (z) {
            this.mNotificationManager.deleteNotificationChannel(this.mNotificationTag);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostNotification() {
        if (this.mPosted) {
            return;
        }
        this.mPosted = true;
        android.content.Context context = this.mService.mContext;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.pm.ApplicationInfo applicationInfoAsUser = getApplicationInfoAsUser(packageManager, this.mPackageName, this.mUserId);
        java.lang.String charSequence = applicationInfoAsUser != null ? packageManager.getApplicationLabel(applicationInfoAsUser).toString() : this.mPackageName;
        createNotificationChannel(context, charSequence);
        java.lang.String string = context.getString(android.R.string.aerr_process_repeated, charSequence);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putStringArray("android.foregroundApps", new java.lang.String[]{this.mPackageName});
        android.app.Notification.Builder contentIntent = new android.app.Notification.Builder(context, this.mNotificationTag).setOngoing(true).setContentTitle(context.getString(android.R.string.aerr_report, charSequence)).setContentText(string).setSmallIcon(android.R.drawable.activity_title_bar).setColor(context.getColor(android.R.color.system_notification_accent_color)).setStyle(new android.app.Notification.BigTextStyle().bigText(string)).setLocalOnly(true).addExtras(bundle).setContentIntent(getContentIntent(context, this.mPackageName));
        if (applicationInfoAsUser != null) {
            android.graphics.drawable.Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfoAsUser);
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
            android.graphics.Bitmap buildScaledBitmap = com.android.internal.util.ImageUtils.buildScaledBitmap(applicationIcon, dimensionPixelSize, dimensionPixelSize);
            if (buildScaledBitmap != null) {
                contentIntent.setLargeIcon(buildScaledBitmap);
            }
        }
        this.mNotificationManager.notify(this.mNotificationTag, 0, contentIntent.build());
    }

    private android.app.PendingIntent getContentIntent(android.content.Context context, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.settings.MANAGE_APP_OVERLAY_PERMISSION", android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, str, null));
        intent.setFlags(268468224);
        intent.putExtra("android.intent.extra.user_handle", android.os.UserHandle.of(this.mUserId));
        return android.app.PendingIntent.getActivity(context, this.mRequestCode, intent, android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
    }

    private void createNotificationChannel(android.content.Context context, java.lang.String str) {
        if (sChannelGroup == null) {
            sChannelGroup = new android.app.NotificationChannelGroup(CHANNEL_PREFIX, this.mService.mContext.getString(android.R.string.aerr_mute));
            this.mNotificationManager.createNotificationChannelGroup(sChannelGroup);
        }
        java.lang.String string = context.getString(android.R.string.aerr_process, str);
        if (this.mNotificationManager.getNotificationChannel(this.mNotificationTag) != null) {
            return;
        }
        android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel(this.mNotificationTag, string, 1);
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        notificationChannel.setBlockable(true);
        notificationChannel.setGroup(sChannelGroup.getId());
        notificationChannel.setBypassDnd(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
    }

    private android.content.pm.ApplicationInfo getApplicationInfoAsUser(android.content.pm.PackageManager packageManager, java.lang.String str, int i) {
        try {
            return packageManager.getApplicationInfoAsUser(str, 0, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
