package com.android.server;

/* loaded from: classes.dex */
public final class SystemServer implements android.util.Dumpable {
    private static final java.lang.String ACCESSIBILITY_MANAGER_SERVICE_CLASS = "com.android.server.accessibility.AccessibilityManagerService$Lifecycle";
    private static final java.lang.String ACCOUNT_SERVICE_CLASS = "com.android.server.accounts.AccountManagerService$Lifecycle";
    private static final java.lang.String ADB_SERVICE_CLASS = "com.android.server.adb.AdbService$Lifecycle";
    private static final java.lang.String AD_SERVICES_MANAGER_SERVICE_CLASS = "com.android.server.adservices.AdServicesManagerService$Lifecycle";
    private static final java.lang.String ALARM_MANAGER_SERVICE_CLASS = "com.android.server.alarm.AlarmManagerService";
    private static final java.lang.String AMBIENT_CONTEXT_MANAGER_SERVICE_CLASS = "com.android.server.ambientcontext.AmbientContextManagerService";
    private static final java.lang.String APPSEARCH_MODULE_LIFECYCLE_CLASS = "com.android.server.appsearch.AppSearchModule$Lifecycle";
    private static final java.lang.String APPWIDGET_SERVICE_CLASS = "com.android.server.appwidget.AppWidgetService";
    private static final java.lang.String APP_COMPAT_OVERRIDES_SERVICE_CLASS = "com.android.server.compat.overrides.AppCompatOverridesService$Lifecycle";
    private static final java.lang.String APP_HIBERNATION_SERVICE_CLASS = "com.android.server.apphibernation.AppHibernationService";
    private static final java.lang.String APP_PREDICTION_MANAGER_SERVICE_CLASS = "com.android.server.appprediction.AppPredictionManagerService";
    private static final java.lang.String ARC_NETWORK_SERVICE_CLASS = "com.android.server.arc.net.ArcNetworkService";
    private static final java.lang.String ARC_PERSISTENT_DATA_BLOCK_SERVICE_CLASS = "com.android.server.arc.persistent_data_block.ArcPersistentDataBlockService";
    private static final java.lang.String ARC_SYSTEM_HEALTH_SERVICE = "com.android.server.arc.health.ArcSystemHealthService";
    private static final java.lang.String AUTO_FILL_MANAGER_SERVICE_CLASS = "com.android.server.autofill.AutofillManagerService";
    private static final java.lang.String BACKUP_MANAGER_SERVICE_CLASS = "com.android.server.backup.BackupManagerService$Lifecycle";
    private static final java.lang.String BLOB_STORE_MANAGER_SERVICE_CLASS = "com.android.server.blob.BlobStoreManagerService";
    private static final java.lang.String BLOCK_MAP_FILE = "/cache/recovery/block.map";
    private static final java.lang.String BLUETOOTH_APEX_SERVICE_JAR_PATH = "/apex/com.android.btservices/javalib/service-bluetooth.jar";
    private static final java.lang.String BLUETOOTH_SERVICE_CLASS = "com.android.server.bluetooth.BluetoothService";
    private static final java.lang.String CAR_SERVICE_HELPER_SERVICE_CLASS = "com.android.internal.car.CarServiceHelperService";
    private static final java.lang.String COMPANION_DEVICE_MANAGER_SERVICE_CLASS = "com.android.server.companion.CompanionDeviceManagerService";
    private static final java.lang.String CONNECTIVITY_SERVICE_APEX_PATH = "/apex/com.android.tethering/javalib/service-connectivity.jar";
    private static final java.lang.String CONNECTIVITY_SERVICE_INITIALIZER_CLASS = "com.android.server.ConnectivityServiceInitializer";
    private static final java.lang.String CONTENT_CAPTURE_MANAGER_SERVICE_CLASS = "com.android.server.contentcapture.ContentCaptureManagerService";
    private static final java.lang.String CONTENT_SERVICE_CLASS = "com.android.server.content.ContentService$Lifecycle";
    private static final java.lang.String CONTENT_SUGGESTIONS_SERVICE_CLASS = "com.android.server.contentsuggestions.ContentSuggestionsManagerService";
    private static final java.lang.String CREDENTIAL_MANAGER_SERVICE_CLASS = "com.android.server.credentials.CredentialManagerService";
    private static final int DEFAULT_SYSTEM_THEME = 16974863;
    private static final java.lang.String DEVICE_IDLE_CONTROLLER_CLASS = "com.android.server.DeviceIdleController";
    private static final java.lang.String DEVICE_LOCK_APEX_PATH = "/apex/com.android.devicelock/javalib/service-devicelock.jar";
    private static final java.lang.String DEVICE_LOCK_SERVICE_CLASS = "com.android.server.devicelock.DeviceLockService";
    private static final java.lang.String ENHANCED_CONFIRMATION_SERVICE_CLASS = "com.android.ecm.EnhancedConfirmationService";
    private static final java.lang.String GAME_MANAGER_SERVICE_CLASS = "com.android.server.app.GameManagerService$Lifecycle";
    private static final java.lang.String GNSS_TIME_UPDATE_SERVICE_CLASS = "com.android.server.timedetector.GnssTimeUpdateService$Lifecycle";
    private static final java.lang.String HEALTHCONNECT_MANAGER_SERVICE_CLASS = "com.android.server.healthconnect.HealthConnectManagerService";
    private static final java.lang.String HEALTH_SERVICE_CLASS = "com.android.clockwork.healthservices.HealthService";
    private static final java.io.File HEAP_DUMP_PATH = new java.io.File("/data/system/heapdump/");
    private static final java.lang.String IOT_SERVICE_CLASS = "com.android.things.server.IoTSystemService";
    private static final java.lang.String IP_CONNECTIVITY_METRICS_CLASS = "com.android.server.connectivity.IpConnectivityMetrics";
    private static final java.lang.String ISOLATED_COMPILATION_SERVICE_CLASS = "com.android.server.compos.IsolatedCompilationService";
    private static final java.lang.String JOB_SCHEDULER_SERVICE_CLASS = "com.android.server.job.JobSchedulerService";
    private static final java.lang.String LOCATION_TIME_ZONE_MANAGER_SERVICE_CLASS = "com.android.server.timezonedetector.location.LocationTimeZoneManagerService$Lifecycle";
    private static final java.lang.String LOCK_SETTINGS_SERVICE_CLASS = "com.android.server.locksettings.LockSettingsService$Lifecycle";
    private static final java.lang.String LOWPAN_SERVICE_CLASS = "com.android.server.lowpan.LowpanService";
    private static final int MAX_HEAP_DUMPS = 2;
    private static final java.lang.String MEDIA_COMMUNICATION_SERVICE_CLASS = "com.android.server.media.MediaCommunicationService";
    private static final java.lang.String MEDIA_RESOURCE_MONITOR_SERVICE_CLASS = "com.android.server.media.MediaResourceMonitorService";
    private static final java.lang.String MEDIA_SESSION_SERVICE_CLASS = "com.android.server.media.MediaSessionService";
    private static final java.lang.String MIDI_SERVICE_CLASS = "com.android.server.midi.MidiService$Lifecycle";
    private static final java.lang.String MUSIC_RECOGNITION_MANAGER_SERVICE_CLASS = "com.android.server.musicrecognition.MusicRecognitionManagerService";
    private static final java.lang.String NETWORK_STATS_SERVICE_INITIALIZER_CLASS = "com.android.server.NetworkStatsServiceInitializer";
    private static final java.lang.String ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS = "com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle";
    private static final java.lang.String PERSISTENT_DATA_BLOCK_PROP = "ro.frp.pst";
    private static final java.lang.String PRINT_MANAGER_SERVICE_CLASS = "com.android.server.print.PrintManagerService";
    private static final java.lang.String PROFILING_SERVICE_JAR_PATH = "/apex/com.android.profiling/javalib/service-profiling.jar";
    private static final java.lang.String PROFILING_SERVICE_LIFECYCLE_CLASS = "android.os.profiling.ProfilingService$Lifecycle";
    private static final java.lang.String REBOOT_READINESS_LIFECYCLE_CLASS = "com.android.server.scheduling.RebootReadinessManagerService$Lifecycle";
    private static final java.lang.String RESOURCE_ECONOMY_SERVICE_CLASS = "com.android.server.tare.InternalResourceService";
    private static final java.lang.String ROLE_SERVICE_CLASS = "com.android.role.RoleService";
    private static final java.lang.String ROLLBACK_MANAGER_SERVICE_CLASS = "com.android.server.rollback.RollbackManagerService";
    private static final java.lang.String SAFETY_CENTER_SERVICE_CLASS = "com.android.safetycenter.SafetyCenterService";
    private static final java.lang.String SCHEDULING_APEX_PATH = "/apex/com.android.scheduling/javalib/service-scheduling.jar";
    private static final java.lang.String SDK_SANDBOX_MANAGER_SERVICE_CLASS = "com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle";
    private static final java.lang.String SEARCH_MANAGER_SERVICE_CLASS = "com.android.server.search.SearchManagerService$Lifecycle";
    private static final java.lang.String SEARCH_UI_MANAGER_SERVICE_CLASS = "com.android.server.searchui.SearchUiManagerService";
    private static final java.lang.String SLICE_MANAGER_SERVICE_CLASS = "com.android.server.slice.SliceManagerService$Lifecycle";
    private static final long SLOW_DELIVERY_THRESHOLD_MS = 200;
    private static final long SLOW_DISPATCH_THRESHOLD_MS = 100;
    private static final java.lang.String SMARTSPACE_MANAGER_SERVICE_CLASS = "com.android.server.smartspace.SmartspaceManagerService";
    private static final java.lang.String SPEECH_RECOGNITION_MANAGER_SERVICE_CLASS = "com.android.server.speech.SpeechRecognitionManagerService";
    private static final java.lang.String START_BLOB_STORE_SERVICE = "startBlobStoreManagerService";
    private static final java.lang.String START_HIDL_SERVICES = "StartHidlServices";
    private static final java.lang.String START_SENSOR_MANAGER_SERVICE = "StartISensorManagerService";
    private static final java.lang.String STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS = "com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle";
    private static final java.lang.String STATS_COMPANION_APEX_PATH = "/apex/com.android.os.statsd/javalib/service-statsd.jar";
    private static final java.lang.String STATS_COMPANION_LIFECYCLE_CLASS = "com.android.server.stats.StatsCompanion$Lifecycle";
    private static final java.lang.String STATS_PULL_ATOM_SERVICE_CLASS = "com.android.server.stats.pull.StatsPullAtomService";
    private static final java.lang.String STORAGE_MANAGER_SERVICE_CLASS = "com.android.server.StorageManagerService$Lifecycle";
    private static final java.lang.String STORAGE_STATS_SERVICE_CLASS = "com.android.server.usage.StorageStatsService$Lifecycle";
    private static final java.lang.String SYSPROP_FDTRACK_ABORT_THRESHOLD = "persist.sys.debug.fdtrack_abort_threshold";
    private static final java.lang.String SYSPROP_FDTRACK_ENABLE_THRESHOLD = "persist.sys.debug.fdtrack_enable_threshold";
    private static final java.lang.String SYSPROP_FDTRACK_INTERVAL = "persist.sys.debug.fdtrack_interval";
    private static final java.lang.String SYSPROP_START_COUNT = "sys.system_server.start_count";
    private static final java.lang.String SYSPROP_START_ELAPSED = "sys.system_server.start_elapsed";
    private static final java.lang.String SYSPROP_START_UPTIME = "sys.system_server.start_uptime";
    private static final java.lang.String SYSTEM_CAPTIONS_MANAGER_SERVICE_CLASS = "com.android.server.systemcaptions.SystemCaptionsManagerService";
    private static final java.lang.String SYSTEM_STATE_DISPLAY_SERVICE_CLASS = "com.android.clockwork.systemstatedisplay.SystemStateDisplayService";
    private static final java.lang.String TAG = "SystemServer";
    private static final java.lang.String TETHERING_CONNECTOR_CLASS = "android.net.ITetheringConnector";
    private static final java.lang.String TEXT_TO_SPEECH_MANAGER_SERVICE_CLASS = "com.android.server.texttospeech.TextToSpeechManagerService";
    private static final java.lang.String THERMAL_OBSERVER_CLASS = "com.android.clockwork.ThermalObserver";
    private static final java.lang.String TIME_DETECTOR_SERVICE_CLASS = "com.android.server.timedetector.TimeDetectorService$Lifecycle";
    private static final java.lang.String TIME_ZONE_DETECTOR_SERVICE_CLASS = "com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle";
    private static final java.lang.String TRANSLATION_MANAGER_SERVICE_CLASS = "com.android.server.translation.TranslationManagerService";
    private static final java.lang.String UNCRYPT_PACKAGE_FILE = "/cache/recovery/uncrypt_file";
    private static final java.lang.String UPDATABLE_DEVICE_CONFIG_SERVICE_CLASS = "com.android.server.deviceconfig.DeviceConfigInit$Lifecycle";
    private static final java.lang.String USB_SERVICE_CLASS = "com.android.server.usb.UsbService$Lifecycle";
    private static final java.lang.String UWB_APEX_SERVICE_JAR_PATH = "/apex/com.android.uwb/javalib/service-uwb.jar";
    private static final java.lang.String UWB_SERVICE_CLASS = "com.android.server.uwb.UwbService";
    private static final java.lang.String VIRTUAL_DEVICE_MANAGER_SERVICE_CLASS = "com.android.server.companion.virtual.VirtualDeviceManagerService";
    private static final java.lang.String VOICE_RECOGNITION_MANAGER_SERVICE_CLASS = "com.android.server.voiceinteraction.VoiceInteractionManagerService";
    private static final java.lang.String WALLPAPER_EFFECTS_GENERATION_MANAGER_SERVICE_CLASS = "com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService";
    private static final java.lang.String WALLPAPER_SERVICE_CLASS = "com.android.server.wallpaper.WallpaperManagerService$Lifecycle";
    private static final java.lang.String WEAR_CONNECTIVITY_SERVICE_CLASS = "com.android.clockwork.connectivity.WearConnectivityService";
    private static final java.lang.String WEAR_DEBUG_SERVICE_CLASS = "com.android.clockwork.debug.WearDebugService";
    private static final java.lang.String WEAR_DISPLAYOFFLOAD_SERVICE_CLASS = "com.android.clockwork.displayoffload.DisplayOffloadService";
    private static final java.lang.String WEAR_DISPLAY_SERVICE_CLASS = "com.android.clockwork.display.WearDisplayService";
    private static final java.lang.String WEAR_MODE_SERVICE_CLASS = "com.android.clockwork.modes.ModeManagerService";
    private static final java.lang.String WEAR_POWER_SERVICE_CLASS = "com.android.clockwork.power.WearPowerService";
    private static final java.lang.String WEAR_SETTINGS_SERVICE_CLASS = "com.android.clockwork.settings.WearSettingsService";
    private static final java.lang.String WEAR_TIME_SERVICE_CLASS = "com.android.clockwork.time.WearTimeService";
    private static final java.lang.String WIFI_APEX_SERVICE_JAR_PATH = "/apex/com.android.wifi/javalib/service-wifi.jar";
    private static final java.lang.String WIFI_AWARE_SERVICE_CLASS = "com.android.server.wifi.aware.WifiAwareService";
    private static final java.lang.String WIFI_P2P_SERVICE_CLASS = "com.android.server.wifi.p2p.WifiP2pService";
    private static final java.lang.String WIFI_RTT_SERVICE_CLASS = "com.android.server.wifi.rtt.RttService";
    private static final java.lang.String WIFI_SCANNING_SERVICE_CLASS = "com.android.server.wifi.scanner.WifiScanningService";
    private static final java.lang.String WIFI_SERVICE_CLASS = "com.android.server.wifi.WifiService";
    private static final java.lang.String WRIST_ORIENTATION_SERVICE_CLASS = "com.android.clockwork.wristorientation.WristOrientationService";
    private static final int sMaxBinderThreads = 31;
    private static java.util.LinkedList<android.util.Pair<java.lang.String, android.app.ApplicationErrorReport.CrashInfo>> sPendingWtfs;
    private com.android.server.am.ActivityManagerService mActivityManagerService;
    private android.content.ContentResolver mContentResolver;
    private com.android.server.pm.DataLoaderManagerService mDataLoaderManagerService;
    private com.android.server.display.DisplayManagerService mDisplayManagerService;
    private com.android.server.EntropyMixer mEntropyMixer;
    private boolean mFirstBoot;
    private android.content.pm.PackageManager mPackageManager;
    private com.android.server.pm.PackageManagerService mPackageManagerService;
    private com.android.server.power.PowerManagerService mPowerManagerService;
    private java.util.Timer mProfilerSnapshotTimer;
    private final boolean mRuntimeRestart;
    private android.content.Context mSystemContext;
    private com.android.server.SystemServiceManager mSystemServiceManager;
    private com.android.server.webkit.WebViewUpdateService mWebViewUpdateService;
    private com.android.server.wm.WindowManagerGlobalLock mWindowManagerGlobalLock;
    private java.util.concurrent.Future<?> mZygotePreload;
    private long mIncrementalServiceHandle = 0;
    private final com.android.server.SystemServer.SystemServerDumper mDumper = new com.android.server.SystemServer.SystemServerDumper();
    private final int mFactoryTestMode = android.os.FactoryTest.getMode();
    private final int mStartCount = android.os.SystemProperties.getInt(SYSPROP_START_COUNT, 0) + 1;
    private final long mRuntimeStartElapsedTime = android.os.SystemClock.elapsedRealtime();
    private final long mRuntimeStartUptime = android.os.SystemClock.uptimeMillis();

    private static native void fdtrackAbort();

    private static native void initZygoteChildHeapProfiling();

    private static native void setIncrementalServiceSystemReady(long j);

    private static native void startHidlServices();

    private static native void startISensorManagerService();

    private static native void startIStatsService();

    private static native long startIncrementalService();

    private static native void startMemtrackProxyService();

    private static int getMaxFd() {
        java.io.FileDescriptor fileDescriptor = null;
        try {
            try {
                fileDescriptor = android.system.Os.open("/dev/null", android.system.OsConstants.O_RDONLY | android.system.OsConstants.O_CLOEXEC, 0);
                int int$ = fileDescriptor.getInt$();
                try {
                    android.system.Os.close(fileDescriptor);
                    return int$;
                } catch (android.system.ErrnoException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                if (fileDescriptor != null) {
                    try {
                        android.system.Os.close(fileDescriptor);
                    } catch (android.system.ErrnoException e2) {
                        throw new java.lang.RuntimeException(e2);
                    }
                }
                throw th;
            }
        } catch (android.system.ErrnoException e3) {
            android.util.Slog.e("System", "Failed to get maximum fd: " + e3);
            if (fileDescriptor == null) {
                return Integer.MAX_VALUE;
            }
            try {
                android.system.Os.close(fileDescriptor);
                return Integer.MAX_VALUE;
            } catch (android.system.ErrnoException e4) {
                throw new java.lang.RuntimeException(e4);
            }
        }
    }

    private static void dumpHprof() {
        java.util.TreeSet treeSet = new java.util.TreeSet();
        for (java.io.File file : HEAP_DUMP_PATH.listFiles()) {
            if (file.isFile() && file.getName().startsWith("fdtrack-")) {
                treeSet.add(file);
            }
        }
        if (treeSet.size() >= 2) {
            for (int i = 0; i < 1; i++) {
                treeSet.pollLast();
            }
            java.util.Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                java.io.File file2 = (java.io.File) it.next();
                if (!file2.delete()) {
                    android.util.Slog.w("System", "Failed to clean up hprof " + file2);
                }
            }
        }
        try {
            android.os.Debug.dumpHprofData("/data/system/heapdump/fdtrack-" + new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date()) + ".hprof");
        } catch (java.io.IOException e) {
            android.util.Slog.e("System", "Failed to dump fdtrack hprof", e);
        }
    }

    private static void spawnFdLeakCheckThread() {
        final int i = android.os.SystemProperties.getInt(SYSPROP_FDTRACK_ENABLE_THRESHOLD, 1600);
        final int i2 = android.os.SystemProperties.getInt(SYSPROP_FDTRACK_ABORT_THRESHOLD, 3000);
        final int i3 = android.os.SystemProperties.getInt(SYSPROP_FDTRACK_INTERVAL, 120);
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.SystemServer.lambda$spawnFdLeakCheckThread$0(i, i2, i3);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$spawnFdLeakCheckThread$0(int i, int i2, int i3) {
        boolean z = false;
        long j = 0;
        while (true) {
            int maxFd = getMaxFd();
            if (maxFd > i) {
                java.lang.System.gc();
                java.lang.System.runFinalization();
                maxFd = getMaxFd();
            }
            if (maxFd > i && !z) {
                android.util.Slog.i("System", "fdtrack enable threshold reached, enabling");
                com.android.internal.util.FrameworkStatsLog.write(364, 2, maxFd);
                java.lang.System.loadLibrary("fdtrack");
                z = true;
            } else if (maxFd > i2) {
                android.util.Slog.i("System", "fdtrack abort threshold reached, dumping and aborting");
                com.android.internal.util.FrameworkStatsLog.write(364, 3, maxFd);
                dumpHprof();
                fdtrackAbort();
            } else {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                if (elapsedRealtime > j) {
                    long j2 = elapsedRealtime + 3600000;
                    com.android.internal.util.FrameworkStatsLog.write(364, z ? 2 : 1, maxFd);
                    j = j2;
                }
            }
            try {
                java.lang.Thread.sleep(i3 * 1000);
            } catch (java.lang.InterruptedException e) {
            }
        }
    }

    public static void main(java.lang.String[] strArr) {
        new com.android.server.SystemServer().run();
    }

    public SystemServer() {
        android.os.Process.setStartTimes(this.mRuntimeStartElapsedTime, this.mRuntimeStartUptime, this.mRuntimeStartElapsedTime, this.mRuntimeStartUptime);
        this.mRuntimeRestart = this.mStartCount > 1;
    }

    @Override // android.util.Dumpable
    public java.lang.String getDumpableName() {
        return com.android.server.SystemServer.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.printf("Runtime restart: %b\n", java.lang.Boolean.valueOf(this.mRuntimeRestart));
        printWriter.printf("Start count: %d\n", java.lang.Integer.valueOf(this.mStartCount));
        printWriter.print("Runtime start-up time: ");
        android.util.TimeUtils.formatDuration(this.mRuntimeStartUptime, printWriter);
        printWriter.println();
        printWriter.print("Runtime start-elapsed time: ");
        android.util.TimeUtils.formatDuration(this.mRuntimeStartElapsedTime, printWriter);
        printWriter.println();
    }

    private final class SystemServerDumper extends android.os.Binder {

        @com.android.internal.annotations.GuardedBy({"mDumpables"})
        private final android.util.ArrayMap<java.lang.String, android.util.Dumpable> mDumpables;

        private SystemServerDumper() {
            this.mDumpables = new android.util.ArrayMap<>(4);
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.util.IndentingPrintWriter indentingPrintWriter;
            int i = 0;
            boolean z = strArr != null && strArr.length > 0;
            synchronized (this.mDumpables) {
                if (z) {
                    try {
                        if ("--list".equals(strArr[0])) {
                            int size = this.mDumpables.size();
                            while (i < size) {
                                printWriter.println(this.mDumpables.keyAt(i));
                                i++;
                            }
                            return;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (z && "--name".equals(strArr[0])) {
                    if (strArr.length < 2) {
                        printWriter.println("Must pass at least one argument to --name");
                        return;
                    }
                    java.lang.String str = strArr[1];
                    android.util.Dumpable dumpable = this.mDumpables.get(str);
                    if (dumpable == null) {
                        printWriter.printf("No dumpable named %s\n", str);
                        return;
                    }
                    indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
                    try {
                        dumpable.dump(indentingPrintWriter, (java.lang.String[]) java.util.Arrays.copyOfRange(strArr, 2, strArr.length));
                        indentingPrintWriter.close();
                        return;
                    } finally {
                    }
                }
                int size2 = this.mDumpables.size();
                indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
                while (i < size2) {
                    try {
                        android.util.Dumpable valueAt = this.mDumpables.valueAt(i);
                        indentingPrintWriter.printf("%s:\n", new java.lang.Object[]{valueAt.getDumpableName()});
                        indentingPrintWriter.increaseIndent();
                        valueAt.dump(indentingPrintWriter, strArr);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        i++;
                    } finally {
                    }
                }
                indentingPrintWriter.close();
                return;
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDumpable(@android.annotation.NonNull android.util.Dumpable dumpable) {
            synchronized (this.mDumpables) {
                this.mDumpables.put(dumpable.getDumpableName(), dumpable);
            }
        }
    }

    private void run() {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        try {
            timingsTraceAndSlog.traceBegin("InitBeforeStartServices");
            android.os.SystemProperties.set(SYSPROP_START_COUNT, java.lang.String.valueOf(this.mStartCount));
            android.os.SystemProperties.set(SYSPROP_START_ELAPSED, java.lang.String.valueOf(this.mRuntimeStartElapsedTime));
            android.os.SystemProperties.set(SYSPROP_START_UPTIME, java.lang.String.valueOf(this.mRuntimeStartUptime));
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.SYSTEM_SERVER_START, java.lang.Integer.valueOf(this.mStartCount), java.lang.Long.valueOf(this.mRuntimeStartUptime), java.lang.Long.valueOf(this.mRuntimeStartElapsedTime));
            com.android.server.SystemTimeZone.initializeTimeZoneSettingsIfRequired();
            if (!android.os.SystemProperties.get("persist.sys.language").isEmpty()) {
                android.os.SystemProperties.set("persist.sys.locale", java.util.Locale.getDefault().toLanguageTag());
                android.os.SystemProperties.set("persist.sys.language", "");
                android.os.SystemProperties.set("persist.sys.country", "");
                android.os.SystemProperties.set("persist.sys.localevar", "");
            }
            android.os.Binder.setWarnOnBlocking(true);
            android.content.pm.PackageItemInfo.forceSafeLabels();
            android.database.sqlite.SQLiteGlobal.sDefaultSyncMode = "FULL";
            android.database.sqlite.SQLiteCompatibilityWalFlags.init((java.lang.String) null);
            android.util.Slog.i(TAG, "Entered the Android system server!");
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_SYSTEM_RUN, elapsedRealtime);
            if (!this.mRuntimeRestart) {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 19, elapsedRealtime);
            }
            android.os.SystemProperties.set("persist.sys.dalvik.vm.lib.2", dalvik.system.VMRuntime.getRuntime().vmLibrary());
            dalvik.system.VMRuntime.getRuntime().clearGrowthLimit();
            android.os.Build.ensureFingerprintProperty();
            android.os.Environment.setUserRequired(true);
            android.os.BaseBundle.setShouldDefuse(true);
            android.os.Parcel.setStackTraceParceling(true);
            com.android.internal.os.BinderInternal.disableBackgroundScheduling(true);
            com.android.internal.os.BinderInternal.setMaxThreads(31);
            android.os.Process.setThreadPriority(-2);
            android.os.Process.setCanSelfBackground(false);
            android.os.Looper.prepareMainLooper();
            android.os.Looper.getMainLooper().setSlowLogThresholdMs(SLOW_DISPATCH_THRESHOLD_MS, SLOW_DELIVERY_THRESHOLD_MS);
            android.app.SystemServiceRegistry.sEnableServiceNotFoundWtf = true;
            java.lang.System.loadLibrary("android_servers");
            initZygoteChildHeapProfiling();
            if (android.os.Build.IS_ENG) {
                spawnFdLeakCheckThread();
            }
            performPendingShutdown();
            createSystemContext();
            android.app.ActivityThread.initializeMainlineModules();
            android.os.ServiceManager.addService("system_server_dumper", this.mDumper);
            this.mDumper.addDumpable(this);
            this.mSystemServiceManager = new com.android.server.SystemServiceManager(this.mSystemContext);
            this.mSystemServiceManager.setStartInfo(this.mRuntimeRestart, this.mRuntimeStartElapsedTime, this.mRuntimeStartUptime);
            this.mDumper.addDumpable(this.mSystemServiceManager);
            com.android.server.LocalServices.addService(com.android.server.SystemServiceManager.class, this.mSystemServiceManager);
            this.mDumper.addDumpable(com.android.server.SystemServerInitThreadPool.start());
            if (!com.android.text.flags.Flags.useOptimizedBoottimeFontLoading()) {
                android.util.Slog.i(TAG, "Loading pre-installed system font map.");
                android.graphics.Typeface.loadPreinstalledSystemFontMap();
            }
            if (android.os.Build.IS_DEBUGGABLE) {
                java.lang.String str = android.os.SystemProperties.get("persist.sys.dalvik.jvmtiagent");
                if (!str.isEmpty()) {
                    int indexOf = str.indexOf(61);
                    try {
                        android.os.Debug.attachJvmtiAgent(str.substring(0, indexOf), str.substring(indexOf + 1, str.length()), null);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e("System", "*************************************************");
                        android.util.Slog.e("System", "********** Failed to load jvmti plugin: " + str);
                    }
                }
            }
            timingsTraceAndSlog.traceEnd();
            com.android.internal.os.RuntimeInit.setDefaultApplicationWtfHandler(new com.android.internal.os.RuntimeInit.ApplicationWtfHandler() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda2
                public final boolean handleApplicationWtf(android.os.IBinder iBinder, java.lang.String str2, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) {
                    boolean handleEarlySystemWtf;
                    handleEarlySystemWtf = com.android.server.SystemServer.handleEarlySystemWtf(iBinder, str2, z, parcelableCrashInfo, i);
                    return handleEarlySystemWtf;
                }
            });
            try {
                timingsTraceAndSlog.traceBegin("StartServices");
                startBootstrapServices(timingsTraceAndSlog);
                startCoreServices(timingsTraceAndSlog);
                startOtherServices(timingsTraceAndSlog);
                startApexServices(timingsTraceAndSlog);
                updateWatchdogTimeout(timingsTraceAndSlog);
                com.android.server.criticalevents.CriticalEventLog.getInstance().logSystemServerStarted();
                timingsTraceAndSlog.traceEnd();
                android.os.StrictMode.initVmDefaults(null);
                if (!this.mRuntimeRestart && !isFirstBootOrUpgrade()) {
                    long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 20, elapsedRealtime2);
                    if (elapsedRealtime2 > 60000) {
                        android.util.Slog.wtf(com.android.server.utils.TimingsTraceAndSlog.SYSTEM_SERVER_TIMING_TAG, "SystemServer init took too long. uptimeMillis=" + elapsedRealtime2);
                    }
                }
                android.os.Binder.setTransactionCallback(new android.os.IBinderCallback() { // from class: com.android.server.SystemServer.1
                    public void onTransactionError(int i, int i2, int i3, int i4) {
                        com.android.server.SystemServer.this.mActivityManagerService.frozenBinderTransactionDetected(i, i2, i3, i4);
                    }
                });
                android.os.Looper.loop();
                throw new java.lang.RuntimeException("Main thread loop unexpectedly exited");
            } finally {
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private static boolean isValidTimeZoneId(java.lang.String str) {
        return (str == null || str.isEmpty() || !com.android.i18n.timezone.ZoneInfoDb.getInstance().hasTimeZone(str)) ? false : true;
    }

    private boolean isFirstBootOrUpgrade() {
        return this.mPackageManagerService.isFirstBoot() || this.mPackageManagerService.isDeviceUpgrading();
    }

    private void reportWtf(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.w(TAG, "***********************************************");
        android.util.Slog.wtf(TAG, "BOOT FAILURE " + str, th);
    }

    private void performPendingShutdown() {
        final java.lang.String str;
        java.lang.String str2 = android.os.SystemProperties.get(com.android.server.power.ShutdownThread.SHUTDOWN_ACTION_PROPERTY, "");
        if (str2 != null && str2.length() > 0) {
            final boolean z = str2.charAt(0) == '1';
            java.lang.String str3 = null;
            if (str2.length() > 1) {
                str = str2.substring(1, str2.length());
            } else {
                str = null;
            }
            if (str != null && str.startsWith("recovery-update")) {
                java.io.File file = new java.io.File(UNCRYPT_PACKAGE_FILE);
                if (file.exists()) {
                    try {
                        str3 = android.os.FileUtils.readTextFile(file, 0, null);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Error reading uncrypt package file", e);
                    }
                    if (str3 != null && str3.startsWith("/data") && !new java.io.File(BLOCK_MAP_FILE).exists()) {
                        android.util.Slog.e(TAG, "Can't find block map file, uncrypt failed or unexpected runtime restart?");
                        return;
                    }
                }
            }
            android.os.Message obtain = android.os.Message.obtain(com.android.server.UiThread.getHandler(), new java.lang.Runnable() { // from class: com.android.server.SystemServer.2
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.power.ShutdownThread.rebootOrShutdown(null, z, str);
                }
            });
            obtain.setAsynchronous(true);
            com.android.server.UiThread.getHandler().sendMessage(obtain);
        }
    }

    private void createSystemContext() {
        android.app.ActivityThread systemMain = android.app.ActivityThread.systemMain();
        this.mSystemContext = systemMain.getSystemContext();
        this.mSystemContext.setTheme(16974863);
        systemMain.getSystemUiContext().setTheme(16974863);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [android.os.IBinder, com.android.server.compat.PlatformCompat] */
    private void startBootstrapServices(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startBootstrapServices");
        timingsTraceAndSlog.traceBegin("ArtModuleServiceInitializer");
        com.android.server.art.ArtModuleServiceInitializer.setArtModuleServiceManager(new android.os.ArtModuleServiceManager());
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartWatchdog");
        com.android.server.Watchdog watchdog = com.android.server.Watchdog.getInstance();
        watchdog.start();
        this.mDumper.addDumpable(watchdog);
        timingsTraceAndSlog.traceEnd();
        android.util.Slog.i(TAG, "Reading configuration...");
        timingsTraceAndSlog.traceBegin("ReadingSystemConfig");
        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.SystemConfig.getInstance();
            }
        }, "ReadingSystemConfig");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("PlatformCompat");
        ?? platformCompat = new com.android.server.compat.PlatformCompat(this.mSystemContext);
        android.os.ServiceManager.addService("platform_compat", (android.os.IBinder) platformCompat);
        android.os.ServiceManager.addService("platform_compat_native", new com.android.server.compat.PlatformCompatNative(platformCompat));
        android.app.AppCompatCallbacks.install(new long[0]);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartFileIntegrityService");
        this.mSystemServiceManager.startService(com.android.server.security.FileIntegrityService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartInstaller");
        com.android.server.pm.Installer installer = (com.android.server.pm.Installer) this.mSystemServiceManager.startService(com.android.server.pm.Installer.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("DeviceIdentifiersPolicyService");
        this.mSystemServiceManager.startService(com.android.server.os.DeviceIdentifiersPolicyService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartFeatureFlagsService");
        this.mSystemServiceManager.startService(com.android.server.flags.FeatureFlagsService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("UriGrantsManagerService");
        this.mSystemServiceManager.startService(com.android.server.uri.UriGrantsManagerService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPowerStatsService");
        this.mSystemServiceManager.startService(com.android.server.powerstats.PowerStatsService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartIStatsService");
        startIStatsService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MemtrackProxyService");
        startMemtrackProxyService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartAccessCheckingService");
        com.android.server.LocalServices.addService(com.android.server.pm.permission.PermissionMigrationHelper.class, new com.android.server.pm.permission.PermissionMigrationHelperImpl());
        com.android.server.LocalServices.addService(com.android.server.appop.AppOpMigrationHelper.class, new com.android.server.appop.AppOpMigrationHelperImpl());
        this.mSystemServiceManager.startService(com.android.server.permission.access.AccessCheckingService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartActivityManager");
        com.android.server.wm.ActivityTaskManagerService service = ((com.android.server.wm.ActivityTaskManagerService.Lifecycle) this.mSystemServiceManager.startService(com.android.server.wm.ActivityTaskManagerService.Lifecycle.class)).getService();
        this.mActivityManagerService = com.android.server.am.ActivityManagerService.Lifecycle.startService(this.mSystemServiceManager, service);
        this.mActivityManagerService.setSystemServiceManager(this.mSystemServiceManager);
        this.mActivityManagerService.setInstaller(installer);
        this.mWindowManagerGlobalLock = service.getGlobalLock();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDataLoaderManagerService");
        this.mDataLoaderManagerService = (com.android.server.pm.DataLoaderManagerService) this.mSystemServiceManager.startService(com.android.server.pm.DataLoaderManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartIncrementalService");
        this.mIncrementalServiceHandle = startIncrementalService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPowerManager");
        this.mPowerManagerService = (com.android.server.power.PowerManagerService) this.mSystemServiceManager.startService(com.android.server.power.PowerManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartThermalManager");
        this.mSystemServiceManager.startService(com.android.server.power.ThermalManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartHintManager");
        this.mSystemServiceManager.startService(com.android.server.power.hint.HintManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("InitPowerManagement");
        this.mActivityManagerService.initPowerManagement();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRecoverySystemService");
        this.mSystemServiceManager.startService(com.android.server.recoverysystem.RecoverySystemService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        com.android.server.RescueParty.registerHealthObserver(this.mSystemContext);
        com.android.server.PackageWatchdog.getInstance(this.mSystemContext).noteBoot();
        timingsTraceAndSlog.traceBegin("StartLightsService");
        this.mSystemServiceManager.startService(com.android.server.lights.LightsService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDisplayOffloadService");
        if (android.os.SystemProperties.getBoolean("config.enable_display_offload", false)) {
            this.mSystemServiceManager.startService(WEAR_DISPLAYOFFLOAD_SERVICE_CLASS);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDisplayManager");
        this.mDisplayManagerService = (com.android.server.display.DisplayManagerService) this.mSystemServiceManager.startService(com.android.server.display.DisplayManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("WaitForDisplay");
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 100);
        timingsTraceAndSlog.traceEnd();
        if (!this.mRuntimeRestart) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 14, android.os.SystemClock.elapsedRealtime());
        }
        timingsTraceAndSlog.traceBegin("StartDomainVerificationService");
        com.android.server.pm.verify.domain.DomainVerificationService domainVerificationService = new com.android.server.pm.verify.domain.DomainVerificationService(this.mSystemContext, com.android.server.SystemConfig.getInstance(), platformCompat);
        this.mSystemServiceManager.startService(domainVerificationService);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPackageManagerService");
        try {
            com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("packagemanagermain");
            this.mPackageManagerService = com.android.server.pm.PackageManagerService.main(this.mSystemContext, installer, domainVerificationService, this.mFactoryTestMode != 0);
            com.android.server.Watchdog.getInstance().resumeWatchingCurrentThread("packagemanagermain");
            this.mFirstBoot = this.mPackageManagerService.isFirstBoot();
            this.mPackageManager = this.mSystemContext.getPackageManager();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("DexUseManagerLocal");
            com.android.server.LocalManagerRegistry.addManager(com.android.server.art.DexUseManagerLocal.class, com.android.server.art.DexUseManagerLocal.createInstance(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            if (!this.mRuntimeRestart && !isFirstBootOrUpgrade()) {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 15, android.os.SystemClock.elapsedRealtime());
            }
            if (!android.os.SystemProperties.getBoolean("config.disable_otadexopt", false)) {
                timingsTraceAndSlog.traceBegin("StartOtaDexOptService");
                try {
                    com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("moveab");
                    com.android.server.pm.OtaDexoptService.main(this.mSystemContext, this.mPackageManagerService);
                } finally {
                    try {
                    } finally {
                    }
                }
            }
            if (android.os.Build.IS_ARC) {
                timingsTraceAndSlog.traceBegin("StartArcSystemHealthService");
                this.mSystemServiceManager.startService(ARC_SYSTEM_HEALTH_SERVICE);
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartUserManagerService");
            this.mSystemServiceManager.startService(com.android.server.pm.UserManagerService.LifeCycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("InitAttributerCache");
            com.android.internal.policy.AttributeCache.init(this.mSystemContext);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SetSystemProcess");
            this.mActivityManagerService.setSystemProcess();
            timingsTraceAndSlog.traceEnd();
            platformCompat.registerPackageReceiver(this.mSystemContext);
            timingsTraceAndSlog.traceBegin("InitWatchdog");
            watchdog.init(this.mSystemContext, this.mActivityManagerService);
            timingsTraceAndSlog.traceEnd();
            this.mDisplayManagerService.setupSchedulerPolicies();
            timingsTraceAndSlog.traceBegin("StartOverlayManagerService");
            this.mSystemServiceManager.startService(new com.android.server.om.OverlayManagerService(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartResourcesManagerService");
            com.android.server.resources.ResourcesManagerService resourcesManagerService = new com.android.server.resources.ResourcesManagerService(this.mSystemContext);
            resourcesManagerService.setActivityManagerService(this.mActivityManagerService);
            this.mSystemServiceManager.startService(resourcesManagerService);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSensorPrivacyService");
            this.mSystemServiceManager.startService(new com.android.server.sensorprivacy.SensorPrivacyService(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            if (android.os.SystemProperties.getInt("persist.sys.displayinset.top", 0) > 0) {
                this.mActivityManagerService.updateSystemUiContext();
                ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).onOverlayChanged();
            }
            timingsTraceAndSlog.traceBegin("StartSensorService");
            this.mSystemServiceManager.startService(com.android.server.sensors.SensorService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceEnd();
        } catch (java.lang.Throwable th) {
            com.android.server.Watchdog.getInstance().resumeWatchingCurrentThread("packagemanagermain");
            throw th;
        }
    }

    private void startCoreServices(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startCoreServices");
        timingsTraceAndSlog.traceBegin("StartSystemConfigService");
        this.mSystemServiceManager.startService(com.android.server.SystemConfigService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBatteryService");
        this.mSystemServiceManager.startService(com.android.server.BatteryService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartUsageService");
        this.mSystemServiceManager.startService(com.android.server.usage.UsageStatsService.class);
        this.mActivityManagerService.setUsageStatsManager((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class));
        timingsTraceAndSlog.traceEnd();
        if (this.mPackageManager.hasSystemFeature("android.software.webview")) {
            timingsTraceAndSlog.traceBegin("StartWebViewUpdateService");
            this.mWebViewUpdateService = (com.android.server.webkit.WebViewUpdateService) this.mSystemServiceManager.startService(com.android.server.webkit.WebViewUpdateService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartCachedDeviceStateService");
        this.mSystemServiceManager.startService(com.android.server.CachedDeviceStateService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBinderCallsStatsService");
        this.mSystemServiceManager.startService(com.android.server.BinderCallsStatsService.LifeCycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartLooperStatsService");
        this.mSystemServiceManager.startService(com.android.server.LooperStatsService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRollbackManagerService");
        this.mSystemServiceManager.startService(ROLLBACK_MANAGER_SERVICE_CLASS);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartNativeTombstoneManagerService");
        this.mSystemServiceManager.startService(com.android.server.os.NativeTombstoneManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBugreportManagerService");
        this.mSystemServiceManager.startService(com.android.server.os.BugreportManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin(com.android.server.gpu.GpuService.TAG);
        this.mSystemServiceManager.startService(com.android.server.gpu.GpuService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRemoteProvisioningService");
        this.mSystemServiceManager.startService(com.android.server.security.rkp.RemoteProvisioningService.class);
        timingsTraceAndSlog.traceEnd();
        if (android.os.Build.IS_DEBUGGABLE || android.os.Build.IS_ENG) {
            timingsTraceAndSlog.traceBegin("CpuMonitorService");
            this.mSystemServiceManager.startService(com.android.server.cpu.CpuMonitorService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(171:232|(2:233|234)|235|(1:237)|238|(1:242)|243|(1:642)|247|(3:248|249|250)|(2:251|252)|253|(1:255)(1:634)|256|(1:258)(1:633)|259|(1:261)(1:632)|262|(1:264)(1:631)|265|(1:267)|268|(1:270)(1:630)|271|(2:272|273)|274|275|276|277|278|279|280|281|282|(1:284)|285|286|287|288|289|290|(1:293)|(1:295)|296|(1:298)|299|(1:301)|302|(1:304)|305|(1:307)|308|(2:309|310)|311|312|313|314|315|316|317|318|319|(2:320|321)|(3:322|323|324)|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|343|344|345|(2:346|347)|348|349|350|351|(4:353|354|355|356)|(4:361|362|363|364)|368|(1:370)(1:560)|371|(1:373)|374|(1:376)(6:547|548|549|550|551|552)|377|(1:379)|(1:381)|(1:383)|(4:385|386|387|388)|392|(1:394)|395|396|397|398|(1:543)|(1:405)|406|407|408|409|(1:411)|412|(1:414)|415|(1:539)|419|(1:421)|422|(1:424)|425|426|427|428|429|430|431|(1:532)(6:434|435|436|437|438|439)|440|441|442|443|(1:445)|446|(1:448)|449|(1:451)|452|(1:454)|455|(1:457)|458|(1:521)|462|(1:520)|466|(1:468)|469|(1:471)|472|(1:474)|475|476|477|478|479|480|(1:482)|(1:484)|(1:486)|487|(1:489)|(4:491|492|493|494)|(4:499|500|501|502)|506|(2:507|508)|509) */
    /* JADX WARN: Can't wrap try/catch for region: R(174:232|(2:233|234)|235|(1:237)|238|(1:242)|243|(1:642)|247|248|249|250|251|252|253|(1:255)(1:634)|256|(1:258)(1:633)|259|(1:261)(1:632)|262|(1:264)(1:631)|265|(1:267)|268|(1:270)(1:630)|271|(2:272|273)|274|275|276|277|278|279|280|281|282|(1:284)|285|286|287|288|289|290|(1:293)|(1:295)|296|(1:298)|299|(1:301)|302|(1:304)|305|(1:307)|308|(2:309|310)|311|312|313|314|315|316|317|318|319|(2:320|321)|(3:322|323|324)|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|343|344|345|(2:346|347)|348|349|350|351|(4:353|354|355|356)|(4:361|362|363|364)|368|(1:370)(1:560)|371|(1:373)|374|(1:376)(6:547|548|549|550|551|552)|377|(1:379)|(1:381)|(1:383)|(4:385|386|387|388)|392|(1:394)|395|396|397|398|(1:543)|(1:405)|406|407|408|409|(1:411)|412|(1:414)|415|(1:539)|419|(1:421)|422|(1:424)|425|426|427|428|429|430|431|(1:532)(6:434|435|436|437|438|439)|440|441|442|443|(1:445)|446|(1:448)|449|(1:451)|452|(1:454)|455|(1:457)|458|(1:521)|462|(1:520)|466|(1:468)|469|(1:471)|472|(1:474)|475|476|477|478|479|480|(1:482)|(1:484)|(1:486)|487|(1:489)|(4:491|492|493|494)|(4:499|500|501|502)|506|(2:507|508)|509) */
    /* JADX WARN: Can't wrap try/catch for region: R(176:232|(2:233|234)|235|(1:237)|238|(1:242)|243|(1:642)|247|248|249|250|251|252|253|(1:255)(1:634)|256|(1:258)(1:633)|259|(1:261)(1:632)|262|(1:264)(1:631)|265|(1:267)|268|(1:270)(1:630)|271|272|273|274|275|276|277|278|279|280|281|282|(1:284)|285|286|287|288|289|290|(1:293)|(1:295)|296|(1:298)|299|(1:301)|302|(1:304)|305|(1:307)|308|309|310|311|312|313|314|315|316|317|318|319|(2:320|321)|(3:322|323|324)|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|343|344|345|(2:346|347)|348|349|350|351|(4:353|354|355|356)|(4:361|362|363|364)|368|(1:370)(1:560)|371|(1:373)|374|(1:376)(6:547|548|549|550|551|552)|377|(1:379)|(1:381)|(1:383)|(4:385|386|387|388)|392|(1:394)|395|396|397|398|(1:543)|(1:405)|406|407|408|409|(1:411)|412|(1:414)|415|(1:539)|419|(1:421)|422|(1:424)|425|426|427|428|429|430|431|(1:532)(6:434|435|436|437|438|439)|440|441|442|443|(1:445)|446|(1:448)|449|(1:451)|452|(1:454)|455|(1:457)|458|(1:521)|462|(1:520)|466|(1:468)|469|(1:471)|472|(1:474)|475|476|477|478|479|480|(1:482)|(1:484)|(1:486)|487|(1:489)|(4:491|492|493|494)|(4:499|500|501|502)|506|(2:507|508)|509) */
    /* JADX WARN: Can't wrap try/catch for region: R(82:8|9|(2:11|(104:13|(1:15)|16|(1:18)|19|(1:21)|22|(1:24)|25|(1:27)|28|(1:30)(1:691)|31|(1:34)|35|(1:37)(2:687|(1:689)(1:690))|38|(1:42)|43|44|(1:46)(2:683|(1:685))|47|(6:49|(1:51)(5:670|671|672|673|674)|52|53|54|55)(1:682)|56|57|58|59|(7:63|64|65|66|67|68|69)|76|77|78|79|80|81|82|83|84|85|86|87|88|89|(1:91)(182:232|233|234|235|(1:237)|238|(1:242)|243|(1:642)|247|248|249|250|251|252|253|(1:255)(1:634)|256|(1:258)(1:633)|259|(1:261)(1:632)|262|(1:264)(1:631)|265|(1:267)|268|(1:270)(1:630)|271|272|273|274|275|276|277|278|279|280|281|282|(1:284)|285|286|287|288|289|290|(1:293)|(1:295)|296|(1:298)|299|(1:301)|302|(1:304)|305|(1:307)|308|309|310|311|312|313|314|315|316|317|318|319|320|321|322|323|324|325|326|327|328|329|330|331|332|333|334|335|336|337|338|339|340|341|342|343|344|345|346|347|348|349|350|351|(4:353|354|355|356)|(4:361|362|363|364)|368|(1:370)(1:560)|371|(1:373)|374|(1:376)(6:547|548|549|550|551|552)|377|(1:379)|(1:381)|(1:383)|(4:385|386|387|388)|392|(1:394)|395|396|397|398|(1:543)|(1:405)|406|407|408|409|(1:411)|412|(1:414)|415|(1:539)|419|(1:421)|422|(1:424)|425|426|427|428|429|430|431|(1:532)(6:434|435|436|437|438|439)|440|441|442|443|(1:445)|446|(1:448)|449|(1:451)|452|(1:454)|455|(1:457)|458|(1:521)|462|(1:520)|466|(1:468)|469|(1:471)|472|(1:474)|475|476|477|478|479|480|(1:482)|(1:484)|(1:486)|487|(1:489)|(4:491|492|493|494)|(4:499|500|501|502)|506|507|508|509)|92|(4:94|(1:96)|97|(1:99))|100|(1:102)|103|(1:105)|106|(1:108)|(1:110)|111|(1:113)(1:231)|114|(1:116)|117|(2:119|(1:(1:125)(1:124))(1:126))|127|(1:129)(1:230)|130|131|132|133|134|135|136|(2:218|219)|138|(1:140)|141|(1:143)(1:217)|144|145|146|147|148|149|150|12df|(1:158)|159|(1:161)|162|163|164|165|(6:167|168|169|170|171|172)|177|178|(1:180)|181|(1:183)|184|(1:186)|187|(1:202)|191|(1:193)|194|195|196|197|198))|692|16|(0)|19|(0)|22|(0)|25|(0)|28|(0)(0)|31|(1:34)|35|(0)(0)|38|(2:40|42)|43|44|(0)(0)|47|(0)(0)|56|57|58|59|(8:61|63|64|65|66|67|68|69)|76|77|78|79|80|81|82|83|84|85|86|87|88|89|(0)(0)|92|(0)|100|(0)|103|(0)|106|(0)|(0)|111|(0)(0)|114|(0)|117|(0)|127|(0)(0)|130|131|132|133|134|135|136|(0)|138|(0)|141|(0)(0)|144|145|146|147|148|149|150|12df) */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x12d3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x12d4, code lost:
    
        reportWtf("RegisterLogMteState", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x12bf, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x12c0, code lost:
    
        reportWtf("making Window Manager Service ready", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x121b, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x1222, code lost:
    
        reportWtf("Making " + r1 + " ready", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x121e, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x121f, code lost:
    
        r27 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:514:0x0e5b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:515:0x0e5c, code lost:
    
        r3 = r0;
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x0e61, code lost:
    
        reportWtf("starting MediaRouterService", r3);
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:518:0x0e5e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:519:0x0e5f, code lost:
    
        r3 = r0;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:523:0x0cc2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x0cc3, code lost:
    
        reportWtf("starting CertBlacklister", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:534:0x0c86, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:535:0x0c87, code lost:
    
        reportWtf("starting RuntimeService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0c6a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:538:0x0c6b, code lost:
    
        reportWtf("starting DiskStats Service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:541:0x0b72, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:542:0x0b73, code lost:
    
        android.util.Slog.e(com.android.server.SystemServer.TAG, "Failure starting HardwarePropertiesManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:546:0x0b1f, code lost:
    
        android.util.Slog.e(com.android.server.SystemServer.TAG, "Failure starting AdbService");
     */
    /* JADX WARN: Code restructure failed: missing block: B:562:0x09b9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x09ba, code lost:
    
        reportWtf("starting LocationTimeZoneManagerService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:568:0x0984, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:570:0x098b, code lost:
    
        reportWtf("starting TimeZoneDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x0987, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:573:0x0988, code lost:
    
        r23 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:575:0x0962, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:576:0x0963, code lost:
    
        r12 = r0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:577:0x0968, code lost:
    
        reportWtf("starting Country Detector", r12);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:579:0x0965, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:580:0x0966, code lost:
    
        r12 = r0;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:582:0x0938, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:583:0x0939, code lost:
    
        reportWtf("starting TimeDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:585:0x08f2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:586:0x08f3, code lost:
    
        reportWtf("starting UpdateLockService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:588:0x08d6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:589:0x08d7, code lost:
    
        reportWtf("starting SystemUpdateManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:598:0x0893, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:599:0x0894, code lost:
    
        r8 = r0;
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:600:0x0899, code lost:
    
        reportWtf("starting VPN Manager Service", r8);
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:602:0x0896, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:603:0x0897, code lost:
    
        r8 = r0;
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x0878, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:606:0x0879, code lost:
    
        reportWtf("starting SecurityStateManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:611:0x0775, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:612:0x0776, code lost:
    
        r4 = r0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:613:0x077b, code lost:
    
        reportWtf("starting NetworkPolicy Service", r4);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:615:0x0778, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:616:0x0779, code lost:
    
        r4 = r0;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:618:0x0701, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:619:0x0702, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:620:0x0707, code lost:
    
        reportWtf("starting NetworkManagement Service", r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x0704, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:623:0x0705, code lost:
    
        r4 = r0;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:625:0x06e6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:626:0x06e7, code lost:
    
        reportWtf("initializing NetworkStackClient", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:647:0x04f1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:648:0x04f2, code lost:
    
        reportWtf("performing fstrim", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:650:0x04da, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:657:0x150e, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:659:0x0496, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:660:0x0497, code lost:
    
        reportWtf("starting GrammarInflectionService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:662:0x047e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:663:0x047f, code lost:
    
        reportWtf("starting LocaleManagerService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:665:0x0409, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:666:0x040a, code lost:
    
        reportWtf("making display ready", r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x1050  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x106b  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x10fc  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x110f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x111e  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x113f  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x1158  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x1193  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x1277  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x1289  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x12e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x016c A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x129c  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x1242 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0198 A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x11a3  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x1133  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0512  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01c5 A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:255:0x05f1  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0652  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0672  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0692  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x06aa  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0213 A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0790 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:295:0x07b6  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x07ee  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x080b  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0828  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x09d1  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x09eb  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x0a10  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x0a30  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0a46  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0aac  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x02b7 A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0abd  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x0ace  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0adf  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x0b02  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0b34  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0b53  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0b80  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0bd5  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x0bee  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0c23  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0c4b  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0c93 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:445:0x0d11  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0d2d  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0d55  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0d71  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0da8  */
    /* JADX WARN: Removed duplicated region for block: B:460:0x0dc2  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0de6  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0e09  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0e22  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0e3b  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0e84  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x0e98  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x0ea9  */
    /* JADX WARN: Removed duplicated region for block: B:489:0x0edf  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0ef0  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0f06  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0a50  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0a20  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:630:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:631:0x0682  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x0662  */
    /* JADX WARN: Removed duplicated region for block: B:633:0x0633  */
    /* JADX WARN: Removed duplicated region for block: B:634:0x0601  */
    /* JADX WARN: Removed duplicated region for block: B:682:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:683:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:687:0x02bf A[Catch: all -> 0x00de, TryCatch #29 {all -> 0x00de, blocks: (B:9:0x006e, B:11:0x00c9, B:13:0x00d3, B:16:0x00f1, B:18:0x016c, B:19:0x017b, B:21:0x0198, B:22:0x01a7, B:24:0x01c5, B:25:0x01d7, B:27:0x0213, B:28:0x0222, B:31:0x0235, B:34:0x0284, B:35:0x0293, B:37:0x02b7, B:38:0x02e4, B:40:0x0315, B:42:0x031b, B:43:0x032a, B:687:0x02bf, B:689:0x02cb, B:690:0x02d3, B:692:0x00e2), top: B:8:0x006e }] */
    /* JADX WARN: Removed duplicated region for block: B:691:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0fa3  */
    /* JADX WARN: Type inference failed for: r10v19, types: [android.os.IBinder, com.android.server.wm.WindowManagerService] */
    /* JADX WARN: Type inference failed for: r11v1, types: [android.os.IBinder, com.android.server.input.InputManagerService] */
    /* JADX WARN: Type inference failed for: r8v54, types: [android.os.IBinder, com.android.server.statusbar.StatusBarManagerService] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void startOtherServices(@android.annotation.NonNull final com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        final boolean detectSafeMode;
        com.android.server.TelephonyRegistry telephonyRegistry;
        com.android.internal.widget.ILockSettings iLockSettings;
        com.android.internal.widget.ILockSettings iLockSettings2;
        com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle lifecycle;
        com.android.server.net.NetworkPolicyManagerService networkPolicyManagerService;
        java.lang.Throwable th;
        com.android.server.VcnManagementService vcnManagementService;
        com.android.server.net.NetworkPolicyManagerService networkPolicyManagerService2;
        com.android.server.VcnManagementService vcnManagementService2;
        com.android.server.CountryDetectorService countryDetectorService;
        android.os.INetworkManagementService iNetworkManagementService;
        com.android.server.SystemServiceManager systemServiceManager;
        java.lang.StringBuilder sb;
        com.android.server.timedetector.NetworkTimeUpdateService networkTimeUpdateService;
        boolean hasSystemFeature;
        boolean hasSystemFeature2;
        boolean hasSystemFeature3;
        com.android.server.media.MediaRouterService mediaRouterService;
        com.android.server.VpnManagerService vpnManagerService;
        com.android.internal.widget.ILockSettings iLockSettings3;
        final com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle lifecycle2;
        com.android.server.timedetector.NetworkTimeUpdateService networkTimeUpdateService2;
        com.android.server.VcnManagementService vcnManagementService3;
        java.lang.Throwable th2;
        android.net.vcn.IVcnManagementService.Stub create;
        ?? statusBarManagerService;
        com.android.server.MmsServiceBroker mmsServiceBroker;
        final com.android.server.HsumBootUserInitializer createInstance;
        java.lang.String[] strArr;
        java.lang.StringBuilder sb2;
        timingsTraceAndSlog.traceBegin("startOtherServices");
        this.mSystemServiceManager.updateOtherServicesStartIndex();
        final android.content.Context context = this.mSystemContext;
        boolean z = android.os.SystemProperties.getBoolean("config.disable_systemtextclassifier", false);
        boolean z2 = android.os.SystemProperties.getBoolean("config.disable_networktime", false);
        boolean z3 = android.os.SystemProperties.getBoolean("config.disable_cameraservice", false);
        final boolean hasSystemFeature4 = context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        boolean hasSystemFeature5 = context.getPackageManager().hasSystemFeature("org.chromium.arc");
        boolean hasSystemFeature6 = context.getPackageManager().hasSystemFeature("android.software.leanback");
        boolean hasSystemFeature7 = context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance");
        if (!android.os.Build.IS_DEBUGGABLE || !android.os.SystemProperties.getBoolean("debug.crash_system", false)) {
            try {
                this.mZygotePreload = com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.SystemServer.lambda$startOtherServices$1();
                    }
                }, "SecondaryZygotePreload");
                timingsTraceAndSlog.traceBegin("StartKeyAttestationApplicationIdProviderService");
                android.os.ServiceManager.addService("sec_key_att_app_id_provider", new com.android.server.security.KeyAttestationApplicationIdProviderService(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartKeyChainSystemService");
                this.mSystemServiceManager.startService(com.android.server.security.KeyChainSystemService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartBinaryTransparencyService");
                this.mSystemServiceManager.startService(com.android.server.BinaryTransparencyService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSchedulingPolicyService");
                android.os.ServiceManager.addService("scheduling_policy", new com.android.server.os.SchedulingPolicyService());
                timingsTraceAndSlog.traceEnd();
                if (!this.mPackageManager.hasSystemFeature("android.hardware.microphone")) {
                    if (!this.mPackageManager.hasSystemFeature("android.software.telecom")) {
                        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartTelephonyRegistry");
                        com.android.internal.telephony.ITelephonyRegistry.Stub telephonyRegistry2 = new com.android.server.TelephonyRegistry(context, new com.android.server.TelephonyRegistry.ConfigurationProvider());
                        android.os.ServiceManager.addService("telephony.registry", telephonyRegistry2);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartEntropyMixer");
                        this.mEntropyMixer = new com.android.server.EntropyMixer(context);
                        timingsTraceAndSlog.traceEnd();
                        this.mContentResolver = context.getContentResolver();
                        timingsTraceAndSlog.traceBegin("StartAccountManagerService");
                        this.mSystemServiceManager.startService(ACCOUNT_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartContentService");
                        this.mSystemServiceManager.startService(CONTENT_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InstallSystemProviders");
                        this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
                        this.mSystemServiceManager.startService(UPDATABLE_DEVICE_CONFIG_SERVICE_CLASS);
                        android.database.sqlite.SQLiteCompatibilityWalFlags.reset();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDropBoxManager");
                        this.mSystemServiceManager.startService(com.android.server.DropBoxManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                            timingsTraceAndSlog.traceBegin("StartEnhancedConfirmationService");
                            this.mSystemServiceManager.startService(ENHANCED_CONFIRMATION_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartRoleManagerService");
                        com.android.server.LocalManagerRegistry.addManager(com.android.server.role.RoleServicePlatformHelper.class, new com.android.server.policy.role.RoleServicePlatformHelperImpl(this.mSystemContext));
                        this.mSystemServiceManager.startService(ROLE_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature6) {
                            timingsTraceAndSlog.traceBegin("StartVibratorManagerService");
                            this.mSystemServiceManager.startService(com.android.server.vibrator.VibratorManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
                        android.os.ServiceManager.addService("dynamic_system", new com.android.server.DynamicSystemService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                            timingsTraceAndSlog.traceBegin("StartConsumerIrService");
                            android.os.ServiceManager.addService("consumer_ir", new com.android.server.ConsumerIrService(context));
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartResourceEconomy");
                        this.mSystemServiceManager.startService(RESOURCE_ECONOMY_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
                        this.mSystemServiceManager.startService(ALARM_MANAGER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartInputManagerService");
                        ?? inputManagerService = new com.android.server.input.InputManagerService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
                        this.mSystemServiceManager.startService(com.android.server.devicestate.DeviceStateManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!z3) {
                            timingsTraceAndSlog.traceBegin("StartCameraServiceProxy");
                            this.mSystemServiceManager.startService(com.android.server.camera.CameraServiceProxy.class);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartWindowManagerService");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
                        ?? main = com.android.server.wm.WindowManagerService.main(context, inputManagerService, this.mFirstBoot, new com.android.server.policy.PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
                        android.os.ServiceManager.addService("window", (android.os.IBinder) main, false, 17);
                        android.os.ServiceManager.addService("input", (android.os.IBinder) inputManagerService, false, 1);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SetWindowManagerService");
                        this.mActivityManagerService.setWindowManager(main);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
                        main.onInitReady();
                        timingsTraceAndSlog.traceEnd();
                        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.SystemServer.lambda$startOtherServices$2();
                            }
                        }, START_SENSOR_MANAGER_SERVICE);
                        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.SystemServer.lambda$startOtherServices$3();
                            }
                        }, START_HIDL_SERVICES);
                        if (!hasSystemFeature4 && hasSystemFeature7) {
                            timingsTraceAndSlog.traceBegin("StartVrManagerService");
                            this.mSystemServiceManager.startService(com.android.server.vr.VrManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartInputManager");
                        inputManagerService.setWindowManagerCallbacks(main.getInputManagerCallback());
                        inputManagerService.start();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
                        this.mDisplayManagerService.windowManagerAndInputReady();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                            android.util.Slog.i(TAG, "No Bluetooth Service (factory test)");
                        } else if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
                            android.util.Slog.i(TAG, "No Bluetooth Service (Bluetooth Hardware Not Present)");
                        } else {
                            timingsTraceAndSlog.traceBegin("StartBluetoothService");
                            this.mSystemServiceManager.startServiceFromJar(BLUETOOTH_SERVICE_CLASS, BLUETOOTH_APEX_SERVICE_JAR_PATH);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("IpConnectivityMetrics");
                        this.mSystemServiceManager.startService(IP_CONNECTIVITY_METRICS_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
                        this.mSystemServiceManager.startService(com.android.server.net.watchlist.NetworkWatchlistService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PinnerService");
                        this.mSystemServiceManager.startService(com.android.server.PinnerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (android.os.Build.IS_DEBUGGABLE && com.android.server.profcollect.ProfcollectForwardingService.enabled()) {
                            timingsTraceAndSlog.traceBegin(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG);
                            this.mSystemServiceManager.startService(com.android.server.profcollect.ProfcollectForwardingService.class);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("SignedConfigService");
                        com.android.server.signedconfig.SignedConfigService.registerUpdateReceiver(this.mSystemContext);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppIntegrityService");
                        this.mSystemServiceManager.startService(com.android.server.integrity.AppIntegrityManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLogcatManager");
                        this.mSystemServiceManager.startService(com.android.server.logcat.LogcatManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        detectSafeMode = main.detectSafeMode();
                        if (!detectSafeMode) {
                            android.provider.Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 1);
                        } else if (context.getResources().getBoolean(android.R.bool.config_autoResetAirplaneMode)) {
                            android.provider.Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 0);
                        }
                        if (this.mFactoryTestMode != 1) {
                            telephonyRegistry = telephonyRegistry2;
                        } else {
                            timingsTraceAndSlog.traceBegin("StartInputMethodManagerLifecycle");
                            java.lang.String string = context.getResources().getString(android.R.string.config_defaultWellbeingPackage);
                            if (string.isEmpty()) {
                                this.mSystemServiceManager.startService(com.android.server.inputmethod.InputMethodManagerService.Lifecycle.class);
                                telephonyRegistry = telephonyRegistry2;
                            } else {
                                try {
                                    sb2 = new java.lang.StringBuilder();
                                    telephonyRegistry = telephonyRegistry2;
                                } catch (java.lang.Throwable th3) {
                                    th = th3;
                                    telephonyRegistry = telephonyRegistry2;
                                }
                                try {
                                    sb2.append("Starting custom IMMS: ");
                                    sb2.append(string);
                                    android.util.Slog.i(TAG, sb2.toString());
                                    this.mSystemServiceManager.startService(string);
                                } catch (java.lang.Throwable th4) {
                                    th = th4;
                                    reportWtf("starting " + string, th);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAccessibilityManagerService");
                                    this.mSystemServiceManager.startService(ACCESSIBILITY_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                                    main.displayReady();
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mFactoryTestMode != 1) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartUiModeManager");
                                    this.mSystemServiceManager.startService(com.android.server.UiModeManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                                    this.mSystemServiceManager.startService(com.android.server.locales.LocaleManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                                    this.mSystemServiceManager.startService(com.android.server.grammaticalinflection.GrammaticalInflectionService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                                    this.mSystemServiceManager.startService(APP_HIBERNATION_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                                    com.android.server.pm.DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                                    com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                                    this.mPackageManagerService.updatePackagesIfNeeded();
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                                    this.mPackageManagerService.performFstrimIfNeeded();
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mFactoryTestMode != 1) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                                    this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (hasSystemFeature4) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                                    }
                                    if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                                    this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                                    this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                                    this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                                    this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                                    this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                                    this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                                    this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                                    this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (android.server.Flags.telemetryApisService()) {
                                    }
                                    if (detectSafeMode) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                                    }
                                    if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartClipboardService");
                                    this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("AppServiceManager");
                                    this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                                    this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                                    timingsTraceAndSlog.traceEnd();
                                    java.lang.String string2 = context.getResources().getString(1057554451);
                                    java.lang.reflect.Constructor<?> declaredConstructor = java.lang.Class.forName(string2).getDeclaredConstructor(android.content.Context.class);
                                    declaredConstructor.setAccessible(true);
                                    java.lang.Object newInstance = declaredConstructor.newInstance(this.mSystemContext);
                                    com.android.server.input.InputManagerService inputManagerService2 = inputManagerService;
                                    java.lang.reflect.Method declaredMethod = newInstance.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                                    declaredMethod.setAccessible(true);
                                    declaredMethod.invoke(newInstance, new java.lang.Object[0]);
                                    timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                                    if (iLockSettings3 != null) {
                                    }
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                                    timingsTraceAndSlog.traceEnd();
                                    createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                                    if (createInstance != null) {
                                    }
                                    if (android.os.UserManager.isCommunalProfileEnabled()) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                                    main.systemReady();
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                                    com.android.server.LogMteState.register(context);
                                    timingsTraceAndSlog.traceEnd();
                                    synchronized (com.android.server.SystemService.class) {
                                    }
                                }
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAccessibilityManagerService");
                            try {
                                this.mSystemServiceManager.startService(ACCESSIBILITY_MANAGER_SERVICE_CLASS);
                            } catch (java.lang.Throwable th5) {
                                reportWtf("starting Accessibility Manager", th5);
                            }
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                        main.displayReady();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1 && !"0".equals(android.os.SystemProperties.get("system_init.startmountservice"))) {
                            timingsTraceAndSlog.traceBegin("StartStorageManagerService");
                            try {
                                this.mSystemServiceManager.startService(STORAGE_MANAGER_SERVICE_CLASS);
                                android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"));
                            } catch (java.lang.Throwable th6) {
                                reportWtf("starting StorageManagerService", th6);
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStorageStatsService");
                            try {
                                this.mSystemServiceManager.startService(STORAGE_STATS_SERVICE_CLASS);
                            } catch (java.lang.Throwable th7) {
                                reportWtf("starting StorageStatsService", th7);
                            }
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartUiModeManager");
                        this.mSystemServiceManager.startService(com.android.server.UiModeManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                        this.mSystemServiceManager.startService(com.android.server.locales.LocaleManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                        this.mSystemServiceManager.startService(com.android.server.grammaticalinflection.GrammaticalInflectionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                        this.mSystemServiceManager.startService(APP_HIBERNATION_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                        com.android.server.pm.DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                        com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                        this.mPackageManagerService.updatePackagesIfNeeded();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                        this.mPackageManagerService.performFstrimIfNeeded();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                            iLockSettings3 = null;
                            lifecycle2 = null;
                            vpnManagerService = null;
                            vcnManagementService3 = null;
                            networkTimeUpdateService2 = null;
                            networkPolicyManagerService2 = null;
                            countryDetectorService = null;
                            iNetworkManagementService = null;
                            mediaRouterService = null;
                        } else {
                            timingsTraceAndSlog.traceBegin("StartLockSettingsService");
                            try {
                                this.mSystemServiceManager.startService(LOCK_SETTINGS_SERVICE_CLASS);
                                iLockSettings = com.android.internal.widget.ILockSettings.Stub.asInterface(android.os.ServiceManager.getService("lock_settings"));
                            } catch (java.lang.Throwable th8) {
                                reportWtf("starting LockSettingsService service", th8);
                                iLockSettings = null;
                            }
                            timingsTraceAndSlog.traceEnd();
                            boolean z4 = !android.os.SystemProperties.get(PERSISTENT_DATA_BLOCK_PROP).equals("");
                            if (z4) {
                                timingsTraceAndSlog.traceBegin("StartPersistentDataBlock");
                                this.mSystemServiceManager.startService(com.android.server.pdb.PersistentDataBlockService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (android.os.Build.IS_ARC && android.os.SystemProperties.getInt("ro.boot.dev_mode", 0) == 1) {
                                timingsTraceAndSlog.traceBegin("StartArcPersistentDataBlock");
                                this.mSystemServiceManager.startService(ARC_PERSISTENT_DATA_BLOCK_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartTestHarnessMode");
                            this.mSystemServiceManager.startService(com.android.server.testharness.TestHarnessModeService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (z4 || com.android.server.oemlock.OemLockService.isHalPresent()) {
                                timingsTraceAndSlog.traceBegin("StartOemLockService");
                                this.mSystemServiceManager.startService(com.android.server.oemlock.OemLockService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartDeviceIdleController");
                            this.mSystemServiceManager.startService(DEVICE_IDLE_CONTROLLER_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDevicePolicyManager");
                            com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle lifecycle3 = (com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle) this.mSystemServiceManager.startService(com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatusBarManagerService");
                            try {
                                statusBarManagerService = new com.android.server.statusbar.StatusBarManagerService(context);
                                statusBarManagerService.publishGlobalActionsProvider();
                                iLockSettings2 = iLockSettings;
                                lifecycle = lifecycle3;
                            } catch (java.lang.Throwable th9) {
                                th = th9;
                                iLockSettings2 = iLockSettings;
                                lifecycle = lifecycle3;
                            }
                            try {
                                android.os.ServiceManager.addService("statusbar", (android.os.IBinder) statusBarManagerService, false, 20);
                            } catch (java.lang.Throwable th10) {
                                th = th10;
                                reportWtf("starting StatusBarManagerService", th);
                                timingsTraceAndSlog.traceEnd();
                                if (!deviceHasConfigString(context, android.R.string.config_defaultDisplayCompatHostActivity)) {
                                }
                                startContentCaptureService(context, timingsTraceAndSlog);
                                startAttentionService(context, timingsTraceAndSlog);
                                startRotationResolverService(context, timingsTraceAndSlog);
                                startSystemCaptionsManagerService(context, timingsTraceAndSlog);
                                startTextToSpeechManagerService(context, timingsTraceAndSlog);
                                startWearableSensingService(timingsTraceAndSlog);
                                if (!deviceHasConfigString(context, android.R.string.config_customVpnAlwaysOnDisconnectedDialogComponent)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartSpeechRecognitionManagerService");
                                this.mSystemServiceManager.startService(SPEECH_RECOGNITION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (!deviceHasConfigString(context, android.R.string.config_customVpnConfirmDialogComponent)) {
                                }
                                if (!deviceHasConfigString(context, android.R.string.config_defaultAttentionService)) {
                                }
                                if (deviceHasConfigString(context, android.R.string.config_defaultOnDeviceIntelligenceDeviceConfigNamespace)) {
                                }
                                if (!deviceHasConfigString(context, android.R.string.config_defaultOnDeviceSandboxedInferenceService)) {
                                }
                                timingsTraceAndSlog.traceBegin("InitConnectivityModuleConnector");
                                android.net.ConnectivityModuleConnector.getInstance().init(context);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("InitNetworkStackClient");
                                android.net.NetworkStackClient.getInstance().init();
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartNetworkManagementService");
                                android.os.INetworkManagementService iNetworkManagementService2 = com.android.server.net.NetworkManagementService.create(context);
                                android.os.ServiceManager.addService("network_management", iNetworkManagementService2);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartFontManagerService");
                                this.mSystemServiceManager.startService(new com.android.server.graphics.fonts.FontManagerService.Lifecycle(context, detectSafeMode));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                                this.mSystemServiceManager.startService(com.android.server.textservices.TextServicesManagerService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                if (!z) {
                                }
                                timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                                this.mSystemServiceManager.startService(com.android.server.NetworkScoreService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                                this.mSystemServiceManager.startServiceFromJar(NETWORK_STATS_SERVICE_INITIALIZER_CLASS, CONNECTIVITY_SERVICE_APEX_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                                android.net.INetworkPolicyManager.Stub networkPolicyManagerService3 = new com.android.server.net.NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService2);
                                android.os.ServiceManager.addService("netpolicy", networkPolicyManagerService3);
                                networkPolicyManagerService = networkPolicyManagerService3;
                                timingsTraceAndSlog.traceEnd();
                                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                                    timingsTraceAndSlog.traceBegin("StartWifi");
                                    this.mSystemServiceManager.startServiceFromJar(WIFI_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartWifiScanning");
                                    this.mSystemServiceManager.startServiceFromJar(WIFI_SCANNING_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                }
                                if (hasSystemFeature5) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartPacProxyService");
                                android.os.ServiceManager.addService("pac_proxy", new com.android.server.connectivity.PacProxyService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartConnectivityService");
                                this.mSystemServiceManager.startServiceFromJar(CONNECTIVITY_SERVICE_INITIALIZER_CLASS, CONNECTIVITY_SERVICE_APEX_PATH);
                                networkPolicyManagerService.bindConnectivityManager();
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSecurityStateManagerService");
                                android.os.ServiceManager.addService("security_state", new com.android.server.SecurityStateManagerService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                                android.net.IVpnManager.Stub create2 = com.android.server.VpnManagerService.create(context);
                                android.os.ServiceManager.addService("vpn_management", create2);
                                com.android.server.VpnManagerService vpnManagerService2 = create2;
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                                create = com.android.server.VcnManagementService.create(context);
                                android.os.ServiceManager.addService("vcn_management", create);
                                networkPolicyManagerService2 = networkPolicyManagerService;
                                vcnManagementService2 = create;
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                                android.os.ServiceManager.addService("system_update", new com.android.server.SystemUpdateManagerService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                                android.os.ServiceManager.addService("updatelock", new com.android.server.UpdateLockService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartNotificationManager");
                                this.mSystemServiceManager.startService(com.android.server.notification.NotificationManagerService.class);
                                com.android.internal.notification.SystemNotificationChannels.removeDeprecated(context);
                                com.android.internal.notification.SystemNotificationChannels.createAll(context);
                                android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                                this.mSystemServiceManager.startService(com.android.server.storage.DeviceStorageMonitorService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                                this.mSystemServiceManager.startService(TIME_DETECTOR_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                                this.mSystemServiceManager.startService(com.android.server.location.LocationManagerService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                                android.location.ICountryDetector.Stub countryDetectorService2 = new com.android.server.CountryDetectorService(context);
                                android.os.ServiceManager.addService("country_detector", countryDetectorService2);
                                com.android.server.CountryDetectorService countryDetectorService3 = countryDetectorService2;
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                                countryDetectorService = countryDetectorService3;
                                this.mSystemServiceManager.startService(TIME_ZONE_DETECTOR_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAltitudeService");
                                this.mSystemServiceManager.startService(com.android.server.location.altitude.AltitudeService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                                this.mSystemServiceManager.startService(LOCATION_TIME_ZONE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (context.getResources().getBoolean(android.R.bool.config_enableGeocoderOverlay)) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (!context.getResources().getBoolean(android.R.bool.config_enableTelephonyTimeZoneDetection)) {
                                }
                                if (deviceHasConfigString(context, android.R.string.config_defaultSearchSelectorPackageName)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAudioService");
                                if (hasSystemFeature5) {
                                }
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                                this.mSystemServiceManager.startService(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                                }
                                if (!hasSystemFeature6) {
                                }
                                if (hasSystemFeature4) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAdbService");
                                this.mSystemServiceManager.startService(ADB_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartUsbService");
                                this.mSystemServiceManager.startService(USB_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                                android.os.ServiceManager.addService("hardware_properties", new com.android.server.HardwarePropertiesManagerService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartColorDisplay");
                                this.mSystemServiceManager.startService(com.android.server.display.color.ColorDisplayService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartJobScheduler");
                                this.mSystemServiceManager.startService(JOB_SCHEDULER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                                this.mSystemServiceManager.startService(com.android.server.soundtrigger.SoundTriggerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTrustManager");
                                this.mSystemServiceManager.startService(com.android.server.trust.TrustManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                                this.mSystemServiceManager.startService(APPWIDGET_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                                this.mSystemServiceManager.startService(VOICE_RECOGNITION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (com.android.server.GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                                }
                                timingsTraceAndSlog.traceBegin("StartSensorNotification");
                                this.mSystemServiceManager.startService(com.android.server.SensorNotificationService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                                android.os.ServiceManager.addService("diskstats", new com.android.server.DiskStatsService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("RuntimeService");
                                android.os.ServiceManager.addService("runtime", new com.android.server.RuntimeService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (hasSystemFeature4) {
                                }
                                networkTimeUpdateService = null;
                                timingsTraceAndSlog.traceBegin("CertBlacklister");
                                new com.android.server.CertBlacklister(context);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                                this.mSystemServiceManager.startService(com.android.server.emergency.EmergencyAffordanceService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin(START_BLOB_STORE_SERVICE);
                                this.mSystemServiceManager.startService(BLOB_STORE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartDreamManager");
                                this.mSystemServiceManager.startService(com.android.server.dreams.DreamManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                                android.os.ServiceManager.addService("graphicsstats", new android.graphics.GraphicsStatsService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (com.android.server.coverage.CoverageService.ENABLED) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                                this.mSystemServiceManager.startService(com.android.server.security.AttestationVerificationManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                                }
                                if (context.getResources().getBoolean(android.R.bool.config_enableStylusPointerIcon)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                                this.mSystemServiceManager.startService(com.android.server.restrictions.RestrictionsManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                                this.mSystemServiceManager.startService(MEDIA_SESSION_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.interactive.TvInteractiveAppManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartTvInputManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.TvInputManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                                android.media.IMediaRouterService.Stub mediaRouterService2 = new com.android.server.media.MediaRouterService(context);
                                android.os.ServiceManager.addService("media_router", mediaRouterService2);
                                com.android.server.media.MediaRouterService mediaRouterService3 = mediaRouterService2;
                                timingsTraceAndSlog.traceEnd();
                                hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                                hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                                hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                                if (hasSystemFeature) {
                                }
                                if (hasSystemFeature2) {
                                }
                                if (hasSystemFeature3) {
                                }
                                timingsTraceAndSlog.traceBegin("StartBiometricService");
                                this.mSystemServiceManager.startService(com.android.server.biometrics.BiometricService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAuthService");
                                this.mSystemServiceManager.startService(com.android.server.biometrics.AuthService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (android.adaptiveauth.Flags.enableAdaptiveAuth()) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartSelinuxAuditLogsService");
                                com.android.server.selinux.SelinuxAuditLogsService.schedule(context);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                                this.mSystemServiceManager.startService(com.android.server.pm.ShortcutService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                                this.mSystemServiceManager.startService(com.android.server.pm.LauncherAppsService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                                this.mSystemServiceManager.startService(com.android.server.pm.CrossProfileAppsService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartPeopleService");
                                this.mSystemServiceManager.startService(com.android.server.people.PeopleService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                                this.mSystemServiceManager.startService(com.android.server.media.metrics.MediaMetricsManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                                this.mSystemServiceManager.startService(com.android.server.pm.BackgroundInstallControlService.class);
                                timingsTraceAndSlog.traceEnd();
                                mediaRouterService = mediaRouterService3;
                                vpnManagerService = vpnManagerService2;
                                iLockSettings3 = iLockSettings2;
                                lifecycle2 = lifecycle;
                                networkTimeUpdateService2 = networkTimeUpdateService;
                                vcnManagementService3 = vcnManagementService2;
                                timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                                this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (hasSystemFeature4) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                                this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                                this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                                this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                                this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                                this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                                this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                                this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                                this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (android.server.Flags.telemetryApisService()) {
                                }
                                if (detectSafeMode) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                                }
                                if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartClipboardService");
                                this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("AppServiceManager");
                                this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                                this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                                timingsTraceAndSlog.traceEnd();
                                java.lang.String string22 = context.getResources().getString(1057554451);
                                java.lang.reflect.Constructor<?> declaredConstructor2 = java.lang.Class.forName(string22).getDeclaredConstructor(android.content.Context.class);
                                declaredConstructor2.setAccessible(true);
                                java.lang.Object newInstance2 = declaredConstructor2.newInstance(this.mSystemContext);
                                com.android.server.input.InputManagerService inputManagerService22 = inputManagerService;
                                java.lang.reflect.Method declaredMethod2 = newInstance2.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                                declaredMethod2.setAccessible(true);
                                declaredMethod2.invoke(newInstance2, new java.lang.Object[0]);
                                timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                                if (iLockSettings3 != null) {
                                }
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                                timingsTraceAndSlog.traceEnd();
                                createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                                if (createInstance != null) {
                                }
                                if (android.os.UserManager.isCommunalProfileEnabled()) {
                                }
                                timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                                main.systemReady();
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                                com.android.server.LogMteState.register(context);
                                timingsTraceAndSlog.traceEnd();
                                synchronized (com.android.server.SystemService.class) {
                                }
                            }
                            timingsTraceAndSlog.traceEnd();
                            if (!deviceHasConfigString(context, android.R.string.config_defaultDisplayCompatHostActivity)) {
                                timingsTraceAndSlog.traceBegin("StartMusicRecognitionManagerService");
                                this.mSystemServiceManager.startService(MUSIC_RECOGNITION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.d(TAG, "MusicRecognitionManagerService not defined by OEM or disabled by flag");
                            }
                            startContentCaptureService(context, timingsTraceAndSlog);
                            startAttentionService(context, timingsTraceAndSlog);
                            startRotationResolverService(context, timingsTraceAndSlog);
                            startSystemCaptionsManagerService(context, timingsTraceAndSlog);
                            startTextToSpeechManagerService(context, timingsTraceAndSlog);
                            startWearableSensingService(timingsTraceAndSlog);
                            if (!deviceHasConfigString(context, android.R.string.config_customVpnAlwaysOnDisconnectedDialogComponent)) {
                                timingsTraceAndSlog.traceBegin("StartAmbientContextService");
                                this.mSystemServiceManager.startService(AMBIENT_CONTEXT_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.d(TAG, "AmbientContextManagerService not defined by OEM or disabled by flag");
                            }
                            timingsTraceAndSlog.traceBegin("StartSpeechRecognitionManagerService");
                            this.mSystemServiceManager.startService(SPEECH_RECOGNITION_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (!deviceHasConfigString(context, android.R.string.config_customVpnConfirmDialogComponent)) {
                                timingsTraceAndSlog.traceBegin("StartAppPredictionService");
                                this.mSystemServiceManager.startService(APP_PREDICTION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.d(TAG, "AppPredictionService not defined by OEM");
                            }
                            if (!deviceHasConfigString(context, android.R.string.config_defaultAttentionService)) {
                                timingsTraceAndSlog.traceBegin("StartContentSuggestionsService");
                                this.mSystemServiceManager.startService(CONTENT_SUGGESTIONS_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.d(TAG, "ContentSuggestionsService not defined by OEM");
                            }
                            if (deviceHasConfigString(context, android.R.string.config_defaultOnDeviceIntelligenceDeviceConfigNamespace)) {
                                timingsTraceAndSlog.traceBegin("StartSearchUiService");
                                this.mSystemServiceManager.startService(SEARCH_UI_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!deviceHasConfigString(context, android.R.string.config_defaultOnDeviceSandboxedInferenceService)) {
                                timingsTraceAndSlog.traceBegin("StartSmartspaceService");
                                this.mSystemServiceManager.startService(SMARTSPACE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.d(TAG, "SmartspaceManagerService not defined by OEM or disabled by flag");
                            }
                            timingsTraceAndSlog.traceBegin("InitConnectivityModuleConnector");
                            try {
                                android.net.ConnectivityModuleConnector.getInstance().init(context);
                            } catch (java.lang.Throwable th11) {
                                reportWtf("initializing ConnectivityModuleConnector", th11);
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("InitNetworkStackClient");
                            android.net.NetworkStackClient.getInstance().init();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNetworkManagementService");
                            android.os.INetworkManagementService iNetworkManagementService22 = com.android.server.net.NetworkManagementService.create(context);
                            android.os.ServiceManager.addService("network_management", iNetworkManagementService22);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartFontManagerService");
                            this.mSystemServiceManager.startService(new com.android.server.graphics.fonts.FontManagerService.Lifecycle(context, detectSafeMode));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                            this.mSystemServiceManager.startService(com.android.server.textservices.TextServicesManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!z) {
                                timingsTraceAndSlog.traceBegin("StartTextClassificationManagerService");
                                this.mSystemServiceManager.startService(com.android.server.textclassifier.TextClassificationManagerService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                            this.mSystemServiceManager.startService(com.android.server.NetworkScoreService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                            this.mSystemServiceManager.startServiceFromJar(NETWORK_STATS_SERVICE_INITIALIZER_CLASS, CONNECTIVITY_SERVICE_APEX_PATH);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                            android.net.INetworkPolicyManager.Stub networkPolicyManagerService32 = new com.android.server.net.NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService22);
                            android.os.ServiceManager.addService("netpolicy", networkPolicyManagerService32);
                            networkPolicyManagerService = networkPolicyManagerService32;
                            timingsTraceAndSlog.traceEnd();
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi") && !hasSystemFeature5) {
                                timingsTraceAndSlog.traceBegin("StartWifi");
                                this.mSystemServiceManager.startServiceFromJar(WIFI_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartWifiScanning");
                                this.mSystemServiceManager.startServiceFromJar(WIFI_SCANNING_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (hasSystemFeature5) {
                                timingsTraceAndSlog.traceBegin("StartArcNetworking");
                                this.mSystemServiceManager.startService(ARC_NETWORK_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                                timingsTraceAndSlog.traceBegin("StartRttService");
                                this.mSystemServiceManager.startServiceFromJar(WIFI_RTT_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                                timingsTraceAndSlog.traceBegin("StartWifiAware");
                                this.mSystemServiceManager.startServiceFromJar(WIFI_AWARE_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                                timingsTraceAndSlog.traceBegin("StartWifiP2P");
                                this.mSystemServiceManager.startServiceFromJar(WIFI_P2P_SERVICE_CLASS, WIFI_APEX_SERVICE_JAR_PATH);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                                timingsTraceAndSlog.traceBegin("StartLowpan");
                                this.mSystemServiceManager.startService(LOWPAN_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartPacProxyService");
                            try {
                                android.os.ServiceManager.addService("pac_proxy", new com.android.server.connectivity.PacProxyService(context));
                            } catch (java.lang.Throwable th12) {
                                reportWtf("starting PacProxyService", th12);
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartConnectivityService");
                            this.mSystemServiceManager.startServiceFromJar(CONNECTIVITY_SERVICE_INITIALIZER_CLASS, CONNECTIVITY_SERVICE_APEX_PATH);
                            networkPolicyManagerService.bindConnectivityManager();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSecurityStateManagerService");
                            android.os.ServiceManager.addService("security_state", new com.android.server.SecurityStateManagerService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                            android.net.IVpnManager.Stub create22 = com.android.server.VpnManagerService.create(context);
                            android.os.ServiceManager.addService("vpn_management", create22);
                            com.android.server.VpnManagerService vpnManagerService22 = create22;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                            try {
                                create = com.android.server.VcnManagementService.create(context);
                            } catch (java.lang.Throwable th13) {
                                th = th13;
                                vcnManagementService = null;
                            }
                            try {
                                android.os.ServiceManager.addService("vcn_management", create);
                                networkPolicyManagerService2 = networkPolicyManagerService;
                                vcnManagementService2 = create;
                            } catch (java.lang.Throwable th14) {
                                th = th14;
                                vcnManagementService = create;
                                networkPolicyManagerService2 = networkPolicyManagerService;
                                reportWtf("starting VCN Management Service", th);
                                vcnManagementService2 = vcnManagementService;
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                                android.os.ServiceManager.addService("system_update", new com.android.server.SystemUpdateManagerService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                                android.os.ServiceManager.addService("updatelock", new com.android.server.UpdateLockService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartNotificationManager");
                                this.mSystemServiceManager.startService(com.android.server.notification.NotificationManagerService.class);
                                com.android.internal.notification.SystemNotificationChannels.removeDeprecated(context);
                                com.android.internal.notification.SystemNotificationChannels.createAll(context);
                                android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                                this.mSystemServiceManager.startService(com.android.server.storage.DeviceStorageMonitorService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                                this.mSystemServiceManager.startService(TIME_DETECTOR_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                                this.mSystemServiceManager.startService(com.android.server.location.LocationManagerService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                                android.location.ICountryDetector.Stub countryDetectorService22 = new com.android.server.CountryDetectorService(context);
                                android.os.ServiceManager.addService("country_detector", countryDetectorService22);
                                com.android.server.CountryDetectorService countryDetectorService32 = countryDetectorService22;
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                                countryDetectorService = countryDetectorService32;
                                this.mSystemServiceManager.startService(TIME_ZONE_DETECTOR_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAltitudeService");
                                this.mSystemServiceManager.startService(com.android.server.location.altitude.AltitudeService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                                this.mSystemServiceManager.startService(LOCATION_TIME_ZONE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (context.getResources().getBoolean(android.R.bool.config_enableGeocoderOverlay)) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (!context.getResources().getBoolean(android.R.bool.config_enableTelephonyTimeZoneDetection)) {
                                }
                                if (deviceHasConfigString(context, android.R.string.config_defaultSearchSelectorPackageName)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAudioService");
                                if (hasSystemFeature5) {
                                }
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                                this.mSystemServiceManager.startService(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                                }
                                if (!hasSystemFeature6) {
                                }
                                if (hasSystemFeature4) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAdbService");
                                this.mSystemServiceManager.startService(ADB_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartUsbService");
                                this.mSystemServiceManager.startService(USB_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                                android.os.ServiceManager.addService("hardware_properties", new com.android.server.HardwarePropertiesManagerService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartColorDisplay");
                                this.mSystemServiceManager.startService(com.android.server.display.color.ColorDisplayService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartJobScheduler");
                                this.mSystemServiceManager.startService(JOB_SCHEDULER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                                this.mSystemServiceManager.startService(com.android.server.soundtrigger.SoundTriggerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartTrustManager");
                                this.mSystemServiceManager.startService(com.android.server.trust.TrustManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                                this.mSystemServiceManager.startService(APPWIDGET_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                                this.mSystemServiceManager.startService(VOICE_RECOGNITION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (com.android.server.GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                                }
                                timingsTraceAndSlog.traceBegin("StartSensorNotification");
                                this.mSystemServiceManager.startService(com.android.server.SensorNotificationService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                                android.os.ServiceManager.addService("diskstats", new com.android.server.DiskStatsService(context));
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("RuntimeService");
                                android.os.ServiceManager.addService("runtime", new com.android.server.RuntimeService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (hasSystemFeature4) {
                                }
                                networkTimeUpdateService = null;
                                timingsTraceAndSlog.traceBegin("CertBlacklister");
                                new com.android.server.CertBlacklister(context);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                                this.mSystemServiceManager.startService(com.android.server.emergency.EmergencyAffordanceService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin(START_BLOB_STORE_SERVICE);
                                this.mSystemServiceManager.startService(BLOB_STORE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartDreamManager");
                                this.mSystemServiceManager.startService(com.android.server.dreams.DreamManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                                android.os.ServiceManager.addService("graphicsstats", new android.graphics.GraphicsStatsService(context));
                                timingsTraceAndSlog.traceEnd();
                                if (com.android.server.coverage.CoverageService.ENABLED) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                                this.mSystemServiceManager.startService(com.android.server.security.AttestationVerificationManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                                }
                                if (context.getResources().getBoolean(android.R.bool.config_enableStylusPointerIcon)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                                this.mSystemServiceManager.startService(com.android.server.restrictions.RestrictionsManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                                this.mSystemServiceManager.startService(MEDIA_SESSION_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.interactive.TvInteractiveAppManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartTvInputManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.TvInputManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                                android.media.IMediaRouterService.Stub mediaRouterService22 = new com.android.server.media.MediaRouterService(context);
                                android.os.ServiceManager.addService("media_router", mediaRouterService22);
                                com.android.server.media.MediaRouterService mediaRouterService32 = mediaRouterService22;
                                timingsTraceAndSlog.traceEnd();
                                hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                                hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                                hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                                if (hasSystemFeature) {
                                }
                                if (hasSystemFeature2) {
                                }
                                if (hasSystemFeature3) {
                                }
                                timingsTraceAndSlog.traceBegin("StartBiometricService");
                                this.mSystemServiceManager.startService(com.android.server.biometrics.BiometricService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAuthService");
                                this.mSystemServiceManager.startService(com.android.server.biometrics.AuthService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (android.adaptiveauth.Flags.enableAdaptiveAuth()) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                if (!hasSystemFeature4) {
                                }
                                timingsTraceAndSlog.traceBegin("StartSelinuxAuditLogsService");
                                com.android.server.selinux.SelinuxAuditLogsService.schedule(context);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                                this.mSystemServiceManager.startService(com.android.server.pm.ShortcutService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                                this.mSystemServiceManager.startService(com.android.server.pm.LauncherAppsService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                                this.mSystemServiceManager.startService(com.android.server.pm.CrossProfileAppsService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartPeopleService");
                                this.mSystemServiceManager.startService(com.android.server.people.PeopleService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                                this.mSystemServiceManager.startService(com.android.server.media.metrics.MediaMetricsManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                                this.mSystemServiceManager.startService(com.android.server.pm.BackgroundInstallControlService.class);
                                timingsTraceAndSlog.traceEnd();
                                mediaRouterService = mediaRouterService32;
                                vpnManagerService = vpnManagerService22;
                                iLockSettings3 = iLockSettings2;
                                lifecycle2 = lifecycle;
                                networkTimeUpdateService2 = networkTimeUpdateService;
                                vcnManagementService3 = vcnManagementService2;
                                timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                                this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                                if (hasSystemFeature4) {
                                }
                                if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                                }
                                if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                                }
                                timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                                this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                                this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                                this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                                this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                                this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                                this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                                this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                                this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                                if (android.server.Flags.telemetryApisService()) {
                                }
                                if (detectSafeMode) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                                }
                                if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                                }
                                if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                                }
                                timingsTraceAndSlog.traceBegin("StartClipboardService");
                                this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("AppServiceManager");
                                this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                                this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                                timingsTraceAndSlog.traceEnd();
                                java.lang.String string222 = context.getResources().getString(1057554451);
                                java.lang.reflect.Constructor<?> declaredConstructor22 = java.lang.Class.forName(string222).getDeclaredConstructor(android.content.Context.class);
                                declaredConstructor22.setAccessible(true);
                                java.lang.Object newInstance22 = declaredConstructor22.newInstance(this.mSystemContext);
                                com.android.server.input.InputManagerService inputManagerService222 = inputManagerService;
                                java.lang.reflect.Method declaredMethod22 = newInstance22.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                                declaredMethod22.setAccessible(true);
                                declaredMethod22.invoke(newInstance22, new java.lang.Object[0]);
                                timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                                if (iLockSettings3 != null) {
                                }
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                                timingsTraceAndSlog.traceEnd();
                                createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                                if (createInstance != null) {
                                }
                                if (android.os.UserManager.isCommunalProfileEnabled()) {
                                }
                                timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                                main.systemReady();
                                timingsTraceAndSlog.traceEnd();
                                timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                                com.android.server.LogMteState.register(context);
                                timingsTraceAndSlog.traceEnd();
                                synchronized (com.android.server.SystemService.class) {
                                }
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                            android.os.ServiceManager.addService("system_update", new com.android.server.SystemUpdateManagerService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                            android.os.ServiceManager.addService("updatelock", new com.android.server.UpdateLockService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNotificationManager");
                            this.mSystemServiceManager.startService(com.android.server.notification.NotificationManagerService.class);
                            com.android.internal.notification.SystemNotificationChannels.removeDeprecated(context);
                            com.android.internal.notification.SystemNotificationChannels.createAll(context);
                            android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                            this.mSystemServiceManager.startService(com.android.server.storage.DeviceStorageMonitorService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                            this.mSystemServiceManager.startService(TIME_DETECTOR_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                            this.mSystemServiceManager.startService(com.android.server.location.LocationManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                            android.location.ICountryDetector.Stub countryDetectorService222 = new com.android.server.CountryDetectorService(context);
                            android.os.ServiceManager.addService("country_detector", countryDetectorService222);
                            com.android.server.CountryDetectorService countryDetectorService322 = countryDetectorService222;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                            countryDetectorService = countryDetectorService322;
                            this.mSystemServiceManager.startService(TIME_ZONE_DETECTOR_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAltitudeService");
                            try {
                                this.mSystemServiceManager.startService(com.android.server.location.altitude.AltitudeService.Lifecycle.class);
                            } catch (java.lang.Throwable th15) {
                                reportWtf("starting AltitudeService service", th15);
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                            this.mSystemServiceManager.startService(LOCATION_TIME_ZONE_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (context.getResources().getBoolean(android.R.bool.config_enableGeocoderOverlay)) {
                                timingsTraceAndSlog.traceBegin("StartGnssTimeUpdateService");
                                try {
                                    this.mSystemServiceManager.startService(GNSS_TIME_UPDATE_SERVICE_CLASS);
                                } catch (java.lang.Throwable th16) {
                                    reportWtf("starting GnssTimeUpdateService service", th16);
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartSearchManagerService");
                                try {
                                    this.mSystemServiceManager.startService(SEARCH_MANAGER_SERVICE_CLASS);
                                } catch (java.lang.Throwable th17) {
                                    reportWtf("starting Search Service", th17);
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!context.getResources().getBoolean(android.R.bool.config_enableTelephonyTimeZoneDetection)) {
                                timingsTraceAndSlog.traceBegin("StartWallpaperManagerService");
                                this.mSystemServiceManager.startService(WALLPAPER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                android.util.Slog.i(TAG, "Wallpaper service disabled by config");
                            }
                            if (deviceHasConfigString(context, android.R.string.config_defaultSearchSelectorPackageName)) {
                                timingsTraceAndSlog.traceBegin("StartWallpaperEffectsGenerationService");
                                this.mSystemServiceManager.startService(WALLPAPER_EFFECTS_GENERATION_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartAudioService");
                            if (hasSystemFeature5) {
                                this.mSystemServiceManager.startService(com.android.server.audio.AudioService.Lifecycle.class);
                                iNetworkManagementService = iNetworkManagementService22;
                            } else {
                                java.lang.String string3 = context.getResources().getString(android.R.string.config_defaultTrustAgent);
                                try {
                                    systemServiceManager = this.mSystemServiceManager;
                                    sb = new java.lang.StringBuilder();
                                    sb.append(string3);
                                    iNetworkManagementService = iNetworkManagementService22;
                                } catch (java.lang.Throwable th18) {
                                    th = th18;
                                    iNetworkManagementService = iNetworkManagementService22;
                                }
                                try {
                                    sb.append("$Lifecycle");
                                    systemServiceManager.startService(sb.toString());
                                } catch (java.lang.Throwable th19) {
                                    th = th19;
                                    reportWtf("starting " + string3, th);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                                    this.mSystemServiceManager.startService(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                                    }
                                    if (!hasSystemFeature6) {
                                    }
                                    if (hasSystemFeature4) {
                                    }
                                    if (!hasSystemFeature4) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartAdbService");
                                    this.mSystemServiceManager.startService(ADB_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartUsbService");
                                    this.mSystemServiceManager.startService(USB_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (!hasSystemFeature4) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                                    android.os.ServiceManager.addService("hardware_properties", new com.android.server.HardwarePropertiesManagerService(context));
                                    timingsTraceAndSlog.traceEnd();
                                    if (!hasSystemFeature4) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartColorDisplay");
                                    this.mSystemServiceManager.startService(com.android.server.display.color.ColorDisplayService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartJobScheduler");
                                    this.mSystemServiceManager.startService(JOB_SCHEDULER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                                    this.mSystemServiceManager.startService(com.android.server.soundtrigger.SoundTriggerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartTrustManager");
                                    this.mSystemServiceManager.startService(com.android.server.trust.TrustManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                                    this.mSystemServiceManager.startService(APPWIDGET_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                                    this.mSystemServiceManager.startService(VOICE_RECOGNITION_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (com.android.server.GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartSensorNotification");
                                    this.mSystemServiceManager.startService(com.android.server.SensorNotificationService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                                    android.os.ServiceManager.addService("diskstats", new com.android.server.DiskStatsService(context));
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("RuntimeService");
                                    android.os.ServiceManager.addService("runtime", new com.android.server.RuntimeService(context));
                                    timingsTraceAndSlog.traceEnd();
                                    if (hasSystemFeature4) {
                                    }
                                    networkTimeUpdateService = null;
                                    timingsTraceAndSlog.traceBegin("CertBlacklister");
                                    new com.android.server.CertBlacklister(context);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                                    this.mSystemServiceManager.startService(com.android.server.emergency.EmergencyAffordanceService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin(START_BLOB_STORE_SERVICE);
                                    this.mSystemServiceManager.startService(BLOB_STORE_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartDreamManager");
                                    this.mSystemServiceManager.startService(com.android.server.dreams.DreamManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                                    android.os.ServiceManager.addService("graphicsstats", new android.graphics.GraphicsStatsService(context));
                                    timingsTraceAndSlog.traceEnd();
                                    if (com.android.server.coverage.CoverageService.ENABLED) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                                    this.mSystemServiceManager.startService(com.android.server.security.AttestationVerificationManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                                    }
                                    if (context.getResources().getBoolean(android.R.bool.config_enableStylusPointerIcon)) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                                    this.mSystemServiceManager.startService(com.android.server.restrictions.RestrictionsManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                                    this.mSystemServiceManager.startService(MEDIA_SESSION_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                                    this.mSystemServiceManager.startService(com.android.server.tv.interactive.TvInteractiveAppManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartTvInputManager");
                                    this.mSystemServiceManager.startService(com.android.server.tv.TvInputManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                                    android.media.IMediaRouterService.Stub mediaRouterService222 = new com.android.server.media.MediaRouterService(context);
                                    android.os.ServiceManager.addService("media_router", mediaRouterService222);
                                    com.android.server.media.MediaRouterService mediaRouterService322 = mediaRouterService222;
                                    timingsTraceAndSlog.traceEnd();
                                    hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                                    hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                                    hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                                    if (hasSystemFeature) {
                                    }
                                    if (hasSystemFeature2) {
                                    }
                                    if (hasSystemFeature3) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartBiometricService");
                                    this.mSystemServiceManager.startService(com.android.server.biometrics.BiometricService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAuthService");
                                    this.mSystemServiceManager.startService(com.android.server.biometrics.AuthService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (android.adaptiveauth.Flags.enableAdaptiveAuth()) {
                                    }
                                    if (!hasSystemFeature4) {
                                    }
                                    if (!hasSystemFeature4) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartSelinuxAuditLogsService");
                                    com.android.server.selinux.SelinuxAuditLogsService.schedule(context);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                                    this.mSystemServiceManager.startService(com.android.server.pm.ShortcutService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.LauncherAppsService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.CrossProfileAppsService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartPeopleService");
                                    this.mSystemServiceManager.startService(com.android.server.people.PeopleService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                                    this.mSystemServiceManager.startService(com.android.server.media.metrics.MediaMetricsManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.BackgroundInstallControlService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    mediaRouterService = mediaRouterService322;
                                    vpnManagerService = vpnManagerService22;
                                    iLockSettings3 = iLockSettings2;
                                    lifecycle2 = lifecycle;
                                    networkTimeUpdateService2 = networkTimeUpdateService;
                                    vcnManagementService3 = vcnManagementService2;
                                    timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                                    this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (hasSystemFeature4) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                                    }
                                    if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                                    this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                                    this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                                    this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                                    this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                                    this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                                    this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                                    this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                                    this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (android.server.Flags.telemetryApisService()) {
                                    }
                                    if (detectSafeMode) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                                    }
                                    if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartClipboardService");
                                    this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("AppServiceManager");
                                    this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                                    this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                                    timingsTraceAndSlog.traceEnd();
                                    java.lang.String string2222 = context.getResources().getString(1057554451);
                                    java.lang.reflect.Constructor<?> declaredConstructor222 = java.lang.Class.forName(string2222).getDeclaredConstructor(android.content.Context.class);
                                    declaredConstructor222.setAccessible(true);
                                    java.lang.Object newInstance222 = declaredConstructor222.newInstance(this.mSystemContext);
                                    com.android.server.input.InputManagerService inputManagerService2222 = inputManagerService;
                                    java.lang.reflect.Method declaredMethod222 = newInstance222.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                                    declaredMethod222.setAccessible(true);
                                    declaredMethod222.invoke(newInstance222, new java.lang.Object[0]);
                                    timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                                    if (iLockSettings3 != null) {
                                    }
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                                    timingsTraceAndSlog.traceEnd();
                                    createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                                    if (createInstance != null) {
                                    }
                                    if (android.os.UserManager.isCommunalProfileEnabled()) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                                    main.systemReady();
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                                    com.android.server.LogMteState.register(context);
                                    timingsTraceAndSlog.traceEnd();
                                    synchronized (com.android.server.SystemService.class) {
                                    }
                                }
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                            this.mSystemServiceManager.startService(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                                timingsTraceAndSlog.traceBegin("StartBroadcastRadioService");
                                this.mSystemServiceManager.startService(com.android.server.broadcastradio.BroadcastRadioService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature6) {
                                timingsTraceAndSlog.traceBegin("StartDockObserver");
                                this.mSystemServiceManager.startService(com.android.server.DockObserver.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartThermalObserver");
                                this.mSystemServiceManager.startService(THERMAL_OBSERVER_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartWiredAccessoryManager");
                                try {
                                    inputManagerService.setWiredAccessoryCallbacks(new com.android.server.WiredAccessoryManager(context, inputManagerService));
                                } catch (java.lang.Throwable th20) {
                                    reportWtf("starting WiredAccessoryManager", th20);
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                                timingsTraceAndSlog.traceBegin("StartMidiManager");
                                this.mSystemServiceManager.startService(MIDI_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartAdbService");
                            this.mSystemServiceManager.startService(ADB_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host") || this.mPackageManager.hasSystemFeature("android.hardware.usb.accessory") || android.os.Build.IS_EMULATOR) {
                                timingsTraceAndSlog.traceBegin("StartUsbService");
                                this.mSystemServiceManager.startService(USB_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartSerialService");
                                this.mSystemServiceManager.startService(com.android.server.SerialService.Lifecycle.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                            android.os.ServiceManager.addService("hardware_properties", new com.android.server.HardwarePropertiesManagerService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartTwilightService");
                                this.mSystemServiceManager.startService(com.android.server.twilight.TwilightService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartColorDisplay");
                            this.mSystemServiceManager.startService(com.android.server.display.color.ColorDisplayService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartJobScheduler");
                            this.mSystemServiceManager.startService(JOB_SCHEDULER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                            this.mSystemServiceManager.startService(com.android.server.soundtrigger.SoundTriggerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTrustManager");
                            this.mSystemServiceManager.startService(com.android.server.trust.TrustManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                                timingsTraceAndSlog.traceBegin("StartBackupManager");
                                this.mSystemServiceManager.startService(BACKUP_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets") || context.getResources().getBoolean(android.R.bool.config_enableAppCloningBuildingBlocks)) {
                                timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                                this.mSystemServiceManager.startService(APPWIDGET_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                            this.mSystemServiceManager.startService(VOICE_RECOGNITION_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (com.android.server.GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                                timingsTraceAndSlog.traceBegin("StartGestureLauncher");
                                this.mSystemServiceManager.startService(com.android.server.GestureLauncherService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartSensorNotification");
                            this.mSystemServiceManager.startService(com.android.server.SensorNotificationService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                                timingsTraceAndSlog.traceBegin("StartContextHubSystemService");
                                this.mSystemServiceManager.startService(com.android.server.ContextHubSystemService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                            android.os.ServiceManager.addService("diskstats", new com.android.server.DiskStatsService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("RuntimeService");
                            android.os.ServiceManager.addService("runtime", new com.android.server.RuntimeService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (hasSystemFeature4 && !z2) {
                                timingsTraceAndSlog.traceBegin("StartNetworkTimeUpdateService");
                                try {
                                    networkTimeUpdateService = new com.android.server.timedetector.NetworkTimeUpdateService(context);
                                } catch (java.lang.Throwable th21) {
                                    th2 = th21;
                                    networkTimeUpdateService = null;
                                }
                                try {
                                    android.os.ServiceManager.addService("network_time_update_service", networkTimeUpdateService);
                                } catch (java.lang.Throwable th22) {
                                    th2 = th22;
                                    reportWtf("starting NetworkTimeUpdate service", th2);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("CertBlacklister");
                                    new com.android.server.CertBlacklister(context);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                                    this.mSystemServiceManager.startService(com.android.server.emergency.EmergencyAffordanceService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin(START_BLOB_STORE_SERVICE);
                                    this.mSystemServiceManager.startService(BLOB_STORE_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartDreamManager");
                                    this.mSystemServiceManager.startService(com.android.server.dreams.DreamManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                                    android.os.ServiceManager.addService("graphicsstats", new android.graphics.GraphicsStatsService(context));
                                    timingsTraceAndSlog.traceEnd();
                                    if (com.android.server.coverage.CoverageService.ENABLED) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                                    this.mSystemServiceManager.startService(com.android.server.security.AttestationVerificationManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                                    }
                                    if (context.getResources().getBoolean(android.R.bool.config_enableStylusPointerIcon)) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                                    this.mSystemServiceManager.startService(com.android.server.restrictions.RestrictionsManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                                    this.mSystemServiceManager.startService(MEDIA_SESSION_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                                    this.mSystemServiceManager.startService(com.android.server.tv.interactive.TvInteractiveAppManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartTvInputManager");
                                    this.mSystemServiceManager.startService(com.android.server.tv.TvInputManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                                    android.media.IMediaRouterService.Stub mediaRouterService2222 = new com.android.server.media.MediaRouterService(context);
                                    android.os.ServiceManager.addService("media_router", mediaRouterService2222);
                                    com.android.server.media.MediaRouterService mediaRouterService3222 = mediaRouterService2222;
                                    timingsTraceAndSlog.traceEnd();
                                    hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                                    hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                                    hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                                    if (hasSystemFeature) {
                                    }
                                    if (hasSystemFeature2) {
                                    }
                                    if (hasSystemFeature3) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartBiometricService");
                                    this.mSystemServiceManager.startService(com.android.server.biometrics.BiometricService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAuthService");
                                    this.mSystemServiceManager.startService(com.android.server.biometrics.AuthService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (android.adaptiveauth.Flags.enableAdaptiveAuth()) {
                                    }
                                    if (!hasSystemFeature4) {
                                    }
                                    if (!hasSystemFeature4) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartSelinuxAuditLogsService");
                                    com.android.server.selinux.SelinuxAuditLogsService.schedule(context);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                                    this.mSystemServiceManager.startService(com.android.server.pm.ShortcutService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.LauncherAppsService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.CrossProfileAppsService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartPeopleService");
                                    this.mSystemServiceManager.startService(com.android.server.people.PeopleService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                                    this.mSystemServiceManager.startService(com.android.server.media.metrics.MediaMetricsManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                                    this.mSystemServiceManager.startService(com.android.server.pm.BackgroundInstallControlService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    mediaRouterService = mediaRouterService3222;
                                    vpnManagerService = vpnManagerService22;
                                    iLockSettings3 = iLockSettings2;
                                    lifecycle2 = lifecycle;
                                    networkTimeUpdateService2 = networkTimeUpdateService;
                                    vcnManagementService3 = vcnManagementService2;
                                    timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                                    this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    if (hasSystemFeature4) {
                                    }
                                    if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                                    }
                                    if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                                    this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                                    this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                                    this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                                    this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                                    this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                                    this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                                    this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                                    this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                    if (android.server.Flags.telemetryApisService()) {
                                    }
                                    if (detectSafeMode) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                                    }
                                    if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                                    }
                                    if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartClipboardService");
                                    this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("AppServiceManager");
                                    this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                                    this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                                    timingsTraceAndSlog.traceEnd();
                                    java.lang.String string22222 = context.getResources().getString(1057554451);
                                    java.lang.reflect.Constructor<?> declaredConstructor2222 = java.lang.Class.forName(string22222).getDeclaredConstructor(android.content.Context.class);
                                    declaredConstructor2222.setAccessible(true);
                                    java.lang.Object newInstance2222 = declaredConstructor2222.newInstance(this.mSystemContext);
                                    com.android.server.input.InputManagerService inputManagerService22222 = inputManagerService;
                                    java.lang.reflect.Method declaredMethod2222 = newInstance2222.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                                    declaredMethod2222.setAccessible(true);
                                    declaredMethod2222.invoke(newInstance2222, new java.lang.Object[0]);
                                    timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                                    if (iLockSettings3 != null) {
                                    }
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                                    timingsTraceAndSlog.traceEnd();
                                    createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                                    if (createInstance != null) {
                                    }
                                    if (android.os.UserManager.isCommunalProfileEnabled()) {
                                    }
                                    timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                                    main.systemReady();
                                    timingsTraceAndSlog.traceEnd();
                                    timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                                    com.android.server.LogMteState.register(context);
                                    timingsTraceAndSlog.traceEnd();
                                    synchronized (com.android.server.SystemService.class) {
                                    }
                                }
                                timingsTraceAndSlog.traceEnd();
                            } else {
                                networkTimeUpdateService = null;
                            }
                            timingsTraceAndSlog.traceBegin("CertBlacklister");
                            new com.android.server.CertBlacklister(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                            this.mSystemServiceManager.startService(com.android.server.emergency.EmergencyAffordanceService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin(START_BLOB_STORE_SERVICE);
                            this.mSystemServiceManager.startService(BLOB_STORE_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDreamManager");
                            this.mSystemServiceManager.startService(com.android.server.dreams.DreamManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                            android.os.ServiceManager.addService("graphicsstats", new android.graphics.GraphicsStatsService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (com.android.server.coverage.CoverageService.ENABLED) {
                                timingsTraceAndSlog.traceBegin("AddCoverageService");
                                android.os.ServiceManager.addService(com.android.server.coverage.CoverageService.COVERAGE_SERVICE, new com.android.server.coverage.CoverageService());
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                                timingsTraceAndSlog.traceBegin("StartPrintManager");
                                this.mSystemServiceManager.startService(PRINT_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                            this.mSystemServiceManager.startService(com.android.server.security.AttestationVerificationManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                                timingsTraceAndSlog.traceBegin("StartCompanionDeviceManager");
                                this.mSystemServiceManager.startService(COMPANION_DEVICE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (context.getResources().getBoolean(android.R.bool.config_enableStylusPointerIcon)) {
                                timingsTraceAndSlog.traceBegin("StartVirtualDeviceManager");
                                this.mSystemServiceManager.startService(VIRTUAL_DEVICE_MANAGER_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                            this.mSystemServiceManager.startService(com.android.server.restrictions.RestrictionsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                            this.mSystemServiceManager.startService(MEDIA_SESSION_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                                timingsTraceAndSlog.traceBegin("StartHdmiControlService");
                                this.mSystemServiceManager.startService(com.android.server.hdmi.HdmiControlService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv") || this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.interactive.TvInteractiveAppManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv") || this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                timingsTraceAndSlog.traceBegin("StartTvInputManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.TvInputManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                                timingsTraceAndSlog.traceBegin("StartTunerResourceManager");
                                this.mSystemServiceManager.startService(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                                timingsTraceAndSlog.traceBegin("StartMediaResourceMonitor");
                                this.mSystemServiceManager.startService(MEDIA_RESOURCE_MONITOR_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                                timingsTraceAndSlog.traceBegin("StartTvRemoteService");
                                this.mSystemServiceManager.startService(com.android.server.tv.TvRemoteService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                            android.media.IMediaRouterService.Stub mediaRouterService22222 = new com.android.server.media.MediaRouterService(context);
                            android.os.ServiceManager.addService("media_router", mediaRouterService22222);
                            com.android.server.media.MediaRouterService mediaRouterService32222 = mediaRouterService22222;
                            timingsTraceAndSlog.traceEnd();
                            hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                            hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                            hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                            if (hasSystemFeature) {
                                timingsTraceAndSlog.traceBegin("StartFaceSensor");
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (hasSystemFeature2) {
                                timingsTraceAndSlog.traceBegin("StartIrisSensor");
                                this.mSystemServiceManager.startService(com.android.server.biometrics.sensors.iris.IrisService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (hasSystemFeature3) {
                                timingsTraceAndSlog.traceBegin("StartFingerprintSensor");
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartBiometricService");
                            this.mSystemServiceManager.startService(com.android.server.biometrics.BiometricService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAuthService");
                            this.mSystemServiceManager.startService(com.android.server.biometrics.AuthService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (android.adaptiveauth.Flags.enableAdaptiveAuth()) {
                                timingsTraceAndSlog.traceBegin("StartAdaptiveAuthService");
                                this.mSystemServiceManager.startService(com.android.server.adaptiveauth.AdaptiveAuthService.class);
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartDynamicCodeLoggingService");
                                try {
                                    com.android.server.pm.DynamicCodeLoggingService.schedule(context);
                                } catch (java.lang.Throwable th23) {
                                    reportWtf("starting DynamicCodeLoggingService", th23);
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            if (!hasSystemFeature4) {
                                timingsTraceAndSlog.traceBegin("StartPruneInstantAppsJobService");
                                try {
                                    com.android.server.PruneInstantAppsJobService.schedule(context);
                                } catch (java.lang.Throwable th24) {
                                    reportWtf("StartPruneInstantAppsJobService", th24);
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartSelinuxAuditLogsService");
                            try {
                                com.android.server.selinux.SelinuxAuditLogsService.schedule(context);
                            } catch (java.lang.Throwable th25) {
                                reportWtf("starting SelinuxAuditLogsService", th25);
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                            this.mSystemServiceManager.startService(com.android.server.pm.ShortcutService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                            this.mSystemServiceManager.startService(com.android.server.pm.LauncherAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                            this.mSystemServiceManager.startService(com.android.server.pm.CrossProfileAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPeopleService");
                            this.mSystemServiceManager.startService(com.android.server.people.PeopleService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                            this.mSystemServiceManager.startService(com.android.server.media.metrics.MediaMetricsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                            this.mSystemServiceManager.startService(com.android.server.pm.BackgroundInstallControlService.class);
                            timingsTraceAndSlog.traceEnd();
                            mediaRouterService = mediaRouterService32222;
                            vpnManagerService = vpnManagerService22;
                            iLockSettings3 = iLockSettings2;
                            lifecycle2 = lifecycle;
                            networkTimeUpdateService2 = networkTimeUpdateService;
                            vcnManagementService3 = vcnManagementService2;
                        }
                        timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                        this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (hasSystemFeature4) {
                            timingsTraceAndSlog.traceBegin("StartWearPowerService");
                            this.mSystemServiceManager.startService(WEAR_POWER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartHealthService");
                            this.mSystemServiceManager.startService(HEALTH_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSystemStateDisplayService");
                            this.mSystemServiceManager.startService(SYSTEM_STATE_DISPLAY_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartWearConnectivityService");
                            this.mSystemServiceManager.startService(WEAR_CONNECTIVITY_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartWearDisplayService");
                            this.mSystemServiceManager.startService(WEAR_DISPLAY_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (android.os.Build.IS_DEBUGGABLE) {
                                timingsTraceAndSlog.traceBegin("StartWearDebugService");
                                this.mSystemServiceManager.startService(WEAR_DEBUG_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartWearTimeService");
                            this.mSystemServiceManager.startService(WEAR_TIME_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartWearSettingsService");
                            this.mSystemServiceManager.startService(WEAR_SETTINGS_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartWearModeService");
                            this.mSystemServiceManager.startService(WEAR_MODE_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                            if (android.os.SystemProperties.getBoolean("config.enable_wristorientation", false)) {
                                timingsTraceAndSlog.traceBegin("StartWristOrientationService");
                                this.mSystemServiceManager.startService(WRIST_ORIENTATION_SERVICE_CLASS);
                                timingsTraceAndSlog.traceEnd();
                            }
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                            timingsTraceAndSlog.traceBegin("StartSliceManagerService");
                            this.mSystemServiceManager.startService(SLICE_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                            timingsTraceAndSlog.traceBegin("StartIoTSystemService");
                            this.mSystemServiceManager.startService(IOT_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                        this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                        this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                        this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                        this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                        this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                        this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                        this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                        this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        if (android.server.Flags.telemetryApisService()) {
                            timingsTraceAndSlog.traceBegin("StartProfilingCompanion");
                            this.mSystemServiceManager.startServiceFromJar(PROFILING_SERVICE_LIFECYCLE_CLASS, PROFILING_SERVICE_JAR_PATH);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (detectSafeMode) {
                            this.mActivityManagerService.enterSafeMode();
                        }
                        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                            mmsServiceBroker = null;
                        } else {
                            timingsTraceAndSlog.traceBegin("StartMmsService");
                            com.android.server.MmsServiceBroker mmsServiceBroker2 = (com.android.server.MmsServiceBroker) this.mSystemServiceManager.startService(com.android.server.MmsServiceBroker.class);
                            timingsTraceAndSlog.traceEnd();
                            mmsServiceBroker = mmsServiceBroker2;
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                            timingsTraceAndSlog.traceBegin("StartAutoFillService");
                            this.mSystemServiceManager.startService(AUTO_FILL_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                            if (android.provider.DeviceConfig.getBoolean("credential_manager", "enable_credential_manager", true)) {
                                if (hasSystemFeature4 && !android.credentials.flags.Flags.wearCredentialManagerEnabled()) {
                                    android.util.Slog.d(TAG, "CredentialManager disabled on wear.");
                                } else {
                                    timingsTraceAndSlog.traceBegin("StartCredentialManagerService");
                                    this.mSystemServiceManager.startService(CREDENTIAL_MANAGER_SERVICE_CLASS);
                                    timingsTraceAndSlog.traceEnd();
                                }
                            } else {
                                android.util.Slog.d(TAG, "CredentialManager disabled.");
                            }
                        }
                        if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                            timingsTraceAndSlog.traceBegin("StartTranslationManagerService");
                            this.mSystemServiceManager.startService(TRANSLATION_MANAGER_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        } else {
                            android.util.Slog.d(TAG, "TranslationService not defined by OEM");
                        }
                        timingsTraceAndSlog.traceBegin("StartClipboardService");
                        this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppServiceManager");
                        this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                        this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                        timingsTraceAndSlog.traceEnd();
                        java.lang.String string222222 = context.getResources().getString(1057554451);
                        java.lang.reflect.Constructor<?> declaredConstructor22222 = java.lang.Class.forName(string222222).getDeclaredConstructor(android.content.Context.class);
                        declaredConstructor22222.setAccessible(true);
                        java.lang.Object newInstance22222 = declaredConstructor22222.newInstance(this.mSystemContext);
                        com.android.server.input.InputManagerService inputManagerService222222 = inputManagerService;
                        java.lang.reflect.Method declaredMethod22222 = newInstance22222.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                        declaredMethod22222.setAccessible(true);
                        declaredMethod22222.invoke(newInstance22222, new java.lang.Object[0]);
                        timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                        if (iLockSettings3 != null) {
                            try {
                                iLockSettings3.systemReady();
                            } catch (java.lang.Throwable th26) {
                                reportWtf("making Lock Settings Service ready", th26);
                            }
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                        timingsTraceAndSlog.traceEnd();
                        createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                        if (createInstance != null) {
                            timingsTraceAndSlog.traceBegin("HsumBootUserInitializer.init");
                            createInstance.init(timingsTraceAndSlog);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (android.os.UserManager.isCommunalProfileEnabled()) {
                            timingsTraceAndSlog.traceBegin("CommunalProfileInitializer.init");
                            new com.android.server.CommunalProfileInitializer(this.mActivityManagerService).init(timingsTraceAndSlog);
                            timingsTraceAndSlog.traceEnd();
                        } else {
                            timingsTraceAndSlog.traceBegin("CommunalProfileInitializer.removeCommunalProfileIfPresent");
                            com.android.server.CommunalProfileInitializer.removeCommunalProfileIfPresent();
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                        main.systemReady();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                        com.android.server.LogMteState.register(context);
                        timingsTraceAndSlog.traceEnd();
                        synchronized (com.android.server.SystemService.class) {
                            try {
                                if (sPendingWtfs != null) {
                                    this.mActivityManagerService.schedulePendingSystemServerWtfs(sPendingWtfs);
                                    sPendingWtfs = null;
                                }
                            } finally {
                            }
                        }
                        if (detectSafeMode) {
                            this.mActivityManagerService.showSafeModeOverlay();
                        }
                        android.content.res.Configuration computeNewConfiguration = main.computeNewConfiguration(0);
                        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
                        context.getDisplay().getMetrics(displayMetrics);
                        context.getResources().updateConfiguration(computeNewConfiguration, displayMetrics);
                        android.content.res.Resources.Theme theme = context.getTheme();
                        if (theme.getChangingConfigurations() != 0) {
                            theme.rebase();
                        }
                        timingsTraceAndSlog.traceBegin("StartPermissionPolicyService");
                        this.mSystemServiceManager.startService(com.android.server.policy.PermissionPolicyService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakePackageManagerServiceReady");
                        this.mPackageManagerService.systemReady();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeDisplayManagerServiceReady");
                        try {
                            this.mDisplayManagerService.systemReady(detectSafeMode);
                        } catch (java.lang.Throwable th27) {
                            reportWtf("making Display Manager Service ready", th27);
                        }
                        timingsTraceAndSlog.traceEnd();
                        this.mSystemServiceManager.setSafeMode(detectSafeMode);
                        timingsTraceAndSlog.traceBegin("StartDeviceSpecificServices");
                        java.lang.String[] stringArray = this.mSystemContext.getResources().getStringArray(android.R.array.config_defaultPinnerServiceFiles);
                        int length = stringArray.length;
                        int i = 0;
                        while (i < length) {
                            java.lang.String str = stringArray[i];
                            timingsTraceAndSlog.traceBegin("StartDeviceSpecificServices " + str);
                            try {
                                this.mSystemServiceManager.startService(str);
                                strArr = stringArray;
                            } catch (java.lang.Throwable th28) {
                                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                                strArr = stringArray;
                                sb3.append("starting ");
                                sb3.append(str);
                                reportWtf(sb3.toString(), th28);
                            }
                            timingsTraceAndSlog.traceEnd();
                            i++;
                            stringArray = strArr;
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin(com.android.server.app.GameManagerService.TAG);
                        this.mSystemServiceManager.startService(GAME_MANAGER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.uwb")) {
                            timingsTraceAndSlog.traceBegin("UwbService");
                            this.mSystemServiceManager.startServiceFromJar(UWB_SERVICE_CLASS, UWB_APEX_SERVICE_JAR_PATH);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartBootPhaseDeviceSpecificServicesReady");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSafetyCenterService");
                        this.mSystemServiceManager.startService(SAFETY_CENTER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppSearchModule");
                        this.mSystemServiceManager.startService(APPSEARCH_MODULE_LIFECYCLE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        if (android.os.SystemProperties.getBoolean("ro.config.isolated_compilation_enabled", false)) {
                            timingsTraceAndSlog.traceBegin("IsolatedCompilationService");
                            this.mSystemServiceManager.startService(ISOLATED_COMPILATION_SERVICE_CLASS);
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("StartMediaCommunicationService");
                        this.mSystemServiceManager.startService(MEDIA_COMMUNICATION_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppCompatOverridesService");
                        this.mSystemServiceManager.startService(APP_COMPAT_OVERRIDES_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("HealthConnectManagerService");
                        this.mSystemServiceManager.startService(HEALTHCONNECT_MANAGER_SERVICE_CLASS);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.software.device_lock")) {
                            timingsTraceAndSlog.traceBegin("DeviceLockService");
                            this.mSystemServiceManager.startServiceFromJar(DEVICE_LOCK_SERVICE_CLASS, DEVICE_LOCK_APEX_PATH);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (android.permission.flags.Flags.sensitiveNotificationAppProtection() || android.view.flags.Flags.sensitiveContentAppProtection()) {
                            timingsTraceAndSlog.traceBegin("StartSensitiveContentProtectionManager");
                            this.mSystemServiceManager.startService(com.android.server.SensitiveContentProtectionManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                        }
                        final android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) context.getSystemService("connectivity");
                        final android.os.INetworkManagementService iNetworkManagementService3 = iNetworkManagementService;
                        final com.android.server.net.NetworkPolicyManagerService networkPolicyManagerService4 = networkPolicyManagerService2;
                        final com.android.server.input.InputManagerService inputManagerService3 = inputManagerService222222;
                        final com.android.server.VpnManagerService vpnManagerService3 = vpnManagerService;
                        final com.android.server.VcnManagementService vcnManagementService4 = vcnManagementService3;
                        final com.android.server.CountryDetectorService countryDetectorService4 = countryDetectorService;
                        final com.android.server.timedetector.NetworkTimeUpdateService networkTimeUpdateService3 = networkTimeUpdateService2;
                        final com.android.server.TelephonyRegistry telephonyRegistry3 = telephonyRegistry;
                        final com.android.server.media.MediaRouterService mediaRouterService4 = mediaRouterService;
                        final com.android.server.MmsServiceBroker mmsServiceBroker3 = mmsServiceBroker;
                        this.mActivityManagerService.systemReady(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.SystemServer.this.lambda$startOtherServices$6(timingsTraceAndSlog, lifecycle2, hasSystemFeature4, context, detectSafeMode, connectivityManager, iNetworkManagementService3, networkPolicyManagerService4, vpnManagerService3, vcnManagementService4, createInstance, countryDetectorService4, networkTimeUpdateService3, inputManagerService3, telephonyRegistry3, mediaRouterService4, mmsServiceBroker3);
                            }
                        }, timingsTraceAndSlog);
                        timingsTraceAndSlog.traceBegin("LockSettingsThirdPartyAppsStarted");
                        com.android.internal.widget.LockSettingsInternal lockSettingsInternal = (com.android.internal.widget.LockSettingsInternal) com.android.server.LocalServices.getService(com.android.internal.widget.LockSettingsInternal.class);
                        if (lockSettingsInternal != null) {
                            lockSettingsInternal.onThirdPartyAppsStarted();
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSystemUI");
                        try {
                            startSystemUi(context, main);
                        } catch (java.lang.Throwable th29) {
                            reportWtf("starting System UI", th29);
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceEnd();
                        return;
                    }
                }
                timingsTraceAndSlog.traceBegin("StartTelecomLoaderService");
                this.mSystemServiceManager.startService(com.android.server.telecom.TelecomLoaderService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartTelephonyRegistry");
                com.android.internal.telephony.ITelephonyRegistry.Stub telephonyRegistry22 = new com.android.server.TelephonyRegistry(context, new com.android.server.TelephonyRegistry.ConfigurationProvider());
                android.os.ServiceManager.addService("telephony.registry", telephonyRegistry22);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartEntropyMixer");
                this.mEntropyMixer = new com.android.server.EntropyMixer(context);
                timingsTraceAndSlog.traceEnd();
                this.mContentResolver = context.getContentResolver();
                timingsTraceAndSlog.traceBegin("StartAccountManagerService");
                this.mSystemServiceManager.startService(ACCOUNT_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartContentService");
                this.mSystemServiceManager.startService(CONTENT_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("InstallSystemProviders");
                this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
                this.mSystemServiceManager.startService(UPDATABLE_DEVICE_CONFIG_SERVICE_CLASS);
                android.database.sqlite.SQLiteCompatibilityWalFlags.reset();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartDropBoxManager");
                this.mSystemServiceManager.startService(com.android.server.DropBoxManagerService.class);
                timingsTraceAndSlog.traceEnd();
                if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                }
                timingsTraceAndSlog.traceBegin("StartRoleManagerService");
                com.android.server.LocalManagerRegistry.addManager(com.android.server.role.RoleServicePlatformHelper.class, new com.android.server.policy.role.RoleServicePlatformHelperImpl(this.mSystemContext));
                this.mSystemServiceManager.startService(ROLE_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                if (!hasSystemFeature6) {
                }
                timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
                android.os.ServiceManager.addService("dynamic_system", new com.android.server.DynamicSystemService(context));
                timingsTraceAndSlog.traceEnd();
                if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                }
                timingsTraceAndSlog.traceBegin("StartResourceEconomy");
                this.mSystemServiceManager.startService(RESOURCE_ECONOMY_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
                this.mSystemServiceManager.startService(ALARM_MANAGER_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartInputManagerService");
                ?? inputManagerService4 = new com.android.server.input.InputManagerService(context);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
                this.mSystemServiceManager.startService(com.android.server.devicestate.DeviceStateManagerService.class);
                timingsTraceAndSlog.traceEnd();
                if (!z3) {
                }
                timingsTraceAndSlog.traceBegin("StartWindowManagerService");
                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
                ?? main2 = com.android.server.wm.WindowManagerService.main(context, inputManagerService4, this.mFirstBoot, new com.android.server.policy.PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
                android.os.ServiceManager.addService("window", (android.os.IBinder) main2, false, 17);
                android.os.ServiceManager.addService("input", (android.os.IBinder) inputManagerService4, false, 1);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("SetWindowManagerService");
                this.mActivityManagerService.setWindowManager(main2);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
                main2.onInitReady();
                timingsTraceAndSlog.traceEnd();
                com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.SystemServer.lambda$startOtherServices$2();
                    }
                }, START_SENSOR_MANAGER_SERVICE);
                com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.SystemServer.lambda$startOtherServices$3();
                    }
                }, START_HIDL_SERVICES);
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartVrManagerService");
                    this.mSystemServiceManager.startService(com.android.server.vr.VrManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartInputManager");
                inputManagerService4.setWindowManagerCallbacks(main2.getInputManagerCallback());
                inputManagerService4.start();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
                this.mDisplayManagerService.windowManagerAndInputReady();
                timingsTraceAndSlog.traceEnd();
                if (this.mFactoryTestMode != 1) {
                }
                timingsTraceAndSlog.traceBegin("IpConnectivityMetrics");
                this.mSystemServiceManager.startService(IP_CONNECTIVITY_METRICS_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
                this.mSystemServiceManager.startService(com.android.server.net.watchlist.NetworkWatchlistService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("PinnerService");
                this.mSystemServiceManager.startService(com.android.server.PinnerService.class);
                timingsTraceAndSlog.traceEnd();
                if (android.os.Build.IS_DEBUGGABLE) {
                    timingsTraceAndSlog.traceBegin(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG);
                    this.mSystemServiceManager.startService(com.android.server.profcollect.ProfcollectForwardingService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("SignedConfigService");
                com.android.server.signedconfig.SignedConfigService.registerUpdateReceiver(this.mSystemContext);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("AppIntegrityService");
                this.mSystemServiceManager.startService(com.android.server.integrity.AppIntegrityManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLogcatManager");
                this.mSystemServiceManager.startService(com.android.server.logcat.LogcatManagerService.class);
                timingsTraceAndSlog.traceEnd();
                detectSafeMode = main2.detectSafeMode();
                if (!detectSafeMode) {
                }
                if (this.mFactoryTestMode != 1) {
                }
                timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                main2.displayReady();
                timingsTraceAndSlog.traceEnd();
                if (this.mFactoryTestMode != 1) {
                    timingsTraceAndSlog.traceBegin("StartStorageManagerService");
                    this.mSystemServiceManager.startService(STORAGE_MANAGER_SERVICE_CLASS);
                    android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"));
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartStorageStatsService");
                    this.mSystemServiceManager.startService(STORAGE_STATS_SERVICE_CLASS);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartUiModeManager");
                this.mSystemServiceManager.startService(com.android.server.UiModeManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                this.mSystemServiceManager.startService(com.android.server.locales.LocaleManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                this.mSystemServiceManager.startService(com.android.server.grammaticalinflection.GrammaticalInflectionService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                this.mSystemServiceManager.startService(APP_HIBERNATION_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                com.android.server.pm.DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                com.android.server.Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                this.mPackageManagerService.updatePackagesIfNeeded();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                this.mPackageManagerService.performFstrimIfNeeded();
                timingsTraceAndSlog.traceEnd();
                if (this.mFactoryTestMode != 1) {
                }
                timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                this.mSystemServiceManager.startService(com.android.server.media.projection.MediaProjectionManagerService.class);
                timingsTraceAndSlog.traceEnd();
                if (hasSystemFeature4) {
                }
                if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                }
                timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                this.mSystemServiceManager.startServiceFromJar(STATS_COMPANION_LIFECYCLE_CLASS, STATS_COMPANION_APEX_PATH);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                this.mSystemServiceManager.startServiceFromJar(REBOOT_READINESS_LIFECYCLE_CLASS, SCHEDULING_APEX_PATH);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                this.mSystemServiceManager.startService(STATS_PULL_ATOM_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                this.mSystemServiceManager.startService(STATS_BOOTSTRAP_ATOM_SERVICE_LIFECYCLE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                this.mSystemServiceManager.startService(com.android.server.incident.IncidentCompanionService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                this.mSystemServiceManager.startService(SDK_SANDBOX_MANAGER_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                this.mSystemServiceManager.startService(AD_SERVICES_MANAGER_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                this.mSystemServiceManager.startService(ON_DEVICE_PERSONALIZATION_SYSTEM_SERVICE_CLASS);
                timingsTraceAndSlog.traceEnd();
                if (android.server.Flags.telemetryApisService()) {
                }
                if (detectSafeMode) {
                }
                if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                }
                if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                }
                if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                }
                if (!deviceHasConfigString(context, android.R.string.config_defaultQrCodeComponent)) {
                }
                timingsTraceAndSlog.traceBegin("StartClipboardService");
                this.mSystemServiceManager.startService(com.android.server.clipboard.ClipboardService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("AppServiceManager");
                this.mSystemServiceManager.startService(com.android.server.appbinding.AppBindingService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                this.mSystemServiceManager.startService(com.android.server.tracing.TracingServiceProxy.class);
                timingsTraceAndSlog.traceEnd();
                java.lang.String string2222222 = context.getResources().getString(1057554451);
                java.lang.reflect.Constructor<?> declaredConstructor222222 = java.lang.Class.forName(string2222222).getDeclaredConstructor(android.content.Context.class);
                declaredConstructor222222.setAccessible(true);
                java.lang.Object newInstance222222 = declaredConstructor222222.newInstance(this.mSystemContext);
                com.android.server.input.InputManagerService inputManagerService2222222 = inputManagerService4;
                java.lang.reflect.Method declaredMethod222222 = newInstance222222.getClass().getDeclaredMethod("run", new java.lang.Class[0]);
                declaredMethod222222.setAccessible(true);
                declaredMethod222222.invoke(newInstance222222, new java.lang.Object[0]);
                timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                if (iLockSettings3 != null) {
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY);
                timingsTraceAndSlog.traceEnd();
                createInstance = com.android.server.HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(android.R.bool.config_intrusiveNotificationLed));
                if (createInstance != null) {
                }
                if (android.os.UserManager.isCommunalProfileEnabled()) {
                }
                timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                main2.systemReady();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                com.android.server.LogMteState.register(context);
                timingsTraceAndSlog.traceEnd();
                synchronized (com.android.server.SystemService.class) {
                }
            } catch (java.lang.Throwable th30) {
                android.util.Slog.e("System", "******************************************");
                android.util.Slog.e("System", "************ Failure starting core service");
                throw th30;
            }
        } else {
            throw new java.lang.RuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startOtherServices$1() {
        try {
            android.util.Slog.i(TAG, "SecondaryZygotePreload");
            com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
            newAsyncLog.traceBegin("SecondaryZygotePreload");
            java.lang.String[] strArr = android.os.Build.SUPPORTED_32_BIT_ABIS;
            if (strArr.length > 0 && !android.os.Process.ZYGOTE_PROCESS.preloadDefault(strArr[0])) {
                android.util.Slog.e(TAG, "Unable to preload default resources for secondary");
            }
            newAsyncLog.traceEnd();
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception preloading default resources", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startOtherServices$2() {
        com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin(START_SENSOR_MANAGER_SERVICE);
        startISensorManagerService();
        newAsyncLog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startOtherServices$3() {
        com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin(START_HIDL_SERVICES);
        startHidlServices();
        newAsyncLog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOtherServices$6(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle lifecycle, boolean z, android.content.Context context, boolean z2, android.net.ConnectivityManager connectivityManager, com.android.server.net.NetworkManagementService networkManagementService, com.android.server.net.NetworkPolicyManagerService networkPolicyManagerService, com.android.server.VpnManagerService vpnManagerService, com.android.server.VcnManagementService vcnManagementService, com.android.server.HsumBootUserInitializer hsumBootUserInitializer, com.android.server.CountryDetectorService countryDetectorService, com.android.server.timedetector.NetworkTimeUpdateService networkTimeUpdateService, com.android.server.input.InputManagerService inputManagerService, com.android.server.TelephonyRegistry telephonyRegistry, com.android.server.media.MediaRouterService mediaRouterService, com.android.server.MmsServiceBroker mmsServiceBroker) {
        java.util.concurrent.Future<?> future;
        android.util.Slog.i(TAG, "Making services ready");
        timingsTraceAndSlog.traceBegin("StartActivityManagerReadyPhase");
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 550);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartObservingNativeCrashes");
        try {
            this.mActivityManagerService.startObservingNativeCrashes();
        } catch (java.lang.Throwable th) {
            reportWtf("observing native crashes", th);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("RegisterAppOpsPolicy");
        try {
            this.mActivityManagerService.setAppOpsPolicy(new com.android.server.policy.AppOpsPolicy(this.mSystemContext));
        } catch (java.lang.Throwable th2) {
            reportWtf("registering app ops policy", th2);
        }
        timingsTraceAndSlog.traceEnd();
        if (this.mWebViewUpdateService == null) {
            future = null;
        } else {
            future = com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.SystemServer.this.lambda$startOtherServices$4();
                }
            }, "WebViewFactoryPreparation");
        }
        if (this.mPackageManager.hasSystemFeature("android.hardware.type.automotive")) {
            timingsTraceAndSlog.traceBegin("StartCarServiceHelperService");
            android.app.admin.DevicePolicySafetyChecker startService = this.mSystemServiceManager.startService(CAR_SERVICE_HELPER_SERVICE_CLASS);
            if (startService instanceof android.util.Dumpable) {
                this.mDumper.addDumpable((android.util.Dumpable) startService);
            }
            if (startService instanceof android.app.admin.DevicePolicySafetyChecker) {
                lifecycle.setDevicePolicySafetyChecker(startService);
            }
            timingsTraceAndSlog.traceEnd();
        }
        if (z) {
            timingsTraceAndSlog.traceBegin("StartWearService");
            java.lang.String string = context.getString(android.R.string.config_tvRemoteServicePackage);
            if (!android.text.TextUtils.isEmpty(string)) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(string);
                if (unflattenFromString != null) {
                    android.content.Intent intent = new android.content.Intent();
                    intent.setComponent(unflattenFromString);
                    intent.addFlags(256);
                    context.startServiceAsUser(intent, android.os.UserHandle.SYSTEM);
                } else {
                    android.util.Slog.d(TAG, "Null wear service component name.");
                }
            }
            timingsTraceAndSlog.traceEnd();
        }
        if (z2) {
            timingsTraceAndSlog.traceBegin("EnableAirplaneModeInSafeMode");
            try {
                connectivityManager.setAirplaneMode(true);
            } catch (java.lang.Throwable th3) {
                reportWtf("enabling Airplane Mode during Safe Mode bootup", th3);
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("MakeNetworkManagementServiceReady");
        if (networkManagementService != null) {
            try {
                networkManagementService.systemReady();
            } catch (java.lang.Throwable th4) {
                reportWtf("making Network Managment Service ready", th4);
            }
        }
        java.util.concurrent.CountDownLatch networkScoreAndNetworkManagementServiceReady = networkPolicyManagerService != null ? networkPolicyManagerService.networkScoreAndNetworkManagementServiceReady() : null;
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeConnectivityServiceReady");
        if (connectivityManager != null) {
            try {
                connectivityManager.systemReady();
            } catch (java.lang.Throwable th5) {
                reportWtf("making Connectivity Service ready", th5);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeVpnManagerServiceReady");
        if (vpnManagerService != null) {
            try {
                vpnManagerService.systemReady();
            } catch (java.lang.Throwable th6) {
                reportWtf("making VpnManagerService ready", th6);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeVcnManagementServiceReady");
        if (vcnManagementService != null) {
            try {
                vcnManagementService.systemReady();
            } catch (java.lang.Throwable th7) {
                reportWtf("making VcnManagementService ready", th7);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeNetworkPolicyServiceReady");
        if (networkPolicyManagerService != null) {
            try {
                networkPolicyManagerService.systemReady(networkScoreAndNetworkManagementServiceReady);
            } catch (java.lang.Throwable th8) {
                reportWtf("making Network Policy Service ready", th8);
            }
        }
        timingsTraceAndSlog.traceEnd();
        this.mPackageManagerService.waitForAppDataPrepared();
        timingsTraceAndSlog.traceBegin("PhaseThirdPartyAppsCanStart");
        if (future != null) {
            com.android.internal.util.ConcurrentUtils.waitForFutureNoInterrupt(future, "WebViewFactoryPreparation");
        }
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 600);
        timingsTraceAndSlog.traceEnd();
        if (hsumBootUserInitializer != null) {
            timingsTraceAndSlog.traceBegin("HsumBootUserInitializer.systemRunning");
            hsumBootUserInitializer.systemRunning(timingsTraceAndSlog);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartNetworkStack");
        try {
            android.net.NetworkStackClient.getInstance().start();
        } catch (java.lang.Throwable th9) {
            reportWtf("starting Network Stack", th9);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartTethering");
        try {
            android.net.ConnectivityModuleConnector.getInstance().startModuleService(TETHERING_CONNECTOR_CLASS, "android.permission.MAINLINE_NETWORK_STACK", new android.net.ConnectivityModuleConnector.ModuleServiceCallback() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda8
                @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
                public final void onModuleServiceConnected(android.os.IBinder iBinder) {
                    android.os.ServiceManager.addService("tethering", iBinder, false, 6);
                }
            });
        } catch (java.lang.Throwable th10) {
            reportWtf("starting Tethering", th10);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeCountryDetectionServiceReady");
        if (countryDetectorService != null) {
            try {
                countryDetectorService.systemRunning();
            } catch (java.lang.Throwable th11) {
                reportWtf("Notifying CountryDetectorService running", th11);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeNetworkTimeUpdateReady");
        if (networkTimeUpdateService != null) {
            try {
                networkTimeUpdateService.systemRunning();
            } catch (java.lang.Throwable th12) {
                reportWtf("Notifying NetworkTimeService running", th12);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeInputManagerServiceReady");
        if (inputManagerService != null) {
            try {
                inputManagerService.systemRunning();
            } catch (java.lang.Throwable th13) {
                reportWtf("Notifying InputManagerService running", th13);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeTelephonyRegistryReady");
        if (telephonyRegistry != null) {
            try {
                telephonyRegistry.systemRunning();
            } catch (java.lang.Throwable th14) {
                reportWtf("Notifying TelephonyRegistry running", th14);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeMediaRouterServiceReady");
        if (mediaRouterService != null) {
            try {
                mediaRouterService.systemRunning();
            } catch (java.lang.Throwable th15) {
                reportWtf("Notifying MediaRouterService running", th15);
            }
        }
        timingsTraceAndSlog.traceEnd();
        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
            timingsTraceAndSlog.traceBegin("MakeMmsServiceReady");
            if (mmsServiceBroker != null) {
                try {
                    mmsServiceBroker.systemRunning();
                } catch (java.lang.Throwable th16) {
                    reportWtf("Notifying MmsService running", th16);
                }
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("IncidentDaemonReady");
        try {
            android.os.IIncidentManager asInterface = android.os.IIncidentManager.Stub.asInterface(android.os.ServiceManager.getService("incident"));
            if (asInterface != null) {
                asInterface.systemRunning();
            }
        } catch (java.lang.Throwable th17) {
            reportWtf("Notifying incident daemon running", th17);
        }
        timingsTraceAndSlog.traceEnd();
        if (this.mIncrementalServiceHandle != 0) {
            timingsTraceAndSlog.traceBegin("MakeIncrementalServiceReady");
            setIncrementalServiceSystemReady(this.mIncrementalServiceHandle);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("OdsignStatsLogger");
        try {
            com.android.server.pm.dex.OdsignStatsLogger.triggerStatsWrite();
        } catch (java.lang.Throwable th18) {
            reportWtf("Triggering OdsignStatsLogger", th18);
        }
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOtherServices$4() {
        android.util.Slog.i(TAG, "WebViewFactoryPreparation");
        com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("WebViewFactoryPreparation");
        com.android.internal.util.ConcurrentUtils.waitForFutureNoInterrupt(this.mZygotePreload, "Zygote preload");
        this.mZygotePreload = null;
        this.mWebViewUpdateService.prepareWebViewInSystemServer();
        newAsyncLog.traceEnd();
    }

    private void startApexServices(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startApexServices");
        for (com.android.server.pm.ApexSystemServiceInfo apexSystemServiceInfo : com.android.server.pm.ApexManager.getInstance().getApexSystemServices()) {
            java.lang.String name = apexSystemServiceInfo.getName();
            java.lang.String jarPath = apexSystemServiceInfo.getJarPath();
            timingsTraceAndSlog.traceBegin("starting " + name);
            if (android.text.TextUtils.isEmpty(jarPath)) {
                this.mSystemServiceManager.startService(name);
            } else {
                this.mSystemServiceManager.startServiceFromJar(name, jarPath);
            }
            timingsTraceAndSlog.traceEnd();
        }
        this.mSystemServiceManager.sealStartedServices();
        timingsTraceAndSlog.traceEnd();
    }

    private void updateWatchdogTimeout(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("UpdateWatchdogTimeout");
        com.android.server.Watchdog.getInstance().registerSettingsObserver(this.mSystemContext);
        timingsTraceAndSlog.traceEnd();
    }

    private boolean deviceHasConfigString(@android.annotation.NonNull android.content.Context context, int i) {
        return !android.text.TextUtils.isEmpty(context.getString(i));
    }

    private void startSystemCaptionsManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!deviceHasConfigString(context, android.R.string.config_defaultProfcollectReportUploaderAction)) {
            android.util.Slog.d(TAG, "SystemCaptionsManagerService disabled because resource is not overlaid");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartSystemCaptionsManagerService");
        this.mSystemServiceManager.startService(SYSTEM_CAPTIONS_MANAGER_SERVICE_CLASS);
        timingsTraceAndSlog.traceEnd();
    }

    private void startTextToSpeechManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("StartTextToSpeechManagerService");
        this.mSystemServiceManager.startService(TEXT_TO_SPEECH_MANAGER_SERVICE_CLASS);
        timingsTraceAndSlog.traceEnd();
    }

    private void startContentCaptureService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        boolean z;
        java.lang.String property = android.provider.DeviceConfig.getProperty("content_capture", "service_explicitly_enabled");
        if (property != null && !property.equalsIgnoreCase("default")) {
            z = java.lang.Boolean.parseBoolean(property);
            if (z) {
                android.util.Slog.d(TAG, "ContentCaptureService explicitly enabled by DeviceConfig");
            } else {
                android.util.Slog.d(TAG, "ContentCaptureService explicitly disabled by DeviceConfig");
                return;
            }
        } else {
            z = false;
        }
        if (!z) {
            if (!deviceHasConfigString(context, android.R.string.config_defaultAppPredictionService)) {
                android.util.Slog.d(TAG, "ContentCaptureService disabled because resource is not overlaid");
                return;
            } else if (!deviceHasConfigString(context, android.R.string.config_defaultAssistantAccessComponent)) {
                android.util.Slog.d(TAG, "ContentProtectionService disabled because resource is not overlaid, ContentCaptureService still enabled");
            }
        }
        timingsTraceAndSlog.traceBegin("StartContentCaptureService");
        this.mSystemServiceManager.startService(CONTENT_CAPTURE_MANAGER_SERVICE_CLASS);
        com.android.server.contentcapture.ContentCaptureManagerInternal contentCaptureManagerInternal = (com.android.server.contentcapture.ContentCaptureManagerInternal) com.android.server.LocalServices.getService(com.android.server.contentcapture.ContentCaptureManagerInternal.class);
        if (contentCaptureManagerInternal != null && this.mActivityManagerService != null) {
            this.mActivityManagerService.setContentCaptureManager(contentCaptureManagerInternal);
        }
        timingsTraceAndSlog.traceEnd();
    }

    private void startAttentionService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!com.android.server.attention.AttentionManagerService.isServiceConfigured(context)) {
            android.util.Slog.d(TAG, "AttentionService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartAttentionManagerService");
        this.mSystemServiceManager.startService(com.android.server.attention.AttentionManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    private void startRotationResolverService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!com.android.server.rotationresolver.RotationResolverManagerService.isServiceConfigured(context)) {
            android.util.Slog.d(TAG, "RotationResolverService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartRotationResolverService");
        this.mSystemServiceManager.startService(com.android.server.rotationresolver.RotationResolverManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    private void startWearableSensingService(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startWearableSensingService");
        this.mSystemServiceManager.startService(com.android.server.wearable.WearableSensingManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    private static void startSystemUi(android.content.Context context, com.android.server.wm.WindowManagerService windowManagerService) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(packageManagerInternal.getSystemUiServiceComponent());
        intent.addFlags(256);
        context.startServiceAsUser(intent, android.os.UserHandle.SYSTEM);
        windowManagerService.onSystemUiStarted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean handleEarlySystemWtf(android.os.IBinder iBinder, java.lang.String str, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) {
        int myPid = android.os.Process.myPid();
        com.android.server.am.EventLogTags.writeAmWtf(android.os.UserHandle.getUserId(1000), myPid, "system_server", -1, str, parcelableCrashInfo.exceptionMessage);
        com.android.internal.util.FrameworkStatsLog.write(80, 1000, str, "system_server", myPid, 3);
        synchronized (com.android.server.SystemServer.class) {
            try {
                if (sPendingWtfs == null) {
                    sPendingWtfs = new java.util.LinkedList<>();
                }
                sPendingWtfs.add(new android.util.Pair<>(str, parcelableCrashInfo));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return false;
    }
}
