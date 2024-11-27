package com.android.server.am;

/* loaded from: classes.dex */
public abstract class PreBootBroadcaster extends android.content.IIntentReceiver.Stub {
    private static final int MSG_HIDE = 2;
    private static final int MSG_SHOW = 1;
    private static final java.lang.String TAG = "PreBootBroadcaster";
    private final com.android.internal.util.ProgressReporter mProgress;
    private final boolean mQuiet;
    private final com.android.server.am.ActivityManagerService mService;
    private final java.util.List<android.content.pm.ResolveInfo> mTargets;
    private final int mUserId;
    private int mIndex = 0;
    private android.os.Handler mHandler = new android.os.Handler(com.android.server.UiThread.get().getLooper(), null, true) { // from class: com.android.server.am.PreBootBroadcaster.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.app.PendingIntent pendingIntent;
            android.content.Context context = com.android.server.am.PreBootBroadcaster.this.mService.mContext;
            android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
            int i = message.arg1;
            int i2 = message.arg2;
            switch (message.what) {
                case 1:
                    java.lang.CharSequence text = context.getText(android.R.string.alternative_unlock_setup_notification_title);
                    android.content.Intent intent = new android.content.Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.HelpTrampoline");
                    intent.putExtra("android.intent.extra.TEXT", "help_url_upgrading");
                    if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                        pendingIntent = android.app.PendingIntent.getActivity(context, 0, intent, 67108864);
                    } else {
                        pendingIntent = null;
                    }
                    notificationManager.notifyAsUser(com.android.server.am.PreBootBroadcaster.TAG, 13, new android.app.Notification.Builder(com.android.server.am.PreBootBroadcaster.this.mService.mContext, com.android.internal.notification.SystemNotificationChannels.UPDATES).setSmallIcon(android.R.drawable.stat_notify_sync_anim0).setWhen(0L).setOngoing(true).setTicker(text).setColor(context.getColor(android.R.color.system_notification_accent_color)).setContentTitle(text).setContentIntent(pendingIntent).setVisibility(1).setProgress(i, i2, false).build(), android.os.UserHandle.of(com.android.server.am.PreBootBroadcaster.this.mUserId));
                    break;
                case 2:
                    notificationManager.cancelAsUser(com.android.server.am.PreBootBroadcaster.TAG, 13, android.os.UserHandle.of(com.android.server.am.PreBootBroadcaster.this.mUserId));
                    break;
            }
        }
    };
    private final android.content.Intent mIntent = new android.content.Intent("android.intent.action.PRE_BOOT_COMPLETED");

    public abstract void onFinished();

    public PreBootBroadcaster(com.android.server.am.ActivityManagerService activityManagerService, int i, com.android.internal.util.ProgressReporter progressReporter, boolean z) {
        this.mService = activityManagerService;
        this.mUserId = i;
        this.mProgress = progressReporter;
        this.mQuiet = z;
        this.mIntent.addFlags(33554688);
        this.mTargets = this.mService.mContext.getPackageManager().queryBroadcastReceiversAsUser(this.mIntent, 1048576, android.os.UserHandle.of(i));
    }

    public void sendNext() {
        long j;
        if (this.mIndex < this.mTargets.size()) {
            if (!this.mService.isUserRunning(this.mUserId, 0)) {
                android.util.Slog.i(TAG, "User " + this.mUserId + " is no longer running; skipping remaining receivers");
                this.mHandler.obtainMessage(2).sendToTarget();
                onFinished();
                return;
            }
            if (!this.mQuiet) {
                this.mHandler.obtainMessage(1, this.mTargets.size(), this.mIndex).sendToTarget();
            }
            java.util.List<android.content.pm.ResolveInfo> list = this.mTargets;
            int i = this.mIndex;
            this.mIndex = i + 1;
            android.content.pm.ResolveInfo resolveInfo = list.get(i);
            android.content.ComponentName componentName = resolveInfo.activityInfo.getComponentName();
            if (this.mProgress != null) {
                this.mProgress.setProgress(this.mIndex, this.mTargets.size(), this.mService.mContext.getString(android.R.string.allow, resolveInfo.activityInfo.loadLabel(this.mService.mContext.getPackageManager())));
            }
            android.util.Slog.i(TAG, "Pre-boot of " + componentName.toShortString() + " for user " + this.mUserId);
            com.android.server.am.EventLogTags.writeAmPreBoot(this.mUserId, componentName.getPackageName());
            this.mIntent.setComponent(componentName);
            android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            if (activityManagerInternal == null) {
                j = 10000;
            } else {
                j = activityManagerInternal.getBootTimeTempAllowListDuration();
            }
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setTemporaryAppAllowlist(j, 0, 201, "");
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                } catch (java.lang.Throwable th) {
                    th = th;
                }
                try {
                    this.mService.broadcastIntentLocked(null, null, null, this.mIntent, null, this, 0, null, null, null, null, null, -1, makeBasic.toBundle(), true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), this.mUserId);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        this.mHandler.obtainMessage(2).sendToTarget();
        onFinished();
    }

    public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
        sendNext();
    }
}
