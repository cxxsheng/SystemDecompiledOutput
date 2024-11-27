package com.android.server.am;

/* loaded from: classes.dex */
public final class BatteryStatsService extends com.android.internal.app.IBatteryStats.Stub implements android.os.PowerManagerInternal.LowPowerModeListener, com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback, com.android.server.power.stats.BatteryStatsImpl.EnergyStatsRetriever, com.android.server.Watchdog.Monitor {
    private static final java.lang.String BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP_PROPERTY = "BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP";
    static final boolean DBG = false;
    private static final java.lang.String DEVICE_CONFIG_NAMESPACE = "backstage_power";
    private static final java.lang.String EMPTY = "Empty";
    private static final int MAX_LOW_POWER_STATS_SIZE = 32768;
    private static final java.lang.String MIN_CONSUMED_POWER_THRESHOLD_KEY = "min_consumed_power_threshold";
    private static final int POWER_STATS_QUERY_TIMEOUT_MILLIS = 2000;
    static final java.lang.String TAG = "BatteryStatsService";
    static final java.lang.String TRACE_TRACK_WAKEUP_REASON = "wakeup_reason";
    private static com.android.internal.app.IBatteryStats sService;
    private final com.android.server.power.stats.AggregatedPowerStatsConfig mAggregatedPowerStatsConfig;
    private android.os.BatteryManagerInternal mBatteryManagerInternal;
    private final com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig mBatteryStatsConfig;
    private final com.android.server.power.stats.BatteryUsageStatsProvider mBatteryUsageStatsProvider;
    private final android.util.AtomicFile mConfigFile;
    private final android.content.Context mContext;
    private final com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;
    final com.android.server.power.stats.wakeups.CpuWakeupStats mCpuWakeupStats;
    private final android.os.BatteryStats.BatteryStatsDumpHelper mDumpHelper;
    private final android.os.Handler mHandler;
    private final com.android.internal.os.MonotonicClock mMonotonicClock;
    private final com.android.internal.os.PowerProfile mPowerProfile;
    private final com.android.server.power.stats.PowerStatsScheduler mPowerStatsScheduler;
    private final com.android.server.power.stats.PowerStatsStore mPowerStatsStore;
    private final com.android.server.power.stats.PowerStatsUidResolver mPowerStatsUidResolver;
    final com.android.server.power.stats.BatteryStatsImpl mStats;
    private final com.android.server.power.stats.BatteryExternalStatsWorker mWorker;
    private volatile boolean mMonitorEnabled = true;
    private java.nio.charset.CharsetDecoder mDecoderStat = java.nio.charset.StandardCharsets.UTF_8.newDecoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE).replaceWith("?");
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.Object mPowerStatsLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mPowerStatsLock"})
    private android.power.PowerStatsInternal mPowerStatsInternal = null;

    @com.android.internal.annotations.GuardedBy({"mPowerStatsLock"})
    private java.util.Map<java.lang.Integer, java.lang.String> mEntityNames = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mPowerStatsLock"})
    private java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, java.lang.String>> mStateNames = new java.util.HashMap();

    @com.android.internal.annotations.GuardedBy({"mStats"})
    private int mLastPowerStateFromRadio = 1;

    @com.android.internal.annotations.GuardedBy({"mStats"})
    private int mLastPowerStateFromWifi = 1;
    private final android.net.INetworkManagementEventObserver mActivityChangeObserver = new com.android.server.net.BaseNetworkObserver() { // from class: com.android.server.am.BatteryStatsService.1
        public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) {
            int i3;
            if (z) {
                i3 = 3;
            } else {
                i3 = 1;
            }
            if (j <= 0) {
                j = android.os.SystemClock.elapsedRealtimeNanos();
            }
            switch (i) {
                case 0:
                    com.android.server.am.BatteryStatsService.this.noteMobileRadioPowerState(i3, j, i2);
                    break;
                case 1:
                    com.android.server.am.BatteryStatsService.this.noteWifiRadioPowerState(i3, j, i2);
                    break;
                default:
                    android.util.Slog.d(com.android.server.am.BatteryStatsService.TAG, "Received unexpected transport in interfaceClassDataActivityChanged unexpected type: " + i);
                    break;
            }
        }
    };
    private android.net.ConnectivityManager.NetworkCallback mNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.am.BatteryStatsService.2
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            com.android.server.am.BatteryStatsService.this.noteConnectivityChanged(com.android.net.module.util.NetworkCapabilitiesUtils.getDisplayTransport(networkCapabilities.getTransportTypes()), networkCapabilities.hasCapability(21) ? "CONNECTED" : "SUSPENDED");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            com.android.server.am.BatteryStatsService.this.noteConnectivityChanged(-1, "DISCONNECTED");
        }
    };
    private final com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider mUserManagerUserInfoProvider = new com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider() { // from class: com.android.server.am.BatteryStatsService.3
        private com.android.server.pm.UserManagerInternal umi;

        @Override // com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider
        public int[] getUserIds() {
            if (this.umi == null) {
                this.umi = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            }
            if (this.umi != null) {
                return this.umi.getUserIds();
            }
            return null;
        }
    };
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread("batterystats-handler");

    private native void getRailEnergyPowerStats(com.android.internal.os.RailStats railStats);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeWaitWakeup(java.nio.ByteBuffer byteBuffer);

    private void populatePowerEntityMaps() {
        android.hardware.power.stats.PowerEntity[] powerEntityInfo = this.mPowerStatsInternal.getPowerEntityInfo();
        if (powerEntityInfo == null) {
            return;
        }
        for (android.hardware.power.stats.PowerEntity powerEntity : powerEntityInfo) {
            java.util.HashMap hashMap = new java.util.HashMap();
            for (int i = 0; i < powerEntity.states.length; i++) {
                android.hardware.power.stats.State state = powerEntity.states[i];
                hashMap.put(java.lang.Integer.valueOf(state.id), state.name);
            }
            this.mEntityNames.put(java.lang.Integer.valueOf(powerEntity.id), powerEntity.name);
            this.mStateNames.put(java.lang.Integer.valueOf(powerEntity.id), hashMap);
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback
    public void fillLowPowerStats(com.android.internal.os.RpmStats rpmStats) {
        synchronized (this.mPowerStatsLock) {
            if (this.mPowerStatsInternal == null || this.mEntityNames.isEmpty() || this.mStateNames.isEmpty()) {
                return;
            }
            try {
                android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr = this.mPowerStatsInternal.getStateResidencyAsync(new int[0]).get(2000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (stateResidencyResultArr == null) {
                    return;
                }
                for (android.hardware.power.stats.StateResidencyResult stateResidencyResult : stateResidencyResultArr) {
                    com.android.internal.os.RpmStats.PowerStateSubsystem subsystem = rpmStats.getSubsystem(this.mEntityNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)));
                    for (int i = 0; i < stateResidencyResult.stateResidencyData.length; i++) {
                        android.hardware.power.stats.StateResidency stateResidency = stateResidencyResult.stateResidencyData[i];
                        subsystem.putState(this.mStateNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)).get(java.lang.Integer.valueOf(stateResidency.id)), stateResidency.totalTimeInStateMs, (int) stateResidency.totalStateEntryCount);
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to getStateResidencyAsync", e);
            }
        }
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.EnergyStatsRetriever
    public void fillRailDataStats(com.android.internal.os.RailStats railStats) {
        getRailEnergyPowerStats(railStats);
    }

    @Override // com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback
    public java.lang.String getSubsystemLowPowerStats() {
        synchronized (this.mPowerStatsLock) {
            if (this.mPowerStatsInternal == null || this.mEntityNames.isEmpty() || this.mStateNames.isEmpty()) {
                return EMPTY;
            }
            try {
                android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr = this.mPowerStatsInternal.getStateResidencyAsync(new int[0]).get(2000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (stateResidencyResultArr == null || stateResidencyResultArr.length == 0) {
                    return EMPTY;
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder("SubsystemPowerState");
                int i = 32768;
                int i2 = 0;
                while (true) {
                    if (i2 >= stateResidencyResultArr.length) {
                        break;
                    }
                    android.hardware.power.stats.StateResidencyResult stateResidencyResult = stateResidencyResultArr[i2];
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append(" subsystem_" + i2);
                    sb2.append(" name=" + this.mEntityNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)));
                    for (int i3 = 0; i3 < stateResidencyResult.stateResidencyData.length; i3++) {
                        android.hardware.power.stats.StateResidency stateResidency = stateResidencyResult.stateResidencyData[i3];
                        sb2.append(" state_" + i3);
                        sb2.append(" name=" + this.mStateNames.get(java.lang.Integer.valueOf(stateResidencyResult.id)).get(java.lang.Integer.valueOf(stateResidency.id)));
                        sb2.append(" time=" + stateResidency.totalTimeInStateMs);
                        sb2.append(" count=" + stateResidency.totalStateEntryCount);
                        sb2.append(" last entry=" + stateResidency.lastEntryTimestampMs);
                    }
                    if (sb2.length() <= i) {
                        i -= sb2.length();
                        sb.append((java.lang.CharSequence) sb2);
                        i2++;
                    } else {
                        android.util.Slog.e(TAG, "getSubsystemLowPowerStats: buffer not enough");
                        break;
                    }
                }
                return sb.toString();
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to getStateResidencyAsync", e);
                return EMPTY;
            }
        }
    }

    BatteryStatsService(android.content.Context context, java.io.File file) {
        this.mContext = context;
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
        this.mMonotonicClock = new com.android.internal.os.MonotonicClock(new java.io.File(file, "monotonic_clock.xml"));
        this.mPowerProfile = new com.android.internal.os.PowerProfile(context);
        this.mCpuScalingPolicies = new com.android.internal.os.CpuScalingPolicyReader().read();
        boolean z = context.getResources().getBoolean(android.R.bool.config_batteryStatsResetOnUnplugHighBatteryLevel);
        this.mBatteryStatsConfig = new com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder().setResetOnUnplugHighBatteryLevel(z).setResetOnUnplugAfterSignificantCharge(context.getResources().getBoolean(android.R.bool.config_batteryStatsResetOnUnplugAfterSignificantCharge)).setPowerStatsThrottlePeriodCpu(context.getResources().getInteger(android.R.integer.config_defaultPeakRefreshRate)).build();
        this.mPowerStatsUidResolver = new com.android.server.power.stats.PowerStatsUidResolver();
        this.mStats = new com.android.server.power.stats.BatteryStatsImpl(this.mBatteryStatsConfig, com.android.internal.os.Clock.SYSTEM_CLOCK, this.mMonotonicClock, file, this.mHandler, this, this, this.mUserManagerUserInfoProvider, this.mPowerProfile, this.mCpuScalingPolicies, this.mPowerStatsUidResolver);
        this.mWorker = new com.android.server.power.stats.BatteryExternalStatsWorker(context, this.mStats);
        this.mStats.setExternalStatsSyncLocked(this.mWorker);
        this.mStats.setRadioScanningTimeoutLocked(this.mContext.getResources().getInteger(android.R.integer.config_pinnerWebviewPinBytes) * 1000);
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        this.mStats.startTrackingSystemServerCpuTime();
        this.mAggregatedPowerStatsConfig = createAggregatedPowerStatsConfig();
        this.mPowerStatsStore = new com.android.server.power.stats.PowerStatsStore(file, this.mHandler, this.mAggregatedPowerStatsConfig);
        this.mPowerStatsScheduler = createPowerStatsScheduler(this.mContext);
        this.mBatteryUsageStatsProvider = new com.android.server.power.stats.BatteryUsageStatsProvider(context, new com.android.server.power.stats.PowerStatsExporter(this.mPowerStatsStore, new com.android.server.power.stats.PowerStatsAggregator(this.mAggregatedPowerStatsConfig, this.mStats.getHistory())), this.mPowerProfile, this.mCpuScalingPolicies, this.mPowerStatsStore, com.android.internal.os.Clock.SYSTEM_CLOCK);
        this.mStats.saveBatteryUsageStatsOnReset(this.mBatteryUsageStatsProvider, this.mPowerStatsStore);
        this.mDumpHelper = new com.android.server.power.stats.BatteryStatsDumpHelperImpl(this.mBatteryUsageStatsProvider);
        this.mCpuWakeupStats = new com.android.server.power.stats.wakeups.CpuWakeupStats(context, android.R.xml.irq_device_map, this.mHandler);
        this.mConfigFile = new android.util.AtomicFile(new java.io.File(file, "battery_usage_stats_config"));
    }

    private com.android.server.power.stats.PowerStatsScheduler createPowerStatsScheduler(android.content.Context context) {
        long integer = context.getResources().getInteger(android.R.integer.config_aggregatedPowerStatsSpanDuration);
        long integer2 = context.getResources().getInteger(android.R.integer.config_pdp_reject_retry_delay_ms);
        com.android.server.power.stats.PowerStatsScheduler.AlarmScheduler alarmScheduler = new com.android.server.power.stats.PowerStatsScheduler.AlarmScheduler() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda14
            @Override // com.android.server.power.stats.PowerStatsScheduler.AlarmScheduler
            public final void scheduleAlarm(long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
                com.android.server.am.BatteryStatsService.this.lambda$createPowerStatsScheduler$0(j, str, onAlarmListener, handler);
            }
        };
        final com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl = this.mStats;
        java.util.Objects.requireNonNull(batteryStatsImpl);
        return new com.android.server.power.stats.PowerStatsScheduler(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.schedulePowerStatsSampleCollection();
            }
        }, new com.android.server.power.stats.PowerStatsAggregator(this.mAggregatedPowerStatsConfig, this.mStats.getHistory()), integer, integer2, this.mPowerStatsStore, alarmScheduler, com.android.internal.os.Clock.SYSTEM_CLOCK, this.mMonotonicClock, new java.util.function.Supplier() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Long lambda$createPowerStatsScheduler$1;
                lambda$createPowerStatsScheduler$1 = com.android.server.am.BatteryStatsService.this.lambda$createPowerStatsScheduler$1();
                return lambda$createPowerStatsScheduler$1;
            }
        }, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPowerStatsScheduler$0(long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
        ((android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class)).set(3, j, str, onAlarmListener, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Long lambda$createPowerStatsScheduler$1() {
        return java.lang.Long.valueOf(this.mStats.getHistory().getStartTime());
    }

    private com.android.server.power.stats.AggregatedPowerStatsConfig createAggregatedPowerStatsConfig() {
        com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig = new com.android.server.power.stats.AggregatedPowerStatsConfig();
        aggregatedPowerStatsConfig.trackPowerComponent(1).trackDeviceStates(0, 1).trackUidStates(0, 1, 2).setProcessor(new com.android.server.power.stats.CpuAggregatedPowerStatsProcessor(this.mPowerProfile, this.mCpuScalingPolicies));
        return aggregatedPowerStatsConfig;
    }

    public static com.android.server.am.BatteryStatsService create(android.content.Context context, java.io.File file, android.os.Handler handler, com.android.server.power.stats.BatteryStatsImpl.BatteryCallback batteryCallback) {
        com.android.server.am.BatteryStatsService batteryStatsService = new com.android.server.am.BatteryStatsService(context, file);
        batteryStatsService.mStats.setCallback(batteryCallback);
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.readLocked();
        }
        batteryStatsService.scheduleWriteToDisk();
        return batteryStatsService;
    }

    public void publish() {
        com.android.server.LocalServices.addService(android.os.BatteryStatsInternal.class, new com.android.server.am.BatteryStatsService.LocalService());
        android.os.ServiceManager.addService("batterystats", asBinder());
    }

    public void systemServicesReady() {
        com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl = this.mStats;
        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        batteryStatsImpl.setPowerStatsCollectorEnabled(false);
        com.android.server.power.stats.BatteryUsageStatsProvider batteryUsageStatsProvider = this.mBatteryUsageStatsProvider;
        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        batteryUsageStatsProvider.setPowerStatsExporterEnabled(false);
        this.mWorker.systemServicesReady();
        this.mStats.systemServicesReady(this.mContext);
        this.mCpuWakeupStats.systemServicesReady();
        android.os.INetworkManagementService asInterface = android.os.INetworkManagementService.Stub.asInterface(android.os.ServiceManager.getService("network_management"));
        android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        try {
            if (!com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
                asInterface.registerObserver(this.mActivityChangeObserver);
            }
            connectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not register INetworkManagement event observer " + e);
        }
        synchronized (this.mPowerStatsLock) {
            try {
                this.mPowerStatsInternal = (android.power.PowerStatsInternal) com.android.server.LocalServices.getService(android.power.PowerStatsInternal.class);
                if (this.mPowerStatsInternal != null) {
                    populatePowerEntityMaps();
                } else {
                    android.util.Slog.e(TAG, "Could not register PowerStatsInternal");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);
        com.android.server.Watchdog.getInstance().addMonitor(this);
        new com.android.server.am.DataConnectionStats(this.mContext, this.mHandler).startMonitoring();
        registerStatsCallbacks();
    }

    public void onSystemReady() {
        this.mStats.onSystemReady();
        com.android.server.power.stats.PowerStatsScheduler powerStatsScheduler = this.mPowerStatsScheduler;
        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        powerStatsScheduler.start(false);
    }

    private final class LocalService extends android.os.BatteryStatsInternal {
        private LocalService() {
        }

        @Override // android.os.BatteryStatsInternal
        public java.lang.String[] getWifiIfaces() {
            return (java.lang.String[]) com.android.server.am.BatteryStatsService.this.mStats.getWifiIfaces().clone();
        }

        @Override // android.os.BatteryStatsInternal
        public java.lang.String[] getMobileIfaces() {
            return (java.lang.String[]) com.android.server.am.BatteryStatsService.this.mStats.getMobileIfaces().clone();
        }

        @Override // android.os.BatteryStatsInternal
        public com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes getSystemServiceCpuThreadTimes() {
            return com.android.server.am.BatteryStatsService.this.mStats.getSystemServiceCpuThreadTimes();
        }

        @Override // android.os.BatteryStatsInternal
        public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) {
            return com.android.server.am.BatteryStatsService.this.getBatteryUsageStats(list);
        }

        @Override // android.os.BatteryStatsInternal
        public void noteJobsDeferred(int i, int i2, long j) {
            com.android.server.am.BatteryStatsService.this.noteJobsDeferred(i, i2, j);
        }

        private int transportToSubsystem(android.net.NetworkCapabilities networkCapabilities) {
            if (networkCapabilities.hasTransport(1)) {
                return 2;
            }
            if (networkCapabilities.hasTransport(0)) {
                return 5;
            }
            return -1;
        }

        @Override // android.os.BatteryStatsInternal
        public void noteCpuWakingNetworkPacket(android.net.Network network, long j, int i) {
            if (i < 0) {
                android.util.Slog.e(com.android.server.am.BatteryStatsService.TAG, "Invalid uid for waking network packet: " + i);
                return;
            }
            int transportToSubsystem = transportToSubsystem(((android.net.ConnectivityManager) com.android.server.am.BatteryStatsService.this.mContext.getSystemService(android.net.ConnectivityManager.class)).getNetworkCapabilities(network));
            if (transportToSubsystem == -1) {
                android.util.Slog.wtf(com.android.server.am.BatteryStatsService.TAG, "Could not map transport for network: " + network + " while attributing wakeup by packet sent to uid: " + i);
                return;
            }
            com.android.server.am.BatteryStatsService.this.noteCpuWakingActivity(transportToSubsystem, j, i);
        }

        @Override // android.os.BatteryStatsInternal
        public void noteBinderCallStats(int i, long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection) {
            synchronized (com.android.server.am.BatteryStatsService.this.mLock) {
                android.os.Handler handler = com.android.server.am.BatteryStatsService.this.mHandler;
                final com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl = com.android.server.am.BatteryStatsService.this.mStats;
                java.util.Objects.requireNonNull(batteryStatsImpl);
                handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.am.BatteryStatsService$LocalService$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                        com.android.server.power.stats.BatteryStatsImpl.this.noteBinderCallStats(((java.lang.Integer) obj).intValue(), ((java.lang.Long) obj2).longValue(), (java.util.Collection) obj3, ((java.lang.Long) obj4).longValue(), ((java.lang.Long) obj5).longValue());
                    }
                }, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j), collection, java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime()), java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis())));
            }
        }

        @Override // android.os.BatteryStatsInternal
        public void noteBinderThreadNativeIds(int[] iArr) {
            synchronized (com.android.server.am.BatteryStatsService.this.mLock) {
                com.android.server.am.BatteryStatsService.this.mStats.noteBinderThreadNativeIds(iArr);
            }
        }

        @Override // android.os.BatteryStatsInternal
        public void noteWakingSoundTrigger(long j, int i) {
            com.android.server.am.BatteryStatsService.this.noteCpuWakingActivity(3, j, i);
        }

        @Override // android.os.BatteryStatsInternal
        public void noteWakingAlarmBatch(long j, int... iArr) {
            com.android.server.am.BatteryStatsService.this.noteCpuWakingActivity(1, j, iArr);
        }
    }

    void noteCpuWakingActivity(final int i, final long j, final int... iArr) {
        java.util.Objects.requireNonNull(iArr);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.BatteryStatsService.this.lambda$noteCpuWakingActivity$2(i, j, iArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteCpuWakingActivity$2(int i, long j, int[] iArr) {
        this.mCpuWakeupStats.noteWakingActivity(i, j, iArr);
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        if (!this.mMonitorEnabled) {
            return;
        }
        synchronized (this.mLock) {
        }
        synchronized (this.mStats) {
        }
    }

    private static void awaitUninterruptibly(java.util.concurrent.Future<?> future) {
        while (true) {
            try {
                future.get();
                return;
            } catch (java.lang.InterruptedException e) {
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.ExecutionException e2) {
                return;
            }
        }
    }

    private void syncStats(java.lang.String str, int i) {
        awaitUninterruptibly(this.mWorker.scheduleSync(str, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void awaitCompletion() {
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda86
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (java.lang.InterruptedException e) {
        }
    }

    public void initPowerManagement() {
        android.os.PowerManagerInternal powerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        powerManagerInternal.registerLowPowerModeObserver(this);
        synchronized (this.mStats) {
            this.mStats.notePowerSaveModeLockedInit(powerManagerInternal.getLowPowerState(9).batterySaverEnabled, android.os.SystemClock.elapsedRealtime(), android.os.SystemClock.uptimeMillis());
        }
        new com.android.server.am.BatteryStatsService.WakeupReasonThread().start();
    }

    public void shutdown() {
        android.util.Slog.w("BatteryStats", "Writing battery stats before shutdown...");
        awaitCompletion();
        syncStats("shutdown", 127);
        synchronized (this.mStats) {
            this.mStats.shutdownLocked();
        }
        this.mWorker.shutdown();
        this.mMonotonicClock.write();
    }

    public static com.android.internal.app.IBatteryStats getService() {
        if (sService != null) {
            return sService;
        }
        sService = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        return sService;
    }

    public int getServiceType() {
        return 9;
    }

    public void onLowPowerModeChanged(final android.os.PowerSaveState powerSaveState) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda97
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$onLowPowerModeChanged$4(powerSaveState, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLowPowerModeChanged$4(android.os.PowerSaveState powerSaveState, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePowerSaveModeLocked(powerSaveState.batterySaverEnabled, j, j2);
        }
    }

    public com.android.server.power.stats.BatteryStatsImpl getActiveStatistics() {
        return this.mStats;
    }

    public void scheduleWriteToDisk() {
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$scheduleWriteToDisk$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleWriteToDisk$5() {
        this.mWorker.scheduleWrite();
    }

    void removeUid(final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$removeUid$6(i, elapsedRealtime);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeUid$6(int i, long j) {
        this.mCpuWakeupStats.onUidRemoved(i);
        synchronized (this.mStats) {
            this.mStats.removeUidStatsLocked(i, j);
        }
    }

    void onCleanupUser(final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda66
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$onCleanupUser$7(i, elapsedRealtime);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCleanupUser$7(int i, long j) {
        synchronized (this.mStats) {
            this.mStats.onCleanupUserLocked(i, j);
        }
    }

    void onUserRemoved(final int i) {
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda100
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$onUserRemoved$8(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserRemoved$8(int i) {
        synchronized (this.mStats) {
            this.mStats.onUserRemovedLocked(i);
        }
    }

    void addIsolatedUid(int i, int i2) {
        this.mPowerStatsUidResolver.noteIsolatedUidAdded(i, i2);
        com.android.internal.util.FrameworkStatsLog.write(43, i2, i, 1);
    }

    void removeIsolatedUid(int i, int i2) {
        this.mPowerStatsUidResolver.noteIsolatedUidRemoved(i, i2);
        com.android.internal.util.FrameworkStatsLog.write(43, -1, i, 0);
    }

    void noteProcessStart(final java.lang.String str, final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteProcessStart$9(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(28, i, str, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteProcessStart$9(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteProcessStartLocked(str, i, j, j2);
        }
    }

    void noteProcessCrash(final java.lang.String str, final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda93
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteProcessCrash$10(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(28, i, str, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteProcessCrash$10(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteProcessCrashLocked(str, i, j, j2);
        }
    }

    void noteProcessAnr(final java.lang.String str, final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteProcessAnr$11(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteProcessAnr$11(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteProcessAnrLocked(str, i, j, j2);
        }
    }

    void noteProcessFinish(final java.lang.String str, final int i) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteProcessFinish$12(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(28, i, str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteProcessFinish$12(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteProcessFinishLocked(str, i, j, j2);
        }
    }

    void noteUidProcessState(final int i, final int i2) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda83
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteUidProcessState$13(i, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteUidProcessState$13(int i, int i2, long j, long j2) {
        this.mCpuWakeupStats.noteUidProcessState(i, i2);
        synchronized (this.mStats) {
            this.mStats.noteUidProcessStateLocked(i, i2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public java.util.List<android.os.BatteryUsageStats> getBatteryUsageStats(java.util.List<android.os.BatteryUsageStatsQuery> list) {
        super.getBatteryUsageStats_enforcePermission();
        awaitCompletion();
        if (com.android.server.power.stats.BatteryUsageStatsProvider.shouldUpdateStats(list, android.os.SystemClock.elapsedRealtime(), this.mWorker.getLastCollectionTimeStamp())) {
            syncStats("get-stats", 127);
            com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        }
        return this.mBatteryUsageStatsProvider.getBatteryUsageStats(this.mStats, list);
    }

    private void registerStatsCallbacks() {
        android.app.StatsManager statsManager = (android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class);
        com.android.server.am.BatteryStatsService.StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new com.android.server.am.BatteryStatsService.StatsPullAtomCallbackImpl();
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET_USING_POWER_PROFILE_MODEL, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_BEFORE_RESET, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
    }

    private class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        private StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
            long startClockTime;
            android.os.BatteryUsageStats batteryUsageStats;
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_BEFORE_RESET /* 10111 */:
                    long lastBatteryUsageStatsBeforeResetAtomPullTimestamp = com.android.server.am.BatteryStatsService.this.getLastBatteryUsageStatsBeforeResetAtomPullTimestamp();
                    synchronized (com.android.server.am.BatteryStatsService.this.mStats) {
                        startClockTime = com.android.server.am.BatteryStatsService.this.mStats.getStartClockTime();
                    }
                    batteryUsageStats = com.android.server.am.BatteryStatsService.this.getBatteryUsageStats(java.util.List.of(new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includeProcessStateData().includeVirtualUids().aggregateSnapshots(lastBatteryUsageStatsBeforeResetAtomPullTimestamp, startClockTime).build())).get(0);
                    com.android.server.am.BatteryStatsService.this.setLastBatteryUsageStatsBeforeResetAtomPullTimestamp(startClockTime);
                    break;
                case com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET /* 10112 */:
                    batteryUsageStats = com.android.server.am.BatteryStatsService.this.getBatteryUsageStats(java.util.List.of(new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includeProcessStateData().includeVirtualUids().includePowerModels().setMinConsumedPowerThreshold(android.provider.DeviceConfig.getFloat(com.android.server.am.BatteryStatsService.DEVICE_CONFIG_NAMESPACE, com.android.server.am.BatteryStatsService.MIN_CONSUMED_POWER_THRESHOLD_KEY, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)).build())).get(0);
                    break;
                case com.android.internal.util.FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET_USING_POWER_PROFILE_MODEL /* 10113 */:
                    batteryUsageStats = com.android.server.am.BatteryStatsService.this.getBatteryUsageStats(java.util.List.of(new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includeProcessStateData().includeVirtualUids().powerProfileModeledOnly().includePowerModels().build())).get(0);
                    break;
                default:
                    throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, batteryUsageStats.getStatsProto()));
            return 0;
        }
    }

    @android.annotation.RequiresNoPermission
    public boolean isCharging() {
        boolean isCharging;
        synchronized (this.mStats) {
            isCharging = this.mStats.isCharging();
        }
        return isCharging;
    }

    @android.annotation.RequiresNoPermission
    public long computeBatteryTimeRemaining() {
        long computeBatteryTimeRemaining;
        synchronized (this.mStats) {
            try {
                computeBatteryTimeRemaining = this.mStats.computeBatteryTimeRemaining(android.os.SystemClock.elapsedRealtime());
                if (computeBatteryTimeRemaining >= 0) {
                    computeBatteryTimeRemaining /= 1000;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return computeBatteryTimeRemaining;
    }

    @android.annotation.RequiresNoPermission
    public long computeChargeTimeRemaining() {
        long computeChargeTimeRemaining;
        synchronized (this.mStats) {
            try {
                computeChargeTimeRemaining = this.mStats.computeChargeTimeRemaining(android.os.SystemClock.elapsedRealtime());
                if (computeChargeTimeRemaining >= 0) {
                    computeChargeTimeRemaining /= 1000;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return computeChargeTimeRemaining;
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public long computeBatteryScreenOffRealtimeMs() {
        long computeBatteryScreenOffRealtime;
        super.computeBatteryScreenOffRealtimeMs_enforcePermission();
        synchronized (this.mStats) {
            computeBatteryScreenOffRealtime = this.mStats.computeBatteryScreenOffRealtime(android.os.SystemClock.elapsedRealtimeNanos() / 1000, 0) / 1000;
        }
        return computeBatteryScreenOffRealtime;
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public long getScreenOffDischargeMah() {
        long uahDischargeScreenOff;
        super.getScreenOffDischargeMah_enforcePermission();
        synchronized (this.mStats) {
            uahDischargeScreenOff = this.mStats.getUahDischargeScreenOff(0) / 1000;
        }
        return uahDischargeScreenOff;
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteEvent(final int i, final java.lang.String str, final int i2) {
        super.noteEvent_enforcePermission();
        if (str == null) {
            android.util.Slog.wtfStack(TAG, "noteEvent called with null name. code = " + i);
            return;
        }
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda80
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteEvent$14(i, str, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteEvent$14(int i, java.lang.String str, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteEventLocked(i, str, i2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteSyncStart(final java.lang.String str, final int i) {
        super.noteSyncStart_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteSyncStart$15(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(7, i, (java.lang.String) null, str, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteSyncStart$15(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteSyncStartLocked(str, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteSyncFinish(final java.lang.String str, final int i) {
        super.noteSyncFinish_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteSyncFinish$16(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(7, i, (java.lang.String) null, str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteSyncFinish$16(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteSyncFinishLocked(str, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteJobStart(final java.lang.String str, final int i) {
        super.noteJobStart_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteJobStart$17(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteJobStart$17(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteJobStartLocked(str, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteJobFinish(final java.lang.String str, final int i, final int i2) {
        super.noteJobFinish_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteJobFinish$18(str, i, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteJobFinish$18(java.lang.String str, int i, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteJobFinishLocked(str, i, i2, j, j2);
        }
    }

    void noteJobsDeferred(final int i, final int i2, final long j) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda64
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteJobsDeferred$19(i, i2, j, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteJobsDeferred$19(int i, int i2, long j, long j2, long j3) {
        synchronized (this.mStats) {
            this.mStats.noteJobsDeferredLocked(i, i2, j, j2, j3);
        }
    }

    public void noteWakupAlarm(final java.lang.String str, final int i, android.os.WorkSource workSource, final java.lang.String str2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", "noteWakupAlarm");
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda88
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWakupAlarm$20(str, i, workSource2, str2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWakupAlarm$20(java.lang.String str, int i, android.os.WorkSource workSource, java.lang.String str2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWakupAlarmLocked(str, i, workSource, str2, j, j2);
        }
    }

    public void noteAlarmStart(final java.lang.String str, android.os.WorkSource workSource, final int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", "noteAlarmStart");
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteAlarmStart$21(str, workSource2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteAlarmStart$21(java.lang.String str, android.os.WorkSource workSource, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteAlarmStartLocked(str, workSource, i, j, j2);
        }
    }

    public void noteAlarmFinish(final java.lang.String str, android.os.WorkSource workSource, final int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.UPDATE_DEVICE_STATS", "noteAlarmFinish");
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteAlarmFinish$22(str, workSource2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteAlarmFinish$22(java.lang.String str, android.os.WorkSource workSource, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteAlarmFinishLocked(str, workSource, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartWakelock(final int i, final int i2, final java.lang.String str, final java.lang.String str2, final int i3, final boolean z) {
        super.noteStartWakelock_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartWakelock$23(i, i2, str, str2, i3, z, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartWakelock$23(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStartWakeLocked(i, i2, null, str, str2, i3, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopWakelock(final int i, final int i2, final java.lang.String str, final java.lang.String str2, final int i3) {
        super.noteStopWakelock_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopWakelock$24(i, i2, str, str2, i3, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopWakelock$24(int i, int i2, java.lang.String str, java.lang.String str2, int i3, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStopWakeLocked(i, i2, null, str, str2, i3, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartWakelockFromSource(android.os.WorkSource workSource, final int i, final java.lang.String str, final java.lang.String str2, final int i2, final boolean z) {
        super.noteStartWakelockFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartWakelockFromSource$25(workSource2, i, str, str2, i2, z, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartWakelockFromSource$25(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStartWakeFromSourceLocked(workSource, i, str, str2, i2, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteChangeWakelockFromSource(android.os.WorkSource workSource, final int i, final java.lang.String str, final java.lang.String str2, final int i2, android.os.WorkSource workSource2, final int i3, final java.lang.String str3, final java.lang.String str4, final int i4, final boolean z) {
        super.noteChangeWakelockFromSource_enforcePermission();
        android.os.WorkSource workSource3 = workSource != null ? new android.os.WorkSource(workSource) : null;
        final android.os.WorkSource workSource4 = workSource2 != null ? new android.os.WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            try {
                try {
                    final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    final long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    final android.os.WorkSource workSource5 = workSource3;
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda33
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.BatteryStatsService.this.lambda$noteChangeWakelockFromSource$26(workSource5, i, str, str2, i2, workSource4, i3, str3, str4, i4, z, elapsedRealtime, uptimeMillis);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteChangeWakelockFromSource$26(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, android.os.WorkSource workSource2, int i3, java.lang.String str3, java.lang.String str4, int i4, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteChangeWakelockFromSourceLocked(workSource, i, str, str2, i2, workSource2, i3, str3, str4, i4, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopWakelockFromSource(android.os.WorkSource workSource, final int i, final java.lang.String str, final java.lang.String str2, final int i2) {
        super.noteStopWakelockFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopWakelockFromSource$27(workSource2, i, str, str2, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopWakelockFromSource$27(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStopWakeFromSourceLocked(workSource, i, str, str2, i2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteLongPartialWakelockStart(final java.lang.String str, final java.lang.String str2, final int i) {
        super.noteLongPartialWakelockStart_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda107
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteLongPartialWakelockStart$28(str, str2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteLongPartialWakelockStart$28(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteLongPartialWakelockStart(str, str2, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteLongPartialWakelockStartFromSource(final java.lang.String str, final java.lang.String str2, android.os.WorkSource workSource) {
        super.noteLongPartialWakelockStartFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda46
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteLongPartialWakelockStartFromSource$29(str, str2, workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteLongPartialWakelockStartFromSource$29(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteLongPartialWakelockStartFromSource(str, str2, workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteLongPartialWakelockFinish(final java.lang.String str, final java.lang.String str2, final int i) {
        super.noteLongPartialWakelockFinish_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteLongPartialWakelockFinish$30(str, str2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteLongPartialWakelockFinish$30(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteLongPartialWakelockFinish(str, str2, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteLongPartialWakelockFinishFromSource(final java.lang.String str, final java.lang.String str2, android.os.WorkSource workSource) {
        super.noteLongPartialWakelockFinishFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda52
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteLongPartialWakelockFinishFromSource$31(str, str2, workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteLongPartialWakelockFinishFromSource$31(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteLongPartialWakelockFinishFromSource(str, str2, workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartSensor(final int i, final int i2) {
        super.noteStartSensor_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartSensor$32(i, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(5, i, (java.lang.String) null, i2, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartSensor$32(int i, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStartSensorLocked(i, i2, j, j2);
        }
    }

    public void noteWakeupSensorEvent(long j, int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000) {
            throw new java.lang.SecurityException("Calling uid " + callingUid + " is not system uid");
        }
        long millis = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(j);
        android.hardware.Sensor sensorByHandle = ((android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class)).getSensorByHandle(i2);
        if (sensorByHandle == null) {
            android.util.Slog.w(TAG, "Unknown sensor handle " + i2 + " received in noteWakeupSensorEvent");
            return;
        }
        if (i < 0) {
            android.util.Slog.wtf(TAG, "Invalid uid " + i + " for sensor event with sensor: " + sensorByHandle);
            return;
        }
        noteCpuWakingActivity(4, millis, i);
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopSensor(final int i, final int i2) {
        super.noteStopSensor_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda85
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopSensor$33(i, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(5, i, (java.lang.String) null, i2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopSensor$33(int i, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteStopSensorLocked(i, i2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteVibratorOn(final int i, final long j) {
        super.noteVibratorOn_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda98
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteVibratorOn$34(i, j, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteVibratorOn$34(int i, long j, long j2, long j3) {
        synchronized (this.mStats) {
            this.mStats.noteVibratorOnLocked(i, j, j2, j3);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteVibratorOff(final int i) {
        super.noteVibratorOff_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteVibratorOff$35(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteVibratorOff$35(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteVibratorOffLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteGpsChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) {
        super.noteGpsChanged_enforcePermission();
        final android.os.WorkSource workSource3 = workSource != null ? new android.os.WorkSource(workSource) : null;
        final android.os.WorkSource workSource4 = workSource2 != null ? new android.os.WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda89
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteGpsChanged$36(workSource3, workSource4, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteGpsChanged$36(android.os.WorkSource workSource, android.os.WorkSource workSource2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteGpsChangedLocked(workSource, workSource2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteGpsSignalQuality(final int i) {
        super.noteGpsSignalQuality_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda102
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteGpsSignalQuality$37(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteGpsSignalQuality$37(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteGpsSignalQualityLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteScreenState(final int i) {
        super.noteScreenState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda79
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteScreenState$38(i, elapsedRealtime, uptimeMillis, currentTimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(29, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteScreenState$38(int i, long j, long j2, long j3) {
        synchronized (this.mStats) {
            this.mStats.noteScreenStateLocked(0, i, j, j2, j3);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteScreenBrightness(final int i) {
        super.noteScreenBrightness_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteScreenBrightness$39(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(9, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteScreenBrightness$39(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteScreenBrightnessLocked(0, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteUserActivity(final int i, final int i2) {
        super.noteUserActivity_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteUserActivity$40(i, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteUserActivity$40(int i, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteUserActivityLocked(i, i2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWakeUp(final java.lang.String str, final int i) {
        super.noteWakeUp_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda81
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWakeUp$41(str, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWakeUp$41(java.lang.String str, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWakeUpLocked(str, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteInteractive(final boolean z) {
        super.noteInteractive_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda106
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteInteractive$42(z, elapsedRealtime);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteInteractive$42(boolean z, long j) {
        synchronized (this.mStats) {
            this.mStats.noteInteractiveLocked(z, j);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteConnectivityChanged(final int i, final java.lang.String str) {
        super.noteConnectivityChanged_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda61
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteConnectivityChanged$43(i, str, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteConnectivityChanged$43(int i, java.lang.String str, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteConnectivityChangedLocked(i, str, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteMobileRadioPowerState(final int i, final long j, final int i2) {
        super.noteMobileRadioPowerState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda101
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteMobileRadioPowerState$44(i, j, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(12, i2, null, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteMobileRadioPowerState$44(int i, long j, int i2, long j2, long j3) {
        synchronized (this.mStats) {
            try {
                if (this.mLastPowerStateFromRadio == i) {
                    return;
                }
                this.mLastPowerStateFromRadio = i;
                boolean noteMobileRadioPowerStateLocked = this.mStats.noteMobileRadioPowerStateLocked(i, j, i2, j2, j3);
                if (noteMobileRadioPowerStateLocked) {
                    this.mWorker.scheduleSync("modem-data", 4);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void notePhoneOn() {
        super.notePhoneOn_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePhoneOn$45(elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePhoneOn$45(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePhoneOnLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void notePhoneOff() {
        super.notePhoneOff_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda63
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePhoneOff$46(elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePhoneOff$46(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePhoneOffLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void notePhoneSignalStrength(final android.telephony.SignalStrength signalStrength) {
        super.notePhoneSignalStrength_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda73
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePhoneSignalStrength$47(signalStrength, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePhoneSignalStrength$47(android.telephony.SignalStrength signalStrength, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePhoneSignalStrengthLocked(signalStrength, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void notePhoneDataConnectionState(final int i, final boolean z, final int i2, final int i3, final int i4) {
        super.notePhoneDataConnectionState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda77
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePhoneDataConnectionState$48(i, z, i2, i3, i4, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePhoneDataConnectionState$48(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePhoneDataConnectionStateLocked(i, z, i2, i3, i4, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void notePhoneState(final int i) {
        super.notePhoneState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePhoneState$49(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePhoneState$49(int i, long j, long j2) {
        int simState = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).getSimState();
        synchronized (this.mStats) {
            this.mStats.notePhoneStateLocked(i, simState, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiOn() {
        super.noteWifiOn_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiOn$50(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(113, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiOn$50(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiOnLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiOff() {
        super.noteWifiOff_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiOff$51(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(113, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiOff$51(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiOffLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartAudio(final int i) {
        super.noteStartAudio_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda78
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartAudio$52(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(23, i, null, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartAudio$52(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteAudioOnLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopAudio(final int i) {
        super.noteStopAudio_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda99
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopAudio$53(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(23, i, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopAudio$53(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteAudioOffLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartVideo(final int i) {
        super.noteStartVideo_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda74
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartVideo$54(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(24, i, null, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartVideo$54(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteVideoOnLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopVideo(final int i) {
        super.noteStopVideo_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopVideo$55(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(24, i, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopVideo$55(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteVideoOffLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteResetAudio() {
        super.noteResetAudio_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteResetAudio$56(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(23, -1, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteResetAudio$56(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteResetAudioLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteResetVideo() {
        super.noteResetVideo_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteResetVideo$57(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(24, -1, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteResetVideo$57(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteResetVideoLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFlashlightOn(final int i) {
        super.noteFlashlightOn_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFlashlightOn$58(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(26, i, null, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFlashlightOn$58(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFlashlightOnLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFlashlightOff(final int i) {
        super.noteFlashlightOff_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda54
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFlashlightOff$59(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(26, i, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFlashlightOff$59(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFlashlightOffLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStartCamera(final int i) {
        super.noteStartCamera_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda92
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStartCamera$60(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(25, i, null, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStartCamera$60(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteCameraOnLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteStopCamera(final int i) {
        super.noteStopCamera_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteStopCamera$61(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(25, i, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteStopCamera$61(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteCameraOffLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteResetCamera() {
        super.noteResetCamera_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteResetCamera$62(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(25, -1, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteResetCamera$62(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteResetCameraLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteResetFlashlight() {
        super.noteResetFlashlight_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda91
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteResetFlashlight$63(elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(26, -1, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteResetFlashlight$63(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteResetFlashlightLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiRadioPowerState(final int i, final long j, final int i2) {
        super.noteWifiRadioPowerState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiRadioPowerState$64(i, j, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write_non_chained(13, i2, null, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiRadioPowerState$64(int i, long j, int i2, long j2, long j3) {
        synchronized (this.mStats) {
            try {
                if (this.mLastPowerStateFromWifi == i) {
                    return;
                }
                this.mLastPowerStateFromWifi = i;
                if (this.mStats.isOnBattery()) {
                    java.lang.String str = (i == 3 || i == 2) ? com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE : "inactive";
                    this.mWorker.scheduleSync("wifi-data: " + str, 2);
                }
                this.mStats.noteWifiRadioPowerState(i, j, i2, j2, j3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiRunning(android.os.WorkSource workSource) {
        super.noteWifiRunning_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiRunning$65(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(114, workSource, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiRunning$65(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiRunningLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiRunningChanged(android.os.WorkSource workSource, android.os.WorkSource workSource2) {
        super.noteWifiRunningChanged_enforcePermission();
        final android.os.WorkSource workSource3 = workSource != null ? new android.os.WorkSource(workSource) : null;
        final android.os.WorkSource workSource4 = workSource2 != null ? new android.os.WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda58
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiRunningChanged$66(workSource3, workSource4, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(114, workSource2, 1);
        com.android.internal.util.FrameworkStatsLog.write(114, workSource, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiRunningChanged$66(android.os.WorkSource workSource, android.os.WorkSource workSource2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiRunningChangedLocked(workSource, workSource2, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiStopped(android.os.WorkSource workSource) {
        super.noteWifiStopped_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : workSource;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiStopped$67(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
        com.android.internal.util.FrameworkStatsLog.write(114, workSource, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiStopped$67(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiStoppedLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiState(final int i, final java.lang.String str) {
        super.noteWifiState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiState$68(i, str, elapsedRealtime);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiState$68(int i, java.lang.String str, long j) {
        synchronized (this.mStats) {
            this.mStats.noteWifiStateLocked(i, str, j);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiSupplicantStateChanged(final int i, final boolean z) {
        super.noteWifiSupplicantStateChanged_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda42
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiSupplicantStateChanged$69(i, z, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiSupplicantStateChanged$69(int i, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiSupplicantStateChangedLocked(i, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiRssiChanged(final int i) {
        super.noteWifiRssiChanged_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda45
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiRssiChanged$70(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiRssiChanged$70(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiRssiChangedLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFullWifiLockAcquired(final int i) {
        super.noteFullWifiLockAcquired_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFullWifiLockAcquired$71(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFullWifiLockAcquired$71(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFullWifiLockAcquiredLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFullWifiLockReleased(final int i) {
        super.noteFullWifiLockReleased_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda59
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFullWifiLockReleased$72(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFullWifiLockReleased$72(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFullWifiLockReleasedLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiScanStarted(final int i) {
        super.noteWifiScanStarted_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda82
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiScanStarted$73(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiScanStarted$73(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiScanStartedLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiScanStopped(final int i) {
        super.noteWifiScanStopped_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiScanStopped$74(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiScanStopped$74(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiScanStoppedLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiMulticastEnabled(final int i) {
        super.noteWifiMulticastEnabled_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiMulticastEnabled$75(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiMulticastEnabled$75(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiMulticastEnabledLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiMulticastDisabled(final int i) {
        super.noteWifiMulticastDisabled_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiMulticastDisabled$76(i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiMulticastDisabled$76(int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiMulticastDisabledLocked(i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFullWifiLockAcquiredFromSource(android.os.WorkSource workSource) {
        super.noteFullWifiLockAcquiredFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda96
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFullWifiLockAcquiredFromSource$77(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFullWifiLockAcquiredFromSource$77(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFullWifiLockAcquiredFromSourceLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteFullWifiLockReleasedFromSource(android.os.WorkSource workSource) {
        super.noteFullWifiLockReleasedFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteFullWifiLockReleasedFromSource$78(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteFullWifiLockReleasedFromSource$78(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteFullWifiLockReleasedFromSourceLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiScanStartedFromSource(android.os.WorkSource workSource) {
        super.noteWifiScanStartedFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiScanStartedFromSource$79(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiScanStartedFromSource$79(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiScanStartedFromSourceLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiScanStoppedFromSource(android.os.WorkSource workSource) {
        super.noteWifiScanStoppedFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda68
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiScanStoppedFromSource$80(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiScanStoppedFromSource$80(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiScanStoppedFromSourceLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiBatchedScanStartedFromSource(android.os.WorkSource workSource, final int i) {
        super.noteWifiBatchedScanStartedFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda104
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiBatchedScanStartedFromSource$81(workSource2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiBatchedScanStartedFromSource$81(android.os.WorkSource workSource, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiBatchedScanStartedFromSourceLocked(workSource, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiBatchedScanStoppedFromSource(android.os.WorkSource workSource) {
        super.noteWifiBatchedScanStoppedFromSource_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiBatchedScanStoppedFromSource$82(workSource2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiBatchedScanStoppedFromSource$82(android.os.WorkSource workSource, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteWifiBatchedScanStoppedFromSourceLocked(workSource, j, j2);
        }
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.NETWORK_STACK", "android.permission.MAINLINE_NETWORK_STACK"})
    public void noteNetworkInterfaceForTransports(final java.lang.String str, final int[] iArr) {
        super.noteNetworkInterfaceForTransports_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda95
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteNetworkInterfaceForTransports$83(str, iArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteNetworkInterfaceForTransports$83(java.lang.String str, int[] iArr) {
        this.mStats.noteNetworkInterfaceForTransports(str, iArr);
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteNetworkStatsEnabled() {
        super.noteNetworkStatsEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda69
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteNetworkStatsEnabled$84();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteNetworkStatsEnabled$84() {
        this.mWorker.scheduleSync("network-stats-enabled", 6);
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteDeviceIdleMode(final int i, final java.lang.String str, final int i2) {
        super.noteDeviceIdleMode_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda71
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteDeviceIdleMode$85(i, str, i2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteDeviceIdleMode$85(int i, java.lang.String str, int i2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteDeviceIdleModeLocked(i, str, i2, j, j2);
        }
    }

    public void notePackageInstalled(final java.lang.String str, final long j) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePackageInstalled$86(str, j, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePackageInstalled$86(java.lang.String str, long j, long j2, long j3) {
        synchronized (this.mStats) {
            this.mStats.notePackageInstalledLocked(str, j, j2, j3);
        }
    }

    public void notePackageUninstalled(final java.lang.String str) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda84
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$notePackageUninstalled$87(str, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notePackageUninstalled$87(java.lang.String str, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.notePackageUninstalledLocked(str, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteBleScanStarted(android.os.WorkSource workSource, final boolean z) {
        super.noteBleScanStarted_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteBleScanStarted$88(workSource2, z, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteBleScanStarted$88(android.os.WorkSource workSource, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteBluetoothScanStartedFromSourceLocked(workSource, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteBleScanStopped(android.os.WorkSource workSource, final boolean z) {
        super.noteBleScanStopped_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda94
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteBleScanStopped$89(workSource2, z, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteBleScanStopped$89(android.os.WorkSource workSource, boolean z, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteBluetoothScanStoppedFromSourceLocked(workSource, z, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteBleScanReset() {
        super.noteBleScanReset_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteBleScanReset$90(elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteBleScanReset$90(long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteResetBluetoothScanLocked(j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteBleScanResults(android.os.WorkSource workSource, final int i) {
        super.noteBleScanResults_enforcePermission();
        final android.os.WorkSource workSource2 = workSource != null ? new android.os.WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda72
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteBleScanResults$91(workSource2, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteBleScanResults$91(android.os.WorkSource workSource, int i, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.noteBluetoothScanResultsFromSourceLocked(workSource, i, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteWifiControllerActivity(final android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        super.noteWifiControllerActivity_enforcePermission();
        if (wifiActivityEnergyInfo == null || !wifiActivityEnergyInfo.isValid()) {
            android.util.Slog.e(TAG, "invalid wifi data given: " + wifiActivityEnergyInfo);
            return;
        }
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            final android.app.usage.NetworkStatsManager networkStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda48
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteWifiControllerActivity$92(wifiActivityEnergyInfo, elapsedRealtime, uptimeMillis, networkStatsManager);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteWifiControllerActivity$92(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo, long j, long j2, android.app.usage.NetworkStatsManager networkStatsManager) {
        this.mStats.updateWifiState(wifiActivityEnergyInfo, -1L, j, j2, networkStatsManager);
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteBluetoothControllerActivity(final android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
        super.noteBluetoothControllerActivity_enforcePermission();
        if (bluetoothActivityEnergyInfo == null || !bluetoothActivityEnergyInfo.isValid()) {
            android.util.Slog.e(TAG, "invalid bluetooth data given: " + bluetoothActivityEnergyInfo);
            return;
        }
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteBluetoothControllerActivity$93(bluetoothActivityEnergyInfo, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteBluetoothControllerActivity$93(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.updateBluetoothStateLocked(bluetoothActivityEnergyInfo, -1L, j, j2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void noteModemControllerActivity(final android.telephony.ModemActivityInfo modemActivityInfo) {
        super.noteModemControllerActivity_enforcePermission();
        if (modemActivityInfo == null) {
            android.util.Slog.e(TAG, "invalid modem data given: " + modemActivityInfo);
            return;
        }
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            final android.app.usage.NetworkStatsManager networkStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteModemControllerActivity$94(modemActivityInfo, elapsedRealtime, uptimeMillis, networkStatsManager);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteModemControllerActivity$94(android.telephony.ModemActivityInfo modemActivityInfo, long j, long j2, android.app.usage.NetworkStatsManager networkStatsManager) {
        this.mStats.noteModemControllerActivity(modemActivityInfo, -1L, j, j2, networkStatsManager);
    }

    public boolean isOnBattery() {
        return this.mStats.isOnBattery();
    }

    @android.annotation.EnforcePermission("android.permission.UPDATE_DEVICE_STATS")
    public void setBatteryState(final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final long j) {
        super.setBatteryState_enforcePermission();
        synchronized (this.mLock) {
            try {
                try {
                    final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    final long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    final long currentTimeMillis = java.lang.System.currentTimeMillis();
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.BatteryStatsService.this.lambda$setBatteryState$97(i3, i, i2, i4, i5, i6, i7, i8, j, elapsedRealtime, uptimeMillis, currentTimeMillis);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBatteryState$97(final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final long j, final long j2, final long j3, final long j4) {
        this.mWorker.scheduleRunnable(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda87
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.BatteryStatsService.this.lambda$setBatteryState$96(i, i2, i3, i4, i5, i6, i7, i8, j, j2, j3, j4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBatteryState$96(final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final long j, final long j2, final long j3, final long j4) {
        synchronized (this.mStats) {
            try {
                if (this.mStats.isOnBattery() == com.android.server.power.stats.BatteryStatsImpl.isOnBattery(i, i2)) {
                    this.mStats.setBatteryStateLocked(i2, i3, i, i4, i5, i6, i7, i8, j, j2, j3, j4);
                } else {
                    this.mWorker.scheduleSync("battery-state", 127);
                    this.mWorker.scheduleRunnable(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda90
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.BatteryStatsService.this.lambda$setBatteryState$95(i2, i3, i, i4, i5, i6, i7, i8, j, j2, j3, j4);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBatteryState$95(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, long j2, long j3, long j4) {
        synchronized (this.mStats) {
            this.mStats.setBatteryStateLocked(i, i2, i3, i4, i5, i6, i7, i8, j, j2, j3, j4);
        }
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public long getAwakeTimeBattery() {
        super.getAwakeTimeBattery_enforcePermission();
        return this.mStats.getAwakeTimeBattery();
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public long getAwakeTimePlugged() {
        super.getAwakeTimePlugged_enforcePermission();
        return this.mStats.getAwakeTimePlugged();
    }

    final class WakeupReasonThread extends java.lang.Thread {
        private static final int MAX_REASON_SIZE = 512;
        private java.nio.charset.CharsetDecoder mDecoder;
        private java.nio.CharBuffer mUtf16Buffer;
        private java.nio.ByteBuffer mUtf8Buffer;

        WakeupReasonThread() {
            super("BatteryStats_wakeupReason");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.Process.setThreadPriority(-2);
            this.mDecoder = java.nio.charset.StandardCharsets.UTF_8.newDecoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE).replaceWith("?");
            this.mUtf8Buffer = java.nio.ByteBuffer.allocateDirect(512);
            this.mUtf16Buffer = java.nio.CharBuffer.allocate(512);
            while (true) {
                try {
                    java.lang.String waitWakeup = waitWakeup();
                    if (waitWakeup != null) {
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        android.os.Trace.instantForTrack(131072L, com.android.server.am.BatteryStatsService.TRACE_TRACK_WAKEUP_REASON, elapsedRealtime + " " + waitWakeup);
                        com.android.server.am.BatteryStatsService.this.awaitCompletion();
                        com.android.server.am.BatteryStatsService.this.mCpuWakeupStats.noteWakeupTimeAndReason(elapsedRealtime, uptimeMillis, waitWakeup);
                        synchronized (com.android.server.am.BatteryStatsService.this.mStats) {
                            com.android.server.am.BatteryStatsService.this.mStats.noteWakeupReasonLocked(waitWakeup, elapsedRealtime, uptimeMillis);
                        }
                    } else {
                        return;
                    }
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.e(com.android.server.am.BatteryStatsService.TAG, "Failure reading wakeup reasons", e);
                    return;
                }
            }
        }

        private java.lang.String waitWakeup() {
            this.mUtf8Buffer.clear();
            this.mUtf16Buffer.clear();
            this.mDecoder.reset();
            int nativeWaitWakeup = com.android.server.am.BatteryStatsService.nativeWaitWakeup(this.mUtf8Buffer);
            if (nativeWaitWakeup < 0) {
                return null;
            }
            if (nativeWaitWakeup == 0) {
                return "unknown";
            }
            this.mUtf8Buffer.limit(nativeWaitWakeup);
            this.mDecoder.decode(this.mUtf8Buffer, this.mUtf16Buffer, true);
            this.mUtf16Buffer.flip();
            return this.mUtf16Buffer.toString();
        }
    }

    private void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Battery stats (batterystats) dump options:");
        printWriter.println("  [--checkin] [--proto] [--history] [--history-start] [--charged] [-c]");
        printWriter.println("  [--daily] [--reset] [--reset-all] [--write] [--new-daily] [--read-daily]");
        printWriter.println("  [-h] [<package.name>]");
        printWriter.println("  --checkin: generate output for a checkin report; will write (and clear) the");
        printWriter.println("             last old completed stats when they had been reset.");
        printWriter.println("  -c: write the current stats in checkin format.");
        printWriter.println("  --proto: write the current aggregate stats (without history) in proto format.");
        printWriter.println("  --history: show only history data.");
        printWriter.println("  --history-start <num>: show only history data starting at given time offset.");
        printWriter.println("  --history-create-events <num>: create <num> of battery history events.");
        printWriter.println("  --charged: only output data since last charged.");
        printWriter.println("  --daily: only output full daily data.");
        printWriter.println("  --reset: reset the stats, clearing all current data.");
        printWriter.println("  --reset-all: reset the stats, clearing all current and past data.");
        printWriter.println("  --write: force write current collected stats to disk.");
        printWriter.println("  --new-daily: immediately create and write new daily stats record.");
        printWriter.println("  --read-daily: read-load last written daily stats.");
        printWriter.println("  --settings: dump the settings key/values related to batterystats");
        printWriter.println("  --cpu: dump cpu stats for debugging purpose");
        printWriter.println("  --wakeups: dump CPU wakeup history and attribution.");
        printWriter.println("  --power-profile: dump the power profile constants");
        printWriter.println("  --usage: write battery usage stats. Optional arguments:");
        printWriter.println("     --proto: output as a binary protobuffer");
        printWriter.println("     --model power-profile: use the power profile model even if measured energy is available");
        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        printWriter.println("  <package.name>: optional name of package to filter output by.");
        printWriter.println("  -h: print this help text.");
        printWriter.println("Battery stats (batterystats) commands:");
        printWriter.println("  enable|disable <option>");
        printWriter.println("    Enable or disable a running option.  Option state is not saved across boots.");
        printWriter.println("    Options are:");
        printWriter.println("      full-history: include additional detailed events in battery history:");
        printWriter.println("          wake_lock_in, alarms and proc events");
        printWriter.println("      no-auto-reset: don't automatically reset stats when unplugged");
        printWriter.println("      pretend-screen-off: pretend the screen is off, even if screen state changes");
    }

    private void dumpSettings(java.io.PrintWriter printWriter) {
        awaitCompletion();
        synchronized (this.mStats) {
            this.mStats.dumpConstantsLocked(printWriter);
            printWriter.println("Flags:");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("    com.android.server.power.optimization.streamlined_battery_stats: ");
            com.android.server.power.optimization.Flags.streamlinedBatteryStats();
            sb.append(false);
            printWriter.println(sb.toString());
        }
    }

    private void dumpCpuStats(java.io.PrintWriter printWriter) {
        awaitCompletion();
        synchronized (this.mStats) {
            this.mStats.dumpCpuStatsLocked(printWriter);
        }
    }

    private void dumpStatsSample(java.io.PrintWriter printWriter) {
        this.mStats.dumpStatsSample(printWriter);
    }

    private void dumpAggregatedStats(java.io.PrintWriter printWriter) {
        this.mPowerStatsScheduler.aggregateAndDumpPowerStats(printWriter);
    }

    private void dumpPowerStatsStore(java.io.PrintWriter printWriter) {
        this.mPowerStatsStore.dump(new android.util.IndentingPrintWriter(printWriter, "  "));
    }

    private void dumpPowerStatsStoreTableOfContents(java.io.PrintWriter printWriter) {
        this.mPowerStatsStore.dumpTableOfContents(new android.util.IndentingPrintWriter(printWriter, "  "));
    }

    private void dumpMeasuredEnergyStats(java.io.PrintWriter printWriter) {
        awaitCompletion();
        syncStats("dump", 127);
        synchronized (this.mStats) {
            this.mStats.dumpEnergyConsumerStatsLocked(printWriter);
        }
    }

    private void dumpPowerProfile(java.io.PrintWriter printWriter) {
        synchronized (this.mStats) {
            this.mStats.dumpPowerProfileLocked(printWriter);
        }
    }

    private void dumpUsageStats(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, int i, boolean z) {
        awaitCompletion();
        syncStats("dump", 127);
        android.os.BatteryUsageStatsQuery.Builder includePowerModels = new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includeProcessStateData().includePowerModels();
        if (i == 1) {
            includePowerModels.powerProfileModeledOnly();
        }
        android.os.BatteryUsageStatsQuery build = includePowerModels.build();
        synchronized (this.mStats) {
            this.mStats.prepareForDumpLocked();
        }
        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
        android.os.BatteryUsageStats batteryUsageStats = this.mBatteryUsageStatsProvider.getBatteryUsageStats(this.mStats, build);
        if (z) {
            batteryUsageStats.dumpToProto(fileDescriptor);
        } else {
            batteryUsageStats.dump(printWriter, "");
        }
    }

    private int doEnableOrDisable(java.io.PrintWriter printWriter, int i, java.lang.String[] strArr, boolean z) {
        int i2 = i + 1;
        if (i2 >= strArr.length) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Missing option argument for ");
            sb.append(z ? "--enable" : "--disable");
            printWriter.println(sb.toString());
            dumpHelp(printWriter);
            return -1;
        }
        if ("full-wake-history".equals(strArr[i2]) || "full-history".equals(strArr[i2])) {
            awaitCompletion();
            synchronized (this.mStats) {
                this.mStats.setRecordAllHistoryLocked(z);
            }
        } else if ("no-auto-reset".equals(strArr[i2])) {
            awaitCompletion();
            synchronized (this.mStats) {
                this.mStats.setNoAutoReset(z);
            }
        } else if ("pretend-screen-off".equals(strArr[i2])) {
            awaitCompletion();
            synchronized (this.mStats) {
                this.mStats.setPretendScreenOff(z);
            }
        } else {
            printWriter.println("Unknown enable/disable option: " + strArr[i2]);
            dumpHelp(printWriter);
            return -1;
        }
        return i2;
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mMonitorEnabled = false;
        try {
            dumpUnmonitored(fileDescriptor, printWriter, strArr);
        } finally {
            this.mMonitorEnabled = true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void dumpUnmonitored(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        long j;
        int i;
        boolean z;
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i3;
        char c;
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            long j2 = -1;
            if (strArr == null) {
                j = -1;
                i = -1;
                z = false;
                i2 = 0;
                z2 = false;
                z3 = false;
                z4 = false;
                z5 = false;
            } else {
                int i4 = 0;
                z = false;
                i2 = 0;
                int i5 = -1;
                z2 = false;
                z3 = false;
                z4 = false;
                boolean z6 = false;
                while (i4 < strArr.length) {
                    java.lang.String str = strArr[i4];
                    if ("--checkin".equals(str)) {
                        z2 = true;
                        z4 = true;
                    } else if ("--history".equals(str)) {
                        i2 |= 8;
                    } else if ("--history-start".equals(str)) {
                        i2 |= 8;
                        i4++;
                        if (i4 >= strArr.length) {
                            printWriter.println("Missing time argument for --history-since");
                            dumpHelp(printWriter);
                            return;
                        } else {
                            j2 = com.android.internal.util.ParseUtils.parseLong(strArr[i4], 0L);
                            z6 = true;
                        }
                    } else if ("--history-create-events".equals(str)) {
                        i4++;
                        if (i4 >= strArr.length) {
                            printWriter.println("Missing events argument for --history-create-events");
                            dumpHelp(printWriter);
                            return;
                        }
                        long parseLong = com.android.internal.util.ParseUtils.parseLong(strArr[i4], 0L);
                        awaitCompletion();
                        synchronized (this.mStats) {
                            this.mStats.createFakeHistoryEvents(parseLong);
                            printWriter.println("Battery history create events started.");
                        }
                        z = true;
                    } else if ("-c".equals(str)) {
                        i2 |= 16;
                        z2 = true;
                    } else if ("--proto".equals(str)) {
                        z3 = true;
                    } else if ("--charged".equals(str)) {
                        i2 |= 2;
                    } else if ("--daily".equals(str)) {
                        i2 |= 4;
                    } else if ("--reset-all".equals(str)) {
                        awaitCompletion();
                        synchronized (this.mStats) {
                            this.mStats.resetAllStatsAndHistoryLocked(2);
                            this.mPowerStatsStore.reset();
                            printWriter.println("Battery stats and history reset.");
                        }
                        z = true;
                    } else if ("--reset".equals(str)) {
                        awaitCompletion();
                        synchronized (this.mStats) {
                            this.mStats.resetAllStatsAndHistoryLocked(2);
                            printWriter.println("Battery stats reset.");
                        }
                        z = true;
                    } else if ("--write".equals(str)) {
                        awaitCompletion();
                        syncStats("dump", 127);
                        synchronized (this.mStats) {
                            this.mStats.writeSyncLocked();
                            printWriter.println("Battery stats written.");
                        }
                        z = true;
                    } else if ("--new-daily".equals(str)) {
                        awaitCompletion();
                        synchronized (this.mStats) {
                            this.mStats.recordDailyStatsLocked();
                            printWriter.println("New daily stats written.");
                        }
                        z = true;
                    } else if ("--read-daily".equals(str)) {
                        awaitCompletion();
                        synchronized (this.mStats) {
                            this.mStats.readDailyStatsLocked();
                            printWriter.println("Last daily stats read.");
                        }
                        z = true;
                    } else {
                        if ("--enable".equals(str) || "enable".equals(str)) {
                            int doEnableOrDisable = doEnableOrDisable(printWriter, i4, strArr, true);
                            if (doEnableOrDisable < 0) {
                                return;
                            }
                            printWriter.println("Enabled: " + strArr[doEnableOrDisable]);
                            return;
                        }
                        if ("--disable".equals(str) || "disable".equals(str)) {
                            int doEnableOrDisable2 = doEnableOrDisable(printWriter, i4, strArr, false);
                            if (doEnableOrDisable2 < 0) {
                                return;
                            }
                            printWriter.println("Disabled: " + strArr[doEnableOrDisable2]);
                            return;
                        }
                        if ("-h".equals(str)) {
                            dumpHelp(printWriter);
                            return;
                        }
                        if ("--settings".equals(str)) {
                            dumpSettings(printWriter);
                            return;
                        }
                        if ("--cpu".equals(str)) {
                            dumpCpuStats(printWriter);
                            return;
                        }
                        if ("--measured-energy".equals(str)) {
                            dumpMeasuredEnergyStats(printWriter);
                            return;
                        }
                        if ("--power-profile".equals(str)) {
                            dumpPowerProfile(printWriter);
                            return;
                        }
                        if ("--usage".equals(str)) {
                            int i6 = i4 + 1;
                            int i7 = 0;
                            boolean z7 = false;
                            while (i6 < strArr.length) {
                                java.lang.String str2 = strArr[i6];
                                switch (str2.hashCode()) {
                                    case -1619625591:
                                        if (str2.equals("--model")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1616754616:
                                        if (str2.equals("--proto")) {
                                            c = 0;
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
                                        z7 = true;
                                        break;
                                    case 1:
                                        i6++;
                                        if (i6 < strArr.length) {
                                            if ("power-profile".equals(strArr[i6])) {
                                                i7 = 1;
                                                break;
                                            } else {
                                                printWriter.println("Unknown power model: " + strArr[i6]);
                                                dumpHelp(printWriter);
                                                return;
                                            }
                                        } else {
                                            printWriter.println("--model without a value");
                                            dumpHelp(printWriter);
                                            return;
                                        }
                                }
                                i6++;
                            }
                            dumpUsageStats(fileDescriptor, printWriter, i7, z7);
                            return;
                        }
                        if ("--wakeups".equals(str)) {
                            this.mCpuWakeupStats.dump(new android.util.IndentingPrintWriter(printWriter, "  "), android.os.SystemClock.elapsedRealtime());
                            return;
                        }
                        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
                        com.android.server.power.optimization.Flags.streamlinedBatteryStats();
                        if ("--store".equals(str)) {
                            dumpPowerStatsStore(printWriter);
                            return;
                        }
                        if ("--store-toc".equals(str)) {
                            dumpPowerStatsStoreTableOfContents(printWriter);
                            return;
                        }
                        if ("-a".equals(str)) {
                            i2 |= 32;
                        } else {
                            if (str.length() > 0 && str.charAt(0) == '-') {
                                printWriter.println("Unknown option: " + str);
                                dumpHelp(printWriter);
                                return;
                            }
                            try {
                                i5 = this.mContext.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getCallingUserId());
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                printWriter.println("Unknown package: " + str);
                                dumpHelp(printWriter);
                                return;
                            }
                        }
                    }
                    i4++;
                }
                j = j2;
                i = i5;
                z5 = z6;
            }
            if (z) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (android.os.BatteryStats.checkWifiOnly(this.mContext)) {
                    i2 |= 64;
                }
                awaitCompletion();
                syncStats("dump", 127);
                if (i >= 0 && (i2 & 10) == 0) {
                    i3 = (i2 | 2) & (-17);
                } else {
                    i3 = i2;
                }
                if (z3) {
                    java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mContext.getPackageManager().getInstalledApplications(4325376);
                    if (z4) {
                        synchronized (this.mStats.mCheckinFile) {
                            try {
                                if (this.mStats.mCheckinFile.exists()) {
                                    byte[] readFully = this.mStats.mCheckinFile.readFully();
                                    if (readFully != null) {
                                        android.os.Parcel obtain = android.os.Parcel.obtain();
                                        obtain.unmarshall(readFully, 0, readFully.length);
                                        obtain.setDataPosition(0);
                                        com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl = new com.android.server.power.stats.BatteryStatsImpl(this.mBatteryStatsConfig, com.android.internal.os.Clock.SYSTEM_CLOCK, this.mMonotonicClock, null, this.mStats.mHandler, null, null, this.mUserManagerUserInfoProvider, this.mPowerProfile, this.mCpuScalingPolicies, new com.android.server.power.stats.PowerStatsUidResolver());
                                        batteryStatsImpl.readSummaryFromParcel(obtain);
                                        obtain.recycle();
                                        batteryStatsImpl.dumpProtoLocked(this.mContext, fileDescriptor, installedApplications, i3, j, this.mDumpHelper);
                                        this.mStats.mCheckinFile.delete();
                                        return;
                                    }
                                }
                            } catch (android.os.ParcelFormatException | java.io.IOException e2) {
                                android.util.Slog.w(TAG, "Failure reading checkin file " + this.mStats.mCheckinFile.getBaseFile(), e2);
                            } finally {
                            }
                        }
                    }
                    awaitCompletion();
                    synchronized (this.mStats) {
                        try {
                            this.mStats.dumpProtoLocked(this.mContext, fileDescriptor, installedApplications, i3, j, this.mDumpHelper);
                            if (z5) {
                                this.mStats.writeAsyncLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if (z2) {
                    java.util.List<android.content.pm.ApplicationInfo> installedApplications2 = this.mContext.getPackageManager().getInstalledApplications(4325376);
                    if (z4) {
                        synchronized (this.mStats.mCheckinFile) {
                            try {
                                if (this.mStats.mCheckinFile.exists()) {
                                    byte[] readFully2 = this.mStats.mCheckinFile.readFully();
                                    if (readFully2 != null) {
                                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                                        obtain2.unmarshall(readFully2, 0, readFully2.length);
                                        obtain2.setDataPosition(0);
                                        com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl2 = new com.android.server.power.stats.BatteryStatsImpl(this.mBatteryStatsConfig, com.android.internal.os.Clock.SYSTEM_CLOCK, this.mMonotonicClock, null, this.mStats.mHandler, null, null, this.mUserManagerUserInfoProvider, this.mPowerProfile, this.mCpuScalingPolicies, new com.android.server.power.stats.PowerStatsUidResolver());
                                        batteryStatsImpl2.readSummaryFromParcel(obtain2);
                                        obtain2.recycle();
                                        batteryStatsImpl2.dumpCheckin(this.mContext, printWriter, installedApplications2, i3, j, this.mDumpHelper);
                                        this.mStats.mCheckinFile.delete();
                                        return;
                                    }
                                }
                            } catch (android.os.ParcelFormatException | java.io.IOException e3) {
                                android.util.Slog.w(TAG, "Failure reading checkin file " + this.mStats.mCheckinFile.getBaseFile(), e3);
                            } finally {
                            }
                        }
                    }
                    awaitCompletion();
                    this.mStats.dumpCheckin(this.mContext, printWriter, installedApplications2, i3, j, this.mDumpHelper);
                    if (z5) {
                        this.mStats.writeAsyncLocked();
                        return;
                    }
                    return;
                }
                awaitCompletion();
                this.mStats.dump(this.mContext, printWriter, i3, i, j, this.mDumpHelper);
                if (z5) {
                    this.mStats.writeAsyncLocked();
                }
                printWriter.println();
                this.mCpuWakeupStats.dump(new android.util.IndentingPrintWriter(printWriter, "  "), android.os.SystemClock.elapsedRealtime());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.UPDATE_DEVICE_STATS", "android.permission.BATTERY_STATS"})
    public android.os.connectivity.CellularBatteryStats getCellularBatteryStats() {
        android.os.connectivity.CellularBatteryStats cellularBatteryStats;
        super.getCellularBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            cellularBatteryStats = this.mStats.getCellularBatteryStats();
        }
        return cellularBatteryStats;
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.UPDATE_DEVICE_STATS", "android.permission.BATTERY_STATS"})
    public android.os.connectivity.WifiBatteryStats getWifiBatteryStats() {
        android.os.connectivity.WifiBatteryStats wifiBatteryStats;
        super.getWifiBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            wifiBatteryStats = this.mStats.getWifiBatteryStats();
        }
        return wifiBatteryStats;
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public android.os.connectivity.GpsBatteryStats getGpsBatteryStats() {
        android.os.connectivity.GpsBatteryStats gpsBatteryStats;
        super.getGpsBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            gpsBatteryStats = this.mStats.getGpsBatteryStats();
        }
        return gpsBatteryStats;
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public android.os.WakeLockStats getWakeLockStats() {
        android.os.WakeLockStats wakeLockStats;
        super.getWakeLockStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            wakeLockStats = this.mStats.getWakeLockStats();
        }
        return wakeLockStats;
    }

    @android.annotation.EnforcePermission("android.permission.BATTERY_STATS")
    public android.os.BluetoothBatteryStats getBluetoothBatteryStats() {
        android.os.BluetoothBatteryStats bluetoothBatteryStats;
        super.getBluetoothBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            bluetoothBatteryStats = this.mStats.getBluetoothBatteryStats();
        }
        return bluetoothBatteryStats;
    }

    public android.os.health.HealthStatsParceler takeUidSnapshot(int i) {
        android.os.health.HealthStatsParceler healthStatsForUidLocked;
        if (i != android.os.Binder.getCallingUid()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                awaitCompletion();
                if (shouldCollectExternalStats()) {
                    syncStats("get-health-stats-for-uids", 127);
                }
                synchronized (this.mStats) {
                    healthStatsForUidLocked = getHealthStatsForUidLocked(i);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return healthStatsForUidLocked;
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Crashed while writing for takeUidSnapshot(" + i + ")", e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.os.health.HealthStatsParceler[] takeUidSnapshots(int[] iArr) {
        android.os.health.HealthStatsParceler[] healthStatsParcelerArr;
        if (!onlyCaller(iArr)) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                awaitCompletion();
                if (shouldCollectExternalStats()) {
                    syncStats("get-health-stats-for-uids", 127);
                }
                synchronized (this.mStats) {
                    try {
                        int length = iArr.length;
                        healthStatsParcelerArr = new android.os.health.HealthStatsParceler[length];
                        for (int i = 0; i < length; i++) {
                            healthStatsParcelerArr[i] = getHealthStatsForUidLocked(iArr[i]);
                        }
                    } finally {
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return healthStatsParcelerArr;
            } catch (java.lang.Exception e) {
                throw e;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean shouldCollectExternalStats() {
        return android.os.SystemClock.elapsedRealtime() - this.mWorker.getLastCollectionTimeStamp() > this.mStats.getExternalStatsCollectionRateLimitMs();
    }

    private static boolean onlyCaller(int[] iArr) {
        int callingUid = android.os.Binder.getCallingUid();
        for (int i : iArr) {
            if (i != callingUid) {
                return false;
            }
        }
        return true;
    }

    android.os.health.HealthStatsParceler getHealthStatsForUidLocked(int i) {
        com.android.server.am.HealthStatsBatteryStatsWriter healthStatsBatteryStatsWriter = new com.android.server.am.HealthStatsBatteryStatsWriter();
        android.os.health.HealthStatsWriter healthStatsWriter = new android.os.health.HealthStatsWriter(android.os.health.UidHealthStats.CONSTANTS);
        android.os.BatteryStats.Uid uid = this.mStats.getUidStats().get(i);
        if (uid != null) {
            healthStatsBatteryStatsWriter.writeUid(healthStatsWriter, this.mStats, uid);
        }
        return new android.os.health.HealthStatsParceler(healthStatsWriter);
    }

    @android.annotation.EnforcePermission("android.permission.POWER_SAVER")
    public boolean setChargingStateUpdateDelayMillis(int i) {
        super.setChargingStateUpdateDelayMillis_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.Settings.Global.putLong(this.mContext.getContentResolver(), "battery_charging_state_update_delay", i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void updateForegroundTimeIfOnBattery(final java.lang.String str, final int i, final long j) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$updateForegroundTimeIfOnBattery$98(i, str, elapsedRealtime, uptimeMillis, j);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateForegroundTimeIfOnBattery$98(int i, java.lang.String str, long j, long j2, long j3) {
        if (!isOnBattery()) {
            return;
        }
        synchronized (this.mStats) {
            try {
                com.android.server.power.stats.BatteryStatsImpl.Uid.Proc processStatsLocked = this.mStats.getProcessStatsLocked(i, str, j, j2);
                if (processStatsLocked != null) {
                    processStatsLocked.addForegroundTimeLocked(j3);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void noteCurrentTimeChanged() {
        synchronized (this.mLock) {
            final long currentTimeMillis = java.lang.System.currentTimeMillis();
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteCurrentTimeChanged$99(currentTimeMillis, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteCurrentTimeChanged$99(long j, long j2, long j3) {
        synchronized (this.mStats) {
            this.mStats.noteCurrentTimeChangedLocked(j, j2, j3);
        }
    }

    void updateBatteryStatsOnActivityUsage(java.lang.String str, java.lang.String str2, final int i, int i2, final boolean z) {
        int i3;
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$updateBatteryStatsOnActivityUsage$100(z, i, elapsedRealtime, uptimeMillis);
                }
            });
        }
        if (z) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        com.android.internal.util.FrameworkStatsLog.write(42, i, str, str2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBatteryStatsOnActivityUsage$100(boolean z, int i, long j, long j2) {
        synchronized (this.mStats) {
            try {
                if (z) {
                    this.mStats.noteActivityResumedLocked(i, j, j2);
                } else {
                    this.mStats.noteActivityPausedLocked(i, j, j2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void noteProcessDied(final int i, final int i2) {
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda76
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteProcessDied$101(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteProcessDied$101(int i, int i2) {
        synchronized (this.mStats) {
            this.mStats.noteProcessDiedLocked(i, i2);
        }
    }

    void reportExcessiveCpu(final int i, final java.lang.String str, final long j, final long j2) {
        synchronized (this.mLock) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda75
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$reportExcessiveCpu$102(i, str, j, j2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportExcessiveCpu$102(int i, java.lang.String str, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.reportExcessiveCpuLocked(i, str, j, j2);
        }
    }

    void noteServiceStartRunning(final int i, final java.lang.String str, final java.lang.String str2) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda103
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteServiceStartRunning$103(i, str, str2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteServiceStartRunning$103(int i, java.lang.String str, java.lang.String str2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.getServiceStatsLocked(i, str, str2, j, j2).startRunningLocked(j2);
        }
    }

    void noteServiceStopRunning(final int i, final java.lang.String str, final java.lang.String str2) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteServiceStopRunning$104(i, str, str2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteServiceStopRunning$104(int i, java.lang.String str, java.lang.String str2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.getServiceStatsLocked(i, str, str2, j, j2).stopRunningLocked(j2);
        }
    }

    void noteServiceStartLaunch(final int i, final java.lang.String str, final java.lang.String str2) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda105
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteServiceStartLaunch$105(i, str, str2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteServiceStartLaunch$105(int i, java.lang.String str, java.lang.String str2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.getServiceStatsLocked(i, str, str2, j, j2).startLaunchedLocked(j2);
        }
    }

    void noteServiceStopLaunch(final int i, final java.lang.String str, final java.lang.String str2) {
        synchronized (this.mLock) {
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            final long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.BatteryStatsService.this.lambda$noteServiceStopLaunch$106(i, str, str2, elapsedRealtime, uptimeMillis);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$noteServiceStopLaunch$106(int i, java.lang.String str, java.lang.String str2, long j, long j2) {
        synchronized (this.mStats) {
            this.mStats.getServiceStatsLocked(i, str, str2, j, j2).stopLaunchedLocked(j2);
        }
    }

    public void setLastBatteryUsageStatsBeforeResetAtomPullTimestamp(long j) {
        synchronized (this.mConfigFile) {
            java.util.Properties properties = new java.util.Properties();
            try {
                java.io.FileInputStream openRead = this.mConfigFile.openRead();
                try {
                    properties.load(openRead);
                    if (openRead != null) {
                        openRead.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Cannot load config file " + this.mConfigFile, e);
            }
            properties.put(BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP_PROPERTY, java.lang.String.valueOf(j));
            java.io.FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = this.mConfigFile.startWrite();
                properties.store(fileOutputStream, "Statsd atom pull timestamps");
                this.mConfigFile.finishWrite(fileOutputStream);
            } catch (java.io.IOException e2) {
                this.mConfigFile.failWrite(fileOutputStream);
                android.util.Slog.e(TAG, "Cannot save config file " + this.mConfigFile, e2);
            }
        }
    }

    public long getLastBatteryUsageStatsBeforeResetAtomPullTimestamp() {
        long parseLong;
        synchronized (this.mConfigFile) {
            java.util.Properties properties = new java.util.Properties();
            try {
                java.io.FileInputStream openRead = this.mConfigFile.openRead();
                try {
                    properties.load(openRead);
                    if (openRead != null) {
                        openRead.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Cannot load config file " + this.mConfigFile, e);
            }
            parseLong = java.lang.Long.parseLong(properties.getProperty(BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP_PROPERTY, "0"));
        }
        return parseLong;
    }

    @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
    public void setChargerAcOnline(boolean z, boolean z2) {
        super.setChargerAcOnline_enforcePermission();
        this.mBatteryManagerInternal.setChargerAcOnline(z, z2);
    }

    @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
    public void setBatteryLevel(int i, boolean z) {
        super.setBatteryLevel_enforcePermission();
        this.mBatteryManagerInternal.setBatteryLevel(i, z);
    }

    @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
    public void unplugBattery(boolean z) {
        super.unplugBattery_enforcePermission();
        this.mBatteryManagerInternal.unplugBattery(z);
    }

    @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
    public void resetBattery(boolean z) {
        super.resetBattery_enforcePermission();
        this.mBatteryManagerInternal.resetBattery(z);
    }

    @android.annotation.EnforcePermission("android.permission.DEVICE_POWER")
    public void suspendBatteryInput() {
        super.suspendBatteryInput_enforcePermission();
        this.mBatteryManagerInternal.suspendBatteryInput();
    }
}
