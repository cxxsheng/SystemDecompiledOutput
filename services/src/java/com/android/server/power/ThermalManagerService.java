package com.android.server.power;

/* loaded from: classes2.dex */
public class ThermalManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    public static final int MAX_FORECAST_SEC = 60;
    public static final int MIN_FORECAST_SEC = 0;
    private static final java.lang.String TAG = com.android.server.power.ThermalManagerService.class.getSimpleName();
    private final android.content.Context mContext;
    private final java.util.concurrent.atomic.AtomicBoolean mHalReady;
    private com.android.server.power.ThermalManagerService.ThermalHalWrapper mHalWrapper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsStatusOverride;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IThermalService.Stub mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mStatus;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, android.os.Temperature> mTemperatureMap;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.power.ThermalManagerService.TemperatureWatcher mTemperatureWatcher;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.RemoteCallbackList<android.os.IThermalEventListener> mThermalEventListeners;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.RemoteCallbackList<android.os.IThermalStatusListener> mThermalStatusListeners;

    public ThermalManagerService(android.content.Context context) {
        this(context, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    ThermalManagerService(android.content.Context context, @android.annotation.Nullable com.android.server.power.ThermalManagerService.ThermalHalWrapper thermalHalWrapper) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mThermalEventListeners = new android.os.RemoteCallbackList<>();
        this.mThermalStatusListeners = new android.os.RemoteCallbackList<>();
        this.mTemperatureMap = new android.util.ArrayMap<>();
        this.mHalReady = new java.util.concurrent.atomic.AtomicBoolean();
        this.mTemperatureWatcher = new com.android.server.power.ThermalManagerService.TemperatureWatcher();
        this.mService = new android.os.IThermalService.Stub() { // from class: com.android.server.power.ThermalManagerService.1
            public boolean registerThermalEventListener(android.os.IThermalEventListener iThermalEventListener) {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!com.android.server.power.ThermalManagerService.this.mThermalEventListeners.register(iThermalEventListener, null)) {
                            return false;
                        }
                        com.android.server.power.ThermalManagerService.this.postEventListenerCurrentTemperatures(iThermalEventListener, null);
                        return true;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }

            public boolean registerThermalEventListenerWithType(android.os.IThermalEventListener iThermalEventListener, int i) {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!com.android.server.power.ThermalManagerService.this.mThermalEventListeners.register(iThermalEventListener, new java.lang.Integer(i))) {
                            return false;
                        }
                        com.android.server.power.ThermalManagerService.this.postEventListenerCurrentTemperatures(iThermalEventListener, new java.lang.Integer(i));
                        return true;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }

            public boolean unregisterThermalEventListener(android.os.IThermalEventListener iThermalEventListener) {
                boolean unregister;
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    try {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            unregister = com.android.server.power.ThermalManagerService.this.mThermalEventListeners.unregister(iThermalEventListener);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return unregister;
            }

            public android.os.Temperature[] getCurrentTemperatures() {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                        return new android.os.Temperature[0];
                    }
                    java.util.List<android.os.Temperature> currentTemperatures = com.android.server.power.ThermalManagerService.this.mHalWrapper.getCurrentTemperatures(false, 0);
                    return (android.os.Temperature[]) currentTemperatures.toArray(new android.os.Temperature[currentTemperatures.size()]);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public android.os.Temperature[] getCurrentTemperaturesWithType(int i) {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                        return new android.os.Temperature[0];
                    }
                    java.util.List<android.os.Temperature> currentTemperatures = com.android.server.power.ThermalManagerService.this.mHalWrapper.getCurrentTemperatures(true, i);
                    return (android.os.Temperature[]) currentTemperatures.toArray(new android.os.Temperature[currentTemperatures.size()]);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public boolean registerThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) {
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!com.android.server.power.ThermalManagerService.this.mThermalStatusListeners.register(iThermalStatusListener)) {
                            return false;
                        }
                        com.android.server.power.ThermalManagerService.this.postStatusListener(iThermalStatusListener);
                        return true;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }

            public boolean unregisterThermalStatusListener(android.os.IThermalStatusListener iThermalStatusListener) {
                boolean unregister;
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    try {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            unregister = com.android.server.power.ThermalManagerService.this.mThermalStatusListeners.unregister(iThermalStatusListener);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return unregister;
            }

            public int getCurrentThermalStatus() {
                int i;
                int i2;
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    try {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            int callingUid = android.os.Binder.getCallingUid();
                            if (com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                                i = 1;
                            } else {
                                i = 2;
                            }
                            com.android.internal.util.FrameworkStatsLog.write(772, callingUid, i, com.android.server.power.ThermalManagerService.thermalSeverityToStatsdStatus(com.android.server.power.ThermalManagerService.this.mStatus));
                            i2 = com.android.server.power.ThermalManagerService.this.mStatus;
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return i2;
            }

            public android.os.CoolingDevice[] getCurrentCoolingDevices() {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                        return new android.os.CoolingDevice[0];
                    }
                    java.util.List<android.os.CoolingDevice> currentCoolingDevices = com.android.server.power.ThermalManagerService.this.mHalWrapper.getCurrentCoolingDevices(false, 0);
                    return (android.os.CoolingDevice[]) currentCoolingDevices.toArray(new android.os.CoolingDevice[currentCoolingDevices.size()]);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public android.os.CoolingDevice[] getCurrentCoolingDevicesWithType(int i) {
                com.android.server.power.ThermalManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                        return new android.os.CoolingDevice[0];
                    }
                    java.util.List<android.os.CoolingDevice> currentCoolingDevices = com.android.server.power.ThermalManagerService.this.mHalWrapper.getCurrentCoolingDevices(true, i);
                    return (android.os.CoolingDevice[]) currentCoolingDevices.toArray(new android.os.CoolingDevice[currentCoolingDevices.size()]);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public float getThermalHeadroom(int i) {
                if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                    com.android.internal.util.FrameworkStatsLog.write(773, android.os.IThermalService.Stub.getCallingUid(), 2, Float.NaN);
                    return Float.NaN;
                }
                if (i < 0 || i > 60) {
                    com.android.internal.util.FrameworkStatsLog.write(773, android.os.IThermalService.Stub.getCallingUid(), 4, Float.NaN);
                    return Float.NaN;
                }
                return com.android.server.power.ThermalManagerService.this.mTemperatureWatcher.getForecast(i);
            }

            public float[] getThermalHeadroomThresholds() {
                float[] copyOf;
                if (!com.android.server.power.ThermalManagerService.this.mHalReady.get()) {
                    com.android.internal.util.FrameworkStatsLog.write(774, android.os.Binder.getCallingUid(), 2);
                    throw new java.lang.IllegalStateException("Thermal HAL connection is not initialized");
                }
                if (!android.os.Flags.allowThermalHeadroomThresholds()) {
                    com.android.internal.util.FrameworkStatsLog.write(774, android.os.Binder.getCallingUid(), 3);
                    throw new java.lang.UnsupportedOperationException("Thermal headroom thresholds not enabled");
                }
                synchronized (com.android.server.power.ThermalManagerService.this.mTemperatureWatcher.mSamples) {
                    com.android.internal.util.FrameworkStatsLog.write(774, android.os.Binder.getCallingUid(), 1);
                    copyOf = java.util.Arrays.copyOf(com.android.server.power.ThermalManagerService.this.mTemperatureWatcher.mHeadroomThresholds, com.android.server.power.ThermalManagerService.this.mTemperatureWatcher.mHeadroomThresholds.length);
                }
                return copyOf;
            }

            protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                com.android.server.power.ThermalManagerService.this.dumpInternal(fileDescriptor, printWriter, strArr);
            }

            private boolean isCallerShell() {
                int callingUid = android.os.Binder.getCallingUid();
                return callingUid == 2000 || callingUid == 0;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
                if (!isCallerShell()) {
                    android.util.Slog.w(com.android.server.power.ThermalManagerService.TAG, "Only shell is allowed to call thermalservice shell commands");
                } else {
                    com.android.server.power.ThermalManagerService.this.new ThermalShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
                }
            }
        };
        this.mContext = context;
        this.mHalWrapper = thermalHalWrapper;
        if (thermalHalWrapper != null) {
            thermalHalWrapper.setCallback(new com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda0(this));
        }
        this.mStatus = 0;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("thermalservice", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            onActivityManagerReady();
        }
        if (i == 1000) {
            registerStatsCallbacks();
        }
    }

    private void onActivityManagerReady() {
        synchronized (this.mLock) {
            try {
                boolean z = this.mHalWrapper != null;
                if (!z) {
                    this.mHalWrapper = new com.android.server.power.ThermalManagerService.ThermalHalAidlWrapper(new com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda0(this));
                    z = this.mHalWrapper.connectToHal();
                }
                if (!z) {
                    this.mHalWrapper = new com.android.server.power.ThermalManagerService.ThermalHal20Wrapper(new com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda0(this));
                    z = this.mHalWrapper.connectToHal();
                }
                if (!z) {
                    this.mHalWrapper = new com.android.server.power.ThermalManagerService.ThermalHal11Wrapper(new com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda0(this));
                    z = this.mHalWrapper.connectToHal();
                }
                if (!z) {
                    this.mHalWrapper = new com.android.server.power.ThermalManagerService.ThermalHal10Wrapper(new com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda0(this));
                    z = this.mHalWrapper.connectToHal();
                }
                if (!z) {
                    android.util.Slog.w(TAG, "No Thermal HAL service on this device");
                    return;
                }
                java.util.List<android.os.Temperature> currentTemperatures = this.mHalWrapper.getCurrentTemperatures(false, 0);
                int size = currentTemperatures.size();
                if (size == 0) {
                    android.util.Slog.w(TAG, "Thermal HAL reported invalid data, abort connection");
                }
                for (int i = 0; i < size; i++) {
                    onTemperatureChanged(currentTemperatures.get(i), false);
                }
                onTemperatureMapChangedLocked();
                this.mTemperatureWatcher.updateThresholds();
                this.mHalReady.set(true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postStatusListener(final android.os.IThermalStatusListener iThermalStatusListener) {
        if (!com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.ThermalManagerService.this.lambda$postStatusListener$0(iThermalStatusListener);
            }
        })) {
            android.util.Slog.e(TAG, "Thermal callback failed to queue");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postStatusListener$0(android.os.IThermalStatusListener iThermalStatusListener) {
        try {
            iThermalStatusListener.onStatusChange(this.mStatus);
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Thermal callback failed to call", e);
        }
    }

    private void notifyStatusListenersLocked() {
        int beginBroadcast = this.mThermalStatusListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                postStatusListener(this.mThermalStatusListeners.getBroadcastItem(i));
            } finally {
                this.mThermalStatusListeners.finishBroadcast();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureMapChangedLocked() {
        int size = this.mTemperatureMap.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.os.Temperature valueAt = this.mTemperatureMap.valueAt(i2);
            if (valueAt.getType() == 3 && valueAt.getStatus() >= i) {
                i = valueAt.getStatus();
            }
        }
        if (!this.mIsStatusOverride) {
            setStatusLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStatusLocked(int i) {
        if (i != this.mStatus) {
            this.mStatus = i;
            notifyStatusListenersLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postEventListenerCurrentTemperatures(android.os.IThermalEventListener iThermalEventListener, @android.annotation.Nullable java.lang.Integer num) {
        synchronized (this.mLock) {
            try {
                int size = this.mTemperatureMap.size();
                for (int i = 0; i < size; i++) {
                    postEventListener(this.mTemperatureMap.valueAt(i), iThermalEventListener, num);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void postEventListener(final android.os.Temperature temperature, final android.os.IThermalEventListener iThermalEventListener, @android.annotation.Nullable java.lang.Integer num) {
        if ((num == null || num.intValue() == temperature.getType()) && !com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.ThermalManagerService.lambda$postEventListener$1(iThermalEventListener, temperature);
            }
        })) {
            android.util.Slog.e(TAG, "Thermal callback failed to queue");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postEventListener$1(android.os.IThermalEventListener iThermalEventListener, android.os.Temperature temperature) {
        try {
            iThermalEventListener.notifyThrottling(temperature);
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Thermal callback failed to call", e);
        }
    }

    private void notifyEventListenersLocked(android.os.Temperature temperature) {
        int beginBroadcast = this.mThermalEventListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                postEventListener(temperature, this.mThermalEventListeners.getBroadcastItem(i), (java.lang.Integer) this.mThermalEventListeners.getBroadcastCookie(i));
            } catch (java.lang.Throwable th) {
                this.mThermalEventListeners.finishBroadcast();
                throw th;
            }
        }
        this.mThermalEventListeners.finishBroadcast();
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.THERMAL_CHANGED, temperature.getName(), java.lang.Integer.valueOf(temperature.getType()), java.lang.Float.valueOf(temperature.getValue()), java.lang.Integer.valueOf(temperature.getStatus()), java.lang.Integer.valueOf(this.mStatus));
    }

    private void shutdownIfNeeded(android.os.Temperature temperature) {
        if (temperature.getStatus() != 6) {
        }
        android.os.PowerManager powerManager = (android.os.PowerManager) getContext().getSystemService(android.os.PowerManager.class);
        switch (temperature.getType()) {
            case 0:
            case 1:
            case 3:
            case 9:
                powerManager.shutdown(false, "thermal", false);
                break;
            case 2:
                powerManager.shutdown(false, "thermal,battery", false);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureChanged(android.os.Temperature temperature, boolean z) {
        shutdownIfNeeded(temperature);
        synchronized (this.mLock) {
            try {
                android.os.Temperature put = this.mTemperatureMap.put(temperature.getName(), temperature);
                if (put == null || put.getStatus() != temperature.getStatus()) {
                    notifyEventListenersLocked(temperature);
                }
                if (z) {
                    onTemperatureMapChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTemperatureChangedCallback(android.os.Temperature temperature) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            onTemperatureChanged(temperature, true);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerStatsCallbacks() {
        android.app.StatsManager statsManager = (android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class);
        if (statsManager != null) {
            statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.power.ThermalManagerService$$ExternalSyntheticLambda1
                public final int onPullAtom(int i, java.util.List list) {
                    int onPullAtom;
                    onPullAtom = com.android.server.power.ThermalManagerService.this.onPullAtom(i, list);
                    return onPullAtom;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onPullAtom(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        float[] copyOf;
        if (i == 10201) {
            synchronized (this.mTemperatureWatcher.mSamples) {
                copyOf = java.util.Arrays.copyOf(this.mTemperatureWatcher.mHeadroomThresholds, this.mTemperatureWatcher.mHeadroomThresholds.length);
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.THERMAL_HEADROOM_THRESHOLDS, copyOf));
            return 0;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int thermalSeverityToStatsdStatus(int i) {
        switch (i) {
        }
        return 0;
    }

    private static void dumpItemsLocked(java.io.PrintWriter printWriter, java.lang.String str, java.util.Collection<?> collection) {
        java.util.Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            printWriter.println(str + it.next().toString());
        }
    }

    private static void dumpTemperatureThresholds(java.io.PrintWriter printWriter, java.lang.String str, java.util.List<android.hardware.thermal.TemperatureThreshold> list) {
        for (android.hardware.thermal.TemperatureThreshold temperatureThreshold : list) {
            printWriter.println(str + "TemperatureThreshold{mType=" + temperatureThreshold.type + ", mName=" + temperatureThreshold.name + ", mHotThrottlingThresholds=" + java.util.Arrays.toString(temperatureThreshold.hotThrottlingThresholds) + ", mColdThrottlingThresholds=" + java.util.Arrays.toString(temperatureThreshold.coldThrottlingThresholds) + "}");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void dumpInternal(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!com.android.internal.util.DumpUtils.checkDumpPermission(getContext(), TAG, printWriter)) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    printWriter.println("IsStatusOverride: " + this.mIsStatusOverride);
                    printWriter.println("ThermalEventListeners:");
                    this.mThermalEventListeners.dump(printWriter, "\t");
                    printWriter.println("ThermalStatusListeners:");
                    this.mThermalStatusListeners.dump(printWriter, "\t");
                    printWriter.println("Thermal Status: " + this.mStatus);
                    printWriter.println("Cached temperatures:");
                    dumpItemsLocked(printWriter, "\t", this.mTemperatureMap.values());
                    printWriter.println("HAL Ready: " + this.mHalReady.get());
                    if (this.mHalReady.get()) {
                        printWriter.println("HAL connection:");
                        this.mHalWrapper.dump(printWriter, "\t");
                        printWriter.println("Current temperatures from HAL:");
                        dumpItemsLocked(printWriter, "\t", this.mHalWrapper.getCurrentTemperatures(false, 0));
                        printWriter.println("Current cooling devices from HAL:");
                        dumpItemsLocked(printWriter, "\t", this.mHalWrapper.getCurrentCoolingDevices(false, 0));
                        printWriter.println("Temperature static thresholds from HAL:");
                        dumpTemperatureThresholds(printWriter, "\t", this.mHalWrapper.getTemperatureThresholds(false, 0));
                    }
                } finally {
                }
            }
            if (android.os.Flags.allowThermalHeadroomThresholds()) {
                synchronized (this.mTemperatureWatcher.mSamples) {
                    printWriter.println("Temperature headroom thresholds:");
                    printWriter.println(java.util.Arrays.toString(this.mTemperatureWatcher.mHeadroomThresholds));
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    class ThermalShellCommand extends android.os.ShellCommand {
        ThermalShellCommand() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            char c;
            java.lang.String str2 = str != null ? str : "";
            switch (str2.hashCode()) {
                case -61558984:
                    if (str2.equals("inject-temperature")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str2.equals("reset")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 385515795:
                    if (str2.equals("override-status")) {
                        c = 1;
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
                    return runInjectTemperature();
                case 1:
                    return runOverrideStatus();
                case 2:
                    return runReset();
                default:
                    return handleDefaultCommands(str);
            }
        }

        private int runReset() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                    com.android.server.power.ThermalManagerService.this.mIsStatusOverride = false;
                    com.android.server.power.ThermalManagerService.this.onTemperatureMapChangedLocked();
                }
                return 0;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:34:0x01a3  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x01f5 A[Catch: all -> 0x004c, TRY_LEAVE, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x020e  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0233 A[Catch: all -> 0x004c, RuntimeException -> 0x0238, TRY_LEAVE, TryCatch #0 {RuntimeException -> 0x0238, blocks: (B:43:0x022d, B:45:0x0233), top: B:42:0x022d, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x023a  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0212  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0216  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x021a  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x021e  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0222  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0225  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x01a4 A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x01af A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x01ba A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01c5 A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x01cf A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x01da A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x01e5 A[Catch: all -> 0x004c, TryCatch #1 {all -> 0x004c, blocks: (B:3:0x0004, B:4:0x003b, B:7:0x012e, B:8:0x0131, B:32:0x0194, B:33:0x01a0, B:36:0x01f2, B:37:0x01f5, B:41:0x0228, B:43:0x022d, B:45:0x0233, B:47:0x023f, B:53:0x024f, B:62:0x01a4, B:65:0x01af, B:68:0x01ba, B:71:0x01c5, B:74:0x01cf, B:77:0x01da, B:80:0x01e5, B:83:0x0040, B:86:0x004f, B:89:0x005b, B:92:0x0067, B:95:0x0073, B:98:0x007f, B:101:0x008b, B:104:0x0097, B:107:0x00a2, B:110:0x00ad, B:113:0x00b8, B:116:0x00c4, B:119:0x00d0, B:122:0x00da, B:125:0x00e4, B:128:0x00ee, B:131:0x00f9, B:134:0x0103, B:137:0x010d, B:140:0x0117, B:143:0x0121), top: B:2:0x0004, inners: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int runInjectTemperature() {
            char c;
            java.lang.String upperCase;
            boolean z;
            int i;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String upperCase2 = nextArgRequired.toUpperCase();
                int i2 = 18;
                switch (upperCase2.hashCode()) {
                    case -1905220446:
                        if (upperCase2.equals(com.android.server.display.config.SensorData.TEMPERATURE_TYPE_DISPLAY)) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1290540065:
                        if (upperCase2.equals("SPEAKER")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case -697981146:
                        if (upperCase2.equals("FLASHLIGHT")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case -174162312:
                        if (upperCase2.equals("AMBIENT")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66952:
                        if (upperCase2.equals("CPU")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 70796:
                        if (upperCase2.equals("GPU")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 77523:
                        if (upperCase2.equals("NPU")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 82279:
                        if (upperCase2.equals("SOC")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 83289:
                        if (upperCase2.equals("TPU")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2461479:
                        if (upperCase2.equals("POGO")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2547069:
                        if (upperCase2.equals(com.android.server.display.config.SensorData.TEMPERATURE_TYPE_SKIN)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2664213:
                        if (upperCase2.equals("WIFI")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 73532170:
                        if (upperCase2.equals("MODEM")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 386661166:
                        if (upperCase2.equals("BCL_PERCENTAGE")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 386742765:
                        if (upperCase2.equals("BATTERY")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 433141802:
                        if (upperCase2.equals("UNKNOWN")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 443104892:
                        if (upperCase2.equals("USB_PORT")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 864826154:
                        if (upperCase2.equals("BCL_VOLTAGE")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 869307255:
                        if (upperCase2.equals("POWER_AMPLIFIER")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1359385925:
                        if (upperCase2.equals("BCL_CURRENT")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1980544805:
                        if (upperCase2.equals("CAMERA")) {
                            c = 16;
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
                        i2 = -1;
                        java.lang.String nextArgRequired2 = getNextArgRequired();
                        upperCase = nextArgRequired2.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                if (upperCase.equals("SEVERE")) {
                                    z = 3;
                                    break;
                                }
                                z = -1;
                                break;
                            case -1560189025:
                                if (upperCase.equals(com.android.server.utils.PriorityDump.PRIORITY_ARG_CRITICAL)) {
                                    z = 4;
                                    break;
                                }
                                z = -1;
                                break;
                            case 2402104:
                                if (upperCase.equals("NONE")) {
                                    z = false;
                                    break;
                                }
                                z = -1;
                                break;
                            case 72432886:
                                if (upperCase.equals("LIGHT")) {
                                    z = true;
                                    break;
                                }
                                z = -1;
                                break;
                            case 120640881:
                                if (upperCase.equals("EMERGENCY")) {
                                    z = 5;
                                    break;
                                }
                                z = -1;
                                break;
                            case 163769603:
                                if (upperCase.equals("MODERATE")) {
                                    z = 2;
                                    break;
                                }
                                z = -1;
                                break;
                            case 613283414:
                                if (upperCase.equals("SHUTDOWN")) {
                                    z = 6;
                                    break;
                                }
                                z = -1;
                                break;
                            default:
                                z = -1;
                                break;
                        }
                        switch (z) {
                            case false:
                                i = 0;
                                break;
                            case true:
                                i = 1;
                                break;
                            case true:
                                i = 2;
                                break;
                            case true:
                                i = 3;
                                break;
                            case true:
                                i = 4;
                                break;
                            case true:
                                i = 5;
                                break;
                            case true:
                                i = 6;
                                break;
                            default:
                                outPrintWriter.println("Invalid throttle status: " + nextArgRequired2);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return -1;
                        }
                        java.lang.String nextArgRequired3 = getNextArgRequired();
                        try {
                            java.lang.String nextArg = getNextArg();
                            com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg != null ? java.lang.Float.parseFloat(nextArg) : 28.0f, i2, nextArgRequired3, i), true);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return 0;
                        } catch (java.lang.RuntimeException e) {
                            outPrintWriter.println("Error: " + e.toString());
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return -1;
                        }
                    case 1:
                        i2 = 0;
                        java.lang.String nextArgRequired22 = getNextArgRequired();
                        upperCase = nextArgRequired22.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32 = getNextArgRequired();
                        java.lang.String nextArg2 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2 != null ? java.lang.Float.parseFloat(nextArg2) : 28.0f, i2, nextArgRequired32, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 2:
                        i2 = 1;
                        java.lang.String nextArgRequired222 = getNextArgRequired();
                        upperCase = nextArgRequired222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322 = getNextArgRequired();
                        java.lang.String nextArg22 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22 != null ? java.lang.Float.parseFloat(nextArg22) : 28.0f, i2, nextArgRequired322, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 3:
                        i2 = 2;
                        java.lang.String nextArgRequired2222 = getNextArgRequired();
                        upperCase = nextArgRequired2222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222 = getNextArgRequired();
                        java.lang.String nextArg222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222 != null ? java.lang.Float.parseFloat(nextArg222) : 28.0f, i2, nextArgRequired3222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 4:
                        i2 = 3;
                        java.lang.String nextArgRequired22222 = getNextArgRequired();
                        upperCase = nextArgRequired22222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222 = getNextArgRequired();
                        java.lang.String nextArg2222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222 != null ? java.lang.Float.parseFloat(nextArg2222) : 28.0f, i2, nextArgRequired32222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 5:
                        i2 = 4;
                        java.lang.String nextArgRequired222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222 = getNextArgRequired();
                        java.lang.String nextArg22222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222 != null ? java.lang.Float.parseFloat(nextArg22222) : 28.0f, i2, nextArgRequired322222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 6:
                        i2 = 5;
                        java.lang.String nextArgRequired2222222 = getNextArgRequired();
                        upperCase = nextArgRequired2222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222222 = getNextArgRequired();
                        java.lang.String nextArg222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222222 != null ? java.lang.Float.parseFloat(nextArg222222) : 28.0f, i2, nextArgRequired3222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 7:
                        i2 = 6;
                        java.lang.String nextArgRequired22222222 = getNextArgRequired();
                        upperCase = nextArgRequired22222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222222 = getNextArgRequired();
                        java.lang.String nextArg2222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222222 != null ? java.lang.Float.parseFloat(nextArg2222222) : 28.0f, i2, nextArgRequired32222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case '\b':
                        i2 = 7;
                        java.lang.String nextArgRequired222222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222222 = getNextArgRequired();
                        java.lang.String nextArg22222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222222 != null ? java.lang.Float.parseFloat(nextArg22222222) : 28.0f, i2, nextArgRequired322222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case '\t':
                        i2 = 8;
                        java.lang.String nextArgRequired2222222222 = getNextArgRequired();
                        upperCase = nextArgRequired2222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222222222 = getNextArgRequired();
                        java.lang.String nextArg222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222222222 != null ? java.lang.Float.parseFloat(nextArg222222222) : 28.0f, i2, nextArgRequired3222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case '\n':
                        i2 = 9;
                        java.lang.String nextArgRequired22222222222 = getNextArgRequired();
                        upperCase = nextArgRequired22222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222222222 = getNextArgRequired();
                        java.lang.String nextArg2222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222222222 != null ? java.lang.Float.parseFloat(nextArg2222222222) : 28.0f, i2, nextArgRequired32222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 11:
                        i2 = 10;
                        java.lang.String nextArgRequired222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222222222 = getNextArgRequired();
                        java.lang.String nextArg22222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222222222 != null ? java.lang.Float.parseFloat(nextArg22222222222) : 28.0f, i2, nextArgRequired322222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case '\f':
                        i2 = 11;
                        java.lang.String nextArgRequired2222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired2222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222222222222 = getNextArgRequired();
                        java.lang.String nextArg222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222222222222 != null ? java.lang.Float.parseFloat(nextArg222222222222) : 28.0f, i2, nextArgRequired3222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case '\r':
                        i2 = 12;
                        java.lang.String nextArgRequired22222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired22222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222222222222 = getNextArgRequired();
                        java.lang.String nextArg2222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222222222222 != null ? java.lang.Float.parseFloat(nextArg2222222222222) : 28.0f, i2, nextArgRequired32222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 14:
                        i2 = 13;
                        java.lang.String nextArgRequired222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222222222222 = getNextArgRequired();
                        java.lang.String nextArg22222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222222222222 != null ? java.lang.Float.parseFloat(nextArg22222222222222) : 28.0f, i2, nextArgRequired322222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 15:
                        i2 = 14;
                        java.lang.String nextArgRequired2222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired2222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222222222222222 = getNextArgRequired();
                        java.lang.String nextArg222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222222222222222 != null ? java.lang.Float.parseFloat(nextArg222222222222222) : 28.0f, i2, nextArgRequired3222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 16:
                        i2 = 15;
                        java.lang.String nextArgRequired22222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired22222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222222222222222 = getNextArgRequired();
                        java.lang.String nextArg2222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222222222222222 != null ? java.lang.Float.parseFloat(nextArg2222222222222222) : 28.0f, i2, nextArgRequired32222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 17:
                        i2 = 16;
                        java.lang.String nextArgRequired222222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222222222222222 = getNextArgRequired();
                        java.lang.String nextArg22222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222222222222222 != null ? java.lang.Float.parseFloat(nextArg22222222222222222) : 28.0f, i2, nextArgRequired322222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 18:
                        i2 = 17;
                        java.lang.String nextArgRequired2222222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired2222222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired3222222222222222222 = getNextArgRequired();
                        java.lang.String nextArg222222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg222222222222222222 != null ? java.lang.Float.parseFloat(nextArg222222222222222222) : 28.0f, i2, nextArgRequired3222222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 19:
                        java.lang.String nextArgRequired22222222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired22222222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired32222222222222222222 = getNextArgRequired();
                        java.lang.String nextArg2222222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg2222222222222222222 != null ? java.lang.Float.parseFloat(nextArg2222222222222222222) : 28.0f, i2, nextArgRequired32222222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    case 20:
                        i2 = 19;
                        java.lang.String nextArgRequired222222222222222222222 = getNextArgRequired();
                        upperCase = nextArgRequired222222222222222222222.toUpperCase();
                        switch (upperCase.hashCode()) {
                            case -1852393868:
                                break;
                            case -1560189025:
                                break;
                            case 2402104:
                                break;
                            case 72432886:
                                break;
                            case 120640881:
                                break;
                            case 163769603:
                                break;
                            case 613283414:
                                break;
                        }
                        switch (z) {
                        }
                        java.lang.String nextArgRequired322222222222222222222 = getNextArgRequired();
                        java.lang.String nextArg22222222222222222222 = getNextArg();
                        com.android.server.power.ThermalManagerService.this.onTemperatureChanged(new android.os.Temperature(nextArg22222222222222222222 != null ? java.lang.Float.parseFloat(nextArg22222222222222222222) : 28.0f, i2, nextArgRequired322222222222222222222, i), true);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    default:
                        outPrintWriter.println("Invalid temperature type: " + nextArgRequired);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        private int runOverrideStatus() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                try {
                    int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                    if (android.os.Temperature.isValidStatus(parseInt)) {
                        synchronized (com.android.server.power.ThermalManagerService.this.mLock) {
                            com.android.server.power.ThermalManagerService.this.mIsStatusOverride = true;
                            com.android.server.power.ThermalManagerService.this.setStatusLocked(parseInt);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 0;
                    }
                    outPrintWriter.println("Invalid status: " + parseInt);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                } catch (java.lang.RuntimeException e) {
                    outPrintWriter.println("Error: " + e.toString());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Thermal service (thermalservice) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  inject-temperature TYPE STATUS NAME [VALUE]");
            outPrintWriter.println("    injects a new temperature sample for the specified device.");
            outPrintWriter.println("    type and status strings follow the names in android.os.Temperature.");
            outPrintWriter.println("  override-status STATUS");
            outPrintWriter.println("    sets and locks the thermal status of the device to STATUS.");
            outPrintWriter.println("    status code is defined in android.os.Temperature.");
            outPrintWriter.println("  reset");
            outPrintWriter.println("    unlocks the thermal status of the device.");
            outPrintWriter.println();
        }
    }

    static abstract class ThermalHalWrapper {
        protected static final java.lang.String TAG = com.android.server.power.ThermalManagerService.ThermalHalWrapper.class.getSimpleName();
        protected static final int THERMAL_HAL_DEATH_COOKIE = 5612;
        protected com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback mCallback;
        protected final java.lang.Object mHalLock = new java.lang.Object();

        @java.lang.FunctionalInterface
        interface TemperatureChangedCallback {
            void onValues(android.os.Temperature temperature);
        }

        protected abstract boolean connectToHal();

        protected abstract void dump(java.io.PrintWriter printWriter, java.lang.String str);

        protected abstract java.util.List<android.os.CoolingDevice> getCurrentCoolingDevices(boolean z, int i);

        protected abstract java.util.List<android.os.Temperature> getCurrentTemperatures(boolean z, int i);

        @android.annotation.NonNull
        protected abstract java.util.List<android.hardware.thermal.TemperatureThreshold> getTemperatureThresholds(boolean z, int i);

        ThermalHalWrapper() {
        }

        @com.android.internal.annotations.VisibleForTesting
        protected void setCallback(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }

        protected void resendCurrentTemperatures() {
            synchronized (this.mHalLock) {
                try {
                    java.util.List<android.os.Temperature> currentTemperatures = getCurrentTemperatures(false, 0);
                    int size = currentTemperatures.size();
                    for (int i = 0; i < size; i++) {
                        this.mCallback.onValues(currentTemperatures.get(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        final class DeathRecipient implements android.os.IHwBinder.DeathRecipient {
            DeathRecipient() {
            }

            public void serviceDied(long j) {
                if (j == 5612) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL service died cookie: " + j);
                    synchronized (com.android.server.power.ThermalManagerService.ThermalHalWrapper.this.mHalLock) {
                        com.android.server.power.ThermalManagerService.ThermalHalWrapper.this.connectToHal();
                        com.android.server.power.ThermalManagerService.ThermalHalWrapper.this.resendCurrentTemperatures();
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ThermalHalAidlWrapper extends com.android.server.power.ThermalManagerService.ThermalHalWrapper implements android.os.IBinder.DeathRecipient {
        private android.hardware.thermal.IThermal mInstance = null;
        private final android.hardware.thermal.IThermalChangedCallback mThermalChangedCallback = new android.hardware.thermal.IThermalChangedCallback.Stub() { // from class: com.android.server.power.ThermalManagerService.ThermalHalAidlWrapper.1
            public void notifyThrottling(android.hardware.thermal.Temperature temperature) throws android.os.RemoteException {
                android.os.Temperature temperature2 = new android.os.Temperature(temperature.value, temperature.type, temperature.name, temperature.throttlingStatus);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.power.ThermalManagerService.ThermalHalAidlWrapper.this.mCallback.onValues(temperature2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public int getInterfaceVersion() throws android.os.RemoteException {
                return 2;
            }

            public java.lang.String getInterfaceHash() throws android.os.RemoteException {
                return "2f49c78011338b42b43d5d0e250d9b520850cc1f";
            }
        };

        ThermalHalAidlWrapper(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.Temperature> getCurrentTemperatures(boolean z, int i) {
            android.hardware.thermal.Temperature[] temperaturesWithType;
            synchronized (this.mHalLock) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mInstance == null) {
                    return arrayList;
                }
                try {
                    try {
                        temperaturesWithType = z ? this.mInstance.getTemperaturesWithType(i) : this.mInstance.getTemperatures();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentTemperatures, reconnecting", e);
                        connectToHal();
                    }
                } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e2) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices due to invalid status", e2);
                }
                if (temperaturesWithType == null) {
                    return arrayList;
                }
                for (android.hardware.thermal.Temperature temperature : temperaturesWithType) {
                    if (!android.os.Temperature.isValidStatus(temperature.throttlingStatus)) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Invalid temperature status " + temperature.throttlingStatus + " received from AIDL HAL");
                        temperature.throttlingStatus = 0;
                    }
                    if (!z || temperature.type == i) {
                        arrayList.add(new android.os.Temperature(temperature.value, temperature.type, temperature.name, temperature.throttlingStatus));
                    }
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.CoolingDevice> getCurrentCoolingDevices(boolean z, int i) {
            android.hardware.thermal.CoolingDevice[] coolingDevices;
            synchronized (this.mHalLock) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mInstance == null) {
                    return arrayList;
                }
                try {
                    try {
                        if (z) {
                            coolingDevices = this.mInstance.getCoolingDevicesWithType(i);
                        } else {
                            coolingDevices = this.mInstance.getCoolingDevices();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices, reconnecting", e);
                        connectToHal();
                    }
                } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e2) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices due to invalid status", e2);
                }
                if (coolingDevices == null) {
                    return arrayList;
                }
                for (android.hardware.thermal.CoolingDevice coolingDevice : coolingDevices) {
                    if (!android.os.CoolingDevice.isValidType(coolingDevice.type)) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Invalid cooling device type " + coolingDevice.type + " from AIDL HAL");
                    } else if (!z || coolingDevice.type == i) {
                        arrayList.add(new android.os.CoolingDevice(coolingDevice.value, coolingDevice.type, coolingDevice.name));
                    }
                }
                return arrayList;
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        @android.annotation.NonNull
        protected java.util.List<android.hardware.thermal.TemperatureThreshold> getTemperatureThresholds(boolean z, final int i) {
            synchronized (this.mHalLock) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mInstance == null) {
                    return arrayList;
                }
                try {
                    try {
                        android.hardware.thermal.TemperatureThreshold[] temperatureThresholdsWithType = z ? this.mInstance.getTemperatureThresholdsWithType(i) : this.mInstance.getTemperatureThresholds();
                        if (temperatureThresholdsWithType == null) {
                            return arrayList;
                        }
                        if (z) {
                            return (java.util.List) java.util.Arrays.stream(temperatureThresholdsWithType).filter(new java.util.function.Predicate() { // from class: com.android.server.power.ThermalManagerService$ThermalHalAidlWrapper$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(java.lang.Object obj) {
                                    boolean lambda$getTemperatureThresholds$0;
                                    lambda$getTemperatureThresholds$0 = com.android.server.power.ThermalManagerService.ThermalHalAidlWrapper.lambda$getTemperatureThresholds$0(i, (android.hardware.thermal.TemperatureThreshold) obj);
                                    return lambda$getTemperatureThresholds$0;
                                }
                            }).collect(java.util.stream.Collectors.toList());
                        }
                        return java.util.Arrays.asList(temperatureThresholdsWithType);
                    } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getTemperatureThresholds due to invalid status", e);
                        return arrayList;
                    }
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getTemperatureThresholds, reconnecting...", e2);
                    connectToHal();
                    return arrayList;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getTemperatureThresholds$0(int i, android.hardware.thermal.TemperatureThreshold temperatureThreshold) {
            return temperatureThreshold.type == i;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected boolean connectToHal() {
            synchronized (this.mHalLock) {
                initProxyAndRegisterCallback(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(android.hardware.thermal.IThermal.DESCRIPTOR + "/default")));
            }
            return this.mInstance != null;
        }

        @com.android.internal.annotations.VisibleForTesting
        void initProxyAndRegisterCallback(android.os.IBinder iBinder) {
            synchronized (this.mHalLock) {
                if (iBinder != null) {
                    this.mInstance = android.hardware.thermal.IThermal.Stub.asInterface(iBinder);
                    try {
                        iBinder.linkToDeath(this, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Unable to connect IThermal AIDL instance", e);
                        connectToHal();
                    }
                    if (this.mInstance != null) {
                        try {
                            android.util.Slog.i(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL AIDL service connected with version " + this.mInstance.getInterfaceVersion());
                            registerThermalChangedCallback();
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Unable to read interface version from Thermal HAL", e2);
                            connectToHal();
                        }
                    }
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void registerThermalChangedCallback() {
            try {
                this.mInstance.registerThermalChangedCallback(this.mThermalChangedCallback);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Unable to connect IThermal AIDL instance", e);
                connectToHal();
            } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e2) {
                android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't registerThermalChangedCallback due to invalid status", e2);
            }
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("ThermalHAL AIDL 2  connected: ");
                    sb.append(this.mInstance != null ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
                    printWriter.println(sb.toString());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            android.util.Slog.w(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal AIDL HAL died, reconnecting...");
            connectToHal();
        }
    }

    static class ThermalHal10Wrapper extends com.android.server.power.ThermalManagerService.ThermalHalWrapper {

        @com.android.internal.annotations.GuardedBy({"mHalLock"})
        private android.hardware.thermal.V1_0.IThermal mThermalHal10 = null;

        ThermalHal10Wrapper(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.Temperature> getCurrentTemperatures(final boolean z, final int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal10 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal10.getTemperatures(new android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda1
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal10Wrapper.lambda$getCurrentTemperatures$0(z, i, arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentTemperatures, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentTemperatures$0(boolean z, int i, java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V1_0.Temperature temperature = (android.hardware.thermal.V1_0.Temperature) it.next();
                    if (!z || i == temperature.type) {
                        list.add(new android.os.Temperature(temperature.currentValue, temperature.type, temperature.name, 0));
                    }
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get temperatures because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.CoolingDevice> getCurrentCoolingDevices(final boolean z, final int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal10 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal10.getCoolingDevices(new android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda0
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal10Wrapper.lambda$getCurrentCoolingDevices$1(z, i, arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentCoolingDevices$1(boolean z, int i, java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V1_0.CoolingDevice coolingDevice = (android.hardware.thermal.V1_0.CoolingDevice) it.next();
                    if (!z || i == coolingDevice.type) {
                        list.add(new android.os.CoolingDevice((long) coolingDevice.currentValue, coolingDevice.type, coolingDevice.name));
                    }
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get cooling device because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.hardware.thermal.TemperatureThreshold> getTemperatureThresholds(boolean z, int i) {
            return new java.util.ArrayList();
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected boolean connectToHal() {
            boolean z;
            synchronized (this.mHalLock) {
                z = true;
                try {
                    this.mThermalHal10 = android.hardware.thermal.V1_0.IThermal.getService(true);
                    this.mThermalHal10.linkToDeath(new com.android.server.power.ThermalManagerService.ThermalHalWrapper.DeathRecipient(), 5612L);
                    android.util.Slog.i(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 1.0 service connected, no thermal call back will be called due to legacy API.");
                } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 1.0 service not connected.");
                    this.mThermalHal10 = null;
                }
                if (this.mThermalHal10 == null) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("ThermalHAL 1.0 connected: ");
                    sb.append(this.mThermalHal10 != null ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
                    printWriter.println(sb.toString());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static class ThermalHal11Wrapper extends com.android.server.power.ThermalManagerService.ThermalHalWrapper {

        @com.android.internal.annotations.GuardedBy({"mHalLock"})
        private android.hardware.thermal.V1_1.IThermal mThermalHal11 = null;
        private final android.hardware.thermal.V1_1.IThermalCallback.Stub mThermalCallback11 = new android.hardware.thermal.V1_1.IThermalCallback.Stub() { // from class: com.android.server.power.ThermalManagerService.ThermalHal11Wrapper.1
            public void notifyThrottling(boolean z, android.hardware.thermal.V1_0.Temperature temperature) {
                android.os.Temperature temperature2 = new android.os.Temperature(temperature.currentValue, temperature.type, temperature.name, z ? 3 : 0);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.power.ThermalManagerService.ThermalHal11Wrapper.this.mCallback.onValues(temperature2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };

        ThermalHal11Wrapper(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.Temperature> getCurrentTemperatures(final boolean z, final int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal11 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal11.getTemperatures(new android.hardware.thermal.V1_0.IThermal.getTemperaturesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal11Wrapper$$ExternalSyntheticLambda1
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal11Wrapper.lambda$getCurrentTemperatures$0(z, i, arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentTemperatures, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentTemperatures$0(boolean z, int i, java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V1_0.Temperature temperature = (android.hardware.thermal.V1_0.Temperature) it.next();
                    if (!z || i == temperature.type) {
                        list.add(new android.os.Temperature(temperature.currentValue, temperature.type, temperature.name, 0));
                    }
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get temperatures because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.CoolingDevice> getCurrentCoolingDevices(final boolean z, final int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal11 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal11.getCoolingDevices(new android.hardware.thermal.V1_0.IThermal.getCoolingDevicesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal11Wrapper$$ExternalSyntheticLambda0
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal11Wrapper.lambda$getCurrentCoolingDevices$1(z, i, arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentCoolingDevices$1(boolean z, int i, java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V1_0.CoolingDevice coolingDevice = (android.hardware.thermal.V1_0.CoolingDevice) it.next();
                    if (!z || i == coolingDevice.type) {
                        list.add(new android.os.CoolingDevice((long) coolingDevice.currentValue, coolingDevice.type, coolingDevice.name));
                    }
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get cooling device because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.hardware.thermal.TemperatureThreshold> getTemperatureThresholds(boolean z, int i) {
            return new java.util.ArrayList();
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected boolean connectToHal() {
            boolean z;
            synchronized (this.mHalLock) {
                z = true;
                try {
                    this.mThermalHal11 = android.hardware.thermal.V1_1.IThermal.getService(true);
                    this.mThermalHal11.linkToDeath(new com.android.server.power.ThermalManagerService.ThermalHalWrapper.DeathRecipient(), 5612L);
                    this.mThermalHal11.registerThermalCallback(this.mThermalCallback11);
                    android.util.Slog.i(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 1.1 service connected, limited thermal functions due to legacy API.");
                } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 1.1 service not connected.");
                    this.mThermalHal11 = null;
                }
                if (this.mThermalHal11 == null) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("ThermalHAL 1.1 connected: ");
                    sb.append(this.mThermalHal11 != null ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
                    printWriter.println(sb.toString());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static class ThermalHal20Wrapper extends com.android.server.power.ThermalManagerService.ThermalHalWrapper {

        @com.android.internal.annotations.GuardedBy({"mHalLock"})
        private android.hardware.thermal.V2_0.IThermal mThermalHal20 = null;
        private final android.hardware.thermal.V2_0.IThermalChangedCallback.Stub mThermalCallback20 = new android.hardware.thermal.V2_0.IThermalChangedCallback.Stub() { // from class: com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.1
            public void notifyThrottling(android.hardware.thermal.V2_0.Temperature temperature) {
                android.os.Temperature temperature2 = new android.os.Temperature(temperature.value, temperature.type, temperature.name, temperature.throttlingStatus);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.this.mCallback.onValues(temperature2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };

        ThermalHal20Wrapper(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback temperatureChangedCallback) {
            this.mCallback = temperatureChangedCallback;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.Temperature> getCurrentTemperatures(boolean z, int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal20 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal20.getCurrentTemperatures(z, i, new android.hardware.thermal.V2_0.IThermal.getCurrentTemperaturesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda3
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.lambda$getCurrentTemperatures$0(arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentTemperatures, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentTemperatures$0(java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V2_0.Temperature temperature = (android.hardware.thermal.V2_0.Temperature) it.next();
                    if (!android.os.Temperature.isValidStatus(temperature.throttlingStatus)) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Invalid status data from HAL");
                        temperature.throttlingStatus = 0;
                    }
                    list.add(new android.os.Temperature(temperature.value, temperature.type, temperature.name, temperature.throttlingStatus));
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get temperatures because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.os.CoolingDevice> getCurrentCoolingDevices(boolean z, int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal20 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal20.getCurrentCoolingDevices(z, i, new android.hardware.thermal.V2_0.IThermal.getCurrentCoolingDevicesCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda1
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.lambda$getCurrentCoolingDevices$1(arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getCurrentCoolingDevices, reconnecting...", e);
                    connectToHal();
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getCurrentCoolingDevices$1(java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.thermal.V2_0.CoolingDevice coolingDevice = (android.hardware.thermal.V2_0.CoolingDevice) it.next();
                    list.add(new android.os.CoolingDevice(coolingDevice.value, coolingDevice.type, coolingDevice.name));
                }
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get cooling device because of HAL error: " + thermalStatus.debugMessage);
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected java.util.List<android.hardware.thermal.TemperatureThreshold> getTemperatureThresholds(boolean z, int i) {
            synchronized (this.mHalLock) {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                if (this.mThermalHal20 == null) {
                    return arrayList;
                }
                try {
                    this.mThermalHal20.getTemperatureThresholds(z, i, new android.hardware.thermal.V2_0.IThermal.getTemperatureThresholdsCallback() { // from class: com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda2
                        public final void onValues(android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList2) {
                            com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.this.lambda$getTemperatureThresholds$2(arrayList, thermalStatus, arrayList2);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't getTemperatureThresholds, reconnecting...", e);
                }
                return arrayList;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTemperatureThresholds$2(java.util.List list, android.hardware.thermal.V1_0.ThermalStatus thermalStatus, java.util.ArrayList arrayList) {
            if (thermalStatus.code == 0) {
                list.addAll((java.util.Collection) arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.power.ThermalManagerService$ThermalHal20Wrapper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.hardware.thermal.TemperatureThreshold convertToAidlTemperatureThreshold;
                        convertToAidlTemperatureThreshold = com.android.server.power.ThermalManagerService.ThermalHal20Wrapper.this.convertToAidlTemperatureThreshold((android.hardware.thermal.V2_0.TemperatureThreshold) obj);
                        return convertToAidlTemperatureThreshold;
                    }
                }).collect(java.util.stream.Collectors.toList()));
                return;
            }
            android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Couldn't get temperature thresholds because of HAL error: " + thermalStatus.debugMessage);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.hardware.thermal.TemperatureThreshold convertToAidlTemperatureThreshold(android.hardware.thermal.V2_0.TemperatureThreshold temperatureThreshold) {
            android.hardware.thermal.TemperatureThreshold temperatureThreshold2 = new android.hardware.thermal.TemperatureThreshold();
            temperatureThreshold2.name = temperatureThreshold.name;
            temperatureThreshold2.type = temperatureThreshold.type;
            temperatureThreshold2.coldThrottlingThresholds = temperatureThreshold.coldThrottlingThresholds;
            temperatureThreshold2.hotThrottlingThresholds = temperatureThreshold.hotThrottlingThresholds;
            return temperatureThreshold2;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected boolean connectToHal() {
            boolean z;
            synchronized (this.mHalLock) {
                z = true;
                try {
                    this.mThermalHal20 = android.hardware.thermal.V2_0.IThermal.getService(true);
                    this.mThermalHal20.linkToDeath(new com.android.server.power.ThermalManagerService.ThermalHalWrapper.DeathRecipient(), 5612L);
                    this.mThermalHal20.registerThermalChangedCallback(this.mThermalCallback20, false, 0);
                    android.util.Slog.i(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 2.0 service connected.");
                } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
                    android.util.Slog.e(com.android.server.power.ThermalManagerService.ThermalHalWrapper.TAG, "Thermal HAL 2.0 service not connected.");
                    this.mThermalHal20 = null;
                }
                if (this.mThermalHal20 == null) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper
        protected void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            synchronized (this.mHalLock) {
                try {
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("ThermalHAL 2.0 connected: ");
                    sb.append(this.mThermalHal20 != null ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
                    printWriter.println(sb.toString());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class TemperatureWatcher {
        private static final float DEGREES_BETWEEN_ZERO_AND_ONE = 30.0f;
        private static final int INACTIVITY_THRESHOLD_MILLIS = 10000;
        private static final int MINIMUM_SAMPLE_COUNT = 3;
        private static final int RING_BUFFER_SIZE = 30;
        private final android.os.Handler mHandler = com.android.internal.os.BackgroundThread.getHandler();

        @com.android.internal.annotations.GuardedBy({"mSamples"})
        @com.android.internal.annotations.VisibleForTesting
        final android.util.ArrayMap<java.lang.String, java.util.ArrayList<com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample>> mSamples = new android.util.ArrayMap<>();

        @com.android.internal.annotations.GuardedBy({"mSamples"})
        @com.android.internal.annotations.VisibleForTesting
        android.util.ArrayMap<java.lang.String, java.lang.Float> mSevereThresholds = new android.util.ArrayMap<>();

        @com.android.internal.annotations.GuardedBy({"mSamples"})
        float[] mHeadroomThresholds = new float[7];

        @com.android.internal.annotations.GuardedBy({"mSamples"})
        private long mLastForecastCallTimeMillis = 0;

        @com.android.internal.annotations.VisibleForTesting
        long mInactivityThresholdMillis = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;

        TemperatureWatcher() {
        }

        void updateThresholds() {
            synchronized (this.mSamples) {
                try {
                    java.util.List<android.hardware.thermal.TemperatureThreshold> temperatureThresholds = com.android.server.power.ThermalManagerService.this.mHalWrapper.getTemperatureThresholds(true, 3);
                    if (android.os.Flags.allowThermalHeadroomThresholds()) {
                        java.util.Arrays.fill(this.mHeadroomThresholds, Float.NaN);
                    }
                    for (int i = 0; i < temperatureThresholds.size(); i++) {
                        android.hardware.thermal.TemperatureThreshold temperatureThreshold = temperatureThresholds.get(i);
                        if (temperatureThreshold.hotThrottlingThresholds.length > 3) {
                            float f = temperatureThreshold.hotThrottlingThresholds[3];
                            if (!java.lang.Float.isNaN(f)) {
                                this.mSevereThresholds.put(temperatureThreshold.name, java.lang.Float.valueOf(f));
                                if (android.os.Flags.allowThermalHeadroomThresholds()) {
                                    for (int i2 = 1; i2 <= 6; i2++) {
                                        if (i2 != 3 && temperatureThreshold.hotThrottlingThresholds.length > i2) {
                                            updateHeadroomThreshold(i2, temperatureThreshold.hotThrottlingThresholds[i2], f);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void updateHeadroomThreshold(int i, float f, float f2) {
            if (!java.lang.Float.isNaN(f)) {
                synchronized (this.mSamples) {
                    try {
                        if (i == 3) {
                            this.mHeadroomThresholds[i] = 1.0f;
                            return;
                        }
                        float normalizeTemperature = normalizeTemperature(f, f2);
                        if (java.lang.Float.isNaN(this.mHeadroomThresholds[i])) {
                            this.mHeadroomThresholds[i] = normalizeTemperature;
                        } else {
                            this.mHeadroomThresholds[i] = java.lang.Math.min(this.mHeadroomThresholds[i], normalizeTemperature);
                        }
                    } finally {
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateTemperature() {
            synchronized (this.mSamples) {
                try {
                    if (android.os.SystemClock.elapsedRealtime() - this.mLastForecastCallTimeMillis < this.mInactivityThresholdMillis) {
                        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.power.ThermalManagerService$TemperatureWatcher$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.power.ThermalManagerService.TemperatureWatcher.this.updateTemperature();
                            }
                        }, 1000L);
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        java.util.List<android.os.Temperature> currentTemperatures = com.android.server.power.ThermalManagerService.this.mHalWrapper.getCurrentTemperatures(true, 3);
                        for (int i = 0; i < currentTemperatures.size(); i++) {
                            android.os.Temperature temperature = currentTemperatures.get(i);
                            if (!java.lang.Float.isNaN(temperature.getValue())) {
                                java.util.ArrayList<com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample> computeIfAbsent = this.mSamples.computeIfAbsent(temperature.getName(), new java.util.function.Function() { // from class: com.android.server.power.ThermalManagerService$TemperatureWatcher$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final java.lang.Object apply(java.lang.Object obj) {
                                        java.util.ArrayList lambda$updateTemperature$0;
                                        lambda$updateTemperature$0 = com.android.server.power.ThermalManagerService.TemperatureWatcher.lambda$updateTemperature$0((java.lang.String) obj);
                                        return lambda$updateTemperature$0;
                                    }
                                });
                                if (computeIfAbsent.size() == 30) {
                                    computeIfAbsent.remove(0);
                                }
                                computeIfAbsent.add(new com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample(elapsedRealtime, temperature.getValue()));
                            }
                        }
                        return;
                    }
                    this.mSamples.clear();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.ArrayList lambda$updateTemperature$0(java.lang.String str) {
            return new java.util.ArrayList(30);
        }

        @com.android.internal.annotations.VisibleForTesting
        float getSlopeOf(java.util.List<com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample> list) {
            long j = 0;
            float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            long j2 = 0;
            float f2 = 0.0f;
            for (int i = 0; i < list.size(); i++) {
                com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample sample = list.get(i);
                j2 += sample.time;
                f2 += sample.temperature;
            }
            long size = j2 / list.size();
            float size2 = f2 / list.size();
            for (int i2 = 0; i2 < list.size(); i2++) {
                com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample sample2 = list.get(i2);
                long j3 = sample2.time - size;
                j += j3 * j3;
                f += j3 * (sample2.temperature - size2);
            }
            return f / j;
        }

        @com.android.internal.annotations.VisibleForTesting
        static float normalizeTemperature(float f, float f2) {
            float f3 = f2 - DEGREES_BETWEEN_ZERO_AND_ONE;
            if (f <= f3) {
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            }
            return (f - f3) / DEGREES_BETWEEN_ZERO_AND_ONE;
        }

        float getForecast(int i) {
            synchronized (this.mSamples) {
                try {
                    this.mLastForecastCallTimeMillis = android.os.SystemClock.elapsedRealtime();
                    if (this.mSamples.isEmpty()) {
                        updateTemperature();
                    }
                    if (this.mSamples.isEmpty()) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.TAG, "No temperature samples found");
                        com.android.internal.util.FrameworkStatsLog.write(773, android.os.Binder.getCallingUid(), 5, Float.NaN);
                        return Float.NaN;
                    }
                    if (this.mSevereThresholds.isEmpty()) {
                        android.util.Slog.e(com.android.server.power.ThermalManagerService.TAG, "No temperature thresholds found");
                        com.android.internal.util.FrameworkStatsLog.write(773, android.os.Binder.getCallingUid(), 6, Float.NaN);
                        return Float.NaN;
                    }
                    float f = Float.NaN;
                    int i2 = 0;
                    for (java.util.Map.Entry<java.lang.String, java.util.ArrayList<com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample>> entry : this.mSamples.entrySet()) {
                        java.lang.String key = entry.getKey();
                        java.util.ArrayList<com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample> value = entry.getValue();
                        java.lang.Float f2 = this.mSevereThresholds.get(key);
                        if (f2 == null) {
                            i2++;
                            android.util.Slog.e(com.android.server.power.ThermalManagerService.TAG, "No threshold found for " + key);
                        } else {
                            float f3 = value.get(0).temperature;
                            if (value.size() < 3) {
                                float normalizeTemperature = normalizeTemperature(f3, f2.floatValue());
                                if (java.lang.Float.isNaN(f) || normalizeTemperature > f) {
                                    f = normalizeTemperature;
                                }
                            } else {
                                float normalizeTemperature2 = normalizeTemperature(f3 + (getSlopeOf(value) * i * 1000.0f), f2.floatValue());
                                if (java.lang.Float.isNaN(f) || normalizeTemperature2 > f) {
                                    f = normalizeTemperature2;
                                }
                            }
                        }
                    }
                    if (i2 == this.mSamples.size()) {
                        com.android.internal.util.FrameworkStatsLog.write(773, android.os.Binder.getCallingUid(), 6, Float.NaN);
                    } else {
                        com.android.internal.util.FrameworkStatsLog.write(773, android.os.Binder.getCallingUid(), 1, f);
                    }
                    return f;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample createSampleForTesting(long j, float f) {
            return new com.android.server.power.ThermalManagerService.TemperatureWatcher.Sample(j, f);
        }

        @com.android.internal.annotations.VisibleForTesting
        class Sample {
            public float temperature;
            public long time;

            Sample(long j, float f) {
                this.time = j;
                this.temperature = f;
            }
        }
    }
}
