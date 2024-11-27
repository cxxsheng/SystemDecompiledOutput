package com.android.server.notification;

/* loaded from: classes2.dex */
public class ScheduleConditionProvider extends com.android.server.notification.SystemConditionProviderService {
    private static final java.lang.String EXTRA_TIME = "time";
    private static final java.lang.String NOT_SHOWN = "...";
    private static final int REQUEST_CODE_EVALUATE = 1;
    private static final java.lang.String SCP_SETTING = "snoozed_schedule_condition_provider";
    private static final java.lang.String SEPARATOR = ";";
    static final java.lang.String TAG = "ConditionProviders.SCP";
    private android.app.AlarmManager mAlarmManager;
    private boolean mConnected;
    private long mNextAlarmTime;
    private boolean mRegistered;
    static final boolean DEBUG = true;
    public static final android.content.ComponentName COMPONENT = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.notification.ScheduleConditionProvider.class.getName());
    private static final java.lang.String SIMPLE_NAME = com.android.server.notification.ScheduleConditionProvider.class.getSimpleName();
    private static final java.lang.String ACTION_EVALUATE = SIMPLE_NAME + ".EVALUATE";
    private final android.content.Context mContext = this;
    private final android.util.ArrayMap<android.net.Uri, android.service.notification.ScheduleCalendar> mSubscriptions = new android.util.ArrayMap<>();
    private android.util.ArraySet<android.net.Uri> mSnoozedForAlarm = new android.util.ArraySet<>();
    private android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.ScheduleConditionProvider.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.notification.ScheduleConditionProvider.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ScheduleConditionProvider.TAG, "onReceive " + intent.getAction());
            }
            if ("android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction())) {
                synchronized (com.android.server.notification.ScheduleConditionProvider.this.mSubscriptions) {
                    try {
                        java.util.Iterator it = com.android.server.notification.ScheduleConditionProvider.this.mSubscriptions.keySet().iterator();
                        while (it.hasNext()) {
                            android.service.notification.ScheduleCalendar scheduleCalendar = (android.service.notification.ScheduleCalendar) com.android.server.notification.ScheduleConditionProvider.this.mSubscriptions.get((android.net.Uri) it.next());
                            if (scheduleCalendar != null) {
                                scheduleCalendar.setTimeZone(java.util.Calendar.getInstance().getTimeZone());
                            }
                        }
                    } finally {
                    }
                }
            }
            com.android.server.notification.ScheduleConditionProvider.this.evaluateSubscriptions();
        }
    };

    public ScheduleConditionProvider() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "new " + SIMPLE_NAME + "()");
        }
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public android.content.ComponentName getComponent() {
        return COMPONENT;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public boolean isValidConditionId(android.net.Uri uri) {
        return android.service.notification.ZenModeConfig.isValidScheduleConditionId(uri);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        printWriter.print("    ");
        printWriter.print(SIMPLE_NAME);
        printWriter.println(":");
        printWriter.print("      mConnected=");
        printWriter.println(this.mConnected);
        printWriter.print("      mRegistered=");
        printWriter.println(this.mRegistered);
        printWriter.println("      mSubscriptions=");
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        synchronized (this.mSubscriptions) {
            try {
                for (android.net.Uri uri : this.mSubscriptions.keySet()) {
                    printWriter.print("        ");
                    printWriter.print(meetsSchedule(this.mSubscriptions.get(uri), currentTimeMillis) ? "* " : "  ");
                    printWriter.println(uri);
                    printWriter.print("            ");
                    printWriter.println(this.mSubscriptions.get(uri).toString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("      snoozed due to alarm: " + android.text.TextUtils.join(SEPARATOR, this.mSnoozedForAlarm));
        com.android.server.notification.SystemConditionProviderService.dumpUpcomingTime(printWriter, "mNextAlarmTime", this.mNextAlarmTime, currentTimeMillis);
    }

    @Override // android.service.notification.ConditionProviderService
    public void onConnected() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onConnected");
        }
        this.mConnected = true;
        readSnoozed();
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void onBootComplete() {
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            android.util.Slog.d(TAG, "onDestroy");
        }
        this.mConnected = false;
    }

    @Override // android.service.notification.ConditionProviderService
    public void onSubscribe(android.net.Uri uri) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onSubscribe " + uri);
        }
        if (!android.service.notification.ZenModeConfig.isValidScheduleConditionId(uri)) {
            notifyCondition(createCondition(uri, 3, "invalidId"));
            return;
        }
        synchronized (this.mSubscriptions) {
            this.mSubscriptions.put(uri, android.service.notification.ZenModeConfig.toScheduleCalendar(uri));
        }
        evaluateSubscriptions();
    }

    @Override // android.service.notification.ConditionProviderService
    public void onUnsubscribe(android.net.Uri uri) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onUnsubscribe " + uri);
        }
        synchronized (this.mSubscriptions) {
            this.mSubscriptions.remove(uri);
        }
        removeSnoozed(uri);
        evaluateSubscriptions();
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void attachBase(android.content.Context context) {
        attachBaseContext(context);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public android.service.notification.IConditionProvider asInterface() {
        return onBind(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evaluateSubscriptions() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        this.mNextAlarmTime = 0L;
        long nextAlarm = getNextAlarm();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSubscriptions) {
            try {
                setRegistered(!this.mSubscriptions.isEmpty());
                for (android.net.Uri uri : this.mSubscriptions.keySet()) {
                    android.service.notification.Condition evaluateSubscriptionLocked = evaluateSubscriptionLocked(uri, this.mSubscriptions.get(uri), currentTimeMillis, nextAlarm);
                    if (evaluateSubscriptionLocked != null) {
                        arrayList.add(evaluateSubscriptionLocked);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        notifyConditions((android.service.notification.Condition[]) arrayList.toArray(new android.service.notification.Condition[arrayList.size()]));
        updateAlarm(currentTimeMillis, this.mNextAlarmTime);
    }

    @com.android.internal.annotations.GuardedBy({"mSubscriptions"})
    @com.android.internal.annotations.VisibleForTesting
    android.service.notification.Condition evaluateSubscriptionLocked(android.net.Uri uri, android.service.notification.ScheduleCalendar scheduleCalendar, long j, long j2) {
        android.service.notification.Condition condition;
        if (DEBUG) {
            android.util.Slog.d(TAG, java.lang.String.format("evaluateSubscriptionLocked cal=%s, now=%s, nextUserAlarmTime=%s", scheduleCalendar, com.android.server.notification.SystemConditionProviderService.ts(j), com.android.server.notification.SystemConditionProviderService.ts(j2)));
        }
        if (scheduleCalendar == null) {
            android.service.notification.Condition createCondition = createCondition(uri, 3, "!invalidId");
            removeSnoozed(uri);
            return createCondition;
        }
        if (scheduleCalendar.isInSchedule(j)) {
            if (conditionSnoozed(uri)) {
                condition = createCondition(uri, 0, "snoozed");
            } else if (scheduleCalendar.shouldExitForAlarm(j)) {
                android.service.notification.Condition createCondition2 = createCondition(uri, 0, "alarmCanceled");
                addSnoozed(uri);
                condition = createCondition2;
            } else {
                condition = createCondition(uri, 1, "meetsSchedule");
            }
        } else {
            android.service.notification.Condition createCondition3 = createCondition(uri, 0, "!meetsSchedule");
            removeSnoozed(uri);
            condition = createCondition3;
        }
        scheduleCalendar.maybeSetNextAlarm(j, j2);
        long nextChangeTime = scheduleCalendar.getNextChangeTime(j);
        if (nextChangeTime > 0 && nextChangeTime > j && (this.mNextAlarmTime == 0 || nextChangeTime < this.mNextAlarmTime)) {
            this.mNextAlarmTime = nextChangeTime;
        }
        return condition;
    }

    private void updateAlarm(long j, long j2) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        android.app.PendingIntent pendingIntent = getPendingIntent(j2);
        alarmManager.cancel(pendingIntent);
        if (j2 > j) {
            if (DEBUG) {
                android.util.Slog.d(TAG, java.lang.String.format("Scheduling evaluate for %s, in %s, now=%s", com.android.server.notification.SystemConditionProviderService.ts(j2), com.android.server.notification.SystemConditionProviderService.formatDuration(j2 - j), com.android.server.notification.SystemConditionProviderService.ts(j)));
            }
            alarmManager.setExact(0, j2, pendingIntent);
        } else if (DEBUG) {
            android.util.Slog.d(TAG, "Not scheduling evaluate");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.PendingIntent getPendingIntent(long j) {
        return android.app.PendingIntent.getBroadcast(this.mContext, 1, new android.content.Intent(ACTION_EVALUATE).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).addFlags(268435456).putExtra(EXTRA_TIME, j), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    public long getNextAlarm() {
        android.app.AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(android.app.ActivityManager.getCurrentUser());
        if (nextAlarmClock != null) {
            return nextAlarmClock.getTriggerTime();
        }
        return 0L;
    }

    private boolean meetsSchedule(android.service.notification.ScheduleCalendar scheduleCalendar, long j) {
        return scheduleCalendar != null && scheduleCalendar.isInSchedule(j);
    }

    private void setRegistered(boolean z) {
        if (this.mRegistered == z) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "setRegistered " + z);
        }
        this.mRegistered = z;
        if (this.mRegistered) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction(ACTION_EVALUATE);
            intentFilter.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
            registerReceiver(this.mReceiver, intentFilter, 2);
            return;
        }
        unregisterReceiver(this.mReceiver);
    }

    private android.service.notification.Condition createCondition(android.net.Uri uri, int i, java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "notifyCondition " + uri + " " + android.service.notification.Condition.stateToString(i) + " reason=" + str);
        }
        return new android.service.notification.Condition(uri, NOT_SHOWN, NOT_SHOWN, NOT_SHOWN, 0, i, 2);
    }

    private boolean conditionSnoozed(android.net.Uri uri) {
        boolean contains;
        synchronized (this.mSnoozedForAlarm) {
            contains = this.mSnoozedForAlarm.contains(uri);
        }
        return contains;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addSnoozed(android.net.Uri uri) {
        synchronized (this.mSnoozedForAlarm) {
            this.mSnoozedForAlarm.add(uri);
            saveSnoozedLocked();
        }
    }

    private void removeSnoozed(android.net.Uri uri) {
        synchronized (this.mSnoozedForAlarm) {
            this.mSnoozedForAlarm.remove(uri);
            saveSnoozedLocked();
        }
    }

    private void saveSnoozedLocked() {
        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), SCP_SETTING, android.text.TextUtils.join(SEPARATOR, this.mSnoozedForAlarm), android.app.ActivityManager.getCurrentUser());
    }

    private void readSnoozed() {
        synchronized (this.mSnoozedForAlarm) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), SCP_SETTING, android.app.ActivityManager.getCurrentUser());
                    if (stringForUser != null) {
                        java.lang.String[] split = stringForUser.split(SEPARATOR);
                        for (int i = 0; i < split.length; i++) {
                            java.lang.String str = split[i];
                            if (str != null) {
                                str = str.trim();
                            }
                            if (!android.text.TextUtils.isEmpty(str)) {
                                this.mSnoozedForAlarm.add(android.net.Uri.parse(str));
                            }
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }
}
