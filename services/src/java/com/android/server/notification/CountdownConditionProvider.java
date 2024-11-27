package com.android.server.notification;

/* loaded from: classes2.dex */
public class CountdownConditionProvider extends com.android.server.notification.SystemConditionProviderService {
    private static final java.lang.String EXTRA_CONDITION_ID = "condition_id";
    private static final int REQUEST_CODE = 100;
    private static final java.lang.String TAG = "ConditionProviders.CCP";
    private boolean mConnected;
    private boolean mIsAlarm;
    private long mTime;
    private static final boolean DEBUG = android.util.Log.isLoggable("ConditionProviders", 3);
    public static final android.content.ComponentName COMPONENT = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.notification.CountdownConditionProvider.class.getName());
    private static final java.lang.String ACTION = com.android.server.notification.CountdownConditionProvider.class.getName();
    private final android.content.Context mContext = this;
    private final com.android.server.notification.CountdownConditionProvider.Receiver mReceiver = new com.android.server.notification.CountdownConditionProvider.Receiver();

    public CountdownConditionProvider() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "new CountdownConditionProvider()");
        }
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public android.content.ComponentName getComponent() {
        return COMPONENT;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public boolean isValidConditionId(android.net.Uri uri) {
        return android.service.notification.ZenModeConfig.isValidCountdownConditionId(uri);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void attachBase(android.content.Context context) {
        attachBaseContext(context);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void onBootComplete() {
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public android.service.notification.IConditionProvider asInterface() {
        return onBind(null);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        printWriter.println("    CountdownConditionProvider:");
        printWriter.print("      mConnected=");
        printWriter.println(this.mConnected);
        printWriter.print("      mTime=");
        printWriter.println(this.mTime);
    }

    @Override // android.service.notification.ConditionProviderService
    public void onConnected() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onConnected");
        }
        this.mContext.registerReceiver(this.mReceiver, new android.content.IntentFilter(ACTION), 2);
        this.mConnected = true;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            android.util.Slog.d(TAG, "onDestroy");
        }
        if (this.mConnected) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
        this.mConnected = false;
    }

    @Override // android.service.notification.ConditionProviderService
    public void onSubscribe(android.net.Uri uri) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onSubscribe " + uri);
        }
        this.mTime = android.service.notification.ZenModeConfig.tryParseCountdownConditionId(uri);
        this.mIsAlarm = android.service.notification.ZenModeConfig.isValidCountdownToAlarmConditionId(uri);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        android.app.PendingIntent pendingIntent = getPendingIntent(uri);
        alarmManager.cancel(pendingIntent);
        if (this.mTime > 0) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            java.lang.CharSequence relativeTimeSpanString = android.text.format.DateUtils.getRelativeTimeSpanString(this.mTime, currentTimeMillis, 60000L);
            if (this.mTime <= currentTimeMillis) {
                notifyCondition(newCondition(this.mTime, this.mIsAlarm, 0));
            } else {
                alarmManager.setExact(0, this.mTime, pendingIntent);
            }
            if (DEBUG) {
                android.util.Slog.d(TAG, java.lang.String.format("%s %s for %s, %s in the future (%s), now=%s", this.mTime <= currentTimeMillis ? "Not scheduling" : "Scheduling", ACTION, com.android.server.notification.SystemConditionProviderService.ts(this.mTime), java.lang.Long.valueOf(this.mTime - currentTimeMillis), relativeTimeSpanString, com.android.server.notification.SystemConditionProviderService.ts(currentTimeMillis)));
            }
        }
    }

    android.app.PendingIntent getPendingIntent(android.net.Uri uri) {
        return android.app.PendingIntent.getBroadcast(this.mContext, 100, new android.content.Intent(ACTION).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).putExtra(EXTRA_CONDITION_ID, uri).setFlags(1073741824), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    @Override // android.service.notification.ConditionProviderService
    public void onUnsubscribe(android.net.Uri uri) {
    }

    private final class Receiver extends android.content.BroadcastReceiver {
        private Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.notification.CountdownConditionProvider.ACTION.equals(intent.getAction())) {
                android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(com.android.server.notification.CountdownConditionProvider.EXTRA_CONDITION_ID, android.net.Uri.class);
                boolean isValidCountdownToAlarmConditionId = android.service.notification.ZenModeConfig.isValidCountdownToAlarmConditionId(uri);
                long tryParseCountdownConditionId = android.service.notification.ZenModeConfig.tryParseCountdownConditionId(uri);
                if (com.android.server.notification.CountdownConditionProvider.DEBUG) {
                    android.util.Slog.d(com.android.server.notification.CountdownConditionProvider.TAG, "Countdown condition fired: " + uri);
                }
                if (tryParseCountdownConditionId > 0) {
                    com.android.server.notification.CountdownConditionProvider.this.notifyCondition(com.android.server.notification.CountdownConditionProvider.newCondition(tryParseCountdownConditionId, isValidCountdownToAlarmConditionId, 0));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final android.service.notification.Condition newCondition(long j, boolean z, int i) {
        return new android.service.notification.Condition(android.service.notification.ZenModeConfig.toCountdownConditionId(j, z), "", "", "", 0, i, 1);
    }

    public static java.lang.String tryParseDescription(android.net.Uri uri) {
        long tryParseCountdownConditionId = android.service.notification.ZenModeConfig.tryParseCountdownConditionId(uri);
        if (tryParseCountdownConditionId == 0) {
            return null;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        return java.lang.String.format("Scheduled for %s, %s in the future (%s), now=%s", com.android.server.notification.SystemConditionProviderService.ts(tryParseCountdownConditionId), java.lang.Long.valueOf(tryParseCountdownConditionId - currentTimeMillis), android.text.format.DateUtils.getRelativeTimeSpanString(tryParseCountdownConditionId, currentTimeMillis, 60000L), com.android.server.notification.SystemConditionProviderService.ts(currentTimeMillis));
    }
}
