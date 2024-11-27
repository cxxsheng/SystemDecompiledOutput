package com.android.server;

/* loaded from: classes.dex */
public class Watchdog implements android.util.Dumpable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int COMPLETED = 0;
    private static final boolean DB = false;
    public static final boolean DEBUG = false;
    private static final long DEFAULT_TIMEOUT = 60000;
    static final int OVERDUE = 3;
    private static final int PRE_WATCHDOG_TIMEOUT_RATIO = 4;
    private static final java.lang.String PROP_FATAL_LOOP_COUNT = "framework_watchdog.fatal_count";
    private static final java.lang.String PROP_FATAL_LOOP_WINDOWS_SECS = "framework_watchdog.fatal_window.second";
    static final java.lang.String TAG = "Watchdog";
    private static final java.lang.String TIMEOUT_HISTORY_FILE = "/data/system/watchdog-timeout-history.txt";
    static final int WAITED_UNTIL_PRE_WATCHDOG = 2;
    static final int WAITING = 1;
    private static com.android.server.Watchdog sWatchdog;
    private com.android.server.am.ActivityManagerService mActivity;
    private android.app.IActivityController mController;
    private final com.android.server.Watchdog.HandlerChecker mMonitorChecker;
    private final com.android.server.am.TraceErrorLogger mTraceErrorLogger;
    public static final java.lang.String[] NATIVE_STACKS_OF_INTEREST = {"/system/bin/audioserver", "/system/bin/cameraserver", "/system/bin/drmserver", "/system/bin/keystore2", "/system/bin/mediadrmserver", "/system/bin/mediaserver", "/system/bin/netd", "/system/bin/sdcard", "/system/bin/servicemanager", "/system/bin/surfaceflinger", "/system/bin/vold", "media.extractor", "media.metrics", "media.codec", "media.swcodec", "media.transcoding", "com.android.bluetooth", "/apex/com.android.art/bin/artd", "/apex/com.android.os.statsd/bin/statsd"};
    public static final java.util.List<java.lang.String> HAL_INTERFACES_OF_INTEREST = java.util.Arrays.asList("android.hardware.audio@4.0::IDevicesFactory", "android.hardware.audio@5.0::IDevicesFactory", "android.hardware.audio@6.0::IDevicesFactory", "android.hardware.audio@7.0::IDevicesFactory", android.hardware.biometrics.face.V1_0.IBiometricsFace.kInterfaceName, android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint.kInterfaceName, "android.hardware.bluetooth@1.0::IBluetoothHci", "android.hardware.camera.provider@2.4::ICameraProvider", "android.hardware.gnss@1.0::IGnss", "android.hardware.graphics.allocator@2.0::IAllocator", "android.hardware.graphics.allocator@4.0::IAllocator", "android.hardware.graphics.composer@2.1::IComposer", android.hardware.health.V2_0.IHealth.kInterfaceName, "android.hardware.light@2.0::ILight", "android.hardware.media.c2@1.0::IComponentStore", "android.hardware.media.omx@1.0::IOmx", "android.hardware.media.omx@1.0::IOmxStore", "android.hardware.neuralnetworks@1.0::IDevice", "android.hardware.power@1.0::IPower", "android.hardware.power.stats@1.0::IPowerStats", "android.hardware.sensors@1.0::ISensors", "android.hardware.sensors@2.0::ISensors", "android.hardware.sensors@2.1::ISensors", "android.hardware.vibrator@1.0::IVibrator", "android.hardware.vr@1.0::IVr", "android.system.suspend@1.0::ISystemSuspend");
    public static final java.lang.String[] AIDL_INTERFACE_PREFIXES_OF_INTEREST = {"android.hardware.audio.core.IModule/", "android.hardware.audio.core.IConfig/", "android.hardware.audio.effect.IFactory/", "android.hardware.biometrics.face.IFace/", "android.hardware.biometrics.fingerprint.IFingerprint/", "android.hardware.bluetooth.IBluetoothHci/", "android.hardware.camera.provider.ICameraProvider/", "android.hardware.gnss.IGnss/", "android.hardware.graphics.allocator.IAllocator/", "android.hardware.graphics.composer3.IComposer/", "android.hardware.health.IHealth/", "android.hardware.input.processor.IInputProcessor/", "android.hardware.light.ILights/", "android.hardware.neuralnetworks.IDevice/", "android.hardware.power.IPower/", "android.hardware.power.stats.IPowerStats/", "android.hardware.sensors.ISensors/", "android.hardware.vibrator.IVibrator/", "android.hardware.vibrator.IVibratorManager/", "android.system.suspend.ISystemSuspend/"};
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<com.android.server.Watchdog.HandlerCheckerAndTimeout> mHandlerCheckers = new java.util.ArrayList<>();
    private boolean mAllowRestart = true;
    private volatile long mWatchdogTimeoutMillis = 60000;
    private final java.util.List<java.lang.Integer> mInterestingJavaPids = new java.util.ArrayList();
    private final java.lang.Thread mThread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.Watchdog$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.Watchdog.this.run();
        }
    }, "watchdog");

    public interface Monitor {
        void monitor();
    }

    static final class HandlerCheckerAndTimeout {
        private final java.util.Optional<java.lang.Long> mCustomTimeoutMillis;
        private final com.android.server.Watchdog.HandlerChecker mHandler;

        private HandlerCheckerAndTimeout(com.android.server.Watchdog.HandlerChecker handlerChecker, java.util.Optional<java.lang.Long> optional) {
            this.mHandler = handlerChecker;
            this.mCustomTimeoutMillis = optional;
        }

        com.android.server.Watchdog.HandlerChecker checker() {
            return this.mHandler;
        }

        java.util.Optional<java.lang.Long> customTimeoutMillis() {
            return this.mCustomTimeoutMillis;
        }

        static com.android.server.Watchdog.HandlerCheckerAndTimeout withDefaultTimeout(com.android.server.Watchdog.HandlerChecker handlerChecker) {
            return new com.android.server.Watchdog.HandlerCheckerAndTimeout(handlerChecker, java.util.Optional.empty());
        }

        static com.android.server.Watchdog.HandlerCheckerAndTimeout withCustomTimeout(com.android.server.Watchdog.HandlerChecker handlerChecker, long j) {
            return new com.android.server.Watchdog.HandlerCheckerAndTimeout(handlerChecker, java.util.Optional.of(java.lang.Long.valueOf(j)));
        }
    }

    public static class HandlerChecker implements java.lang.Runnable {
        private java.time.Clock mClock;
        private boolean mCompleted;
        private com.android.server.Watchdog.Monitor mCurrentMonitor;
        private final android.os.Handler mHandler;
        private java.lang.Object mLock;
        private final java.util.ArrayList<com.android.server.Watchdog.Monitor> mMonitorQueue;
        private final java.util.ArrayList<com.android.server.Watchdog.Monitor> mMonitors;
        private final java.lang.String mName;
        private int mPauseCount;
        private long mPauseEndTimeMillis;
        private long mStartTimeMillis;
        private long mWaitMaxMillis;

        HandlerChecker(android.os.Handler handler, java.lang.String str, java.lang.Object obj, java.time.Clock clock) {
            this.mMonitors = new java.util.ArrayList<>();
            this.mMonitorQueue = new java.util.ArrayList<>();
            this.mHandler = handler;
            this.mName = str;
            this.mLock = obj;
            this.mCompleted = true;
            this.mClock = clock;
        }

        HandlerChecker(android.os.Handler handler, java.lang.String str, java.lang.Object obj) {
            this(handler, str, obj, android.os.SystemClock.uptimeClock());
        }

        void addMonitorLocked(com.android.server.Watchdog.Monitor monitor) {
            this.mMonitorQueue.add(monitor);
        }

        public void scheduleCheckLocked(long j) {
            this.mWaitMaxMillis = j;
            if (this.mCompleted) {
                this.mMonitors.addAll(this.mMonitorQueue);
                this.mMonitorQueue.clear();
            }
            long millis = this.mClock.millis();
            boolean z = this.mPauseCount > 0 || this.mPauseEndTimeMillis > millis;
            if ((this.mMonitors.size() == 0 && isHandlerPolling()) || z) {
                this.mCompleted = true;
                return;
            }
            if (!this.mCompleted) {
                return;
            }
            this.mCompleted = false;
            this.mCurrentMonitor = null;
            this.mStartTimeMillis = millis;
            this.mPauseEndTimeMillis = 0L;
            this.mHandler.postAtFrontOfQueue(this);
        }

        boolean isHandlerPolling() {
            return this.mHandler.getLooper().getQueue().isPolling();
        }

        public int getCompletionStateLocked() {
            if (this.mCompleted) {
                return 0;
            }
            long millis = this.mClock.millis() - this.mStartTimeMillis;
            if (millis < this.mWaitMaxMillis / 4) {
                return 1;
            }
            if (millis < this.mWaitMaxMillis) {
                return 2;
            }
            return 3;
        }

        public java.lang.Thread getThread() {
            return this.mHandler.getLooper().getThread();
        }

        public java.lang.String getName() {
            return this.mName;
        }

        java.lang.String describeBlockedStateLocked() {
            java.lang.String str;
            if (this.mCurrentMonitor == null) {
                str = "Blocked in handler";
            } else {
                str = "Blocked in monitor " + this.mCurrentMonitor.getClass().getName();
            }
            return str + " on " + this.mName + " (" + getThread().getName() + ") for " + ((this.mClock.millis() - this.mStartTimeMillis) / 1000) + "s";
        }

        @Override // java.lang.Runnable
        public void run() {
            int size = this.mMonitors.size();
            for (int i = 0; i < size; i++) {
                synchronized (this.mLock) {
                    this.mCurrentMonitor = this.mMonitors.get(i);
                }
                this.mCurrentMonitor.monitor();
            }
            synchronized (this.mLock) {
                this.mCompleted = true;
                this.mCurrentMonitor = null;
            }
        }

        public void pauseForLocked(int i, java.lang.String str) {
            this.mPauseEndTimeMillis = this.mClock.millis() + i;
            this.mCompleted = true;
            android.util.Slog.i(com.android.server.Watchdog.TAG, "Pausing of HandlerChecker: " + this.mName + " for reason: " + str + ". Pause end time: " + this.mPauseEndTimeMillis);
        }

        public void pauseLocked(java.lang.String str) {
            this.mPauseCount++;
            this.mCompleted = true;
            android.util.Slog.i(com.android.server.Watchdog.TAG, "Pausing HandlerChecker: " + this.mName + " for reason: " + str + ". Pause count: " + this.mPauseCount);
        }

        public void resumeLocked(java.lang.String str) {
            if (this.mPauseCount > 0) {
                this.mPauseCount--;
                android.util.Slog.i(com.android.server.Watchdog.TAG, "Resuming HandlerChecker: " + this.mName + " for reason: " + str + ". Pause count: " + this.mPauseCount);
                return;
            }
            android.util.Slog.wtf(com.android.server.Watchdog.TAG, "Already resumed HandlerChecker: " + this.mName);
        }

        public java.lang.String toString() {
            return "CheckerHandler for " + this.mName;
        }
    }

    final class RebootRequestReceiver extends android.content.BroadcastReceiver {
        RebootRequestReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getIntExtra("nowait", 0) != 0) {
                com.android.server.Watchdog.this.rebootSystem("Received ACTION_REBOOT broadcast");
                return;
            }
            android.util.Slog.w(com.android.server.Watchdog.TAG, "Unsupported ACTION_REBOOT broadcast: " + intent);
        }
    }

    private static final class BinderThreadMonitor implements com.android.server.Watchdog.Monitor {
        private BinderThreadMonitor() {
        }

        @Override // com.android.server.Watchdog.Monitor
        public void monitor() {
            android.os.Binder.blockUntilThreadAvailable();
        }
    }

    public static com.android.server.Watchdog getInstance() {
        if (sWatchdog == null) {
            sWatchdog = new com.android.server.Watchdog();
        }
        return sWatchdog;
    }

    private Watchdog() {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread("watchdog.monitor", 0, true);
        serviceThread.start();
        this.mMonitorChecker = new com.android.server.Watchdog.HandlerChecker(new android.os.Handler(serviceThread.getLooper()), "monitor thread", this.mLock);
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(this.mMonitorChecker));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.FgThread.getHandler(), "foreground thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(new android.os.Handler(android.os.Looper.getMainLooper()), "main thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.UiThread.getHandler(), "ui thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.IoThread.getHandler(), "i/o thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.DisplayThread.getHandler(), "display thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.AnimationThread.getHandler(), "animation thread", this.mLock)));
        this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(com.android.server.wm.SurfaceAnimationThread.getHandler(), "surface animation thread", this.mLock)));
        addMonitor(new com.android.server.Watchdog.BinderThreadMonitor());
        this.mInterestingJavaPids.add(java.lang.Integer.valueOf(android.os.Process.myPid()));
        this.mTraceErrorLogger = new com.android.server.am.TraceErrorLogger();
    }

    public void start() {
        this.mThread.start();
    }

    public void init(android.content.Context context, com.android.server.am.ActivityManagerService activityManagerService) {
        this.mActivity = activityManagerService;
        context.registerReceiver(new com.android.server.Watchdog.RebootRequestReceiver(), new android.content.IntentFilter("android.intent.action.REBOOT"), "android.permission.REBOOT", null);
    }

    private static class SettingsObserver extends android.database.ContentObserver {
        private final android.content.Context mContext;
        private final android.net.Uri mUri;
        private final com.android.server.Watchdog mWatchdog;

        SettingsObserver(android.content.Context context, com.android.server.Watchdog watchdog) {
            super(com.android.internal.os.BackgroundThread.getHandler());
            this.mUri = android.provider.Settings.Global.getUriFor("system_server_watchdog_timeout_ms");
            this.mContext = context;
            this.mWatchdog = watchdog;
            onChange();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }

        public void onChange() {
            try {
                this.mWatchdog.updateWatchdogTimeout(android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "system_server_watchdog_timeout_ms", 60000L));
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(com.android.server.Watchdog.TAG, "Exception while reading settings " + e.getMessage(), e);
            }
        }
    }

    public void registerSettingsObserver(android.content.Context context) {
        context.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("system_server_watchdog_timeout_ms"), false, new com.android.server.Watchdog.SettingsObserver(context, this), 0);
    }

    void updateWatchdogTimeout(long j) {
        if (!android.os.Build.IS_USERDEBUG && j <= 20000) {
            j = 20001;
        }
        this.mWatchdogTimeoutMillis = j;
        android.util.Slog.i(TAG, "Watchdog timeout updated to " + this.mWatchdogTimeoutMillis + " millis");
    }

    private static boolean isInterestingJavaProcess(java.lang.String str) {
        return str.equals(com.android.server.StorageManagerService.sMediaStoreAuthorityProcessName) || str.equals("com.android.phone");
    }

    public void processStarted(java.lang.String str, int i) {
        if (isInterestingJavaProcess(str)) {
            android.util.Slog.i(TAG, "Interesting Java process " + str + " started. Pid " + i);
            synchronized (this.mLock) {
                this.mInterestingJavaPids.add(java.lang.Integer.valueOf(i));
            }
        }
    }

    public void processDied(java.lang.String str, int i) {
        if (isInterestingJavaProcess(str)) {
            android.util.Slog.i(TAG, "Interesting Java process " + str + " died. Pid " + i);
            synchronized (this.mLock) {
                this.mInterestingJavaPids.remove(java.lang.Integer.valueOf(i));
            }
        }
    }

    public void setActivityController(android.app.IActivityController iActivityController) {
        synchronized (this.mLock) {
            this.mController = iActivityController;
        }
    }

    public void setAllowRestart(boolean z) {
        synchronized (this.mLock) {
            this.mAllowRestart = z;
        }
    }

    public void addMonitor(com.android.server.Watchdog.Monitor monitor) {
        synchronized (this.mLock) {
            this.mMonitorChecker.addMonitorLocked(monitor);
        }
    }

    public void addThread(android.os.Handler handler) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withDefaultTimeout(new com.android.server.Watchdog.HandlerChecker(handler, handler.getLooper().getThread().getName(), this.mLock)));
        }
    }

    public void addThread(android.os.Handler handler, long j) {
        synchronized (this.mLock) {
            this.mHandlerCheckers.add(com.android.server.Watchdog.HandlerCheckerAndTimeout.withCustomTimeout(new com.android.server.Watchdog.HandlerChecker(handler, handler.getLooper().getThread().getName(), this.mLock), j));
        }
    }

    public void pauseWatchingCurrentThreadFor(int i, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.Watchdog.HandlerCheckerAndTimeout> it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    com.android.server.Watchdog.HandlerChecker checker = it.next().checker();
                    if (java.lang.Thread.currentThread().equals(checker.getThread())) {
                        checker.pauseForLocked(i, str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void pauseWatchingMonitorsFor(int i, java.lang.String str) {
        this.mMonitorChecker.pauseForLocked(i, str);
    }

    public void pauseWatchingCurrentThread(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.Watchdog.HandlerCheckerAndTimeout> it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    com.android.server.Watchdog.HandlerChecker checker = it.next().checker();
                    if (java.lang.Thread.currentThread().equals(checker.getThread())) {
                        checker.pauseLocked(str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void resumeWatchingCurrentThread(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.Watchdog.HandlerCheckerAndTimeout> it = this.mHandlerCheckers.iterator();
                while (it.hasNext()) {
                    com.android.server.Watchdog.HandlerChecker checker = it.next().checker();
                    if (java.lang.Thread.currentThread().equals(checker.getThread())) {
                        checker.resumeLocked(str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void rebootSystem(java.lang.String str) {
        android.util.Slog.i(TAG, "Rebooting system because: " + str);
        try {
            android.os.ServiceManager.getService("power").reboot(false, str, false);
        } catch (android.os.RemoteException e) {
        }
    }

    private int evaluateCheckerCompletionLocked() {
        int i = 0;
        for (int i2 = 0; i2 < this.mHandlerCheckers.size(); i2++) {
            i = java.lang.Math.max(i, this.mHandlerCheckers.get(i2).checker().getCompletionStateLocked());
        }
        return i;
    }

    private java.util.ArrayList<com.android.server.Watchdog.HandlerChecker> getCheckersWithStateLocked(int i) {
        java.util.ArrayList<com.android.server.Watchdog.HandlerChecker> arrayList = new java.util.ArrayList<>();
        for (int i2 = 0; i2 < this.mHandlerCheckers.size(); i2++) {
            com.android.server.Watchdog.HandlerChecker checker = this.mHandlerCheckers.get(i2).checker();
            if (checker.getCompletionStateLocked() == i) {
                arrayList.add(checker);
            }
        }
        return arrayList;
    }

    private java.lang.String describeCheckersLocked(java.util.List<com.android.server.Watchdog.HandlerChecker> list) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        for (int i = 0; i < list.size(); i++) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(list.get(i).describeBlockedStateLocked());
        }
        return sb.toString();
    }

    private static void addInterestingHidlPids(java.util.HashSet<java.lang.Integer> hashSet) {
        try {
            java.util.Iterator<android.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo> it = android.hidl.manager.V1_0.IServiceManager.getService().debugDump().iterator();
            while (it.hasNext()) {
                android.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo next = it.next();
                if (next.pid != -1 && HAL_INTERFACES_OF_INTEREST.contains(next.interfaceName)) {
                    hashSet.add(java.lang.Integer.valueOf(next.pid));
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, e);
        }
    }

    private static void addInterestingAidlPids(java.util.HashSet<java.lang.Integer> hashSet) {
        android.os.ServiceDebugInfo[] serviceDebugInfo = android.os.ServiceManager.getServiceDebugInfo();
        if (serviceDebugInfo == null) {
            return;
        }
        for (android.os.ServiceDebugInfo serviceDebugInfo2 : serviceDebugInfo) {
            for (java.lang.String str : AIDL_INTERFACE_PREFIXES_OF_INTEREST) {
                if (serviceDebugInfo2.name.startsWith(str)) {
                    hashSet.add(java.lang.Integer.valueOf(serviceDebugInfo2.debugPid));
                }
            }
        }
    }

    static java.util.ArrayList<java.lang.Integer> getInterestingNativePids() {
        java.util.HashSet hashSet = new java.util.HashSet();
        addInterestingAidlPids(hashSet);
        addInterestingHidlPids(hashSet);
        int[] pidsForCommands = android.os.Process.getPidsForCommands(NATIVE_STACKS_OF_INTEREST);
        if (pidsForCommands != null) {
            for (int i : pidsForCommands) {
                hashSet.add(java.lang.Integer.valueOf(i));
            }
        }
        return new java.util.ArrayList<>(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void run() {
        java.util.ArrayList<java.lang.Integer> arrayList;
        boolean z;
        boolean z2;
        java.util.ArrayList<com.android.server.Watchdog.HandlerChecker> arrayList2;
        java.lang.String str;
        android.app.IActivityController iActivityController;
        boolean z3 = false;
        while (true) {
            java.util.Collections.emptyList();
            long j = this.mWatchdogTimeoutMillis;
            long j2 = j / 4;
            synchronized (this.mLock) {
                for (int i = 0; i < this.mHandlerCheckers.size(); i++) {
                    try {
                        com.android.server.Watchdog.HandlerCheckerAndTimeout handlerCheckerAndTimeout = this.mHandlerCheckers.get(i);
                        handlerCheckerAndTimeout.checker().scheduleCheckLocked(handlerCheckerAndTimeout.customTimeoutMillis().orElse(java.lang.Long.valueOf(android.os.Build.HW_TIMEOUT_MULTIPLIER * j)).longValue());
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                char c = 0;
                for (long j3 = j2; j3 > 0; j3 = j2 - (android.os.SystemClock.uptimeMillis() - uptimeMillis)) {
                    if (android.os.Debug.isDebuggerConnected()) {
                        c = 2;
                    }
                    try {
                        this.mLock.wait(j3);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.wtf(TAG, e);
                    }
                    if (android.os.Debug.isDebuggerConnected()) {
                        c = 2;
                    }
                }
                int evaluateCheckerCompletionLocked = evaluateCheckerCompletionLocked();
                if (evaluateCheckerCompletionLocked == 0) {
                    z3 = false;
                } else {
                    boolean z4 = true;
                    if (evaluateCheckerCompletionLocked != 1) {
                        if (evaluateCheckerCompletionLocked == 2) {
                            if (!z3) {
                                android.util.Slog.i(TAG, "WAITED_UNTIL_PRE_WATCHDOG");
                                arrayList2 = getCheckersWithStateLocked(2);
                                str = describeCheckersLocked(arrayList2);
                                z = true;
                                arrayList = new java.util.ArrayList<>(this.mInterestingJavaPids);
                                z2 = true;
                            }
                        } else {
                            java.util.ArrayList<com.android.server.Watchdog.HandlerChecker> checkersWithStateLocked = getCheckersWithStateLocked(3);
                            java.lang.String describeCheckersLocked = describeCheckersLocked(checkersWithStateLocked);
                            boolean z5 = this.mAllowRestart;
                            arrayList = new java.util.ArrayList<>(this.mInterestingJavaPids);
                            z = z5;
                            z2 = false;
                            z4 = z3;
                            arrayList2 = checkersWithStateLocked;
                            str = describeCheckersLocked;
                        }
                        logWatchog(z2, str, arrayList);
                        if (z2) {
                            z3 = z4;
                        } else {
                            synchronized (this.mLock) {
                                iActivityController = this.mController;
                            }
                            if (iActivityController != null) {
                                android.util.Slog.i(TAG, "Reporting stuck state to activity controller");
                                try {
                                    android.os.Binder.setDumpDisabled("Service dumps disabled due to hung system process.");
                                    if (iActivityController.systemNotResponding(str) >= 0) {
                                        android.util.Slog.i(TAG, "Activity controller requested to coninue to wait");
                                        z3 = false;
                                    }
                                } catch (android.os.RemoteException e2) {
                                }
                            }
                            if (android.os.Debug.isDebuggerConnected()) {
                                c = 2;
                            }
                            if (c >= 2) {
                                android.util.Slog.w(TAG, "Debugger connected: Watchdog is *not* killing the system process");
                            } else if (c > 0) {
                                android.util.Slog.w(TAG, "Debugger was connected: Watchdog is *not* killing the system process");
                            } else if (!z) {
                                android.util.Slog.w(TAG, "Restart not allowed: Watchdog is *not* killing the system process");
                            } else {
                                android.util.Slog.w(TAG, "*** WATCHDOG KILLING SYSTEM PROCESS: " + str);
                                com.android.server.WatchdogDiagnostics.diagnoseCheckers(arrayList2);
                                android.util.Slog.w(TAG, "*** GOODBYE!");
                                if (!android.os.Build.IS_USER && isCrashLoopFound() && !android.sysprop.WatchdogProperties.should_ignore_fatal_count().orElse(false).booleanValue()) {
                                    breakCrashLoop();
                                }
                                android.os.Process.killProcess(android.os.Process.myPid());
                                java.lang.System.exit(10);
                            }
                            z3 = false;
                        }
                    }
                }
            }
        }
    }

    private void logWatchog(boolean z, java.lang.String str, java.util.ArrayList<java.lang.Integer> arrayList) {
        java.lang.String str2;
        java.lang.String logLinesForSystemServerTraceFile = com.android.server.criticalevents.CriticalEventLog.getInstance().logLinesForSystemServerTraceFile();
        final java.util.UUID generateErrorId = this.mTraceErrorLogger.generateErrorId();
        if (this.mTraceErrorLogger.isAddErrorIdEnabled()) {
            this.mTraceErrorLogger.addProcessInfoAndErrorIdToTrace("system_server", android.os.Process.myPid(), generateErrorId);
            this.mTraceErrorLogger.addSubjectToTrace(str, generateErrorId);
        }
        if (z) {
            com.android.server.criticalevents.CriticalEventLog.getInstance().logHalfWatchdog(str);
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SYSTEM_SERVER_PRE_WATCHDOG_OCCURRED);
            str2 = "pre_watchdog";
        } else {
            com.android.server.criticalevents.CriticalEventLog.getInstance().logWatchdog(str, generateErrorId);
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.WATCHDOG, str);
            com.android.internal.util.FrameworkStatsLog.write(185, str);
            str2 = "watchdog";
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        final java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(com.android.server.ResourcePressureUtil.currentPsiState());
        com.android.internal.os.ProcessCpuTracker processCpuTracker = new com.android.internal.os.ProcessCpuTracker(false);
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        final java.io.File dumpStackTraces = com.android.server.am.StackTracesDumpHelper.dumpStackTraces(arrayList, processCpuTracker, new android.util.SparseBooleanArray(), java.util.concurrent.CompletableFuture.completedFuture(getInterestingNativePids()), stringWriter, str, logLinesForSystemServerTraceFile, new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), null);
        android.os.SystemClock.sleep(5000L);
        processCpuTracker.update();
        sb.append(processCpuTracker.printCurrentState(uptimeMillis, 10));
        sb.append(stringWriter.getBuffer());
        if (!z) {
            doSysRq('w');
            doSysRq('l');
        }
        final java.lang.String str3 = str2;
        java.lang.Thread thread = new java.lang.Thread("watchdogWriteToDropbox") { // from class: com.android.server.Watchdog.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (com.android.server.Watchdog.this.mActivity != null) {
                    com.android.server.Watchdog.this.mActivity.addErrorToDropBox(str3, null, "system_server", null, null, null, null, sb.toString(), dumpStackTraces, null, null, null, generateErrorId, null);
                }
            }
        };
        thread.start();
        try {
            thread.join(2000L);
        } catch (java.lang.InterruptedException e) {
        }
    }

    private void doSysRq(char c) {
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter("/proc/sysrq-trigger");
            fileWriter.write(c);
            fileWriter.close();
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to write to /proc/sysrq-trigger", e);
        }
    }

    private void resetTimeoutHistory() {
        writeTimeoutHistory(new java.util.ArrayList());
    }

    private void writeTimeoutHistory(java.lang.Iterable<java.lang.String> iterable) {
        java.lang.String join = java.lang.String.join(",", iterable);
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(TIMEOUT_HISTORY_FILE);
            try {
                fileWriter.write(android.os.SystemProperties.get("ro.boottime.zygote"));
                fileWriter.write(":");
                fileWriter.write(join);
                fileWriter.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write file /data/system/watchdog-timeout-history.txt", e);
        }
    }

    private java.lang.String[] readTimeoutHistory() {
        java.lang.String[] strArr = new java.lang.String[0];
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(TIMEOUT_HISTORY_FILE));
            try {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return strArr;
                }
                java.lang.String[] split = readLine.trim().split(":");
                java.lang.String str = split.length >= 1 ? split[0] : "";
                java.lang.String str2 = split.length >= 2 ? split[1] : "";
                if (!android.os.SystemProperties.get("ro.boottime.zygote").equals(str) || str2.isEmpty()) {
                    bufferedReader.close();
                    return strArr;
                }
                java.lang.String[] split2 = str2.split(",");
                bufferedReader.close();
                return split2;
            } finally {
            }
        } catch (java.io.FileNotFoundException e) {
            return strArr;
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to read file /data/system/watchdog-timeout-history.txt", e2);
            return strArr;
        }
    }

    private boolean hasActiveUsbConnection() {
        try {
            if ("CONFIGURED".equals(android.os.FileUtils.readTextFile(new java.io.File("/sys/class/android_usb/android0/state"), 128, null).trim())) {
                return true;
            }
            return false;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to determine if device was on USB", e);
            return false;
        }
    }

    private boolean isCrashLoopFound() {
        int intValue = android.sysprop.WatchdogProperties.fatal_count().orElse(0).intValue();
        long millis = java.util.concurrent.TimeUnit.SECONDS.toMillis(android.sysprop.WatchdogProperties.fatal_window_seconds().orElse(0).intValue());
        if (intValue == 0 || millis == 0) {
            if (intValue != millis) {
                android.util.Slog.w(TAG, java.lang.String.format("sysprops '%s' and '%s' should be set or unset together", PROP_FATAL_LOOP_COUNT, PROP_FATAL_LOOP_WINDOWS_SECS));
            }
            return false;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.lang.String[] readTimeoutHistory = readTimeoutHistory();
        java.util.ArrayList arrayList = new java.util.ArrayList(java.util.Arrays.asList((java.lang.String[]) java.util.Arrays.copyOfRange(readTimeoutHistory, java.lang.Math.max(0, (readTimeoutHistory.length - intValue) - 1), readTimeoutHistory.length)));
        arrayList.add(java.lang.String.valueOf(elapsedRealtime));
        writeTimeoutHistory(arrayList);
        if (hasActiveUsbConnection()) {
            return false;
        }
        try {
            return arrayList.size() >= intValue && elapsedRealtime - java.lang.Long.parseLong((java.lang.String) arrayList.get(0)) < millis;
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.w(TAG, "Failed to parseLong " + ((java.lang.String) arrayList.get(0)), e);
            resetTimeoutHistory();
            return false;
        }
    }

    private void breakCrashLoop() {
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter("/dev/kmsg_debug", true);
            try {
                fileWriter.append((java.lang.CharSequence) "Fatal reset to escape the system_server crashing loop\n");
                fileWriter.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to append to kmsg", e);
        }
        doSysRq('c');
    }

    @Override // android.util.Dumpable
    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        printWriter.print("WatchdogTimeoutMillis=");
        printWriter.println(this.mWatchdogTimeoutMillis);
    }
}
