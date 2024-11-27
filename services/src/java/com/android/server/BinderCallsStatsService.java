package com.android.server;

/* loaded from: classes.dex */
public class BinderCallsStatsService extends android.os.Binder {
    private static final java.lang.String PERSIST_SYS_BINDER_CALLS_DETAILED_TRACKING = "persist.sys.binder_calls_detailed_tracking";
    private static final java.lang.String SERVICE_NAME = "binder_calls_stats";
    private static final java.lang.String TAG = "BinderCallsStatsService";
    private final com.android.internal.os.BinderCallsStats mBinderCallsStats;
    private com.android.server.BinderCallsStatsService.SettingsObserver mSettingsObserver;
    private final com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider mWorkSourceProvider;

    static class AuthorizedWorkSourceProvider implements com.android.internal.os.BinderInternal.WorkSourceProvider {
        private android.util.ArraySet<java.lang.Integer> mAppIdTrustlist = new android.util.ArraySet<>();

        AuthorizedWorkSourceProvider() {
        }

        public int resolveWorkSourceUid(int i) {
            int callingUid = getCallingUid();
            if (this.mAppIdTrustlist.contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(callingUid)))) {
                return i != -1 ? i : callingUid;
            }
            return callingUid;
        }

        public void systemReady(android.content.Context context) {
            this.mAppIdTrustlist = createAppidTrustlist(context);
        }

        public void dump(java.io.PrintWriter printWriter, com.android.internal.os.AppIdToPackageMap appIdToPackageMap) {
            printWriter.println("AppIds of apps that can set the work source:");
            java.util.Iterator<java.lang.Integer> it = this.mAppIdTrustlist.iterator();
            while (it.hasNext()) {
                printWriter.println("\t- " + appIdToPackageMap.mapAppId(it.next().intValue()));
            }
        }

        protected int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        private android.util.ArraySet<java.lang.Integer> createAppidTrustlist(android.content.Context context) {
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            arraySet.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(android.os.Process.myUid())));
            android.content.pm.PackageManager packageManager = context.getPackageManager();
            java.util.List<android.content.pm.PackageInfo> packagesHoldingPermissions = packageManager.getPackagesHoldingPermissions(new java.lang.String[]{"android.permission.UPDATE_DEVICE_STATS"}, 786432);
            int size = packagesHoldingPermissions.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.PackageInfo packageInfo = packagesHoldingPermissions.get(i);
                try {
                    arraySet.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageManager.getPackageUid(packageInfo.packageName, 786432))));
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.e(com.android.server.BinderCallsStatsService.TAG, "Cannot find uid for package name " + packageInfo.packageName, e);
                }
            }
            return arraySet;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SettingsObserver extends android.database.ContentObserver {
        private final com.android.internal.os.BinderCallsStats mBinderCallsStats;
        private final android.content.Context mContext;
        private boolean mEnabled;
        private final android.util.KeyValueListParser mParser;
        private final android.net.Uri mUri;
        private final com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider mWorkSourceProvider;

        SettingsObserver(android.content.Context context, com.android.internal.os.BinderCallsStats binderCallsStats, com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider authorizedWorkSourceProvider) {
            super(com.android.internal.os.BackgroundThread.getHandler());
            this.mUri = android.provider.Settings.Global.getUriFor(com.android.server.BinderCallsStatsService.SERVICE_NAME);
            this.mParser = new android.util.KeyValueListParser(',');
            this.mContext = context;
            context.getContentResolver().registerContentObserver(this.mUri, false, this, 0);
            this.mBinderCallsStats = binderCallsStats;
            this.mWorkSourceProvider = authorizedWorkSourceProvider;
            onChange();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        public void onChange() {
            if (!android.os.SystemProperties.get(com.android.server.BinderCallsStatsService.PERSIST_SYS_BINDER_CALLS_DETAILED_TRACKING).isEmpty()) {
                return;
            }
            try {
                this.mParser.setString(android.provider.Settings.Global.getString(this.mContext.getContentResolver(), com.android.server.BinderCallsStatsService.SERVICE_NAME));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(com.android.server.BinderCallsStatsService.TAG, "Bad binder call stats settings", e);
            }
            this.mBinderCallsStats.setDetailedTracking(this.mParser.getBoolean("detailed_tracking", true));
            this.mBinderCallsStats.setSamplingInterval(this.mParser.getInt("sampling_interval", 1000));
            this.mBinderCallsStats.setMaxBinderCallStats(this.mParser.getInt("max_call_stats_count", android.net.util.NetworkConstants.ETHER_MTU));
            this.mBinderCallsStats.setTrackScreenInteractive(this.mParser.getBoolean("track_screen_state", false));
            this.mBinderCallsStats.setTrackDirectCallerUid(this.mParser.getBoolean("track_calling_uid", true));
            this.mBinderCallsStats.setIgnoreBatteryStatus(this.mParser.getBoolean("ignore_battery_status", false));
            this.mBinderCallsStats.setShardingModulo(this.mParser.getInt("sharding_modulo", 1));
            this.mBinderCallsStats.setCollectLatencyData(this.mParser.getBoolean("collect_latency_data", true));
            com.android.internal.os.BinderCallsStats.SettingsObserver.configureLatencyObserver(this.mParser, this.mBinderCallsStats.getLatencyObserver());
            boolean z = this.mParser.getBoolean(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED, true);
            if (this.mEnabled != z) {
                if (z) {
                    android.os.Binder.setObserver(this.mBinderCallsStats);
                    android.os.Binder.setProxyTransactListener(new android.os.Binder.PropagateWorkSourceTransactListener());
                    android.os.Binder.setWorkSourceProvider(this.mWorkSourceProvider);
                } else {
                    android.os.Binder.setObserver(null);
                    android.os.Binder.setProxyTransactListener(null);
                    android.os.Binder.setWorkSourceProvider(new com.android.internal.os.BinderInternal.WorkSourceProvider() { // from class: com.android.server.BinderCallsStatsService$SettingsObserver$$ExternalSyntheticLambda0
                        public final int resolveWorkSourceUid(int i) {
                            int lambda$onChange$0;
                            lambda$onChange$0 = com.android.server.BinderCallsStatsService.SettingsObserver.lambda$onChange$0(i);
                            return lambda$onChange$0;
                        }
                    });
                }
                this.mEnabled = z;
                this.mBinderCallsStats.reset();
                this.mBinderCallsStats.setAddDebugEntries(z);
                this.mBinderCallsStats.getLatencyObserver().reset();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$onChange$0(int i) {
            return android.os.Binder.getCallingUid();
        }
    }

    public static class Internal {
        private final com.android.internal.os.BinderCallsStats mBinderCallsStats;

        Internal(com.android.internal.os.BinderCallsStats binderCallsStats) {
            this.mBinderCallsStats = binderCallsStats;
        }

        public void reset() {
            this.mBinderCallsStats.reset();
        }

        public java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> getExportedCallStats() {
            return this.mBinderCallsStats.getExportedCallStats();
        }

        public android.util.ArrayMap<java.lang.String, java.lang.Integer> getExportedExceptionStats() {
            return this.mBinderCallsStats.getExportedExceptionStats();
        }
    }

    public static class LifeCycle extends com.android.server.SystemService {
        private com.android.internal.os.BinderCallsStats mBinderCallsStats;
        private com.android.server.BinderCallsStatsService mService;
        private com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider mWorkSourceProvider;

        public LifeCycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mBinderCallsStats = new com.android.internal.os.BinderCallsStats(new com.android.internal.os.BinderCallsStats.Injector());
            this.mWorkSourceProvider = new com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider();
            this.mService = new com.android.server.BinderCallsStatsService(this.mBinderCallsStats, this.mWorkSourceProvider);
            publishLocalService(com.android.server.BinderCallsStatsService.Internal.class, new com.android.server.BinderCallsStatsService.Internal(this.mBinderCallsStats));
            publishBinderService(com.android.server.BinderCallsStatsService.SERVICE_NAME, this.mService);
            if (android.os.SystemProperties.getBoolean(com.android.server.BinderCallsStatsService.PERSIST_SYS_BINDER_CALLS_DETAILED_TRACKING, false)) {
                android.util.Slog.i(com.android.server.BinderCallsStatsService.TAG, "Enabled CPU usage tracking for binder calls. Controlled by persist.sys.binder_calls_detailed_tracking or via dumpsys binder_calls_stats --enable-detailed-tracking");
                this.mBinderCallsStats.setDetailedTracking(true);
            }
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (500 == i) {
                this.mBinderCallsStats.setDeviceState((com.android.internal.os.CachedDeviceState.Readonly) getLocalService(com.android.internal.os.CachedDeviceState.Readonly.class));
                com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
                final android.os.BatteryStatsInternal batteryStatsInternal = (android.os.BatteryStatsInternal) getLocalService(android.os.BatteryStatsInternal.class);
                this.mBinderCallsStats.setCallStatsObserver(new com.android.internal.os.BinderInternal.CallStatsObserver() { // from class: com.android.server.BinderCallsStatsService.LifeCycle.1
                    public void noteCallStats(int i2, long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection) {
                        batteryStatsInternal.noteBinderCallStats(i2, j, collection);
                    }

                    public void noteBinderThreadNativeIds(int[] iArr) {
                        batteryStatsInternal.noteBinderThreadNativeIds(iArr);
                    }
                });
                this.mWorkSourceProvider.systemReady(getContext());
                this.mService.systemReady(getContext());
            }
        }
    }

    BinderCallsStatsService(com.android.internal.os.BinderCallsStats binderCallsStats, com.android.server.BinderCallsStatsService.AuthorizedWorkSourceProvider authorizedWorkSourceProvider) {
        this.mBinderCallsStats = binderCallsStats;
        this.mWorkSourceProvider = authorizedWorkSourceProvider;
    }

    public void systemReady(android.content.Context context) {
        this.mSettingsObserver = new com.android.server.BinderCallsStatsService.SettingsObserver(context, this.mBinderCallsStats, this.mWorkSourceProvider);
    }

    public void reset() {
        android.util.Slog.i(TAG, "Resetting stats");
        this.mBinderCallsStats.reset();
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(android.app.ActivityThread.currentApplication(), SERVICE_NAME, printWriter)) {
            return;
        }
        int i = -1;
        boolean z = false;
        int i2 = 0;
        if (strArr != null) {
            int i3 = -1;
            boolean z2 = false;
            while (i2 < strArr.length) {
                java.lang.String str = strArr[i2];
                if ("-a".equals(str)) {
                    z2 = true;
                } else {
                    if ("-h".equals(str)) {
                        printWriter.println("dumpsys binder_calls_stats options:");
                        printWriter.println("  -a: Verbose");
                        printWriter.println("  --work-source-uid <UID>: Dump binder calls from the UID");
                        return;
                    }
                    if ("--work-source-uid".equals(str)) {
                        i2++;
                        if (i2 >= strArr.length) {
                            throw new java.lang.IllegalArgumentException("Argument expected after \"" + str + "\"");
                        }
                        java.lang.String str2 = strArr[i2];
                        try {
                            i3 = java.lang.Integer.parseInt(str2);
                        } catch (java.lang.NumberFormatException e) {
                            printWriter.println("Invalid UID: " + str2);
                            return;
                        }
                    } else {
                        continue;
                    }
                }
                i2++;
            }
            if (strArr.length > 0 && i3 == -1 && new com.android.server.BinderCallsStatsService.BinderCallsStatsShellCommand(printWriter).exec(this, (java.io.FileDescriptor) null, java.io.FileDescriptor.out, java.io.FileDescriptor.err, strArr) == 0) {
                return;
            }
            z = z2;
            i = i3;
        }
        this.mBinderCallsStats.dump(printWriter, com.android.internal.os.AppIdToPackageMap.getSnapshot(), i, z);
    }

    public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
        com.android.server.BinderCallsStatsService.BinderCallsStatsShellCommand binderCallsStatsShellCommand = new com.android.server.BinderCallsStatsService.BinderCallsStatsShellCommand(null);
        int exec = binderCallsStatsShellCommand.exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        if (exec != 0) {
            binderCallsStatsShellCommand.onHelp();
        }
        return exec;
    }

    private class BinderCallsStatsShellCommand extends android.os.ShellCommand {
        private final java.io.PrintWriter mPrintWriter;

        BinderCallsStatsShellCommand(java.io.PrintWriter printWriter) {
            this.mPrintWriter = printWriter;
        }

        public java.io.PrintWriter getOutPrintWriter() {
            if (this.mPrintWriter != null) {
                return this.mPrintWriter;
            }
            return super.getOutPrintWriter();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            char c;
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            if (str == null) {
                return -1;
            }
            switch (str.hashCode()) {
                case -1615291473:
                    if (str.equals("--reset")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1289263917:
                    if (str.equals("--no-sampling")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1237677752:
                    if (str.equals("--disable")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -534486470:
                    if (str.equals("--work-source-uid")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -106516359:
                    if (str.equals("--dump-worksource-provider")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1101165347:
                    if (str.equals("--enable")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1448286703:
                    if (str.equals("--disable-detailed-tracking")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 2041864970:
                    if (str.equals("--enable-detailed-tracking")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.BinderCallsStatsService.this.reset();
                    outPrintWriter.println("binder_calls_stats reset.");
                    return 0;
                case 1:
                    android.os.Binder.setObserver(com.android.server.BinderCallsStatsService.this.mBinderCallsStats);
                    return 0;
                case 2:
                    android.os.Binder.setObserver(null);
                    return 0;
                case 3:
                    com.android.server.BinderCallsStatsService.this.mBinderCallsStats.setSamplingInterval(1);
                    return 0;
                case 4:
                    android.os.SystemProperties.set(com.android.server.BinderCallsStatsService.PERSIST_SYS_BINDER_CALLS_DETAILED_TRACKING, "1");
                    com.android.server.BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(true);
                    outPrintWriter.println("Detailed tracking enabled");
                    return 0;
                case 5:
                    android.os.SystemProperties.set(com.android.server.BinderCallsStatsService.PERSIST_SYS_BINDER_CALLS_DETAILED_TRACKING, "");
                    com.android.server.BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(false);
                    outPrintWriter.println("Detailed tracking disabled");
                    return 0;
                case 6:
                    com.android.server.BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(true);
                    com.android.server.BinderCallsStatsService.this.mWorkSourceProvider.dump(outPrintWriter, com.android.internal.os.AppIdToPackageMap.getSnapshot());
                    return 0;
                case 7:
                    java.lang.String nextArgRequired = getNextArgRequired();
                    try {
                        com.android.server.BinderCallsStatsService.this.mBinderCallsStats.recordAllCallsForWorkSourceUid(java.lang.Integer.parseInt(nextArgRequired));
                        return 0;
                    } catch (java.lang.NumberFormatException e) {
                        outPrintWriter.println("Invalid UID: " + nextArgRequired);
                        return -1;
                    }
                default:
                    return handleDefaultCommands(str);
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("binder_calls_stats commands:");
            outPrintWriter.println("  --reset: Reset stats");
            outPrintWriter.println("  --enable: Enable tracking binder calls");
            outPrintWriter.println("  --disable: Disables tracking binder calls");
            outPrintWriter.println("  --no-sampling: Tracks all calls");
            outPrintWriter.println("  --enable-detailed-tracking: Enables detailed tracking");
            outPrintWriter.println("  --disable-detailed-tracking: Disables detailed tracking");
            outPrintWriter.println("  --work-source-uid <UID>: Track all binder calls from the UID");
        }
    }
}
