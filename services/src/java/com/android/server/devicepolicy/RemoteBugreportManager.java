package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class RemoteBugreportManager {
    static final java.lang.String BUGREPORT_MIMETYPE = "application/vnd.android.bugreport";
    private static final java.lang.String CTL_STOP = "ctl.stop";
    private static final int NOTIFICATION_ID = 678432343;
    private static final java.lang.String REMOTE_BUGREPORT_SERVICE = "bugreportd";
    private static final long REMOTE_BUGREPORT_TIMEOUT_MILLIS = 600000;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.devicepolicy.DevicePolicyManagerService.Injector mInjector;
    private final com.android.server.devicepolicy.DevicePolicyManagerService mService;
    private final java.security.SecureRandom mRng = new java.security.SecureRandom();
    private final java.util.concurrent.atomic.AtomicLong mRemoteBugreportNonce = new java.util.concurrent.atomic.AtomicLong();
    private final java.util.concurrent.atomic.AtomicBoolean mRemoteBugreportServiceIsActive = new java.util.concurrent.atomic.AtomicBoolean();
    private final java.util.concurrent.atomic.AtomicBoolean mRemoteBugreportSharingAccepted = new java.util.concurrent.atomic.AtomicBoolean();
    private final java.lang.Runnable mRemoteBugreportTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.devicepolicy.RemoteBugreportManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.devicepolicy.RemoteBugreportManager.this.lambda$new$0();
        }
    };
    private final android.content.BroadcastReceiver mRemoteBugreportFinishedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.devicepolicy.RemoteBugreportManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.REMOTE_BUGREPORT_DISPATCH".equals(intent.getAction()) && com.android.server.devicepolicy.RemoteBugreportManager.this.mRemoteBugreportServiceIsActive.get()) {
                com.android.server.devicepolicy.RemoteBugreportManager.this.onBugreportFinished(intent);
            }
        }
    };
    private final android.content.BroadcastReceiver mRemoteBugreportConsentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.devicepolicy.RemoteBugreportManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            com.android.server.devicepolicy.RemoteBugreportManager.this.cancelNotification();
            if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED".equals(action)) {
                com.android.server.devicepolicy.RemoteBugreportManager.this.onBugreportSharingAccepted();
            } else if ("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED".equals(action)) {
                com.android.server.devicepolicy.RemoteBugreportManager.this.onBugreportSharingDeclined();
            }
            com.android.server.devicepolicy.RemoteBugreportManager.this.mContext.unregisterReceiver(com.android.server.devicepolicy.RemoteBugreportManager.this.mRemoteBugreportConsentReceiver);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface RemoteBugreportNotificationType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.mRemoteBugreportServiceIsActive.get()) {
            onBugreportFailed();
        }
    }

    public RemoteBugreportManager(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, com.android.server.devicepolicy.DevicePolicyManagerService.Injector injector) {
        this.mService = devicePolicyManagerService;
        this.mInjector = injector;
        this.mContext = devicePolicyManagerService.mContext;
        this.mHandler = devicePolicyManagerService.mHandler;
    }

    private android.app.Notification buildNotification(int i) {
        android.content.Intent intent = new android.content.Intent("android.settings.SHOW_REMOTE_BUGREPORT_DIALOG");
        intent.addFlags(268468224);
        intent.putExtra("android.app.extra.bugreport_notification_type", i);
        android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(this.mContext.getPackageManager(), 1048576);
        if (resolveActivityInfo != null) {
            intent.setComponent(resolveActivityInfo.getComponentName());
        } else {
            com.android.server.utils.Slogf.wtf("DevicePolicyManager", "Failed to resolve intent for remote bugreport dialog");
        }
        android.app.Notification.Builder extend = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(android.R.drawable.stat_notify_sync_anim0).setOngoing(true).setLocalOnly(true).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, i, intent, 67108864, null, android.os.UserHandle.CURRENT)).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).extend(new android.app.Notification.TvExtender());
        if (i == 2) {
            extend.setContentTitle(this.mContext.getString(android.R.string.sending)).setProgress(0, 0, true);
        } else if (i == 1) {
            extend.setContentTitle(this.mContext.getString(android.R.string.status_bar_sync_failing)).setProgress(0, 0, true);
        } else if (i == 3) {
            android.app.PendingIntent broadcast = android.app.PendingIntent.getBroadcast(this.mContext, NOTIFICATION_ID, new android.content.Intent("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED"), android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
            extend.addAction(new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, this.mContext.getString(android.R.string.date_picker_mode), android.app.PendingIntent.getBroadcast(this.mContext, NOTIFICATION_ID, new android.content.Intent("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED"), android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF)).build()).addAction(new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, this.mContext.getString(android.R.string.select_minutes), broadcast).build()).setContentTitle(this.mContext.getString(android.R.string.select_year)).setContentText(this.mContext.getString(android.R.string.select_multiple_keyboards_layout_notification_title)).setStyle(new android.app.Notification.BigTextStyle().bigText(this.mContext.getString(android.R.string.select_multiple_keyboards_layout_notification_title)));
        }
        return extend.build();
    }

    public boolean requestBugreport() {
        long nextLong;
        if (this.mRemoteBugreportServiceIsActive.get() || this.mService.getDeviceOwnerRemoteBugreportUriAndHash() != null) {
            com.android.server.utils.Slogf.d("DevicePolicyManager", "Remote bugreport wasn't started because there's already one running");
            return false;
        }
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        do {
            try {
                nextLong = this.mRng.nextLong();
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.e("DevicePolicyManager", "Failed to make remote calls to start bugreportremote service", e);
                return false;
            } finally {
                this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
            }
        } while (nextLong == 0);
        this.mInjector.getIActivityManager().requestRemoteBugReport(nextLong);
        this.mRemoteBugreportNonce.set(nextLong);
        this.mRemoteBugreportServiceIsActive.set(true);
        this.mRemoteBugreportSharingAccepted.set(false);
        registerRemoteBugreportReceivers();
        notify(1);
        this.mHandler.postDelayed(this.mRemoteBugreportTimeoutRunnable, 600000L);
        return true;
    }

    private void registerRemoteBugreportReceivers() {
        try {
            this.mContext.registerReceiver(this.mRemoteBugreportFinishedReceiver, new android.content.IntentFilter("android.intent.action.REMOTE_BUGREPORT_DISPATCH", BUGREPORT_MIMETYPE), 2);
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            com.android.server.utils.Slogf.w("DevicePolicyManager", e, "Failed to set type %s", BUGREPORT_MIMETYPE);
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED");
        intentFilter.addAction("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED");
        this.mContext.registerReceiver(this.mRemoteBugreportConsentReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBugreportFinished(android.content.Intent intent) {
        java.lang.String str;
        long longExtra = intent.getLongExtra("android.intent.extra.REMOTE_BUGREPORT_NONCE", 0L);
        if (longExtra == 0 || this.mRemoteBugreportNonce.get() != longExtra) {
            com.android.server.utils.Slogf.w("DevicePolicyManager", "Invalid nonce provided, ignoring " + longExtra);
            return;
        }
        this.mHandler.removeCallbacks(this.mRemoteBugreportTimeoutRunnable);
        this.mRemoteBugreportServiceIsActive.set(false);
        android.net.Uri data = intent.getData();
        if (data == null) {
            str = null;
        } else {
            str = data.toString();
        }
        java.lang.String stringExtra = intent.getStringExtra("android.intent.extra.REMOTE_BUGREPORT_HASH");
        if (this.mRemoteBugreportSharingAccepted.get()) {
            shareBugreportWithDeviceOwnerIfExists(str, stringExtra);
            cancelNotification();
        } else {
            this.mService.setDeviceOwnerRemoteBugreportUriAndHash(str, stringExtra);
            notify(3);
        }
        this.mContext.unregisterReceiver(this.mRemoteBugreportFinishedReceiver);
    }

    private void onBugreportFailed() {
        this.mRemoteBugreportServiceIsActive.set(false);
        this.mInjector.systemPropertiesSet(CTL_STOP, REMOTE_BUGREPORT_SERVICE);
        this.mRemoteBugreportSharingAccepted.set(false);
        this.mService.setDeviceOwnerRemoteBugreportUriAndHash(null, null);
        cancelNotification();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("android.app.extra.BUGREPORT_FAILURE_REASON", 0);
        this.mService.sendDeviceOwnerCommand("android.app.action.BUGREPORT_FAILED", bundle);
        this.mContext.unregisterReceiver(this.mRemoteBugreportConsentReceiver);
        this.mContext.unregisterReceiver(this.mRemoteBugreportFinishedReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBugreportSharingAccepted() {
        this.mRemoteBugreportSharingAccepted.set(true);
        android.util.Pair<java.lang.String, java.lang.String> deviceOwnerRemoteBugreportUriAndHash = this.mService.getDeviceOwnerRemoteBugreportUriAndHash();
        if (deviceOwnerRemoteBugreportUriAndHash != null) {
            shareBugreportWithDeviceOwnerIfExists((java.lang.String) deviceOwnerRemoteBugreportUriAndHash.first, (java.lang.String) deviceOwnerRemoteBugreportUriAndHash.second);
        } else if (this.mRemoteBugreportServiceIsActive.get()) {
            notify(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBugreportSharingDeclined() {
        if (this.mRemoteBugreportServiceIsActive.get()) {
            this.mInjector.systemPropertiesSet(CTL_STOP, REMOTE_BUGREPORT_SERVICE);
            this.mRemoteBugreportServiceIsActive.set(false);
            this.mHandler.removeCallbacks(this.mRemoteBugreportTimeoutRunnable);
            this.mContext.unregisterReceiver(this.mRemoteBugreportFinishedReceiver);
        }
        this.mRemoteBugreportSharingAccepted.set(false);
        this.mService.setDeviceOwnerRemoteBugreportUriAndHash(null, null);
        this.mService.sendDeviceOwnerCommand("android.app.action.BUGREPORT_SHARING_DECLINED", null);
    }

    private void shareBugreportWithDeviceOwnerIfExists(java.lang.String str, java.lang.String str2) {
        try {
            try {
            } catch (java.io.FileNotFoundException e) {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt("android.app.extra.BUGREPORT_FAILURE_REASON", 1);
                this.mService.sendDeviceOwnerCommand("android.app.action.BUGREPORT_FAILED", bundle);
            }
            if (str == null) {
                throw new java.io.FileNotFoundException();
            }
            this.mService.sendBugreportToDeviceOwner(android.net.Uri.parse(str), str2);
        } finally {
            this.mRemoteBugreportSharingAccepted.set(false);
            this.mService.setDeviceOwnerRemoteBugreportUriAndHash(null, null);
        }
    }

    public void checkForPendingBugreportAfterBoot() {
        if (this.mService.getDeviceOwnerRemoteBugreportUriAndHash() == null) {
            return;
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED");
        intentFilter.addAction("com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED");
        this.mContext.registerReceiver(this.mRemoteBugreportConsentReceiver, intentFilter);
        notify(3);
    }

    private void notify(int i) {
        this.mInjector.getNotificationManager().notifyAsUser("DevicePolicyManager", NOTIFICATION_ID, buildNotification(i), android.os.UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelNotification() {
        this.mInjector.getNotificationManager().cancelAsUser("DevicePolicyManager", NOTIFICATION_ID, android.os.UserHandle.ALL);
    }
}
