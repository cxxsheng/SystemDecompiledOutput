package com.android.server.notification;

/* loaded from: classes2.dex */
public class EventConditionProvider extends com.android.server.notification.SystemConditionProviderService {
    private static final long CHANGE_DELAY = 2000;
    private static final java.lang.String EXTRA_TIME = "time";
    private static final java.lang.String NOT_SHOWN = "...";
    private static final int REQUEST_CODE_EVALUATE = 1;
    private static final java.lang.String TAG = "ConditionProviders.ECP";
    private boolean mBootComplete;
    private boolean mConnected;
    private long mNextAlarmTime;
    private boolean mRegistered;
    private final android.os.HandlerThread mThread;
    private final android.os.Handler mWorker;
    private static final boolean DEBUG = android.util.Log.isLoggable("ConditionProviders", 3);
    public static final android.content.ComponentName COMPONENT = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.notification.EventConditionProvider.class.getName());
    private static final java.lang.String SIMPLE_NAME = com.android.server.notification.EventConditionProvider.class.getSimpleName();
    private static final java.lang.String ACTION_EVALUATE = SIMPLE_NAME + ".EVALUATE";
    private final android.content.Context mContext = this;
    private final android.util.ArraySet<android.net.Uri> mSubscriptions = new android.util.ArraySet<>();
    private final android.util.SparseArray<com.android.server.notification.CalendarTracker> mTrackers = new android.util.SparseArray<>();
    private final com.android.server.notification.CalendarTracker.Callback mTrackerCallback = new com.android.server.notification.CalendarTracker.Callback() { // from class: com.android.server.notification.EventConditionProvider.2
        @Override // com.android.server.notification.CalendarTracker.Callback
        public void onChanged() {
            if (com.android.server.notification.EventConditionProvider.DEBUG) {
                android.util.Slog.d(com.android.server.notification.EventConditionProvider.TAG, "mTrackerCallback.onChanged");
            }
            com.android.server.notification.EventConditionProvider.this.mWorker.removeCallbacks(com.android.server.notification.EventConditionProvider.this.mEvaluateSubscriptionsW);
            com.android.server.notification.EventConditionProvider.this.mWorker.postDelayed(com.android.server.notification.EventConditionProvider.this.mEvaluateSubscriptionsW, com.android.server.notification.EventConditionProvider.CHANGE_DELAY);
        }
    };
    private final android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.EventConditionProvider.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.notification.EventConditionProvider.DEBUG) {
                android.util.Slog.d(com.android.server.notification.EventConditionProvider.TAG, "onReceive " + intent.getAction());
            }
            com.android.server.notification.EventConditionProvider.this.evaluateSubscriptions();
        }
    };
    private final java.lang.Runnable mEvaluateSubscriptionsW = new java.lang.Runnable() { // from class: com.android.server.notification.EventConditionProvider.4
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.notification.EventConditionProvider.this.evaluateSubscriptionsW();
        }
    };

    public EventConditionProvider() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "new " + SIMPLE_NAME + "()");
        }
        this.mThread = new android.os.HandlerThread(TAG, 10);
        this.mThread.start();
        this.mWorker = new android.os.Handler(this.mThread.getLooper());
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public android.content.ComponentName getComponent() {
        return COMPONENT;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public boolean isValidConditionId(android.net.Uri uri) {
        return android.service.notification.ZenModeConfig.isValidEventConditionId(uri);
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
        printWriter.print("      mBootComplete=");
        printWriter.println(this.mBootComplete);
        com.android.server.notification.SystemConditionProviderService.dumpUpcomingTime(printWriter, "mNextAlarmTime", this.mNextAlarmTime, java.lang.System.currentTimeMillis());
        synchronized (this.mSubscriptions) {
            try {
                printWriter.println("      mSubscriptions=");
                java.util.Iterator<android.net.Uri> it = this.mSubscriptions.iterator();
                while (it.hasNext()) {
                    android.net.Uri next = it.next();
                    printWriter.print("        ");
                    printWriter.println(next);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("      mTrackers=");
        for (int i = 0; i < this.mTrackers.size(); i++) {
            printWriter.print("        user=");
            printWriter.println(this.mTrackers.keyAt(i));
            this.mTrackers.valueAt(i).dump("          ", printWriter);
        }
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public void onBootComplete() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onBootComplete");
        }
        if (this.mBootComplete) {
            return;
        }
        this.mBootComplete = true;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.notification.EventConditionProvider.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.notification.EventConditionProvider.this.reloadTrackers();
            }
        }, intentFilter);
        reloadTrackers();
    }

    @Override // android.service.notification.ConditionProviderService
    public void onConnected() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onConnected");
        }
        this.mConnected = true;
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
        if (!android.service.notification.ZenModeConfig.isValidEventConditionId(uri)) {
            notifyCondition(createCondition(uri, 0));
            return;
        }
        synchronized (this.mSubscriptions) {
            try {
                if (this.mSubscriptions.add(uri)) {
                    evaluateSubscriptions();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.service.notification.ConditionProviderService
    public void onUnsubscribe(android.net.Uri uri) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onUnsubscribe " + uri);
        }
        synchronized (this.mSubscriptions) {
            try {
                if (this.mSubscriptions.remove(uri)) {
                    evaluateSubscriptions();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
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
    public void reloadTrackers() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "reloadTrackers");
        }
        for (int i = 0; i < this.mTrackers.size(); i++) {
            this.mTrackers.valueAt(i).setCallback(null);
        }
        this.mTrackers.clear();
        for (android.os.UserHandle userHandle : android.os.UserManager.get(this.mContext).getUserProfiles()) {
            android.content.Context contextForUser = userHandle.isSystem() ? this.mContext : getContextForUser(this.mContext, userHandle);
            if (contextForUser == null) {
                android.util.Slog.w(TAG, "Unable to create context for user " + userHandle.getIdentifier());
            } else {
                this.mTrackers.put(userHandle.getIdentifier(), new com.android.server.notification.CalendarTracker(this.mContext, contextForUser));
            }
        }
        evaluateSubscriptions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evaluateSubscriptions() {
        if (!this.mWorker.hasCallbacks(this.mEvaluateSubscriptionsW)) {
            this.mWorker.post(this.mEvaluateSubscriptionsW);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evaluateSubscriptionsW() {
        java.util.Iterator<android.net.Uri> it;
        com.android.server.notification.CalendarTracker.CheckEventResult checkEventResult;
        java.util.Iterator<android.net.Uri> it2;
        if (DEBUG) {
            android.util.Slog.d(TAG, "evaluateSubscriptions");
        }
        if (!this.mBootComplete) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Skipping evaluate before boot complete");
                return;
            }
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.ArrayList<android.service.notification.Condition> arrayList = new java.util.ArrayList();
        synchronized (this.mSubscriptions) {
            int i = 0;
            for (int i2 = 0; i2 < this.mTrackers.size(); i2++) {
                try {
                    this.mTrackers.valueAt(i2).setCallback(this.mSubscriptions.isEmpty() ? null : this.mTrackerCallback);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            setRegistered(!this.mSubscriptions.isEmpty());
            java.util.Iterator<android.net.Uri> it3 = this.mSubscriptions.iterator();
            long j = 0;
            while (it3.hasNext()) {
                android.net.Uri next = it3.next();
                android.service.notification.ZenModeConfig.EventInfo tryParseEventConditionId = android.service.notification.ZenModeConfig.tryParseEventConditionId(next);
                if (tryParseEventConditionId == null) {
                    arrayList.add(createCondition(next, i));
                    it = it3;
                } else {
                    if (tryParseEventConditionId.calName == null) {
                        int i3 = i;
                        checkEventResult = null;
                        while (i3 < this.mTrackers.size()) {
                            com.android.server.notification.CalendarTracker.CheckEventResult checkEvent = this.mTrackers.valueAt(i3).checkEvent(tryParseEventConditionId, currentTimeMillis);
                            if (checkEventResult == null) {
                                it2 = it3;
                                checkEventResult = checkEvent;
                            } else {
                                checkEventResult.inEvent |= checkEvent.inEvent;
                                it2 = it3;
                                checkEventResult.recheckAt = java.lang.Math.min(checkEventResult.recheckAt, checkEvent.recheckAt);
                            }
                            i3++;
                            it3 = it2;
                        }
                        it = it3;
                    } else {
                        it = it3;
                        int resolveUserId = android.service.notification.ZenModeConfig.EventInfo.resolveUserId(tryParseEventConditionId.userId);
                        com.android.server.notification.CalendarTracker calendarTracker = this.mTrackers.get(resolveUserId);
                        if (calendarTracker == null) {
                            android.util.Slog.w(TAG, "No calendar tracker found for user " + resolveUserId);
                            arrayList.add(createCondition(next, 0));
                        } else {
                            checkEventResult = calendarTracker.checkEvent(tryParseEventConditionId, currentTimeMillis);
                        }
                    }
                    if (checkEventResult.recheckAt != 0 && (j == 0 || checkEventResult.recheckAt < j)) {
                        j = checkEventResult.recheckAt;
                    }
                    if (!checkEventResult.inEvent) {
                        i = 0;
                        arrayList.add(createCondition(next, 0));
                    } else {
                        i = 0;
                        arrayList.add(createCondition(next, 1));
                    }
                    it3 = it;
                }
                it3 = it;
                i = 0;
            }
            rescheduleAlarm(currentTimeMillis, j);
        }
        for (android.service.notification.Condition condition : arrayList) {
            if (condition != null) {
                notifyCondition(condition);
            }
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "evaluateSubscriptions took " + (java.lang.System.currentTimeMillis() - currentTimeMillis));
        }
    }

    private void rescheduleAlarm(long j, long j2) {
        this.mNextAlarmTime = j2;
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        android.app.PendingIntent pendingIntent = getPendingIntent(j2);
        alarmManager.cancel(pendingIntent);
        if (j2 == 0 || j2 < j) {
            if (DEBUG) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Not scheduling evaluate: ");
                sb.append(j2 == 0 ? "no time specified" : "specified time in the past");
                android.util.Slog.d(TAG, sb.toString());
                return;
            }
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, java.lang.String.format("Scheduling evaluate for %s, in %s, now=%s", com.android.server.notification.SystemConditionProviderService.ts(j2), com.android.server.notification.SystemConditionProviderService.formatDuration(j2 - j), com.android.server.notification.SystemConditionProviderService.ts(j)));
        }
        alarmManager.setExact(0, j2, pendingIntent);
    }

    android.app.PendingIntent getPendingIntent(long j) {
        return android.app.PendingIntent.getBroadcast(this.mContext, 1, new android.content.Intent(ACTION_EVALUATE).addFlags(268435456).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).putExtra(EXTRA_TIME, j), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    private android.service.notification.Condition createCondition(android.net.Uri uri, int i) {
        return new android.service.notification.Condition(uri, NOT_SHOWN, NOT_SHOWN, NOT_SHOWN, 0, i, 2);
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
            registerReceiver(this.mReceiver, intentFilter, 2);
            return;
        }
        unregisterReceiver(this.mReceiver);
    }

    private static android.content.Context getContextForUser(android.content.Context context, android.os.UserHandle userHandle) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
