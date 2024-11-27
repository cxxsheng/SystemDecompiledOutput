package com.android.internal.jank;

/* loaded from: classes4.dex */
public class InteractionJankMonitor {

    @java.lang.Deprecated
    public static final int CUJ_BIOMETRIC_PROMPT_TRANSITION = 56;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_CLOCK_MOVE_ANIMATION = 70;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_OCCLUSION = 64;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PASSWORD_APPEAR = 17;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PASSWORD_DISAPPEAR = 20;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PATTERN_APPEAR = 18;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PATTERN_DISAPPEAR = 21;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PIN_APPEAR = 19;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_PIN_DISAPPEAR = 22;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_TRANSITION_FROM_AOD = 23;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_TRANSITION_TO_AOD = 24;

    @java.lang.Deprecated
    public static final int CUJ_LOCKSCREEN_UNLOCK_ANIMATION = 29;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_ADD = 14;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_APP_START = 16;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_HEADS_UP_APPEAR = 12;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_HEADS_UP_DISAPPEAR = 13;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_REMOVE = 15;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_EXPAND_COLLAPSE = 0;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_QS_EXPAND_COLLAPSE = 5;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_QS_SCROLL_SWIPE = 6;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_ROW_EXPAND = 3;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_ROW_SWIPE = 4;

    @java.lang.Deprecated
    public static final int CUJ_NOTIFICATION_SHADE_SCROLL_FLING = 2;

    @java.lang.Deprecated
    public static final int CUJ_PIP_TRANSITION = 35;

    @java.lang.Deprecated
    public static final int CUJ_PREDICTIVE_BACK_CROSS_ACTIVITY = 84;

    @java.lang.Deprecated
    public static final int CUJ_PREDICTIVE_BACK_CROSS_TASK = 85;

    @java.lang.Deprecated
    public static final int CUJ_PREDICTIVE_BACK_HOME = 86;

    @java.lang.Deprecated
    public static final int CUJ_SCREEN_OFF = 40;

    @java.lang.Deprecated
    public static final int CUJ_SCREEN_OFF_SHOW_AOD = 41;

    @java.lang.Deprecated
    public static final int CUJ_SETTINGS_PAGE_SCROLL = 28;

    @java.lang.Deprecated
    public static final int CUJ_SETTINGS_SLIDER = 53;

    @java.lang.Deprecated
    public static final int CUJ_SETTINGS_TOGGLE = 57;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_HISTORY_BUTTON = 30;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_MEDIA_PLAYER = 31;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_QS_TILE = 32;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_APP_LAUNCH_FROM_SETTINGS_BUTTON = 33;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_CLEAR_ALL = 62;

    @java.lang.Deprecated
    public static final int CUJ_SHADE_DIALOG_OPEN = 58;

    @java.lang.Deprecated
    public static final int CUJ_SPLASHSCREEN_AVD = 38;

    @java.lang.Deprecated
    public static final int CUJ_SPLASHSCREEN_EXIT_ANIM = 39;

    @java.lang.Deprecated
    public static final int CUJ_SPLIT_SCREEN_DOUBLE_TAP_DIVIDER = 82;

    @java.lang.Deprecated
    public static final int CUJ_SPLIT_SCREEN_RESIZE = 52;

    @java.lang.Deprecated
    public static final int CUJ_STATUS_BAR_APP_LAUNCH_FROM_CALL_CHIP = 34;

    @java.lang.Deprecated
    public static final int CUJ_SUW_LOADING_SCREEN_FOR_STATUS = 48;

    @java.lang.Deprecated
    public static final int CUJ_SUW_LOADING_TO_NEXT_FLOW = 47;

    @java.lang.Deprecated
    public static final int CUJ_SUW_LOADING_TO_SHOW_INFO_WITH_ACTIONS = 45;

    @java.lang.Deprecated
    public static final int CUJ_SUW_SHOW_FUNCTION_SCREEN_WITH_ACTIONS = 46;

    @java.lang.Deprecated
    public static final int CUJ_TAKE_SCREENSHOT = 54;

    @java.lang.Deprecated
    public static final int CUJ_TASKBAR_COLLAPSE = 61;

    @java.lang.Deprecated
    public static final int CUJ_TASKBAR_EXPAND = 60;

    @java.lang.Deprecated
    public static final int CUJ_UNFOLD_ANIM = 44;

    @java.lang.Deprecated
    public static final int CUJ_USER_DIALOG_OPEN = 59;

    @java.lang.Deprecated
    public static final int CUJ_USER_SWITCH = 37;

    @java.lang.Deprecated
    public static final int CUJ_VOLUME_CONTROL = 55;
    private static final boolean DEFAULT_DEBUG_OVERLAY_ENABLED = false;
    private static final int DEFAULT_SAMPLING_INTERVAL = 1;
    private static final int DEFAULT_TRACE_THRESHOLD_FRAME_TIME_MILLIS = 64;
    private static final int DEFAULT_TRACE_THRESHOLD_MISSED_FRAMES = 3;
    static final long EXECUTOR_TASK_TIMEOUT = 500;
    private static final int MAX_LENGTH_SESSION_NAME = 100;
    private static final java.lang.String SETTINGS_DEBUG_OVERLAY_ENABLED_KEY = "debug_overlay_enabled";
    private static final java.lang.String SETTINGS_ENABLED_KEY = "enabled";
    private static final java.lang.String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
    private static final java.lang.String SETTINGS_THRESHOLD_FRAME_TIME_MILLIS_KEY = "trace_threshold_frame_time_millis";
    private static final java.lang.String SETTINGS_THRESHOLD_MISSED_FRAMES_KEY = "trace_threshold_missed_frames";
    private com.android.internal.jank.InteractionMonitorDebugOverlay mDebugOverlay;
    private final com.android.internal.jank.DisplayResolutionTracker mDisplayResolutionTracker;
    private final android.os.Handler mWorker;
    private static final java.lang.String TAG = com.android.internal.jank.InteractionJankMonitor.class.getSimpleName();
    private static final java.lang.String ACTION_PREFIX = com.android.internal.jank.InteractionJankMonitor.class.getCanonicalName();
    private static final java.lang.String DEFAULT_WORKER_NAME = TAG + "-Worker";
    private static final long DEFAULT_TIMEOUT_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(2);
    private static final boolean DEFAULT_ENABLED = android.os.Build.IS_DEBUGGABLE;
    public static final java.lang.String ACTION_SESSION_END = ACTION_PREFIX + ".ACTION_SESSION_END";
    public static final java.lang.String ACTION_SESSION_CANCEL = ACTION_PREFIX + ".ACTION_SESSION_CANCEL";
    private final android.util.SparseArray<com.android.internal.jank.InteractionJankMonitor.RunningTracker> mRunningTrackers = new android.util.SparseArray<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private int mDebugBgColor = android.graphics.Color.CYAN;
    private double mDebugYOffset = 0.1d;
    private volatile boolean mEnabled = DEFAULT_ENABLED;
    private int mSamplingInterval = 1;
    private int mTraceThresholdMissedFrames = 3;
    private int mTraceThresholdFrameTimeMillis = 64;

    /* JADX INFO: Access modifiers changed from: private */
    @java.lang.FunctionalInterface
    interface TimeFunction {
        void invoke(long j, long j2, long j3);
    }

    private static class InstanceHolder {
        public static final com.android.internal.jank.InteractionJankMonitor INSTANCE = new com.android.internal.jank.InteractionJankMonitor(new android.os.HandlerThread(com.android.internal.jank.InteractionJankMonitor.DEFAULT_WORKER_NAME));

        private InstanceHolder() {
        }
    }

    public static com.android.internal.jank.InteractionJankMonitor getInstance() {
        return com.android.internal.jank.InteractionJankMonitor.InstanceHolder.INSTANCE;
    }

    public InteractionJankMonitor(android.os.HandlerThread handlerThread) {
        handlerThread.start();
        this.mWorker = handlerThread.getThreadHandler();
        this.mDisplayResolutionTracker = new com.android.internal.jank.DisplayResolutionTracker(this.mWorker);
        final android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null || currentApplication.checkCallingOrSelfPermission(android.Manifest.permission.READ_DEVICE_CONFIG) != 0) {
            android.util.Log.w(TAG, "Initializing without READ_DEVICE_CONFIG permission. enabled=" + this.mEnabled + ", interval=" + this.mSamplingInterval + ", missedFrameThreshold=" + this.mTraceThresholdMissedFrames + ", frameTimeThreshold=" + this.mTraceThresholdFrameTimeMillis + ", package=" + (currentApplication == null ? "null" : currentApplication.getPackageName()));
        } else {
            this.mWorker.post(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionJankMonitor.this.lambda$new$0(currentApplication);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.Context context) {
        try {
            updateProperties(android.provider.DeviceConfig.getProperties("interaction_jank_monitor", new java.lang.String[0]));
            android.provider.DeviceConfig.addOnPropertiesChangedListener("interaction_jank_monitor", new android.os.HandlerExecutor(this.mWorker), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.internal.jank.InteractionJankMonitor.this.updateProperties(properties);
                }
            });
        } catch (java.lang.SecurityException e) {
            android.util.Log.d(TAG, "Can't get properties: READ_DEVICE_CONFIG granted=" + context.checkCallingOrSelfPermission(android.Manifest.permission.READ_DEVICE_CONFIG) + ", package=" + context.getPackageName());
        }
    }

    public com.android.internal.jank.FrameTracker createFrameTracker(com.android.internal.jank.InteractionJankMonitor.Configuration configuration) {
        android.view.View view = configuration.mView;
        return new com.android.internal.jank.FrameTracker(configuration, view == null ? null : new com.android.internal.jank.FrameTracker.ThreadedRendererWrapper(view.getThreadedRenderer()), view != null ? new com.android.internal.jank.FrameTracker.ViewRootWrapper(view.getViewRootImpl()) : null, new com.android.internal.jank.FrameTracker.SurfaceControlWrapper(), new com.android.internal.jank.FrameTracker.ChoreographerWrapper(android.view.Choreographer.getInstance()), new com.android.internal.jank.FrameTracker.FrameMetricsWrapper(), new com.android.internal.jank.FrameTracker.StatsLogWrapper(this.mDisplayResolutionTracker), this.mTraceThresholdMissedFrames, this.mTraceThresholdFrameTimeMillis, new com.android.internal.jank.InteractionJankMonitor.AnonymousClass1(configuration));
    }

    /* renamed from: com.android.internal.jank.InteractionJankMonitor$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.internal.jank.FrameTracker.FrameTrackerListener {
        final /* synthetic */ com.android.internal.jank.InteractionJankMonitor.Configuration val$config;

        AnonymousClass1(com.android.internal.jank.InteractionJankMonitor.Configuration configuration) {
            this.val$config = configuration;
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void onCujEvents(final com.android.internal.jank.FrameTracker frameTracker, final java.lang.String str, final int i) {
            android.os.Handler handler = this.val$config.getHandler();
            final com.android.internal.jank.InteractionJankMonitor.Configuration configuration = this.val$config;
            handler.runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionJankMonitor.AnonymousClass1.this.lambda$onCujEvents$0(configuration, frameTracker, str, i);
                }
            }, com.android.internal.jank.InteractionJankMonitor.EXECUTOR_TASK_TIMEOUT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCujEvents$0(com.android.internal.jank.InteractionJankMonitor.Configuration configuration, com.android.internal.jank.FrameTracker frameTracker, java.lang.String str, int i) {
            com.android.internal.jank.InteractionJankMonitor.this.handleCujEvents(configuration.mCujType, frameTracker, str, i);
        }

        @Override // com.android.internal.jank.FrameTracker.FrameTrackerListener
        public void triggerPerfetto(final com.android.internal.jank.InteractionJankMonitor.Configuration configuration) {
            com.android.internal.jank.InteractionJankMonitor.this.mWorker.post(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.util.PerfettoTrigger.trigger(com.android.internal.jank.InteractionJankMonitor.Configuration.this.getPerfettoTrigger());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCujEvents(int i, com.android.internal.jank.FrameTracker frameTracker, java.lang.String str, int i2) {
        if (needRemoveTasks(str, i2)) {
            removeTrackerIfCurrent(i, frameTracker, i2);
        }
    }

    private static boolean needRemoveTasks(java.lang.String str, int i) {
        return (str.equals(ACTION_SESSION_END) && i != 0) || (str.equals(ACTION_SESSION_CANCEL) && i != 16 && i != 19);
    }

    public boolean isInstrumenting(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mRunningTrackers.contains(i);
        }
        return contains;
    }

    public boolean begin(android.view.View view, int i) {
        try {
            return begin(com.android.internal.jank.InteractionJankMonitor.Configuration.Builder.withView(i, view));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    public boolean begin(com.android.internal.jank.InteractionJankMonitor.Configuration.Builder builder) {
        try {
            final com.android.internal.jank.InteractionJankMonitor.Configuration build = builder.build();
            postEventLogToWorkerThread(new com.android.internal.jank.InteractionJankMonitor.TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda2
                @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
                public final void invoke(long j, long j2, long j3) {
                    com.android.internal.jank.EventLogTags.writeJankCujEventsBeginRequest(r0.mCujType, j, j2, j3, com.android.internal.jank.InteractionJankMonitor.Configuration.this.mTag);
                }
            });
            final com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult = new com.android.internal.jank.InteractionJankMonitor.TrackerResult();
            if (!build.getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionJankMonitor.this.lambda$begin$2(trackerResult, build);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                android.util.Log.d(TAG, "begin failed due to timeout, CUJ=" + com.android.internal.jank.Cuj.getNameOfCuj(build.mCujType));
                return false;
            }
            return trackerResult.mResult;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d(TAG, "Build configuration failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$begin$2(com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult, com.android.internal.jank.InteractionJankMonitor.Configuration configuration) {
        trackerResult.mResult = beginInternal(configuration);
    }

    private boolean beginInternal(final com.android.internal.jank.InteractionJankMonitor.Configuration configuration) {
        com.android.internal.jank.InteractionJankMonitor.RunningTracker putTrackerIfNoCurrent;
        final int i = configuration.mCujType;
        if (!shouldMonitor() || (putTrackerIfNoCurrent = putTrackerIfNoCurrent(i, new java.util.function.Supplier() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.internal.jank.InteractionJankMonitor.RunningTracker lambda$beginInternal$4;
                lambda$beginInternal$4 = com.android.internal.jank.InteractionJankMonitor.this.lambda$beginInternal$4(configuration, i);
                return lambda$beginInternal$4;
            }
        })) == null) {
            return false;
        }
        putTrackerIfNoCurrent.mTracker.begin();
        scheduleTimeoutAction(putTrackerIfNoCurrent.mConfig, putTrackerIfNoCurrent.mTimeoutAction);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.internal.jank.InteractionJankMonitor.RunningTracker lambda$beginInternal$4(com.android.internal.jank.InteractionJankMonitor.Configuration configuration, final int i) {
        return new com.android.internal.jank.InteractionJankMonitor.RunningTracker(configuration, createFrameTracker(configuration), new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.jank.InteractionJankMonitor.this.lambda$beginInternal$3(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginInternal$3(int i) {
        cancel(i, 19);
    }

    public boolean shouldMonitor() {
        return this.mEnabled && java.util.concurrent.ThreadLocalRandom.current().nextInt(this.mSamplingInterval) == 0;
    }

    public void scheduleTimeoutAction(com.android.internal.jank.InteractionJankMonitor.Configuration configuration, java.lang.Runnable runnable) {
        configuration.getHandler().postDelayed(runnable, configuration.mTimeout);
    }

    public boolean end(final int i) {
        postEventLogToWorkerThread(new com.android.internal.jank.InteractionJankMonitor.TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda8
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                com.android.internal.jank.EventLogTags.writeJankCujEventsEndRequest(i, j, j2, j3);
            }
        });
        final com.android.internal.jank.InteractionJankMonitor.RunningTracker tracker = getTracker(i);
        if (tracker == null) {
            return false;
        }
        try {
            final com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult = new com.android.internal.jank.InteractionJankMonitor.TrackerResult();
            if (!tracker.mConfig.getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionJankMonitor.this.lambda$end$6(trackerResult, tracker);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                android.util.Log.d(TAG, "end failed due to timeout, CUJ=" + com.android.internal.jank.Cuj.getNameOfCuj(i));
                return false;
            }
            return trackerResult.mResult;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d(TAG, "Execute end task failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$end$6(com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult, com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker) {
        trackerResult.mResult = endInternal(runningTracker);
    }

    private boolean endInternal(com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker) {
        if (removeTrackerIfCurrent(runningTracker, 0)) {
            return false;
        }
        runningTracker.mTracker.end(0);
        return true;
    }

    public boolean cancel(final int i) {
        postEventLogToWorkerThread(new com.android.internal.jank.InteractionJankMonitor.TimeFunction() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda5
            @Override // com.android.internal.jank.InteractionJankMonitor.TimeFunction
            public final void invoke(long j, long j2, long j3) {
                com.android.internal.jank.EventLogTags.writeJankCujEventsCancelRequest(i, j, j2, j3);
            }
        });
        return cancel(i, 16);
    }

    public boolean cancel(int i, final int i2) {
        final com.android.internal.jank.InteractionJankMonitor.RunningTracker tracker = getTracker(i);
        if (tracker == null) {
            return false;
        }
        try {
            final com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult = new com.android.internal.jank.InteractionJankMonitor.TrackerResult();
            if (!tracker.mConfig.getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.jank.InteractionJankMonitor.this.lambda$cancel$8(trackerResult, tracker, i2);
                }
            }, EXECUTOR_TASK_TIMEOUT)) {
                android.util.Log.d(TAG, "cancel failed due to timeout, CUJ=" + com.android.internal.jank.Cuj.getNameOfCuj(i));
                return false;
            }
            return trackerResult.mResult;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d(TAG, "Execute cancel task failed!", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$8(com.android.internal.jank.InteractionJankMonitor.TrackerResult trackerResult, com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker, int i) {
        trackerResult.mResult = cancelInternal(runningTracker, i);
    }

    private boolean cancelInternal(com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker, int i) {
        if (removeTrackerIfCurrent(runningTracker, i)) {
            return false;
        }
        runningTracker.mTracker.cancel(i);
        return true;
    }

    private com.android.internal.jank.InteractionJankMonitor.RunningTracker putTrackerIfNoCurrent(int i, java.util.function.Supplier<com.android.internal.jank.InteractionJankMonitor.RunningTracker> supplier) {
        synchronized (this.mLock) {
            if (this.mRunningTrackers.contains(i)) {
                return null;
            }
            com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker = supplier.get();
            if (runningTracker == null) {
                return null;
            }
            this.mRunningTrackers.put(i, runningTracker);
            if (this.mDebugOverlay != null) {
                this.mDebugOverlay.onTrackerAdded(i, runningTracker);
            }
            return runningTracker;
        }
    }

    private com.android.internal.jank.InteractionJankMonitor.RunningTracker getTracker(int i) {
        com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker;
        synchronized (this.mLock) {
            runningTracker = this.mRunningTrackers.get(i);
        }
        return runningTracker;
    }

    private boolean removeTrackerIfCurrent(com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker, int i) {
        return removeTrackerIfCurrent(runningTracker.mConfig.mCujType, runningTracker.mTracker, i);
    }

    private boolean removeTrackerIfCurrent(int i, com.android.internal.jank.FrameTracker frameTracker, int i2) {
        synchronized (this.mLock) {
            com.android.internal.jank.InteractionJankMonitor.RunningTracker runningTracker = this.mRunningTrackers.get(i);
            if (runningTracker != null && runningTracker.mTracker == frameTracker) {
                runningTracker.mConfig.getHandler().removeCallbacks(runningTracker.mTimeoutAction);
                this.mRunningTrackers.remove(i);
                if (this.mDebugOverlay != null) {
                    this.mDebugOverlay.onTrackerRemoved(i, i2, this.mRunningTrackers);
                }
                return false;
            }
            return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateProperties(android.provider.DeviceConfig.Properties properties) {
        char c;
        for (java.lang.String str : properties.getKeyset()) {
            boolean z = false;
            switch (str.hashCode()) {
                case -1609594047:
                    if (str.equals("enabled")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -464409256:
                    if (str.equals(SETTINGS_THRESHOLD_FRAME_TIME_MILLIS_KEY)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -94225891:
                    if (str.equals("sampling_interval")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1147866214:
                    if (str.equals(SETTINGS_DEBUG_OVERLAY_ENABLED_KEY)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1589403900:
                    if (str.equals(SETTINGS_THRESHOLD_MISSED_FRAMES_KEY)) {
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
                    this.mSamplingInterval = properties.getInt(str, 1);
                    break;
                case 1:
                    this.mTraceThresholdMissedFrames = properties.getInt(str, 3);
                    break;
                case 2:
                    this.mTraceThresholdFrameTimeMillis = properties.getInt(str, 64);
                    break;
                case 3:
                    this.mEnabled = properties.getBoolean(str, DEFAULT_ENABLED);
                    break;
                case 4:
                    if (android.os.Build.IS_DEBUGGABLE && properties.getBoolean(str, false)) {
                        z = true;
                    }
                    if (z && this.mDebugOverlay == null) {
                        this.mDebugOverlay = new com.android.internal.jank.InteractionMonitorDebugOverlay(this.mLock, this.mDebugBgColor, this.mDebugYOffset);
                        break;
                    } else if (!z && this.mDebugOverlay != null) {
                        this.mDebugOverlay.dispose();
                        this.mDebugOverlay = null;
                        break;
                    }
                    break;
                default:
                    android.util.Log.w(TAG, "Got a change event for an unknown property: " + str + " => " + properties.getString(str, ""));
                    break;
            }
        }
    }

    @java.lang.Deprecated
    public static java.lang.String getNameOfInteraction(int i) {
        return com.android.internal.jank.Cuj.getNameOfInteraction(i);
    }

    @java.lang.Deprecated
    public static java.lang.String getNameOfCuj(int i) {
        return com.android.internal.jank.Cuj.getNameOfCuj(i);
    }

    public void configDebugOverlay(int i, double d) {
        this.mDebugBgColor = i;
        this.mDebugYOffset = d;
    }

    private void postEventLogToWorkerThread(final com.android.internal.jank.InteractionJankMonitor.TimeFunction timeFunction) {
        final long convert = java.util.concurrent.TimeUnit.NANOSECONDS.convert(java.time.Instant.now().getEpochSecond(), java.util.concurrent.TimeUnit.SECONDS) + r0.getNano();
        final long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
        final long uptimeNanos = android.os.SystemClock.uptimeNanos();
        this.mWorker.post(new java.lang.Runnable() { // from class: com.android.internal.jank.InteractionJankMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.jank.InteractionJankMonitor.TimeFunction.this.invoke(convert, elapsedRealtimeNanos, uptimeNanos);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TrackerResult {
        private boolean mResult;

        private TrackerResult() {
        }
    }

    public static class Configuration {
        private final android.content.Context mContext;
        private final int mCujType;
        private final boolean mDeferMonitor;
        private final android.os.Handler mHandler;
        private final java.lang.String mSessionName;
        private final android.view.SurfaceControl mSurfaceControl;
        private final boolean mSurfaceOnly;
        private final java.lang.String mTag;
        private final long mTimeout;
        private final android.view.View mView;

        public static class Builder {
            private final int mAttrCujType;
            private android.view.SurfaceControl mAttrSurfaceControl;
            private boolean mAttrSurfaceOnly;
            private android.view.View mAttrView = null;
            private android.content.Context mAttrContext = null;
            private long mAttrTimeout = com.android.internal.jank.InteractionJankMonitor.DEFAULT_TIMEOUT_MS;
            private java.lang.String mAttrTag = "";
            private boolean mAttrDeferMonitor = true;

            public static com.android.internal.jank.InteractionJankMonitor.Configuration.Builder withSurface(int i, android.content.Context context, android.view.SurfaceControl surfaceControl) {
                return new com.android.internal.jank.InteractionJankMonitor.Configuration.Builder(i).setContext(context).setSurfaceControl(surfaceControl).setSurfaceOnly(true);
            }

            public static com.android.internal.jank.InteractionJankMonitor.Configuration.Builder withView(int i, android.view.View view) {
                return new com.android.internal.jank.InteractionJankMonitor.Configuration.Builder(i).setView(view).setContext(view.getContext());
            }

            private Builder(int i) {
                this.mAttrCujType = i;
            }

            private com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setView(android.view.View view) {
                this.mAttrView = view;
                return this;
            }

            public com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setTimeout(long j) {
                this.mAttrTimeout = j;
                return this;
            }

            public com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setTag(java.lang.String str) {
                this.mAttrTag = str;
                return this;
            }

            private com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setSurfaceOnly(boolean z) {
                this.mAttrSurfaceOnly = z;
                return this;
            }

            private com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setContext(android.content.Context context) {
                this.mAttrContext = context;
                return this;
            }

            private com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setSurfaceControl(android.view.SurfaceControl surfaceControl) {
                this.mAttrSurfaceControl = surfaceControl;
                return this;
            }

            public com.android.internal.jank.InteractionJankMonitor.Configuration.Builder setDeferMonitorForAnimationStart(boolean z) {
                this.mAttrDeferMonitor = z;
                return this;
            }

            public com.android.internal.jank.InteractionJankMonitor.Configuration build() throws java.lang.IllegalArgumentException {
                return new com.android.internal.jank.InteractionJankMonitor.Configuration(this.mAttrCujType, this.mAttrView, this.mAttrTag, this.mAttrTimeout, this.mAttrSurfaceOnly, this.mAttrContext, this.mAttrSurfaceControl, this.mAttrDeferMonitor);
            }
        }

        private Configuration(int i, android.view.View view, java.lang.String str, long j, boolean z, android.content.Context context, android.view.SurfaceControl surfaceControl, boolean z2) {
            this.mCujType = i;
            this.mTag = str;
            this.mSessionName = generateSessionName(com.android.internal.jank.Cuj.getNameOfCuj(i), str);
            this.mTimeout = j;
            this.mView = view;
            this.mSurfaceOnly = z;
            this.mContext = context == null ? view != null ? view.getContext().getApplicationContext() : null : context;
            this.mSurfaceControl = surfaceControl;
            this.mDeferMonitor = z2;
            validate();
            this.mHandler = this.mSurfaceOnly ? this.mContext.getMainThreadHandler() : this.mView.getHandler();
        }

        public static java.lang.String generateSessionName(java.lang.String str, java.lang.String str2) {
            int length;
            boolean z = !android.text.TextUtils.isEmpty(str2);
            if (z && str2.length() > (length = 100 - str.length())) {
                str2 = str2.substring(0, length - 3).concat(android.telecom.Logging.Session.TRUNCATE_STRING);
            }
            if (z) {
                return android.text.TextUtils.formatSimple("J<%s::%s>", str, str2);
            }
            return android.text.TextUtils.formatSimple("J<%s>", str);
        }

        private void validate() {
            boolean z;
            boolean z2;
            boolean z3;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z4 = true;
            if (this.mTag != null) {
                z = false;
            } else {
                sb.append("Invalid tag; ");
                z = true;
            }
            if (this.mTimeout < 0) {
                sb.append("Invalid timeout value; ");
                z = true;
            }
            if (this.mSurfaceOnly) {
                if (this.mContext == null) {
                    sb.append("Must pass in a context if only instrument surface; ");
                    z = true;
                }
                if (this.mSurfaceControl == null || !this.mSurfaceControl.isValid()) {
                    sb.append("Must pass in a valid surface control if only instrument surface; ");
                } else {
                    z4 = z;
                }
            } else if (hasValidView()) {
                z4 = z;
            } else {
                if (this.mView == null) {
                    z2 = false;
                    z3 = false;
                } else {
                    boolean isAttachedToWindow = this.mView.isAttachedToWindow();
                    z3 = this.mView.getViewRootImpl() != null;
                    r3 = isAttachedToWindow;
                    z2 = this.mView.getThreadedRenderer() != null;
                }
                sb.append("invalid view: view=" + this.mView + ", attached=" + r3 + ", hasViewRoot=" + z3 + ", hasRenderer=" + z2);
            }
            if (z4) {
                throw new java.lang.IllegalArgumentException(sb.toString());
            }
        }

        boolean hasValidView() {
            return this.mSurfaceOnly || !(this.mView == null || !this.mView.isAttachedToWindow() || this.mView.getViewRootImpl() == null || this.mView.getThreadedRenderer() == null);
        }

        public boolean isSurfaceOnly() {
            return this.mSurfaceOnly;
        }

        public android.view.SurfaceControl getSurfaceControl() {
            return this.mSurfaceControl;
        }

        public android.view.View getView() {
            return this.mView;
        }

        public boolean shouldDeferMonitor() {
            return this.mDeferMonitor;
        }

        public android.os.Handler getHandler() {
            return this.mHandler;
        }

        public int getDisplayId() {
            return (this.mSurfaceOnly ? this.mContext : this.mView.getContext()).getDisplayId();
        }

        public java.lang.String getSessionName() {
            return this.mSessionName;
        }

        public int getStatsdInteractionType() {
            return com.android.internal.jank.Cuj.getStatsdInteractionType(this.mCujType);
        }

        public boolean logToStatsd() {
            return com.android.internal.jank.Cuj.logToStatsd(this.mCujType);
        }

        public java.lang.String getPerfettoTrigger() {
            return android.text.TextUtils.formatSimple("com.android.telemetry.interaction-jank-monitor-%d", java.lang.Integer.valueOf(this.mCujType));
        }

        public int getCujType() {
            return this.mCujType;
        }
    }

    static class RunningTracker {
        public final com.android.internal.jank.InteractionJankMonitor.Configuration mConfig;
        public final java.lang.Runnable mTimeoutAction;
        public final com.android.internal.jank.FrameTracker mTracker;

        RunningTracker(com.android.internal.jank.InteractionJankMonitor.Configuration configuration, com.android.internal.jank.FrameTracker frameTracker, java.lang.Runnable runnable) {
            this.mConfig = configuration;
            this.mTracker = frameTracker;
            this.mTimeoutAction = runnable;
        }
    }
}
