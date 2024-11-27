package com.android.server.powerstats;

/* loaded from: classes2.dex */
public class PowerStatsService extends com.android.server.SystemService {
    private static final java.lang.String DATA_STORAGE_SUBDIR = "powerstats";
    private static final int DATA_STORAGE_VERSION = 0;
    private static final boolean DEBUG = false;
    private static final double INTERVAL_RANDOM_NOISE_GENERATION_ALPHA = 50.0d;
    static final java.lang.String KEY_POWER_MONITOR_API_ENABLED = "power_monitor_api_enabled";
    private static final long MAX_POWER_MONITOR_AGE_MILLIS = 30000;
    private static final long MAX_RANDOM_NOISE_UWS = 10000000;
    private static final java.lang.String METER_CACHE_FILENAME = "meterCache";
    private static final java.lang.String METER_FILENAME = "log.powerstats.meter.0";
    private static final java.lang.String MODEL_CACHE_FILENAME = "modelCache";
    private static final java.lang.String MODEL_FILENAME = "log.powerstats.model.0";
    private static final java.lang.String RESIDENCY_CACHE_FILENAME = "residencyCache";
    private static final java.lang.String RESIDENCY_FILENAME = "log.powerstats.residency.0";
    private static final java.lang.String TAG = com.android.server.powerstats.PowerStatsService.class.getSimpleName();

    @android.annotation.Nullable
    private com.android.server.powerstats.BatteryTrigger mBatteryTrigger;
    private final com.android.internal.os.Clock mClock;
    private android.content.Context mContext;
    private java.io.File mDataStoragePath;
    private final android.provider.DeviceConfigInterface mDeviceConfig;
    private final com.android.server.powerstats.PowerStatsService.DeviceConfigListener mDeviceConfigListener;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.hardware.power.stats.EnergyConsumer[] mEnergyConsumers;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.hardware.power.stats.Channel[] mEnergyMeters;
    private android.os.Handler mHandler;
    private final com.android.server.powerstats.PowerStatsService.Injector mInjector;
    private com.android.server.powerstats.IntervalRandomNoiseGenerator mIntervalRandomNoiseGenerator;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.os.Looper mLooper;
    private boolean mPowerMonitorApiEnabled;
    private com.android.server.powerstats.PowerStatsService.PowerMonitorState[] mPowerMonitorStates;
    private volatile android.os.PowerMonitor[] mPowerMonitors;

    @android.annotation.Nullable
    private android.power.PowerStatsInternal mPowerStatsInternal;

    @android.annotation.Nullable
    private com.android.server.powerstats.PowerStatsLogger mPowerStatsLogger;

    @android.annotation.Nullable
    private com.android.server.powerstats.StatsPullAtomCallbackImpl mPullAtomCallback;
    private final android.os.IBinder mService;

    @android.annotation.Nullable
    private com.android.server.powerstats.TimerTrigger mTimerTrigger;

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {

        @com.android.internal.annotations.GuardedBy({"this"})
        private com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper mPowerStatsHALWrapper;

        Injector() {
        }

        com.android.internal.os.Clock getClock() {
            return com.android.internal.os.Clock.SYSTEM_CLOCK;
        }

        java.io.File createDataStoragePath() {
            return new java.io.File(android.os.Environment.getDataSystemDeDirectory(0), com.android.server.powerstats.PowerStatsService.DATA_STORAGE_SUBDIR);
        }

        java.lang.String createMeterFilename() {
            return com.android.server.powerstats.PowerStatsService.METER_FILENAME;
        }

        java.lang.String createModelFilename() {
            return com.android.server.powerstats.PowerStatsService.MODEL_FILENAME;
        }

        java.lang.String createResidencyFilename() {
            return com.android.server.powerstats.PowerStatsService.RESIDENCY_FILENAME;
        }

        java.lang.String createMeterCacheFilename() {
            return com.android.server.powerstats.PowerStatsService.METER_CACHE_FILENAME;
        }

        java.lang.String createModelCacheFilename() {
            return com.android.server.powerstats.PowerStatsService.MODEL_CACHE_FILENAME;
        }

        java.lang.String createResidencyCacheFilename() {
            return com.android.server.powerstats.PowerStatsService.RESIDENCY_CACHE_FILENAME;
        }

        com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper createPowerStatsHALWrapperImpl() {
            return com.android.server.powerstats.PowerStatsHALWrapper.getPowerStatsHalImpl();
        }

        com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper getPowerStatsHALWrapperImpl() {
            com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper iPowerStatsHALWrapper;
            synchronized (this) {
                try {
                    if (this.mPowerStatsHALWrapper == null) {
                        this.mPowerStatsHALWrapper = com.android.server.powerstats.PowerStatsHALWrapper.getPowerStatsHalImpl();
                    }
                    iPowerStatsHALWrapper = this.mPowerStatsHALWrapper;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return iPowerStatsHALWrapper;
        }

        com.android.server.powerstats.PowerStatsLogger createPowerStatsLogger(android.content.Context context, android.os.Looper looper, java.io.File file, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper iPowerStatsHALWrapper) {
            return new com.android.server.powerstats.PowerStatsLogger(context, looper, file, str, str2, str3, str4, str5, str6, iPowerStatsHALWrapper);
        }

        com.android.server.powerstats.BatteryTrigger createBatteryTrigger(android.content.Context context, com.android.server.powerstats.PowerStatsLogger powerStatsLogger) {
            return new com.android.server.powerstats.BatteryTrigger(context, powerStatsLogger, true);
        }

        com.android.server.powerstats.TimerTrigger createTimerTrigger(android.content.Context context, com.android.server.powerstats.PowerStatsLogger powerStatsLogger) {
            return new com.android.server.powerstats.TimerTrigger(context, powerStatsLogger, true);
        }

        com.android.server.powerstats.StatsPullAtomCallbackImpl createStatsPullerImpl(android.content.Context context, android.power.PowerStatsInternal powerStatsInternal) {
            return new com.android.server.powerstats.StatsPullAtomCallbackImpl(context, powerStatsInternal);
        }

        android.provider.DeviceConfigInterface getDeviceConfig() {
            return android.provider.DeviceConfigInterface.REAL;
        }

        com.android.server.powerstats.IntervalRandomNoiseGenerator createIntervalRandomNoiseGenerator() {
            return new com.android.server.powerstats.IntervalRandomNoiseGenerator(com.android.server.powerstats.PowerStatsService.INTERVAL_RANDOM_NOISE_GENERATION_ALPHA);
        }
    }

    /* renamed from: com.android.server.powerstats.PowerStatsService$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.IPowerStatsService.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSupportedPowerMonitors$0(android.os.ResultReceiver resultReceiver) {
            com.android.server.powerstats.PowerStatsService.this.getSupportedPowerMonitorsImpl(resultReceiver);
        }

        public void getSupportedPowerMonitors(final android.os.ResultReceiver resultReceiver) {
            com.android.server.powerstats.PowerStatsService.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.powerstats.PowerStatsService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.powerstats.PowerStatsService.AnonymousClass1.this.lambda$getSupportedPowerMonitors$0(resultReceiver);
                }
            });
        }

        public void getPowerMonitorReadings(final int[] iArr, final android.os.ResultReceiver resultReceiver) {
            final int callingUid = android.os.Binder.getCallingUid();
            com.android.server.powerstats.PowerStatsService.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.powerstats.PowerStatsService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.powerstats.PowerStatsService.AnonymousClass1.this.lambda$getPowerMonitorReadings$1(iArr, resultReceiver, callingUid);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getPowerMonitorReadings$1(int[] iArr, android.os.ResultReceiver resultReceiver, int i) {
            com.android.server.powerstats.PowerStatsService.this.getPowerMonitorReadingsImpl(iArr, resultReceiver, i);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.powerstats.PowerStatsService.this.mContext, com.android.server.powerstats.PowerStatsService.TAG, printWriter)) {
                if (com.android.server.powerstats.PowerStatsService.this.mPowerStatsLogger == null) {
                    android.util.Slog.e(com.android.server.powerstats.PowerStatsService.TAG, "PowerStats HAL is not initialized.  No data available.");
                    return;
                }
                if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    if ("model".equals(strArr[1])) {
                        com.android.server.powerstats.PowerStatsService.this.mPowerStatsLogger.writeModelDataToFile(fileDescriptor);
                        return;
                    } else if ("meter".equals(strArr[1])) {
                        com.android.server.powerstats.PowerStatsService.this.mPowerStatsLogger.writeMeterDataToFile(fileDescriptor);
                        return;
                    } else {
                        if ("residency".equals(strArr[1])) {
                            com.android.server.powerstats.PowerStatsService.this.mPowerStatsLogger.writeResidencyDataToFile(fileDescriptor);
                            return;
                        }
                        return;
                    }
                }
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
                indentingPrintWriter.println("PowerStatsService dumpsys: available PowerEntities");
                android.hardware.power.stats.PowerEntity[] powerEntityInfo = com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getPowerEntityInfo();
                indentingPrintWriter.increaseIndent();
                com.android.server.powerstats.ProtoStreamUtils.PowerEntityUtils.dumpsys(powerEntityInfo, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: available Channels");
                android.hardware.power.stats.Channel[] energyMeterInfo = com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getEnergyMeterInfo();
                indentingPrintWriter.increaseIndent();
                com.android.server.powerstats.ProtoStreamUtils.ChannelUtils.dumpsys(energyMeterInfo, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: available EnergyConsumers");
                android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
                indentingPrintWriter.increaseIndent();
                com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerUtils.dumpsys(energyConsumerInfo, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PowerStatsService dumpsys: PowerStatsLogger stats");
                indentingPrintWriter.increaseIndent();
                com.android.server.powerstats.PowerStatsService.this.mPowerStatsLogger.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    private class DeviceConfigListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        public java.util.concurrent.Executor mExecutor;

        private DeviceConfigListener() {
            this.mExecutor = new android.os.HandlerExecutor(com.android.server.powerstats.PowerStatsService.this.getHandler());
        }

        void startListening() {
            com.android.server.powerstats.PowerStatsService.this.mDeviceConfig.addOnPropertiesChangedListener("battery_stats", this.mExecutor, this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            com.android.server.powerstats.PowerStatsService.this.refreshFlags();
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            onSystemServicesReady();
        } else if (i == 1000) {
            onBootCompleted();
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (getPowerStatsHal().isInitialized()) {
            this.mPowerStatsInternal = new com.android.server.powerstats.PowerStatsService.LocalService();
            publishLocalService(android.power.PowerStatsInternal.class, this.mPowerStatsInternal);
        }
        publishBinderService(DATA_STORAGE_SUBDIR, this.mService);
    }

    private void onSystemServicesReady() {
        this.mPullAtomCallback = this.mInjector.createStatsPullerImpl(this.mContext, this.mPowerStatsInternal);
        this.mDeviceConfigListener.startListening();
        refreshFlags();
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean getDeleteMeterDataOnBoot() {
        return this.mPowerStatsLogger.getDeleteMeterDataOnBoot();
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean getDeleteModelDataOnBoot() {
        return this.mPowerStatsLogger.getDeleteModelDataOnBoot();
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean getDeleteResidencyDataOnBoot() {
        return this.mPowerStatsLogger.getDeleteResidencyDataOnBoot();
    }

    private void onBootCompleted() {
        if (getPowerStatsHal().isInitialized()) {
            this.mDataStoragePath = this.mInjector.createDataStoragePath();
            this.mPowerStatsLogger = this.mInjector.createPowerStatsLogger(this.mContext, getLooper(), this.mDataStoragePath, this.mInjector.createMeterFilename(), this.mInjector.createMeterCacheFilename(), this.mInjector.createModelFilename(), this.mInjector.createModelCacheFilename(), this.mInjector.createResidencyFilename(), this.mInjector.createResidencyCacheFilename(), getPowerStatsHal());
            this.mBatteryTrigger = this.mInjector.createBatteryTrigger(this.mContext, this.mPowerStatsLogger);
            this.mTimerTrigger = this.mInjector.createTimerTrigger(this.mContext, this.mPowerStatsLogger);
            return;
        }
        android.util.Slog.e(TAG, "Failed to start PowerStatsService loggers");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper getPowerStatsHal() {
        return this.mInjector.getPowerStatsHALWrapperImpl();
    }

    private android.os.Looper getLooper() {
        synchronized (this) {
            try {
                if (this.mLooper == null) {
                    android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
                    handlerThread.start();
                    return handlerThread.getLooper();
                }
                return this.mLooper;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Handler getHandler() {
        android.os.Handler handler;
        synchronized (this) {
            try {
                if (this.mHandler == null) {
                    this.mHandler = new android.os.Handler(getLooper());
                }
                handler = this.mHandler;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    private android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo() {
        android.hardware.power.stats.EnergyConsumer[] energyConsumerArr;
        synchronized (this) {
            try {
                if (this.mEnergyConsumers == null) {
                    this.mEnergyConsumers = getPowerStatsHal().getEnergyConsumerInfo();
                }
                energyConsumerArr = this.mEnergyConsumers;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return energyConsumerArr;
    }

    private android.hardware.power.stats.Channel[] getEnergyMeterInfo() {
        android.hardware.power.stats.Channel[] channelArr;
        synchronized (this) {
            try {
                if (this.mEnergyMeters == null) {
                    this.mEnergyMeters = getPowerStatsHal().getEnergyMeterInfo();
                }
                channelArr = this.mEnergyMeters;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return channelArr;
    }

    public PowerStatsService(android.content.Context context) {
        this(context, new com.android.server.powerstats.PowerStatsService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    public PowerStatsService(android.content.Context context, com.android.server.powerstats.PowerStatsService.Injector injector) {
        super(context);
        this.mDeviceConfigListener = new com.android.server.powerstats.PowerStatsService.DeviceConfigListener();
        this.mEnergyConsumers = null;
        this.mEnergyMeters = null;
        this.mService = new com.android.server.powerstats.PowerStatsService.AnonymousClass1();
        this.mPowerMonitorApiEnabled = true;
        this.mContext = context;
        this.mInjector = injector;
        this.mClock = injector.getClock();
        this.mDeviceConfig = injector.getDeviceConfig();
    }

    void refreshFlags() {
        setPowerMonitorApiEnabled(this.mDeviceConfig.getBoolean("battery_stats", KEY_POWER_MONITOR_API_ENABLED, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LocalService extends android.power.PowerStatsInternal {
        private LocalService() {
        }

        @Override // android.power.PowerStatsInternal
        public android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo() {
            return com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
        }

        @Override // android.power.PowerStatsInternal
        public java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> getEnergyConsumedAsync(final int[] iArr) {
            final java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> completableFuture = new java.util.concurrent.CompletableFuture<>();
            com.android.server.powerstats.PowerStatsService.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.powerstats.PowerStatsService$LocalService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.powerstats.PowerStatsService.LocalService.this.lambda$getEnergyConsumedAsync$0(completableFuture, iArr);
                }
            });
            return completableFuture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getEnergyConsumedAsync$0(java.util.concurrent.CompletableFuture completableFuture, int[] iArr) {
            com.android.server.powerstats.PowerStatsService.this.getEnergyConsumedAsync(completableFuture, iArr);
        }

        @Override // android.power.PowerStatsInternal
        public android.hardware.power.stats.PowerEntity[] getPowerEntityInfo() {
            return com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getPowerEntityInfo();
        }

        @Override // android.power.PowerStatsInternal
        public java.util.concurrent.CompletableFuture<android.hardware.power.stats.StateResidencyResult[]> getStateResidencyAsync(final int[] iArr) {
            final java.util.concurrent.CompletableFuture<android.hardware.power.stats.StateResidencyResult[]> completableFuture = new java.util.concurrent.CompletableFuture<>();
            com.android.server.powerstats.PowerStatsService.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.powerstats.PowerStatsService$LocalService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.powerstats.PowerStatsService.LocalService.this.lambda$getStateResidencyAsync$1(completableFuture, iArr);
                }
            });
            return completableFuture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStateResidencyAsync$1(java.util.concurrent.CompletableFuture completableFuture, int[] iArr) {
            com.android.server.powerstats.PowerStatsService.this.getStateResidencyAsync(completableFuture, iArr);
        }

        @Override // android.power.PowerStatsInternal
        public android.hardware.power.stats.Channel[] getEnergyMeterInfo() {
            return com.android.server.powerstats.PowerStatsService.this.getPowerStatsHal().getEnergyMeterInfo();
        }

        @Override // android.power.PowerStatsInternal
        public java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyMeasurement[]> readEnergyMeterAsync(final int[] iArr) {
            final java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyMeasurement[]> completableFuture = new java.util.concurrent.CompletableFuture<>();
            com.android.server.powerstats.PowerStatsService.this.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.powerstats.PowerStatsService$LocalService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.powerstats.PowerStatsService.LocalService.this.lambda$readEnergyMeterAsync$2(completableFuture, iArr);
                }
            });
            return completableFuture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$readEnergyMeterAsync$2(java.util.concurrent.CompletableFuture completableFuture, int[] iArr) {
            com.android.server.powerstats.PowerStatsService.this.readEnergyMeterAsync(completableFuture, iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getEnergyConsumedAsync(java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> completableFuture, int[] iArr) {
        int length;
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumed = getPowerStatsHal().getEnergyConsumed(iArr);
        android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = getEnergyConsumerInfo();
        if (energyConsumerInfo != null) {
            if (iArr.length == 0) {
                length = energyConsumerInfo.length;
            } else {
                length = iArr.length;
            }
            if (energyConsumed == null || length != energyConsumed.length) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Requested ids:");
                if (iArr.length == 0) {
                    sb.append("ALL");
                }
                sb.append("[");
                for (int i = 0; i < iArr.length; i++) {
                    int i2 = iArr[i];
                    sb.append(i2);
                    sb.append("(type:");
                    sb.append((int) energyConsumerInfo[i2].type);
                    sb.append(",ord:");
                    sb.append(energyConsumerInfo[i2].ordinal);
                    sb.append(",name:");
                    sb.append(energyConsumerInfo[i2].name);
                    sb.append(")");
                    if (i != length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                sb.append(", Received result ids:");
                if (energyConsumed == null) {
                    sb.append("null");
                } else {
                    sb.append("[");
                    int length2 = energyConsumed.length;
                    for (int i3 = 0; i3 < length2; i3++) {
                        int i4 = energyConsumed[i3].id;
                        sb.append(i4);
                        sb.append("(type:");
                        sb.append((int) energyConsumerInfo[i4].type);
                        sb.append(",ord:");
                        sb.append(energyConsumerInfo[i4].ordinal);
                        sb.append(",name:");
                        sb.append(energyConsumerInfo[i4].name);
                        sb.append(")");
                        if (i3 != length2 - 1) {
                            sb.append(", ");
                        }
                    }
                    sb.append("]");
                }
                android.util.Slog.wtf(TAG, "Missing result from getEnergyConsumedAsync call. " + ((java.lang.Object) sb));
            }
        }
        completableFuture.complete(energyConsumed);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getStateResidencyAsync(java.util.concurrent.CompletableFuture<android.hardware.power.stats.StateResidencyResult[]> completableFuture, int[] iArr) {
        completableFuture.complete(getPowerStatsHal().getStateResidency(iArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readEnergyMeterAsync(java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyMeasurement[]> completableFuture, int[] iArr) {
        completableFuture.complete(getPowerStatsHal().readEnergyMeter(iArr));
    }

    private static class PowerMonitorState {
        public long energyUws;
        public final int id;
        public final android.os.PowerMonitor powerMonitor;
        public long prevEnergyUws;
        public long timestampMs;

        private PowerMonitorState(android.os.PowerMonitor powerMonitor, int i) {
            this.energyUws = -1L;
            this.powerMonitor = powerMonitor;
            this.id = i;
        }
    }

    private void setPowerMonitorApiEnabled(boolean z) {
        if (z != this.mPowerMonitorApiEnabled) {
            this.mPowerMonitorApiEnabled = z;
            this.mPowerMonitors = null;
            this.mPowerMonitorStates = null;
        }
    }

    private void ensurePowerMonitors() {
        if (this.mPowerMonitors != null) {
            return;
        }
        synchronized (this) {
            try {
                if (this.mPowerMonitors != null) {
                    return;
                }
                if (this.mIntervalRandomNoiseGenerator == null) {
                    this.mIntervalRandomNoiseGenerator = this.mInjector.createIntervalRandomNoiseGenerator();
                }
                if (!this.mPowerMonitorApiEnabled) {
                    this.mPowerMonitors = new android.os.PowerMonitor[0];
                    this.mPowerMonitorStates = new com.android.server.powerstats.PowerStatsService.PowerMonitorState[0];
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                android.hardware.power.stats.Channel[] energyMeterInfo = getEnergyMeterInfo();
                int length = energyMeterInfo.length;
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    android.hardware.power.stats.Channel channel = energyMeterInfo[i];
                    android.os.PowerMonitor powerMonitor = new android.os.PowerMonitor(i2, 1, getChannelName(channel));
                    arrayList.add(powerMonitor);
                    arrayList2.add(new com.android.server.powerstats.PowerStatsService.PowerMonitorState(powerMonitor, channel.id));
                    i++;
                    i2++;
                }
                android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = getEnergyConsumerInfo();
                int length2 = energyConsumerInfo.length;
                int i3 = 0;
                while (i3 < length2) {
                    android.hardware.power.stats.EnergyConsumer energyConsumer = energyConsumerInfo[i3];
                    android.os.PowerMonitor powerMonitor2 = new android.os.PowerMonitor(i2, 0, getEnergyConsumerName(energyConsumer, energyConsumerInfo));
                    arrayList.add(powerMonitor2);
                    arrayList2.add(new com.android.server.powerstats.PowerStatsService.PowerMonitorState(powerMonitor2, energyConsumer.id));
                    i3++;
                    i2++;
                }
                this.mPowerMonitors = (android.os.PowerMonitor[]) arrayList.toArray(new android.os.PowerMonitor[arrayList.size()]);
                this.mPowerMonitorStates = (com.android.server.powerstats.PowerStatsService.PowerMonitorState[]) arrayList2.toArray(new com.android.server.powerstats.PowerStatsService.PowerMonitorState[arrayList.size()]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    private java.lang.String getChannelName(android.hardware.power.stats.Channel channel) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        sb.append(channel.name);
        sb.append("]:");
        if (channel.subsystem != null) {
            sb.append(channel.subsystem);
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007a  */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String getEnergyConsumerName(android.hardware.power.stats.EnergyConsumer energyConsumer, android.hardware.power.stats.EnergyConsumer[] energyConsumerArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        switch (energyConsumer.type) {
            case 1:
                sb.append("BLUETOOTH");
                break;
            case 2:
                sb.append("CPU");
                break;
            case 3:
                sb.append(com.android.server.display.config.SensorData.TEMPERATURE_TYPE_DISPLAY);
                break;
            case 4:
                sb.append("GNSS");
                break;
            case 5:
                sb.append("MOBILE_RADIO");
                break;
            case 6:
                sb.append("WIFI");
                break;
            case 7:
                sb.append("CAMERA");
                break;
            default:
                if (energyConsumer.name != null && !energyConsumer.name.isBlank()) {
                    sb.append(energyConsumer.name.toUpperCase(java.util.Locale.ENGLISH));
                    break;
                } else {
                    sb.append("CONSUMER_");
                    sb.append((int) energyConsumer.type);
                    break;
                }
        }
        boolean z = true;
        boolean z2 = energyConsumer.ordinal != 0;
        if (!z2) {
            for (android.hardware.power.stats.EnergyConsumer energyConsumer2 : energyConsumerArr) {
                if (energyConsumer2.type == energyConsumer.type && energyConsumer2.ordinal != 0) {
                    if (z) {
                        sb.append('/');
                        sb.append(energyConsumer.ordinal);
                    }
                    return sb.toString();
                }
            }
        }
        z = z2;
        if (z) {
        }
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void getSupportedPowerMonitorsImpl(android.os.ResultReceiver resultReceiver) {
        ensurePowerMonitors();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelableArray("monitors", this.mPowerMonitors);
        resultReceiver.send(0, bundle);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void getPowerMonitorReadingsImpl(@android.annotation.NonNull int[] iArr, android.os.ResultReceiver resultReceiver, int i) {
        ensurePowerMonitors();
        int length = iArr.length;
        com.android.server.powerstats.PowerStatsService.PowerMonitorState[] powerMonitorStateArr = new com.android.server.powerstats.PowerStatsService.PowerMonitorState[length];
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 < 0 || i3 >= this.mPowerMonitorStates.length) {
                resultReceiver.send(1, null);
                return;
            }
            powerMonitorStateArr[i2] = this.mPowerMonitorStates[i3];
            if (this.mPowerMonitorStates[i3] != null && this.mPowerMonitorStates[i3].timestampMs < j) {
                j = this.mPowerMonitorStates[i3].timestampMs;
            }
        }
        if (j == 0 || this.mClock.elapsedRealtime() - j > 30000) {
            updateEnergyConsumers(powerMonitorStateArr);
            updateEnergyMeasurements(powerMonitorStateArr);
            this.mIntervalRandomNoiseGenerator.refresh();
        }
        long[] jArr = new long[length];
        long[] jArr2 = new long[length];
        for (int i4 = 0; i4 < length; i4++) {
            com.android.server.powerstats.PowerStatsService.PowerMonitorState powerMonitorState = powerMonitorStateArr[i4];
            if (powerMonitorState.energyUws != -1 && powerMonitorState.prevEnergyUws != -1) {
                jArr[i4] = this.mIntervalRandomNoiseGenerator.addNoise(java.lang.Math.max(powerMonitorState.prevEnergyUws, powerMonitorState.energyUws - MAX_RANDOM_NOISE_UWS), powerMonitorState.energyUws, i);
            } else {
                jArr[i4] = powerMonitorState.energyUws;
            }
            jArr2[i4] = powerMonitorState.timestampMs;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLongArray("energy", jArr);
        bundle.putLongArray("timestamps", jArr2);
        resultReceiver.send(0, bundle);
    }

    private void updateEnergyConsumers(com.android.server.powerstats.PowerStatsService.PowerMonitorState[] powerMonitorStateArr) {
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumed;
        int[] collectIds = collectIds(powerMonitorStateArr, 0);
        if (collectIds == null || (energyConsumed = getPowerStatsHal().getEnergyConsumed(collectIds)) == null) {
            return;
        }
        for (com.android.server.powerstats.PowerStatsService.PowerMonitorState powerMonitorState : powerMonitorStateArr) {
            if (powerMonitorState.powerMonitor.getType() == 0) {
                int length = energyConsumed.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        android.hardware.power.stats.EnergyConsumerResult energyConsumerResult = energyConsumed[i];
                        if (energyConsumerResult.id != powerMonitorState.id) {
                            i++;
                        } else {
                            powerMonitorState.prevEnergyUws = powerMonitorState.energyUws;
                            powerMonitorState.energyUws = energyConsumerResult.energyUWs;
                            powerMonitorState.timestampMs = energyConsumerResult.timestampMs;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void updateEnergyMeasurements(com.android.server.powerstats.PowerStatsService.PowerMonitorState[] powerMonitorStateArr) {
        android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter;
        int[] collectIds = collectIds(powerMonitorStateArr, 1);
        if (collectIds == null || (readEnergyMeter = getPowerStatsHal().readEnergyMeter(collectIds)) == null) {
            return;
        }
        for (com.android.server.powerstats.PowerStatsService.PowerMonitorState powerMonitorState : powerMonitorStateArr) {
            if (powerMonitorState.powerMonitor.getType() == 1) {
                int length = readEnergyMeter.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        android.hardware.power.stats.EnergyMeasurement energyMeasurement = readEnergyMeter[i];
                        if (energyMeasurement.id != powerMonitorState.id) {
                            i++;
                        } else {
                            powerMonitorState.prevEnergyUws = powerMonitorState.energyUws;
                            powerMonitorState.energyUws = energyMeasurement.energyUWs;
                            powerMonitorState.timestampMs = energyMeasurement.timestampMs;
                            break;
                        }
                    }
                }
            }
        }
    }

    @android.annotation.Nullable
    private int[] collectIds(com.android.server.powerstats.PowerStatsService.PowerMonitorState[] powerMonitorStateArr, int i) {
        int i2 = 0;
        for (com.android.server.powerstats.PowerStatsService.PowerMonitorState powerMonitorState : powerMonitorStateArr) {
            if (powerMonitorState.powerMonitor.getType() == i) {
                i2++;
            }
        }
        if (i2 == 0) {
            return null;
        }
        int[] iArr = new int[i2];
        int i3 = 0;
        for (com.android.server.powerstats.PowerStatsService.PowerMonitorState powerMonitorState2 : powerMonitorStateArr) {
            if (powerMonitorState2.powerMonitor.getType() == i) {
                iArr[i3] = powerMonitorState2.id;
                i3++;
            }
        }
        return iArr;
    }
}
