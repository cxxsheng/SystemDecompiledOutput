package com.android.server.alarm;

/* loaded from: classes.dex */
public class AlarmManagerService extends com.android.server.SystemService {
    static final int ACTIVE_INDEX = 0;
    static final boolean DEBUG_ALARM_CLOCK = false;
    static final boolean DEBUG_BATCH = false;
    static final boolean DEBUG_BG_LIMIT = false;
    static final boolean DEBUG_LISTENER_CALLBACK = false;
    static final boolean DEBUG_STANDBY = false;
    static final boolean DEBUG_TARE = false;
    static final boolean DEBUG_WAKELOCK = false;
    private static final java.lang.String DST_OFFSET_PROPERTY = "persist.sys.time.dst_offset";
    private static final java.lang.String DST_TRANSITION_PROPERTY = "persist.sys.time.dst_transition";
    private static final int ELAPSED_REALTIME_WAKEUP_MASK = 4;
    static final int FREQUENT_INDEX = 2;
    static final long INDEFINITE_DELAY = 31536000000L;
    static final int IS_WAKEUP_MASK = 5;
    static final long MIN_FUZZABLE_INTERVAL = 10000;
    static final int NEVER_INDEX = 4;
    private static final android.content.Intent NEXT_ALARM_CLOCK_CHANGED_INTENT = new android.content.Intent("android.app.action.NEXT_ALARM_CLOCK_CHANGED").addFlags(android.hardware.audio.common.V2_0.AudioFormat.APTX_HD);
    static final int PRIORITY_NORMAL = 2;
    static final int PRIORITY_SYSTEM = 0;
    static final int PRIORITY_WAKEUP = 1;
    static final int RARE_INDEX = 3;
    static final boolean RECORD_ALARMS_IN_HISTORY = true;
    static final boolean RECORD_DEVICE_IDLE_ALARMS = false;
    private static final int REMOVAL_HISTORY_SIZE_PER_UID = 10;
    private static final int RTC_WAKEUP_MASK = 1;
    static final java.lang.String TAG = "AlarmManager";
    private static final long TEMPORARY_QUOTA_DURATION = 86400000;
    static final int TICK_HISTORY_DEPTH = 10;
    private static final java.lang.String TIMEOFFSET_PROPERTY = "persist.sys.time.offset";
    static final int TIME_CHANGED_MASK = 65536;
    static final java.lang.String TIME_TICK_TAG = "TIME_TICK";
    static final int WORKING_INDEX = 1;
    static final boolean localLOGV = false;
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    android.app.ActivityOptions mActivityOptsRestrictBal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArrayMap<com.android.server.tare.EconomyManagerInternal.ActionBill, java.lang.Boolean>> mAffordabilityCache;
    private final com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener mAffordabilityChangeListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.lang.Runnable mAlarmClockUpdater;
    final java.util.Comparator<com.android.server.alarm.Alarm> mAlarmDispatchComparator;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.alarm.AlarmStore mAlarmStore;
    android.util.SparseIntArray mAlarmsPerUid;
    com.android.server.alarm.AlarmManagerService.AppWakeupHistory mAllowWhileIdleCompatHistory;
    final java.util.ArrayList<com.android.server.alarm.AlarmManagerService.IdleDispatchEntry> mAllowWhileIdleDispatches;
    com.android.server.alarm.AlarmManagerService.AppWakeupHistory mAllowWhileIdleHistory;
    android.app.AppOpsManager mAppOps;

    @com.android.internal.annotations.VisibleForTesting
    boolean mAppStandbyParole;
    private com.android.server.AppStateTrackerImpl mAppStateTracker;
    com.android.server.alarm.AlarmManagerService.AppWakeupHistory mAppWakeupHistory;
    private final android.content.Intent mBackgroundIntent;
    private android.os.BatteryStatsInternal mBatteryStatsInternal;
    android.app.BroadcastOptions mBroadcastOptsRestrictBal;
    int mBroadcastRefCount;
    final android.util.SparseArray<android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats>> mBroadcastStats;
    com.android.server.alarm.AlarmManagerService.ClockReceiver mClockReceiver;
    com.android.server.alarm.AlarmManagerService.Constants mConstants;
    android.app.PendingIntent mDateChangeSender;
    final com.android.server.alarm.AlarmManagerService.DeliveryTracker mDeliveryTracker;
    private final com.android.server.tare.EconomyManagerInternal mEconomyManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    volatile java.util.Set<java.lang.Integer> mExactAlarmCandidates;
    private final com.android.server.AppStateTrackerImpl.Listener mForceAppStandbyListener;
    com.android.server.alarm.AlarmManagerService.AlarmHandler mHandler;
    private final android.util.SparseArray<android.app.AlarmManager.AlarmClockInfo> mHandlerSparseAlarmClockArray;
    java.util.ArrayList<com.android.server.alarm.AlarmManagerService.InFlight> mInFlight;
    private final java.util.ArrayList<com.android.server.AlarmManagerInternal.InFlightListener> mInFlightListeners;
    private final com.android.server.alarm.AlarmManagerService.Injector mInjector;
    boolean mInteractive;
    long mLastAlarmDeliveryTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseIntArray mLastOpScheduleExactAlarm;
    private final android.util.SparseLongArray mLastPriorityAlarmDispatch;
    private long mLastTickReceived;
    private long mLastTickSet;
    long mLastTimeChangeClockTime;
    long mLastTimeChangeRealtime;
    private long mLastTrigger;
    private long mLastWakeup;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mListenerCount;
    android.os.IBinder.DeathRecipient mListenerDeathRecipient;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mListenerFinishCount;
    com.android.server.DeviceIdleInternal mLocalDeviceIdleController;
    private volatile com.android.server.pm.permission.PermissionManagerServiceInternal mLocalPermissionManager;
    final java.lang.Object mLock;
    final com.android.internal.util.LocalLog mLog;
    long mMaxDelayTime;
    com.android.server.alarm.MetricsHelper mMetricsHelper;
    private final android.util.SparseArray<android.app.AlarmManager.AlarmClockInfo> mNextAlarmClockForUser;
    private boolean mNextAlarmClockMayChange;
    private long mNextNonWakeUpSetAt;
    private long mNextNonWakeup;
    long mNextNonWakeupDeliveryTime;
    private int mNextTickHistory;
    com.android.server.alarm.Alarm mNextWakeFromIdle;
    private long mNextWakeUpSetAt;
    private long mNextWakeup;
    long mNonInteractiveStartTime;
    long mNonInteractiveTime;
    int mNumDelayedAlarms;
    int mNumTimeChanged;
    android.app.BroadcastOptions mOptsTimeBroadcast;
    android.app.BroadcastOptions mOptsWithFgs;
    android.app.BroadcastOptions mOptsWithFgsForAlarmClock;
    android.app.BroadcastOptions mOptsWithoutFgs;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    android.util.SparseArray<java.util.ArrayList<com.android.server.alarm.Alarm>> mPendingBackgroundAlarms;
    com.android.server.alarm.Alarm mPendingIdleUntil;
    java.util.ArrayList<com.android.server.alarm.Alarm> mPendingNonWakeupAlarms;
    private final android.util.SparseBooleanArray mPendingSendNextAlarmClockChangedForUser;
    private final android.util.SparseArray<com.android.internal.util.RingBuffer<com.android.server.alarm.AlarmManagerService.RemovedAlarm>> mRemovalHistory;
    private android.app.role.RoleManager mRoleManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSendCount;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSendFinishCount;
    private final android.os.IBinder mService;
    long mStartCurrentDelayTime;
    private final com.android.internal.util.jobs.StatLogger mStatLogger;
    int mSystemUiUid;
    com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve mTemporaryQuotaReserve;
    private final long[] mTickHistory;
    android.content.Intent mTimeTickIntent;
    android.os.Bundle mTimeTickOptions;
    android.app.IAlarmListener mTimeTickTrigger;
    private final android.util.SparseArray<android.app.AlarmManager.AlarmClockInfo> mTmpSparseAlarmClockArray;
    long mTotalDelayTime;
    private android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
    boolean mUseFrozenStateToDropListenerAlarms;
    android.os.PowerManager.WakeLock mWakeLock;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DispatchPriority {
    }

    static final class IdleDispatchEntry {
        long argRealtime;
        long elapsedRealtime;
        java.lang.String op;
        java.lang.String pkg;
        java.lang.String tag;
        int uid;

        IdleDispatchEntry() {
        }
    }

    interface Stats {
        public static final int HAS_SCHEDULE_EXACT_ALARM = 1;
        public static final int REORDER_ALARMS_FOR_STANDBY = 0;
        public static final int REORDER_ALARMS_FOR_TARE = 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void close(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNextAlarm(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long init();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int set(long j, int i, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int waitForAlarm(long j);

    static boolean isTimeTickAlarm(com.android.server.alarm.Alarm alarm) {
        return alarm.uid == 1000 && TIME_TICK_TAG.equals(alarm.listenerTag);
    }

    private static android.app.BroadcastOptions makeBasicAlarmBroadcastOptions() {
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setAlarmBroadcast(true);
        return makeBasic;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mNextAlarmClockMayChange = true;
    }

    static class TemporaryQuotaReserve {
        private long mMaxDuration;
        private final android.util.ArrayMap<android.content.pm.UserPackage, com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo> mQuotaBuffer = new android.util.ArrayMap<>();

        private static class QuotaInfo {
            public long expirationTime;
            public long lastUsage;
            public int remainingQuota;

            private QuotaInfo() {
            }
        }

        TemporaryQuotaReserve(long j) {
            this.mMaxDuration = j;
        }

        void replenishQuota(java.lang.String str, int i, int i2, long j) {
            if (i2 <= 0) {
                return;
            }
            android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
            com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo quotaInfo = this.mQuotaBuffer.get(of);
            if (quotaInfo == null) {
                quotaInfo = new com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo();
                this.mQuotaBuffer.put(of, quotaInfo);
            }
            quotaInfo.remainingQuota = i2;
            quotaInfo.expirationTime = j + this.mMaxDuration;
        }

        boolean hasQuota(java.lang.String str, int i, long j) {
            com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo quotaInfo = this.mQuotaBuffer.get(android.content.pm.UserPackage.of(i, str));
            return quotaInfo != null && quotaInfo.remainingQuota > 0 && j <= quotaInfo.expirationTime;
        }

        void recordUsage(java.lang.String str, int i, long j) {
            com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo quotaInfo = this.mQuotaBuffer.get(android.content.pm.UserPackage.of(i, str));
            if (quotaInfo == null) {
                android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "Temporary quota being consumed at " + j + " but not found for package: " + str + ", user: " + i);
                return;
            }
            if (j > quotaInfo.lastUsage) {
                if (quotaInfo.remainingQuota <= 0) {
                    android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "Temporary quota being consumed at " + j + " but remaining only " + quotaInfo.remainingQuota + " for package: " + str + ", user: " + i);
                } else if (quotaInfo.expirationTime < j) {
                    android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "Temporary quota being consumed at " + j + " but expired at " + quotaInfo.expirationTime + " for package: " + str + ", user: " + i);
                } else {
                    quotaInfo.remainingQuota--;
                }
                quotaInfo.lastUsage = j;
            }
        }

        void cleanUpExpiredQuotas(long j) {
            for (int size = this.mQuotaBuffer.size() - 1; size >= 0; size--) {
                if (this.mQuotaBuffer.valueAt(size).expirationTime < j) {
                    this.mQuotaBuffer.removeAt(size);
                }
            }
        }

        void removeForUser(int i) {
            for (int size = this.mQuotaBuffer.size() - 1; size >= 0; size--) {
                if (this.mQuotaBuffer.keyAt(size).userId == i) {
                    this.mQuotaBuffer.removeAt(size);
                }
            }
        }

        void removeForPackage(java.lang.String str, int i) {
            this.mQuotaBuffer.remove(android.content.pm.UserPackage.of(i, str));
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mQuotaBuffer.size(); i++) {
                android.content.pm.UserPackage keyAt = this.mQuotaBuffer.keyAt(i);
                com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve.QuotaInfo valueAt = this.mQuotaBuffer.valueAt(i);
                indentingPrintWriter.print(keyAt.packageName);
                indentingPrintWriter.print(", u");
                indentingPrintWriter.print(keyAt.userId);
                indentingPrintWriter.print(": ");
                if (valueAt == null) {
                    indentingPrintWriter.print("--");
                } else {
                    indentingPrintWriter.print("quota: ");
                    indentingPrintWriter.print(valueAt.remainingQuota);
                    indentingPrintWriter.print(", expiration: ");
                    android.util.TimeUtils.formatDuration(valueAt.expirationTime, j, indentingPrintWriter);
                    indentingPrintWriter.print(" last used: ");
                    android.util.TimeUtils.formatDuration(valueAt.lastUsage, j, indentingPrintWriter);
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class AppWakeupHistory {
        private final android.util.ArrayMap<android.content.pm.UserPackage, android.util.LongArrayQueue> mPackageHistory = new android.util.ArrayMap<>();
        private long mWindowSize;

        AppWakeupHistory(long j) {
            this.mWindowSize = j;
        }

        void recordAlarmForPackage(java.lang.String str, int i, long j) {
            android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
            android.util.LongArrayQueue longArrayQueue = this.mPackageHistory.get(of);
            if (longArrayQueue == null) {
                longArrayQueue = new android.util.LongArrayQueue();
                this.mPackageHistory.put(of, longArrayQueue);
            }
            if (longArrayQueue.size() == 0 || longArrayQueue.peekLast() < j) {
                longArrayQueue.addLast(j);
            }
            snapToWindow(longArrayQueue);
        }

        void removeForUser(int i) {
            for (int size = this.mPackageHistory.size() - 1; size >= 0; size--) {
                if (this.mPackageHistory.keyAt(size).userId == i) {
                    this.mPackageHistory.removeAt(size);
                }
            }
        }

        void removeForPackage(java.lang.String str, int i) {
            this.mPackageHistory.remove(android.content.pm.UserPackage.of(i, str));
        }

        private void snapToWindow(android.util.LongArrayQueue longArrayQueue) {
            while (longArrayQueue.peekFirst() + this.mWindowSize < longArrayQueue.peekLast()) {
                longArrayQueue.removeFirst();
            }
        }

        int getTotalWakeupsInWindow(java.lang.String str, int i) {
            android.util.LongArrayQueue longArrayQueue = this.mPackageHistory.get(android.content.pm.UserPackage.of(i, str));
            if (longArrayQueue == null) {
                return 0;
            }
            return longArrayQueue.size();
        }

        long getNthLastWakeupForPackage(java.lang.String str, int i, int i2) {
            int size;
            android.util.LongArrayQueue longArrayQueue = this.mPackageHistory.get(android.content.pm.UserPackage.of(i, str));
            if (longArrayQueue != null && (size = longArrayQueue.size() - i2) >= 0) {
                return longArrayQueue.get(size);
            }
            return 0L;
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mPackageHistory.size(); i++) {
                android.content.pm.UserPackage keyAt = this.mPackageHistory.keyAt(i);
                android.util.LongArrayQueue valueAt = this.mPackageHistory.valueAt(i);
                indentingPrintWriter.print(keyAt.packageName);
                indentingPrintWriter.print(", u");
                indentingPrintWriter.print(keyAt.userId);
                indentingPrintWriter.print(": ");
                int max = java.lang.Math.max(0, valueAt.size() - 100);
                for (int size = valueAt.size() - 1; size >= max; size--) {
                    android.util.TimeUtils.formatDuration(valueAt.get(size), j, indentingPrintWriter);
                    indentingPrintWriter.print(", ");
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    static class RemovedAlarm {
        static final int REMOVE_REASON_ALARM_CANCELLED = 1;
        static final int REMOVE_REASON_DATA_CLEARED = 3;
        static final int REMOVE_REASON_EXACT_PERMISSION_REVOKED = 2;
        static final int REMOVE_REASON_LISTENER_BINDER_DIED = 5;
        static final int REMOVE_REASON_LISTENER_CACHED = 6;
        static final int REMOVE_REASON_PI_CANCELLED = 4;
        static final int REMOVE_REASON_UNDEFINED = 0;
        final com.android.server.alarm.Alarm.Snapshot mAlarmSnapshot;
        final int mRemoveReason;
        final long mWhenRemovedElapsed;
        final long mWhenRemovedRtc;

        RemovedAlarm(com.android.server.alarm.Alarm alarm, int i, long j, long j2) {
            this.mAlarmSnapshot = new com.android.server.alarm.Alarm.Snapshot(alarm);
            this.mRemoveReason = i;
            this.mWhenRemovedRtc = j;
            this.mWhenRemovedElapsed = j2;
        }

        static final boolean isLoggable(int i) {
            return i != 0;
        }

        static final java.lang.String removeReasonToString(int i) {
            switch (i) {
                case 1:
                    return "alarm_cancelled";
                case 2:
                    return "exact_alarm_permission_revoked";
                case 3:
                    return "data_cleared";
                case 4:
                    return "pi_cancelled";
                case 5:
                    return "listener_binder_died";
                case 6:
                    return "listener_cached";
                default:
                    return "unknown:" + i;
            }
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j, java.text.SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Reason", removeReasonToString(this.mRemoveReason));
            indentingPrintWriter.print("elapsed=");
            android.util.TimeUtils.formatDuration(this.mWhenRemovedElapsed, j, indentingPrintWriter);
            indentingPrintWriter.print(" rtc=");
            indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mWhenRemovedRtc)));
            indentingPrintWriter.println();
            indentingPrintWriter.println("Snapshot:");
            indentingPrintWriter.increaseIndent();
            this.mAlarmSnapshot.dump(indentingPrintWriter, j);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class Constants implements android.provider.DeviceConfig.OnPropertiesChangedListener, com.android.server.tare.EconomyManagerInternal.TareStateChangeListener {
        private static final long DEFAULT_ALLOW_WHILE_IDLE_ALLOWLIST_DURATION = 10000;
        private static final int DEFAULT_ALLOW_WHILE_IDLE_COMPAT_QUOTA = 7;
        private static final long DEFAULT_ALLOW_WHILE_IDLE_COMPAT_WINDOW = 3600000;
        private static final int DEFAULT_ALLOW_WHILE_IDLE_QUOTA = 72;
        private static final long DEFAULT_ALLOW_WHILE_IDLE_WINDOW = 3600000;
        private static final int DEFAULT_APP_STANDBY_RESTRICTED_QUOTA = 1;
        private static final long DEFAULT_APP_STANDBY_RESTRICTED_WINDOW = 86400000;
        private static final long DEFAULT_APP_STANDBY_WINDOW = 3600000;
        private static final long DEFAULT_CACHED_LISTENER_REMOVAL_DELAY = 10000;
        private static final boolean DEFAULT_DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = true;
        private static final long DEFAULT_LISTENER_TIMEOUT = 5000;
        private static final int DEFAULT_MAX_ALARMS_PER_UID = 500;
        private static final long DEFAULT_MAX_DEVICE_IDLE_FUZZ = 900000;
        private static final long DEFAULT_MAX_INTERVAL = 31536000000L;
        private static final long DEFAULT_MIN_DEVICE_IDLE_FUZZ = 120000;
        private static final long DEFAULT_MIN_FUTURITY = 5000;
        private static final long DEFAULT_MIN_INTERVAL = 60000;
        private static final long DEFAULT_MIN_WINDOW = 600000;
        private static final long DEFAULT_PRIORITY_ALARM_DELAY = 540000;
        private static final int DEFAULT_TEMPORARY_QUOTA_BUMP = 0;
        private static final boolean DEFAULT_TIME_TICK_ALLOWED_WHILE_IDLE = true;

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA = "allow_while_idle_compat_quota";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW = "allow_while_idle_compat_window";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOW_WHILE_IDLE_QUOTA = "allow_while_idle_quota";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION = "allow_while_idle_whitelist_duration";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_ALLOW_WHILE_IDLE_WINDOW = "allow_while_idle_window";
        private static final java.lang.String KEY_APP_STANDBY_RESTRICTED_QUOTA = "standby_quota_restricted";
        private static final java.lang.String KEY_APP_STANDBY_RESTRICTED_WINDOW = "app_standby_restricted_window";
        private static final java.lang.String KEY_APP_STANDBY_WINDOW = "app_standby_window";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_CACHED_LISTENER_REMOVAL_DELAY = "cached_listener_removal_delay";
        private static final java.lang.String KEY_DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = "delay_nonwakeup_alarms_while_screen_off";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_LISTENER_TIMEOUT = "listener_timeout";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_ALARMS_PER_UID = "max_alarms_per_uid";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_DEVICE_IDLE_FUZZ = "max_device_idle_fuzz";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MAX_INTERVAL = "max_interval";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MIN_DEVICE_IDLE_FUZZ = "min_device_idle_fuzz";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MIN_FUTURITY = "min_futurity";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MIN_INTERVAL = "min_interval";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_MIN_WINDOW = "min_window";
        private static final java.lang.String KEY_PREFIX_STANDBY_QUOTA = "standby_quota_";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_PRIORITY_ALARM_DELAY = "priority_alarm_delay";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_TEMPORARY_QUOTA_BUMP = "temporary_quota_bump";
        private static final java.lang.String KEY_TIME_TICK_ALLOWED_WHILE_IDLE = "time_tick_allowed_while_idle";

        @com.android.internal.annotations.VisibleForTesting
        final java.lang.String[] KEYS_APP_STANDBY_QUOTAS = {"standby_quota_active", "standby_quota_working", "standby_quota_frequent", "standby_quota_rare", "standby_quota_never"};
        private final int[] DEFAULT_APP_STANDBY_QUOTAS = {720, 10, 2, 1, 0};
        public long MIN_FUTURITY = 5000;
        public long MIN_INTERVAL = 60000;
        public long MAX_INTERVAL = 31536000000L;
        public long MIN_WINDOW = 600000;
        public long ALLOW_WHILE_IDLE_WHITELIST_DURATION = 10000;
        public long LISTENER_TIMEOUT = 5000;
        public int MAX_ALARMS_PER_UID = 500;
        public long APP_STANDBY_WINDOW = 3600000;
        public int[] APP_STANDBY_QUOTAS = new int[this.DEFAULT_APP_STANDBY_QUOTAS.length];
        public int APP_STANDBY_RESTRICTED_QUOTA = 1;
        public long APP_STANDBY_RESTRICTED_WINDOW = 86400000;
        public boolean TIME_TICK_ALLOWED_WHILE_IDLE = true;
        public int ALLOW_WHILE_IDLE_QUOTA = 72;
        public int ALLOW_WHILE_IDLE_COMPAT_QUOTA = 7;
        public long ALLOW_WHILE_IDLE_COMPAT_WINDOW = 3600000;
        public long ALLOW_WHILE_IDLE_WINDOW = 3600000;
        public long PRIORITY_ALARM_DELAY = DEFAULT_PRIORITY_ALARM_DELAY;
        public long MIN_DEVICE_IDLE_FUZZ = 120000;
        public long MAX_DEVICE_IDLE_FUZZ = DEFAULT_MAX_DEVICE_IDLE_FUZZ;
        public int USE_TARE_POLICY = 0;
        public int TEMPORARY_QUOTA_BUMP = 0;
        public boolean DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = true;
        public long CACHED_LISTENER_REMOVAL_DELAY = 10000;
        private long mLastAllowWhileIdleWhitelistDuration = -1;
        private int mVersion = 0;

        Constants(android.os.Handler handler) {
            updateAllowWhileIdleWhitelistDurationLocked();
            for (int i = 0; i < this.APP_STANDBY_QUOTAS.length; i++) {
                this.APP_STANDBY_QUOTAS[i] = this.DEFAULT_APP_STANDBY_QUOTAS[i];
            }
        }

        public int getVersion() {
            int i;
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                i = this.mVersion;
            }
            return i;
        }

        public void start() {
            com.android.server.alarm.AlarmManagerService.this.mInjector.registerDeviceConfigListener(this);
            com.android.server.tare.EconomyManagerInternal economyManagerInternal = (com.android.server.tare.EconomyManagerInternal) com.android.server.LocalServices.getService(com.android.server.tare.EconomyManagerInternal.class);
            economyManagerInternal.registerTareStateChangeListener(this, 268435456);
            onPropertiesChanged(android.provider.DeviceConfig.getProperties("alarm_manager", new java.lang.String[0]));
            updateTareSettings(economyManagerInternal.getEnabledMode(268435456));
        }

        @android.annotation.SuppressLint({"MissingPermission"})
        public void updateAllowWhileIdleWhitelistDurationLocked() {
            if (this.mLastAllowWhileIdleWhitelistDuration != this.ALLOW_WHILE_IDLE_WHITELIST_DURATION) {
                this.mLastAllowWhileIdleWhitelistDuration = this.ALLOW_WHILE_IDLE_WHITELIST_DURATION;
                com.android.server.alarm.AlarmManagerService.this.mOptsWithFgs.setTemporaryAppAllowlist(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, 0, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE, "");
                com.android.server.alarm.AlarmManagerService.this.mOptsWithFgsForAlarmClock.setTemporaryAppAllowlist(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, 0, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_ALARM_CLOCK, "");
                com.android.server.alarm.AlarmManagerService.this.mOptsWithoutFgs.setTemporaryAppAllowlist(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, 1, -1, "");
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            char c;
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    this.mVersion++;
                    boolean z = false;
                    boolean z2 = false;
                    for (java.lang.String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -1577286106:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW)) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1490947714:
                                    if (str.equals(KEY_MIN_DEVICE_IDLE_FUZZ)) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1293038119:
                                    if (str.equals(KEY_MIN_FUTURITY)) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1251256606:
                                    if (str.equals(KEY_CACHED_LISTENER_REMOVAL_DELAY)) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -975718548:
                                    if (str.equals(KEY_MAX_ALARMS_PER_UID)) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -880907612:
                                    if (str.equals(KEY_APP_STANDBY_RESTRICTED_WINDOW)) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -618440710:
                                    if (str.equals(KEY_PRIORITY_ALARM_DELAY)) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -591494837:
                                    if (str.equals(KEY_TEMPORARY_QUOTA_BUMP)) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -577593775:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_QUOTA)) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -564889801:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_WINDOW)) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -410928980:
                                    if (str.equals(KEY_MAX_DEVICE_IDLE_FUZZ)) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -392965507:
                                    if (str.equals(KEY_MIN_WINDOW)) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -147388512:
                                    if (str.equals(KEY_APP_STANDBY_WINDOW)) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 932562134:
                                    if (str.equals(KEY_LISTENER_TIMEOUT)) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1139967827:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION)) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1213697417:
                                    if (str.equals(KEY_TIME_TICK_ALLOWED_WHILE_IDLE)) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1528643904:
                                    if (str.equals(KEY_MAX_INTERVAL)) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1883600258:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA)) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2003069970:
                                    if (str.equals(KEY_MIN_INTERVAL)) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2099862680:
                                    if (str.equals(KEY_DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF)) {
                                        c = 18;
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
                                    this.MIN_FUTURITY = properties.getLong(KEY_MIN_FUTURITY, 5000L);
                                    break;
                                case 1:
                                    this.MIN_INTERVAL = properties.getLong(KEY_MIN_INTERVAL, 60000L);
                                    break;
                                case 2:
                                    this.MAX_INTERVAL = properties.getLong(KEY_MAX_INTERVAL, 31536000000L);
                                    break;
                                case 3:
                                    this.ALLOW_WHILE_IDLE_QUOTA = properties.getInt(KEY_ALLOW_WHILE_IDLE_QUOTA, 72);
                                    if (this.ALLOW_WHILE_IDLE_QUOTA <= 0) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Must have positive allow_while_idle quota");
                                        this.ALLOW_WHILE_IDLE_QUOTA = 1;
                                        break;
                                    }
                                    break;
                                case 4:
                                    this.MIN_WINDOW = properties.getLong(KEY_MIN_WINDOW, 600000L);
                                    break;
                                case 5:
                                    this.ALLOW_WHILE_IDLE_COMPAT_QUOTA = properties.getInt(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA, 7);
                                    if (this.ALLOW_WHILE_IDLE_COMPAT_QUOTA <= 0) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Must have positive allow_while_idle_compat quota");
                                        this.ALLOW_WHILE_IDLE_COMPAT_QUOTA = 1;
                                        break;
                                    }
                                    break;
                                case 6:
                                    this.ALLOW_WHILE_IDLE_WINDOW = properties.getLong(KEY_ALLOW_WHILE_IDLE_WINDOW, 3600000L);
                                    if (this.ALLOW_WHILE_IDLE_WINDOW > 3600000) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Cannot have allow_while_idle_window > 3600000");
                                        this.ALLOW_WHILE_IDLE_WINDOW = 3600000L;
                                        break;
                                    } else if (this.ALLOW_WHILE_IDLE_WINDOW != 3600000) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Using a non-default allow_while_idle_window = " + this.ALLOW_WHILE_IDLE_WINDOW);
                                        break;
                                    }
                                    break;
                                case 7:
                                    this.ALLOW_WHILE_IDLE_COMPAT_WINDOW = properties.getLong(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW, 3600000L);
                                    if (this.ALLOW_WHILE_IDLE_COMPAT_WINDOW > 3600000) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Cannot have allow_while_idle_compat_window > 3600000");
                                        this.ALLOW_WHILE_IDLE_COMPAT_WINDOW = 3600000L;
                                        break;
                                    } else if (this.ALLOW_WHILE_IDLE_COMPAT_WINDOW != 3600000) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Using a non-default allow_while_idle_compat_window = " + this.ALLOW_WHILE_IDLE_COMPAT_WINDOW);
                                        break;
                                    }
                                    break;
                                case '\b':
                                    this.ALLOW_WHILE_IDLE_WHITELIST_DURATION = properties.getLong(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION, 10000L);
                                    updateAllowWhileIdleWhitelistDurationLocked();
                                    break;
                                case '\t':
                                    this.LISTENER_TIMEOUT = properties.getLong(KEY_LISTENER_TIMEOUT, 5000L);
                                    break;
                                case '\n':
                                    this.MAX_ALARMS_PER_UID = properties.getInt(KEY_MAX_ALARMS_PER_UID, 500);
                                    if (this.MAX_ALARMS_PER_UID < 500) {
                                        android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Cannot set max_alarms_per_uid lower than 500");
                                        this.MAX_ALARMS_PER_UID = 500;
                                        break;
                                    }
                                    break;
                                case 11:
                                case '\f':
                                    updateStandbyWindowsLocked();
                                    break;
                                case '\r':
                                    this.TIME_TICK_ALLOWED_WHILE_IDLE = properties.getBoolean(KEY_TIME_TICK_ALLOWED_WHILE_IDLE, true);
                                    break;
                                case 14:
                                    this.PRIORITY_ALARM_DELAY = properties.getLong(KEY_PRIORITY_ALARM_DELAY, DEFAULT_PRIORITY_ALARM_DELAY);
                                    break;
                                case 15:
                                case 16:
                                    if (!z) {
                                        updateDeviceIdleFuzzBoundaries();
                                        z = true;
                                        break;
                                    }
                                    break;
                                case 17:
                                    this.TEMPORARY_QUOTA_BUMP = properties.getInt(KEY_TEMPORARY_QUOTA_BUMP, 0);
                                    break;
                                case 18:
                                    this.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = properties.getBoolean(KEY_DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF, true);
                                    break;
                                case 19:
                                    this.CACHED_LISTENER_REMOVAL_DELAY = properties.getLong(KEY_CACHED_LISTENER_REMOVAL_DELAY, 10000L);
                                    break;
                                default:
                                    if (str.startsWith(KEY_PREFIX_STANDBY_QUOTA) && !z2) {
                                        updateStandbyQuotasLocked();
                                        z2 = true;
                                        break;
                                    }
                                    break;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal.TareStateChangeListener
        public void onTareEnabledModeChanged(int i) {
            updateTareSettings(i);
        }

        private void updateTareSettings(int i) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    if (this.USE_TARE_POLICY != i) {
                        this.USE_TARE_POLICY = i;
                        boolean updateAlarmDeliveries = com.android.server.alarm.AlarmManagerService.this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$Constants$$ExternalSyntheticLambda0
                            @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                            public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                                boolean lambda$updateTareSettings$0;
                                lambda$updateTareSettings$0 = com.android.server.alarm.AlarmManagerService.Constants.this.lambda$updateTareSettings$0(alarm);
                                return lambda$updateTareSettings$0;
                            }
                        });
                        if (this.USE_TARE_POLICY != 1) {
                            com.android.server.alarm.AlarmManagerService.this.mAffordabilityCache.clear();
                        }
                        if (updateAlarmDeliveries) {
                            com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                            com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$updateTareSettings$0(com.android.server.alarm.Alarm alarm) {
            boolean adjustDeliveryTimeBasedOnBucketLocked = com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnBucketLocked(alarm);
            boolean adjustDeliveryTimeBasedOnTareLocked = com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnTareLocked(alarm);
            if (this.USE_TARE_POLICY == 1) {
                com.android.server.alarm.AlarmManagerService.this.registerTareListener(alarm);
            } else {
                com.android.server.alarm.AlarmManagerService.this.mEconomyManagerInternal.unregisterAffordabilityChangeListener(android.os.UserHandle.getUserId(alarm.uid), alarm.sourcePackage, com.android.server.alarm.AlarmManagerService.this.mAffordabilityChangeListener, com.android.server.alarm.TareBill.getAppropriateBill(alarm));
            }
            return adjustDeliveryTimeBasedOnBucketLocked || adjustDeliveryTimeBasedOnTareLocked;
        }

        private void updateDeviceIdleFuzzBoundaries() {
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("alarm_manager", new java.lang.String[]{KEY_MIN_DEVICE_IDLE_FUZZ, KEY_MAX_DEVICE_IDLE_FUZZ});
            this.MIN_DEVICE_IDLE_FUZZ = properties.getLong(KEY_MIN_DEVICE_IDLE_FUZZ, 120000L);
            this.MAX_DEVICE_IDLE_FUZZ = properties.getLong(KEY_MAX_DEVICE_IDLE_FUZZ, DEFAULT_MAX_DEVICE_IDLE_FUZZ);
            if (this.MAX_DEVICE_IDLE_FUZZ < this.MIN_DEVICE_IDLE_FUZZ) {
                android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "max_device_idle_fuzz cannot be smaller than min_device_idle_fuzz! Increasing to " + this.MIN_DEVICE_IDLE_FUZZ);
                this.MAX_DEVICE_IDLE_FUZZ = this.MIN_DEVICE_IDLE_FUZZ;
            }
        }

        private void updateStandbyQuotasLocked() {
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("alarm_manager", this.KEYS_APP_STANDBY_QUOTAS);
            this.APP_STANDBY_QUOTAS[0] = properties.getInt(this.KEYS_APP_STANDBY_QUOTAS[0], this.DEFAULT_APP_STANDBY_QUOTAS[0]);
            for (int i = 1; i < this.KEYS_APP_STANDBY_QUOTAS.length; i++) {
                this.APP_STANDBY_QUOTAS[i] = properties.getInt(this.KEYS_APP_STANDBY_QUOTAS[i], java.lang.Math.min(this.APP_STANDBY_QUOTAS[i - 1], this.DEFAULT_APP_STANDBY_QUOTAS[i]));
            }
            this.APP_STANDBY_RESTRICTED_QUOTA = java.lang.Math.max(1, android.provider.DeviceConfig.getInt("alarm_manager", KEY_APP_STANDBY_RESTRICTED_QUOTA, 1));
        }

        private void updateStandbyWindowsLocked() {
            android.provider.DeviceConfig.Properties properties = android.provider.DeviceConfig.getProperties("alarm_manager", new java.lang.String[]{KEY_APP_STANDBY_WINDOW, KEY_APP_STANDBY_RESTRICTED_WINDOW});
            this.APP_STANDBY_WINDOW = properties.getLong(KEY_APP_STANDBY_WINDOW, 3600000L);
            if (this.APP_STANDBY_WINDOW > 3600000) {
                android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Cannot exceed the app_standby_window size of 3600000");
                this.APP_STANDBY_WINDOW = 3600000L;
            } else if (this.APP_STANDBY_WINDOW < 3600000) {
                android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "Using a non-default app_standby_window of " + this.APP_STANDBY_WINDOW);
            }
            this.APP_STANDBY_RESTRICTED_WINDOW = java.lang.Math.max(this.APP_STANDBY_WINDOW, properties.getLong(KEY_APP_STANDBY_RESTRICTED_WINDOW, 86400000L));
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Settings:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("version", java.lang.Integer.valueOf(this.mVersion));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_FUTURITY);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_FUTURITY, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_INTERVAL);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_INTERVAL, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MAX_INTERVAL);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MAX_INTERVAL, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_WINDOW);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_LISTENER_TIMEOUT);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.LISTENER_TIMEOUT, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_QUOTA, java.lang.Integer.valueOf(this.ALLOW_WHILE_IDLE_QUOTA));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_WINDOW);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA, java.lang.Integer.valueOf(this.ALLOW_WHILE_IDLE_COMPAT_QUOTA));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_COMPAT_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MAX_ALARMS_PER_UID, java.lang.Integer.valueOf(this.MAX_ALARMS_PER_UID));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_APP_STANDBY_WINDOW);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.APP_STANDBY_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            for (int i = 0; i < this.KEYS_APP_STANDBY_QUOTAS.length; i++) {
                indentingPrintWriter.print(this.KEYS_APP_STANDBY_QUOTAS[i], java.lang.Integer.valueOf(this.APP_STANDBY_QUOTAS[i]));
                indentingPrintWriter.println();
            }
            indentingPrintWriter.print(KEY_APP_STANDBY_RESTRICTED_QUOTA, java.lang.Integer.valueOf(this.APP_STANDBY_RESTRICTED_QUOTA));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_APP_STANDBY_RESTRICTED_WINDOW);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.APP_STANDBY_RESTRICTED_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_TIME_TICK_ALLOWED_WHILE_IDLE, java.lang.Boolean.valueOf(this.TIME_TICK_ALLOWED_WHILE_IDLE));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_PRIORITY_ALARM_DELAY);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.PRIORITY_ALARM_DELAY, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_DEVICE_IDLE_FUZZ);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MIN_DEVICE_IDLE_FUZZ, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MAX_DEVICE_IDLE_FUZZ);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.MAX_DEVICE_IDLE_FUZZ, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print("enable_tare", android.app.tare.EconomyManager.enabledModeToString(this.USE_TARE_POLICY));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_TEMPORARY_QUOTA_BUMP, java.lang.Integer.valueOf(this.TEMPORARY_QUOTA_BUMP));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF, java.lang.Boolean.valueOf(this.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_CACHED_LISTENER_REMOVAL_DELAY);
            indentingPrintWriter.print("=");
            android.util.TimeUtils.formatDuration(this.CACHED_LISTENER_REMOVAL_DELAY, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }

        void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.MIN_FUTURITY);
            protoOutputStream.write(1112396529666L, this.MIN_INTERVAL);
            protoOutputStream.write(1112396529671L, this.MAX_INTERVAL);
            protoOutputStream.write(1112396529667L, this.LISTENER_TIMEOUT);
            protoOutputStream.write(1112396529670L, this.ALLOW_WHILE_IDLE_WHITELIST_DURATION);
            protoOutputStream.end(start);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$1(com.android.server.alarm.Alarm alarm, com.android.server.alarm.Alarm alarm2) {
        boolean z = (alarm.flags & 16) != 0;
        if (z != ((alarm2.flags & 16) != 0)) {
            return z ? -1 : 1;
        }
        if (alarm.priorityClass < alarm2.priorityClass) {
            return -1;
        }
        if (alarm.priorityClass > alarm2.priorityClass) {
            return 1;
        }
        boolean z2 = alarm.listener == this.mTimeTickTrigger;
        if (z2 != (alarm2.listener == this.mTimeTickTrigger)) {
            return z2 ? -1 : 1;
        }
        if (alarm.getRequestedElapsed() < alarm2.getRequestedElapsed()) {
            return -1;
        }
        return alarm.getRequestedElapsed() > alarm2.getRequestedElapsed() ? 1 : 0;
    }

    void calculateDeliveryPriorities(java.util.ArrayList<com.android.server.alarm.Alarm> arrayList) {
        int size = arrayList.size();
        android.util.ArraySet arraySet = new android.util.ArraySet(4);
        for (int i = 0; i < size; i++) {
            com.android.server.alarm.Alarm alarm = arrayList.get(i);
            if (alarm.wakeup) {
                arraySet.add(android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage));
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.alarm.Alarm alarm2 = arrayList.get(i2);
            if (alarm2.creatorUid == 1000 && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(alarm2.sourcePackage)) {
                alarm2.priorityClass = 0;
            } else if (arraySet.contains(android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm2.creatorUid), alarm2.sourcePackage))) {
                alarm2.priorityClass = 1;
            } else {
                alarm2.priorityClass = 2;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    AlarmManagerService(android.content.Context context, com.android.server.alarm.AlarmManagerService.Injector injector) {
        super(context);
        this.mBackgroundIntent = new android.content.Intent().addFlags(4);
        this.mLog = new com.android.internal.util.LocalLog(TAG);
        this.mLock = new java.lang.Object();
        this.mExactAlarmCandidates = java.util.Collections.emptySet();
        this.mLastOpScheduleExactAlarm = new android.util.SparseIntArray();
        this.mAffordabilityCache = new android.util.SparseArrayMap<>();
        this.mPendingBackgroundAlarms = new android.util.SparseArray<>();
        this.mTickHistory = new long[10];
        this.mBroadcastRefCount = 0;
        this.mAlarmsPerUid = new android.util.SparseIntArray();
        this.mPendingNonWakeupAlarms = new java.util.ArrayList<>();
        this.mInFlight = new java.util.ArrayList<>();
        this.mInFlightListeners = new java.util.ArrayList<>();
        this.mLastPriorityAlarmDispatch = new android.util.SparseLongArray();
        this.mRemovalHistory = new android.util.SparseArray<>();
        this.mDeliveryTracker = new com.android.server.alarm.AlarmManagerService.DeliveryTracker();
        this.mInteractive = true;
        this.mAllowWhileIdleDispatches = new java.util.ArrayList<>();
        this.mStatLogger = new com.android.internal.util.jobs.StatLogger("Alarm manager stats", new java.lang.String[]{"REORDER_ALARMS_FOR_STANDBY", "HAS_SCHEDULE_EXACT_ALARM", "REORDER_ALARMS_FOR_TARE"});
        this.mOptsWithFgs = makeBasicAlarmBroadcastOptions();
        this.mOptsWithFgsForAlarmClock = makeBasicAlarmBroadcastOptions();
        this.mOptsWithoutFgs = makeBasicAlarmBroadcastOptions();
        this.mOptsTimeBroadcast = makeBasicAlarmBroadcastOptions();
        this.mActivityOptsRestrictBal = android.app.ActivityOptions.makeBasic();
        this.mBroadcastOptsRestrictBal = makeBasicAlarmBroadcastOptions();
        this.mNextAlarmClockForUser = new android.util.SparseArray<>();
        this.mTmpSparseAlarmClockArray = new android.util.SparseArray<>();
        this.mPendingSendNextAlarmClockChangedForUser = new android.util.SparseBooleanArray();
        this.mAlarmClockUpdater = new java.lang.Runnable() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.alarm.AlarmManagerService.this.lambda$new$0();
            }
        };
        this.mHandlerSparseAlarmClockArray = new android.util.SparseArray<>();
        this.mAlarmDispatchComparator = new java.util.Comparator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda19
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$new$1;
                lambda$new$1 = com.android.server.alarm.AlarmManagerService.this.lambda$new$1((com.android.server.alarm.Alarm) obj, (com.android.server.alarm.Alarm) obj2);
                return lambda$new$1;
            }
        };
        this.mPendingIdleUntil = null;
        this.mNextWakeFromIdle = null;
        this.mBroadcastStats = new android.util.SparseArray<>();
        this.mNumDelayedAlarms = 0;
        this.mTotalDelayTime = 0L;
        this.mMaxDelayTime = 0L;
        this.mService = new com.android.server.alarm.AlarmManagerService.AnonymousClass4();
        this.mAffordabilityChangeListener = new com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener() { // from class: com.android.server.alarm.AlarmManagerService.7
            @Override // com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener
            public void onAffordabilityChanged(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, boolean z) {
                synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                    try {
                        android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.alarm.AlarmManagerService.this.mAffordabilityCache.get(i, str);
                        if (arrayMap == null) {
                            arrayMap = new android.util.ArrayMap();
                            com.android.server.alarm.AlarmManagerService.this.mAffordabilityCache.add(i, str, arrayMap);
                        }
                        arrayMap.put(actionBill, java.lang.Boolean.valueOf(z));
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(12, i, z ? 1 : 0, str).sendToTarget();
            }
        };
        this.mForceAppStandbyListener = new com.android.server.alarm.AlarmManagerService.AnonymousClass8();
        this.mSendCount = 0;
        this.mSendFinishCount = 0;
        this.mListenerCount = 0;
        this.mListenerFinishCount = 0;
        this.mInjector = injector;
        this.mEconomyManagerInternal = (com.android.server.tare.EconomyManagerInternal) com.android.server.LocalServices.getService(com.android.server.tare.EconomyManagerInternal.class);
    }

    public AlarmManagerService(android.content.Context context) {
        this(context, new com.android.server.alarm.AlarmManagerService.Injector(context));
    }

    static boolean isRtc(int i) {
        return i == 1 || i == 0;
    }

    private long convertToElapsed(long j, int i) {
        if (isRtc(i)) {
            return j - (this.mInjector.getCurrentTimeMillis() - this.mInjector.getElapsedRealtimeMillis());
        }
        return j;
    }

    long getMinimumAllowedWindow(long j, long j2) {
        return java.lang.Math.min((long) ((j2 - j) * 0.75d), this.mConstants.MIN_WINDOW);
    }

    static long maxTriggerTime(long j, long j2, long j3) {
        long j4 = 0;
        if (j3 == 0) {
            j3 = j2 - j;
        }
        if (j3 >= 10000) {
            j4 = j3;
        }
        long addClampPositive = addClampPositive(j2, (long) (j4 * 0.75d));
        if (j3 == 0) {
            return java.lang.Math.min(addClampPositive, addClampPositive(j2, 3600000L));
        }
        return addClampPositive;
    }

    void reevaluateRtcAlarms() {
        synchronized (this.mLock) {
            try {
                boolean updateAlarmDeliveries = this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda14
                    @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                    public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                        boolean lambda$reevaluateRtcAlarms$2;
                        lambda$reevaluateRtcAlarms$2 = com.android.server.alarm.AlarmManagerService.this.lambda$reevaluateRtcAlarms$2(alarm);
                        return lambda$reevaluateRtcAlarms$2;
                    }
                });
                if (updateAlarmDeliveries && this.mPendingIdleUntil != null && this.mNextWakeFromIdle != null && isRtc(this.mNextWakeFromIdle.type) && this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda15
                    @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                    public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                        boolean lambda$reevaluateRtcAlarms$3;
                        lambda$reevaluateRtcAlarms$3 = com.android.server.alarm.AlarmManagerService.this.lambda$reevaluateRtcAlarms$3(alarm);
                        return lambda$reevaluateRtcAlarms$3;
                    }
                })) {
                    this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda16
                        @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                        public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                            boolean lambda$reevaluateRtcAlarms$4;
                            lambda$reevaluateRtcAlarms$4 = com.android.server.alarm.AlarmManagerService.this.lambda$reevaluateRtcAlarms$4(alarm);
                            return lambda$reevaluateRtcAlarms$4;
                        }
                    });
                }
                if (updateAlarmDeliveries) {
                    rescheduleKernelAlarmsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reevaluateRtcAlarms$2(com.android.server.alarm.Alarm alarm) {
        if (!isRtc(alarm.type)) {
            return false;
        }
        return restoreRequestedTime(alarm);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reevaluateRtcAlarms$3(com.android.server.alarm.Alarm alarm) {
        return alarm == this.mPendingIdleUntil && adjustIdleUntilTime(alarm);
    }

    boolean reorderAlarmsBasedOnStandbyBuckets(final android.util.ArraySet<android.content.pm.UserPackage> arraySet) {
        long time = this.mStatLogger.getTime();
        boolean updateAlarmDeliveries = this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda25
            @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
            public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                boolean lambda$reorderAlarmsBasedOnStandbyBuckets$5;
                lambda$reorderAlarmsBasedOnStandbyBuckets$5 = com.android.server.alarm.AlarmManagerService.this.lambda$reorderAlarmsBasedOnStandbyBuckets$5(arraySet, alarm);
                return lambda$reorderAlarmsBasedOnStandbyBuckets$5;
            }
        });
        this.mStatLogger.logDurationStat(0, time);
        return updateAlarmDeliveries;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reorderAlarmsBasedOnStandbyBuckets$5(android.util.ArraySet arraySet, com.android.server.alarm.Alarm alarm) {
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage);
        if (arraySet != null && !arraySet.contains(of)) {
            return false;
        }
        return adjustDeliveryTimeBasedOnBucketLocked(alarm);
    }

    boolean reorderAlarmsBasedOnTare(final android.util.ArraySet<android.content.pm.UserPackage> arraySet) {
        long time = this.mStatLogger.getTime();
        boolean updateAlarmDeliveries = this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda26
            @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
            public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                boolean lambda$reorderAlarmsBasedOnTare$6;
                lambda$reorderAlarmsBasedOnTare$6 = com.android.server.alarm.AlarmManagerService.this.lambda$reorderAlarmsBasedOnTare$6(arraySet, alarm);
                return lambda$reorderAlarmsBasedOnTare$6;
            }
        });
        this.mStatLogger.logDurationStat(2, time);
        return updateAlarmDeliveries;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reorderAlarmsBasedOnTare$6(android.util.ArraySet arraySet, com.android.server.alarm.Alarm alarm) {
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage);
        if (arraySet != null && !arraySet.contains(of)) {
            return false;
        }
        return adjustDeliveryTimeBasedOnTareLocked(alarm);
    }

    private boolean restoreRequestedTime(com.android.server.alarm.Alarm alarm) {
        return alarm.setPolicyElapsed(0, convertToElapsed(alarm.origWhen, alarm.type));
    }

    static long clampPositive(long j) {
        return j >= 0 ? j : com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    static long addClampPositive(long j, long j2) {
        long j3 = j + j2;
        if (j3 >= 0) {
            return j3;
        }
        if (j < 0 || j2 < 0) {
            return 0L;
        }
        return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void sendPendingBackgroundAlarmsLocked(int i, java.lang.String str) {
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = this.mPendingBackgroundAlarms.get(i);
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        if (str != null) {
            java.util.ArrayList<com.android.server.alarm.Alarm> arrayList2 = new java.util.ArrayList<>();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size).matches(str)) {
                    arrayList2.add(arrayList.remove(size));
                }
            }
            if (arrayList.size() == 0) {
                this.mPendingBackgroundAlarms.remove(i);
            }
            arrayList = arrayList2;
        } else {
            this.mPendingBackgroundAlarms.remove(i);
        }
        deliverPendingBackgroundAlarmsLocked(arrayList, this.mInjector.getElapsedRealtimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void sendAllUnrestrictedPendingBackgroundAlarmsLocked() {
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>();
        findAllUnrestrictedPendingBackgroundAlarmsLockedInner(this.mPendingBackgroundAlarms, arrayList, new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isBackgroundRestricted;
                isBackgroundRestricted = com.android.server.alarm.AlarmManagerService.this.isBackgroundRestricted((com.android.server.alarm.Alarm) obj);
                return isBackgroundRestricted;
            }
        });
        if (arrayList.size() > 0) {
            deliverPendingBackgroundAlarmsLocked(arrayList, this.mInjector.getElapsedRealtimeMillis());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static void findAllUnrestrictedPendingBackgroundAlarmsLockedInner(android.util.SparseArray<java.util.ArrayList<com.android.server.alarm.Alarm>> sparseArray, java.util.ArrayList<com.android.server.alarm.Alarm> arrayList, java.util.function.Predicate<com.android.server.alarm.Alarm> predicate) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.alarm.Alarm> valueAt = sparseArray.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                com.android.server.alarm.Alarm alarm = valueAt.get(size2);
                if (!predicate.test(alarm)) {
                    arrayList.add(alarm);
                    valueAt.remove(size2);
                }
            }
            if (valueAt.size() == 0) {
                sparseArray.removeAt(size);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void deliverPendingBackgroundAlarmsLocked(java.util.ArrayList<com.android.server.alarm.Alarm> arrayList, long j) {
        com.android.server.alarm.AlarmManagerService alarmManagerService;
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList2;
        long j2;
        boolean z;
        int i;
        int i2;
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList3 = arrayList;
        long j3 = j;
        int size = arrayList.size();
        boolean z2 = false;
        int i3 = 0;
        while (i3 < size) {
            com.android.server.alarm.Alarm alarm = arrayList3.get(i3);
            if (!alarm.wakeup) {
                z = z2;
            } else {
                z = true;
            }
            alarm.count = 1;
            if (alarm.repeatInterval <= 0) {
                i = i3;
                i2 = size;
            } else {
                alarm.count = (int) (alarm.count + ((j3 - alarm.getRequestedElapsed()) / alarm.repeatInterval));
                long j4 = alarm.count * alarm.repeatInterval;
                long requestedElapsed = alarm.getRequestedElapsed() + j4;
                i = i3;
                i2 = size;
                setImplLocked(alarm.type, alarm.origWhen + j4, requestedElapsed, maxTriggerTime(j, requestedElapsed, alarm.repeatInterval) - requestedElapsed, alarm.repeatInterval, alarm.operation, null, null, alarm.flags, alarm.workSource, alarm.alarmClock, alarm.uid, alarm.packageName, null, -1);
            }
            i3 = i + 1;
            arrayList3 = arrayList;
            j3 = j;
            z2 = z;
            size = i2;
        }
        if (z2) {
            alarmManagerService = this;
            arrayList2 = arrayList;
            j2 = j;
        } else {
            alarmManagerService = this;
            j2 = j;
            if (!alarmManagerService.checkAllowNonWakeupDelayLocked(j2)) {
                arrayList2 = arrayList;
            } else {
                if (alarmManagerService.mPendingNonWakeupAlarms.size() == 0) {
                    alarmManagerService.mStartCurrentDelayTime = j2;
                    alarmManagerService.mNextNonWakeupDeliveryTime = j2 + ((alarmManagerService.currentNonWakeupFuzzLocked(j2) * 3) / 2);
                }
                alarmManagerService.mPendingNonWakeupAlarms.addAll(arrayList);
                alarmManagerService.mNumDelayedAlarms += arrayList.size();
                return;
            }
        }
        if (alarmManagerService.mPendingNonWakeupAlarms.size() > 0) {
            arrayList2.addAll(alarmManagerService.mPendingNonWakeupAlarms);
            long j5 = j2 - alarmManagerService.mStartCurrentDelayTime;
            alarmManagerService.mTotalDelayTime += j5;
            if (alarmManagerService.mMaxDelayTime < j5) {
                alarmManagerService.mMaxDelayTime = j5;
            }
            alarmManagerService.mPendingNonWakeupAlarms.clear();
        }
        calculateDeliveryPriorities(arrayList);
        java.util.Collections.sort(arrayList2, alarmManagerService.mAlarmDispatchComparator);
        deliverAlarmsLocked(arrayList, j);
    }

    static final class InFlight {
        final int mAlarmType;
        final com.android.server.alarm.AlarmManagerService.BroadcastStats mBroadcastStats;
        final int mCreatorUid;
        final com.android.server.alarm.AlarmManagerService.FilterStats mFilterStats;
        final android.os.IBinder mListener;
        final android.app.PendingIntent mPendingIntent;
        final int mPriorityClass;
        final java.lang.String mTag;
        final int mUid;
        final long mWhenElapsed;
        final android.os.WorkSource mWorkSource;

        InFlight(com.android.server.alarm.AlarmManagerService alarmManagerService, com.android.server.alarm.Alarm alarm, long j) {
            com.android.server.alarm.AlarmManagerService.BroadcastStats statsLocked;
            this.mPendingIntent = alarm.operation;
            this.mWhenElapsed = j;
            this.mListener = alarm.listener != null ? alarm.listener.asBinder() : null;
            this.mWorkSource = alarm.workSource;
            this.mUid = alarm.uid;
            this.mCreatorUid = alarm.creatorUid;
            this.mTag = alarm.statsTag;
            if (alarm.operation != null) {
                statsLocked = alarmManagerService.getStatsLocked(alarm.operation);
            } else {
                statsLocked = alarmManagerService.getStatsLocked(alarm.uid, alarm.packageName);
            }
            this.mBroadcastStats = statsLocked;
            com.android.server.alarm.AlarmManagerService.FilterStats filterStats = this.mBroadcastStats.filterStats.get(this.mTag);
            if (filterStats == null) {
                filterStats = new com.android.server.alarm.AlarmManagerService.FilterStats(this.mBroadcastStats, this.mTag);
                this.mBroadcastStats.filterStats.put(this.mTag, filterStats);
            }
            filterStats.lastTime = j;
            this.mFilterStats = filterStats;
            this.mAlarmType = alarm.type;
            this.mPriorityClass = alarm.priorityClass;
        }

        boolean isBroadcast() {
            return this.mPendingIntent != null && this.mPendingIntent.isBroadcast();
        }

        public java.lang.String toString() {
            return "InFlight{pendingIntent=" + this.mPendingIntent + ", when=" + this.mWhenElapsed + ", workSource=" + this.mWorkSource + ", uid=" + this.mUid + ", creatorUid=" + this.mCreatorUid + ", tag=" + this.mTag + ", broadcastStats=" + this.mBroadcastStats + ", filterStats=" + this.mFilterStats + ", alarmType=" + this.mAlarmType + ", priorityClass=" + this.mPriorityClass + "}";
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mTag);
            protoOutputStream.write(1112396529667L, this.mWhenElapsed);
            protoOutputStream.write(1159641169924L, this.mAlarmType);
            if (this.mPendingIntent != null) {
                this.mPendingIntent.dumpDebug(protoOutputStream, 1146756268037L);
            }
            if (this.mBroadcastStats != null) {
                this.mBroadcastStats.dumpDebug(protoOutputStream, 1146756268038L);
            }
            if (this.mFilterStats != null) {
                this.mFilterStats.dumpDebug(protoOutputStream, 1146756268039L);
            }
            if (this.mWorkSource != null) {
                this.mWorkSource.dumpDebug(protoOutputStream, 1146756268040L);
            }
            protoOutputStream.end(start);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBroadcastAlarmPendingLocked(int i) {
        int size = this.mInFlightListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mInFlightListeners.get(i2).broadcastAlarmPending(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBroadcastAlarmCompleteLocked(int i) {
        int size = this.mInFlightListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mInFlightListeners.get(i2).broadcastAlarmComplete(i);
        }
    }

    static final class FilterStats {
        long aggregateTime;
        int count;
        long lastTime;
        final com.android.server.alarm.AlarmManagerService.BroadcastStats mBroadcastStats;
        final java.lang.String mTag;
        int nesting;
        int numWakeup;
        long startTime;

        FilterStats(com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats, java.lang.String str) {
            this.mBroadcastStats = broadcastStats;
            this.mTag = str;
        }

        public java.lang.String toString() {
            return "FilterStats{tag=" + this.mTag + ", lastTime=" + this.lastTime + ", aggregateTime=" + this.aggregateTime + ", count=" + this.count + ", numWakeup=" + this.numWakeup + ", startTime=" + this.startTime + ", nesting=" + this.nesting + "}";
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mTag);
            protoOutputStream.write(1112396529666L, this.lastTime);
            protoOutputStream.write(1112396529667L, this.aggregateTime);
            protoOutputStream.write(1120986464260L, this.count);
            protoOutputStream.write(1120986464261L, this.numWakeup);
            protoOutputStream.write(1112396529670L, this.startTime);
            protoOutputStream.write(1120986464263L, this.nesting);
            protoOutputStream.end(start);
        }
    }

    static final class BroadcastStats {
        long aggregateTime;
        int count;
        final android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.FilterStats> filterStats = new android.util.ArrayMap<>();
        final java.lang.String mPackageName;
        final int mUid;
        int nesting;
        int numWakeup;
        long startTime;

        BroadcastStats(int i, java.lang.String str) {
            this.mUid = i;
            this.mPackageName = str;
        }

        public java.lang.String toString() {
            return "BroadcastStats{uid=" + this.mUid + ", packageName=" + this.mPackageName + ", aggregateTime=" + this.aggregateTime + ", count=" + this.count + ", numWakeup=" + this.numWakeup + ", startTime=" + this.startTime + ", nesting=" + this.nesting + "}";
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mPackageName);
            protoOutputStream.write(1112396529667L, this.aggregateTime);
            protoOutputStream.write(1120986464260L, this.count);
            protoOutputStream.write(1120986464261L, this.numWakeup);
            protoOutputStream.write(1112396529670L, this.startTime);
            protoOutputStream.write(1120986464263L, this.nesting);
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mInjector.init();
        this.mHandler = new com.android.server.alarm.AlarmManagerService.AlarmHandler();
        this.mOptsWithFgs.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsWithFgsForAlarmClock.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsWithoutFgs.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsTimeBroadcast.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mActivityOptsRestrictBal.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mBroadcastOptsRestrictBal.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mMetricsHelper = new com.android.server.alarm.MetricsHelper(getContext(), this.mLock);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        com.android.server.alarm.Flags.useFrozenStateToDropListenerAlarms();
        this.mUseFrozenStateToDropListenerAlarms = false;
        if (this.mUseFrozenStateToDropListenerAlarms) {
            ((android.app.ActivityManager) getContext().getSystemService(android.app.ActivityManager.class)).registerUidFrozenStateChangedCallback(new android.os.HandlerExecutor(this.mHandler), new android.app.ActivityManager.UidFrozenStateChangedCallback() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda2
                public final void onUidFrozenStateChanged(int[] iArr, int[] iArr2) {
                    com.android.server.alarm.AlarmManagerService.this.lambda$onStart$7(iArr, iArr2);
                }
            });
        }
        this.mListenerDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.alarm.AlarmManagerService.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied(android.os.IBinder iBinder) {
                android.app.IAlarmListener asInterface = android.app.IAlarmListener.Stub.asInterface(iBinder);
                synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                    com.android.server.alarm.AlarmManagerService.this.removeLocked(null, asInterface, 5);
                }
            }
        };
        synchronized (this.mLock) {
            try {
                this.mConstants = new com.android.server.alarm.AlarmManagerService.Constants(this.mHandler);
                this.mAlarmStore = new com.android.server.alarm.LazyAlarmStore();
                this.mAlarmStore.setAlarmClockRemovalListener(this.mAlarmClockUpdater);
                this.mAppWakeupHistory = new com.android.server.alarm.AlarmManagerService.AppWakeupHistory(3600000L);
                this.mAllowWhileIdleHistory = new com.android.server.alarm.AlarmManagerService.AppWakeupHistory(3600000L);
                this.mAllowWhileIdleCompatHistory = new com.android.server.alarm.AlarmManagerService.AppWakeupHistory(3600000L);
                this.mTemporaryQuotaReserve = new com.android.server.alarm.AlarmManagerService.TemporaryQuotaReserve(86400000L);
                this.mNextNonWakeup = 0L;
                this.mNextWakeup = 0L;
                this.mInjector.initializeTimeIfRequired();
                this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                this.mSystemUiUid = this.mInjector.getSystemUiUid(this.mPackageManagerInternal);
                if (this.mSystemUiUid <= 0) {
                    android.util.Slog.wtf(TAG, "SysUI package not found!");
                }
                this.mWakeLock = this.mInjector.getAlarmWakeLock();
                this.mTimeTickIntent = new android.content.Intent("android.intent.action.TIME_TICK").addFlags(1344274432);
                this.mTimeTickOptions = android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
                this.mTimeTickTrigger = new com.android.server.alarm.AlarmManagerService.AnonymousClass2();
                android.content.Intent intent = new android.content.Intent("android.intent.action.DATE_CHANGED");
                intent.addFlags(538968064);
                this.mDateChangeSender = android.app.PendingIntent.getBroadcastAsUser(getContext(), 0, intent, 67108864, android.os.UserHandle.ALL);
                this.mClockReceiver = this.mInjector.getClockReceiver(this);
                new com.android.server.alarm.AlarmManagerService.ChargingReceiver();
                new com.android.server.alarm.AlarmManagerService.InteractiveStateReceiver();
                new com.android.server.alarm.AlarmManagerService.UninstallReceiver();
                if (this.mInjector.isAlarmDriverPresent()) {
                    new com.android.server.alarm.AlarmManagerService.AlarmThread().start();
                } else {
                    android.util.Slog.w(TAG, "Failed to open alarm driver. Falling back to a handler.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        publishLocalService(com.android.server.AlarmManagerInternal.class, new com.android.server.alarm.AlarmManagerService.LocalService());
        publishBinderService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM, this.mService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$7(int[] iArr, int[] iArr2) {
        int length = iArr2.length;
        if (iArr.length != length) {
            android.util.Slog.wtf(TAG, "Got different length arrays in frozen state callback! uids.length: " + iArr.length + " frozenStates.length: " + length);
            return;
        }
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i = 0; i < length; i++) {
            if (iArr2[i] == 1 && android.app.compat.CompatChanges.isChangeEnabled(265195908L, iArr[i])) {
                intArray.add(iArr[i]);
            }
        }
        if (intArray.size() > 0) {
            removeExactListenerAlarms(intArray.toArray());
        }
    }

    /* renamed from: com.android.server.alarm.AlarmManagerService$2, reason: invalid class name */
    class AnonymousClass2 extends android.app.IAlarmListener.Stub {
        AnonymousClass2() {
        }

        public void doAlarm(final android.app.IAlarmCompleteListener iAlarmCompleteListener) throws android.os.RemoteException {
            com.android.server.alarm.AlarmManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.alarm.AlarmManagerService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.alarm.AlarmManagerService.AnonymousClass2.this.lambda$doAlarm$0(iAlarmCompleteListener);
                }
            });
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.mLastTickReceived = com.android.server.alarm.AlarmManagerService.this.mInjector.getCurrentTimeMillis();
            }
            com.android.server.alarm.AlarmManagerService.this.mClockReceiver.scheduleTimeTickEvent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$doAlarm$0(android.app.IAlarmCompleteListener iAlarmCompleteListener) {
            com.android.server.alarm.AlarmManagerService.this.getContext().sendBroadcastAsUser(com.android.server.alarm.AlarmManagerService.this.mTimeTickIntent, android.os.UserHandle.ALL, null, com.android.server.alarm.AlarmManagerService.this.mTimeTickOptions);
            try {
                iAlarmCompleteListener.alarmComplete(this);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeExactListenerAlarms(final int... iArr) {
        synchronized (this.mLock) {
            removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeExactListenerAlarms$8;
                    lambda$removeExactListenerAlarms$8 = com.android.server.alarm.AlarmManagerService.lambda$removeExactListenerAlarms$8(iArr, (com.android.server.alarm.Alarm) obj);
                    return lambda$removeExactListenerAlarms$8;
                }
            }, 6);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeExactListenerAlarms$8(int[] iArr, com.android.server.alarm.Alarm alarm) {
        if (!com.android.internal.util.jobs.ArrayUtils.contains(iArr, alarm.uid) || alarm.listener == null || alarm.windowLength != 0) {
            return false;
        }
        android.util.Slog.w(TAG, "Alarm " + alarm.listenerTag + " being removed for " + android.os.UserHandle.formatUid(alarm.uid) + ":" + alarm.packageName + " because the app got frozen");
        return true;
    }

    void refreshExactAlarmCandidates() {
        java.lang.String[] appOpPermissionPackages = this.mLocalPermissionManager.getAppOpPermissionPackages("android.permission.SCHEDULE_EXACT_ALARM");
        android.util.ArraySet arraySet = new android.util.ArraySet(appOpPermissionPackages.length);
        for (java.lang.String str : appOpPermissionPackages) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(str, 4194304L, 0);
            if (packageUid > 0) {
                arraySet.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageUid)));
            }
        }
        this.mExactAlarmCandidates = java.util.Collections.unmodifiableSet(arraySet);
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
        super.onUserStarting(targetUser);
        final int userIdentifier = targetUser.getUserIdentifier();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.alarm.AlarmManagerService.this.lambda$onUserStarting$9(userIdentifier);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserStarting$9(int i) {
        java.util.Iterator<java.lang.Integer> it = this.mExactAlarmCandidates.iterator();
        while (it.hasNext()) {
            int uid = android.os.UserHandle.getUid(i, it.next().intValue());
            com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(uid);
            if (androidPackage != null) {
                int checkOpNoThrow = this.mAppOps.checkOpNoThrow(107, uid, androidPackage.getPackageName());
                synchronized (this.mLock) {
                    this.mLastOpScheduleExactAlarm.put(uid, checkOpNoThrow);
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            synchronized (this.mLock) {
                this.mConstants.start();
                this.mAppOps = (android.app.AppOpsManager) getContext().getSystemService("appops");
                this.mLocalDeviceIdleController = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
                this.mUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
                this.mAppStateTracker = (com.android.server.AppStateTrackerImpl) com.android.server.LocalServices.getService(com.android.server.AppStateTracker.class);
                this.mAppStateTracker.addListener(this.mForceAppStandbyListener);
                this.mAppStandbyParole = ((android.os.BatteryManager) getContext().getSystemService(android.os.BatteryManager.class)).isCharging();
                this.mClockReceiver.scheduleTimeTickEvent();
                this.mClockReceiver.scheduleDateChangedEvent();
            }
            try {
                this.mInjector.getAppOpsService().startWatchingMode(107, (java.lang.String) null, new com.android.internal.app.IAppOpsCallback.Stub() { // from class: com.android.server.alarm.AlarmManagerService.3
                    public void opChanged(int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                        int valueAt;
                        boolean z;
                        int userId = android.os.UserHandle.getUserId(i3);
                        if (i2 == 107 && com.android.server.alarm.AlarmManagerService.isExactAlarmChangeEnabled(str, userId) && !com.android.server.alarm.AlarmManagerService.this.hasUseExactAlarmInternal(str, i3) && com.android.server.alarm.AlarmManagerService.this.mExactAlarmCandidates.contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(i3)))) {
                            int checkOpNoThrow = com.android.server.alarm.AlarmManagerService.this.mAppOps.checkOpNoThrow(107, i3, str);
                            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                                try {
                                    int indexOfKey = com.android.server.alarm.AlarmManagerService.this.mLastOpScheduleExactAlarm.indexOfKey(i3);
                                    if (indexOfKey < 0) {
                                        valueAt = android.app.AppOpsManager.opToDefaultMode(107);
                                        com.android.server.alarm.AlarmManagerService.this.mLastOpScheduleExactAlarm.put(i3, checkOpNoThrow);
                                    } else {
                                        valueAt = com.android.server.alarm.AlarmManagerService.this.mLastOpScheduleExactAlarm.valueAt(indexOfKey);
                                        com.android.server.alarm.AlarmManagerService.this.mLastOpScheduleExactAlarm.setValueAt(indexOfKey, checkOpNoThrow);
                                    }
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                            if (valueAt == checkOpNoThrow) {
                                return;
                            }
                            boolean z2 = true;
                            if (com.android.server.alarm.AlarmManagerService.this.isScheduleExactAlarmDeniedByDefault(str, android.os.UserHandle.getUserId(i3))) {
                                boolean z3 = com.android.server.alarm.AlarmManagerService.this.getContext().checkPermission("android.permission.SCHEDULE_EXACT_ALARM", -1, i3) == 0;
                                if (valueAt == 3) {
                                    z = z3;
                                } else {
                                    z = valueAt == 0;
                                }
                                if (checkOpNoThrow == 3) {
                                    z2 = z3;
                                } else if (checkOpNoThrow != 0) {
                                    z2 = false;
                                }
                            } else {
                                z = valueAt == 3 || valueAt == 0;
                                if (checkOpNoThrow != 3 && checkOpNoThrow != 0) {
                                    z2 = false;
                                }
                            }
                            if (z && !z2) {
                                com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(8, i3, 0, str).sendToTarget();
                            } else if (!z && z2) {
                                com.android.server.alarm.AlarmManagerService.this.sendScheduleExactAlarmPermissionStateChangedBroadcast(str, userId);
                            }
                        }
                    }
                });
            } catch (android.os.RemoteException e) {
            }
            this.mLocalPermissionManager = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
            refreshExactAlarmCandidates();
            ((com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class)).addListener(new com.android.server.alarm.AlarmManagerService.AppStandbyTracker());
            this.mBatteryStatsInternal = (android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class);
            this.mRoleManager = (android.app.role.RoleManager) getContext().getSystemService(android.app.role.RoleManager.class);
            this.mMetricsHelper.registerPuller(new java.util.function.Supplier() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.alarm.AlarmStore lambda$onBootPhase$10;
                    lambda$onBootPhase$10 = com.android.server.alarm.AlarmManagerService.this.lambda$onBootPhase$10();
                    return lambda$onBootPhase$10;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.alarm.AlarmStore lambda$onBootPhase$10() {
        return this.mAlarmStore;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mInjector.close();
        } finally {
            super.finalize();
        }
    }

    boolean setTimeImpl(long j, int i, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mInjector.setCurrentTimeMillis(j, i, str);
        }
        return true;
    }

    void setTimeZoneImpl(java.lang.String str, int i, java.lang.String str2) {
        boolean timeZoneId;
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone(str);
        synchronized (this) {
            try {
                timeZoneId = com.android.server.SystemTimeZone.setTimeZoneId(str, i, str2);
                android.os.SystemProperties.set(TIMEOFFSET_PROPERTY, java.lang.String.valueOf(timeZone.getOffset(this.mInjector.getCurrentTimeMillis())));
                java.time.zone.ZoneOffsetTransition nextTransition = timeZone.toZoneId().getRules().nextTransition(java.time.Instant.now());
                if (nextTransition != null) {
                    long millis = java.util.concurrent.TimeUnit.SECONDS.toMillis(nextTransition.getOffsetAfter().getTotalSeconds() - nextTransition.getOffsetBefore().getTotalSeconds());
                    android.os.SystemProperties.set(DST_TRANSITION_PROPERTY, java.lang.String.valueOf(java.util.concurrent.TimeUnit.SECONDS.toMillis(nextTransition.toEpochSecond())));
                    android.os.SystemProperties.set(DST_OFFSET_PROPERTY, java.lang.String.valueOf(millis));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.TimeZone.setDefault(null);
        if (timeZoneId) {
            this.mClockReceiver.scheduleDateChangedEvent();
            android.content.Intent intent = new android.content.Intent("android.intent.action.TIMEZONE_CHANGED");
            intent.addFlags(622854144);
            intent.putExtra("time-zone", timeZone.getID());
            this.mOptsTimeBroadcast.setTemporaryAppAllowlist(this.mActivityManagerInternal.getBootTimeTempAllowListDuration(), 0, 204, "");
            this.mOptsTimeBroadcast.setDeliveryGroupPolicy(1);
            getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, null, this.mOptsTimeBroadcast.toBundle());
        }
    }

    void removeImpl(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) {
        synchronized (this.mLock) {
            removeLocked(pendingIntent, iAlarmListener, 0);
        }
    }

    void setImpl(int i, long j, long j2, long j3, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str, int i2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo, int i3, java.lang.String str2, android.os.Bundle bundle, int i4) {
        long j4;
        long j5;
        long j6 = j;
        if ((pendingIntent == null && iAlarmListener == null) || (pendingIntent != null && iAlarmListener != null)) {
            android.util.Slog.w(TAG, "Alarms must either supply a PendingIntent or an AlarmReceiver");
            return;
        }
        if (iAlarmListener != null) {
            try {
                iAlarmListener.asBinder().linkToDeath(this.mListenerDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Dropping unreachable alarm listener " + str);
                return;
            }
        }
        long j7 = this.mConstants.MIN_INTERVAL;
        if (j3 > 0 && j3 < j7) {
            android.util.Slog.w(TAG, "Suspiciously short interval " + j3 + " millis; expanding to " + (j7 / 1000) + " seconds");
        } else if (j3 <= this.mConstants.MAX_INTERVAL) {
            j7 = j3;
        } else {
            android.util.Slog.w(TAG, "Suspiciously long interval " + j3 + " millis; clamping");
            j7 = this.mConstants.MAX_INTERVAL;
        }
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Invalid alarm type " + i);
        }
        if (j6 < 0) {
            android.util.Slog.w(TAG, "Invalid alarm trigger time! " + j6 + " from uid=" + i3 + " pid=" + android.os.Binder.getCallingPid());
            j6 = 0;
        }
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        long max = java.lang.Math.max((android.os.UserHandle.isCore(i3) ? 0L : this.mConstants.MIN_FUTURITY) + elapsedRealtimeMillis, convertToElapsed(j6, i));
        if (j2 == 0) {
            j5 = j2;
            j4 = j7;
        } else if (j2 < 0) {
            j4 = j7;
            j5 = maxTriggerTime(elapsedRealtimeMillis, max, j4) - max;
        } else {
            long minimumAllowedWindow = getMinimumAllowedWindow(elapsedRealtimeMillis, max);
            if (j2 > 86400000) {
                android.util.Slog.w(TAG, "Window length " + j2 + "ms too long; limiting to 1 day");
                j4 = j7;
                minimumAllowedWindow = 86400000;
            } else {
                if ((i2 & 64) != 0 || j2 >= minimumAllowedWindow) {
                    j4 = j7;
                } else if (!isExemptFromMinWindowRestrictions(i3)) {
                    j4 = j7;
                    if (android.app.compat.CompatChanges.isChangeEnabled(185199076L, str2, android.os.UserHandle.getUserHandleForUid(i3))) {
                        android.util.Slog.w(TAG, "Window length " + j2 + "ms too short; expanding to " + minimumAllowedWindow + "ms.");
                    }
                } else {
                    j4 = j7;
                }
                minimumAllowedWindow = j2;
            }
            j5 = minimumAllowedWindow;
        }
        synchronized (this.mLock) {
            try {
                try {
                    if (this.mAlarmsPerUid.get(i3, 0) >= this.mConstants.MAX_ALARMS_PER_UID) {
                        java.lang.String str3 = "Maximum limit of concurrent alarms " + this.mConstants.MAX_ALARMS_PER_UID + " reached for uid: " + android.os.UserHandle.formatUid(i3) + ", callingPackage: " + str2;
                        android.util.Slog.w(TAG, str3);
                        if (i3 != 1000) {
                            throw new java.lang.IllegalStateException(str3);
                        }
                        android.util.EventLog.writeEvent(1397638484, "234441463", -1, str3);
                    }
                    setImplLocked(i, j6, max, j5, j4, pendingIntent, iAlarmListener, str, i2, workSource, alarmClockInfo, i3, str2, bundle, i4);
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

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setImplLocked(int i, long j, long j2, long j3, long j4, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str, int i2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo, int i3, java.lang.String str2, android.os.Bundle bundle, int i4) {
        com.android.server.alarm.Alarm alarm = new com.android.server.alarm.Alarm(i, j, j2, j3, j4, pendingIntent, iAlarmListener, str, workSource, i2, alarmClockInfo, i3, str2, bundle, i4);
        if (this.mActivityManagerInternal.isAppStartModeDisabled(i3, str2)) {
            android.util.Slog.w(TAG, "Not setting alarm from " + i3 + ":" + alarm + " -- package not allowed to start");
            return;
        }
        int uidProcessState = this.mActivityManagerInternal.getUidProcessState(i3);
        removeLocked(pendingIntent, iAlarmListener, 0);
        incrementAlarmCount(alarm.uid);
        setImplLocked(alarm);
        com.android.server.alarm.MetricsHelper.pushAlarmScheduled(alarm, uidProcessState);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getQuotaForBucketLocked(int i) {
        char c;
        if (i <= 10) {
            c = 0;
        } else if (i <= 20) {
            c = 1;
        } else if (i <= 30) {
            c = 2;
        } else if (i < 50) {
            c = 3;
        } else {
            c = 4;
        }
        return this.mConstants.APP_STANDBY_QUOTAS[c];
    }

    private boolean adjustIdleUntilTime(com.android.server.alarm.Alarm alarm) {
        if ((alarm.flags & 16) == 0) {
            return false;
        }
        boolean restoreRequestedTime = restoreRequestedTime(alarm);
        if (this.mNextWakeFromIdle == null) {
            return restoreRequestedTime;
        }
        long whenElapsed = this.mNextWakeFromIdle.getWhenElapsed();
        if (alarm.getWhenElapsed() < whenElapsed - this.mConstants.MIN_DEVICE_IDLE_FUZZ) {
            return restoreRequestedTime;
        }
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        long j = whenElapsed - elapsedRealtimeMillis;
        if (j <= this.mConstants.MIN_DEVICE_IDLE_FUZZ) {
            alarm.setPolicyElapsed(0, elapsedRealtimeMillis);
            return true;
        }
        alarm.setPolicyElapsed(0, whenElapsed - java.util.concurrent.ThreadLocalRandom.current().nextLong(this.mConstants.MIN_DEVICE_IDLE_FUZZ, java.lang.Math.min(this.mConstants.MAX_DEVICE_IDLE_FUZZ, j) + 1));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean adjustDeliveryTimeBasedOnBatterySaver(com.android.server.alarm.Alarm alarm) {
        int i;
        long j;
        com.android.server.alarm.AlarmManagerService.AppWakeupHistory appWakeupHistory;
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        if (isExemptFromBatterySaver(alarm)) {
            return false;
        }
        if (this.mAppStateTracker == null || !this.mAppStateTracker.areAlarmsRestrictedByBatterySaver(alarm.creatorUid, alarm.sourcePackage)) {
            return alarm.setPolicyElapsed(3, elapsedRealtimeMillis);
        }
        if ((alarm.flags & 8) == 0) {
            if (isAllowedWhileIdleRestricted(alarm)) {
                int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
                if ((alarm.flags & 4) != 0) {
                    i = this.mConstants.ALLOW_WHILE_IDLE_QUOTA;
                    j = this.mConstants.ALLOW_WHILE_IDLE_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleHistory;
                } else {
                    i = this.mConstants.ALLOW_WHILE_IDLE_COMPAT_QUOTA;
                    j = this.mConstants.ALLOW_WHILE_IDLE_COMPAT_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleCompatHistory;
                }
                if (appWakeupHistory.getTotalWakeupsInWindow(alarm.sourcePackage, userId) >= i) {
                    elapsedRealtimeMillis = appWakeupHistory.getNthLastWakeupForPackage(alarm.sourcePackage, userId, i) + j;
                }
            } else if ((alarm.flags & 64) != 0) {
                long j2 = this.mLastPriorityAlarmDispatch.get(alarm.creatorUid, 0L);
                if (j2 != 0) {
                    elapsedRealtimeMillis = this.mConstants.PRIORITY_ALARM_DELAY + j2;
                }
            } else {
                elapsedRealtimeMillis += 31536000000L;
            }
        }
        return alarm.setPolicyElapsed(3, elapsedRealtimeMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAllowedWhileIdleRestricted(com.android.server.alarm.Alarm alarm) {
        return (alarm.flags & 36) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: adjustDeliveryTimeBasedOnDeviceIdle, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean lambda$triggerAlarmsLocked$25(com.android.server.alarm.Alarm alarm) {
        int i;
        long j;
        com.android.server.alarm.AlarmManagerService.AppWakeupHistory appWakeupHistory;
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        if (this.mPendingIdleUntil == null || this.mPendingIdleUntil == alarm) {
            return alarm.setPolicyElapsed(2, elapsedRealtimeMillis);
        }
        if ((alarm.flags & 10) == 0) {
            if (isAllowedWhileIdleRestricted(alarm)) {
                int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
                if ((alarm.flags & 4) != 0) {
                    i = this.mConstants.ALLOW_WHILE_IDLE_QUOTA;
                    j = this.mConstants.ALLOW_WHILE_IDLE_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleHistory;
                } else {
                    i = this.mConstants.ALLOW_WHILE_IDLE_COMPAT_QUOTA;
                    j = this.mConstants.ALLOW_WHILE_IDLE_COMPAT_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleCompatHistory;
                }
                if (appWakeupHistory.getTotalWakeupsInWindow(alarm.sourcePackage, userId) >= i) {
                    elapsedRealtimeMillis = java.lang.Math.min(appWakeupHistory.getNthLastWakeupForPackage(alarm.sourcePackage, userId, i) + j, this.mPendingIdleUntil.getWhenElapsed());
                }
            } else if ((alarm.flags & 64) != 0) {
                long j2 = this.mLastPriorityAlarmDispatch.get(alarm.creatorUid, 0L);
                if (j2 != 0) {
                    elapsedRealtimeMillis = this.mConstants.PRIORITY_ALARM_DELAY + j2;
                }
                elapsedRealtimeMillis = java.lang.Math.min(elapsedRealtimeMillis, this.mPendingIdleUntil.getWhenElapsed());
            } else {
                elapsedRealtimeMillis = this.mPendingIdleUntil.getWhenElapsed();
            }
        }
        return alarm.setPolicyElapsed(2, elapsedRealtimeMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean adjustDeliveryTimeBasedOnBucketLocked(com.android.server.alarm.Alarm alarm) {
        long nthLastWakeupForPackage;
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        if (this.mConstants.USE_TARE_POLICY == 1 || isExemptFromAppStandby(alarm) || this.mAppStandbyParole) {
            return alarm.setPolicyElapsed(1, elapsedRealtimeMillis);
        }
        java.lang.String str = alarm.sourcePackage;
        int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
        int appStandbyBucket = this.mUsageStatsManagerInternal.getAppStandbyBucket(str, userId, elapsedRealtimeMillis);
        int totalWakeupsInWindow = this.mAppWakeupHistory.getTotalWakeupsInWindow(str, userId);
        if (appStandbyBucket == 45) {
            if (totalWakeupsInWindow > 0) {
                long nthLastWakeupForPackage2 = this.mAppWakeupHistory.getNthLastWakeupForPackage(str, userId, this.mConstants.APP_STANDBY_RESTRICTED_QUOTA);
                if (elapsedRealtimeMillis - nthLastWakeupForPackage2 < this.mConstants.APP_STANDBY_RESTRICTED_WINDOW) {
                    return alarm.setPolicyElapsed(1, nthLastWakeupForPackage2 + this.mConstants.APP_STANDBY_RESTRICTED_WINDOW);
                }
            }
        } else {
            int quotaForBucketLocked = getQuotaForBucketLocked(appStandbyBucket);
            if (totalWakeupsInWindow >= quotaForBucketLocked) {
                if (this.mTemporaryQuotaReserve.hasQuota(str, userId, elapsedRealtimeMillis)) {
                    alarm.mUsingReserveQuota = true;
                    return alarm.setPolicyElapsed(1, elapsedRealtimeMillis);
                }
                if (quotaForBucketLocked <= 0) {
                    nthLastWakeupForPackage = elapsedRealtimeMillis + 31536000000L;
                } else {
                    nthLastWakeupForPackage = this.mAppWakeupHistory.getNthLastWakeupForPackage(str, userId, quotaForBucketLocked) + this.mConstants.APP_STANDBY_WINDOW;
                }
                return alarm.setPolicyElapsed(1, nthLastWakeupForPackage);
            }
        }
        alarm.mUsingReserveQuota = false;
        return alarm.setPolicyElapsed(1, elapsedRealtimeMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean adjustDeliveryTimeBasedOnTareLocked(com.android.server.alarm.Alarm alarm) {
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        if (this.mConstants.USE_TARE_POLICY != 1 || isExemptFromTare(alarm) || hasEnoughWealthLocked(alarm)) {
            return alarm.setPolicyElapsed(4, elapsedRealtimeMillis);
        }
        return alarm.setPolicyElapsed(4, elapsedRealtimeMillis + 31536000000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerTareListener(com.android.server.alarm.Alarm alarm) {
        if (this.mConstants.USE_TARE_POLICY != 1) {
            return;
        }
        this.mEconomyManagerInternal.registerAffordabilityChangeListener(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage, this.mAffordabilityChangeListener, com.android.server.alarm.TareBill.getAppropriateBill(alarm));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeUnregisterTareListenerLocked(final com.android.server.alarm.Alarm alarm) {
        if (this.mConstants.USE_TARE_POLICY != 1) {
            return;
        }
        final com.android.server.tare.EconomyManagerInternal.ActionBill appropriateBill = com.android.server.alarm.TareBill.getAppropriateBill(alarm);
        if (this.mAlarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$maybeUnregisterTareListenerLocked$11;
                lambda$maybeUnregisterTareListenerLocked$11 = com.android.server.alarm.AlarmManagerService.lambda$maybeUnregisterTareListenerLocked$11(com.android.server.alarm.Alarm.this, appropriateBill, (com.android.server.alarm.Alarm) obj);
                return lambda$maybeUnregisterTareListenerLocked$11;
            }
        }) == 0) {
            int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
            this.mEconomyManagerInternal.unregisterAffordabilityChangeListener(userId, alarm.sourcePackage, this.mAffordabilityChangeListener, appropriateBill);
            android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mAffordabilityCache.get(userId, alarm.sourcePackage);
            if (arrayMap != null) {
                arrayMap.remove(appropriateBill);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$maybeUnregisterTareListenerLocked$11(com.android.server.alarm.Alarm alarm, com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, com.android.server.alarm.Alarm alarm2) {
        return alarm.creatorUid == alarm2.creatorUid && alarm.sourcePackage.equals(alarm2.sourcePackage) && actionBill.equals(com.android.server.alarm.TareBill.getAppropriateBill(alarm2));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setImplLocked(com.android.server.alarm.Alarm alarm) {
        if ((alarm.flags & 16) != 0) {
            adjustIdleUntilTime(alarm);
            if (this.mPendingIdleUntil != alarm && this.mPendingIdleUntil != null) {
                android.util.Slog.wtfStack(TAG, "setImplLocked: idle until changed from " + this.mPendingIdleUntil + " to " + alarm);
                com.android.server.alarm.AlarmStore alarmStore = this.mAlarmStore;
                final com.android.server.alarm.Alarm alarm2 = this.mPendingIdleUntil;
                java.util.Objects.requireNonNull(alarm2);
                alarmStore.remove(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return com.android.server.alarm.Alarm.this.equals((com.android.server.alarm.Alarm) obj);
                    }
                });
            }
            this.mPendingIdleUntil = alarm;
            this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda10
                @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm3) {
                    boolean lambda$setImplLocked$12;
                    lambda$setImplLocked$12 = com.android.server.alarm.AlarmManagerService.this.lambda$setImplLocked$12(alarm3);
                    return lambda$setImplLocked$12;
                }
            });
        } else if (this.mPendingIdleUntil != null) {
            lambda$triggerAlarmsLocked$25(alarm);
        }
        if ((alarm.flags & 2) != 0 && (this.mNextWakeFromIdle == null || this.mNextWakeFromIdle.getWhenElapsed() > alarm.getWhenElapsed())) {
            this.mNextWakeFromIdle = alarm;
            if (this.mPendingIdleUntil != null && this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda11
                @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm3) {
                    boolean lambda$setImplLocked$13;
                    lambda$setImplLocked$13 = com.android.server.alarm.AlarmManagerService.this.lambda$setImplLocked$13(alarm3);
                    return lambda$setImplLocked$13;
                }
            })) {
                this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda12
                    @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                    public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm3) {
                        boolean lambda$setImplLocked$14;
                        lambda$setImplLocked$14 = com.android.server.alarm.AlarmManagerService.this.lambda$setImplLocked$14(alarm3);
                        return lambda$setImplLocked$14;
                    }
                });
            }
        }
        if (alarm.alarmClock != null) {
            this.mNextAlarmClockMayChange = true;
        }
        adjustDeliveryTimeBasedOnBatterySaver(alarm);
        adjustDeliveryTimeBasedOnBucketLocked(alarm);
        adjustDeliveryTimeBasedOnTareLocked(alarm);
        registerTareListener(alarm);
        this.mAlarmStore.add(alarm);
        rescheduleKernelAlarmsLocked();
        updateNextAlarmClockLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setImplLocked$13(com.android.server.alarm.Alarm alarm) {
        return alarm == this.mPendingIdleUntil && adjustIdleUntilTime(alarm);
    }

    private final class LocalService implements com.android.server.AlarmManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.AlarmManagerInternal
        public boolean isIdling() {
            return com.android.server.alarm.AlarmManagerService.this.isIdlingImpl();
        }

        @Override // com.android.server.AlarmManagerInternal
        public void removeAlarmsForUid(int i) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.removeLocked(i, 3);
            }
        }

        @Override // com.android.server.AlarmManagerInternal
        public void remove(android.app.PendingIntent pendingIntent) {
            com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(7, pendingIntent).sendToTarget();
        }

        @Override // com.android.server.AlarmManagerInternal
        public boolean shouldGetBucketElevation(java.lang.String str, int i) {
            return com.android.server.alarm.AlarmManagerService.this.hasUseExactAlarmInternal(str, i) || (!android.app.compat.CompatChanges.isChangeEnabled(262645982L, str, android.os.UserHandle.getUserHandleForUid(i)) && com.android.server.alarm.AlarmManagerService.this.hasScheduleExactAlarmInternal(str, i));
        }

        @Override // com.android.server.AlarmManagerInternal
        public void setTimeZone(java.lang.String str, int i, java.lang.String str2) {
            com.android.server.alarm.AlarmManagerService.this.setTimeZoneImpl(str, i, str2);
        }

        @Override // com.android.server.AlarmManagerInternal
        public void setTime(long j, int i, java.lang.String str) {
            com.android.server.alarm.AlarmManagerService.this.setTimeImpl(j, i, str);
        }

        @Override // com.android.server.AlarmManagerInternal
        public void registerInFlightListener(com.android.server.AlarmManagerInternal.InFlightListener inFlightListener) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.mInFlightListeners.add(inFlightListener);
            }
        }
    }

    boolean hasUseExactAlarmInternal(java.lang.String str, int i) {
        return isUseExactAlarmEnabled(str, android.os.UserHandle.getUserId(i)) && android.content.PermissionChecker.checkPermissionForPreflight(getContext(), "android.permission.USE_EXACT_ALARM", -1, i, str) == 0;
    }

    boolean hasScheduleExactAlarmInternal(java.lang.String str, int i) {
        long time = this.mStatLogger.getTime();
        boolean z = false;
        if (this.mExactAlarmCandidates.contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(i))) && isExactAlarmChangeEnabled(str, android.os.UserHandle.getUserId(i))) {
            if (isScheduleExactAlarmDeniedByDefault(str, android.os.UserHandle.getUserId(i))) {
                if (android.content.PermissionChecker.checkPermissionForPreflight(getContext(), "android.permission.SCHEDULE_EXACT_ALARM", -1, i, str) == 0) {
                    z = true;
                }
            } else {
                int checkOpNoThrow = this.mAppOps.checkOpNoThrow(107, i, str);
                if (checkOpNoThrow == 3 || checkOpNoThrow == 0) {
                    z = true;
                }
            }
        }
        this.mStatLogger.logDurationStat(1, time);
        return z;
    }

    boolean isExemptFromMinWindowRestrictions(int i) {
        return isExemptFromExactAlarmPermissionNoLock(i);
    }

    boolean isExemptFromExactAlarmPermissionNoLock(int i) {
        if (android.os.Build.IS_DEBUGGABLE && java.lang.Thread.holdsLock(this.mLock)) {
            android.util.Slog.wtfStack(TAG, "Alarm lock held while calling into DeviceIdleController");
        }
        return android.os.UserHandle.isSameApp(this.mSystemUiUid, i) || android.os.UserHandle.isCore(i) || this.mLocalDeviceIdleController == null || this.mLocalDeviceIdleController.isAppOnWhitelist(android.os.UserHandle.getAppId(i));
    }

    /* renamed from: com.android.server.alarm.AlarmManagerService$4, reason: invalid class name */
    class AnonymousClass4 extends android.app.IAlarmManager.Stub {
        AnonymousClass4() {
        }

        public void set(java.lang.String str, int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, java.lang.String str2, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) {
            long j4;
            int i3;
            android.os.Bundle bundle;
            boolean z;
            boolean z2;
            int i4;
            android.os.Bundle bundle2;
            int i5;
            int callingUid = com.android.server.alarm.AlarmManagerService.this.mInjector.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            if (callingUid != com.android.server.alarm.AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, userId)) {
                throw new java.lang.SecurityException("Package " + str + " does not belong to the calling uid " + callingUid);
            }
            if (j3 != 0 && iAlarmListener != null) {
                throw new java.lang.IllegalArgumentException("Repeating alarms cannot use AlarmReceivers");
            }
            if (workSource != null) {
                com.android.server.alarm.AlarmManagerService.this.getContext().enforcePermission("android.permission.UPDATE_DEVICE_STATS", android.os.Binder.getCallingPid(), callingUid, "AlarmManager.set");
            }
            if ((i2 & 16) == 0) {
                j4 = j2;
                i3 = i2;
            } else if (callingUid != 1000) {
                i3 = i2 & (-17);
                j4 = j2;
            } else {
                i3 = i2;
                j4 = 0;
            }
            int i6 = i3 & (-43);
            if (alarmClockInfo != null) {
                i6 |= 2;
                j4 = 0;
            } else if (workSource == null && (android.os.UserHandle.isCore(callingUid) || android.os.UserHandle.isSameApp(callingUid, com.android.server.alarm.AlarmManagerService.this.mSystemUiUid) || (com.android.server.alarm.AlarmManagerService.this.mAppStateTracker != null && com.android.server.alarm.AlarmManagerService.this.mAppStateTracker.isUidPowerSaveIdleExempt(callingUid)))) {
                i6 = (i6 | 8) & (-69);
            }
            int i7 = 0;
            boolean z3 = (i6 & 4) != 0;
            boolean z4 = j4 == 0;
            int i8 = -1;
            if ((i6 & 64) != 0) {
                com.android.server.alarm.AlarmManagerService.this.getContext().enforcePermission("android.permission.SCHEDULE_PRIORITIZED_ALARM", android.os.Binder.getCallingPid(), callingUid, "AlarmManager.setPrioritized");
                i6 &= -5;
                if (!z4) {
                    bundle2 = null;
                    i4 = -1;
                } else {
                    i4 = 5;
                    bundle2 = null;
                }
            } else if (z4 || z3) {
                if (com.android.server.alarm.AlarmManagerService.isExactAlarmChangeEnabled(str, userId)) {
                    if (iAlarmListener == null) {
                        z = !z4;
                        z2 = z4;
                    } else if (!z4) {
                        z = z3;
                        z2 = false;
                    } else {
                        i8 = 4;
                        z = z3;
                        z2 = false;
                    }
                    if (z4) {
                        if (alarmClockInfo != null) {
                            bundle = com.android.server.alarm.AlarmManagerService.this.mOptsWithFgsForAlarmClock.toBundle();
                        } else {
                            bundle = com.android.server.alarm.AlarmManagerService.this.mOptsWithFgs.toBundle();
                        }
                    } else {
                        bundle = com.android.server.alarm.AlarmManagerService.this.mOptsWithoutFgs.toBundle();
                    }
                } else {
                    if (z3 || alarmClockInfo != null) {
                        bundle = com.android.server.alarm.AlarmManagerService.this.mOptsWithFgs.toBundle();
                    } else {
                        bundle = null;
                    }
                    if (!z4) {
                        z = z3;
                        z2 = false;
                    } else {
                        i8 = 2;
                        z = z3;
                        z2 = false;
                    }
                }
                if (!z2) {
                    z3 = z;
                    i7 = i8;
                } else if (com.android.server.alarm.AlarmManagerService.this.hasUseExactAlarmInternal(str, callingUid)) {
                    i7 = 3;
                    z3 = z;
                } else if (com.android.server.alarm.AlarmManagerService.this.hasScheduleExactAlarmInternal(str, callingUid)) {
                    z3 = z;
                } else {
                    if (!com.android.server.alarm.AlarmManagerService.this.isExemptFromExactAlarmPermissionNoLock(callingUid)) {
                        throw new java.lang.SecurityException("Caller " + str + " needs to hold android.permission.SCHEDULE_EXACT_ALARM or android.permission.USE_EXACT_ALARM to set exact alarms.");
                    }
                    i7 = 1;
                    bundle = z3 ? com.android.server.alarm.AlarmManagerService.this.mOptsWithoutFgs.toBundle() : null;
                }
                if (!z3) {
                    i4 = i7;
                    bundle2 = bundle;
                } else {
                    i6 = (i6 & (-5)) | 32;
                    i4 = i7;
                    bundle2 = bundle;
                }
            } else {
                bundle2 = null;
                i4 = -1;
            }
            if (!z4) {
                i5 = i6;
            } else {
                i5 = i6 | 1;
            }
            com.android.server.alarm.AlarmManagerService.this.setImpl(i, j, j4, j3, pendingIntent, iAlarmListener, str2, i5, workSource, alarmClockInfo, callingUid, str, bundle2, i4);
        }

        public boolean canScheduleExactAlarms(java.lang.String str) {
            int callingUid = com.android.server.alarm.AlarmManagerService.this.mInjector.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            int packageUid = com.android.server.alarm.AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, userId);
            if (callingUid == packageUid) {
                return !com.android.server.alarm.AlarmManagerService.isExactAlarmChangeEnabled(str, userId) || com.android.server.alarm.AlarmManagerService.this.isExemptFromExactAlarmPermissionNoLock(packageUid) || com.android.server.alarm.AlarmManagerService.this.hasScheduleExactAlarmInternal(str, packageUid) || com.android.server.alarm.AlarmManagerService.this.hasUseExactAlarmInternal(str, packageUid);
            }
            throw new java.lang.SecurityException("Uid " + callingUid + " cannot query canScheduleExactAlarms for package " + str);
        }

        public boolean hasScheduleExactAlarm(java.lang.String str, int i) {
            int callingUid = com.android.server.alarm.AlarmManagerService.this.mInjector.getCallingUid();
            if (android.os.UserHandle.getUserId(callingUid) != i) {
                com.android.server.alarm.AlarmManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "hasScheduleExactAlarm");
            }
            int packageUid = com.android.server.alarm.AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, i);
            if (callingUid != packageUid && !android.os.UserHandle.isCore(callingUid)) {
                throw new java.lang.SecurityException("Uid " + callingUid + " cannot query hasScheduleExactAlarm for package " + str);
            }
            if (packageUid > 0) {
                return com.android.server.alarm.AlarmManagerService.this.hasScheduleExactAlarmInternal(str, packageUid);
            }
            return false;
        }

        @android.annotation.EnforcePermission("android.permission.SET_TIME")
        public boolean setTime(long j) {
            setTime_enforcePermission();
            return com.android.server.alarm.AlarmManagerService.this.setTimeImpl(j, 100, "AlarmManager.setTime() called");
        }

        @android.annotation.EnforcePermission("android.permission.SET_TIME_ZONE")
        public void setTimeZone(java.lang.String str) {
            setTimeZone_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.alarm.AlarmManagerService.this.setTimeZoneImpl(str, 100, "AlarmManager.setTimeZone() called");
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void remove(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener) {
            if (pendingIntent == null && iAlarmListener == null) {
                android.util.Slog.w(com.android.server.alarm.AlarmManagerService.TAG, "remove() with no intent or listener");
                return;
            }
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.removeLocked(pendingIntent, iAlarmListener, 1);
            }
        }

        public void removeAll(final java.lang.String str) {
            final int callingUid = com.android.server.alarm.AlarmManagerService.this.mInjector.getCallingUid();
            if (callingUid == 1000) {
                android.util.Slog.wtfStack(com.android.server.alarm.AlarmManagerService.TAG, "Attempt to remove all alarms from the system uid package: " + str);
                return;
            }
            if (callingUid != com.android.server.alarm.AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(callingUid))) {
                throw new java.lang.SecurityException("Package " + str + " does not belong to the calling uid " + callingUid);
            }
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$4$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$removeAll$0;
                        lambda$removeAll$0 = com.android.server.alarm.AlarmManagerService.AnonymousClass4.lambda$removeAll$0(str, callingUid, (com.android.server.alarm.Alarm) obj);
                        return lambda$removeAll$0;
                    }
                }, 1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeAll$0(java.lang.String str, int i, com.android.server.alarm.Alarm alarm) {
            return alarm.matches(str) && alarm.creatorUid == i;
        }

        public long getNextWakeFromIdleTime() {
            return com.android.server.alarm.AlarmManagerService.this.getNextWakeFromIdleTimeImpl();
        }

        public android.app.AlarmManager.AlarmClockInfo getNextAlarmClock(int i) {
            return com.android.server.alarm.AlarmManagerService.this.getNextAlarmClockImpl(com.android.server.alarm.AlarmManagerService.this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 0, "getNextAlarmClock", (java.lang.String) null));
        }

        @android.annotation.EnforcePermission("android.permission.DUMP")
        public int getConfigVersion() {
            getConfigVersion_enforcePermission();
            return com.android.server.alarm.AlarmManagerService.this.mConstants.getVersion();
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.jobs.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.alarm.AlarmManagerService.this.getContext(), com.android.server.alarm.AlarmManagerService.TAG, printWriter)) {
                if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                    com.android.server.alarm.AlarmManagerService.this.dumpProto(fileDescriptor);
                } else {
                    com.android.server.alarm.AlarmManagerService.this.dumpImpl(new android.util.IndentingPrintWriter(printWriter, "  "));
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.alarm.AlarmManagerService.ShellCmd().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isExactAlarmChangeEnabled(java.lang.String str, int i) {
        return android.app.compat.CompatChanges.isChangeEnabled(171306433L, str, android.os.UserHandle.of(i));
    }

    private static boolean isUseExactAlarmEnabled(java.lang.String str, int i) {
        return android.app.compat.CompatChanges.isChangeEnabled(218533173L, str, android.os.UserHandle.of(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isScheduleExactAlarmDeniedByDefault(java.lang.String str, int i) {
        return android.app.compat.CompatChanges.isChangeEnabled(226439802L, str, android.os.UserHandle.of(i));
    }

    @dalvik.annotation.optimization.NeverCompile
    void dumpImpl(final android.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.String str;
        com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats;
        long j;
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Current Alarm Manager state:");
                indentingPrintWriter.increaseIndent();
                this.mConstants.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Feature Flags:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print(com.android.server.alarm.Flags.FLAG_USE_FROZEN_STATE_TO_DROP_LISTENER_ALARMS, java.lang.Boolean.valueOf(this.mUseFrozenStateToDropListenerAlarms));
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                if (this.mConstants.USE_TARE_POLICY == 1) {
                    indentingPrintWriter.println("TARE details:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Affordability cache:");
                    indentingPrintWriter.increaseIndent();
                    this.mAffordabilityCache.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda17
                        public final void accept(int i, java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.alarm.AlarmManagerService.lambda$dumpImpl$15(indentingPrintWriter, i, (java.lang.String) obj, (android.util.ArrayMap) obj2);
                        }
                    });
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.println("App Standby Parole: " + this.mAppStandbyParole);
                    indentingPrintWriter.println();
                }
                if (this.mAppStateTracker != null) {
                    this.mAppStateTracker.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
                java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                indentingPrintWriter.print("nowRTC=");
                indentingPrintWriter.print(currentTimeMillis);
                indentingPrintWriter.print("=");
                indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(currentTimeMillis)));
                indentingPrintWriter.print(" nowELAPSED=");
                indentingPrintWriter.print(elapsedRealtimeMillis);
                indentingPrintWriter.println();
                indentingPrintWriter.print("mLastTimeChangeClockTime=");
                indentingPrintWriter.print(this.mLastTimeChangeClockTime);
                indentingPrintWriter.print("=");
                indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(this.mLastTimeChangeClockTime)));
                indentingPrintWriter.print("mLastTimeChangeRealtime=");
                indentingPrintWriter.println(this.mLastTimeChangeRealtime);
                indentingPrintWriter.print("mLastTickReceived=");
                indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(this.mLastTickReceived)));
                indentingPrintWriter.print("mLastTickSet=");
                indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(this.mLastTickSet)));
                indentingPrintWriter.println();
                indentingPrintWriter.println("Recent TIME_TICK history:");
                indentingPrintWriter.increaseIndent();
                int i = this.mNextTickHistory;
                do {
                    i--;
                    if (i < 0) {
                        i = 9;
                    }
                    long j2 = this.mTickHistory[i];
                    if (j2 > 0) {
                        str = simpleDateFormat.format(new java.util.Date(currentTimeMillis - (elapsedRealtimeMillis - j2)));
                    } else {
                        str = "-";
                    }
                    indentingPrintWriter.println(str);
                } while (i != this.mNextTickHistory);
                indentingPrintWriter.decreaseIndent();
                com.android.server.SystemServiceManager systemServiceManager = (com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class);
                if (systemServiceManager != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("RuntimeStarted=");
                    indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date((currentTimeMillis - elapsedRealtimeMillis) + systemServiceManager.getRuntimeStartElapsedTime())));
                    if (systemServiceManager.isRuntimeRestarted()) {
                        indentingPrintWriter.print("  (Runtime restarted)");
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Runtime uptime (elapsed): ");
                    android.util.TimeUtils.formatDuration(elapsedRealtimeMillis, systemServiceManager.getRuntimeStartElapsedTime(), indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Runtime uptime (uptime): ");
                    android.util.TimeUtils.formatDuration(uptimeMillis, systemServiceManager.getRuntimeStartUptime(), indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println();
                if (!this.mInteractive) {
                    indentingPrintWriter.print("Time since non-interactive: ");
                    android.util.TimeUtils.formatDuration(elapsedRealtimeMillis - this.mNonInteractiveStartTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Max wakeup delay: ");
                android.util.TimeUtils.formatDuration(currentNonWakeupFuzzLocked(elapsedRealtimeMillis), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Time since last dispatch: ");
                android.util.TimeUtils.formatDuration(elapsedRealtimeMillis - this.mLastAlarmDeliveryTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next non-wakeup delivery time: ");
                android.util.TimeUtils.formatDuration(this.mNextNonWakeupDeliveryTime, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.println();
                long j3 = currentTimeMillis - elapsedRealtimeMillis;
                long j4 = this.mNextWakeup + j3;
                long j5 = currentTimeMillis;
                long j6 = this.mNextNonWakeup + j3;
                indentingPrintWriter.print("Next non-wakeup alarm: ");
                android.util.TimeUtils.formatDuration(this.mNextNonWakeup, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.print(this.mNextNonWakeup);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(j6)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("set at ");
                android.util.TimeUtils.formatDuration(this.mNextNonWakeUpSetAt, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next wakeup alarm: ");
                android.util.TimeUtils.formatDuration(this.mNextWakeup, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.print(this.mNextWakeup);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(simpleDateFormat.format(new java.util.Date(j4)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("set at ");
                android.util.TimeUtils.formatDuration(this.mNextWakeUpSetAt, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next kernel non-wakeup alarm: ");
                android.util.TimeUtils.formatDuration(this.mInjector.getNextAlarm(3), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next kernel wakeup alarm: ");
                android.util.TimeUtils.formatDuration(this.mInjector.getNextAlarm(2), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Last wakeup: ");
                android.util.TimeUtils.formatDuration(this.mLastWakeup, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(this.mLastWakeup);
                indentingPrintWriter.print("Last trigger: ");
                android.util.TimeUtils.formatDuration(this.mLastTrigger, elapsedRealtimeMillis, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(this.mLastTrigger);
                indentingPrintWriter.print("Num time change events: ");
                indentingPrintWriter.println(this.mNumTimeChanged);
                indentingPrintWriter.println();
                indentingPrintWriter.println("App ids requesting SCHEDULE_EXACT_ALARM: " + this.mExactAlarmCandidates);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Last OP_SCHEDULE_EXACT_ALARM: [");
                int i2 = 0;
                for (int i3 = 0; i3 < this.mLastOpScheduleExactAlarm.size(); i3++) {
                    if (i3 > 0) {
                        indentingPrintWriter.print(", ");
                    }
                    android.os.UserHandle.formatUid(indentingPrintWriter, this.mLastOpScheduleExactAlarm.keyAt(i3));
                    indentingPrintWriter.print(":" + android.app.AppOpsManager.modeToName(this.mLastOpScheduleExactAlarm.valueAt(i3)));
                }
                indentingPrintWriter.println("]");
                indentingPrintWriter.println();
                indentingPrintWriter.println("Next alarm clock information: ");
                indentingPrintWriter.increaseIndent();
                java.util.TreeSet treeSet = new java.util.TreeSet();
                for (int i4 = 0; i4 < this.mNextAlarmClockForUser.size(); i4++) {
                    treeSet.add(java.lang.Integer.valueOf(this.mNextAlarmClockForUser.keyAt(i4)));
                }
                for (int i5 = 0; i5 < this.mPendingSendNextAlarmClockChangedForUser.size(); i5++) {
                    treeSet.add(java.lang.Integer.valueOf(this.mPendingSendNextAlarmClockChangedForUser.keyAt(i5)));
                }
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    android.app.AlarmManager.AlarmClockInfo alarmClockInfo = this.mNextAlarmClockForUser.get(intValue);
                    long triggerTime = alarmClockInfo != null ? alarmClockInfo.getTriggerTime() : 0L;
                    boolean z = this.mPendingSendNextAlarmClockChangedForUser.get(intValue);
                    indentingPrintWriter.print("user:");
                    indentingPrintWriter.print(intValue);
                    indentingPrintWriter.print(" pendingSend:");
                    indentingPrintWriter.print(z);
                    indentingPrintWriter.print(" time:");
                    indentingPrintWriter.print(triggerTime);
                    if (triggerTime <= 0) {
                        j = j5;
                    } else {
                        indentingPrintWriter.print(" = ");
                        indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date(triggerTime)));
                        indentingPrintWriter.print(" = ");
                        j = j5;
                        android.util.TimeUtils.formatDuration(triggerTime, j, indentingPrintWriter);
                    }
                    indentingPrintWriter.println();
                    j5 = j;
                }
                indentingPrintWriter.decreaseIndent();
                if (this.mAlarmStore.size() > 0) {
                    indentingPrintWriter.println();
                    this.mAlarmStore.dump(indentingPrintWriter, elapsedRealtimeMillis, simpleDateFormat);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Pending user blocked background alarms: ");
                indentingPrintWriter.increaseIndent();
                boolean z2 = false;
                for (int i6 = 0; i6 < this.mPendingBackgroundAlarms.size(); i6++) {
                    java.util.ArrayList<com.android.server.alarm.Alarm> valueAt = this.mPendingBackgroundAlarms.valueAt(i6);
                    if (valueAt != null && valueAt.size() > 0) {
                        dumpAlarmList(indentingPrintWriter, valueAt, elapsedRealtimeMillis, simpleDateFormat);
                        z2 = true;
                    }
                }
                if (!z2) {
                    indentingPrintWriter.println("none");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Pending alarms per uid: [");
                for (int i7 = 0; i7 < this.mAlarmsPerUid.size(); i7++) {
                    if (i7 > 0) {
                        indentingPrintWriter.print(", ");
                    }
                    android.os.UserHandle.formatUid(indentingPrintWriter, this.mAlarmsPerUid.keyAt(i7));
                    indentingPrintWriter.print(":");
                    indentingPrintWriter.print(this.mAlarmsPerUid.valueAt(i7));
                }
                indentingPrintWriter.println("]");
                indentingPrintWriter.println();
                indentingPrintWriter.println("App Alarm history:");
                this.mAppWakeupHistory.dump(indentingPrintWriter, elapsedRealtimeMillis);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Temporary Quota Reserves:");
                this.mTemporaryQuotaReserve.dump(indentingPrintWriter, elapsedRealtimeMillis);
                if (this.mPendingIdleUntil != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Idle mode state:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.print("Idling until: ");
                    if (this.mPendingIdleUntil != null) {
                        indentingPrintWriter.println(this.mPendingIdleUntil);
                        this.mPendingIdleUntil.dump(indentingPrintWriter, elapsedRealtimeMillis, simpleDateFormat);
                    } else {
                        indentingPrintWriter.println("null");
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mNextWakeFromIdle != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Next wake from idle: ");
                    indentingPrintWriter.println(this.mNextWakeFromIdle);
                    indentingPrintWriter.increaseIndent();
                    this.mNextWakeFromIdle.dump(indentingPrintWriter, elapsedRealtimeMillis, simpleDateFormat);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.print("Past-due non-wakeup alarms: ");
                if (this.mPendingNonWakeupAlarms.size() > 0) {
                    indentingPrintWriter.println(this.mPendingNonWakeupAlarms.size());
                    indentingPrintWriter.increaseIndent();
                    dumpAlarmList(indentingPrintWriter, this.mPendingNonWakeupAlarms, elapsedRealtimeMillis, simpleDateFormat);
                    indentingPrintWriter.decreaseIndent();
                } else {
                    indentingPrintWriter.println("(none)");
                }
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("Number of delayed alarms: ");
                indentingPrintWriter.print(this.mNumDelayedAlarms);
                indentingPrintWriter.print(", total delay time: ");
                android.util.TimeUtils.formatDuration(this.mTotalDelayTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Max delay time: ");
                android.util.TimeUtils.formatDuration(this.mMaxDelayTime, indentingPrintWriter);
                indentingPrintWriter.print(", max non-interactive time: ");
                android.util.TimeUtils.formatDuration(this.mNonInteractiveTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Broadcast ref count: ");
                indentingPrintWriter.println(this.mBroadcastRefCount);
                indentingPrintWriter.print("PendingIntent send count: ");
                indentingPrintWriter.println(this.mSendCount);
                indentingPrintWriter.print("PendingIntent finish count: ");
                indentingPrintWriter.println(this.mSendFinishCount);
                indentingPrintWriter.print("Listener send count: ");
                indentingPrintWriter.println(this.mListenerCount);
                indentingPrintWriter.print("Listener finish count: ");
                indentingPrintWriter.println(this.mListenerFinishCount);
                indentingPrintWriter.println();
                if (this.mInFlight.size() > 0) {
                    indentingPrintWriter.println("Outstanding deliveries:");
                    indentingPrintWriter.increaseIndent();
                    for (int i8 = 0; i8 < this.mInFlight.size(); i8++) {
                        indentingPrintWriter.print("#");
                        indentingPrintWriter.print(i8);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mInFlight.get(i8));
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Allow while idle history:");
                this.mAllowWhileIdleHistory.dump(indentingPrintWriter, elapsedRealtimeMillis);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Allow while idle compat history:");
                this.mAllowWhileIdleCompatHistory.dump(indentingPrintWriter, elapsedRealtimeMillis);
                indentingPrintWriter.println();
                if (this.mLastPriorityAlarmDispatch.size() > 0) {
                    indentingPrintWriter.println("Last priority alarm dispatches:");
                    indentingPrintWriter.increaseIndent();
                    for (int i9 = 0; i9 < this.mLastPriorityAlarmDispatch.size(); i9++) {
                        indentingPrintWriter.print("UID: ");
                        android.os.UserHandle.formatUid(indentingPrintWriter, this.mLastPriorityAlarmDispatch.keyAt(i9));
                        indentingPrintWriter.print(": ");
                        android.util.TimeUtils.formatDuration(this.mLastPriorityAlarmDispatch.valueAt(i9), elapsedRealtimeMillis, indentingPrintWriter);
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mRemovalHistory.size() > 0) {
                    indentingPrintWriter.println("Removal history:");
                    indentingPrintWriter.increaseIndent();
                    for (int i10 = 0; i10 < this.mRemovalHistory.size(); i10++) {
                        android.os.UserHandle.formatUid(indentingPrintWriter, this.mRemovalHistory.keyAt(i10));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        com.android.server.alarm.AlarmManagerService.RemovedAlarm[] removedAlarmArr = (com.android.server.alarm.AlarmManagerService.RemovedAlarm[]) this.mRemovalHistory.valueAt(i10).toArray();
                        for (int length = removedAlarmArr.length - 1; length >= 0; length += -1) {
                            indentingPrintWriter.print("#" + (removedAlarmArr.length - length) + ": ");
                            removedAlarmArr[length].dump(indentingPrintWriter, elapsedRealtimeMillis, simpleDateFormat);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mLog.dump(indentingPrintWriter, "Recent problems:")) {
                    indentingPrintWriter.println();
                }
                com.android.server.alarm.AlarmManagerService.FilterStats[] filterStatsArr = new com.android.server.alarm.AlarmManagerService.FilterStats[10];
                java.util.Comparator<com.android.server.alarm.AlarmManagerService.FilterStats> comparator = new java.util.Comparator<com.android.server.alarm.AlarmManagerService.FilterStats>() { // from class: com.android.server.alarm.AlarmManagerService.5
                    @Override // java.util.Comparator
                    public int compare(com.android.server.alarm.AlarmManagerService.FilterStats filterStats, com.android.server.alarm.AlarmManagerService.FilterStats filterStats2) {
                        if (filterStats.aggregateTime < filterStats2.aggregateTime) {
                            return 1;
                        }
                        if (filterStats.aggregateTime > filterStats2.aggregateTime) {
                            return -1;
                        }
                        return 0;
                    }
                };
                int i11 = 0;
                int i12 = 0;
                while (i11 < this.mBroadcastStats.size()) {
                    android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> valueAt2 = this.mBroadcastStats.valueAt(i11);
                    int i13 = i2;
                    while (i13 < valueAt2.size()) {
                        com.android.server.alarm.AlarmManagerService.BroadcastStats valueAt3 = valueAt2.valueAt(i13);
                        int i14 = i2;
                        while (i14 < valueAt3.filterStats.size()) {
                            com.android.server.alarm.AlarmManagerService.FilterStats valueAt4 = valueAt3.filterStats.valueAt(i14);
                            if (i12 > 0) {
                                i2 = java.util.Arrays.binarySearch(filterStatsArr, i2, i12, valueAt4, comparator);
                            }
                            if (i2 < 0) {
                                i2 = (-i2) - 1;
                            }
                            android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> arrayMap = valueAt2;
                            if (i2 < 10) {
                                int i15 = (10 - i2) - 1;
                                if (i15 <= 0) {
                                    broadcastStats = valueAt3;
                                } else {
                                    broadcastStats = valueAt3;
                                    java.lang.System.arraycopy(filterStatsArr, i2, filterStatsArr, i2 + 1, i15);
                                }
                                filterStatsArr[i2] = valueAt4;
                                if (i12 < 10) {
                                    i12++;
                                }
                            } else {
                                broadcastStats = valueAt3;
                            }
                            i14++;
                            valueAt2 = arrayMap;
                            valueAt3 = broadcastStats;
                            i2 = 0;
                        }
                        i13++;
                        i2 = 0;
                    }
                    i11++;
                    i2 = 0;
                }
                if (i12 > 0) {
                    indentingPrintWriter.println("Top Alarms:");
                    indentingPrintWriter.increaseIndent();
                    for (int i16 = 0; i16 < i12; i16++) {
                        com.android.server.alarm.AlarmManagerService.FilterStats filterStats = filterStatsArr[i16];
                        if (filterStats.nesting > 0) {
                            indentingPrintWriter.print("*ACTIVE* ");
                        }
                        android.util.TimeUtils.formatDuration(filterStats.aggregateTime, indentingPrintWriter);
                        indentingPrintWriter.print(" running, ");
                        indentingPrintWriter.print(filterStats.numWakeup);
                        indentingPrintWriter.print(" wakeups, ");
                        indentingPrintWriter.print(filterStats.count);
                        indentingPrintWriter.print(" alarms: ");
                        android.os.UserHandle.formatUid(indentingPrintWriter, filterStats.mBroadcastStats.mUid);
                        indentingPrintWriter.print(":");
                        indentingPrintWriter.print(filterStats.mBroadcastStats.mPackageName);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print(filterStats.mTag);
                        indentingPrintWriter.println();
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Alarm Stats:");
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i17 = 0; i17 < this.mBroadcastStats.size(); i17++) {
                    android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> valueAt5 = this.mBroadcastStats.valueAt(i17);
                    for (int i18 = 0; i18 < valueAt5.size(); i18++) {
                        com.android.server.alarm.AlarmManagerService.BroadcastStats valueAt6 = valueAt5.valueAt(i18);
                        if (valueAt6.nesting > 0) {
                            indentingPrintWriter.print("*ACTIVE* ");
                        }
                        android.os.UserHandle.formatUid(indentingPrintWriter, valueAt6.mUid);
                        indentingPrintWriter.print(":");
                        indentingPrintWriter.print(valueAt6.mPackageName);
                        indentingPrintWriter.print(" ");
                        android.util.TimeUtils.formatDuration(valueAt6.aggregateTime, indentingPrintWriter);
                        indentingPrintWriter.print(" running, ");
                        indentingPrintWriter.print(valueAt6.numWakeup);
                        indentingPrintWriter.println(" wakeups:");
                        arrayList.clear();
                        for (int i19 = 0; i19 < valueAt6.filterStats.size(); i19++) {
                            arrayList.add(valueAt6.filterStats.valueAt(i19));
                        }
                        java.util.Collections.sort(arrayList, comparator);
                        indentingPrintWriter.increaseIndent();
                        for (int i20 = 0; i20 < arrayList.size(); i20++) {
                            com.android.server.alarm.AlarmManagerService.FilterStats filterStats2 = (com.android.server.alarm.AlarmManagerService.FilterStats) arrayList.get(i20);
                            if (filterStats2.nesting > 0) {
                                indentingPrintWriter.print("*ACTIVE* ");
                            }
                            android.util.TimeUtils.formatDuration(filterStats2.aggregateTime, indentingPrintWriter);
                            indentingPrintWriter.print(" ");
                            indentingPrintWriter.print(filterStats2.numWakeup);
                            indentingPrintWriter.print(" wakes ");
                            indentingPrintWriter.print(filterStats2.count);
                            indentingPrintWriter.print(" alarms, last ");
                            android.util.TimeUtils.formatDuration(filterStats2.lastTime, elapsedRealtimeMillis, indentingPrintWriter);
                            indentingPrintWriter.println(":");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.print(filterStats2.mTag);
                            indentingPrintWriter.println();
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.println();
                this.mStatLogger.dump(indentingPrintWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpImpl$15(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, android.util.ArrayMap arrayMap) {
        int size = arrayMap.size();
        if (size > 0) {
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(":");
            indentingPrintWriter.print(str);
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < size; i2++) {
                indentingPrintWriter.print(com.android.server.alarm.TareBill.getName((com.android.server.tare.EconomyManagerInternal.ActionBill) arrayMap.keyAt(i2)));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(arrayMap.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    void dumpProto(java.io.FileDescriptor fileDescriptor) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            try {
                long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
                long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
                protoOutputStream.write(1112396529665L, currentTimeMillis);
                protoOutputStream.write(1112396529666L, elapsedRealtimeMillis);
                protoOutputStream.write(1112396529667L, this.mLastTimeChangeClockTime);
                protoOutputStream.write(1112396529668L, this.mLastTimeChangeRealtime);
                this.mConstants.dumpProto(protoOutputStream, 1146756268037L);
                if (this.mAppStateTracker != null) {
                    this.mAppStateTracker.dumpProto(protoOutputStream, 1146756268038L);
                }
                protoOutputStream.write(1133871366151L, this.mInteractive);
                if (!this.mInteractive) {
                    protoOutputStream.write(1112396529672L, elapsedRealtimeMillis - this.mNonInteractiveStartTime);
                    protoOutputStream.write(1112396529673L, currentNonWakeupFuzzLocked(elapsedRealtimeMillis));
                    protoOutputStream.write(1112396529674L, elapsedRealtimeMillis - this.mLastAlarmDeliveryTime);
                    protoOutputStream.write(1112396529675L, elapsedRealtimeMillis - this.mNextNonWakeupDeliveryTime);
                }
                protoOutputStream.write(1112396529676L, this.mNextNonWakeup - elapsedRealtimeMillis);
                protoOutputStream.write(1112396529677L, this.mNextWakeup - elapsedRealtimeMillis);
                protoOutputStream.write(1112396529678L, elapsedRealtimeMillis - this.mLastWakeup);
                protoOutputStream.write(1112396529679L, elapsedRealtimeMillis - this.mNextWakeUpSetAt);
                protoOutputStream.write(1112396529680L, this.mNumTimeChanged);
                java.util.TreeSet treeSet = new java.util.TreeSet();
                int size = this.mNextAlarmClockForUser.size();
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    treeSet.add(java.lang.Integer.valueOf(this.mNextAlarmClockForUser.keyAt(i2)));
                }
                int size2 = this.mPendingSendNextAlarmClockChangedForUser.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    treeSet.add(java.lang.Integer.valueOf(this.mPendingSendNextAlarmClockChangedForUser.keyAt(i3)));
                }
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    android.app.AlarmManager.AlarmClockInfo alarmClockInfo = this.mNextAlarmClockForUser.get(intValue);
                    long triggerTime = alarmClockInfo != null ? alarmClockInfo.getTriggerTime() : 0L;
                    boolean z = this.mPendingSendNextAlarmClockChangedForUser.get(intValue);
                    long start = protoOutputStream.start(2246267895826L);
                    protoOutputStream.write(1120986464257L, intValue);
                    protoOutputStream.write(1133871366146L, z);
                    protoOutputStream.write(1112396529667L, triggerTime);
                    protoOutputStream.end(start);
                }
                this.mAlarmStore.dumpProto(protoOutputStream, elapsedRealtimeMillis);
                for (int i4 = 0; i4 < this.mPendingBackgroundAlarms.size(); i4++) {
                    java.util.ArrayList<com.android.server.alarm.Alarm> valueAt = this.mPendingBackgroundAlarms.valueAt(i4);
                    if (valueAt != null) {
                        java.util.Iterator<com.android.server.alarm.Alarm> it2 = valueAt.iterator();
                        while (it2.hasNext()) {
                            it2.next().dumpDebug(protoOutputStream, 2246267895828L, elapsedRealtimeMillis);
                        }
                    }
                }
                if (this.mPendingIdleUntil != null) {
                    this.mPendingIdleUntil.dumpDebug(protoOutputStream, 1146756268053L, elapsedRealtimeMillis);
                }
                if (this.mNextWakeFromIdle != null) {
                    this.mNextWakeFromIdle.dumpDebug(protoOutputStream, 1146756268055L, elapsedRealtimeMillis);
                }
                java.util.Iterator<com.android.server.alarm.Alarm> it3 = this.mPendingNonWakeupAlarms.iterator();
                while (it3.hasNext()) {
                    it3.next().dumpDebug(protoOutputStream, 2246267895832L, elapsedRealtimeMillis);
                }
                protoOutputStream.write(1120986464281L, this.mNumDelayedAlarms);
                protoOutputStream.write(1112396529690L, this.mTotalDelayTime);
                protoOutputStream.write(1112396529691L, this.mMaxDelayTime);
                protoOutputStream.write(1112396529692L, this.mNonInteractiveTime);
                protoOutputStream.write(1120986464285L, this.mBroadcastRefCount);
                protoOutputStream.write(1120986464286L, this.mSendCount);
                protoOutputStream.write(1120986464287L, this.mSendFinishCount);
                protoOutputStream.write(1120986464288L, this.mListenerCount);
                protoOutputStream.write(1120986464289L, this.mListenerFinishCount);
                java.util.Iterator<com.android.server.alarm.AlarmManagerService.InFlight> it4 = this.mInFlight.iterator();
                while (it4.hasNext()) {
                    it4.next().dumpDebug(protoOutputStream, 2246267895842L);
                }
                this.mLog.dumpDebug(protoOutputStream, 1146756268069L);
                com.android.server.alarm.AlarmManagerService.FilterStats[] filterStatsArr = new com.android.server.alarm.AlarmManagerService.FilterStats[10];
                java.util.Comparator<com.android.server.alarm.AlarmManagerService.FilterStats> comparator = new java.util.Comparator<com.android.server.alarm.AlarmManagerService.FilterStats>() { // from class: com.android.server.alarm.AlarmManagerService.6
                    @Override // java.util.Comparator
                    public int compare(com.android.server.alarm.AlarmManagerService.FilterStats filterStats, com.android.server.alarm.AlarmManagerService.FilterStats filterStats2) {
                        if (filterStats.aggregateTime < filterStats2.aggregateTime) {
                            return 1;
                        }
                        if (filterStats.aggregateTime > filterStats2.aggregateTime) {
                            return -1;
                        }
                        return 0;
                    }
                };
                int i5 = 0;
                int i6 = 0;
                while (i5 < this.mBroadcastStats.size()) {
                    android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> valueAt2 = this.mBroadcastStats.valueAt(i5);
                    int i7 = i;
                    while (i7 < valueAt2.size()) {
                        com.android.server.alarm.AlarmManagerService.BroadcastStats valueAt3 = valueAt2.valueAt(i7);
                        int i8 = i;
                        while (i8 < valueAt3.filterStats.size()) {
                            com.android.server.alarm.AlarmManagerService.FilterStats valueAt4 = valueAt3.filterStats.valueAt(i8);
                            if (i6 > 0) {
                                i = java.util.Arrays.binarySearch(filterStatsArr, i, i6, valueAt4, comparator);
                            }
                            if (i < 0) {
                                i = (-i) - 1;
                            }
                            if (i < 10) {
                                int i9 = (10 - i) - 1;
                                if (i9 > 0) {
                                    java.lang.System.arraycopy(filterStatsArr, i, filterStatsArr, i + 1, i9);
                                }
                                filterStatsArr[i] = valueAt4;
                                if (i6 < 10) {
                                    i6++;
                                }
                            }
                            i8++;
                            i = 0;
                        }
                        i7++;
                        i = 0;
                    }
                    i5++;
                    i = 0;
                }
                for (int i10 = 0; i10 < i6; i10++) {
                    long start2 = protoOutputStream.start(2246267895846L);
                    com.android.server.alarm.AlarmManagerService.FilterStats filterStats = filterStatsArr[i10];
                    protoOutputStream.write(1120986464257L, filterStats.mBroadcastStats.mUid);
                    protoOutputStream.write(1138166333442L, filterStats.mBroadcastStats.mPackageName);
                    filterStats.dumpDebug(protoOutputStream, 1146756268035L);
                    protoOutputStream.end(start2);
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i11 = 0; i11 < this.mBroadcastStats.size(); i11++) {
                    android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> valueAt5 = this.mBroadcastStats.valueAt(i11);
                    for (int i12 = 0; i12 < valueAt5.size(); i12++) {
                        long start3 = protoOutputStream.start(2246267895847L);
                        com.android.server.alarm.AlarmManagerService.BroadcastStats valueAt6 = valueAt5.valueAt(i12);
                        valueAt6.dumpDebug(protoOutputStream, 1146756268033L);
                        arrayList.clear();
                        for (int i13 = 0; i13 < valueAt6.filterStats.size(); i13++) {
                            arrayList.add(valueAt6.filterStats.valueAt(i13));
                        }
                        java.util.Collections.sort(arrayList, comparator);
                        java.util.Iterator it5 = arrayList.iterator();
                        while (it5.hasNext()) {
                            ((com.android.server.alarm.AlarmManagerService.FilterStats) it5.next()).dumpDebug(protoOutputStream, 2246267895810L);
                        }
                        protoOutputStream.end(start3);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        protoOutputStream.flush();
    }

    long getNextWakeFromIdleTimeImpl() {
        long whenElapsed;
        synchronized (this.mLock) {
            try {
                whenElapsed = this.mNextWakeFromIdle != null ? this.mNextWakeFromIdle.getWhenElapsed() : com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return whenElapsed;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isIdlingImpl() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPendingIdleUntil != null;
        }
        return z;
    }

    android.app.AlarmManager.AlarmClockInfo getNextAlarmClockImpl(int i) {
        android.app.AlarmManager.AlarmClockInfo alarmClockInfo;
        synchronized (this.mLock) {
            alarmClockInfo = this.mNextAlarmClockForUser.get(i);
        }
        return alarmClockInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNextAlarmClockLocked() {
        if (!this.mNextAlarmClockMayChange) {
            return;
        }
        this.mNextAlarmClockMayChange = false;
        android.util.SparseArray<android.app.AlarmManager.AlarmClockInfo> sparseArray = this.mTmpSparseAlarmClockArray;
        sparseArray.clear();
        java.util.Iterator<com.android.server.alarm.Alarm> it = this.mAlarmStore.asList().iterator();
        while (it.hasNext()) {
            com.android.server.alarm.Alarm next = it.next();
            if (next.alarmClock != null) {
                int userId = android.os.UserHandle.getUserId(next.uid);
                android.app.AlarmManager.AlarmClockInfo alarmClockInfo = this.mNextAlarmClockForUser.get(userId);
                if (sparseArray.get(userId) == null) {
                    sparseArray.put(userId, next.alarmClock);
                } else if (next.alarmClock.equals(alarmClockInfo) && alarmClockInfo.getTriggerTime() <= sparseArray.get(userId).getTriggerTime()) {
                    sparseArray.put(userId, alarmClockInfo);
                }
            }
        }
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            android.app.AlarmManager.AlarmClockInfo valueAt = sparseArray.valueAt(i);
            int keyAt = sparseArray.keyAt(i);
            if (!valueAt.equals(this.mNextAlarmClockForUser.get(keyAt))) {
                updateNextAlarmInfoForUserLocked(keyAt, valueAt);
            }
        }
        for (int size2 = this.mNextAlarmClockForUser.size() - 1; size2 >= 0; size2--) {
            int keyAt2 = this.mNextAlarmClockForUser.keyAt(size2);
            if (sparseArray.get(keyAt2) == null) {
                updateNextAlarmInfoForUserLocked(keyAt2, null);
            }
        }
    }

    private void updateNextAlarmInfoForUserLocked(int i, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) {
        if (alarmClockInfo != null) {
            this.mNextAlarmClockForUser.put(i, alarmClockInfo);
        } else {
            this.mNextAlarmClockForUser.remove(i);
        }
        this.mPendingSendNextAlarmClockChangedForUser.put(i, true);
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendNextAlarmClockChanged() {
        int i;
        android.util.SparseArray<android.app.AlarmManager.AlarmClockInfo> sparseArray = this.mHandlerSparseAlarmClockArray;
        sparseArray.clear();
        synchronized (this.mLock) {
            try {
                int size = this.mPendingSendNextAlarmClockChangedForUser.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mPendingSendNextAlarmClockChangedForUser.keyAt(i2);
                    sparseArray.append(keyAt, this.mNextAlarmClockForUser.get(keyAt));
                }
                this.mPendingSendNextAlarmClockChangedForUser.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size2 = sparseArray.size();
        for (i = 0; i < size2; i++) {
            int keyAt2 = sparseArray.keyAt(i);
            android.provider.Settings.System.putStringForUser(getContext().getContentResolver(), "next_alarm_formatted", formatNextAlarm(getContext(), sparseArray.valueAt(i), keyAt2), keyAt2);
            getContext().sendBroadcastAsUser(NEXT_ALARM_CLOCK_CHANGED_INTENT, new android.os.UserHandle(keyAt2));
        }
    }

    private static java.lang.String formatNextAlarm(android.content.Context context, android.app.AlarmManager.AlarmClockInfo alarmClockInfo, int i) {
        return alarmClockInfo == null ? "" : android.text.format.DateFormat.format(android.text.format.DateFormat.getBestDateTimePattern(java.util.Locale.getDefault(), android.text.format.DateFormat.is24HourFormat(context, i) ? "EHm" : "Ehma"), alarmClockInfo.getTriggerTime()).toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002b, code lost:
    
        if (r7 != r5) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void rescheduleKernelAlarmsLocked() {
        long j;
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        if (this.mAlarmStore.size() > 0) {
            long nextWakeupDeliveryTime = this.mAlarmStore.getNextWakeupDeliveryTime();
            j = this.mAlarmStore.getNextDeliveryTime();
            if (nextWakeupDeliveryTime != 0) {
                this.mNextWakeup = nextWakeupDeliveryTime;
                this.mNextWakeUpSetAt = elapsedRealtimeMillis;
                setLocked(2, nextWakeupDeliveryTime);
            }
        }
        j = 0;
        if (this.mPendingNonWakeupAlarms.size() > 0 && (j == 0 || this.mNextNonWakeupDeliveryTime < j)) {
            j = this.mNextNonWakeupDeliveryTime;
        }
        if (j != 0) {
            this.mNextNonWakeup = j;
            this.mNextNonWakeUpSetAt = elapsedRealtimeMillis;
            setLocked(3, j);
        }
    }

    void removeExactAlarmsOnPermissionRevoked(final int i, final java.lang.String str, boolean z) {
        if (isExemptFromExactAlarmPermissionNoLock(i) || !isExactAlarmChangeEnabled(str, android.os.UserHandle.getUserId(i))) {
            return;
        }
        android.util.Slog.w(TAG, "Package " + str + ", uid " + i + " lost permission to set exact alarms!");
        java.util.function.Predicate<com.android.server.alarm.Alarm> predicate = new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeExactAlarmsOnPermissionRevoked$16;
                lambda$removeExactAlarmsOnPermissionRevoked$16 = com.android.server.alarm.AlarmManagerService.lambda$removeExactAlarmsOnPermissionRevoked$16(i, str, (com.android.server.alarm.Alarm) obj);
                return lambda$removeExactAlarmsOnPermissionRevoked$16;
            }
        };
        synchronized (this.mLock) {
            removeAlarmsInternalLocked(predicate, 2);
        }
        if (z) {
            com.android.server.pm.permission.PermissionManagerService.killUid(android.os.UserHandle.getAppId(i), android.os.UserHandle.getUserId(i), "schedule_exact_alarm revoked");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeExactAlarmsOnPermissionRevoked$16(int i, java.lang.String str, com.android.server.alarm.Alarm alarm) {
        return alarm.uid == i && alarm.packageName.equals(str) && alarm.windowLength == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeAlarmsInternalLocked(java.util.function.Predicate<com.android.server.alarm.Alarm> predicate, int i) {
        boolean z;
        long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
        long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
        java.util.ArrayList<com.android.server.alarm.Alarm> remove = this.mAlarmStore.remove(predicate);
        int i2 = 1;
        boolean z2 = !remove.isEmpty();
        for (int size = this.mPendingBackgroundAlarms.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.alarm.Alarm> valueAt = this.mPendingBackgroundAlarms.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                if (predicate.test(valueAt.get(size2))) {
                    remove.add(valueAt.remove(size2));
                }
            }
            if (valueAt.size() == 0) {
                this.mPendingBackgroundAlarms.removeAt(size);
            }
        }
        for (int size3 = this.mPendingNonWakeupAlarms.size() - 1; size3 >= 0; size3--) {
            if (predicate.test(this.mPendingNonWakeupAlarms.get(size3))) {
                remove.add(this.mPendingNonWakeupAlarms.remove(size3));
            }
        }
        java.util.Iterator<com.android.server.alarm.Alarm> it = remove.iterator();
        while (it.hasNext()) {
            com.android.server.alarm.Alarm next = it.next();
            decrementAlarmCount(next.uid, i2);
            if (next.listener != null) {
                next.listener.asBinder().unlinkToDeath(this.mListenerDeathRecipient, 0);
            }
            if (com.android.server.alarm.AlarmManagerService.RemovedAlarm.isLoggable(i)) {
                com.android.internal.util.RingBuffer<com.android.server.alarm.AlarmManagerService.RemovedAlarm> ringBuffer = this.mRemovalHistory.get(next.uid);
                if (ringBuffer == null) {
                    ringBuffer = new com.android.internal.util.RingBuffer<>(com.android.server.alarm.AlarmManagerService.RemovedAlarm.class, 10);
                    this.mRemovalHistory.put(next.uid, ringBuffer);
                }
                ringBuffer.append(new com.android.server.alarm.AlarmManagerService.RemovedAlarm(next, i, currentTimeMillis, elapsedRealtimeMillis));
                maybeUnregisterTareListenerLocked(next);
                it = it;
                currentTimeMillis = currentTimeMillis;
                i2 = 1;
            }
        }
        if (z2) {
            if (this.mPendingIdleUntil != null && predicate.test(this.mPendingIdleUntil)) {
                this.mPendingIdleUntil = null;
                z = true;
            } else {
                z = false;
            }
            if (this.mNextWakeFromIdle != null && predicate.test(this.mNextWakeFromIdle)) {
                this.mNextWakeFromIdle = this.mAlarmStore.getNextWakeFromIdleAlarm();
                if (this.mPendingIdleUntil != null) {
                    z |= this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda22
                        @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                        public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                            boolean lambda$removeAlarmsInternalLocked$17;
                            lambda$removeAlarmsInternalLocked$17 = com.android.server.alarm.AlarmManagerService.this.lambda$removeAlarmsInternalLocked$17(alarm);
                            return lambda$removeAlarmsInternalLocked$17;
                        }
                    });
                }
            }
            if (z) {
                this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda23
                    @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                    public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                        boolean lambda$removeAlarmsInternalLocked$18;
                        lambda$removeAlarmsInternalLocked$18 = com.android.server.alarm.AlarmManagerService.this.lambda$removeAlarmsInternalLocked$18(alarm);
                        return lambda$removeAlarmsInternalLocked$18;
                    }
                });
            }
            rescheduleKernelAlarmsLocked();
            updateNextAlarmClockLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$removeAlarmsInternalLocked$17(com.android.server.alarm.Alarm alarm) {
        return alarm == this.mPendingIdleUntil && adjustIdleUntilTime(alarm);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeLocked(final android.app.PendingIntent pendingIntent, final android.app.IAlarmListener iAlarmListener, int i) {
        if (pendingIntent == null && iAlarmListener == null) {
            return;
        }
        removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeLocked$19;
                lambda$removeLocked$19 = com.android.server.alarm.AlarmManagerService.lambda$removeLocked$19(pendingIntent, iAlarmListener, (com.android.server.alarm.Alarm) obj);
                return lambda$removeLocked$19;
            }
        }, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeLocked$19(android.app.PendingIntent pendingIntent, android.app.IAlarmListener iAlarmListener, com.android.server.alarm.Alarm alarm) {
        return alarm.matches(pendingIntent, iAlarmListener);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeLocked(final int i, int i2) {
        if (i == 1000) {
            return;
        }
        removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeLocked$20;
                lambda$removeLocked$20 = com.android.server.alarm.AlarmManagerService.lambda$removeLocked$20(i, (com.android.server.alarm.Alarm) obj);
                return lambda$removeLocked$20;
            }
        }, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeLocked$20(int i, com.android.server.alarm.Alarm alarm) {
        return alarm.uid == i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeLocked(final java.lang.String str, int i) {
        if (str == null) {
            return;
        }
        removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeLocked$21;
                lambda$removeLocked$21 = com.android.server.alarm.AlarmManagerService.lambda$removeLocked$21(str, (com.android.server.alarm.Alarm) obj);
                return lambda$removeLocked$21;
            }
        }, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeLocked$21(java.lang.String str, com.android.server.alarm.Alarm alarm) {
        return alarm.matches(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeForStoppedLocked(final int i) {
        if (i == 1000) {
            return;
        }
        removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeForStoppedLocked$22;
                lambda$removeForStoppedLocked$22 = com.android.server.alarm.AlarmManagerService.this.lambda$removeForStoppedLocked$22(i, (com.android.server.alarm.Alarm) obj);
                return lambda$removeForStoppedLocked$22;
            }
        }, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$removeForStoppedLocked$22(int i, com.android.server.alarm.Alarm alarm) {
        return alarm.uid == i && this.mActivityManagerInternal.isAppStartModeDisabled(i, alarm.packageName);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void removeUserLocked(final int i) {
        if (i == 0) {
            android.util.Slog.w(TAG, "Ignoring attempt to remove system-user state!");
            return;
        }
        removeAlarmsInternalLocked(new java.util.function.Predicate() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeUserLocked$23;
                lambda$removeUserLocked$23 = com.android.server.alarm.AlarmManagerService.lambda$removeUserLocked$23(i, (com.android.server.alarm.Alarm) obj);
                return lambda$removeUserLocked$23;
            }
        }, 0);
        for (int size = this.mLastPriorityAlarmDispatch.size() - 1; size >= 0; size--) {
            if (android.os.UserHandle.getUserId(this.mLastPriorityAlarmDispatch.keyAt(size)) == i) {
                this.mLastPriorityAlarmDispatch.removeAt(size);
            }
        }
        for (int size2 = this.mRemovalHistory.size() - 1; size2 >= 0; size2--) {
            if (android.os.UserHandle.getUserId(this.mRemovalHistory.keyAt(size2)) == i) {
                this.mRemovalHistory.removeAt(size2);
            }
        }
        for (int size3 = this.mLastOpScheduleExactAlarm.size() - 1; size3 >= 0; size3--) {
            if (android.os.UserHandle.getUserId(this.mLastOpScheduleExactAlarm.keyAt(size3)) == i) {
                this.mLastOpScheduleExactAlarm.removeAt(size3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeUserLocked$23(int i, com.android.server.alarm.Alarm alarm) {
        return android.os.UserHandle.getUserId(alarm.uid) == i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void interactiveStateChangedLocked(boolean z) {
        if (this.mInteractive != z) {
            this.mInteractive = z;
            long elapsedRealtimeMillis = this.mInjector.getElapsedRealtimeMillis();
            if (z) {
                if (this.mPendingNonWakeupAlarms.size() > 0) {
                    long j = elapsedRealtimeMillis - this.mStartCurrentDelayTime;
                    this.mTotalDelayTime += j;
                    if (this.mMaxDelayTime < j) {
                        this.mMaxDelayTime = j;
                    }
                    deliverAlarmsLocked(new java.util.ArrayList<>(this.mPendingNonWakeupAlarms), elapsedRealtimeMillis);
                    this.mPendingNonWakeupAlarms.clear();
                }
                if (this.mNonInteractiveStartTime > 0) {
                    long j2 = elapsedRealtimeMillis - this.mNonInteractiveStartTime;
                    if (j2 > this.mNonInteractiveTime) {
                        this.mNonInteractiveTime = j2;
                    }
                }
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.alarm.AlarmManagerService.this.lambda$interactiveStateChangedLocked$24();
                    }
                });
                return;
            }
            this.mNonInteractiveStartTime = elapsedRealtimeMillis;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$interactiveStateChangedLocked$24() {
        getContext().sendBroadcastAsUser(this.mTimeTickIntent, android.os.UserHandle.ALL, null, this.mTimeTickOptions);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean lookForPackageLocked(java.lang.String str, int i) {
        java.util.Iterator<com.android.server.alarm.Alarm> it = this.mAlarmStore.asList().iterator();
        while (it.hasNext()) {
            com.android.server.alarm.Alarm next = it.next();
            if (next.matches(str) && next.creatorUid == i) {
                return true;
            }
        }
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = this.mPendingBackgroundAlarms.get(i);
        if (arrayList != null) {
            java.util.Iterator<com.android.server.alarm.Alarm> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                if (it2.next().matches(str)) {
                    return true;
                }
            }
        }
        java.util.Iterator<com.android.server.alarm.Alarm> it3 = this.mPendingNonWakeupAlarms.iterator();
        while (it3.hasNext()) {
            com.android.server.alarm.Alarm next2 = it3.next();
            if (next2.matches(str) && next2.creatorUid == i) {
                return true;
            }
        }
        return false;
    }

    private void setLocked(int i, long j) {
        if (this.mInjector.isAlarmDriverPresent()) {
            this.mInjector.setAlarm(i, j);
            return;
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 1;
        this.mHandler.removeMessages(obtain.what);
        this.mHandler.sendMessageAtTime(obtain, j);
    }

    static final void dumpAlarmList(android.util.IndentingPrintWriter indentingPrintWriter, java.util.ArrayList<com.android.server.alarm.Alarm> arrayList, long j, java.text.SimpleDateFormat simpleDateFormat) {
        int size = arrayList.size();
        for (int i = size - 1; i >= 0; i--) {
            com.android.server.alarm.Alarm alarm = arrayList.get(i);
            indentingPrintWriter.print(com.android.server.alarm.Alarm.typeToString(alarm.type));
            indentingPrintWriter.print(" #");
            indentingPrintWriter.print(size - i);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(alarm);
            indentingPrintWriter.increaseIndent();
            alarm.dump(indentingPrintWriter, j, simpleDateFormat);
            indentingPrintWriter.decreaseIndent();
        }
    }

    private static boolean isExemptFromBatterySaver(com.android.server.alarm.Alarm alarm) {
        if (alarm.alarmClock != null) {
            return true;
        }
        return (alarm.operation != null && (alarm.operation.isActivity() || alarm.operation.isForegroundService())) || android.os.UserHandle.isCore(alarm.creatorUid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isBackgroundRestricted(com.android.server.alarm.Alarm alarm) {
        if (alarm.alarmClock != null) {
            return false;
        }
        if (alarm.operation != null && alarm.operation.isActivity()) {
            return false;
        }
        java.lang.String str = alarm.sourcePackage;
        int i = alarm.creatorUid;
        return (android.os.UserHandle.isCore(i) || this.mAppStateTracker == null || !this.mAppStateTracker.areAlarmsRestricted(i, str)) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int triggerAlarmsLocked(java.util.ArrayList<com.android.server.alarm.Alarm> arrayList, long j) {
        com.android.server.alarm.Alarm alarm;
        final com.android.server.alarm.AlarmManagerService alarmManagerService = this;
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList2 = arrayList;
        long j2 = j;
        java.util.Iterator<com.android.server.alarm.Alarm> it = alarmManagerService.mAlarmStore.removePendingAlarms(j2).iterator();
        int i = 0;
        while (it.hasNext()) {
            com.android.server.alarm.Alarm next = it.next();
            if (alarmManagerService.isBackgroundRestricted(next)) {
                java.util.ArrayList<com.android.server.alarm.Alarm> arrayList3 = alarmManagerService.mPendingBackgroundAlarms.get(next.creatorUid);
                if (arrayList3 == null) {
                    arrayList3 = new java.util.ArrayList<>();
                    alarmManagerService.mPendingBackgroundAlarms.put(next.creatorUid, arrayList3);
                }
                arrayList3.add(next);
            } else {
                next.count = 1;
                arrayList2.add(next);
                if ((next.flags & 2) != 0) {
                    com.android.server.EventLogTags.writeDeviceIdleWakeFromIdle(alarmManagerService.mPendingIdleUntil != null ? 1 : 0, next.statsTag);
                }
                if (alarmManagerService.mPendingIdleUntil == next) {
                    alarmManagerService.mPendingIdleUntil = null;
                    alarmManagerService.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda7
                        @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                        public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm2) {
                            boolean lambda$triggerAlarmsLocked$25;
                            lambda$triggerAlarmsLocked$25 = com.android.server.alarm.AlarmManagerService.this.lambda$triggerAlarmsLocked$25(alarm2);
                            return lambda$triggerAlarmsLocked$25;
                        }
                    });
                }
                if (alarmManagerService.mNextWakeFromIdle == next) {
                    alarmManagerService.mNextWakeFromIdle = alarmManagerService.mAlarmStore.getNextWakeFromIdleAlarm();
                }
                if (next.repeatInterval <= 0) {
                    alarm = next;
                } else {
                    next.count = (int) (next.count + ((j2 - next.getRequestedElapsed()) / next.repeatInterval));
                    long j3 = next.count * next.repeatInterval;
                    long requestedElapsed = next.getRequestedElapsed() + j3;
                    alarm = next;
                    setImplLocked(next.type, next.origWhen + j3, requestedElapsed, maxTriggerTime(j, requestedElapsed, next.repeatInterval) - requestedElapsed, next.repeatInterval, next.operation, null, null, next.flags, next.workSource, next.alarmClock, next.uid, next.packageName, null, -1);
                }
                com.android.server.alarm.Alarm alarm2 = alarm;
                if (alarm2.wakeup) {
                    i++;
                }
                if (alarm2.alarmClock == null) {
                    alarmManagerService = this;
                } else {
                    alarmManagerService = this;
                    alarmManagerService.mNextAlarmClockMayChange = true;
                }
                arrayList2 = arrayList;
                j2 = j;
            }
        }
        calculateDeliveryPriorities(arrayList);
        java.util.Collections.sort(arrayList, alarmManagerService.mAlarmDispatchComparator);
        return i;
    }

    long currentNonWakeupFuzzLocked(long j) {
        long j2 = j - this.mNonInteractiveStartTime;
        if (j2 < com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
            return 120000L;
        }
        if (j2 < 1800000) {
            return 900000L;
        }
        return 3600000L;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean checkAllowNonWakeupDelayLocked(long j) {
        if (this.mConstants.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF && !this.mInteractive && this.mLastAlarmDeliveryTime > 0) {
            return (this.mPendingNonWakeupAlarms.size() <= 0 || this.mNextNonWakeupDeliveryTime >= j) && j - this.mLastAlarmDeliveryTime <= currentNonWakeupFuzzLocked(j);
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void deliverAlarmsLocked(java.util.ArrayList<com.android.server.alarm.Alarm> arrayList, long j) {
        this.mLastAlarmDeliveryTime = j;
        for (int i = 0; i < arrayList.size(); i++) {
            com.android.server.alarm.Alarm alarm = arrayList.get(i);
            if (alarm.wakeup) {
                android.os.Trace.traceBegin(131072L, "Dispatch wakeup alarm to " + alarm.packageName);
            } else {
                android.os.Trace.traceBegin(131072L, "Dispatch non-wakeup alarm to " + alarm.packageName);
            }
            try {
                this.mActivityManagerInternal.noteAlarmStart(alarm.operation, alarm.workSource, alarm.uid, alarm.statsTag);
                this.mDeliveryTracker.deliverLocked(alarm, j);
                reportAlarmEventToTare(alarm);
                if (alarm.repeatInterval <= 0) {
                    maybeUnregisterTareListenerLocked(alarm);
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Failure sending alarm.", e);
            }
            android.os.Trace.traceEnd(131072L);
            decrementAlarmCount(alarm.uid, 1);
        }
    }

    private void reportAlarmEventToTare(com.android.server.alarm.Alarm alarm) {
        int i;
        if (this.mConstants.USE_TARE_POLICY == 0) {
            return;
        }
        boolean z = (alarm.flags & 12) != 0;
        if (alarm.alarmClock != null) {
            i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_CLOCK;
        } else if (alarm.wakeup) {
            if (alarm.windowLength == 0) {
                if (z) {
                    i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE;
                } else {
                    i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT;
                }
            } else if (z) {
                i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT_ALLOW_WHILE_IDLE;
            } else {
                i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_INEXACT;
            }
        } else if (alarm.windowLength == 0) {
            if (z) {
                i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT_ALLOW_WHILE_IDLE;
            } else {
                i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_EXACT;
            }
        } else if (z) {
            i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT_ALLOW_WHILE_IDLE;
        } else {
            i = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_NONWAKEUP_INEXACT;
        }
        this.mEconomyManagerInternal.noteInstantaneousEvent(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage, i, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isExemptFromAppStandby(com.android.server.alarm.Alarm alarm) {
        return (alarm.alarmClock == null && !android.os.UserHandle.isCore(alarm.creatorUid) && (alarm.flags & 12) == 0) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isExemptFromTare(com.android.server.alarm.Alarm alarm) {
        return (alarm.alarmClock == null && !android.os.UserHandle.isCore(alarm.creatorUid) && (alarm.flags & 8) == 0) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private android.content.Context mContext;
        private long mNativeData;

        Injector(android.content.Context context) {
            this.mContext = context;
        }

        void init() {
            java.lang.System.loadLibrary("alarm_jni");
            this.mNativeData = com.android.server.alarm.AlarmManagerService.init();
        }

        int waitForAlarm() {
            return com.android.server.alarm.AlarmManagerService.waitForAlarm(this.mNativeData);
        }

        boolean isAlarmDriverPresent() {
            return this.mNativeData != 0;
        }

        void setAlarm(int i, long j) {
            long j2;
            long j3 = 0;
            if (j < 0) {
                j2 = 0;
            } else {
                long j4 = j / 1000;
                j2 = 1000 * (j % 1000) * 1000;
                j3 = j4;
            }
            int i2 = com.android.server.alarm.AlarmManagerService.set(this.mNativeData, i, j3, j2);
            if (i2 != 0) {
                android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "Unable to set kernel alarm, now=" + android.os.SystemClock.elapsedRealtime() + " type=" + i + " @ (" + j3 + "," + j2 + "), ret = " + i2 + " = " + android.system.Os.strerror(i2));
            }
        }

        int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        long getNextAlarm(int i) {
            return com.android.server.alarm.AlarmManagerService.getNextAlarm(this.mNativeData, i);
        }

        void initializeTimeIfRequired() {
            com.android.server.SystemClockTime.initializeIfRequired();
        }

        void setCurrentTimeMillis(long j, int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.SystemClockTime.setTimeAndConfidence(j, i, str);
        }

        void close() {
            com.android.server.alarm.AlarmManagerService.close(this.mNativeData);
        }

        long getElapsedRealtimeMillis() {
            return android.os.SystemClock.elapsedRealtime();
        }

        long getCurrentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        android.os.PowerManager.WakeLock getAlarmWakeLock() {
            return ((android.os.PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, "*alarm*");
        }

        int getSystemUiUid(android.content.pm.PackageManagerInternal packageManagerInternal) {
            return packageManagerInternal.getPackageUid(packageManagerInternal.getSystemUiServiceComponent().getPackageName(), 1048576L, 0);
        }

        com.android.internal.app.IAppOpsService getAppOpsService() {
            return com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        }

        com.android.server.alarm.AlarmManagerService.ClockReceiver getClockReceiver(com.android.server.alarm.AlarmManagerService alarmManagerService) {
            java.util.Objects.requireNonNull(alarmManagerService);
            return alarmManagerService.new ClockReceiver();
        }

        void registerDeviceConfigListener(android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("alarm_manager", com.android.server.AppSchedulingModuleThread.getExecutor(), onPropertiesChangedListener);
        }
    }

    private class AlarmThread extends java.lang.Thread {
        private int mFalseWakeups;
        private int mWtfThreshold;

        AlarmThread() {
            super(com.android.server.alarm.AlarmManagerService.TAG);
            this.mFalseWakeups = 0;
            this.mWtfThreshold = 100;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long j;
            long j2;
            java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>();
            while (true) {
                int waitForAlarm = com.android.server.alarm.AlarmManagerService.this.mInjector.waitForAlarm();
                long currentTimeMillis = com.android.server.alarm.AlarmManagerService.this.mInjector.getCurrentTimeMillis();
                long elapsedRealtimeMillis = com.android.server.alarm.AlarmManagerService.this.mInjector.getElapsedRealtimeMillis();
                synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                    com.android.server.alarm.AlarmManagerService.this.mLastWakeup = elapsedRealtimeMillis;
                }
                if (waitForAlarm == 0) {
                    android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "waitForAlarm returned 0, nowRTC = " + currentTimeMillis + ", nowElapsed = " + elapsedRealtimeMillis);
                }
                arrayList.clear();
                if ((waitForAlarm & 65536) != 0) {
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        j = com.android.server.alarm.AlarmManagerService.this.mLastTimeChangeClockTime;
                        j2 = (elapsedRealtimeMillis - com.android.server.alarm.AlarmManagerService.this.mLastTimeChangeRealtime) + j;
                    }
                    if (j == 0 || currentTimeMillis < j2 - 1000 || currentTimeMillis > j2 + 1000) {
                        com.android.internal.util.FrameworkStatsLog.write(45, currentTimeMillis);
                        com.android.server.alarm.AlarmManagerService.this.removeImpl(null, com.android.server.alarm.AlarmManagerService.this.mTimeTickTrigger);
                        com.android.server.alarm.AlarmManagerService.this.removeImpl(com.android.server.alarm.AlarmManagerService.this.mDateChangeSender, null);
                        com.android.server.alarm.AlarmManagerService.this.reevaluateRtcAlarms();
                        com.android.server.alarm.AlarmManagerService.this.mClockReceiver.scheduleTimeTickEvent();
                        com.android.server.alarm.AlarmManagerService.this.mClockReceiver.scheduleDateChangedEvent();
                        synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                            com.android.server.alarm.AlarmManagerService.this.mNumTimeChanged++;
                            com.android.server.alarm.AlarmManagerService.this.mLastTimeChangeClockTime = currentTimeMillis;
                            com.android.server.alarm.AlarmManagerService.this.mLastTimeChangeRealtime = elapsedRealtimeMillis;
                        }
                        android.content.Intent intent = new android.content.Intent("android.intent.action.TIME_SET");
                        intent.addFlags(622854144);
                        com.android.server.alarm.AlarmManagerService.this.mOptsTimeBroadcast.setTemporaryAppAllowlist(com.android.server.alarm.AlarmManagerService.this.mActivityManagerInternal.getBootTimeTempAllowListDuration(), 0, 205, "");
                        com.android.server.alarm.AlarmManagerService.this.mOptsTimeBroadcast.setDeliveryGroupPolicy(1);
                        com.android.server.alarm.AlarmManagerService.this.getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, null, com.android.server.alarm.AlarmManagerService.this.mOptsTimeBroadcast.toBundle());
                        waitForAlarm |= 5;
                    }
                }
                if (waitForAlarm != 65536) {
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        try {
                            com.android.server.alarm.AlarmManagerService.this.mLastTrigger = elapsedRealtimeMillis;
                            int triggerAlarmsLocked = com.android.server.alarm.AlarmManagerService.this.triggerAlarmsLocked(arrayList, elapsedRealtimeMillis);
                            if (triggerAlarmsLocked == 0 && com.android.server.alarm.AlarmManagerService.this.checkAllowNonWakeupDelayLocked(elapsedRealtimeMillis)) {
                                if (com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms.size() == 0) {
                                    com.android.server.alarm.AlarmManagerService.this.mStartCurrentDelayTime = elapsedRealtimeMillis;
                                    com.android.server.alarm.AlarmManagerService.this.mNextNonWakeupDeliveryTime = elapsedRealtimeMillis + ((com.android.server.alarm.AlarmManagerService.this.currentNonWakeupFuzzLocked(elapsedRealtimeMillis) * 3) / 2);
                                }
                                com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms.addAll(arrayList);
                                com.android.server.alarm.AlarmManagerService.this.mNumDelayedAlarms += arrayList.size();
                                com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                            } else {
                                if (com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms.size() > 0) {
                                    com.android.server.alarm.AlarmManagerService.this.calculateDeliveryPriorities(com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms);
                                    arrayList.addAll(com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms);
                                    java.util.Collections.sort(arrayList, com.android.server.alarm.AlarmManagerService.this.mAlarmDispatchComparator);
                                    long j3 = elapsedRealtimeMillis - com.android.server.alarm.AlarmManagerService.this.mStartCurrentDelayTime;
                                    com.android.server.alarm.AlarmManagerService.this.mTotalDelayTime += j3;
                                    if (com.android.server.alarm.AlarmManagerService.this.mMaxDelayTime < j3) {
                                        com.android.server.alarm.AlarmManagerService.this.mMaxDelayTime = j3;
                                    }
                                    com.android.server.alarm.AlarmManagerService.this.mPendingNonWakeupAlarms.clear();
                                }
                                if (com.android.server.alarm.AlarmManagerService.this.mLastTimeChangeRealtime != elapsedRealtimeMillis && arrayList.isEmpty()) {
                                    int i = this.mFalseWakeups + 1;
                                    this.mFalseWakeups = i;
                                    if (i >= this.mWtfThreshold) {
                                        android.util.Slog.wtf(com.android.server.alarm.AlarmManagerService.TAG, "Too many (" + this.mFalseWakeups + ") false wakeups, nowElapsed=" + elapsedRealtimeMillis);
                                        if (this.mWtfThreshold < 100000) {
                                            this.mWtfThreshold *= 10;
                                        } else {
                                            this.mFalseWakeups = 0;
                                        }
                                    }
                                }
                                android.util.ArraySet<android.content.pm.UserPackage> arraySet = new android.util.ArraySet<>();
                                android.util.IntArray intArray = new android.util.IntArray();
                                android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                                android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray();
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    com.android.server.alarm.Alarm alarm = arrayList.get(i2);
                                    com.android.server.alarm.AlarmManagerService.increment(sparseIntArray, alarm.uid);
                                    if (alarm.wakeup) {
                                        intArray.add(alarm.uid);
                                        com.android.server.alarm.AlarmManagerService.increment(sparseIntArray2, alarm.uid);
                                    }
                                    if (com.android.server.alarm.AlarmManagerService.this.mConstants.USE_TARE_POLICY == 1) {
                                        if (!com.android.server.alarm.AlarmManagerService.isExemptFromTare(alarm)) {
                                            arraySet.add(android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage));
                                        }
                                    } else if (!com.android.server.alarm.AlarmManagerService.isExemptFromAppStandby(alarm)) {
                                        arraySet.add(android.content.pm.UserPackage.of(android.os.UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage));
                                    }
                                }
                                if (intArray.size() > 0 && com.android.server.alarm.AlarmManagerService.this.mBatteryStatsInternal != null) {
                                    com.android.server.alarm.AlarmManagerService.this.mBatteryStatsInternal.noteWakingAlarmBatch(elapsedRealtimeMillis, intArray.toArray());
                                }
                                com.android.server.alarm.AlarmManagerService.this.deliverAlarmsLocked(arrayList, elapsedRealtimeMillis);
                                com.android.server.alarm.AlarmManagerService.this.mTemporaryQuotaReserve.cleanUpExpiredQuotas(elapsedRealtimeMillis);
                                if (com.android.server.alarm.AlarmManagerService.this.mConstants.USE_TARE_POLICY == 1) {
                                    com.android.server.alarm.AlarmManagerService.this.reorderAlarmsBasedOnTare(arraySet);
                                } else {
                                    com.android.server.alarm.AlarmManagerService.this.reorderAlarmsBasedOnStandbyBuckets(arraySet);
                                }
                                com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                                com.android.server.alarm.AlarmManagerService.this.logAlarmBatchDelivered(arrayList.size(), triggerAlarmsLocked, sparseIntArray, sparseIntArray2);
                            }
                        } finally {
                        }
                    }
                } else {
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void increment(android.util.SparseIntArray sparseIntArray, int i) {
        int indexOfKey = sparseIntArray.indexOfKey(i);
        if (indexOfKey >= 0) {
            sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
        } else {
            sparseIntArray.put(i, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAlarmBatchDelivered(int i, int i2, android.util.SparseIntArray sparseIntArray, android.util.SparseIntArray sparseIntArray2) {
        int[] iArr = new int[sparseIntArray.size()];
        int[] iArr2 = new int[sparseIntArray.size()];
        int[] iArr3 = new int[sparseIntArray.size()];
        for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
            iArr[i3] = sparseIntArray.keyAt(i3);
            iArr2[i3] = sparseIntArray.valueAt(i3);
            iArr3[i3] = sparseIntArray2.get(iArr[i3], 0);
        }
        com.android.server.alarm.MetricsHelper.pushAlarmBatchDelivered(i, i2, iArr, iArr2, iArr3);
    }

    void setWakelockWorkSource(android.os.WorkSource workSource, int i, java.lang.String str, boolean z) {
        try {
            android.os.PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (!z) {
                str = null;
            }
            wakeLock.setHistoryTag(str);
        } catch (java.lang.Exception e) {
        }
        if (workSource != null) {
            this.mWakeLock.setWorkSource(workSource);
            return;
        }
        if (i >= 0) {
            this.mWakeLock.setWorkSource(new android.os.WorkSource(i));
            return;
        }
        this.mWakeLock.setWorkSource(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAlarmAttributionUid(com.android.server.alarm.Alarm alarm) {
        if (alarm.workSource != null && !alarm.workSource.isEmpty()) {
            return alarm.workSource.getAttributionUid();
        }
        return alarm.creatorUid;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean canAffordBillLocked(@android.annotation.NonNull com.android.server.alarm.Alarm alarm, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
        java.lang.String str = alarm.sourcePackage;
        android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mAffordabilityCache.get(userId, str);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap();
            this.mAffordabilityCache.add(userId, str, arrayMap);
        }
        if (arrayMap.containsKey(actionBill)) {
            return ((java.lang.Boolean) arrayMap.get(actionBill)).booleanValue();
        }
        boolean canPayFor = this.mEconomyManagerInternal.canPayFor(userId, str, actionBill);
        arrayMap.put(actionBill, java.lang.Boolean.valueOf(canPayFor));
        return canPayFor;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasEnoughWealthLocked(@android.annotation.NonNull com.android.server.alarm.Alarm alarm) {
        return canAffordBillLocked(alarm, com.android.server.alarm.TareBill.getAppropriateBill(alarm));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Bundle getAlarmOperationBundle(com.android.server.alarm.Alarm alarm) {
        if (alarm.mIdleOptions != null) {
            return alarm.mIdleOptions;
        }
        if (alarm.operation.isActivity()) {
            return this.mActivityOptsRestrictBal.toBundle();
        }
        return this.mBroadcastOptsRestrictBal.toBundle();
    }

    @com.android.internal.annotations.VisibleForTesting
    class AlarmHandler extends android.os.Handler {
        public static final int ALARM_EVENT = 1;
        public static final int APP_STANDBY_BUCKET_CHANGED = 5;
        public static final int CHARGING_STATUS_CHANGED = 6;
        public static final int CHECK_EXACT_ALARM_PERMISSION_ON_UPDATE = 13;
        public static final int LISTENER_TIMEOUT = 3;
        public static final int REFRESH_EXACT_ALARM_CANDIDATES = 11;
        public static final int REMOVE_EXACT_ALARMS = 8;
        public static final int REMOVE_EXACT_LISTENER_ALARMS_ON_CACHED = 15;
        public static final int REMOVE_FOR_CANCELED = 7;
        public static final int REPORT_ALARMS_ACTIVE = 4;
        public static final int SEND_NEXT_ALARM_CLOCK_CHANGED = 2;
        public static final int TARE_AFFORDABILITY_CHANGED = 12;
        public static final int TEMPORARY_QUOTA_CHANGED = 14;

        AlarmHandler() {
            super(android.os.Looper.myLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>();
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        com.android.server.alarm.AlarmManagerService.this.triggerAlarmsLocked(arrayList, com.android.server.alarm.AlarmManagerService.this.mInjector.getElapsedRealtimeMillis());
                        com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                    }
                    for (int i = 0; i < arrayList.size(); i++) {
                        com.android.server.alarm.Alarm alarm = arrayList.get(i);
                        try {
                            alarm.operation.send(null, 0, null, null, null, null, com.android.server.alarm.AlarmManagerService.this.getAlarmOperationBundle(alarm));
                        } catch (android.app.PendingIntent.CanceledException e) {
                            if (alarm.repeatInterval > 0) {
                                com.android.server.alarm.AlarmManagerService.this.removeImpl(alarm.operation, null);
                            }
                        }
                        com.android.server.alarm.AlarmManagerService.this.decrementAlarmCount(alarm.uid, 1);
                    }
                    return;
                case 2:
                    com.android.server.alarm.AlarmManagerService.this.sendNextAlarmClockChanged();
                    return;
                case 3:
                    com.android.server.alarm.AlarmManagerService.this.mDeliveryTracker.alarmTimedOut((android.os.IBinder) message.obj);
                    return;
                case 4:
                    if (com.android.server.alarm.AlarmManagerService.this.mLocalDeviceIdleController != null) {
                        com.android.server.alarm.AlarmManagerService.this.mLocalDeviceIdleController.setAlarmsActive(message.arg1 != 0);
                        return;
                    }
                    return;
                case 5:
                case 14:
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        try {
                            android.util.ArraySet<android.content.pm.UserPackage> arraySet = new android.util.ArraySet<>();
                            arraySet.add(android.content.pm.UserPackage.of(message.arg1, (java.lang.String) message.obj));
                            if (com.android.server.alarm.AlarmManagerService.this.reorderAlarmsBasedOnStandbyBuckets(arraySet)) {
                                com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                case 6:
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        try {
                            com.android.server.alarm.AlarmManagerService.this.mAppStandbyParole = ((java.lang.Boolean) message.obj).booleanValue();
                            if (com.android.server.alarm.AlarmManagerService.this.reorderAlarmsBasedOnStandbyBuckets(null)) {
                                com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                case 7:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) message.obj;
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        com.android.server.alarm.AlarmManagerService.this.removeLocked(pendingIntent, null, 4);
                    }
                    return;
                case 8:
                    com.android.server.alarm.AlarmManagerService.this.removeExactAlarmsOnPermissionRevoked(message.arg1, (java.lang.String) message.obj, true);
                    return;
                case 9:
                case 10:
                default:
                    return;
                case 11:
                    com.android.server.alarm.AlarmManagerService.this.refreshExactAlarmCandidates();
                    return;
                case 12:
                    synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                        try {
                            int i2 = message.arg1;
                            java.lang.String str = (java.lang.String) message.obj;
                            android.util.ArraySet<android.content.pm.UserPackage> arraySet2 = new android.util.ArraySet<>();
                            arraySet2.add(android.content.pm.UserPackage.of(i2, str));
                            if (com.android.server.alarm.AlarmManagerService.this.reorderAlarmsBasedOnTare(arraySet2)) {
                                com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                com.android.server.alarm.AlarmManagerService.this.updateNextAlarmClockLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                case 13:
                    java.lang.String str2 = (java.lang.String) message.obj;
                    int i3 = message.arg1;
                    if (!com.android.server.alarm.AlarmManagerService.this.hasScheduleExactAlarmInternal(str2, i3) && !com.android.server.alarm.AlarmManagerService.this.hasUseExactAlarmInternal(str2, i3)) {
                        com.android.server.alarm.AlarmManagerService.this.removeExactAlarmsOnPermissionRevoked(i3, str2, false);
                        return;
                    }
                    return;
                case 15:
                    com.android.server.alarm.AlarmManagerService.this.removeExactListenerAlarms(((java.lang.Integer) message.obj).intValue());
                    return;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class ChargingReceiver extends android.content.BroadcastReceiver {
        ChargingReceiver() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.os.action.CHARGING");
            intentFilter.addAction("android.os.action.DISCHARGING");
            com.android.server.alarm.AlarmManagerService.this.getContext().registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            boolean z;
            if ("android.os.action.CHARGING".equals(intent.getAction())) {
                z = true;
            } else {
                z = false;
            }
            com.android.server.alarm.AlarmManagerService.this.mHandler.removeMessages(6);
            com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(6, java.lang.Boolean.valueOf(z)).sendToTarget();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class ClockReceiver extends android.content.BroadcastReceiver {
        public ClockReceiver() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.DATE_CHANGED");
            com.android.server.alarm.AlarmManagerService.this.getContext().registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals("android.intent.action.DATE_CHANGED")) {
                scheduleDateChangedEvent();
            }
        }

        public void scheduleTimeTickEvent() {
            long currentTimeMillis = com.android.server.alarm.AlarmManagerService.this.mInjector.getCurrentTimeMillis();
            com.android.server.alarm.AlarmManagerService.this.setImpl(3, com.android.server.alarm.AlarmManagerService.this.mInjector.getElapsedRealtimeMillis() + ((((currentTimeMillis / 60000) + 1) * 60000) - currentTimeMillis), 0L, 0L, null, com.android.server.alarm.AlarmManagerService.this.mTimeTickTrigger, com.android.server.alarm.AlarmManagerService.TIME_TICK_TAG, (com.android.server.alarm.AlarmManagerService.this.mConstants.TIME_TICK_ALLOWED_WHILE_IDLE ? 8 : 0) | 1, null, null, android.os.Process.myUid(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, null, 1);
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.mLastTickSet = currentTimeMillis;
            }
        }

        public void scheduleDateChangedEvent() {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(com.android.server.alarm.AlarmManagerService.this.mInjector.getCurrentTimeMillis());
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.add(5, 1);
            com.android.server.alarm.AlarmManagerService.this.setImpl(1, calendar.getTimeInMillis(), 0L, 0L, com.android.server.alarm.AlarmManagerService.this.mDateChangeSender, null, null, 1, null, null, android.os.Process.myUid(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, null, 1);
        }
    }

    class InteractiveStateReceiver extends android.content.BroadcastReceiver {
        public InteractiveStateReceiver() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.setPriority(1000);
            com.android.server.alarm.AlarmManagerService.this.getContext().registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.interactiveStateChangedLocked("android.intent.action.SCREEN_ON".equals(intent.getAction()));
            }
        }
    }

    class UninstallReceiver extends android.content.BroadcastReceiver {
        public UninstallReceiver() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            com.android.server.alarm.AlarmManagerService.this.getContext().registerReceiverForAllUsers(this, intentFilter, null, null);
            android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            intentFilter2.addAction("android.intent.action.USER_STOPPED");
            intentFilter2.addAction("android.intent.action.UID_REMOVED");
            com.android.server.alarm.AlarmManagerService.this.getContext().registerReceiverForAllUsers(this, intentFilter2, null, null);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0098 A[Catch: all -> 0x0025, TryCatch #0 {all -> 0x0025, blocks: (B:4:0x000d, B:5:0x0017, B:9:0x0067, B:13:0x0098, B:15:0x009b, B:17:0x009f, B:19:0x00a3, B:20:0x00da, B:22:0x00e5, B:24:0x00f5, B:26:0x00fb, B:28:0x0102, B:32:0x0105, B:33:0x00d5, B:35:0x0108, B:38:0x006b, B:40:0x0073, B:42:0x0075, B:43:0x007c, B:45:0x0082, B:47:0x0088, B:48:0x008f, B:49:0x010a, B:51:0x0119, B:52:0x012e, B:54:0x0130, B:55:0x0149, B:57:0x014b, B:59:0x0153, B:60:0x0174, B:62:0x0176, B:64:0x017f, B:68:0x0189, B:69:0x018c, B:66:0x018e, B:72:0x0191, B:74:0x001b, B:77:0x0028, B:80:0x0032, B:83:0x003c, B:86:0x0046, B:89:0x0050, B:92:0x005a), top: B:3:0x000d }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x009f A[Catch: all -> 0x0025, TryCatch #0 {all -> 0x0025, blocks: (B:4:0x000d, B:5:0x0017, B:9:0x0067, B:13:0x0098, B:15:0x009b, B:17:0x009f, B:19:0x00a3, B:20:0x00da, B:22:0x00e5, B:24:0x00f5, B:26:0x00fb, B:28:0x0102, B:32:0x0105, B:33:0x00d5, B:35:0x0108, B:38:0x006b, B:40:0x0073, B:42:0x0075, B:43:0x007c, B:45:0x0082, B:47:0x0088, B:48:0x008f, B:49:0x010a, B:51:0x0119, B:52:0x012e, B:54:0x0130, B:55:0x0149, B:57:0x014b, B:59:0x0153, B:60:0x0174, B:62:0x0176, B:64:0x017f, B:68:0x0189, B:69:0x018c, B:66:0x018e, B:72:0x0191, B:74:0x001b, B:77:0x0028, B:80:0x0032, B:83:0x003c, B:86:0x0046, B:89:0x0050, B:92:0x005a), top: B:3:0x000d }] */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String[] stringArrayExtra;
            java.lang.String schemeSpecificPart;
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    java.lang.String action = intent.getAction();
                    switch (action.hashCode()) {
                        case -1749672628:
                            if (action.equals("android.intent.action.UID_REMOVED")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1403934493:
                            if (action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1072806502:
                            if (action.equals("android.intent.action.QUERY_PACKAGE_RESTART")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -757780528:
                            if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -742246786:
                            if (action.equals("android.intent.action.USER_STOPPED")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 525384130:
                            if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1544582882:
                            if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                                c = 3;
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
                            for (java.lang.String str : intent.getStringArrayExtra("android.intent.extra.PACKAGES")) {
                                if (com.android.server.alarm.AlarmManagerService.this.lookForPackageLocked(str, intExtra)) {
                                    setResultCode(-1);
                                    return;
                                }
                            }
                            return;
                        case 1:
                            int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                            if (intExtra2 >= 0) {
                                com.android.server.alarm.AlarmManagerService.this.removeUserLocked(intExtra2);
                                com.android.server.alarm.AlarmManagerService.this.mAppWakeupHistory.removeForUser(intExtra2);
                                com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleHistory.removeForUser(intExtra2);
                                com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleCompatHistory.removeForUser(intExtra2);
                                com.android.server.alarm.AlarmManagerService.this.mTemporaryQuotaReserve.removeForUser(intExtra2);
                            }
                            return;
                        case 2:
                            com.android.server.alarm.AlarmManagerService.this.mLastPriorityAlarmDispatch.delete(intExtra);
                            com.android.server.alarm.AlarmManagerService.this.mRemovalHistory.delete(intExtra);
                            com.android.server.alarm.AlarmManagerService.this.mLastOpScheduleExactAlarm.delete(intExtra);
                            return;
                        case 3:
                            com.android.server.alarm.AlarmManagerService.this.mHandler.sendEmptyMessage(11);
                            if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                                com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(13, intExtra, -1, intent.getData().getSchemeSpecificPart()).sendToTarget();
                            }
                            return;
                        case 4:
                            stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                            if (stringArrayExtra != null && stringArrayExtra.length > 0) {
                                for (java.lang.String str2 : stringArrayExtra) {
                                    if (intExtra >= 0) {
                                        com.android.server.alarm.AlarmManagerService.this.mAppWakeupHistory.removeForPackage(str2, android.os.UserHandle.getUserId(intExtra));
                                        com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleHistory.removeForPackage(str2, android.os.UserHandle.getUserId(intExtra));
                                        com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleCompatHistory.removeForPackage(str2, android.os.UserHandle.getUserId(intExtra));
                                        com.android.server.alarm.AlarmManagerService.this.mTemporaryQuotaReserve.removeForPackage(str2, android.os.UserHandle.getUserId(intExtra));
                                        com.android.server.alarm.AlarmManagerService.this.removeLocked(intExtra, 0);
                                    } else {
                                        com.android.server.alarm.AlarmManagerService.this.removeLocked(str2, 0);
                                    }
                                    for (int size = com.android.server.alarm.AlarmManagerService.this.mBroadcastStats.size() - 1; size >= 0; size--) {
                                        android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> valueAt = com.android.server.alarm.AlarmManagerService.this.mBroadcastStats.valueAt(size);
                                        if (valueAt.remove(str2) != null && valueAt.size() <= 0) {
                                            com.android.server.alarm.AlarmManagerService.this.mBroadcastStats.removeAt(size);
                                        }
                                    }
                                }
                            }
                            return;
                        case 5:
                            if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                                return;
                            } else {
                                com.android.server.alarm.AlarmManagerService.this.mHandler.sendEmptyMessage(11);
                            }
                        case 6:
                            android.net.Uri data = intent.getData();
                            if (data != null && (schemeSpecificPart = data.getSchemeSpecificPart()) != null) {
                                stringArrayExtra = new java.lang.String[]{schemeSpecificPart};
                                if (stringArrayExtra != null) {
                                    while (r2 < r0) {
                                    }
                                    break;
                                }
                                return;
                            }
                            stringArrayExtra = null;
                            if (stringArrayExtra != null) {
                            }
                            return;
                        default:
                            stringArrayExtra = null;
                            if (stringArrayExtra != null) {
                            }
                            return;
                    }
                } finally {
                }
            }
        }
    }

    private final class AppStandbyTracker extends com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener {
        private AppStandbyTracker() {
        }

        public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
            com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(5, i, -1, str).sendToTarget();
        }

        public void triggerTemporaryQuotaBump(java.lang.String str, int i) {
            int i2;
            int packageUid;
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                i2 = com.android.server.alarm.AlarmManagerService.this.mConstants.TEMPORARY_QUOTA_BUMP;
            }
            if (i2 <= 0 || (packageUid = com.android.server.alarm.AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, i)) < 0 || android.os.UserHandle.isCore(packageUid)) {
                return;
            }
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.mTemporaryQuotaReserve.replenishQuota(str, i, i2, com.android.server.alarm.AlarmManagerService.this.mInjector.getElapsedRealtimeMillis());
            }
            com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(14, i, -1, str).sendToTarget();
        }
    }

    /* renamed from: com.android.server.alarm.AlarmManagerService$8, reason: invalid class name */
    class AnonymousClass8 extends com.android.server.AppStateTrackerImpl.Listener {
        AnonymousClass8() {
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void updateAllAlarms() {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    if (com.android.server.alarm.AlarmManagerService.this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$8$$ExternalSyntheticLambda0
                        @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                        public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                            boolean lambda$updateAllAlarms$0;
                            lambda$updateAllAlarms$0 = com.android.server.alarm.AlarmManagerService.AnonymousClass8.this.lambda$updateAllAlarms$0(alarm);
                            return lambda$updateAllAlarms$0;
                        }
                    })) {
                        com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$updateAllAlarms$0(com.android.server.alarm.Alarm alarm) {
            return com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm);
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void updateAlarmsForUid(final int i) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    if (com.android.server.alarm.AlarmManagerService.this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$8$$ExternalSyntheticLambda1
                        @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                        public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm) {
                            boolean lambda$updateAlarmsForUid$1;
                            lambda$updateAlarmsForUid$1 = com.android.server.alarm.AlarmManagerService.AnonymousClass8.this.lambda$updateAlarmsForUid$1(i, alarm);
                            return lambda$updateAlarmsForUid$1;
                        }
                    })) {
                        com.android.server.alarm.AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$updateAlarmsForUid$1(int i, com.android.server.alarm.Alarm alarm) {
            if (alarm.creatorUid != i) {
                return false;
            }
            return com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm);
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void unblockAllUnrestrictedAlarms() {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.sendAllUnrestrictedPendingBackgroundAlarmsLocked();
            }
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void unblockAlarmsForUid(int i) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.sendPendingBackgroundAlarmsLocked(i, null);
            }
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void unblockAlarmsForUidPackage(int i, java.lang.String str) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.sendPendingBackgroundAlarmsLocked(i, str);
            }
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void removeAlarmsForUid(int i) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.removeForStoppedLocked(i);
            }
        }

        @Override // com.android.server.AppStateTrackerImpl.Listener
        public void handleUidCachedChanged(int i, boolean z) {
            long j;
            if (com.android.server.alarm.AlarmManagerService.this.mUseFrozenStateToDropListenerAlarms || !android.app.compat.CompatChanges.isChangeEnabled(265195908L, i)) {
                return;
            }
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                j = com.android.server.alarm.AlarmManagerService.this.mConstants.CACHED_LISTENER_REMOVAL_DELAY;
            }
            java.lang.Integer valueOf = java.lang.Integer.valueOf(i);
            if (!z || com.android.server.alarm.AlarmManagerService.this.mHandler.hasEqualMessages(15, valueOf)) {
                com.android.server.alarm.AlarmManagerService.this.mHandler.removeEqualMessages(15, valueOf);
            } else {
                com.android.server.alarm.AlarmManagerService.this.mHandler.sendMessageDelayed(com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(15, valueOf), j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final com.android.server.alarm.AlarmManagerService.BroadcastStats getStatsLocked(android.app.PendingIntent pendingIntent) {
        return getStatsLocked(pendingIntent.getCreatorUid(), pendingIntent.getCreatorPackage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final com.android.server.alarm.AlarmManagerService.BroadcastStats getStatsLocked(int i, java.lang.String str) {
        android.util.ArrayMap<java.lang.String, com.android.server.alarm.AlarmManagerService.BroadcastStats> arrayMap = this.mBroadcastStats.get(i);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            this.mBroadcastStats.put(i, arrayMap);
        }
        com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats = arrayMap.get(str);
        if (broadcastStats == null) {
            com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats2 = new com.android.server.alarm.AlarmManagerService.BroadcastStats(i, str);
            arrayMap.put(str, broadcastStats2);
            return broadcastStats2;
        }
        return broadcastStats;
    }

    class DeliveryTracker extends android.app.IAlarmCompleteListener.Stub implements android.app.PendingIntent.OnFinished {
        DeliveryTracker() {
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private com.android.server.alarm.AlarmManagerService.InFlight removeLocked(android.app.PendingIntent pendingIntent, android.content.Intent intent) {
            for (int i = 0; i < com.android.server.alarm.AlarmManagerService.this.mInFlight.size(); i++) {
                com.android.server.alarm.AlarmManagerService.InFlight inFlight = com.android.server.alarm.AlarmManagerService.this.mInFlight.get(i);
                if (inFlight.mPendingIntent == pendingIntent) {
                    if (pendingIntent.isBroadcast()) {
                        com.android.server.alarm.AlarmManagerService.this.notifyBroadcastAlarmCompleteLocked(inFlight.mUid);
                    }
                    return com.android.server.alarm.AlarmManagerService.this.mInFlight.remove(i);
                }
            }
            com.android.server.alarm.AlarmManagerService.this.mLog.w("No in-flight alarm for " + pendingIntent + " " + intent);
            return null;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private com.android.server.alarm.AlarmManagerService.InFlight removeLocked(android.os.IBinder iBinder) {
            for (int i = 0; i < com.android.server.alarm.AlarmManagerService.this.mInFlight.size(); i++) {
                if (com.android.server.alarm.AlarmManagerService.this.mInFlight.get(i).mListener == iBinder) {
                    return com.android.server.alarm.AlarmManagerService.this.mInFlight.remove(i);
                }
            }
            com.android.server.alarm.AlarmManagerService.this.mLog.w("No in-flight alarm for listener " + iBinder);
            return null;
        }

        private void updateStatsLocked(com.android.server.alarm.AlarmManagerService.InFlight inFlight) {
            long elapsedRealtimeMillis = com.android.server.alarm.AlarmManagerService.this.mInjector.getElapsedRealtimeMillis();
            com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats = inFlight.mBroadcastStats;
            broadcastStats.nesting--;
            if (broadcastStats.nesting <= 0) {
                broadcastStats.nesting = 0;
                broadcastStats.aggregateTime += elapsedRealtimeMillis - broadcastStats.startTime;
            }
            com.android.server.alarm.AlarmManagerService.FilterStats filterStats = inFlight.mFilterStats;
            filterStats.nesting--;
            if (filterStats.nesting <= 0) {
                filterStats.nesting = 0;
                filterStats.aggregateTime += elapsedRealtimeMillis - filterStats.startTime;
            }
            com.android.server.alarm.AlarmManagerService.this.mActivityManagerInternal.noteAlarmFinish(inFlight.mPendingIntent, inFlight.mWorkSource, inFlight.mUid, inFlight.mTag);
        }

        private void updateTrackingLocked(com.android.server.alarm.AlarmManagerService.InFlight inFlight) {
            if (inFlight != null) {
                updateStatsLocked(inFlight);
            }
            com.android.server.alarm.AlarmManagerService alarmManagerService = com.android.server.alarm.AlarmManagerService.this;
            alarmManagerService.mBroadcastRefCount--;
            if (com.android.server.alarm.AlarmManagerService.this.mBroadcastRefCount == 0) {
                com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(4, 0, 0).sendToTarget();
                com.android.server.alarm.AlarmManagerService.this.mWakeLock.release();
                if (com.android.server.alarm.AlarmManagerService.this.mInFlight.size() > 0) {
                    com.android.server.alarm.AlarmManagerService.this.mLog.w("Finished all dispatches with " + com.android.server.alarm.AlarmManagerService.this.mInFlight.size() + " remaining inflights");
                    for (int i = 0; i < com.android.server.alarm.AlarmManagerService.this.mInFlight.size(); i++) {
                        com.android.server.alarm.AlarmManagerService.this.mLog.w("  Remaining #" + i + ": " + com.android.server.alarm.AlarmManagerService.this.mInFlight.get(i));
                    }
                    com.android.server.alarm.AlarmManagerService.this.mInFlight.clear();
                    return;
                }
                return;
            }
            if (com.android.server.alarm.AlarmManagerService.this.mInFlight.size() > 0) {
                com.android.server.alarm.AlarmManagerService.InFlight inFlight2 = com.android.server.alarm.AlarmManagerService.this.mInFlight.get(0);
                com.android.server.alarm.AlarmManagerService.this.setWakelockWorkSource(inFlight2.mWorkSource, inFlight2.mCreatorUid, inFlight2.mTag, false);
            } else {
                com.android.server.alarm.AlarmManagerService.this.mLog.w("Alarm wakelock still held but sent queue empty");
                com.android.server.alarm.AlarmManagerService.this.mWakeLock.setWorkSource(null);
            }
        }

        public void alarmComplete(android.os.IBinder iBinder) {
            if (iBinder == null) {
                com.android.server.alarm.AlarmManagerService.this.mLog.w("Invalid alarmComplete: uid=" + android.os.Binder.getCallingUid() + " pid=" + android.os.Binder.getCallingPid());
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                    try {
                        com.android.server.alarm.AlarmManagerService.this.mHandler.removeMessages(3, iBinder);
                        com.android.server.alarm.AlarmManagerService.InFlight removeLocked = removeLocked(iBinder);
                        if (removeLocked != null) {
                            updateTrackingLocked(removeLocked);
                            com.android.server.alarm.AlarmManagerService.this.mListenerFinishCount++;
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.app.PendingIntent.OnFinished
        public void onSendFinished(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                com.android.server.alarm.AlarmManagerService.this.mSendFinishCount++;
                updateTrackingLocked(removeLocked(pendingIntent, intent));
            }
        }

        public void alarmTimedOut(android.os.IBinder iBinder) {
            synchronized (com.android.server.alarm.AlarmManagerService.this.mLock) {
                try {
                    com.android.server.alarm.AlarmManagerService.InFlight removeLocked = removeLocked(iBinder);
                    if (removeLocked != null) {
                        updateTrackingLocked(removeLocked);
                        com.android.server.alarm.AlarmManagerService.this.mListenerFinishCount++;
                    } else {
                        com.android.server.alarm.AlarmManagerService.this.mLog.w("Spurious timeout of listener " + iBinder);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0112  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x011f  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x018d  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x01b5  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x01ca  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x01cf  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x01ba  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0144  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x016a  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0121  */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void deliverLocked(final com.android.server.alarm.Alarm alarm, long j) {
            com.android.server.alarm.AlarmManagerService.InFlight inFlight;
            final boolean z;
            com.android.server.alarm.AlarmManagerService.BroadcastStats broadcastStats;
            com.android.server.alarm.AlarmManagerService.FilterStats filterStats;
            long uid = android.os.ThreadLocalWorkSource.setUid(com.android.server.alarm.AlarmManagerService.getAlarmAttributionUid(alarm));
            try {
                final boolean z2 = false;
                if (alarm.operation == null) {
                    com.android.server.alarm.AlarmManagerService.this.mListenerCount++;
                    alarm.listener.asBinder().unlinkToDeath(com.android.server.alarm.AlarmManagerService.this.mListenerDeathRecipient, 0);
                    if (alarm.listener == com.android.server.alarm.AlarmManagerService.this.mTimeTickTrigger) {
                        long[] jArr = com.android.server.alarm.AlarmManagerService.this.mTickHistory;
                        com.android.server.alarm.AlarmManagerService alarmManagerService = com.android.server.alarm.AlarmManagerService.this;
                        int i = alarmManagerService.mNextTickHistory;
                        alarmManagerService.mNextTickHistory = i + 1;
                        jArr[i] = j;
                        if (com.android.server.alarm.AlarmManagerService.this.mNextTickHistory >= 10) {
                            com.android.server.alarm.AlarmManagerService.this.mNextTickHistory = 0;
                        }
                    }
                    try {
                        alarm.listener.doAlarm(this);
                        com.android.server.alarm.AlarmManagerService.this.mHandler.sendMessageDelayed(com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(3, alarm.listener.asBinder()), com.android.server.alarm.AlarmManagerService.this.mConstants.LISTENER_TIMEOUT);
                        android.os.ThreadLocalWorkSource.restore(uid);
                        if (com.android.server.alarm.AlarmManagerService.this.mBroadcastRefCount == 0) {
                        }
                        inFlight = new com.android.server.alarm.AlarmManagerService.InFlight(com.android.server.alarm.AlarmManagerService.this, alarm, j);
                        com.android.server.alarm.AlarmManagerService.this.mInFlight.add(inFlight);
                        com.android.server.alarm.AlarmManagerService.this.mBroadcastRefCount++;
                        if (inFlight.isBroadcast()) {
                        }
                        if (com.android.server.alarm.AlarmManagerService.this.mPendingIdleUntil == null) {
                        }
                        if (com.android.server.alarm.AlarmManagerService.this.mAppStateTracker != null) {
                            z2 = true;
                        }
                        if (!z) {
                        }
                        if (!com.android.server.alarm.AlarmManagerService.isAllowedWhileIdleRestricted(alarm)) {
                        }
                        if (!com.android.server.alarm.AlarmManagerService.isExemptFromAppStandby(alarm)) {
                        }
                        broadcastStats = inFlight.mBroadcastStats;
                        broadcastStats.count++;
                        if (broadcastStats.nesting != 0) {
                        }
                        filterStats = inFlight.mFilterStats;
                        filterStats.count++;
                        if (filterStats.nesting != 0) {
                        }
                        if (alarm.type != 2) {
                        }
                        broadcastStats.numWakeup++;
                        filterStats.numWakeup++;
                        com.android.server.alarm.AlarmManagerService.this.mActivityManagerInternal.noteWakeupAlarm(alarm.operation, alarm.workSource, alarm.uid, alarm.packageName, alarm.statsTag);
                    } catch (java.lang.Exception e) {
                        com.android.server.alarm.AlarmManagerService.this.mListenerFinishCount++;
                        android.os.ThreadLocalWorkSource.restore(uid);
                        return;
                    }
                }
                com.android.server.alarm.AlarmManagerService.this.mSendCount++;
                try {
                    alarm.operation.send(com.android.server.alarm.AlarmManagerService.this.getContext(), 0, com.android.server.alarm.AlarmManagerService.this.mBackgroundIntent.putExtra("android.intent.extra.ALARM_COUNT", alarm.count), com.android.server.alarm.AlarmManagerService.this.mDeliveryTracker, com.android.server.alarm.AlarmManagerService.this.mHandler, null, com.android.server.alarm.AlarmManagerService.this.getAlarmOperationBundle(alarm));
                    android.os.ThreadLocalWorkSource.restore(uid);
                    if (com.android.server.alarm.AlarmManagerService.this.mBroadcastRefCount == 0) {
                        com.android.server.alarm.AlarmManagerService.this.setWakelockWorkSource(alarm.workSource, alarm.creatorUid, alarm.statsTag, true);
                        com.android.server.alarm.AlarmManagerService.this.mWakeLock.acquire();
                        com.android.server.alarm.AlarmManagerService.this.mHandler.obtainMessage(4, 1, 0).sendToTarget();
                    }
                    inFlight = new com.android.server.alarm.AlarmManagerService.InFlight(com.android.server.alarm.AlarmManagerService.this, alarm, j);
                    com.android.server.alarm.AlarmManagerService.this.mInFlight.add(inFlight);
                    com.android.server.alarm.AlarmManagerService.this.mBroadcastRefCount++;
                    if (inFlight.isBroadcast()) {
                        com.android.server.alarm.AlarmManagerService.this.notifyBroadcastAlarmPendingLocked(alarm.uid);
                    }
                    z = com.android.server.alarm.AlarmManagerService.this.mPendingIdleUntil == null;
                    if (com.android.server.alarm.AlarmManagerService.this.mAppStateTracker != null && com.android.server.alarm.AlarmManagerService.this.mAppStateTracker.isForceAllAppsStandbyEnabled()) {
                        z2 = true;
                    }
                    if (!z || z2) {
                        if (!com.android.server.alarm.AlarmManagerService.isAllowedWhileIdleRestricted(alarm)) {
                            ((4 & alarm.flags) != 0 ? com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleHistory : com.android.server.alarm.AlarmManagerService.this.mAllowWhileIdleCompatHistory).recordAlarmForPackage(alarm.sourcePackage, android.os.UserHandle.getUserId(alarm.creatorUid), j);
                            com.android.server.alarm.AlarmManagerService.this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticLambda0
                                @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                                public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm2) {
                                    boolean lambda$deliverLocked$0;
                                    lambda$deliverLocked$0 = com.android.server.alarm.AlarmManagerService.DeliveryTracker.this.lambda$deliverLocked$0(alarm, z, z2, alarm2);
                                    return lambda$deliverLocked$0;
                                }
                            });
                        } else if ((alarm.flags & 64) != 0) {
                            com.android.server.alarm.AlarmManagerService.this.mLastPriorityAlarmDispatch.put(alarm.creatorUid, j);
                            com.android.server.alarm.AlarmManagerService.this.mAlarmStore.updateAlarmDeliveries(new com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticLambda1
                                @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
                                public final boolean updateAlarmDelivery(com.android.server.alarm.Alarm alarm2) {
                                    boolean lambda$deliverLocked$1;
                                    lambda$deliverLocked$1 = com.android.server.alarm.AlarmManagerService.DeliveryTracker.this.lambda$deliverLocked$1(alarm, z, z2, alarm2);
                                    return lambda$deliverLocked$1;
                                }
                            });
                        }
                    }
                    if (!com.android.server.alarm.AlarmManagerService.isExemptFromAppStandby(alarm)) {
                        int userId = android.os.UserHandle.getUserId(alarm.creatorUid);
                        if (alarm.mUsingReserveQuota) {
                            com.android.server.alarm.AlarmManagerService.this.mTemporaryQuotaReserve.recordUsage(alarm.sourcePackage, userId, j);
                        } else {
                            com.android.server.alarm.AlarmManagerService.this.mAppWakeupHistory.recordAlarmForPackage(alarm.sourcePackage, userId, j);
                        }
                    }
                    broadcastStats = inFlight.mBroadcastStats;
                    broadcastStats.count++;
                    if (broadcastStats.nesting != 0) {
                        broadcastStats.nesting = 1;
                        broadcastStats.startTime = j;
                    } else {
                        broadcastStats.nesting++;
                    }
                    filterStats = inFlight.mFilterStats;
                    filterStats.count++;
                    if (filterStats.nesting != 0) {
                        filterStats.nesting = 1;
                        filterStats.startTime = j;
                    } else {
                        filterStats.nesting++;
                    }
                    if (alarm.type != 2 || alarm.type == 0) {
                        broadcastStats.numWakeup++;
                        filterStats.numWakeup++;
                        com.android.server.alarm.AlarmManagerService.this.mActivityManagerInternal.noteWakeupAlarm(alarm.operation, alarm.workSource, alarm.uid, alarm.packageName, alarm.statsTag);
                    }
                } catch (android.app.PendingIntent.CanceledException e2) {
                    if (alarm.repeatInterval > 0) {
                        com.android.server.alarm.AlarmManagerService.this.removeImpl(alarm.operation, null);
                    }
                    com.android.server.alarm.AlarmManagerService.this.mSendFinishCount++;
                    android.os.ThreadLocalWorkSource.restore(uid);
                }
            } catch (java.lang.Throwable th) {
                android.os.ThreadLocalWorkSource.restore(uid);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$deliverLocked$0(com.android.server.alarm.Alarm alarm, boolean z, boolean z2, com.android.server.alarm.Alarm alarm2) {
            if (alarm2.creatorUid == alarm.creatorUid && com.android.server.alarm.AlarmManagerService.isAllowedWhileIdleRestricted(alarm2)) {
                return (z && com.android.server.alarm.AlarmManagerService.this.lambda$triggerAlarmsLocked$25(alarm2)) || (z2 && com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm2));
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$deliverLocked$1(com.android.server.alarm.Alarm alarm, boolean z, boolean z2, com.android.server.alarm.Alarm alarm2) {
            if (alarm2.creatorUid != alarm.creatorUid || (alarm.flags & 64) == 0) {
                return false;
            }
            return (z && com.android.server.alarm.AlarmManagerService.this.lambda$triggerAlarmsLocked$25(alarm2)) || (z2 && com.android.server.alarm.AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm2));
        }
    }

    private void incrementAlarmCount(int i) {
        increment(this.mAlarmsPerUid, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendScheduleExactAlarmPermissionStateChangedBroadcast(java.lang.String str, int i) {
        android.content.Intent intent = new android.content.Intent("android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED");
        intent.addFlags(872415232);
        intent.setPackage(str);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(this.mActivityManagerInternal.getBootTimeTempAllowListDuration(), 0, 207, "");
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.of(i), null, makeBasic.toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decrementAlarmCount(int i, int i2) {
        int i3;
        int indexOfKey = this.mAlarmsPerUid.indexOfKey(i);
        if (indexOfKey < 0) {
            i3 = 0;
        } else {
            i3 = this.mAlarmsPerUid.valueAt(indexOfKey);
            if (i3 > i2) {
                this.mAlarmsPerUid.setValueAt(indexOfKey, i3 - i2);
            } else {
                this.mAlarmsPerUid.removeAt(indexOfKey);
            }
        }
        if (i3 < i2) {
            android.util.Slog.wtf(TAG, "Attempt to decrement existing alarm count " + i3 + " by " + i2 + " for uid " + i);
        }
    }

    private class ShellCmd extends android.os.ShellCommand {
        private ShellCmd() {
        }

        android.app.IAlarmManager getBinderService() {
            return android.app.IAlarmManager.Stub.asInterface(com.android.server.alarm.AlarmManagerService.this.mService);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                switch (str.hashCode()) {
                    case -2120488796:
                        if (str.equals("get-config-version")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1369384280:
                        if (str.equals("set-time")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2023087364:
                        if (str.equals("set-timezone")) {
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
                        if (getBinderService().setTime(java.lang.Long.parseLong(getNextArgRequired()))) {
                            break;
                        }
                        break;
                    case 1:
                        getBinderService().setTimeZone(getNextArgRequired());
                        break;
                    case 2:
                        outPrintWriter.println(getBinderService().getConfigVersion());
                        break;
                }
            } catch (java.lang.Exception e) {
                outPrintWriter.println(e);
                return -1;
            }
            return handleDefaultCommands(str);
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Alarm manager service (alarm) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  set-time TIME");
            outPrintWriter.println("    Set the system clock time to TIME where TIME is milliseconds");
            outPrintWriter.println("    since the Epoch.");
            outPrintWriter.println("  set-timezone TZ");
            outPrintWriter.println("    Set the system timezone to TZ where TZ is an Olson id.");
            outPrintWriter.println("  get-config-version");
            outPrintWriter.println("    Returns an integer denoting the version of device_config keys the service is sync'ed to. As long as this returns the same version, the values of the config are guaranteed to remain the same.");
        }
    }
}
