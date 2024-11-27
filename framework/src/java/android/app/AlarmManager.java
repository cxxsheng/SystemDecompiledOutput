package android.app;

/* loaded from: classes.dex */
public class AlarmManager {
    public static final java.lang.String ACTION_NEXT_ALARM_CLOCK_CHANGED = "android.app.action.NEXT_ALARM_CLOCK_CHANGED";
    public static final java.lang.String ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED = "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED";
    public static final int ELAPSED_REALTIME = 3;
    public static final int ELAPSED_REALTIME_WAKEUP = 2;
    public static final long ENABLE_USE_EXACT_ALARM = 218533173;
    public static final long ENFORCE_MINIMUM_WINDOW_ON_INEXACT_ALARMS = 185199076;
    public static final long EXACT_LISTENER_ALARMS_DROPPED_ON_CACHED = 265195908;
    public static final int FLAG_ALLOW_WHILE_IDLE = 4;
    public static final int FLAG_ALLOW_WHILE_IDLE_COMPAT = 32;
    public static final int FLAG_ALLOW_WHILE_IDLE_UNRESTRICTED = 8;
    public static final int FLAG_IDLE_UNTIL = 16;
    public static final int FLAG_PRIORITIZE = 64;
    public static final int FLAG_STANDALONE = 1;
    public static final int FLAG_WAKE_FROM_IDLE = 2;
    private static final java.lang.String GENERATED_TAG_PREFIX = "$android.alarm.generated";
    public static final long INTERVAL_DAY = 86400000;
    public static final long INTERVAL_FIFTEEN_MINUTES = 900000;
    public static final long INTERVAL_HALF_DAY = 43200000;
    public static final long INTERVAL_HALF_HOUR = 1800000;
    public static final long INTERVAL_HOUR = 3600000;
    public static final long REQUIRE_EXACT_ALARM_PERMISSION = 171306433;
    public static final int RTC = 1;
    public static final int RTC_WAKEUP = 0;
    public static final long SCHEDULE_EXACT_ALARM_DENIED_BY_DEFAULT = 226439802;
    public static final long SCHEDULE_EXACT_ALARM_DOES_NOT_ELEVATE_BUCKET = 262645982;
    private static final java.lang.String TAG = "AlarmManager";
    public static final long WINDOW_EXACT = 0;
    public static final long WINDOW_HEURISTIC = -1;
    private static java.util.WeakHashMap<android.app.AlarmManager.OnAlarmListener, java.lang.ref.WeakReference<android.app.AlarmManager.ListenerWrapper>> sWrappers;
    private final boolean mAlwaysExact;
    private final android.content.Context mContext;
    private final android.os.Handler mMainThreadHandler;
    private final java.lang.String mPackageName;
    private final android.app.IAlarmManager mService;
    private final int mTargetSdkVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AlarmType {
    }

    public interface OnAlarmListener {
        void onAlarm();
    }

    final class ListenerWrapper extends android.app.IAlarmListener.Stub implements java.lang.Runnable {
        android.app.IAlarmCompleteListener mCompletion;
        java.util.concurrent.Executor mExecutor;
        final android.app.AlarmManager.OnAlarmListener mListener;

        public ListenerWrapper(android.app.AlarmManager.OnAlarmListener onAlarmListener) {
            this.mListener = onAlarmListener;
        }

        void setExecutor(java.util.concurrent.Executor executor) {
            this.mExecutor = executor;
        }

        public void cancel() {
            try {
                android.app.AlarmManager.this.mService.remove(null, this);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.app.IAlarmListener
        public void doAlarm(android.app.IAlarmCompleteListener iAlarmCompleteListener) {
            this.mCompletion = iAlarmCompleteListener;
            this.mExecutor.execute(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mListener.onAlarm();
                try {
                    this.mCompletion.alarmComplete(this);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(android.app.AlarmManager.TAG, "Unable to report completion to Alarm Manager!", e);
                }
            } catch (java.lang.Throwable th) {
                try {
                    this.mCompletion.alarmComplete(this);
                } catch (java.lang.Exception e2) {
                    android.util.Log.e(android.app.AlarmManager.TAG, "Unable to report completion to Alarm Manager!", e2);
                }
                throw th;
            }
        }
    }

    AlarmManager(android.app.IAlarmManager iAlarmManager, android.content.Context context) {
        this.mService = iAlarmManager;
        this.mContext = context;
        this.mPackageName = context.getPackageName();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mAlwaysExact = this.mTargetSdkVersion < 19;
        this.mMainThreadHandler = new android.os.Handler(context.getMainLooper());
    }

    private long legacyExactLength() {
        return this.mAlwaysExact ? 0L : -1L;
    }

    public void set(int i, long j, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, legacyExactLength(), 0L, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void set(int i, long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
        setImpl(i, j, legacyExactLength(), 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, handler, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setRepeating(int i, long j, long j2, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, legacyExactLength(), j2, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, j2, 0L, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
        setImpl(i, j, j2, 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, handler, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setWindow(int i, long j, long j2, java.lang.String str, java.util.concurrent.Executor executor, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        setImpl(i, j, j2, 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, executor, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    @android.annotation.SystemApi
    public void setWindow(int i, long j, long j2, java.lang.String str, java.util.concurrent.Executor executor, android.os.WorkSource workSource, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        setImpl(i, j, j2, 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, executor, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    @android.annotation.SystemApi
    public void setPrioritized(int i, long j, long j2, java.lang.String str, java.util.concurrent.Executor executor, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, j2, 0L, 64, (android.app.PendingIntent) null, onAlarmListener, str, executor, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setExact(int i, long j, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, 0L, 0L, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setExact(int i, long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
        setImpl(i, j, 0L, 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, handler, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setIdleUntil(int i, long j, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler) {
        setImpl(i, j, 0L, 0L, 16, (android.app.PendingIntent) null, onAlarmListener, str, handler, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setAlarmClock(android.app.AlarmManager.AlarmClockInfo alarmClockInfo, android.app.PendingIntent pendingIntent) {
        setImpl(0, alarmClockInfo.getTriggerTime(), 0L, 0L, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, alarmClockInfo);
    }

    @android.annotation.SystemApi
    public void set(int i, long j, long j2, long j3, android.app.PendingIntent pendingIntent, android.os.WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void set(int i, long j, long j2, long j3, java.lang.String str, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler, android.os.WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, (android.app.PendingIntent) null, onAlarmListener, str, handler, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    private static java.lang.String makeTag(long j, android.os.WorkSource workSource) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(GENERATED_TAG_PREFIX);
        sb.append(":");
        sb.append(android.os.UserHandle.formatUid((workSource == null || workSource.isEmpty()) ? android.os.Process.myUid() : workSource.getAttributionUid()));
        sb.append(":");
        sb.append(j);
        return sb.toString();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void set(int i, long j, long j2, long j3, android.app.AlarmManager.OnAlarmListener onAlarmListener, android.os.Handler handler, android.os.WorkSource workSource) {
        setImpl(i, j, j2, j3, 0, (android.app.PendingIntent) null, onAlarmListener, makeTag(j, workSource), handler, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    @android.annotation.SystemApi
    public void setExact(int i, long j, java.lang.String str, java.util.concurrent.Executor executor, android.os.WorkSource workSource, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(workSource);
        java.util.Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, 0L, 0L, 0, (android.app.PendingIntent) null, onAlarmListener, str, executor, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    private void setImpl(int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.AlarmManager.OnAlarmListener onAlarmListener, java.lang.String str, android.os.Handler handler, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) {
        setImpl(i, j, j2, j3, i2, pendingIntent, onAlarmListener, str, new android.os.HandlerExecutor(handler != null ? handler : this.mMainThreadHandler), workSource, alarmClockInfo);
    }

    private void setImpl(int i, long j, long j2, long j3, int i2, android.app.PendingIntent pendingIntent, android.app.AlarmManager.OnAlarmListener onAlarmListener, java.lang.String str, java.util.concurrent.Executor executor, android.os.WorkSource workSource, android.app.AlarmManager.AlarmClockInfo alarmClockInfo) {
        long j4;
        android.app.AlarmManager.ListenerWrapper listenerWrapper;
        if (j >= 0) {
            j4 = j;
        } else {
            j4 = 0;
        }
        android.app.AlarmManager.ListenerWrapper listenerWrapper2 = null;
        if (onAlarmListener == null) {
            listenerWrapper = null;
        } else {
            synchronized (android.app.AlarmManager.class) {
                if (sWrappers == null) {
                    sWrappers = new java.util.WeakHashMap<>();
                }
                java.lang.ref.WeakReference<android.app.AlarmManager.ListenerWrapper> weakReference = sWrappers.get(onAlarmListener);
                if (weakReference != null) {
                    listenerWrapper2 = weakReference.get();
                }
                if (listenerWrapper2 == null) {
                    listenerWrapper2 = new android.app.AlarmManager.ListenerWrapper(onAlarmListener);
                    sWrappers.put(onAlarmListener, new java.lang.ref.WeakReference<>(listenerWrapper2));
                }
            }
            listenerWrapper2.setExecutor(executor);
            listenerWrapper = listenerWrapper2;
        }
        try {
            this.mService.set(this.mPackageName, i, j4, j2, j3, i2, pendingIntent, listenerWrapper, str, workSource, alarmClockInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInexactRepeating(int i, long j, long j2, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, -1L, j2, 0, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setAndAllowWhileIdle(int i, long j, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, -1L, 0L, 4, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void setExactAndAllowWhileIdle(int i, long j, android.app.PendingIntent pendingIntent) {
        setImpl(i, j, 0L, 0L, 4, pendingIntent, (android.app.AlarmManager.OnAlarmListener) null, (java.lang.String) null, (android.os.Handler) null, (android.os.WorkSource) null, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    @android.annotation.SystemApi
    public void setExactAndAllowWhileIdle(int i, long j, java.lang.String str, java.util.concurrent.Executor executor, android.os.WorkSource workSource, android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onAlarmListener);
        setImpl(i, j, 0L, 0L, 4, (android.app.PendingIntent) null, onAlarmListener, str, executor, workSource, (android.app.AlarmManager.AlarmClockInfo) null);
    }

    public void cancel(android.app.PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            if (this.mTargetSdkVersion >= 24) {
                throw new java.lang.NullPointerException("cancel() called with a null PendingIntent");
            }
            android.util.Log.e(TAG, "cancel() called with a null PendingIntent");
        } else {
            try {
                this.mService.remove(pendingIntent, null);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancel(android.app.AlarmManager.OnAlarmListener onAlarmListener) {
        android.app.AlarmManager.ListenerWrapper listenerWrapper;
        java.lang.ref.WeakReference<android.app.AlarmManager.ListenerWrapper> weakReference;
        if (onAlarmListener == null) {
            throw new java.lang.NullPointerException("cancel() called with a null OnAlarmListener");
        }
        synchronized (android.app.AlarmManager.class) {
            if (sWrappers != null && (weakReference = sWrappers.get(onAlarmListener)) != null) {
                listenerWrapper = weakReference.get();
            } else {
                listenerWrapper = null;
            }
        }
        if (listenerWrapper == null) {
            android.util.Log.w(TAG, "Unrecognized alarm listener " + onAlarmListener);
        } else {
            listenerWrapper.cancel();
        }
    }

    public void cancelAll() {
        try {
            this.mService.removeAll(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTime(long j) {
        try {
            this.mService.setTime(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTimeZone(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        if (this.mTargetSdkVersion >= 23 && !com.android.i18n.timezone.ZoneInfoDb.getInstance().hasTimeZone(str)) {
            throw new java.lang.IllegalArgumentException("Timezone: " + str + " is not an Olson ID");
        }
        try {
            this.mService.setTimeZone(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getNextWakeFromIdleTime() {
        try {
            return this.mService.getNextWakeFromIdleTime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canScheduleExactAlarms() {
        try {
            return this.mService.canScheduleExactAlarms(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasScheduleExactAlarm(java.lang.String str, int i) {
        try {
            return this.mService.hasScheduleExactAlarm(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.AlarmManager.AlarmClockInfo getNextAlarmClock() {
        return getNextAlarmClock(this.mContext.getUserId());
    }

    public android.app.AlarmManager.AlarmClockInfo getNextAlarmClock(int i) {
        try {
            return this.mService.getNextAlarmClock(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class AlarmClockInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AlarmManager.AlarmClockInfo> CREATOR = new android.os.Parcelable.Creator<android.app.AlarmManager.AlarmClockInfo>() { // from class: android.app.AlarmManager.AlarmClockInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AlarmManager.AlarmClockInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.AlarmManager.AlarmClockInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AlarmManager.AlarmClockInfo[] newArray(int i) {
                return new android.app.AlarmManager.AlarmClockInfo[i];
            }
        };
        private final android.app.PendingIntent mShowIntent;
        private final long mTriggerTime;

        public AlarmClockInfo(long j, android.app.PendingIntent pendingIntent) {
            this.mTriggerTime = j;
            this.mShowIntent = pendingIntent;
        }

        AlarmClockInfo(android.os.Parcel parcel) {
            this.mTriggerTime = parcel.readLong();
            this.mShowIntent = (android.app.PendingIntent) parcel.readParcelable(android.app.PendingIntent.class.getClassLoader());
        }

        public long getTriggerTime() {
            return this.mTriggerTime;
        }

        public android.app.PendingIntent getShowIntent() {
            return this.mShowIntent;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mTriggerTime);
            parcel.writeParcelable(this.mShowIntent, i);
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.mTriggerTime);
            if (this.mShowIntent != null) {
                this.mShowIntent.dumpDebug(protoOutputStream, 1146756268034L);
            }
            protoOutputStream.end(start);
        }
    }
}
