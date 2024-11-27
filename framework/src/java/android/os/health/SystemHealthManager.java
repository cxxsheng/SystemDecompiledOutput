package android.os.health;

/* loaded from: classes3.dex */
public class SystemHealthManager {
    private static final java.util.Comparator<android.os.PowerMonitor> POWER_MONITOR_COMPARATOR = java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda1
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(java.lang.Object obj) {
            int i;
            i = ((android.os.PowerMonitor) obj).index;
            return i;
        }
    });
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private java.util.List<android.os.PowerMonitor> mPowerMonitorsInfo;
    private final java.lang.Object mPowerMonitorsLock;
    private final android.os.IPowerStatsService mPowerStats;

    public SystemHealthManager() {
        this(com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats")), android.os.IPowerStatsService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.POWER_STATS_SERVICE)));
    }

    public SystemHealthManager(com.android.internal.app.IBatteryStats iBatteryStats, android.os.IPowerStatsService iPowerStatsService) {
        this.mPowerMonitorsLock = new java.lang.Object();
        this.mBatteryStats = iBatteryStats;
        this.mPowerStats = iPowerStatsService;
    }

    public static android.os.health.SystemHealthManager from(android.content.Context context) {
        return (android.os.health.SystemHealthManager) context.getSystemService(android.content.Context.SYSTEM_HEALTH_SERVICE);
    }

    public android.os.health.HealthStats takeUidSnapshot(int i) {
        try {
            return this.mBatteryStats.takeUidSnapshot(i).getHealthStats();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public android.os.health.HealthStats takeMyUidSnapshot() {
        return takeUidSnapshot(android.os.Process.myUid());
    }

    public android.os.health.HealthStats[] takeUidSnapshots(int[] iArr) {
        try {
            android.os.health.HealthStatsParceler[] takeUidSnapshots = this.mBatteryStats.takeUidSnapshots(iArr);
            android.os.health.HealthStats[] healthStatsArr = new android.os.health.HealthStats[iArr.length];
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                healthStatsArr[i] = takeUidSnapshots[i].getHealthStats();
            }
            return healthStatsArr;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void getSupportedPowerMonitors(java.util.concurrent.Executor executor, final java.util.function.Consumer<java.util.List<android.os.PowerMonitor>> consumer) {
        final java.util.List<android.os.PowerMonitor> list;
        synchronized (this.mPowerMonitorsLock) {
            if (this.mPowerMonitorsInfo != null) {
                list = this.mPowerMonitorsInfo;
            } else if (this.mPowerStats == null) {
                this.mPowerMonitorsInfo = java.util.List.of();
                list = this.mPowerMonitorsInfo;
            } else {
                list = null;
            }
        }
        if (list != null) {
            if (executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(list);
                    }
                });
                return;
            } else {
                consumer.accept(list);
                return;
            }
        }
        try {
            this.mPowerStats.getSupportedPowerMonitors(new android.os.health.SystemHealthManager.AnonymousClass1(null, executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.os.health.SystemHealthManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.ResultReceiver {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$onResult;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(android.os.Handler handler, java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            super(handler);
            this.val$executor = executor;
            this.val$onResult = consumer;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            android.os.PowerMonitor[] powerMonitorArr = (android.os.PowerMonitor[]) bundle.getParcelableArray(android.os.IPowerStatsService.KEY_MONITORS, android.os.PowerMonitor.class);
            final java.util.List asList = powerMonitorArr != null ? java.util.Arrays.asList(powerMonitorArr) : java.util.List.of();
            synchronized (android.os.health.SystemHealthManager.this.mPowerMonitorsLock) {
                android.os.health.SystemHealthManager.this.mPowerMonitorsInfo = asList;
            }
            if (this.val$executor != null) {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$onResult;
                executor.execute(new java.lang.Runnable() { // from class: android.os.health.SystemHealthManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(asList);
                    }
                });
                return;
            }
            this.val$onResult.accept(asList);
        }
    }

    public void getPowerMonitorReadings(java.util.List<android.os.PowerMonitor> list, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.os.PowerMonitorReadings, java.lang.RuntimeException> outcomeReceiver) {
        if (this.mPowerStats == null) {
            final java.lang.IllegalArgumentException illegalArgumentException = new java.lang.IllegalArgumentException("Unsupported power monitor");
            if (executor != null) {
                executor.execute(new java.lang.Runnable() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(illegalArgumentException);
                    }
                });
                return;
            } else {
                outcomeReceiver.onError(illegalArgumentException);
                return;
            }
        }
        android.os.PowerMonitor[] powerMonitorArr = (android.os.PowerMonitor[]) list.toArray(new android.os.PowerMonitor[list.size()]);
        java.util.Arrays.sort(powerMonitorArr, POWER_MONITOR_COMPARATOR);
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = powerMonitorArr[i].index;
        }
        try {
            this.mPowerStats.getPowerMonitorReadings(iArr, new android.os.health.SystemHealthManager.AnonymousClass2(null, powerMonitorArr, executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.os.health.SystemHealthManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.ResultReceiver {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.os.OutcomeReceiver val$onResult;
        final /* synthetic */ android.os.PowerMonitor[] val$powerMonitorsArray;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(android.os.Handler handler, android.os.PowerMonitor[] powerMonitorArr, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$powerMonitorsArray = powerMonitorArr;
            this.val$executor = executor;
            this.val$onResult = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            final java.lang.RuntimeException illegalStateException;
            if (i == 0) {
                final android.os.PowerMonitorReadings powerMonitorReadings = new android.os.PowerMonitorReadings(this.val$powerMonitorsArray, bundle.getLongArray(android.os.IPowerStatsService.KEY_ENERGY), bundle.getLongArray(android.os.IPowerStatsService.KEY_TIMESTAMPS));
                if (this.val$executor != null) {
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$onResult;
                    executor.execute(new java.lang.Runnable() { // from class: android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(powerMonitorReadings);
                        }
                    });
                    return;
                }
                this.val$onResult.onResult(powerMonitorReadings);
                return;
            }
            if (i == 1) {
                illegalStateException = new java.lang.IllegalArgumentException("Unsupported power monitor");
            } else {
                illegalStateException = new java.lang.IllegalStateException("Unrecognized result code " + i);
            }
            if (this.val$executor != null) {
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$onResult;
                executor2.execute(new java.lang.Runnable() { // from class: android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(illegalStateException);
                    }
                });
                return;
            }
            this.val$onResult.onError(illegalStateException);
        }
    }
}
