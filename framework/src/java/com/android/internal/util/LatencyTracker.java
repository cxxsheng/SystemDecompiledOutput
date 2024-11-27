package com.android.internal.util;

/* loaded from: classes5.dex */
public class LatencyTracker {
    public static final int ACTION_BACK_SYSTEM_ANIMATION = 25;
    public static final int ACTION_CHECK_CREDENTIAL = 3;
    public static final int ACTION_CHECK_CREDENTIAL_UNLOCKED = 4;
    public static final int ACTION_EXPAND_PANEL = 0;
    public static final int ACTION_FACE_WAKE_AND_UNLOCK = 7;
    public static final int ACTION_FINGERPRINT_WAKE_AND_UNLOCK = 2;
    public static final int ACTION_FOLD_TO_AOD = 18;
    public static final int ACTION_KEYGUARD_FPS_UNLOCK_TO_HOME = 24;
    public static final int ACTION_LOAD_SHARE_SHEET = 16;
    public static final int ACTION_LOCKSCREEN_UNLOCK = 11;
    public static final int ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE = 26;
    public static final int ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE_WITH_SHADE_OPEN = 27;
    public static final int ACTION_NOTIFICATION_BIG_PICTURE_LOADED = 23;
    public static final int ACTION_REQUEST_IME_HIDDEN = 21;
    public static final int ACTION_REQUEST_IME_SHOWN = 20;
    public static final int ACTION_ROTATE_SCREEN = 6;
    public static final int ACTION_ROTATE_SCREEN_CAMERA_CHECK = 9;
    public static final int ACTION_ROTATE_SCREEN_SENSOR = 10;
    public static final int ACTION_SHOW_BACK_ARROW = 15;
    public static final int ACTION_SHOW_SELECTION_TOOLBAR = 17;
    public static final int ACTION_SHOW_VOICE_INTERACTION = 19;
    public static final int ACTION_SMARTSPACE_DOORBELL = 22;
    public static final int ACTION_START_RECENTS_ANIMATION = 8;
    public static final int ACTION_SWITCH_DISPLAY_UNFOLD = 13;
    public static final int ACTION_TOGGLE_RECENTS = 1;
    public static final int ACTION_TURN_ON_SCREEN = 5;
    public static final int ACTION_UDFPS_ILLUMINATE = 14;
    public static final int ACTION_USER_SWITCH = 12;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_SAMPLING_INTERVAL = 5;
    public static final java.lang.String SETTINGS_ENABLED_KEY = "enabled";
    private static final java.lang.String SETTINGS_SAMPLING_INTERVAL_KEY = "sampling_interval";
    private static final java.lang.String TAG = "LatencyTracker";
    private static final boolean DEFAULT_ENABLED = android.os.Build.IS_DEBUGGABLE;
    private static final int[] ACTIONS_ALL = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
    public static final int[] STATSD_ACTION = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 27, 28, 29, 30, 31};
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArray<com.android.internal.util.LatencyTracker.Session> mSessions = new android.util.SparseArray<>();
    private final android.util.SparseArray<com.android.internal.util.LatencyTracker.ActionProperties> mActionPropertiesMap = new android.util.SparseArray<>();
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda2
        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            com.android.internal.util.LatencyTracker.this.updateProperties(properties);
        }
    };
    private boolean mEnabled = DEFAULT_ENABLED;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    private static final class SLatencyTrackerHolder {
        private static final com.android.internal.util.LatencyTracker sLatencyTracker = new com.android.internal.util.LatencyTracker();

        private SLatencyTrackerHolder() {
        }

        static {
            sLatencyTracker.startListeningForLatencyTrackerConfigChanges();
        }
    }

    public static com.android.internal.util.LatencyTracker getInstance(android.content.Context context) {
        return com.android.internal.util.LatencyTracker.SLatencyTrackerHolder.sLatencyTracker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProperties(android.provider.DeviceConfig.Properties properties) {
        synchronized (this.mLock) {
            int i = properties.getInt("sampling_interval", 5);
            boolean z = this.mEnabled;
            this.mEnabled = properties.getBoolean("enabled", DEFAULT_ENABLED);
            if (z != this.mEnabled) {
                android.util.Log.d(TAG, "Latency tracker " + (this.mEnabled ? "enabled" : "disabled") + android.media.MediaMetrics.SEPARATOR);
            }
            for (int i2 : ACTIONS_ALL) {
                java.lang.String lowerCase = getNameOfAction(STATSD_ACTION[i2]).toLowerCase(java.util.Locale.ROOT);
                this.mActionPropertiesMap.put(i2, new com.android.internal.util.LatencyTracker.ActionProperties(i2, properties.getBoolean(lowerCase + "_enable", this.mEnabled), properties.getInt(lowerCase + "_sample_interval", i), properties.getInt(lowerCase + "_trace_threshold", properties.getInt(lowerCase + "", -1))));
            }
            onDeviceConfigPropertiesUpdated(this.mActionPropertiesMap);
        }
    }

    public void startListeningForLatencyTrackerConfigChanges() {
        final android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null || currentApplication.checkCallingOrSelfPermission(android.Manifest.permission.READ_DEVICE_CONFIG) != 0) {
            return;
        }
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.util.LatencyTracker.this.lambda$startListeningForLatencyTrackerConfigChanges$0(currentApplication);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startListeningForLatencyTrackerConfigChanges$0(android.content.Context context) {
        try {
            updateProperties(android.provider.DeviceConfig.getProperties("latency_tracker", new java.lang.String[0]));
            android.provider.DeviceConfig.addOnPropertiesChangedListener("latency_tracker", com.android.internal.os.BackgroundThread.getExecutor(), this.mOnPropertiesChangedListener);
        } catch (java.lang.SecurityException e) {
            android.util.Log.d(TAG, "Can't get properties: READ_DEVICE_CONFIG granted=" + context.checkCallingOrSelfPermission(android.Manifest.permission.READ_DEVICE_CONFIG) + ", package=" + context.getPackageName());
        }
    }

    public void stopListeningForLatencyTrackerConfigChanges() {
        android.provider.DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
    }

    public static java.lang.String getNameOfAction(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ACTION_EXPAND_PANEL";
            case 2:
                return "ACTION_TOGGLE_RECENTS";
            case 3:
                return "ACTION_FINGERPRINT_WAKE_AND_UNLOCK";
            case 4:
                return "ACTION_CHECK_CREDENTIAL";
            case 5:
                return "ACTION_CHECK_CREDENTIAL_UNLOCKED";
            case 6:
                return "ACTION_TURN_ON_SCREEN";
            case 7:
                return "ACTION_ROTATE_SCREEN";
            case 8:
                return "ACTION_FACE_WAKE_AND_UNLOCK";
            case 9:
                return "ACTION_START_RECENTS_ANIMATION";
            case 10:
                return "ACTION_ROTATE_SCREEN_CAMERA_CHECK";
            case 11:
                return "ACTION_ROTATE_SCREEN_SENSOR";
            case 12:
                return "ACTION_LOCKSCREEN_UNLOCK";
            case 13:
                return "ACTION_USER_SWITCH";
            case 14:
                return "ACTION_SWITCH_DISPLAY_UNFOLD";
            case 15:
                return "ACTION_UDFPS_ILLUMINATE";
            case 16:
                return "ACTION_SHOW_BACK_ARROW";
            case 17:
                return "ACTION_LOAD_SHARE_SHEET";
            case 18:
                return "ACTION_SHOW_SELECTION_TOOLBAR";
            case 19:
                return "ACTION_FOLD_TO_AOD";
            case 20:
                return "ACTION_SHOW_VOICE_INTERACTION";
            case 21:
                return "ACTION_REQUEST_IME_SHOWN";
            case 22:
                return "ACTION_REQUEST_IME_HIDDEN";
            case 23:
                return "ACTION_SMARTSPACE_DOORBELL";
            case 24:
            case 25:
            case 26:
            default:
                throw new java.lang.IllegalArgumentException("Invalid action");
            case 27:
                return "ACTION_NOTIFICATION_BIG_PICTURE_LOADED";
            case 28:
                return "ACTION_KEYGUARD_FPS_UNLOCK_TO_HOME";
            case 29:
                return "ACTION_BACK_SYSTEM_ANIMATION";
            case 30:
                return "ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE";
            case 31:
                return "ACTION_NOTIFICATIONS_HIDDEN_FOR_MEASURE_WITH_SHADE_OPEN";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getTraceNameOfAction(int i, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return "L<" + getNameOfAction(STATSD_ACTION[i]) + ">";
        }
        return "L<" + getNameOfAction(STATSD_ACTION[i]) + "::" + str + ">";
    }

    private static java.lang.String getTraceTriggerNameForAction(int i) {
        return "com.android.telemetry.latency-tracker-" + getNameOfAction(STATSD_ACTION[i]);
    }

    @java.lang.Deprecated
    public static boolean isEnabled(android.content.Context context) {
        return getInstance(context).isEnabled();
    }

    @java.lang.Deprecated
    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        return z;
    }

    public static boolean isEnabled(android.content.Context context, int i) {
        return getInstance(context).isEnabled(i);
    }

    public boolean isEnabled(int i) {
        synchronized (this.mLock) {
            com.android.internal.util.LatencyTracker.ActionProperties actionProperties = this.mActionPropertiesMap.get(i);
            if (actionProperties == null) {
                return false;
            }
            return actionProperties.isEnabled();
        }
    }

    public void onActionStart(int i) {
        onActionStart(i, null);
    }

    public void onActionStart(final int i, java.lang.String str) {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                if (this.mSessions.get(i) != null) {
                    return;
                }
                com.android.internal.util.LatencyTracker.Session session = new com.android.internal.util.LatencyTracker.Session(i, str);
                session.begin(new java.lang.Runnable() { // from class: com.android.internal.util.LatencyTracker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.util.LatencyTracker.this.lambda$onActionStart$1(i);
                    }
                });
                this.mSessions.put(i, session);
            }
        }
    }

    public void onActionEnd(int i) {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                com.android.internal.util.LatencyTracker.Session session = this.mSessions.get(i);
                if (session == null) {
                    return;
                }
                session.end();
                this.mSessions.delete(i);
                logAction(i, session.duration());
            }
        }
    }

    /* renamed from: onActionCancel, reason: merged with bridge method [inline-methods] */
    public void lambda$onActionStart$1(int i) {
        synchronized (this.mLock) {
            com.android.internal.util.LatencyTracker.Session session = this.mSessions.get(i);
            if (session == null) {
                return;
            }
            session.cancel();
            this.mSessions.delete(i);
        }
    }

    public long getActiveActionStartTime(int i) {
        synchronized (this.mLock) {
            if (!this.mSessions.contains(i)) {
                return -1L;
            }
            return this.mSessions.get(i).mStartRtc;
        }
    }

    public void logAction(int i, int i2) {
        synchronized (this.mLock) {
            if (isEnabled(i)) {
                com.android.internal.util.LatencyTracker.ActionProperties actionProperties = this.mActionPropertiesMap.get(i);
                if (actionProperties == null) {
                    return;
                }
                boolean z = java.util.concurrent.ThreadLocalRandom.current().nextInt(actionProperties.getSamplingInterval()) == 0;
                int traceThreshold = actionProperties.getTraceThreshold();
                boolean z2 = traceThreshold > 0 && i2 >= traceThreshold;
                android.util.EventLog.writeEvent(36070, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                if (z2) {
                    onTriggerPerfetto(getTraceTriggerNameForAction(i));
                }
                if (z) {
                    onLogToFrameworkStats(new com.android.internal.util.LatencyTracker.FrameworkStatsLogEvent(i, 306, STATSD_ACTION[i], i2));
                }
            }
        }
    }

    static class Session {
        private final int mAction;
        private final java.lang.String mName;
        private final java.lang.String mTag;
        private java.lang.Runnable mTimeoutRunnable;
        private long mStartRtc = -1;
        private long mEndRtc = -1;

        Session(int i, java.lang.String str) {
            java.lang.String str2;
            this.mAction = i;
            this.mTag = str;
            if (android.text.TextUtils.isEmpty(this.mTag)) {
                str2 = com.android.internal.util.LatencyTracker.getNameOfAction(com.android.internal.util.LatencyTracker.STATSD_ACTION[this.mAction]);
            } else {
                str2 = com.android.internal.util.LatencyTracker.getNameOfAction(com.android.internal.util.LatencyTracker.STATSD_ACTION[this.mAction]) + "::" + this.mTag;
            }
            this.mName = str2;
        }

        java.lang.String name() {
            return this.mName;
        }

        java.lang.String traceName() {
            return com.android.internal.util.LatencyTracker.getTraceNameOfAction(this.mAction, this.mTag);
        }

        void begin(final java.lang.Runnable runnable) {
            this.mStartRtc = android.os.SystemClock.elapsedRealtime();
            android.os.Trace.asyncTraceForTrackBegin(4096L, traceName(), traceName(), 0);
            this.mTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.internal.util.LatencyTracker$Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.util.LatencyTracker.Session.this.lambda$begin$0(runnable);
                }
            };
            com.android.internal.os.BackgroundThread.getHandler().postDelayed(this.mTimeoutRunnable, java.util.concurrent.TimeUnit.SECONDS.toMillis(15L));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$begin$0(java.lang.Runnable runnable) {
            android.os.Trace.instantForTrack(4096L, traceName(), "timeout");
            runnable.run();
        }

        void end() {
            this.mEndRtc = android.os.SystemClock.elapsedRealtime();
            android.os.Trace.asyncTraceForTrackEnd(4096L, traceName(), 0);
            com.android.internal.os.BackgroundThread.getHandler().removeCallbacks(this.mTimeoutRunnable);
            this.mTimeoutRunnable = null;
        }

        void cancel() {
            android.os.Trace.instantForTrack(4096L, traceName(), "cancel");
            android.os.Trace.asyncTraceForTrackEnd(4096L, traceName(), 0);
            com.android.internal.os.BackgroundThread.getHandler().removeCallbacks(this.mTimeoutRunnable);
            this.mTimeoutRunnable = null;
        }

        int duration() {
            return (int) (this.mEndRtc - this.mStartRtc);
        }
    }

    public static class ActionProperties {
        static final java.lang.String ENABLE_SUFFIX = "_enable";
        static final java.lang.String LEGACY_TRACE_THRESHOLD_SUFFIX = "";
        static final java.lang.String SAMPLE_INTERVAL_SUFFIX = "_sample_interval";
        static final java.lang.String TRACE_THRESHOLD_SUFFIX = "_trace_threshold";
        private final int mAction;
        private final boolean mEnabled;
        private final int mSamplingInterval;
        private final int mTraceThreshold;

        public ActionProperties(int i, boolean z, int i2, int i3) {
            this.mAction = i;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) com.android.internal.util.LatencyTracker.Action.class, (java.lang.annotation.Annotation) null, this.mAction);
            this.mEnabled = z;
            this.mSamplingInterval = i2;
            this.mTraceThreshold = i3;
        }

        public int getAction() {
            return this.mAction;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public int getSamplingInterval() {
            return this.mSamplingInterval;
        }

        public int getTraceThreshold() {
            return this.mTraceThreshold;
        }

        public java.lang.String toString() {
            return "ActionProperties{ mAction=" + this.mAction + ", mEnabled=" + this.mEnabled + ", mSamplingInterval=" + this.mSamplingInterval + ", mTraceThreshold=" + this.mTraceThreshold + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof com.android.internal.util.LatencyTracker.ActionProperties)) {
                return false;
            }
            com.android.internal.util.LatencyTracker.ActionProperties actionProperties = (com.android.internal.util.LatencyTracker.ActionProperties) obj;
            if (this.mAction == actionProperties.mAction && this.mEnabled == actionProperties.mEnabled && this.mSamplingInterval == actionProperties.mSamplingInterval && this.mTraceThreshold == actionProperties.mTraceThreshold) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((((this.mAction + 31) * 31) + java.lang.Boolean.hashCode(this.mEnabled)) * 31) + this.mSamplingInterval) * 31) + this.mTraceThreshold;
        }
    }

    public void onDeviceConfigPropertiesUpdated(android.util.SparseArray<com.android.internal.util.LatencyTracker.ActionProperties> sparseArray) {
    }

    public void onTriggerPerfetto(java.lang.String str) {
        com.android.internal.util.PerfettoTrigger.trigger(str);
    }

    public void onLogToFrameworkStats(com.android.internal.util.LatencyTracker.FrameworkStatsLogEvent frameworkStatsLogEvent) {
        com.android.internal.util.FrameworkStatsLog.write(frameworkStatsLogEvent.logCode, frameworkStatsLogEvent.statsdAction, frameworkStatsLogEvent.durationMillis);
    }

    public static class FrameworkStatsLogEvent {
        public final int action;
        public final int durationMillis;
        public final int logCode;
        public final int statsdAction;

        private FrameworkStatsLogEvent(int i, int i2, int i3, int i4) {
            this.action = i;
            this.logCode = i2;
            this.statsdAction = i3;
            this.durationMillis = i4;
        }

        public java.lang.String toString() {
            return "FrameworkStatsLogEvent{ logCode=" + this.logCode + ", statsdAction=" + this.statsdAction + ", durationMillis=" + this.durationMillis + "}";
        }
    }
}
