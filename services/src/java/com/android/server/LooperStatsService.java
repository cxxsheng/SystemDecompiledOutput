package com.android.server;

/* loaded from: classes.dex */
public class LooperStatsService extends android.os.Binder {
    private static final java.lang.String DEBUG_SYS_LOOPER_STATS_ENABLED = "debug.sys.looper_stats_enabled";
    private static final boolean DEFAULT_ENABLED = true;
    private static final int DEFAULT_ENTRIES_SIZE_CAP = 1500;
    private static final int DEFAULT_SAMPLING_INTERVAL = 1000;
    private static final boolean DEFAULT_TRACK_SCREEN_INTERACTIVE = false;
    private static final java.lang.String LOOPER_STATS_SERVICE_NAME = "looper_stats";
    private static final java.lang.String SETTINGS_ENABLED_KEY = "enabled";
    private static final java.lang.String SETTINGS_IGNORE_BATTERY_STATUS_KEY = "ignore_battery_status";
    private static final java.lang.String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
    private static final java.lang.String SETTINGS_TRACK_SCREEN_INTERACTIVE_KEY = "track_screen_state";
    private static final java.lang.String TAG = "LooperStatsService";
    private final android.content.Context mContext;
    private boolean mEnabled;
    private boolean mIgnoreBatteryStatus;
    private final com.android.internal.os.LooperStats mStats;
    private boolean mTrackScreenInteractive;

    private LooperStatsService(android.content.Context context, com.android.internal.os.LooperStats looperStats) {
        this.mEnabled = false;
        this.mTrackScreenInteractive = false;
        this.mIgnoreBatteryStatus = false;
        this.mContext = context;
        this.mStats = looperStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromSettings() {
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(android.provider.Settings.Global.getString(this.mContext.getContentResolver(), LOOPER_STATS_SERVICE_NAME));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Bad looper_stats settings", e);
        }
        setSamplingInterval(keyValueListParser.getInt(SETTINGS_SAMPLING_INTERVAL_KEY, 1000));
        setTrackScreenInteractive(keyValueListParser.getBoolean(SETTINGS_TRACK_SCREEN_INTERACTIVE_KEY, false));
        setIgnoreBatteryStatus(keyValueListParser.getBoolean(SETTINGS_IGNORE_BATTERY_STATUS_KEY, false));
        setEnabled(android.os.SystemProperties.getBoolean(DEBUG_SYS_LOOPER_STATS_ENABLED, keyValueListParser.getBoolean("enabled", true)));
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.LooperStatsService.LooperShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.os.AppIdToPackageMap snapshot = com.android.internal.os.AppIdToPackageMap.getSnapshot();
            printWriter.print("Start time: ");
            printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mStats.getStartTimeMillis()));
            printWriter.print("On battery time (ms): ");
            printWriter.println(this.mStats.getBatteryTimeMillis());
            java.util.List entries = this.mStats.getEntries();
            entries.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.LooperStatsService$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Integer lambda$dump$0;
                    lambda$dump$0 = com.android.server.LooperStatsService.lambda$dump$0((com.android.internal.os.LooperStats.ExportedEntry) obj);
                    return lambda$dump$0;
                }
            }).thenComparing(new java.util.function.Function() { // from class: com.android.server.LooperStatsService$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String str;
                    str = ((com.android.internal.os.LooperStats.ExportedEntry) obj).threadName;
                    return str;
                }
            }).thenComparing(new java.util.function.Function() { // from class: com.android.server.LooperStatsService$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String str;
                    str = ((com.android.internal.os.LooperStats.ExportedEntry) obj).handlerClassName;
                    return str;
                }
            }).thenComparing(new java.util.function.Function() { // from class: com.android.server.LooperStatsService$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String str;
                    str = ((com.android.internal.os.LooperStats.ExportedEntry) obj).messageName;
                    return str;
                }
            }));
            printWriter.println(java.lang.String.join(",", java.util.Arrays.asList("work_source_uid", "thread_name", "handler_class", "message_name", "is_interactive", "message_count", "recorded_message_count", "total_latency_micros", "max_latency_micros", "total_cpu_micros", "max_cpu_micros", "recorded_delay_message_count", "total_delay_millis", "max_delay_millis", "exception_count")));
            java.util.Iterator it = entries.iterator();
            while (it.hasNext()) {
                com.android.internal.os.LooperStats.ExportedEntry exportedEntry = (com.android.internal.os.LooperStats.ExportedEntry) it.next();
                if (!exportedEntry.messageName.startsWith("__DEBUG_")) {
                    printWriter.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", snapshot.mapUid(exportedEntry.workSourceUid), exportedEntry.threadName, exportedEntry.handlerClassName, exportedEntry.messageName, java.lang.Boolean.valueOf(exportedEntry.isInteractive), java.lang.Long.valueOf(exportedEntry.messageCount), java.lang.Long.valueOf(exportedEntry.recordedMessageCount), java.lang.Long.valueOf(exportedEntry.totalLatencyMicros), java.lang.Long.valueOf(exportedEntry.maxLatencyMicros), java.lang.Long.valueOf(exportedEntry.cpuUsageMicros), java.lang.Long.valueOf(exportedEntry.maxCpuUsageMicros), java.lang.Long.valueOf(exportedEntry.recordedDelayMessageCount), java.lang.Long.valueOf(exportedEntry.delayMillis), java.lang.Long.valueOf(exportedEntry.maxDelayMillis), java.lang.Long.valueOf(exportedEntry.exceptionCount));
                    it = it;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$dump$0(com.android.internal.os.LooperStats.ExportedEntry exportedEntry) {
        return java.lang.Integer.valueOf(exportedEntry.workSourceUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            this.mStats.reset();
            this.mStats.setAddDebugEntries(z);
            android.os.Looper.setObserver(z ? this.mStats : null);
        }
    }

    private void setTrackScreenInteractive(boolean z) {
        if (this.mTrackScreenInteractive != z) {
            this.mTrackScreenInteractive = z;
            this.mStats.reset();
        }
    }

    private void setIgnoreBatteryStatus(boolean z) {
        if (this.mIgnoreBatteryStatus != z) {
            this.mStats.setIgnoreBatteryStatus(z);
            this.mIgnoreBatteryStatus = z;
            this.mStats.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSamplingInterval(int i) {
        if (i > 0) {
            this.mStats.setSamplingInterval(i);
            return;
        }
        android.util.Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + i);
    }

    public static class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.LooperStatsService mService;
        private final com.android.server.LooperStatsService.SettingsObserver mSettingsObserver;
        private final com.android.internal.os.LooperStats mStats;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mStats = new com.android.internal.os.LooperStats(1000, 1500);
            this.mService = new com.android.server.LooperStatsService(getContext(), this.mStats);
            this.mSettingsObserver = new com.android.server.LooperStatsService.SettingsObserver(this.mService);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishLocalService(com.android.internal.os.LooperStats.class, this.mStats);
            publishBinderService(com.android.server.LooperStatsService.LOOPER_STATS_SERVICE_NAME, this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (500 == i) {
                this.mService.initFromSettings();
                getContext().getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor(com.android.server.LooperStatsService.LOOPER_STATS_SERVICE_NAME), false, this.mSettingsObserver, 0);
                this.mStats.setDeviceState((com.android.internal.os.CachedDeviceState.Readonly) getLocalService(com.android.internal.os.CachedDeviceState.Readonly.class));
            }
        }
    }

    private static class SettingsObserver extends android.database.ContentObserver {
        private final com.android.server.LooperStatsService mService;

        SettingsObserver(com.android.server.LooperStatsService looperStatsService) {
            super(com.android.internal.os.BackgroundThread.getHandler());
            this.mService = looperStatsService;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            this.mService.initFromSettings();
        }
    }

    private class LooperShellCommand extends android.os.ShellCommand {
        private LooperShellCommand() {
        }

        public int onCommand(java.lang.String str) {
            if ("enable".equals(str)) {
                com.android.server.LooperStatsService.this.setEnabled(true);
                return 0;
            }
            if ("disable".equals(str)) {
                com.android.server.LooperStatsService.this.setEnabled(false);
                return 0;
            }
            if ("reset".equals(str)) {
                com.android.server.LooperStatsService.this.mStats.reset();
                return 0;
            }
            if (com.android.server.LooperStatsService.SETTINGS_SAMPLING_INTERVAL_KEY.equals(str)) {
                com.android.server.LooperStatsService.this.setSamplingInterval(java.lang.Integer.parseUnsignedInt(getNextArgRequired()));
                return 0;
            }
            return handleDefaultCommands(str);
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("looper_stats commands:");
            outPrintWriter.println("  enable: Enable collecting stats.");
            outPrintWriter.println("  disable: Disable collecting stats.");
            outPrintWriter.println("  sampling_interval: Change the sampling interval.");
            outPrintWriter.println("  reset: Reset stats.");
        }
    }
}
